package com.chd.hrp.hr.dao.training.setting;

import java.util.List;
import java.util.Map;

import com.chd.base.SqlMapper;
import com.chd.hrp.hr.entity.training.setting.HrTrainSite;

public interface HrTrainSiteMapper extends SqlMapper{
    /**
     * 添加查询培训地点是否存在
     * @param entityMap
     * @return
     */
	List<HrTrainSite> queryTrainSiteById(Map<String, Object> entityMap);
    /**
     * 修改查询名称是否存在
     * @param entityMap
     * @return
     */
	List<HrTrainSite> queryTrainSiteByIdName(Map<String, Object> entityMap);
	/**
	 * 删除培训地点
	 * @param entityList
	 */
	void deleteTrainSite(List<HrTrainSite> entityList);
	/**
	 * 导入查询
	 * @param saveMap
	 * @return
	 */
	List<HrTrainSite> queryByCodeName(Map<String, Object> saveMap);
	/**
	 * 查询是否使用
	 * @param hrTrainSite
	 * @return
	 */
	int queryUseTrainSite(HrTrainSite hrTrainSite);

}
