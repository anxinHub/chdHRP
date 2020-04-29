/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.ass.dao.check.general;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.ass.entity.apply.AssApplyDept;
/**
 * 
 * @Description:
 * 051001资产盘亏申报(专用设备)
 * @Table:
 * ASS_CHK_A_General
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

public interface AssChkAGeneralMapper extends SqlMapper{
	public Map<String,Object>  collectAssChkAGeneral(Map<String, Object> entityMap)throws DataAccessException;

	public int updateBatchConfirm(List<Map<String, Object>> listVo)throws DataAccessException;

	public List<AssApplyDept> queryAssApplyDeptByPlanDept(Map<String, Object> entityMap)throws DataAccessException;

	public List<AssApplyDept> queryAssApplyDeptByPlanDept(Map<String, Object> entityMap, RowBounds rowBounds)throws DataAccessException;
}
