package com.chd.hrp.pac.serviceImpl.jbrps;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.chd.base.SessionManager;
import com.chd.base.SysPage;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.base.util.DateUtil;
import com.chd.hrp.budg.dao.project.information.HosProjDictMapper;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.chd.hrp.mat.entity.MatFimType;
import com.chd.hrp.pac.service.jbrps.PactMainJBRtService;
import com.chd.hrp.pac.dao.jbrps.PactMainJBRMapper;
@Service(value="pactMainJBRtService")
public class PactMainJBRtServiceImpl implements PactMainJBRtService{
  
	private static Logger logger= Logger.getLogger(PactMainJBRtServiceImpl.class);
	
	@Resource(name = "pactMainJBRMapper")
	private PactMainJBRMapper pactMainJBRMapper;
	
	@Override
	public String queryPactAlteration(Map<String, Object> entityMap)throws DataAccessException{
		
		try{
			List<Map<String,Object>> list = (List<Map<String,Object>>) pactMainJBRMapper.queryPactAlteration(entityMap);
			return ChdJson.toJsonLower(list);
		}catch (Exception e){
			logger.warn(e.getMessage(), e);
			throw new SysException(e.getMessage(), e);
			
		}
	}
	
	@Override
	public String add(Map<String, Object> mapVo) {
		
		try{
			int state =pactMainJBRMapper.add(mapVo);
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";
			
		}catch(Exception e){
			
			logger.warn(e.getMessage(),e);
			throw new SysException(e.getMessage(),e);
		}
	}
	
	@Override
	public String update(Map<String ,Object>mapVo){
		
		try{
			int state =pactMainJBRMapper.update(mapVo);
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";
		}catch(Exception e){
			logger.warn(e.getMessage(),e);
			throw new SysException(e.getMessage(),e);
		}
	}
}
