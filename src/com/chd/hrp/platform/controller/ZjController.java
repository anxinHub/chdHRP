/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */

package com.chd.hrp.platform.controller;

import java.io.IOException;
import java.text.DateFormat;
import java.util.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
 

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.chd.base.BaseController;
import com.chd.base.MyConfig;
import com.chd.base.SessionManager;
import com.chd.base.util.ChdJson;
import com.chd.base.util.Plupload;
import com.chd.base.util.StringTool;
import com.chd.base.util.UploadUtil;
import com.chd.hrp.mat.dao.info.basic.MatTypeMapper;
import com.chd.hrp.mat.entity.MatType;
import com.chd.hrp.mat.service.base.MatCommonService;
import com.chd.hrp.mat.service.info.basic.MatTypeDictService;
import com.chd.hrp.mat.service.info.basic.MatTypeService;
import com.chd.hrp.mat.service.order.init.MatOrderInitService;
import com.chd.hrp.mat.service.purchase.make.MatPurMainService;
import com.chd.hrp.mat.serviceImpl.info.basic.MatTypeServiceImpl;
import com.chd.hrp.platform.dao.ZjMapper;
import com.chd.hrp.platform.entity.Zj;
import com.chd.hrp.platform.serviceImpl.ZjServiceImpl;

