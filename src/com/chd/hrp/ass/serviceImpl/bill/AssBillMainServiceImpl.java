/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */

package com.chd.hrp.ass.serviceImpl.bill;

import java.util.ArrayList;
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

import com.alibaba.fastjson.JSONObject;
import com.chd.base.SysPage;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.base.util.DateUtil;
import com.chd.hrp.ass.dao.HrpAssSelectMapper;
import com.chd.hrp.ass.dao.bill.AssBillDetailMapper;
import com.chd.hrp.ass.dao.bill.AssBillMainMapper;
import com.chd.hrp.ass.dao.bill.AssBillStageMapper;
import com.chd.hrp.ass.dao.in.AssInMainGeneralMapper;
import com.chd.hrp.ass.dao.in.AssInMainOtherMapper;
import com.chd.hrp.ass.dao.in.AssInMainSpecialMapper;
import com.chd.hrp.ass.dao.pay.AssPayStageGeneralMapper;
import com.chd.hrp.ass.dao.pay.AssPayStageHouseMapper;
import com.chd.hrp.ass.dao.pay.AssPayStageInassetsMapper;
import com.chd.hrp.ass.dao.pay.AssPayStageLandMapper;
import com.chd.hrp.ass.dao.pay.AssPayStageOtherMapper;
import com.chd.hrp.ass.dao.pay.AssPayStageSpecialMapper;
import com.chd.hrp.ass.entity.bill.AssBillMain;
import com.chd.hrp.ass.service.base.AssBaseService;
import com.chd.hrp.ass.service.bill.AssBillMainService;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Description: 发票主表
 * @Table: ASS_BILL_MAIN
 * @Author: bell
 * @email: bell@e-tonggroup.com
 * @Version: 1.0
 */

@Service("assBillMainService")
public class AssBillMainServiceImpl implements AssBillMainService {

	private static Logger logger = Logger.getLogger(AssBillMainServiceImpl.class);
	// 引入DAO操作
	@Resource(name = "assBillMainMapper")
	private final AssBillMainMapper assBillMainMapper = null;

	@Resource(name = "assBillStageMapper")
	private final AssBillStageMapper assBillStageMapper = null;

	@Resource(name = "assBillDetailMapper")
	private final AssBillDetailMapper assBillDetailMapper = null;

	@Resource(name = "assPayStageSpecialMapper")
	private final AssPayStageSpecialMapper assPayStageSpecialMapper = null;

	@Resource(name = "assPayStageOtherMapper")
	private final AssPayStageOtherMapper assPayStageOtherMapper = null;

	@Resource(name = "assPayStageLandMapper")
	private final AssPayStageLandMapper assPayStageLandMapper = null;

	@Resource(name = "assPayStageInassetsMapper")
	private final AssPayStageInassetsMapper assPayStageInassetsMapper = null;

	@Resource(name = "assPayStageHouseMapper")
	private final AssPayStageHouseMapper assPayStageHouseMapper = null;

	@Resource(name = "assPayStageGeneralMapper")
	private final AssPayStageGeneralMapper assPayStageGeneralMapper = null;

	@Resource(name = "assBaseService")
	private final AssBaseService assBaseService = null;

	@Resource(name = "hrpAssSelectMapper")
	private final HrpAssSelectMapper hrpAssSelectMapper = null;

	@Resource(name = "assInMainSpecialMapper")
	private final AssInMainSpecialMapper assInMainSpecialMapper = null;

	@Resource(name = "assInMainOtherMapper")
	private final AssInMainOtherMapper assInMainOtherMapper = null;

	@Resource(name = "assInMainGeneralMapper")
	private final AssInMainGeneralMapper assInMainGeneralMapper = null;

