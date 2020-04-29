/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/
package com.chd.hrp.mat.controller.matsysset;
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
import com.chd.hrp.mat.serviceImpl.matsysset.MatModStartServiceImpl;
import com.chd.hrp.sys.entity.ModStart;

/**
* @Title. @Description.
* 系统启用
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


@Controller
public class MatModStartController extends BaseController{
	private static Logger logger = Logger.getLogger(MatModStartController.class);
	
	
	@Resource(name = "matModStartService")
	private final MatModStartServiceImpl matModStartService = null;
   
   
	/**
	*系统启用<BR>
	*维护页面跳转
	*/
	
	@RequestMapping(value = "/hrp/mat/matmodstart/modStartMainPage", method = RequestMethod.GET)
	public String modStartMainPage(Model mode) throws Exception {

		return "hrp/mat/matmodstart/modStartMain";

	}
	/**
	*系统启用<BR>
	*维护页面跳转
	*/
	// 添加页面
	@RequestMapping(value = "/hrp/mat/matmodstart/modStartAddPage", method = RequestMethod.GET)
	public String modStartAddPage(Model mode) throws Exception {

		return "hrp/mat/matmodstart/modStartAdd";

	}
	/**
	*系统启用<BR>
	*保存
	*/
	@RequestMapping(value = "/hrp/mat/matsysset/addModStart", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> addModStart(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
        mapVo.put("hos_id", SessionManager.getHosId());
        mapVo.put("copy_code", SessionManager.getCopyCode());
        mapVo.put("create_user", SessionManager.getUserCode());
        mapVo.put("create_date", new Date());
		String modStartJson = matModStartService.addModStart(mapVo);

		return JSONObject.parseObject(modStartJson);
		
	}
	/**
	*系统启用<BR>
	*查询
	*/
	@RequestMapping(value = "/hrp/mat/matmodstart/queryModStart", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> queryModStart(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
	 
		String modStart = matModStartService.queryModStart(getPage(mapVo));
		
		//System.out.println("aaaaaaaaaaa"+modStart);

		return JSONObject.parseObject(modStart);
		
	}
	/**
	*系统启用<BR>
	*删除
	*/
	@RequestMapping(value = "/hrp/mat/matmodstart/deleteModStart", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteModStart(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		for ( String id: paramVo.split(",")) {
			Map<String, Object> mapVo=new HashMap<String, Object>();
			
            mapVo.put("temp", id);//实际实体类变量
            listVo.add(mapVo);
        }
		String modStartJson = matModStartService.deleteBatchModStart(listVo);
	   return JSONObject.parseObject(modStartJson);
			
	}
	
	/**
	*系统启用<BR>
	*修改页面跳转
	*/
	@RequestMapping(value = "/hrp/mat/matmodstart/modStartUpdatePage", method = RequestMethod.GET)
	
	public String modStartUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
        ModStart modStart = new ModStart();
		modStart = matModStartService.queryModStartByCode(mapVo);
		mode.addAttribute("group_id", modStart.getGroup_id());
		mode.addAttribute("hos_id", modStart.getHos_id());
		mode.addAttribute("copy_code", modStart.getCopy_code());
		mode.addAttribute("mod_code", modStart.getMod_code());
		mode.addAttribute("start_year", modStart.getStart_year());
		mode.addAttribute("start_month", modStart.getStart_month());
		mode.addAttribute("create_user", modStart.getCreate_user());
		mode.addAttribute("create_date", modStart.getCreate_date());
		return "hrp/mat/matmodstart/modStartUpdate";
	}
	/**
	*系统启用<BR>
	*修改保存
	*/	
	
	@RequestMapping(value = "/hrp/mat/matmodstart/updateModStart", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateModStart(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
	   
	   
	   
	   
	   
	   
	   
	   
		String modStartJson = matModStartService.updateModStart(mapVo);
		
		return JSONObject.parseObject(modStartJson);
	}
	/**
	*系统启用<BR>
	*导入
	*/
	
	@RequestMapping(value = "/hrp/mat/matmodstart/importModStart", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> importModStart(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String modStartJson = matModStartService.importModStart(mapVo);
		
		return JSONObject.parseObject(modStartJson);
	}

}

