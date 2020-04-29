package com.chd.hrp.hr.service.transfer;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.hr.entity.transfer.HrDeptTransfer;
/**
 * 部门调动
 * @author Administrator
 *
 */
public interface HrDeptTransferService {
    /**
     * 添加部门
     * @param mapVo
     * @return
     */
	String addHrDeptTransfer(Map<String, Object> mapVo)throws DataAccessException;
    /**
     * 修改部门
     * @param mapVo
     * @return
     */
	String updateHrDeptTransfer(Map<String, Object> mapVo)throws DataAccessException;
	/**
	 * 删除部门管理
	 * @param mapVo
	 * @return
	 */
	String deleteHrDeptTransfer(Map<String, Object> mapVo)throws DataAccessException;
	/**
	 * 查询部门管理
	 * @param page
	 * @return
	 */
	String queryHrDeptTransfer(Map<String, Object> page)throws DataAccessException;
	/**
	 * 审核
	 * @param listVo
	 * @return
	 */
	String auditDeptTransfer(List<HrDeptTransfer> listVo)throws DataAccessException;
	/**
	 * 销审
	 * @param listVo
	 * @return
	 */
	String reAuditdepttransfer(List<HrDeptTransfer> listVo)throws DataAccessException;
	/**
	 * 跳转修改
	 * @param mapVo
	 * @return
	 */
	HrDeptTransfer queryByCodeDeptTransfer(Map<String, Object> mapVo)throws DataAccessException;
	/**
	 * 
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	String queryHosEmpDept(Map<String, Object> mapVo)throws DataAccessException;
	   /**
	 * 打印
	 * 
	 * @param page
	 * @return
	 * @throws DataAccessException
	 */
   List<Map<String,Object>> queryDeptTransByPrint(Map<String, Object> page)throws DataAccessException;
}
