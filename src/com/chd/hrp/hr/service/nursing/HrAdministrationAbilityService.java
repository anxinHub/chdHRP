package com.chd.hrp.hr.service.nursing;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.hr.entity.nursing.HrAdministrationAbility;

/**
 * 行政能力
 * 
 * @author Administrator
 *
 */
public interface HrAdministrationAbilityService {
	/**
	 * 添加行政能力
	 * 
	 * @param mapVo
	 * @return
	 */
	String addAdministrationAbility(Map<String, Object> mapVo)throws DataAccessException;

    /**
     * 删除行政能力
     * @param listVo
     */
	String deleteAdministrationAbility(List<HrAdministrationAbility> listVo)throws DataAccessException;
    /**
     * 查询行政能力
     * @param page
     * @return
     */
	String queryAdministrationAbility(Map<String, Object> page)throws DataAccessException;
    /**
     * 查询获奖下拉表格
     * @param page
     * @return
     */
    String queryPrize(Map<String, Object> page)throws DataAccessException;
    /**
     * 导入
     * @param mapVo
     * @return
     * @throws DataAccessException
     */
	String importAdmin(Map<String, Object> mapVo)throws DataAccessException;

}
