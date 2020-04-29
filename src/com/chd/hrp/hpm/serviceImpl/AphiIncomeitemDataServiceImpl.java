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
import com.chd.hrp.hpm.dao.AphiDeptDictMapper;
import com.chd.hrp.hpm.dao.AphiDeptHipMapper;
import com.chd.hrp.hpm.dao.AphiDeptIncomeMapper;
import com.chd.hrp.hpm.dao.AphiDeptMapper;
import com.chd.hrp.hpm.dao.AphiIncomeitemDataMapper;
import com.chd.hrp.hpm.dao.AphiIncomeitemSeqMapper;
import com.chd.hrp.hpm.entity.AphiDept;
import com.chd.hrp.hpm.entity.AphiDeptDict;
import com.chd.hrp.hpm.entity.AphiDeptHip;
import com.chd.hrp.hpm.entity.AphiDeptIncome;
import com.chd.hrp.hpm.entity.AphiIncomeitemConf;
import com.chd.hrp.hpm.entity.AphiIncomeitemData;
import com.chd.hrp.hpm.entity.AphiIncomeitemSeq;
import com.chd.hrp.hpm.entity.AphiSchemeConf;
import com.chd.hrp.hpm.service.AphiIncomeitemDataService;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Title.
 * @Description.
 * @Copyright: Copyright (c) 2015-2-14 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @Author: LiuYingDuo
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Service("aphiIncomeitemDataService")
public class AphiIncomeitemDataServiceImpl implements AphiIncomeitemDataService {
	
	private static Logger logger = Logger.getLogger(AphiIncomeitemDataServiceImpl.class);
	
	@Resource(name = "aphiIncomeitemDataMapper")
	private final AphiIncomeitemDataMapper aphiIncomeitemDataMapper = null;
	
	@Resource(name = "aphiDeptDictMapper")
	private final AphiDeptDictMapper aphiDeptDictMapper = null;
	
	@Resource(name = "aphiIncomeitemSeqMapper")
	private final AphiIncomeitemSeqMapper aphiIncomeitemSeqMapper = null;
	
	@Resource(name = "aphiDeptMapper")
	private final AphiDeptMapper aphiDeptMapper = null;
	
	@Resource(name = "aphiDeptHipMapper")
	private final AphiDeptHipMapper aphiDeptHipMapper = null;
	
	@Resource(name = "aphiDeptIncomeMapper")
	private AphiDeptIncomeMapper aphiDeptIncomeMapper = null;
	
