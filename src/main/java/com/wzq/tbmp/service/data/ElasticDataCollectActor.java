package com.wzq.tbmp.service.data;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.client.transport.TransportClient;

import akka.actor.UntypedActor;

import com.wzq.tbmp.config.Constant;
import com.wzq.tbmp.util.GsonUtil;
import com.google.gson.JsonObject;

public class ElasticDataCollectActor extends UntypedActor {

	private static Logger logger = Logger.getLogger(ElasticDataCollectActor.class);
	
	private List<JsonObject> dataList = new ArrayList<JsonObject>();
	
	@Override
	public void onReceive(Object obj) throws Exception {
		if(obj instanceof JsonObject){
			JsonObject data = (JsonObject)obj;
			dataList.add(data);
			if (dataList.size() >= Constant.ELASTICSEARCH_CACHE_SIZE) {
				refreshToES();
			}
		} else if (obj instanceof ElasticDataService.Command) {
			ElasticDataService.Command command = (ElasticDataService.Command)obj;
			if (command == ElasticDataService.Command.Flush) {
				refreshToES();
			}
		}
	}

	private void refreshToES() {
		if (dataList.isEmpty()) {
			return;
		}
		try {
			ElasticDataService.IS_WRITING.set(true);
			TransportClient client = ElasticDataService.getInstance().getWriteClient();
			BulkRequestBuilder bulkRequest = client.prepareBulk();
			//bulkRequest.setReplicationType(ReplicationType.ASYNC).setConsistencyLevel(WriteConsistencyLevel.ONE);
			List<JsonObject> removeList = new ArrayList<JsonObject>();
			Iterator<JsonObject> it = dataList.iterator();
			while (it.hasNext()) {
				JsonObject data = it.next();
				//要copy一份，否在下一轮保存的时候，没有节点tableName
				JsonObject copyJo = GsonUtil.parseJsonObject(data.toString());
				String tableName = data.get("tableName").getAsString();
				data.remove("tableName");
				bulkRequest.add(client.prepareIndex(tableName, "json", UUID.randomUUID().toString()).setSource(data.toString()));
				it.remove();
				removeList.add(copyJo);
			}
			BulkResponse bulkResponse = bulkRequest.execute().actionGet();
			if (bulkResponse.hasFailures()) {
				//保存失败，把removeList添加到dataList，以便下一轮保存
				dataList.addAll(removeList);
				logger.error("数据保存失败：" + bulkResponse.buildFailureMessage());
			}
		} catch (Exception e) {
			logger.error(e);
		} finally {
			ElasticDataService.IS_WRITING.set(false);
		}
	}
}
