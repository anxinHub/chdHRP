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
import com.chd.base.util.ReadFiles;
import com.chd.hrp.hr.dao.nursing.HrAcademicAbilityMapper;
import com.chd.hrp.hr.dao.nursing.HrCardiopulmonaryMapper;
import com.chd.hrp.hr.entity.nursing.HrAcademicAbility;
import com.chd.hrp.hr.entity.nursing.HrCardiopulmonary;
import com.chd.hrp.hr.service.nursing.HrCardiopulmonaryService;
import com.chd.hrp.sys.dao.EmpDictMapper;
import com.github.pagehelper.PageInfo;

/**
 * CPR考核
 * 
 * @author Administrator
 *
 */
@Service("hrCardiopulmonaryService")
public class HrCardiopulmonaryServiceImpl implements HrCardiopulmonaryService {
	private static Logger logger = Logger.getLogger(HrCardiopulmonaryServiceImpl.class);

	@Resource(name = "hrCardiopulmonaryMapper")
	private final HrCardiopulmonaryMapper hrCardiopulmonaryMapper = null;
	

	@Resource(name = "hrAcademicAbilityMapper")
	private final HrAcademicAbilityMapper hrAcademicAbilityMapper = null;
	
	@Resource(name = "empDictMapper")
	private final EmpDictMapper empDictMapper = null;

	// 默认显示20条数据
	RowBounds rowBoundsALL = new RowBounds(0, 20);

	/**
	 * CPR考核增加
	 */
	@Override
	public String addCardiopulmonary(Map<String, Object> entityMap) throws DataAccessException {
		try {	
			
			
			List<HrCardiopulmonary> alllistVo = JSONArray.parseArray(String.valueOf(entityMap.get("paramVo")),
				HrCardiopulmonary.class);
		
			/**
			 * 增加
			 */
			if (alllistVo!=null && alllistVo.size() > 0) {
				for (HrCardiopulmonary HrCardiopulmonary : alllistVo) {
					HrCardiopulmonary.setState(0);
					HrCardiopulmonary.setGroup_id(Integer.parseInt(SessionManager.getGroupId()));
					HrCardiopulmonary.setHos_id(Integer.parseInt(SessionManager.getHosId()));
					HrCardiopulmonary.setYear(entityMap.get("year").toString());
			}}
			
		
			   hrCardiopulmonaryMapper.deleteCardiopulmonary(alllistVo);
				hrCardiopulmonaryMapper.addBatch(alllistVo);
				return "{\"msg\":\"保存成功.\",\"state\":\"true\"}";
			
		
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}

	}


	/**
	 * CPR考核删除
	 */
	@Override
	public String deleteCardiopulmonary(List<HrCardiopulmonary> entityList) throws DataAccessException {

		try {
			if (entityList !=null) {
				hrCardiopulmonaryMapper.deleteCardiopulmonary(entityList);
			}

			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}
	}

	/**
	 * CPR考核查询
	 */
	@SuppressWarnings("unchecked")
	@Override
	public String queryCardiopulmonary(Map<String, Object> entityMap) throws DataAccessException {

		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		if (sysPage.getTotal() == -1) {

			List<HrCardiopulmonary> list = (List<HrCardiopulmonary>) hrCardiopulmonaryMapper.query(entityMap);

			return ChdJson.toJson(list);

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

			List<HrCardiopulmonary> list = (List<HrCardiopulmonary>) hrCardiopulmonaryMapper.query(entityMap, rowBounds);

			@SuppressWarnings("rawtypes")
			PageInfo page = new PageInfo(list);

			return ChdJson.toJson(list, page.getTotal());

		}

	}
	@Override
	public String confirmCardiopulmonary(HrCardiopulmonary hrCardiopulmonary) throws DataAccessException {
		try {
			hrCardiopulmonaryMapper.addPromotionEvaluate(hrCardiopulmonary);
			hrCardiopulmonaryMapper.confirmCardiopulmonary(hrCardiopulmonary);
			return "{\"msg\":\"提交成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}
	}
	@Override
	public String confirmCardiopulmonaryBatch(List<HrCardiopulmonary> list) throws DataAccessException {
		try {
			if(list == null || list.size() == 0){
				return "{\"msg\":\"提交失败，请选择行！\",\"state\":\"false\"}";
			}
			hrCardiopulmonaryMapper.addPromotionEvaluateBatch(list);
			hrCardiopulmonaryMapper.confirmCardiopulmonaryBatch(list);
			return "{\"msg\":\"提交成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}
	}


	@Override
	public String reConfirmCardiopulmonary(HrCardiopulmonary hrCardiopulmonary) throws DataAccessException {
		try {
			hrCardiopulmonaryMapper.reConfirmPromotionEvaluate(hrCardiopulmonary);
			hrCardiopulmonaryMapper.confirmCardiopulmonary(hrCardiopulmonary);
			return "{\"msg\":\"撤回成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}
	}
	@Override
	public String reConfirmCardiopulmonaryBatch(List<HrCardiopulmonary> list) throws DataAccessException {
		try {
			if(list == null || list.size() == 0){
				return "{\"msg\":\"撤回失败，请选择行.\",\"state\":\"false\"}";
			}
			hrCardiopulmonaryMapper.reConfirmPromotionEvaluateBatch(list);
			hrCardiopulmonaryMapper.confirmCardiopulmonaryBatch(list);
			return "{\"msg\":\"撤回成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}
	}


	@Override
	public String importCPR(Map<String, Object> entityMap)
			throws DataAccessException {

		int successNum = 0;
		int failureNum = 0;
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
					
					if("null".equals(map.get("year").get(1)) || "null".equals(map.get("emp_id").get(1))){
						continue;
					}
					
					if("".equals(map.get("year").get(1)) || "".equals(map.get("emp_id").get(1))){
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
						failureMsg.append("<br/>职工ID"+map.get("emp_id")+" 不存在; ");
						failureNum++;
						continue;
					}
					
					saveMap.remove("emp_id");
					saveMap.put("emp_id", emp.get("emp_id"));
					saveMap.put("duty_code", emp.get("duty_code"));
					saveMap.put("title_code", emp.get("title_code"));
					saveMap.put("level_code", emp.get("level_code"));
					saveMap.put("cpr_score", map.get("cpr_score").get(1));
					saveMap.put("state", whetherMap.get(map.get("state").get(1)==null?"0":map.get("state").get(1)));
					saveMap.put("note", map.get("note").get(1));
					HrAcademicAbility type = hrCardiopulmonaryMapper.queryByCode(saveMap);
					
					if(type != null){
						failureMsg.append(map.get("year")+"年"+"<br/>人员 "+map.get("emp_name")+" 已存在; ");
						failureNum++;
						continue;
					}
					successNum++;
					saveList.add(saveMap);
				}
				if(saveList.size() > 0){
					hrCardiopulmonaryMapper.addBatch(saveList);
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
