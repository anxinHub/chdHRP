package com.chd.hrp.hr.dao.attendancemanagement.overtime;

import java.util.List;
import java.util.Map;

import com.chd.base.SqlMapper;

import net.shibboleth.utilities.java.support.security.DataExpiredException;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

/**
 * 期初加班登记
 * @author Administrator
 *
 */
public interface HrOvertimeMapper  extends SqlMapper{
	//查询
	public List<Map<String, Object>> queryOvertime(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	public List<Map<String, Object>> getAttendCodeOfJx(Map<String, Object> entityMap) throws DataAccessException;
	//打印
	public List<Map<String, Object>> queryOvertimeByPrint(Map<String, Object> entityMap) throws DataAccessException;
	
	//删除
	public void deleteHrOvertime(Map<String, Object> entityMap) throws DataAccessException;
	
	//提交、取消提交
	public int updateStateByConfirm(Map<String, Object> entityMap) throws DataAccessException;
	
	//审核、消审
	public int updateStateByCheck(Map<String, Object> entityMap) throws DataAccessException;
	
	//退回
	public int updateStateByBack(Map<String, Object> entityMap) throws DataAccessException;
	
	//取出为积休的考勤项目和积休天数
	public List<Map<String,Object>> queryAttendCodeByJx(Map<String, Object> entityMap) throws DataAccessException;
	
	//查询加班数据
	public List<Map<String, Object>> queryOvertimeData(Map<String, Object> entityMap) throws DataAccessException;

	//查询积休天数
	public Map<String,Object> queryHosJxNum(Map<String, Object> entityMap) throws DataAccessException;
	
	//根据单号查询员工的账表信息
	public List<Map<String, Object>> queryBanlaceByOverCode(Map<String, Object> entityMap) throws DataAccessException;
	
	//状态校验（是否存在不为该状态的数据）
	public int notExistsByState(Map<String, Object> map) throws DataExpiredException;

	//批量更新(用于审核)
	public int updateBatchStateByCheck(@Param(value="list") List<Map<String, Object>> list, @Param(value="map") Map<String, Object> map) throws DataExpiredException;
}
