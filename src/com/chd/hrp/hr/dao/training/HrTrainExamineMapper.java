package com.chd.hrp.hr.dao.training;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.hr.entity.training.exam.HrBukao;
import com.chd.hrp.hr.entity.training.exam.HrExam;
import com.chd.hrp.hr.entity.training.exam.HrExamResult;
import com.chd.hrp.hr.entity.training.exam.HrTrainCert;
import com.chd.hrp.hr.entity.training.exam.HrTrainEmpCert;

public interface HrTrainExamineMapper extends SqlMapper {

	/**
	 * 通过主键查一个培训考试安排
	 */
	public HrExam queryExamByPK(Map<String, Object> paraMap) throws DataAccessException;
	
	/**
	 * 通过主键查一个培训补考安排
	 */
	public HrBukao queryBukaoByPK(Map<String, Object> paraMap) throws DataAccessException;
	
	/**
	 * 通过主键查一个培训证书
	 */
	public HrTrainCert queryTrainCertByPK(Map<String, Object> paraMap) throws DataAccessException;

	/**
	 * 保存一个培训考试安排
	 */
	public void insertTrainExam(Map<String, Object> paraMap) throws DataAccessException;

	/**
	 * 查询考试成绩
	 */
	public List<HrExamResult> queryExamResult(Map<String, Object> paraMap) throws DataAccessException;

	/**
	 * 查询考试成绩（带分页）
	 */
	public List<HrExamResult> queryExamResult(Map<String, Object> paraMap, RowBounds rowBounds) throws DataAccessException;

	/**
	 * 添加考试成绩（批量）
	 */
	public int insertExamResultBatch(List<Map<String, Object>> list) throws DataAccessException;

	/**
	 * 删除考试成绩（批量）
	 */
	public void deleteExamResultBatch(List<Map<String, Object>> list) throws DataAccessException;

	/**
	 * 查询补考成绩
	 */
	public List<HrExamResult> queryBukaoEmp(Map<String, Object> paraMap) throws DataAccessException;

	/**
	 * 查询补考成绩(分页)
	 */
	public List<HrExamResult> queryBukaoEmp(Map<String, Object> paraMap, RowBounds rowBounds) throws DataAccessException;

	/**
	 * 删除补考成绩（批量）
	 */
	public void deleteBukaoEmpBatch(List<Map<String, Object>> list) throws DataAccessException;

	/**
	 * 添加补考成绩（批量）
	 */
	public int insertBukaoEmpBatch(List<Map<String, Object>> list) throws DataAccessException;

	/**
	 * 添加培训证书
	 */
	public void insertTrainCert(Map<String, Object> paraMap) throws DataAccessException;

	/**
	 * 查询职工培训证书
	 */
	public List<HrTrainEmpCert> queryTrainEmpCert(Map<String, Object> paraMap) throws DataAccessException;

	/**
	 * 查询职工培训证书（分页）
	 */
	public List<HrTrainEmpCert> queryTrainEmpCert(Map<String, Object> paraMap, RowBounds rowBounds) throws DataAccessException;

	/**
	 * 添加职工培训证书（批量）
	 */
	public void insertTrainEmpCertBatch(List<Map<String, Object>> list) throws DataAccessException;

	/**
	 * 删除职工培训证书（批量）
	 */
	public void deleteTrainEmpCertBatch(List<Map<String, Object>> list) throws DataAccessException;

	/**
	 * 保存补考安排
	 */
	public void insertTrainBukao(Map<String, Object> paraMap) throws DataAccessException;

	/**
	 * 删除培训证书
	 */
	public void deleteTrainCert(Map<String, Object> paraMap) throws DataAccessException;

	/**
	 * 通过职工工号查询培训对象, ltrim(职工工号, '0')
	 */
	public Map<String, Object> queryTrainObjByEmpCode(Map<String, Object> save);

	/**
	 * 生成培训人员证书
	 */
	public void generateTrainExamCert(Map<String, Object> paraMap) throws DataAccessException;

