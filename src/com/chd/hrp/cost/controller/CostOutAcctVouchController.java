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
import com.chd.hrp.cost.entity.CostItemDictNo;
import com.chd.hrp.cost.entity.CostOutAcctVouch;
import com.chd.hrp.cost.service.CostItemDictNoService;
import com.chd.hrp.cost.service.CostOutAcctVouchService;
import com.chd.hrp.cost.service.CostParaService;
import com.chd.hrp.sys.entity.Source;
import com.chd.hrp.sys.service.SourceService;

/**
* @Title. @Description.
* 科室支出总账
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


@Controller
public class CostOutAcctVouchController extends BaseController{
	private static Logger logger = Logger.getLogger(CostOutAcctVouchController.class);
	
	
	@Resource(name = "costOutAcctVouchService")
	private final CostOutAcctVouchService costOutAcctVouchService = null;
	
	@Resource(name = "costItemDictNoService")
	private final CostItemDictNoService costItemDictNoService = null;
	
	@Resource(name = "costParaService")
	private final CostParaService costParaService = null;
   
	@Resource(name = "sourceService")
	private final SourceService sourceService = null;
	
	/**
	*科室支出总账<BR>
	*维护页面跳转
	*/
	
	@RequestMapping(value = "/hrp/cost/costoutacctvouch/costOutAcctVouchMainPage", method = RequestMethod.GET)
	public String costOutAcctVouchMainPage(Model mode) throws Exception {

		return "hrp/cost/costoutacctvouch/costOutAcctVouchMain";

	}
	/**
	 *科室支出总账<BR>
	 *维护页面跳转
	 */
	
	@RequestMapping(value = "/hrp/cost/costoutacctvouch/costOutAcctVouchMainPrmPage", method = RequestMethod.GET)
	public String costOutAcctVouchMainPrmPage(Model mode) throws Exception {
		
		return "hrp/cost/costoutacctvouch/costOutAcctVouchMainPrm";
		
	}
	/**
	*科室支出总账<BR>
	*维护页面跳转
	*/
	// 添加页面
	@RequestMapping(value = "/hrp/cost/costoutacctvouch/costOutAcctVouchAddPage", method = RequestMethod.GET)
	public String costOutAcctVouchAddPage(Model mode) throws Exception {

		return "hrp/cost/costoutacctvouch/costOutAcctVouchAdd";

	}
	/**
	*科室支出总账<BR>
	*保存
	*/
	@RequestMapping(value = "/hrp/cost/costoutacctvouch/addCostOutAcctVouch", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> addCostOutAcctVouch(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if(mapVo.get("group_id") == null){
		mapVo.put("group_id", SessionManager.getGroupId());
		}
		if(mapVo.get("hos_id") == null){
		mapVo.put("hos_id", SessionManager.getHosId());
		}
		if(mapVo.get("copy_code") == null){
        mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		String costOutAcctVouchJson = costOutAcctVouchService.addCostOutAcctVouch(mapVo);

		return JSONObject.parseObject(costOutAcctVouchJson);
		
	}
	/**
	*科室支出总账<BR>
	*查询
	*/
	@RequestMapping(value = "/hrp/cost/costoutacctvouch/queryCostOutAcctVouch", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> queryCostOutAcctVouch(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
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
		
		String costOutAcctVouch = costOutAcctVouchService.queryCostOutAcctVouch(getPage(mapVo));

		return JSONObject.parseObject(costOutAcctVouch);
		
	}
	/**
	*科室支出总账<BR>
	*删除
	*/
	@RequestMapping(value = "/hrp/cost/costoutacctvouch/deleteCostOutAcctVouch", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteCostOutAcctVouch(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		for ( String id: paramVo.split(",")) {
			
			String[] ids=id.split("@");
			
			Map<String, Object> mapVo=new HashMap<String, Object>();
			
			mapVo.put("group_id",ids[0]);   
			
			mapVo.put("hos_id",ids[1]);   
			
			mapVo.put("copy_code",ids[2]);   
			
			mapVo.put("acc_year",ids[3]);   
			
			mapVo.put("acc_month",ids[4]);  
			
			mapVo.put("out_id",ids[5]);   
			
            listVo.add(mapVo);
        }
		
		String costOutAcctVouchJson = costOutAcctVouchService.deleteBatchCostOutAcctVouch(listVo);
	   return JSONObject.parseObject(costOutAcctVouchJson);
			
	}
	
	/**
	*科室支出总账<BR>
	*修改页面跳转
	*/
	@RequestMapping(value = "/hrp/cost/costoutacctvouch/costOutAcctVouchUpdatePage", method = RequestMethod.GET)
	public String costOutAcctVouchUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());
		 
		mapVo.put("hos_id", SessionManager.getHosId());
  
        mapVo.put("copy_code", SessionManager.getCopyCode()); 
		
        CostOutAcctVouch costOutAcctVouch = new CostOutAcctVouch();
        
	    String para_value = MyConfig.getSysPara("03001");
		
    	mapVo.put("is_flag", para_value);
        
		costOutAcctVouch = costOutAcctVouchService.queryCostOutAcctVouchByCode(mapVo);
		
		mode.addAttribute("group_id", costOutAcctVouch.getGroup_id());
		
		mode.addAttribute("hos_id", costOutAcctVouch.getHos_id());
		
		mode.addAttribute("copy_code", costOutAcctVouch.getCopy_code());
		
		mode.addAttribute("out_id", costOutAcctVouch.getOut_id());
		
		mode.addAttribute("year_month", costOutAcctVouch.getAcc_year().toString() + costOutAcctVouch.getAcc_month().toString());
		
		mode.addAttribute("acc_year", costOutAcctVouch.getAcc_year());
		
		mode.addAttribute("acc_month", costOutAcctVouch.getAcc_month());
		
		mode.addAttribute("dept_id", costOutAcctVouch.getDept_id());
		
		mode.addAttribute("dept_no", costOutAcctVouch.getDept_no());
		
		mode.addAttribute("dept_code", costOutAcctVouch.getDept_code());
		
		mode.addAttribute("dept_name", costOutAcctVouch.getDept_name());
		
		mode.addAttribute("cost_item_id", costOutAcctVouch.getCost_item_id());
		
		mode.addAttribute("cost_item_no", costOutAcctVouch.getCost_item_no());
		
		mode.addAttribute("cost_item_code", costOutAcctVouch.getCost_item_code());
		
		mode.addAttribute("cost_item_name", costOutAcctVouch.getCost_item_name());
		
		mode.addAttribute("source_id", costOutAcctVouch.getSource_id());
		
		mode.addAttribute("source_code", costOutAcctVouch.getSource_code());
		
		mode.addAttribute("source_name", costOutAcctVouch.getSource_name());
		
		mode.addAttribute("amount", costOutAcctVouch.getAmount());
		
		return "hrp/cost/costoutacctvouch/costOutAcctVouchUpdate";
	}
	/**
	*科室支出总账<BR>
	*修改保存
	*/	
	
	@RequestMapping(value = "/hrp/cost/costoutacctvouch/updateCostOutAcctVouch", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateCostOutAcctVouch(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if(mapVo.get("group_id") == null){
		mapVo.put("group_id", SessionManager.getGroupId());
		}
		if(mapVo.get("hos_id") == null){
		mapVo.put("hos_id", SessionManager.getHosId());
		}
		if(mapVo.get("copy_code") == null){
        mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		String costOutAcctVouchJson = costOutAcctVouchService.updateCostOutAcctVouch(mapVo);
		
		return JSONObject.parseObject(costOutAcctVouchJson);
	}
	
    // 导入页面
	@RequestMapping(value = "/hrp/cost/costoutacctvouch/costOutAcctVouchImportPage", method = RequestMethod.GET)
	public String costOutAcctVouchImportPage(Model mode) throws Exception {

		return "hrp/cost/costoutacctvouch/costOutAcctVouchImport"; 

	}
	//下载导入模版
	@RequestMapping(value="hrp/cost/costoutacctvouch/downTemplate", method = RequestMethod.GET)  
	 public String downTemplate(Plupload plupload,HttpServletRequest request,
	    		HttpServletResponse response,Model mode) throws IOException { 
	    printTemplate(request,response,"cost\\基础设置","科室支出总账采集.xls");
	    return null; 
	 }
	 
	/**
	*科室支出总账<BR>
	*导入
	*/ 
	@RequestMapping(value="/hrp/cost/costoutacctvouch/costOutAcctVouchImportPage",method = RequestMethod.POST)  
	@ResponseBody
	public String readChargeKindDictFiles(@RequestParam Map<String, Object> mapVo, Model mode) throws IOException { 

		try {
			
			String reJson=costOutAcctVouchService.readAssFinaDictFiles(mapVo);
			
			return reJson;
			
		} catch (Exception e) {
			
			return "{\"error\":\""+e.getMessage()+"\"}";
			
		}
		/*List<Map<String,Object>> list_batch = new ArrayList<Map<String,Object>>();	
		
		List<CostOutAcctVouch> list_err = new ArrayList<CostOutAcctVouch>();
		
		List<String[]> list = UploadUtil.readFile(plupload, request, response);
		
		try {
			for (int i = 1; i < list.size(); i++) {
				
				StringBuffer err_sb = new StringBuffer();
				
				CostOutAcctVouch costOutAcctVouch = new CostOutAcctVouch();
				
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
						
						costOutAcctVouch.setYear_month(temp[0]);
						
						costOutAcctVouch.setAcc_year(year_month.substring(0, 4));
						
						costOutAcctVouch.setAcc_month(year_month.substring(4, 6));

						mapVo.put("acc_year", year_month.substring(0, 4));
						
						mapVo.put("acc_month", year_month.substring(4, 6));

					} else {

						err_sb.append("统计年月为空  ");

					}
					if (StringUtils.isNotEmpty(temp[1])) {
						
						costOutAcctVouch.setDept_code(temp[1]);
						
						mapVo.put("dept_code", temp[1]);
					} else {
						err_sb.append("科室编码为空  ");
					}
					if (StringUtils.isNotEmpty(temp[2])) {
						
						costOutAcctVouch.setDept_name(temp[2]);
						
					}
					if (StringUtils.isNotEmpty(temp[3])) {
						
						costOutAcctVouch.setCost_item_code(temp[3]);
						
						mapVo.put("cost_item_code", temp[3]);
						
					} else {
						
						err_sb.append("成本项目编码为空  ");
						
					}
					if (StringUtils.isNotEmpty(temp[4])) {
						
						costOutAcctVouch.setCost_item_name(temp[4]);
						
					}
					if (StringUtils.isNotEmpty(temp[5])) {
						
						costOutAcctVouch.setSource_code(temp[5]);
						
						mapVo.put("source_code", temp[5]);
					} else {
						err_sb.append("资金来源编码为空  ");
					}
					if (StringUtils.isNotEmpty(temp[6])) {
						
						costOutAcctVouch.setSource_name(temp[6]);
						
						mapVo.put("source_name", temp[6]);
					} else {
						err_sb.append("资金来源名称为空  ");
					}
					if (StringUtils.isNotEmpty(temp[7])) {
						
						costOutAcctVouch.setAmount(Double.valueOf(temp[7]));
						
						mapVo.put("amount", temp[7]);
					} else {
						err_sb.append("金额为空  ");
					}
					
					Map<String, Object> byCodeMap = new HashMap<String, Object>();

					byCodeMap.put("group_id", mapVo.get("group_id"));

					byCodeMap.put("hos_id", mapVo.get("hos_id"));

					byCodeMap.put("copy_code", mapVo.get("copy_code"));
					
					byCodeMap.put("dept_code", mapVo.get("dept_code"));
					
					byCodeMap.put("is_stop", "0");
				
				CostOutAcctVouch costDept = costOutAcctVouchService.queryCostDeptByCode(byCodeMap);
				
				if(costDept != null ){
					
					mapVo.put("dept_id", costDept.getDept_id());
					
					mapVo.put("dept_no", costDept.getDept_no());
					
					costOutAcctVouch.setDept_code((String)mapVo.get("dept_code"));
					
				}else{
					
					costOutAcctVouch.setDept_code((String)mapVo.get("dept_code"));
					
					err_sb.append("科室不存在 ");
					
				}
				
				byCodeMap.put("cost_item_code", mapVo.get("cost_item_code"));

				CostItemDictNo cidn = costItemDictNoService.queryCostItemDictNoByCode(byCodeMap);

				if(cidn != null ){
					
					mapVo.put("cost_item_id", cidn.getCost_item_id());
					
					mapVo.put("cost_item_no", cidn.getCost_item_no());
					
					costOutAcctVouch.setCost_item_code((String)mapVo.get("cost_item_code"));
					
				}else{
					
					costOutAcctVouch.setCost_item_code((String)mapVo.get("cost_item_code"));
					
					err_sb.append("成本项目不存在 ");
					
				}
				
				byCodeMap.put("source_code", mapVo.get("source_code"));
				
				Source source = sourceService.querySourceByCode(byCodeMap);

				if(source != null ){
					
					mapVo.put("source_id", source.getSource_id());
					
					mapVo.put("source_code", source.getSource_code());
					
					costOutAcctVouch.setSource_code((String)mapVo.get("source_code"));
					
				}else{
					
					costOutAcctVouch.setSource_code((String)mapVo.get("source_code"));
					
					err_sb.append("资金来源编码不存在 ");
					
				}
				
			CostOutAcctVouch data_exc_extis = costOutAcctVouchService.queryCostOutAcctVouchByCode(mapVo);
				//根据不同业务提示相关信息
				
				if (data_exc_extis != null) {
					err_sb.append("已经存在此年月对应关系！ ");
				}
				if (err_sb.toString().length() > 0) {
					
					costOutAcctVouch.setError_type(err_sb.toString());
					
					list_err.add(costOutAcctVouch);
				} else {
					
					list_batch.add(mapVo);
					
				}
			}
		} catch (DataAccessException e) {
			
			CostOutAcctVouch data_exc = new CostOutAcctVouch();
			
			data_exc.setError_type("导入系统出错");
			
			list_err.add(data_exc);
		}
		
		if(list_batch.size()>0){
			
			costOutAcctVouchService.addBatchCostOutAcctVouch(list_batch);
			
		}
		
		response.getWriter().print(ChdJson.toJson(list_err, list_err.size()));
		
		return null;*/
    }  
	/**
	*科室支出总账<BR>
	*批量添加
	*/ 
	@RequestMapping(value = "/hrp/cost/costoutacctvouch/addBatchCostOutAcctVouch", method = RequestMethod.POST)
	@ResponseBody
    public Map<String, Object> addBatchCostOutAcctVouch(@RequestParam(value = "ParamVo") String paramVo, Model mode)throws Exception{
		
		List<Map<String, Object>> list_batch = new ArrayList<Map<String, Object>>();
		
		List<CostOutAcctVouch> list_err = new ArrayList<CostOutAcctVouch>();
		
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
			
			mapVo.put("dept_code", jsonObj.get("dept_code"));
			
			mapVo.put("dept_name", jsonObj.get("dept_name"));
			
			mapVo.put("cost_item_code", jsonObj.get("cost_item_code"));
			
			mapVo.put("cost_item_name", jsonObj.get("cost_item_name"));
			
			mapVo.put("source_code", jsonObj.get("source_code"));
			
			mapVo.put("source_name", jsonObj.get("source_name"));
			
			mapVo.put("amount", jsonObj.get("amount"));
			
			///////////////////////////////////////////////////////////////////////////////
		   
			Map<String, Object> byCodeMap = new HashMap<String, Object>();

			byCodeMap.put("group_id", mapVo.get("group_id"));

			byCodeMap.put("hos_id", mapVo.get("hos_id"));

			byCodeMap.put("copy_code", mapVo.get("copy_code"));
			
			byCodeMap.put("dept_code", mapVo.get("dept_code"));
			
			byCodeMap.put("is_stop", 0);
		
					CostOutAcctVouch costDept = costOutAcctVouchService.queryCostDeptByCode(byCodeMap);
					
					if(costDept != null ){
						
						mapVo.put("dept_id", costDept.getDept_id());
						
						mapVo.put("dept_no", costDept.getDept_no());
						
					}else{
			
						err_sb.append("科室不存在 ");
						
					}
					
					byCodeMap.put("cost_item_code", mapVo.get("cost_item_code"));
			
					CostItemDictNo cidn = costItemDictNoService.queryCostItemDictNoByCode(byCodeMap);
			
					if(cidn != null ){
						
						mapVo.put("cost_item_id", cidn.getCost_item_id());
						
						mapVo.put("cost_item_no", cidn.getCost_item_no());
						
					}else{
						
						err_sb.append("成本项目不存在 ");
						
					}
					
					byCodeMap.put("source_code", mapVo.get("source_code"));
					
					Source source = sourceService.querySourceByCode(byCodeMap);

					if(source != null ){
						
						mapVo.put("source_id", source.getSource_id());
						
					}else{
						
						err_sb.append("资金来源编码不存在 ");
						
					}

					
				CostOutAcctVouch data_exc_extis = costOutAcctVouchService.queryCostOutAcctVouchByCode(mapVo);
					//根据不同业务提示相关信息
					
					if (data_exc_extis != null) {
						
						err_sb.append("已经存在此年月对应关系！ ");
						
					}
				
				CostOutAcctVouch costOutAcctVouch = new CostOutAcctVouch();
				
				if (err_sb.toString().length() > 0) {
					costOutAcctVouch.setYear_month(mapVo.get("acc_year").toString() + mapVo.get("acc_month").toString());
					
					
					costOutAcctVouch.setAcc_year(mapVo.get("acc_year").toString());
					
					costOutAcctVouch.setAcc_month(mapVo.get("acc_month").toString());
					
					costOutAcctVouch.setDept_code(mapVo.get("dept_code").toString());
					
					costOutAcctVouch.setDept_name(mapVo.get("dept_name").toString());
					
					costOutAcctVouch.setCost_item_code(mapVo.get("cost_item_code").toString());
					
					costOutAcctVouch.setCost_item_name(mapVo.get("cost_item_name").toString());
					
					costOutAcctVouch.setSource_code(mapVo.get("source_code").toString());
					
					costOutAcctVouch.setSource_name(mapVo.get("source_name").toString());
					
					costOutAcctVouch.setAmount(Double.valueOf(mapVo.get("amount").toString()));
					
					costOutAcctVouch.setError_type(err_sb.toString());
					
					list_err.add(costOutAcctVouch);
				} else {
					
					list_batch.add(mapVo);
					
				}
			}
			
			if(list_batch.size()>0){
				
				costOutAcctVouchService.addBatchCostOutAcctVouch(list_batch);
				
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

	/**
	*科室支出总账<BR>
	*采集页面跳转
	*/
	
	@RequestMapping(value = "/hrp/cost/costoutacctvouch/costOutAcctVouchCollectPage", method = RequestMethod.GET)
	public String costOutAcctVouchCollectPage(Model mode) throws Exception {

		return "hrp/cost/costoutacctvouch/costOutAcctVouchCollect";
	}

	/**
	*科室支出总账<BR>
	*采集
	*/
	@RequestMapping(value = "/hrp/cost/costoutacctvouch/addCostOutAcctVouchByAcc", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addCostOutAcctVouchByAcc(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
        mapVo.put("copy_code", SessionManager.getCopyCode());
        
		String costOutAcctVouchJson;
		try {
			costOutAcctVouchJson = costOutAcctVouchService.addCostOutAcctVouchByAcc(mapVo);
		} catch (Exception e) {
			
			costOutAcctVouchJson = e.getMessage();
		}

		return JSONObject.parseObject(costOutAcctVouchJson);
	}
	@RequestMapping(value="/hrp/cost/costoutacctvouch/readCostItemDictFilesX",method = RequestMethod.POST)  
	@ResponseBody
	public String readCostItemDictFilesX(@RequestParam Map<String, Object> mapVo, Model mode) throws IOException { 

		try {
			
			String reJson=costOutAcctVouchService.readCostItemDictFilesX(mapVo);
			
			return reJson;
			
		} catch (Exception e) {
			
			return "{\"error\":\""+e.getMessage()+"\"}";
			
		}
	}
	/**
	*科室支出总账<BR>
	*查询
	*/
	@RequestMapping(value = "/hrp/cost/costoutacctvouch/queryCostOutAcctVouchPrm", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> queryCostOutAcctVouchPrm(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
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
		
		String costOutAcctVouch = costOutAcctVouchService.queryCostOutAcctVouchPrm(getPage(mapVo));

		return JSONObject.parseObject(costOutAcctVouch);
		
	}
	
	/**
	*科室支出总账<BR>
	*按照月份删除
	*/
	@RequestMapping(value = "/hrp/cost/costoutacctvouch/deleteMonthlyCostOutAcctVouch", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteMonthlyCostOutAcctVouch(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		String costOutAcctVouchJson;
		try {
			
			costOutAcctVouchJson = costOutAcctVouchService.deleteMonthlyCostOutAcctVouch(mapVo);
			
		} catch (Exception e) {
			
			costOutAcctVouchJson = e.getMessage();
		}
		
	   return JSONObject.parseObject(costOutAcctVouchJson);
	
	}
	
   /**
	* 
	* @Title: costOutAcctVouchMainPage
	* @Description: 校验会计数据
	* @param @param mode
	* @param @return
	* @param @throws Exception
	* @return String 
	* @date 2020年2月13日   
	* @author sjy
	 */
	@RequestMapping(value = "/hrp/cost/costoutacctvouch/costOutAcctVouchCheckPage", method = RequestMethod.GET)
	public String costOutAcctVouchCheckPage(Model mode) throws Exception {
		return "hrp/cost/costoutacctvouch/costOutAcctVouchCheck";
	}
	
	/**
	 * 
	* @Title: queryCostOutAcctVouch
	* @Description: 校验会计数据查询
	* @param @param mapVo
	* @param @param mode
	* @param @return
	* @param @throws Exception
	* @return Map<String,Object> 
	* @date 2020年2月13日   
	* @author sjy
	 */
	@RequestMapping(value = "/hrp/cost/costoutacctvouch/checkCostOutAcctVouch", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> checkCostOutAcctVouch(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
        mapVo.put("copy_code", SessionManager.getCopyCode());
        String year_month = mapVo.get("year_month").toString();
		mapVo.put("acc_year", year_month.substring(0, 4));
		mapVo.put("acc_month", year_month.substring(4, 6));;
		String costOutAcctVouch = costOutAcctVouchService.checkCostOutAcctVouch(getPage(mapVo));
		return JSONObject.parseObject(costOutAcctVouch);
		
	}
}

