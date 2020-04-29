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

import com.chd.base.MyConfig;
import com.chd.base.SessionManager;
import com.chd.base.SysPage;
import com.chd.base.exception.SysException;
import com.chd.base.util.DateUtil;
import com.chd.base.util.JsonListBeanUtil;
import com.chd.base.util.SpreadTableJSUtil;
import com.chd.hrp.hpm.dao.AphiEmpDictMapper;
import com.chd.hrp.hpm.dao.AphiEmpDutyDataMapper;
import com.chd.hrp.hpm.dao.AphiEmpMapper;
import com.chd.hrp.hpm.entity.AphiEmp;
import com.chd.hrp.hpm.entity.AphiEmpDict;
import com.chd.hrp.hpm.entity.AphiEmpDutyData;
import com.chd.hrp.hpm.service.AphiEmpDutyDataService;

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

@Service("aphiEmpDutyDataService")
public class AphiEmpDutyDataServiceImpl implements AphiEmpDutyDataService {
	
	private static Logger logger = Logger.getLogger(AphiEmpDutyDataServiceImpl.class);
	
	@Resource(name = "aphiEmpDutyDataMapper")
	private final AphiEmpDutyDataMapper aphiEmpDutyDataMapper = null;
	
	@Resource(name = "aphiEmpMapper")
	private final AphiEmpMapper aphiEmpMapper = null;
	
	@Resource(name = "aphiEmpDictMapper")
	private final AphiEmpDictMapper aphiEmpDictMapper = null;
	
