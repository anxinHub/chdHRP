/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
package com.chd.hrp.prm.serviceImpl;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.hrp.prm.dao.PrmGoalMapper;
import com.chd.hrp.prm.entity.PrmGoal;
import com.chd.hrp.prm.service.PrmEmpSchemeAuditService;


/**
 * 
 * @Description: 0203 医院绩效方案表
 * @Table: PRM_HOS_SCHEME
 * @Author: bell
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Service("prmEmpSchemeAuditService")
public class PrmEmpSchemeAuditServiceImpl implements PrmEmpSchemeAuditService {

	private static Logger logger = Logger.getLogger(PrmEmpSchemeAuditServiceImpl.class);

	// 引入DAO操作
	@Resource(name = "prmGoalMapper")
	private final PrmGoalMapper prmGoalMapper = null;
	
	@Override
	public String auditPrmEmpScheme(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
        
	     PrmGoal prmGoal = prmGoalMapper.queryPrmGoalByCode(entityMap);   
	  
	     
	     if(prmGoal == null){
	    	 
				prmGoalMapper.updatePrmGoal(entityMap);
				
				return "{\"msg\":\"审核 成功.\",\"state\":\"true\"}";
	     }else{
	    	 
	    	    return "{\"msg\":\"已经审核.\",\"\":\"\"}";
	     }
	}

	@Override
	public String reAuditPrmEmpScheme(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		  PrmGoal prmGoal = prmGoalMapper.queryPrmGoalByCode(entityMap);   
		   
		     if(prmGoal == null){
		    	 
					prmGoalMapper.updatePrmGoal(entityMap);
					
					return "{\"msg\":\"取消审核 成功.\",\"state\":\"true\"}";
		     }else{
		    	 
		    	    return "{\"msg\":\"已取消审核.\",\"state\":\"true\"}";
		     }
		

	}

	
}
