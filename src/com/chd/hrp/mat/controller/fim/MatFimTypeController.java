package com.chd.hrp.mat.controller.fim;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.bouncycastle.math.raw.Mod;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.portlet.bind.annotation.ResourceMapping;

import com.alibaba.fastjson.JSONObject;
import com.chd.base.BaseController;
import com.chd.base.SessionManager;
import com.chd.base.util.StringTool;
import com.chd.hrp.mat.entity.MatFimType;
import com.chd.hrp.mat.service.fim.MatFimTypeServer;
@Controller
public class MatFimTypeController extends BaseController{
/**
 * 2017年4月6日 新增物资财务功能
 * @DH
 * 	
 */
	private static Logger logger = Logger.getLogger(MatFimTypeController.class);
	
	@Resource()
	private final  MatFimTypeServer matfimtypeserver=null;
	/**
	 * @Description 
	 * 主页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/mat/fim/matFimTypeMainPage", method = RequestMethod.GET)
	public String matFimTypeMainPage(Model mode) throws Exception {
		return "hrp/mat/fim/matFimTypeMain";
	}
	/**
	 * 查询数据，加载页面
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/fim/queryMatFimType", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMatFimType(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
	    if(mapVo.get("group_id") == null){
			
		mapVo.put("group_id", SessionManager.getGroupId());
		
		}
		
		if(mapVo.get("hos_id") == null){
			
		mapVo.put("hos_id", SessionManager.getHosId());
		
		}
		
		if(mapVo.get("copy_code") == null){
			
		mapVo.put("copy_code", SessionManager.getCopyCode());
        
		}
		String matBillMain = matfimtypeserver.queryMatBillMain(getPage(mapVo));

		return JSONObject.parseObject(matBillMain);
	}
	/**
	 * 跳转添加页面
	 * @return
	 */
	@RequestMapping(value = "/hrp/mat/fim/addMatFimTypePage", method = RequestMethod.GET)
	public String addMatFimTypePage(){
		return "/hrp/mat/fim/matFimTypeAdd";
		
	}
	/**
	 * 添加
	 * @return
	 */
	@RequestMapping(value = "/hrp/mat/fim/addMatFimType", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addMatFimType(
			@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}

		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("fim_type_name").toString()));
		mapVo.put("wbx_code",StringTool.toWuBi(mapVo.get("fim_type_name").toString()));
		String matfimtypeJson = matfimtypeserver.add(mapVo);

		return JSONObject.parseObject(matfimtypeJson);

	}
	
	/**
	 * 修改数据
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/fim/updateMatFimType", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateMatFimType(
			@RequestParam Map<String, Object> mapVo, Model mode)
					throws Exception {
		
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("fim_type_name").toString()));
		mapVo.put("wbx_code",StringTool.toWuBi(mapVo.get("fim_type_name").toString()));
		String matfimtypeJson = matfimtypeserver.update(mapVo);
		
		return JSONObject.parseObject(matfimtypeJson);
		
	}
	/**
	 * 修改物资财务页面跳转
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/fim/updateMatFimTypePage", method = RequestMethod.GET)
	public String updateMatFimTypePage(@RequestParam Map<String, Object> mapVo, Model mode)throws Exception {
		
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		MatFimType matfimtype= new MatFimType();
		matfimtype = matfimtypeserver.updatepage(mapVo);
		mode.addAttribute("fim_type_code",matfimtype.getFim_type_code());
		mode.addAttribute("fim_type_name",matfimtype.getFim_type_name());
		mode.addAttribute("is_stop", matfimtype.getIs_stop());
		mode.addAttribute("wbx_code", matfimtype.getWbx_code());
		mode.addAttribute("spell_code", matfimtype.getSpell_code());
		return "hrp/mat/fim/matFimTypeUpdate";
		
	}

	/**
	 * 删除
	 * @param paramVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/fim/deleteMatFimType", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteMatLocationType(@RequestParam(value = "ParamVo") String paramVo, Model mode)
			throws Exception {

		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();

		for (String id : paramVo.split(",")) {

			Map<String, Object> mapVo = new HashMap<String, Object>();
			String[] ids = id.split("@");

			// 表的主键
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());
			mapVo.put("fim_type_code", ids[3]);

			listVo.add(mapVo);

		}

		String matLocationTypeJson = matfimtypeserver.deleteBatch(listVo);

		return JSONObject.parseObject(matLocationTypeJson);

	}
	
	
		
}
