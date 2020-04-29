package com.chd.hrp.ass.serviceImpl.guanLiReports;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.nutz.lang.Strings;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.SessionManager;
import com.chd.base.SysPage;
import com.chd.base.util.ChdJson;
import com.chd.hrp.ass.dao.guanLiReports.AssPriceChangeMapper;
import com.chd.hrp.ass.service.guanLiReports.AssPriceChangeService;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Description: 资产原值变动
 * @Author: bell
 * @email: bell@e-tonggroup.com
 * @Version: 1.0
 */

@Service("assPriceChangeService")
public class AssPriceChangeServiceImpl implements AssPriceChangeService {

	private static Logger logger = Logger.getLogger(AssPriceChangeServiceImpl.class);
	// 引入DAO操作
	@Resource(name = "assPriceChangeMapper")
	private final AssPriceChangeMapper assPriceChangeMapper = null;

	/**
	 * 资产原值变动查询
	 */
	@Override
	public String query(Map<String, Object> entityMap) throws DataAccessException {

		List<Map<String, Object>> busTypesEsixts = assPriceChangeMapper.queryBusTypesExists(entityMap);
		
		if(busTypesEsixts == null || busTypesEsixts.size() == 0) {
			return "{\"error\":\""+"会计期间   "+entityMap.get("year_month_begin")+" 无数据.\"}";
		}
		
		List<Map<String, Object>> busTypes = assPriceChangeMapper.queryBusTypes(entityMap);

		if (busTypes != null && busTypes.size() > 0) {
			StringBuilder busTypeStr_add = new StringBuilder();
			StringBuilder busTypeStr_dec = new StringBuilder();
			StringBuilder cols_add = new StringBuilder();
			StringBuilder cols_dec = new StringBuilder();
			StringBuilder cols_add_sum = new StringBuilder();
			StringBuilder cols_dec_sum = new StringBuilder();

			for (Map<String, Object> map : busTypes) {
				if (map.get("in_out").toString().equals("1")) {
					busTypeStr_add.append(
							"'" + map.get("bus_type").toString() + "' as bus_" + map.get("bus_type").toString() + ",");
					cols_add.append("bus_" + map.get("bus_type").toString() + ",");
					cols_add_sum.append("sum(bus_" + map.get("bus_type").toString() + ") bus_"
							+ map.get("bus_type").toString() + ",");
				} else {
					busTypeStr_dec.append(
							"'" + map.get("bus_type").toString() + "' as dec_" + map.get("bus_type").toString() + ",");
					cols_dec.append("dec_" + map.get("bus_type").toString() + ",");
					cols_dec_sum.append("sum(dec_" + map.get("bus_type").toString() + ") dec_"
							+ map.get("bus_type").toString() + ",");
				}
			}
			if (busTypeStr_add.length() > 0) {
				busTypeStr_add.deleteCharAt(busTypeStr_add.length() - 1);
			}
			if (busTypeStr_dec.length() > 0) {

				busTypeStr_dec.deleteCharAt(busTypeStr_dec.length() - 1);
			}
			if (cols_add.length() > 0) {

				cols_add.deleteCharAt(cols_add.length() - 1);
			}
			if (cols_dec.length() > 0) {

				cols_dec.deleteCharAt(cols_dec.length() - 1);
			}
			if (cols_add_sum.length() > 0) {
 
				cols_add_sum.deleteCharAt(cols_add_sum.length() - 1);
			}
			if (cols_dec_sum.length() > 0) {
				cols_dec_sum.deleteCharAt(cols_dec_sum.length() - 1);
			}

			entityMap.put("bus_type_add", Strings.isBlank(busTypeStr_add.toString()) ? "''": busTypeStr_add.toString());
			entityMap.put("bus_type_dec", Strings.isBlank(busTypeStr_dec.toString()) ? "''": busTypeStr_dec.toString());
			entityMap.put("cols_add", Strings.isBlank(cols_add.toString()) ? "''": cols_add.toString());
			entityMap.put("cols_dec", Strings.isBlank(cols_dec.toString()) ? "''": cols_dec.toString());
			entityMap.put("cols_add_sum", Strings.isBlank(cols_add_sum.toString()) ? "''": cols_add_sum.toString());
			entityMap.put("cols_dec_sum", Strings.isBlank(cols_dec_sum.toString()) ? "''": cols_dec_sum.toString());
		} 

		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		if (sysPage.getTotal() == -1) {

			List<Map<String, Object>> list = (List<Map<String, Object>>) assPriceChangeMapper.query(entityMap);

			return ChdJson.toJson(list);

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

			List<Map<String, Object>> list = (List<Map<String, Object>>) assPriceChangeMapper.query(entityMap,
					rowBounds);

			PageInfo page = new PageInfo(list);

			return ChdJson.toJson(list, page.getTotal());

		}
	}

