/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/
package com.chd.hrp.acc.controller;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
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

import com.alibaba.fastjson.JSONObject;
import com.chd.base.BaseController;
import com.chd.base.MyConfig;
import com.chd.base.SessionManager;
import com.chd.base.util.ChdJson;
import com.chd.base.util.Plupload;
import com.chd.base.util.StringTool;
import com.chd.base.util.UploadUtil;
import com.chd.hrp.acc.entity.AccCashFlow;
import com.chd.hrp.acc.entity.AccCashFlowInit;
import com.chd.hrp.acc.entity.AccCheckItem;
import com.chd.hrp.acc.entity.AccCheckItemType;
import com.chd.hrp.acc.serviceImpl.AccCashFlowInitServiceImpl;

/**
* @Title. @Description.
* 现金流量初始帐
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


@Controller
public class AccCashFlowInitController extends BaseController{
	private static Logger logger = Logger.getLogger(AccCashFlowInitController.class);
	
	
	@Resource(name = "accCashFlowInitService")
	private final AccCashFlowInitServiceImpl accCashFlowInitService = null;
   
   
	/**
	*现金流量初始帐<BR>
	*主页面跳转
	*/
	
	@RequestMapping(value = "/hrp/acc/acccashflowinit/accCashFlowInitMainPage", method = RequestMethod.GET)
	public String accCashFlowInitMainPage(Model mode) throws Exception {
		String yearMonth = MyConfig.getCurAccYear();
		mode.addAttribute("acc_year", yearMonth.substring(0, 4));
		return "hrp/acc/acccashflowinit/accCashFlowInitMain";

	}
	/**
	*现金流量初始帐<BR>
	*添加页面跳转
	*/
	@RequestMapping(value = "/hrp/acc/acccashflowinit/accCashFlowInitAddPage", method = RequestMethod.GET)
	public String accCashFlowInitAddPage(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		mode.addAttribute("acc_year", mapVo.get("acc_year"));
		return "hrp/acc/acccashflowinit/accCashFlowInitAdd";

	}
	/**
	*现金流量初始帐<BR>
	*导入页面跳转
	*/
	@RequestMapping(value = "/hrp/acc/acccashflowinit/accCashFlowInitImportPage", method = RequestMethod.GET)
	public String accCashFlowInitImportPage(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		return "hrp/acc/acccashflowinit/accCashFlowInitImport";

	}
	/**
	*现金流量初始帐<BR>
	*保存
	*/
	@RequestMapping(value = "/hrp/acc/acccashflowinit/addAccCashFlowInit", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> addAccCashFlowInit(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if(mapVo.get("group_id") == null){
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if(mapVo.get("hos_id") == null){
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if(mapVo.get("copy_code") == null){
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		if(mapVo.get("acc_year") == null){
	        mapVo.put("acc_year", SessionManager.getAcctYear());
		}
		String accCashFlowJson = accCashFlowInitService.addAccCashFlowInit(mapVo);

		return JSONObject.parseObject(accCashFlowJson);
		
	}
	/**
	*现金流量初始帐<BR>
	*查询
	*/
	@RequestMapping(value = "/hrp/acc/acccashflowinit/queryAccCashFlowInit", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> queryAccCashFlowInit(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if(mapVo.get("group_id") == null){
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		
		if(mapVo.get("hos_id") == null){
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		
		if(mapVo.get("copy_code") == null){
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		String accCashFlow = accCashFlowInitService.queryAccCashFlowInit(getPage(mapVo));

		return JSONObject.parseObject(accCashFlow);
		
	}
	/**
	*现金流量初始帐<BR>
	*删除
	*/
	@RequestMapping(value = "/hrp/acc/acccashflowinit/deleteAccCashFlowInit", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAccCashFlowInit(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		for ( String id: paramVo.split(",")) {
			Map<String, Object> mapVo=new HashMap<String, Object>();
			mapVo.put("group_id", id.split("@")[0]);
			mapVo.put("hos_id", id.split("@")[1]);
			mapVo.put("copy_code",id.split("@")[2]);
            mapVo.put("acc_year", id.split("@")[3]);
            mapVo.put("subj_code", id.split("@")[4]);
            mapVo.put("cash_item_id", id.split("@")[5]);
            mapVo.put("subj_name", id.split("@")[6]);
            listVo.add(mapVo);
        }
		String accCashFlowJson = accCashFlowInitService.deleteBatchAccCashFlowInit(listVo);
	   return JSONObject.parseObject(accCashFlowJson);
			
	}
	
	/**
	*现金流量初始帐<BR>
	*修改页面跳转
	*/
	@RequestMapping(value = "/hrp/acc/acccashflowinit/accCashFlowInitUpdatePage", method = RequestMethod.GET)
	
	public String accCashFlowInitUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if(mapVo.get("group_id") == null){
		mapVo.put("group_id", SessionManager.getGroupId());
		}
		if(mapVo.get("hos_id") == null){
		mapVo.put("hos_id", SessionManager.getHosId());
		}
		if(mapVo.get("copy_code") == null){
        mapVo.put("copy_code", SessionManager.getCopyCode());
		}
        AccCashFlowInit accCashFlowInit = new AccCashFlowInit();
        accCashFlowInit = accCashFlowInitService.queryAccCashFlowInitByCode(mapVo);
		mode.addAttribute("group_id", accCashFlowInit.getGroup_id());
		mode.addAttribute("hos_id", accCashFlowInit.getHos_id());
		mode.addAttribute("copy_code", accCashFlowInit.getCopy_code());
		mode.addAttribute("acc_year", accCashFlowInit.getAcc_year());
		mode.addAttribute("cash_item_id", accCashFlowInit.getCash_item_id());
		mode.addAttribute("subj_id", accCashFlowInit.getSubj_id());
		mode.addAttribute("cash_money", accCashFlowInit.getCash_money());
		mode.addAttribute("summarey", accCashFlowInit.getSummary());
		return "hrp/acc/acccashflowinit/accCashFlowInitUpdate";
	}
	/**
	*现金流量初始帐<BR>
	*修改保存
	*/	
	
	@RequestMapping(value = "/hrp/acc/acccashflowinit/updateAccCashFlowInit", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateAccCashFlowInit(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		
		for ( String id: paramVo.split(",")) {
			Map<String, Object> mapVo=new HashMap<String, Object>();
			
			mapVo.put("group_id", id.split("@")[0]);
			mapVo.put("hos_id", id.split("@")[1]);
			mapVo.put("copy_code",id.split("@")[2]);
            mapVo.put("acc_year", id.split("@")[3]);
            mapVo.put("subj_code", id.split("@")[4]);
            mapVo.put("subj_name", id.split("@")[5]);
            mapVo.put("cash_item_id", id.split("@")[6]);
            mapVo.put("cash_item_name", id.split("@")[7]);
            mapVo.put("summary", id.split("@")[8]);
            mapVo.put("cash_money", id.split("@")[9]);
            mapVo.put("old_subj_code", id.split("@")[10]);
            mapVo.put("old_cash_item_id", id.split("@")[11]);
            listVo.add(mapVo);
        }
		
		String accCashFlowJson = accCashFlowInitService.updateBatchAccCashFlowInit(listVo);
		
		return JSONObject.parseObject(accCashFlowJson);
	}
	
	/**
	 * @Description 
	 * 下载导入模版 现金流量初始帐导入
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	@RequestMapping(value="hrp/acc/acccashflowinit/downTemplate", method = RequestMethod.GET)  
	 public String downTemplate(Plupload plupload,HttpServletRequest request,
	    		HttpServletResponse response,Model mode) throws IOException { 
	    			
	    printTemplate(request,response,"acc\\基础设置","现金流量初始帐模版.xls");
	    
	    return null; 
	 }
	 /**
	 * @Description 
	 * 导入数据 现金流量初始帐
	 * @param  plupload
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	 @RequestMapping(value="/hrp/acc/acccashflowinit/readAccCashFlowInitFiles",method = RequestMethod.POST)  
    public String readAccCashFlowInitFiles(Plupload plupload,HttpServletRequest request,
    		HttpServletResponse response,@RequestParam Map<String, Object> entityMap,Model mode) throws IOException { 
			 
		List<AccCashFlowInit> list_err = new ArrayList<AccCashFlowInit>();
		
		List<String[]> list = UploadUtil.readFile(plupload, request, response);
		
		List<Map<String,Object>> addList = new ArrayList<Map<String,Object>>();
		
		try {
			for (int i = 1; i < list.size(); i++){
				
				StringBuffer err_sb = new StringBuffer();
				
				AccCashFlowInit flowInit = new AccCashFlowInit();
				
				String temp[] = list.get(i);// 行
				Map<String, Object> mapVo = new HashMap<String, Object>();
				mapVo.put("group_id", SessionManager.getGroupId());
				mapVo.put("hos_id", SessionManager.getHosId());
				mapVo.put("copy_code", SessionManager.getCopyCode());
				
				for(int j = i + 1 ; j < list.size(); j++){
					String error[] = list.get(j);
					if(temp[0].equals(error[0]) && temp[1].equals(error[1]) && temp[2].equals(error[2])){
						err_sb.append("第"+i+"行数据与第 "+j+"行数据重复;");
					}
						
				}
				
					if (StringTool.isNotBlank(temp[0])) {
						
						flowInit.setAcc_year(temp[0]);
								
		    		mapVo.put("acc_year", temp[0]);
		    		
					} else {
						
						err_sb.append("年度为空 ;");
						
					}
					
					if (StringTool.isNotBlank(temp[1])) {
						
						flowInit.setSubj_id(Long.parseLong(temp[1]));
								
			    		mapVo.put("subj_id", temp[1]);
			    		
			    		//查询  会计科目  是否存在 
						int count = accCashFlowInitService.querySubjExist(mapVo);
						
						if(count == 0){
							err_sb.append("会计科目ID:"+mapVo.get("subj_id")+"不存在;");
						}
			    		
					} else {
						
						err_sb.append("科目ID为空 ;");
						
					}
					if (StringTool.isNotBlank(temp[2])) {
						
						flowInit.setCash_item_id(Long.parseLong(temp[2]));
								
						mapVo.put("cash_item_id", temp[2]);
						
						//查询  现金流量项目  是否存在 
						int conut = accCashFlowInitService.queryAccCashFlowExist(mapVo);
						
						if(conut == 0 ){
							err_sb.append("现金流量项目ID:"+mapVo.get("cash_item_id")+"不存在;");
						}
		    		
					}else{
						
						err_sb.append("现金流量项目ID为空 ;");
						
					}
					if (StringTool.isNotBlank(temp[3])) {
						
						flowInit.setSummary(temp[3]);
								
						mapVo.put("summary", temp[3]);
		    		
					}else{
						
						err_sb.append("备注为空 ;");
						
					}
					if (StringTool.isNotBlank(temp[4])) {
						
						flowInit.setCash_money(Double.parseDouble(temp[4]));
								
						mapVo.put("cash_money", temp[4]);
		    		
					}else{
						
						err_sb.append("金额为空 ;");
						
					}
					
					
					AccCashFlowInit accCashFlowInit = accCashFlowInitService.queryAccCashFlowInitByCode(mapVo);

					if (accCashFlowInit != null) {

						err_sb.append("数据已存在,不能添加.");

					}
				if (err_sb.toString().length() > 0) {
					
					flowInit.setError_type(err_sb.toString());
					
					list_err.add(flowInit);
					
				} else {
				  addList.add(mapVo);
				}
				
			}
			if(list_err.size() ==  0){
				String dataJson = accCashFlowInitService.addBatchAccCashFlowInit(addList);
			}
			
			
		} catch (DataAccessException e) {
			
			AccCashFlowInit data_exc = new AccCashFlowInit();
			
			data_exc.setError_type("导入系统出错");
			
			list_err.add(data_exc);
			
		}
		
		response.getWriter().print(ChdJson.toJson(list_err, list_err.size()));
		
		return null;
		
    }  
	
	// 科目
		@RequestMapping(value = "/hrp/acc/acccashflowinit/queryAccSubj", method = RequestMethod.POST)
		@ResponseBody
		public String queryAccSubj(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
			if (mapVo.get("group_id") == null) {
				mapVo.put("group_id", SessionManager.getGroupId());
			}
			if (mapVo.get("hos_id") == null) {
				mapVo.put("hos_id", SessionManager.getHosId());
			}
			if (mapVo.get("copy_code") == null) {
				mapVo.put("copy_code", SessionManager.getCopyCode());
			}
			if (mapVo.get("acc_year") == null) {
				mapVo.put("acc_year", SessionManager.getAcctYear());
			}

			String subj = accCashFlowInitService.queryAccSubj(mapVo);
			return subj;
		}

}

