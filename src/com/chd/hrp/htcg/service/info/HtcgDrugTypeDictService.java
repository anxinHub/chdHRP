package com.chd.hrp.htcg.service.info;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.htcg.entity.info.HtcgDrugTypeDict;


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

public interface HtcgDrugTypeDictService {

	public String addHtcgDrugTypeDict(Map<String,Object> entityMap)throws DataAccessException;
	
	public String addBatchHtcgDrugTypeDict(List<Map<String,Object>> list)throws DataAccessException;

	public String queryHtcgDrugTypeDict(Map<String,Object> entityMap) throws DataAccessException;
	
	public HtcgDrugTypeDict queryHtcgDrugTypeDictByCode(Map<String,Object> entityMap)throws DataAccessException;

	public String deleteHtcgDrugTypeDict(Map<String,Object> entityMap)throws DataAccessException;
	
	public String deleteBatchHtcgDrugTypeDict(List<Map<String, Object>> list)throws DataAccessException;

	public String updateHtcgDrugTypeDict(Map<String,Object> entityMap)throws DataAccessException;
	
	public String importHtcgDrugTypeDict(Map<String,Object> entityMap)throws DataAccessException;
}
