﻿/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */

package com.chd.hrp.ass.serviceImpl.back;

import java.util.ArrayList;
import java.util.Date;
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
import com.chd.base.SessionManager;
import com.chd.base.SysPage;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.base.util.DateUtil;
import com.chd.hrp.ass.dao.HrpAssSelectMapper;
import com.chd.hrp.ass.dao.back.AssBackDetailSpecialMapper;
import com.chd.hrp.ass.dao.back.AssBackInSpecialMapMapper;
import com.chd.hrp.ass.dao.back.AssBackSpecialMapper;
import com.chd.hrp.ass.dao.back.source.AssBackSourceSpecialMapper;
import com.chd.hrp.ass.dao.card.AssCardSpecialMapper;
import com.chd.hrp.ass.dao.in.AssInMainSpecialMapper;
import com.chd.hrp.ass.dao.pay.AssPayStageSpecialMapper;
import com.chd.hrp.ass.dao.resource.AssResourceSpecialMapper;
import com.chd.hrp.ass.entity.back.AssBackSpecial;
import com.chd.hrp.ass.entity.in.AssInMainSpecial;
import com.chd.hrp.ass.entity.resource.AssResourceSpecial;
import com.chd.hrp.ass.service.back.AssBackSpecialService;
import com.chd.hrp.ass.service.base.AssBaseService;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Description: 050701 资产退货单主表(专用设备)
 * @Table: ASS_BACK_SPECIAL
 * @Author: bell
 * @email: bell@e-tonggroup.com
 * @Version: 1.0
 */
 
@Service("assBackSpecialService")
public class AssBackSpecialServiceImpl implements AssBackSpecialService {

	private static Logger logger = Logger.getLogger(AssBackSpecialServiceImpl.class);
	// 引入DAO操作
	@Resource(name = "assBackSpecialMapper")
	private final AssBackSpecialMapper assBackSpecialMapper = null;

	@Resource(name = "assBackDetailSpecialMapper")
	private final AssBackDetailSpecialMapper assBackDetailSpecialMapper = null;

	@Resource(name = "assBackSourceSpecialMapper")
	private final AssBackSourceSpecialMapper assBackSourceSpecialMapper = null;

	@Resource(name = "assResourceSpecialMapper")
	private final AssResourceSpecialMapper assResourceSpecialMapper = null;

	@Resource(name = "assCardSpecialMapper")
	private final AssCardSpecialMapper assCardSpecialMapper = null;

	@Resource(name = "assBaseService")
	private final AssBaseService assBaseService = null;
	
	@Resource(name = "hrpAssSelectMapper")
	private final HrpAssSelectMapper hrpAssSelectMapper = null;

	@Resource(name = "assBackInSpecialMapMapper")
	private final AssBackInSpecialMapMapper assBackInSpecialMapMapper = null;
	
	@Resource(name = "assInMainSpecialMapper")
	private final AssInMainSpecialMapper assInMainSpecialMapper = null;
	
	@Resource(name = "assPayStageSpecialMapper")
	private final AssPayStageSpecialMapper assPayStageSpecialMapper = null;

