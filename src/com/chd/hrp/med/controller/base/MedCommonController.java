/**
 * @Copyright: Copyright (c) 2015-2-14
 * @Company: 智慧云康（北京）数据科技有限公司
 */
package com.chd.hrp.med.controller.base;

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
import com.chd.base.MyConfig;
import com.chd.base.SessionManager;
import com.chd.hrp.med.service.base.MedCommonService;

/**
 * @Title. @Description.
 * 
 * @Author: LiuYingDuo
 * @email: bell@s-chd.com
 * @Version: 1.0
 */
@Controller
public class MedCommonController extends BaseController {

	private static Logger logger = Logger.getLogger(MedCommonController.class);

	@Resource(name = "medCommonService")
	private final MedCommonService medCommonService = null;
	
	// 材料字典列表（有材料库存）
	@RequestMapping(value = "/hrp/med/queryMedInvList", method = RequestMethod.POST)
	@ResponseBody
	public String queryMedInvList(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		mapVo.put("by_sup_inv", MyConfig.getSysPara("08021"));
		//科室申领业务已分开不需要该条件
		//mapVo.put("by_is_zero", MyConfig.getSysPara("08038"));
		String medInvList = medCommonService.queryMedInvList(getPage(mapVo));
		return medInvList;
	}
	
	// 材料字典列表（有材料库存）  科室申领
		@RequestMapping(value = "/hrp/med/queryMedInvListApply", method = RequestMethod.POST)
		@ResponseBody
		public String queryMedInvListApply(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
			if (mapVo.get("group_id") == null) {
				mapVo.put("group_id", SessionManager.getGroupId());
			}
			if (mapVo.get("hos_id") == null) {
				mapVo.put("hos_id", SessionManager.getHosId());
			}
			if (mapVo.get("copy_code") == null) {
				mapVo.put("copy_code", SessionManager.getCopyCode());
			}
			//科室申领不需要过滤供应商
			//mapVo.put("by_sup_inv", MyConfig.getSysPara("08021"));
			
			//如果页面选择不显示零库存则需要判断系统参数是否选择了按零库存管理
			if(mapVo.get("is_zero") != null && "1".equals(mapVo.get("is_zero").toString())){
				int by_is_zero = Integer.valueOf(String.valueOf(MyConfig.getSysPara("08038")));
				//如果系统参数选择是则优先选择系统参数的判断
				if(by_is_zero == 1){
					mapVo.put("by_is_zero", MyConfig.getSysPara("08038"));
					mapVo.remove("is_zero");
				}
			}
			
			String medInvList = medCommonService.queryMedInvListApply(getPage(mapVo));
			return medInvList;
		}
		
	
	
	// 材料字典列表（专购品入库）
		@RequestMapping(value = "/hrp/med/queryMedInvListSpecial", method = RequestMethod.POST)
		@ResponseBody
		public String queryMedInvListSpecial(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
			if (mapVo.get("group_id") == null) {
				mapVo.put("group_id", SessionManager.getGroupId());
			}
			if (mapVo.get("hos_id") == null) {
				mapVo.put("hos_id", SessionManager.getHosId());
			}
			if (mapVo.get("copy_code") == null) {
				mapVo.put("copy_code", SessionManager.getCopyCode());
			}
			mapVo.put("by_sup_inv", MyConfig.getSysPara("08021"));
			//专购品入库不做0库存管理
			//mapVo.put("by_is_zero", MyConfig.getSysPara("08038"));
			String medInvList = medCommonService.queryMedInvListSpecial(getPage(mapVo));
			return medInvList;
		}
	// 材料字典列表（入库）
		@RequestMapping(value = "/hrp/med/queryMedInvListIn", method = RequestMethod.POST)
		@ResponseBody
		public String queryMedInvListIn(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
			if (mapVo.get("group_id") == null) {
				mapVo.put("group_id", SessionManager.getGroupId());
			}
			if (mapVo.get("hos_id") == null) {
				mapVo.put("hos_id", SessionManager.getHosId());
			}
			if (mapVo.get("copy_code") == null) {
				mapVo.put("copy_code", SessionManager.getCopyCode());
			}
			mapVo.put("by_sup_inv", MyConfig.getSysPara("08021"));
			//材料入库不做0库存管理
			//mapVo.put("by_is_zero", MyConfig.getSysPara("08038"));
			String medInvList = medCommonService.queryMedInvListIn(getPage(mapVo));
			return medInvList;
		}
		
