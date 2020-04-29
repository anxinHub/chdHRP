package com.chd.hrp.hr.dao.transfer;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.apache.poi.ss.formula.functions.T;

import com.chd.base.SqlMapper;
import com.chd.hrp.hr.entity.transfer.HrDeptTransfer;

/**
 * 部门调动
 * @author Administrator
 *
 */
public interface HrDeptTransferMapper extends SqlMapper{
    /**
     * 部门调动增加查询
     * @param entityMap
     * @return
     */
	List<HrDeptTransfer> queryDeptTransferById(Map<String, Object> entityMap);
    /**
     * 删除部门调动
     * @param entityMap
     */
	void deleteDeptTransfer(Map<String, Object> entityMap);
	/**
	 * 审核部门调动
	 * @param list
	 * @return
	 */
	void auditDeptTransfer(@Param(value="list")List<HrDeptTransfer> list,@Param(value="map") Map<String, Object> entityMap);
	/**
	 * 销审部门调动
	 * @param entityMap
	 * @return
	 */
	void reAuditDeptTransfer(Map<String, Object> entityMap);
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
	/**
	 * 查询职工信息
	 * @param entityMap
	 * @return
	 */
	Map<String, Object> queryHosEmpDept(Map<String, Object> entityMap);
	/**
	 * 打印
	 * @param entityMap
	 * @return
	 */
	List<Map<String, Object>> queryDeptTransByPrint(Map<String, Object> entityMap);
	//修改人员基本情况
	void updateEmpTable(@Param(value="map")Map<String, Object> entityMap,@Param(value="list")List<Map<String, Object>> listq);
	void updateEmpDictTable(@Param(value="map")Map<String, Object> entityMap,@Param(value="list")List<Map<String, Object>> listq);
	void updateAttend(@Param(value="map")Map<String, Object> entityMap,@Param(value="list")List<Map<String, Object>> listq);
	void updateAttendManage(@Param(value="map")Map<String, Object> entityMap,@Param(value="list")List<Map<String, Object>> listq);
	void updateAttendD(@Param(value="map")Map<String, Object> entityMap,@Param(value="list")List<Map<String, Object>> listq);
}
