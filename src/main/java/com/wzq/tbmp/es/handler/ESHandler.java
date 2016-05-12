package com.wzq.tbmp.es.handler;

import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;

import com.google.gson.JsonObject;

/**
 * @author cyd
 * @date 2015-6-16
 */
public interface ESHandler {
	public void parseRequest(SearchRequestBuilder request);
	public Object parseSearchHit(JsonObject hit);
	public Object parseResponse(SearchResponse response);
	public Object parseAggResponse(SearchResponse response);
}
