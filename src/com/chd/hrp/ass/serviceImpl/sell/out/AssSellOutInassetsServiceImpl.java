/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.ass.serviceImpl.sell.out;

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
import com.chd.hrp.ass.dao.card.AssCardInassetsMapper;
import com.chd.hrp.ass.dao.resource.AssResourceInassetsMapper;
import com.chd.hrp.ass.dao.sell.out.AssSellOutDetailInassetsMapper;
import com.chd.hrp.ass.dao.sell.out.AssSellOutInassetsMapper;
import com.chd.hrp.ass.dao.sell.out.AssSellOutSpecialMapper;
import com.chd.hrp.ass.dao.sell.out.source.AssSellOutSourceInassetsMapper;
import com.chd.hrp.ass.entity.resource.AssResourceInassets;
import com.chd.hrp.ass.entity.sell.out.AssSellOutInassets;
import com.chd.hrp.ass.service.base.AssBaseService;
import com.chd.hrp.ass.service.sell.out.AssSellOutInassetsService;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Description:
 * 050901 资产有偿调拨出库单主表(无形资产)
 * @Table:
 * ASS_SELL_OUT_INASSETS
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

 
@Service("assSellOutInassetsService")
public class AssSellOutInassetsServiceImpl implements AssSellOutInassetsService {

	private static Logger logger = Logger.getLogger(AssSellOutInassetsServiceImpl.class);
	// 引入DAO操作
		@Resource(name = "assSellOutInassetsMapper")
		private final AssSellOutInassetsMapper assSellOutInassetsMapper = null;

		@Resource(name = "assSellOutDetailInassetsMapper")
		private final AssSellOutDetailInassetsMapper assSellOutDetailInassetsMapper = null;

		@Resource(name = "assSellOutSourceInassetsMapper")
		private final AssSellOutSourceInassetsMapper assSellOutSourceInassetsMapper = null;

		@Resource(name = "assResourceInassetsMapper")
		private final AssResourceInassetsMapper assResourceInassetsMapper = null;

		@Resource(name = "assCardInassetsMapper")
		private final AssCardInassetsMapper assCardInassetsMapper = null;

		@Resource(name = "assBaseService")
		private final AssBaseService assBaseService = null;

		@Resource(name = "hrpAssSelectMapper")
		private final HrpAssSelectMapper hrpAssSelectMapper = null;

		/**
		 * @Description 添加050901 资产有偿调拨出库单主表（无形资产）<BR>
		 * @param entityMap
		 * @return String JSON
		 * @throws DataAccessException
		 */
		@Override
		public String add(Map<String, Object> entityMap) throws DataAccessException {

			// 获取对象050901 资产有偿调拨出库单主表（无形资产）
			AssSellOutInassets assSellOutInassets = queryByCode(entityMap);

			if (assSellOutInassets != null) {

				return "{\"error\":\"数据重复,请重新添加.\"}";

			}

			try {

				int state = assSellOutInassetsMapper.add(entityMap);

				return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

			} catch (Exception e) {

				logger.error(e.getMessage(), e);

				return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 add\"}";

			}

		}

		/**
		 * @Description 批量添加050901 资产有偿调拨出库单主表（无形资产）<BR>
		 * @param entityList
		 * @return String JSON
		 * @throws DataAccessException
		 */
		@Override
		public String addBatch(List<Map<String, Object>> entityList) throws DataAccessException {

			try {

				assSellOutInassetsMapper.addBatch(entityList);

				return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

			} catch (Exception e) {

				logger.error(e.getMessage(), e);

				return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addBatch\"}";

			}

		}

		/**
		 * @Description 更新050901 资产有偿调拨出库单主表（无形资产）<BR>
		 * @param entityMap
		 * @return String JSON
		 * @throws DataAccessException
		 */
		@Override
		public String update(Map<String, Object> entityMap) throws DataAccessException {

			try {

				int state = assSellOutInassetsMapper.update(entityMap);

				return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

			} catch (Exception e) {

				logger.error(e.getMessage(), e);

				return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 update\"}";

			}

		}

