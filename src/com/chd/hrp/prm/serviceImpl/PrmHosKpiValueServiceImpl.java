
/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 package com.chd.hrp.prm.serviceImpl;

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
import com.chd.base.SessionManager;
import com.chd.base.SysPage;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.base.util.SpreadTableJSUtil;
import com.chd.hrp.prm.dao.PrmGoalMapper;
import com.chd.hrp.prm.dao.PrmHosKpiValueMapper;
import com.chd.hrp.prm.dao.PrmHosSchemeMapper;
import com.chd.hrp.prm.dao.PrmKpiLibMapper;
import com.chd.hrp.prm.entity.PrmGoal;
import com.chd.hrp.prm.entity.PrmHosKpiValue;
import com.chd.hrp.prm.entity.PrmHosScheme;
import com.chd.hrp.prm.entity.PrmKpiLib;
import com.chd.hrp.prm.service.PrmHosKpiValueService;
import com.chd.hrp.sys.dao.InfoMapper;
import com.chd.hrp.sys.entity.HosDict;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Description:
 * 0208 医院KPI指标数据准备表
 * @Table:
 * PRM_HOS_KPI_VALUE
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 


@Service("prmHosKpiValueService")
public class PrmHosKpiValueServiceImpl implements PrmHosKpiValueService {

	private static Logger logger = Logger.getLogger(PrmHosKpiValueServiceImpl.class);
	//引入DAO操作
	@Resource(name = "prmHosKpiValueMapper")
	private final PrmHosKpiValueMapper prmHosKpiValueMapper = null;
    
	//引入DAO操作
	@Resource(name = "prmHosSchemeMapper")
	private final PrmHosSchemeMapper prmHosSchemeMapper = null;
	
	//引入DAO操作
	@Resource(name = "infoMapper")
	private final InfoMapper infoMapper = null;
	
	//引入DAO操作
	@Resource(name = "prmGoalMapper")
	private final PrmGoalMapper prmGoalMapper = null;
	
