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
import com.chd.hrp.cost.dao.CostIassetCostRelaMapper;
import com.chd.hrp.cost.entity.CostIassetCostRela;
import com.chd.hrp.cost.service.CostIassetCostRelaService;
import com.github.pagehelper.PageInfo;

/**
* @Title. @Description.
* 无形资产分类与成本项目的对应关系<BR> 
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


@Service("costIassetCostRelaService")
public class CostIassetCostRelaServiceImpl implements CostIassetCostRelaService {
 
	private static Logger logger = Logger.getLogger(CostIassetCostRelaServiceImpl.class);
	
	@Resource(name = "costIassetCostRelaMapper")
	private final CostIassetCostRelaMapper costIassetCostRelaMapper = null;
    
	/**
	 * @Description 
	 * 无形资产分类与成本项目的对应关系<BR> 添加CostIassetCostRela
	 * @param CostIassetCostRela entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addCostIassetCostRela(Map<String,Object> entityMap)throws DataAccessException{
		/*
		 * 2016/10/26 lxj 
		 * 注释掉此处 解决添加操作时报异常的问题
		 * 原因是执行queryCostIassetCostRelaByCode时,查询出多条结果 条件不满足
		 * */
	
		entityMap.put("is_flag", "1");
		
		CostIassetCostRela costIassetCostRela = queryCostIassetCostRelaByCode(entityMap);

		if (costIassetCostRela != null) {

			return "{\"error\":\"已经存在此年月对应关系.\"}";

		}
		
		try {
			
			costIassetCostRelaMapper.addCostIassetCostRela(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码 addCostIassetCostRela\"}";

		}

	}
	
	/**
	 * @Description 
	 * 无形资产分类与成本项目的对应关系<BR> 批量添加CostIassetCostRela
	 * @param  CostIassetCostRela entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addBatchCostIassetCostRela(List<Map<String,Object>> entityList)throws DataAccessException{

		try {
			
			costIassetCostRelaMapper.addBatchCostIassetCostRela(entityList);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码 addBatchCostIassetCostRela\"}";

		}
	}
	
	/**
	 * @Description 
	 * 无形资产分类与成本项目的对应关系<BR> 查询CostIassetCostRela分页
	 * @param  entityMap RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String queryCostIassetCostRela(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		sysPage = (SysPage) entityMap.get("sysPage");
		if (sysPage.getTotal()==-1){
			List<CostIassetCostRela> list = costIassetCostRelaMapper.queryCostIassetCostRela(entityMap);
			return ChdJson.toJson(list);
		}else{
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			List<CostIassetCostRela> list = costIassetCostRelaMapper.queryCostIassetCostRela(entityMap, rowBounds);
			PageInfo page = new PageInfo(list);
			return ChdJson.toJson(list, page.getTotal());
		}
		
	}
	@Override
	public List<Map<String, Object>> queryCostIassetCostRelaPrint(Map<String, Object> entityMap) throws DataAccessException {

		
		List<Map<String, Object>> list=costIassetCostRelaMapper.queryCostIassetCostRelaPrint(entityMap);
		
		return list;

	}
	/**
	 * @Description 
	 * 无形资产分类与成本项目的对应关系<BR> 查询CostIassetCostRelaByCode
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public CostIassetCostRela queryCostIassetCostRelaByCode(Map<String,Object> entityMap)throws DataAccessException{
		
		return costIassetCostRelaMapper.queryCostIassetCostRelaByCode(entityMap);
		
	}
	
	/**
	 * @Description 
	 * 无形资产分类与成本项目的对应关系<BR> 批量删除CostIassetCostRela
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String deleteBatchCostIassetCostRela(List<Map<String,Object>> entityList)throws DataAccessException{

		try {

				int state = costIassetCostRelaMapper.deleteBatchCostIassetCostRela(entityList);
				return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码  deleteBatchCostIassetCostRela\"}";

		}
		
	}
	
	/**
	 * @Description 
	 * 无形资产分类与成本项目的对应关系<BR> 删除CostIassetCostRela
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
  public String deleteCostIassetCostRela(Map<String, Object> entityMap) throws DataAccessException {
		
		try {
				costIassetCostRelaMapper.deleteCostIassetCostRela(entityMap);
				return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码  deleteCostIassetCostRela\"}";

		}
    }
	
	/**
	 * @Description 
	 * 无形资产分类与成本项目的对应关系<BR> 更新CostIassetCostRela
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateCostIassetCostRela(Map<String,Object> entityMap)throws DataAccessException{

		try {

			costIassetCostRelaMapper.updateCostIassetCostRela(entityMap);

			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updateCostIassetCostRela\"}";

		}
	}
	
	/**
	 * @Description 
	 * 无形资产分类与成本项目的对应关系<BR> 批量更新CostIassetCostRela
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateBatchCostIassetCostRela(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {

			costIassetCostRelaMapper.updateBatchCostIassetCostRela(entityList);

			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updateCostIassetCostRela\"}";

		}
		
	}

	/**
	 * @Description
	 * 2016/10/26 lxj 
	 * 无形资产分类与成本项目的对应关系<BR> 继承CostIassetCostRela
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String extendCostIassetCostRela(Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		//1.查询源年月无形资产分类和成本项目的对应关系
		entityMap.put("is_flag", "1");
				
		List<CostIassetCostRela> sourceYearMonthList = costIassetCostRelaMapper.queryCostIassetCostRela(entityMap);
								
		if(sourceYearMonthList.size() == 0 ){
									
			return "{\"error\":\"继承失败 源年月没有无形资产分类与成本项目的对应关系数据存在! \"}";
		}
								
		entityMap.put("acc_year", entityMap.get("end_year"));
		entityMap.put("acc_month", entityMap.get("end_month"));
								
		//2.查询目标年月无形资产和成本项目的对应关系
		List<CostIassetCostRela> targetYearMonthList = costIassetCostRelaMapper.queryCostIassetCostRela(entityMap);
										
		if(targetYearMonthList.size() > 0 ){
											
			return "{\"error\":\"继承失败 目标年月已经有无形资产分类与成本项目的对应关系数据存在! \"}";
		}
								
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
								
		for(int x = 0; x < sourceYearMonthList.size(); x++){
									
			Map<String,Object> mapVo = new HashMap<String,Object>();
										
			CostIassetCostRela costIassetCostRela = sourceYearMonthList.get(x);
										
			mapVo.put("group_id", costIassetCostRela.getGroup_id());
			mapVo.put("hos_id", costIassetCostRela.getHos_id());
			mapVo.put("copy_code", costIassetCostRela.getCopy_code());
			mapVo.put("acc_year", entityMap.get("end_year"));
			mapVo.put("acc_month", entityMap.get("end_month"));
			mapVo.put("asset_type_code", costIassetCostRela.getAsset_type_code());
			mapVo.put("cost_item_code", costIassetCostRela.getCost_item_code());
			list.add(mapVo);
		}
								
		try {
									
			costIassetCostRelaMapper.addBatchCostIassetCostRela(list);
									
			return "{\"msg\":\"继承成功.\",\"state\":\"true\"}";
									
		} catch (Exception e) {
									
			return "{\"error\":\"继承失败 数据库异常 请联系管理员! 错误编码  extendCostIassetCostRela\"}";
		}	
	}
}
