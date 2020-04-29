package com.chd.hrp.budg.controller.base.budgdrug;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.chd.base.BaseController;
import com.chd.base.SessionManager;
import com.chd.base.util.ChdJson;
import com.chd.base.util.Plupload;
import com.chd.base.util.StringTool;
import com.chd.base.util.UploadUtil;
import com.chd.hrp.budg.dao.base.budgdrug.BudgDrugTypeMapper;
import com.chd.hrp.budg.entity.BudgDrugType;
import com.chd.hrp.budg.service.base.budgdrug.BudgDrugTypeDictService;
import com.chd.hrp.budg.service.base.budgdrug.BudgDrugTypeService;
import com.chd.hrp.mat.dao.info.basic.MatTypeMapper;
import com.chd.hrp.med.dao.info.basic.MedTypeMapper;
import com.chd.hrp.med.entity.MedType;
import com.chd.hrp.med.service.base.MedCommonService;
import com.chd.hrp.med.service.info.basic.MedTypeDictService;
import com.chd.hrp.med.service.info.basic.MedTypeService;

/**
 * 
 * @Title:
 * @Description:
 * @Copyright: Copyright (c) 2016-11-24 下午1:38:45
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 * @Author: 
 * @Version: 6.0
 */
@Controller
public class BudgDrugTypeController extends BaseController {
	private static Logger logger =Logger.getLogger(BudgDrugTypeController.class);
	
	// 引入Service服务
	@Resource(name = "budgDrugTypeService")
	private final BudgDrugTypeService budgDrugTypeService = null;
	
	@Resource(name = "medCommonService")
	private final MedCommonService medCommonService = null;
	
	@Resource(name = "budgDrugTypeDictService")
	private final BudgDrugTypeDictService budgDrugTypeDictService = null;
	
	@Resource(name = "budgDrugTypeMapper")
	private final BudgDrugTypeMapper budgDrugTypeMapper = null;    
	
	
	
