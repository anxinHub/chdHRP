/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */

package com.chd.hrp.ass.serviceImpl.sell.in;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.nutz.lang.Strings;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import com.chd.base.MyConfig;
import com.chd.base.SessionManager;
import com.chd.base.SysPage;
import com.chd.base.exception.SysException;
import com.chd.base.ftp.FtpUtil;
import com.chd.base.util.ChdJson;
import com.chd.hrp.ass.dao.accessory.AssAccessoryLandMapper;
import com.chd.hrp.ass.dao.card.AssCardLandMapper;
import com.chd.hrp.ass.dao.depre.acc.AssDepreAccLandMapper;
import com.chd.hrp.ass.dao.depre.manager.AssDepreManageLandMapper;
import com.chd.hrp.ass.dao.dict.AssBillNoMapper;
import com.chd.hrp.ass.dao.file.AssFileLandMapper;
import com.chd.hrp.ass.dao.pay.AssPayStageLandMapper;
import com.chd.hrp.ass.dao.photo.AssPhotoLandMapper;
import com.chd.hrp.ass.dao.resource.AssResourceLandMapper;
import com.chd.hrp.ass.dao.sell.in.AssSellInDetailLandMapper;
import com.chd.hrp.ass.dao.sell.in.AssSellInLandMapper;
import com.chd.hrp.ass.dao.sell.map.AssSellMapLandMapper;
import com.chd.hrp.ass.dao.sell.out.AssSellOutDetailLandMapper;
import com.chd.hrp.ass.dao.sell.out.AssSellOutLandMapper;
import com.chd.hrp.ass.dao.sell.out.source.AssSellOutSourceLandMapper;
import com.chd.hrp.ass.dao.share.AssShareDeptLandMapper;
import com.chd.hrp.ass.dao.share.AssShareDeptRLandMapper;
import com.chd.hrp.ass.entity.card.AssCardLand;
import com.chd.hrp.ass.entity.dict.AssBillNo;
import com.chd.hrp.ass.entity.file.AssFileLand;
import com.chd.hrp.ass.entity.photo.AssPhotoLand;
import com.chd.hrp.ass.entity.sell.in.AssSellInLand;
import com.chd.hrp.ass.entity.sell.out.AssSellOutDetailLand;
import com.chd.hrp.ass.service.base.AssBaseService;
import com.chd.hrp.ass.service.sell.in.AssSellInLandService;
import com.chd.hrp.mat.dao.info.basic.MatStoreMapper;
import com.chd.hrp.sys.dao.DeptDictMapper;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Description: 050901 资产有偿调拨入库单主表(土地)
 * @Table: ASS_SELL_IN_LAND
 * @Author: bell
 * @email: bell@e-tonggroup.com
 * @Version: 1.0
 */
 
@Service("assSellInLandService")
public class AssSellInLandServiceImpl implements AssSellInLandService {    

	private static Logger logger = Logger.getLogger(AssSellInLandServiceImpl.class);
	// 引入DAO操作
	@Resource(name = "assSellInLandMapper")
	private final AssSellInLandMapper assSellInLandMapper = null;

	@Resource(name = "assSellInDetailLandMapper")
	private final AssSellInDetailLandMapper assSellInDetailLandMapper = null;

	@Resource(name = "assSellOutLandMapper")
	private final AssSellOutLandMapper assSellOutLandMapper = null;

	@Resource(name = "assSellOutDetailLandMapper")
	private final AssSellOutDetailLandMapper assSellOutDetailLandMapper = null;

	@Resource(name = "assSellOutSourceLandMapper")
	private final AssSellOutSourceLandMapper assSellOutSourceLandMapper = null;

	@Resource(name = "assCardLandMapper")
	private final AssCardLandMapper assCardLandMapper = null;

	@Resource(name = "assSellMapLandMapper")
	private final AssSellMapLandMapper assSellMapLandMapper = null;

	@Resource(name = "assResourceLandMapper")
	private final AssResourceLandMapper assResourceLandMapper = null;

	@Resource(name = "assShareDeptLandMapper")
	private final AssShareDeptLandMapper assShareDeptLandMapper = null;

	@Resource(name = "assShareDeptRLandMapper")
	private final AssShareDeptRLandMapper assShareDeptRLandMapper = null;

	@Resource(name = "assFileLandMapper")
	private final AssFileLandMapper assFileLandMapper = null;

	@Resource(name = "assPhotoLandMapper")
	private final AssPhotoLandMapper assPhotoLandMapper = null;

	@Resource(name = "assAccessoryLandMapper")
	private final AssAccessoryLandMapper assAccessoryLandMapper = null;

	@Resource(name = "assDepreAccLandMapper")
	private final AssDepreAccLandMapper assDepreAccLandMapper = null;

	@Resource(name = "assDepreManageLandMapper")
	private final AssDepreManageLandMapper assDepreManageLandMapper = null;

	@Resource(name = "assPayStageLandMapper")
	private final AssPayStageLandMapper assPayStageLandMapper = null;

	@Resource(name = "matStoreMapper")
	private final MatStoreMapper matStoreMapper = null;

	@Resource(name = "deptDictMapper")
	private final DeptDictMapper deptDictMapper = null;

	@Resource(name = "assBaseService")
	private final AssBaseService assBaseService = null;