	/**
	 * @Description 添加050701 资产退货单主表(专用设备)<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String add(Map<String, Object> entityMap) throws DataAccessException {

		// 获取对象050701 资产退货单主表(专用设备)
		AssBackSpecial assBackSpecial = queryByCode(entityMap);

		if (assBackSpecial != null) {

			return "{\"error\":\"数据重复,请重新添加.\"}";

		}

		try {

			int state = assBackSpecialMapper.add(entityMap);

			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 add\"}";

		}

	}

	/**
	 * @Description 批量添加050701 资产退货单主表(专用设备)<BR>
	 * @param entityList
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String addBatch(List<Map<String, Object>> entityList) throws DataAccessException {

		try {

			assBackSpecialMapper.addBatch(entityList);

			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addBatch\"}";

		}

	}

	/**
	 * @Description 更新050701 资产退货单主表(专用设备)<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String update(Map<String, Object> entityMap) throws DataAccessException {

		try {

			int state = assBackSpecialMapper.update(entityMap);

			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 update\"}";

		}

	}

	/**
	 * @Description 批量更新050701 资产退货单主表(专用设备)<BR>
	 * @param entityList
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String updateBatch(List<Map<String, Object>> entityList) throws DataAccessException {

		try {

			assBackSpecialMapper.updateBatch(entityList);

			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 updateBatch\"}";

		}

	}

	/**
	 * @Description 删除050701 资产退货单主表(专用设备)<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String delete(Map<String, Object> entityMap) throws DataAccessException {

		try {

			int state = assBackSpecialMapper.delete(entityMap);

			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 delete\"}";

		}

	}

	/**
	 * @Description 批量删除050701 资产退货单主表(专用设备)<BR>
	 * @param entityList
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String deleteBatch(List<Map<String, Object>> entityList) throws DataAccessException {

		try {
			assBackSourceSpecialMapper.deleteBatch(entityList);
			assBackDetailSpecialMapper.deleteBatch(entityList);
			assBackSpecialMapper.deleteBatch(entityList);
			assBackInSpecialMapMapper.deleteBatch(entityList);
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());

		}
	}

	/**
	 * @Description 添加050701 资产退货单主表(专用设备)<BR>
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
		mapVo.put("ass_back_no", entityMap.get("ass_back_no"));
		
		Map<String, Object> vCreateDateMap = new HashMap<String, Object>();
		vCreateDateMap.put("group_id", entityMap.get("group_id"));
		vCreateDateMap.put("hos_id", entityMap.get("hos_id"));
		vCreateDateMap.put("copy_code", entityMap.get("copy_code"));
		vCreateDateMap.put("ass_nature", "02");
		vCreateDateMap.put("bus_type_code", "01");
		vCreateDateMap.put("use_state", "1,2,3,4,5");
		vCreateDateMap.put("in_date", entityMap.get("create_date"));
		
		Map<String, Object> vStoreMap = new HashMap<String, Object>();
		vStoreMap.put("group_id", entityMap.get("group_id"));
		vStoreMap.put("hos_id", entityMap.get("hos_id"));
		vStoreMap.put("copy_code", entityMap.get("copy_code"));
		vStoreMap.put("ass_nature", "02");
		vStoreMap.put("bus_type_code", "01");
		vStoreMap.put("use_state", "1,2,3,4,5");
		vStoreMap.put("store_id", entityMap.get("store_id"));
		vStoreMap.put("store_no", entityMap.get("store_no"));
		
		Map<String, Object> vVenMap = new HashMap<String, Object>();
		vVenMap.put("group_id", entityMap.get("group_id"));
		vVenMap.put("hos_id", entityMap.get("hos_id"));
		vVenMap.put("copy_code", entityMap.get("copy_code"));
		vVenMap.put("ass_nature", "02");
		vVenMap.put("bus_type_code", "01");
		vVenMap.put("use_state", "1,2,3,4,5");
		vVenMap.put("ven_id", entityMap.get("ven_id"));
		vVenMap.put("ven_no", entityMap.get("ven_no"));
		
		entityMap.put("state", "0");
		List<AssBackSpecial> list = (List<AssBackSpecial>) assBackSpecialMapper.queryExists(mapVo);
		try {
			boolean flag = true;
			boolean flag1 = true;
			boolean flag2 = true;
			if (list.size() > 0) {
				assBackSpecialMapper.update(entityMap);
			} else {
				if(entityMap.get("create_date") != null && !"".equals(entityMap.get("create_date"))){
					entityMap.put("year", entityMap.get("create_date").toString().substring(0,4));
					entityMap.put("month", entityMap.get("create_date").toString().substring(5,7));
				}
				entityMap.put("bill_table", "ASS_BACK_SPECIAL");
				String ass_back_no = assBaseService.getBillNOSeqNo(entityMap);
				entityMap.put("ass_back_no", ass_back_no);
				assBackSpecialMapper.add(entityMap);
				assBaseService.updateAssBillNoMaxNo(entityMap);
			}
			
			List<Map> detail = ChdJson.fromJsonAsList(Map.class, entityMap.get("ParamVo").toString());
			Double back_money = 0.0;
			for (Map<String, Object> detailVo : detail) {
				if (detailVo.get("ass_card_no") == null || "".equals(detailVo.get("ass_card_no"))) {
					continue;
				}
				detailVo.put("group_id", entityMap.get("group_id"));
				detailVo.put("hos_id", entityMap.get("hos_id"));
				detailVo.put("copy_code", entityMap.get("copy_code"));
				detailVo.put("ass_card_no", detailVo.get("ass_card_no").toString());
				vCreateDateMap.put("ass_card_no", detailVo.get("ass_card_no"));
				vStoreMap.put("ass_card_no", detailVo.get("ass_card_no"));
				vVenMap.put("ass_card_no", detailVo.get("ass_card_no"));
				
				Integer createDateExists = hrpAssSelectMapper.queryAssExistsSpecialCard(vCreateDateMap);
				if(createDateExists == 0){
					flag = false;
					break;
				}
				
				Integer storeExists = hrpAssSelectMapper.queryAssExistsSpecialCard(vStoreMap);
				if(storeExists == 0){
					flag1 = false;
					break;
				}
				
				Integer venExists = hrpAssSelectMapper.queryAssExistsSpecialCard(vVenMap);
				if(venExists == 0){
					flag2 = false;
					break;
				}
				
				
				String create_date = entityMap.get("create_date").toString();

				detailVo.put("ass_year", create_date.substring(0, create_date.indexOf("-")));

				detailVo.put("ass_month",
						create_date.substring(create_date.indexOf("-") + 1, create_date.lastIndexOf("-")));

				detailVo.put("ass_naturs", "02");

				String results = collectAssBackSpecial(detailVo);

				JSONObject jsonObj = JSONObject.parseObject(results);

				if (jsonObj.containsKey("msg")) {
					transactionManager.rollback(status);
					return jsonObj.toJSONString();
				}

				JSONArray cardArray = JSONArray.parseArray(jsonObj.get("Card").toString());

				JSONObject cardObj = JSONObject.parseObject(cardArray.get(0).toString());

				detailVo.put("ass_back_no", entityMap.get("ass_back_no"));

				detailVo.put("price", detailVo.get("price").toString());

				back_money = back_money + Double.parseDouble(detailVo.get("price").toString());

				detailVo.put("now_depre", cardObj.get("NowDepre").toString());

				detailVo.put("now_manage_depre", cardObj.get("NowManageDepre").toString());

				detailVo.put("add_depre",cardObj.get("AddDepre").toString());

				detailVo.put("add_manage_depre", cardObj.get("AddManageDepre").toString());

				detailVo.put("cur_money", cardObj.get("Cur").toString());

				detailVo.put("fore_money", cardObj.get("Fore").toString());
				
				detailVo.put("add_depre_month", cardObj.get("AddAccMonth").toString());

				if (detailVo.get("note") != null && !detailVo.get("note").equals("")) {
					detailVo.put("note", detailVo.get("note"));
				} else {
					detailVo.put("note", "");
				}

				JSONArray sourceArray = JSONArray.parseArray(jsonObj.get("Source").toString());

				List<AssResourceSpecial> resourceList = assResourceSpecialMapper.queryByAssCardNo(detailVo);
				for (AssResourceSpecial resSource : resourceList) {
					Map<String, Object> mapSource = new HashMap<String, Object>();
					mapSource.put("group_id", resSource.getGroup_id());
					mapSource.put("hos_id", resSource.getHos_id());
					mapSource.put("copy_code", resSource.getCopy_code());
					mapSource.put("ass_back_no", entityMap.get("ass_back_no"));
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
			
			if(!flag){
				transactionManager.rollback(status);
				return "{\"warn\":\"存在尚未入账的卡片不能进行退货.\"}";
			}
			if(!flag1){
				transactionManager.rollback(status);
				return "{\"warn\":\"存在非本仓库的卡片,不能退货.\"}";
			}
			
			if(!flag2){
				transactionManager.rollback(status);
				return "{\"warn\":\"存在非本供应商的卡片,不能退货.\"}";
			}
			
			
			assBackSourceSpecialMapper.delete(entityMap);
			assBackDetailSpecialMapper.delete(entityMap);
			assBackDetailSpecialMapper.addBatch(listVo);
			if (listSourceVo.size() > 0) {
				assBackSourceSpecialMapper.addBatch(listSourceVo);
			}
			entityMap.put("back_money", back_money);
			assBackSpecialMapper.updateBackMoney(entityMap);
			transactionManager.commit(status);
			return "{\"msg\":\"保存成功.\",\"state\":\"true\",\"ass_back_no\":\"" + entityMap.get("ass_back_no").toString()
					+ "\",\"back_money\":\"" + back_money + "\"}";
		} catch (Exception e) {
			transactionManager.rollback(status);
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());

		}

	}

	public String collectAssBackSpecial(Map<String, Object> entityMap) {
		WebApplicationContext ctx = ContextLoader.getCurrentWebApplicationContext();

		DataSourceTransactionManager transactionManager = (DataSourceTransactionManager) ctx
				.getBean("transactionManager");

		DefaultTransactionDefinition def = new DefaultTransactionDefinition();

		def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);

		TransactionStatus status = transactionManager.getTransaction(def); // 获得事务状态

		assBackSpecialMapper.collectAssBackSpecial(entityMap);

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

	/**
	 * @Description 查询结果集050701 资产退货单主表(专用设备)<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String query(Map<String, Object> entityMap) throws DataAccessException {

		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		if (sysPage.getTotal() == -1) {

			List<AssBackSpecial> list = (List<AssBackSpecial>) assBackSpecialMapper.query(entityMap);

			return ChdJson.toJson(list);

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

			List<AssBackSpecial> list = (List<AssBackSpecial>) assBackSpecialMapper.query(entityMap, rowBounds);

			PageInfo page = new PageInfo(list);

			return ChdJson.toJson(list, page.getTotal());

		}

	}

	/**
	 * @Description 获取对象050701 资产退货单主表(专用设备)<BR>
	 * @param entityMap<BR>
	 *            参数为主键
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public <T> T queryByCode(Map<String, Object> entityMap) throws DataAccessException {
		return assBackSpecialMapper.queryByCode(entityMap);
	}

	/**
	 * @Description 获取050701 资产退货单主表(专用设备)<BR>
	 * @param entityMap<BR>
	 *            参数为要检索的字段
	 * @return AssBackSpecial
	 * @throws DataAccessException
	 */
	@Override
	public <T> T queryByUniqueness(Map<String, Object> entityMap) throws DataAccessException {
		return assBackSpecialMapper.queryByUniqueness(entityMap);
	}