		/**
		 * @Description 批量更新050901 资产有偿调拨出库单主表（无形资产）<BR>
		 * @param entityList
		 * @return String JSON
		 * @throws DataAccessException
		 */
		@Override
		public String updateBatch(List<Map<String, Object>> entityList) throws DataAccessException {

			try {

				assSellOutInassetsMapper.updateBatch(entityList);

				return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

			} catch (Exception e) {

				logger.error(e.getMessage(), e);

				return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 updateBatch\"}";

			}

		}

		/**
		 * @Description 删除050901 资产有偿调拨出库单主表（无形资产）<BR>
		 * @param entityMap
		 * @return String JSON
		 * @throws DataAccessException
		 */
		@Override
		public String delete(Map<String, Object> entityMap) throws DataAccessException {

			try {

				int state = assSellOutInassetsMapper.delete(entityMap);

				return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

			} catch (Exception e) {

				logger.error(e.getMessage(), e);

				return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 delete\"}";

			}

		}

		/**
		 * @Description 批量删除050901 资产有偿调拨出库单主表（无形资产）<BR>
		 * @param entityList
		 * @return String JSON
		 * @throws DataAccessException
		 */
		@Override
		public String deleteBatch(List<Map<String, Object>> entityList) throws DataAccessException {

			try {
				assSellOutSourceInassetsMapper.deleteBatch(entityList);
				assSellOutDetailInassetsMapper.deleteBatch(entityList);
				assSellOutInassetsMapper.deleteBatch(entityList);

				return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

			} catch (Exception e) {

				logger.error(e.getMessage(), e);
				throw new SysException(e.getMessage());
			}
		}

