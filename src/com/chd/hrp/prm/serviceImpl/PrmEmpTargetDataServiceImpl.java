
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
import com.chd.hrp.prm.dao.PrmEmpDictMapper;
import com.chd.hrp.prm.dao.PrmEmpMapper;
import com.chd.hrp.prm.dao.PrmEmpTargetDataMapper;
import com.chd.hrp.prm.dao.PrmTargetMapper;
import com.chd.hrp.prm.entity.PrmDeptDict;
import com.chd.hrp.prm.entity.PrmDeptTargetData;
import com.chd.hrp.prm.entity.PrmEmp;
import com.chd.hrp.prm.entity.PrmEmpDict;
import com.chd.hrp.prm.entity.PrmEmpTargetData;
import com.chd.hrp.prm.entity.PrmTarget;
import com.chd.hrp.prm.service.PrmEmpTargetDataService;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Description:
 * 0412 职工绩效指标数据表
 * @Table:
 * PRM_EMP_TARGET_DATA
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 


@Service("prmEmpTargetDataService")
public class PrmEmpTargetDataServiceImpl implements PrmEmpTargetDataService {

	private static Logger logger = Logger.getLogger(PrmEmpTargetDataServiceImpl.class);
	//引入DAO操作
	@Resource(name = "prmEmpTargetDataMapper")
	private final PrmEmpTargetDataMapper prmEmpTargetDataMapper = null;
    
	@Resource(name = "prmTargetMapper")
	private final PrmTargetMapper  prmTargetMapper = null;
	
	@Resource(name = "prmEmpMapper")
	private final PrmEmpMapper prmEmpMapper = null;
	
	@Resource(name = "prmEmpDictMapper")
	private final PrmEmpDictMapper prmEmpDictMapper = null;
	
