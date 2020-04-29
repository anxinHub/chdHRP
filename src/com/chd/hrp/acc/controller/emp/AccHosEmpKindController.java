/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/
package com.chd.hrp.acc.controller.emp;
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
import com.chd.base.util.StringTool;
import com.chd.hrp.acc.entity.AccHosEmpKind;
import com.chd.hrp.acc.serviceImpl.emp.AccHosEmpKindServiceImpl;

/**
* @Title. @Description.
* 职工分类
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


@Controller
public class AccHosEmpKindController extends BaseController{
	private static Logger logger = Logger.getLogger(AccHosEmpKindController.class);
	
	
	@Resource(name = "accHosEmpKindService")
	private final AccHosEmpKindServiceImpl accHosEmpKindService = null;
   
    
	/**
	*职工分类<BR>
	*维护页面跳转
	*/
	
	@RequestMapping(value = "/hrp/acc/acchosempkind/accHosEmpKindMainPage", method = RequestMethod.GET)
	public String accHosEmpKindMainPage(Model mode) throws Exception {

		return "hrp/acc/acchosempkind/accHosEmpKindMain";

	}
	/**
	*职工分类<BR>
	*维护页面跳转
	*/
	// 添加页面
	@RequestMapping(value = "/hrp/acc/acchosempkind/accHosEmpKindAddPage", method = RequestMethod.GET)
	public String accHosEmpKindAddPage(Model mode) throws Exception {

		return "hrp/acc/acchosempkind/accHosEmpKindAdd";

	}
	/**
	*职工分类<BR>
	*保存
	*/
	@RequestMapping(value = "/hrp/acc/acchosempkind/addAccHosEmpKind", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> addAccHosEmpKind(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
			if(mapVo.get("group_id") == null){
			
			mapVo.put("group_id", SessionManager.getGroupId());
			
			}
		
			if(mapVo.get("hos_id") == null){
				
			mapVo.put("hos_id", SessionManager.getHosId());
			
			}
			
			if(mapVo.get("is_auto").equals("true")){
				// 根据名称生成拼音码
				mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("kind_name").toString()));
		
				// 根据名称生成五笔码
				mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("kind_name").toString()));
			}
		
		String AccHosEmpKindJson = accHosEmpKindService.addAccHosEmpKind(mapVo);

		return JSONObject.parseObject(AccHosEmpKindJson);
		
	}
	/**
	*职工分类<BR>
	*查询
	*/
	@RequestMapping(value = "/hrp/acc/acchosempkind/queryAccHosEmpKind", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> queryAccHosEmpKind(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
       
		mapVo.put("hos_id", SessionManager.getHosId());
        
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		if(mapVo.get("kind_name") != null){
		//用来转换大写
		mapVo.put("kind_name",mapVo.get("kind_name").toString().toUpperCase());
		}
        String AccHosEmpKind = accHosEmpKindService.queryAccHosEmpKind(getPage(mapVo));

		return JSONObject.parseObject(AccHosEmpKind);
		
	}
	/**
	*职工分类<BR>
	*删除
	*/
	@RequestMapping(value = "/hrp/acc/acchosempkind/deleteAccHosEmpKind", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAccHosEmpKind(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		
		for ( String id: paramVo.split(",")) {
			
			String [] res = id.split("@");
			
			Map<String, Object> mapVo=new HashMap<String, Object>();
			
            mapVo.put("kind_code", res[0]);//实际实体类变量
            
            mapVo.put("group_id", res[1]);
            
            mapVo.put("hos_id", res[2]);
           
            listVo.add(mapVo);
        }
		
		String AccHosEmpKindJson = accHosEmpKindService.deleteBatchAccHosEmpKind(listVo);
	   
		return JSONObject.parseObject(AccHosEmpKindJson);
			
	}
	
	/**
	*职工分类<BR>
	*修改页面跳转
	*/
	@RequestMapping(value = "/hrp/acc/acchosempkind/accHosEmpKindUpdatePage", method = RequestMethod.GET)
	
	public String accHosEmpKindUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
        
		AccHosEmpKind accHosEmpKind = new AccHosEmpKind();
		
		accHosEmpKind = accHosEmpKindService.queryAccHosEmpKindByCode(mapVo);
		
		mode.addAttribute("group_id", accHosEmpKind.getGroup_id());
		
		mode.addAttribute("hos_id", accHosEmpKind.getHos_id());
		
		mode.addAttribute("kind_code", accHosEmpKind.getKind_code());
		
		mode.addAttribute("kind_name", accHosEmpKind.getKind_name());
		
		mode.addAttribute("spell_code", accHosEmpKind.getSpell_code());
		
		mode.addAttribute("wbx_code", accHosEmpKind.getWbx_code());
		
		mode.addAttribute("note", accHosEmpKind.getNote());
		
		mode.addAttribute("is_stop", accHosEmpKind.getIs_stop());
		
		return "hrp/acc/acchosempkind/accHosEmpKindUpdate";
	}
	/**
	*职工分类<BR>
	*修改保存
	*/	
	
	@RequestMapping(value = "/hrp/acc/acchosempkind/updateAccHosEmpKind", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateAccHosEmpKind(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
	   
			if(mapVo.get("group_id") == null){
			
			mapVo.put("group_id", SessionManager.getGroupId());
			
			}
		
			if(mapVo.get("hos_id") == null){
				
			mapVo.put("hos_id", SessionManager.getHosId());
			
			}
			
			if(mapVo.get("is_auto").equals("true")){
				// 根据名称生成拼音码
				mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("kind_name").toString()));
		
				// 根据名称生成五笔码
				mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("kind_name").toString()));
			}
		
		String accHosEmpKindJson = accHosEmpKindService.updateAccHosEmpKind(mapVo);
		
		return JSONObject.parseObject(accHosEmpKindJson);
	}
	

}

