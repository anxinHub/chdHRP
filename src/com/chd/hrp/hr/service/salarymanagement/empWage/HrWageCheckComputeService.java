package com.chd.hrp.hr.service.salarymanagement.empWage;

import java.util.Map;

import org.springframework.dao.DataAccessException;

/**
 * 【薪资管理-职工薪资-薪资核算】service
 * @author yang
 *
 */
public interface HrWageCheckComputeService {

	/**
	 * 生成
	 * @param paramMap
	 * @return
	 * @throws DataAccessException
	 */
	public String generateEmpWage(Map<String, Object> paramMap) throws DataAccessException;

	/**
	 * 查询薪资核算grid表头
	 * @param paramMap
	 * @return
	 * @throws DataAccessException
	 */
	public String queryWageCheckComputeHead(Map<String, Object> paramMap) throws DataAccessException;

	/**
	 * 查询薪资核算grid表内容
	 * @param paramMap
	 * @return
	 * @throws DataAccessException
	 */
	public String queryWageCheckComputeGrid(Map<String, Object> paramMap) throws DataAccessException;

	/**
	 * 保存职工薪资
	 * @param paramMap
	 * @return
	 */
	public String saveEmpHrWage(Map<String, Object> paramMap) throws DataAccessException;

	/**
	 * 职工薪资计算
	 * @param paramMap
	 * @return
	 * @throws DataAccessException
	 */
	public String collectEmpHrWage(Map<String, Object> paramMap) throws DataAccessException;

	/**
	 * 删除职工薪资
	 * @param paramMap
	 * @return
	 * @throws DataAccessException
	 */
	public String deleteEmpHrWage(Map<String, Object> paramMap) throws DataAccessException;

	/**
	 * 通过薪资方案关联的职工分类查职工
	 * @param paramMap
	 * @return
	 * @throws DataAccessException
	 */
	public String queryEmpByWagePlanEmpKind(Map<String, Object> paramMap) throws DataAccessException;

	public String importCheckCompute(Map<String, Object> mapVo) throws DataAccessException;

	public String wageItemSelect(Map<String, Object> mapVo)throws DataAccessException;

	public String updateItem(Map<String, Object> mapVo)throws DataAccessException;
}
