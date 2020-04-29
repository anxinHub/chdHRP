/*
 *
 */package com.chd.hrp.htcg.dao.info;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.htcg.entity.info.HtcgClpStepDict;
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

public interface HtcgClpStepDictMapper extends SqlMapper{
	
    public int addHtcgClpStepDict(Map<String,Object> entityMap)throws DataAccessException;
	
	public int addBatchHtcgClpStepDict(List<Map<String,Object>> list)throws DataAccessException;
	
	public List<HtcgClpStepDict> queryHtcgClpStepDict(Map<String,Object> entityMap) throws DataAccessException;
	
	public List<HtcgClpStepDict> queryHtcgClpStepDict(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	public HtcgClpStepDict queryHtcgClpStepDictByCode(Map<String,Object> entityMap) throws DataAccessException;
	
    public int deleteHtcgClpStepDict(Map<String,Object> entityMap)throws DataAccessException;
	
	public int deleteBatchHtcgClpStepDict(List<Map<String,Object>> list)throws DataAccessException;
	
	public int updateHtcgClpStepDict(Map<String,Object> entityMap) throws DataAccessException;

}
