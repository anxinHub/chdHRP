package com.chd.hrp.hpm.controller;

import java.io.IOException;
import java.util.*;

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
import com.chd.hrp.hpm.entity.AphiDeptDutyData;
import com.chd.hrp.hpm.service.AphiDeptDutyDataService;

/**
 * 科室岗位系数数据准备
 * @author Administrator
 *
 */
@Controller
public class AphiDeptDutyDataController extends BaseController{

		private static Logger logger = Logger.getLogger(AphiDeptController.class);
		
		@Resource(name = "aphiDeptDutyDataService")
		private final AphiDeptDutyDataService  aphiDeptDutyDataService = null;
		
		
		/**
		 * 主页面
		 * @param mode
		 * @return
		 * @throws Exception
		 */
		@RequestMapping(value = "/hrp/hpm/hpmdeptdutydata/hpmDeptDutyDataMainPage",method = RequestMethod.GET)
		public String hpmDeptDutyDataMainPage(Model mode)throws Exception{
			
			return "hrp/hpm/hpmdeptdutydata/hpmDeptDutyDataMain";
		}
		
		
		/**
		 * 查询
		 * @param mapVo
		 * @param mode
		 * @return
		 * @throws Exception
		 */
		@RequestMapping(value = "/hrp/hpm/hpmdeptdutydata/queryHpmDeptDutyData",method = RequestMethod.POST)
		@ResponseBody
		public Map<String,Object> queryHpmDeptDutyData(@RequestParam Map<String, Object> mapVo,Model mode)throws Exception{
			
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
			if(MyConfig.getSysPara("09004") == null){
				mapVo.put("para_value", 0);
			}else{
				mapVo.put("para_value", MyConfig.getSysPara("09004"));
			}
			
			String returnStr = aphiDeptDutyDataService.queryHpmDeptDutyData(getPage(mapVo));
			return JSONObject.parseObject(returnStr);
		}
		
		/**
		 * 添加页面
		 * @return
		 */
		@RequestMapping(value = "/hrp/hpm/hpmdeptdutydata/addHpmDeptDutyDataPage",method = RequestMethod.GET)
		public String addHpmDeptDutyDataPage(@RequestParam Map<String, Object> mapVo,Model mode)throws Exception{
			
			return "hrp/hpm/hpmdeptdutydata/hpmDeptDutyDataAdd";
		}
		
		/**
		 * 添加保存
		 * @param mapVo
		 * @param mode
		 * @return
		 * @throws Exception
		 */
		@RequestMapping(value = "/hrp/hpm/hpmdeptdutydata/addHpmDeptDutyData",method = RequestMethod.POST)
		@ResponseBody
		public Map<String,Object> addHpmDeptDutyData(@RequestParam Map<String, Object> mapVo,Model mode)throws Exception{
			
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
			
			String returnStr = aphiDeptDutyDataService.addHpmDeptDutyData(mapVo);
			return JSONObject.parseObject(returnStr);
		}
		
		/**
		 * 修改页面
		 * @param mapVo
		 * @param mode
		 * @return
		 * @throws Exception
		 */
		@RequestMapping(value = "/hrp/hpm/hpmdeptdutydata/updateHpmDeptDutyDataPage",method = RequestMethod.GET)
		public String updateHpmDeptDutyDataPage(@RequestParam Map<String, Object> mapVo,Model mode)throws Exception{
			
			if (mapVo.get("group_id") == null) {
				mapVo.put("group_id", SessionManager.getGroupId());
			}
	
			if (mapVo.get("hos_id") == null) {
				mapVo.put("hos_id", SessionManager.getHosId());
			}
	
			if (mapVo.get("copy_code") == null) {
				mapVo.put("copy_code", SessionManager.getCopyCode());
			}
			
			AphiDeptDutyData aphiDeptDutyData = aphiDeptDutyDataService.queryDeptDutyDataByCode(mapVo);
			mode.addAttribute("group_id",mapVo.get("group_id"));
			mode.addAttribute("hos_id",mapVo.get("hos_id"));
			mode.addAttribute("copy_code",aphiDeptDutyData.getCopy_code());
			mode.addAttribute("dept_id",aphiDeptDutyData.getDept_id());
			mode.addAttribute("dept_no",aphiDeptDutyData.getDept_no());
			mode.addAttribute("dept_name",aphiDeptDutyData.getDept_name());
			mode.addAttribute("acct_year",aphiDeptDutyData.getAcct_year());
			mode.addAttribute("acct_month",aphiDeptDutyData.getAcct_month());
			mode.addAttribute("dept_duty_amount",aphiDeptDutyData.getDept_duty_amount());
			
			return "hrp/hpm/hpmdeptdutydata/hpmDeptDutyDataUpdate";
		}
		
