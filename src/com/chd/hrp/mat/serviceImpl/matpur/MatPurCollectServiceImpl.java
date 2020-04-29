package com.chd.hrp.mat.serviceImpl.matpur;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
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
import com.chd.base.util.DateUtil;
import com.chd.hrp.acc.entity.AccYearMonth;
import com.chd.hrp.mat.dao.info.basic.MatTypeDictMapper;
import com.chd.hrp.mat.dao.matpur.MatPurCollectMapper;
import com.chd.hrp.mat.entity.MatTypeDict;
import com.chd.hrp.mat.service.matpur.MatPurCollectService;
import com.chd.hrp.sys.dao.base.SysBaseMapper;
import com.github.pagehelper.PageInfo;

/**
 * @Description: 采购汇总查询
 * @Author: bell
 * @email: bell@s-chd.com
 * @Version: 1.0
 */
@Service("matPurCollectService")
public class MatPurCollectServiceImpl implements MatPurCollectService {

	private static Logger logger = Logger.getLogger(MatPurCollectServiceImpl.class);
 
	@Resource(name = "matPurCollectMapper")
	private final MatPurCollectMapper matPurCollectMapper = null;

	@Resource(name = "sysBaseMapper")
	private final SysBaseMapper sysBaseMapper = null;

	@Resource(name = "matTypeDictMapper")
	private final MatTypeDictMapper matTypeDictMapper = null;

	@Override
	public String queryMatInvPurMain(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		if (sysPage.getTotal() == -1) {

			List<Map<String, Object>> list = matPurCollectMapper.queryMatInvPurMain(entityMap);

			return ChdJson.toJson(list);

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

			List<Map<String, Object>> list = matPurCollectMapper.queryMatInvPurMain(entityMap, rowBounds);

			PageInfo page = new PageInfo(list);

			return ChdJson.toJson(list, page.getTotal());
		}
	}
	
	@Override
	public List<Map<String, Object>> queryMatInvPurMainPrint(
			Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		List<Map<String, Object>> list = matPurCollectMapper.queryMatInvPurMain(entityMap);

		return list;
	}
	
	@Override
	public String queryMatPurCollect(Map<String, Object> entityMap) throws DataAccessException {

		entityMap.put("acc_year", entityMap.get("year"));
		entityMap.put("acc_month", entityMap.get("month"));
		entityMap.put("user_id", SessionManager.getUserId());
		List<AccYearMonth> accYearMonth = sysBaseMapper.queryAccYearMonth(entityMap);

		AccYearMonth acc = new AccYearMonth();
		if (accYearMonth.size() > 0) {
			acc = accYearMonth.get(0);
		} else {
			return ChdJson.toJson(new ArrayList());
		}

		Date begin_date = DateUtil.stringToDate(acc.getBegin_date(), "yyyy-MM-dd");

		Date end_date = DateUtil.stringToDate(acc.getEnd_date(), "yyyy-MM-dd");

		entityMap.put("begin_date", DateUtil.dateToString(begin_date, "yyyy-MM-dd"));

		entityMap.put("end_date", DateUtil.dateToString(end_date, "yyyy-MM-dd"));

		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		if (sysPage.getTotal() == -1) {

			List<Map<String, Object>> list = matPurCollectMapper.queryMatPurCollect(entityMap);

			return ChdJson.toJson(list);

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

			List<Map<String, Object>> list = matPurCollectMapper.queryMatPurCollect(entityMap, rowBounds);

			PageInfo page = new PageInfo(list);

			return ChdJson.toJson(list, page.getTotal());
		}
	}

	@Override
	public String queryMatPurCollectType(Map<String, Object> entityMap) throws DataAccessException {
		entityMap.put("acc_year", entityMap.get("year"));
		entityMap.put("acc_month", entityMap.get("month"));

		List<AccYearMonth> accYearMonth = sysBaseMapper.queryAccYearMonth(entityMap);

		AccYearMonth acc = new AccYearMonth();
		if (accYearMonth.size() > 0) {
			acc = accYearMonth.get(0);
		} else {
			return ChdJson.toJson(new ArrayList());
		}

		Date begin_date = DateUtil.stringToDate(acc.getBegin_date(), "yyyy-MM-dd");

		Date end_date = DateUtil.stringToDate(acc.getEnd_date(), "yyyy-MM-dd");

		entityMap.put("begin_date", DateUtil.dateToString(begin_date, "yyyy-MM-dd"));

		entityMap.put("end_date", DateUtil.dateToString(end_date, "yyyy-MM-dd"));

		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		if (sysPage.getTotal() == -1) {

			List<Map<String, Object>> list = matPurCollectMapper.queryMatPurCollectType(entityMap);

			return ChdJson.toJson(list);

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

			List<Map<String, Object>> list = matPurCollectMapper.queryMatPurCollectType(entityMap, rowBounds);

			PageInfo page = new PageInfo(list);

			return ChdJson.toJson(list, page.getTotal());
		}
	}

	@Override
	public Map<String, Object> ViewSqlMatType(Map<String, Object> entityMap) {
		List<MatTypeDict> listType = matTypeDictMapper.queryMatTypeDict(entityMap);

		StringBuilder sb = new StringBuilder();
		StringBuilder sbview = new StringBuilder();

		for (int i = 0; i < listType.size(); i++) {
			MatTypeDict mt = listType.get(i);
			sb.append("'" + mt.getMat_type_name() + "'");
			sb.append(" t").append(i).append(" ,");
			sbview.append("'" + mt.getMat_type_name() + "'").append("|");
			sbview.append(" t").append(i).append(",");

		}

		String sql_type = sb.toString().substring(0, sb.toString().length() - 1);
		String sbviewTable = sbview.toString().substring(0, sbview.toString().length() - 1);

		entityMap.put("sql_type", "(" + sql_type + ")");
		entityMap.put("sbviewTable", sbviewTable);

		return entityMap;
	}
  
	@Override
	public List<Map<String, Object>> queryMatPurCollectPrint(Map<String, Object> entityMap) throws DataAccessException {


			entityMap.put("acc_year", entityMap.get("year"));
			entityMap.put("acc_month", entityMap.get("month"));
			entityMap.put("user_id", SessionManager.getUserId());
			List<AccYearMonth> accYearMonth = sysBaseMapper.queryAccYearMonth(entityMap);
		
			AccYearMonth acc = new AccYearMonth();
			if (accYearMonth.size() > 0) {
				acc = accYearMonth.get(0);
			} else {
				return new ArrayList();
			}
		
			Date begin_date = DateUtil.stringToDate(acc.getBegin_date(), "yyyy-MM-dd");
		
			Date end_date = DateUtil.stringToDate(acc.getEnd_date(), "yyyy-MM-dd");
		
			entityMap.put("begin_date", DateUtil.dateToString(begin_date, "yyyy-MM-dd"));
		
			entityMap.put("end_date", DateUtil.dateToString(end_date, "yyyy-MM-dd"));


			List<Map<String, Object>> list = matPurCollectMapper.queryMatPurCollect(entityMap);

			return list;

	
	}



}
