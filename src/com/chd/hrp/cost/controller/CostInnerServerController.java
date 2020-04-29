/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/
package com.chd.hrp.cost.controller;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.chd.base.BaseController;
import com.chd.base.MyConfig;
import com.chd.base.SessionManager;
import com.chd.base.util.ChdJson;
import com.chd.base.util.Plupload;
import com.chd.base.util.UploadUtil;
import com.chd.hrp.cost.entity.CostInnerServer;
import com.chd.hrp.cost.entity.CostOutAcctVouch;
import com.chd.hrp.cost.service.CostParaService;
import com.chd.hrp.cost.serviceImpl.CostInnerServerServiceImpl;
import com.chd.hrp.cost.serviceImpl.CostOutAcctVouchServiceImpl;

/**
* @Title. @Description.
* 内部服务量表
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


@Controller
public class CostInnerServerController extends BaseController{
	private static Logger logger = Logger.getLogger(CostInnerServerController.class);
	
	
	@Resource(name = "costInnerServerService")
	private final CostInnerServerServiceImpl costInnerServerService = null;
	
	@Resource(name = "costOutAcctVouchService")
	private final CostOutAcctVouchServiceImpl costOutAcctVouchService = null;
	
	@Resource(name = "costParaService")
	private final CostParaService costParaService = null;
   
   
	/**
	*内部服务量表<BR>
	*维护页面跳转
	*/
	
	@RequestMapping(value = "/hrp/cost/costinnerserver/costInnerServerMainPage", method = RequestMethod.GET)
	public String costInnerServerMainPage(Model mode) throws Exception {

		return "hrp/cost/costinnerserver/costInnerServerMain";

	}
	/**
	*内部服务量表<BR>
	*维护页面跳转
	*/
	// 添加页面
	@RequestMapping(value = "/hrp/cost/costinnerserver/costInnerServerAddPage", method = RequestMethod.GET)
	public String costInnerServerAddPage(Model mode) throws Exception {

		return "hrp/cost/costinnerserver/costInnerServerAdd";

	}
	/**
	*内部服务量表<BR>
	*保存
	*/
	@RequestMapping(value = "/hrp/cost/costinnerserver/addCostInnerServer", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> addCostInnerServer(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if(mapVo.get("group_id") == null){
		mapVo.put("group_id", SessionManager.getGroupId());
		}
		if(mapVo.get("hos_id") == null){
		mapVo.put("hos_id", SessionManager.getHosId());
		}
		if(mapVo.get("copy_code") == null){
        mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		String costInnerServerJson = costInnerServerService.addCostInnerServer(mapVo);

		return JSONObject.parseObject(costInnerServerJson);
		
	}
	/**
	*内部服务量表<BR>
	*查询
	*/
	@RequestMapping(value = "/hrp/cost/costinnerserver/queryCostInnerServer", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> queryCostInnerServer(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if(mapVo.get("group_id") == null){
		mapVo.put("group_id", SessionManager.getGroupId());
		}
		if(mapVo.get("hos_id") == null){
		mapVo.put("hos_id", SessionManager.getHosId());
		}
		if(mapVo.get("copy_code") == null){
        mapVo.put("copy_code", SessionManager.getCopyCode());
		} 
		
		String para_value = MyConfig.getSysPara("03001");
		
    	mapVo.put("is_flag", para_value);
		
		String costInnerServer = costInnerServerService.queryCostInnerServer(getPage(mapVo));

		return JSONObject.parseObject(costInnerServer);
		
	}
	/**
	*内部服务量表<BR>
	*删除
	*/
	@RequestMapping(value = "/hrp/cost/costinnerserver/deleteCostInnerServer", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteCostInnerServer(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		for ( String id: paramVo.split(",")) {
			String[] ids=id.split("@");
			Map<String, Object> mapVo=new HashMap<String, Object>();
			mapVo.put("group_id",ids[0]);   
			
			mapVo.put("hos_id",ids[1]);   
			
			mapVo.put("copy_code",ids[2]);   
			
			mapVo.put("acc_year",ids[3]);  
			mapVo.put("acc_month",ids[4]); 
			
			mapVo.put("server_dept_id",ids[5]);   
			
			mapVo.put("server_dept_no",ids[6]);   
			
			mapVo.put("server_by_dept_id",ids[7]);   
			
			mapVo.put("server_by_dept_no",ids[8]);   
			
			mapVo.put("server_item_code",ids[9]); 
			
            listVo.add(mapVo);
        }
		String costInnerServerJson = costInnerServerService.deleteBatchCostInnerServer(listVo);
	   return JSONObject.parseObject(costInnerServerJson);
			
	}
	
	/**
	*内部服务量表<BR>
	*按月删除
	*/
	@RequestMapping(value = "/hrp/cost/costinnerserver/deleteMonthlyCostInnerServer", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteMonthlyCostInnerServer(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		
		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

        mapVo.put("copy_code", SessionManager.getCopyCode());
			
		String costInnerServerJson;
		
		try {
			
			costInnerServerJson = costInnerServerService.deleteMonthlyCostInnerServer(mapVo);
			
		} catch (Exception e) {
			// TODO: handle exception
			costInnerServerJson = e.getMessage();
		}
		
	   return JSONObject.parseObject(costInnerServerJson);
			
	}
	
	/**
	*内部服务量表<BR>
	*修改页面跳转
	*/
	@RequestMapping(value = "/hrp/cost/costinnerserver/costInnerServerUpdatePage", method = RequestMethod.GET)
	
	public String costInnerServerUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		
		mapVo.put("hos_id", SessionManager.getHosId());
		
        mapVo.put("copy_code", SessionManager.getCopyCode());
		
        CostInnerServer costInnerServer = new CostInnerServer();
        
	    String para_value = MyConfig.getSysPara("03001");
		
    	mapVo.put("is_flag", para_value);
        
		costInnerServer = costInnerServerService.queryCostInnerServerByCode(mapVo);
		
		mode.addAttribute("group_id", costInnerServer.getGroup_id());
		
		mode.addAttribute("hos_id", costInnerServer.getHos_id());
		
		mode.addAttribute("copy_code", costInnerServer.getCopy_code());
		
		mode.addAttribute("year_month", costInnerServer.getAcc_year().toString() + costInnerServer.getAcc_month().toString());
		
		mode.addAttribute("acc_year", costInnerServer.getAcc_year());
		mode.addAttribute("acc_month", costInnerServer.getAcc_month());
		
		mode.addAttribute("server_dept_id", costInnerServer.getServer_dept_id());
		
		mode.addAttribute("server_dept_no", costInnerServer.getServer_dept_no());
		
		mode.addAttribute("server_dept_code", costInnerServer.getServer_dept_code());
		
		mode.addAttribute("server_dept_name", costInnerServer.getServer_dept_name());
		
		mode.addAttribute("server_by_dept_id", costInnerServer.getServer_by_dept_id());
		
		mode.addAttribute("server_by_dept_no", costInnerServer.getServer_by_dept_no());
		
		mode.addAttribute("server_by_dept_code", costInnerServer.getServer_by_dept_code());
		
		mode.addAttribute("server_by_dept_name", costInnerServer.getServer_by_dept_name());
		
		mode.addAttribute("server_item_code", costInnerServer.getServer_item_code());
		
		mode.addAttribute("server_item_name", costInnerServer.getServer_item_name());
		
		mode.addAttribute("server_num", costInnerServer.getServer_num());
		
		return "hrp/cost/costinnerserver/costInnerServerUpdate";
	}
	/**
	*内部服务量表<BR>
	*修改保存
	*/	
	
	@RequestMapping(value = "/hrp/cost/costinnerserver/updateCostInnerServer", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateCostInnerServer(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		
		mapVo.put("group_id", SessionManager.getGroupId());
		
		mapVo.put("hos_id", SessionManager.getHosId());
		
        mapVo.put("copy_code", SessionManager.getCopyCode());
        
		String costInnerServerJson = costInnerServerService.updateCostInnerServer(mapVo);
		
		return JSONObject.parseObject(costInnerServerJson);
	}
	
    // 导入页面
	@RequestMapping(value = "/hrp/cost/costinnerserver/costInnerServerImportPage", method = RequestMethod.GET)
	public String costInnerServerImportPage(Model mode) throws Exception {

		return "hrp/cost/costinnerserver/costInnerServerImport";

	}
	//下载导入模版
	@RequestMapping(value="hrp/cost/costinnerserver/downTemplate", method = RequestMethod.GET)  
	 public String downTemplate(Plupload plupload,HttpServletRequest request,
	    		HttpServletResponse response,Model mode) throws IOException { 
				
	    printTemplate(request,response,"cost\\基础设置","内部服务量采集.xls");
		
	    return null; 
	 }
	 
	/**
	*内部服务量表<BR>
	*导入
	*/ 
	@RequestMapping(value="/hrp/cost/costinnerserver/costInnerServerImportPage",method = RequestMethod.POST)  
	@ResponseBody
	public String readChargeKindDictFiles(@RequestParam Map<String, Object> mapVo, Model mode) throws IOException { 

		try {
			
			String reJson=costInnerServerService.readAssFinaDictFiles(mapVo);
			
			return reJson;
			
		} catch (Exception e) {
			
			return "{\"error\":\""+e.getMessage()+"\"}";
			
		}
			
		/*List<Map<String,Object>> list_batch = new ArrayList<Map<String,Object>>();	
		
		List<CostInnerServer> list_err = new ArrayList<CostInnerServer>();
		
		List<String[]> list = UploadUtil.readFile(plupload, request, response);
		
		try {
			for (int i = 1; i < list.size(); i++) {
				
				StringBuffer err_sb = new StringBuffer();
				
				CostInnerServer costInnerServer = new CostInnerServer();
				
				String temp[] = list.get(i);// 行
				
				Map<String, Object> mapVo = new HashMap<String, Object>();
				
					if (mapVo.get("group_id") == null) {
						
						mapVo.put("group_id", SessionManager.getGroupId());
					}
					if (mapVo.get("hos_id") == null) {
						
						mapVo.put("hos_id", SessionManager.getHosId());
					}
					if (mapVo.get("copy_code") == null) {
						
						mapVo.put("copy_code", SessionManager.getCopyCode());
					}
					if (StringUtils.isNotEmpty(temp[0])) {
						
						String year_month = temp[0].toString();
						
						costInnerServer.setYear_month(temp[0]);
						
						costInnerServer.setAcc_year(year_month.substring(0, 4));
						
						costInnerServer.setAcc_month(year_month.substring(4, 6));

						mapVo.put("acc_year", year_month.substring(0, 4));
						
						mapVo.put("acc_month", year_month.substring(4, 6));

					} else {

						err_sb.append("统计年月为空  ");

					}
					if (StringUtils.isNotEmpty(temp[1])) {
						
						costInnerServer.setServer_dept_code(temp[1]);
						
						mapVo.put("server_dept_code", temp[1]);
						
					} else {
						
						err_sb.append("服务科室编码为空  ");
						
					}
					if (StringUtils.isNotEmpty(temp[2])) {
						
						costInnerServer.setServer_dept_name(temp[2]);
						
					}
					if (StringUtils.isNotEmpty(temp[3])) {
						
						costInnerServer.setServer_by_dept_code(temp[3]);
						
						mapVo.put("server_by_dept_code", temp[3]);
						
					} else {
						
						err_sb.append("被服务科室编码为空  ");
						
					}
					if (StringUtils.isNotEmpty(temp[4])) {
						
						costInnerServer.setServer_by_dept_name(temp[4]);
						
						mapVo.put("server_by_dept_name", temp[4]);
						
					} 
					if (StringUtils.isNotEmpty(temp[5])) {
						
						costInnerServer.setServer_item_code(temp[5]);
						
						mapVo.put("server_item_code", temp[5]);
					} else {
						err_sb.append("服务项目编码为空  ");
					}
					if (StringUtils.isNotEmpty(temp[6])) {
						
						costInnerServer.setServer_item_name(temp[6]);
						
					}
					
					if (StringUtils.isNotEmpty(temp[7])) {
						
						costInnerServer.setServer_num(Long.valueOf(temp[7]));
						
						mapVo.put("server_num", temp[7]);
						
					} else {
						
						err_sb.append("服务量为空  ");
					
					}
					
					Map<String, Object> byCodeMap = new HashMap<String, Object>();

					byCodeMap.put("group_id", mapVo.get("group_id"));

					byCodeMap.put("hos_id", mapVo.get("hos_id"));

					byCodeMap.put("copy_code", mapVo.get("copy_code"));
					
					byCodeMap.put("dept_code", mapVo.get("server_dept_code"));
					
					byCodeMap.put("is_stop", "0");
				
					CostOutAcctVouch costDept = costOutAcctVouchService.queryCostDeptByCode(byCodeMap);
					
					if(costDept != null ){
						
						mapVo.put("server_dept_id", costDept.getDept_id());
						
						mapVo.put("server_dept_no", costDept.getDept_no());
						
						costInnerServer.setServer_dept_code((String)mapVo.get("server_dept_code"));
						
					}else{
						
						costInnerServer.setServer_dept_code((String)mapVo.get("server_dept_code"));
						
						err_sb.append("服务科室不存在 ");
						
					}
					
					Map<String, Object> CodeMap = new HashMap<String, Object>();

					CodeMap.put("group_id", mapVo.get("group_id"));

					CodeMap.put("hos_id", mapVo.get("hos_id"));

					CodeMap.put("copy_code", mapVo.get("copy_code"));
					
					CodeMap.put("dept_code", mapVo.get("server_by_dept_code"));
					
					CodeMap.put("is_stop", "0");
				
					CostOutAcctVouch serverDept = costOutAcctVouchService.queryCostDeptByCode(CodeMap);
					
					if(serverDept != null ){
						
						mapVo.put("server_by_dept_id", serverDept.getDept_id());
						
						mapVo.put("server_by_dept_no", serverDept.getDept_no());
						
						costInnerServer.setServer_by_dept_code((String)mapVo.get("server_by_dept_code"));
						
					}else{
						
						costInnerServer.setServer_by_dept_code((String)mapVo.get("server_by_dept_code"));
						
						err_sb.append("被服务科室不存在 ");
						
					}
					
					CostInnerServer costServerItem = costInnerServerService.queryCostServerItemDict(mapVo);
					
					if(costServerItem == null ){
						
						costInnerServer.setServer_by_dept_code((String)mapVo.get("server_by_dept_code"));
						
						err_sb.append("服务项目不存在 ");
						
					}
					
				CostInnerServer data_exc_extis = costInnerServerService.queryCostInnerServerByCode(mapVo);
				
				//根据不同业务提示相关信息
				
				if (data_exc_extis != null) {
					err_sb.append("编码已经存在！ ");
				}
				if (err_sb.toString().length() > 0) {
					
					costInnerServer.setError_type(err_sb.toString());
					
					list_err.add(costInnerServer);
				} else {
					
					list_batch.add(mapVo);
					
				}
			}
		} catch (DataAccessException e) {
			
			CostInnerServer data_exc = new CostInnerServer();
			
			data_exc.setError_type("导入系统出错");
			
			list_err.add(data_exc);
		}
		
		if(list_batch.size()>0){
			
			costInnerServerService.addBatchCostInnerServer(list_batch);
			
		}
		
		response.getWriter().print(ChdJson.toJson(list_err, list_err.size()));
		
		return null;*/
    }  
	/**
	*内部服务量表<BR>
	*批量添加
	*/ 
	@RequestMapping(value = "/hrp/cost/costinnerserver/addBatchCostInnerServer", method = RequestMethod.POST)
	@ResponseBody
    public Map<String, Object> addBatchCostInnerServer(@RequestParam(value = "ParamVo") String paramVo, Model mode)throws Exception{
		
		List<Map<String, Object>> list_batch = new ArrayList<Map<String, Object>>();
		
		List<CostInnerServer> list_err = new ArrayList<CostInnerServer>();
		
		JSONArray json = JSONArray.parseArray(paramVo);
		
		Iterator it = json.iterator();
		
		try {
			while (it.hasNext()) {
				
			Map<String, Object> mapVo = new HashMap<String, Object>();	
			
			StringBuffer err_sb = new StringBuffer();
			
			JSONObject jsonObj = JSONObject.parseObject(it.next().toString());
			
			if (mapVo.get("group_id") == null) {
				
				mapVo.put("group_id", SessionManager.getGroupId());
			}
			if (mapVo.get("hos_id") == null) {
				
				mapVo.put("hos_id", SessionManager.getHosId());
			}
			if (mapVo.get("copy_code") == null) {
				
				mapVo.put("copy_code", SessionManager.getCopyCode());
			}
			
			String year_month = String.valueOf(jsonObj.get("year_month"));
			
			mapVo.put("acc_year", year_month.substring(0, 4));
			
			mapVo.put("acc_month", year_month.substring(4, 6));
			
			mapVo.put("server_dept_code", jsonObj.get("server_dept_code"));
			
			mapVo.put("server_dept_name", jsonObj.get("server_dept_name"));
			
			mapVo.put("server_by_dept_code", jsonObj.get("server_by_dept_code"));
			
			mapVo.put("server_by_dept_name", jsonObj.get("server_by_dept_name"));
			
			mapVo.put("server_item_code", jsonObj.get("server_item_code"));
			
			mapVo.put("server_item_name", jsonObj.get("server_item_name"));
			
			mapVo.put("server_num", jsonObj.get("server_num"));
			
			Map<String, Object> byCodeMap = new HashMap<String, Object>();

			byCodeMap.put("group_id", mapVo.get("group_id"));

			byCodeMap.put("hos_id", mapVo.get("hos_id"));

			byCodeMap.put("copy_code", mapVo.get("copy_code"));
			
			byCodeMap.put("dept_code", mapVo.get("server_dept_code"));
			
			byCodeMap.put("is_stop", "0");
		
			CostOutAcctVouch costDept = costOutAcctVouchService.queryCostDeptByCode(byCodeMap);
			
			if(costDept != null ){
				
				mapVo.put("server_dept_id", costDept.getDept_id());
				
				mapVo.put("server_dept_no", costDept.getDept_no());
				
			}else{
				
				err_sb.append("服务科室不存在 ");
				
			}
			
			Map<String, Object> CodeMap = new HashMap<String, Object>();

			CodeMap.put("group_id", mapVo.get("group_id"));

			CodeMap.put("hos_id", mapVo.get("hos_id"));

			CodeMap.put("copy_code", mapVo.get("copy_code"));
			
			CodeMap.put("dept_code", mapVo.get("server_by_dept_code"));
			
			CodeMap.put("is_stop", "0");
		
			CostOutAcctVouch serverDept = costOutAcctVouchService.queryCostDeptByCode(CodeMap);
			
			if(serverDept != null ){
				
				mapVo.put("server_by_dept_id", serverDept.getDept_id());
				
				mapVo.put("server_by_dept_no", serverDept.getDept_no());
				
			}else{
				
				err_sb.append("被服务科室不存在 ");
				
			}
			
			CostInnerServer costServerItem = costInnerServerService.queryCostServerItemDict(mapVo);
			
			if(costServerItem == null ){
				
				err_sb.append("服务项目不存在 ");
				
			}
			
				CostInnerServer data_exc_extis = costInnerServerService.queryCostInnerServerByCode(mapVo);
				
				if (data_exc_extis != null) {
					err_sb.append("编码已经存在！ ");
				}
				
				CostInnerServer costInnerServer = new CostInnerServer();
				
				if (err_sb.toString().length() > 0) {
					costInnerServer.setYear_month(mapVo.get("acc_year").toString() + mapVo.get("acc_month").toString());
					
					costInnerServer.setAcc_year(mapVo.get("acc_year").toString());
					
					costInnerServer.setAcc_month(mapVo.get("acc_month").toString());
					
					costInnerServer.setServer_dept_code(mapVo.get("server_dept_code").toString());
					
					costInnerServer.setServer_dept_name(mapVo.get("server_dept_name").toString());
					
					costInnerServer.setServer_by_dept_code(mapVo.get("server_by_dept_code").toString());
					
					costInnerServer.setServer_by_dept_name(mapVo.get("server_by_dept_name").toString());
					
					costInnerServer.setServer_item_code(mapVo.get("server_item_code").toString());
					
					costInnerServer.setServer_item_name(mapVo.get("server_item_name").toString());
					
					costInnerServer.setServer_num(Long.valueOf(mapVo.get("server_num").toString()));
					
					costInnerServer.setError_type(err_sb.toString());
					
					list_err.add(costInnerServer);
				} else {
					
					list_batch.add(mapVo);
					
				}
			}
			
			if(list_batch.size()>0){
				
				costInnerServerService.addBatchCostInnerServer(list_batch);
				
			}
		} catch (DataAccessException e) {
			
			return JSONObject.parseObject("{\"msg\":\"导入系统出错,请重新导入.\",\"state\":\"err\"}");
			
		}
		if (list_err.size() > 0) {
			return JSONObject.parseObject(ChdJson.toJson(list_err,list_err.size()));
			
		} else {
			return JSONObject.parseObject("{\"msg\":\"导入成功.\",\"state\":\"true\"}");
			
		}
    }
}

