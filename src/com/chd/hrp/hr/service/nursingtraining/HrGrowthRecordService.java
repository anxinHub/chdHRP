package com.chd.hrp.hr.service.nursingtraining;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.hr.entity.nursingtraining.HrGrowthRecord;

/**
 * 成长记录
 * @author Administrator
 *
 */
public interface HrGrowthRecordService {
    /**
     * 增加成长记录
     * @param mapVo
     * @return
     */
	String addGrowthRecord(Map<String, Object> mapVo)throws DataAccessException;
    /**
     * 跳转修改页面
     * @param mapVo
     * @return
     */
	HrGrowthRecord queryByCodeGrowthRecord(Map<String, Object> mapVo)throws DataAccessException;
	/**
	 * 修改成长记录
	 * @param mapVo
	 * @return
	 */
	String updateGrowthRecord(Map<String, Object> mapVo)throws DataAccessException;
	/**
	 * 删除成长记录
	 * @param listVo
	 */
	String  deleteGrowthRecord(List<HrGrowthRecord> listVo)throws DataAccessException;
	/**
	 * 查询成长记录
	 * @param page
	 * @return
	 */
	String queryGrowthRecord(Map<String, Object> page)throws DataAccessException;
	/**
	 * 查询左侧菜单
	 * @param mapVo
	 * @return
	 */
	String queryGrowthRecordTree(Map<String, Object> mapVo)throws DataAccessException;

}
