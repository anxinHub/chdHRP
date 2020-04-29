/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */

package com.chd.hrp.budg.controller.business.invdisburse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

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
import com.chd.base.util.ExcelReader;
import com.chd.base.util.Plupload;
import com.chd.base.util.StringTool;
import com.chd.base.util.UploadUtil;
import com.chd.hrp.budg.entity.BudgNoMedMat;
import com.chd.hrp.budg.service.business.invdisburse.BudgNoMedMatService;
import com.chd.hrp.budg.service.common.BudgCommonInfoService;

/**
 * 
 * @Description: 科室非医用材料预算编制
 * @Table: BUDG_NO_MED_MAT
 * @Author: slient
 * @email: slient@e-tonggroup.com
 * @Version: 1.0
 */
@Controller
public class BudgNoMedMatController extends BaseController {

	private static Logger logger = Logger.getLogger(BudgNoMedMatController.class);

	// 引入Service服务
	@Resource(name = "budgNoMedMatService")
	private final BudgNoMedMatService budgNoMedMatService = null;
	
	//引入Service服务(导入用 )
	@Resource(name = "budgCommonInfoService")
	private final BudgCommonInfoService budgCommonInfoService = null;
	/**
	 * @Description 主页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/business/invdisburse/budgnomedmat/budgNoMedMatMainPage", method = RequestMethod.GET)
	public String budgNoMedMatMainPage(Model mode) throws Exception {
		return "hrp/budg/business/invdisburse/budgnomedmat/budgNoMedMatMain";
	}

	/**
	 * @Description 添加页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/business/invdisburse/budgnomedmat/budgNoMedMatAddPage", method = RequestMethod.GET)
	public String budgNoMedMatAddPage(Model mode) throws Exception {
		return "hrp/budg/business/invdisburse/budgnomedmat/budgNoMedMatAdd";
	}

	/**
	 * @Description 添加数据 科室非医用材料预算编制
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/business/invdisburse/budgnomedmat/addBudgNoMedMat", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addBudgNoMedMat(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}

		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}

		if (mapVo.get("year") == null) {
			mapVo.put("year", SessionManager.getAcctYear());
		}

		String budgNoMedMatJson = budgNoMedMatService.add(mapVo);

		return JSONObject.parseObject(budgNoMedMatJson);

	}

	/**
	 * @Description 更新跳转页面 科室非医用材料预算编制
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/business/invdisburse/budgnomedmat/budgNoMedMatUpdatePage", method = RequestMethod.GET)
	public String budgNoMedMatUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}

		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}

		if (mapVo.get("year") == null) {
			mapVo.put("year", SessionManager.getAcctYear());
		}

		Map<String, Object> budgChargeMat = budgNoMedMatService.queryByCode(mapVo);
		mode.addAllAttributes(budgChargeMat);
		return "hrp/budg/business/invdisburse/budgnomedmat/budgNoMedMatUpdate";
	}

	/**
	 * @Description 更新数据 科室非医用材料预算编制
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/business/invdisburse/budgnomedmat/updateBudgNoMedMat", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateBudgNoMedMat(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}

		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}

		String budgNoMedMatJson = budgNoMedMatService.update(mapVo);

		return JSONObject.parseObject(budgNoMedMatJson);
	}

	/**
	 * @Description 更新数据 科室非医用材料预算编制
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/business/invdisburse/budgnomedmat/addOrUpdateBudgNoMedMat", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addOrUpdateBudgNoMedMat(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		String budgNoMedMatJson = "";

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		budgNoMedMatJson = budgNoMedMatService.addOrUpdate(mapVo);

		return JSONObject.parseObject(budgNoMedMatJson);
	}

	/**
	 * @Description 删除数据 科室非医用材料预算编制
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/business/invdisburse/budgnomedmat/deleteBudgNoMedMat", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteBudgNoMedMat(@RequestParam(value = "ParamVo") String paramVo, Model mode)
			throws Exception {

		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();

		for (String id : paramVo.split(",")) {

			Map<String, Object> mapVo = new HashMap<String, Object>();

			String[] ids = id.split("@");

			// 表的主键
			mapVo.put("group_id", ids[0]);
			mapVo.put("hos_id", ids[1]);
			mapVo.put("copy_code", ids[2]);
			mapVo.put("year", ids[3]);
			mapVo.put("month", ids[4]);
			mapVo.put("dept_id", ids[5]);
			mapVo.put("mat_type_id", ids[6]);

			listVo.add(mapVo);

		}

		String budgNoMedMatJson = budgNoMedMatService.deleteBatch(listVo);

		return JSONObject.parseObject(budgNoMedMatJson);

	}

	/**
	 * @Description 查询数据 科室非医用材料预算编制
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/business/invdisburse/budgnomedmat/queryBudgNoMedMat", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryBudgNoMedMat(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}

		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		if (mapVo.get("year") == null) {
			mapVo.put("year", SessionManager.getAcctYear());
		}
		String budgNoMedMat = budgNoMedMatService.query(getPage(mapVo));

		return JSONObject.parseObject(budgNoMedMat);

	}

	/**
	 * @Description 导入跳转页面 科室非医用材料预算编制
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/business/invdisburse/budgnomedmat/budgNoMedMatImportPage", method = RequestMethod.GET)
	public String budgNoMedMatImportPage(Model mode) throws Exception {

		return "hrp/budg/business/invdisburse/budgnomedmat/budgNoMedMatImport";

	}

	/**
	 * @Description 下载导入模版 科室非医用材料预算编制
	 * @param request
	 * @param response
	 * @param mode
	 * @return String
	 * @throws IOException
	 */
	@RequestMapping(value = "hrp/budg/business/invdisburse/budgnomedmat/downTemplate", method = RequestMethod.GET)
	public String downTemplate(Plupload plupload, HttpServletRequest request, HttpServletResponse response, Model mode)
			throws IOException {

		printTemplate(request, response, "budg\\cost\\mat","科室非医用材料预算编制.xls");

		return null;
	}

