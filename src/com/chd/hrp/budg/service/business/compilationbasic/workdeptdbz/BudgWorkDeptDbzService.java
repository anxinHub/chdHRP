/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.budg.service.business.compilationbasic.workdeptdbz;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlService;
/**
 * 
 * @Description:
 * 科室单病种业务预算
 * @Table:
 * BUDG_WORK_DEPT_DBZ
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

public interface BudgWorkDeptDbzService extends SqlService {
	/**
	 * 根据  病种编码 查询其是否存在
	 * @param mapVo
	 * @return
	 */
	public int queryDiseaseCodeExist(Map<String, Object> mapVo) throws DataAccessException;
	
	/**
	 * 根据病种编码、科室编码 查询 对应关系是否存在（查 科室单病种维护表  BUDG_DEPT_SINGLE_DISEASE）
	 * @param mapVo
	 * @return
	 */
	public Map<String, Object> queryDeptCodeExist(Map<String, Object> mapVo) throws DataAccessException;
	
	/**
	 * 根据病种编码 、应医保类型编码 查询 对应关系是否存在（查 医保单病种维护表 BUDG_YB_SINGLE_DISEASE）
	 * @param mapVo
	 * @return
	 */
	public int queryInsuranceCodeExist(Map<String, Object> mapVo) throws DataAccessException;
	
	/**
	 * 根据主键查询 科室单病种业务预算数据是否存在
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public int queryDataExist(Map<String, Object> mapVo) throws DataAccessException;
	
	/**
	 * 生成时 查询生成数据
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryData(Map<String, Object> mapVo) throws DataAccessException;
	
	/**
	 * 预算科室 下拉框 添加页面用（根据病种编码 查询科室单病种维护表   BUDG_DEPT_SINGLE_DISEASE）
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public String queryBudgDeptDict(Map<String, Object> mapVo) throws DataAccessException;
	
	/**
	 * 医保类型下拉框 添加页面用（根据病种编码 查询医保单病种维护表  BUDG_YB_SINGLE_DISEASE）
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public String queryBudgYBTY(Map<String, Object> mapVo) throws DataAccessException;
	
	/**
	 * 病种名称下拉框
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	public String queryBudgSingleDC(Map<String, Object> mapVo)throws DataAccessException;

	public String budgWorkHosDbzImportNewPage(Map<String, Object> mapVo) throws DataAccessException;
	
	/**
	 * 保存数据
	 * @param listVo
	 * @return
	 * @throws DataAccessException
	 */
	public String save(List<Map<String, Object>> listVo) throws DataAccessException;


}
