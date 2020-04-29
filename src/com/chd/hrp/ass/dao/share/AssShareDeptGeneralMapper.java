/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.ass.dao.share;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
/**
 * 
 * @Description:
 * 分摊科室设置_一般设备
 * @Table:
 * ASS_SHARE_DEPT_GENERAL
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

public interface AssShareDeptGeneralMapper extends SqlMapper{
	List<Map<String, Object>> queryByAssCardNo(Map<String, Object> map)throws DataAccessException;
	
	int deleteBatchByAssCardNo(List<Map<String, Object>> entityMap)throws DataAccessException;

	
}
