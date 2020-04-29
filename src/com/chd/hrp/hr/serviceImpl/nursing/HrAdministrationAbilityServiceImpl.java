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
import com.chd.hrp.hr.dao.nursing.HrAdministrationAbilityMapper;
import com.chd.hrp.hr.entity.nursing.HrAcademicAbility;
import com.chd.hrp.hr.entity.nursing.HrAdministrationAbility;
import com.chd.hrp.hr.service.nursing.HrAdministrationAbilityService;
import com.github.pagehelper.PageInfo;

/**
 * 行政能力
 * 
 * @author Administrator
 *
 */
@Service("hrAdministrationAbilityService")
public class HrAdministrationAbilityServiceImpl implements HrAdministrationAbilityService {
	private static Logger logger = Logger.getLogger(HrAdministrationAbilityServiceImpl.class);

	@Resource(name = "hrAdministrationAbilityMapper")
	private final HrAdministrationAbilityMapper hrAdministrationAbilityMapper = null;
	@Resource(name = "hrAcademicAbilityMapper")
	private final HrAcademicAbilityMapper hrAcademicAbilityMapper = null;
	// 默认显示20条数据
	RowBounds rowBoundsALL = new RowBounds(0, 20);

	/**
	 * 行政能力增加
	 */
	@Override
	public String addAdministrationAbility(Map<String, Object> entityMap) throws DataAccessException {
		try {
		
			List<HrAdministrationAbility> alllistVo = JSONArray.parseArray(String.valueOf(entityMap.get("paramVo")),
					HrAdministrationAbility.class);
			/**
			 * 增加
			 */
			if (alllistVo != null && alllistVo.size() > 0) {
				for (HrAdministrationAbility hrAdministrationAbility : alllistVo) {
					hrAdministrationAbility.setState(0);
					hrAdministrationAbility.setGroup_id(Integer.parseInt(SessionManager.getGroupId()));
					hrAdministrationAbility.setHos_id(Integer.parseInt(SessionManager.getHosId()));
					hrAdministrationAbility.setYear(entityMap.get("year").toString());
				}
			}
				hrAdministrationAbilityMapper.deleteAdministrationAbility(alllistVo);
				hrAdministrationAbilityMapper.addBatch(alllistVo);
				return "{\"msg\":\"保存成功.\",\"state\":\"true\"}";
			
			
		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}

	}


	/**
	 * 行政能力删除
	 */
	@Override
	public String deleteAdministrationAbility(List<HrAdministrationAbility> entityList) throws DataAccessException {

		try {
			if (entityList != null) {
				hrAdministrationAbilityMapper.deleteAdministrationAbility(entityList);
			}


			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}
	}

	/**
	 * 行政能力查询
	 */
	@SuppressWarnings("unchecked")
	@Override
	public String queryAdministrationAbility(Map<String, Object> entityMap) throws DataAccessException {

		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		if (sysPage.getTotal() == -1) {

			List<HrAdministrationAbility> list = (List<HrAdministrationAbility>) hrAdministrationAbilityMapper.query(entityMap);

			return ChdJson.toJson(list);

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

			List<HrAdministrationAbility> list = (List<HrAdministrationAbility>) hrAdministrationAbilityMapper.query(entityMap, rowBounds);

			@SuppressWarnings("rawtypes")
			PageInfo page = new PageInfo(list);

			return ChdJson.toJson(list, page.getTotal());

		}

	}


    /**
     * 查询获奖下拉表格
     */
	@Override
	public String queryPrize(Map<String, Object> entityMap) throws DataAccessException {

		List<Map<String,Object>> list = hrAdministrationAbilityMapper.queryPrize(entityMap);
		return JSONArray.toJSONString(list);

	}


	@Override
	public String importAdmin(Map<String, Object> entityMap)
			throws DataAccessException {

		int successNum = 0;
		int failureNum = 0;
		StringBuilder failureMsg = new StringBuilder();
		List<Map<String,Object>> saveList = new ArrayList<Map<String,Object>>();
		
		try {
			List<Map<String, List<String>>> list = ReadFiles.readFiles(entityMap);
			if(list != null && list.size() > 0){
				for (Map<String, List<String>> map : list) {
					//过滤空行
					if("".equals(map.get("emp_id").get(1)) || "".equals(map.get("year").get(1))){
						continue;
					}
					
					if("null".equals(map.get("emp_id").get(1)) || "null".equals(map.get("year").get(1))){
						continue;
					}
					Map<String,Object> saveMap = new HashMap<String,Object>();
					saveMap.put("group_id", SessionManager.getGroupId());
					saveMap.put("hos_id", SessionManager.getHosId());
					saveMap.put("year", map.get("year").get(1));
					saveMap.put("emp_id", map.get("emp_id").get(1));
					saveMap.put("emp_name", map.get("emp_name").get(1));
					Map<String, Object> emp=hrAcademicAbilityMapper.queryEmp(saveMap);
					if(emp == null){
						failureMsg.append("<br/>职工ID"+map.get("emp_id").get(1)+" 不存在; ");
						failureNum++;
						continue;
					}
					
					saveMap.put("emp_id", emp.get("emp_id"));
					saveMap.put("duty_code", emp.get("duty_code"));
					saveMap.put("title_code", emp.get("title_code"));
					saveMap.put("level_code", emp.get("level_code"));
					saveMap.put("imtheme", map.get("imtheme").get(1));
					if(map.get("prize").get(1)!=null){
					saveMap.put("prize", map.get("prize").get(1));
					Map<String, Object> fdMap = hrAdministrationAbilityMapper.queryAdmin(saveMap);
					if(fdMap == null){
						failureMsg.append("<br/>获奖情况"+map.get("prize")+" 不存在; ");
						failureNum++;
						continue;
					}
					saveMap.put("prize", fdMap.get("FIELD_COL_CODE"));
					}
					saveMap.put("state",0);
					saveMap.put("note", map.get("note").get(1));
					HrAdministrationAbility type = hrAdministrationAbilityMapper.queryByCode(saveMap);
					
					if(type != null){
						failureMsg.append(map.get("year")+"年"+"<br/>人员 "+map.get("emp_name")+" 已存在; ");
						failureNum++;
						continue;
					}
					successNum++;
					saveList.add(saveMap);
				}
				if(saveList.size() > 0){
					hrAdministrationAbilityMapper.addBatch(saveList);
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
