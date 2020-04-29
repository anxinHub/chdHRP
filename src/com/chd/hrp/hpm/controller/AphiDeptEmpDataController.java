package com.chd.hrp.hpm.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
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
import com.chd.base.util.Plupload;
import com.chd.hrp.hpm.entity.AphiDeptEmpData;
import com.chd.hrp.hpm.service.AphiDeptEmpDataService;

/**
 * 科室职工人数数据准备
 * @author Administrator
 *
 */
@Controller
public class AphiDeptEmpDataController extends BaseController{
	
		private static Logger logger = Logger.getLogger(AphiDeptController.class);
		
		@Resource(name = "aphiDeptEmpDataService")
		private final AphiDeptEmpDataService aphiDeptEmpDataService = null;
		
		/**
		 * 主页面
		 * @param mode
		 * @return
		 * @throws Exception
		 */
		@RequestMapping(value = "/hrp/hpm/hpmdeptempdata/hpmDeptEmpDataMainPage",method = RequestMethod.GET)
		public String hpmDeptEmpDataMainPage(Model mode)throws Exception{
			
			return "hrp/hpm/hpmdeptempdata/hpmDeptEmpDataMain";
		}
		
		
		/**
		 * 查询
		 * @param mapVo
		 * @param mode
		 * @return
		 * @throws Exception
		 */
		@RequestMapping(value = "/hrp/hpm/hpmdeptempdata/queryHpmDeptEmpData",method = RequestMethod.POST)
		@ResponseBody
		public Map<String, Object> queryHpmDeptEmpData(@RequestParam Map<String, Object> mapVo, Model mode)throws Exception{
			
			if (mapVo.get("group_id") == null) {
				mapVo.put("group_id", SessionManager.getGroupId());
			}
			if (mapVo.get("hos_id") == null) {
				mapVo.put("hos_id", SessionManager.getHosId());
			}
			if (mapVo.get("copy_code") == null) {
				mapVo.put("copy_code", SessionManager.getCopyCode());
			}
			if (mapVo.get("user_id") == null) {
				mapVo.put("user_id", SessionManager.getUserId());
			}
			if (!"".equals(mapVo.get("acct_yearm")) && mapVo.get("acct_yearm") != null) {

				String acct_year = mapVo.get("acct_yearm").toString().substring(0, 4);

				String acct_month = mapVo.get("acct_yearm").toString().substring(4);

				mapVo.put("acct_year", acct_year);

				mapVo.put("acct_month", acct_month);
			}
			
			String returnStr = aphiDeptEmpDataService.queryHpmDeptEmpData(getPage(mapVo));
			return JSONObject.parseObject(returnStr);
		}
		
		/**
		 * 添加页面
		 * @param mode
		 * @return
		 * @throws Exception
		 */
		@RequestMapping(value = "/hrp/hpm/hpmdeptempdata/addHpmDeptEmpDataPage",method = RequestMethod.GET)
		public String addHpmDeptEmpDataPage(@RequestParam Map<String, Object> mapVo,Model mode)throws Exception{
			
			return "hrp/hpm/hpmdeptempdata/hpmDeptEmpDataAdd";
		}
		
		/**
		 * 添加跳转
		 * @param mapVo
		 * @param mode
		 * @return
		 * @throws Exception
		 */
		@RequestMapping(value = "/hrp/hpm/hpmdeptempdata/addHpmDeptEmpData",method = RequestMethod.POST)
		@ResponseBody
		public Map<String, Object> addHpmDeptEmpData(@RequestParam Map<String, Object> mapVo, Model mode)throws Exception{
			
			if (mapVo.get("group_id") == null) {
				mapVo.put("group_id", SessionManager.getGroupId());
			}
			if (mapVo.get("hos_id") == null) {
				mapVo.put("hos_id", SessionManager.getHosId());
			}
			if (mapVo.get("copy_code") == null) {
				mapVo.put("copy_code", SessionManager.getCopyCode());
			}
			
			if (!"".equals(mapVo.get("acct_yearm")) && mapVo.get("acct_yearm") != null) {

				String acct_year = mapVo.get("acct_yearm").toString().substring(0, 4);

				String acct_month = mapVo.get("acct_yearm").toString().substring(4);

				mapVo.put("acct_year", acct_year);

				mapVo.put("acct_month", acct_month);
			}
			
			String returnStr = aphiDeptEmpDataService.addHpmDeptEmpData(mapVo);
			return JSONObject.parseObject(returnStr);
		}
		
