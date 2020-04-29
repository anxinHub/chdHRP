/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.hr.dao.sysstruc;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.hr.entity.sysstruc.HrTabType;
/**
 * 
 * @Description:
 * 系统结构分类 如：人事档案、组织机构
 * @Table:
 * HR_TAB_TYPE
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

public interface HrTabTypeMapper extends SqlMapper{

	int deleteBatchHrTabType(List<HrTabType> entityList)throws DataAccessException;
	
	int queryTabTypeExits(Map<String,Object> entityMap)throws DataAccessException;

	Map<String,Object> queryTypeTabByName(Map<String, Object> entityMap) throws DataAccessException;
	
}
