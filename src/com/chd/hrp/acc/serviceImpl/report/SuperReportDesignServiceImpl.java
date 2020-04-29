package com.chd.hrp.acc.serviceImpl.report;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.hrp.acc.dao.report.SuperReportDataSetMapper;
import com.chd.hrp.acc.dao.report.SuperReportDesignMapper;
import com.chd.hrp.acc.entity.report.RepDefine;
import com.chd.hrp.acc.entity.report.RepDefineDict;
import com.chd.hrp.acc.entity.report.RepDefineEle;
import com.chd.hrp.acc.service.report.SuperReportDesignService;
import com.chd.hrp.acc.serviceImpl.vouch.SuperVouchServiceImpl;
import com.chd.hrp.sys.dao.notGeneral.SysFunUtilMapper;

/**
 * 超级报表设计类
 * 
 * @author ADMINISTRATOR
 *
 */
@Service("superReportDesignService")
public class SuperReportDesignServiceImpl implements SuperReportDesignService {

	private static Logger logger = Logger.getLogger(SuperVouchServiceImpl.class);

	@Resource(name = "superReportDesignMapper")
	private final SuperReportDesignMapper superReportDesignMapper = null;

	@Resource(name = "sysFunUtilMapper")
	private final SysFunUtilMapper sysFunUtilMapper = null;

	@Resource(name = "superReportDataSetMapper")
	private final SuperReportDataSetMapper superReportDataSetMapper = null;

	// 根据报表编码查询所有字段，该方法没使用，将来扩展用的
	@Override
	public String querySuperReportByCode(Map<String, Object> map) throws DataAccessException {
		// TODO Auto-generated method stub

		String content = "{}";

		return content;
	}

	// 根据报表编码查询报表内容，大字段
	@Override
	public String querySuperReportContentByCode(Map<String, Object> map) throws DataAccessException {
		// TODO Auto-generated method stub

		String content = "{}";

		try {
			RepDefine repDefine = superReportDesignMapper.querySuperReportContentByCode(map);
			if (null != repDefine && repDefine.getContent() != null && repDefine.getContent().length() > 0) {
				// content=new String(repDefine.getContent(),"GBK");
				content = repDefine.getContent();
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage(), e);
		}

		return content;
	}

	// 根据系统编码查询报表，返回tree
	@Override
	public String querySuperReportByMod(Map<String, Object> map) throws DataAccessException {
		// TODO Auto-generated method stub
		StringBuffer sb = new StringBuffer();
		sb.append("{Rows:[");
		List<RepDefine> list = new ArrayList<RepDefine>();
		list = superReportDesignMapper.querySuperReportByMod(map);
		
		if(map.get("mod_code") != null && "01".equals(map.get("mod_code"))){
			//拼装顶级（新旧制度）,报表编码输入2会导致id重复，前面加特殊字符
			sb.append("{id:\"~!@#2\",pId:\"top\",name:\"政府会计制度（新）\",title:\"政府会计制度（新）\",open:false},");
			sb.append("{id:\"~!@#1\",pId:\"top\",name:\"医院财务制度（旧）\",title:\"医院财务制度（旧）\",open:false},");
		}
		
		// 拼装级次
		if (list != null && list.size() > 0) {
			Map<String, Object> pidMap = new HashMap<String, Object>();
			int row = 0;
			for (RepDefine rep : list) {

				if (pidMap.size() == 0 || pidMap.get(rep.getReport_attr() + rep.getReport_group()) == null) {
					if (row == 0) {
						sb.append("{");
					} else {
						sb.append(",{");
					}
					row++;
					pidMap.put(rep.getReport_attr() + rep.getReport_group(), "t_" + row);
					sb.append("id:\"t_" + row + "\",");
					if(rep.getMod_code() != null && "01".equals(rep.getMod_code())){
						sb.append("pId:\"~!@#"+rep.getReport_attr()+"\",");
					}else{
						sb.append("pId:\"top\",");
					}
					sb.append("name:\"" + rep.getReport_group() + "\",");
					sb.append("title:\"" + rep.getReport_group() + "\",");
					sb.append("open:false");
					sb.append("}");
				}

			}

			for (RepDefine rep : list) {
				sb.append(",{");
				sb.append("id:\"" + rep.getReport_code() + "\",");
				sb.append("pId:\"" + pidMap.get(rep.getReport_attr() + rep.getReport_group()) + "\",");
				sb.append("name:\"" + rep.getReport_name() + "\",");
				sb.append("report_type:\"" + rep.getReport_type() + "\",");
				sb.append("report_group:\"" + rep.getReport_group() + "\",");
				sb.append("report_note:\"" + rep.getReport_note() + "\",");
				sb.append("report_attr:\"" + rep.getReport_attr() + "\",");
				sb.append("sort_code:\"" + rep.getSort_code() + "\",");
				sb.append("is_perm:\"" + rep.getIs_perm() + "\",");
				sb.append("is_sys:\"" + rep.getIs_sys() + "\",");
				sb.append("title:\"" + rep.getReport_code() + "\",");
				sb.append("is_write:\"" + rep.getIs_write() + "\",");
				sb.append("is_stop:\"" + rep.getIs_stop() + "\"");

				sb.append("}");
			}
		}

		sb.append("]}");
		System.out.println(sb.toString());
		return sb.toString();
	}

