/**
 * @Copyright: Copyright (c) 2015-2-14
 * @Company: 智慧云康（北京）数据科技有限公司
 */
package com.chd.hrp.hip.controller;

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
import com.chd.hrp.hip.service.HrpHipSelectService;

/**
 * @Title. @Description.
 * 
 * @Author: LiuYingDuo
 * @email: bell@s-chd.com
 * @Version: 1.0
 */
@Controller
public class HrpHipSelectController extends BaseController {

	private static Logger logger = Logger.getLogger(HrpHipSelectController.class);

	@Resource(name = "hrpHipSelectService")
	private final HrpHipSelectService hrpHipSelectService = null;


	@RequestMapping(value = "/hrp/hip/queryHosInfoDict", method = RequestMethod.POST)
	@ResponseBody
	public String queryHosInfoDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		if (mapVo.get("group_id") == null) {mapVo.put("group_id", SessionManager.getGroupId());}
		
		String str = hrpHipSelectService.queryHosInfoDict(mapVo);
		
		return str;
	}
	
	@RequestMapping(value = "/hrp/hip/queryHosCopy", method = RequestMethod.POST)
	@ResponseBody
	public String queryHosCopy(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		if (mapVo.get("group_id") == null) {mapVo.put("group_id", SessionManager.getGroupId());}
		
		if (mapVo.get("hos_id") == null) {mapVo.put("hos_id", SessionManager.getHosId());}
		
		String str = hrpHipSelectService.queryHosCopy(mapVo);
		
		return str;
	}
	
	//业务系统
	@RequestMapping(value = "/hrp/hip/querySysMod", method = RequestMethod.POST)
	@ResponseBody
	public String querySysMod(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		if (mapVo.get("group_id") == null) {mapVo.put("group_id", SessionManager.getGroupId());}
		
		if (mapVo.get("hos_id") == null) {mapVo.put("hos_id", SessionManager.getHosId());}
		
		if (mapVo.get("copy_code") == null) {mapVo.put("copy_code", SessionManager.getCopyCode());}
		
		String str = hrpHipSelectService.querySysMod(mapVo);
		
		return str;
	}
	
	// 业务系统
	@RequestMapping(value = "/hrp/hip/queryHipInitView", method = RequestMethod.POST)
	@ResponseBody
	public String queryHipInitView(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {mapVo.put("group_id", SessionManager.getGroupId());}
		
		if (mapVo.get("hos_id") == null) {mapVo.put("hos_id", SessionManager.getHosId());}
		
		if (mapVo.get("copy_code") == null) {mapVo.put("copy_code", SessionManager.getCopyCode());}

		String str = hrpHipSelectService.queryHipInitView(mapVo);

		return str;
	}
	
	// 业务系统
	@RequestMapping(value = "/hrp/hip/queryHipDsCof", method = RequestMethod.POST)
	@ResponseBody
	public String queryHipDsCof(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {mapVo.put("group_id", SessionManager.getGroupId());}
		
		if (mapVo.get("hos_id") == null) {mapVo.put("hos_id", SessionManager.getHosId());}
		
		if (mapVo.get("copy_code") == null) {mapVo.put("copy_code", SessionManager.getCopyCode());}

		String str = hrpHipSelectService.queryHipDsCof(mapVo);

		return str;
	}
	
	// 平台科室字典
	@RequestMapping(value = "/hrp/hip/queryHipDeptDict", method = RequestMethod.POST)
	@ResponseBody
	public String queryHipDeptDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {mapVo.put("group_id", SessionManager.getGroupId());}

		if (mapVo.get("hos_id") == null) {mapVo.put("hos_id", SessionManager.getHosId());}

		if (mapVo.get("copy_code") == null) {mapVo.put("copy_code", SessionManager.getCopyCode());}

		String str = hrpHipSelectService.queryHipDeptDict(mapVo);

		return str;
	}
	
	// HRP科室字典
	@RequestMapping(value = "/hrp/hip/queryHosDeptDict", method = RequestMethod.POST)
	@ResponseBody
	public String queryHosDeptDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {mapVo.put("group_id", SessionManager.getGroupId());}

		if (mapVo.get("hos_id") == null) {mapVo.put("hos_id", SessionManager.getHosId());}

		if (mapVo.get("copy_code") == null) {mapVo.put("copy_code", SessionManager.getCopyCode());}

		String str = hrpHipSelectService.queryHosDeptDict(mapVo);

		return str;
	}
	
	// HIP收费类别
	@RequestMapping(value = "/hrp/hip/queryHipChargeKindDict", method = RequestMethod.POST)
	@ResponseBody
	public String queryHipChargeKindDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {mapVo.put("group_id", SessionManager.getGroupId());}

		if (mapVo.get("hos_id") == null) {mapVo.put("hos_id", SessionManager.getHosId());}

		if (mapVo.get("copy_code") == null) {mapVo.put("copy_code", SessionManager.getCopyCode());}

		String str = hrpHipSelectService.queryHipChargeKindDict(mapVo);

		return str;
	}
	
	// HRP收费类别字典
	@RequestMapping(value = "/hrp/hip/queryCostChargeKindArrt", method = RequestMethod.POST)
	@ResponseBody
	public String queryCostChargeKindArrt(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {mapVo.put("group_id", SessionManager.getGroupId());}

		if (mapVo.get("hos_id") == null) {mapVo.put("hos_id", SessionManager.getHosId());}

		if (mapVo.get("copy_code") == null) {mapVo.put("copy_code", SessionManager.getCopyCode());}

		String str = hrpHipSelectService.queryCostChargeKindArrt(mapVo);

		return str;
	}
	
	// HIP收费项目字典
	@RequestMapping(value = "/hrp/hip/queryHipChargeDetailDict", method = RequestMethod.POST)
	@ResponseBody
	public String queryHipChargeDetailDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {mapVo.put("group_id", SessionManager.getGroupId());}

		if (mapVo.get("hos_id") == null) {mapVo.put("hos_id", SessionManager.getHosId());}

		if (mapVo.get("copy_code") == null) {mapVo.put("copy_code", SessionManager.getCopyCode());}

		String str = hrpHipSelectService.queryHipChargeDetailDict(mapVo);

		return str;
	}
	
	// HRP收费项目字典
	@RequestMapping(value = "/hrp/hip/queryCostChargeItemArrt", method = RequestMethod.POST)
	@ResponseBody
	public String queryCostChargeItemArrt(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {mapVo.put("group_id", SessionManager.getGroupId());}

		if (mapVo.get("hos_id") == null) {mapVo.put("hos_id", SessionManager.getHosId());}

		if (mapVo.get("copy_code") == null) {mapVo.put("copy_code", SessionManager.getCopyCode());}

		String str = hrpHipSelectService.queryCostChargeItemArrt(mapVo);

		return str;
	}
	
	// HRP收费项目字典
	@RequestMapping(value = "/hrp/hip/queryHipPayTypeDict", method = RequestMethod.POST)
	@ResponseBody
	public String queryHipPayTypeDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {mapVo.put("group_id", SessionManager.getGroupId());}

		if (mapVo.get("hos_id") == null) {mapVo.put("hos_id", SessionManager.getHosId());}

		if (mapVo.get("copy_code") == null) {mapVo.put("copy_code", SessionManager.getCopyCode());}

		String str = hrpHipSelectService.queryHipPayTypeDict(mapVo);

		return str;
	}
	
	// HRP收费项目字典
	@RequestMapping(value = "/hrp/hip/queryAccPayType", method = RequestMethod.POST)
	@ResponseBody
	public String queryAccPayType(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {mapVo.put("group_id", SessionManager.getGroupId());}

		if (mapVo.get("hos_id") == null) {mapVo.put("hos_id", SessionManager.getHosId());}

		if (mapVo.get("copy_code") == null) {mapVo.put("copy_code", SessionManager.getCopyCode());}

		String str = hrpHipSelectService.queryAccPayType(mapVo);

		return str;
	}
	
	// HIP患者类别
	@RequestMapping(value = "/hrp/hip/queryHipPatientTypeDict", method = RequestMethod.POST)
	@ResponseBody
	public String queryHipPatientTypeDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {mapVo.put("group_id", SessionManager.getGroupId());}

		if (mapVo.get("hos_id") == null) {mapVo.put("hos_id", SessionManager.getHosId());}

		if (mapVo.get("copy_code") == null) {mapVo.put("copy_code", SessionManager.getCopyCode());}

		String str = hrpHipSelectService.queryHipPatientTypeDict(mapVo);

		return str;
	}
	
	// HRP患者类别
	@RequestMapping(value = "/hrp/hip/queryHosPatientType", method = RequestMethod.POST)
	@ResponseBody
	public String queryHosPatientType(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {mapVo.put("group_id", SessionManager.getGroupId());}

		if (mapVo.get("hos_id") == null) {mapVo.put("hos_id", SessionManager.getHosId());}

		if (mapVo.get("copy_code") == null) {mapVo.put("copy_code", SessionManager.getCopyCode());}

		String str = hrpHipSelectService.queryHosPatientType(mapVo);

		return str;
	}
	
	// HIP供应商字典
	@RequestMapping(value = "/hrp/hip/queryHipSupDict", method = RequestMethod.POST)
	@ResponseBody
	public String queryHipSupDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {mapVo.put("group_id", SessionManager.getGroupId());}

		if (mapVo.get("hos_id") == null) {mapVo.put("hos_id", SessionManager.getHosId());}

		if (mapVo.get("copy_code") == null) {mapVo.put("copy_code", SessionManager.getCopyCode());}

		String str = hrpHipSelectService.queryHipSupDict(mapVo);

		return str;
	}
	
	// HRP供应商字典变更
	@RequestMapping(value = "/hrp/hip/queryHosSupDict", method = RequestMethod.POST)
	@ResponseBody
	public String queryHosSupDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {mapVo.put("group_id", SessionManager.getGroupId());}

		if (mapVo.get("hos_id") == null) {mapVo.put("hos_id", SessionManager.getHosId());}

		if (mapVo.get("copy_code") == null) {mapVo.put("copy_code", SessionManager.getCopyCode());}

		String str = hrpHipSelectService.queryHosSupDict(mapVo);

		return str;
	}
	
	// HIP仓库字典
	@RequestMapping(value = "/hrp/hip/queryHipStoreDict", method = RequestMethod.POST)
	@ResponseBody
	public String queryHipStoreDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {mapVo.put("group_id", SessionManager.getGroupId());}

		if (mapVo.get("hos_id") == null) {mapVo.put("hos_id", SessionManager.getHosId());}

		if (mapVo.get("copy_code") == null) {mapVo.put("copy_code", SessionManager.getCopyCode());}

		String str = hrpHipSelectService.queryHipStoreDict(mapVo);

		return str;
	}
	
	// HRP仓库字典变更
	@RequestMapping(value = "/hrp/hip/queryHosStoreDict", method = RequestMethod.POST)
	@ResponseBody
	public String queryHosStoreDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {mapVo.put("group_id", SessionManager.getGroupId());}

		if (mapVo.get("hos_id") == null) {mapVo.put("hos_id", SessionManager.getHosId());}

		if (mapVo.get("copy_code") == null) {mapVo.put("copy_code", SessionManager.getCopyCode());}

		String str = hrpHipSelectService.queryHosStoreDict(mapVo);

		return str;
	}
	
	// HRP仓库字典变更
	@RequestMapping(value = "/hrp/hip/queryHipMedTypeDict", method = RequestMethod.POST)
	@ResponseBody
	public String queryHipMedTypeDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {mapVo.put("group_id", SessionManager.getGroupId());}

		if (mapVo.get("hos_id") == null) {mapVo.put("hos_id", SessionManager.getHosId());}

		if (mapVo.get("copy_code") == null) {mapVo.put("copy_code", SessionManager.getCopyCode());}

		String str = hrpHipSelectService.queryHipMedTypeDict(mapVo);

		return str;
	}
	
	// HRP仓库字典变更
	@RequestMapping(value = "/hrp/hip/queryMedType", method = RequestMethod.POST)
	@ResponseBody
	public String queryMedType(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {mapVo.put("group_id", SessionManager.getGroupId());}

		if (mapVo.get("hos_id") == null) {mapVo.put("hos_id", SessionManager.getHosId());}

		if (mapVo.get("copy_code") == null) {mapVo.put("copy_code", SessionManager.getCopyCode());}

		String str = hrpHipSelectService.queryMedType(mapVo);

		return str;
	}
	
	// HIP药品字典
	@RequestMapping(value = "/hrp/hip/queryHipMedDict", method = RequestMethod.POST)
	@ResponseBody
	public String queryHipMedDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {mapVo.put("group_id", SessionManager.getGroupId());}

		if (mapVo.get("hos_id") == null) {mapVo.put("hos_id", SessionManager.getHosId());}

		if (mapVo.get("copy_code") == null) {mapVo.put("copy_code", SessionManager.getCopyCode());}

		String str = hrpHipSelectService.queryHipMedDict(mapVo);

		return str;
	}
	
	// HRP药品字典
	@RequestMapping(value = "/hrp/hip/queryMedInv", method = RequestMethod.POST)
	@ResponseBody
	public String queryMedInv(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {mapVo.put("group_id", SessionManager.getGroupId());}

		if (mapVo.get("hos_id") == null) {mapVo.put("hos_id", SessionManager.getHosId());}

		if (mapVo.get("copy_code") == null) {mapVo.put("copy_code", SessionManager.getCopyCode());}

		String str = hrpHipSelectService.queryMedInv(mapVo);

		return str;
	}
	
	// HIP材料分类字典
	@RequestMapping(value = "/hrp/hip/queryHipMatTypeDict", method = RequestMethod.POST)
	@ResponseBody
	public String queryHipMatTypeDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {mapVo.put("group_id", SessionManager.getGroupId());}

		if (mapVo.get("hos_id") == null) {mapVo.put("hos_id", SessionManager.getHosId());}

		if (mapVo.get("copy_code") == null) {mapVo.put("copy_code", SessionManager.getCopyCode());}

		String str = hrpHipSelectService.queryHipMatTypeDict(mapVo);

		return str;
	}
	
	// HRP材料分类字典
	@RequestMapping(value = "/hrp/hip/queryMatTypeDict", method = RequestMethod.POST)
	@ResponseBody
	public String queryMatTypeDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {mapVo.put("group_id", SessionManager.getGroupId());}

		if (mapVo.get("hos_id") == null) {mapVo.put("hos_id", SessionManager.getHosId());}

		if (mapVo.get("copy_code") == null) {mapVo.put("copy_code", SessionManager.getCopyCode());}

		String str = hrpHipSelectService.queryMatTypeDict(mapVo);

		return str;
	}
	
	// HIP材料字典
	@RequestMapping(value = "/hrp/hip/queryHipMatInvDict", method = RequestMethod.POST)
	@ResponseBody
	public String queryHipMatInvDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {mapVo.put("group_id", SessionManager.getGroupId());}

		if (mapVo.get("hos_id") == null) {mapVo.put("hos_id", SessionManager.getHosId());}

		if (mapVo.get("copy_code") == null) {mapVo.put("copy_code", SessionManager.getCopyCode());}

		String str = hrpHipSelectService.queryHipMatInvDict(mapVo);

		return str;
	}
	
	// HRP材料字典
	@RequestMapping(value = "/hrp/hip/queryMatInvDict", method = RequestMethod.POST)
	@ResponseBody
	public String queryMatInvDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {mapVo.put("group_id", SessionManager.getGroupId());}

		if (mapVo.get("hos_id") == null) {mapVo.put("hos_id", SessionManager.getHosId());}

		if (mapVo.get("copy_code") == null) {mapVo.put("copy_code", SessionManager.getCopyCode());}

		String str = hrpHipSelectService.queryMatInvDict(mapVo);

		return str;
	}
	
	// HIP资产分类字典
	@RequestMapping(value = "/hrp/hip/queryHipAssTypeDict", method = RequestMethod.POST)
	@ResponseBody
	public String queryHipAssTypeDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {mapVo.put("group_id", SessionManager.getGroupId());}

		if (mapVo.get("hos_id") == null) {mapVo.put("hos_id", SessionManager.getHosId());}

		if (mapVo.get("copy_code") == null) {mapVo.put("copy_code", SessionManager.getCopyCode());}

		String str = hrpHipSelectService.queryHipAssTypeDict(mapVo);

		return str;
	}
	
	// HRP资产分类字典
	@RequestMapping(value = "/hrp/hip/queryAssTypeDict", method = RequestMethod.POST)
	@ResponseBody
	public String queryAssTypeDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {mapVo.put("group_id", SessionManager.getGroupId());}

		if (mapVo.get("hos_id") == null) {mapVo.put("hos_id", SessionManager.getHosId());}

		if (mapVo.get("copy_code") == null) {mapVo.put("copy_code", SessionManager.getCopyCode());}

		String str = hrpHipSelectService.queryAssTypeDict(mapVo);

		return str;
	}
	
	
	// HIP资产字典
	@RequestMapping(value = "/hrp/hip/queryHipAssDict", method = RequestMethod.POST)
	@ResponseBody
	public String queryHipAssDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {mapVo.put("group_id", SessionManager.getGroupId());}

		if (mapVo.get("hos_id") == null) {mapVo.put("hos_id", SessionManager.getHosId());}

		if (mapVo.get("copy_code") == null) {mapVo.put("copy_code", SessionManager.getCopyCode());}

		String str = hrpHipSelectService.queryHipAssDict(mapVo);

		return str;
	}
	
	// HRP资产字典
	@RequestMapping(value = "/hrp/hip/queryAssDict", method = RequestMethod.POST)
	@ResponseBody
	public String queryAssDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {mapVo.put("group_id", SessionManager.getGroupId());}

		if (mapVo.get("hos_id") == null) {mapVo.put("hos_id", SessionManager.getHosId());}

		if (mapVo.get("copy_code") == null) {mapVo.put("copy_code", SessionManager.getCopyCode());}

		String str = hrpHipSelectService.queryAssDict(mapVo);

		return str;
	}
	
	// HIP资金来源
	@RequestMapping(value = "/hrp/hip/queryHipSourceDict", method = RequestMethod.POST)
	@ResponseBody
	public String queryHipSourceDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {mapVo.put("group_id", SessionManager.getGroupId());}

		if (mapVo.get("hos_id") == null) {mapVo.put("hos_id", SessionManager.getHosId());}

		if (mapVo.get("copy_code") == null) {mapVo.put("copy_code", SessionManager.getCopyCode());}

		String str = hrpHipSelectService.queryHipSourceDict(mapVo);

		return str;
	}
	
	// HRP资金来源
	@RequestMapping(value = "/hrp/hip/queryHosSource", method = RequestMethod.POST)
	@ResponseBody
	public String queryHosSource(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {mapVo.put("group_id", SessionManager.getGroupId());}

		if (mapVo.get("hos_id") == null) {mapVo.put("hos_id", SessionManager.getHosId());}

		if (mapVo.get("copy_code") == null) {mapVo.put("copy_code", SessionManager.getCopyCode());}

		String str = hrpHipSelectService.queryHosSource(mapVo);

		return str;
	}

	// HIP资金来源
	@RequestMapping(value = "/hrp/hip/queryHipPaymentItemDict", method = RequestMethod.POST)
	@ResponseBody
	public String queryHipPaymentItemDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {mapVo.put("group_id", SessionManager.getGroupId());}

		if (mapVo.get("hos_id") == null) {mapVo.put("hos_id", SessionManager.getHosId());}

		if (mapVo.get("copy_code") == null) {mapVo.put("copy_code", SessionManager.getCopyCode());}

		String str = hrpHipSelectService.queryHipPaymentItemDict(mapVo);

		return str;
	}
	
	// HRP资金来源
	@RequestMapping(value = "/hrp/hip/queryBudgPaymentItemDict", method = RequestMethod.POST)
	@ResponseBody
	public String queryBudgPaymentItemDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {mapVo.put("group_id", SessionManager.getGroupId());}

		if (mapVo.get("hos_id") == null) {mapVo.put("hos_id", SessionManager.getHosId());}

		if (mapVo.get("copy_code") == null) {mapVo.put("copy_code", SessionManager.getCopyCode());}

		String str = hrpHipSelectService.queryBudgPaymentItemDict(mapVo);

		return str;
	}
	// 物资分类动态表头
	@RequestMapping(value = "/hrp/hip/queryALLMatTypeDict", method = RequestMethod.POST)
	@ResponseBody
	public String queryALLMatTypeDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		if (mapVo.get("group_id") == null) {mapVo.put("group_id", SessionManager.getGroupId());}
		
		if (mapVo.get("hos_id") == null) {mapVo.put("hos_id", SessionManager.getHosId());}
		
		if (mapVo.get("copy_code") == null) {mapVo.put("copy_code", SessionManager.getCopyCode());}
		
		mapVo.put("user_id", SessionManager.getUserId());
		
		String str = hrpHipSelectService.queryALLMatTypeDict(mapVo);
		return str;
	}
	// 财务分类动态表头
	@RequestMapping(value = "/hrp/hip/queryALLMatFimTypeDict", method = RequestMethod.POST)
	@ResponseBody
	public String queryALLMatFimTypeDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		if (mapVo.get("group_id") == null) {mapVo.put("group_id", SessionManager.getGroupId());}
		
		if (mapVo.get("hos_id") == null) {mapVo.put("hos_id", SessionManager.getHosId());}
		
		if (mapVo.get("copy_code") == null) {mapVo.put("copy_code", SessionManager.getCopyCode());}
		
		String str = hrpHipSelectService.queryALLMatFimTypeDict(mapVo);
		return str;
	}
	// 药品分类动态表头
		@RequestMapping(value = "/hrp/hip/queryALLMedTypeDict", method = RequestMethod.POST)
		@ResponseBody
		public String queryALLMedTypeDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
			
			if (mapVo.get("group_id") == null) {mapVo.put("group_id", SessionManager.getGroupId());}
			
			if (mapVo.get("hos_id") == null) {mapVo.put("hos_id", SessionManager.getHosId());}
			
			if (mapVo.get("copy_code") == null) {mapVo.put("copy_code", SessionManager.getCopyCode());}
			
			String str = hrpHipSelectService.queryALLMedTypeDict(mapVo);
			return str;
		}
		// 财务分类动态表头
		@RequestMapping(value = "/hrp/hip/queryALLMedFimTypeDict", method = RequestMethod.POST)
		@ResponseBody
		public String queryALLMedFimTypeDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
			
			if (mapVo.get("group_id") == null) {mapVo.put("group_id", SessionManager.getGroupId());}
			
			if (mapVo.get("hos_id") == null) {mapVo.put("hos_id", SessionManager.getHosId());}
			
			if (mapVo.get("copy_code") == null) {mapVo.put("copy_code", SessionManager.getCopyCode());}
			
			String str = hrpHipSelectService.queryALLMedFimTypeDict(mapVo);
			return str;
		}
		
		@RequestMapping(value = "/hrp/hip/queryHipDataSource", method = RequestMethod.POST)
		@ResponseBody
		public String queryHipDataSource(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
			
			
			String str = hrpHipSelectService.queryHipDataSource(mapVo);
			return str;
		}
		
}
