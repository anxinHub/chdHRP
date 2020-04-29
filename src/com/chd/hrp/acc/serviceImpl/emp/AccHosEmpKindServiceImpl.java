/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/

package com.chd.hrp.acc.serviceImpl.emp;

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
import com.chd.hrp.acc.dao.emp.AccHosEmpKindMapper;
import com.chd.hrp.acc.entity.AccHosEmpKind;
import com.chd.hrp.acc.service.emp.AccHosEmpKindService;
import com.github.pagehelper.PageInfo;

/**
* @Title. @Description.
* 职工分类<BR> 
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


@Service("accHosEmpKindService")
public class AccHosEmpKindServiceImpl implements AccHosEmpKindService {

	private static Logger logger = Logger.getLogger(AccHosEmpKindServiceImpl.class);
	
	@Resource(name = "accHosEmpKindMapper")
	private final AccHosEmpKindMapper accHosEmpKindMapper = null;
    
	/**
	 * @Description 
	 * 职工分类<BR> 添加AccHosEmpKind
	 * @param AccHosEmpKind entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addAccHosEmpKind(Map<String,Object> entityMap)throws DataAccessException{
		
		AccHosEmpKind accHosEmpKind = queryAccHosEmpKindByCode(entityMap);

		if (accHosEmpKind != null) {

			return "{\"error\":\"编码：" + entityMap.get("kind_code").toString() + "重复.\"}";

		}
		
		try {
			
			accHosEmpKindMapper.addAccHosEmpKind(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码 addAccHosEmpKind\"}";

		}

	}
	
	/**
	 * @Description 
	 * 职工分类<BR> 批量添加AccHosEmpKind
	 * @param  AccHosEmpKind entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addBatchAccHosEmpKind(List<Map<String,Object>> entityList)throws DataAccessException{

		try {
			
			accHosEmpKindMapper.addBatchAccHosEmpKind(entityList);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码 addBatchAccHosEmpKind\"}";

		}
	}
	
	/**
	 * @Description 
	 * 职工分类<BR> 查询AccHosEmpKind分页
	 * @param  entityMap RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String queryAccHosEmpKind(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage)entityMap.get("sysPage");
		
		if(sysPage.getTotal() == -1){
			
			List<AccHosEmpKind> list = accHosEmpKindMapper.queryAccHosEmpKind(entityMap);
			
			return ChdJson.toJson(list);
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<AccHosEmpKind> list = accHosEmpKindMapper.queryAccHosEmpKind(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list,page.getTotal());
		}
		
	}
	
	/**
	 * @Description 
	 * 职工分类<BR> 查询AccHosEmpKindByCode
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public AccHosEmpKind queryAccHosEmpKindByCode(Map<String,Object> entityMap)throws DataAccessException{
		
		return accHosEmpKindMapper.queryAccHosEmpKindByCode(entityMap);
		
	}
	
	/**
	 * @Description 
	 * 职工分类<BR> 批量删除AccHosEmpKind
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String deleteBatchAccHosEmpKind(List<Map<String,Object>> entityList)throws DataAccessException{

		try {

				int state = accHosEmpKindMapper.deleteBatchAccHosEmpKind(entityList);
				
				return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码  deleteBatchAccHosEmpKind\"}";

		}
		
	}
	
	/**
	 * @Description 
	 * 职工分类<BR> 删除AccHosEmpKind
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
  public String deleteAccHosEmpKind(Map<String, Object> entityMap) throws DataAccessException {
		
		try {
			accHosEmpKindMapper.deleteAccHosEmpKind(entityMap);
				
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码  deleteAccHosEmpKind\"}";

		}
    }
	
	/**
	 * @Description 
	 * 职工分类<BR> 更新AccHosEmpKind
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateAccHosEmpKind(Map<String,Object> entityMap)throws DataAccessException{

		try {

			accHosEmpKindMapper.updateAccHosEmpKind(entityMap);

			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updateAccHosEmpKind\"}";

		}
	}
	
	/**
	 * @Description 
	 * 职工分类<BR> 批量更新AccHosEmpKind
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateBatchAccHosEmpKind(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {

			accHosEmpKindMapper.updateBatchAccHosEmpKind(entityList);

			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updateAccHosEmpKind\"}";

		}
		
	}
	
	@Override
	public List<Map<String,Object>> queryAccHosEmpKindPrint(Map<String,Object> entityMap) throws DataAccessException{
		
		entityMap.put("group_id", SessionManager.getGroupId());
	       
		entityMap.put("hos_id", SessionManager.getHosId());
        
		entityMap.put("copy_code", SessionManager.getCopyCode());
		
		if(entityMap.get("kind_name") != null){
		//用来转换大写
			entityMap.put("kind_name",entityMap.get("kind_name").toString().toUpperCase());
		
		}
		
		List<Map<String,Object>> list = accHosEmpKindMapper.queryAccHosEmpKindPrint(entityMap);
		
		return list;
		
	}
	
}
