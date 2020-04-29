package com.chd.hrp.hip.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.chd.base.BaseController;
import com.chd.base.SessionManager;
import com.chd.base.util.DateUtil;
import com.chd.base.util.UUIDLong;
import com.chd.hrp.hip.entity.HipDataType;
import com.chd.hrp.hip.entity.HipSyncLog;
import com.chd.hrp.hip.service.DataTypeService;
import com.chd.hrp.hip.service.cache.CacheDataService;


@Controller
@RequestMapping(value="/hrp/hip/dataType")
public class DataTypeController extends BaseController {
	private static Logger logger = Logger.getLogger(DataTypeController.class);

	@Resource(name = "dataTypeService")
	private final DataTypeService dataTypeService = null;

	@Resource(name = "cacheDataService")
	private final CacheDataService cacheDataService = null;
	

	@RequestMapping(value = "/mainPage", method = RequestMethod.GET)
	public String mainPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		return "hrp/hip/dataType/main";
	}
	
	@RequestMapping(value = "/addPage", method = RequestMethod.GET)
	public String addPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		return "hrp/hip/dataType/add";
	}
	
	@RequestMapping(value = "/queryPage", method = RequestMethod.GET)
	public String queryPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		return "hrp/hip/dataType/query";
	}
	
	@RequestMapping(value = "/logPage", method = RequestMethod.GET)
	public String logPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mode.addAttribute("type_id", mapVo.get("type_id"));
		return "hrp/hip/dataType/log";
	}
	
	@RequestMapping(value = "/decodeSetPage", method = RequestMethod.GET)
	public String decodeSetPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mode.addAttribute("type_id", mapVo.get("type_id"));
		return "hrp/hip/dataType/decodeSet";
	}

	@RequestMapping(value = "/queryHipDataType", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryHipDataType(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null || "".equals(String.valueOf(mapVo.get("group_id")))) {mapVo.put("group_id", SessionManager.getGroupId());}

		if (mapVo.get("hos_id") == null || "".equals(String.valueOf(mapVo.get("hos_id")))) {mapVo.put("hos_id", SessionManager.getHosId());}


		String str = dataTypeService.queryHipDataType(getPage(mapVo));

		return JSONObject.parseObject(str);

	}

	@RequestMapping(value = "/queryHrpTable", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryHrpTable(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null || "".equals(String.valueOf(mapVo.get("group_id")))) {mapVo.put("group_id", SessionManager.getGroupId());}

		if (mapVo.get("hos_id") == null || "".equals(String.valueOf(mapVo.get("hos_id")))) {mapVo.put("hos_id", SessionManager.getHosId());}

		if (mapVo.get("copy_code") == null || "".equals(String.valueOf(mapVo.get("copy_code")))) {mapVo.put("copy_code", SessionManager.getCopyCode());}
		

		String str = dataTypeService.queryHrpTable(getPage(mapVo));

		return JSONObject.parseObject(str);

	}
	
	
	@RequestMapping(value = "/queryHrpTableColumn", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryHrpTableColumn(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null || "".equals(String.valueOf(mapVo.get("group_id")))) {mapVo.put("group_id", SessionManager.getGroupId());}

		if (mapVo.get("hos_id") == null || "".equals(String.valueOf(mapVo.get("hos_id")))) {mapVo.put("hos_id", SessionManager.getHosId());}

		if (mapVo.get("copy_code") == null || "".equals(String.valueOf(mapVo.get("copy_code")))) {mapVo.put("copy_code", SessionManager.getCopyCode());}
		

		String str = dataTypeService.queryHrpTableColumn(mapVo);

		return JSONObject.parseObject(str);

	}
	
	@RequestMapping(value = "/queryHipSyncLog", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryHipSyncLog(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		String str = dataTypeService.queryHipSyncLog(getPage(mapVo));

		return JSONObject.parseObject(str);

	}
	
	@RequestMapping(value = "/clearHipSyncLog", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> clearHipSyncLog(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		String str = dataTypeService.clearHipSyncLog(getPage(mapVo));

		return JSONObject.parseObject(str);

	}
	
	@RequestMapping(value = "/queryHipSyncLogByTypeId", method = RequestMethod.POST)
	@ResponseBody
	public String queryHipSyncLogByTypeId(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());

		HipSyncLog hipSyncLog = dataTypeService.queryHipSyncLogByTypeId(mapVo);

		return JSONObject.toJSON(hipSyncLog).toString();

	}
	
	
	@RequestMapping(value = "/runJobPage", method = RequestMethod.GET)
	public String runJobPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		return "hrp/hip/dataType/runJob";
	}
	
	/**
	 * 多数据源同步数据
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/syncData", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> syncData(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		//查询到要同步的SQL
		Map<String, Object> reMap=new HashMap<String, Object>();
		 
		if(mapVo.get("type_id")==null || "".equals(mapVo.get("type_id").toString())){
			reMap.put("error", "没有要同步的业务");
			return reMap;
		}
				
		List<HipDataType> list=dataTypeService.queryHipDataTypeBySql(mapVo);
		if(list==null || list.size()==0){
			reMap.put("error", "没有查询到要同步的SQL");
			return reMap;
		}
		
		String usreCode=SessionManager.getUserCode();
		String userName=SessionManager.getUserName();
		if(null == usreCode){
			usreCode="admin";
			userName="系统管理员";
		}
		
		int type=0;
		if(mapVo.get("isAuto")!=null && !"".equals(mapVo.get("isAuto"))){
			type=1;
		}
		
		List<Map<String, Object>> logList=new ArrayList<Map<String,Object>>(); 
		Map<String, Object> cacheMap=null;
		Map<String, Object> logMap=null;
		StringBuffer note=null;
		
		int isOk=0;
		for(HipDataType dataType: list){
			if(dataType.getQ_sql()==null || "".equals(dataType.getQ_sql())){
				continue;
			}	
				
			//日志参数：
			//日志格式：2019-09-05 09:42:00，开始同步：[cache] [1,15,001] [按日期同步,增量同步] [med_type_code] [ACC_MED_TYPE_HIS]
			logMap=new HashMap<String, Object>();
			note=new StringBuffer();
			Date startData=DateUtil.getCurrenDate();
			note.append(DateUtil.dateToString(startData)+", 开始同步 ["+dataType.getSource_code()+"]");
			note.append(" ["+dataType.getDgroup_id()+", "+dataType.getDhos_id()+", "+dataType.getDcopy_code()+"]");
			note.append(" ["+(dataType.getData_type()==1?"按日期取数":"无")+", "+(dataType.getSync_type()==0?"删除同步":"增量同步")+"]");
			note.append(" ["+dataType.getTo_table()+"]");
			note.append(" ["+dataType.getPk_col()+"]");
			
			logMap.put("log_id", UUIDLong.absStringUUID());
			logMap.put("group_id", dataType.getGroup_id());
			logMap.put("hos_id", dataType.getHos_id());
			logMap.put("start_time", startData);
			logMap.put("type_id", dataType.getType_id());
			logMap.put("user_name", usreCode+" "+userName);
			logMap.put("type", type);
			logMap.put("state", 0);
			
			//查询参数
			cacheMap=new HashMap<String, Object>();
			String sql=dataType.getQ_sql();
			note.append("<br>"+DateUtil.dateToString(DateUtil.getCurrenDate())+", 查询数据");
			
			if(mapVo.get("begin_date")!=null && !"".equals(mapVo.get("begin_date").toString())){
				sql=sql.replace("@begin_date", "'"+mapVo.get("begin_date").toString()+"'");
				note.append(" ["+mapVo.get("begin_date").toString());
				
			}
			if(mapVo.get("end_date")!=null && !"".equals(mapVo.get("end_date").toString())){
				sql=sql.replace("@end_date", "'"+mapVo.get("end_date").toString()+"'");
				note.append(", "+mapVo.get("end_date").toString()+"]");
			}
			cacheMap.put("sql", sql);
			
			
			try{
				
				//执行cache查询sql
				List<Map<String, Object>> cacheList=cacheDataService.queryCacheSql(cacheMap);
				if(cacheList==null || cacheList.size()==0){
					isOk++;
					note.append(" [0条]");
					continue;
				}
				
				//保存HRP数据
				note.append(" ["+cacheList.size()+"条]");
				String reNote=dataTypeService.saveHrpData(mapVo,dataType, cacheList);
				note.append("<br>"+reNote);
				isOk++;
				
			}catch(Exception e){
				logger.error(e.getMessage(),e);
				logMap.put("state", 1);
				note.append("<br>"+e.getMessage());
				
			}finally{
				logMap.put("note", note.toString());
				logMap.put("end_time", DateUtil.getCurrenDate());
				logList.add(logMap);
			}
				
		}
		
		//保存日志
		if(logList!=null && logList.size()>0){
			dataTypeService.saveHipSyncLog(logList);
		}
		
		reMap.put("msg", "共"+list.size()+"条<br>执行成功："+isOk+"条<br>执行失败："+(list.size()-isOk)+"条");
		return reMap;

	}
	
	
	@RequestMapping(value = "/saveHipDataType", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveHipDataType(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		String re_json = dataTypeService.saveHipDataType(mapVo);

		return JSONObject.parseObject(re_json);
	}
	
	

	
	@RequestMapping(value = "/updatePage", method = RequestMethod.GET)
	public String updatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {


		HipDataType hipDataType = new HipDataType();

		hipDataType = dataTypeService.queryHipDataTypeById(mapVo);

		mode.addAttribute("group_id", hipDataType.getGroup_id());
		mode.addAttribute("hos_id", hipDataType.getHos_id());
		mode.addAttribute("dgroup_id", hipDataType.getDgroup_id());
		mode.addAttribute("dhos_id", hipDataType.getDhos_id());
		mode.addAttribute("dcopy_code", hipDataType.getDcopy_code());
		mode.addAttribute("data_type", hipDataType.getData_type());
		mode.addAttribute("sync_type", hipDataType.getSync_type());
		mode.addAttribute("type_id", hipDataType.getType_id());
		mode.addAttribute("type_code", hipDataType.getType_code());
		mode.addAttribute("type_name", hipDataType.getType_name());
		mode.addAttribute("source_code", hipDataType.getSource_code());
		mode.addAttribute("source_name", hipDataType.getSource_name());
		mode.addAttribute("mod_code", hipDataType.getMod_code());
		mode.addAttribute("to_table", hipDataType.getTo_table());
		mode.addAttribute("q_sql", hipDataType.getQ_sql());
		mode.addAttribute("is_stop", hipDataType.getIs_stop());
		mode.addAttribute("note", hipDataType.getNote());
		mode.addAttribute("pk_col", hipDataType.getPk_col());
		
		mode.addAttribute("is_group", hipDataType.getIs_group());
		mode.addAttribute("is_hos", hipDataType.getIs_hos());
		mode.addAttribute("is_copy", hipDataType.getIs_copy());
		
		mode.addAttribute("dgroup_name", hipDataType.getDgroup_name());
		mode.addAttribute("dhos_name", hipDataType.getDhos_name());
		mode.addAttribute("dcopy_name", hipDataType.getDcopy_name());
		
		return "hrp/hip/dataType/update";
	}
	
	@RequestMapping(value = "/deleteHipDataType", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteHipDataType(@RequestParam(value = "ParamVo") String paramVo, Model mode)
			throws Exception {

		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();
		for (String id : paramVo.split(",")) {

			Map<String, Object> mapVo = new HashMap<String, Object>();

			String[] ids = id.split("@");

			// 表的主键
			mapVo.put("type_id", ids[1]);
			
			
			listVo.add(mapVo);
		}
		

		String re_json = dataTypeService.deleteHipDataType(listVo);

		return JSONObject.parseObject(re_json);

	}


	@RequestMapping(value = "/queryHipDataDecode", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryHipDataDecode(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());

		String str = dataTypeService.queryHipDataDecode(mapVo);

		return JSONObject.parseObject(str);

	}
	
	@RequestMapping(value = "/addHipDataDecode", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addHipDataDecode(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		Map<String, Object> retMap = null;
		try {
			retMap = dataTypeService.addHipDataDecode(mapVo);
		} catch (Exception e) {
			retMap = new HashMap<String, Object>();
			retMap.put("error", "操作失败！");
		}

		return retMap;
	}

	@RequestMapping(value = "/querySourceColListByType", method = RequestMethod.POST)
	@ResponseBody
	public String querySourceColListByType(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		String str = dataTypeService.querySourceColListByType(mapVo);
		
		return str;
	}

	@RequestMapping(value = "/queryHrpDictTable", method = RequestMethod.POST)
	@ResponseBody
	public String queryHrpDictTable(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		String str = dataTypeService.queryHrpDictTable(mapVo);
		
		return str;
	}

	@RequestMapping(value = "/queryHrpDictTableCol", method = RequestMethod.POST)
	@ResponseBody
	public String queryHrpDictTableCol(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		String str = dataTypeService.queryHrpDictTableCol(mapVo);
		
		return str;
	}
	
}
