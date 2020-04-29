
/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 package com.chd.hrp.prm.serviceImpl;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.SessionManager;
import com.chd.base.SysPage;
import com.chd.base.util.ChdJson;
import com.chd.base.util.JsonListMapUtil;
import com.chd.hrp.prm.dao.PrmEmpMapper;
import com.chd.hrp.prm.dao.PrmEmpTargetDataCalculateMapper;
import com.chd.hrp.prm.dao.PrmTargetMapper;
import com.chd.hrp.prm.entity.PrmEmp;
import com.chd.hrp.prm.entity.PrmEmpTargetDataCalculate;
import com.chd.hrp.prm.entity.PrmTarget;
import com.chd.hrp.prm.service.PrmEmpTargetDataCalculateService;
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
 


@Service("prmEmpTargetDataCalculateService")
public class PrmEmpTargetDataCalculateServiceImpl implements PrmEmpTargetDataCalculateService {

	private static Logger logger = Logger.getLogger(PrmEmpTargetDataCalculateServiceImpl.class);
	//引入DAO操作
	@Resource(name = "prmEmpTargetDataCalculateMapper")
	private final PrmEmpTargetDataCalculateMapper prmEmpTargetDataCalculateMapper = null;
    
	@Resource(name = "prmTargetMapper")
	private final PrmTargetMapper  prmTargetMapper = null;
	
	@Resource(name = "prmEmpMapper")
	private final PrmEmpMapper prmEmpMapper = null;
	/**
	 * @Description 
	 * 添加0412 职工绩效指标数据表<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addPrmEmpTargetDataCalculate(Map<String,Object> entityMap)throws DataAccessException{
		
		//获取对象0412 职工绩效指标数据表
		PrmEmpTargetDataCalculate prmEmpTargetDataCalculate = prmEmpTargetDataCalculateMapper.queryPrmEmpTargetDataCalculateByCode(entityMap);

		if (prmEmpTargetDataCalculate != null) {

			return "{\"error\":\"数据重复,请重新添加.\"}";

		}
		
		try {
			
			int state = prmEmpTargetDataCalculateMapper.addPrmEmpTargetDataCalculate(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addPrmEmpTargetData\"}";

		}
		
	}
	
	@Override
	public String updateBatchPrmEmpTargetDataCalculate(
			List<Map<String, Object>> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		try {
			
			 prmEmpTargetDataCalculateMapper.updateBatchPrmEmpTargetDataCalculate(entityMap);
				
				return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

			}
			catch (Exception e) {

				logger.error(e.getMessage(), e);

				return "{\"error\":\"跟新失败 数据库异常 请联系管理员! 方法 updateBatchPrmEmpTargetData\"}";

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
	public String queryPrmEmpTargetDataCalculate(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<PrmEmpTargetDataCalculate> list = prmEmpTargetDataCalculateMapper.queryPrmEmpTargetDataCalculate(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<PrmEmpTargetDataCalculate> list = prmEmpTargetDataCalculateMapper.queryPrmEmpTargetDataCalculate(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
		
	}
	
	@Override
	public String queryPrmEmpTargetPrmTargetDataCalculate(
			Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
           SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<PrmEmpTargetDataCalculate> list = prmEmpTargetDataCalculateMapper.queryPrmEmpTargetPrmTargetDataCalculate(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<PrmEmpTargetDataCalculate> list = prmEmpTargetDataCalculateMapper.queryPrmEmpTargetPrmTargetDataCalculate(entityMap, rowBounds);
			
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
	public PrmEmpTargetDataCalculate queryPrmEmpTargetDataCalculateByCode(Map<String,Object> entityMap)throws DataAccessException{
		return prmEmpTargetDataCalculateMapper.queryPrmEmpTargetDataCalculateByCode(entityMap);
	
	}

	@Override
	public String auditPrmEmpTargetDataCalculate(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		try {
			

	        
			  int state = prmEmpTargetDataCalculateMapper.auditPrmEmpTargetDataCalculate(entityMap);
				
				return "{\"msg\":\"审核成功.\",\"state\":\"true\"}";

			}
			catch (Exception e) {

				logger.error(e.getMessage(), e);

				return "{\"error\":\"审核失败 数据库异常 请联系管理员!\"}";

			}	
	}

	@Override
	public String reAuditPrmEmpTargetDataCalculate(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		try {

			  int state = prmEmpTargetDataCalculateMapper.auditPrmEmpTargetDataCalculate(entityMap);
				
				return "{\"msg\":\"取消审核成功.\",\"state\":\"true\"}";

			}
			catch (Exception e) {

				logger.error(e.getMessage(), e);

				return "{\"error\":\"取消审核失败 数据库异常 请联系管理员!\"}";

			}	
	}

	@Override
	public String createPrmEmpTargetDataCalculate(
			Map<String, Object> entityMap, String paramVo)
			throws DataAccessException {
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
		    
		    prmEmpTargetDataCalculateMapper.cleanPrmEmptTargetDataCalculate(mapVo);
		    
		    int state = 0;
			/*职工*/
			entityMap.put("target_nature", "04");
			
			Map<String, List<PrmTarget>> prmTargetMapVo = new HashMap<String,  List<PrmTarget>>();
			
			/*查询全院手动录入基本指标*/
			List<PrmTarget> prmTarget = prmTargetMapper.queryPrmTargetNatureCreateCalculate(entityMap);
			
		 	prmTargetMapVo.put("target_code", prmTarget);  
		 	
		 	
		 	entityMap.put("is_stop", 0);
		 	 
		 	List<PrmEmp>  prmEmpDict = prmEmpMapper.queryPrmEmp(entityMap);
		 	
		 	for (PrmEmp prmEmpDict2 : prmEmpDict) {
				
		 		  for (Iterator<String> iterator = prmTargetMapVo.keySet().iterator(); iterator.hasNext();) {
		     			
		   		      String key = iterator.next();
		   		      
		   		      List<PrmTarget> l = prmTargetMapVo.get(key);
		   	
		   	
		   		      for (int i = 0; i < l.size(); i++) {
		   		    	
		   		    	mapVo.put("target_code", l.get(i).getTarget_code().toString());
		   		  	    mapVo.put("target_value", 0);
		   		  	    mapVo.put("emp_no", prmEmpDict2.getEmp_no());
		   		    	mapVo.put("emp_id", prmEmpDict2.getEmp_id());
		   		    	mapVo.put("is_audit", 0);
		   		    	mapVo.put("user_code", "");
		   		    	mapVo.put("audit_date", "");		
		   		    	
		   		    	state =  prmEmpTargetDataCalculateMapper.addPrmEmpTargetDataCalculate(mapVo);
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
	public List<Map<String,Object>> queryPrmEmpTargetDataCalculatePrint(Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		
		List<PrmEmpTargetDataCalculate> list = prmEmpTargetDataCalculateMapper.queryPrmEmpTargetPrmTargetDataCalculate(entityMap);

		return JsonListMapUtil.beanToListMap(list);
	}

	
}
