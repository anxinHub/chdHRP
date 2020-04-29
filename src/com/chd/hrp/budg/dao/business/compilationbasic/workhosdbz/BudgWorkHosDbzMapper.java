/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.budg.dao.business.compilationbasic.workhosdbz;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
/**
 * 
 * @Description:
 * 医院单病种业务预算
 * @Table:
 * BUDG_WORK_HOS_DBZ
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

public interface BudgWorkHosDbzMapper extends SqlMapper{

	/**
	 * 根据主键查询 医院单病种业务预算是否已存在
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public int queryDataExist(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 生成时 查询生成数据
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryData(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 汇总时 查询 已存在的 科室单病种业务预算数据
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryDeptDataExist(Map<String, Object> mapVo) throws DataAccessException;
	
	/**
	 * 汇总时 查询 不存在的 科室单病种业务预算数据
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryDeptDataNotExist(Map<String, Object> mapVo) throws DataAccessException ;

	public List<Map<String, Object>> queryBudgDiseaseCodeId(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 批量 查询 添加数据是否已存在
	 * @param addList
	 * @return
	 * @throws DataAccessException
	 */
	public String queryDataExistList(List<Map<String, Object>> addList) throws DataAccessException;
	
}
