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
import com.chd.hrp.acc.dao.books.memorandumbook.GroupAccMatchOutMapper;
import com.chd.hrp.acc.entity.AccMatchIncome;
import com.chd.hrp.acc.entity.AccMatchOut;
import com.chd.hrp.acc.service.books.memorandumbook.AccMatchOutService;
import com.chd.hrp.acc.service.books.memorandumbook.GroupAccMatchOutService;
import com.github.pagehelper.PageInfo;

@Service("groupAccMatchOutService")
public class GroupAccMatchOutServiceImpl implements GroupAccMatchOutService{
	
	private static Logger logger = Logger.getLogger(GroupAccMatchOutServiceImpl.class);
	
	@Resource(name = "groupAccMatchOutMapper")
	private final GroupAccMatchOutMapper groupAccMatchOutMapper = null;

	@Override
	public String addGroupAccMatchOut(Map<String, Object> entityMap)
			throws DataAccessException {
		try {
			groupAccMatchOutMapper.addGroupAccMatchOut(entityMap);
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);
			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码 addAccMatchOut\"}";
		}
	}

	@Override
	public String addBatchGroupAccMatchOut(List<Map<String, Object>> entityMap)
			throws DataAccessException {
		try {
			groupAccMatchOutMapper.addBatchGroupAccMatchOut(entityMap);
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);
			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码 addBatchAccMatchOut\"}";
		}
	}

	@Override
	public String queryGroupAccMatchOut(Map<String, Object> entityMap) throws DataAccessException {
		
		
		  SysPage sysPage = new SysPage();
			
			sysPage = (SysPage) entityMap.get("sysPage");
			
			if (sysPage.getTotal()==-1){
				
				List<AccMatchOut> list = groupAccMatchOutMapper.queryGroupAccMatchOut(entityMap);
				
				return ChdJson.toJson(list);
				
			}else{
				
				RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
				
				List<AccMatchOut> list = groupAccMatchOutMapper.queryGroupAccMatchOut(entityMap, rowBounds);
				
				PageInfo page = new PageInfo(list);
				
				return ChdJson.toJson(list, page.getTotal());
				
			}
	}


	@Override
	public AccMatchOut queryGroupAccMatchOutByCode(Map<String, Object> entityMap)
			throws DataAccessException {
		
		return groupAccMatchOutMapper.queryGroupAccMatchOutByCode(entityMap);
	}

	@Override
	public String deleteGroupAccMatchOut(Map<String, Object> entityMap)
			throws DataAccessException {
		try {

			 groupAccMatchOutMapper.deleteGroupAccMatchOut(entityMap);
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

			}
			catch (Exception e) {
		
				logger.error(e.getMessage(), e);
		
				return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码  deleteAccMatchOut\"}";
		
			}
	}

	@Override
	public String deleteBatchGroupAccMatchOut(List<Map<String, Object>> entityMap)
			throws DataAccessException {
		
		try {

			 groupAccMatchOutMapper.deleteBatchGroupAccMatchOut(entityMap);
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {
	
			logger.error(e.getMessage(), e);
	
			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码  deleteBatchAccMatchOut\"}";
	
		}
	}

	@Override
	public String updateGroupAccMatchOut(Map<String, Object> entityMap)
			throws DataAccessException {
		try {
			groupAccMatchOutMapper.updateGroupAccMatchOut(entityMap);

			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updateAccMatchOut\"}";

		}
	}

	@Override
	public String updateBatchGroupAccMatchOut(List<Map<String, Object>> entityMap)
			throws DataAccessException {
		try {

			groupAccMatchOutMapper.updateBatchGroupAccMatchOut(entityMap);

			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updateBatchAccMatchOut\"}";

		}
	}

	//配套资金支出   打印
	@Override
	public List<Map<String, Object>> queryGroupAccMatchOutPrint(
			Map<String, Object> entityMap) throws DataAccessException {
		
		TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		
		List<Map<String, Object>> list = groupAccMatchOutMapper.queryGroupAccMatchOutPrint(entityMap);
		
		return list ;
	}

}
