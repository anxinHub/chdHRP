package com.chd.hrp.htc.serviceImpl.relative.report.hos;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.SysPage;
import com.chd.base.util.ChdJson;
import com.chd.hrp.htc.dao.relative.report.hos.HtcRelativeChargeCostHosReportMapper;
import com.chd.hrp.htc.service.relative.report.hos.HtcRelativeChargeCostHosReportService;
import com.github.pagehelper.PageInfo;

@Service("htcRelativeChargeCostHosReportService")
public class HtcRelativeChargeCostHosReportServiceImpl implements HtcRelativeChargeCostHosReportService{
	
	private static Logger logger = Logger.getLogger(HtcRelativeChargeCostHosReportServiceImpl.class);
	
	@Resource(name = "htcRelativeChargeCostHosReportMapper")
	private final HtcRelativeChargeCostHosReportMapper htcRelativeChargeCostHosReportMapper = null;

	
	@Override
	public String queryHtcRelativeChargeCostHosReport(
			Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
        SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if(sysPage.getTotal() == -1){
			
			List<Map<String,Object>> list = htcRelativeChargeCostHosReportMapper.queryHtcRelativeChargeCostHosReport(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<Map<String,Object>> list = htcRelativeChargeCostHosReportMapper.queryHtcRelativeChargeCostHosReport(entityMap, rowBounds);
		
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
		}
	}
}
