/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.med.serviceImpl.requrie.reqsearch;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.hrp.med.service.requrie.reqsearch.MedRequireReqSearchService;

/**
 * 
 * @Description:
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */

@Service("medRequireReqSearchService")
public class MedRequireReqSearchServiceImpl implements MedRequireReqSearchService {

	private static Logger logger = Logger.getLogger(MedRequireReqSearchServiceImpl.class);
	
	@Override
	public String add(Map<String,Object> entityMap)throws DataAccessException{
		return null;
		
	}
	
	@Override
	public String addBatch(List<Map<String,Object>> entityList)throws DataAccessException{
		return null;
	}
	
	@Override
	public String update(Map<String,Object> entityMap)throws DataAccessException{
		return null;
	}
	
	@Override
	public String updateBatch(List<Map<String,Object>> entityList)throws DataAccessException{
		return null;
	}
	
	@Override
    public String delete(Map<String, Object> entityMap) throws DataAccessException {
		return null;
    }
    
	@Override
	public String deleteBatch(List<Map<String,Object>> entityList)throws DataAccessException{
		return null;
	}
	
	@Override
	public String addOrUpdate(Map<String,Object> entityMap)throws DataAccessException{
		return null;
	}
	
	@Override
	public String query(Map<String,Object> entityMap) throws DataAccessException{
		return null;
	}
	
	@Override
	public <T>T queryByCode(Map<String,Object> entityMap)throws DataAccessException{
		Map<String,Object> map = new HashMap<String,Object>();
		return (T) map;
	}
	
	
	@Override
	public <T>T queryByUniqueness(Map<String,Object> entityMap)throws DataAccessException{
		Map<String,Object> map = new HashMap<String,Object>();
		return (T) map;
	}
	
	@Override
	public List<?> queryExists(Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}
	
}
