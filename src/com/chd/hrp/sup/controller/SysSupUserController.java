/**
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */

package com.chd.hrp.sup.controller;
  
import java.io.IOException;
import java.util.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
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
import com.chd.base.util.DateUtil;
import com.chd.base.util.Plupload;
import com.chd.base.util.StringTool;
import com.chd.base.util.UploadUtil;
import com.chd.base.util.WisdomCloud;
import com.chd.hrp.sup.entity.SysSupUser;
import com.chd.hrp.sup.service.SysSupUserService;

/**
 * @Description: 供应商管理用户
 * @Table: SYS_SUP_USER
 * @Author: bell
 * @email: bell@s-chd.com
 * @Version: 1.0
 */
   
@Controller
public class SysSupUserController extends BaseController {

	private static Logger logger = Logger.getLogger(SysSupUserController.class);

	// 引入Service服务
	@Resource(name = "sysSupUserService")
	private final SysSupUserService sysSupUserService = null;

	/**
	 * @Description 主页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/sup/syssupuser/sysSupUserMainPage", method = RequestMethod.GET)
	public String sysSupUserMainPage(Model mode) throws Exception {

		return "hrp/sup/syssupuser/sysSupUserMain";

	}

	/**
	 * @Description 添加页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/sup/syssupuser/sysSupUserAddPage", method = RequestMethod.GET)
	public String sysSupUserAddPage(Model mode) throws Exception {

		return "hrp/sup/syssupuser/sysSupUserAdd";

	}

	/**
	 * @Description 添加数据 供应商管理用户
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/sup/syssupuser/addSysSupUser", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addSysSupUser(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}

		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}

		if (mapVo.get("acct_year") == null) {
			mapVo.put("acct_year", SessionManager.getAcctYear());
		}
		if (mapVo.get("mod_code") == null) {
			mapVo.put("mod_code", "10");
		}
		WisdomCloud wc = new WisdomCloud();
		mapVo.put("user_pwd", wc.encrypt("123456"));

		mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("user_name").toString()));
		mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("user_name").toString()));

		String sysSupUserJson = sysSupUserService.add(mapVo);

		return JSONObject.parseObject(sysSupUserJson);

	}

	/**
	 * @Description 更新跳转页面 供应商管理用户
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/sup/syssupuser/sysSupUserUpdatePage", method = RequestMethod.GET)
	public String sysSupUserUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}

		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}

		if (mapVo.get("acct_year") == null) {
			mapVo.put("acct_year", SessionManager.getAcctYear());
		}

		if (mapVo.get("mod_code") == null) {
			mapVo.put("mod_code", "10");
		}
		SysSupUser sysSupUser = new SysSupUser();
		
		if("0".equals(mapVo.get("sup_id").toString())){
			sysSupUser = sysSupUserService.queryByCodeSupId(mapVo);
			mode.addAttribute("group_id", sysSupUser.getGroup_id());
			mode.addAttribute("hos_id", sysSupUser.getHos_id());
			mode.addAttribute("user_code", sysSupUser.getUser_code());
			mode.addAttribute("user_name", sysSupUser.getUser_name());
			mode.addAttribute("user_pwd", sysSupUser.getUser_pwd());
			mode.addAttribute("spell_code", sysSupUser.getSpell_code());
			mode.addAttribute("wbx_code", sysSupUser.getWbx_code());
			mode.addAttribute("mod_code", sysSupUser.getMod_code());
			mode.addAttribute("copy_code", sysSupUser.getCopy_code());
			mode.addAttribute("sup_id", sysSupUser.getSup_id());
			mode.addAttribute("sup_no", sysSupUser.getSup_no());
			mode.addAttribute("sup_code", "");
			mode.addAttribute("sup_name", "");
			mode.addAttribute("is_disable", sysSupUser.getIs_disable());
			mode.addAttribute("is_manager", sysSupUser.getIs_manager());
		}else{
			sysSupUser = sysSupUserService.queryByCode(mapVo);
			mode.addAttribute("group_id", sysSupUser.getGroup_id());
			mode.addAttribute("hos_id", sysSupUser.getHos_id());
			mode.addAttribute("user_code", sysSupUser.getUser_code());
			mode.addAttribute("user_name", sysSupUser.getUser_name());
			mode.addAttribute("user_pwd", sysSupUser.getUser_pwd());
			mode.addAttribute("spell_code", sysSupUser.getSpell_code());
			mode.addAttribute("wbx_code", sysSupUser.getWbx_code());
			mode.addAttribute("mod_code", sysSupUser.getMod_code());
			mode.addAttribute("copy_code", sysSupUser.getCopy_code());
			mode.addAttribute("sup_id", sysSupUser.getSup_id());
			mode.addAttribute("sup_no", sysSupUser.getSup_no());
			mode.addAttribute("sup_code", sysSupUser.getSup_code());
			mode.addAttribute("is_disable", sysSupUser.getIs_disable());
			mode.addAttribute("is_manager", sysSupUser.getIs_manager());
		}
		
		return "hrp/sup/syssupuser/sysSupUserUpdate";
	}

	/**
	 * @Description 更新数据 供应商管理用户
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/sup/syssupuser/updateSysSupUser", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateSysSupUser(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}

		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		if (mapVo.get("mod_code") == null) {
			mapVo.put("mod_code", "10");
		}
		
		mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("user_name").toString()));

		mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("user_name").toString()));

		String sysSupUserJson = sysSupUserService.update(mapVo);

		return JSONObject.parseObject(sysSupUserJson);
	}

	/**
	 * @Description 更新数据 供应商管理用户
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/sup/syssupuser/addOrUpdateSysSupUser", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addOrUpdateSysSupUser(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		String sysSupUserJson = "";

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}

		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		if (mapVo.get("mod_code") == null) {
			mapVo.put("mod_code", "10");
		}
	
		mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("user_name").toString()));

		mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("user_name").toString()));

		List<Map> detail = ChdJson.fromJsonAsList(Map.class, mapVo.get("ParamVo").toString());

		for (Map<String, Object> detailVo : detail) {

			if (detailVo.get("group_id") == null) {
				detailVo.put("group_id", SessionManager.getGroupId());
			}

			if (detailVo.get("hos_id") == null) {
				detailVo.put("hos_id", SessionManager.getHosId());
			}

			if (detailVo.get("copy_code") == null) {
				detailVo.put("copy_code", SessionManager.getCopyCode());
			}

			detailVo.put("spell_code", StringTool.toPinyinShouZiMu(detailVo.get("user_name").toString()));

			detailVo.put("wbx_code", StringTool.toWuBi(detailVo.get("user_name").toString()));

			sysSupUserJson = sysSupUserService.addOrUpdate(detailVo);

		}
		return JSONObject.parseObject(sysSupUserJson);
	}

	/**
	 * @Description 删除数据 供应商管理用户
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/sup/syssupuser/deleteSysSupUser", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteSysSupUser(@RequestParam(value = "ParamVo") String paramVo, Model mode) throws Exception {

		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();

		for (String id : paramVo.split(",")) {

			Map<String, Object> mapVo = new HashMap<String, Object>();

			String[] ids = id.split("@");

			// 表的主键
			mapVo.put("group_id", ids[0]);
			mapVo.put("hos_id", ids[1]);
			mapVo.put("user_code", ids[2]);

			listVo.add(mapVo);

		}

		String sysSupUserJson = sysSupUserService.deleteBatch(listVo);

		return JSONObject.parseObject(sysSupUserJson);

	}

	/**
	 * @Description 查询数据 供应商管理用户
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/sup/syssupuser/querySysSupUser", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> querySysSupUser(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {

			mapVo.put("group_id", SessionManager.getGroupId());

		}

		if (mapVo.get("hos_id") == null) {

			mapVo.put("hos_id", SessionManager.getHosId());

		}

		if (mapVo.get("copy_code") == null) {

			mapVo.put("copy_code", SessionManager.getCopyCode());

		}
		if (mapVo.get("acct_year") == null) {

			mapVo.put("acct_year", SessionManager.getAcctYear());

		}
		String sysSupUser = sysSupUserService.query(getPage(mapVo));

		return JSONObject.parseObject(sysSupUser);

	}

	/**
	 * @Description 导入跳转页面 供应商管理用户
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/sup/syssupuser/sysSupUserImportPage", method = RequestMethod.GET)
	public String sysSupUserImportPage(Model mode) throws Exception {

		return "hrp/sup/syssupuser/sysSupUserImport";

	}

	/**
	 * @Description 下载导入模版 供应商管理用户
	 * @param request
	 * @param response
	 * @param mode
	 * @return String
	 * @throws IOException
	 */
	@RequestMapping(value = "hrp/sup/syssupuser/downTemplate", method = RequestMethod.GET)
	public String downTemplate(Plupload plupload, HttpServletRequest request, HttpServletResponse response, Model mode) throws IOException {

		printTemplate(request, response, "sup\\downTemplate", "供应商管理用户.xls");

		return null;
	}

