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
import com.chd.hrp.hr.dao.nursing.HrPeerEvaluationMapper;
import com.chd.hrp.hr.entity.nursing.HrAcademicAbility;
import com.chd.hrp.hr.entity.nursing.HrPeerEvaluation;
import com.chd.hrp.hr.service.nursing.HrPeerEvaluationService;
import com.github.pagehelper.PageInfo;

/**
 * 同行评价
 * 
 * @author Administrator
 *
 */
@Service("hrPeerEvaluationService")
public class HrPeerEvaluationServiceImpl implements HrPeerEvaluationService {
	private static Logger logger = Logger.getLogger(HrPeerEvaluationServiceImpl.class);

	@Resource(name = "hrPeerEvaluationMapper")
	private final HrPeerEvaluationMapper hrPeerEvaluationMapper = null;
	@Resource(name = "hrAcademicAbilityMapper")
	private final HrAcademicAbilityMapper hrAcademicAbilityMapper = null;
	// 默认显示20条数据
	RowBounds rowBoundsALL = new RowBounds(0, 20);

	/**
	 * 同行评价增加
	 */
	@Override
	public String addPeerEvaluation(Map<String, Object> entityMap) throws DataAccessException {
		try {
			
			/**
			 * 先删除
			 */
			//hrPeerEvaluationMapper.deleteHrPeer(entityMap);
			List<HrPeerEvaluation> alllistVo = JSONArray.parseArray(String.valueOf(entityMap.get("paramVo")),
					HrPeerEvaluation.class);

			/**
			 * 增加
			 */
			if (alllistVo != null && alllistVo.size() > 0) {
				for (HrPeerEvaluation HrPeerEvaluation : alllistVo) {
					HrPeerEvaluation.setState(0);
					HrPeerEvaluation.setGroup_id(Integer.parseInt(SessionManager.getGroupId()));
					HrPeerEvaluation.setHos_id(Integer.parseInt(SessionManager.getHosId()));
					HrPeerEvaluation.setYear(entityMap.get("year").toString());
				}
			}
			
		
				hrPeerEvaluationMapper.deletePeerEvaluation(alllistVo);
				hrPeerEvaluationMapper.addBatch(alllistVo);

				return "{\"msg\":\"保存成功.\",\"state\":\"true\"}";
			
			

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}

	}

	/**
	 * 同行评价删除
	 */
	@Override
	public String deletePeerEvaluation(List<HrPeerEvaluation> entityList) throws DataAccessException {

		try {
			if (entityList != null) {
				hrPeerEvaluationMapper.deletePeerEvaluation(entityList);
			}

			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}
	}

	/**
	 * 同行评价查询
	 */
	@SuppressWarnings("unchecked")
	@Override
	public String queryPeerEvaluation(Map<String, Object> entityMap) throws DataAccessException {

		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		if (sysPage.getTotal() == -1) {

			List<HrPeerEvaluation> list = (List<HrPeerEvaluation>) hrPeerEvaluationMapper.query(entityMap);

			return ChdJson.toJson(list);

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

			List<HrPeerEvaluation> list = (List<HrPeerEvaluation>) hrPeerEvaluationMapper.query(entityMap, rowBounds);

			@SuppressWarnings("rawtypes")
			PageInfo page = new PageInfo(list);

			return ChdJson.toJson(list, page.getTotal());

		}

	}
	@Override
	public String confirmPeerEvaluation(HrPeerEvaluation hrPeerEvaluation) throws DataAccessException  {
		try {
			hrPeerEvaluationMapper.addPromotionEvaluate(hrPeerEvaluation);
	         
			 hrPeerEvaluationMapper.confirmPeerEvaluation(hrPeerEvaluation);
			
			return "{\"msg\":\"提交成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}

	}
	
	/**
	 * 批量提交
	 * @author yangyunfei
	 */
	@Override
	public String confirmPeerEvaluation(List<HrPeerEvaluation> list) throws DataAccessException{
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("group_id", SessionManager.getGroupId());
			map.put("hos_id", SessionManager.getHosId());
			map.put("state", 1);
			int count = hrPeerEvaluationMapper.queryStateCount(map, list);
			if(count > 0){
				return "{\"msg\":\"只能包含未提交的数据\",\"state\":\"false\"}";
			}
			hrPeerEvaluationMapper.confirmPeerEvaluationBatch(map, list);
			return "{\"msg\":\"提交成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}
	}


	@Override
	public String reConfirmPeerEvaluation(HrPeerEvaluation hrPeerEvaluation) throws DataAccessException {
		try {
			hrPeerEvaluationMapper.reConfirmPromotionEvaluate(hrPeerEvaluation);
			hrPeerEvaluationMapper.reConfirmPeerEvaluation(hrPeerEvaluation);
			return "{\"msg\":\"撤回成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}
	}
	@Override
	public String reConfirmPeerEvaluationBatch(List<HrPeerEvaluation> list) throws DataAccessException {
		try {
			if(list == null || list.size() == 0){
				return "{\"msg\":\"撤回失败，请选择行\",\"state\":\"false\"}";
			}
			hrPeerEvaluationMapper.reConfirmPromotionEvaluateBatch(list);
			hrPeerEvaluationMapper.reConfirmPeerEvaluationBatch(list);
			return "{\"msg\":\"撤回成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}
	}

	@Override
	public String importPEER(Map<String, Object> entityMap)
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
						failureMsg.append("<br/>职工ID"+map.get("emp_id")+" 不存在; ");
						failureNum++;
						continue;
					}
					saveMap.put("emp_id", emp.get("emp_id"));
					saveMap.put("duty_code", emp.get("duty_code"));
					saveMap.put("title_code", emp.get("title_code"));
					saveMap.put("level_code", emp.get("level_code"));
					saveMap.put("peer_score", map.get("peer_score").get(1));
					saveMap.put("state", whetherMap.get(map.get("state").get(1)==null?"0":map.get("state").get(1)));
					saveMap.put("note", map.get("note").get(1));
					HrAcademicAbility type = hrPeerEvaluationMapper.queryByCode(saveMap);
					
					if(type != null){
						failureMsg.append(map.get("year")+"年"+"<br/>人员 "+map.get("emp_name")+" 已存在; ");
						failureNum++;
						continue;
					}
					successNum++;
					saveList.add(saveMap);
				}
				if(saveList.size() > 0){
					hrPeerEvaluationMapper.addBatch(saveList);
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
