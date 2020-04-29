/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */

package com.chd.hrp.mat.controller.account.report;

import java.util.*;

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
import com.chd.hrp.mat.service.account.report.MatAccountReportInvStockSetService;

/**
 * 
 * @Description:  041101 材料库存汇总表设置
 * @Table: MAT_SHOW_SET
 * @Author: bell
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Controller
public class MatAccountReportInvStockSetController extends BaseController {

	private static Logger logger = Logger.getLogger(MatAccountReportInvStockSetController.class);

	// 引入Service服务
	@Resource(name = "matAccountReportInvStockSetService")
	private final MatAccountReportInvStockSetService matAccountReportInvStockSetService = null;
	
	/**
	 * @Description 主页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/account/report/matInvStockSet/mainPage", method = RequestMethod.GET)
	public String MatShowSetMainPage(Model mode) throws Exception {

		return "hrp/mat/account/report/matInvStockSet/main";
	}

	/**
	 * @Description 
	 * 添加页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/mat/account/report/matInvStockSet/addPage", method = RequestMethod.GET)
	public String MatShowSetAddPage(Model mode) throws Exception {

		return "hrp/mat/account/report/matInvStockSet/add";
	}

	/**
	 * @Description 查询数据  
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/account/report/matInvStockSet/queryMatShowSet", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMatShowSet(@RequestParam Map<String, Object> mapVo, Model mode)throws Exception {
		//添加默认信息
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("user_id", SessionManager.getUserId());
		mapVo.put("show_history", MyConfig.getSysPara("03001"));
		
		String matJson = matAccountReportInvStockSetService.queryMatShowSet(getPage(mapVo));
		
		return JSONObject.parseObject(matJson);
	}

	/**
	 * @Description 添加数据  
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/account/report/matInvStockSet/addMatShowSet", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addMatShowSet(@RequestParam Map<String, Object> mapVo, Model mode)throws Exception {
		
		//添加默认数据
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("user_id", SessionManager.getUserId());
		
		String matJson;
		try {
			matJson = matAccountReportInvStockSetService.addMatShowSet(mapVo);
		} catch (Exception e) {
			matJson = e.getMessage();
		}

		return JSONObject.parseObject(matJson);
	}

	/**
	 * @Description 
	 * 更新跳转页面
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/mat/account/report/matInvStockSet/updatePage", method = RequestMethod.GET)
	public String matShowSetUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("user_id", SessionManager.getUserId());
		mapVo.put("show_history", MyConfig.getSysPara("03001"));
		
		mode.addAttribute("matShowSet", matAccountReportInvStockSetService.queryMatShowSetByCode(mapVo));
		return "hrp/mat/account/report/matInvStockSet/update";
	}

	/**
	 * @Description 更新数据  
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/account/report/matInvStockSet/updateMatShowSet", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateMatShowSet(@RequestParam Map<String, Object> mapVo, Model mode)throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("user_id", SessionManager.getUserId());
		
		String matJson;
		try {
			matJson = matAccountReportInvStockSetService.updateMatShowSet(mapVo);
		} catch (Exception e) {
			matJson = e.getMessage();
		}

		return JSONObject.parseObject(matJson);
	}

	/**
	 * @Description 删除数据  
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/account/report/matInvStockSet/deleteMatShowSet", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteMatShowSet(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
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
		String matJson = matAccountReportInvStockSetService.deleteMatShowSetBatch(listVo);
		
		return JSONObject.parseObject(matJson);
	}

	/**
	 * @Description 
	 * 明细设置跳转页面
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/mat/account/report/matInvStockSet/busRealPage", method = RequestMethod.GET)
	public String matShowSetDetailPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mode.addAttribute("group_id", mapVo.get("group_id"));
		mode.addAttribute("hos_id", mapVo.get("hos_id"));
		mode.addAttribute("copy_code", mapVo.get("copy_code"));
		mode.addAttribute("show_id", mapVo.get("show_id"));
		mode.addAttribute("show_name", mapVo.get("show_name"));
		
		return "hrp/mat/account/report/matInvStockSet/busReal";
	}

	/**
	 * @Description 查询明细数据  
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/account/report/matInvStockSet/queryMatShowDetailByCode", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMatShowDetailByCode(@RequestParam Map<String, Object> mapVo, Model mode)throws Exception {
		//添加默认信息
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("user_id", SessionManager.getUserId());
		mapVo.put("show_history", MyConfig.getSysPara("03001"));
		
		String matJson = matAccountReportInvStockSetService.queryMatShowDetailByCode(mapVo);
		
		return JSONObject.parseObject(matJson);
	}

	/**
	 * @Description 添加数据  
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/account/report/matInvStockSet/addMatShowDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addMatShowDetail(@RequestParam Map<String, Object> mapVo, Model mode)throws Exception {
		
		//添加默认数据
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("user_id", SessionManager.getUserId());
		
		String matJson = matAccountReportInvStockSetService.addMatShowDetail(mapVo);

		return JSONObject.parseObject(matJson);
	}
}
