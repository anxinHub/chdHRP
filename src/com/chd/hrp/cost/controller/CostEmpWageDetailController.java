/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/
package com.chd.hrp.cost.controller;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
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
import com.chd.base.MyConfig;
import com.chd.base.SessionManager;
import com.chd.base.util.ChdJson;
import com.chd.base.util.ExcelWriter;
import com.chd.base.util.Plupload;
import com.chd.base.util.UploadUtil;
import com.chd.hrp.cost.entity.CostBonusSchemeSet;
import com.chd.hrp.cost.entity.CostEmpAttr;
import com.chd.hrp.cost.entity.CostEmpWageDetail;
import com.chd.hrp.cost.entity.CostWageSchemeSet;
import com.chd.hrp.cost.service.CostParaService;
import com.chd.hrp.cost.serviceImpl.CostEmpAttrServiceImpl;
import com.chd.hrp.cost.serviceImpl.CostEmpWageDetailServiceImpl;
import com.chd.hrp.cost.serviceImpl.CostWageSchemeSetServiceImpl;
import com.chd.hrp.sys.entity.DeptDict;
import com.chd.hrp.sys.serviceImpl.DeptDictServiceImpl;

/**
* @Title. @Description.
* 人员工资明细数据表
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


@Controller
public class CostEmpWageDetailController extends BaseController{
	private static Logger logger = Logger.getLogger(CostEmpWageDetailController.class);
	
	
	@Resource(name = "costEmpWageDetailService")
	private final CostEmpWageDetailServiceImpl costEmpWageDetailService = null;
	
	
	@Resource(name = "costWageSchemeSetService")
	private final CostWageSchemeSetServiceImpl costWageSchemeSetService = null;
	
	@Resource(name = "deptDictService")
	private final DeptDictServiceImpl deptDictService = null;
	
	@Resource(name = "costEmpAttrService")
	private final CostEmpAttrServiceImpl costEmpAttrService = null;
	
	@Resource(name = "costParaService")
	private final CostParaService costParaService = null;
   
   
	/**
	*人员工资明细数据表<BR>
	*维护页面跳转
	*/
	
	@RequestMapping(value = "/hrp/cost/costempwagedetail/costEmpWageDetailMainPage", method = RequestMethod.GET)
	public String costEmpWageDetailMainPage(Model mode) throws Exception {

		return "hrp/cost/costempwagedetail/costEmpWageDetailMain";

	}
	/**
	*人员工资明细数据表<BR>
	*维护页面跳转
	*/
	// 添加页面
	@RequestMapping(value = "/hrp/cost/costempwagedetail/costEmpWageDetailAddPage", method = RequestMethod.GET)
	public String costEmpWageDetailAddPage(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
			if(mapVo.get("group_id") == null){
			mapVo.put("group_id", SessionManager.getGroupId());
			}
			if(mapVo.get("hos_id") == null){
			mapVo.put("hos_id", SessionManager.getHosId());
			}
			if(mapVo.get("copy_code") == null){
	        mapVo.put("copy_code", SessionManager.getCopyCode());
			}
			List<CostWageSchemeSet> list = null;
			String scheme_id = mapVo.get("scheme_id").toString();
			if(!"".equals(scheme_id) && scheme_id != null){
			    list = costWageSchemeSetService.queryCostWageSchemeSetByTitleList(mapVo);
				mode.addAttribute("wageSchemeSetList", list);
				mode.addAttribute("scheme_id", scheme_id);
			}else{
				mode.addAttribute("wageSchemeSetList", list = new ArrayList<CostWageSchemeSet>());
				mode.addAttribute("scheme_id", "");
			}
			
			return "hrp/cost/costempwagedetail/costEmpWageDetailAdd";

	}
	
	@RequestMapping(value = "/hrp/cost/costempwagedetail/queryWageSchemeSet", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> queryWageSchemeSet(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if(mapVo.get("group_id") == null){
		mapVo.put("group_id", SessionManager.getGroupId());
		}
		if(mapVo.get("hos_id") == null){
		mapVo.put("hos_id", SessionManager.getHosId());
		}
		if(mapVo.get("copy_code") == null){
        mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		String costEmpWageDetail = "";
		costEmpWageDetail = costWageSchemeSetService.queryCostWageSchemeSetByTitle(mapVo);
		return JSONObject.parseObject(costEmpWageDetail);
		
	}
	
	/**
	*人员工资明细数据表<BR>
	*保存
	*/
	@RequestMapping(value = "/hrp/cost/costempwagedetail/addCostEmpWageDetail", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> addCostEmpWageDetail(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if(mapVo.get("group_id") == null){
		mapVo.put("group_id", SessionManager.getGroupId());
		}
		if(mapVo.get("hos_id") == null){
		mapVo.put("hos_id", SessionManager.getHosId());
		}
		if(mapVo.get("copy_code") == null){
        mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		String costEmpWageDetailJson = costEmpWageDetailService.addCostEmpWageDetail(mapVo);

		return JSONObject.parseObject(costEmpWageDetailJson);
		
	}
	/**
	*人员工资明细数据表<BR>
	*查询
	*/
	@RequestMapping(value = "/hrp/cost/costempwagedetail/queryCostEmpWageDetail", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> queryCostEmpWageDetail(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if(mapVo.get("group_id") == null){
		mapVo.put("group_id", SessionManager.getGroupId());
		}
		if(mapVo.get("hos_id") == null){
		mapVo.put("hos_id", SessionManager.getHosId());
		}
		if(mapVo.get("copy_code") == null){
        mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		String para_value = MyConfig.getSysPara("03001");
		
    	mapVo.put("is_flag", para_value);
		
		String costEmpWageDetail = costEmpWageDetailService.queryCostEmpWageDetail(getPage(mapVo));

		return JSONObject.parseObject(costEmpWageDetail);
		
	}
	/**
	*人员工资明细数据表<BR>
	*删除
	*/
	@RequestMapping(value = "/hrp/cost/costempwagedetail/deleteCostEmpWageDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteCostEmpWageDetail(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		for ( String id: paramVo.split(",")) {
			String[] ids=id.split("@");
			Map<String, Object> mapVo=new HashMap<String, Object>();
			mapVo.put("group_id",ids[0]);   
			mapVo.put("hos_id",ids[1]);   
			mapVo.put("copy_code",ids[2]);   
			mapVo.put("acc_year",ids[3]);   
			mapVo.put("acc_month",ids[4]); 
			mapVo.put("dept_id",ids[5]);   
			mapVo.put("dept_no",ids[6]);   
			mapVo.put("emp_id",ids[7]);   
			mapVo.put("emp_no",ids[8]);   
			mapVo.put("emp_kind_code",ids[9]); 
            listVo.add(mapVo);
        }
		String costEmpWageDetailJson = costEmpWageDetailService.deleteBatchCostEmpWageDetail(listVo);
	   return JSONObject.parseObject(costEmpWageDetailJson);
			
	}
	
	/**
	*人员工资明细数据表<BR>
	*修改页面跳转
	*/
	@RequestMapping(value = "/hrp/cost/costempwagedetail/costEmpWageDetailUpdatePage", method = RequestMethod.GET)
	
	public String costEmpWageDetailUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if(mapVo.get("group_id") == null){
		mapVo.put("group_id", SessionManager.getGroupId());
		}
		if(mapVo.get("hos_id") == null){
		mapVo.put("hos_id", SessionManager.getHosId());
		}
		if(mapVo.get("copy_code") == null){
        mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		List<CostWageSchemeSet> list = null;
		String scheme_id = mapVo.get("scheme_id").toString();
		if(!"".equals(scheme_id) && scheme_id != null){
		    list = costWageSchemeSetService.queryCostWageSchemeSetByTitleList(mapVo);
			mode.addAttribute("wageSchemeSetList", list);
			mode.addAttribute("scheme_id", scheme_id);
		}else{
			mode.addAttribute("wageSchemeSetList", list = new ArrayList<CostWageSchemeSet>());
			mode.addAttribute("scheme_id", "");
		}
        CostEmpWageDetail costEmpWageDetail = new CostEmpWageDetail();
        
		String para_value = (String) ((SessionManager.getCostParaMap().get("03001")==null)?0:SessionManager.getCostParaMap().get("03001").toString());
		
    	mapVo.put("is_flag", para_value);
        
		costEmpWageDetail = costEmpWageDetailService.queryCostEmpWageDetailByCode(mapVo);
		mode.addAttribute("group_id", costEmpWageDetail.getGroup_id());
		mode.addAttribute("hos_id", costEmpWageDetail.getHos_id());
		mode.addAttribute("year_month", costEmpWageDetail.getAcc_year().toString() + costEmpWageDetail.getAcc_month().toString());
		
		mode.addAttribute("copy_code", costEmpWageDetail.getCopy_code());
		mode.addAttribute("acc_year", costEmpWageDetail.getAcc_year());
		mode.addAttribute("acc_month", costEmpWageDetail.getAcc_month());
		mode.addAttribute("dept_id", costEmpWageDetail.getDept_id());
		mode.addAttribute("dept_no", costEmpWageDetail.getDept_no());
		mode.addAttribute("dept_name", costEmpWageDetail.getDept_name());
		mode.addAttribute("emp_id", costEmpWageDetail.getEmp_id());
		mode.addAttribute("emp_no", costEmpWageDetail.getEmp_no());
		mode.addAttribute("emp_name", costEmpWageDetail.getEmp_name());
		mode.addAttribute("emp_kind_code", costEmpWageDetail.getEmp_kind_code());
		mode.addAttribute("emp_kind_name", costEmpWageDetail.getEmp_kind_name());
		mode.addAttribute("wage1", costEmpWageDetail.getWage1());
		mode.addAttribute("wage2", costEmpWageDetail.getWage2());
		mode.addAttribute("wage3", costEmpWageDetail.getWage3());
		mode.addAttribute("wage4", costEmpWageDetail.getWage4());
		mode.addAttribute("wage5", costEmpWageDetail.getWage5());
		mode.addAttribute("wage6", costEmpWageDetail.getWage6());
		mode.addAttribute("wage7", costEmpWageDetail.getWage7());
		mode.addAttribute("wage8", costEmpWageDetail.getWage8());
		mode.addAttribute("wage9", costEmpWageDetail.getWage9());
		mode.addAttribute("wage10", costEmpWageDetail.getWage10());
		mode.addAttribute("wage11", costEmpWageDetail.getWage11());
		mode.addAttribute("wage12", costEmpWageDetail.getWage12());
		mode.addAttribute("wage13", costEmpWageDetail.getWage13());
		mode.addAttribute("wage14", costEmpWageDetail.getWage14());
		mode.addAttribute("wage15", costEmpWageDetail.getWage15());
		mode.addAttribute("wage16", costEmpWageDetail.getWage16());
		mode.addAttribute("wage17", costEmpWageDetail.getWage17());
		mode.addAttribute("wage18", costEmpWageDetail.getWage18());
		mode.addAttribute("wage19", costEmpWageDetail.getWage19());
		mode.addAttribute("wage20", costEmpWageDetail.getWage20());
		mode.addAttribute("wage21", costEmpWageDetail.getWage21());
		mode.addAttribute("wage22", costEmpWageDetail.getWage22());
		mode.addAttribute("wage23", costEmpWageDetail.getWage23());
		mode.addAttribute("wage24", costEmpWageDetail.getWage24());
		mode.addAttribute("wage25", costEmpWageDetail.getWage25());
		mode.addAttribute("wage26", costEmpWageDetail.getWage26());
		mode.addAttribute("wage27", costEmpWageDetail.getWage27());
		mode.addAttribute("wage28", costEmpWageDetail.getWage28());
		mode.addAttribute("wage29", costEmpWageDetail.getWage29());
		mode.addAttribute("wage30", costEmpWageDetail.getWage30());
		mode.addAttribute("wage31", costEmpWageDetail.getWage31());
		mode.addAttribute("wage32", costEmpWageDetail.getWage32());
		mode.addAttribute("wage33", costEmpWageDetail.getWage33());
		mode.addAttribute("wage34", costEmpWageDetail.getWage34());
		mode.addAttribute("wage35", costEmpWageDetail.getWage35());
		return "hrp/cost/costempwagedetail/costEmpWageDetailUpdate";
	}
	/**
	*人员工资明细数据表<BR>
	*修改保存
	*/	
	
	@RequestMapping(value = "/hrp/cost/costempwagedetail/updateCostEmpWageDetail", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateCostEmpWageDetail(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if(mapVo.get("group_id") == null){
		mapVo.put("group_id", SessionManager.getGroupId());
		}
		if(mapVo.get("hos_id") == null){
		mapVo.put("hos_id", SessionManager.getHosId());
		}
		if(mapVo.get("copy_code") == null){
        mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		String costEmpWageDetailJson = costEmpWageDetailService.updateCostEmpWageDetail(mapVo);
		
		return JSONObject.parseObject(costEmpWageDetailJson);
	}
	
    // 导入页面
	@RequestMapping(value = "/hrp/cost/costempwagedetail/costEmpWageDetailImportPage", method = RequestMethod.GET)
	public String costEmpWageDetailImportPage(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
			if(mapVo.get("group_id") == null){
			mapVo.put("group_id", SessionManager.getGroupId());
			}
			if(mapVo.get("hos_id") == null){
			mapVo.put("hos_id", SessionManager.getHosId());
			}
			if(mapVo.get("copy_code") == null){
	        mapVo.put("copy_code", SessionManager.getCopyCode());
			}
			List<CostWageSchemeSet> list = null;
			String scheme_id = mapVo.get("scheme_id").toString();
			if(!"".equals(scheme_id) && scheme_id != null){
			    list = costWageSchemeSetService.queryCostWageSchemeSetByTitleList(mapVo);
				mode.addAttribute("wageSchemeSetList", list);
				mode.addAttribute("scheme_id", scheme_id);
			}else{
				mode.addAttribute("wageSchemeSetList", list = new ArrayList<CostWageSchemeSet>());
				mode.addAttribute("scheme_id", "");
			}
		return "hrp/cost/costempwagedetail/costEmpWageDetailImport";

	}
	//下载导入模版
	@RequestMapping(value="hrp/cost/costempwagedetail/downTemplate", method = RequestMethod.GET)  
	 public String downTemplate(String scheme_id,Plupload plupload,HttpServletRequest request,
	    		HttpServletResponse response,Model mode) throws IOException { 
		List<List> list = new ArrayList();
		List<String> listdata = new ArrayList<String>();
		listdata.add("统计年");
		listdata.add("统计月");
		listdata.add("科室编码");
		listdata.add("科室名称");
		listdata.add("职工编码");
		listdata.add("职工名称");
		listdata.add("职工分类编码");
		listdata.add("职工分类名称");
		Map<String, Object> entityMap = new HashMap<String, Object>();
		if(!"".equals(scheme_id) && scheme_id != null){
			entityMap.put("scheme_id", scheme_id);
			List<CostWageSchemeSet> wageSchemeSetList = costWageSchemeSetService.queryCostWageSchemeSetByTitleList(entityMap);
			for(int i = 0 ; i < wageSchemeSetList.size() ; i ++){
				listdata.add(wageSchemeSetList.get(i).getWage_item_name());
			}
		}	
		list.add(listdata);
		String ctxPath = request.getSession().getServletContext().getRealPath("/")
				+ "\\" + "excelTemplate\\"+"\\cost\\基础设置\\";
		String downLoadPath = ctxPath + "职工工资明细数据采集.xls";
		ExcelWriter.exportExcel(new File(downLoadPath), list);
	    printTemplate(request,response,"cost\\基础设置","职工工资明细数据采集.xls");
	    return null; 
	 }
	 
	/**
	*人员工资明细数据表<BR>
	*导入
	 * @throws Exception 
	 * @throws SecurityException 
	 * @throws NoSuchMethodException 
	*/ 
	@RequestMapping(value="/hrp/cost/costempwagedetail/readCostEmpWageDetailFiles",method = RequestMethod.POST)  
    public String readChargeKindDictFiles(String scheme_id,Plupload plupload,HttpServletRequest request,
    		HttpServletResponse response,Model mode) throws NoSuchMethodException, SecurityException, Exception { 
			
		List<CostEmpWageDetail> list_err = new ArrayList<CostEmpWageDetail>();
		List<String[]> list = UploadUtil.readFile(plupload, request, response);
		try {
			for (int i = 1; i < list.size(); i++) {
				StringBuffer err_sb = new StringBuffer();
				CostEmpWageDetail costEmpWageDetail = new CostEmpWageDetail();
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
						
						String year_month = temp[0].toString();
						
						costEmpWageDetail.setYear_month(temp[0]);
						
						costEmpWageDetail.setAcc_year(year_month.substring(0, 4));
						
						costEmpWageDetail.setAcc_month(year_month.substring(4, 6));

						mapVo.put("acc_year", year_month.substring(0, 4));
						
						mapVo.put("acc_month", year_month.substring(4, 6));

					} else {

						err_sb.append("统计年月为空  ");

					}
					if (StringUtils.isNotEmpty(temp[1])) {
						mapVo.put("dept_code", temp[1]);
						DeptDict deptDict = deptDictService.queryDeptDictByDeptNo(mapVo);
						costEmpWageDetail.setDept_code(temp[1]);
						costEmpWageDetail.setDept_name(temp[2]);
						if(deptDict != null){
							mapVo.put("dept_id", deptDict.getDept_id());
							mapVo.put("dept_no", deptDict.getDept_no());
						}else{
							err_sb.append("科室不存在  ");
						}
					} else {
						err_sb.append("科室编码为空  ");
					}
					
					if (StringUtils.isNotEmpty(temp[3])) {
						mapVo.put("emp_code", temp[3]);
						CostEmpAttr empAttr = costEmpAttrService.queryCostEmpAttrByCode(mapVo);
						costEmpWageDetail.setEmp_code(temp[3]);
						costEmpWageDetail.setEmp_name(temp[4]);
						if(empAttr != null){
							mapVo.put("emp_id", empAttr.getEmp_id());
							mapVo.put("emp_no", empAttr.getEmp_no());
						}else{
							err_sb.append("职工不存在  ");
						}
					} else {
						err_sb.append("职工编码为空  ");
					}
					
					if (StringUtils.isNotEmpty(temp[5])) {
						costEmpWageDetail.setEmp_kind_code(temp[5]);
						costEmpWageDetail.setEmp_kind_name(temp[6]);
						mapVo.put("emp_kind_code", temp[5]);
					} else {
						err_sb.append("职工分类编码为空  ");
					}
					mapVo.put("scheme_id", scheme_id);
					if(!"".equals(scheme_id) && scheme_id != null){
						List<CostWageSchemeSet> wageSchemeSetList = costWageSchemeSetService.queryCostWageSchemeSetByTitleList(mapVo);
						for(int j = 0 ; j < wageSchemeSetList.size() ; j ++){
							String wage = wageSchemeSetList.get(j).getWage_column();
							if (StringUtils.isNotEmpty(temp[7+j])) {
									Class cls = Class.forName("com.chd.hrp.cost.entity.CostEmpWageDetail");
						            Method m = cls.getDeclaredMethod("set" + convertFieldName(wage),double.class);
						            m.invoke(costEmpWageDetail, Double.valueOf(temp[7+j]));
						            mapVo.put(wage, Double.valueOf(temp[7+j]));
							} else {
								err_sb.append(wageSchemeSetList.get(j).getWage_item_name()+"为空  ");
							}
						}
					}
				CostEmpWageDetail data_exc_extis = costEmpWageDetailService.queryCostEmpWageDetailByCode(mapVo);
				if (data_exc_extis != null) {
					err_sb.append("已经存在！ ");
				}
				if (err_sb.toString().length() > 0) {
					costEmpWageDetail.setError_type(err_sb.toString());
					list_err.add(costEmpWageDetail);
				} else {
					String dataJson = costEmpWageDetailService.addCostEmpWageDetail(mapVo);
				}
			}
		} catch (DataAccessException e) {
			CostEmpWageDetail data_exc = new CostEmpWageDetail();
			data_exc.setError_type("导入系统出错");
			list_err.add(data_exc);
		}
		response.getWriter().print(ChdJson.toJson(list_err, list_err.size()));
		return null;
    }  
	
	private static String convertFieldName(String fieldName) throws Exception {
		byte[] items = fieldName.getBytes();
		items[0] = (byte) ((char) items[0] - 'a' + 'A');
		return new String(items);
	}
	
	
	
	/**
	*人员工资明细数据表<BR>
	*批量添加
	*/ 
	@RequestMapping(value = "/hrp/cost/costempwagedetail/addBatchCostEmpWageDetail", method = RequestMethod.POST)
	@ResponseBody
    public Map<String, Object> addBatchCostEmpWageDetail(String scheme_id,@RequestParam(value = "ParamVo") String paramVo, Model mode)throws Exception{
		
		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();
		List<CostEmpWageDetail> list_err = new ArrayList<CostEmpWageDetail>();
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
			String year_month = String.valueOf(jsonObj.get("year_month"));
			
			mapVo.put("acc_year", year_month.substring(0, 4));
			
			mapVo.put("acc_month", year_month.substring(4, 6));
			mapVo.put("dept_code", jsonObj.get("dept_code"));
			mapVo.put("dept_name", jsonObj.get("dept_name"));
			DeptDict deptDict = deptDictService.queryDeptDictByDeptNo(mapVo);
			if(deptDict != null){
				mapVo.put("dept_id", deptDict.getDept_id());
				mapVo.put("dept_no", deptDict.getDept_no());
			}else{
				err_sb.append("科室不存在  ");
			}
			mapVo.put("emp_code", jsonObj.get("emp_code"));
			mapVo.put("emp_name", jsonObj.get("emp_name"));
			CostEmpAttr empAttr = costEmpAttrService.queryCostEmpAttrByCode(mapVo);
			if(empAttr != null){
				mapVo.put("emp_id", empAttr.getEmp_id());
				mapVo.put("emp_no", empAttr.getEmp_no());
			}else{
				err_sb.append("职工不存在  ");
			}
			mapVo.put("emp_kind_code", jsonObj.get("emp_kind_code"));
			mapVo.put("emp_kind_name", jsonObj.get("emp_kind_name"));
			if(!"".equals(scheme_id) && scheme_id != null){
				mapVo.put("scheme_id", scheme_id);
				List<CostWageSchemeSet> wageSchemeSetList = costWageSchemeSetService.queryCostWageSchemeSetByTitleList(mapVo);
				for(int j = 0 ; j < wageSchemeSetList.size() ; j ++){
					String wage = wageSchemeSetList.get(j).getWage_column();
					mapVo.put(wage, jsonObj.get(wage));
				}
			}
				CostEmpWageDetail data_exc_extis = costEmpWageDetailService.queryCostEmpWageDetailByCode(mapVo);
				if (data_exc_extis != null) {
					err_sb.append("已经存在！ ");
				}
				CostEmpWageDetail costEmpWageDetail = new CostEmpWageDetail();
				if (err_sb.toString().length() > 0) {
					costEmpWageDetail.setYear_month(mapVo.get("acc_year").toString() + mapVo.get("acc_month").toString());
					
					costEmpWageDetail.setAcc_year(mapVo.get("acc_year").toString());
					costEmpWageDetail.setAcc_month(mapVo.get("acc_month").toString());
					costEmpWageDetail.setDept_code(mapVo.get("dept_code").toString());
					costEmpWageDetail.setDept_name(mapVo.get("dept_name").toString());
					costEmpWageDetail.setEmp_code(mapVo.get("emp_code").toString());
					costEmpWageDetail.setEmp_name(mapVo.get("emp_name").toString());
					costEmpWageDetail.setEmp_kind_code(mapVo.get("emp_kind_code").toString());
					costEmpWageDetail.setEmp_kind_name(mapVo.get("emp_kind_name").toString());
					costEmpWageDetail.setError_type(err_sb.toString());
					list_err.add(costEmpWageDetail);
				} else {
					mapVo.put("scheme_id", scheme_id);
					if(!"".equals(scheme_id) && scheme_id != null){
						List<CostWageSchemeSet> wageSchemeSetList = costWageSchemeSetService.queryCostWageSchemeSetByTitleList(mapVo);
						for(int j = 0 ; j < wageSchemeSetList.size() ; j ++){
							String wage = wageSchemeSetList.get(j).getWage_column();
							Class cls = Class.forName("com.chd.hrp.cost.entity.CostEmpWageDetail");
				            Method m = cls.getDeclaredMethod("set" + convertFieldName(wage),double.class);
				            m.invoke(costEmpWageDetail, Double.valueOf(mapVo.get(wage).toString()));
						}
					}
					costEmpWageDetailService.addCostEmpWageDetail(mapVo);
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