		/**
		 * 修改页面
		 * @param mapVo
		 * @param mode
		 * @return
		 * @throws Exception
		 */
		@RequestMapping(value = "/hrp/hpm/hpmdeptempdata/updateHpmDeptEmpDataPage",method = RequestMethod.GET)
		public String updateHpmDeptEmpDataPage(@RequestParam Map<String, Object> mapVo,Model mode)throws Exception{
			if (mapVo.get("group_id") == null) {
				mapVo.put("group_id", SessionManager.getGroupId());
			}
	
			if (mapVo.get("hos_id") == null) {
				mapVo.put("hos_id", SessionManager.getHosId());
			}
	
			if (mapVo.get("copy_code") == null) {
				mapVo.put("copy_code", SessionManager.getCopyCode());
			}
			
			AphiDeptEmpData aphiDeptEmpData = new AphiDeptEmpData();
			
			aphiDeptEmpData = aphiDeptEmpDataService.queryDeptEmpDataByCode(mapVo);
			mode.addAttribute("group_id",mapVo.get("group_id"));
			mode.addAttribute("hos_id",mapVo.get("hos_id"));
			mode.addAttribute("copy_code",aphiDeptEmpData.getCopy_code());
			mode.addAttribute("acct_year",aphiDeptEmpData.getAcct_year());
			mode.addAttribute("acct_month",aphiDeptEmpData.getAcct_month());
			mode.addAttribute("dept_id",aphiDeptEmpData.getDept_id());
			mode.addAttribute("dept_no",aphiDeptEmpData.getDept_no());
			mode.addAttribute("dept_name",aphiDeptEmpData.getDept_name());
			mode.addAttribute("emp_amount",aphiDeptEmpData.getEmp_amount());
			mode.addAttribute("error_type",aphiDeptEmpData.getError_type());
			
			return "hrp/hpm/hpmdeptempdata/hpmDeptEmpDataUpdate";
		}
		
		/**
		 * 修改跳转
		 * @param mapVo
		 * @param mode
		 * @return
		 * @throws Exception
		 */
		@RequestMapping(value = "/hrp/hpm/hpmdeptempdata/updateHpmDeptEmpData",method = RequestMethod.POST)
		@ResponseBody
		public Map<String,Object> updateHpmDeptEmpData(@RequestParam Map<String, Object> mapVo,Model mode)throws Exception{
			if (mapVo.get("group_id") == null) {
				mapVo.put("group_id", SessionManager.getGroupId());
			}
	
			if (mapVo.get("hos_id") == null) {
				mapVo.put("hos_id", SessionManager.getHosId());
			}
	
			if (mapVo.get("copy_code") == null) {
				mapVo.put("copy_code", SessionManager.getCopyCode());
			}
			
			
			String returnStr = aphiDeptEmpDataService.updateHpmDeptEmpData(mapVo);
			return JSONObject.parseObject(returnStr);
		}
		
		/**
		 * 删除	
		 * @param mapVo
		 * @param mode
		 * @return
		 * @throws Exception
		 */
		@RequestMapping(value = "/hrp/hpm/hpmdeptempdata/deleteHpmDeptEmpData",method = RequestMethod.POST)
		@ResponseBody
		public Map<String,Object> deleteHpmDeptEmpData(@RequestParam String checkIds,Model mode)throws Exception{
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
			
			
			String returnStr = aphiDeptEmpDataService.deleteHpmDeptEmpData(mapVo,checkIds);
			return JSONObject.parseObject(returnStr);
		}
	
		/**
		 * 生成页面
		 * @param mapVo
		 * @param mode
		 * @return
		 * @throws Exception
		 */
		@RequestMapping(value = "/hrp/hpm/hpmdeptempdata/initHpmDeptEmpDataPage",method = RequestMethod.GET)
		
		public String initHpmDeptEmpDataPage(@RequestParam Map<String, Object> mapVo,Model mode)throws Exception{
			
			if (mapVo.get("group_id") == null) {
				mapVo.put("group_id", SessionManager.getGroupId());
			}
	
			if (mapVo.get("hos_id") == null) {
				mapVo.put("hos_id", SessionManager.getHosId());
			}
	
			if (mapVo.get("copy_code") == null) {
				mapVo.put("copy_code", SessionManager.getCopyCode());
			}
			
			if( MyConfig.getSysPara("09001") == null){
				mapVo.put("para_value",0);
			}else{
				mapVo.put("para_value", MyConfig.getSysPara("09001"));
			}
			
			
			mode.addAttribute("acct_yearm", mapVo.get("acct_yearm"));
			return "hrp/hpm/hpmdeptempdata/hpmDeptEmpDataFast";
		}
		
