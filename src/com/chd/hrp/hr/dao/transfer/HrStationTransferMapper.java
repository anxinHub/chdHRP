package com.chd.hrp.hr.dao.transfer;

import java.util.List;
import java.util.Map;

import org.apache.poi.ss.formula.functions.T;

import com.chd.base.SqlMapper;
import com.chd.hrp.hr.entity.transfer.HrStationTransfer;

/**
 * 调岗管理
 * 
 * @author Administrator
 *
 */
public interface HrStationTransferMapper extends SqlMapper {
	/**
	 * 调岗增加查询数据重复
	 * 
	 * @param entityMap
	 * @return
	 */
	List<HrStationTransfer> queryStationTransferById(Map<String, Object> entityMap);

	/**
	 * 调岗删除
	 * 
	 * @param entityList
	 */
	void deleteStationTransfer(List<HrStationTransfer> entityList);

	/**
	 * 调岗审核
	 * 
	 * @param entityList
	 * @return
	 */
	String auditStationTransfer(List<Map<String, Object>> entityList);

	/**
	 * 调岗销审
	 * 
	 * @param entityList
	 * @return
	 */
	String reAuditStationTransfer(List<Map<String, Object>> entityList);
    /**
     * 查询职务
     * @param entityMap
     * @return
     */
	List<T> queryDuty(Map<String, Object> entityMap);
    /**
     * 查询岗位
     * @param entityMap
     * @return
     */
	List<T> queryStation(Map<String, Object> entityMap);
    /**
     * 查询部门
     * @param entityMap
     * @return
     */
	List<T> queryDept(Map<String, Object> entityMap);
	/**
     * 查询职工类别
     * @param entityMap
     * @return
     */
	List<T> queryEmpType(Map<String, Object> entityMap);
	/**
     * 查询职工
     * @param entityMap
     * @return
     */
	List<T> queryEmp(Map<String, Object> entityMap);
    
}
