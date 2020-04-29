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
import com.chd.hrp.cost.entity.CostItemDictNo;
import com.chd.hrp.cost.entity.CostOhterDetail;
import com.chd.hrp.cost.entity.CostOutAcctVouch;
import com.chd.hrp.cost.entity.CostRiskDetail;
import com.chd.hrp.cost.service.CostItemDictNoService;
import com.chd.hrp.cost.service.CostParaService;
import com.chd.hrp.cost.serviceImpl.CostOhterDetailServiceImpl;
import com.chd.hrp.cost.serviceImpl.CostOutAcctVouchServiceImpl;
import com.chd.hrp.cost.serviceImpl.CostRiskDetailServiceImpl;

/**
* @Title. @Description.
* 科室其他费用表
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


@Controller
public class CostOhterDetailController extends BaseController{
	private static Logger logger = Logger.getLogger(CostOhterDetailController.class);
	
	
	@Resource(name = "costOhterDetailService")
	private final CostOhterDetailServiceImpl costOhterDetailService = null;
	
	@Resource(name = "costRiskDetailService")
	private final CostRiskDetailServiceImpl costRiskDetailService = null;
	
	@Resource(name = "costOutAcctVouchService")
	private final CostOutAcctVouchServiceImpl costOutAcctVouchService = null;
	
	@Resource(name = "costItemDictNoService")
	private final CostItemDictNoService costItemDictNoService = null;
   
	@Resource(name = "costParaService")
	private final CostParaService costParaService = null;
   
	/**
	*科室其他费用表<BR>
	*维护页面跳转
	*/
	
	@RequestMapping(value = "/hrp/cost/costohterdetail/costOhterDetailMainPage", method = RequestMethod.GET)
	public String costOhterDetailMainPage(Model mode) throws Exception {

		return "hrp/cost/costohterdetail/costOhterDetailMain";

	}
	/**
	*科室其他费用表<BR>
	*维护页面跳转
	*/
	// 添加页面
	@RequestMapping(value = "/hrp/cost/costohterdetail/costOhterDetailAddPage", method = RequestMethod.GET)
	public String costOhterDetailAddPage(Model mode) throws Exception {

		return "hrp/cost/costohterdetail/costOhterDetailAdd";

	}
	/**
	*科室其他费用表<BR>
	*保存
	*/
	@RequestMapping(value = "/hrp/cost/costohterdetail/addCostOhterDetail", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> addCostOhterDetail(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if(mapVo.get("group_id") == null){
		mapVo.put("group_id", SessionManager.getGroupId());
		}
		if(mapVo.get("hos_id") == null){
		mapVo.put("hos_id", SessionManager.getHosId());
		}
		if(mapVo.get("copy_code") == null){
        mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		String costOhterDetailJson = costOhterDetailService.addCostOhterDetail(mapVo);

		return JSONObject.parseObject(costOhterDetailJson);
		
	}
	/**
	*科室其他费用表<BR>
	*查询
	*/
	@RequestMapping(value = "/hrp/cost/costohterdetail/queryCostOhterDetail", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> queryCostOhterDetail(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
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
		
		String costOhterDetail = costOhterDetailService.queryCostOhterDetail(getPage(mapVo));

		return JSONObject.parseObject(costOhterDetail);
		
	}
	/**
	*科室其他费用表<BR>
	*删除
	*/
	@RequestMapping(value = "/hrp/cost/costohterdetail/deleteCostOhterDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteCostOhterDetail(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		for ( String id: paramVo.split(",")) {
			String[] ids=id.split("@");
			Map<String, Object> mapVo=new HashMap<String, Object>();
			mapVo.put("group_id",ids[0]);   
			
			mapVo.put("hos_id",ids[1]);   
			
			mapVo.put("copy_code",ids[2]);   
			
			mapVo.put("acc_year",ids[3]);   
			
			mapVo.put("acc_month",ids[4]);   
			
			mapVo.put("dept_id",ids[5]);   
						
			mapVo.put("dept_no",ids[6]);   
			
			mapVo.put("cost_item_id",ids[7]);   
			
			mapVo.put("cost_item_no",ids[8]);   
			
			mapVo.put("source_id",ids[9]); 
			
            listVo.add(mapVo);
        }
		String costOhterDetailJson = costOhterDetailService.deleteBatchCostOhterDetail(listVo);
	   return JSONObject.parseObject(costOhterDetailJson);
			
	}
	
	/**
	*科室其他费用表<BR>
	*修改页面跳转
	*/
	@RequestMapping(value = "/hrp/cost/costohterdetail/costOhterDetailUpdatePage", method = RequestMethod.GET)
	
	public String costOhterDetailUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if(mapVo.get("group_id") == null){
		mapVo.put("group_id", SessionManager.getGroupId());
		}
		if(mapVo.get("hos_id") == null){
		mapVo.put("hos_id", SessionManager.getHosId());
		}
		if(mapVo.get("copy_code") == null){
        mapVo.put("copy_code", SessionManager.getCopyCode());
		}
        CostOhterDetail costOhterDetail = new CostOhterDetail();
        
        String para_value = (String) ((SessionManager.getCostParaMap().get("03001")==null)?0:SessionManager.getCostParaMap().get("03001").toString());
		
		mapVo.put("is_flag", para_value);
        
		costOhterDetail = costOhterDetailService.queryCostOhterDetailByCode(mapVo);
		
		mode.addAttribute("group_id", costOhterDetail.getGroup_id());
		
		mode.addAttribute("hos_id", costOhterDetail.getHos_id());
		
		mode.addAttribute("copy_code", costOhterDetail.getCopy_code());
		
		mode.addAttribute("year_month", costOhterDetail.getAcc_year().toString() + costOhterDetail.getAcc_month().toString());
		
		mode.addAttribute("acc_year", costOhterDetail.getAcc_year());
		mode.addAttribute("acc_month", costOhterDetail.getAcc_month());
		
		mode.addAttribute("dept_id", costOhterDetail.getDept_id());
		
		mode.addAttribute("dept_no", costOhterDetail.getDept_no());
		
		mode.addAttribute("dept_code", costOhterDetail.getDept_code());
		
		mode.addAttribute("dept_name", costOhterDetail.getDept_name());
		
		mode.addAttribute("cost_item_id", costOhterDetail.getCost_item_id());
		
		mode.addAttribute("cost_item_no", costOhterDetail.getCost_item_no());
		
		mode.addAttribute("cost_item_code", costOhterDetail.getCost_item_code());
		
		mode.addAttribute("cost_item_name", costOhterDetail.getCost_item_name());
		
		mode.addAttribute("source_id", costOhterDetail.getSource_id());
		
		mode.addAttribute("source_code", costOhterDetail.getSource_code());
		
		mode.addAttribute("source_name", costOhterDetail.getSource_name());
		
		mode.addAttribute("amount", costOhterDetail.getAmount());
		
		return "hrp/cost/costohterdetail/costOhterDetailUpdate";
	}
	/**
	*科室其他费用表<BR>
	*修改保存
	*/	
	
	@RequestMapping(value = "/hrp/cost/costohterdetail/updateCostOhterDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateCostOhterDetail(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if(mapVo.get("group_id") == null){
		mapVo.put("group_id", SessionManager.getGroupId());
		}
		if(mapVo.get("hos_id") == null){
		mapVo.put("hos_id", SessionManager.getHosId());
		}
		if(mapVo.get("copy_code") == null){
        mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		String costOhterDetailJson = costOhterDetailService.updateCostOhterDetail(mapVo);
		
		return JSONObject.parseObject(costOhterDetailJson);
	}
	
    // 导入页面
	@RequestMapping(value = "/hrp/cost/costohterdetail/costOhterDetailImportPage", method = RequestMethod.GET)
	public String costOhterDetailImportPage(Model mode) throws Exception {

		return "hrp/cost/costohterdetail/costOhterDetailImport";

	}
	//下载导入模版
	@RequestMapping(value="hrp/cost/costohterdetail/downTemplate", method = RequestMethod.GET)  
	 public String downTemplate(Plupload plupload,HttpServletRequest request,
	    		HttpServletResponse response,Model mode) throws IOException { 
				
	    printTemplate(request,response,"cost\\基础设置","科室其他费用采集.xls");
		
	    return null; 
	 }
	 
	/**
	*科室其他费用表<BR>
	*导入
	*/ 
	@RequestMapping(value="/hrp/cost/costohterdetail/readCostOhterDetailFiles",method = RequestMethod.POST)  
    public String readChargeKindDictFiles(Plupload plupload,HttpServletRequest request,
    		HttpServletResponse response,Model mode) throws IOException { 
			
		List<Map<String,Object>> list_batch = new ArrayList<Map<String,Object>>();	
		
		List<CostOhterDetail> list_err = new ArrayList<CostOhterDetail>();
		
		List<String[]> list = UploadUtil.readFile(plupload, request, response);
		
		try {
			for (int i = 1; i < list.size(); i++) {
				
				StringBuffer err_sb = new StringBuffer();
				
				CostOhterDetail costOhterDetail = new CostOhterDetail();
				
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
						
						costOhterDetail.setYear_month(temp[0]);
						
						costOhterDetail.setAcc_year(year_month.substring(0, 4));
						
						costOhterDetail.setAcc_month(year_month.substring(4, 6));

						mapVo.put("acc_year", year_month.substring(0, 4));
						
						mapVo.put("acc_month", year_month.substring(4, 6));

					} else {

						err_sb.append("统计年月为空  ");

					}
					if (StringUtils.isNotEmpty(temp[1])) {
						
						costOhterDetail.setDept_code(temp[1]);
						
						mapVo.put("dept_code", temp[1]);
						
					} else {
						
						err_sb.append("科室编码为空  ");
						
					}
					if (StringUtils.isNotEmpty(temp[2])) {
						
						costOhterDetail.setDept_name(temp[2]);
						
					}
					if (StringUtils.isNotEmpty(temp[3])) {
						
						costOhterDetail.setCost_item_id(Long.valueOf(temp[3]));
						
						mapVo.put("cost_item_code", temp[3]);
						
					} else {
						
						err_sb.append("成本项目编码为空  ");
						
					}
					if (StringUtils.isNotEmpty(temp[4])) {
						
						costOhterDetail.setCost_item_name(temp[4]);
						
					}
					if (StringUtils.isNotEmpty(temp[5])) {
						
						costOhterDetail.setSource_code(temp[5]);
						
						mapVo.put("source_code", temp[5]);
					} else {
						err_sb.append("资金来源编码为空  ");
					}
					if (StringUtils.isNotEmpty(temp[6])) {
						
						costOhterDetail.setSource_name(temp[6]);
						
					}
					if (StringUtils.isNotEmpty(temp[7])) {
						
						costOhterDetail.setAmount(Double.valueOf(temp[7]));
						
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
					
					costOhterDetail.setDept_code((String)mapVo.get("dept_code"));
					
				}else{
					
					costOhterDetail.setDept_code((String)mapVo.get("dept_code"));
					
					err_sb.append("科室不存在 ");
					
				}
				
				byCodeMap.put("cost_item_code", mapVo.get("cost_item_code"));

				CostItemDictNo cidn = costItemDictNoService.queryCostItemDictNoByCode(byCodeMap);

				if(cidn != null ){
					
					mapVo.put("cost_item_id", cidn.getCost_item_id());
					
					mapVo.put("cost_item_no", cidn.getCost_item_no());
					
					costOhterDetail.setCost_item_code((String)mapVo.get("cost_item_code"));
					
				}else{
					
					costOhterDetail.setCost_item_code((String)mapVo.get("cost_item_code"));
					
					err_sb.append("成本项目不存在 ");
					
				}
				
				CostRiskDetail costSource = costRiskDetailService.queryCostSource(mapVo);
				
				if(costSource != null ){
					
					mapVo.put("source_id", costSource.getSource_id());
					
					costOhterDetail.setSource_code((String)mapVo.get("source_code"));
					
				}else{
					
					costOhterDetail.setSource_code((String)mapVo.get("source_code"));
					
					err_sb.append("资金来源不存在 ");
					
				}
				
				CostOhterDetail data_exc_extis = costOhterDetailService.queryCostOhterDetailByCode(mapVo);
				
				//根据不同业务提示相关信息
				
				if (data_exc_extis != null) {
					err_sb.append("编码已经存在！ ");
				}
				if (err_sb.toString().length() > 0) {
					
					costOhterDetail.setError_type(err_sb.toString());
					
					list_err.add(costOhterDetail);
				} else {
					
					list_batch.add(mapVo);
					
				}
			}
		} catch (DataAccessException e) {
			
			CostOhterDetail data_exc = new CostOhterDetail();
			
			data_exc.setError_type("导入系统出错");
			
			list_err.add(data_exc);
		}
		
		if(list_batch.size()>0){
			
			costOhterDetailService.addBatchCostOhterDetail(list_batch);
			
		}
		
		response.getWriter().print(ChdJson.toJson(list_err, list_err.size()));
		
		return null;
    }  
	/**
	*科室其他费用表<BR>
	*批量添加
	*/ 
	@RequestMapping(value = "/hrp/cost/costohterdetail/addBatchCostOhterDetail", method = RequestMethod.POST)
	@ResponseBody
    public Map<String, Object> addBatchCostOhterDetail(@RequestParam(value = "ParamVo") String paramVo, Model mode)throws Exception{
		
		List<Map<String, Object>> list_batch = new ArrayList<Map<String, Object>>();
		
		List<CostOhterDetail> list_err = new ArrayList<CostOhterDetail>();
		
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
		
		CostRiskDetail costSource = costRiskDetailService.queryCostSource(mapVo);
		
		if(costSource != null ){
			
			mapVo.put("source_id", costSource.getSource_id());
			
		}else{
			
			err_sb.append("资金来源不存在 ");
			
		}
		   
				CostOhterDetail data_exc_extis = costOhterDetailService.queryCostOhterDetailByCode(mapVo);
				
				if (data_exc_extis != null) {
					err_sb.append("编码已经存在！ ");
				}
				
				CostOhterDetail costOhterDetail = new CostOhterDetail();
				
				if (err_sb.toString().length() > 0) {
					costOhterDetail.setYear_month(mapVo.get("acc_year").toString() + mapVo.get("acc_month").toString());
					
					costOhterDetail.setAcc_year(mapVo.get("acc_year").toString());
					
					costOhterDetail.setAcc_month(mapVo.get("acc_month").toString());
					
					costOhterDetail.setDept_code(mapVo.get("dept_code").toString());
					
					costOhterDetail.setDept_name(mapVo.get("dept_name").toString());
					
					costOhterDetail.setCost_item_code(mapVo.get("cost_item_code").toString());
					
					costOhterDetail.setCost_item_name(mapVo.get("cost_item_name").toString());
					
					costOhterDetail.setSource_code(mapVo.get("source_code").toString());
					
					costOhterDetail.setSource_name(mapVo.get("source_name").toString());
					
					costOhterDetail.setAmount(Double.valueOf(mapVo.get("amount").toString()));
					
					
					costOhterDetail.setError_type(err_sb.toString());
					
					list_err.add(costOhterDetail);
				} else {
					
					list_batch.add(mapVo);
					
				}
			}
			
			if(list_batch.size()>0){
				
				costOhterDetailService.addBatchCostOhterDetail(list_batch);
				
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

