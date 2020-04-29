/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */

package com.chd.hrp.hr.serviceImpl.medicalmanagement;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.chd.base.SessionManager;
import com.chd.base.SysPage;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.base.util.DateUtil;
import com.chd.base.util.ReadFiles;
import com.chd.hrp.hr.dao.base.HrSelectMapper;
import com.chd.hrp.hr.dao.medicalmanagement.HrDrugPermDetailMapper;
import com.chd.hrp.hr.dao.medicalmanagement.HrDrugPermMapper;
import com.chd.hrp.hr.entity.medicalmanagement.HrDrugPerm;
import com.chd.hrp.hr.service.medicalmanagement.HrDrugPermService;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Description:
 * 
 * @Table: HR_DRUG_PERM 药品权限管理
 * @Author: bell
 * @email: bell@e-tonggroup.com
 * @Version: 1.0
 */

@Service("hrDrugPermService")
public class HrDrugPermServiceImpl implements HrDrugPermService {

	private static Logger logger = Logger.getLogger(HrDrugPermServiceImpl.class);
	// 引入DAO操作
	@Resource(name = "hrDrugPermMapper")
	private final HrDrugPermMapper hrDrugPermMapper = null;

	// 引入DAO操作
	@Resource(name = "hrDrugPermDetailMapper")
	private final HrDrugPermDetailMapper hrDrugPermDetailMapper = null;

	@Resource(name = "hrSelectMapper")
	private final HrSelectMapper hrSelectMapper = null;

