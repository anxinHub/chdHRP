/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.ass.serviceImpl.allot.out;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.chd.base.SysPage;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.hrp.ass.dao.HrpAssSelectMapper;
import com.chd.hrp.ass.dao.allot.in.AssAllotInHouseMapper;
import com.chd.hrp.ass.dao.allot.out.AssAllotOutDetailHouseMapper;
import com.chd.hrp.ass.dao.allot.out.AssAllotOutHouseMapper;
import com.chd.hrp.ass.dao.allot.out.AssAllotOutHouseMapper;
import com.chd.hrp.ass.dao.allot.out.source.AssAllotOutSourceHouseMapper;
import com.chd.hrp.ass.dao.card.AssCardHouseMapper;
import com.chd.hrp.ass.dao.resource.AssResourceHouseMapper;
import com.chd.hrp.ass.entity.allot.out.AssAllotOutHouse;
import com.chd.hrp.ass.entity.allot.out.AssAllotOutHouse;
import com.chd.hrp.ass.entity.resource.AssResourceHouse;
import com.chd.hrp.ass.service.allot.out.AssAllotOutHouseService;
import com.chd.hrp.ass.service.base.AssBaseService;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Description:
 * 050901 资产无偿调拨出库单主表（房屋及建筑物）
 * @Table:
 * ASS_ALLOT_OUT_HOUSE
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

 
@Service("assAllotOutHouseService")
public class AssAllotOutHouseServiceImpl implements AssAllotOutHouseService {

	private static Logger logger = Logger.getLogger(AssAllotOutHouseServiceImpl.class);
	// 引入DAO操作
	@Resource(name = "assAllotOutHouseMapper")
	private final AssAllotOutHouseMapper assAllotOutHouseMapper = null;

	@Resource(name = "assAllotOutDetailHouseMapper")
	private final AssAllotOutDetailHouseMapper assAllotOutDetailHouseMapper = null;

	@Resource(name = "assAllotOutSourceHouseMapper")
	private final AssAllotOutSourceHouseMapper assAllotOutSourceHouseMapper = null;

	@Resource(name = "assResourceHouseMapper")
	private final AssResourceHouseMapper assResourceHouseMapper = null;

	@Resource(name = "assCardHouseMapper")
	private final AssCardHouseMapper assCardHouseMapper = null;

	@Resource(name = "assBaseService")
	private final AssBaseService assBaseService = null;

	@Resource(name = "hrpAssSelectMapper")
	private final HrpAssSelectMapper hrpAssSelectMapper = null;

