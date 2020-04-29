/**
 * @Copyright: Copyright (c) 2015-2-14
 * @Company: 智慧云康（北京）数据科技有限公司
 */
package com.chd.hrp.mat.controller.base; 
 
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
import com.chd.hrp.mat.service.base.MatCommonService;

/**
 * @Title. @Description.
 * 
 * @Author: LiuYingDuo 
 * @email: bell@s-chd.com
 * @Version: 1.0
 */
@Controller
public class MatCommonController extends BaseController {

	private static Logger logger = Logger.getLogger(MatCommonController.class);

	@Resource(name = "matCommonService")
	private final MatCommonService matCommonService = null;
	
	// 材料字典列表（有材料库存）
	@RequestMapping(value = "/hrp/mat/queryMatInvList", method = RequestMethod.POST)
	@ResponseBody
	public String queryMatInvList(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		mapVo.put("by_sup_inv", MyConfig.getSysPara("04021"));
		//科室申领业务已分开不需要该条件
		//mapVo.put("by_is_zero", MyConfig.getSysPara("04038"));
		String matInvList = matCommonService.queryMatInvList(getPage(mapVo));
		return matInvList;
	}
	
	
	//材料字典列表代销入库
	@RequestMapping(value = "/hrp/mat/queryMatAffiInvListIn", method = RequestMethod.POST)
	@ResponseBody
	public String queryMatAffiInvListIn(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		mapVo.put("by_sup_inv", MyConfig.getSysPara("04021"));
		//科室申领业务已分开不需要该条件
		//mapVo.put("by_is_zero", MyConfig.getSysPara("04038"));
		String matInvList = matCommonService.queryMatAffiInvListIn(getPage(mapVo));
		return matInvList;
	}
	
	
	
	// 材料字典列表（有材料库存）  科室申领
		@RequestMapping(value = "/hrp/mat/queryMatInvListApply", method = RequestMethod.POST)
		@ResponseBody
		public String queryMatInvListApply(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
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
			//mapVo.put("by_sup_inv", MyConfig.getSysPara("04021"));
			
			//如果页面选择不显示零库存则需要判断系统参数是否选择了按零库存管理 
			if(mapVo.get("is_zero") != null && "1".equals(mapVo.get("is_zero").toString())){
				int by_is_zero = Integer.valueOf(String.valueOf(MyConfig.getSysPara("04038")));
				//如果系统参数选择是则优先选择系统参数的判断
				if(by_is_zero == 1){
					mapVo.put("by_is_zero", MyConfig.getSysPara("04038"));
					mapVo.remove("is_zero");
				}
			} 
			if(mapVo.get("inv_code") != null && !mapVo.get("inv_code").equals("")) {  
					mapVo.put("inv_code", mapVo.get("inv_code").toString().toUpperCase()); 
			} 
			if(mapVo.get("is_stop") != null && "1".equals(mapVo.get("is_stop").toString())) {  
				mapVo.put("is_stop", 0); 
			} 
			
			String matInvList = matCommonService.queryMatInvListApply(getPage(mapVo));
			return matInvList;
		}
		
	
	
	// 材料字典列表（专购品入库）
		@RequestMapping(value = "/hrp/mat/queryMatInvListSpecial", method = RequestMethod.POST)
		@ResponseBody
		public String queryMatInvListSpecial(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
			if (mapVo.get("group_id") == null) {
				mapVo.put("group_id", SessionManager.getGroupId());
			}
			if (mapVo.get("hos_id") == null) {
				mapVo.put("hos_id", SessionManager.getHosId());
			}
			if (mapVo.get("copy_code") == null) {
				mapVo.put("copy_code", SessionManager.getCopyCode());
			}
			mapVo.put("by_sup_inv", MyConfig.getSysPara("04021"));
			//专购品入库不做0库存管理 
			//mapVo.put("by_is_zero", MyConfig.getSysPara("04038"));
			String matInvList = matCommonService.queryMatInvListSpecial(getPage(mapVo));
			return matInvList;
		}
	// 材料字典列表（入库）
		@RequestMapping(value = "/hrp/mat/queryMatInvListIn", method = RequestMethod.POST)
		@ResponseBody
		public String queryMatInvListIn(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
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
			mapVo.put("by_sup_inv", MyConfig.getSysPara("04021"));
			//材料入库不做0库存管理
			//mapVo.put("by_is_zero", MyConfig.getSysPara("04038"));
			String matInvList = matCommonService.queryMatInvListIn(getPage(mapVo));
			return matInvList;
		}
		
