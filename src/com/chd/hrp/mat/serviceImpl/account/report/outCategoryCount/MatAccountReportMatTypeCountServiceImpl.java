package com.chd.hrp.mat.serviceImpl.account.report.outCategoryCount;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.MyConfig;
import com.chd.base.SessionManager;
import com.chd.base.SysPage;
import com.chd.base.util.ChdJson;
import com.chd.hrp.mat.dao.account.report.outCategoryCount.MatAccountReportMatTypeCountMapper;
import com.chd.hrp.mat.service.account.report.outCategoryCount.MatAccountReportMatTypeCountService;
import com.github.pagehelper.PageInfo;

/**
 * @Description:
 * 出库分类统计:物资分类统计
 * @Table: 无针对表
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
@Service("matAccountReportMatTypeCountService")
public class MatAccountReportMatTypeCountServiceImpl implements MatAccountReportMatTypeCountService {
	
	@Resource(name = "matAccountReportMatTypeCountMapper")
	private final MatAccountReportMatTypeCountMapper matAccountReportMatTypeCountMapper = null;
	
	@Override
	public String queryMatTypeCount(Map<String, Object> entityMap) throws DataAccessException {
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<Map<String,Object>> list = matAccountReportMatTypeCountMapper.queryMatTypeCount(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<Map<String,Object>> list = matAccountReportMatTypeCountMapper.queryMatTypeCount(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
	}

	@Override
	public List<Map<String, Object>> queryMatTypeCountPrint(Map<String, Object> entityMap) throws DataAccessException {
		entityMap.put("group_id", SessionManager.getGroupId());
		entityMap.put("hos_id", SessionManager.getHosId());
		entityMap.put("copy_code", SessionManager.getCopyCode());
		entityMap.put("user_id", SessionManager.getUserId());
		entityMap.put("show_history", MyConfig.getSysPara("03001"));
		List<Map<String,Object>> list = matAccountReportMatTypeCountMapper.queryMatTypeCount(entityMap);
		return list;
	}

}
