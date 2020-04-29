package com.chd.hrp.hr.service.salarymanagement.socialSecurityManage;

import java.util.Map;

import org.springframework.dao.DataAccessException;
/**
 * 【薪资管理-社保管理】：社保险种
 * @author yang
 *
 */
public interface HrInsurKindService {

	/**
	 * 社保险种主查询
	 * @param paramMap
	 * @return
	 * @throws DataAccessException
	 */
	public Map<String, Object> queryInsurKind(Map<String, Object> paramMap) throws DataAccessException;

	/**
	 * 社保险种 保存
	 * @param paramMap
	 * @return
	 * @throws DataAccessException
	 */
	public String saveInsureKind(Map<String, Object> paramMap) throws DataAccessException;
	
	/**
	 * 社保险种 删除
	 * @param paramMap
	 * @return
	 * @throws DataAccessException
	 */
	public String deleteInsureKind(Map<String, Object> paramMap) throws DataAccessException;

}
