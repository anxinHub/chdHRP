/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
package com.chd.hrp.prm.serviceImpl;

import java.util.*;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.SessionManager;
import com.chd.base.SysPage;
import com.chd.base.util.ChdJson;
import com.chd.base.util.DateUtil;
import com.chd.hrp.prm.dao.PrmGoalMapper;
import com.chd.hrp.prm.dao.PrmHosKpiAdMapper;
import com.chd.hrp.prm.dao.PrmHosKpiObjMapper;
import com.chd.hrp.prm.dao.PrmHosKpiSectionMapper;
import com.chd.hrp.prm.dao.PrmHosSchemeMapper;
import com.chd.hrp.prm.entity.PrmGoal;
import com.chd.hrp.prm.entity.PrmHosKpiAd;
import com.chd.hrp.prm.entity.PrmHosKpiObj;
import com.chd.hrp.prm.entity.PrmHosKpiSection;
import com.chd.hrp.prm.entity.PrmHosScheme;
import com.chd.hrp.prm.service.PrmHosSchemeAuditService;
import com.chd.hrp.prm.service.PrmHosSchemeService;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Description: 0203 医院绩效方案表
 * @Table: PRM_HOS_SCHEME
 * @Author: bell
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Service("prmHosSchemeAuditService")
public class PrmHosSchemeAuditServiceImpl implements PrmHosSchemeAuditService {

	
	private static Logger logger = Logger.getLogger(PrmHosSchemeAuditServiceImpl.class);
	
	// 引入DAO操作
	@Resource(name = "prmGoalMapper")
	private final PrmGoalMapper prmGoalMapper = null;
	

	@Override
	public String auditPrmHosScheme(Map<String,Object> entityMap)
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
	public String reAuditPrmHosScheme(Map<String,Object> entityMap)
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
