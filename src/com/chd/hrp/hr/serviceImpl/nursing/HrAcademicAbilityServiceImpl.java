package com.chd.hrp.hr.serviceImpl.nursing;

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
import com.chd.base.SessionManager;
import com.chd.base.SysPage;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.base.util.ReadFiles;
import com.chd.hrp.hr.dao.nursing.HrAcademicAbilityMapper;
import com.chd.hrp.hr.entity.attendancemanagement.attend.HrAttendItemFz;
import com.chd.hrp.hr.entity.nursing.HrAcademicAbility;
import com.chd.hrp.hr.service.nursing.HrAcademicAbilityService;
import com.chd.hrp.sys.dao.EmpDictMapper;
import com.chd.hrp.sys.entity.EmpDict;
import com.github.pagehelper.PageInfo;

/**
 * 学术能力
 * 
 * @author Administrator
 *
 */
@Service("hrAcademicAbilityService")
public class HrAcademicAbilityServiceImpl implements HrAcademicAbilityService {
	private static Logger logger = Logger.getLogger(HrAcademicAbilityServiceImpl.class);

	@Resource(name = "hrAcademicAbilityMapper")
	private final HrAcademicAbilityMapper hrAcademicAbilityMapper = null;
	
	@Resource(name = "empDictMapper")
	private final EmpDictMapper empDictMapper = null;

	// 默认显示20条数据
	RowBounds rowBoundsALL = new RowBounds(0, 20);

	/**
	 * 学术能力增加
	 */
	@Override
	public String addAcademicAbility(Map<String, Object> entityMap) throws DataAccessException {
		try {
			boolean falg = true;
			List<HrAcademicAbility> alllistVo = JSONArray.parseArray(String.valueOf(entityMap.get("paramVo")),
					HrAcademicAbility.class);

			/**
			 * 增加
			 */
			if (alllistVo != null && alllistVo.size() > 0) {
				for (HrAcademicAbility hrAcademicAbility : alllistVo) {
					if (StringUtils.isEmpty(hrAcademicAbility.getYear()) || hrAcademicAbility.getEmp_id() == null) {
						falg = false;
						break;
					}
					hrAcademicAbility.setGroup_id(Integer.parseInt(SessionManager.getGroupId()));
					hrAcademicAbility.setHos_id(Integer.parseInt(SessionManager.getHosId()));
				}
			}
			if (!falg) {
				return ("{\"error\":\"保存失败,请不要保存空行数据\",\"state\":\"false\"}");
			}
			hrAcademicAbilityMapper.deleteBatchStoreCondition(alllistVo);
			hrAcademicAbilityMapper.addStoreCondition(alllistVo);

			return "{\"msg\":\"保存成功.\",\"state\":\"true\"}";

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}

	}

	/**
	 * 学术能力删除
	 */
	@Override
	public String deleteAcademicAbility(List<HrAcademicAbility> entityList) throws DataAccessException {

		try {
			if (entityList != null) {
				hrAcademicAbilityMapper.deleteBatchStoreCondition(entityList);
			}
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}
	}

	/**
	 * 学术能力查询
	 */
	@SuppressWarnings("unchecked")
	@Override
	public String queryAcademicAbility(Map<String, Object> entityMap) throws DataAccessException {

		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		if (sysPage.getTotal() == -1) {

			List<HrAcademicAbility> list = (List<HrAcademicAbility>) hrAcademicAbilityMapper.query(entityMap);

			return ChdJson.toJson(list);

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

			List<HrAcademicAbility> list = (List<HrAcademicAbility>) hrAcademicAbilityMapper.query(entityMap,
					rowBounds);

			@SuppressWarnings("rawtypes")
			PageInfo page = new PageInfo(list);

			return ChdJson.toJson(list, page.getTotal());

		}

	}

	@Override
	public String queryAcademicAbilityTree(Map<String, Object> entityMap) throws DataAccessException {

		StringBuilder treeJson = new StringBuilder();

		treeJson.append("[");
		List<HrAcademicAbility> storeTypeList = (List<HrAcademicAbility>) hrAcademicAbilityMapper.query(entityMap);
		for (HrAcademicAbility storeType : storeTypeList) {
			/*
			 * treeJson.append( "{'id':'" + storeType.getKind_code() +
			 * "', 'pId':'0', 'name':'" + storeType.getKind_name() + "'},");
			 */
		}
		treeJson.append("]");
		return treeJson.toString();
	}

