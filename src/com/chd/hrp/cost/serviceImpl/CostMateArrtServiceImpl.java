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
import com.chd.hrp.cost.dao.CostMateArrtMapper;
import com.chd.hrp.cost.dao.CostMateTypeArrtMapper;
import com.chd.hrp.cost.entity.CostMateArrt;
import com.chd.hrp.cost.entity.CostMateTypeArrt;
import com.chd.hrp.cost.service.CostMateArrtService;
import com.chd.hrp.mat.dao.info.basic.MatInvMapper;
import com.chd.hrp.mat.dao.info.basic.MatTypeMapper;
import com.chd.hrp.mat.entity.MatType;
import com.github.pagehelper.PageInfo;

/**
* @Title. @Description.
* 成本_材料信息字典<BR> 
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


@Service("costMateArrtService")
public class CostMateArrtServiceImpl implements CostMateArrtService {

	private static Logger logger = Logger.getLogger(CostMateArrtServiceImpl.class);
	
	@Resource(name = "costMateArrtMapper")
	private final CostMateArrtMapper costMateArrtMapper = null;
	
	@Resource(name = "costMateTypeArrtMapper")
	private final CostMateTypeArrtMapper costMateTypeArrtMapper = null;
	
	@Resource(name = "matInvMapper")
	private final MatInvMapper matInvMapper = null;
	
	@Resource(name = "matTypeMapper")
	private final MatTypeMapper matTypeMapper= null;
	/**
	 * @Description 
	 * 成本_材料信息字典<BR> 添加CostMateArrt
	 * @param CostMateArrt entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addCostMateArrt(Map<String,Object> entityMap)throws DataAccessException{
		
		Map<String, Object> byCodeMap = new HashMap<String, Object>();
		
		byCodeMap.put("group_id", entityMap.get("group_id"));
		
		byCodeMap.put("hos_id", entityMap.get("hos_id"));
		
		byCodeMap.put("copy_code", entityMap.get("copy_code"));
		
		byCodeMap.put("mate_code", entityMap.get("mate_code"));
		byCodeMap.put("mate_name", entityMap.get("mate_name"));
		
		
		List<CostMateArrt> costMateArrt = costMateArrtMapper.queryCostMateArrtAdd(byCodeMap);
	    if (costMateArrt.size() > 0 ) {
	    	for(CostMateArrt item : costMateArrt){
				if(item.getMate_code().equals(byCodeMap.get("mate_code").toString())){
					return "{\"error\":\"编码已存在.\"}";
				}
                if(item.getMate_name().equals(byCodeMap.get("mate_name").toString())){
					
					return "{\"error\":\"名称已存在.\"}";
				}
			  }

			}
		

		try {
			
			costMateArrtMapper.addCostMateArrt(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码 addCostMateArrt\"}";

		}

	}
	
	/**
	 * @Description 
	 * 成本_材料信息字典<BR> 批量添加CostMateArrt
	 * @param  CostMateArrt entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addBatchCostMateArrt(List<Map<String,Object>> entityList)throws DataAccessException{

		try {
			
			costMateArrtMapper.addBatchCostMateArrt(entityList);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码 addBatchCostMateArrt\"}";

		}
	}
	
	/**
	 * @Description 
	 * 成本_材料信息字典<BR> 查询CostMateArrt分页
	 * @param  entityMap RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String queryCostMateArrt(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		sysPage = (SysPage) entityMap.get("sysPage");
		if (sysPage.getTotal()==-1){
			List<CostMateArrt> list = costMateArrtMapper.queryCostMateArrt(entityMap);
			return ChdJson.toJson(list);
		}else{
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			List<CostMateArrt> list = costMateArrtMapper.queryCostMateArrt(entityMap, rowBounds);
			PageInfo page = new PageInfo(list);
			return ChdJson.toJson(list, page.getTotal());
		}
		
	}
	@Override
	public List<Map<String, Object>> queryCostMateArrtPrint(Map<String, Object> entityMap) throws DataAccessException {

		
		List<Map<String, Object>> list=costMateArrtMapper.queryCostMateArrtPrint(entityMap);
		
		return list;

	}
	/**
	 * @Description 
	 * 成本_材料信息字典<BR> 查询CostMateArrtByCode
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public CostMateArrt queryCostMateArrtByCode(Map<String,Object> entityMap)throws DataAccessException{
		
		return costMateArrtMapper.queryCostMateArrtByCode(entityMap);
		
	}
	
	/**
	 * @Description 
	 * 成本_材料信息字典<BR> 批量删除CostMateArrt
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String deleteBatchCostMateArrt(List<Map<String,Object>> entityList)throws DataAccessException{

		try {

				int state = costMateArrtMapper.deleteBatchCostMateArrt(entityList);
				return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码  deleteBatchCostMateArrt\"}";

		}
		
	}
	
	/**
	 * @Description 
	 * 成本_材料信息字典<BR> 删除CostMateArrt
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
  public String deleteCostMateArrt(Map<String, Object> entityMap) throws DataAccessException {
		
		try {
				costMateArrtMapper.deleteCostMateArrt(entityMap);
				return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码  deleteCostMateArrt\"}";

		}
    }
	
	/**
	 * @Description 
	 * 成本_材料信息字典<BR> 更新CostMateArrt
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateCostMateArrt(Map<String,Object> entityMap)throws DataAccessException{
		
		try {

			costMateArrtMapper.updateCostMateArrt(entityMap);

			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updateCostMateArrt\"}";

		}
	}
	
	/**
	 * @Description 
	 * 成本_材料信息字典<BR> 批量更新CostMateArrt
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateBatchCostMateArrt(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {

			costMateArrtMapper.updateBatchCostMateArrt(entityList);

			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updateCostMateArrt\"}";

		}
		
	}

	/**
	 * @Description
	 * 2016/10/31 lxj 
	 * 成本_材料信息字典<BR> 同步 CostMateArrt
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String syncCostMateArrt(Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		try {
			
			List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
			List<Map<String,Object>> costMateArrtlist = new ArrayList<Map<String,Object>>();

			//1.查询物流管理系统物资材料信息
			List<Map<String,Object>> matInvList = (List<Map<String, Object>>) matInvMapper.query(entityMap);
			List<CostMateArrt> mateList = costMateArrtMapper.queryCostMateArrt(entityMap);
			
			//1.1查询物流管理系统材料分类信息
			List<MatType> matTypeList = matTypeMapper.queryMatType(entityMap);
			List<CostMateTypeArrt> mateTypeList = costMateTypeArrtMapper.queryCostMateTypeArrt(entityMap);
			
			if(matTypeList.size() == 0 ){
				
				return "{\"error\":\"未找到材料分类数据.\"}";
			}
			
			if(matInvList.size() == 0 ){
				
				return "{\"error\":\"未找到材料数据.\"}";
			}
			
			for(int x = 0;x < mateTypeList.size(); x++){
				
				for(int y = 0 ; y < matTypeList.size() ; y++){
					
					if(matTypeList.get(y).getMat_type_code().equals(mateTypeList.get(x).getMate_type_code())){//如果相等就删除
						
						matTypeList.remove(y);
					
					}
				}
			}
			//
			for(int x = 0;x < mateList.size(); x++){
				
				for(int y = 0 ; y < matInvList.size() ; y++){
					
					if(matInvList.get(y).get("inv_code").equals(mateList.get(x).getMate_code())){//如果相等就删除
						
						matInvList.remove(y);
					
					}
				}
			}
			//2.删除成本材料分类和材料信息
//			costMateArrtMapper.deleteCostMateArrtAll(entityMap);//删除所有材料信息
//			costMateTypeArrtMapper.deleteCostMateTypeArrtAll(entityMap);//删除所有材料分类
			
			
			//3同步物资分类数据到成本分类中
			for(int x = 0 ; x < matTypeList.size(); x++){
				
				Map<String,Object> mapVo = new HashMap<String,Object>();
				
				mapVo.put("group_id", matTypeList.get(x).getGroup_id());
				
				mapVo.put("hos_id", matTypeList.get(x).getHos_id());
				
				mapVo.put("copy_code", matTypeList.get(x).getCopy_code());
				
				mapVo.put("mate_type_id",matTypeList.get(x).getMat_type_id());
				
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
			
			//4.同步物资材料到成本材料信息中
			
			for(int x = 0; x < matInvList.size() ; x++){
				
				Map<String,Object> mapVo = new HashMap<String,Object>();
				
				mapVo.put("group_id", matInvList.get(x).get("group_id"));
				mapVo.put("hos_id", matInvList.get(x).get("hos_id"));
				mapVo.put("copy_code", matInvList.get(x).get("copy_code"));
				mapVo.put("mate_type_id", matInvList.get(x).get("mat_type_id"));
				mapVo.put("mate_code", matInvList.get(x).get("inv_code"));
				mapVo.put("mate_name", matInvList.get(x).get("inv_name"));
				mapVo.put("mate_mode", matInvList.get(x).get("inv_model"));
				mapVo.put("meas_code", matInvList.get(x).get("unit_code"));
				if(matInvList.get(x).get("price") != null){
					mapVo.put("price", matInvList.get(x).get("plan_price"));
				}else{
					mapVo.put("price", "0");
				}
				
				if(matInvList.get(x).get("spell_code") != null){
					mapVo.put("spell_code", matInvList.get(x).get("spell_code"));
				}else{
					mapVo.put("spell_code", "");
				}
				
				if(matInvList.get(x).get("wbx_code") != null){
					mapVo.put("wbx_code", matInvList.get(x).get("wbx_code"));
				}else{
					mapVo.put("wbx_code", "");
				}
				
				costMateArrtlist.add(mapVo);
			}
			
			//costMateTypeArrtMapper.addBatchCostMateTypeArrt(list);//同步物资分类到材料分类
			costMateArrtMapper.addBatchCostMateArrt(costMateArrtlist);//同步物资材料到材料信息
			
			return "{\"msg\":\"同步成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e.getMessage(), e);

			return "{\"error\":\"同步失败 数据库异常 请联系管理员! 错误编码  syncCostMateArrt\"}";

		}
		
	}
	/**
	 * @Description
	 * 2016/10/31 lxj 
	 * 成本_材料信息字典<BR> 同步 CostMateArrt
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String syncCostMateArrtNew(Map<String, Object> mapVo) {
		costMateArrtMapper.addBatchCostMateArrtNew(mapVo);//同步物资材料到材料信息
		return "{\"msg\":\"同步成功.\",\"state\":\"true\"}";
	}
}
