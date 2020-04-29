package com.chd.hrp.hr.controller.nursing;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
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
import com.chd.hrp.hr.entity.nursing.HrLevelSet;
import com.chd.hrp.hr.service.base.HrBaseService;
import com.chd.hrp.hr.service.nursing.HrLevelSetService;

/**
 * 晋阶目标设置
 * 
 * @author Administrator
 *
 */
@Controller
@RequestMapping(value = "/hrp/hr/nursing")
public class HrLevelSetController extends BaseController {

	@SuppressWarnings("unused")
	private static Logger logger = Logger.getLogger(HrLevelSetController.class);

	// 引入Service服务
	@Resource(name = "hrLevelSetService")
	private final HrLevelSetService hrLevelSetService = null;

	@Resource(name = "hrBaseService")
	private final HrBaseService hrBaseService = null;
	/**
	 * 主页面跳转
	 * 
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrLevelSetMainPage", method = RequestMethod.GET)
	public String stationMainPage(Model mode) throws Exception {
		return "hrp/hr/nursing/levelset/levelSetMainPage";
	}

	/**
	 * 添加页面跳转
	 * 
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/addLevelSetPage", method = RequestMethod.GET)
	public String stationAddPage(Model mode) throws Exception {

		return "hrp/hr/nursing/levelset/LevelSetAdd";

	}

	/**
	 * @Description 添加数据
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/addLevelSet", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addLevelSet(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}

		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		try {
			String hosEmpKindJson = hrLevelSetService.addLevelSet(mapVo);

			return JSONObject.parseObject(hosEmpKindJson);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + "\"}");
		}

	}



	/**
	 * @Description 删除数据
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteLevelSet", method = RequestMethod.POST)
	@ResponseBody

	public String deleteLevelSet(@RequestParam String paramVo, Model mode) throws Exception {
		 String str = "";
			boolean falg = true;
		List<HrLevelSet> listVo = JSONArray.parseArray(paramVo, HrLevelSet.class);
		List<HrLevelSet> entityList = new ArrayList<HrLevelSet>();
		try {
			for (HrLevelSet hrLevelSet : listVo) {
				if(StringUtils.isBlank(hrLevelSet.getYear()) || StringUtils.isBlank(hrLevelSet.getLevel_code())){
					continue;
				}
				hrLevelSet.setGroup_id(Integer.parseInt(SessionManager.getGroupId()));
				hrLevelSet.setHos_id(Integer.parseInt(SessionManager.getHosId()));
				
				entityList.add(hrLevelSet);
				
		/*		if (StringUtils.isBlank(hrCardiopulmonary.getYear()) && hrCardiopulmonary.getEmp_id() == null || StringUtils.isBlank(hrCardiopulmonary.getEmp_id().toString())) {
					listVo.remove(hrLevelSet);
				}*/
			
			}
			if(entityList.size() == 0){
				return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
			}
			return hrLevelSetService.deleteLevelSet(listVo);

		} catch (Exception e) {
			return "{\"error\":\"" + e.getMessage() + "\"}";
		}
	}

	/**
	 * @Description 查询数据
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryLevelSet", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryLevelSet(@RequestParam Map<String, Object> mapVo, Model mode)
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
		String stationTransef = hrLevelSetService.queryLevelSet(getPage(mapVo));

		return JSONObject.parseObject(stationTransef);

	}
	/**
	 * @Description 生成数据
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/generateLevelSet", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> generateLevelSet(@RequestParam Map<String, Object> mapVo, Model mode)
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
		try {
			String stationTransef = hrLevelSetService.generateLevelSet(getPage(mapVo));

			return JSONObject.parseObject(stationTransef);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + "\"}");
		}

	}
	/**
	 * @Description 继承
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/inheritLevelSet", method = RequestMethod.POST)
	@ResponseBody
	public String inheritLevelSet(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {
		try {
			if (mapVo.get("group_id") == null) {

				mapVo.put("group_id", SessionManager.getGroupId());

			}

			if (mapVo.get("hos_id") == null) {

				mapVo.put("hos_id", SessionManager.getHosId());

			}

			if (mapVo.get("copy_code") == null) {

				mapVo.put("copy_code", SessionManager.getCopyCode());

			}

			return hrLevelSetService.copyLevelSet(mapVo);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return "{\"error\":\"" + e.getMessage() + "\"}";
		}

	}
}