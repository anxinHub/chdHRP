package com.chd.hrp.hr.controller.sysstruc;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

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
import com.chd.hrp.hr.entity.sysstruc.HrStoreType;
import com.chd.hrp.hr.service.base.HrBaseService;
import com.chd.hrp.hr.service.sysstruc.HrStoreTypeService;

/**
 * 档案库分类构建
 * @author Administrator
 *
 */
@Controller
@RequestMapping(value = "/hrp/hr/sysstruc")
public class HrStoreTypeController extends BaseController{
	@SuppressWarnings("unused")
	private static Logger logger = Logger.getLogger(HrStoreTypeController.class);
	
	// 引入Service服务
	
	@Resource(name = "hrStoreTypeService")
	private final HrStoreTypeService hrStoreTypeService = null;
	@Resource(name = "hrBaseService")
	private final HrBaseService hrBaseService = null;
	/**
	 * 主页面跳转
	 * 
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrStoreTypeMainPage", method = RequestMethod.GET)
	public String hrStoreTypeMianPage(Model mode) throws Exception {
		return "hrp/hr/sysstruc/storetype/storeTypeMainPage";
	}

	/**
	 * 添加页面跳转
	 * 
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/addHrStoreTypePage", method = RequestMethod.GET)
	public String addHrStoreType(Model mode) throws Exception {

		return "hrp/hr/sysstruc/storetype/storeTypeAdd";

	}

	/**
	 * 新增档案库分类
	 */
	@RequestMapping(value = "/addHrStoreType", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addStoreTypePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		String deptKindJson = hrStoreTypeService.add(mapVo);
		return JSONObject.parseObject(deptKindJson);

	}
	/**
	 * 修改页面跳转
	 * 
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateHrStoreTypePage", method = RequestMethod.GET)
	public String updateHrStoreType(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		
		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		HrStoreType hrStoreType = new HrStoreType();
		
		hrStoreType=hrStoreTypeService.queryByCode(mapVo);
		
		mode.addAttribute("group_id", hrStoreType.getGroup_id());
		mode.addAttribute("hos_id", hrStoreType.getHos_id());
		mode.addAttribute("store_type_code", hrStoreType.getStore_type_code());
		mode.addAttribute("store_type_name", hrStoreType.getStore_type_name());
		mode.addAttribute("note", hrStoreType.getNote());
		
		return "hrp/hr/sysstruc/storetype/storeTypeUpdate";

	}
	/**																																																								
	 * 修改档案分类
	 */
	@RequestMapping(value = "/updateHrStoreType", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateHrStoreTypePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		String deptKindJson = hrStoreTypeService.update(mapVo);
		return JSONObject.parseObject(deptKindJson);

	}
	
	   /**
     * 删除
     * @param paramVo
     * @param mode
     * @return
     * @throws Exception
     */
	@RequestMapping(value = "/deleteHrStoreType")
	@ResponseBody
	public String deleteStoreType(@RequestParam String paramVo, Model mode)
			throws Exception {
		  String str = "";
			boolean falg = true;
		List<HrStoreType> listVo = JSONArray.parseArray(paramVo, HrStoreType.class);
		try {
			for (HrStoreType hrStoreType : listVo) {
				str = str + hrBaseService.isExistsDataByTable("HR_STORE_TYPE", hrStoreType.getStore_type_code())== null ? ""
						: hrBaseService.isExistsDataByTable("HR_STORE_TYPE", hrStoreType.getStore_type_code());
				if (Strings.isNotBlank(str)) {
					falg = false;
					continue;
				}
			}
			if (!falg) {
				return ("{\"error\":\"删除失败，选择的档案库分类被以下业务使用：【" + str.substring(0, str.length() - 1)
				+ "】。\",\"state\":\"false\"}");
			}
			return  hrStoreTypeService.deleteBatchHrStoreType(listVo);
		} catch (Exception e) {
			return "{\"error\":\""+e.getMessage()+"\"}";
		}

	

	}
	

	/**
	 * 查询所有
	 * 
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryHrStoreType")
	@ResponseBody
	public Map<String, Object> queryStoreType(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		String assInMainSpecial = hrStoreTypeService.query(getPage(mapVo));

		return JSONObject.parseObject(assInMainSpecial);
	}
	/**
	 * 导入数据
	 * @param mapVo
	 * @param mode
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/importDate", method = RequestMethod.POST)
	@ResponseBody
	public String importDate(@RequestParam Map<String, Object> mapVo, Model mode,
			HttpServletRequest request) throws Exception {
		String reJson = hrStoreTypeService.importDate(mapVo);
		return reJson;
	}
}