	/**
	 * @Description 获取050701 资产退货单主表(专用设备)<BR>
	 * @param entityMap<BR>
	 *            参数为要检索的字段
	 * @return List<AssBackSpecial>
	 * @throws DataAccessException
	 */
	@Override
	public List<?> queryExists(Map<String, Object> entityMap) throws DataAccessException {
		return assBackSpecialMapper.queryExists(entityMap);
	}

	@Override
	public String updateBackConfirm(List<Map<String, Object>> entityMap, List<Map<String, Object>> cardEntityMap)
			throws DataAccessException {
		try {
			assBackSpecialMapper.updateBatchConfirm(entityMap);
			assCardSpecialMapper.updateBackConfirm(cardEntityMap);
			
			assPayStageSpecialMapper.updateBatchBackBill(cardEntityMap);
			assPayStageSpecialMapper.updateBatchBackPay(cardEntityMap);
			
			assResourceSpecialMapper.updateBatchByBack(cardEntityMap);

			return "{\"msg\":\"确认成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());
		}
	}
	
	/**
	 * 专用设备 退货 新版打印  调用的方法
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	@Override
	public Map<String, Object> queryAssBackSpecialByPrintTemlatePrint(Map<String, Object> entityMap)throws DataAccessException {
		
		try{
			
			Map<String,Object> reMap=new HashMap<String,Object>();
			WebApplicationContext context = ContextLoader.getCurrentWebApplicationContext();
			AssBackSpecialMapper assBackSpecialMapper = (AssBackSpecialMapper)context.getBean("assBackSpecialMapper");
			 
			//主页面 批量打印查询
			if("1".equals(String.valueOf(entityMap.get("p_num")))){
				
				//查询 专用设备 退货主表
				List<Map<String,Object>> map= assBackSpecialMapper.queryAssBackSpecialPrintTemlateByMainBatch(entityMap);
				//查询 专用设备  退货明细表
				List<Map<String,Object>> list= assBackSpecialMapper.queryAssBackSpecialPrintTemlateByDetail(entityMap);
				
				reMap.put("main", map);
				
				reMap.put("detail", list); 
				
				return reMap;
				
			}else{ //修改页面 打印查询
				//
				Map<String,Object> map= assBackSpecialMapper.queryAssBackSpecialPrintTemlateByMain(entityMap);
				//查询 专用设备  退货明细表
				List<Map<String,Object>> list= assBackSpecialMapper.queryAssBackSpecialPrintTemlateByDetail(entityMap);
				
			
				reMap.put("main", map);
				
				reMap.put("detail", list);
				
				return reMap;
				
			}
			
		}catch(Exception e){
			logger.error(e.getMessage(),e);
			throw new SysException(e.getMessage());
		}
		
		
	}
	
	/**
	 * 专用设备 退货单状态查询  （打印时校验数据专用）
	 */
	@Override
	public List<String> queryAssBackSpecialState(Map<String, Object> mapVo) throws DataAccessException {
		
		return assBackSpecialMapper.queryAssBackSpecialState(mapVo);
	}

