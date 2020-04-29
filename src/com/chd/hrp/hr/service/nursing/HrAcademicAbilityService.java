package com.chd.hrp.hr.service.nursing;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.hr.entity.nursing.HrAcademicAbility;

/**
 * 学术能力
 * 
 * @author Administrator
 *
 */
public interface HrAcademicAbilityService {
	/**
	 * 添加学术能力
	 * 
	 * @param mapVo
	 * @return
	 */
	String addAcademicAbility(Map<String, Object> mapVo)throws DataAccessException;

    /**
     * 删除学术能力
     * @param listVo
     */
	String deleteAcademicAbility(List<HrAcademicAbility> listVo)throws DataAccessException;
    /**
     * 查询学术能力
     * @param page
     * @return
     */
	String queryAcademicAbility(Map<String, Object> page)throws DataAccessException;
   /**
    * 查询左侧树形
    * @param mapVo
    * @return
    */
	String queryAcademicAbilityTree(Map<String, Object> mapVo)throws DataAccessException;
    /**
     * 提交
     * @param hrAcademicAbility
     * @return
     */
    String confirmAcademicAbility(HrAcademicAbility hrAcademicAbility) throws DataAccessException ;
    /**
     * 提交(批量)
     * @param List<hrAcademicAbility>
     * @return
     */
    String confirmAcademicAbilityBatch(List<HrAcademicAbility> listVo) throws DataAccessException;
    /**
     * 撤回
     * @param hrAcademicAbility
     * @return
     */
	String reConfirmAcademicAbility(HrAcademicAbility hrAcademicAbility) throws DataAccessException ;
	/**
     * 撤回(批量)
     * @param List<HrAcademicAbility>
     * @return
     */
	String reConfirmAcademicAbilityBatch(List<HrAcademicAbility> listVo) throws DataAccessException;
     /**
      * 导入
      * @param mapVo
      * @return
      * @throws DataAccessException
      */
	String importAcademic(Map<String, Object> mapVo)throws DataAccessException ;



}
