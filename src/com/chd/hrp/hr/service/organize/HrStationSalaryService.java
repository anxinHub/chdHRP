package com.chd.hrp.hr.service.organize;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.hr.entity.orangnize.HrStationSalary;

public interface HrStationSalaryService {
    /**
     * 添加岗位薪资标准
     * @param mapVo
     * @return
     */
	String addStationSalary(Map<String, Object> mapVo)throws DataAccessException;
    /**
     * 修改查询
     * @param mapVo
     * @return
     */
	HrStationSalary queryByCodeStationSalary(Map<String, Object> mapVo)throws DataAccessException;
	/**
	 * 修改岗位薪资标准
	 * @param mapVo
	 * @return
	 */
	String updateStationSalary(Map<String, Object> mapVo)throws DataAccessException;
	/**
	 * 删除岗位薪资标准
	 * @param listVo
	 * @return 
	 */
	String deleteBatchStationSalary(List<HrStationSalary> listVo)throws DataAccessException;
	/**
	 * 查询岗位薪资标准
	 * @param page
	 * @return
	 */
	String queryHrStationSalary(Map<String, Object> page)throws DataAccessException;
	/**
	 * 查询左侧菜单
	 * @param mapVo
	 * @return
	 */
	String queryStationSalaryDefTree(Map<String, Object> mapVo)throws DataAccessException;

}
