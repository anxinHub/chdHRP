package com.chd.hrp.hr.dao.training;

import java.util.List;
import java.util.Map;

import com.chd.base.SqlMapper;

public interface HrTrainRecordMapper extends SqlMapper {

	Map<String, Object> queryTrainClass(Map<String, Object> mapVo);

	void addTrainClasss(Map<String, Object> mapVo);

	void updateTrainClasss(Map<String, Object> mapVo);

	void addTrainRecordTarget(List<Map<String, Object>> listVo);

	void deleteTrainRecordTarget(List<Map<String, Object>> listVo);

	List<Map<String, Object>> queryTrainRecordTarget(Map<String, Object> mapVo);

	List<Map<String, Object>> queryTrainRecordNotice(Map<String, Object> mapVo);

	List<Map<String, Object>> queryTrainObject(Map<String, Object> mapVo);

	void createTrainRecordNotice(Map<String, Object> trainClass);

	void deleteTrainRecordNotice(List<Map<String, Object>> listVo);

	void addTrainRecordNotice(List<Map<String, Object>> listVo);

	List<Map<String, Object>> queryTrainRecordSignIn(Map<String, Object> mapVo);

	void deleteTrainRecordSignIn(List<Map<String, Object>> listVo);

	void saveTrainRecordSignIn(List<Map<String, Object>> listVo);

	List<Map<String, Object>> queryTrainRecordCourseware(Map<String, Object> mapVo);

	void deleteTrainRecordCourseware(List<Map<String, Object>> listVo);

	void saveTrainRecordCourseware(List<Map<String, Object>> listVo);

	List<Map<String, Object>> queryTrainRecordBKEmp(Map<String, Object> mapVo);

	void deleteTrainRecordBKEmp(List<Map<String, Object>> listVo);

	void saveTrainRecordBKEmp(List<Map<String, Object>> listVo);

	Map<String, Object> queryTrainRecordBK(Map<String, Object> mapVo);
	
	void saveTrainRecordBK(Map<String, Object> mapVo);
	
	void updateTrainRecordBK(Map<String, Object> mapVo);

	Map<String, Object> queryEmpDetailInfo(Map<String, Object> mapVo);

	void saveTrainRecordPhoto(List<Map<String, Object>> saveList);

	void deleteTrainRecordCoursewareByPlanId(Map<String, Object> mapVo);

	List<Map<String, Object>> querySignInEmpSelect(Map<String, Object> mapVo);

	void deleteTrainRecordNoticeByPlanId(Map<String, Object> mapVo);

	void deleteTrainRecordTargetByPlanId(Map<String, Object> mapVo);

	void deleteTrainRecordSignInByPlanId(Map<String, Object> mapVo);

	void deleteTrainRecordBKEmpByPlanId(Map<String, Object> mapVo);

	List<Map<String, Object>> queryTrainRecordPhoto(Map<String, Object> mapVo);

	void deleteTrainRecordPhoto(Map<String, Object> mapVo);

	List<Map<String, Object>> queryNoticeModeSelect(Map<String, Object> mapVo);

	Integer queryEmpExists(Map<String, Object> saveMap);

	String queryEmpIdByCodeName(Map<String, Object> saveMap);

	List<Map<String, Object>> queryTrainRecordTargetForAdd(Map<String, Object> mapVo);

	List<Map<String, Object>> queryTrainObjectForSignIn(Map<String, Object> mapVo);

	List<Map<String, Object>> queryEmpSelectForRecord(Map<String, Object> mapVo);

}
