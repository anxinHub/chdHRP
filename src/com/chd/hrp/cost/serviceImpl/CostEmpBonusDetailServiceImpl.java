/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/

package com.chd.hrp.cost.serviceImpl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.SysPage;
import com.chd.base.util.ChdJson;
import com.chd.hrp.cost.dao.CostBonusSchemeSetMapper;
import com.chd.hrp.cost.dao.CostEmpBonusDetailMapper;
import com.chd.hrp.cost.entity.CostBonusSchemeSet;
import com.chd.hrp.cost.entity.CostEmpBonusDetail;
import com.chd.hrp.cost.entity.CostWageSchemeSet;
import com.chd.hrp.cost.service.CostEmpBonusDetailService;
import com.github.pagehelper.PageInfo;

/**
* @Title. @Description.
* 人员奖金明细数据表<BR> 
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


@Service("costEmpBonusDetailService")
public class CostEmpBonusDetailServiceImpl implements CostEmpBonusDetailService {

	private static Logger logger = Logger.getLogger(CostEmpBonusDetailServiceImpl.class);
	
	@Resource(name = "costEmpBonusDetailMapper")
	private final CostEmpBonusDetailMapper costEmpBonusDetailMapper = null;
	
	@Resource(name = "costBonusSchemeSetMapper")
	private final CostBonusSchemeSetMapper costBonusSchemeSetMapper = null;
    
	/**
	 * @Description 
	 * 人员奖金明细数据表<BR> 添加CostEmpBonusDetail
	 * @param CostEmpBonusDetail entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addCostEmpBonusDetail(Map<String,Object> entityMap)throws DataAccessException{
		
		CostEmpBonusDetail costEmpBonusDetail = queryCostEmpBonusDetailByCode(entityMap);

		if (costEmpBonusDetail != null) {

			//return "{\"error\":\"编码：" + entityMap.get("emp_kind_code").toString() + "重复.\"}";
			return "{\"error\":\"相同的科室，职工，职工分类重复添加.\"}";

		}
		
		try {
			String scheme_id = entityMap.get("scheme_id").toString();
			StringBuffer sql = new StringBuffer();
			StringBuffer sql_value = new StringBuffer();
			if(!"".equals(scheme_id) && scheme_id != null){
				List<CostBonusSchemeSet> list = costBonusSchemeSetMapper.queryCostBonusSchemeSetByTitle(entityMap);
				for(int i = 0 ; i < list.size() ; i ++){
					if((i+1) == list.size()){
						sql.append(","+list.get(i).getBonus_column());
						sql_value.append(","+entityMap.get(list.get(i).getBonus_column()));
					}else{
						sql.append(","+list.get(i).getBonus_column()+",");
						sql_value.append(","+entityMap.get(list.get(i).getBonus_column())+",");
					}
				}
			}
			entityMap.put("sql_text", sql.toString());
			entityMap.put("sql_value", sql_value.toString());
			costEmpBonusDetailMapper.addCostEmpBonusDetail(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码 addCostEmpBonusDetail\"}";

		}

	}
	
	/**
	 * @Description 
	 * 人员奖金明细数据表<BR> 批量添加CostEmpBonusDetail
	 * @param  CostEmpBonusDetail entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addBatchCostEmpBonusDetail(List<Map<String,Object>> entityList)throws DataAccessException{

		try {
			
			costEmpBonusDetailMapper.addBatchCostEmpBonusDetail(entityList);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码 addBatchCostEmpBonusDetail\"}";

		}
	}
	
	/**
	 * @Description 
	 * 人员奖金明细数据表<BR> 查询CostEmpBonusDetail分页
	 * @param  entityMap RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String queryCostEmpBonusDetail(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		sysPage = (SysPage) entityMap.get("sysPage");
		if (sysPage.getTotal()==-1){
			List<CostEmpBonusDetail> list = costEmpBonusDetailMapper.queryCostEmpBonusDetail(entityMap);
			return ChdJson.toJson(list);
		}else{
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			List<CostEmpBonusDetail> list = costEmpBonusDetailMapper.queryCostEmpBonusDetail(entityMap, rowBounds);
			PageInfo page = new PageInfo(list);
			return ChdJson.toJson(list, page.getTotal());
		}
		
	}
	@Override
	public List<Map<String, Object>> queryCostEmpBonusDetailPrint(Map<String, Object> entityMap) throws DataAccessException {

		
		List<Map<String, Object>> list=costEmpBonusDetailMapper.queryCostEmpBonusDetailPrint(entityMap);
		
		return list;

	}
	/**
	 * @Description 
	 * 人员奖金明细数据表<BR> 查询CostEmpBonusDetailByCode
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public CostEmpBonusDetail queryCostEmpBonusDetailByCode(Map<String,Object> entityMap)throws DataAccessException{
		
		return costEmpBonusDetailMapper.queryCostEmpBonusDetailByCode(entityMap);
		
	}
	
	/**
	 * @Description 
	 * 人员奖金明细数据表<BR> 批量删除CostEmpBonusDetail
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String deleteBatchCostEmpBonusDetail(List<Map<String,Object>> entityList)throws DataAccessException{

		try {

				int state = costEmpBonusDetailMapper.deleteBatchCostEmpBonusDetail(entityList);
				return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码  deleteBatchCostEmpBonusDetail\"}";

		}
		
	}
	
	/**
	 * @Description 
	 * 人员奖金明细数据表<BR> 删除CostEmpBonusDetail
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
  public String deleteCostEmpBonusDetail(Map<String, Object> entityMap) throws DataAccessException {
		
		try {
				costEmpBonusDetailMapper.deleteCostEmpBonusDetail(entityMap);
				return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码  deleteCostEmpBonusDetail\"}";

		}
    }
	
	/**
	 * @Description 
	 * 人员奖金明细数据表<BR> 更新CostEmpBonusDetail
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateCostEmpBonusDetail(Map<String,Object> entityMap)throws DataAccessException{

		try {
			String scheme_id = entityMap.get("scheme_id").toString();
			StringBuffer sql = new StringBuffer();
			if(!"".equals(scheme_id) && scheme_id != null){
				List<CostBonusSchemeSet> list = costBonusSchemeSetMapper.queryCostBonusSchemeSetByTitle(entityMap);
				for(int i = 0 ; i < list.size() ; i ++){
					if((i+1) == list.size()){
						sql.append(","+list.get(i).getBonus_column()+"="+entityMap.get(list.get(i).getBonus_column()));
					}else{
						sql.append(","+list.get(i).getBonus_column()+"="+entityMap.get(list.get(i).getBonus_column())+",");
					}
				}
			}
			entityMap.put("sql_value", sql.toString());
			costEmpBonusDetailMapper.updateCostEmpBonusDetail(entityMap);

			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updateCostEmpBonusDetail\"}";

		}
	}
	
	/**
	 * @Description 
	 * 人员奖金明细数据表<BR> 批量更新CostEmpBonusDetail
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateBatchCostEmpBonusDetail(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {

			costEmpBonusDetailMapper.updateBatchCostEmpBonusDetail(entityList);

			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updateCostEmpBonusDetail\"}";

		}
		
	}
}
