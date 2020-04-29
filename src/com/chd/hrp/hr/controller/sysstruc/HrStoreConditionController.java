package com.chd.hrp.hr.controller.sysstruc;

import java.util.ArrayList;
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
import com.chd.hrp.hr.entity.sysstruc.HrStoreCondition;
import com.chd.hrp.hr.service.base.HrBaseService;
import com.chd.hrp.hr.service.sysstruc.HrStoreConditionService;

/**
 * 档案库人员限定配置
 * @author Administrator
 *
 */
@Controller
@RequestMapping(value = "/hrp/hr/sysstruc")
public class HrStoreConditionController extends BaseController{
	
	
	@SuppressWarnings("unused")
	private static Logger logger = Logger.getLogger(HrStoreConditionController.class);
	
	// 引入Service服务
	
	@Resource(name = "hrStoreConditionService")
	private final HrStoreConditionService hrStoreConditionService = null;
	@Resource(name = "hrBaseService")
	private final HrBaseService hrBaseService = null;
	/**
	 * 主页面跳转
	 * 
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrStoreConditionMainPage", method = RequestMethod.GET)
	public String hrStoreConditionMianPage(Model mode) throws Exception {
		return "hrp/hr/sysstruc/storecondition/storeConditionMainPage";
	}

	/**
	 * 添加页面跳转
	 * 
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/addHrStoreConditionPage", method = RequestMethod.GET)
	public String addHrStoreCondition(Model mode) throws Exception {

		return "hrp/hr/sysstruc/storecondition/storeConditionAdd";

	}

	/**
	 * 新增档案库人员限定配置
	 */
	@RequestMapping(value = "/addHrStoreCondition", method = RequestMethod.POST)
	@ResponseBody
	public String addHrStoreCondition(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		String deptKindJson;
		try {
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());

			deptKindJson = hrStoreConditionService.addStoreConditionPage(mapVo);
			return deptKindJson;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return "{\"error\":\"" + e.getMessage() + "\"}";
		}
	}
	
	
	   /**
     * 删除档案库人员限定配置
     * @param paramVo
     * @param mode
     * @return
     * @throws Exception
     */
	@RequestMapping(value = "/deleteHrStoreCondition", method = RequestMethod.POST)
	@ResponseBody
	public String deleteStoreCondition(@RequestParam String paramVo, Model mode) throws Exception {
		
		String str = "";
		boolean falg = true;
		List<HrStoreCondition> listVo = JSONArray.parseArray(paramVo, HrStoreCondition.class);

		List<HrStoreCondition> entityList = new ArrayList<HrStoreCondition>();//重新组装List,用于组装正确的删除数据,避免误删除操作
		
		try {
			for (HrStoreCondition hrStoreCondition : listVo) {
				/*	str = str + hrBaseService.isExistsDataByTable("HR_STORE_CONDITION", hrStoreCondition.getCol_code())== null ? ""
						: hrBaseService.isExistsDataByTable("HR_STORE_CONDITION", hrStoreCondition.getCol_code());*/
				if (Strings.isNotBlank(str)) {
					falg = false;
					continue;
				}
				
				
				//判断主键是否为空,避免误删数据
				if(hrStoreCondition.getStore_type_code() == null || hrStoreCondition.getLine_no() ==null){
					continue;
				}
				if("".equals(hrStoreCondition.getStore_type_code())|| "".equals(String.valueOf(hrStoreCondition.getLine_no()))){
					continue;
				}
				
				entityList.add(hrStoreCondition);
			
			}
			if (!falg) {
				return ("{\"error\":\"删除失败，选择的档案库人员限定配置被以下业务使用：【" + str.substring(0, str.length() - 1)
				+ "】。\",\"state\":\"false\"}");
			}
			
			if(entityList.size() == 0){
				return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
			}
			
			return hrStoreConditionService.deleteBatch(entityList);
			
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
	@RequestMapping(value = "/queryStoreCondition", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryStoreCondition(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		String assInMainSpecial = hrStoreConditionService.queryStoreCondition(getPage(mapVo));

		return JSONObject.parseObject(assInMainSpecial);
	}
	
	/**
	 * @Description 查询数据(左侧树型菜单) 档案库构建表
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryStoreConditionTree", method = RequestMethod.POST,produces = "application/json;charset=UTF-8")
	@ResponseBody
	public String queryStoreConditionTree(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		return hrStoreConditionService.queryStoreConditionTree(mapVo);
	
	}
	
	/**
	 * 数据构建 数据表分类下拉框
	 * 
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryHrHosConditionTabStruc", method = RequestMethod.POST)
	@ResponseBody
	public String queryHrHosConditionTabStruc(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		String json = hrStoreConditionService.queryHrHosConditionTabStruc(mapVo);
		return json;

	}
	/**
	 * 数据构建 系统结构列名查询
	 * 
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryHrHosColStruc", method = RequestMethod.POST)
	@ResponseBody
	public String queryHrHosColStruc(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		String json = hrStoreConditionService.queryHrHosColStruc(mapVo);
		return json;

	}
	/**
	 * 数据构建 条件符号查询
	 * 
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryHrHosConSign", method = RequestMethod.POST)
	@ResponseBody
	public String queryHrHosConSign(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		String json = hrStoreConditionService.queryHrHosConSign(mapVo);
		return json;

	}
	
	/**
	 * 数据构建 连接符号查询
	 * 
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryHrHosJoinSign", method = RequestMethod.POST)
	@ResponseBody
	public String queryHrHosJoinSign(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		String json = hrStoreConditionService.queryHrHosJoinSign(mapVo);
		return json;

	}
	
	/**
	 * 导入
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/importExcelCondition", method = RequestMethod.POST)
	@ResponseBody
	public String importExcelCondition(@RequestParam Map<String,Object> mapVo, Model mode)throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
			
		String reJson = hrStoreConditionService.importExcelCondition(mapVo);
		return reJson;
	}
	
	/**
	 * 数据构建 系统结构列 下拉框
	 * 
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryHrTabColStruc", method = RequestMethod.POST)
	@ResponseBody
	public String queryHrColStrucSelect(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		if(mapVo.get("tab_code").toString()==null || mapVo.get("tab_code").toString().equals("")){
			return "{\"error\":\"数据表名称不能为空\"}";
		}
		String json = hrStoreConditionService.queryHrTabColStruc(mapVo);
		return json;

	}

}
