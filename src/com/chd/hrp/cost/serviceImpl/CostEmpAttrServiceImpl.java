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
import com.chd.hrp.cost.dao.CostEmpAttrMapper;
import com.chd.hrp.cost.entity.CostEmpAttr;
import com.chd.hrp.cost.service.CostEmpAttrService;
import com.github.pagehelper.PageInfo;

/**
* @Title. @Description.
* 成本_职工字典表<BR> 
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


@Service("costEmpAttrService")
public class CostEmpAttrServiceImpl implements CostEmpAttrService {

	private static Logger logger = Logger.getLogger(CostEmpAttrServiceImpl.class);
	
	@Resource(name = "costEmpAttrMapper")
	private final CostEmpAttrMapper costEmpAttrMapper = null;
    
	/**
	 * @Description 
	 * 成本_职工字典表<BR> 添加CostEmpAttr
	 * @param CostEmpAttr entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addCostEmpAttr(Map<String,Object> entityMap)throws DataAccessException{
		
		CostEmpAttr costEmp = costEmpAttrMapper.queryEmpByCode(entityMap);
		
		CostEmpAttr costEmpAttr = queryCostEmpAttrByCode(entityMap);

		if (costEmpAttr != null) {

			return "{\"error\":\"编码：" + entityMap.get("emp_id").toString() + "重复.\"}";

		}
		
		try {
			
			costEmpAttrMapper.addCostEmpAttr(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码 addCostEmpAttr\"}";

		}

	}
	
	/**
	 * @Description 
	 * 成本_职工字典表<BR> 批量添加CostEmpAttr
	 * @param  CostEmpAttr entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addBatchCostEmpAttr(List<Map<String,Object>> entityList)throws DataAccessException{

		try {
			
			costEmpAttrMapper.addBatchCostEmpAttr(entityList);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码 addBatchCostEmpAttr\"}";

		}
	}
	
	/**
	 * @Description 
	 * 成本_职工字典表<BR> 查询CostEmpAttr分页
	 * @param  entityMap RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String queryCostEmpAttr(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		sysPage = (SysPage) entityMap.get("sysPage");
		if (sysPage.getTotal()==-1){
			List<CostEmpAttr> list = costEmpAttrMapper.queryCostEmpAttr(entityMap);
			return ChdJson.toJson(list);
		}else{
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			List<CostEmpAttr> list = costEmpAttrMapper.queryCostEmpAttr(entityMap, rowBounds);
			PageInfo page = new PageInfo(list);
			return ChdJson.toJson(list, page.getTotal());
		}
		
	}
	@Override
	public List<Map<String, Object>> queryCostEmpAttrPrint(Map<String, Object> entityMap) throws DataAccessException {

		
		List<Map<String, Object>> list=costEmpAttrMapper.queryCostEmpAttrPrint(entityMap);
		
		return list;

	}
	/**
	 * @Description 
	 * 成本_职工字典表<BR> 查询CostEmpAttrByCode
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public CostEmpAttr queryCostEmpAttrByCode(Map<String,Object> entityMap)throws DataAccessException{
		
		return costEmpAttrMapper.queryCostEmpAttrByCode(entityMap);
		
	}
	
	/**
	 * @Description 
	 * 成本_职工字典表<BR> 批量删除CostEmpAttr
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String deleteBatchCostEmpAttr(List<Map<String,Object>> entityList)throws DataAccessException{

		try {

				int state = costEmpAttrMapper.deleteBatchCostEmpAttr(entityList);
				return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码  deleteBatchCostEmpAttr\"}";

		}
		
	}
	
	/**
	 * @Description 
	 * 成本_职工字典表<BR> 删除CostEmpAttr
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
  public String deleteCostEmpAttr(Map<String, Object> entityMap) throws DataAccessException {
		
		try {
				costEmpAttrMapper.deleteCostEmpAttr(entityMap);
				return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码  deleteCostEmpAttr\"}";

		}
    }
	
	/**
	 * @Description 
	 * 成本_职工字典表<BR> 更新CostEmpAttr
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateCostEmpAttr(Map<String,Object> entityMap)throws DataAccessException{

		try {

			costEmpAttrMapper.updateCostEmpAttr(entityMap);

			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updateCostEmpAttr\"}";

		}
	}
	
	/**
	 * @Description 
	 * 成本_职工字典表<BR> 批量更新CostEmpAttr
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateBatchCostEmpAttr(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {

			costEmpAttrMapper.updateBatchCostEmpAttr(entityList);

			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updateCostEmpAttr\"}";

		}
		
	}

	@Override
	public CostEmpAttr queryEmpByCode(Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		return costEmpAttrMapper.queryEmpByCode(entityMap);
	}
}
