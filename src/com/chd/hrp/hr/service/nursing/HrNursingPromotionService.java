package com.chd.hrp.hr.service.nursing;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.hr.entity.nursing.HrNursingPromotion;
import com.chd.hrp.hr.entity.nursing.HrPromotionLeave;


/**
 * 护理晋级申请
 * @author Administrator
 *
 */
public interface HrNursingPromotionService {
	/**
	 * 增加护理晋级申请
	 * @param mapVo
	 * @return
	 */

	String addNursingPromotion(Map<String, Object> mapVo)throws DataAccessException;
    /**
     * 修改页面跳转查询护理晋级申请
     * @param mapVo
     * @return
     */
	HrNursingPromotion queryByCodeNursingPromotion(Map<String, Object> mapVo)throws DataAccessException;
	/**
	 * 修改护理晋级申请
	 * @param mapVo
	 * @return
	 */
	String updateNursingPromotion(Map<String, Object> mapVo)throws DataAccessException;
	/**
	 * 删除护理晋级申请
	 * @param listVo
	 */
	String deleteNursingPromotion(List<HrNursingPromotion> listVo)throws DataAccessException;
	/**
	 * 查询护理晋级申请
	 * @param page
	 * @return
	 */
	String queryNursingPromotion(Map<String, Object> page)throws DataAccessException;
	/**
	 * 打印
	 * @param page
	 * @return
	 * @throws DataAccessException
	 */
	List<Map<String,Object>> queryNursingPromotionByPrint(Map<String, Object> page)throws DataAccessException;
	/**
	 * 提交护理晋级申请（单条）
	 * @param hrNursingPromotion
	 * @return
	 */
	String confirmNursingPromotion(HrNursingPromotion hrNursingPromotion)throws DataAccessException;
	/**
	 * 提交护理晋级申请(批量)
	 * @param hrNursingPromotion
	 * @return
	 */
	String confirmNursingPromotionBatch(List<HrNursingPromotion> list) throws DataAccessException;
	/**
	 * 取消提交护理晋级申请(单条)
	 * @param hrNursingPromotion
	 * @return
	 */
	String reConfirmNursingPromotion(HrNursingPromotion hrNursingPromotion)throws DataAccessException;
	/**
	 * 取消提交护理晋级申请(批量)
	 * @param hrNursingPromotion
	 * @return
	 */
	String reConfirmNursingPromotionBatch(List<HrNursingPromotion> list) throws DataAccessException;
	/**
	 * 查询员工明细信息
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	String queryHosEmpDetail(Map<String, Object> mapVo)throws DataAccessException;
	/**
	 * 查询级别
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	String queryLevel(Map<String, Object> mapVo)throws DataAccessException;
	/**
	 * 查询三年资料
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	String queryAttend(Map<String, Object> mapVo)throws DataAccessException;
	/**
	 * 添加近三年资料
	 * @param listVo
	 * @return
	 * @throws DataAccessException
	 */
	String addaddNursingPromotion(List<HrPromotionLeave> listVo)throws DataAccessException;
	/**
	 * 删除近三年资料
	 * @param listVo
	 * @return
	 * @throws DataAccessException
	 */
	String deletePromotionLeave(List<HrNursingPromotion> listVo)throws DataAccessException;

}
