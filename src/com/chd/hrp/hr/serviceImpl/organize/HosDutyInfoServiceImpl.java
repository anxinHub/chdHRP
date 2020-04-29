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
import com.chd.base.util.StringTool;
import com.chd.hrp.hr.dao.base.HrSelectMapper;
import com.chd.hrp.hr.dao.organize.HosDutyInfoMapper;
import com.chd.hrp.hr.dao.organize.HosDutyKindMapper;
import com.chd.hrp.hr.dao.organize.HosDutyLevelMapper;
import com.chd.hrp.hr.entity.orangnize.HosDuty;
import com.chd.hrp.hr.service.organize.HosDutyInfoService;
import com.github.pagehelper.PageInfo;

@Service("hosDutyInfoService")
public class HosDutyInfoServiceImpl implements HosDutyInfoService {
	
	private static Logger logger = Logger.getLogger(HosDutyInfoServiceImpl.class);
	//引入DAO操作
	@Resource(name = "hosDutyInfoMapper")
	private final HosDutyInfoMapper hosDutyInfoMapper = null;
	
	@Resource(name = "hosDutyKindMapper")
	private final HosDutyKindMapper hosDutyKindMapper = null;
	
	@Resource(name = "hosDutyLevelMapper")
	private final HosDutyLevelMapper hosDutyLevelMapper = null;
	
	@Resource(name = "hrSelectMapper")
	private final HrSelectMapper hrSelectMapper = null;
	
	/**
	 * 添加
	 */
	@Override
	public String addHrDuty(Map<String, Object> entityMap) throws DataAccessException {
		try {
			List<HosDuty> list = hosDutyInfoMapper.queryDutyByCode(entityMap);
			if (list.size() > 0) {
				for (HosDuty HrDuty : list) {
					if (HrDuty.getDuty_code().equals(entityMap.get("duty_code"))) {
						return "{\"error\":\"编码：" + HrDuty.getDuty_code().toString() + "已存在.\"}";
					}
					if (HrDuty.getDuty_name().equals(entityMap.get("duty_name"))) {
						return "{\"error\":\"名称：" + HrDuty.getDuty_name().toString() + "已存在.\"}";
					}
				}
			}
		
			int state = hosDutyInfoMapper.add(entityMap);
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}
	}
	
	/**
	 * 更新
	 */
	@Override
	public String updateHrDuty(Map<String, Object> entityMap) throws DataAccessException {
		try {
			if("1".equals(entityMap.get("is_flag"))){
				List<HosDuty> list = hosDutyInfoMapper.queryDutyByName(entityMap);
				if (list.size() > 0) {
					for (HosDuty HrDuty : list) {
						if (HrDuty.getDuty_name().equals(entityMap.get("duty_name"))) {
							return "{\"error\":\"名称：" + HrDuty.getDuty_name().toString() + "已存在.\"}";
						}
					}
				}
			}
			
			int state = hosDutyInfoMapper.update(entityMap);
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}
	}
	/**
	 * 根据编码查询信息
	 */
	@Override
	public HosDuty queryHrDutyByCode(Map<String, Object> entityMap) throws DataAccessException {
		return hosDutyInfoMapper.queryByCode(entityMap);
	}
	
