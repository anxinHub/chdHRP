package com.chd.hrp.hr.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;

import com.chd.base.SqlMapper;
import com.chd.hrp.hr.entity.orangnize.HosStation;

public interface InsertMapper extends SqlMapper{
     /**
      * 查询
      * @param entityMap
      * @return
      */
	List<Map<String, Object>> queryStationBaseInfo(Map<String, Object> entityMap);
    /**
     * 查询带分页
     * @param entityMap
     * @param rowBounds
     * @return
     */
	List<Map<String, Object>> queryStationBaseInfo(Map<String, Object> entityMap, RowBounds rowBounds);
	/**
	 * 修改
	 * @param entityMap
	 */
	void updateStationBaseInfo(Map<String, Object> entityMap);
	/**
	 * 添加
	 * @param entityMap
	 */
	void addStationBaseInfo(Map<String, Object> entityMap);
	/**
	 * 删除
	 * @param entityMap
	 */
	void deleteBatchStationBaseInfo(Map entityMap);

}
