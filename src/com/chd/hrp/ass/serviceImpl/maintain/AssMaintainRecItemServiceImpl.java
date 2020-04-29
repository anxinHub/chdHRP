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
import com.chd.hrp.ass.dao.maintain.AssMaintainRecAssMapper;
import com.chd.hrp.ass.dao.maintain.AssMaintainRecItemMapper;
import com.chd.hrp.ass.dao.maintain.AssMaintainRecMapper;
import com.chd.hrp.ass.entity.maintain.AssMaintainPlanAss;
import com.chd.hrp.ass.entity.maintain.AssMaintainPlanItem;
import com.chd.hrp.ass.entity.maintain.AssMaintainRecAss;
import com.chd.hrp.ass.entity.maintain.AssMaintainRecItem;
import com.chd.hrp.ass.service.maintain.AssMaintainRecItemService;
import com.github.pagehelper.PageInfo;

@Service("assMaintainRecItemService")
public class AssMaintainRecItemServiceImpl implements AssMaintainRecItemService {
	
	private static Logger logger = Logger.getLogger(AssMaintainRecItemServiceImpl.class);
	//引入DAO操作
	@Resource(name = "assMaintainRecItemMapper")
	private final AssMaintainRecItemMapper assMaintainRecItemMapper = null;
	
	@Resource(name = "assMaintainRecAssMapper")
	private final AssMaintainRecAssMapper assMaintainRecAssMapper = null;
	
	@Resource(name = "assMaintainRecMapper")
	private final AssMaintainRecMapper assMaintainRecMapper = null;
	