	/**
	 * @Description 导入数据 供应商管理用户
	 * @param plupload
	 * @param request
	 * @param response
	 * @param mode
	 * @return String
	 * @throws IOException
	 */
	@RequestMapping(value = "/hrp/sup/syssupuser/readSysSupUserFiles", method = RequestMethod.POST)
	public String readSysSupUserFiles(Plupload plupload, HttpServletRequest request, HttpServletResponse response, Model mode) throws IOException {

		List<SysSupUser> list_err = new ArrayList<SysSupUser>();

		List<String[]> list = UploadUtil.readFile(plupload, request, response);

		try {
			for (int i = 1; i < list.size(); i++) {

				StringBuffer err_sb = new StringBuffer();

				SysSupUser sysSupUser = new SysSupUser();

				String temp[] = list.get(i);// 行
				Map<String, Object> mapVo = new HashMap<String, Object>();

				mapVo.put("group_id", SessionManager.getGroupId());

				mapVo.put("hos_id", SessionManager.getHosId());

				if (StringTool.isNotBlank(temp[2])) {

					sysSupUser.setUser_code(temp[2]);
					mapVo.put("user_code", temp[2]);

				} else {

					err_sb.append("用户编码为空  ");

				}

				if (StringTool.isNotBlank(temp[3])) {

					sysSupUser.setUser_name(temp[3]);
					mapVo.put("user_name", temp[3]);

				} else {

					err_sb.append("用户名称为空  ");

				}

				if (StringTool.isNotBlank(temp[4])) {

					sysSupUser.setUser_pwd(temp[4]);
					mapVo.put("user_pwd", temp[4]);

				} else {

					err_sb.append("密码为空  ");

				}

				if (StringTool.isNotBlank(temp[5])) {

					sysSupUser.setSpell_code(temp[5]);
					mapVo.put("spell_code", temp[5]);

				} else {

					err_sb.append("拼音码为空  ");

				}

				if (StringTool.isNotBlank(temp[6])) {

					sysSupUser.setWbx_code(temp[6]);
					mapVo.put("wbx_code", temp[6]);

				} else {

					err_sb.append("五笔码为空  ");

				}

				if (StringTool.isNotBlank(temp[7])) {

					sysSupUser.setMod_code(temp[7]);
					mapVo.put("mod_code", temp[7]);

				} else {

					err_sb.append("系统模块为空  ");

				}

				mapVo.put("copy_code", SessionManager.getCopyCode());

				if (StringTool.isNotBlank(temp[9])) {

					sysSupUser.setSup_id(Long.valueOf(temp[9]));
					mapVo.put("sup_id", temp[9]);

				} else {

					err_sb.append("所属供应商为空  ");

				}

				SysSupUser data_exc_extis = sysSupUserService.queryByCode(mapVo);

				if (data_exc_extis != null) {

					err_sb.append("数据已经存在！ ");

				}
				if (err_sb.toString().length() > 0) {

					sysSupUser.setError_type(err_sb.toString());

					list_err.add(sysSupUser);

				} else {
					mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("").toString()));
					mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("").toString()));