	/**
	 * @Description 导入数据 科室非医用材料预算编制
	 * @param plupload
	 * @param request
	 * @param response
	 * @param mode
	 * @return String
	 * @throws IOException
	 */
	@RequestMapping(value = "/hrp/budg/business/invdisburse/budgnomedmat/readBudgNoMedMatFiles", method = RequestMethod.POST)
	public String readBudgNoMedMatFiles(Plupload plupload, HttpServletRequest request, HttpServletResponse response,
			Model mode) throws IOException {

		List<Map<String,Object>> list_err = new ArrayList<Map<String,Object>>();
		
		List<String[]> list = UploadUtil.readFile(plupload, request, response);
		
		List<Map<String,Object>> addList = new ArrayList<Map<String,Object>>();
		
		String[] monthData = {"01","02","03","04","05","06","07","08","09","10","11","12"}; 
		
		Map<String,Object> paraMap = new HashMap<String,Object>();
		
		paraMap.put("group_id", SessionManager.getGroupId());   
		 
		paraMap.put("hos_id", SessionManager.getHosId());   
		 
		paraMap.put("copy_code", SessionManager.getCopyCode());   
		
		//查询 科室基本信息(根据编码 匹配ID用)预算科室
		List<Map<String,Object>> deptList =  budgCommonInfoService.queryDeptData(paraMap) ;
		
		try {
			for (int i = 1; i < list.size(); i++) {
				
				StringBuffer err_sb = new StringBuffer();
				
				Map<String,Object> map = new HashMap<String,Object>();
				
				String temp[] = list.get(i);// 行
				Map<String, Object> mapVo = new HashMap<String, Object>();
				
	    		mapVo.put("group_id", SessionManager.getGroupId());   
				 
	    		mapVo.put("hos_id", SessionManager.getHosId());   
				 
	    		mapVo.put("copy_code", SessionManager.getCopyCode());   
	    		
	    		for(int j = i + 1 ; j < list.size(); j++){
					String error[] = list.get(j);
					
					if(temp[0].equals(error[0]) && temp[1].equals(error[1]) && temp[2].equals(error[2])&& temp[4].equals(error[4])){
						err_sb.append("第"+i+"行数据与第 "+j+"行数据重复;");
					}
						
				}
				 
				if (ExcelReader.validExceLColunm(temp, 0)) {
					
					map.put("year",temp[0]);
					
		    		mapVo.put("year", temp[0]);
				
				} else {
					
					err_sb.append("年度为空;");
					
				}
				 
				if (ExcelReader.validExceLColunm(temp,1)) {
					
					map.put("month",temp[1]);
					
					if(!Arrays.asList(monthData).contains(temp[1])){
						
						err_sb.append("月份输入不合法（两位数字01-12）;");
						
					}else{
						
			    		mapVo.put("month", temp[1]);
						
					}
				
				} else {
					
					err_sb.append("月份为空 ;");
					
				}
				 
				if (ExcelReader.validExceLColunm(temp,2)) {
					
					map.put("dept_code",temp[2]);
					map.put("dept_name", temp[3]);
					
					int flag = 0 ;
					
					for(Map<String,Object> item : deptList){
						
						if(temp[2].equals(item.get("dept_code"))){
							
							map.put("dept_id", String.valueOf(item.get("dept_id")));
				    		mapVo.put("dept_id", String.valueOf(item.get("dept_id")));
				    		flag = 1;
				    		
				    		break ;
						}
						
					}
					
					if(flag == 0){
						
						err_sb.append("科室编码输入错误,不存在该科室;");
					}
				
				} else {
					
					err_sb.append("科室编码为空;");
					
				}
				 
				if (ExcelReader.validExceLColunm(temp,4)) {
					
					map.put("mat_type_code",temp[4]);
					map.put("mat_type_name", temp[5]);
					
					mapVo.put("mat_type_code",temp[4]);
					mapVo.put("no_medical","1");//非医用
					//查询 预算物质材料基本信息(根据编码 匹配ID用)
					Map<String,Object> typeMap = budgCommonInfoService.queryBudgMatType(mapVo) ;
					
						
					if(typeMap != null ){
						
						map.put("mat_type_id", String.valueOf(typeMap.get("mat_type_id")));
			    		mapVo.put("mat_type_id", String.valueOf(typeMap.get("mat_type_id")));
			    		
					}else{
						err_sb.append("物资类别编码输入错误,不存在该物资类别或输入年度该物资类别与预算科目对应关系不存在;");
					}
					
				} else {
					
					err_sb.append("物资类别编码为空;");
					
				}
				 
				if (ExcelReader.validExceLColunm(temp,6)) {
					
					map.put("cost_budg",Double.valueOf(temp[6]));
		    		mapVo.put("cost_budg", temp[6]);
				
				} else {
					
					err_sb.append("金额为空;");
					
				}
				if (ExcelReader.validExceLColunm(temp,7)) {
					
					map.put("remark",temp[7]);
		    		mapVo.put("remark", temp[7]);
				
				} else {
					
					map.put("remark","");
		    		mapVo.put("remark", "");
					
				}
				//校验数据 是否存在
				int count = budgNoMedMatService.queryDataExist(mapVo);
				
				if (count > 0 ) {
					
					err_sb.append("数据已经存在！ ");
					
				}
				if (err_sb.toString().length() > 0) {
					
					map.put("error_type",err_sb.toString());
					
					list_err.add(map);
					
				} else {
					mapVo.put("last_cost", "") ;
					mapVo.put("grow_rate", "") ;
					mapVo.put("count_value", "") ;
					addList.add(mapVo) ;
				}
				
			}
			
			if(list_err.size() == 0){
				
				if(addList.size() > 0 ){
					
					String dataJson = budgNoMedMatService.addBatch(addList);
					
				}else{
					
					Map<String, Object> data_exc = new HashMap<String, Object>();
					
					data_exc.put("error_type","没有数据,导入失败!");
					
					list_err.add(data_exc);
				}
				
			}
			
		} catch (DataAccessException e) {
			
			Map<String, Object> data_exc = new HashMap<String, Object>();
			
			data_exc.put("error_type","导入系统出错");
			
			list_err.add(data_exc);
			
		}
		
		response.getWriter().print(ChdJson.toJson(list_err, list_err.size()));
		
		return null;

	}