	/**
	 * @Description 添加050901 资产有偿调拨入库单主表（无形资产)<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String add(Map<String, Object> entityMap) throws DataAccessException {

		// 获取对象050901 资产有偿调拨入库单主表（无形资产)
		AssSellInLand assSellInLand = queryByCode(entityMap);

		if (assSellInLand != null) {

			return "{\"error\":\"数据重复,请重新添加.\"}";

		}

		try {

			int state = assSellInLandMapper.add(entityMap);

			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 add\"}";

		}

	}

	/**
	 * @Description 批量添加050901 资产有偿调拨入库单主表（无形资产)<BR>
	 * @param entityList
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String addBatch(List<Map<String, Object>> entityList) throws DataAccessException {

		try {

			assSellInLandMapper.addBatch(entityList);

			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addBatch\"}";

		}

	}

	/**
	 * @Description 更新050901 资产有偿调拨入库单主表（无形资产)<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String update(Map<String, Object> entityMap) throws DataAccessException {

		try {

			int state = assSellInLandMapper.update(entityMap);

			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 update\"}";

		}

	}

	/**
	 * @Description 批量更新050901 资产有偿调拨入库单主表（无形资产)<BR>
	 * @param entityList
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String updateBatch(List<Map<String, Object>> entityList) throws DataAccessException {

		try {

			assSellInLandMapper.updateBatch(entityList);

			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 updateBatch\"}";

		}

	}

	/**
	 * @Description 删除050901 资产有偿调拨入库单主表（无形资产)<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String delete(Map<String, Object> entityMap) throws DataAccessException {

		try {

			int state = assSellInLandMapper.delete(entityMap);

			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 delete\"}";

		}

	}

	/**
	 * @Description 批量删除050901 资产有偿调拨入库单主表（无形资产)<BR>
	 * @param entityList
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String deleteBatch(List<Map<String, Object>> entityList) throws DataAccessException {
		try {
			for (int i = 0; i < entityList.size(); i++) {
				List<Map<String, Object>> listMap = new ArrayList<Map<String, Object>>();
				listMap.add(entityList.get(i));
				delCard(listMap);
			}
			assSellInDetailLandMapper.deleteBatch(entityList);
			assSellMapLandMapper.deleteBatch(entityList);
			assSellInLandMapper.deleteBatch(entityList);

			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());
		}
	}

	private void delCard(List<Map<String, Object>> entityList) {
		try {
			Map<String, Object> inMapVo = new HashMap<String, Object>();
			inMapVo.put("group_id", entityList.get(0).get("group_id"));
			inMapVo.put("hos_id", entityList.get(0).get("hos_id"));
			inMapVo.put("copy_code", entityList.get(0).get("copy_code"));
			inMapVo.put("ass_in_no", entityList.get(0).get("ass_in_no"));
			inMapVo.put("ass_id", entityList.get(0).get("ass_id"));
			inMapVo.put("ass_no", entityList.get(0).get("ass_no"));

			List<Map<String, Object>> cardList = assCardLandMapper.queryByAssInNo(inMapVo);
			if (cardList.size() > 0) {

				// 删除资产文档
				for (Map<String, Object> fileMap : cardList) {
					List<AssFileLand> assFileLandList = (List<AssFileLand>) assFileLandMapper.queryExists(fileMap);
					if (assFileLandList.size() > 0 && assFileLandList != null) {
						for (AssFileLand assFileLand : assFileLandList) {
							if (assFileLand.getFile_url() != null && !assFileLand.getFile_url().equals("")) {
								String file_name = assFileLand.getFile_url().substring(
										assFileLand.getFile_url().lastIndexOf("/") + 1,
										assFileLand.getFile_url().length());
								String path = assFileLand.getFile_url().substring(0,
										assFileLand.getFile_url().lastIndexOf("/"));
								FtpUtil.deleteFile(path, file_name);
								path = path.substring(0, path.lastIndexOf("/"));
								FtpUtil.deleteDirectory(assFileLand.getAss_card_no(), path);
							}
						}
					}
				}
				// 删除资产照片
				for (Map<String, Object> photoMap : cardList) {
					List<AssPhotoLand> assPhotoLandList = (List<AssPhotoLand>) assPhotoLandMapper.queryExists(photoMap);
					if (assPhotoLandList.size() > 0 && assPhotoLandList != null) {
						for (AssPhotoLand assPhotoLand : assPhotoLandList) {
							if (assPhotoLand.getFile_url() != null && !assPhotoLand.getFile_url().equals("")) {
								String file_name = assPhotoLand.getFile_url().substring(
										assPhotoLand.getFile_url().lastIndexOf("/") + 1,
										assPhotoLand.getFile_url().length());
								String path = assPhotoLand.getFile_url().substring(0,
										assPhotoLand.getFile_url().lastIndexOf("/"));
								FtpUtil.deleteFile(path, file_name);
								path = path.substring(0, path.lastIndexOf("/"));
								FtpUtil.deleteDirectory(assPhotoLand.getAss_card_no(), path);
							}
						}
					}
				}
				assShareDeptRLandMapper.deleteBatch(cardList);
				assShareDeptLandMapper.deleteBatch(cardList);
				assResourceLandMapper.deleteBatch(cardList);
				assFileLandMapper.deleteBatch(cardList);
				assPhotoLandMapper.deleteBatch(cardList);
				assAccessoryLandMapper.deleteBatch(cardList);
				assPayStageLandMapper.deleteBatch(cardList);
				assDepreAccLandMapper.deleteBatch(cardList);
				assDepreManageLandMapper.deleteBatch(cardList);
				assCardLandMapper.deleteBatchByAssInNo(cardList);
			}

		} catch (Exception e) {
			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());
		}
	}

	/**
	 * @Description 添加050901 资产有偿调拨入库单主表（无形资产)<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String addOrUpdate(Map<String, Object> entityMap) throws DataAccessException {
		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();
		Map<String, Object> mapVo = new HashMap<String, Object>();
		mapVo.put("group_id", entityMap.get("group_id"));
		mapVo.put("hos_id", entityMap.get("hos_id"));
		mapVo.put("copy_code", entityMap.get("copy_code"));
		mapVo.put("sell_in_no", entityMap.get("sell_in_no"));
		mapVo.put("ass_in_no", entityMap.get("sell_in_no"));
		if(entityMap.get("create_date") != null && !"".equals(entityMap.get("create_date"))){
			entityMap.put("year", entityMap.get("create_date").toString().substring(0,4));
			entityMap.put("month", entityMap.get("create_date").toString().substring(5,7));
		}
		entityMap.put("bill_table", "ASS_SELL_IN_LAND");
		List<AssSellInLand> list = (List<AssSellInLand>) assSellInLandMapper.queryExists(mapVo);
		try {
			if (list.size() > 0) {
				int state = assSellInLandMapper.update(entityMap);
			} else {
				String sell_in_no = assBaseService.getBillNOSeqNo(entityMap);
				entityMap.put("sell_in_no", sell_in_no);
				int state = assSellInLandMapper.add(entityMap);
				assBaseService.updateAssBillNoMaxNo(entityMap);
			}

			List<Map> detail = ChdJson.fromJsonAsList(Map.class, entityMap.get("ParamVo").toString());

			List<Map<String, Object>> cardList = assCardLandMapper.queryByAssInNo(mapVo);

			List<Map<String, Object>> delCardList = new ArrayList<Map<String, Object>>();

			for (Map<String, Object> det : cardList) {
				boolean falg = false;
				for (Map<String, Object> temp : detail) {
					if (temp.get("ass_id") == null || "".equals(temp.get("ass_id"))) {
						continue;
					}
					if (det.get("ass_id").toString().equals(temp.get("ass_id").toString().split("@")[0])) {
						falg = true;
						break;
					}
				}
				if (falg == false) {
					delCardList.add(det);
				}
			}
			if (delCardList.size() > 0) {
				delCard(delCardList);
			}

			Double price = 0.0;
			Double add_depre = 0.0;
			Double cur_money = 0.0;
			Double fore_money = 0.0;

			for (Map<String, Object> detailVo : detail) {
				if (detailVo.get("ass_id") == null || "".equals(detailVo.get("ass_id"))) {
					continue;
				}
				detailVo.put("group_id", entityMap.get("group_id"));
				detailVo.put("hos_id", entityMap.get("hos_id"));
				detailVo.put("copy_code", entityMap.get("copy_code"));
				detailVo.put("sell_in_no", entityMap.get("sell_in_no"));

				String ass_id_no = detailVo.get("ass_id").toString();
				detailVo.put("ass_id", ass_id_no.split("@")[0]);
				detailVo.put("ass_no", ass_id_no.split("@")[1]);
				detailVo.put("ass_ori_card_no", detailVo.get("ass_ori_card_no"));

				if (detailVo.get("price") != null && !detailVo.get("price").equals("")) {
					detailVo.put("price", detailVo.get("price").toString());
					price = price + Double.parseDouble(detailVo.get("price").toString());
				} else {
					detailVo.put("price", "0");
				}

				if (detailVo.get("add_depre") != null && !detailVo.get("add_depre").equals("")) {
					detailVo.put("add_depre", detailVo.get("add_depre").toString());
					add_depre = add_depre + Double.parseDouble(detailVo.get("add_depre").toString());
				} else {
					detailVo.put("add_depre", "0");
				}

				if (detailVo.get("cur_money") != null && !detailVo.get("cur_money").equals("")) {
					detailVo.put("cur_money", detailVo.get("cur_money").toString());
					cur_money = cur_money + Double.parseDouble(detailVo.get("cur_money").toString());
				} else {
					detailVo.put("cur_money", "0");
				}

				if (detailVo.get("fore_money") != null && !detailVo.get("fore_money").equals("")) {
					detailVo.put("fore_money", detailVo.get("fore_money").toString());
					fore_money = fore_money + Double.parseDouble(detailVo.get("fore_money").toString());
				} else {
					detailVo.put("fore_money", "0");
				}

				if (detailVo.get("add_depre_month") != null && !detailVo.get("add_depre_month").equals("")) {
					detailVo.put("add_depre_month", String.valueOf(detailVo.get("add_depre_month")));
				} else {
					detailVo.put("add_depre_month", "0");
				}
				if (detailVo.get("note") != null && !detailVo.get("note").equals("")) {
					detailVo.put("note", detailVo.get("note"));
				} else {
					detailVo.put("note", "");
				}

				listVo.add(detailVo);
			}

			assSellInDetailLandMapper.delete(entityMap);
			assSellInDetailLandMapper.addBatch(listVo);
			entityMap.put("price", price + "");
			entityMap.put("add_depre", add_depre + "");
			entityMap.put("cur_money", cur_money + "");
			entityMap.put("fore_money", fore_money + "");

			assSellInLandMapper.updateInMoney(entityMap);
			return "{\"msg\":\"保存成功.\",\"state\":\"true\",\"sell_in_no\":\"" + entityMap.get("sell_in_no").toString()
					+ "\",\"price\":\"" + price + "\",\"add_depre\":\"" + add_depre + "\",\"cur_money\":\"" + cur_money
					+ "\",\"fore_money\":\"" + fore_money + "\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}

	}

	/**
	 * @Description 查询结果集050901 资产有偿调拨入库单主表（无形资产)<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String query(Map<String, Object> entityMap) throws DataAccessException {

		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		if (sysPage.getTotal() == -1) {

			List<AssSellInLand> list = (List<AssSellInLand>) assSellInLandMapper.query(entityMap);

			return ChdJson.toJson(list);

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

			List<AssSellInLand> list = (List<AssSellInLand>) assSellInLandMapper.query(entityMap, rowBounds);

			PageInfo page = new PageInfo(list);

			return ChdJson.toJson(list, page.getTotal());

		}

	}

	/**
	 * @Description 获取对象050901 资产有偿调拨入库单主表（无形资产)<BR>
	 * @param entityMap<BR>
	 *            参数为主键
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public <T> T queryByCode(Map<String, Object> entityMap) throws DataAccessException {
		return assSellInLandMapper.queryByCode(entityMap);
	}

	/**
	 * @Description 获取050901 资产有偿调拨入库单主表（无形资产)<BR>
	 * @param entityMap<BR>
	 *            参数为要检索的字段
	 * @return AssSellInLand
	 * @throws DataAccessException
	 */
	@Override
	public <T> T queryByUniqueness(Map<String, Object> entityMap) throws DataAccessException {
		return assSellInLandMapper.queryByUniqueness(entityMap);
	}

