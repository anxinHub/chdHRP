/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/
package com.chd.hrp.cost.controller;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.nutz.lang.Strings;
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
import com.chd.hrp.ass.service.base.AssBaseService;
import com.chd.hrp.cost.entity.CostChargeItemArrt;
import com.chd.hrp.cost.entity.CostChargeKindArrt;
import com.chd.hrp.cost.serviceImpl.CostChargeItemArrtServiceImpl;
import com.chd.hrp.cost.serviceImpl.CostChargeKindArrtServiceImpl;

/**
* @Title. @Description.
* 成本_收费项目字典
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


@Controller
public class CostChargeItemArrtController extends BaseController{
	private static Logger logger = Logger.getLogger(CostChargeItemArrtController.class);
	
	
	@Resource(name = "costChargeItemArrtService")
	private final CostChargeItemArrtServiceImpl costChargeItemArrtService = null;
	
	@Resource(name = "costChargeKindArrtService")
	private final CostChargeKindArrtServiceImpl costChargeKindArrtService = null;
   
	@Resource(name = "assBaseService")
	private final AssBaseService assBaseService = null;
   
	/**
	*成本_收费项目字典<BR>
	*维护页面跳转
	*/
	
	@RequestMapping(value = "/hrp/cost/costchargeitemarrt/costChargeItemArrtMainPage", method = RequestMethod.GET)
	public String costChargeItemArrtMainPage(Model mode) throws Exception {

		return "hrp/cost/costchargeitemarrt/costChargeItemArrtMain";

	}
	/**
	*成本_收费项目字典<BR>
	*维护页面跳转
	*/
	// 添加页面
	@RequestMapping(value = "/hrp/cost/costchargeitemarrt/costChargeItemArrtAddPage", method = RequestMethod.GET)
	public String costChargeItemArrtAddPage(Model mode) throws Exception {

		return "hrp/cost/costchargeitemarrt/costChargeItemArrtAdd";

	}
	/**
	*成本_收费项目字典<BR>
	*保存
	*/
	@RequestMapping(value = "/hrp/cost/costchargeitemarrt/addCostChargeItemArrt", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> addCostChargeItemArrt(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
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
        mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("charge_item_code").toString()));
		//根据名称生成五笔码
        mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("charge_item_code").toString()));
		String costChargeItemArrtJson = costChargeItemArrtService.addCostChargeItemArrt(mapVo);

		return JSONObject.parseObject(costChargeItemArrtJson);
		
	}
	/**
	*成本_收费项目字典<BR>
	*查询
	*/
	@RequestMapping(value = "/hrp/cost/costchargeitemarrt/queryCostChargeItemArrt", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> queryCostChargeItemArrt(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if(mapVo.get("group_id") == null){
		mapVo.put("group_id", SessionManager.getGroupId());
		}
		if(mapVo.get("hos_id") == null){
		mapVo.put("hos_id", SessionManager.getHosId());
		}
		if(mapVo.get("copy_code") == null){
        mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		String costChargeItemArrt = costChargeItemArrtService.queryCostChargeItemArrt(getPage(mapVo));

		return JSONObject.parseObject(costChargeItemArrt);
		
	}
	/**
	 *成本_收费项目字典<BR>
	 *查询
	 */
	@RequestMapping(value = "/hrp/cost/costchargeitemarrt/queryCostChargeItemArrtByKindCode", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> queryCostChargeItemArrtByKindCode(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if(mapVo.get("group_id") == null){
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if(mapVo.get("hos_id") == null){
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if(mapVo.get("copy_code") == null){
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		String costChargeItemArrt = costChargeItemArrtService.queryCostChargeItemArrtByKindCode(getPage(mapVo));
		
		return JSONObject.parseObject(costChargeItemArrt);
		
	}
	/**
	*成本_收费项目字典<BR>
	*删除
	*/
	@RequestMapping(value = "/hrp/cost/costchargeitemarrt/deleteCostChargeItemArrt", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteCostChargeItemArrt(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		String retErrot = "";
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		for ( String id: paramVo.split(",")) {
			String[] ids=id.split("@");
			
			/*String str = assBaseService.isExistsDataByTable("cost_charge_item_arrt", ids[4]);
			if(Strings.isNotBlank(str)){
				retErrot = "{\"error\":\"删除失败，选择的收费项目被以下业务使用：【" + str.substring(0, str.length() - 1) + "】。\",\"state\":\"false\"}";
				return JSONObject.parseObject(retErrot);
			}*/
			
			Map<String, Object> mapVo=new HashMap<String, Object>();
			mapVo.put("group_id",ids[0]);   
			mapVo.put("hos_id",ids[1]);   
			mapVo.put("copy_code",ids[2]);   
			mapVo.put("charge_kind_id",ids[3]);   
			mapVo.put("charge_item_id",ids[4]); 
            listVo.add(mapVo);
        }
		String costChargeItemArrtJson = costChargeItemArrtService.deleteBatchCostChargeItemArrt(listVo);
	   return JSONObject.parseObject(costChargeItemArrtJson);
			
	}
	
	/**
	*成本_收费项目字典<BR>
	*修改页面跳转
	*/
	@RequestMapping(value = "/hrp/cost/costchargeitemarrt/costChargeItemArrtUpdatePage", method = RequestMethod.GET)
	
	public String costChargeItemArrtUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if(mapVo.get("group_id") == null){
		mapVo.put("group_id", SessionManager.getGroupId());
		}
		if(mapVo.get("hos_id") == null){
		mapVo.put("hos_id", SessionManager.getHosId());
		}
		if(mapVo.get("copy_code") == null){
        mapVo.put("copy_code", SessionManager.getCopyCode());
		}
        CostChargeItemArrt costChargeItemArrt = new CostChargeItemArrt();
		costChargeItemArrt = costChargeItemArrtService.queryCostChargeItemArrtByCode(mapVo);
		mode.addAttribute("group_id", costChargeItemArrt.getGroup_id());
		mode.addAttribute("hos_id", costChargeItemArrt.getHos_id());
		mode.addAttribute("copy_code", costChargeItemArrt.getCopy_code());
		mode.addAttribute("charge_kind_id", costChargeItemArrt.getCharge_kind_id());
		mode.addAttribute("charge_kind_name", costChargeItemArrt.getCharge_kind_name());
		mode.addAttribute("charge_item_id", costChargeItemArrt.getCharge_item_id());
		mode.addAttribute("charge_item_code", costChargeItemArrt.getCharge_item_code());
		mode.addAttribute("charge_item_name", costChargeItemArrt.getCharge_item_name());
		mode.addAttribute("price", costChargeItemArrt.getPrice());
		mode.addAttribute("is_stop", costChargeItemArrt.getIs_stop());
		mode.addAttribute("spell_code", costChargeItemArrt.getSpell_code());
		mode.addAttribute("wbx_code", costChargeItemArrt.getWbx_code());
		return "hrp/cost/costchargeitemarrt/costChargeItemArrtUpdate";
	}
	/**
	*成本_收费项目字典<BR>
	*修改保存
	*/	
	
	@RequestMapping(value = "/hrp/cost/costchargeitemarrt/updateCostChargeItemArrt", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateCostChargeItemArrt(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
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
        mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("charge_item_code").toString()));
		//根据名称生成五笔码
        mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("charge_item_code").toString()));
		
		String costChargeItemArrtJson = costChargeItemArrtService.updateCostChargeItemArrt(mapVo);
		
		return JSONObject.parseObject(costChargeItemArrtJson);
	}
	
	
	@RequestMapping(value = "/hrp/cost/costchargeitemarrt/costChargeItemArrtImportPage", method = RequestMethod.POST)
	@ResponseBody
	public String costChargeItemArrtImportPage(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {

		try {

			String reJson = costChargeItemArrtService.costChargeItemArrtImportPage(mapVo);

			return reJson;

		}
		catch (Exception e) {

			return "{\"error\":\"" + e.getMessage() + "\"}";

		}
	}
	
	/**
	*成本_收费项目字典<BR>
	*批量添加
	*/ 
	@RequestMapping(value = "/hrp/cost/costchargeitemarrt/addBatchCostChargeItemArrt", method = RequestMethod.POST)
	@ResponseBody
    public Map<String, Object> addBatchCostChargeItemArrt(@RequestParam(value = "ParamVo") String paramVo, Model mode)throws Exception{
		
		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();
		List<CostChargeItemArrt> list_err = new ArrayList<CostChargeItemArrt>();
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
		String s = null;
		Iterator it = json.iterator();
		try {
			while (it.hasNext()) {
			StringBuffer err_sb = new StringBuffer();
			JSONObject jsonObj = JSONObject.parseObject(it.next().toString());
			
			mapVo.put("charge_kind_code", jsonObj.get("charge_kind_code"));
			mapVo.put("charge_kind_name", jsonObj.get("charge_kind_name"));
			CostChargeKindArrt chargeKindArrt = costChargeKindArrtService.queryCostChargeKindArrtByCode(mapVo);
			if(chargeKindArrt != null){
				mapVo.put("charge_kind_id", chargeKindArrt.getCharge_kind_id());
			}else{
				err_sb.append("收入类别不存在  ");
			}
			mapVo.put("charge_item_code", jsonObj.get("charge_item_code"));
			mapVo.put("charge_item_name", jsonObj.get("charge_item_name"));
			mapVo.put("price", jsonObj.get("price"));
			mapVo.put("is_stop", jsonObj.get("is_stop"));
			mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("charge_item_name").toString()));
			mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("charge_item_name").toString()));
				CostChargeItemArrt data_exc_extis = costChargeItemArrtService.queryCostChargeItemArrtByCode(mapVo);
				if (data_exc_extis != null) {
					err_sb.append("编码已经存在！ ");
				}
				CostChargeItemArrt costChargeItemArrt = new CostChargeItemArrt();
				if (err_sb.toString().length() > 0) {
					costChargeItemArrt.setCharge_kind_code(mapVo.get("charge_kind_code").toString());
					costChargeItemArrt.setCharge_kind_name(mapVo.get("charge_kind_name").toString());
					costChargeItemArrt.setCharge_item_code(mapVo.get("charge_item_code").toString());
					costChargeItemArrt.setCharge_item_name(mapVo.get("charge_item_name").toString());
					costChargeItemArrt.setPrice(Double.valueOf(mapVo.get("price").toString()));
					costChargeItemArrt.setIs_stop(Integer.getInteger(mapVo.get("is_stop").toString()));
					costChargeItemArrt.setError_type(err_sb.toString());
					list_err.add(costChargeItemArrt);
				} else {
					costChargeItemArrtService.addCostChargeItemArrt(mapVo);
				}
			}
		} catch (DataAccessException e) {
			return JSONObject.parseObject("{\"msg\":\"导入系统出错,请重新导入.\",\"state\":\"err\"}");
		}
		if (list_err.size() > 0) {
			return JSONObject.parseObject(ChdJson.toJson(list_err,list_err.size()));
		} else {
			return JSONObject.parseObject("{\"msg\":\"导入成功.\",\"state\":\"true\"}");
		}
    }
}

