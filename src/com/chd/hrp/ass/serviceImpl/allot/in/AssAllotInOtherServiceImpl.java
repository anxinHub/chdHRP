/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.ass.serviceImpl.allot.in;

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
import com.chd.base.util.DateUtil;
import com.chd.hrp.ass.dao.accessory.AssAccessoryOtherMapper;
import com.chd.hrp.ass.dao.allot.in.AssAllotInDetailOtherMapper;
import com.chd.hrp.ass.dao.allot.in.AssAllotInOtherMapper;
import com.chd.hrp.ass.dao.allot.map.AssAllotMapOtherMapper;
import com.chd.hrp.ass.dao.allot.out.AssAllotOutDetailOtherMapper;
import com.chd.hrp.ass.dao.allot.out.source.AssAllotOutSourceOtherMapper;
import com.chd.hrp.ass.dao.card.AssCardOtherMapper;
import com.chd.hrp.ass.dao.depre.acc.AssDepreAccOtherMapper;
import com.chd.hrp.ass.dao.depre.manager.AssDepreManageOtherMapper;
import com.chd.hrp.ass.dao.dict.AssBillNoMapper;
import com.chd.hrp.ass.dao.file.AssFileOtherMapper;
import com.chd.hrp.ass.dao.pay.AssPayStageOtherMapper;
import com.chd.hrp.ass.dao.photo.AssPhotoOtherMapper;
import com.chd.hrp.ass.dao.resource.AssResourceOtherMapper;
import com.chd.hrp.ass.dao.share.AssShareDeptROtherMapper;
import com.chd.hrp.ass.dao.share.AssShareDeptOtherMapper;
import com.chd.hrp.ass.entity.allot.in.AssAllotInOther;
import com.chd.hrp.ass.entity.allot.out.AssAllotOutDetailOther;
import com.chd.hrp.ass.entity.card.AssCardOther;
import com.chd.hrp.ass.entity.dict.AssBillNo;
import com.chd.hrp.ass.entity.file.AssFileOther;
import com.chd.hrp.ass.entity.photo.AssPhotoOther;
import com.chd.hrp.ass.service.allot.in.AssAllotInOtherService;
import com.chd.hrp.ass.service.base.AssBaseService;
import com.chd.hrp.mat.dao.info.basic.MatStoreMapper;
import com.chd.hrp.sys.dao.DeptDictMapper;
import com.chd.hrp.sys.entity.DeptDict;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Description:
 * 050901 资产无偿调拨入库单主表(其他固定资产)
 * @Table:
 * ASS_ALLOT_IN_OTHER
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

 
@Service("assAllotInOtherService")
public class AssAllotInOtherServiceImpl implements AssAllotInOtherService {    

	private static Logger logger = Logger.getLogger(AssAllotInOtherServiceImpl.class);
	// 引入DAO操作
	@Resource(name = "assAllotInOtherMapper")
	private final AssAllotInOtherMapper assAllotInOtherMapper = null;

	@Resource(name = "assAllotInDetailOtherMapper")
	private final AssAllotInDetailOtherMapper assAllotInDetailOtherMapper = null;

	@Resource(name = "assAllotOutDetailOtherMapper")
	private final AssAllotOutDetailOtherMapper assAllotOutDetailOtherMapper = null;
	
	@Resource(name = "assAllotOutSourceOtherMapper")
	private final AssAllotOutSourceOtherMapper assAllotOutSourceOtherMapper = null;

	@Resource(name = "assAllotMapOtherMapper")
	private final AssAllotMapOtherMapper assAllotMapOtherMapper = null;

	@Resource(name = "assCardOtherMapper")
	private final AssCardOtherMapper assCardOtherMapper = null;

	@Resource(name = "assResourceOtherMapper")
	private final AssResourceOtherMapper assResourceOtherMapper = null;

	@Resource(name = "assShareDeptOtherMapper")
	private final AssShareDeptOtherMapper assShareDeptOtherMapper = null;

	@Resource(name = "assShareDeptROtherMapper")
	private final AssShareDeptROtherMapper assShareDeptROtherMapper = null;

	@Resource(name = "assFileOtherMapper")
	private final AssFileOtherMapper assFileOtherMapper = null;

	@Resource(name = "assPhotoOtherMapper")
	private final AssPhotoOtherMapper assPhotoOtherMapper = null;

	@Resource(name = "assAccessoryOtherMapper")
	private final AssAccessoryOtherMapper assAccessoryOtherMapper = null;

	@Resource(name = "assDepreAccOtherMapper")
	private final AssDepreAccOtherMapper assDepreAccOtherMapper = null;

	@Resource(name = "assDepreManageOtherMapper")
	private final AssDepreManageOtherMapper assDepreManageOtherMapper = null;

	@Resource(name = "assPayStageOtherMapper")
	private final AssPayStageOtherMapper assPayStageOtherMapper = null;

	@Resource(name = "matStoreMapper")
	private final MatStoreMapper matStoreMapper = null;

	@Resource(name = "deptDictMapper")
	private final DeptDictMapper deptDictMapper = null;

	@Resource(name = "assBaseService")
	private final AssBaseService assBaseService = null;

