package com.chd.hrp.hr.service.salarymanagement.wageItemCal;

import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.hr.entity.salarymanagement.wagePlanManage.HrWageItemCal;
/**
 * 
 * @author yang
 *
 */
public interface HrWageItemCalService {

	/**
	 * 工资项取值方法主查询
	 * @param paramMap
	 * @return
	 * @throws DataAccessException
	 */
	public String queryHrWageItemCal(Map<String, Object> paramMap) throws DataAccessException;

	/**
	 * 工资项取值方法  添加
	 * @param paramMap
	 * @return
	 * @throws DataAccessException
	 */
	public String addHrWageItemCal(Map<String, Object> paramMap) throws DataAccessException;

	/**
	 * 工资项取值方法  删除
	 * @param paramMap
	 * @return
	 * @throws DataAccessException
	 */
	public String deleteHrWageItemCal(Map<String, Object> paramMap) throws DataAccessException;

	/**
	 * 取一个工资项取值方法，通过主键
	 * @param paramMap
	 * @return
	 * @throws DataAccessException
	 */
	public HrWageItemCal getHrWageItemCalByPK(Map<String, Object> paramMap) throws DataAccessException;

	/**
	 * 工资项取值方法 更新
	 * @param paramMap
	 * @return
	 * @throws DataAccessException
	 */
	public String updateHrWageItemCal(Map<String, Object> paramMap) throws DataAccessException;

	/**
	 * 职工分类下拉
	 * @param paramMap
	 * @return
	 * @throws DataAccessException
	 */
	public String empKindSelect(Map<String, Object> paramMap) throws DataAccessException;

	/**
	 * 编辑计算公式时的左侧树
	 * @param paramMap
	 * @return
	 * @throws DataAccessException
	 */
	public String queryHrWageItemCalTree(Map<String, Object> paramMap) throws DataAccessException;
	
	/**
	 * 公式转译
	 * @param paramMap
	 * @return
	 */
	public Map<String, Object> calTranslation(Map<String, Object> paramMap);
}
