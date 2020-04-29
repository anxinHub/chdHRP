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
import com.chd.hrp.acc.dao.books.memorandumbook.AccMatchOutMapper;
import com.chd.hrp.acc.entity.AccMatchIncome;
import com.chd.hrp.acc.entity.AccMatchOut;
import com.chd.hrp.acc.service.books.memorandumbook.AccMatchOutService;
import com.github.pagehelper.PageInfo;

@Service("accMatchOutService")
public class AccMatchOutServiceImpl implements AccMatchOutService{
	
	private static Logger logger = Logger.getLogger(AccMatchOutServiceImpl.class);
	
	@Resource(name = "accMatchOutMapper")
	private final AccMatchOutMapper accMatchOutMapper = null;

	@Override
	public String addAccMatchOut(Map<String, Object> entityMap)
			throws DataAccessException {
		try {
			accMatchOutMapper.addAccMatchOut(entityMap);
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);
			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码 addAccMatchOut\"}";
		}
	}

	@Override
	public String addBatchAccMatchOut(List<Map<String, Object>> entityMap)
			throws DataAccessException {
		try {
			accMatchOutMapper.addBatchAccMatchOut(entityMap);
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);
			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码 addBatchAccMatchOut\"}";
		}
	}

	@Override
	public String queryAccMatchOut(Map<String, Object> entityMap) throws DataAccessException {
		
		
		  SysPage sysPage = new SysPage();
			
			sysPage = (SysPage) entityMap.get("sysPage");
			
			if (sysPage.getTotal()==-1){
				
				List<AccMatchOut> list = accMatchOutMapper.queryAccMatchOut(entityMap);
				
				return ChdJson.toJson(list);
				
			}else{
				
				RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
				
				List<AccMatchOut> list = accMatchOutMapper.queryAccMatchOut(entityMap, rowBounds);
				
				PageInfo page = new PageInfo(list);
				
				return ChdJson.toJson(list, page.getTotal());
				
			}
	}


	@Override
	public AccMatchOut queryAccMatchOutByCode(Map<String, Object> entityMap)
			throws DataAccessException {
		
		return accMatchOutMapper.queryAccMatchOutByCode(entityMap);
	}

	@Override
	public String deleteAccMatchOut(Map<String, Object> entityMap)
			throws DataAccessException {
		try {

			 accMatchOutMapper.deleteAccMatchOut(entityMap);
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

			}
			catch (Exception e) {
		
				logger.error(e.getMessage(), e);
		
				return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码  deleteAccMatchOut\"}";
		
			}
	}

	@Override
	public String deleteBatchAccMatchOut(List<Map<String, Object>> entityMap)
			throws DataAccessException {
		
		try {

			 accMatchOutMapper.deleteBatchAccMatchOut(entityMap);
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {
	
			logger.error(e.getMessage(), e);
	
			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码  deleteBatchAccMatchOut\"}";
	
		}
	}

	@Override
	public String updateAccMatchOut(Map<String, Object> entityMap)
			throws DataAccessException {
		try {
			accMatchOutMapper.updateAccMatchOut(entityMap);

			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updateAccMatchOut\"}";

		}
	}

	@Override
	public String updateBatchAccMatchOut(List<Map<String, Object>> entityMap)
			throws DataAccessException {
		try {

			accMatchOutMapper.updateBatchAccMatchOut(entityMap);

			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updateBatchAccMatchOut\"}";

		}
	}

	//配套资金支出   打印
	@Override
	public List<Map<String, Object>> queryAccMatchOutPrint(
			Map<String, Object> entityMap) throws DataAccessException {
		
		TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		
		List<Map<String, Object>> list = accMatchOutMapper.queryAccMatchOutPrint(entityMap);
		
		return list ;
	}

}
