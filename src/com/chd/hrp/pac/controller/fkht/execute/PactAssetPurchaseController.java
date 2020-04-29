package com.chd.hrp.pac.controller.fkht.execute;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.chd.base.BaseController;
import com.chd.base.SessionManager;
import com.chd.hrp.pac.service.fkht.execute.PactAssetPurchaseService;

@Controller
@RequestMapping(value = "/hrp/pac/fkht/pactExecAnaly")
public class PactAssetPurchaseController extends BaseController {

	@Resource(name = "pactAssetPurchaseService")
	private PactAssetPurchaseService pactAssetPurchaseService;
	
	/**
	 * 资产采购执行查询页面
	 * @return
	 */
	@RequestMapping(value = "/pactAssetPurchaseDetPage")
	public String pactAssetPurchaseDetPage() {
		return "hrp/pac/fkht/execute/pactAssetPurchaseDet";
	}
	/**
	 * 资产采购执行查询 入库链接页面
	 * @return
	 */
	@RequestMapping(value = "/pactAssetPurchaseDetInPage")
	public String pactAssetPurchaseDetInPage(@RequestParam Map<String, Object> mapVo, Model mode) {
		mode.addAttribute("pact_code", mapVo.get("pact_code"));
		mode.addAttribute("sup_id", mapVo.get("sup_id"));
		return "hrp/pac/fkht/execute/pactAssetPurchaseDetIn";
	}
	/**
	 * 资产采购执行查询 退货链接页面
	 * @return
	 */
	@RequestMapping(value = "/pactAssetPurchaseDetBackPage")
	public String pactAssetPurchaseDetBackPage(@RequestParam Map<String, Object> mapVo, Model mode) {
		mode.addAttribute("pact_code", mapVo.get("pact_code"));
		mode.addAttribute("sup_id", mapVo.get("sup_id"));
		return "hrp/pac/fkht/execute/pactAssetPurchaseDetBack";
	}
	/**
	 * 资产采购执行汇总查询
	 * @return
	 */
	@RequestMapping(value = "/pactAssetPurchaseMainPage")
	public String toPactAssetPurchaseMainPage() {
		return "hrp/pac/fkht/execute/pactAssetPurchaseMain";
	}
	/**
	 * 资产采购执行汇总查询 安装链接页面
	 * @return
	 */
	@RequestMapping(value = "/pactAssetPurchaseMainInsPage")
	public String pactAssetPurchaseMainInsPage(@RequestParam Map<String, Object> mapVo, Model mode) {
		mode.addAttribute("pact_code", mapVo.get("pact_code"));
		mode.addAttribute("sup_id", mapVo.get("sup_id"));
		return "hrp/pac/fkht/execute/mainRef/pactAssetPurchaseMainIns";
	}
	/**
	 * 资产采购执行汇总查询 验收链接页面
	 * @return
	 */
	@RequestMapping(value = "/pactAssetPurchaseMainAcceptPage")
	public String pactAssetPurchaseMainAcceptPage(@RequestParam Map<String, Object> mapVo, Model mode) {
		mode.addAttribute("pact_code", mapVo.get("pact_code"));
		mode.addAttribute("sup_id", mapVo.get("sup_id"));
		return "hrp/pac/fkht/execute/mainRef/pactAssetPurchaseMainAccept";
	}
	/**
	 * 资产采购执行汇总查询 入库链接页面
	 * @return
	 */
	@RequestMapping(value = "/pactAssetPurchaseMainInPage")
	public String pactAssetPurchaseMainInPage(@RequestParam Map<String, Object> mapVo, Model mode) {
		mode.addAttribute("pact_code", mapVo.get("pact_code"));
		mode.addAttribute("sup_id", mapVo.get("sup_id"));
		return "hrp/pac/fkht/execute/mainRef/pactAssetPurchaseMainIn";
	}
	/**
	 * 资产采购执行汇总查询 退货链接页面
	 * @return
	 */
	@RequestMapping(value = "/pactAssetPurchaseMainBackPage")
	public String pactAssetPurchaseMainBackPage(@RequestParam Map<String, Object> mapVo, Model mode) {
		mode.addAttribute("pact_code", mapVo.get("pact_code"));
		mode.addAttribute("sup_id", mapVo.get("sup_id"));
		return "hrp/pac/fkht/execute/mainRef/pactAssetPurchaseMainBack";
	}
	
