
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
import com.chd.hrp.prm.dao.PrmKpiNatureMapper;
import com.chd.hrp.prm.entity.PrmKpiNature;
import com.chd.hrp.prm.service.PrmKpiNatureService;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Description:
 * 0207 KPI指标性质字典表
 * @Table:
 * PRM_KPI_NATURE
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 


@Service("prmKpiNatureService")
public class PrmKpiNatureServiceImpl implements PrmKpiNatureService {

	private static Logger logger = Logger.getLogger(PrmKpiNatureServiceImpl.class);
	//引入DAO操作
	@Resource(name = "prmKpiNatureMapper")
	private final PrmKpiNatureMapper prmKpiNatureMapper = null;
    
	/**
	 * @Description 
	 * 添加0207 KPI指标性质字典表<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addPrmKpiNature(Map<String,Object> entityMap)throws DataAccessException{
		
		//获取对象0207 KPI指标性质字典表
		PrmKpiNature prmKpiNature = queryPrmKpiNatureByCode(entityMap);

		if (prmKpiNature != null) {

			return "{\"error\":\"数据重复,请重新添加.\"}";

		}
		
		try {
			
			int state = prmKpiNatureMapper.addPrmKpiNature(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addPrmKpiNature\"}";

		}
		
	}
	/**
	 * @Description 
	 * 批量添加0207 KPI指标性质字典表<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addBatchPrmKpiNature(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
			prmKpiNatureMapper.addBatchPrmKpiNature(entityList);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addBatchPrmKpiNature\"}";

		}
		
	}
	
		/**
	 * @Description 
	 * 更新0207 KPI指标性质字典表<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updatePrmKpiNature(Map<String,Object> entityMap)throws DataAccessException{
		
		try {
			
		  int state = prmKpiNatureMapper.updatePrmKpiNature(entityMap);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 updatePrmKpiNature\"}";

		}	
		
	}
	/**
	 * @Description 
	 * 批量更新0207 KPI指标性质字典表<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateBatchPrmKpiNature(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
		  prmKpiNatureMapper.updateBatchPrmKpiNature(entityList);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 updateBatchPrmKpiNature\"}";

		}	
		
	}
	/**
	 * @Description 
	 * 删除0207 KPI指标性质字典表<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
    public String deletePrmKpiNature(Map<String, Object> entityMap) throws DataAccessException {
    	
    try {
			
			int state = prmKpiNatureMapper.deletePrmKpiNature(entityMap);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 deletePrmKpiNature\"}";

		}	
		
  }
    
	/**
	 * @Description 
	 * 批量删除0207 KPI指标性质字典表<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String deleteBatchPrmKpiNature(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
			prmKpiNatureMapper.deleteBatchPrmKpiNature(entityList);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 deleteBatchPrmKpiNature\"}";

		}	
	}
	
	/**
	 * @Description 
	 * 查询结果集0207 KPI指标性质字典表<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String queryPrmKpiNature(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<PrmKpiNature> list = prmKpiNatureMapper.queryPrmKpiNature(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<PrmKpiNature> list = prmKpiNatureMapper.queryPrmKpiNature(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
		
	}
	
	/**
	 * @Description 
	 * 获取对象0207 KPI指标性质字典表<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public PrmKpiNature queryPrmKpiNatureByCode(Map<String,Object> entityMap)throws DataAccessException{
		return prmKpiNatureMapper.queryPrmKpiNatureByCode(entityMap);
	}
	
}
