package com.chd.hrp.hr.dao.salarymanagement.wagePlanManage;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.hr.entity.salarymanagement.wagePlanManage.HrWageItem;
import com.chd.hrp.hr.entity.salarymanagement.wagePlanManage.HrWagePlan;
import com.chd.hrp.hr.entity.salarymanagement.wagePlanManage.HrWagePlanKind;

public interface HrWagePlanManageMapper extends SqlMapper{
	
	/**
	 * 查一个薪资方案下的所有工资项
	 * @param paramMap
	 * @return
	 */
	public List<HrWageItem> queryHrWageItemsByPlanCode(Map<String, Object> paramMap) throws DataAccessException;
	
	/**
	 * 通过主键取一个薪资方案
	 * @param paramMap
	 * @return
	 */
	public HrWagePlan findHrWagePlanByPK(Map<String, Object> paramMap) throws DataAccessException;

	/**
	 * 更新薪资方案
	 * @param paramMap
	 */
	public void updateWagePlan(Map<String, Object> paramMap) throws DataAccessException;
	
	/**
	 * 修改前，查薪资方案名称是否已占用
	 * @param paramMap
	 * @return
	 */
	public int queryPlanNameOccupy(Map<String, Object> paramMap) throws DataAccessException;
	
	/**
	 * 查询工资项（不带分页）
	 */
	public List<Map<String,Object>> queryWageItem(Map<String,Object> paramMap) throws DataAccessException;
	
	/**
	 * 查询工资项（带分页）
	 */
	public List<Map<String,Object>> queryWageItem(Map<String,Object> paramMap, RowBounds rowBounds) throws DataAccessException;

	/**
	 * 查工资项code和name是否已存在
	 * @param paramMap
	 * @return
	 */
	public List<Map<String, Object>> queryItemCodeExists(Map<String, Object> paramMap) throws DataAccessException;

	/**
	 * 添加工资项
	 * @param paramMap
	 * @throws DataAccessException
	 */
	public void addWageItem(Map<String, Object> paramMap) throws DataAccessException;

	/**
	 * 复制薪资方案
	 * @param list
	 */
	public void copyHrWageItem(Map<String, Object> paramMap) throws DataAccessException;

	/**
	 * 取薪资方案(提供下拉选用)
	 * @param paramMap
	 * @return
	 */
	public List<Map<String, Object>> selectWagePlan(Map<String, Object> paramMap) throws DataAccessException;

	/**
	 * 删除工资项
	 * @param mapList
	 */
	public void deleteHrWageItemBatch(List<HrWageItem> mapList) throws DataAccessException;

	/**
	 * 更新前检查（有结果：名称被占用）
	 * @param paramMap
	 * @return
	 */
	public List<Map<String, Object>> queryWageItemUpdateBefore(Map<String, Object> paramMap) throws DataAccessException;

	/**
	 * 更新工资项
	 * @param paramMap
	 */
	public void updateHrWageItem(Map<String, Object> paramMap) throws DataAccessException;
	
	/**
	 * 通过主键取一个工资项
	 * @param paramMap
	 * @return
	 */
	public HrWageItem findHrWageItemByPK(Map<String, Object> paramMap) throws DataAccessException;

	/**
	 * 通过外键删除薪资方案与职工分类的关联关系
	 * @param paramMap
	 * @throws DataAccessException
	 */
	public void deleteHrWagePlanKindByFK(Map<String, Object> paramMap) throws DataAccessException;
	/** 通过外键删除薪资方案与职工分类的关联关系(批量) */
	public void deleteHrWagePlanKindByFKBatch(List<Map<String, Object>> delList) throws DataAccessException;
	/**
	 * 添加薪资方案与职工分类的关联关系
	 * @param list
	 * @throws DataAccessException
	 */
	public void addHrWagePlanKindBatch(List<Map<String, Object>> list) throws DataAccessException;

	/**
	 * 取薪资方案关联的职工分类
	 * @param paramMap
	 * @return
	 * @throws DataAccessException
	 */
	public List<HrWagePlanKind> findHrWagePlanKindByFK(Map<String, Object> paramMap) throws DataAccessException;

	/**
	 * 取工资项类型(提供下拉选用)
	 * @param paramMap
	 * @return
	 */
	public List<Map<String,Object>> selectAccWageItemType(Map<String, Object> paramMap) throws DataAccessException;

	/**
	 * 复制前的检查
	 * @param paramMap
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryCopyHrWageItemBefore(Map<String, Object> paramMap) throws DataAccessException;

	/**
	 * 取薪资方案下工资项(提供下拉选用)
	 * @param paramMap
	 * @return
	 */
	public List<Map<String, Object>> selectWageItem(Map<String, Object> paramMap) throws DataAccessException;

	/**
	 * 查工资项性质
	 * @param queryMap
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryWageItemNature(Map<String, Object> queryMap) throws DataAccessException;
	
	/**
	 * 查工资项性质
	 * @param queryMap
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryWageItemType(Map<String, Object> queryMap) throws DataAccessException;

	/**
	 * 查询已经存在的工资项
	 * @param list
	 * @return
	 * @throws DataAccessException
	 */
	public List<HrWageItem> queryHrWageItemsExists(List<Map<String, Object>> list) throws DataAccessException;

	/**
	 * 添加工资项
	 * @param list
	 * @throws DataAccessException
	 */
	public void addHrWageItemBatch(List<Map<String, Object>> list) throws DataAccessException;

	/**
	 * 查询薪资方案
	 * @param paramMap
	 * @return
	 * @throws DataAccessException
	 */
	public List<HrWagePlan> queryHrWagePlan(Map<String, Object> paramMap) throws DataAccessException;

	/**
	 * 查询工资项
	 * @param paramMap
	 * @return
	 * @throws DataAccessException
	 */
	public List<HrWageItem> queryHrWageItem(Map<String, Object> paramMap) throws DataAccessException;

}