	/**
	 * @Description 添加发票主表<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String add(Map<String, Object> entityMap) throws DataAccessException {

		// 获取对象发票主表
		AssBillMain assBillMain = queryByCode(entityMap);

		if (assBillMain != null) {

			return "{\"error\":\"数据重复,请重新添加.\"}";

		}

		try {

			int state = assBillMainMapper.add(entityMap);

			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 add\"}";

		}

	}

	/**
	 * @Description 批量添加发票主表<BR>
	 * @param entityList
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String addBatch(List<Map<String, Object>> entityList) throws DataAccessException {

		try {

			assBillMainMapper.addBatch(entityList);

			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addBatch\"}";

		}

	}

	/**
	 * @Description 更新发票主表<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String update(Map<String, Object> entityMap) throws DataAccessException {

		try {

			int state = assBillMainMapper.update(entityMap);

			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 update\"}";

		}

	}

	/**
	 * @Description 批量更新发票主表<BR>
	 * @param entityList
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String updateBatch(List<Map<String, Object>> entityList) throws DataAccessException {

		try {

			assBillMainMapper.updateBatch(entityList);

			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 updateBatch\"}";

		}

	}

	/**
	 * @Description 删除发票主表<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String delete(Map<String, Object> entityMap) throws DataAccessException {

		try {

			int state = assBillMainMapper.delete(entityMap);

			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 delete\"}";

		}

	}

	/**
	 * @Description 批量删除发票主表<BR>
	 * @param entityList
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String deleteBatch(List<Map<String, Object>> entityList) throws DataAccessException {
		try {

			for (Map<String, Object> mapVo : entityList) {
				List<Map<String, Object>> stageList = assBillStageMapper.queryByBillNo(mapVo);

				for (Map<String, Object> detailVo : stageList) {

					detailVo.put("group_id", detailVo.get("group_id"));
					detailVo.put("hos_id", detailVo.get("hos_id"));
					detailVo.put("copy_code", detailVo.get("copy_code"));
					detailVo.put("bill_no", detailVo.get("bill_no"));
					detailVo.put("naturs_code", detailVo.get("naturs_code"));
					detailVo.put("ass_card_no", detailVo.get("ass_card_no"));
					detailVo.put("stage_no", detailVo.get("stage_no"));

				}
			}

			assBillStageMapper.deleteBatch(entityList);
			assBillDetailMapper.deleteBatch(entityList);
			assBillMainMapper.deleteBatch(entityList);

			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());

		}
	}

	/**
	 * @Description 添加发票主表<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String addOrUpdate(Map<String, Object> entityMap) throws DataAccessException {
		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> billPayStageList = new ArrayList<Map<String, Object>>();

		Map<String, Object> mapVo = new HashMap<String, Object>();
		mapVo.put("group_id", entityMap.get("group_id"));
		mapVo.put("hos_id", entityMap.get("hos_id"));
		mapVo.put("copy_code", entityMap.get("copy_code"));
		mapVo.put("bill_no", entityMap.get("bill_no"));

		// 通过入库日期检索卡片是否存在
		Map<String, Object> vCreateDateMap = new HashMap<String, Object>();
		vCreateDateMap.put("group_id", entityMap.get("group_id"));
		vCreateDateMap.put("hos_id", entityMap.get("hos_id"));
		vCreateDateMap.put("copy_code", entityMap.get("copy_code"));
		// vCreateDateMap.put("use_state", "1,2,3,4,5");
		vCreateDateMap.put("is_payment", "y");
		vCreateDateMap.put("in_date", entityMap.get("create_date"));

		// 通过供应商检索卡片是否存在
		Map<String, Object> vVenMap = null;
		if (entityMap.get("ven_id") != null && !entityMap.get("ven_id").equals("")) {
			vVenMap = new HashMap<String, Object>();
			vVenMap.put("group_id", entityMap.get("group_id"));
			vVenMap.put("hos_id", entityMap.get("hos_id"));
			vVenMap.put("copy_code", entityMap.get("copy_code"));
			// vVenMap.put("use_state", "1,2,3,4,5");
			vVenMap.put("is_payment", "y");
			vVenMap.put("ven_id_bill", entityMap.get("ven_id"));
		}
		// 通过合同检索卡片是否存在
		Map<String, Object> vContractMap = null;
		if (entityMap.get("pact_code") != null && !entityMap.get("pact_code").equals("")) {
			vContractMap = new HashMap<String, Object>();
			vContractMap.put("group_id", entityMap.get("group_id"));
			vContractMap.put("hos_id", entityMap.get("hos_id"));
			vContractMap.put("copy_code", entityMap.get("copy_code"));
			// vContractMap.put("use_state", "1,2,3,4,5");
			vContractMap.put("is_payment", "y");
			vContractMap.put("pact_code", entityMap.get("pact_code"));
		}

		entityMap.put("state", "0");

		List<AssBillMain> list = (List<AssBillMain>) assBillMainMapper.queryExists(mapVo);
		try {
			if (list.size() > 0) {
				assBillMainMapper.update(entityMap);
			} else {
				if (entityMap.get("create_date") != null && !"".equals(entityMap.get("create_date"))) {
					entityMap.put("year", entityMap.get("create_date").toString().substring(0, 4));
					entityMap.put("month", entityMap.get("create_date").toString().substring(5, 7));
				}
				entityMap.put("bill_table", "ASS_BILL_MAIN");
				String bill_no = assBaseService.getBillNOSeqNo(entityMap);
				entityMap.put("bill_no", bill_no);
				assBillMainMapper.add(entityMap);
				assBaseService.updateAssBillNoMaxNo(entityMap);
			}
			List<Map> detail = ChdJson.fromJsonAsList(Map.class, entityMap.get("ParamVo").toString());

			List<Map<String, Object>> billStageList = assBillDetailMapper.queryByBillNo(mapVo);

			List<Map<String, Object>> delBillStageList = new ArrayList<Map<String, Object>>();
			for (Map<String, Object> det : billStageList) {
				boolean falg = false;
				for (Map<String, Object> temp : detail) {
					if (temp.get("naturs_code") == null || "".equals(temp.get("naturs_code"))) {
						continue;
					}
					if (det.get("ass_card_no").toString().equals(temp.get("ass_card_no").toString())
							&& det.get("naturs_code").toString().equals(temp.get("naturs_code").toString())) {
						falg = true;
						break;
					}
				}
				if (falg == false) {
					delBillStageList.add(det);
				}
			}

			/**
			 * 如果更换卡片，先删除原有分期付款
			 */
			if (delBillStageList.size() > 0) {

				assBillStageMapper.deleteBatch(delBillStageList);

			}

