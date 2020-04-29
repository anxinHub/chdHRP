/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.ass.serviceImpl.maintain;

import java.util.*;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.SysPage;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.hrp.ass.dao.maintain.AssMaintainPlanAssMapper;
import com.chd.hrp.ass.dao.maintain.AssMaintainRecAssMapper;
import com.chd.hrp.ass.dao.maintain.AssMaintainRecItemMapper;
import com.chd.hrp.ass.entity.maintain.AssMaintainPlanAss;
import com.chd.hrp.ass.entity.maintain.AssMaintainRecAss;
import com.chd.hrp.ass.service.maintain.AssMaintainRecAssService;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Description:
 * 051203 保养记录资产明细
 * @Table:
 * ASS_MAINTAIN_REC_ASS
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 


@Service("assMaintainRecAssService")
public class AssMaintainRecAssServiceImpl implements AssMaintainRecAssService { 

	private static Logger logger = Logger.getLogger(AssMaintainRecAssServiceImpl.class);
	//引入DAO操作
	@Resource(name = "assMaintainRecAssMapper")
	private final AssMaintainRecAssMapper assMaintainRecAssMapper = null;
	
	@Resource(name = "assMaintainRecItemMapper")
	private final AssMaintainRecItemMapper assMaintainRecItemMapper = null;
	
	@Resource(name = "assMaintainPlanAssMapper")
	private final AssMaintainPlanAssMapper assMaintainPlanAssMapper = null;
    
	/**
	 * @Description 
	 * 添加051203 保养记录资产明细<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addAssMaintainRecAss(Map<String,Object> entityMap)throws DataAccessException{
		
		//获取对象051203 保养记录资产明细
		AssMaintainRecAss assMaintainRecAss = queryAssMaintainRecAssByCode(entityMap);

		if (assMaintainRecAss != null) {

			return "{\"error\":\"数据重复,请重新添加.\"}";

		}
		
		try {
			
			int state = assMaintainRecAssMapper.addAssMaintainRecAss(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}
		
	}
	/**
	 * @Description 
	 * 批量添加051203 保养记录资产明细<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addBatchAssMaintainRecAss(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
			assMaintainRecAssMapper.addBatchAssMaintainRecAss(entityList);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}
		
	}
	
		/**
	 * @Description 
	 * 更新051203 保养记录资产明细<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateAssMaintainRecAss(Map<String,Object> entityMap)throws DataAccessException{
		
		try {
			
		  int state = assMaintainRecAssMapper.updateAssMaintainRecAss(entityMap);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}	
		
	}
	/**
	 * @Description 
	 * 批量更新051203 保养记录资产明细<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateBatchAssMaintainRecAss(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
		  assMaintainRecAssMapper.updateBatchAssMaintainRecAss(entityList);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}	
		
	}
	/**
	 * @Description 
	 * 删除051203 保养记录资产明细<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
    public String deleteAssMaintainRecAss(Map<String, Object> entityMap) throws DataAccessException {
    	
    try {
			
			int state = assMaintainRecAssMapper.deleteAssMaintainRecAss(entityMap);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}	
		
  }
    
	/**
	 * @Description 
	 * 批量删除051203 保养记录资产明细<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String deleteBatchAssMaintainRecAss(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
			assMaintainRecAssMapper.deleteBatchAssMaintainRecAss(entityList);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}	
	}
	
	/**
	 * @Description 
	 * 添加051203 保养记录资产明细<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addOrUpdateAssMaintainRecAss(Map<String,Object> entityMap)throws DataAccessException{
		/**
		* 备注 先判断是否存在 存在即更新不存在则添加<br>
		* 在判断是否存在时可根据实际情况进行修改传递的参数进行判断<br>
		* 判断是否名称相同 判断是否 编码相同 等一系列规则
		*/
		//判断是否存在对象051203 保养记录资产明细
		Map<String, Object> mapVo=new HashMap<String, Object>();
		mapVo.put("group_id",entityMap.get("group_id"));
		mapVo.put("hos_id",entityMap.get("hos_id"));
    	mapVo.put("copy_code", entityMap.get("copy_code"));
    	mapVo.put("acct_year", entityMap.get("acct_year"));
		
		List<AssMaintainRecAss> list = assMaintainRecAssMapper.queryAssMaintainRecAssExists(mapVo);
		
