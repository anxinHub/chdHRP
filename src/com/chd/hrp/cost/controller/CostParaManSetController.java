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
import com.chd.base.util.DateUtil;
import com.chd.base.util.Plupload;
import com.chd.base.util.UploadUtil;
import com.chd.hrp.cost.entity.CostDeptParaDict;
import com.chd.hrp.cost.entity.CostItemDictNo;
import com.chd.hrp.cost.entity.CostParaManSet;
import com.chd.hrp.cost.service.CostDeptParaDictService;
import com.chd.hrp.cost.service.CostItemDictNoService;
import com.chd.hrp.cost.service.CostParaManSetDataService;
import com.chd.hrp.cost.service.CostParaManSetService;
import com.chd.hrp.cost.service.CostParaService;
import com.chd.hrp.sys.entity.DeptDict;
import com.chd.hrp.sys.service.DeptDictService;

/**
 * @Title. @Description. 管理分摊设置
 * @Author: LiuYingDuo
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Controller
public class CostParaManSetController extends BaseController {

	private static Logger logger = Logger.getLogger(CostParaManSetController.class);

	@Resource(name = "costParaManSetService")
	private final CostParaManSetService costParaManSetService = null;
	
	@Resource(name = "costParaService")
	private final CostParaService costParaService = null;
	
	@Resource(name = "costItemDictNoService")
	private final CostItemDictNoService costItemDictNoService = null;
	
	@Resource(name = "deptDictService")
	private final DeptDictService deptDictService = null;
	
	@Resource(name = "costDeptParaDictService")
	private final CostDeptParaDictService costDeptParaDictService = null;
	
	@Resource(name = "costParaManSetDataService")
	private final CostParaManSetDataService costParaManSetDataService = null;

	/**
	 * 管理分摊设置<BR>
	 * 维护页面跳转
	 */

	@RequestMapping(value = "/hrp/cost/costparamanset/costParaManSetMainPage", method = RequestMethod.GET)
	public String costParaManSetMainPage(Model mode) throws Exception {

		return "hrp/cost/costparamanset/costParaManSetMain";

	}

	/**
	 * 管理分摊设置<BR>
	 * 维护页面跳转
	 */
	// 添加页面
	@RequestMapping(value = "/hrp/cost/costparamanset/costParaManSetAddPage", method = RequestMethod.GET)
	public String costParaManSetAddPage(Model mode) throws Exception {

		return "hrp/cost/costparamanset/costParaManSetAdd";

	}

	/**
	 * 管理分摊设置<BR>
	 * 维护页面跳转
	 */
	// 快速填充
	@RequestMapping(value = "/hrp/cost/costparamanset/costParaManSetFastPage", method = RequestMethod.GET)
	public String costParaManSetFastPage(Model mode) throws Exception {

		return "hrp/cost/costparamanset/costParaManSetFast";

	}
	
	/**
	 * 管理分摊设置<BR>
	 * 快速填充保存
	 */
	@RequestMapping(value = "/hrp/cost/costparamanset/fastCostParaManSet", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> fastCostParaManSet(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		String costParaManSetJson = null;
		
		List list_batch = new ArrayList();
		
		List<String> list_dept = new ArrayList<String>();
		
		List<String> list_server_dept = new ArrayList<String>();
		
		List<String> list_cost_item = new ArrayList<String>();
		
		String dept = (String) mapVo.get("dept");
		
		String server_dept = (String) mapVo.get("server_dept");
		
		String cost_item = (String) mapVo.get("cost_item");
		
		Map<String, Object> entityMap = new HashMap<String, Object>();
		
		if (entityMap.get("group_id") == null) {
			
			entityMap.put("group_id", SessionManager.getGroupId());
			
		}
		
		if (entityMap.get("hos_id") == null) {
			
			entityMap.put("hos_id", SessionManager.getHosId());
			
		}
		
		if (entityMap.get("copy_code") == null) {
			
			entityMap.put("copy_code", SessionManager.getCopyCode());
		}
		
		entityMap.put("is_stop", "0");
		
		entityMap.put("is_last", "1");
		
		if(StringUtils.isNotEmpty(dept)){
			
			String[] dept_split = dept.split(";");
			
			for(int i =0 ; i<dept_split.length; i++){
				
				list_dept.add(dept_split[i]);
				
			}
			
		}else{
			
			entityMap.put("kind_code", "('04')");

			List<DeptDict> list= deptDictService.queryDeptDictNo(entityMap);
			
			for(int i =0 ;i<list.size();i++){
				
				DeptDict dd = list.get(i);
				
				list_dept.add(dd.getDept_id()+"."+dd.getDept_no());
				
			}
			
			
		}
		
		if(StringUtils.isNotEmpty(server_dept)){
			
			String[] server_dept_split = server_dept.split(";");
			
			for(int i = 0 ;i<server_dept_split.length;i++){
				
				list_server_dept.add(server_dept_split[i]);
				
			}
			
		}else{

			List<DeptDict> list= deptDictService.queryDeptDictNo(entityMap);
			
			for(int i =0 ;i<list.size();i++){
				
				DeptDict dd = list.get(i);
				
				list_server_dept.add(dd.getDept_id()+"."+dd.getDept_no());
				
			}
			
		}
		if(StringUtils.isNotEmpty(cost_item)){
			
			String[] cost_item_split = cost_item.split(";");
			
			for(int i = 0 ;i<cost_item_split.length;i++){
				
				list_cost_item.add(cost_item_split[i]);
				
			}
			
		}else{
			
			List<CostItemDictNo> list = costItemDictNoService.queryItemDictNo(entityMap);
			
			for(int i=0; i<list.size();i++){

				CostItemDictNo cid = list.get(i);
				
				list_cost_item.add(cid.getCost_item_id()+"."+cid.getCost_item_no());
				
			}
			
		}	

        
        for(int i =0 ; i<list_dept.size(); i++){
        	
        	for(int j = 0; j<list_server_dept.size(); j++){
        		
        		for(int k =0 ; k<list_cost_item.size(); k++){
        			
        			Map<String, Object> para_map = new HashMap<String, Object>();
        			
        			if (para_map.get("group_id") == null) {
        				
        				para_map.put("group_id", SessionManager.getGroupId());
        				
        			}
        			
        			if (para_map.get("hos_id") == null) {
        				
        				para_map.put("hos_id", SessionManager.getHosId());
        				
        			}
        			
        			if (para_map.get("copy_code") == null) {
        				
        				para_map.put("copy_code", SessionManager.getCopyCode());
        				
        			}
        			
        			para_map.put("dept_id", list_dept.get(i).split("\\.")[0]);
        			
        			para_map.put("dept_no", list_dept.get(i).split("\\.")[1]);
        			
        			para_map.put("server_dept_id", list_server_dept.get(j).split("\\.")[0]);
        			
        			para_map.put("server_dept_no", list_server_dept.get(j).split("\\.")[1]);
        			
        			para_map.put("cost_item_id", list_cost_item.get(k).split("\\.")[0]);
        			
        			para_map.put("cost_item_no", list_cost_item.get(k).split("\\.")[1]);
        			
        			para_map.put("acc_year", mapVo.get("acc_year"));
        			
        			para_map.put("acc_month", mapVo.get("acc_month"));
        			
        			para_map.put("para_code", mapVo.get("para_code"));
        			
        			list_batch.add(para_map);

        		}

        	}
        }

        
    	costParaManSetDataService.deleteBatchCostParaManSetData(list_batch);
    	
    	costParaManSetService.deleteBatchCostParaManSet(list_batch);
    	
    	costParaManSetJson=costParaManSetService.addBatchCostParaManSet(list_batch);

		return JSONObject.parseObject(costParaManSetJson);

	}
	/**
	 * 管理分摊设置<BR>
	 * 继承
	 */
	@RequestMapping(value = "/hrp/cost/costparamanset/inheritCostParaManSet", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> inheritCostParaManSet(@RequestParam(value = "year_month", required = true) String year_month, Model mode) throws Exception {

		Map<String, Object> para_map = new HashMap<String, Object>();
		
		if (para_map.get("group_id") == null) {
			
			para_map.put("group_id", SessionManager.getGroupId());
			
		}
		if (para_map.get("hos_id") == null) {
			
			para_map.put("hos_id", SessionManager.getHosId());
			
		}
		if (para_map.get("copy_code") == null) {
			
			para_map.put("copy_code", SessionManager.getCopyCode());
			
		}
		
		para_map.put("acc_year", year_month.substring(0,4));
		
		para_map.put("acc_month", year_month.substring(4,6));
		
		para_map.put("is_flag", "0");

		List<CostParaManSet> list = costParaManSetService.queryCostParaManSetNoPage(para_map);
		
		if(list.size() == 0){
			
			return JSONObject.parseObject("{\"msg\":\"统计年月:"+para_map.get("acc_year")+para_map.get("acc_month")+"没有数据，无法继承到下个月\",\"state\":\"false\"}");
		}
		
		
		List list_batch = new ArrayList();
		
		for(int i =0; i<list.size(); i++){
			
			CostParaManSet cps = list.get(i);
			
			para_map = new HashMap<String, Object>();
			
			if (para_map.get("group_id") == null) {
				
				para_map.put("group_id", SessionManager.getGroupId());
				
			}
			if (para_map.get("hos_id") == null) {
				
				para_map.put("hos_id", SessionManager.getHosId());
				
			}
			
			if (para_map.get("copy_code") == null) {
				
				para_map.put("copy_code", SessionManager.getCopyCode());
			}
			
			para_map.put("dept_id", cps.getDept_id());
			
			para_map.put("dept_no", cps.getDept_no());
			
			para_map.put("server_dept_id", cps.getServer_dept_id());
			
			para_map.put("server_dept_no", cps.getServer_dept_no());
			
			para_map.put("cost_item_id", cps.getCost_item_id());
			
			para_map.put("cost_item_no", cps.getCost_item_no());
			
			para_map.put("acc_year", DateUtil.getNextYear_Month(year_month).substring(0,4));
			
			para_map.put("acc_month", DateUtil.getNextYear_Month(year_month).substring(4,6));
			
			para_map.put("para_code", cps.getPara_code());
			
			list_batch.add(para_map);
			
		}
		
		costParaManSetDataService.deleteBatchCostParaManSetData(list_batch);
    	
    	costParaManSetService.deleteBatchCostParaManSet(list_batch);
		
		String costParaManSetJson=costParaManSetService.addBatchCostParaManSet(list_batch);

		return JSONObject.parseObject(costParaManSetJson);

	}
	/**
	 * 管理分摊设置<BR>
	 * 保存
	 */
	@RequestMapping(value = "/hrp/cost/costparamanset/addCostParaManSet", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addCostParaManSet(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		String costParaManSetJson = costParaManSetService.addCostParaManSet(mapVo);

		return JSONObject.parseObject(costParaManSetJson);

	}

	/**
	 * 管理分摊设置<BR>
	 * 查询
	 */
	@RequestMapping(value = "/hrp/cost/costparamanset/queryCostParaManSet", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryCostParaManSet(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		
		String para_value = (String) ((SessionManager.getCostParaMap().get("03001")==null)?0:SessionManager.getCostParaMap().get("03001").toString());
		
		mapVo.put("is_flag", para_value);
		
		String costParaManSet = costParaManSetService.queryCostParaManSet(getPage(mapVo));

		return JSONObject.parseObject(costParaManSet);

	}

	/**
	 * 管理分摊设置<BR>
	 * 删除
	 */
	@RequestMapping(value = "/hrp/cost/costparamanset/deleteCostParaManSet", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteCostParaManSet(@RequestParam(value = "ParamVo") String paramVo, Model mode) throws Exception {

		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();
		for (String id : paramVo.split(",")) {
			String[] ids = id.split("@");
			Map<String, Object> mapVo = new HashMap<String, Object>();
			mapVo.put("group_id", ids[0]);

			mapVo.put("hos_id", ids[1]);

			mapVo.put("copy_code", ids[2]);
			
			mapVo.put("acc_year", ids[3]);
			
			mapVo.put("acc_month", ids[4]);

			mapVo.put("dept_id", ids[5]);

			mapVo.put("server_dept_id", ids[6]);

			mapVo.put("cost_item_id", ids[7]);

			listVo.add(mapVo);
		}
		String costParaManSetJson = costParaManSetService.deleteBatchCostParaManSet(listVo);
		return JSONObject.parseObject(costParaManSetJson);

	}

	/**
	 * 管理分摊设置<BR>
	 * 修改页面跳转
	 */
	@RequestMapping(value = "/hrp/cost/costparamanset/costParaManSetUpdatePage", method = RequestMethod.GET)
	public String costParaManSetUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		
		String para_value = (String) ((SessionManager.getCostParaMap().get("03001")==null)?0:SessionManager.getCostParaMap().get("03001").toString());
		
		mapVo.put("is_flag", para_value);
		
		CostParaManSet costParaManSet = new CostParaManSet();
		
		costParaManSet = costParaManSetService.queryCostParaManSetByCode(mapVo);
		
		mode.addAttribute("group_id", costParaManSet.getGroup_id());

		mode.addAttribute("hos_id", costParaManSet.getHos_id());

		mode.addAttribute("copy_code", costParaManSet.getCopy_code());

		mode.addAttribute("dept_id", costParaManSet.getDept_id());

		mode.addAttribute("dept_no", costParaManSet.getDept_no());

		mode.addAttribute("server_dept_id", costParaManSet.getServer_dept_id());

		mode.addAttribute("server_dept_no", costParaManSet.getServer_dept_no());

		mode.addAttribute("cost_item_id", costParaManSet.getCost_item_id());

		mode.addAttribute("cost_item_no", costParaManSet.getCost_item_no());

		mode.addAttribute("para_code", costParaManSet.getPara_code());

		mode.addAttribute("dept_code", costParaManSet.getDept_name());
		
		mode.addAttribute("dept_name", costParaManSet.getDept_name());
		
		mode.addAttribute("server_dept_code", costParaManSet.getServer_dept_name());
		
		mode.addAttribute("server_dept_name", costParaManSet.getServer_dept_name());
		
		mode.addAttribute("cost_item_code", costParaManSet.getCost_item_name());
		
		mode.addAttribute("cost_item_name", costParaManSet.getCost_item_name());
		
		mode.addAttribute("para_name", costParaManSet.getPara_name());
		
		mode.addAttribute("acc_year", costParaManSet.getAcc_year());
		
		mode.addAttribute("acc_month", costParaManSet.getAcc_month());

		return "hrp/cost/costparamanset/costParaManSetUpdate";
	}

	/**
	 * 管理分摊设置<BR>
	 * 修改保存
	 */

	@RequestMapping(value = "/hrp/cost/costparamanset/updateCostParaManSet", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateCostParaManSet(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}

		String costParaManSetJson = costParaManSetService.updateCostParaManSet(mapVo);

		return JSONObject.parseObject(costParaManSetJson);
	}

	// 导入页面
	@RequestMapping(value = "/hrp/cost/costparamanset/costParaManSetImportPage", method = RequestMethod.GET)
	public String costParaManSetImportPage(Model mode) throws Exception {

		return "hrp/cost/costparamanset/costParaManSetImport";

	}

	// 下载导入模版
	@RequestMapping(value = "hrp/cost/costparamanset/downTemplate", method = RequestMethod.GET)
	public String downTemplate(Plupload plupload, HttpServletRequest request, HttpServletResponse response, Model mode) throws IOException {

		printTemplate(request, response, "cost\\基础设置", "管理分摊设置.xls");

		return null;
	}

	/**
	 * 管理分摊设置<BR>
	 * 导入
	 */
	@RequestMapping(value = "/hrp/cost/costparamanset/readCostParaManSetFiles", method = RequestMethod.POST)
	public String readChargeKindDictFiles(Plupload plupload, HttpServletRequest request, HttpServletResponse response, Model mode) throws IOException {

		List<Map<String, Object>> list_batch = new ArrayList<Map<String, Object>>();

		List<CostParaManSet> list_err = new ArrayList<CostParaManSet>();

		List<String[]> list = UploadUtil.readFile(plupload, request, response);

		try {
			for (int i = 1; i < list.size(); i++) {

				StringBuffer err_sb = new StringBuffer();

				CostParaManSet costParaManSet = new CostParaManSet();

				String temp[] = list.get(i);// 行

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
				
				if (StringUtils.isNotEmpty(temp[0])) {
					
					costParaManSet.setYear_month(temp[0]);
					
					mapVo.put("year_month", temp[0]);
					
				} else {
					err_sb.append("统计年月为空  ");
				}
				
				if (StringUtils.isNotEmpty(temp[1])) {

					mapVo.put("dept_code", temp[1]);
					
				} else {
					
					err_sb.append("服务科室编码为空  ");
					
				}
				
				if (StringUtils.isNotEmpty(temp[2])) {

					mapVo.put("dept_name", temp[2]);
					
				} else {
					
					err_sb.append("服务科室名称为空  ");
					
				}
				
				if (StringUtils.isNotEmpty(temp[3])) {

					mapVo.put("server_dept_code", temp[3]);
					
				} else {
					
					err_sb.append("受益科室编码为空  ");
					
				}
				
				if (StringUtils.isNotEmpty(temp[4])) {

					mapVo.put("server_dept_name", temp[4]);
					
				} else {
					
					err_sb.append("受益科室名称为空  ");
					
				}
				
				if (StringUtils.isNotEmpty(temp[5])) {

					mapVo.put("cost_item_code", temp[5]);
					
				} else {
					
					err_sb.append("成本项目编码为空  ");
					
				}
				
				if (StringUtils.isNotEmpty(temp[6])) {

					mapVo.put("cost_item_name", temp[6]);
					
				} else {
					
					err_sb.append("成本项目名称为空  ");
					
				}
				
				if (StringUtils.isNotEmpty(temp[7])) {

					mapVo.put("para_code", temp[7]);
					
				} else {
					
					err_sb.append("分摊参数编码为空  ");
					
				}
				if (StringUtils.isNotEmpty(temp[8])) {

					mapVo.put("para_name", temp[8]);
					
				} else {
					
					err_sb.append("分摊参数名称为空  ");
					
				}
				
				Map<String, Object> byCodeMap = new HashMap<String, Object>();
				
				byCodeMap.put("group_id", mapVo.get("group_id"));
				
				byCodeMap.put("hos_id", mapVo.get("hos_id"));
				
				byCodeMap.put("copy_code", mapVo.get("copy_code"));
				
				byCodeMap.put("is_stop", "0");
				
				byCodeMap.put("dept_code", mapVo.get("dept_code"));
				
				byCodeMap.put("kind_code", "('04')");
				
				List<DeptDict> list_deptDict = deptDictService.queryDeptDictNo(byCodeMap);
				
				if(list_deptDict.size()>0){
					
					DeptDict deptDict = list_deptDict.get(0);
					
					mapVo.put("dept_id", deptDict.getDept_id());
					
					mapVo.put("dept_no", deptDict.getDept_no());
					
					costParaManSet.setDept_code((String)mapVo.get("dept_code"));
					
					costParaManSet.setDept_name((String)mapVo.get("dept_name"));
					
				}else{
					
					costParaManSet.setDept_code((String)mapVo.get("dept_code"));
					
					costParaManSet.setDept_name((String)mapVo.get("dept_name"));
					
					err_sb.append("服务科室不存在 ");
					
				}
				
				
				byCodeMap.put("kind_code", "");
				
				byCodeMap.put("dept_code", mapVo.get("server_dept_code"));
				
				DeptDict serverDeptDict = deptDictService.queryDeptDictByDeptCode(byCodeMap);
				
				if(serverDeptDict != null ){
					
					mapVo.put("server_dept_id", serverDeptDict.getDept_id());
					
					mapVo.put("server_dept_no", serverDeptDict.getDept_no());
					
					costParaManSet.setServer_dept_code((String)mapVo.get("server_dept_code"));
					
					costParaManSet.setServer_dept_name((String)mapVo.get("server_dept_name"));
					
				}else{
					
					costParaManSet.setServer_dept_code((String)mapVo.get("server_dept_code"));
					
					costParaManSet.setServer_dept_name((String)mapVo.get("server_dept_name"));
					
					err_sb.append("受益科室不存在 ");
					
				}
				
				byCodeMap.put("cost_item_code", mapVo.get("cost_item_code"));

				CostItemDictNo cidn = costItemDictNoService.queryCostItemDictNoByCode(byCodeMap);

				if(cidn != null ){
					
					mapVo.put("cost_item_id", cidn.getCost_item_id());
					
					mapVo.put("cost_item_no", cidn.getCost_item_no());
					
					costParaManSet.setCost_item_code((String)mapVo.get("cost_item_code"));
					
					costParaManSet.setCost_item_name((String)mapVo.get("cost_item_name"));
					
				}else{
					
					costParaManSet.setCost_item_code((String)mapVo.get("cost_item_code"));
					
					costParaManSet.setCost_item_name((String)mapVo.get("cost_item_name"));
					
					err_sb.append("成本项目不存在 ");
					
				}
				
				byCodeMap.put("para_code", mapVo.get("para_code"));

				CostDeptParaDict cdpd = costDeptParaDictService.queryCostDeptParaDictByCode(byCodeMap);

				if(cdpd != null ){
					
					mapVo.put("para_code", cdpd.getPara_code());
					
					costParaManSet.setPara_code((String)mapVo.get("para_code"));
					
					costParaManSet.setPara_name((String)mapVo.get("para_name"));
					
				}else{
					
					costParaManSet.setPara_code((String)mapVo.get("para_code"));
					
					costParaManSet.setPara_name((String)mapVo.get("para_name"));
					
					err_sb.append("分摊参数不存在 ");
					
				}
				
				byCodeMap.put("dept_id", mapVo.get("dept_id"));
				
				byCodeMap.put("server_dept_id", mapVo.get("server_dept_id"));
				
				byCodeMap.put("cost_item_id", mapVo.get("cost_item_id"));
				
				byCodeMap.put("dept_code", "");
				
				byCodeMap.put("server_dept_code", "");
				
				byCodeMap.put("cost_item_code", "");
				
				byCodeMap.put("para_code", "");
				
				byCodeMap.put("is_flag", 0);
				
				CostParaManSet data_exc_extis = costParaManSetService.queryCostParaManSetByCode(byCodeMap);

				// 根据不同业务提示相关信息

				if (data_exc_extis != null) {
					
					err_sb.append("已存在此分摊配置 ");
					
				}
				if (err_sb.toString().length() > 0) {

					costParaManSet.setError_type(err_sb.toString());

					list_err.add(costParaManSet);
					
				} else {

					list_batch.add(mapVo);

				}
			}
		}
		catch (DataAccessException e) {

			CostParaManSet data_exc = new CostParaManSet();

			data_exc.setError_type("导入系统出错");

			list_err.add(data_exc);
		}

		if (list_batch.size() > 0) {

			costParaManSetService.addBatchCostParaManSet(list_batch);

		}

		response.getWriter().print(ChdJson.toJson(list_err, list_err.size()));

		return null;
	}

	/**
	 * 管理分摊设置<BR>
	 * 批量添加
	 */
	@RequestMapping(value = "/hrp/cost/costparamanset/addBatchCostParaManSet", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addBatchCostParaManSet(@RequestParam(value = "ParamVo") String paramVo, Model mode) throws Exception {

		List<Map<String, Object>> list_batch = new ArrayList<Map<String, Object>>();

		List<CostParaManSet> list_err = new ArrayList<CostParaManSet>();

		JSONArray json = JSONArray.parseArray(paramVo);

		Iterator it = json.iterator();

		try {
			while (it.hasNext()) {

				Map<String, Object> mapVo = new HashMap<String, Object>();

				StringBuffer err_sb = new StringBuffer();

				JSONObject jsonObj = JSONObject.parseObject(it.next().toString());

				if (mapVo.get("group_id") == null) {

					mapVo.put("group_id", SessionManager.getGroupId());
				}
				if (mapVo.get("hos_id") == null) {

					mapVo.put("hos_id", SessionManager.getHosId());
				}
				if (mapVo.get("copy_code") == null) {

					mapVo.put("copy_code", SessionManager.getCopyCode());
				}

				mapVo.put("year_month", jsonObj.get("year_month"));
				
				mapVo.put("dept_code", jsonObj.get("dept_code"));

				mapVo.put("dept_name", jsonObj.get("dept_name"));

				mapVo.put("server_dept_code", jsonObj.get("server_dept_code"));

				mapVo.put("server_dept_name", jsonObj.get("server_dept_name"));

				mapVo.put("cost_item_code", jsonObj.get("cost_item_code"));

				mapVo.put("cost_item_name", jsonObj.get("cost_item_name"));

				mapVo.put("para_code", jsonObj.get("para_code"));
				
				mapVo.put("para_name", jsonObj.get("para_name"));
				
				Map<String, Object> byCodeMap = new HashMap<String, Object>();
				
				byCodeMap.put("group_id", mapVo.get("group_id"));
				
				byCodeMap.put("hos_id", mapVo.get("hos_id"));
				
				byCodeMap.put("copy_code", mapVo.get("copy_code"));
				
				byCodeMap.put("is_stop", "0");
				
				byCodeMap.put("dept_code", mapVo.get("dept_code"));
				
				byCodeMap.put("kind_code", "('04')");
				
				List<DeptDict> list_deptDict = deptDictService.queryDeptDictNo(byCodeMap);
				
				if(list_deptDict.size()>0){
					
					DeptDict deptDict = list_deptDict.get(0);
					
					mapVo.put("dept_id", deptDict.getDept_id());
					
					mapVo.put("dept_no", deptDict.getDept_no());
					
				}else{
					
					err_sb.append("服务科室不存在 ");
					
				}
	
				byCodeMap.put("kind_code", "");
				
				byCodeMap.put("dept_code", mapVo.get("server_dept_code"));
				
				DeptDict serverDeptDict = deptDictService.queryDeptDictByDeptCode(byCodeMap);
				
				if(serverDeptDict != null ){
					
					mapVo.put("server_dept_id", serverDeptDict.getDept_id());
					
					mapVo.put("server_dept_no", serverDeptDict.getDept_no());
					
				}else{
					
					err_sb.append("受益科室不存在 ");
					
				}
				
				byCodeMap.put("cost_item_code", mapVo.get("cost_item_code"));

				CostItemDictNo cidn = costItemDictNoService.queryCostItemDictNoByCode(byCodeMap);

				if(cidn != null ){
					
					mapVo.put("cost_item_id", cidn.getCost_item_id());
					
					mapVo.put("cost_item_no", cidn.getCost_item_no());
					
				}else{
					
					err_sb.append("成本项目不存在 ");
					
				}
				
				byCodeMap.put("para_code", mapVo.get("para_code"));

				CostDeptParaDict cdpd = costDeptParaDictService.queryCostDeptParaDictByCode(byCodeMap);

				if(cdpd != null ){
					
					mapVo.put("para_code", cdpd.getPara_code());
					
				}else{
					
					err_sb.append("分摊参数不存在 ");
					
				}
				
				byCodeMap.put("dept_id", mapVo.get("dept_id"));
				
				byCodeMap.put("server_dept_id", mapVo.get("server_dept_id"));
				
				byCodeMap.put("cost_item_id", mapVo.get("cost_item_id"));
				
				byCodeMap.put("dept_code", "");
				
				byCodeMap.put("server_dept_code", "");
				
				byCodeMap.put("cost_item_code", "");
				
				byCodeMap.put("para_code", "");
				
				byCodeMap.put("is_flag", 0);
				
				CostParaManSet data_exc_extis = costParaManSetService.queryCostParaManSetByCode(byCodeMap);

				if (data_exc_extis != null) {
					
					err_sb.append("已存在此分摊配置  ");
					
				}

				CostParaManSet costParaManSet = new CostParaManSet();

				if (err_sb.toString().length() > 0) {
					
					costParaManSet.setYear_month(mapVo.get("year_month").toString());
					
					costParaManSet.setDept_code(mapVo.get("dept_code").toString());

					costParaManSet.setDept_name(mapVo.get("dept_name").toString());

					costParaManSet.setServer_dept_code(mapVo.get("server_dept_code").toString());

					costParaManSet.setServer_dept_name(mapVo.get("server_dept_name").toString());

					costParaManSet.setCost_item_code(mapVo.get("cost_item_code").toString());

					costParaManSet.setCost_item_name(mapVo.get("cost_item_name").toString());

					costParaManSet.setPara_code(mapVo.get("para_code").toString());
					
					costParaManSet.setPara_name(mapVo.get("para_name").toString());

					costParaManSet.setError_type(err_sb.toString());

					list_err.add(costParaManSet);
					
				} else {

					list_batch.add(mapVo);

				}
			}

			if (list_batch.size() > 0) {

				costParaManSetService.addBatchCostParaManSet(list_batch);

			}
		}
		catch (DataAccessException e) {

			return JSONObject.parseObject("{\"msg\":\"导入系统出错,请重新导入.\",\"state\":\"err\"}");

		}
		if (list_err.size() > 0) {
			return JSONObject.parseObject(ChdJson.toJson(list_err, list_err.size()));

		} else {
			return JSONObject.parseObject("{\"msg\":\"导入成功.\",\"state\":\"true\"}");

		}
	}
}
