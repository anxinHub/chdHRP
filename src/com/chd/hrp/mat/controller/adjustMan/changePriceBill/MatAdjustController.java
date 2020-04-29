
package com.chd.hrp.mat.controller.adjustMan.changePriceBill;

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
import com.chd.hrp.mat.entity.MatAdjustDetail;
import com.chd.hrp.mat.service.adjustMan.changePriceBill.MatAdjustService;
import com.chd.hrp.sys.entity.DeptDict;

/**
 * 
 * @Description:
 * 04113 调价单
 * @Table:
 * MAT_ADJUST_MAIN
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
@Controller
public class MatAdjustController extends BaseController{
	
	private static Logger logger = Logger.getLogger(MatAdjustController.class);
	
	@Resource(name = "matAdjustService")
	private final MatAdjustService matAdjustService = null ;
	
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
	@RequestMapping(value = "/hrp/mat/adjustMan/changePriceBill/matAdjustMainPage", method = RequestMethod.GET)
	public String matAdjustMainPage(Model mode) throws Exception {

		mode.addAttribute("p04006", MyConfig.getSysPara("04006"));

		return "hrp/mat/adjustMan/changePriceBill/matAdjustMain";

	}
	
	/**
	 * @Description 
	 * 维护页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	//添加页面跳转
	@RequestMapping(value = "/hrp/mat/adjustMan/changePriceBill/matAdjustAddPage", method = RequestMethod.GET)
	public String matAdjustAddPage(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		
		mode.addAttribute("p04006", MyConfig.getSysPara("04006"));

		return "hrp/mat/adjustMan/changePriceBill/matAdjustAdd";

	}
	/**
	 * 选择材料
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/adjustMan/changePriceBill/choiceInvBySupPage", method = RequestMethod.GET)
	public String choiceInvBySupPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
	    if(mapVo.get("group_id") == null){
	    	mapVo.put("group_id", SessionManager.getGroupId());
		}
		if(mapVo.get("hos_id") == null){
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if(mapVo.get("copy_code") == null){
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		if(mapVo.get("show_history") == null){
			mapVo.put("show_history", MyConfig.getSysPara("03001"));
		}
			
		mode.addAttribute("store_id", mapVo.get("store_id"));
		mode.addAttribute("store_text", mapVo.get("store_text"));
		mode.addAttribute("p04006", MyConfig.getSysPara("04006"));
		
		return "hrp/mat/adjustMan/changePriceBill/choiceInvBySup";
	}
	
	/**
	 * @Description 
	 * 维护页面跳转 
	 * @param  mode
	 * @return String
	 * @throws Exception
	*/
	//修改页面跳转
	@RequestMapping(value = "/hrp/mat/adjustMan/changePriceBill/updateMatAdjustPage", method = RequestMethod.GET)
	public String updateMatAdjustPage(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		
		String para = MyConfig.getSysPara("04006");
		
		mode.addAttribute("para_value", para);//单价字段小数点后保留位数
		
		Map<String,Object> matAdjust = matAdjustService.queryByCode(mapVo);
		
		mode.addAttribute("group_id",matAdjust.get("group_id"));
		mode.addAttribute("hos_id",matAdjust.get("hos_id"));
		mode.addAttribute("copy_code",matAdjust.get("copy_code"));
		mode.addAttribute("adjust_id",matAdjust.get("adjust_id"));
		mode.addAttribute("adjust_code",matAdjust.get("adjust_code"));
		mode.addAttribute("create_date", matAdjust.get("create_date"));
		mode.addAttribute("note", matAdjust.get("note"));
		mode.addAttribute("maker", matAdjust.get("maker"));
		mode.addAttribute("make_date", matAdjust.get("make_date"));
		mode.addAttribute("checker", matAdjust.get("checker"));
		mode.addAttribute("adjust_date",matAdjust.get("adjust_date"));
		mode.addAttribute("state",mapVo.get("state"));
		
		mode.addAttribute("p04006", MyConfig.getSysPara("04006"));

		return "hrp/mat/adjustMan/changePriceBill/matAdjustUpdate";

	}
	
	/**
	 * @Description 
	 * 调价单 添加
	 * @param  mode
	 * @return Map
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/mat/adjustMan/changePriceBill/addMatAdjust", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> addMatAdjust(@RequestParam Map<String,Object> mapVo,Model mode) throws Exception {
		if(mapVo.get("group_id") == null){
			
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		
		if(mapVo.get("hos_id") == null){
		
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		
		if(mapVo.get("copy_code") == null){
    	
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		String matAdjustJson;
		
		try {
			
			matAdjustJson = matAdjustService.add(mapVo);
			
		} catch (Exception e) {
			
			matAdjustJson = e.getMessage();
			
		}
		
		
		return JSONObject.parseObject(matAdjustJson);
	}
	
	/**
	 * @Description 
	 * 调价单 更新调价单主表及明细表数据
	 * @param  mode
	 * @return Map
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/mat/adjustMan/changePriceBill/updateMatAdjust", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> updateMatAdjust(@RequestParam Map<String,Object> mapVo,Model mode) throws Exception {
		if(mapVo.get("group_id") == null){
			
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		
		if(mapVo.get("hos_id") == null){
		
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		
		if(mapVo.get("copy_code") == null){
    	
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		String matAdjustJson;
		
		try {
			
			matAdjustJson = matAdjustService.update(mapVo);
			
		} catch (Exception e) {
			
			matAdjustJson = e.getMessage();
		}
		
		return JSONObject.parseObject(matAdjustJson);
	}
	
	
	/**
	 * @Description 
	 * 调价单 查询
	 * @param  mode
	 * @return Map
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/mat/adjustMan/changePriceBill/queryMatAdjust", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> queryMatAdjust(@RequestParam Map<String,Object> mapVo,Model mode) throws Exception {
		if(mapVo.get("group_id") == null){
			
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		
		if(mapVo.get("hos_id") == null){
		
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		
		if(mapVo.get("copy_code") == null){
    	
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		String matAdjustJson="";
		if(mapVo.get("show_detail").equals("1")){
			matAdjustJson = matAdjustService.queryDetails(getPage(mapVo));
		}else{
			matAdjustJson = matAdjustService.query(getPage(mapVo));
		}
		
		return JSONObject.parseObject(matAdjustJson);
	}
	
	/**
	 * @Description 
	 * 调价单 查询
	 * @param  mode
	 * @return Map
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/mat/adjustMan/changePriceBill/queryMatAdjustDetailByCode", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> queryMatAdjustDetailByCode(@RequestParam Map<String,Object> mapVo,Model mode) throws Exception {
		if(mapVo.get("group_id") == null){
			
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		
		if(mapVo.get("hos_id") == null){
		
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		
		if(mapVo.get("copy_code") == null){
    	
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		String matAdjustDetailJson= matAdjustService.queryMatAdjustDetailByCode(getPage(mapVo));
		
		return JSONObject.parseObject(matAdjustDetailJson);
	}
	
	/**
	 * @Description 
	 * 调价单 审核 修改状态
	 * @param  mode
	 * @return Map
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/mat/adjustMan/changePriceBill/updateMatAdjustState", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> updateMatAdjustState(@RequestParam Map<String,Object> mapVo,Model mode) throws Exception {
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
			
		String matAdjustJson;
		try {
			matAdjustJson = matAdjustService.updateMatAdjustState(listVo);//调价单需求不明确，暂时注释
		} catch (Exception e) {
			matAdjustJson = e.getMessage();
		}
		
		return JSONObject.parseObject(matAdjustJson);
	}
	/**
	 * @Description 
	 * 调价单 审核 修改状态
	 * @param  mode
	 * @return Map
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/adjustMan/changePriceBill/updateCheckMatAdjustState", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> updateCheckMatAdjustState(@RequestParam Map<String,Object> mapVo,Model mode) throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());
			mapVo.put("checker", SessionManager.getUserId());
			mapVo.put("adjust_date", sdf.format(new Date()));
		String matAdjustJson;
		try {
			matAdjustJson = matAdjustService.updateCheckMatAdjustState(mapVo);//调价单需求不明确，暂时注释
		} catch (Exception e) {
			matAdjustJson = e.getMessage();
		}
		
		return JSONObject.parseObject(matAdjustJson);
	}
	
	/**
	 * @Description 
	 * 调价单  批量删除
	 * @param  mode
	 * @return Map
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/mat/adjustMan/changePriceBill/deleteBatchMatAdjust", method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> deleteBatchMatAdjustState(@RequestParam Map<String,Object> mapVo,Model mode) throws Exception {
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
		
		String matAdjustJson;
		
		try {
			
			matAdjustJson = matAdjustService.deleteBatch(listVo);
			
		} catch (Exception e) {
			
			matAdjustJson = e.getMessage();
		}
		
		return JSONObject.parseObject(matAdjustJson);
	}
	
	// 材料字典列表
	@RequestMapping(value = "/hrp/mat/adjustMan/changePriceBill/queryMatInvList", method = RequestMethod.POST)
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
		String matInvList = matAdjustService.queryMatInvList(getPage(mapVo));
		return matInvList;
	}
	
	/**
	 * 生成入库单
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/adjustMan/changePriceBill/queryChoiceInvBySupData", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryChoiceInvBySupData(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if(mapVo.get("group_id") == null){
			mapVo.put("group_id", SessionManager.getGroupId());
		}
					
		if(mapVo.get("hos_id") == null){
			mapVo.put("hos_id", SessionManager.getHosId());
		}
					
		if(mapVo.get("copy_code") == null){
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
					
		String detailData = matAdjustService.queryChoiceInvBySupData(mapVo);
					
		return JSONObject.parseObject(detailData);
					
	}
	
	/**
	 * 根据供应商查材料
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/adjustMan/changePriceBill/queryMatChoiceInvBySup", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMatChoiceInvBySup(@RequestParam Map<String, Object> mapVo, Model mode)throws Exception {
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
		
		String detail = matAdjustService.queryMatChoiceInvBySup(getPage(mapVo));;
		
		return JSONObject.parseObject(detail);
	}
	
	
	
	//下载导入模版
	@RequestMapping(value="/hrp/mat/adjustMan/changePriceBill/downTemplate", method = RequestMethod.GET)  
	public String downTemplate(Plupload plupload,HttpServletRequest request,
		HttpServletResponse response,Model mode) throws IOException { 
		
		printTemplate(request,response,"mat\\downTemplate","调价单.xls");
		return null; 
	}
	
	// 导入页面
	@RequestMapping(value = "/hrp/mat/adjustMan/changePriceBill/matAdjustImportPage", method = RequestMethod.GET)
	public String matAdjustImportPage(Model mode) throws Exception {

		return "hrp/mat/adjustMan/changePriceBill/matAdjustImport";

	}
	
	/**
	*调价单<BR>
	*导入
	*/ 
	@RequestMapping(value="/hrp/mat/adjustMan/changePriceBill/readMatAdjustFiles",method = RequestMethod.POST)  
    public String readMatAdjustFiles(Plupload plupload,HttpServletRequest request,
    		HttpServletResponse response,Model mode) throws IOException { 
		
		List<Map<String, Object>> list_batch = new ArrayList<Map<String, Object>>();
			
		List<MatAdjustDetail> list_err = new ArrayList<MatAdjustDetail>();
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		List<String[]> list = UploadUtil.readFile(plupload, request, response);
		
		Map<String,Object> dataMap = new HashMap<String,Object>();
		
		try {
			for (int i = 1; i < list.size(); i++) {
				
				StringBuffer err_sb = new StringBuffer();
				
				MatAdjustDetail matAdjustDetail = new MatAdjustDetail();
				
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
						
						matAdjustDetail.setInv_code(String.valueOf(temp[0]));
						
						Map<String,Object> map = matAdjustService.queryMatAdjustInvByCode(mapVo);
						
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
						
						matAdjustDetail.setOld_price(Double.parseDouble(String.valueOf(temp[1])));
						
						mapVo.put("old_price", temp[1]);
					}else{
						
						err_sb.append("原计划价为空 ");
					}
					
					if (ExcelReader.validExceLColunm(temp, 2)) {
						
						matAdjustDetail.setNew_price(Double.parseDouble(String.valueOf(temp[2])));
						
						mapVo.put("new_price", temp[2]);
					}else{
						
						err_sb.append("新计划价为空 ");
					}
					
					if (ExcelReader.validExceLColunm(temp, 3)) {
						
						matAdjustDetail.setAdjust_reason(temp[3]);
						mapVo.put("adjust_reason", temp[3]);
					}
					
					if(err_sb.toString().length() > 0){
						
						matAdjustDetail.setError_type(err_sb.toString());
						
						list_err.add(matAdjustDetail);
						
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
				
				matAdjustService.add(dataMap);
			}
			
		} catch (DataAccessException e) {
			
			e.printStackTrace();
			
			MatAdjustDetail data_exc = new MatAdjustDetail();
			
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
