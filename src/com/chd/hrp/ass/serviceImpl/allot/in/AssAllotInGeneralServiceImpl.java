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
import com.chd.hrp.ass.dao.accessory.AssAccessoryGeneralMapper;
import com.chd.hrp.ass.dao.allot.in.AssAllotInDetailGeneralMapper;
import com.chd.hrp.ass.dao.allot.in.AssAllotInGeneralMapper;
import com.chd.hrp.ass.dao.allot.map.AssAllotMapGeneralMapper;
import com.chd.hrp.ass.dao.allot.out.AssAllotOutDetailGeneralMapper;
import com.chd.hrp.ass.dao.allot.out.source.AssAllotOutSourceGeneralMapper;
import com.chd.hrp.ass.dao.card.AssCardGeneralMapper;
import com.chd.hrp.ass.dao.depre.acc.AssDepreAccGeneralMapper;
import com.chd.hrp.ass.dao.depre.manager.AssDepreManageGeneralMapper;
import com.chd.hrp.ass.dao.dict.AssBillNoMapper;
import com.chd.hrp.ass.dao.file.AssFileGeneralMapper;
import com.chd.hrp.ass.dao.pay.AssPayStageGeneralMapper;
import com.chd.hrp.ass.dao.photo.AssPhotoGeneralMapper;
import com.chd.hrp.ass.dao.resource.AssResourceGeneralMapper;
import com.chd.hrp.ass.dao.share.AssShareDeptRGeneralMapper;
import com.chd.hrp.ass.dao.share.AssShareDeptGeneralMapper;
import com.chd.hrp.ass.entity.allot.in.AssAllotInGeneral;
import com.chd.hrp.ass.entity.allot.out.AssAllotOutDetailGeneral;
import com.chd.hrp.ass.entity.card.AssCardGeneral;
import com.chd.hrp.ass.entity.dict.AssBillNo;
import com.chd.hrp.ass.entity.file.AssFileGeneral;
import com.chd.hrp.ass.entity.photo.AssPhotoGeneral;
import com.chd.hrp.ass.service.allot.in.AssAllotInGeneralService;
import com.chd.hrp.ass.service.base.AssBaseService;
import com.chd.hrp.mat.dao.info.basic.MatStoreMapper;
import com.chd.hrp.sys.dao.DeptDictMapper;
import com.chd.hrp.sys.entity.DeptDict;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Description:
 * 050901 资产无偿调拨入库单主表（一般设备）
 * @Table:
 * ASS_ALLOT_IN_GENERAL
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

 
@Service("assAllotInGeneralService")
public class AssAllotInGeneralServiceImpl implements AssAllotInGeneralService {    

	private static Logger logger = Logger.getLogger(AssAllotInGeneralServiceImpl.class);
	// 引入DAO操作
	@Resource(name = "assAllotInGeneralMapper")
	private final AssAllotInGeneralMapper assAllotInGeneralMapper = null;

	@Resource(name = "assAllotInDetailGeneralMapper")
	private final AssAllotInDetailGeneralMapper assAllotInDetailGeneralMapper = null;

	@Resource(name = "assAllotOutDetailGeneralMapper")
	private final AssAllotOutDetailGeneralMapper assAllotOutDetailGeneralMapper = null;
	
	@Resource(name = "assAllotOutSourceGeneralMapper")
	private final AssAllotOutSourceGeneralMapper assAllotOutSourceGeneralMapper = null;

	@Resource(name = "assAllotMapGeneralMapper")
	private final AssAllotMapGeneralMapper assAllotMapGeneralMapper = null;

	@Resource(name = "assCardGeneralMapper")
	private final AssCardGeneralMapper assCardGeneralMapper = null;

	@Resource(name = "assResourceGeneralMapper")
	private final AssResourceGeneralMapper assResourceGeneralMapper = null;

	@Resource(name = "assShareDeptGeneralMapper")
	private final AssShareDeptGeneralMapper assShareDeptGeneralMapper = null;

	@Resource(name = "assShareDeptRGeneralMapper")
	private final AssShareDeptRGeneralMapper assShareDeptRGeneralMapper = null;

	@Resource(name = "assFileGeneralMapper")
	private final AssFileGeneralMapper assFileGeneralMapper = null;

	@Resource(name = "assPhotoGeneralMapper")
	private final AssPhotoGeneralMapper assPhotoGeneralMapper = null;

	@Resource(name = "assAccessoryGeneralMapper")
	private final AssAccessoryGeneralMapper assAccessoryGeneralMapper = null;

	@Resource(name = "assDepreAccGeneralMapper")
	private final AssDepreAccGeneralMapper assDepreAccGeneralMapper = null;

	@Resource(name = "assDepreManageGeneralMapper")
	private final AssDepreManageGeneralMapper assDepreManageGeneralMapper = null;

	@Resource(name = "assPayStageGeneralMapper")
	private final AssPayStageGeneralMapper assPayStageGeneralMapper = null;

	@Resource(name = "matStoreMapper")
	private final MatStoreMapper matStoreMapper = null;

	@Resource(name = "deptDictMapper")
	private final DeptDictMapper deptDictMapper = null;

	@Resource(name = "assBaseService")
	private final AssBaseService assBaseService = null;

