package com.chd.hrp.hr.service.attendancemanagement.overtime;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.hr.entity.attendancemanagement.overtime.HrOvertime;


/**
 * 期初加班登记
 * @author Administrator
 *
 */
public interface HrOvertimeService {
	/**
	 * 增加期初加班登记
	 * @param mapVo
	 * @return
	 */

	public Map<String, Object> addOvertime(Map<String, Object> mapVo)throws DataAccessException;
    /**
     * 修改页面跳转查询期初加班登记
     * @param mapVo
     * @return
     */
	public Map<String, Object> queryByCodeOvertime(Map<String, Object> mapVo)throws DataAccessException;
	/**
	 * 修改期初加班登记
	 * @param mapVo
	 * @return
	 */
	public Map<String, Object> updateOvertime(Map<String, Object> mapVo)throws DataAccessException;
	/**
	 * 删除期初加班登记
	 * @param listVo
	 */
	public Map<String, Object> deleteOvertime(Map<String, Object> mapVo)throws DataAccessException;
	/**
	 * 查询期初加班登记
	 * @param page
	 * @return
	 */
	public String queryOvertime(Map<String, Object> page)throws DataAccessException;
	/**
	 * 提交
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public Map<String, Object> confirmOvertime(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * 撤回
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public Map<String, Object> reConfirmOvertime(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * 打印
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryOvertimeByPrint(Map<String, Object> entityMap) throws DataAccessException;

}
