package com.chd.hrp.hpm.serviceImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.chd.base.SessionManager;
import com.chd.base.SysPage;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.base.util.SpreadTableJSUtil;
import com.chd.hrp.hpm.dao.AphiCostitemDataMapper;
import com.chd.hrp.hpm.dao.AphiCostitemSeqMapper;
import com.chd.hrp.hpm.dao.AphiDeptDictMapper;
import com.chd.hrp.hpm.dao.AphiDeptHipMapper;
import com.chd.hrp.hpm.dao.AphiDeptMapper;
import com.chd.hrp.hpm.entity.AphiCostitemConf;
import com.chd.hrp.hpm.entity.AphiCostitemData;
import com.chd.hrp.hpm.entity.AphiCostitemSeq;
import com.chd.hrp.hpm.entity.AphiDept;
import com.chd.hrp.hpm.entity.AphiDeptDict;
import com.chd.hrp.hpm.entity.AphiDeptHip;
import com.chd.hrp.hpm.entity.AphiSchemeConf;
import com.chd.hrp.hpm.service.AphiCostitemDataService;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Title. @Description.
 * 
 * @Copyright: Copyright (c) 2015-2-14 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @Author: LiuYingDuo
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Service("aphiCostitemDataService")
public class AphiCostitemDataServiceImpl implements AphiCostitemDataService {

	private static Logger logger = Logger.getLogger(AphiCostitemDataServiceImpl.class);

	@Resource(name = "aphiCostitemDataMapper")
	private final AphiCostitemDataMapper aphiCostitemDataMapper = null;

	@Resource(name = "aphiDeptDictMapper")
	private final AphiDeptDictMapper aphiDeptDictMapper = null;

	@Resource(name = "aphiCostitemSeqMapper")
	private final AphiCostitemSeqMapper aphiCostitemSeqMapper = null;

	@Resource(name = "aphiDeptMapper")
	private final AphiDeptMapper aphiDeptMapper = null;

	@Resource(name = "aphiDeptHipMapper")
	private final AphiDeptHipMapper aphiDeptHipMapper = null;

	/**
	 * 
	 */
	@Override
	public String addCostitemData(Map<String, Object> entityMap) throws DataAccessException {

		int state = aphiCostitemDataMapper.addCostitemData(entityMap);

		if (state > 0) {

			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		} else {

			return "{\"msg\":\"添加失败.\",\"state\":\"false\"}";

		}
	}

	/**
	 * 
	 */
	@Override
	public String queryCostitemData(Map<String, Object> entityMap) throws DataAccessException {

		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		if (sysPage.getTotal() == -1) {

			List<AphiCostitemData> list = aphiCostitemDataMapper.queryCostitemData(entityMap);

			return ChdJson.toJson(list);

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

			List<AphiCostitemData> list = aphiCostitemDataMapper.queryCostitemData(entityMap, rowBounds);

			PageInfo page = new PageInfo(list);

			return ChdJson.toJson(list, page.getTotal());

		}
	}

	/**
	 * 
	 */
	@Override
	public AphiCostitemData queryCostitemDataByCode(Map<String, Object> entityMap) throws DataAccessException {

		return aphiCostitemDataMapper.queryCostitemDataByCode(entityMap);

	}

	/**
	 * 
	 */
	@Override
	public String deleteCostitemDataCodes(Map<String, Object> entityMap, String codes) throws DataAccessException {

		try {

			if (codes != null && !codes.equals("")) {

				String[] code_codeArray = codes.split(",");

				for (String code : code_codeArray) {

					String[] code_array = code.split(";");

					entityMap.put("acct_year", code_array[3]);

					entityMap.put("acct_month", code_array[4]);

					entityMap.put("dept_id", code_array[0]);
					entityMap.put("dept_no", code_array[1]);
					entityMap.put("cost_item_code", code_array[2]);

					// AphiDeptBonusAudit deptBonusAudit =
					// aphiDeptBonusAuditMapper.queryDeptBonusAuditByCode(entityMap);
					// if(deptBonusAudit !=null){
					//
					// if( deptBonusAudit.getIs_audit()== 1){
					//
					// return "{\"msg\":\"本月奖金已审核后。数据不能删除.\",\"state\":\"true\"}";
					//
					// }
					// }

					aphiCostitemDataMapper.deleteCostitemData(entityMap);
				}

				return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

			} else {

				return "{\"msg\":\"没有要删除的数据.\",\"state\":\"false\"}";

			}

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException("{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码  deleteIncomeItem\"}");

		}
	}

