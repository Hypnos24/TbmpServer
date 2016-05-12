package com.wzq.tbmp.service.data;

import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;

import org.apache.log4j.Logger;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.ImmutableSettings;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;

import akka.actor.ActorRef;
import akka.actor.Props;

import com.wzq.tbmp.config.Constant;
import com.wzq.tbmp.service.akka.AkkaService;
import com.google.gson.JsonObject;

public class ElasticDataService {
	private static Logger logger = Logger.getLogger(ElasticDataService.class);
	private static ElasticDataService instance = new ElasticDataService();
	private TransportClient readClient;
	private TransportClient writeClient;
	private ActorRef actor;
	
	public static AtomicBoolean IS_WRITING = new AtomicBoolean(false);
	
	public static ElasticDataService getInstance() {
		return instance;
	}
	
	public enum Command {
		Flush
	}
	
	public void start() {
		Settings settings = ImmutableSettings.settingsBuilder()
				.put("cluster.name", Constant.ELASTICSEARCH_CLUSTER_NAME).build();
		readClient = new TransportClient(settings);
		readClient.addTransportAddress(new InetSocketTransportAddress(Constant.ELASTICSEARCH_HOST, Constant.ELASTICSEARCH_PORT));
		logger.info("启动elasticsearch读通道成功:"
				+ "cluster.name="+Constant.ELASTICSEARCH_CLUSTER_NAME+","
				+ "host="+Constant.ELASTICSEARCH_HOST+","
				+ "port="+Constant.ELASTICSEARCH_PORT);
		writeClient = new TransportClient(settings);
		writeClient.addTransportAddress(new InetSocketTransportAddress(Constant.ELASTICSEARCH_HOST, Constant.ELASTICSEARCH_PORT));
		logger.info("启动elasticsearch写通道成功:"
				+ "cluster.name="+Constant.ELASTICSEARCH_CLUSTER_NAME+","
				+ "host="+Constant.ELASTICSEARCH_HOST+","
				+ "port="+Constant.ELASTICSEARCH_PORT);
		actor = AkkaService.getInstance().getActorSystem().actorOf(Props.create(ElasticDataCollectActor.class));
	}
	
	public void stop() {
		if (writeClient != null) {
			writeClient.close();
		}
		if (readClient != null) {
			readClient.close();
		}
	}
	
	public TransportClient getReadClient() {
		return readClient;
	}
	
	//由ElasticDataCollectActor专用
	public TransportClient getWriteClient() {
		return writeClient;
	}

	public void save(String tableName, JsonObject data, int areaId) {
		if (areaId > 0) {
			tableName = tableName+"_s"+areaId;
		}
		BulkRequestBuilder bulkRequest = writeClient.prepareBulk();
		bulkRequest.add(writeClient.prepareIndex(tableName, "json", UUID.randomUUID().toString()).setSource(data.toString()));
		BulkResponse bulkResponse = bulkRequest.execute().actionGet();
		if (bulkResponse.hasFailures()) {
			logger.error("数据保存失败.");
		}else{
			//logger.info("数据保存成功.");
		}
	}
	
	public void batchSave(String tableName, JsonObject data, int areaId) {
		if (areaId > 0) {
			tableName = tableName+"_s"+areaId;
		}
		data.addProperty("tableName", tableName);
		while (IS_WRITING.get()) {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		actor.tell(data, ActorRef.noSender());
	}
	
	public void refreshCacheToES() {
		if (actor == null) {
			return;
		}
		actor.tell(Command.Flush, ActorRef.noSender());
	}

}
