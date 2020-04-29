/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.ass.serviceImpl.measure;

import java.util.*;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import com.chd.base.SysPage;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.base.util.DateUtil;
import com.chd.hrp.ass.dao.maintain.AssMaintainRecMapper;
import com.chd.hrp.ass.dao.measure.AssMeasurePlanAssMapper;
import com.chd.hrp.ass.dao.measure.AssMeasurePlanMapper;
import com.chd.hrp.ass.entity.measure.AssMeasurePlan;
import com.chd.hrp.ass.service.base.AssBaseService;
import com.chd.hrp.ass.service.measure.AssMeasurePlanAssService;
import com.chd.hrp.ass.service.measure.AssMeasurePlanService;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Description:
 * 051204 检查计量计划
 * @Table:
 * ASS_MEASURE_PLAN
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
  

 
@Service("assMeasurePlanService")
public class AssMeasurePlanServiceImpl implements AssMeasurePlanService {

	private static Logger logger = Logger.getLogger(AssMeasurePlanServiceImpl.class);
	//引入DAO操作
	@Resource(name = "assMeasurePlanMapper")
	private final AssMeasurePlanMapper assMeasurePlanMapper = null;
	
	@Resource(name = "assBaseService")
	private final AssBaseService assBaseService = null;
	
	@Resource(name = "assMeasurePlanAssMapper")
	private final AssMeasurePlanAssMapper assMeasurePlanAssMapper = null;
	
	@Resource(name = "assMeasurePlanAssService")
	private final AssMeasurePlanAssService assMeasurePlanAssService = null;
    
	// 默认显示20条数据
			RowBounds rowBoundsALL = new RowBounds(0, 20);
	/**
	 * @Description 
	 * 添加051204 检查计量计划<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addAssMeasurePlan(Map<String,Object> entityMap)throws DataAccessException{
		
		//获取对象051204 检查计量计划
		AssMeasurePlan assMeasurePlan = queryAssMeasurePlanByCode(entityMap);

		if (assMeasurePlan != null) {

			return "{\"error\":\"数据重复,请重新添加.\"}";

		}
		
		try {
			
			int state = assMeasurePlanMapper.addAssMeasurePlan(entityMap);
			
			Long sequence = assMeasurePlanMapper.queryCurrentSequence(); 
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\",\"sequence\":\""+sequence+"\"}"; 
			 

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}
		
	}
	/**
	 * @Description 
	 * 批量添加051204 检查计量计划<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addBatchAssMeasurePlan(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
			assMeasurePlanMapper.addBatchAssMeasurePlan(entityList);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}
		
	}
	
		/**
	 * @Description 
	 * 更新051204 检查计量计划<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateAssMeasurePlan(Map<String,Object> entityMap)throws DataAccessException{
		
		try {
			
		  int state = assMeasurePlanMapper.updateAssMeasurePlan(entityMap);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}	
		
	}
	/**
	 * @Description 
	 * 批量更新051204 检查计量计划<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateBatchAssMeasurePlan(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
		  assMeasurePlanMapper.updateBatchAssMeasurePlan(entityList);
			
			return "{\"msg\":\"操作成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}	
		
	}
	/**
	 * @Description 
	 * 删除051204 检查计量计划<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
    public String deleteAssMeasurePlan(Map<String, Object> entityMap) throws DataAccessException {
    	
    try {
			
			int state = assMeasurePlanMapper.deleteAssMeasurePlan(entityMap);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}	
		
  }
    
	/**
	 * @Description 
	 * 批量删除051204 检查计量计划<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String deleteBatchAssMeasurePlan(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			/**
			 * 删除计量计划表时 1、 删除计量计划资产明细  2、删除计量计划表
			 */
			
			assMeasurePlanAssMapper.deleteBatchAssMeasurePlanAss(entityList);
			
