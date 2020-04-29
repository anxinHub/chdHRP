package com.chd.hrp.hr.service.transfer;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.hr.entity.transfer.HrStationTransfer;


public interface HrStationTransferService {
    /**
     * 添加调岗
     * @param mapVo
     * @return
     */
	String addHrStationTransfer(Map<String, Object> mapVo)throws DataAccessException;
    /**
     * 修改调岗
     * @param mapVo
     * @return
     */
	String updateHrStationTransfer(Map<String, Object> mapVo)throws DataAccessException;
	/**
	 * 删除调岗管理
	 * @param listVo
	 * @return
	 */
	String deleteHrStationTransfer(List<HrStationTransfer> listVo)throws DataAccessException;
	/**
	 * 查询调岗管理
	 * @param page
	 * @return
	 */
	String queryHrStationTransfer(Map<String, Object> page)throws DataAccessException;
	/**
	 * 审核
	 * @param listVo
	 * @return
	 */
	String auditStationTransfer(List<Map<String, Object>> listVo)throws DataAccessException;
	/**
	 * 销审
	 * @param listVo
	 * @return
	 */
	String reAuditStationTransfer(List<Map<String, Object>> listVo)throws DataAccessException;
	/**
	 * 跳转修改
	 * @param mapVo
	 * @return
	 */
	HrStationTransfer queryByCodeStationTransfer(Map<String, Object> mapVo)throws DataAccessException;

}
