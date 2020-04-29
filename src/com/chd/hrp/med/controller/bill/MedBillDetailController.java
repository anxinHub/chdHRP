/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.med.controller.bill;
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
import com.chd.hrp.med.entity.MedBillDetail;
import com.chd.hrp.med.service.bill.MedBillDetailService;
import com.chd.hrp.med.serviceImpl.bill.MedBillDetailServiceImpl;
/**
 * 
 * @Description:
 * 保存一个发票对应的入库单，及金额
 * @Table:
 * MED_BILL_DETAIL
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 

@Controller
public class MedBillDetailController extends BaseController{
	
	private static Logger logger = Logger.getLogger(MedBillDetailController.class);
	
	//引入Service服务
	@Resource(name = "medBillDetailService")
	private final MedBillDetailService medBillDetailService = null;
   
	/**
	 * @Description 
	 * 主页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/med/medbilldetail/medBillDetailMainPage", method = RequestMethod.GET)
	public String medBillDetailMainPage(Model mode) throws Exception {

		return "hrp/med/medbilldetail/medBillDetailMain";

	}

	/**
	 * @Description 
	 * 添加页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/med/medbilldetail/medBillDetailAddPage", method = RequestMethod.GET)
	public String medBillDetailAddPage(Model mode) throws Exception {

		return "hrp/med/medbilldetail/medBillDetailAdd";

	}

	/**
	 * @Description 
	 * 添加数据 保存一个发票对应的入库单，及金额
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/med/medbilldetail/addMedBillDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addMedBillDetail(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

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
		
       
		String medBillDetailJson = medBillDetailService.addMedBillDetail(mapVo);

		return JSONObject.parseObject(medBillDetailJson);
		
	}
/**
	 * @Description 
	 * 更新跳转页面 保存一个发票对应的入库单，及金额
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/med/medbilldetail/medBillDetailUpdatePage", method = RequestMethod.GET)
	public String medBillDetailUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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
		
		MedBillDetail medBillDetail = new MedBillDetail();
    
		medBillDetail = medBillDetailService.queryMedBillDetailByCode(mapVo);
		
		mode.addAttribute("group_id", medBillDetail.getGroup_id());
		mode.addAttribute("hos_id", medBillDetail.getHos_id());
		mode.addAttribute("copy_code", medBillDetail.getCopy_code());
		mode.addAttribute("bill_id", medBillDetail.getBill_id());
		mode.addAttribute("bill_detail_id", medBillDetail.getBill_detail_id());
		mode.addAttribute("in_id", medBillDetail.getIn_id());
		mode.addAttribute("in_detail_id", medBillDetail.getIn_detail_id());
		mode.addAttribute("bill_money", medBillDetail.getBill_money());
		mode.addAttribute("fav_money", medBillDetail.getFav_money());
		mode.addAttribute("in_money", medBillDetail.getIn_money());
		
		return "hrp/med/medbilldetail/medBillDetailUpdate";
	}
		
	/**
	 * @Description 
	 * 更新数据 保存一个发票对应的入库单，及金额
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/med/medbilldetail/updateMedBillDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateMedBillDetail(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		

		if(mapVo.get("group_id") == null){
		mapVo.put("group_id", SessionManager.getGroupId());   
		}

		if(mapVo.get("hos_id") == null){
		mapVo.put("hos_id", SessionManager.getHosId());   
		}

		if(mapVo.get("copy_code") == null){
		mapVo.put("copy_code", SessionManager.getCopyCode());   
		}
	  
		String medBillDetailJson = medBillDetailService.updateMedBillDetail(mapVo);
		
		return JSONObject.parseObject(medBillDetailJson);
	}
	
	/**
	 * @Description 
	 * 更新数据 保存一个发票对应的入库单，及金额
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/med/medbilldetail/addOrUpdateMedBillDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addOrUpdateMedBillDetail(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String medBillDetailJson ="";
		
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
	  
		medBillDetailJson = medBillDetailService.addOrUpdateMedBillDetail(detailVo);
		
		}
		return JSONObject.parseObject(medBillDetailJson);
	}
	
	/**
	 * @Description 
	 * 删除数据 保存一个发票对应的入库单，及金额
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/med/medbilldetail/deleteMedBillDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteMedBillDetail(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		
			for ( String id: paramVo.split(",")) {
				
				Map<String, Object> mapVo=new HashMap<String, Object>();
				
				String[] ids=id.split("@");
				
				//表的主键
				mapVo.put("group_id", ids[0])   ;
				mapVo.put("hos_id", ids[1])   ;
				mapVo.put("copy_code", ids[2])   ;
				mapVo.put("bill_id", ids[3]);
				
	      listVo.add(mapVo);
	      
	    }
	    
		String medBillDetailJson = medBillDetailService.deleteBatchMedBillDetail(listVo);
			
	  return JSONObject.parseObject(medBillDetailJson);
			
	}
	
	/**
	 * @Description 
	 * 查询数据 保存一个发票对应的入库单，及金额
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/med/medbilldetail/queryMedBillDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMedBillDetail(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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
		String medBillDetail = medBillDetailService.queryMedBillDetail(getPage(mapVo));

		return JSONObject.parseObject(medBillDetail);
		
	}
	
  /**
	 * @Description 
	 * 导入跳转页面 保存一个发票对应的入库单，及金额
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/med/medbilldetail/medBillDetailImportPage", method = RequestMethod.GET)
	public String medBillDetailImportPage(Model mode) throws Exception {

		return "hrp/med/medbilldetail/medBillDetailImport";

	}
	/**
	 * @Description 
	 * 下载导入模版 保存一个发票对应的入库单，及金额
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	 @RequestMapping(value="hrp/med/medbilldetail/downTemplate", method = RequestMethod.GET)  
	 public String downTemplate(Plupload plupload,HttpServletRequest request,
	    		HttpServletResponse response,Model mode) throws IOException { 
	    			
	    printTemplate(request,response,"med\\downTemplate","保存一个发票对应的入库单，及金额.xls");
	    
	    return null; 
	 }
	 /**
	 * @Description 
	 * 导入数据 保存一个发票对应的入库单，及金额
	 * @param  plupload
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	@RequestMapping(value="/hrp/med/medbilldetail/readMedBillDetailFiles",method = RequestMethod.POST)  
    public String readMedBillDetailFiles(Plupload plupload,HttpServletRequest request,
    		HttpServletResponse response,Model mode) throws IOException { 
			 
		List<MedBillDetail> list_err = new ArrayList<MedBillDetail>();
		
		List<String[]> list = UploadUtil.readFile(plupload, request, response);
		
		try {
			for (int i = 1; i < list.size(); i++) {
				
				StringBuffer err_sb = new StringBuffer();
				
				MedBillDetail medBillDetail = new MedBillDetail();
				
				String temp[] = list.get(i);// 行
				Map<String, Object> mapVo = new HashMap<String, Object>();
				
		    		mapVo.put("group_id", SessionManager.getGroupId());   
		    		         
					 
		    		mapVo.put("hos_id", SessionManager.getHosId());   
		    		         
					 
		    		mapVo.put("copy_code", SessionManager.getCopyCode());   
		    		         
					 
					if (StringTool.isNotBlank(temp[3])) {
						
					medBillDetail.setBill_id(Long.valueOf(temp[3]));
		    		mapVo.put("bill_id", temp[3]);
					
					} else {
						
						err_sb.append("发票ID为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[4])) {
						
					medBillDetail.setBill_detail_id(Long.valueOf(temp[4]));
		    		mapVo.put("bill_detail_id", temp[4]);
					
					} else {
						
						err_sb.append("发票明细ID为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[5])) {
						
					medBillDetail.setIn_id(temp[5]);
		    		mapVo.put("in_id", temp[5]);
					
					} else {
						
						err_sb.append("入库单ID为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[6])) {
						
					medBillDetail.setIn_detail_id(Long.valueOf(temp[6]));
		    		mapVo.put("in_detail_id", temp[6]);
					
					} else {
						
						err_sb.append("入库明细ID为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[7])) {
						
					medBillDetail.setBill_money(Long.valueOf(temp[7]));
		    		mapVo.put("bill_money", temp[7]);
					
					} else {
						
						err_sb.append("发票金额为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[8])) {
						
					medBillDetail.setFav_money(Double.valueOf(temp[8]));
		    		mapVo.put("fav_money", temp[8]);
					
					} else {
						
						err_sb.append("优惠金额为空  ");
						
					}
					 
					if (StringTool.isNotBlank(temp[9])) {
						
					medBillDetail.setIn_money(Double.valueOf(temp[9]));
		    		mapVo.put("pay_money", temp[9]);
					
					} else {
						
						err_sb.append("累计付款为空  ");
						
					}
					 
					 
					
				MedBillDetail data_exc_extis = medBillDetailService.queryMedBillDetailByCode(mapVo);
				
				if (data_exc_extis != null) {
					
					err_sb.append("数据已经存在！ ");
					
				}
				if (err_sb.toString().length() > 0) {
					
					medBillDetail.setError_type(err_sb.toString());
					
					list_err.add(medBillDetail);
					
				} else {
			  
					String dataJson = medBillDetailService.addMedBillDetail(mapVo);
					
				}
				
			}
			
		} catch (DataAccessException e) {
			
			MedBillDetail data_exc = new MedBillDetail();
			
			data_exc.setError_type("导入系统出错");
			
			list_err.add(data_exc);
			
		}
		
		response.getWriter().print(ChdJson.toJson(list_err, list_err.size()));
		
		return null;
		
    }  
   /**
	 * @Description 
	 * 批量添加数据 保存一个发票对应的入库单，及金额
	 * @param  plupload
	 * @param  request
	 * @param  response
	 * @param  mode
	 * @return String
	 * @throws IOException
	*/
	@RequestMapping(value = "/hrp/med/medbilldetail/addBatchMedBillDetail", method = RequestMethod.POST)
	@ResponseBody
    public Map<String, Object> addBatchMedBillDetail(@RequestParam(value = "ParamVo") String paramVo, Model mode)throws Exception{
				
		List<MedBillDetail> list_err = new ArrayList<MedBillDetail>();
		
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
			
			MedBillDetail medBillDetail = new MedBillDetail();
			
			JSONObject jsonObj = JSONObject.parseObject(it.next().toString());
			
			
					
					
					
					if (StringTool.isNotBlank(jsonObj.get("bill_id"))) {
						
					medBillDetail.setBill_id(Long.valueOf((String)jsonObj.get("bill_id")));
		    		mapVo.put("bill_id", jsonObj.get("bill_id"));
		    		} else {
						
						err_sb.append("发票ID为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("bill_detail_id"))) {
						
					medBillDetail.setBill_detail_id(Long.valueOf((String)jsonObj.get("bill_detail_id")));
		    		mapVo.put("bill_detail_id", jsonObj.get("bill_detail_id"));
		    		} else {
						
						err_sb.append("发票明细ID为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("in_id"))) {
						
					medBillDetail.setIn_id((String)jsonObj.get("in_id"));
		    		mapVo.put("in_id", jsonObj.get("in_id"));
		    		} else {
						
						err_sb.append("入库单ID为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("in_detail_id"))) {
						
					medBillDetail.setIn_detail_id(Long.valueOf((String)jsonObj.get("in_detail_id")));
		    		mapVo.put("in_detail_id", jsonObj.get("in_detail_id"));
		    		} else {
						
						err_sb.append("入库明细ID为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("bill_money"))) {
						
					medBillDetail.setBill_money(Long.valueOf((String)jsonObj.get("bill_money")));
		    		mapVo.put("bill_money", jsonObj.get("bill_money"));
		    		} else {
						
						err_sb.append("发票金额为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("fav_money"))) {
						
					medBillDetail.setFav_money(Double.valueOf((String)jsonObj.get("fav_money")));
		    		mapVo.put("fav_money", jsonObj.get("fav_money"));
		    		} else {
						
						err_sb.append("优惠金额为空  ");
						
					}
					
					if (StringTool.isNotBlank(jsonObj.get("pay_money"))) {
						
					medBillDetail.setIn_money(Double.valueOf((String)jsonObj.get("pay_money")));
		    		mapVo.put("pay_money", jsonObj.get("pay_money"));
		    		} else {
						
						err_sb.append("累计付款为空  ");
						
					}
					
					
					
				MedBillDetail data_exc_extis = medBillDetailService.queryMedBillDetailByCode(mapVo);
				
				if (data_exc_extis != null) {
					
					err_sb.append("编码已经存在！ ");
					
				}
				if (err_sb.toString().length() > 0) {
					
					medBillDetail.setError_type(err_sb.toString());
					
					list_err.add(medBillDetail);
					
				} else {
			  
					String dataJson = medBillDetailService.addMedBillDetail(mapVo);
					
				}
				
			}
			
		} catch (DataAccessException e) {
			
			MedBillDetail data_exc = new MedBillDetail();
			
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