	/**
	 * @Description 添加050901 资产无偿调拨入库单主表（一般设备）<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String add(Map<String, Object> entityMap) throws DataAccessException {

		// 获取对象050901 资产无偿调拨入库单主表（一般设备）
		AssAllotInGeneral assAllotInGeneral = queryByCode(entityMap);

		if (assAllotInGeneral != null) {

			return "{\"error\":\"数据重复,请重新添加.\"}";

		}

		try {

			int state = assAllotInGeneralMapper.add(entityMap);

			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 add\"}";

		}

	}

	/**
	 * @Description 批量添加050901 资产无偿调拨入库单主表（一般设备）<BR>
	 * @param entityList
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String addBatch(List<Map<String, Object>> entityList) throws DataAccessException {

		try {

			assAllotInGeneralMapper.addBatch(entityList);

			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addBatch\"}";

		}

	}

	/**
	 * @Description 更新050901 资产无偿调拨入库单主表（一般设备）<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String update(Map<String, Object> entityMap) throws DataAccessException {

		try {

			int state = assAllotInGeneralMapper.update(entityMap);

			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 update\"}";

		}

	}

	/**
	 * @Description 批量更新050901 资产无偿调拨入库单主表（一般设备）<BR>
	 * @param entityList
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String updateBatch(List<Map<String, Object>> entityList) throws DataAccessException {

		try {

			assAllotInGeneralMapper.updateBatch(entityList);

			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 updateBatch\"}";

		}

	}

	/**
	 * @Description 删除050901 资产无偿调拨入库单主表（一般设备）<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String delete(Map<String, Object> entityMap) throws DataAccessException {

		try {

			int state = assAllotInGeneralMapper.delete(entityMap);

			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 delete\"}";

		}

	}

	/**
	 * @Description 批量删除050901 资产无偿调拨入库单主表（一般设备）<BR>
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
			assAllotInDetailGeneralMapper.deleteBatch(entityList);
			assAllotMapGeneralMapper.deleteBatch(entityList);
			assAllotInGeneralMapper.deleteBatch(entityList);

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

			List<Map<String, Object>> cardList = assCardGeneralMapper.queryByAssInNo(inMapVo);
			if (cardList.size() > 0) {
				// 删除条码文件
				for (Map<String, Object> map : cardList) {
					AssCardGeneral assCardGeneral = assCardGeneralMapper.queryByCode(map);
					if (assCardGeneral.getBar_url() != null && !assCardGeneral.getBar_url().equals("")) {
						String file_name = assCardGeneral.getBar_url().substring(
								assCardGeneral.getBar_url().lastIndexOf("/") + 1, assCardGeneral.getBar_url().length());
						String path = assCardGeneral.getBar_url().substring(0,
								assCardGeneral.getBar_url().lastIndexOf("/"));
						FtpUtil.deleteFile(path, file_name);
					}
				}

				// 删除资产文档
				for (Map<String, Object> fileMap : cardList) {
					List<AssFileGeneral> assFileGeneralList = (List<AssFileGeneral>) assFileGeneralMapper
							.queryExists(fileMap);
					if (assFileGeneralList.size() > 0 && assFileGeneralList != null) {
						for (AssFileGeneral assFileGeneral : assFileGeneralList) {
							if (assFileGeneral.getFile_url() != null && !assFileGeneral.getFile_url().equals("")) {
								String file_name = assFileGeneral.getFile_url().substring(
										assFileGeneral.getFile_url().lastIndexOf("/") + 1,
										assFileGeneral.getFile_url().length());
								String path = assFileGeneral.getFile_url().substring(0,
										assFileGeneral.getFile_url().lastIndexOf("/"));
								FtpUtil.deleteFile(path, file_name);
								path = path.substring(0,path.lastIndexOf("/"));
								FtpUtil.deleteDirectory(assFileGeneral.getAss_card_no(), path);
							}
						}
					}
				}
				// 删除资产照片
				for (Map<String, Object> photoMap : cardList) {
					List<AssPhotoGeneral> assPhotoGeneralList = (List<AssPhotoGeneral>) assPhotoGeneralMapper
							.queryExists(photoMap);
					if (assPhotoGeneralList.size() > 0 && assPhotoGeneralList != null) {
						for (AssPhotoGeneral assPhotoGeneral : assPhotoGeneralList) {
							if (assPhotoGeneral.getFile_url() != null && !assPhotoGeneral.getFile_url().equals("")) {
								String file_name = assPhotoGeneral.getFile_url().substring(
										assPhotoGeneral.getFile_url().lastIndexOf("/") + 1,
										assPhotoGeneral.getFile_url().length());
								String path = assPhotoGeneral.getFile_url().substring(0,
										assPhotoGeneral.getFile_url().lastIndexOf("/"));
								FtpUtil.deleteFile(path, file_name);
								path = path.substring(0,path.lastIndexOf("/"));
								FtpUtil.deleteDirectory(assPhotoGeneral.getAss_card_no(), path);
							}
						}
					}
				}
				assShareDeptRGeneralMapper.deleteBatch(cardList);
				assShareDeptGeneralMapper.deleteBatch(cardList);
				assResourceGeneralMapper.deleteBatch(cardList);
				assFileGeneralMapper.deleteBatch(cardList);
				assPhotoGeneralMapper.deleteBatch(cardList);
				assAccessoryGeneralMapper.deleteBatch(cardList);
				assPayStageGeneralMapper.deleteBatch(cardList);
				assDepreAccGeneralMapper.deleteBatch(cardList);
				assDepreManageGeneralMapper.deleteBatch(cardList);
				assCardGeneralMapper.deleteBatchByAssInNo(cardList);
			}

		} catch (Exception e) {
			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());
		}
	}

	/**
	 * @Description 添加050901 资产无偿调拨入库单主表（一般设备）<BR>
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

		List<AssAllotInGeneral> list = (List<AssAllotInGeneral>) assAllotInGeneralMapper.queryExists(mapVo);
		try {
			if (list.size() > 0) {
				int state = assAllotInGeneralMapper.update(entityMap);
			} else {

				if(entityMap.get("create_date") != null && !"".equals(entityMap.get("create_date"))){
					entityMap.put("year", entityMap.get("create_date").toString().substring(0,4));
					entityMap.put("month", entityMap.get("create_date").toString().substring(5,7));
				}

				entityMap.put("bill_table", "ASS_ALLOT_IN_GENERAL");
				String allot_in_no = assBaseService.getBillNOSeqNo(entityMap);

				entityMap.put("allot_in_no", allot_in_no);
				int state = assAllotInGeneralMapper.add(entityMap);
				assBaseService.updateAssBillNoMaxNo(entityMap);
			}

			List<Map> detail = ChdJson.fromJsonAsList(Map.class, entityMap.get("ParamVo").toString());

			List<Map<String, Object>> cardList = assCardGeneralMapper.queryByAssInNo(mapVo);

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

			assAllotInDetailGeneralMapper.delete(entityMap);
			assAllotInDetailGeneralMapper.addBatch(listVo);
			entityMap.put("price", price + "");
			entityMap.put("add_depre", add_depre + "");
			entityMap.put("cur_money", cur_money + "");
			entityMap.put("fore_money", fore_money + "");

			assAllotInGeneralMapper.updateInMoney(entityMap);
			return "{\"msg\":\"保存成功.\",\"state\":\"true\",\"allot_in_no\":\"" + entityMap.get("allot_in_no").toString()
					+ "\",\"price\":\"" + price + "\",\"add_depre\":\"" + add_depre + "\",\"cur_money\":\"" + cur_money
					+ "\",\"fore_money\":\"" + fore_money + "\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}

	}

	/**
	 * @Description 查询结果集050901 资产无偿调拨入库单主表（一般设备）<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String query(Map<String, Object> entityMap) throws DataAccessException {

		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		if (sysPage.getTotal() == -1) {

			List<AssAllotInGeneral> list = (List<AssAllotInGeneral>) assAllotInGeneralMapper.query(entityMap);

			return ChdJson.toJson(list);

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

			List<AssAllotInGeneral> list = (List<AssAllotInGeneral>) assAllotInGeneralMapper.query(entityMap,
					rowBounds);

			PageInfo page = new PageInfo(list);

			return ChdJson.toJson(list, page.getTotal());

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

	/**
	 * @Description 获取对象050901 资产无偿调拨入库单主表（一般设备）<BR>
	 * @param entityMap<BR>
	 *            参数为主键
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public <T> T queryByCode(Map<String, Object> entityMap) throws DataAccessException {
		return assAllotInGeneralMapper.queryByCode(entityMap);
	}

	/**
	 * @Description 获取050901 资产无偿调拨入库单主表（一般设备）<BR>
	 * @param entityMap<BR>
	 *            参数为要检索的字段
	 * @return AssAllotInGeneral
	 * @throws DataAccessException
	 */
	@Override
	public <T> T queryByUniqueness(Map<String, Object> entityMap) throws DataAccessException {
		return assAllotInGeneralMapper.queryByUniqueness(entityMap);
	}

