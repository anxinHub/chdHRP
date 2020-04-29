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
import com.chd.hrp.hr.dao.organize.HosStationLevelMapper;
import com.chd.hrp.hr.entity.orangnize.HosDutyLevel;
import com.chd.hrp.hr.entity.orangnize.HosStationLevel;
import com.chd.hrp.hr.service.organize.HosStationLevelService;
import com.github.pagehelper.PageInfo;

@Service("hosStationLevelService")
public class HosStationLevelServiceImpl implements HosStationLevelService {

	private static Logger logger = Logger.getLogger(HosStationLevelServiceImpl.class);
	//引入DAO操作
	@Resource(name = "hosStationLevelMapper")
	private final HosStationLevelMapper hosStationLevelMapper = null;
    
	@Resource(name = "hrSelectMapper")
	private final HrSelectMapper hrSelectMapper = null;
	
	/**
	 * 添加职务等级
	*/
	@Override
	public String addHrStationLevel(Map<String,Object> entityMap)throws DataAccessException{
		//获取对象职务等级
		try {
			List<HosStationLevel> list = (List<HosStationLevel>) hosStationLevelMapper.queryHrStationLevelByCode(entityMap);
			for (HosStationLevel level : list) {
				if (level != null) {
					if (level.getStation_level_code().equals(entityMap.get("station_level_code"))) {
						return "{\"error\":\"编码：" + level.getStation_level_code().toString() + "已存在.\"}";
					}
					if (level.getStation_level_name().equals(entityMap.get("station_level_name"))) {
						return "{\"error\":\"名称：" + level.getStation_level_name().toString() + "已存在.\"}";
					}

				}
			}
		
			int state = hosStationLevelMapper.add(entityMap);
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}
		
	}
	
