/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */

package com.chd.hrp.ass.serviceImpl.change;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import com.chd.base.SessionManager;
import com.chd.base.SysPage;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.base.util.DateUtil;
import com.chd.hrp.ass.dao.HrpAssSelectMapper;
import com.chd.hrp.ass.dao.card.AssCardLandMapper;
import com.chd.hrp.ass.dao.change.AssChangeDetailLandMapper;
import com.chd.hrp.ass.dao.change.AssChangeHouseMapper;
import com.chd.hrp.ass.dao.change.AssChangeLandMapper;
import com.chd.hrp.ass.dao.change.source.AssChangeSourceLandMapper;
import com.chd.hrp.ass.dao.pay.AssPayStageLandMapper;
import com.chd.hrp.ass.dao.resource.AssResourceLandMapper;
import com.chd.hrp.ass.entity.change.AssChangeLand;
import com.chd.hrp.ass.entity.change.AssChangeOther;
import com.chd.hrp.ass.entity.change.source.AssChangeSourceLand;
import com.chd.hrp.ass.entity.resource.AssResourceLand;
import com.chd.hrp.ass.service.base.AssBaseService;
import com.chd.hrp.ass.service.change.AssChangeLandService;
import com.chd.hrp.mat.dao.info.basic.MatPayTermMapper;
import com.chd.hrp.mat.entity.MatPayTerm;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Description: 050805 资产原值变动(土地)
 * @Table: ASS_Change_LAND
 * @Author: bell
 * @email: bell@e-tonggroup.com
 * @Version: 1.0
 */
  
@Service("assChangeLandService")
public class AssChangeLandServiceImpl implements AssChangeLandService {

	private static Logger logger = Logger.getLogger(AssChangeLandServiceImpl.class);
	// 引入DAO操作
	@Resource(name = "assChangeLandMapper")
	private final AssChangeLandMapper assChangeLandMapper = null;

	@Resource(name = "assChangeDetailLandMapper")
	private final AssChangeDetailLandMapper assChangeDetailLandMapper = null;

	@Resource(name = "assChangeSourceLandMapper")
	private final AssChangeSourceLandMapper assChangeSourceLandMapper = null;

	@Resource(name = "assResourceLandMapper")
	private final AssResourceLandMapper assResourceLandMapper = null;

	@Resource(name = "assBaseService")
	private final AssBaseService assBaseService = null;

	@Resource(name = "hrpAssSelectMapper")
	private final HrpAssSelectMapper hrpAssSelectMapper = null;

	@Resource(name = "assCardLandMapper")
	private final AssCardLandMapper assCardLandMapper = null;
	
	@Resource(name = "assPayStageLandMapper")
	private final AssPayStageLandMapper assPayStageLandMapper = null;
	
	@Resource(name = "matPayTermMapper")
	private final MatPayTermMapper matPayTermMapper = null;