	/**
	 * @Description 添加050901 资产无偿调拨出库单主表（无形资产）<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String add(Map<String, Object> entityMap) throws DataAccessException {

		// 获取对象050901 资产无偿调拨出库单主表（无形资产）
		AssAllotOutHouse assAllotOutHouse = queryByCode(entityMap);

		if (assAllotOutHouse != null) {

			return "{\"error\":\"数据重复,请重新添加.\"}";

		}

		try {

			int state = assAllotOutHouseMapper.add(entityMap);

			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 add\"}";

		}

	}

	/**
	 * @Description 批量添加050901 资产无偿调拨出库单主表（无形资产）<BR>
	 * @param entityList
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String addBatch(List<Map<String, Object>> entityList) throws DataAccessException {

		try {

			assAllotOutHouseMapper.addBatch(entityList);

			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addBatch\"}";

		}

	}

	/**
	 * @Description 更新050901 资产无偿调拨出库单主表（无形资产）<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String update(Map<String, Object> entityMap) throws DataAccessException {

		try {

			int state = assAllotOutHouseMapper.update(entityMap);

			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 update\"}";

		}

	}

	/**
	 * @Description 批量更新050901 资产无偿调拨出库单主表（无形资产）<BR>
	 * @param entityList
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String updateBatch(List<Map<String, Object>> entityList) throws DataAccessException {

		try {

			assAllotOutHouseMapper.updateBatch(entityList);

			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 updateBatch\"}";

		}

	}

	/**
	 * @Description 删除050901 资产无偿调拨出库单主表（无形资产）<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String delete(Map<String, Object> entityMap) throws DataAccessException {

		try {

			int state = assAllotOutHouseMapper.delete(entityMap);

			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 delete\"}";

		}

	}

	/**
	 * @Description 批量删除050901 资产无偿调拨出库单主表（无形资产）<BR>
	 * @param entityList
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String deleteBatch(List<Map<String, Object>> entityList) throws DataAccessException {

		try {
			assAllotOutSourceHouseMapper.deleteBatch(entityList);
			assAllotOutDetailHouseMapper.deleteBatch(entityList);
			assAllotOutHouseMapper.deleteBatch(entityList);

			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}
	}

	/**
	 * @Description 添加050901 资产无偿调拨出库单主表（无形资产）<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String addOrUpdate(Map<String, Object> entityMap) throws DataAccessException {
		WebApplicationContext ctx = ContextLoader.getCurrentWebApplicationContext();
		DataSourceTransactionManager transactionManager = (DataSourceTransactionManager) ctx
				.getBean("transactionManager");

		DefaultTransactionDefinition def = new DefaultTransactionDefinition();

		def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);

		TransactionStatus status = transactionManager.getTransaction(def); // 获得事务状态

		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> listSourceVo = new ArrayList<Map<String, Object>>();
		Map<String, Object> mapVo = new HashMap<String, Object>();
		mapVo.put("group_id", entityMap.get("group_id"));
		mapVo.put("hos_id", entityMap.get("hos_id"));
		mapVo.put("copy_code", entityMap.get("copy_code"));
		mapVo.put("allot_out_no", entityMap.get("allot_out_no"));

		Map<String, Object> vCreateDateMap = new HashMap<String, Object>();
		vCreateDateMap.put("group_id", entityMap.get("group_id"));
		vCreateDateMap.put("hos_id", entityMap.get("hos_id"));
		vCreateDateMap.put("copy_code", entityMap.get("copy_code"));
		vCreateDateMap.put("ass_nature", "01");
		vCreateDateMap.put("use_state", "1,2,3,4,5");
		vCreateDateMap.put("in_date", entityMap.get("create_date"));

		entityMap.put("state", "0");
		List<AssAllotOutHouse> list = (List<AssAllotOutHouse>) assAllotOutHouseMapper.queryExists(mapVo);
		try {
			boolean flag = true;
			boolean flag1 = true;
			if (list.size() > 0) {
				assAllotOutHouseMapper.update(entityMap);
			} else {
				if(entityMap.get("create_date") != null && !"".equals(entityMap.get("create_date"))){
					entityMap.put("year", entityMap.get("create_date").toString().substring(0,4));
					entityMap.put("month", entityMap.get("create_date").toString().substring(5,7));
				}
				entityMap.put("bill_table", "ASS_ALLOT_OUT_HOUSE");
				String allot_out_no = assBaseService.getBillNOSeqNo(entityMap);
				entityMap.put("allot_out_no", allot_out_no);
				assAllotOutHouseMapper.add(entityMap);
				assBaseService.updateAssBillNoMaxNo(entityMap);
			}

			List<Map> detail = ChdJson.fromJsonAsList(Map.class, entityMap.get("ParamVo").toString());
			Double price = 0.0;
			Double add_depre = 0.0;
			Double cur_money = 0.0;
			Double fore_money = 0.0;

			for (Map<String, Object> detailVo : detail) {
				if (detailVo.get("ass_card_no") == null || "".equals(detailVo.get("ass_card_no"))) {
					continue;
				}
				detailVo.put("group_id", entityMap.get("group_id"));
				detailVo.put("hos_id", entityMap.get("hos_id"));
				detailVo.put("copy_code", entityMap.get("copy_code"));
				detailVo.put("ass_card_no", detailVo.get("ass_card_no").toString());
				vCreateDateMap.put("ass_card_no", detailVo.get("ass_card_no"));

				Integer createDateExists = hrpAssSelectMapper.queryAssExistsHouseCard(vCreateDateMap);
				if (createDateExists == 0) {
					flag = false;
					break;
				}

				String create_date = entityMap.get("create_date").toString();

				detailVo.put("ass_year", create_date.substring(0, create_date.indexOf("-")));

				detailVo.put("ass_month",
						create_date.substring(create_date.indexOf("-") + 1, create_date.lastIndexOf("-")));

			detailVo.put("ass_naturs", "01");

				String results = collectAssAllotOutHouse(detailVo);

				JSONObject jsonObj = JSONObject.parseObject(results);

				if (jsonObj.containsKey("msg")) {
					transactionManager.rollback(status);
					return jsonObj.toJSONString();
				}

				JSONArray cardArray = JSONArray.parseArray(jsonObj.get("Card").toString());

				JSONObject cardObj = JSONObject.parseObject(cardArray.get(0).toString());

				detailVo.put("allot_out_no", entityMap.get("allot_out_no"));

				detailVo.put("price", detailVo.get("price").toString());

				price = price + Double.parseDouble(detailVo.get("price").toString());

				detailVo.put("now_depre", cardObj.get("NowDepre").toString());

				detailVo.put("now_manage_depre", cardObj.get("NowManageDepre").toString());

				detailVo.put("add_depre", cardObj.get("AddDepre").toString());

				add_depre = add_depre + Double.parseDouble(cardObj.get("AddDepre").toString());

				detailVo.put("add_manage_depre", cardObj.get("AddManageDepre").toString());

				detailVo.put("cur_money", cardObj.get("Cur").toString());

				cur_money = cur_money + Double.parseDouble(cardObj.get("Cur").toString());

				detailVo.put("fore_money", cardObj.get("Fore").toString());

				detailVo.put("add_depre_month", cardObj.get("AddAccMonth").toString());

				fore_money = fore_money + Double.parseDouble(cardObj.get("Fore").toString());

				if (detailVo.get("note") != null && !detailVo.get("note").equals("")) {
					detailVo.put("note", detailVo.get("note"));
				} else {
					detailVo.put("note", "");
				}

				JSONArray sourceArray = JSONArray.parseArray(jsonObj.get("Source").toString());

				List<AssResourceHouse> resourceList = assResourceHouseMapper.queryByAssCardNo(detailVo);
				for (AssResourceHouse resSource : resourceList) {
					Map<String, Object> mapSource = new HashMap<String, Object>();
					mapSource.put("group_id", resSource.getGroup_id());
					mapSource.put("hos_id", resSource.getHos_id());
					mapSource.put("copy_code", resSource.getCopy_code());
					mapSource.put("allot_out_no", entityMap.get("allot_out_no"));
					mapSource.put("ass_card_no", resSource.getAss_card_no());
					mapSource.put("source_id", resSource.getSource_id());
					mapSource.put("price", resSource.getPrice());
					for (int i = 0; i < sourceArray.size(); i++) {
						JSONObject job = sourceArray.getJSONObject(i);
						if (resSource.getSource_id() == Long.parseLong(job.get("ID").toString())) {
							mapSource.put("now_depre", job.get("NowDepre").toString());
							mapSource.put("add_depre", job.get("AddDepre").toString());
							mapSource.put("cur_money", job.get("Cur").toString());
							mapSource.put("fore_money", job.get("Fore").toString());
							mapSource.put("now_manage_depre", job.get("NowManageDepre").toString());
							mapSource.put("add_manage_depre", job.get("AddManageDepre").toString());
							mapSource.put("add_depre_month", job.get("AddAccMonth").toString());
						}
					}

					if (detailVo.get("note") != null && !detailVo.get("note").equals("")) {
						mapSource.put("note", detailVo.get("note"));
					} else {
						mapSource.put("note", "");
					}
					listSourceVo.add(mapSource);
				}

				listVo.add(detailVo);
			}

			if (!flag) {
				transactionManager.rollback(status);
				return "{\"warn\":\"存在尚未入账的卡片不能进行退货.\"}";
			}
			if (!flag1) {
				transactionManager.rollback(status);
				return "{\"warn\":\"存在非本仓库的卡片,不能退货.\"}";
			}

			assAllotOutSourceHouseMapper.delete(entityMap);
			assAllotOutDetailHouseMapper.delete(entityMap);
			assAllotOutDetailHouseMapper.addBatch(listVo);
			if (listSourceVo.size() > 0) {
				assAllotOutSourceHouseMapper.addBatch(listSourceVo);
			}
			entityMap.put("price", price + "");
			entityMap.put("add_depre", add_depre + "");
			entityMap.put("cur_money", cur_money + "");
			entityMap.put("fore_money", fore_money + "");
			assAllotOutHouseMapper.updateAllotOutMoney(entityMap);
			transactionManager.commit(status);
			return "{\"msg\":\"保存成功.\",\"state\":\"true\",\"allot_out_no\":\""
					+ entityMap.get("allot_out_no").toString() + "\",\"price\":\"" + price + "\",\"add_depre\":\""
					+ add_depre + "\",\"cur_money\":\"" + cur_money + "\",\"fore_money\":\"" + fore_money + "\"}";
		} catch (Exception e) {
			transactionManager.rollback(status);
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());

		}

	}

	/**
	 * @Description 查询结果集050901 资产无偿调拨出库单主表（无形资产）<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String query(Map<String, Object> entityMap) throws DataAccessException {

		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		if (sysPage.getTotal() == -1) {

			List<AssAllotOutHouse> list = (List<AssAllotOutHouse>) assAllotOutHouseMapper.query(entityMap);

			return ChdJson.toJson(list);

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

			List<AssAllotOutHouse> list = (List<AssAllotOutHouse>) assAllotOutHouseMapper.query(entityMap,
					rowBounds);

			PageInfo page = new PageInfo(list);

			return ChdJson.toJson(list, page.getTotal());

		}

	}

	/**
	 * @Description 获取对象050901 资产无偿调拨出库单主表（无形资产）<BR>
	 * @param entityMap<BR>
	 *            参数为主键
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public <T> T queryByCode(Map<String, Object> entityMap) throws DataAccessException {
		return assAllotOutHouseMapper.queryByCode(entityMap);
	}

	/**
	 * @Description 获取050901 资产无偿调拨出库单主表（无形资产）<BR>
	 * @param entityMap<BR>
	 *            参数为要检索的字段
	 * @return AssAllotOutHouse
	 * @throws DataAccessException
	 */
	@Override
	public <T> T queryByUniqueness(Map<String, Object> entityMap) throws DataAccessException {
		return assAllotOutHouseMapper.queryByUniqueness(entityMap);
	}

