/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.budg.dao.business.drugdisburse;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
/**
 * 
 * @Description:
 * 科室药品支出预算编制
 * @Table:
 * BUDG_DRUG
 * @Author: slient
 * @email:  slient@e-tonggroup.com
 * @Version: 1.0
 */
public interface BudgDrugMapper extends SqlMapper{

	/**
	 * 查询科室列表
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryHosDeptDict(Map<String, Object> mapVo)throws DataAccessException;

	/**
	 * 查询药品分类列表
	 * 根据年度从BUDG_MED_TYPE_SUBJ中取
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryBudgMedTypeSubj(Map<String, Object> mapVo)throws DataAccessException;

	/**
	 * 查询上年支出数据
	 * @param mapVo
	 * @return
	 */
	public List<Map<String, Object>> querySubjCodebyYearOrMedTypeId(Map<String, Object> mapVo) throws DataAccessException;
	/**
	 * 通过SQL完成部分业务逻辑  生成所需要的数据
	 * @param mapVo
	 * @return
	 */
	public List<Map<String, Object>> queryInsertData(Map<String, Object> mapVo) throws DataAccessException;
	/**
	 * 根据调整比例  更新数据
	 */
	public int budgDrugUpdateAdjRate(List<Map<String, Object>> listVo) throws DataAccessException;
	
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
	public void generateAddBatch(List<Map<String, Object>> addList) throws DataAccessException;
	
	/**
	 * 校验数据是否已存在
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public int queryDataExist(Map<String, Object> entityMap)throws DataAccessException;
	
	/**
	 * 查询添加数据是否已存在
	 * @param addList
	 * @return
	 * @throws DataAccessException
	 */
	public String queryDataExistList(List<Map<String, Object>> addList) throws DataAccessException;
	
	/**
	 * 根据 参数  查询收入预算、上年收入、上年同期支出  计算收入预算增长比例和计算值用
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public Map<String, Object> queryLastCostAndRate(Map<String, Object> mapVo) throws DataAccessException;
	
}
