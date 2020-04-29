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
import com.chd.hrp.acc.dao.books.memorandumbook.GroupAccMatchIncomeMapper;
import com.chd.hrp.acc.entity.AccMatchIncome;
import com.chd.hrp.acc.entity.AccMatchInit;
import com.chd.hrp.acc.service.books.memorandumbook.AccMatchIncomeService;
import com.chd.hrp.acc.service.books.memorandumbook.GroupAccMatchIncomeService;
import com.github.pagehelper.PageInfo;

@Service("groupAccMatchIncomeService")
public class GroupAccMatchIncomeServiceImpl implements GroupAccMatchIncomeService{
	
	private static Logger logger = Logger.getLogger(GroupAccMatchIncomeServiceImpl.class);
	
	@Resource(name = "groupAccMatchIncomeMapper")
	private final GroupAccMatchIncomeMapper groupAccMatchIncomeMapper = null;

	@Override
	public String addGroupAccMatchIncome(Map<String, Object> entityMap)
			throws DataAccessException {
		
		try {
			groupAccMatchIncomeMapper.addGroupAccMatchIncome(entityMap);
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);
			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码 addAccMatchIncome\"}";
		}
	}

	@Override
	public String addBatchGroupAccMatchIncome(List<Map<String, Object>> entityMap)
			throws DataAccessException {
		try {
			groupAccMatchIncomeMapper.addBatchGroupAccMatchIncome(entityMap);
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);
			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码 addBatchAccMatchIncome\"}";
		}
	}

	@Override
	public String queryGroupAccMatchIncome(Map<String, Object> entityMap) throws DataAccessException {
		
		
		  SysPage sysPage = new SysPage();
			
			sysPage = (SysPage) entityMap.get("sysPage");
			
			if (sysPage.getTotal()==-1){
				
				List<AccMatchIncome> list = groupAccMatchIncomeMapper.queryGroupAccMatchIncome(entityMap);
				
				return ChdJson.toJson(list);
				
			}else{
				
				RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
				
				List<AccMatchIncome> list = groupAccMatchIncomeMapper.queryGroupAccMatchIncome(entityMap, rowBounds);
				
				PageInfo page = new PageInfo(list);
				
				return ChdJson.toJson(list, page.getTotal());
				
			}
	}


	@Override
	public AccMatchIncome queryGroupAccMatchIncomeByCode(Map<String, Object> entityMap)
			throws DataAccessException {
		
		return groupAccMatchIncomeMapper.queryGroupAccMatchIncomeByCode(entityMap);
	}

	@Override
	public String deleteGroupAccMatchIncome(Map<String, Object> entityMap)
			throws DataAccessException {
		try {

			int state = groupAccMatchIncomeMapper.deleteGroupAccMatchIncome(entityMap);
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

			}
			catch (Exception e) {
		
				logger.error(e.getMessage(), e);
		
				return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码  deleteAccMatchIncome\"}";
		
			}
	}

	@Override
	public String deleteBatchGroupAccMatchIncome(List<Map<String, Object>> entityMap)
			throws DataAccessException {
		
		try {

			int state = groupAccMatchIncomeMapper.deleteBatchGroupAccMatchIncome(entityMap);
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {
	
			logger.error(e.getMessage(), e);
	
			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码  deleteBatchAccMatchIncome\"}";
	
		}
	}

	@Override
	public String updateGroupAccMatchIncome(Map<String, Object> entityMap)
			throws DataAccessException {
		try {
			groupAccMatchIncomeMapper.updateGroupAccMatchIncome(entityMap);

			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updateAccMatchIncome\"}";

		}
	}

	@Override
	public String updateBatchGroupAccMatchIncome(List<Map<String, Object>> entityMap)
			throws DataAccessException {
		try {

			groupAccMatchIncomeMapper.updateBatchGroupAccMatchIncome(entityMap);

			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updateBatchAccMatchIncome\"}";

		}
	}

	//配套资金收入  打印
	@Override
	public List<Map<String, Object>> queryGroupAccMatchIncomePrint(
			Map<String, Object> entityMap) throws DataAccessException {
		
		TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		
		List<Map<String, Object>> list = groupAccMatchIncomeMapper.queryGroupAccMatchIncomePrint(entityMap);
		
		return list ;
	}

}