		// 材料字典列表（期初入库）
	   @RequestMapping(value = "/hrp/med/queryMedInvListInitial", method = RequestMethod.POST)
	   @ResponseBody
	   public String queryMedInvListInitial(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		 if (mapVo.get("group_id") == null) {
			 mapVo.put("group_id", SessionManager.getGroupId());
		 }
		 if (mapVo.get("hos_id") == null) {
			 mapVo.put("hos_id", SessionManager.getHosId());
		 }
		 if (mapVo.get("copy_code") == null) {
			 mapVo.put("copy_code", SessionManager.getCopyCode());
		 }
		 mapVo.put("by_sup_inv", MyConfig.getSysPara("08021"));
		 //期初入库不做0库存管理
		 //mapVo.put("by_is_zero", MyConfig.getSysPara("08038"));
		 String medInvList = medCommonService.queryMedInvListInitial(getPage(mapVo));
		 return medInvList;
		 }
	
	// 材料字典列表（没有材料库存,仓库）
	@RequestMapping(value = "/hrp/med/queryMedInvListNotStore", method = RequestMethod.POST)
	@ResponseBody
	public String queryMedInvListNotStore(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		//科室申领不需要供应商过滤
		//mapVo.put("by_sup_inv", MyConfig.getSysPara("08021"));
		String medInvList = medCommonService.queryMedInvListNotStore(getPage(mapVo));
		return medInvList;
	}
	
	// 代销材料字典列表
	@RequestMapping(value = "/hrp/med/queryMedAffiInvList", method = RequestMethod.POST)
	@ResponseBody
	public String queryMedAffiInvList(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		mapVo.put("by_sup_inv", MyConfig.getSysPara("08021"));
		String medInvList = medCommonService.queryMedAffiInvList(getPage(mapVo));
		return medInvList;
	}
	
	// 材料图片列表
	@RequestMapping(value = "/hrp/med/queryInvPicture", method = RequestMethod.POST)
	@ResponseBody
	public String queryInvPicture(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		String medInvList = medCommonService.queryInvPicture(mapVo);
		return medInvList;
	}
	
	// 普通库存材料列表（不含批次） 库存盘点
	@RequestMapping(value = "/hrp/med/queryMedStockInvList", method = RequestMethod.POST)
	@ResponseBody
	public String queryMedStockInvList(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		String medInvList = medCommonService.queryMedStockInvList(getPage(mapVo));
		return medInvList;
	}
	
	// 普通库存材料列表（不含批次） 材料调拨 
		@RequestMapping(value = "/hrp/med/queryMedStockInvListTran", method = RequestMethod.POST)
		@ResponseBody
		public String queryMedStockInvListTran(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
			if (mapVo.get("group_id") == null) {
				mapVo.put("group_id", SessionManager.getGroupId());
			}
			if (mapVo.get("hos_id") == null) {
				mapVo.put("hos_id", SessionManager.getHosId());
			}
			if (mapVo.get("copy_code") == null) {
				mapVo.put("copy_code", SessionManager.getCopyCode());
			}
			String medInvList = medCommonService.queryMedStockInvListTran(getPage(mapVo));
			return medInvList;
		}
		
	
	// 普通库存材料列表（不含批次）  材料出库
		@RequestMapping(value = "/hrp/med/queryMedStockInvListOut", method = RequestMethod.POST)
		@ResponseBody
		public String queryMedStockInvListOut(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
			if (mapVo.get("group_id") == null) {
				mapVo.put("group_id", SessionManager.getGroupId());
			}
			if (mapVo.get("hos_id") == null) {
				mapVo.put("hos_id", SessionManager.getHosId());
			}
			if (mapVo.get("copy_code") == null) {
				mapVo.put("copy_code", SessionManager.getCopyCode());
			}
			String medInvList = medCommonService.queryMedStockInvListOut(getPage(mapVo));
			return medInvList;
		}
	
	// 普通库存材料列表（不含批次） 材料退货
		@RequestMapping(value = "/hrp/med/queryMedStockInvListBack", method = RequestMethod.POST)
		@ResponseBody
		public String queryMedStockInvListBack(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
			if (mapVo.get("group_id") == null) {
				mapVo.put("group_id", SessionManager.getGroupId());
			}
			if (mapVo.get("hos_id") == null) {
				mapVo.put("hos_id", SessionManager.getHosId());
			}
			if (mapVo.get("copy_code") == null) {
				mapVo.put("copy_code", SessionManager.getCopyCode());
			}
			String medInvList = medCommonService.queryMedStockInvListBack(getPage(mapVo));
			return medInvList;
		}
		