	/**
	 * 
	 */
	@Override
	public String updateCostitemData(Map<String, Object> entityMap) throws DataAccessException {

		// AphiDeptBonusAudit deptBonusAudit =
		// aphiDeptBonusAuditMapper.queryDeptBonusAuditByCode(entityMap);
		// if(deptBonusAudit !=null){
		//
		// if( deptBonusAudit.getIs_audit()== 1){
		//
		// return "{\"msg\":\"本月奖金已审核后。数据不能删除.\",\"state\":\"true\"}";
		//
		// }
		// }

		int state = aphiCostitemDataMapper.updateCostitemData(entityMap);

		if (state > 0) {

			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";

		} else {

			return "{\"msg\":\"修改失败.\",\"state\":\"false\"}";

		}
	}

	@Override
	public String initCostitemData(Map<String, Object> entityMap) throws DataAccessException {
		if (entityMap.get("user_id") == null) {
			entityMap.put("user_id", SessionManager.getUserId());
		}
		String codes = (String) entityMap.get("checkIds");

		String[] code_array = codes.split(";");

		String year_month = code_array[0];

		String acct_year = year_month.substring(0, 4);

		String acct_month = year_month.substring(4, 6);

		entityMap.put("acct_year", acct_year);

		entityMap.put("acct_month", acct_month);

		if (code_array.length > 2) {

			String dept_id = code_array[1];

			entityMap.put("dept_id", dept_id);
			entityMap.put("dept_id", code_array[2]);

		} else if (code_array.length > 3) {

			String cost_item_code = code_array[3];

			entityMap.put("cost_item_code", cost_item_code);

		}

		String rbtnl = (String) entityMap.get("rbtnl");

		if ("all".equals(rbtnl)) {

			try {

				deleteCostitemData(entityMap);

				List<AphiCostitemData> costList = aphiCostitemDataMapper.queryCostitemDept(entityMap);

				if (costList.size() > 0) {

					for (int i = 0; i < costList.size(); i++) {

						AphiCostitemData costitemData = costList.get(i);

						entityMap.put("dept_id", costitemData.getDept_id());
						entityMap.put("dept_no", costitemData.getDept_no());

						entityMap.put("cost_item_code", costitemData.getCost_item_code());

						aphiCostitemDataMapper.addCostitemData(entityMap);

					}

					return "{\"msg\":\"生成成功.\",\"state\":\"true\"}";

				}

				return "{\"msg\":\"没有生成数据.\",\"state\":\"false\"}";

			} catch (Exception e) {
				logger.error(e.getMessage(), e);
				throw new SysException("{\"error\":\"生成失败 数据库异常 请联系管理员! 错误编码  initIncomeitemData\"}");
			}

		} else {

			try {

				entityMap.put("sql",
						"select cost_item_code from aphi_costitem_data ahtd where ahtd.acct_month=#{acct_month} and ahtd.acct_year=#{acct_year}");

				List<AphiCostitemData> list = aphiCostitemDataMapper.queryCostitemDept(entityMap);

				entityMap.put("sqls",
						"select dept_id from aphi_costitem_data ahtd where ahtd.acct_month=#{acct_month} and ahtd.acct_year=#{acct_year}");

				entityMap.put("sql", "");

				List<AphiCostitemData> costItmeList = aphiCostitemDataMapper.queryCostitemDept(entityMap);

				if (list.size() > 0 && costItmeList.size() > 0) {

					for (int i = 0; i < costItmeList.size(); i++) {

						AphiCostitemData costitemData = costItmeList.get(i);

						entityMap.put("dept_id", costitemData.getDept_id());
						entityMap.put("dept_id", costitemData.getDept_no());

						for (int j = 0; j < list.size(); j++) {

							AphiCostitemData costitem = list.get(j);

							entityMap.put("cost_item_code", costitem.getCost_item_code());

							aphiCostitemDataMapper.addCostitemData(entityMap);

						}

					}

					return "{\"msg\":\"生成成功.\",\"state\":\"true\"}";

				}

				return "{\"msg\":\"没有生成数据.\",\"state\":\"false\"}";

			} catch (Exception e) {
				logger.error(e.getMessage(), e);
				throw new SysException("{\"error\":\"生成失败 数据库异常 请联系管理员! 错误编码  initIncomeitemData\"}");

			}

		}

	}

