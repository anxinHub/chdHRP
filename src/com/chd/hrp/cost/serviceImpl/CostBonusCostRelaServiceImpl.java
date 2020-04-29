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
import com.chd.hrp.cost.dao.CostBonusCostRelaMapper;
import com.chd.hrp.cost.dao.CostEmpKindBonusItemSetMapper;
import com.chd.hrp.cost.entity.CostBonusCostRela;
import com.chd.hrp.cost.entity.CostEmpKindBonusItemSet;
import com.chd.hrp.cost.service.CostBonusCostRelaService;
import com.github.pagehelper.PageInfo;

/**
* @Title. @Description.
* 奖金项与成本项目的对应关系<BR> 
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


@Service("costBonusCostRelaService")
public class CostBonusCostRelaServiceImpl implements CostBonusCostRelaService {

	private static Logger logger = Logger.getLogger(CostBonusCostRelaServiceImpl.class);
	
	@Resource(name = "costBonusCostRelaMapper")
	private final CostBonusCostRelaMapper costBonusCostRelaMapper = null;
    
	@Resource(name = "costEmpKindBonusItemSetMapper")
	private final CostEmpKindBonusItemSetMapper costEmpKindBonusItemSetMapper = null;
	/**
	 * @Description 
	 * 奖金项与成本项目的对应关系<BR> 添加CostBonusCostRela
	 * @param CostBonusCostRela entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addCostBonusCostRela(Map<String,Object> entityMap)throws DataAccessException{
		
		
		List<CostEmpKindBonusItemSet> costEmpKindBonusItemSetList = costEmpKindBonusItemSetMapper.queryCostEmpKindBonusItemSet(entityMap);

		if (costEmpKindBonusItemSetList.size()<=0) {

			return "{\"error\":\"没有配置职工分类奖金项目对应关系.\"}";

		}
		
		
		CostBonusCostRela costBonusCostRela = queryCostBonusCostRelaByCode(entityMap);
		
		

		if (costBonusCostRela != null) {

			return "{\"error\":\"已经存在关系不能重复添加.\"}";

		}
		
		try {
			
			costBonusCostRelaMapper.addCostBonusCostRela(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码 addCostBonusCostRela\"}";

		}

	}
	
	/**
	 * @Description 
	 * 奖金项与成本项目的对应关系<BR> 批量添加CostBonusCostRela
	 * @param  CostBonusCostRela entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addBatchCostBonusCostRela(List<Map<String,Object>> entityList)throws DataAccessException{

		try {
			
			costBonusCostRelaMapper.addBatchCostBonusCostRela(entityList);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码 addBatchCostBonusCostRela\"}";

		}
	}
	
	/**
	 * @Description 
	 * 奖金项与成本项目的对应关系<BR> 查询CostBonusCostRela分页
	 * @param  entityMap RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String queryCostBonusCostRela(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		sysPage = (SysPage) entityMap.get("sysPage");
		if (sysPage.getTotal()==-1){
			List<CostBonusCostRela> list = costBonusCostRelaMapper.queryCostBonusCostRela(entityMap);
			return ChdJson.toJson(list);
		}else{
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			List<CostBonusCostRela> list = costBonusCostRelaMapper.queryCostBonusCostRela(entityMap, rowBounds);
			PageInfo page = new PageInfo(list);
			return ChdJson.toJson(list, page.getTotal());
		}
		
	}
	@Override
	public List<Map<String, Object>> queryCostBonusCostRelaPrint(Map<String, Object> entityMap) throws DataAccessException {

		
		List<Map<String, Object>> list=costBonusCostRelaMapper.queryCostBonusCostRelaPrint(entityMap);
		
		return list;

	}
	/**
	 * @Description 
	 * 奖金项与成本项目的对应关系<BR> 查询CostBonusCostRelaByCode
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public CostBonusCostRela queryCostBonusCostRelaByCode(Map<String,Object> entityMap)throws DataAccessException{
		
		return costBonusCostRelaMapper.queryCostBonusCostRelaByCode(entityMap);
		
	}
	
	/**
	 * @Description 
	 * 奖金项与成本项目的对应关系<BR> 批量删除CostBonusCostRela
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String deleteBatchCostBonusCostRela(List<Map<String,Object>> entityList)throws DataAccessException{

		try {

				int state = costBonusCostRelaMapper.deleteBatchCostBonusCostRela(entityList);
				return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码  deleteBatchCostBonusCostRela\"}";

		}
		
	}
	
	/**
	 * @Description 
	 * 奖金项与成本项目的对应关系<BR> 删除CostBonusCostRela
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
  public String deleteCostBonusCostRela(Map<String, Object> entityMap) throws DataAccessException {
		
		try {
				costBonusCostRelaMapper.deleteCostBonusCostRela(entityMap);
				return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码  deleteCostBonusCostRela\"}";

		}
    }
	
	/**
	 * @Description 
	 * 奖金项与成本项目的对应关系<BR> 更新CostBonusCostRela
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateCostBonusCostRela(Map<String,Object> entityMap)throws DataAccessException{

		try {

			costBonusCostRelaMapper.updateCostBonusCostRela(entityMap);

			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updateCostBonusCostRela\"}";

		}
	}
	
	/**
	 * @Description 
	 * 奖金项与成本项目的对应关系<BR> 批量更新CostBonusCostRela
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateBatchCostBonusCostRela(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {

			costBonusCostRelaMapper.updateBatchCostBonusCostRela(entityList);

			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updateCostBonusCostRela\"}";

		}
		
	}
	
	/**
	 * @Description 
	 * @Time 2016/10/26
	 * @author lxj
	 * 奖金项与成本项目的对应关系<BR> 继承CostBonusCostRela
	 * @param CostBonusCostRela entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String extendCostBonusCostRela(Map<String,Object> entityMap)throws DataAccessException{
		
		//1.查询源年月奖金项和成本项目的对应关系
		List<CostBonusCostRela> sourceYearMonthList = costBonusCostRelaMapper.queryCostBonusCostRela(entityMap);
				
		if(sourceYearMonthList.size() == 0 ){
					
			return "{\"error\":\"继承失败 源年月没有奖金项与成本项目的对应关系数据存在! \"}";
		}
				
		entityMap.put("acc_year", entityMap.get("end_year"));
		entityMap.put("acc_month", entityMap.get("end_month"));
				
		//2.查询目标年月奖金项和成本项目的对应关系
		List<CostBonusCostRela> targetYearMonthList = costBonusCostRelaMapper.queryCostBonusCostRela(entityMap);
						
		if(targetYearMonthList.size() > 0 ){
							
			return "{\"error\":\"继承失败 目标年月已经有奖金项与成本项目的对应关系数据存在! \"}";
		}
				
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
				
		for(int x = 0; x < sourceYearMonthList.size(); x++){
					
			Map<String,Object> mapVo = new HashMap<String,Object>();
					
			CostBonusCostRela costBonusCostRela = sourceYearMonthList.get(x);
					
			mapVo.put("group_id", costBonusCostRela.getGroup_id());
			mapVo.put("hos_id", costBonusCostRela.getHos_id());
			mapVo.put("copy_code", costBonusCostRela.getCopy_code());
			mapVo.put("acc_year", entityMap.get("end_year"));
			mapVo.put("acc_month", entityMap.get("end_month"));
			mapVo.put("emp_kind_code", costBonusCostRela.getEmp_kind_code());
			mapVo.put("bonus_item_code", costBonusCostRela.getBonus_item_code());
			mapVo.put("cost_item_id", costBonusCostRela.getCost_item_id());
			mapVo.put("cost_item_no", costBonusCostRela.getCost_item_no());
			list.add(mapVo);
		}
				
		try {
					
			costBonusCostRelaMapper.addBatchCostBonusCostRela(list);
					
			return "{\"msg\":\"继承成功.\",\"state\":\"true\"}";
					
		} catch (Exception e) {
					
			return "{\"error\":\"继承失败 数据库异常 请联系管理员! 错误编码  extendCostBonusCostRela\"}";
		}		
	}
}
