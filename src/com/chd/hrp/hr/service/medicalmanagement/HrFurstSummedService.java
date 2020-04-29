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

import com.chd.hrp.hr.entity.medicalmanagement.HrFurstdSum;

/**
 * 
 * @Description:
 * 进修总结
 * @Table:
 * HR_FURSTD_SUM
 * @Author: ade
 * @email:  ade@e-tonggroup.com
 * @Version: 1.0
 */

public interface HrFurstSummedService {

     /**
      * 添加进修总结
      * @param mapVo
      * @return
      * @throws DataAccessException
      */
	String addFurstSummed(Map<String, Object> mapVo)throws DataAccessException;
	/**
	 * 修改页查询
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	HrFurstdSum queryByCodeFurstSummed(Map<String, Object> mapVo)throws DataAccessException;
	/**
	 * 修改进修总结
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	String updateFurstSummed(Map<String, Object> mapVo)throws DataAccessException;
	/**
	 * 删除进修总结
	 * @param listVo
	 * @return
	 * @throws DataAccessException
	 */
	String deleteFurstSummed(List<HrFurstdSum> listVo)throws DataAccessException;
	/**
	 * 查询进修总结
	 * @param page
	 * @return
	 * @throws DataAccessException
	 */
	String queryFurstSummed(Map<String, Object> page)throws DataAccessException;
	/**
	 * 提交
	 * @param hrFurstSummed
	 * @return
	 * @throws DataAccessException
	 */
	String confirmHrFurstSummed(HrFurstdSum hrFurstSummed)throws DataAccessException;
	/**
	 * 撤回
	 * @param hrFurstSummed
	 * @return
	 * @throws DataAccessException
	 */
	String reConfirmHrHrFurstSummed(HrFurstdSum hrFurstSummed)throws DataAccessException;
	/**
	 * 审核
	 * @param hrFurstSummed
	 * @return
	 * @throws DataAccessException
	 */
	String auditHrFurstSummed(HrFurstdSum hrFurstSummed)throws DataAccessException;
	/**
	 * 销审
	 * @param hrFurstSummed
	 * @return
	 * @throws DataAccessException
	 */
	String unauditHrHrFurstSummed(HrFurstdSum hrFurstSummed)throws DataAccessException;
	/**
	 * 未通过
	 * @param hrFurstSummed
	 * @return
	 * @throws DataAccessException
	 */
	String dispassHrHrFurstSummed(HrFurstdSum hrFurstSummed)throws DataAccessException;
    /**
     * 查询申请单号
     * @param mapVo
     * @return
     * @throws DataAccessException
     */
	String queryAppNo(Map<String, Object> mapVo) throws DataAccessException;
	/**
	 * 查询单号对应人员
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	String queryFurstdApp(Map<String, Object> mapVo)throws DataAccessException;
	  /**
		 * 打印
		 * 
		 * @param page
		 * @return
		 * @throws DataAccessException
		 */
	List<Map<String,Object>> queryFurstSummedByPrint(Map<String, Object> page)throws DataAccessException;
}
