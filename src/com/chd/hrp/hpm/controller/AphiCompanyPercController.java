package com.chd.hrp.hpm.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
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
import com.chd.base.util.JsonListBeanUtil;
import com.chd.base.util.Plupload;
import com.chd.base.util.UploadUtil;
import com.chd.hrp.hpm.entity.AphiCompanyPerc;
import com.chd.hrp.hpm.service.AphiCompanyPercService;

/**
 * alfred
 */

@Controller
public class AphiCompanyPercController extends BaseController {

	private static Logger logger = Logger.getLogger(AphiCompanyPercController.class);

	@Resource(name = "aphiCompanyPercService")
	private final AphiCompanyPercService aphiCompanyPercService = null;

	// 维护页面跳转
	@RequestMapping(value = "/hrp/hpm/hpmcompanyperc/hpmCompanyPercMainPage", method = RequestMethod.GET)
	public String companyPercMainPage(Model mode) throws Exception {

		return "hrp/hpm/hpmcompanyperc/hpmCompanyPercMain";

	}

	@RequestMapping(value = "/hrp/hpm/hpmcompanyperc/hpmCompanyPercImportPage", method = RequestMethod.GET)
	public String companyPercImportPage(Model mode) throws Exception {

		return "hrp/hpm/hpmcompanyperc/hpmCompanyPercImport";

	}

	// 添加页面
	@RequestMapping(value = "/hrp/hpm/hpmcompanyperc/hpmCompanyPercAddPage", method = RequestMethod.GET)
	public String companyPercAddPage(Model mode) throws Exception {

		return "hrp/hpm/hpmcompanyperc/hpmCompanyPercAdd";

	}