	/**
	 * @Description 添加050901 资产无偿调拨入库单主表（其他固定资产）<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String add(Map<String, Object> entityMap) throws DataAccessException {

		// 获取对象050901 资产无偿调拨入库单主表（其他固定资产）
		AssAllotInOther assAllotInOther = queryByCode(entityMap);

		if (assAllotInOther != null) {

			return "{\"error\":\"数据重复,请重新添加.\"}";

		}

		try {

			int state = assAllotInOtherMapper.add(entityMap);

			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 add\"}";

		}

	}

	/**
	 * @Description 批量添加050901 资产无偿调拨入库单主表（其他固定资产）<BR>
	 * @param entityList
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String addBatch(List<Map<String, Object>> entityList) throws DataAccessException {

		try {

			assAllotInOtherMapper.addBatch(entityList);

			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addBatch\"}";

		}

	}

	/**
	 * @Description 更新050901 资产无偿调拨入库单主表（其他固定资产）<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String update(Map<String, Object> entityMap) throws DataAccessException {

		try {

			int state = assAllotInOtherMapper.update(entityMap);

			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 update\"}";

		}

	}

	/**
	 * @Description 批量更新050901 资产无偿调拨入库单主表（其他固定资产）<BR>
	 * @param entityList
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String updateBatch(List<Map<String, Object>> entityList) throws DataAccessException {

		try {

			assAllotInOtherMapper.updateBatch(entityList);

			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 updateBatch\"}";

		}

	}

	/**
	 * @Description 删除050901 资产无偿调拨入库单主表（其他固定资产）<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String delete(Map<String, Object> entityMap) throws DataAccessException {

		try {

			int state = assAllotInOtherMapper.delete(entityMap);

			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 delete\"}";

		}

	}

	/**
	 * @Description 批量删除050901 资产无偿调拨入库单主表（其他固定资产）<BR>
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
			assAllotInDetailOtherMapper.deleteBatch(entityList);
			assAllotMapOtherMapper.deleteBatch(entityList);
			assAllotInOtherMapper.deleteBatch(entityList);

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

			List<Map<String, Object>> cardList = assCardOtherMapper.queryByAssInNo(inMapVo);
			if (cardList.size() > 0) {
				// 删除条码文件
				for (Map<String, Object> map : cardList) {
					AssCardOther assCardOther = assCardOtherMapper.queryByCode(map);
					if (assCardOther.getBar_url() != null && !assCardOther.getBar_url().equals("")) {
						String file_name = assCardOther.getBar_url().substring(
								assCardOther.getBar_url().lastIndexOf("/") + 1, assCardOther.getBar_url().length());
						String path = assCardOther.getBar_url().substring(0,
								assCardOther.getBar_url().lastIndexOf("/"));
						FtpUtil.deleteFile(path, file_name);
					}
				}

				// 删除资产文档
				for (Map<String, Object> fileMap : cardList) {
					List<AssFileOther> assFileOtherList = (List<AssFileOther>) assFileOtherMapper
							.queryExists(fileMap);
					if (assFileOtherList.size() > 0 && assFileOtherList != null) {
						for (AssFileOther assFileOther : assFileOtherList) {
							if (assFileOther.getFile_url() != null && !assFileOther.getFile_url().equals("")) {
								String file_name = assFileOther.getFile_url().substring(
										assFileOther.getFile_url().lastIndexOf("/") + 1,
										assFileOther.getFile_url().length());
								String path = assFileOther.getFile_url().substring(0,
										assFileOther.getFile_url().lastIndexOf("/"));
								FtpUtil.deleteFile(path, file_name);
								path = path.substring(0,path.lastIndexOf("/"));
								FtpUtil.deleteDirectory(assFileOther.getAss_card_no(), path);
							}
						}
					}
				}
				// 删除资产照片
				for (Map<String, Object> photoMap : cardList) {
					List<AssPhotoOther> assPhotoOtherList = (List<AssPhotoOther>) assPhotoOtherMapper
							.queryExists(photoMap);
					if (assPhotoOtherList.size() > 0 && assPhotoOtherList != null) {
						for (AssPhotoOther assPhotoOther : assPhotoOtherList) {
							if (assPhotoOther.getFile_url() != null && !assPhotoOther.getFile_url().equals("")) {
								String file_name = assPhotoOther.getFile_url().substring(
										assPhotoOther.getFile_url().lastIndexOf("/") + 1,
										assPhotoOther.getFile_url().length());
								String path = assPhotoOther.getFile_url().substring(0,
										assPhotoOther.getFile_url().lastIndexOf("/"));
								FtpUtil.deleteFile(path, file_name);
								path = path.substring(0,path.lastIndexOf("/"));
								FtpUtil.deleteDirectory(assPhotoOther.getAss_card_no(), path);
							}
						}
					}
				}
				assShareDeptROtherMapper.deleteBatch(cardList);
				assShareDeptOtherMapper.deleteBatch(cardList);
				assResourceOtherMapper.deleteBatch(cardList);
				assFileOtherMapper.deleteBatch(cardList);
				assPhotoOtherMapper.deleteBatch(cardList);
				assAccessoryOtherMapper.deleteBatch(cardList);
				assPayStageOtherMapper.deleteBatch(cardList);
				assDepreAccOtherMapper.deleteBatch(cardList);
				assDepreManageOtherMapper.deleteBatch(cardList);
				assCardOtherMapper.deleteBatchByAssInNo(cardList);
			}

		} catch (Exception e) {
			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());
		}
	}

	/**
	 * @Description 添加050901 资产无偿调拨入库单主表（其他固定资产）<BR>
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
		mapVo.put("allot_in_no", entityMap.get("allot_in_no"));
		mapVo.put("ass_in_no", entityMap.get("allot_in_no"));

		List<AssAllotInOther> list = (List<AssAllotInOther>) assAllotInOtherMapper.queryExists(mapVo);
		try {
			if (list.size() > 0) {
				int state = assAllotInOtherMapper.update(entityMap);
			} else {

				if(entityMap.get("create_date") != null && !"".equals(entityMap.get("create_date"))){
					entityMap.put("year", entityMap.get("create_date").toString().substring(0,4));
					entityMap.put("month", entityMap.get("create_date").toString().substring(5,7));
				}


				entityMap.put("bill_table", "ASS_ALLOT_IN_OTHER");
				String allot_in_no = assBaseService.getBillNOSeqNo(entityMap);

				entityMap.put("allot_in_no", allot_in_no);
				int state = assAllotInOtherMapper.add(entityMap);
				assBaseService.updateAssBillNoMaxNo(entityMap);
			}

			List<Map> detail = ChdJson.fromJsonAsList(Map.class, entityMap.get("ParamVo").toString());

			List<Map<String, Object>> cardList = assCardOtherMapper.queryByAssInNo(mapVo);

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
				detailVo.put("allot_in_no", entityMap.get("allot_in_no"));

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
					detailVo.put("add_depre_month", detailVo.get("add_depre_month"));
				} else {
					detailVo.put("add_depre_month", "0");
				}

				if (detailVo.get("fac_id") != null && !detailVo.get("fac_id").equals("") && !detailVo.get("fac_id").toString().equals("@")) {
					String fac_id_no = detailVo.get("fac_id").toString();
					detailVo.put("fac_id", fac_id_no.split("@")[0]);
					detailVo.put("fac_no", fac_id_no.split("@")[1]);
				} else {
					detailVo.put("fac_id", "");
					detailVo.put("fac_no", "");
				}

				if (detailVo.get("note") != null && !detailVo.get("note").equals("")) {
					detailVo.put("note", detailVo.get("note"));
				} else {
					detailVo.put("note", "");
				}

				if (detailVo.get("ass_spec") != null && !detailVo.get("ass_spec").equals("")) {
					detailVo.put("ass_spec", detailVo.get("ass_spec"));
				} else {
					detailVo.put("ass_spec", "");
				}

				if (detailVo.get("ass_model") != null && !detailVo.get("ass_model").equals("")) {
					detailVo.put("ass_model", detailVo.get("ass_model"));
				} else {
					detailVo.put("ass_model", "");
				}

				if (detailVo.get("ass_brand") != null && !detailVo.get("ass_brand").equals("")) {
					detailVo.put("ass_brand", detailVo.get("ass_brand"));
				} else {
					detailVo.put("ass_brand", "");
				}

				listVo.add(detailVo);
			}

			assAllotInDetailOtherMapper.delete(entityMap);
			assAllotInDetailOtherMapper.addBatch(listVo);
			entityMap.put("price", price + "");
			entityMap.put("add_depre", add_depre + "");
			entityMap.put("cur_money", cur_money + "");
			entityMap.put("fore_money", fore_money + "");

			assAllotInOtherMapper.updateInMoney(entityMap);
			return "{\"msg\":\"保存成功.\",\"state\":\"true\",\"allot_in_no\":\"" + entityMap.get("allot_in_no").toString()
					+ "\",\"price\":\"" + price + "\",\"add_depre\":\"" + add_depre + "\",\"cur_money\":\"" + cur_money
					+ "\",\"fore_money\":\"" + fore_money + "\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}

	}

	/**
	 * @Description 查询结果集050901 资产无偿调拨入库单主表（其他固定资产）<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String query(Map<String, Object> entityMap) throws DataAccessException {

		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		if (sysPage.getTotal() == -1) {

			List<AssAllotInOther> list = (List<AssAllotInOther>) assAllotInOtherMapper.query(entityMap);

			return ChdJson.toJson(list);

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

			List<AssAllotInOther> list = (List<AssAllotInOther>) assAllotInOtherMapper.query(entityMap,
					rowBounds);

			PageInfo page = new PageInfo(list);

			return ChdJson.toJson(list, page.getTotal());

		}

	}

	/**
	 * @Description 获取对象050901 资产无偿调拨入库单主表（其他固定资产）<BR>
	 * @param entityMap<BR>
	 *            参数为主键
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public <T> T queryByCode(Map<String, Object> entityMap) throws DataAccessException {
		return assAllotInOtherMapper.queryByCode(entityMap);
	}

	/**
	 * @Description 获取050901 资产无偿调拨入库单主表（其他固定资产）<BR>
	 * @param entityMap<BR>
	 *            参数为要检索的字段
	 * @return AssAllotInOther
	 * @throws DataAccessException
	 */
	@Override
	public <T> T queryByUniqueness(Map<String, Object> entityMap) throws DataAccessException {
		return assAllotInOtherMapper.queryByUniqueness(entityMap);
	}

