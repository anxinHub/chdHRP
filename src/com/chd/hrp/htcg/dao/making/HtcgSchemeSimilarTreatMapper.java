package com.chd.hrp.htcg.dao.making;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.htcg.entity.making.HtcgSchemeSimilarTreat;
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

public interface HtcgSchemeSimilarTreatMapper extends SqlMapper{
	
    public int addHtcgSchemeSimilarTreat(Map<String,Object> entityMap)throws DataAccessException;
	
	public int addBatchHtcgSchemeSimilarTreat(List<Map<String,Object>> list)throws DataAccessException;
	
	public List<HtcgSchemeSimilarTreat> queryHtcgSchemeSimilarTreat(Map<String,Object> entityMap) throws DataAccessException;
	
	public List<HtcgSchemeSimilarTreat> queryHtcgSchemeSimilarTreat(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	public HtcgSchemeSimilarTreat queryHtcgSchemeSimilarTreatByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	public int deleteHtcgSchemeSimilarTreat(Map<String,Object> entityMap)throws DataAccessException;
	
	public int deleteBatchHtcgSchemeSimilarTreat(List<Map<String,Object>> list)throws DataAccessException;

	public int updateHtcgSchemeSimilarTreat(Map<String,Object> entityMap)throws DataAccessException;
	
	public int updateBatchHtcgSchemeSimilarTreat(List<Map<String,Object>> list)throws DataAccessException;
	
}
