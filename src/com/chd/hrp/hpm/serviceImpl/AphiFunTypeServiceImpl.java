
/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 package com.chd.hrp.hpm.serviceImpl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.SysPage;
import com.chd.base.util.ChdJson;
import com.chd.hrp.hpm.dao.AphiFunMapper;
import com.chd.hrp.hpm.dao.AphiFunTypeMapper;
import com.chd.hrp.hpm.entity.AphiFun;
import com.chd.hrp.hpm.entity.AphiFunType;
import com.chd.hrp.hpm.service.AphiFunTypeService;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Description:
 * 9909 绩效函数分类表
 * @Table:
 * PRM_FUN_TYPE
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 


@Service("aphiFunTypeService")
public class AphiFunTypeServiceImpl implements AphiFunTypeService {

	private static Logger logger = Logger.getLogger(AphiFunTypeServiceImpl.class);
	//引入DAO操作
	@Resource(name = "aphiFunTypeMapper")
	private final AphiFunTypeMapper aphiFunTypeMapper = null;
	
	@Resource(name = "aphiFunMapper")
	private final AphiFunMapper aphiFunMapper = null;
    
	/**
	 * @Description 
	 * 添加9909 绩效函数分类表<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addPrmFunType(Map<String,Object> entityMap)throws DataAccessException{
		
		//获取对象9909 绩效函数分类表
		AphiFunType prmFunType = queryPrmFunTypeByCode(entityMap);

		if (prmFunType != null) {

			return "{\"error\":\"数据重复,请重新添加.\"}";

		}
		
		try {
			
			int state = aphiFunTypeMapper.addPrmFunType(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addPrmFunType\"}";

		}
		
	}
	/**
	 * @Description 
	 * 批量添加9909 绩效函数分类表<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addBatchPrmFunType(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
			aphiFunTypeMapper.addBatchPrmFunType(entityList);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addBatchPrmFunType\"}";

		}
		
	}
	
		/**
	 * @Description 
	 * 更新9909 绩效函数分类表<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updatePrmFunType(Map<String,Object> entityMap)throws DataAccessException{
		
		try {
			
		  int state = aphiFunTypeMapper.updatePrmFunType(entityMap);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 updatePrmFunType\"}";

		}	
		
	}
	/**
	 * @Description 
	 * 批量更新9909 绩效函数分类表<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateBatchPrmFunType(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
		  aphiFunTypeMapper.updateBatchPrmFunType(entityList);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 updateBatchPrmFunType\"}";

		}	
		
	}
	/**
	 * @Description 
	 * 删除9909 绩效函数分类表<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
    public String deletePrmFunType(Map<String, Object> entityMap) throws DataAccessException {
    	
    try {
			
			int state = aphiFunTypeMapper.deletePrmFunType(entityMap);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 deletePrmFunType\"}";

		}	
		
  }
    
	/**
	 * @Description 
	 * 批量删除9909 绩效函数分类表<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String deleteBatchPrmFunType(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
			aphiFunTypeMapper.deleteBatchPrmFunType(entityList);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 deleteBatchPrmFunType\"}";

		}	
	}
	
	/**
	 * @Description 
	 * 查询结果集9909 绩效函数分类表<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String queryPrmFunType(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<AphiFunType> list = aphiFunTypeMapper.queryPrmFunType(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<AphiFunType> list = aphiFunTypeMapper.queryPrmFunType(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
		
	}
	
	/**
	 * @Description 
	 * 获取对象9909 绩效函数分类表<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public AphiFunType queryPrmFunTypeByCode(Map<String,Object> entityMap)throws DataAccessException{
		return aphiFunTypeMapper.queryPrmFunTypeByCode(entityMap);
	}
	
	/**
	 * 函数分类 树
	 */
	@Override
	public String queryPrmFunTree(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
			StringBuilder json = new StringBuilder();
			
			List<AphiFun> list = aphiFunMapper.queryPrmFun(entityMap);
			
			List<AphiFunType> typeList = aphiFunTypeMapper.queryPrmFunType(entityMap);
			
			if (list.size() == 0) {

				json.append("{Rows:[]}");

				return json.toString();

			}
			
			// 拼接SQL
			
			
			json.append("{Rows:[");
			
			for(AphiFunType prmFunType : typeList){
				json.append("{");
				
				json.append("\"id\":\""+prmFunType.getType_code()+"\",\"pid\":\"-1\","+"\"text\":"+"\""+prmFunType.getType_name()+"\"");

				json.append("},");
				
			}
			for( int i =0 ;i < list.size(); i++){
				
				AphiFun hft = list.get(i);
				
				json.append("{");
				
				json.append("\"pid\":\""+hft.getType_code()+"\",\"id\":\""+hft.getFun_code()+"\","+"\"text\":"+"\""+hft.getFun_name()+"\"");

				json.append("},");
				
			}
			
			if(list.size() != 0){
				
				json.setCharAt(json.length() - 1, ']');
			}else{
				json.append("]");
			}
			json.append("}");

			
	        return json.toString();  
			//return JsonListBeanUtil.listToJson();
			
	    
	}
	
}
