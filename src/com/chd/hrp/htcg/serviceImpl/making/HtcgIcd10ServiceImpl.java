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
import com.chd.hrp.htcg.dao.making.HtcgIcd10Mapper;
import com.chd.hrp.htcg.entity.making.HtcgIcd10;
import com.chd.hrp.htcg.service.making.HtcgIcd10Service;
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


@Service("htcgIcd10Service")
public class HtcgIcd10ServiceImpl implements HtcgIcd10Service {

	private static Logger logger = Logger.getLogger(HtcgIcd10ServiceImpl.class);
	
	@Resource(name = "htcgIcd10Mapper")
	private final HtcgIcd10Mapper htcgIcd10Mapper = null;
     
	@Override
	public String addHtcgIcd10(Map<String,Object> entityMap)throws DataAccessException{
		
		HtcgIcd10 icd10 =  htcgIcd10Mapper.queryHtcgIcd10ByCode(entityMap);
		
	    if (icd10 != null) { 
			return "{\"error\":\"\",\"error\":\"编码已经存在，不能重复添加.\"}";
		}
	     
	    try{
		      htcgIcd10Mapper.addHtcgIcd10(entityMap);
		      
		      return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";
			 
	    } catch (Exception e) {

			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"添加失败\"}");

		}
	}
	

	@Override
	public String addBatchHtcgIcd10(List<Map<String, Object>> list)
			throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public String queryHtcgIcd10(Map<String,Object> entityMap) throws DataAccessException{
		SysPage sysPage = new SysPage();
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<HtcgIcd10> list = htcgIcd10Mapper.queryHtcgIcd10(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<HtcgIcd10> list = htcgIcd10Mapper.queryHtcgIcd10(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
		
	}
	 
	@Override
	public HtcgIcd10 queryHtcgIcd10ByCode(Map<String,Object> entityMap)throws DataAccessException{
		return htcgIcd10Mapper.queryHtcgIcd10ByCode(entityMap);
	}
	 



	@Override
	public String deleteHtcgIcd10(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}
	
	public String deleteBatchHtcgIcd10(List<Map<String,Object>> entityList)throws DataAccessException{
		 
		try{
	        htcgIcd10Mapper.deleteBatchHtcgIcd10(entityList);
        	 
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
	    } catch (Exception e) {

			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"删除失败\"}");

		}
		
	}
	 
	 
	@Override
	public String updateHtcgIcd10(Map<String,Object> entityMap)throws DataAccessException{
	    try{
	    	
			htcgIcd10Mapper.updateHtcgIcd10(entityMap);
			
			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";
		 
	    } catch (Exception e) {

			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"修改失败\"}");

		}
	}
	
	@Override
	public String importHtcgIcd10(Map<String, Object> entityMap) throws DataAccessException {

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
				
				mapVo.put("icd10_code", map2.get("icd10_code").get(1));
				
				mapVo.put("icd10_name", map2.get("icd10_name").get(1));
				
				mapVo.put("icd10_note", map2.get("icd10_note").get(1));
				
				 if(!StringTool.isNotBlank(mapVo.get("icd10_note"))){
 					 mapVo.put("icd10_note", "");
 				 }
				
	            mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("icd10_name").toString()));
				
				mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("icd10_name").toString()));
				
				HtcgIcd10 icd10 =  htcgIcd10Mapper.queryHtcgIcd10ByCode(mapVo);
				
				if(icd10 != null){
					
					return "{\"msg\":\"" + map2.get("icd10_code").get(0) + "，病种分类编码重复！\",\"state\":\"false\",\"row_cell\":\"" + map2.get("icd10_code").get(0)+ "\"}";
				}
				 
				listMap.add(mapVo);
				
			}
			
			if(listMap.size() > 0 ){
				htcgIcd10Mapper.addBatchHtcgIcd10(listMap);
			}
			return "{\"msg\":\"导入成功.\",\"state\":\"true\"}";
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			logger.debug(e.getMessage());
			throw new SysException(e.getMessage());
		}

	}

}