	/**
	 * @Description 
	 * 添加0412 职工绩效指标数据表<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addPrmEmpTargetData(Map<String,Object> entityMap)throws DataAccessException{
		
		//获取对象0412 职工绩效指标数据表
		PrmEmpTargetData prmEmpTargetData = queryPrmEmpTargetDataByCode(entityMap);

		if (prmEmpTargetData != null) {

			return "{\"error\":\"数据重复,请重新添加.\"}";

		}
		
		try {
			
			int state = prmEmpTargetDataMapper.addPrmEmpTargetData(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addPrmEmpTargetData\"}";

		}
		
	}
	/**
	 * @Description 
	 * 批量添加0412 职工绩效指标数据表<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addBatchPrmEmpTargetData(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
			prmEmpTargetDataMapper.addBatchPrmEmpTargetData(entityList);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addBatchPrmEmpTargetData\"}";

		}
		
	}
	
		/**
	 * @Description 
	 * 更新0412 职工绩效指标数据表<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updatePrmEmpTargetData(Map<String,Object> entityMap)throws DataAccessException{
		
		try {
			
		  int state = prmEmpTargetDataMapper.updatePrmEmpTargetData(entityMap);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 updatePrmEmpTargetData\"}";

		}	
		
	}
	/**
	 * @Description 
	 * 批量更新0412 职工绩效指标数据表<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateBatchPrmEmpTargetData(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
		  prmEmpTargetDataMapper.updateBatchPrmEmpTargetData(entityList);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 updateBatchPrmEmpTargetData\"}";

		}	
		
	}
	/**
	 * @Description 
	 * 删除0412 职工绩效指标数据表<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
    public String deletePrmEmpTargetData(Map<String, Object> entityMap) throws DataAccessException {
    	
    try {
			
			int state = prmEmpTargetDataMapper.deletePrmEmpTargetData(entityMap);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 deletePrmEmpTargetData\"}";

		}	
		
  }
    
	/**
	 * @Description 
	 * 批量删除0412 职工绩效指标数据表<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String deleteBatchPrmEmpTargetData(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
			prmEmpTargetDataMapper.deleteBatchPrmEmpTargetData(entityList);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 deleteBatchPrmEmpTargetData\"}";

		}	
	}
	
	/**
	 * @Description 
	 * 查询结果集0412 职工绩效指标数据表<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String queryPrmEmpTargetData(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<PrmEmpTargetData> list = prmEmpTargetDataMapper.queryPrmEmpTargetData(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<PrmEmpTargetData> list = prmEmpTargetDataMapper.queryPrmEmpTargetData(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
		
	}
	
	/**
	 * @Description 
	 * 获取对象0412 职工绩效指标数据表<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public PrmEmpTargetData queryPrmEmpTargetDataByCode(Map<String,Object> entityMap)throws DataAccessException{
		return prmEmpTargetDataMapper.queryPrmEmpTargetDataByCode(entityMap);
	}
	@Override
	public String auditPrmEmpTargetData(Map<String, Object> entityMap) throws DataAccessException {
		
		try {
			
			if(entityMap.get("checkedRows") == null){//未选择数据、按年月审核
				
				prmEmpTargetDataMapper.auditPrmEmpTargetData(entityMap);
				
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
					mapVo.put("emp_id", jsonObj.get("emp_id"));
					mapVo.put("emp_no", jsonObj.get("emp_no"));
					mapVo.put("target_code", jsonObj.get("target_code"));
					
					mapVo.put("is_audit", entityMap.get("is_audit"));
					mapVo.put("user_code", entityMap.get("user_code"));
					mapVo.put("audit_date", entityMap.get("audit_date"));
					
					prmEmpTargetDataMapper.auditPrmEmpTargetData(mapVo);
				}
			}
			
			
			return "{\"msg\":\"审核成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException("{\"error\":\"操作失败 \"}");

		}
	}
	
	@Override
	public String reAuditPrmEmpTargetData(Map<String, Object> entityMap) throws DataAccessException {
		
		try {
			
			if(entityMap.get("checkedRows") == null){//未选择数据、按年月审核
				
				prmEmpTargetDataMapper.auditPrmEmpTargetData(entityMap);
				
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
					mapVo.put("emp_id", jsonObj.get("emp_id"));
					mapVo.put("emp_no", jsonObj.get("emp_no"));
					mapVo.put("target_code", jsonObj.get("target_code"));
					
					mapVo.put("is_audit", 0);
					mapVo.put("user_code","");
					mapVo.put("audit_date","");
					
					prmEmpTargetDataMapper.auditPrmEmpTargetData(mapVo);
				}
			}
			
			
			return "{\"msg\":\"反审核成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException("{\"error\":\"操作失败 \"}");

		}
	}
	
	
	@Override
	public String createPrmEmpTargetData(Map<String, Object> entityMap,
			String paramVo) throws DataAccessException {
		// TODO Auto-generated method stub
		  Map<String, Object> mapVo =  new HashMap<String, Object>();
		   if(mapVo.get("group_id") == null){
			
			mapVo.put("group_id", SessionManager.getGroupId());
			 
			}
			
			if(mapVo.get("hos_id") == null){
				
			mapVo.put("hos_id", SessionManager.getHosId());
			 
			}
			
			if(mapVo.get("copy_code") == null){
				
	        mapVo.put("copy_code", SessionManager.getCopyCode());
	    
			}
			if(mapVo.get("acct_year") == null){
				
	      mapVo.put("acct_year", SessionManager.getAcctYear());
	   
			}
			
			mapVo.put("acc_year", paramVo.substring(0, 4));
		    mapVo.put("acc_month", paramVo.substring(4, 6));
		    
		    
		    prmEmpTargetDataMapper.cleanPrmEmptTargetData(mapVo);
		    
		    int state = 0;
			/*职工*/
			entityMap.put("target_nature", "04");
			
			Map<String, List<PrmTarget>> prmTargetMapVo = new HashMap<String,  List<PrmTarget>>();
			
			/*查询全院手动录入基本指标*/
			List<PrmTarget> prmTarget = prmTargetMapper.queryPrmTargetNatureCreate(entityMap);
			
		 	prmTargetMapVo.put("target_code", prmTarget);  
		 	
		    entityMap.put("is_stop", 0);
		     
		 	List<PrmEmp>  prmEmpDict = prmEmpMapper.queryPrmEmp(entityMap);
		 	
		 	for (PrmEmp PrmEmpDict2:prmEmpDict) {
		 		
			    for (Iterator<String> iterator = prmTargetMapVo.keySet().iterator(); iterator.hasNext();) {
	      			
		   		      String key = iterator.next();
		   		      
		   		      List<PrmTarget> l = prmTargetMapVo.get(key);
		   	
		   		      for (int i = 0; i < l.size(); i++) {
		   		    	
		   		    	mapVo.put("target_code", l.get(i).getTarget_code().toString());
		   		  	    mapVo.put("target_value", 0);
		   		  	    mapVo.put("emp_no", PrmEmpDict2.getEmp_no());
		   		    	mapVo.put("emp_id", PrmEmpDict2.getEmp_id());
		   		    	mapVo.put("is_audit", 0);
		   		    	mapVo.put("user_code", "");
		   		    	mapVo.put("audit_date", "");	
		   		    	
		   		    	state =  prmEmpTargetDataMapper.addPrmEmpTargetData(mapVo);
		   		    }
		        		
		         }
		 		
		 		
			}
		 	
