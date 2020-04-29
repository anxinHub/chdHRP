/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/
package com.chd.hrp.acc.controller;
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
import com.chd.base.SessionManager;
import com.chd.hrp.acc.entity.AccEmpAttr;
import com.chd.hrp.acc.serviceImpl.AccEmpAttrServiceImpl;

/**
* @Title. @Description.
*职工字典属性表
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


@Controller
public class AccEmpAttrController extends BaseController{
	private static Logger logger = Logger.getLogger(AccEmpAttrController.class);
	
	
	@Resource(name = "accEmpAttrService")
	private final AccEmpAttrServiceImpl accEmpAttrService = null;
   
    
	/**
	*职工字典属性表<BR>
	*维护页面跳转
	*/
	
	@RequestMapping(value = "/hrp/acc/accempattr/accEmpAttrMainPage", method = RequestMethod.GET)
	public String accEmpAttrMainPage(Model mode) throws Exception {

		return "hrp/acc/accempattr/accEmpAttrMain";

	}
	/**
	*职工字典属性表<BR>
	*维护页面跳转
	*/
	// 添加页面
	@RequestMapping(value = "/hrp/acc/accempattr/accEmpAttrAddPage", method = RequestMethod.GET)
	public String accEmpAttrAddPage(Model mode) throws Exception {

		return "hrp/acc/accempattr/accEmpAttrAdd";

	}
	/**
	*职工字典属性表<BR>
	*保存
	*/
	@RequestMapping(value = "/hrp/acc/accempattr/addAccEmpAttr", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> addAccEmpAttr(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
			if(mapVo.get("group_id") == null){
			
			mapVo.put("group_id", SessionManager.getGroupId());
			
			}
		
			if(mapVo.get("hos_id") == null){
				
			mapVo.put("hos_id", SessionManager.getHosId());
			
			}
		
		String accEmpAttrJson = accEmpAttrService.addAccEmpAttr(mapVo);

		return JSONObject.parseObject(accEmpAttrJson);
		
	}
	/**
	*职工字典属性表<BR>
	*查询
	*/
	@RequestMapping(value = "/hrp/acc/accempattr/queryAccEmpAttr", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> queryAccEmpAttr(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
	 
			if(mapVo.get("group_id") == null){
			
			mapVo.put("group_id", SessionManager.getGroupId());
			
			}
		
			if(mapVo.get("hos_id") == null){
				
			mapVo.put("hos_id", SessionManager.getHosId());
			
			}
			
		String accEmpAttr = accEmpAttrService.queryAccEmpAttr(getPage(mapVo));

		return JSONObject.parseObject(accEmpAttr);
		
	}
	/**
	*职工字典属性表<BR>
	*删除
	*/
	@RequestMapping(value = "/hrp/acc/accempattr/deleteAccEmpAttr", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAccEmpAttr(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		for ( String id: paramVo.split(",")) {
			Map<String, Object> mapVo=new HashMap<String, Object>();
			
            mapVo.put("temp", id);//实际实体类变量
            listVo.add(mapVo);
        }
		String accEmpAttrJson = accEmpAttrService.deleteBatchAccEmpAttr(listVo);
	   return JSONObject.parseObject(accEmpAttrJson);
			
	}
	
	/**
	*职工字典属性表<BR>
	*修改页面跳转
	*/
	@RequestMapping(value = "/hrp/acc/accempattr/accEmpAttrUpdatePage", method = RequestMethod.GET)
	
	public String accEmpAttrUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
       
		AccEmpAttr accEmpAttr = new AccEmpAttr();
		
		accEmpAttr = accEmpAttrService.queryAccEmpAttrByCode(mapVo);
		
		return "hrp/acc/accempattr/accEmpAttrUpdate";
	}
	/**
	*职工字典属性表<BR>
	*修改保存
	*/	
	
	@RequestMapping(value = "/hrp/acc/accempattr/updateAccEmpAttr", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateAccEmpAttr(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
	   
	   
		String accEmpAttrJson = accEmpAttrService.updateAccEmpAttr(mapVo);
		
		return JSONObject.parseObject(accEmpAttrJson);
	}
	/**
	*职工字典属性表<BR>
	*导入
	*/
	
	@RequestMapping(value = "/hrp/acc/accempattr/importAccEmpAttr", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> importAccEmpAttr(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String accEmpAttrJson = accEmpAttrService.importAccEmpAttr(mapVo);
		
		return JSONObject.parseObject(accEmpAttrJson);
	}
	
	/**
	*
	*根据职工id查询身份证号
	*/
	
	@RequestMapping(value = "/hrp/acc/accempattr/queryAccEmpNumber", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAccEmpNumber(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		
		mapVo.put("hos_id", SessionManager.getHosId());
		
		 String accEmpAttr  = accEmpAttrService.queryAccEmpNumber(mapVo);
		
		return JSONObject.parseObject(accEmpAttr);
	}
	/**
	 * 根据员工id查部门
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/acc/accempattr/queryDeptByEmpid", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryDeptByEmpid(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		
		mapVo.put("hos_id", SessionManager.getHosId());
		
		String accEmpAttr  = accEmpAttrService.queryDeptByEmpid(mapVo);
		
		return JSONObject.parseObject(accEmpAttr);
	}
	
	@RequestMapping(value = "/hrp/acc/accempattr/initAccEmpAttr", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> initAccEmpAttr(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
			if(mapVo.get("group_id") == null){
			
			mapVo.put("group_id", SessionManager.getGroupId());
			
			}
		
			if(mapVo.get("hos_id") == null){
				
			mapVo.put("hos_id", SessionManager.getHosId());
			
			}
		
		String accEmpAttrJson = accEmpAttrService.initAccEmpAttr(mapVo);

		return JSONObject.parseObject(accEmpAttrJson);
		
	}
	
	@RequestMapping(value = "/hrp/acc/accempattr/initAccEmpAttrMainPage", method = RequestMethod.GET)
		
	public String initAccEmpAttrMainPage(String param,Model mode) throws Exception {
		
		if(param.length()>0){
			
			mode.addAttribute("emp_ids", param.toString().substring(0, param.length()-1));
			
		}
		
		return "hrp/acc/acccheckitem/accCheckEmpBatchUpdate";
	}

}

