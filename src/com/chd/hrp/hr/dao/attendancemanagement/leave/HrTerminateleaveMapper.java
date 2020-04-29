package com.chd.hrp.hr.dao.attendancemanagement.leave;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.hr.entity.attendancemanagement.leave.HrTerminateleave;

/**
 * 职工销假申请表
 * @author Administrator
 *
 */
public interface HrTerminateleaveMapper  extends SqlMapper{
     /**
      * 添加查询
      * @param entityMap
      * @return
      */
	List<HrTerminateleave> queryTerminateleaveById(Map<String, Object> entityMap);
	/**
	 * 提交职工销假申请
	 * @param entityList
	 * @return
	 */
	int confirmTerminateleave(List<HrTerminateleave> entityList);
	/**
	 * 取消提交职工销假申请
	 * @param entityMap
	 * @return
	 */
	String reConfirmTerminateleave(List<HrTerminateleave> entityMap);
	/**
	 * 查询休假申请编码(已核定的休假申请并且没有添加过销假申请的)
	 * @param entityMap
	 * @return
	 */
	List<Map<String, Object>> queryApplyCode(Map<String, Object> entityMap);
	Map<String, Object>  queryApplying(Map<String, Object> entityMap);
	Map<String, Object> queryByCodeTerminateleave(Map<String, Object> entityMap);
	void auditTerminateleave(List<Map> saveList);
	/**
	 * 修改状态
	 * @param updatList
	 * @param entityMap
	 * @throws DataAccessException
	 */
	//public void updateXJState(@Param(value="list") List<Map<String, Object>> updatList, @Param(value="map") Map<String, Object> entityMap) throws DataAccessException;
	public void updateXJState(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * 休假管理表增加销假数据
	 * @param entityMap
	 * @throws DataAccessException
	 */
	public void insertXJHrEmpHoliday(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * 休假管理表删除销假数据
	 * @param entityMap
	 * @throws DataAccessException
	 */
	public void deleteXJHrEmpHoliday(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * 删除
	 * @param entityMap
	 * @throws DataAccessException
	 */
	public void deleteTerminateleave(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * 查询
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryXJHrEmpHoliday(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * 修改额度
	 * @param entityList
	 * @throws DataAccessException
	 */
	public void updateBatchXJHrEmpHoliday(List<Map<String, Object>> entityList) throws DataAccessException;
	public void updateXJHrEmpHoliday(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * 打印
	 */
	public  List<Map<String, Object>> queryTerminateleaveByPrint(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * 
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	List<Map<String, Object>> queryXXjInfo(Map<String, Object> entityMap) throws DataAccessException;

	/** 
	 * 此方法用于【考勤管理-休假管理-审核】页面查询，<br>
	 * 与query()不同处是：query2()查询方法执行的sql把休假申请编号与销假申请编号的列别名做了颠倒，<br>
	 * 供查询时分类（休假、销假）显示对应的申请编号<br>
	 * @author yangyunfei
	 */
	public List<?> query2(Map<String,Object> entityMap) throws DataAccessException;
	/** 
	 * 此方法用于【考勤管理-休假管理-审核】页面查询，<br>
	 * 与query()不同处是：query2()查询方法执行的sql把休假申请编号与销假申请编号的列别名做了颠倒，<br>
	 * 供查询时分类（休假、销假）显示对应的申请编号<br>
	 * @author yangyunfei
	 */
	public List<?> query2(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	/**
	 * 通过休假申请编号查它们的销假申请
	 * @param map
	 * @param list
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryXxjapplyByXjapplyCode(@Param(value = "map") Map<String, Object> map, 
			@Param(value = "list") List<String> list) throws DataAccessException;
	
}
