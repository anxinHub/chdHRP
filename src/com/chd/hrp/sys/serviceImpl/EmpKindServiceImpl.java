/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/

package com.chd.hrp.sys.serviceImpl;

import java.util.*;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.SessionManager;
import com.chd.base.SysPage;
import com.chd.base.util.ChdJson;
import com.chd.base.util.JsonListBeanUtil;
import com.chd.base.util.StringTool;
import com.chd.hrp.sys.dao.EmpKindMapper;
import com.chd.hrp.sys.dao.EmpMapper;
import com.chd.hrp.sys.entity.Copy;
import com.chd.hrp.sys.entity.Emp;
import com.chd.hrp.sys.entity.EmpKind;
import com.chd.hrp.sys.service.EmpKindService;
import com.github.pagehelper.PageInfo;

/**
* @Title. @Description.
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


@Service("empKindService")
public class EmpKindServiceImpl implements EmpKindService {

	private static Logger logger = Logger.getLogger(EmpKindServiceImpl.class);
	
	@Resource(name = "empKindMapper")
	private final EmpKindMapper empKindMapper = null;
    @Resource(name = "empMapper")
	private final EmpMapper empMapper = null; 
	/**
	 * @Description 添加EmpKind
	 * @param EmpKind entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addEmpKind(Map<String,Object> entityMap)throws DataAccessException{
		entityMap.put("group_id", SessionManager.getGroupId());
		entityMap.put("hos_id", SessionManager.getHosId());
		entityMap.put("spell_code", StringTool.toPinyinShouZiMu(entityMap.get("kind_name").toString()));
		entityMap.put("wbx_code", StringTool.toWuBi(entityMap.get("kind_name").toString()));
		EmpKind empKind = queryEmpKindByCode(entityMap);

		if (empKind != null) {

			return "{\"error\":\"编码：" + empKind.getKind_code().toString() + "已存在.\"}";

		}
		
		try {
			
			empKindMapper.addEmpKind(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码 addEmpKind\"}";

		}

	}
	
	/**
	 * @Description 批量添加EmpKind
	 * @param  EmpKind entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addBatchEmpKind(List<Map<String,Object>> entityList)throws DataAccessException{

		try {
			
			empKindMapper.addBatchEmpKind(entityList);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码 addBatchEmpKind\"}";

		}
	}
	
	/**
	 * @Description 查询EmpKind分页
	 * @param  entityMap RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String queryEmpKind(Map<String,Object> entityMap) throws DataAccessException{
		
		entityMap.put("group_id", SessionManager.getGroupId());
		entityMap.put("hos_id", SessionManager.getHosId());
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		 if (sysPage.getTotal()==-1){
				
			 List<EmpKind> list = empKindMapper.queryEmpKind(entityMap);
				
				return ChdJson.toJson(list);
				
			
			}else{
				
				RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
				
				List<EmpKind> list = empKindMapper.queryEmpKind(entityMap, rowBounds);
				
		        PageInfo page = new PageInfo(list); 
				
				return ChdJson.toJson(list, page.getTotal());
				
			}
		
	}
	
	/**
	 * @Description 查询EmpKindByCode
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public EmpKind queryEmpKindByCode(Map<String,Object> entityMap)throws DataAccessException{
		
		return empKindMapper.queryEmpKindByCode(entityMap);
		
	}
	
	/**
	 * @Description 批量删除EmpKind
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String deleteBatchEmpKind(List<Map<String,Object>> entityList)throws DataAccessException{

		try {
				for(Map<String,Object> item : entityList){
					List<Emp> list = empMapper.queryEmp(item);
					if(list.size()>0){
						return "{\"error\":\"数据正在使用，无法删除.\",\"state\":\"true\"}";
					}
				}
				int state = empKindMapper.deleteBatchEmpKind(entityList);
				return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码  deleteBatchEmpKind\"}";

		}
		
	}
	
		/**
	 * @Description 删除EmpKind
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
  public String deleteEmpKind(Map<String, Object> entityMap) throws DataAccessException {
		
		try {
				List<Emp> list = empMapper.queryEmp(entityMap);
				if(list.size()>0){
					return "{\"error\":\"数据正在使用，无法删除.\",\"state\":\"true\"}";
				}
				empKindMapper.deleteEmpKind(entityMap);
				
				
				return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码  deleteEmpKind\"}";

		}
    }
	
	/**
	 * @Description 更新EmpKind
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateEmpKind(Map<String,Object> entityMap)throws DataAccessException{

		try {
			entityMap.put("spell_code", StringTool.toPinyinShouZiMu(entityMap.get("kind_name").toString()));
			entityMap.put("wbx_code", StringTool.toWuBi(entityMap.get("kind_name").toString()));
			empKindMapper.updateEmpKind(entityMap);

			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updateEmpKind\"}";

		}
	}
	
	/**
	 * @Description 批量更新EmpKind
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateBatchEmpKind(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {

			empKindMapper.updateBatchEmpKind(entityList);

			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updateEmpKind\"}";

		}
		
	}
	
	/**
	 * @Description 导入EmpKind
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String importEmpKind(Map<String,Object> entityMap)throws DataAccessException{

		try {

			

			return "{\"msg\":\"导入成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"导入失败 数据库异常 请联系管理员! 错误编码  import{className}\"}";

		}
	}
}
