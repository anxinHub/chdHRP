package com.chd.hrp.acc.serviceImpl.books.memorandumbook;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.chd.base.SysPage;
import com.chd.base.util.ChdJson;
import com.chd.hrp.acc.dao.books.memorandumbook.AccMatchInitMapper;
import com.chd.hrp.acc.entity.AccMatchInit;
import com.chd.hrp.acc.service.books.memorandumbook.AccMatchInitService;
import com.github.pagehelper.PageInfo;

@Service("accMatchInitService")
public class AccMatchInitServiceImpl implements AccMatchInitService{
	
	private static Logger logger = Logger.getLogger(AccMatchInitServiceImpl.class);
	
	@Resource(name = "accMatchInitMapper")
	private final AccMatchInitMapper accMatchInitMapper = null;

	@Override
	public String addAccMatchInit(Map<String, Object> entityMap)
			throws DataAccessException {
		AccMatchInit accMatchInit = accMatchInitMapper.queryAccMatchInitByCode(entityMap);

		if (accMatchInit != null) {
			return "{\"error\":\"项目 " + accMatchInit.getProj_name() + " 已存在，请勿重复添加.\"}";
		}
		
		try {
			accMatchInitMapper.addAccMatchInit(entityMap);
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);
			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码 addAccMatchInit\"}";
		}
	}

	@Override
	public String addBatchAccMatchInit(List<Map<String, Object>> entityMap)
			throws DataAccessException {
		try {
			accMatchInitMapper.addBatchAccMatchInit(entityMap);
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);
			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码 addBatchAccMatchInit\"}";
		}
	}

	@Override
	public String queryAccMatchInit(Map<String, Object> entityMap) throws DataAccessException {
		
         SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<AccMatchInit> list = accMatchInitMapper.queryAccMatchInit(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<AccMatchInit> list = accMatchInitMapper.queryAccMatchInit(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
	}


	@Override
	public AccMatchInit queryAccMatchInitByCode(Map<String, Object> entityMap)
			throws DataAccessException {
		
		return accMatchInitMapper.queryAccMatchInitByCode(entityMap);
	}

	@Override
	public String deleteAccMatchInit(Map<String, Object> entityMap)
			throws DataAccessException {
		try {

			int state = accMatchInitMapper.deleteAccMatchInit(entityMap);
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

			}
			catch (Exception e) {
		
				logger.error(e.getMessage(), e);
		
				return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码  deleteAccMatchInit\"}";
		
			}
	}

	@Override
	public String deleteBatchAccMatchInit(List<Map<String, Object>> entityMap)
			throws DataAccessException {
		
		try {

			int state = accMatchInitMapper.deleteBatchAccMatchInit(entityMap);
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {
	
			logger.error(e.getMessage(), e);
	
			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码  deleteBatchAccMatchInit\"}";
	
		}
	}

	@Override
	public String updateAccMatchInit(Map<String, Object> entityMap)
			throws DataAccessException {
		try {
			accMatchInitMapper.updateAccMatchInit(entityMap);

			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updateAccMatchInit\"}";

		}
	}

	@Override
	public String updateBatchAccMatchInit(List<Map<String, Object>> entityMap)
			throws DataAccessException {
		try {

			accMatchInitMapper.updateBatchAccMatchInit(entityMap);

			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updateBatchAccMatchInit\"}";

		}
	}

	@Override
	public List<Map<String, Object>> queryAccMatchInitPrint(
			Map<String, Object> entityMap) throws DataAccessException {
		
		TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		
		List<Map<String, Object>> list = accMatchInitMapper.queryAccMatchInitPrint(entityMap);
		
		return list ;
	}

}
