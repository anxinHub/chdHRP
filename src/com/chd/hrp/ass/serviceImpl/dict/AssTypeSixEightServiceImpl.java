
/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 package com.chd.hrp.ass.serviceImpl.dict;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.nutz.lang.Strings;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.SessionManager;
import com.chd.base.SysPage;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.base.util.ReadFiles;
import com.chd.base.util.StringTool;
import com.chd.hrp.ass.dao.dict.AssTypeSixEightMapper;
import com.chd.hrp.ass.entity.dict.AssGBcode;
import com.chd.hrp.ass.entity.dict.AssTypeSixEight;
import com.chd.hrp.ass.service.dict.AssTypeSixEightService;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Description:
 * 050104 68分类字典
 * @Table:
 * ASS_USAGE_DICT
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 
 

@Service("assTypeSixEightService")
public class AssTypeSixEightServiceImpl implements AssTypeSixEightService {

	private static Logger logger = Logger.getLogger(AssTypeSixEightServiceImpl.class);
	//引入DAO操作
	@Resource(name = "assTypeSixEightMapper")
	private final AssTypeSixEightMapper assTypeSixEightMapper = null;
    
	/**
	 * @Description 
	 * 添加050104 68分类字典<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addAssTypeSixEight(Map<String,Object> entityMap)throws DataAccessException{
		
		//获取对象050104 68分类字典
		AssTypeSixEight assTypeSixEight = queryAssTypeSixEightByCode(entityMap);

		if (assTypeSixEight != null) {

			return "{\"error\":\"数据重复,请重新添加.\"}";

		}
		
		try {
			
			int state = assTypeSixEightMapper.addAssTypeSixEight(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}
		
	}
	/**
	 * @Description 
	 * 批量添加050104 68分类字典<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addBatchAssTypeSixEight(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
			assTypeSixEightMapper.addBatchAssTypeSixEight(entityList);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}
		
	}
	
		/**
	 * @Description 
	 * 更新050104 68分类字典<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateAssTypeSixEight(Map<String,Object> entityMap)throws DataAccessException{
		
		try {
			
		  int state = assTypeSixEightMapper.updateAssTypeSixEight(entityMap);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}	
		
	}
	/**
	 * @Description 
	 * 批量更新050104 68分类字典<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateBatchAssTypeSixEight(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
			assTypeSixEightMapper.updateBatchAssTypeSixEight(entityList);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}	
		
	}
	/**
	 * @Description 
	 * 删除050104 68分类字典<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
    public String deleteAssTypeSixEight(Map<String, Object> entityMap) throws DataAccessException {
    	
		try {
			
			AssTypeSixEight at= assTypeSixEightMapper.queryAssTypeSixEightByCode(entityMap);
			Map<String, Object> supper_code_map =new HashMap<String, Object>();
		
			supper_code_map.put("supper_code", at.getType_code());
			assTypeSixEightMapper.deleteAssTypeSixEightBySuperCode(supper_code_map);
    	
			int state = assTypeSixEightMapper.deleteAssTypeSixEight(entityMap);
			
			//如果有上级
			if(Strings.isNotBlank(at.getSupper_code())) {
				//如果上级无其它下级 则更改上级为末级  
				Map<String, Object> supper_map =new HashMap<String, Object>(entityMap);
				supper_map.put("supper_code", at.getSupper_code());
				List<AssTypeSixEight> list = assTypeSixEightMapper.queryAssSixEightChild(supper_map);
				if(list == null || list.size() == 0){
					supper_map.put("is_last", 1);
					assTypeSixEightMapper.updateAssTypeSixEightIsLast(supper_map);
				}
			}
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}	
		
  }
    
	/**
	 * @Description 
	 * 批量删除050104 68分类字典<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String deleteBatchAssTypeSixEight(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
						
			
			assTypeSixEightMapper.deleteBatchAssTypeSixEight(entityList);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}	
	}
	
	/**
	 * @Description 
	 * 查询结果集050104 68分类字典<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String queryAssTypeSixEight(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<AssTypeSixEight> list = assTypeSixEightMapper.queryAssTypeSixEight(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<AssTypeSixEight> list = assTypeSixEightMapper.queryAssTypeSixEight(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
		
	}
	
	/**
	 * @Description 
	 * 获取对象050104 68分类字典<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public AssTypeSixEight queryAssTypeSixEightByCode(Map<String,Object> entityMap)throws DataAccessException{
		return assTypeSixEightMapper.queryAssTypeSixEightByCode(entityMap);
	}
	@Override
	public AssTypeSixEight queryAssTypeSixEightByCodeOrName(Map<String, Object> mapVo) throws DataAccessException {
		return assTypeSixEightMapper.queryAssTypeSixEightByCodeOrName(mapVo);
	}
	/**
	 * 导入数据 050104 68分类字典
	 * @throws Exception 
	 */
	@Override
	public String assTypeSixEightImport(Map<String, Object> mapVo) throws Exception {
		try {
			Map<String, Object> is_last_map = new HashMap<String, Object>();
			is_last_map.put("是", "1");
			is_last_map.put("否", "0");
			is_last_map.put("1", "1");
			is_last_map.put("0", "0");
			
			
			List<Map<String, List<String>>> list = ReadFiles.readFiles(mapVo);
			if (list==null || list.size()==0) {
				return	"{\"error\":\"表头或者数据为空！请重新导入.\"}";
			}
			for (Map<String, List<String>> map : list) {
				if(map.get("equi_usage_code").get(1)==null || "".equals(map.get("equi_usage_code").get(1))){
					return "{\"msg\":\""+map.get("equi_usage_code").get(0)+"，为空！\",\"state\":\"false\",\"row_cell\":\""+map.get("equi_usage_code").get(0)+"\"}";

				}
				if (map.get("equi_usage_name").get(1)==null || map.get("equi_usage_name").get(1).equals("")){
					return "{\"msg\":\""+map.get("equi_usage_name").get(0)+"，为空！\",\"state\":\"false\",\"row_cell\":\""+map.get("equi_usage_name").get(0)+"\"}";
				}
				if (map.get("is_stop").get(1)==null || map.get("is_stop").get(1).equals("")){
					return "{\"msg\":\""+map.get("is_stop").get(0)+"，为空！\",\"state\":\"false\",\"row_cell\":\""+map.get("is_stop").get(0)+"\"}";
				}
				
				
				if (map.get("is_last").get(1) == null || map.get("is_last").get(1).equals("")) {
						return "{\"msg\":\"" + map.get("is_last").get(0)
								+ "，是否末级分类为空！\",\"state\":\"false\",\"row_cell\":\"" + map.get("is_last").get(0) + "\"}";
				}
				 if (map.get("supper_code").get(1) == null || map.get("supper_code").get(1).equals("")) {
						return "{\"msg\":\"" + map.get("supper_code").get(0)
								+ "，上级编码为空！\",\"state\":\"false\",\"row_cell\":\"" + map.get("supper_code").get(0) + "\"}";
				 }
				
				Map<String, Object> mapVo1 = new HashMap<String, Object>();

				mapVo1.put("group_id", SessionManager.getGroupId());

				mapVo1.put("hos_id", SessionManager.getHosId());

				mapVo1.put("copy_code", SessionManager.getCopyCode());
				mapVo1.put("equi_usage_code", map.get("equi_usage_code").get(1));
				mapVo1.put("equi_usage_name", map.get("equi_usage_name").get(1));
				
				mapVo1.put("supper_code", map.get("supper_code").get(1));
				mapVo1.put("is_last", is_last_map.get(map.get("is_last").get(1)));
				mapVo1.put("is_stop", is_last_map.get(map.get("is_stop").get(1)));
				
				
				Map<String, Object> map_code = new HashMap<String, Object>();
				map_code.put("group_id", mapVo1.get("group_id"));
				map_code.put("hos_id", mapVo1.get("hos_id"));
				map_code.put("copy_code", mapVo1.get("copy_code"));
				map_code.put("equi_usage_code", mapVo1.get("equi_usage_code"));
				AssTypeSixEight data_exc_extis_code = assTypeSixEightMapper.queryAssTypeSixEightByCodeOrName(map_code);

				if (data_exc_extis_code != null) {
					return "{\"warn\":\"" + map.get("equi_usage_code").get(0) + ",编码已经存在！\",\"state\":\"false\",\"row_cell\":\""
							+  map.get("equi_usage_code").get(0) + "\"}";

				}
				// 判断唯一性 名字

				Map<String, Object> map_name = new HashMap<String, Object>();
				map_name.put("group_id", mapVo1.get("group_id"));
				map_name.put("hos_id", mapVo1.get("hos_id"));
				map_name.put("copy_code", mapVo1.get("copy_code"));
				map_name.put("equi_usage_name", mapVo1.get("equi_usage_name"));

				AssTypeSixEight data_exc_extis_name = assTypeSixEightMapper.queryAssTypeSixEightByCodeOrName(map_name);

				if (data_exc_extis_name != null) {

					return "{\"warn\":\"" +  map.get("equi_usage_name").get(0) + ",名称已经存在！\",\"state\":\"false\",\"row_cell\":\""
							+ map.get("equi_usage_name").get(0) + "\"}";

				}
				mapVo1.put("spell_code", StringTool.toPinyinShouZiMu(mapVo1.get("equi_usage_name").toString()));

				mapVo1.put("wbx_code", StringTool.toWuBi(mapVo1.get("equi_usage_name").toString()));
				
				assTypeSixEightMapper.addAssTypeSixEight(mapVo1);
			}
			return"{\"msg\":\"添加成功.\",\"state\":\"true\"}";
			
		} catch (Exception e) {
			throw new SysException(e);
		}
	}
	@Override
	public List<AssTypeSixEight> queryAssTypeSixEightChild(Map<String, Object> mapVo) throws DataAccessException {
		return assTypeSixEightMapper.queryAssSixEightChild(mapVo);
	}
	
}