		/**
		 * @Description 添加050901 资产有偿调拨出库单主表（无形资产）<BR>
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
			mapVo.put("sell_out_no", entityMap.get("sell_out_no"));

			Map<String, Object> vCreateDateMap = new HashMap<String, Object>();
			vCreateDateMap.put("group_id", entityMap.get("group_id"));
			vCreateDateMap.put("hos_id", entityMap.get("hos_id"));
			vCreateDateMap.put("copy_code", entityMap.get("copy_code"));
			vCreateDateMap.put("ass_nature", "05");
			vCreateDateMap.put("use_state", "1,2,3,4,5");
			vCreateDateMap.put("in_date", entityMap.get("create_date"));

			Map<String, Object> vStoreMap = new HashMap<String, Object>();
			vStoreMap.put("group_id", entityMap.get("group_id"));
			vStoreMap.put("hos_id", entityMap.get("hos_id"));
			vStoreMap.put("copy_code", entityMap.get("copy_code"));
			vStoreMap.put("ass_nature", "05");
			vStoreMap.put("use_state", "1,2,3,4,5");
			vStoreMap.put("store_id", entityMap.get("out_store_id"));
			vStoreMap.put("store_no", entityMap.get("out_store_no"));

			entityMap.put("state", "0");
			List<AssSellOutInassets> list = (List<AssSellOutInassets>) assSellOutInassetsMapper.queryExists(mapVo);
			try {
				boolean flag = true;
				boolean flag1 = true;
				if (list.size() > 0) {
					assSellOutInassetsMapper.update(entityMap);
				} else {
					if(entityMap.get("create_date") != null && !"".equals(entityMap.get("create_date"))){
						entityMap.put("year", entityMap.get("create_date").toString().substring(0,4));
						entityMap.put("month", entityMap.get("create_date").toString().substring(5,7));
					}
					entityMap.put("bill_table", "ASS_SELL_OUT_INASSETS");
					String sell_out_no = assBaseService.getBillNOSeqNo(entityMap);
					entityMap.put("sell_out_no", sell_out_no);
					assSellOutInassetsMapper.add(entityMap);
					assBaseService.updateAssBillNoMaxNo(entityMap);
				}

				List<Map> detail = ChdJson.fromJsonAsList(Map.class, entityMap.get("ParamVo").toString());
				Double price = 0.0;
				Double add_depre = 0.0;
				Double cur_money = 0.0;
				Double fore_money = 0.0;
				Double sell_price = 0.0;
				Double dispose_income = 0.0;
				Double dispose_tax = 0.0;

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
					Integer createDateExists = hrpAssSelectMapper.queryAssExistsInassetsCard(vCreateDateMap);
					if (createDateExists == 0) {
						flag = false;
						break;
					}

					Integer storeExists = hrpAssSelectMapper.queryAssExistsInassetsCard(vStoreMap);
					if (storeExists == 0) {
						flag1 = false;
						break;
					}
					
					String create_date = entityMap.get("create_date").toString();

					detailVo.put("ass_year", create_date.substring(0, create_date.indexOf("-")));

					detailVo.put("ass_month",
							create_date.substring(create_date.indexOf("-") + 1, create_date.lastIndexOf("-")));

					detailVo.put("ass_naturs", "05");

					String results = collectAssAllotOutInassets(detailVo);

					JSONObject jsonObj = JSONObject.parseObject(results);

					if (jsonObj.containsKey("msg")) {
						transactionManager.rollback(status);
						return jsonObj.toJSONString();
					}

					JSONArray cardArray = JSONArray.parseArray(jsonObj.get("Card").toString());

					JSONObject cardObj = JSONObject.parseObject(cardArray.get(0).toString());

					detailVo.put("sell_out_no", entityMap.get("sell_out_no"));

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

					fore_money = fore_money + Double.parseDouble(cardObj.get("Fore").toString());

					detailVo.put("add_depre_month", cardObj.get("AddAccMonth").toString());

					if (detailVo.get("sell_price") != null && !detailVo.get("sell_price").equals("")) {
						sell_price = sell_price + Double.parseDouble(detailVo.get("sell_price").toString());
						detailVo.put("sell_price", detailVo.get("sell_price"));
					} else {
						detailVo.put("sell_price", 0);
					}

					if (detailVo.get("dispose_income") != null && !detailVo.get("dispose_income").equals("")) {
						dispose_income = dispose_income + Double.parseDouble(detailVo.get("dispose_income").toString());
						detailVo.put("dispose_income", detailVo.get("dispose_income"));
					} else {
						detailVo.put("dispose_income", 0);
					}

					if (detailVo.get("dispose_tax") != null && !detailVo.get("dispose_tax").equals("")) {
						dispose_tax = dispose_tax + Double.parseDouble(detailVo.get("dispose_tax").toString());
						detailVo.put("dispose_tax", detailVo.get("dispose_tax"));
					} else {
						detailVo.put("dispose_tax", 0);
					}

					if (detailVo.get("note") != null && !detailVo.get("note").equals("")) {

						detailVo.put("note", detailVo.get("note"));
					} else {
						detailVo.put("note", "");
					}

					JSONArray sourceArray = JSONArray.parseArray(jsonObj.get("Source").toString());

					List<AssResourceInassets> resourceList = assResourceInassetsMapper.queryByAssCardNo(detailVo);
					for (AssResourceInassets resSource : resourceList) {
						Map<String, Object> mapSource = new HashMap<String, Object>();
						mapSource.put("group_id", resSource.getGroup_id());
						mapSource.put("hos_id", resSource.getHos_id());
						mapSource.put("copy_code", resSource.getCopy_code());
						mapSource.put("sell_out_no", entityMap.get("sell_out_no"));
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

				assSellOutSourceInassetsMapper.delete(entityMap);
				assSellOutDetailInassetsMapper.delete(entityMap);
				assSellOutDetailInassetsMapper.addBatch(listVo);
				if (listSourceVo.size() > 0) {
					assSellOutSourceInassetsMapper.addBatch(listSourceVo);
				}
				entityMap.put("price", price + "");
				entityMap.put("add_depre", add_depre + "");
				entityMap.put("cur_money", cur_money + "");
				entityMap.put("fore_money", fore_money + "");
				entityMap.put("sell_price", sell_price + "");
				entityMap.put("dispose_income", dispose_income + "");
				entityMap.put("dispose_tax", dispose_tax + "");
				assSellOutInassetsMapper.updateSellOutMoney(entityMap);
				transactionManager.commit(status);
				return "{\"msg\":\"保存成功.\",\"state\":\"true\",\"sell_out_no\":\"" + entityMap.get("sell_out_no").toString()
						+ "\",\"price\":\"" + price + "\",\"add_depre\":\"" + add_depre + "\",\"cur_money\":\"" + cur_money
						+ "\",\"fore_money\":\"" + fore_money + "\",\"sell_price\":\"" + sell_price
						+ "\",\"dispose_income\":\"" + dispose_income + "\",\"dispose_tax\":\"" + dispose_tax + "\"}";
			} catch (Exception e) {
				transactionManager.rollback(status);
				logger.error(e.getMessage(), e);
				throw new SysException(e.getMessage());

			}

		}

		/**
		 * @Description 查询结果集050901 资产有偿调拨出库单主表（无形资产）<BR>
		 * @param entityMap
		 * @return String JSON
		 * @throws DataAccessException
		 */
		@Override
		public String query(Map<String, Object> entityMap) throws DataAccessException {

			SysPage sysPage = new SysPage();

			sysPage = (SysPage) entityMap.get("sysPage");

			if (sysPage.getTotal() == -1) {

				List<AssSellOutInassets> list = (List<AssSellOutInassets>) assSellOutInassetsMapper.query(entityMap);

				return ChdJson.toJson(list);

			} else {

				RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

				List<AssSellOutInassets> list = (List<AssSellOutInassets>) assSellOutInassetsMapper.query(entityMap,
						rowBounds);

				PageInfo page = new PageInfo(list);

				return ChdJson.toJson(list, page.getTotal());

			}

		}

