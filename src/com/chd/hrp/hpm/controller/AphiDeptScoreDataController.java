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
import com.chd.base.SessionManager;
import com.chd.base.util.Plupload;
import com.chd.hrp.hpm.entity.AphiDeptScoreData;
import com.chd.hrp.hpm.service.AphiDeptScoreDataService;

/**
 * 科室绩效结果数据准备
 * @author Administrator
 *
 */
@Controller
public class AphiDeptScoreDataController extends BaseController{

		private static Logger logger = Logger.getLogger(AphiDeptController.class);
		
		@Resource(name = "aphiDeptScoreDataService")
		private final AphiDeptScoreDataService aphiDeptScoreDataService = null;
	
		
		/**
		 * 主页面
		 * @param mode
		 * @return
		 * @throws Exception
		 */
		@RequestMapping(value = "/hrp/hpm/hpmdeptscoredata/hpmDeptScoreDataMainPage",method = RequestMethod.GET)
		public String hpmDeptScoreDataMainPage(Model mode)throws Exception{
			
			return "hrp/hpm/hpmdeptscoredata/hpmDeptScoreDataMain";
		}
	
	
		/**
		 * 查询
		 * @param mapVo
		 * @param mode
		 * @return
		 * @throws Exception
		 */
		@RequestMapping(value = "/hrp/hpm/hpmdeptscoredata/queryHpmDeptScoreData",method = RequestMethod.POST)
		@ResponseBody
		public Map<String,Object> queryHpmDeptScoreData(@RequestParam Map<String, Object> mapVo,Model mode)throws Exception{
			
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
			
			String returnStr = aphiDeptScoreDataService.queryHpmDeptScoreData(getPage(mapVo));
			return JSONObject.parseObject(returnStr);
		}
		
		/**
		 * 添加页面
		 * @param mapVo
		 * @param mode
		 * @return
		 * @throws Exception
		 */
		@RequestMapping(value = "/hrp/hpm/hpmdeptscoredata/addHpmDeptScoreDataPage",method = RequestMethod.GET)
		public String addHpmDeptScoreDataPage(@RequestParam Map<String, Object> mapVo,Model mode)throws Exception{
			
			return "hrp/hpm/hpmdeptscoredata/hpmDeptScoreDataAdd";
		}
		
		/**
		 * 添加保存
		 * @param mapVo
		 * @param mode
		 * @return
		 * @throws Exception
		 */
		@RequestMapping(value = "/hrp/hpm/hpmdeptscoredata/addHpmDeptScoreData",method = RequestMethod.POST)
		@ResponseBody
		public Map<String,Object> addHpmDeptScoreData(@RequestParam Map<String, Object> mapVo,Model mode)throws Exception{
			
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
			
			String returnStr = aphiDeptScoreDataService.addHpmDeptScoreData(mapVo);
			return JSONObject.parseObject(returnStr);
		}
		
		/**
		 * 修改页面
		 * @param mapVo
		 * @param mode
		 * @return
		 * @throws Exception
		 */
		@RequestMapping(value = "/hrp/hpm/hpmdeptscoredata/updateHpmDeptScoreDataPage",method = RequestMethod.GET)
		public String updateHpmDeptScoreDataPage(@RequestParam Map<String, Object> mapVo,Model mode)throws Exception{
			
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
			
			AphiDeptScoreData aphiDeptScoreData = aphiDeptScoreDataService.queryDeptScoreDataByCode(mapVo);
			
			mode.addAttribute("group_id",mapVo.get("group_id"));
			mode.addAttribute("hos_id",mapVo.get("hos_id"));
			mode.addAttribute("copy_code", aphiDeptScoreData.getCopy_code());
			mode.addAttribute("acct_year",aphiDeptScoreData.getAcct_year());
			mode.addAttribute("acct_month", aphiDeptScoreData.getAcct_month());
			mode.addAttribute("dept_id", aphiDeptScoreData.getDept_id());
			mode.addAttribute("dept_name", aphiDeptScoreData.getDept_name());
			mode.addAttribute("dept_no", aphiDeptScoreData.getDept_no());
			mode.addAttribute("c_score", aphiDeptScoreData.getC_score());
			mode.addAttribute("f_score", aphiDeptScoreData.getF_score());
			mode.addAttribute("l_score", aphiDeptScoreData.getL_score());
			mode.addAttribute("p_score", aphiDeptScoreData.getP_score());
			mode.addAttribute("root_score", aphiDeptScoreData.getRoot_score());
			
			return "hrp/hpm/hpmdeptscoredata/hpmDeptScoreDataUpdate";
		}
		
		
		/**
		 * 修改保存
		 * @param mapVo
		 * @param mode
		 * @return
		 * @throws Exception
		 */
		@RequestMapping(value = "/hrp/hpm/hpmdeptscoredata/updateHpmDeptScoreData",method = RequestMethod.POST)
		@ResponseBody
		public Map<String,Object> updateHpmDeptScoreData(@RequestParam Map<String, Object> mapVo,Model mode)throws Exception{
			
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
			
			
			String returnStr = aphiDeptScoreDataService.updateHpmDeptScoreData(mapVo);
			return JSONObject.parseObject(returnStr);
		}
		
		
		/**
		 * 删除
		 * @param mapVo
		 * @param mode
		 * @return
		 * @throws Exception
		 */
		@RequestMapping(value = "/hrp/hpm/hpmdeptscoredata/deleteHpmDeptScoreData",method = RequestMethod.POST)
		@ResponseBody
		public Map<String,Object> deleteHpmDeptScoreData(@RequestParam String checkIds,Model mode)throws Exception{
			
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
			
			
			
			String returnStr = aphiDeptScoreDataService.deleteHpmDeptScoreData(mapVo,checkIds);
			return JSONObject.parseObject(returnStr);
		}
		
		/**
		 * 导入页面
		 * @param mapVo
		 * @param mode
		 * @return
		 * @throws Exception
		 */
		@RequestMapping(value = "/hrp/hpm/hpmdeptscoredata/hpmDeptScoreDataImpotrPage",method = RequestMethod.GET)
		public String hpmDeptScoreDataImpotrPage(@RequestParam Map<String, Object> mapVo,Model mode)throws Exception{
			
			
			return "hrp/hpm/hpmdeptscoredata/hpmDeptScoreDataImport";
		}
		
		/**
		 * 导入
		 * @param mapVo
		 * @param mode
		 * @return
		 * @throws Exception
		 */
		@RequestMapping(value = "/hrp/hpm/hpmdeptscoredata/hpmDeptScoreDataImpotr",method = RequestMethod.POST)
		@ResponseBody
		public String importHpmDeptScoreData(@RequestParam Map<String, Object> mapVo,Model mode)throws Exception{
			
			try {
				String impJson = aphiDeptScoreDataService.importHpmDeptScoreData(mapVo);
				return impJson;
			} catch (Exception e) {
				// TODO: handle exception
				return "{\"error\":\""+e.getMessage()+"\"}";
			}
		}
		
		/**
		 * 下载模板
		 * @param plupload
		 * @param request
		 * @param response
		 * @param mode
		 * @return
		 * @throws IOException
		 */
		@RequestMapping(value = "/hrp/hpm/hpmdeptscoredata/downTemplateHpmDeptScoreData",method = RequestMethod.GET)
		public String downTemplateHpmDeptScoreData(Plupload plupload, HttpServletRequest request, HttpServletResponse response, Model mode) throws IOException {
			
			printTemplate(request, response, "hpm\\downTemplate", "科室绩效结果数据准备.xlsx");
			
			return null;
		}
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
	
}