/**
 * 
 * @Description: 04103 物资分类字典
 * @Table: MAT_TYPE
 * @Author: bell
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Controller
public class ZjController extends BaseController {      

	private static Logger logger = Logger.getLogger(ZjController.class);   

	@Resource(name="zjService")
	private final ZjServiceImpl zjService=null;
	@Resource(name = "matOrderInitService")
	private final MatOrderInitService matOrderInitService = null;
	@Resource(name = "zjMapper")
	private final ZjMapper zjMapper = null;
	
	
	
	/**
	 * 订单发送至浙江省平台
	 * @param paramVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/order/audit/sendOrderToProvincePlatfrom", method = RequestMethod.POST)
	@ResponseBody
	public  Map<String, Object> sendOrderToProvincePlatfrom(@RequestParam Map<String, Object> map, Model mode) throws Exception {
		
		
		String orderId=map.get("orderId").toString();
		
		Map<String, Object> mapVo=new HashMap<String, Object>();
		//表的主键
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("user_id", SessionManager.getUserId());
		mapVo.put("order_id", orderId);
		
		
		String matOrderMain=null;
		try{
			matOrderMain = zjService.sendOrder(mapVo);
			//zjService.getProcureCatalog();
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			matOrderMain="{\"error\":\""+e.getMessage()+"\"}";
		}
		
		return JSONObject.parseObject(matOrderMain);
	}
	
	
	/**
	 * 订单配送页面跳转
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/order/init/matOrderPlatformDeliveryMainPage", method = RequestMethod.GET)
	public String getDelivery(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		if(mapVo.get("show_history") == null){
			mapVo.put("show_history", MyConfig.getSysPara("03001"));
		}
		
		Map<String, Object> matOrderMain = matOrderInitService.queryByCode(mapVo);
		
		
		mode.addAttribute("matOrderMain", matOrderMain);
		
		mode.addAttribute("order_id", mapVo.get("order_id"));
		//is_notice值  订单未发送 0 发送供应商平台 1 发送省平台未获取配送信息 2 发送省平台获取了配送信息 3
		//当订单发送省平台,但是为获取
		if ("2".equals(String.valueOf(mapVo.get("is_notice")))) {
			zjService.getDelivery(mapVo);
		}
		
		return "/hrp/mat/order/init/matOrderPlatformDeliveryMain";

	}
	
	
	
	
	/**
	 * 订单配送明细查询
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/storage/query/queryPlatformDelivery", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryPlatformDelivery(@RequestParam Map<String, Object> mapVo, Model mode)throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("user_id", SessionManager.getUserId());
		mapVo.put("show_history", MyConfig.getSysPara("03001"));
		
		
		String queryStockDetail = zjService.queryPlatformDelivery(mapVo);

		return JSONObject.parseObject(queryStockDetail);

	}

	
	

	/**
	 * 通过订单配送信息生成入库单
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/storage/query/createInByPlatfromOrder", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> createInByPlatfromOrder(@RequestParam Map<String, Object> mapVo, Model mode)throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("user_id", SessionManager.getUserId());
		mapVo.put("show_history", MyConfig.getSysPara("03001"));
		
		String createInByPlatfromOrder = zjService.createInByPlatfromOrder(mapVo);

		return JSONObject.parseObject(createInByPlatfromOrder);

	}
	
	
	
	/**
	 * 退货单发送至浙江省平台
	 * @param paramVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/storage/back/sendBackToProvincePlatfrom", method = RequestMethod.POST)
	@ResponseBody
	public  Map<String, Object> sendBackToProvincePlatfrom(@RequestParam Map<String, Object> map, Model mode) throws Exception {

		Integer in_id=Integer.parseInt(map.get("in_id").toString());//退库主ID;
		
		Map<String, Object> mapVo=new HashMap<String, Object>();
		//表的主键
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("user_id", SessionManager.getUserId());
		mapVo.put("in_id", in_id);
		
		String matBackMain=null;
		try{
			matBackMain = zjService.sendMatBack(mapVo);
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			matBackMain="{\"error\":\""+e.getMessage()+"\"}";
		}
		return JSONObject.parseObject(matBackMain);
	}
	
	/**
	 * 省平台中获取退货信息
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/storage/back/queryMatBackDetailBySpt", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMatBackDetailBySpt(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		String matBackDetail = zjService.getMatBackDetail(mapVo);
		
		return JSONObject.parseObject(matBackDetail);
	}
	
	/**
	 * 支付单发送至浙江省平台
	 * @param paramVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/matpay/matpaymain/ppaySend", method = RequestMethod.POST)
	@ResponseBody
	public  Map<String, Object> ppaySend(@RequestParam Map<String, Object> map, Model mode) throws Exception {
		
		String payid=map.get("payid").toString();
		Map<String, Object> mapVo=new HashMap<String, Object>();
		
		//表的主键
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("user_id", SessionManager.getUserId());
		mapVo.put("pay_id", payid);
		String matOrderMain=null;
		try{
			matOrderMain = zjService.sendpay(mapVo);
			//zjService.getProcureCatalog();
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			matOrderMain="{\"error\":\""+e.getMessage()+"\"}";
		}
		
		return JSONObject.parseObject(matOrderMain);
	}
	
	
	
	
	/**
	 * @Description 主页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/platform/matInvRelaMainPage", method = RequestMethod.GET)
	public String matStoreInvMainPage(Model mode) throws Exception {

		return "hrp/mat/platform/matInvRelaMain";
	}
	/**
	 * @Description 添加页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/platform/matInvRelaAddPage", method = RequestMethod.GET)
	public String matInvRelaAddPage(Model mode) throws Exception {

		return "hrp/mat/platform/matInvRelaAdd";

	}
	 
	/**
	 * 省平台材料对应关系    查询
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/platform/queryMatInvRela", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMatInvRela(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
			
		mapVo.put("group_id", SessionManager.getGroupId());
			
		mapVo.put("hos_id", SessionManager.getHosId());
			
		mapVo.put("copy_code", SessionManager.getCopyCode());
			
		if(mapVo.get("inv_code") != null){
			mapVo.put("inv_code", mapVo.get("inv_code").toString().toUpperCase()) ;
		}
		if(mapVo.get("goodsid") != null){
			mapVo.put("goodsid", mapVo.get("goodsid").toString().toUpperCase()) ;
		}
		String matInvRela = zjService.queryMatInvRela(getPage(mapVo));
			
		return JSONObject.parseObject(matInvRela);
		
	}
	/**
	 * 省平台材料对应关系    添加
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/platform/addMatInvRela", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addMatInvRela(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		
		mapVo.put("hos_id", SessionManager.getHosId());
		
		mapVo.put("copy_code", SessionManager.getCopyCode());
			
		String matInvRela = zjService.addMatInvRela(mapVo);
			
		return JSONObject.parseObject(matInvRela);
		
		
	}
	/**
	 * 省平台材料对应关系    删除
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/platform/deleteMatInvRela", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteMatInvRela(@RequestParam(value = "ParamVo") String paramVo, Model mode) throws Exception {
 
		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();

		for (String id : paramVo.split(",")) {

			Map<String, Object> mapVo = new HashMap<String, Object>();

			String[] ids = id.split("@");

			// 表的主键
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());
			
			mapVo.put("inv_id", ids[0]);
			mapVo.put("goodsid", ids[1]);

			listVo.add(mapVo);

		}
		
		String matInvRela = zjService.deleteBatchMatInvRela(listVo);
		
		return JSONObject.parseObject(matInvRela);
		
		
	}
	
	/**
	 * 省平台材料对应关系  修改页面跳转
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/platform/updateMatInvRelaPage", method = RequestMethod.GET)
	public String updateMatInvRelaPage(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		 
		mapVo.put("group_id", SessionManager.getGroupId());
	 
		mapVo.put("hos_id", SessionManager.getHosId());
	 
		mapVo.put("copy_code", SessionManager.getCopyCode());
		 
		Map<String, Object> matInvRela = zjService.queryMatInvRelaByCode(mapVo);
		
		mode.addAttribute("matInvRela", matInvRela);
		
		return "hrp/mat/platform/matInvRelaUpdate";

	}
	
	/**
	 * 省平台材料对应关系   更新
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/platform/updateMatInvRela", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateMatInvRela(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		
		mapVo.put("hos_id", SessionManager.getHosId());
		
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		String matInvRela = zjService.updateMatInvRela(mapVo);
		
		return JSONObject.parseObject(matInvRela);
		
		
	}
	/**
	 * 省平台材料对应关系  同步
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/platform/saveSyncMatInvRela", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveSyncMatInvRela(@RequestParam(value = "endDate") String endDate, Model mode) throws Exception {
		
		Map<String,Object> mapVo = new HashMap<String, Object>();
		
		mapVo.put("group_id", SessionManager.getGroupId());
		
		mapVo.put("hos_id", SessionManager.getHosId());
		
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		mapVo.put("endDate", endDate);
		 
		String matInvRela =  zjService.getProcureCatalog(mapVo);
		
		return JSONObject.parseObject(matInvRela);
		
		
	}
	
	/**
	 * 省平台材料对应关系    HRP材料 下拉框查询数据
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/platform/queryMatInvHrpBySelect", method = RequestMethod.POST)
	@ResponseBody
	public String queryMatInvHrpBySelect(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		 
			mapVo.put("group_id", SessionManager.getGroupId());
		 
			mapVo.put("hos_id", SessionManager.getHosId());
		 
			mapVo.put("copy_code", SessionManager.getCopyCode());
		 
		String matInvRela = zjService.queryMatInvHrpBySelect(mapVo);
		
		return matInvRela;
		
	}
	/**
	 * 省平台材料对应关系    省平台 材料 下拉框查询数据
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/platform/queryMatInvSptBySelect", method = RequestMethod.POST)
	@ResponseBody
	public String queryMatInvSptBySelect(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		
		mapVo.put("hos_id", SessionManager.getHosId());
		
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		String matInvRela = zjService.queryMatInvSptBySelect(mapVo);
		
		return matInvRela;
		
	}
	
	/**
	 * 省平台那边状态是已撤销(09)的状态下,才能操作撤销按钮
	 */
	@RequestMapping(value = "/hrp/mat/order/audit/revokeOrderToProvincePlatfrom", method = RequestMethod.POST)
	@ResponseBody
	public  Map<String, Object> revokeOrderToProvincePlatfrom(@RequestParam Map<String, Object> map, Model mode) throws Exception {
		
		
		String orderId=map.get("orderId").toString();
		
		Map<String, Object> mapVo=new HashMap<String, Object>();
		//表的主键
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("user_id", SessionManager.getUserId());
		mapVo.put("order_id", orderId);
		
		
		String matOrderMain=null;
		try{
			matOrderMain = zjService.getOrderDetailRevoke(mapVo);
			//zjService.getProcureCatalog();
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			matOrderMain="{\"error\":\""+e.getMessage()+"\"}";
		}
		
		return JSONObject.parseObject(matOrderMain);
	}
	
	/**
	  * @Description 导入跳转页面 省平台对应关系
	  * @param mode
	  * @return String
	  * @throws Exception
	  */
	 @RequestMapping(value = "/hrp/mat/platform/matPlatFormImportPage", method = RequestMethod.GET)
	 public String matPlatFormImportPage(Model mode) throws Exception {

	  return "hrp/mat/platform/matInvRelaImport";

	 }
	
	
	 /**
	  * 导入保存
	  * @param paramVo
	  * @param mode
	  * @return
	  * @throws Exception
	  */
	 @RequestMapping(value = "/hrp/mat/platform/addMatInvRelaFiles", method = RequestMethod.POST)
	 @ResponseBody
     public Map<String, Object> addMatInvRelaFiles(@RequestParam(value = "ParamVo") String paramVo, Model mode)throws Exception{
	  
		  List<Zj> list_err = new ArrayList<Zj>();
		  
		  JSONArray json = JSONArray.parseArray(paramVo);
		 
		  Map<String, Object> mapVo = new HashMap<String, Object>();
		  
		   if (mapVo.get("group_id") == null) {
		    
		    mapVo.put("group_id", SessionManager.getGroupId());
		    
		   }
		   
		   if (mapVo.get("hos_id") == null) {
		    
		    mapVo.put("hos_id", SessionManager.getHosId());
		    
		   }
		   if (mapVo.get("copy_code") == null) {
		    
		    mapVo.put("copy_code", SessionManager.getCopyCode());
		    
		   }
		  
		   Iterator it = json.iterator();
		  
		   try {
		   
		   while (it.hasNext()) {
		    
		   StringBuffer err_sb = new StringBuffer();
		   
		   Zj zj = new Zj();
		   
		   JSONObject jsonObj = JSONObject.parseObject(it.next().toString());
		   
	
		     if (StringTool.isNotBlank(jsonObj.get("inv_code"))) {
		      
		      zj.setInv_code(jsonObj.get("inv_code").toString());
		      
		      mapVo.put("inv_code", jsonObj.get("inv_code").toString());
		        
		        } else {
		      
		      err_sb.append("HRP材料编码为空 ");
		      
		     }
		     
		     if (StringTool.isNotBlank(jsonObj.get("inv_name"))) {
		      
		     zj.setInv_name(jsonObj.get("inv_name").toString());
		     
		        mapVo.put("inv_name", jsonObj.get("inv_name").toString());
		        
		        } else {
		      
		      err_sb.append("HRP材料名称为空  ");
		      
		     }
		     
		     
		     
		     
		     if (StringTool.isNotBlank(jsonObj.get("goodsid"))) {
		      
		     zj.setGoodsid(jsonObj.get("goodsid").toString());
		     
		        mapVo.put("goodsid", jsonObj.get("goodsid"));
		        
		        } else {
		      
		      err_sb.append("省平台材料编码为空");
		      
		     }
		     
		     
		     if (StringTool.isNotBlank(jsonObj.get("goodsname"))) {
		      
		     zj.setGoodsname(jsonObj.get("goodsname").toString());
		     
		        mapVo.put("goodsname", jsonObj.get("goodsname").toString());
		        
		        } else {
		      
		      err_sb.append("省平台材料名称为空  ");
		      
		     }
		     
		     int data_exc_extis = zjMapper.queryMatInvRelaByCodes(mapVo);
		     
		     if(data_exc_extis >0){
		      
		      err_sb.append("省平台对应关系已经存在！ ");
		     }
		     
		     if (err_sb.toString().length() > 0) {
		 
		      zj.setError_type(err_sb.toString());
		 
		      list_err.add(zj);
		 
		     } else {
		      
		      //exitesMap.put(temp[0], mapVo);
		      
		      zjService.addMatInvRela(mapVo);
		 
		     }
		    
		   }
		  } catch (DataAccessException e) {
		   
		   Zj zj = new Zj();
		   
		   list_err.add(zj);
		   
		   return JSONObject.parseObject("{\"msg\":\"导入系统出错,请重新导入.\",\"state\":\"err\"}");
		   
		  }
		   
		  if (list_err.size() > 0) {
		   
		   return JSONObject.parseObject(ChdJson.toJson(list_err,list_err.size()));
		   
		  } else {
		   
		   return JSONObject.parseObject("{\"msg\":\"导入成功.\",\"state\":\"true\"}");
		   
		  }
	  
	    }
	 
	 /**
	  * @Description 下载导入模版  省平台对应关系(cjc)
	  * @param request
	  * @param response
	  * @param mode
	  * @return String
	  * @throws IOException
	  */
	 @RequestMapping(value = "/hrp/mat/platform/downTemplate", method = RequestMethod.GET)
	 public String downTemplate(Plupload plupload, HttpServletRequest request,
	   HttpServletResponse response, Model mode) throws IOException {

	  printTemplate(request, response, "mat\\downTemplate",
	    "省平台对应关系.xls");

	  return null;
	 }
	 
	 /**
	  * @Description 批量修改页面跳转
	  * @param mode
	  * @return String
	  * @throws Exception
	  */
	 @RequestMapping(value = "/hrp/mat/platform/matInvRelaUpdatePage", method = RequestMethod.GET)
	 public String matInvRelaUpdatePage(Model mode) throws Exception {

	  return "hrp/mat/platform/matInvRelaUpdateBaths";

	 }
	 
	//批量修改页面HRP材料查询
	 @RequestMapping(value = "/hrp/mat/platform/queryInvCode", method = RequestMethod.POST)
	 @ResponseBody
	 public Map<String, Object> queryInvCode(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

	  if (mapVo.get("group_id") == null) {

	   mapVo.put("group_id", SessionManager.getGroupId());

	  }

	  if (mapVo.get("hos_id") == null) {

	   mapVo.put("hos_id", SessionManager.getHosId());

	  }

	  if (mapVo.get("copy_code") == null) {

	   mapVo.put("copy_code", SessionManager.getCopyCode());

	  }
	  String invCode = zjService.queryInvCode(getPage(mapVo));

	  return JSONObject.parseObject(invCode);

	 }
	 
	//批量修改页面HRP材料查询
	 @RequestMapping(value = "/hrp/mat/platform/queryGoodsid", method = RequestMethod.POST)
	 @ResponseBody
	 public Map<String, Object> queryGoodsid(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

	  if (mapVo.get("group_id") == null) {

	   mapVo.put("group_id", SessionManager.getGroupId());

	  }

	  if (mapVo.get("hos_id") == null) {

	   mapVo.put("hos_id", SessionManager.getHosId());

	  }

	  if (mapVo.get("copy_code") == null) {

	   mapVo.put("copy_code", SessionManager.getCopyCode());

	  }
	  String goodsid = zjService.queryGoodsid(getPage(mapVo));

	  return JSONObject.parseObject(goodsid);

	 }
	 
	//HRP材料与省平台材料保存(更新)
	 @RequestMapping(value = "/hrp/mat/platform/addMatInvMaping", method = RequestMethod.POST)
	 @ResponseBody
	 public Map<String, Object> addMatInvMaping(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

	  List<Map<String, Object>> dataAddedBatch = new ArrayList<Map<String, Object>>();// 存放数据

	  String dataSection = String.valueOf(mapVo.get("maping_data"));

	  JSONArray dataSectionJson = JSONArray.parseArray(dataSection);

	  Iterator dataSectionJsonIt = dataSectionJson.iterator();

	  while (dataSectionJsonIt.hasNext()) {

	   JSONObject jsonObj = JSONObject.parseObject(dataSectionJsonIt.next().toString());

	   try {

	    /* ADD */

	    Map<String, Object> mapAdd = new HashMap<String, Object>();

	    mapAdd.put("group_id", SessionManager.getGroupId());

	    mapAdd.put("hos_id", SessionManager.getHosId());

	    mapAdd.put("copy_code", SessionManager.getCopyCode());
	    
	    mapAdd.put("inv_id", String.valueOf(jsonObj.get("inv_id")));
	    
	    mapAdd.put("inv_name", String.valueOf(jsonObj.get("inv_name")));

	    mapAdd.put("goodsid", String.valueOf(jsonObj.get("goodsid")));
	    
	    mapAdd.put("goodsname", String.valueOf(jsonObj.get("goodsname")));

	    dataAddedBatch.add(mapAdd);

	   } catch (Exception e) {
	    // TODO: handle exception
	    e.printStackTrace();

	   }

	  }
	  String addBatchMatInvSectionJson = "";

	  if (dataAddedBatch.size() > 0) {
	   zjMapper.deleteBatchMatInvRela(dataAddedBatch);
	   addBatchMatInvSectionJson = zjService.addBatchInvRelaMaping(dataAddedBatch);
	  }

	  String StringJson = "";
	  if (addBatchMatInvSectionJson != "") {

	   StringJson = addBatchMatInvSectionJson;

	  } else {

	   StringJson = "{\"msg\":\"数据没变化.\",\"state\":\"true\"}";
	  }

	  return JSONObject.parseObject(StringJson);

	 }
	 
	 /**
	  * @Description 
	  * 导入数据 
	  * @param  plupload
	  * @param  request
	  * @param  response
	  * @param  mode
	  * @return String
	  * @throws IOException
	 */
	 @RequestMapping(value="/hrp/mat/platform/readMatInvRelaFiles",method = RequestMethod.POST)  
	    public String readMatInvRelaFiles(Plupload plupload,HttpServletRequest request,
	      HttpServletResponse response,Model mode) throws IOException {   
	  
	  List<Zj> list_err = new ArrayList<Zj>();

	  //Map<String, Map<String,Object>> exitesMap = new HashMap<String, Map<String,Object>>();
	  
	  List<String[]> list = UploadUtil.readFile(plupload, request, response);

	  try {
	   for (int i = 1; i < list.size(); i++) {

	    StringBuffer err_sb = new StringBuffer();

	    Zj zj = new Zj();
	    

	    String temp[] = list.get(i);// 行
	    
	    if(temp == null){
	    	
	    	break;
	    }
	    
	    Map<String, Object> mapVo = new HashMap<String, Object>();

	    mapVo.put("group_id", SessionManager.getGroupId());

	    mapVo.put("hos_id", SessionManager.getHosId());

	    mapVo.put("copy_code", SessionManager.getCopyCode());

	    if (StringTool.isNotBlank(temp[0])) {

	     zj.setInv_code(temp[0]);
	     
	     mapVo.put("inv_code", temp[0]);

	    } else {

	     err_sb.append("HRP材料编码为空");

	    }

	    if (StringTool.isNotBlank(temp[1])) {

	     zj.setInv_name(temp[1]);
	     
	     mapVo.put("inv_name", temp[1]);

	    } else {

	     err_sb.append("HRP材料名称为空");

	    }


	    if (StringTool.isNotBlank(temp[2])) {

	     zj.setGoodsid(temp[2]);
	     
	     mapVo.put("goodsid", temp[2]);

	    } else {

	     err_sb.append("省平台材料编码为空");

	    }

	    if (StringTool.isNotBlank(temp[3])) {

	     zj.setGoodsname(temp[3]);
	     
	     mapVo.put("goodsname", temp[3]);

	    } else {

	     err_sb.append("省平台材料名称为空 ");

	    }
	    
	     int data_exc_extis = zjMapper.queryMatInvRelaByCodes(mapVo);
	    
	    if(data_exc_extis >0){
	     
	     err_sb.append("省平台对应关系已经存在！ ");
	    }
	    
	    if (err_sb.toString().length() > 0) {

	     zj.setError_type(err_sb.toString());

	     list_err.add(zj);

	    } else {
	     
	     //exitesMap.put(temp[0], mapVo);
	     
	     zjService.addMatInvRela(mapVo);

	    }
	    
	   }    
	   
	  } catch (DataAccessException e) {

	   Zj zj = new Zj();

	   zj.setError_type("导入系统出错");

	   list_err.add(zj);

	  }

	  response.getWriter().print(ChdJson.toJson(list_err, list_err.size()));

	  return null;
	  
	    }
	 
	
}
