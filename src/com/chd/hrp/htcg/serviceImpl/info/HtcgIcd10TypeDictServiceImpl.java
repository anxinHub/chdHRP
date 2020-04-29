package com.chd.hrp.htcg.serviceImpl.info;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.SessionManager;
import com.chd.base.SysPage;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.base.util.ReadFiles;
import com.chd.base.util.StringTool;
import com.chd.hrp.htcg.dao.info.HtcgIcd10TypeDictMapper;
import com.chd.hrp.htcg.entity.info.HtcgIcd10TypeDict;
import com.chd.hrp.htcg.service.info.HtcgIcd10TypeDictService;
import com.github.pagehelper.PageInfo;
@Service("htcgIcd10TypeDictService")
public class HtcgIcd10TypeDictServiceImpl implements HtcgIcd10TypeDictService{

	private static Logger logger = Logger.getLogger(HtcgIcd10TypeDictServiceImpl.class);
	
	@Resource(name="htcgIcd10TypeDictMapper")
	private final HtcgIcd10TypeDictMapper htcgIcd10TypeDictMapper = null;

	@Override
	public String addHtcgIcd10TypeDict(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		try {
			
			HtcgIcd10TypeDict htcgIcd10TypeDict =htcgIcd10TypeDictMapper.queryHtcgIcd10TypeDictDicByCode(entityMap);
			
			if(null != htcgIcd10TypeDict){
				
				return "{\"error\":\"编码已存在!.\",\"state\":\"true\"}";
			}
			
			htcgIcd10TypeDictMapper.addHtcgIcd10TypeDict(entityMap);
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";
			
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"添加失败\"}");
		}
	}

	@Override
	public String addBatchHtcgIcd10TypeDict(List<Map<String, Object>> list)
			throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String queryHtcgIcd10TypeDict(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal() == -1) {
			
			List<HtcgIcd10TypeDict> list = htcgIcd10TypeDictMapper.queryHtcgIcd10TypeDict(entityMap);
			
			return ChdJson.toJson(list);
		} else {
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(),sysPage.getPagesize());
			
			List<HtcgIcd10TypeDict> list = htcgIcd10TypeDictMapper.queryHtcgIcd10TypeDict(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());

		}
	}

	@Override
	public HtcgIcd10TypeDict queryHtcgIcd10TypeDictDicByCode(
			Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		return htcgIcd10TypeDictMapper.queryHtcgIcd10TypeDictDicByCode(entityMap);
	}

	@Override
	public String deleteHtcgIcd10TypeDict(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
  	   try {

			htcgIcd10TypeDictMapper.deleteHtcgIcd10TypeDict(entityMap);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
			
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"删除失败\"}");
		}
	}

	@Override
	public String deleteBatchHtcgIcd10TypeDict(
			List<Map<String, Object>> entityList) throws DataAccessException {
		// TODO Auto-generated method stub
		   try {

				htcgIcd10TypeDictMapper.deleteBatchHtcgIcd10TypeDict(entityList);
				
				return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
				
			} catch (Exception e) {
				// TODO: handle exception
				logger.error(e.getMessage(), e);
				throw new SysException("{\"error\":\"删除失败\"}");
			}
	}

	@Override
	public String updateHtcgIcd10TypeDict(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		   try {

				htcgIcd10TypeDictMapper.updateHtcgIcd10TypeDict(entityMap);
				
				return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";
				
			} catch (Exception e) {
				// TODO: handle exception
				logger.error(e.getMessage(), e);
				throw new SysException("{\"error\":\"修改失败\"}");
			}
	}

	@Override
	public String impIcd10TypeDictReadFiles(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		try {
			
			 /**
			  * 诊断性质内置诊断编码为{01,02,03}
			  */
			 Map<String, Object> natureMap = new HashMap<String, Object>();
			 natureMap.put("01", "门（急）诊诊断");
			 natureMap.put("02", "入院诊断");
			 natureMap.put("03", "出院诊断");
					 
			 List<Map<String, List<String>>> list = ReadFiles.readFiles(entityMap);
	       	  
        	 List<Map<String,Object>> resultSet = new ArrayList<Map<String,Object>>();
        	 
        	 if (list.size() == 0 || list == null) {
   				return "{\"error\":\"表头或者数据为空！请重新导入.\"}";
   			 }
        	 
        	 for (Map<String, List<String>> map : list) {
        		 
                Map<String, Object> mapVo = new HashMap<String, Object>();
        		 
        		 mapVo.put("group_id", SessionManager.getGroupId());
 				
 				 mapVo.put("hos_id", SessionManager.getHosId());
 				
 				 mapVo.put("copy_code", SessionManager.getCopyCode());
 				 
                 mapVo.put("icd10_type_code", map.get("icd10_type_code").get(1));
 				 
 				 mapVo.put("icd10_type_name", map.get("icd10_type_name").get(1));
 				 
 				 mapVo.put("icd10_nature_code", map.get("icd10_nature_code").get(1));
 				 
 				 mapVo.put("icd10_nature_name", map.get("icd10_nature_name").get(1));
 				 
 				 mapVo.put("is_stop", 0);
 				 
 				 mapVo.put("spell_code", StringTool.toPinyinShouZiMu(map.get("icd10_type_name").toString()));
 				
 				 mapVo.put("wbx_code", StringTool.toWuBi(map.get("icd10_type_name").toString()));
 				 
 				 /**
 				  * 判断
 				  * 导入的诊断性质是否和内置诊断性质编码相同
 				  */
 				 if(null ==natureMap.get(mapVo.get("icd10_nature_code"))){
 					 
 					return "{\"error\":\""+ map.get("icd10_nature_code").get(0)+" 诊断性质:"+map.get("icd10_nature_code").get(1)+" 不存在.\",\"state\":\"false\"}";
 				 }
 					 
 					 
 				HtcgIcd10TypeDict htcgIcd10TypeDict =htcgIcd10TypeDictMapper.queryHtcgIcd10TypeDictDicByCode(mapVo);
 				
 				if(null != htcgIcd10TypeDict){
 					
 					return "{\"error\":\""+ map.get("icd10_type_code").get(0)+" 诊断类型:"+map.get("icd10_type_code").get(1)+" 重复.\",\"state\":\"false\"}";
 				}
 				 
 				 resultSet.add(mapVo);
        	 }
        	 
        	  if(resultSet.size() > 0 ){
        		  htcgIcd10TypeDictMapper.addBatchHtcgIcd10TypeDict(resultSet);
        	  }
        	  return "{\"msg\":\"导入成功.\",\"state\":\"true\"}";
			
		} catch (Exception e) {
			// TODO: handle exception
			logger.debug(e.getMessage());
			throw new SysException("{\"error\":\"导入失败\"}");
		}
	}



}
