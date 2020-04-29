/**
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
package com.chd.hrp.budg.controller.base.budgcostfasset;

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
import com.chd.base.util.UploadUtil;
import com.chd.hrp.ass.entity.dict.AssDepreMethodDict;
import com.chd.hrp.ass.entity.dict.AssDict;
import com.chd.hrp.ass.entity.dict.AssTypeDict;
import com.chd.hrp.ass.entity.dict.AssUsageDict;
import com.chd.hrp.ass.service.base.AssBaseService;
import com.chd.hrp.ass.service.dict.AssDepreMethodDictService;
import com.chd.hrp.ass.service.dict.AssNoDictService;
import com.chd.hrp.ass.service.dict.AssUsageDictService;
import com.chd.hrp.budg.service.base.budgcostfasset.BudgAssDictService;
import com.chd.hrp.budg.service.base.budgcostfasset.BudgCostFassetTypeService;
import com.chd.hrp.sys.entity.FacDict;
import com.chd.hrp.sys.entity.SupDict;
import com.chd.hrp.sys.entity.Unit;
import com.chd.hrp.sys.service.UnitService;
import com.chd.hrp.sys.serviceImpl.FacDictServiceImpl;
import com.chd.hrp.sys.serviceImpl.SupDictServiceImpl;

/**
 * @Description: 050102 资产字典
 * @Table: ASS_DICT
 * @Author: bell
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Controller
public class BudgAssDictController extends BaseController {

	private static Logger logger = Logger.getLogger(BudgAssDictController.class);

	// 引入Service服务
	@Resource(name = "budgAssDictService")
	private final BudgAssDictService budgAssDictService = null;

	@Resource(name = "assNoDictService")
	private final AssNoDictService assNoDictService = null;

	@Resource(name = "assBaseService")
	private final AssBaseService assBaseService = null;
	
	@Resource(name = "budgCostFassetTypeService")
	private final BudgCostFassetTypeService budgCostFassetTypeService = null;
	
	@Resource(name = "unitService")
	private final UnitService unitService = null;
	
	@Resource(name = "assDepreMethodDictService")
	private final AssDepreMethodDictService assDepreMethodDictService = null;
	
	@Resource(name = "assUsageDictService")
	private final AssUsageDictService assUsageDictService = null;
	
	@Resource(name = "facDictService")
	private final FacDictServiceImpl facDictService = null;
	
	@Resource(name = "supDictService")
	private final SupDictServiceImpl supDictService = null;

	/**
	 * @Description 主页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/base/budgcostfasset/budgassdict/budgAssDictMainPage", method = RequestMethod.GET)
	public String budgAssDictMainPage(Model mode) throws Exception {

		return "hrp/budg/base/budgcostfasset/budgassdict/budgAssDictMain";

	}

	/**
	 * @Description 添加页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/base/budgcostfasset/budgassdict/budgAssDictAddPage", method = RequestMethod.GET)
	public String budgAssDictAddPage(Model mode) throws Exception {

		return "hrp/budg/base/budgcostfasset/budgassdict/budgAssDictAdd";

	}

	/**
	 * @Description 添加数据 050102 资产字典
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/base/budgcostfasset/budgassdict/addBudgAssDict", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addBudgAssDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

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
		if (mapVo.get("fac_id") != null && !mapVo.get("fac_id").toString().equals("")) {
			String[] fac = mapVo.get("fac_id").toString().split("@");
			mapVo.put("fac_id", fac[0]);
			mapVo.put("fac_no", fac[1]);

		}
		if (mapVo.get("ven_id") != null && !mapVo.get("ven_id").toString().equals("")) {
			String[] ven = mapVo.get("ven_id").toString().split("@");
			mapVo.put("ven_id", ven[0]);
			mapVo.put("ven_no", ven[1]);

		}

		mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("ass_name").toString()));

		mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("ass_name").toString()));
		String assDictJson = budgAssDictService.add(mapVo);
		Map<String, Object> vo = new HashMap<String, Object>();
		vo.put("group_id", mapVo.get("group_id"));
		vo.put("hos_id", mapVo.get("hos_id"));
		vo.put("copy_code", mapVo.get("copy_code"));
		vo.put("ass_code", mapVo.get("ass_code"));
		AssDict ad = budgAssDictService.queryByUniqueness(vo);
		mapVo.put("ass_no", 0);
		mapVo.put("ass_id", ad.getAss_id());
		String assNoDictJson = assNoDictService.addAssNoDict(mapVo);

		return JSONObject.parseObject(assDictJson);

	}

	/**
	 * @Description 更新跳转页面 050102 资产字典
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/base/budgcostfasset/budgassdict/budgAssDictUpdatePage", method = RequestMethod.GET)
	public String budgAssDictUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

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

		AssDict assDict = new AssDict();

		assDict = budgAssDictService.queryByCode(mapVo);

		mode.addAttribute("group_id", assDict.getGroup_id());
		mode.addAttribute("hos_id", assDict.getHos_id());
		mode.addAttribute("copy_code", assDict.getCopy_code());
		mode.addAttribute("ass_id", assDict.getAss_id());
		mode.addAttribute("ass_code", assDict.getAss_code());
		mode.addAttribute("ass_name", assDict.getAss_name());
		mode.addAttribute("ass_type_id", assDict.getAss_type_id());
		mode.addAttribute("ass_type_name", assDict.getAss_type_name());
		mode.addAttribute("acc_type_code", assDict.getAcc_type_code());
		mode.addAttribute("acc_type_name", assDict.getAcc_type_name());
		mode.addAttribute("ass_unit", assDict.getAss_unit());
		mode.addAttribute("is_measure", assDict.getIs_measure());
		mode.addAttribute("is_depre", assDict.getIs_depre());
		mode.addAttribute("ass_depre_code", assDict.getAss_depre_code());
		mode.addAttribute("ass_depre_name", assDict.getAss_depre_name());
		mode.addAttribute("depre_years", assDict.getDepre_years());
		mode.addAttribute("is_stop", assDict.getIs_stop());
		mode.addAttribute("ass_spec", assDict.getAss_spec());
		mode.addAttribute("ass_model", assDict.getAss_model());
		mode.addAttribute("fac_id", assDict.getFac_id());
		mode.addAttribute("fac_id_name", assDict.getFac_id_name());
		mode.addAttribute("fac_no", assDict.getFac_no());
		mode.addAttribute("fac_no_name", assDict.getFac_no_name());
		mode.addAttribute("ven_id", assDict.getVen_id());
		mode.addAttribute("ven_id_name", assDict.getVen_id_name());
		mode.addAttribute("ven_no", assDict.getVen_no());
		mode.addAttribute("ven_no_name", assDict.getVen_no_name());
		mode.addAttribute("usage_code", assDict.getUsage_code());
		mode.addAttribute("usage_name", assDict.getUsage_name());
		mode.addAttribute("gb_code", assDict.getGb_code());
		mode.addAttribute("spell_code", assDict.getSpell_code());
		mode.addAttribute("wbx_code", assDict.getWbx_code());

		return "hrp/budg/base/budgcostfasset/budgassdict/budgAssDictUpdate";
	}

	/**
	 * @Description 更新数据 050102 资产字典
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/base/budgcostfasset/budgassdict/updateBudgAssDict", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateBudgAssDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
 

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

		mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("ass_name").toString()));

		mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("ass_name").toString()));

		String assDictJson = budgAssDictService.update(mapVo);

		return JSONObject.parseObject(assDictJson);
	}

	/**
	 * @Description 删除数据 050102 资产字典
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/base/budgcostfasset/budgassdict/deleteBudgAssDict", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteBudgAssDict(@RequestParam(value = "ParamVo") String paramVo, Model mode) throws Exception {
		String retErrot = "";
		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();
		for (String id : paramVo.split(",")) {

			Map<String, Object> mapVo = new HashMap<String, Object>();

			String[] ids = id.split("@");
			String str = assBaseService.isExistsDataByTable("ass_type_dict", ids[3]);
            
			if(Strings.isNotBlank(str)){
				retErrot = "{\"error\":\"删除失败，选择的资产字典被以下业务使用：【" + str.substring(0, str.length() - 1) + "】。\",\"state\":\"false\"}";
				return JSONObject.parseObject(retErrot);
			}
			// 表的主键
			mapVo.put("group_id", ids[0]);
			mapVo.put("hos_id", ids[1]);
			mapVo.put("copy_code", ids[2]);
			mapVo.put("ass_id", ids[3]);
			mapVo.put("is_stop", "1");
			listVo.add(mapVo);

		}
		String assDictJson = budgAssDictService.deleteBatch(listVo);
		assNoDictService.updateBatchAssNoDict(listVo);
		// String assNoDictJson=assNoDictService.updateBatchAssNoDict(listVo);
		// String assNoDictJson = assNoDictService.deleteBatchAssNoDict(listVo);

		return JSONObject.parseObject(assDictJson);

	}

	/**
	 * @Description 查询数据 050102 资产字典
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/base/budgcostfasset/budgassdict/queryBudgAssDict", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryBudgAssDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

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
		String assDict = budgAssDictService.query(getPage(mapVo));

		return JSONObject.parseObject(assDict);

	}

	/**
	 * @Description 导入跳转页面 050102 资产字典
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/base/budgcostfasset/budgassdict/budgAssDictImportPage", method = RequestMethod.GET)
	public String assDictImportPage(Model mode) throws Exception {

		return "hrp/budg/base/budgcostfasset/budgassdict/budgAssDictImport";

	}

	/**
	 * @Description 下载导入模版 050102 资产字典
	 * @param request
	 * @param response
	 * @param mode
	 * @return String
	 * @throws IOException
	 */
	@RequestMapping(value = "hrp/budg/base/budgcostfasset/budgassdict/downTemplate", method = RequestMethod.GET)
	public String downTemplate(Plupload plupload, HttpServletRequest request, HttpServletResponse response, Model mode) throws IOException {

		printTemplate(request, response, "ass\\downTemplate", "资产字典.xls");

		return null;
	}

	/**
	 * @Description 导入数据 050102 资产字典
	 * @param plupload
	 * @param request
	 * @param response
	 * @param mode
	 * @return String
	 * @throws IOException
	 */
	@RequestMapping(value = "/hrp/budg/base/budgcostfasset/budgassdict/readBudgAssDictFiles", method = RequestMethod.POST)
	public String readBudgAssDictFiles(Plupload plupload, HttpServletRequest request, HttpServletResponse response, Model mode) throws IOException {

		List<AssDict> list_err = new ArrayList<AssDict>();

		List<String[]> list = UploadUtil.readFile(plupload, request, response);

		try {
			for (int i = 1; i < list.size(); i++) {

				StringBuffer err_sb = new StringBuffer();

				AssDict assDict = new AssDict();

				String temp[] = list.get(i);// 行
				Map<String, Object> mapVo = new HashMap<String, Object>();
				mapVo.put("group_id", SessionManager.getGroupId());
				mapVo.put("hos_id", SessionManager.getHosId());
				mapVo.put("copy_code", SessionManager.getCopyCode());

				//mapVo.put("ass_id", 0);

				if (StringTool.isNotBlank(temp[0])) {

					assDict.setAss_code(temp[0]);

					mapVo.put("ass_code", temp[0]);

				} else {

					err_sb.append("资产编码为空  ");

				}

				if (StringTool.isNotBlank(temp[1])) {

					assDict.setAss_name(temp[1]);

					mapVo.put("ass_name", temp[1]);

				} else {

					err_sb.append("资产名称为空  ");

				}

				if (StringTool.isNotBlank(temp[2])) {
					Map<String,Object> map = new HashMap<String,Object>();
					map.put("group_id", SessionManager.getGroupId());
					map.put("hos_id", SessionManager.getHosId());
					map.put("copy_code", SessionManager.getCopyCode());
					map.put("ass_type_code", temp[2]);
					AssTypeDict assType = budgCostFassetTypeService.queryByUniqueness(map);
					assDict.setAss_type_code(temp[2]);
					if(assType != null){
						mapVo.put("ass_type_id", assType.getAss_type_id());
					}else{
						err_sb.append("资产分类编码错误 ");
					}
				} else {

					err_sb.append("资产分类编码为空  ");

				}

				mapVo.put("acc_type_code", temp[3]);
				
				
				if (StringTool.isNotBlank(temp[4])) {
					Map<String,Object> map = new HashMap<String,Object>();
					map.put("group_id", SessionManager.getGroupId());
					map.put("hos_id", SessionManager.getHosId());
					map.put("unit_code", temp[4]);
					assDict.setAss_unit(temp[4]);
					Unit unitList = unitService.queryUnitByCode(map);//计量单位
					if(unitList != null){
						mapVo.put("ass_unit", unitList.getUnit_code());
					}else{
						err_sb.append("单位编码错误 ");
					}
				}else{
					mapVo.put("unit_code", "");
				}
				

				if (StringTool.isNotBlank(temp[5])) {

					assDict.setIs_measure(Integer.valueOf(getYesNo(temp[5])));
					
					mapVo.put("is_measure", getYesNo(temp[5]));

				} else {

					err_sb.append("是否计量为空  ");

				}

				if (StringTool.isNotBlank(temp[6])) {

					assDict.setIs_depre(Integer.valueOf(getYesNo(temp[5])));

					mapVo.put("is_depre", getYesNo(temp[6]));

				} else {

					err_sb.append("是否折旧为空  ");

				}

				
				if (StringTool.isNotBlank(temp[7])) {
					Map<String,Object> map = new HashMap<String,Object>();
					map.put("group_id", SessionManager.getGroupId());
					map.put("hos_id", SessionManager.getHosId());
					map.put("copy_code", SessionManager.getCopyCode());
					map.put("ass_depre_code", temp[7]);
					assDict.setAss_depre_code(temp[7]);
					AssDepreMethodDict assDepreMethodDict = assDepreMethodDictService.queryAssDepreMethodDictByCode(map);
					if(assDepreMethodDict != null){
						mapVo.put("ass_depre_code", assDepreMethodDict.getAss_depre_code());
					}else{
						err_sb.append("折旧方法编码错误 ");
					}
				}else{
					mapVo.put("ass_depre_code", "");
				}

				if (StringTool.isNotBlank(temp[8])) {
					
					assDict.setDepre_years(Integer.valueOf(temp[8]));

					mapVo.put("depre_years", temp[8]);

				} else {

					err_sb.append("折旧年限为空  ");

				}

				if (StringTool.isNotBlank(temp[9])) {

					assDict.setIs_stop(Integer.valueOf(getYesNo(temp[5])));

					mapVo.put("is_stop", getYesNo(temp[9]));

				} else {

					err_sb.append("是否停用为空  ");

				}

				mapVo.put("ass_spec", temp[10]);

				mapVo.put("ass_model", temp[11]);
				
				if (StringTool.isNotBlank(temp[12])) {
					Map<String,Object> map = new HashMap<String,Object>();
					map.put("group_id", SessionManager.getGroupId());
					map.put("hos_id", SessionManager.getHosId());
					map.put("copy_code", SessionManager.getCopyCode());
					map.put("fac_code", temp[12]);
					assDict.setFac_code(temp[12]);
					assDict.setFac_name(temp[13]);
					FacDict facDict = facDictService.queryFacDictByCode(map);
					if(facDict != null){
						mapVo.put("fac_id", facDict.getFac_id());
						mapVo.put("fac_no", facDict.getFac_no());
					}else{
						err_sb.append("生产厂商法编码错误 ");
					}
				}else{
					mapVo.put("fac_id", "");

					mapVo.put("fac_no", "");
				}

				if (StringTool.isNotBlank(temp[14])) {
					Map<String,Object> map = new HashMap<String,Object>();
					map.put("group_id", SessionManager.getGroupId());
					map.put("hos_id", SessionManager.getHosId());
					map.put("copy_code", SessionManager.getCopyCode());
					map.put("sup_code", temp[14]);
					assDict.setVen_code(temp[14]);
					assDict.setVen_name(temp[15]);
					SupDict supDict = supDictService.querySupDictByCode(map);
					if(supDict != null){
						mapVo.put("ven_id", supDict.getSup_id());
						mapVo.put("ven_no", supDict.getSup_no());
					}else{
						err_sb.append("供应商编码错误 ");
					}
				}else{
					mapVo.put("ven_id", "");

					mapVo.put("ven_no", "");
				}
				
				if (StringTool.isNotBlank(temp[16])) {
					Map<String,Object> map = new HashMap<String,Object>();
					map.put("group_id", SessionManager.getGroupId());
					map.put("hos_id", SessionManager.getHosId());
					map.put("copy_code", SessionManager.getCopyCode());
					map.put("equi_usage_code", temp[16]);
					assDict.setUsage_code(temp[16]);
					AssUsageDict assUsageDict = assUsageDictService.queryAssUsageDictByCode(map);
					if(assUsageDict != null){
						mapVo.put("usage_code", assUsageDict.getEqui_usage_code());
					}else{
						err_sb.append("资产用途编码错误 ");
					}
				}else{
					mapVo.put("usage_code", "");
				}

				mapVo.put("gb_code", temp[17]);

				AssDict data_exc_extis = budgAssDictService.queryByCode(mapVo);

				if (data_exc_extis != null) {

					err_sb.append("编码已经存在！ ");

				}
				if (err_sb.toString().length() > 0) {

					assDict.setError_type(err_sb.toString());

					list_err.add(assDict);

				} else {
					mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("ass_name").toString()));

					mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("ass_name").toString()));

					String dataJson = budgAssDictService.add(mapVo);
					Map<String, Object> vo = new HashMap<String, Object>();
					vo.put("group_id", mapVo.get("group_id"));
					vo.put("hos_id", mapVo.get("hos_id"));
					vo.put("copy_code", mapVo.get("copy_code"));
					vo.put("ass_code", mapVo.get("ass_code"));
					AssDict ad = budgAssDictService.queryByUniqueness(vo);
					//mapVo.put("ass_no", 0);
					mapVo.put("ass_id", ad.getAss_id());
					String assNoDictJson = assNoDictService.addAssNoDict(mapVo);
				}

			}

		}
		catch (DataAccessException e) {

			AssDict data_exc = new AssDict();

			data_exc.setError_type("导入系统出错");

			list_err.add(data_exc);

		}

		response.getWriter().print(ChdJson.toJson(list_err, list_err.size()));

		return null;

	}

	/**
	 * @Description 批量添加数据 050102 资产字典
	 * @param plupload
	 * @param request
	 * @param response
	 * @param mode
	 * @return String
	 * @throws IOException
	 */
	@RequestMapping(value = "/hrp/budg/base/budgcostfasset/budgassdict/addBatchAssDict", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addBatchAssDict(@RequestParam(value = "ParamVo") String paramVo, Model mode) throws Exception {

		List<AssDict> list_err = new ArrayList<AssDict>();

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

				AssDict assDict = new AssDict();

				JSONObject jsonObj = JSONObject.parseObject(it.next().toString());

				if (StringTool.isNotBlank(jsonObj.get("ass_code"))) {

					assDict.setAss_code((String) jsonObj.get("ass_code"));

					mapVo.put("ass_code", jsonObj.get("ass_code"));

				} else {

					err_sb.append("资产编码为空  ");

				}
				if (StringTool.isNotBlank(jsonObj.get("ass_name"))) {

					assDict.setAss_name((String) jsonObj.get("ass_name"));

					mapVo.put("ass_name", jsonObj.get("ass_name"));

				} else {

					err_sb.append("资产名称为空  ");

				}
				if (StringTool.isNotBlank(jsonObj.get("ass_type_code"))) {
					
					Map<String,Object> map = new HashMap<String,Object>();
					map.put("group_id", SessionManager.getGroupId());
					map.put("hos_id", SessionManager.getHosId());
					map.put("copy_code", SessionManager.getCopyCode());
					map.put("ass_type_code1", jsonObj.get("ass_type_code"));
					List<AssTypeDict> assType = budgCostFassetTypeService.queryAssTypeDictList(map);//资产分类
					assDict.setAss_type_code((String)jsonObj.get("ass_type_code"));
					if(assType.size() > 0){
						mapVo.put("ass_type_id", assType.get(0).getAss_type_id());
					}else{
						err_sb.append("资产分类编码错误 ");
					}

				} else {

					err_sb.append("类别编码为空  ");

				}
				if (StringTool.isNotBlank(jsonObj.get("acc_type_code"))) {

					assDict.setAcc_type_code((String) jsonObj.get("acc_type_code"));

					mapVo.put("acc_type_code", jsonObj.get("acc_type_code"));

				} else {

					mapVo.put("acc_type_code", "");

				}
				if (StringTool.isNotBlank(jsonObj.get("ass_unit"))) {
					Map<String,Object> map = new HashMap<String,Object>();
					map.put("group_id", SessionManager.getGroupId());
					map.put("hos_id", SessionManager.getHosId());
					map.put("unit_code", jsonObj.get("ass_unit"));
					assDict.setAss_unit((String) jsonObj.get("ass_unit"));
					Unit unitList = unitService.queryUnitByCode(map);//计量单位
					if(unitList != null){
						mapVo.put("ass_unit", unitList.getUnit_code());
					}else{
						err_sb.append("单位编码错误 ");
					}

				} else {

					mapVo.put("ass_unit", "");
				}
				if (StringTool.isNotBlank(jsonObj.get("is_measure"))) {

					assDict.setIs_measure(Integer.valueOf((String) jsonObj.get("is_measure")));
					
					mapVo.put("is_measure", getYesNo((String)jsonObj.get("is_measure")));

				} else {

					err_sb.append("是否计量为空  ");

				}
				if (StringTool.isNotBlank(jsonObj.get("is_depre"))) {

					assDict.setIs_depre(Integer.valueOf((String) jsonObj.get("is_depre")));

					mapVo.put("is_depre", getYesNo((String)jsonObj.get("is_depre")));

				} else {

					err_sb.append("是否折旧为空  ");

				}
				if (StringTool.isNotBlank(jsonObj.get("ass_depre_code"))) {
					Map<String,Object> map = new HashMap<String,Object>();
					map.put("group_id", SessionManager.getGroupId());
					map.put("hos_id", SessionManager.getHosId());
					map.put("copy_code", SessionManager.getCopyCode());
					map.put("ass_depre_code", jsonObj.get("ass_depre_code"));
					assDict.setAss_depre_code((String) jsonObj.get("ass_depre_code"));
					AssDepreMethodDict assDepreMethodDict = assDepreMethodDictService.queryAssDepreMethodDictByCode(map);
					if(assDepreMethodDict != null){
						mapVo.put("ass_depre_code", assDepreMethodDict.getAss_depre_code());
					}else{
						err_sb.append("折旧方法编码错误 ");
					}
				} else {
					mapVo.put("ass_depre_code", "");
				}
				if (StringTool.isNotBlank(jsonObj.get("depre_years"))) {

					assDict.setDepre_years(Integer.valueOf((String) jsonObj.get("depre_years")));

					mapVo.put("depre_years", jsonObj.get("depre_years"));

				} else {

					err_sb.append("折旧年限为空  ");

				}
				if (StringTool.isNotBlank(jsonObj.get("is_stop"))) {

					assDict.setIs_stop(Integer.valueOf((String) jsonObj.get("is_stop")));

					mapVo.put("is_stop", getYesNo((String)jsonObj.get("is_stop")));

				} else {

					err_sb.append("是否停用为空  ");

				}
				if (StringTool.isNotBlank(jsonObj.get("ass_spec"))) {

					assDict.setAss_spec((String) jsonObj.get("ass_spec"));

					mapVo.put("ass_spec", jsonObj.get("ass_spec"));

				} else {

					mapVo.put("ass_spec", "");

				}
				if (StringTool.isNotBlank(jsonObj.get("ass_model"))) {

					assDict.setAss_model((String) jsonObj.get("ass_model"));

					mapVo.put("ass_model", jsonObj.get("ass_model"));

				} else {

					mapVo.put("ass_model", "");

				}
				if (StringTool.isNotBlank(jsonObj.get("fac_code"))) {
					Map<String,Object> map = new HashMap<String,Object>();
					map.put("group_id", SessionManager.getGroupId());
					map.put("hos_id", SessionManager.getHosId());
					map.put("copy_code", SessionManager.getCopyCode());
					map.put("fac_code", jsonObj.get("fac_code"));
					assDict.setFac_code((String)jsonObj.get("fac_code"));
					assDict.setFac_name((String)jsonObj.get("fac_name"));
					FacDict facDict = facDictService.queryFacDictByCode(map);
					if(facDict != null){
						mapVo.put("fac_id", facDict.getFac_id());
						mapVo.put("fac_no", facDict.getFac_no());
					}else{
						err_sb.append("生产厂商法编码错误 ");
					}
				} else {
					mapVo.put("fac_id", "");
					mapVo.put("fac_no", "");
				}
				if (StringTool.isNotBlank(jsonObj.get("ven_code"))) {
					Map<String,Object> map = new HashMap<String,Object>();
					map.put("group_id", SessionManager.getGroupId());
					map.put("hos_id", SessionManager.getHosId());
					map.put("copy_code", SessionManager.getCopyCode());
					map.put("sup_code", jsonObj.get("ven_code"));
					assDict.setVen_code((String)jsonObj.get("ven_code"));
					assDict.setVen_name((String)jsonObj.get("ven_name"));
					SupDict supDict = supDictService.querySupDictByCode(map);
					if(supDict != null){
						mapVo.put("ven_id", supDict.getSup_id());
						mapVo.put("ven_no", supDict.getSup_no());
					}else{
						err_sb.append("供应商编码错误 ");
					}
				} else {
					mapVo.put("ven_id", "");
					mapVo.put("ven_no", "");
				}
				if (StringTool.isNotBlank(jsonObj.get("usage_code"))) {

					mapVo.put("usage_code", jsonObj.get("usage_code"));
					
					Map<String,Object> map = new HashMap<String,Object>();
					map.put("group_id", SessionManager.getGroupId());
					map.put("hos_id", SessionManager.getHosId());
					map.put("copy_code", SessionManager.getCopyCode());
					map.put("equi_usage_code", jsonObj.get("usage_code"));
					assDict.setUsage_code((String) jsonObj.get("usage_code"));
					AssUsageDict assUsageDict = assUsageDictService.queryAssUsageDictByCode(map);
					if(assUsageDict != null){
						mapVo.put("usage_code", assUsageDict.getEqui_usage_code());
					}else{
						err_sb.append("资产用途编码错误 ");
					}
				} else {
					mapVo.put("usage_code", "");
				}
				if (StringTool.isNotBlank(jsonObj.get("gb_code"))) {

					assDict.setGb_code((String) jsonObj.get("gb_code"));

					mapVo.put("gb_code", jsonObj.get("gb_code"));

				} else {
					mapVo.put("gb_code", "");
				}

				AssDict data_exc_extis = budgAssDictService.queryByCode(mapVo);

				if (data_exc_extis != null) {

					err_sb.append("编码已经存在！ ");
				}
				if (err_sb.toString().length() > 0) {

					assDict.setError_type(err_sb.toString());

					list_err.add(assDict);

				} else {
					mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("ass_name").toString()));

					mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("ass_name").toString()));

					String dataJson = budgAssDictService.add(mapVo);
					Map<String, Object> vo = new HashMap<String, Object>();
					vo.put("group_id", mapVo.get("group_id"));
					vo.put("hos_id", mapVo.get("hos_id"));
					vo.put("copy_code", mapVo.get("copy_code"));
					vo.put("ass_code", mapVo.get("ass_code"));
					AssDict ad = budgAssDictService.queryByUniqueness(vo);
					mapVo.put("ass_no", 0);
					mapVo.put("ass_id", ad.getAss_id());
					String assNoDictJson = assNoDictService.addAssNoDict(mapVo);
				}

			}

		}
		catch (DataAccessException e) {

			AssDict data_exc = new AssDict();

			list_err.add(data_exc);

			return JSONObject.parseObject("{\"msg\":\"导入系统出错,请重新导入.\",\"state\":\"err\"}");

		}

		if (list_err.size() > 0) {

			return JSONObject.parseObject(ChdJson.toJson(list_err, list_err.size()));

		} else {

			return JSONObject.parseObject("{\"msg\":\"导入成功.\",\"state\":\"true\"}");

		}

	}
	
	
	public String getYesNo(String str){
		if(str.equals("是")){
			return "1";
		}else if(str.equals("否")){
			return "0";
		}else if(str.equals("1")){
			return "1";
		}else{
			return "0";
		}
	}

}