	@Override
	public List<AphiCostitemConf> getCostItemConf(Map<String, Object> entityMap) throws DataAccessException {

		return aphiCostitemDataMapper.getCostItemConf(entityMap);

	}

	@Override
	public String calculate(Map<String, Object> entityMap) throws DataAccessException {

		int state = aphiCostitemDataMapper.calculate(entityMap);

		if (state > 0) {

			return "{\"msg\":\"计算成功.\",\"state\":\"true\"}";

		} else {

			return "{\"msg\":\"计算失败.\",\"state\":\"false\"}";

		}
	}

	@Override
	public AphiCostitemConf getCostItemConfByCode(Map<String, Object> entityMap) throws DataAccessException {

		return aphiCostitemDataMapper.getCostItemConfByCode(entityMap);

	}

	@Override
	public AphiSchemeConf getSchemeSeqNo(Map<String, Object> entityMap) throws DataAccessException {

		return aphiCostitemDataMapper.getSchemeSeqNo(entityMap);

	}

	@Override
	public String addBatchCostitemData(List<Map<String, Object>> entityMap) throws DataAccessException {

		int state = aphiCostitemDataMapper.addBatchCostitemData(entityMap);

		if (state > 0) {

			return "{\"msg\":\"导入成功.\",\"state\":\"true\"}";

		} else {

			return "{\"msg\":\"导入失败.\",\"state\":\"false\"}";

		}
	}

	@Override
	public String getCostitemTargetValue(Map<String, Object> entityMap) throws DataAccessException {
		String acct_year_month = (String) entityMap.get("acct_yearm");

		entityMap.put("acct_year", acct_year_month.substring(0, 4));

		entityMap.put("acct_month", acct_year_month.substring(4, 6));

		// List<AphiDeptBonusAudit> deptBonusAuditList =
		// aphiDeptBonusAuditMapper.deptBonusIsAudit(entityMap);
		//
		// if(deptBonusAuditList.size()>0){
		//
		// return "{\"state\":\"true\"}";
		//
		// }else{
		//
		// return "{\"state\":\"false\"}";
		//
		// }

		return "{\"state\":\"true\"}";
	}

	@Override
	public String deleteCostitemData(Map<String, Object> entityMap) throws DataAccessException {

		int state = aphiCostitemDataMapper.deleteCostitemData(entityMap);

		if (state > 0) {

			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		} else {

			return "{\"msg\":\"没有要删除的数据.\",\"state\":\"false\"}";

		}
	}