		if (list != null) {

			int state = assMaintainRecAssMapper.updateAssMaintainRecAss(entityMap);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		
		try {
			
			int state = assMaintainRecAssMapper.addAssMaintainRecAss(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}
		
	}
	/**
	 * @Description 
	 * 查询结果集051203 保养记录资产明细<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String queryAssMaintainRecAss(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<AssMaintainRecAss> list = assMaintainRecAssMapper.queryAssMaintainRecAss(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<AssMaintainRecAss> list = assMaintainRecAssMapper.queryAssMaintainRecAss(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
		
	}
	
	/**
	 * @Description 
	 * 获取对象051203 保养记录资产明细<BR> 
	 * @param  entityMap<BR>
	 *  参数为主键
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public AssMaintainRecAss queryAssMaintainRecAssByCode(Map<String,Object> entityMap)throws DataAccessException{
		return assMaintainRecAssMapper.queryAssMaintainRecAssByCode(entityMap);
	}
	
	/**
	 * @Description 
	 * 获取051203 保养记录资产明细<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return AssMaintainRecAss
	 * @throws DataAccessException
	*/
	@Override
	public AssMaintainRecAss queryAssMaintainRecAssByUniqueness(Map<String,Object> entityMap)throws DataAccessException{
		return assMaintainRecAssMapper.queryAssMaintainRecAssByUniqueness(entityMap);
	}
	
	/**
	 * @Description 
	 * 获取051203 保养记录资产明细<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return List<AssMaintainRecAss>
	 * @throws DataAccessException
	*/
	@Override
	public List<AssMaintainRecAss> queryAssMaintainRecAssExists(Map<String,Object> entityMap)throws DataAccessException{
		return assMaintainRecAssMapper.queryAssMaintainRecAssExists(entityMap);
	}
	@Override
	public String addOrUpdateAssMaintainRecDetail(Map<String, Object> entityMap) {
		//判断是否存在对象051202 保养计划项目明细
				Map<String, Object> mapVo=new HashMap<String, Object>();
				
				Map<String, Object> mapVoDetail=new HashMap<String, Object>();
				
				Map<String, Object> validateMapVo =new HashMap<String, Object>();
				
				mapVo.put("group_id",entityMap.get("group_id"));
				
				mapVo.put("hos_id",entityMap.get("hos_id"));
				
		    	mapVo.put("copy_code", entityMap.get("copy_code"));
		    	
		    	//mapVo.put("acct_year", entityMap.get("acct_year"));
		    	 
		        //mapVo.put("plan_id", entityMap.get("plan_id"));
		    		
		        mapVo.put("rec_id", entityMap.get("rec_id"));
				
		        mapVo.put("detail_id", entityMap.get("detail_id"));
		       // mapVo.put("ass_card_no",entityMap.get("ass_card_no"));
		        
		    	validateMapVo.put("group_id",entityMap.get("group_id"));
		    	validateMapVo.put("rec_id", entityMap.get("rec_id"));
		    	
		    	validateMapVo.put("hos_id",entityMap.get("hos_id"));
		    	
		    	validateMapVo.put("copy_code", entityMap.get("copy_code"));
		    	
		    	validateMapVo.put("rec_id",entityMap.get("rec_id"));
		    	
		    	validateMapVo.put("ass_card_no",entityMap.get("ass_card_no"));
		    
		    	List<AssMaintainRecAss> list = (List<AssMaintainRecAss>) assMaintainRecAssMapper.queryAssMaintainRecAssExists(mapVo);
		
		    	try {
		    		
					if (list.size()>0) { 
						
						
						for (AssMaintainRecAss assAcceptDetail : list) {
							
							String ass_card_no = assAcceptDetail.getAss_card_no().toString().substring(4,12);
						 	String asscard_no = entityMap.get("ass_card_no").toString().substring(4, 12);
							int ass_card_no1 = Integer.valueOf(ass_card_no);
							int asscard_no1 = Integer.valueOf(asscard_no);
							if (ass_card_no1 != asscard_no1) {
								assMaintainRecItemMapper.deleteAssMaintaintItemByAssMaintainDetail(assAcceptDetail);

								int state = assMaintainRecAssMapper.updateAssMaintainRecAss(entityMap);
							}
							
						}
							return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";
						
						
						/*assMaintainRecAssMapper.updateAssMaintainRecAss(entityMap);
						
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
						
						return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";*/
			
					}else {
					
					 
						List<AssMaintainRecAss> list1 = (List<AssMaintainRecAss>) assMaintainRecAssMapper.queryByAssMaintainRecAssId(validateMapVo);
						
						if (list1.size()>0){
							 
							 return "{\"error\":\"资产卡片号重复.\",\"state\":\"true\"}";
						 }
						
						 assMaintainRecAssMapper.addAssMaintainRecAss(entityMap);
						 
						 /*Long detail_id = assMaintainRecAssMapper.queryCurrentSequence();
						 
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
						*/
						return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";
						
					}

			}
			catch (Exception e) {

				logger.error(e.getMessage(), e);

				throw new SysException(e.getMessage());

			}
		    	
		    	
		    	
		    	
		    	
		    	
	}
	
	RowBounds rowBoundsALL = new RowBounds(0, 20); 
	@Override
	public String queryMaintainPlanRec(Map<String, Object> entityMap) {
		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

		List<Map<String, Object>> list = null;
		list = assMaintainPlanAssMapper.queryAssMaintainPlanAssRec(entityMap, rowBounds);
		
		PageInfo page = new PageInfo(list);

		return ChdJson.toJson(list, page.getTotal());
	}
	
}
