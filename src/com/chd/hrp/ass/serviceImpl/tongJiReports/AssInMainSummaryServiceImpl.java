/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */

package com.chd.hrp.ass.serviceImpl.tongJiReports;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.SessionManager;
import com.chd.base.SysPage;
import com.chd.base.util.ChdJson;
import com.chd.hrp.ass.dao.tongJiReports.AssInMainSummaryMapper;
import com.chd.hrp.ass.entity.in.AssInMainSpecial;
import com.chd.hrp.ass.service.tongJiReports.AssInMainSummaryService;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Description: 050801 入库金额 根据库房 供应商 汇总
 * @Table:
 * @Author: bell
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Service("assInMainSummaryService")
public class AssInMainSummaryServiceImpl implements AssInMainSummaryService {    

	private static Logger logger = Logger.getLogger(AssInMainSummaryServiceImpl.class);
	// 引入DAO操作
	@Resource(name = "assInMainSummaryMapper")
	private final AssInMainSummaryMapper assInMainSummaryMapper = null;

	/**
	 * @Description 查询结果集 资产入库按库房 供应商汇总报表查询<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String queryAssInMainBySummary(Map<String, Object> entityMap) throws DataAccessException {

		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

		List<Map<String, Object>> list = null;
		
		if(entityMap.get("bus_type") != null && !entityMap.get("bus_type").equals("")){
			if(entityMap.get("bus_type").equals("0")){//采购入库处理
				list = assInMainSummaryMapper.queryAssInMainBySummaryByIn(entityMap, rowBounds);
			}else if(entityMap.get("bus_type").equals("1")){//采购退货处理
				list = assInMainSummaryMapper.queryAssInMainBySummaryByBack(entityMap, rowBounds);
			}
			
		}else{
			list = assInMainSummaryMapper.queryAssInMainBySummary(entityMap, rowBounds);
		}
		

		PageInfo page = new PageInfo(list);

		return ChdJson.toJson(list, page.getTotal());

	}

	@Override
	public String queryAssInMainSummaryByType(Map<String, Object> entityMap) throws DataAccessException {
		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

		List<Map<String, Object>> list = null;

		list = assInMainSummaryMapper.queryAssInMainSummaryByType(entityMap, rowBounds);

		PageInfo page = new PageInfo(list);

		return ChdJson.toJson(list, page.getTotal());
	}

	@Override
	public List<Map<String, Object>> queryAssInMainSummaryPrint(
			Map<String, Object> entityMap) throws DataAccessException {
		entityMap.put("group_id", SessionManager.getGroupId());
		entityMap.put("hos_id", SessionManager.getHosId());
		entityMap.put("copy_code", SessionManager.getCopyCode());
		entityMap.put("user_id", SessionManager.getUserId());
		List<Map<String, Object>> list = null;
		if(entityMap.get("bus_type") != null && !entityMap.get("bus_type").equals("")){
			if(entityMap.get("bus_type").equals("0")){//采购入库处理
				list = assInMainSummaryMapper.queryAssInMainSummaryPrintByIn(entityMap);
			}else if(entityMap.get("bus_type").equals("1")){//采购退货处理
				list = assInMainSummaryMapper.queryAssInMainSummaryPrintByBack(entityMap);
			}
			
		}else{
			list =  assInMainSummaryMapper.queryAssInMainSummaryPrint(entityMap);
		}
	
		return list;
	}

	@Override
	public List<Map<String, Object>> queryAssInMainSummaryByTypePrint(
			Map<String, Object> entityMap) throws DataAccessException {
		entityMap.put("group_id", SessionManager.getGroupId());
		entityMap.put("hos_id", SessionManager.getHosId());
		entityMap.put("copy_code", SessionManager.getCopyCode());
		entityMap.put("user_id", SessionManager.getUserId());
		
		List<Map<String, Object>> list = assInMainSummaryMapper.queryAssInMainSummaryByTypePrint(entityMap);
	
		return list;
	}

	@Override
	public String queryInMainSituation(Map<String, Object> entityMap) throws DataAccessException {
		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		if (sysPage.getTotal() == -1) {

			List<Map<String, Object>> list = assInMainSummaryMapper.queryInMainSituation(entityMap);

			return ChdJson.toJson(list);

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

			List<Map<String, Object>> list =  assInMainSummaryMapper.queryInMainSituation(entityMap, rowBounds);

			PageInfo page = new PageInfo(list);

			return ChdJson.toJson(list, page.getTotal());

		}
	}

	@Override
	public List<Map<String, Object>> queryInMainSituationPrint(Map<String, Object> entityMap)
			throws DataAccessException {
		entityMap.put("group_id", SessionManager.getGroupId());
		entityMap.put("hos_id", SessionManager.getHosId());
		entityMap.put("copy_code", SessionManager.getCopyCode());
		entityMap.put("user_id", SessionManager.getUserId());
		
		List<Map<String, Object>> list = assInMainSummaryMapper.queryInMainSituationPrint(entityMap);
	
		return list;
	}

}
