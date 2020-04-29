package com.chd.hrp.med.serviceImpl.medpur;

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

import com.chd.base.SysPage;
import com.chd.base.util.ChdJson;
import com.chd.base.util.DateUtil;
import com.chd.hrp.acc.entity.AccYearMonth;
import com.chd.hrp.med.dao.info.basic.MedTypeDictMapper;
import com.chd.hrp.med.dao.medpur.MedPurCollectMapper;
import com.chd.hrp.med.entity.MedTypeDict;
import com.chd.hrp.med.service.medpur.MedPurCollectService;
import com.chd.hrp.sys.dao.base.SysBaseMapper;
import com.github.pagehelper.PageInfo;

/**
 * @Description: 采购汇总查询
 * @Author: bell
 * @email: bell@s-chd.com
 * @Version: 1.0
 */
@Service("medPurCollectService")
public class MedPurCollectServiceImpl implements MedPurCollectService {

	private static Logger logger = Logger.getLogger(MedPurCollectServiceImpl.class);

	@Resource(name = "medPurCollectMapper")
	private final MedPurCollectMapper medPurCollectMapper = null;

	@Resource(name = "sysBaseMapper")
	private final SysBaseMapper sysBaseMapper = null;

	@Resource(name = "medTypeDictMapper")
	private final MedTypeDictMapper medTypeDictMapper = null;

	@Override
	public String queryMedPurCollect(Map<String, Object> entityMap) throws DataAccessException {

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

			List<Map<String, Object>> list = medPurCollectMapper.queryMedPurCollect(entityMap);

			return ChdJson.toJson(list);

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

			List<Map<String, Object>> list = medPurCollectMapper.queryMedPurCollect(entityMap, rowBounds);

			PageInfo page = new PageInfo(list);

			return ChdJson.toJson(list, page.getTotal());
		}
	}

	@Override
	public String queryMedPurCollectType(Map<String, Object> entityMap) throws DataAccessException {
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

			List<Map<String, Object>> list = medPurCollectMapper.queryMedPurCollectType(entityMap);

			return ChdJson.toJson(list);

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

			List<Map<String, Object>> list = medPurCollectMapper.queryMedPurCollectType(entityMap, rowBounds);

			PageInfo page = new PageInfo(list);

			return ChdJson.toJson(list, page.getTotal());
		}
	}

	@Override
	public Map<String, Object> ViewSqlMedType(Map<String, Object> entityMap) {
		List<MedTypeDict> listType = medTypeDictMapper.queryMedTypeDict(entityMap);

		StringBuilder sb = new StringBuilder();
		StringBuilder sbview = new StringBuilder();

		for (int i = 0; i < listType.size(); i++) {
			MedTypeDict mt = listType.get(i);
			sb.append("'" + mt.getMed_type_name() + "'");
			sb.append(" t").append(i).append(" ,");
			sbview.append("'" + mt.getMed_type_name() + "'").append("|");
			sbview.append(" t").append(i).append(",");

		}

		String sql_type = sb.toString().substring(0, sb.toString().length() - 1);
		String sbviewTable = sbview.toString().substring(0, sbview.toString().length() - 1);

		entityMap.put("sql_type", "(" + sql_type + ")");
		entityMap.put("sbviewTable", sbviewTable);

		return entityMap;
	}
}
