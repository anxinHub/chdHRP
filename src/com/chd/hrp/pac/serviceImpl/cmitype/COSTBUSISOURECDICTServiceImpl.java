package com.chd.hrp.pac.serviceImpl.cmitype;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.chd.base.exception.SysException;

import com.chd.hrp.pac.dao.basicset.common.PactSelectMapper;
import com.chd.hrp.pac.dao.cmitype.COSTBUSISOURECDICTMapper;
import com.chd.hrp.pac.service.cmitype.COSTBUSISOURECDICTService;
@Service("COSTBUSISOURECDICTService")
public class COSTBUSISOURECDICTServiceImpl implements COSTBUSISOURECDICTService{
	private static Logger logger = Logger.getLogger(PACTInterfaceTypeServiceImpl.class);
	@Resource(name = "pactSelectMapper")
	private PactSelectMapper pactSelectMapper = null;
	@Resource(name = "COSTBUSISOURECDICTMapper")
	private COSTBUSISOURECDICTMapper costMapper = null;
	
	@Override
	public String queryCOSTBUSISOURECDICT(Map<String, Object> entityMap) {
		// TODO Auto-generated method stub
		try {
		//List<Map<String,Object>> list = pactSelectMapper.queryCOSTBUSISOURECDICT(entityMap);
		List<Map<String,Object>> list = costMapper.queryCOSTBUSISOURECDICT(entityMap);
		//return ChdJson.toJson(list);	//页面调试有数据显示
		return JSONArray.toJSONString(list);		//页面报类型转换的错误
	}catch (Exception e) {
		logger.warn(e.getMessage(), e);
		throw new SysException(e.getMessage(), e);
	}
	}
	

}
