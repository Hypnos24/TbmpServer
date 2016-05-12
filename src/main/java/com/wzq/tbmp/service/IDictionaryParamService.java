package com.wzq.tbmp.service;

import java.util.List;
import java.util.Map;

import com.wzq.tbmp.bean.DictionaryParamBean;
import com.wzq.tbmp.bean.PageBean;
import com.wzq.tbmp.pojo.DictionaryParam;

public interface IDictionaryParamService {
	public PageBean queryDictionaryParam(DictionaryParamBean paramBean);
	
	public boolean updateDictionaryParam(DictionaryParamBean paramBean);
	
	public boolean addDictionaryParam(DictionaryParamBean paramBean, StringBuffer sb);
	
	public List<DictionaryParam> queryDictionaryParam(int module);
	public Map<String, String> queryDictionaryParamMap(int module);
	
	public DictionaryParam getDictionaryParamByPk(String index, int module);
}
