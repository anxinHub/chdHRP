
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
import com.chd.hrp.prm.dao.PrmDeptKpiSectionMapper;
import com.chd.hrp.prm.entity.PrmDeptKpiSection;
import com.chd.hrp.prm.service.PrmDeptKpiSectionService;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Description:
 * 0305 科室指标区间法参数表
 * @Table:
 * PRM_DEPT_KPI_SECTION
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 


@Service("prmDeptKpiSectionService")
public class PrmDeptKpiSectionServiceImpl implements PrmDeptKpiSectionService {

	private static Logger logger = Logger.getLogger(PrmDeptKpiSectionServiceImpl.class);
	//引入DAO操作
	@Resource(name = "prmDeptKpiSectionMapper")
	private final PrmDeptKpiSectionMapper prmDeptKpiSectionMapper = null;
    
	/**
	 * @Description 
	 * 添加0305 科室指标区间法参数表<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addPrmDeptKpiSection(Map<String,Object> entityMap)throws DataAccessException{
		
		//获取对象0305 科室指标区间法参数表
		PrmDeptKpiSection prmDeptKpiSection = queryPrmDeptKpiSectionByCode(entityMap);

		if (prmDeptKpiSection != null) {

			return "{\"error\":\"数据重复,请重新添加.\"}";

		}
		
		try {
			
			int state = prmDeptKpiSectionMapper.addPrmDeptKpiSection(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addPrmDeptKpiSection\"}";

		}
		
	}
	/**
	 * @Description 
	 * 批量添加0305 科室指标区间法参数表<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addBatchPrmDeptKpiSection(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
			prmDeptKpiSectionMapper.addBatchPrmDeptKpiSection(entityList);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addBatchPrmDeptKpiSection\"}";

		}
		
	}
	
		/**
	 * @Description 
	 * 更新0305 科室指标区间法参数表<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updatePrmDeptKpiSection(Map<String,Object> entityMap)throws DataAccessException{
		
		try {
			
		  int state = prmDeptKpiSectionMapper.updatePrmDeptKpiSection(entityMap);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 updatePrmDeptKpiSection\"}";

		}	
		
	}
	/**
	 * @Description 
	 * 批量更新0305 科室指标区间法参数表<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateBatchPrmDeptKpiSection(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
		  prmDeptKpiSectionMapper.updateBatchPrmDeptKpiSection(entityList);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 updateBatchPrmDeptKpiSection\"}";

		}	
		
	}
	/**
	 * @Description 
	 * 删除0305 科室指标区间法参数表<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
    public String deletePrmDeptKpiSection(Map<String, Object> entityMap) throws DataAccessException {
    	
    try {
			
			int state = prmDeptKpiSectionMapper.deletePrmDeptKpiSection(entityMap);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 deletePrmDeptKpiSection\"}";

		}	
		
  }
    
	/**
	 * @Description 
	 * 批量删除0305 科室指标区间法参数表<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String deleteBatchPrmDeptKpiSection(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
			prmDeptKpiSectionMapper.deleteBatchPrmDeptKpiSection(entityList);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 deleteBatchPrmDeptKpiSection\"}";

		}	
	}
	
	/**
	 * @Description 
	 * 查询结果集0305 科室指标区间法参数表<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String queryPrmDeptKpiSection(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<PrmDeptKpiSection> list = prmDeptKpiSectionMapper.queryPrmDeptKpiSection(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<PrmDeptKpiSection> list = prmDeptKpiSectionMapper.queryPrmDeptKpiSection(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
		
	}
	
	/**
	 * @Description 
	 * 获取对象0305 科室指标区间法参数表<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public PrmDeptKpiSection queryPrmDeptKpiSectionByCode(Map<String,Object> entityMap)throws DataAccessException{
		return prmDeptKpiSectionMapper.queryPrmDeptKpiSectionByCode(entityMap);
	}
	
}
