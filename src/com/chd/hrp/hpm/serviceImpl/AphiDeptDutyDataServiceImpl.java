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

import com.chd.base.SessionManager;
import com.chd.base.SysPage;
import com.chd.base.exception.SysException;
import com.chd.base.util.DateUtil;
import com.chd.base.util.JsonListBeanUtil;
import com.chd.base.util.SpreadTableJSUtil;
import com.chd.hrp.hpm.dao.AphiDeptDictMapper;
import com.chd.hrp.hpm.dao.AphiDeptDutyDataMapper;
import com.chd.hrp.hpm.dao.AphiDeptTargetDataMapper;
import com.chd.hrp.hpm.entity.AphiDeptDict;
import com.chd.hrp.hpm.entity.AphiDeptDutyData;
import com.chd.hrp.hpm.entity.AphiDeptTargetData;
import com.chd.hrp.hpm.service.AphiDeptDutyDataService;

/**
 * 科室岗位系数数据准备
 * 
 * @author Administrator
 *
 */
@Service("aphiDeptDutyDataService")
public class AphiDeptDutyDataServiceImpl implements AphiDeptDutyDataService {

	private static Logger logger = Logger.getLogger(AphiDeptDictServiceImpl.class);

	@Resource(name = "aphiDeptDutyDataMapper")
	private final AphiDeptDutyDataMapper aphiDeptDutyDataMapper = null;

	@Resource(name = "aphiDeptDictMapper")
	private final AphiDeptDictMapper aphiDeptDictMapper = null;

	@Resource(name = "aphiDeptTargetDataMapper")
	private final AphiDeptTargetDataMapper aphiDeptTargetDataMapper = null;

	/**
	 * 查询
	 */
	@Override
	public String queryHpmDeptDutyData(Map<String, Object> entityMap) throws DataAccessException {
		if (entityMap.get("user_id") == null) {
			entityMap.put("user_id", SessionManager.getUserId());
		}
		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

		List<AphiDeptDutyData> list = aphiDeptDutyDataMapper.queryDeptDutyData(entityMap, rowBounds);

		return JsonListBeanUtil.listToJson(list, sysPage.getTotal());
	}

	/**
	 * 添加
	 */
	@Override
	public String addHpmDeptDutyData(Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		int state = aphiDeptDutyDataMapper.addDeptDutyData(entityMap);

		if (state > 0) {

			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		} else {

			return "{\"msg\":\"添加失败.\",\"state\":\"false\"}";

		}

	}

	/**
	 * 编码
	 */
	@Override
	public AphiDeptDutyData queryDeptDutyDataByCode(Map<String, Object> entityMap) throws DataAccessException {
		if (entityMap.get("user_id") == null) {
			entityMap.put("user_id", SessionManager.getUserId());
		}
		return aphiDeptDutyDataMapper.queryDeptDutyDataByCode(entityMap);
	}

