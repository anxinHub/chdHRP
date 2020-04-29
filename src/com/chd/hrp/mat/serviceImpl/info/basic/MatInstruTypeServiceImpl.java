/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.mat.serviceImpl.info.basic;

import java.util.*;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.SessionManager;
import com.chd.base.SysPage;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.base.util.SpreadTableJSUtil;
import com.chd.base.util.StringTool;
import com.chd.base.util.UUIDLong;
import com.chd.hrp.mat.dao.base.MatCommonMapper;
import com.chd.hrp.mat.dao.info.basic.MatInstruTypeMapper;
import com.chd.hrp.mat.entity.MatInstruType;
import com.chd.hrp.mat.service.info.basic.MatInstruTypeService;
import com.chd.hrp.sys.dao.notGeneral.SysFunUtilMapper;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Description:
 * 医疗器械字典
 * @Table:
 * MAT_INSTRU_TYPE
 * @Author: weixiaofeng
 * @Version: 1.0
 */
@Service("matInstruTypeService")
public class MatInstruTypeServiceImpl implements MatInstruTypeService {

	private static Logger logger = Logger.getLogger(MatInstruTypeServiceImpl.class);
	//引入DAO操作
	@Resource(name = "matInstruTypeMapper")
	private final MatInstruTypeMapper MatInstruTypeMapper = null;    
	@Resource(name = "matCommonMapper")
	private final MatCommonMapper matCommonMapper = null;      
	@Resource(name = "sysFunUtilMapper")
	private final SysFunUtilMapper sysFunUtilMapper = null;    
	
	@Override
	public String queryMatInstruTypeByTree(Map<String, Object> entityMap)
			throws DataAccessException {
		StringBuilder jsonResult = new StringBuilder();
		
		jsonResult.append("[");
		List<MatInstruType> list = MatInstruTypeMapper.queryMatInstruType(entityMap);
		if (list.size()>0) {
			int row = 0;
			for (MatInstruType mt : list) {
				if (row == 0) {
					jsonResult.append("{");
				} else {
					jsonResult.append(",{");
				}
				row++;
				jsonResult.append("\"pcode\":\""+mt.getSuper_code()+"\"," + 
						"\"code\":\"" + mt.getInstru_type_code()+ "\"," + 
						"\"id\":\"" + mt.getInstru_type_id()+ "\"," + 
						"\"text\":" + "\"" + mt.getInstru_type_code()+ " " + mt.getInstru_type_name() + "\"" 
					);
				jsonResult.append("}");
			}
		}
		jsonResult.append("]");
		return jsonResult.toString();
	}
	