	/**
	 * @Description 添加050805 资产原值变动(土地)<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String add(Map<String, Object> entityMap) throws DataAccessException {

		// 获取对象050805 资产原值变动(土地)
		AssChangeLand assChangeLand = queryByCode(entityMap);

		if (assChangeLand != null) {

			return "{\"error\":\"数据重复,请重新添加.\"}";

		}

		try {

			int state = assChangeLandMapper.add(entityMap);

			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 add\"}";

		}

	}

	/**
	 * @Description 批量添加050805 资产原值变动(土地)<BR>
	 * @param entityList
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String addBatch(List<Map<String, Object>> entityList) throws DataAccessException {

		try {

			assChangeLandMapper.addBatch(entityList);

			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addBatch\"}";

		}

	}

	/**
	 * @Description 更新050805 资产原值变动(土地)<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String update(Map<String, Object> entityMap) throws DataAccessException {

		try {

			int state = assChangeLandMapper.update(entityMap);

			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 update\"}";

		}

	}

	/**
	 * @Description 批量更新050805 资产原值变动(土地)<BR>
	 * @param entityList
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String updateBatch(List<Map<String, Object>> entityList) throws DataAccessException {

		try {

			assChangeLandMapper.updateBatch(entityList);

			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 updateBatch\"}";

		}

	}

	/**
	 * @Description 删除050805 资产原值变动(土地)<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String delete(Map<String, Object> entityMap) throws DataAccessException {

		try {

			int state = assChangeLandMapper.delete(entityMap);

			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 delete\"}";

		}

	}

	/**
	 * @Description 批量删除050805 资产原值变动(土地)<BR>
	 * @param entityList
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String deleteBatch(List<Map<String, Object>> entityList) throws DataAccessException {

		try {
			assChangeSourceLandMapper.deleteBatch(entityList);
			assChangeDetailLandMapper.deleteBatch(entityList);
			assChangeLandMapper.deleteBatch(entityList);

			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());

		}
	}

	/**
	 * @Description 添加050805 资产原值变动(土地)<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String addOrUpdate(Map<String, Object> entityMap) throws DataAccessException {
		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> detSourceList = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> sourceList = new ArrayList<Map<String, Object>>();
		Map<String, Object> mapVo = new HashMap<String, Object>();
		mapVo.put("group_id", entityMap.get("group_id"));
		mapVo.put("hos_id", entityMap.get("hos_id"));
		mapVo.put("copy_code", entityMap.get("copy_code"));
		mapVo.put("change_no", entityMap.get("change_no"));

		Map<String, Object> vCreateDateMap = new HashMap<String, Object>();
		vCreateDateMap.put("group_id", entityMap.get("group_id"));
		vCreateDateMap.put("hos_id", entityMap.get("hos_id"));
		vCreateDateMap.put("copy_code", entityMap.get("copy_code"));
		vCreateDateMap.put("ass_nature", "06");
		vCreateDateMap.put("use_state", "1,2,3,4,5");
		vCreateDateMap.put("in_date", entityMap.get("create_date"));
		vCreateDateMap.put("price_change_where", "true");

		entityMap.put("state", "0");
		List<AssChangeLand> list = (List<AssChangeLand>) assChangeLandMapper.queryExists(mapVo);
		boolean flag = true;
		try {
			if (list.size() > 0) {
				assChangeLandMapper.update(entityMap);
			} else {
				if(entityMap.get("create_date") != null && !"".equals(entityMap.get("create_date"))){
					entityMap.put("year", entityMap.get("create_date").toString().substring(0,4));
					entityMap.put("month", entityMap.get("create_date").toString().substring(5,7));
				}
				entityMap.put("bill_table", "ASS_CHANGE_LAND");
				String change_no = assBaseService.getBillNOSeqNo(entityMap);
				entityMap.put("change_no", change_no);
				assChangeLandMapper.add(entityMap);
				assBaseService.updateAssBillNoMaxNo(entityMap);
			}

			List<Map> detail = ChdJson.fromJsonAsList(Map.class, entityMap.get("ParamVo").toString());

			List<Map<String, Object>> detailList = assChangeDetailLandMapper.queryByChangeNoMap(mapVo);

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
				assChangeSourceLandMapper.deleteBatch(detSourceList);
			}

			for (Map<String, Object> detailVo : detail) {
				if (detailVo.get("ass_card_no") == null || "".equals(detailVo.get("ass_card_no"))) {
					continue;
				}

				detailVo.put("group_id", entityMap.get("group_id"));
				detailVo.put("hos_id", entityMap.get("hos_id"));
				detailVo.put("copy_code", entityMap.get("copy_code"));
				detailVo.put("change_no", entityMap.get("change_no"));

				vCreateDateMap.put("ass_card_no", detailVo.get("ass_card_no"));

				Integer createDateExists = hrpAssSelectMapper.queryAssExistsLandCard(vCreateDateMap);
				if (createDateExists == 0) {
					flag = false;
					break;
				}

				if (detailVo.get("old_price") != null && !detailVo.get("old_price").equals("")) {
					detailVo.put("old_price", detailVo.get("old_price") + "");
				} else {
					detailVo.put("old_price", "0");
				}

				if (detailVo.get("change_price") != null && !detailVo.get("change_price").equals("")) {
					detailVo.put("change_price", detailVo.get("change_price") + "");
				} else {
					detailVo.put("change_price", "0");
				}

				if (detailVo.get("price") != null && !detailVo.get("price").equals("")) {
					detailVo.put("price", detailVo.get("price") + "");
				} else {
					detailVo.put("price", "0");
				}

				if (detailVo.get("note") != null && !detailVo.get("note").equals("")) {
					detailVo.put("note", detailVo.get("note"));
				} else {
					detailVo.put("note", "");
				}
				Double price = 0.0;
				Double change_price = 0.0;
				List<Map<String, Object>> existsSource = assChangeSourceLandMapper
						.queryChangeSourceByAssCardNo(detailVo);
				if (existsSource.size() == 0 || existsSource == null) {
					List<Map<String, Object>> reSourceList = assResourceLandMapper.queryByAssCardNoMap(detailVo);
					for (Map<String, Object> source : reSourceList) {
						source.put("change_no", entityMap.get("change_no"));
						source.put("ass_card_no", detailVo.get("ass_card_no"));
						source.put("source_id", source.get("source_id"));
						source.put("old_price", source.get("price"));
						source.put("change_price", "0");
						source.put("price", source.get("price"));
						source.put("note", "");
						price = price + Double.parseDouble(source.get("price").toString());
						sourceList.add(source);
					}
				} else {
					for (Map<String, Object> source : existsSource) {
						price = price + Double.parseDouble(source.get("price").toString());
						change_price = change_price + Double.parseDouble(source.get("change_price").toString());
					}
				}
				detailVo.put("price", price);
				detailVo.put("change_price", change_price);
				listVo.add(detailVo);
			}

			if (!flag) {
				TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
				return "{\"warn\":\"存在尚未入账的卡片不能进行变动.\"}";
			}

			assChangeDetailLandMapper.delete(entityMap);
			assChangeDetailLandMapper.addBatch(listVo);

			if (sourceList.size() > 0) {
				assChangeSourceLandMapper.deleteBatch(sourceList);
				assChangeSourceLandMapper.addBatch(sourceList);
			}
			return "{\"msg\":\"保存成功.\",\"state\":\"true\",\"change_no\":\"" + entityMap.get("change_no") + "\"}";

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}

	}

	/**
	 * @Description 查询结果集050805 资产原值变动(土地)<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String query(Map<String, Object> entityMap) throws DataAccessException {

		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		if (sysPage.getTotal() == -1) {

			List<AssChangeLand> list = (List<AssChangeLand>) assChangeLandMapper.query(entityMap);

			return ChdJson.toJson(list);

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

			List<AssChangeLand> list = (List<AssChangeLand>) assChangeLandMapper.query(entityMap,
					rowBounds);

			PageInfo page = new PageInfo(list);

			return ChdJson.toJson(list, page.getTotal());

		}

	}

	/**
	 * @Description 获取对象050805 资产原值变动(土地)<BR>
	 * @param entityMap<BR>
	 *            参数为主键
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public <T> T queryByCode(Map<String, Object> entityMap) throws DataAccessException {
		return assChangeLandMapper.queryByCode(entityMap);
	}

	/**
	 * @Description 获取050805 资产原值变动(土地)<BR>
	 * @param entityMap<BR>
	 *            参数为要检索的字段
	 * @return AssChangeLand
	 * @throws DataAccessException
	 */
	@Override
	public <T> T queryByUniqueness(Map<String, Object> entityMap) throws DataAccessException {
		return assChangeLandMapper.queryByUniqueness(entityMap);
	}