	/**
	 * @Description 获取050901 资产无偿调拨出库单主表（无形资产）<BR>
	 * @param entityMap<BR>
	 *            参数为要检索的字段
	 * @return List<AssAllotOutHouse>
	 * @throws DataAccessException
	 */
	@Override
	public List<?> queryExists(Map<String, Object> entityMap) throws DataAccessException {
		return assAllotOutHouseMapper.queryExists(entityMap);
	}

	@Override
	public String updateAllotOutConfirm(List<Map<String, Object>> entityMap, List<Map<String, Object>> cardEntityMap)
			throws DataAccessException {
		try {
			assAllotOutHouseMapper.updateConfirm(entityMap);
			assCardHouseMapper.updateBackConfirm(cardEntityMap);

			return "{\"msg\":\"确认成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());
		}
	}

	public String collectAssAllotOutHouse(Map<String, Object> entityMap) {
		WebApplicationContext ctx = ContextLoader.getCurrentWebApplicationContext();

		DataSourceTransactionManager transactionManager = (DataSourceTransactionManager) ctx
				.getBean("transactionManager");

		DefaultTransactionDefinition def = new DefaultTransactionDefinition();

		def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);

		TransactionStatus status = transactionManager.getTransaction(def); // 获得事务状态

		assAllotOutHouseMapper.collectAssAllotOutHouse(entityMap);

