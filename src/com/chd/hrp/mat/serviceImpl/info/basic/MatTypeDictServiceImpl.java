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
import com.chd.base.util.StringTool;
import com.chd.hrp.mat.dao.base.MatCommonMapper;
import com.chd.hrp.mat.dao.info.basic.MatTypeDictMapper;
import com.chd.hrp.mat.dao.info.basic.MatTypeMapper;
import com.chd.hrp.mat.entity.MatType;
import com.chd.hrp.mat.entity.MatTypeDict;
import com.chd.hrp.mat.service.info.basic.MatTypeDictService;
import com.chd.hrp.mat.service.info.basic.MatTypeService;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Description:
 * 04104 物资分类变更表
 * @Table:
 * MAT_TYPE_DICT
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 


@Service("matTypeDictService")
public class MatTypeDictServiceImpl implements MatTypeDictService {

	private static Logger logger = Logger.getLogger(MatTypeDictServiceImpl.class);
	//引入DAO操作
	@Resource(name = "matTypeDictMapper")
	private final MatTypeDictMapper matTypeDictMapper = null;
	@Resource(name = "matTypeMapper")
	private final MatTypeMapper matTypeMapper = null;
	@Resource(name = "matCommonMapper")
	private final MatCommonMapper matCommonMapper = null;
	@Resource(name = "matTypeService")
	private final MatTypeService matTypeService = null;
	
	@Override
	public String queryMatTypeDictByTree(Map<String, Object> entityMap)
			throws DataAccessException {
		StringBuilder jsonResult = new StringBuilder();
		jsonResult.append("[");
		List<MatTypeDict> list = matTypeDictMapper.queryMatTypeDictTwo(entityMap);
		if (list.size()>0) {
			int row = 0;
			for (MatTypeDict mt : list) {
				if (row == 0) {
					jsonResult.append("{");
				} else {
					jsonResult.append(",{");
				}
				row++;
				jsonResult.append("\"pcode\":\""+mt.getSuper_code()+"\"," + 
						"\"code\":\"" + mt.getMat_type_code()+ "\"," + 
						"\"id\":\"" + mt.getMat_type_id()+ "\"," + 
						"\"text\":" + "\"" + mt.getMat_type_code()+ " " + mt.getMat_type_name() + "\"," + 
						"\"no\":" + "\"" + mt.getMat_type_no() + "\"" 
					);
				jsonResult.append("}");
			}
		}
		jsonResult.append("]");
		return jsonResult.toString();
	}
	
	/**
	 * @Description 
	 * 添加04104 物资分类变更表<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String saveMatTypeDict(Map<String,Object> entityMap)throws DataAccessException{
		entityMap.remove("super_code");
		entityMap.remove("spell_code");
		entityMap.remove("wbz_code");
		//判断名称编码是否重复
		List<Map<String, Object>> mtlist = matTypeService.queryMatTypeByCodeName(entityMap);
		if (mtlist.size()>0) {
			for(Map<String, Object> matType : mtlist ){
				if(entityMap.get("mat_type_code").equals(matType.get("code"))){
					return "{\"error\":\"编码：" + matType.get("code").toString() + "已存在.\"}";
				}
				//if(entityMap.get("mat_type_name").equals(matType.get("name"))){
				//	return "{\"error\":\"名称：" + matType.get("name").toString() + "已存在.\"}";
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
        		return "{\"error\":\"变更失败 编码位数不符合编码规则 请重新输入！\"}";
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
	        	return "{\"error\":\"输入类别编码的上级类别编码（"+super_code+"）不存在，不允许变更，请添加上级类别后再操作！\"}";
	   	 	}
	        for(MatType matType : list ){
	            utilMap.put("mat_type_id", matType.getMat_type_id());
	        }
		}
		
		Map<String, Object> dictMap = new HashMap<String, Object>();
		dictMap.put("group_id", entityMap.get("group_id"));
		dictMap.put("hos_id", entityMap.get("hos_id"));
		dictMap.put("copy_code", entityMap.get("copy_code"));
		dictMap.put("c_max", "mat_type_no");
		dictMap.put("table_name", "mat_type_dict");
		dictMap.put("c_name", "mat_type_id");
		dictMap.put("c_value", entityMap.get("mat_type_id"));
		String max_no = matCommonMapper.getMatMaxNo(dictMap);
		
		try {
	        //更新物资类别
			matTypeMapper.updateMatTypeByDict(entityMap);
			if(!"0".equals(super_code)){
		        //更新上级类别is_last为否(0)
	            utilMap.put("is_last", "0");
				matTypeMapper.updateMatTypeIsLast(utilMap);
				matTypeDictMapper.updateMatTypeDictIsLast(utilMap);
			}
			
			//更新未变更前的上级科目末级标志
			Map<String, Object> isLastMap = new HashMap<String, Object>();
			isLastMap.put("group_id", entityMap.get("group_id"));
			isLastMap.put("hos_id", entityMap.get("hos_id"));
			isLastMap.put("copy_code", entityMap.get("copy_code"));
			isLastMap.put("mat_type_id", entityMap.get("mat_type_id"));
			matTypeMapper.updateMatTypeSuperIsLastByIds(isLastMap);
			matTypeDictMapper.updateMatTypeDictSuperIsLastByIds(isLastMap);
			
			//变更信息
			entityMap.put("max_no", max_no);
			entityMap.put("change_user", SessionManager.getUserId());
			entityMap.put("change_date", new Date());
			entityMap.put("change_note", "变更");
			
			if("1".equals(entityMap.get("is_save_change"))){
				//停用上一次的变更记录
				matTypeDictMapper.updateMatTypeDictIsStop(entityMap);
				
	            //新增物资类别变更
				matTypeDictMapper.addMatTypeDict(entityMap);
			}else{
				//更新变更记录数据为现有数据
				matTypeDictMapper.updateMatTypeDict(entityMap);
			}
			return "{\"msg\":\"变更成功.\",\"state\":\"true\",\"mat_type_id\":\""+entityMap.get("mat_type_id")+"\"}";
		}
		catch (DataAccessException e) {
			logger.error(e.getMessage(), e);
			
			throw new SysException("{\"error\":\"操作失败\"}");
			//return "{\"error\":\"变更失败 数据库异常 请联系管理员! 方法 saveMatTypeDict\"}";
		}
	}
	/**
	 * @Description 
	 * 查询结果集04104 物资分类变更表<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String queryMatTypeDict(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		sysPage = (SysPage) entityMap.get("sysPage");
		if (sysPage.getTotal()==-1){
			List<MatTypeDict> list = matTypeDictMapper.queryMatTypeDict(entityMap);
			
			return ChdJson.toJson(list);
		}else{
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			List<MatTypeDict> list = matTypeDictMapper.queryMatTypeDict(entityMap, rowBounds);
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
		}
	}
	
	/**
	 * @Description 
	 * 获取对象04104 物资分类变更表<BR> 
	 * @param  entityMap<BR>
	 *  参数为主键
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public MatTypeDict queryMatTypeDictById(Map<String,Object> entityMap)throws DataAccessException{
		return matTypeDictMapper.queryMatTypeDictById(entityMap);
	}
	
	/**
	 * @Description 
	 * 获取04104 物资分类变更表<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return MatTypeDict
	 * @throws DataAccessException
	*/
	@Override
	public MatTypeDict queryMatTypeDictByUniqueness(Map<String,Object> entityMap)throws DataAccessException{
		return matTypeDictMapper.queryMatTypeDictByUniqueness(entityMap);
	}
	
}
