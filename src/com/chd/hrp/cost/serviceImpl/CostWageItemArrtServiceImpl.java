/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/

package com.chd.hrp.cost.serviceImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.SysPage;
import com.chd.base.util.ChdJson;
import com.chd.hrp.acc.dao.AccWageItemsMapper;
import com.chd.hrp.acc.entity.AccWageItems;
import com.chd.hrp.cost.dao.CostWageDetailMapMapper;
import com.chd.hrp.cost.dao.CostWageItemArrtMapper;
import com.chd.hrp.cost.entity.CostWageItemArrt;
import com.chd.hrp.cost.service.CostWageItemArrtService;
import com.github.pagehelper.PageInfo;

/**
* @Title. @Description.
* 成本_工资项属性表<BR> 
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


@Service("costWageItemArrtService")
public class CostWageItemArrtServiceImpl implements CostWageItemArrtService {

	private static Logger logger = Logger.getLogger(CostWageItemArrtServiceImpl.class);
	
	@Resource(name = "costWageItemArrtMapper")
	private final CostWageItemArrtMapper costWageItemArrtMapper = null;
	
	@Resource(name = "costWageDetailMapMapper")
	private final CostWageDetailMapMapper costWageDetailMapMapper = null;
	
	@Resource(name = "accWageItemsMapper")
	private final AccWageItemsMapper accWageItemsMapper = null;
    
	/**
	 * @Description 
	 * 成本_工资项属性表<BR> 添加CostWageItemArrt
	 * @param CostWageItemArrt entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addCostWageItemArrt(Map<String,Object> entityMap)throws DataAccessException{
		
		CostWageItemArrt costWageItemArrt = queryCostWageItemArrtByCode(entityMap);

		if (costWageItemArrt != null) {

			return "{\"error\":\"已经存在此对应关系.\"}";

		}
		
		try {
			
			costWageItemArrtMapper.addCostWageItemArrt(entityMap);
			
			costWageDetailMapMapper.addCostWageDetailMap(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码 addCostWageItemArrt\"}";

		}

	}
	
	/**
	 * @Description 
	 * 成本_工资项属性表<BR> 批量添加CostWageItemArrt
	 * @param  CostWageItemArrt entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addBatchCostWageItemArrt(List<Map<String,Object>> entityList)throws DataAccessException{

		try {
			
			costWageItemArrtMapper.addBatchCostWageItemArrt(entityList);
			
			costWageDetailMapMapper.addBatchCostWageDetailMap(entityList);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码 addBatchCostWageItemArrt\"}";

		}
	}
	
	/**
	 * @Description 
	 * 成本_工资项属性表<BR> 查询CostWageItemArrt分页
	 * @param  entityMap RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String queryCostWageItemArrt(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		sysPage = (SysPage) entityMap.get("sysPage");
		if (sysPage.getTotal()==-1){
			List<CostWageItemArrt> list = costWageItemArrtMapper.queryCostWageItemArrt(entityMap);
			return ChdJson.toJson(list);
		}else{
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			List<CostWageItemArrt> list = costWageItemArrtMapper.queryCostWageItemArrt(entityMap, rowBounds);
			PageInfo page = new PageInfo(list);
			return ChdJson.toJson(list, page.getTotal());
		}
		
	}
	@Override
	public List<Map<String, Object>> queryCostWageItemArrtPrint(Map<String, Object> entityMap) throws DataAccessException {

		
		List<Map<String, Object>> list=costWageItemArrtMapper.queryCostWageItemArrtPrint(entityMap);
		
		return list;

	}
	/**
	 * @Description 
	 * 成本_工资项属性表<BR> 查询CostWageItemArrtByCode
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public CostWageItemArrt queryCostWageItemArrtByCode(Map<String,Object> entityMap)throws DataAccessException{
		
		return costWageItemArrtMapper.queryCostWageItemArrtByCode(entityMap);
		
	}
	
	/**
	 * @Description 
	 * 成本_工资项属性表<BR> 批量删除CostWageItemArrt
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String deleteBatchCostWageItemArrt(List<Map<String,Object>> entityList)throws DataAccessException{

		try {

				int state = costWageItemArrtMapper.deleteBatchCostWageItemArrt(entityList);
				return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码  deleteBatchCostWageItemArrt\"}";

		}
		
	}
	
	/**
	 * @Description 
	 * 成本_工资项属性表<BR> 删除CostWageItemArrt
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
  public String deleteCostWageItemArrt(Map<String, Object> entityMap) throws DataAccessException {
		
		try {
				costWageItemArrtMapper.deleteCostWageItemArrt(entityMap);
				return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码  deleteCostWageItemArrt\"}";

		}
    }
	
	/**
	 * @Description 
	 * 成本_工资项属性表<BR> 更新CostWageItemArrt
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateCostWageItemArrt(Map<String,Object> entityMap)throws DataAccessException{

		try {

			costWageItemArrtMapper.updateCostWageItemArrt(entityMap);

			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updateCostWageItemArrt\"}";

		}
	}
	
	/**
	 * @Description 
	 * 成本_工资项属性表<BR> 批量更新CostWageItemArrt
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateBatchCostWageItemArrt(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {

			costWageItemArrtMapper.updateBatchCostWageItemArrt(entityList);

			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updateCostWageItemArrt\"}";

		}
		
	}

	/**
	 * @Description
	 * 2016/10/28 lxj 
	 * 成本_工资项属性表<BR> 同步财务工资项到成本工资项
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String syncCostWageItemArrt(Map<String, Object> entityMap)throws DataAccessException {
		
		try {
			List<Map<String,Object>> mapList = new ArrayList<Map<String,Object>>();//用来存储组装后的财务工资项数据
			
			//1.查询财务工资项
			List<AccWageItems> accWageList = accWageItemsMapper.queryAccWageItems(entityMap);
			
			if(accWageList.size() == 0 ){
				
				return "{\"error\":\"同步失败,没有可同步的数据.\"}";
			}
			
			//2.查询成本工资项
			 List<CostWageItemArrt> costWageList = costWageItemArrtMapper.queryCostWageItemArrt(entityMap);
			
			 
			 //3.比较工资项
			 if(costWageList.size() > 0 ){//如果成本工资项有数据存在,就比较
				 
				 for(int x = 0 ; x < costWageList.size(); x++){
					 
					 for(int y = 0; y < accWageList.size(); y++){
						 
						 if(accWageList.get(y).getItem_code().equals(costWageList.get(x).getWage_item_code())){
							 
							 accWageList.remove(y);
						 }
					 }
				 }
				 
				 if(accWageList.size() == 0 ){
					 
					 return "{\"error\":\"同步失败,数据编码已存在.\"}";
				 }
				 
				 for(int x = 0 ; x < accWageList.size(); x++){
					 
					 Map<String,Object> mapVo = new HashMap<String,Object>();
					 
					 mapVo.put("group_id", accWageList.get(x).getGroup_id());
					 mapVo.put("hos_id", accWageList.get(x).getHos_id());
					 mapVo.put("copy_code", accWageList.get(x).getCopy_code());
					 mapVo.put("wage_item_code", accWageList.get(x).getItem_code());
					 mapVo.put("wage_item_name", accWageList.get(x).getItem_name());
					 mapVo.put("is_stop", accWageList.get(x).getIs_stop());
					 if(accWageList.get(x).getNote() != null){
						 mapVo.put("remark", accWageList.get(x).getNote());
					 }else{
						 mapVo.put("remark","");
					 }
					 if(accWageList.get(x).getSpell_code() != null){
						 mapVo.put("spell_code", accWageList.get(x).getSpell_code());
					 }else{
						 mapVo.put("spell_code","");
					 }
					 if(accWageList.get(x).getWbx_code() != null){
						 mapVo.put("wbx_code",accWageList.get(x).getWbx_code());
					 }else{
						 mapVo.put("wbx_code","");
					 }
					 mapList.add(mapVo);
				 }
				 
				 costWageItemArrtMapper.addBatchCostWageItemArrt(mapList);
				 
				 return "{\"msg\":\"同步成功.\",\"state\":\"true\"}";
				 
			 }else{//没有成本工资项有数据存在,直接添加
				 
				 for(int x = 0 ; x < accWageList.size(); x++){
					 
					 Map<String,Object> mapVo = new HashMap<String,Object>();
					 
					 mapVo.put("group_id", accWageList.get(x).getGroup_id());
					 mapVo.put("hos_id", accWageList.get(x).getHos_id());
					 mapVo.put("copy_code", accWageList.get(x).getCopy_code());
					 mapVo.put("wage_item_code", accWageList.get(x).getItem_code());
					 mapVo.put("wage_item_name", accWageList.get(x).getItem_name());
					 mapVo.put("is_stop", accWageList.get(x).getIs_stop());
					 if(accWageList.get(x).getNote() != null){
						 mapVo.put("remark", accWageList.get(x).getNote());
					 }else{
						 mapVo.put("remark","");
					 }
					 if(accWageList.get(x).getSpell_code() != null){
						 mapVo.put("spell_code", accWageList.get(x).getSpell_code());
					 }else{
						 mapVo.put("spell_code","");
					 }
					 if(accWageList.get(x).getWbx_code() != null){
						 mapVo.put("wbx_code",accWageList.get(x).getWbx_code());
					 }else{
						 mapVo.put("wbx_code","");
					 }
					 mapList.add(mapVo);
				 }
				 
				 costWageItemArrtMapper.addBatchCostWageItemArrt(mapList);
				 
				 return "{\"msg\":\"同步成功.\",\"state\":\"true\"}";
			 }
			
		} catch (Exception e) {
			// TODO: handle exception
			return "{\"error\":\"同步失败 数据库异常 请联系管理员! 错误编码  syncCostWageItemArrt\"}";

		}
	}
	/**
	 * @Description
	 * 2016/10/28 lxj 
	 * 成本_工资项属性表<BR> 同步财务工资项到成本工资项
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String syncCostWageItemArrtNew(Map<String, Object> mapVo) {
		 costWageItemArrtMapper.addBatchCostWageItemArrtNew(mapVo);
		 
		 return "{\"msg\":\"同步成功.\",\"state\":\"true\"}";
	}
}