	/**
	 * @Description 
	 * 添加051202 保养记录项目明细<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addAssMaintainRecItem(Map<String, Object> entityMap)
			throws DataAccessException {
		//获取对象051202 保养记录项目明细
				AssMaintainRecItem assMaintainRecItem = queryAssMaintainRecItemByCode(entityMap);

				if (assMaintainRecItem != null) {

					return "{\"error\":\"数据重复,请重新添加.\"}";

				}
				
				try {
					
					int state = assMaintainRecItemMapper.addAssMaintainRecItem(entityMap);
					
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
	public String addBatchAssMaintainRecItem(List<Map<String, Object>> entityMap)
			throws DataAccessException {
try {
			
			assMaintainRecItemMapper.addBatchAssMaintainRecItem(entityMap);
			
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
	public String updateAssMaintainRecItem(Map<String, Object> entityMap)
			throws DataAccessException {
		try {
			
			  int state = assMaintainRecItemMapper.updateAssMaintainRecItem(entityMap);
				
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
	public String updateBatchAssMaintainRecItem(
			List<Map<String, Object>> entityMap) throws DataAccessException {
		try {
			
			  assMaintainRecItemMapper.updateBatchAssMaintainRecItem(entityMap);
				
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
	public String deleteAssMaintainRecItem(Map<String, Object> entityMap)
			throws DataAccessException {
		 try {
				
				int state = assMaintainRecItemMapper.deleteAssMaintainRecItem(entityMap);
				
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
	public String deleteBatchAssMaintainRecItem(
			List<Map<String, Object>> entityMap) throws DataAccessException {
try {
			
			assMaintainRecItemMapper.deleteBatchAssMaintainRecItem(entityMap);
			assMaintainRecAssMapper.deleteBatchAssMaintainRecAss(entityMap);
			
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
	public String addOrUpdateAssMaintainRecItem(Map<String, Object> entityMap)
			throws DataAccessException {
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
    		
        mapVo.put("rec_id", entityMap.get("rec_id"));
		
        mapVo.put("detail_id", entityMap.get("detail_id"));
        
    	validateMapVo.put("group_id",entityMap.get("group_id"));
    	validateMapVo.put("rec_id", entityMap.get("rec_id"));
    	
    	validateMapVo.put("hos_id",entityMap.get("hos_id"));
    	
    	validateMapVo.put("copy_code", entityMap.get("copy_code"));
    	
    	validateMapVo.put("plan_id",entityMap.get("plan_id"));
    	
    	validateMapVo.put("ass_card_no",entityMap.get("ass_card_no"));
    
    	List<AssMaintainRecAss> list = (List<AssMaintainRecAss>) assMaintainRecAssMapper.queryAssMaintainRecAssExists(mapVo);
    	
    	try {
    		
				if (list.size()>0) { 
					
					assMaintainRecAssMapper.updateAssMaintainRecAss(entityMap);
					
					mapVoDetail.put("group_id",entityMap.get("group_id"));
					
			    	mapVoDetail.put("hos_id",entityMap.get("hos_id"));
					
			    	mapVoDetail.put("copy_code", entityMap.get("copy_code"));
					
			    	mapVoDetail.put("item_code", entityMap.get("maintain_item_code"));
			    	
			    	mapVoDetail.put("item_name",  entityMap.get("maintain_item_name"));
			    	 
			        mapVoDetail.put("plan_id", entityMap.get("plan_id"));
			        mapVoDetail.put("rec_id", entityMap.get("rec_id"));
			    		  
			        mapVoDetail.put("detail_id", entityMap.get("detail_id"));
					
					 //assMaintainRecItemMapper.deleteAssMaintainRecItem(mapVoDetail);
		
					int state = assMaintainRecItemMapper.updateAssMaintainRecItem(mapVoDetail);
					
					return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";
		
				}else {
				
				 
					List<AssMaintainRecAss> list1 = (List<AssMaintainRecAss>) assMaintainRecAssMapper.queryByAssMaintainRecAssId(validateMapVo);
					
					if (list1.size()>0){
						 
						 return "{\"error\":\"资产卡片号重复.\",\"state\":\"true\"}";
					 }
					
					 assMaintainRecAssMapper.addAssMaintainRecAss(entityMap);
					 
					 Long detail_id = assMaintainRecAssMapper.queryCurrentSequence();
					 
					    mapVoDetail.put("group_id",entityMap.get("group_id"));
						
				    	mapVoDetail.put("hos_id",entityMap.get("hos_id"));
						
				    	mapVoDetail.put("copy_code", entityMap.get("copy_code"));
				    	
				    	 mapVoDetail.put("plan_id",entityMap.get("plan_id"));
				    	 
				    	 mapVoDetail.put("ass_card_no",entityMap.get("ass_card_no"));
				    	 mapVoDetail.put("rec_id", entityMap.get("rec_id"));
				    	mapVoDetail.put("item_code", entityMap.get("maintain_item_code"));
				    	
				    	mapVoDetail.put("item_name",  entityMap.get("maintain_item_name"));
					  
						mapVoDetail.put("detail_id", detail_id);
						
					 assMaintainRecItemMapper.deleteAssMaintainRecItem(mapVoDetail);
					 
					int state = assMaintainRecItemMapper.addAssMaintainRecItem(mapVoDetail);
					
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
	public String queryAssMaintainRecItem(Map<String, Object> entityMap)
			throws DataAccessException {
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<AssMaintainRecItem> list = assMaintainRecItemMapper.queryAssMaintainRecItem(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{ 
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<AssMaintainRecItem> list =null;
			
				if (entityMap.get("ass_nature").equals("02")){
					
					list = assMaintainRecItemMapper.queryAssMaintainRecItemSpecial(entityMap, rowBounds);
				
					}
				if (entityMap.get("ass_nature").equals("03")){
					 
					list = assMaintainRecItemMapper.queryAssMaintainRecItemGeneral(entityMap, rowBounds);
				
					}
				if (entityMap.get("ass_nature").equals("01")){
					
					list = assMaintainRecItemMapper.queryAssMaintainRecItemHouse(entityMap, rowBounds);
				
					}
				if (entityMap.get("ass_nature").equals("04")){
					
					list = assMaintainRecItemMapper.queryAssMaintainRecItemOther(entityMap, rowBounds);
				
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
	public AssMaintainRecItem queryAssMaintainRecItemByCode(
			Map<String, Object> entityMap) throws DataAccessException {
		return assMaintainRecItemMapper.queryAssMaintainRecItemByCode(entityMap);
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
	public AssMaintainRecItem queryAssMaintainRecItemByUniqueness(
			Map<String, Object> entityMap) throws DataAccessException {
		return assMaintainRecItemMapper.queryAssMaintainRecItemByUniqueness(entityMap);
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
	public List<AssMaintainRecItem> queryAssMaintainRecItemExists(
			Map<String, Object> entityMap) throws DataAccessException {
		return assMaintainRecItemMapper.queryAssMaintainRecItemExists(entityMap);
	}


}
