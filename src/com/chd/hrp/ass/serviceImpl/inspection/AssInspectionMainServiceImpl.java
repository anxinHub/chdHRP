package com.chd.hrp.ass.serviceImpl.inspection; 

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
import com.chd.hrp.ass.dao.dict.AssCheckItemAffiMapper;
import com.chd.hrp.ass.dao.dict.AssCheckItemDictMapper;
import com.chd.hrp.ass.dao.dict.AssInspectionItemMapper;
import com.chd.hrp.ass.dao.inspection.AssInspectionDetailMapper;
import com.chd.hrp.ass.dao.inspection.AssInspectionMainMapper;
import com.chd.hrp.ass.entity.accept.AssAcceptDetail;
import com.chd.hrp.ass.entity.dict.AssCheckItemAffi;
import com.chd.hrp.ass.entity.dict.AssCheckItemDict;
import com.chd.hrp.ass.entity.dict.AssInspectionItem;
import com.chd.hrp.ass.dao.maintain.AssMaintainPlanMapper;
import com.chd.hrp.ass.entity.inspection.AssInspectionMain;
import com.chd.hrp.ass.service.base.AssBaseService;
import com.chd.hrp.ass.service.inspection.AssInspectionMainService;
import com.github.pagehelper.PageInfo;

@Service("assInspectionMainService")
public class AssInspectionMainServiceImpl implements AssInspectionMainService {
 
	private static Logger logger = Logger.getLogger(AssInspectionMainServiceImpl.class);
	
	// 引入DAO操作
	@Resource(name = "assInspectionMainMapper")
	private final AssInspectionMainMapper assInspectionMainMapper = null;

	@Resource(name = "assInspectionDetailMapper")
	private final AssInspectionDetailMapper assInspectionDetailMapper = null;
	
	@Resource(name = "assInspectionItemMapper")
	private final AssInspectionItemMapper assInspectionItemMapper = null;
	
	@Resource(name = "assCheckItemAffiMapper")
	private final AssCheckItemAffiMapper assCheckItemAffiMapper = null;
	
	@Resource(name = "assCheckItemDictMapper")
	private final AssCheckItemDictMapper assCheckItemDictMapper = null;
	
	@Resource(name = "assBaseService")
	private final AssBaseService assBaseService = null;

	/**
	 * 巡检记录表 添加
	 */
	@Override
	public String addAssInspectionMain(Map<String, Object> entityMap) throws DataAccessException {
		
		// 获取对象051001资产巡检主表
		AssInspectionMain assInspectionMain = queryAssInspectionMainByCode(entityMap);

		if (assInspectionMain != null) {

			return "{\"error\":\"数据重复,请重新添加.\"}";

		}

		try {

			int state = assInspectionMainMapper.addAssInspectionMain(entityMap);

			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}
	}

	/**
	 * 巡检记录表 查询（Bycode）
	 */
	public AssInspectionMain queryAssInspectionMainByCode( Map<String, Object> entityMap) {

		return assInspectionMainMapper.queryAssInspectionMainByCode(entityMap);

	}

	/**
	 * 巡检记录表 修改
	 */
	@Override
	public String updateAssInspectionMain(Map<String, Object> entityMap) throws DataAccessException {
		
		try {

			int state = assInspectionMainMapper.updateAssInspectionMain(entityMap);

			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}
	}

