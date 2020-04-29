/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */

package com.chd.hrp.ass.controller.maintain;

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
import com.chd.hrp.ass.entity.maintain.AssMaintainRecAss;
import com.chd.hrp.ass.service.maintain.AssMaintainRecAssService;

/**
 * 
 * @Description: 051203 保养记录资产明细
 * @Table: ASS_MAINTAIN_REC_ASS
 * @Author: bell
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Controller
public class AssMaintainRecAssController extends BaseController {  

	private static Logger logger = Logger.getLogger(AssMaintainRecAssController.class);

	// 引入Service服务
	@Resource(name = "assMaintainRecAssService")
	private final AssMaintainRecAssService assMaintainRecAssService = null;

	/**
	 * @Description 主页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assmaintainrecass/assMaintainRecAssMainPage", method = RequestMethod.GET)
	public String assMaintainRecAssMainPage(Model mode) throws Exception {

		return "hrp/ass/assmaintainrecass/assMaintainRecAssMain";

	}

	/**
	 * @Description 添加页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assmaintainrecass/assMaintainRecAssAddPage", method = RequestMethod.GET)
	public String assMaintainRecAssAddPage(Model mode) throws Exception {

		return "hrp/ass/assmaintainrecass/assMaintainRecAssAdd";

	}

	/**
	 * @Description 添加数据 051203 保养记录资产明细
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assmaintainrecass/addAssMaintainRecAss", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addAssMaintainRecAss(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());
		try {

			String assMaintainRecAssJson = assMaintainRecAssService.addAssMaintainRecAss(mapVo);

			return JSONObject.parseObject(assMaintainRecAssJson);
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");
		}
	}

	/**
	 * @Description 更新跳转页面 051203 保养记录资产明细
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assmaintainrecass/assMaintainRecAssUpdatePage", method = RequestMethod.GET)
	public String assMaintainRecAssUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		AssMaintainRecAss assMaintainRecAss = new AssMaintainRecAss();

		assMaintainRecAss = assMaintainRecAssService.queryAssMaintainRecAssByCode(mapVo);

		mode.addAttribute("group_id", assMaintainRecAss.getGroup_id());
		mode.addAttribute("hos_id", assMaintainRecAss.getHos_id());
		mode.addAttribute("copy_code", assMaintainRecAss.getCopy_code());
		mode.addAttribute("rec_id", assMaintainRecAss.getRec_id());
		mode.addAttribute("ass_card_no", assMaintainRecAss.getAss_card_no());
		mode.addAttribute("maintain_money", assMaintainRecAss.getMaintain_money());
		mode.addAttribute("maintain_unit", assMaintainRecAss.getMaintain_unit());
		mode.addAttribute("maintain_hours", assMaintainRecAss.getMaintain_hours());

		return "hrp/ass/assmaintainrecass/assMaintainRecAssUpdate";
	}

	/**
	 * @Description 更新数据 051203 保养记录资产明细
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assmaintainrecass/updateAssMaintainRecAss", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateAssMaintainRecAss(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());
		try {

			String assMaintainRecAssJson = assMaintainRecAssService.updateAssMaintainRecAss(mapVo);

			return JSONObject.parseObject(assMaintainRecAssJson);
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");
		}
	}

	/**
	 * @Description 更新数据 051203 保养记录资产明细
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assmaintainrecass/addOrUpdateAssMaintainRecAss", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addOrUpdateAssMaintainRecAss(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		String assMaintainRecAssJson = "";

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());
		try {
			List<Map> detail = ChdJson.fromJsonAsList(Map.class, mapVo.get("ParamVo").toString());

			for (Map<String, Object> detailVo : detail) {

				detailVo.put("group_id", SessionManager.getGroupId());

				detailVo.put("hos_id", SessionManager.getHosId());

				detailVo.put("copy_code", SessionManager.getCopyCode());

				assMaintainRecAssJson = assMaintainRecAssService.addOrUpdateAssMaintainRecAss(detailVo);

			}
			return JSONObject.parseObject(assMaintainRecAssJson);
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");
		}
	}

	/**
	 * @Description 删除数据 051203 保养记录资产明细
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assmaintainrecass/deleteAssMaintainRecAss", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAssMaintainRecAss(@RequestParam(value = "ParamVo") String paramVo, Model mode)
			throws Exception {

		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();

		for (String id : paramVo.split(",")) {

			Map<String, Object> mapVo = new HashMap<String, Object>();

			String[] ids = id.split("@");

			// 表的主键
			mapVo.put("group_id", ids[0]);
			mapVo.put("hos_id", ids[1]);
			mapVo.put("copy_code", ids[2]);
			mapVo.put("rec_id", ids[3]);
			mapVo.put("ass_card_no", ids[4]);

			listVo.add(mapVo);

		}
		try {
			String assMaintainRecAssJson = assMaintainRecAssService.deleteBatchAssMaintainRecAss(listVo);

			return JSONObject.parseObject(assMaintainRecAssJson);
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");
		}
	}

	/**
	 * @Description 查询数据 051203 保养记录资产明细
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assmaintainrecass/queryAssMaintainRecAss", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssMaintainRecAss(@RequestParam Map<String, Object> mapVo, Model mode)
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
		if (mapVo.get("acct_year") == null) {

			mapVo.put("acct_year", SessionManager.getAcctYear());

		}
		if (mapVo.get("rec_id") == null) {

			mapVo.put("rec_id", "0");

		}
		if(("1").equals(mapVo.get("ass_nature")) || ("2").equals(mapVo.get("ass_nature"))  || ("3").equals(mapVo.get("ass_nature")) || ("4").equals(mapVo.get("ass_nature")) || ("5").equals(mapVo.get("ass_nature")) || ("6").equals(mapVo.get("ass_nature"))){
			String ass_nature = "0"+ mapVo.get("ass_nature");
			mapVo.put("ass_nature", ass_nature);
		}
		
		String assMaintainRecAss = assMaintainRecAssService.queryAssMaintainRecAss(getPage(mapVo));

		return JSONObject.parseObject(assMaintainRecAss);

	}

	/**
	 * @Description 导入跳转页面 051203 保养记录资产明细
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assmaintainrecass/assMaintainRecAssImportPage", method = RequestMethod.GET)
	public String assMaintainRecAssImportPage(Model mode) throws Exception {

		return "hrp/ass/assmaintainrecass/assMaintainRecAssImport";

	}

	/**
	 * @Description 下载导入模版 051203 保养记录资产明细
	 * @param request
	 * @param response
	 * @param mode
	 * @return String
	 * @throws IOException
	 */
	@RequestMapping(value = "hrp/ass/assmaintainrecass/downTemplate", method = RequestMethod.GET)
	public String downTemplate(Plupload plupload, HttpServletRequest request, HttpServletResponse response, Model mode)
			throws IOException {

		printTemplate(request, response, "ass\\downTemplate", "051203 保养记录资产明细.xls");

		return null;
	}

	/**
	 * @Description 导入数据 051203 保养记录资产明细
	 * @param plupload
	 * @param request
	 * @param response
	 * @param mode
	 * @return String
	 * @throws IOException
	 */
	@RequestMapping(value = "/hrp/ass/assmaintainrecass/readAssMaintainRecAssFiles", method = RequestMethod.POST)
	public String readAssMaintainRecAssFiles(Plupload plupload, HttpServletRequest request,
			HttpServletResponse response, Model mode) throws IOException {

		List<AssMaintainRecAss> list_err = new ArrayList<AssMaintainRecAss>();

		List<String[]> list = UploadUtil.readFile(plupload, request, response);

		try {
			for (int i = 1; i < list.size(); i++) {

				StringBuffer err_sb = new StringBuffer();

				AssMaintainRecAss assMaintainRecAss = new AssMaintainRecAss();

				String temp[] = list.get(i);// 行
				Map<String, Object> mapVo = new HashMap<String, Object>();

				mapVo.put("group_id", SessionManager.getGroupId());

				mapVo.put("hos_id", SessionManager.getHosId());

				mapVo.put("copy_code", SessionManager.getCopyCode());

				if (StringTool.isNotBlank(temp[3])) {

					assMaintainRecAss.setRec_id(Long.valueOf(temp[3]));
					mapVo.put("rec_id", temp[3]);

				} else {

					err_sb.append("保养ID为空  ");

				}

				if (StringTool.isNotBlank(temp[4])) {

					assMaintainRecAss.setAss_card_no(temp[4]);
					mapVo.put("ass_card_no", temp[4]);

				} else {

					err_sb.append("资产卡片号为空  ");

				}

				if (StringTool.isNotBlank(temp[5])) {

					assMaintainRecAss.setMaintain_money(Double.valueOf(temp[5]));
					mapVo.put("maintain_money", temp[5]);

				} else {

					err_sb.append("保养费用为空  ");

				}

				if (StringTool.isNotBlank(temp[6])) {

					assMaintainRecAss.setMaintain_unit(Integer.valueOf(temp[6]));
					mapVo.put("maintain_unit", temp[6]);

				} else {

					err_sb.append("工时单位为空  ");

				}

				if (StringTool.isNotBlank(temp[7])) {

					assMaintainRecAss.setMaintain_hours(Double.valueOf(temp[7]));
					mapVo.put("maintain_hours", temp[7]);

				} else {

					err_sb.append("保养工时为空  ");

				}

				AssMaintainRecAss data_exc_extis = assMaintainRecAssService.queryAssMaintainRecAssByCode(mapVo);

				if (data_exc_extis != null) {

					err_sb.append("数据已经存在！ ");

				}
				if (err_sb.toString().length() > 0) {

					assMaintainRecAss.setError_type(err_sb.toString());

					list_err.add(assMaintainRecAss);

				} else {
					try {
						String dataJson = assMaintainRecAssService.addAssMaintainRecAss(mapVo);
					} catch (Exception e) {
						return "{\"error\":\"" + e.getMessage() + " \"}";
					}
				}

			}

		} catch (DataAccessException e) {

			AssMaintainRecAss data_exc = new AssMaintainRecAss();

			data_exc.setError_type("导入系统出错");

			list_err.add(data_exc);

		}

		response.getWriter().print(ChdJson.toJson(list_err, list_err.size()));

		return null;

	}

	/**
	 * @Description 批量添加数据 051203 保养记录资产明细
	 * @param plupload
	 * @param request
	 * @param response
	 * @param mode
	 * @return String
	 * @throws IOException
	 */
	@RequestMapping(value = "/hrp/ass/assmaintainrecass/addBatchAssMaintainRecAss", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addBatchAssMaintainRecAss(@RequestParam(value = "ParamVo") String paramVo, Model mode)
			throws Exception {

		List<AssMaintainRecAss> list_err = new ArrayList<AssMaintainRecAss>();

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

				AssMaintainRecAss assMaintainRecAss = new AssMaintainRecAss();

				JSONObject jsonObj = JSONObject.parseObject(it.next().toString());

				if (StringTool.isNotBlank(jsonObj.get("rec_id"))) {

					assMaintainRecAss.setRec_id(Long.valueOf((String) jsonObj.get("rec_id")));
					mapVo.put("rec_id", jsonObj.get("rec_id"));
				} else {

					err_sb.append("保养ID为空  ");

				}

				if (StringTool.isNotBlank(jsonObj.get("ass_card_no"))) {

					assMaintainRecAss.setAss_card_no((String) jsonObj.get("ass_card_no"));
					mapVo.put("ass_card_no", jsonObj.get("ass_card_no"));
				} else {

					err_sb.append("资产卡片号为空  ");

				}

				if (StringTool.isNotBlank(jsonObj.get("maintain_money"))) {

					assMaintainRecAss.setMaintain_money(Double.valueOf((String) jsonObj.get("maintain_money")));
					mapVo.put("maintain_money", jsonObj.get("maintain_money"));
				} else {

					err_sb.append("保养费用为空  ");

				}

				if (StringTool.isNotBlank(jsonObj.get("maintain_unit"))) {

					assMaintainRecAss.setMaintain_unit(Integer.valueOf((String) jsonObj.get("maintain_unit")));
					mapVo.put("maintain_unit", jsonObj.get("maintain_unit"));
				} else {

					err_sb.append("工时单位为空  ");

				}

				if (StringTool.isNotBlank(jsonObj.get("maintain_hours"))) {

					assMaintainRecAss.setMaintain_hours(Double.valueOf((String) jsonObj.get("maintain_hours")));
					mapVo.put("maintain_hours", jsonObj.get("maintain_hours"));
				} else {

					err_sb.append("保养工时为空  ");

				}

				AssMaintainRecAss data_exc_extis = assMaintainRecAssService.queryAssMaintainRecAssByCode(mapVo);

				if (data_exc_extis != null) {

					err_sb.append("编码已经存在！ ");

				}
				if (err_sb.toString().length() > 0) {

					assMaintainRecAss.setError_type(err_sb.toString());

					list_err.add(assMaintainRecAss);

				} else {
					try {
						String dataJson = assMaintainRecAssService.addAssMaintainRecAss(mapVo);
					} catch (Exception e) {
						return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");
					}
				}

			}

		} catch (DataAccessException e) {

			AssMaintainRecAss data_exc = new AssMaintainRecAss();

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
	 * @Description 根据资产性质来选择资产卡片号 下拉框表格
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assmaintainrec/queryMaintainPlanRec", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMaintainPlanRec(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {

			mapVo.put("group_id", SessionManager.getGroupId());

		}

		if (mapVo.get("hos_id") == null) {

			mapVo.put("hos_id", SessionManager.getHosId());

		}
		
		if (mapVo.get("copy_code") == null) {

			mapVo.put("copy_code", SessionManager.getCopyCode());

		}
		String assMaintainPlanRecJson = "";
		
		assMaintainPlanRecJson = assMaintainRecAssService.queryMaintainPlanRec(getPage(mapVo));
		
		return JSONObject.parseObject(assMaintainPlanRecJson);

	}

}
