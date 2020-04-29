package com.chd.hrp.hr.serviceImpl.attendancemanagement.leave;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.chd.base.MyConfig;
import com.chd.base.SessionManager;
import com.chd.base.SysPage;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.base.util.ReadFiles;
import com.chd.hrp.hr.dao.attendancemanagement.attend.HrAttendItemMapper;
import com.chd.hrp.hr.dao.attendancemanagement.leave.HrAttdentVacalBalanceMapper;
import com.chd.hrp.hr.dao.attendancemanagement.leave.HrXJEDMapper;
import com.chd.hrp.hr.dao.base.HrSelectMapper;
import com.chd.hrp.hr.service.attendancemanagement.leave.HrXJEDService;
import com.chd.hrp.hr.service.base.HrBaseService;
import com.github.pagehelper.PageInfo;

/**
 * 休假额度设置
 * 
 * @author Administrator
 *
 */
@Service("hrXJEDService")
public class HrXJEDServiceImpl implements HrXJEDService {
	private static Logger logger = Logger.getLogger(HrXJEDServiceImpl.class);

	@Resource(name = "hrXJEDMapper")
	private final HrXJEDMapper hrXJEDMapper = null;
	@Resource(name = "hrBaseService")
	private final HrBaseService hrBaseService = null;

	@Resource(name = "hrAttendItemMapper")
	private final HrAttendItemMapper hrAttendItemMapper = null;
	
	@Resource(name = "hrAttdentVacalBalanceMapper")
	private final HrAttdentVacalBalanceMapper hrAttdentVacalBalanceMapper = null;
	
	@Resource(name = "hrSelectMapper")
	private final HrSelectMapper hrSelectMapper = null;
	// 默认显示20条数据
	RowBounds rowBoundsALL = new RowBounds(0, 20);
	
