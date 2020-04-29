package com.chd.hrp.hr.service.salarymanagement.socialSecurityManage;

import java.util.Map;

import org.springframework.dao.DataAccessException;

/**
 * 【薪资管理-社保管理】：缴费基数设置
 * @author yang
 *
 */
public interface HrInsurBaseCalService {

	/**
	 * 查询
	 * @param paramMap
	 * @return
	 * @throws DataAccessException
	 */
	public String queryHrInsurBaseCal(Map<String, Object> paramMap) throws DataAccessException;

	/**
	 * 删除
	 * @param paramMap
	 * @return
	 * @throws DataAccessException
	 */
	public String deleteHrInsurBaseCal(Map<String, Object> paramMap) throws DataAccessException;
	
	/**
	 * 添加公式
	 * @param paramMap
	 * @return
	 * @throws DataAccessException
	 */
	public String addHrInsurBaseCal(Map<String, Object> paramMap) throws DataAccessException;
 
	/**
	 * 保存（更新公式）
	 * @param paramMap
	 * @return
	 * @throws DataAccessException
	 */
	public String updateHrInsurBaseCal(Map<String, Object> paramMap) throws DataAccessException;

	/**
	 * 公式设置 左侧树
	 * @param paramMap
	 * @return
	 * @throws DataAccessException
	 */
	public String queryInsurBaseSetFunTree(Map<String, Object> paramMap) throws DataAccessException;

	

}
