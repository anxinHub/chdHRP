/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */

package com.chd.hrp.hr.serviceImpl.medicalmanagement;

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
import com.chd.base.SessionManager;
import com.chd.base.SysPage;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.base.util.ReadFiles;
import com.chd.hrp.hr.dao.medicalmanagement.HrClinicDocWorkMapper;
import com.chd.hrp.hr.dao.medicalmanagement.HrTechDocWorkMapper;
import com.chd.hrp.hr.entity.medicalmanagement.HrClinicDocWork;
import com.chd.hrp.hr.entity.medicalmanagement.HrDrugPerm;
import com.chd.hrp.hr.entity.medicalmanagement.HrTechDocWork;
import com.chd.hrp.hr.service.medicalmanagement.HrClinicDocWorkService;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Description:
 * 
 * @Table: HR_CLINIC_DOC_WORK 临床医生工作量
 * @Author: bell
 * @email: bell@e-tonggroup.com
 * @Version: 1.0
 */

@Service("hrClinicDocWorkService")
public class HrClinicDocWorkServiceImpl implements HrClinicDocWorkService {

	private static Logger logger = Logger
			.getLogger(HrClinicDocWorkServiceImpl.class);
	// 引入DAO操作
	@Resource(name = "hrClinicDocWorkMapper")
	private final HrClinicDocWorkMapper hrClinicDocWorkMapper = null;

	// 引入DAO操作
	@Resource(name = "hrTechDocWorkMapper")
	private final HrTechDocWorkMapper hrTechDocWorkMapper = null;