	/**
	* @Description 主页跳转
	* @param 
	* @return 
	* @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/base/budgdrug/budgdrugtype/budgDrugTypeMainPage", method = RequestMethod.GET)
	public String  medTypeMainPagea(Model model)throws Exception{
		
		return "hrp/budg/base/budgdrug/budgdrugtype/budgDrugTypeMain";
			
	}
	
	/**
	 * @Description 编码规则
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/base/budgdrug/budgdrugtype/getRules", method = RequestMethod.POST)
	@ResponseBody
	public String getRules(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
        
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
	    mapVo.put("copy_code", SessionManager.getCopyCode());
	    mapVo.put("proj_code", "MED_TYPE");
	    mapVo.put("mod_code", "08");
	    String rules = medCommonService.getMedHosRules(mapVo);//获取编码规则2-2-2....
	    String[] ruless  = rules.split("-");
	    
	    StringBuffer sb = new StringBuffer();
	    
	    for(int i = 0; i <= ruless.length; i++){
	    	
	    	if(ruless[i].equals("0")){
	    		break;
	    	}
	    	if(i > 0){
	    		sb.append("-");
	    	}
	    	sb.append(ruless[i]);
	    }
		return "{\"ruless\":\""+sb.toString()+"\"}";
	}
	
	/**
	 * @Description 树形结构
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/base/budgdrug/budgdrugtype/queryBudgDrugTypeByTree", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryBudgDrugTypeByTree(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		String group = budgDrugTypeService.queryBudgDrugTypeByTree(mapVo);

		return JSONObject.parseObject(group);
	}
	
	/**
	 * @Description 
	 * 查询数据   药品分类变更表
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/base/budgdrug/budgdrugtype/queryBudgDrugTypeDict", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryBudgDrugTypeDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
	    if(mapVo.get("group_id") == null){
	    	mapVo.put("group_id", SessionManager.getGroupId());
		}
		if(mapVo.get("hos_id") == null){
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if(mapVo.get("copy_code") == null){
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		String medTypeDict = budgDrugTypeDictService.query(getPage(mapVo));

		return JSONObject.parseObject(medTypeDict);
	}
	
	/**
	 * @Description 
	 * 添加数据  药品分类变更表
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/budg/base/budgdrug/budgdrugtype/saveBudgDrugTypeDict", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveBudgDrugTypeDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		if(mapVo.get("group_id") == null){
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if(mapVo.get("hos_id") == null){
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if(mapVo.get("copy_code") == null){
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		String medTypeDictJson = budgDrugTypeDictService.add(mapVo);

		return JSONObject.parseObject(medTypeDictJson);
	}
	
	/**
	 * @Description 根据主键加载数据
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/base/budgdrug/budgdrugtype/queryBudgDrugTypeById", method = RequestMethod.POST)
	@ResponseBody
	public BudgDrugType queryBudgDrugTypeById(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}

		return budgDrugTypeService.queryBudgDrugTypeById(mapVo);
	}
	
	
	/**
	 * @Description 添加数据08102 药品分类字典
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/base/budgdrug/budgdrugtype/addBudgDrugType", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addBudgDrugType(
			@RequestParam Map<String, Object> mapVo, Model mode)
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


		mapVo.put("spell_code",	StringTool.toPinyinShouZiMu(mapVo.get("med_type_name").toString()));
		mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("med_type_name").toString()));

		String medTypeJson = budgDrugTypeService.add(mapVo);

		return JSONObject.parseObject(medTypeJson);

	}
	
	/**
	 * @Description 更新数据08102 药品分类字典
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/base/budgdrug/budgdrugtype/saveBudgDrugType", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveBudgDrugType(@RequestParam Map<String, Object> mapVo, Model mode)throws Exception {
		
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		if (mapVo.get("med_type_id") == null) {
			mapVo.put("med_type_id", "");
		}

		String medTypeJson = budgDrugTypeService.addOrUpdate(mapVo);

		return JSONObject.parseObject(medTypeJson);
	}
	
	/**
	 * @Description 删除数据 08102 药品分类字典
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/base/budgdrug/budgdrugtype/deleteBudgDrugType", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteBudgDrugType(@RequestParam Map<String, Object> mapVo, Model mode)throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}

		String medTypeJson = budgDrugTypeService.delete(mapVo);

		return JSONObject.parseObject(medTypeJson);

	}
	
	/**
	 * @Description 查询数据 08102 药品分类字典
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/base/budgdrug/budgdrugtype/queryBudgDrugType", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryBudgDrugType(@RequestParam Map<String, Object> mapVo, Model mode)throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		String medType = budgDrugTypeService.query(getPage(mapVo));
		
		return JSONObject.parseObject(medType);
	}

	/**
	 * @Description 跳转页面药品类别变更
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/base/budgdrug/budgdrugtype/budgDrugTypeDictPage", method = RequestMethod.GET)
	public String medTypeDictPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		mode.addAttribute("medType", budgDrugTypeService.queryBudgDrugTypeById(mapVo));

		return "hrp/budg/base/budgdrug/budgdrugtype/budgDrugTypeDict";
	}
	
	/**
	 * @Description 下载导入模版 药品分类字典
	 * @param request
	 * @param response
	 * @param mode
	 * @return String
	 * @throws IOException
	 */
	@RequestMapping(value = "hrp/budg/base/budgdrug/budgdrugtype/downTemplate", method = RequestMethod.GET)
	public String downTemplate(Plupload plupload, HttpServletRequest request,
			HttpServletResponse response, Model mode) throws IOException {

		printTemplate(request, response, "med\\downTemplate","08102药品分类字典.xls");

		return null;
	}
	