	/**
	 * @Description 
	 * 添加医疗器械字典<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addMatInstruType(Map<String,Object> entityMap)throws DataAccessException{
		
		entityMap.remove("super_code");
		entityMap.remove("spell_code");
		entityMap.remove("wbx_code");
		//判断名称编码是否重复
		List<Map<String, Object>> mtlist = queryMatInstruTypeByCodeName(entityMap);
		if (mtlist.size()>0) {
			for(Map<String, Object> MatInstruType : mtlist ){
				if(entityMap.get("instru_type_code").equals(MatInstruType.get("code"))){
					return "{\"error\":\"编码：" + MatInstruType.get("code").toString() + "已存在.\"}";
				}
				//if(entityMap.get("mat_type_name").equals(MatInstruType.get("name"))){
					//return "{\"error\":\"名称：" + MatInstruType.get("name").toString() + "已存在.\"}";
				//}
			}
		}
		
		//判断是否符合编码规则
		Map<String, Object> comMap = new HashMap<String, Object>();
		comMap.put("group_id", entityMap.get("group_id"));
		comMap.put("hos_id", entityMap.get("hos_id"));
		comMap.put("copy_code", entityMap.get("copy_code"));
		comMap.put("proj_code", "MAT_INSTRU_TYPE".toUpperCase());
		comMap.put("mod_code", "04");
        String rules = matCommonMapper.getMatHosRules(comMap);//获取编码规则2-2-2....
        if(rules.length() <= 0){
        	return "{\"error\":\"请维护医疗器械分类编码规则！\"}";
        }
        String instru_type_code = (String)entityMap.get("instru_type_code");//类别编码
        StringBuffer s_code = new StringBuffer();//上级编码
        String super_code_temp = "0";
        int type_level =0;//级次 
        int begin_index = 0;
        int end_index = 0;
        int length = 0;
        String[] ruless  = rules.split("-");
        int strLength = instru_type_code.length();
        int wholeLength = 0;
        for(String rule : ruless){
        	wholeLength += Integer.parseInt(rule);
        }
        if(wholeLength < strLength)
        	return "{\"error\":\"添加失败 编码位数不符合编码规则 请重新输入！\"}";
        for(int i = 0; i < ruless.length ; i++){
        	if("0".equals(ruless[i])){
        		break;
        	}
    		s_code.append(super_code_temp);
        	length = Integer.valueOf(ruless[i]);
        	end_index = begin_index + length;
        	//防止越界
        	if(end_index >= strLength){
        		end_index = strLength;
        	}
        	super_code_temp = instru_type_code.substring(begin_index, end_index);
        	
        	if(super_code_temp.length() == length){
        		type_level += 1;
        	}else{
        		return "{\"error\":\"添加失败 编码位数不符合编码规则 请重新输入！\"}";
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
        entityMap.put("super_code", super_code.toString());
        entityMap.put("type_level", type_level);
		entityMap.put("spell_code", StringTool.toPinyinShouZiMu(entityMap.get("instru_type_name").toString()));
		entityMap.put("wbx_code", StringTool.toWuBi(entityMap.get("instru_type_name").toString()));

		Map<String, Object> utilMap = new HashMap<String, Object>();
		if(!"0".equals(super_code)){
	        //判断上级编码是否存在
			utilMap.put("group_id", entityMap.get("group_id"));
			utilMap.put("hos_id", entityMap.get("hos_id"));
			utilMap.put("copy_code", entityMap.get("copy_code"));
			utilMap.put("instru_type_code", super_code.toString());
			utilMap.put("type_level", type_level-1);
	        List<MatInstruType> list = MatInstruTypeMapper.queryMatInstruTypeByCode(utilMap);
	        for(MatInstruType MatInstruType : list ){
	            if(1==MatInstruType.getIs_stop()){
	            	return "{\"error\":\"输入类别编码的上级类别编码（"+super_code+"）已停用，不允许添加！\"}";
	            }
	        }
	        if(list.size() == 0){
	        	return "{\"error\":\"输入类别编码的上级类别编码（"+super_code+"）不存在，不允许添加，请添加上级类别后再操作！\"}";
	   	 	}
	        for(MatInstruType MatInstruType : list ){
	            utilMap.put("instru_type_id", MatInstruType.getInstru_type_id());
	        }
		}
        
        try {
        	entityMap.put("instru_type_id", UUIDLong.absStringUUID());
        	
            //新增医疗器械分类
			MatInstruTypeMapper.addMatInstruType(entityMap);
			
			if(!"0".equals(super_code)){
		        //更新上级类别is_last为否(0)
	            utilMap.put("is_last", "0");
				MatInstruTypeMapper.updateMatInstruType(utilMap);
			}
			return "{\"msg\":\"添加成功.\",\"state\":\"true\",\"instru_type_id\":\""+entityMap.get("instru_type_id")+"\"}";
		}
		catch (DataAccessException e) {
			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"添加失败\"}");
		}
	}
	
	/**
	 * @Description 
	 * 批量添加医疗器械字典<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addBatchMatInstruType(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			MatInstruTypeMapper.addBatchMatInstruType(entityList);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";
		}
		catch (DataAccessException e) {
			logger.error(e.getMessage(), e);

			throw new SysException("{\"error\":\"添加失败\"}");
			//return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addBatchMatInstruType\"}";
		}
	}
	
		/**
	 * @Description 
	 * 更新医疗器械字典<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateMatInstruType(Map<String,Object> entityMap)throws DataAccessException{
		
		try {
			
			entityMap.put("spell_code", StringTool.toPinyinShouZiMu(entityMap.get("instru_type_name").toString()));
			entityMap.put("wbx_code", StringTool.toWuBi(entityMap.get("instru_type_name").toString()));
			
			MatInstruTypeMapper.updateMatInstruType(entityMap);
			return "{\"msg\":\"更新成功.\",\"state\":\"true\",\"instru_type_id\":\""+entityMap.get("instru_type_id")+"\"}";
		}
		catch (DataAccessException e) {
			logger.error(e.getMessage(), e);

			throw new SysException("{\"error\":\"更新失败\"}");
			//return "{\"error\":\"更新失败 数据库异常 请联系管理员! 方法 updateMatInstruType\"}";
		}	
	}
	/**
	 * @Description 
	 * 批量更新医疗器械字典<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateBatchMatInstruType(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
		  MatInstruTypeMapper.updateBatchMatInstruType(entityList);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (DataAccessException e) {

			logger.error(e.getMessage(), e);

			throw new SysException("{\"error\":\"更新失败\"}");
			//return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 updateBatchMatInstruType\"}";

		}	
		
	}
	/**
	 * @Description 
	 * 删除医疗器械字典<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
    public String deleteMatInstruType(Map<String, Object> entityMap) throws DataAccessException {
    
		try {
			//判断是否被使用
			String reStr="";
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("dict_code", "MAT_INSTRU_TYPE".toUpperCase());
			map.put("group_id", entityMap.get("group_id"));
			map.put("hos_id", entityMap.get("hos_id"));
			map.put("copy_code", entityMap.get("copy_code"));
			map.put("dict_id_str", entityMap.get("instru_type_id"));
			map.put("acc_year", "");
			map.put("p_flag", "1");
			sysFunUtilMapper.querySysDictDelCheck(map);
			
			if(map.get("reNote")!=null) {
				reStr+=map.get("reNote");
			}
			
			if(reStr!=null && !reStr.equals("")){
				return "{\"error\":\"删除失败，选择的医疗器械分类被以下业务使用：【"+reStr.substring(0,reStr.length()-1)+"】。\",\"state\":\"false\"}";
			}
			
			int i = MatInstruTypeMapper.queryIsInvUse(entityMap);
			
			if(i > 0 ){
				return "{\"warn\":\"当前医疗器械分类已被物资材料使用!\",\"state\":\"false\"}";
			}
			Map<String, Object> dictMap = new HashMap<String, Object>();
			dictMap.put("instru_type_code", MatInstruTypeMapper.selectSuperCode(entityMap).toString());
			//删除医疗器械分类
			MatInstruTypeMapper.deleteMatInstruType(entityMap);
			
			
			dictMap.put("group_id", entityMap.get("group_id"));
			dictMap.put("hos_id", entityMap.get("hos_id"));
			dictMap.put("copy_code", entityMap.get("copy_code"));
			//修改上级编码非末级标记
			dictMap.put("instru_type_id", entityMap.get("instru_type_id"));
			dictMap.put("is_last", "0");
			MatInstruTypeMapper.updateMatInstruType(dictMap);
			
			MatInstruTypeMapper.updateLast(dictMap);
		}catch (DataAccessException e) {
			logger.error(e.getMessage(), e);
			
			throw new SysException("{\"error\":\"删除失败\"}");
		}	
		return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
  }
    
	/**
	 * @Description 
	 * 批量删除医疗器械字典<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String deleteBatchMatInstruType(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			MatInstruTypeMapper.deleteBatchMatInstruType(entityList);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		}catch (DataAccessException e) {
			logger.error(e.getMessage(), e);

			throw new SysException("{\"error\":\"删除失败\"}");
			//return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 deleteBatchMatInstruType\"}";
		}	
	}
	
	/**
	 * @Description 
	 * 添加医疗器械字典<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String saveMatInstruType(Map<String,Object> entityMap)throws DataAccessException{
				
		if ("".equals(entityMap.get("instru_type_id"))) {
			return addMatInstruType(entityMap);
		}else{
			return updateMatInstruType(entityMap);
		}		
	}
	/**
	 * @Description 
	 * 查询结果集医疗器械字典<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String queryMatInstruType(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<MatInstruType> list = MatInstruTypeMapper.queryMatInstruType(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<MatInstruType> list = MatInstruTypeMapper.queryMatInstruType(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
	}
	
	/**
	 * @Description 
	 * 获取对象医疗器械字典<BR> 
	 * @param  entityMap<BR>
	 *  参数为主键
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public MatInstruType queryMatInstruTypeById(Map<String,Object> entityMap)throws DataAccessException{
		
		return MatInstruTypeMapper.queryMatInstruTypeById(entityMap);
	}
	
	/**
	 * @Description 
	 * 获取医疗器械字典<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return MatInstruType
	 * @throws DataAccessException
	*/
	@Override
	public MatInstruType queryMatInstruTypeByUniqueness(Map<String,Object> entityMap)throws DataAccessException{
		return MatInstruTypeMapper.queryMatInstruTypeByUniqueness(entityMap);
	}
	
