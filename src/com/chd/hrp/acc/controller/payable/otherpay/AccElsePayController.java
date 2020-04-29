package com.chd.hrp.acc.controller.payable.otherpay;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
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
import com.chd.base.util.DateUtil;
import com.chd.hrp.acc.entity.AccEmpAccount;
import com.chd.hrp.acc.entity.payable.AccElsePay;
import com.chd.hrp.acc.entity.payable.BudgChargeApply;
import com.chd.hrp.acc.service.payable.otherpay.AccElsePayService;
import com.chd.hrp.budg.serviceImpl.common.BudgNoRulesServiceImpl;
@Controller
public class AccElsePayController extends BaseController{
	private static Logger logger = Logger.getLogger(AccElsePayController.class);	
	
	@Resource(name = "accElsePayService")
	private final AccElsePayService accElsePayService = null;
	
	@Resource(name="budgNoRulesService")
    private final BudgNoRulesServiceImpl  budgNoRulesService=null;
	
	/**
	 * 主页面跳转
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/acc/payable/otherpay/elsepay/accElsePayMainPage", method = RequestMethod.GET)
	public String applyMainPage(Model mode) throws Exception {
		
		return "hrp/acc/payable/otherpay/elsepay/accElsePayMain";
	}
	
	
	/**
	 * 添加页面跳转
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/acc/payable/otherpay/elsepay/accElsePayAddPage", method = RequestMethod.GET)
	public String accElsePayAddPage(Model mode) throws Exception {
		
		return "hrp/acc/payable/otherpay/elsepay/accElsePayAdd";
	}
	
	
	/**
	 * @Description 
	 * 打印模板页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/acc/payable/otherpay/elsepay/printSetPage", method = RequestMethod.GET)
	public String printSetPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		if(mapVo!=null && mapVo.size()>0){
			for(Map.Entry<String, Object> entry:mapVo.entrySet()){ 
				mode.addAttribute(entry.getKey(),entry.getValue());
			}
		}

		return "redirect:../../../accvouch/superPrint/printSetPage.do?isCheck=false";
	}
	
	/**
	 * @Description 更新跳转页面 
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/acc/payable/otherpay/elsepay/accElesePayUpdatePage", method = RequestMethod.GET)
	public String accElesePayUpdatePage(@RequestParam Map<String, Object> mapVo,
			Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}

		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}

		AccElsePay accElsePay = accElsePayService.queryByCode(mapVo);

		mode.addAttribute("group_id",	   accElsePay.getGroup_id());
		mode.addAttribute("hos_id",	   accElsePay.getHos_id());
		mode.addAttribute("copy_code",	   accElsePay.getCopy_code());
		mode.addAttribute("pay_code",	   accElsePay.getPay_code());
		mode.addAttribute("pay_money",	   accElsePay.getPay_money());
		mode.addAttribute("remark",	   accElsePay.getRemark());
		mode.addAttribute("price",	   accElsePay.getPrice());
		mode.addAttribute("amount",	   accElsePay.getAmount());
		mode.addAttribute("maker_name",	   accElsePay.getMaker_name());
		mode.addAttribute("maker", accElsePay.getMaker());
		mode.addAttribute("make_date",	   DateUtil.dateToString(accElsePay.getMake_date(),"yyyy-MM-dd"));
		mode.addAttribute("payee", accElsePay.getPayee());
		mode.addAttribute("emp_no", accElsePay.getEmp_no());
		mode.addAttribute("emp_code", accElsePay.getEmp_code());
		mode.addAttribute("payee_name", accElsePay.getPayee_name());
		mode.addAttribute("card_no", accElsePay.getCard_no());
		mode.addAttribute("bank", accElsePay.getBank());
		mode.addAttribute("phone", accElsePay.getPhone());
		mode.addAttribute("dept_id",	   accElsePay.getDept_id());
		mode.addAttribute("dept_no",   accElsePay.getDept_no());
		mode.addAttribute("dept_code",	   accElsePay.getDept_code());
		mode.addAttribute("dept_name",   accElsePay.getDept_name());
		
		return "hrp/acc/payable/otherpay/elsepay/accElsePayUpdate";
	}

	/**
	 * @Description 查询数据
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/acc/payable/otherpay/elsepay/queryAccElsePay", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAccElsePay( @RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}

		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		String str = accElsePayService.query(getPage(mapVo));

		return JSONObject.parseObject(str);

	}
	
	/**
	 * @Description 添加数据
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/acc/payable/otherpay/elsepay/addAccElsePay", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addAccElsePay( @RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}

		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		//构建 取 审批单号 参数Map
		Map<String,Object> noMap = new HashMap<String,Object>();	
		
		noMap.put("table_code", "ACC_ELSE_PAY");
		noMap.put("table_name", "其他费用付款凭证");
		
		Map<String,Object> map = budgNoRulesService.getBudgNextNo(noMap) ;
		
		if("true".equals(map.get("state"))){
			//自动生成审批单号
			mapVo.put("pay_code",map.get("noCode"));
			
		}else{
			
			return map ;
		}
		
		mapVo.put("maker", SessionManager.getUserId())   ;
		
		mapVo.put("make_date",DateUtil.stringToDate(DateUtil.dateToString(new Date(), "yyyy-MM-dd"),"yyyy-MM-dd"));
       
		
		String str = accElsePayService.add(mapVo);

		return JSONObject.parseObject(str);

	}
	
	
	/**
	 * @Description 更新数据
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/acc/payable/otherpay/elsepay/updateAccELsePay", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateAccELsePay( @RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}

		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		
		String str = accElsePayService.update(mapVo);

		return JSONObject.parseObject(str);

	}
	
	/**
	 * @Description 删除数据
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/acc/payable/otherpay/elsepay/deleteAccELsePay", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAccELsePay(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		
			for ( String id: paramVo.split(",")) {
				
				Map<String, Object> mapVo=new HashMap<String, Object>();
				
				String[] ids=id.split("@");
				
				//表的主键
				mapVo.put("group_id", ids[0])   ;
				mapVo.put("hos_id", ids[1])   ;
				mapVo.put("copy_code", ids[2])   ;
				mapVo.put("pay_code", ids[3]);
				
				listVo.add(mapVo);
	      
	    }
	    
		String str = accElsePayService.deleteBatch(listVo);

		return JSONObject.parseObject(str);

	}
	
	/**
	 * @Description 查询职工账号
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/acc/payable/otherpay/elsepay/queryGetEmpCardNo", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryGetEmpCardNo(@RequestParam Map<String, Object> mapVo, Model mode,HttpServletResponse rsp) throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}

		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		AccEmpAccount accEmpAccount = accElsePayService.queryAccEmpAccountByEmp(mapVo);
		
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("accEmpAccount", accEmpAccount);
		rsp.getWriter().print(jsonObj.toJSONString());
		return null;
	}
}
