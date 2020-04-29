
/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 package com.chd.hrp.ass.serviceImpl.dict;

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
import com.chd.base.util.ReadFiles;
import com.chd.base.util.StringTool;
import com.chd.hrp.ass.dao.dict.AssPropDictMapper;
import com.chd.hrp.ass.entity.dict.AssPropDict;
import com.chd.hrp.ass.entity.dict.AssTypeDict;
import com.chd.hrp.ass.service.dict.AssPropDictService;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Description:
 * 050107 产权形式字典
 * @Table:
 * ASS_ACCEPT_ITEM_DICT
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 


@Service("assPropDictService")
public class AssPropDictServiceImpl implements AssPropDictService {

	private static Logger logger = Logger.getLogger(AssPropDictServiceImpl.class);
	//引入DAO操作
	@Resource(name = "assPropDictMapper")
	private final AssPropDictMapper assPropDictMapper = null;
    
	/**
	 * @Description 
	 * 添加050107 产权形式字典<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addAssPropDict(Map<String,Object> entityMap)throws DataAccessException{
		
		try {
			
			int state = assPropDictMapper.addAssPropDict(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}
		
	}
	/**
	 * @Description 
	 * 批量添加050107 产权形式字典<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addBatchAssPropDict(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
			assPropDictMapper.addBatchAssPropDict(entityList);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}
		
	}
	
		/**
	 * @Description 
	 * 更新050107 产权形式字典<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateAssPropDict(Map<String,Object> entityMap)throws DataAccessException{
		
		try {
			
		  int state = assPropDictMapper.updateAssPropDict(entityMap);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}	
		
	}
	/**
	 * @Description 
	 * 批量更新050107 产权形式字典<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateBatchAssPropDict(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
		  assPropDictMapper.updateBatchAssPropDict(entityList);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}	
		
	}
	/**
	 * @Description 
	 * 删除050107 产权形式字典<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
    public String deleteAssPropDict(Map<String, Object> entityMap) throws DataAccessException {
    	
    try {
			
			int state = assPropDictMapper.deleteAssPropDict(entityMap);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}	
		
  }
    
	/**
	 * @Description 
	 * 批量删除050107 产权形式字典<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String deleteBatchAssPropDict(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
			assPropDictMapper.deleteBatchAssPropDict(entityList);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}	
	}
	
	/**
	 * @Description 
	 * 查询结果集050107 产权形式字典<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String queryAssPropDict(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<AssPropDict> list = assPropDictMapper.queryAssPropDict(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<AssPropDict> list = assPropDictMapper.queryAssPropDict(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
		
	}
	
	/**
	 * @Description 
	 * 获取对象050107 产权形式字典<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public AssPropDict queryAssPropDictByCode(Map<String,Object> entityMap)throws DataAccessException{
		return assPropDictMapper.queryAssPropDictByCode(entityMap);
	}
	@Override
	public String assPropDictImport(Map<String, Object> mapVo) {
		
		try {
			List<Map<String, List<String>>> list = ReadFiles.readFiles(mapVo);
			if (list.size()==0 || list==null) {
				return	"{\"error\":\"表头或者数据为空！请重新导入.\"}";
			}
			for (Map<String, List<String>> map : list) {
				if(map.get("prop_code").get(1)==null || "".equals(map.get("prop_code").get(1))){
				return "{\"msg\":\""+map.get("prop_code").get(0)+"，为空！\",\"state\":\"false\",\"row_cell\":\""+map.get("prop_code").get(0)+"\"}";

				}
				if (map.get("prop_name").get(1)==null || map.get("prop_name").get(1).equals("")){
					return "{\"msg\":\""+map.get("prop_name").get(0)+"，为空！\",\"state\":\"false\",\"row_cell\":\""+map.get("prop_name").get(0)+"\"}";
				}
				if (map.get("is_stop").get(1)==null || map.get("is_stop").get(1).equals("")){
					return "{\"msg\":\""+map.get("is_stop").get(0)+"，为空！\",\"state\":\"false\",\"row_cell\":\""+map.get("is_stop").get(0)+"\"}";
				}
				Map<String, Object> mapVo1 = new HashMap<String, Object>();

				mapVo1.put("group_id", SessionManager.getGroupId());

				mapVo1.put("hos_id", SessionManager.getHosId());

				mapVo1.put("copy_code", SessionManager.getCopyCode());
				mapVo1.put("prop_code", map.get("prop_code").get(1));
				mapVo1.put("prop_name", map.get("prop_name").get(1));
				mapVo1.put("is_stop", map.get("is_stop").get(1));
				
				Map<String, Object> map_code = new HashMap<String, Object>();
				map_code.put("group_id", mapVo1.get("group_id"));
				map_code.put("hos_id", mapVo1.get("hos_id"));
				map_code.put("copy_code", mapVo1.get("copy_code"));
				map_code.put("prop_code", mapVo1.get("prop_code"));

				AssTypeDict data_exc_extis_code = assPropDictMapper.queryExistsCode(map_code);

				if (data_exc_extis_code != null) {
					return "{\"warn\":\"" + map.get("prop_code").get(0) + ",编码已经存在！\",\"state\":\"false\",\"row_cell\":\""
							+  map.get("prop_code").get(0) + "\"}";

				}
				// 判断唯一性 名字

				Map<String, Object> map_name = new HashMap<String, Object>();
				map_name.put("group_id", mapVo1.get("group_id"));
				map_name.put("hos_id", mapVo1.get("hos_id"));
				map_name.put("copy_code", mapVo1.get("copy_code"));
				map_name.put("prop_name", mapVo1.get("prop_name"));

				AssTypeDict data_exc_extis_name = assPropDictMapper.queryExistsName(map_name);

				if (data_exc_extis_name != null) {

					return "{\"warn\":\"" +  map.get("prop_name").get(0) + ",名称已经存在！\",\"state\":\"false\",\"row_cell\":\""
							+ map.get("prop_name").get(0) + "\"}";

				}
				mapVo1.put("spell_code", StringTool.toPinyinShouZiMu(mapVo1.get("prop_name").toString()));

				mapVo1.put("wbx_code", StringTool.toWuBi(mapVo1.get("prop_name").toString()));
				
				 assPropDictMapper.addAssPropDict(mapVo1);
			}
			return"{\"msg\":\"添加成功.\",\"state\":\"true\"}";
			
		} catch (Exception e) {
			throw new SysException(e);
		}
	}
	@Override
	public String readAssPropDictFiles(Map<String, Object> mapVo) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public AssPropDict queryByName(Map<String, Object> entityMap) throws DataAccessException {
		return assPropDictMapper.queryByName(entityMap);

	}			
	
}