		String ass_AppCode = entityMap.get("ass_AppCode").toString();
		String ass_ErrTxt = "";
		if (entityMap.get("ass_ErrTxt") != null && !entityMap.get("ass_ErrTxt").equals("")) {
			ass_ErrTxt = entityMap.get("ass_ErrTxt").toString();
		}

		if ("0".equals(ass_AppCode)) {
			// 计算成功！提交事务
			transactionManager.commit(status);
			return entityMap.get("ass_json_str").toString();

		} else if ("-1".equals(ass_AppCode) || "100".equals(ass_AppCode)) {
			// 计算失败！回滚事务
			transactionManager.rollback(status);

			return "{\"msg\":\"" + ass_ErrTxt + "\",\"state\":\"" + ass_AppCode + "\"}";
		}

		if (!"".equals(entityMap.get("ass_ErrTxt").toString()) && null != entityMap.get("ass_ErrTxt").toString()) {

			ass_ErrTxt = entityMap.get("ass_ErrTxt").toString();

		}
		return "{\"msg\":\"" + ass_ErrTxt + "\",\"state\":\"" + ass_AppCode + "\"}";

	}

	@Override
	public String queryByAllotInImport(Map<String, Object> entityMap) throws DataAccessException {
		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		if (sysPage.getTotal() == -1) {

			List<AssAllotOutHouse> list = (List<AssAllotOutHouse>) assAllotOutHouseMapper
					.queryByAllotInImport(entityMap);

			return ChdJson.toJson(list);

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

			List<AssAllotOutHouse> list = (List<AssAllotOutHouse>) assAllotOutHouseMapper
					.queryByAllotInImport(entityMap, rowBounds);

			PageInfo page = new PageInfo(list);

			return ChdJson.toJson(list, page.getTotal());

		}
	}

	@Override
	public String queryByAllotInImportView(Map<String, Object> entityMap) throws DataAccessException {
		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		if (sysPage.getTotal() == -1) {

			List<AssAllotOutHouse> list = (List<AssAllotOutHouse>) assAllotOutHouseMapper
					.queryByAllotInImportView(entityMap);

			return ChdJson.toJson(list);

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

			List<AssAllotOutHouse> list = (List<AssAllotOutHouse>) assAllotOutHouseMapper
					.queryByAllotInImportView(entityMap, rowBounds);

			PageInfo page = new PageInfo(list);

			return ChdJson.toJson(list, page.getTotal());

		}
	}
	@Override
	public Map<String,Object> printAssAllotOutHouseData(Map<String, Object> map)throws DataAccessException {
		
		try{
			Map<String,Object> reMap=new HashMap<String,Object>();
			WebApplicationContext context = ContextLoader.getCurrentWebApplicationContext();
			AssAllotOutHouseMapper assAllotOutHouseMapper = (AssAllotOutHouseMapper)context.getBean("assAllotOutHouseMapper");
			
			//查询凭证主表
			List<Map<String,Object>> mainList=assAllotOutHouseMapper.queryAssAllotOutHouseByAssInNo(map);
					
			//查询凭证明细表
			List<Map<String,Object>> detailList=assAllotOutHouseMapper.queryAssAllotOutHouseDetailByAssInNo(map);
			
			reMap.put("main", mainList);
			reMap.put("detail", detailList);
			
			return reMap;
		}catch(Exception e){
			logger.error(e.getMessage(),e);
			throw new SysException(e.getMessage());
		}
		
	}
	

	/**
	 * 入库单状态查询  （打印时校验数据专用）
	 */
	@Override
	public List<String> queryAssAllotInHouseStates(Map<String, Object> mapVo) throws DataAccessException {
		
		return assAllotOutHouseMapper.queryAssAllotInHouseStates(mapVo);
	}

	@Override
	public String queryDetails(Map<String, Object> entityMap) {
		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		if (sysPage.getTotal() == -1) {

			List<Map<String, Object>> list = (List<Map<String, Object>>) assAllotOutHouseMapper.queryDetails(entityMap);

			return ChdJson.toJson(list);

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

			List<Map<String, Object>> list = (List<Map<String, Object>>) assAllotOutHouseMapper.queryDetails(entityMap, rowBounds);

			PageInfo page = new PageInfo(list);

			return ChdJson.toJson(list, page.getTotal());

		}
	}
	
	
}