			assMeasurePlanMapper.deleteBatchAssMeasurePlan(entityList);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}	
	}
	
	/**
	 * @Description 
	 * 添加051204 检查计量计划<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addOrUpdateAssMeasurePlan(Map<String,Object> entityMap)throws DataAccessException{
		
		String plan_id = entityMap.get("plan_id").toString();
		String plan_no = entityMap.get("plan_no").toString();
		/**
		* 备注 先判断是否存在 存在即更新不存在则添加<br>
		* 在判断是否存在时可根据实际情况进行修改传递的参数进行判断<br>
		* 判断是否名称相同 判断是否 编码相同 等一系列规则
		*/
		//判断是否存在对象051204 检查计量计划
		Map<String, Object> mapVo=new HashMap<String, Object>();
		
		mapVo.put("group_id",entityMap.get("group_id"));
		
		mapVo.put("hos_id",entityMap.get("hos_id"));
		
    	mapVo.put("copy_code", entityMap.get("copy_code"));
    	
    	mapVo.put("plan_id", entityMap.get("plan_id"));
		
		List<AssMeasurePlan> list = assMeasurePlanMapper.queryAssMeasurePlanExists(mapVo);
		

		try {
			if (list.size()>0) {

				int state = assMeasurePlanMapper.updateAssMeasurePlan(entityMap);
				
				//return "{\"msg\":\"更新成功.\",\"state\":\"true\",\"plan_id\":\""+entityMap.get("plan_id")+"\",\"plan_no\":\""+entityMap.get("plan_no")+"\"}";


			}else{
			if(entityMap.get("create_date") != null && !"".equals(entityMap.get("create_date"))){
				entityMap.put("year", entityMap.get("create_date").toString().substring(0,4));
				entityMap.put("month", entityMap.get("create_date").toString().substring(5,7));
			}
			 entityMap.put("bill_table", "ASS_MEASURE_PLAN");
			
			 plan_no = assBaseService.getBillNOSeqNo(entityMap);
			 
			entityMap.put("plan_no", plan_no); 
			
			int state = assMeasurePlanMapper.addAssMeasurePlan(entityMap);
			
			 plan_id =String.valueOf( assMeasurePlanMapper.queryCurrentSequence());
			
			if(state > 0){
				
				assBaseService.updateAssBillNoMaxNo(entityMap);
				
			}
			//return "{\"msg\":\"添加成功.\",\"state\":\"true\",\"plan_id\":\""+plan_id+"\",\"plan_no\":\""+plan_no+"\"}";

			}
			List<Map> detail = ChdJson.fromJsonAsList(Map.class, entityMap.get("ParamVo").toString());
			for (Map<String, Object> detailVo : detail) {
				
				if (detailVo.get("ass_card_no") == null || "".equals(detailVo.get("ass_card_no"))) {
					continue;
				}
				detailVo.put("group_id", entityMap.get("group_id"));
				detailVo.put("hos_id", entityMap.get("hos_id"));
				detailVo.put("copy_code", entityMap.get("copy_code"));
				detailVo.put("plan_id", plan_id);
				detailVo.put("ass_card_no", detailVo.get("ass_card_no").toString());
				if(detailVo.get("ass_code") != null && !detailVo.get("ass_code").equals("")){
					detailVo.put("ass_code", detailVo.get("ass_code"));
				}else{
					detailVo.put("ass_code", "");
				}
				if(detailVo.get("ass_name") != null && !detailVo.get("ass_name").equals("")){
					detailVo.put("ass_name", detailVo.get("ass_name"));
				}else{
					detailVo.put("ass_name", "");
				}
				if(detailVo.get("ass_mondl") != null && !detailVo.get("ass_mondl").equals("")){
					detailVo.put("ass_mondl", detailVo.get("ass_mondl"));
				}else{
					detailVo.put("ass_mondl", "");
				}
				if(detailVo.get("ass_spec") != null && !detailVo.get("ass_spec").equals("")){
					detailVo.put("ass_spec", detailVo.get("ass_spec"));
				}else{
					detailVo.put("ass_spec", "");
				}
				if(detailVo.get("ass_brand") != null && !detailVo.get("ass_brand").equals("")){
					detailVo.put("ass_brand", detailVo.get("ass_brand"));
				}else{
					detailVo.put("ass_brand", "");
				}
				if(detailVo.get("fac_name") != null && !detailVo.get("fac_name").equals("")){
					detailVo.put("fac_name", detailVo.get("fac_name"));
				}else{
					detailVo.put("fac_name", "");
				}
				if(detailVo.get("measure_cycle") != null && !detailVo.get("measure_cycle").equals("")){
					detailVo.put("measure_cycle", detailVo.get("measure_cycle"));
				}else{
					detailVo.put("measure_cycle", "");
				}
				if(detailVo.get("measure_kind") != null && !detailVo.get("measure_kind").equals("")){
					detailVo.put("measure_kind", detailVo.get("measure_kind"));
				}else{
					detailVo.put("measure_kind", "");
				}
				if(detailVo.get("measure_desc") != null && !detailVo.get("measure_desc").equals("")){
					detailVo.put("measure_desc", detailVo.get("measure_desc"));
				}else{
					detailVo.put("measure_desc", "");
				}
				/*if(detailVo.get("is_inner") != null && !detailVo.get("is_inner").equals("")){
					detailVo.put("is_inner", detailVo.get("is_inner"));
				}else{
					detailVo.put("is_inner", "");
				}
				if(detailVo.get("outer_measure_org") != null && !detailVo.get("outer_measure_org").equals("")){
					detailVo.put("outer_measure_org", detailVo.get("outer_measure_org"));
				}else{
					detailVo.put("outer_measure_org", "");
				}*/
				if(detailVo.get("plan_exec_date") != null && !detailVo.get("plan_exec_date").equals("")){
					detailVo.put("plan_exec_date", DateUtil.stringToDate(detailVo.get("plan_exec_date").toString(), "yyyy-MM-dd"));
				} else{
					detailVo.put("plan_exec_date", "");
				}
				if (detailVo.get("detail_id") == null) {
					detailVo.put("detail_id", "0");
				}
				assMeasurePlanAssService.addOrUpdateAssMeasurePlanAss(detailVo);
			}
			
			return "{\"msg\":\"操作成功.\",\"state\":\"true\",\"plan_id\":\""+plan_id+"\",\"plan_no\":\""+plan_no+"\"}";
		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}
		
	}
	/**
	 * @Description 
	 * 查询结果集051204 检查计量计划<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String queryAssMeasurePlan(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<AssMeasurePlan> list = assMeasurePlanMapper.queryAssMeasurePlan(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<AssMeasurePlan> list = assMeasurePlanMapper.queryAssMeasurePlan(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
		
	}
	
	/**
	 * @Description 
	 * 获取对象051204 检查计量计划<BR> 
	 * @param  entityMap<BR>
	 *  参数为主键
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public AssMeasurePlan queryAssMeasurePlanByCode(Map<String,Object> entityMap)throws DataAccessException{
		return assMeasurePlanMapper.queryAssMeasurePlanByCode(entityMap);
	}
	
	/**
	 * @Description 
	 * 获取051204 检查计量计划<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return AssMeasurePlan
	 * @throws DataAccessException
	*/
	@Override
	public AssMeasurePlan queryAssMeasurePlanByUniqueness(Map<String,Object> entityMap)throws DataAccessException{
		return assMeasurePlanMapper.queryAssMeasurePlanByUniqueness(entityMap);
	}
	
	/**
	 * @Description 
	 * 获取051204 检查计量计划<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return List<AssMeasurePlan>
	 * @throws DataAccessException
	*/
	@Override
	public List<AssMeasurePlan> queryAssMeasurePlanExists(Map<String,Object> entityMap)throws DataAccessException{
		return assMeasurePlanMapper.queryAssMeasurePlanExists(entityMap);
	}
	@Override
	public String updateBatchAssMeasurePlanBack( List<Map<String,Object>> entityList) throws DataAccessException {
		// TODO Auto-generated method stub
		try {
			
			  assMeasurePlanMapper.updateBatchAssMeasurePlanBack(entityList);
				
				return "{\"msg\":\"操作成功.\",\"state\":\"true\"}";

			}
			catch (Exception e) {

				logger.error(e.getMessage(), e);

				throw new SysException(e.getMessage());

			}
	}
	@Override
	public String updateBatchAssMeasurePlanStop( List<Map<String,Object>> entityList) throws DataAccessException {
		// TODO Auto-generated method stub
		try {
			
			  assMeasurePlanMapper.updateBatchAssMeasurePlanStop(entityList);
				
				return "{\"msg\":\"操作成功.\",\"state\":\"true\"}";

			}
			catch (Exception e) {

				logger.error(e.getMessage(), e);

				throw new SysException(e.getMessage());

			}
	}
	/**
 * 查询专用设备卡片
 */
