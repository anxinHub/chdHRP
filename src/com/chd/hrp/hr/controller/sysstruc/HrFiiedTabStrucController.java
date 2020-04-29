package com.chd.hrp.hr.controller.sysstruc;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.chd.base.BaseController;
import com.chd.base.SessionManager;
import com.chd.base.exception.SysException;
import com.chd.hrp.hr.entity.sysstruc.HrFiiedTabStruc;
import com.chd.hrp.hr.entity.sysstruc.HrFiiedView;
import com.chd.hrp.hr.service.sysstruc.HrFiiedTabStrucService;
import com.chd.hrp.hr.service.sysstruc.HrFiiedViewService;

/**
 * 
 * @ClassName: HrFiiedTabStrucController
 * @Description: 代码表构建
 * @author zn
 * @date 2017年10月17日 下午2:47:13
 * 
 *
 */

@Controller
@RequestMapping(value = "/hrp/hr/sysstruc/hrfiiedtabstruc")
public class HrFiiedTabStrucController extends BaseController {

	private static Logger logger = Logger.getLogger(HrFiiedTabStrucController.class);

	// 引入Service服务
	@Resource(name = "hrFiiedTabStrucService")
	private final HrFiiedTabStrucService hrFiiedTabStrucService = null;

	@Resource(name = "hrFiiedViewService")
	private final HrFiiedViewService hrFiiedViewService = null;