		/**
		 * @Description 获取对象050901 资产有偿调拨出库单主表（无形资产）<BR>
		 * @param entityMap<BR>
		 *            参数为主键
		 * @return String JSON
		 * @throws DataAccessException
		 */
		@Override
		public <T> T queryByCode(Map<String, Object> entityMap) throws DataAccessException {
			return assSellOutInassetsMapper.queryByCode(entityMap);
		}

		/**
		 * @Description 获取050901 资产有偿调拨出库单主表（无形资产）<BR>
		 * @param entityMap<BR>
		 *            参数为要检索的字段
		 * @return AssSellOutInassets
		 * @throws DataAccessException
		 */
		@Override
		public <T> T queryByUniqueness(Map<String, Object> entityMap) throws DataAccessException {
			return assSellOutInassetsMapper.queryByUniqueness(entityMap);
		}

		/**
		 * @Description 获取050901 资产有偿调拨出库单主表（无形资产）<BR>
		 * @param entityMap<BR>
		 *            参数为要检索的字段
		 * @return List<AssSellOutInassets>
		 * @throws DataAccessException
		 */
		@Override
		public List<?> queryExists(Map<String, Object> entityMap) throws DataAccessException {
			return assSellOutInassetsMapper.queryExists(entityMap);
		}

		public String collectAssAllotOutInassets(Map<String, Object> entityMap) {
			WebApplicationContext ctx = ContextLoader.getCurrentWebApplicationContext();

			DataSourceTransactionManager transactionManager = (DataSourceTransactionManager) ctx
					.getBean("transactionManager");

			DefaultTransactionDefinition def = new DefaultTransactionDefinition();

			def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);

			TransactionStatus status = transactionManager.getTransaction(def); // 获得事务状态

			assSellOutInassetsMapper.collectAssSellOutInassets(entityMap);

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
		public String updateSellOutConfirm(List<Map<String, Object>> entityMap, List<Map<String, Object>> cardEntityMap)
				throws DataAccessException {
			try {
				assSellOutInassetsMapper.updateConfirm(entityMap);
				assCardInassetsMapper.updateSellOutConfirm(cardEntityMap);

				return "{\"msg\":\"确认成功.\",\"state\":\"true\"}";
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
				throw new SysException(e.getMessage());
			}
		}

