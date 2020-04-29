package com.chd.hrp.htcg.serviceImpl.making.drgs;
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
import com.chd.hrp.htcg.dao.making.drgs.HtcgDrgsMapper;
import com.chd.hrp.htcg.dao.making.drgs.HtcgDrgsTypeMapper;
import com.chd.hrp.htcg.entity.making.drgs.HtcgDrgs;
import com.chd.hrp.htcg.entity.making.drgs.HtcgDrgsType;
import com.chd.hrp.htcg.service.making.drgs.HtcgDrgsService;
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


@Service("htcgDrgsService")
public class HtcgDrgsServiceImpl implements HtcgDrgsService {

	private static Logger logger = Logger.getLogger(HtcgDrgsServiceImpl.class);
	
	@Resource(name = "htcgDrgsMapper")
	private final HtcgDrgsMapper htcgDrgsMapper = null;
	
	
	@Resource(name = "htcgDrgsTypeMapper")
	private final HtcgDrgsTypeMapper htcgDrgsTypeMapper = null;
	
	/**
	 * 
	 */
	@Override
	public String addHtcgDrgs(Map<String,Object> entityMap)throws DataAccessException{
		
		HtcgDrgs drgs = queryHtcgDrgsByCode(entityMap);
		
	     if (drgs != null) { 
	    	 
	    	 return "{\"error\":\"编码存在.\",\"state\":\"false\"}";
	    	 
	     }
	    try{
	    	
	    	htcgDrgsMapper.addHtcgDrgs(entityMap);

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
	public String queryHtcgDrgs(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<HtcgDrgs> list = htcgDrgsMapper.queryHtcgDrgs(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<HtcgDrgs> list = htcgDrgsMapper.queryHtcgDrgs(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
		
	}
	
	/**
	 * 
	 */
	@Override
	public HtcgDrgs queryHtcgDrgsByCode(Map<String,Object> entityMap)throws DataAccessException{
		return htcgDrgsMapper.queryHtcgDrgsByCode(entityMap);
	}
	
	
	/**
	 * 
	 */
	
	public String deleteBatchHtcgDrgs(List<Map<String,Object>> entityList)throws DataAccessException{
		 
		
		try{ 
			
			htcgDrgsMapper.deleteBatchHtcgDrgs(entityList);
			 
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
			 
	    } catch (Exception e) {

			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"删除失败\"}");

		}	
	}
	 
	@Override
	public String updateHtcgDrgs(Map<String,Object> entityMap)throws DataAccessException{
		
		
		entityMap.put("spell_code", StringTool.toPinyinShouZiMu(entityMap.get("drgs_name").toString())); 
		
	    entityMap.put("wbx_code", StringTool.toWuBi(entityMap.get("drgs_name").toString()));
	     
		try{
			
			htcgDrgsMapper.updateHtcgDrgs(entityMap);
		 
			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";
 
	    } catch (Exception e) {

			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"修改失败\"}");

		}	
	}


	@Override
	public String importHtcgDrgs(Map<String, Object> entityMap) throws DataAccessException {

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
				
				mapVo.put("drgs_code", map2.get("drgs_code").get(1));
				
				mapVo.put("drgs_name", map2.get("drgs_name").get(1));
				
				mapVo.put("drgs_type_code", map2.get("drgs_type_code").get(1));
				
				mapVo.put("drgs_type_name", map2.get("drgs_type_name").get(1));
				
				mapVo.put("drgs_note", map2.get("drgs_note").get(1));
			
				mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("drgs_name").toString()));
				
				mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("drgs_name").toString()));
				
				 if(!StringTool.isNotBlank(mapVo.get("drgs_note"))){
 					 mapVo.put("drgs_note", "");
 				 }
				 
				Map<String, Object> drgsTypeMap = new HashMap<String, Object>();
				drgsTypeMap.put("group_id", mapVo.get("group_id"));
				drgsTypeMap.put("hos_id", mapVo.get("hos_id"));
				drgsTypeMap.put("copy_code", mapVo.get("copy_code"));
				drgsTypeMap.put("drgs_type_code", mapVo.get("drgs_type_code"));
				HtcgDrgsType htcgDrgsType = htcgDrgsTypeMapper.queryHtcgDrgsTypeByCode(drgsTypeMap);
				
				if(null == htcgDrgsType){
					
					return "{\"error\":\""+ map2.get("drgs_type_code").get(0)+"\t病种分类编码:"+map2.get("drgs_type_code").get(1)+"不存在.\",\"state\":\"false\"}";
				}
				
				HtcgDrgs drgs = queryHtcgDrgsByCode(mapVo);
				
			     if (drgs != null) { 
			    	 
			    	 return "{\"error\":\""+ map2.get("drgs_code").get(0)+"\t病种编码:"+map2.get("drgs_code").get(1)+" 重复.\",\"state\":\"false\"}";
			     }
				
				listMap.add(mapVo);
				
			}
			
			if(listMap.size() > 0){
			   htcgDrgsMapper.addBatchHtcgDrgs(listMap) ;
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
