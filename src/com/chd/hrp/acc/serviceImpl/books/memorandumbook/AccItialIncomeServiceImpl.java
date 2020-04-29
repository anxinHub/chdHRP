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
import com.chd.hrp.acc.dao.books.memorandumbook.AccItialIncomeMapper;
import com.chd.hrp.acc.dao.books.subjaccount.AccSubjLedgerMapper;
import com.chd.hrp.acc.entity.AccItialIncome;
import com.chd.hrp.acc.entity.AccSubjLedger;
import com.chd.hrp.acc.service.books.memorandumbook.AccItialIncomeService;
import com.github.pagehelper.PageInfo;

@Service("accItialIncomeService")
public class AccItialIncomeServiceImpl implements AccItialIncomeService{
	
	private static Logger logger = Logger.getLogger(AccItialIncomeServiceImpl.class);
	
	@Resource(name = "accItialIncomeMapper")
	private final AccItialIncomeMapper accItialIncomeMapper = null;
	
	@Resource(name = "accSubjLedgerMapper")
	private final AccSubjLedgerMapper accSubjLedgerMapper = null;

	@Override
	public String queryAccItialIncome(Map<String, Object> entityMap)
			throws DataAccessException {
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<AccItialIncome> list = accItialIncomeMapper.queryAccItialIncome(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<AccItialIncome> list = accItialIncomeMapper.queryAccItialIncome(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
	}

	@Override
	public String addAccItialIncome(Map<String, Object> entityMap)
			throws DataAccessException {
		
		AccItialIncome accItialIncome = accItialIncomeMapper.queryAccItialIncomeByCode(entityMap);

		if (accItialIncome != null) {
			
			return "{\"error\":\"编码：" + accItialIncome.getSub_id().toString() + "重复.\"}";
		
		}
		
		try {
			accItialIncomeMapper.addAccItialIncome(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {
			
			logger.error(e.getMessage(), e);
			
			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码 addAccItialIncome\"}";
		}
	}

	@Override
	public String addBatchAccItialIncome(List<Map<String, Object>> entityMap)
			throws DataAccessException {
		
		try {
			
			accItialIncomeMapper.addBatchAccItialIncome(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";
		}
		catch (Exception e) {
			
			logger.error(e.getMessage(), e);
			
			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码 addBatchAccMatchInit\"}";
		}
	}

	@Override
	public String deleteBatchAccItialIncome(List<Map<String, Object>> entityMap)
			throws DataAccessException {
		
		try {

			accItialIncomeMapper.deleteBatchAccItialIncome(entityMap);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

			}
			catch (Exception e) {
		
				logger.error(e.getMessage(), e);
		
				return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码  deleteBatchAccItialIncome\"}";
		
			}
	}

	@Override
	public String deleteAccItialIncome(Map<String, Object> entityMap)
			throws DataAccessException {
		
		try {

			accItialIncomeMapper.deleteAccItialIncome(entityMap);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {
	
			logger.error(e.getMessage(), e);
	
			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码  deleteBatchAccMatchInit\"}";
	
		}
	}

	@Override
	public String queryAccVouchImport(Map<String, Object> entityMap)
			throws DataAccessException {
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<AccItialIncome> list = accItialIncomeMapper.queryAccVouchImport(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<AccItialIncome> list = accItialIncomeMapper.queryAccVouchImport(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
	}

	@Override
	public List<Map<String, Object>> queryAccItialIncomePrint(
			Map<String, Object> entityMap) throws DataAccessException {
		
		TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		List<Map<String, Object>> resList=accItialIncomeMapper.queryAccItialIncomePrint(entityMap);
		 
		 return resList;
	}

}
