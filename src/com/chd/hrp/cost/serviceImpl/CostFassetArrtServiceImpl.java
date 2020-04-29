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
import com.chd.hrp.ass.dao.dict.AssDictMapper;
import com.chd.hrp.ass.dao.dict.AssTypeDictMapper;
import com.chd.hrp.ass.entity.dict.AssDict;
import com.chd.hrp.ass.entity.dict.AssTypeDict;
import com.chd.hrp.cost.dao.CostFassetArrtMapper;
import com.chd.hrp.cost.entity.CostFassetArrt;
import com.chd.hrp.cost.service.CostFassetArrtService;
import com.github.pagehelper.PageInfo;

/**
* @Title. @Description.
* 成本_固定资产字典<BR> 
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


@Service("costFassetArrtService")
public class CostFassetArrtServiceImpl implements CostFassetArrtService {

	private static Logger logger = Logger.getLogger(CostFassetArrtServiceImpl.class);
	
	@Resource(name = "costFassetArrtMapper")
	private final CostFassetArrtMapper costFassetArrtMapper = null;
	@Resource(name = "assDictMapper")
	private final AssDictMapper assDictMapper = null;
    
	/**
	 * @Description 
	 * 成本_固定资产字典<BR> 添加CostFassetArrt
	 * @param CostFassetArrt entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addCostFassetArrt(Map<String,Object> entityMap)throws DataAccessException{
		
		Map<String, Object> byCodeMap = new HashMap<String, Object>();
		
		byCodeMap.put("group_id", entityMap.get("group_id"));
		
		byCodeMap.put("hos_id", entityMap.get("hos_id"));
		
		byCodeMap.put("copy_code", entityMap.get("copy_code"));
		
		byCodeMap.put("asset_code", entityMap.get("asset_code"));

		CostFassetArrt costFassetArrt = queryCostFassetArrtByCode(entityMap);

		if (costFassetArrt != null) {

			return "{\"error\":\"编码：" + entityMap.get("asset_code").toString() + "重复.\"}";

		}
		
		try {
			
			costFassetArrtMapper.addCostFassetArrt(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码 addCostFassetArrt\"}";

		}

	}
	
	/**
	 * @Description 
	 * 成本_固定资产字典<BR> 批量添加CostFassetArrt
	 * @param  CostFassetArrt entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addBatchCostFassetArrt(List<Map<String,Object>> entityList)throws DataAccessException{

		try {
			
			costFassetArrtMapper.addBatchCostFassetArrt(entityList);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码 addBatchCostFassetArrt\"}";

		}
	}
	
	/**
	 * @Description 
	 * 成本_固定资产字典<BR> 查询CostFassetArrt分页
	 * @param  entityMap RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String queryCostFassetArrt(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		sysPage = (SysPage) entityMap.get("sysPage");
		if (sysPage.getTotal()==-1){
			List<CostFassetArrt> list = costFassetArrtMapper.queryCostFassetArrt(entityMap);
			return ChdJson.toJson(list);
		}else{
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			List<CostFassetArrt> list = costFassetArrtMapper.queryCostFassetArrt(entityMap, rowBounds);
			PageInfo page = new PageInfo(list);
			return ChdJson.toJson(list, page.getTotal());
		}
		
	}
	@Override
	public List<Map<String, Object>> queryCostFassetArrtPrint(Map<String, Object> entityMap) throws DataAccessException {

		
		List<Map<String, Object>> list=costFassetArrtMapper.queryCostFassetArrtPrint(entityMap);
		
		return list;

	}
	/**
	 * @Description 
	 * 成本_固定资产字典<BR> 查询CostFassetArrtByCode
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public CostFassetArrt queryCostFassetArrtByCode(Map<String,Object> entityMap)throws DataAccessException{
		
		return costFassetArrtMapper.queryCostFassetArrtByCode(entityMap);
		
	}
	
	/**
	 * @Description 
	 * 成本_固定资产字典<BR> 批量删除CostFassetArrt
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String deleteBatchCostFassetArrt(List<Map<String,Object>> entityList)throws DataAccessException{

		try {

				int state = costFassetArrtMapper.deleteBatchCostFassetArrt(entityList);
				return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码  deleteBatchCostFassetArrt\"}";

		}
		
	}
	
	/**
	 * @Description 
	 * 成本_固定资产字典<BR> 删除CostFassetArrt
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
  public String deleteCostFassetArrt(Map<String, Object> entityMap) throws DataAccessException {
		
		try {
				costFassetArrtMapper.deleteCostFassetArrt(entityMap);
				return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码  deleteCostFassetArrt\"}";

		}
    }
	
	/**
	 * @Description 
	 * 成本_固定资产字典<BR> 更新CostFassetArrt
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateCostFassetArrt(Map<String,Object> entityMap)throws DataAccessException{

		try {

			costFassetArrtMapper.updateCostFassetArrt(entityMap);

			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updateCostFassetArrt\"}";

		}
	}
	
	/**
	 * @Description 
	 * 成本_固定资产字典<BR> 批量更新CostFassetArrt
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateBatchCostFassetArrt(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {

			costFassetArrtMapper.updateBatchCostFassetArrt(entityList);

			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updateCostFassetArrt\"}";

		}
		
	}

	@Override
	public String syncCostFassetArrt(Map<String, Object> entityMap)throws DataAccessException {
		// TODO Auto-generated method stub
		try {
			
			
			//1.查询固定资产分类
			List<AssDict> assList = assDictMapper.queryAssDict(entityMap);
			
			if(assList.size() == 0 ){
				
				return "{\"error\":\"未找到固定资产分类数据.\"}";
			}
			
			List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
			
			for(int x = 0 ; x < assList.size(); x++){
				
				Map<String,Object> mapVo = new HashMap<String,Object>();
				mapVo.put("group_id", assList.get(x).getGroup_id());
				mapVo.put("hos_id", assList.get(x).getHos_id());
				mapVo.put("copy_code", assList.get(x).getCopy_code());
				mapVo.put("asset_type_id", assList.get(x).getAss_type_id());
				mapVo.put("asset_code", assList.get(x).getAss_code());
				mapVo.put("asset_name", assList.get(x).getAss_name());
				
				mapVo.put("dep_year", assList.get(x).getDepre_years());
			
				if(assList.get(x).getSpell_code() != null){
					mapVo.put("spell_code", assList.get(x).getSpell_code());
				}else{
					mapVo.put("spell_code", "");
				}
				
				if( assList.get(x).getWbx_code() != null){
					mapVo.put("wbx_code", assList.get(x).getWbx_code());
				}else{
					mapVo.put("wbx_code", "");
				}
				
				list.add(mapVo);
			}
			
			//4.添加固定资产
			costFassetArrtMapper.addBatchCostFassetArrt(list);
			
			return "{\"msg\":\"同步成功.\",\"state\":\"true\"}";
			
		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"同步失败 数据库异常 请联系管理员! 错误编码  syncCostFassetTypeArrt\"}";

		}
	}

	@Override
	public String syncCostFassetArrtNew(Map<String, Object> mapVo)
			throws DataAccessException {
				costFassetArrtMapper.addBatchCostFassetArrtNew(mapVo);
		
				return "{\"msg\":\"同步成功.\",\"state\":\"true\"}";
	}
}
