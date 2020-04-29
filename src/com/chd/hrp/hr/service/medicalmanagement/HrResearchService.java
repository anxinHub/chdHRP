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

import com.chd.hrp.hr.entity.medicalmanagement.HrResearch;

/**
 * 
 * @Description:
 * 
 * @Table:
 * HR_RESEARCH
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

public interface HrResearchService  {
    /**
     * 添加纠纷调查
     * @param mapVo
     * @return
     */
	String addResearch(Map<String, Object> mapVo)throws DataAccessException;
    /**
     * 修改页面跳转
     * @param mapVo
     * @return
     */
	HrResearch queryByCodeResearch(Map<String, Object> mapVo)throws DataAccessException;
	/**
	 * 更新纠纷调查
	 * @param mapVo
	 * @return
	 */
	String updateResearch(Map<String, Object> mapVo)throws DataAccessException;
	/**
	 * 删除纠纷调查
	 * @param listVo
	 * @return
	 */
	String deleteResearch(List<HrResearch> listVo)throws DataAccessException;
	/**
	 * 查询数据
	 * @param page
	 * @return
	 */
	String queryResearch(Map<String, Object> page)throws DataAccessException;
	/**
	 * 提交时候查询
	 * @param hrNursingPromotion
	 * @return
	 */
	HrResearch queryByCode(HrResearch hrNursingPromotion)throws DataAccessException;
	/**
	 * 提交纠纷调查（批量）
	 * @param List<HrResearch>
	 * @return
	 */
	String confirmHrTechRec(List<HrResearch> list) throws DataAccessException;
	/**
	 * 撤回纠纷调查（批量）
	 */
	String reConfirmHrHrTechRec(List<HrResearch> list) throws DataAccessException;
	/**
	 * 审核
	 * @param hrNursingPromotion
	 * @return
	 */
	String auditHrTechRec(HrResearch hrNursingPromotion)throws DataAccessException;
	/**
	 * 销审
	 * @param hrNursingPromotion
	 * @return
	 */
	String unauditHrHrTechRec(HrResearch hrNursingPromotion)throws DataAccessException;
	/**
	 * 添加页面提交查询
	 * @param mapVo
	 * @return
	 */
	HrResearch queryByCodeAdd(Map<String, Object> mapVo)throws DataAccessException;
	/**
	 * 添加页提交
	 * @param research
	 * @return
	 */
	String confirmHrResearchAdd(HrResearch research)throws DataAccessException;
	/**
	 * 添加页撤回
	 * @param research
	 * @return
	 */
	String reConfirmHResearchAdd(HrResearch research)throws DataAccessException;
	/**
	 * 查询住院号
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	String queryinHosNo(Map<String, Object> mapVo)throws DataAccessException;
	/**
	 * 查询投诉详细信息
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	String queryinHosNoDetail(Map<String, Object> mapVo)throws DataAccessException;
	/**
	 * 查询明显数据
	 * @param page
	 * @return
	 * @throws DataAccessException
	 */
	String queryResearchDetail(Map<String, Object> page)throws DataAccessException;
	/**
	 * 查询投诉调查情况明细
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	String queryByCodeResearchD(Map<String, Object> mapVo)throws DataAccessException;
	/**
 * 打印
 * @param page
 * @return
 * @throws DataAccessException
 */
List<Map<String,Object>> queryResearchByPrint(Map<String, Object> page)throws DataAccessException;
	String deleteResearchDetail(List<HrResearch> listVo)throws DataAccessException;

}