	// 保存
	@RequestMapping(value = "/hrp/hpm/hpmcompanyperc/addHpmCompanyPerc", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addCompanyPerc(
			@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {
		
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		String companyPercJson = aphiCompanyPercService.addCompanyPerc(mapVo);

		return JSONObject.parseObject(companyPercJson);

	}

	// 查询
	@RequestMapping(value = "/hrp/hpm/hpmcompanyperc/queryHpmCompanyPerc", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryCompanyPerc(
			@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		String companyPerc = aphiCompanyPercService
				.queryCompanyPerc(getPage(mapVo));

		return JSONObject.parseObject(companyPerc);

	}

	// 删除
	@RequestMapping(value = "/hrp/hpm/hpmcompanyperc/deleteHpmCompanyPerc", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteCompanyPerc(
			@RequestParam(value = "checkIds", required = true) String checkIds,
			Model mode) throws Exception {

		Map map = new HashMap();

		String companyPercJson = aphiCompanyPercService.deleteCompanyPerc(map,
				checkIds);

		return JSONObject.parseObject(companyPercJson);
	}

	// 修改页面跳转
	@RequestMapping(value = "/hrp/hpm/hpmcompanyperc/hpmCompanyPercUpdatePage", method = RequestMethod.GET)
	public String companyPercUpdatePage(
			@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		AphiCompanyPerc companyPerc = aphiCompanyPercService
				.queryCompanyPercByCode(mapVo);

		mode.addAttribute("group_id", companyPerc.getGroup_id());
		
		mode.addAttribute("hos_id", companyPerc.getHos_id());

		mode.addAttribute("copy_code", companyPerc.getCopy_code());
		
		mode.addAttribute("group_name", companyPerc.getGroup_name());
		
		mode.addAttribute("hos_name", companyPerc.getHos_name());

		/*mode.addAttribute("copy_name", companyPerc.getCopy_name());

		mode.addAttribute("comp_name", companyPerc.getComp_name());*/

		mode.addAttribute("comp_percent", companyPerc.getComp_percent());

		return "hrp/hpm/hpmcompanyperc/hpmCompanyPercUpdate";
	}

	// 修改保存
	@RequestMapping(value = "/hrp/hpm/hpmcompanyperc/updateHpmCompanyPerc", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateCompanyPerc(
			@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {
		
		mapVo.put("copy_code", SessionManager.getCopyCode());

		String companyPercJson = aphiCompanyPercService.updateCompanyPerc(mapVo);

		return JSONObject.parseObject(companyPercJson);
	}

	// 生成
	@RequestMapping(value = "/hrp/hpm/hpmcompanyperc/createCompanyPerc", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> createCompanyPerc(
			@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		String COPY_CODE = SessionManager.getCopyCode();
		
		if(mapVo.get("group_id") == null){
			
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		
		if(mapVo.get("hos_id") == null){
			
			mapVo.put("hos_id", SessionManager.getHosId());
		}

		mapVo.put("copy_code", COPY_CODE);

		String deptBalancePercConfJson = aphiCompanyPercService
				.createCompanyPerc(mapVo);
		return JSONObject.parseObject(deptBalancePercConfJson);
	}

	/** 上传处理方法 */
	@RequestMapping(value = "/hrp/hpm/hpmcompanyperc/readCompanyPercFiles", method = RequestMethod.POST)
	public String readCompanyPercFiles(Plupload plupload,
			HttpServletRequest request, HttpServletResponse response, Model mode)
			throws IOException {

			String COPY_CODE = SessionManager.getCopyCode();
			
			Map<String, Object> mapVo = new HashMap<String, Object>();

			if(mapVo.get("group_id") == null){
				
				mapVo.put("group_id", SessionManager.getGroupId());
			}
			
			if(mapVo.get("hos_id") == null){
				
				mapVo.put("hos_id", SessionManager.getHosId());
			}

			mapVo.put("copy_code", COPY_CODE);

			List<AphiCompanyPerc> list2 = new ArrayList<AphiCompanyPerc>();

			List<String[]> list = UploadUtil.readFile(plupload, request, response);

			// List<Item> errorList = new ArrayList<Item>();

			try {

				for (int i = 1; i < list.size(); i++) {

					StringBuffer err_sb = new StringBuffer();

					AphiCompanyPerc companyPerc = new AphiCompanyPerc();

					String temp[] = list.get(i);// 行
					
					if (temp[2].length() == 1) {

						temp[2] = "0" + temp[2];

					}

					if (StringUtils.isNotEmpty(temp[0])) {

						//hpmCompanyPerc.setComp_code(temp[0]);

						mapVo.put("comp_code", temp[0]);

					} else {

						err_sb.append("单位名称为空  ");

					}
					
					if (StringUtils.isNotEmpty(temp[1])) {

						companyPerc.setCopy_code(temp[1]);

						mapVo.put("copy_code", temp[1]);

					} else {

						err_sb.append("帐套名称为空  ");

					}
					
					if (StringUtils.isNotEmpty(temp[2])) {

						companyPerc.setComp_percent(Double.parseDouble(temp[2]));

						mapVo.put("comp_percent", temp[2]);

					} else {

						err_sb.append("计提比例为空  ");

					}

					AphiCompanyPerc cp =aphiCompanyPercService.queryCompanyPercByCode(mapVo);

					if (cp != null) {

						err_sb.append("数据编码已经存在！ ");

					}

					if (err_sb.toString().length() > 0) {

						companyPerc.setError_type(err_sb.toString());

						list2.add(companyPerc);

					} else {

						aphiCompanyPercService.addCompanyPerc(mapVo);

					}

				}

			} catch (DataAccessException e) {
				AphiCompanyPerc cd = new AphiCompanyPerc();

				cd.setError_type("导入系统出错");

				list2.add(cd);

				response.getWriter().print(
						JsonListBeanUtil.listToJson(list2, list2.size()));

				return null;
			}

			mode.addAttribute("resultsJson",
					JsonListBeanUtil.listToJson(list2, list2.size()));
			return "/hrp/hpm/hpmcompanyperc/hpmCompanyPercImportMessage";
		}

		@RequestMapping(value = "/hrp/hpm/hpmcompanyperc/addBatchCompanyPerctDict", method = RequestMethod.POST)
		@ResponseBody
		public Map<String, Object> addBatchCompanyPerctDict(
				@RequestParam(value = "ParamVo") String paramVo, Model mode,
				HttpServletResponse response) throws Exception {

			List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();

			List<AphiCompanyPerc> list_err = new ArrayList<AphiCompanyPerc>();

			JSONArray json = JSONArray.parseArray(paramVo);

			Map<String, Object> mapVo = new HashMap<String, Object>();

			String s = null;

			Iterator it = json.iterator();
			try {
				while (it.hasNext()) {

					StringBuffer err_sb = new StringBuffer();

					AphiCompanyPerc companyPerc = new AphiCompanyPerc();

					JSONObject jsonObj = JSONObject.parseObject(it.next()
							.toString());

					// Set<String> key = jsonObj.keySet();

					if(mapVo.get("group_id") == null){
						
						mapVo.put("group_id", SessionManager.getGroupId());
					}
					
					if(mapVo.get("hos_id") == null){
						
						mapVo.put("hos_id", SessionManager.getHosId());
					}

					mapVo.put("copy_code", SessionManager.getCopyCode());

					mapVo.put("comp_percent", jsonObj.get("comp_percent"));

					AphiCompanyPerc cp =aphiCompanyPercService.queryCompanyPercByCode(mapVo);

					if (cp != null) {

						err_sb.append("数据编码已经存在！ ");

					}

					if (err_sb.toString().length() > 0) {

						companyPerc.setGroup_id((Long)jsonObj.get("group_id"));
						companyPerc.setHos_id((Long)jsonObj.get("hos_id"));

						companyPerc.setCopy_code((String) jsonObj.get("copy_code"));
						
						companyPerc.setComp_percent((Double) jsonObj.get("comp_percent"));

						companyPerc.setError_type(err_sb.toString());

						list_err.add(companyPerc);
					} else {

						s = aphiCompanyPercService.addCompanyPerc(mapVo);

					}
				}

			} catch (DataAccessException e) {

				return JSONObject
						.parseObject("{\"msg\":\"导入系统出错,请重新导入.\",\"state\":\"err\"}");

			}

			if (list_err.size() > 0) {

				return JSONObject.parseObject(JsonListBeanUtil.listToJson(list_err,
						list_err.size()));

			} else {

				return JSONObject
						.parseObject("{\"msg\":\"导入成功.\",\"state\":\"true\"}");

			}

		}
		
		
		
		//下载导入模板
		 @RequestMapping(value="/hrp/hpm/hpmcompanyperc/downTemplateCompanyPerc", method = RequestMethod.GET)  
			 public String downTemplateCompanyPerc(Plupload plupload,HttpServletRequest request,
			    		HttpServletResponse response,Model mode) throws IOException { 
			    printTemplate(request,response,"hpm\\方案制定\\总额预算制计将模式\\总额计提配置","结余计提比例.xls");
			    return null; 
			 }

}
