/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/
package com.chd.hrp.acc.controller;
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
import com.chd.base.SessionManager;
import com.chd.base.util.StringTool;
import com.chd.hrp.acc.entity.AccSupAttr;
import com.chd.hrp.acc.serviceImpl.AccSupAttrServiceImpl;

/**
* @Title. @Description.
* 供应商字典属性表
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


@Controller
public class AccSupAttrController extends BaseController{
	private static Logger logger = Logger.getLogger(AccSupAttrController.class);
	
	
	@Resource(name = "accSupAttrService")
	private final AccSupAttrServiceImpl accSupAttrService = null;
   
    
	/**
	*供应商字典属性表<BR>
	*维护页面跳转
	*/
	
	@RequestMapping(value = "/hrp/acc/accsupattr/accSupAttrMainPage", method = RequestMethod.GET)
	public String accSupAttrMainPage(Model mode) throws Exception {

		return "hrp/acc/accsupattr/accSupAttrMain";

	}
	/**
	*供应商字典属性表<BR>
	*维护页面跳转
	*/
	// 添加页面
	@RequestMapping(value = "/hrp/acc/accsupattr/accSupAttrAddPage", method = RequestMethod.GET)
	public String accSupAttrAddPage(Model mode) throws Exception {

		return "hrp/acc/accsupattr/accSupAttrAdd";

	}
	/**
	*供应商字典属性表<BR>
	*保存
	*/
	@RequestMapping(value = "/hrp/acc/accsupattr/addAccSupAttr", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> addAccSupAttr(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		String accSupAttrJson = accSupAttrService.addAccSupAttr(mapVo);

		return JSONObject.parseObject(accSupAttrJson);
		
	}
	/**
	*供应商字典属性表<BR>
	*查询
	*/
	@RequestMapping(value = "/hrp/acc/accsupattr/queryAccSupAttr", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> queryAccSupAttr(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
	 
		String accSupAttr = accSupAttrService.queryAccSupAttr(getPage(mapVo));

		return JSONObject.parseObject(accSupAttr);
		
	}
	/**
	*供应商字典属性表<BR>
	*删除
	*/
	@RequestMapping(value = "/hrp/acc/accsupattr/deleteAccSupAttr", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAccSupAttr(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		for ( String id: paramVo.split(",")) {
			Map<String, Object> mapVo=new HashMap<String, Object>();
			
            mapVo.put("temp", id);//实际实体类变量
            listVo.add(mapVo);
        }
		String accSupAttrJson = accSupAttrService.deleteBatchAccSupAttr(listVo);
	   return JSONObject.parseObject(accSupAttrJson);
			
	}
	
	/**
	*供应商字典属性表<BR>
	*修改页面跳转
	*/
	@RequestMapping(value = "/hrp/acc/accsupattr/accSupAttrUpdatePage", method = RequestMethod.GET)
	
	public String accSupAttrUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
        AccSupAttr accSupAttr = new AccSupAttr();
		accSupAttr = accSupAttrService.queryAccSupAttrByCode(mapVo);
		mode.addAttribute("sup_id", accSupAttr.getSup_id());
		mode.addAttribute("group_id", accSupAttr.getGroup_id());
		mode.addAttribute("hos_id", accSupAttr.getHos_id());
		mode.addAttribute("bank_name", accSupAttr.getBank_name());
		mode.addAttribute("bank_number", accSupAttr.getBank_number());
		mode.addAttribute("legal", accSupAttr.getLegal());
		mode.addAttribute("regis_no", accSupAttr.getRegis_no());
		mode.addAttribute("phone", accSupAttr.getPhone());
		mode.addAttribute("mobile", accSupAttr.getMobile());
		mode.addAttribute("contact", accSupAttr.getContact());
		mode.addAttribute("fax", accSupAttr.getFax());
		mode.addAttribute("email", accSupAttr.getEmail());
		mode.addAttribute("region", accSupAttr.getRegion());
		mode.addAttribute("zip_code", accSupAttr.getZip_code());
		mode.addAttribute("address", accSupAttr.getAddress());
		mode.addAttribute("note", accSupAttr.getNote());
		return "hrp/acc/accsupattr/accSupAttrUpdate";
	}
	/**
	*供应商字典属性表<BR>
	*修改保存
	*/	
	
	@RequestMapping(value = "/hrp/acc/accsupattr/updateAccSupAttr", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateAccSupAttr(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
	   
	   
	   
	   
	   
	   
	   
	   
	   
	   
	   
	   
	   
	   
	   
	   
		String accSupAttrJson = accSupAttrService.updateAccSupAttr(mapVo);
		
		return JSONObject.parseObject(accSupAttrJson);
	}
	/**
	*供应商字典属性表<BR>
	*导入
	*/
	
	@RequestMapping(value = "/hrp/acc/accsupattr/importAccSupAttr", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> importAccSupAttr(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String accSupAttrJson = accSupAttrService.importAccSupAttr(mapVo);
		
		return JSONObject.parseObject(accSupAttrJson);
	}

}

