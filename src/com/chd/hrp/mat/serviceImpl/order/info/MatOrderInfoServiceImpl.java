package com.chd.hrp.mat.serviceImpl.order.info;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.util.ChdJson;
import com.chd.hrp.mat.dao.order.info.MatOrderInfoMapper;
import com.chd.hrp.mat.service.order.info.MatOrderInfoService;

@Service("matOrderInfoService")
public class MatOrderInfoServiceImpl implements MatOrderInfoService {
	private static Logger logger = Logger.getLogger(MatOrderInfoServiceImpl.class);
	
	//引入dao
	@Resource(name = "matOrderInfoMapper")
	private final MatOrderInfoMapper matOrderInfoMapper = null;
	
	
	
	@Override
	public String add(Map<String, Object> entityMap) throws DataAccessException {
			try {
				return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";
			}
			catch (Exception e) {
				logger.error(e.getMessage(), e);
				return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addMatOrderMain\"}";
			}
	}
	
	@Override
	public String addBatch(List<Map<String, Object>> entityMap) throws DataAccessException {
		try {
			//暂无该业务
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";
		}catch (Exception e) {
			logger.error(e.getMessage(), e);
			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addBatchMatOrderMain\"}";
		}
	}
	
	
	@Override
	public String update(Map<String, Object> entityMap) throws DataAccessException {
		try {			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";
		}catch (Exception e) {
			logger.error(e.getMessage(), e);
			return "{\"error\":\"更新失败 数据库异常 请联系管理员! 方法 updateMatOrderMain\"}";
		}
	}
	
	
	@Override
	public String updateBatch(List<Map<String, Object>> entityMap) throws DataAccessException {
		try {
			//暂无该业务
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";
		}catch (Exception e) {
			logger.error(e.getMessage(), e);
			return "{\"error\":\"更新失败 数据库异常 请联系管理员! 方法 updateBatchMatOrderMain\"}";
		}	
	}

	@Override
	public String delete(Map<String, Object> entityMap) throws DataAccessException {
		return null;
	}
	
	@Override
	public String deleteBatch(List<Map<String, Object>> entityList) throws DataAccessException {
		try {
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		}catch (Exception e) {
			logger.error(e.getMessage(), e);
			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 deleteBatchMatOrderInit\"}";
		}
	}
	
	@Override
	public String addOrUpdate(Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public <T> T queryByUniqueness(Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public List<?> queryExists(Map<String, Object> entityMap) throws DataAccessException {
		
		return null;
	}
	
	@Override
	public String query(Map<String, Object> entityMap) throws DataAccessException {
		return null;
	}
	
	@Override
	public <T> T queryByCode(Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}
	
	/**
	 * @Description 
	 * 订单信息查询--主查询
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String queryMatOrderInfo(Map<String, Object> entityMap) throws DataAccessException {
		List<?> list = matOrderInfoMapper.queryMatOrderInfo(entityMap);
		return ChdJson.toJson(list);
	}

	
	
	
}
