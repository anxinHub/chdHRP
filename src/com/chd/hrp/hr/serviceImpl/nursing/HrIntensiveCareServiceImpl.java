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
import com.chd.hrp.hr.dao.nursing.HrIntensiveCareMapper;
import com.chd.hrp.hr.entity.nursing.HrAcademicAbility;
import com.chd.hrp.hr.entity.nursing.HrIntensiveCare;
import com.chd.hrp.hr.entity.nursing.HrIntensiveCare;
import com.chd.hrp.hr.service.nursing.HrIntensiveCareService;
import com.github.pagehelper.PageInfo;

/**
 * 重症护理能力
 * 
 * @author Administrator
 *
 */
@Service("hrIntensiveCareService")
public class HrIntensiveCareServiceImpl implements HrIntensiveCareService {
	private static Logger logger = Logger.getLogger(HrIntensiveCareServiceImpl.class);

	@Resource(name = "hrIntensiveCareMapper")
	private final HrIntensiveCareMapper hrIntensiveCareMapper = null;

	// 默认显示20条数据
	RowBounds rowBoundsALL = new RowBounds(0, 20);

	/**
	 * 重症护理能力增加
	 */
	@Override
	public String addIntensiveCare(Map<String, Object> entityMap) throws DataAccessException {

		try {

		List<HrIntensiveCare> alllistVo = JSONArray.parseArray(String.valueOf(entityMap.get("paramVo")),
					HrIntensiveCare.class);

			/**
			 * 增加
			 */
			if (alllistVo != null && alllistVo.size() > 0) {
				for (HrIntensiveCare hrAcademicAbility : alllistVo) {
					hrAcademicAbility.setState(0);
					hrAcademicAbility.setGroup_id(Integer.parseInt(entityMap.get("group_id").toString()));
					hrAcademicAbility.setHos_id(Integer.parseInt(entityMap.get("hos_id").toString()));
					hrAcademicAbility.setYear(entityMap.get("year").toString());
				}
			}
			
			hrIntensiveCareMapper.deleteIntensiveCare(alllistVo);
			int state = hrIntensiveCareMapper.addBatch(alllistVo);
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}

	}

	/**
	 * 重症护理能力删除
	 */
	@Override
	public String deleteIntensiveCare(List<HrIntensiveCare> entityList) throws DataAccessException {

		try {
			if (entityList != null) {
				hrIntensiveCareMapper.deleteIntensiveCare(entityList);
			}

			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}
	}

	/**
	 * 重症护理能力查询
	 */
	@SuppressWarnings("unchecked")
	@Override
	public String queryIntensiveCare(Map<String, Object> entityMap) throws DataAccessException {

		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		if (sysPage.getTotal() == -1) {

			List<HrIntensiveCare> list = (List<HrIntensiveCare>) hrIntensiveCareMapper.query(entityMap);

			return ChdJson.toJson(list);

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

			List<HrIntensiveCare> list = (List<HrIntensiveCare>) hrIntensiveCareMapper.query(entityMap, rowBounds);

			@SuppressWarnings("rawtypes")
			PageInfo page = new PageInfo(list);

			return ChdJson.toJson(list, page.getTotal());

		}

	}

	@Override
	public String importAdmin2(Map<String, Object> entityMap)
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
		Map<String, Object> SameMap = new HashMap<String, Object>();
		SameMap.put("不符合", "1");
		SameMap.put("符合", "0");
		SameMap.put("1", "1");
		SameMap.put("0", "0");
		try {
			List<Map<String, List<String>>> list = ReadFiles.readFiles(entityMap);
			if(list != null && list.size() > 0){
				for (Map<String, List<String>> map : list) {
					//过滤空行
					if("".equals(map.get("dept_code").get(1)) || "".equals(map.get("year").get(1))){
						continue;
					}
					
					if("null".equals(map.get("dept_code").get(1)) || "null".equals(map.get("year").get(1))){
						continue;
					}
					Map<String,Object> saveMap = new HashMap<String,Object>();
					saveMap.put("group_id", SessionManager.getGroupId());
					saveMap.put("hos_id", SessionManager.getHosId());
					saveMap.put("year", map.get("year").get(1));
					saveMap.put("dept_code", map.get("dept_code").get(1));
					Map<String, Object> dept=hrIntensiveCareMapper.queryDept(saveMap);
					if(dept == null){
						failureMsg.append("<br/>科室编号"+map.get("dept_code").get(1)+" 不存在; ");
						failureNum++;
						continue;
					}
					saveMap.put("dept_id", dept.get("dept_id"));
					saveMap.put("dept_no", dept.get("dept_no"));
					saveMap.put("patient", map.get("patient").get(1));
					saveMap.put("diagnose", map.get("diagnose").get(1));
					saveMap.put("in_hos_no", map.get("in_hos_no").get(1));
					saveMap.put("is_same", SameMap.get(map.get("is_same").get(1)));
					saveMap.put("state", "1");
					saveMap.put("note", map.get("note").get(1));
					HrIntensiveCare type = hrIntensiveCareMapper.queryByCode(saveMap);
					if(type != null){
						failureMsg.append("<br/>"+map.get("year")+"年"+"科室 "+dept.get("dept_name")+" 已存在; ");
						failureNum++;
						continue;
					}
					successNum++;
					saveList.add(saveMap);
				}
				if(saveList.size() > 0){
					hrIntensiveCareMapper.addBatch(saveList);
				}
				if (failureNum>0){
					failureMsg.insert(0, "，失败 "+failureNum+" 条，导入信息如下：");
				}
			}
			return "{\"msg\":\"已成功导入 "+successNum+"条"+failureMsg+"\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new SysException(e.getMessage());
		}
	}

}
