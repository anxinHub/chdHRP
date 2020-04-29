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

import com.chd.base.SessionManager;
import com.chd.base.SysPage;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.base.util.ReadFiles;
import com.chd.base.util.StringTool;
import com.chd.hrp.cost.dao.CostChargeItemArrtMapper;
import com.chd.hrp.cost.entity.CostChargeItemArrt;
import com.chd.hrp.cost.entity.CostChargeKindArrt;
import com.chd.hrp.cost.service.CostChargeItemArrtService;
import com.chd.hrp.hip.entity.HipDeptRef;
import com.chd.hrp.sys.entity.DeptDict;
import com.github.pagehelper.PageInfo;

/**
* @Title. @Description.
* 成本_收费项目字典<BR> 
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


@Service("costChargeItemArrtService")
public class CostChargeItemArrtServiceImpl implements CostChargeItemArrtService {

	private static Logger logger = Logger.getLogger(CostChargeItemArrtServiceImpl.class);
	
	@Resource(name = "costChargeItemArrtMapper")
	private final CostChargeItemArrtMapper costChargeItemArrtMapper = null;
    
	@Resource(name = "costChargeKindArrtService")
	private final CostChargeKindArrtServiceImpl costChargeKindArrtService = null;
	/**
	 * @Description 
	 * 成本_收费项目字典<BR> 添加CostChargeItemArrt
	 * @param CostChargeItemArrt entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addCostChargeItemArrt(Map<String,Object> entityMap)throws DataAccessException{
		
		CostChargeItemArrt costChargeItemArrt = queryCostChargeItemArrtByCode(entityMap);

		if (costChargeItemArrt != null) {

			return "{\"error\":\"编码：" + entityMap.get("charge_item_code").toString() + "重复.\"}";

		}
		
		try {
			
			costChargeItemArrtMapper.addCostChargeItemArrt(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码 addCostChargeItemArrt\"}";

		}

	}
	
	/**
	 * @Description 
	 * 成本_收费项目字典<BR> 批量添加CostChargeItemArrt
	 * @param  CostChargeItemArrt entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addBatchCostChargeItemArrt(List<Map<String,Object>> entityList)throws DataAccessException{

		try {
			
			costChargeItemArrtMapper.addBatchCostChargeItemArrt(entityList);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码 addBatchCostChargeItemArrt\"}";

		}
	}
	
	/**
	 * @Description 
	 * 成本_收费项目字典<BR> 查询CostChargeItemArrt分页
	 * @param  entityMap RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String queryCostChargeItemArrt(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		sysPage = (SysPage) entityMap.get("sysPage");
		if (sysPage.getTotal()==-1){
			List<CostChargeItemArrt> list = costChargeItemArrtMapper.queryCostChargeItemArrt(entityMap);
			return ChdJson.toJson(list);
		}else{
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			List<CostChargeItemArrt> list = costChargeItemArrtMapper.queryCostChargeItemArrt(entityMap, rowBounds);
			PageInfo page = new PageInfo(list);
			return ChdJson.toJson(list, page.getTotal());
		}
		
	}
	 @Override
	 public List<Map<String, Object>> queryCostChargeItemArrtPrint(Map<String, Object> entityMap) throws DataAccessException {
			
			
			List<Map<String, Object>> list=costChargeItemArrtMapper.queryCostChargeItemArrtPrint(entityMap);
			
			return list;

	 }
	/**
	 * @Description 
	 * 成本_收费项目字典<BR> 查询CostChargeItemArrt分页
	 * @param  entityMap RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String queryCostChargeItemArrtByKindCode(Map<String,Object> entityMap) throws DataAccessException{
		
			List<CostChargeItemArrt> list = costChargeItemArrtMapper.queryCostChargeItemArrtByKindCode(entityMap);
			return ChdJson.toJson(list);
		
	}
	
	/**
	 * @Description 
	 * 成本_收费项目字典<BR> 查询CostChargeItemArrtByCode
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public CostChargeItemArrt queryCostChargeItemArrtByCode(Map<String,Object> entityMap)throws DataAccessException{
		
		return costChargeItemArrtMapper.queryCostChargeItemArrtByCode(entityMap);
		
	}
	
	/**
	 * @Description 
	 * 成本_收费项目字典<BR> 批量删除CostChargeItemArrt
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String deleteBatchCostChargeItemArrt(List<Map<String,Object>> entityList)throws DataAccessException{

		try {

				int state = costChargeItemArrtMapper.deleteBatchCostChargeItemArrt(entityList);
				return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码  deleteBatchCostChargeItemArrt\"}";

		}
		
	}
	
	/**
	 * @Description 
	 * 成本_收费项目字典<BR> 删除CostChargeItemArrt
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
  public String deleteCostChargeItemArrt(Map<String, Object> entityMap) throws DataAccessException {
		
		try {
				costChargeItemArrtMapper.deleteCostChargeItemArrt(entityMap);
				return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码  deleteCostChargeItemArrt\"}";

		}
    }
	
	/**
	 * @Description 
	 * 成本_收费项目字典<BR> 更新CostChargeItemArrt
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateCostChargeItemArrt(Map<String,Object> entityMap)throws DataAccessException{

		try {

			costChargeItemArrtMapper.updateCostChargeItemArrt(entityMap);

			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updateCostChargeItemArrt\"}";

		}
	}
	
	/**
	 * @Description 
	 * 成本_收费项目字典<BR> 批量更新CostChargeItemArrt
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateBatchCostChargeItemArrt(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {

			costChargeItemArrtMapper.updateBatchCostChargeItemArrt(entityList);

			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updateCostChargeItemArrt\"}";

		}
		
	}
	
	@Override
	public String costChargeItemArrtImportPage(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		String dataJson = null;
		
		try {
			
			List<Map<String, List<String>>> list = ReadFiles.readFiles(entityMap);
			
			if (list.size() == 0 || list == null) {
				
				return "{\"error\":\"表头或者数据为空！请重新导入.\"}";
			}
			
			for (Map<String, List<String>> map2 : list) {
				
				if(null == map2.get("charge_item_code").get(1) || ("").equals(map2.get("charge_item_code").get(1))){

					return "{\"msg\":\"" + map2.get("charge_item_code").get(0) + "，收费项目编码为空！\",\"state\":\"false\",\"row_cell\":\"" + map2.get("charge_item_code").get(0)
					        + "\"}";
					
				}else if(null == map2.get("charge_item_name").get(1) || ("").equals(map2.get("charge_item_name").get(1))){
					
					return "{\"msg\":\"" + map2.get("charge_item_name").get(0) + "，收费项目名称为空！\",\"state\":\"false\",\"row_cell\":\""
					        + map2.get("charge_item_name").get(0) + "\"}";
				
				}else if(null == map2.get("charge_kind_code").get(1) || ("").equals(map2.get("charge_kind_code").get(1))){
					
					return "{\"msg\":\"" + map2.get("charge_kind_code").get(0) + "，收费类别编码为空！\",\"state\":\"false\",\"row_cell\":\""
					        + map2.get("charge_kind_code").get(0) + "\"}";
				
				}else if(null == map2.get("charge_kind_name").get(1) || ("").equals(map2.get("charge_kind_name").get(1))){
					
					return "{\"msg\":\"" + map2.get("charge_kind_name").get(0) + "，时程名称为空！\",\"state\":\"false\",\"row_cell\":\""
					        + map2.get("charge_kind_name").get(0) + "\"}";
				
				}else if(null == map2.get("price").get(1) || ("").equals(map2.get("price").get(1))){
					
					return "{\"msg\":\"" + map2.get("price").get(0) + "，价格为空！\",\"state\":\"false\",\"row_cell\":\""
					        + map2.get("price").get(0) + "\"}";
				
				}else if(null == map2.get("is_stop").get(1) || ("").equals(map2.get("is_stop").get(1))){
					
					return "{\"msg\":\"" + map2.get("is_stop").get(0) + "，是否停用为空！\",\"state\":\"false\",\"row_cell\":\""
					        + map2.get("is_stop").get(0) + "\"}";
				
				}
				
				Map<String, Object> mapVo = new HashMap<String, Object>();
				
				mapVo.put("group_id", SessionManager.getGroupId());

				mapVo.put("hos_id", SessionManager.getHosId());

				mapVo.put("copy_code", SessionManager.getCopyCode());
				
				mapVo.put("charge_item_code", map2.get("charge_item_code").get(1));
				
				mapVo.put("charge_item_name", map2.get("charge_item_name").get(1));
				
				mapVo.put("price", map2.get("price").get(1));
				
				mapVo.put("is_stop", map2.get("is_stop").get(1));
				
				//根据名称生成拼音码
		        mapVo.put("spell_code", StringTool.toPinyinShouZiMu(map2.get("charge_item_name").get(1).toString()));
				//根据名称生成五笔码
		        mapVo.put("wbx_code", StringTool.toWuBi(map2.get("charge_item_name").get(1).toString()));
				
				// 判断唯一性 编码
				Map<String, Object> map_code = new HashMap<String, Object>();
				
				map_code.put("group_id", mapVo.get("group_id"));
				
				map_code.put("hos_id", mapVo.get("hos_id"));
				
				map_code.put("copy_code", mapVo.get("copy_code"));
				
				map_code.put("charge_kind_code", map2.get("charge_kind_code").get(1));
				
				CostChargeKindArrt chargeKindArrt = costChargeKindArrtService.queryCostChargeKindArrtByCode(map_code);
				if(chargeKindArrt != null){
					mapVo.put("charge_kind_id", chargeKindArrt.getCharge_kind_id());
				}else{
					return "{\"msg\":\"" + map2.get("charge_kind_code").get(0) + "，收费类别不存在！\",\"state\":\"false\",\"row_cell\":\""
					        + map2.get("charge_kind_code").get(0) + "\"}";
				}
				
				Map<String, Object> map_dept_code = new HashMap<String, Object>();
				map_dept_code.put("group_id", mapVo.get("group_id"));
				map_dept_code.put("hos_id", mapVo.get("hos_id"));
				map_dept_code.put("copy_code", mapVo.get("copy_code"));
				map_dept_code.put("charge_item_code", mapVo.get("charge_item_code"));
				
				CostChargeItemArrt data_exc_extis = this.queryCostChargeItemArrtByCode(map_dept_code);
				if (data_exc_extis != null) {
					return "{\"warn\":\"" + map2.get("charge_item_code").get(0) + ",编码已存在！\",\"state\":\"false\",\"row_cell\":\""
					        + map2.get("charge_item_code").get(0) + "\"}";
				}
				
				dataJson =addCostChargeItemArrt(mapVo);
				
			}
			    return dataJson;
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			logger.debug(e.getMessage());
			throw new SysException(e.getMessage());
		}
	}
}
