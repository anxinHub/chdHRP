package com.chd.hrp.acc.serviceImpl;
/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/

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
import com.chd.hrp.acc.dao.AccTargetMapper;
import com.chd.hrp.acc.service.AccTargetService;
import com.chd.hrp.acc.service.verification.AccBudgLederService;
import com.chd.hrp.sys.dao.UnitMapper;
import com.chd.hrp.sys.dao.notGeneral.SysFunUtilMapper;
import com.github.pagehelper.PageInfo;

@Service("accTargetService")
public class AccTargetServiceImpl implements AccTargetService{

	private static Logger logger = Logger.getLogger(AccTargetServiceImpl.class);
	
	@Resource(name = "accTargetMapper")
	private final AccTargetMapper accTargetMapper = null;

	@Resource(name = "sysFunUtilMapper")
	private final SysFunUtilMapper sysFunUtilMapper = null;
	
	@Resource(name = "unitMapper")
	private final UnitMapper unitMapper = null;
	
	@Resource(name = "accBudgLederService")
	private final AccBudgLederService accBudgLederService = null;
	
	@Override
	public String addAccTarget(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		try {

			Map<String,Object> accTargetMap = accTargetMapper.queryAccTargetByCode(entityMap);
			
			if(accTargetMap != null){
				
				return "{\"error\":\"编码：" + accTargetMap.get("target_code").toString() + "已存在.\"}";
			}
			
			
			/*
			 *自动生成排序号 
			 * */
			Map<String, Object> utilMap = new HashMap<String, Object>();
			
			utilMap.put("group_id", entityMap.get("group_id"));
			
			utilMap.put("hos_id", entityMap.get("hos_id"));
			
			utilMap.put("copy_code",entityMap.get("copy_code"));
			
			utilMap.put("field_table","acc_target");
			
			utilMap.put("field_sort", "sort_code");
			
			int count = sysFunUtilMapper.querySysMaxSort(utilMap);
			
			entityMap.put("sort_code", count);
			
			accTargetMapper.addAccTarget(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";
			
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码 addAccTarget\"}";
		}

	}

	@Override
	public String queryAccTarget(Map<String, Object> entityMap) throws DataAccessException {
		SysPage sysPage = new SysPage();
		sysPage = (SysPage) entityMap.get("sysPage");
		if (sysPage.getTotal() == -1) {
			List<Map<String, Object>> list = accTargetMapper.queryAccTarget(entityMap);
			return ChdJson.toJson(list);
		} else {
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			List<Map<String, Object>> list = accTargetMapper.queryAccTarget(entityMap, rowBounds);
			PageInfo page = new PageInfo(list);
			return ChdJson.toJson(list, page.getTotal());
		}
	}

	@Override
	public String queryAccTargetData(Map<String, Object> entityMap) throws DataAccessException {
		SysPage sysPage = new SysPage();
		sysPage = (SysPage) entityMap.get("sysPage");
		if (sysPage.getTotal() == -1) {
			List<Map<String, Object>> list = accTargetMapper.queryAccTargetData(entityMap);
			return ChdJson.toJson(list);
		} else {
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			List<Map<String, Object>> list = accTargetMapper.queryAccTargetData(entityMap, rowBounds);
			PageInfo page = new PageInfo(list);
			return ChdJson.toJson(list, page.getTotal());
		}
	}

	@Override
	public Map<String, Object> queryAccTargetByCode(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		return accTargetMapper.queryAccTargetByCode(entityMap);
	}


	@Override
	public Map<String, Object> queryAccTargetDataByCode(
			Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		return accTargetMapper.queryAccTargetDataByCode(entityMap);
	}

	@Override
	public String updateAccTarget(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		
		try {
			
			accTargetMapper.updateAccTarget(entityMap);
			
			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";
			
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e.getMessage(), e);

			return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updateAccTarget\"}";
		}

	}

	@Override
	public String saveAccTargetData(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		
		try {
			
			Map<String, Object>  accTargetDateMap = accTargetMapper.queryAccTargetDataByCode(entityMap);
			
			if(accTargetDateMap!=null){
				
				return "{\"msg\":\"该基本指标已经存在数据！\",\"state\":\"true\"}";
			}
			
			accTargetMapper.deleteAccTargetData(entityMap);
			
			accTargetMapper.addAccTargetData(entityMap);
			
			
			return "{\"log\":\"1\",\"state\":\"true\"}";
			
		} catch (Exception e) {
			// TODO: handle exception
			throw new SysException(e.getMessage());

		}
	}
	
	
	
	
	@Override
	public String deleteBatchAccTarget(List<Map<String, Object>> list)
			throws DataAccessException {
		// TODO Auto-generated method stub
		try {
			
			accTargetMapper.deleteBatchAccTarget(list);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
			
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码  deleteBatchAccTarget\"}";
		}

	}

