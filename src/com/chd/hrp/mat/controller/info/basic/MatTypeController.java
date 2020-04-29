/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */

package com.chd.hrp.mat.controller.info.basic;

import java.io.IOException;
import java.text.DateFormat;
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
import com.chd.hrp.mat.dao.info.basic.MatTypeMapper;
import com.chd.hrp.mat.entity.MatType;
import com.chd.hrp.mat.service.base.MatCommonService;
import com.chd.hrp.mat.service.info.basic.MatTypeDictService;
import com.chd.hrp.mat.service.info.basic.MatTypeService;

/**
 * 
 * @Description: 04103 物资分类字典
 * @Table: MAT_TYPE
 * @Author: bell
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Controller
public class MatTypeController extends BaseController {

	private static Logger logger = Logger.getLogger(MatTypeController.class);

	// 引入Service服务
	@Resource(name = "matTypeService")
	private final MatTypeService matTypeService = null;
	@Resource(name = "matTypeDictService")
	private final MatTypeDictService matTypeDictService = null;
	@Resource(name = "matCommonService")
	private final MatCommonService matCommonService = null;
	
	@Resource(name = "matTypeMapper")
	private final MatTypeMapper matTypeMapper = null;    

	/**
	 * @Description 主页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/info/basic/type/matTypeMainPage", method = RequestMethod.GET)
	public String matTypeMainPage(Model mode) throws Exception {

		return "hrp/mat/info/basic/type/matTypeMain";
	}
	
	/**
	 * @Description 编码规则
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/info/basic/type/getRules", method = RequestMethod.POST)
	@ResponseBody
	public String getRules(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
        
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
	    mapVo.put("copy_code", SessionManager.getCopyCode());
	    mapVo.put("proj_code", "MAT_TYPE");
	    mapVo.put("mod_code", "04");
	    String rules = matCommonService.getMatHosRules(mapVo);//获取编码规则2-2-2....
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
	@RequestMapping(value = "/hrp/mat/info/basic/type/queryMatTypeByTree", method = RequestMethod.POST)
	@ResponseBody
	public String queryMatTypeByTree(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		String group = matTypeService.queryMatTypeByTree(mapVo);

		return group;
	}
	
	/**
	 * @Description 树形结构（含变更号）
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/info/basic/type/queryMatTypeDictByTree", method = RequestMethod.POST)
	@ResponseBody
	public String queryMatTypeDictByTree(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
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
		String group = matTypeDictService.queryMatTypeDictByTree(mapVo);

		return group;
	}
	
	/**
	 * @Description 根据主键加载数据
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/info/basic/type/queryMatTypeById", method = RequestMethod.POST)
	@ResponseBody
	public MatType queryMatTypeById(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}

		return matTypeService.queryMatTypeById(mapVo);
	}

	/**
	 * @Description 添加数据 04103 物资分类字典
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/info/basic/type/addMatType", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addMatType(
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
				StringTool.toPinyinShouZiMu(mapVo.get("mat_type_name").toString()));
		mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("mat_type_name").toString()));

		String matTypeJson;
		try {
			matTypeJson = matTypeService.addMatType(mapVo);
		} catch (Exception e) {
			matTypeJson = e.getMessage();
		}

		return JSONObject.parseObject(matTypeJson);

	}

	/**
	 * @Description 更新数据 04103 物资分类字典
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/info/basic/type/saveMatType", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveMatType(@RequestParam Map<String, Object> mapVo, Model mode)throws Exception {
		
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());
			mapVo.put("change_user", SessionManager.getUserId());
			mapVo.put("change_date",new Date());
			mapVo.put("change_note", "变更");
			if (mapVo.get("mat_type_id") == null) {
				mapVo.put("mat_type_id", "");
			}
	
			String matTypeJson;
			try {
				matTypeJson = matTypeService.saveMatType(mapVo);
				matTypeJson = matTypeDictService.saveMatTypeDict(mapVo);
			} catch (Exception e) {
				matTypeJson = e.getMessage();
			}
	
			return JSONObject.parseObject(matTypeJson);
	}

	/**
	 * @Description 删除数据 04103 物资分类字典
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/info/basic/type/deleteMatType", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteMatType(@RequestParam Map<String, Object> mapVo, Model mode)throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}

		String matTypeJson;
		try {
			matTypeJson = matTypeService.deleteMatType(mapVo);
		} catch (Exception e) {
			matTypeJson = e.getMessage();
		}

		return JSONObject.parseObject(matTypeJson);

	}

	/**
	 * @Description 查询数据 04103 物资分类字典
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/info/basic/type/queryMatType", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMatType(@RequestParam Map<String, Object> mapVo, Model mode)throws Exception {
		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		String matType = matTypeService.queryMatType(mapVo);
		
		return JSONObject.parseObject(matType);
	}
	
	/**
	 * @Description 跳转页面物资类别变更
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/info/basic/type/matTypeDictPage", method = RequestMethod.GET)
	public String matTypeDictPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		mode.addAttribute("matType", matTypeService.queryMatTypeById(mapVo));

		return "hrp/mat/info/basic/type/matTypeDict";
	}

	/**
	 * @Description 
	 * 添加数据 04104 物资分类变更表
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/mat/info/basic/type/saveMatTypeDict", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveMatTypeDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if(mapVo.get("group_id") == null){
			mapVo.put("group_id", SessionManager.getGroupId());
		}
		if(mapVo.get("hos_id") == null){
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if(mapVo.get("copy_code") == null){
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}

		String matTypeDictJson;
		try {
			matTypeDictJson = matTypeDictService.saveMatTypeDict(mapVo);
		} catch (Exception e) {
			matTypeDictJson = e.getMessage();
		}

		return JSONObject.parseObject(matTypeDictJson);
	}

	/**
	 * @Description 
	 * 查询数据 04104 物资分类变更表
	 * @param  mapVo
	 * @param  mode
	 * @return Map<String, Object>
	 * @throws Exception
	*/
	@RequestMapping(value = "/hrp/mat/info/basic/type/queryMatTypeDict", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMatTypeDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
	    if(mapVo.get("group_id") == null){
	    	mapVo.put("group_id", SessionManager.getGroupId());
		}
		if(mapVo.get("hos_id") == null){
			mapVo.put("hos_id", SessionManager.getHosId());
		}
		if(mapVo.get("copy_code") == null){
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		
		String matTypeDict = matTypeDictService.queryMatTypeDict(getPage(mapVo));

		return JSONObject.parseObject(matTypeDict);
	}

	/**
	 * @Description 下载导入模版 04103 物资分类字典
	 * @param request
	 * @param response
	 * @param mode
	 * @return String
	 * @throws IOException
	 */
	@RequestMapping(value = "hrp/mat/info/basic/type/downTemplate", method = RequestMethod.GET)
	public String downTemplate(Plupload plupload, HttpServletRequest request,
			HttpServletResponse response, Model mode) throws IOException {

		printTemplate(request, response, "mat\\downTemplate","04102物资分类字典.xls");

		return null;
	}

	/**
	 * @Description 导入数据 04103 物资分类字典
	 * @param plupload
	 * @param request
	 * @param response
	 * @param mode
	 * @return String
	 * @throws IOException
	 */
	@RequestMapping(value = "/hrp/mat/info/basic/type/readMatTypeFiles", method = RequestMethod.POST)
	public String readMatTypeFiles(Plupload plupload,
			HttpServletRequest request, HttpServletResponse response, Model mode)
			throws IOException {

		List<MatType> list_err = new ArrayList<MatType>();

		List<String[]> list = UploadUtil.readFile(plupload, request, response);

		try {
			for (int i = 0; i < list.size(); i++) {

				StringBuffer err_sb = new StringBuffer();

				MatType matType = new MatType();

				String temp[] = list.get(i);// 行
				
				Map<String, Object> mapVo = new HashMap<String, Object>();

				mapVo.put("group_id", SessionManager.getGroupId());

				mapVo.put("hos_id", SessionManager.getHosId());

				mapVo.put("copy_code", SessionManager.getCopyCode());

				if (StringTool.isNotBlank(temp[0])) {

					matType.setMat_type_code(temp[0]);
					
					mapVo.put("mat_type_code", temp[0]);

				} else {

					err_sb.append("物资类别编码为空");

				}

				if (StringTool.isNotBlank(temp[1])) {

					matType.setMat_type_name(temp[1]);
					
					mapVo.put("mat_type_name", temp[1]);

				} else {

					err_sb.append("物资类别名称为空");

				}


				if (StringTool.isNotBlank(temp[2])) {

					matType.setIs_last(Integer.parseInt(temp[2]));
					
					mapVo.put("is_last", temp[2]);

				} else {

					err_sb.append("是否末级为空  ");

				}

				if (StringTool.isNotBlank(temp[3])) {

					matType.setIs_stop(Integer.parseInt(temp[3]));
					
					mapVo.put("is_stop", temp[3]);

				} else {

					err_sb.append("是否停用为空  ");

				}

				

				if (StringTool.isNotBlank(temp[4])) {

					matType.setNote(temp[4]);
					
					mapVo.put("note", temp[4]);

				} else {

                    matType.setNote(null);
					
					mapVo.put("note", null);

				}

				if (StringTool.isNotBlank(temp[5])) {

					matType.setIs_auto_exp(Integer.parseInt(temp[5]));
					
					mapVo.put("is_auto_exp", temp[5]);

				} else {

					err_sb.append("自动有效期为空  ");

				}
				
				//判断是否符合编码规则
				Map<String, Object> comMap = new HashMap<String, Object>();
				
				comMap.put("group_id", mapVo.get("group_id"));
				
				comMap.put("hos_id", mapVo.get("hos_id"));
				
				comMap.put("copy_code", mapVo.get("copy_code"));
				
				comMap.put("proj_code", "mat_type".toUpperCase());
				
				comMap.put("mod_code", "04");
				
		        String rules = matCommonService.getMatHosRules(comMap);//获取编码规则2-2-2....
		        
		        String mat_type_code = (String)mapVo.get("mat_type_code");//类别编码
		        
		        StringBuffer s_code = new StringBuffer();//上级编码
				
		        String super_code_temp = "0";
		        int type_level =0;//级次 
		        int begin_index = 0;
		        int end_index = 0;
		        int length = 0;
		        String[] ruless  = rules.split("-");
		        
		        int strLength = mat_type_code.length();
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
		        	super_code_temp = mat_type_code.substring(begin_index, end_index);
		        	
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
					utilMap.put("mat_type_code", super_code.toString());
					utilMap.put("type_level", type_level-1);
			        List<MatType> list1 = matTypeMapper.queryMatTypeByCode(utilMap);
			        
			        if(list1.size() == 0){
			        	
			        	err_sb.append("上级类别编码不存在 ");
			        }

			        for(MatType matType1 : list1 ){
			        	
			            utilMap.put("mat_type_id", matType1.getMat_type_id());
			        }
				}
				
				
				
				List<MatType>  data_exc_extis = matTypeMapper.queryMatTypeByCode(mapVo);

				if (data_exc_extis.size() > 0 ) {
					
					err_sb.append("编码已经存在！ ");
				}
				
				if (err_sb.toString().length() > 0) {

					matType.setError_type(err_sb.toString());

					list_err.add(matType);

				} else {
					
					mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("mat_type_name").toString()));
					
					mapVo.put("wbx_code",StringTool.toWuBi(mapVo.get("mat_type_name").toString()));

					matTypeService.impMatType(mapVo,utilMap);

				}

			}

		} catch (DataAccessException e) {

			MatType data_exc = new MatType();

			data_exc.setError_type("导入系统出错");

			list_err.add(data_exc);

		}

		response.getWriter().print(ChdJson.toJson(list_err, list_err.size()));

		return null;

	}

	/**
	 * @Description 批量添加数据 04103 物资分类字典
	 * @param plupload
	 * @param request
	 * @param response
	 * @param mode
	 * @return String
	 * @throws IOException
	 */
	@RequestMapping(value = "/hrp/mat/info/basic/type/addBatchMatType", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addBatchMatType(
			@RequestParam(value = "ParamVo") String paramVo, Model mode)
			throws Exception {

		List<MatType> list_err = new ArrayList<MatType>();

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

				MatType matType = new MatType();

				JSONObject jsonObj = JSONObject.parseObject(it.next().toString());


				if (StringTool.isNotBlank(jsonObj.get("mat_type_code"))) {

					matType.setMat_type_code((String) jsonObj.get("mat_type_code"));
					
					mapVo.put("mat_type_code", jsonObj.get("mat_type_code"));
					
				} else {

					err_sb.append("物资类别编码为空  ");

				}

				if (StringTool.isNotBlank(jsonObj.get("mat_type_name"))) {

					matType.setMat_type_name((String) jsonObj.get("mat_type_name"));
					
					mapVo.put("mat_type_name", jsonObj.get("mat_type_name"));
					
				} else {

					err_sb.append("物资类别名称为空  ");

				}

				if (StringTool.isNotBlank(jsonObj.get("is_last"))) {

					matType.setIs_last(Integer.parseInt(jsonObj.get("is_last").toString()));
					
					mapVo.put("is_last", jsonObj.get("is_last"));
					
				} else {

					err_sb.append("是否末级为空  ");

				}

				if (StringTool.isNotBlank(jsonObj.get("is_stop"))) {

					matType.setIs_stop(Integer.parseInt(jsonObj.get("is_stop").toString()));
					
					mapVo.put("is_stop", jsonObj.get("is_stop"));
					
				} else {

					err_sb.append("是否停用为空  ");

				}

				if (StringTool.isNotBlank(jsonObj.get("note"))) {

					matType.setNote((String) jsonObj.get("note"));
					
					mapVo.put("note", jsonObj.get("note"));
					
				} else {

					matType.setNote(null);
					
					mapVo.put("note", null);

				}


				if (StringTool.isNotBlank(jsonObj.get("is_auto_exp"))) {

					matType.setIs_auto_exp(Integer.parseInt(jsonObj.get("is_auto_exp").toString()));
					
					mapVo.put("is_auto_exp", jsonObj.get("is_auto_exp"));
					
				} else {

					err_sb.append("自动有效期为空  ");
				}
				//判断是否符合编码规则
				Map<String, Object> comMap = new HashMap<String, Object>();
				
				comMap.put("group_id", mapVo.get("group_id"));
				
				comMap.put("hos_id", mapVo.get("hos_id"));
				
				comMap.put("copy_code", mapVo.get("copy_code"));
				
				comMap.put("proj_code", "mat_type".toUpperCase());
				
				comMap.put("mod_code", "04");
				
		        String rules = matCommonService.getMatHosRules(comMap);//获取编码规则2-2-2....
		        
		        String mat_type_code = (String)mapVo.get("mat_type_code");//类别编码
		        
		        StringBuffer s_code = new StringBuffer();//上级编码
				
		        String super_code_temp = "0";
		        int type_level =0;//级次 
		        int begin_index = 0;
		        int end_index = 0;
		        int length = 0;
		        String[] ruless  = rules.split("-");
		        
		        int strLength = mat_type_code.length();
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
		        	super_code_temp = mat_type_code.substring(begin_index, end_index);
		        	
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
					utilMap.put("mat_type_code", super_code.toString());
					utilMap.put("type_level", type_level-1);
			        List<MatType> list1 = matTypeMapper.queryMatTypeByCode(utilMap);
			        
			        if(list1.size() == 0){
			        	
			        	err_sb.append("上级类别编码不存在  ");
			        }
			        
			        for(MatType matType1 : list1 ){
			        	
			            utilMap.put("mat_type_id", matType1.getMat_type_id());
			        }

				}
				
				
				
				List<MatType>  data_exc_extis = matTypeMapper.queryMatTypeByCode(mapVo);

				if (data_exc_extis.size() > 0 ) {
					
					err_sb.append("编码已经存在！ ");
				}

				if (err_sb.toString().length() > 0) {
					
					matType.setError_type(err_sb.toString());
					
					list_err.add(matType);
				} else {
					
					mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("mat_type_name").toString()));
					
					mapVo.put("wbx_code",StringTool.toWuBi(mapVo.get("mat_type_name").toString()));
					
				     matTypeService.impMatType(mapVo,utilMap);
				}
			}
			
		} catch (DataAccessException e) {
			
			MatType data_exc = new MatType();
			
			list_err.add(data_exc);
			
			return JSONObject.parseObject("{\"msg\":\"导入系统出错,请重新导入.\",\"state\":\"err\"}");
		}
		
		if (list_err.size() > 0) {
			
			return JSONObject.parseObject(ChdJson.toJson(list_err,list_err.size()));
			
		} else {
			
			return JSONObject.parseObject("{\"msg\":\"导入成功.\",\"state\":\"true\"}");
		}
	}
	
	/**
	 * @Description 导入页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/mat/info/basic/type/matTypeImportPage", method = RequestMethod.GET)
	public String matTypeImportNewPage(Model mode) throws Exception {

		return "hrp/mat/info/basic/type/matTypeImport";
	}
	
	/**
	 * @Description 
	 * 导入数据 
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/hrp/mat/info/basic/type/import",method = RequestMethod.POST)  
	@ResponseBody
	public Map<String, Object> importData(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		Map<String, Object> retMap = null;
		try {
			retMap = matTypeService.importData(mapVo);
		} catch (Exception e) {
			retMap = new HashMap<String, Object>();
			retMap.put("state", "false");
			retMap.put("error", e.getMessage());
		}
		
		return retMap;
	}
}
