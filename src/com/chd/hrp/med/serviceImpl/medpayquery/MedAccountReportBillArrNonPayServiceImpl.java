package com.chd.hrp.med.serviceImpl.medpayquery;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.SysPage;
import com.chd.base.util.ChdJson;
import com.chd.base.util.JsonListMapUtil;
import com.chd.hrp.med.dao.medpayquery.MedAccountReportBillArrNonPayMapper;
import com.chd.hrp.med.service.medpayquery.MedAccountReportBillArrNonPayService;
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

@Service("medAccountReportBillArrNonPayService")
public class MedAccountReportBillArrNonPayServiceImpl implements MedAccountReportBillArrNonPayService {
	
	private static Logger logger = Logger.getLogger(MedAccountReportBillArrNonPayServiceImpl.class);
	
	@Resource(name = "medAccountReportBillArrNonPayMapper")
	private final MedAccountReportBillArrNonPayMapper medAccountReportBillArrNonPayMapper = null;
	
	@Override
	public String queryMedAccountReportBillArrNonPay(Map<String, Object> entityMap) throws DataAccessException {
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<Map<String,Object>> list = medAccountReportBillArrNonPayMapper.queryMedAccountReportBillArrNonPay(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<Map<String,Object>> list = medAccountReportBillArrNonPayMapper.queryMedAccountReportBillArrNonPay(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
	}
	
	@Override
	public String queryMedAccountReportBillArrNonPayMain(Map<String, Object> entityMap) throws DataAccessException {
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<Map<String,Object>> list = medAccountReportBillArrNonPayMapper.queryMedAccountReportBillArrNonPayMain(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<Map<String,Object>> list = medAccountReportBillArrNonPayMapper.queryMedAccountReportBillArrNonPayMain(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
	}
	
	/**
	 * 发票统计表
	 */
	@Override
	public String queryMedPayBillDetail(Map<String, Object> entityMap) throws DataAccessException {
		SysPage sysPage = new SysPage();
		sysPage = (SysPage) entityMap.get("sysPage");
		if (sysPage.getTotal()==-1){
			List<Map<String,Object>> list = JsonListMapUtil.toListMapLower(medAccountReportBillArrNonPayMapper.queryMedPayBillDetail(entityMap));  
			return ChdJson.toJson(list);
		}else{
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			List<Map<String,Object>> list = JsonListMapUtil.toListMapLower(medAccountReportBillArrNonPayMapper.queryMedPayBillDetail(entityMap, rowBounds));
			PageInfo page = new PageInfo(list);
			return ChdJson.toJson(list, page.getTotal());
			
		}
	}

}
