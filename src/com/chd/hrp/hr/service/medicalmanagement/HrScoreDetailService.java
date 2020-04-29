/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.hr.service.medicalmanagement;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.hr.entity.medicalmanagement.HrScoreDetail;
/**
 * 
 * @Description:
 * 
 * @Table:
 * HR_SCORE_DETAIL
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

public interface HrScoreDetailService {
    /**
     * 添加投诉扣分明细
     * @param mapVo
     * @return
     * @throws DataAccessException
     */
	String addScoreDetail(Map<String, Object> mapVo)throws DataAccessException;
     /**
      * 删除投诉扣分明细
      * @param listVo
      * @return
      * @throws DataAccessException
      */
	String deleteScoreDetail(List<HrScoreDetail> listVo)throws DataAccessException;
	/**
	 * 查询投诉扣分明细
	 * @param page
	 * @return
	 * @throws DataAccessException
	 */
	String queryScoreDetail(Map<String, Object> page)throws DataAccessException;
	/**
	 * 提交查询
	 * @param hrScoreDetail
	 * @return
	 * @throws DataAccessException
	 */
	HrScoreDetail queryByCode(HrScoreDetail hrScoreDetail)throws DataAccessException;
	/**
	 * 提交投诉扣分明细
	 * @param hrPlanit
	 * @return
	 * @throws DataAccessException
	 */
	String confirmScoreDetail(String hrScoreDetail)throws DataAccessException;
	/**
	 * 取消投诉扣分明细（单条）
	 * @param hrScoreDetail
	 * @return
	 * @throws DataAccessException
	 */
	String reConfirmScoreDetail(HrScoreDetail hrScoreDetail)throws DataAccessException;
	/**
	 * 取消投诉扣分明细(批量)
	 * @param List<HrScoreDetail>
	 * @return
	 * @throws DataAccessException
	 */
	String reConfirmScoreDetailBatch(List<HrScoreDetail> list) throws DataAccessException;
	/**
	 * 查询下拉表格
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	String queryHrResearch(Map<String, Object> mapVo)throws DataAccessException;
	/**
	 * 打印
	 * @param page
	 * @return
	 * @throws DataAccessException
	 */
	List<Map<String,Object>> queryScoreDetailByPrint(Map<String, Object> page)throws DataAccessException;
}
