package com.chd.hrp.htc.serviceImpl.task.report.hos;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.SysPage;
import com.chd.base.util.ChdJson;
import com.chd.hrp.htc.dao.task.report.hos.HtcTaskChargeCostHosReportMapper;
import com.chd.hrp.htc.service.task.report.hos.HtcTaskChargeCostHosReportService;
import com.github.pagehelper.PageInfo;

@Service("htcTaskChargeCostHosReportService")
public class HtcTaskChargeCostHosReportServiceImpl implements HtcTaskChargeCostHosReportService{
	
private static Logger logger = Logger.getLogger(HtcTaskChargeCostHosReportServiceImpl.class);
	
	@Resource(name = "htcTaskChargeCostHosReportMapper")
	private final HtcTaskChargeCostHosReportMapper htcTaskChargeCostHosReportMapper = null;

	@Override
	public String queryHtcTaskChargeCostHosReport(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		 SysPage sysPage = new SysPage();
			
			sysPage = (SysPage) entityMap.get("sysPage");
			
			if(sysPage.getTotal() == -1){
				                                       
				List<Map<String,Object>> list = htcTaskChargeCostHosReportMapper.queryHtcTaskChargeCostHosReport(entityMap);
				
				return ChdJson.toJson(list);
				
			}else{
				RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
				
				List<Map<String,Object>> list = htcTaskChargeCostHosReportMapper.queryHtcTaskChargeCostHosReport(entityMap, rowBounds);
			
				PageInfo page = new PageInfo(list);
				
				return ChdJson.toJson(list, page.getTotal());
			}
	}

}