	// 导入数据
	@Override
	public String hpmcostitemDataImport(Map<String, Object> map) throws DataAccessException {
		try {
			String columns = map.get("columns").toString();
			JSONArray jsonColumns = JSONObject.parseArray(columns);
			if (jsonColumns == null || jsonColumns.size() == 0) {
				return "{\"error\":\"表头为空！\",\"state\":\"false\"}";
			}

			String content = map.get("content").toString();
			List<Map<String, List<String>>> liData = SpreadTableJSUtil.toListMap(content, 1);
			if (liData == null || liData.size() == 0) {
				return "{\"error\":\"没有数据！\",\"state\":\"false\"}";
			}

			// new Map查询导入时对应数据信息
			Map<String, Object> entityMap = new HashMap<String, Object>();
			entityMap.put("group_id", SessionManager.getGroupId());
			entityMap.put("hos_id", SessionManager.getHosId());
			entityMap.put("copy_code", SessionManager.getCopyCode());
			entityMap.put("user_id", SessionManager.getUserId());
			entityMap.put("is_stop", "0");// 查询未停用
			entityMap.put("dept_kind_code", map.get("dept_kind_code"));

			// 查询所有绩效科室
			List<AphiDeptDict> deptDictList = aphiDeptDictMapper.queryPrmDeptDict(entityMap);
			// 用于存储查询deptList中的AphiDept对象,以键值对的形式存储,用于判断科室是否存在
			Map<String, AphiDeptDict> deptDictMap = new HashMap<String, AphiDeptDict>();
			// 将科室List存入Map 键:dept_name 值:AphiDeptDict
			for (AphiDeptDict deptDict : deptDictList) {
				deptDictMap.put(deptDict.getDept_name(), deptDict);
				deptDictMap.put(deptDict.getDept_code(), deptDict);
			}

			// 按科室与系统平台对应关系查询科室 List
			List<AphiDept> deptList = aphiDeptMapper.queryAphiDeptRelaByMaping(entityMap);
			// 用于存储查询deptList中的AphiDept对象,以键值对的形式存储,用于判断科室是否存在
			Map<String, AphiDept> deptMap = new HashMap<String, AphiDept>();
			// 将科室List存入Map 键:dept_name 值:AphiDept
			for (AphiDept dept : deptList) {
				deptMap.put(dept.getDept_name(), dept);
				deptMap.put(dept.getDept_code(), dept);
			}

			// 按科室与其它平台对应关系查询科室 List
			List<AphiDeptHip> deptHipList = aphiDeptHipMapper.queryAphiDeptRelaByMapingHip(entityMap);
			// 用于存储查询deptHipList中的AphiDeptHip对象,以键值对的形式存储,用于判断科室是否存在
			Map<String, AphiDeptHip> deptHipMap = new HashMap<String, AphiDeptHip>();
			// 将科室List存入Map 键:dept_name 值:AphiDeptHip
			for (AphiDeptHip deptHip : deptHipList) {
				deptHipMap.put(deptHip.getDept_name(), deptHip);
				deptHipMap.put(deptHip.getDept_code(), deptHip);
			}

			// 查询支出项目字典List
			List<AphiCostitemSeq> costitemSeqList = aphiCostitemSeqMapper.queryCostitemSeq(entityMap);
			// 以键值对的形式存储,用于判断是否存在支出项目
			Map<String, AphiCostitemSeq> costitemSeqMap = new HashMap<String, AphiCostitemSeq>();
			//// 收入项目List 放入Map 键 cost_item_code 值 AphiCostitemSeq
			for (AphiCostitemSeq costItemSeq : costitemSeqList) {
				costitemSeqMap.put(costItemSeq.getCost_item_code(), costItemSeq);
				costitemSeqMap.put(costItemSeq.getCost_iitem_name(), costItemSeq);
			}

			// 用于存储传的数据值,判断数据是否重复
			Map<String, String> exitMap = new HashMap<String, String>();
			// 存储要添加保存的数据List
			List<Map<String, Object>> returnList = new ArrayList<Map<String, Object>>();
			// 用于记录重复数据
			StringBuffer err_sb = new StringBuffer();

			for (Map<String, List<String>> item : liData) {
				for (String st : item.keySet()) {
					if (item.get(st).get(1) == null) {
						break;
					}

					List<String> acct_year = item.get("核算年度");
					List<String> acct_month = item.get("核算月份");
					List<String> dept_name = item.get("科室名称");
					List<String> cost_item_code = item.get("支出项目编码");
					List<String> prim_cost = item.get("直接支出");
					List<String> calc_cost = item.get("间接支出");

					if (acct_year.get(1) == null) {
						return "{\"warn\":\"年度为空！\",\"state\":\"false\",\"row_cell\":" + acct_year.get(0) + "\"\"}";
					}

					if (acct_month.get(1) == null) {
						return "{\"warn\":\"月份为空！\",\"state\":\"false\",\"row_cell\":\"" + acct_month.get(0) + "\"}";
					}

					if (dept_name.get(1) == null) {
						return "{\"warn\":\"科室名称为空！\",\"state\":\"false\",\"row_cell\":\"\"}";
					} else {
						if (deptMap.get(dept_name.get(1)) == null && deptHipMap.get(dept_name.get(1)) == null
								&& deptDictMap.get(dept_name.get(1)) == null) {
							return "{\"warn\":\"" + dept_name.get(1) + ",科室名称不存在！\",\"state\":\"false\",\"row_cell\":\""
									+ dept_name.get(0) + "\"}";
						}
					}

					if (cost_item_code.get(1) == null) {
						return "{\"warn\":\"支出项目编码单元格为空！\",\"state\":\"false\",\"row_cell\":\"" + cost_item_code.get(0)
								+ "\"}";
					} else if (Character.isDigit(cost_item_code.get(1).charAt(0))) {
						if (costitemSeqMap.get(cost_item_code.get(1)) == null) {
							return "{\"warn\":\"支出项目编码不存在！\",\"state\":\"false\",\"row_cell\":\""
									+ cost_item_code.get(0) + "\"}";
						}
					} else if (costitemSeqMap.get(cost_item_code.get(1)) == null) {
						return "{\"warn\":\"支出项目名称不存在！\",\"state\":\"false\",\"row_cell\":\"" + cost_item_code.get(0)
								+ "\"}";
					}

					if (prim_cost.get(1) == null) {
						return "{\"warn\":\"直接支出为空！\",\"state\":\"false\",\"row_cell\":\" " + prim_cost.get(0) + "\"}";
					} else {
						try {
							Double.parseDouble((prim_cost.get(1)));
						} catch (NumberFormatException e) {
							return "{\"warn\":\"" + prim_cost.get(1)
									+ ",直接支出输入不合法,只能输入数字！\",\"state\":\"false\",\"row_cell\":\"" + prim_cost.get(0)
									+ "\"}";
						}
					}

					if (calc_cost.get(1) == null) {
						return "{\"warn\":\"间接支出为空！\",\"state\":\"false\",\"row_cell\":\" " + calc_cost.get(0) + "\"}";
					} else {
						try {
							Double.parseDouble((calc_cost.get(1)));
						} catch (NumberFormatException e) {
							return "{\"warn\":\"" + calc_cost.get(1)
									+ ",间接支出输入不合法,只能输入数字！\",\"state\":\"false\",\"row_cell\":\"" + calc_cost.get(0)
									+ "\"}";
						}
					}

					// 以年度+月份+指标编码+科室名称为键值,判断导入数据是否重复
					String key = acct_year.get(1) + acct_month.get(1) + cost_item_code.get(1) + dept_name.get(1);
					if (exitMap.get(key) != null) {
						err_sb.append(acct_year.get(1) + "年度," + acct_month.get(1) + "月份," + cost_item_code.get(1)
								+ "支出项目," + dept_name.get(1) + "科室名称 ");
					} else {
						exitMap.put(key, key);
					}

					// 添加数据Map中add到returnList
					Map<String, Object> returnMap = new HashMap<String, Object>();
					returnMap.put("group_id", SessionManager.getGroupId());
					returnMap.put("hos_id", SessionManager.getHosId());
					returnMap.put("copy_code", SessionManager.getCopyCode());
					returnMap.put("acct_year", acct_year.get(1));
					returnMap.put("acct_month", acct_month.get(1));
					returnMap.put("dept_name", dept_name.get(1));
					// 系统平台科室
					if (deptMap.get(dept_name.get(1)) != null) {
						returnMap.put("dept_id", deptMap.get(dept_name.get(1)).getDept_id());
						returnMap.put("dept_no", deptMap.get(dept_name.get(1)).getDept_no());
					}

					// 其它平台科室
					if (deptHipMap.get(dept_name.get(1)) != null) {
						returnMap.put("dept_id", deptHipMap.get(dept_name.get(1)).getDept_id());
						returnMap.put("dept_no", deptHipMap.get(dept_name.get(1)).getDept_no());
					}

					// 绩效科室
					if (deptDictMap.get(dept_name.get(1)) != null) {
						returnMap.put("dept_id", deptDictMap.get(dept_name.get(1)).getDept_id());
						returnMap.put("dept_no", deptDictMap.get(dept_name.get(1)).getDept_no());
					}
					returnMap.put("cost_item_code", cost_item_code.get(1));
					returnMap.put("prim_cost", prim_cost.get(1));
					returnMap.put("prim_cost_ret", 0);// 进一步做关联嘻哈偶像呢计算计提比例
					returnMap.put("calc_cost", calc_cost.get(1));
					returnMap.put("calc_cost_ret", 0);
					returnMap.put("cost_tot_ret", 0);

					returnList.add(returnMap);
					break;
				}
			}
			if (err_sb.toString().length() > 0) {
				return "{\"warn\":\"以下数据【" + err_sb.toString() + "】数据重复！\",\"state\":\"false\"}";
			} else {
				aphiCostitemDataMapper.addBatch(returnList);
				return "{\"msg\":\"导入成功.\",\"state\":\"true\"}";
			}

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}
	}

}