	@Override
	public String queryDetails(Map<String, Object> entityMap)
			throws DataAccessException {
		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		if (sysPage.getTotal() == -1) {

			List<Map<String, Object>> list = (List<Map<String, Object>>) assBackSpecialMapper.queryDetails(entityMap);

			return ChdJson.toJson(list);

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

			List<Map<String, Object>> list = (List<Map<String, Object>>) assBackSpecialMapper.queryDetails(entityMap, rowBounds);

			PageInfo page = new PageInfo(list);

			return ChdJson.toJson(list, page.getTotal());

		}
	}

	@Override
	public String addAssBack(Map<String, Object> entityMap) {
		
		 entityMap.put("bill_table", "ASS_BACK_SPECIAL");
		try{
			String ass_back_no = assBaseService.getBillNOSeqNo(entityMap);
			entityMap.put("ass_back_no", ass_back_no);
			int state = assBackSpecialMapper.add(entityMap);
			if (state > 0) {
				assBaseService.updateAssBillNoMaxNo(entityMap);
			}
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\",\"ass_back_no\":\"" + ass_back_no
					+ "\"}";
			
		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}
		
		
	}

	@Override
	public String assImportBackIn(Map<String, Object> entityMap) throws DataAccessException{
		WebApplicationContext ctx = ContextLoader.getCurrentWebApplicationContext();
		DataSourceTransactionManager transactionManager = (DataSourceTransactionManager) ctx
				.getBean("transactionManager");

		DefaultTransactionDefinition def = new DefaultTransactionDefinition();

		def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);

