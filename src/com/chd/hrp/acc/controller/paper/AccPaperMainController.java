package com.chd.hrp.acc.controller.paper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.chd.hrp.acc.service.paper.AccPaperDetailService;
import com.chd.hrp.acc.service.paper.AccPaperMainService;

@Controller
public class AccPaperMainController extends BaseController {

	private static Logger logger = Logger.getLogger(AccPaperMainController.class);

	@Resource(name = "accPaperMainService")
	private final AccPaperMainService accPaperMainService = null;

	@Resource(name = "accPaperDetailService")
	private final AccPaperDetailService accPaperDetailService = null;

	/**
	 * 票据类型<BR>
	 * 维护页面跳转
	 */
	@RequestMapping(value = "/hrp/acc/accpaper/accpapermain/accPaperMainMainPage", method = RequestMethod.GET)
	public String accPaperMainMainPage(Model mode) throws Exception {

		return "hrp/acc/accpaper/accpapermain/accPaperMainMain";

	}

	/**
	 * 票据类型<BR>
	 * 添加页面跳转
	 */
	@RequestMapping(value = "/hrp/acc/accpaper/accpapermain/accPaperMainAddPage", method = RequestMethod.GET)
	public String accPaperMainAddPage(Model mode) throws Exception {

		return "hrp/acc/accpaper/accpapermain/accPaperMainAdd";

	}

	/**
	 * <BR>
	 * 保存
	 */
	@RequestMapping(value = "/hrp/acc/accpaper/accpapermain/addAccPaperMain", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addAccPaperMain(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		try {

			String addAccPaperMainJson = accPaperMainService.addAccPaperMain(mapVo);

			return JSONObject.parseObject(addAccPaperMainJson);

		} catch (Exception e) {
			// TODO: handle exception
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + "\"}");
		}

	}

	/**
	 * 修改页面<BR>
	 *
	 */
	@RequestMapping(value = "/hrp/acc/accpaper/accpapermain/accPaperMainUpdatePage", method = RequestMethod.GET)
	public String accPaperMainUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Map<String, Object> paperMainMap = accPaperMainService.queryAccPaperMainByCode(mapVo);
		mode.addAttribute("group_id", paperMainMap.get("group_id"));
		mode.addAttribute("hos_id", paperMainMap.get("hos_id"));
		mode.addAttribute("copy_code", paperMainMap.get("copy_code"));
		mode.addAttribute("pid", paperMainMap.get("pid"));
		mode.addAttribute("type_code", paperMainMap.get("type_code"));
		mode.addAttribute("type_name", paperMainMap.get("type_name"));
		mode.addAttribute("dept_id", paperMainMap.get("dept_id"));
		mode.addAttribute("dept_no", paperMainMap.get("dept_no"));
		mode.addAttribute("dept_code", paperMainMap.get("dept_code"));
		mode.addAttribute("dept_name", paperMainMap.get("dept_name"));
		mode.addAttribute("user_id", paperMainMap.get("user_id"));
		mode.addAttribute("user_name", paperMainMap.get("user_name"));
		mode.addAttribute("opt_date", sdf.format(paperMainMap.get("opt_date")));
		mode.addAttribute("begin_num", paperMainMap.get("begin_num"));
		mode.addAttribute("end_num", paperMainMap.get("end_num"));
		mode.addAttribute("amount", paperMainMap.get("amount"));
		mode.addAttribute("amoney", paperMainMap.get("amoney"));
		mode.addAttribute("state", paperMainMap.get("state"));
		mode.addAttribute("note", paperMainMap.get("note"));

		return "hrp/acc/accpaper/accpapermain/accPaperMainUpdate";

	}

	/**
	 * <BR>
	 * 修改保存
	 */

	@RequestMapping(value = "/hrp/acc/accpaper/accpapermain/updateAccPaperMain", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateAccPaperMain(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("pid", mapVo.get("pid"));
		mapVo.put("state1", "1");

		List<Map<String, Object>> existList = accPaperDetailService.queryexistAccPaperDetail(mapVo);
		if (existList.size() > 0) {
			return JSONObject.parseObject("{\"error\":\"票据已使用，禁止修改! \"}");
		}

		String updateAccPaperMainJson = accPaperMainService.updateAccPaperMain(mapVo);

		return JSONObject.parseObject(updateAccPaperMainJson);
	}

	/**
	 * <BR>
	 * 删除
	 */
	@RequestMapping(value = "/hrp/acc/accpaper/accpapermain/deleteBatchAccPaperMain", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteBatchAccPaperMain(@RequestParam(value = "ParamVo") String paramVo, Model mode)
			throws Exception {

		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();

		boolean flag = true;

		for (String id : paramVo.split(",")) {

			String[] ids = id.split("@");

			Map<String, Object> mapVo = new HashMap<String, Object>();

			mapVo.put("group_id", ids[0]);

			mapVo.put("hos_id", ids[1]);

			mapVo.put("copy_code", ids[2]);

			mapVo.put("pid", ids[3]);

			listVo.add(mapVo);

			Map<String, Object> uMap = new HashMap<String, Object>();

			uMap.put("group_id", mapVo.get("group_id"));

			uMap.put("hos_id", mapVo.get("hos_id"));

			uMap.put("copy_code", mapVo.get("copy_code"));

			uMap.put("pid", mapVo.get("pid"));

			uMap.put("state1", "1");

			List<Map<String, Object>> existList = accPaperDetailService.queryexistAccPaperDetail(uMap);

			if (existList.size() > 0) {

				flag = false;

				break;
			}
		}

		if (flag == false) {

			return JSONObject.parseObject("{\"error\":\"删除失败,票据已使用! \"}");

		}

		String deleteBatchAccPaperMainJson = accPaperMainService.deleteBatchAccPaperMain(listVo);

		return JSONObject.parseObject(deleteBatchAccPaperMainJson);

	}

	/**
	 * <BR>
	 * 查询
	 */
	@RequestMapping(value = "/hrp/acc/accpaper/accpapermain/queryAccPaperMain", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAccPaperMain(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		String queryAccPaperMainJson = accPaperMainService.queryAccPaperMain(getPage(mapVo));

		return JSONObject.parseObject(queryAccPaperMainJson);

	}

}