package com.chd.hrp.hr.dao.medicalmanagement;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import com.chd.base.SqlMapper;
import com.chd.hrp.hr.entity.medicalmanagement.HrMeetDiagRecord;

public interface HrMeetDiagRecordMapper extends SqlMapper{
    /**
     * 删除全部全院大会诊记录
     * @param entityMap
     */
	void deletehMeetDiagRecord(Map<String, Object> entityMap);
    /**
     * 删除选中全院大会诊记录
     * @param entityList
     * @param mapVo 
     */
	void deleteMeetDiagRecord(@Param(value="list") List<HrMeetDiagRecord> entityList,@Param(value="map") Map<String, Object> mapVo);
	/**
	 * 查询全院大会诊
	 * @param entityMap
	 * @return
	 */
	List<Map<String, Object>> queryMeetDiagApp(Map<String, Object> entityMap);
	/**
	 * 查询全院大会诊
	 * @param entityMap
	 * @return
	 */
	List<Map<String, Object>> queryMeetDiagApp(Map<String, Object> entityMap,RowBounds rowBounds);
	/**
	 * 提交
	 * @param hrMeetDiagRecord
	 */
	void confirmMeetDiagRecord(HrMeetDiagRecord hrMeetDiagRecord);
	/**
	 * 撤回
	 * @param hrMeetDiagRecord
	 */
	void reConfirmMeetDiagRecord(HrMeetDiagRecord hrMeetDiagRecord);
	/**
	 * 打印
	 * @param entityMap
	 * @return
	 */
	List<Map<String, Object>> queryRecordByPrint(Map<String, Object> entityMap);
	/**
	 * 添加查询
	 * @param hrMeetDiagRecord
	 * @return
	 */
	HrMeetDiagRecord queryByCodeAdd(HrMeetDiagRecord hrMeetDiagRecord);
	/**
	 * 添加
	 * @param hrMeetDiagRecord
	 * @return
	 */
	int addMeetDiagRecord(HrMeetDiagRecord hrMeetDiagRecord);
	void addBatchMeetDiagRecord(@Param(value="list") List<HrMeetDiagRecord> alllistVo, @Param(value="map") Map<String, Object> entityMap);

}
