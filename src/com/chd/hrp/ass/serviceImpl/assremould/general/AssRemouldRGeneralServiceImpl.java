package com.chd.hrp.ass.serviceImpl.assremould.general;

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
import com.chd.hrp.ass.dao.assremould.general.AssRemouldAdetailGeneralMapper;
import com.chd.hrp.ass.dao.assremould.general.AssRemouldRGeneralMapper;
import com.chd.hrp.ass.dao.assremould.general.AssRemouldRdetailGeneralMapper;
import com.chd.hrp.ass.dao.assremould.general.AssRemouldRsourceGeneralMapper;
import com.chd.hrp.ass.dao.card.AssCardGeneralMapper;
import com.chd.hrp.ass.dao.resource.AssResourceGeneralMapper;
import com.chd.hrp.ass.entity.assremould.AssRemouldAdetail;
import com.chd.hrp.ass.entity.assremould.general.AssRemouldAdetailGeneral;
import com.chd.hrp.ass.entity.assremould.general.AssRemouldRGeneral;
import com.chd.hrp.ass.entity.change.AssChangeGeneral;
import com.chd.hrp.ass.entity.resource.AssResourceGeneral;
import com.chd.hrp.ass.service.assremould.general.AssRemouldRGeneralService;
import com.chd.hrp.ass.service.base.AssBaseService;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Description: 050805 资产改造记录(一般设备)
 * @Table: ASS_REMOULD_R_General
 * @Author: bell
 * @email: bell@e-tonggroup.com
 * @Version: 1.0
 */
@Service("assRemouldRGeneralService")
public class AssRemouldRGeneralServiceImpl implements AssRemouldRGeneralService {
 
	private static Logger logger = Logger.getLogger(AssRemouldRGeneralServiceImpl.class);
	// 引入DAO操作 主表
	@Resource(name = "assRemouldRGeneralMapper")
	private final AssRemouldRGeneralMapper assRemouldRGeneralMapper = null;
	// 明细表
	@Resource(name = "assRemouldRdetailGeneralMapper")
	private final AssRemouldRdetailGeneralMapper assRemouldRdetailGeneralMapper = null;
	// 明细表
	@Resource(name = "assRemouldAdetailGeneralMapper")
	private final AssRemouldAdetailGeneralMapper assRemouldAdetailGeneralMapper = null;
	// 资金来源表
	@Resource(name = "assRemouldRsourceGeneralMapper")
	private final AssRemouldRsourceGeneralMapper assRemouldRsourceGeneralMapper = null;
	// 卡片资金来源
	@Resource(name = "assResourceGeneralMapper")
	private final AssResourceGeneralMapper assResourceGeneralMapper = null;
	@Resource(name = "assBaseService")
	private final AssBaseService assBaseService = null;

	@Resource(name = "hrpAssSelectMapper")
	private final HrpAssSelectMapper hrpAssSelectMapper = null;

	@Resource(name = "assCardGeneralMapper")
	private final AssCardGeneralMapper assCardGeneralMapper = null;

