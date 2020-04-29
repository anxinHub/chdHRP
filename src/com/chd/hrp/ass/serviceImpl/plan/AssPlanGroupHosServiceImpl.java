/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.ass.serviceImpl.plan;

import java.util.*;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import com.chd.base.SysPage;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.hrp.ass.dao.plan.AssPlanDeptMapper;
import com.chd.hrp.ass.dao.plan.AssPlanGroupHosMapper;
import com.chd.hrp.ass.entity.plan.AssPlanGroupHos;
import com.chd.hrp.ass.service.plan.AssPlanGroupHosService;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Description:
 * 050302 集团与医院购置计划关系表
 * @Table:
 * ASS_PLAN_GROUP_HOS
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 


@Service("assPlanGroupHosService")
public class AssPlanGroupHosServiceImpl implements AssPlanGroupHosService {

	private static Logger logger = Logger.getLogger(AssPlanGroupHosServiceImpl.class);
	//引入DAO操作
	@Resource(name = "assPlanGroupHosMapper")
	private final AssPlanGroupHosMapper assPlanGroupHosMapper = null;
    
	/**
	 * @Description 
	 * 添加050302 集团与医院购置计划关系表<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addAssPlanGroupHos(Map<String,Object> entityMap)throws DataAccessException{
		
		try {
			
			int state = assPlanGroupHosMapper.addAssPlanGroupHos(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}
		
	}
	/**
	 * @Description 
	 * 批量添加050302 集团与医院购置计划关系表<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addBatchAssPlanGroupHos(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
			assPlanGroupHosMapper.addBatchAssPlanGroupHos(entityList);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}
		
	}
	/**
	 * @Description 
	 * 删除050302 集团与医院购置计划关系表<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
    public String deleteAssPlanGroupHos(Map<String, Object> entityMap) throws DataAccessException {
    	
    try {
			
			int state = assPlanGroupHosMapper.deleteAssPlanGroupHos(entityMap);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}	
		
  }
    
	/**
	 * @Description 
	 * 批量删除050302 集团与医院购置计划关系表<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String deleteBatchAssPlanGroupHos(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
			assPlanGroupHosMapper.deleteBatchAssPlanGroupHos(entityList);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}	
	}
	
	/**
	 * @Description 购置计划打印05009
	 * @param entityMap
	 * @return String
	 * @throws DataAccessException
	 */
	@Override
	public Map<String,Object> queryAssPlanGroupDY(Map<String, Object> map)throws DataAccessException {
		
		try{
			Map<String,Object> reMap=new HashMap<String,Object>();
			WebApplicationContext context = ContextLoader.getCurrentWebApplicationContext();
			AssPlanGroupHosMapper assPlanGroupHosMapper = (AssPlanGroupHosMapper)context.getBean("assPlanGroupHosMapper");
			if("1".equals(String.valueOf(map.get("p_num")))){
				//集团置购计划打印模板主表
				List<Map<String,Object>> mainList=assPlanGroupHosMapper.queryAssPlanGroupBatch(map);
						
				//集团置购计划打印模板从表
				List<Map<String,Object>> detailList=assPlanGroupHosMapper.queryAssPlanGroup_detail(map);
				
				reMap.put("main", mainList);
				reMap.put("detail", detailList);
			}else{
				Map<String,Object> mainList=assPlanGroupHosMapper.queryAssPlanGroupByPrint(map);
				
				//集团置购打印模板从表
				List<Map<String,Object>> detailList=assPlanGroupHosMapper.queryAssPlanGroup_detail(map);
				reMap.put("main", mainList);
				reMap.put("detail", detailList);
			}
			
		
			
			return reMap;
		}catch(Exception e){
			logger.error(e.getMessage(),e);
			throw new SysException(e.getMessage());
		}
		
	}
	
	
	/**
	 * @Description 
	 * 查询结果集050302 集团与医院购置计划关系表<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String queryAssPlanGroupHos(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<AssPlanGroupHos> list = assPlanGroupHosMapper.queryAssPlanGroupHos(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<AssPlanGroupHos> list = assPlanGroupHosMapper.queryAssPlanGroupHos(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
		
	}
	@Override
	public List<AssPlanGroupHos> queryAssPlanGroupHosList(Map<String, Object> entityMap) throws DataAccessException {
		return assPlanGroupHosMapper.queryAssPlanGroupHos(entityMap);
	}
	/**
	 * 打印状态
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	@Override
	public List<String> queryPlanGroupDeptState(Map<String, Object> mapVo)
			throws DataAccessException {
	
		return assPlanGroupHosMapper.queryPlanGroupDeptState(mapVo);
	}
}