	/**
	 * 删除考试安排
	 */
	public void deleteTrainExam(Map<String, Object> paraMap) throws DataAccessException;

	/**
	 * 删除补考安排
	 */
	public void deleteTrainBukao(Map<String, Object> paraMap) throws DataAccessException;

	/**
	 * 更新一条考试成绩
	 */
	public int updateExamResult(Map<String, Object> paraMap) throws DataAccessException;
	
	/**
	 * 批量更新考试成绩
	 */
	public int updateExamResultBatch(List<Map<String, Object>> list) throws DataAccessException;

	/**
	 * 更新补考记录 单个
	 */
	public int updateBukaoEmp(Map<String, Object> paraMap) throws DataAccessException;
	
	/**
	 * 更新补考记录 批量
	 */
	public int updateBukaoEmpBatch(List<Map<String, Object>> list) throws DataAccessException;
	
	/**
	 * 更新职工培训证书 单个
	 */
	public int updateTrainEmpCert(Map<String, Object> paraMap) throws DataAccessException;
	
	/**
	 * 更新职工培训证书 批量
	 */
	public int updateTrainEmpCertBatch(List<Map<String, Object>> list) throws DataAccessException;

	/**
	 * 更新补考安排
	 */
	public void updateTrainBukao(Map<String, Object> paraMap) throws DataAccessException;

	/**
	 * 按不合格的考试成绩批量添加补考记录
	 */
	public void generateBukaoEmpBatch(Map<String, Object> paraMap) throws DataAccessException;

	/**
	 *  查询考试结果、与缺考职工数据
	 */
	public List<HrExamResult> queryExamResultEmpData(Map<String, Object> paraMap) throws DataAccessException;
	
	/**
	 *  查询考试结果、与缺考职工数据（分页）
	 */
	public List<HrExamResult> queryExamResultEmpData(Map<String, Object> paraMap, RowBounds rowBounds) throws DataAccessException;

	/**
	 * 查考试成绩、补考记录  职工数据
	 */
	public List<HrExamResult> queryExamBukaoResultEmpData(Map<String, Object> paraMap) throws DataAccessException;

	/**
	 * 查考试成绩、补考记录  职工数据（分页）
	 */
	public List<HrExamResult> queryExamBukaoResultEmpData(Map<String, Object> paraMap, RowBounds rowBounds) throws DataAccessException;

	/**
	 * 取一个职工培训证书 by pk
	 */
	public HrTrainEmpCert getTrainEmpCert(Map<String, Object> paraMap) throws DataAccessException;

	/**
	 * 取电子证书所需要的职工培训证书数据 
	 */
	public List<Map<String, Object>> getTrainEmpCertMapDZB(Map<String, Object> paraMap) throws DataAccessException;

	/**
	 * 取上传证书的职工培训证书记录 by 培训计划
	 */
	public List<HrTrainEmpCert> queryTrainEmpCertExistsCertPath(Map<String, Object> paraMap) throws DataAccessException;

	/**
	 * 查证书最大编号
	 */
	public Map<String, Object> queryMaxCertCode(Map<String, Object> paraMap) throws DataAccessException;

	/**
	 * 查询没有证书编号的记录
	 */
	public List<HrTrainEmpCert> queryNotCertCodeData(Map<String, Object> paraMap) throws DataAccessException;

	/**
	 * 查询培训对象分布职工数据
	 */
	public List<HrExamResult> queryTrainObjEmpData(Map<String, Object> paraMap) throws DataAccessException;

	/**
	 * 查询培训对象分布职工数据（分页）
	 */
	public List<HrExamResult> queryTrainObjEmpData(Map<String, Object> paraMap, RowBounds rowBounds) throws DataAccessException;

	/**
	 * 生成考试记录
	 */
	public void generateExamResult(Map<String, Object> paraMap) throws DataAccessException;

	/**
	 * 查不合格和缺考的人
	 */
	public List<HrExamResult> queryNoPassAndNoExamEmpData(Map<String, Object> paraMap) throws DataAccessException;


}
