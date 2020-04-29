/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */

package com.chd.hrp.ass.serviceImpl.pay.back;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.SysPage;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.hrp.ass.dao.bill.back.AssBackBillStageMapper;
import com.chd.hrp.ass.dao.pay.back.AssBackPayDetailMapper;
import com.chd.hrp.ass.dao.pay.back.AssBackPayMainMapper;
import com.chd.hrp.ass.dao.pay.back.AssBackPayStageMapper;
import com.chd.hrp.ass.entity.bill.back.AssBackBillStage;
import com.chd.hrp.ass.entity.pay.back.AssBackPayDetail;
import com.chd.hrp.ass.entity.pay.back.AssBackPayStage;
import com.chd.hrp.ass.service.pay.back.AssBackPayStageService;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Description: tabledesc
 * @Table: ASS_BACK_PAY_STAGE
 * @Author: bell
 * @email: bell@e-tonggroup.com
 * @Version: 1.0
 */

@Service("assBackPayStageService")
public class AssBackPayStageServiceImpl implements AssBackPayStageService {

	private static Logger logger = Logger.getLogger(AssBackPayStageServiceImpl.class);
	// 引入DAO操作
	@Resource(name = "assBackPayStageMapper")
	private final AssBackPayStageMapper assBackPayStageMapper = null;

	@Resource(name = "assBackBillStageMapper")
	private final AssBackBillStageMapper assBackBillStageMapper = null;

	@Resource(name = "assBackPayDetailMapper")
	private final AssBackPayDetailMapper assBackPayDetailMapper = null;

	@Resource(name = "assBackPayMainMapper")
	private final AssBackPayMainMapper assBackPayMainMapper = null;

