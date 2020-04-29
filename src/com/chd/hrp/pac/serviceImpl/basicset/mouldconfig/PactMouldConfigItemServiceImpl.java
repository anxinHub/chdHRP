package com.chd.hrp.pac.serviceImpl.basicset.mouldconfig;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.hrp.pac.dao.basicset.mouldconfig.PactMouldConfigItemMapper;
//import com.chd.hrp.pac.entity.basicset.doc.PactDocEntity;
import com.chd.hrp.pac.service.basicset.mouldconfig.PactMouldConfigItemService;
import com.chd.hrp.pac.serviceImpl.basicset.mouldconfig.PactMouldConfigItemServiceImpl;
import com.chd.hrp.pac.dao.basicset.mouldconfig.PactMouldConfigItemMapper;

@Service("pactMouldConfigItemService")
public class PactMouldConfigItemServiceImpl implements PactMouldConfigItemService{
	private static Logger logger = Logger.getLogger(PactMouldConfigItemServiceImpl.class);

	@Resource(name = "pactMouldConfigItemMapper")
	private PactMouldConfigItemMapper pactMouldConfigItemMapper;
	
	@Override
	public String queryPactMouldConfig(Map<String, Object> mapVo) {
		List<Map<String,Object>> list= pactMouldConfigItemMapper.queryPactMouldConfig(mapVo);
		
		return  ChdJson.toJson(list);
	}
	
	@Override
	public String queryDataPropertySelect(Map<String, Object> mapVo) {
		List<Map<String,Object>> list= pactMouldConfigItemMapper.queryDataPropertySelect(mapVo);
		
		return  ChdJson.toJson(list);
	}
	
	@Override
	public String add(Map<String, Object> mapVo)
	{
		//根据gup_id,hos_id,copy_code,mould_code,查询是否存在重复数据
				System.out.println("hhhyyy");
				int existCode = pactMouldConfigItemMapper.queryIsExistMouldByCode(mapVo);
				if (existCode > 0) {
					return "{\"error\":\"数据重复,请重新添加.\"}";
				}
				try {
					System.out.println("hhh");
					int state = pactMouldConfigItemMapper.add(mapVo);
					return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";
				}catch (Exception e) {
					logger.error(e.getMessage(), e);
					return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 pactMouldConfigItemMapper add\"}";
				}
	}
	
	@Override
	public String deleteBatch(List<Map<String, Object>> listVo) {
		try {
			pactMouldConfigItemMapper.deleteBatch(listVo);			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		}catch (Exception e) {
			logger.error(e.getMessage(), e);
			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 deleteBatchMatLocationType\"}";
		}	
	}
	
	@Override
	public String queryMouldItem(Map<String, Object> mapVo) {
		try{
		List<Map<String,Object>> list= pactMouldConfigItemMapper.queryMouldItem(mapVo);
		return JSONArray.toJSONString(list);
		}catch (Exception e){
			logger.error(e.getMessage(), e);
			return "{\"error\":\"查询失败\"}";
		}
		
	}
	
	@Override
	public String update(Map<String, Object> mapVo)
	{
				try {
					//System.out.println("hhh");
					int state = pactMouldConfigItemMapper.update(mapVo);
					return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";
				}
				catch (Exception e) {
					logger.error(e.getMessage(), e);
					return "{\"error\":\"更新失败 数据库异常 请联系管理员! 方法 pactMouldConfigItemMapper update\"}";
				}
	}
	
}
