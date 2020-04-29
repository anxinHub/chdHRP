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
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.chd.base.SessionManager;
import com.chd.base.SysPage;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.hrp.ass.dao.dict.AssMaintainItemDictMapper;
import com.chd.hrp.ass.dao.maintain.AssMaintainPlanAssMapper;
import com.chd.hrp.ass.dao.maintain.AssMaintainPlanItemMapper;
import com.chd.hrp.ass.dao.maintain.AssMaintainPlanMapper;
import com.chd.hrp.ass.entity.dict.AssMaintainItemDict;
import com.chd.hrp.ass.entity.maintain.AssMaintainPlan;
import com.chd.hrp.ass.entity.maintain.AssMaintainPlanItem;
import com.chd.hrp.ass.service.base.AssBaseService;
import com.chd.hrp.ass.service.maintain.AssMaintainPlanAssService;
import com.chd.hrp.ass.service.maintain.AssMaintainPlanService;
import com.github.pagehelper.PageInfo;
 
/**
 * 
 * @Description:
 * 051202 保养计划
 * @Table:
 * ASS_MAINTAIN_PLAN
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 


@Service("assMaintainPlanService")
public class AssMaintainPlanServiceImpl implements AssMaintainPlanService {   

	private static Logger logger = Logger.getLogger(AssMaintainPlanServiceImpl.class);
	//引入DAO操作
	@Resource(name = "assMaintainPlanMapper")
	private final AssMaintainPlanMapper assMaintainPlanMapper = null;
	
	@Resource(name = "assMaintainPlanItemMapper")
	private final AssMaintainPlanItemMapper assMaintainPlanItemMapper = null;
	
	@Resource(name = "assMaintainPlanAssMapper")
	private final AssMaintainPlanAssMapper assMaintainPlanAssMapper = null;
	
	@Resource(name = "assBaseService")
	private final AssBaseService assBaseService = null;
	
	@Resource(name = "assMaintainPlanAssService")
	private final AssMaintainPlanAssService assMaintainPlanAssService = null;
	
	@Resource(name = "assMaintainItemDictMapper")
	private final AssMaintainItemDictMapper assMaintainItemDictMapper = null;
	
	/*@Resource(name = "assMaintainItemAffiMapper")
	private final AssMaintainItemAffiMapper assMaintainItemAffiMapper = null;*/
    
	/**
	 * @Description 
	 * 添加051202 保养计划<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addAssMaintainPlan(Map<String,Object> entityMap)throws DataAccessException{
		
		//获取对象051202 保养计划
		AssMaintainPlan assMaintainPlan = queryAssMaintainPlanByCode(entityMap);

		if (assMaintainPlan != null) {

			return "{\"error\":\"数据重复,请重新添加.\"}";

		}
		
		try {
			
			int state = assMaintainPlanMapper.addAssMaintainPlan(entityMap);
			
			Long sequence = assMaintainPlanMapper.queryCurrentSequence();
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\",\"sequence\":\""+sequence+"\"}"; 
 

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}
		
	}
	/**
	 * @Description 
	 * 批量添加051202 保养计划<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addBatchAssMaintainPlan(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
			assMaintainPlanMapper.addBatchAssMaintainPlan(entityList);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}
		
	}
	
		/**
	 * @Description 
	 * 更新051202 保养计划<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateAssMaintainPlan(Map<String,Object> entityMap)throws DataAccessException{
		
		try {
			
		  int state = assMaintainPlanMapper.updateAssMaintainPlan(entityMap);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}	
		
	}
	/**
	 * @Description 
	 * 批量更新051202 保养计划<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateBatchAssMaintainPlan(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
		  assMaintainPlanMapper.updateBatchAssMaintainPlan(entityList);
			
			return "{\"msg\":\"操作成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}	
		
	}
	/**
	 * @Description 
	 * 删除051202 保养计划<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
    public String deleteAssMaintainPlan(Map<String, Object> entityMap) throws DataAccessException {
    	
    try {
			
			int state = assMaintainPlanMapper.deleteAssMaintainPlan(entityMap);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}	
		
  }
    
	/**
	 * @Description 
	 * 批量删除051202 保养计划<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String deleteBatchAssMaintainPlan(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			/**
			 * 保养计划执行删除时   1、删除项目明细  2、删除资产明细 3、删除保养项目
			 */
			assMaintainPlanItemMapper.deleteBatchAssMaintainPlanItem(entityList);
			
			assMaintainPlanAssMapper.deleteBatchAssMaintainPlanAss(entityList);
			
			assMaintainPlanMapper.deleteBatchAssMaintainPlan(entityList);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}	
	}
	
	/**
	 * @Description 
	 * 添加051202 保养计划<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addOrUpdateAssMaintainPlan(Map<String,Object> entityMap)throws DataAccessException{
		/**
		* 备注 先判断是否存在 存在即更新不存在则添加<br>
		* 在判断是否存在时可根据实际情况进行修改传递的参数进行判断<br>
		* 判断是否名称相同 判断是否 编码相同 等一系列规则
		*/
		//判断是否存在对象051202 保养计划
		
		Map<String, Object> mapVo=new HashMap<String, Object>();
		
		mapVo.put("group_id",entityMap.get("group_id"));
		
		mapVo.put("hos_id",entityMap.get("hos_id"));
		
		mapVo.put("plan_exec_emp",entityMap.get("plan_exec_emp"));
    	mapVo.put("copy_code", entityMap.get("copy_code"));
    	 
    	mapVo.put("plan_id", entityMap.get("plan_id"));
		
		List<AssMaintainPlan> list = assMaintainPlanMapper.queryAssMaintainPlanExists(mapVo);
		
		if (list.size()>0) {

			int state = assMaintainPlanMapper.updateAssMaintainPlan(entityMap);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\",\"plan_id\":\""+entityMap.get("plan_id")+"\",\"plan_no\":\""+entityMap.get("plan_no")+"\"}";


		}
		
		try {
			if(entityMap.get("create_date") != null && !"".equals(entityMap.get("create_date"))){
				entityMap.put("year", entityMap.get("create_date").toString().substring(0,4));
				entityMap.put("month", entityMap.get("create_date").toString().substring(5,7));
			}
			entityMap.put("bill_table", "ASS_MAINTAIN_PLAN");
			String plan_no = assBaseService.getBillNOSeqNo(entityMap);
			 
			entityMap.put("plan_no", plan_no); 
			
			int state = assMaintainPlanMapper.addAssMaintainPlan(entityMap);
			
			Long plan_id = assMaintainPlanMapper.queryCurrentSequence();
			
			if(state > 0){
				
				assBaseService.updateAssBillNoMaxNo(entityMap);
				
			}
			return "{\"msg\":\"添加成功.\",\"state\":\"true\",\"plan_id\":\""+plan_id+"\",\"plan_no\":\""+plan_no+"\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}
		
	}
	/**
	 * @Description 
	 * 查询结果集051202 保养计划<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String queryAssMaintainPlan(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<AssMaintainPlan> list = assMaintainPlanMapper.queryAssMaintainPlan(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<AssMaintainPlan> list = assMaintainPlanMapper.queryAssMaintainPlan(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
		
	}
	
	/**
	 * @Description 
	 * 获取对象051202 保养计划<BR> 
	 * @param  entityMap<BR>
	 *  参数为主键
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public AssMaintainPlan queryAssMaintainPlanByCode(Map<String,Object> entityMap)throws DataAccessException{
		return assMaintainPlanMapper.queryAssMaintainPlanByCode(entityMap);
	}
	
	/**
	 * @Description 
	 * 获取051202 保养计划<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return AssMaintainPlan
	 * @throws DataAccessException
	*/
	@Override
	public AssMaintainPlan queryAssMaintainPlanByUniqueness(Map<String,Object> entityMap)throws DataAccessException{
		return assMaintainPlanMapper.queryAssMaintainPlanByUniqueness(entityMap);
	}
	
	/**
	 * @Description 
	 * 获取051202 保养计划<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return List<AssMaintainPlan>
	 * @throws DataAccessException
	*/
	@Override
	public List<AssMaintainPlan> queryAssMaintainPlanExists(Map<String,Object> entityMap)throws DataAccessException{
		return assMaintainPlanMapper.queryAssMaintainPlanExists(entityMap);
	}
	
	/**
	 * 消审
	 */
	@Override
	public String updateBatchAssMaintainPlanBack(List<Map<String,Object>> entityList) throws DataAccessException {
		// TODO Auto-generated method stub
		try {
			
			  assMaintainPlanMapper.updateBatchAssMaintainPlanBack(entityList);
				
				return "{\"msg\":\"操作成功.\",\"state\":\"true\"}";

			}
			catch (Exception e) {

				logger.error(e.getMessage(), e);

				throw new SysException(e.getMessage());

			}	
	}
	
	/**
	 * 终止计划
	 */
	@Override
	public String updateBatchAssMaintainPlanStop( List<Map<String,Object>> entityList) throws DataAccessException {
		// TODO Auto-generated method stub
		try {
			
			  assMaintainPlanMapper.updateBatchAssMaintainPlanStop(entityList);
				
				return "{\"msg\":\"操作成功.\",\"state\":\"true\"}";

			}
			catch (Exception e) {

				logger.error(e.getMessage(), e);

				throw new SysException(e.getMessage());

			}	
	}
	//保养提醒
	@Override
	public String queryAssMainRemindPlan(Map<String, Object> entityMap)
			throws DataAccessException {
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<AssMaintainPlan> list = assMaintainPlanMapper.queryAssMainRemindPlan(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<AssMaintainPlan> list = assMaintainPlanMapper.queryAssMainRemindPlan(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
	}
	
	//新版打印  调用的方法
		@Override
		public Map<String, Object> queryAssMainTainPlanPrint(Map<String, Object> entityMap)throws DataAccessException {
			
			try{
				
				Map<String,Object> reMap=new HashMap<String,Object>();
				WebApplicationContext context = ContextLoader.getCurrentWebApplicationContext();
				AssMaintainPlanMapper assMaintainPlanMapper = (AssMaintainPlanMapper)context.getBean("assMaintainPlanMapper");
				 
				//主页面 批量打印查询
				if("1".equals(String.valueOf(entityMap.get("p_num")))){
					
					//查询 专用设备 入库主表
					List<Map<String,Object>> map= assMaintainPlanMapper.queryAssMainTainPlanByMainBatch(entityMap);
					//查询 专用设备  入库明细表
					List<Map<String,Object>> list= assMaintainPlanMapper.queryAssMainTainPlanByDetail(entityMap);
					
					reMap.put("main", map);
					
					reMap.put("detail", list); 
					
					return reMap;
					
				}else{ //修改页面 打印查询
					//
					Map<String,Object> map= assMaintainPlanMapper.queryAssMainTainPlanByMain(entityMap);
					//查询 专用设备  入库明细表
					List<Map<String,Object>> list= assMaintainPlanMapper.queryAssMainTainPlanByDetail(entityMap);
					
				
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
		public List<String> queryAssMainTainState(Map<String, Object> mapVo) {
			// TODO Auto-generated method stub
			return assMaintainPlanMapper.queryAssMainTainState(mapVo);
		}
		
		
		@Override
		public String queryAssMaintainItem(Map<String, Object> entityMap) {
			
			
			
			SysPage sysPage = new SysPage();

			sysPage = (SysPage) entityMap.get("sysPage");

			if (sysPage.getTotal() == -1) {
				List<AssMaintainPlanItem> list = assMaintainPlanItemMapper.queryAssMaintainItem(entityMap);
				return ChdJson.toJson(list);
			} else {
				RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
				List<AssMaintainPlanItem> list = assMaintainPlanItemMapper.queryAssMaintainItem(entityMap, rowBounds);
				PageInfo page = new PageInfo(list);
				return ChdJson.toJson(list, page.getTotal());
			}
		}
		@Override
		public String buildAssMaintainItem(Map<String, Object> entityMap) {
			/*List<AssMaintainItemAffi> affiList = assMaintainItemAffiMapper.queryAssMaintainItemAffi(entityMap);
			if (affiList.size() > 0) {
				return ChdJson.toJson(affiList);
			}*/
			List<AssMaintainItemDict> dictList = assMaintainItemDictMapper.queryAssMaintainItemDict(entityMap);
			if (dictList.size() > 0) {
				return ChdJson.toJson(dictList);
			}
			return "{\"warn\":\"没有可生成验收项目.\"}";
		}
		@Override
		public String saveAssMaintainItem(List<Map<String, Object>> entityMap) {
			try {
				Map<String, Object> mapVo = new HashMap<String, Object>();
				mapVo.put("group_id", entityMap.get(0).get("group_id"));
				mapVo.put("hos_id", entityMap.get(0).get("hos_id"));
				mapVo.put("copy_code", entityMap.get(0).get("copy_code"));
				mapVo.put("plan_id", entityMap.get(0).get("plan_id"));
				mapVo.put("detail_id", entityMap.get(0).get("detail_id"));
				assMaintainPlanItemMapper.deleteAssMaintainPlanItem(mapVo);

				assMaintainPlanItemMapper.addBatchAssMaintainPlanItem(entityMap);

				return "{\"msg\":\"保存成功.\",\"state\":\"true\"}";

			} catch (Exception e) {
				logger.error(e.getMessage(), e);
				throw new SysException(e.getMessage());
			}
		}
		@Override
		public String deleteAssMaintainItem(List<Map<String, Object>> entityMap) {
			try {
				
				
				assMaintainPlanItemMapper.deleteBatchAssMaintainPlanItem(entityMap);

				return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

			} catch (Exception e) {
				logger.error(e.getMessage(), e);
				throw new SysException(e.getMessage());
			}
		}
		@Override
		public String addOrUpdateMain(Map<String, Object> entityMap) {
			
			String plan_id = entityMap.get("plan_id").toString();
			String plan_no = entityMap.get("plan_no").toString();
			
			Map<String, Object> buildMapVo1 = new HashMap<String, Object>();
			Map<String, Object> buildMap = new HashMap<String, Object>();
		
			String assBuildMaintainMainJson = "";
			String assMaintainMainDetail = "";
			// 判断是否存在对象050601 主表
			Map<String, Object> mapVo = new HashMap<String, Object>();
			mapVo.put("group_id", entityMap.get("group_id"));
			mapVo.put("hos_id", entityMap.get("hos_id"));
			mapVo.put("copy_code", entityMap.get("copy_code"));
			mapVo.put("plan_id", entityMap.get("plan_id"));
			mapVo.put("plan_exec_emp",entityMap.get("plan_exec_emp"));
			mapVo.put("plan_id", entityMap.get("plan_id"));
			
			List<AssMaintainPlan> assAcceptList = assMaintainPlanMapper.queryAssMaintainPlanExists(mapVo);
			entityMap.put("bill_table", "ASS_MAINTAIN_PLAN");
			try{
				
				if (assAcceptList.size() > 0) {
					assMaintainPlanMapper.updateAssMaintainPlan(entityMap);
				}else{
				    plan_no = assBaseService.getBillNOSeqNo(entityMap);
					entityMap.put("plan_no", plan_no);
					int state = assMaintainPlanMapper.addAssMaintainPlan(entityMap);
					plan_id = String.valueOf(queryCurrentSequence());
					if (state > 0) {
						assBaseService.updateAssBillNoMaxNo(entityMap);
					}
				}
				
				buildMapVo1.put("group_id", SessionManager.getGroupId());

				buildMapVo1.put("hos_id", SessionManager.getHosId());

				buildMapVo1.put("copy_code", SessionManager.getCopyCode());
				
				buildMapVo1.put("plan_id",plan_id);
				List<Map> detail = ChdJson.fromJsonAsList(Map.class, entityMap.get("ParamVo").toString());
				
				for (Map<String, Object> detailVo : detail) {

					detailVo.put("group_id", SessionManager.getGroupId());

					detailVo.put("hos_id", SessionManager.getHosId());

					detailVo.put("copy_code", SessionManager.getCopyCode());
					
					if (detailVo.get("ass_card_no") == null || "".equals(detailVo.get("ass_card_no"))) {
						continue;
					}
					detailVo.put("plan_id", plan_id);

					detailVo.put("plan_no", plan_no);
				
					if (detailVo.get("detail_id") == null) {
						detailVo.put("detail_id", "0");
					} else {
						detailVo.put("detail_id", detailVo.get("detail_id"));
					}
					
				/*	String ass_id_no = detailVo.get("ass_id").toString();

					detailVo.put("ass_id", ass_id_no.split("@")[0]);

					detailVo.put("ass_no", ass_id_no.split("@")[1]);
					
					String fac_id_no = String.valueOf(detailVo.get("fac_id"));

					if(fac_id_no != null && fac_id_no.split("@").length == 2){
						detailVo.put("fac_id", fac_id_no.split("@")[0]);
						detailVo.put("fac_no", fac_id_no.split("@")[1]);
					}else{
						detailVo.put("fac_id", null);
						detailVo.put("fac_no", null);
					}
					*/
					assMaintainPlanAssService.addOrUpdateAssMaintainDetail(detailVo);
				}	
				
				//生成验收项目列表
				List<Map<String, Object>> listVoBuild = null;
				List<Map<String, Object>> detailList = assMaintainPlanAssMapper.queryAssMaintainPlanAssByUpdate(buildMapVo1);
				
				for (Map<String, Object> map : detailList) {
					//buildMap.put("accept_detail_id", map.get("accept_detail_id"));
					buildMapVo1.put("ass_card_no", String.valueOf(map.get("ass_card_no")));
					buildMapVo1.put("maintain_degree", entityMap.get("maintain_degree"));
					assBuildMaintainMainJson = buildAssMaintainItem(buildMapVo1);
				    Map<String,Object> assBuildAcceptMainMap = JSONObject.parseObject(assBuildMaintainMainJson);
				    if(assBuildAcceptMainMap.get("warn") != null){
				    	//return ;
				    	throw new SysException("没有可生成的保养项目！");
				    }else {
					List<Map> list = JSONArray.parseArray(assBuildAcceptMainMap.get("Rows").toString(), Map.class);
					listVoBuild = new ArrayList<Map<String, Object>>();
					for (Map<String,Object> map2 : list) {
						map2.put("group_id", SessionManager.getGroupId());

						map2.put("hos_id", SessionManager.getHosId());
						map2.put("ass_card_no", map.get("ass_card_no"));

						map2.put("copy_code", SessionManager.getCopyCode());
						map2.put("detail_id", map.get("detail_id"));
						map2.put("plan_id", plan_id);
						map2.put("is_normal", "2");
						map2.put("item_code", map2.get("maintain_item_code"));
						map2.put("item_name", map2.get("maintain_item_name"));
						listVoBuild.add(map2);
					}
					
				}
				
				assMaintainPlanItemMapper.deleteAssMaintainPlanItem(buildMapVo1);

				assMaintainPlanItemMapper.addBatchAssMaintainPlanItem(listVoBuild);
				}
				return "{\"msg\":\"操作成功.\",\"state\":\"true\",\"plan_id\":\"" + plan_id + "\",\"plan_no\":\""
						+ plan_no + "\"}";
				
			} catch (Exception e) {

				logger.error(e.getMessage());

				throw new SysException(e.getMessage());

			}
		
		}
		
		@Override
		public Long queryCurrentSequence() throws DataAccessException {
			// TODO Auto-generated method stub
			return assMaintainPlanMapper.queryCurrentSequence();
		}
		@Override
		public String queryDetails(Map<String, Object> entityMap) {
			SysPage sysPage = new SysPage();

			sysPage = (SysPage) entityMap.get("sysPage");

			if (sysPage.getTotal() == -1) {

				List<Map<String, Object>> list = (List<Map<String, Object>>) assMaintainPlanMapper.queryDetails(entityMap);

				return ChdJson.toJson(list);

			} else {

				RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

				List<Map<String, Object>> list = (List<Map<String, Object>>) assMaintainPlanMapper.queryDetails(entityMap, rowBounds);

				PageInfo page = new PageInfo(list);

				return ChdJson.toJson(list, page.getTotal());

			}
		}
	
}
