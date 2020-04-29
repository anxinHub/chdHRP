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

import com.chd.base.MyConfig;
import com.chd.base.SessionManager;
import com.chd.base.SysPage;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.base.util.DateUtil;
import com.chd.base.util.NumberUtil;
import com.chd.base.util.SpreadTableJSUtil;
import com.chd.base.util.StringTool;
import com.chd.hrp.mat.dao.base.MatCommonMapper;
import com.chd.hrp.mat.dao.info.basic.MatTypeDictMapper;
import com.chd.hrp.mat.dao.info.basic.MatTypeMapper;
import com.chd.hrp.mat.entity.MatType;
import com.chd.hrp.mat.service.info.basic.MatTypeService;
import com.chd.hrp.sys.dao.notGeneral.SysFunUtilMapper;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Description:
 * 04103 物资分类字典
 * @Table:
 * MAT_TYPE
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 


@Service("matTypeService")
public class MatTypeServiceImpl implements MatTypeService {

	private static Logger logger = Logger.getLogger(MatTypeServiceImpl.class);
	//引入DAO操作
	@Resource(name = "matTypeMapper")
	private final MatTypeMapper matTypeMapper = null;    
	@Resource(name = "matCommonMapper")
	private final MatCommonMapper matCommonMapper = null;    
	@Resource(name = "matTypeDictMapper")
	private final MatTypeDictMapper matTypeDictMapper = null;    
	@Resource(name = "sysFunUtilMapper")
	private final SysFunUtilMapper sysFunUtilMapper = null;    
	
	@Override
	public String queryMatTypeByTree(Map<String, Object> entityMap)
			throws DataAccessException {
		StringBuilder jsonResult = new StringBuilder();
		//jsonResult.append("{Rows:[");
		jsonResult.append("[");
		List<MatType> list = matTypeMapper.queryMatType(entityMap);
		if (list.size()>0) {
			int row = 0;
			for (MatType mt : list) {
				if (row == 0) {
					jsonResult.append("{");
				} else {
					jsonResult.append(",{");
				}
				row++;
				jsonResult.append("\"pcode\":\""+mt.getSuper_code()+"\"," + 
						"\"code\":\"" + mt.getMat_type_code()+ "\"," + 
						"\"id\":\"" + mt.getMat_type_id()+ "\"," + 
						"\"text\":" + "\"" + mt.getMat_type_code()+ " " + mt.getMat_type_name() + "\"" 
					);
				jsonResult.append("}");
			}
		}
		//jsonResult.append("]}");
		jsonResult.append("]");
		return jsonResult.toString();
	}
	
