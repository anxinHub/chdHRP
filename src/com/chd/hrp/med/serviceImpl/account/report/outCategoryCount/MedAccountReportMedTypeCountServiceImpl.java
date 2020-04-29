package com.chd.hrp.med.serviceImpl.account.report.outCategoryCount;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.SysPage;
import com.chd.base.util.ChdJson;
import com.chd.hrp.med.dao.account.report.outCategoryCount.MedAccountReportMedTypeCountMapper;
import com.chd.hrp.med.service.account.report.outCategoryCount.MedAccountReportMedTypeCountService;
import com.github.pagehelper.PageInfo;

/**
 * @Description:
 * 出库分类统计:药品分类统计
 * @Table: 无针对表
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
@Service("medAccountReportMedTypeCountService")
public class MedAccountReportMedTypeCountServiceImpl implements MedAccountReportMedTypeCountService {
	
	@Resource(name = "medAccountReportMedTypeCountMapper")
	private final MedAccountReportMedTypeCountMapper medAccountReportMedTypeCountMapper = null;
	
	@Override
	public String queryMedTypeCount(Map<String, Object> entityMap) throws DataAccessException {
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<Map<String,Object>> list = medAccountReportMedTypeCountMapper.queryMedTypeCount(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<Map<String,Object>> list = medAccountReportMedTypeCountMapper.queryMedTypeCount(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
	}

}