	/**
	 * 
	 */
	@Override
	public String addIncomeitemData(Map<String, Object> entityMap) throws DataAccessException {
		
		// 查询关联表得到数据
		Map<String, Object> returnMap = new HashMap<String, Object>();
		
		int state = aphiIncomeitemDataMapper.addIncomeitemData(entityMap);
		
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
	public String queryIncomeitemData(Map<String, Object> entityMap) throws DataAccessException {
		if (entityMap.get("user_id") == null) {
			entityMap.put("user_id", SessionManager.getUserId());
		}
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal() == -1) {
			
			List<AphiIncomeitemData> list = aphiIncomeitemDataMapper.queryIncomeitemData(entityMap);
			
			return ChdJson.toJson(list);
			
		} else {
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<AphiIncomeitemData> list = aphiIncomeitemDataMapper.queryIncomeitemData(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
		
	}
	
	/**
	 * 
	 */
	@Override
	public AphiIncomeitemData queryIncomeitemDataByCode(Map<String, Object> entityMap) throws DataAccessException {
		if (entityMap.get("user_id") == null) {
			entityMap.put("user_id", SessionManager.getUserId());
		}
		return aphiIncomeitemDataMapper.queryIncomeitemDataByCode(entityMap);
		
	}
	
	/**
	 * 
	 */
	@Override
	public String deleteIncomeitemDataCodes(Map<String, Object> entityMap, String codes) throws DataAccessException {
		
		try {
			
			if (codes != null && !codes.equals("")) {
				
				String[] code_codeArray = codes.split(",");
				
				for (String code : code_codeArray) {
					
					String[] code_array = code.split(";");
					
					entityMap.put("dept_id", code_array[0]);
					entityMap.put("dept_no", code_array[1]);
					entityMap.put("cost_item_code", code_array[2]);
					
					entityMap.put("acct_year", code_array[3]);
					
					entityMap.put("acct_month", code_array[4]);
					
					aphiIncomeitemDataMapper.deleteIncomeitemData(entityMap);
					
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
	public String updateIncomeitemData(Map<String, Object> entityMap) throws DataAccessException {
		
		// AphiDeptBonusAudit dba =
		// aphiDeptBonusAuditMapper.queryDeptBonusAuditByCode(entityMap);
		//
		// if (dba != null) {
		//
		// if (dba.getIs_audit() == 1) {
		//
		// return "{\"error\":\"本月奖金已审核,不能修改本月数据\"}";
		//
		// }
		// if (dba.getIs_grant() == 1) {
		//
		// return "{\"error\":\"本月奖金已发放,不能修改本月数据\"}";
		//
		// }
		//
		// }
		
		try {
			
			aphiIncomeitemDataMapper.updateIncomeitemData(entityMap);
			
			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";
			
		} catch (Exception e) {
			
			logger.error(e.getMessage(), e);
			
			throw new SysException("{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updateHospTargetData\"}");
			
		}
		
	}
	
	@Override
	public String initIncomeitemData(Map<String, Object> entityMap) throws DataAccessException {
		if (entityMap.get("user_id") == null) {
			entityMap.put("user_id", SessionManager.getUserId());
		}
		String codes = (String) entityMap.get("checkIds");
		
		String[] code_array = codes.split(",");
		
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
			
			String income_item_code = code_array[3];
			
			entityMap.put("income_item_code", income_item_code);
			
		}
		
		String rbtnl = (String) entityMap.get("rbtnl");
		
		if ("all".equals(rbtnl)) {
			
			try {
				
				deleteIncomeitemData(entityMap);
				
				List<AphiIncomeitemData> incomeList = aphiIncomeitemDataMapper.queryIncomeitemDept(entityMap);
				
				if (incomeList.size() > 0) {
					
					for (int i = 0; i < incomeList.size(); i++) {
						
						AphiIncomeitemData incomeitemData = incomeList.get(i);
						
						entityMap.put("dept_id", incomeitemData.getDept_id());
						entityMap.put("dept_no", incomeitemData.getDept_no());
						
						entityMap.put("income_item_code", incomeitemData.getIncome_item_code());
						
						aphiIncomeitemDataMapper.addIncomeitemData(entityMap);
						
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
						"select income_item_code from aphi_incomeItem_data ahtd where ahtd.acct_month=#{acct_month} and ahtd.acct_year=#{acct_year}");
				
				List<AphiIncomeitemData> list = aphiIncomeitemDataMapper.queryIncomeitemDept(entityMap);
				
				entityMap.put("sqls", "select dept_id from aphi_incomeItem_data ahtd where ahtd.acct_month=#{acct_month} and ahtd.acct_year=#{acct_year}");
				
				entityMap.put("sql", "");
				
				List<AphiIncomeitemData> incoemItmeList = aphiIncomeitemDataMapper.queryIncomeitemDept(entityMap);
				
				if (list.size() > 0 && incoemItmeList.size() > 0) {
					
					for (int i = 0; i < incoemItmeList.size(); i++) {
						
						AphiIncomeitemData incomeitemData = incoemItmeList.get(i);
						
						entityMap.put("dept_id", incomeitemData.getDept_id());
						entityMap.put("dept_no", incomeitemData.getDept_no());
						
						for (int j = 0; j < list.size(); j++) {
							
							AphiIncomeitemData incomeitem = list.get(j);
							
							entityMap.put("income_item_code", incomeitem.getIncome_item_code());
							
							aphiIncomeitemDataMapper.addIncomeitemData(entityMap);
							
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
	public List<AphiIncomeitemConf> getIncomeItemConfSeq(Map<String, Object> entityMap) throws DataAccessException {
		
		return aphiIncomeitemDataMapper.getIncomeItemConfSeq(entityMap);
		
	}
	
	@Override
	public String calculate(Map<String, Object> entityMap) throws DataAccessException {
		
		int state = aphiIncomeitemDataMapper.calculate(entityMap);
		
		if (state > 0) {
			
			return "{\"msg\":\"计算成功.\",\"state\":\"true\"}";
			
		} else {
			
			return "{\"msg\":\"计算失败.\",\"state\":\"false\"}";
		}
	}
	
	@Override
	public AphiSchemeConf getSchemeSeqNo(Map<String, Object> entityMap) throws DataAccessException {
		
		return aphiIncomeitemDataMapper.getSchemeSeqNo(entityMap);
		
	}
	
	@Override
	public AphiIncomeitemConf getIncomeItemConfByCode(Map<String, Object> entityMap) throws DataAccessException {
		
		return aphiIncomeitemDataMapper.getIncomeItemConfByCode(entityMap);
		
	}
	
	@Override
	public String addBatchIncomeitemData(List<Map<String, Object>> entityMap) throws DataAccessException {
		
		int state = aphiIncomeitemDataMapper.addBatchIncomeitemData(entityMap);
		
		if (state > 0) {
			
			return "{\"msg\":\"导入成功.\",\"state\":\"true\"}";
			
		} else {
			
			return "{\"msg\":\"导入失败.\",\"state\":\"false\"}";
			
		}
	}
	
	@Override
	public String getIncomeitemTargetValue(Map<String, Object> entityMap) throws DataAccessException {
		
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
		
		return "{\"state\":\"true\"}";
	}
	
	@Override
	public String deleteIncomeitemData(Map<String, Object> entityMap) throws DataAccessException {
		
		int state = aphiIncomeitemDataMapper.deleteIncomeitemData(entityMap);
		
		if (state > 0) {
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
			
		} else {
			
			return "{\"msg\":\"没有要删除的数据.\",\"state\":\"false\"}";
			
		}
	}
	
	/**
	 * 导入程序
	 */
	@Override
	public String incomeitemDataImport(Map<String, Object> map) throws DataAccessException {
		// TODO Auto-generated method stub
		
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
			
			// 查询收入项目字典 List
			List<AphiIncomeitemSeq> incomeItemSeqList = aphiIncomeitemSeqMapper.queryIncomeitemSeq(entityMap);
			// 以键值对的形式存储,用于判断是否存在收入项目
			Map<String, AphiIncomeitemSeq> incomeItemSeqMap = new HashMap<String, AphiIncomeitemSeq>();
			// 收入项目List 放入Map 键 income_item_code 值 AphiIncomeItemSeq
			for (AphiIncomeitemSeq incomeItemSeq : incomeItemSeqList) {
				incomeItemSeqMap.put(incomeItemSeq.getIncome_item_code(), incomeItemSeq);
				incomeItemSeqMap.put(incomeItemSeq.getIncome_item_name(), incomeItemSeq);
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
					List<String> income_item_code = item.get("收入项目编码");
					List<String> order_money = item.get("开单金额");
					List<String> perform_money = item.get("执行金额");
					
					if (acct_year.get(1) == null) {
						return "{\"warn\":\"年度为空！\",\"state\":\"false\",\"row_cell\":" + acct_year.get(0) + "\"\"}";
					}
					
					if (acct_month.get(1) == null) {
						return "{\"warn\":\"月份为空！\",\"state\":\"false\",\"row_cell\":\"" + acct_month.get(0) + "\"}";
					}
					
					if (dept_name.get(1) == null) {
						return "{\"warn\":\"科室名称为空！\",\"state\":\"false\",\"row_cell\":\"\"}";
					} else {
						if (deptMap.get(dept_name.get(1)) == null && deptHipMap.get(dept_name.get(1)) == null && deptDictMap.get(dept_name.get(1)) == null) {
							return "{\"warn\":\"" + dept_name.get(1) + ",科室名称不存在！\",\"state\":\"false\",\"row_cell\":\"" + dept_name.get(0) + "\"}";
						}
					}
					
					if (income_item_code.get(1) == null) {
						return "{\"warn\":\"收入项目编码单元格为空！\",\"state\":\"false\",\"row_cell\":\"" + income_item_code.get(0) + "\"}";
					} else if (Character.isDigit(income_item_code.get(1).charAt(0))) {
						if (incomeItemSeqMap.get(income_item_code.get(1)) == null) {
							return "{\"warn\":\"收入项目编码不存在！\",\"state\":\"false\",\"row_cell\":\"" + income_item_code.get(0) + "\"}";
						}
					} else if (incomeItemSeqMap.get(income_item_code.get(1)) == null) {
						return "{\"warn\":\"收入项目名称不存在！\",\"state\":\"false\",\"row_cell\":\"" + income_item_code.get(0) + "\"}";
					}
					
					if (order_money.get(1) == null) {
						return "{\"warn\":\"开单收入为空！\",\"state\":\"false\",\"row_cell\":\" " + order_money.get(0) + "\"}";
					} else {
						try {
							Double.parseDouble((order_money.get(1)));
						} catch (NumberFormatException e) {
							return "{\"warn\":\"" + order_money.get(1) + ",开单收入输入不合法,只能输入数字！\",\"state\":\"false\",\"row_cell\":\"" + order_money.get(0)
									+ "\"}";
						}
					}
					
					if (perform_money.get(1) == null) {
						return "{\"warn\":\"执行收入为空！\",\"state\":\"false\",\"row_cell\":\" " + perform_money.get(0) + "\"}";
					} else {
						try {
							Double.parseDouble((perform_money.get(1)));
						} catch (NumberFormatException e) {
							return "{\"warn\":\"" + perform_money.get(1) + ",执行收入输入不合法,只能输入数字！\",\"state\":\"false\",\"row_cell\":\"" + perform_money.get(0)
									+ "\"}";
						}
					}
					
					// 以年度+月份+收入项目+科室名称为键值,判断导入数据是否重复
					String key = acct_year.get(1) + acct_month.get(1) + income_item_code.get(1) + dept_name.get(1);
					if (exitMap.get(key) != null) {
						err_sb.append(acct_year.get(1) + "年度," + acct_month.get(1) + "月份," + income_item_code.get(1) + "收入项目," + dept_name.get(1) + "科室名称 ");
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
					returnMap.put("income_item_code", income_item_code.get(1));
					returnMap.put("order_money", order_money.get(1));
					returnMap.put("order_ret_money", 0);// 进一步做关联查询计算出计提比例
					returnMap.put("perform_money", perform_money.get(1));
					returnMap.put("perform_ret_money", 0);
					returnMap.put("income_tot_money", 0);
					
					returnList.add(returnMap);
					break;
				}
			}
			if (err_sb.toString().length() > 0) {
				return "{\"warn\":\"以下数据【" + err_sb.toString() + "】数据重复！\",\"state\":\"false\"}";
			} else {
				aphiIncomeitemDataMapper.addBatchIncomeitemData(returnList);
				return "{\"msg\":\"导入成功.\",\"state\":\"true\"}";
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}
	}

	@Override
	public String addHisHrp(Map<String, Object> mapVo)
			throws DataAccessException {
		
		List<Map<String, Object>> deptIdList = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> allList = new ArrayList<Map<String, Object>>();
		//初始化变量
		String group_id = SessionManager.getGroupId();
		
		String hos_id = SessionManager.getHosId();
		
		String copy_code = SessionManager.getCopyCode();
		
		String year_month = mapVo.get("acct_yearm").toString();
		
		String acct_year = year_month.substring(0, 4);
		
		String acct_month = year_month.substring(4, 6);
		
		try{
			//1.从收入对应关系里取当月的对应关系如果没有提醒当月没做对应关系
			List<AphiDeptIncome> list = (List<AphiDeptIncome>) aphiDeptIncomeMapper.query(mapVo);
			
			if(list.size() >0){
				
				aphiIncomeitemDataMapper.deleteIncomeitemData(mapVo);
				
				// 查出各科室与项目的关系
				for(AphiDeptIncome deptList : list){
					
					Map<String, Object> deptMap = new HashMap<String, Object>();
					
					deptMap.put("group_id", group_id);
					
					deptMap.put("hos_id", hos_id);
					
					deptMap.put("copy_code", copy_code);
					
					deptMap.put("acct_year", acct_year);
					
					deptMap.put("acct_month", acct_month);
					
					deptMap.put("dept_id",deptList.getDept_id().toString());
					
					deptMap.put("dept_no",deptList.getDept_no().toString());
					
					deptMap.put("income_item_code",deptList.getIncome_item_code().toString());
					
					//把绩效科室转化成系统平台科室
					List<AphiDept> deptAphiList = aphiDeptMapper.queryAphiDeptRelaByMaping(deptMap);
					
					deptMap.put("dept_id",deptAphiList.get(0).getSys_dept_id());
					
					deptMap.put("dept_no",deptAphiList.get(0).getSys_dept_no());
					
					deptIdList.add(deptMap);
				}
				
				//按科室和项目 一起查询,查出数据
				List<AphiIncomeitemData> listHis = aphiIncomeitemDataMapper.queryHisHrp(deptIdList);
				
				if(listHis.size() > 0){
					//根据科室和收入项目得到金额
					for(AphiIncomeitemData moneyList : listHis){
						
						Map<String, Object> moneyMap = new HashMap<String, Object>();
						
						moneyMap.put("group_id", group_id);
						
						moneyMap.put("hos_id", hos_id);
						
						moneyMap.put("copy_code", copy_code);
						
						moneyMap.put("acct_year", acct_year);
						
						moneyMap.put("acct_month", acct_month);
						
						moneyMap.put("sys_dept_id", moneyList.getDept_id());
						
						moneyMap.put("sys_dept_no", moneyList.getDept_no());
						
						//把绩效科室转化成系统平台科室
						List<AphiDept> deptAphiList = aphiDeptMapper.queryAphiDeptRelaByMaping(moneyMap);
						
						moneyMap.put("dept_id",deptAphiList.get(0).getDept_id());
						
						moneyMap.put("dept_no",deptAphiList.get(0).getDept_no());
						
						moneyMap.put("income_item_code", moneyList.getIncome_item_code());
						
						moneyMap.put("order_money", moneyList.getOrder_money());
						
						moneyMap.put("perform_money", moneyList.getPerform_money());
						
						allList.add(moneyMap);
					}
					
					aphiIncomeitemDataMapper.addBatch(allList);
					
				}
				return "{\"state\":\"true\"}"; 
				
			} else {
				
				return "{\"warn\":\"当月没有做科室与收入项目的对应关系.\",\"state\":\"true\"}";
			}
			
		
		
		
		
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}
	}
}
