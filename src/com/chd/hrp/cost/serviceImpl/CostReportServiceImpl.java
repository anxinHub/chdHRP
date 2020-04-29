/**
 * @Copyright: Copyright (c) 2015-2-14
 * @Company: 智慧云康（北京）数据科技有限公司
 */

package com.chd.hrp.cost.serviceImpl;

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
import com.chd.hrp.cost.dao.CostReportMapper;
import com.chd.hrp.cost.service.CostReportService;
import com.github.pagehelper.PageInfo;

/**
 * @Title. @Description. 科室直接成本报表<BR>
 * 
 * @Author: LiuYingDuo
 * @email: bell@s-chd.com
 * @Version: 1.0
 */  

@Service("costReportService")
public class CostReportServiceImpl implements CostReportService {

	private static Logger logger = Logger.getLogger(CostReportServiceImpl.class);

	@Resource(name = "costReportMapper")
	private final CostReportMapper costReportMapper = null;

	
	@Override
	public String queryCostTypeDictThead(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		
		List<Map<String, Object>> list = costReportMapper.queryCostTypeDictThead(entityMap);

		return ChdJson.toJsonLower(list);
	}
	
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public String queryCostDeptReport_1(Map<String, Object> entityMap) throws DataAccessException {
		
		List<Map<String, Object>> typeDictList = costReportMapper.queryCostTypeDictThead(entityMap);
		
		entityMap.put("type", ChdJson.toListLower(typeDictList));
		
		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		if (sysPage.getTotal() == -1) {

			List<Map<String, Object>> list = costReportMapper.queryCostDeptReport_1(entityMap);

			return ChdJson.toJsonLower(list);

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

			List<Map<String, Object>> list = costReportMapper.queryCostDeptReport_1(entityMap, rowBounds);

			PageInfo page = new PageInfo(list);

			return ChdJson.toJsonLower(list, page.getTotal());

		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public String queryCostDeptReport_2(Map<String, Object> entityMap) throws DataAccessException {

       List<Map<String, Object>> typeDictList = costReportMapper.queryCostTypeDictThead(entityMap);
		
		entityMap.put("type", ChdJson.toListLower(typeDictList));
		
		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		// 判断是否显示变更记录
		String is_flag = entityMap.get("is_flag") == null ? "" : entityMap.get("is_flag").toString();

		if (sysPage.getTotal() == -1) {

			List<Map<String, Object>> list = costReportMapper.queryCostDeptReport_2(entityMap);

			return ChdJson.toJsonLower(list);

		} else {
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

			List<Map<String, Object>> list = costReportMapper.queryCostDeptReport_2(entityMap, rowBounds);

			PageInfo page = new PageInfo(list);

			return ChdJson.toJsonLower(list, page.getTotal());

		}

	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public String queryCostDeptReport_3(Map<String, Object> entityMap) throws DataAccessException {

		List<Map<String, Object>> typeDictList = costReportMapper.queryCostTypeDictThead(entityMap);
			
		entityMap.put("type", ChdJson.toListLower(typeDictList));
			
		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		// 判断是否显示变更记录
		String is_flag = entityMap.get("is_flag") == null ? "" : entityMap.get("is_flag").toString();

		if (sysPage.getTotal() == -1) {

			List<Map<String, Object>> list = costReportMapper.queryCostDeptReport_3(entityMap);

			return ChdJson.toJsonLower(list);

		} else {
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

			List<Map<String, Object>> list = costReportMapper.queryCostDeptReport_3(entityMap, rowBounds);

			PageInfo page = new PageInfo(list);

			return ChdJson.toJsonLower(list, page.getTotal());

		}
	}
	
	@Override
	public List<Map<String, Object>> queryCostReportPrint1(Map<String, Object> entityMap) throws DataAccessException {

		
        List<Map<String, Object>> typeDictList = costReportMapper.queryCostTypeDictThead(entityMap);
		
		entityMap.put("type", ChdJson.toListLower(typeDictList));
		
		List<Map<String, Object>> list=costReportMapper.queryCostDeptReport_1(entityMap);
		
		return list;

	}
	@Override
	public List<Map<String, Object>> queryCostReportPrint2(Map<String, Object> entityMap) throws DataAccessException {

		List<Map<String, Object>> typeDictList = costReportMapper.queryCostTypeDictThead(entityMap);
			
		entityMap.put("type", ChdJson.toListLower(typeDictList));
			
		List<Map<String, Object>> list=costReportMapper.queryCostDeptReport_2(entityMap);
		
		return list;

	}
	@Override
	public List<Map<String, Object>> queryCostReportPrint3(Map<String, Object> entityMap) throws DataAccessException {

		
		List<Map<String, Object>> typeDictList = costReportMapper.queryCostTypeDictThead(entityMap);
		
		entityMap.put("type", ChdJson.toListLower(typeDictList));
		
		List<Map<String, Object>> list=costReportMapper.queryCostDeptReport_3(entityMap);
		
		return list;

	}



}
