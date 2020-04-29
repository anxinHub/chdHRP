package com.chd.hrp.acc.serviceImpl.tell;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.SysPage;
import com.chd.base.util.ChdJson;
import com.chd.hrp.acc.dao.AccParaMapper;
import com.chd.hrp.acc.dao.tell.AccBankVeriMapper;
import com.chd.hrp.acc.entity.AccBankVeri;
import com.chd.hrp.acc.entity.AccBudgLeder;
import com.chd.hrp.acc.entity.AccPara;
import com.chd.hrp.acc.service.tell.AccBankVeriService;
import com.github.pagehelper.PageInfo;
@Service("accBankVeriService")
public class AccBankVeriServiceImpl implements AccBankVeriService{
	
private static Logger logger = Logger.getLogger(AccBankVeriServiceImpl.class);
	
	@Resource(name = "accBankVeriMapper")
	private final AccBankVeriMapper accBankVeriMapper = null;
	
	@Resource(name = "accParaMapper")
	private final AccParaMapper accParaMapper = null;



	@Override
	public String addBatchAccBankVeri(List<Map<String, Object>> entityMap) throws DataAccessException {
		try {
			entityMap.get(0).put("log_id", "");
			entityMap.get(0).put("note", "批量对账");
			accBankVeriMapper.addAccBankVeriLog(entityMap.get(0));
			accBankVeriMapper.addBatchAccBankVeri(entityMap);			
			return "{\"msg\":\"批量对账成功.\",\"state\":\"true\"}";
		}catch (Exception e) {
			logger.error(e.getMessage(), e);
			return "{\"error\":\"批量对账失败 数据库异常 请联系管理员! 错误编码 addBatchAccBankVeri\"}";
		}
	}

	@Override
	public String queryAccBankVeri(Map<String, Object> entityMap) throws DataAccessException {
		SysPage sysPage = new SysPage();		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
		List<AccBankVeri> list = accBankVeriMapper.queryAccBankVeri(entityMap, rowBounds);
		PageInfo page = new PageInfo(list);
		return ChdJson.toJson(list, page.getTotal());
	}

	@Override
	public AccBankVeri queryAccBankVeriByCode(Map<String, Object> entityMap) throws DataAccessException {
		return accBankVeriMapper.queryAccBankVeriByCode(entityMap);
	}

	@Override
	public String deleteAccBankVeri(Map<String, Object> entityMap) throws DataAccessException {
		try {
			entityMap.put("log_id", "");
			entityMap.put("note", "取消对账");
			accBankVeriMapper.addAccBankVeriLog(entityMap);
			accBankVeriMapper.deleteAccBankVeri(entityMap);
			return "{\"msg\":\"取消对账成功.\",\"state\":\"true\"}";
		}catch (Exception e) {
			logger.error(e.getMessage(), e);
			return "{\"error\":\"取消对账失败 数据库异常 请联系管理员! 错误编码  deleteAccBankVeri\"}";	
		}
	}

	@Override
	public String deleteBatchAccBankVeri(List<Map<String, Object>> entityMap) throws DataAccessException {
		try {
			accBankVeriMapper.deleteBatchAccBankVeri(entityMap);
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		}catch (Exception e) {	
			logger.error(e.getMessage(), e);	
			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码  deleteBatchAccBankVeri\"}";	
		}
	}

	@Override
	public String deleteAccBankVeriBySubjAndDate(Map<String, Object> entityMap) throws DataAccessException {
		try {
			entityMap.put("log_id", "");
			entityMap.put("note", "批量取消对账");
			accBankVeriMapper.addAccBankVeriLog(entityMap);
			accBankVeriMapper.deleteAccBankVeriBySubjAndDate(entityMap);
			return "{\"msg\":\"批量取消对账成功.\",\"state\":\"true\"}";
		}catch (Exception e) {
			logger.error(e.getMessage(), e);
			return "{\"error\":\"取消对账失败 数据库异常 请联系管理员! 错误编码  deleteAccBankVeriBySubjAndDate\"}";
		}
	}

}
