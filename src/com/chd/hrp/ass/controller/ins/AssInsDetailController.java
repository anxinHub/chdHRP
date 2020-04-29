/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */

package com.chd.hrp.ass.controller.ins;

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
import com.chd.base.util.ChdJson;
import com.chd.hrp.ass.entity.ins.AssInsDetail;
import com.chd.hrp.ass.entity.ins.AssInsMain;
import com.chd.hrp.ass.service.ins.AssInsDetailService;
import com.chd.hrp.ass.service.ins.AssInsMainService;

/**
 * 
 * @Description: 050601 资产安装明细
 * @Table: ASS_INS_DETAIL
 * @Author: bell
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Controller
public class AssInsDetailController extends BaseController {

	private static Logger logger = Logger.getLogger(AssInsDetailController.class);

	// 引入Service服务
	@Resource(name = "assInsDetailService")
	private final AssInsDetailService assInsDetailService = null;
	@Resource(name = "assInsMainService")
	private final AssInsMainService assInsMainService = null;

	/**
	 * @Description 主页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assinsdetail/assInsDetailMainPage", method = RequestMethod.GET)
	public String assInsDetailMainPage(Model mode) throws Exception {

		return "hrp/ass/assinsdetail/assInsDetailMain";

	}

	/**
	 * @Description 添加页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assinsdetail/assInsDetailAddPage", method = RequestMethod.GET)
	public String assInsDetailAddPage(Model mode) throws Exception {

		return "hrp/ass/assinsdetail/assInsDetailAdd";

	}

	/**
	 * @Description 添加数据 050601 资产安装明细
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assinsdetail/addAssInsDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addAssInsDetail(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		try {
			String assInsDetailJson = assInsDetailService.addAssInsDetail(mapVo);

			return JSONObject.parseObject(assInsDetailJson);
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");
		}
	}

	/**
	 * @Description 更新跳转页面 050601 资产安装明细
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assinsdetail/assInsDetailUpdatePage", method = RequestMethod.GET)
	public String assInsDetailUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		AssInsDetail assInsDetail = new AssInsDetail();

		assInsDetail = assInsDetailService.queryAssInsDetailByCode(mapVo);

		mode.addAttribute("group_id", assInsDetail.getGroup_id());
		mode.addAttribute("hos_id", assInsDetail.getHos_id());
		mode.addAttribute("copy_code", assInsDetail.getCopy_code());
		mode.addAttribute("ins_id", assInsDetail.getIns_id());
		mode.addAttribute("ins_detail_id", assInsDetail.getIns_detail_id());
		mode.addAttribute("ins_no", assInsDetail.getIns_no());
		mode.addAttribute("ass_id", assInsDetail.getAss_id());
		mode.addAttribute("ass_no", assInsDetail.getAss_no());
		mode.addAttribute("ass_model", assInsDetail.getAss_model());
		mode.addAttribute("ass_spec", assInsDetail.getAss_spec());
		mode.addAttribute("ass_brand", assInsDetail.getAss_brand());
		mode.addAttribute("fac_id", assInsDetail.getFac_id());
		mode.addAttribute("fac_no", assInsDetail.getFac_no());
		mode.addAttribute("ins_price", assInsDetail.getIns_price());
		mode.addAttribute("ins_amount", assInsDetail.getIns_amount());
		mode.addAttribute("ins_money", assInsDetail.getIns_money());
		mode.addAttribute("ins_company", assInsDetail.getIns_company());
		mode.addAttribute("ins_tele", assInsDetail.getIns_tele());
		mode.addAttribute("ins_engr", assInsDetail.getIns_engr());
		mode.addAttribute("ins_desc", assInsDetail.getIns_desc());

		return "hrp/ass/assinsdetail/assInsDetailUpdate";
	}

	/**
	 * @Description 更新数据 050601 资产安装明细
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assinsdetail/updateAssInsDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateAssInsDetail(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());
		try {
			String assInsDetailJson = assInsDetailService.updateAssInsDetail(mapVo);

			return JSONObject.parseObject(assInsDetailJson);
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");
		}
	}


	/**
	 * @Description 删除数据 050601 资产安装明细
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assinsdetail/deleteAssInsDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAssInsDetail(@RequestParam(value = "ParamVo") String paramVo, Model mode)
			throws Exception {

		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();

		for (String id : paramVo.split(",")) {

			Map<String, Object> mapVo = new HashMap<String, Object>();

			String[] ids = id.split("@");

			// 表的主键
			mapVo.put("group_id", ids[0]);

			mapVo.put("hos_id", ids[1]);

			mapVo.put("copy_code", ids[2]);

			mapVo.put("ins_id", ids[3]);

			mapVo.put("ins_detail_id", ids[4]);

			listVo.add(mapVo);

		}
		try {
			String assInsDetailJson = assInsDetailService.deleteBatchAssInsDetail(listVo);

			return JSONObject.parseObject(assInsDetailJson);
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");
		}
	}

	/**
	 * @Description 查询数据 050601 资产安装明细
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assinsdetail/queryAssInsDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssInsDetail(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		String assInsDetail = assInsDetailService.queryAssInsDetailByUpdate(getPage(mapVo));

		return JSONObject.parseObject(assInsDetail);

	}

}
