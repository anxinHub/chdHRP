package com.chd.hrp.hr.controller.scientificresearch;


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
import com.chd.hrp.hr.service.base.HrBaseService;
import com.chd.hrp.hr.service.scientificresearch.HrAcademicPapersCategoryService;
import com.chd.hrp.hr.service.sysstruc.HrFiiedTabStrucService;

/**
 * 学术论文类别
 * 
 * @author Administrator
 *
 */
@Controller
@RequestMapping(value = "/hrp/hr/scientificresearch")
public class HrAcademicPapersCategoryController extends BaseController {

	@SuppressWarnings("unused")
	private static Logger logger = Logger.getLogger(HrAcademicPapersCategoryController.class);

	// 引入Service服务
	@Resource(name = "hrAcademicPapersCategoryService")
	private final HrAcademicPapersCategoryService hrAcademicPapersCategoryService = null;

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
	@RequestMapping(value = "/hrAcademicPapersCategoryMainPage", method = RequestMethod.GET)
	public String stationMainPage(Model mode) throws Exception {
		return "hrp/hr/scientificresearch/academicpaperscategory/academicPapersCategoryMainPage";
	}

	

	/**
	 * @Description 查询数据
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryAcademicPapersCategory", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAcademicPapersCategory(@RequestParam Map<String, Object> mapVo, Model mode)
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
		
		mapVo.put("field_tab_code", "DIC_PAPER_TYPE");
		
		String stationTransef = hrFiiedTabStrucService.queryHrFiiedData(mapVo);
		//String stationTransef = hrAcademicPapersCategoryService.queryAcademicPapersCategory(getPage(mapVo));

		return JSONObject.parseObject(stationTransef);

	}
}
