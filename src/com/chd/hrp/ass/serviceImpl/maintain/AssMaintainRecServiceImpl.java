/** 
` * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.ass.serviceImpl.maintain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.chd.hrp.ass.dao.maintain.AssMaintainRecAssMapper;
import com.chd.hrp.ass.dao.maintain.AssMaintainRecItemMapper;
import com.chd.hrp.ass.dao.maintain.AssMaintainRecMapper;
import com.chd.hrp.ass.entity.dict.AssMaintainItemDict;
import com.chd.hrp.ass.entity.maintain.AssMaintainPlanItem;
import com.chd.hrp.ass.entity.maintain.AssMaintainRec;
import com.chd.hrp.ass.entity.maintain.AssMaintainRecItem;
import com.chd.hrp.ass.service.base.AssBaseService;
import com.chd.hrp.ass.service.maintain.AssMaintainRecAssService;
import com.chd.hrp.ass.service.maintain.AssMaintainRecService;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Description:
 * 051203 保养记录
 * @Table:
 * ASS_MAINTAIN_REC
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 
 

@Service("assMaintainRecService")
public class AssMaintainRecServiceImpl implements AssMaintainRecService { 

	private static Logger logger = Logger.getLogger(AssMaintainRecServiceImpl.class);
	//引入DAO操作
	@Resource(name = "assMaintainRecMapper")
	private final AssMaintainRecMapper assMaintainRecMapper = null;
	
	@Resource(name = "assMaintainRecAssMapper")
	private final AssMaintainRecAssMapper assMaintainRecAssMapper = null;
	
	@Resource(name = "assMaintainRecItemMapper")
	private final AssMaintainRecItemMapper assMaintainRecItemMapper = null;
	
	@Resource(name = "assMaintainRecAssService")
	private final AssMaintainRecAssService assMaintainRecAssService = null;
	
	@Resource(name = "assMaintainItemDictMapper")
	private final AssMaintainItemDictMapper assMaintainItemDictMapper = null;
	
	@Resource(name = "assBaseService")
	private final AssBaseService assBaseService = null;
	
	@Resource(name = "assMaintainPlanItemMapper")
	private final AssMaintainPlanItemMapper assMaintainPlanItemMapper = null;
	
	@Resource(name = "assMaintainPlanMapper")
	private final AssMaintainPlanMapper assMaintainPlanMapper = null;
	
	@Resource(name = "assMaintainPlanAssMapper")
	private final AssMaintainPlanAssMapper assMaintainPlanAssMapper = null;
    
	/**
	 * @Description 
	 * 添加051203 保养记录<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addAssMaintainRec(Map<String,Object> entityMap)throws DataAccessException{
		
		//获取对象051203 保养记录
		AssMaintainRec assMaintainRec = queryAssMaintainRecByCode(entityMap);

		if (assMaintainRec != null) {

			return "{\"error\":\"数据重复,请重新添加.\"}";

		}
		
		try {
			
			int state = assMaintainRecMapper.addAssMaintainRec(entityMap);

			Long sequence = assMaintainRecMapper.queryCurrentSequence();
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\",\"sequence\":\""+sequence+"\"}"; 

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}
		
	}
	/**
	 * @Description 
	 * 批量添加051203 保养记录<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addBatchAssMaintainRec(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
			assMaintainRecMapper.addBatchAssMaintainRec(entityList);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}
		
	}
	
		/**
	 * @Description 
	 * 更新051203 保养记录<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateAssMaintainRec(Map<String,Object> entityMap)throws DataAccessException{
		
		try {
			
		  int state = assMaintainRecMapper.updateAssMaintainRec(entityMap);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}	
		
	}
	/**
	 * @Description 
	 * 批量更新051203 保养记录<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateBatchAssMaintainRec(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
		  assMaintainRecMapper.updateBatchAssMaintainRec(entityList);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}	
		
	}
	/**
	 * @Description 
	 * 删除051203 保养记录<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
    public String deleteAssMaintainRec(Map<String, Object> entityMap) throws DataAccessException {
    	
    try {
			
			int state = assMaintainRecMapper.deleteAssMaintainRec(entityMap);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}	
		
  }
    
	/**
	 * @Description 
	 * 批量删除051203 保养记录<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String deleteBatchAssMaintainRec(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
			/**
			 * 删除保养记录时  1、删除保养记录项目明细  2、删除保养记录资产明细  3、删除保养记录
			 */
			
			assMaintainRecItemMapper.deleteBatchAssMaintainRecItem(entityList);
			
			assMaintainRecAssMapper.deleteBatchAssMaintainRecAss(entityList);
			
			assMaintainRecMapper.deleteBatchAssMaintainRec(entityList);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}	
	}
	
	/**
	 * @Description 
	 * 添加051203 保养记录<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addOrUpdateAssMaintainRec(Map<String,Object> entityMap)throws DataAccessException{
		/**
		* 备注 先判断是否存在 存在即更新不存在则添加<br>
		* 在判断是否存在时可根据实际情况进行修改传递的参数进行判断<br>
		* 判断是否名称相同 判断是否 编码相同 等一系列规则
		*/
		//判断是否存在对象051203 保养记录
		Map<String, Object> mapVo=new HashMap<String, Object>();
		
		mapVo.put("group_id",entityMap.get("group_id"));
		
		mapVo.put("hos_id",entityMap.get("hos_id"));
		
    	mapVo.put("copy_code", entityMap.get("copy_code"));
    	
    	mapVo.put("rec_id", entityMap.get("rec_id"));
		
		List<AssMaintainRec> list = assMaintainRecMapper.queryAssMaintainRecExists(mapVo);
		
		if (list.size()>0) {

			int state = assMaintainRecMapper.updateAssMaintainRec(entityMap);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\",\"rec_id\":\""+entityMap.get("rec_id")+"\",\"rec_no\":\""+entityMap.get("rec_no")+"\"}";


		}
		
		try {
			if(entityMap.get("create_date") != null && !"".equals(entityMap.get("create_date"))){
				entityMap.put("year", entityMap.get("create_date").toString().substring(0,4));
				entityMap.put("month", entityMap.get("create_date").toString().substring(5,7));
			}
			entityMap.put("bill_table", "ASS_MAINTAIN_REC");
			String rec_no = assBaseService.getBillNOSeqNo(entityMap);
			 
			entityMap.put("rec_no", rec_no); 
			
			int state = assMaintainRecMapper.addAssMaintainRec(entityMap);
			
			Long rec_id = assMaintainRecMapper.queryCurrentSequence();
			
			if(state > 0){
				
				assBaseService.updateAssBillNoMaxNo(entityMap);
				
			}
			return "{\"msg\":\"添加成功.\",\"state\":\"true\",\"rec_id\":\""+rec_id+"\",\"rec_no\":\""+rec_no+"\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}
		
	}
	/**
	 * @Description 
	 * 查询结果集051203 保养记录<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String queryAssMaintainRec(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<AssMaintainRec> list = assMaintainRecMapper.queryAssMaintainRec(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<AssMaintainRec> list = assMaintainRecMapper.queryAssMaintainRec(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
		
	}
	
	/**
	 * @Description 
	 * 获取对象051203 保养记录<BR> 
	 * @param  entityMap<BR>
	 *  参数为主键
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public AssMaintainRec queryAssMaintainRecByCode(Map<String,Object> entityMap)throws DataAccessException{
		return assMaintainRecMapper.queryAssMaintainRecByCode(entityMap);
	}
	
	/**
	 * @Description 
	 * 获取051203 保养记录<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return AssMaintainRec
	 * @throws DataAccessException
	*/
	@Override
	public AssMaintainRec queryAssMaintainRecByUniqueness(Map<String,Object> entityMap)throws DataAccessException{
		return assMaintainRecMapper.queryAssMaintainRecByUniqueness(entityMap);
	}
	
	/**
	 * @Description 
	 * 获取051203 保养记录<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return List<AssMaintainRec>
	 * @throws DataAccessException
	*/
	@Override
	public List<AssMaintainRec> queryAssMaintainRecExists(Map<String,Object> entityMap)throws DataAccessException{
		return assMaintainRecMapper.queryAssMaintainRecExists(entityMap);
	}
	@Override
	public String updateBatchAssMaintainRecBack( List<Map<String,Object>> entityList) throws DataAccessException {
		// TODO Auto-generated method stub

		try {
			
			  assMaintainRecMapper.updateBatchAssMaintainRecBack(entityList);
				
				return "{\"msg\":\"操作成功.\",\"state\":\"true\"}";

			}
			catch (Exception e) {

				logger.error(e.getMessage(), e);

				throw new SysException(e.getMessage());

			}	
		
	}
	@Override
	public String updateBatchAssMaintainRecStop(List<Map<String,Object>> entityList) throws DataAccessException {
		// TODO Auto-generated method stub
		try {
			
			  assMaintainRecMapper.updateBatchAssMaintainRecStop(entityList);
				
				return "{\"msg\":\"操作成功.\",\"state\":\"true\"}";

			}
			catch (Exception e) {

				logger.error(e.getMessage(), e);

				throw new SysException(e.getMessage());

			}	
		
	}
	@Override
	public String queryAssMaintainRecCard(Map<String, Object> entityMap) throws DataAccessException {
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<AssMaintainRec> list = assMaintainRecMapper.queryAssMaintainRecCard(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<AssMaintainRec> list = assMaintainRecMapper.queryAssMaintainRecCard(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
		
	}
	@Override
	public String addOrUpdateMainRec(Map<String, Object> entityMap) {
		
		String rec_id = entityMap.get("rec_id").toString();
		String rec_no = entityMap.get("rec_no").toString();
		
		Map<String, Object> buildMapVo1 = new HashMap<String, Object>();
		Map<String, Object> buildMap = new HashMap<String, Object>();
	
		String assBuildMaintainMainJson = "";
		String assMaintainMainDetail = "";
	
		// 判断是否存在对象050601 主表
		Map<String, Object> mapVo = new HashMap<String, Object>();
		mapVo.put("group_id",entityMap.get("group_id"));
		
		mapVo.put("hos_id",entityMap.get("hos_id"));
		
    	mapVo.put("copy_code", entityMap.get("copy_code"));
    	
    	mapVo.put("rec_id", entityMap.get("rec_id"));
    	
		
		List<AssMaintainRec> list = assMaintainRecMapper.queryAssMaintainRecExists(mapVo);
	entityMap.put("bill_table", "ASS_MAINTAIN_REC");
		try{
			if (list.size()>0) {
				assMaintainRecMapper.updateAssMaintainRec(entityMap);
			}else{
				

				 rec_no = assBaseService.getBillNOSeqNo(entityMap);
				 
				entityMap.put("rec_no", rec_no); 
				
				int state = assMaintainRecMapper.addAssMaintainRec(entityMap);
				
				rec_id = String.valueOf(queryCurrentSequence());
				
				if(state > 0){
					
					assBaseService.updateAssBillNoMaxNo(entityMap);
					
				}
			}
				buildMapVo1.put("group_id", SessionManager.getGroupId());

				buildMapVo1.put("hos_id", SessionManager.getHosId());

				buildMapVo1.put("copy_code", SessionManager.getCopyCode());
				
				buildMapVo1.put("rec_id",rec_id);
				
				buildMapVo1.put("plan_id", entityMap.get("plan_id"));
				
				
				List<Map> detail = ChdJson.fromJsonAsList(Map.class, entityMap.get("ParamVo").toString());
				for (Map<String, Object> detailVo : detail) {
					
					detailVo.put("group_id", SessionManager.getGroupId());
	
					detailVo.put("hos_id", SessionManager.getHosId());
	
					detailVo.put("copy_code", SessionManager.getCopyCode());
	
					if (detailVo.get("ass_card_no") == null || "".equals(detailVo.get("ass_card_no"))) {
						continue;
					}
					detailVo.put("item_code", detailVo.get("maintain_item_code"));
					
					detailVo.put("item_name", detailVo.get("maintain_item_name"));
	
					detailVo.put("maintain_money", detailVo.get("maintain_money"));
	
					detailVo.put("maintain_unit", detailVo.get("maintain_unit"));
	
					detailVo.put("maintain_hours", detailVo.get("maintain_hours"));
	
					detailVo.put("rec_id", rec_id);
					
					detailVo.put("rec_no", rec_no);
	
					if (detailVo.get("detail_id") == null) {
						detailVo.put("detail_id", "0");
					} else {
						detailVo.put("detail_id", detailVo.get("detail_id"));
					}
					
					assMaintainRecAssService.addOrUpdateAssMaintainRecDetail(detailVo);
					
				} 
				
				//生成验收项目列表
				//buildMapVo1.put("ass_card_no", ass_card_nos);
				List<AssMaintainPlanItem> list2  = assMaintainPlanItemMapper.queryAssMaintainItem(buildMapVo1);
				if(entityMap.get("plan_id") != null && entityMap.get("plan_id") != ""){
					if(list2.size() > 0){
						List<Map<String, Object>> listVoBuild = null;
						List<Map<String, Object>> detailList = assMaintainRecAssMapper.queryAssMaintainRecAssByUpdate(buildMapVo1);
						
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
							List<Map> list1 = JSONArray.parseArray(assBuildAcceptMainMap.get("Rows").toString(), Map.class);
							listVoBuild = new ArrayList<Map<String, Object>>();
							for (Map<String,Object> map2 : list1) {
								map2.put("group_id", SessionManager.getGroupId());
	
								map2.put("hos_id", SessionManager.getHosId());
								map2.put("ass_card_no", map.get("ass_card_no"));
	
								map2.put("copy_code", SessionManager.getCopyCode());
								map2.put("detail_id", map.get("detail_id"));
								map2.put("rec_id", rec_id);
								map2.put("is_normal", "2");
								map2.put("item_code", map2.get("item_code"));
								map2.put("item_name", map2.get("item_name"));
								listVoBuild.add(map2);
							}
							
						}
						
						    assMaintainRecItemMapper.deleteAssMaintainRecItem(buildMapVo1);
	
							assMaintainRecItemMapper.addBatchAssMaintainRecItem(listVoBuild);
						}
						
					} else{
						List<Map<String, Object>> listVoBuild = null;
						List<Map<String, Object>> detailList = assMaintainRecAssMapper.queryAssMaintainRecAssByUpdate(buildMapVo1);
						
						for (Map<String, Object> map : detailList) {
							//buildMap.put("accept_detail_id", map.get("accept_detail_id"));
							buildMapVo1.put("ass_card_no", String.valueOf(map.get("ass_card_no")));
							assBuildMaintainMainJson = buildAssRecItem(buildMapVo1);
						    Map<String,Object> assBuildAcceptMainMap = JSONObject.parseObject(assBuildMaintainMainJson);
						    if(assBuildAcceptMainMap.get("warn") != null){
						    	//return ;
						    	throw new SysException("没有可生成的保养项目！");
						    }else {
							List<Map> list1 = JSONArray.parseArray(assBuildAcceptMainMap.get("Rows").toString(), Map.class);
							listVoBuild = new ArrayList<Map<String, Object>>();
							for (Map<String,Object> map2 : list1) {
								map2.put("group_id", SessionManager.getGroupId());
		
								map2.put("hos_id", SessionManager.getHosId());
								map2.put("ass_card_no", map.get("ass_card_no"));
		
								map2.put("copy_code", SessionManager.getCopyCode());
								map2.put("detail_id", map.get("detail_id"));
								map2.put("rec_id", rec_id);
								map2.put("is_normal", "2");
								map2.put("item_code", map2.get("maintain_item_code"));
								map2.put("item_name", map2.get("maintain_item_name"));
								if( map2.get("note") == null){
									map2.put("note", "");
								}else{
									map2.put("note", map2.get("note"));
								}
								listVoBuild.add(map2);
							}
							
						}
						
					assMaintainRecItemMapper.deleteAssMaintainRecItem(buildMapVo1);
	
					assMaintainRecItemMapper.addBatchAssMaintainRecItem(listVoBuild);
					}
					}
				}
				return "{\"msg\":\"操作成功.\",\"state\":\"true\",\"rec_id\":\"" + rec_id + "\",\"rec_no\":\""
						+ rec_no + "\"}";
			
			
			
		} catch (Exception e) {

			logger.error(e.getMessage());

			throw new SysException(e.getMessage());

		}
	
	}
	
	
	@Override
	public String buildAssMaintainItem(Map<String, Object> entityMap) {
		/*List<AssMaintainItemAffi> affiList = assMaintainItemAffiMapper.queryAssMaintainItemAffi(entityMap);
		if (affiList.size() > 0) {
			return ChdJson.toJson(affiList);
		}*/
		List<AssMaintainPlanItem> dictList = assMaintainPlanItemMapper.queryAssMaintainItem(entityMap);
		if (dictList.size() > 0) {
			return ChdJson.toJson(dictList);
		}
		return "{\"warn\":\"没有可生成验收项目.\"}";
	}
	
	
	@Override
	public Long queryCurrentSequence() throws DataAccessException {
		// TODO Auto-generated method stub
		return assMaintainRecMapper.queryCurrentSequence();
	}
	@Override
	public String saveAssMaintainItem(List<Map<String, Object>> entityMap) {
		try {
			Map<String, Object> mapVo = new HashMap<String, Object>();
			mapVo.put("group_id", entityMap.get(0).get("group_id"));
			mapVo.put("hos_id", entityMap.get(0).get("hos_id"));
			mapVo.put("copy_code", entityMap.get(0).get("copy_code"));
			mapVo.put("rec_id", entityMap.get(0).get("rec_id"));
			mapVo.put("detail_id", entityMap.get(0).get("detail_id"));
			assMaintainRecItemMapper.deleteAssMaintainRecItem(mapVo);

			assMaintainRecItemMapper.addBatchAssMaintainRecItem(entityMap);

			return "{\"msg\":\"保存成功.\",\"state\":\"true\"}";

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}
	}
	@Override
	public String deleteAssMaintainItem(List<Map<String, Object>> entityMap) {
		try {
			
			
			assMaintainRecItemMapper.deleteBatchAssMaintainRecItem(entityMap);

			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}
	}
	@Override
	public String buildAssRecItem(Map<String, Object> entityMap) {
		List<AssMaintainItemDict> dictList = assMaintainItemDictMapper.queryAssMaintainItemDict(entityMap);
		if (dictList.size() > 0) {
			return ChdJson.toJson(dictList);
		}
		return "{\"warn\":\"没有可生成验收项目.\"}";
	}
	@Override
	public String queryAssRecItem(Map<String, Object> entityMap) {
		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		if (sysPage.getTotal() == -1) {
			List<AssMaintainRecItem> list = assMaintainRecItemMapper.queryAssMaintainRecItemm(entityMap);
			return ChdJson.toJson(list);
		} else {
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			List<AssMaintainRecItem> list = assMaintainRecItemMapper.queryAssMaintainRecItemm(entityMap, rowBounds);
			PageInfo page = new PageInfo(list);
			return ChdJson.toJson(list, page.getTotal());
		}
	}
	
	//新版打印  调用的方法
	@Override
	public Map<String, Object> queryAssMainRecPrint(Map<String, Object> entityMap)throws DataAccessException {
		
		try{
			
			Map<String,Object> reMap=new HashMap<String,Object>();
			WebApplicationContext context = ContextLoader.getCurrentWebApplicationContext();
			AssMaintainRecMapper assMaintainRecMapper = (AssMaintainRecMapper)context.getBean("assMaintainRecMapper");
			 
			//主页面 批量打印查询
			if("1".equals(String.valueOf(entityMap.get("p_num")))){
				
				//查询 专用设备 入库主表
				List<Map<String,Object>> map= assMaintainRecMapper.queryAssMainRecPlanByMainBatch(entityMap);
				//查询 专用设备  入库明细表
				List<Map<String,Object>> list= assMaintainRecMapper.queryAssMainRecPlanByDetail(entityMap);
				
				reMap.put("main", map);
				
				reMap.put("detail", list); 
				
				return reMap;
				
			}else{ //修改页面 打印查询
				//
				Map<String,Object> map= assMaintainRecMapper.queryAssMainRecPlanByMain(entityMap);
				//查询 专用设备  入库明细表
				List<Map<String,Object>> list= assMaintainRecMapper.queryAssMainRecPlanByDetail(entityMap);
				
			
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
	public List<String> queryAssMainRecState(Map<String, Object> mapVo) {
		// TODO Auto-generated method stub
		return assMaintainRecMapper.queryAssMainRecState(mapVo);
	}
	@Override
	public String queryDetails(Map<String, Object> entityMap) {
		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		if (sysPage.getTotal() == -1) {

			List<Map<String, Object>> list = (List<Map<String, Object>>) assMaintainRecMapper.queryDetails(entityMap);

			return ChdJson.toJson(list);

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

			List<Map<String, Object>> list = (List<Map<String, Object>>) assMaintainRecMapper.queryDetails(entityMap, rowBounds);

			PageInfo page = new PageInfo(list);

			return ChdJson.toJson(list, page.getTotal()); 

		}
	}
	@Override
	public int queryAssMainRecStateList(List<Map<String, Object>> listVo)
			throws DataAccessException {
		// TODO Auto-generated method stub
		return assMaintainRecMapper.queryAssMainRecStateList(listVo);
	}
	
	
}
