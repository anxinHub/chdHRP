/**
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
package com.chd.hrp.ass.controller.dict;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.nutz.lang.Strings;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.chd.base.BaseController;
import com.chd.base.SessionManager;
import com.chd.base.util.ChdJson;
import com.chd.base.util.Plupload;
import com.chd.base.util.StringTool;
import com.chd.hrp.ass.entity.dict.AssGBcode;
import com.chd.hrp.ass.service.base.AssBaseService;
import com.chd.hrp.ass.service.dict.AssGBcodeService;

/**
 * @Description: 050101国标码字典
 * @Table: ASS_GB_DICT
 * @Author: bell
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Controller
public class AssGBcodeController extends BaseController {

	private static Logger logger = Logger.getLogger(AssGBcodeController.class);

	// 引入Service服务
	@Resource(name = "assGBcodeService")
	private final AssGBcodeService assGBcodeService = null;

	// 引入Service服务
	@Resource(name = "assBaseService")
	private final AssBaseService assBaseService = null;

	/**
	 * @Description 主页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assgbcode/assGBcodeMainPage", method = RequestMethod.GET)
	public String assGBcodeMainPage(Model mode) throws Exception {
		return "hrp/ass/assgbcode/assGBcodeMain";

	}

	/**
	 * @Description 
	 * 添加页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/ass/assgbcode/assGBcodeAddPage", method = RequestMethod.GET)
	public String addGBcode(Model mode) throws Exception {

		return "hrp/ass/assgbcode/assGBcodeAdd";


	}


	/**
	 * @Description 添加数据 050101国标码字典
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assgbcode/addAssGBcode", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addAssGBcode(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		String assGBcodeJson = "";
		mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("gb_name").toString()));

		mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("gb_name").toString()));

		try{
		assGBcodeJson = assGBcodeService.addAssGBcode(mapVo);
		}catch(Exception e){
			return JSONObject.parseObject("{\"error\":\""+e.getMessage()+" \"}");
		}
		return JSONObject.parseObject(assGBcodeJson);

	}

	/**
	 * @Description 更新跳转页面 050101国标码字典
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assgbcode/assGBcodeUpdatePage", method = RequestMethod.GET)
	public String assGBcodeUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

	

		AssGBcode AssGBcode = new AssGBcode();

		AssGBcode = assGBcodeService.queryAssGBcodeByCode(mapVo);

	
		mode.addAttribute("gb_code", AssGBcode.getGb_code());
		mode.addAttribute("gb_name", AssGBcode.getGb_name());
		mode.addAttribute("supper_code", AssGBcode.getSupper_code());

		mode.addAttribute("spell_code", AssGBcode.getSpell_code());
		mode.addAttribute("wbx_code", AssGBcode.getWbx_code());
		mode.addAttribute("is_last", AssGBcode.getIs_last());
		mode.addAttribute("gb_level", AssGBcode.getGb_level());
		mode.addAttribute("is_stop", AssGBcode.getIs_stop());
		return "hrp/ass/assgbcode/assGBcodeUpdate";

	}

	/**
	 * @Description 更新数据 050101国标码字典
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assgbcode/updateAssGBcode", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateAssGBcode(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String AssGBcodeJson = "" ;

		mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("gb_name").toString()));

		mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("gb_name").toString()));
		
	
		try{
		 
			AssGBcodeJson = assGBcodeService.updateAssGBcode(mapVo);
		
		}catch(Exception e){
			
			return JSONObject.parseObject("{\"error\":\""+e.getMessage()+" \"}");
		}
		
		return JSONObject.parseObject(AssGBcodeJson);
	}

	/**
	 * @Description 删除数据 050101国标码字典
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assgbcode/deleteAssGBcode", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAssGBcode(@RequestParam(value = "ParamVo") String paramVo, Model mode) throws Exception {

		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();
		String AssGBcodeJson = "";
		String retErrot = "";
		for (String id : paramVo.split(",")) {

			Map<String, Object> mapVo = new HashMap<String, Object>();

			String[] ids = id.split("@");
			String str = assBaseService.isExistsDataByTable("ASS_GB_DICT", ids[3]);

			if (Strings.isNotBlank(str)) {
				retErrot = "{\"error\":\"删除失败，选择的资产类别被以下业务使用：【" + str.substring(0, str.length() - 1) + "】。\",\"state\":\"false\"}";
				return JSONObject.parseObject(retErrot);
			}

			// 表的主键
			mapVo.put("group_id", ids[0]);
			mapVo.put("hos_id", ids[1]);
			mapVo.put("copy_code", ids[2]);
			mapVo.put("gb_code", ids[3]);
//			mapVo.put("supper_code", ids[4]);
			//存在下级分类时，不允许修改 
			List<AssGBcode> child = assGBcodeService.queryAssGBcodeChild(mapVo);
			if (child !=null && child.size()>0) {
				retErrot = "{\"error\":\"存在下级分类，请先删除下级\",\"state\":\"false\"}";
				return JSONObject.parseObject(retErrot);
			}

			try{
			AssGBcodeJson = assGBcodeService.deleteAssGBcode(mapVo);
			// listVo.add(mapVo);
			}catch(Exception e){
				return JSONObject.parseObject("{\"error\":\""+e.getMessage()+" \"}");
			}
		}

		// String AssGBcodeJson =
		// AssGBcodeService.deleteBatchAssGBcode(listVo);

		return JSONObject.parseObject(AssGBcodeJson);

	}

	/**
	 * @Description 查询数据 050101国标码字典
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assgbcode/queryAssGBcode", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssGBcode(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {


		String AssGBcode = assGBcodeService.queryAssGBcode(getPage(mapVo));

		return JSONObject.parseObject(AssGBcode);

	}

	
	/**
	 * @Description 导入数据 050101国标码字典
	 * @param plupload
	 * @param request
	 * @param response
	 * @param mode
	 * @return String
	 * @throws IOException
	 */
	@RequestMapping(value = "/hrp/ass/assgbcode/assGBcodeImportPage", method = RequestMethod.POST)
	@ResponseBody
	public String readAssGBcodeFiles(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		try {
			
			String reJson=assGBcodeService.readAssGBcodeFiles(mapVo);
			
			return reJson;
			
		} catch (Exception e) {
			
			return "{\"error\":\""+e.getMessage()+"\"}";
			
		}
	}
		

	
}
