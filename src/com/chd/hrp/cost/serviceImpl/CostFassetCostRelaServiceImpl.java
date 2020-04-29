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
import com.chd.hrp.cost.dao.CostFassetCostRelaMapper;
import com.chd.hrp.cost.entity.CostBonusCostRela;
import com.chd.hrp.cost.entity.CostFassetCostRela;
import com.chd.hrp.cost.service.CostFassetCostRelaService;
import com.github.pagehelper.PageInfo;

/**
* @Title. @Description.
* 固定资产分类与成本项目的对应关系<BR> 
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


@Service("costFassetCostRelaService")
public class CostFassetCostRelaServiceImpl implements CostFassetCostRelaService {
 
	private static Logger logger = Logger.getLogger(CostFassetCostRelaServiceImpl.class);
	
	@Resource(name = "costFassetCostRelaMapper")
	private final CostFassetCostRelaMapper costFassetCostRelaMapper = null;
    
	/**
	 * @Description 
	 * 固定资产分类与成本项目的对应关系<BR> 添加CostFassetCostRela
	 * @param CostFassetCostRela entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addCostFassetCostRela(Map<String,Object> entityMap)throws DataAccessException{
		/*
		 * 2016/10/26 lxj
		 * 解决添加操作时报异常的问题
		 * 原因是执行queryCostFassetCostRelaByCode时,返回多条数据,条件不满足
		 * */
		entityMap.put("is_flag", "1");

		CostFassetCostRela costFassetCostRela = queryCostFassetCostRelaByCode(entityMap);

		if (costFassetCostRela != null) {

			return "{\"error\":\"已经存在此年月对应关系.\"}";

		}
		
		try {
			
			costFassetCostRelaMapper.addCostFassetCostRela(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码 addCostFassetCostRela\"}";

		}

	}
	
	/**
	 * @Description 
	 * 固定资产分类与成本项目的对应关系<BR> 批量添加CostFassetCostRela
	 * @param  CostFassetCostRela entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addBatchCostFassetCostRela(List<Map<String,Object>> entityList)throws DataAccessException{

		try {
			
			costFassetCostRelaMapper.addBatchCostFassetCostRela(entityList);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码 addBatchCostFassetCostRela\"}";

		}
	}
	
	/**
	 * @Description 
	 * 固定资产分类与成本项目的对应关系<BR> 查询CostFassetCostRela分页
	 * @param  entityMap RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String queryCostFassetCostRela(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		sysPage = (SysPage) entityMap.get("sysPage");
		if (sysPage.getTotal()==-1){
			List<CostFassetCostRela> list = costFassetCostRelaMapper.queryCostFassetCostRela(entityMap);
			return ChdJson.toJson(list);
		}else{
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			List<CostFassetCostRela> list = costFassetCostRelaMapper.queryCostFassetCostRela(entityMap, rowBounds);
			PageInfo page = new PageInfo(list);
			return ChdJson.toJson(list, page.getTotal());
		}
		
	}
	@Override
	public List<Map<String, Object>> queryCostFassetArrtPrint(Map<String, Object> entityMap) throws DataAccessException {

		
		List<Map<String, Object>> list=costFassetCostRelaMapper.queryCostFassetArrtPrint(entityMap);
		
		return list;

	}
	/**
	 * @Description 
	 * 固定资产分类与成本项目的对应关系<BR> 查询CostFassetCostRelaByCode
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public CostFassetCostRela queryCostFassetCostRelaByCode(Map<String,Object> entityMap)throws DataAccessException{
		
		return costFassetCostRelaMapper.queryCostFassetCostRelaByCode(entityMap);
		
	}
	
	/**
	 * @Description 
	 * 固定资产分类与成本项目的对应关系<BR> 批量删除CostFassetCostRela
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String deleteBatchCostFassetCostRela(List<Map<String,Object>> entityList)throws DataAccessException{

		try {

				int state = costFassetCostRelaMapper.deleteBatchCostFassetCostRela(entityList);
				return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码  deleteBatchCostFassetCostRela\"}";

		}
		
	}
	
	/**
	 * @Description 
	 * 固定资产分类与成本项目的对应关系<BR> 删除CostFassetCostRela
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
  public String deleteCostFassetCostRela(Map<String, Object> entityMap) throws DataAccessException {
		
		try {
				costFassetCostRelaMapper.deleteCostFassetCostRela(entityMap);
				return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码  deleteCostFassetCostRela\"}";

		}
    }
	
	/**
	 * @Description 
	 * 固定资产分类与成本项目的对应关系<BR> 更新CostFassetCostRela
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateCostFassetCostRela(Map<String,Object> entityMap)throws DataAccessException{

		try {

			costFassetCostRelaMapper.updateCostFassetCostRela(entityMap);

			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updateCostFassetCostRela\"}";

		}
	}
	
	/**
	 * @Description 
	 * 固定资产分类与成本项目的对应关系<BR> 批量更新CostFassetCostRela
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateBatchCostFassetCostRela(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {

			costFassetCostRelaMapper.updateBatchCostFassetCostRela(entityList);

			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updateCostFassetCostRela\"}";

		}
		
	}

	/**
	 * @Description
	 * 2016/10/26 lxj
	 * 固定资产分类与成本项目的对应关系<BR> 继承CostFassetCostRela
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String extendCostFassetCostRela(Map<String, Object> entityMap)throws DataAccessException {
		// TODO Auto-generated method stub
		//1.查询源年月固定资产分类和成本项目的对应关系
		entityMap.put("is_flag", "1");
		
		List<CostFassetCostRela> sourceYearMonthList = costFassetCostRelaMapper.queryCostFassetCostRela(entityMap);
						
		if(sourceYearMonthList.size() == 0 ){
							
			return "{\"error\":\"继承失败 源年月没有固定资产分类与成本项目的对应关系数据存在! \"}";
		}
						
		entityMap.put("acc_year", entityMap.get("end_year"));
		entityMap.put("acc_month", entityMap.get("end_month"));
						
		//2.查询目标年月固定资产分类和成本项目的对应关系
		List<CostFassetCostRela> targetYearMonthList = costFassetCostRelaMapper.queryCostFassetCostRela(entityMap);
								
		if(targetYearMonthList.size() > 0 ){
									
			return "{\"error\":\"继承失败 目标年月已经有固定资产分类与成本项目的对应关系数据存在! \"}";
		}
						
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
						
		for(int x = 0; x < sourceYearMonthList.size(); x++){
							
			Map<String,Object> mapVo = new HashMap<String,Object>();
							
			CostFassetCostRela costFassetCostRela = sourceYearMonthList.get(x);
							
			mapVo.put("group_id", costFassetCostRela.getGroup_id());
			mapVo.put("hos_id", costFassetCostRela.getHos_id());
			mapVo.put("copy_code", costFassetCostRela.getCopy_code());
			mapVo.put("acc_year", entityMap.get("end_year"));
			mapVo.put("acc_month", entityMap.get("end_month"));
			mapVo.put("asset_type_code", costFassetCostRela.getAsset_type_code());
			mapVo.put("cost_item_code", costFassetCostRela.getCost_item_code());
			list.add(mapVo);
		}
						
		try {
							
			costFassetCostRelaMapper.addBatchCostFassetCostRela(list);
							
			return "{\"msg\":\"继承成功.\",\"state\":\"true\"}";
							
		} catch (Exception e) {
							
			return "{\"error\":\"继承失败 数据库异常 请联系管理员! 错误编码  extendCostFassetCostRela\"}";
		}	
	}
}
