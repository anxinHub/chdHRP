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
import com.chd.hrp.sup.entity.SupUser;
import com.chd.hrp.sup.service.SupUserService;

/**
 * @Description: 供应商用户
 * @Table: SUP_USER
 * @Author: bell
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Controller
public class SupUserController extends BaseController {

	private static Logger logger = Logger.getLogger(SupUserController.class);

	// 引入Service服务
	@Resource(name = "supUserService")
	private final SupUserService supUserService = null;

	/**
	 * @Description 主页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/sup/supuser/supUserMainPage", method = RequestMethod.GET)
	public String supUserMainPage(Model mode) throws Exception {

		return "hrp/sup/supuser/supUserMain";

	}

	/**
	 * @Description 添加页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/sup/supuser/supUserAddPage", method = RequestMethod.GET)
	public String supUserAddPage(Model mode) throws Exception {

		return "hrp/sup/supuser/supUserAdd";

	}

	/**
	 * @Description 添加数据 供应商用户
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/sup/supuser/addSupUser", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addSupUser(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

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
		if (mapVo.get("sup_id") == null) {
			mapVo.put("sup_id", SessionManager.getSupId());
		}
		if (mapVo.get("sup_no") == null) {
			mapVo.put("sup_no", SessionManager.getSupNo());
		}
		WisdomCloud wc = new WisdomCloud();
		mapVo.put("user_pwd", wc.encrypt(mapVo.get("user_pwd").toString()));
		
		mapVo.put("mod_code", "10");

		mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("user_name").toString()));
		mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("user_name").toString()));

		String supUserJson = supUserService.add(mapVo);

		return JSONObject.parseObject(supUserJson);

	}

	/**
	 * @Description 更新跳转页面 供应商用户
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/sup/supuser/supUserUpdatePage", method = RequestMethod.GET)
	public String supUserUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

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
		if (mapVo.get("sup_id") == null) {
			mapVo.put("sup_id", SessionManager.getSupId());
		}
		if (mapVo.get("sup_no") == null) {
			mapVo.put("sup_no", SessionManager.getSupNo());
		}
		SupUser supUser = new SupUser();

		supUser = supUserService.queryByCode(mapVo);

		mode.addAttribute("group_id", supUser.getGroup_id());
		mode.addAttribute("hos_id", supUser.getHos_id());
		mode.addAttribute("user_code", supUser.getUser_code());
		mode.addAttribute("user_name", supUser.getUser_name());
		mode.addAttribute("user_pwd", supUser.getUser_pwd());
		mode.addAttribute("spell_code", supUser.getSpell_code());
		mode.addAttribute("wbx_code", supUser.getWbx_code());
		mode.addAttribute("mod_code", supUser.getMod_code());
		mode.addAttribute("copy_code", supUser.getCopy_code());
		mode.addAttribute("sup_id", supUser.getSup_id());
		mode.addAttribute("sup_no", supUser.getSup_no());

		return "hrp/sup/supuser/supUserUpdate";
	}

	/**
	 * @Description 更新数据 供应商用户
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/sup/supuser/updateSupUser", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateSupUser(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}

		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}

		if (mapVo.get("sup_id") == null) {
			mapVo.put("sup_id", SessionManager.getSupId());
		}
		if (mapVo.get("sup_no") == null) {
			mapVo.put("sup_no", SessionManager.getSupNo());
		}
		if(mapVo.get("user_pwd")!=null){
			WisdomCloud wc = new WisdomCloud();
			mapVo.put("user_pwd", wc.encrypt(mapVo.get("user_pwd").toString()));
		}
		
		mapVo.put("mod_code", "10");

		mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("user_name").toString()));
		mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("user_name").toString()));

		String supUserJson = supUserService.update(mapVo);

		return JSONObject.parseObject(supUserJson);
	}

	/**
	 * @Description 更新数据 供应商用户
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/sup/supuser/addOrUpdateSupUser", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addOrUpdateSupUser(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		String supUserJson = "";

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}

		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		if (mapVo.get("sup_id") == null) {
			mapVo.put("sup_id", SessionManager.getSupId());
		}
		if (mapVo.get("sup_no") == null) {
			mapVo.put("sup_no", SessionManager.getSupNo());
		}
		if(mapVo.get("user_pwd")!=null){
			WisdomCloud wc = new WisdomCloud();
			mapVo.put("user_pwd", wc.encrypt(mapVo.get("user_pwd").toString()));
		}
		mapVo.put("mod_code", "10");

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

			if (detailVo.get("sup_id") == null) {
				detailVo.put("sup_id", SessionManager.getSupId());
			}
			if (detailVo.get("sup_no") == null) {
				detailVo.put("sup_no", SessionManager.getSupNo());
			}
			if(detailVo.get("user_pwd")!=null){
				WisdomCloud wc1 = new WisdomCloud();
				mapVo.put("user_pwd", wc1.encrypt(mapVo.get("user_pwd").toString()));
			}
			detailVo.put("spell_code", StringTool.toPinyinShouZiMu(detailVo.get("user_name").toString()));

			detailVo.put("wbx_code", StringTool.toWuBi(detailVo.get("user_name").toString()));

			supUserJson = supUserService.addOrUpdate(detailVo);

		}
		return JSONObject.parseObject(supUserJson);
	}

	/**
	 * @Description 删除数据 供应商用户
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/sup/supuser/deleteSupUser", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteSupUser(@RequestParam(value = "ParamVo") String paramVo, Model mode) throws Exception {

		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();

		for (String id : paramVo.split(",")) {

			Map<String, Object> mapVo = new HashMap<String, Object>();

			String[] ids = id.split("@");

			// 表的主键
			mapVo.put("user_code", ids[0]);
			mapVo.put("group_id", ids[1]);
			mapVo.put("hos_id", ids[2]);
			mapVo.put("copy_code", ids[3]);

			listVo.add(mapVo);

		}

		String supUserJson = supUserService.deleteBatch(listVo);

		return JSONObject.parseObject(supUserJson);

	}

	/**
	 * @Description 查询数据 供应商用户
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/sup/supuser/querySupUser", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> querySupUser(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

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
		if (mapVo.get("sup_id") == null) {
			mapVo.put("sup_id", SessionManager.getSupId());
		}
		
		String supUser = supUserService.query(getPage(mapVo));

		return JSONObject.parseObject(supUser);

	}

	/**
	 * @Description 导入跳转页面 供应商用户
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/sup/supuser/supUserImportPage", method = RequestMethod.GET)
	public String supUserImportPage(Model mode) throws Exception {

		return "hrp/sup/supuser/supUserImport";

	}

	/**
	 * @Description 下载导入模版 供应商用户
	 * @param request
	 * @param response
	 * @param mode
	 * @return String
	 * @throws IOException
	 */
	@RequestMapping(value = "hrp/sup/supuser/downTemplate", method = RequestMethod.GET)
	public String downTemplate(Plupload plupload, HttpServletRequest request, HttpServletResponse response, Model mode) throws IOException {

		printTemplate(request, response, "sup\\downTemplate", "供应商用户.xls");

		return null;
	}

	/**
	 * @Description 导入数据 供应商用户
	 * @param plupload
	 * @param request
	 * @param response
	 * @param mode
	 * @return String
	 * @throws IOException
	 */
	@RequestMapping(value = "/hrp/sup/supuser/readSupUserFiles", method = RequestMethod.POST)
	public String readSupUserFiles(Plupload plupload, HttpServletRequest request, HttpServletResponse response, Model mode) throws IOException {

		List<SupUser> list_err = new ArrayList<SupUser>();

		List<String[]> list = UploadUtil.readFile(plupload, request, response);

		try {
			for (int i = 1; i < list.size(); i++) {

				StringBuffer err_sb = new StringBuffer();

				SupUser supUser = new SupUser();

				String temp[] = list.get(i);// 行
				Map<String, Object> mapVo = new HashMap<String, Object>();

				mapVo.put("group_id", SessionManager.getGroupId());

				mapVo.put("hos_id", SessionManager.getHosId());

				if (StringTool.isNotBlank(temp[2])) {

					supUser.setUser_code(temp[2]);
					mapVo.put("user_code", temp[2]);

				} else {

					err_sb.append("用户编码为空  ");

				}

				if (StringTool.isNotBlank(temp[3])) {

					supUser.setUser_name(temp[3]);
					mapVo.put("user_name", temp[3]);

				} else {

					err_sb.append("用户名称为空  ");

				}

				if (StringTool.isNotBlank(temp[4])) {

					supUser.setUser_pwd(temp[4]);
					mapVo.put("user_pwd", temp[4]);

				} else {

					err_sb.append("密码为空  ");

				}

				if (StringTool.isNotBlank(temp[5])) {

					supUser.setSpell_code(temp[5]);
					mapVo.put("spell_code", temp[5]);

				} else {

					err_sb.append("拼音码为空  ");

				}

				if (StringTool.isNotBlank(temp[6])) {

					supUser.setWbx_code(temp[6]);
					mapVo.put("wbx_code", temp[6]);

				} else {

					err_sb.append("五笔码为空  ");

				}

				if (StringTool.isNotBlank(temp[7])) {

					supUser.setMod_code(temp[7]);
					mapVo.put("mod_code", temp[7]);

				} else {

					err_sb.append("最后登录模块为空  ");

				}

				mapVo.put("copy_code", SessionManager.getCopyCode());

				if (StringTool.isNotBlank(temp[9])) {

					supUser.setSup_id(Long.valueOf(temp[9]));
					mapVo.put("sup_id", temp[9]);

				} else {

					err_sb.append("所属供应商为空  ");

				}

				SupUser data_exc_extis = supUserService.queryByCode(mapVo);

				if (data_exc_extis != null) {

					err_sb.append("数据已经存在！ ");

				}
				if (err_sb.toString().length() > 0) {

					supUser.setError_type(err_sb.toString());

					list_err.add(supUser);

				} else {
					mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("").toString()));
					mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("").toString()));

					String dataJson = supUserService.add(mapVo);

				}

			}

		}
		catch (DataAccessException e) {

			SupUser data_exc = new SupUser();

			data_exc.setError_type("导入系统出错");

			list_err.add(data_exc);

		}

		response.getWriter().print(ChdJson.toJson(list_err, list_err.size()));

		return null;

	}

	/**
	 * @Description 批量添加数据 供应商用户
	 * @param plupload
	 * @param request
	 * @param response
	 * @param mode
	 * @return String
	 * @throws IOException
	 */
	@RequestMapping(value = "/hrp/sup/supuser/addBatchSupUser", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addBatchSupUser(@RequestParam(value = "ParamVo") String paramVo, Model mode) throws Exception {

		List<SupUser> list_err = new ArrayList<SupUser>();

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

				SupUser supUser = new SupUser();

				JSONObject jsonObj = JSONObject.parseObject(it.next().toString());

				if (StringTool.isNotBlank(jsonObj.get("user_code"))) {

					supUser.setUser_code((String) jsonObj.get("user_code"));
					mapVo.put("user_code", jsonObj.get("user_code"));
				} else {

					err_sb.append("用户编码为空  ");

				}

				if (StringTool.isNotBlank(jsonObj.get("user_name"))) {

					supUser.setUser_name((String) jsonObj.get("user_name"));
					mapVo.put("user_name", jsonObj.get("user_name"));
				} else {

					err_sb.append("用户名称为空  ");

				}

				if (StringTool.isNotBlank(jsonObj.get("user_pwd"))) {

					supUser.setUser_pwd((String) jsonObj.get("user_pwd"));
					mapVo.put("user_pwd", jsonObj.get("user_pwd"));
				} else {

					err_sb.append("密码为空  ");

				}

				if (StringTool.isNotBlank(jsonObj.get("spell_code"))) {

					supUser.setSpell_code((String) jsonObj.get("spell_code"));
					mapVo.put("spell_code", jsonObj.get("spell_code"));
				} else {

					err_sb.append("拼音码为空  ");

				}

				if (StringTool.isNotBlank(jsonObj.get("wbx_code"))) {

					supUser.setWbx_code((String) jsonObj.get("wbx_code"));
					mapVo.put("wbx_code", jsonObj.get("wbx_code"));
				} else {

					err_sb.append("五笔码为空  ");

				}

				if (StringTool.isNotBlank(jsonObj.get("mod_code"))) {

					supUser.setMod_code((String) jsonObj.get("mod_code"));
					mapVo.put("mod_code", jsonObj.get("mod_code"));
				} else {

					err_sb.append("最后登录模块为空  ");

				}

				if (StringTool.isNotBlank(jsonObj.get("sup_id"))) {

					supUser.setSup_id(Long.valueOf((String) jsonObj.get("sup_id")));
					mapVo.put("sup_id", jsonObj.get("sup_id"));
				} else {

					err_sb.append("所属供应商为空  ");

				}

				SupUser data_exc_extis = supUserService.queryByCode(mapVo);

				if (data_exc_extis != null) {

					err_sb.append("编码已经存在！ ");

				}
				if (err_sb.toString().length() > 0) {

					supUser.setError_type(err_sb.toString());

					list_err.add(supUser);

				} else {
					mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("").toString()));
					mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("").toString()));

					String dataJson = supUserService.add(mapVo);

				}

			}

		}
		catch (DataAccessException e) {

			SupUser data_exc = new SupUser();

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
	@RequestMapping(value = "/hrp/sup/syssupuser/updateUserPassword", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateUserPassword(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
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
		if(mapVo.get("user_pwd")!=null){
			WisdomCloud wc = new WisdomCloud();
			mapVo.put("user_pwd", wc.encrypt(mapVo.get("user_pwd").toString()));
		}
		
		String generate=supUserService.updateUserPassword(mapVo);
		
		return JSONObject.parseObject(generate);
		
	}

}
