package com.chd.hrp.acc.serviceImpl.books.memorandumbook;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.chd.base.SysPage;
import com.chd.base.util.ChdJson;
import com.chd.hrp.acc.dao.books.memorandumbook.GroupAccItialIncomeMapper;
import com.chd.hrp.acc.dao.books.subjaccount.GroupAccSubjLedgerMapper;
import com.chd.hrp.acc.entity.AccItialIncome;
import com.chd.hrp.acc.entity.AccSubjLedger;
import com.chd.hrp.acc.service.books.memorandumbook.AccItialIncomeService;
import com.chd.hrp.acc.service.books.memorandumbook.GroupAccItialIncomeService;
import com.github.pagehelper.PageInfo;

@Service("groupAccItialIncomeService")
public class GroupAccItialIncomeServiceImpl implements GroupAccItialIncomeService{
	
	private static Logger logger = Logger.getLogger(GroupAccItialIncomeServiceImpl.class);
	
	@Resource(name = "groupAccItialIncomeMapper")
	private final GroupAccItialIncomeMapper groupAccItialIncomeMapper = null;
	
	@Resource(name = "groupAccSubjLedgerMapper")
	private final GroupAccSubjLedgerMapper groupAccSubjLedgerMapper = null;

	@Override
	public String queryGroupAccItialIncome(Map<String, Object> entityMap)
			throws DataAccessException {
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<AccItialIncome> list = groupAccItialIncomeMapper.queryGroupAccItialIncome(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<AccItialIncome> list = groupAccItialIncomeMapper.queryGroupAccItialIncome(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
	}

	@Override
	public String addGroupAccItialIncome(Map<String, Object> entityMap)
			throws DataAccessException {
		
		AccItialIncome accItialIncome = groupAccItialIncomeMapper.queryGroupAccItialIncomeByCode(entityMap);

		if (accItialIncome != null) {
			
			return "{\"error\":\"编码：" + accItialIncome.getSub_id().toString() + "重复.\"}";
		
		}
		
		try {
			groupAccItialIncomeMapper.addGroupAccItialIncome(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {
			
			logger.error(e.getMessage(), e);
			
			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码 addAccItialIncome\"}";
		}
	}

	@Override
	public String addBatchGroupAccItialIncome(List<Map<String, Object>> entityMap)
			throws DataAccessException {
		
		try {
			
			groupAccItialIncomeMapper.addBatchGroupAccItialIncome(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";
		}
		catch (Exception e) {
			
			logger.error(e.getMessage(), e);
			
			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码 addBatchAccMatchInit\"}";
		}
	}

	@Override
	public String deleteBatchGroupAccItialIncome(List<Map<String, Object>> entityMap)
			throws DataAccessException {
		
		try {

			groupAccItialIncomeMapper.deleteBatchGroupAccItialIncome(entityMap);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

			}
			catch (Exception e) {
		
				logger.error(e.getMessage(), e);
		
				return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码  deleteBatchAccItialIncome\"}";
		
			}
	}

	@Override
	public String deleteGroupAccItialIncome(Map<String, Object> entityMap)
			throws DataAccessException {
		
		try {

			groupAccItialIncomeMapper.deleteGroupAccItialIncome(entityMap);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {
	
			logger.error(e.getMessage(), e);
	
			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码  deleteBatchAccMatchInit\"}";
	
		}
	}

	@Override
	public String queryGroupAccVouchImport(Map<String, Object> entityMap)
			throws DataAccessException {
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<AccItialIncome> list = groupAccItialIncomeMapper.queryGroupAccVouchImport(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<AccItialIncome> list = groupAccItialIncomeMapper.queryGroupAccVouchImport(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
	}

	@Override
	public List<Map<String, Object>> queryGroupAccItialIncomePrint(
			Map<String, Object> entityMap) throws DataAccessException {
		
		TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		List<Map<String, Object>> resList=groupAccItialIncomeMapper.queryGroupAccItialIncomePrint(entityMap);
		 
		 return resList;
	}

}
