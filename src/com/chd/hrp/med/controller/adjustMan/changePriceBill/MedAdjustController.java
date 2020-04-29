
package com.chd.hrp.med.controller.adjustMan.changePriceBill;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
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
import com.chd.base.util.ChdJson;
import com.chd.base.util.ExcelReader;
import com.chd.base.util.Plupload;
import com.chd.base.util.UploadUtil;
import com.chd.hrp.acc.entity.AccPara;
import com.chd.hrp.acc.service.AccParaService;
import com.chd.hrp.cost.entity.CostChargeKindArrt;
import com.chd.hrp.cost.entity.CostIncomeMain;
import com.chd.hrp.med.entity.MedAdjustDetail;
import com.chd.hrp.med.service.adjustMan.changePriceBill.MedAdjustService;
import com.chd.hrp.sys.entity.DeptDict;

/**
 * 
 * @Description:
 * 04113 调价单
 * @Table:
 * MED_ADJUST_MAIN
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
@Controller
public class MedAdjustController extends BaseController{
	
	private static Logger logger = Logger.getLogger(MedAdjustController.class);
	
	@Resource(name = "medAdjustService")
	private final MedAdjustService medAdjustService = null ;
	
	@Resource(name = "accParaService")
	private final AccParaService accParaService = null;
	
	/**
	 * @Description 
	 * 维护页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	//主页面跳转
	@RequestMapping(value = "/hrp/med/adjustMan/changePriceBill/medAdjustMainPage", method = RequestMethod.GET)
	public String medAdjustMainPage(Model mode) throws Exception {

		return "hrp/med/adjustMan/changePriceBill/medAdjustMain";

	}
	
	/**
	 * @Description 
	 * 维护页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	//添加页面跳转
	@RequestMapping(value = "/hrp/med/adjustMan/changePriceBill/medAdjustAddPage", method = RequestMethod.GET)
	public String medAdjustAddPage(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		
		/*if(mapVo.get("group_id") == null){
	    	mapVo.put("group_id", SessionManager.getGroupId());
		}
		
		if(mapVo.get("hos_id") == null){
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		
		if(mapVo.get("copy_code") == null){
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		mapVo.put("para_code", "04006");//单价字段小数点后保留位数 系统参数编码
		AccPara accPara = accParaService.queryAccParaByCode(mapVo);
		mode.addAttribute("para_value", accPara.getPara_value());//单价字段小数点后保留位数 
*/
		mode.addAttribute("p08006", MyConfig.getSysPara("08006"));
		
		return "hrp/med/adjustMan/changePriceBill/medAdjustAdd";

	}
	
	/**
	 * @Description 
	 * 维护页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	//修改页面跳转
	@RequestMapping(value = "/hrp/med/adjustMan/changePriceBill/updateMedAdjustPage", method = RequestMethod.GET)
	public String updateMedAdjustPage(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		
		if(mapVo.get("group_id") == null){
	    	mapVo.put("group_id", SessionManager.getGroupId());
		}
		
		if(mapVo.get("hos_id") == null){
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		
		if(mapVo.get("copy_code") == null){
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		/*mapVo.put("para_code", "04006");
		
		AccPara accPara = accParaService.queryAccParaByCode(mapVo);
		
		mode.addAttribute("para_value", accPara.getPara_value());*/
		
		Map<String,Object> medAdjust = medAdjustService.queryByCode(mapVo);
		
		mode.addAttribute("group_id",medAdjust.get("group_id"));
		mode.addAttribute("hos_id",medAdjust.get("hos_id"));
		mode.addAttribute("copy_code",medAdjust.get("copy_code"));
		mode.addAttribute("adjust_id",medAdjust.get("adjust_id"));
		mode.addAttribute("adjust_code",medAdjust.get("adjust_code"));
		mode.addAttribute("create_date", medAdjust.get("create_date"));
		mode.addAttribute("note", medAdjust.get("note"));
		mode.addAttribute("maker", medAdjust.get("maker"));
		mode.addAttribute("make_date", medAdjust.get("make_date"));
		mode.addAttribute("checker", medAdjust.get("checker"));
		mode.addAttribute("adjust_date",medAdjust.get("adjust_date"));
		mode.addAttribute("state",mapVo.get("state"));
		
		mode.addAttribute("p08006", MyConfig.getSysPara("08006"));

		return "hrp/med/adjustMan/changePriceBill/medAdjustUpdate";

	}
	
	/**
	 * @Description 
	 * 调价单 添加
	 * @param  mode
	 * @return Map
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/med/adjustMan/changePriceBill/addMedAdjust", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> addMedAdjust(@RequestParam Map<String,Object> mapVo,Model mode) throws Exception {
		if(mapVo.get("group_id") == null){
			
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		
		if(mapVo.get("hos_id") == null){
		
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		
		if(mapVo.get("copy_code") == null){
    	
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		String medAdjustJson;
		
		try {
			
			medAdjustJson = medAdjustService.add(mapVo);
			
		} catch (Exception e) {
			
			medAdjustJson = e.getMessage();
			
		}
		
		
		return JSONObject.parseObject(medAdjustJson);
	}
	
	/**
	 * @Description 
	 * 调价单 更新调价单主表及明细表数据
	 * @param  mode
	 * @return Map
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/med/adjustMan/changePriceBill/updateMedAdjust", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> updateMedAdjust(@RequestParam Map<String,Object> mapVo,Model mode) throws Exception {
		if(mapVo.get("group_id") == null){
			
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		
		if(mapVo.get("hos_id") == null){
		
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		
		if(mapVo.get("copy_code") == null){
    	
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		String medAdjustJson;
		
		try {
			
			medAdjustJson = medAdjustService.update(mapVo);
			
		} catch (Exception e) {
			
			medAdjustJson = e.getMessage();
		}
		
		return JSONObject.parseObject(medAdjustJson);
	}
	
	
	/**
	 * @Description 
	 * 调价单 查询
	 * @param  mode
	 * @return Map
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/med/adjustMan/changePriceBill/queryMedAdjust", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> queryMedAdjust(@RequestParam Map<String,Object> mapVo,Model mode) throws Exception {
		if(mapVo.get("group_id") == null){
			
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		
		if(mapVo.get("hos_id") == null){
		
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		
		if(mapVo.get("copy_code") == null){
    	
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		String medAdjustJson = medAdjustService.query(getPage(mapVo));
		
		return JSONObject.parseObject(medAdjustJson);
	}
	
	/**
	 * @Description 
	 * 调价单 查询
	 * @param  mode
	 * @return Map
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/med/adjustMan/changePriceBill/queryMedAdjustDetailByCode", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> queryMedAdjustDetailByCode(@RequestParam Map<String,Object> mapVo,Model mode) throws Exception {
		if(mapVo.get("group_id") == null){
			
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		
		if(mapVo.get("hos_id") == null){
		
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		
		if(mapVo.get("copy_code") == null){
    	
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		String medAdjustDetailJson= medAdjustService.queryMedAdjustDetailByCode(getPage(mapVo));
		
		return JSONObject.parseObject(medAdjustDetailJson);
	}
	
	/**
	 * @Description 
	 * 调价单 审核 修改状态
	 * @param  mode
	 * @return Map
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/med/adjustMan/changePriceBill/updateMedAdjustState", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> updateMedAdjustState(@RequestParam Map<String,Object> mapVo,Model mode) throws Exception {
		if(mapVo.get("group_id") == null){
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		
		if(mapVo.get("hos_id") == null){
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		
		if(mapVo.get("copy_code") == null){
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		String paramVo = mapVo.get("paramVo").toString();
			for ( String id: paramVo.split(",")) {
				Map<String, Object> map=new HashMap<String, Object>();
				String[] ids=id.split("@");
				//表的主键
				map.put("group_id", mapVo.get("group_id"));
				map.put("hos_id", mapVo.get("hos_id"));
				map.put("copy_code", mapVo.get("copy_code"));
				map.put("adjust_id", ids[3]);
				map.put("state", ids[4]);
				map.put("checker", SessionManager.getUserId());
				map.put("adjust_date", sdf.format(new Date()));
				listVo.add(map);
	    }
			
		String medAdjustJson;
		try {
			medAdjustJson = medAdjustService.updateMedAdjustState(listVo);//调价单需求不明确，暂时注释
		} catch (Exception e) {
			medAdjustJson = e.getMessage();
		}
		
		return JSONObject.parseObject(medAdjustJson);
	}
	
	/**
	 * @Description 
	 * 调价单  批量删除
	 * @param  mode
	 * @return Map
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/med/adjustMan/changePriceBill/deleteBatchMedAdjust", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> deleteBatchMedAdjustState(@RequestParam Map<String,Object> mapVo,Model mode) throws Exception {
		if(mapVo.get("group_id") == null){
			
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		
		if(mapVo.get("hos_id") == null){
		
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		
		if(mapVo.get("copy_code") == null){
    	
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		
		String paramVo = mapVo.get("paramVo").toString();
		
		if(paramVo != null && !"".equals(paramVo)){
		
			for ( String id: paramVo.split(",")) {
				
				Map<String, Object> map=new HashMap<String, Object>();
				
				String[] ids=id.split("@");
				
				//表的主键
				map.put("group_id", ids[0]);
				map.put("hos_id", ids[1]);
				map.put("copy_code", ids[2]);
				map.put("adjust_id", ids[3]);
				listVo.add(map);
			}
		}
		
		String medAdjustJson;
		
		try {
			
			medAdjustJson = medAdjustService.deleteBatch(listVo);
			
		} catch (Exception e) {
			
			medAdjustJson = e.getMessage();
		}
		
		return JSONObject.parseObject(medAdjustJson);
	}
	
	// 材料字典列表
	@RequestMapping(value = "/hrp/med/adjustMan/changePriceBill/queryMedInvList", method = RequestMethod.POST)
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
		String medInvList = medAdjustService.queryMedInvList(getPage(mapVo));
		return medInvList;
	}
	
	//下载导入模版
	@RequestMapping(value="/hrp/med/adjustMan/changePriceBill/downTemplate", method = RequestMethod.GET)  
	public String downTemplate(Plupload plupload,HttpServletRequest request,
		HttpServletResponse response,Model mode) throws IOException { 
		
		printTemplate(request,response,"med\\downTemplate","调价单.xls");
		return null; 
	}
	
	// 导入页面
	@RequestMapping(value = "/hrp/med/adjustMan/changePriceBill/medAdjustImportPage", method = RequestMethod.GET)
	public String medAdjustImportPage(Model mode) throws Exception {

		return "hrp/med/adjustMan/changePriceBill/medAdjustImport";

	}
	
	/**
	*调价单<BR>
	*导入
	*/ 
	@RequestMapping(value="/hrp/med/adjustMan/changePriceBill/readMedAdjustFiles",method = RequestMethod.POST)  
    public String readMedAdjustFiles(Plupload plupload,HttpServletRequest request,
    		HttpServletResponse response,Model mode) throws IOException { 
		
		List<Map<String, Object>> list_batch = new ArrayList<Map<String, Object>>();
			
		List<MedAdjustDetail> list_err = new ArrayList<MedAdjustDetail>();
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		List<String[]> list = UploadUtil.readFile(plupload, request, response);
		
		Map<String,Object> dataMap = new HashMap<String,Object>();
		
		try {
			for (int i = 1; i < list.size(); i++) {
				
				StringBuffer err_sb = new StringBuffer();
				
				MedAdjustDetail medAdjustDetail = new MedAdjustDetail();
				
				String temp[] = list.get(i);
				
				if(validExcelRow(temp)){//过滤空行
					
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
					
					if (ExcelReader.validExceLColunm(temp, 0)) {
						
						mapVo.put("inv_code", temp[0]);
						
						medAdjustDetail.setInv_code(String.valueOf(temp[0]));
						
						Map<String,Object> map = medAdjustService.queryMedAdjustInvByCode(mapVo);
						
						if(map != null && !"".equals(map)){
							
							mapVo.put("inv_id", map.get("inv_id"));
							
							mapVo.put("inv_no", map.get("inv_no"));
						}else{
							
							err_sb.append("材料编码不存在 ");
						}
						
					}else{
						
						err_sb.append("材料编码为空 ;");
					}
					
					
					if (ExcelReader.validExceLColunm(temp, 1)) {
						
						medAdjustDetail.setOld_price(Double.parseDouble(String.valueOf(temp[1])));
						
						mapVo.put("old_price", temp[1]);
					}else{
						
						err_sb.append("原计划价为空 ");
					}
					
					if (ExcelReader.validExceLColunm(temp, 2)) {
						
						medAdjustDetail.setNew_price(Double.parseDouble(String.valueOf(temp[2])));
						
						mapVo.put("new_price", temp[2]);
					}else{
						
						err_sb.append("新计划价为空 ");
					}
					
					if (ExcelReader.validExceLColunm(temp, 3)) {
						
						medAdjustDetail.setAdjust_reason(temp[3]);
						mapVo.put("adjust_reason", temp[3]);
					}
					
					if(err_sb.toString().length() > 0){
						
						medAdjustDetail.setError_type(err_sb.toString());
						
						list_err.add(medAdjustDetail);
						
					}else{
						
						list_batch.add(mapVo);
						
					}
				}
			}
			
			if(list_err.size() == 0){
				
				dataMap.put("group_id", SessionManager.getGroupId());
				
				dataMap.put("hos_id", SessionManager.getHosId());
				
				dataMap.put("copy_code", SessionManager.getCopyCode());
				
				dataMap.put("create_date", sdf.format(new Date()));
				
				dataMap.put("allData", JSONObject.toJSONString(list_batch));
				
				medAdjustService.add(dataMap);
			}
			
		} catch (DataAccessException e) {
			
			e.printStackTrace();
			
			MedAdjustDetail data_exc = new MedAdjustDetail();
			
			data_exc.setError_type("导入系统出错");
			
			list_err.add(data_exc);
		}
		
		response.getWriter().print(ChdJson.toJson(list_err, list_err.size()));
		return null;
    } 
	
	/**
	 * @Description 
	 * 调价单 用于导入Excel 判断是否空行,作为判断过滤
	 * @param  mode
	 * @return Map
	 * @throws Exception
	*/
	public boolean validExcelRow (String[] arr) {
		
		if(arr.length == 0 ){
			
			return false;
		}
		
		boolean flag = false;
		
		for (int i = 0; i < arr.length; i++) {//遍历当前行所有列
			
			if(arr[i] != null && !"".equals(arr[i])){
				
				flag = true;
				break;
			}
		}
		
		return flag;
	}
}
