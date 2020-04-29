/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.mat.controller.payment;

import java.util.Date;
import java.util.HashMap;
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
import com.chd.base.util.DateUtil;
import com.chd.hrp.mat.service.payment.MatBillService;
/** 
 * 
 * @Description:
 * 采购发票
 * @Table:
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 

@Controller
@RequestMapping(value="/hrp/mat/payment/bill")
public class MatBillController extends BaseController{
	
	private static Logger logger = Logger.getLogger(MatBillController.class);
	
	//引入Service服务
	@Resource(name = "matBillService")
	private final MatBillService matBillService = null;
	
	/**
	 * @Description 
	 * 主页面跳转  
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/matBillListPage", method = RequestMethod.GET)
	public String matBillMainMainPage(Model mode) throws Exception {
		mode.addAttribute("p04005", MyConfig.getSysPara("04005"));
		mode.addAttribute("p04027", MyConfig.getSysPara("04027"));
		
		return "hrp/mat/payment/bill/matBillList";
	}

	/**
	 * @Description 
	 * 主页面查询
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "queryMatBillList", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMatBillList(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String retJson = matBillService.queryMatBillList(getPage(mapVo));

		return JSONObject.parseObject(retJson);
	}

	/**
	 * @Description 
	 * 添加页面跳转  
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/matBillAddPage", method = RequestMethod.GET)
	public String matBillAddPage(Model mode) throws Exception {
		mode.addAttribute("p04005", MyConfig.getSysPara("04005"));
		mode.addAttribute("p04006", MyConfig.getSysPara("04006"));
		mode.addAttribute("p04027", MyConfig.getSysPara("04027"));
		
		return "hrp/mat/payment/bill/matBillAdd";
	}

	/**
	 * @Description 
	 * 修改页面跳转  
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/matBillUpdatePage", method = RequestMethod.GET)
	public String matBillUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mode.addAttribute("p04005", MyConfig.getSysPara("04005"));
		mode.addAttribute("p04006", MyConfig.getSysPara("04006"));
		mode.addAttribute("p04027", MyConfig.getSysPara("04027"));
		//添加主表数据
		Map<String, Object> matBillMain = matBillService.queryMatBillMainById(mapVo);
		//转换发票日期
		if(matBillMain.get("bill_date") != null && !"".equals(matBillMain.get("bill_date").toString())){
			matBillMain.put("bill_date", DateUtil.dateToString((Date) matBillMain.get("bill_date"), "yyyy-MM-dd"));
		}
		mode.addAttribute("matBillMain", matBillMain);
		
		return "hrp/mat/payment/bill/matBillUpdate";
	}
	
	/**
	 * @Description 
	 * 保存单据
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/saveMatBill", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveMatBill(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		Map<String, Object> retMap = null;
		try{
			retMap = matBillService.saveMatBill(mapVo);
		} catch (Exception e) {
			retMap = new HashMap<String, Object>();
			retMap.put("state", "false");
			retMap.put("error", "操作失败！");
		}
		
		return retMap;
	}

	/**
	 * @Description 
	 * 加载明细
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/queryMatBillDetailById", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMatBillDetailById(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String retJson = matBillService.queryMatBillDetailById(mapVo);

		return JSONObject.parseObject(retJson);
	}
	
	/**
	 * @Description 
	 * 入库单查询页面跳转  
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/matInByBillPage", method = RequestMethod.GET)
	public String matInByBillPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mode.addAttribute("p04005", MyConfig.getSysPara("04005"));
		mode.addAttribute("p04006", MyConfig.getSysPara("04006"));
		
		return "hrp/mat/payment/bill/matInByBill";
	}

	/**
	 * @Description 
	 * 入库单查询
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "queryMatInByBill", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMatInByBill(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String retJson = matBillService.queryMatInByBill(getPage(mapVo));

		return JSONObject.parseObject(retJson);
	}

	/**
	 * @Description 
	 * 入库单明细查询
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "queryMatInDetailByBill", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMatInDetailByBill(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String retJson = matBillService.queryMatInDetailByBill(mapVo);
		
		return JSONObject.parseObject(retJson);
	}
	


	/**
	 * @Description 
	 * 根据所选入库单生成采购发票明细
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "queryMatBillDetailByIn", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMatBillDetailByIn(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String retJson = matBillService.queryMatBillDetailByIn(getPage(mapVo));
		
		return JSONObject.parseObject(retJson);
	}
	
	/**
	 * @Description 
	 * 审核
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/auditMatBill", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> auditMatBill(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		Map<String, Object> retMap = null;
		try{
			mapVo.put("state", 2);
			mapVo.put("exists_state", 2);
			mapVo.put("checker", SessionManager.getUserId());
			mapVo.put("chk_date", new Date());
			retMap = matBillService.updateMatBillState(mapVo);
		} catch (Exception e) {
			retMap = new HashMap<String, Object>();
			retMap.put("state", "false");
			retMap.put("error", "操作失败！");
		}
		
		return retMap;
	}
	
	/**
	 * @Description 
	 * 消审
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/unAuditMatBill", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> unAuditMatBill(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		Map<String, Object> retMap = null;
		try{
			mapVo.put("state", 1);
			mapVo.put("exists_state", 1);
			mapVo.put("checker", null);
			mapVo.put("chk_date", null);
			retMap = matBillService.updateMatBillState(mapVo);
		} catch (Exception e) {
			retMap = new HashMap<String, Object>();
			retMap.put("state", "false");
			retMap.put("error", "操作失败！");
		}
		
		return retMap;
	}
	
	/**
	 * @Description 
	 * 删除
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/deleteMatBill", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteMatBill(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		Map<String, Object> retMap = null;
		try{
			retMap = matBillService.deleteMatBill(mapVo);
		} catch (Exception e) {
			retMap = new HashMap<String, Object>();
			retMap.put("state", "false");
			retMap.put("error", "操作失败！");
		}
		
		return retMap;
	}
	
	/**
	 * @Description 
	 * 批量修改备注信息
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/updateMatBillNote", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateMatBillNote(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		Map<String, Object> retMap = null;
		try{
			retMap = matBillService.updateMatBillNote(mapVo);
		} catch (Exception e) {
			retMap = new HashMap<String, Object>();
			retMap.put("state", "false");
			retMap.put("error", "操作失败！");
		}
		
		return retMap;
	}
}

