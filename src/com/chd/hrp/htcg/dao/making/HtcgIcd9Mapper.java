package com.chd.hrp.htcg.dao.making;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.htcg.entity.making.HtcgIcd9;
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

public interface HtcgIcd9Mapper extends SqlMapper{
	 
	public int addHtcgIcd9(Map<String,Object> entityMap)throws DataAccessException;
	 
	public int addBatchHtcgIcd9(List<Map<String,Object>> entityMap)throws DataAccessException;
	
	public int deleteHtcgIcd9(Map<String,Object> entityMap)throws DataAccessException;
	
	public int deleteBatchHtcgIcd9(List<Map<String,Object>> entityMap)throws DataAccessException;
	
	public int updateHtcgIcd9(Map<String,Object> entityMap)throws DataAccessException;
	
	public List<HtcgIcd9> queryHtcgIcd9(Map<String,Object> entityMap) throws DataAccessException;
	
	public List<HtcgIcd9> queryHtcgIcd9(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	 
	public HtcgIcd9 queryHtcgIcd9ByCode(Map<String,Object> entityMap)throws DataAccessException;

	public HtcgIcd9 queryHtcgIcd9Imp(Map<String, Object> mapVo) throws DataAccessException;
	
}
