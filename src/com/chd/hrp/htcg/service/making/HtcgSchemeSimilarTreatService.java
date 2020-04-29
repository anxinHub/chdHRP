package com.chd.hrp.htcg.service.making;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

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

public interface HtcgSchemeSimilarTreatService {
	
	public String addHtcgSchemeSimilarTreat(Map<String,Object> entityMap)throws DataAccessException;
	
	public String addBatchHtcgSchemeSimilarTreat(List<Map<String,Object>> list)throws DataAccessException;
	
	public String queryHtcgSchemeSimilarTreat(Map<String,Object> entityMap) throws DataAccessException;
	
	public HtcgSchemeSimilarTreat queryHtcgSchemeSimilarTreatByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	public String deleteHtcgSchemeSimilarTreat(Map<String,Object> entityMap)throws DataAccessException;
	
	public String deleteBatchHtcgSchemeSimilarTreat(List<Map<String,Object>> list)throws DataAccessException;

	public String updateHtcgSchemeSimilarTreat(Map<String,Object> entityMap)throws DataAccessException;
	
	public String updateBatchHtcgSchemeSimilarTreat(List<Map<String,Object>> list)throws DataAccessException;
}
