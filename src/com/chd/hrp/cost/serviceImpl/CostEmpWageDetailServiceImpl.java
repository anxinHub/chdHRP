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
import com.chd.hrp.cost.dao.CostEmpWageDetailMapper;
import com.chd.hrp.cost.dao.CostWageSchemeSetMapper;
import com.chd.hrp.cost.entity.CostEmpWageDetail;
import com.chd.hrp.cost.entity.CostWageSchemeSet;
import com.chd.hrp.cost.service.CostEmpWageDetailService;
import com.github.pagehelper.PageInfo;

/**
* @Title. @Description.
* 人员工资明细数据表<BR> 
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


@Service("costEmpWageDetailService")
public class CostEmpWageDetailServiceImpl implements CostEmpWageDetailService {

	private static Logger logger = Logger.getLogger(CostEmpWageDetailServiceImpl.class);
	
	@Resource(name = "costEmpWageDetailMapper")
	private final CostEmpWageDetailMapper costEmpWageDetailMapper = null;
	
	@Resource(name = "costWageSchemeSetMapper")
	private final CostWageSchemeSetMapper costWageSchemeSetMapper = null;
    
	/**
	 * @Description 
	 * 人员工资明细数据表<BR> 添加CostEmpWageDetail
	 * @param CostEmpWageDetail entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addCostEmpWageDetail(Map<String,Object> entityMap)throws DataAccessException{
		
		CostEmpWageDetail costEmpWageDetail = queryCostEmpWageDetailByCode(entityMap);

		if (costEmpWageDetail != null) {

			//return "{\"error\":\"编码：" + entityMap.get("item_code").toString() + "重复.\"}";
			return "{\"error\":\"相同科室，职工，职工分类重复添加.\"}";

		}
		
		try {
			String scheme_id = entityMap.get("scheme_id").toString();
			StringBuffer sql = new StringBuffer();
			StringBuffer sql_value = new StringBuffer();
			if(!"".equals(scheme_id) && scheme_id != null){
				List<CostWageSchemeSet> list = costWageSchemeSetMapper.queryCostWageSchemeSetByTitle(entityMap);
				for(int i = 0 ; i < list.size() ; i ++){
					if((i+1) == list.size()){
						sql.append(","+list.get(i).getWage_column());
						sql_value.append(","+entityMap.get(list.get(i).getWage_column()));
					}else{
						sql.append(","+list.get(i).getWage_column()+",");
						sql_value.append(","+entityMap.get(list.get(i).getWage_column())+",");
					}
				}
			}
			entityMap.put("sql_text", sql.toString());
			entityMap.put("sql_value", sql_value.toString());
			costEmpWageDetailMapper.addCostEmpWageDetail(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码 addCostEmpWageDetail\"}";

		}

	}
	
	/**
	 * @Description 
	 * 人员工资明细数据表<BR> 批量添加CostEmpWageDetail
	 * @param  CostEmpWageDetail entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addBatchCostEmpWageDetail(List<Map<String,Object>> entityList)throws DataAccessException{

		try {
			
			costEmpWageDetailMapper.addBatchCostEmpWageDetail(entityList);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码 addBatchCostEmpWageDetail\"}";

		}
	}
	
	/**
	 * @Description 
	 * 人员工资明细数据表<BR> 查询CostEmpWageDetail分页
	 * @param  entityMap RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String queryCostEmpWageDetail(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		sysPage = (SysPage) entityMap.get("sysPage");
		if (sysPage.getTotal()==-1){
			List<CostEmpWageDetail> list = costEmpWageDetailMapper.queryCostEmpWageDetail(entityMap);
			return ChdJson.toJson(list);
		}else{
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			List<CostEmpWageDetail> list = costEmpWageDetailMapper.queryCostEmpWageDetail(entityMap, rowBounds);
			PageInfo page = new PageInfo(list);
			return ChdJson.toJson(list, page.getTotal());
		}
		
	}
	@Override
	public List<Map<String, Object>> queryCostEmpWageDetailPrint(Map<String, Object> entityMap) throws DataAccessException {

		
		List<Map<String, Object>> list=costEmpWageDetailMapper.queryCostEmpWageDetailPrint(entityMap);
		
		return list;

	}
	/**
	 * @Description 
	 * 人员工资明细数据表<BR> 查询CostEmpWageDetailByCode
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public CostEmpWageDetail queryCostEmpWageDetailByCode(Map<String,Object> entityMap)throws DataAccessException{
		
		return costEmpWageDetailMapper.queryCostEmpWageDetailByCode(entityMap);
		
	}
	
	/**
	 * @Description 
	 * 人员工资明细数据表<BR> 批量删除CostEmpWageDetail
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String deleteBatchCostEmpWageDetail(List<Map<String,Object>> entityList)throws DataAccessException{

		try {

				int state = costEmpWageDetailMapper.deleteBatchCostEmpWageDetail(entityList);
				return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码  deleteBatchCostEmpWageDetail\"}";

		}
		
	}
	
	/**
	 * @Description 
	 * 人员工资明细数据表<BR> 删除CostEmpWageDetail
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
  public String deleteCostEmpWageDetail(Map<String, Object> entityMap) throws DataAccessException {
		
		try {
				costEmpWageDetailMapper.deleteCostEmpWageDetail(entityMap);
				return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码  deleteCostEmpWageDetail\"}";

		}
    }
	
	/**
	 * @Description 
	 * 人员工资明细数据表<BR> 更新CostEmpWageDetail
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateCostEmpWageDetail(Map<String,Object> entityMap)throws DataAccessException{

		try {
			String scheme_id = entityMap.get("scheme_id").toString();
			StringBuffer sql = new StringBuffer();
			if(!"".equals(scheme_id) && scheme_id != null){
				List<CostWageSchemeSet> list = costWageSchemeSetMapper.queryCostWageSchemeSetByTitle(entityMap);
				for(int i = 0 ; i < list.size() ; i ++){
					if((i+1) == list.size()){
						sql.append(","+list.get(i).getWage_column()+"="+entityMap.get(list.get(i).getWage_column()));
					}else{
						sql.append(","+list.get(i).getWage_column()+"="+entityMap.get(list.get(i).getWage_column())+",");
					}
				}
			}
			entityMap.put("sql_value", sql.toString());
			costEmpWageDetailMapper.updateCostEmpWageDetail(entityMap);

			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updateCostEmpWageDetail\"}";

		}
	}
	
	/**
	 * @Description 
	 * 人员工资明细数据表<BR> 批量更新CostEmpWageDetail
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateBatchCostEmpWageDetail(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {

			costEmpWageDetailMapper.updateBatchCostEmpWageDetail(entityList);

			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updateCostEmpWageDetail\"}";

		}
		
	}
}
