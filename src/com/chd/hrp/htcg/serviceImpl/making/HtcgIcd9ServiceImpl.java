package com.chd.hrp.htcg.serviceImpl.making;

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
import com.chd.hrp.htcg.dao.making.HtcgIcd9Mapper;
import com.chd.hrp.htcg.entity.making.HtcgIcd9;
import com.chd.hrp.htcg.service.making.HtcgIcd9Service;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Title. 
 * @Description.
 * @Copyright: Copyright (c) 2015-2-14 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @Author: LiuYingDuo
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */


@Service("htcgIcd9Service")
public class HtcgIcd9ServiceImpl implements HtcgIcd9Service {

	private static Logger logger = Logger.getLogger(HtcgIcd9ServiceImpl.class);
	
	@Resource(name = "htcgIcd9Mapper")
	private final HtcgIcd9Mapper htcgIcd9Mapper = null;
    
	/**
	 * 
	 */
	@Override
	public String addHtcgIcd9(Map<String,Object> entityMap)throws DataAccessException{
		
		HtcgIcd9 icd9 = queryHtcgIcd9ByCode(entityMap);
		if(icd9 != null){
			return "{\"msg\":\"手术编码重复,请重新添加.\",\"state\":\"false\"}";
		} 
	    try{
			
	    	htcgIcd9Mapper.addHtcgIcd9(entityMap);
				 
		    return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";
				 
	    } catch (Exception e) {

			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"添加失败\"}");

		}	 
			
	}
	  
	
	/**
	 * 
	 */
	@Override
	public String queryHtcgIcd9(Map<String,Object> entityMap) throws DataAccessException{
		SysPage sysPage = new SysPage();
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<HtcgIcd9> list = htcgIcd9Mapper.queryHtcgIcd9(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<HtcgIcd9> list = htcgIcd9Mapper.queryHtcgIcd9(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
		 
	}
	
	/**
	 * 
	 */
	@Override
	public HtcgIcd9 queryHtcgIcd9ByCode(Map<String,Object> entityMap)throws DataAccessException{
		return htcgIcd9Mapper.queryHtcgIcd9ByCode(entityMap);
	}
	
	
	/**
	 * 
	 */
	
	public String deleteBatchHtcgIcd9(List<Map<String,Object>> entityList)throws DataAccessException{
		 
		try{
	    	htcgIcd9Mapper.deleteBatchHtcgIcd9(entityList);
        	 
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
			 
	    } catch (Exception e) {

			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"删除失败\"}");

		}	
	}
	 
	@Override
	public String updateHtcgIcd9(Map<String,Object> entityMap)throws DataAccessException{
		
		entityMap.put("spell_code", StringTool.toPinyinShouZiMu(entityMap.get("icd9_name").toString()));
		
	    entityMap.put("wbx_code", StringTool.toWuBi(entityMap.get("icd9_name").toString()));
	    
		try{
			htcgIcd9Mapper.updateHtcgIcd9(entityMap);
		 
			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";
		 
	    } catch (Exception e) {

			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"修改失败\"}");

		}
	}
	@Override
	public String importHtcgIcd9(Map<String, Object> entityMap) throws DataAccessException {

		try {
			
			List<Map<String, List<String>>> list = ReadFiles.readFiles(entityMap);
			
			List<Map<String,Object>> listMap = new ArrayList<Map<String,Object>>();
			
			if (list.size() == 0 || list == null) {
				
				return "{\"error\":\"表头或者数据为空！请重新导入.\"}";
			}
			
			for (Map<String, List<String>> map2 : list) {
				
				Map<String, Object> mapVo = new HashMap<String, Object>();
				
				mapVo.put("group_id", SessionManager.getGroupId());

				mapVo.put("hos_id", SessionManager.getHosId());

				mapVo.put("copy_code", SessionManager.getCopyCode());
				
				mapVo.put("icd9_code", map2.get("icd9_code").get(1));
				
				mapVo.put("icd9_name", map2.get("icd9_name").get(1));
				
				mapVo.put("icd9_note", map2.get("icd9_note").get(1));
				
				mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("icd9_name").toString()));
				
				mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("icd9_name").toString()));
				
				 if(!StringTool.isNotBlank(mapVo.get("icd9_note"))){
 					 mapVo.put("icd9_note", "");
 				 }
				 
                HtcgIcd9 icd9 = queryHtcgIcd9ByCode(mapVo);
				
				if(icd9 != null){
					return "{\"msg\":\"" + map2.get("icd9_code").get(0) + "，手术编码重复！\",\"state\":\"false\",\"row_cell\":\"" + map2.get("icd9_code").get(0)+ "\"}";
				}
				listMap.add(mapVo);
				
			}
			
			htcgIcd9Mapper.addBatchHtcgIcd9(listMap) ;
			
			return "{\"msg\":\"导入成功.\",\"state\":\"true\"}";
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			logger.debug(e.getMessage());
			throw new SysException(e.getMessage());
		}

	}

	 
}