					String dataJson = sysSupUserService.add(mapVo);

				}

			}

		}
		catch (DataAccessException e) {

			SysSupUser data_exc = new SysSupUser();

			data_exc.setError_type("导入系统出错");

			list_err.add(data_exc);

		}

		response.getWriter().print(ChdJson.toJson(list_err, list_err.size()));

		return null;

	}

	/**
	 * @Description 批量添加数据 供应商管理用户
	 * @param plupload
	 * @param request
	 * @param response
	 * @param mode
	 * @return String
	 * @throws IOException
	 */
	@RequestMapping(value = "/hrp/sup/syssupuser/addBatchSysSupUser", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addBatchSysSupUser(@RequestParam(value = "ParamVo") String paramVo, Model mode) throws Exception {

		List<SysSupUser> list_err = new ArrayList<SysSupUser>();

		JSONArray json = JSONArray.parseArray(paramVo);

		Map<String, Object> mapVo = new HashMap<String, Object>();

		if (mapVo.get("group_id") == null) {

			mapVo.put("group_id", SessionManager.getGroupId());

		}

		if (mapVo.get("hos_id") == null) {

			mapVo.put("hos_id", SessionManager.getHosId());

		}
		if (mapVo.get("copy_code") == null) {

			mapVo.put("copy_code", SessionManager.getCopyCode());

		}

		Iterator it = json.iterator();

		try {

			while (it.hasNext()) {

				StringBuffer err_sb = new StringBuffer();

				SysSupUser sysSupUser = new SysSupUser();

				JSONObject jsonObj = JSONObject.parseObject(it.next().toString());

				if (StringTool.isNotBlank(jsonObj.get("user_code"))) {

					sysSupUser.setUser_code((String) jsonObj.get("user_code"));
					mapVo.put("user_code", jsonObj.get("user_code"));
				} else {

					err_sb.append("用户编码为空  ");

				}

				if (StringTool.isNotBlank(jsonObj.get("user_name"))) {

					sysSupUser.setUser_name((String) jsonObj.get("user_name"));
					mapVo.put("user_name", jsonObj.get("user_name"));
				} else {

					err_sb.append("用户名称为空  ");

				}

				if (StringTool.isNotBlank(jsonObj.get("user_pwd"))) {

					sysSupUser.setUser_pwd((String) jsonObj.get("user_pwd"));
					mapVo.put("user_pwd", jsonObj.get("user_pwd"));
				} else {

					err_sb.append("密码为空  ");

				}

				if (StringTool.isNotBlank(jsonObj.get("spell_code"))) {

					sysSupUser.setSpell_code((String) jsonObj.get("spell_code"));
					mapVo.put("spell_code", jsonObj.get("spell_code"));
				} else {

					err_sb.append("拼音码为空  ");

				}

				if (StringTool.isNotBlank(jsonObj.get("wbx_code"))) {

					sysSupUser.setWbx_code((String) jsonObj.get("wbx_code"));
					mapVo.put("wbx_code", jsonObj.get("wbx_code"));
				} else {

					err_sb.append("五笔码为空  ");

				}

				if (StringTool.isNotBlank(jsonObj.get("mod_code"))) {

					sysSupUser.setMod_code((String) jsonObj.get("mod_code"));
					mapVo.put("mod_code", jsonObj.get("mod_code"));
				} else {

					err_sb.append("系统模块为空  ");

				}

				if (StringTool.isNotBlank(jsonObj.get("sup_id"))) {

					sysSupUser.setSup_id(Long.valueOf((String) jsonObj.get("sup_id")));
					mapVo.put("sup_id", jsonObj.get("sup_id"));
				} else {

					err_sb.append("所属供应商为空  ");

				}

				SysSupUser data_exc_extis = sysSupUserService.queryByCode(mapVo);

				if (data_exc_extis != null) {

					err_sb.append("编码已经存在！ ");

				}
				if (err_sb.toString().length() > 0) {

					sysSupUser.setError_type(err_sb.toString());

					list_err.add(sysSupUser);

				} else {
					mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("").toString()));
					mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("").toString()));

					String dataJson = sysSupUserService.add(mapVo);

				}

			}

		}
		catch (DataAccessException e) {

			SysSupUser data_exc = new SysSupUser();

			list_err.add(data_exc);

			return JSONObject.parseObject("{\"msg\":\"导入系统出错,请重新导入.\",\"state\":\"err\"}");

		}

		if (list_err.size() > 0) {

			return JSONObject.parseObject(ChdJson.toJson(list_err, list_err.size()));

		} else {

			return JSONObject.parseObject("{\"msg\":\"导入成功.\",\"state\":\"true\"}");

		}

	}
	
	/**
	 * 
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/sup/syssupuser/generate", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> generate(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {

			mapVo.put("group_id", SessionManager.getGroupId());

		}

		if (mapVo.get("hos_id") == null) {

			mapVo.put("hos_id", SessionManager.getHosId());

		}
		if (mapVo.get("copy_code") == null) {

			mapVo.put("copy_code", SessionManager.getCopyCode());

		}
		if (mapVo.get("mod_code") == null) {
			
			mapVo.put("mod_code", "10");
			
		}
		String generate=sysSupUserService.generate(mapVo);
		
		return JSONObject.parseObject(generate);
		
	}
	/**
	 * 
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/sup/syssupuser/updateSysSupUserPwd", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateSysSupUserPwd(@RequestParam(value = "ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo=new ArrayList<Map<String,Object>>();
		WisdomCloud wc = new WisdomCloud();
		for (String id : paramVo.split(",")) {
			Map<String, Object> mapVo=new HashMap<String, Object>();
			String[] idd=id.split("@");
			
			mapVo.put("group_id", idd[0]);
			mapVo.put("hos_id", idd[1]);
			mapVo.put("user_code", idd[2]);
			mapVo.put("user_pwd", wc.encrypt("123456"));
			
			listVo.add(mapVo);
		}
		
		//System.out.println("CCCCCCCCCCCC"+listVo);
		String generate=sysSupUserService.updateSysSupUserPwd(listVo);
		
		return JSONObject.parseObject(generate);
		
	}
	
}