@Override
public String queryAssCardSpecial(Map<String, Object> entityMap) {

	/*RowBounds rowBounds = new RowBounds(0, 20);
	if (entityMap.get("pageSize") != null) {
		rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
	} else {
		rowBounds = rowBoundsALL;
	}*/
	SysPage sysPage = new SysPage();

	sysPage = (SysPage) entityMap.get("sysPage");
	
	RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
	
	List<Map<String, Object>> list = assMeasurePlanMapper.queryAssCardSpecial(entityMap, rowBounds);
	PageInfo page = new PageInfo(list);
	return ChdJson.toJson(list, page.getTotal());

}
/**
 * 查询一般设备卡片
 */
@Override
public String queryAssCardGeneral(Map<String, Object> entityMap) {

	/*RowBounds rowBounds = new RowBounds(0, 20);
	if (entityMap.get("pageSize") != null) {
		rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
	} else {
		rowBounds = rowBoundsALL;
	}*/
	SysPage sysPage = new SysPage();

	sysPage = (SysPage) entityMap.get("sysPage");
	
	RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
	
	List<Map<String, Object>> list = assMeasurePlanMapper.queryAssCardGeneral(entityMap, rowBounds);
	PageInfo page = new PageInfo(list);
	return ChdJson.toJson(list, page.getTotal());

}
/**
 * 查询房屋及建筑卡片
 */