	/**
	 * 更新职务等级
	*/
	@Override
	public String updateHrStationLevel(Map<String,Object> entityMap) throws DataAccessException{
		try {
			if ("1".equals(entityMap.get("is_flag"))) {
				List<HosStationLevel> list = (List<HosStationLevel>) hosStationLevelMapper.queryHrStationLevelByName(entityMap);
				if (list.size() > 0) {
					for (HosStationLevel level : list) {
						if (level.getStation_level_name().equals(entityMap.get("station_level_name"))) {
							return "{\"error\":\"名称：" + level.getStation_level_name().toString() + "已存在.\"}";
						}
					}
				}
			} 

			int state = hosStationLevelMapper.update(entityMap);
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";
			
		}catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}	
	}
	/**
	 * 根据编码查询
	 */
	@Override
	public HosStationLevel queryHrStationLevelByCode(Map<String, Object> entityMap) throws DataAccessException{
		return hosStationLevelMapper.queryDutyLevelByCode(entityMap);
	}
	
	/**
	 * 删除
	 */
	@Override
	public String deleteHrStationLevel(List<HosStationLevel> entityList) throws DataAccessException{
		try {
			if(entityList.size() > 0){
				hosStationLevelMapper.deleteBatchHrStationLevel(entityList);
				return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
			}else{
				return "{\"msg\":\"删除失败,没有要删除的数据！\",\"state\":\"false\"}";
			}
			
			
		}catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}	
	}
	
	/**
	 * 查询职务等级
	*/
	@Override
	@SuppressWarnings("unchecked")
	public String queryStationLevel(Map<String,Object> entityMap) throws DataAccessException{
		SysPage sysPage = new SysPage();
		sysPage = (SysPage) entityMap.get("sysPage");
		if (sysPage.getTotal()==-1){
			List<HosStationLevel> list = (List<HosStationLevel>)hosStationLevelMapper.queryStationLevel(entityMap);
			return ChdJson.toJson(list);
		}else{
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			List<HosStationLevel> list = (List<HosStationLevel>)hosStationLevelMapper.queryStationLevel(entityMap, rowBounds);
			//@SuppressWarnings("rawtypes")
			PageInfo page = new PageInfo(list);
			return ChdJson.toJson(list, page.getTotal());
		}
	}
	
	/**
	 * 导入数据
	 */
	@Override
	public String importDate(Map<String, Object> entityMap) throws DataAccessException {
		int successNum = 0;
		int failureNum = 0;
		StringBuilder failureMsg = new StringBuilder();
		List<Map<String,Object>> saveList = new ArrayList<Map<String,Object>>();
		Map<String, Object> whetherMap = new HashMap<String, Object>();
		whetherMap.put("是", "1");
		whetherMap.put("否", "0");
		whetherMap.put("1", "1");
		whetherMap.put("0", "0");
		entityMap.put("group_id", SessionManager.getGroupId());
		entityMap.put("hos_id", SessionManager.getHosId());
		
		/**
		 * 职务等级
		 */
		List<Map<String,Object>> levelList = hosStationLevelMapper.queryStationLevelList(entityMap);
		Map<String,Object> levelMap = new HashMap<String,Object>();
		for(Map<String,Object> level : levelList){
			levelMap.put(level.get("id").toString(), level.get("id").toString());
			levelMap.put(level.get("text").toString(), level.get("id").toString());
		}
		
		try {
			List<Map<String, List<String>>> list = ReadFiles.readFiles(entityMap);
			if(list != null && list.size() > 0){
				for (Map<String, List<String>> map : list) {
					Map<String,Object> saveMap = new HashMap<String,Object>();
					saveMap.put("group_id", SessionManager.getGroupId());
					saveMap.put("hos_id", SessionManager.getHosId());
					saveMap.put("copy_code", SessionManager.getCopyCode());
					
					//过滤空行
					if("".equals(map.get("station_level_code").get(1)) || "".equals(map.get("station_level_name").get(1))){
						continue;
					}
					
					if("null".equals(map.get("station_level_code").get(1)) || "null".equals(map.get("station_level_name").get(1))){
						continue;
					}
					saveMap.put("station_level_code", map.get("station_level_code").get(1));
					saveMap.put("station_level_name", map.get("station_level_name").get(1));
					saveMap.put("spell_code", StringTool.toPinyinShouZiMu(map.get("station_level_name").get(1)));
					saveMap.put("wbx_code", StringTool.toWuBi(map.get("station_level_name").get(1)));
					saveMap.put("is_stop", whetherMap.get(map.get("is_stop").get(1)));
					saveMap.put("note", map.get("note").get(1));
					
					if(!"".equals(levelMap.get(saveMap.get("station_level_code"))) && levelMap.get(saveMap.get("station_level_code")) != null){
						failureMsg.append("<br/>岗位等级编码: "+saveMap.get("station_level_code")+" 已存在; ");
						failureNum++;
						continue; 
					}
					
					if(!"".equals(levelMap.get(saveMap.get("station_level_name"))) && levelMap.get(saveMap.get("station_level_name")) != null){
						failureMsg.append("<br/>岗位等级名称: "+saveMap.get("station_level_name")+" 已存在; ");
						failureNum++;
						continue;
					}
					
					/*HosStationLevel type = hosStationLevelMapper.queryDutyLevelByCode(saveMap);
					if(type != null){
						failureMsg.append("<br/>类别代码 "+map.get("station_level_code").get(1)+" 已存在; ");
						failureNum++;
						continue;
					}*/
					successNum++;
					saveList.add(saveMap);
				}
				if(saveList.size() > 0){
					hosStationLevelMapper.addBatch(saveList);
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

	@Override
	public List<HosStationLevel> queryListLevel(List<HosStationLevel> listVo) throws DataAccessException  {
		return hosStationLevelMapper.queryListLevel(listVo);
	}

	@Override
	public List<Map<String, Object>> queryStationLevelByPrint(
			Map<String, Object> entityMap) throws DataAccessException {

		 List<Map<String,Object>> list = ChdJson.toListLower(hosStationLevelMapper.queryStationLevelByPrint(entityMap));
		 return list;
	}

	

	
	

}