	// 保存报表
	@Override
	public String saveSuperReport(Map<String, Object> map) throws DataAccessException {
		// TODO Auto-generated method stub

		try {

			if (map.get("report_group").equals("")) {
				map.put("report_group", "系统默认");
			}

			// 添加
			if (map.get("sort_code").toString().equals("系统生成")) {
				map.put("field_table", "REP_DEFINE");
				map.put("field_key1", "report_code");
				map.put("field_value1", map.get("report_code"));
				map.put("field_key2", "mod_code");
				map.put("field_value2", map.get("mod_code"));
				int count = sysFunUtilMapper.existsSysCodeNameByAdd(map);
				if (count > 0) {
					return "{\"error\":\"报表编码：[" + map.get("report_code").toString() + "]重复.\"}";
				}

				map.remove("field_table");
				map.remove("field_key1");
				map.remove("field_value1");
				map.remove("field_key2");
				map.remove("field_value2");

				// 取最大排序号
				map.put("field_table", "REP_DEFINE");
				map.put("field_sort", "sort_code");
				map.put("field_key1", "mod_code");
				map.put("field_value1", map.get("mod_code"));
				count = sysFunUtilMapper.querySysMaxSort(map);
				map.put("sort_code", count);
				// 添加
				superReportDesignMapper.insertSuperReport(map);
			} else {
				// 修改
				superReportDesignMapper.updateSuperReport(map);
			}

			return "{\"msg\":\"保存成功.\",\"state\":\"true\"}";
		} catch (Exception e) {

			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());

		}

	}

	// 保存报表内容，大字段
	@Override
	public String updateSuperReportContent(Map<String, Object> map) throws DataAccessException {
		try {
			superReportDesignMapper.updateSuperReportContent(map);
			return "{\"msg\":\"保存成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}
	}

	// 根据报表编码删除报表
	@Override
	public String deleteSuperReportByCode(Map<String, Object> map) throws DataAccessException {
		// TODO Auto-generated method stub
		try {

			int count = superReportDesignMapper.querySuperReportInstanceCountByCode(map);
			if (count > 0) {
				return "{\"error\":\"已经有报表数据，不能删除，到【报表管理】页面删除该报表数据！\",\"state\":\"false\"}";
			}

			superReportDesignMapper.deleteSuperReportByCode(map);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());

		}
		return "{\"msg\":\"删除成功！\",\"state\":\"true\"}";
	}

	// 根据系统编码查询报表元素
	@Override
	public String querySuperReportEleByMod(Map<String, Object> map) throws DataAccessException {
		// TODO Auto-generated method stub
		StringBuffer sb = new StringBuffer();
		sb.append("{Rows:[");
		// 加载系统元素
		sb.append(getSystemEle());

		List<RepDefineEle> list = new ArrayList<RepDefineEle>();
		list = superReportDesignMapper.querySuperReportEleByMod(map);

		// 拼装级次
		if (list != null && list.size() > 0) {
			int row = 0;

			// 系统模块（一级）
			Map<String, Object> modMap = new HashMap<String, Object>();
			for (RepDefineEle rep : list) {

				if (modMap.size() == 0 || modMap.get(rep.getMod_code()) == null) {
					if (row == 0) {
						sb.append("{");
					} else {
						sb.append(",{");
					}
					row++;
					modMap.put(rep.getMod_code(), "m_" + row);
					sb.append("id:\"m_" + row + "\",");
					sb.append("pId:\"top\",");
					sb.append("name:\"" + rep.getMod_name() + "\",");
					sb.append("title:\"" + rep.getMod_name() + "\",");
					sb.append("open:false");
					sb.append("}");
				}

			}

			// 元素分组（二级）
			row = 0;
			Map<String, Object> eleGroupMap = new HashMap<String, Object>();
			for (RepDefineEle rep : list) {

				if (eleGroupMap.size() == 0 || eleGroupMap.get(rep.getMod_code() + rep.getEle_group()) == null) {
					if (row == 0) {
						sb.append("{");
					} else {
						sb.append(",{");
					}
					row++;
					eleGroupMap.put(rep.getMod_code() + rep.getEle_group(), "t_" + row);
					sb.append("id:\"t_" + row + "\",");
					sb.append("pId:\"" + modMap.get(rep.getMod_code()) + "\",");
					sb.append("name:\"" + rep.getEle_group() + "\",");
					sb.append("title:\"" + rep.getEle_group() + "\",");
					sb.append("open:false");
					sb.append("}");
				}

			}

			// 元素（三级）
			for (RepDefineEle rep : list) {
				sb.append(",{");
				sb.append("id:\"" + rep.getEle_code() + "\",");
				sb.append("pId:\"" + eleGroupMap.get(rep.getMod_code() + rep.getEle_group()) + "\",");
				sb.append("name:\"" + rep.getEle_name() + "\",");
				sb.append("ele_group:\"" + rep.getEle_group() + "\",");
				sb.append("mod_code:\"" + rep.getMod_code() + "\",");
				sb.append("ele_type:\"" + rep.getEle_type() + "\",");
				sb.append("sort_code:\"" + rep.getSort_code() + "\",");
				sb.append("note:\"" + (rep.getNote() == null ? "" : rep.getNote()) + "\",");
				sb.append("is_sys:\"" + rep.getIs_sys() + "\",");
				sb.append("formula_type:\"2\",");// 公式类型1非计算元素、2计算元素
				sb.append("title:\"" + rep.getEle_code() + "\",");
				sb.append("is_stop:\"" + rep.getIs_stop() + "\"");
				sb.append("}");
			}
		}

		sb.append("]}");
		return sb.toString();
	}

	// 根据报表元素查询报表元素参数
	@Override
	public String querySuperReportParaByEle(Map<String, Object> map) throws DataAccessException {
		// TODO Auto-generated method stub
		return ChdJson.toJson(superReportDesignMapper.querySuperReportParaByEle(map));
	}

	// 根据报表数据集查询报表数据集参数
	@Override
	public String querySuperReportParaByDs(Map<String, Object> map) throws DataAccessException {
		// TODO Auto-generated method stub
		return ChdJson.toJson(superReportDesignMapper.querySuperReportParaByDs(map));
		
	}

	// 参数下拉框数据初始化，报表定义通用
	@Override
	public String querySuperReportParaSelectData(Map<String, Object> map) throws DataAccessException {
		// TODO Auto-generated method stub
		// 核算类型，切换核算项
		if (map.get("check_type_name") != null) {
			if (map.get("check_type_name").equals("部门")) {
				map.put("dict_code", "S005");
			} else if (map.get("check_type_name").equals("职工")) {
				map.put("dict_code", "S006");
			} else if (map.get("check_type_name").equals("项目")) {
				map.put("dict_code", "S007");
			} else if (map.get("check_type_name").equals("库房")) {
				map.put("dict_code", "S008");
			} else if (map.get("check_type_name").equals("客户")) {
				map.put("dict_code", "S009");
			} else if (map.get("check_type_name").equals("供应商")) {
				map.put("dict_code", "S010");
			} else if (map.get("check_type_name").equals("资金来源")) {
				map.put("dict_code", "S011");
			} else if (map.get("check_type_name").equals("单位")) {
				map.put("dict_code", "S012");
			}
			map.put("column_check", null);
		}
		//新增核算类型，切换核算项
		if (map.get("column_check") != null) {
			if (map.get("column_check").equals("CHECK1")) {
				map.put("dict_code", "S005");
			} else if (map.get("column_check").equals("CHECK2")) {
				map.put("dict_code", "S006");
			} else if (map.get("column_check").equals("CHECK3")) {
				map.put("dict_code", "S007");
			} else if (map.get("column_check").equals("CHECK4")) {
				map.put("dict_code", "S008");
			} else if (map.get("column_check").equals("CHECK5")) {
				map.put("dict_code", "S009");
			} else if (map.get("column_check").equals("CHECK6")) {
				map.put("dict_code", "S010");
			} else if (map.get("column_check").equals("CHECK7")) {
				map.put("dict_code", "S011");
			} else if (map.get("column_check").equals("CHECK8")) {
				map.put("dict_code", "S012");
			}
			map.put("check_type_name", null);
		}

		RepDefineDict dict = superReportDesignMapper.querySuperReportDictBySql(map);
		if (dict == null || dict.getDict_sql() == null || dict.getDict_sql().equals("")) {
			return "{}";
		}

		// 查询sql
		String dictSql = dict.getDict_sql();

		for (Map.Entry<String, Object> entry : map.entrySet()) {
			if (entry.getKey().equalsIgnoreCase("dict_code") || entry.getKey().equalsIgnoreCase("key")
					|| entry.getKey().equalsIgnoreCase("isCheck")) {
				continue;
			}
			dictSql = dictSql.replace("#{" + entry.getKey() + "}", "'" + entry.getValue() + "'");
			/*
			 * dictSql=dictSql.replace("#{group_id}", map.get("group_id").toString());
			 * dictSql=dictSql.replace("#{hos_id}", map.get("hos_id").toString());
			 * dictSql=dictSql.replace("#{copy_code}",
			 * "'"+map.get("copy_code").toString()+"'");
			 * dictSql=dictSql.replace("#{acc_year}",
			 * "'"+map.get("acc_year").toString()+"'");
			 * dictSql=dictSql.replace("#{user_id}", map.get("user_id").toString());
			 * dictSql=dictSql.replace("#{user_code}",
			 * "'"+map.get("user_code").toString()+"'");
			 */
		}

		// 检索条件
		String dictCheckSql = dict.getDict_check_sql();
		if (dictCheckSql != null && !dictCheckSql.equals("") && map.get("key") != null
				&& !map.get("key").toString().equals("")) {

			dictCheckSql = dictCheckSql.replace("#{key}", map.get("key").toString().toUpperCase());
			dictSql = dictSql.toLowerCase();
			if (dictSql.lastIndexOf("order by") != -1) {
				dictSql = dictSql.substring(0, dictSql.indexOf("order by")) + " and(" + dictCheckSql + ") "
						+ dictSql.substring(dictSql.indexOf("order by"), dictSql.length());

			} else {
				dictSql = dictSql + " and(" + dictCheckSql + ")";
			}

		}

		if (!dictSql.equals("")) {
			logger.debug(dictSql);
			return JSON.toJSONString(superReportDesignMapper.querySuperReportParaSelectData(dictSql));
		}

		return "{}";
	}

	// 获取系统元素
	private String getSystemEle() {
		StringBuffer sb = new StringBuffer();
		// 系统元素（一级）
		sb.append("{");
		sb.append("id:\"system\",");
		sb.append("pId:\"top\",");
		sb.append("name:\"系统元素\",");
		sb.append("title:\"系统元素\",");
		sb.append("open:false");
		sb.append("}");

		sb.append(",{");
		sb.append("id:\"group_code\",");
		sb.append("pId:\"system\",");
		sb.append("name:\"集团编码\",");
		sb.append("ele_type:\"999\",");
		sb.append("formula_type:\"1\",");// 公式类型1非计算元素、2计算元素
		sb.append("title:\"group_code\",");
		sb.append("open:false");
		sb.append("}");

		sb.append(",{");
		sb.append("id:\"group_simple\",");
		sb.append("pId:\"system\",");
		sb.append("name:\"集团简称\",");
		sb.append("ele_type:\"999\",");
		sb.append("formula_type:\"1\",");// 公式类型1非计算元素、2计算元素
		sb.append("title:\"group_simple\",");
		sb.append("open:false");
		sb.append("}");

		sb.append(",{");
		sb.append("id:\"group_name\",");
		sb.append("pId:\"system\",");
		sb.append("name:\"集团名称\",");
		sb.append("ele_type:\"999\",");
		sb.append("formula_type:\"1\",");// 公式类型1非计算元素、2计算元素
		sb.append("title:\"group_name\",");
		sb.append("open:false");
		sb.append("}");

		sb.append(",{");
		sb.append("id:\"hos_code\",");
		sb.append("pId:\"system\",");
		sb.append("name:\"医院编码\",");
		sb.append("ele_type:\"999\",");
		sb.append("formula_type:\"1\",");// 公式类型1非计算元素、2计算元素
		sb.append("title:\"hos_code\",");
		sb.append("open:false");
		sb.append("}");

		sb.append(",{");
		sb.append("id:\"hos_simple\",");
		sb.append("pId:\"system\",");
		sb.append("name:\"医院简称\",");
		sb.append("ele_type:\"999\",");
		sb.append("formula_type:\"1\",");// 公式类型1非计算元素、2计算元素
		sb.append("title:\"hos_simple\",");
		sb.append("open:false");
		sb.append("}");

		sb.append(",{");
		sb.append("id:\"hos_name\",");
		sb.append("pId:\"system\",");
		sb.append("name:\"医院名称\",");
		sb.append("ele_type:\"999\",");
		sb.append("formula_type:\"1\",");// 公式类型1非计算元素、2计算元素
		sb.append("title:\"hos_name\",");
		sb.append("open:false");
		sb.append("}");

		sb.append(",{");
		sb.append("id:\"copy_code\",");
		sb.append("pId:\"system\",");
		sb.append("name:\"账套编码\",");
		sb.append("ele_type:\"999\",");
		sb.append("formula_type:\"1\",");// 公式类型1非计算元素、2计算元素
		sb.append("title:\"copy_code\",");
		sb.append("open:false");
		sb.append("}");

		sb.append(",{");
		sb.append("id:\"copy_name\",");
		sb.append("pId:\"system\",");
		sb.append("name:\"账套名称\",");
		sb.append("ele_type:\"999\",");
		sb.append("formula_type:\"1\",");// 公式类型1非计算元素、2计算元素
		sb.append("title:\"copy_name\",");
		sb.append("open:false");
		sb.append("}");

		sb.append(",{");
		sb.append("id:\"user_code\",");
		sb.append("pId:\"system\",");
		sb.append("name:\"用户编码\",");
		sb.append("ele_type:\"999\",");
		sb.append("formula_type:\"1\",");// 公式类型1非计算元素、2计算元素
		sb.append("title:\"user_code\",");
		sb.append("open:false");
		sb.append("}");

		sb.append(",{");
		sb.append("id:\"user_name\",");
		sb.append("pId:\"system\",");
		sb.append("name:\"用户名称\",");
		sb.append("ele_type:\"999\",");
		sb.append("formula_type:\"1\",");// 公式类型1非计算元素、2计算元素
		sb.append("title:\"user_name\",");
		sb.append("open:false");
		sb.append("}");

		sb.append(",{");
		sb.append("id:\"emp_code\",");
		sb.append("pId:\"system\",");
		sb.append("name:\"职工编码\",");
		sb.append("ele_type:\"999\",");
		sb.append("formula_type:\"1\",");// 公式类型1非计算元素、2计算元素
		sb.append("title:\"emp_code\",");
		sb.append("open:false");
		sb.append("}");

		sb.append(",{");
		sb.append("id:\"emp_name\",");
		sb.append("pId:\"system\",");
		sb.append("name:\"职工名称\",");
		sb.append("ele_type:\"999\",");
		sb.append("formula_type:\"1\",");// 公式类型1非计算元素、2计算元素
		sb.append("title:\"emp_name\",");
		sb.append("open:false");
		sb.append("}");

		sb.append(",{");
		sb.append("id:\"sys_year\",");
		sb.append("pId:\"system\",");
		sb.append("name:\"系统年度\",");
		sb.append("ele_type:\"999\",");
		sb.append("note:\"系统登录的年度，格式：yyyy\",");
		sb.append("formula_type:\"1\",");// 公式类型1非计算元素、2计算元素
		sb.append("title:\"sys_year\",");
		sb.append("open:false");
		sb.append("}");

		sb.append(",{");
		sb.append("id:\"sys_month\",");
		sb.append("pId:\"system\",");
		sb.append("name:\"系统月份\",");
		sb.append("ele_type:\"999\",");
		sb.append("note:\"系统登录的月份，格式：MM\",");
		sb.append("formula_type:\"1\",");// 公式类型1非计算元素、2计算元素
		sb.append("title:\"sys_month\",");
		sb.append("open:false");
		sb.append("}");

		sb.append(",{");
		sb.append("id:\"sys_date\",");
		sb.append("pId:\"system\",");
		sb.append("name:\"系统时间[年月日]\",");
		sb.append("ele_type:\"999\",");
		sb.append("note:\"格式：yyyy-MM-dd\",");
		sb.append("formula_type:\"1\",");// 公式类型1非计算元素、2计算元素
		sb.append("title:\"sys_date\",");
		sb.append("open:false");
		sb.append("}");

		sb.append(",{");
		sb.append("id:\"sys_time\",");
		sb.append("pId:\"system\",");
		sb.append("name:\"系统时间[年月日时分秒]\",");
		sb.append("ele_type:\"999\",");
		sb.append("note:\"格式：yyyy-MM-dd 24HH:mm:ss\",");
		sb.append("formula_type:\"1\",");// 公式类型1非计算元素、2计算元素
		sb.append("title:\"sys_time\",");
		sb.append("open:false");
		sb.append("}");

		sb.append(",{");
		sb.append("id:\"report_year\",");
		sb.append("pId:\"system\",");
		sb.append("name:\"报表年度\",");
		sb.append("ele_type:\"999\",");
		sb.append("note:\"月报、季报、半年报、年报有效，页面选择的年度\",");
		sb.append("formula_type:\"1\",");// 公式类型1非计算元素、2计算元素
		sb.append("title:\"report_year\",");
		sb.append("open:false");
		sb.append("}");

		sb.append(",{");
		sb.append("id:\"report_period\",");
		sb.append("pId:\"system\",");
		sb.append("name:\"报表期间\",");
		sb.append("ele_type:\"999\",");
		sb.append("note:\"月报、季报、半年报、年报有效，页面选择的期间\",");
		sb.append("formula_type:\"1\",");// 公式类型1非计算元素、2计算元素
		sb.append("title:\"report_period\",");
		sb.append("open:false");
		sb.append("}");

		return sb.toString();
	}

	@Override
	public String queryMakeSuperReportDsManager(Map<String, Object> mapVo) throws DataAccessException {

		List<Map<String, Object>> list = superReportDataSetMapper.querySuperReportDsColForMake(mapVo);

		StringBuffer sb = new StringBuffer();
		sb.append("{Rows:[");

		// 拼装级次
		if (list != null && list.size() > 0) {
			int row = 0;
			// 系统模块（一级）
			Map<String, Object> modMap = new HashMap<String, Object>();
			for (Map<String, Object> map : list) {
				if (modMap.get(map.get("mod_code").toString()) == null) {
					if (row == 0) {
						sb.append("{");
					} else {
						sb.append(",{");
					}
					row++;
					modMap.put(map.get("mod_code").toString(), map.get("mod_code").toString());
					sb.append("id:\"" + map.get("mod_code").toString() + "\",");
					sb.append("pId:\"top\",");
					sb.append("name:\"" + map.get("mod_name") + "\",");
					sb.append("title:\"" + map.get("mod_name") + "\",");
					sb.append("open:false");
					sb.append("}");
				}
			}

			Map<String, Object> classMap = new HashMap<String, Object>();
			row = 0;
			for (Map<String, Object> map : list) {
				String ds_class = map.get("ds_class").toString();
				if (classMap.get(ds_class) == null) {
					String className = "";
					if ("1".equals(ds_class)) {
						className = "行数据集";
					} else {
						className = "列数据集";
					}
					if (row == 0) {
						sb.append("{");
					} else {
						sb.append(",{");
					}
					row++;
					classMap.put(ds_class, ds_class);
					sb.append("id:\"" + ds_class + "\",");
					sb.append("pId:\"" + modMap.get(map.get("mod_code").toString()) + "\",");
					sb.append("name:\"" + className + "\",");
					sb.append("title:\"" + className + "\",");
					sb.append("open:false");
					sb.append("}");
				}
			}

			// 数据集分组（三级）
			row = 0;
			Map<String, Object> groupMap = new HashMap<String, Object>();
			for (Map<String, Object> map : list) {
				if (groupMap.get(map.get("ds_group").toString()) == null) {
					if (row == 0) {
						sb.append("{");
					} else {
						sb.append(",{");
					}
					row++;
					groupMap.put(map.get("ds_group").toString(), "group_"+row);
					sb.append("id:\"group_"+row + "\",");
					sb.append("pId:\"" + classMap.get(map.get("ds_class").toString()) + "\",");
					sb.append("name:\"" + map.get("ds_group") + "\",");
					sb.append("title:\"" + map.get("ds_group") + "\",");
					sb.append("open:false");
					sb.append("}");
				}
			}

			// 数据集（四级）
			row = 0;
			Map<String, Object> dsMap = new HashMap<String, Object>();
			for (Map<String, Object> map : list) {
				if (dsMap.get(map.get("ds_code").toString()) == null) {
					if (row == 0) {
						sb.append("{");
					} else {
						sb.append(",{");
					}
					row++;
					dsMap.put(map.get("ds_code").toString(), map.get("ds_code").toString());
					sb.append("id:\"" + map.get("ds_code") + "\",");
					sb.append("pId:\"" + groupMap.get(map.get("ds_group").toString()) + "\",");
					sb.append("name:\"" + map.get("ds_name") + "\",");
					sb.append("title:\"" + map.get("ds_name") + "\",");
					sb.append("open:false");
					sb.append("}");
				}
			}

			// 数据列（五级）
			row = 0;
			for (Map<String, Object> map : list) {
				if (dsMap.get(map.get("ds_code").toString()) != null) {
					if (row == 0) {
						sb.append("{");
					} else {
						sb.append(",{");
					}
					row++;
					sb.append("id:\"" + map.get("col_code") + "\",");
					sb.append("pId:\"" + dsMap.get(map.get("ds_code").toString()) + "\",");
					sb.append("name:\"" + map.get("col_name") + "\",");
					sb.append("title:\"" + map.get("col_name") + "\",");
					sb.append("open:false");
					sb.append("}");
				}
			}
		}
		sb.append("]}");
		return sb.toString();
	}

	@Override
	public List<Map<String, Object>> queryDetailParamForMakeReport(Map<String, Object> mapVo) {
		List<Map<String, Object>> list = new ArrayList<>();
		String data = (String) mapVo.get("data");
		String[] strings = data.trim().split(",");
		for (String string : strings) {
			String[] split = string.split("\\.");
			Map<String, Object> map = new HashMap<>();
			map.put("ds_code", split[0]);
			map.put("col_code", split[1]);
			list.add(map);
		}
		mapVo.put("list", list);
		List<Map<String, Object>> relist = superReportDataSetMapper.queryDetailParamForMakeReport(mapVo);

		List<Map<String, Object>> list2 = superReportDataSetMapper.queryDetailParamForMakeReport2(mapVo);
		
		for (Map<String, Object> map : list2) {
			String id = map.get("para_id").toString();
			for (Map<String, Object> map2 : relist) {
				String paraId =  map2.get("para_id").toString();
				if (id.equals(paraId)) {
					map2.put("para_value", map.get("para_value"));
				}
			}
		}

		return relist;
	}

	@Override
	public String updateRepRepDSPara(Map<String, Object> mapVo) {
		try {
			superReportDataSetMapper.saveRepRepDSPara(mapVo);
			return "{\"msg\":\"更新成功！\",\"state\":\"true\"}";
		} catch (Exception e) {
			throw new SysException(e.getMessage(), e.getCause());
		}
	}

	@Override
	public List<Map<String, Object>> queryDsParaByDistinct(
			Map<String, Object> mapVo) {
		List<Map<String, Object>> relist = superReportDataSetMapper.queryDsParaByDistinct(mapVo);
		return relist;
	}

}
