/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.mat.serviceImpl.purchase.query;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.MyConfig;
import com.chd.base.SessionManager;
import com.chd.base.SysPage;
import com.chd.base.util.ChdJson;
import com.chd.base.util.DateUtil;
import com.chd.hrp.mat.dao.purchase.query.MatPurchaseQueryMapper;
import com.chd.hrp.mat.service.purchase.query.MatPurchaseQueryService;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Description:
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */

@Service("matPurchaseQueryService")
public class MatPurchaseQueryServiceImpl implements MatPurchaseQueryService {

	private static Logger logger = Logger.getLogger(MatPurchaseQueryServiceImpl.class);

	@Resource(name = "matPurchaseQueryMapper")
	private final MatPurchaseQueryMapper matPurchaseQueryMapper = null;
	
	//科室需求计划汇总查询(科室)
	@Override
	public String queryMatPurInvReport(Map<String, Object> entityMap) throws DataAccessException{

		entityMap.put("group_id", SessionManager.getGroupId());
		entityMap.put("hos_id", SessionManager.getHosId());
		entityMap.put("copy_code", SessionManager.getCopyCode());
		entityMap.put("show_history", MyConfig.getSysPara("03001"));
		entityMap.put("user_id", SessionManager.getUserId());
		
		if(entityMap.get("begin_date") != null && !"".equals(entityMap.get("begin_date"))){
			entityMap.put("begin_date", DateUtil.stringToDate(entityMap.get("begin_date").toString(), "yyyy-MM-dd"));
		}
		if(entityMap.get("end_date") != null && !"".equals(entityMap.get("end_date"))){
			entityMap.put("end_date", DateUtil.stringToDate(entityMap.get("end_date").toString(), "yyyy-MM-dd"));
		}
		
		SysPage sysPage = new SysPage();
		sysPage = (SysPage) entityMap.get("sysPage");
		if (sysPage.getTotal()==-1){
			List<Map<String, Object>> list = matPurchaseQueryMapper.queryMatPurInvReport(entityMap);
			
			return ChdJson.toJson(list);
		}else{
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			List<Map<String, Object>> list = matPurchaseQueryMapper.queryMatPurInvReport(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
				
			return ChdJson.toJson(list, page.getTotal());
		}
	}
	
	//打印科室需求计划汇总(科室)--列表打印
	@Override
	public List<Map<String, Object>> printMatPurInvReport(Map<String, Object> entityMap) throws DataAccessException{

		entityMap.put("group_id", SessionManager.getGroupId());
		entityMap.put("hos_id", SessionManager.getHosId());
		entityMap.put("copy_code", SessionManager.getCopyCode());
		entityMap.put("show_history", MyConfig.getSysPara("03001"));
		entityMap.put("user_id", SessionManager.getUserId());

		if(entityMap.get("begin_date") != null && !"".equals(entityMap.get("begin_date"))){
			entityMap.put("begin_date", DateUtil.stringToDate(entityMap.get("begin_date").toString(), "yyyy-MM-dd"));
		}
		if(entityMap.get("end_date") != null && !"".equals(entityMap.get("end_date"))){
			entityMap.put("end_date", DateUtil.stringToDate(entityMap.get("end_date").toString(), "yyyy-MM-dd"));
		}
		
		List<Map<String, Object>> list = matPurchaseQueryMapper.queryMatPurInvReport(entityMap);
		
		return list;
	}
}