	@Override
	public Map<String, Object> queryBusTypes(Map<String, Object> entityMap) throws DataAccessException {

		Map<String, Object> ret = new HashMap<String, Object>();
		// 增加
		entityMap.put("in_out", "1");
		ret.put("bus_add", ChdJson.toJson(assPriceChangeMapper.queryBusTypes(entityMap)));

		// 减少
		entityMap.put("in_out", "0");
		ret.put("bus_dec", ChdJson.toJson(assPriceChangeMapper.queryBusTypes(entityMap)));

		return ret;
	}

	@Override
	public List<Map<String, Object>> queryAssPriceChangMainPrint(Map<String, Object> entityMap)
			throws DataAccessException {
		entityMap.put("group_id", SessionManager.getGroupId());
		entityMap.put("hos_id", SessionManager.getHosId());
		entityMap.put("copy_code", SessionManager.getCopyCode());
		entityMap.put("user_id", SessionManager.getUserId());
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> busTypes = assPriceChangeMapper.queryBusTypes(entityMap);

		if (busTypes != null && busTypes.size() > 0) {
			StringBuilder busTypeStr_add = new StringBuilder();
			StringBuilder busTypeStr_dec = new StringBuilder();
			StringBuilder cols_add = new StringBuilder();
			StringBuilder cols_dec = new StringBuilder();
			StringBuilder cols_add_sum = new StringBuilder();
			StringBuilder cols_dec_sum = new StringBuilder();

			for (Map<String, Object> map : busTypes) {
				if (map.get("in_out").toString().equals("1")) {
					busTypeStr_add.append(
							"'" + map.get("bus_type").toString() + "' as bus_" + map.get("bus_type").toString() + ",");
					cols_add.append("bus_" + map.get("bus_type").toString() + ",");
					cols_add_sum.append("sum(bus_" + map.get("bus_type").toString() + ") bus_"
							+ map.get("bus_type").toString() + ",");
				} else {
					busTypeStr_dec.append(
							"'" + map.get("bus_type").toString() + "' as dec_" + map.get("bus_type").toString() + ",");
					cols_dec.append("dec_" + map.get("bus_type").toString() + ",");
					cols_dec_sum.append("sum(dec_" + map.get("bus_type").toString() + ") dec_"
							+ map.get("bus_type").toString() + ",");
				}
			}

			if (busTypeStr_add.length() > 0) {
				busTypeStr_add.deleteCharAt(busTypeStr_add.length() - 1);
			}
			if (busTypeStr_dec.length() > 0) {

				busTypeStr_dec.deleteCharAt(busTypeStr_dec.length() - 1);
			}
			if (cols_add.length() > 0) {

				cols_add.deleteCharAt(cols_add.length() - 1);
			}
			if (cols_dec.length() > 0) {

				cols_dec.deleteCharAt(cols_dec.length() - 1);
			}
			if (cols_add_sum.length() > 0) {

				cols_add_sum.deleteCharAt(cols_add_sum.length() - 1);
			}
			if (cols_dec_sum.length() > 0) {
				cols_dec_sum.deleteCharAt(cols_dec_sum.length() - 1);
			}

			entityMap.put("bus_type_add", Strings.isBlank(busTypeStr_add.toString()) ? "''": busTypeStr_add.toString());
			entityMap.put("bus_type_dec", Strings.isBlank(busTypeStr_dec.toString()) ? "''": busTypeStr_dec.toString());
			entityMap.put("cols_add", Strings.isBlank(cols_add.toString()) ? "''": cols_add.toString());
			entityMap.put("cols_dec", Strings.isBlank(cols_dec.toString()) ? "''": cols_dec.toString());
			entityMap.put("cols_add_sum", Strings.isBlank(cols_add_sum.toString()) ? "''": cols_add_sum.toString());
			entityMap.put("cols_dec_sum", Strings.isBlank(cols_dec_sum.toString()) ? "''": cols_dec_sum.toString());
	

			list = assPriceChangeMapper.queryAssPriceChangMainPrint(entityMap);
		}

		return list;
	}

}