	/**
	 * @Description 获取050901 资产有偿调拨入库单主表（无形资产)<BR>
	 * @param entityMap<BR>
	 *            参数为要检索的字段
	 * @return List<AssSellInLand>
	 * @throws DataAccessException
	 */
	@Override
	public List<?> queryExists(Map<String, Object> entityMap) throws DataAccessException {
		return assSellInLandMapper.queryExists(entityMap);
	}

	private String getAssYear() {
		String yearMonth = MyConfig.getCurAccYearMonth();
		return yearMonth.substring(0, 4);
	}

	private String getAssMonth() {
		String yearMonth = MyConfig.getCurAccYearMonth();
		return yearMonth.substring(4, 6);
	}

	// 引入DAO操作
	@Resource(name = "assBillNoMapper")
	private final AssBillNoMapper assBillNoMapper = null;

	private int updateAssBillNoMaxNo(String tableName) throws DataAccessException {
		Map<String, Object> entityMap = new HashMap<String, Object>();
		entityMap.put("group_id", SessionManager.getGroupId());
		entityMap.put("hos_id", SessionManager.getHosId());
		entityMap.put("copy_code", SessionManager.getCopyCode());
		entityMap.put("bill_table", tableName);
		return assBillNoMapper.updateAssBillNoMaxNo(entityMap);
	}

	private String getBillNOSeqNo(String tableName) throws DataAccessException {
		Map<String, Object> entityMap = new HashMap<String, Object>();
		entityMap.put("group_id", SessionManager.getGroupId());
		entityMap.put("hos_id", SessionManager.getHosId());
		entityMap.put("copy_code", SessionManager.getCopyCode());
		entityMap.put("bill_table", tableName.toUpperCase());
		AssBillNo bill = queryAssBillNoByName(entityMap);
		String pref = bill.getPref();
		int seq_no = bill.getSeq_no();
		int max_no = bill.getMax_no();
		return pref + Strings.alignRight(max_no, seq_no, '0');
	}

	public AssBillNo queryAssBillNoByName(Map<String, Object> entityMap) throws DataAccessException {
		if (entityMap.get("bill_table") != null) {
			entityMap.put("bill_table", entityMap.get("bill_table").toString().toUpperCase());
		}
		return assBillNoMapper.queryAssBillNoByCode(entityMap);
	}

	private String formatNumberZero(Object obj) {
		if (obj != null) {
			if (obj.toString().equals("0")) {
				return "0";
			} else {
				return obj.toString();
			}
		} else {
			return "";
		}
	}