	@Override
	public String addClinicalWorkload(Map<String, Object> entityMap)
			throws DataAccessException {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			/**
			 * 先删除
			 */
			//hrClinicDocWorkMapper.deletehrClinicDocWork(entityMap);
			List<HrClinicDocWork> alllistVo = JSONArray.parseArray(String.valueOf(entityMap.get("paramVo")),HrClinicDocWork.class);

			/**
			 * 增加
			 */
			if (alllistVo != null && alllistVo.size() > 0) {
			hrClinicDocWorkMapper.deleteClinicalWorkload(alllistVo,entityMap);
			hrClinicDocWorkMapper.addBatchClinicalWorkload(alllistVo,entityMap);
			}
			return "{\"msg\":\"保存成功.\",\"state\":\"true\"}";
		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}

	}

	@Override
	public String queryClinicalWorkload(Map<String, Object> entityMap)
			throws DataAccessException {

		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		if (sysPage.getTotal() == -1) {

			List<HrClinicDocWork> list = (List<HrClinicDocWork>) hrClinicDocWorkMapper
					.query(entityMap);

			return ChdJson.toJson(list);

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(),
					sysPage.getPagesize());

			List<HrClinicDocWork> list = (List<HrClinicDocWork>) hrClinicDocWorkMapper
					.query(entityMap, rowBounds);

			PageInfo page = new PageInfo(list);

			return ChdJson.toJson(list, page.getTotal());

		}

	}

	@Override
	public String deleteClinicalWorkload(List<HrClinicDocWork> entityList)
			throws DataAccessException {

		try {
			if (entityList != null) {
				Map<String,Object> mapVo=new HashMap<String,Object>();
				mapVo.put("group_id", SessionManager.getGroupId())   ;
				mapVo.put("hos_id", SessionManager.getHosId())   ;
				hrClinicDocWorkMapper.deleteClinicalWorkload(entityList,mapVo);
			}

			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}
	}

	@Override
	public String importClinicalDocWork(Map<String, Object> entityMap)
			throws DataAccessException {
		int successNum = 0;
		int failureNum = 0;
		StringBuilder failureMsg = new StringBuilder();
		List<Map<String, Object>> saveList = new ArrayList<Map<String, Object>>();
		Map<String, Object> whetherMap = new HashMap<String, Object>();
		whetherMap.put("是", "1");
		whetherMap.put("否", "0");
		whetherMap.put("1", "1");
		whetherMap.put("0", "0");

		try {
			List<Map<String, List<String>>> list = ReadFiles
					.readFiles(entityMap);
			if (list != null && list.size() > 0) {
				for (Map<String, List<String>> map : list) {
					
					//过滤空行
					if (map.get("dept_id").get(1) == null || map.get("emp_id").get(1) == null || map.get("year_month").get(1) == null) {
						continue;
					}
					if ("".equals(map.get("dept_id").get(1))|| "".equals(map.get("emp_id").get(1)) || "".equals(map.get("year_month").get(1))) {
						continue;
					}
					if ("null".equals(map.get("dept_id").get(1))|| "null".equals(map.get("emp_id").get(1)) || "null".equals(map.get("year_month").get(1))) {
						continue;
					}
					
					Map<String, Object> saveMap = new HashMap<String, Object>();
					saveMap.put("group_id", SessionManager.getGroupId());
					saveMap.put("hos_id", SessionManager.getHosId());

					// 科室
					Map<String, Object> useDeptDict = null;
					Map<String, Object> useDeptMap = new HashMap<String, Object>();
					Map<String, Object> deptMap = new HashMap<String, Object>();
					deptMap.put("group_id", SessionManager.getGroupId());
					deptMap.put("hos_id", SessionManager.getHosId());
					deptMap.put("copy_code", SessionManager.getCopyCode());
					deptMap.put("dept_code", map.get("dept_id").get(1));
					deptMap.put("dept_name", map.get("dept_id").get(1));
					useDeptDict = hrTechDocWorkMapper
							.queryDeptDictByCodeOrName(deptMap);
					if (useDeptDict == null
							|| useDeptDict.get("dept_id") == null) {
						failureMsg.append(map.get("dept_id").get(0)
								+ ":科室不存在！<br/>");
						failureNum++;
						continue;
					}
					saveMap.put("dept_id", useDeptDict.get("dept_id"));
					// 人员
					Map<String, Object> hos_emp = null;
					Map<String, Object> emp = new HashMap<String, Object>();
					Map<String, Object> empMap = new HashMap<String, Object>();
					empMap.put("group_id", SessionManager.getGroupId());
					empMap.put("hos_id", SessionManager.getHosId());
					empMap.put("copy_code", SessionManager.getCopyCode());
					empMap.put("emp_id", map.get("emp_id").get(1));
					empMap.put("emp_name", map.get("emp_id").get(1));
					empMap.put("dept_id", useDeptDict.get("dept_id"));
					empMap.put("dept_no", useDeptDict.get("dept_no"));
					hos_emp = hrTechDocWorkMapper.queryEmpByCode(empMap);
					if (hos_emp == null || hos_emp.get("emp_id") == null) {
						failureMsg.append("在"
								+ useDeptDict.get("dept_name") + "科中"
								+ map.get("emp_id").get(0) + "人员不存在！<br/>");
						failureNum++;
						continue;
					}
					saveMap.put("emp_id", hos_emp.get("emp_id"));
					saveMap.put("year_month", map.get("year_month").get(1));
					
					if(map.get("man_patient_num").get(1) != null && !"null".equals(map.get("man_patient_num").get(1))){
						saveMap.put("man_patient_num", map.get("man_patient_num").get(1));
					}else{
						saveMap.put("man_patient_num","");
					}
					
					if(map.get("cmi").get(1) != null && !"null".equals(map.get("cmi").get(1))){
						saveMap.put("cmi", map.get("cmi").get(1));
					}else{
						saveMap.put("cmi", "");
					}
					
					if(map.get("rw_patient_num").get(1) != null && !"null".equals(map.get("rw_patient_num").get(1))){
						saveMap.put("rw_patient_num", map.get("rw_patient_num").get(1));
					}else{
						saveMap.put("rw_patient_num","");
					}
					
					if(map.get("man_oper_num").get(1) != null && !"null".equals(map.get("man_oper_num").get(1))){
						saveMap.put("man_oper_num", map.get("man_oper_num").get(1));
					}else{
						saveMap.put("man_oper_num", "");
					}
					
					if(map.get("man_oper_perc").get(1) != null && !"null".equals(map.get("man_oper_perc").get(1))){
						saveMap.put("man_oper_perc", map.get("man_oper_perc").get(1));
					}else{
						saveMap.put("man_oper_perc","");
					}
					
					if(map.get("avg_inhos_charge").get(1) != null && !"null".equals(map.get("avg_inhos_charge").get(1))){
						saveMap.put("avg_inhos_charge", map.get("avg_inhos_charge").get(1));
					}else{
						saveMap.put("avg_inhos_charge","");
					}
					
					if(map.get("avg_inhos_days").get(1) != null && !"null".equals(map.get("avg_inhos_days").get(1))){
						saveMap.put("avg_inhos_days", map.get("avg_inhos_days").get(1));
					}else{
						saveMap.put("avg_inhos_days","");
					}
					
					if(map.get("med_val_add").get(1) != null && !"null".equals(map.get("med_val_add").get(1))){
						saveMap.put("med_val_add", map.get("med_val_add").get(1));
					}else{
						saveMap.put("med_val_add", "");
					}
					
					if(map.get("note").get(1) != null && !"null".equals(map.get("note").get(1))){
						saveMap.put("note", map.get("note").get(1));
					}else{
						saveMap.put("note", "");
					}
					

					HrClinicDocWork type = hrClinicDocWorkMapper.queryByCode(saveMap);

					if (type != null) {
						failureMsg.append("<br/> 已存在相同的记录; ");
						failureNum++;
						continue;
					}
					successNum++;
					saveList.add(saveMap);
				}
				if (saveList.size() > 0) {
					hrClinicDocWorkMapper.addBatch(saveList);
				}
				if (failureNum > 0) {
					failureMsg.insert(0, "，失败 " + failureNum + " 条，导入信息如下：");
				}
			}
			return "{\"msg\":\"已成功导入 " + successNum + "条" + failureMsg
					+ "\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new SysException("{\"error\":\"导入失败！\"}");
		}
	}

}
