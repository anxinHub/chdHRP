package com.chd.hrp.hr.service.attendancemanagement.leave;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.hr.entity.attendancemanagement.leave.HrTerminateleave;


/**
 * 职工销假申请表
 * @author Administrator
 *
 */
public interface HrTerminateleaveService {
	/**
	 * 增加职工销假申请
	 * @param mapVo
	 * @return
	 */

	String addTerminateleave(Map<String, Object> mapVo)throws DataAccessException;
    /**
     * 修改页面跳转查询职工销假申请
     * @param mapVo
     * @return
     */
	Map<String, Object> queryByCodeTerminateleave(Map<String, Object> mapVo)throws DataAccessException;
	/**
	 * 修改职工销假申请
	 * @param mapVo
	 * @return
	 */
	String updateTerminateleave(Map<String, Object> mapVo)throws DataAccessException;
	
	/**
	 * 查询职工销假申请
	 * @param page
	 * @return
	 */
	String queryTerminateleave(Map<String, Object> page)throws DataAccessException;

	/**
	 * 查询休假申请编号
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	String queryApplyCode(Map<String, Object> mapVo)throws DataAccessException;
	/**
	 * 查询单号对应信息
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	String queryApplying(Map<String, Object> mapVo)throws DataAccessException;
	/**
	 * 提交
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	String confirmTerminateleave(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * 取消提交
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	String reConfirmTerminateleave(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * 删除职工销假申请
	 * @param listVo
	 */
	String deleteTerminateleave(Map<String, Object> entityMap)throws DataAccessException;
	/**
	 * 打印
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	List<Map<String, Object>> queryTerminateleaveByPrint(Map<String, Object> entityMap) throws DataAccessException;
}
