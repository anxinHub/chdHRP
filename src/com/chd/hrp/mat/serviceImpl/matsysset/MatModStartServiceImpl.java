/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/

package com.chd.hrp.mat.serviceImpl.matsysset;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
 
import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.SysPage;
import com.chd.base.util.ChdJson;
import com.chd.hrp.sys.dao.ModStartMapper;
import com.chd.hrp.sys.entity.ModStart;
import com.chd.hrp.sys.service.ModStartService;

/**
* @Title. @Description.
* 系统启用<BR> 
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


@Service("matModStartService")
public class MatModStartServiceImpl implements ModStartService {

	private static Logger logger = Logger.getLogger(MatModStartServiceImpl.class);
	
	@Resource(name = "modStartMapper")
	private final ModStartMapper modStartMapper = null;
	
	/**
	 * @Description 
	 * 系统启用<BR> 添加ModStart
	 * @param ModStart entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addModStart(Map<String,Object> entityMap)throws DataAccessException{
		//物流系统中的子系统启用不需要该判断
		if("04".equals(entityMap.get("mod_code").toString())){
			List<Map<String,Object>> matInvDictList = modStartMapper.queryMatInvDict(entityMap);
			
			if(matInvDictList.size()>0 ){
				
				return "{\"error\":\"系统有数据存在，启用失败\",\"state\":\"true\"}";
			}
		}
		
		try {
			
			ModStart modStart = modStartMapper.existsModStartByCode(entityMap);
			
			if (modStart != null) {
					return "{\"error\":\"不能重复启用.\",\"state\":\"true\"}";
					
				} else {
					modStart = queryModStartByCode(entityMap);
					if(modStart != null){
						modStartMapper.updateModStart(entityMap);
					}else{
						modStartMapper.addModStart(entityMap);
					}
					return "{\"msg\":\"启用成功.\",\"state\":\"true\"}";
				}
			
		} catch (Exception e) {
			
			logger.error(e.getMessage(), e);

			return "{\"error\":\"系统异常 请联系管理员! 错误编码 addModStart\"}";
		}

	}
	
	/**
	 * @Description 
	 * 系统启用<BR> 批量添加ModStart
	 * @param  ModStart entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addBatchModStart(List<Map<String,Object>> entityList)throws DataAccessException{

		try {
			
			modStartMapper.addBatchModStart(entityList);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码 addBatchModStart\"}";

		}
	}
	
	/**
	 * @Description 
	 * 系统启用<BR> 查询ModStart分页
	 * @param  entityMap RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String queryModStart(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
		return ChdJson.toJson(modStartMapper.queryModStart(entityMap, rowBounds), sysPage.getTotal());
		
	}
	
	/**
	 * @Description 
	 * 系统启用<BR> 查询ModStartByCode
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public ModStart queryModStartByCode(Map<String,Object> entityMap)throws DataAccessException{
		
		return modStartMapper.queryModStartByCode(entityMap);
		
	}
	
	/**
	 * @Description 
	 * 系统启用<BR> 批量删除ModStart
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String deleteBatchModStart(List<Map<String,Object>> entityList)throws DataAccessException{

		try {

				int state = modStartMapper.deleteBatchModStart(entityList);
				return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码  deleteBatchModStart\"}";

		}
		
	}
	
	/**
	 * @Description 
	 * 系统启用<BR> 删除ModStart
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
  public String deleteModStart(Map<String, Object> entityMap) throws DataAccessException {
		
		try {
				modStartMapper.deleteModStart(entityMap);
				return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码  deleteModStart\"}";

		}
    }
	
	/**
	 * @Description 
	 * 系统启用<BR> 更新ModStart
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateModStart(Map<String,Object> entityMap)throws DataAccessException{

		try {

			modStartMapper.updateModStart(entityMap);

			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updateModStart\"}";

		}
	}
	
	/**
	 * @Description 
	 * 系统启用<BR> 批量更新ModStart
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateBatchModStart(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {

			modStartMapper.updateBatchModStart(entityList);

			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updateModStart\"}";

		}
		
	}
	
	/**
	 * @Description 
	 * 系统启用<BR> 导入ModStart
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String importModStart(Map<String,Object> entityMap)throws DataAccessException{

		try {

			

			return "{\"msg\":\"导入成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"导入失败 数据库异常 请联系管理员! 错误编码  import{className}\"}";

		}
	}

	@Override
	public String querySysModStart(Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}
}
