package com.chd.hrp.hr.dao.salarymanagement.empWage;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.hr.entity.salarymanagement.empWage.HrWage;

public interface HrWageCheckComputeMapper extends SqlMapper{
	
	/** 保存职工薪资 */
	public void saveHrWageBatch(@Param(value = "map") Map<String, Object> map, @Param(value = "list") List<Map> list) throws DataAccessException;
	
	/**
	 * 删除已经存在的职工薪资
	 * @param paramMap
	 * @return
	 */
	public void deleteEmpHrWageByEmpIds(Map<String, Object> paramMap) throws DataAccessException;

	/**
	 * 删除职工薪资
	 * @param list
	 * @throws DataAccessException
	 */
	public void deleteHrWageBatch(List<HrWage> list) throws DataAccessException;
	
	/**
	 * 通过薪资方案关联的职工分类查职工
	 * @param paramMap
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryEmpByWagePlanEmpKind(Map<String, Object> paramMap) throws DataAccessException;
     /**
      * 计算
      * @param entityMap
      * @throws DataAccessException
      */
	public void collectEmpHrWage(Map<String, Object> entityMap)throws DataAccessException;
     /**
      * 生成
      * @param paramMap
      * @throws DataAccessException
      */
	public void generateEmpWage(Map<String, Object> paramMap)throws DataAccessException;
     /**
      * 删除薪资核算表数据
      * @param paramMap
      * @throws DataAccessException
      */
	public void deleteEmpWage(Map<String, Object> paramMap)throws DataAccessException;

	public Map<String, Object> queryHosEmpId(Map<String, Object> saveMap)throws DataAccessException;
    /**
     * 查询是否存在工资数据
     * @param saveMap
     * @return
     * @throws DataAccessException
     */
	public Map<String, Object> queryHrWage(Map<String, Object> saveMap)throws DataAccessException;

	public void addBatchWage(@Param(value = "list") List<Map<String, Object>> saveList)throws DataAccessException;

	public void updateBatchWage(@Param(value = "list") List<Map<String, Object>> saveList)throws DataAccessException;

	public List<Map<String, Object>> wageItemSelect(Map<String, Object> entityMap)throws DataAccessException;

	public void updateItem(Map<String, Object> entityMap)throws DataAccessException;

	public void generateEmpInsur(Map<String, Object> paramMap)throws DataAccessException;

	public void generateEmpFund(Map<String, Object> paramMap)throws DataAccessException;
}
