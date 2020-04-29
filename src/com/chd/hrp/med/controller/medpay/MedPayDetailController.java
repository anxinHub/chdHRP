/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.med.controller.medpay;
import java.io.IOException;
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
import com.chd.base.SessionManager;
import com.chd.base.util.ChdJson;
import com.chd.base.util.Plupload;
import com.chd.base.util.StringTool;
import com.chd.base.util.UploadUtil;
import com.chd.hrp.med.entity.MedPayDetail;
import com.chd.hrp.med.service.medpay.MedPayDetailService;
/**
 * 
 * @Description:
 * 保存一个付款单对应的发票
 * @Table:
 * MED_PAY_DETAIL
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 

@Controller
public class MedPayDetailController extends BaseController{
	
	private static Logger logger = Logger.getLogger(MedPayDetailController.class);
	
	//引入Service服务
	@Resource(name = "medPayDetailService")
	private final MedPayDetailService medPayDetailService = null;
   
	/**
	 * @Description 
	 * 主页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/med/medpaydetail/medPayDetailMainPage", method = RequestMethod.GET)
	public String medPayDetailMainPage(Model mode) throws Exception {

		return "hrp/med/medpaydetail/medPayDetailMain";

	}

	/**
	 * @Description 
	 * 添加页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/med/medpaydetail/medPayDetailAddPage", method = RequestMethod.GET)
	public String medPayDetailAddPage(Model mode) throws Exception {

		return "hrp/med/medpaydetail/medPayDetailAdd";

	}

	/**
	 * @Description 
	 * 添加数据 保存一个付款单对应的发票
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/med/medpaydetail/addMedPayDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addMedPayDetail(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if(mapVo.get("group_id") == null){
		mapVo.put("group_id", SessionManager.getGroupId());
		}
		
		if(mapVo.get("hos_id") == null){
		mapVo.put("hos_id", SessionManager.getHosId());
		}
		
		if(mapVo.get("copy_code") == null){
    	mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		if(mapVo.get("acct_year") == null){
    	mapVo.put("acct_year", SessionManager.getAcctYear());
		}
		
       
		String medPayDetailJson = medPayDetailService.addMedPayDetail(mapVo);

		return JSONObject.parseObject(medPayDetailJson);
		
	}
/**
	 * @Description 
	 * 更新跳转页面 保存一个付款单对应的发票
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/med/medpaydetail/medPayDetailUpdatePage", method = RequestMethod.GET)
	public String medPayDetailUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
	    if(mapVo.get("group_id") == null){
		mapVo.put("group_id", SessionManager.getGroupId());
		}
		
		if(mapVo.get("hos_id") == null){
		mapVo.put("hos_id", SessionManager.getHosId());
		}
		
		if(mapVo.get("copy_code") == null){
        mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		if(mapVo.get("acct_year") == null){
        mapVo.put("acct_year", SessionManager.getAcctYear());
		}
		
		MedPayDetail medPayDetail = new MedPayDetail();
    
		medPayDetail = medPayDetailService.queryMedPayDetailByCode(mapVo);
		
		mode.addAttribute("group_id", medPayDetail.getGroup_id());
		mode.addAttribute("hos_id", medPayDetail.getHos_id());
		mode.addAttribute("copy_code", medPayDetail.getCopy_code());
		mode.addAttribute("pay_id", medPayDetail.getPay_id());
		mode.addAttribute("pay_detail_id", medPayDetail.getPay_detail_id());
		mode.addAttribute("bill_id", medPayDetail.getBill_id());
		mode.addAttribute("bill_detail_id", medPayDetail.getBill_detail_id());
		mode.addAttribute("bill_money", medPayDetail.getBill_money());
		mode.addAttribute("payed_money", medPayDetail.getPayed_money());
		mode.addAttribute("pay_money", medPayDetail.getPay_money());
		
		return "hrp/med/medpaydetail/medPayDetailUpdate";
	}
		
	/**
	 * @Description 
	 * 更新数据 保存一个付款单对应的发票
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/med/medpaydetail/updateMedPayDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateMedPayDetail(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		

		if(mapVo.get("group_id") == null){
		mapVo.put("group_id", SessionManager.getGroupId());   
		}

		if(mapVo.get("hos_id") == null){
		mapVo.put("hos_id", SessionManager.getHosId());   
		}

		if(mapVo.get("copy_code") == null){
		mapVo.put("copy_code", SessionManager.getCopyCode());   
		}
	  
		String medPayDetailJson = medPayDetailService.updateMedPayDetail(mapVo);
		
		return JSONObject.parseObject(medPayDetailJson);
	}
	
	/**
	 * @Description 
	 * 更新数据 保存一个付款单对应的发票
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/med/medpaydetail/addOrUpdateMedPayDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addOrUpdateMedPayDetail(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String medPayDetailJson ="";
		
		List<Map> detail = ChdJson.fromJsonAsList(Map.class, mapVo.get("ParamVo").toString());
		
		for (Map<String, Object> detailVo : detail) {

		if(detailVo.get("group_id") == null){
		detailVo.put("group_id", SessionManager.getGroupId());   
		}

		if(detailVo.get("hos_id") == null){
		detailVo.put("hos_id", SessionManager.getHosId());   
		}

		if(detailVo.get("copy_code") == null){
		detailVo.put("copy_code", SessionManager.getCopyCode());   
		}
	  
		medPayDetailJson = medPayDetailService.addOrUpdateMedPayDetail(detailVo);
		
		}
		return JSONObject.parseObject(medPayDetailJson);
	}
	
	/**
	 * @Description 
	 * 删除数据 保存一个付款单对应的发票
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/med/medpaydetail/deleteMedPayDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteMedPayDetail(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		
			for ( String id: paramVo.split(",")) {
				
				Map<String, Object> mapVo=new HashMap<String, Object>();
				
				String[] ids=id.split("@");
				
				//表的主键
				mapVo.put("group_id", ids[0])   ;
				mapVo.put("hos_id", ids[1])   ;
				mapVo.put("copy_code", ids[2])   ;
				mapVo.put("pay_id", ids[3])   ;
				mapVo.put("pay_detail_id", ids[4]);
				
	      listVo.add(mapVo);
	      
	    }
	    
		String medPayDetailJson = medPayDetailService.deleteBatchMedPayDetail(listVo);
			
	  return JSONObject.parseObject(medPayDetailJson);
			
	}
	
	/**
	 * @Description 
	 * 查询数据 保存一个付款单对应的发票
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/med/medpaydetail/queryMedPayDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMedPayDetail(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
	    if(mapVo.get("group_id") == null){
			
		mapVo.put("group_id", SessionManager.getGroupId());
		
		}
		
		if(mapVo.get("hos_id") == null){
			
		mapVo.put("hos_id", SessionManager.getHosId());
		
		}
		
		if(mapVo.get("copy_code") == null){
			
		mapVo.put("copy_code", SessionManager.getCopyCode());
        
		}
		if(mapVo.get("acct_year") == null){
			
		mapVo.put("acct_year", SessionManager.getAcctYear());
        
		}
		String medPayDetail = medPayDetailService.queryMedPayDetail(getPage(mapVo));

		return JSONObject.parseObject(medPayDetail);
		
	}
	
  /**
	 * @Description 
	 * 导入跳转页面 保存一个付款单对应的发票
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/med/medpaydetail/medPayDetailImportPage", method = RequestMethod.GET)
	public String medPayDetailImportPage(Model mode) throws Exception {

		return "hrp/med/medpaydetail/medPayDetailImport";

	}
	/**
	 * @Description 
	 * 下载导入模版 保存一个付款单对应的发票
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	 @RequestMapping(value="hrp/med/medpaydetail/downTemplate", method = RequestMethod.GET)  
	 public String downTemplate(Plupload plupload,HttpServletRequest request,
	    		HttpServletResponse response,Model mode) throws IOException { 
	    			
	    printTemplate(request,response,"med\\downTemplate","保存一个付款单对应的发票.xls");
	    
	    return null; 
	 }
	 /**
	 * @Description 
	 * 导入数据 保存一个付款单对应的发票
	 * @param  plupload
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	@RequestMapping(value="/hrp/med/medpaydetail/readMedPayDetailFiles",method = RequestMethod.POST)  
    public String readMedPayDetailFiles(Plupload plupload,HttpServletRequest request,
    		HttpServletResponse response,Model mode) throws IOException { 
			 
		List<MedPayDetail> list_err = new ArrayList<MedPayDetail>();
		
		List<String[]> list = UploadUtil.readFile(plupload, request, response);
		
		try {
			for (int i = 1; i < list.size(); i++) {
				
				StringBuffer err_sb = new StringBuffer();
				
				MedPayDetail medPayDetail = new MedPayDetail();
				
				String temp[] = list.get(i);// 行
				Map<String, Object> mapVo = new HashMap<String, Object>();
				
		    		mapVo.put("group_id", SessionManager.getGroupId());   
		    		         
					 
		    		mapVo.put("hos_id", SessionManager.getHosId());   
		    		         
					 
		    		mapVo.put("copy_code", SessionManager.getCopyCode());   
		    		         
					 
					if (StringTool.isNotBlank(temp[3])) {
						
					medPayDetail.setPay_id(Long.valueOf(temp[3]));
		    		mapVo.put("pay_id", temp[3]);
					
					} else {
						
						err_sb.append("付款单ID为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[4])) {
						
					medPayDetail.setPay_detail_id(Long.valueOf(temp[4]));
		    		mapVo.put("pay_detail_id", temp[4]);
					
					} else {
						
						err_sb.append("付款单明细ID为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[5])) {
						
					medPayDetail.setBill_id(Long.valueOf(temp[5]));
		    		mapVo.put("bill_id", temp[5]);
					
					} else {
						
						err_sb.append("发票ID为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[6])) {
						
					medPayDetail.setBill_detail_id(Long.valueOf(temp[6]));
		    		mapVo.put("bill_detail_id", temp[6]);
					
					} else {
						
						err_sb.append("发票明细ID为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[7])) {
						
					medPayDetail.setBill_money(Double.valueOf(temp[7]));
		    		mapVo.put("bill_money", temp[7]);
					
					} else {
						
						err_sb.append("发票金额为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[8])) {
						
					medPayDetail.setPayed_money(Double.valueOf(temp[8]));
		    		mapVo.put("payed_money", temp[8]);
					
					} else {
						
						err_sb.append("已付金额为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[9])) {
						
					medPayDetail.setPay_money(Double.valueOf(temp[9]));
		    		mapVo.put("pay_money", temp[9]);
					
					} else {
						
						err_sb.append("付款金额为空  ");
						
					}
					 
					
				MedPayDetail data_exc_extis = medPayDetailService.queryMedPayDetailByCode(mapVo);
				
				if (data_exc_extis != null) {
					
					err_sb.append("数据已经存在！ ");
					
				}
				if (err_sb.toString().length() > 0) {
					
					medPayDetail.setError_type(err_sb.toString());
					
					list_err.add(medPayDetail);
					
				} else {
			  
					String dataJson = medPayDetailService.addMedPayDetail(mapVo);
					
				}
				
			}
			
		} catch (DataAccessException e) {
			
			MedPayDetail data_exc = new MedPayDetail();
			
			data_exc.setError_type("导入系统出错");
			
			list_err.add(data_exc);
			
		}
		
		response.getWriter().print(ChdJson.toJson(list_err, list_err.size()));
		
		return null;
		
    }  
   /**
	 * @Description 
	 * 批量添加数据 保存一个付款单对应的发票
	 * @param  plupload
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	@RequestMapping(value = "/hrp/med/medpaydetail/addBatchMedPayDetail", method = RequestMethod.POST)
	@ResponseBody
    public Map<String, Object> addBatchMedPayDetail(@RequestParam(value = "ParamVo") String paramVo, Model mode)throws Exception{
				
		List<MedPayDetail> list_err = new ArrayList<MedPayDetail>();
		
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
			
			MedPayDetail medPayDetail = new MedPayDetail();
			
			JSONObject jsonObj = JSONObject.parseObject(it.next().toString());
			
			
					
					
					
					if (StringTool.isNotBlank(jsonObj.get("pay_id"))) {
						
					medPayDetail.setPay_id(Long.valueOf((String)jsonObj.get("pay_id")));
		    		mapVo.put("pay_id", jsonObj.get("pay_id"));
		    		} else {
						
						err_sb.append("付款单ID为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("pay_detail_id"))) {
						
					medPayDetail.setPay_detail_id(Long.valueOf((String)jsonObj.get("pay_detail_id")));
		    		mapVo.put("pay_detail_id", jsonObj.get("pay_detail_id"));
		    		} else {
						
						err_sb.append("付款单明细ID为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("bill_id"))) {
						
					medPayDetail.setBill_id(Long.valueOf((String)jsonObj.get("bill_id")));
		    		mapVo.put("bill_id", jsonObj.get("bill_id"));
		    		} else {
						
						err_sb.append("发票ID为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("bill_detail_id"))) {
						
					medPayDetail.setBill_detail_id(Long.valueOf((String)jsonObj.get("bill_detail_id")));
		    		mapVo.put("bill_detail_id", jsonObj.get("bill_detail_id"));
		    		} else {
						
						err_sb.append("发票明细ID为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("fav_money"))) {
						
					medPayDetail.setPay_money(Double.valueOf((String)jsonObj.get("pay_money")));
		    		mapVo.put("pay_money", jsonObj.get("pay_money"));
		    		} else {
						
						err_sb.append("发票金额为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("payed_money"))) {
						
					medPayDetail.setPay_money(Double.valueOf((String)jsonObj.get("payed_money")));
		    		mapVo.put("payed_money", jsonObj.get("payed_money"));
		    		} else {
						
						err_sb.append("已付金额为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("pay_money"))) {
						
					medPayDetail.setPay_money(Double.valueOf((String)jsonObj.get("pay_money")));
		    		mapVo.put("pay_money", jsonObj.get("pay_money"));
		    		} else {
						
						err_sb.append("付款金额为空  ");
						
					}
					
					
				MedPayDetail data_exc_extis = medPayDetailService.queryMedPayDetailByCode(mapVo);
				
				if (data_exc_extis != null) {
					
					err_sb.append("编码已经存在！ ");
					
				}
				if (err_sb.toString().length() > 0) {
					
					medPayDetail.setError_type(err_sb.toString());
					
					list_err.add(medPayDetail);
					
				} else {
			  
					String dataJson = medPayDetailService.addMedPayDetail(mapVo);
					
				}
				
			}
			
		} catch (DataAccessException e) {
			
			MedPayDetail data_exc = new MedPayDetail();
			
			list_err.add(data_exc);
			
			return JSONObject.parseObject("{\"msg\":\"导入系统出错,请重新导入.\",\"state\":\"err\"}");
			
		}
			
		if (list_err.size() > 0) {
			
			return JSONObject.parseObject(ChdJson.toJson(list_err,list_err.size()));
			
		} else {
			
			return JSONObject.parseObject("{\"msg\":\"导入成功.\",\"state\":\"true\"}");
			
		}
		
    }
    
}