			boolean flag = true;
			boolean flag1 = true;
			boolean flag2 = true;
			for (Map<String, Object> detailVo : detail) {
				if (detailVo.get("naturs_code") == null || "".equals(detailVo.get("naturs_code"))) {
					continue;
				}

				detailVo.put("group_id", entityMap.get("group_id"));
				detailVo.put("hos_id", entityMap.get("hos_id"));
				detailVo.put("copy_code", entityMap.get("copy_code"));
				detailVo.put("bill_no", entityMap.get("bill_no"));
				detailVo.put("naturs_code", detailVo.get("naturs_code"));
				detailVo.put("ass_card_no", detailVo.get("ass_card_no"));
				detailVo.put("ass_in_no", detailVo.get("ass_in_no"));
				vCreateDateMap.put("ass_card_no", detailVo.get("ass_card_no"));

				if (detailVo.get("naturs_code").toString().equals("01")) {
					Integer createDateExists = hrpAssSelectMapper.queryAssExistsHouseCard(vCreateDateMap);
					if (createDateExists == 0) {
						flag = false;
						break;
					}
					if (entityMap.get("ven_id") != null && !entityMap.get("ven_id").equals("")) {
						vVenMap.put("ass_card_no", detailVo.get("ass_card_no"));
						Integer venExists = hrpAssSelectMapper.queryAssExistsHouseCard(vVenMap);
						if (venExists == 0) {
							flag1 = false;
							break;
						}
					}

					if (entityMap.get("pact_code") != null && !entityMap.get("pact_code").equals("")) {
						vContractMap.put("ass_card_no", detailVo.get("ass_card_no"));
						Integer contractExists = hrpAssSelectMapper.queryAssExistsHouseCard(vContractMap);
						if (contractExists == 0) {
							flag2 = false;
							break;
						}
					}

				} else if (detailVo.get("naturs_code").toString().equals("02")) {
					Integer createDateExists = hrpAssSelectMapper.queryAssExistsSpecialCard(vCreateDateMap);
					if (createDateExists == 0) {
						flag = false;
						break;
					}
					if (entityMap.get("ven_id") != null && !entityMap.get("ven_id").equals("")) {
						vVenMap.put("ass_card_no", detailVo.get("ass_card_no"));
						Integer venExists = hrpAssSelectMapper.queryAssExistsSpecialCard(vVenMap);
						if (venExists == 0) {
							flag1 = false;
							break;
						}
					}

					if (entityMap.get("pact_code") != null && !entityMap.get("pact_code").equals("")) {
						vContractMap.put("ass_card_no", detailVo.get("ass_card_no"));
						Integer contractExists = hrpAssSelectMapper.queryAssExistsSpecialCard(vContractMap);
						if (contractExists == 0) {
							flag2 = false;
							break;
						}
					}

				} else if (detailVo.get("naturs_code").toString().equals("03")) {
					Integer createDateExists = hrpAssSelectMapper.queryAssExistsGeneralCard(vCreateDateMap);
					if (createDateExists == 0) {
						flag = false;
						break;
					}
					if (entityMap.get("ven_id") != null && !entityMap.get("ven_id").equals("")) {
						vVenMap.put("ass_card_no", detailVo.get("ass_card_no"));
						Integer venExists = hrpAssSelectMapper.queryAssExistsGeneralCard(vVenMap);
						if (venExists == 0) {
							flag1 = false;
							break;
						}
					}

					if (entityMap.get("pact_code") != null && !entityMap.get("pact_code").equals("")) {
						vContractMap.put("ass_card_no", detailVo.get("ass_card_no"));
						Integer contractExists = hrpAssSelectMapper.queryAssExistsGeneralCard(vContractMap);
						if (contractExists == 0) {
							flag2 = false;
							break;
						}
					}
				} else if (detailVo.get("naturs_code").toString().equals("04")) {
					Integer createDateExists = hrpAssSelectMapper.queryAssExistsOtherCard(vCreateDateMap);
					if (createDateExists == 0) {
						flag = false;
						break;
					}
					if (entityMap.get("ven_id") != null && !entityMap.get("ven_id").equals("")) {
						vVenMap.put("ass_card_no", detailVo.get("ass_card_no"));
						Integer venExists = hrpAssSelectMapper.queryAssExistsOtherCard(vVenMap);
						if (venExists == 0) {
							flag1 = false;
							break;
						}
					}

					if (entityMap.get("pact_code") != null && !entityMap.get("pact_code").equals("")) {
						vContractMap.put("ass_card_no", detailVo.get("ass_card_no"));
						Integer contractExists = hrpAssSelectMapper.queryAssExistsOtherCard(vContractMap);
						if (contractExists == 0) {
							flag2 = false;
							break;
						}
					}
				} else if (detailVo.get("naturs_code").toString().equals("05")) {
					Integer createDateExists = hrpAssSelectMapper.queryAssExistsInassetsCard(vCreateDateMap);
					if (createDateExists == 0) {
						flag = false;
						break;
					}
					if (entityMap.get("ven_id") != null && !entityMap.get("ven_id").equals("")) {
						vVenMap.put("ass_card_no", detailVo.get("ass_card_no"));
						Integer venExists = hrpAssSelectMapper.queryAssExistsInassetsCard(vVenMap);
						if (venExists == 0) {
							flag1 = false;
							break;
						}
					}

					if (entityMap.get("pact_code") != null && !entityMap.get("pact_code").equals("")) {
						vContractMap.put("ass_card_no", detailVo.get("ass_card_no"));
						Integer contractExists = hrpAssSelectMapper.queryAssExistsInassetsCard(vContractMap);
						if (contractExists == 0) {
							flag2 = false;
							break;
						}
					}
				} else if (detailVo.get("naturs_code").toString().equals("06")) {
					Integer createDateExists = hrpAssSelectMapper.queryAssExistsLandCard(vCreateDateMap);
					if (createDateExists == 0) {
						flag = false;
						break;
					}
					if (entityMap.get("ven_id") != null && !entityMap.get("ven_id").equals("")) {
						vVenMap.put("ass_card_no", detailVo.get("ass_card_no"));
						Integer venExists = hrpAssSelectMapper.queryAssExistsLandCard(vVenMap);
						if (venExists == 0) {
							flag1 = false;
							break;
						}
					}

					if (entityMap.get("pact_code") != null && !entityMap.get("pact_code").equals("")) {
						vContractMap.put("ass_card_no", detailVo.get("ass_card_no"));
						Integer contractExists = hrpAssSelectMapper.queryAssExistsLandCard(vContractMap);
						if (contractExists == 0) {
							flag2 = false;
							break;
						}
					}
				}

				if (detailVo.get("bill_money") != null && !detailVo.get("bill_money").equals("")) {
					detailVo.put("bill_money", detailVo.get("bill_money") + "");
				} else {
					detailVo.put("bill_money", "0");
				}

				if (detailVo.get("note") != null && !detailVo.get("note").equals("")) {
					detailVo.put("note", detailVo.get("note"));
				} else {
					detailVo.put("note", "");
				}

				listVo.add(detailVo);
			}

