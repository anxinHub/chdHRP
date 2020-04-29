package com.chd.hrp.hr.dao.salarymanagement.wageItemCal;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.hr.entity.salarymanagement.wagePlanManage.HrWageItemCal;
/**
 * 
 * @author yang
 *
 */
public interface HrWageItemCalMapper extends SqlMapper {

	/**
	 * 通过主键查一个工资项取值方法
	 * @param paramMap
	 * @return
	 * @throws DataAccessException
	 */
	public HrWageItemCal findHrWageItemCalByPK(Map<String, Object> paramMap) throws DataAccessException;

	/**
	 * 职工分类下拉选，带“全部”选项
	 * @param paramMap
	 * @return
	 */
	public List<Map<String, Object>> selectEmpKind(Map<String, Object> paramMap);
	
	/**
	 * 执行函数
	 * @param paramMap
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryFun(Map<String, Object> paramMap) throws DataAccessException;

}
