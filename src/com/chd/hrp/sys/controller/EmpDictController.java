/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/
package com.chd.hrp.sys.controller;
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
import com.chd.hrp.sys.entity.EmpDict;
import com.chd.hrp.sys.serviceImpl.EmpDictServiceImpl;

/**
* @Title. @Description.
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


@Controller
public class EmpDictController extends BaseController{
	private static Logger logger = Logger.getLogger(EmpDictController.class);
	
	
	@Resource(name = "empDictService")
	private final EmpDictServiceImpl empDictService = null;
   
    
	
	// 维护页面跳转
	@RequestMapping(value = "/hrp/sys/empdict/empDictMainPage", method = RequestMethod.GET)
	public String empDictMainPage(Model mode) throws Exception {

		return "hrp/sys/empdict/empDictMain";

	}

	// 添加页面
	@RequestMapping(value = "/hrp/sys/empdict/empDictAddPage", method = RequestMethod.GET)
	public String empDictAddPage(Model mode) throws Exception {

		return "hrp/sys/empdict/empDictAdd";

	}

	// 保存
	@RequestMapping(value = "/hrp/sys/empdict/addEmpDict", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> addEmpDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		
		String empDictJson = empDictService.addEmpDict(mapVo);

		return JSONObject.parseObject(empDictJson);
		
	}

	// 查询
	@RequestMapping(value = "/hrp/sys/empdict/queryEmpDict", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> queryEmpDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
	  
		String empDict = empDictService.queryEmpDict(getPage(mapVo));

		return JSONObject.parseObject(empDict);
		
	}
	
	/**
	 * 系统平台【职工信息-职工维护】：主查询
	 */
	@RequestMapping(value = "/hrp/sys/empdict/queryEmpDictList", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryEmpDictList(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		String empDict = empDictService.queryEmpDictList(getPage(mapVo));
		return JSONObject.parseObject(empDict);
	}
	
	//删除
	@RequestMapping(value = "/hrp/sys/empdict/deleteEmpDict", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteEmpDict(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		for ( String id: paramVo.split(",")) {
			Map<String, Object> mapVo=new HashMap<String, Object>();
			
			
            mapVo.put("temp", id);//实际实体类变量
            listVo.add(mapVo);
        }
		String empDictJson = empDictService.deleteBatchEmpDict(listVo);
	   return JSONObject.parseObject(empDictJson);
			
	}
	
	
	// 修改页面跳转
	@RequestMapping(value = "/hrp/sys/empdict/empDictUpdatePage", method = RequestMethod.GET)
	
	public String empDictUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
        EmpDict empDict = new EmpDict();
		empDict = empDictService.queryEmpDictByCode(mapVo);
		mode.addAttribute("emp_no", empDict.getEmp_no());
		mode.addAttribute("group_id", empDict.getGroup_id());
		mode.addAttribute("hos_id", empDict.getHos_id());
		mode.addAttribute("emp_id", empDict.getEmp_id());
		mode.addAttribute("emp_code", empDict.getEmp_code());
		mode.addAttribute("emp_name", empDict.getEmp_name());
		mode.addAttribute("user_code", empDict.getUser_code());
		mode.addAttribute("create_date", empDict.getCreate_date());
		mode.addAttribute("note", empDict.getNote());
		mode.addAttribute("is_stop", empDict.getIs_stop());
		
		return "hrp/sys/empdict/empDictUpdate";
	}
		
	// 修改保存
	@RequestMapping(value = "/hrp/sys/empdict/updateEmpDict", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateEmpDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String empDictJson = empDictService.updateEmpDict(mapVo);
		
		return JSONObject.parseObject(empDictJson);
	}
	
	// 导入
	@RequestMapping(value = "/hrp/sys/empdict/importEmpDict", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> importEmpDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String empDictJson = empDictService.importEmpDict(mapVo);
		
		return JSONObject.parseObject(empDictJson);
	}

}