	/**
	 * @Description 主页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrFiiedTabStrucMainPage", method = RequestMethod.GET)
	public String hrFiiedTabStrucMainPage(Model mode) throws Exception {
		return "hrp/hr/sysstruc/hrfiiedtabstruc/hrFiiedTabStrucMain";
	}

	/**
	 * @Description 添加页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrFiiedTabStrucAddPage", method = RequestMethod.GET)
	public String hrFiiedTabStrucAddPage(Model mode) throws Exception {
		return "hrp/hr/sysstruc/hrfiiedtabstruc/hrFiiedTabStrucAdd";
	}

	/**
	 * @Description 学科分类查询页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrFiiedTabsubjectMainPage", method = RequestMethod.GET)
	public String hrFiiedTabsubjectMainPage(@RequestParam Map<String, Object> entityMap, Model mode) throws Exception {
		entityMap.put("group_id", SessionManager.getGroupId());
		entityMap.put("hos_id", SessionManager.getHosId());
		entityMap.put("copy_code", SessionManager.getCopyCode());
		HrFiiedTabStruc hrFiiedTabStruc = hrFiiedTabStrucService.queryByCode(entityMap);
		mode.addAttribute("field_tab_code", hrFiiedTabStruc.getField_tab_code());
		mode.addAttribute("field_tab_name", hrFiiedTabStruc.getField_tab_name());
		return "hrp/hr/sysstruc/hrfiiedtabstruc/hrFiiedTabsubjectMain";
	}

	/**
	 * @Description 更新页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrFiiedTabStrucUpdatePage", method = RequestMethod.GET)
	public String hrFiiedTabStrucUpdatePage(@RequestParam Map<String, Object> entityMap, Model mode) throws Exception {
		entityMap.put("group_id", SessionManager.getGroupId());
		entityMap.put("hos_id", SessionManager.getHosId());
		entityMap.put("copy_code", SessionManager.getCopyCode());
		HrFiiedTabStruc hrFiiedTabStruc = hrFiiedTabStrucService.queryByCode(entityMap);
		mode.addAttribute("field_tab_code", hrFiiedTabStruc.getField_tab_code());
		mode.addAttribute("field_tab_name", hrFiiedTabStruc.getField_tab_name());
		mode.addAttribute("type_filed_code", hrFiiedTabStruc.getType_filed_code());
		mode.addAttribute("is_innr", hrFiiedTabStruc.getIs_innr());
		mode.addAttribute("is_cite", hrFiiedTabStruc.getIs_cite());
		mode.addAttribute("note", hrFiiedTabStruc.getNote());
		return "hrp/hr/sysstruc/hrfiiedtabstruc/hrFiiedTabStrucUpdate";
	}

	/**
	 * @Description 根据treeId查询代码项数据
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryHrFiiedData", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public String queryHrFiiedData(@RequestParam Map<String, Object> entityMap, Model mode) throws Exception {
		entityMap.put("group_id", SessionManager.getGroupId());
		entityMap.put("hos_id", SessionManager.getHosId());
		entityMap.put("copy_code", SessionManager.getCopyCode());
		String hrFiiedData = hrFiiedTabStrucService.queryHrFiiedData(entityMap);

		return hrFiiedData;
	}

	/**
	 * @Description 根据treeId查询数据表数据
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryColAndTabName", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public String queryColAndTabName(@RequestParam Map<String, Object> entityMap, Model mode) throws Exception {
		entityMap.put("group_id", SessionManager.getGroupId());
		entityMap.put("hos_id", SessionManager.getHosId());
		entityMap.put("copy_code", SessionManager.getCopyCode());
		String hrFiiedData = hrFiiedTabStrucService.queryColAndTabName(entityMap);

		return hrFiiedData;
	}

	/**
	 * @Description 查询页面tree
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryHrFiiedTabStrucTree", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public String queryHrFiiedTabStrucTree(@RequestParam Map<String, Object> entityMap, Model mode) throws Exception {
		entityMap.put("group_id", SessionManager.getGroupId());
		entityMap.put("hos_id", SessionManager.getHosId());
		entityMap.put("copy_code", SessionManager.getCopyCode());
		String hrFiiedTabStrucTree = hrFiiedTabStrucService.queryHrFiiedTabStrucTree(entityMap);

		return hrFiiedTabStrucTree;
	}

	/**
	 * @Description 添加数据
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrFiiedTabStrucAdd", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public String hrFiiedTabStrucAdd(@RequestParam Map<String, Object> entityMap, Model mode) throws Exception {
		entityMap.put("group_id", SessionManager.getGroupId());
		entityMap.put("hos_id", SessionManager.getHosId());
		entityMap.put("copy_code", SessionManager.getCopyCode());

		return hrFiiedTabStrucService.add(entityMap);
	}

	/**
	 * @Description 删除数据
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/deleteHrFiiedData", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public String deleteHrFiiedData(@RequestParam Map<String, Object> entityMap, Model mode) throws Exception {
		List<Map> listVo = JSONArray.parseArray(entityMap.get("ParamVo").toString(), Map.class);
		for (Map map : listVo) {
			map.put("group_id", SessionManager.getGroupId());
			map.put("hos_id", SessionManager.getHosId());
			map.put("copy_code", SessionManager.getCopyCode());
		}

		return hrFiiedTabStrucService.deleteHrFiiedData(listVo);
	}

	/**
	 * @Description 添加数据
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/saveHrFiiedData", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public String saveHrFiiedData(@RequestParam Map<String, Object> entityMap, Model mode) throws Exception {

		try {
			return hrFiiedTabStrucService.saveHrFiiedData(entityMap);
		} catch (Exception e) {
			return "{\"error\":\""+e.getMessage()+"\"}";
		}
	}

	/**
	 * @Description 添加数据
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrFiiedTabStrucUpdate", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public String hrFiiedTabStrucUpdate(@RequestParam Map<String, Object> entityMap, Model mode) throws Exception {
		entityMap.put("group_id", SessionManager.getGroupId());
		entityMap.put("hos_id", SessionManager.getHosId());
		entityMap.put("copy_code", SessionManager.getCopyCode());
		return hrFiiedTabStrucService.update(entityMap);
	}

	/**
	 * @Description 删除数据
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrFiiedTabStrucDelete", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public String hrFiiedTabStrucDelete(@RequestParam Map<String, Object> entityMap, Model mode) throws Exception {
		entityMap.put("group_id", SessionManager.getGroupId());
		entityMap.put("hos_id", SessionManager.getHosId());
		entityMap.put("copy_code", SessionManager.getCopyCode());
		return hrFiiedTabStrucService.delete(entityMap);
	}

	/**
	 * 设置外部引用SQL页面
	 * 
	 * @param entityMap
	 * @param mode
	 * @return
	 */
	@RequestMapping(value = "/hrFiiedViewAddPage", method = RequestMethod.GET)
	public String hrFiiedViewAddPage(@RequestParam Map<String, Object> entityMap, Model mode) {
		mode.addAllAttributes(entityMap);
		entityMap.put("group_id", SessionManager.getGroupId());
		entityMap.put("hos_id", SessionManager.getHosId());
		entityMap.put("copy_code", SessionManager.getCopyCode());
		HrFiiedView hrFiiedView = hrFiiedViewService.queryByCode(entityMap);
		if(hrFiiedView != null){
			mode.addAttribute("cite_sql", hrFiiedView.getCite_sql());
			mode.addAttribute("query_sql", hrFiiedView.getQuery_sql());
			mode.addAttribute("note", hrFiiedView.getNote());
		}
		return "hrp/hr/sysstruc/hrfiiedtabstruc/hrFiiedViewAdd";
	}

	/**
	 * 设置外部引用SQL业务
	 * 
	 * @param entityMap
	 * @param mode
	 * @return
	 */
	@RequestMapping(value = "/saveHrFiiedView", method = RequestMethod.POST)
	@ResponseBody
	public String saveHrFiiedView(@RequestParam Map<String, Object> entityMap, Model mode) {
		entityMap.put("group_id", SessionManager.getGroupId());
		entityMap.put("hos_id", SessionManager.getHosId());
		entityMap.put("copy_code", SessionManager.getCopyCode());
		try {
			return hrFiiedViewService.saveHrFiiedView(entityMap);
		} catch (Exception e) {
			logger.error(e.getMessage());
			return "{\"error\":\""+e.getMessage()+"\"}";
			
		}
	}
	/**
	 * 导入数据
	 * @param mapVo
	 * @param mode
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/importDateHFTS", method = RequestMethod.POST)
	@ResponseBody
	public String importDateHFTS(@RequestParam Map<String, Object> mapVo, Model mode,
			HttpServletRequest request) throws Exception {
		String reJson = hrFiiedTabStrucService.importDate(mapVo);
		return reJson;
	}
}
