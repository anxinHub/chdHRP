package com.chd.hrp.hr.serviceImpl.nursing;

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
import com.chd.base.util.DateUtil;
import com.chd.base.util.ReadFiles;
import com.chd.hrp.hr.dao.nursing.HrAcademicAbilityMapper;
import com.chd.hrp.hr.dao.nursing.HrTeachingAbilityMapper;
import com.chd.hrp.hr.entity.nursing.HrAcademicAbility;
import com.chd.hrp.hr.entity.nursing.HrPeerEvaluation;
import com.chd.hrp.hr.entity.nursing.HrTeachingAbility;
import com.chd.hrp.hr.service.nursing.HrTeachingAbilityService;
import com.github.pagehelper.PageInfo;

/**
 * 教学能力
 * 
 * @author Administrator
 *
 */
@Service("hrTeachingAbilityService")
public class HrTeachingAbilityServiceImpl implements HrTeachingAbilityService {
	private static Logger logger = Logger.getLogger(HrTeachingAbilityServiceImpl.class);

	@Resource(name = "hrTeachingAbilityMapper")
	private final HrTeachingAbilityMapper hrTeachingAbilityMapper = null;

	@Resource(name = "hrAcademicAbilityMapper")
	private final HrAcademicAbilityMapper hrAcademicAbilityMapper = null;
	// 默认显示20条数据
	RowBounds rowBoundsALL = new RowBounds(0, 20);

	/**
	 * 教学能力增加
	 */
	@Override
	public String addTeachingAbility(Map<String, Object> entityMap) throws DataAccessException {
		try {
           
			List<HrTeachingAbility> alllistVo = JSONArray.parseArray(String.valueOf(entityMap.get("paramVo")),HrTeachingAbility.class);

			/**
			 * 增加
			 */
			if (alllistVo != null && alllistVo.size() > 0) {
				for (HrTeachingAbility HrAcademicAbility : alllistVo) {
					HrAcademicAbility.setState(0);
					HrAcademicAbility.setGroup_id(Integer.parseInt(SessionManager.getGroupId()));
					HrAcademicAbility.setHos_id(Integer.parseInt(SessionManager.getHosId()));
				}
			}
			
		
				hrTeachingAbilityMapper.deleteTeachingAbility(alllistVo);
				hrTeachingAbilityMapper.addBatch(alllistVo);
				return "{\"msg\":\"保存成功.\",\"state\":\"true\"}";
			
		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}

	}

	
	/**
	 * 教学能力删除
	 */
	@Override
	public String deleteTeachingAbility(List<HrTeachingAbility> entityList) throws DataAccessException {

		try {
			if (entityList != null) {
				hrTeachingAbilityMapper.deleteTeachingAbility(entityList);
			}


			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}
	}

	/**
	 * 教学能力查询
	 */
	@SuppressWarnings("unchecked")
	@Override
	public String queryTeachingAbility(Map<String, Object> entityMap) throws DataAccessException {

		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		if (sysPage.getTotal() == -1) {

			List<HrTeachingAbility> list = (List<HrTeachingAbility>) hrTeachingAbilityMapper.query(entityMap);

			return ChdJson.toJson(list);

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

			List<HrTeachingAbility> list = (List<HrTeachingAbility>) hrTeachingAbilityMapper.query(entityMap,
					rowBounds);

			@SuppressWarnings("rawtypes")
			PageInfo page = new PageInfo(list);

			return ChdJson.toJson(list, page.getTotal());

		}

	}



	/**
	 * 教学种类下拉框
	 */
	@Override
	public String queryTeachType(Map<String, Object> entityMap) throws DataAccessException {
		List<Map<String, Object>> list = hrTeachingAbilityMapper.queryTeachType(entityMap);
		return JSONArray.toJSONString(list);
	}

