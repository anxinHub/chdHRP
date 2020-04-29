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
import com.chd.hrp.hr.dao.organize.HosDutyLevelMapper;
import com.chd.hrp.hr.entity.orangnize.HosDutyLevel;
import com.chd.hrp.hr.service.organize.HosDutyLevelService;
import com.github.pagehelper.PageInfo;

@Service("hosDutyLevelService")
public class HosDutyLevelServiceImpl implements HosDutyLevelService {
	private static Logger logger = Logger.getLogger(HosDutyLevelServiceImpl.class);
	//引入DAO操作
	@Resource(name = "hosDutyLevelMapper")
	private final HosDutyLevelMapper hosDutyLevelMapper = null;
	
	@Resource(name = "hrSelectMapper")
	private final HrSelectMapper hrSelectMapper = null;
	
    
	/**
	 * 添加职务等级
	*/
	@Override
	public String addHrDutyLevel(Map<String,Object> entityMap)throws DataAccessException{
		//获取对象职务等级
		List<HosDutyLevel> list = hosDutyLevelMapper.queryDutyLevelByCode(entityMap);

		if (list.size() > 0) {
			for (HosDutyLevel hrDutyLevel : list) {
				if (hrDutyLevel.getDuty_level_code().equals(entityMap.get("duty_level_code"))) {
					return "{\"error\":\"编码：" + hrDutyLevel.getDuty_level_code().toString() + "已存在.\"}";
				}
				if (hrDutyLevel.getDuty_level_name().equals(entityMap.get("duty_level_name"))) {
					return "{\"error\":\"名称：" + hrDutyLevel.getDuty_level_name().toString() + "已存在.\"}";
				}
			}
		}
		
		try {
			int state = hosDutyLevelMapper.add(entityMap);
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
	public String updateHrDutyLevel(Map<String,Object> entityMap)throws DataAccessException{
		try {

				//获取对象职务等级
				List<HosDutyLevel> list = hosDutyLevelMapper.queryDutyLevelByName(entityMap);

				if (list.size() > 0) {
					for (HosDutyLevel hrDutyLevel : list) {
						if (hrDutyLevel.getDuty_level_name().equals(entityMap.get("duty_level_name"))) {
							return "{\"error\":\"名称：" + hrDutyLevel.getDuty_level_name().toString() + "已存在.\"}";
						}
					}
				}
			
			
			int state = hosDutyLevelMapper.update(entityMap);
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";
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
	public String queryHrDutyLevel(Map<String,Object> entityMap) throws DataAccessException{
		SysPage sysPage = new SysPage();
		sysPage = (SysPage) entityMap.get("sysPage");
		if (sysPage.getTotal()==-1){
			List<HosDutyLevel> list = (List<HosDutyLevel>)hosDutyLevelMapper.query(entityMap);
			return ChdJson.toJson(list);
		}else{
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			List<HosDutyLevel> list = (List<HosDutyLevel>)hosDutyLevelMapper.query(entityMap, rowBounds);
			@SuppressWarnings("rawtypes")
			PageInfo page = new PageInfo(list);
			return ChdJson.toJson(list, page.getTotal());
		}
	}
	
	/**
	 * 获取对象职务等级<BR> 
	*/
	@Override
	public HosDutyLevel queryByCode(Map<String,Object> entityMap)throws DataAccessException{
		return hosDutyLevelMapper.queryByCode(entityMap);
	}
	
	/**
	 * 删除
	 */
	@Override
	public String deleteHrDutyLevel(List<HosDutyLevel> entityList)throws DataAccessException{
		try {
			hosDutyLevelMapper.deleteBatchHrDutyLevel(entityList);
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		}catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
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
		List<Map<String,Object>> levelList = hosDutyLevelMapper.queryByCodeLevel(entityMap);
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
					if("".equals(map.get("duty_level_code").get(1)) || "".equals(map.get("duty_level_name").get(1))){
						continue;
					}
					
					if("null".equals(map.get("duty_level_code").get(1)) || "null".equals(map.get("duty_level_name").get(1))){
						continue;
					}
					saveMap.put("duty_level_code", map.get("duty_level_code").get(1));
					saveMap.put("duty_level_name", map.get("duty_level_name").get(1));
					saveMap.put("spell_code", StringTool.toPinyinShouZiMu(map.get("duty_level_name").get(1)));
					saveMap.put("wbx_code", StringTool.toWuBi(map.get("duty_level_name").get(1)));
					saveMap.put("is_stop", whetherMap.get(map.get("is_stop").get(1)));
					saveMap.put("note", map.get("note").get(1));
					
					if(!"".equals(levelMap.get(saveMap.get("duty_level_code"))) && levelMap.get(saveMap.get("duty_level_code")) != null){
						failureMsg.append("<br/>职务等级编码: "+saveMap.get("duty_level_code")+" 已存在; ");
						failureNum++;
						continue; 
					}
					
					if(!"".equals(levelMap.get(saveMap.get("duty_level_name"))) && levelMap.get(saveMap.get("duty_level_name")) != null){
						failureMsg.append("<br/>职务等级名称: "+saveMap.get("duty_level_name")+" 已存在; ");
						failureNum++;
						continue;
					}
					
					/*HosDutyLevel type = hosDutyLevelMapper.queryByCode(saveMap);
					if(type != null){
						failureMsg.append("<br/>类别代码 "+map.get("duty_level_code").get(1)+" 已存在; ");
						failureNum++;
						continue;
					}*/
					successNum++;
					saveList.add(saveMap);
				}
				if(saveList.size() > 0){
					hosDutyLevelMapper.addBatch(saveList);
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
	public List<HosDutyLevel> queryListDuty(List<HosDutyLevel> listVo) {
		return hosDutyLevelMapper.queryListDuty(listVo);
	}

	@Override
	public List<Map<String, Object>> queryDutyLevelByPrint(
			Map<String, Object> entityMap) throws DataAccessException {
		 List<Map<String,Object>> list = ChdJson.toListLower(hosDutyLevelMapper.queryDutyLevelByPrint(entityMap));
		 return list;
	}
}
