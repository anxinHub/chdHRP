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
import com.chd.hrp.sys.entity.Copy;
import com.chd.hrp.sys.serviceImpl.CopyServiceImpl;

/**
* @Title. @Description.
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


@Controller
public class CopyController extends BaseController{
	private static Logger logger = Logger.getLogger(CopyController.class);
	
	
	@Resource(name = "copyService")
	private final CopyServiceImpl copyService = null;
   
    
	
	// 维护页面跳转
	@RequestMapping(value = "/hrp/sys/copy/copyMainPage", method = RequestMethod.GET)
	public String copyMainPage(Model mode) throws Exception {

		return "hrp/sys/copy/copyMain";

	}

	// 添加页面
	@RequestMapping(value = "/hrp/sys/copy/copyAddPage", method = RequestMethod.GET)
	public String copyAddPage(Model mode) throws Exception {

		return "hrp/sys/copy/copyAdd";

	}

	// 保存
	@RequestMapping(value = "/hrp/sys/copy/addCopy", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> addCopy(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		try{
			String copyJson = copyService.addCopy(mapVo);
			return JSONObject.parseObject(copyJson);
		}catch(Exception e){
			return JSONObject.parseObject("{\"error\":\""+e.getMessage()+"\",\"state\":\"false\"}");
		}
		
		
	}

	// 查询
	@RequestMapping(value = "/hrp/sys/copy/queryCopy", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> queryCopy(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		String copy = copyService.queryCopy(mapVo);

		return JSONObject.parseObject(copy);
		
	}
	
	//删除
	@RequestMapping(value = "/hrp/sys/copy/deleteCopy", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteCopy(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		try{
			List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
			for ( String id: paramVo.split(",")) {
				Map<String, Object> mapVo=new HashMap<String, Object>();
				mapVo.put("group_id", id.split("@")[0]);
				mapVo.put("hos_id", id.split("@")[1]);
	            mapVo.put("copy_code", id.split("@")[2]);
	            listVo.add(mapVo);
	        }
			String copyJson = copyService.deleteBatchCopy(listVo);
		   return JSONObject.parseObject(copyJson);
		}catch(Exception e){
			return JSONObject.parseObject("{\"error\":\""+e.getMessage()+"\",\"state\":\"false\"}"); 
		}
			
	}
	
	
	// 修改页面跳转
	@RequestMapping(value = "/hrp/sys/copy/copyUpdatePage", method = RequestMethod.GET)
	
	public String copyUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
        Copy copy = new Copy();
		copy = copyService.queryCopyByCode(mapVo);
		mode.addAttribute("group_id", copy.getGroup_id());
		mode.addAttribute("hos_id", copy.getHos_id());
		mode.addAttribute("copy_code", copy.getCopy_code());
		mode.addAttribute("copy_name", copy.getCopy_name());
		mode.addAttribute("is_stop", copy.getIs_stop());
		mode.addAttribute("nature_code", copy.getNature_code());
		mode.addAttribute("nature_name", copy.getNature_name());
		mode.addAttribute("note", copy.getNote());
		
		return "hrp/sys/copy/copyUpdate";
	}
		
	// 修改保存
	@RequestMapping(value = "/hrp/sys/copy/updateCopy", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateCopy(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		try{
			String copyJson = copyService.updateCopy(mapVo);
			
			return JSONObject.parseObject(copyJson);
		}catch(Exception e){
			return JSONObject.parseObject("{\"error\":\""+e.getMessage()+"\",\"state\":\"false\"}");
		}
	}
	
	// 导入
	@RequestMapping(value = "/hrp/sys/copy/importCopy", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> importCopy(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String copyJson = copyService.importCopy(mapVo);
		
		return JSONObject.parseObject(copyJson);
	}

}