			if (!flag) {
				TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
				return "{\"warn\":\"存在尚未入账的卡片不能保存.\"}";
			}
			if (!flag1) {
				TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
				return "{\"warn\":\"存在非本供应商的卡片不能保存.\"}";
			}
			if (!flag2) {
				TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
				return "{\"warn\":\"存在尚非本合同的卡片不能保存.\"}";
			}

			assBillDetailMapper.delete(entityMap);
			assBillDetailMapper.addBatch(listVo);

			if (billPayStageList.size() > 0) {
				assBillStageMapper.deleteBatch(billPayStageList);
				assBillStageMapper.addBatch(billPayStageList);
			}

			for (Map<String, Object> cardMap : listVo) {
				Map<String, Object> para = new HashMap<String, Object>();
				para.put("group_id", cardMap.get("group_id"));
				para.put("hos_id", cardMap.get("hos_id"));
				para.put("copy_code", cardMap.get("copy_code"));
				para.put("ass_in_no", cardMap.get("ass_in_no"));
				para.put("invoice_no", entityMap.get("invoice_no"));

				if (cardMap.get("ass_in_no") != null && !cardMap.get("ass_in_no").equals("")) {
					if (cardMap.get("naturs_code").toString().equals("02")) {
						assInMainSpecialMapper.updateAssInMainBillNo(para);
					} else if (cardMap.get("naturs_code").toString().equals("03")) {
						assInMainGeneralMapper.updateAssInMainBillNo(para);
					} else if (cardMap.get("naturs_code").toString().equals("04")) {
						assInMainOtherMapper.updateAssInMainBillNo(para);
					}
				}

			}

