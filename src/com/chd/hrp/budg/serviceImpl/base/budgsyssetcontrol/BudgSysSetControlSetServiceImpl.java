package com.chd.hrp.budg.serviceImpl.base.budgsyssetcontrol;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.nutz.json.Json;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.chd.base.SessionManager;
import com.chd.base.SqlService;
import com.chd.base.SysPage;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.hrp.budg.dao.base.budgsyssetcontrolSet.BudgSysSetControlSetMapper;
import com.chd.hrp.budg.entity.BudgAccSubjShip;
import com.chd.hrp.budg.entity.BudgCDet;
import com.chd.hrp.budg.entity.BudgSysSetControlSet;
import com.chd.hrp.budg.service.base.budgsyssetcontrol.BudgSysSetControlSetService;
import com.github.pagehelper.PageInfo;




@Service("budgSysSetControlSetService")
public class BudgSysSetControlSetServiceImpl  implements BudgSysSetControlSetService{
	private static Logger logger = Logger.getLogger(BudgSysSetControlSetServiceImpl.class);
	//引入DAO操作
	@Resource(name = "budgSysSetControlSetMapper")
	private final BudgSysSetControlSetMapper budgSysSetControlSetMapper = null;
	
	
	
	
	
	@Override
	public String addBudgSysSetControl(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String initBudgSysSetControl(Map<String, Object> entityMap)
			throws DataAccessException {
		try {
		/*生成所选预算年度和所选方案的初始方案,若已有数据，确认后再更新数据。
		
		
		 */
		/**
		 * 从BUDG_C_P_L中取方案对应的环节link_Code和模块MOD_CODE，IS_STOP=0；
		 */
		List<Map<String, Object>>  listVo= new ArrayList<Map<String, Object>>();
		List<Map<String, Object>>  linkCode= budgSysSetControlSetMapper.queryLinkcode(entityMap);
		if(linkCode.size()>0){
			
			for (Map<String, Object> map : linkCode) {
				Map<String, Object> linkMap=new HashMap<String, Object>();
				linkMap.put("link_code", map.get("LINK_CODE"));
				linkMap.put("mod_code", map.get("MOD_CODE"));
				linkMap.put("group_id", SessionManager.getGroupId());   
				linkMap.put("hos_id", SessionManager.getHosId());   
				linkMap.put("copy_code", SessionManager.getCopyCode());   
				linkMap.put("budg_year", entityMap.get("budg_year"));   
				linkMap.put("plan_code", entityMap.get("plan_code")); 
				linkMap.put("use_nature", 0); 
				linkMap.put("re_link", ""); 
				linkMap.put("cont_w", "01"); 
				linkMap.put("is_start", "0"); 
				listVo.add(linkMap);
				
			}
		}
		
		/**
		 * 	从BUDG_C_TAB中取方案对应的默认预算表tab_code,IS_STOP=0，IS_DEF=1;
		 */
		List<Map<String, Object>>  listVo1= new ArrayList<Map<String, Object>>();
		List<Map<String, Object>>  tabCode= budgSysSetControlSetMapper.queryTabCode(entityMap);
			if(tabCode.size()>0){
						
						for (Map<String, Object> map : tabCode) {
							for (Map<String, Object> map1 : listVo) {
								map1.put("tab_code", map.get("TAB_CODE"));
								listVo1.add(map1);
							}
						
							
						}
					}
		/**
		 * 从BUDG_C_TAB_W中取预算表对应的默认控制模式CONT_M 控制层次CONT_L控制期间 CONT_P,IS_STOP=0，IS_DEF=1;
		 */
			List<Map<String, Object>>  listVo2= new ArrayList<Map<String, Object>>();
	
		for (Map<String, Object> map1 : listVo) {
			entityMap.put("tab_code", map1.get("tab_code"));   
				
			  
			List<Map<String, Object>>  cont= budgSysSetControlSetMapper.queryCont(entityMap);
			if(cont.size()>0){
						
						for (Map<String, Object> map : cont) {

							
								map1.put("cont_m", map.get("CONT_M"));
								map1.put("cont_l", map.get("CONT_L"));
								map1.put("cont_p", map.get("CONT_P"));
								
								listVo2.add(map1);
							}
							
						}
					}
			/**
			 * 从BUDG_C_NOTE中取该环节对应的默认控制节点CONT_NOTE，IS_STOP=0，IS_DEF=1;
			 * 
			 */
			List<Map<String, Object>>  listVo3= new ArrayList<Map<String, Object>>();
			for (Map<String, Object> map1 : listVo2) {
				entityMap.put("mod_code", map1.get("mod_code"));
				entityMap.put("link_code", map1.get("link_code"));

			List<Map<String, Object>>  linkCod= budgSysSetControlSetMapper.querylinkCod(entityMap);
			if(linkCod.size()>0){
						
						for (Map<String, Object> map : linkCod) {
							
								map1.put("cont_note", map.get("NOTE_CODE"));
								
								listVo3.add(map1);
							}
							
						
							
						}
					}
		/**
		 * 从BUDG_C_STATE中取该环节对应的默认占用状态USE_STATE，可能存在多个，IS_STOP=0，IS_DEF=1;
		 */
			List<Map<String, Object>>  listVo4= new ArrayList<Map<String, Object>>();
		
			
				for (Map<String, Object> map1 : listVo3) {
					StringBuffer s= new StringBuffer();
					entityMap.put("mod_code", map1.get("mod_code"));
					entityMap.put("link_code", map1.get("link_code"));
		List<Map<String, Object>>  useState= budgSysSetControlSetMapper.queryUseState(entityMap);
		if(useState.size()>0){
					
					for (Map<String, Object> map : useState) {
					s.append(map.get("STATE_CODE")+",");
						
					}
				}
		
		if(s.length()>0){

			s.deleteCharAt(s.length()-1);
		

				
				map1.put("use_state", s.toString());
				listVo4.add(map1);
			}
		}
		
		
		
		
		
		
		
		//获取对象
		BudgSysSetControlSet budgAccSubjShip = budgSysSetControlSetMapper.queryByCodeSysSetControlSet(entityMap);
     if(listVo4.size()>0){
    	 if (budgAccSubjShip != null ) {

 			budgSysSetControlSetMapper.updateBatch(listVo4);

 		}else{
 			budgSysSetControlSetMapper.addBatch(listVo4);
 		}
     }
		
		
		
		
		
		
		
		return "{\"msg\":\"保存成功.\",\"state\":\"true\"}";
		}catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"保存失败.\",\"state\":\"false\"}");
		}
	}

	@Override
	public String extendBudgSysSetControl(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String checkBudgSysSetControl(Map<String, Object> entityMap)
			throws DataAccessException {
	  try{
		if(!entityMap.get("cont_m").equals("1")){
			int conut =	budgSysSetControlSetMapper.queryBudgDetCheck(entityMap);
			if(conut==0){
				
				return "{\"error\":\"启用失败,明细控制方式没有明细数据!\"}";
			}
			
		}
		entityMap.put("blink_code", entityMap.get("re_link"));
		 //查询A环节使用预算方案1进行控制时，选择关联环节B，则B环节需要采用预算方案1进行控制，且所选预算表、控制模式、控制层次及控制期间要与A环节一致。
		int exict=budgSysSetControlSetMapper.queryCheckExict(entityMap);
		if(exict==0){
			return "{\"error\":\"启用失败,关联环节所选预算表、控制模式、控制层次及控制期间与要启用的方案不一致!\"}";
		}else{
			entityMap.put("is_start", "1");
			//启用
			budgSysSetControlSetMapper.checkBudgSysSetControl(entityMap);
		}
		
		
		return "{\"msg\":\"启用成功!\"}";
	  }catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException("{\"error\":\"更新失败.\",\"state\":\"false\"}");

		}
	}

	@Override
	public String updateBudgSysSetControl(Map<String, Object> entityMap)
			throws DataAccessException {
		Map<String, Object> commonMap = new HashMap<String, Object>();

		
		try {
			JSONArray json = JSONArray.parseArray(String.valueOf(entityMap.get("allData")));
			Iterator iterator = json.iterator();
			JSONObject jsonObj = null;
			while (iterator.hasNext()) {
				
				jsonObj = JSONObject.parseObject(iterator.next().toString());

				String use_state1=jsonObj.get("use_state").toString();
				String re_link1=jsonObj.get("re_link").toString();
   
				
				commonMap.put("budg_year", jsonObj.get("budg_year"));
				commonMap.put("cont_l", jsonObj.get("cont_l"));
				commonMap.put("cont_m", jsonObj.get("cont_m"));
				commonMap.put("cont_note", jsonObj.get("cont_note"));
				commonMap.put("cont_p", jsonObj.get("cont_p"));
				commonMap.put("cont_w", jsonObj.get("cont_w"));
				commonMap.put("link_code", jsonObj.get("link_code"));
				commonMap.put("mod_code", jsonObj.get("mod_code"));
				commonMap.put("plan_code", jsonObj.get("plan_code"));
				commonMap.put("re_link", re_link1.replace("[", "").replace("]", "").replace("\"", "").replace("\"", ""));
				commonMap.put("tab_code", jsonObj.get("tab_code"));
				commonMap.put("use_nature", jsonObj.get("use_nature"));
				commonMap.put("use_state", use_state1.replace("[", "").replace("]", "").replace("\"", "").replace("\"", ""));
				
			}
					int state = budgSysSetControlSetMapper.update(commonMap);
					
					return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";
				
			
			

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException("{\"error\":\"更新失败.\",\"state\":\"false\"}");

		}	
	}

	@Override
	public String updateBatchBudgSetControl(Map<String, Object> detailVo)
			throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String updateSetBudgSetControl(Map<String, Object> entityMap)
			throws DataAccessException {
		try {
			
				   int state = budgSysSetControlSetMapper.updateDetBatch(entityMap);
				   
			   
					
					return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";
				
			
			

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException("{\"error\":\"更新失败.\",\"state\":\"false\"}");

		}	
	}

	@Override
	public String updateDetailBudgSysSetControl(Map<String, Object> entityMap)
			throws DataAccessException {
		try {
			//查询明细
			int conut =	budgSysSetControlSetMapper.queryBudgDet(entityMap);
			   if(conut>0){
				   int state = budgSysSetControlSetMapper.updateDet(entityMap);
				   
			   }else{
				   
				   int state = budgSysSetControlSetMapper.addDet(entityMap);
			   }
					
					
					return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";
				
			
			

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException("{\"error\":\"更新失败.\",\"state\":\"false\"}");

		}	
	}


	@Override
	public String queryBudgSysSetControl(Map<String, Object> entityMap)
			throws DataAccessException {
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<BudgSysSetControlSet> list = budgSysSetControlSetMapper.queryBudgSysSetControl(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<BudgSysSetControlSet> list = budgSysSetControlSetMapper.queryBudgSysSetControl(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
		
	}

	@Override
	public BudgSysSetControlSet queryByCodeSysSetControlSet(
			Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		return budgSysSetControlSetMapper.queryByCode(entityMap);
	}

	@Override
	public String updateBudgSysSetControlSet(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String queryBudgCDet(Map<String, Object> entityMap)
			throws DataAccessException {
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<BudgCDet> list = budgSysSetControlSetMapper.queryBudgCDet(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<BudgCDet> list = budgSysSetControlSetMapper.queryBudgCDet(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
		
	}

	@Override
	public String queryBudgModSelect(Map<String, Object> entityMap)
			throws DataAccessException {
		/*   <if test="key != null and key != ''">
					AND (hdd.dept_code like '${key}%' or
						hdd.dept_name like '%${key}%' or
						hdd.spell_code like '${key}%' or
						hdd.wbx_code like '${key}%' )
					</if>*/
		
		Map<String, Object> entityMa= budgSysSetControlSetMapper.queryBudgCplan(entityMap);
		String sql=entityMa.get("ITEM_SQL").toString();
		
		   sql=replaceConstant(sql,entityMap);
		   
		   entityMap.put("sql", sql);
		   
		   
		return JSON.toJSONString(budgSysSetControlSetMapper.queryBudgModSelect(entityMap));
	}
	private String replaceConstant(String replaceStr,Map<String, Object> entityMap){
		
		replaceStr = replaceStr.replaceAll("@group_id", entityMap.get("group_id").toString());
		
		replaceStr = replaceStr.replaceAll("@hos_id", entityMap.get("hos_id").toString());
		
		replaceStr = replaceStr.replaceAll("@copy_code","'" +entityMap.get("copy_code").toString()+"'");
		
		replaceStr = replaceStr.replaceAll("@budg_year", entityMap.get("budg_year").toString());
		if(entityMap.containsKey("item_code")){
			if(!entityMap.get("item_code").equals("")){
				replaceStr = replaceStr.replaceAll("@item", entityMap.get("item_code").toString());
			}else{
				replaceStr = replaceStr.replaceAll("@item", "''");
			}
		
		}else{
			replaceStr = replaceStr.replaceAll("@item", "''");
		}
		/*if(entityMap.get("item_code")!=null||!entityMap.get("item_code").equals("null")){
		
		}*/
		
		return replaceStr;
		
	}

	@Override
	public String queryItemCode(Map<String, Object> entityMap)
			throws DataAccessException {
		entityMap.put("budg_year", SessionManager.getAcctYear());
		Map<String, Object> entityMa= budgSysSetControlSetMapper.queryBudgCplan(entityMap);
		String sql=entityMa.get("ITEM_SQL").toString();
		
		   sql=replaceConstant(sql,entityMap);
		   
		   entityMap.put("sql", sql);
		   List<Map<String, Object>> list=  budgSysSetControlSetMapper.queryBudgModSelect(entityMap);
		   List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		   for (Map<String, Object> map : list) {
			   Map<String, Object> entityM = new HashMap<String, Object>();
			   entityM.put("item_code", map.get("id"));
			   entityM.put("item_name", map.get("text"));
			   listVo.add(entityM);
		}
		   return ChdJson.toJson(listVo);
	}

	@Override
	public String deleteBudgSysSetControl(List<BudgCDet> listVo)
			throws DataAccessException {

		try {

			budgSysSetControlSetMapper.deleteBudgSysSetControl(listVo);

			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException("{\"error\":\"更新失败.\",\"state\":\"false\"}");

		}
	}

	@Override
	public Map<String, Object> queryItemTabName(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		return budgSysSetControlSetMapper.queryItemTabName(entityMap);
	}

	@Override
	public String queryLinkName(Map<String, Object> entityMap)
			throws DataAccessException {
		 List<Map<String, Object>> list=  budgSysSetControlSetMapper.queryLinkName(entityMap);
		return JSON.toJSONString(list);
	}

	@Override
	public String updateSetBudgCopy(Map<String, Object> entityMap)
			throws DataAccessException {
		try {
		
		
			 List<BudgCDet> list=budgSysSetControlSetMapper.queryBudgCDet(entityMap);
		
			 if(list.size()>0){
				 
				 for (BudgCDet budgCDet : list) {
					 Map<String, Object> entity= new HashMap<String, Object>();
						
						 entity.put("group_id", entityMap.get("group_id"));
						 entity.put("hos_id", entityMap.get("hos_id"));
						 entity.put("copy_code", entityMap.get("copy_code"));
						 entity.put("budg_year", entityMap.get("budg_year"));
						 entity.put("plan_code", entityMap.get("plan_code"));
						 entity.put("mod_code", entityMap.get("copymod_code"));
						 entity.put("link_code", entityMap.get("copylink_code"));
						 entity.put("item_code",budgCDet.getItem_code() );
						 entity.put("cont_l",budgCDet.getCont_l() );
						 entity.put("cont_p",budgCDet.getCont_p() );
						 entity.put("cont_w",budgCDet.getCont_w() );
						 entity.put("item_name",budgCDet.getItem_name() );
						 
						 
						//查询明细
							int conut =	budgSysSetControlSetMapper.queryBudgDet(entity);
							   if(conut>0){
								   int state = budgSysSetControlSetMapper.updateDet(entity);
								   
							   }else{
								   
								   int state = budgSysSetControlSetMapper.addDet(entity);
							   }
									
									
								
							
							

						}
					return "{\"msg\":\"保存成功.\",\"state\":\"true\"}";
					
						// budgSysSetControlSetMapper.updateSetBudgCopy(entity);
				
			 }
		
		
			
		
		
		
		
		
		
		
		return "{\"msg\":\"保存成功.\",\"state\":\"true\"}";
		}catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"保存失败.\",\"state\":\"false\"}");
		}
	}

	@Override
	public String uncheckBudgSysSetControl(Map<String, Object> entityMap)
			throws DataAccessException {
		  try{
			
					entityMap.put("is_start", "0");
					//停用
					budgSysSetControlSetMapper.checkBudgSysSetControl(entityMap);
				
				
				
				return "{\"msg\":\"停用成功!\"}";
			  }catch (Exception e) {

					logger.error(e.getMessage(), e);

					throw new SysException("{\"error\":\"更新失败.\",\"state\":\"false\"}");

				}
			}
	
}
