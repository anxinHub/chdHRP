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
import com.chd.hrp.mat.service.payment.MatPayService;
/** 
 * 
 * @Description:
 * 付款单
 * @Table:
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 

@Controller
@RequestMapping(value="/hrp/mat/payment/pay")
public class MatPayController extends BaseController{
	
	private static Logger logger = Logger.getLogger(MatPayController.class);
	
	//引入Service服务
	@Resource(name = "matPayService")
	private final MatPayService matPayService = null;
	
	/**
	 * @Description 
	 * 主页面跳转  
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/matPayListPage", method = RequestMethod.GET)
	public String matPayListPage(Model mode) throws Exception {
		mode.addAttribute("p04005", MyConfig.getSysPara("04005"));
		mode.addAttribute("p04027", MyConfig.getSysPara("04027"));
		
		return "hrp/mat/payment/pay/matPayList";
	}

	/**
	 * @Description 
	 * 主页面查询
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "queryMatPayList", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMatPayList(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String retJson = matPayService.queryMatPayList(getPage(mapVo));

		return JSONObject.parseObject(retJson);
	}

	/**
	 * @Description 
	 * 添加页面跳转  
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/matPayAddPage", method = RequestMethod.GET)
	public String matPayAddPage(Model mode) throws Exception {
		mode.addAttribute("p04005", MyConfig.getSysPara("04005"));
		mode.addAttribute("p04006", MyConfig.getSysPara("04006"));
		mode.addAttribute("p04027", MyConfig.getSysPara("04027"));
		
		return "hrp/mat/payment/pay/matPayAdd";
	}

	/**
	 * @Description 
	 * 修改页面跳转  
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/matPayUpdatePage", method = RequestMethod.GET)
	public String matPayUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mode.addAttribute("p04005", MyConfig.getSysPara("04005"));
		mode.addAttribute("p04006", MyConfig.getSysPara("04006"));
		mode.addAttribute("p04027", MyConfig.getSysPara("04027"));
		//添加主表数据
		Map<String, Object> matPayMain = matPayService.queryMatPayMainById(mapVo);
		//转换发票日期
		if(matPayMain.get("pay_date") != null && !"".equals(matPayMain.get("pay_date").toString())){
			matPayMain.put("pay_date", DateUtil.dateToString((Date) matPayMain.get("pay_date"), "yyyy-MM-dd"));
		}
		mode.addAttribute("matPayMain", matPayMain);
		
		return "hrp/mat/payment/pay/matPayUpdate";
	}
	
	/**
	 * @Description 
	 * 保存单据
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/saveMatPay", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveMatPay(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		Map<String, Object> retMap = null;
		try{
			retMap = matPayService.saveMatPay(mapVo);
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
	@RequestMapping(value = "/queryMatPayDetailById", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMatPayDetailById(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String retJson = matPayService.queryMatPayDetailById(mapVo);

		return JSONObject.parseObject(retJson);
	}
	
	/**
	 * @Description 
	 * 入库单查询页面跳转  
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/matBillByPayPage", method = RequestMethod.GET)
	public String matBillByPayPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mode.addAttribute("p04005", MyConfig.getSysPara("04005"));
		mode.addAttribute("p04006", MyConfig.getSysPara("04006"));
		
		return "hrp/mat/payment/pay/matBillByPay";
	}

	/**
	 * @Description 
	 * 发票查询
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "queryMatBillByPay", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMatBillByPay(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String retJson = matPayService.queryMatBillByPay(getPage(mapVo));

		return JSONObject.parseObject(retJson);
	}

	/**
	 * @Description 
	 * 发票明细查询
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "queryMatBillDetailByPay", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMatBillDetailByPay(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String retJson = matPayService.queryMatBillDetailByPay(mapVo);
		
		return JSONObject.parseObject(retJson);
	}
	


	/**
	 * @Description 
	 * 根据所选入库单生成付款单明细
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "queryMatPayDetailByBill", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMatPayDetailByBill(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String retJson = matPayService.queryMatPayDetailByBill(getPage(mapVo));
		
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
	@RequestMapping(value = "/auditMatPay", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> auditMatPay(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		Map<String, Object> retMap = null;
		try{
			mapVo.put("state", 2);
			mapVo.put("exists_state", 2);
			mapVo.put("checker", SessionManager.getUserId());
			mapVo.put("chk_date", new Date());
			retMap = matPayService.updateMatPayState(mapVo);
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
	@RequestMapping(value = "/unAuditMatPay", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> unAuditMatPay(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		Map<String, Object> retMap = null;
		try{
			mapVo.put("state", 1);
			mapVo.put("exists_state", 1);
			mapVo.put("checker", null);
			mapVo.put("chk_date", null);
			retMap = matPayService.updateMatPayState(mapVo);
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
	@RequestMapping(value = "/deleteMatPay", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteMatPay(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		Map<String, Object> retMap = null;
		try{
			retMap = matPayService.deleteMatPay(mapVo);
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
	@RequestMapping(value = "/updateMatPayNote", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateMatPayNote(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		Map<String, Object> retMap = null;
		try{
			retMap = matPayService.updateMatPayNote(mapVo);
		} catch (Exception e) {
			retMap = new HashMap<String, Object>();
			retMap.put("state", "false");
			retMap.put("error", "操作失败！");
		}
		
		return retMap;
	}
}