	/**
	 * 批量删除
	 */
	@Override
	public String deleteBatchHrDuty(List<HosDuty> entityList) throws DataAccessException {
		try {
			hosDutyInfoMapper.deleteBatchHrDuty(entityList);
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
	public String queryHrDuty(Map<String, Object> entityMap) throws DataAccessException {
		SysPage sysPage = new SysPage();
		sysPage = (SysPage) entityMap.get("sysPage");
		if (sysPage.getTotal() == -1) {
			List<HosDuty> list = (List<HosDuty>) hosDutyInfoMapper.query(entityMap);
			return ChdJson.toJson(list);
		} else {
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			List<HosDuty> list = (List<HosDuty>) hosDutyInfoMapper.query(entityMap, rowBounds);
			PageInfo page = new PageInfo(list);
			return ChdJson.toJson(list, page.getTotal());
		}
	}
	/**
	 * 职务信息树
	 */
	@Override
	public String queryHrDutyTree(Map<String, Object> entityMap) throws DataAccessException {
		StringBuilder treeJson = new StringBuilder();

		treeJson.append("[");
		List<HosDuty> storeTypeList = (List<HosDuty>) hosDutyInfoMapper.query(entityMap);
		for (HosDuty storeType : storeTypeList) {
			treeJson.append(
					"{'id':'" + storeType.getDuty_code() + "', 'pId':'0', 'name':'" + storeType.getDuty_name() + "'},");

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
		Map<String,Object> kindMap = new HashMap<String,Object>();
		entityMap.put("group_id", SessionManager.getGroupId());
		entityMap.put("hos_id", SessionManager.getHosId());
		/**
		 * 职务类别
		 */
		List<Map<String,Object>> kindList = hosDutyKindMapper.queryByCodeKind(entityMap);
		for(Map<String,Object> kind : kindList){
			kindMap.put(kind.get("id").toString(), kind.get("id").toString());
			kindMap.put(kind.get("text").toString(), kind.get("id").toString());
		}
		
		/**
		 * 职务等级
		 */
		List<Map<String,Object>> levelList = hosDutyLevelMapper.queryByCodeLevel(entityMap);
		Map<String,Object> levelMap = new HashMap<String,Object>();
		for(Map<String,Object> level : levelList){
			levelMap.put(level.get("id").toString(), level.get("id").toString());
			levelMap.put(level.get("text").toString(), level.get("id").toString());
		}
		
		/**
		 * 职务信息
		 */
		List<Map<String,Object>> dutyList = hosDutyInfoMapper.queryDutyCode(entityMap);
		Map<String,Object> dutyMap = new HashMap<String,Object>();
		for(Map<String,Object> duty : dutyList){
			dutyMap.put(duty.get("id").toString(), duty.get("id").toString());
			dutyMap.put(duty.get("text").toString(), duty.get("id").toString());
		}
		
		List<Map<String,Object>> saveList = new ArrayList<Map<String,Object>>();
		Map<String, Object> whetherMap = new HashMap<String, Object>();
		whetherMap.put("是", "1");
		whetherMap.put("否", "0");
		whetherMap.put("1", "1");
		whetherMap.put("0", "0");
		
		try {
			List<Map<String, List<String>>> list = ReadFiles.readFiles(entityMap);
			if(list != null && list.size() > 0){
				for (Map<String, List<String>> map : list) {
					Map<String,Object> saveMap = new HashMap<String,Object>();
					saveMap.put("group_id", SessionManager.getGroupId());
					saveMap.put("hos_id", SessionManager.getHosId());
					
					//过滤空行
					if("".equals(map.get("duty_code").get(1)) || "".equals(map.get("duty_name").get(1))){
						continue;
					}
					if("null".equals(map.get("duty_code").get(1)) || "null".equals(map.get("duty_name").get(1))){
						continue;
					}
					
					saveMap.put("duty_code", map.get("duty_code").get(1));
					saveMap.put("duty_name", map.get("duty_name").get(1));
					saveMap.put("spell_code", StringTool.toPinyinShouZiMu(map.get("duty_name").get(1)));
					saveMap.put("wbx_code", StringTool.toWuBi(map.get("duty_name").get(1)));
					saveMap.put("is_stop", whetherMap.get(map.get("is_stop").get(1)));
					saveMap.put("note", map.get("note").get(1));
					
					if(!"".equals(dutyMap.get(saveMap.get("duty_code"))) && dutyMap.get(saveMap.get("duty_code")) != null){
						failureMsg.append("<br/>职务编码: "+map.get("duty_code").get(1)+" 已存在; ");
						failureNum++;
						continue; 
					}
					
					if(!"".equals(dutyMap.get(saveMap.get("duty_name"))) && dutyMap.get(saveMap.get("duty_name")) != null){
						failureMsg.append("<br/>职务名称: "+map.get("duty_name").get(1)+" 已存在; ");
						failureNum++;
						continue;
					}
					
					/*HosDuty type = hosDutyInfoMapper.queryByCode(saveMap);
					
					if(type != null){
						failureMsg.append("<br/>类别代码: "+saveMap.get("duty_code")+" 已存在; ");
						failureNum++;
						continue;
					}*/
					//职务类别
					if("".equals(kindMap.get(map.get("kind_code").get(1))) || kindMap.get(map.get("kind_code").get(1)) == null){
						failureMsg.append("<br/>职务类别: "+map.get("kind_code").get(1)+" 不存在; ");
						failureNum++;
						continue;
					}else{
						saveMap.put("kind_code", kindMap.get(map.get("kind_code").get(1)));
					}
					//职务等级
					if("".equals(levelMap.get(map.get("duty_level_code").get(1))) || levelMap.get(map.get("duty_level_code").get(1)) == null){
						failureMsg.append("<br/>职务等级: "+map.get("duty_level_code")+" 不存在或已停用; ");
						failureNum++;
						continue;
					}else{
						saveMap.put("duty_level_code", levelMap.get(map.get("duty_level_code").get(1)));
					}
					
					successNum++;
					saveList.add(saveMap);
				}
				if(saveList.size() > 0){
					hosDutyInfoMapper.addBatch(saveList);
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
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	@Override
	public List<Map<String, Object>> queryHrDutyPrint(Map<String, Object> entityMap) throws DataAccessException {
		return hosDutyInfoMapper.queryHrDutyPrint(entityMap);
	}

}