	/**
	 * @Description 批量添加数据 科室非医用材料预算编制
	 * @param plupload
	 * @param request
	 * @param response
	 * @param mode
	 * @return String
	 * @throws IOException
	 */
	@RequestMapping(value = "/hrp/budg/business/invdisburse/budgnomedmat/addBatchBudgNoMedMat", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addBatchBudgNoMedMat(@RequestParam(value = "ParamVo") String paramVo, Model mode)
			throws Exception {

		List<BudgNoMedMat> list_err = new ArrayList<BudgNoMedMat>();

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

				BudgNoMedMat budgNoMedMat = new BudgNoMedMat();

				JSONObject jsonObj = JSONObject.parseObject(it.next().toString());

				if (StringTool.isNotBlank(jsonObj.get("year"))) {

					budgNoMedMat.setYear((String) jsonObj.get("year"));
					mapVo.put("year", jsonObj.get("year"));
				} else {

					err_sb.append("年度为空  ");

				}

				if (StringTool.isNotBlank(jsonObj.get("month"))) {

					budgNoMedMat.setMonth((String) jsonObj.get("month"));
					mapVo.put("month", jsonObj.get("month"));
				} else {

					err_sb.append("月为空  ");

				}

				if (StringTool.isNotBlank(jsonObj.get("dept_id"))) {

					budgNoMedMat.setDept_id(Long.valueOf((String) jsonObj.get("dept_id")));
					mapVo.put("dept_id", jsonObj.get("dept_id"));
				} else {

					err_sb.append("部门ID为空  ");

				}

				if (StringTool.isNotBlank(jsonObj.get("mat_type_id"))) {

					budgNoMedMat.setMat_type_id(Long.valueOf((String) jsonObj.get("mat_type_id")));
					mapVo.put("mat_type_id", jsonObj.get("mat_type_id"));
				} else {

					err_sb.append("物资类别ID为空  ");

				}

				

				BudgNoMedMat data_exc_extis = budgNoMedMatService.queryByCode(mapVo);

				if (data_exc_extis != null) {

					err_sb.append("编码已经存在！ ");

				}
				if (err_sb.toString().length() > 0) {

					budgNoMedMat.setError_type(err_sb.toString());

					list_err.add(budgNoMedMat);

				} else {

					budgNoMedMatService.add(mapVo);

				}

			}

		} catch (DataAccessException e) {

			BudgNoMedMat data_exc = new BudgNoMedMat();

			list_err.add(data_exc);

			return JSONObject.parseObject("{\"msg\":\"导入系统出错,请重新导入.\",\"state\":\"err\"}");

		}

