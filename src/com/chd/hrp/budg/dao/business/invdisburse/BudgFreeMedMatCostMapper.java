/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.budg.dao.business.invdisburse;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
/**
 * 
 * @Description:
 * 属性（NATURE）取自系统字典表：01门诊02住院03检查
 * @Table:
 * BUDG_FREE_MED_MAT_COST
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

public interface BudgFreeMedMatCostMapper extends SqlMapper{

	/**
	 * 校验数据 是否存在
	 * @param map
	 * @return
	 * @throws DataAccessException
	 */
	public int queryDataExist(Map<String, Object> map)throws DataAccessException;

	/**
	 * 删除所选年度 全年数据
	 * @param mapVo
	 * @throws DataAccessException
	 */
	public int deleteYearAllData(Map<String, Object> mapVo) throws DataAccessException;
	 
	/**
	 * 采集同时添加数据
	 * @param mapVo
	 * @throws DataAccessException
	 */
	public int collectData(Map<String, Object> mapVo) throws DataAccessException;
	
}
