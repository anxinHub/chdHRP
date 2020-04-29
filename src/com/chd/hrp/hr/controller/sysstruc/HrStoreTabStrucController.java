package com.chd.hrp.hr.controller.sysstruc;

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

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.chd.base.BaseController;
import com.chd.base.SessionManager;
import com.chd.base.util.ChdJson;
import com.chd.hrp.hr.entity.sysstruc.HrStoreTabStruc;
import com.chd.hrp.hr.service.base.HrBaseService;
import com.chd.hrp.hr.service.sysstruc.HrStoreTabStrucService;

/**
 * 档案库数据集配置
 * 
 * @author Administrator
 *
 */
@Controller
@RequestMapping(value = "/hrp/hr/sysstruc")
public class HrStoreTabStrucController extends BaseController {
	@SuppressWarnings("unused")
	private static Logger logger = Logger.getLogger(HrStoreTabStrucController.class);

	// 引入Service服务

	@Resource(name = "hrStoreTabStrucService")
	private final HrStoreTabStrucService hrStoreTabStrucService = null;
	@Resource(name = "hrBaseService")
	private final HrBaseService hrBaseService = null;

	/**
	 * 主页面跳转
	 * 
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrStoreTabStrucMainPage", method = RequestMethod.GET)
	public String hrStoreTabStrucMianPage(Model mode) throws Exception {
		return "hrp/hr/sysstruc/storetabstruc/storeTabStrucMainPage";
	}

	/**
	 * 添加页面跳转
	 * 
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/addHrStoreTabStrucPage", method = RequestMethod.GET)
	public String addHrStoreTabStruc(Model mode) throws Exception {

		return "hrp/hr/sysstruc/storetabstruc/storeTabStrucAdd";

	}

	/**
	 * 新增档案库数据集配置
	 */
	@RequestMapping(value = "/addHrStoreTabStruc", method = RequestMethod.POST)
	@ResponseBody
	public String addStoreTabStrucPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());

		try {
			return hrStoreTabStrucService.addOrUpdateHrColStruc(mapVo);
		} catch (Exception e) {
			return "{\"error\":\"" + e.getMessage() + "\"}";
		}

	}

	/**
	 * 删除档案库数据集配置
	 * 
	 * @param paramVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteHrStoreTabStruc")
	@ResponseBody
	public String deleteStoreTabStruc(@RequestParam String paramVo, Model mode) throws Exception {
		String str = "";
		boolean falg = true;
		List<HrStoreTabStruc> listVo = JSONArray.parseArray(paramVo, HrStoreTabStruc.class);
		if (listVo != null && !listVo.isEmpty()) {
			try {
				for (HrStoreTabStruc hrStoreTabStruc : listVo) {
					str = str + hrBaseService.isExistsDataByTable("HR_STORE_TAB_STRUC",
							hrStoreTabStruc.getStore_type_code()) == null ? ""
									: hrBaseService.isExistsDataByTable("HR_STORE_TAB_STRUC",
											hrStoreTabStruc.getStore_type_code());
					if (Strings.isNotBlank(str)) {
						falg = false;
						continue;
					}
				}
				if (!falg) {
					return ("{\"error\":\"删除失败，选择的档案库数据集配置被以下业务使用：【" + str.substring(0, str.length() - 1)
							+ "】。\",\"state\":\"false\"}");
				}
				return hrStoreTabStrucService.deleteBatch(listVo);
			} catch (Exception e) {
				return "{\"error\":\"" + e.getMessage() + "\"}";
			}
		} else {
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
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
	@RequestMapping(value = "/queryHrStoreTabStruc")
	@ResponseBody
	public Map<String, Object> queryStoreTabStruc(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		String assInMainSpecial = hrStoreTabStrucService.query(getPage(mapVo));

		return JSONObject.parseObject(assInMainSpecial);
	}

	/**
	 * 数据构建 数据表分类下拉框
	 * 
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryHrHosTabStruc", method = RequestMethod.POST)
	@ResponseBody
	public String queryHrHosTabStruc(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		String json = hrStoreTabStrucService.queryHrHosTabStruc(mapVo);
		return json;

	}

	/**
	 * 根据档案编码查询
	 * 
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryStoreTabStrucByCode")
	@ResponseBody
	public Map<String, Object> queryStoreTabStrucByCode(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		String assInMainSpecial = hrStoreTabStrucService.queryStoreTabStrucByCode(getPage(mapVo));

		return JSONObject.parseObject(assInMainSpecial);
	}

	/**
	 * @Description 查询数据(左侧树型菜单) 档案库构建表
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryStoreTabStrucTree", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public String queryStoreTabStrucTree(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());

		return hrStoreTabStrucService.queryStoreTabStrucTree(mapVo);

	}

	/**
	 * @Description 查询数据架构
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryStoreTabStrucByTree", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryStoreTabStrucByTree(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());
		List<?> l_map = hrStoreTabStrucService.queryStoreTabStrucByTree(mapVo);
		return JSONObject.parseObject(ChdJson.toJson(l_map));
	}
	
	//导入
	@RequestMapping(value = "/importExcel", method = RequestMethod.POST)
	@ResponseBody
	public String importExcel(@RequestParam Map<String,Object> mapVo, Model mode)throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
			
		String reJson = hrStoreTabStrucService.importExcel(mapVo);
		return reJson;
	}
	
	//批量添加查询
	@RequestMapping(value = "/storeTabBatchAddPage", method = RequestMethod.GET)
	public String storeTabBatchAddPage(@RequestParam Map<String,Object> mapVo, Model mode)throws Exception {
		return "hrp/hr/sysstruc/storetabstruc/storeTabBatchAdd";
	}
	
	//批量添加
	@RequestMapping(value = "/addStoreTabBatch", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addStoreTabBatch(@RequestParam Map<String,Object> mapVo, Model mode)throws Exception {
		try {
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());
				
			String reJson = hrStoreTabStrucService.addStoreTabBatch(mapVo);
			return JSON.parseObject(reJson);
		} catch (Exception e) {
			return JSON.parseObject("{\"error\":\"添加失败\":\""+e.getMessage()+"\"}");
		}
	}
}