	/**
	 * @Description 获取050901 资产无偿调拨入库单主表（其他固定资产）<BR>
	 * @param entityMap<BR>
	 *            参数为要检索的字段
	 * @return List<AssAllotInOther>
	 * @throws DataAccessException
	 */
	@Override
	public List<?> queryExists(Map<String, Object> entityMap) throws DataAccessException {
		return assAllotInOtherMapper.queryExists(entityMap);
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

	@Override
	public String updateAudit(List<Map<String, Object>> entityMap) throws DataAccessException {
		try {
			assAllotInOtherMapper.updateAudit(entityMap);

			return "{\"msg\":\"审核成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());
		}
	}

	@Override
	public String updateReAudit(List<Map<String, Object>> entityMap) throws DataAccessException {
		try {
			assAllotInOtherMapper.updateReAudit(entityMap);

			return "{\"msg\":\"销审成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());
		}
	}

	@Override
	public String updateConfirm(List<Map<String, Object>> entityMap) throws DataAccessException {
		try {
			assAllotInOtherMapper.updateConfirm(entityMap);
			assCardOtherMapper.updateConfirm(entityMap);

			return "{\"msg\":\"确认成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());
		}
	}

	// 普通录入生成方式
	private String initCardByAllotInOther(Map<String, Object> map) {
		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();
		try {
			String basePath = "ass";
			String group_id = map.get("group_id").toString();
			String hos_id = map.get("hos_id").toString();
			String copy_code = map.get("copy_code").toString();
			String assTwoPath = "assbartwo";
			String assOnePath = "assbarone";
			String twoFilePath = group_id + "/" + hos_id + "/" + copy_code + "/" + basePath + "/" + assTwoPath + "/04/";// 资产性质
			String oneFilePath = group_id + "/" + hos_id + "/" + copy_code + "/" + basePath + "/" + assOnePath + "/04/";// 资产性质
			List<Map<String, Object>> list = assAllotInDetailOtherMapper.queryByInit(map);

			for (int i = 0; i < list.size(); i++) {
				List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
				mapList.add(list.get(i));
				delCard(mapList);// 先删除卡片
			}
			for (Map<String, Object> temp : list) {
				Map<String, Object> entityMap = new HashMap<String, Object>();
				String ass_card_no = getBillNOSeqNo("ASS_CARD_OTHER");
				updateAssBillNoMaxNo("ASS_CARD_OTHER");
				entityMap.put("group_id", map.get("group_id"));
				entityMap.put("hos_id", map.get("hos_id"));
				entityMap.put("copy_code", map.get("copy_code"));
				entityMap.put("ass_card_no", ass_card_no);
				entityMap.put("ass_ori_card_no", temp.get("ass_ori_card_no"));
				entityMap.put("ass_id", temp.get("ass_id"));
				entityMap.put("ass_no", temp.get("ass_no"));
				entityMap.put("gb_code", temp.get("gb_code"));
				entityMap.put("ass_spec", temp.get("ass_spec"));
				entityMap.put("ass_mondl", temp.get("ass_model"));
				entityMap.put("ass_brand", temp.get("ass_brand"));
				entityMap.put("ass_amount", "1");
				entityMap.put("unit_code", temp.get("unit_code"));
				entityMap.put("fac_no", temp.get("fac_no"));
				entityMap.put("fac_id", temp.get("fac_id"));
				entityMap.put("ven_id", null);
				entityMap.put("ven_no", null);
				entityMap.put("is_dept", "0");
				entityMap.put("store_id", temp.get("in_store_id"));
				entityMap.put("store_no", temp.get("in_store_no"));
				entityMap.put("proc_store_id", map.get("in_store_id"));
				entityMap.put("proc_store_no", map.get("in_store_no"));
				Map<String, Object> deptMap = matStoreMapper.queryByCode(entityMap);

				if (deptMap.get("dept_id") != null && !deptMap.get("dept_id").equals("")) {
					Map<String, Object> deptNoMap = new HashMap<String, Object>();
					deptNoMap.put("group_id", deptMap.get("group_id"));
					deptNoMap.put("hos_id", deptMap.get("hos_id"));
					deptNoMap.put("dept_id", deptMap.get("dept_id"));
					DeptDict deptDict = deptDictMapper.queryDeptDictByDeptCode(deptNoMap);
					entityMap.put("dept_id", deptDict.getDept_id());
					entityMap.put("dept_no", deptDict.getDept_no());
				}

				entityMap.put("price", formatNumberZero(temp.get("price")));
				entityMap.put("depre_money", formatNumberZero(temp.get("add_depre")));
				entityMap.put("add_depre_month", temp.get("add_depre_month"));
				entityMap.put("manage_depre_money", "0");
				entityMap.put("cur_money", formatNumberZero(temp.get("cur_money")));
				entityMap.put("fore_money", formatNumberZero(temp.get("fore_money")));
				entityMap.put("buy_type", temp.get("bus_type_code"));
				entityMap.put("use_state", "0");
				entityMap.put("is_depr", formatNumberZero(temp.get("is_depre")));
				entityMap.put("depr_method", temp.get("ass_depre_code"));
				entityMap.put("acc_depre_amount", temp.get("depre_years"));
				entityMap.put("is_manage_depre", temp.get("is_manage_depre"));
				entityMap.put("manage_depr_method", temp.get("manage_depr_method"));
				entityMap.put("manage_depre_amount", formatNumberZero(temp.get("manage_depre_amount")));
				entityMap.put("is_measure", formatNumberZero(temp.get("is_measure")));
				entityMap.put("is_throw", "0");
				entityMap.put("pact_code", null);
				entityMap.put("ins_money", null);
				entityMap.put("ins_date", null);
				entityMap.put("accept_emp", null);
				entityMap.put("accept_date", null);
				entityMap.put("service_date", null);
				entityMap.put("ass_seq_no", null);
				entityMap.put("location", null);
				entityMap.put("note", temp.get("note"));
				entityMap.put("ass_in_no", temp.get("allot_in_no"));
				entityMap.put("allot_in_no", temp.get("allot_in_no"));
				entityMap.put("in_date", null);
				entityMap.put("run_date", null);
				entityMap.put("is_bar", formatNumberZero(temp.get("is_bar")));
				entityMap.put("bar_type", temp.get("bar_type"));
				entityMap.put("bar_code", ass_card_no);
				entityMap.put("is_init", "0");
				entityMap.put("dispose_type", null);
				entityMap.put("dispose_cost", null);
				entityMap.put("dispose_income", null);
				entityMap.put("dispose_tax", null);
				entityMap.put("ass_purpose", null);
				entityMap.put("proj_id", null);
				entityMap.put("proj_no", null);
				entityMap.put("source_id", 1);
				entityMap.put("pay_money", "0");
				entityMap.put("unpay_money", formatNumberZero(temp.get("price")));
				if (temp.get("is_bar") != null && !temp.get("is_bar").equals("")) {
					if (temp.get("is_bar").toString().equals("1")) {
						if (temp.get("bar_type") != null && !temp.get("bar_type").equals("")) {
							if (temp.get("bar_type").toString().equals("1")) {
								FtpUtil.getBarcodeWriteFile(ass_card_no, "", oneFilePath, ass_card_no + ".png");// 一维码
								entityMap.put("bar_url", oneFilePath + ass_card_no + ".png");

							} else if (temp.get("bar_type").toString().equals("2")) {
								FtpUtil.getQRWriteFile(ass_card_no, "", twoFilePath, ass_card_no + ".png");// 二维码
								entityMap.put("bar_url", twoFilePath + ass_card_no + ".png");
							}
						}
					}

				}

				if (entityMap.get("dept_id") != null && !entityMap.get("dept_id").equals("")
						&& entityMap.get("dept_no") != null && !entityMap.get("dept_no").equals("")) {
					entityMap.put("area", 1);
					entityMap.put("ass_year", getAssYear());
					entityMap.put("ass_month", getAssMonth());
				}else{
					return "{\"warn\":\"仓库没有维护所在科室,不能生成! \"}";
				}

				listVo.add(entityMap);
			}
			assCardOtherMapper.addBatch(listVo);
			assResourceOtherMapper.addBatch(listVo);
			assShareDeptROtherMapper.addBatch(listVo);
			assShareDeptOtherMapper.addBatch(listVo);

			return "{\"msg\":\"生成成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}
	}
	
	//对于数据为0的进行处理
	private String formatNumberZero(Object obj){
		if(obj != null){
			if(obj.toString().equals("0")){
				return "0";
			}else{
				return obj.toString();
			}
		}else{
			return "";
		}
	}

	// 引入出库单生成方式
	private String initCardByImportAllotInOther(Map<String, Object> map) {
		String basePath = "ass";
		String group_id = map.get("group_id").toString();
		String hos_id = map.get("hos_id").toString();
		String copy_code = map.get("copy_code").toString();
		String assTwoPath = "assbartwo";
		String assOnePath = "assbarone";
		String twoFilePath = group_id + "/" + hos_id + "/" + copy_code + "/" + basePath + "/" + assTwoPath + "/04/";// 资产性质
		String oneFilePath = group_id + "/" + hos_id + "/" + copy_code + "/" + basePath + "/" + assOnePath + "/04/";// 资产性质
		map.put("ass_card_no", map.get("ass_ori_card_no"));
		try {
			AssCardOther assCardOther = assCardOtherMapper.queryByCode(map);// 查询原始的卡片
			if (assCardOther == null) {// 通过原始卡片没有找到对应卡片-以普通方式生成卡片
				return initCardByAllotInOther(map);
			} else {
				List<Map<String, Object>> list = assAllotInDetailOtherMapper.queryByInit(map);// 查找对应明细数据
				for (int i = 0; i < list.size(); i++) {
					delCard(list);// 先删除卡片
				}
				Map<String, Object> temp = list.get(0);
				
				Map<String, Object> entityMap = new HashMap<String, Object>();
				String ass_card_no = getBillNOSeqNo("ASS_CARD_OTHER");
				updateAssBillNoMaxNo("ASS_CARD_OTHER");
				entityMap.put("group_id", map.get("group_id"));
				entityMap.put("hos_id", map.get("hos_id"));
				entityMap.put("copy_code", map.get("copy_code"));
				entityMap.put("ass_card_no", ass_card_no);
				entityMap.put("ass_ori_card_no", map.get("ass_ori_card_no"));
				entityMap.put("ass_id", assCardOther.getAss_id());
				entityMap.put("ass_no", assCardOther.getAss_no());
				entityMap.put("gb_code", assCardOther.getGb_code());
				entityMap.put("ass_spec", temp.get("ass_spec"));
				entityMap.put("ass_mondl", temp.get("ass_mondl"));
				entityMap.put("ass_brand", temp.get("ass_brand"));
				entityMap.put("ass_amount", "1");
				entityMap.put("unit_code", assCardOther.getUnit_code());
				entityMap.put("fac_no", temp.get("fac_no"));
				entityMap.put("fac_id", temp.get("fac_id"));
				entityMap.put("ven_id", assCardOther.getVen_id());
				entityMap.put("ven_no", assCardOther.getVen_no());
				entityMap.put("is_dept", formatNumberZero(assCardOther.getIs_dept()));
				entityMap.put("store_id", map.get("in_store_id"));
				entityMap.put("store_no", map.get("in_store_no"));
				entityMap.put("proc_store_id", map.get("in_store_id"));
				entityMap.put("proc_store_no", map.get("in_store_no"));
				Map<String, Object> deptMap = matStoreMapper.queryByCode(entityMap);

				if (deptMap.get("dept_id") != null && !deptMap.get("dept_id").equals("")) {
					Map<String, Object> deptNoMap = new HashMap<String, Object>();
					deptNoMap.put("group_id", deptMap.get("group_id"));
					deptNoMap.put("hos_id", deptMap.get("hos_id"));
					deptNoMap.put("dept_id", deptMap.get("dept_id"));
					DeptDict deptDict = deptDictMapper.queryDeptDictByDeptCode(deptNoMap);
					entityMap.put("dept_id", deptDict.getDept_id());
					entityMap.put("dept_no", deptDict.getDept_no());
				}
				entityMap.put("price", temp.get("price"));
				entityMap.put("depre_money", temp.get("add_depre"));
				entityMap.put("add_depre_month", temp.get("add_depre_month"));
				entityMap.put("cur_money", temp.get("cur_money"));
				entityMap.put("fore_money", temp.get("fore_money"));
				entityMap.put("buy_type", "03");
				entityMap.put("acc_depre_amount", formatNumberZero(assCardOther.getAcc_depre_amount()));
				entityMap.put("manage_depre_amount", formatNumberZero(assCardOther.getManage_depre_amount()));
				entityMap.put("manage_depre_money", "0");
				entityMap.put("add_manage_month", "0");
				entityMap.put("is_manage_depre", formatNumberZero(assCardOther.getIs_manage_depre()));
				entityMap.put("manage_depr_method", assCardOther.getManage_depr_method());
				entityMap.put("depr_method", assCardOther.getDepr_method());
				entityMap.put("is_depr", formatNumberZero(assCardOther.getIs_depr()));
				entityMap.put("use_state", "0");
				entityMap.put("is_measure", formatNumberZero(assCardOther.getIs_measure()));
				entityMap.put("is_throw", formatNumberZero(assCardOther.getIs_throw()));
				entityMap.put("pact_code", assCardOther.getPact_code());
				entityMap.put("ins_money", assCardOther.getIns_money());
				entityMap.put("ins_date", DateUtil.dateToString(assCardOther.getIns_date(), "yyyy-MM-dd"));
				entityMap.put("accept_emp", assCardOther.getAccept_emp());
				entityMap.put("accept_date", DateUtil.dateToString(assCardOther.getAccept_date(), "yyyy-MM-dd"));
				entityMap.put("service_date", DateUtil.dateToString(assCardOther.getService_date(), "yyyy-MM-dd"));
				entityMap.put("ass_seq_no", assCardOther.getAss_seq_no());
				entityMap.put("location", assCardOther.getLocation());
				entityMap.put("note", assCardOther.getNote());
				entityMap.put("ass_in_no", map.get("allot_in_no"));
				entityMap.put("allot_in_no", map.get("allot_in_no"));
				entityMap.put("in_date", null);
				entityMap.put("run_date", null);
				entityMap.put("is_bar", formatNumberZero(assCardOther.getIs_bar()));
				entityMap.put("bar_type", assCardOther.getBar_type());
				entityMap.put("bar_code", ass_card_no);
				entityMap.put("is_init", "0");
				entityMap.put("dispose_type", null);
				entityMap.put("dispose_cost", null);
				entityMap.put("dispose_income", null);
				entityMap.put("dispose_tax", null);
				entityMap.put("ass_purpose", assCardOther.getAss_purpose());
				entityMap.put("proj_id", assCardOther.getProj_id());
				entityMap.put("proj_no", assCardOther.getProj_no());

				if (assCardOther.getIs_bar() != null && !assCardOther.getIs_bar().equals("")) {
					if (assCardOther.getIs_bar() == 1) {
						if (assCardOther.getBar_type() != null && !assCardOther.getBar_type().equals("")) {
							if (assCardOther.getBar_type() == 1) {
								FtpUtil.getBarcodeWriteFile(ass_card_no, "", oneFilePath, ass_card_no + ".png");// 一维码
								entityMap.put("bar_url", oneFilePath + ass_card_no + ".png");

							} else if (assCardOther.getBar_type() == 2) {
								FtpUtil.getQRWriteFile(ass_card_no, "", twoFilePath, ass_card_no + ".png");// 二维码
								entityMap.put("bar_url", twoFilePath + ass_card_no + ".png");
							}
						}
					}

				}
				
				if (entityMap.get("dept_id") != null && !entityMap.get("dept_id").equals("")
						&& entityMap.get("dept_no") != null && !entityMap.get("dept_no").equals("")) {
					entityMap.put("area", 1);
					entityMap.put("ass_year", getAssYear());
					entityMap.put("ass_month", getAssMonth());
				}else{
					return "{\"warn\":\"仓库没有维护所在科室,不能生成! \"}";
				}
				
				List<Map<String, Object>> shareDeptList = new ArrayList<Map<String,Object>>();
				shareDeptList.add(entityMap);
				
				assCardOtherMapper.add(entityMap);// 插入卡片
				assShareDeptROtherMapper.addBatch(shareDeptList);
				assShareDeptOtherMapper.addBatch(shareDeptList);
				Map<String, Object> allotMap = new HashMap<String, Object>();
				allotMap.put("group_id", map.get("out_group_id"));
				allotMap.put("hos_id", map.get("out_hos_id"));
				allotMap.put("copy_code", map.get("copy_code"));
				allotMap.put("ass_card_no", map.get("ass_ori_card_no"));
				List<Map<String, Object>> resourceList = assAllotOutSourceOtherMapper.queryByAlloutOutNoAndAssCardNo(allotMap);//查询资金来源
				List<Map<String, Object>> newResourceList = new ArrayList<Map<String,Object>>();
				for (Map<String, Object> resource : resourceList) {
					resource.put("group_id", map.get("group_id"));
					resource.put("hos_id", map.get("hos_id"));
					resource.put("copy_code", map.get("copy_code"));
					resource.put("manage_depre_money", resource.get("add_manage_depre"));
					resource.put("ass_card_no", ass_card_no);
					resource.put("depre_money", resource.get("add_depre") + "");
					resource.put("pay_money", "0");
					resource.put("unpay_money", formatNumberZero(resource.get("price")));
					newResourceList.add(resource);
				}
				if(newResourceList.size() > 0){
					assResourceOtherMapper.addBatch(newResourceList);
				}

				List<Map<String, Object>> fileList = assFileOtherMapper.queryByAssCardNo(map);// 资产文档
				List<Map<String, Object>> newFileList = new ArrayList<Map<String,Object>>();
				for (Map<String, Object> file : fileList) {
					file.put("group_id", map.get("group_id"));
					file.put("hos_id", map.get("hos_id"));
					file.put("copy_code", map.get("copy_code"));
					file.put("ass_card_no", ass_card_no);
					String fileName = file.get("file_url").toString().substring(file.get("file_url").toString().lastIndexOf("/") + 1, file.get("file_url").toString().length());
					String assFilePath = "assfile";
					String filePath = group_id+"/"+hos_id+"/"+copy_code+"/"+basePath+"/"+assFilePath + "/04/" + ass_card_no;
					FtpUtil.copyFile(file.get("file_url").toString(), filePath);
					file.put("file_url", filePath + "/" + fileName);
					newFileList.add(file);
				}
				if(newFileList.size() > 0){
					assFileOtherMapper.addBatch(newFileList);
				}

				List<Map<String, Object>> photoList = assPhotoOtherMapper.queryByAssCardNo(map);// 资产照片
				List<Map<String, Object>> newPhotoList = new ArrayList<Map<String,Object>>();
				for (Map<String, Object> photo : photoList) {
					photo.put("group_id", map.get("group_id"));
					photo.put("hos_id", map.get("hos_id"));
					photo.put("copy_code", map.get("copy_code"));
					photo.put("ass_card_no", ass_card_no);
					String fileName = photo.get("file_url").toString().substring(photo.get("file_url").toString().lastIndexOf("/") + 1, photo.get("file_url").toString().length());
					String assFilePath = "assphoto";
					String filePath = group_id+"/"+hos_id+"/"+copy_code+"/"+basePath+"/"+assFilePath + "/04/" + ass_card_no;
					FtpUtil.copyFile(photo.get("file_url").toString(), filePath);
					photo.put("file_url", filePath + "/" + fileName);
					newPhotoList.add(photo);
				}
				if(newPhotoList.size() > 0){
					assPhotoOtherMapper.addBatch(newPhotoList);
				}


				List<Map<String, Object>> accessoryList = assAccessoryOtherMapper.queryByAssCardNo(map);// 资产附件
				List<Map<String, Object>> newAccessoryList = new ArrayList<Map<String,Object>>();
				for (Map<String, Object> accessory : accessoryList) {
					accessory.put("group_id", map.get("group_id"));
					accessory.put("hos_id", map.get("hos_id"));
					accessory.put("copy_code", map.get("copy_code"));
					accessory.put("ass_card_no", ass_card_no);
					newAccessoryList.add(accessory);
				}
				if(newAccessoryList.size() > 0){
					assAccessoryOtherMapper.addBatch(newAccessoryList);
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
	public String initAssAllotInCardOther(Map<String, Object> map) throws DataAccessException {

		try {
			if (map.get("is_import").equals("0")) {
				return initCardByAllotInOther(map);
			} else if (map.get("is_import").equals("1")) {
				return initCardByImportAllotInOther(map);
			} else {
				return "{\"warn\":\"生成失败.\"}";
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}
	}

	/**
	 * 引入出库单
	 */
	@Override
	public String initAssAllotInOther(Map<String, Object> entityMap) throws DataAccessException {
		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> mapVo = new ArrayList<Map<String, Object>>();
		try {
			List<AssAllotOutDetailOther> outDetailList = assAllotOutDetailOtherMapper
					.queryByImportAllotIn(entityMap);
					entityMap.put("bill_table", "ASS_ALLOT_IN_OTHER");
			String allot_in_no = assBaseService.getBillNOSeqNo(entityMap);
			Double price = 0.0;
			Double add_depre = 0.0;
			Double cur_money = 0.0;
			Double fore_money = 0.0;
			for (AssAllotOutDetailOther assAllotOutDetailOther : outDetailList) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("group_id", assAllotOutDetailOther.getGroup_id());
				map.put("hos_id", assAllotOutDetailOther.getHos_id());
				map.put("copy_code", assAllotOutDetailOther.getCopy_code());
				map.put("allot_in_no", allot_in_no);
				map.put("ass_id", assAllotOutDetailOther.getAss_id());
				map.put("ass_no", assAllotOutDetailOther.getAss_no());
				map.put("ass_ori_card_no", assAllotOutDetailOther.getAss_card_no());
				map.put("ass_brand", assAllotOutDetailOther.getAss_brand());
				map.put("ass_spec", assAllotOutDetailOther.getAss_spec());
				map.put("ass_model", assAllotOutDetailOther.getAss_model());
				map.put("fac_id", assAllotOutDetailOther.getFac_id());
				map.put("fac_no", assAllotOutDetailOther.getFac_no());
				map.put("price", assAllotOutDetailOther.getPrice() + "");
				map.put("add_depre", assAllotOutDetailOther.getAdd_depre());
				map.put("add_depre_month", assAllotOutDetailOther.getAdd_depre_month());
				map.put("cur_money", assAllotOutDetailOther.getCur_money() + "");
				map.put("fore_money", assAllotOutDetailOther.getFore_money() + "");
				map.put("note", assAllotOutDetailOther.getNote());
				price = price + assAllotOutDetailOther.getPrice();
				add_depre = add_depre + assAllotOutDetailOther.getAdd_depre();
				cur_money = cur_money + assAllotOutDetailOther.getCur_money();
				fore_money = fore_money + assAllotOutDetailOther.getFore_money();
				listVo.add(map);
			}
			// 主表增加
			entityMap.put("allot_in_no", allot_in_no);
			entityMap.put("state", "0");
			entityMap.put("note", "引入调剂出库");
			assAllotInOtherMapper.add(entityMap);
			assBaseService.updateAssBillNoMaxNo(entityMap);
			// 明细表增加
			assAllotInDetailOtherMapper.addBatch(listVo);

			for (int i = 0; i < entityMap.get("allot_out_nos").toString().split(",").length; i++) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("group_id", entityMap.get("group_id"));
				map.put("hos_id", entityMap.get("hos_id"));
				map.put("copy_code", entityMap.get("copy_code"));
				map.put("allot_in_no", allot_in_no);
				map.put("out_group_id", entityMap.get("out_group_id"));
				map.put("out_hos_id", entityMap.get("out_hos_id"));
				map.put("out_copy_code", entityMap.get("out_copy_codes").toString().split(",")[i].replaceAll("'", ""));
				map.put("allot_out_no", entityMap.get("allot_out_nos").toString().split(",")[i].replaceAll("'", ""));
				mapVo.add(map);
			}
			// 增加关系表
			assAllotMapOtherMapper.addBatch(mapVo);

			entityMap.put("price", price + "");
			entityMap.put("add_depre", add_depre + "");
			entityMap.put("cur_money", cur_money + "");
			entityMap.put("fore_money", fore_money + "");
			// 计算主表金额
			assAllotInOtherMapper.updateInMoney(entityMap);
			return "{\"msg\":\"引入成功.\",\"state\":\"true\",\"update_para\":\"" + entityMap.get("group_id") + "|"
					+ entityMap.get("hos_id") + "|" + entityMap.get("copy_code") + "|" + allot_in_no + "|1\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}

	}
	
	/**
	 * 其他固定资产  资产调剂入库 批量打印  新版打印  调用的方法
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	@Override
	public Map<String, Object> queryAssAllotInOtherByPrintTemlatePrint(Map<String, Object> entityMap)throws DataAccessException {
		
		try{
			
			Map<String,Object> reMap=new HashMap<String,Object>();
			WebApplicationContext context = ContextLoader.getCurrentWebApplicationContext();
			AssAllotInOtherMapper assAllotInOtherMapper = (AssAllotInOtherMapper)context.getBean("assAllotInOtherMapper");
			 
			//主页面 批量打印查询
			if("1".equals(String.valueOf(entityMap.get("p_num")))){
				
				//查询 其他固定资产 资产调剂入库 入库主表
				List<Map<String,Object>> map= assAllotInOtherMapper.queryAssAllotInOtherPrintTemlateByMainBatch(entityMap);
				//查询 其他固定资产  资产调剂入库 入库明细表
				List<Map<String,Object>> list= assAllotInOtherMapper.queryAssAllotInOtherPrintTemlateByDetail(entityMap);
				
				reMap.put("main", map);
				
				reMap.put("detail", list); 
				
				return reMap;
				
			}else{ //修改页面 打印查询
				//
				Map<String,Object> map= assAllotInOtherMapper.queryAssAllotInOtherPrintTemlateByMain(entityMap);
				//查询 其他固定资产  资产调剂入库 入库明细表
				List<Map<String,Object>> list= assAllotInOtherMapper.queryAssAllotInOtherPrintTemlateByDetail(entityMap);
				
			
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
	 * 其他固定资产  资产调剂入库  入库单状态查询（打印校验数据用）
	 */
	@Override
	public List<String> queryAssAllotInOtherState(Map<String, Object> mapVo) throws DataAccessException {
		
		return assAllotInOtherMapper.queryAssAllotInOtherState(mapVo);
	}

	private String initCardByAllotBatchInOther(Map<String, Object> map) {
		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();
		try {
			String basePath = "ass";
			String group_id = map.get("group_id").toString();
			String hos_id = map.get("hos_id").toString();
			String copy_code = map.get("copy_code").toString();
			String assTwoPath = "assbartwo";
			String assOnePath = "assbarone";
			String twoFilePath = group_id + "/" + hos_id + "/" + copy_code + "/" + basePath + "/" + assTwoPath + "/04/";// 资产性质
			String oneFilePath = group_id + "/" + hos_id + "/" + copy_code + "/" + basePath + "/" + assOnePath + "/04/";// 资产性质
			List<Map<String, Object>> list = assAllotInDetailOtherMapper.queryByInit(map);

			for (int i = 0; i < list.size(); i++) {
				List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
				mapList.add(list.get(i));
				delCard(mapList);// 先删除卡片
			}
			
			for (Map<String, Object> temp : list) {
				Map<String, Object> entityMap = new HashMap<String, Object>();
				String ass_card_no = getBillNOSeqNo("ASS_CARD_OTHER");
				updateAssBillNoMaxNo("ASS_CARD_OTHER");
				entityMap.put("group_id", map.get("group_id"));
				entityMap.put("hos_id", map.get("hos_id"));
				entityMap.put("copy_code", map.get("copy_code"));
				entityMap.put("ass_card_no", ass_card_no);
				entityMap.put("ass_ori_card_no", temp.get("ass_ori_card_no"));
				entityMap.put("ass_id", temp.get("ass_id"));
				entityMap.put("ass_no", temp.get("ass_no"));
				entityMap.put("gb_code", temp.get("gb_code"));
				entityMap.put("ass_spec", temp.get("ass_spec"));
				entityMap.put("ass_mondl", temp.get("ass_model"));
				entityMap.put("ass_brand", temp.get("ass_brand"));
				entityMap.put("ass_amount", "1");
				entityMap.put("unit_code", temp.get("unit_code"));
				entityMap.put("fac_no", temp.get("fac_no"));
				entityMap.put("fac_id", temp.get("fac_id"));
				entityMap.put("ven_id", null);
				entityMap.put("ven_no", null);
				entityMap.put("is_dept", "0");
				entityMap.put("store_id", temp.get("in_store_id"));
				entityMap.put("store_no", temp.get("in_store_no"));
				entityMap.put("proc_store_id", map.get("in_store_id"));
				entityMap.put("proc_store_no", map.get("in_store_no"));
				Map<String, Object> deptMap = matStoreMapper.queryByCode(entityMap);

				if (deptMap.get("dept_id") != null && !deptMap.get("dept_id").equals("")) {
					Map<String, Object> deptNoMap = new HashMap<String, Object>();
					deptNoMap.put("group_id", deptMap.get("group_id"));
					deptNoMap.put("hos_id", deptMap.get("hos_id"));
					deptNoMap.put("dept_id", deptMap.get("dept_id"));
					DeptDict deptDict = deptDictMapper.queryDeptDictByDeptCode(deptNoMap);
					entityMap.put("dept_id", deptDict.getDept_id());
					entityMap.put("dept_no", deptDict.getDept_no());
				}

				entityMap.put("price", formatNumberZero(temp.get("price")));
				entityMap.put("depre_money", formatNumberZero(temp.get("add_depre")));
				entityMap.put("add_depre_month", temp.get("add_depre_month"));
				entityMap.put("manage_depre_money", "0");
				entityMap.put("cur_money", formatNumberZero(temp.get("cur_money")));
				entityMap.put("fore_money", formatNumberZero(temp.get("fore_money")));
				entityMap.put("buy_type", temp.get("bus_type_code"));
				entityMap.put("use_state", "0");
				entityMap.put("is_depr", formatNumberZero(temp.get("is_depre")));
				entityMap.put("depr_method", temp.get("ass_depre_code"));
				entityMap.put("acc_depre_amount", temp.get("depre_years"));
				entityMap.put("is_manage_depre", temp.get("is_manage_depre"));
				entityMap.put("manage_depr_method", temp.get("manage_depr_method"));
				entityMap.put("manage_depre_amount", formatNumberZero(temp.get("manage_depre_amount")));
				entityMap.put("is_measure", formatNumberZero(temp.get("is_measure")));
				entityMap.put("is_throw", "0");
				entityMap.put("pact_code", null);
				entityMap.put("ins_money", null);
				entityMap.put("ins_date", null);
				entityMap.put("accept_emp", null);
				entityMap.put("accept_date", null);
				entityMap.put("service_date", null);
				entityMap.put("ass_seq_no", null);
				entityMap.put("location", null);
				entityMap.put("note", temp.get("note"));
				entityMap.put("ass_in_no", temp.get("allot_in_no"));
				entityMap.put("allot_in_no", temp.get("allot_in_no"));
				entityMap.put("in_date", null);
				entityMap.put("run_date", null);
				entityMap.put("is_bar", formatNumberZero(temp.get("is_bar")));
				entityMap.put("bar_type", temp.get("bar_type"));
				entityMap.put("bar_code", ass_card_no);
				entityMap.put("is_init", "0");
				entityMap.put("dispose_type", null);
				entityMap.put("dispose_cost", null);
				entityMap.put("dispose_income", null);
				entityMap.put("dispose_tax", null);
				entityMap.put("ass_purpose", null);
				entityMap.put("proj_id", null);
				entityMap.put("proj_no", null);
				entityMap.put("source_id", 1);
				entityMap.put("pay_money", "0");
				entityMap.put("unpay_money", formatNumberZero(temp.get("price")));
				if (temp.get("is_bar") != null && !temp.get("is_bar").equals("")) {
					if (temp.get("is_bar").toString().equals("1")) {
						if (temp.get("bar_type") != null && !temp.get("bar_type").equals("")) {
							if (temp.get("bar_type").toString().equals("1")) {
								FtpUtil.getBarcodeWriteFile(ass_card_no, "", oneFilePath, ass_card_no + ".png");// 一维码
								entityMap.put("bar_url", oneFilePath + ass_card_no + ".png");

							} else if (temp.get("bar_type").toString().equals("2")) {
								FtpUtil.getQRWriteFile(ass_card_no, "", twoFilePath, ass_card_no + ".png");// 二维码
								entityMap.put("bar_url", twoFilePath + ass_card_no + ".png");
							}
						}
					}

				}

				if (entityMap.get("dept_id") != null && !entityMap.get("dept_id").equals("")
						&& entityMap.get("dept_no") != null && !entityMap.get("dept_no").equals("")) {
					entityMap.put("area", 1);
					entityMap.put("ass_year", getAssYear());
					entityMap.put("ass_month", getAssMonth());
				} else {
					return "{\"warn\":\"仓库没有维护所在科室,不能生成! \"}";
				}

				listVo.add(entityMap);
			}
			assCardOtherMapper.addBatch(listVo);
			assResourceOtherMapper.addBatch(listVo);
			assShareDeptROtherMapper.addBatch(listVo);
			assShareDeptOtherMapper.addBatch(listVo);

			return "{\"msg\":\"生成成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}
	}
	
	// 引入出库单生成方式
	private String initCardByImportAllotBatchInOther(Map<String, Object> map) {
		String basePath = "ass";
		String group_id = map.get("group_id").toString();
		String hos_id = map.get("hos_id").toString();
		String copy_code = map.get("copy_code").toString();
		String assTwoPath = "assbartwo";
		String assOnePath = "assbarone";
		String twoFilePath = group_id + "/" + hos_id + "/" + copy_code + "/" + basePath + "/" + assTwoPath + "/04/";// 资产性质
		String oneFilePath = group_id + "/" + hos_id + "/" + copy_code + "/" + basePath + "/" + assOnePath + "/04/";// 资产性质
		map.put("ass_card_no", map.get("ass_ori_card_nos"));
		try {
			List<Map<String, Object>> list = assAllotInDetailOtherMapper.queryByInit(map);// 查找对应明细数据

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
				List<AssCardOther> cardList = (List<AssCardOther>)assCardOtherMapper.queryByAssCardOldNo(entiMap);// 查询原始的卡片
				entiMap.put("UNIT_CODE", map2.get("UNIT_CODE"));
				entiMap.put("ass_in_no", map2.get("ass_in_no"));
				entiMap.put("ass_depre_code", map2.get("ass_depre_code"));
				entiMap.put("fore_money", map2.get("fore_money"));
				entiMap.put("ass_id", map2.get("ass_id"));
				entiMap.put("add_depre_month", map2.get("add_depre_month"));
				entiMap.put("depre_years", map2.get("depre_years"));
				entiMap.put("out_group_id", map2.get("out_group_id"));
				entiMap.put("allot_in_no", map2.get("allot_in_no")+"");
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
					initCardByAllotInOther(entiMap);
				} else {
					
				for(AssCardOther assCardOther : cardList){
					Map<String, Object> entityMap = new HashMap<String, Object>();
					String ass_card_no = getBillNOSeqNo("ASS_CARD_OTHER");
					updateAssBillNoMaxNo("ASS_CARD_OTHER");
					entityMap.put("group_id", map.get("group_id"));
					entityMap.put("hos_id", map.get("hos_id"));
					entityMap.put("copy_code", map.get("copy_code"));
					entityMap.put("ass_card_no", ass_card_no);
					entityMap.put("ass_ori_card_no", assCardOther.getAss_card_no());
					entityMap.put("ass_id", assCardOther.getAss_id());
					entityMap.put("ass_no", assCardOther.getAss_no());
					entityMap.put("gb_code", assCardOther.getGb_code());
					entityMap.put("ass_spec", map2.get("ass_spec"));
					entityMap.put("ass_mondl", map2.get("ass_mondl"));
					entityMap.put("ass_brand", map2.get("ass_brand"));
					entityMap.put("ass_amount", "1");
					entityMap.put("unit_code", assCardOther.getUnit_code());
					entityMap.put("fac_no", map2.get("fac_no"));
					entityMap.put("fac_id", map2.get("fac_id"));
					entityMap.put("ven_id", assCardOther.getVen_id());
					entityMap.put("ven_no", assCardOther.getVen_no());
					entityMap.put("is_dept", formatNumberZero(assCardOther.getIs_dept()));
					entityMap.put("store_id", map.get("in_store_id"));
					entityMap.put("store_no", map.get("in_store_no"));
					entityMap.put("proc_store_id", map.get("in_store_id"));
					entityMap.put("proc_store_no", map.get("in_store_no"));
					Map<String, Object> deptMap = matStoreMapper.queryByCode(entityMap);

					if (deptMap.get("dept_id") != null && !deptMap.get("dept_id").equals("")) {
						Map<String, Object> deptNoMap = new HashMap<String, Object>();
						deptNoMap.put("group_id", deptMap.get("group_id"));
						deptNoMap.put("hos_id", deptMap.get("hos_id"));
						deptNoMap.put("dept_id", deptMap.get("dept_id"));
						DeptDict deptDict = deptDictMapper.queryDeptDictByDeptCode(deptNoMap);
						entityMap.put("dept_id", deptDict.getDept_id());
						entityMap.put("dept_no", deptDict.getDept_no());
					}
					entityMap.put("price", map2.get("price"));
					entityMap.put("depre_money", map2.get("add_depre"));
					entityMap.put("add_depre_month", map2.get("add_depre_month"));
					entityMap.put("cur_money", map2.get("cur_money"));
					entityMap.put("fore_money", map2.get("fore_money"));
					entityMap.put("buy_type", "03");
					entityMap.put("acc_depre_amount", formatNumberZero(assCardOther.getAcc_depre_amount()));
					entityMap.put("manage_depre_amount", formatNumberZero(assCardOther.getManage_depre_amount()));
					entityMap.put("manage_depre_money", "0");
					entityMap.put("add_manage_month", "0");
					entityMap.put("is_manage_depre", formatNumberZero(assCardOther.getIs_manage_depre()));
					entityMap.put("manage_depr_method", assCardOther.getManage_depr_method());
					entityMap.put("depr_method", assCardOther.getDepr_method());
					entityMap.put("is_depr", formatNumberZero(assCardOther.getIs_depr()));
					entityMap.put("use_state", "0");
					entityMap.put("is_measure", formatNumberZero(assCardOther.getIs_measure()));
					entityMap.put("is_throw", formatNumberZero(assCardOther.getIs_throw()));
					entityMap.put("pact_code", assCardOther.getPact_code());
					entityMap.put("ins_money", assCardOther.getIns_money());
					entityMap.put("ins_date", DateUtil.dateToString(assCardOther.getIns_date(), "yyyy-MM-dd"));
					entityMap.put("accept_emp", assCardOther.getAccept_emp());
					entityMap.put("accept_date", DateUtil.dateToString(assCardOther.getAccept_date(), "yyyy-MM-dd"));
					entityMap.put("service_date", DateUtil.dateToString(assCardOther.getService_date(), "yyyy-MM-dd"));
					entityMap.put("ass_seq_no", assCardOther.getAss_seq_no());
					entityMap.put("location", assCardOther.getLocation());
					entityMap.put("note", assCardOther.getNote());
					entityMap.put("ass_in_no", map.get("allot_in_no"));
					entityMap.put("allot_in_no", map.get("allot_in_no"));
					entityMap.put("in_date", null);
					entityMap.put("run_date", null);
					entityMap.put("is_bar", formatNumberZero(assCardOther.getIs_bar()));
					entityMap.put("bar_type", assCardOther.getBar_type());
					entityMap.put("bar_code", ass_card_no);
					entityMap.put("is_init", "0");
					entityMap.put("dispose_type", null);
					entityMap.put("dispose_cost", null);
					entityMap.put("dispose_income", null);
					entityMap.put("dispose_tax", null);
					entityMap.put("ass_purpose", assCardOther.getAss_purpose());
					entityMap.put("proj_id", assCardOther.getProj_id());
					entityMap.put("proj_no", assCardOther.getProj_no());

					if (assCardOther.getIs_bar() != null && !assCardOther.getIs_bar().equals("")) {
						if (assCardOther.getIs_bar() == 1) {
							if (assCardOther.getBar_type() != null && !assCardOther.getBar_type().equals("")) {
								if (assCardOther.getBar_type() == 1) {
									FtpUtil.getBarcodeWriteFile(ass_card_no, "", oneFilePath, ass_card_no + ".png");// 一维码
									entityMap.put("bar_url", oneFilePath + ass_card_no + ".png");

								} else if (assCardOther.getBar_type() == 2) {
									FtpUtil.getQRWriteFile(ass_card_no, "", twoFilePath, ass_card_no + ".png");// 二维码
									entityMap.put("bar_url", twoFilePath + ass_card_no + ".png");
								}
							}
						}

					}

					if (entityMap.get("dept_id") != null && !entityMap.get("dept_id").equals("")
							&& entityMap.get("dept_no") != null && !entityMap.get("dept_no").equals("")) {
						entityMap.put("area", 1);
						entityMap.put("ass_year", getAssYear());
						entityMap.put("ass_month", getAssMonth());
					} else {
						return "{\"warn\":\"仓库没有维护所在科室,不能生成! \"}";
					}

					List<Map<String, Object>> shareDeptList = new ArrayList<Map<String, Object>>();
					shareDeptList.add(entityMap);

					assCardOtherMapper.add(entityMap);// 插入卡片
					assShareDeptROtherMapper.addBatch(shareDeptList);
					assShareDeptOtherMapper.addBatch(shareDeptList);
					Map<String, Object> allotMap = new HashMap<String, Object>();
					allotMap.put("group_id", map.get("out_group_id"));
					allotMap.put("hos_id", map.get("out_hos_id"));
					allotMap.put("copy_code", map.get("copy_code"));
					allotMap.put("ass_card_no", assCardOther.getAss_card_no());
					List<Map<String, Object>> resourceList = assAllotOutSourceOtherMapper
							.queryByAlloutOutNoAndAssCardNo(allotMap);// 查询资金来源
					List<Map<String, Object>> newResourceList = new ArrayList<Map<String, Object>>();
					for (Map<String, Object> resource : resourceList) {
						resource.put("group_id", map.get("group_id"));
						resource.put("hos_id", map.get("hos_id"));
						resource.put("copy_code", map.get("copy_code"));
						resource.put("ass_card_no", ass_card_no);
						resource.put("manage_depre_money", resource.get("add_manage_depre") + "");
						resource.put("depre_money", resource.get("add_depre") + "");
						resource.put("pay_money", "0");
						resource.put("unpay_money", formatNumberZero(resource.get("price")));
						newResourceList.add(resource);
					}
					if (newResourceList.size() > 0) {
						assResourceOtherMapper.addBatch(newResourceList);
					}

					List<Map<String, Object>> fileList = assFileOtherMapper.queryByAssCardNo(map2);// 资产文档
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
								+ "/01/" + ass_card_no;
						FtpUtil.copyFile(file.get("file_url").toString(), filePath);
						file.put("file_url", filePath + "/" + fileName);
						newFileList.add(file);
					}
					if (newFileList.size() > 0) {
						assFileOtherMapper.addBatch(newFileList);
					}

					List<Map<String, Object>> photoList = assPhotoOtherMapper.queryByAssCardNo(map2);// 资产照片
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
								+ "/01/" + ass_card_no;
						FtpUtil.copyFile(photo.get("file_url").toString(), filePath);
						photo.put("file_url", filePath + "/" + fileName);
						newPhotoList.add(photo);
					}
					if (newPhotoList.size() > 0) {
						assPhotoOtherMapper.addBatch(newPhotoList);
					}

					List<Map<String, Object>> accessoryList = assAccessoryOtherMapper.queryByAssCardNo(map2);// 资产附件
					List<Map<String, Object>> newAccessoryList = new ArrayList<Map<String, Object>>();
					for (Map<String, Object> accessory : accessoryList) {
						accessory.put("group_id", map.get("group_id"));
						accessory.put("hos_id", map.get("hos_id"));
						accessory.put("copy_code", map.get("copy_code"));
						accessory.put("ass_card_no", ass_card_no);
						newAccessoryList.add(accessory);
					}
					if (newAccessoryList.size() > 0) {
						assAccessoryOtherMapper.addBatch(newAccessoryList);
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
	
	@Override
	public String initAssAllotInBatchCardOther(Map<String, Object> map)
			throws DataAccessException {
		try {
			if (map.get("is_import").equals("0")) {
				return initCardByAllotBatchInOther(map);
			} else if (map.get("is_import").equals("1")) {
				return initCardByImportAllotBatchInOther(map);
			} else {
				return "{\"warn\":\"生成失败.\"}";
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}
	}

	@Override
	public String queryDetails(Map<String, Object> entityMap)
			throws DataAccessException {
		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		if (sysPage.getTotal() == -1) {

			List<Map<String, Object>> list = (List<Map<String, Object>>) assAllotInOtherMapper.queryDetails(entityMap);

			return ChdJson.toJson(list);

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

			List<Map<String, Object>> list = (List<Map<String, Object>>) assAllotInOtherMapper.queryDetails(entityMap, rowBounds);

			PageInfo page = new PageInfo(list);

			return ChdJson.toJson(list, page.getTotal());

		}
	}
}
