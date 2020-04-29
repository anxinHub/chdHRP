package com.chd.hrp.hr.service.attendancemanagement.scheduling;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.hr.entity.attendancemanagement.scheduling.HrAttendClassType;

/**
 * 班次分类
 * @author Administrator
 *
 */
public interface HrAttendClassTypeService {
    /**
     * 修改跳转
     * @param mapVo
     * @return
     */
	HrAttendClassType queryByCode(Map<String, Object> mapVo) throws DataAccessException ;
    /**
     * 添加班次分类
     * @param mapVo
     * @return
     * @throws DataAccessException
     */
	String addAttendClassType(Map<String, Object> mapVo)throws DataAccessException;
    /**
     * 删除班次分类
     * @param listVo
     * @return
     * @throws DataAccessException
     */
	String deleteAttendClassType(List<HrAttendClassType> listVo)throws DataAccessException;
	/**
	 * 查询班次分类
	 * @param page
	 * @return
	 * @throws DataAccessException
	 */
	String queryAttendClassType(Map<String, Object> page)throws DataAccessException;
	/**
	 * 修改
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	String updateAttendClassType(Map<String, Object> mapVo)throws DataAccessException;
	  /**
		 * 打印
		 * 
		 * @param page
		 * @return
		 * @throws DataAccessException
		 */
	List<Map<String,Object>> queryAttendClassTypeByPrint(Map<String, Object> page)throws DataAccessException;

}
