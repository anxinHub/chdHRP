package com.chd.hrp.hr.serviceImpl.salarymanagement.socialSecurityManage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.chd.base.SessionManager;
import com.chd.base.SysPage;
import com.chd.base.util.ChdJson;
import com.chd.hrp.hr.dao.salarymanagement.socialSecurityManage.HrBehoovePayInsurMapper;
import com.chd.hrp.hr.dao.salarymanagement.socialSecurityManage.HrInsurKindMapper;
import com.chd.hrp.hr.entity.salarymanagement.socialSecurityManage.HrInsurKind;
import com.chd.hrp.hr.service.salarymanagement.socialSecurityManage.HrBehoovePayInsurService;
import com.github.pagehelper.PageInfo;
/**
 * 【薪资管理-社保管理】：应缴社保查询
 * @author yang
 *
 */
@Service("hrBehoovePayInsurService")
public class HrBehoovePayInsurServiceImpl implements HrBehoovePayInsurService {

	private static Logger logger = Logger.getLogger(HrBehoovePayInsurServiceImpl.class);
	
	@Resource(name = "hrBehoovePayInsurMapper")
	private final HrBehoovePayInsurMapper hrBehoovePayInsurMapper = null;
	@Resource(name = "hrInsurKindMapper")
	private final HrInsurKindMapper hrInsurKindMapper = null;

	// 查询表头
	@Override
	public String queryBehoovePayInsurHead(Map<String, Object> paramMap) throws DataAccessException {
		try{
			paramMap.put("group_id", SessionManager.getGroupId());
			paramMap.put("hos_id", SessionManager.getHosId());
			List<Map<String, Object>> colList = new ArrayList<Map<String, Object>>();
			// 所有社保险种
			List<HrInsurKind> insurKindList = hrInsurKindMapper.queryInsurKind(paramMap);
			if(insurKindList.size() > 0){
				// { display : '养老保险', columns:[
				//         { display :'个人', name: '', width:100 },
				//         { display :'单位', name: '', width:100 }
				//     ]
				// }
				for(HrInsurKind insur : insurKindList){
					Map<String, Object> col_1 = new HashMap<String, Object>();
					col_1.put("display", "单位");
					col_1.put("name", "unit_amount_" + insur.getInsure_code());
					col_1.put("width", 100);
					col_1.put("align", "center");
					
					Map<String, Object> col_2 = new HashMap<String, Object>();
					col_2.put("display", "个人");
					col_2.put("name", "individual_amount_" + insur.getInsure_code());
					col_2.put("width", 100);
					col_2.put("align", "center");
					
					List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
					list.add(col_1);
					list.add(col_2);
					
					Map<String, Object> col = new HashMap<String, Object>();
					col.put("display", insur.getInsure_name());
					col.put("columns", list);
					
					colList.add(col);
				}
			}
			
			Map<String, Object> headMap = new HashMap<String, Object>();
			headMap.put("columns", colList);
//			headMap.put("totalColumns", "");
			return JSONArray.toJSONString(headMap);
		}catch(Exception e){
			logger.error(e.getMessage(), e);
			return "{\"error\":\"返回表头失败.\",\"state\":\"false\"}";
		}
	}
	
	// 主查询
	@Override
	public Map<String, Object> queryBehoovePayInsur(Map<String, Object> paramMap) throws DataAccessException {
		if(paramMap.get("group_id") == null){
			paramMap.put("group_id", SessionManager.getGroupId());
		}
		if(paramMap.get("hos_id") == null){
			paramMap.put("hos_id", SessionManager.getHosId());
		}
		if(StringUtils.isNotEmpty(paramMap.get("dept_id").toString())){
			paramMap.put("dept_id", paramMap.get("dept_id").toString().split("@")[1]);
		}
		
		paramMap = addSqlParam(paramMap);
		
		SysPage sysPage = new SysPage();
		sysPage = (SysPage) paramMap.get("sysPage");
		if(sysPage.getTotal() == -1){
			List<Map<String, Object>> list = ChdJson
				.toListLower((List<Map<String, Object>>) hrBehoovePayInsurMapper.query(paramMap));
			return JSONObject.parseObject(ChdJson.toJson(list));
		}else{
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			List<Map<String,Object>> list = ChdJson
				.toListLower((List<Map<String,Object>>) hrBehoovePayInsurMapper.query(paramMap, rowBounds));
			PageInfo page = new PageInfo(list);
			return JSONObject.parseObject(ChdJson.toJson(list, page.getTotal()));
		}
	}

	// 补充主查询sql
	private Map<String, Object> addSqlParam(Map<String, Object> paramMap) {
		List<HrInsurKind> insurKindList = hrInsurKindMapper.queryInsurKind(paramMap);
		String prefix = "insur_";// 临时表别名前缀
		// 临时表查询通用sql
		String commSelect = " as (select group_id, hos_id, emp_id, insur_type, individual_amount, "
						  + "unit_amount from hr_insur where insur_type = ";
		StringBuilder selectSql = new StringBuilder();// 临时表
		StringBuilder colSql = new StringBuilder();// 列
		StringBuilder countCol_1 = new StringBuilder();// 单位合计
		StringBuilder countCol_2 = new StringBuilder();// 个人合计
		StringBuilder joinSql = new StringBuilder();// join连接表
		if(insurKindList.size() > 0){
			for(HrInsurKind insur : insurKindList){
				String ic = insur.getInsure_code();
				String tn = prefix + ic;
				selectSql.append(", " + tn + commSelect + "'" + ic + "')");
				joinSql.append(" left join " + tn + " on " + tn + ".group_id = hr_insur_tmp.group_id ")
					   .append("and " + tn + ".hos_id = hr_insur_tmp.hos_id ")
					   .append("and " + tn + ".emp_id = hr_insur_tmp.emp_id ");
				colSql.append(", " + tn + ".individual_amount as individual_amount_" + ic + ", ")
					  .append(tn + ".unit_amount as unit_amount_" + ic);
				countCol_1.append("+nvl(" + tn + ".unit_amount,0)");
				countCol_2.append("+nvl(" + tn + ".individual_amount,0)");
			}
		}
		countCol_1.append(" as unit_amount_count");
		countCol_2.append(" as individual_amount_count");
		colSql.append(", " + countCol_1.substring(1) + ", " + countCol_2.substring(1));
		paramMap.put("with_select", selectSql);
		paramMap.put("columns", colSql);
		paramMap.put("join", joinSql);
		return paramMap;
	}
}
