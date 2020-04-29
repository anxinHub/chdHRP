package com.chd.hrp.hr.dao.training.plant;

import java.util.List;
import java.util.Map;

import com.chd.base.SqlMapper;
import com.chd.hrp.hr.entity.training.plant.HrPlant;

public interface HrPlantMapper extends SqlMapper{
	   /**
	    * 删除
	    * @param entityList
	    */
		void deletePlant(List<HrPlant> entityList);
	    /**
	     * 添加查询
	     * @param entityMap
	     * @return
	     */
	    List<HrPlant> queryPlantById(Map<String, Object> entityMap);
		List<HrPlant> queryPlantByIdName(Map<String, Object> entityMap);
		List<HrPlant> queryPlantByName(Map<String, Object> entityMap);
		/**
		 *  查询是否使用
		 * @param hrPlant
		 * @return
		 */
		int queryUsePlant(HrPlant hrPlant);
		/**
		 * 查询部门
		 * @param saveMap
		 * @return
		 */
		List<Map<String, Object>> queryDeptInfo(Map<String, Object> saveMap);
		/**
		 * 查询培训类别
		 * @param saveMap
		 * @return
		 */
		List<Map<String, Object>> queryTrainTypeInfo(Map<String, Object> saveMap);
		/**
		 * 培训方式
		 * @param saveMap
		 * @return
		 */
		List<Map<String, Object>> queryTrainWayInfo(Map<String, Object> saveMap);
		/**
		 * 查询部门no
		 * @param saveMap
		 * @return
		 */
		String queryDeptNo(Map<String, Object> saveMap);
		List<HrPlant> queryPlantByIdOrName(Map<String, Object> entityMap);
		/**
		 * 是否发证
		 * @param mapVo
		 * @return
		 */
		int queryByCodeCert(Map<String, Object> mapVo);
		/**
		 * 是否考核
		 * @param mapVo
		 * @return
		 */
		int queryByCodeExam(Map<String, Object> mapVo);
		int queryUserPlan(List<HrPlant> listVo);
		/**
		 * 删除所有
		 * @param listVo
		 */
		void deletePlantBach(List<HrPlant> listVo);
		
		
		

}
