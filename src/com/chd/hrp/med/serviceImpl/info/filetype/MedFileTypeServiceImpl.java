package com.chd.hrp.med.serviceImpl.info.filetype;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.chd.base.SysPage;
import com.chd.base.util.ChdJson;
import com.chd.base.util.StringTool;
import com.chd.hrp.med.dao.info.filetype.MedFileTypeMapper;
import com.chd.hrp.med.entity.MedFileType;
import com.chd.hrp.med.service.info.filetype.MedFileTypeService;
import com.chd.hrp.med.serviceImpl.info.relaset.MedStoreMatchServiceImpl;
import com.chd.hrp.sys.dao.notGeneral.SysFunUtilMapper;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Description:
 * 08114 文档分类
 * @Table:
 * MED_FILE_TYPE
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
@Service("medFileTypeService")
public class MedFileTypeServiceImpl implements MedFileTypeService {
	
	private static Logger logger = Logger.getLogger(MedFileTypeServiceImpl.class);
	
	@Resource(name = "medFileTypeMapper")
	private MedFileTypeMapper medFileTypeMapper = null;
	
	@Resource(name = "sysFunUtilMapper")
	private SysFunUtilMapper sysFunUtilMapper = null;
	/**
	 * @Description 
	 * 文档分类<BR>添加
	 * @param  entityMap<BR>
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String add(Map<String, Object> entityMap) throws DataAccessException {
			
		if(entityMap.get("group_id") == null || "".equals(entityMap.get("group_id").toString())){
				
			return "{\"error\":\"添加失败 group_id不能为空! 错误编码 add\"}";
		}
			
		if(entityMap.get("hos_id") == null || "".equals(entityMap.get("hos_id").toString())){
				
			return "{\"error\":\"添加失败 hos_id不能为空! 错误编码 add\"}";
		}
			
		if(entityMap.get("copy_code") == null || "".equals(entityMap.get("copy_code").toString())){
				
			return "{\"error\":\"添加失败 copy_code不能为空! 错误编码 add\"}";
		}
			
		if(entityMap.get("type_code") == null || "".equals(entityMap.get("type_code").toString())){
				
			return "{\"error\":\"添加失败 type_code不能为空! 错误编码 add\"}";
		}
			
		if(entityMap.get("type_name") == null || "".equals(entityMap.get("type_name").toString())){
				
			return "{\"error\":\"添加失败 type_name不能为空! 错误编码 add\"}";
		}
			
		if(entityMap.get("is_stop") == null || "".equals(entityMap.get("is_stop").toString())){
				
			return "{\"error\":\"添加失败 is_stop不能为空! 错误编码 add\"}";
		}

		MedFileType medFileType = medFileTypeMapper.queryByCode(entityMap);//先查询编码是否已经存在
			
		if(medFileType != null){
				
			return "{\"error\":\"添加失败 文档类别编码已存在! 错误编码 add\"}";
		}
			
		entityMap.put("wbx_code", StringTool.toWuBi(entityMap.get("type_name").toString()));
			
		entityMap.put("spell_code",StringTool.toPinyinShouZiMu(entityMap.get("type_name").toString()));
			
		medFileTypeMapper.add(entityMap);
		
		return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";
			
	}

	@Override
	public String addBatch(List<Map<String, Object>> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	/**
	 * @Description 
	 * 文档分类<BR>修改
	 * @param  entityMap<BR>
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String update(Map<String, Object> entityMap) throws DataAccessException {
		
		if(entityMap.get("group_id") == null || "".equals(entityMap.get("group_id").toString())){
				
			return "{\"error\":\"修改失败 group_id不能为空! 错误编码 update\"}";
		}
			
		if(entityMap.get("hos_id") == null || "".equals(entityMap.get("hos_id").toString())){
				
			return "{\"error\":\"修改失败 hos_id不能为空! 错误编码 update\"}";
		}
			
		if(entityMap.get("copy_code") == null || "".equals(entityMap.get("copy_code").toString())){
				
			return "{\"error\":\"修改失败 copy_code不能为空! 错误编码 update\"}";
		}
			
		if(entityMap.get("type_code") == null || "".equals(entityMap.get("type_code").toString())){
				
			return "{\"error\":\"添加失败 type_code不能为空! 错误编码 add\"}";
		}
				
		if(entityMap.get("type_name") == null || "".equals(entityMap.get("type_name").toString())){
					
			return "{\"error\":\"添加失败 type_name不能为空! 错误编码 add\"}";
		}
				
		if(entityMap.get("is_stop") == null || "".equals(entityMap.get("is_stop").toString())){
					
			return "{\"error\":\"添加失败 is_stop不能为空! 错误编码 add\"}";
		}
			
		medFileTypeMapper.update(entityMap);
			
		return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";
			
	}

	@Override
	public String updateBatch(List<Map<String, Object>> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String delete(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}
	
	/**
	 * @Description 
	 * 文档分类<BR>批量删除
	 * @param  entityMap<BR>
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	@Transactional(rollbackFor=Exception.class)
	public String deleteBatch(List<Map<String, Object>> entityMap) throws DataAccessException {
		
		if(entityMap== null || entityMap.size() <= 0 ){
			
			return "{\"error\":\"删除失败 entityMap不能为空! 错误编码  deleteBatch\"}";
		}
		
		for(Map<String,Object> paramMap:entityMap){
			
			if(paramMap.get("group_id") == null || "".equals(paramMap.get("group_id").toString())){
				
				return "{\"error\":\"删除失败 group_id不能为空! 错误编码  deleteBatch\"}";
			}
				
			if(paramMap.get("hos_id") == null || "".equals(paramMap.get("hos_id").toString())){
					
				return "{\"error\":\"删除失败 hos_id不能为空! 错误编码  deleteBatch\"}";
			}
				
			if(paramMap.get("copy_code") == null || "".equals(paramMap.get("copy_code").toString())){
					
				return "{\"error\":\"删除失败 copy_code不能为空! 错误编码  deleteBatch\"}";
			}
				
			if(paramMap.get("type_code") == null || "".equals(paramMap.get("type_code").toString())){
					
				return "{\"error\":\"删除失败 type_code不能为空! 错误编码  deleteBatch\"}";
			}
		}
		
		String reStr="";
		
		String typeCode="";
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		map.put("dict_code", "MED_FILE_TYPE");
		
		map.put("group_id", entityMap.get(0).get("group_id"));
		
		map.put("hos_id", entityMap.get(0).get("hos_id"));
		
		map.put("copy_code", entityMap.get(0).get("copy_code"));
		
		map.put("acc_year", "");
		
		map.put("p_flag", "");
		
		for(Map<String, Object> mapVo : entityMap){
			
			if(typeCode.indexOf(mapVo.get("type_code").toString())==-1){
				
				typeCode+="'"+mapVo.get("type_code").toString()+"',";
			}
				
			if(typeCode.length()>2900){
				
				map.put("dict_id_str", typeCode.substring(0, typeCode.length()-1));
				//删除文档时，判断业务表是否使用
				//if(LoadSystemInfo.getHrpMap().get("hrpDatatype").toString().equalsIgnoreCase("oracle")){
				sysFunUtilMapper.querySysDictDelCheck(map);
				
				typeCode="";
					//}
				if(map.get("reNote")!=null)reStr+=map.get("reNote");
					
			}
				
		}
			
		if(!typeCode.equals("")){
				
			map.put("dict_id_str", typeCode.substring(0, typeCode.length()-1));
				
			sysFunUtilMapper.querySysDictDelCheck(map);
				
			typeCode="";
				
			if(map.get("reNote")!=null)reStr+=map.get("reNote");
		}
		
		if(reStr!=null && !reStr.equals("")){
			
			return "{\"error\":\"删除失败，选择的文档信息被以下业务使用：【"+reStr.substring(0,reStr.length()-1)+"】。\",\"state\":\"false\"}";
		}
		
		
		//批量删除
		int state = medFileTypeMapper.deleteBatch(entityMap);
		
		return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

	}

	@Override
	public String addOrUpdate(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}
	
	/**
	 * @Description 
	 * 文档分类<BR>添加
	 * @param  entityMap<BR>
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String query(Map<String, Object> entityMap) throws DataAccessException {
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage)entityMap.get("sysPage");
		
		if(sysPage.getTotal() == -1){
			
			List<?> list = medFileTypeMapper.query(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(),sysPage.getPagesize());
			
			List<?> list = medFileTypeMapper.query(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list,page.getTotal());
		}
	}
	
	/**
	 * @Description 
	 * 文档分类<BR> 编码查询
	 * @param  entityMap<BR>
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public <T> T queryByCode(Map<String, Object> entityMap) throws DataAccessException {
		
		return medFileTypeMapper.queryByCode(entityMap);
	}

	@Override
	public <T> T queryByUniqueness(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<?> queryExists(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

}
