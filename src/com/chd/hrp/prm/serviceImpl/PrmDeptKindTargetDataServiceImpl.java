
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
import com.chd.hrp.prm.dao.PrmDeptKindTargetDataMapper;
import com.chd.hrp.prm.entity.PrmDeptKindTargetData;
import com.chd.hrp.prm.service.PrmDeptKindTargetDataService;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Description:
 * 0313 科室类别绩效指标数据表
 * @Table:
 * PRM_DEPT_KIND_TARGET_DATA
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 


@Service("prmDeptKindTargetDataService")
public class PrmDeptKindTargetDataServiceImpl implements PrmDeptKindTargetDataService {

	private static Logger logger = Logger.getLogger(PrmDeptKindTargetDataServiceImpl.class);
	//引入DAO操作
	@Resource(name = "prmDeptKindTargetDataMapper")
	private final PrmDeptKindTargetDataMapper prmDeptKindTargetDataMapper = null;
    
	/**
	 * @Description 
	 * 添加0313 科室类别绩效指标数据表<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addPrmDeptKindTargetData(Map<String,Object> entityMap)throws DataAccessException{
		
		//获取对象0313 科室类别绩效指标数据表
		PrmDeptKindTargetData prmDeptKindTargetData = queryPrmDeptKindTargetDataByCode(entityMap);

		if (prmDeptKindTargetData != null) {

			return "{\"error\":\"数据重复,请重新添加.\"}";

		}
		
		try {
			
			int state = prmDeptKindTargetDataMapper.addPrmDeptKindTargetData(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addPrmDeptKindTargetData\"}";

		}
		
	}
	/**
	 * @Description 
	 * 批量添加0313 科室类别绩效指标数据表<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addBatchPrmDeptKindTargetData(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
			prmDeptKindTargetDataMapper.addBatchPrmDeptKindTargetData(entityList);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addBatchPrmDeptKindTargetData\"}";

		}
		
	}
	
		/**
	 * @Description 
	 * 更新0313 科室类别绩效指标数据表<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updatePrmDeptKindTargetData(Map<String,Object> entityMap)throws DataAccessException{
		
		try {
			
		  int state = prmDeptKindTargetDataMapper.updatePrmDeptKindTargetData(entityMap);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 updatePrmDeptKindTargetData\"}";

		}	
		
	}
	/**
	 * @Description 
	 * 批量更新0313 科室类别绩效指标数据表<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateBatchPrmDeptKindTargetData(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
		  prmDeptKindTargetDataMapper.updateBatchPrmDeptKindTargetData(entityList);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 updateBatchPrmDeptKindTargetData\"}";

		}	
		
	}
	/**
	 * @Description 
	 * 删除0313 科室类别绩效指标数据表<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
    public String deletePrmDeptKindTargetData(Map<String, Object> entityMap) throws DataAccessException {
    	
    try {
			
			int state = prmDeptKindTargetDataMapper.deletePrmDeptKindTargetData(entityMap);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 deletePrmDeptKindTargetData\"}";

		}	
		
  }
    
	/**
	 * @Description 
	 * 批量删除0313 科室类别绩效指标数据表<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String deleteBatchPrmDeptKindTargetData(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
			prmDeptKindTargetDataMapper.deleteBatchPrmDeptKindTargetData(entityList);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 deleteBatchPrmDeptKindTargetData\"}";

		}	
	}
	
	/**
	 * @Description 
	 * 查询结果集0313 科室类别绩效指标数据表<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String queryPrmDeptKindTargetData(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<PrmDeptKindTargetData> list = prmDeptKindTargetDataMapper.queryPrmDeptKindTargetData(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<PrmDeptKindTargetData> list = prmDeptKindTargetDataMapper.queryPrmDeptKindTargetData(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
		
	}
	
	/**
	 * @Description 
	 * 获取对象0313 科室类别绩效指标数据表<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public PrmDeptKindTargetData queryPrmDeptKindTargetDataByCode(Map<String,Object> entityMap)throws DataAccessException{
		return prmDeptKindTargetDataMapper.queryPrmDeptKindTargetDataByCode(entityMap);
	}
	
}
