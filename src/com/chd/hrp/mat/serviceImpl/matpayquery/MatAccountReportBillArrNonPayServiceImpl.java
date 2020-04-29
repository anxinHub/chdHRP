package com.chd.hrp.mat.serviceImpl.matpayquery;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.SessionManager;
import com.chd.base.SysPage;
import com.chd.base.util.ChdJson;
import com.chd.base.util.JsonListMapUtil;
import com.chd.hrp.mat.dao.matpayquery.MatAccountReportBillArrNonPayMapper;
import com.chd.hrp.mat.service.matpayquery.MatAccountReportBillArrNonPayService;
import com.github.pagehelper.PageInfo;

/**
 * @Description:
 * 票到款未付明细表
 * @Table:
 * 无针对表
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */

@Service("matAccountReportBillArrNonPayService")
public class MatAccountReportBillArrNonPayServiceImpl implements MatAccountReportBillArrNonPayService {
	
	private static Logger logger = Logger.getLogger(MatAccountReportBillArrNonPayServiceImpl.class);
	
	@Resource(name = "matAccountReportBillArrNonPayMapper")
	private final MatAccountReportBillArrNonPayMapper matAccountReportBillArrNonPayMapper = null;
	
	@Override
	public String queryMatAccountReportBillArrNonPay(Map<String, Object> entityMap) throws DataAccessException {
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<Map<String,Object>> list = matAccountReportBillArrNonPayMapper.queryMatAccountReportBillArrNonPay(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<Map<String,Object>> list = matAccountReportBillArrNonPayMapper.queryMatAccountReportBillArrNonPay(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
	}
	@Override
	public List<Map<String,Object>> queryMatAccountReportBillArrNonPayPrint(Map<String,Object> entityMap) throws DataAccessException{
					
				entityMap.put("group_id", SessionManager.getGroupId());
						
				entityMap.put("hos_id", SessionManager.getHosId());
						
				entityMap.put("copy_code", SessionManager.getCopyCode());
			
		List<Map<String,Object>> list = matAccountReportBillArrNonPayMapper.queryMatAccountReportBillArrNonPay(entityMap);
		
		return list;
	}
	@Override
	public String queryMatAccountReportBillArrNonPayMain(Map<String, Object> entityMap) throws DataAccessException {
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<Map<String,Object>> list = matAccountReportBillArrNonPayMapper.queryMatAccountReportBillArrNonPayMain(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<Map<String,Object>> list = matAccountReportBillArrNonPayMapper.queryMatAccountReportBillArrNonPayMain(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
	}
	
	@Override
	public List<Map<String,Object>> queryMatAccountReportBillArrNonPayMainPrint(Map<String,Object> entityMap) throws DataAccessException{
				entityMap.put("group_id", SessionManager.getGroupId());
				
				entityMap.put("hos_id", SessionManager.getHosId());
						
				entityMap.put("copy_code", SessionManager.getCopyCode());
		List<Map<String,Object>> list = matAccountReportBillArrNonPayMapper.queryMatAccountReportBillArrNonPayMain(entityMap);
		
		return list;
	}
	/**
	 * 发票统计表
	 */
	@Override
	public String queryMatPayBillDetail(Map<String, Object> entityMap) throws DataAccessException {
		SysPage sysPage = new SysPage();
		sysPage = (SysPage) entityMap.get("sysPage");
		if (sysPage.getTotal()==-1){
			List<Map<String,Object>> list = matAccountReportBillArrNonPayMapper.queryMatPayBillDetail(entityMap);  
			return ChdJson.toJsonLower(list);
		}else{
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			List<Map<String,Object>> list = matAccountReportBillArrNonPayMapper.queryMatPayBillDetail(entityMap, rowBounds);
			PageInfo page = new PageInfo(list);
			return ChdJson.toJsonLower(list, page.getTotal());
			
		}
	}
	/**
	 * 票到款未付总表
	 */
	@Override
	public String queryMatAccountReportBillArrNonPaySum(Map<String, Object> entityMap) throws DataAccessException {
		SysPage sysPage = new SysPage();
		sysPage = (SysPage) entityMap.get("sysPage");
		if (sysPage.getTotal()==-1){
			List<Map<String,Object>> list = JsonListMapUtil.toListMapLower(matAccountReportBillArrNonPayMapper.queryMatAccountReportBillArrNonPaySum(entityMap));  
			return ChdJson.toJson(list);
		}else{
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			List<Map<String,Object>> list = JsonListMapUtil.toListMapLower(matAccountReportBillArrNonPayMapper.queryMatAccountReportBillArrNonPaySum(entityMap, rowBounds));
			PageInfo page = new PageInfo(list);
			return ChdJson.toJson(list, page.getTotal());
			
		}
	}

	@Override
	public List<Map<String, Object>> queryMatAccountReportBillArrNonPaySumPrint(Map<String, Object> entityMap)
			throws DataAccessException {
		entityMap.put("group_id", SessionManager.getGroupId());
		entityMap.put("hos_id", SessionManager.getHosId());
		entityMap.put("copy_code", SessionManager.getCopyCode());
		List<Map<String,Object>> list = JsonListMapUtil.toListMapLower(matAccountReportBillArrNonPayMapper.queryMatAccountReportBillArrNonPaySum(entityMap));  
		return list;
	}
	@Override
	public List<Map<String, Object>> queryMatPayBillDetailPrint(Map<String, Object> entityMap)
			throws DataAccessException {
		List<Map<String,Object>> list = matAccountReportBillArrNonPayMapper.queryMatPayBillDetail(entityMap);  
		return list;
	}
	/**
	 * 汇总查询供应商 应付款,已付款,未付款总额
	 */
	@Override
	public String querySupInPayUnpaidAmountMoney(Map<String, Object> entityMap) {
		 String begin_date = (entityMap.get("begin_date")==null 
	        		|| StringUtils.isBlank(entityMap.get("begin_date").toString()))?"1970-01-01":entityMap.get("begin_date").toString();
		 entityMap.put("begin_date", begin_date); 
		 List<Map<String,Object>> list = matAccountReportBillArrNonPayMapper.querySupInPayUnpaidAmountMoney(entityMap);  
		return ChdJson.toJson(list);
		
	}	
	
	/**
	 * 汇总查询供应商 应付款,已付款,未付款总额-页面打印
	 */
	@Override
	public List<Map<String,Object>> querySupInPayUnpaidAmountMoneyPrint(Map<String, Object> entityMap) throws DataAccessException {
				
		entityMap.put("group_id", SessionManager.getGroupId());

		entityMap.put("hos_id", SessionManager.getHosId());

		entityMap.put("copy_code", SessionManager.getCopyCode());

		entityMap.put("user_id", SessionManager.getUserId());
 
		List<Map<String, Object>> list = matAccountReportBillArrNonPayMapper.querySupInPayUnpaidAmountMoney(entityMap);
		return list;
		
	}


}
