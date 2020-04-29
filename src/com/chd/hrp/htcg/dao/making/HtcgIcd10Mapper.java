package com.chd.hrp.htcg.dao.making;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.htcg.entity.making.HtcgIcd10;
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

public interface HtcgIcd10Mapper extends SqlMapper{
	 
	public int addHtcgIcd10(Map<String,Object> entityMap)throws DataAccessException;
	 
	public int addBatchHtcgIcd10(List<Map<String,Object>> list)throws DataAccessException;
	 
	public int updateHtcgIcd10(Map<String,Object> entityMap)throws DataAccessException;
	
	public int deleteHtcgIcd10(Map<String,Object> entityMap)throws DataAccessException;
	
	public int deleteBatchHtcgIcd10(List<Map<String,Object>> list)throws DataAccessException;
	
	public List<HtcgIcd10> queryHtcgIcd10(Map<String,Object> entityMap) throws DataAccessException;
	
	public List<HtcgIcd10> queryHtcgIcd10(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	 
	public HtcgIcd10 queryHtcgIcd10ByCode(Map<String,Object> entityMap)throws DataAccessException;

	public HtcgIcd10 queryHtcgIcd10Imp(Map<String, Object> htcgIcd9Map) throws DataAccessException;

	
	
}