	private String initCardBySellInLand(Map<String, Object> map) {
		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();
		try {
			List<Map<String, Object>> list = assSellInDetailLandMapper.queryByInit(map);

			delCard(list);
			for (Map<String, Object> temp : list) {
				Map<String, Object> entityMap = new HashMap<String, Object>();
				String ass_card_no = getBillNOSeqNo("ASS_CARD_LAND");
				updateAssBillNoMaxNo("ASS_CARD_LAND");
				// 卡片表
				entityMap.put("group_id", map.get("group_id"));
				entityMap.put("hos_id", map.get("hos_id"));
				entityMap.put("copy_code", map.get("copy_code"));
				entityMap.put("ass_card_no", ass_card_no);
				entityMap.put("ass_ori_card_no", temp.get("ass_ori_card_no"));
				entityMap.put("ass_id", temp.get("ass_id"));
				entityMap.put("ass_no", temp.get("ass_no"));
				entityMap.put("gb_code", temp.get("gb_code"));
				/*
				 * entityMap.put("ass_mondl", temp.get("ass_model"));
				 * entityMap.put("ass_brand", temp.get("ass_brand"));
				 */
				entityMap.put("ass_amount", "1");

				entityMap.put("price", formatNumberZero(temp.get("price")));
				entityMap.put("depre_money", formatNumberZero(temp.get("add_depre")));
				entityMap.put("manage_depre_money", "0");
				entityMap.put("cur_money", formatNumberZero(temp.get("cur_money")));
				entityMap.put("fore_money", formatNumberZero(temp.get("fore_money")));
				entityMap.put("buy_type", temp.get("bus_type_code"));
				entityMap.put("unit_code", temp.get("unit_code"));
				entityMap.put("use_state", "0");
				entityMap.put("dispose_type", null);
				entityMap.put("dispose_date", null);
				entityMap.put("dispose_cost", null);
				entityMap.put("dispose_income", null);
				entityMap.put("dispose_tax", null);
				entityMap.put("is_depr", formatNumberZero(temp.get("is_depre")));
				entityMap.put("depr_method", temp.get("ass_depre_code"));
				entityMap.put("acc_depre_amount", temp.get("depre_years"));
				entityMap.put("is_manage_depre", temp.get("is_manage_depre"));
				entityMap.put("manage_depr_method", temp.get("manage_depr_method"));
				entityMap.put("manage_depre_amount", formatNumberZero(temp.get("manage_depre_amount")));
				entityMap.put("ven_id", null);
				entityMap.put("ven_no", null);
				entityMap.put("land_area", null);
				entityMap.put("cert_name", null);
				entityMap.put("cert_code", null);
				entityMap.put("cert_date", null);
				entityMap.put("prop_code", null);
				entityMap.put("land_no", null);
				entityMap.put("gain_date", null);
				entityMap.put("run_date", null);
				entityMap.put("location", null);
				entityMap.put("dept_id", null);
				entityMap.put("dept_no", null);
				entityMap.put("land_source_code", null);
				entityMap.put("note", temp.get("note"));
				entityMap.put("ass_in_no", temp.get("sell_in_no"));
				entityMap.put("sell_in_no", temp.get("sell_in_no"));
				entityMap.put("in_date", null);
				entityMap.put("is_init", "0");
				entityMap.put("pact_code", null);
				entityMap.put("apply_date", null);
				entityMap.put("add_depre_month", temp.get("add_depre_month"));
				entityMap.put("add_manage_month", "0");
				
				entityMap.put("proc_store_id", null);
				entityMap.put("proc_store_no", null);


				// 分摊科室设置_土地
				// entityMap.put("area", null);//面积
				// 资产资金来源匹配表_土地
				entityMap.put("source_id", 1);
				entityMap.put("pay_money", "0");
				entityMap.put("unpay_money", formatNumberZero(temp.get("price")));

				/**
				 * if (entityMap.get("dept_id") != null &&
				 * !entityMap.get("dept_id").equals("") &&
				 * entityMap.get("dept_no") != null &&
				 * !entityMap.get("dept_no").equals("")) { entityMap.put("area",
				 * 1); entityMap.put("ass_year", getAssYear());
				 * entityMap.put("ass_month", getAssMonth()); }else{ return
				 * "{\"warn\":\"仓库没有维护所在科室,不能生成! \"}"; }
				 */

				listVo.add(entityMap);
			}
			assCardLandMapper.addBatch(listVo);
			assResourceLandMapper.addBatch(listVo);
			// assShareDeptRLandMapper.addBatch(listVo);
			// assShareDeptLandMapper.addBatch(listVo);

			return "{\"msg\":\"生成成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}
	}

	private String initCardByImportSellInLand(Map<String, Object> map) {
		String basePath = "ass";
		String group_id = map.get("group_id").toString();
		String hos_id = map.get("hos_id").toString();
		String copy_code = map.get("copy_code").toString();
		String bus_type_code = map.get("bus_type_code").toString();
		map.put("ass_card_no", map.get("ass_ori_card_no"));
		try {
			AssCardLand assCardLand = assCardLandMapper.queryByCode(map);// 查询原始的卡片
			if (assCardLand == null) {// 通过原始卡片没有找到对应卡片-以普通方式生成卡片
				return initCardBySellInLand(map);
			} else {
				List<Map<String, Object>> list = assSellInDetailLandMapper.queryByInit(map);// 查找对应明细数据
				delCard(list);// 先删除卡片

				Map<String, Object> entityMap = new HashMap<String, Object>();
				String ass_card_no = getBillNOSeqNo("ASS_CARD_LAND");
				updateAssBillNoMaxNo("ASS_CARD_LAND");
				entityMap.put("group_id", map.get("group_id"));
				entityMap.put("hos_id", map.get("hos_id"));
				entityMap.put("copy_code", map.get("copy_code"));
				entityMap.put("ass_card_no", ass_card_no);
				entityMap.put("ass_ori_card_no", map.get("ass_ori_card_no"));
				entityMap.put("ass_id", assCardLand.getAss_id());
				entityMap.put("ass_no", assCardLand.getAss_no());
				entityMap.put("gb_code", assCardLand.getGb_code());
				entityMap.put("ass_amount", "1");
				entityMap.put("unit_code", assCardLand.getUnit_code());
				entityMap.put("ven_id", assCardLand.getVen_id());
				entityMap.put("ven_no", assCardLand.getVen_no());
				entityMap.put("store_id", null);
				entityMap.put("store_no", null);

				/**
				 * Map<String, Object> deptMap =
				 * matStoreMapper.queryByCode(entityMap);
				 * 
				 * if (deptMap.get("dept_id") != null &&
				 * !deptMap.get("dept_id").equals("")) { Map<String, Object>
				 * deptNoMap = new HashMap<String, Object>();
				 * deptNoMap.put("group_id", deptMap.get("group_id"));
				 * deptNoMap.put("hos_id", deptMap.get("hos_id"));
				 * deptNoMap.put("dept_id", deptMap.get("dept_id")); DeptDict
				 * deptDict = deptDictMapper.queryDeptDictByDeptCode(deptNoMap);
				 * entityMap.put("dept_id", deptDict.getDept_id());
				 * entityMap.put("dept_no", deptDict.getDept_no()); }
				 */
				entityMap.put("price", formatNumberZero(list.get(0).get("price")));
				entityMap.put("depre_money", formatNumberZero(list.get(0).get("add_depre")));
				entityMap.put("add_depre_month", formatNumberZero(list.get(0).get("add_depre_month")));
				entityMap.put("cur_money", formatNumberZero(list.get(0).get("cur_money")));
				entityMap.put("fore_money", formatNumberZero(list.get(0).get("fore_money")));
				entityMap.put("buy_type", list.get(0).get("bus_type_code"));
				entityMap.put("acc_depre_amount", formatNumberZero(assCardLand.getAcc_depre_amount()));
				entityMap.put("manage_depre_amount", formatNumberZero(assCardLand.getManage_depre_amount()));
				entityMap.put("manage_depre_money", "0");
				entityMap.put("add_manage_month", "0");
				entityMap.put("is_manage_depre", formatNumberZero(assCardLand.getIs_manage_depre()));
				entityMap.put("manage_depr_method", assCardLand.getManage_depr_method());
				entityMap.put("depr_method", assCardLand.getDepr_method());
				entityMap.put("is_depr", formatNumberZero(assCardLand.getIs_depr()));
				entityMap.put("use_state", "0");
				entityMap.put("pact_code", assCardLand.getPact_code());
				entityMap.put("location", assCardLand.getLocation());
				entityMap.put("note", assCardLand.getNote());
				entityMap.put("ass_in_no", map.get("sell_in_no"));
				entityMap.put("sell_in_no", map.get("sell_in_no"));
				entityMap.put("in_date", null);
				entityMap.put("run_date", null);
				entityMap.put("bar_code", ass_card_no);
				entityMap.put("is_init", "0");
				entityMap.put("dispose_type", null);
				entityMap.put("dispose_cost", null);
				entityMap.put("dispose_income", null);
				entityMap.put("dispose_tax", null);
				
				entityMap.put("proc_store_id", assCardLand.getProc_store_id());
				entityMap.put("proc_store_no", assCardLand.getProc_store_no());


				/**
				 * if (entityMap.get("dept_id") != null &&
				 * !entityMap.get("dept_id").equals("") &&
				 * entityMap.get("dept_no") != null &&
				 * !entityMap.get("dept_no").equals("")) { entityMap.put("area",
				 * 1); entityMap.put("ass_year", getAssYear());
				 * entityMap.put("ass_month", getAssMonth()); }else{ return
				 * "{\"warn\":\"仓库没有维护所在科室,不能生成! \"}"; }
				 */

				// List<Map<String, Object>> shareDeptList = new
				// ArrayList<Map<String, Object>>();
				// shareDeptList.add(entityMap);

				assCardLandMapper.add(entityMap);// 插入卡片
				// assShareDeptRLandMapper.addBatch(shareDeptList);
				// assShareDeptLandMapper.addBatch(shareDeptList);
				Map<String, Object> sellMap = new HashMap<String, Object>();
				sellMap.put("group_id", map.get("out_group_id"));
				sellMap.put("hos_id", map.get("out_hos_id"));
				sellMap.put("copy_code", map.get("copy_code"));
				sellMap.put("ass_card_no", map.get("ass_ori_card_no"));
				List<Map<String, Object>> newResourceList = new ArrayList<Map<String, Object>>();
				// 平价调拨
				if (bus_type_code.equals("13")) {
					List<Map<String, Object>> resourceList = assSellOutSourceLandMapper
							.queryBySellOutNoAndAssCardNo(sellMap);// 查询资金来源
					for (Map<String, Object> resource : resourceList) {
						resource.put("group_id", map.get("group_id"));
						resource.put("hos_id", map.get("hos_id"));
						resource.put("copy_code", map.get("copy_code"));
						resource.put("ass_card_no", ass_card_no);
						resource.put("depre_money", resource.get("add_depre") + "");
						resource.put("manage_depre_money", resource.get("add_manage_depre") + "");
						resource.put("pay_money", "0");
						resource.put("unpay_money", formatNumberZero(resource.get("price")));
						newResourceList.add(resource);
					}

				}
				// 异价调拨
				if (bus_type_code.equals("15")) {
					Map<String, Object> resource = new HashMap<String, Object>();
					resource.put("group_id", map.get("group_id"));
					resource.put("hos_id", map.get("hos_id"));
					resource.put("copy_code", map.get("copy_code"));
					resource.put("ass_card_no", ass_card_no);
					resource.put("source_id", 1);
					resource.put("manage_depre_money", "0");
					resource.put("price", list.get(0).get("price"));
					resource.put("depre_money", list.get(0).get("add_depre"));
					resource.put("cur_money", list.get(0).get("cur_money"));
					resource.put("fore_money", list.get(0).get("fore_money"));
					resource.put("pay_money", "0");
					resource.put("unpay_money", list.get(0).get("price"));
					newResourceList.add(resource);
				}

				if (newResourceList.size() > 0) {
					assResourceLandMapper.addBatch(newResourceList);
				}

				List<Map<String, Object>> fileList = assFileLandMapper.queryByAssCardNo(map);// 资产文档
				List<Map<String, Object>> newFileList = new ArrayList<Map<String, Object>>();
				for (Map<String, Object> file : fileList) {
					file.put("group_id", map.get("group_id"));
					file.put("hos_id", map.get("hos_id"));
					file.put("copy_code", map.get("copy_code"));
					file.put("ass_card_no", ass_card_no);
					String fileName = file.get("file_url").toString().substring(
							file.get("file_url").toString().lastIndexOf("/") + 1,
							file.get("file_url").toString().length());
					String assFilePath = "assfile";
					String filePath = group_id + "/" + hos_id + "/" + copy_code + "/" + basePath + "/" + assFilePath
							+ "/06/" + ass_card_no;
					FtpUtil.copyFile(file.get("file_url").toString(), filePath);
					file.put("file_url", filePath + "/" + fileName);
					newFileList.add(file);
				}
				if (newFileList.size() > 0) {
					assFileLandMapper.addBatch(newFileList);
				}

				List<Map<String, Object>> photoList = assPhotoLandMapper.queryByAssCardNo(map);// 资产照片
				List<Map<String, Object>> newPhotoList = new ArrayList<Map<String, Object>>();
				for (Map<String, Object> photo : photoList) {
					photo.put("group_id", map.get("group_id"));
					photo.put("hos_id", map.get("hos_id"));
					photo.put("copy_code", map.get("copy_code"));
					photo.put("ass_card_no", ass_card_no);
					String fileName = photo.get("file_url").toString().substring(
							photo.get("file_url").toString().lastIndexOf("/") + 1,
							photo.get("file_url").toString().length());
					String assFilePath = "assphoto";
					String filePath = group_id + "/" + hos_id + "/" + copy_code + "/" + basePath + "/" + assFilePath
							+ "/06/" + ass_card_no;
					FtpUtil.copyFile(photo.get("file_url").toString(), filePath);
					photo.put("file_url", filePath + "/" + fileName);
					newPhotoList.add(photo);
				}
				if (newPhotoList.size() > 0) {
					assPhotoLandMapper.addBatch(newPhotoList);
				}

				List<Map<String, Object>> accessoryList = assAccessoryLandMapper.queryByAssCardNo(map);// 资产附件
				List<Map<String, Object>> newAccessoryList = new ArrayList<Map<String, Object>>();
				for (Map<String, Object> accessory : accessoryList) {
					accessory.put("group_id", map.get("group_id"));
					accessory.put("hos_id", map.get("hos_id"));
					accessory.put("copy_code", map.get("copy_code"));
					accessory.put("ass_card_no", ass_card_no);
					newAccessoryList.add(accessory);
				}
				if (newAccessoryList.size() > 0) {
					assAccessoryLandMapper.addBatch(newAccessoryList);
				}

			}

			return "{\"msg\":\"生成成功.\",\"state\":\"true\"}";
		} catch (DataAccessException e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}
	}

	@Override
	public String initAssSellInCardLand(Map<String, Object> map) throws DataAccessException {

		try {
			if (map.get("is_import").equals("0")) {
				return initCardBySellInLand(map);
			} else if (map.get("is_import").equals("1")) {
				return initCardByImportSellInLand(map);
			} else {
				return "{\"warn\":\"生成失败.\"}";
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}
	}

	@Override
	public String updateAudit(List<Map<String, Object>> entityMap) throws DataAccessException {
		try {
			assSellInLandMapper.updateAudit(entityMap);

			return "{\"msg\":\"审核成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());
		}
	}

	@Override
	public String updateReAudit(List<Map<String, Object>> entityMap) throws DataAccessException {
		try {
			assSellInLandMapper.updateReAudit(entityMap);

			return "{\"msg\":\"销审成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());
		}
	}

	@Override
	public String updateConfirm(List<Map<String, Object>> entityMap) throws DataAccessException {
		try {
			assSellInLandMapper.updateConfirm(entityMap);
			assCardLandMapper.updateConfirm(entityMap);

			return "{\"msg\":\"确认成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());
		}
	}

	@Override
	public String initAssSellInLand(Map<String, Object> entityMap) throws DataAccessException {
		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> mapVo = new ArrayList<Map<String, Object>>();
		try {
			List<AssSellOutDetailLand> outDetailList = assSellOutDetailLandMapper.queryByImportSellIn(entityMap);
			String bus_type_code = entityMap.get("bus_type_code").toString();
			entityMap.put("bill_table", "ASS_SELL_IN_LAND");
			String sell_in_no = assBaseService.getBillNOSeqNo(entityMap);
			Double price = 0.0;
			Double add_depre = 0.0;
			Double cur_money = 0.0;
			Double fore_money = 0.0;
			for (AssSellOutDetailLand assSellOutDetailLand : outDetailList) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("group_id", assSellOutDetailLand.getGroup_id());
				map.put("hos_id", assSellOutDetailLand.getHos_id());
				map.put("copy_code", assSellOutDetailLand.getCopy_code());
				map.put("sell_in_no", sell_in_no);
				map.put("ass_id", assSellOutDetailLand.getAss_id());
				map.put("ass_no", assSellOutDetailLand.getAss_no());
				map.put("ass_ori_card_no", assSellOutDetailLand.getAss_card_no());
				map.put("ass_brand", assSellOutDetailLand.getAss_brand());
				map.put("ass_spec", assSellOutDetailLand.getAss_spec());
				map.put("ass_model", assSellOutDetailLand.getAss_model());
				map.put("fac_id", assSellOutDetailLand.getFac_id());
				map.put("fac_no", assSellOutDetailLand.getFac_no());
				Double sourcePrice = 0.0;
				Double sourceAddDepre = 0.0;
				Double curPrice = 0.0;
				Double forePrice = 0.0;

				// 平价调拨
				if (bus_type_code.equals("13")) {
					sourcePrice = assSellOutDetailLand.getPrice();
					sourceAddDepre = 0.0;
					curPrice = assSellOutDetailLand.getCur_money();
					forePrice = assSellOutDetailLand.getFore_money();
					map.put("add_depre_month", "0");
				}

				// 异价调拨
				if (bus_type_code.equals("15")) {
					sourcePrice = assSellOutDetailLand.getSell_price();
					sourceAddDepre = assSellOutDetailLand.getAdd_depre();
					curPrice = assSellOutDetailLand.getSell_price();
					forePrice = 0.0;
					map.put("add_depre_month", assSellOutDetailLand.getAdd_depre_month());
				}

				map.put("price", sourcePrice + "");
				map.put("add_depre", sourceAddDepre + "");
				map.put("cur_money", curPrice + "");
				map.put("fore_money", forePrice);

				map.put("note", assSellOutDetailLand.getNote());
				price = price + sourcePrice;
				add_depre = add_depre + sourceAddDepre;
				cur_money = cur_money + curPrice;
				fore_money = fore_money + forePrice;
				listVo.add(map);
			}
			// 主表增加
			entityMap.put("sell_in_no", sell_in_no);
			entityMap.put("state", "0");
			entityMap.put("note", "引入调剂出库");
			assSellInLandMapper.add(entityMap);
			assBaseService.updateAssBillNoMaxNo(entityMap);
			// 明细表增加
			assSellInDetailLandMapper.addBatch(listVo);

			for (int i = 0; i < entityMap.get("sell_out_nos").toString().split(",").length; i++) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("group_id", entityMap.get("group_id"));
				map.put("hos_id", entityMap.get("hos_id"));
				map.put("copy_code", entityMap.get("copy_code"));
				map.put("sell_in_no", sell_in_no);
				map.put("out_group_id", entityMap.get("out_group_id"));
				map.put("out_hos_id", entityMap.get("out_hos_id"));
				map.put("out_copy_code", entityMap.get("out_copy_codes").toString().split(",")[i].replaceAll("'", ""));
				map.put("sell_out_no", entityMap.get("sell_out_nos").toString().split(",")[i].replaceAll("'", ""));
				mapVo.add(map);
			}
			// 增加关系表
			assSellMapLandMapper.addBatch(mapVo);

			entityMap.put("price", price + "");
			entityMap.put("add_depre", add_depre + "");
			entityMap.put("cur_money", cur_money + "");
			entityMap.put("fore_money", fore_money + "");
			// 计算主表金额
			assSellInLandMapper.updateInMoney(entityMap);
			return "{\"msg\":\"引入成功.\",\"state\":\"true\",\"update_para\":\"" + entityMap.get("group_id") + "|"
					+ entityMap.get("hos_id") + "|" + entityMap.get("copy_code") + "|" + sell_in_no + "|1\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}
	}

	@Override
	public Map<String, Object> printAssSellInLandData(Map<String, Object> map) throws DataAccessException {

		try {
			Map<String, Object> reMap = new HashMap<String, Object>();
			WebApplicationContext context = ContextLoader.getCurrentWebApplicationContext();
			AssSellInLandMapper assSellInLandMapper = (AssSellInLandMapper) context.getBean("assSellInLandMapper");

			// 查询凭证主表
			List<Map<String, Object>> mainList = assSellInLandMapper.queryAssSellInLandByAssInNo(map);

			// 查询凭证明细表
			List<Map<String, Object>> detailList = assSellInLandMapper.queryAssSellInLandDetailByAssInNo(map);

			reMap.put("main", mainList);
			reMap.put("detail", detailList);

			return reMap;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}

	}

	@Override
	public String initAssSellInBatchCardLand(Map<String, Object> map) throws DataAccessException {
		try {
			if (map.get("is_import").equals("0")) {
				return initCardBySellBatchInLand(map);
			} else if (map.get("is_import").equals("1")) {
				return initCardByImportSellBatchInLand(map);
			} else {
				return "{\"warn\":\"生成失败.\"}";
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}
	}

	private String initCardByImportSellBatchInLand(Map<String, Object> map) {
		String basePath = "ass";
		String group_id = map.get("group_id").toString();
		String hos_id = map.get("hos_id").toString();
		String copy_code = map.get("copy_code").toString();
		String bus_type_code = map.get("bus_type_code").toString();
		// map.put("ass_card_no", map.get("ass_ori_card_no"));
		/*
		 * String carno = map.get("ass_ori_card_nos").toString(); String car[]
		 * =carno.split(","); final List<String> ids = new ArrayList<String>();
		 * for (int i = 0; i < car.length; i++) { ids.add(car[i]); }
		 * map.put("ass_ori_card_no", ids);
		 */
		try {
			List<Map<String, Object>> list = assSellInDetailLandMapper.queryByInit(map);// 查找对应明细数据
			for (int i = 0; i < list.size(); i++) {
				List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
				mapList.add(list.get(i));
				delCard(mapList);// 先删除卡片
			}
			for (Map<String, Object> map2 : list) {
				Map<String, Object> entiMap = new HashMap<String, Object>();
				entiMap.put("group_id", map2.get("group_id"));
				entiMap.put("hos_id", map2.get("hos_id"));
				entiMap.put("copy_code", map2.get("copy_code"));
				entiMap.put("ass_ori_card_no", map2.get("ass_ori_card_no").toString());
				entiMap.put("ass_card_no", map2.get("ass_ori_card_no").toString());

				List<AssCardLand> cardList = (List<AssCardLand>) assCardLandMapper.queryByAssCardOldNo(entiMap);// 查询原始的卡片
				entiMap.put("UNIT_CODE", map2.get("UNIT_CODE"));
				entiMap.put("ass_in_no", map2.get("ass_in_no"));
				entiMap.put("ass_depre_code", map2.get("ass_depre_code"));
				entiMap.put("fore_money", map2.get("fore_money"));
				entiMap.put("ass_id", map2.get("ass_id"));
				entiMap.put("add_depre_month", map2.get("add_depre_month"));
				entiMap.put("depre_years", map2.get("depre_years"));
				entiMap.put("out_group_id", map2.get("out_group_id"));
				entiMap.put("allot_in_no", map2.get("allot_in_no	"));
				entiMap.put("ass_no", map2.get("ass_no"));
				entiMap.put("bus_type_code", map2.get("bus_type_code"));
				entiMap.put("is_measure", map2.get("is_measure"));
				entiMap.put("price", map2.get("price"));
				entiMap.put("cur_money", map2.get("cur_money"));
				entiMap.put("is_depre", map2.get("is_depre	"));
				entiMap.put("add_depre", map2.get("add_depre"));
				entiMap.put("out_hos_id", map2.get("out_hos_id"));
				entiMap.put("is_manage_depre", map2.get("is_manage_depre"));
				if (cardList == null || cardList.size() == 0) {// 通过原始卡片没有找到对应卡片-以普通方式生成卡片
					initCardBySellInLand(entiMap);
				} else {

					for (AssCardLand assCardLand : cardList) {
						Map<String, Object> entityMap = new HashMap<String, Object>();
						String ass_card_no = getBillNOSeqNo("ASS_CARD_LAND");
						updateAssBillNoMaxNo("ASS_CARD_LAND");
						entityMap.put("group_id", map.get("group_id"));
						entityMap.put("hos_id", map.get("hos_id"));
						entityMap.put("copy_code", map.get("copy_code"));
						entityMap.put("ass_card_no", ass_card_no);
						entityMap.put("ass_ori_card_no", assCardLand.getAss_card_no());
						entityMap.put("ass_id", assCardLand.getAss_id());
						entityMap.put("ass_no", assCardLand.getAss_no());
						entityMap.put("gb_code", assCardLand.getGb_code());
						entityMap.put("ass_amount", "1");
						entityMap.put("unit_code", assCardLand.getUnit_code());
						entityMap.put("ven_id", assCardLand.getVen_id());
						entityMap.put("ven_no", assCardLand.getVen_no());
						entityMap.put("store_id", null);
						entityMap.put("store_no", null);

						/**
						 * Map<String, Object> deptMap =
						 * matStoreMapper.queryByCode(entityMap);
						 * 
						 * if (deptMap.get("dept_id") != null &&
						 * !deptMap.get("dept_id").equals("")) { Map<String,
						 * Object> deptNoMap = new HashMap<String, Object>();
						 * deptNoMap.put("group_id", deptMap.get("group_id"));
						 * deptNoMap.put("hos_id", deptMap.get("hos_id"));
						 * deptNoMap.put("dept_id", deptMap.get("dept_id"));
						 * DeptDict deptDict =
						 * deptDictMapper.queryDeptDictByDeptCode(deptNoMap);
						 * entityMap.put("dept_id", deptDict.getDept_id());
						 * entityMap.put("dept_no", deptDict.getDept_no()); }
						 */
						entityMap.put("price", formatNumberZero(map2.get("price")));
						entityMap.put("depre_money", formatNumberZero(map2.get("add_depre")));
						entityMap.put("add_depre_month", map2.get("add_depre_month"));
						entityMap.put("cur_money", formatNumberZero(map2.get("cur_money")));
						entityMap.put("fore_money", formatNumberZero(map2.get("fore_money")));
						entityMap.put("buy_type", list.get(0).get("bus_type_code"));
						entityMap.put("acc_depre_amount", formatNumberZero(assCardLand.getAcc_depre_amount()));
						entityMap.put("manage_depre_amount", formatNumberZero(assCardLand.getManage_depre_amount()));
						entityMap.put("manage_depre_money", "0");
						entityMap.put("add_manage_month", "0");
						entityMap.put("is_manage_depre", formatNumberZero(assCardLand.getIs_manage_depre()));
						entityMap.put("manage_depr_method", assCardLand.getManage_depr_method());
						entityMap.put("depr_method", assCardLand.getDepr_method());
						entityMap.put("is_depr", formatNumberZero(assCardLand.getIs_depr()));
						entityMap.put("use_state", "0");
						entityMap.put("pact_code", assCardLand.getPact_code());
						entityMap.put("location", assCardLand.getLocation());
						entityMap.put("note", assCardLand.getNote());
						entityMap.put("ass_in_no", map.get("sell_in_no"));
						entityMap.put("sell_in_no", map.get("sell_in_no"));
						entityMap.put("in_date", null);
						entityMap.put("run_date", null);
						entityMap.put("bar_code", ass_card_no);
						entityMap.put("is_init", "0");
						entityMap.put("dispose_type", null);
						entityMap.put("dispose_cost", null);
						entityMap.put("dispose_income", null);
						entityMap.put("dispose_tax", null);
						
						entityMap.put("proc_store_id", assCardLand.getProc_store_id());
						entityMap.put("proc_store_no", assCardLand.getProc_store_no());

						/**
						 * if (entityMap.get("dept_id") != null &&
						 * !entityMap.get("dept_id").equals("") &&
						 * entityMap.get("dept_no") != null &&
						 * !entityMap.get("dept_no").equals("")) {
						 * entityMap.put("area", 1); entityMap.put("ass_year",
						 * getAssYear()); entityMap.put("ass_month",
						 * getAssMonth()); }else{ return
						 * "{\"warn\":\"仓库没有维护所在科室,不能生成! \"}"; }
						 */

						// List<Map<String, Object>> shareDeptList = new
						// ArrayList<Map<String, Object>>();
						// shareDeptList.add(entityMap);

						assCardLandMapper.add(entityMap);// 插入卡片
						// assShareDeptRLandMapper.addBatch(shareDeptList);
						// assShareDeptLandMapper.addBatch(shareDeptList);
						Map<String, Object> sellMap = new HashMap<String, Object>();
						sellMap.put("group_id", map.get("out_group_id"));
						sellMap.put("hos_id", map.get("out_hos_id"));
						sellMap.put("copy_code", map.get("copy_code"));
						sellMap.put("ass_card_no", assCardLand.getAss_card_no());
						List<Map<String, Object>> newResourceList = new ArrayList<Map<String, Object>>();
						// 平价调拨
						if (bus_type_code.equals("13")) {
							List<Map<String, Object>> resourceList = assSellOutSourceLandMapper
									.queryBySellOutNoAndAssCardNo(sellMap);// 查询资金来源
							for (Map<String, Object> resource : resourceList) {
								resource.put("group_id", map.get("group_id"));
								resource.put("hos_id", map.get("hos_id"));
								resource.put("copy_code", map.get("copy_code"));
								resource.put("ass_card_no", ass_card_no);
								resource.put("manage_depre_money", "0");
								resource.put("depre_money", resource.get("add_depre") + "");
								resource.put("pay_money", "0");
								resource.put("unpay_money", formatNumberZero(resource.get("price")));
								newResourceList.add(resource);
							}

						}
						// 异价调拨
						if (bus_type_code.equals("15")) {
							Map<String, Object> resource = new HashMap<String, Object>();
							resource.put("group_id", map.get("group_id"));
							resource.put("hos_id", map.get("hos_id"));
							resource.put("copy_code", map.get("copy_code"));
							resource.put("ass_card_no", ass_card_no);
							resource.put("source_id", 1);
							resource.put("manage_depre_money", "0");
							resource.put("price", list.get(0).get("price"));
							resource.put("depre_money", list.get(0).get("add_depre"));
							resource.put("cur_money", list.get(0).get("cur_money"));
							resource.put("fore_money", list.get(0).get("fore_money"));
							resource.put("pay_money", "0");
							resource.put("unpay_money", list.get(0).get("price"));
							newResourceList.add(resource);
						}

						if (newResourceList.size() > 0) {
							assResourceLandMapper.addBatch(newResourceList);
						}

						List<Map<String, Object>> fileList = assFileLandMapper.queryByAssCardNo(map2);// 资产文档
						List<Map<String, Object>> newFileList = new ArrayList<Map<String, Object>>();
						for (Map<String, Object> file : fileList) {
							file.put("group_id", map.get("group_id"));
							file.put("hos_id", map.get("hos_id"));
							file.put("copy_code", map.get("copy_code"));
							file.put("ass_card_no", ass_card_no);
							String fileName = file.get("file_url").toString().substring(
									file.get("file_url").toString().lastIndexOf("/") + 1,
									file.get("file_url").toString().length());
							String assFilePath = "assfile";
							String filePath = group_id + "/" + hos_id + "/" + copy_code + "/" + basePath + "/"
									+ assFilePath + "/06/" + ass_card_no;
							FtpUtil.copyFile(file.get("file_url").toString(), filePath);
							file.put("file_url", filePath + "/" + fileName);
							newFileList.add(file);
						}
						if (newFileList.size() > 0) {
							assFileLandMapper.addBatch(newFileList);
						}

						List<Map<String, Object>> photoList = assPhotoLandMapper.queryByAssCardNo(map2);// 资产照片
						List<Map<String, Object>> newPhotoList = new ArrayList<Map<String, Object>>();
						for (Map<String, Object> photo : photoList) {
							photo.put("group_id", map.get("group_id"));
							photo.put("hos_id", map.get("hos_id"));
							photo.put("copy_code", map.get("copy_code"));
							photo.put("ass_card_no", ass_card_no);
							String fileName = photo.get("file_url").toString().substring(
									photo.get("file_url").toString().lastIndexOf("/") + 1,
									photo.get("file_url").toString().length());
							String assFilePath = "assphoto";
							String filePath = group_id + "/" + hos_id + "/" + copy_code + "/" + basePath + "/"
									+ assFilePath + "/06/" + ass_card_no;
							FtpUtil.copyFile(photo.get("file_url").toString(), filePath);
							photo.put("file_url", filePath + "/" + fileName);
							newPhotoList.add(photo);
						}
						if (newPhotoList.size() > 0) {
							assPhotoLandMapper.addBatch(newPhotoList);
						}

						List<Map<String, Object>> accessoryList = assAccessoryLandMapper.queryByAssCardNo(map2);// 资产附件
						List<Map<String, Object>> newAccessoryList = new ArrayList<Map<String, Object>>();
						for (Map<String, Object> accessory : accessoryList) {
							accessory.put("group_id", map.get("group_id"));
							accessory.put("hos_id", map.get("hos_id"));
							accessory.put("copy_code", map.get("copy_code"));
							accessory.put("ass_card_no", ass_card_no);
							newAccessoryList.add(accessory);
						}
						if (newAccessoryList.size() > 0) {
							assAccessoryLandMapper.addBatch(newAccessoryList);
						}

					}
				}
			}
			return "{\"msg\":\"生成成功.\",\"state\":\"true\"}";
		} catch (DataAccessException e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}
	}

	private String initCardBySellBatchInLand(Map<String, Object> map) {
		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();
		try {
			List<Map<String, Object>> list = assSellInDetailLandMapper.queryByInit(map);

			for (int i = 0; i < list.size(); i++) {
				List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
				mapList.add(list.get(i));
				delCard(mapList);// 先删除卡片
			}
			for (Map<String, Object> temp : list) {
				Map<String, Object> entityMap = new HashMap<String, Object>();
				String ass_card_no = getBillNOSeqNo("ASS_CARD_LAND");
				updateAssBillNoMaxNo("ASS_CARD_LAND");
				// 卡片表
				entityMap.put("group_id", map.get("group_id"));
				entityMap.put("hos_id", map.get("hos_id"));
				entityMap.put("copy_code", map.get("copy_code"));
				entityMap.put("ass_card_no", ass_card_no);
				entityMap.put("ass_ori_card_no", temp.get("ass_ori_card_no"));
				entityMap.put("ass_id", temp.get("ass_id"));
				entityMap.put("ass_no", temp.get("ass_no"));
				entityMap.put("gb_code", temp.get("gb_code"));
				/*
				 * entityMap.put("ass_mondl", temp.get("ass_model"));
				 * entityMap.put("ass_brand", temp.get("ass_brand"));
				 */
				entityMap.put("ass_amount", "1");

				entityMap.put("price", formatNumberZero(temp.get("price")));
				entityMap.put("depre_money", formatNumberZero(temp.get("add_depre")));
				entityMap.put("manage_depre_money", "0");
				entityMap.put("cur_money", formatNumberZero(temp.get("cur_money")));
				entityMap.put("fore_money", formatNumberZero(temp.get("fore_money")));
				entityMap.put("buy_type", temp.get("bus_type_code"));
				entityMap.put("unit_code", temp.get("unit_code"));
				entityMap.put("use_state", "0");
				entityMap.put("dispose_type", null);
				entityMap.put("dispose_date", null);
				entityMap.put("dispose_cost", null);
				entityMap.put("dispose_income", null);
				entityMap.put("dispose_tax", null);
				entityMap.put("is_depr", formatNumberZero(temp.get("is_depre")));
				entityMap.put("depr_method", temp.get("ass_depre_code"));
				entityMap.put("acc_depre_amount", temp.get("depre_years"));
				entityMap.put("is_manage_depre", temp.get("is_manage_depre"));
				entityMap.put("manage_depr_method", temp.get("manage_depr_method"));
				entityMap.put("manage_depre_amount", formatNumberZero(temp.get("manage_depre_amount")));
				entityMap.put("ven_id", null);
				entityMap.put("ven_no", null);
				entityMap.put("land_area", null);
				entityMap.put("cert_name", null);
				entityMap.put("cert_code", null);
				entityMap.put("cert_date", null);
				entityMap.put("prop_code", null);
				entityMap.put("land_no", null);
				entityMap.put("gain_date", null);
				entityMap.put("run_date", null);
				entityMap.put("location", null);
				entityMap.put("dept_id", null);
				entityMap.put("dept_no", null);
				entityMap.put("land_source_code", null);
				entityMap.put("note", temp.get("note"));
				entityMap.put("ass_in_no", temp.get("sell_in_no"));
				entityMap.put("sell_in_no", temp.get("sell_in_no"));
				entityMap.put("in_date", null);
				entityMap.put("is_init", "0");
				entityMap.put("pact_code", null);
				entityMap.put("apply_date", null);
				entityMap.put("add_depre_month", temp.get("add_depre_month"));
				entityMap.put("add_manage_month", "0");
				
				entityMap.put("proc_store_id", null);
				entityMap.put("proc_store_no", null);

				// 分摊科室设置_土地
				// entityMap.put("area", null);//面积
				// 资产资金来源匹配表_土地
				entityMap.put("source_id", 1);
				entityMap.put("pay_money", "0");
				entityMap.put("unpay_money", formatNumberZero(temp.get("price")));

				/**
				 * if (entityMap.get("dept_id") != null &&
				 * !entityMap.get("dept_id").equals("") &&
				 * entityMap.get("dept_no") != null &&
				 * !entityMap.get("dept_no").equals("")) { entityMap.put("area",
				 * 1); entityMap.put("ass_year", getAssYear());
				 * entityMap.put("ass_month", getAssMonth()); }else{ return
				 * "{\"warn\":\"仓库没有维护所在科室,不能生成! \"}"; }
				 */

				listVo.add(entityMap);
			}
			assCardLandMapper.addBatch(listVo);
			assResourceLandMapper.addBatch(listVo);
			// assShareDeptRLandMapper.addBatch(listVo);
			// assShareDeptLandMapper.addBatch(listVo);

			return "{\"msg\":\"生成成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}
	}

	@Override
	public List<Map<String, Object>> queryDept(Map<String, Object> mapVo) throws DataAccessException {
		// TODO Auto-generated method stub
		return assSellInLandMapper.queryDept(mapVo);
	}

	/**
	 * 入库单状态查询 （打印时校验数据专用）
	 */
	@Override
	public List<String> queryAssSellInLandStates(Map<String, Object> mapVo) throws DataAccessException {

		return assSellInLandMapper.queryAssSellInLandStates(mapVo);
	}

	@Override
	public String queryDetails(Map<String, Object> entityMap) {
		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		if (sysPage.getTotal() == -1) {

			List<Map<String, Object>> list = (List<Map<String, Object>>) assSellInLandMapper.queryDetails(entityMap);

			return ChdJson.toJson(list);

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

			List<Map<String, Object>> list = (List<Map<String, Object>>) assSellInLandMapper.queryDetails(entityMap,
					rowBounds);

			PageInfo page = new PageInfo(list);

			return ChdJson.toJson(list, page.getTotal());

		}
	}

}
