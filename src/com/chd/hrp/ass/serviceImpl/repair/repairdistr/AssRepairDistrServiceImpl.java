package com.chd.hrp.ass.serviceImpl.repair.repairdistr;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import com.alibaba.fastjson.JSONArray;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.base.util.UUIDLong;
import com.chd.hrp.ass.dao.repair.repairdistr.AssRepairDistrMapper;
import com.chd.hrp.ass.dao.repair.repreportcentre.AssRepReportCentreMapper;
import com.chd.hrp.ass.service.repair.repairdistr.AssRepairDistrService;
import com.chd.hrp.ass.serviceImpl.repair.AssInvArrtServiceImpl;
import com.chd.hrp.mat.dao.base.MatCommonMapper;
@Service("assRepairDistrService")
public class AssRepairDistrServiceImpl implements  AssRepairDistrService{
	
	private static Logger logger = Logger.getLogger(AssInvArrtServiceImpl.class);
	//引入DAO操作
	@Resource(name = "assRepairDistrMapper")
	private final AssRepairDistrMapper assRepairDistrMapper = null;
	@Resource(name = "matCommonMapper")
	private final MatCommonMapper matCommonMapper = null;
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
	public String queryAssRepairByState(Map<String, Object> mapVo) {
		List<Map<String,Object>> list = assRepairDistrMapper.queryAssRepairByState(mapVo);
		return ChdJson.toJsonLower(list);
	}
	@Override
	public Map<String, Object> queryAssRepairByCode(Map<String, Object> mapVo) {
		
		return assRepairDistrMapper.queryAssRepairByCode(mapVo);
	}
	/**
	 * 回退
	 */
	@Override
	public String backAssRepair(Map<String, Object> mapVo) {
		String[] repCodes= mapVo.get("rep_code").toString().split(",");
		StringBuilder  repCode=new StringBuilder();
		for (String string : repCodes) {
			repCode.append(string+",");
		}
		repCode.setCharAt(repCode.length()-1, ' ');
		mapVo.put("rep_code", repCode);
		
		try {
			//检查单据发送状态
			List<String> repCodess=assRepairDistrMapper.queryAssRepairExist(mapVo);
			if(repCodess!=null && repCodess.size()>0){
				StringBuilder  resp=new StringBuilder();
				List<String> list=new ArrayList<String>();
				for (String repCo : repCodess) {
					for (int i = 0; i < repCodes.length; i++) {
						if(repCo.equals(repCodes[i])){
							list.add(repCo);
							break;
						}
					}
				}
				for (String string : list) {
					resp.append(string+",");
				}
				return "{\"error\":\""+resp+"单据已被派单！"+"\",\"state\":\"false\"}";
			}
			//更新单据表
			assRepairDistrMapper.backAssRepair(mapVo);
			
			return "{\"msg\":\"成功\",\"state\":\"true\"}";
		} catch (Exception e) {
			throw new SysException(e);
		}
	}
	@Override
	public String queryImgUrlByRepCode(Map<String, Object> mapVo) {
		List<Map<String,Object>> list =assRepairDistrMapper.queryImgUrlByRepCode(mapVo);
		return JSONArray.toJSONString(list);
	}
	@Override
	public String updateRepairDistrState(Map<String, Object> mapVo) {
		
		Map<String,Object> resMap = new HashMap<String, Object>();
		try {
			int amount=0;
			String[] repNo=mapVo.get("rep_code").toString().split(",");
			for(String rep:repNo){
					//添加派单数据到工作表
					Map<String,Object> repDataMap = new HashMap<String, Object>();
					repDataMap.putAll(mapVo);
					repDataMap.put("rep_code",rep);
					repDataMap.put("task_user",mapVo.get("user_id"));
					repDataMap.put("task_id", UUIDLong.absStringUUID());
					repDataMap.put("state",2); 
					int rc=assRepairDistrMapper.addAssRepairTask(repDataMap);
					if(rc>0){
						amount=amount+1;
					}
				}
			mapVo.put("amount",amount);
			Map<String,Object> uesrMap = assRepairDistrMapper.queryUserWorkByUserId(mapVo);
			if(uesrMap!=null){
				//更新工作量表  将工程师工作量+1
				assRepairDistrMapper.updateUserWorkByUserId(mapVo);
			}else{
				//工程师没有工作量时添加工程师工作量
				assRepairDistrMapper.addUserWork(mapVo);
			}
			//更新维修单为已发送状态
			assRepairDistrMapper.updateAssRepairSend(mapVo);
			
			 
			return "{\"msg\":\"成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			throw new SysException(e);
		}
	}
	
	public Map<String, Object> queryAssRepairCenterPrint(Map<String, Object> mapVo) {
		Map<String, Object> result = new HashMap<String, Object>();
		WebApplicationContext context = ContextLoader.getCurrentWebApplicationContext();
		AssRepReportCentreMapper assRepairCentreMapper = (AssRepReportCentreMapper) context.getBean("assRepReportCentreMapper");
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