	@Override
	public String confirmAcademicAbility(HrAcademicAbility hrAcademicAbility) throws DataAccessException {
		try {
			hrAcademicAbilityMapper.addPromotionEvaluate(hrAcademicAbility);

			hrAcademicAbilityMapper.confirmAcademicAbility(hrAcademicAbility);

			return "{\"msg\":\"提交成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}

	}
	@Override
	public String confirmAcademicAbilityBatch(List<HrAcademicAbility> list) throws DataAccessException {
		try {
			if(list == null || list.size() == 0){
				return "{\"msg\":\"提交失败，请选择行！\",\"state\":\"false\"}";
			}
			hrAcademicAbilityMapper.addPromotionEvaluateBatch(list);
			hrAcademicAbilityMapper.confirmAcademicAbilityBatch(list);
			return "{\"msg\":\"提交成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}
	}

	@Override
	public String reConfirmAcademicAbility(HrAcademicAbility hrAcademicAbility) throws DataAccessException {
		try {
			hrAcademicAbilityMapper.reConfirmPromotionEvaluate(hrAcademicAbility);

			hrAcademicAbilityMapper.confirmAcademicAbility(hrAcademicAbility);

			return "{\"msg\":\"撤回成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}

	}
	@Override
	public String reConfirmAcademicAbilityBatch(List<HrAcademicAbility> list) throws DataAccessException {
		try {
			if(list == null || list.size() == 0){
				return "{\"msg\":\"撤回失败，请选择行！\",\"state\":\"false\"}";
			}
			hrAcademicAbilityMapper.reConfirmPromotionEvaluateBatch(list);
			hrAcademicAbilityMapper.confirmAcademicAbilityBatch(list);
			return "{\"msg\":\"撤回成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}
	}

	/**
	 * 导入
	 */
	@Override
	public String importAcademic(Map<String, Object> entityMap) throws DataAccessException {
		int successNum = 0;
		int failureNum = 0;
		StringBuilder failureMsg = new StringBuilder();
		List<Map<String, Object>> saveList = new ArrayList<Map<String, Object>>();
		Map<String, Object> whetherMap = new HashMap<String, Object>();
		whetherMap.put("提交", "1");
		whetherMap.put("新建", "0");
		whetherMap.put("1", "1");
		whetherMap.put("0", "0");

		try {
			// 查询所有员工信息

			List<Map<String, List<String>>> list = ReadFiles.readFiles(entityMap);
			if (list != null && list.size() > 0) {
				for (Map<String, List<String>> map : list) {
					
					//过滤空行
					if("".equals(map.get("emp_id").get(1)) || "".equals(map.get("year").get(1))){
						continue;
					}
					
					if("null".equals(map.get("emp_id").get(1)) || "null".equals(map.get("year").get(1))){
						continue;
					}
					Map<String, Object> saveMap = new HashMap<String, Object>();
					saveMap.put("group_id", SessionManager.getGroupId());
					saveMap.put("hos_id", SessionManager.getHosId());
					saveMap.put("year", map.get("year").get(1));
					saveMap.put("emp_id", map.get("emp_id").get(1));
					Map<String, Object> emp = hrAcademicAbilityMapper.queryEmp(saveMap);
					if(emp == null){
						failureMsg.append("<br/>职工ID"+map.get("emp_id")+" 不存在; ");
						failureNum++;
						continue;
					}
					saveMap.put("emp_id", emp.get("emp_id"));
					saveMap.put("emp_name", emp.get("emp_name"));
					HrAcademicAbility type = hrAcademicAbilityMapper.queryByCode(saveMap);
					if(type != null){
						failureMsg.append(map.get("year")+"年"+"<br/>人员 "+map.get("emp_name")+" 已存在; ");
						failureNum++;
						continue;
					}
					
					saveMap.put("duty_code", emp.get("duty_code"));
					saveMap.put("title_code", null);
					saveMap.put("level_code", null);
					saveMap.put("book_report", map.get("book_report").get(1));
					saveMap.put("case_analy", map.get("case_analy").get(1));
					saveMap.put("special_case_analy", map.get("special_case_analy").get(1));
					saveMap.put("state",
						whetherMap.get(StringUtils.isEmpty(map.get("state").get(1)) ? "0" : map.get("state").get(1)));
					saveMap.put("note", map.get("note").get(1));
					
					successNum++;
					saveList.add(saveMap);
					
				}
				if (saveList.size() > 0) {
					hrAcademicAbilityMapper.addBatch(saveList);
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
