package com.chd.hrp.hr.serviceImpl.organize;

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
import com.chd.base.util.ChdJson;
import com.chd.base.util.ReadFiles;
import com.chd.hrp.hr.dao.base.HrSelectMapper;
import com.chd.hrp.hr.dao.organize.HosDutyInfoMapper;
import com.chd.hrp.hr.dao.organize.HosDutyLevelMapper;
import com.chd.hrp.hr.dao.organize.HosStationInfoMapper;
import com.chd.hrp.hr.dao.organize.HosStationLevelMapper;
import com.chd.hrp.hr.entity.orangnize.HosStation;
import com.chd.hrp.hr.service.organize.HosStationInfoService;
import com.github.pagehelper.PageInfo;

@Service("hosStationInfoService")
public class HosStationInfoServiceImpl implements HosStationInfoService {
	private static Logger logger = Logger.getLogger(HosStationInfoServiceImpl.class);

	@Resource(name = "hosStationInfoMapper")
	private final HosStationInfoMapper hosStationInfoMapper = null;
	@Resource(name = "hosStationLevelMapper")
	private final HosStationLevelMapper hosStationLevelMapper = null;

	@Resource(name = "hrSelectMapper")
	private final HrSelectMapper hrSelectMapper = null;
	
	@Resource(name = "hosDutyInfoMapper")
	private final HosDutyInfoMapper hosDutyInfoMapper = null;
	
	@Resource(name = "hosDutyLevelMapper")
	private final HosDutyLevelMapper hosDutyLevelMapper = null;
	
	// 默认显示20条数据
	RowBounds rowBoundsALL = new RowBounds(0, 20);

	@Override
	public String addStationInfo(Map<String, Object> entityMap) throws DataAccessException {
		try {
			// 获取对象职务等级
			List<HosStation> list = hosStationInfoMapper.queryInfoByCode(entityMap);

			if (list.size() > 0) {
				for (HosStation hrDutyLevel : list) {
					if (hrDutyLevel.getStation_code().equals(entityMap.get("station_code"))) {
						return "{\"error\":\"编码：" + hrDutyLevel.getStation_code().toString() + "已存在.\"}";
					}
					if (hrDutyLevel.getStation_name().equals(entityMap.get("station_name"))) {
						return "{\"error\":\"名称：" + hrDutyLevel.getStation_name().toString() + "已存在.\"}";
					}
				}
			}

			hosStationInfoMapper.add(entityMap);
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}
	}
	/**
	 * 根据编码查询
	 */
	@Override
	public HosStation queryByCode(Map<String, Object> entityMap) throws DataAccessException {
		return hosStationInfoMapper.queryByCode(entityMap);
	}
	
	/**
	 * 修改
	 */
	@Override
	public String updateStationInfo(Map<String, Object> entityMap) throws DataAccessException {
		try {
			if("1".equals(entityMap.get("is_flag"))){
				// 获取对象职务等级
				List<HosStation> list = hosStationInfoMapper.queryInfoByName(entityMap);

				if (list.size() > 0) {
					for (HosStation hrDutyLevel : list) {
						if (hrDutyLevel.getStation_name().equals(entityMap.get("station_name"))) {
							return "{\"error\":\"名称：" + hrDutyLevel.getStation_name().toString() + "已存在.\"}";
						}
					}
				}
			}
			
			hosStationInfoMapper.update(entityMap);
			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}
	}
	
	/**
	 * 批量删除
	 */
	@Override
	public String deleteBatchStationInfo(List<HosStation> entityList) throws DataAccessException {
		try {
			hosStationInfoMapper.deleteBatchStationInfo(entityList);
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}
	}
	
