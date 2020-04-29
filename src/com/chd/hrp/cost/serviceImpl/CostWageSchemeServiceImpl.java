/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/

package com.chd.hrp.cost.serviceImpl;

import java.util.*;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.SysPage;
import com.chd.base.util.ChdJson;
import com.chd.hrp.cost.dao.CostWageSchemeMapper;
import com.chd.hrp.cost.entity.CostWageScheme;
import com.chd.hrp.cost.service.CostWageSchemeService;
import com.github.pagehelper.PageInfo;

/**
* @Title. @Description.
* 职工工资查询方案<BR> 
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


@Service("costWageSchemeService")
public class CostWageSchemeServiceImpl implements CostWageSchemeService {

	private static Logger logger = Logger.getLogger(CostWageSchemeServiceImpl.class);
	
	@Resource(name = "costWageSchemeMapper")
	private final CostWageSchemeMapper costWageSchemeMapper = null;
    
	/**
	 * @Description 
	 * 职工工资查询方案<BR> 添加CostWageScheme
	 * @param CostWageScheme entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addCostWageScheme(Map<String,Object> entityMap)throws DataAccessException{
		
		CostWageScheme costWageScheme = queryCostWageSchemeByCode(entityMap);

		if (costWageScheme != null) {

			return "{\"error\":\"编码：" + entityMap.get("item_code").toString() + "重复.\"}";

		}
		
		try {
			
			costWageSchemeMapper.addCostWageScheme(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码 addCostWageScheme\"}";

		}

	}
	
	/**
	 * @Description 
	 * 职工工资查询方案<BR> 批量添加CostWageScheme
	 * @param  CostWageScheme entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addBatchCostWageScheme(List<Map<String,Object>> entityList)throws DataAccessException{

		try {
			
			costWageSchemeMapper.addBatchCostWageScheme(entityList);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码 addBatchCostWageScheme\"}";

		}
	}
	
	/**
	 * @Description 
	 * 职工工资查询方案<BR> 查询CostWageScheme分页
	 * @param  entityMap RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String queryCostWageScheme(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<CostWageScheme> list = costWageSchemeMapper.queryCostWageScheme(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<CostWageScheme> list = costWageSchemeMapper.queryCostWageScheme(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
		
	}
	
	/**
	 * @Description 
	 * 职工工资查询方案<BR> 查询CostWageSchemeByCode
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public CostWageScheme queryCostWageSchemeByCode(Map<String,Object> entityMap)throws DataAccessException{
		
		return costWageSchemeMapper.queryCostWageSchemeByCode(entityMap);
		
	}
	
	/**
	 * @Description 
	 * 职工工资查询方案<BR> 批量删除CostWageScheme
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String deleteBatchCostWageScheme(List<Map<String,Object>> entityList)throws DataAccessException{

		try {

			int state = costWageSchemeMapper.deleteBatchCostWageScheme(entityList);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码  deleteBatchCostWageScheme\"}";

		}
		
	}
	
	/**
	 * @Description 
	 * 职工工资查询方案<BR> 删除CostWageScheme
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
  public String deleteCostWageScheme(Map<String, Object> entityMap) throws DataAccessException {
		
		try {
			costWageSchemeMapper.deleteCostWageScheme(entityMap);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码  deleteCostWageScheme\"}";

		}
    }
	
	/**
	 * @Description 
	 * 职工工资查询方案<BR> 更新CostWageScheme
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateCostWageScheme(Map<String,Object> entityMap)throws DataAccessException{

		try {

			costWageSchemeMapper.updateCostWageScheme(entityMap);

			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updateCostWageScheme\"}";

		}
	}
	
	/**
	 * @Description 
	 * 职工工资查询方案<BR> 批量更新CostWageScheme
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateBatchCostWageScheme(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {

			costWageSchemeMapper.updateBatchCostWageScheme(entityList);

			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updateCostWageScheme\"}";

		}
		
	}

	@Override
	public CostWageScheme CostWageSequence() throws DataAccessException {
		
		return costWageSchemeMapper.queryCostWageSequence();
	}
}
