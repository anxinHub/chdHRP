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
import com.chd.hrp.ass.dao.dict.AssTypeDictMapper;
import com.chd.hrp.ass.entity.dict.AssTypeDict;
import com.chd.hrp.cost.dao.CostFassetArrtMapper;
import com.chd.hrp.cost.dao.CostFassetTypeArrtMapper;
import com.chd.hrp.cost.entity.CostFassetTypeArrt;
import com.chd.hrp.cost.service.CostFassetTypeArrtService;
import com.github.pagehelper.PageInfo;

/**
* @Title. @Description.
* 成本_固定资产分类字典<BR> 
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


@Service("costFassetTypeArrtService")
public class CostFassetTypeArrtServiceImpl implements CostFassetTypeArrtService {

	private static Logger logger = Logger.getLogger(CostFassetTypeArrtServiceImpl.class);
	
	@Resource(name = "costFassetTypeArrtMapper")
	private final CostFassetTypeArrtMapper costFassetTypeArrtMapper = null;
	
	@Resource(name = "assTypeDictMapper")
	private final AssTypeDictMapper assTypeDictMapper = null;
	
	@Resource(name = "costFassetArrtMapper")
	private final CostFassetArrtMapper costFassetArrtMapper = null;
    
	/**
	 * @Description 
	 * 成本_固定资产分类字典<BR> 添加CostFassetTypeArrt
	 * @param CostFassetTypeArrt entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addCostFassetTypeArrt(Map<String,Object> entityMap)throws DataAccessException{
		
		Map<String, Object> byCodeMap = new HashMap<String, Object>();
		
		byCodeMap.put("group_id", entityMap.get("group_id"));
		
		byCodeMap.put("hos_id", entityMap.get("hos_id"));
		
		byCodeMap.put("copy_code", entityMap.get("copy_code"));
		
		byCodeMap.put("asset_type_code", entityMap.get("asset_type_code"));
		
		CostFassetTypeArrt costFassetTypeArrt = queryCostFassetTypeArrtByCode(byCodeMap);

		if (costFassetTypeArrt != null) {

			return "{\"error\":\"编码：" + entityMap.get("asset_type_code").toString() + "重复.\"}";

		}
		
		try {
			
			costFassetTypeArrtMapper.addCostFassetTypeArrt(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码 addCostFassetTypeArrt\"}";

		}

	}
	
	/**
	 * @Description 
	 * 成本_固定资产分类字典<BR> 批量添加CostFassetTypeArrt
	 * @param  CostFassetTypeArrt entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addBatchCostFassetTypeArrt(List<Map<String,Object>> entityList)throws DataAccessException{

		try {
			
			costFassetTypeArrtMapper.addBatchCostFassetTypeArrt(entityList);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码 addBatchCostFassetTypeArrt\"}";

		}
	}
	
	/**
	 * @Description 
	 * 成本_固定资产分类字典<BR> 查询CostFassetTypeArrt分页
	 * @param  entityMap RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String queryCostFassetTypeArrt(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		sysPage = (SysPage) entityMap.get("sysPage");
		if (sysPage.getTotal()==-1){
			List<CostFassetTypeArrt> list = costFassetTypeArrtMapper.queryCostFassetTypeArrt(entityMap);
			return ChdJson.toJson(list);
		}else{
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			List<CostFassetTypeArrt> list = costFassetTypeArrtMapper.queryCostFassetTypeArrt(entityMap, rowBounds);
			PageInfo page = new PageInfo(list);
			return ChdJson.toJson(list, page.getTotal());
		}
		
	}
	@Override
	public List<Map<String, Object>> queryCostFassetTypeArrtPrint(Map<String, Object> entityMap) throws DataAccessException {

		
		List<Map<String, Object>> list=costFassetTypeArrtMapper.queryCostFassetTypeArrtPrint(entityMap);
		
		return list;

	}
	/**
	 * @Description 
	 * 成本_固定资产分类字典<BR> 查询CostFassetTypeArrtByCode
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public CostFassetTypeArrt queryCostFassetTypeArrtByCode(Map<String,Object> entityMap)throws DataAccessException{
		
		return costFassetTypeArrtMapper.queryCostFassetTypeArrtByCode(entityMap);
		
	}
	
	/**
	 * @Description 
	 * 成本_固定资产分类字典<BR> 批量删除CostFassetTypeArrt
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String deleteBatchCostFassetTypeArrt(List<Map<String,Object>> entityList)throws DataAccessException{

		try {

				int state = costFassetTypeArrtMapper.deleteBatchCostFassetTypeArrt(entityList);
				return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码  deleteBatchCostFassetTypeArrt\"}";

		}
		
	}
	
	/**
	 * @Description 
	 * 成本_固定资产分类字典<BR> 删除CostFassetTypeArrt
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
  public String deleteCostFassetTypeArrt(Map<String, Object> entityMap) throws DataAccessException {
		
		try {
				costFassetTypeArrtMapper.deleteCostFassetTypeArrt(entityMap);
				return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码  deleteCostFassetTypeArrt\"}";

		}
    }
	
	/**
	 * @Description 
	 * 成本_固定资产分类字典<BR> 更新CostFassetTypeArrt
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateCostFassetTypeArrt(Map<String,Object> entityMap)throws DataAccessException{

		try {

			costFassetTypeArrtMapper.updateCostFassetTypeArrt(entityMap);

			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updateCostFassetTypeArrt\"}";

		}
	}
	
	/**
	 * @Description 
	 * 成本_固定资产分类字典<BR> 批量更新CostFassetTypeArrt
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateBatchCostFassetTypeArrt(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {

			costFassetTypeArrtMapper.updateBatchCostFassetTypeArrt(entityList);

			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updateCostFassetTypeArrt\"}";

		}
		
	}
	
	/**
	 * @Description 
	 * 2016/10/31 lxj 
	 * 成本_固定资产分类字典<BR> 同步CostFassetTypeArrt
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String syncCostFassetTypeArrt(Map<String, Object> entityMap) throws DataAccessException {
		
		try {
			
			entityMap.put("ass_fassetcode", "05");
			
			//1.查询固定资产分类
			List<AssTypeDict> assTypeList = assTypeDictMapper.queryAssTypeDict(entityMap);
			
			if(assTypeList.size() == 0 ){
				
				return "{\"error\":\"未找到固定资产分类数据.\"}";
			}
			
			List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
			
			for(int x = 0 ; x < assTypeList.size(); x++){
				
				Map<String,Object> mapVo = new HashMap<String,Object>();
				mapVo.put("group_id", assTypeList.get(x).getGroup_id());
				mapVo.put("hos_id", assTypeList.get(x).getHos_id());
				mapVo.put("copy_code", assTypeList.get(x).getCopy_code());
				mapVo.put("asset_type_code", assTypeList.get(x).getAss_type_code());
				mapVo.put("asset_type_name", assTypeList.get(x).getAss_type_name());
				if(assTypeList.get(x).getSuper_code() != null){
					mapVo.put("supper_code",assTypeList.get(x).getSuper_code());
				}else{
					mapVo.put("supper_code", "TOP");
				}
				
				mapVo.put("is_last", assTypeList.get(x).getIs_last());
				
				if(assTypeList.get(x).getSpell_code() != null){
					mapVo.put("spell_code", assTypeList.get(x).getSpell_code());
				}else{
					mapVo.put("spell_code", "");
				}
				
				if( assTypeList.get(x).getWbx_code() != null){
					mapVo.put("wbx_code", assTypeList.get(x).getWbx_code());
				}else{
					mapVo.put("wbx_code", "");
				}
				
				list.add(mapVo);
			}
			
//			//2.删除成本固定资产
//			costFassetArrtMapper.deleteAllCostFassetArrt(entityMap);
//			//3.删除成本固定资产分类
//			costFassetTypeArrtMapper.deleteAllCostFassetTypeArrt(entityMap);
			//4.添加固定资产分类
			costFassetTypeArrtMapper.addBatchCostFassetTypeArrt(list);
			
			return "{\"msg\":\"同步成功.\",\"state\":\"true\"}";
			
		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"同步失败 数据库异常 请联系管理员! 错误编码  syncCostFassetTypeArrt\"}";

		}
	}

	/**
	 * @Description 
	 * 2016/10/31 lxj 
	 * 成本_固定资产分类字典<BR> 同步CostFassetTypeArrt
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String syncCostFassetTypeArrtFNew(Map<String, Object> mapVo)
			throws DataAccessException {
		costFassetTypeArrtMapper.addBatchCostMateTypeArrtNew(mapVo);
		
		return "{\"msg\":\"同步成功.\",\"state\":\"true\"}";
	}
}
