package com.chd.hrp.ass.serviceImpl.repair.myrepair;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import com.alibaba.fastjson.JSONArray;
import com.chd.base.SessionManager;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.hrp.ass.dao.repair.myrepair.AssMyRepairMapper;
import com.chd.hrp.ass.dao.repair.repaircentre.AssRepairCentreMapper;
import com.chd.hrp.ass.service.repair.myrepair.AssMyRepairService;

@Service("assMyRepairService")
public class AssMyRepairServiceImpl implements AssMyRepairService{

	@Resource(name = "assMyRepairMapper")
	private final AssMyRepairMapper assMyRepairMapper=null;
	
	@Override
	public String add(Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String addBatch(List<Map<String, Object>> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String update(Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String updateBatch(List<Map<String, Object>> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String delete(Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String deleteBatch(List<Map<String, Object>> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String addOrUpdate(Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String query(Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> T queryByCode(Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> T queryByUniqueness(Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<?> queryExists(Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String queryAssMyRepairCenter(Map<String, Object> mapVo) {
		
		List<Map<String,Object>> list=assMyRepairMapper.queryAssMyRepairCenter(mapVo);
		
		return ChdJson.toJsonLower(list);
	}


@Override
	public Map<String, Object> submitRepScore(Map<String, Object> mapVo) {
		try {
			String rep_code= "";
			 Map<String,Object> resMap = new HashMap<String, Object>();
			 assMyRepairMapper.submitRepScore(mapVo);
			resMap.put("state", true);
			resMap.put("msg", "成功");
			return resMap;
		} catch (Exception e) {
			throw new SysException(e);
		}
	}
	  
	@Override
	public String queryTimeLineRender(Map<String, Object> mapVo) {
		List<Map<String,Object>>list = assMyRepairMapper.queryTimeLineRender(mapVo);
		return JSONArray.toJSONString(list);
	}

  
	@Override
	public Map<String, Object> queryCardDataByCode(Map<String, Object> mapVo) {
		
		Map<String, Object> map =assMyRepairMapper.queryCardDataByCode(mapVo);
		
		return map;
	}
	@Override
	public String queryImgUrlByRepCode(Map<String, Object> mapVo) {
		List<Map<String,Object>> list =assMyRepairMapper.queryImgUrlByRepCode(mapVo);
		return JSONArray.toJSONString(list);
	}

	 
	@Override
	public Map<String, Object> queryAssRepairByCode(Map<String, Object> mapVo) {
		
		return assMyRepairMapper.queryAssRepairByCode(mapVo);
	}

	@Override
	public String queryRepTeamUser(Map<String, Object> mapVo) {
		List<Map<String,Object>> list = assMyRepairMapper.queryRepTeamUser(mapVo);
		return JSONArray.toJSONString(list);
	}
	/**
	 * 接单  更新状态为 3 维修中
	 */
	@Override
	public String repairReceiving(Map<String, Object> mapVo) {
		String[] repCodes= mapVo.get("rep_code").toString().split(",");
		StringBuilder  repCode=new StringBuilder();
		for (String string : repCodes) {
			repCode.append(string+",");
		}
		repCode.setCharAt(repCode.length()-1, ' ');
		mapVo.put("rep_code", repCode);
		mapVo.put("state", 2);
		mapVo.put("task_user", SessionManager.getUserId());
		try {
			 
			List<Map<String,Object>> task_rep_codes = assMyRepairMapper.existsAssRepairTaskState(mapVo);
			if(task_rep_codes!=null && task_rep_codes.size()>0){
				for (Map<String, Object> map : task_rep_codes) {
					if(!"2".equals(map.get("STATE").toString())){
						String rep_code = map.get("REP_CODE").toString();
						return"{\"error\":\""+rep_code+"不是'待维修'状态\",\"state\":\"false\"}";
					}
					
				}
			}
			mapVo.put("state", 3);
			//更新单据表
			assMyRepairMapper.updateAssRepairState(mapVo);
			//添加任务表
			assMyRepairMapper.addAssRepTask(mapVo);
			
			return "{\"msg\":\"成功\",\"state\":\"true\"}";
		} catch (Exception e) {
			throw new SysException(e);
		}
	}
	
	/**
	 * 退单  更新状态为 2待修
	 */
	@Override
	public String repairBack(Map<String, Object> mapVo) {
		String[] repCodes= mapVo.get("rep_code").toString().split(",");
		StringBuilder  repCode=new StringBuilder();
		for (String string : repCodes) {
			repCode.append(string+",");
		}
		repCode.setCharAt(repCode.length()-1, ' ');
		mapVo.put("rep_code", repCode);
		mapVo.put("state", 3);
		mapVo.put("task_user", SessionManager.getUserId());
		try {
			 
			List<Map<String,Object>> task_rep_codes = assMyRepairMapper.existsAssRepairTaskState(mapVo);
			if(task_rep_codes!=null && task_rep_codes.size()>0){
				for (Map<String, Object> map : task_rep_codes) {
					if(!"3".equals(map.get("STATE").toString())){
						String rep_code = map.get("REP_CODE").toString();
						return"{\"error\":\""+rep_code+"不是'维修中'状态\",\"state\":\"false\"}";
					}
					
				}
			}
			mapVo.put("state", 2);
			mapVo.put("order_time", "");
			//更新单据表
			assMyRepairMapper.updateAssRepairStateBack(mapVo);
			//添加任务表
			assMyRepairMapper.deleteAssRepTask(mapVo);
			
			return "{\"msg\":\"成功\",\"state\":\"true\"}";
		} catch (Exception e) {
			throw new SysException(e);
		}
	}
	
	/**
	 * 转单
	 */
	@Override
	public String updateRepUser(Map<String, Object> mapVo) {
		String[] repCodes= mapVo.get("rep_code").toString().split(",");
		StringBuilder  repCode=new StringBuilder();
		for (String string : repCodes) {
			repCode.append(string+",");
		}
		repCode.setCharAt(repCode.length()-1, ' ');
		mapVo.put("rep_code", repCode);
		mapVo.put("state", 3);
		mapVo.put("task_user", SessionManager.getUserId());
		try {
			List<Map<String,Object>> task_rep_codes = assMyRepairMapper.existsAssRepairTaskState(mapVo);
			if(task_rep_codes!=null && task_rep_codes.size()>0){
				for (Map<String, Object> map : task_rep_codes) {
					if(!"3".equals(map.get("STATE").toString())){
						String rep_code = map.get("REP_CODE").toString();
						return"{\"error\":\""+rep_code+"不是'维修中'状态\",\"state\":\"false\"}";
					}
					
				}
			}
			mapVo.put("state", 2);
			mapVo.put("myUserId",SessionManager.getUserId());
			//对我的单据添加一条状态为已转单的数据 state=4
			assMyRepairMapper.addAssRepTaskByMyUserId(mapVo);
			//添加一条所选工程师的状态为待维修的任务表数据 state = 2
			assMyRepairMapper.addAssRepTask(mapVo);
			//更新单据表 state= 2
			assMyRepairMapper.updateAssRepairSend(mapVo);
			return "{\"msg\":\"成功\",\"state\":\"true\"}";
		} catch (Exception e) {
			throw new SysException(e);
		}
	}
	/**
	 * 误报  单据结束维系
	 */
	@Override
	public String updateEndRepUser(Map<String, Object> mapVo) {
		String[] repCodes= mapVo.get("rep_code").toString().split(",");
		StringBuilder  repCode=new StringBuilder();
		for (String string : repCodes) {
			repCode.append(string+",");
		}
		repCode.setCharAt(repCode.length()-1, ' ');
		mapVo.put("rep_code", repCode);
		mapVo.put("state", "3");
		mapVo.put("task_user", SessionManager.getUserId());
		try {
			List<Map<String,Object>> task_rep_codes = assMyRepairMapper.existsAssRepairTaskState(mapVo);
			if(task_rep_codes!=null && task_rep_codes.size()>0){
				for (Map<String, Object> map : task_rep_codes) {
					if(!"3".equals(map.get("STATE").toString())){
						String rep_code = map.get("REP_CODE").toString();
						return"{\"error\":\""+rep_code+"不是'维修中'状态\",\"state\":\"false\"}";
					}
				}
			}
			mapVo.put("state", 6);
			//更新任务表
			assMyRepairMapper.addAssRepTask(mapVo);
			//更新单据表
			assMyRepairMapper.updateAssRepairEndState(mapVo);
			return  "{\"msg\":\"成功\",\"state\":\"true\"}";
		} catch (Exception e) {
			throw new SysException(e);
		}
	}

	@Override
	public String queryMatInvDict(Map<String, Object> mapVo) {
		List<Map<String,Object>> list =assMyRepairMapper.queryMatInvDict(mapVo);
		return ChdJson.toJsonLower(list);
	}
	/**
	 * 完成维修
	 */
	@Override
	public String updateEndRepairState(Map<String, Object> mapVo) {
		String[] repCodes= mapVo.get("rep_code").toString().split(",");
		StringBuilder  repCode=new StringBuilder();
		for (String string : repCodes) {
			repCode.append(string+",");
		}
		repCode.setCharAt(repCode.length()-1, ' ');
		mapVo.put("rep_code", repCode);
		mapVo.put("state", "3");
		mapVo.put("task_user", SessionManager.getUserId());
		
		try {
			List<Map<String,Object>> task_rep_codes = assMyRepairMapper.existsAssRepairTaskState(mapVo);
				if(task_rep_codes!=null && task_rep_codes.size()>0){
					for (Map<String, Object> map : task_rep_codes) {
						if(!"3".equals(map.get("STATE").toString())){
							String rep_code = map.get("REP_CODE").toString();
							return"{\"error\":\""+rep_code+"不是'维修中'状态\",\"state\":\"false\"}";
						}
						
					}
				}
				if("true".equals(mapVo.get("is_card").toString())){
					mapVo.put("is_card", 1);
				}else{
					mapVo.put("is_card", 0);
				}
				if("true".equals(mapVo.get("is_base").toString())){
					mapVo.put("is_base", 1);
				}else{
					mapVo.put("is_base", 0);
				}
				  
				mapVo.put("state", 5);
				//更新任务表
				assMyRepairMapper.addAssRepTask(mapVo);
				//更新单据表
				assMyRepairMapper.updateEndRepairState(mapVo);
				if(mapVo.get("invdata")!=null && !mapVo.get("invdata").toString().equals("")){
					String[] invData= mapVo.get("invdata").toString().split(",");
					for (String string : invData) {
						Map<String,Object> invMap = new HashMap<String, Object>();
						invMap.put("group_id", SessionManager.getGroupId());
						invMap.put("hos_id", SessionManager.getHosId());
						invMap.put("copy_code", SessionManager.getCopyCode());
						invMap.put("rep_code", mapVo.get("rep_code").toString().trim());
						invMap.put("inv_id", Integer.parseInt(string.split(" ")[0]));
						invMap.put("inv_no", Integer.parseInt(string.split(" ")[1]));
						invMap.put("amount", Double.parseDouble(string.split(" ")[2]));
						assMyRepairMapper.addRepairInv(invMap);
					}
				}
		} catch (Exception e) {
			
			throw new SysException(e);
		}
		return "{\"msg\":\"成功\",\"state\":\"true\"}";
	}
 
	public Map<String, Object> queryAssRepairCenterPrint(Map<String, Object> mapVo) {
		Map<String, Object> result = new HashMap<String, Object>();
		WebApplicationContext context = ContextLoader.getCurrentWebApplicationContext();
		AssRepairCentreMapper assRepairCentreMapper = (AssRepairCentreMapper) context.getBean("assRepairCentreMapper");
		if ("1".equals(String.valueOf(mapVo.get("p_num")))) {
			mapVo.put("is_list", true);
			List<Map<String, Object>> map = assRepairCentreMapper.queryAssRepairCenterPrint(mapVo);
			result.put("main", map);
		} else {
			mapVo.put("is_list", false);
			Map<String, Object> map = assRepairCentreMapper.queryAssRepairCenterPrint(mapVo).get(0);
			result.put("main", map);
		}
		return result;
	
	}

}
