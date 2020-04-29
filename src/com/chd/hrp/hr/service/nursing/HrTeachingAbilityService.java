package com.chd.hrp.hr.service.nursing;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.hr.entity.nursing.HrTeachingAbility;

/**
 * 教学能力
 * 
 * @author Administrator
 *
 */
public interface HrTeachingAbilityService {
	/**
	 * 添加教学能力
	 * 
	 * @param mapVo
	 * @return
	 */
	String addTeachingAbility(Map<String, Object> mapVo)throws DataAccessException;

    /**
     * 删除教学能力
     * @param listVo
     */
	String deleteTeachingAbility(List<HrTeachingAbility> listVo)throws DataAccessException;
    /**
     * 查询教学能力
     * @param page
     * @return
     */
	String queryTeachingAbility(Map<String, Object> page)throws DataAccessException;
   /**
    * 查询下拉框教学种类
    * @param mapVo
    * @return
    */
	String queryTeachType(Map<String, Object> mapVo)throws DataAccessException;
    /**
     * 查询下拉表格教学种类
     * @param page
     * @return
     * @throws DataAccessException
     */
    String queryHrTeachType(Map<String, Object> page)throws DataAccessException;
    /**
     * 导入
     * @param mapVo
     * @return
     * @throws DataAccessException
     */
	String importTeach(Map<String, Object> mapVo)throws DataAccessException;

}
