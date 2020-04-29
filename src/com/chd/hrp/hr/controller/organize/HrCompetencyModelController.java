package com.chd.hrp.hr.controller.organize;

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

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.chd.base.BaseController;
import com.chd.base.SessionManager;
import com.chd.base.util.StringTool;
import com.chd.hrp.hr.entity.orangnize.HrCompetencyModel;
import com.chd.hrp.hr.service.base.HrBaseService;
import com.chd.hrp.hr.service.organize.HrCompetencyModelService;

/**
 * 
 * @author Administrator
 * 能力素质模型
 */
@Controller
@RequestMapping(value = "/hrp/hr/organize/competency")
public class HrCompetencyModelController extends BaseController {
	
	@SuppressWarnings("unused")
	private static Logger logger = Logger.getLogger(HrCompetencyModelController.class);
	
	// 引入Service服务
	// 引入Service服务

		@Resource(name = "hrCompetencyModelService")
		private final HrCompetencyModelService hrCompetencyModelService = null;
		@Resource(name = "hrBaseService")
		private final HrBaseService hrBaseService = null;
		/**
		 * 主页面跳转
		 * 
		 * @param mode
		 * @return
		 * @throws Exception
		 */
		@RequestMapping(value = "/competencyModelMainPage", method = RequestMethod.GET)
		public String competencyModelMainPage(Model mode) throws Exception {
			return "hrp/hr/organize/competencymodel/competencyModelMainPage";
		}

		/**
		 * 添加页面跳转
		 * 
		 * @param mode
		 * @return
		 * @throws Exception
		 */
		@RequestMapping(value = "/addCompetencyModelPage", method = RequestMethod.GET)
		public String addCompetencyModelPage(Model mode) throws Exception {

			return "hrp/hr/organize/competencymodel/addCompetencyModel";

		}
		/**
		 * 添加保存
		 * 
		 * @param mode
		 * @return
		 * @throws Exception
		 */
		@RequestMapping(value = "/addCompetencyModel", method = RequestMethod.POST)
		@ResponseBody
	public Map<String, Object> CompetencyModelAdd(@RequestParam Map<String, Object> mapVo, Model mode)
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
		mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("").toString()));
		mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("").toString()));
		try {
			String deptJson = hrCompetencyModelService.CompetencyModelAdd(mapVo);

			return JSONObject.parseObject(deptJson);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + "\"}");
		}

	}
		
		/**
		 * 修改页面跳转
		 * 
		 * @param mode
		 * @return
		 * @throws Exception
		 */
		@RequestMapping(value = "/updateCompetencyModelPage", method = RequestMethod.GET)
		public String updateCompetencyModelPage(Model mode) throws Exception {

			return "hrp/hr/organize/competencymodel/updateCompetencyModel";

		}
	/**
	 * @Description 更新数据
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateCompetencyModel", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateCompetencyModel(@RequestParam Map<String, Object> mapVo, Model mode)
		throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}

		mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("").toString()));

		mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("").toString()));
		try {
			String hosEmpKindJson = hrCompetencyModelService.updateCompetencyModel(mapVo);

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
		@RequestMapping(value = "/deleteCompetencyModel", method = RequestMethod.POST)
		@ResponseBody

		public String deleteCompetencyModel(@RequestParam String paramVo, Model mode) throws Exception {
			   String str = "";
				boolean falg = true;
			 List<HrCompetencyModel> listVo = JSONArray.parseArray(paramVo, HrCompetencyModel.class);
			try {
				/**
				 * 判断是否被引用
				 */
				for (HrCompetencyModel hrCompetencyModel : listVo) {
					/*str = str + hrBaseService.isExistsDataByTable("HR_DUTY", hrCompetencyModel.getKind_code())== null ? ""
							: hrBaseService.isExistsDataByTable("HR_DUTY", hrCompetencyModel.getKind_code());*/
					if (Strings.isNotBlank(str)) {
						falg = false;
						continue;
					}
				}
				if (!falg) {
					return ("{\"error\":\"删除失败，选择的职工休假申请被以下业务使用：【" + str.substring(0, str.length() - 1)
					+ "】。\",\"state\":\"false\"}");
				}
				//hrCompetencyModelService.deleteBatchCompetencyModel(listVo);
				return null;

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
		@RequestMapping(value = "/queryCompetencyModel", method = RequestMethod.POST)
		@ResponseBody
		public Map<String, Object> queryCompetencyModel(@RequestParam Map<String, Object> mapVo, Model mode)
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
			String CompetencyModelTransef = hrCompetencyModelService.queryHrCompetencyModel(getPage(mapVo));

			return JSONObject.parseObject(CompetencyModelTransef);

		}
		/**
		 * @Description 查询数据(左侧菜单) 能力素质模型
		 * @param mapVo
		 * @param mode
		 * @return Map<String, Object>
		 * @throws Exception
		 */
		@RequestMapping(value = "/queryCompetencyModelTree", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
		@ResponseBody
		public String queryStationDefTree(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());

			return hrCompetencyModelService.queryCompetencyModelTree(mapVo);

		}
}