		@Override
		public String queryBySellInImport(Map<String, Object> entityMap) throws DataAccessException {
			SysPage sysPage = new SysPage();
			
			sysPage = (SysPage) entityMap.get("sysPage");
			
			if (sysPage.getTotal()==-1){
				
				List<AssSellOutInassets> list = (List<AssSellOutInassets>)assSellOutInassetsMapper.queryBySellInImport(entityMap);
				
				return ChdJson.toJson(list);
				
			}else{
				
				RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
				
				List<AssSellOutInassets> list = (List<AssSellOutInassets>)assSellOutInassetsMapper.queryBySellInImport(entityMap, rowBounds);
				
				PageInfo page = new PageInfo(list);
				
				return ChdJson.toJson(list, page.getTotal());
				
			}
		}
		@Override
		public String queryBySellInImportView(Map<String, Object> entityMap) throws DataAccessException {
			SysPage sysPage = new SysPage();
			
			sysPage = (SysPage) entityMap.get("sysPage");
			
			if (sysPage.getTotal()==-1){
				
				List<AssSellOutInassets> list = (List<AssSellOutInassets>)assSellOutInassetsMapper.queryBySellInImportView(entityMap);
				
				return ChdJson.toJson(list);
				
			}else{
				
				RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
				
				List<AssSellOutInassets> list = (List<AssSellOutInassets>)assSellOutInassetsMapper.queryBySellInImportView(entityMap, rowBounds);
				
				PageInfo page = new PageInfo(list);
				
				return ChdJson.toJson(list, page.getTotal());
				
			}
		}
	
		/**
		 * 其他无形资产 资产调拨出库 新版打印  调用的方法
		 * @param entityMap
		 * @return
		 * @throws DataAccessException
		 */
		@Override
		public Map<String, Object> queryAssSellOutInassetsByPrintTemlatePrint(Map<String, Object> entityMap)throws DataAccessException {
			
			try{
				
				Map<String,Object> reMap=new HashMap<String,Object>();
				WebApplicationContext context = ContextLoader.getCurrentWebApplicationContext();
				AssSellOutInassetsMapper assSellOutInassetsMapper = (AssSellOutInassetsMapper)context.getBean("assSellOutInassetsMapper");
				 
				//主页面 批量打印查询
				if("1".equals(String.valueOf(entityMap.get("p_num")))){
					
					//查询 其他无形资产 资产调拨出库 主表数据
					List<Map<String,Object>> map= assSellOutInassetsMapper.queryAssSellOutInassetsPrintTemlateByMainBatch(entityMap);
					//查询 其他无形资产 资产调拨出库 明细表数据
					List<Map<String,Object>> list= assSellOutInassetsMapper.queryAssSellOutInassetsPrintTemlateByDetail(entityMap);
					
					reMap.put("main", map);
					
					reMap.put("detail", list); 
					
					return reMap;
					
				}else{ //修改页面 打印查询
					//查询 其他无形资产 资产调拨出库 主表数据
					Map<String,Object> map= assSellOutInassetsMapper.queryAssSellOutInassetsPrintTemlateByMain(entityMap);
					//查询 其他无形资产 资产调拨出库 明细表数据
					List<Map<String,Object>> list= assSellOutInassetsMapper.queryAssSellOutInassetsPrintTemlateByDetail(entityMap);
					
				
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
		 * 其他无形资产  资产调拨出库 出库单状态查询 （ 打印校验数据用）
		 */
		@Override
		public List<String> queryAssSellOutInassetsState(Map<String, Object> mapVo) throws DataAccessException {
			
			return assSellOutInassetsMapper.queryAssSellOutInassetsState(mapVo);
		}

		@Override
		public String queryDetails(Map<String, Object> entityMap) {
			SysPage sysPage = new SysPage();

			sysPage = (SysPage) entityMap.get("sysPage");

			if (sysPage.getTotal() == -1) {

				List<Map<String, Object>> list = (List<Map<String, Object>>) assSellOutInassetsMapper.queryDetails(entityMap);

				return ChdJson.toJson(list);

			} else {

				RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

				List<Map<String, Object>> list = (List<Map<String, Object>>) assSellOutInassetsMapper.queryDetails(entityMap, rowBounds);

				PageInfo page = new PageInfo(list);

				return ChdJson.toJson(list, page.getTotal());

			}
		}
}
