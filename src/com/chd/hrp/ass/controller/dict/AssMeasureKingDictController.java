package com.chd.hrp.ass.controller.dict;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.nutz.lang.Strings;
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
import com.chd.hrp.ass.entity.dict.AssMeasureKingDict;
import com.chd.hrp.ass.service.base.AssBaseService;
import com.chd.hrp.ass.service.dict.AssMeasureKingDictService;
@Controller
public class AssMeasureKingDictController extends BaseController{
	private static Logger logger = Logger.getLogger(AssMeasureKingDictController.class);

	// 引入Service服务
	@Resource(name = "assMeasureKingDictService")
	private final AssMeasureKingDictService assMeasureKingDictService = null;
	@Resource(name = "assBaseService")
	private final AssBaseService assBaseService = null;
	
	
	/**
	 * @Description 主页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assmeasurekingdict/assMeasureKingDictMainPage", method = RequestMethod.GET)
	public String assMeasureKingDictMainPage(Model mode) throws Exception {

		return "hrp/ass/assmeasurekingdict/assMeasureKingDictMain";

	}

	/**
	 * @Description 添加页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assmeasurekingdict/assMeasureKingDictAddPage", method = RequestMethod.GET)
	public String assMeasureKingDictAddPage(Model mode) throws Exception {

		return "hrp/ass/assmeasurekingdict/assMeasureKingDictAdd";

	}

	/**
	 * @Description 添加数据 050107 产权形式字典
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assmeasurekingdict/addAssMeasureKingDict", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addAssMeasureKingDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		String assAcceptItemDictJson = "";

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("measure_king_name").toString()));
		mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("measure_king_name").toString()));
		try {

			String retErrot = "";

			// 根据编号获取对象
			AssMeasureKingDict assMeasureKingDict = assMeasureKingDictService.queryAssMeasureKingDictByCode(mapVo);
			if (assMeasureKingDict != null) {

				retErrot = "{\"error\":\"编码：【" + assMeasureKingDict.getMeasure_king_name() + "】重复,请修改后添加！\",\"state\":\"false\"}";
				return JSONObject.parseObject(retErrot);

			}
			// 根据名称获取对象
			AssMeasureKingDict assMeasureKingDictName = assMeasureKingDictService.queryByName(mapVo);
			if (assMeasureKingDictName != null) {

				retErrot = "{\"error\":\"名称：【" + assMeasureKingDictName.getMeasure_king_name() + "】重复,请修改后添加！\",\"state\":\"false\"}";
				return JSONObject.parseObject(retErrot);

			}
			assAcceptItemDictJson = assMeasureKingDictService.addAssMeasureKingDict(mapVo);

		} catch (Exception e) {

			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");

		}

		return JSONObject.parseObject(assAcceptItemDictJson);

	}

	/**
	 * @Description 更新跳转页面 050107 产权形式字典
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assmeasurekingdict/assMeasureKingDictUpdatePage", method = RequestMethod.GET)
	public String assAcceptItemDictUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		AssMeasureKingDict assAcceptItemDict = new AssMeasureKingDict();

		assAcceptItemDict = assMeasureKingDictService.queryAssMeasureKingDictByCode(mapVo);

		mode.addAttribute("group_id", assAcceptItemDict.getGroup_id());
		mode.addAttribute("hos_id", assAcceptItemDict.getHos_id());
		mode.addAttribute("copy_code", assAcceptItemDict.getCopy_code());
		mode.addAttribute("measure_king_code", assAcceptItemDict.getMeasure_king_code());
		mode.addAttribute("measure_king_name", assAcceptItemDict.getMeasure_king_name());
		mode.addAttribute("spell_code", assAcceptItemDict.getSpell_code());
		mode.addAttribute("wbx_code", assAcceptItemDict.getWbx_code());
		mode.addAttribute("is_stop", assAcceptItemDict.getIs_stop());

		return "hrp/ass/assmeasurekingdict/assMeasureKingDictUpdate";
	}

	/**
	 * @Description 更新数据 050107 产权形式字典
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assmeasurekingdict/updateAssMeasureKingDict", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateAssMeasureKingDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		String assAcceptItemDictJson = "";

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("measure_king_name").toString()));
		mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("measure_king_name").toString()));
		try {

			assAcceptItemDictJson = assMeasureKingDictService.updateAssMeasureKingDict(mapVo);

		} catch (Exception e) {

			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");

		}

		return JSONObject.parseObject(assAcceptItemDictJson);
	}

	/**
	 * @Description 删除数据 050107 产权形式字典
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assmeasurekingdict/deleteAssMeasureKingDict", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAssMeasureKingDict(@RequestParam(value = "ParamVo") String paramVo, Model mode)
			throws Exception {

		String assAcceptItemDictJson = "";

		String str = "";
		boolean falg = true;
		String retErrot = "";

		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();

		for (String id : paramVo.split(",")) {

			Map<String, Object> mapVo = new HashMap<String, Object>();

			String[] ids = id.split("@");
			str = str + assBaseService.isExistsDataByTable("ASS_MEASURE_KING_DICT", ids[3]) == null ? ""
					: assBaseService.isExistsDataByTable("ASS_MEASURE_KING_DICT", ids[3]);

			if (Strings.isNotBlank(str)) {
				falg = false;
				continue;
			}
			// 表的主键
			mapVo.put("group_id", ids[0]);
			mapVo.put("hos_id", ids[1]);
			mapVo.put("copy_code", ids[2]);
			mapVo.put("measure_king_code", ids[3]);

			listVo.add(mapVo);

		}
		if (!falg) {
			retErrot = "{\"error\":\"删除失败，选择的记录分类字典被以下业务使用：【" + str.substring(0, str.length() - 1)
					+ "】。\",\"state\":\"false\"}";
			return JSONObject.parseObject(retErrot);
		}
		try {

			assAcceptItemDictJson = assMeasureKingDictService.deleteBatchAssMeasureKingDict(listVo);

		} catch (Exception e) {

			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");

		}

		return JSONObject.parseObject(assAcceptItemDictJson);

	}

	/**
	 * @Description 查询数据 050107 产权形式字典
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assmeasurekingdict/queryAssMeasureKingDict", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssMeasureKingDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());
		String assAcceptItemDict = assMeasureKingDictService.queryAssMeasureKingDict(getPage(mapVo));

		return JSONObject.parseObject(assAcceptItemDict);
	}

	/**
	 * @Description 导入跳转页面 050107 产权形式字典
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assmeasurekingdict/assMeasureKingDictImport", method = RequestMethod.POST)
	@ResponseBody
	public String assMeasureKingDictImport(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		try {

			String reJson = assMeasureKingDictService.assMeasureKingDictImport(mapVo);

			return reJson;

		} catch (Exception e) {

			return "{\"error\":\"" + e.getMessage() + "\"}";

		}
	}
	
}
