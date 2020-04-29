package com.chd.hrp.hr.service.organize;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.hr.entity.orangnize.HrCompetencyTarget;

public interface HrCompetencyTargetService {
   /**
    * 添加能力素质指标
    * @param mapVo
    * @return
    */
	String CompetencyTargetAdd(Map<String, Object> mapVo)throws DataAccessException;
    /**
     * 修改查询能力素质指标
     * @param mapVo
     * @return
     */
	HrCompetencyTarget queryByCode(Map<String, Object> mapVo)throws DataAccessException;
    /**
     * 查询能力素质指标
     * @param page
     * @return
     */
	String queryHrCompetencyTarget(Map<String, Object> page)throws DataAccessException;
	/**
	 * 修改能力素质指标
	 * @param mapVo
	 * @return
	 */
	String updateCompetencyTarget(Map<String, Object> mapVo)throws DataAccessException;
	/**
	 * 删除能力素质指标
	 * @param listVo
	 */
	String deleteBatchCompetencyTarget(List<HrCompetencyTarget> listVo)throws DataAccessException;

}