		/**
		 * 材料退货  先把批次查出来，然后根据inv_id,batch_no,bar_code....求和
		 * @param mapVo
		 * @param mode
		 * @return
		 * @throws Exception
		 */
		@RequestMapping(value = "/hrp/med/queryMedStockInvListBackNew", method = RequestMethod.POST)
		@ResponseBody
		public String queryMedStockInvListBackNew(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
			if (mapVo.get("group_id") == null) {
				mapVo.put("group_id", SessionManager.getGroupId());
			}
			if (mapVo.get("hos_id") == null) {
				mapVo.put("hos_id", SessionManager.getHosId());
			}
			if (mapVo.get("copy_code") == null) {
				mapVo.put("copy_code", SessionManager.getCopyCode());
			}
			String medInvList = medCommonService.queryMedStockInvListBackNew(getPage(mapVo));
			return medInvList;
		}
		
		
	// 普通库存材料明细列表（含批次）
	@RequestMapping(value = "/hrp/med/queryMedStockInvDetailList", method = RequestMethod.POST)
	@ResponseBody
	public String queryMedStockInvDetailList(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		String medInvList = medCommonService.queryMedStockInvDetailList(getPage(mapVo));
		return medInvList;
	}
	
	//获取材料列表，有上期耗用量，上期计划量，本期计划量
	@RequestMapping(value = "/hrp/med/queryMedInvListDept", method = RequestMethod.POST)
	@ResponseBody
	public String queryMedInvListDept(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		if(mapVo.get("store_id") == null || "null".equals(mapVo.get("store_id"))){
			
			mapVo.put("store_id", "");
		}
		String medInvList = medCommonService.queryMedInvListDept(getPage(mapVo));
		return medInvList;
	}
	//代销出库材料列表(不含批次)
	@RequestMapping(value = "/hrp/med/queryMedAffiOutInvList", method = RequestMethod.POST)
	@ResponseBody
	public String queryMedAffiOutInvList(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		String medInvList = medCommonService.queryMedAffiOutInvList(getPage(mapVo));
		return medInvList;
	}
	
	//代销出库材料列表(含批次)
	@RequestMapping(value = "/hrp/med/queryMedAffiOutDetailInvList", method = RequestMethod.POST)
	@ResponseBody
	public String queryMedAffiOutDetailInvList(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
			
		String medInvList = medCommonService.queryMedAffiOutDetailInvList(getPage(mapVo));
		return medInvList;
	}
	
