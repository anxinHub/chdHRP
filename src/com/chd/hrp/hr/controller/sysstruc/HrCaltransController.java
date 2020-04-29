package com.chd.hrp.hr.controller.sysstruc;

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

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.chd.base.BaseController;
import com.chd.base.SessionManager;
import com.chd.hrp.hr.entity.sysstruc.HrCaltrans;
import com.chd.hrp.hr.service.sysstruc.HrCaltransService;

/**
 * 
 * @ClassName: HrCaltransController
 * @Description: 计算事物
 * @author zn
 * @date 2017年10月17日 下午2:47:32
 * 
 *
 */

@Controller
@RequestMapping(value = "/hrp/hr/sysstruc/hrcaltrans")
public class HrCaltransController extends BaseController {

	@SuppressWarnings("unused")
	private static Logger logger = Logger.getLogger(HrCaltransController.class);

	// 引入Service服务
	@Resource(name = "hrCaltransService")
	private final HrCaltransService hrCaltransService = null;

	/**
	 * @Description 主页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrCaltransMainPage", method = RequestMethod.GET)
	public String hrCaltransMainPage(Model mode) throws Exception {
		return "hrp/hr/sysstruc/hrcaltrans/hrCaltransMain";
	}

	
	@RequestMapping(value = "/hrCaltransSetCron", method = RequestMethod.GET)
	public String hrCaltransSetCron(Model mode) throws Exception {
		return "hrp/hr/sysstruc/hrcaltrans/hrCaltransSetCron";
	}
	/**
	 * @Description 添加页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrCaltransAddPage", method = RequestMethod.GET)
	public String hrCaltransAddPage(Model mode) throws Exception {
		return "hrp/hr/sysstruc/hrcaltrans/hrCaltransAdd";
	}

	/**
	 * @Description 更新页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrCaltransUpdatePage", method = RequestMethod.GET)
	public String hrCaltransUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		HrCaltrans hrCaltrans = hrCaltransService.queryHrCaltransByCode(mapVo);
		mode.addAttribute("group_id", hrCaltrans.getGroup_id());
		mode.addAttribute("hos_id", hrCaltrans.getHos_id());
		//mode.addAttribute("copy_code", hrCaltrans.getCopy_code());
		mode.addAttribute("tran_id", hrCaltrans.getTran_id());
		mode.addAttribute("main_tab_code", hrCaltrans.getMain_tab_code());
		mode.addAttribute("main_col_code", hrCaltrans.getMain_col_code());
		mode.addAttribute("tran_freq", hrCaltrans.getTran_freq());
		mode.addAttribute("exec_time", hrCaltrans.getExec_time());
		mode.addAttribute("exec_func", hrCaltrans.getExec_func());
		mode.addAttribute("tran_state", hrCaltrans.getTran_state());
		mode.addAttribute("is_system", hrCaltrans.getIs_system());
		mode.addAttribute("mod", hrCaltrans.getMod_code());
		mode.addAttribute("func_type", hrCaltrans.getFunc_type());
		mode.addAttribute("note", hrCaltrans.getNote());
		return "hrp/hr/sysstruc/hrcaltrans/hrCaltransUpdate";
	}

	/**
	 * @Description 查询数据
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryHrCaltrans", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryHrCaltranss(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());

		  String hrFun =hrCaltransService.queryHrCaltrans(getPage(mapVo));
		  return JSONObject.parseObject(hrFun);
	}

	/**
	 * @Description 添加数据
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/addHrCaltrans", method = RequestMethod.POST)
	@ResponseBody
	public String addHrCaltrans(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		try {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("tran_state", "0");
			return hrCaltransService.addHrCaltrans(mapVo);
		} catch (Exception e) {
			return "{\"error\":\"" + e.getMessage() + "\"}";
		}

	}

	/**
	 * @Description 更新数据
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateHrCaltrans", method = RequestMethod.POST)
	@ResponseBody
	public String updateHrCaltrans(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("tran_state", "0");
		try {
			return hrCaltransService.updateHrCaltrans(mapVo);
		} catch (Exception e) {
			return "{\"error\":\"" + e.getMessage() + "\"}";
		}
	}

	/**
	 * @Description 删除数据
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteHrCaltrans", method = RequestMethod.POST)
	@ResponseBody
	public String deleteHrCaltrans(@RequestParam String ParamVo, Model mode) throws Exception {

		try {
			List<HrCaltrans> listVo = JSONArray.parseArray(ParamVo, HrCaltrans.class);
			return hrCaltransService.deleteBatchHrCaltrans(listVo);
		} catch (Exception e) {
			return "{\"error\":\"" + e.getMessage() + "\"}";
		}
	}

	/**
	 * @Description 删除数据
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/startFuncHrCaltrans", method = RequestMethod.POST)
	@ResponseBody
	public String startFuncHrCaltrans(@RequestParam String ParamVo, Model mode) throws Exception {

		try {
			List<Map> listVo = JSONArray.parseArray(ParamVo, Map.class);
			return hrCaltransService.startFuncHrCaltrans(listVo);
		} catch (Exception e) {
			return "{\"error\":\"" + e.getMessage() + "\"}";
		}
	}

	/**
	 * @Description 删除数据
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/stopFuncHrCaltrans", method = RequestMethod.POST)
	@ResponseBody
	public String stopFuncHrCaltrans(@RequestParam String ParamVo, Model mode) throws Exception {

		try {
			List<Map> listVo = JSONArray.parseArray(ParamVo, Map.class);
			return hrCaltransService.stopFuncHrCaltrans(listVo);
		} catch (Exception e) {
			return "{\"error\":\"" + e.getMessage() + "\"}";
		}
	}

	// 函数分类树
	@RequestMapping(value = "/queryCaltransFunTypeTree", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public String queryCaltransFunTypeTree(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		if (mapVo.get("func_type_select")!=null) {
			mapVo.put("type_code", mapVo.get("func_type_select"));
		}
		
		String json = hrCaltransService.queryCaltransFunTypeTree(mapVo);
		return json;

	}

	/**
	 * @Description 查询函数
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryHrFun", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object>queryHrFun(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());

	String  hrFun= hrCaltransService.queryHrFun(getPage(mapVo));
	return JSONObject.parseObject(hrFun);
	}

	/**
	 * @Description 查询动态表单
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryPrmFunByCode", method = RequestMethod.POST)
	@ResponseBody
	public String queryPrmFunByCode(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());

		return hrCaltransService.queryPrmFunByCode(mapVo);
	}
	// 应用模式字典
		@RequestMapping(value = "/queryHrFunParaByDict", method = RequestMethod.POST)
		@ResponseBody
		public String queryHrFunParaByDict(
				@RequestParam Map<String, Object> mapVo, Model mode)
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
			return hrCaltransService.queryHrFunParaByDict(mapVo);

		}

}
