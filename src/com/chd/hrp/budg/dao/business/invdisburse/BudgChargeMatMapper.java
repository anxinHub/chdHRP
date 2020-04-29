/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.budg.dao.business.invdisburse;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
/**
 * 
 * @Description:
 * tabledesc
 * @Table:
 * BUDG_CHARGE_MAT
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

public interface BudgChargeMatMapper extends SqlMapper{
	
	/**
	 * 查询上年支出数据
	 * @param mapVo
	 * @return
	 */
	public List<Map<String, Object>> querySubjCodebyYearOrMatTypeId(Map<String, Object> mapVo) throws DataAccessException;
	/**
	 * 通过SQL完成部分业务逻辑  生成所需要的数据
	 * @param mapVo
	 * @return
	 */
	public List<Map<String, Object>> queryInsertData(Map<String, Object> mapVo) throws DataAccessException;
	/**
	 * 根据调整比例  更新数据
	 */
	public int budgChargeMatUpdateAdjRate(List<Map<String, Object>> listVo) throws DataAccessException;
	
	/**
	 * 查询预算年度物资分类与收入科目对应关系是否维护
	 * @param mapVo
	 * @return
	 */
	public int queryIncomeSubj(Map<String, Object> mapVo) throws DataAccessException;
	
	/**
	 * 批量添加生成数据
	 * @param addList
	 */
	public int generateAddBatch(List<Map<String, Object>> addList) throws DataAccessException;
	
	/**
	 * 校验数据 是否存在
	 * @param map
	 * @return
	 * @throws DataAccessException
	 */
	public int queryDataExist(Map<String, Object> map) throws DataAccessException;
}
