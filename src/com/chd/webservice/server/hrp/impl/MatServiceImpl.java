package com.chd.webservice.server.hrp.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.chd.hrp.webservice.dao.hrp.MatMapper;
import com.chd.webservice.server.hrp.MatService;

@Service("matService")
public class MatServiceImpl implements MatService{
	
	private static Logger logger = Logger.getLogger(MatServiceImpl.class);


	@Resource(name = "matMapper")
	private final MatMapper matMapper = null;
 
	@Autowired
	private DataSourceTransactionManager txManager;

	//HRP云平台下载医院供应商（停用）
	@SuppressWarnings("unchecked")
	@Override
	public String queryHosSupDict(String group_id, String hos_id, String sup_id) {
		// TODO Auto-generated method stub
		
		Map<String, Object> resMap = new HashMap<String, Object>();
		resMap.put("state", "true");
		if(group_id==null || group_id.equals("")){
			resMap.put("state", "false");
			resMap.put("msg", "HRP云平台下载医院供应商：gourp_id参数为空");
			logger.error("HRP云平台下载医院供应商：gourp_id参数为空");
			return JSON.toJSONString(resMap); 
		}
		
		if(hos_id==null || hos_id.equals("")){
			resMap.put("state", "false");
			resMap.put("msg", "HRP云平台下载医院供应商：hos_id参数为空");
			logger.error("HRP云平台下载医院供应商：hos_id参数为空");
			return JSON.toJSONString(resMap); 
		}
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("group_id", group_id);
		map.put("hos_id", hos_id);
		map.put("sup_id", sup_id);
		
		
		logger.debug("HRP云平台下载医院供应商："+map.toString());
		
		List<Map<String, Object>> resList=new ArrayList<Map<String,Object>>();
		resList=matMapper.queryHosSupDict(map);
		resMap.put("data", resList);
		
		logger.debug("HRP云平台下载医院供应商：返回数据"+resList.size()+"条");
		return JSON.toJSONString(resMap); 
		
	}

}
