package com.chd.hrp.hr.service.salarymanagement.accumulationfund;

import java.util.Map;

import org.springframework.dao.DataAccessException;

/**
 * 【薪资管理-公积金等】:缴费基数设置
 * @author yang
 *
 */
public interface HrFundBaseCalService {

	/**
	 * 查询
	 * @param paramMap
	 * @return
	 * @throws DataAccessException
	 */
	public String queryHrFundBaseCal(Map<String, Object> paramMap) throws DataAccessException;

	/**
	 * 删除公式
	 * @param paramMap
	 * @return
	 * @throws DataAccessException
	 */
	public String deleteHrFundBaseCal(Map<String, Object> paramMap) throws DataAccessException;

	/**
	 * 添加公式
	 * @param paramMap
	 * @return
	 * @throws DataAccessException
	 */
	public String addHrFundBaseCal(Map<String, Object> paramMap) throws DataAccessException;

	/**
	 * 保存（更新）公式
	 * @param paramMap
	 * @return
	 * @throws DataAccessException
	 */
	public String updateHrFundBaseCal(Map<String, Object> paramMap) throws DataAccessException;

	/**
	 * 公式设置左侧树
	 * @param paramMap
	 * @return
	 * @throws DataAccessException
	 */
	public String queryFundBaseSetFunTree(Map<String, Object> paramMap) throws DataAccessException;

}
