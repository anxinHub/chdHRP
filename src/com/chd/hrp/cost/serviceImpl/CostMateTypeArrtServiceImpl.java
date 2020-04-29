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

import com.chd.base.SessionManager;
import com.chd.base.SysPage;
import com.chd.base.util.ChdJson;
import com.chd.hrp.cost.dao.CostMateArrtMapper;
import com.chd.hrp.cost.dao.CostMateTypeArrtMapper;
import com.chd.hrp.cost.entity.CostMateArrt;
import com.chd.hrp.cost.entity.CostMateTypeArrt;
import com.chd.hrp.cost.service.CostMateTypeArrtService;
import com.chd.hrp.mat.dao.info.basic.MatTypeMapper;
import com.chd.hrp.mat.entity.MatType;
import com.github.pagehelper.PageInfo;

/**
 * @Title. @Description. 成本_材料分类字典<BR>
 * @Author: LiuYingDuo
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Service("costMateTypeArrtService")
public class CostMateTypeArrtServiceImpl implements CostMateTypeArrtService {

	private static Logger logger = Logger.getLogger(CostMateTypeArrtServiceImpl.class);
 
	@Resource(name = "costMateTypeArrtMapper")
	private final CostMateTypeArrtMapper costMateTypeArrtMapper = null;
	
	@Resource(name = "costMateArrtMapper")
	private final CostMateArrtMapper costMateArrtMapper = null;
	
	@Resource(name = "matTypeMapper")
	private final MatTypeMapper matTypeMapper= null;
	
	

	/**
	 * @Description 成本_材料分类字典<BR>
	 *              添加CostMateTypeArrt
	 * @param CostMateTypeArrt
	 *            entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String addCostMateTypeArrt(Map<String, Object> entityMap) throws DataAccessException {

		Map<String, Object> byCodeMap = new HashMap<String, Object>();
		
		byCodeMap.put("group_id", entityMap.get("group_id"));
		
		byCodeMap.put("hos_id", entityMap.get("hos_id"));
		
		byCodeMap.put("copy_code", entityMap.get("copy_code"));
		
		byCodeMap.put("mate_type_code", entityMap.get("mate_type_code"));
	
		CostMateTypeArrt costMateTypeArrt = queryCostMateTypeArrtByCode(byCodeMap);

		if (costMateTypeArrt != null) {

			return "{\"error\":\"编码：" + entityMap.get("mate_type_code").toString() + "重复.\"}";

		}

		try {

			costMateTypeArrtMapper.addCostMateTypeArrt(entityMap);

			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码 addCostMateTypeArrt\"}";

		}

	}

	/**
	 * @Description 成本_材料分类字典<BR>
	 *              批量添加CostMateTypeArrt
	 * @param CostMateTypeArrt
	 *            entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String addBatchCostMateTypeArrt(List<Map<String, Object>> entityList) throws DataAccessException {

		try {

			costMateTypeArrtMapper.addBatchCostMateTypeArrt(entityList);

			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码 addBatchCostMateTypeArrt\"}";

		}
	}

	/**
	 * @Description 成本_材料分类字典<BR>
	 *              查询CostMateTypeArrt分页
	 * @param entityMap
	 *            RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String queryCostMateTypeArrt(Map<String, Object> entityMap) throws DataAccessException {

		SysPage sysPage = new SysPage();
		sysPage = (SysPage) entityMap.get("sysPage");
		if (sysPage.getTotal() == -1) {
			List<CostMateTypeArrt> list = costMateTypeArrtMapper.queryCostMateTypeArrt(entityMap);
			return ChdJson.toJson(list);
		} else {
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			List<CostMateTypeArrt> list = costMateTypeArrtMapper.queryCostMateTypeArrt(entityMap, rowBounds);
			PageInfo page = new PageInfo(list);
			return ChdJson.toJson(list, page.getTotal());
		}

	}
	@Override
	public List<Map<String, Object>> queryCostMateTypeArrtPrint(Map<String, Object> entityMap) throws DataAccessException {

		
		List<Map<String, Object>> list=costMateTypeArrtMapper.queryCostMateTypeArrtPrint(entityMap);
		
		return list;

	}
	/**
	 * @Description 成本_材料分类字典<BR>
	 *              查询CostMateTypeArrtByCode
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public CostMateTypeArrt queryCostMateTypeArrtByCode(Map<String, Object> entityMap) throws DataAccessException {

		return costMateTypeArrtMapper.queryCostMateTypeArrtByCode(entityMap);

	}

	/**
	 * @Description 成本_材料分类字典<BR>
	 *              批量删除CostMateTypeArrt
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String deleteBatchCostMateTypeArrt(List<Map<String, Object>> entityList) throws DataAccessException {

		try {
			
			int state = costMateTypeArrtMapper.deleteBatchCostMateTypeArrt(entityList);
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码  deleteBatchCostMateTypeArrt\"}";

		}

	}

	/**
	 * @Description 成本_材料分类字典<BR>
	 *              删除CostMateTypeArrt
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String deleteCostMateTypeArrt(Map<String, Object> entityMap) throws DataAccessException {

		try {
			
			costMateTypeArrtMapper.deleteCostMateTypeArrt(entityMap);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码  deleteCostMateTypeArrt\"}";

		}
	}

	/**
	 * @Description 成本_材料分类字典<BR>
	 *              更新CostMateTypeArrt
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String updateCostMateTypeArrt(Map<String, Object> entityMap) throws DataAccessException {
		
		try {

			costMateTypeArrtMapper.updateCostMateTypeArrt(entityMap);

			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updateCostMateTypeArrt\"}";

		}
	}

	/**
	 * @Description 成本_材料分类字典<BR>
	 *              批量更新CostMateTypeArrt
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String updateBatchCostMateTypeArrt(List<Map<String, Object>> entityList) throws DataAccessException {

		try {

			costMateTypeArrtMapper.updateBatchCostMateTypeArrt(entityList);

			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updateCostMateTypeArrt\"}";

		}

	}

	/**
	 * 2016/10/31 lxj
	 * @Description 成本_材料分类字典<BR>
	 *              同步
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String syncCostMateTypeArrt(Map<String, Object> entityMap) throws DataAccessException {
		
		try {
			List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
			//1.查询物资分类所有数据
			List<MatType> matTypeList = matTypeMapper.queryMatType(entityMap);
			
			if(matTypeList.size() == 0 ){
				
				return "{\"error\":\"未找到同步的数据.\"}";
			}
			
			List<CostMateTypeArrt> mateTypeList = costMateTypeArrtMapper.queryCostMateTypeArrt(entityMap);
			
			//2.删除成本材料信息和材料分类所有数据
//			costMateArrtMapper.deleteCostMateArrtAll(entityMap);//删除材料信息
//			costMateTypeArrtMapper.deleteCostMateTypeArrtAll(entityMap);//删除材料分类信息
			
			
			//3.同步数据到成本分类中
			if(mateTypeList.size() > 0 ){//成本科室职工分类 有数据就比较
				
				for(int x = 0;x < mateTypeList.size(); x++){
					
					for(int y = 0 ; y < matTypeList.size() ; y++){
						
						if(matTypeList.get(y).getMat_type_code().equals(mateTypeList.get(x).getMate_type_code())){//如果相等就删除
							
							matTypeList.remove(y);
						
						}
					}
				}
				
				if(matTypeList.size() == 0){
					
					return "{\"error\":\"同步失败,数据已存在.\"}";
				}
			
			for(int x = 0 ; x < matTypeList.size(); x++){
				
				Map<String,Object> mapVo = new HashMap<String,Object>();
				
				mapVo.put("group_id", matTypeList.get(x).getGroup_id());
				
				mapVo.put("hos_id", matTypeList.get(x).getHos_id());
				
				mapVo.put("copy_code", matTypeList.get(x).getCopy_code());
				
				mapVo.put("mate_type_code", matTypeList.get(x).getMat_type_code());
				
				mapVo.put("mate_type_name", matTypeList.get(x).getMat_type_name());
				
				mapVo.put("supper_code", matTypeList.get(x).getSuper_code());
				
				mapVo.put("is_last", matTypeList.get(x).getIs_last());
				if(matTypeList.get(x).getSpell_code() != null){
					mapVo.put("spell_code", matTypeList.get(x).getSpell_code());
				}else{
					mapVo.put("spell_code","");
				}
				
				if(matTypeList.get(x).getWbx_code() != null){
					mapVo.put("wbx_code", matTypeList.get(x).getWbx_code());
				}else{
					mapVo.put("wbx_code","");
				}
				
				list.add(mapVo);
			}
			
			costMateTypeArrtMapper.addBatchCostMateTypeArrt(list);
			
			return "{\"msg\":\"同步成功.\",\"state\":\"true\"}";
			}else{
				for(int x = 0 ; x < matTypeList.size(); x++){
					
					Map<String,Object> mapVo = new HashMap<String,Object>();
					
					mapVo.put("group_id", matTypeList.get(x).getGroup_id());
					
					mapVo.put("hos_id", matTypeList.get(x).getHos_id());
					
					mapVo.put("copy_code", matTypeList.get(x).getCopy_code());
					
					mapVo.put("mate_type_code", matTypeList.get(x).getMat_type_code());
					
					mapVo.put("mate_type_name", matTypeList.get(x).getMat_type_name());
					
					mapVo.put("supper_code", matTypeList.get(x).getSuper_code());
					
					mapVo.put("is_last", matTypeList.get(x).getIs_last());
					if(matTypeList.get(x).getSpell_code() != null){
						mapVo.put("spell_code", matTypeList.get(x).getSpell_code());
					}else{
						mapVo.put("spell_code","");
					}
					
					if(matTypeList.get(x).getWbx_code() != null){
						mapVo.put("wbx_code", matTypeList.get(x).getWbx_code());
					}else{
						mapVo.put("wbx_code","");
					}
					
					list.add(mapVo);
				}
				
				costMateTypeArrtMapper.addBatchCostMateTypeArrt(list);
				
				return "{\"msg\":\"同步成功.\",\"state\":\"true\"}";
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e);

			return "{\"error\":\"同步失败 数据库异常 请联系管理员! 错误编码  syncCostMateTypeArrt\"}";
		}
	}
	/**
	 * 2016/10/31 lxj
	 * @Description 成本_材料分类字典<BR>
	 *              同步
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String syncCostMateTypeArrtNew(Map<String, Object> entityMap) throws DataAccessException {
		
		/*List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		Map<String,Object> mapVo = new HashMap<String,Object>();
		mapVo.put("group_id", SessionManager.getGroupId());*/
		/*try {
			//1.查询物资分类所有数据
			List<MatType> matTypeList = matTypeMapper.queryMatType(entityMap);
			
			if(matTypeList.size() == 0 ){
				
				return "{\"error\":\"未找到同步的数据.\"}";
			}
			
			List<CostMateTypeArrt> mateTypeList = costMateTypeArrtMapper.queryCostMateTypeArrt(entityMap);
			
			//2.删除成本材料信息和材料分类所有数据
//			costMateArrtMapper.deleteCostMateArrtAll(entityMap);//删除材料信息
//			costMateTypeArrtMapper.deleteCostMateTypeArrtAll(entityMap);//删除材料分类信息
			
			
			//3.同步数据到成本分类中
			if(mateTypeList.size() > 0 ){//成本科室职工分类 有数据就比较
				
				for(int x = 0;x < mateTypeList.size(); x++){
					
					for(int y = 0 ; y < matTypeList.size() ; y++){
						
						if(matTypeList.get(y).getMat_type_code().equals(mateTypeList.get(x).getMate_type_code())){//如果相等就删除
							
							matTypeList.remove(y);
						
						}
					}
				}
				
				if(matTypeList.size() == 0){
					
					return "{\"error\":\"同步失败,数据已存在.\"}";
				}
			
			for(int x = 0 ; x < matTypeList.size(); x++){
				
				Map<String,Object> mapVo = new HashMap<String,Object>();
				
				mapVo.put("group_id", matTypeList.get(x).getGroup_id());
				
				mapVo.put("hos_id", matTypeList.get(x).getHos_id());
				
				mapVo.put("copy_code", matTypeList.get(x).getCopy_code());
				
				mapVo.put("mate_type_code", matTypeList.get(x).getMat_type_code());
				
				mapVo.put("mate_type_name", matTypeList.get(x).getMat_type_name());
				
				mapVo.put("supper_code", matTypeList.get(x).getSuper_code());
				
				mapVo.put("is_last", matTypeList.get(x).getIs_last());
				if(matTypeList.get(x).getSpell_code() != null){
					mapVo.put("spell_code", matTypeList.get(x).getSpell_code());
				}else{
					mapVo.put("spell_code","");
				}
				
				if(matTypeList.get(x).getWbx_code() != null){
					mapVo.put("wbx_code", matTypeList.get(x).getWbx_code());
				}else{
					mapVo.put("wbx_code","");
				}
				
				list.add(mapVo);
			}
			
			costMateTypeArrtMapper.addBatchCostMateTypeArrt(list);
			
			return "{\"msg\":\"同步成功.\",\"state\":\"true\"}";
			}else{
				for(int x = 0 ; x < matTypeList.size(); x++){
					
					
					mapVo.put("group_id", matTypeList.get(x).getGroup_id());
					
					mapVo.put("hos_id", matTypeList.get(x).getHos_id());
					
					mapVo.put("copy_code", matTypeList.get(x).getCopy_code());
					
					mapVo.put("mate_type_code", matTypeList.get(x).getMat_type_code());
					
					mapVo.put("mate_type_name", matTypeList.get(x).getMat_type_name());
					
					mapVo.put("supper_code", matTypeList.get(x).getSuper_code());
					
					mapVo.put("is_last", matTypeList.get(x).getIs_last());
					if(matTypeList.get(x).getSpell_code() != null){
						mapVo.put("spell_code", matTypeList.get(x).getSpell_code());
					}else{
						mapVo.put("spell_code","");
					}
					
					if(matTypeList.get(x).getWbx_code() != null){
						mapVo.put("wbx_code", matTypeList.get(x).getWbx_code());
					}else{
						mapVo.put("wbx_code","");
					}
					
					list.add(mapVo);
				}*/
		        
				costMateTypeArrtMapper.addBatchCostMateTypeArrtNew(entityMap);
				
				return "{\"msg\":\"同步成功.\",\"state\":\"true\"}";
		}
	}