	/**
	 * @Description 
	 * 添加04103 物资分类字典<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addMatType(Map<String,Object> entityMap)throws DataAccessException{
		
		entityMap.remove("super_code");
		entityMap.remove("spell_code");
		entityMap.remove("wbx_code");
		//判断名称编码是否重复
		List<Map<String, Object>> mtlist = queryMatTypeByCodeName(entityMap);
		if (mtlist.size()>0) {
			for(Map<String, Object> matType : mtlist ){
				if(entityMap.get("mat_type_code").equals(matType.get("code"))){
					return "{\"error\":\"编码：" + matType.get("code").toString() + "已存在.\"}";
				}
				//if(entityMap.get("mat_type_name").equals(matType.get("name"))){
					//return "{\"error\":\"名称：" + matType.get("name").toString() + "已存在.\"}";
				//}
			}
		}
		
		//判断是否符合编码规则
		Map<String, Object> comMap = new HashMap<String, Object>();
		comMap.put("group_id", entityMap.get("group_id"));
		comMap.put("hos_id", entityMap.get("hos_id"));
		comMap.put("copy_code", entityMap.get("copy_code"));
		comMap.put("proj_code", "mat_type".toUpperCase());
		comMap.put("mod_code", "04");
        String rules = matCommonMapper.getMatHosRules(comMap);//获取编码规则2-2-2....
        if(rules.length() <= 0){
        	return "{\"error\":\"请维护物资类别编码规则！\"}";
        }
        String mat_type_code = (String)entityMap.get("mat_type_code");//类别编码
        StringBuffer s_code = new StringBuffer();//上级编码
        String super_code_temp = "0";
        int type_level =0;//级次 
        int begin_index = 0;
        int end_index = 0;
        int length = 0;
        String[] ruless  = rules.split("-");
        int strLength = mat_type_code.length();
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
        	super_code_temp = mat_type_code.substring(begin_index, end_index);
        	
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
		entityMap.put("spell_code", StringTool.toPinyinShouZiMu(entityMap.get("mat_type_name").toString()));
		entityMap.put("wbx_code", StringTool.toWuBi(entityMap.get("mat_type_name").toString()));

		Map<String, Object> utilMap = new HashMap<String, Object>();
		if(!"0".equals(super_code)){
	        //判断上级编码是否存在
			utilMap.put("group_id", entityMap.get("group_id"));
			utilMap.put("hos_id", entityMap.get("hos_id"));
			utilMap.put("copy_code", entityMap.get("copy_code"));
			utilMap.put("mat_type_code", super_code.toString());
			utilMap.put("type_level", type_level-1);
	        List<MatType> list = matTypeMapper.queryMatTypeByCode(utilMap);
	        if(list.size() == 0){
	        	return "{\"error\":\"输入类别编码的上级类别编码（"+super_code+"）不存在，不允许添加，请添加上级类别后再操作！\"}";
	   	 	}
	        for(MatType matType : list ){
	            utilMap.put("mat_type_id", matType.getMat_type_id());
	        }
		}
        
        try {
            //新增物资类别
			matTypeMapper.addMatType(entityMap);
			
	        //新增物资类别变更
			entityMap.put("change_user", SessionManager.getUserId());
			entityMap.put("change_date", new Date());
			entityMap.put("change_note", "新增");
			matTypeDictMapper.addMatTypeDict(entityMap);
			if(!"0".equals(super_code)){
		        //更新上级类别is_last为否(0)
	            utilMap.put("is_last", "0");
				matTypeMapper.updateMatTypeIsLast(utilMap);
				matTypeDictMapper.updateMatTypeDictIsLast(utilMap);
			}
			return "{\"msg\":\"添加成功.\",\"state\":\"true\",\"mat_type_id\":\""+entityMap.get("mat_type_id")+"\"}";
		}
		catch (DataAccessException e) {
			logger.error(e.getMessage(), e);
			
			throw new SysException("{\"error\":\"添加失败\"}");
			//return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addMatType\"}";
		}
	}
	
	@Override
	public String impMatType(Map<String,Object> entityMap,Map<String,Object> utilMap)throws DataAccessException{
		
		 try {
	            //新增物资类别
				matTypeMapper.addMatType(entityMap);
				
		        //新增物资类别变更
				entityMap.put("change_user", SessionManager.getUserId());
				entityMap.put("change_date", new Date());
				entityMap.put("change_note", "新增");
				matTypeDictMapper.addMatTypeDict(entityMap);
				if(!"0".equals(entityMap.get("entityMap"))){
			        //更新上级类别is_last为否(0)
		            utilMap.put("is_last", "0");
					matTypeMapper.updateMatTypeIsLast(utilMap);
					matTypeDictMapper.updateMatTypeDictIsLast(utilMap);
				}
				return "{\"msg\":\"添加成功.\",\"state\":\"true\",\"mat_type_id\":\""+entityMap.get("mat_type_id")+"\"}";
			}
			catch (DataAccessException e) {
				logger.error(e.getMessage(), e);
				
				throw new SysException("{\"error\":\"添加失败\"}");
				//return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addMatType\"}";
			}
		
	}
	
	/**
	 * @Description 
	 * 批量添加04103 物资分类字典<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addBatchMatType(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			matTypeMapper.addBatchMatType(entityList);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";
		}
		catch (DataAccessException e) {
			logger.error(e.getMessage(), e);

			throw new SysException("{\"error\":\"添加失败\"}");
			//return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addBatchMatType\"}";
		}
	}
	
		/**
	 * @Description 
	 * 更新04103 物资分类字典<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateMatType(Map<String,Object> entityMap)throws DataAccessException{
		
		try {
			matTypeMapper.updateMatType(entityMap);
			return "{\"msg\":\"更新成功.\",\"state\":\"true\",\"mat_type_id\":\""+entityMap.get("mat_type_id")+"\"}";
		}
		catch (DataAccessException e) {
			logger.error(e.getMessage(), e);

			throw new SysException("{\"error\":\"更新失败\"}");
			//return "{\"error\":\"更新失败 数据库异常 请联系管理员! 方法 updateMatType\"}";
		}	
	}
	/**
	 * @Description 
	 * 批量更新04103 物资分类字典<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateBatchMatType(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
		  matTypeMapper.updateBatchMatType(entityList);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (DataAccessException e) {

			logger.error(e.getMessage(), e);

			throw new SysException("{\"error\":\"更新失败\"}");
			//return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 updateBatchMatType\"}";

		}	
		
	}
	/**
	 * @Description 
	 * 删除04103 物资分类字典<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
    public String deleteMatType(Map<String, Object> entityMap) throws DataAccessException {
    
		try {
			//判断是否被使用
			String reStr="";
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("dict_code", "mat_type".toUpperCase());
			map.put("group_id", entityMap.get("group_id"));
			map.put("hos_id", entityMap.get("hos_id"));
			map.put("copy_code", entityMap.get("copy_code"));
			map.put("dict_id_str", entityMap.get("mat_type_id"));
			map.put("acc_year", "");
			map.put("p_flag", "1");
			sysFunUtilMapper.querySysDictDelCheck(map);
			
			
			if(map.get("reNote")!=null) {
				reStr+=map.get("reNote");
			}
			
			//System.out.println("222222222222222"+reStr);
			
			
			if(reStr!=null && !reStr.equals("")){
				return "{\"error\":\"删除失败，选择的物资类别被以下业务使用：【"+reStr.substring(0,reStr.length()-1)+"】。\",\"state\":\"false\"}";
			}
			
			//判断是否为非末级
		
			//MatType mt = matTypeMapper.queryMatTypeById(entityMap);
			
			
			//if(mt.getIs_last() == 0){
				
				
				//return "{\"error\":\"删除失败，选择的物资类别为非末级！\",\"state\":\"false\"}";
				
			//}
			
			//删除物资类别
			
			
			matTypeMapper.deleteMatType(entityMap);
			
			Map<String, Object> dictMap = new HashMap<String, Object>();
			dictMap.put("group_id", entityMap.get("group_id"));
			dictMap.put("hos_id", entityMap.get("hos_id"));
			dictMap.put("copy_code", entityMap.get("copy_code"));
			//修改上级编码非末级标记
			dictMap.put("mat_type_id", entityMap.get("mat_type_id"));
			matTypeMapper.updateMatTypeSuperIsLastByIds(dictMap);
			matTypeDictMapper.updateMatTypeDictSuperIsLastByIds(dictMap);
			
			//新增变更表删除记录
			entityMap.put("change_user", SessionManager.getUserId());
			entityMap.put("change_date", new Date());
			entityMap.put("change_note", "删除");
			matTypeDictMapper.addMatTypeDictForDelete(entityMap);
			
			//停用所有变更记录
			matTypeDictMapper.updateMatTypeDictIsStop(entityMap);
		}catch (DataAccessException e) {
			logger.error(e.getMessage(), e);
			
			throw new SysException("{\"error\":\"删除失败\"}");
			//return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 deleteMatType\"}";
		}	
		return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
  }
    
	/**
	 * @Description 
	 * 批量删除04103 物资分类字典<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String deleteBatchMatType(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			matTypeMapper.deleteBatchMatType(entityList);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		}catch (DataAccessException e) {
			logger.error(e.getMessage(), e);

			throw new SysException("{\"error\":\"删除失败\"}");
			//return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 deleteBatchMatType\"}";
		}	
	}
	
	/**
	 * @Description 
	 * 添加04103 物资分类字典<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String saveMatType(Map<String,Object> entityMap)throws DataAccessException{
				
		if ("".equals(entityMap.get("mat_type_id"))) {
			return addMatType(entityMap);
		}else{
			return updateMatType(entityMap);
		}		
	}
	/**
	 * @Description 
	 * 查询结果集04103 物资分类字典<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String queryMatType(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<MatType> list = matTypeMapper.queryMatType(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<MatType> list = matTypeMapper.queryMatType(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
	}
	
	/**
	 * @Description 
	 * 获取对象04103 物资分类字典<BR> 
	 * @param  entityMap<BR>
	 *  参数为主键
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public MatType queryMatTypeById(Map<String,Object> entityMap)throws DataAccessException{
		
		return matTypeMapper.queryMatTypeById(entityMap);
	}
	
	/**
	 * @Description 
	 * 获取04103 物资分类字典<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return MatType
	 * @throws DataAccessException
	*/
	@Override
	public MatType queryMatTypeByUniqueness(Map<String,Object> entityMap)throws DataAccessException{
		return matTypeMapper.queryMatTypeByUniqueness(entityMap);
	}
	