	@Override
	public String deleteBatchAccTargetData(List<Map<String, Object>> list)
			throws DataAccessException {
		// TODO Auto-generated method stub
         try {
			
        	 if(list.size() > 0){
			   accTargetMapper.deleteBatchAccTargetData(list);
        	 }
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
			
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码  deleteBatchAccTargetData\"}";
		}
	}

	@Override
	public String addBatchAccTarget(List<Map<String, Object>> list)
			throws DataAccessException {
		// TODO Auto-generated method stub
		try {
			
			accTargetMapper.addBatchAccTarget(list);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";
			
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加成功 数据库异常 请联系管理员! 错误编码  addBatchAccTarget\"}";
		}
	}

	@Override
	public String saveBatchAccTargetData(Map<String, Object> entityMap)
			throws DataAccessException {
		/*int flag = 0;
		flag = accBudgLederService.queryAccMonthIsAcc(entityMap);
	   
	    if(flag == 1){
	    	
	    	 return "{\"error\":\"当前会计期间已经结账！\"}";
	    }*/
		
		// TODO Auto-generated method stub
		List<Map<String,Object>> dataList = new ArrayList<Map<String,Object>>();
		if(entityMap.get("data") != null && !"[]".equals(String.valueOf(entityMap.get("data")))){
			JSONArray json = JSONArray.parseArray((String)entityMap.get("data"));
			Iterator it = json.iterator();
			while (it.hasNext()) {
				JSONObject jsonObj = JSONObject.parseObject(it.next().toString());
				
				Map<String,Object> dateMap = new HashMap<String,Object>();
				
				if(jsonObj.get("target_code")!=null&&!"".equals(jsonObj.get("target_code"))){
					
					dateMap.put("group_id", entityMap.get("group_id"));
					
					dateMap.put("hos_id", entityMap.get("hos_id"));
					
					dateMap.put("copy_code", entityMap.get("copy_code"));
					
					dateMap.put("acc_year", entityMap.get("acc_year"));
					
					dateMap.put("acc_month", entityMap.get("acc_month"));
					
					dateMap.put("target_code", jsonObj.get("target_code").toString().split(",")[0]);
					
					if(jsonObj.containsKey("target_num")){
						
						if(null != jsonObj.get("target_num").toString() &&!"".equals(jsonObj.get("target_num").toString())){
							
							dateMap.put("target_num", Double.parseDouble(jsonObj.get("target_num").toString()));
						}else{
							dateMap.put("target_num",0);
						}
						
					}else{
						
						dateMap.put("target_num",0);
						
					}
					
					dataList.add(dateMap);
					
				}
				
			}
			
		}
		
		try {
			
			if(dataList.size() > 0){
				
			      
				  accTargetMapper.deleteBatchAccTargetData(dataList);
				  
			      accTargetMapper.addBatchAccTargetData(dataList);
			
			}
			return "{\"msg\":\"保存成功.\",\"state\":\"true\"}";
			
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		    //return "{\"error\":\"保存失败 数据库异常 请联系管理员! 错误编码  saveBatchAccTargetData\"}";
		}

	}
	
	
	@Override
	public String inheritAccTargetData(Map<String, Object> entityMap)
			throws DataAccessException {

		
		try {
      
				  accTargetMapper.inheritDeleteAccTargetData(entityMap);
				  
			      accTargetMapper.inheritAddAccTargetData(entityMap);
			
			
			return "{\"msg\":\"保存成功.\",\"state\":\"true\"}";
			
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		    //return "{\"error\":\"保存失败 数据库异常 请联系管理员! 错误编码  inheritAccTargetData\"}";
		}

	}
	
	
	
	@Override
	public String addImportAccTargetData(List<Map<String, Object>> list)
			throws DataAccessException {
		// TODO Auto-generated method stub
		try {
			accTargetMapper.deleteImportAccTargetData(list);
			accTargetMapper.addImportAccTargetData(list);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";
			
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
			//return "{\"error\":\"添加成功 数据库异常 请联系管理员! 错误编码  addBatchAccTarget\"}";
		}
	}
	
}