	@RequestMapping(value = "/pactAssetPurchaseDetailMainPage")
	public String toPactAssetPurchaseDetailMainPage(@RequestParam Map<String, Object> mapVo, Model mode) {
		if(mapVo.get("pact_code") != null){
			mode.addAttribute("pact_code", mapVo.get("pact_code"));
		}else{
			mode.addAttribute("pact_code", null);
		}
		return "hrp/pac/fkht/execute/pactAssetPurchaseDetailMain";
	}
	
	
	/**
	 * 资产采购执行查询
	 * @param mapVo
	 * @param mode
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/queryPactAssPurchaseFKHTDet")
	public Map<String, Object> queryPactAssPurchaseFKHTDet(@RequestParam Map<String, Object> mapVo, Model mode) {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());

		String query = pactAssetPurchaseService.queryPactAssPurchaseFKHTDet(getPage(mapVo));
		return JSONObject.parseObject(query);
	}
	
	@ResponseBody
	@RequestMapping(value = "/queryPactAssetPurchaseFKHT")
	public Map<String, Object> queryPactAssetPurchaseFKHT(@RequestParam Map<String, Object> mapVo, Model mode) {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());

		String query = pactAssetPurchaseService.queryPactAssetPurchaseFKHT(mapVo);
		return JSONObject.parseObject(query);
	}
	@ResponseBody
	@RequestMapping(value = "/queryPactAssetPurchaseDetailFKHT")
	public Map<String, Object> queryPactAssetPurchaseDetailFKHT(@RequestParam Map<String, Object> mapVo, Model mode) {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		String query = pactAssetPurchaseService.queryPactAssetPurchaseDetailFKHT(mapVo);
		return JSONObject.parseObject(query);
	}
	
	/**
	 * 资产采购执行查询  入库链接查询
	 * @param mapVo
	 * @param mode
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/queryPactAssPurchaseFKHTDetIn")
	public Map<String, Object> queryPactAssPurchaseFKHTDetIn(@RequestParam Map<String, Object> mapVo, Model mode) {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		mapVo.put("pact_code", mapVo.get("pact_code"));
		mapVo.put("sup_id", mapVo.get("sup_id"));

		String query = pactAssetPurchaseService.queryPactAssPurchaseFKHTDetIn(getPage(mapVo));
		return JSONObject.parseObject(query);
	}
	
	/**
	 * 资产采购执行查询  退货链接查询
	 * @param mapVo
	 * @param mode
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/queryPactAssPurchaseFKHTDetBack")
	public Map<String, Object> queryPactAssPurchaseFKHTDetBack(@RequestParam Map<String, Object> mapVo, Model mode) {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		mapVo.put("pact_code", mapVo.get("pact_code"));
		mapVo.put("sup_id", mapVo.get("sup_id"));

		String query = pactAssetPurchaseService.queryPactAssPurchaseFKHTDetBack(getPage(mapVo));
		return JSONObject.parseObject(query);
	}
	
	/**
	 * 资产采购执行汇总查询  安装链接查询
	 * @param mapVo
	 * @param mode
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/queryPactAssPurchaseFKHTMainIns")
	public Map<String, Object> queryPactAssPurchaseFKHTMainIns(@RequestParam Map<String, Object> mapVo, Model mode) {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		mapVo.put("pact_code", mapVo.get("pact_code"));
		mapVo.put("sup_id", mapVo.get("sup_id"));

		String query = pactAssetPurchaseService.queryPactAssPurchaseFKHTMainIns(getPage(mapVo));
		return JSONObject.parseObject(query);
	}
	
	/**
	 * 资产采购执行汇总查询  验收链接查询
	 * @param mapVo
	 * @param mode
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/queryPactAssPurchaseFKHTMainAccept")
	public Map<String, Object> queryPactAssPurchaseFKHTMainAccept(@RequestParam Map<String, Object> mapVo, Model mode) {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		mapVo.put("pact_code", mapVo.get("pact_code"));
		mapVo.put("sup_id", mapVo.get("sup_id"));

		String query = pactAssetPurchaseService.queryPactAssPurchaseFKHTMainAccept(getPage(mapVo));
		return JSONObject.parseObject(query);
	}
	
	/**
	 * 资产采购执行汇总查询 入库链接查询
	 * @param mapVo
	 * @param mode
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/queryPactAssPurchaseFKHTMainIn")
	public Map<String, Object> queryPactAssPurchaseFKHTMainIn(@RequestParam Map<String, Object> mapVo, Model mode) {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		mapVo.put("pact_code", mapVo.get("pact_code"));
		mapVo.put("sup_id", mapVo.get("sup_id"));

		String query = pactAssetPurchaseService.queryPactAssPurchaseFKHTMainIn(getPage(mapVo));
		return JSONObject.parseObject(query);
	}
	
	/**
	 * 资产采购执行汇总查询  退货链接查询
	 * @param mapVo
	 * @param mode
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/queryPactAssPurchaseFKHTMainBack")
	public Map<String, Object> queryPactAssPurchaseFKHTMainBack(@RequestParam Map<String, Object> mapVo, Model mode) {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		mapVo.put("pact_code", mapVo.get("pact_code"));
		mapVo.put("sup_id", mapVo.get("sup_id"));

		String query = pactAssetPurchaseService.queryPactAssPurchaseFKHTMainBack(getPage(mapVo));
		return JSONObject.parseObject(query);
	}
	  
	
	
	
	
	/**
	 * 资产采购应付款  付款记录 页面
	 */
	@RequestMapping(value = "/pactAssetPurchasePayPayPage")
	public String toPactAssetPurchasePayPayPage(@RequestParam Map<String, Object> mapVo, Model mode) {
		if(mapVo.get("pact_code") != null){
			mode.addAttribute("pact_code", mapVo.get("pact_code"));
		}else{
			mode.addAttribute("pact_code", null);
		}
		return "hrp/pac/fkht/execute/pactAssetPurchasePayPay";
	}
	/**
	 * 资产采购应付款  发票记录 页面
	 */
	@RequestMapping(value = "/pactAssetPurchasePayBillPage")
	public String toPactAssetPurchasePayBillPage(@RequestParam Map<String, Object> mapVo, Model mode) {
		if(mapVo.get("pact_code") != null){
			mode.addAttribute("pact_code", mapVo.get("pact_code"));
		}else{
			mode.addAttribute("pact_code", null);
		}
		return "hrp/pac/fkht/execute/pactAssetPurchasePayBill";
	}
	/**
	 * 资产采购应付款  页面
	 */
	@RequestMapping(value = "/pactAssetPurchasePayPage")
	public String toPactAssetPurchasePayPage(@RequestParam Map<String, Object> mapVo, Model mode) {
		if(mapVo.get("pact_code") != null){
			mode.addAttribute("pact_code", mapVo.get("pact_code"));
		}else{
			mode.addAttribute("pact_code", null);
		}
		return "hrp/pac/fkht/execute/pactAssetPurchasePay";
	}
	/**
	 * 资产采购应付款明细  页面
	 */
	@RequestMapping(value = "/pactAssetPurchasePayMDPage")
	public String toPactAssetPurchasePayMDPage(@RequestParam Map<String, Object> mapVo, Model mode) {
		if(mapVo.get("pact_code") != null){
			mode.addAttribute("pact_code", mapVo.get("pact_code"));
		}else{
			mode.addAttribute("pact_code", null);
		}
		return "hrp/pac/fkht/execute/pactAssetPurchasePayMD";
	}
	/**
	 * 付款记录  查询
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/queryPactAssetPurchasePayPay")
	public Map<String, Object> queryPactAssetPurchasePayPay(@RequestParam Map<String, Object> mapVo, Model mode) {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		String query = pactAssetPurchaseService.queryPactAssetPurchasePayPay(mapVo);
		return JSONObject.parseObject(query);
	}
	/**
	 * 发票记录  查询
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/queryPactAssetPurchasePayBill")
	public Map<String, Object> queryPactAssetPurchasePayBill(@RequestParam Map<String, Object> mapVo, Model mode) {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		String query = pactAssetPurchaseService.queryPactAssetPurchasePayBill(mapVo);
		return JSONObject.parseObject(query);
	}
	/**
	 * 资产采购应付款  查询
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/queryPactAssetPurchasePay")
	public Map<String, Object> queryPactAssetPurchasePay(@RequestParam Map<String, Object> mapVo, Model mode) {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());

		String query = pactAssetPurchaseService.queryPactAssetPurchasePay(mapVo);
		/* * String query = "{ \"Rows\": ["  * +"{ \"pact_code\":\"001\", \"pact_name\":\"YYGLY\", \"trade_type_name\":\"贸易类别a\", \"sign_date\":null,\"dept_name\":\"内科\", \"pact_money\":4444,  \"kpje\":2233,\"wkpje\": 1122, \"fkje\":100, \"ykpwfk\":200}, "* +"{ \"pact_code\":\"002\", \"pact_name\":\"YYGLY\", \"trade_type_name\":\"贸易类别b\", \"sign_date\":null,\"dept_name\":\"内科\", \"pact_money\":4444,  \"kpje\":2233,\"wkpje\": 1122, \"fkje\":100, \"ykpwfk\":200},"* +"{ \"pact_code\":\"003\", \"pact_name\":\"YYGLY\", \"trade_type_name\":\"贸易类别c\", \"sign_date\":null,\"dept_name\":\"内科\", \"pact_money\":4444,  \"kpje\":2233,\"wkpje\": 1122, \"fkje\":100, \"ykpwfk\":200},"* +"{ \"pact_code\":\"004\", \"pact_name\":\"YYGLY\", \"trade_type_name\":\"贸易类别d\", \"sign_date\":null,\"dept_name\":\"内科\", \"pact_money\":4444,  \"kpje\":2233,\"wkpje\": 1122, \"fkje\":100, \"ykpwfk\":200}"* +"], \"Total\": 4}" ;*/
		return JSONObject.parseObject(query);
	}
	/**
	 * 资产采购应付款明细  查询
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/queryPactAssetPurchasePayMD")
	public Map<String, Object> queryPactAssetPurchasePayMD(@RequestParam Map<String, Object> mapVo, Model mode) {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		String query = pactAssetPurchaseService.queryPactAssetPurchasePayMD(mapVo);
		return JSONObject.parseObject(query);
	}
}
