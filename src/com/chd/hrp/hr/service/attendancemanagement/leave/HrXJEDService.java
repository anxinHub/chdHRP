package com.chd.hrp.hr.service.attendancemanagement.leave;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.hr.entity.attendancemanagement.leave.HrXJED;


public interface HrXJEDService {
    /**
     * 添加休假额度
     * @param mapVo
     * @return
     * @throws DataAccessException
     */
	String addXJED(Map<String, Object> mapVo) throws DataAccessException;
   
	/**
	 * 查询休假额度
	 * @param page
	 * @return
	 * @throws DataAccessException
	 */
	String queryXJED(Map<String, Object> page)throws DataAccessException;

	/**
	 * 保存数据
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	String saveXJED(Map<String, Object> mapVo)throws DataAccessException;
	/**
	 * 查询休假类型下拉框
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	String queryAttendCode(Map<String, Object> mapVo)throws DataAccessException;
	
	/**
	 * @Description 查询动态表头休假类型
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	String queryAttendItem(Map<String, Object> mapVo)throws DataAccessException;
	   /**
		 * 打印
		 * 
		 * @param page
		 * @return
		 * @throws DataAccessException
		 */
	List<Map<String,Object>> queryXJEDByPrint(Map<String, Object> page)throws DataAccessException;
    /**
     * 导入
     * @param mapVo
     * @return
     * @throws DataAccessException
     */
	String importXJED(Map<String, Object> mapVo)throws DataAccessException;
    /**
     * 初始化人员
     * @param mapVo
     * @return
     * @throws DataAccessException
     */
	String importEmp(Map<String, Object> mapVo)throws DataAccessException;
	/**
	 * 批量添加页面查询员工
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	String queryEmpByDeptId(Map<String, Object> entityMap) throws DataAccessException;
	
	//更新全院控制额度
	public Map<String, Object> updateXjedByItem(Map<String, Object> map) throws DataAccessException;
   /**
    * 查询个人控制方式额度
    * @param mapVo
    * @return
    * @throws DataAccessException
    */
	String queryItemGeRen(Map<String, Object> mapVo)throws DataAccessException;
}