	/**
	 * 查询种类下拉表格
	 */
	@Override
	public String queryHrTeachType(Map<String, Object> entityMap) throws DataAccessException {

		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		if (sysPage.getTotal() == -1) {

			List<HrTeachingAbility> list = (List<HrTeachingAbility>) hrTeachingAbilityMapper
					.queryHrTeachType(entityMap);

			return ChdJson.toJson(list);

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

			List<HrTeachingAbility> list = (List<HrTeachingAbility>) hrTeachingAbilityMapper.queryHrTeachType(entityMap,
					rowBounds);

			@SuppressWarnings("rawtypes")
			PageInfo page = new PageInfo(list);

			return ChdJson.toJson(list, page.getTotal());

		}

	}


	@Override
	public String importTeach(Map<String, Object> entityMap)
			throws DataAccessException {

		int successNum = 0;
		int failureNum = 0;
		boolean dateFlag=true;
		StringBuilder failureMsg = new StringBuilder();
		List<Map<String,Object>> saveList = new ArrayList<Map<String,Object>>();
		Map<String, Object> whetherMap = new HashMap<String, Object>();
		whetherMap.put("提交", "1");
		whetherMap.put("新建", "0");
		whetherMap.put("1", "1");
		whetherMap.put("0", "0");
		
		try {
			List<Map<String, List<String>>> list = ReadFiles.readFiles(entityMap);
			if(list != null && list.size() > 0){
				for (Map<String, List<String>> map : list) {
					//过滤空行
					if("".equals(map.get("emp_id").get(1)) || "".equals(map.get("teach_date").get(1))){
						continue;
					}
					
					if("null".equals(map.get("emp_id").get(1)) || "null".equals(map.get("teach_date").get(1))){
						continue;
					}
					Map<String,Object> saveMap = new HashMap<String,Object>();
					saveMap.put("group_id", SessionManager.getGroupId());
					saveMap.put("hos_id", SessionManager.getHosId());
					saveMap.put("teach_date", map.get("teach_date").get(1));
					if(map.get("teach_date").get(1).toString()!=null){
						dateFlag=DateUtil.CheckDateYMD(map.get("teach_date").get(1));
						   if (!dateFlag) {
					        	failureMsg.append("<br/>"+map.get("teach_date").get(1)+" 日期格式不合法;<br/>日期格式举例：20180101 ");
								failureNum++;
								continue;
					        }else{
					        	saveMap.put("teach_date", map.get("teach_date").get(1));
					        }
					}
					saveMap.put("emp_id", map.get("emp_id").get(1));
					saveMap.put("emp_name", map.get("emp_name").get(1));
					Map<String, Object> emp=hrAcademicAbilityMapper.queryEmp(saveMap);
					if(emp == null){
						failureMsg.append("<br/>职工ID"+map.get("emp_id")+" 不存在; ");
						failureNum++;
						continue;
					}
					saveMap.put("emp_id", emp.get("emp_id"));
					saveMap.put("duty_code", emp.get("duty_code"));
					saveMap.put("title_code", emp.get("title_code"));
					saveMap.put("level_code", emp.get("level_code"));
					if(map.get("teach_type").get(1)!=null){
					saveMap.put("teach_type", map.get("teach_type").get(1));
					Map<String, Object> teach = hrTeachingAbilityMapper.queryTeach(saveMap);
					if(teach == null){
						failureMsg.append("<br/>教学种类"+map.get("teach_type")+" 不存在; ");
						failureNum++;
						continue;
					}
					saveMap.put("teach_type", teach.get("teach_type"));
					}
					saveMap.put("teach_type", "");
					saveMap.put("state", whetherMap.get(map.get("state").get(1)));
					saveMap.put("note", map.get("note").get(1));
					HrAcademicAbility type = hrTeachingAbilityMapper.queryByCode(saveMap);
					
					if(type != null){
						failureMsg.append(map.get("year")+"年"+"<br/>人员 "+map.get("emp_name")+" 已存在; ");
						failureNum++;
						continue;
					}
					successNum++;
					saveList.add(saveMap);
				}
				if(saveList.size() > 0){
					hrTeachingAbilityMapper.addBatch(saveList);
				}
				if (failureNum>0){
					failureMsg.insert(0, "，失败 "+failureNum+" 条，导入信息如下：");
				}
			}
			return "{\"msg\":\"已成功导入 "+successNum+"条"+failureMsg+"\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage());
			return "{\"error\":\"导入失败！\"}";
		}
	}

}
