/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.ass.serviceImpl.repair;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.SessionManager;
import com.chd.base.SysPage;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.hrp.ass.dao.repair.AssRepTeamDeptMapMapper;
import com.chd.hrp.ass.dao.repair.AssRepairTeamDictMapper;
import com.chd.hrp.ass.entity.repair.AssRepairTeamDict;
import com.chd.hrp.ass.service.repair.AssRepairTeamDictService;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Description:
 * tabledesc
 * @Table:
 * ASS_REPAIR_TEAM_DICT
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 


@Service("assRepairTeamDictService")
public class AssRepairTeamDictServiceImpl implements AssRepairTeamDictService {

	private static Logger logger = Logger.getLogger(AssRepairTeamDictServiceImpl.class);
	//引入DAO操作  主表
	@Resource(name = "assRepairTeamDictMapper")
	private final AssRepairTeamDictMapper assRepairTeamDictMapper = null;
	
	//引入DAO操作   维修班组科室对应关系表
	@Resource(name = "assRepTeamDeptMapMapper")
	private final AssRepTeamDeptMapMapper assRepTeamDeptMapMapper = null;
    
	/**
	 * @Description 
	 * 添加tabledesc<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String add(Map<String,Object> entityMap)throws DataAccessException{
		
		//获取对象tabledesc
		AssRepairTeamDict assRepairTeamDict = queryByCode(entityMap);

		if (assRepairTeamDict != null) {

			return "{\"error\":\"数据重复,请重新添加.\"}";

		}
		
		try {
			
			int state = assRepairTeamDictMapper.add(entityMap);
			List<Map> detail = ChdJson.fromJsonAsList(Map.class, entityMap.get("ParamVo").toString());
			for (Map map : detail) {
				entityMap.putAll(map);
				assRepTeamDeptMapMapper.add(entityMap);
			}
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 add\"}";

		}
		
	}
	/**
	 * @Description 
	 * 批量添加tabledesc<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addBatch(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
			assRepairTeamDictMapper.addBatch(entityList);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addBatch\"}";

		}
		
	}
	
		/**
	 * @Description 
	 * 更新tabledesc<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String update(Map<String,Object> entityMap)throws DataAccessException{
		
		try {
			
		  int state = assRepairTeamDictMapper.update(entityMap);
		  assRepTeamDeptMapMapper.delete(entityMap);
		  List<Map> detail = ChdJson.fromJsonAsList(Map.class, entityMap.get("ParamVo").toString());
			for (Map map : detail) {
				entityMap.putAll(map);
				assRepTeamDeptMapMapper.add(entityMap);
			}
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 update\"}";

		}	
		
	}
	/**
	 * @Description 
	 * 批量更新tabledesc<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateBatch(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
		  assRepairTeamDictMapper.updateBatch(entityList);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 updateBatch\"}";

		}	
		
	}
	/**
	 * @Description 
	 * 删除tabledesc<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
    public String delete(Map<String, Object> entityMap) throws DataAccessException {
    	
    try {
			
			int state = assRepairTeamDictMapper.delete(entityMap);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 delete\"}";

		}	
		
  }
    
	/**
	 * @Description 
	 * 批量删除tabledesc<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String deleteBatch(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			StringBuilder rep_team_codes = new StringBuilder();
			for (Map<String, Object> map : entityList) {
				rep_team_codes.append("'"+map.get("rep_team_code").toString()+"',");
			}
			String rep_team_code  =rep_team_codes.substring(0, rep_team_codes.length()-1);
			Map<String,Object> mapVo = new HashMap<String, Object>();
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());
			mapVo.put("rep_team_code", rep_team_code);
			
			 List<String> listVo=assRepairTeamDictMapper.queryRepUserByTeamCode(mapVo);
			 if(null!=listVo&&0!=listVo.size()){
				 StringBuilder user_ids = new StringBuilder();
				 for (String string : listVo) {
					 
					 user_ids.append("'"+string+"',");
				 }
				 String user_id  =user_ids.substring(0, user_ids.length()-1);
				 mapVo.put("user_id", user_id);
				 assRepairTeamDictMapper.deleteInUseRepUserByUserid(mapVo);
			 }
			assRepairTeamDictMapper.deleteInUseRepTeamByTeamCode(mapVo);
			 
			assRepTeamDeptMapMapper.deleteBatch(entityList);
			
			assRepairTeamDictMapper.deleteBatch(entityList);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 deleteBatch\"}";

		}	
	}
	 
	/**
	 * @Description 
	 * 添加tabledesc<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addOrUpdate(Map<String,Object> entityMap)throws DataAccessException{
		/**
		* 备注 先判断是否存在 存在即更新不存在则添加<br>
		* 在判断是否存在时可根据实际情况进行修改传递的参数进行判断<br>
		* 判断是否名称相同 判断是否 编码相同 等一系列规则
		*/
		//判断是否存在对象tabledesc
		Map<String, Object> mapVo=new HashMap<String, Object>();
		mapVo.put("group_id",entityMap.get("group_id"));
		mapVo.put("hos_id",entityMap.get("hos_id"));
    	mapVo.put("copy_code", entityMap.get("copy_code"));
    	mapVo.put("acct_year", entityMap.get("acct_year"));
		