	/**
	 * 添加权限
	 */
	@Override
	public String addHrDrugPerm(Map<String, Object> entityMap) throws DataAccessException {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			/*	*//**
					 * 先删除权限
					 *//*
						 * hrDrugPermMapper.deletehrDrugPerm(entityMap);
						 */
			List<HrDrugPerm> alllistVo = JSONArray.parseArray(String.valueOf(entityMap.get("paramVo")),
					HrDrugPerm.class);

			/**
			 * 增加
			 */
			if (alllistVo != null && alllistVo.size() > 0) {

				// for (HrDrugPerm hrDrugPerm : alllistVo) {
				//
				// hrDrugPerm.setGroup_id(Double.parseDouble((entityMap.get("group_id").toString())));
				// hrDrugPerm.setHos_id(Double.parseDouble(entityMap.get("hos_id").toString()));
				//
				// hrDrugPerm.setState(0);
				// /*HrDrugPerm DrugPerm= hrDrugPermMapper.queryByCode(hrDrugPerm);
				// if (DrugPerm==null) {
				// int state = hrDrugPermMapper.add(hrDrugPerm);
				// successNum++;
				// }else{
				// failureMsg.append("<br/> 已存在相同的记录; ");
				// failureNum++;
				// }
				// if (failureNum>0){
				// failureMsg.insert(0, "，失败 "+failureNum+" 条，信息如下：");
				// }
				// } */
				// }
				for (HrDrugPerm hrDrugPerm : alllistVo) {
					if (hrDrugPerm.getState() == null) {
						hrDrugPerm.setState(0);
					}
				}

				hrDrugPermMapper.deleteBatchDrugPerm(alllistVo, entityMap);
				hrDrugPermMapper.addBatch(alllistVo, entityMap);
				// hrDrugPermDetailMapper.updateBatch(alllistVo,entityMap);
			}

			return "{\"msg\":\"保存成功.\",\"state\":\"true\"}";
		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}

	}

	/**
	 * 删除权限
	 */
	@Override
	public String deletehrDrugPerm(List<HrDrugPerm> entityList) throws DataAccessException {

		try {

			if (entityList != null && entityList.size() > 0) {

				Map<String, Object> map = new HashMap<String, Object>();
				map.put("group_id", SessionManager.getGroupId());
				map.put("hos_id", SessionManager.getHosId());
				map.put("state", 1);
				int count = hrDrugPermMapper.selectDrugPermByState(entityList, map);
				if (count > 0) {
					return "{\"error\":\"已提交的数据不能删除.\",\"state\":\"false\"}";
				}

				hrDrugPermMapper.deleteBatchDrugPerm(entityList, map);
			}

			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}
	}

	/**
	 * 查询权限
	 */
	@Override
	public String queryHrDrugPerm(Map<String, Object> entityMap) throws DataAccessException {

		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		if (sysPage.getTotal() == -1) {

			List<HrDrugPerm> list = (List<HrDrugPerm>) hrDrugPermMapper.query(entityMap);

			return ChdJson.toJson(list);

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

			List<HrDrugPerm> list = (List<HrDrugPerm>) hrDrugPermMapper.query(entityMap, rowBounds);

			@SuppressWarnings("rawtypes")
			PageInfo page = new PageInfo(list);

			return ChdJson.toJson(list, page.getTotal());

		}

	}

	@Override
	public HrDrugPerm queryByCode(HrDrugPerm hrDrugPerm) throws DataAccessException {
		return hrDrugPermMapper.queryByCode(hrDrugPerm);
	}

	@Override
	public String confirmDrugPerm(List<HrDrugPerm> listVo ) throws DataAccessException {
		try {
			List<HrDrugPerm> finalList = listVo;
			for (HrDrugPerm hrDrugPerm : listVo) {
				hrDrugPerm.setGroup_id(Double.parseDouble(SessionManager.getGroupId()));
				hrDrugPerm.setHos_id(Double.parseDouble(SessionManager.getHosId()));
				SimpleDateFormat a = new SimpleDateFormat("yyyy-MM-dd");
				hrDrugPerm.setApply_date(new Date());
				HrDrugPerm hDrugPerm = hrDrugPermMapper.queryByCode(hrDrugPerm);

				if (hDrugPerm != null) {
					if (hDrugPerm.getState() == null || hDrugPerm.getState() == 0) {
						hrDrugPerm.setState(1);
					} else {
						finalList.remove(hrDrugPerm);
					}
				}
			}
			
			hrDrugPermMapper.updateBatch(finalList);
			hrDrugPermDetailMapper.updateBatch(finalList);
			return "{\"msg\":\"提交成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}
	}

	@Override
	public String reConfirmDrugPerm(List<HrDrugPerm> listVo) throws DataAccessException {
		try {
			for (HrDrugPerm hrDrugPerm : listVo) {
				hrDrugPerm.setState(0);
				hrDrugPerm.setApply_date(null);
				hrDrugPerm.setGroup_id(Double.parseDouble(SessionManager.getGroupId()));
				hrDrugPerm.setHos_id(Double.parseDouble(SessionManager.getHosId()));
			}
			
			hrDrugPermMapper.updateBatch(listVo);
			hrDrugPermDetailMapper.updateBatch(listVo);
			return "{\"msg\":\"撤回成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}
	}

	@Override
	public String importDrugPerm(Map<String, Object> entityMap) throws DataAccessException {
		int successNum = 0;
		int failureNum = 0;
		StringBuilder failureMsg = new StringBuilder();
		List<Map<String, Object>> saveList = new ArrayList<Map<String, Object>>();
		Map<String, Object> whetherMap = new HashMap<String, Object>();
		whetherMap.put("提交", "1");
		whetherMap.put("新建", "0");
		whetherMap.put("1", "1");
		whetherMap.put("0", "0");
		boolean dateFlag=true;

		// 查询权限类别
		Map<String, Object> permMap = new HashMap<String, Object>();
		permMap.put("group_id", SessionManager.getGroupId());
		permMap.put("hos_id", SessionManager.getHosId());
		List<Map<String, Object>> select = hrSelectMapper.queryPermTypeSelect(permMap);
		Map<String, String> permTypeMap = new HashMap<String, String>();
		for (Map<String, Object> map1 : select) {
			Set<Entry<String, Object>> entrySet = map1.entrySet();
			String key = "";
			String value = "";
			for (Entry<String, Object> entry : entrySet) {
				if (entry.getKey().equals("id")) {
					key = (String) entry.getValue();
				}
				if (entry.getKey().equals("text")) {
					value = (String) entry.getValue();
				}
			}
			permTypeMap.put(value, key);
		}

		try {
			List<Map<String, List<String>>> list = ReadFiles.readFiles(entityMap);
			if (list != null && list.size() > 0) {
				for (Map<String, List<String>> map : list) {
					// 过滤空行
					if (map.get("emp_id").get(1) == null || "".equals(map.get("emp_id").get(1))
							|| "null".equals(map.get("emp_id").get(1))) {
						continue;
					}
					Map<String, Object> saveMap = new HashMap<String, Object>();
					saveMap.put("emp_code", map.get("emp_id").get(1));
					saveMap.putAll(permMap);
					Map<String, Object> empMap = hrSelectMapper.queryEmpById(saveMap);
					if (/*empMap.size() == 0 ||*/ empMap==null) {
						failureMsg.append("<br/>员工 " + map.get("emp_id").get(1) + " 不存在; ");
						failureNum++;
						continue;
					}

					HrDrugPerm drugPerm = new HrDrugPerm();
					//SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
					if (map.get("get_date").get(1) != null) {
						dateFlag = DateUtil.CheckDate(map.get("get_date")
								.get(1).toString());
						if (!dateFlag) {
							failureMsg.append("<br/>"+map.get("get_date").get(1)
									+ " 日期格式不合法;<br/>日期格式举例：2018-01-01 ");
							failureNum++;
							continue;
						} else {
							saveMap.put("get_date", map.get("get_date").get(1));
						}

					}else{
						saveMap.put("get_date", null);
					}
					if (map.get("stop_date").get(1) != null) {
						dateFlag = DateUtil.CheckDate(map.get("stop_date")
								.get(1).toString());
						if (!dateFlag) {
							failureMsg.append("<br/>"+map.get("stop_date").get(1)
									+ "日期格式不合法;<br/>日期格式举例：2018-01-01 ");
							failureNum++;
							continue;
						} else {
							saveMap.put("stop_date", map.get("stop_date")
									.get(1));
						}

					}else{
						saveMap.put("stop_date",null);
					}
                    if(permTypeMap.containsValue(map.get("perm_type").get(1))){
                    	saveMap.put("perm_type",map.get("perm_type").get(1));
                    }else  if(permTypeMap.containsKey(map.get("perm_type").get(1))){
                    	saveMap.put("perm_type",permTypeMap.get(map.get("perm_type").get(1)));
                    }else{
                    	failureMsg.append("<br/>"+map.get("perm_type").get(1)
								+ "权限类别不存在");
						failureNum++;
						continue;
                    }
					
					if (map.get("apply_date").get(1) != null) {
						dateFlag = DateUtil.CheckDate(map.get("apply_date").get(1).toString());
						if (!dateFlag) {
							failureMsg.append("<br/>"+map.get("apply_date").get(1)+ " 日期格式不合法;<br/>日期格式举例：2018-01-01 ");
							failureNum++;
							continue;
						} else {
							saveMap.put("apply_date", map.get("apply_date").get(1));
						}
					}else{
						saveMap.put("apply_date", null);
					}
					saveMap.put("note", map.get("note").get(1));
					saveMap.put("med_id", null);
					saveMap.put("emp_id", empMap.get("emp_id"));
					
					Map<String, Object> drugMap = hrDrugPermMapper.queryEmp(saveMap);
					if (drugMap!=null) {
						failureMsg.append("<br/>员工 " + map.get("emp_id").get(1)+ "已存在相同权限; ");
						failureNum++;
						continue;
					}
				
					saveMap.put("state",map.get("state").get(1) == null ? "0" :  whetherMap.get(map.get("state").get(1)));
					
					successNum++;
					saveList.add(saveMap);
				}
				if (saveList.size() > 0) {
					hrDrugPermMapper.addBatchimport(saveList, permMap);
				}
				if (failureNum > 0) {
					failureMsg.insert(0, "，失败 " + failureNum + " 条，导入信息如下：");
				}
			}
			return "{\"msg\":\"已成功导入 " + successNum + "条" + failureMsg + "\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage());
			return "{\"error\":\"导入失败！\"}";
		}
	}

}