	/**
	 * @Description 添加tabledesc<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String add(Map<String, Object> entityMap) throws DataAccessException {

		// 获取对象tabledesc
		AssBackPayStage assBackPayStage = queryByCode(entityMap);

		if (assBackPayStage != null) {

			return "{\"error\":\"数据重复,请重新添加.\"}";

		}

		try {

			int state = assBackPayStageMapper.add(entityMap);

			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 add\"}";

		}

	}

	/**
	 * @Description 批量添加tabledesc<BR>
	 * @param entityList
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String addBatch(List<Map<String, Object>> entityList) throws DataAccessException {

		try {

			assBackPayStageMapper.addBatch(entityList);

			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addBatch\"}";

		}

	}

	/**
	 * @Description 更新tabledesc<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String update(Map<String, Object> entityMap) throws DataAccessException {

		try {

			int state = assBackPayStageMapper.update(entityMap);

			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 update\"}";

		}

	}

	/**
	 * @Description 批量更新tabledesc<BR>
	 * @param entityList
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String updateBatch(List<Map<String, Object>> entityList) throws DataAccessException {

		try {

			assBackPayStageMapper.updateBatch(entityList);

			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 updateBatch\"}";

		}

	}

	/**
	 * @Description 删除tabledesc<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String delete(Map<String, Object> entityMap) throws DataAccessException {

		try {

			int state = assBackPayStageMapper.delete(entityMap);

			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 delete\"}";

		}

	}

	/**
	 * @Description 批量删除tabledesc<BR>
	 * @param entityList
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String deleteBatch(List<Map<String, Object>> entityList) throws DataAccessException {
		try {
			Map<String, Object> mapVo = new HashMap<String, Object>();
			mapVo.put("group_id", entityList.get(0).get("group_id"));
			mapVo.put("hos_id", entityList.get(0).get("hos_id"));
			mapVo.put("copy_code", entityList.get(0).get("copy_code"));
			mapVo.put("pay_no", entityList.get(0).get("pay_no"));
			mapVo.put("bill_no", entityList.get(0).get("bill_no"));
			List<AssBackPayStage> backPayStageList = (List<AssBackPayStage>) assBackPayStageMapper.query(mapVo);
			Double bill_money = 0.0;
			for (AssBackPayStage assBackPayStage : backPayStageList) {
				bill_money = bill_money + assBackPayStage.getPay_money();
			}
			mapVo.put("bill_money", bill_money + "");
			assBackPayDetailMapper.updateBillMoney(mapVo);

			Map<String, Object> detMapVo = new HashMap<String, Object>();
			detMapVo.put("group_id", entityList.get(0).get("group_id"));
			detMapVo.put("hos_id", entityList.get(0).get("hos_id"));
			detMapVo.put("copy_code", entityList.get(0).get("copy_code"));
			detMapVo.put("pay_no", entityList.get(0).get("pay_no"));

			List<AssBackPayDetail> detailList = (List<AssBackPayDetail>) assBackPayDetailMapper.query(detMapVo);
			Double pay_money = 0.0;
			for (AssBackPayDetail temp : detailList) {
				pay_money += temp.getBill_money();
			}
			detMapVo.put("pay_money", pay_money + "");

			assBackPayMainMapper.updatePayMoney(detMapVo);

			return "{\"msg\":\"保存成功.\",\"state\":\"true\",\"bill_money\":\"" + bill_money + "\",\"pay_money\":\""
					+ pay_money + "\"}";

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}
	}

	/**
	 * @Description 添加tabledesc<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String addOrUpdate(Map<String, Object> entityMap) throws DataAccessException {
		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();
		try {
			List<Map> detail = ChdJson.fromJsonAsList(Map.class, entityMap.get("ParamVo").toString());
			for (Map<String, Object> detailVo : detail) {
				if (detailVo.get("pay_code") == null || "".equals(detailVo.get("pay_code"))) {
					continue;
				}

				detailVo.put("group_id", entityMap.get("group_id"));
				detailVo.put("hos_id", entityMap.get("hos_id"));
				detailVo.put("copy_code", entityMap.get("copy_code"));
				detailVo.put("pay_no", entityMap.get("pay_no"));
				detailVo.put("bill_no", entityMap.get("bill_no"));
				detailVo.put("pay_code", detailVo.get("pay_code"));
				if (detailVo.get("pay_money") != null && !detailVo.get("pay_money").equals("")) {
					detailVo.put("pay_money", detailVo.get("pay_money"));
				} else {
					detailVo.put("pay_money", "0");
				}

				if (detailVo.get("note") != null && !detailVo.get("note").equals("")) {
					detailVo.put("note", detailVo.get("note"));
				} else {
					detailVo.put("note", "");
				}
				listVo.add(detailVo);
			}
			assBackPayStageMapper.delete(entityMap);
			assBackPayStageMapper.addBatch(listVo);

			Map<String, Object> mapVo = new HashMap<String, Object>();
			mapVo.put("group_id", entityMap.get("group_id"));
			mapVo.put("hos_id", entityMap.get("hos_id"));
			mapVo.put("copy_code", entityMap.get("copy_code"));
			mapVo.put("pay_no", entityMap.get("pay_no"));
			mapVo.put("bill_no", entityMap.get("bill_no"));
			List<AssBackPayStage> payBackStageList = (List<AssBackPayStage>) assBackPayStageMapper.query(mapVo);
			Double bill_money = 0.0;
			for (AssBackPayStage assBackPayStage : payBackStageList) {
				bill_money = bill_money + assBackPayStage.getPay_money();
			}
			mapVo.put("bill_money", bill_money + "");
			assBackPayDetailMapper.updateBillMoney(mapVo);

			Map<String, Object> detMapVo = new HashMap<String, Object>();
			detMapVo.put("group_id", entityMap.get("group_id"));
			detMapVo.put("hos_id", entityMap.get("hos_id"));
			detMapVo.put("copy_code", entityMap.get("copy_code"));
			detMapVo.put("pay_no", entityMap.get("pay_no"));

			List<AssBackPayDetail> detailList = (List<AssBackPayDetail>) assBackPayDetailMapper.query(detMapVo);
			Double pay_money = 0.0;
			for (AssBackPayDetail temp : detailList) {
				pay_money += temp.getBill_money();
			}
			detMapVo.put("pay_money", pay_money + "");

			assBackPayMainMapper.updatePayMoney(detMapVo);

			return "{\"msg\":\"保存成功.\",\"state\":\"true\",\"bill_money\":\"" + bill_money + "\",\"pay_money\":\""
					+ pay_money + "\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}

	}

	/**
	 * @Description 查询结果集tabledesc<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String query(Map<String, Object> entityMap) throws DataAccessException {

		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		if (sysPage.getTotal() == -1) {

			List<AssBackPayStage> list = (List<AssBackPayStage>) assBackPayStageMapper.query(entityMap);

			return ChdJson.toJson(list);

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

			List<AssBackPayStage> list = (List<AssBackPayStage>) assBackPayStageMapper.query(entityMap, rowBounds);

			PageInfo page = new PageInfo(list);

			return ChdJson.toJson(list, page.getTotal());

		}

	}

	/**
	 * @Description 获取对象tabledesc<BR>
	 * @param entityMap<BR>
	 *            参数为主键
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public <T> T queryByCode(Map<String, Object> entityMap) throws DataAccessException {
		return assBackPayStageMapper.queryByCode(entityMap);
	}

	/**
	 * @Description 获取tabledesc<BR>
	 * @param entityMap<BR>
	 *            参数为要检索的字段
	 * @return AssBackPayStage
	 * @throws DataAccessException
	 */
	@Override
	public <T> T queryByUniqueness(Map<String, Object> entityMap) throws DataAccessException {
		return assBackPayStageMapper.queryByUniqueness(entityMap);
	}

	/**
	 * @Description 获取tabledesc<BR>
	 * @param entityMap<BR>
	 *            参数为要检索的字段
	 * @return List<AssBackPayStage>
	 * @throws DataAccessException
	 */
	@Override
	public List<?> queryExists(Map<String, Object> entityMap) throws DataAccessException {
		return assBackPayStageMapper.queryExists(entityMap);
	}

	@Override
	public String queryBackBillStageDetail(Map<String, Object> entityMap) throws DataAccessException {
		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		if (sysPage.getTotal() == -1) {
			List<AssBackBillStage> list = (List<AssBackBillStage>) assBackBillStageMapper
					.queryBackBillStageByBillNo(entityMap);
			return ChdJson.toJson(list);
		} else {
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			List<AssBackBillStage> list = (List<AssBackBillStage>) assBackBillStageMapper
					.queryBackBillStageByBillNo(entityMap, rowBounds);
			PageInfo page = new PageInfo(list);
			return ChdJson.toJson(list, page.getTotal());

		}
	}

	@Override
	public List<Map<String, Object>> queryByPayNo(Map<String, Object> entityMap) throws DataAccessException {
		return assBackPayStageMapper.queryByPayNo(entityMap);
	}

}
