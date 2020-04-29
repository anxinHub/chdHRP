package com.chd.hrp.hpm.serviceImpl;

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
import com.chd.base.util.JsonListBeanUtil;
import com.chd.hrp.hpm.dao.AphiSchemeConfMapper;
import com.chd.hrp.hpm.dao.AphiItemMapper;
import com.chd.hrp.hpm.entity.AphiDept;
import com.chd.hrp.hpm.entity.AphiItem;
import com.chd.hrp.hpm.entity.AphiSchemeConf;
import com.chd.hrp.hpm.service.AphiSchemeConfService;
import com.github.pagehelper.PageInfo;

@Service("aphiSchemeConfService")
public class AphiSchemeConfServiceImpl implements AphiSchemeConfService {

	private static Logger logger = Logger.getLogger(AphiSchemeConfServiceImpl.class);

	@Resource(name = "aphiSchemeConfMapper")
	private AphiSchemeConfMapper aphiSchemeConfMapper = null;
	
	@Resource(name = "aphiItemMapper")
	private final AphiItemMapper aphiItemMapper = null;

	@Override
	public String queryHpmSchemeConf(Map<String, Object> entityMap) throws DataAccessException {
		
		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		if (sysPage.getTotal() == -1) {

			List<AphiSchemeConf> list = aphiSchemeConfMapper.querySchemeConf(entityMap);

			return ChdJson.toJson(list);

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

			List<AphiSchemeConf> list = aphiSchemeConfMapper.querySchemeConf(entityMap, rowBounds);

			PageInfo page = new PageInfo(list);

			return ChdJson.toJson(list, page.getTotal());

		}
	}

	@Override
	public String addHpmSchemeConf(Map<String, Object> entityMap) throws DataAccessException {

		try {
			String year_month = (String) entityMap.get("year_month");

			String acct_year = year_month.substring(0, 4);

			String acct_month = year_month.substring(4, 6);

			entityMap.put("acct_year", acct_year);

			entityMap.put("acct_month", acct_month);

			aphiSchemeConfMapper.addSchemeConf(entityMap);

			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";
		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码  addSchemeApply\"}";
		}

	}

	@Override
	public String updateHpmSchemeConf(Map<String, Object> entityMap) throws DataAccessException {

		int result = aphiSchemeConfMapper.updateSchemeConf(entityMap);

		if (result > 0) {
			
			return "{\"msg\":\"本月奖金应用方案已改变，请重新计算！\",\"state\":\"true\"}";
			
		}
		
		return "{\"msg\":\"修改失败.\",\"state\":\"true\"}";
	}

	@Override
	public String deleteHpmSchemeConf(Map<String, Object> entityMap, String codes) throws DataAccessException {

		try {

			if (codes != null && !codes.equals("")) {

				String[] code_codeArray = codes.split(",");

				for (String code : code_codeArray) {

					String[] code_array = code.split(";");

					entityMap.put("acct_year", code_array[0]);
					
					entityMap.put("acct_month", code_array[1]);

					aphiSchemeConfMapper.deleteSchemeConf(entityMap);
				}

				return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

			} else {

				return "{\"msg\":\"没有要删除的数据.\",\"state\":\"false\"}";

			}
		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码  deleteIncomeItem\"}";

		}
	}

	@Override
	public AphiSchemeConf queryHpmSchemeConfByCode(Map<String, Object> entityMap) throws DataAccessException {

		return aphiSchemeConfMapper.querySchemeConfByCode(entityMap);
		
	}

	@Override
	public String createHpmSchemeConf(Map<String, Object> entityMap) throws DataAccessException {

		String rbtnl = (String) entityMap.get("rbtnl");
		try {
			String year_month = (String) entityMap.get("year_month");

			String scheme_seq_no = (String) entityMap.get("scheme_seq_no");

			// 生成下一月
			String next_year_month = DateUtil.getNextYear_Month(year_month);

			entityMap.put("scheme_seq_no", scheme_seq_no);

			entityMap.put("acct_year", next_year_month.substring(0, 4));

			entityMap.put("acct_month", next_year_month.substring(4, 6));

			aphiSchemeConfMapper.addSchemeConf(entityMap);

			if ("yes".equals(rbtnl)) {

				// 生成当前月

				entityMap.put("scheme_seq_no", scheme_seq_no);

				entityMap.put("acct_year", year_month.substring(0, 4));

				entityMap.put("acct_month", year_month.substring(4, 6));

				aphiSchemeConfMapper.addSchemeConf(entityMap);

			}

			return "{\"msg\":\"生成成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"生成失败 数据库异常 请联系管理员! 错误编码  createSchemeApply\"}";
		}

	}

	@Override
	public String queryHpmSchemeConfForDept(Map<String, Object> entityMap) throws DataAccessException {
		
		StringBuffer sql = new StringBuffer();

		List searchList = aphiItemMapper.qeuryItemData(entityMap);
		
		for (int i = 0; i < searchList.size(); i++) {
			
			AphiItem item = (AphiItem) searchList.get(i);
			
			sql.append("max(nvl((case when ai.item_code = '" + item.getItem_code() + "' then to_char(formula_method_chs) end),'')) as  formula_method_chs" + i + ",");
			
			sql.append("max(nvl((case when ai.item_code = '" + item.getItem_code() + "' then ads.formula_code end),'')) as  formula_code" + i + ",");
		
		}
		
		entityMap.put("sql", sql);
		
		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		if (sysPage.getTotal() == -1) {

			List<Map<String,Object>> list = aphiSchemeConfMapper.queryHpmSchemeConfForDept(entityMap);

			return ChdJson.toJsonLower(list);

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

			List<Map<String,Object>> list = aphiSchemeConfMapper.queryHpmSchemeConfForDept(entityMap, rowBounds);

			PageInfo page = new PageInfo(list);

			return ChdJson.toJsonLower(list, page.getTotal());

		}
	}

	@Override
	public String queryHpmSchemeConfGridForDept(Map<String, Object> entityMap) throws DataAccessException {
		
		StringBuilder json = new StringBuilder();
		
		List<AphiItem> queryItemDict = aphiItemMapper.qeuryItemData(entityMap);
		
		json.append("[");
		
		for (int i = 0; i < queryItemDict.size(); i++) {
			
			AphiItem item = queryItemDict.get(i);
			
			json.append("{");
			
			json.append("\"id\":\"" + item.getItem_code() + "\"," + "\"text\":" + "\"" 
									+ item.getItem_name() + "\"," + "\"Sort\":" + "\"" 
									+ i + "\"");
			
			json.append("},");
		}
		json.setCharAt(json.length() - 1, ']');

		return json.toString();
	}

}
