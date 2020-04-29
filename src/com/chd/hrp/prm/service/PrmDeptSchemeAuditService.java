
/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 package com.chd.hrp.prm.service;
import java.util.*;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.prm.entity.PrmHosScheme;
/**
 * 
 * @Description:
 * 0203 医院绩效方案表
 * @Table:
 * PRM_HOS_SCHEME
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 


public interface PrmDeptSchemeAuditService {

	public String auditPrmDeptScheme(Map<String,Object> entityMap)throws DataAccessException;
	
	
	public String reAuditPrmDeptScheme(Map<String,Object> entityMap)throws DataAccessException;
}
