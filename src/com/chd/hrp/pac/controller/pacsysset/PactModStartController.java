package com.chd.hrp.pac.controller.pacsysset;

import java.util.Date;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.chd.base.BaseController;
import com.chd.base.SessionManager;
import com.chd.hrp.pac.serviceImpl.pacsysset.PactModStartServiceImpl;
import com.chd.hrp.sys.service.ModService;

/**
 * 系统启动
 * 
 * @author Hao Tong
 * @date 2018年8月10日 下午1:50:55
 *
 */
@Controller
@RequestMapping(value = "/hrp/pac/pacsysset")
public class PactModStartController extends BaseController {

	@Resource(name = "modService")
	private ModService modService = null;

	@Resource(name = "pactModStartService")
	private PactModStartServiceImpl modStartService = null;

	@RequestMapping(value = "/pacModMainPage", method = RequestMethod.GET)
	public String pacModMainPage() {
		return "hrp/pac/pacsysset/pacModMain";
	}

	/**
	 * 系统启用页面查询数据
	 * 
	 * @param mapVo
	 * @param mode
	 * @return
	 */
	@RequestMapping(value = "/queryMod", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMod(@RequestParam Map<String, Object> mapVo, Model mode) {
		try {
			mapVo.put("mod_code", "11%");
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());
			mapVo.put("acc_year", SessionManager.getAcctYear());
			String mod = modService.queryMod(getPage(mapVo));

			return JSONObject.parseObject(mod);
		} catch (DataAccessException e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + "\"}");
		}
	}

	/**
	 * 设置系统启用
	 * 
	 * @param mapVo
	 * @param mode
	 * @return
	 */
	@RequestMapping(value = "/addPacModStart", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addPacModStart(@RequestParam Map<String, Object> mapVo, Model mode) {
		try {
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());
			mapVo.put("create_user", SessionManager.getUserCode());
			mapVo.put("create_date", new Date());

			String modStartJson = modStartService.addModStart(mapVo);
			return JSONObject.parseObject(modStartJson);
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + "\"}");
		}
	}
}