		TransactionStatus status = transactionManager.getTransaction(def); // 获得事务状态
		
		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> mapListVo = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> listSourceVo = new ArrayList<Map<String, Object>>();
		try{
			List<AssInMainSpecial> assPayMain = assInMainSpecialMapper.queryByInitBack(entityMap);
			
			String cr_date = DateUtil.dateFormat(entityMap.get("create_date"), "yyyy-MM-dd");
			entityMap.put("year", cr_date.substring(0,4));
			entityMap.put("month", cr_date.substring(5,7));
			entityMap.put("store_code", entityMap.get("store_code").toString());
			entityMap.put("bill_table", "ASS_BACK_SPECIAL");
			String ass_back_no = assBaseService.getBillNOSeqNo(entityMap);
			
			// 主表保存
			entityMap.put("ass_back_no", ass_back_no);
			entityMap.put("group_id", entityMap.get("group_id"));
			entityMap.put("hos_id", entityMap.get("hos_id"));
			entityMap.put("copy_code", entityMap.get("copy_code"));
			//entityMap.put("ass_in_no", entityMap.get("ass_in_nos"));
			entityMap.put("store_id", entityMap.get("store_id"));
			entityMap.put("store_no", entityMap.get("store_no"));
			entityMap.put("ven_id", entityMap.get("ven_id"));
			entityMap.put("ven_no", entityMap.get("ven_no"));
			entityMap.put("back_money", "0");
			entityMap.put("create_emp", SessionManager.getUserId());
			entityMap.put("create_date", DateUtil.dateToString(new Date(),"yyyy-MM-dd"));
			entityMap.put("back_date", null);
			entityMap.put("confirm_emp", null);
			entityMap.put("note", "引入采购入库");
			entityMap.put("state", "0");
			
			
			//明细表保存
			Double back_money = 0.0;
			for (AssInMainSpecial assPayDetailSpecial : assPayMain) {
				Map<String, Object> qcardMap = new HashMap<String, Object>();
				qcardMap.put("group_id", assPayDetailSpecial.getGroup_id());
				qcardMap.put("hos_id", assPayDetailSpecial.getHos_id());
				qcardMap.put("copy_code", assPayDetailSpecial.getCopy_code());
				qcardMap.put("ass_in_no", assPayDetailSpecial.getAss_in_no());
				qcardMap.put("ass_back_no", ass_back_no);
				List<Map<String, Object>> cardList = assCardSpecialMapper.queryByAssInNo(qcardMap);
				Map<String, Object> map= null;
				
				for(Map<String, Object> card : cardList){
					
					if(card.get("is_dept").toString().equals("1")){
						return "{\"msg\":\"引入失败,单据中存在已经出库的数据.\",\"state\":\"false\"}";
					}else if(card.get("use_state").toString().equals("0")){
						continue;
					}else if(card.get("use_state").toString().equals("6")){
						return "{\"msg\":\"引入失败,单据中已有退库数据.\",\"state\":\"false\"}";
					}else if(card.get("use_state").toString().equals("7")){
						return "{\"msg\":\"引入失败,单据中已有退库数据.\",\"state\":\"false\"}";
					} else if(card.get("use_state").toString().equals("8")){
						return "{\"msg\":\"引入失败,单据中已有退库数据.\",\"state\":\"false\"}";
					}
					String create_date = DateUtil.dateToString(new Date(),"yyyy-MM-dd").toString();
					 map = new HashMap<String, Object>();
					map.put("group_id", card.get("group_id"));
					map.put("hos_id", card.get("hos_id"));
					map.put("copy_code", card.get("copy_code"));
					map.put("ass_back_no", ass_back_no);
					map.put("ass_card_no", card.get("ass_card_no"));
					map.put("ass_year", create_date.substring(0, create_date.indexOf("-")));

					map.put("ass_month",
							create_date.substring(create_date.indexOf("-") + 1, create_date.lastIndexOf("-")));

					map.put("ass_naturs", "02");

					String results = collectAssBackSpecial(map);

					JSONObject jsonObj = JSONObject.parseObject(results);

					if (jsonObj.containsKey("msg")) {
						transactionManager.rollback(status);
						return jsonObj.toJSONString();
					}

					JSONArray cardArray = JSONArray.parseArray(jsonObj.get("Card").toString());

					JSONObject cardObj = JSONObject.parseObject(cardArray.get(0).toString());

					map.put("ass_back_no", entityMap.get("ass_back_no").toString());

					map.put("price", card.get("price").toString());

					back_money = back_money + Double.parseDouble(map.get("price").toString());

					map.put("now_depre", cardObj.get("NowDepre").toString());

					map.put("now_manage_depre", cardObj.get("NowManageDepre").toString());

					map.put("add_depre",cardObj.get("AddDepre").toString());

					map.put("add_manage_depre", cardObj.get("AddManageDepre").toString());

					map.put("cur_money", cardObj.get("Cur").toString());

					map.put("fore_money", cardObj.get("Fore").toString());
					
					map.put("add_depre_month", cardObj.get("AddAccMonth").toString());

					if (map.get("note") != null && !map.get("note").equals("")) {
						map.put("note", map.get("note"));
					} else {
						map.put("note", "");
					}
					JSONArray sourceArray = JSONArray.parseArray(jsonObj.get("Source").toString());
					List<AssResourceSpecial> resourceList = assResourceSpecialMapper.queryByAssCardNo(map);
					for (AssResourceSpecial resSource : resourceList) {
						Map<String, Object> mapSource = new HashMap<String, Object>();
						mapSource.put("group_id", resSource.getGroup_id());
						mapSource.put("hos_id", resSource.getHos_id());
						mapSource.put("copy_code", resSource.getCopy_code());
						mapSource.put("ass_back_no", entityMap.get("ass_back_no"));
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

						if (map.get("note") != null && !map.get("note").equals("")) {
							mapSource.put("note", map.get("note"));
						} else {
							mapSource.put("note", "");
						}
						listSourceVo.add(mapSource);
					}
					
					listVo.add(map);
				}
				
				mapListVo.add(qcardMap);
			}
			
			if(listVo.size() == 0){
				return "{\"msg\":\"引入失败,没有可生成的单据.\",\"state\":\"false\"}";
			}
			
			
			  assBackSpecialMapper.add(entityMap);
			  assBaseService.updateAssBillNoMaxNo(entityMap);
			 assBackDetailSpecialMapper.addBatch(listVo);
			 assBackInSpecialMapMapper.addBatch(mapListVo);
			 if (listSourceVo.size() > 0) {
					assBackSourceSpecialMapper.addBatch(listSourceVo);
				}
				entityMap.put("back_money", back_money);
				assBackSpecialMapper.updateBackMoney(entityMap);
				transactionManager.commit(status);
			 return "{\"msg\":\"引入成功.\",\"state\":\"true\",\"update_para\":\"" + entityMap.get("group_id") + "|"
				+ entityMap.get("hos_id") + "|" + entityMap.get("copy_code") + "|" + ass_back_no + "|1\"}";
			
		} catch (Exception e){
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}
	}

	@Override
	public String updateAssBackMainBillNo(Map<String, Object> entityMap) throws DataAccessException {
		try {
			
			assBackSpecialMapper.updateAssBackMainBillNo(entityMap);

			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}
	}

}
