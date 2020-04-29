
/*
 *
 */
 package com.chd.hrp.eqc.serviceImpl.base;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
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
import com.chd.base.SessionManager;
import com.chd.base.SysPage;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.base.util.DateUtil;
import com.chd.hrp.eqc.dao.base.AssEqGroupDetailMapper;
import com.chd.hrp.eqc.dao.base.AssEqGroupMapper;
import com.chd.hrp.eqc.service.base.AssEqGroupService;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Title. 
 * @Description.
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Author: bell
 * @Version: 1.0
 */

/**
* 00机组维护 ASS_EQGroup Service实现类
*/
@Service("assEqGroupService")
public class AssEqGroupServiceImpl implements AssEqGroupService {

	private static Logger logger = Logger.getLogger(AssEqGroupServiceImpl.class);
	//引入DAO操作
	@Resource(name = "assEqGroupMapper")
	private final AssEqGroupMapper assEqGroupMapper = null;
	
	@Resource(name = "assEqGroupDetailMapper")
	private final AssEqGroupDetailMapper assEqGroupDetailMapper = null;
	
	
    
	/**
	 * @Description 
	 * 添加00机组维护 ASS_EQGroup<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String add(Map<String,Object> entityMap)throws DataAccessException{
		
		//获取对象00机组维护 ASS_EQGroup
		Map<String,Object> assEqGroup = queryByCode(entityMap);

		if (assEqGroup != null) {

			return "{\"error\":\"数据重复,请重新添加.\"}";

		}
		
		try {
			
			int state = assEqGroupMapper.add(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException("{\"error\":\"操作失败.\",\"state\":\"false\"}");

		}
		
	}
	/**
	 * @Description 
	 * 批量添加00机组维护 ASS_EQGroup<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addBatch(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
			assEqGroupMapper.addBatch(entityList);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException("{\"error\":\"操作失败.\",\"state\":\"false\"}");

		}
		
	}
	
		/**
	 * @Description 
	 * 更新00机组维护 ASS_EQGroup<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String update(Map<String,Object> entityMap)throws DataAccessException{
		
		try {
			
		  int state = assEqGroupMapper.update(entityMap);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException("{\"error\":\"操作失败.\",\"state\":\"false\"}");

		}	
		
	}
	/**
	 * @Description 
	 * 批量更新00机组维护 ASS_EQGroup<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateBatch(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
		  assEqGroupMapper.updateBatch(entityList);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException("{\"error\":\"操作失败.\",\"state\":\"false\"}");

		}	
		
	}
	/**
	 * @Description 
	 * 删除00机组维护 ASS_EQGroup<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
    public String delete(Map<String, Object> entityMap) throws DataAccessException {
    	
    try {
			
			int state = assEqGroupMapper.delete(entityMap);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException("{\"error\":\"操作失败.\",\"state\":\"false\"}");

		}	
		
  }
    
	/**
	 * @Description 
	 * 批量删除00机组维护 ASS_EQGroup<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String deleteBatch(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
			assEqGroupDetailMapper.deleteBatch(entityList);
			
			assEqGroupMapper.deleteBatch(entityList);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException("{\"error\":\"操作失败.\",\"state\":\"false\"}");

		}	
	}
	
	/**
	 * @Description 
	 * 查询结果集00机组维护 ASS_EQGroup<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String query(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<Map<String,Object>> list = (List<Map<String, Object>>) assEqGroupMapper.query(entityMap);
			
			for(Map<String,Object> item : list ){
				
				List<Map<String,Object>> detailList = (List<Map<String, Object>>) assEqGroupDetailMapper.query(item);
				
				item.put("detailData", JSONObject.parseObject(ChdJson.toJson(detailList)));
			}
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<Map<String,Object>> list = (List<Map<String, Object>>) assEqGroupMapper.query(entityMap, rowBounds);
			
			for(Map<String,Object> item : list ){
				
				List<Map<String,Object>> detailList = (List<Map<String, Object>>) assEqGroupDetailMapper.query(item);
				
				item.put("detailData", JSONObject.parseObject(ChdJson.toJson(detailList)));
			}
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
		
	}
	
	/**
	 * @Description 
	 * 获取对象00机组维护 ASS_EQGroup<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public <T> T queryByCode(Map<String,Object> entityMap)throws DataAccessException{
		return assEqGroupMapper.queryByCode(entityMap);
	}
	@Override
	public String addOrUpdate(Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public <T> T queryByUniqueness(Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public List<?> queryExists(Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public String save(List<Map<String, Object>> listVo) throws DataAccessException {
		List<Map<String, Object>> addList= new ArrayList<Map<String,Object>>();
		
		List<Map<String, Object>> updateList= new ArrayList<Map<String,Object>>();
		
		for(Map<String,Object> item : listVo){
			
			if("1".equals(item.get("flag"))){ //添加
				
				addList.add(item) ;
				
			}else{ //修改
				updateList.add(item) ;
			}
		}
		
		try {
			
			if(addList.size()>0){
				//查询添加数据是否已存在
				String  str = assEqGroupMapper.queryDataExist(addList) ;
				
				if(str == null){
					
					int count = assEqGroupMapper.addBatch(addList);
					
					saveDetail(addList);
					
				}else{
					
					return "{\"message\":\"机组"+str+"数据已存在\",\"state\":\"false\"}";
				}
				
				
			}
			
			if(updateList.size()>0){
				
				int state = assEqGroupMapper.updateBatch(updateList);
				
				saveDetail(updateList);
			}
			
			return "{\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);
			
			throw new SysException("{\"error\":\"保存失败.\",\"state\":\"false\"}");

		}
	}
	
	/**
	 * 保存机组明细数据
	 * @param listVo
	 * @return
	 * @throws DataAccessException
	 * @throws ParseException 
	 */
	public String saveDetail(List<Map<String, Object>> listVo) throws DataAccessException, ParseException {
		List<Map<String, Object>> addList= new ArrayList<Map<String,Object>>();
		
		List<Map<String, Object>> updateList= new ArrayList<Map<String,Object>>();
		
		for(Map<String,Object> item : listVo){
			
			if("1".equals(item.get("flag"))){ //添加
				if (item.get("detailData") != null) {
					JSONObject json = JSONObject.parseObject(item.get("detailData").toString());
					if(json != null){
						JSONArray detailData = JSONArray.parseArray(json.get("Rows").toString());
						if (!detailData.isEmpty()) {
							Iterator itData = detailData.iterator();
							
							while (itData.hasNext()) {
								JSONObject jsonObj = JSONObject.parseObject(itData.next().toString());
								Map<String,Object> detailMap = new HashMap<String,Object>();
								detailMap.put("group_id", SessionManager.getGroupId());
								detailMap.put("hos_id", SessionManager.getHosId());
								detailMap.put("copy_code", SessionManager.getCopyCode());
								detailMap.put("eq_unit_code", item.get("eq_unit_code"));
								detailMap.put("eo_eq_group", jsonObj.get("eo_eq_group"));
								detailMap.put("main_flag", jsonObj.get("main_flag"));
								detailMap.put("income_rate", jsonObj.get("income_rate"));
								detailMap.put("expend_rate", jsonObj.get("expend_rate"));
								detailMap.put("from_date", DateUtil.stringToDate(jsonObj.get("from_date").toString(), "yyyy-MM-dd"));
								if(jsonObj.get("to_date") != null && !"".equals(jsonObj.get("to_date"))){
									detailMap.put("to_date", DateUtil.stringToDate(jsonObj.get("to_date").toString(), "yyyy-MM-dd"));
								}else{
									detailMap.put("to_date", null);
								}
								detailMap.put("update_date", DateUtil.stringToDate(DateUtil.dateFormat1(new Date(),"yyyy-MM-dd"),"yyyy-MM-dd"));
								
								detailMap.put("update_time",DateUtil.stringToDate(DateUtil.dateFormat1(new Date(),"yyyy-MM-dd HH:mm:ss"),"yyyy-MM-dd HH:mm:ss"));
								detailMap.put("update_userdr", SessionManager.getUserId());
								detailMap.put("invalid_flag", 0);
								detailMap.put("remark", null);
								
								addList.add(detailMap);
							}
						}
					}
					
				}
				
			}else{ //修改
				
				if (item.get("detailData") != null) {
					JSONObject json = JSONObject.parseObject(item.get("detailData").toString());
					if(json != null){
						JSONArray detailData = JSONArray.parseArray(json.get("Rows").toString());
						if (!detailData.isEmpty()) {
							Iterator itData = detailData.iterator();
							
							while (itData.hasNext()) {
								JSONObject jsonObj = JSONObject.parseObject(itData.next().toString());
								Map<String,Object> detailMap = new HashMap<String,Object>();
								
								detailMap.put("group_id", SessionManager.getGroupId());
								detailMap.put("hos_id", SessionManager.getHosId());
								detailMap.put("copy_code", SessionManager.getCopyCode());
								detailMap.put("eq_unit_code", item.get("eq_unit_code"));
								detailMap.put("eo_eq_group", jsonObj.get("eo_eq_group"));
								detailMap.put("main_flag", jsonObj.get("main_flag"));
								detailMap.put("income_rate", jsonObj.get("income_rate"));
								detailMap.put("expend_rate", jsonObj.get("expend_rate"));
								detailMap.put("from_date", DateUtil.stringToDate(jsonObj.get("from_date").toString(), "yyyy-MM-dd"));
								if(jsonObj.get("to_date") != null && !"".equals(jsonObj.get("to_date"))){
									detailMap.put("to_date", DateUtil.stringToDate(jsonObj.get("to_date").toString(), "yyyy-MM-dd"));
								}else{
									detailMap.put("to_date", null);
								}
								detailMap.put("update_date", DateUtil.getCurrenDate("yyyy-MM-dd"));
								
								detailMap.put("update_time",DateUtil.getCurrenDate("HH:mm:ss"));
								detailMap.put("update_userdr", SessionManager.getUserId());
								detailMap.put("invalid_flag", 0);
								detailMap.put("remark", null);
								
								updateList.add(detailMap);
							}
						}
					}
				}
			}
		}
		try {
			
			if(addList.size()>0){
					
				int count = assEqGroupDetailMapper.addBatch(addList);
				
			}
			
			if(updateList.size()>0){
				
				int state = assEqGroupDetailMapper.deleteBatch(updateList);
				
				int count = assEqGroupDetailMapper.addBatch(updateList);
			}
			
			return "{\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);
			
			throw new SysException("{\"error\":\"保存失败.\",\"state\":\"false\"}");

		}
	}
}