	/**
	 * 巡检记录表 添加
	 */
	@Override
	public String addOrUpdateAssInspectionMain(Map<String, Object> entityMap) throws DataAccessException {
		/**
		 * 备注 先判断是否存在 存在即更新不存在则添加<br>
		 * 在判断是否存在时可根据实际情况进行修改传递的参数进行判断<br>
		 * 判断是否名称相同 判断是否 编码相同 等一系列规则
		 */
		// 判断是否存在对象051001资产处置主表
		Map<String, Object> buildMapVo1 = new HashMap<String, Object>();
		Map<String, Object> mapVo = new HashMap<String, Object>();
		
		mapVo.put("group_id", entityMap.get("group_id"));
		
		mapVo.put("hos_id", entityMap.get("hos_id"));
		
		mapVo.put("copy_code", entityMap.get("copy_code"));
		
		mapVo.put("acct_year", entityMap.get("acct_year"));
		
		mapVo.put("ins_id", entityMap.get("ins_id"));

		List<AssInspectionMain> list = assInspectionMainMapper.queryAssInspectionMainExists(mapVo);

		if (list.size()>0) {

			int state = assInspectionMainMapper.updateAssInspectionMain(entityMap);

			return "{\"msg\":\"更新成功.\",\"state\":\"true\",\"ins_id\":\""+entityMap.get("ins_id")+"\",\"ins_no\":\""+entityMap.get("ins_no")+"\"}";


		}
		Long ins_id = null;
		try {

			if(entityMap.get("create_date") != null && !"".equals(entityMap.get("create_date"))){
				entityMap.put("year", entityMap.get("create_date").toString().substring(0,4));
				entityMap.put("month", entityMap.get("create_date").toString().substring(5,7));
			}
			entityMap.put("bill_table", "ASS_INSPECTION");
			String ins_no = assBaseService.getBillNOSeqNo(entityMap);
			 
			entityMap.put("ins_no", ins_no); 
			
			int state = assInspectionMainMapper.addAssInspectionMain(entityMap);
			
			ins_id = assInspectionMainMapper.queryCurrentSequence();
			
			if(state > 0){
				
				assBaseService.updateAssBillNoMaxNo(entityMap);
				
			}
			buildMapVo1.put("group_id", SessionManager.getGroupId());

			buildMapVo1.put("hos_id", SessionManager.getHosId());

			buildMapVo1.put("copy_code", SessionManager.getCopyCode());
			
			buildMapVo1.put("ins_id",ins_id);

			String assBuildAcceptMainJson = "";
			
			//生成验收项目列表
			List<Map<String, Object>> listVoBuild = new ArrayList<Map<String, Object>>();
			List<Map<String, Object>> detailList = assInspectionDetailMapper.queryAssInspectionDetailByUpdate(buildMapVo1);
			
			for (Map<String, Object> map : detailList) {
				//buildMap.put("accept_detail_id", map.get("accept_detail_id"));
				buildMapVo1.put("ass_id", String.valueOf(map.get("ass_id")).split("@")[0]);
				assBuildAcceptMainJson = buildAssInsItem(buildMapVo1);
			    Map<String,Object> assBuildAcceptMainMap = JSONObject.parseObject(assBuildAcceptMainJson);
			    if(assBuildAcceptMainMap.get("warn") != null){
			    	//return ;
			    	throw new SysException("没有可生成的巡检项目！");
			    }else {
				List<Map> list1 = JSONArray.parseArray(assBuildAcceptMainMap.get("Rows").toString(), Map.class);
				for (Map<String,Object> map2 : list1) {
					map2.put("group_id", SessionManager.getGroupId());

					map2.put("hos_id", SessionManager.getHosId());

					map2.put("copy_code", SessionManager.getCopyCode());
					map2.put("detail_id", map.get("detail_id"));
					map2.put("ins_id", ins_id);
					
					map2.put("is_normal", "2");
					map2.put("item_code", map2.get("accept_item_code"));
					listVoBuild.add(map2);
				}
				
			}
			
			    assInspectionItemMapper.deleteAssInsItem(buildMapVo1);

			    assInspectionItemMapper.addBatchAssInsItem(listVoBuild);
			}
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\",\"ins_id\":\""+ins_id+"\",\"ins_no\":\""+ins_no+"\"}";
		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}

	}

	/**
	 * 巡检记录表 删除
	 */
	@Override
	public String deleteBatchAssInspectionMain( List<Map<String, Object>> entityList) throws DataAccessException {
		
		
		try {

			/**
			 * 删除巡检记录表时 1、删除巡检记录明细 2、删除巡检记录表
			 */
			assInspectionDetailMapper.deleteBatchAssInspectionDetail(entityList);

			assInspectionMainMapper.deleteBatchAssInspectionMain(entityList);

			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}
	}