	/**
	 * 查询
	 */
	@Override
	public String queryStationInfo(Map<String, Object> entityMap) throws DataAccessException {
		SysPage sysPage = new SysPage();
		sysPage = (SysPage) entityMap.get("sysPage");
		if (sysPage.getTotal() == -1) {
			List<HosStation> list = (List<HosStation>) hosStationInfoMapper.query(entityMap);
			return ChdJson.toJson(list);
		} else {
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			List<HosStation> list = (List<HosStation>) hosStationInfoMapper.query(entityMap, rowBounds);
			PageInfo page = new PageInfo(list);
			return ChdJson.toJson(list, page.getTotal());
		}
	}
	
	/**
	 * 岗位树
	 */
	@Override
	public String queryStationInfoTree(Map<String, Object> entityMap) throws DataAccessException {
		StringBuilder treeJson = new StringBuilder();

		treeJson.append("[");
		List<HosStation> storeTypeList = (List<HosStation>) hosStationInfoMapper.query(entityMap);
		for (HosStation storeType : storeTypeList) {
			treeJson.append(
					"{'id':'" + storeType.getStation_code() + "', 'pId':'0', 'name':'" + storeType.getStation_name() + "'},");

		}
		treeJson.append("]");
		return treeJson.toString();
	}

	/**
	 * 导入
	 */
	@Override
	public String importDate(Map<String, Object> entityMap) throws DataAccessException {
		int successNum = 0;
		int failureNum = 0;
		StringBuilder failureMsg = new StringBuilder();
		String dept_name = null;
		
		entityMap.put("group_id", SessionManager.getGroupId());
		entityMap.put("hos_id", SessionManager.getHosId());
		
		List<Map<String,Object>> saveList = new ArrayList<Map<String,Object>>();
		/**
		 * 是否
		 */
		Map<String, Object> whetherMap = new HashMap<String, Object>();
		whetherMap.put("是", "1");
		whetherMap.put("否", "0");
		whetherMap.put("1", "1");
		whetherMap.put("0", "0");
		
		/**
		 * 部门信息
		 */
		List<Map<String,Object>> deptList = hrSelectMapper.queryCalssDept(entityMap);
		Map<String,Object> deptMap = new HashMap<String,Object>();
		for(Map<String,Object> dept : deptList){
			dept_name = dept.get("text").toString().split(" ")[1];
			
			deptMap.put(dept.get("text").toString().split(" ")[0], dept.get("id").toString()+"@"+dept_name);
			deptMap.put(dept.get("text").toString().split(" ")[1], dept.get("id").toString()+"@"+dept_name);
		}
		
		/**
		 * 职务信息
		 */
		List<Map<String,Object>> dutyList = hosDutyInfoMapper.queryDutyCode(entityMap);
		Map<String,Object> dutyMap = new HashMap<String,Object>();
		for(Map<String,Object> duty : dutyList){
			dutyMap.put(duty.get("id").toString(), duty.get("id").toString()+"@"+duty.get("text").toString());
			dutyMap.put(duty.get("text").toString(), duty.get("id").toString()+"@"+duty.get("text").toString());
		}
		
		// 岗位等级信息
		List<Map<String,Object>> levelList = hosStationLevelMapper.queryStationLevelList(entityMap);
		Map<String,Object> levelMap = new HashMap<String,Object>();
		for(Map<String,Object> level : levelList){
			levelMap.put(level.get("id").toString(), level.get("id").toString());
			levelMap.put(level.get("text").toString(), level.get("id").toString());
		}
		
		/**
		 * 岗位信息
		 */
		List<Map<String,Object>> stationList = hosStationInfoMapper.queryStationInfoList(entityMap);
		Map<String,Object> statioinMap = new HashMap<String,Object>();
		for(Map<String,Object> station : stationList){
			statioinMap.put(station.get("id").toString(), station.get("id").toString());
			statioinMap.put(station.get("text").toString(), station.get("id").toString());
		}
		
		try {
			List<Map<String, List<String>>> list = ReadFiles.readFiles(entityMap);
			if(list != null && list.size() > 0){
				for (Map<String, List<String>> map : list) {
					String stationName = "";
					Map<String,Object> saveMap = new HashMap<String,Object>();
					saveMap.put("group_id", SessionManager.getGroupId());
					saveMap.put("hos_id", SessionManager.getHosId());
					saveMap.put("copy_code", SessionManager.getCopyCode());
					
					if("".equals(map.get("station_code").get(1))){
						continue;
					}
					saveMap.put("station_code", map.get("station_code").get(1));
					saveMap.put("station_name", map.get("station_name").get(1));
					/**
					 * 判断编码是否存在
					 */
					if(!"".equals(statioinMap.get(saveMap.get("station_code"))) && statioinMap.get(saveMap.get("station_code")) != null){
						failureMsg.append("<br/>岗位编码: "+saveMap.get("station_code")+" 已存在; ");
						failureNum++;
						continue; 
					}
					if(!"".equals(statioinMap.get(saveMap.get("station_name"))) && statioinMap.get(saveMap.get("station_name")) != null){
						failureMsg.append("<br/>岗位名称: "+saveMap.get("station_name")+" 已存在; ");
						failureNum++;
						continue; 
					}
					
					saveMap.put("duty_code", map.get("duty_code").get(1));
					saveMap.put("station_level_code", map.get("station_level_code").get(1));
					saveMap.put("is_stop", whetherMap.get(map.get("is_stop").get(1)));
					saveMap.put("note", map.get("note").get(1));
					
					/**
					 * 判断部门信息是否存在
					 */
					if(map.get("dept_code").get(1)!=null){
						if("".equals(deptMap.get(map.get("dept_code").get(1))) || deptMap.get(map.get("dept_code").get(1)) ==null){
							failureMsg.append("<br/>科室信息: "+map.get("dept_code").get(1)+" 不存在; ");
							failureNum++;
							continue;
						}else{
							stationName = deptMap.get(map.get("dept_code").get(1)).toString().split("@")[1];
							saveMap.put("dept_id", deptMap.get(map.get("dept_code").get(1)).toString().split("@")[0]);
						}
					}
					
					
					/**
					 * 判断职务信息是否存在
					 */
					if("".equals(dutyMap.get(saveMap.get("duty_code").toString())) || dutyMap.get(saveMap.get("duty_code")) ==null){
						failureMsg.append("<br/>职务信息 "+saveMap.get("duty_code")+" 不存在; ");
						failureNum++;
						continue;
					}else{
						stationName = stationName+dutyMap.get(saveMap.get("duty_code").toString()).toString().split("@")[1];
						saveMap.put("duty_code", dutyMap.get(saveMap.get("duty_code").toString()).toString().split("@")[0]);
					}
					
					/**
					 * 岗位名称生成规则=部门名称+职务名称
					 */
					saveMap.put("station_name", stationName);
					/**
					 * 判断岗位等级信息是否存在
					 */
					if("".equals(levelMap.get(saveMap.get("station_level_code").toString()))|| levelMap.get(saveMap.get("station_level_code").toString()) ==null){
						failureMsg.append("<br/>岗位等级信息 "+saveMap.get("station_level_code")+" 不存在; ");
						failureNum++;
						continue;
					}else{
						saveMap.put("station_level_code", levelMap.get(saveMap.get("station_level_code").toString()));
					}
					
					successNum++;
					saveList.add(saveMap);
				}
				if(saveList.size() > 0){
					hosStationInfoMapper.addBatch(saveList);
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
	
	/**
	 * 打印
	 */
	@Override
	public List<Map<String, Object>> queryStationInfoPrint(Map<String, Object> entityMap) throws DataAccessException {
		return hosStationInfoMapper.queryStationInfoPrint(entityMap);
	}
	@Override
	public Map<String, Object> queryDeptInfo(Map<String, Object> entityMap) throws DataAccessException {
		Map<String, Object> deptMap = hosStationInfoMapper.queryDeptInfoByCode(entityMap);
		return deptMap;
	}
}
