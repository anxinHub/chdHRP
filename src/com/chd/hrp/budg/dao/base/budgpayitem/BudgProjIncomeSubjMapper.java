/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.budg.dao.base.budgpayitem;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
/**
 * 
 * @Description:
 * 支出项目与收入预算科目对应关系
 * @Table:
 * BUDG_PROJ_INCOME_SUBJ
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

public interface BudgProjIncomeSubjMapper extends SqlMapper{
	
	/**
	 * 根据支出项目编码 查询支出项目ID
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public Map<String, Object> queryItemCodeExist(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 项目信息查询 （添加、修改页面用） 不分页
	 * @param entityMap
	 * @return
	 */
	public List<Map<String, Object>> queryBudgProjDict(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 项目信息查询 （添加、修改页面用） 分页
	 * @param mapVo
	 * @param rowBounds
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String,Object>> queryBudgProjDict(Map<String, Object> mapVo, RowBounds rowBounds) throws DataAccessException;
	
	/**
	 * 根据 收入预算科目编码 查询该年度收入预算科目 是否为末级科目、是否存在 （导入校验用）
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public int queryIncomeSubjByCode(Map<String, Object> mapVo) throws DataAccessException;
	
	/**
	 * 查询 要继承的数据 
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryCopyData(Map<String, Object> mapVo) throws DataAccessException;
	
	/**
	 * 查询当前年度 收入预算科目编码信息（继承时 筛选数据用）
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public List<String> queryIncomeSubjCode(Map<String, Object> mapVo) throws DataAccessException;
	
	
}