		if (list_err.size() > 0) {

			return JSONObject.parseObject(ChdJson.toJson(list_err, list_err.size()));

		} else {

			return JSONObject.parseObject("{\"msg\":\"导入成功.\",\"state\":\"true\"}");

		}

	}
	
	/**
	 * 科室名称 下拉框
	 * 
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/business/invdisburse/budgnomedmat/queryHosDeptDict", method = RequestMethod.POST)
	@ResponseBody
	public String queryHosDeptDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		return budgNoMedMatService.queryHosDeptDict(mapVo);

	}
	
	/**
	 * 物资分类 下拉框
	 * 
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/business/invdisburse/budgnomedmat/queryBudgMatTypeSubj", method = RequestMethod.POST)
	@ResponseBody
	public String queryBudgMatTypeSubj(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		return budgNoMedMatService.queryBudgMatTypeSubj(mapVo);

	}
	
	/**
	 * 生成
	 * @param mapVo
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/business/invdisburse/budgnomedmat/generateBudgNoMedMat", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> generateBudgNoMedMat(@RequestParam Map<String, Object> mapVo) throws Exception{
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		return JSONObject.parseObject(budgNoMedMatService.copyBudgNoMedMat(mapVo));
	}
	
	/**
	 * @Description 增长比例设置页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/business/invdisburse/budgnomedmat/budgNoMedMatGrowRatePage", method = RequestMethod.GET)
	public String budgNoMedMatGrowRatePage(Model mode) throws Exception {
		return "hrp/budg/business/invdisburse/budgnomedmat/budgNoMedMatGrowRate";
	}

	/**
	 * 计算
	 * @param mapVo
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/business/invdisburse/budgnomedmat/collectBudgNoMedMat", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> budgChargeMatUpdateAdjRate(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		
		for ( String id: paramVo.split(",")) {
			
			Map<String, Object> mapVo=new HashMap<String, Object>();
			
			String[] ids=id.split("@");
			
			//表的主键
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
	    	mapVo.put("copy_code", SessionManager.getCopyCode());
	    	
			mapVo.put("year", ids[0]);
			mapVo.put("month", ids[1]);
			mapVo.put("dept_id", ids[2]);
			mapVo.put("mat_type_id", ids[3]);
			mapVo.put("last_cost", ids[4]);
			mapVo.put("grow_rate", ids[5]);
			if("-1".equals(ids[6])){
				mapVo.put("remark", "");
			}else{
				
				mapVo.put("remark", ids[6]);
			}
			
			listVo.add(mapVo);
      
    }
		String budgWageJson = budgNoMedMatService.collectBudgNoMedMat(listVo);
		
		return JSONObject.parseObject(budgWageJson);
	}
}
