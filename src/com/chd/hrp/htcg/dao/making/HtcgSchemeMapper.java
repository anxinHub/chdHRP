package com.chd.hrp.htcg.dao.making;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.htcg.entity.making.HtcgScheme;
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

public interface HtcgSchemeMapper extends SqlMapper{
	 
    public int addHtcgScheme(Map<String,Object> entityMap)throws DataAccessException;
	
	public int addBatchHtcgScheme(List<Map<String,Object>> list)throws DataAccessException;
	
	public List<HtcgScheme> queryHtcgScheme(Map<String,Object> entityMap) throws DataAccessException;
	
	public List<HtcgScheme> queryHtcgScheme(Map<String,Object> entityMap,RowBounds rowBounds) throws DataAccessException;
	 
	public HtcgScheme queryHtcgSchemeByCode(Map<String,Object> entityMap)throws DataAccessException;
	  
	public int deleteHtcgScheme(Map<String,Object> entityMap)throws DataAccessException;
	
	public int deleteBatchHtcgScheme(List<Map<String,Object>> list)throws DataAccessException;
	 
	public int updateHtcgScheme(Map<String,Object> entityMap)throws DataAccessException;
	
	
}
