/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/

package com.chd.hrp.sys.serviceImpl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.SessionManager;
import com.chd.base.SysPage;
import com.chd.base.util.ChdJson;
import com.chd.base.util.StringTool;
import com.chd.hrp.sys.dao.DeptKindMapper;
import com.chd.hrp.sys.dao.DeptMapper;
import com.chd.hrp.sys.entity.Dept;
import com.chd.hrp.sys.entity.DeptKind;
import com.chd.hrp.sys.service.DeptKindService;
import com.github.pagehelper.PageInfo;

/**
* @Title. @Description.
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


@Service("deptKindService")
public class DeptKindServiceImpl implements DeptKindService {

	private static Logger logger = Logger.getLogger(DeptKindServiceImpl.class);
	
	@Resource(name = "deptKindMapper")
	private final DeptKindMapper deptKindMapper = null;
    
	@Resource(name = "deptMapper")
	private final DeptMapper deptMapper = null;
	
	/**
	 * @Description 添加DeptKind
	 * @param DeptKind entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addDeptKind(Map<String,Object> entityMap)throws DataAccessException{
		entityMap.put("group_id", SessionManager.getGroupId());
		entityMap.put("hos_id", SessionManager.getHosId());
		List<DeptKind> deptKind = deptKindMapper.queryDeptKindByName(entityMap);

		if (deptKind != null || deptKind.size() > 0) {

			for (DeptKind deptKind2 : deptKind) {
				if(deptKind2.getKind_code().equals(entityMap.get("kind_code").toString())){
					return "{\"error\":\"编码：" + entityMap.get("kind_code").toString() + "重复.\"}";
				}
				if(deptKind2.getKind_name().equals(entityMap.get("kind_name").toString())){
					return "{\"error\":\"名称：" + entityMap.get("kind_name").toString() + "重复.\"}";
				}
			}
			

		}
		entityMap.put("spell_code", StringTool.toPinyinShouZiMu(entityMap.get("kind_name").toString()));
		entityMap.put("wbx_code", StringTool.toWuBi(entityMap.get("kind_name").toString()));
		try {
			
			deptKindMapper.addDeptKind(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码 addDeptKind\"}";

		}

	}
	
	/**
	 * @Description 批量添加DeptKind
	 * @param  DeptKind entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addBatchDeptKind(List<Map<String,Object>> entityList)throws DataAccessException{

		try {
			
			deptKindMapper.addBatchDeptKind(entityList);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码 addBatchDeptKind\"}";

		}
	}
	
	/**
	 * @Description 查询DeptKind分页
	 * @param  entityMap RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String queryDeptKind(Map<String,Object> entityMap) throws DataAccessException{
		
		entityMap.put("group_id", SessionManager.getGroupId());
		entityMap.put("hos_id", SessionManager.getHosId());
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		 
		 if (sysPage.getTotal()==-1){
				
			 List<DeptKind> list = deptKindMapper.queryDeptKind(entityMap);
				
				return ChdJson.toJson(list);
				
			
			}else{
				
				RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
				
				List<DeptKind> list = deptKindMapper.queryDeptKind(entityMap, rowBounds);
				
		        PageInfo page = new PageInfo(list); 
				
				return ChdJson.toJson(list, page.getTotal());
				
			}
		
	}
	
	/**
	 * @Description 查询DeptKindByCode
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public DeptKind queryDeptKindByCode(Map<String,Object> entityMap)throws DataAccessException{
		
		return deptKindMapper.queryDeptKindByCode(entityMap);
		
	}
	
	/**
	 * @Description 批量删除DeptKind
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String deleteBatchDeptKind(List<Map<String,Object>> entityList)throws DataAccessException{

		try {
				for(Map<String,Object> item : entityList){
					List<Dept> list = deptMapper.queryDept(item);
					
					if(list.size() >0 ){
						return "{\"error\":\"所选部门分类正被部门架构使用，无法删除.\",\"state\":\"true\"}";
					}
				}
				int state = deptKindMapper.deleteBatchDeptKind(entityList);
				return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码  deleteBatchDeptKind\"}";

		}
		
	}
	
		/**
	 * @Description 删除DeptKind
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
  public String deleteDeptKind(Map<String, Object> entityMap) throws DataAccessException {
		
		try {
				deptKindMapper.deleteDeptKind(entityMap);
				return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);
			
			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码  deleteDeptKind\"}";

		}
    }
	
	/**
	 * @Description 更新DeptKind
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateDeptKind(Map<String,Object> entityMap)throws DataAccessException{

		try {
			List<DeptKind> deptKind = deptKindMapper.queryDeptKindByCodeName(entityMap);

			if (deptKind != null || deptKind.size() > 0) {

				for (DeptKind deptKind2 : deptKind) {
					if(deptKind2.getKind_name().equals(entityMap.get("kind_name").toString())){
						return "{\"error\":\"名称：" + entityMap.get("kind_name").toString() + "重复.\"}";
					}
				}
				

			}
			entityMap.put("spell_code", StringTool.toPinyinShouZiMu(entityMap.get("kind_name").toString()));
			entityMap.put("wbx_code", StringTool.toWuBi(entityMap.get("kind_name").toString()));
			deptKindMapper.updateDeptKind(entityMap);

			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updateDeptKind\"}";

		}
	}
	
	/**
	 * @Description 批量更新DeptKind
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateBatchDeptKind(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {

			deptKindMapper.updateBatchDeptKind(entityList);

			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updateDeptKind\"}";

		}
		
	}
	
	/**
	 * @Description 导入DeptKind
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String importDeptKind(Map<String,Object> entityMap)throws DataAccessException{

		try {
			
			return "{\"msg\":\"导入成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"导入失败 数据库异常 请联系管理员! 错误编码  import{className}\"}";

		}
	}

	//同步平台分类
	@Override
	public String quneryPlatformKind(Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		 
		 if (sysPage.getTotal()==-1){
				
			List<DeptKind> list = deptKindMapper.quneryPlatformKind(entityMap);
				
			return ChdJson.toJson(list);
				
			
			}else{
				
				RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
				
				List<DeptKind> list = deptKindMapper.quneryPlatformKind(entityMap, rowBounds);
				
		        PageInfo page = new PageInfo(list); 
				
				return ChdJson.toJson(list, page.getTotal());
				
			}
	}
}