	/**
	 * 根据code和name查询出所有符合要求的数据
	 * @param entityMap
	 * @return Map
	 */
	@Override
	public List<Map<String, Object>> queryMatInstruTypeByCodeName(Map<String,Object> entityMap){
		Map<String,Object> utilMap=new HashMap<String,Object>();
		utilMap.put("group_id", entityMap.get("group_id"));
		utilMap.put("hos_id", entityMap.get("hos_id"));
		utilMap.put("copy_code", entityMap.get("copy_code"));
		utilMap.put("table_name", "MAT_INSTRU_TYPE");
		if(entityMap.get("instru_type_id") != null && !"".equals(entityMap.get("instru_type_id").toString())){
			utilMap.put("c_id", "instru_type_id");
			utilMap.put("c_id_value", entityMap.get("instru_type_id"));
		}
		utilMap.put("c_code", "instru_type_code");
		utilMap.put("c_code_value", entityMap.get("instru_type_code"));
		utilMap.put("c_name", "instru_type_name");
		utilMap.put("c_name_value", entityMap.get("instru_type_name"));
		return matCommonMapper.existsMatCodeName(utilMap);
	}
	
	@Override
	public Map<String, Object> importData(Map<String, Object> entityMap) throws DataAccessException{
		Map<String, Object> retMap = new HashMap<String, Object>();
		retMap.put("state", "true");
		try {	
			List<Map<String, Object>> typeAddList = new ArrayList<Map<String,Object>>();
			List<String> typeUpdateList = new ArrayList<String>();
			String group_id = SessionManager.getGroupId();
			String hos_id = SessionManager.getHosId();
			String copy_code = SessionManager.getCopyCode();
			String user_id = SessionManager.getUserId();
			
			Map<String, Object> seachMap = new HashMap<String, Object>();
			seachMap.put("group_id", group_id);
			seachMap.put("hos_id", hos_id);
			seachMap.put("copy_code", copy_code);
			seachMap.put("user_id", user_id);
			
			//获取物资分类
			List<Map<String, Object>> MatInstruTypeList = MatInstruTypeMapper.queryMatInstruTypeListForImport(seachMap);
			Map<String, String> MatInstruTypeMap = new HashMap<String, String>();
			for(Map<String, Object> MatInstruType : MatInstruTypeList){
				MatInstruTypeMap.put(MatInstruType.get("INSTRU_TYPE_CODE").toString(), MatInstruType.get("INSTRU_TYPE_ID").toString());
				MatInstruTypeMap.put(MatInstruType.get("INSTRU_TYPE_NAME").toString(), MatInstruType.get("INSTRU_TYPE_ID").toString());
			}
			
			//是否
			Map<String, String> yesOrNoMap = new HashMap<String, String>();
			yesOrNoMap.put("1", "1");
			yesOrNoMap.put("0", "0");
			yesOrNoMap.put("是", "1");
			yesOrNoMap.put("否", "0");
			
			//获取医疗器械分类编码规则
			seachMap.put("proj_code", "MAT_INSTRU_TYPE".toUpperCase());
			seachMap.put("mod_code", "04");
	        String rules = matCommonMapper.getMatHosRules(seachMap);//获取编码规则2-2-2....
	        String[] ruless  = rules.split("-");
			
			//解析前台数据
			String data = entityMap.get("data").toString();
			List<Map<String, List<String>>> dataList = SpreadTableJSUtil.toListMap(data, 1);
			if(dataList==null || dataList.size()==0){

				retMap.put("state", "false");
				retMap.put("error", "没有数据！");
			}
			String instru_type_code;
			String instru_type_name;
			String instru_type_id;
			List<String> rowList = null;

	        StringBuffer s_code = null;//上级编码
	        String super_code_temp;
	        int type_level;//级次 
	        int begin_index;
	        int end_index;
	        int length;
	        int strLength;
			
			for(Map<String, List<String>> dataMap : dataList){
				Map<String, Object> map = new HashMap<String, Object>();
				
				/**校验医疗器械分类编码************begin*****************/
				rowList = dataMap.get("医疗器械分类编码");
				if(rowList == null || rowList.get(1) == null || "".equals(rowList.get(1))){
					retMap.put("state", "false");
					retMap.put("warn", rowList.get(0) + "，医疗器械分类编码为空！");
					retMap.put("row_cell", rowList.get(0));
					return retMap;
				}
				instru_type_code = rowList.get(1);
				if(MatInstruTypeMap.containsKey(instru_type_code)){
					retMap.put("state", "false");
					retMap.put("warn", rowList.get(0) + "，医疗器械分类编码已存在！");
					retMap.put("row_cell", rowList.get(0));
					return retMap;
				}

				//判断是否符合编码规则
				s_code = new StringBuffer();//上级编码
				super_code_temp = "0";
				type_level =0;//级次 
				begin_index = 0;
				end_index = 0;
				length = 0;
				strLength = instru_type_code.length();
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
					super_code_temp = instru_type_code.substring(begin_index, end_index);
					
					if(super_code_temp.length() == length){
						type_level += 1;
					}else{
						retMap.put("state", "false");
						retMap.put("warn", "编码位数不符合编码规则");
						retMap.put("row_cell", rowList.get(0));
						return retMap;
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
				if(!"0".equals(super_code)){
			        //判断上级编码是否存在
					if(!MatInstruTypeMap.containsKey(super_code)){
						retMap.put("state", "false");
						retMap.put("warn", "上级编码不存在");
						retMap.put("row_cell", rowList.get(0));
						return retMap;
					}
					
					//记录上级编码以便修改为非末级
					typeUpdateList.add(MatInstruTypeMap.get(super_code));
				}

				map.put("instru_type_code", instru_type_code);
				map.put("super_code", super_code);
				map.put("type_level", type_level);
				map.put("is_last", 1);
				/**校验医疗器械分类编码************end*******************/
				
				/**校验医疗器械分类名称************begin*****************/
				rowList = dataMap.get("医疗器械分类名称");
				if(rowList == null || rowList.get(1) == null || "".equals(rowList.get(1))){
					retMap.put("state", "false");
					retMap.put("warn", rowList.get(0) + "，医疗器械分类名称为空！");
					retMap.put("row_cell", rowList.get(0));
					return retMap;
				}
				instru_type_name = rowList.get(1);
				if(MatInstruTypeMap.containsKey(instru_type_name)){
					retMap.put("state", "false");
					retMap.put("warn", rowList.get(0) + "，医疗器械分类名称已存在！");
					retMap.put("row_cell", rowList.get(0));
					return retMap;
				}
				
				map.put("instru_type_name", instru_type_name);
				map.put("spell_code", StringTool.toPinyinShouZiMu(instru_type_name));
				map.put("wbx_code", StringTool.toWuBi(instru_type_name));
				/**校验医疗器械分类名称************end*******************/

				/**校验是否停用**********begin*****************/
				rowList = dataMap.get("是否停用");
				if(rowList == null || rowList.get(1) == null || "".equals(rowList.get(1))){
					map.put("is_stop", 0);
				}else{
					if(!yesOrNoMap.containsKey(rowList.get(1))){
						retMap.put("state", "false");
						retMap.put("warn", rowList.get(0) + "，是否停用不存在！");
						retMap.put("row_cell", rowList.get(0));
						return retMap;
					}
					map.put("is_stop", yesOrNoMap.get(rowList.get(1)));
				}
				/**校验是否停用**********end*******************/

				//必填项
				map.put("group_id", group_id);
				map.put("hos_id", hos_id);
				map.put("copy_code", copy_code);
				map.put("is_budg", 0);
				instru_type_id = String.valueOf(UUIDLong.absStringUUID());
				map.put("instru_type_id", instru_type_id);
				map.put("is_stop", 0);
				//增加新记录
				typeAddList.add(map);
				//记录该类别
				MatInstruTypeMap.put(instru_type_code, instru_type_id);
				MatInstruTypeMap.put(instru_type_name, instru_type_id);
			}
			
			//批量添加
			MatInstruTypeMapper.addBatchMatInstruType(typeAddList);
			//修改已有的上级编码为非末级
			if(typeUpdateList.size() > 0){
				MatInstruTypeMapper.updateMatInstruTypeIsLastForImport(seachMap, typeUpdateList);
			}
			
			retMap.put("msg", "操作成功！");
		}catch (Exception e) {
			logger.error(e.getMessage(), e);
			
			throw new SysException("操作失败");
		}
		
		return retMap;
	}
}
