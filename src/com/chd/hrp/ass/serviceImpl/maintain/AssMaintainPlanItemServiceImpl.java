/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.ass.serviceImpl.maintain;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.SysPage;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.hrp.ass.dao.maintain.AssMaintainPlanAssMapper;
import com.chd.hrp.ass.dao.maintain.AssMaintainPlanItemMapper;
import com.chd.hrp.ass.dao.maintain.AssMaintainPlanMapper;
import com.chd.hrp.ass.entity.maintain.AssMaintainPlanAss;
import com.chd.hrp.ass.entity.maintain.AssMaintainPlanItem;
import com.chd.hrp.ass.service.maintain.AssMaintainPlanItemService;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Description:
 * 051202 保养计划项目明细
 * @Table:
 * ASS_MAINTAIN_PLAN_ITEM
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 


@Service("assMaintainPlanItemService")
public class AssMaintainPlanItemServiceImpl implements AssMaintainPlanItemService {

	private static Logger logger = Logger.getLogger(AssMaintainPlanItemServiceImpl.class);
	//引入DAO操作
	@Resource(name = "assMaintainPlanItemMapper")
	private final AssMaintainPlanItemMapper assMaintainPlanItemMapper = null;
	
	@Resource(name = "assMaintainPlanAssMapper")
	private final AssMaintainPlanAssMapper assMaintainPlanAssMapper = null;
	
	@Resource(name = "assMaintainPlanMapper")
	private final AssMaintainPlanMapper assMaintainPlanMapper = null;
    
	/**
	 * @Description 
	 * 添加051202 保养计划项目明细<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addAssMaintainPlanItem(Map<String,Object> entityMap)throws DataAccessException{
		
		//获取对象051202 保养计划项目明细
		AssMaintainPlanItem assMaintainPlanItem = queryAssMaintainPlanItemByCode(entityMap);

		if (assMaintainPlanItem != null) {

			return "{\"error\":\"数据重复,请重新添加.\"}";

		}
		
		try {
			
			int state = assMaintainPlanItemMapper.addAssMaintainPlanItem(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}
		
	}
	/**
	 * @Description 
	 * 批量添加051202 保养计划项目明细<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addBatchAssMaintainPlanItem(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
			assMaintainPlanItemMapper.addBatchAssMaintainPlanItem(entityList);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}
		
	}
	
		/**
	 * @Description 
	 * 更新051202 保养计划项目明细<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateAssMaintainPlanItem(Map<String,Object> entityMap)throws DataAccessException{
		
		try {
			
		  int state = assMaintainPlanItemMapper.updateAssMaintainPlanItem(entityMap);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}	
		
	}
	/**
	 * @Description 
	 * 批量更新051202 保养计划项目明细<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateBatchAssMaintainPlanItem(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
		  assMaintainPlanItemMapper.updateBatchAssMaintainPlanItem(entityList);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}	
		
	}
	/**
	 * @Description 
	 * 删除051202 保养计划项目明细<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
    public String deleteAssMaintainPlanItem(Map<String, Object> entityMap) throws DataAccessException {
    	
    try {
			
			int state = assMaintainPlanItemMapper.deleteAssMaintainPlanItem(entityMap);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}	
		
  }
    
	/**
	 * @Description 
	 * 批量删除051202 保养计划项目明细<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String deleteBatchAssMaintainPlanItem(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
			assMaintainPlanItemMapper.deleteBatchAssMaintainPlanItem(entityList);
			assMaintainPlanAssMapper.deleteBatchAssMaintainPlanAss(entityList);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}	
	}
	
	/**
	 * @Description 
	 * 添加051202 保养计划项目明细<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addOrUpdateAssMaintainPlanItem(Map<String,Object> entityMap)throws DataAccessException{
		/**
		* 备注 先判断是否存在 存在即更新不存在则添加<br>
		* 在判断是否存在时可根据实际情况进行修改传递的参数进行判断<br>
		* 判断是否名称相同 判断是否 编码相同 等一系列规则
		*/
		//判断是否存在对象051202 保养计划项目明细
		Map<String, Object> mapVo=new HashMap<String, Object>();
		
		Map<String, Object> mapVoDetail=new HashMap<String, Object>();
		
		Map<String, Object> validateMapVo =new HashMap<String, Object>();
		
		mapVo.put("group_id",entityMap.get("group_id"));
		
		mapVo.put("hos_id",entityMap.get("hos_id"));
		
    	mapVo.put("copy_code", entityMap.get("copy_code"));
    	
    	mapVo.put("acct_year", entityMap.get("acct_year"));
    	 
        mapVo.put("plan_id", entityMap.get("plan_id"));
    		
        mapVo.put("detail_id", entityMap.get("detail_id"));
        
    	validateMapVo.put("group_id",entityMap.get("group_id"));
    	
    	validateMapVo.put("hos_id",entityMap.get("hos_id"));
    	
    	validateMapVo.put("copy_code", entityMap.get("copy_code"));
    	
    	validateMapVo.put("plan_id",entityMap.get("plan_id"));
    	
    	validateMapVo.put("ass_card_no",entityMap.get("ass_card_no"));
    
    	List<AssMaintainPlanAss> list = (List<AssMaintainPlanAss>) assMaintainPlanAssMapper.queryAssMaintainPlanAssExists(mapVo);
    	