		// 材料字典列表（期初入库）
	   @RequestMapping(value = "/hrp/mat/queryMatInvListInitial", method = RequestMethod.POST)
	   @ResponseBody
	   public String queryMatInvListInitial(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		 if (mapVo.get("group_id") == null) {
			 mapVo.put("group_id", SessionManager.getGroupId());
		 }
		 if (mapVo.get("hos_id") == null) {
			 mapVo.put("hos_id", SessionManager.getHosId());
		 }
		 if (mapVo.get("copy_code") == null) {
			 mapVo.put("copy_code", SessionManager.getCopyCode());
		 }
		 mapVo.put("by_sup_inv", MyConfig.getSysPara("04021"));
		 //期初入库不做0库存管理
		 //mapVo.put("by_is_zero", MyConfig.getSysPara("04038"));
		 String matInvList = matCommonService.queryMatInvListInitial(getPage(mapVo));
		 return matInvList;
		 }
	
	// 材料字典列表（没有材料库存,仓库）
	@RequestMapping(value = "/hrp/mat/queryMatInvListNotStore", method = RequestMethod.POST)
	@ResponseBody
	public String queryMatInvListNotStore(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
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
		//mapVo.put("by_sup_inv", MyConfig.getSysPara("04021"));
		String matInvList = matCommonService.queryMatInvListNotStore(getPage(mapVo));
		return matInvList;
	}
	
	// 代销材料字典列表
	@RequestMapping(value = "/hrp/mat/queryMatAffiInvList", method = RequestMethod.POST)
	@ResponseBody
	public String queryMatAffiInvList(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		mapVo.put("by_sup_inv", MyConfig.getSysPara("04021"));
		String matInvList = matCommonService.queryMatAffiInvList(getPage(mapVo));
		return matInvList;
	}
	