	/**
	 * 添加保存
	 */
	@Override
	public String addEmpDutyData(Map<String, Object> entityMap) throws DataAccessException {
		
		try {
			
			AphiEmpDutyData edd = aphiEmpDutyDataMapper.queryEmpDutyDataByCode(entityMap);
			
			if (edd != null) {
				
				return "{\"error\":\"添加失败 当前数据库存在此配置!\"}";
				
			}
			
			aphiEmpDutyDataMapper.addEmpDutyData(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";
			
		} catch (Exception e) {
			
			logger.error(e.getMessage(), e);
			
			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码 addItem\"}";
			
		}
	}
	
	/**
	 * 查询
	 */
	@Override
	public String queryEmpDutyData(Map<String, Object> entityMap) throws DataAccessException {
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
		
		return JsonListBeanUtil.listToJson(aphiEmpDutyDataMapper.queryEmpDutyData(entityMap, rowBounds), sysPage.getTotal());
	}
	
	/**
	 * 编码查询
	 */
	@Override
	public AphiEmpDutyData queryEmpDutyDataByCode(Map<String, Object> entityMap) throws DataAccessException {
		return aphiEmpDutyDataMapper.queryEmpDutyDataByCode(entityMap);
	}
	
	/**
	 * 删除
	 */
	@Override
	public String deleteEmpDutyData(Map<String, Object> entityMap, String codes) throws DataAccessException {
		try {
			
			if (codes != null && !codes.equals("")) {
				
				String[] code_codeArray = codes.split(",");
				
				for (String code : code_codeArray) {
					
					String[] code_array = code.split(";");
					
					entityMap.put("emp_id", code_array[0]);
					
					entityMap.put("emp_no", code_array[1]);
					
					String acct_year_month = code_array[2];
					
					entityMap.put("acct_year", acct_year_month.substring(0, 4));
					
					entityMap.put("acct_month", acct_year_month.substring(4, 6));
					
					aphiEmpDutyDataMapper.deleteEmpDutyData(entityMap);
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
	
	/**
	 * 修改保存
	 */
	@Override
	public String updateEmpDutyData(Map<String, Object> entityMap) throws DataAccessException {
		
		int state = aphiEmpDutyDataMapper.updateEmpDutyData(entityMap);
		
		if (state > 0) {
			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";
		} else {
			return "{\"msg\":\"修改失败.\",\"state\":\"false\"}";
		}
	}
	
	/**
	 * 生成保存
	 */
	@Override
	public String initEmpDutyData(Map<String, Object> entityMap) throws DataAccessException {
		
		String acct_year_month = (String) entityMap.get("acct_yearm");
		
		entityMap.put("acct_year", acct_year_month.substring(0, 4));
		
		entityMap.put("acct_month", acct_year_month.substring(4, 6));
		
		// AphiDeptBonusAudit dba =
		// aphiDeptBonusAuditMapper.queryDeptBonusAuditByCode(entityMap);
		//
		// if(dba != null){
		//
		// if (dba.getIs_audit() == 1) {
		//
		// return "{\"error\":\"本月奖金已审核,不能生成本月数据\"}";
		//
		// }
		// if (dba.getIs_grant() == 1) {
		//
		// return "{\"error\":\"本月奖金已发放,不能生成本月数据\"}";
		//
		// }
		//
		// }
		//
		String dept_id = (String) entityMap.get("dept_id");
		
		entityMap.put("dept_id", dept_id);
		
		entityMap.put("is_stop", "0");
		
		try {
			
			String rbtnl = (String) entityMap.get("rbtnl");
			
			if ("all".equals(rbtnl)) {
				
				// List<AphiEmp> empList = aphiEmpMapper.queryEmp(entityMap);
				
				List<AphiEmpDict> aphiEmpDict = aphiEmpDictMapper.queryAphiEmpDictList(entityMap);
				
				List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
				
				for (AphiEmpDict empDict : aphiEmpDict) {
					
					Map<String, Object> map = new HashMap<String, Object>();
					
					map.put("group_id", entityMap.get("group_id"));
					
					map.put("hos_id", entityMap.get("hos_id"));
					
					map.put("copy_code", entityMap.get("copy_code"));
					
					map.put("acct_year", entityMap.get("acct_year"));
					
					map.put("acct_month", entityMap.get("acct_month"));
					
					map.put("emp_id", empDict.getEmp_id());
					
					map.put("emp_no", empDict.getEmp_no());
					
					map.put("emp_duty_amount", 0.0);
					
					list.add(map);
					
				}
				
				aphiEmpDutyDataMapper.deleteEmpDutyData(entityMap);// delete
				
				List<Map<String, Object>> batchList = new ArrayList<Map<String, Object>>();
				
				if (list.size() > 0) {
					
					for (int i = 0; i < list.size(); i++) {
						
						batchList.add(list.get(i));
						
						if (i % 100 == 0) {
							
							aphiEmpDutyDataMapper.addBatchEmpDutyData(batchList);
							
							batchList.removeAll(batchList);
						} else {
							aphiEmpDutyDataMapper.addBatchEmpDutyData(batchList);
						}
						
					}
					
					return "{\"msg\":\"覆盖生成成功.\",\"state\":\"true\"}";
					
				}
				
			} else if ("check".equals(rbtnl)) {
				
				Map<Object, AphiEmpDutyData> map = new HashMap<Object, AphiEmpDutyData>();
				
				List<AphiEmpDutyData> empDutyDataList = aphiEmpDutyDataMapper.queryEmpDutyData(entityMap);
				
				for (AphiEmpDutyData edd : empDutyDataList) {
					
					map.put(edd.getEmp_id(), edd);
					
				}
				
				// List<AphiEmp> empList = aphiEmpMapper.queryEmp(entityMap);
				List<AphiEmp> empList = null;
				List<AphiEmpDict> aphiEmpDict = aphiEmpDictMapper.queryAphiEmpDictList(entityMap);
				List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
				
				for (AphiEmpDict empDict : aphiEmpDict) {
					
					if (map.get(empDict.getEmp_id()) == null) {
						
						Map<String, Object> incrementMap = new HashMap<String, Object>();
						
						incrementMap.put("group_id", entityMap.get("group_id"));
						
						incrementMap.put("hos_id", entityMap.get("hos_id"));
						
						incrementMap.put("copy_code", entityMap.get("copy_code"));
						
						incrementMap.put("acct_year", entityMap.get("acct_year"));
						
						incrementMap.put("acct_month", entityMap.get("acct_month"));
						
						incrementMap.put("emp_id", empDict.getEmp_id());
						
						incrementMap.put("emp_no", empDict.getEmp_no());
						
						incrementMap.put("emp_duty_amount", 0.0);
						
						list.add(incrementMap);
						
					}
					
				}
				
				List<Map<String, Object>> batchList = new ArrayList<Map<String, Object>>();
				
				if (list.size() > 0) {
					
					for (int i = 0; i < list.size(); i++) {
						
						batchList.add(list.get(i));
						
						if (i % 100 == 0) {
							
							aphiEmpDutyDataMapper.addBatchEmpDutyData(batchList);
							
							batchList.removeAll(batchList);
						} else {
							aphiEmpDutyDataMapper.addBatchEmpDutyData(batchList);
						}
						
					}
					
					return "{\"msg\":\"增量生成成功.\",\"state\":\"true\"}";
					
				}
				
			} else {
				
				aphiEmpDutyDataMapper.deleteEmpDutyData(entityMap);// delete
				
				List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
				
				Map<String, Object> inheritMap = new HashMap<String, Object>();
				
				inheritMap.put("group_id", entityMap.get("group_id"));
				
				inheritMap.put("hos_id", entityMap.get("hos_id"));
				
				inheritMap.put("copy_code", entityMap.get("copy_code"));
				
				String preYearMonth = DateUtil.getPreData((String) entityMap.get("acct_yearm"));
				
				inheritMap.put("acct_year", preYearMonth.substring(0, 4));
				
				inheritMap.put("acct_month", preYearMonth.substring(4, 6));
				
				List<AphiEmpDutyData> empDutyDataList = aphiEmpDutyDataMapper.queryEmpDutyData(inheritMap);
				
				Map<String, String> deptKindTargetDataMap = new HashMap<String, String>();
				
				for (AphiEmpDutyData edd : empDutyDataList) {
					
					Map<String, Object> incrementMap = new HashMap<String, Object>();
					
					inheritMap.put("group_id", entityMap.get("group_id"));
					
					inheritMap.put("hos_id", entityMap.get("hos_id"));
					
					incrementMap.put("copy_code", entityMap.get("copy_code"));
					
					incrementMap.put("acct_year", entityMap.get("acct_year"));
					
					incrementMap.put("acct_month", entityMap.get("acct_month"));
					
					incrementMap.put("emp_id", edd.getEmp_id());
					
					incrementMap.put("emp_no", edd.getEmp_no());
					
					incrementMap.put("emp_duty_amount", edd.getEmp_duty_amount());
					
					list.add(incrementMap);
				}
				
				List<Map<String, Object>> batchList = new ArrayList<Map<String, Object>>();
				
				if (list.size() > 0) {
					
					for (int i = 0; i < list.size(); i++) {
						
						batchList.add(list.get(i));
						
						if (i % 100 == 0) {
							
							aphiEmpDutyDataMapper.addBatchEmpDutyData(batchList);
							
							batchList.removeAll(batchList);
						} else {
							aphiEmpDutyDataMapper.addBatchEmpDutyData(batchList);
						}
						
					}
					
					return "{\"msg\":\"继承数据成功.\",\"state\":\"true\"}";
					
				}
				
			}
			
			return "{\"msg\":\"生成成功.\",\"state\":\"true\"}";
			
		} catch (Exception e) {
			
			logger.error(e.getMessage(), e);
			
			throw new SysException("{\"error\":\"生成失败 数据库异常 请联系管理员! 错误编码 initEmpDutyData\"}");
		}
	}
	
	/**
	 * 导入
	 */
	@Override
	public String importHpmEmpDutyData(Map<String, Object> map) throws DataAccessException {
		// TODO Auto-generated method stub
		try {
			
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
			entityMap.put("is_stop", "0");// 查询未停用
			
			// 查询职工变更字典表
			List<AphiEmpDict> aphiEmpDictList = aphiEmpDictMapper.queryAphiEmpDictList(entityMap);
			
			List<AphiEmpDutyData> aphiEmpDutyDataList = aphiEmpDutyDataMapper.queryEmpDutyData(entityMap);
			
			// 以键值对的形式存储,用于判断职工是否存在
			Map<String, AphiEmpDict> aphiEmpDictMap = new HashMap<String, AphiEmpDict>();
			
			Map<String, AphiEmpDutyData> aphiEmpDutyDataMap = new HashMap<String, AphiEmpDutyData>();
			
			// 用于存储传的数据值,判断数据是否重复
			Map<String, String> exitMap = new HashMap<String, String>();
			
			// 存储要添加保存的数据List
			List<Map<String, Object>> returnList = new ArrayList<Map<String, Object>>();
			
			// 用于记录重复数据
			StringBuffer err_sb = new StringBuffer();
			
			// 职工存到map中 编码 姓名为key 值为aphiEmpDict
			for (AphiEmpDict aphiEmpDict : aphiEmpDictList) {
				aphiEmpDictMap.put(aphiEmpDict.getEmp_code(), aphiEmpDict);
				aphiEmpDictMap.put(aphiEmpDict.getEmp_name(), aphiEmpDict);
			}
			
			// 遍历导入数据-遍历单一导入数据,得到名称-判断是否为空-跳出循环
			for (Map<String, List<String>> item : liData) {
				for (String st : item.keySet()) {
					if (item.get(st).get(1) == null) {
						break;
					}
					
					List<String> acct_year = item.get("核算年度");
					List<String> acct_month = item.get("核算月份");
					List<String> emp_code = item.get("职工编码");
					List<String> emp_duty_amount = item.get("岗位系数");
					
					if (acct_year.get(1) == null) {
						return "{\"warn\":\"年度为空！\",\"state\":\"false\",\"row_cell\":" + acct_year.get(0) + "\"\"}";
					}
					
					if (acct_month.get(1) == null) {
						return "{\"warn\":\"月份为空！\",\"state\":\"false\",\"row_cell\":\"" + acct_month.get(0) + "\"}";
					}
					
					if (emp_code.get(1) == null) {
						return "{\"warn\":\"职工编码单元格为空！\",\"state\":\"false\",\"row_cell\":\"\"}";
					} else if (Character.isDigit(emp_code.get(1).charAt(0))) {
						if (aphiEmpDictMap.get(emp_code.get(1)) == null) {
							return "{\"warn\":\"" + emp_code.get(1) + ",职工编码不存在！\",\"state\":\"false\",\"row_cell\":\"" + emp_code.get(0) + "\"}";
						}
					} else if (aphiEmpDictMap.get(emp_code.get(1)) == null) {
						return "{\"warn\":\"" + emp_code.get(1) + ",职工名称不存在！\",\"state\":\"false\",\"row_cell\":\"" + emp_code.get(0) + "\"}";
					}
					
					for (AphiEmpDutyData aphiEmpDutyData : aphiEmpDutyDataList) {
						if (Character.isDigit(emp_code.get(1).charAt(0))) {
							if (aphiEmpDictMap.get(emp_code.get(1)).getEmp_id() == aphiEmpDutyData.getEmp_id()) {
								return "{\"warn\":\"" + emp_code.get(1) + ",职工编码已经存在！\",\"state\":\"false\",\"row_cell\":\"" + emp_code.get(0) + "\"}";
							}
						} else if (aphiEmpDutyData.getEmp_name().equals(emp_code.get(1))) {
							return "{\"warn\":\"" + emp_code.get(1) + ",职工名称已经存在！\",\"state\":\"false\",\"row_cell\":\"" + emp_code.get(0) + "\"}";
						}
						
					}
					
					if (emp_duty_amount.get(1) == null) {
						return "{\"warn\":\"岗位系数为空！\",\"state\":\"false\",\"row_cell\":\" " + emp_duty_amount.get(0) + "\"}";
					} else {
						try {
							Double.parseDouble((emp_duty_amount.get(1)));
						} catch (NumberFormatException e) {
							return "{\"warn\":\"" + emp_duty_amount.get(1) + ",岗位系数输入不合法,只能输入数字！\",\"state\":\"false\",\"row_cell\":\""
									+ emp_duty_amount.get(0) + "\"}";
						}
					}
					
					// 以年度+月份+指标编码+科室名称为键值,判断导入数据是否重复
					String key = acct_year.get(1) + acct_month.get(1) + emp_duty_amount.get(1) + emp_code.get(1);
					if (exitMap.get(key) != null) {
						err_sb.append(acct_year.get(1) + "年度," + acct_month.get(1) + "月份," + emp_duty_amount.get(1) + "岗位系数," + emp_code.get(1) + "职工编码");
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
					// returnMap.put("emp_code", emp_code.get(1));
					returnMap.put("emp_id", aphiEmpDictMap.get(emp_code.get(1)).getEmp_id());
					returnMap.put("emp_no", aphiEmpDictMap.get(emp_code.get(1)).getEmp_no());
					returnMap.put("emp_duty_amount", emp_duty_amount.get(1));
					
					returnList.add(returnMap);
					break;
				}
			}
			
			if (err_sb.toString().length() > 0) {
				return "{\"warn\":\"以下数据【" + err_sb.toString() + "】数据重复！\",\"state\":\"false\"}";
			} else {
				// 优化批量添加
				List<Map<String, Object>> batchList = new ArrayList<Map<String, Object>>();
				
				for (int i = 0; i < returnList.size(); i++) {
					
					batchList.add(returnList.get(i));
					
					if (i % 100 == 0) {
						
						aphiEmpDutyDataMapper.addBatchEmpDutyData(batchList);
						
						batchList.removeAll(batchList);
					} else {
						aphiEmpDutyDataMapper.addBatchEmpDutyData(batchList);
					}
				}
				
				return "{\"msg\":\"导入成功.\",\"state\":\"true\"}";
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e.getMessage(), e);
			throw new SysException("{\"msg\":\"导入操作失败.\",\"state\":\"false\"}");
		}
	}
	
	@Override
	public List<Map<String, Object>> queryEmpDutyDataPrint(Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		
		if (MyConfig.getSysPara("09001")== null) {
			entityMap.put("para_value", 0);
		} else {
			entityMap.put("para_value", MyConfig.getSysPara("09001"));
		}
		
		if (entityMap.get("acct_yearm") != null && !"".equals(entityMap.get("acct_yearm"))) {
			
			String acct_year = entityMap.get("acct_yearm").toString().substring(0, 4);
			String acct_month = entityMap.get("acct_yearm").toString().substring(4);
			
			entityMap.put("acct_year", acct_year);
			entityMap.put("acct_month", acct_month);
			
		}
		
		List<Map<String, Object>> list = aphiEmpDutyDataMapper.queryEmpDutyDataPrint(entityMap);
		
		return list;
	}
	
}