	/**
	 * 根据code和name查询出所有符合要求的数据
	 * @param entityMap
	 * @return Map
	 */
	@Override
	public List<Map<String, Object>> queryMatTypeByCodeName(Map<String,Object> entityMap){
		Map<String,Object> utilMap=new HashMap<String,Object>();
		utilMap.put("group_id", entityMap.get("group_id"));
		utilMap.put("hos_id", entityMap.get("hos_id"));
		utilMap.put("copy_code", entityMap.get("copy_code"));
		utilMap.put("table_name", "mat_type");
		if(entityMap.get("mat_type_id") != null && !"".equals(entityMap.get("mat_type_id").toString())){
			utilMap.put("c_id", "mat_type_id");
			utilMap.put("c_id_value", entityMap.get("mat_type_id"));
		}
		utilMap.put("c_code", "mat_type_code");
		utilMap.put("c_code_value", entityMap.get("mat_type_code"));
		utilMap.put("c_name", "mat_type_name");
		utilMap.put("c_name_value", entityMap.get("mat_type_name"));
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
			List<Map<String, Object>> matTypeList = matTypeMapper.queryMatTypeListForImport(seachMap);
			Map<String, String> matTypeMap = new HashMap<String, String>();
			for(Map<String, Object> matType : matTypeList){
				matTypeMap.put(matType.get("MAT_TYPE_CODE").toString(), matType.get("MAT_TYPE_ID").toString());
				matTypeMap.put(matType.get("MAT_TYPE_NAME").toString(), matType.get("MAT_TYPE_ID").toString());
			}
			
			//获取物资财务分类
			List<Map<String, Object>> matFimTypeList = matTypeMapper.queryMatFimTypeListForImport(seachMap);
			Map<String, String> matFimTypeMap = new HashMap<String, String>();
			for(Map<String, Object> matFimType : matFimTypeList){
				matFimTypeMap.put(matFimType.get("FIM_TYPE_CODE").toString(), matFimType.get("FIM_TYPE_CODE").toString());
				matFimTypeMap.put(matFimType.get("FIM_TYPE_NAME").toString(), matFimType.get("FIM_TYPE_CODE").toString());
			}
			
			//是否
			Map<String, String> yesOrNoMap = new HashMap<String, String>();
			yesOrNoMap.put("1", "1");
			yesOrNoMap.put("0", "0");
			yesOrNoMap.put("是", "1");
			yesOrNoMap.put("否", "0");
			
			//获取物资类别编码规则
			seachMap.put("proj_code", "mat_type".toUpperCase());
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
			String mat_type_code;
			String mat_type_name;
			String mat_type_id;
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
				
				/**校验物资类别编码************begin*****************/
				rowList = dataMap.get("物资类别编码");
				if(rowList == null || rowList.get(1) == null || "".equals(rowList.get(1))){
					retMap.put("state", "false");
					retMap.put("warn", rowList.get(0) + "，物资类别编码为空！");
					retMap.put("row_cell", rowList.get(0));
					return retMap;
				}
				mat_type_code = rowList.get(1);
				if(matTypeMap.containsKey(mat_type_code)){
					retMap.put("state", "false");
					retMap.put("warn", rowList.get(0) + "，物资类别编码已存在！");
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
				strLength = mat_type_code.length();
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
					if(!matTypeMap.containsKey(super_code)){
						retMap.put("state", "false");
						retMap.put("warn", "上级编码不存在");
						retMap.put("row_cell", rowList.get(0));
						return retMap;
					}
					
					//记录上级编码以便修改为非末级
					typeUpdateList.add(matTypeMap.get(super_code));
				}

				map.put("mat_type_code", mat_type_code);
				map.put("super_code", super_code);
				map.put("type_level", type_level);
				map.put("is_last", 1);
				/**校验物资类别编码************end*******************/
				
				/**校验物资类别名称************begin*****************/
				rowList = dataMap.get("物资类别名称");
				if(rowList == null || rowList.get(1) == null || "".equals(rowList.get(1))){
					retMap.put("state", "false");
					retMap.put("warn", rowList.get(0) + "，物资类别名称为空！");
					retMap.put("row_cell", rowList.get(0));
					return retMap;
				}
				mat_type_name = rowList.get(1);
				if(matTypeMap.containsKey(mat_type_name)){
					retMap.put("state", "false");
					retMap.put("warn", rowList.get(0) + "，物资类别名称已存在！");
					retMap.put("row_cell", rowList.get(0));
					return retMap;
				}
				
				map.put("mat_type_name", mat_type_name);
				map.put("spell_code", StringTool.toPinyinShouZiMu(mat_type_name));
				map.put("wbx_code", StringTool.toWuBi(mat_type_name));
				/**校验物资类别名称************end*******************/

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

				/**校验是否自动有效期**********begin*****************/
				rowList = dataMap.get("是否自动有效期");
				if(rowList == null || rowList.get(1) == null || "".equals(rowList.get(1))){
					map.put("is_auto_exp", 0);
				}else{
					if(!yesOrNoMap.containsKey(rowList.get(1))){
						retMap.put("state", "false");
						retMap.put("warn", rowList.get(0) + "，是否自动有效期不存在！");
						retMap.put("row_cell", rowList.get(0));
						return retMap;
					}
					map.put("is_auto_exp", yesOrNoMap.get(rowList.get(1)));
				}
				/**校验是否自动有效期**********end*******************/

				/**校验物资财务分类**************begin*****************/
				rowList = dataMap.get("物资财务分类");
				if(rowList == null || rowList.get(1) == null || "".equals(rowList.get(1))){
					map.put("fim_type_code", null);
				}else{
					if(!matFimTypeMap.containsKey(rowList.get(1))){
						retMap.put("state", "false");
						retMap.put("warn", rowList.get(0) + "，物资财务分类不存在！");
						retMap.put("row_cell", rowList.get(0));
						return retMap;
					}
					map.put("fim_type_code", matFimTypeMap.get(rowList.get(1)));
				}
				/**校验物资财务分类**********end*******************/
				
				/**校验备注******begin*****************/
				rowList = dataMap.get("备注");
				if(rowList == null || rowList.get(1) == null || "".equals(rowList.get(1))){
					map.put("note", null);
				}else{
					map.put("note", rowList.get(1));
				}
				/**校验备注******end*******************/
				
				//必填项
				map.put("group_id", group_id);
				map.put("hos_id", hos_id);
				map.put("copy_code", copy_code);
				map.put("is_budg", 0);
				mat_type_id = String.valueOf(matTypeMapper.queryMatTypeSeq());
				map.put("mat_type_id", mat_type_id);
				//变更表
				map.put("change_user", SessionManager.getUserId());
				map.put("change_date", new Date());
				map.put("change_note", "新增");
				map.put("is_stop", 0);
				//增加新记录
				typeAddList.add(map);
				//记录该类别
				matTypeMap.put(mat_type_code, mat_type_id);
				matTypeMap.put(mat_type_name, mat_type_id);
			}
			
			//批量添加
			matTypeMapper.addBatchMatType(typeAddList);
			matTypeDictMapper.addBatchMatTypeDict(typeAddList);
			//修改已有的上级编码为非末级
			if(typeUpdateList.size() > 0){
				matTypeMapper.updateMatTypeIsLastForImport(seachMap, typeUpdateList);
			}
			
			retMap.put("msg", "操作成功！");
		}catch (Exception e) {
			logger.error(e.getMessage(), e);
			
			throw new SysException("操作失败");
		}
		
		return retMap;
	}
}
