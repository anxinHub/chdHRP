/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.ass.serviceImpl.bid;

import java.util.*;
import javax.annotation.Resource;
import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import com.chd.base.SysPage;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.hrp.ass.dao.bid.AssBidPlanMapMapper;
import com.chd.hrp.ass.entity.bid.AssBidPlanMap;
import com.chd.hrp.ass.service.bid.AssBidPlanMapService;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Description:
 * 050401 招标与计划关系表
 * @Table:
 * ASS_BID_PLAN_MAP
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 


@Service("assBidPlanMapService")
public class AssBidPlanMapServiceImpl implements AssBidPlanMapService {

	private static Logger logger = Logger.getLogger(AssBidPlanMapServiceImpl.class);
	//引入DAO操作
	@Resource(name = "assBidPlanMapMapper")
	private final AssBidPlanMapMapper assBidPlanMapMapper = null;
    
	/**
	 * @Description 
	 * 添加050401 招标与计划关系表<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addAssBidPlanMap(Map<String,Object> entityMap)throws DataAccessException{
		
		//获取对象050401 招标与计划关系表
		AssBidPlanMap assBidPlanMap = queryAssBidPlanMapByCode(entityMap);

		if (assBidPlanMap != null) {

			return "{\"error\":\"数据重复,请重新添加.\"}";

		}
		
		try {
			
			int state = assBidPlanMapMapper.addAssBidPlanMap(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}
		
	}
	/**
	 * @Description 
	 * 批量添加050401 招标与计划关系表<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addBatchAssBidPlanMap(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
			assBidPlanMapMapper.addBatchAssBidPlanMap(entityList);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}
		
	}
	
		/**
	 * @Description 
	 * 更新050401 招标与计划关系表<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateAssBidPlanMap(Map<String,Object> entityMap)throws DataAccessException{
		
		try {
			
		  int state = assBidPlanMapMapper.updateAssBidPlanMap(entityMap);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}	
		
	}
	/**
	 * @Description 
	 * 批量更新050401 招标与计划关系表<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateBatchAssBidPlanMap(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
		  assBidPlanMapMapper.updateBatchAssBidPlanMap(entityList);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}	
		
	}
	/**
	 * @Description 
	 * 删除050401 招标与计划关系表<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
    public String deleteAssBidPlanMap(Map<String, Object> entityMap) throws DataAccessException {
    	
    try {
			
			int state = assBidPlanMapMapper.deleteAssBidPlanMap(entityMap);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}	
		
  }
    
	/**
	 * @Description 
	 * 批量删除050401 招标与计划关系表<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String deleteBatchAssBidPlanMap(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
			assBidPlanMapMapper.deleteBatchAssBidPlanMap(entityList);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}	
	}
	
	/**
	 * @Description 
	 * 添加050401 招标与计划关系表<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addOrUpdateAssBidPlanMap(Map<String,Object> entityMap)throws DataAccessException{
		/**
		* 备注 先判断是否存在 存在即更新不存在则添加<br>
		* 在判断是否存在时可根据实际情况进行修改传递的参数进行判断<br>
		* 判断是否名称相同 判断是否 编码相同 等一系列规则
		*/
		//判断是否存在对象050401 招标与计划关系表
		Map<String, Object> mapVo=new HashMap<String, Object>();
		mapVo.put("group_id",entityMap.get("group_id"));
		mapVo.put("hos_id",entityMap.get("hos_id"));
    	mapVo.put("copy_code", entityMap.get("copy_code"));
    	mapVo.put("acct_year", entityMap.get("acct_year"));
		
		List<AssBidPlanMap> list = assBidPlanMapMapper.queryAssBidPlanMapExists(mapVo);
		
		if (list != null) {

			int state = assBidPlanMapMapper.updateAssBidPlanMap(entityMap);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		
		try {
			
			int state = assBidPlanMapMapper.addAssBidPlanMap(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}
		
	}
	/**
	 * @Description 
	 * 查询结果集050401 招标与计划关系表<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String queryAssBidPlanMap(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<AssBidPlanMap> list = assBidPlanMapMapper.queryAssBidPlanMap(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<AssBidPlanMap> list = assBidPlanMapMapper.queryAssBidPlanMap(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
		
	}
	
	/**
	 * @Description 
	 * 获取对象050401 招标与计划关系表<BR> 
	 * @param  entityMap<BR>
	 *  参数为主键
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public AssBidPlanMap queryAssBidPlanMapByCode(Map<String,Object> entityMap)throws DataAccessException{
		return assBidPlanMapMapper.queryAssBidPlanMapByCode(entityMap);
	}
	
	/**
	 * @Description 
	 * 获取050401 招标与计划关系表<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return AssBidPlanMap
	 * @throws DataAccessException
	*/
	@Override
	public AssBidPlanMap queryAssBidPlanMapByUniqueness(Map<String,Object> entityMap)throws DataAccessException{
		return assBidPlanMapMapper.queryAssBidPlanMapByUniqueness(entityMap);
	}
	
	/**
	 * @Description 
	 * 获取050401 招标与计划关系表<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return List<AssBidPlanMap>
	 * @throws DataAccessException
	*/
	@Override
	public List<AssBidPlanMap> queryAssBidPlanMapExists(Map<String,Object> entityMap)throws DataAccessException{
		return assBidPlanMapMapper.queryAssBidPlanMapExists(entityMap);
	}
	
}
