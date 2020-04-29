package com.chd.hrp.hr.controller.sysstruc;

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
import com.chd.base.BaseController;
import com.chd.base.SessionManager;
import com.chd.hrp.hr.entity.sysstruc.HrColStruc;
import com.chd.hrp.hr.entity.sysstruc.HrTabStruc;
import com.chd.hrp.hr.service.sysstruc.HrColStrucService;
import com.chd.hrp.hr.service.sysstruc.HrTabStrucService;
import com.chd.hrp.sys.service.base.SysBaseService;

/**
 * 
 * @ClassName: HrTabStrucController
 * @Description: 数据表构建
 * @author zn
 * @date 2017年10月17日 下午2:46:45
 * 
 *
 */

@Controller
@RequestMapping(value = "/hrp/hr/sysstruc/hrtabstruc")
public class HrTabStrucController extends BaseController {

	@SuppressWarnings("unused")
	private static Logger logger = Logger.getLogger(HrTabStrucController.class);

	// 引入Service服务
	@Resource(name = "hrTabStrucService")
	private final HrTabStrucService hrTabStrucService = null;
	
	@Resource(name = "hrColStrucService")
	private final HrColStrucService hrColStrucService = null;
	
	@Resource(name = "sysBaseService")
	private final SysBaseService sysBaseService = null;

	/**
	 * @Description 主页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrTabStrucMainPage", method = RequestMethod.GET)
	public String hrTabStrucMainPage(Model mode) throws Exception {
		return "hrp/hr/sysstruc/hrtabstruc/hrTabStrucMain";
	}

	/**
	 * @Description 添加页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrTabStrucAddPage", method = RequestMethod.GET)
	public String hrTabStrucAddPage(Model mode) throws Exception {
		String tab_code = hrTabStrucService.queryHrTabCodeSeq();
		mode.addAttribute("tab_code",tab_code);
		return "hrp/hr/sysstruc/hrtabstruc/hrTabStrucAdd";
	}

	/**
	 * @Description 更新页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrTabStrucUpdatePage", method = RequestMethod.GET)
	public String hrTabStrucUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		HrTabStruc hrTabStruc = hrTabStrucService.queryHrTabStrucByCode(mapVo);
		
		if(hrTabStruc != null){
			mode.addAttribute("group_id", hrTabStruc.getGroup_id());
			mode.addAttribute("hos_id", hrTabStruc.getHos_id());
			//mode.addAttribute("copy_code", hrTabStruc.getCopy_code());
			mode.addAttribute("tab_code", hrTabStruc.getTab_code());
			mode.addAttribute("tab_name", hrTabStruc.getTab_name());
			mode.addAttribute("type_tab_code", hrTabStruc.getType_tab_code());
			mode.addAttribute("is_innr", hrTabStruc.getIs_innr());
			mode.addAttribute("note", hrTabStruc.getNote());
		}
		
		return "hrp/hr/sysstruc/hrtabstruc/hrTabStrucUpdate";
	}
	
	/**
	 * @Description 查询数据 系统构建表
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryHrTabStruc", method = RequestMethod.POST)
	@ResponseBody
	public String queryHrTabStrucs(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		return hrTabStrucService.queryHrTabStruc(mapVo);
	}

	/**
	 * @Description 添加数据 系统构建表
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/addHrTabStruc", method = RequestMethod.POST)
	@ResponseBody
	public String addHrTabStruc(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		try {
			String tab_code = mapVo.get("tab_code").toString();
			if(tab_code.length() > 30){
				return "{\"error\":\"数据表编码长度最大值为30个字符\"}";
			}
			return hrTabStrucService.addHrTabStruc(mapVo);
		} catch (Exception e) {
			return "{\"error\":\""+e.getMessage()+"\"}";
		}
		
	}

	/**
	 * @Description 更新数据  系统构建表
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/updateHrTabStruc", method = RequestMethod.POST)
	@ResponseBody
	public String updateHrTabStruc(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		try {
			return hrTabStrucService.updateHrTabStruc(mapVo);
		} catch (Exception e) {
			return "{\"error\":\""+e.getMessage()+"\"}";
		}
	}

	/**
	 * @Description 删除数据 系统构建表
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteHrTabStruc", method = RequestMethod.POST)
	@ResponseBody
	public String deleteHrTabStruc(@RequestParam Map<String, Object> mapVo, Model mode)throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		try {
			//判断数据表是否被引用
			//String str = sysBaseService.isExistsDataByTable("cost_charge_item_arrt", mapVo.get("tab_code"));
			//if(Strings.isNotBlank(str)){
			//	return "{\"error\":\"删除失败，选择的收费项目被以下业务使用：【" + str.substring(0, str.length() - 1) + "】。\",\"state\":\"false\"}";
			//}
			return hrTabStrucService.deleteHrTabStruc(mapVo);
		} catch (Exception e) {
			return "{\"error\":\""+e.getMessage()+"\"}";
		}
	}
	
	/**
	 * @Description 查询数据(左侧树型菜单) 系统构建表
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryHrTabStrucTree", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public String queryHrTabStrucsTree(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		return hrTabStrucService.queryHrTabStrucTree(mapVo);
	}
	
	/**
	 * @Description 查询数据 系统结构列名
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryHrColStruc", method = RequestMethod.POST)
	@ResponseBody
	public String queryHrColStruc(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		return hrColStrucService.queryHrColStruc(mapVo);
	}
	
	/**
	 * @Description 添加数据 系统结构列名
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/saveHrColStruc", method = RequestMethod.POST)
	@ResponseBody
	public String saveHrColStruc(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		try {
			return hrColStrucService.addOrUpdateHrColStruc(mapVo);
		} catch (Exception e) {
			return "{\"error\":\""+e.getMessage()+"\"}";
		}
		
	}
	
	/**
	 * @Description 删除数据 系统结构列名
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteHrColStruc", method = RequestMethod.POST)
	@ResponseBody
	public String deleteHrColStruc(@RequestParam Map<String,Object> mapVo, Model mode)throws Exception {
		
		if(mapVo == null || mapVo.get("tab_code") == null || StringUtils.isBlank(mapVo.get("tab_code").toString())){
			return "{\"warn\":\"参数不完整\"}";
		}
		
		//表信息
		String tab_code = mapVo.get("tab_code").toString();
		
		//列信息
		List<HrColStruc> listVo = JSONArray.parseArray(mapVo.get("paramVo").toString(), HrColStruc.class);
		
		try {
			return hrColStrucService.deleteBatchHrColStruc(tab_code,listVo);
		} catch (Exception e) {
			return "{\"error\":\""+e.getMessage()+"\"}";
		}
	}
	
	//导入
	@RequestMapping(value = "/importExcel", method = RequestMethod.POST)
	@ResponseBody
	public String importExcel(@RequestParam Map<String,Object> mapVo, Model mode)throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		String reJson = hrColStrucService.importExcel(mapVo);
		return reJson;
	}
}
