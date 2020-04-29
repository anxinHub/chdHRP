/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.budg.serviceImpl.base.budginv;

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
import com.chd.base.util.StringTool;
import com.chd.hrp.budg.dao.base.budginv.BudgTypeDictMapper;
import com.chd.hrp.budg.dao.base.budginv.BudgTypeMapper;
import com.chd.hrp.budg.entity.BudgType;
import com.chd.hrp.budg.service.base.budginv.BudgTypeService;
import com.chd.hrp.mat.dao.base.MatCommonMapper;
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
 


@Service("budgTypeService")
public class BudgTypeServiceImpl implements BudgTypeService {

	private static Logger logger = Logger.getLogger(BudgTypeServiceImpl.class);
	//引入DAO操作
	@Resource(name = "budgTypeMapper")
	private final BudgTypeMapper budgTypeMapper = null;    
	@Resource(name = "matCommonMapper")
	private final MatCommonMapper matCommonMapper = null;    
	@Resource(name = "budgTypeDictMapper")
	private final BudgTypeDictMapper budgTypeDictMapper = null;    
	@Resource(name = "sysFunUtilMapper")
	private final SysFunUtilMapper sysFunUtilMapper = null;    
	
	@Override
	public String queryBudgTypeByTree(Map<String, Object> entityMap) throws DataAccessException {
		StringBuilder jsonResult = new StringBuilder();
		jsonResult.append("{Rows:[");
		List<BudgType> list = budgTypeMapper.queryBudgType(entityMap);
		if (list.size()>0) {
			int row = 0;
			for (BudgType mt : list) {
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
		jsonResult.append("]}");
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
	public String addBudgType(Map<String,Object> entityMap)throws DataAccessException{
		
		entityMap.remove("super_code");
		entityMap.remove("spell_code");
		entityMap.remove("wbx_code");
		//判断名称编码是否重复
		List<Map<String, Object>> mtlist = queryBudgTypeByCodeName(entityMap);
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
	        List<BudgType> list = budgTypeMapper.queryBudgTypeByCode(utilMap);
	        if(list.size() == 0){
	        	return "{\"error\":\"输入类别编码的上级类别编码（"+super_code+"）不存在，不允许添加，请添加上级类别后再操作！\"}";
	   	 	}
	        for(BudgType matType : list ){
	            utilMap.put("mat_type_id", matType.getMat_type_id());
	        }
		}
        
        try {
            //新增物资类别
			budgTypeMapper.addBudgType(entityMap);
			
	        //新增物资类别变更
			entityMap.put("change_user", SessionManager.getUserId());
			entityMap.put("change_date", new Date());
			entityMap.put("change_note", "新增");
			budgTypeDictMapper.addBudgTypeDict(entityMap);
			if(!"0".equals(super_code)){
		        //更新上级类别is_last为否(0)
	            utilMap.put("is_last", "0");
				budgTypeMapper.updateBudgTypeIsLast(utilMap);
				budgTypeDictMapper.updateBudgTypeDictIsLast(utilMap);
			}
			return "{\"msg\":\"添加成功.\",\"state\":\"true\",\"mat_type_id\":\""+entityMap.get("mat_type_id")+"\"}";
		}
		catch (DataAccessException e) {
			logger.error(e.getMessage(), e);
			
			throw new SysException("{\"error\":\"添加失败\"}");
			//return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addMatType\"}";
		}
	}
	
	public String impBudgType(Map<String,Object> entityMap,Map<String,Object> utilMap)throws DataAccessException{
		
		 try {
	            //新增物资类别
				budgTypeMapper.addBudgType(entityMap);
				
		        //新增物资类别变更
				entityMap.put("change_user", SessionManager.getUserId());
				entityMap.put("change_date", new Date());
				entityMap.put("change_note", "新增");
				budgTypeDictMapper.addBudgTypeDict(entityMap);
				if(!"0".equals(entityMap.get("entityMap"))){
			        //更新上级类别is_last为否(0)
		            utilMap.put("is_last", "0");
					budgTypeMapper.updateBudgTypeIsLast(utilMap);
					budgTypeDictMapper.updateBudgTypeDictIsLast(utilMap);
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
	public String addBatchBudgType(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			budgTypeMapper.addBatchBudgType(entityList);
			
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
	public String updateBudgType(Map<String,Object> entityMap)throws DataAccessException{
		
		try {
			budgTypeMapper.updateBudgType(entityMap);
			
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
	public String updateBatchBudgType(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
		  budgTypeMapper.updateBatchBudgType(entityList);
			
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
    public String deleteBudgType(Map<String, Object> entityMap) throws DataAccessException {
    
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
		
			//MatType mt = budgTypeMapper.queryMatTypeById(entityMap);
			
			
			//if(mt.getIs_last() == 0){
				
				
				//return "{\"error\":\"删除失败，选择的物资类别为非末级！\",\"state\":\"false\"}";
				
			//}
			
			//删除物资类别
			
			
			budgTypeMapper.deleteBudgType(entityMap);
			
			Map<String, Object> dictMap = new HashMap<String, Object>();
			dictMap.put("group_id", entityMap.get("group_id"));
			dictMap.put("hos_id", entityMap.get("hos_id"));
			dictMap.put("copy_code", entityMap.get("copy_code"));
			//修改上级编码非末级标记
			dictMap.put("mat_type_id", entityMap.get("mat_type_id"));
			budgTypeMapper.updateBudgTypeSuperIsLastByIds(dictMap);
			budgTypeDictMapper.updateBudgTypeDictSuperIsLastByIds(dictMap);
			
			//新增变更表删除记录
			entityMap.put("change_user", SessionManager.getUserId());
			entityMap.put("change_date", new Date());
			entityMap.put("change_note", "删除");
			budgTypeDictMapper.addBudgTypeDictForDelete(entityMap);
			
			//停用所有变更记录
			budgTypeDictMapper.updateBudgTypeDictIsStop(entityMap);
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
	public String deleteBatchBudgType(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			budgTypeMapper.deleteBatchBudgType(entityList);
			
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
	public String saveBudgType(Map<String,Object> entityMap)throws DataAccessException{
				
		if ("".equals(entityMap.get("mat_type_id"))) {
			return addBudgType(entityMap);
		}else{
			return updateBudgType(entityMap);
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
	public String queryBudgType(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<BudgType> list = budgTypeMapper.queryBudgType(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<BudgType> list = budgTypeMapper.queryBudgType(entityMap, rowBounds);
			
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
	public BudgType queryBudgTypeById(Map<String,Object> entityMap)throws DataAccessException{
		
		return budgTypeMapper.queryBudgTypeById(entityMap);
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
	public BudgType queryBudgTypeByUniqueness(Map<String,Object> entityMap)throws DataAccessException{
		return budgTypeMapper.queryBudgTypeByUniqueness(entityMap);
	}
	
	/**
	 * 根据code和name查询出所有符合要求的数据
	 * @param entityMap
	 * @return Map
	 */
	@Override
	public List<Map<String, Object>> queryBudgTypeByCodeName(Map<String,Object> entityMap){
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

}
