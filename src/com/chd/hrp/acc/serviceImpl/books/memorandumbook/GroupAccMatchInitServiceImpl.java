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
import com.chd.hrp.acc.dao.books.memorandumbook.GroupAccMatchInitMapper;
import com.chd.hrp.acc.entity.AccBudgLeder;
import com.chd.hrp.acc.entity.AccItialIncome;
import com.chd.hrp.acc.entity.AccMatchInit;
import com.chd.hrp.acc.entity.AccSubjType;
import com.chd.hrp.acc.service.books.memorandumbook.AccMatchInitService;
import com.chd.hrp.acc.service.books.memorandumbook.GroupAccMatchInitService;
import com.chd.hrp.acc.serviceImpl.AccBalanceAdjustmentServiceImpl;
import com.github.pagehelper.PageInfo;

@Service("groupAccMatchInitService")
public class GroupAccMatchInitServiceImpl implements GroupAccMatchInitService{
	
	private static Logger logger = Logger.getLogger(GroupAccMatchInitServiceImpl.class);
	
	@Resource(name = "groupAccMatchInitMapper")
	private final GroupAccMatchInitMapper groupAccMatchInitMapper = null;

	@Override
	public String addGroupAccMatchInit(Map<String, Object> entityMap)
			throws DataAccessException {
		AccMatchInit accMatchInit = groupAccMatchInitMapper.queryGroupAccMatchInitByCode(entityMap);

		if (accMatchInit != null) {
			return "{\"error\":\"编码：" + entityMap.get("group_id").toString() + "重复.\"}";
		}
		
		try {
			groupAccMatchInitMapper.addGroupAccMatchInit(entityMap);
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);
			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码 addAccMatchInit\"}";
		}
	}

	@Override
	public String addBatchGroupAccMatchInit(List<Map<String, Object>> entityMap)
			throws DataAccessException {
		try {
			groupAccMatchInitMapper.addBatchGroupAccMatchInit(entityMap);
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);
			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码 addBatchAccMatchInit\"}";
		}
	}

	@Override
	public String queryGroupAccMatchInit(Map<String, Object> entityMap) throws DataAccessException {
		
         SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<AccMatchInit> list = groupAccMatchInitMapper.queryGroupAccMatchInit(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<AccMatchInit> list = groupAccMatchInitMapper.queryGroupAccMatchInit(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
	}


	@Override
	public AccMatchInit queryGroupAccMatchInitByCode(Map<String, Object> entityMap)
			throws DataAccessException {
		
		return groupAccMatchInitMapper.queryGroupAccMatchInitByCode(entityMap);
	}

	@Override
	public String deleteGroupAccMatchInit(Map<String, Object> entityMap)
			throws DataAccessException {
		try {

			int state = groupAccMatchInitMapper.deleteGroupAccMatchInit(entityMap);
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

			}
			catch (Exception e) {
		
				logger.error(e.getMessage(), e);
		
				return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码  deleteAccMatchInit\"}";
		
			}
	}

	@Override
	public String deleteBatchGroupAccMatchInit(List<Map<String, Object>> entityMap)
			throws DataAccessException {
		
		try {

			int state = groupAccMatchInitMapper.deleteBatchGroupAccMatchInit(entityMap);
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {
	
			logger.error(e.getMessage(), e);
	
			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码  deleteBatchAccMatchInit\"}";
	
		}
	}

	@Override
	public String updateGroupAccMatchInit(Map<String, Object> entityMap)
			throws DataAccessException {
		try {
			groupAccMatchInitMapper.updateGroupAccMatchInit(entityMap);

			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updateAccMatchInit\"}";

		}
	}

	@Override
	public String updateBatchGroupAccMatchInit(List<Map<String, Object>> entityMap)
			throws DataAccessException {
		try {

			groupAccMatchInitMapper.updateBatchGroupAccMatchInit(entityMap);

			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updateBatchAccMatchInit\"}";

		}
	}

	@Override
	public List<Map<String, Object>> queryGroupAccMatchInitPrint(
			Map<String, Object> entityMap) throws DataAccessException {
		
		TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		
		List<Map<String, Object>> list = groupAccMatchInitMapper.queryGroupAccMatchInitPrint(entityMap);
		
		return list ;
	}

}