    	try {
    		
				if (list.size()>0) { 
					
					assMaintainPlanAssMapper.updateAssMaintainPlanAss(entityMap);
					
					mapVoDetail.put("group_id",entityMap.get("group_id"));
					
			    	mapVoDetail.put("hos_id",entityMap.get("hos_id"));
					
			    	mapVoDetail.put("copy_code", entityMap.get("copy_code"));
					
			    	mapVoDetail.put("item_code", entityMap.get("maintain_item_code"));
			    	
			    	mapVoDetail.put("item_name",  entityMap.get("maintain_item_name"));
			    	 
			        mapVoDetail.put("plan_id", entityMap.get("plan_id"));
			    		  
			        mapVoDetail.put("detail_id", entityMap.get("detail_id"));
			        
			        mapVoDetail.put("ass_card_no",entityMap.get("ass_card_no"));
					
					 //assMaintainPlanItemMapper.deleteAssMaintainPlanItem(mapVoDetail);
		
					int state = assMaintainPlanItemMapper.updateAssMaintainPlanItem(mapVoDetail);
					
					return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";
		
				}else {
				
				 
					List<AssMaintainPlanAss> list1 = (List<AssMaintainPlanAss>) assMaintainPlanAssMapper.queryByAssMaintainPlanId(validateMapVo);
					
					if (list1.size()>0){
						 
						 return "{\"error\":\"资产卡片号重复.\",\"state\":\"true\"}";
					 }
					
					 assMaintainPlanAssMapper.addAssMaintainPlanAss(entityMap);
					 
					 Long detail_id = assMaintainPlanAssMapper.queryCurrentSequence();
					 
					    mapVoDetail.put("group_id",entityMap.get("group_id"));
						
				    	mapVoDetail.put("hos_id",entityMap.get("hos_id"));
						
				    	mapVoDetail.put("copy_code", entityMap.get("copy_code"));
				    	
				    	 mapVoDetail.put("plan_id",entityMap.get("plan_id"));
				    	 
				    	 mapVoDetail.put("ass_card_no",entityMap.get("ass_card_no"));
						
				    	mapVoDetail.put("item_code", entityMap.get("maintain_item_code"));
				    	
				    	mapVoDetail.put("item_name",  entityMap.get("maintain_item_name"));
					  
						mapVoDetail.put("detail_id", detail_id);
						
					 assMaintainPlanItemMapper.deleteAssMaintainPlanItem(mapVoDetail);
					 
					int state = assMaintainPlanItemMapper.addAssMaintainPlanItem(mapVoDetail);
					
					return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";
					
				}

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}
		
	}
	/**
	 * @Description 
	 * 查询结果集051202 保养计划项目明细<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String queryAssMaintainPlanItem(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<AssMaintainPlanItem> list = assMaintainPlanItemMapper.queryAssMaintainPlanItem(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{ 
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<AssMaintainPlanItem> list =null;
			
				if (entityMap.get("ass_nature").equals("02")){
					
					list = assMaintainPlanItemMapper.queryAssMaintainPlanItemSpecial(entityMap, rowBounds);
				
					}
				if (entityMap.get("ass_nature").equals("03")){
					 
					list = assMaintainPlanItemMapper.queryAssMaintainPlanItemGeneral(entityMap, rowBounds);
				
					}
				if (entityMap.get("ass_nature").equals("01")){
					
					list = assMaintainPlanItemMapper.queryAssMaintainPlanItemHouse(entityMap, rowBounds);
				
					}
				if (entityMap.get("ass_nature").equals("04")){
					
					list = assMaintainPlanItemMapper.queryAssMaintainPlanItemOther(entityMap, rowBounds);
				
					}
				 
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
		
	}
	
	/**
	 * @Description 
	 * 获取对象051202 保养计划项目明细<BR> 
	 * @param  entityMap<BR>
	 *  参数为主键
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public AssMaintainPlanItem queryAssMaintainPlanItemByCode(Map<String,Object> entityMap)throws DataAccessException{
		return assMaintainPlanItemMapper.queryAssMaintainPlanItemByCode(entityMap);
	}
	
	/**
	 * @Description 
	 * 获取051202 保养计划项目明细<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return AssMaintainPlanItem
	 * @throws DataAccessException
	*/
	@Override
	public AssMaintainPlanItem queryAssMaintainPlanItemByUniqueness(Map<String,Object> entityMap)throws DataAccessException{
		return assMaintainPlanItemMapper.queryAssMaintainPlanItemByUniqueness(entityMap);
	}
	
	/**
	 * @Description 
	 * 获取051202 保养计划项目明细<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return List<AssMaintainPlanItem>
	 * @throws DataAccessException
	*/
	@Override
	public List<AssMaintainPlanItem> queryAssMaintainPlanItemExists(Map<String,Object> entityMap)throws DataAccessException{
		return assMaintainPlanItemMapper.queryAssMaintainPlanItemExists(entityMap);
	}
	@Override
	public List<AssMaintainPlanItem> queryAssMaintainPlanItemByPlans(Map<String, Object> entityMap)
			throws DataAccessException {
		List<AssMaintainPlanItem> list = assMaintainPlanItemMapper.queryAssMaintainPlanItem(entityMap);
		return list;
	}
	
}
