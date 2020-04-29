/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.mat.serviceImpl.info.basic;

import java.util.*;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.SysPage;
import com.chd.base.util.ChdJson;
import com.chd.hrp.mat.dao.info.basic.HosPackageMapper;
import com.chd.hrp.mat.entity.HosPackage;
import com.chd.hrp.mat.service.info.basic.HosPackageService;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Description:
 * 04117 包装单位表
 * @Table:
 * HOS_PACKAGE
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 


@Service("hosPackageService")
public class HosPackageServiceImpl implements HosPackageService {

	private static Logger logger = Logger.getLogger(HosPackageServiceImpl.class);
	//引入DAO操作
	@Resource(name = "hosPackageMapper")
	private final HosPackageMapper hosPackageMapper = null;
    
	/**
	 * @Description 
	 * 添加04117 包装单位表<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addHosPackage(Map<String,Object> entityMap)throws DataAccessException{
		
		//获取对象04117 包装单位表
		
		List<HosPackage> list2 = hosPackageMapper.queryHosPackageByNameCode(entityMap);

		if (list2.size()>0) {

			return "{\"error\":\"包装编码已存在,请重新添加.\"}";

		}
		
		
		List<HosPackage> list = hosPackageMapper.queryHosPackageByName(entityMap);

		if (list.size()>0) {

			return "{\"error\":\"包装名称已存在,请重新添加.\"}";

		}
		
		try {
			
			int state = hosPackageMapper.addHosPackage(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addHosPackage\"}";

		}
		
	}
	/**
	 * @Description 
	 * 批量添加04117 包装单位表<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addBatchHosPackage(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
			hosPackageMapper.addBatchHosPackage(entityList);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addBatchHosPackage\"}";

		}
		
	}
	
		/**
	 * @Description 
	 * 更新04117 包装单位表<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateHosPackage(Map<String,Object> entityMap)throws DataAccessException{
		
		List<HosPackage> list = hosPackageMapper.queryHosPackageByName(entityMap);

		if (list.size()>0) {

			return "{\"error\":\"包装名称已存在,请重新添加.\"}";

		}
		try {
			
		  int state = hosPackageMapper.updateHosPackage(entityMap);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"更新失败 数据库异常 请联系管理员! 方法 updateHosPackage\"}";

		}	
		
	}
	/**
	 * @Description 
	 * 批量更新04117 包装单位表<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateBatchHosPackage(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
		  hosPackageMapper.updateBatchHosPackage(entityList);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 updateBatchHosPackage\"}";

		}	
		
	}
	/**
	 * @Description 
	 * 删除04117 包装单位表<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
    public String deleteHosPackage(Map<String, Object> entityMap) throws DataAccessException {
    	
    try {
			
			int state = hosPackageMapper.deleteHosPackage(entityMap);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 deleteHosPackage\"}";

		}	
		
  }
    
	/**
	 * @Description 
	 * 批量删除04117 包装单位表<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String deleteBatchHosPackage(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
			hosPackageMapper.deleteBatchHosPackage(entityList);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 deleteBatchHosPackage\"}";

		}	
	}
	
	/**
	 * @Description 
	 * 添加04117 包装单位表<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addOrUpdateHosPackage(Map<String,Object> entityMap)throws DataAccessException{
		
		//获取对象04117 包装单位表
		HosPackage hosPackage = queryHosPackageByCode(entityMap);

		if (hosPackage != null) {

			int state = hosPackageMapper.updateHosPackage(entityMap);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		
		try {
			
			int state = hosPackageMapper.addHosPackage(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addOrUpdateHosPackage\"}";

		}
		
	}
	/**
	 * @Description 
	 * 查询结果集04117 包装单位表<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String queryHosPackage(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<HosPackage> list = hosPackageMapper.queryHosPackage(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<HosPackage> list = hosPackageMapper.queryHosPackage(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
		
	}
	
	/**
	 * @Description 
	 * 获取对象04117 包装单位表<BR> 
	 * @param  entityMap<BR>
	 *  参数为主键
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public HosPackage queryHosPackageByCode(Map<String,Object> entityMap)throws DataAccessException{
		return hosPackageMapper.queryHosPackageByCode(entityMap);
	}
	
	/**
	 * @Description 
	 * 获取04117 包装单位表<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return HosPackage
	 * @throws DataAccessException
	*/
	@Override
	public HosPackage queryHosPackageByUniqueness(Map<String,Object> entityMap)throws DataAccessException{
		return hosPackageMapper.queryHosPackageByUniqueness(entityMap);
	}
	/**
	 * 根据 包装单位名称 查询 包装单位记录 （判断包装单位名称是否已使用）
	 * @param mapVo
	 * @return
	 */
	public List<HosPackage> queryHosPackageByName(Map<String, Object> mapVo) {
		return hosPackageMapper.queryHosPackageByName(mapVo);
	}
	
}