	/**
	 * @Description 导入跳转页面  药品分类字典
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/budg/base/budgdrug/budgdrugtype/budgDrugTypeImportPage", method = RequestMethod.GET)
	public String medTypeImportPage(Model mode) throws Exception {

		return "hrp/budg/base/budgdrug/budgdrugtype/budgDrugTypeImport";

	}
	
	/**
	 * @Description 导入数据 08102药品分类字典
	 * @param plupload
	 * @param request
	 * @param response
	 * @param mode
	 * @return String
	 * @throws IOException
	 */
	@RequestMapping(value = "/hrp/budg/base/budgdrug/budgdrugtype/readMedTypeFiles", method = RequestMethod.POST)
	public String readMedTypeFiles(Plupload plupload,
			HttpServletRequest request, HttpServletResponse response, Model mode)
			throws IOException {

		List<MedType> list_err = new ArrayList<MedType>();

		List<String[]> list = UploadUtil.readFile(plupload, request, response);

		try {
			for (int i = 1; i < list.size(); i++) {

				StringBuffer err_sb = new StringBuffer();

				MedType medType = new MedType();

				String temp[] = list.get(i);// 行
				
				Map<String, Object> mapVo = new HashMap<String, Object>();

				mapVo.put("group_id", SessionManager.getGroupId());

				mapVo.put("hos_id", SessionManager.getHosId());

				mapVo.put("copy_code", SessionManager.getCopyCode());

				if (StringTool.isNotBlank(temp[0].toString().trim())) {

					medType.setMed_type_code(temp[0].toString().trim());
					
					mapVo.put("med_type_code", temp[0].toString().trim());

				} else {

					err_sb.append("药品类别编码为空");

				}

				if (StringTool.isNotBlank(temp[1].toString().trim())) {

					medType.setMed_type_name(temp[1].toString().trim());
					
					mapVo.put("med_type_name", temp[1].toString().trim());

				} else {

					err_sb.append("药品类别名称为空");

				}


				if (StringTool.isNotBlank(temp[2])) {

					medType.setIs_last(Integer.parseInt(temp[2]));
					
					mapVo.put("is_last", temp[2]);

				} else {

					err_sb.append("是否末级为空  ");

				}

				if (StringTool.isNotBlank(temp[3])) {

					medType.setIs_stop(Integer.parseInt(temp[3]));
					
					mapVo.put("is_stop", temp[3]);

				} else {

					err_sb.append("是否停用为空  ");

				}

				

				if (StringTool.isNotBlank(temp[4])) {

					medType.setNote(temp[4]);
					
					mapVo.put("note", temp[4]);

				} else {

					 medType.setNote(null);
						
					 mapVo.put("note", null);

				}

				if (StringTool.isNotBlank(temp[5])) {

					medType.setIs_auto_exp(Integer.parseInt(temp[5]));
					
					mapVo.put("is_auto_exp", temp[5]);

				} else {

					err_sb.append("自动有效期为空  ");

				}
				
				//判断是否符合编码规则
				Map<String, Object> comMap = new HashMap<String, Object>();
				
				comMap.put("group_id", mapVo.get("group_id"));
				
				comMap.put("hos_id", mapVo.get("hos_id"));
				
				comMap.put("copy_code", mapVo.get("copy_code"));
				
				comMap.put("proj_code", "med_type".toUpperCase());
				
				comMap.put("mod_code", "08");
				
		        String rules = medCommonService.getMedHosRules(comMap);//获取编码规则2-2-2....
		        
		        String med_type_code = (String)mapVo.get("med_type_code");//类别编码
		        
		        StringBuffer s_code = new StringBuffer();//上级编码
				
		        String super_code_temp = "0";
		        int type_level =0;//级次 
		        int begin_index = 0;
		        int end_index = 0;
		        int length = 0;
		        String[] ruless  = rules.split("-");
		        
		        int strLength = med_type_code.length();
		        for(int k = 0; k < ruless.length ; k++){
		        	if("0".equals(ruless[k])){
		        		break;
		        	}
		    		s_code.append(super_code_temp);
		        	length = Integer.valueOf(ruless[k]);
		        	end_index = begin_index + length;
		        	//防止越界
		        	if(end_index >= strLength){
		        		end_index = strLength;
		        	}
		        	super_code_temp = med_type_code.substring(begin_index, end_index);
		        	
		        	if(super_code_temp.length() == length){
		        		type_level += 1;
		        	}else{
		        		err_sb.append("编码位数不符合编码规则");
		        	}
		        	begin_index += length;
		        	//截取完毕以后跳出
		        	if(end_index == strLength){
		        		break;
		        	}
		        }
		        String super_code = "";
		        if(s_code.length()>1){
		        	super_code = s_code.toString().substring(1, s_code.length());
		        }else{
		        	super_code = s_code.toString();
		        }
		        
		        mapVo.put("super_code", super_code.toString());
		        
		        mapVo.put("type_level", type_level);
				

		        Map<String, Object> utilMap = new HashMap<String, Object>();
				
				if(!"0".equals(super_code)){
			        //判断上级编码是否存在
					utilMap.put("group_id", mapVo.get("group_id"));
					utilMap.put("hos_id", mapVo.get("hos_id"));
					utilMap.put("copy_code", mapVo.get("copy_code"));
					utilMap.put("med_type_code", super_code.toString());
					utilMap.put("type_level", type_level-1);
			        Map<String, Object> map = budgDrugTypeMapper.queryByCode(utilMap);
			        
			        if(map == null){
			        	
			        	err_sb.append("上级类别编码不存在 ");
			        }

			            utilMap.put("med_type_id", map.get("med_type_id"));
			        
				}
				
				
				
				Map<String, Object>  data_exc_extis = budgDrugTypeMapper.queryByCode(mapVo);

				if (data_exc_extis != null ) {
					
					err_sb.append("编码已经存在！ ");
				}
				
				if (err_sb.toString().length() > 0) {

					medType.setError_type(err_sb.toString());

					list_err.add(medType);

				} else {
					
					mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("med_type_name").toString().trim()));
					
					mapVo.put("wbx_code",StringTool.toWuBi(mapVo.get("med_type_name").toString().trim()));

					budgDrugTypeService.impMedType(mapVo,utilMap);

				}

			}

		} catch (DataAccessException e) {

			MedType data_exc = new MedType();

			data_exc.setError_type("导入系统出错");

			list_err.add(data_exc);

		}

		response.getWriter().print(ChdJson.toJson(list_err, list_err.size()));

		return null;

	}

	/**
	 * @Description 批量添加数据 08102药品分类字典
	 * @param plupload
	 * @param request
	 * @param response
	 * @param mode
	 * @return String
	 * @throws IOException
	 */
	@RequestMapping(value = "/hrp/budg/base/budgdrug/budgdrugtype/addBatchMedType", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addBatchMedType(
			@RequestParam(value = "ParamVo") String paramVo, Model mode)
			throws Exception {

		List<MedType> list_err = new ArrayList<MedType>();

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

				MedType medType = new MedType();

				JSONObject jsonObj = JSONObject.parseObject(it.next().toString());


				if (StringTool.isNotBlank(jsonObj.get("med_type_code"))) {

					medType.setMed_type_code((String) jsonObj.get("med_type_code"));
					
					mapVo.put("med_type_code", jsonObj.get("med_type_code"));
					
				} else {

					err_sb.append("物资类别编码为空  ");

				}

				if (StringTool.isNotBlank(jsonObj.get("med_type_name"))) {

					medType.setMed_type_name((String) jsonObj.get("med_type_name"));
					
					mapVo.put("med_type_name", jsonObj.get("med_type_name"));
					
				} else {

					err_sb.append("物资类别名称为空  ");

				}

				if (StringTool.isNotBlank(jsonObj.get("is_last"))) {

					medType.setIs_last(Integer.parseInt(jsonObj.get("is_last").toString()));
					
					mapVo.put("is_last", jsonObj.get("is_last"));
					
				} else {

					err_sb.append("是否末级为空  ");

				}

				if (StringTool.isNotBlank(jsonObj.get("is_stop"))) {

					medType.setIs_stop(Integer.parseInt(jsonObj.get("is_stop").toString()));
					
					mapVo.put("is_stop", jsonObj.get("is_stop"));
					
				} else {

					err_sb.append("是否停用为空  ");

				}

				if (StringTool.isNotBlank(jsonObj.get("note"))) {

					medType.setNote((String) jsonObj.get("note"));
					
					mapVo.put("note", jsonObj.get("note"));
					
				} else {

					err_sb.append("备注为空  ");

				}


				if (StringTool.isNotBlank(jsonObj.get("is_auto_exp"))) {

					medType.setIs_auto_exp(Integer.parseInt(jsonObj.get("is_auto_exp").toString()));
					
					mapVo.put("is_auto_exp", jsonObj.get("is_auto_exp"));
					
				} else {

					err_sb.append("自动有效期为空  ");
				}
				//判断是否符合编码规则
				Map<String, Object> comMap = new HashMap<String, Object>();
				
				comMap.put("group_id", mapVo.get("group_id"));
				
				comMap.put("hos_id", mapVo.get("hos_id"));
				
				comMap.put("copy_code", mapVo.get("copy_code"));
				
				comMap.put("proj_code", "med_type".toUpperCase());
				
				comMap.put("mod_code", "04");
				
		        String rules = medCommonService.getMedHosRules(comMap);//获取编码规则2-2-2....
		        
		        String med_type_code = (String)mapVo.get("med_type_code");//类别编码
		        
		        StringBuffer s_code = new StringBuffer();//上级编码
				
		        String super_code_temp = "0";
		        int type_level =0;//级次 
		        int begin_index = 0;
		        int end_index = 0;
		        int length = 0;
		        String[] ruless  = rules.split("-");
		        
		        int strLength = med_type_code.length();
		        for(int k = 0; k < ruless.length ; k++){
		        	if("0".equals(ruless[k])){
		        		break;
		        	}
		    		s_code.append(super_code_temp);
		        	length = Integer.valueOf(ruless[k]);
		        	end_index = begin_index + length;
		        	//防止越界
		        	if(end_index >= strLength){
		        		end_index = strLength;
		        	}
		        	super_code_temp = med_type_code.substring(begin_index, end_index);
		        	
		        	if(super_code_temp.length() == length){
		        		type_level += 1;
		        	}else{
		        		err_sb.append("编码位数不符合编码规则");
		        	}
		        	begin_index += length;
		        	//截取完毕以后跳出
		        	if(end_index == strLength){
		        		break;
		        	}
		        }
		        String super_code = "";
		        if(s_code.length()>1){
		        	super_code = s_code.toString().substring(1, s_code.length());
		        }else{
		        	super_code = s_code.toString();
		        }
		        
		        mapVo.put("super_code", super_code.toString());
		        
		        mapVo.put("type_level", type_level);
				

		        Map<String, Object> utilMap = new HashMap<String, Object>();
				
				if(!"0".equals(super_code)){
			        //判断上级编码是否存在
					utilMap.put("group_id", mapVo.get("group_id"));
					utilMap.put("hos_id", mapVo.get("hos_id"));
					utilMap.put("copy_code", mapVo.get("copy_code"));
					utilMap.put("med_type_code", super_code.toString());
					utilMap.put("type_level", type_level-1);
			        Map<String, Object> map = budgDrugTypeMapper.queryByCode(utilMap);
			        
			        if(map==null){
			        	
			        	err_sb.append("上级类别编码不存在  ");
			        }
			        
			            utilMap.put("med_type_id", map.get("med_type_id"));
			        

				}
				
				
				
				Map<String, Object>  data_exc_extis = budgDrugTypeMapper.queryByCode(mapVo);

				if (data_exc_extis != null ) {
					
					err_sb.append("编码已经存在！ ");
				}

				if (err_sb.toString().length() > 0) {
					
					medType.setError_type(err_sb.toString());
					
					list_err.add(medType);
				} else {
					
					mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("med_type_name").toString()));
					
					mapVo.put("wbx_code",StringTool.toWuBi(mapVo.get("med_type_name").toString()));
					
				     budgDrugTypeService.impMedType(mapVo,utilMap);
				}
			}
			
		} catch (DataAccessException e) {
			
			MedType data_exc = new MedType();
			
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
