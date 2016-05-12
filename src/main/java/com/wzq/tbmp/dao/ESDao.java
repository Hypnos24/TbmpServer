package com.wzq.tbmp.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.sort.SortOrder;

import com.wzq.tbmp.bean.PageBean;
import com.wzq.tbmp.es.handler.ESHandler;
import com.wzq.tbmp.service.data.ElasticDataService;
import com.wzq.tbmp.util.GsonUtil;
import com.google.gson.JsonObject;


public class ESDao extends DaoSupport {
	
	private final Logger logger = Logger.getLogger(ESDao.class);
	
	public List queryESList(ESHandler handler, String tableName, String sortField, SortOrder order) {
		try {
			SearchRequestBuilder request = ElasticDataService.getInstance().getReadClient().
					prepareSearch(tableName)
					.setTypes("json")
					.setSearchType(SearchType.SCAN).setScroll(new TimeValue(60000)).setTimeout(new TimeValue(60000));
			
			handler.parseRequest(request);
			
			request.setSize(1000);
			
			if (sortField != null && order != null) {
				request.addSort(sortField, order);
			}
			
			logger.debug("[queryESList] " + request.toString());
			
			List list = new ArrayList();
			SearchResponse response = request.execute().actionGet();
			
			logger.debug("[queryESList] " + response.toString());
			
			while (true) {
				for(SearchHit hit : response.getHits()){
					JsonObject jo = GsonUtil.parseJsonObject(hit.getSourceAsString());
					jo.addProperty("_id", hit.getId());
					list.add(handler.parseSearchHit(jo));
				}
				response = ElasticDataService.getInstance().getReadClient().prepareSearchScroll(response.getScrollId()).setScroll(new TimeValue(60000)).execute().actionGet();
				if (response.getHits().getHits().length == 0) {
					break;
				}
			}
			return list;
		} catch (Exception e) {
			return new ArrayList();
		}
	}
	
	public Object queryESObject(ESHandler handler, String tableName, String sortField, SortOrder order) {
		try {
			SearchRequestBuilder request = ElasticDataService.getInstance().getReadClient().
					prepareSearch(tableName)
					.setTypes("json")
					.setSearchType(SearchType.SCAN).setScroll(new TimeValue(60000)).setTimeout(new TimeValue(60000));;
			
			handler.parseRequest(request);
			
			request.setSize(1000);
			
			if (sortField != null && order != null) {
				request.addSort(sortField, order);
			}
			
			logger.debug("[queryESObject] " + request.toString());
			
			SearchResponse response = request.execute().actionGet();
			
			logger.debug("[queryESObject] " + response.toString());
			
			return handler.parseResponse(response);
		} catch (Exception e) {
			return new Object();
		}
	}
	
	public Object queryESAgg(ESHandler handler, String tableName) {
		try {
			SearchRequestBuilder request = ElasticDataService.getInstance().getReadClient().
					prepareSearch(tableName)
					.setTypes("json")
					.setSearchType(SearchType.COUNT).setTimeout(new TimeValue(60000));;
			
			handler.parseRequest(request);
			
			logger.debug("[queryESAgg] " + request.toString());
			
			SearchResponse response = request.execute().actionGet();
			
			logger.debug("[queryESAgg] " + response.toString());
			
			return handler.parseAggResponse(response);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}
	
	public PageBean queryESPageBean(ESHandler handler, int page, int pageSize, String tableName, String sortField, SortOrder order) {
		try {
			SearchRequestBuilder request = ElasticDataService.getInstance().getReadClient()
					.prepareSearch(tableName)
					.setTypes("json")
					.setSearchType(SearchType.DFS_QUERY_THEN_FETCH).setTimeout(new TimeValue(60000));;
			
			handler.parseRequest(request);
			
			if (sortField != null && order != null) {
				request.addSort(sortField, order);
			}
			int from = (page - 1) * pageSize;
			request.setFrom(from).setSize(pageSize);
			
			logger.debug("[queryESPageBean] " + request.toString());
			
			List list = new ArrayList();
			
			SearchResponse response = request.execute().actionGet();
			
			logger.debug("[queryESPageBean] " + response.toString());
			
			long allRow = response.getHits().getTotalHits();
			
			for (SearchHit hit : response.getHits()) {
				JsonObject jo = GsonUtil.parseJsonObject(hit.getSourceAsString());
				jo.addProperty("_id", hit.getId());
				list.add(handler.parseSearchHit(jo));
			}
			return new PageBean(list, (int)allRow, page, pageSize);
		} catch (Exception e) {
			return new PageBean(new ArrayList(), 0, page, pageSize);
		}
	}
	
}