	//引入DAO操作
	@Resource(name = "prmKpiLibMapper")
	private final PrmKpiLibMapper prmKpiLibMapper = null;
	/**
	 * @Description 
	 * 添加0208 医院KPI指标数据准备表<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addPrmHosKpiValue(Map<String,Object> entityMap)throws DataAccessException{
		
		//获取对象0208 医院KPI指标数据准备表
		PrmHosKpiValue prmHosKpiValue = queryPrmHosKpiValueByCode(entityMap);

		if (prmHosKpiValue != null) {

			return "{\"error\":\"数据重复,请重新添加.\"}";

		}
		
		try {
			
			int state = prmHosKpiValueMapper.addPrmHosKpiValue(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addPrmHosKpiValue\"}";

		}
		
	}
	/**
	 * @Description 
	 * 批量添加0208 医院KPI指标数据准备表<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addBatchPrmHosKpiValue(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
			prmHosKpiValueMapper.addBatchPrmHosKpiValue(entityList);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addBatchPrmHosKpiValue\"}";

		}
		
	}
	
		/**
	 * @Description 
	 * 更新0208 医院KPI指标数据准备表<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updatePrmHosKpiValue(Map<String,Object> entityMap)throws DataAccessException{
		
		try {
			
		  int state = prmHosKpiValueMapper.updatePrmHosKpiValue(entityMap);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 updatePrmHosKpiValue\"}";

		}	
		
	}
	/**
	 * @Description 
	 * 批量更新0208 医院KPI指标数据准备表<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateBatchPrmHosKpiValue(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
		  prmHosKpiValueMapper.updateBatchPrmHosKpiValue(entityList);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 updateBatchPrmHosKpiValue\"}";

		}	
		
	}
	/**
	 * @Description 
	 * 删除0208 医院KPI指标数据准备表<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
    public String deletePrmHosKpiValue(Map<String, Object> entityMap) throws DataAccessException {
    	
    try {
			
			int state = prmHosKpiValueMapper.deletePrmHosKpiValue(entityMap);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 deletePrmHosKpiValue\"}";

		}	
		
  }
    
	/**
	 * @Description 
	 * 批量删除0208 医院KPI指标数据准备表<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String deleteBatchPrmHosKpiValue(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
			prmHosKpiValueMapper.deleteBatchPrmHosKpiValue(entityList);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 deleteBatchPrmHosKpiValue\"}";

		}	
	}
	
	/**
	 * @Description 
	 * 查询结果集0208 医院KPI指标数据准备表<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String queryPrmHosKpiValue(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<PrmHosKpiValue> list = prmHosKpiValueMapper.queryPrmHosKpiValue(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<PrmHosKpiValue> list = prmHosKpiValueMapper.queryPrmHosKpiValue(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
		
	}
	
	/**
	 * @Description 
	 * 获取对象0208 医院KPI指标数据准备表<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public PrmHosKpiValue queryPrmHosKpiValueByCode(Map<String,Object> entityMap)throws DataAccessException{
		return prmHosKpiValueMapper.queryPrmHosKpiValueByCode(entityMap);
	}
	@Override
	public String queryPrmHosKpiValueScheme(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
	SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		

		
		if (sysPage.getTotal()==-1){
			
			List<PrmHosKpiValue> list = prmHosKpiValueMapper.queryPrmHosKpiValueScheme(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<PrmHosKpiValue> list = prmHosKpiValueMapper.queryPrmHosKpiValueScheme(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
	}
	@Override
	public String createPrmHosTargetData(Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		
		int state = 0;
		
		entityMap.put("sql", "method_code in ('01')");
	   
		
		prmHosKpiValueMapper.cleanPrmHosKpiValue(entityMap);
		
		List<PrmHosScheme> PrmHosScheme = prmHosSchemeMapper.queryPrmHosScheme(entityMap);
		
		Map<String, List<PrmHosScheme>> PrmHosSchemetMapVo = new HashMap<String,  List<PrmHosScheme>>();
		
		PrmHosSchemetMapVo.put("PrmHosScheme", PrmHosScheme);
		
		
		   for (Iterator<String> iterator = PrmHosSchemetMapVo.keySet().iterator(); iterator.hasNext();) {
     			
	   		      String key = iterator.next();
	   		      
	   		      List<PrmHosScheme> l = PrmHosSchemetMapVo.get(key);
	   	
	   	
	   		      for (int i = 0; i < l.size(); i++) {
	   		    	
	   		    	  
	   		    	  entityMap.put("goal_code", l.get(i).getGoal_code());
	   		    	  entityMap.put("kpi_code", l.get(i).getKpi_code());
	   		    	  entityMap.put("check_hos_id", l.get(i).getCheck_hos_id());
	   		    	  entityMap.put("kpi_value", 0);
	   		    	  entityMap.put("is_audit", 0);
	   		    	  entityMap.put("user_code", "");
	   		    	  entityMap.put("audit_date", "");
	   		   	      state = prmHosKpiValueMapper.addPrmHosKpiValue(entityMap);

	   		    }
	        		
	         }
		
		   
		   if(state > 0){
			   return "{\"msg\":\"KPI指标数据生成成功.\",\"state\":\"true\"}";
		   }else{
			   return "{\"error\":\"KPI指标数据生成失败\"}";

		   }
		
	}
	@Override
	public String auditPrmHosKpiValue(Map<String, Object> entityMap) throws DataAccessException {
		
		try {
			
			if(entityMap.get("checkedRows") == null){//未选择数据、按年月审核
				
				prmHosKpiValueMapper.auditPrmHosKpiValue(entityMap);
				
			}else{//有选择数据、按选择的数据审核
				
				JSONArray checkDataJson = JSONArray.parseArray((String) entityMap.get("checkedRows"));//获取选择的审核数据
				Iterator iterator = checkDataJson.iterator();
				
				while(iterator.hasNext()){
					
					Map<String,Object> mapVo = new HashMap<String,Object>();
					
					JSONObject jsonObj = JSONObject.parseObject(iterator.next().toString());
					
					mapVo.put("group_id", jsonObj.get("group_id"));
					mapVo.put("hos_id", jsonObj.get("hos_id"));
					mapVo.put("copy_code", jsonObj.get("copy_code"));
					mapVo.put("acc_year", jsonObj.get("acc_year"));
					mapVo.put("acc_month", jsonObj.get("acc_month"));
					mapVo.put("check_hos_id", jsonObj.get("check_hos_id"));
					mapVo.put("goal_code", jsonObj.get("goal_code"));
					mapVo.put("kpi_code", jsonObj.get("kpi_code"));
					
					mapVo.put("is_audit", entityMap.get("is_audit"));
					mapVo.put("user_code", entityMap.get("user_code"));
					mapVo.put("audit_date", entityMap.get("audit_date"));
					
					prmHosKpiValueMapper.auditPrmHosKpiValue(mapVo);
				}
			}
			
			return "{\"msg\":\"审核成功.\",\"state\":\"true\"}";
		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException("{\"error\":\"审核失败 数据库异常 请联系管理员!\"}");

		}	
	}
	
	
	@Override
	public String reauditPrmHosKpiValue(Map<String, Object> entityMap)throws DataAccessException {
		// TODO Auto-generated method stub
		
		try {
			
			if(entityMap.get("checkedRows") == null){//未选择数据、按年月审核
				
				prmHosKpiValueMapper.auditPrmHosKpiValue(entityMap);
				
			}else{//有选择数据、按选择的数据审核
				
				JSONArray checkDataJson = JSONArray.parseArray((String) entityMap.get("checkedRows"));//获取选择的审核数据
				Iterator iterator = checkDataJson.iterator();
				
				while(iterator.hasNext()){
					
					Map<String,Object> mapVo = new HashMap<String,Object>();
					
					JSONObject jsonObj = JSONObject.parseObject(iterator.next().toString());
					
					mapVo.put("group_id", jsonObj.get("group_id"));
					mapVo.put("hos_id", jsonObj.get("hos_id"));
					mapVo.put("copy_code", jsonObj.get("copy_code"));
					mapVo.put("acc_year", jsonObj.get("acc_year"));
					mapVo.put("acc_month", jsonObj.get("acc_month"));
					mapVo.put("check_hos_id", jsonObj.get("check_hos_id"));
					mapVo.put("goal_code", jsonObj.get("goal_code"));
					mapVo.put("kpi_code", jsonObj.get("kpi_code"));
					
					mapVo.put("is_audit", 0);
					mapVo.put("user_code","");
					mapVo.put("audit_date","");
					
					prmHosKpiValueMapper.auditPrmHosKpiValue(mapVo);
				}
			}
			
			return "{\"msg\":\"反审核成功.\",\"state\":\"true\"}";
		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException("{\"error\":\"审核失败 数据库异常 请联系管理员!\"}");

		}
	}
	
	
	/**
	 * @Description 
	 * 导入 医院KPI指标数据准备表<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	@Override
	public String importPrmHosKpiValue(Map<String,Object> entityMap)throws DataAccessException {
		
		try {
			
			//1.判断表头是否为空
			String columns=entityMap.get("columns").toString();
			JSONArray jsonColumns = JSONObject.parseArray(columns);	
			if(jsonColumns==null || jsonColumns.size()==0){
				return "{\"error\":\"表头为空！\",\"state\":\"false\"}";
			}
			
			//2.判断数据是否为空
			String content=entityMap.get("content").toString();
			List<Map<String,List<String>>> liData=SpreadTableJSUtil.toListMap(content,1);
			if(liData==null || liData.size()==0){
				return "{\"error\":\"没有数据！\",\"state\":\"false\"}";
			}
			
			Map<String, Object> queryMap =new HashMap<String,Object>();
			queryMap.put("group_id", SessionManager.getGroupId());
			queryMap.put("hos_id", SessionManager.getHosId());
			queryMap.put("copy_code", SessionManager.getCopyCode());
			
			// 3.查询 kpi指标字典 List
			List<PrmKpiLib> kpiList = prmKpiLibMapper.queryPrmKpiLib(queryMap);
			//用于存储查询kpiList中的PrmKpiLib对象,以键值对的形式存储,用于判断指标是否存在
			Map<String, PrmKpiLib> kpiMap = new HashMap<String, PrmKpiLib>();
			//将指标List存入Map   键:Kpi_code 值:PrmKpiLib
			for(PrmKpiLib kpi : kpiList){
				kpiMap.put(kpi.getKpi_code(), kpi);
				kpiMap.put(kpi.getKpi_name(), kpi);
			}
			
			//4.判断表头中指标是否存在
			StringBuffer sb = new StringBuffer();//提示信息:用于存储表头中不存在的指标
			Map<String,String> kpiColumnMap = new HashMap<String,String>();//用于存储表头中的指标,作为遍历数据时取指标值
			
			for(Map<String,List<String>> item : liData ){
				for(Map.Entry<String, List<String>> entry : item.entrySet()){
					String key = entry.getKey();
					if("年度".equals(key) || "月份".equals(key) || "医院编码".equals(key) || "目标编码或目标名称".equals(key)){
						continue;
					}
					
					kpiColumnMap.put(key, key);
					if(kpiMap.get(key) == null){
						sb.append("指标" + key + "不存在,");
					}
				}
				break;//判断指标表头是否存在,只遍历一次
			}
			
			if(kpiColumnMap == null || kpiColumnMap.size() == 0){
				return "{\"error\":\"表头中未存在指标或未填写任何指标\",\"state\":\"false\"}";
			}
			
			//表头含有不存在指标 返回提示
			if(sb.length() > 0){
				return "{\"error\":\"" + sb.deleteCharAt(sb.length() -1).toString()+ "\",\"state\":\"false\"}";
			}
			
			
			List<PrmHosKpiValue> phkvList = prmHosKpiValueMapper.queryPrmHosKpiValue(queryMap);
			//5.以年、月、医院id、目标编码、指标编码作为键,phkv对象作为值,判断数据是否存在
			Map<String,PrmHosKpiValue> phkvMap = new HashMap<String,PrmHosKpiValue>();
			for(PrmHosKpiValue phkv : phkvList){
				
				String key = String.valueOf(phkv.getAcc_year()) +
						String.valueOf(phkv.getAcc_month()) + 
						String.valueOf(phkv.getCheck_hos_id()) + 
						String.valueOf(phkv.getGoal_code()) +
						String.valueOf(phkv.getKpi_code()) ;
				
				phkvMap.put(key, phkv);
			}
			
			queryMap.put("is_stop", "0");
			//查询所有医院
			List<HosDict> hosDictList = prmHosKpiValueMapper.queryHosInfoByGroupId(queryMap);
			//用于存储查询hosDictList中的HosDict对象,以键值对的形式存储,用于判断医院编码是否存在
			Map<String,HosDict> hosDictMap = new HashMap<String,HosDict>();
			//将科室List存入Map   键:dept_name 值:AphiDeptDict
			for(HosDict hosDict : hosDictList){
				hosDictMap.put(hosDict.getHos_code(), hosDict);
			}
			
			//查询当前会计年度目标
			queryMap.put("acc_year", SessionManager.getAcctYear());
			List<PrmGoal> goalList = prmGoalMapper.queryPrmGoalHos(queryMap);
			Map<String,PrmGoal> goalMap = new HashMap<String,PrmGoal>();
			for(PrmGoal prmGoal : goalList){
				goalMap.put(prmGoal.getGoal_code(), prmGoal);
				goalMap.put(prmGoal.getGoal_name(), prmGoal);
			}
			
			
			//6.组装数据
			
			//用于存储传的数据值,判断数据是否重复
			Map<String,String> exitMap = new HashMap<String,String>();
			//存储添加数据List
			List<Map<String,Object>> addList = new ArrayList<Map<String,Object>>();
			
			//存储修改数据List
			List<Map<String,Object>> updateList = new ArrayList<Map<String,Object>>();
			//用于记录重复数据
			StringBuffer err_sb = new StringBuffer();
			
			for(Map.Entry<String, String> entry:kpiColumnMap.entrySet()){//遍历指标
				
				//遍历导入数据
				for(Map<String,List<String>> item : liData ){
					
					List<String> acc_year = item.get("年度") ;
					List<String> acc_month = item.get("月份") ;
					List<String> hos_code = item.get("医院编码") ;
					List<String> goal = item.get("目标编码或目标名称") ;
					List<String> kpi_code = item.get(entry.getKey()) ;//指标
					
					
					if(acc_year.get(1) == null){
						return "{\"warn\":\"年度为空！\",\"state\":\"false\",\"row_cell\":" +acc_year.get(0) +"\"\"}";
					}
					
					if(acc_month.get(1) == null){
						return "{\"warn\":\"月份为空！\",\"state\":\"false\",\"row_cell\":\"" + acc_month.get(0) +"\"}";
					}
					
					if(hos_code.get(1) == null){
						return "{\"warn\":\"医院编码为空！\",\"state\":\"false\",\"row_cell\":\""+hos_code.get(0)+"\"}";
					}else{
						if(hosDictMap.get(hos_code.get(1)) == null /*&& deptHipMap.get(dept_name.get(1)) == null && deptDictMap.get(dept_name.get(1)) == null*/){
							return "{\"warn\":\"" + hos_code.get(1) + ",医院编码不存在！\",\"state\":\"false\",\"row_cell\":\"" + hos_code.get(0) + "\"}";
						}
					}
					
					if(goal.get(1) == null){
						return "{\"warn\":\"目标为空！\",\"state\":\"false\",\"row_cell\":\""+goal.get(0)+"\"}";
					}else{
						if(goalMap.get(goal.get(1)) == null ){
							return "{\"warn\":\"" + goal.get(1) + ",目标不存在！\",\"state\":\"false\",\"row_cell\":\"" + goal.get(0) + "\"}";
						}
					}
					
					if(kpi_code.get(1) == null){
						return "{\"warn\":\"指标值为空！\",\"state\":\"false\",\"row_cell\":\" " + kpi_code.get(0) + "\"}";
					}else{
						 try{
							 Double.parseDouble((kpi_code.get(1)));//校验是否为数值
						 }catch(NumberFormatException e){
							 return "{\"warn\":\"" + kpi_code.get(1) + ",指标值输入不合法,只能输入数字！\",\"state\":\"false\",\"row_cell\":\"" + kpi_code.get(0) + "\"}";
						 }
					}
					
					//以年度|月份|目标|指标|医院编码为键值,判断导入数据是否重复
					String key = acc_year.get(1) + "|" +acc_month.get(1) + "|" + goal.get(1) + "|" + kpiMap.get(entry.getKey()).getKpi_code() + "|" + hos_code.get(1);
					if(exitMap.get(key) != null ){
						err_sb.append(acc_year.get(1)+"年度," + acc_month.get(1)+"月份," + hos_code.get(1)+ "医院编码," + goal.get(1)+ "目标," + entry.getKey()+"指标<br/>");
					}else{
						exitMap.put(key, key);
					}
					
					
					//添加数据Map
					Map<String,Object> addMap = new HashMap<String,Object>();
					addMap.put("group_id", SessionManager.getGroupId());
					addMap.put("hos_id", SessionManager.getHosId());
					addMap.put("copy_code", SessionManager.getCopyCode());
					addMap.put("acc_year", acc_year.get(1));
					addMap.put("acc_month", acc_month.get(1));
					addMap.put("goal_code",goalMap.get(goal.get(1)).getGoal_code());
					addMap.put("kpi_code",kpiMap.get(entry.getKey()).getKpi_code());
					
					//考核医院
					if(hosDictMap.get(hos_code.get(1)) != null){
						addMap.put("check_hos_id",hosDictMap.get(hos_code.get(1)).getHos_id());
					}
					
					addMap.put("kpi_value", kpi_code.get(1));
					addMap.put("is_audit", 0);
					addMap.put("user_code", "");
					addMap.put("audit_date","");
					
					//根据年+月+科室id+科室变更no+指标编码 作为键 判断数据库中是否存在数据
					String is_exit_key = 
							String.valueOf(addMap.get("acc_year")) +
							String.valueOf(addMap.get("acc_month")) +
							String.valueOf(addMap.get("check_hos_id")) +
							String.valueOf(addMap.get("goal_code")) +
							String.valueOf(addMap.get("kpi_code")) ;
					
					
					PrmHosKpiValue phkv = phkvMap.get(is_exit_key);
					if(phkv == null){//不存在,添加
						
						addList.add(addMap);
					}else{
						
						if( !"1".equals(String.valueOf(phkv.getIs_audit())) ){//存在,如果未审核,添加到修改List
							
							updateList.add(addMap);
						}
					}
				}
			}
			
			if( err_sb.length() > 0){//重复数据是否存在
				 return "{\"warn\":\"以下数据【" +err_sb.toString() + "】数据重复！\",\"state\":\"false\"}";
			}
			
			if(addList.size() > 0){
				prmHosKpiValueMapper.addBatchPrmHosKpiValue(addList);
			}
			
			if(updateList.size() > 0){
				prmHosKpiValueMapper.updateBatchPrmHosKpiValue(updateList);
			}
			
			return "{\"msg\":\"导入成功.\",\"state\":\"true\"}";
			
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e.getMessage(), e);
			
			throw new SysException("{\"error\":\"导入失败.\"}");
		}
	}
}
