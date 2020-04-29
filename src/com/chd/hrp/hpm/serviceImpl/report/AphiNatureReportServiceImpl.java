package com.chd.hrp.hpm.serviceImpl.report;

import java.text.SimpleDateFormat;
import java.util.Calendar;
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
import com.chd.hrp.hpm.dao.AphiNatureReportMapper;
import com.chd.hrp.hpm.dao.AphiTargetMapper;
import com.chd.hrp.hpm.entity.AphiTarget;
import com.chd.hrp.hpm.service.report.AphiNatureReportService;
import com.github.pagehelper.PageInfo;

/**
 * @Description: 报表
 * @Author: bell
 * @email: bell@s-chd.com
 * @Version: 1.0
 */
@Service("aphiNatureReportService")
public class AphiNatureReportServiceImpl implements AphiNatureReportService {

	@Resource(name = "aphiTargetMapper")
	private final AphiTargetMapper aphiTargetMapper = null;

	@Resource(name = "aphiNatureReportMapper")
	private final AphiNatureReportMapper aphiNatureReportMapper = null;

	private final Logger logger = Logger.getLogger(AphiNatureReportServiceImpl.class);

	@Override
	public String queryTargetTree(Map<String, Object> entityMap) throws DataAccessException {

		List<AphiTarget> targetList = aphiTargetMapper.queryPrmTarget(entityMap);

		StringBuilder json = new StringBuilder();

		if (targetList.size() == 0 && targetList.size() == 0) {

			json.append("{Rows:[]}");

			return json.toString();

		}

		try {

			json.append("{Rows:[");

			for (AphiTarget target : targetList) {

				json.append("{");
				json.append("\"pId\":\"-1\"," + "\"id\":\"" + target.getTarget_code() + "\"," + "\"name\":" + "\"" + target.getTarget_name() + "\"");
				json.append("},");
			}

			json.setCharAt(json.length() - 1, ']');

			json.append("}");

		} catch (Exception e) {

			json.append("{Rows:[]}");

			return json.toString();

		}

		return json.toString();

	}

	@Override
	public String queryHpmNatureReport(Map<String, Object> entityMap) throws DataAccessException {
		try {
			if (entityMap.get("user_id") == null) {
				entityMap.put("user_id", SessionManager.getUserId());
			}
			Map<String, Object> dateMap = getPreBeginEndYearMonth(String.valueOf(entityMap.get("begin_date")), String.valueOf(entityMap.get("end_date")));

			if (dateMap == null) {

				return "{\"error\":\"日期格式错误 \"}";
			}

			entityMap.put("pre_begin_date", dateMap.get("pre_begin_date"));
			entityMap.put("pre_end_date", dateMap.get("pre_end_date"));

			// 获取查询条件指标编码值
			String[] targetCodes = String.valueOf(entityMap.get("column_name")).split(",");

			// 本期查询语句中SQL
			StringBuffer bq_sql_column = new StringBuffer();
			StringBuffer bq_sql = new StringBuffer();

			// 上期查询语句中SQL
			StringBuffer sq_sql_column = new StringBuffer();
			StringBuffer sq_sql = new StringBuffer();

			// 查询结果集时计算同比SQL
			StringBuffer tb_sql = new StringBuffer();

			// 查询条件指标编码
			StringBuffer targetCode = new StringBuffer();

			for (int x = 0; x < targetCodes.length; x++) {// 遍历指标值,拼写查询SQL

				String code = targetCodes[x];

				// nvl(bq.bq_a001,0) as bq_a001,
				// nvl(sq.sq_a001,0) as sq_a001,
				bq_sql_column.append("nvl(bq.bq_" + code + ",0) as bq_" + code + ",");
				sq_sql_column.append("nvl(sq.sq_" + code + ",0) as sq_" + code + ",");

				// sum(nvl((case when adtd.target_code='a001' then
				// adtd.target_value end),0)) as bq001,
				bq_sql.append("sum(nvl((case when adtd.target_code='" + code + "' then adtd.target_value end),0)) as bq_" + code + ",");
				sq_sql.append("sum(nvl((case when adtd.target_code='" + code + "' then adtd.target_value end),0)) as sq_" + code + ",");

				// case when nvl(bq.bq_a001,0)=0 then 0
				// when nvl(sq.sq_a001,0)=0 then 0
				// else (bq.bq_a001 - sq.sq_a001)/sq.sq_a001 end as tb_a001,
				tb_sql.append("case when nvl(bq.bq_" + code + ",0)=0 then 0 ");
				tb_sql.append("when nvl(sq.sq_" + code + ",0)=0 then 0 ");
				tb_sql.append("else (bq.bq_" + code + "- sq.sq_" + code + ")/sq.sq_" + code + " end as tb_" + code + ",");

				// 'a001','a002'
				targetCode.append("'" + code + "',");
			}

			targetCode = targetCode.deleteCharAt(targetCode.length() - 1);

			entityMap.put("bq_sql_column", bq_sql_column.toString());
			entityMap.put("sq_sql_column", sq_sql_column.toString());
			entityMap.put("bq_sql", bq_sql.toString());
			entityMap.put("sq_sql", sq_sql.toString());
			entityMap.put("tb_sql", tb_sql.toString());
			entityMap.put("targetCode", targetCode.toString());

			SysPage sysPage = new SysPage();

			sysPage = (SysPage) entityMap.get("sysPage");

			List<Map<String, Object>> list = aphiNatureReportMapper.queryHpmNatureReport(entityMap);

			return ChdJson.toJsonLower(list);

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"操作失败 \"}";
		}

	}

	// 根据传入的起始期间计算上一期间
	public Map<String, Object> getPreBeginEndYearMonth(String begin_date, String end_date) {

		Map<String, Object> map = new HashMap<String, Object>();// 用于返回计算完的数据

		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");

			if (begin_date == null || "".equals(begin_date) || !begin_date.matches("\\d{4}[-]\\d{2}")) {
				return null;
			}
			if (end_date == null || "".equals(end_date) || !end_date.matches("\\d{4}[-]\\d{2}")) {
				return null;
			}

			String begin_month = begin_date.split("-")[1];
			String end_month = end_date.split("-")[1];
			int begin_num;
			int end_num;

			if (begin_month.indexOf(0) == 0) {
				begin_num = Integer.parseInt(String.valueOf(begin_month.charAt(1)));
			} else {
				begin_num = Integer.parseInt(begin_month);
			}

			if (end_month.indexOf(0) == 0) {
				end_num = Integer.parseInt(String.valueOf(end_month.charAt(1)));
			} else {
				end_num = Integer.parseInt(end_month);
			}

			int x = end_num - begin_num + 1;

			Calendar cal = Calendar.getInstance();
			cal.setTime(sdf.parse(begin_date));
			cal.add(Calendar.MONTH, -x);
			map.put("pre_begin_date", sdf.format(cal.getTime()));

			Calendar cld = Calendar.getInstance();
			cld.setTime(sdf.parse(begin_date));
			cld.add(Calendar.MONTH, -1);
			map.put("pre_end_date", sdf.format(cld.getTime()));

		} catch (Exception e) {

			e.printStackTrace();
		}

		return map;
	}
}