@Override
public String queryAssCardHouse(Map<String, Object> entityMap) {

	/*RowBounds rowBounds = new RowBounds(0, 20);
	if (entityMap.get("pageSize") != null) {
		rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
	} else {
		rowBounds = rowBoundsALL;
	}*/
	SysPage sysPage = new SysPage();

	sysPage = (SysPage) entityMap.get("sysPage");
	
	RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
	
	List<Map<String, Object>> list = assMeasurePlanMapper.queryAssCardHouse(entityMap, rowBounds);
	PageInfo page = new PageInfo(list);
	return ChdJson.toJson(list, page.getTotal());

}
/**
 * 查询其他无形资产卡片
 */
@Override
public String queryAssCardInassets(Map<String, Object> entityMap) {

	/*RowBounds rowBounds = new RowBounds(0, 20);
	if (entityMap.get("pageSize") != null) {
		rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
	} else {
		rowBounds = rowBoundsALL;
	}*/
	SysPage sysPage = new SysPage();

	sysPage = (SysPage) entityMap.get("sysPage");
	
	RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
	
	List<Map<String, Object>> list = assMeasurePlanMapper.queryAssCardInassets(entityMap, rowBounds);
	PageInfo page = new PageInfo(list);
	return ChdJson.toJson(list, page.getTotal());

}
/**
 * 查询其他固定资产卡片
 */