	/**
	 * 修改
	 */
	@Override
	public String updateHpmDeptDutyData(Map<String, Object> entityMap) throws DataAccessException {

		// AphiDeptBonusAudit deptBonusAudit =
		// aphiDeptBonusAuditMapper.queryDeptBonusAuditByCode(entityMap);
		//
		// if(deptBonusAudit !=null){
		//
		// if( deptBonusAudit.getIs_audit()== 1){
		//
		// return "{\"msg\":\"本月奖金已审核后。数据不能操作.\",\"state\":\"true\"}";
		//
		// }
		// }

		try {
			if (entityMap.get("user_id") == null) {
				entityMap.put("user_id", SessionManager.getUserId());
			}
			AphiDeptDutyData aphiDeptDutyData = aphiDeptDutyDataMapper.queryDeptDutyDataByCode(entityMap);

			if (aphiDeptDutyData != null) {

				int state = aphiDeptDutyDataMapper.updateDeptDutyData(entityMap);

				if (state > 0) {

					return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";

				} else {

					return "{\"msg\":\"修改失败.\",\"state\":\"false\"}";

				}
			} else {

				return "{\"msg\":\"修改失败.\",\"state\":\"false\"}";
			}

		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e.getMessage(), e);

			throw new SysException("{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updateDeptDutyData\"}");

		}

	}

	/**
	 * 删除
	 */
	@Override
	public String deleteHpmDeptDutyData(Map<String, Object> entityMap, String codes) throws DataAccessException {
		// TODO Auto-generated method stub
		try {
			if (codes != null && !codes.equals("")) {
				String[] codes_array = codes.split(",");

				for (String code : codes_array) {

					String[] code_array = code.split(";");

					entityMap.put("dept_id", code_array[0]);

					entityMap.put("dept_no", code_array[1]);

					entityMap.put("acct_year", code_array[2].substring(0, 4));

					entityMap.put("acct_month", code_array[2].substring(4));

					aphiDeptDutyDataMapper.deleteDeptDutyData(entityMap);

				}

				return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

			} else {
				return "{\"msg\":\"没有要删除的数据.\",\"state\":\"false\"}";
			}

		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e.getMessage(), e);

			throw new SysException("{\"error\":\"操作失败 数据库异常 请联系管理员! 错误编码  deleteDeptDutyData\"}");
		}

	}

	/**
	 * 生成
	 */
	@Override
	public String initHpmDeptDutyData(Map<String, Object> entityMap) throws DataAccessException {

		// AphiDeptBonusAudit deptBonusAudit =
		// aphiDeptBonusAuditMapper.queryDeptBonusAuditByCode(entityMap);
		//
		// if(deptBonusAudit !=null){
		//
		// if( deptBonusAudit.getIs_audit()== 1){
		//
		// return "{\"msg\":\"本月奖金已审核后。数据不能生成.\",\"state\":\"true\"}";
		//
		// }
		// if(deptBonusAudit.getIs_grant()== 1){
		//
		// return "{\"error\":\"本月奖金已发放,不能生成本月数据\"}";
		//
		// }
		// }
		if (entityMap.get("user_id") == null) {
			entityMap.put("user_id", SessionManager.getUserId());
		}
		// 查询指标
		List<AphiDeptTargetData> auditlist = aphiDeptTargetDataMapper.getDeptTargetValue(entityMap);

		if (auditlist.size() > 0) {

			return "{\"error\":\"该月指标已审核，不能进行生成\"}";

		}

		String rbtnl = (String) entityMap.get("rbtnl");

		if (rbtnl.equals("all")) {
			try {

				List<AphiDeptDict> aphiDeptDictList = aphiDeptDictMapper.queryPrmDeptDict(entityMap);

				List<AphiDeptDutyData> aphiDeptDutyDataList = aphiDeptDutyDataMapper.queryDeptDutyData(entityMap);

				List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

				for (int i = 0; i < aphiDeptDictList.size(); i++) {
					AphiDeptDict aphiDeptDict = aphiDeptDictList.get(i);
					Map<String, Object> allMap = new HashMap<String, Object>();
					allMap.put("group_id", entityMap.get("group_id"));
					allMap.put("hos_id", entityMap.get("hos_id"));
					allMap.put("copy_code", entityMap.get("copy_code"));
					allMap.put("dept_id", aphiDeptDict.getDept_id());
					allMap.put("dept_no", aphiDeptDict.getDept_no());
					allMap.put("acct_year", entityMap.get("acct_year"));
					allMap.put("acct_month", entityMap.get("acct_month"));
					allMap.put("dept_duty_amount", 0.0);

					list.add(allMap);
				}

				// 清空
				aphiDeptDutyDataMapper.deleteDeptDutyData(entityMap);

				List<Map<String, Object>> batchList = new ArrayList<Map<String, Object>>();

				if (list.size() > 0) {

					for (int i = 0; i < list.size(); i++) {

						batchList.add(list.get(i));

						if (i % 100 == 0) {

							aphiDeptDutyDataMapper.addBatchDeptDutyData(batchList);

							batchList.removeAll(batchList);
						}

					}

					aphiDeptDutyDataMapper.addBatchDeptDutyData(batchList);

					return "{\"msg\":\"生成成功.\",\"state\":\"true\"}";

				} else {
					return "{\"msg\":\"没有生成数据.\",\"state\":\"false\"}";
				}

			} catch (Exception e) {
				// TODO: handle exception
				logger.error(e.getMessage(), e);

				throw new SysException("{\"error\":\"生成失败 数据库异常 请联系管理员! 错误编码  initHpmDeptDutyData\"}");

			}

		} else if (rbtnl.equals("check")) {

			try {

				Map<Object, AphiDeptDutyData> map = new HashMap<Object, AphiDeptDutyData>();

				List<AphiDeptDict> aphiDeptDictList = aphiDeptDictMapper.queryPrmDeptDict(entityMap);

				List<AphiDeptDutyData> aphiDeptDutyDataList = aphiDeptDutyDataMapper.queryDeptDutyData(entityMap);

				for (AphiDeptDutyData aphiDeptDutyData : aphiDeptDutyDataList) {
					map.put(aphiDeptDutyData.getDept_id(), aphiDeptDutyData);
				}

				List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

				for (AphiDeptDict aphiDeptDict : aphiDeptDictList) {
					if (map.get(aphiDeptDict.getDept_id()) == null) {
						Map<String, Object> checkMap = new HashMap<String, Object>();
						checkMap.put("group_id", entityMap.get("group_id"));
						checkMap.put("hos_id", entityMap.get("hos_id"));
						checkMap.put("copy_code", entityMap.get("copy_code"));
						checkMap.put("acct_year", entityMap.get("acct_year"));
						checkMap.put("acct_month", entityMap.get("acct_month"));
						checkMap.put("dept_id", aphiDeptDict.getDept_id());
						checkMap.put("dept_no", aphiDeptDict.getDept_no());
						checkMap.put("dept_duty_amount", 0.0);

						list.add(checkMap);
					}
				}

				List<Map<String, Object>> batchList = new ArrayList<Map<String, Object>>();

				if (list.size() > 0) {

					for (int i = 0; i < list.size(); i++) {

						batchList.add(list.get(i));

						if (i % 100 == 0) {

							aphiDeptDutyDataMapper.addBatchDeptDutyData(batchList);

							batchList.removeAll(batchList);
						}

					}

					aphiDeptDutyDataMapper.addBatchDeptDutyData(batchList);

					return "{\"msg\":\"生成成功.\",\"state\":\"true\"}";

				} else {
					return "{\"msg\":\"没有生成数据.\",\"state\":\"false\"}";
				}

			} catch (Exception e) {
				// TODO: handle exception

				logger.error(e.getMessage(), e);

				throw new SysException("{\"error\":\"生成失败 数据库异常 请联系管理员! 错误编码  initHpmDeptDutyData\"}");
			}

		} else {

			try {

				// 先清空
				aphiDeptDutyDataMapper.deleteDeptDutyData(entityMap);

				Map<String, Object> map = new HashMap<String, Object>();

				map.put("group_id", entityMap.get("group_id"));
				map.put("hos_id", entityMap.get("hos_id"));
				map.put("copy_code", entityMap.get("copy_code"));
				String preYearMonth = DateUtil.getPreData((String) entityMap.get("acct_yearm"));
				map.put("acct_year", preYearMonth.substring(0, 4));
				map.put("acct_month", preYearMonth.substring(4, 6));
				map.put("user_id", SessionManager.getUserId());

				List<AphiDeptDict> aphiDeptDictList = aphiDeptDictMapper.queryPrmDeptDict(entityMap);

				List<AphiDeptDutyData> aphiDeptDutyDataList = aphiDeptDutyDataMapper.queryDeptDutyData(map);

				List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

				for (AphiDeptDutyData aphiDeptDutyData : aphiDeptDutyDataList) {

					Map<String, Object> incrementMap = new HashMap<String, Object>();

					incrementMap.put("group_id", entityMap.get("group_id"));

					incrementMap.put("hos_id", entityMap.get("hos_id"));

					incrementMap.put("copy_code", entityMap.get("copy_code"));

					incrementMap.put("acct_year", entityMap.get("acct_year"));

					incrementMap.put("acct_month", entityMap.get("acct_month"));

					incrementMap.put("dept_duty_amount", aphiDeptDutyData.getDept_duty_amount());

					incrementMap.put("dept_id", aphiDeptDutyData.getDept_id());

					incrementMap.put("dept_no", aphiDeptDutyData.getDept_no());

					list.add(incrementMap);

				}

				List<Map<String, Object>> batchList = new ArrayList<Map<String, Object>>();

				if (list.size() > 0) {

					for (int i = 0; i < list.size(); i++) {

						batchList.add(list.get(i));

						if (i % 100 == 0) {

							aphiDeptDutyDataMapper.addBatchDeptDutyData(batchList);

							batchList.removeAll(batchList);
						}

					}

					aphiDeptDutyDataMapper.addBatchDeptDutyData(batchList);

					return "{\"msg\":\"生成成功.\",\"state\":\"true\"}";

				} else {
					return "{\"msg\":\"没有生成数据.\",\"state\":\"false\"}";
				}

			} catch (Exception e) {
				// TODO: handle exception

				logger.error(e.getMessage(), e);

				throw new SysException("{\"msg\":\"生成失败.\",\"state\":\"false\"}");
			}

		}

	}

	/**
	 * 计算
	 */
	@Override
	public String collectHpmDeptDutyData(Map<String, Object> entityMap) throws DataAccessException {
		if (entityMap.get("user_id") == null) {
			entityMap.put("user_id", SessionManager.getUserId());
		}
		List<AphiDeptDict> aphiDeptDict = aphiDeptDictMapper.queryPrmDeptDict(entityMap);

		List<AphiDeptDutyData> aphiDeptDutyDataList = aphiDeptDutyDataMapper.getDeptEmpDutyAmount(entityMap);// 计算dept_duty_amount总数

		if (aphiDeptDutyDataList != null) {
			for (AphiDeptDutyData aphiDeptDutyData : aphiDeptDutyDataList) {

				Map<String, Object> map = new HashMap<String, Object>();
				map.put("group_id", entityMap.get("group_id"));
				map.put("hos_id", entityMap.get("hos_id"));
				map.put("copy_code", entityMap.get("copy_code"));
				map.put("acct_year", entityMap.get("acct_year"));
				map.put("acct_month", entityMap.get("acct_month"));
				map.put("dept_id", aphiDeptDutyData.getDept_id());
				map.put("dept_no", aphiDeptDutyData.getDept_no());
				map.put("dept_duty_amount", aphiDeptDutyData.getDept_duty_amount());

				aphiDeptDutyDataMapper.updateDeptDutyData(map);
			}
			return "{\"msg\":\"计算成功.\",\"state\":\"true\"}";
		} else {
			return "{\"msg\":\"没有数据.\",\"state\":\"false\"}";
		}

	}

	/**
	 * 导入
	 */
	@Override
	public String hpmDeptDutyDataImport(Map<String, Object> map) throws DataAccessException {
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
			entityMap.put("user_id", SessionManager.getUserId());
			entityMap.put("copy_code", SessionManager.getCopyCode());
			entityMap.put("is_stop", "0");// 查询未停用

			// 查询科室字典 List
			List<AphiDeptDict> deptList = aphiDeptDictMapper.queryPrmDeptDict(entityMap);

			List<AphiDeptDutyData> aphiDeptDutyDataList = aphiDeptDutyDataMapper.queryDeptDutyData(entityMap);

			// 以键值对的形式存储,用于判断科室是否存在
			Map<String, AphiDeptDict> deptMap = new HashMap<String, AphiDeptDict>();

			// 用于存储传的数据值,判断数据是否重复
			Map<String, String> exitMap = new HashMap<String, String>();

			// 存储要添加保存的数据List
			List<Map<String, Object>> returnList = new ArrayList<Map<String, Object>>();

			// 用于记录重复数据
			StringBuffer err_sb = new StringBuffer();

			// 将科室List存入Map 键:dept_name 值:AphiDeptDict
			for (AphiDeptDict dept : deptList) {
				deptMap.put(dept.getDept_name(), dept);
				deptMap.put(dept.getDept_code(), dept);
			}

			// 遍历导入数据-遍历单一导入数据,得到名称-判断是否为空-跳出循环
			for (Map<String, List<String>> item : liData) {
				for (String st : item.keySet()) {
					if (item.get(st).get(1) == null) {
						break;
					}

					List<String> acct_year = item.get("核算年度");
					List<String> acct_month = item.get("核算月份");
					List<String> dept_name = item.get("科室名称");
					List<String> dept_duty_amount = item.get("岗位系数");

					if (acct_year.get(1) == null) {
						return "{\"warn\":\"年度为空！\",\"state\":\"false\",\"row_cell\":" + acct_year.get(0) + "\"\"}";
					}

					if (acct_month.get(1) == null) {
						return "{\"warn\":\"月份为空！\",\"state\":\"false\",\"row_cell\":\"" + acct_month.get(0) + "\"}";
					}

					if (dept_name.get(1) == null) {
						return "{\"warn\":\"科室名称单元格为空！\",\"state\":\"false\",\"row_cell\":\"\"}";
					} else if (Character.isDigit(dept_name.get(1).charAt(0))) {
						if (deptMap.get(dept_name.get(1)) == null) {
							return "{\"warn\":\"" + dept_name.get(1) + ",科室编码不存在！\",\"state\":\"false\",\"row_cell\":\""
									+ dept_name.get(0) + "\"}";
						}
					} else if (deptMap.get(dept_name.get(1)) == null) {
						return "{\"warn\":\"" + dept_name.get(1) + ",科室名称不存在！\",\"state\":\"false\",\"row_cell\":\""
								+ dept_name.get(0) + "\"}";
					}

					if (dept_duty_amount.get(1) == null) {
						return "{\"warn\":\"岗位系数为空！\",\"state\":\"false\",\"row_cell\":\" " + dept_duty_amount.get(0)
								+ "\"}";
					} else {
						try {
							Double.parseDouble((dept_duty_amount.get(1)));
						} catch (NumberFormatException e) {
							return "{\"warn\":\"" + dept_duty_amount.get(1)
									+ ",岗位系数输入不合法,只能输入数字！\",\"state\":\"false\",\"row_cell\":\""
									+ dept_duty_amount.get(0) + "\"}";
						}
					}

					for (AphiDeptDutyData aphiDeptDutyData : aphiDeptDutyDataList) {
						if (Character.isDigit(dept_name.get(1).charAt(0))) {
							// String dept = ;
							if ((acct_year.get(1) + acct_month.get(1) + deptMap.get(dept_name.get(1)).getDept_id())
									.equals(aphiDeptDutyData.getAcct_year() + aphiDeptDutyData.getAcct_month()
											+ aphiDeptDutyData.getDept_id())) {
								return "{\"warn\":\"" + dept_name.get(1)
										+ ",科室编码已经存在！\",\"state\":\"false\",\"row_cell\":\"" + dept_name.get(0) + "\"}";
							}
						} else if ((acct_year.get(1) + acct_month.get(1) + dept_name.get(1))
								.equals(aphiDeptDutyData.getAcct_year() + aphiDeptDutyData.getAcct_month()
										+ aphiDeptDutyData.getDept_name())) {
							return "{\"warn\":\"" + dept_name.get(1)
									+ ",科室名称已经存在！\",\"state\":\"false\",\"row_cell\":\"" + dept_name.get(0) + "\"}";
						}

					}

					// 以年度+月份+指标编码+科室名称为键值,判断导入数据是否重复
					String key = acct_year.get(1) + acct_month.get(1) + dept_duty_amount.get(1) + dept_name.get(1);
					if (exitMap.get(key) != null) {
						err_sb.append(acct_year.get(1) + "年度," + acct_month.get(1) + "月份," + dept_duty_amount.get(1)
								+ "岗位系数," + dept_name.get(1) + "科室名称 ");
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
					returnMap.put("dept_id", deptMap.get(dept_name.get(1)).getDept_id());
					returnMap.put("dept_no", deptMap.get(dept_name.get(1)).getDept_no());
					returnMap.put("dept_duty_amount", dept_duty_amount.get(1));

					returnList.add(returnMap);
					break;

				}
			}

			if (err_sb.toString().length() > 0) {
				return "{\"warn\":\"以下数据【" + err_sb.toString() + "】数据重复！\",\"state\":\"false\"}";
			} else {

				aphiDeptDutyDataMapper.addBatchDeptDutyData(returnList);

				return "{\"msg\":\"导入成功.\",\"state\":\"true\"}";
			}

		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}
	}

	@Override
	public List<Map<String, Object>> queryHpmDeptDutyDataPrint(Map<String, Object> entityMap)
			throws DataAccessException {
		if (entityMap.get("user_id") == null) {
			entityMap.put("user_id", SessionManager.getUserId());
		}
		if (entityMap.get("acct_yearm") != null && !"".equals(entityMap.get("acct_yearm"))) {

			String acct_year = entityMap.get("acct_yearm").toString().substring(0, 4);
			String acct_month = entityMap.get("acct_yearm").toString().substring(4);

			entityMap.put("acct_year", acct_year);
			entityMap.put("acct_month", acct_month);
		}

		List<Map<String, Object>> list = aphiDeptDutyDataMapper.queryDeptDutyDataPrint(entityMap);

		return list;
	}

}