			return "{\"msg\":\"保存成功.\",\"state\":\"true\",\"bill_no\":\"" + entityMap.get("bill_no") + "\"}";

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());

		}

	}

	/**
	 * @Description 查询结果集发票主表<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String query(Map<String, Object> entityMap) throws DataAccessException {

		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		if (sysPage.getTotal() == -1) {

			List<AssBillMain> list = (List<AssBillMain>) assBillMainMapper.query(entityMap);

			return ChdJson.toJson(list);

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

			List<AssBillMain> list = (List<AssBillMain>) assBillMainMapper.query(entityMap, rowBounds);

			PageInfo page = new PageInfo(list);

			return ChdJson.toJson(list, page.getTotal());

		}

	}

	/**
	 * @Description 付款发票打印
	 * @param entityMap
	 * @return String
	 * @throws DataAccessException
	 */
	@Override
	public Map<String, Object> queryAssBillDY(Map<String, Object> map) throws DataAccessException {

		try {
			Map<String, Object> reMap = new HashMap<String, Object>();
			WebApplicationContext context = ContextLoader.getCurrentWebApplicationContext();
			AssBillMainMapper assBillMainMapper = (AssBillMainMapper) context.getBean("assBillMainMapper");
			if ("1".equals(String.valueOf(map.get("p_num")))) {
				// 付款发票打印模板主表
				List<Map<String, Object>> mainList = assBillMainMapper.queryAssBillMainBatch(map);

				// 付款发票打印模板从表
				List<Map<String, Object>> detailList = assBillMainMapper.queryAssBill_detail(map);

				reMap.put("main", mainList);
				reMap.put("detail", detailList);
			} else {
				Map<String, Object> mainList = assBillMainMapper.querAssBillByPrint(map);

				// 付款发票打印模板从表
				List<Map<String, Object>> detailList = assBillMainMapper.queryAssBill_detail(map);
				reMap.put("main", mainList);
				reMap.put("detail", detailList);
			}

			return reMap;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}

	}

	/**
	 * @Description 获取对象发票主表<BR>
	 * @param entityMap<BR>
	 *            参数为主键
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public <T> T queryByCode(Map<String, Object> entityMap) throws DataAccessException {
		return assBillMainMapper.queryByCode(entityMap);
	}

	/**
	 * @Description 获取发票主表<BR>
	 * @param entityMap<BR>
	 *            参数为要检索的字段
	 * @return AssBillMain
	 * @throws DataAccessException
	 */
	@Override
	public <T> T queryByUniqueness(Map<String, Object> entityMap) throws DataAccessException {
		return assBillMainMapper.queryByUniqueness(entityMap);
	}

	/**
	 * @Description 获取发票主表<BR>
	 * @param entityMap<BR>
	 *            参数为要检索的字段
	 * @return List<AssBillMain>
	 * @throws DataAccessException
	 */
	@Override
	public List<?> queryExists(Map<String, Object> entityMap) throws DataAccessException {
		return assBillMainMapper.queryExists(entityMap);
	}

	@Override
	public String updateBillConfirm(List<Map<String, Object>> entityMap) throws DataAccessException {

		List<Map<String, Object>> listSpecial = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> listGeneral = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> listHouse = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> listOther = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> listInassets = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> listLand = new ArrayList<Map<String, Object>>();

		try {

			for (Map<String, Object> temp : entityMap) {
				List<Map<String, Object>> listBillStage = assBillStageMapper.queryByBillNo(temp);
				for (Map<String, Object> map : listBillStage) {
					map.put("bill_money", map.get("pay_plan_money"));

					if (map.get("naturs_code").equals("01")) {
						listHouse.add(map);
					} else if (map.get("naturs_code").equals("02")) {
						listSpecial.add(map);
					} else if (map.get("naturs_code").equals("03")) {
						listGeneral.add(map);
					} else if (map.get("naturs_code").equals("04")) {
						listOther.add(map);
					} else if (map.get("naturs_code").equals("05")) {
						listInassets.add(map);
					} else if (map.get("naturs_code").equals("06")) {
						listLand.add(map);
					}
				}
			}

			if (listHouse.size() > 0) {
				assPayStageHouseMapper.updateBatchBillMoney(listHouse);
			}
			if (listSpecial.size() > 0) {
				assPayStageSpecialMapper.updateBatchBillMoney(listSpecial);
			}
			if (listGeneral.size() > 0) {
				assPayStageGeneralMapper.updateBatchBillMoney(listGeneral);
			}
			if (listOther.size() > 0) {
				assPayStageOtherMapper.updateBatchBillMoney(listOther);
			}
			if (listInassets.size() > 0) {
				assPayStageInassetsMapper.updateBatchBillMoney(listInassets);
			}
			if (listLand.size() > 0) {
				assPayStageLandMapper.updateBatchBillMoney(listLand);
			}

			assBillMainMapper.updateConfirm(entityMap);
			return "{\"msg\":\"确认成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}
	}

	@Override
	public String updateReAudit(List<Map<String, Object>> listVo) {
		List<Map<String, Object>> listSpecial = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> listGeneral = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> listHouse = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> listOther = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> listInassets = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> listLand = new ArrayList<Map<String, Object>>();
		try {

			for (Map<String, Object> temp : listVo) {
				List<Map<String, Object>> listBillStage = assBillStageMapper.queryByBillNo(temp);
				for (Map<String, Object> map : listBillStage) {
					map.put("bill_money", map.get("pay_plan_money"));

					if (map.get("naturs_code").equals("01")) {
						listHouse.add(map);
					} else if (map.get("naturs_code").equals("02")) {
						listSpecial.add(map);
					} else if (map.get("naturs_code").equals("03")) {
						listGeneral.add(map);
					} else if (map.get("naturs_code").equals("04")) {
						listOther.add(map);
					} else if (map.get("naturs_code").equals("05")) {
						listInassets.add(map);
					} else if (map.get("naturs_code").equals("06")) {
						listLand.add(map);
					}
				}
			}

			if (listHouse.size() > 0) {
				assPayStageHouseMapper.updateDelBatchBillMoney(listHouse);
			}
			if (listSpecial.size() > 0) {
				assPayStageSpecialMapper.updateDelBatchBillMoney(listSpecial);
			}
			if (listGeneral.size() > 0) {
				assPayStageGeneralMapper.updateDelBatchBillMoney(listGeneral);
			}
			if (listOther.size() > 0) {
				assPayStageOtherMapper.updateDelBatchBillMoney(listOther);
			}
			if (listInassets.size() > 0) {
				assPayStageInassetsMapper.updateDelBatchBillMoney(listInassets);
			}
			if (listLand.size() > 0) {
				assPayStageLandMapper.updateDelBatchBillMoney(listLand);
			}
			assBillMainMapper.updateReAudit(listVo);
			return "{\"msg\":\"取消确认成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}
	}

	@Override
	public String queryimport(Map<String, Object> entityMap) {
		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		if (sysPage.getTotal() == -1) {

			List<AssBillMain> list = (List<AssBillMain>) assBillMainMapper.queryBillMainImport(entityMap);

			return ChdJson.toJson(list);

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

			List<AssBillMain> list = (List<AssBillMain>) assBillMainMapper.queryBillMainImport(entityMap, rowBounds);

			PageInfo page = new PageInfo(list);

			return ChdJson.toJson(list, page.getTotal());

		}
	}

	@Override
	public List<String> queryAssBillState(Map<String, Object> mapVo) throws DataAccessException {

		return assBillMainMapper.queryAssBillState(mapVo);
	}

	@Override
	public String queryAssInAndAssChange(Map<String, Object> entityMap) throws DataAccessException {
		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		if (sysPage.getTotal() == -1) {

			List<Map<String, Object>> list = (List<Map<String, Object>>) assBillMainMapper
					.queryAssInAndAssChange(entityMap);

			return ChdJson.toJson(list);

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

			List<Map<String, Object>> list = (List<Map<String, Object>>) assBillMainMapper
					.queryAssInAndAssChange(entityMap, rowBounds);

			PageInfo page = new PageInfo(list);

			return ChdJson.toJson(list, page.getTotal());

		}
	}

	@Override
	public String importInAndChange(Map<String, Object> map) throws DataAccessException {
		List<Map<String, Object>> detailListVo = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> billPayStageList = new ArrayList<Map<String, Object>>();

		try {
			/**
			 * 构建主表数据
			 */
			String cr_date = DateUtil.dateFormat(map.get("create_date"), "yyyy-MM-dd");
			map.put("year", cr_date.substring(0, 4));
			map.put("month", cr_date.substring(5, 7));
			 map.put("bill_table", "ASS_BILL_MAIN");
			String bill_no = assBaseService.getBillNOSeqNo(map);
			map.put("bill_no", bill_no);
			map.put("state", "0");
			if (map.get("invoice_no") == null || map.get("invoice_no").equals("")) {
				map.put("invoice_no", map.get("invoice_num"));
			}

			String paramVo = map.get("params").toString();

			/**
			 * 临时卡片发票栈
			 */
			Map<String, Object> cardMapVo = new HashMap<String, Object>();

			/**
			 * 构建明细数据
			 */
			for (int i = 0; i < paramVo.split(",").length; i++) {
				Map<String, Object> mapVo = new HashMap<String, Object>();
				String[] ids = paramVo.split(",")[i].split("@");

				String ass_card_no = ids[0];
				Double bill_money = Double.valueOf(ids[3]);
				String naturs_code = ids[4];
				String ass_in_no = ids[5];
				if (cardMapVo.containsKey(ass_card_no)) {
					bill_money = bill_money + Double.valueOf(cardMapVo.get("bill_money").toString());
					detailListVo.get(i - 1).put("bill_money", "-" + bill_money);
					continue;
				}
				cardMapVo.put(ass_card_no, bill_money);

				mapVo.put("group_id", map.get("group_id"));
				mapVo.put("hos_id", map.get("hos_id"));
				mapVo.put("copy_code", map.get("copy_code"));
				mapVo.put("bill_no", map.get("bill_no"));
				mapVo.put("naturs_code", naturs_code);
				mapVo.put("ass_card_no", ass_card_no);
				mapVo.put("ass_in_no", ass_in_no);
				mapVo.put("bill_money", bill_money);
				mapVo.put("note", "");
				detailListVo.add(mapVo);
			}

			/**
			 * 构建分期付款数据
			 */
			Double bill_money = 0.0;
			for (String id : paramVo.split(",")) {

				Map<String, Object> mapVo = new HashMap<String, Object>();
				String[] ids = id.split("@");

				String ass_card_no = ids[0];
				String stage_no = ids[1];
				String stage_name = ids[2];
				Double un_paymoney = Double.valueOf(ids[3]);
				String naturs_code = ids[4];

				mapVo.put("group_id", map.get("group_id"));
				mapVo.put("hos_id", map.get("hos_id"));
				mapVo.put("copy_code", map.get("copy_code"));
				mapVo.put("bill_no", map.get("bill_no"));
				mapVo.put("naturs_code", naturs_code);
				mapVo.put("ass_card_no", ass_card_no);
				mapVo.put("pay_plan_money", un_paymoney);
				mapVo.put("bill_money", un_paymoney + "");
				mapVo.put("stage_no", stage_no);
				mapVo.put("stage_name", stage_name);
				bill_money = bill_money + un_paymoney;
				billPayStageList.add(mapVo);

			}

			assBillMainMapper.add(map);
			assBaseService.updateAssBillNoMaxNo(map);
			assBillDetailMapper.addBatch(detailListVo);
			assBillStageMapper.addBatch(billPayStageList);

			map.put("bill_money", bill_money);
			assBillMainMapper.updateBillMoney(map);

			/**
			 * 更新入库单发票号
			 */
			for (Map<String, Object> cardMap : detailListVo) {
				Map<String, Object> para = new HashMap<String, Object>();
				para.put("group_id", cardMap.get("group_id"));
				para.put("hos_id", cardMap.get("hos_id"));
				para.put("copy_code", cardMap.get("copy_code"));
				para.put("ass_in_no", cardMap.get("ass_in_no"));
				para.put("invoice_no", map.get("invoice_no"));
				if (cardMap.get("naturs_code").toString().equals("02")) {
					assInMainSpecialMapper.updateAssInMainBillNo(para);
				} else if (cardMap.get("naturs_code").toString().equals("03")) {
					assInMainGeneralMapper.updateAssInMainBillNo(para);
				} else if (cardMap.get("naturs_code").toString().equals("04")) {
					assInMainOtherMapper.updateAssInMainBillNo(para);
				}
			}

			return "{\"msg\":\"引入成功.\",\"state\":\"true\",\"update_para\":\"" + map.get("group_id") + "|"
					+ map.get("hos_id") + "|" + map.get("copy_code") + "|" + bill_no + "\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}
	}

	@Override
	public String queryimports(Map<String, Object> entityMap) {
		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		if (sysPage.getTotal() == -1) {

			List<AssBillMain> list = (List<AssBillMain>) assBillMainMapper.queryBillMainImports(entityMap);

			return ChdJson.toJson(list);

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

			List<AssBillMain> list = (List<AssBillMain>) assBillMainMapper.queryBillMainImports(entityMap, rowBounds);

			PageInfo page = new PageInfo(list);

			return ChdJson.toJson(list, page.getTotal());

		}
	}

	/**
	 * 生成发票单
	 * 
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	@Override
	public String queryInMainBill(Map<String, Object> entityMap) throws DataAccessException {
		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		if (sysPage.getTotal() == -1) {

			List<Map<String, Object>> list = assBillMainMapper.queryInMainBill(entityMap);

			/*
			 * //附加 明细数据 for(Map<String,Object> item : list){ // 明细数据 查询
			 * List<Map<String,Object>> detailList=
			 * assBillMainMapper.queryAssBillDetail_Add(item); // 存放 明细数据
			 * item.put("detail",
			 * JSONObject.parseObject(ChdJson.toJson(detailList))); }
			 */

			return ChdJson.toJson(list);

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

			List<Map<String, Object>> list = assBillMainMapper.queryInMainBill(entityMap, rowBounds);

			// 附加 明细数据
			for (Map<String, Object> item : list) {
				// 明细数据 查询
				List<Map<String, Object>> detailList = assBillMainMapper.queryAssBillDetail_Add(item);
				// 存放 明细数据
				item.put("detail", JSONObject.parseObject(ChdJson.toJson(detailList)));
			}
			PageInfo page = new PageInfo(list);

			return ChdJson.toJson(list, page.getTotal());
		}

	}

	/**
	 * 生成发票单
	 */
	@Override
	public String importInAndMap(Map<String, Object> map) {

		List<Map<String, Object>> detailListVo = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> billPayStageList = new ArrayList<Map<String, Object>>();

		try {
			/**
			 * 构建主表数据
			 */
			String cr_date = DateUtil.dateFormat(map.get("create_date"), "yyyy-MM-dd");
			map.put("year", cr_date.substring(0, 4));
			map.put("month", cr_date.substring(5, 7));
			 map.put("bill_table", "ASS_PRE_BILL_MAIN");
			String bill_no = assBaseService.getBillNOSeqNo(map);
			map.put("bill_no", bill_no);
			map.put("state", "0");
			if (map.get("invoice_no") == null || map.get("invoice_no").equals("")) {
				map.put("invoice_no", map.get("invoice_num"));
			}

			String paramVo = map.get("params").toString();

			/**
			 * 临时卡片发票栈
			 */
			Map<String, Object> cardMapVo = new HashMap<String, Object>();

			/**
			 * 构建明细数据
			 */
			String[] ids = paramVo.split(",");

			String[] ass_card_nos = ids;

			for (int i = 0; i < ass_card_nos.length; i++) {
				Map<String, Object> mapVo = new HashMap<String, Object>();

				String ass_card_no = ass_card_nos[i];

				map.put("ass_card_no", ass_card_no);

				Map<String, Object> map1 = assBillMainMapper.queryAssInAndAssChanges(map);
				Double bill_money =0D;
				String naturs_code ="";
                if(map1!=null) {
                	bill_money = map1.get("unpay_money")==null?0: Double.valueOf(map1.get("unpay_money").toString());
   				    naturs_code = map1.get("naturs_code")==null?"":map1.get("naturs_code").toString();
                }

				if (cardMapVo.containsKey(ass_card_no)) {
					bill_money = bill_money + Double.valueOf(cardMapVo.get("bill_money").toString());
					detailListVo.get(i - 1).put("bill_money", "-" + bill_money);
					continue;
				}

				cardMapVo.put(ass_card_no, bill_money);

				mapVo.put("group_id", map.get("group_id"));

				mapVo.put("hos_id", map.get("hos_id"));

				mapVo.put("copy_code", map.get("copy_code"));

				mapVo.put("bill_no", map.get("bill_no"));

				mapVo.put("naturs_code", naturs_code);

				mapVo.put("ass_card_no", ass_card_no);

				mapVo.put("bill_money", bill_money);

				mapVo.put("note", "");

				detailListVo.add(mapVo);

			}

			/**
			 * 构建分期付款数据
			 */
			Double bill_money = 0.0;
			for (int i = 0; i < ass_card_nos.length; i++) {

				Map<String, Object> mapVo = new HashMap<String, Object>();

				String ass_card_no = ass_card_nos[i];

				map.put("ass_card_no", ass_card_no);

				Map<String, Object> map2 = assBillMainMapper.queryAssInAndAssChanges(map);

				String stage_no =map2.get("stage_no")==null?"": map2.get("stage_no").toString();

				String stage_name =map2.get("stage_name")==null?"": map2.get("stage_name").toString();

				Double un_paymoney =map2.get("unpay_money")==null?0: Double.valueOf(map2.get("unpay_money").toString());

				String naturs_code =map2.get("naturs_code")==null?"": map2.get("naturs_code").toString();

				mapVo.put("group_id", map.get("group_id"));

				mapVo.put("hos_id", map.get("hos_id"));

				mapVo.put("copy_code", map.get("copy_code"));

				mapVo.put("bill_no", map.get("bill_no"));

				mapVo.put("naturs_code", naturs_code);

				mapVo.put("ass_card_no", ass_card_no);

				mapVo.put("pay_plan_money", un_paymoney);

				mapVo.put("bill_money", un_paymoney + "");

				mapVo.put("stage_no", stage_no);

				mapVo.put("stage_name", stage_name);

				bill_money = bill_money + un_paymoney;

				billPayStageList.add(mapVo);
			}

			assBillMainMapper.add(map);
			assBaseService.updateAssBillNoMaxNo(map);
			assBillDetailMapper.addBatch(detailListVo);
			assBillStageMapper.addBatch(billPayStageList);

			map.put("bill_money", bill_money);
			assBillMainMapper.updateBillMoney(map);

			return "{\"msg\":\"生成成功.\",\"state\":\"true\",\"update_para\":\"" + map.get("group_id") + "|"
					+ map.get("hos_id") + "|" + map.get("copy_code") + "|" + bill_no + "\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}
	}
}