@Override
public String queryAssCardOther(Map<String, Object> entityMap) {

	/*RowBounds rowBounds = new RowBounds(0, 20);
	if (entityMap.get("pageSize") != null) {
		rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
	} else {
		rowBounds = rowBoundsALL;
	}*/
	SysPage sysPage = new SysPage();

	sysPage = (SysPage) entityMap.get("sysPage");
	
	RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
	
	List<Map<String, Object>> list = assMeasurePlanMapper.queryAssCardOther(entityMap, rowBounds);
	PageInfo page = new PageInfo(list);
	return ChdJson.toJson(list, page.getTotal());

}
/**
 * 查询土地卡片
 */
@Override
public String queryAssCardLand(Map<String, Object> entityMap) {

	/*RowBounds rowBounds = new RowBounds(0, 20);
	if (entityMap.get("pageSize") != null) {
		rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
	} else {
		rowBounds = rowBoundsALL;
	}*/
	SysPage sysPage = new SysPage();

	sysPage = (SysPage) entityMap.get("sysPage");
	
	RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
	
	List<Map<String, Object>> list = assMeasurePlanMapper.queryAssCardLand(entityMap, rowBounds);
	PageInfo page = new PageInfo(list);
	return ChdJson.toJson(list, page.getTotal());

}
//计量提醒
@Override
public String queryAssMeasureRemindPlan(Map<String, Object> entityMap)
		throws DataAccessException {
	SysPage sysPage = new SysPage();
	
	sysPage = (SysPage) entityMap.get("sysPage");
	
	if (sysPage.getTotal()==-1){
		
		List<AssMeasurePlan> list = assMeasurePlanMapper.queryAssMeasureRemindPlan(entityMap);
		
		return ChdJson.toJson(list);
		
	}else{
		
		RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
		
		List<AssMeasurePlan> list = assMeasurePlanMapper.queryAssMeasureRemindPlan(entityMap, rowBounds);
		
		PageInfo page = new PageInfo(list);
		
		return ChdJson.toJson(list, page.getTotal());
		
	}
}

//新版打印  调用的方法
	@Override
	public Map<String, Object> queryAssMeasurePlanPrint(Map<String, Object> entityMap)throws DataAccessException {
		
		try{
			
			Map<String,Object> reMap=new HashMap<String,Object>();
			WebApplicationContext context = ContextLoader.getCurrentWebApplicationContext();
			AssMeasurePlanMapper assMeasurePlanMapper = (AssMeasurePlanMapper)context.getBean("assMeasurePlanMapper");
			 
			//主页面 批量打印查询
			if("1".equals(String.valueOf(entityMap.get("p_num")))){
				
				//查询 专用设备 入库主表
				List<Map<String,Object>> map= assMeasurePlanMapper.queryAssMeasurePlanByMainBatch(entityMap);
				//查询 专用设备  入库明细表
				List<Map<String,Object>> list= assMeasurePlanMapper.queryAssMeasurePlanByDetail(entityMap);
				
				reMap.put("main", map);
				
				reMap.put("detail", list); 
				
				return reMap;
				
			}else{ //修改页面 打印查询
				//
				Map<String,Object> map= assMeasurePlanMapper.queryAssMeasurePlanByMain(entityMap);
				//查询 专用设备  入库明细表
				List<Map<String,Object>> list= assMeasurePlanMapper.queryAssMeasurePlanByDetail(entityMap);
				
			
				reMap.put("main", map);
				
				reMap.put("detail", list);
				
				return reMap;
				
			}
			
		}catch(Exception e){
			logger.error(e.getMessage(),e);
			throw new SysException(e.getMessage());
		}
		
		
	}
	@Override
	public List<String> queryAssMeasurePlanState(Map<String, Object> mapVo) {
		// TODO Auto-generated method stub
		return assMeasurePlanMapper.queryAssMeasurePlanState(mapVo);
	}

}
