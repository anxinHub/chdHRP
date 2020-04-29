
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
import com.chd.hrp.ass.dao.dict.AssMaintainItemDictMapper;
import com.chd.hrp.ass.entity.dict.AssMaintainItemDict;
import com.chd.hrp.ass.service.dict.AssMaintainItemDictService;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Description:
 * 050108 保养项目字典
 * @Table:
 * ASS_MAINTAIN_ITEM_DICT
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 


@Service("assMaintainItemDictService")
public class AssMaintainItemDictServiceImpl implements AssMaintainItemDictService {

	private static Logger logger = Logger.getLogger(AssMaintainItemDictServiceImpl.class);
	//引入DAO操作
	@Resource(name = "assMaintainItemDictMapper")
	private final AssMaintainItemDictMapper assMaintainItemDictMapper = null;
    
	/**
	 * @Description 
	 * 添加050108 保养项目字典<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addAssMaintainItemDict(Map<String,Object> entityMap)throws DataAccessException{
		
	
		
		try {
			
			assMaintainItemDictMapper.addAssMaintainItemDict(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}
		
	}
	/**
	 * @Description 
	 * 批量添加050108 保养项目字典<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addBatchAssMaintainItemDict(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
			assMaintainItemDictMapper.addBatchAssMaintainItemDict(entityList);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}
		
	}
	
		/**
	 * @Description 
	 * 更新050108 保养项目字典<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateAssMaintainItemDict(Map<String,Object> entityMap)throws DataAccessException{
		
		try {
			
		  int state = assMaintainItemDictMapper.updateAssMaintainItemDict(entityMap);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}	
		
	}
	/**
	 * @Description 
	 * 批量更新050108 保养项目字典<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateBatchAssMaintainItemDict(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
		  assMaintainItemDictMapper.updateBatchAssMaintainItemDict(entityList);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}	
		
	}
	/**
	 * @Description 
	 * 删除050108 保养项目字典<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
    public String deleteAssMaintainItemDict(Map<String, Object> entityMap) throws DataAccessException {
    	
    try {
			
			int state = assMaintainItemDictMapper.deleteAssMaintainItemDict(entityMap);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}	
		
  }
    
	/**
	 * @Description 
	 * 批量删除050108 保养项目字典<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String deleteBatchAssMaintainItemDict(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
			assMaintainItemDictMapper.deleteBatchAssMaintainItemDict(entityList);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}	
	}
	
	/**
	 * @Description 
	 * 查询结果集050108 保养项目字典<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String queryAssMaintainItemDict(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<AssMaintainItemDict> list = assMaintainItemDictMapper.queryAssMaintainItemDict(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<AssMaintainItemDict> list = assMaintainItemDictMapper.queryAssMaintainItemDict(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
		
	}
	
	/**
	 * @Description 
	 * 获取对象050108 保养项目字典<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public AssMaintainItemDict queryAssMaintainItemDictByCode(Map<String,Object> entityMap)throws DataAccessException{
		return assMaintainItemDictMapper.queryAssMaintainItemDictByCode(entityMap);
	}
	@Override
	public String readAssMaintainItemDictFiles(Map<String, Object> entityMap) throws DataAccessException {
		String dataJson = null;
		Map<String, Object> is_stop_map = new HashMap<String, Object>();
		is_stop_map.put("是", "1");
		is_stop_map.put("否", "0");
		is_stop_map.put("1", "1");
		is_stop_map.put("0", "0");
		try {

			List<Map<String, List<String>>> list = ReadFiles.readFiles(entityMap);
			if (list.size() == 0 || list == null) {
				return "{\"error\":\"表头或者数据为空！请重新导入.\"}";
			}
			for (Map<String, List<String>> map2 : list) {

				if (map2.get("maintain_item_code").get(1) == null || map2.get("maintain_item_code").get(1).equals("")) {
					return "{\"msg\":\"" + map2.get("maintain_item_code").get(0)
							+ "，保养项目编码为空！\",\"state\":\"false\",\"row_cell\":\"" + map2.get("maintain_item_code").get(0)
							+ "\"}";
				} else if (map2.get("maintain_item_name").get(1) == null || map2.get("maintain_item_name").get(1).equals("")) {
					return "{\"msg\":\"" + map2.get("maintain_item_name").get(0)
							+ "，保养项目名称为空！\",\"state\":\"false\",\"row_cell\":\"" + map2.get("maintain_item_name").get(0)
							+ "\"}";
				} else if (map2.get("is_stop").get(1) == null || map2.get("is_stop").get(1).equals("")) {
					return "{\"msg\":\"" + map2.get("is_stop").get(0) + "，是否停用为空！\",\"state\":\"false\",\"row_cell\":\""
							+ map2.get("is_stop").get(0) + "\"}";
				} 

				Map<String, Object> mapVo1 = new HashMap<String, Object>();

				mapVo1.put("group_id", SessionManager.getGroupId());
				mapVo1.put("hos_id", SessionManager.getHosId());
				mapVo1.put("copy_code", SessionManager.getCopyCode());
				mapVo1.put("maintain_item_code", map2.get("maintain_item_code").get(1));
				mapVo1.put("maintain_item_name", map2.get("maintain_item_name").get(1));
				mapVo1.put("is_stop", is_stop_map.get(map2.get("is_stop").get(1)));
				// 判断唯一性 编码

				Map<String, Object> map_code = new HashMap<String, Object>();
				map_code.put("group_id", mapVo1.get("group_id"));
				map_code.put("hos_id", mapVo1.get("hos_id"));
				map_code.put("copy_code", mapVo1.get("copy_code"));
				map_code.put("maintain_item_code", mapVo1.get("maintain_item_code"));

				AssMaintainItemDict data_exc_extis_code = assMaintainItemDictMapper.queryAssMaintainItemDictByUniqueness(map_code);

				if (data_exc_extis_code != null) {
					continue;
					/*return "{\"warn\":\"" + map2.get("maintain_item_code").get(0)
							+ ",编码已经存在！\",\"state\":\"false\",\"row_cell\":\"" + map2.get("maintain_item_code").get(0)
							+ "\"}";*/

				}
				// 判断唯一性 名字

				Map<String, Object> map_name = new HashMap<String, Object>();
				map_name.put("group_id", mapVo1.get("group_id"));
				map_name.put("hos_id", mapVo1.get("hos_id"));
				map_name.put("copy_code", mapVo1.get("copy_code"));
				map_name.put("maintain_item_name", mapVo1.get("maintain_item_name"));

				AssMaintainItemDict data_exc_extis_name = assMaintainItemDictMapper.queryAssMaintainItemDictByUniqueness(map_name);

				if (data_exc_extis_name != null) {
					continue;
					/*return "{\"warn\":\"" + map2.get("maintain_item_name").get(0)
							+ ",名称已经存在！\",\"state\":\"false\",\"row_cell\":\"" + map2.get("maintain_item_name").get(0)
							+ "\"}";*/

				}

				mapVo1.put("spell_code", StringTool.toPinyinShouZiMu(mapVo1.get("maintain_item_name").toString()));

				mapVo1.put("wbx_code", StringTool.toWuBi(mapVo1.get("maintain_item_name").toString()));
				
				assMaintainItemDictMapper.addAssMaintainItemDict(mapVo1);
				dataJson = "{\"msg\":\"导入成功.\",\"state\":\"true\"}";
			}

			return dataJson;

		} catch (Exception e) {

			e.printStackTrace();
			logger.debug(e.getMessage());
			throw new SysException(e.getMessage());

		}
	}
	@Override
	public AssMaintainItemDict queryByName(Map<String, Object> entityMap) throws DataAccessException {
		return assMaintainItemDictMapper.queryByName(entityMap);

	}
	
}
