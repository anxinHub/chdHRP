﻿package com.chd.hrp.htc.serviceImpl.task.basic;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.SysPage;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.hrp.htc.dao.task.basic.HtcIassetDictMapper;
import com.chd.hrp.htc.entity.task.basic.HtcIassetDict;
import com.chd.hrp.htc.service.task.basic.HtcIassetDictService;
import com.github.pagehelper.PageInfo;

/**
 * 2015-3-18 author:alfred
 */

@Service("htcIassetDictService")
public class HtcIassetDictServiceImpl implements HtcIassetDictService {

	private static Logger logger = Logger.getLogger(HtcIassetDictServiceImpl.class);

	@Resource(name = "htcIassetDictMapper")
	private final HtcIassetDictMapper htcIassetDictMapper = null;

	@Override
	public String addHtcIassetDict(Map<String, Object> entityMap) throws DataAccessException {
		
		try {
			HtcIassetDict htcIassetDict = queryHtcIassetDictByCode(entityMap);
			
			if(null != htcIassetDict){
				return "{\"error\":\"卡片号重复.\",\"state\":\"false\"}";
			}
			
			htcIassetDictMapper.addHtcIassetDict(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"添加失败\"}");
		}
	}

	@Override
	public String queryHtcIassetDict(Map<String, Object> entityMap) throws DataAccessException {
		
		SysPage sysPage = new SysPage();
		sysPage = (SysPage) entityMap.get("sysPage");
		if(sysPage.getTotal() == -1){
			List<HtcIassetDict> list = htcIassetDictMapper.queryHtcIassetDict(entityMap);
			
			return ChdJson.toJson(list);
		}else{
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<HtcIassetDict> list = htcIassetDictMapper.queryHtcIassetDict(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
		}
	}

	@Override
	public HtcIassetDict queryHtcIassetDictByCode(Map<String, Object> entityMap) throws DataAccessException {
		
		return htcIassetDictMapper.queryHtcIassetDictByCode(entityMap);
		
	}

	@Override
	public String deleteHtcIassetDict(Map<String, Object> entityMap) throws DataAccessException {
		
		try {
			htcIassetDictMapper.deleteHtcIassetDict(entityMap);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage(), e);
			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码 deleteHtcIassetDict\"}";
		}
	}

	@Override
	public String deleteBatchHtcIassetDict(List<Map<String, Object>> entityList) throws DataAccessException{
		
		try {
			htcIassetDictMapper.deleteBatchHtcIassetDict(entityList);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"删除失败\"}");
		}
	}
	
	@Override
	public String updateHtcIassetDict(Map<String, Object> entityMap) throws DataAccessException {
		
		try {
			htcIassetDictMapper.updateHtcIassetDict(entityMap);
			
			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"修改失败\"}");
		}
	}


}
