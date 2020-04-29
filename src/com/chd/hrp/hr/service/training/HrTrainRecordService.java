package com.chd.hrp.hr.service.training;

import java.util.List;
import java.util.Map;

public interface HrTrainRecordService {

	String saveTrainRecordCourse(Map<String, Object> mapVo);

	String saveTrainRecordTarget(List<Map<String, Object>> listVo);

	String deleteTrainRecordTarget(List<Map<String, Object>> listVo);

	String queryTrainRecordTarget(Map<String, Object> mapVo);
	
	List<Map<String, Object>> queryTrainRecordTargetPrint(Map<String, Object> mapVo);

	String queryTrainRecordNotice(Map<String, Object> mapVo);

	String createTrainRecordNotice(Map<String, Object> mapVo);

	String deleteTrainRecordNotice(List<Map<String, Object>> listVo);

	String saveTrainRecordNotice(List<Map<String, Object>> listVo);

	String queryTrainRecordSignIn(Map<String, Object> mapVo);

	String deleteTrainRecordSignIn(List<Map<String, Object>> listVo);

	String saveTrainRecordSignIn(List<Map<String, Object>> listVo);

	String queryTrainRecordCourseware(Map<String, Object> mapVo);

	String deleteTrainRecordCourseware(List<Map<String, Object>> listVo);

	String saveTrainRecordCourseware(List<Map<String, Object>> listVo);

	String queryTrainRecordBKEmp(Map<String, Object> mapVo);

	String deleteTrainRecordBKEmp(List<Map<String, Object>> listVo);

	String saveTrainRecordBKEmp(List<Map<String, Object>> listVo);

	String saveTrainRecordBK(Map<String, Object> mapVo);

	String queryEmpDetailInfo(Map<String, Object> mapVo);

	String saveTrainRecordPhoto(List<Map<String, Object>> saveList);

	String querySignInEmpSelect(Map<String, Object> mapVo);

	String queryTrainRecordClass(Map<String, Object> mapVo);

	String queryTrainRecordBK(Map<String, Object> mapVo);

	List<Map<String, Object>> queryTrainRecordPhoto(Map<String, Object> mapVo);

	String deleteTrainRecordPhoto(Map<String, Object> mapVo);

	String importDate(Map<String, Object> mapVo);

	String queryNoticeModeSelect(Map<String, Object> mapVo);

	List<Map<String, Object>> queryTrainRecordNoticePrint(Map<String, Object> mapVo);

	List<Map<String, Object>> queryTrainRecordSignInPrint(Map<String, Object> mapVo);

	List<Map<String, Object>> queryTrainRecordBKEmpPrint(Map<String, Object> mapVo);

	String queryTrainRecordTargetForAdd(Map<String, Object> mapVo);

	String createTrainRecordSignIn(Map<String, Object> mapVo);

	String createTrainRecordBKEmp(Map<String, Object> mapVo);

	String queryEmpSelectForRecord(Map<String, Object> mapVo);


}
