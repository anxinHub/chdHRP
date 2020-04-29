/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/
package com.chd.hrp.cost.controller;
import java.util.*;
import javax.annotation.Resource;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.dao.DataAccessException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.alibaba.fastjson.JSONObject;
import com.chd.base.BaseController;
import com.chd.base.SessionManager;
import com.chd.base.util.StringTool;
import com.chd.base.util.ChdJson;
import com.chd.base.util.DateUtil;
import com.chd.base.util.Plupload;
import com.chd.base.util.UploadUtil;
import com.chd.hrp.cost.entity.CostItemDictNo;
import com.chd.hrp.cost.serviceImpl.CostItemDictNoServiceImpl;

/**
* @Title. @Description.
* 成本项目变更表
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


@Controller
public class CostItemDictNoController extends BaseController{
	private static Logger logger = Logger.getLogger(CostItemDictNoController.class);
	
	
	@Resource(name = "costItemDictNoService")
	private final CostItemDictNoServiceImpl costItemDictNoService = null;
   
   
	/**
	*成本项目变更表<BR>
	*维护页面跳转
	*/
	
	@RequestMapping(value = "/hrp/cost/costitemdictno/costItemDictNoMainPage", method = RequestMethod.GET)
	public String costItemDictNoMainPage(Model mode) throws Exception {

		return "hrp/cost/costitemdictno/costItemDictNoMain";

	}
	/**
	*成本项目变更表<BR>
	*维护页面跳转
	*/
	// 添加页面
	@RequestMapping(value = "/hrp/cost/costitemdictno/costItemDictNoAddPage", method = RequestMethod.GET)
	public String costItemDictNoAddPage(Model mode) throws Exception {

		return "hrp/cost/costitemdictno/costItemDictNoAdd";

	}
	/**
	*成本项目变更表<BR>
	*保存
	*/
	@RequestMapping(value = "/hrp/cost/costitemdictno/addCostItemDictNo", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> addCostItemDictNo(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if(mapVo.get("group_id") == null){
		mapVo.put("group_id", SessionManager.getGroupId());
		}
		if(mapVo.get("hos_id") == null){
		mapVo.put("hos_id", SessionManager.getHosId());
		}
		if(mapVo.get("copy_code") == null){
        mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		//System.out.println(mapVo.get("cost_item_code"));
		//根据名称生成拼音码
        mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("cost_item_name").toString()));
		//根据名称生成五笔码
        mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("cost_item_name").toString()));
		String costItemDictNoJson = costItemDictNoService.addCostItemDictNo(mapVo);

		return JSONObject.parseObject(costItemDictNoJson);
		
	}
	/**
	*成本项目变更表<BR>
	*查询
	*/
	@RequestMapping(value = "/hrp/cost/costitemdictno/queryCostItemDictNo", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> queryCostItemDictNo(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if(mapVo.get("group_id") == null){
		mapVo.put("group_id", SessionManager.getGroupId());
		}
		if(mapVo.get("hos_id") == null){
		mapVo.put("hos_id", SessionManager.getHosId());
		}
		if(mapVo.get("copy_code") == null){
        mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		String costItemDictNo = costItemDictNoService.queryCostItemDictNo(getPage(mapVo));

		return JSONObject.parseObject(costItemDictNo);
		
	}
	/**
	*成本项目变更表<BR>
	*删除
	*/
	@RequestMapping(value = "/hrp/cost/costitemdictno/deleteCostItemDictNo", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteCostItemDictNo(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		for ( String id: paramVo.split(",")) {
			String[] ids=id.split("@");
			Map<String, Object> mapVo=new HashMap<String, Object>();
			mapVo.put("group_id",ids[0]);   
			mapVo.put("hos_id",ids[1]);   
			mapVo.put("copy_code",ids[2]);   
			mapVo.put("cost_item_id",ids[3]);   
			mapVo.put("cost_item_no",ids[4]); 
            listVo.add(mapVo);
        }
		String costItemDictNoJson = costItemDictNoService.deleteBatchCostItemDictNo(listVo);
	   return JSONObject.parseObject(costItemDictNoJson);
			
	}
	
	/**
	*成本项目变更表<BR>
	*修改页面跳转
	*/
	@RequestMapping(value = "/hrp/cost/costitemdictno/costItemDictNoUpdatePage", method = RequestMethod.GET)
	
	public String costItemDictNoUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if(mapVo.get("group_id") == null){
		mapVo.put("group_id", SessionManager.getGroupId());
		}
		if(mapVo.get("hos_id") == null){
		mapVo.put("hos_id", SessionManager.getHosId());
		}
		if(mapVo.get("copy_code") == null){
        mapVo.put("copy_code", SessionManager.getCopyCode());
		}
        CostItemDictNo costItemDictNo = new CostItemDictNo();
		costItemDictNo = costItemDictNoService.queryCostItemDictNoByCode(mapVo);
		mode.addAttribute("group_id", costItemDictNo.getGroup_id());
		mode.addAttribute("hos_id", costItemDictNo.getHos_id());
		mode.addAttribute("copy_code", costItemDictNo.getCopy_code());
		mode.addAttribute("cost_item_id", costItemDictNo.getCost_item_id());
		mode.addAttribute("cost_item_no", costItemDictNo.getCost_item_no());
		mode.addAttribute("cost_item_code", costItemDictNo.getCost_item_code());
		mode.addAttribute("cost_item_name", costItemDictNo.getCost_item_name());
		mode.addAttribute("user_code", costItemDictNo.getUser_code());
		mode.addAttribute("create_date", costItemDictNo.getCreate_date());
		
        mode.addAttribute("supp_item_code", costItemDictNo.getSupp_item_code());
		mode.addAttribute("cost_type_id", costItemDictNo.getCost_type_id());
		mode.addAttribute("nature_id", costItemDictNo.getNature_id());
		mode.addAttribute("busi_data_source", costItemDictNo.getBusi_data_source());
		mode.addAttribute("item_grade", costItemDictNo.getItem_grade());
		mode.addAttribute("note", costItemDictNo.getNote());
		mode.addAttribute("is_stop", costItemDictNo.getIs_stop());
		mode.addAttribute("spell_code", costItemDictNo.getSpell_code());
		mode.addAttribute("wbx_code", costItemDictNo.getWbx_code());
		return "hrp/cost/costitemdictno/costItemDictNoUpdate";
	}
	/**
	*成本项目变更表<BR>
	*修改保存
	*/	
	
	@RequestMapping(value = "/hrp/cost/costitemdictno/updateCostItemDictNo", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateCostItemDictNo(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if(mapVo.get("group_id") == null){
		mapVo.put("group_id", SessionManager.getGroupId());
		}
		if(mapVo.get("hos_id") == null){
		mapVo.put("hos_id", SessionManager.getHosId());
		}
		if(mapVo.get("copy_code") == null){
        mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		//根据名称生成拼音码
        mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("cost_item_name").toString()));
		//根据名称生成五笔码
        mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("cost_item_name").toString()));
		
		String costItemDictNoJson = costItemDictNoService.updateCostItemDictNo(mapVo);
		
		return JSONObject.parseObject(costItemDictNoJson);
	}
	
    // 导入页面
	@RequestMapping(value = "/hrp/cost/costitemdictno/costItemDictNoImportPage", method = RequestMethod.GET)
	public String costItemDictNoImportPage(Model mode) throws Exception {

		return "hrp/cost/costitemdictno/costItemDictNoImport";

	}
	//下载导入模版
	@RequestMapping(value="hrp/cost/costitemdictno/downTemplate", method = RequestMethod.GET)  
	 public String downTemplate(Plupload plupload,HttpServletRequest request,
	    		HttpServletResponse response,Model mode) throws IOException { 
	    printTemplate(request,response,"cost\\对应目录","对应字典.xls");
	    return null; 
	 }
	 
	/**
	*成本项目变更表<BR>
	*导入
	*/ 
	@RequestMapping(value="/hrp/cost/costitemdictno/readCostItemDictNoFiles",method = RequestMethod.POST)  
    public String readChargeKindDictFiles(Plupload plupload,HttpServletRequest request,
    		HttpServletResponse response,Model mode) throws IOException { 
    	List<CostItemDictNo> list2 = new ArrayList<CostItemDictNo> ();  
    	List<String[]> list = UploadUtil.readFile(plupload, request, response);
    	List<CostItemDictNo> errorList = new ArrayList<CostItemDictNo>();
		for (int i = 1; i < list.size(); i++) {
			String temp[] = list.get(i);//行
			Map<String, Object> mapVo = new HashMap<String, Object>();
			//模版生成,根据不同字典处理业务
			if("".equals(temp[0]) || "".equals(temp[1])){
				CostItemDictNo costItemDictNo = new CostItemDictNo();
				
					costItemDictNo.setGroup_id(Long.getLong(SessionManager.getGroupId()));
					costItemDictNo.setHos_id(Long.getLong(SessionManager.getHosId()));
					costItemDictNo.setCopy_code(SessionManager.getCopyCode());
					costItemDictNo.setCost_item_id(Long.getLong(temp[3]));
					costItemDictNo.setCost_item_no(Long.getLong(temp[4]));
					costItemDictNo.setCost_item_code(temp[5]);
					costItemDictNo.setCost_item_name(temp[6]);
					costItemDictNo.setUser_code(temp[7]);
					costItemDictNo.setCreate_date(DateUtil.stringToDate(temp[8]));
					costItemDictNo.setNote(temp[9]);
					costItemDictNo.setIs_stop(Integer.getInteger(temp[10]));
				costItemDictNo.setError_type("数据表列存在空的数据列");
				errorList.add(costItemDictNo);
			}else{
				//需要转换各别列 通过SessionManager 里面获取
					mapVo.put("group_id", SessionManager.getGroupId());
				
					mapVo.put("hos_id", SessionManager.getHosId());
				
					mapVo.put("copy_code", SessionManager.getCopyCode());
				
					mapVo.put("cost_item_id",temp[3]);
				
					mapVo.put("cost_item_no",temp[4]);
				
					mapVo.put("cost_item_code",temp[5]);
				
					mapVo.put("cost_item_name",temp[6]);
				
					mapVo.put("user_code",temp[7]);
				
					mapVo.put("create_date",temp[8]);
				
					mapVo.put("note",temp[9]);
				
					mapVo.put("is_stop",temp[10]);
				
					mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("cost_item_name").toString()));
				
					mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("cost_item_name").toString()));
				
			}
			try {
				CostItemDictNo data = new CostItemDictNo();
				data = costItemDictNoService.queryCostItemDictNoByCode(mapVo);
					if(data != null){
					data.setGroup_id(Long.getLong(SessionManager.getGroupId()));
					data.setHos_id(Long.getLong(SessionManager.getHosId()));
					data.setCopy_code(SessionManager.getCopyCode());
					data.setCost_item_id(Long.getLong(temp[3]));
					data.setCost_item_no(Long.getLong(temp[4]));
					data.setCost_item_code(temp[5]);
					data.setCost_item_name(temp[6]);
					data.setUser_code(temp[7]);
					data.setCreate_date(DateUtil.stringToDate(temp[8]));
					data.setNote(temp[9]);
					data.setIs_stop(Integer.getInteger(temp[10]));
					data.setError_type("数据在数据库中已存在");
					errorList.add(data);
					}else{
							if(i==list.size()-1){
							String dataJson = costItemDictNoService.addCostItemDictNo(mapVo);
							JSONObject json = JSONObject.parseObject(dataJson);
							String flag = String.valueOf(json.get("state"));
							if(!flag.equals("true")){
								CostItemDictNo data_error = new CostItemDictNo();
								data_error.setGroup_id(Long.getLong(SessionManager.getGroupId()));
								data_error.setHos_id(Long.getLong(SessionManager.getHosId()));
								data_error.setCopy_code(SessionManager.getCopyCode());
								data_error.setCost_item_id(Long.getLong(temp[3]));
								data_error.setCost_item_no(Long.getLong(temp[4]));
								data_error.setCost_item_code(temp[5]);
								data_error.setCost_item_name(temp[6]);
								data_error.setUser_code(temp[7]);
								data_error.setCreate_date(DateUtil.stringToDate(temp[8]));
								data_error.setNote(temp[9]);
								data_error.setIs_stop(Integer.getInteger(temp[10]));
								data_error.setError_type("导入失败");
								errorList.add(data_error);
								}
							}
					}
			} catch (DataAccessException e) {
				CostItemDictNo data_exc = new CostItemDictNo();
								data_exc.setGroup_id(Long.getLong(SessionManager.getGroupId()));
								data_exc.setHos_id(Long.getLong(SessionManager.getHosId()));
								data_exc.setCopy_code(SessionManager.getCopyCode());
								data_exc.setCost_item_id(Long.getLong(temp[3]));
								data_exc.setCost_item_no(Long.getLong(temp[4]));
								data_exc.setCost_item_code(temp[5]);
								data_exc.setCost_item_name(temp[6]);
								data_exc.setUser_code(temp[7]);
								data_exc.setCreate_date(DateUtil.stringToDate(temp[8]));
								data_exc.setNote(temp[9]);
								data_exc.setIs_stop(Integer.getInteger(temp[10]));
				data_exc.setError_type("导入系统出错");
				errorList.add(data_exc);
			}
		}
		list2.addAll(errorList);
		mode.addAttribute("resultsJson",  ChdJson.toJson(list2, list2.size()));
	    	return "hrp/cost/costitemdictno/costitemdictnoImportMessage";
    }  
	/**
	*成本项目变更表<BR>
	*批量添加
	*/ 
	@RequestMapping(value = "/hrp/cost/costitemdictno/addBatchCostItemDictNo", method = RequestMethod.POST)
	@ResponseBody
    public Map<String, Object> addBatchCostItemDictNo(@RequestParam(value = "ParamVo") String paramVo, Model mode)throws Exception{
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		for ( String id: paramVo.split(",")) {
			String[] ids=id.split("@");
			Map<String, Object> mapVo=new HashMap<String, Object>();
			mapVo.put("group_id",ids[0]);   
			mapVo.put("hos_id",ids[1]);   
			mapVo.put("copy_code",ids[2]);   
			mapVo.put("cost_item_id",ids[3]);   
			mapVo.put("cost_item_no",ids[4]);   
			mapVo.put("cost_item_code",ids[5]);   
			mapVo.put("cost_item_name",ids[6]);   
			mapVo.put("user_code",ids[7]);   
			mapVo.put("create_date",ids[8]);   
			mapVo.put("note",ids[9]);   
			mapVo.put("is_stop",ids[10]);   
			mapVo.put("spell_code",StringTool.toPinyinShouZiMu(ids[6]));   
			mapVo.put("wbx_code",StringTool.toWuBi(ids[6])); 
            listVo.add(mapVo);
        }
		String costItemDictNoJson = costItemDictNoService.addBatchCostItemDictNo(listVo);
	    return JSONObject.parseObject(costItemDictNoJson);
    }
}

