package com.chd.hrp.htc.dao.task.basic;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.htc.entity.task.basic.HtcBonusCostRela;
/**
 * 
 * @Title. 
 * @Description.
 * @Copyright: Copyright (c) 2015-2-14 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @Author: LiuYingDuo
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */

public interface HtcBonusCostRelaMapper  extends SqlMapper{
	
	/**
	 * 添加奖金项目与成本项目对应关系
	 */
	public int addHtcBonusCostRela(Map<String,Object> entityMap)throws DataAccessException;
	
	
	public int addBatchHtcBonusCostRela(List<Map<String,Object>> entityMap)throws DataAccessException;
	/**
	 * 查询奖金项目与成本项目对应关系
	 */	
	public List<HtcBonusCostRela> queryHtcBonusCostRela(Map<String,Object> entityMap) throws DataAccessException;	
	
	public List<HtcBonusCostRela> queryHtcBonusCostRela(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	/**
	 * 通过id 查询奖金项目与成本项目对应关系
	 */
	public HtcBonusCostRela queryHtcBonusCostRelaByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * 批量删除奖金项目与成本项目对应关系
	 */
	public int deleteBatchHtcBonusCostRela(List<Map<String,Object>> listVo)throws DataAccessException;
	
    /**
     * 删除奖金项目与成本项目对应关系
     */
    public int deleteHtcBonusCostRela(Map<String,Object> entityMap)throws DataAccessException;
    
	/**
	 * 更新奖金项目与成本项目对应关系
	 */
	public int updateHtcBonusCostRela(Map<String,Object> entityMap)throws DataAccessException;
}
