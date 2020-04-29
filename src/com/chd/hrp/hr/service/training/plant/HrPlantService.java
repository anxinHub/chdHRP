package com.chd.hrp.hr.service.training.plant;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.hr.entity.training.plant.HrPlant;

public interface HrPlantService {
	  /**
     * 添加
     * @param mapVo
     * @return
     * @throws DataAccessException
     */
	String addPlant(Map<String, Object> mapVo) throws DataAccessException;
	  /**
     * 修改跳转
     * @param mapVo
     * @return
     * @throws DataAccessException
     */
	HrPlant queryByCode(Map<String, Object> mapVo) throws DataAccessException;
	  /**
     * 修改
     * @param mapVo
     * @return
     * @throws DataAccessException
     */
	String updatePlant(Map<String, Object> mapVo) throws DataAccessException;
	  /**
     * 查询使用
     * @param mapVo
     * @return
     * @throws DataAccessException
     */
	int queryUsePlant(HrPlant hrPlant) throws DataAccessException;
	  /**
     * 删除
     * @param mapVo
     * @return
     * @throws DataAccessException
     */
	String deletePlant(List<HrPlant> listVo) throws DataAccessException;
	  /**
     * 查询
     * @param mapVo
     * @return
     * @throws DataAccessException
     */
	String queryPlant(Map<String, Object> page) throws DataAccessException;
	  /**
     * 导入
     * @param mapVo
     * @return
     * @throws DataAccessException
     */
	String importDate(Map<String, Object> mapVo) throws DataAccessException;
	/**
	 * 是否发证
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	int queryByCodeCert(Map<String, Object> mapVo)throws DataAccessException;
	/**
	 * 是否考核
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	int queryByCodeExam(Map<String, Object> mapVo)throws DataAccessException;
	int queryUserPlan(List<HrPlant> listVo)throws DataAccessException;
	String deletePlantBach(List<HrPlant> listVo)throws DataAccessException;

}
