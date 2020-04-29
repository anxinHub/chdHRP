package com.chd.hrp.hr.service.medicalmanagement;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.hr.entity.medicalmanagement.HrInnovation;

public interface HrInnovationService {
     /**
      * 添加创新能力
      * @param mapVo
      * @return
      * @throws DataAccessException
      */
	String addInnovation(Map<String, Object> mapVo)throws DataAccessException;
     /**
      * 删除创新能力
      * @param listVo
      * @return
      * @throws DataAccessException
      */
	String deleteInnovation(List<HrInnovation> listVo)throws DataAccessException;
       /**
        * 查询创新能力
        * @param page
        * @return
        * @throws DataAccessException
        */
	String queryInnovation(Map<String, Object> page)throws DataAccessException;
       /**
        * 导入创新能力
        * @param mapVo
        * @return
        * @throws DataAccessException
        */
	String importInnovation(Map<String, Object> mapVo)throws DataAccessException;
    /**
     * 提交时候查询创新能力
     * @param hrNursingPromotion
     * @return
     * @throws DataAccessException
     */
	HrInnovation queryByCode(HrInnovation hrNursingPromotion)throws DataAccessException;
     /**
      * 提交创新能力
      * @param hrPlanit
      * @return
      * @throws DataAccessException
      */
	String confirmInnovation(HrInnovation hrPlanit)throws DataAccessException;
     /**
      * 撤回创新能力
      * @param hrNursingPromotion
      * @return
      * @throws DataAccessException
      */
	String reConfirmInnovation(HrInnovation hrNursingPromotion)throws DataAccessException;
	
	/**
	 * 打印
	 * 
	 * @param page
	 * @return
	 * @throws DataAccessException
	 */
List<Map<String,Object>> queryInnovationByPrint(Map<String, Object> page)throws DataAccessException;
}
