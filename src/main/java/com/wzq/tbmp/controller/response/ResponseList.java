package com.wzq.tbmp.controller.response;

import java.util.List;

public class ResponseList extends ResponseInfo {
	private static final long serialVersionUID = -8393478350625430804L;
	private List results;
	private int allRow;
	private int totalPage;
	private int currentPage;
	private int pageSize;

	public ResponseList() {

	}

	public ResponseList(String respCode, String respDesc, List results) {
		super(respCode, respDesc);
		this.results = results;
	}

	public List getResults() {
		return results;
	}

	public void setResults(List results) {
		this.results = results;
	}

	public int getAllRow() {
		return allRow;
	}

	public void setAllRow(int allRow) {
		this.allRow = allRow;
	}

	public int getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	
}
