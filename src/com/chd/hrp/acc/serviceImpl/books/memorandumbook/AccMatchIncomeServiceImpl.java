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
import com.chd.hrp.acc.dao.books.memorandumbook.AccMatchIncomeMapper;
import com.chd.hrp.acc.entity.AccMatchIncome;
import com.chd.hrp.acc.entity.AccMatchInit;
import com.chd.hrp.acc.service.books.memorandumbook.AccMatchIncomeService;
import com.github.pagehelper.PageInfo;

@Service("accMatchIncomeService")
public class AccMatchIncomeServiceImpl implements AccMatchIncomeService{
	
	private static Logger logger = Logger.getLogger(AccMatchIncomeServiceImpl.class);
	
	@Resource(name = "accMatchIncomeMapper")
	private final AccMatchIncomeMapper accMatchIncomeMapper = null;

	@Override
	public String addAccMatchIncome(Map<String, Object> entityMap)
			throws DataAccessException {
		
		try {
			accMatchIncomeMapper.addAccMatchIncome(entityMap);
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);
			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码 addAccMatchIncome\"}";
		}
	}

	@Override
	public String addBatchAccMatchIncome(List<Map<String, Object>> entityMap)
			throws DataAccessException {
		try {
			accMatchIncomeMapper.addBatchAccMatchIncome(entityMap);
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);
			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码 addBatchAccMatchIncome\"}";
		}
	}

	@Override
	public String queryAccMatchIncome(Map<String, Object> entityMap) throws DataAccessException {
		
		
		  SysPage sysPage = new SysPage();
			
			sysPage = (SysPage) entityMap.get("sysPage");
			
			if (sysPage.getTotal()==-1){
				
				List<AccMatchIncome> list = accMatchIncomeMapper.queryAccMatchIncome(entityMap);
				
				return ChdJson.toJson(list);
				
			}else{
				
				RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
				
				List<AccMatchIncome> list = accMatchIncomeMapper.queryAccMatchIncome(entityMap, rowBounds);
				
				PageInfo page = new PageInfo(list);
				
				return ChdJson.toJson(list, page.getTotal());
				
			}
	}


	@Override
	public AccMatchIncome queryAccMatchIncomeByCode(Map<String, Object> entityMap)
			throws DataAccessException {
		
		return accMatchIncomeMapper.queryAccMatchIncomeByCode(entityMap);
	}

	@Override
	public String deleteAccMatchIncome(Map<String, Object> entityMap)
			throws DataAccessException {
		try {

			int state = accMatchIncomeMapper.deleteAccMatchIncome(entityMap);
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

			}
			catch (Exception e) {
		
				logger.error(e.getMessage(), e);
		
				return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码  deleteAccMatchIncome\"}";
		
			}
	}

	@Override
	public String deleteBatchAccMatchIncome(List<Map<String, Object>> entityMap)
			throws DataAccessException {
		
		try {

			int state = accMatchIncomeMapper.deleteBatchAccMatchIncome(entityMap);
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {
	
			logger.error(e.getMessage(), e);
	
			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码  deleteBatchAccMatchIncome\"}";
	
		}
	}

	@Override
	public String updateAccMatchIncome(Map<String, Object> entityMap)
			throws DataAccessException {
		try {
			accMatchIncomeMapper.updateAccMatchIncome(entityMap);

			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updateAccMatchIncome\"}";

		}
	}

	@Override
	public String updateBatchAccMatchIncome(List<Map<String, Object>> entityMap)
			throws DataAccessException {
		try {

			accMatchIncomeMapper.updateBatchAccMatchIncome(entityMap);

			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updateBatchAccMatchIncome\"}";

		}
	}

	//配套资金收入  打印
	@Override
	public List<Map<String, Object>> queryAccMatchIncomePrint(
			Map<String, Object> entityMap) throws DataAccessException {
		
		TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		
		List<Map<String, Object>> list = accMatchIncomeMapper.queryAccMatchIncomePrint(entityMap);
		
		return list ;
	}

}
