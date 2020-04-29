package com.chd.hrp.hr.service.scientificresearch;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.hr.entity.scientificresearch.HrScientificRewards;


/**
 * 科研成果奖励
 * @author Administrator
 *
 */
public interface HrScientificRewardsService {
    /**
     * 增加科研成果奖励
     * @param mapVo
     * @return
     */
	String addScientificRewards(Map<String, Object> mapVo)throws DataAccessException;
    /**
     * 跳转修改页面
     * @param mapVo
     * @return
     */
	HrScientificRewards queryByCodeScientificRewards(Map<String, Object> mapVo)throws DataAccessException;
	/**
	 * 修改科研成果奖励
	 * @param mapVo
	 * @return
	 */
	String updateScientificRewards(Map<String, Object> mapVo)throws DataAccessException;
	/**
	 * 删除科研成果奖励
	 * @param listVo
	 */
	String  deleteScientificRewards(List<HrScientificRewards> listVo)throws DataAccessException;
	/**
	 * 查询科研成果奖励
	 * @param page
	 * @return
	 */
	String queryScientificRewards(Map<String, Object> page)throws DataAccessException;
	/**
	 * 查询左侧菜单
	 * @param mapVo
	 * @return
	 */
	String queryScientificRewardsTree(Map<String, Object> mapVo)throws DataAccessException;

}
