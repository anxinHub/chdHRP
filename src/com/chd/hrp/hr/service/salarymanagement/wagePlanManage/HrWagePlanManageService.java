package com.chd.hrp.hr.service.salarymanagement.wagePlanManage;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.hr.entity.salarymanagement.wagePlanManage.HrWageItem;
import com.chd.hrp.hr.entity.salarymanagement.wagePlanManage.HrWagePlan;
import com.chd.hrp.hr.entity.salarymanagement.wagePlanManage.HrWagePlanKind;

/**
 * 【薪资管理-薪资方案管理】 service
 * @author yangyunfei
 *
 */
public interface HrWagePlanManageService {

	/**
	 * 查询薪资方案
	 * @param paramMap
	 * @return
	 * @throws DataAccessException
	 */
	public String queryWagePlan(Map<String, Object> paramMap) throws DataAccessException;
	
	/**
	 * 删除薪资方案
	 * @param paramMap
	 * @return
	 * @throws DataAccessException
	 */
	public String deleteWagePlan(Map<String, Object> paramMap) throws DataAccessException;
	
	/**
	 * 添加薪资方案
	 * @param paramMap
	 * @return
	 * @throws DataAccessException
	 */
	public String addWagePlan(Map<String, Object> paramMap) throws DataAccessException;
	
	/**
	 * 更新薪资方案
	 * @param mapVo
	 * @return
	 */
	public String updateWagePlan(Map<String, Object> mapVo) throws DataAccessException;
	
	/**
	 * 取一个薪资方案
	 * @param paramMap
	 * @return
	 */
	public HrWagePlan findHrWagePlan(Map<String, Object> paramMap) throws DataAccessException;
	
	/**
	 * 查询工资项
	 * @param paramMap
	 * @return
	 * @throws DataAccessException
	 */
	public String queryWageItem(Map<String, Object> paramMap) throws DataAccessException;

	/**
	 * 删除工资项
	 * @param paramMap
	 * @return
	 * @throws DataAccessException
	 */
	public String deleteWageItem(Map<String, Object> paramMap) throws DataAccessException;

	/**
	 * 复制薪资方案
	 * @param paramMap
	 * @return
	 * @throws DataAccessException
	 */
	public String copyWagePlan(Map<String, Object> paramMap) throws DataAccessException;

	/**
	 * 添加工资项
	 * @param paramMap
	 * @return
	 * @throws DataAccessException
	 */
	public String addWageItem(Map<String, Object> paramMap) throws DataAccessException;
	
	/**
	 * 更新工资项
	 * @param paramMap
	 * @return
	 * @throws DataAccessException
	 */
	public String updateHrWageItem(Map<String, Object> mapVo) throws DataAccessException;

	/**
	 * 
	 * @param paramMap
	 * @return
	 */
	public String wagePlanSelect(Map<String, Object> paramMap) throws DataAccessException;

	/**
	 * 取一个工资项
	 * @param paramMap
	 * @return
	 */
	public HrWageItem findHrWageItem(Map<String, Object> paramMap) throws DataAccessException;

	/**
	 * 下拉选薪资标准表取值
	 * @param paramMap
	 * @return
	 * @throws DataAccessException
	 */
	public String selectHrWageStan(Map<String, Object> paramMap) throws DataAccessException;

	public List<HrWagePlanKind> findHrWagePlanKindByFK(Map<String, Object> paramMap) throws DataAccessException;

	/**
	 * 下拉选工资项类型
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public String accWageItemTypeSelect(Map<String, Object> paramMap) throws DataAccessException;

	/**
	 * 下拉选工资项
	 * @param paramMap
	 * @return
	 * @throws DataAccessException
	 */
	public String wageItemSelect(Map<String, Object> paramMap) throws DataAccessException;

	/**
	 * 导入工资项
	 * @param paramMap
	 * @return
	 * @throws DataAccessException
	 */
	public String importHrWageItemData(Map<String, Object> paramMap) throws DataAccessException;


}
