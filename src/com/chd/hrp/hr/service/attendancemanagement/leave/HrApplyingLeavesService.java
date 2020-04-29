package com.chd.hrp.hr.service.attendancemanagement.leave;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.hr.entity.attendancemanagement.leave.HrApplyingLeaves;


/**
 * 职工休假申请
 * @author Administrator
 *
 */
public interface HrApplyingLeavesService {
	/**
	 * 增加职工休假申请
	 * @param mapVo
	 * @return
	 */

	String addApplyingLeaves(Map<String, Object> mapVo)throws DataAccessException;
    /**
     * 修改页面跳转查询职工休假申请
     * @param mapVo
     * @return
     */
	HrApplyingLeaves queryByCodeApplyingLeaves(Map<String, Object> mapVo)throws DataAccessException;
	/**
	 * 修改职工休假申请
	 * @param mapVo
	 * @return
	 */
	String updateApplyingLeaves(Map<String, Object> mapVo)throws DataAccessException;
	
	/**
	 * 查询职工休假申请
	 * @param page
	 * @return
	 */
	String queryApplyingLeaves(Map<String, Object> page)throws DataAccessException;
	
	
	/**
	 * 查询休假额度
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	String queryAttendSum(Map<String, Object> mapVo)throws DataAccessException;
	/**
	 * 查询休假历史
	 * @param page
	 * @return
	 * @throws DataAccessException
	 */
	String queryHistroy(Map<String, Object> page)throws DataAccessException;
	/**
	 * 删除数据
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	String deleteApplyingLeaves(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * 提交休假申请
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	String confirmApplyingLeaves(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * 取消提交
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	String reConfirmApplyingLeaves(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * 作废
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	String cancelFApplyingLeaves(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * 打印
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	List<Map<String, Object>> queryApplyingLeavesByPrint(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * 计算休假天数
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	String countXJDays(Map<String, Object> map) throws DataAccessException;
	
	
}
