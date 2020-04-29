package com.chd.hrp.htcg.dao.making.drgs;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.htcg.entity.making.drgs.HtcgDrgsType;
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

public interface HtcgDrgsTypeMapper extends SqlMapper{
	
    public int addHtcgDrgsType(Map<String,Object> entityMap)throws DataAccessException;
	
	public int addBatchHtcgDrgsType(List<Map<String,Object>> list)throws DataAccessException;
	 
	public List<HtcgDrgsType> queryHtcgDrgsType(Map<String,Object> entityMap) throws DataAccessException;
	
	public List<HtcgDrgsType> queryHtcgDrgsType(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	 
	public HtcgDrgsType queryHtcgDrgsTypeByCode(Map<String,Object> entityMap)throws DataAccessException;
	 
	public int deleteHtcgDrgsType(Map<String,Object> entityMap)throws DataAccessException;
	
	public int deleteBatchHtcgDrgsType(List<Map<String,Object>> list)throws DataAccessException;
	 
	public int updateHtcgDrgsType(Map<String,Object> entityMap)throws DataAccessException;
	
	
	 
}