	/**
	 * @Description 获取050805 资产原值变动(土地)<BR>
	 * @param entityMap<BR>
	 *            参数为要检索的字段
	 * @return List<AssChangeLand>
	 * @throws DataAccessException
	 */
	@Override
	public List<?> queryExists(Map<String, Object> entityMap) throws DataAccessException {
		return assChangeLandMapper.queryExists(entityMap);
	}

	@Override
	public String deleteAssChangeSourceLand(List<Map<String, Object>> entityMap) {
		try {
			assChangeSourceLandMapper.deleteBatch(entityMap);

			Map<String, Object> mapVo = new HashMap<String, Object>();
			mapVo.put("group_id", entityMap.get(0).get("group_id"));
			mapVo.put("hos_id", entityMap.get(0).get("hos_id"));
			mapVo.put("copy_code", entityMap.get(0).get("copy_code"));
			mapVo.put("change_no", entityMap.get(0).get("change_no"));
			mapVo.put("ass_card_no", entityMap.get(0).get("ass_card_no"));
			Double change_price = 0.0;
			Double price = 0.0;
			List<Map<String, Object>> listMap = assChangeSourceLandMapper.queryChangeSourceByAssCardNo(mapVo);
			for (Map<String, Object> map : listMap) {
				change_price = change_price + Double.parseDouble(map.get("change_price").toString());
				price = price + Double.parseDouble(map.get("price").toString());
			}
			mapVo.put("change_price", change_price + "");
			mapVo.put("price", price + "");

			assChangeDetailLandMapper.updatePriceBySource(mapVo);

			return "{\"msg\":\"删除成功.\",\"state\":\"true\",\"change_price\":\"" + change_price + "\",\"price\":\""
					+ price + "\"}";

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}
	}

