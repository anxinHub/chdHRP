package com.chd.hrp.hr.service.organize;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.hr.entity.orangnize.HrCompetencyModel;

public interface HrCompetencyModelService {
	   /**
	    * 添加能力素质模型
	    * @param mapVo
	    * @return
	    */
		String CompetencyModelAdd(Map<String, Object> mapVo)throws DataAccessException;
	    /**
	     * 修改查询能力素质模型
	     * @param mapVo
	     * @return
	     */
		HrCompetencyModel queryByCode(Map<String, Object> mapVo)throws DataAccessException;
	    /**
	     * 查询能力素质模型
	     * @param page
	     * @return
	     */
		String queryHrCompetencyModel(Map<String, Object> page)throws DataAccessException;
		/**
		 * 修改能力素质模型
		 * @param mapVo
		 * @return
		 */
		String updateCompetencyModel(Map<String, Object> mapVo)throws DataAccessException;
		/**
		 * 删除能力素质模型
		 * @param listVo
		 */
		String deleteBatchCompetencyModel(List<HrCompetencyModel> listVo)throws DataAccessException;
		/**
		 * 查询左侧树形
		 * @param mapVo
		 * @return
		 */
		String queryCompetencyModelTree(Map<String, Object> mapVo)throws DataAccessException;

}
