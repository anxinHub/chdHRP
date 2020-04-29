
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
import com.chd.hrp.prm.dao.PrmGradeParaMapper;
import com.chd.hrp.prm.entity.PrmGradePara;
import com.chd.hrp.prm.service.PrmGradeParaService;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Description:
 * 0204 指标评分方法参数表
 * @Table:
 * PRM_GRADE_PARA
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 


@Service("prmGradeParaService")
public class PrmGradeParaServiceImpl implements PrmGradeParaService {

	private static Logger logger = Logger.getLogger(PrmGradeParaServiceImpl.class);
	//引入DAO操作
	@Resource(name = "prmGradeParaMapper")
	private final PrmGradeParaMapper prmGradeParaMapper = null;
    
	/**
	 * @Description 
	 * 添加0204 指标评分方法参数表<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addPrmGradePara(Map<String,Object> entityMap)throws DataAccessException{
		
		//获取对象0204 指标评分方法参数表
		PrmGradePara prmGradePara = queryPrmGradeParaByCode(entityMap);

		if (prmGradePara != null) {

			return "{\"error\":\"数据重复,请重新添加.\"}";

		}
		
		try {
			
			int state = prmGradeParaMapper.addPrmGradePara(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addPrmGradePara\"}";

		}
		
	}
	/**
	 * @Description 
	 * 批量添加0204 指标评分方法参数表<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addBatchPrmGradePara(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
			prmGradeParaMapper.addBatchPrmGradePara(entityList);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addBatchPrmGradePara\"}";

		}
		
	}
	
		/**
	 * @Description 
	 * 更新0204 指标评分方法参数表<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updatePrmGradePara(Map<String,Object> entityMap)throws DataAccessException{
		
		try {
			
		  int state = prmGradeParaMapper.updatePrmGradePara(entityMap);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 updatePrmGradePara\"}";

		}	
		
	}
	/**
	 * @Description 
	 * 批量更新0204 指标评分方法参数表<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateBatchPrmGradePara(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
		  prmGradeParaMapper.updateBatchPrmGradePara(entityList);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 updateBatchPrmGradePara\"}";

		}	
		
	}
	/**
	 * @Description 
	 * 删除0204 指标评分方法参数表<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
    public String deletePrmGradePara(Map<String, Object> entityMap) throws DataAccessException {
    	
    try {
			
			int state = prmGradeParaMapper.deletePrmGradePara(entityMap);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 deletePrmGradePara\"}";

		}	
		
  }
    
	/**
	 * @Description 
	 * 批量删除0204 指标评分方法参数表<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String deleteBatchPrmGradePara(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
			prmGradeParaMapper.deleteBatchPrmGradePara(entityList);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 deleteBatchPrmGradePara\"}";

		}	
	}
	
	/**
	 * @Description 
	 * 查询结果集0204 指标评分方法参数表<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String queryPrmGradePara(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<PrmGradePara> list = prmGradeParaMapper.queryPrmGradePara(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<PrmGradePara> list = prmGradeParaMapper.queryPrmGradePara(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
		
	}
	
	/**
	 * @Description 
	 * 获取对象0204 指标评分方法参数表<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public PrmGradePara queryPrmGradeParaByCode(Map<String,Object> entityMap)throws DataAccessException{
		return prmGradeParaMapper.queryPrmGradeParaByCode(entityMap);
	}
	
}