	/**
	 * @Description 添加050805 资产改造记录(一般设备)<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String add(Map<String, Object> entityMap) throws DataAccessException {

		// 获取对象050805 资产改造记录(一般设备)
		AssRemouldRGeneral assRemouldRGeneral = queryByCode(entityMap);

		if (assRemouldRGeneral != null) {

			return "{\"error\":\"数据重复,请重新添加.\"}";

		}

		try {

			int state = assRemouldRGeneralMapper.add(entityMap);

			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 add\"}";

		}

	}

	/**
	 * @Description 批量添加050805 资产改造记录(一般设备)<BR>
	 * @param entityList
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String addBatch(List<Map<String, Object>> entityList) throws DataAccessException {

		try {

			assRemouldRGeneralMapper.addBatch(entityList);

			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addBatch\"}";

		}

	}

	/**
	 * @Description 更新050805 资产改造记录(一般设备)<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String update(Map<String, Object> entityMap) throws DataAccessException {

		try {

			int state = assRemouldRGeneralMapper.update(entityMap);

			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 update\"}";

		}

	}

	/**
	 * @Description 批量更新050805 资产改造记录(一般设备)<BR>
	 * @param entityList
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String updateBatch(List<Map<String, Object>> entityList) throws DataAccessException {

		try {

			assRemouldRGeneralMapper.updateBatch(entityList);

			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 updateBatch\"}";

		}

	}

	/**
	 * @Description 删除050805 资产改造记录(一般设备)<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String delete(Map<String, Object> entityMap) throws DataAccessException {

		try {

			int state = assRemouldRGeneralMapper.delete(entityMap);

			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 delete\"}";

		}

	}

	/**
	 * @Description 批量删除050805 资产改造记录(一般设备)<BR>
	 * @param entityList
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String deleteBatch(List<Map<String, Object>> entityList) throws DataAccessException {

		try {

			assRemouldRGeneralMapper.deleteBatch(entityList);
			assRemouldRsourceGeneralMapper.deleteBatch(entityList);
			assRemouldRdetailGeneralMapper.deleteBatch(entityList);

			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 deleteBatch\"}";

		}
	}

	/**
	 * @Description 添加050805 资产改造记录(一般设备)<BR>
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
		List<Map<String, Object>> detSourceList = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> sourceList = new ArrayList<Map<String, Object>>();
		Map<String, Object> mapVo = new HashMap<String, Object>();
		mapVo.put("group_id", entityMap.get("group_id"));
		mapVo.put("hos_id", entityMap.get("hos_id"));
		mapVo.put("copy_code", entityMap.get("copy_code"));
		mapVo.put("record_no", entityMap.get("record_no"));

		Map<String, Object> vCreateDateMap = new HashMap<String, Object>();
		vCreateDateMap.put("group_id", entityMap.get("group_id"));
		vCreateDateMap.put("hos_id", entityMap.get("hos_id"));
		vCreateDateMap.put("copy_code", entityMap.get("copy_code"));
		vCreateDateMap.put("ass_nature", "03");
		// vCreateDateMap.put("bus_type_code", "01");
		vCreateDateMap.put("use_state", "1,2,3,4,5,6");
		vCreateDateMap.put("in_date", entityMap.get("create_date"));
		vCreateDateMap.put("state", "0");

		/*
		 * Map<String, Object> vStoreMap = new HashMap<String, Object>();
		 * vStoreMap.put("group_id", entityMap.get("group_id")); vStoreMap.put("hos_id",
		 * entityMap.get("hos_id")); vStoreMap.put("copy_code",
		 * entityMap.get("copy_code")); vStoreMap.put("ass_nature", "03");
		 * vStoreMap.put("bus_type_code", "01"); vStoreMap.put("use_state",
		 * "1,2,3,4,5"); vStoreMap.put("store_id", entityMap.get("store_id"));
		 * vStoreMap.put("store_no", entityMap.get("store_no"));
		 * 
		 * Map<String, Object> vVenMap = new HashMap<String, Object>();
		 * vVenMap.put("group_id", entityMap.get("group_id")); vVenMap.put("hos_id",
		 * entityMap.get("hos_id")); vVenMap.put("copy_code",
		 * entityMap.get("copy_code")); vVenMap.put("ass_nature", "03");
		 * vVenMap.put("bus_type_code", "01"); vVenMap.put("use_state", "1,2,3,4,5");
		 * vVenMap.put("ven_id", entityMap.get("ven_id")); vVenMap.put("ven_no",
		 * entityMap.get("ven_no"));
		 */
		entityMap.put("state", "0");
		// 查询主表是否存在，add or update
		List<AssChangeGeneral> list = (List<AssChangeGeneral>) assRemouldRGeneralMapper.queryExists(mapVo);
		boolean flag = true;
		try {
			if (list.size() > 0) {
				assRemouldRGeneralMapper.update(entityMap);
			} else {
				// 调公用方法 生成单号
				if(entityMap.get("create_date") != null && !"".equals(entityMap.get("create_date"))){
					entityMap.put("year", entityMap.get("create_date").toString().substring(0,4));
					entityMap.put("month", entityMap.get("create_date").toString().substring(5,7));
				}
				entityMap.put("bill_table", "ASS_REMOULD_R_GENERAL");
				String record_no = assBaseService.getBillNOSeqNo(entityMap);
				entityMap.put("record_no", record_no);
				assRemouldRGeneralMapper.add(entityMap);
				assBaseService.updateAssBillNoMaxNo(entityMap);
			}
			// 取页面卡片数据
			List<Map> detail = ChdJson.fromJsonAsList(Map.class, entityMap.get("ParamVo").toString());
			// 取明细表卡片呢数据
			List<Map<String, Object>> detailList = assRemouldRdetailGeneralMapper.queryByRecordNoMap(mapVo);

			// 依次循环，修改时判断是否修改了卡片，如没有则略过，如更改了卡片数据，则将资金来源表中保存的原有资金来源删除，重新添加新卡片的对应资金来源
			// 第一次添加时略过此步骤
			for (Map<String, Object> det : detailList) {
				boolean falg = false;
				for (Map<String, Object> temp : detail) {
					if (temp.get("ass_card_no") == null || "".equals(temp.get("ass_card_no"))) {
						continue;
					}
					if (det.get("ass_card_no").toString().equals(temp.get("ass_card_no").toString())) {
						falg = true;
						break;
					}
				}
				if (falg == false) {
					detSourceList.add(det);
				}
			}
			if (detSourceList.size() > 0) {
				assRemouldRsourceGeneralMapper.deleteBatch(detSourceList);
			}

			for (Map<String, Object> detailVo : detail) {
				if (detailVo.get("ass_card_no") == null || "".equals(detailVo.get("ass_card_no"))) {
					continue;
				}

				detailVo.put("group_id", entityMap.get("group_id"));
				detailVo.put("hos_id", entityMap.get("hos_id"));
				detailVo.put("copy_code", entityMap.get("copy_code"));
				detailVo.put("record_no", entityMap.get("record_no"));
				// detailVo.put("bus_type_code", entityMap.get("bus_type_code"));

				vCreateDateMap.put("ass_card_no", detailVo.get("ass_card_no"));
				// 判断卡片是否入账，没入库的卡片不能操作
				Integer createDateExists = hrpAssSelectMapper.queryAssExistsGeneralCard(vCreateDateMap);
				if (createDateExists == 0) {
					flag = false;
					break;
				}

				if (detailVo.get("change_price") != null && !detailVo.get("change_price").equals("")) {
					detailVo.put("change_price", detailVo.get("change_price"));
				} else {
					detailVo.put("change_price", 0);
				}

				if (detailVo.get("note") != null && !detailVo.get("note").equals("")) {
					detailVo.put("note", detailVo.get("note"));
				} else {
					detailVo.put("note", "");
				}
				// 更新时查询明细表，按卡片编号取改造费用
				Double price = 0.0;
				Double change_price = 0.0;
				List<Map<String, Object>> existsSource = assRemouldRsourceGeneralMapper
						.queryChangeSourceByAssCardNo(detailVo);
				if (existsSource.size() == 0 || existsSource == null) {
					List<Map<String, Object>> reSourceList = assResourceGeneralMapper.queryByAssCardNoMap(detailVo);
					for (Map<String, Object> source : reSourceList) {
						source.put("record_no", entityMap.get("record_no"));
						source.put("ass_card_no", detailVo.get("ass_card_no"));
						source.put("change_price", detailVo.get("change_price"));
						source.put("note", detailVo.get("note"));
						sourceList.add(source);
					}
				} else {
					for (Map<String, Object> source : existsSource) {
						change_price = change_price + Double.parseDouble(source.get("change_price").toString());
					}
				}
				detailVo.put("change_price", change_price);
				listVo.add(detailVo);
			}

			if (!flag) {
				transactionManager.rollback(status);
				return "{\"warn\":\"存在尚未入账的卡片不能进行变动.\"}";
			}
			assRemouldRsourceGeneralMapper.delete(entityMap);
			assRemouldRdetailGeneralMapper.delete(entityMap);
			assRemouldRdetailGeneralMapper.addBatch(listVo);

			if (sourceList.size() > 0) {
				assRemouldRsourceGeneralMapper.deleteBatch(sourceList);
				assRemouldRsourceGeneralMapper.addBatch(sourceList);
			}
			transactionManager.commit(status);
			return "{\"msg\":\"保存成功.\",\"state\":\"true\",\"record_no\":\"" + entityMap.get("record_no") + "\"}";

		} catch (Exception e) {
			transactionManager.rollback(status);
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}

	}

	/**
	 * @Description 查询结果集050805 资产改造记录(一般设备)<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String query(Map<String, Object> entityMap) throws DataAccessException {

		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		if (sysPage.getTotal() == -1) {

			List<AssRemouldRGeneral> list = (List<AssRemouldRGeneral>) assRemouldRGeneralMapper.query(entityMap);

			return ChdJson.toJson(list);

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

			List<AssRemouldRGeneral> list = (List<AssRemouldRGeneral>) assRemouldRGeneralMapper.query(entityMap,
					rowBounds);

			PageInfo page = new PageInfo(list);

			return ChdJson.toJson(list, page.getTotal());

		}

	}

	/**
	 * @Description 获取对象050805 资产改造记录(一般设备)<BR>
	 * @param entityMap<BR>
	 *            参数为主键
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public <T> T queryByCode(Map<String, Object> entityMap) throws DataAccessException {
		return assRemouldRGeneralMapper.queryByCode(entityMap);
	}

	/**
	 * @Description 获取050805 资产改造记录(一般设备)<BR>
	 * @param entityMap<BR>
	 *            参数为要检索的字段
	 * @return AssRemouldRGeneral
	 * @throws DataAccessException
	 */
	@Override
	public <T> T queryByUniqueness(Map<String, Object> entityMap) throws DataAccessException {
		return assRemouldRGeneralMapper.queryByUniqueness(entityMap);
	}

	/**
	 * @Description 获取050805 资产改造记录(一般设备)<BR>
	 * @param entityMap<BR>
	 *            参数为要检索的字段
	 * @return List<AssRemouldRGeneral>
	 * @throws DataAccessException
	 */
	@Override
	public List<?> queryExists(Map<String, Object> entityMap) throws DataAccessException {
		return assRemouldRGeneralMapper.queryExists(entityMap);
	}

	@Override
	public String queryAssRemouldSourceGeneral(Map<String, Object> mapVo) {
		List<Map<String, Object>> list = assRemouldRsourceGeneralMapper.queryAssRemouldSourceGeneral(mapVo);
		return ChdJson.toJson(list);
	}

	@Override
	public String queryAssRemouldRdetailGeneral(Map<String, Object> mapVo) {
		List<Map<String, Object>> list = assRemouldRdetailGeneralMapper.queryByRecordNoMap(mapVo);
		return ChdJson.toJson(list);
	}

	@Override
	public String saveAssRemouldRSourceGeneral(Map<String, Object> entityMap) {
		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();
		List<Map> detail = ChdJson.fromJsonAsList(Map.class, entityMap.get("ParamVo").toString());
		Double change_price = 0.0;
		Double price = 0.0;
		try {
			for (Map<String, Object> detailVo : detail) {
				if (detailVo.get("source_id") == null || "".equals(detailVo.get("source_id"))) {
					continue;
				}
				detailVo.put("group_id", entityMap.get("group_id"));
				detailVo.put("hos_id", entityMap.get("hos_id"));
				detailVo.put("copy_code", entityMap.get("copy_code"));
				detailVo.put("record_no", entityMap.get("record_no"));
				detailVo.put("ass_card_no", entityMap.get("ass_card_no"));
				detailVo.put("source_id", String.valueOf(detailVo.get("source_id")));

				if (detailVo.get("change_price") != null && !detailVo.get("change_price").equals("")) {
					change_price = change_price + Double.parseDouble(detailVo.get("change_price").toString());
					detailVo.put("change_price", detailVo.get("change_price") + "");
				} else {
					detailVo.put("change_price", "0");
				}

				if (detailVo.get("note") != null && !detailVo.get("note").equals("")) {
					detailVo.put("note", detailVo.get("note"));
					entityMap.put("note", detailVo.get("note"));
				} else {
					detailVo.put("note", "");
					entityMap.put("note", "");
				}

				listVo.add(detailVo);
			}

			assRemouldRsourceGeneralMapper.delete(entityMap);
			assRemouldRsourceGeneralMapper.addBatch(listVo);

			entityMap.put("change_price", change_price + "");

			assRemouldRdetailGeneralMapper.updatePriceBySource(entityMap);

			return "{\"msg\":\"保存成功.\",\"state\":\"true\",\"change_price\":\"" + change_price + "\"}";
		} catch (NumberFormatException e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		} catch (DataAccessException e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}
	}

	@Override
	public String updateAssRemouldAgeneralConfirmState(List<Map<String, Object>> listVo,
			List<Map<String, Object>> listCardVo) {

		try {
			assRemouldRGeneralMapper.updateConfirmAssRemouldRspecial(listVo);
			assCardGeneralMapper.updateRemouldRSpecialConfirm(listCardVo);
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			throw new SysException(e);
		}
	}

	// 删除资金来源
	@Override
	public String deleteAssSourceGeneral(List<Map<String, Object>> entityMap) {
		try {
			assRemouldRsourceGeneralMapper.deleteBatch(entityMap);

			Map<String, Object> mapVo = new HashMap<String, Object>();
			mapVo.put("group_id", entityMap.get(0).get("group_id"));
			mapVo.put("hos_id", entityMap.get(0).get("hos_id"));
			mapVo.put("copy_code", entityMap.get(0).get("copy_code"));
			mapVo.put("record_no", entityMap.get(0).get("record_no"));
			mapVo.put("ass_card_no", entityMap.get(0).get("ass_card_no"));
			Double change_price = 0.0;

			List<Map<String, Object>> listMap = assRemouldRsourceGeneralMapper.queryChangeSourceByAssCardNo(mapVo);
			for (Map<String, Object> map : listMap) {
				change_price = change_price + Double.parseDouble(map.get("change_price").toString());

			}
			mapVo.put("change_price", change_price + "");

			assRemouldRdetailGeneralMapper.updatePriceBySource(mapVo);

			return "{\"msg\":\"删除成功.\",\"state\":\"true\",\"change_price\":\"" + change_price + "\"}";

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}
	}

	/**
	 * 引入改造申请
	 */
	@Override
	public String initAssCheckGeneral(Map<String, Object> entityMap) {

		WebApplicationContext ctx = ContextLoader.getCurrentWebApplicationContext();
		DataSourceTransactionManager transactionManager = (DataSourceTransactionManager) ctx
				.getBean("transactionManager");

		DefaultTransactionDefinition def = new DefaultTransactionDefinition();

		def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);

		TransactionStatus status = transactionManager.getTransaction(def); // 获得事务状态

		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> detSourceList = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> sourceList = new ArrayList<Map<String, Object>>();

		try {
			String carno = entityMap.get("apply_no").toString();
			String car[] = carno.split(",");
			final List<String> ids = new ArrayList<String>();
			for (int i = 0; i < car.length; i++) {
				ids.add(car[i]);
			}
			entityMap.put("apply_no", ids);

			List<AssRemouldAdetailGeneral> assRemouldAdetail = (List<AssRemouldAdetailGeneral>) assRemouldAdetailGeneralMapper
					.query(entityMap);

			// 调公用方法 生成单号
			entityMap.put("bill_table", "ASS_REMOULD_R_GENERAL");
			String record_no = assBaseService.getBillNOSeqNo(entityMap);
			entityMap.put("record_no", record_no);
			entityMap.put("state", "0");
			entityMap.put("note", "引入改造申请");
			assRemouldRGeneralMapper.add(entityMap);

			// 关系表插入数据
			for (String temp : carno.toString().split(",")) {// 循环生成单据的明细ID
				Map<String, Object> planApplyMapvo = new HashMap<String, Object>();
				planApplyMapvo.put("group_id", entityMap.get("group_id"));
				planApplyMapvo.put("hos_id", entityMap.get("hos_id"));
				planApplyMapvo.put("copy_code", entityMap.get("copy_code"));
				planApplyMapvo.put("record_no", record_no);
				planApplyMapvo.put("apply_no", temp);

				assRemouldRGeneralMapper.addAssPlanDeptImportBid(planApplyMapvo);
			}

			assBaseService.updateAssBillNoMaxNo(entityMap);

			for (AssRemouldAdetailGeneral assRemouldAdetail2 : assRemouldAdetail) {

				Map<String, Object> map = new HashMap<String, Object>();

				map.put("group_id", assRemouldAdetail2.getGroup_id());
				map.put("hos_id", assRemouldAdetail2.getHos_id());
				map.put("copy_code", assRemouldAdetail2.getCopy_code());
				map.put("record_no", record_no);
				map.put("ass_card_no", assRemouldAdetail2.getAss_card_no());
				map.put("change_price", "0");
				map.put("note", "");
				String create_date = entityMap.get("create_date").toString();

				map.put("ass_year", create_date.substring(0, create_date.indexOf("-")));

				map.put("ass_month", create_date.substring(create_date.indexOf("-") + 1, create_date.lastIndexOf("-")));

				map.put("ass_naturs", "03");
				String results = collectAssRemoildRGeneral(map);

				JSONObject jsonObj = JSONObject.parseObject(results);

				if (jsonObj.containsKey("msg")) {
					transactionManager.rollback(status);
					return jsonObj.toJSONString();
				}

				JSONArray sourceArray = JSONArray.parseArray(jsonObj.get("Source").toString());
				List<AssResourceGeneral> resourceList = assResourceGeneralMapper.queryByAssCardNo(map);
				for (AssResourceGeneral resSource : resourceList) {
					Map<String, Object> mapSource = new HashMap<String, Object>();
					mapSource.put("group_id", resSource.getGroup_id());
					mapSource.put("hos_id", resSource.getHos_id());
					mapSource.put("copy_code", resSource.getCopy_code());
					mapSource.put("ass_chk_no", entityMap.get("ass_chk_no"));
					mapSource.put("ass_card_no", resSource.getAss_card_no());
					mapSource.put("source_id", resSource.getSource_id());
					mapSource.put("price", resSource.getPrice());
					mapSource.put("record_no", record_no);
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
					mapSource.put("change_price", "0");
					if (map.get("note") != null && !map.get("note").equals("")) {
						mapSource.put("note", map.get("note"));
					} else {
						mapSource.put("note", "");
					}
					detSourceList.add(mapSource);
				}

				listVo.add(map);

			}
			assRemouldRdetailGeneralMapper.addBatch(listVo);

			if (detSourceList.size() > 0) {
				// assRemouldRsourceGeneralMapper.deleteBatch(detSourceList);
				assRemouldRsourceGeneralMapper.addBatch(detSourceList);
			}
			transactionManager.commit(status);
			return "{\"msg\":\"引入成功.\",\"state\":\"true\",\"update_para\":\"" + entityMap.get("group_id") + "|"
					+ entityMap.get("hos_id") + "|" + entityMap.get("copy_code") + "|" + record_no + "|" + carno
					+ "|1\"}";
		} catch (Exception e) {
			transactionManager.rollback(status);
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}
	}

	public String collectAssRemoildRGeneral(Map<String, Object> entityMap) {
		WebApplicationContext ctx = ContextLoader.getCurrentWebApplicationContext();

		DataSourceTransactionManager transactionManager = (DataSourceTransactionManager) ctx
				.getBean("transactionManager");

		DefaultTransactionDefinition def = new DefaultTransactionDefinition();

		def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);

		TransactionStatus status = transactionManager.getTransaction(def); // 获得事务状态

		assRemouldRGeneralMapper.collectAssRemoildRGeneral(entityMap);

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
	public String deleteBatchAssApplyPlanMap(List<Map<String, Object>> listVo) {
		try {
			
			assRemouldRGeneralMapper.deleteBatchAssApplyPlanMap(listVo);
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		} catch (Exception e) {
			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}
	
		
	}
}