	/**
	 * @Description 获取050901 资产无偿调拨入库单主表（一般设备）<BR>
	 * @param entityMap<BR>
	 *            参数为要检索的字段
	 * @return List<AssAllotInGeneral>
	 * @throws DataAccessException
	 */
	@Override
	public List<?> queryExists(Map<String, Object> entityMap) throws DataAccessException {
		return assAllotInGeneralMapper.queryExists(entityMap);
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
			assAllotInGeneralMapper.updateAudit(entityMap);

			return "{\"msg\":\"审核成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());
		}
	}

	@Override
	public String updateReAudit(List<Map<String, Object>> entityMap) throws DataAccessException {
		try {
			assAllotInGeneralMapper.updateReAudit(entityMap);

			return "{\"msg\":\"销审成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());
		}
	}

	@Override
	public String updateConfirm(List<Map<String, Object>> entityMap) throws DataAccessException {
		try {
			assAllotInGeneralMapper.updateConfirm(entityMap);
			assCardGeneralMapper.updateConfirm(entityMap);

			return "{\"msg\":\"确认成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());
		}
	}

	// 普通录入生成方式
	private String initCardByAllotInGeneral(Map<String, Object> map) {
		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();
		try {
			String basePath = "ass";
			String group_id = map.get("group_id").toString();
			String hos_id = map.get("hos_id").toString();
			String copy_code = map.get("copy_code").toString();
			String assTwoPath = "assbartwo";
			String assOnePath = "assbarone";
			String twoFilePath = group_id + "/" + hos_id + "/" + copy_code + "/" + basePath + "/" + assTwoPath + "/03/";// 资产性质
			String oneFilePath = group_id + "/" + hos_id + "/" + copy_code + "/" + basePath + "/" + assOnePath + "/03/";// 资产性质
			List<Map<String, Object>> list = assAllotInDetailGeneralMapper.queryByInit(map);

			delCard(list);
			for (Map<String, Object> temp : list) {
				Map<String, Object> entityMap = new HashMap<String, Object>();
				String ass_card_no = getBillNOSeqNo("ASS_CARD_GENERAL");
				updateAssBillNoMaxNo("ASS_CARD_GENERAL");
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
			assCardGeneralMapper.addBatch(listVo);
			assResourceGeneralMapper.addBatch(listVo);
			assShareDeptRGeneralMapper.addBatch(listVo);
			assShareDeptGeneralMapper.addBatch(listVo);

			return "{\"msg\":\"生成成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}
	}

	// 引入出库单生成方式
	private String initCardByImportAllotInGeneral(Map<String, Object> map) {
		String basePath = "ass";
		String group_id = map.get("group_id").toString();
		String hos_id = map.get("hos_id").toString();
		String copy_code = map.get("copy_code").toString();
		String assTwoPath = "assbartwo";
		String assOnePath = "assbarone";
		String twoFilePath = group_id + "/" + hos_id + "/" + copy_code + "/" + basePath + "/" + assTwoPath + "/03/";// 资产性质
		String oneFilePath = group_id + "/" + hos_id + "/" + copy_code + "/" + basePath + "/" + assOnePath + "/03/";// 资产性质
		map.put("ass_card_no", map.get("ass_ori_card_no"));
		try {
			AssCardGeneral assCardGeneral = assCardGeneralMapper.queryByCode(map);// 查询原始的卡片
			if (assCardGeneral == null) {// 通过原始卡片没有找到对应卡片-以普通方式生成卡片
				return initCardByAllotInGeneral(map);
			} else {
				List<Map<String, Object>> list = assAllotInDetailGeneralMapper.queryByInit(map);// 查找对应明细数据
				delCard(list);// 先删除卡片
				
				Map<String, Object> temp = list.get(0);

				Map<String, Object> entityMap = new HashMap<String, Object>();
				String ass_card_no = getBillNOSeqNo("ASS_CARD_GENERAL");
				updateAssBillNoMaxNo("ASS_CARD_GENERAL");
				entityMap.put("group_id", map.get("group_id"));
				entityMap.put("hos_id", map.get("hos_id"));
				entityMap.put("copy_code", map.get("copy_code"));
				entityMap.put("ass_card_no", ass_card_no);
				entityMap.put("ass_ori_card_no", map.get("ass_ori_card_no"));
				entityMap.put("ass_id", assCardGeneral.getAss_id());
				entityMap.put("ass_no", assCardGeneral.getAss_no());
				entityMap.put("gb_code", assCardGeneral.getGb_code());
				entityMap.put("ass_spec", temp.get("ass_spec"));
				entityMap.put("ass_mondl", temp.get("ass_model"));
				entityMap.put("ass_brand", temp.get("ass_brand"));
				entityMap.put("ass_amount", "1");
				entityMap.put("unit_code", assCardGeneral.getUnit_code());
				entityMap.put("fac_no", temp.get("fac_no"));
				entityMap.put("fac_id", temp.get("fac_id"));
				entityMap.put("ven_id", assCardGeneral.getVen_id());
				entityMap.put("ven_no", assCardGeneral.getVen_no());
				entityMap.put("is_dept", formatNumberZero(assCardGeneral.getIs_dept()));
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
				entityMap.put("price", formatNumberZero(list.get(0).get("price")));
				entityMap.put("depre_money", formatNumberZero(list.get(0).get("add_depre")));
				entityMap.put("add_depre_month", formatNumberZero(list.get(0).get("add_depre_month")));
				entityMap.put("cur_money", formatNumberZero(list.get(0).get("cur_money")));
				entityMap.put("fore_money", formatNumberZero(list.get(0).get("fore_money")));
				entityMap.put("buy_type", "03");
				entityMap.put("acc_depre_amount", formatNumberZero(assCardGeneral.getAcc_depre_amount()));
				entityMap.put("manage_depre_amount", formatNumberZero(assCardGeneral.getManage_depre_amount()));
				entityMap.put("manage_depre_money", "0");
				entityMap.put("add_manage_month", "0");
				entityMap.put("is_manage_depre", formatNumberZero(assCardGeneral.getIs_manage_depre()));
				entityMap.put("manage_depr_method", assCardGeneral.getManage_depr_method());
				entityMap.put("depr_method", assCardGeneral.getDepr_method());
				entityMap.put("is_depr", formatNumberZero(assCardGeneral.getIs_depr()));
				entityMap.put("use_state", "0");
				entityMap.put("is_measure", formatNumberZero(assCardGeneral.getIs_measure()));
				entityMap.put("is_throw", formatNumberZero(assCardGeneral.getIs_throw()));
				entityMap.put("pact_code", assCardGeneral.getPact_code());
				entityMap.put("ins_money", assCardGeneral.getIns_money());
				entityMap.put("ins_date", DateUtil.dateToString(assCardGeneral.getIns_date(), "yyyy-MM-dd"));
				entityMap.put("accept_emp", assCardGeneral.getAccept_emp());
				entityMap.put("accept_date", DateUtil.dateToString(assCardGeneral.getAccept_date(), "yyyy-MM-dd"));
				entityMap.put("service_date", DateUtil.dateToString(assCardGeneral.getService_date(), "yyyy-MM-dd"));
				entityMap.put("ass_seq_no", assCardGeneral.getAss_seq_no());
				entityMap.put("location", assCardGeneral.getLocation());
				entityMap.put("note", assCardGeneral.getNote());
				entityMap.put("ass_in_no", map.get("allot_in_no"));
				entityMap.put("allot_in_no", map.get("allot_in_no"));
				entityMap.put("in_date", null);
				entityMap.put("run_date", null);
				entityMap.put("is_bar", formatNumberZero(assCardGeneral.getIs_bar()));
				entityMap.put("bar_type", assCardGeneral.getBar_type());
				entityMap.put("bar_code", ass_card_no);
				entityMap.put("is_init", "0");
				entityMap.put("dispose_type", null);
				entityMap.put("dispose_cost", null);
				entityMap.put("dispose_income", null);
				entityMap.put("dispose_tax", null);
				entityMap.put("ass_purpose", assCardGeneral.getAss_purpose());
				entityMap.put("proj_id", assCardGeneral.getProj_id());
				entityMap.put("proj_no", assCardGeneral.getProj_no());

				if (assCardGeneral.getIs_bar() != null && !assCardGeneral.getIs_bar().equals("")) {
					if (assCardGeneral.getIs_bar() == 1) {
						if (assCardGeneral.getBar_type() != null && !assCardGeneral.getBar_type().equals("")) {
							if (assCardGeneral.getBar_type() == 1) {
								FtpUtil.getBarcodeWriteFile(ass_card_no, "", oneFilePath, ass_card_no + ".png");// 一维码
								entityMap.put("bar_url", oneFilePath + ass_card_no + ".png");

							} else if (assCardGeneral.getBar_type() == 2) {
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
				
				assCardGeneralMapper.add(entityMap);// 插入卡片
				assShareDeptRGeneralMapper.addBatch(shareDeptList);
				assShareDeptGeneralMapper.addBatch(shareDeptList);
				Map<String, Object> allotMap = new HashMap<String, Object>();
				allotMap.put("group_id", map.get("out_group_id"));
				allotMap.put("hos_id", map.get("out_hos_id"));
				allotMap.put("copy_code", map.get("copy_code"));
				allotMap.put("ass_card_no", map.get("ass_ori_card_no"));
				List<Map<String, Object>> resourceList = assAllotOutSourceGeneralMapper.queryByAlloutOutNoAndAssCardNo(allotMap);//查询资金来源
				List<Map<String, Object>> newResourceList = new ArrayList<Map<String,Object>>();
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
				if(newResourceList.size() > 0){
					assResourceGeneralMapper.addBatch(newResourceList);
				}

				List<Map<String, Object>> fileList = assFileGeneralMapper.queryByAssCardNo(map);// 资产文档
				List<Map<String, Object>> newFileList = new ArrayList<Map<String,Object>>();
				for (Map<String, Object> file : fileList) {
					file.put("group_id", map.get("group_id"));
					file.put("hos_id", map.get("hos_id"));
					file.put("copy_code", map.get("copy_code"));
					file.put("ass_card_no", ass_card_no);
					String fileName = file.get("file_url").toString().substring(file.get("file_url").toString().lastIndexOf("/") + 1, file.get("file_url").toString().length());
					String assFilePath = "assfile";
					String filePath = group_id+"/"+hos_id+"/"+copy_code+"/"+basePath+"/"+assFilePath + "/03/" + ass_card_no;
					FtpUtil.copyFile(file.get("file_url").toString(), filePath);
					file.put("file_url", filePath + "/" + fileName);
					newFileList.add(file);
				}
				if(newFileList.size() > 0){
					assFileGeneralMapper.addBatch(newFileList);
				}

				List<Map<String, Object>> photoList = assPhotoGeneralMapper.queryByAssCardNo(map);// 资产照片
				List<Map<String, Object>> newPhotoList = new ArrayList<Map<String,Object>>();
				for (Map<String, Object> photo : photoList) {
					photo.put("group_id", map.get("group_id"));
					photo.put("hos_id", map.get("hos_id"));
					photo.put("copy_code", map.get("copy_code"));
					photo.put("ass_card_no", ass_card_no);
					String fileName = photo.get("file_url").toString().substring(photo.get("file_url").toString().lastIndexOf("/") + 1, photo.get("file_url").toString().length());
					String assFilePath = "assphoto";
					String filePath = group_id+"/"+hos_id+"/"+copy_code+"/"+basePath+"/"+assFilePath + "/03/" + ass_card_no;
					FtpUtil.copyFile(photo.get("file_url").toString(), filePath);
					photo.put("file_url", filePath + "/" + fileName);
					newPhotoList.add(photo);
				}
				if(newPhotoList.size() > 0){
					assPhotoGeneralMapper.addBatch(newPhotoList);
				}


				List<Map<String, Object>> accessoryList = assAccessoryGeneralMapper.queryByAssCardNo(map);// 资产附件
				List<Map<String, Object>> newAccessoryList = new ArrayList<Map<String,Object>>();
				for (Map<String, Object> accessory : accessoryList) {
					accessory.put("group_id", map.get("group_id"));
					accessory.put("hos_id", map.get("hos_id"));
					accessory.put("copy_code", map.get("copy_code"));
					accessory.put("ass_card_no", ass_card_no);
					newAccessoryList.add(accessory);
				}
				if(newAccessoryList.size() > 0){
					assAccessoryGeneralMapper.addBatch(newAccessoryList);
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
	public String initAssAllotInCardGeneral(Map<String, Object> map) throws DataAccessException {

		try {
			if (map.get("is_import").equals("0")) {
				return initCardByAllotInGeneral(map);
			} else if (map.get("is_import").equals("1")) {
				return initCardByImportAllotInGeneral(map);
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
	public String initAssAllotInGeneral(Map<String, Object> entityMap) throws DataAccessException {
		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> mapVo = new ArrayList<Map<String, Object>>();
		try {
			List<AssAllotOutDetailGeneral> outDetailList = assAllotOutDetailGeneralMapper
					.queryByImportAllotIn(entityMap);
					entityMap.put("bill_table", "ASS_ALLOT_IN_GENERAL");
			String allot_in_no = assBaseService.getBillNOSeqNo(entityMap);
			assBaseService.updateAssBillNoMaxNo(entityMap);
			Double price = 0.0;
			Double add_depre = 0.0;
			Double cur_money = 0.0;
			Double fore_money = 0.0;
			for (AssAllotOutDetailGeneral assAllotOutDetailGeneral : outDetailList) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("group_id", assAllotOutDetailGeneral.getGroup_id());
				map.put("hos_id", assAllotOutDetailGeneral.getHos_id());
				map.put("copy_code", assAllotOutDetailGeneral.getCopy_code());
				map.put("allot_in_no", allot_in_no);
				map.put("ass_id", assAllotOutDetailGeneral.getAss_id());
				map.put("ass_no", assAllotOutDetailGeneral.getAss_no());
				map.put("ass_ori_card_no", assAllotOutDetailGeneral.getAss_card_no());
				map.put("ass_brand", assAllotOutDetailGeneral.getAss_brand());
				map.put("ass_spec", assAllotOutDetailGeneral.getAss_spec());
				map.put("ass_model", assAllotOutDetailGeneral.getAss_model());
				map.put("fac_id", assAllotOutDetailGeneral.getFac_id());
				map.put("fac_no", assAllotOutDetailGeneral.getFac_no());
				map.put("price", assAllotOutDetailGeneral.getPrice() + "");
				map.put("add_depre", assAllotOutDetailGeneral.getAdd_depre());
				map.put("add_depre_month", assAllotOutDetailGeneral.getAdd_depre_month());
				map.put("cur_money", assAllotOutDetailGeneral.getCur_money() + "");
				map.put("fore_money", assAllotOutDetailGeneral.getFore_money() + "");
				map.put("note", assAllotOutDetailGeneral.getNote());
				price = price + assAllotOutDetailGeneral.getPrice();
				add_depre = add_depre + assAllotOutDetailGeneral.getAdd_depre();
				cur_money = cur_money + assAllotOutDetailGeneral.getCur_money();
				fore_money = fore_money + assAllotOutDetailGeneral.getFore_money();
				listVo.add(map);
			}
			// 主表增加
			entityMap.put("allot_in_no", allot_in_no);
			entityMap.put("state", "0");
			entityMap.put("note", "引入调剂出库");
			assAllotInGeneralMapper.add(entityMap);
			assBaseService.updateAssBillNoMaxNo(entityMap);
			// 明细表增加
			assAllotInDetailGeneralMapper.addBatch(listVo);

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
			assAllotMapGeneralMapper.addBatch(mapVo);

			entityMap.put("price", price + "");
			entityMap.put("add_depre", add_depre + "");
			entityMap.put("cur_money", cur_money + "");
			entityMap.put("fore_money", fore_money + "");
			// 计算主表金额
			assAllotInGeneralMapper.updateInMoney(entityMap);
			return "{\"msg\":\"引入成功.\",\"state\":\"true\",\"update_para\":\"" + entityMap.get("group_id") + "|"
					+ entityMap.get("hos_id") + "|" + entityMap.get("copy_code") + "|" + allot_in_no + "|1\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}

	}
	
	/**
	 * 一般设备  资产调剂入库 批量打印  新版打印  调用的方法
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	@Override
	public Map<String, Object> queryAssAllotInGeneralByPrintTemlatePrint(Map<String, Object> entityMap)throws DataAccessException {
		
		try{
			
			Map<String,Object> reMap=new HashMap<String,Object>();
			WebApplicationContext context = ContextLoader.getCurrentWebApplicationContext();
			AssAllotInGeneralMapper assAllotInGeneralMapper = (AssAllotInGeneralMapper)context.getBean("assAllotInGeneralMapper");
			 
			//主页面 批量打印查询
			if("1".equals(String.valueOf(entityMap.get("p_num")))){
				
				//查询 一般设备 资产调剂入库 入库主表
				List<Map<String,Object>> map= assAllotInGeneralMapper.queryAssAllotInGeneralPrintTemlateByMainBatch(entityMap);
				//查询 一般设备  资产调剂入库 入库明细表
				List<Map<String,Object>> list= assAllotInGeneralMapper.queryAssAllotInGeneralPrintTemlateByDetail(entityMap);
				
				reMap.put("main", map);
				
				reMap.put("detail", list); 
				
				return reMap;
				
			}else{ //修改页面 打印查询
				//
				Map<String,Object> map= assAllotInGeneralMapper.queryAssAllotInGeneralPrintTemlateByMain(entityMap);
				//查询 一般设备  资产调剂入库 入库明细表
				List<Map<String,Object>> list= assAllotInGeneralMapper.queryAssAllotInGeneralPrintTemlateByDetail(entityMap);
				
			
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
	 * 一般设备  资产调剂入库  入库单状态查询（打印校验数据用）
	 */
	@Override
	public List<String> queryAssAllotInGeneralState(Map<String, Object> mapVo) throws DataAccessException {
		
		return assAllotInGeneralMapper.queryAssAllotInGeneralState(mapVo);
	}
	
	private String initCardByAllotBatchInGeneral(Map<String, Object> map) {
		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();
		try {
			String basePath = "ass";
			String group_id = map.get("group_id").toString();
			String hos_id = map.get("hos_id").toString();
			String copy_code = map.get("copy_code").toString();
			String assTwoPath = "assbartwo";
			String assOnePath = "assbarone";
			String twoFilePath = group_id + "/" + hos_id + "/" + copy_code + "/" + basePath + "/" + assTwoPath + "/03/";// 资产性质
			String oneFilePath = group_id + "/" + hos_id + "/" + copy_code + "/" + basePath + "/" + assOnePath + "/03/";// 资产性质
			List<Map<String, Object>> list = assAllotInDetailGeneralMapper.queryByInit(map);
			for (int i = 0; i < list.size(); i++) {
				List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
				mapList.add(list.get(i));
				delCard(mapList);// 先删除卡片
			}
			for (Map<String, Object> temp : list) {
				Map<String, Object> entityMap = new HashMap<String, Object>();
				String ass_card_no = getBillNOSeqNo("ASS_CARD_GENERAL");
				updateAssBillNoMaxNo("ASS_CARD_GENERAL");
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
			assCardGeneralMapper.addBatch(listVo);
			assResourceGeneralMapper.addBatch(listVo);
			assShareDeptRGeneralMapper.addBatch(listVo);
			assShareDeptGeneralMapper.addBatch(listVo);

			return "{\"msg\":\"生成成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}
	}
	
	
	private String initCardByImportAllotBatchInGeneral(Map<String, Object> map) {
		String basePath = "ass";
		String group_id = map.get("group_id").toString();
		String hos_id = map.get("hos_id").toString();
		String copy_code = map.get("copy_code").toString();
		String assTwoPath = "assbartwo";
		String assOnePath = "assbarone";
		String twoFilePath = group_id + "/" + hos_id + "/" + copy_code + "/" + basePath + "/" + assTwoPath + "/03/";// 资产性质
		String oneFilePath = group_id + "/" + hos_id + "/" + copy_code + "/" + basePath + "/" + assOnePath + "/03/";// 资产性质
		map.put("ass_card_no", map.get("ass_ori_card_nos"));
		try {
			List<Map<String, Object>> list = assAllotInDetailGeneralMapper.queryByInit(map);// 查找对应明细数据
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
				List<AssCardGeneral> cardList = (List<AssCardGeneral>)assCardGeneralMapper.queryByAssCardOldNo(entiMap);// 查询原始的卡片
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
						 initCardByAllotInGeneral(entiMap);
					} else {
				

				for(AssCardGeneral assCardGeneral : cardList){
					Map<String, Object> entityMap = new HashMap<String, Object>();
					String ass_card_no = getBillNOSeqNo("ASS_CARD_GENERAL");
					updateAssBillNoMaxNo("ASS_CARD_GENERAL");
					entityMap.put("group_id", map.get("group_id"));
					entityMap.put("hos_id", map.get("hos_id"));
					entityMap.put("copy_code", map.get("copy_code"));
					entityMap.put("ass_card_no", ass_card_no);
					entityMap.put("ass_ori_card_no", assCardGeneral.getAss_card_no());
					entityMap.put("ass_id", assCardGeneral.getAss_id());
					entityMap.put("ass_no", assCardGeneral.getAss_no());
					entityMap.put("gb_code", assCardGeneral.getGb_code());
					entityMap.put("ass_spec", map2.get("ass_spec"));
					entityMap.put("ass_mondl", map2.get("ass_mondl"));
					entityMap.put("ass_brand", map2.get("ass_brand"));
					entityMap.put("ass_amount", "1");
					entityMap.put("unit_code", assCardGeneral.getUnit_code());
					entityMap.put("fac_no", map2.get("fac_no"));
					entityMap.put("fac_id", map2.get("fac_id"));
					entityMap.put("ven_id", assCardGeneral.getVen_id());
					entityMap.put("ven_no", assCardGeneral.getVen_no());
					entityMap.put("is_dept", formatNumberZero(assCardGeneral.getIs_dept()));
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
					entityMap.put("price", formatNumberZero(map2.get("price")));
					entityMap.put("depre_money", formatNumberZero(map2.get("add_depre")));
					entityMap.put("add_depre_month", map2.get("add_depre_month"));
					entityMap.put("cur_money", formatNumberZero(map2.get("cur_money")));
					entityMap.put("fore_money", formatNumberZero(map2.get("fore_money")));
					entityMap.put("buy_type", "03");
					entityMap.put("acc_depre_amount", formatNumberZero(assCardGeneral.getAcc_depre_amount()));
					entityMap.put("manage_depre_amount", formatNumberZero(assCardGeneral.getManage_depre_amount()));
					entityMap.put("manage_depre_money", "0");
					entityMap.put("add_manage_month", "0");
					entityMap.put("is_manage_depre", formatNumberZero(assCardGeneral.getIs_manage_depre()));
					entityMap.put("manage_depr_method", assCardGeneral.getManage_depr_method());
					entityMap.put("depr_method", assCardGeneral.getDepr_method());
					entityMap.put("is_depr", formatNumberZero(assCardGeneral.getIs_depr()));
					entityMap.put("use_state", "0");
					entityMap.put("is_measure", formatNumberZero(assCardGeneral.getIs_measure()));
					entityMap.put("is_throw", formatNumberZero(assCardGeneral.getIs_throw()));
					entityMap.put("pact_code", assCardGeneral.getPact_code());
					entityMap.put("ins_money", assCardGeneral.getIns_money());
					entityMap.put("ins_date", DateUtil.dateToString(assCardGeneral.getIns_date(), "yyyy-MM-dd"));
					entityMap.put("accept_emp", assCardGeneral.getAccept_emp());
					entityMap.put("accept_date", DateUtil.dateToString(assCardGeneral.getAccept_date(), "yyyy-MM-dd"));
					entityMap.put("service_date", DateUtil.dateToString(assCardGeneral.getService_date(), "yyyy-MM-dd"));
					entityMap.put("ass_seq_no", assCardGeneral.getAss_seq_no());
					entityMap.put("location", assCardGeneral.getLocation());
					entityMap.put("note", assCardGeneral.getNote());
					entityMap.put("ass_in_no", map.get("allot_in_no"));
					entityMap.put("allot_in_no", map.get("allot_in_no"));
					entityMap.put("in_date", null);
					entityMap.put("run_date", null);
					entityMap.put("is_bar", formatNumberZero(assCardGeneral.getIs_bar()));
					entityMap.put("bar_type", assCardGeneral.getBar_type());
					entityMap.put("bar_code", ass_card_no);
					entityMap.put("is_init", "0");
					entityMap.put("dispose_type", null);
					entityMap.put("dispose_cost", null);
					entityMap.put("dispose_income", null);
					entityMap.put("dispose_tax", null);
					entityMap.put("ass_purpose", assCardGeneral.getAss_purpose());
					entityMap.put("proj_id", assCardGeneral.getProj_id());
					entityMap.put("proj_no", assCardGeneral.getProj_no());

					if (assCardGeneral.getIs_bar() != null && !assCardGeneral.getIs_bar().equals("")) {
						if (assCardGeneral.getIs_bar() == 1) {
							if (assCardGeneral.getBar_type() != null && !assCardGeneral.getBar_type().equals("")) {
								if (assCardGeneral.getBar_type() == 1) {
									FtpUtil.getBarcodeWriteFile(ass_card_no, "", oneFilePath, ass_card_no + ".png");// 一维码
									entityMap.put("bar_url", oneFilePath + ass_card_no + ".png");

								} else if (assCardGeneral.getBar_type() == 2) {
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
					
					assCardGeneralMapper.add(entityMap);// 插入卡片
					assShareDeptRGeneralMapper.addBatch(shareDeptList);
					assShareDeptGeneralMapper.addBatch(shareDeptList);
					Map<String, Object> allotMap = new HashMap<String, Object>();
					allotMap.put("group_id", map.get("out_group_id"));
					allotMap.put("hos_id", map.get("out_hos_id"));
					allotMap.put("copy_code", map.get("copy_code"));
					allotMap.put("ass_card_no",assCardGeneral.getAss_card_no());
					List<Map<String, Object>> resourceList = assAllotOutSourceGeneralMapper.queryByAlloutOutNoAndAssCardNo(allotMap);//查询资金来源
					List<Map<String, Object>> newResourceList = new ArrayList<Map<String,Object>>();
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
					if(newResourceList.size() > 0){
						assResourceGeneralMapper.addBatch(newResourceList);
					}

					List<Map<String, Object>> fileList = assFileGeneralMapper.queryByAssCardNo(map2);// 资产文档
					List<Map<String, Object>> newFileList = new ArrayList<Map<String,Object>>();
					for (Map<String, Object> file : fileList) {
						file.put("group_id", map.get("group_id"));
						file.put("hos_id", map.get("hos_id"));
						file.put("copy_code", map.get("copy_code"));
						file.put("ass_card_no", ass_card_no);
						String fileName = file.get("file_url").toString().substring(file.get("file_url").toString().lastIndexOf("/") + 1, file.get("file_url").toString().length());
						String assFilePath = "assfile";
						String filePath = group_id+"/"+hos_id+"/"+copy_code+"/"+basePath+"/"+assFilePath + "/03/" + ass_card_no;
						FtpUtil.copyFile(file.get("file_url").toString(), filePath);
						file.put("file_url", filePath + "/" + fileName);
						newFileList.add(file);
					}
					if(newFileList.size() > 0){
						assFileGeneralMapper.addBatch(newFileList);
					}

					List<Map<String, Object>> photoList = assPhotoGeneralMapper.queryByAssCardNo(map2);// 资产照片
					List<Map<String, Object>> newPhotoList = new ArrayList<Map<String,Object>>();
					for (Map<String, Object> photo : photoList) {
						photo.put("group_id", map.get("group_id"));
						photo.put("hos_id", map.get("hos_id"));
						photo.put("copy_code", map.get("copy_code"));
						photo.put("ass_card_no", ass_card_no);
						String fileName = photo.get("file_url").toString().substring(photo.get("file_url").toString().lastIndexOf("/") + 1, photo.get("file_url").toString().length());
						String assFilePath = "assphoto";
						String filePath = group_id+"/"+hos_id+"/"+copy_code+"/"+basePath+"/"+assFilePath + "/03/" + ass_card_no;
						FtpUtil.copyFile(photo.get("file_url").toString(), filePath);
						photo.put("file_url", filePath + "/" + fileName);
						newPhotoList.add(photo);
					}
					if(newPhotoList.size() > 0){
						assPhotoGeneralMapper.addBatch(newPhotoList);
					}


					List<Map<String, Object>> accessoryList = assAccessoryGeneralMapper.queryByAssCardNo(map2);// 资产附件
					List<Map<String, Object>> newAccessoryList = new ArrayList<Map<String,Object>>();
					for (Map<String, Object> accessory : accessoryList) {
						accessory.put("group_id", map.get("group_id"));
						accessory.put("hos_id", map.get("hos_id"));
						accessory.put("copy_code", map.get("copy_code"));
						accessory.put("ass_card_no", ass_card_no);
						newAccessoryList.add(accessory);
					}
					if(newAccessoryList.size() > 0){
						assAccessoryGeneralMapper.addBatch(newAccessoryList);
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
	public String initAssAllotInBatchCardGeneral(Map<String, Object> map) throws DataAccessException {
		try {
			if (map.get("is_import").equals("0")) {
				return initCardByAllotBatchInGeneral(map);
			} else if (map.get("is_import").equals("1")) {
				return initCardByImportAllotBatchInGeneral(map);
			} else {
				return "{\"warn\":\"生成失败.\"}";
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}
	}

	@Override
	public String queryDetails(Map<String, Object> entityMap) {
		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		if (sysPage.getTotal() == -1) {

			List<Map<String, Object>> list = (List<Map<String, Object>>) assAllotInGeneralMapper.queryDetails(entityMap);

			return ChdJson.toJson(list);

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

			List<Map<String, Object>> list = (List<Map<String, Object>>) assAllotInGeneralMapper.queryDetails(entityMap, rowBounds);

			PageInfo page = new PageInfo(list);

			return ChdJson.toJson(list, page.getTotal());

		}
	}
}
