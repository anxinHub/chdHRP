/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.budg.service.business.compilationbasic.workhosdbz;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlService;
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
 

public interface BudgWorkHosDbzService extends SqlService {
	
	/**
	 * 根据主键查询 医院单病种业务预算是否已存在
	 * @param item
	 * @return
	 */
	public int queryDataExist(Map<String, Object> item) throws DataAccessException;
	
	/**
	 * 生成时 查询生成数据
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryData(Map<String, Object> mapVo) throws DataAccessException;
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
	public List<Map<String, Object>> queryDeptDataNotExist(Map<String, Object> mapVo) throws DataAccessException;

	public String budgWorkHosDbzImportNewPage(Map<String, Object> mapVo) throws DataAccessException;
	
	/**
	 * 保存数据
	 * @param listVo
	 * @return
	 * @throws DataAccessException
	 */
	public String save(List<Map<String, Object>> listVo) throws DataAccessException;

}
