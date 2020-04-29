package com.chd.hrp.med.controller.info.basic;
import java.io.File;
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
import com.chd.base.util.ExcelWriterTo2007;
import com.chd.base.util.Plupload;
import com.chd.base.util.StringTool;
import com.chd.base.util.UploadUtil;
import com.chd.hrp.med.dao.info.basic.MedTypeMapper;
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
public class MedTypeController extends BaseController {
	private static Logger logger =Logger.getLogger(MedTypeController.class);
	
	// 引入Service服务
	@Resource(name = "medTypeService")
	private final MedTypeService medTypeService = null;
	
	@Resource(name = "medCommonService")
	private final MedCommonService medCommonService = null;
	
	@Resource(name = "medTypeDictService")
	private final MedTypeDictService medTypeDictService = null;
	
	@Resource(name = "medTypeMapper")
	private final MedTypeMapper medTypeMapper = null;    
	
	
	
	/**
	* @Description 主页跳转
	* @param 
	* @return 
	* @throws Exception
	*/
	@RequestMapping(value = "/hrp/med/info/basic/type/medTypeMainPage", method = RequestMethod.GET)
	public String  medTypeMainPage(Model model)throws Exception{
		
		return "hrp/med/info/basic/type/medTypeMain";
			
	}
	
	/**
	 * @Description 编码规则
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/info/basic/type/getRules", method = RequestMethod.POST)
	@ResponseBody
	public String getRules(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
        
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
	    mapVo.put("copy_code", SessionManager.getCopyCode());
	    mapVo.put("proj_code", "MED_TYPE");
	    mapVo.put("mod_code", "08");
	    String rules = medCommonService.getMedHosRules(mapVo);//获取编码规则2-2-2....
	    if(rules == null || rules.equals("")){
	    	return "{\"error\":\"编码规则不存在！\"}";
	    }
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
	@RequestMapping(value = "/hrp/med/info/basic/type/queryMedTypeByTree", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMedTypeByTree(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		String group = medTypeService.queryMedTypeByTree(mapVo);

		return JSONObject.parseObject(group);
	}
	
	/**
	 * @Description 树形结构（含变更号）
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/info/basic/type/queryMedTypeDictByTree", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMedTypeDictByTree(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		 mapVo.put("user_id", SessionManager.getUserId());
		mapVo.put("is_stop", 0);
		String group = medTypeDictService.queryMedTypeDictByTree(mapVo);

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
	@RequestMapping(value = "/hrp/med/info/basic/type/queryMedTypeDict", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMedTypeDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
	    if(mapVo.get("group_id") == null){
	    	mapVo.put("group_id", SessionManager.getGroupId());
		}
		if(mapVo.get("hos_id") == null){
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if(mapVo.get("copy_code") == null){
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		String medTypeDict = medTypeDictService.queryMedTypeDict(getPage(mapVo));

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
	@RequestMapping(value = "/hrp/med/info/basic/type/saveMedTypeDict", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveMedTypeDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
System.out.println("============");
		if(mapVo.get("group_id") == null){
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if(mapVo.get("hos_id") == null){
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if(mapVo.get("copy_code") == null){
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		String medTypeDictJson = medTypeDictService.saveMedTypeDict(mapVo);

		return JSONObject.parseObject(medTypeDictJson);
	}
	
	/**
	 * @Description 根据主键加载数据
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/info/basic/type/queryMedTypeById", method = RequestMethod.POST)
	@ResponseBody
	public MedType queryMedTypeById(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}

		return medTypeService.queryMedTypeById(mapVo);
	}
	
	
	/**
	 * @Description 添加数据08102 药品分类字典
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/info/basic/type/addMedType", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addMedType(
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

		if (mapVo.get("acct_year") == null) {
			mapVo.put("acct_year", SessionManager.getAcctYear());
		}

		mapVo.put("spell_code",
				StringTool.toPinyinShouZiMu(mapVo.get("").toString()));
		mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("").toString()));

		String medTypeJson = medTypeService.addMedType(mapVo);

		return JSONObject.parseObject(medTypeJson);

	}
	
	/**
	 * @Description 更新数据08102 药品分类字典
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/info/basic/type/saveMedType", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveMedType(@RequestParam Map<String, Object> mapVo, Model mode)throws Exception {
		
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

		String medTypeJson = medTypeService.saveMedType(mapVo);

		return JSONObject.parseObject(medTypeJson);
	}
	
	/**
	 * @Description 删除数据 08102 药品分类字典
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/info/basic/type/deleteMedType", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteMedType(@RequestParam Map<String, Object> mapVo, Model mode)throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}

		String medTypeJson = medTypeService.deleteMedType(mapVo);

		return JSONObject.parseObject(medTypeJson);

	}
	
	/**
	 * @Description 查询数据 08102 药品分类字典
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/info/basic/type/queryMedType", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMedType(@RequestParam Map<String, Object> mapVo, Model mode)throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		String medType = medTypeService.queryMedType(mapVo);
		
		return JSONObject.parseObject(medType);
	}

	/**
	 * @Description 跳转页面药品类别变更
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/info/basic/type/medTypeDictPage", method = RequestMethod.GET)
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
		
		mode.addAttribute("medType", medTypeService.queryMedTypeById(mapVo));

		return "hrp/med/info/basic/type/medTypeDict";
	}
	
	/**
	 * @Description 下载导入模版 药品分类字典
	 * @param request
	 * @param response
	 * @param mode
	 * @return String
	 * @throws IOException
	 */
	@RequestMapping(value = "hrp/med/info/basic/type/downTemplate", method = RequestMethod.GET)
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
	@RequestMapping(value = "/hrp/med/info/basic/type/medTypeImportPage", method = RequestMethod.GET)
	public String medTypeImportPage(Model mode) throws Exception {

		return "hrp/med/info/basic/type/medTypeImport";

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
	@RequestMapping(value = "/hrp/med/info/basic/type/readMedTypeFiles", method = RequestMethod.POST)
	public String readMedTypeFiles(Plupload plupload, HttpServletRequest request, HttpServletResponse response, Model mode)
			throws IOException {
		
		Map<String,Object> map = new HashMap<String,Object>();//用于判断数据填的是和否,0和1
		map.put("否", 0);
		map.put("是", 1);
		map.put("0", 0);
		map.put("1", 1);
		
		
		List<MedType> list_err = new ArrayList<MedType>();//错误信息
		List<String[]> list = UploadUtil.readFile(plupload, request, response);//读取导入数据
		
		if(list.size() <= 1){
			return null;
		}
		
		

		try {
			for (int i = 1; i < list.size(); i++) {

				StringBuffer err_sb = new StringBuffer();
				MedType medType = new MedType();
				String temp[] = list.get(i);// 行
				
				Map<String, Object> utilMap = new HashMap<String, Object>();
				Map<String, Object> mapVo = new HashMap<String, Object>();
				mapVo.put("group_id", SessionManager.getGroupId());
				mapVo.put("hos_id", SessionManager.getHosId());
				mapVo.put("copy_code", SessionManager.getCopyCode());

				if (StringTool.isNotBlank(temp[0].toString().trim())) {
					
					medType.setMed_type_code(temp[0].toString().trim());
					mapVo.put("med_type_code", temp[0].toString().trim());
					
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
					
					if(!"0".equals(super_code)){
				        //判断上级编码是否存在
						utilMap.put("group_id", mapVo.get("group_id"));
						utilMap.put("hos_id", mapVo.get("hos_id"));
						utilMap.put("copy_code", mapVo.get("copy_code"));
						utilMap.put("med_type_code", super_code.toString());
						utilMap.put("type_level", type_level-1);
				        List<MedType> list1 = medTypeMapper.queryMedTypeByCode(utilMap);
				        
				        if(list1.size() == 0){
				        	err_sb.append("上级类别编码不存在 ");
				        }

				        for(MedType medType1 : list1 ){
				            utilMap.put("med_type_id", medType1.getMed_type_id());
				        }
					}
					
					
					
				} else {
					err_sb.append("药品类别编码为空 ");
				}

				
				if (StringTool.isNotBlank(temp[1].toString().trim())) {
					medType.setMed_type_name(temp[1].toString().trim());
					mapVo.put("med_type_name", temp[1].toString().trim());
				} else {
					err_sb.append("药品类别名称为空 ");
				}

				if (StringTool.isNotBlank(temp[2])) {
					if(map.get(temp[2]) == null){
						err_sb.append("是否末级【" + temp[2] + "】字段值不规范 ");
					}else{
						medType.setIs_last(Integer.parseInt(String.valueOf(map.get(temp[2]))));
						mapVo.put("is_last", map.get(temp[2]));
					}
					
				} else {
					err_sb.append("是否末级为空  ");
				}

				if (StringTool.isNotBlank(temp[3])) {
					
					if(map.get(temp[3]) == null){
						err_sb.append("是否停用" + temp[3] + "字段值不规范 ");
					}else{
						medType.setIs_stop(Integer.parseInt(String.valueOf(map.get(temp[3]))));
						mapVo.put("is_stop", map.get(temp[3]));
					}
					
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
					if(map.get(temp[5]) == null){
						err_sb.append("自动有效期【" + temp[5] + "】字段值不规范 ");
					}else{
						medType.setIs_auto_exp(Integer.parseInt(String.valueOf(map.get(temp[5]))));
						mapVo.put("is_auto_exp", map.get(temp[5]));
					}

				} else {
					err_sb.append("自动有效期为空  ");
				}
				
				List<MedType>  data_exc_extis = medTypeMapper.queryMedTypeByCode(mapVo);

				if (data_exc_extis.size() > 0 ) {
					err_sb.append("编码已经存在！ ");
				}
				
				if (err_sb.toString().length() > 0) {
					medType.setError_type(err_sb.toString());
					list_err.add(medType);
				} else {
					mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("med_type_name").toString().trim()));
					mapVo.put("wbx_code",StringTool.toWuBi(mapVo.get("med_type_name").toString().trim()));
					medTypeService.impMedType(mapVo,utilMap);
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
	@RequestMapping(value = "/hrp/med/info/basic/type/addBatchMedType", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addBatchMedType( @RequestParam(value = "ParamVo") String paramVo, Model mode) throws Exception {

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
		
		Map<String, Object> map = new HashMap<String, Object>();//判断是否,0和1
		map.put("是", 1);
		map.put("否", 0);
		map.put("1", 1);
		map.put("0", 0);

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
					err_sb.append("药品类别编码为空  ");
				}

				if (StringTool.isNotBlank(jsonObj.get("med_type_name"))) {
					medType.setMed_type_name((String) jsonObj.get("med_type_name"));
					mapVo.put("med_type_name", jsonObj.get("med_type_name"));
				} else {
					err_sb.append("药品类别名称为空  ");
				}
				
				String is_last = String.valueOf(jsonObj.get("is_last"));
				if (StringTool.isNotBlank(is_last)) {
					if(map.get(is_last) == null){
						err_sb.append("是否末级字段值【" + is_last + "】不规范");
					}else{
						medType.setIs_last(Integer.parseInt(String.valueOf(map.get(is_last))));
						mapVo.put("is_last",Integer.parseInt(String.valueOf(map.get(is_last))));
					}
				} else {
					err_sb.append("是否末级为空  ");
				}
				
				String is_stop = String.valueOf(jsonObj.get("is_stop"));
				if (StringTool.isNotBlank(is_stop)) {
					if(map.get(is_stop) == null){
						err_sb.append("是否停用字段值【" + is_stop + "】不规范");
					}else{
						medType.setIs_stop(Integer.parseInt(String.valueOf(map.get(is_stop))));
						mapVo.put("is_stop",Integer.parseInt(String.valueOf(map.get(is_stop))));
					}
				} else {
					err_sb.append("是否停用为空  ");
				}

				if (StringTool.isNotBlank(jsonObj.get("note"))) {
					medType.setNote((String) jsonObj.get("note"));
					mapVo.put("note", jsonObj.get("note"));
				} else {
					err_sb.append("备注为空  ");
				}

				String is_auto_exp = String.valueOf(jsonObj.get("is_auto_exp"));
				if (StringTool.isNotBlank(is_auto_exp)) {
					if(map.get(is_auto_exp) == null){
						err_sb.append("是否自动有效期字段值【" + is_last + "】不规范");
					}else{
						medType.setIs_auto_exp(Integer.parseInt(String.valueOf(map.get(is_auto_exp))));
						mapVo.put("is_auto_exp", Integer.parseInt(String.valueOf(map.get(is_auto_exp))));
					}
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
			        List<MedType> list1 = medTypeMapper.queryMedTypeByCode(utilMap);
			        
			        if(list1.size() == 0){
			        	err_sb.append("上级类别编码不存在  ");
			        }
			        
			        for(MedType medType1 : list1 ){
			            utilMap.put("med_type_id", medType1.getMed_type_id());
			        }
				}
				
				
				
				List<MedType>  data_exc_extis = medTypeMapper.queryMedTypeByCode(mapVo);
				if (data_exc_extis.size() > 0 ) {
					err_sb.append("编码已经存在！ ");
				}

				if (err_sb.toString().length() > 0) {
					medType.setError_type(err_sb.toString());
					list_err.add(medType);
				} else {
					mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("med_type_name").toString()));
					mapVo.put("wbx_code",StringTool.toWuBi(mapVo.get("med_type_name").toString()));
				     medTypeService.impMedType(mapVo,utilMap);
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

	
	
	//导出
	@RequestMapping(value="/hrp/med/info/basic/type/expMedTypeToExcel", method = RequestMethod.GET)  
	public String expMedTypeToExcel(@RequestParam Map<String, Object> mapVo,Plupload plupload,HttpServletRequest request,
			    HttpServletResponse response,Model mode) throws IOException { 
		
		if(mapVo.get("group_id") == null){
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		
		if(mapVo.get("hos_id") == null){
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		
		if(mapVo.get("copy_code") == null){
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		
		List<MedType> medTypeList = medTypeService.queryMedTypeList(mapVo);
		
		if(medTypeList.size() == 0){
			return null;
		}
		
		List<List> list = new ArrayList<List>();//所有数据List
		
		//组装Excel表头
		List<String> headList = new ArrayList<String>();//表头List
		headList.add("药品类别编码");
		headList.add("药品类别名称");
		headList.add("上级编码");
		headList.add("是否停用");
		headList.add("是否自动有效期");
		headList.add("药品财务分类");
		list.add(headList);
		
		for(int x = 0 ; x < medTypeList.size() ; x++){
			
			List<String> row = new ArrayList<String>();//数据List
			
			MedType m = medTypeList.get(x);
			row.add(m.getMed_type_code());
			row.add(m.getMed_type_name());
			row.add(m.getSuper_code());
			
			String is_stop = m.getIs_stop() == 1 ? "是" :"否" ;
			row.add(is_stop);
			
			String is_auto_exp = m.getIs_auto_exp() == 1 ? "是" :"否" ;
			row.add(is_auto_exp);
			row.add(m.getFim_type_name());
			
			list.add(row);
		}
		
		
		
		String fileName = "药品分类.xlsx";
		
		String ctxPath = request.getSession().getServletContext().getRealPath("/")
				+ "\\" + "excelTemplate\\"+"\\med\\downTemplate\\";
				
		String downLoadPath = ctxPath + fileName;
				
		ExcelWriterTo2007.exportExcel(new File(downLoadPath), list);
				
		printTemplate(request, response, "med\\downTemplate", fileName);
		return null;
	}
	
}
