package com.chd.hrp.eqc.controller.common;

import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.chd.base.BaseController;
import com.chd.base.SessionManager;
import com.chd.hrp.eqc.service.common.AssEqcSelectService;

@Controller
public class AssEqcSelectController extends BaseController {
	
	private static Logger logger = Logger.getLogger(AssEqcSelectController.class);
	
	@Resource(name ="assEqcSelectService")
	private AssEqcSelectService assEqcSelectService = null;
	
	
	/**
	 * 部门类型下拉框
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/eqc/queryDeptType", method = RequestMethod.POST)
	@ResponseBody
	public String queryDeptType(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		String deptType = assEqcSelectService.queryDeptType(mapVo);
		return deptType;

	}
	
	/**
	 * 部门分类下拉框
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/eqc/queryDeptKind", method = RequestMethod.POST)
	@ResponseBody
	public String queryDeptKind(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		String kind = assEqcSelectService.queryDeptKind(mapVo);
		return kind;

	}
	
	/**
	 * 部门性质下拉框
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/eqc/queryDeptNature", method = RequestMethod.POST)
	@ResponseBody
	public String queryDeptNature(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		String natur = assEqcSelectService.queryDeptNature(mapVo);
		return natur;

	}
	
	/**
	 * 部门支出性质下拉框
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/eqc/queryDeptOut", method = RequestMethod.POST)
	@ResponseBody
	public String queryDeptOut(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		String out = assEqcSelectService.queryDeptOut(mapVo);
		return out;

	}
	
	
	/**
	 * 科室下拉框
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/eqc/queryDeptDict", method = RequestMethod.POST)
	@ResponseBody
	public String queryDeptDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		mapVo.put("user_id", SessionManager.getUserId());
		String nature = assEqcSelectService.queryDeptDict(mapVo);
		return nature;

	}
	
	/**
	 * 固定资产类别 下拉框
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/eqc/queryCostFassetType", method = RequestMethod.POST)
	@ResponseBody
	public String queryCostFassetType(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		String method = assEqcSelectService.queryAssType(mapVo);
		return method;

	}
	
	
	/**
	 * 资产字典  下拉框
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/eqc/queryAssDict", method = RequestMethod.POST)
	@ResponseBody
	public String queryAssDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		
		String paymentItem = assEqcSelectService.queryAssDict(mapVo);

		return paymentItem;

	}
	
	/**
	 * 资产字典无形资产  下拉框
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/eqc/queryAssDictInassets", method = RequestMethod.POST)
	@ResponseBody
	public String queryAssDictInassets(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		
		String paymentItem = assEqcSelectService.queryAssDictInassets(mapVo);

		return paymentItem;

	}
	
	
	/**
	 * 职工
	 * @param mapVo (f_code required=true)
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/eqc/queryEmpDict", method = RequestMethod.POST)
	@ResponseBody
	public String queryEmpDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		String subjType = assEqcSelectService.queryEmpDict(mapVo);
		return subjType;
	}
	
	/**
	 * 仓库  下拉框 不带NO
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/eqc/queryHosStoreDict", method = RequestMethod.POST)
	@ResponseBody
	public String queryHosStoreDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		
		String paymentItem = assEqcSelectService.queryHosStoreDict(mapVo);

		return paymentItem;

	}
	
	/**
	 * 仓库  下拉框 带NO
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/eqc/queryHosStoreDictNo", method = RequestMethod.POST)
	@ResponseBody
	public String queryHosStoreDictNo(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		
		String paymentItem = assEqcSelectService.queryHosStoreDictNo(mapVo);

		return paymentItem;

	}
	
	/**
	 * 仪器来源  下拉框 
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/eqc/queryBusiDataSource", method = RequestMethod.POST)
	@ResponseBody
	public String queryBusiDataSource(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String source = assEqcSelectService.queryBusiDataSource(mapVo);

		return source;

	}
	
	/**
	 * 资产卡片 设备下拉框   id（ass_card_no） text(ass_card_no ass_name )
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/eqc/queryAssCardDict", method = RequestMethod.POST)
	@ResponseBody
	public String queryAssCardDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		String source = assEqcSelectService.queryAssCardDict(mapVo);

		return source;

	}
	
	/**
	 * 用户下拉框
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/eqc/querySysUser", method = RequestMethod.POST)
	@ResponseBody
	public String querySysUser(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		
		String user = assEqcSelectService.querySysUser(mapVo);

		return user;

	}
	
	/**
	 * 服务项（收费类别 ） 下拉框
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/eqc/queryCostChargeKind", method = RequestMethod.POST)
	@ResponseBody
	public String queryCostChargeKind(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		String kind = assEqcSelectService.queryCostChargeKind(mapVo);

		return kind;

	}
	
	/**
	 * 计量单位 下拉框
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/eqc/queryHosUnit", method = RequestMethod.POST)
	@ResponseBody
	public String queryHosUnit(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		String unit = assEqcSelectService.queryHosUnit(mapVo);

		return unit;

	}
	
	/**
	 * 服务细项（收费项目 ） 下拉框
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/eqc/queryCostChargeItem", method = RequestMethod.POST)
	@ResponseBody
	public String queryCostChargeItem(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		String item = assEqcSelectService.queryCostChargeItem(mapVo);

		return item;

	}
	
	/**
	 * 消耗资源 下拉框
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/eqc/queryAssEqcConsumableItem", method = RequestMethod.POST)
	@ResponseBody
	public String queryAssEqcConsumableItem(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		String cons = assEqcSelectService.queryAssEqcConsumableItem(mapVo);

		return cons;

	}
	
	/**
	 * 资源类型 下拉框
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/eqc/queryAssEqcResourceType", method = RequestMethod.POST)
	@ResponseBody
	public String queryAssEqcResourceType(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		String type = assEqcSelectService.queryAssEqcResourceType(mapVo);

		return type;

	}
	
	/**
	 * 会计年度 下拉框
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/eqc/queryAssYear", method = RequestMethod.POST)
	@ResponseBody
	public String queryAssYear(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		String year = assEqcSelectService.queryAssYear(mapVo);

		return year;

	}
	
	/**
	 * 机组/设备 下拉框
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/eqc/queryAssGroupOrCardDict", method = RequestMethod.POST)
	@ResponseBody
	public String queryAssGroupOrCardDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		String grouporcard = assEqcSelectService.queryAssGroupOrCardDict(mapVo);

		return grouporcard;

	}
	
	/**
	 * 分析项 下拉框
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/eqc/queryAssAnalysisObject", method = RequestMethod.POST)
	@ResponseBody
	public String queryAssAnalysisObject(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		String analysisobject = assEqcSelectService.queryAssAnalysisObject(mapVo);

		return analysisobject;

	}
	
}