		/**
		 * 修改保存
		 * @param mapVo
		 * @param mode
		 * @return
		 * @throws Exception
		 */
		@RequestMapping(value ="/hrp/hpm/hpmdeptdutydata/updateHpmDeptDutyData",method = RequestMethod.POST)
		@ResponseBody
		public Map<String,Object> updateHpmDeptDutyData(@RequestParam Map<String, Object> mapVo,Model mode)throws Exception{
			
			if (mapVo.get("group_id") == null) {
				mapVo.put("group_id", SessionManager.getGroupId());
			}
	
			if (mapVo.get("hos_id") == null) {
				mapVo.put("hos_id", SessionManager.getHosId());
			}
	
			if (mapVo.get("copy_code") == null) {
				mapVo.put("copy_code", SessionManager.getCopyCode());
			}
			
			
			
			
			String returnStr = aphiDeptDutyDataService.updateHpmDeptDutyData(mapVo);
			return JSONObject.parseObject(returnStr);
		}
		
		
		/**
		 * 删除
		 * @param mapVo
		 * @param mode
		 * @return
		 * @throws Exception
		 */
		@RequestMapping(value = "/hrp/hpm/hpmdeptdutydata/deleteHpmDeptDutyData",method = RequestMethod.POST)
		@ResponseBody
		public Map<String,Object> deleteHpmDeptDutyData(@RequestParam String checkIds,Model mode)throws Exception{
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
			
			
			
			String returnStr = aphiDeptDutyDataService.deleteHpmDeptDutyData(mapVo,checkIds);
			return JSONObject.parseObject(returnStr);
		}
		
		
		/**
		 * 生成页面
		 * @param mapVo
		 * @param mode
		 * @return
		 * @throws Exception
		 */
		@RequestMapping(value = "/hrp/hpm/hpmdeptdutydata/initHpmDeptDutyDataPage",method = RequestMethod.GET)
		public String initHpmDeptDutyDataPage(@RequestParam Map<String, Object> mapVo,Model mode)throws Exception{
			mode.addAttribute("acct_yearm", mapVo.get("acct_yearm"));
			mode.addAttribute("dept_id", mapVo.get("dept_id"));
			mode.addAttribute("dept_no", mapVo.get("dept_no"));
			
			return "hrp/hpm/hpmdeptdutydata/hpmDeptDutyDataChoose";
		}
		
		
		/**
		 * 生成保存
		 * @param mapVo
		 * @param mode
		 * @return
		 * @throws Exception
		 */
		@RequestMapping(value = "/hrp/hpm/hpmdeptdutydata/initHpmDeptDutyData",method = RequestMethod.POST)
		@ResponseBody
		public Map<String,Object> initHpmDeptDutyData(@RequestParam Map<String, Object> mapVo,Model mode)throws Exception{
			
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
			
			String returnStr = aphiDeptDutyDataService.initHpmDeptDutyData(mapVo);
			return JSONObject.parseObject(returnStr);
		}
		
		
		/**
		 * 计算
		 * @param mapVo
		 * @param mode
		 * @return
		 * @throws Exception
		 */
		@RequestMapping(value = "/hrp/hpm/hpmdeptdutydata/collectHpmDeptDutyData",method = RequestMethod.POST)
		@ResponseBody
		public Map<String,Object> collectHpmDeptDutyData(@RequestParam Map<String, Object> mapVo,Model mode)throws Exception{
			
			if (mapVo.get("group_id") == null) {
				mapVo.put("group_id", SessionManager.getGroupId());
			}
			if (mapVo.get("hos_id") == null) {
				mapVo.put("hos_id", SessionManager.getHosId());
			}
			if (mapVo.get("copy_code") == null) {
				mapVo.put("copy_code", SessionManager.getCopyCode());
			}
			
			if(MyConfig.getSysPara("09001") == null){
				mapVo.put("para_value", 0);
			}else{
				mapVo.put("para_value", MyConfig.getSysPara("09001"));
			}
			
			if (!"".equals(mapVo.get("acct_yearm")) && mapVo.get("acct_yearm") != null) {

				String acct_year = mapVo.get("acct_yearm").toString().substring(0, 4);

				String acct_month = mapVo.get("acct_yearm").toString().substring(4);

				mapVo.put("acct_year", acct_year);

				mapVo.put("acct_month", acct_month);
			}
			
			
			String returnStr = aphiDeptDutyDataService.collectHpmDeptDutyData(mapVo);
			return JSONObject.parseObject(returnStr);
		}
		
		
		/**
		 * 导入页面
		 * @param mode
		 * @return
		 * @throws Exception
		 */
		@RequestMapping(value = "/hrp/hpm/hpmdeptdutydata/hpmDeptDutyDataImportPage",method = RequestMethod.GET)
		public String hpmDeptDutyDataImportPage(Model mode)throws Exception{
			
			return "hrp/hpm/hpmdeptdutydata/hpmDeptDutyDataImport";
		}
		
		/**
		 * 导入跳转
		 * @param mapVo
		 * @param mode
		 * @return
		 * @throws Exception
		 */
		@RequestMapping(value = "/hrp/hpm/hpmdeptdutydata/hpmDeptDutyDataImport",method = RequestMethod.POST)
		@ResponseBody
		public String hpmDeptDutyDataImport(@RequestParam Map<String, Object> mapVo,Model mode)throws Exception{
			try {
				String impJson = aphiDeptDutyDataService.hpmDeptDutyDataImport(mapVo);
				return impJson;
			} catch (Exception e) {
				// TODO: handle exception
				return "{\"error\":\""+e.getMessage()+"\"}";
			}
			
		}
		
		//下载导入模板
		@RequestMapping(value = "/hrp/hpm/hpmdeptdutydata/downTemplateHpmDeptDutyData",method = RequestMethod.GET)
		public String downTemplateHpmDeptDutyData(Plupload plupload, HttpServletRequest request, HttpServletResponse response, Model mode) throws IOException {
			
			printTemplate(request, response, "hpm\\downTemplate", "科室岗位系数数据准备.xlsx");
			
			return null;
		}
		
	
}
