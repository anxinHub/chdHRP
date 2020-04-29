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
import com.chd.hrp.med.dao.medpayquery.MedAccountReportInvArrBillUnArrMapper;
import com.chd.hrp.med.service.medpayquery.MedAccountReportInvArrBillUnArrService;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Description:
 * 货到票未到明细表
 * @Table:
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
@Service("medAccountReportInvArrBillUnArrService")
public class MedAccountReportInvArrBillUnArrServiceImpl implements MedAccountReportInvArrBillUnArrService {
	
	private static Logger logger = Logger.getLogger(MedAccountReportInvArrBillUnArrServiceImpl.class);
	
	@Resource(name = "medAccountReportInvArrBillUnArrMapper")
	private final MedAccountReportInvArrBillUnArrMapper medAccountReportInvArrBillUnArrMapper = null;
	@Override
	public String queryMedAccountReportInvArrBillUnArr(Map<String, Object> entityMap) throws DataAccessException {
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<Map<String,Object>> list = medAccountReportInvArrBillUnArrMapper.queryMedAccountReportInvArrBillUnArr(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<Map<String,Object>> list = medAccountReportInvArrBillUnArrMapper.queryMedAccountReportInvArrBillUnArr(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
	}

}