		/**
		 * 生成跳转
		 * @param mapVo
		 * @param mode
		 * @return
		 */
		@RequestMapping(value = "/hrp/hpm/hpmdeptempdata/initHpmDeptEmpData",method = RequestMethod.POST)
		@ResponseBody
		public Map<String,Object> initHpmDeptEmpData(@RequestParam Map<String, Object> mapVo,Model mode){
			if (mapVo.get("group_id") == null) {
				mapVo.put("group_id", SessionManager.getGroupId());
			}
	
			if (mapVo.get("hos_id") == null) {
				mapVo.put("hos_id", SessionManager.getHosId());
			}
	
			if (mapVo.get("copy_code") == null) {
				mapVo.put("copy_code", SessionManager.getCopyCode());
			}
			if (!"".equals(mapVo.get("acct_yearm")) && mapVo.get("acct_yearm") != null) {

				String acct_year = mapVo.get("acct_yearm").toString().substring(0, 4);

				String acct_month = mapVo.get("acct_yearm").toString().substring(4);

				mapVo.put("acct_year", acct_year);

				mapVo.put("acct_month", acct_month);
			}
			
			String returnStr = aphiDeptEmpDataService.initHpmDeptEmpData(mapVo);
			return JSONObject.parseObject(returnStr);
		}
	
		/**
		 * 计算
		 * @param mapVo
		 * @param mode
		 * @return
		 * @throws Exception
		 */
		@RequestMapping(value = "/hrp/hpm/hpmdeptempdata/collectHpmDeptEmpData",method = RequestMethod.POST)
		@ResponseBody
		public Map<String,Object> collectHpmDeptEmpData(@RequestParam Map<String, Object> mapVo,Model mode)throws Exception{
			
			if (mapVo.get("group_id") == null) {
				mapVo.put("group_id", SessionManager.getGroupId());
			}
	
			if (mapVo.get("hos_id") == null) {
				mapVo.put("hos_id", SessionManager.getHosId());
			}
	
			if (mapVo.get("copy_code") == null) {
				mapVo.put("copy_code", SessionManager.getCopyCode());
			}
			if (!"".equals(mapVo.get("acct_yearm")) && mapVo.get("acct_yearm") != null) {

				String acct_year = mapVo.get("acct_yearm").toString().substring(0, 4);

				String acct_month = mapVo.get("acct_yearm").toString().substring(4);

				mapVo.put("acct_year", acct_year);

				mapVo.put("acct_month", acct_month);
			}
			
			if(MyConfig.getSysPara("09001") == null){
				mapVo.put("para_value", 0);
			}else{
				mapVo.put("para_value", MyConfig.getSysPara("09001"));
			}
			
			
			String returnStr = aphiDeptEmpDataService.collectHpmDeptEmpData(mapVo);
			return JSONObject.parseObject(returnStr);
		}
		
		/**
		 * 导入页面
		 * @param mode
		 * @return
		 * @throws Exception
		 */
		@RequestMapping(value = "/hrp/hpm/hpmdeptempdata/hpmDeptEmpDataImportPage",method = RequestMethod.GET)
		public String hpmDeptEmpDataImportPage(Model mode)throws Exception{
			
			return "hrp/hpm/hpmdeptempdata/hpmDeptEmpDataImport";
		}
		
		//导入跳转
		@RequestMapping(value = "/hrp/hpm/hpmdeptempdata/hpmDeptEmpDataImport",method = RequestMethod.POST)
		@ResponseBody
		public String hpmDeptEmpDataImport(@RequestParam Map<String, Object> mapVo,Model mode)throws Exception{
			try {
				String impJson = aphiDeptEmpDataService.hpmDeptEmpDataImport(mapVo);
				return impJson;
			} catch (Exception e) {
				// TODO: handle exception
				return "{\"error\":\""+e.getMessage()+"\"}";
			}
		}
		
		//下载导入模板
		@RequestMapping(value = "/hrp/hpm/hpmdeptempdata/downTemplateHpmDeptEmpData",method = RequestMethod.GET)
		public String downTemplatehpmDeptEmpData(Plupload plupload, HttpServletRequest request, HttpServletResponse response, Model mode) throws IOException {
			
			printTemplate(request, response, "hpm\\downTemplate", "科室人数数据准备.xlsx");
			
			return null;
		}

}
