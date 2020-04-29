
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
import com.chd.hrp.prm.dao.PrmEmpKpiSectionMapper;
import com.chd.hrp.prm.entity.PrmEmpKpiSection;
import com.chd.hrp.prm.service.PrmEmpKpiSectionService;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Description:
 * 0405 职工指标区间法参数表
 * @Table:
 * PRM_EMP_KPI_SECTION
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 


@Service("prmEmpKpiSectionService")
public class PrmEmpKpiSectionServiceImpl implements PrmEmpKpiSectionService {

	private static Logger logger = Logger.getLogger(PrmEmpKpiSectionServiceImpl.class);
	//引入DAO操作
	@Resource(name = "prmEmpKpiSectionMapper")
	private final PrmEmpKpiSectionMapper prmEmpKpiSectionMapper = null;
    
	/**
	 * @Description 
	 * 添加0405 职工指标区间法参数表<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addPrmEmpKpiSection(Map<String,Object> entityMap)throws DataAccessException{
		
		//获取对象0405 职工指标区间法参数表
		PrmEmpKpiSection prmEmpKpiSection = queryPrmEmpKpiSectionByCode(entityMap);

		if (prmEmpKpiSection != null) {

			return "{\"error\":\"数据重复,请重新添加.\"}";

		}
		
		try {
			
			int state = prmEmpKpiSectionMapper.addPrmEmpKpiSection(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addPrmEmpKpiSection\"}";

		}
		
	}
	/**
	 * @Description 
	 * 批量添加0405 职工指标区间法参数表<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addBatchPrmEmpKpiSection(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
			prmEmpKpiSectionMapper.addBatchPrmEmpKpiSection(entityList);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addBatchPrmEmpKpiSection\"}";

		}
		
	}
	
		/**
	 * @Description 
	 * 更新0405 职工指标区间法参数表<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updatePrmEmpKpiSection(Map<String,Object> entityMap)throws DataAccessException{
		
		try {
			
		  int state = prmEmpKpiSectionMapper.updatePrmEmpKpiSection(entityMap);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 updatePrmEmpKpiSection\"}";

		}	
		
	}
	/**
	 * @Description 
	 * 批量更新0405 职工指标区间法参数表<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateBatchPrmEmpKpiSection(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
		  prmEmpKpiSectionMapper.updateBatchPrmEmpKpiSection(entityList);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 updateBatchPrmEmpKpiSection\"}";

		}	
		
	}
	/**
	 * @Description 
	 * 删除0405 职工指标区间法参数表<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
    public String deletePrmEmpKpiSection(Map<String, Object> entityMap) throws DataAccessException {
    	
    try {
			
			int state = prmEmpKpiSectionMapper.deletePrmEmpKpiSection(entityMap);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 deletePrmEmpKpiSection\"}";

		}	
		
  }
    
	/**
	 * @Description 
	 * 批量删除0405 职工指标区间法参数表<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String deleteBatchPrmEmpKpiSection(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
			prmEmpKpiSectionMapper.deleteBatchPrmEmpKpiSection(entityList);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 deleteBatchPrmEmpKpiSection\"}";

		}	
	}
	
	/**
	 * @Description 
	 * 查询结果集0405 职工指标区间法参数表<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String queryPrmEmpKpiSection(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<PrmEmpKpiSection> list = prmEmpKpiSectionMapper.queryPrmEmpKpiSection(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<PrmEmpKpiSection> list = prmEmpKpiSectionMapper.queryPrmEmpKpiSection(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
		
	}
	
	/**
	 * @Description 
	 * 获取对象0405 职工指标区间法参数表<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public PrmEmpKpiSection queryPrmEmpKpiSectionByCode(Map<String,Object> entityMap)throws DataAccessException{
		return prmEmpKpiSectionMapper.queryPrmEmpKpiSectionByCode(entityMap);
	}
	
}
