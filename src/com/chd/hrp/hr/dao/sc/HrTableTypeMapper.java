/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.hr.dao.sc;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.hr.entity.sc.HrTableType;
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
 

public interface HrTableTypeMapper extends SqlMapper{

	int deleteBatchHrTableType(List<HrTableType> entityList)throws DataAccessException;
	
	int queryTableTypeExits(Map<String,Object> entityMap)throws DataAccessException;

	Map<String,Object> queryTypeTabByName(Map<String, Object> entityMap) throws DataAccessException;
	
}