		List<AssRepairTeamDict> list = (List<AssRepairTeamDict>)assRepairTeamDictMapper.queryExists(mapVo);
		
		if (list.size()>0) {

			int state = assRepairTeamDictMapper.update(entityMap);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		
		try {
			
			int state = assRepairTeamDictMapper.add(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addOrUpdate\"}";

		}
		
	}
	/**
	 * @Description 
	 * 查询结果集tabledesc<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String query(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<AssRepairTeamDict> list = (List<AssRepairTeamDict>)assRepairTeamDictMapper.query(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<AssRepairTeamDict> list = (List<AssRepairTeamDict>)assRepairTeamDictMapper.query(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
		
	}
	
	/**
	 * @Description 
	 * 获取对象tabledesc<BR> 
	 * @param  entityMap<BR>
	 *  参数为主键
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public <T> T queryByCode(Map<String,Object> entityMap)throws DataAccessException{
		return assRepairTeamDictMapper.queryByCode(entityMap);
	}
	
	/**
	 * @Description 
	 * 获取tabledesc<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return AssRepairTeamDict
	 * @throws DataAccessException
	*/
	@Override
	public <T> T queryByUniqueness(Map<String,Object> entityMap)throws DataAccessException{
		return assRepairTeamDictMapper.queryByUniqueness(entityMap);
	}
	
	/**
	 * @Description 
	 * 获取tabledesc<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return List<AssRepairTeamDict>
	 * @throws DataAccessException
	*/
	@Override
	public List<?> queryExists(Map<String,Object> entityMap)throws DataAccessException{
		return assRepairTeamDictMapper.queryExists(entityMap);
	}
	@Override
	public String queryAssRepTeamDeptMapByTeamCode(Map<String, Object> mapVo) {
		List<Map<String,Object>> list = assRepTeamDeptMapMapper.queryAssRepTeamDeptMapByTeamCode(mapVo);
		return ChdJson.toJson(list);
	}
	@Override
	public String queryRepUser(Map<String, Object> mapVo) {
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) mapVo.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<Map<String,Object>> list = assRepairTeamDictMapper.queryRepUser(mapVo);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<Map<String,Object>> list = assRepairTeamDictMapper.queryRepUser(mapVo, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJsonLower(list, page.getTotal());
			
		}
		
	}
	@Override
	public String querySysUserNotExists(Map<String, Object> mapVo) {
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) mapVo.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<Map<String,Object>> list = assRepairTeamDictMapper.querySysUserNotExists(mapVo);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<Map<String,Object>> list = assRepairTeamDictMapper.querySysUserNotExists(mapVo, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJsonLower(list, page.getTotal());
			
		}
		
	}
	@Override
	public String addRepairUser(List<Map<String, Object>> listVo) {
		
		for (Map<String, Object> map : listVo) {
			
			assRepairTeamDictMapper.addRepairUser(map);
		}
		return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";
	}
	@Override
	public String deleteAssRepairUser(List<String> listVo) {
		
		Map<String,Object> mapVo = new HashMap<String, Object>();
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		//删除用户时 先删除  用户与故障分类、用户与卡片、用户与资产分类   三张对应关系表  然后删除用户排班表 
		StringBuilder user_ids = new StringBuilder();
		for (String string : listVo) {
			
			user_ids.append("'"+string+"',");
		}
		String user_id  =user_ids.substring(0, user_ids.length()-1);
		mapVo.put("user_id", user_id);
		assRepairTeamDictMapper.deleteInUseRepUserByUserid(mapVo);
		 
		//删除用户
		assRepairTeamDictMapper.deleteAssRepairUser(mapVo ,listVo);
		return "{\"msg\":\"成功.\",\"state\":\"true\"}";
	}
	@Override
	public Map<String, Object> queryUserById(Map<String, Object> mapVo) {
		 
		return assRepairTeamDictMapper.queryUserById(mapVo);
	}
	
	
	@Override
	public String updateAssRepairUser(Map<String, Object> mapVo) {
		try {
			assRepairTeamDictMapper.updateAssRepairUser(mapVo);
			
			return  "{\"msg\":\"成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			// TODO Auto-generated catch block
			throw new SysException(e);
		}
	}
	
}
