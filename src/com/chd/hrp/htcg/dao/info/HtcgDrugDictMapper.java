package com.chd.hrp.htcg.dao.info;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.htcg.entity.info.HtcgDrugDict;
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

public interface HtcgDrugDictMapper extends SqlMapper{
	
    public int addHtcgDrugDict(Map<String,Object> entityMap)throws DataAccessException;
	
	public int addBatchHtcgDrugDict(List<Map<String,Object>> list)throws DataAccessException;
	
	public List<HtcgDrugDict> queryHtcgDrugDict(Map<String,Object> entityMap) throws DataAccessException;
	
	public List<HtcgDrugDict> queryHtcgDrugDict(Map<String,Object> entityMap,RowBounds rowBounds) throws DataAccessException;
	
	public HtcgDrugDict queryHtcgDrugDictByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	public int deleteHtcgDrugDict(Map<String,Object> entityMap)throws DataAccessException;
	
	public int deleteBatchHtcgDrugDict(List<Map<String,Object>> list)throws DataAccessException;
	
	public int updateHtcgDrugDict(Map<String,Object> entityMap)throws DataAccessException;
}
