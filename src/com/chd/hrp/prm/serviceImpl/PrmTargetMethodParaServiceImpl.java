
/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 package com.chd.hrp.prm.serviceImpl;

import java.util.*;
import javax.annotation.Resource;
import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import com.chd.base.SysPage;
import com.chd.base.util.ChdJson;
import com.chd.hrp.prm.dao.PrmTargetMethodParaMapper;
import com.chd.hrp.prm.entity.PrmTargetMethodPara;
import com.chd.hrp.prm.service.PrmTargetMethodParaService;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Description:
 * 9903 指标取值方法参数表
 * @Table:
 * PRM_TARGET_METHOD_PARA
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 


@Service("prmTargetMethodParaService")
public class PrmTargetMethodParaServiceImpl implements PrmTargetMethodParaService {

	private static Logger logger = Logger.getLogger(PrmTargetMethodParaServiceImpl.class);
	//引入DAO操作
	@Resource(name = "prmTargetMethodParaMapper")
	private final PrmTargetMethodParaMapper prmTargetMethodParaMapper = null;
    
	/**
	 * @Description 
	 * 添加9903 指标取值方法参数表<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addPrmTargetMethodPara(Map<String,Object> entityMap)throws DataAccessException{
		
		//获取对象9903 指标取值方法参数表
		PrmTargetMethodPara prmTargetMethodPara = queryPrmTargetMethodParaByCode(entityMap);

		if (prmTargetMethodPara != null) {

			return "{\"error\":\"数据重复,请重新添加.\"}";

		}
		
		try {
			
			int state = prmTargetMethodParaMapper.addPrmTargetMethodPara(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addPrmTargetMethodPara\"}";

		}
		
	}
	@Override
	public String addPrmTargetMethod(Map<String,Object> entityMap)throws DataAccessException{
		
		//获取对象9903 指标取值方法参数表
		/*
		PrmTargetMethodPara prmTargetMethodPara = queryPrmTargetMethodParaByCode(entityMap);

		if (prmTargetMethodPara != null) {

			return "{\"error\":\"数据重复,请重新添加.\"}";

		}
		*/
		try {
			
			int state = prmTargetMethodParaMapper.addPrmTargetMethod(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addPrmTargetMethodPara\"}";

		}
		
	}
	/**
	 * @Description 
	 * 批量添加9903 指标取值方法参数表<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addBatchPrmTargetMethodPara(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
			prmTargetMethodParaMapper.addBatchPrmTargetMethodPara(entityList);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addBatchPrmTargetMethodPara\"}";

		}
		
	}
	
		/**
	 * @Description 
	 * 更新9903 指标取值方法参数表<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updatePrmTargetMethodPara(Map<String,Object> entityMap)throws DataAccessException{
		
		try {
			
		  int state = prmTargetMethodParaMapper.updatePrmTargetMethodPara(entityMap);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 updatePrmTargetMethodPara\"}";

		}	
		
	}
	/**
	 * @Description 
	 * 批量更新9903 指标取值方法参数表<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateBatchPrmTargetMethodPara(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
		  prmTargetMethodParaMapper.updateBatchPrmTargetMethodPara(entityList);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 updateBatchPrmTargetMethodPara\"}";

		}	
		
	}
	/**
	 * @Description 
	 * 删除9903 指标取值方法参数表<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
    public String deletePrmTargetMethodPara(Map<String, Object> entityMap) throws DataAccessException {
    	
    try {
			
			int state = prmTargetMethodParaMapper.deletePrmTargetMethodPara(entityMap);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 deletePrmTargetMethodPara\"}";

		}	
		
  }
    
	/**
	 * @Description 
	 * 批量删除9903 指标取值方法参数表<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String deleteBatchPrmTargetMethodPara(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
			prmTargetMethodParaMapper.deleteBatchPrmTargetMethodPara(entityList);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 deleteBatchPrmTargetMethodPara\"}";

		}	
	}
	
	/**
	 * @Description 
	 * 查询结果集9903 指标取值方法参数表<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String queryPrmTargetMethodPara(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<PrmTargetMethodPara> list = prmTargetMethodParaMapper.queryPrmTargetMethodPara(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<PrmTargetMethodPara> list = prmTargetMethodParaMapper.queryPrmTargetMethodPara(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
		
	}
	
	
	
	/**
	 * @Description 
	 * 获取对象9903 指标取值方法参数表<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public PrmTargetMethodPara queryPrmTargetMethodParaByCode(Map<String,Object> entityMap)throws DataAccessException{
		return prmTargetMethodParaMapper.queryPrmTargetMethodParaByCode(entityMap);
	}
	
}
