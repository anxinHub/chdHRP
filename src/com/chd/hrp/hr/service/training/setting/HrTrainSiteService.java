package com.chd.hrp.hr.service.training.setting;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.hr.entity.training.setting.HrTrainSite;

public interface HrTrainSiteService {
    /**
     * 增加培训地点
     * @param mapVo
     * @return
     */
	String addTrainSite(Map<String, Object> mapVo) throws DataAccessException;
    /**
     * 修改页面跳转查询培训地点
     * @param mapVo
     * @return
     */
	HrTrainSite queryByCode(Map<String, Object> mapVo) throws DataAccessException;
	/**
	 * 修改培训地点
	 * @param mapVo
	 * @return
	 */
	String updateTrainSite(Map<String, Object> mapVo) throws DataAccessException;
	/**
	 * 删除查询是否使用
	 * @param hrTrainSite
	 * @return
	 */
	int queryUseTrainSite(HrTrainSite hrTrainSite) throws DataAccessException;
	/**
	 * 删除培训地点
	 * @param listVo
	 * @return
	 */
	String deleteTrainSite(List<HrTrainSite> listVo) throws DataAccessException;
	/**
	 * 查询
	 * @param page
	 * @return
	 */
	String queryTrainSite(Map<String, Object> page) throws DataAccessException;
	/**
	 * 导入
	 * @param mapVo
	 * @return
	 */
	String importDate(Map<String, Object> mapVo) throws DataAccessException;

}