	// 材料图片列表
	@RequestMapping(value = "/hrp/mat/queryInvPicture", method = RequestMethod.POST)
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
		String matInvList = matCommonService.queryInvPicture(mapVo);
		return matInvList;
	}
	
	// 普通库存材料列表（不含批次） 库存盘点
	@RequestMapping(value = "/hrp/mat/queryMatStockInvList", method = RequestMethod.POST)
	@ResponseBody
	public String queryMatStockInvList(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		String matInvList = matCommonService.queryMatStockInvList(getPage(mapVo));
		return matInvList;
	}
	
	// 普通库存材料列表（不含批次） 材料调拨 
		@RequestMapping(value = "/hrp/mat/queryMatStockInvListTran", method = RequestMethod.POST)
		@ResponseBody
		public String queryMatStockInvListTran(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
			if (mapVo.get("group_id") == null) {
				mapVo.put("group_id", SessionManager.getGroupId());
			}
			if (mapVo.get("hos_id") == null) {
				mapVo.put("hos_id", SessionManager.getHosId());
			}
			if (mapVo.get("copy_code") == null) {
				mapVo.put("copy_code", SessionManager.getCopyCode());
			}
			String matInvList = matCommonService.queryMatStockInvListTran(getPage(mapVo));
			return matInvList;
		}
		
	
	// 普通库存材料列表（不含批次）  材料出库
		@RequestMapping(value = "/hrp/mat/queryMatStockInvListOut", method = RequestMethod.POST)
		@ResponseBody
		public String queryMatStockInvListOut(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
			if (mapVo.get("group_id") == null) {
				mapVo.put("group_id", SessionManager.getGroupId());
			}
			if (mapVo.get("hos_id") == null) {
				mapVo.put("hos_id", SessionManager.getHosId());
			}
			if (mapVo.get("copy_code") == null) {
				mapVo.put("copy_code", SessionManager.getCopyCode());
			}
			String matInvList = matCommonService.queryMatStockInvListOut(getPage(mapVo));
			return matInvList;
		}
	
		// 普通库存材料列表（不含批次） 科室退库
		@RequestMapping(value = "/hrp/mat/queryMatStockInvListOutByBack", method = RequestMethod.POST)
		@ResponseBody
		public String queryMatStockInvListOutByBack(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
			if (mapVo.get("group_id") == null) {
				mapVo.put("group_id", SessionManager.getGroupId());
			}
			if (mapVo.get("hos_id") == null) {
				mapVo.put("hos_id", SessionManager.getHosId());
			}
			if (mapVo.get("copy_code") == null) {
				mapVo.put("copy_code", SessionManager.getCopyCode());
			}
			String matInvList = matCommonService.queryMatStockInvListOutByBack(getPage(mapVo));
			return matInvList;
		}
		
	// 普通库存材料列表（不含批次） 材料退货
		@RequestMapping(value = "/hrp/mat/queryMatStockInvListBack", method = RequestMethod.POST)
		@ResponseBody
		public String queryMatStockInvListBack(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
			if (mapVo.get("group_id") == null) {
				mapVo.put("group_id", SessionManager.getGroupId());
			}
			if (mapVo.get("hos_id") == null) {
				mapVo.put("hos_id", SessionManager.getHosId());
			}
			if (mapVo.get("copy_code") == null) {
				mapVo.put("copy_code", SessionManager.getCopyCode());
			}
			String matInvList = matCommonService.queryMatStockInvListBack(getPage(mapVo));
			return matInvList;
		}
		
		/**
		 * 材料退货  先把批次查出来，然后根据inv_id,batch_no,bar_code....求和
		 * @param mapVo
		 * @param mode
		 * @return
		 * @throws Exception
		 */ 
		@RequestMapping(value = "/hrp/mat/queryMatStockInvListBackNew", method = RequestMethod.POST)
		@ResponseBody
		public String queryMatStockInvListBackNew(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
			if (mapVo.get("group_id") == null) {
				mapVo.put("group_id", SessionManager.getGroupId());
			}
			if (mapVo.get("hos_id") == null) {
				mapVo.put("hos_id", SessionManager.getHosId());
			}
			if (mapVo.get("copy_code") == null) {
				mapVo.put("copy_code", SessionManager.getCopyCode());
			}
			String matInvList = matCommonService.queryMatStockInvListBackNew(getPage(mapVo));
			return matInvList;
		}
		
		
	// 普通库存材料明细列表（含批次）
	@RequestMapping(value = "/hrp/mat/queryMatStockInvDetailList", method = RequestMethod.POST)
	@ResponseBody
	public String queryMatStockInvDetailList(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		String matInvList = matCommonService.queryMatStockInvDetailList(getPage(mapVo));
		return matInvList;
	}
	
	//获取材料列表，有上期耗用量，上期计划量，本期计划量
	@RequestMapping(value = "/hrp/mat/queryMatInvListDept", method = RequestMethod.POST)
	@ResponseBody
	public String queryMatInvListDept(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
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
		String matInvList = matCommonService.queryMatInvListDept(getPage(mapVo));
		return matInvList;
	}
	//代销出库材料列表(不含批次)
	@RequestMapping(value = "/hrp/mat/queryMatAffiOutInvList", method = RequestMethod.POST)
	@ResponseBody
	public String queryMatAffiOutInvList(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		String matInvList = matCommonService.queryMatAffiOutInvList(getPage(mapVo));
		return matInvList;
	}
	//代销出库材料列表(不含批次)调拨
	@RequestMapping(value = "/hrp/mat/queryMatAffiTranInvList", method = RequestMethod.POST)
	@ResponseBody
	public String queryMatAffiTranInvList(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		String matInvList = matCommonService.queryMatAffiTranInvList(getPage(mapVo));
		return matInvList;
	}
	//代销出库材料列表(含批次)
	@RequestMapping(value = "/hrp/mat/queryMatAffiOutDetailInvList", method = RequestMethod.POST)
	@ResponseBody
	public String queryMatAffiOutDetailInvList(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
			
		String matInvList = matCommonService.queryMatAffiOutDetailInvList(getPage(mapVo));
		return matInvList;
	}
	
	/**
	 * @Description 先进先出自动匹配材料
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/queryMatInvByFifo", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMatInvByFifo(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		String matInvList = matCommonService.queryMatInvByFifo(mapVo);

		return JSONObject.parseObject(matInvList);
	}
	/**
	 * @Description 先进先出自动匹配材料
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/queryMatAffiInvByFifo", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMatAffiInvByFifo(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		String matInvList = matCommonService.queryMatAffiInvByFifo(mapVo);

		return JSONObject.parseObject(matInvList);
	}
	/**
	 * 判断是否有不属于该仓库的材料
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/existsMatStoreIncludeInv", method = RequestMethod.POST)
	@ResponseBody
	public String existsMatStoreIncludeInv(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		String matMsg = matCommonService.existsMatStoreIncludeInv(mapVo);
		return matMsg;
	}
	
	/**
	 * 判断是否有条码存在
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/existsMatSnInv", method = RequestMethod.POST)
	@ResponseBody
	public String existsMatSnInv(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		String matMsg = matCommonService.existsMatSnInv(mapVo);
		return matMsg;
	}
	/**
	 * 判断是否有不属于该供应商的材料
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/existsMatSupIncludeInv", method = RequestMethod.POST)
	@ResponseBody
	public String existsMatSupIncludeInv(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		mapVo.put("by_sup_inv", MyConfig.getSysPara("04021"));
		
		String matMsg = matCommonService.existsMatSupIncludeInv(mapVo);
		return matMsg;
	}
	
	/**
	 * 判断供应商是否是材料的唯一供应商
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/existsMatInvOnlySup", method = RequestMethod.POST)
	@ResponseBody
	public String existsMatInvOnlySup(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		String matMsg = matCommonService.existsMatInvOnlySup(mapVo);
		return matMsg;
	}
	
	
	/**
	 * （代销）代销材料是否包含于该供应商中
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/existsMatAffiInvOnlySup", method = RequestMethod.POST)
	@ResponseBody
	public String existsMatAffiInvOnlySup(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		String matMsg = matCommonService.existsMatInvOnlySup(mapVo);
		return matMsg;
	}
	/**
	 * （代销）代销材料是否存在唯一供应商
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/existsMatAffiSupIncludeInv", method = RequestMethod.POST)
	@ResponseBody
	public String existsMatAffiSupIncludeInv(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		mapVo.put("by_sup_inv", MyConfig.getSysPara("04021"));
		
		String matMsg = matCommonService.existsMatAffiSupIncludeInv(mapVo);
		return matMsg;
	}
	
	/**
	 * （代销）代销材料在该供应商是否有库存
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/existsMatAffiSupIncludeInvAmount", method = RequestMethod.POST)
	@ResponseBody
	public String existsMatAffiSupIncludeInvAmount(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		mapVo.put("by_sup_inv", MyConfig.getSysPara("04021"));
		
		String matMsg = matCommonService.existsMatAffiSupIncludeInvAmount(mapVo);
		return matMsg;
	}
	
	
	/**
	 * 退货时看看供应商是否入库该材料（常备）
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/existsMatInSupIsInv", method = RequestMethod.POST)
	@ResponseBody
	public String existsMatInSupIsInv(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		mapVo.put("by_sup_inv", MyConfig.getSysPara("04021"));
		
		String matMsg = matCommonService.existsMatInSupIsInv(mapVo);
		return matMsg;
	}
	/**
	 * 退货时看看供应商是否入库该材料
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/existsMatAffiSupIsInv", method = RequestMethod.POST)
	@ResponseBody
	public String existsMatAffiSupIsInv(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		mapVo.put("by_sup_inv", MyConfig.getSysPara("04021"));
		
		String matMsg = matCommonService.existsMatAffiSupIsInv(mapVo);
		return matMsg;
	}
	/**
	 * @Description 判断批号有效日期是否一致
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/queryMatInvBatchInva", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMatInvBatchInva(@RequestParam Map<String, Object> mapVo, Model mode)throws Exception {
		
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		String matJson = matCommonService.queryMatInvBatchInva(mapVo);

		return JSONObject.parseObject(matJson);
	}

	/**
	 * @Description 判断批号灭菌日期是否一致
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/queryMatInvBatchDisinfect", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMatInvBatchDisinfect(@RequestParam Map<String, Object> mapVo, Model mode)throws Exception {
		
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		String matJson = matCommonService.queryMatInvBatchDisinfect(mapVo);

		return JSONObject.parseObject(matJson);
	}
	/**
	 * @Description 判断批号单价是否一致
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/queryMatInvBatchPrice", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMatInvBatchPrice(@RequestParam Map<String, Object> mapVo, Model mode)throws Exception {
		
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		String matJson = matCommonService.queryMatInvBatchPrice(mapVo);
		
		return JSONObject.parseObject(matJson);
	}
	/**
	 * @Description 采购计划 材料列表
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/queryMatInvListByPur", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMatInvListByPur(@RequestParam Map<String, Object> mapVo, Model mode)throws Exception {
		
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
			mapVo.put("mod_code", "04");
		}
		String matJson = matCommonService.queryMatInvListByPur(getPage(mapVo));
		
		return JSONObject.parseObject(matJson);
	}
	
	/**
	 * 查询 判断材料证件过期 
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/queryMatCertDate", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMatCertDate(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		String matMsg = matCommonService.queryMatCertDate(mapVo);
		return JSONObject.parseObject(matMsg);
	}

	//耐用品材料字典列表
	@RequestMapping(value = "/hrp/mat/queryMatDuraInvList", method = RequestMethod.POST)
	@ResponseBody
	public String queryMatDuraInvList(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		String matInvList = matCommonService.queryMatDuraInvList(getPage(mapVo));
		return matInvList;
	}

	//耐用品库房材料字典列表
	@RequestMapping(value = "/hrp/mat/queryMatDuraStoreInvList", method = RequestMethod.POST)
	@ResponseBody
	public String queryMatDuraStoreInvList(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		String matInvList = matCommonService.queryMatDuraStoreInvList(getPage(mapVo));
		return matInvList;
	}

	//耐用品库房材料条码字典列表
	@RequestMapping(value = "/hrp/mat/queryMatDuraStoreInvBarList", method = RequestMethod.POST)
	@ResponseBody
	public String queryMatDuraStoreInvBarList(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		String matInvList = matCommonService.queryMatDuraStoreInvBarList(getPage(mapVo));
		return matInvList;
	}

	//耐用品科室材料字典列表
	@RequestMapping(value = "/hrp/mat/queryMatDuraDeptInvList", method = RequestMethod.POST)
	@ResponseBody
	public String queryMatDuraDeptInvList(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		String matInvList = matCommonService.queryMatDuraDeptInvList(getPage(mapVo));
		return matInvList;
	}

	

	//耐用品科室材料字典列表
	@RequestMapping(value = "/hrp/mat/queryMatDuraDeptInvListNew", method = RequestMethod.POST)
	@ResponseBody
	public String queryMatDuraDeptInvListNew(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		String matInvList = matCommonService.queryMatDuraDeptInvListNew(getPage(mapVo));
		return matInvList;
	}
	//耐用品科室材料条码字典列表
	@RequestMapping(value = "/hrp/mat/queryMatDuraDeptInvBarList", method = RequestMethod.POST)
	@ResponseBody
	public String queryMatDuraDeptInvBarList(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		String matInvList = matCommonService.queryMatDuraDeptInvBarList(getPage(mapVo));
		return matInvList;
	}

	//普通材料扫码出库返回对应材料列表
	@RequestMapping(value = "/hrp/mat/queryMatOutInvListByBar", method = RequestMethod.POST)
	@ResponseBody
	public String queryMatOutInvListByBar(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		String matInvList;
		try {
			matInvList = matCommonService.queryMatOutInvListByBar(mapVo);
		} catch (Exception e) {
			matInvList = "{\"error\":\""+e.getMessage()+"\"}";
		}
		return matInvList;
	}

	//代销材料扫码出库返回对应材料列表
	@RequestMapping(value = "/hrp/mat/queryMatAffiOutInvListByBar", method = RequestMethod.POST)
	@ResponseBody
	public String queryMatAffiOutInvListByBar(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		String matInvList;
		try {
			matInvList = matCommonService.queryMatAffiOutInvListByBar(mapVo);
		} catch (Exception e) {
			matInvList = "{\"error\":\""+e.getMessage()+"\"}";
		}
		return matInvList;
	}

	//普通材料退库查询科室已领用材料列表
	@RequestMapping(value = "/hrp/mat/queryMatDeptStockInvList", method = RequestMethod.POST)
	@ResponseBody
	public String queryMatDeptStockInvList(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		String matInvList;
		try {
			matInvList = matCommonService.queryMatDeptStockInvList(mapVo);
		} catch (Exception e) {
			matInvList = "{\"error\":\""+e.getMessage()+"\"}";
		}
		return matInvList;
	}
	/**
	 * 仓库是否控制科室申请数量不能大于仓库库存
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/queryMatStoreByCode", method = RequestMethod.POST)
	@ResponseBody
	public String queryMatStoreByCode(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		String matMsg = matCommonService.queryMatStoreByCode(mapVo);
		return matMsg;
	}
	
		//  代销 科室退库
			@RequestMapping(value = "/hrp/mat/queryMatAffiOutBackInvList", method = RequestMethod.POST)
			@ResponseBody
			public String queryMatAffiOutBackInvList(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
				if (mapVo.get("group_id") == null) {
					mapVo.put("group_id", SessionManager.getGroupId());
				}
				if (mapVo.get("hos_id") == null) {
					mapVo.put("hos_id", SessionManager.getHosId());
				}
				if (mapVo.get("copy_code") == null) {
					mapVo.put("copy_code", SessionManager.getCopyCode());
				}
				//System.out.println(mapVo);
				String matInvList = matCommonService.queryMatAffiOutBackInvList(getPage(mapVo));
				return matInvList;
			}
	/**
	 * 获取相同材料统一批次的最大过期时间
	 * @param mapVo
	 * @return
	 */
	@RequestMapping(value="/hrp/mat/getInvaTimeByBatchNo",method=RequestMethod.POST)
	@ResponseBody
	public String getInvaTimeByBatchNo(@RequestParam Map<String, Object> mapVo){
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		String InvaTime = matCommonService.getInvaTimeByBatchNo(getPage(mapVo));
		return InvaTime;
	}
	/**
	 * 科室配套表筛选材料 	
	 * @param mapVo
	 * @return
	 */
	@RequestMapping(value="/hrp/mat/queryMatInvListByMatch",method=RequestMethod.POST)
	@ResponseBody
	public String queryMatInvListByMatch(@RequestParam Map<String, Object> mapVo){
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		String InvaTime = matCommonService.queryMatInvListByMatch(getPage(mapVo));
		return InvaTime;
	}
	/**
	 * 获取日期和供应商对应的付款协议编码字符串
	 * @param mapVo
	 * @return
	 */
	@RequestMapping(value="/hrp/mat/getMatPactFkxyInfo",method=RequestMethod.POST)
	@ResponseBody
	public String getMatPactFkxyInfo(@RequestParam Map<String, Object> mapVo){
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		String PactFkxyStr = matCommonService.getMatPactFkxyInfo(getPage(mapVo));
		return PactFkxyStr;
	}
}
