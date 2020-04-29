package com.chd.hrp.hpm.serviceImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.chd.base.SysPage;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.hrp.hpm.dao.AphiTemplateWageConfMapper;
import com.chd.hrp.hpm.service.AphiTemplateWageConfService;
import com.github.pagehelper.PageInfo;

@Service("aphiTemplateWageConfService")
public class AphiTemplateWageConfServiceImpl implements AphiTemplateWageConfService{
	
	private static Logger logger = Logger.getLogger(AphiTemplateWageConfServiceImpl.class);
	
	@Resource(name = "aphiTemplateWageConfMapper")
	private final AphiTemplateWageConfMapper aphiTemplateWageConfMapper = null;

	@Override
	public String addAphiTemplateWageConf(Map<String, Object> entityMap) throws DataAccessException {
		
		try {
			
			List<Map<String,Object>> detailList = new ArrayList<Map<String,Object>>();
			JSONArray detailDataJson = JSONArray.parseArray(String.valueOf(entityMap.get("detail_data")));
			Iterator iterator = detailDataJson.iterator();
			
			while (iterator.hasNext()) {
				
				JSONObject jsonObj = JSONObject.parseObject(iterator.next().toString());
				
				if( jsonObj.get("wage_item") == null || "".equals(jsonObj.get("wage_item")) ){
					continue;
				}
				
				if(jsonObj.get("emp_item") == null || "".equals(jsonObj.get("emp_item"))){
					continue;
				}
				
				Map<String,Object> detailMap = new HashMap<String,Object>();
				detailMap.put("group_id", entityMap.get("group_id"));
				detailMap.put("hos_id", entityMap.get("hos_id"));
				detailMap.put("copy_code", entityMap.get("copy_code"));
				detailMap.put("wage_code", entityMap.get("wage_code"));
				detailMap.put("wage_item", jsonObj.get("wage_item"));
				detailMap.put("emp_item", jsonObj.get("emp_item"));
				
				detailList.add(detailMap);
				
			}
			
			/****  修改前       ****/
			/*List<Map<String, Object>> list = aphiTemplateWageConfMapper.queryAphiTemplateWageConf(entityMap);
			
			if(list.size() == 0 ){
				aphiTemplateWageConfMapper.addAphiTemplateWageConf(entityMap);
			}else{
				aphiTemplateWageConfMapper.updateAphiTemplateWageConf(entityMap);
			}
			
			
			aphiTemplateWageConfMapper.deleteAphiTemplateWageConfDetailAll(entityMap);*///清空数据
			
			
			/*************************修改后cjc*********************************/
			
			List<Map<String, Object>> list = aphiTemplateWageConfMapper.queryAphiTemplateWageConf(entityMap);
			
			if(list.size() == 0 ){
			
				aphiTemplateWageConfMapper.addAphiTemplateWageConf(entityMap);
			
			}else{
			
				aphiTemplateWageConfMapper.updateAphiTemplateWageConf(entityMap);
			
			}
			
			 aphiTemplateWageConfMapper.deleteAphiTemplateWageConfDetailAll(entityMap);
				
			 aphiTemplateWageConfMapper.addBatchAphiTemplateWageConfDetail(detailList);
		
			return "{\"msg\":\"保存成功.\",\"state\":\"true\"}";
			
		} catch (Exception e) {
			
			// TODO: handle exception
			logger.error(e.getMessage(), e);
			
			throw new SysException("{\"error\":\"保存失败 \"}");
		}
	}

	@Override
	public String queryAphiTemplateWageConfDetail( Map<String, Object> entityMap) throws DataAccessException {
		
		SysPage sysPage = new SysPage();
		sysPage = (SysPage) entityMap.get("sysPage");

		if (sysPage.getTotal() == -1) {

			List<Map<String,Object>> list = aphiTemplateWageConfMapper.queryAphiTemplateWageConfDetail(entityMap);
			return ChdJson.toJson(list);

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			List<Map<String,Object>> list = aphiTemplateWageConfMapper.queryAphiTemplateWageConfDetail(entityMap, rowBounds);
			PageInfo page = new PageInfo(list);

			return ChdJson.toJson(list, page.getTotal());

		}
	}

	@Override
	public String queryWageItem(Map<String, Object> entityMap) throws DataAccessException { // TODO Auto-generated method stub
		
		
		SysPage sysPage = new SysPage();
		sysPage = (SysPage) entityMap.get("sysPage");
		if (sysPage.getTotal()==-1){
			List<Map<String, Object>> list = aphiTemplateWageConfMapper.queryWageItem(entityMap);
			
			return ChdJson.toJsonLower(list);
		}else{
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			List<Map<String, Object>> list =aphiTemplateWageConfMapper.queryWageItem(entityMap, rowBounds);
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJsonLower(list, page.getTotal());
		}
	}

	@Override
	public Map<String, Object> queryAphiTemplateWageConfByCode(Map<String, Object> entityMap) throws DataAccessException {
		
		List<Map<String, Object>> list = aphiTemplateWageConfMapper.queryAphiTemplateWageConf(entityMap);
		
		if(list.size() > 0){
			return list.get(0);
		}else{
			return null;
		}
		
	}

	@Override
	public Map<String, Object> queryWage(Map<String, Object> entityMap) throws DataAccessException {
		
		List<Map<String, Object>> list = aphiTemplateWageConfMapper.queryWage(entityMap);

		return ChdJson.toListLower(list).get(0);
	}

	@Override
	public String deleteBatchWage(List<Map<String, Object>> listVo)
			throws DataAccessException {
		try {
			
			aphiTemplateWageConfMapper.deleteAphiTemplateWageConfDetailMain(listVo);
			
			//当明细表没有数据的时候 ,删除主表数据
			int list = aphiTemplateWageConfMapper.queryWageDetailList(listVo);
			
			if(list == 0){
				
				aphiTemplateWageConfMapper.deleteAphiTemplateWageMain(listVo);
			
			}

			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}
	}

}