	@Override
	public String queryAssChangeSourceLand(Map<String, Object> entityMap) {
		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		if (sysPage.getTotal() == -1) {

			List<AssChangeSourceLand> list = (List<AssChangeSourceLand>) assChangeSourceLandMapper
					.query(entityMap);

			return ChdJson.toJson(list);

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

			List<AssChangeSourceLand> list = (List<AssChangeSourceLand>) assChangeSourceLandMapper
					.query(entityMap, rowBounds);

			PageInfo page = new PageInfo(list);

			return ChdJson.toJson(list, page.getTotal());

		}
	}

	@Override
	public String saveAssChangeSourceLand(Map<String, Object> entityMap) {
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
				detailVo.put("change_no", entityMap.get("change_no"));
				detailVo.put("ass_card_no", entityMap.get("ass_card_no"));
				detailVo.put("source_id", String.valueOf(detailVo.get("source_id")));

				if (detailVo.get("old_price") != null && !detailVo.get("old_price").equals("")) {
					detailVo.put("old_price", detailVo.get("old_price") + "");
				} else {
					detailVo.put("old_price", "0");
				}

				if (detailVo.get("change_price") != null && !detailVo.get("change_price").equals("")) {
					change_price = change_price + Double.parseDouble(detailVo.get("change_price").toString());
					detailVo.put("change_price", detailVo.get("change_price") + "");
				} else {
					detailVo.put("change_price", "0");
				}

				if (detailVo.get("price") != null && !detailVo.get("price").equals("")) {
					price = price + Double.parseDouble(detailVo.get("price").toString());
					detailVo.put("price", detailVo.get("price") + "");
				} else {
					detailVo.put("price", "0");
				}

				if (detailVo.get("note") != null && !detailVo.get("note").equals("")) {
					detailVo.put("note", detailVo.get("note"));
				} else {
					detailVo.put("note", "");
				}

				listVo.add(detailVo);
			}

			assChangeSourceLandMapper.delete(entityMap);
			assChangeSourceLandMapper.addBatch(listVo);

			entityMap.put("change_price", change_price + "");
			entityMap.put("price", price + "");

			assChangeDetailLandMapper.updatePriceBySource(entityMap);

