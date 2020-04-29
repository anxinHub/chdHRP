package com.chd.hrp.hr.controller.scientificresearch;

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
import com.chd.hrp.hr.entity.scientificresearch.HrSCIImpactFactor;
import com.chd.hrp.hr.service.base.HrBaseService;
import com.chd.hrp.hr.service.scientificresearch.HrSCIImpactFactorService;
import com.chd.hrp.hr.service.sysstruc.HrFiiedTabStrucService;

/**
 * SCI论文影响因子
 * 
 * @author Administrator
 *
 */
@Controller
@RequestMapping(value = "/hrp/hr/scientificresearch")
public class HrSCIImpactFactorController extends BaseController {

	@SuppressWarnings("unused")
	private static Logger logger = Logger.getLogger(HrSCIImpactFactorController.class);

	// 引入Service服务
	@Resource(name = "hrSCIImpactFactorService")
	private final HrSCIImpactFactorService hrSCIImpactFactorService = null;

	@Resource(name = "hrBaseService")
	private final HrBaseService hrBaseService = null;
	
	@Resource(name = "hrFiiedTabStrucService")
	private final HrFiiedTabStrucService hrFiiedTabStrucService = null;
	
	/**
	 * 主页面跳转
	 * 
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrSCIImpactFactorMainPage", method = RequestMethod.GET)
	public String stationMainPage(Model mode) throws Exception {
		return "hrp/hr/scientificresearch/sciimpactfactor/SCIImpactFactorMainPage";
	}

	/**
	 * 添加页面跳转
	 * 
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/addSCIImpactFactorPage", method = RequestMethod.GET)
	public String stationAddPage(Model mode) throws Exception {

		return "hrp/hr/scientificresearch/sciimpactfactor/SCIImpactFactorAdd";

	}

	/**
	 * @Description 添加数据
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/addSCIImpactFactor", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addSCIImpactFactor(@RequestParam Map<String, Object> mapVo, Model mode)
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

			String hosEmpKindJson = hrSCIImpactFactorService.addSCIImpactFactor(mapVo);

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
	@RequestMapping(value = "/deleteSCIImpactFactor", method = RequestMethod.POST)
	@ResponseBody

	public String deleteSCIImpactFactor(@RequestParam String paramVo, Model mode) throws Exception {
		 String str = "";
			boolean falg = true;
		List<HrSCIImpactFactor> listVo = JSONArray.parseArray(paramVo, HrSCIImpactFactor.class);
		try {
			for (HrSCIImpactFactor hrSCIImpactFactor : listVo) {
				
			}
			
			return  hrSCIImpactFactorService.deleteSCIImpactFactor(listVo);

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
	@RequestMapping(value = "/querySCIImpactFactor", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> querySCIImpactFactor(@RequestParam Map<String, Object> mapVo, Model mode)
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
		
		mapVo.put("field_tab_code", "DIC_AFFECT_PARA");
		
		String stationTransef = hrFiiedTabStrucService.queryHrFiiedData(mapVo);
		//String stationTransef = hrSCIImpactFactorService.querySCIImpactFactor(getPage(mapVo));

		return JSONObject.parseObject(stationTransef);

	}
}
