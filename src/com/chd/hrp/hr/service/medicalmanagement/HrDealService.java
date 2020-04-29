package com.chd.hrp.hr.service.medicalmanagement;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlService;
import com.chd.hrp.hr.entity.medicalmanagement.HrDeal;

public interface HrDealService {
/**
     * 添加投诉调查处理
     * @param mapVo
     * @return
     */
	String addDeal(Map<String, Object> mapVo)throws DataAccessException;
/**
     * 修改页面跳转
     * @param mapVo
     * @return
     */
    HrDeal queryByCode(Map<String, Object> mapVo)throws DataAccessException;
    /**
     * 修改投诉纠纷调查处理
     * @param mapVo
     * @return
     */
    String updateDeal(Map<String, Object> mapVo)throws DataAccessException;
    /**
     * 删除投诉纠纷调查处理
     * @param listVo
     * @return
     * @throws DataAccessException
     */
	String deleteDeal(List<HrDeal> listVo)throws DataAccessException;
	/**
	 * 查询投诉纠纷调查处理
	 * @param page
	 * @return
	 * @throws DataAccessException
	 */
	String queryDeal(Map<String, Object> page)throws DataAccessException;
	/**
	 * 查询投诉纠纷调查处理明细
	 * @param page
	 * @return
	 * @throws DataAccessException
	 */
	String queryDealDetail(Map<String, Object> page)throws DataAccessException;
	/**
	 * 提交时候查询
	 * @param hrNursingPromotion
	 * @return
	 * @throws DataAccessException
	 */
	HrDeal queryByCodeCon(HrDeal hrdeal)throws DataAccessException;
	/**
	 * 提交（单条）
	 * @param hrNursingPromotion
	 * @return
	 * @throws DataAccessException
	 */
	String confirmDeal(HrDeal hrDeal)throws DataAccessException;
	/**
	 * 提交(批量)
	 * @param List<HrDeal>
	 * @return
	 * @throws DataAccessException
	 */
	String confirmDeal(List<HrDeal> list) throws DataAccessException;
	/**
	 * 提交(批量)
	 * @param List<HrDeal>
	 * @return
	 * @throws DataAccessException
	 */
	String confirmDealBatch(List<HrDeal> listVo) throws DataAccessException;
	/**
	 * 取消提交(单条)
	 * @param hrNursingPromotion
	 * @return
	 * @throws DataAccessException
	 */
	String reConfirmHrDeal(HrDeal hrdeal)throws DataAccessException;
	/**
	 * 取消提交（批量）
	 * @param List<HrDeal>
	 * @return
	 * @throws DataAccessException
	 */
	String reConfirmHrDealBatch(List<HrDeal> listVo) throws DataAccessException;
	/**
 * 打印
 * @param page
 * @return
 * @throws DataAccessException
 */
List<Map<String,Object>> queryDealByPrint(Map<String, Object> page)throws DataAccessException;
/**
 * 投诉纠纷调查住院号
 * @param mapVo
 * @return
 * @throws DataAccessException
 */
	String queryResearchInHosNo(Map<String, Object> mapVo)throws DataAccessException;
	/**
	 * 删除明细
	 * @param listVo
	 * @return
	 * @throws DataAccessException
	 */
String deleteDealDetail(List<HrDeal> listVo)throws DataAccessException;
	/**
	 * 通过提交状态查记录数
	 * @param map
	 * @param list
	 * @return
	 * @throws DataAccessException
	 */
	int queryByIsCommit(Map<String, Object> map, List<HrDeal> list) throws DataAccessException;
}
