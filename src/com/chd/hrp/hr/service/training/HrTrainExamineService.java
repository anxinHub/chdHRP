package com.chd.hrp.hr.service.training;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.hr.entity.training.exam.HrBukao;
import com.chd.hrp.hr.entity.training.exam.HrExam;
import com.chd.hrp.hr.entity.training.exam.HrExamResult;
import com.chd.hrp.hr.entity.training.exam.HrTrainCert;
import com.chd.hrp.hr.entity.training.exam.HrTrainEmpCert;

public interface HrTrainExamineService {

	/**
	 * 查询培训计划树，返回json
	 */
	public String queryTrainPlanTreeJson(Map<String, Object> paraMap) throws DataAccessException;

	/**
	 * 查询一个培训计划关联的考试、补考、证书
	 */
	public Map<String, Object> queryTrainPlanRelationExam(Map<String, Object> paraMap) throws DataAccessException;

	/**
	 * 保存培训考试安排
	 */
	public String saveTrainExam(Map<String, Object> paraMap) throws DataAccessException;

	/**
	 * 查询培训考试成绩
	 */
	public String queryExamResult(Map<String, Object> paraMap) throws DataAccessException;

	/**
	 * 保存培训考试成绩
	 */
	public String saveExamResultBatch(List<HrExamResult> list) throws DataAccessException;

	/**
	 * 删除考试成绩
	 */
	public String deleteExamResultBatch(List<HrExamResult> list) throws DataAccessException;
	
	/**
	 * 考试成绩-更新
	 */
	public boolean updateExamResult(Map<String, Object> paraMap) throws DataAccessException;
	
	/**
	 * 考试成绩-批量更新
	 */
	boolean updateExamResultBatch(List<Map<String, Object>> list) throws DataAccessException;
	
	/**
	 * 考试成绩-查询全部
	 */
	public List<HrExamResult> queryExamResultList(Map<String, Object> paraMap) throws DataAccessException;

	/**
	 * 导入考试成绩
	 */
	public String importExamResult(Map<String, Object> paraMap) throws DataAccessException;

	/**
	 * 查询补考成绩
	 */
	public String queryBukaoEmp(Map<String, Object> paraMap) throws DataAccessException;

	/**
	 * 删除补考成绩
	 */
	public String deleteBukaoEmpBatch(List<HrExamResult> list) throws DataAccessException;

	/**
	 * 保存补考成绩
	 */
	public String saveBukaoEmpBatch(List<HrExamResult> list) throws DataAccessException;

	/**
	 * 导入补考成绩
	 */
	public String importBukaoEmp(Map<String, Object> paraMap) throws DataAccessException;
	
	/**
	 * 保存补考安排
	 */
	public String saveTrainBukao(Map<String, Object> paraMap) throws DataAccessException;
	
	/**
	 * 补考记录-更新
	 */
	public boolean updateBukaoEmp(Map<String, Object> paraMap) throws DataAccessException;
	
	/**
	 * 补考记录-批量更新
	 */
	public boolean updateBukaoEmpBatch(List<Map<String, Object>> list) throws DataAccessException;
	
	/**
	 * 补考记录-查询全部
	 */
	public List<HrExamResult> queryBukaoEmpList(Map<String, Object> paraMap) throws DataAccessException;

	/**
	 * 保存培训证书
	 */
	public String saveTrainCert(Map<String, Object> paraMap) throws DataAccessException;

	/**
	 * 查询职工培训证书
	 */
	public String queryTrainEmpCert(Map<String, Object> paraMap) throws DataAccessException;

	/**
	 * 保存职工培训证书
	 */
	public String saveTrainEmpCertBatch(List<HrTrainEmpCert> list) throws DataAccessException;

	/**
	 * 删除职工培训证书
	 */
	public String deleteTrainEmpCertBatch(List<HrTrainEmpCert> list) throws DataAccessException;

	/**
	 * 生成职工培训证书
	 */
	public String generateTrainExamCert(Map<String, Object> paraMap) throws DataAccessException;

	/**
	 * 职工培训证书-更新
	 */
	public boolean updateTrainEmpCert(Map<String, Object> paraMap) throws DataAccessException;

	/**
	 * 职工培训证书-批量更新
	 */
	boolean updateTrainEmpCertBatch(List<Map<String, Object>> list) throws DataAccessException;

	/**
	 * 职工培训证书-查询全部
	 */
	public List<HrTrainEmpCert> queryTrainEmpCertList(Map<String, Object> paraMap) throws DataAccessException;

	/**
	 * 取一个考试安排
	 */
	public HrExam getExam(Map<String, Object> paraMap) throws DataAccessException;

	/**
	 * 取一个补考安排
	 */
	public HrBukao getBukao(Map<String, Object> paraMap) throws DataAccessException;
	
	/**
	 * 取一个培训证书
	 */
	public HrTrainCert getTrainCert(Map<String, Object> paraMap) throws DataAccessException;

	/**
	 * 批量生成补考记录
	 */
	public String generateBukaoEmpBatch(Map<String, Object> paraMap) throws DataAccessException;

	/**
	 * 更新一个补考安排
	 */
	public boolean updateTrainBukao(Map<String, Object> paraMap) throws DataAccessException;

	/**
	 * 查询考试成绩记录职工数据
	 */
	public String queryExamResultEmpData(Map<String, Object> paraMap) throws DataAccessException;

	/**
	 * 查询考试、补考记录职工数据
	 */
	public String queryExamBukaoResultEmpData(Map<String, Object> paraMap) throws DataAccessException;

	/**
	 * 取一条职工培训证书记录
	 */
	public HrTrainEmpCert getTrainEmpCert(Map<String, Object> paraMap) throws DataAccessException;

	/**
	 * 打印电子证书，取职工、证书数据
	 */
	public Map<String, Object> generateDZBTrainEmpCert(Map<String, Object> paraMap) throws DataAccessException;
	
	/**
	 * 打印电子证书，取职工、证书数据
	 */
	public Map<String, Object> generateDZBTrainEmpCertBatch(Map<String, Object> paraMap) throws DataAccessException;

	/**
	 * 查询职工培训证书上传过的记录
	 */
	public List<HrTrainEmpCert> queryTrainEmpCertExistsCertPath(Map<String, Object> paraMap) throws DataAccessException;

	/**
	 * 取最大证书编号
	 */
	public String getMaxCertCode(Map<String, Object> paraMap) throws DataAccessException;

	/**
	 * 查询培训对象职工数据
	 */
	public String queryTrainObjEmpData(Map<String, Object> paraMap) throws DataAccessException;

	/**
	 * 生成考试记录
	 */
	public String generateExamResult(Map<String, Object> paraMap) throws DataAccessException;

	/**
	 * 查不合格和缺考的人的记录
	 */
	public List<HrExamResult> queryNoPassAndNoExamEmpData(Map<String, Object> paraMap) throws DataAccessException;

}