			if( state > 0){
       			return "{\"msg\":\"院级指标数据生成成功.\",\"state\":\"true\"}";
       		}else{
       			return "{\"error\":\"院级指标数据生成异常 请联系管理员!\"}";
       		}
			
	}
	@Override
	public String queryPrmEmpTargetPrmTargetData(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
	SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<PrmEmpTargetData> list = prmEmpTargetDataMapper.queryPrmEmpTargetPrmTargetData(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<PrmEmpTargetData> list = prmEmpTargetDataMapper.queryPrmEmpTargetPrmTargetData(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
		
	}
	
	
	@Override
	public String importPrmEmpTargetData(Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		 
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
			
			// 3.查询 基本指标字典 List
			List<PrmTarget> targetList = prmTargetMapper.queryPrmTarget(entityMap);
			//用于存储查询targetList中的AphiTarget对象,以键值对的形式存储,用于判断指标是否存在
			Map<String, PrmTarget> targetMap = new HashMap<String, PrmTarget>();
			//将指标List存入Map   键:target_code 值:AphiTarget
			for(PrmTarget target : targetList){
				targetMap.put(target.getTarget_code(), target);
				targetMap.put(target.getTarget_name(), target);
			}
						
			
			//4.判断表头中指标是否存在
			StringBuffer sb = new StringBuffer();//提示信息:用于存储表头中不存在的指标
			Map<String,String> targetColumnMap = new HashMap<String,String>();//用于存储表头中的指标,作为遍历数据时取指标值
			
			for(Map<String,List<String>> item : liData ){
				for(Map.Entry<String, List<String>> entry : item.entrySet()){
					String key = entry.getKey();
					if("年度".equals(key) || "月份".equals(key) || "职工编码或者职工姓名".equals(key)){
						continue;
					}
					
					targetColumnMap.put(key, key);
					if(targetMap.get(key) == null){
						sb.append("指标" + key + "不存在,");
					}
				}
				break;//判断指标表头是否存在,只遍历一次
			}
			
			if(targetColumnMap == null || targetColumnMap.size() == 0){
				return "{\"error\":\"表头中未存在指标或未填写任何指标\",\"state\":\"false\"}";
			}
			
			//表头含有不存在指标 返回提示
			if(sb.length() > 0){
				return "{\"error\":\"" + sb.deleteCharAt(sb.length() -1).toString()+ "\",\"state\":\"false\"}";
			}
			
			List<PrmEmpTargetData> petdList = prmEmpTargetDataMapper.queryPrmEmpTargetData(entityMap);
			//5.以年、月、科室id、科室变更no、指标编码作为键,pdtd对象作为值,判断数据是否存在
			Map<String,PrmEmpTargetData> petdMap = new HashMap<String,PrmEmpTargetData>();
			for(PrmEmpTargetData petd : petdList){
				
				String key = String.valueOf(petd.getAcc_year()) +
						String.valueOf(petd.getAcc_month()) + 
						String.valueOf(petd.getEmp_id()) + 
						String.valueOf(petd.getEmp_no()) + 
						String.valueOf(petd.getTarget_code());
				
				petdMap.put(key, petd);
			}
			
			//查询所有职工
			List<PrmEmpDict> empDictList = prmEmpDictMapper.queryPrmEmpDictList(queryMap);
			//用于存储查询empDictList中的PrmEmpDict对象,以键值对的形式存储,用于判断职工是否存在
			Map<String,PrmEmpDict> empDictMap = new HashMap<String,PrmEmpDict>();
			//将职工List存入Map   键:emp_name 值:PrmEmpDict
			for(PrmEmpDict empDict : empDictList){
				empDictMap.put(empDict.getEmp_name(), empDict);
				empDictMap.put(empDict.getEmp_code(), empDict);
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
			
			for(Map.Entry<String, String> entry:targetColumnMap.entrySet()){//遍历指标
				
				//遍历导入数据
				for(Map<String,List<String>> item : liData ){
					
					List<String> acc_year = item.get("年度") ;
					List<String> acc_month = item.get("月份") ;
					List<String> emp_name = item.get("职工编码或者职工姓名") ;
					List<String> target_code = item.get(entry.getKey()) ;//指标
					
					
					if(acc_year.get(1) == null){
						return "{\"warn\":\"年度为空！\",\"state\":\"false\",\"row_cell\":" +acc_year.get(0) +"\"\"}";
					}
					
					if(acc_month.get(1) == null){
						return "{\"warn\":\"月份为空！\",\"state\":\"false\",\"row_cell\":\"" + acc_month.get(0) +"\"}";
					}
					
					if(target_code.get(1) == null){
						return "{\"warn\":\"指标值为空！\",\"state\":\"false\",\"row_cell\":\"" + target_code.get(0) + "\"}";
					}
					
					if(emp_name.get(1) == null){
						return "{\"warn\":\"职工为空！\",\"state\":\"false\",\"row_cell\":\"" + target_code.get(0) + "\"}";
					}else{
						if(empDictMap.get(emp_name.get(1)) == null ){
							return "{\"warn\":\"" + emp_name.get(1) + ",职工不存在！\",\"state\":\"false\",\"row_cell\":\"" + emp_name.get(0) + "\"}";
						}
					}
					
					if(target_code.get(1) == null){
						return "{\"warn\":\"指标值为空！\",\"state\":\"false\",\"row_cell\":\" " + target_code.get(0) + "\"}";
					}else{
						 try{
							 Double.parseDouble((target_code.get(1)));//校验是否为数值
						 }catch(NumberFormatException e){
							 return "{\"warn\":\"" + target_code.get(1) + ",指标值输入不合法,只能输入数字！\",\"state\":\"false\",\"row_cell\":\"" + target_code.get(0) + "\"}";
						 }
					}
					
					//以年度|月份|指标|职工称为键值,判断导入数据是否重复
					String key = acc_year.get(1) + "|" +acc_month.get(1) + "|" + targetMap.get(entry.getKey()).getTarget_code() + "|" + emp_name.get(1);
					if(exitMap.get(key) != null ){
						err_sb.append(acc_year.get(1)+"年度," + acc_month.get(1)+"月份," + emp_name.get(1)+"职工," + entry.getKey()+"指标<br/>");
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
					addMap.put("target_code",targetMap.get(entry.getKey()).getTarget_code());
					
					
					if(empDictMap.get(emp_name.get(1)) != null){
						addMap.put("emp_id",empDictMap.get(emp_name.get(1)).getEmp_id());
						addMap.put("emp_no",empDictMap.get(emp_name.get(1)).getEmp_no());
					}
					
					addMap.put("target_value", target_code.get(1));
					addMap.put("is_audit", 0);
					addMap.put("user_code", "");
					addMap.put("audit_date","");
					
					//根据年+月+职工id+职工变更no+指标编码 作为键 判断数据库中是否存在数据
					String is_exit_key = 
							String.valueOf(addMap.get("acc_year")) +
							String.valueOf(addMap.get("acc_month")) +
							String.valueOf(addMap.get("emp_id")) +
							String.valueOf(addMap.get("emp_no")) +
							String.valueOf(addMap.get("target_code")) ;
					
					
					PrmEmpTargetData petd = petdMap.get(is_exit_key);
					if(petd == null){//不存在,添加
						
						addList.add(addMap);
					}else{
						
						if( !"1".equals(String.valueOf(petd.getIs_audit())) ){//存在,如果未审核,添加到修改List
							
							updateList.add(addMap);
						}
					}
				}
			}
			
			if( err_sb.length() > 0){//重复数据是否存在
				 return "{\"warn\":\"以下数据【" +err_sb.toString() + "】数据重复！\",\"state\":\"false\"}";
			}
			
			
			if(addList.size() > 0){//添加
				
				List<Map<String,Object>> tempAddList = new ArrayList<Map<String,Object>>();
				
				for (int i = 0; i < addList.size(); i++) {
					
					tempAddList.add(addList.get(i));

					if ((i+1) % 500 == 0) {
						prmEmpTargetDataMapper.addBatchPrmEmpTargetData(tempAddList);
						tempAddList.removeAll(tempAddList);
					}
				}
				prmEmpTargetDataMapper.addBatchPrmEmpTargetData(tempAddList);
			}
			
			
			if(updateList.size() > 0){//修改
				
				List<Map<String,Object>> tempUpdateList = new ArrayList<Map<String,Object>>();
				
				for (int i = 0; i < updateList.size(); i++) {
					
					tempUpdateList.add(updateList.get(i));

					if ((i+1) % 500 == 0) {
						prmEmpTargetDataMapper.updateBatchPrmEmpTargetData(tempUpdateList);
						tempUpdateList.removeAll(tempUpdateList);
					}
				}
				prmEmpTargetDataMapper.updateBatchPrmEmpTargetData(tempUpdateList);
			}
			
			
			return "{\"msg\":\"导入成功.\",\"state\":\"true\"}";
			
			
			
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e.getMessage(), e);
			
			throw new SysException("{\"error\":\"导入失败.\"}");
		}
		 
	}
	
}
