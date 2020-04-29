/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */

package com.chd.hrp.med.controller.account.report;

import java.util.ArrayList;
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
import com.chd.base.MyConfig;
import com.chd.base.SessionManager;
import com.chd.hrp.med.service.account.report.MedAccountReportInvStockSetService;

/**
 * 
 * @Description:  081101 药品库存汇总表设置
 * @Table: MED_SHOW_SET
 * @Author: bell
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Controller
public class MedAccountReportInvStockSetController extends BaseController {

	private static Logger logger = Logger.getLogger(MedAccountReportInvStockSetController.class);

	// 引入Service服务
	@Resource(name = "medAccountReportInvStockSetService")
	private final MedAccountReportInvStockSetService medAccountReportInvStockSetService = null;
	
	/**
	 * @Description 主页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/account/report/medInvStockSet/mainPage", method = RequestMethod.GET)
	public String MedShowSetMainPage(Model mode) throws Exception {

		return "hrp/med/account/report/medInvStockSet/main";
	}

	/**
	 * @Description 
	 * 添加页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/med/account/report/medInvStockSet/addPage", method = RequestMethod.GET)
	public String MedShowSetAddPage(Model mode) throws Exception {

		return "hrp/med/account/report/medInvStockSet/add";
	}

	/**
	 * @Description 查询数据  
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/account/report/medInvStockSet/queryMedShowSet", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMedShowSet(@RequestParam Map<String, Object> mapVo, Model mode)throws Exception {
		//添加默认信息
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("user_id", SessionManager.getUserId());
		mapVo.put("show_history", MyConfig.getSysPara("03001"));
		
		String medJson = medAccountReportInvStockSetService.queryMedShowSet(getPage(mapVo));
		
		return JSONObject.parseObject(medJson);
	}

	/**
	 * @Description 添加数据  
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/account/report/medInvStockSet/addMedShowSet", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addMedShowSet(@RequestParam Map<String, Object> mapVo, Model mode)throws Exception {
		
		//添加默认数据
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("user_id", SessionManager.getUserId());
		
		String medJson;
		try {
			medJson = medAccountReportInvStockSetService.addMedShowSet(mapVo);
		} catch (Exception e) {
			medJson = e.getMessage();
		}

		return JSONObject.parseObject(medJson);
	}

	/**
	 * @Description 
	 * 更新跳转页面
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/med/account/report/medInvStockSet/updatePage", method = RequestMethod.GET)
	public String medShowSetUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("user_id", SessionManager.getUserId());
		mapVo.put("show_history", MyConfig.getSysPara("03001"));
		
		mode.addAttribute("medShowSet", medAccountReportInvStockSetService.queryMedShowSetByCode(mapVo));
		return "hrp/med/account/report/medInvStockSet/update";
	}

	/**
	 * @Description 更新数据  
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/account/report/medInvStockSet/updateMedShowSet", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateMedShowSet(@RequestParam Map<String, Object> mapVo, Model mode)throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("user_id", SessionManager.getUserId());
		
		String medJson;
		try {
			medJson = medAccountReportInvStockSetService.updateMedShowSet(mapVo);
		} catch (Exception e) {
			medJson = e.getMessage();
		}

		return JSONObject.parseObject(medJson);
	}

	/**
	 * @Description 删除数据  
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/account/report/medInvStockSet/deleteMedShowSet", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteMedShowSet(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		for ( String id: paramVo.split(",")) {
			Map<String, Object> mapVo=new HashMap<String, Object>();
			String[] ids=id.split("@");
			//表的主键
			mapVo.put("group_id", ids[0]);
			mapVo.put("hos_id", ids[1]);
			mapVo.put("copy_code", ids[2]);
			mapVo.put("show_id", ids[3]);
			listVo.add(mapVo);
		}
		String medJson = medAccountReportInvStockSetService.deleteMedShowSetBatch(listVo);
		
		return JSONObject.parseObject(medJson);
	}

	/**
	 * @Description 
	 * 明细设置跳转页面
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/med/account/report/medInvStockSet/busRealPage", method = RequestMethod.GET)
	public String medShowSetDetailPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mode.addAttribute("group_id", mapVo.get("group_id"));
		mode.addAttribute("hos_id", mapVo.get("hos_id"));
		mode.addAttribute("copy_code", mapVo.get("copy_code"));
		mode.addAttribute("show_id", mapVo.get("show_id"));
		mode.addAttribute("show_name", mapVo.get("show_name"));
		
		return "hrp/med/account/report/medInvStockSet/busReal";
	}

	/**
	 * @Description 查询明细数据  
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/account/report/medInvStockSet/queryMedShowDetailByCode", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMedShowDetailByCode(@RequestParam Map<String, Object> mapVo, Model mode)throws Exception {
		//添加默认信息
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("user_id", SessionManager.getUserId());
		mapVo.put("show_history", MyConfig.getSysPara("03001"));
		
		String medJson = medAccountReportInvStockSetService.queryMedShowDetailByCode(mapVo);
		
		return JSONObject.parseObject(medJson);
	}

	/**
	 * @Description 添加数据  
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/account/report/medInvStockSet/addMedShowDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addMedShowDetail(@RequestParam Map<String, Object> mapVo, Model mode)throws Exception {
		
		//添加默认数据
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("user_id", SessionManager.getUserId());
		
		String medJson = medAccountReportInvStockSetService.addMedShowDetail(mapVo);

		return JSONObject.parseObject(medJson);
	}
}
