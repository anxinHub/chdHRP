package com.chd.hrp.hr.dao.attendancemanagement.leave;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.hr.entity.attendancemanagement.leave.HrXJED;
import com.chd.hrp.hr.entity.attendancemanagement.leave.HrXJEDEmp;
/**
 * 休假额度设置
 * @author Administrator
 *
 */ 
public interface HrXJEDMapper  extends SqlMapper{
    
    /**
     * 修改
     * @param listVo
     * @return
     */
	int updateXJED(@Param(value="list") List<Map<String, Object>> saveList,@Param(value="map") Map<String,Object> map);
	
	List<Map<String, Object>> queryAttendCode(Map<String, Object> entityMap);

	/**
	 * 查询人员带分页
	 * @param entityMap
	 * @return
	 */
	List<HrXJED> queryXJEDDetail(Map<String, Object> entityMap,RowBounds rowBounds);
	/**
	 * 查询人员不带分页
	 * @param entityMap
	 * @return
	 */
	List<HrXJED> queryXJEDDetail(Map<String, Object> entityMap);
	
	/**
	 * 动态表头休假类型
	 * @param entityMap
	 * @return
	 */
	List<Map<String, Object>> queryAttendItem(Map<String, Object> entityMap);
	
	
	/**
	 * 导入增加
	 * @param entityList
	 * @param map
	 */
	void addXJED(@Param(value="list") List<Map<String, Object>> entityList,@Param(value="map") Map<String,Object> map);
	/**
	 * 打印 
	 * @param entityMap
	 * @return
	 */
	List<Map<String, Object>> queryXJEDByPrint(Map<String, Object> entityMap);
	
	
	
	/**
	 * 初始化增加
	 * @param entityMap
	 * @throws DataAccessException
	 */
	void addImpXJED(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * 批量查询员工
	 * @param entityMap
	 * @return
	 */
	List<Map<String, Object>> queryEmpByDeptId(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * 查询职工考勤项目信息
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	List<Map<String, Object>> queryAttendEmpByYear(Map<String, Object> entityMap) throws DataAccessException;

	//查询全院控制的休假项目额度
	public String queryEdByItem(Map<String, Object> entityMap) throws DataAccessException;
	
	//根据休假项目批量修改额度
	public int updateEdByItem(Map<String, Object> entityMap) throws DataAccessException;
    //个人限额控制的考勤项目
	List<Map<String, Object>> queryAttendItemEd(Map<String, Object> entityMap);

}
