package com.chd.hrp.acc.serviceImpl.paper;
/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.chd.base.SessionManager;
import com.chd.base.SysPage;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.hrp.acc.dao.paper.AccEInvoiceMapper;
import com.chd.hrp.acc.dao.paper.AccPaperDetailMapper;
import com.chd.hrp.acc.entity.AccEInvoice;
import com.chd.hrp.acc.service.paper.AccEInvoiceService;
import com.chd.hrp.acc.service.paper.AccPaperDetailService;
import com.github.pagehelper.PageInfo;

@Service("accEInvoiceService")
public class AccEInvoiceServiceImpl implements AccEInvoiceService{

	private static Logger logger = Logger.getLogger(AccEInvoiceServiceImpl.class);
	
	@Resource(name = "accEInvoiceMapper")
	private final AccEInvoiceMapper accEInvoiceMapper = null;

	@Override
	public String addAccEInvoice(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		try {
			Map<String, Object> isExistsMap = new HashMap<String, Object>();
			
			isExistsMap.put("group_id", SessionManager.getGroupId());
			isExistsMap.put("hos_id", SessionManager.getHosId());
			isExistsMap.put("copy_code", SessionManager.getCopyCode());
			isExistsMap.put("ei_code", entityMap.get("ei_code").toString());
			
			AccEInvoice accEInvoice = queryAccEInvoiceByCode(isExistsMap);
			
			if(accEInvoice != null){
				return "{\"error\":\"发票号已存在!请重新输入\"}";
			}
			
			accEInvoiceMapper.addAccEInvoice(entityMap);	
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e.getMessage(), e);
			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码 addAccEInvoice\"}";
		}
	}

	@Override
	public String deleteAccEInvoice(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		try {
			accEInvoiceMapper.deleteAccEInvoice(entityMap);		
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e.getMessage(), e);
			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码 deleteAccEInvoice\"}";
		}
	}

	@Override
	public String updateAccEInvoice(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		try {
			accEInvoiceMapper.updateAccEInvoice(entityMap);	
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e.getMessage(), e);
			return "{\"error\":\"更新失败 数据库异常 请联系管理员! 错误编码 updateAccEInvoice\"}";
		}
	}

	@Override
	public String queryAccEInvoice(
			Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		
		List<Map<String, Object>> list = accEInvoiceMapper.queryAccEInvoice(entityMap);
		return ChdJson.toJson(list);
	}

	@Override
	public AccEInvoice queryAccEInvoiceByCode(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		return accEInvoiceMapper.queryAccEInvoiceByCode(entityMap);
	}

	@Override
	public String deleteBatchAccEInvoice(List<Map<String, Object>> list)
			throws DataAccessException {
		// TODO Auto-generated method stub
		try {
			accEInvoiceMapper.deleteBatchAccEInvoice(list);
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage(),e.getCause()); 
		}
	}

}