			return "{\"msg\":\"保存成功.\",\"state\":\"true\",\"change_price\":\"" + change_price + "\",\"price\":\""
					+ price + "\"}";
		} catch (NumberFormatException e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		} catch (DataAccessException e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}
	}

	@Override
	public String updateConfirm(List<Map<String, Object>> entityMap) throws DataAccessException {
		List<Map<String, Object>> cardList = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> reSourceAddList = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> reSourceUpdateList = new ArrayList<Map<String, Object>>();
		
		List<Map<String, Object>> payStageListVo = new ArrayList<Map<String, Object>>();
		MatPayTerm matPayTerm = null;
		Map<String,Object> payTermMap = new HashMap<String, Object>();
		payTermMap.put("group_id", SessionManager.getGroupId());
		payTermMap.put("hos_id", SessionManager.getHosId());
		payTermMap.put("copy_code", SessionManager.getCopyCode());
		if(matPayTermMapper.queryPayTerm(payTermMap) != null && matPayTermMapper.queryPayTerm(payTermMap).size() > 0){
			matPayTerm = (MatPayTerm) matPayTermMapper.queryPayTerm(payTermMap).get(0);
		}else{
			matPayTerm = new MatPayTerm();
			matPayTerm.setPay_term_id((long)1);
		}
		try {
			for (Map<String, Object> map : entityMap) {
				List<Map<String, Object>> detailMap = assChangeDetailLandMapper.queryByChangeNoMap(map);
				
				AssChangeLand assChangeLand = assChangeLandMapper.queryByCode(map);
				
				if (detailMap == null || detailMap.size() == 0) {
					TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
					return "{\"warn\":\"存在没有明细数据的单据,不能确认.\"}";
				}
				for (Map<String, Object> detail : detailMap) {
					List<Map<String, Object>> sourceMap = assChangeSourceLandMapper
							.queryChangeSourceByAssCardNo(detail);
					if (sourceMap == null || sourceMap.size() == 0) {
						TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
						return "{\"warn\":\"存在没有资金来源的单据,不能确认.\"}";
					}
					cardList.add(detail);

					for (Map<String, Object> source : sourceMap) {
						AssResourceLand assResourceLand = assResourceLandMapper.queryByCode(source);
						if (assResourceLand != null) {
							reSourceUpdateList.add(source);
						} else {
							reSourceAddList.add(source);
						}
					}
					
					Map<String,Object> payMap = new HashMap<String, Object>();
					payMap.put("group_id", SessionManager.getGroupId());
					payMap.put("hos_id", SessionManager.getHosId());
					payMap.put("copy_code", SessionManager.getCopyCode());
					payMap.put("ass_card_no", detail.get("ass_card_no"));
					payMap.put("stage_no", assPayStageLandMapper.queryStageLandMaxNo(detail) + 1);
					payMap.put("stage_name", "原值变动");
					payMap.put("pay_term_id", matPayTerm.getPay_term_id());
					payMap.put("pay_plan_date", DateUtil.dateToString(new Date(),"yyyy-MM-dd"));
					payMap.put("pay_plan_percent", "0");
					payMap.put("pay_plan_money", detail.get("change_price").toString());
					payMap.put("pay_money", "0");
					payMap.put("unpay_money", detail.get("change_price").toString());
					payMap.put("ven_id", assChangeLand.getVen_id() == null ? "" :assChangeLand.getVen_id());
					payMap.put("ven_no", assChangeLand.getVen_no() == null ? "" :assChangeLand.getVen_no());
					payMap.put("note", "原值变动");
					payMap.put("is_pre", "0");
					payMap.put("bill_money", "0");
					payMap.put("unbill_money", detail.get("change_price").toString());
					payStageListVo.add(payMap);
				}
			}

			assChangeLandMapper.updateConfirm(entityMap);// 更新主表状态
			assCardLandMapper.updatePriceConfirm(cardList);// 更新卡片原值
			if (reSourceUpdateList.size() > 0) {
				assResourceLandMapper.updateBatchByChange(reSourceUpdateList);
			}

			if (reSourceAddList.size() > 0) {
				assResourceLandMapper.addBatchByChange(reSourceAddList);
			}
			
			if(payStageListVo.size() > 0){
				assPayStageLandMapper.addBatch(payStageListVo);
			}
			return "{\"msg\":\"确认成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}
	}
	
	@Override
	public Map<String,Object> printAssChangeLandData(Map<String, Object> map)throws DataAccessException {
		
		try{
			Map<String,Object> reMap=new HashMap<String,Object>();
			WebApplicationContext context = ContextLoader.getCurrentWebApplicationContext();
			AssChangeLandMapper assChangeLandMapper = (AssChangeLandMapper)context.getBean("assChangeLandMapper");
			
			//查询凭证主表
			List<Map<String,Object>> mainList=assChangeLandMapper.queryAssChangeLandByAssInNo(map);
					
			//查询凭证明细表
			List<Map<String,Object>> detailList=assChangeLandMapper.queryAssChangeLandDetailByAssInNo(map);
			
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
	public List<String> queryAssChangeLandStates(Map<String, Object> mapVo) throws DataAccessException {
		
		return assChangeLandMapper.queryAssChangeLandStates(mapVo);
	}
	
}