	/**
	 * 初始化
	 */
	@Override
	public String importEmp(Map<String, Object> entityMap) throws DataAccessException {
		try {
			entityMap.put("attend_year", MyConfig.getCurAccYear());
			//修改休假额度表
			hrXJEDMapper.addImpXJED(entityMap);
			//修改结余表
			hrAttdentVacalBalanceMapper.addBatch(entityMap);
			
			return "{\"msg\":\"初始化成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}
	}
	
	/**
	 * 批量查询员工
	 */
	@SuppressWarnings("unchecked")
	@Override
	public String queryEmpByDeptId(Map<String, Object> entityMap) throws DataAccessException {
		entityMap.put("attend_year", MyConfig.getCurAccYear());
		List<Map<String, Object>> list = ChdJson.toListLower((List<Map<String, Object>>) hrXJEDMapper.queryEmpByDeptId(entityMap));
		return ChdJson.toJson(list);
	}
	
	/**
	 * 批量增加
	 */
	@Override
	public String addXJED(Map<String, Object> entityMap) throws DataAccessException {

		try {
			entityMap.put("attend_year", MyConfig.getCurAccYear());
			//修改休假额度表
			hrXJEDMapper.update(entityMap);
			//修改结余表
			hrAttdentVacalBalanceMapper.update(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}

	}
	
	/**
	 * 保存
	 */
	@Override
	public String saveXJED(Map<String, Object> entityMap) throws DataAccessException {
		
		try {
			entityMap.put("attend_year", MyConfig.getCurAccYear());
			List<Map<String,Object>> saveList = new ArrayList<Map<String,Object>>();
			
			JSONArray json = JSONArray.parseArray(String.valueOf(entityMap.get("allData")));
			JSONObject jsonObj = null;
			Map<String,Object> saveMap = null;
			Iterator iterator = json.iterator();
			while(iterator.hasNext()){
				jsonObj = JSONObject.parseObject(iterator.next().toString());
				
				if(jsonObj.getString("attend_code").length() > 0){
					saveMap = new HashMap<String,Object>();
					
					saveMap.put("emp_id", jsonObj.get("emp_id"));
					saveMap.put("attend_code", jsonObj.get("attend_code"));
					saveMap.put("attend_ed", jsonObj.get("attend_ed"));
					
					saveMap.put("limit", jsonObj.get("attend_ed"));
					saveMap.put("bala_amt", jsonObj.get("attend_ed"));
					
					saveList.add(saveMap);
				}
			}
			
			if (saveList.size() > 0) {
				hrXJEDMapper.updateXJED(saveList, entityMap);
				hrAttdentVacalBalanceMapper.updateBatch(saveList, entityMap);
			}

			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}
	}
	
	/**
	 * 查询
	 */
	@SuppressWarnings("unchecked")
	@Override
	public String queryXJED(Map<String, Object> entityMap) throws DataAccessException {

		SysPage sysPage = new SysPage();
		sysPage = (SysPage) entityMap.get("sysPage");
		
		StringBuffer sql = new StringBuffer();
		entityMap.put("attend_types", "03");
		entityMap.put("attend_ed_is", "1");
		entityMap.put("attend_year", MyConfig.getCurAccYear());
		
		List<Map<String, Object>> attendcode = (List<Map<String, Object>>) hrAttendItemMapper.query(entityMap);
		for (Map<String, Object> map : attendcode) {
			String attend_code = map.get("attend_code").toString();
			sql.append(" max(case when  hax.attend_code = '" + attend_code + "'  then hax.attend_ed end) as " + "\"" + attend_code + "\"" + ",");
		}
		entityMap.put("sql", sql);
		if (sysPage.getTotal() == -1) {
			List<Map<String, Object>> list = ChdJson.toListLower((List<Map<String, Object>>) hrXJEDMapper.query(entityMap));
			return ChdJson.toJson(list);
		} else {
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			List<Map<String, Object>> list = ChdJson.toListLower((List<Map<String, Object>>) hrXJEDMapper.query(entityMap, rowBounds));
			@SuppressWarnings("rawtypes")
			PageInfo page = new PageInfo(list);
			return ChdJson.toJson(list, page.getTotal());
		}
	}
	
	/**
	 * 打印 
	 */
	@Override
	public List<Map<String, Object>> queryXJEDByPrint(Map<String, Object> entityMap) throws DataAccessException {
		StringBuffer sql = new StringBuffer();
		entityMap.put("attend_types", "03");
		entityMap.put(" attend_year", MyConfig.getCurAccYear());
		entityMap.put("dept_source", MyConfig.getSysPara("06103"));
		List<Map<String, Object>> attendcode = (List<Map<String, Object>>) hrAttendItemMapper.query(entityMap);
		for (Map<String, Object> map : attendcode) {
			String attend_code = map.get("attend_code").toString();
			sql.append(" max(case when  hax.attend_code = '" + attend_code + "'  then hax.attend_ed end) as " + "\"" + attend_code + "\"" + ",");
		}
		entityMap.put("sql", sql);
		List<Map<String, Object>> list = ChdJson.toListLower(hrXJEDMapper.queryXJEDByPrint(entityMap));
		return list;
	}
	
	/**
	 * 考勤分类下拉框
	 */
	@Override
	public String queryAttendCode(Map<String, Object> entityMap) throws DataAccessException {
		List<Map<String, Object>> list = hrXJEDMapper.queryAttendCode(entityMap);
		return JSONArray.toJSONString(list);
	}
	
	/**
	 * 动态查询表头
	 */
	@Override
	public String queryAttendItem(Map<String, Object> entityMap) throws DataAccessException {
		List<Map<String, Object>> list = (List<Map<String, Object>>) hrXJEDMapper.queryAttendItem(entityMap);
		return ChdJson.toJson(list);
	}

	/**
	 * 导入
	 */
	@Override
	public String importXJED(Map<String, Object> entityMap) throws DataAccessException {
		entityMap.put("attend_year", MyConfig.getCurAccYear());
		
		int successNum = 0;
		int failureNum = 0;
		StringBuilder failureMsg = new StringBuilder();
		List<Map<String, Object>> saveList = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> updateList = new ArrayList<Map<String, Object>>();
		try {
			// 个人限额控制的考勤项目
			List<Map<String, Object>> listItem = (List<Map<String, Object>>) hrXJEDMapper.queryAttendItemEd(entityMap);
			Map<String, Object> item = new HashMap<String, Object>();// {0301, 0301, 婚假, 0301}
			for (Map<String, Object> map : listItem) {
				item.put(map.get("attend_code").toString(), map.get("attend_code").toString());
				item.put(map.get("attend_name").toString(), map.get("attend_code").toString());
			}
			
			// 所有职工Map
			List<Map<String,Object>> empList =(List<Map<String, Object>>) hrSelectMapper.queryPerson(entityMap);
			Map<String,Object> empMap = new HashMap<String,Object>();
			for(Map<String,Object> map : empList){
				empMap.put(map.get("text").toString().split(" ")[0], map.get("id"));
				empMap.put(map.get("text").toString().split(" ")[1], map.get("id"));
			}
			
			//判断职工是否存在休假额度表中
			List<Map<String,Object>> empCodeList = hrXJEDMapper.queryAttendEmpByYear(entityMap);
			Map<String,Object> empCodeMap = new HashMap<String,Object>();
			for(Map<String,Object> map : empCodeList){
				empCodeMap.put(map.get("EMP_ID").toString()+"-"+map.get("ATTEND_CODE"), map.get("EMP_ID").toString()+"-"+map.get("ATTEND_CODE"));
			}
			
			// 记录职工，判断重复
			Map<String, Object> repeatMap = new HashMap<String, Object>();
			
			List<Map<String, List<String>>> list = ReadFiles.readFiles(entityMap);
			if (list != null && list.size() > 0) {

				for (Map<String, List<String>> map : list) {
					if(map == null){
						continue;
					}
					
					// 判断数据是否重复
					String key1 = map.get("emp_code").get(1).toString() + "-" + map.get("emp_name").get(1).toString() ;
					if (!"".equals(repeatMap.get(key1)) && repeatMap.get(key1) != null) {
						failureMsg.append("<br/>职工: " + map.get("emp_code").get(1).toString() + " " + map.get("emp_name").get(1).toString() + " 在"
								+ entityMap.get("attend_year") + " 重复导入; ");
						failureNum++;
						continue;
					} else {
						repeatMap.put(key1, map);

						// 遍历动态假期
						for (String key : item.keySet()) {
							if (map.containsKey(key)) {
								
								Map<String, Object> saveMap = new HashMap<String, Object>();
								
								saveMap.put("emp_code", map.get("emp_code").get(1));
								saveMap.put("emp_name", map.get("emp_name").get(1));
								
								//职工ID
								if ("".equals(empMap.get(map.get("emp_code").get(1))) || empMap.get(map.get("emp_code").get(1)) == null) {
									failureMsg.append("<br/>职工信息: " + map.get("emp_code").get(1) + " 不存在; ");
									failureNum++;
									continue;
								} else {
									saveMap.put("emp_id", empMap.get(map.get("emp_code").get(1)));
								}
								//考勤项目
								saveMap.put("attend_code", key);
								
								//考勤项目值
								if ("".equals(map.get(key).get(1).toString()) || map.get(key).get(1) == null) {
									saveMap.put("attend_ed", 0);
								} else {
									saveMap.put("attend_ed", map.get(key).get(1));
								}
								
								saveMap.put("limit", saveMap.get("attend_ed"));
								saveMap.put("bala_amt", saveMap.get("attend_ed"));
								
								//判断职工是否是新添加的
								String keys = saveMap.get("emp_id").toString()+"-"+key;
								if("".equals(empCodeMap.get(keys)) || empCodeMap.get(keys) == null){
									saveList.add(saveMap);
								}else{
									updateList.add(saveMap);
								}

							}
						}
						successNum++;
					}
				}
			}
			
			if (failureNum > 0) {
				return"{\"msg\":\"导入失败："+ failureMsg + "\",\"state\":\"false\"}";
			}
			
			List<Map<String,Object>> tList = new ArrayList<Map<String,Object>>();
			//批量插入
			if(saveList.size() > 0){
				if(saveList.size() <= 300){
					//插入休假额度表
					hrXJEDMapper.addXJED(saveList,entityMap);
					hrAttdentVacalBalanceMapper.addBatchBalance(saveList,entityMap);
				}else{
					for(Map<String, Object> tMap : saveList){
						tList.add(tMap);
						if(tList.size() == 300){
							hrXJEDMapper.addXJED(tList,entityMap);
							hrAttdentVacalBalanceMapper.addBatchBalance(tList,entityMap);
							tList = new ArrayList<Map<String,Object>>();
						}
					}
					if(tList.size() > 0){
						hrXJEDMapper.addXJED(tList,entityMap);
						hrAttdentVacalBalanceMapper.addBatchBalance(tList,entityMap);
					}
				}
			}
			
			//批量修改
			if(updateList.size() > 0){
				if(updateList.size() <= 300){
					//插入休假额度表
					hrXJEDMapper.updateXJED(updateList, entityMap);
					hrAttdentVacalBalanceMapper.updateBatch(updateList, entityMap);
				}else{
					for(Map<String, Object> tMap : updateList){
						tList.add(tMap);
						if(tList.size() == 300){
							hrXJEDMapper.updateXJED(tList, entityMap);
							hrAttdentVacalBalanceMapper.updateBatch(tList, entityMap);
							tList = new ArrayList<Map<String,Object>>();
						}
					}
					if(tList.size() > 0){
						hrXJEDMapper.updateXJED(tList, entityMap);
						hrAttdentVacalBalanceMapper.updateBatch(tList, entityMap);
					}
				}
			}
			
			return "{\"msg\":\"已成功导入 " + successNum + "条" + "\",\"state\":\"true\"}";
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}
	}

	
	//更新全院控制额度
	public Map<String, Object> updateXjedByItem(Map<String, Object> map) throws DataAccessException{
		Map<String, Object> retMap = new  HashMap<String, Object>();
		retMap.put("state", "true");
		
		try {
			map.put("group_id", SessionManager.getGroupId());
			map.put("hos_id", SessionManager.getHosId());
			map.put("attend_year", MyConfig.getCurAccYear());
			
			//查询新额度
			String ed = hrXJEDMapper.queryEdByItem(map);
			map.put("attend_ed", ed);
			map.put("limit", ed);
			
			//修改数据
			hrXJEDMapper.updateEdByItem(map);
			hrAttdentVacalBalanceMapper.updateLimitByItem(map);
			
		} catch (Exception e) {
			
			logger.error(e.getMessage(), e);
			throw new SysException("操作失败");
		}
		
		return retMap;
	}

	@Override
	public String queryItemGeRen(Map<String, Object> entityMap)
			throws DataAccessException {
		List<Map<String, Object>> list = (List<Map<String, Object>>) hrXJEDMapper.queryAttendItemEd(entityMap);
		return ChdJson.toJson(list);
	}

}