	/**
	 * @Description 先进先出自动匹配材料
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/queryMedInvByFifo", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMedInvByFifo(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		String medInvList = medCommonService.queryMedInvByFifo(mapVo);

		return JSONObject.parseObject(medInvList);
	}
	/**
	 * @Description 先进先出自动匹配材料
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/queryMedAffiInvByFifo", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMedAffiInvByFifo(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		String medInvList = medCommonService.queryMedAffiInvByFifo(mapVo);

		return JSONObject.parseObject(medInvList);
	}
	/**
	 * 判断是否有不属于该仓库的材料
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/existsMedStoreIncludeInv", method = RequestMethod.POST)
	@ResponseBody
	public String existsMedStoreIncludeInv(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		String medMsg = medCommonService.existsMedStoreIncludeInv(mapVo);
		return medMsg;
	}
	
	/**
	 * 判断是否有不属于该供应商的材料
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/existsMedSupIncludeInv", method = RequestMethod.POST)
	@ResponseBody
	public String existsMedSupIncludeInv(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		mapVo.put("by_sup_inv", MyConfig.getSysPara("08021"));
		
		String medMsg = medCommonService.existsMedSupIncludeInv(mapVo);
		return medMsg;
	}
	
	/**
	 * 判断供应商是否是材料的唯一供应商
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/existsMedInvOnlySup", method = RequestMethod.POST)
	@ResponseBody
	public String existsMedInvOnlySup(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		String medMsg = medCommonService.existsMedInvOnlySup(mapVo);
		return medMsg;
	}
	
	
	/**
	 * （代销）代销材料是否包含于该供应商中
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/existsMedAffiInvOnlySup", method = RequestMethod.POST)
	@ResponseBody
	public String existsMedAffiInvOnlySup(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		String medMsg = medCommonService.existsMedInvOnlySup(mapVo);
		return medMsg;
	}
	/**
	 * （代销）代销材料是否存在唯一供应商
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/existsMedAffiSupIncludeInv", method = RequestMethod.POST)
	@ResponseBody
	public String existsMedAffiSupIncludeInv(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		mapVo.put("by_sup_inv", MyConfig.getSysPara("08021"));
		
		String medMsg = medCommonService.existsMedAffiSupIncludeInv(mapVo);
		return medMsg;
	}
	
	/**
	 * 退货时看看供应商是否入库该材料（常备）
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/existsMedInSupIsInv", method = RequestMethod.POST)
	@ResponseBody
	public String existsMedInSupIsInv(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		mapVo.put("by_sup_inv", MyConfig.getSysPara("08021"));
		
		String medMsg = medCommonService.existsMedInSupIsInv(mapVo);
		return medMsg;
	}
	/**
	 * 退货时看看供应商是否入库该材料
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/existsMedAffiSupIsInv", method = RequestMethod.POST)
	@ResponseBody
	public String existsMedAffiSupIsInv(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		mapVo.put("by_sup_inv", MyConfig.getSysPara("08021"));
		
		String medMsg = medCommonService.existsMedAffiSupIsInv(mapVo);
		return medMsg;
	}
	/**
	 * @Description 判断批号有效日期是否一致
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/queryMedInvBatchInva", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMedInvBatchInva(@RequestParam Map<String, Object> mapVo, Model mode)throws Exception {
		
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		String medJson = medCommonService.queryMedInvBatchInva(mapVo);

		return JSONObject.parseObject(medJson);
	}

	/**
	 * @Description 判断批号灭菌日期是否一致
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/queryMedInvBatchDisinfect", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMedInvBatchDisinfect(@RequestParam Map<String, Object> mapVo, Model mode)throws Exception {
		
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		String medJson = medCommonService.queryMedInvBatchDisinfect(mapVo);

		return JSONObject.parseObject(medJson);
	}
	/**
	 * @Description 判断批号单价是否一致
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/queryMedInvBatchPrice", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMedInvBatchPrice(@RequestParam Map<String, Object> mapVo, Model mode)throws Exception {
		
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		String medJson = medCommonService.queryMedInvBatchPrice(mapVo);
		
		return JSONObject.parseObject(medJson);
	}
	/**
	 * @Description 采购计划 材料列表
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/queryMedInvListByPur", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMedInvListByPur(@RequestParam Map<String, Object> mapVo, Model mode)throws Exception {
		
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		if (mapVo.get("user_id") == null) {
			mapVo.put("user_id", SessionManager.getUserId());
		}
		if (mapVo.get("mod_code") == null) {
			mapVo.put("mod_code", "08");
		}
		String medJson = medCommonService.queryMedInvListByPur(getPage(mapVo));
		
		return JSONObject.parseObject(medJson);
	}
	
	/**
	 * 查询 判断材料证件过期 
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/queryMedCertDate", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMedCertDate(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		String medMsg = medCommonService.queryMedCertDate(mapVo);
		return JSONObject.parseObject(medMsg);
	}

	//耐用品库房材料字典列表
	@RequestMapping(value = "/hrp/med/queryMedDuraStoreInvList", method = RequestMethod.POST)
	@ResponseBody
	public String queryMedDuraStoreInvList(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		String medInvList = medCommonService.queryMedDuraStoreInvList(getPage(mapVo));
		return medInvList;
	}

	//耐用品科室材料字典列表
	@RequestMapping(value = "/hrp/med/queryMedDuraDeptInvList", method = RequestMethod.POST)
	@ResponseBody
	public String queryMedDuraDeptInvList(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		String medInvList = medCommonService.queryMedDuraDeptInvList(getPage(mapVo));
		return medInvList;
	}

	//普通材料扫码出库返回对应材料列表
	@RequestMapping(value = "/hrp/med/queryMedOutInvListByBar", method = RequestMethod.POST)
	@ResponseBody
	public String queryMedOutInvListByBar(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		String medInvList;
		try {
			medInvList = medCommonService.queryMedOutInvListByBar(mapVo);
		} catch (Exception e) {
			medInvList = "{\"error\":\""+e.getMessage()+"\"}";
		}
		return medInvList;
	}

	//代销材料扫码出库返回对应材料列表
	@RequestMapping(value = "/hrp/med/queryMedAffiOutInvListByBar", method = RequestMethod.POST)
	@ResponseBody
	public String queryMedAffiOutInvListByBar(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		String medInvList;
		try {
			medInvList = medCommonService.queryMedAffiOutInvListByBar(mapVo);
		} catch (Exception e) {
			medInvList = "{\"error\":\""+e.getMessage()+"\"}";
		}
		return medInvList;
	}

	//普通材料退库查询科室已领用材料列表
	@RequestMapping(value = "/hrp/med/queryMedDeptStockInvList", method = RequestMethod.POST)
	@ResponseBody
	public String queryMedDeptStockInvList(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		String medInvList;
		try {
			medInvList = medCommonService.queryMedDeptStockInvList(mapVo);
		} catch (Exception e) {
			medInvList = "{\"error\":\""+e.getMessage()+"\"}";
		}
		return medInvList;
	}
	/**
	 * 仓库是否控制科室申请数量不能大于仓库库存
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/queryMedStoreByCode", method = RequestMethod.POST)
	@ResponseBody
	public String queryMedStoreByCode(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		String medMsg = medCommonService.queryMedStoreByCode(mapVo);
		return medMsg;
	}
}
