package com.chd.hrp.med.serviceImpl.order.exec;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.SysPage;
import com.chd.base.util.ChdJson;
import com.chd.hrp.med.dao.order.MedOrderDetailMapper;
import com.chd.hrp.med.dao.order.MedOrderMainMapper;
import com.chd.hrp.med.dao.order.audit.MedOrderAuditMapper;
import com.chd.hrp.med.dao.order.exec.MedOrderExecMapper;
import com.chd.hrp.med.service.base.MedCommonService;
import com.chd.hrp.med.service.order.exec.MedOrderExecService;
import com.github.pagehelper.PageInfo;

@Service("medOrderExecService")
public class MedOrderExecServiceImpl implements MedOrderExecService {
	private static Logger logger = Logger.getLogger(MedOrderExecServiceImpl.class);
	
	//引入dao
	@Resource(name = "medOrderExecMapper")
	private final MedOrderExecMapper medOrderExecMapper = null;
	
	
	
	@Override
	public String add(Map<String, Object> entityMap) throws DataAccessException {
			try {
				return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";
			}
			catch (Exception e) {
				logger.error(e.getMessage(), e);
				return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addMedOrderMain\"}";
			}
	}
	
	@Override
	public String addBatch(List<Map<String, Object>> entityMap) throws DataAccessException {
		try {
			//暂无该业务
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";
		}catch (Exception e) {
			logger.error(e.getMessage(), e);
			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addBatchMedOrderMain\"}";
		}
	}
	
	
	@Override
	public String update(Map<String, Object> entityMap) throws DataAccessException {
		try {			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";
		}catch (Exception e) {
			logger.error(e.getMessage(), e);
			return "{\"error\":\"更新失败 数据库异常 请联系管理员! 方法 updateMedOrderMain\"}";
		}
	}
	
	
	@Override
	public String updateBatch(List<Map<String, Object>> entityMap) throws DataAccessException {
		try {
			//暂无该业务
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";
		}catch (Exception e) {
			logger.error(e.getMessage(), e);
			return "{\"error\":\"更新失败 数据库异常 请联系管理员! 方法 updateBatchMedOrderMain\"}";
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
			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 deleteBatchMedOrderInit\"}";
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
	
	/**
	 * @Description 
	 * 订单执行查询--主查询
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String queryMedOrderExec(Map<String, Object> entityMap) throws DataAccessException {
		SysPage sysPage = new SysPage();
		sysPage = (SysPage) entityMap.get("sysPage");
		if (sysPage.getTotal()==-1){
			List<?> list = medOrderExecMapper.queryMedOrderExec(entityMap);
			
			return ChdJson.toJson(list);
		}else{
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			List<?> list = medOrderExecMapper.queryMedOrderExec(entityMap, rowBounds);
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
		}
		/*
		List<?> list = medOrderExecMapper.queryMedOrderExec(entityMap);
		return ChdJson.toJson(list);*/
	}

	@Override
	public <T> T queryByCode(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}
	
	
}
