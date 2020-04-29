/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.mat.serviceImpl.info.basic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.SysPage;
import com.chd.base.util.ChdJson;
import com.chd.hrp.mat.dao.info.basic.MatLocationTypeMapper;
import com.chd.hrp.mat.entity.MatLocationType;
import com.chd.hrp.mat.service.info.basic.MatLocationTypeService;
import com.chd.hrp.sys.dao.notGeneral.SysFunUtilMapper;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Description:
 * 04404 货位物资类别信息
 * @Table:
 * MAT_LOCATION_INV_TYPE
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 


@Service("matLocationTypeService")
public class MatLocationTypeServiceImpl implements MatLocationTypeService {

	private static Logger logger = Logger.getLogger(MatLocationTypeServiceImpl.class);
	//引入DAO操作
	@Resource(name = "matLocationTypeMapper")
	private final MatLocationTypeMapper matLocationTypeMapper = null;
    
	@Resource(name = "sysFunUtilMapper")
	private final SysFunUtilMapper sysFunUtilMapper = null;   
	
	/**
	 * @Description 
	 * 查询结果集04401 货位分类字典<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String query(Map<String,Object> entityMap) throws DataAccessException{
		SysPage sysPage = new SysPage();
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){	
			List<MatLocationType> list = (List<MatLocationType>) matLocationTypeMapper.query(entityMap);
			return ChdJson.toJson(list);	
		}else{	
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());	
			List<MatLocationType> list = (List<MatLocationType>) matLocationTypeMapper.query(entityMap, rowBounds);			
			PageInfo page = new PageInfo(list);			
			return ChdJson.toJson(list, page.getTotal());			
		}		
	}
	
	
	/**
	 * @Description 
	 * 添加04401 货位分类字典<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String add(Map<String,Object> entityMap)throws DataAccessException{
		
		//获取对象04401 货位分类字典
		MatLocationType matLocationType = queryByCode(entityMap);
		if (matLocationType != null) {
			return "{\"error\":\"数据重复,请重新添加.\"}";
		}

		try {
			int state = matLocationTypeMapper.add(entityMap);
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);
			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addMatLocationType\"}";

		}
		
	}
	/**
	 * @Description 
	 * 批量添加04401 货位分类字典<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addBatch(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
			matLocationTypeMapper.addBatch(entityList);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addBatchMatLocationType\"}";

		}
		
	}
	
		/**
	 * @Description 
	 * 更新04401 货位分类字典<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String update(Map<String,Object> entityMap)throws DataAccessException{
		
		try {
			
		  int state = matLocationTypeMapper.update(entityMap);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 updateMatLocationType\"}";

		}	
		
	}
	/**
	 * @Description 
	 * 批量更新04401 货位分类字典<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateBatch(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
		  matLocationTypeMapper.updateBatch(entityList);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 updateBatchMatLocationType\"}";

		}	
		
	}
	/**
	 * @Description 
	 * 删除04401 货位分类字典<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
    public String delete(Map<String, Object> entityMap) throws DataAccessException {
    	
    try {
			
			int state = matLocationTypeMapper.delete(entityMap);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 deleteMatLocationType\"}";

		}	
		
  }
    
	/**
	 * @Description 
	 * 批量删除04401 货位分类字典<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String deleteBatch(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			//判断是否被使用
			/*for( int i=0;i<entityList.size(); i++){
				String reStr="";
				Map<String,Object> map = new HashMap<String, Object>();
				map.put("dict_code", "MAT_LOCATION_TYPE");
				map.put("copy_code", entityList.get(i).get("copy_code"));
				map.put("hos_id", entityList.get(i).get("hos_id"));
				map.put("group_id", entityList.get(i).get("group_id"));
				map.put("dict_id_str", entityList.get(i).get("location_type_id"));
				
				System.out.println("********* dict_code:"+ map.get("dict_code"));
				System.out.println("********* hos_id:"+ map.get("hos_id"));
				System.out.println("********* group_id:"+ map.get("group_id"));
				System.out.println("********* copy_code:"+ map.get("copy_code"));
				System.out.println("********* dict_id_str:"+ map.get("dict_id_str"));
				
				sysFunUtilMapper.querySysDictDelCheck(map);
				if(map.get("reNote")!=null) 
					reStr+=map.get("reNote");
					
				if(reStr!=null && !reStr.equals("")){
					return "{\"error\":\"删除失败，选择的物资类别被以下业务使用：【"+reStr.substring(0,reStr.length()-1)+"】。\",\"state\":\"false\"}";
				}
	        }*/
			//  查看要删除的货位是否在mat_location_dict中存在
			for(int a =0;a<entityList.size();a++){
				Map<String,Object> map = new HashMap<String, Object>();
				
				map.put("copy_code", entityList.get(a).get("copy_code"));
				map.put("hos_id", entityList.get(a).get("hos_id"));
				map.put("group_id", entityList.get(a).get("group_id"));
				map.put("location_type_id", entityList.get(a).get("location_type_id"));
				
				int state = matLocationTypeMapper.queryMatLocationDictIsExists(map);
				if(state >0 ){
					return "{\"error\":\"删除失败，选择的货位在货位字典中使用！\",\"state\":\"false\"}";
				}
			}
			//System.out.println("22222222222222222");
			matLocationTypeMapper.deleteBatch(entityList);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 deleteBatchMatLocationType\"}";

		}	
	}
	
	/**
	 * @Description 
	 * 添加04401 货位分类字典<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addOrUpdate(Map<String,Object> entityMap)throws DataAccessException{
		
		//获取对象04401 货位分类字典
		MatLocationType matLocationType = queryByCode(entityMap);

		if (matLocationType != null) {

			int state = matLocationTypeMapper.update(entityMap);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		
		try {
			
			int state = matLocationTypeMapper.add(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addOrUpdateMatLocationType\"}";

		}
		
	}
	
	
	/**
	 * @Description 
	 * 获取对象04401 货位分类字典<BR> 
	 * @param  entityMap<BR>
	 *  参数为主键
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public <T>T queryByCode(Map<String,Object> entityMap)throws DataAccessException{
		return matLocationTypeMapper.queryByCode(entityMap);
	}
	
	/**
	 * @Description 
	 * 获取04401 货位分类字典<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return MatLocationType
	 * @throws DataAccessException
	*/
	@Override
	public <T>T queryByUniqueness(Map<String,Object> entityMap)throws DataAccessException{
		return matLocationTypeMapper.queryByUniqueness(entityMap);
	}


	@Override
	public List<?> queryExists(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}
	
}
