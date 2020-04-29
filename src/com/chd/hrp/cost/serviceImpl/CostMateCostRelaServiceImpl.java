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
import com.chd.hrp.cost.dao.CostMateCostRelaMapper;
import com.chd.hrp.cost.entity.CostMateCostRela;
import com.chd.hrp.cost.service.CostMateCostRelaService;
import com.github.pagehelper.PageInfo;

/**
* @Title. @Description.
* 材料类别与成本项目的对应关系<BR> 
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


@Service("costMateCostRelaService")
public class CostMateCostRelaServiceImpl implements CostMateCostRelaService {
 
	private static Logger logger = Logger.getLogger(CostMateCostRelaServiceImpl.class);
	
	@Resource(name = "costMateCostRelaMapper")
	private final CostMateCostRelaMapper costMateCostRelaMapper = null;
    
	/**
	 * @Description 
	 * 材料类别与成本项目的对应关系<BR> 添加CostMateCostRela
	 * @param CostMateCostRela entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addCostMateCostRela(Map<String,Object> entityMap)throws DataAccessException{
		
		Map<String, Object> byCodeMap = new HashMap<String, Object>();
		
		byCodeMap.put("group_id", entityMap.get("group_id"));
		
		byCodeMap.put("hos_id", entityMap.get("hos_id"));
		
		byCodeMap.put("copy_code", entityMap.get("copy_code"));
		
		byCodeMap.put("mate_type_code", entityMap.get("mate_type_code"));
		
		byCodeMap.put("year_month", entityMap.get("year_month"));
		
		CostMateCostRela costMateCostRela = queryCostMateCostRelaByCode(byCodeMap);

		if (costMateCostRela != null) {

			return "{\"error\":\"已经存在此年月对应关系.\"}";

		}
		
		try {
			
			costMateCostRelaMapper.addCostMateCostRela(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码 addCostMateCostRela\"}";

		}

	}
	
	/**
	 * @Description 
	 * 材料类别与成本项目的对应关系<BR> 批量添加CostMateCostRela
	 * @param  CostMateCostRela entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addBatchCostMateCostRela(List<Map<String,Object>> entityList)throws DataAccessException{

		try {
			
			costMateCostRelaMapper.addBatchCostMateCostRela(entityList);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码 addBatchCostMateCostRela\"}";

		}
	}
	
	/**
	 * @Description 
	 * 材料类别与成本项目的对应关系<BR> 查询CostMateCostRela分页
	 * @param  entityMap RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String queryCostMateCostRela(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		sysPage = (SysPage) entityMap.get("sysPage");
		if (sysPage.getTotal()==-1){
			List<CostMateCostRela> list = costMateCostRelaMapper.queryCostMateCostRela(entityMap);
			return ChdJson.toJson(list);
		}else{
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			List<CostMateCostRela> list = costMateCostRelaMapper.queryCostMateCostRela(entityMap, rowBounds);
			PageInfo page = new PageInfo(list);
			return ChdJson.toJson(list, page.getTotal());
		}
		
	}
	@Override
	public List<Map<String, Object>> queryCostMateCostRelaPrint(Map<String, Object> entityMap) throws DataAccessException {

		
		List<Map<String, Object>> list=costMateCostRelaMapper.queryCostMateCostRelaPrint(entityMap);
		
		return list;

	}
	/**
	 * @Description 
	 * 材料类别与成本项目的对应关系<BR> 查询CostMateCostRelaByCode
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public CostMateCostRela queryCostMateCostRelaByCode(Map<String,Object> entityMap)throws DataAccessException{
		
		return costMateCostRelaMapper.queryCostMateCostRelaByCode(entityMap);
		
	}
	
	/**
	 * @Description 
	 * 材料类别与成本项目的对应关系<BR> 批量删除CostMateCostRela
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String deleteBatchCostMateCostRela(List<Map<String,Object>> entityList)throws DataAccessException{

		try {

				int state = costMateCostRelaMapper.deleteBatchCostMateCostRela(entityList);
				return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码  deleteBatchCostMateCostRela\"}";

		}
		
	}
	
	/**
	 * @Description 
	 * 材料类别与成本项目的对应关系<BR> 删除CostMateCostRela
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
  public String deleteCostMateCostRela(Map<String, Object> entityMap) throws DataAccessException {
		
		try {
				costMateCostRelaMapper.deleteCostMateCostRela(entityMap);
				return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码  deleteCostMateCostRela\"}";

		}
    }
	
	/**
	 * @Description 
	 * 材料类别与成本项目的对应关系<BR> 更新CostMateCostRela
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateCostMateCostRela(Map<String,Object> entityMap)throws DataAccessException{

		try {

			costMateCostRelaMapper.updateCostMateCostRela(entityMap);

			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updateCostMateCostRela\"}";

		}
	}
	
	/**
	 * @Description 
	 * 材料类别与成本项目的对应关系<BR> 批量更新CostMateCostRela
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateBatchCostMateCostRela(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {

			costMateCostRelaMapper.updateBatchCostMateCostRela(entityList);

			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updateCostMateCostRela\"}";

		}
		
	}

	/**
	 * @Description
	 * 2016/10/26 lxj 
	 * 材料类别与成本项目的对应关系<BR> 继承CostMateCostRela
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String extendCostMateCostRela(Map<String,Object> entityMap)throws DataAccessException{
		entityMap.put("is_flag", "1");
		//1.查询源年月材料分类和成本项目的对应关系
		List<CostMateCostRela> sourceYearMonthList = costMateCostRelaMapper.queryCostMateCostRela(entityMap);
						
			if(sourceYearMonthList.size() == 0 ){
							
			return "{\"error\":\"继承失败 源年月没有材料分类与成本项目的对应关系数据存在! \"}";
		}
						
		entityMap.put("acc_year", entityMap.get("end_year"));
		entityMap.put("acc_month", entityMap.get("end_month"));
						
		//2.查询目标年月材料分类和成本项目的对应关系
		List<CostMateCostRela> targetYearMonthList = costMateCostRelaMapper.queryCostMateCostRela(entityMap);
								
		if(targetYearMonthList.size() > 0 ){
									
			return "{\"error\":\"继承失败 目标年月已经有材料分类与成本项目的对应关系数据存在! \"}";
		}
						
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
						
		for(int x = 0; x < sourceYearMonthList.size(); x++){
							
			Map<String,Object> mapVo = new HashMap<String,Object>();
							
			CostMateCostRela costMateCostRela = sourceYearMonthList.get(x);
							
			mapVo.put("group_id", costMateCostRela.getGroup_id());
			mapVo.put("hos_id", costMateCostRela.getHos_id());
			mapVo.put("copy_code", costMateCostRela.getCopy_code());
			mapVo.put("acc_year", entityMap.get("end_year"));
			mapVo.put("acc_month", entityMap.get("end_month"));
			mapVo.put("mate_type_code", costMateCostRela.getMate_type_code());
			mapVo.put("cost_item_code", costMateCostRela.getCost_item_code());
			list.add(mapVo);
		}
						
		try {
							
			costMateCostRelaMapper.addBatchCostMateCostRela(list);
							
				return "{\"msg\":\"继承成功.\",\"state\":\"true\"}";
							
			} catch (Exception e) {
							
				return "{\"error\":\"继承失败 数据库异常 请联系管理员! 错误编码  extendCostMateCostRela\"}";
			}
	}
}
