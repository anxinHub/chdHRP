
/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 package com.chd.hrp.hr.serviceImpl.sysstruc;

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
import com.chd.hrp.hr.dao.sysstruc.HrFunMapper;
import com.chd.hrp.hr.dao.sysstruc.HrFunTypeMapper;
import com.chd.hrp.hr.entity.record.HrEmpType;
import com.chd.hrp.hr.entity.sysstruc.HrFun;
import com.chd.hrp.hr.entity.sysstruc.HrFunParaMethod;
import com.chd.hrp.hr.entity.sysstruc.HrFunType;
import com.chd.hrp.hr.service.sysstruc.HrFunTypeService;
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
 


@Service("hrFunTypeService")
public class HrFunTypeServiceImpl implements HrFunTypeService {

	private static Logger logger = Logger.getLogger(HrFunTypeServiceImpl.class);
	//引入DAO操作
	@Resource(name = "hrFunTypeMapper")
	private final HrFunTypeMapper hrFunTypeMapper = null;
	
	@Resource(name = "hrFunMapper")
	private final HrFunMapper hrFunMapper = null;
    
	/**
	 * @Description 
	 * 添加9909 绩效函数分类表<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addPrmFunType(Map<String,Object> entityMap)throws DataAccessException{
		try {
			//获取对象9909 绩效函数分类表
			HrFunType prmFunType = hrFunTypeMapper.queryByCodeOrName(entityMap);
			if(prmFunType!=null){
				if (prmFunType.getType_code().toString().equals(entityMap.get("type_code"))) {
					return "{\"error\":\"分类编码重复，请重新添加.\"}";
				}
				if (prmFunType.getType_name().toString().equals(entityMap.get("type_name"))) {
					return "{\"error\":\"分类名称重复，请重新添加.\"}";
				}
			}
			
			int state = hrFunTypeMapper.addPrmFunType(entityMap);
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";
		}catch (Exception e) {
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
			hrFunTypeMapper.addBatchPrmFunType(entityList);
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";
		}catch (Exception e) {
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
			HrFunType prmFunType = hrFunTypeMapper.queryByCodeOrName(entityMap);
			if(prmFunType!=null){
				if("1".equals(entityMap.get("is_change").toString())){
					if (prmFunType.getType_name().toString().equals(entityMap.get("type_name"))) {
						return "{\"error\":\"分类名称重复，请重新添加.\"}";
					}
				}
			}
			
			int state = hrFunTypeMapper.updatePrmFunType(entityMap);
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";
		}catch (Exception e) {
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
			
		  hrFunTypeMapper.updateBatchPrmFunType(entityList);
			
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
			
			int state = hrFunTypeMapper.deletePrmFunType(entityMap);
			
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
	public String deleteBatchPrmFunType(List<HrFunType> entityList)throws DataAccessException{
		
		try {
			List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
			for(HrFunType hr : entityList){
				Map<String,Object> map = new HashMap<String,Object>();
				map.put("group_id", hr.getGroup_id());
				map.put("hos_id", hr.getHos_id());
				map.put("copy_code", hr.getCopy_code());
				map.put("type_code", hr.getType_code());
				list.add(map);
			}
			hrFunTypeMapper.deleteBatchPrmFunType(list);
			
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
			
			List<HrFunType> list = hrFunTypeMapper.queryPrmFunType(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<HrFunType> list = hrFunTypeMapper.queryPrmFunType(entityMap, rowBounds);
			
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
	public HrFunType queryPrmFunTypeByCode(Map<String,Object> entityMap)throws DataAccessException{
		return hrFunTypeMapper.queryPrmFunTypeByCode(entityMap);
	}
	
	/**
	 * 函数分类 树
	 */
	@Override
	public String queryPrmFunTree(Map<String, Object> entityMap) throws DataAccessException {

			StringBuilder json = new StringBuilder();
			List<HrFun> list = hrFunMapper.queryPrmFun(entityMap);
			List<HrFunType> typeList = hrFunTypeMapper.queryPrmFunType(entityMap);
			// 拼接SQL
			json.append("[");
			
			for(HrFunType prmFunType : typeList){
				json.append("{'id':'" + prmFunType.getType_code() + "', 'pId':'0', 'name':'" + prmFunType.getType_name() + "'},");
			}
			for( int i =0 ;i < list.size(); i++){
				HrFun hft = list.get(i);
				json.append("{'id':'" + hft.getFun_code() + "', 'pId':'"+hft.getType_code()+"', 'name':'" + hft.getFun_name() + "'},");
			}
			
			if(list.size() != 0){
				json.setCharAt(json.length() - 1, ']');
			}else{
				json.append("]");
			}

	        return json.toString();  
	}
	
}
