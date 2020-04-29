package com.chd.hrp.mat.serviceImpl.storage.traceability;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.chd.base.SysPage;
import com.chd.base.util.ChdJson;
import com.chd.hrp.mat.dao.storage.traceability.MatInvTraceabilityMapper;
import com.chd.hrp.mat.service.storage.traceability.MatInvTraceabilityService;
import com.chd.hrp.sys.entity.User;
import com.github.pagehelper.PageInfo;


@Service("matInvTraceabilityService")
public class MatInvTraceabilityServiceImpl implements MatInvTraceabilityService {
	
	private static Logger logger = Logger.getLogger(MatInvTraceabilityServiceImpl.class);
	
	@Resource(name = "matInvTraceabilityMapper")
	private final MatInvTraceabilityMapper matInvTraceabilityMapper = null;

	/**
	 * 查询左边数据
	 */
	@Override
	public String queryMatInvTraceability(Map<String, Object> entityMap)  
			throws DataAccessException {
		
		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

		List<Map<String,String>> list = matInvTraceabilityMapper.queryMatInvTraceability(entityMap, rowBounds);
		
		PageInfo page = new PageInfo(list);
		
		return ChdJson.toJson(list, page.getTotal());
	}

	
	/**
	 * 右边查询
	 */
	@Override
	public String queryMatInvTraceabilityBar_code(Map<String, Object> entityMap)
			throws DataAccessException {
		List<Map<String,String>> list = matInvTraceabilityMapper.queryMatInvTraceabilityBar_code(entityMap);
		return ChdJson.toJson(list);
	}
	
	
	
	
	
	
	
	
}