	/**
	 * 巡检记录表 查询（分页）
	 */
	@Override
	public String queryAssInspectionMain(Map<String, Object> entityMap) throws DataAccessException {
		
		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		if (sysPage.getTotal() == -1) {

			List<AssInspectionMain> list = assInspectionMainMapper.queryAssInspectionMain(entityMap);

			return ChdJson.toJson(list);

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(),sysPage.getPagesize());

			List<AssInspectionMain> list = assInspectionMainMapper.queryAssInspectionMain(entityMap, rowBounds);

			PageInfo page = new PageInfo(list);

			return ChdJson.toJson(list, page.getTotal());

		}
	}

	@Override
	public AssInspectionMain queryAssInspectionMainByUniqueness(Map<String, Object> entityMap) throws DataAccessException {
		
		return null;
		
	}

	/**
	 * 巡检记录 审核
	 */
	@Override
	public String updateBatchAssInspectionMain( List<Map<String, Object>> entityList) throws DataAccessException {
		
		try {

			assInspectionMainMapper.updateBatchAssInspectionMain(entityList);

			return "{\"msg\":\"操作成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}
	}

	/**
	 * 巡检记录 消审
	 */
	@Override
	public String updateBatchAssInspectionMainBack( List<Map<String, Object>> entityList) throws DataAccessException {

		try {

			assInspectionMainMapper.updateBatchAssInspectionMainBack(entityList);

			return "{\"msg\":\"操作成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}
	}

	/**
	 * 巡检记录 终止
	 */
	@Override
	public String updateBatchAssInspectionMainStop( List<Map<String, Object>> entityList) throws DataAccessException {

		try {

			assInspectionMainMapper.updateBatchAssInspectionMainStop(entityList);

			return "{\"msg\":\"操作成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}
	}

	@Override
	public String queryAssInspectionMainByCard(Map<String, Object> entityMap) throws DataAccessException {
		
		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		if (sysPage.getTotal() == -1) {

			List<AssInspectionMain> list = assInspectionMainMapper.queryAssInspectionMainByCard(entityMap);

			return ChdJson.toJson(list);

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(),sysPage.getPagesize());

			List<AssInspectionMain> list = assInspectionMainMapper.queryAssInspectionMainByCard(entityMap, rowBounds);

			PageInfo page = new PageInfo(list);

			return ChdJson.toJson(list, page.getTotal());

		}
	}
	// 默认显示20条数据
	RowBounds rowBoundsALL = new RowBounds(0, 20);
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
		
		List<Map<String, Object>> list = assInspectionMainMapper.queryAssCardSpecial(entityMap, rowBounds);
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
		
		List<Map<String, Object>> list = assInspectionMainMapper.queryAssCardGeneral(entityMap, rowBounds);
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
		
		List<Map<String, Object>> list = assInspectionMainMapper.queryAssCardHouse(entityMap, rowBounds);
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
		
		List<Map<String, Object>> list = assInspectionMainMapper.queryAssCardInassets(entityMap, rowBounds);
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
		
		List<Map<String, Object>> list = assInspectionMainMapper.queryAssCardOther(entityMap, rowBounds);
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
		
		List<Map<String, Object>> list = assInspectionMainMapper.queryAssCardLand(entityMap, rowBounds);
		PageInfo page = new PageInfo(list);
		return ChdJson.toJson(list, page.getTotal());

	}
	
	//新版打印  调用的方法
	@Override
	public Map<String, Object> queryAssInSpectionMainPrint(Map<String, Object> entityMap)throws DataAccessException {
		
		try{
			
			Map<String,Object> reMap=new HashMap<String,Object>();
			WebApplicationContext context = ContextLoader.getCurrentWebApplicationContext();
			AssInspectionMainMapper assInspectionMainMapper = (AssInspectionMainMapper)context.getBean("assInspectionMainMapper");
			 
			//主页面 批量打印查询
			if("1".equals(String.valueOf(entityMap.get("p_num")))){
				
				//查询 专用设备 入库主表
				List<Map<String,Object>> map= assInspectionMainMapper.queryInspectionMainByMainBatch(entityMap);
				//查询 专用设备  入库明细表
				List<Map<String,Object>> list= assInspectionMainMapper.queryInspectionMainByDetail(entityMap);
				
				reMap.put("main", map);
				
				reMap.put("detail", list); 
				
				return reMap;
				
			}else{ //修改页面 打印查询
				//
				Map<String,Object> map= assInspectionMainMapper.queryInspectionMainByMain(entityMap);
				//查询 专用设备  入库明细表
				List<Map<String,Object>> list= assInspectionMainMapper.queryInspectionMainByDetail(entityMap);
				
			
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
	public String buildAssInsItem(Map<String, Object> entityMap) throws DataAccessException {
		List<AssCheckItemAffi> affiList = assCheckItemAffiMapper.queryAssCheckItemAffi(entityMap);
		if (affiList.size() > 0) {
			return ChdJson.toJson(affiList);
		}
		List<AssCheckItemDict> dictList = assCheckItemDictMapper.queryAssCheckItemDict(entityMap);
		if (dictList.size() > 0) {
			return ChdJson.toJson(dictList);
		}
		return "{\"warn\":\"没有可生成巡检项目.\"}";
	}

	@Override
	public String queryAssInsItem(Map<String, Object> entityMap) throws DataAccessException {
		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		if (sysPage.getTotal() == -1) {
			List<AssInspectionItem> list = assInspectionItemMapper.queryAssInsItem(entityMap);
			return ChdJson.toJson(list);
		} else {
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			List<AssInspectionItem> list = assInspectionItemMapper.queryAssInsItem(entityMap, rowBounds);
			PageInfo page = new PageInfo(list);
			return ChdJson.toJson(list, page.getTotal());
		}
	}

	@Override
	public String saveAssInsItem(List<Map<String, Object>> entityMap) throws DataAccessException {
		try {
			Map<String, Object> mapVo = new HashMap<String, Object>();
			mapVo.put("group_id", entityMap.get(0).get("group_id"));
			mapVo.put("hos_id", entityMap.get(0).get("hos_id"));
			mapVo.put("copy_code", entityMap.get(0).get("copy_code"));
			mapVo.put("ins_id", entityMap.get(0).get("ins_id"));
			mapVo.put("detail_id", entityMap.get(0).get("detail_id"));
			assInspectionItemMapper.deleteAssInsItem(mapVo);

			assInspectionItemMapper.addBatchAssInsItem(entityMap);

			return "{\"msg\":\"保存成功.\",\"state\":\"true\"}";

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}
	}

	@Override
	public String deleteAssInsItem(List<Map<String, Object>> entityMap) throws DataAccessException {
		try {
			
			assInspectionItemMapper.deleteBatchAssInsItem(entityMap);

			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}
	}

	@Override
	public List<AssInspectionItem> queryByInsId(Map<String, Object> entityMap) throws DataAccessException {
		List<AssInspectionItem> list=assInspectionItemMapper.queryByInsId(entityMap);
		return list;
	}

	@Override
	public String deleteAssInsItemByAssInsDetail(AssAcceptDetail entityMap) throws DataAccessException {
		try {
			assInspectionItemMapper.deleteAssInsItemByAssInsDetail(entityMap);

			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}
	}


	@Override
	public List<String> queryInSpectionMainState(Map<String, Object> mapVo) {
		// TODO Auto-generated method stub
		return assInspectionMainMapper.queryInSpectionMainStatee(mapVo);
	}

	@Override
	public String queryDetails(Map<String, Object> entityMap) {
		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		if (sysPage.getTotal() == -1) {

			List<Map<String, Object>> list = (List<Map<String, Object>>) assInspectionMainMapper.queryDetails(entityMap);

			return ChdJson.toJson(list);

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

			List<Map<String, Object>> list = (List<Map<String, Object>>) assInspectionMainMapper.queryDetails(entityMap, rowBounds);

			PageInfo page = new PageInfo(list);

			return ChdJson.toJson(list, page.getTotal());

		}
	}


}
