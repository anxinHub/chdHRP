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
import com.chd.hrp.cost.dao.CostEmpKindWageItemSetMapper;
import com.chd.hrp.cost.dao.CostWageCostRelaMapper;
import com.chd.hrp.cost.entity.CostEmpKindWageItemSet;
import com.chd.hrp.cost.entity.CostWageCostRela;
import com.chd.hrp.cost.service.CostWageCostRelaService;
import com.github.pagehelper.PageInfo;

/**
* @Title. @Description.
* 工资项与成本项目的对应关系<BR> 
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


@Service("costWageCostRelaService")
public class CostWageCostRelaServiceImpl implements CostWageCostRelaService {

	private static Logger logger = Logger.getLogger(CostWageCostRelaServiceImpl.class);
	
	@Resource(name = "costWageCostRelaMapper")
	private final CostWageCostRelaMapper costWageCostRelaMapper = null;
    
	@Resource(name = "costEmpKindWageItemSetMapper")
	private final CostEmpKindWageItemSetMapper costEmpKindWageItemSetMapper = null;
	/**
	 * @Description 
	 * 工资项与成本项目的对应关系<BR> 添加CostWageCostRela
	 * @param CostWageCostRela entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addCostWageCostRela(Map<String,Object> entityMap)throws DataAccessException{
		
		
		List<CostEmpKindWageItemSet> costEmpKindWageItemSetList = costEmpKindWageItemSetMapper.queryCostEmpKindWageItemSet(entityMap);

		if (costEmpKindWageItemSetList.size()<=0) {

			return "{\"error\":\"没有配置职工分类工资项目对应关系.\"}";

		}
		
		CostWageCostRela costWageCostRela = queryCostWageCostRelaByCode(entityMap);

		if (costWageCostRela != null) {

			return "{\"error\":\"已经存在此对应关系.\"}";

		}
		
		try {
			
			costWageCostRelaMapper.addCostWageCostRela(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码 addCostWageCostRela\"}";

		}

	}
	
	/**
	 * @Description 
	 * 工资项与成本项目的对应关系<BR> 批量添加CostWageCostRela
	 * @param  CostWageCostRela entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addBatchCostWageCostRela(List<Map<String,Object>> entityList)throws DataAccessException{

		try {
			
			costWageCostRelaMapper.addBatchCostWageCostRela(entityList);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码 addBatchCostWageCostRela\"}";

		}
	}
	
	/**
	 * @Description 
	 * 工资项与成本项目的对应关系<BR> 查询CostWageCostRela分页
	 * @param  entityMap RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String queryCostWageCostRela(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		sysPage = (SysPage) entityMap.get("sysPage");
		if (sysPage.getTotal()==-1){
			List<CostWageCostRela> list = costWageCostRelaMapper.queryCostWageCostRela(entityMap);
			return ChdJson.toJson(list);
		}else{
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			List<CostWageCostRela> list = costWageCostRelaMapper.queryCostWageCostRela(entityMap, rowBounds);
			PageInfo page = new PageInfo(list);
			return ChdJson.toJson(list, page.getTotal());
		}
		
	}
	@Override
	public List<Map<String, Object>> queryCostWageCostRelaPrint(Map<String, Object> entityMap) throws DataAccessException {

		
		List<Map<String, Object>> list=costWageCostRelaMapper.queryCostWageCostRelaPrint(entityMap);
		
		return list;

	}
	/**
	 * @Description 
	 * 工资项与成本项目的对应关系<BR> 查询CostWageCostRelaByCode
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public CostWageCostRela queryCostWageCostRelaByCode(Map<String,Object> entityMap)throws DataAccessException{
		
		return costWageCostRelaMapper.queryCostWageCostRelaByCode(entityMap);
		
	}
	
	/**
	 * @Description 
	 * 工资项与成本项目的对应关系<BR> 批量删除CostWageCostRela
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String deleteBatchCostWageCostRela(List<Map<String,Object>> entityList)throws DataAccessException{

		try {

				int state = costWageCostRelaMapper.deleteBatchCostWageCostRela(entityList);
				return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码  deleteBatchCostWageCostRela\"}";

		}
		
	}
	
	/**
	 * @Description 
	 * 工资项与成本项目的对应关系<BR> 删除CostWageCostRela
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
  public String deleteCostWageCostRela(Map<String, Object> entityMap) throws DataAccessException {
		
		try {
				costWageCostRelaMapper.deleteCostWageCostRela(entityMap);
				return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码  deleteCostWageCostRela\"}";

		}
    }
	
	/**
	 * @Description 
	 * 工资项与成本项目的对应关系<BR> 更新CostWageCostRela
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateCostWageCostRela(Map<String,Object> entityMap)throws DataAccessException{

		try {

			costWageCostRelaMapper.updateCostWageCostRela(entityMap);

			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updateCostWageCostRela\"}";

		}
	}
	
	/**
	 * @Description 
	 * 工资项与成本项目的对应关系<BR> 批量更新CostWageCostRela
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateBatchCostWageCostRela(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {

			costWageCostRelaMapper.updateBatchCostWageCostRela(entityList);

			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updateCostWageCostRela\"}";

		}
		
	}
	
	/**
	 * @Description 
	 * 2016/10/25 lxj
	 * 工资项与成本项目的对应关系<BR> 继承CostWageCostRela
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String extendCostWageCostRela(Map<String,Object> entityMap)throws DataAccessException{
		
		//1.查询源年月工资项和成本项目的对应关系
		List<CostWageCostRela> selectYearMonthList = costWageCostRelaMapper.queryCostWageCostRela(entityMap);
		
		if(selectYearMonthList.size() == 0 ){
			
			return "{\"error\":\"继承失败 源年月没有工资项与成本项目的对应关系数据存在! \"}";
		}
		
		entityMap.put("acc_year", entityMap.get("end_year"));
		entityMap.put("acc_month", entityMap.get("end_month"));
		
		//2.查询目标年月工资项和成本项目的对应关系
		List<CostWageCostRela> curYearMonthList = costWageCostRelaMapper.queryCostWageCostRela(entityMap);
				
		if(curYearMonthList.size() > 0 ){
					
			return "{\"error\":\"继承失败 目标年月已经有工资项与成本项目的对应关系数据存在! \"}";
		}
		
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		
		for(int x = 0; x < selectYearMonthList.size(); x++){
			
			Map<String,Object> mapVo = new HashMap<String,Object>();
			
			CostWageCostRela costWageCostRela = selectYearMonthList.get(x);
			
			mapVo.put("group_id", costWageCostRela.getGroup_id());
			mapVo.put("hos_id", costWageCostRela.getHos_id());
			mapVo.put("copy_code", costWageCostRela.getCopy_code());
			mapVo.put("acc_year", entityMap.get("end_year"));
			mapVo.put("acc_month", entityMap.get("end_month"));
			mapVo.put("emp_kind_code", costWageCostRela.getEmp_kind_code());
			mapVo.put("wage_item_code", costWageCostRela.getWage_item_code());
			mapVo.put("cost_item_id", costWageCostRela.getCost_item_id());
			mapVo.put("cost_item_no", costWageCostRela.getCost_item_no());
			list.add(mapVo);
		}
		
		try {
			
			costWageCostRelaMapper.addBatchCostWageCostRela(list);
			
			return "{\"msg\":\"继承成功.\",\"state\":\"true\"}";
			
		} catch (Exception e) {
			
			return "{\"error\":\"继承失败 数据库异常 请联系管理员! 错误编码  extendCostWageCostRela\"}";
		}
	}
}
