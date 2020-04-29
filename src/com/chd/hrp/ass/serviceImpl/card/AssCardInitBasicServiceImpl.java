package com.chd.hrp.ass.serviceImpl.card;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.NoTransactionException;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.multipart.MultipartFile;

import com.chd.base.SessionManager;
import com.chd.base.exception.SysException;
import com.chd.base.ftp.FtpUtil;
import com.chd.base.util.DateUtil;
import com.chd.base.util.NumberUtil;
import com.chd.base.util.ReadFiles;
import com.chd.base.util.SpreadTableJSUtil;
import com.chd.hrp.ass.dao.accessory.AssAccessoryInitGeneralMapper;
import com.chd.hrp.ass.dao.accessory.AssAccessoryInitHouseMapper;
import com.chd.hrp.ass.dao.accessory.AssAccessoryInitInassetsMapper;
import com.chd.hrp.ass.dao.accessory.AssAccessoryInitLandMapper;
import com.chd.hrp.ass.dao.accessory.AssAccessoryInitOtherMapper;
import com.chd.hrp.ass.dao.accessory.AssAccessoryInitSpecialMapper;
import com.chd.hrp.ass.dao.base.AssInitAccountMapper;
import com.chd.hrp.ass.dao.card.AssCardBaseMapper;
import com.chd.hrp.ass.dao.card.AssCardInitGeneralMapper;
import com.chd.hrp.ass.dao.card.AssCardInitHouseMapper;
import com.chd.hrp.ass.dao.card.AssCardInitInassetsMapper;
import com.chd.hrp.ass.dao.card.AssCardInitLandMapper;
import com.chd.hrp.ass.dao.card.AssCardInitOtherMapper;
import com.chd.hrp.ass.dao.card.AssCardInitSpecialMapper;
import com.chd.hrp.ass.dao.depre.acc.AssDepreAccInitGeneralMapper;
import com.chd.hrp.ass.dao.depre.acc.AssDepreAccInitHouseMapper;
import com.chd.hrp.ass.dao.depre.acc.AssDepreAccInitInassetsMapper;
import com.chd.hrp.ass.dao.depre.acc.AssDepreAccInitLandMapper;
import com.chd.hrp.ass.dao.depre.acc.AssDepreAccInitOtherMapper;
import com.chd.hrp.ass.dao.depre.acc.AssDepreAccInitSpecialMapper;
import com.chd.hrp.ass.dao.depre.manager.AssDepreManageInitGeneralMapper;
import com.chd.hrp.ass.dao.depre.manager.AssDepreManageInitHouseMapper;
import com.chd.hrp.ass.dao.depre.manager.AssDepreManageInitInassetsMapper;
import com.chd.hrp.ass.dao.depre.manager.AssDepreManageInitLandMapper;
import com.chd.hrp.ass.dao.depre.manager.AssDepreManageInitOtherMapper;
import com.chd.hrp.ass.dao.depre.manager.AssDepreManageInitSpecialMapper;
import com.chd.hrp.ass.dao.dict.AssBusTypeDictMapper;
import com.chd.hrp.ass.dao.dict.AssFacDictMapper;
import com.chd.hrp.ass.dao.dict.AssNatursMapper;
import com.chd.hrp.ass.dao.dict.AssNoDictMapper;
import com.chd.hrp.ass.dao.dict.AssSupDictMapper;
import com.chd.hrp.ass.dao.dict.AssUsageDictMapper;
import com.chd.hrp.ass.dao.pay.AssPayStageInitGeneralMapper;
import com.chd.hrp.ass.dao.pay.AssPayStageInitHouseMapper;
import com.chd.hrp.ass.dao.pay.AssPayStageInitInassetsMapper;
import com.chd.hrp.ass.dao.pay.AssPayStageInitLandMapper;
import com.chd.hrp.ass.dao.pay.AssPayStageInitOtherMapper;
import com.chd.hrp.ass.dao.pay.AssPayStageInitSpecialMapper;
import com.chd.hrp.ass.dao.resource.AssResourceInitGeneralMapper;
import com.chd.hrp.ass.dao.resource.AssResourceInitHouseMapper;
import com.chd.hrp.ass.dao.resource.AssResourceInitInassetsMapper;
import com.chd.hrp.ass.dao.resource.AssResourceInitLandMapper;
import com.chd.hrp.ass.dao.resource.AssResourceInitOtherMapper;
import com.chd.hrp.ass.dao.resource.AssResourceInitSpecialMapper;
import com.chd.hrp.ass.dao.share.AssShareDeptInitGeneralMapper;
import com.chd.hrp.ass.dao.share.AssShareDeptInitHouseMapper;
import com.chd.hrp.ass.dao.share.AssShareDeptInitInassetsMapper;
import com.chd.hrp.ass.dao.share.AssShareDeptInitLandMapper;
import com.chd.hrp.ass.dao.share.AssShareDeptInitOtherMapper;
import com.chd.hrp.ass.dao.share.AssShareDeptInitSpecialMapper;
import com.chd.hrp.ass.entity.accessory.AssAccessoryInitGeneral;
import com.chd.hrp.ass.entity.accessory.AssAccessoryInitHouse;
import com.chd.hrp.ass.entity.accessory.AssAccessoryInitInassets;
import com.chd.hrp.ass.entity.accessory.AssAccessoryInitLand;
import com.chd.hrp.ass.entity.accessory.AssAccessoryInitOther;
import com.chd.hrp.ass.entity.accessory.AssAccessoryInitSpecial;
import com.chd.hrp.ass.entity.card.AssCardInitGeneral;
import com.chd.hrp.ass.entity.card.AssCardInitHouse;
import com.chd.hrp.ass.entity.card.AssCardInitInassets;
import com.chd.hrp.ass.entity.card.AssCardInitLand;
import com.chd.hrp.ass.entity.card.AssCardInitOther;
import com.chd.hrp.ass.entity.card.AssCardInitSpecial;
import com.chd.hrp.ass.entity.dict.AssBusTypeDict;
import com.chd.hrp.ass.entity.dict.AssFacDict;
import com.chd.hrp.ass.entity.dict.AssNaturs;
import com.chd.hrp.ass.entity.dict.AssNoDict;
import com.chd.hrp.ass.entity.dict.AssSupDict;
import com.chd.hrp.ass.entity.dict.AssUsageDict;
import com.chd.hrp.ass.entity.resource.AssResourceInitGeneral;
import com.chd.hrp.ass.entity.resource.AssResourceInitHouse;
import com.chd.hrp.ass.entity.resource.AssResourceInitInassets;
import com.chd.hrp.ass.entity.resource.AssResourceInitLand;
import com.chd.hrp.ass.entity.resource.AssResourceInitOther;
import com.chd.hrp.ass.entity.resource.AssResourceInitSpecial;
import com.chd.hrp.ass.entity.share.AssShareDeptInitGeneral;
import com.chd.hrp.ass.entity.share.AssShareDeptInitHouse;
import com.chd.hrp.ass.entity.share.AssShareDeptInitInassets;
import com.chd.hrp.ass.entity.share.AssShareDeptInitLand;
import com.chd.hrp.ass.entity.share.AssShareDeptInitOther;
import com.chd.hrp.ass.entity.share.AssShareDeptInitSpecial;
import com.chd.hrp.ass.service.base.AssBaseService;
import com.chd.hrp.ass.service.card.AssCardInitBasicService;
import com.chd.hrp.mat.dao.info.basic.MatPayTermMapper;
import com.chd.hrp.mat.dao.info.basic.MatStoreMapper;
import com.chd.hrp.mat.entity.MatPayTerm;
import com.chd.hrp.sys.dao.DeptDictMapper;
import com.chd.hrp.sys.dao.ProjDictMapper;
import com.chd.hrp.sys.dao.SourceMapper;
import com.chd.hrp.sys.dao.StoreDictMapper;
import com.chd.hrp.sys.dao.UnitMapper;
import com.chd.hrp.sys.dao.UserMapper;
import com.chd.hrp.sys.entity.DeptDict;
import com.chd.hrp.sys.entity.ProjDict;
import com.chd.hrp.sys.entity.Source;
import com.chd.hrp.sys.entity.StoreDict;
import com.chd.hrp.sys.entity.Unit;
import com.chd.hrp.sys.entity.User;

@Service("assCardInitBasicService")
public class AssCardInitBasicServiceImpl implements AssCardInitBasicService {    
 
	private static Logger logger = Logger.getLogger(AssCardInitBasicServiceImpl.class);

	@Resource(name = "assBaseService")
	private final AssBaseService assBaseService = null;

	@Resource(name = "deptDictMapper")
	private final DeptDictMapper deptDictMapper = null;

	@Resource(name = "assNatursMapper")
	private final AssNatursMapper assNatursMapper = null;

	@Resource(name = "assShareDeptInitGeneralMapper")
	private final AssShareDeptInitGeneralMapper assShareDeptInitGeneralMapper = null;

	@Resource(name = "assShareDeptInitHouseMapper")
	private final AssShareDeptInitHouseMapper assShareDeptInitHouseMapper = null;

	@Resource(name = "assShareDeptInitOtherMapper")
	private final AssShareDeptInitOtherMapper assShareDeptInitOtherMapper = null;

	@Resource(name = "assShareDeptInitSpecialMapper")
	private final AssShareDeptInitSpecialMapper assShareDeptInitSpecialMapper = null;

	@Resource(name = "assShareDeptInitInassetsMapper")
	private final AssShareDeptInitInassetsMapper assShareDeptInitInassetsMapper = null;

	@Resource(name = "assShareDeptInitLandMapper")
	private final AssShareDeptInitLandMapper assShareDeptInitLandMapper = null;

	@Resource(name = "assAccessoryInitGeneralMapper")
	private final AssAccessoryInitGeneralMapper assAccessoryInitGeneralMapper = null;

	@Resource(name = "assAccessoryInitHouseMapper")
	private final AssAccessoryInitHouseMapper assAccessoryInitHouseMapper = null;

	@Resource(name = "assAccessoryInitOtherMapper")
	private final AssAccessoryInitOtherMapper assAccessoryInitOtherMapper = null;

	@Resource(name = "assAccessoryInitSpecialMapper")
	private final AssAccessoryInitSpecialMapper assAccessoryInitSpecialMapper = null;

	@Resource(name = "assAccessoryInitInassetsMapper")
	private final AssAccessoryInitInassetsMapper assAccessoryInitInassetsMapper = null;

	@Resource(name = "assAccessoryInitLandMapper")
	private final AssAccessoryInitLandMapper assAccessoryInitLandMapper = null;

	@Resource(name = "assCardInitGeneralMapper")
	private final AssCardInitGeneralMapper assCardInitGeneralMapper = null;

	@Resource(name = "assCardInitHouseMapper")
	private final AssCardInitHouseMapper assCardInitHouseMapper = null;

	@Resource(name = "assCardInitOtherMapper")
	private final AssCardInitOtherMapper assCardInitOtherMapper = null;

	@Resource(name = "assCardInitSpecialMapper")
	private final AssCardInitSpecialMapper assCardInitSpecialMapper = null;

	@Resource(name = "assCardInitInassetsMapper")
	private final AssCardInitInassetsMapper assCardInitInassetsMapper = null;

	@Resource(name = "assCardInitLandMapper")
	private final AssCardInitLandMapper assCardInitLandMapper = null;

	@Resource(name = "assResourceInitGeneralMapper")
	private final AssResourceInitGeneralMapper assResourceInitGeneralMapper = null;

	@Resource(name = "assResourceInitHouseMapper")
	private final AssResourceInitHouseMapper assResourceInitHouseMapper = null;

	@Resource(name = "assResourceInitOtherMapper")
	private final AssResourceInitOtherMapper assResourceInitOtherMapper = null;

	@Resource(name = "assResourceInitSpecialMapper")
	private final AssResourceInitSpecialMapper assResourceInitSpecialMapper = null;

	@Resource(name = "assResourceInitInassetsMapper")
	private final AssResourceInitInassetsMapper assResourceInitInassetsMapper = null;

	@Resource(name = "assResourceInitLandMapper")
	private final AssResourceInitLandMapper assResourceInitLandMapper = null;

	@Resource(name = "assNoDictMapper")
	private final AssNoDictMapper assNoDictMapper = null;

	@Resource(name = "assBusTypeDictMapper")
	private final AssBusTypeDictMapper assBusTypeDictMapper = null;

	@Resource(name = "assFacDictMapper")
	private final AssFacDictMapper assFacDictMapper = null;

	@Resource(name = "assSupDictMapper")
	private final AssSupDictMapper assSupDictMapper = null;

	@Resource(name = "storeDictMapper")
	private final StoreDictMapper storeDictMapper = null;

	@Resource(name = "projDictMapper")
	private final ProjDictMapper projDictMapper = null;

	@Resource(name = "assUsageDictMapper")
	private final AssUsageDictMapper assUsageDictMapper = null;

	@Resource(name = "userMapper")
	private final UserMapper userMapper = null;

	@Resource(name = "unitMapper")
	private final UnitMapper unitMapper = null;

	@Resource(name = "sourceMapper")
	private final SourceMapper sourceMapper = null;

	@Resource(name = "matStoreMapper")
	private final MatStoreMapper matStoreMapper = null;

	@Resource(name = "assInitAccountMapper")
	private final AssInitAccountMapper assInitAccountMapper = null;

	@Resource(name = "assDepreAccInitSpecialMapper")
	private final AssDepreAccInitSpecialMapper assDepreAccInitSpecialMapper = null;

	@Resource(name = "assDepreAccInitGeneralMapper")
	private final AssDepreAccInitGeneralMapper assDepreAccInitGeneralMapper = null;

	@Resource(name = "assDepreAccInitHouseMapper")
	private final AssDepreAccInitHouseMapper assDepreAccInitHouseMapper = null;

	@Resource(name = "assDepreAccInitOtherMapper")
	private final AssDepreAccInitOtherMapper assDepreAccInitOtherMapper = null;

	@Resource(name = "assDepreAccInitInassetsMapper")
	private final AssDepreAccInitInassetsMapper assDepreAccInitInassetsMapper = null;

	@Resource(name = "assDepreAccInitLandMapper")
	private final AssDepreAccInitLandMapper assDepreAccInitLandMapper = null;

	@Resource(name = "assDepreManageInitSpecialMapper")
	private final AssDepreManageInitSpecialMapper assDepreManageInitSpecialMapper = null;

	@Resource(name = "assDepreManageInitGeneralMapper")
	private final AssDepreManageInitGeneralMapper assDepreManageInitGeneralMapper = null;

	@Resource(name = "assDepreManageInitHouseMapper")
	private final AssDepreManageInitHouseMapper assDepreManageInitHouseMapper = null;

	@Resource(name = "assDepreManageInitOtherMapper")
	private final AssDepreManageInitOtherMapper assDepreManageInitOtherMapper = null;

	@Resource(name = "assDepreManageInitInassetsMapper")
	private final AssDepreManageInitInassetsMapper assDepreManageInitInassetsMapper = null;

	@Resource(name = "assDepreManageInitLandMapper")
	private final AssDepreManageInitLandMapper assDepreManageInitLandMapper = null;

	@Resource(name = "matPayTermMapper")
	private final MatPayTermMapper matPayTermMapper = null;

	@Resource(name = "assPayStageInitSpecialMapper")
	private final AssPayStageInitSpecialMapper assPayStageInitSpecialMapper = null;

	@Resource(name = "assPayStageInitGeneralMapper")
	private final AssPayStageInitGeneralMapper assPayStageInitGeneralMapper = null;

	@Resource(name = "assPayStageInitHouseMapper")
	private final AssPayStageInitHouseMapper assPayStageInitHouseMapper = null;

	@Resource(name = "assPayStageInitOtherMapper")
	private final AssPayStageInitOtherMapper assPayStageInitOtherMapper = null;

	@Resource(name = "assPayStageInitInassetsMapper")
	private final AssPayStageInitInassetsMapper assPayStageInitInassetsMapper = null;

	@Resource(name = "assPayStageInitLandMapper")
	private final AssPayStageInitLandMapper assPayStageInitLandMapper = null;

	@Resource(name = "assCardBaseMapper")
	private final AssCardBaseMapper assCardBaseMapper = null;

	@Override
	public String getAssCardNo(Map<String, Object> entityMap) throws DataAccessException {
		String ass_card_no = "";

		if (entityMap.get("ass_naturs").equals("01")) {
			entityMap.put("bill_table", "ASS_CARD_HOUSE");
			ass_card_no = assBaseService.getBillNOSeqNo(entityMap);
		} else if (entityMap.get("ass_naturs").equals("02")) {
			entityMap.put("bill_table", "ASS_CARD_SPECIAL");
			ass_card_no = assBaseService.getBillNOSeqNo(entityMap);
		} else if (entityMap.get("ass_naturs").equals("03")) {
			entityMap.put("bill_table", "ASS_CARD_GENERAL");
			ass_card_no = assBaseService.getBillNOSeqNo(entityMap);
		} else if (entityMap.get("ass_naturs").equals("04")) {
			entityMap.put("bill_table", "ASS_CARD_OTHER");
			ass_card_no = assBaseService.getBillNOSeqNo(entityMap);
		} else if (entityMap.get("ass_naturs").equals("05")) {
			entityMap.put("bill_table", "ASS_CARD_INASSETS");
			ass_card_no = assBaseService.getBillNOSeqNo(entityMap);
		} else if (entityMap.get("ass_naturs").equals("06")) {
			entityMap.put("bill_table", "ASS_CARD_LAND");
			ass_card_no = assBaseService.getBillNOSeqNo(entityMap);
		}

		return ass_card_no;
	}

	@Override
	public String importShareDept(Map<String, Object> entityMap) throws Exception {
		try {
			String content = entityMap.get("content").toString();

			// String isAppend = entityMap.get("isAppend").toString();

			List<Map<String, List<String>>> liData = SpreadTableJSUtil.toListMap(content, 1);
			if (liData == null || liData.size() == 0) {
				return "{\"error\":\"没有数据！\",\"state\":\"false\"}";
			}

			/**
			 * if (entityMap.get("ass_naturs").equals("01")) { if (isAppend.equals("true"))
			 * { assShareDeptSpecialMapper.delete(entityMap); } } else if
			 * (entityMap.get("ass_naturs").equals("02")) { if (isAppend.equals("true")) {
			 * assShareDeptGeneralMapper.delete(entityMap); } } else if
			 * (entityMap.get("ass_naturs").equals("03")) { if (isAppend.equals("true")) {
			 * assShareDeptHouseMapper.delete(entityMap); } } else if
			 * (entityMap.get("ass_naturs").equals("04")) { if (isAppend.equals("true")) {
			 * assShareDeptOtherMapper.delete(entityMap); } } else if
			 * (entityMap.get("ass_naturs").equals("05")) { if (isAppend.equals("true")) {
			 * assShareDeptInassetsMapper.delete(entityMap); } } else if
			 * (entityMap.get("ass_naturs").equals("06")) { if (isAppend.equals("true")) {
			 * assShareDeptLandMapper.delete(entityMap); } }
			 */

			for (Map<String, List<String>> mapData : liData) {

				Map<String, Object> mapVo = new HashMap<String, Object>();
				mapVo.put("group_id", entityMap.get("group_id"));
				mapVo.put("hos_id", entityMap.get("hos_id"));
				mapVo.put("copy_code", entityMap.get("copy_code"));
				mapVo.put("ass_card_no", entityMap.get("ass_card_no"));

				String dept_code = mapData.get("科室编码").get(1);
				Map<String, Object> vdataMap = new HashMap<String, Object>();
				vdataMap.put("group_id", entityMap.get("group_id"));
				vdataMap.put("hos_id", entityMap.get("hos_id"));
				if (mapData.get("科室名称") != null) {
					String dept_name = mapData.get("科室名称").get(1);
					vdataMap.put("dept_name", dept_name);
				}
				vdataMap.put("dept_code", dept_code);
				vdataMap.put("is_stop", "0");
				DeptDict dept_dict = deptDictMapper.queryDeptDictByDeptCode(vdataMap);

				if (dept_dict == null) {
					return "{\"msg\":\"" + mapData.get("科室编码").get(0)
							+ "，科室编码不存在！\",\"state\":\"false\",\"row_cell\":\"" + mapData.get("科室编码").get(0)
							+ "\"}";
				} else {
					mapVo.put("dept_id", dept_dict.getDept_id());
					mapVo.put("dept_no", dept_dict.getDept_no());
				}

				if (mapData.get("分摊当量") == null || mapData.get("分摊当量").get(1) == null
						|| !NumberUtil.isNumeric(mapData.get("分摊当量").get(1))) {
					return "{\"msg\":\"" + mapData.get("分摊当量").get(0)
							+ "，不能为空！\",\"state\":\"false\",\"row_cell\":\"" + mapData.get("分摊当量").get(0) + "\"}";
				} else {
					mapVo.put("area", mapData.get("分摊当量").get(1));
				}

				String note = mapData.get("备注") == null ? "" : mapData.get("备注").get(1);
				mapVo.put("note", note);

				if (entityMap.get("ass_naturs").equals("01")) {
					AssShareDeptInitHouse assShareDeptInitHouse = (AssShareDeptInitHouse) assShareDeptInitHouseMapper
							.queryByCode(mapVo);
					if (assShareDeptInitHouse != null) {
						continue;
					} else {
						assShareDeptInitHouseMapper.add(mapVo);
					}

				} else if (entityMap.get("ass_naturs").equals("02")) {
					AssShareDeptInitSpecial assShareDeptInitSpecial = (AssShareDeptInitSpecial) assShareDeptInitSpecialMapper
							.queryByCode(mapVo);
					if (assShareDeptInitSpecial != null) {
						continue;
					} else {
						assShareDeptInitSpecialMapper.add(mapVo);
					}

				} else if (entityMap.get("ass_naturs").equals("03")) {
					AssShareDeptInitGeneral assShareDeptInitGeneral = (AssShareDeptInitGeneral) assShareDeptInitGeneralMapper
							.queryByCode(mapVo);
					if (assShareDeptInitGeneral != null) {
						continue;
					} else {
						assShareDeptInitGeneralMapper.add(mapVo);
					}

				} else if (entityMap.get("ass_naturs").equals("04")) {

					AssShareDeptInitOther assShareDeptInitOther = (AssShareDeptInitOther) assShareDeptInitOtherMapper
							.queryByCode(mapVo);
					if (assShareDeptInitOther != null) {
						continue;
					} else {
						assShareDeptInitOtherMapper.add(mapVo);
					}
				} else if (entityMap.get("ass_naturs").equals("05")) {

					AssShareDeptInitInassets assShareDeptInitInassets = (AssShareDeptInitInassets) assShareDeptInitInassetsMapper
							.queryByCode(mapVo);
					if (assShareDeptInitInassets != null) {
						continue;
					} else {
						assShareDeptInitInassetsMapper.add(mapVo);
					}
				} else if (entityMap.get("ass_naturs").equals("06")) {

					AssShareDeptInitLand assShareDeptInitLand = (AssShareDeptInitLand) assShareDeptInitLandMapper
							.queryByCode(mapVo);
					if (assShareDeptInitLand != null) {
						continue;
					} else {
						assShareDeptInitLandMapper.add(mapVo);
					}
				}
			}
			return "{\"msg\":\"导入成功。\",\"state\":\"true\"}";
		} catch (Exception e) {
			e.printStackTrace();
			throw new SysException(e.getMessage());
		}
	}

	@Override
	public String importFile(Map<String, Object> entityMap, MultipartFile uploadFile, HttpServletRequest request,
			HttpServletResponse response, String filePath) throws Exception {
		try {

			if (entityMap.get("ord_file_url") != null && !entityMap.get("ord_file_url").equals("")) {
				String ord_file_url = entityMap.get("ord_file_url").toString();
				String file_name = ord_file_url.substring(ord_file_url.lastIndexOf("/") + 1, ord_file_url.length());
				String path = ord_file_url.substring(0, ord_file_url.lastIndexOf("/"));
				if (!FtpUtil.deleteFile(path, file_name)) {
					TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
					return "error";
				}
			}

			if (uploadFile != null) {
				if (!FtpUtil.uploadFile(uploadFile, "", filePath, request, response)) {
					TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
					return "error";
				}
			}
			return "{\"msg\":\"上传成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public String deleteFile(List<Map<String, Object>> entityMap) {

		try {
			for (Map<String, Object> map : entityMap) {
				String file_name = map.get("file_url").toString().substring(
						map.get("file_url").toString().lastIndexOf("/") + 1, map.get("file_url").toString().length());
				String path = map.get("file_url").toString().substring(0,
						map.get("file_url").toString().lastIndexOf("/"));
				if (!FtpUtil.deleteFile(path, file_name)) {
					TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
					return "error";
				}
			}
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	@Override
	public String downloadFile(HttpServletResponse response, Map<String, Object> entityMap) {
		try {
			String file_name = entityMap.get("file_url").toString().substring(
					entityMap.get("file_url").toString().lastIndexOf("/") + 1,
					entityMap.get("file_url").toString().length());
			String path = entityMap.get("file_url").toString().substring(0,
					entityMap.get("file_url").toString().lastIndexOf("/"));
			if (!FtpUtil.downloadFile(response, file_name, path)) {
				TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
				return "error";
			}
			return "{\"msg\":\"下载成功.\",\"state\":\"true\"}";
		} catch (NoTransactionException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public String importPhoto(Map<String, Object> entityMap, MultipartFile uploadFile, HttpServletRequest request,
			HttpServletResponse response, String filePath) throws Exception {
		try {

			if (entityMap.get("ord_file_url") != null && !entityMap.get("ord_file_url").equals("")) {
				String ord_file_url = entityMap.get("ord_file_url").toString();
				String file_name = ord_file_url.substring(ord_file_url.lastIndexOf("/") + 1, ord_file_url.length());
				String path = ord_file_url.substring(0, ord_file_url.lastIndexOf("/"));
				if (!FtpUtil.deleteFile(path, file_name)) {
					TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
					return "error";
				}
			}

			if (uploadFile != null) {
				if (!FtpUtil.uploadFile(uploadFile, "", filePath, request, response)) {
					TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
					return "error";
				}
			}
			return "{\"msg\":\"上传成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public String deletePhoto(List<Map<String, Object>> entityMap) {
		try {
			for (Map<String, Object> map : entityMap) {
				String file_name = map.get("file_url").toString().substring(
						map.get("file_url").toString().lastIndexOf("/") + 1, map.get("file_url").toString().length());
				String path = map.get("file_url").toString().substring(0,
						map.get("file_url").toString().lastIndexOf("/"));
				if (!FtpUtil.deleteFile(path, file_name)) {
					TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
					return "error";
				}
			}
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	@Override
	public String downloadPhoto(HttpServletResponse response, Map<String, Object> entityMap) {
		try {
			String file_name = entityMap.get("file_url").toString().substring(
					entityMap.get("file_url").toString().lastIndexOf("/") + 1,
					entityMap.get("file_url").toString().length());
			String path = entityMap.get("file_url").toString().substring(0,
					entityMap.get("file_url").toString().lastIndexOf("/"));
			if (!FtpUtil.downloadFile(response, file_name, path)) {
				TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
				return "error";
			}
			return "{\"msg\":\"下载成功.\",\"state\":\"true\"}";
		} catch (NoTransactionException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public String viewPhoto(HttpServletResponse response, Map<String, Object> entityMap) {
		try {
			String file_name = entityMap.get("file_url").toString().substring(
					entityMap.get("file_url").toString().lastIndexOf("/") + 1,
					entityMap.get("file_url").toString().length());
			String path = entityMap.get("file_url").toString().substring(0,
					entityMap.get("file_url").toString().lastIndexOf("/"));
			if (!FtpUtil.viewImage(response, file_name, path)) {
				TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
				return "error";
			}
			return "{\"msg\":\"下载成功.\",\"state\":\"true\"}";
		} catch (NoTransactionException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public String importAccessory(Map<String, Object> entityMap) throws Exception {
		try {
			String content = entityMap.get("content").toString();

			List<Map<String, List<String>>> liData = SpreadTableJSUtil.toListMap(content, 1);
			if (liData == null || liData.size() == 0) {
				return "{\"error\":\"没有数据！\",\"state\":\"false\"}";
			}

			/**
			 * if (entityMap.get("ass_naturs").equals("01")) { if (isAppend.equals("true"))
			 * { assAccessorySpecialMapper.delete(entityMap); } } else if
			 * (entityMap.get("ass_naturs").equals("02")) { if (isAppend.equals("true")) {
			 * assAccessoryGeneralMapper.delete(entityMap); } } else if
			 * (entityMap.get("ass_naturs").equals("03")) { if (isAppend.equals("true")) {
			 * assAccessoryHouseMapper.delete(entityMap); } } else if
			 * (entityMap.get("ass_naturs").equals("04")) { if (isAppend.equals("true")) {
			 * assAccessoryOtherMapper.delete(entityMap); } } else if
			 * (entityMap.get("ass_naturs").equals("05")) { if (isAppend.equals("true")) {
			 * assAccessoryInassetsMapper.delete(entityMap); } } else if
			 * (entityMap.get("ass_naturs").equals("06")) { if (isAppend.equals("true")) {
			 * assAccessoryLandMapper.delete(entityMap); } }
			 */

			for (Map<String, List<String>> mapData : liData) {

				Map<String, Object> mapVo = new HashMap<String, Object>();
				mapVo.put("group_id", entityMap.get("group_id"));
				mapVo.put("hos_id", entityMap.get("hos_id")); 
				mapVo.put("copy_code", entityMap.get("copy_code"));
				mapVo.put("ass_card_no", entityMap.get("ass_card_no"));

				mapVo.put("accessory_code", mapData.get("附件编码【必填】").get(1));
				mapVo.put("accessory_name", mapData.get("附件名称【必填】").get(1));

				if (mapData.get("资产性质编码") != null && !mapData.get("资产性质编码").equals("")) {
					String naturs_code = mapData.get("资产性质编码").get(1);
					Map<String, Object> vdataMap = new HashMap<String, Object>();
					vdataMap.put("group_id", entityMap.get("group_id"));
					vdataMap.put("hos_id", entityMap.get("hos_id"));
					vdataMap.put("copy_code", entityMap.get("copy_code"));
					if (mapData.get("资产性质名称") != null) {
						String naturs_name = mapData.get("资产性质名称").get(1);
						vdataMap.put("naturs_name", naturs_name);
					}
					vdataMap.put("naturs_code", naturs_code);
					AssNaturs assNaturs = assNatursMapper.queryAssNatursByCode(vdataMap);

					if (assNaturs == null) {
						return "{\"msg\":\"" + mapData.get("资产性质编码").get(0)
								+ "，资产性质编码不存在！\",\"state\":\"false\",\"row_cell\":\"" + mapData.get("资产性质编码").get(0)
								+ "\"}";
					} else {
						mapVo.put("naturs_code", assNaturs.getNaturs_code());

						if (mapData.get("附卡") != null && !mapData.get("附卡").equals("")) {
							String accessory_card_no = mapData.get("附卡").get(1);
							vdataMap.put("ass_card_no", accessory_card_no);
							if (assNaturs.getNaturs_code().equals("01")) {
								AssCardInitSpecial assCardInitSpecial = assCardInitSpecialMapper.queryByCode(vdataMap);
								if (assCardInitSpecial != null) {
									mapVo.put("accessory_card_no", assCardInitSpecial.getAss_card_no());
								} else {
									return "{\"msg\":\"" + mapData.get("附卡").get(0)
											+ "，卡片编码不存在不存在！\",\"state\":\"false\",\"row_cell\":\""
											+ mapData.get("附卡").get(0) + "\"}";
								}
							} else if (assNaturs.getNaturs_code().equals("02")) {
								AssCardInitGeneral assCardInitGeneral = assCardInitGeneralMapper.queryByCode(vdataMap);
								if (assCardInitGeneral != null) {
									mapVo.put("accessory_card_no", assCardInitGeneral.getAss_card_no());
								} else {
									return "{\"msg\":\"" + mapData.get("附卡").get(0)
											+ "，卡片编码不存在不存在！\",\"state\":\"false\",\"row_cell\":\""
											+ mapData.get("附卡").get(0) + "\"}";
								}
							} else if (assNaturs.getNaturs_code().equals("03")) {
								AssCardInitHouse assCardInitHouse = assCardInitHouseMapper.queryByCode(vdataMap);
								if (assCardInitHouse != null) {
									mapVo.put("accessory_card_no", assCardInitHouse.getAss_card_no());
								} else {
									return "{\"msg\":\"" + mapData.get("附卡").get(0)
											+ "，卡片编码不存在不存在！\",\"state\":\"false\",\"row_cell\":\""
											+ mapData.get("附卡").get(0) + "\"}";
								}
							} else if (assNaturs.getNaturs_code().equals("04")) {
								AssCardInitOther assCardInitOther = assCardInitOtherMapper.queryByCode(vdataMap);
								if (assCardInitOther != null) {
									mapVo.put("accessory_card_no", assCardInitOther.getAss_card_no());
								} else {
									return "{\"msg\":\"" + mapData.get("附卡").get(0)
											+ "，卡片编码不存在不存在！\",\"state\":\"false\",\"row_cell\":\""
											+ mapData.get("附卡").get(0) + "\"}";
								}
							} else if (assNaturs.getNaturs_code().equals("05")) {
								AssCardInitInassets assCardInitInassets = assCardInitInassetsMapper
										.queryByCode(vdataMap);
								if (assCardInitInassets != null) {
									mapVo.put("accessory_card_no", assCardInitInassets.getAss_card_no());
								} else {
									return "{\"msg\":\"" + mapData.get("附卡").get(0)
											+ "，卡片编码不存在不存在！\",\"state\":\"false\",\"row_cell\":\""
											+ mapData.get("附卡").get(0) + "\"}";
								}
							} else if (assNaturs.getNaturs_code().equals("06")) {
								AssCardInitLand assCardInitLand = assCardInitLandMapper.queryByCode(vdataMap);
								if (assCardInitLand != null) {
									mapVo.put("accessory_card_no", assCardInitLand.getAss_card_no());
								} else {
									return "{\"msg\":\"" + mapData.get("附卡").get(0)
											+ "，卡片编码不存在不存在！\",\"state\":\"false\",\"row_cell\":\""
											+ mapData.get("附卡").get(0) + "\"}";
								}
							}
						}

					}
				} else {
					mapVo.put("naturs_code", "");
				}

				if (mapData.get("附件数量") != null && !mapData.get("附件数量").equals("")
						&& NumberUtil.isNumeric(mapData.get("附件数量").get(1))) {
					mapVo.put("accessory_amount", mapData.get("附件数量").get(1));
				} else {
					mapVo.put("accessory_amount", null);
				}

				if (mapData.get("附件单价") != null && !mapData.get("附件单价").equals("")
						&& NumberUtil.isNumeric(mapData.get("附件单价").get(1))) {
					mapVo.put("accessory_price", mapData.get("附件单价").get(1));
				} else {
					mapVo.put("accessory_price", null);
				}

				if (mapVo.get("accessory_amount") != null && mapVo.get("accessory_price") != null) {
					mapVo.put("accessory_money", Integer.parseInt(mapVo.get("accessory_amount").toString())
							* Double.parseDouble(mapVo.get("accessory_price").toString()));
				} else {
					mapVo.put("accessory_money", null);
				}

				String note = mapData.get("备注") == null ? "" : mapData.get("备注").get(1);
				mapVo.put("note", note);

				String is_stop = mapData.get("是否停用") == null || mapData.get("是否停用").equals("") ? "0"
						: mapData.get("是否停用").get(1);
				if (is_stop.equals("否")) {
					mapVo.put("is_stop", "0");
				} else if (is_stop.equals("是")) {
					mapVo.put("is_stop", "1");
				} else {
					mapVo.put("is_stop", "0");
				}

				if (entityMap.get("ass_naturs").equals("01")) {

					AssAccessoryInitHouse assAccessoryInitHouse = (AssAccessoryInitHouse) assAccessoryInitHouseMapper
							.queryByCode(mapVo);
					if (assAccessoryInitHouse != null) {
						continue;
					} else {
						assAccessoryInitHouseMapper.add(mapVo);
					}

				} else if (entityMap.get("ass_naturs").equals("02")) {

					AssAccessoryInitSpecial assAccessoryInitSpecial = (AssAccessoryInitSpecial) assAccessoryInitSpecialMapper
							.queryByCode(mapVo);
					if (assAccessoryInitSpecial != null) {
						continue;
					} else {
						assAccessoryInitSpecialMapper.add(mapVo);
					}

				} else if (entityMap.get("ass_naturs").equals("03")) {
					AssAccessoryInitGeneral assAccessoryInitGeneral = (AssAccessoryInitGeneral) assAccessoryInitGeneralMapper
							.queryByCode(mapVo);
					if (assAccessoryInitGeneral != null) {
						continue;
					} else {
						assAccessoryInitGeneralMapper.add(mapVo);
					}

				} else if (entityMap.get("ass_naturs").equals("04")) {

					AssAccessoryInitOther assAccessoryInitOther = (AssAccessoryInitOther) assAccessoryInitOtherMapper
							.queryByCode(mapVo);
					if (assAccessoryInitOther != null) {
						continue;
					} else {
						assAccessoryInitOtherMapper.add(mapVo);
					}
				} else if (entityMap.get("ass_naturs").equals("05")) {

					AssAccessoryInitInassets assAccessoryInitInassets = (AssAccessoryInitInassets) assAccessoryInitInassetsMapper
							.queryByCode(mapVo);
					if (assAccessoryInitInassets != null) {
						continue;
					} else {
						assAccessoryInitInassetsMapper.add(mapVo);
					}
				} else if (entityMap.get("ass_naturs").equals("06")) {

					AssAccessoryInitLand assAccessoryInitLand = (AssAccessoryInitLand) assAccessoryInitLandMapper
							.queryByCode(mapVo);
					if (assAccessoryInitLand != null) {
						continue;
					} else {
						assAccessoryInitLandMapper.add(mapVo);
					}
				}
			}
			return "{\"msg\":\"导入成功。\",\"state\":\"true\"}";
		} catch (Exception e) {
			e.printStackTrace();
			throw new SysException(e.getMessage());
		}
	}

	@Override
	public String importAssCardInitData(Map<String, Object> entityMap) throws Exception {

		StringBuffer errorMsg = new StringBuffer();
		try {
			
			entityMap.put("group_id", SessionManager.getGroupId());
			entityMap.put("hos_id", SessionManager.getHosId());
			entityMap.put("copy_code", SessionManager.getCopyCode());

			// 是否
			Map<String, Object> whetherMap = new HashMap<String, Object>();
			whetherMap.put("是", "1");
			whetherMap.put("否", "0");
			whetherMap.put("1", "1");
			whetherMap.put("0", "0");

			// 在用状态1：在用 0：在库
			Map<String, Object> isDeptMap = new HashMap<String, Object>();
			isDeptMap.put("在用", "1");
			isDeptMap.put("在库", "0");
			isDeptMap.put("1", "1");
			isDeptMap.put("0", "0");

			// 使用状态0:新建1:正常2:待修3:在修4:备用5:闲置6:待处置7:已处置8:处理完毕
			Map<String, Object> useStateMap = new HashMap<String, Object>();
			/*
			 * useStateMap.put("新建", "0"); useStateMap.put("正常", "1"); useStateMap.put("待修",
			 * "2"); useStateMap.put("在修", "3"); useStateMap.put("备用", "4");
			 * useStateMap.put("闲置", "5"); useStateMap.put("待处置", "6");
			 * useStateMap.put("已处置", "7"); useStateMap.put("处理完毕", "8");
			 * useStateMap.put("0", "0"); useStateMap.put("1", "1"); useStateMap.put("2",
			 * "2"); useStateMap.put("3", "3"); useStateMap.put("4", "4");
			 * useStateMap.put("5", "5"); useStateMap.put("6", "6"); useStateMap.put("7",
			 * "7"); useStateMap.put("8", "8");
			 */
			useStateMap.put("新建", "0");
			useStateMap.put("正常", "1");
			useStateMap.put("待修", "2");
			useStateMap.put("在修", "3");
			useStateMap.put("备用", "4");
			useStateMap.put("闲置", "5");
			useStateMap.put("待处置", "6");
			useStateMap.put("已处置", "7");
			useStateMap.put("处理完毕", "8");
			useStateMap.put("0", "0");
			useStateMap.put("1", "1");
			useStateMap.put("2", "1");
			useStateMap.put("3", "7");
			useStateMap.put("4", "7");
			useStateMap.put("5", "5");
			useStateMap.put("6", "3");
			useStateMap.put("7", "6");
			useStateMap.put("8", "8");
			// 资产性质 可通过编码或名称获取编码
			/*
			 * List<AssNaturs> natursList = assNatursMapper.queryAssNaturs(null);
			 * Map<String, String> natursMap = new HashMap<String, String>(); for (AssNaturs
			 * assNaturs : natursList) { natursMap.put(assNaturs.getNaturs_code(),
			 * assNaturs.getNaturs_code()); natursMap.put(assNaturs.getNaturs_name(),
			 * assNaturs.getNaturs_code()); }
			 */

			// 计量单位HOS_UNIT
			Map<String, Object> unitMap = new HashMap<String, Object>();
			unitMap.put("group_id", SessionManager.getGroupId());
			unitMap.put("hos_id", SessionManager.getHosId());
			List<Unit> unitList = unitMapper.queryUnit(unitMap);
			for (Unit unit : unitList) {
				unitMap.put(unit.getUnit_name(), unit.getUnit_code());
				unitMap.put(unit.getUnit_code(), unit.getUnit_code());
			}

			// 资金来源HOS_SOURCE
			Map<String, Object> sourceMap = new HashMap<String, Object>();
			List<Source> sourceList = sourceMapper.querySource(sourceMap);
			for (Source source : sourceList) {
				sourceMap.put(source.getSource_name(), source.getSource_code());
				sourceMap.put(source.getSource_code(), source.getSource_code());
			}

			// 解析EXCEL
			List<Map<String, List<String>>> list = ReadFiles.readFiles(entityMap);
			if (list.size() == 0 || list == null) {
				return "{\"error\":\"表头或者数据为空！请重新导入.\"}";
			}

			for (Map<String, List<String>> map : list) {
				// 获取性质编码 改为从资产字典中获取
				/*
				 * String naturs_code = null; if(map.get("ass_nature") != null &&
				 * map.get("ass_nature").get(1) != null){ naturs_code =
				 * natursMap.get(map.get("ass_nature").get(1)); } if(naturs_code == null){
				 * errorMsg.append(map.get("ass_nature").get(0)+":资产性质不存在！<br/>"); }
				 */

				// 组装数据
				Map<String, Object> data = new HashMap<String, Object>();
				data.put("group_id", SessionManager.getGroupId());
				data.put("hos_id", SessionManager.getHosId());
				data.put("copy_code", SessionManager.getCopyCode());
				data.put("ass_ori_card_no", map.get("ass_ori_card_no").get(1));// 原始卡片号

				// 根据资产编码或名称查询资产ID
				AssNoDict assDict = null;
				if (map.get("ass_code") != null && map.get("ass_code").get(1) != null) {
					data.put("ass_code", map.get("ass_code").get(1));
					assDict = assNoDictMapper.queryAssNoDictByCodeOrName(data);
				}

				if (assDict == null) {
					errorMsg.append(map.get("ass_code").get(0) + ":资产编码不存在！<br/>");
				}

				// 获取性质编码
				String naturs_code = null;
				if (assDict != null && assDict.getNaturs_code() != null) {
					naturs_code = assDict.getNaturs_code();
				}

				if (naturs_code == null) {
					errorMsg.append(map.get("ass_code").get(0) + ":资产未维护资产分类！<br/>");
				}

				// 存在则忽略
				int count = 0;
				if ("01".equals(naturs_code)) {
					count = assCardInitHouseMapper.queryByAssOriCardNo(data);
				} else if ("02".equals(naturs_code)) {
					count = assCardInitSpecialMapper.queryByAssOriCardNo(data);
				} else if ("03".equals(naturs_code)) {
					count = assCardInitGeneralMapper.queryByAssOriCardNo(data);
				} else if ("04".equals(naturs_code)) {
					count = assCardInitOtherMapper.queryByAssOriCardNo(data);
				} else if ("05".equals(naturs_code)) {
					count = assCardInitInassetsMapper.queryByAssOriCardNo(data);
				} else if ("06".equals(naturs_code)) {
					count = assCardInitLandMapper.queryByAssOriCardNo(data);
				}
				if (count > 0) {
					continue;
				}

				// 业务类型 必填
				AssBusTypeDict assBusTypeDict = null;
				if (map.get("bus_type") != null && map.get("bus_type").get(1) != null) {
					Map<String, Object> buyTypeMap = new HashMap<String, Object>();
					buyTypeMap.put("group_id", SessionManager.getGroupId());
					buyTypeMap.put("hos_id", SessionManager.getHosId());
					buyTypeMap.put("copy_code", SessionManager.getCopyCode());
					buyTypeMap.put("bus_type_code", map.get("bus_type").get(1));
					buyTypeMap.put("bus_type_name", map.get("bus_type").get(1));
					assBusTypeDict = assBusTypeDictMapper.queryAssBusTypeDictByCodeOrName(buyTypeMap);
				}

				if (assBusTypeDict == null) {
					errorMsg.append(map.get("bus_type").get(0) + ":业务类型不存在！<br/>");
				}

				// 生产厂商 HOS_FAC_DICT
				AssFacDict hosFacDict = null;
				if (map.get("fac_name") != null && map.get("fac_name").get(1) != null) {
					Map<String, Object> facMap = new HashMap<String, Object>();
					facMap.put("group_id", SessionManager.getGroupId());
					facMap.put("hos_id", SessionManager.getHosId());
					facMap.put("copy_code", SessionManager.getCopyCode());
					facMap.put("fac_code", map.get("fac_name").get(1));
					facMap.put("fac_name", map.get("fac_name").get(1));
					hosFacDict = assFacDictMapper.queryAssFacDictByCodeOrName(facMap);
				}
				// 如果为空 取资产表里生产厂商
				if (hosFacDict == null && assDict != null && assDict.getFac_id() != null) {
					hosFacDict = new AssFacDict();
					hosFacDict.setFac_id(Long.parseLong(assDict.getFac_id()));
					hosFacDict.setFac_no(Long.parseLong(assDict.getFac_no()));
				}

				// 供应商|供地单位|开发商 HOS_SUP_DICT
				AssSupDict assSupDict = null;
				if (map.get("ven_name") != null && map.get("ven_name").get(1) != null) {
					Map<String, Object> supMap = new HashMap<String, Object>();
					supMap.put("group_id", SessionManager.getGroupId());
					supMap.put("hos_id", SessionManager.getHosId());
					supMap.put("copy_code", SessionManager.getCopyCode());
					supMap.put("sup_code", map.get("ven_name").get(1));
					supMap.put("sup_name", map.get("ven_name").get(1));
					assSupDict = assSupDictMapper.queryAssSupDictByCodeOrName(supMap);
				}
				// 如果为空 取资产表里供应商|供地单位|开发商
				if (assSupDict == null && assDict != null && assDict.getVen_id() != null) {
					assSupDict = new AssSupDict();
					assSupDict.setSup_id(Long.parseLong(assDict.getVen_id()));
					assSupDict.setSup_no(Long.parseLong(assDict.getVen_no()));
				}

				if (assSupDict == null || assSupDict.getSup_id() == null) {
					// errorMsg.append(map.get("ven_name").get(0)+":供应商不存在！<br/>");
				}

				// 在用状态
				if (map.get("is_dept") == null || isDeptMap.get(map.get("is_dept").get(1)) == null) {
					errorMsg.append(map.get("is_dept").get(0) + ":在用状态填写'在用'或'在库'！<br/>");
				}

				// 仓库store_name HOS_STORE_DICT
				StoreDict storeDict = null;
				if (map.get("store_name") != null && map.get("store_name").get(1) != null) {
					Map<String, Object> storeMap = new HashMap<String, Object>();
					storeMap.put("group_id", SessionManager.getGroupId());
					storeMap.put("hos_id", SessionManager.getHosId());
					storeMap.put("copy_code", SessionManager.getCopyCode());
					storeMap.put("store_code", map.get("store_name").get(1));
					storeMap.put("store_name", map.get("store_name").get(1));
					storeDict = storeDictMapper.queryStoreDictByCodeOrName(storeMap);
				}
				
				// 使用科室
				DeptDict useDeptDict = null;
				Map<String, Object> useDeptMap = new HashMap<String, Object>();
				if (map.get("use_dept") != null && map.get("use_dept").get(1) != null) {
					// 在用
					Map<String, Object> deptMap = new HashMap<String, Object>();
					deptMap.put("group_id", SessionManager.getGroupId());
					deptMap.put("hos_id", SessionManager.getHosId());
					deptMap.put("copy_code", SessionManager.getCopyCode());
					deptMap.put("dept_code", map.get("use_dept").get(1));
					deptMap.put("dept_name", map.get("use_dept").get(1));
					useDeptDict = deptDictMapper.queryDeptDictByCodeOrName(deptMap);
					if (useDeptDict == null || useDeptDict.getDept_id() == null) {
						errorMsg.append(map.get("use_dept").get(0) + ":使用科室不存在！<br/>");
					}
				} else if (map.get("use_dept") != null && map.get("use_dept").get(0) != null) {
					// 在库 通过库房附属信息维护 获取库房主管部门 做为默认的使用科室
					if (storeDict == null || storeDict.getStore_id() == null) {
						errorMsg.append(map.get("store_name").get(0) + ":仓库不存在！<br/>");
					}
					Map<String, Object> matStoreMap = new HashMap<String, Object>();
					matStoreMap.put("group_id", SessionManager.getGroupId());
					matStoreMap.put("hos_id", SessionManager.getHosId());
					matStoreMap.put("copy_code", SessionManager.getCopyCode());
					matStoreMap.put("store_id", storeDict.getStore_id());
					Map<String, Object> matStore = matStoreMapper.queryByCode(matStoreMap);
					if (matStore != null && matStore.get("dept_id") != null) {
						matStoreMap.put("dept_id", matStore.get("dept_id"));
						useDeptDict = deptDictMapper.queryDeptDictByCode(matStoreMap);
					}
					if (useDeptDict == null || useDeptDict.getDept_id() == null) {
						errorMsg.append(map.get("use_dept").get(0) + ":库房主管部门获取失败，请先维护库房附属信息！<br/>");
					}
				}
				
				//采购仓库
				StoreDict procStoreDict = null;
				if (map.get("proc_store_name") != null && map.get("proc_store_name").get(1) != null) {
					Map<String, Object> storeMap = new HashMap<String, Object>();
					storeMap.put("group_id", SessionManager.getGroupId());
					storeMap.put("hos_id", SessionManager.getHosId());
					storeMap.put("copy_code", SessionManager.getCopyCode());
					storeMap.put("store_code", map.get("proc_store_name").get(1));
					storeMap.put("store_name", map.get("proc_store_name").get(1));
					procStoreDict = storeDictMapper.queryStoreDictByCodeOrName(storeMap);
					if(procStoreDict == null){
						errorMsg.append(map.get("proc_store_name").get(0) + ":采购仓库不存在或维护不正确！<br/>");
					}
				}else{
					errorMsg.append(map.get("proc_store_name").get(0) + ":采购仓库不能为空！<br/>");
				}
				

				

				if (!"".equals(errorMsg.toString())) {
					return "{\"warn\":\"" + errorMsg.toString() + "\"}";
				}

				// 使用科室数据
				useDeptMap.put("group_id", SessionManager.getGroupId());
				useDeptMap.put("hos_id", SessionManager.getHosId());
				useDeptMap.put("copy_code", SessionManager.getCopyCode());
				useDeptMap.put("dept_id", useDeptDict.getDept_id());
				useDeptMap.put("dept_no", useDeptDict.getDept_no());
				useDeptMap.put("area", 1);

				// 管理部门dept_name HOS_DEPT_DICT拿使用科室
				/*
				 * DeptDict deptDict = null; if(map.get("dept_name") != null &&
				 * map.get("dept_name").get(1) != null){ Map<String,Object> deptMap = new
				 * HashMap<String, Object>(); deptMap.put("group_id",
				 * SessionManager.getGroupId()); deptMap.put("hos_id",
				 * SessionManager.getHosId()); deptMap.put("copy_code",
				 * SessionManager.getCopyCode()); deptMap.put("dept_code",
				 * map.get("dept_name").get(1)); deptMap.put("dept_name",
				 * map.get("dept_name").get(1)); deptDict =
				 * deptDictMapper.queryDeptDictByCodeOrName(deptMap); }
				 */

				// 验收人accept_emp SYS_USER
				User user = null;
				if (map.get("accept_emp") != null && map.get("accept_emp").get(1) != null) {
					Map<String, Object> userMap = new HashMap<String, Object>();
					userMap.put("group_id", SessionManager.getGroupId());
					userMap.put("hos_id", SessionManager.getHosId());
					userMap.put("copy_code", SessionManager.getCopyCode());
					userMap.put("user_name", map.get("accept_emp").get(1));
					user = userMapper.queryUserByCode(userMap);
				}

				// 项目proj_name HOS_PROJ_DICT
				ProjDict projDict = null;
				if (map.get("proj_name") != null && map.get("proj_name").get(1) != null) {
					Map<String, Object> projMap = new HashMap<String, Object>();
					projMap.put("group_id", SessionManager.getGroupId());
					projMap.put("hos_id", SessionManager.getHosId());
					projMap.put("copy_code", SessionManager.getCopyCode());
					projMap.put("proj_code", map.get("proj_name").get(1));
					projMap.put("proj_name", map.get("proj_name").get(1));
					projDict = projDictMapper.queryProjDictByCodeOrName(projMap);
				}
				// 用途ass_purpose ASS_USAGE_DICT
				AssUsageDict assUsageDict = null;
				if (map.get("ass_purpose") != null && map.get("ass_purpose").get(1) != null) {
					Map<String, Object> assUsageMap = new HashMap<String, Object>();
					assUsageMap.put("group_id", SessionManager.getGroupId());
					assUsageMap.put("hos_id", SessionManager.getHosId());
					assUsageMap.put("copy_code", SessionManager.getCopyCode());
					assUsageMap.put("equi_usage_code", map.get("ass_purpose").get(1));
					assUsageMap.put("equi_usage_name", map.get("ass_purpose").get(1));
					assUsageDict = assUsageDictMapper.queryAssUsageDictByCodeOrName(assUsageMap);
				}

				// 资金来源source_name
				Map<String, Object> assSourceMap = new HashMap<String, Object>();
				assSourceMap.put("group_id", SessionManager.getGroupId());
				assSourceMap.put("hos_id", SessionManager.getHosId());
				assSourceMap.put("copy_code", SessionManager.getCopyCode());
				assSourceMap.put("price", map.get("price").get(1));
				assSourceMap.put("depre_money", map.get("depre_money").get(1));
				assSourceMap.put("manage_depre_money",
						map.get("manage_depre_money") == null ? 0 : map.get("manage_depre_money").get(1));
				assSourceMap.put("cur_money", map.get("cur_money").get(1));
				assSourceMap.put("fore_money", map.get("fore_money") == null ? 0 : map.get("fore_money").get(1));
				if (map.get("source_name") != null && map.get("source_name").get(1) != null) {
					assSourceMap.put("source_id", sourceMap.get(map.get("source_name").get(1)));
				}

				if (assSourceMap.get("source_id") == null || assSourceMap.get("source_id") == "") {
					// 默认
					assSourceMap.put("source_id", sourceMap.get("自筹资金"));
				}

				data.put("ass_id", assDict.getAss_id());// 资产
				data.put("ass_no", assDict.getAss_no());

				data.put("gb_code", map.get("gb_code") == null ? null : map.get("gb_code").get(1));// 国标码
				data.put("ass_spec", map.get("ass_spec") == null ? null : map.get("ass_spec").get(1));// 规格
				data.put("ass_mondl", map.get("ass_mondl") == null ? null : map.get("ass_mondl").get(1));// 型号
				data.put("ass_brand", map.get("ass_brand") == null ? null : map.get("ass_brand").get(1));// 品牌

				if (Integer.parseInt(map.get("ass_amount").get(1)) > 1) {
					errorMsg.append(map.get("ass_amount").get(0) + ":卡片数量必须为1！<br/>");
				}

				data.put("ass_amount", map.get("ass_amount") == null ? 1 : map.get("ass_amount").get(1));// 数量
				data.put("unit_code", (map.get("unit_code") == null || unitMap == null) ? null
						: unitMap.get(map.get("unit_code").get(1)));// 计量单位

				data.put("price", map.get("price").get(1));// 资产原值
				data.put("depre_money", map.get("depre_money").get(1));// 累计折旧
				data.put("manage_depre_money",
						map.get("manage_depre_money") == null ? 0 : map.get("manage_depre_money").get(1));// 累计分摊
				data.put("cur_money", map.get("cur_money").get(1));// 资产净值
				data.put("fore_money", map.get("fore_money") == null ? 0 : map.get("fore_money").get(1));// 预留残值

				data.put("use_state", useStateMap.get(map.get("use_state").get(1)));// 使用状态

				data.put("buy_type", assBusTypeDict.getBus_type_code());// 业务类型

				data.put("fac_id", hosFacDict == null ? null : hosFacDict.getFac_id());// 生产厂商
				data.put("fac_no", hosFacDict == null ? null : hosFacDict.getFac_no());
				data.put("ven_id", assSupDict == null ? null : assSupDict.getSup_id());// 供应商|供地单位|开发商
				data.put("ven_no", assSupDict == null ? null : assSupDict.getSup_no());
				data.put("is_dept", isDeptMap.get(map.get("is_dept").get(1)));// 1：在用 0：在库
				data.put("dept_id", useDeptDict == null ? null : useDeptDict.getDept_id());// 管理部门
				data.put("dept_no", useDeptDict == null ? null : useDeptDict.getDept_no());
				data.put("store_id", storeDict == null ? null : storeDict.getStore_id());// 仓库
				data.put("store_no", storeDict == null ? null : storeDict.getStore_no());
				
				data.put("proc_store_id", procStoreDict == null ? null : procStoreDict.getStore_id());// 仓库
				data.put("proc_store_no", procStoreDict == null ? null : procStoreDict.getStore_no());

				data.put("is_depr", map.get("is_depr") == null ? 0 : whetherMap.get(map.get("is_depr").get(1)));// 是否折旧
				if (data.get("is_depr") != null && "1".equals(data.get("is_depr"))) {
					data.put("depr_method", "01");// 折旧方法
					data.put("acc_depre_amount",
							map.get("acc_depre_amount") == null ? null : map.get("acc_depre_amount").get(1));// 计提年限
				} else {
					data.put("depr_method", null);// 折旧方法
					data.put("acc_depre_amount", null);// 计提年限
				}

				data.put("is_manage_depre",
						map.get("is_manage_depre") == null ? 0 : whetherMap.get(map.get("is_manage_depre").get(1)));// 是否分摊
				if (data.get("is_manage_depre") != null && "1".equals(data.get("is_manage_depre"))) {
					data.put("manage_depr_method", "01");// 分摊方法
					data.put("manage_depre_amount",
							map.get("manage_depre_amount") == null ? null : map.get("manage_depre_amount").get(1));// 分摊年限
				} else {
					data.put("manage_depr_method", null);// 分摊方法
					data.put("manage_depre_amount", null);// 分摊年限
				}

				data.put("is_measure",
						map.get("is_measure") == null ? 0 : whetherMap.get(map.get("is_measure").get(1))); // 是否计量
				data.put("is_throw", map.get("is_throw") == null ? 0 : whetherMap.get(map.get("is_throw").get(1))); // 是否投放
				// data.put("pact_code", map.get("pact_code")== null ?
				// null:map.get("pact_code").get(1)); //合同id
				data.put("pact_code", null);
				data.put("ins_money", map.get("ins_money") == null ? null : map.get("ins_money").get(1)); // 安装费用
				data.put("ins_date", map.get("ins_date") == null ? null : map.get("ins_date").get(1)); // 安装日期
				data.put("accept_emp", user == null ? null : user.getUser_id()); // 验收人
				data.put("accept_date", map.get("accept_date") == null ? null : map.get("accept_date").get(1)); // 验收日期|竣工日期
				data.put("service_date", map.get("service_date") == null ? null : map.get("service_date").get(1)); // 保修截止日期
				data.put("ass_seq_no", map.get("ass_seq_no") == null ? null : map.get("ass_seq_no").get(1)); // 序列号
				data.put("location", map.get("location").get(1) == null ? null : map.get("location").get(1)); // 存放位置|坐落位置
				data.put("ass_purpose", assUsageDict == null ? null : assUsageDict.getEqui_usage_code()); // 用途
				data.put("proj_id", projDict == null ? null : projDict.getProj_id()); // 项目id
				data.put("proj_no", projDict == null ? null : projDict.getProj_no()); // 项目变更id
				data.put("note", map.get("note") == null ? null : map.get("note").get(1)); // 备注
				data.put("ass_in_no", map.get("ass_in_no") == null ? null : map.get("ass_in_no").get(1)); // 入库单号
				data.put("in_date", map.get("in_date") == null ? null : map.get("in_date").get(1)); // 入库日期
				data.put("run_date", map.get("run_date") == null ? null : map.get("run_date").get(1)); // 投入使用日期
				data.put("is_bar", "1"); // 是否条码管理
				data.put("bar_type", "2"); // 1:一维条码 2:二维条码
				data.put("bar_code",
						map.get("bar_code") == null || map.get("bar_code").equals("") ? data.get("ass_ori_card_no").toString() : map.get("bar_code").get(1)); // 条形码
				// data.put("bar_url", map.get("bar_url").get(1)); //条码url
				data.put("add_depre_month",
						map.get("add_depre_month") == null ? null : map.get("add_depre_month").get(1)); /// 累计折旧月数
				data.put("add_manage_month",
						map.get("add_manage_month") == null ? null : map.get("add_manage_month").get(1)); // 累计分摊月数
				data.put("man_code",
						map.get("man_code") == null ? null : map.get("man_code").get(1));
				String basePath = "ass";
				String group_id = SessionManager.getGroupId();
				String hos_id = SessionManager.getHosId();
				String copy_code = SessionManager.getCopyCode();
				String assTwoPath = "assbartwo";

				entityMap.put("store_code", map.get("proc_store_name").get(1));
				entityMap.put("year", map.get("year"));
				entityMap.put("month", map.get("month"));
				entityMap.put("day", map.get("day"));
				
				// 房屋、土地
				if ("01".equals(naturs_code) || "06".equals(naturs_code)) {

					data.put("cert_name", map.get("cert_name") == null ? null : map.get("cert_name").get(1));// 权属证明
					data.put("cert_code", map.get("cert_code") == null ? null : map.get("cert_code").get(1));// 权属证号
					data.put("cert_date", map.get("cert_date") == null ? null : map.get("cert_date").get(1));// 发证日期
					data.put("prop_code", map.get("prop_code") == null ? null : map.get("prop_code").get(1));// 产权形式

				}

				if ("02".equals(naturs_code)) {// 专用
					String twoFilePath = group_id + "/" + hos_id + "/" + copy_code + "/" + basePath + "/" + assTwoPath
							+ "/02/";// 资产性质
					FtpUtil.getQRWriteFile(data.get("bar_code").toString(), "", twoFilePath,
							data.get("bar_code").toString() + ".png");// 二维码
					data.put("bar_url", twoFilePath + data.get("bar_code").toString() + ".png");
					entityMap.put("bill_table", "ASS_CARD_SPECIAL");
					String cardNo = assBaseService.getBillNOSeqNo(entityMap);
					data.put("ass_card_no", cardNo);
					assCardInitSpecialMapper.add(data);// 卡片
					if (!useDeptMap.isEmpty()) {
						useDeptMap.put("ass_card_no", cardNo);
						assShareDeptInitSpecialMapper.add(useDeptMap);// 使用科室
					}

					// assSourceMap.put("ass_card_no", cardNo);
					// assResourceInitSpecialMapper.add(assSourceMap);//资金来源
					assBaseService.updateAssBillNoMaxNo(entityMap);

				} else if ("03".equals(naturs_code)) {// 一般
					String twoFilePath = group_id + "/" + hos_id + "/" + copy_code + "/" + basePath + "/" + assTwoPath
							+ "/03/";// 资产性质
					FtpUtil.getQRWriteFile(data.get("bar_code").toString(), "", twoFilePath,
							data.get("bar_code").toString() + ".png");// 二维码
					data.put("bar_url", twoFilePath + data.get("bar_code").toString() + ".png");
					data.put("car_place", map.get("car_place") == null ? null : map.get("car_place").get(1));// 车辆产地
					data.put("car_sign", map.get("car_sign") == null ? null : map.get("car_sign").get(1));// 车牌号
					data.put("car_frame", map.get("car_frame") == null ? null : map.get("car_frame").get(1));// 车架号
					data.put("car_engine", map.get("car_engine") == null ? null : map.get("car_engine").get(1));// 发动机号
					data.put("car_brand", map.get("car_brand") == null ? null : map.get("car_brand").get(1));// 厂牌型号
					data.put("car_gas", map.get("car_gas") == null ? null : map.get("car_gas").get(1));// 排气量
					entityMap.put("bill_table", "ASS_CARD_GENERAL");
					String cardNo = assBaseService.getBillNOSeqNo(entityMap);
					data.put("ass_card_no", cardNo);
					assCardInitGeneralMapper.add(data);
					if (!useDeptMap.isEmpty()) {
						useDeptMap.put("ass_card_no", cardNo);
						assShareDeptInitGeneralMapper.add(useDeptMap);// 使用科室
					}

					// assSourceMap.put("ass_card_no", cardNo);
					// assResourceInitGeneralMapper.add(assSourceMap);//资金来源
					assBaseService.updateAssBillNoMaxNo(entityMap);

				} else if ("01".equals(naturs_code)) {// 房屋

					data.put("struct_code", map.get("struct_code") == null ? null : map.get("struct_code").get(1));// 建筑结构
					data.put("house_area", map.get("house_area") == null ? null : map.get("house_area").get(1));// 建筑面积
					data.put("use_area", map.get("use_area") == null ? null : map.get("use_area").get(1));// 使用面积
					data.put("base_area", map.get("base_area") == null ? null : map.get("base_area").get(1));// 地下面积
					entityMap.put("bill_table", "ASS_CARD_HOUSE");
					String cardNo = assBaseService.getBillNOSeqNo(entityMap);
					data.put("ass_card_no", cardNo);
					assCardInitHouseMapper.add(data);
					if (!useDeptMap.isEmpty()) {
						useDeptMap.put("ass_card_no", cardNo);
						assShareDeptInitHouseMapper.add(useDeptMap);// 使用科室
					}

					// assSourceMap.put("ass_card_no", cardNo);
					// assResourceInitHouseMapper.add(assSourceMap);//资金来源
					assBaseService.updateAssBillNoMaxNo(entityMap);

				} else if ("04".equals(naturs_code)) {// 其他固定资产
					String twoFilePath = group_id + "/" + hos_id + "/" + copy_code + "/" + basePath + "/" + assTwoPath
							+ "/04/";// 资产性质
					FtpUtil.getQRWriteFile(data.get("bar_code").toString(), "", twoFilePath,
							data.get("bar_code").toString() + ".png");// 二维码
					data.put("bar_url", twoFilePath + data.get("bar_code").toString() + ".png");
					data.put("book_amount", map.get("book_amount") == null ? null : map.get("book_amount").get(1));// 图书数量
					data.put("relic_amount", map.get("relic_amount") == null ? null : map.get("relic_amount").get(1));// 文物数量
					data.put("relic_grade_code",
							map.get("relic_grade_code") == null ? null : map.get("relic_grade_code").get(1));// 文物等级
					entityMap.put("bill_table", "ASS_CARD_OTHER");
					String cardNo = assBaseService.getBillNOSeqNo(entityMap);
					data.put("ass_card_no", cardNo);
					assCardInitOtherMapper.add(data);
					if (!useDeptMap.isEmpty()) {
						useDeptMap.put("ass_card_no", cardNo);
						assShareDeptInitOtherMapper.add(useDeptMap);// 使用科室
					}

					// assSourceMap.put("ass_card_no", cardNo);
					// assResourceInitOtherMapper.add(assSourceMap);//资金来源
					assBaseService.updateAssBillNoMaxNo(entityMap);

				} else if ("05".equals(naturs_code)) {// 其他无形资产
                    entityMap.put("bill_table", "ASS_CARD_INASSETS");
					String cardNo = assBaseService.getBillNOSeqNo(entityMap);
					data.put("ass_card_no", cardNo);
					assCardInitInassetsMapper.add(data);
					if (!useDeptMap.isEmpty()) {
						useDeptMap.put("ass_card_no", cardNo);
						assShareDeptInitInassetsMapper.add(useDeptMap);// 使用科室
					}

					// assSourceMap.put("ass_card_no", cardNo);
					// assResourceInitInassetsMapper.add(assSourceMap);//资金来源
					assBaseService.updateAssBillNoMaxNo(entityMap);

				} else if ("06".equals(naturs_code)) {// 土地

					data.put("land_area", map.get("land_area") == null ? null : map.get("land_area").get(1));// 占地面积
					data.put("land_no", map.get("land_no") == null ? null : map.get("land_no").get(1));// 地号
					data.put("gain_date", map.get("gain_date") == null ? null : map.get("gain_date").get(1));// 取得日期
					data.put("land_source_code",
							map.get("land_source_code") == null ? null : map.get("land_source_code").get(1));// 土地来源
					entityMap.put("bill_table", "ASS_CARD_LAND");
					String cardNo = assBaseService.getBillNOSeqNo(entityMap);
					data.put("ass_card_no", cardNo);
					assCardInitLandMapper.add(data);
					if (!useDeptMap.isEmpty()) {
						useDeptMap.put("ass_card_no", cardNo);
						assShareDeptInitLandMapper.add(useDeptMap);// 使用科室
					}

					// assSourceMap.put("ass_card_no", cardNo);
					// assResourceInitLandMapper.add(assSourceMap);//资金来源
					assBaseService.updateAssBillNoMaxNo(entityMap);

				}

			}

			/*
			 * if(!"".equals(errorMsg.toString())){ return
			 * "{\"warn\":\""+errorMsg.toString()+"\"}"; }
			 */
			if (!"".equals(errorMsg.toString())) {
				throw new SysException(errorMsg.toString());
				// continue;
			}
			return "{\"msg\":\"导入成功。\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}
	}

	@Override
	public String initAccount(Map<String, Object> entityMap) throws Exception {
		try {
			entityMap.put("group_id", SessionManager.getGroupId());
			entityMap.put("hos_id", SessionManager.getHosId());
			entityMap.put("copy_code", SessionManager.getCopyCode());

			if (assInitAccountMapper.selectInitCardExists(entityMap) == 0) {
				return "{\"warn\":\"期初卡片不存在,无法建账！\"}";
			}
			if (assInitAccountMapper.selectInitResourceExists(entityMap) == 0) {
				return "{\"warn\":\"期初卡片资金来源未维护,无法建账！\"}";
			}

			List<Map<String, Object>> vSourceCardList = assInitAccountMapper.querResourceValidate(entityMap);

			for (Map<String, Object> sourceCard : vSourceCardList) {
				if ((int) Double.parseDouble(sourceCard.get("price").toString()) != 0) {
					return "{\"warn\":\"期初卡片{" + sourceCard.get("ass_card_no").toString()
							+ "}资金来源原值和卡片原值不匹配或资金来源未维护！\"}";
				}
				if ((int) Double.parseDouble(sourceCard.get("depre_money").toString()) != 0) {
					return "{\"warn\":\"期初卡片{" + sourceCard.get("ass_card_no").toString()
							+ "}资金来源累计折旧和卡片累计折旧不匹配或资金来源未维护！\"}";
				}
				if ((int) Double.parseDouble(sourceCard.get("manage_depre_money").toString()) != 0) {
					return "{\"warn\":\"期初卡片{" + sourceCard.get("ass_card_no").toString()
							+ "}资金来源累计分摊和卡片累计分摊不匹配或资金来源未维护！\"}";
				}
				if ((int) Double.parseDouble(sourceCard.get("cur_money").toString()) != 0) {
					return "{\"warn\":\"期初卡片{" + sourceCard.get("ass_card_no").toString()
							+ "}资金来源净值和卡片净值不匹配或资金来源未维护！\"}";
				}
				if ((int) Double.parseDouble(sourceCard.get("fore_money").toString()) != 0) {
					return "{\"warn\":\"期初卡片{" + sourceCard.get("ass_card_no").toString()
							+ "}资金来源残值和卡片残值不匹配或资金来源未维护！\"}";
				}
			}

			if (assInitAccountMapper.selectInitShareDeptExists(entityMap) == 0) {
				return "{\"warn\":\"期初卡片使用科室未维护,无法建账！\"}";
			}

			List<Map<String, Object>> vShareDeptList = assInitAccountMapper.querShareDeptValidate(entityMap);

			for (Map<String, Object> shareDept : vShareDeptList) {
				if (shareDept.get("area") != null && !shareDept.get("area").equals("")) {
					if ((int) Double.parseDouble(shareDept.get("area").toString()) < 1) {
						return "{\"warn\":\"期初卡片{" + shareDept.get("ass_card_no").toString() + "}使用科室未维护或维护不正确！\"}";
					}
				}
			}

			if (assInitAccountMapper.selectInitDepreAccExists(entityMap) > 0) {
				List<Map<String, Object>> vDepreAccList = assInitAccountMapper.querDepreAccValidate(entityMap);
				for (Map<String, Object> depreAcc : vDepreAccList) {
					if ((int) Double.parseDouble(depreAcc.get("price").toString()) != 0) {
						return "{\"warn\":\"期初卡片{" + depreAcc.get("ass_card_no").toString()
								+ "}折旧记录原值和卡片原值不匹配或资金来源未维护！\"}";
					}
					if ((int) Double.parseDouble(depreAcc.get("depre_money").toString()) != 0) {
						return "{\"warn\":\"期初卡片{" + depreAcc.get("ass_card_no").toString()
								+ "}折旧记录累计折旧和卡片累计折旧不匹配或资金来源未维护！\"}";
					}
					if ((int) Double.parseDouble(depreAcc.get("add_depre_month").toString()) != 0) {
						return "{\"warn\":\"期初卡片{" + depreAcc.get("ass_card_no").toString()
								+ "}折旧记录累计折旧月数和卡片累计折旧月数不匹配或资金来源未维护！\"}";
					}
					if ((int) Double.parseDouble(depreAcc.get("cur_money").toString()) != 0) {
						return "{\"warn\":\"期初卡片{" + depreAcc.get("ass_card_no").toString()
								+ "}折旧记录净值和卡片净值不匹配或资金来源未维护！\"}";
					}
					if ((int) Double.parseDouble(depreAcc.get("fore_money").toString()) != 0) {
						return "{\"warn\":\"期初卡片{" + depreAcc.get("ass_card_no").toString()
								+ "}折旧记录残值和卡片残值不匹配或资金来源未维护！\"}";
					}
				}
			}

			assInitAccountMapper.addCardAccount(entityMap);

			assInitAccountMapper.addResourceAccount(entityMap);

			assInitAccountMapper.addShareDeptAccount(entityMap);

			String yearMonth = DateUtil.dateFormat(new Date(), "yyyyMMdd");

			entityMap.put("ass_year", yearMonth.substring(0, 4));

			entityMap.put("ass_month", yearMonth.substring(4, 6));

			assInitAccountMapper.addShareDeptRAccount(entityMap);

			assInitAccountMapper.addPayStageAccount(entityMap);

			assInitAccountMapper.addFileAccount(entityMap);

			assInitAccountMapper.addPhotoAccount(entityMap);

			assInitAccountMapper.addDepreAccAccount(entityMap);

			assInitAccountMapper.addDepreManageAccount(entityMap);

			assInitAccountMapper.addAccessoryAccount(entityMap);
			//创建期初建账标识表 
			Map<String, Object> flagMap = new HashMap<String, Object>();
			flagMap.put("group_id", entityMap.get("group_id"));
			flagMap.put("hos_id", entityMap.get("hos_id"));
			flagMap.put("copy_code", entityMap.get("copy_code"));
			flagMap.put("acc_year", yearMonth.substring(0, 4));
			flagMap.put("acc_month", yearMonth.substring(4, 6));
			flagMap.put("year_month", yearMonth.substring(0, 4)+yearMonth.substring(4, 6));
			flagMap.put("init_date", DateUtil.dateFormat1(new Date(), "yyyy-MM-dd"));

			assInitAccountMapper.addCardInitFlag(flagMap);
			return "{\"msg\":\"建账成功!\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public String importAssResourceInitData(Map<String, Object> entityMap) throws Exception {
		StringBuffer errorMsg = new StringBuffer();

		try {
			// 资金来源HOS_SOURCE
			Map<String, Object> sourceMap = new HashMap<String, Object>();
			List<Source> sourceList = sourceMapper.querySource(sourceMap);
			for (Source source : sourceList) {
				sourceMap.put(source.getSource_name(), source.getSource_code());
				sourceMap.put(source.getSource_code(), source.getSource_code());
			}
			List<Map<String, List<String>>> list = ReadFiles.readFiles(entityMap);
			if (list.size() == 0 || list == null) {
				return "{\"error\":\"表头或者数据为空！请重新导入.\"}";
			}

			for (Map<String, List<String>> map : list) {

				// 组装数据
				Map<String, Object> data = new HashMap<String, Object>();
				data.put("group_id", SessionManager.getGroupId());
				data.put("hos_id", SessionManager.getHosId());
				data.put("copy_code", SessionManager.getCopyCode());

				Map<String, Object> cardData = new HashMap<String, Object>();
				cardData.put("group_id", SessionManager.getGroupId());
				cardData.put("hos_id", SessionManager.getHosId());
				cardData.put("copy_code", SessionManager.getCopyCode());
				cardData.put("ass_card_no", map.get("ass_ori_card_no").get(1));
				Map<String, Object> cardMap = assCardBaseMapper.queryAssCardInitByCardNo(cardData);

				if (cardMap == null) {
					continue;
				}

				// 获取性质编码
				String naturs_code = null;
				if (cardMap != null) {
					naturs_code = cardMap.get("ass_naturs").toString();
				}

				if (naturs_code == null) {
					errorMsg.append(map.get("ass_ori_card_no").get(0) + ":卡片资产未维护资产分类！<br/>");
				}

				String ass_card_no = null;

				data.put("ass_ori_card_no", map.get("ass_ori_card_no").get(1));// 原始卡片编号

				if ("01".equals(naturs_code)) {
					AssCardInitHouse assCardInitHouse = assCardInitHouseMapper.queryByUniqueness(data);
					ass_card_no = assCardInitHouse.getAss_card_no();
				} else if ("02".equals(naturs_code)) {
					AssCardInitSpecial assCardInitSpecial = assCardInitSpecialMapper.queryByUniqueness(data);
					ass_card_no = assCardInitSpecial.getAss_card_no();
				} else if ("03".equals(naturs_code)) {
					AssCardInitGeneral assCardInitGeneral = assCardInitGeneralMapper.queryByUniqueness(data);
					ass_card_no = assCardInitGeneral.getAss_card_no();
				} else if ("04".equals(naturs_code)) {
					AssCardInitOther assCardInitOther = assCardInitOtherMapper.queryByUniqueness(data);
					ass_card_no = assCardInitOther.getAss_card_no();
				} else if ("05".equals(naturs_code)) {
					AssCardInitInassets assCardInitInassets = assCardInitInassetsMapper.queryByUniqueness(data);
					ass_card_no = assCardInitInassets.getAss_card_no();
				} else if ("06".equals(naturs_code)) {
					AssCardInitLand assCardInitLand = assCardInitLandMapper.queryByUniqueness(data);
					ass_card_no = assCardInitLand.getAss_card_no();
				}

				if (ass_card_no == null || "".equals(ass_card_no)) {
					errorMsg.append(map.get("ass_ori_card_no").get(0) + ":卡片编号不存在！<br/>");
				}

				// 资金来源
				String source_id = null;
				if (map.get("source_id") != null && map.get("source_id").get(1) != null) {
					source_id = sourceMap.get(map.get("source_id").get(1)).toString();
				}

				if (source_id == null || "".equals(source_id)) {
					errorMsg.append(map.get("source_id").get(0) + ":资金来源不存在！<br/>");
				}

				if (!"".equals(errorMsg.toString())) {
					throw new SysException(errorMsg.toString());
				}

				data.put("ass_card_no", ass_card_no);// 卡片号
				data.put("source_id", source_id);// 资金来源编码

				data.put("price", map.get("price").get(0) == null ? "0" : map.get("price").get(1));
				data.put("depre_money", map.get("depre_money").get(0) == null ? "0" : map.get("depre_money").get(1));
				data.put("manage_depre_money",
						map.get("manage_depre_money").get(0) == null ? "0" : map.get("manage_depre_money").get(1));
				data.put("cur_money", map.get("cur_money").get(0) == null ? "0" : map.get("cur_money").get(1));
				data.put("fore_money", map.get("fore_money").get(0) == null ? "0" : map.get("fore_money").get(1));
				data.put("pay_money", map.get("pay_money").get(0) == null ? "0" : map.get("pay_money").get(1));
				data.put("unpay_money", map.get("unpay_money").get(0) == null ? "0" : map.get("unpay_money").get(1));
				/***
				 * ********************************************************************************************************************
				 * 东阳数据不准确,给东阳使用，相差0.05以下的付款进行矫正
				 */
				if (data.get("unpay_money") != null && !"".equals(data.get("unpay_money"))
						&& !"0".equals(data.get("unpay_money"))) {
					Double unpay_money = Double.parseDouble(data.get("unpay_money").toString());
					if (unpay_money <= 0.05) {
						if (data.get("pay_money") != null && !"".equals(data.get("pay_money"))
								&& !"0".equals(data.get("pay_money"))) {
							data.put("pay_money", Double.parseDouble(data.get("pay_money").toString()) + unpay_money);
							data.put("unpay_money", "0");
						}
					}
				}

				/**
				 * *********************************************************************************************************************
				 */

				if ("01".equals(naturs_code)) {
					List<AssResourceInitHouse> slist = (List<AssResourceInitHouse>) assResourceInitHouseMapper
							.queryExists(data);
					if (slist.size() > 0) {
						assResourceInitHouseMapper.update(data);
					} else {
						assResourceInitHouseMapper.add(data);
					}
				} else if ("02".equals(naturs_code)) {
					List<AssResourceInitSpecial> slist = (List<AssResourceInitSpecial>) assResourceInitSpecialMapper
							.queryExists(data);
					if (slist.size() > 0) {
						assResourceInitSpecialMapper.update(data);
					} else {
						assResourceInitSpecialMapper.add(data);
					}
				} else if ("03".equals(naturs_code)) {
					List<AssResourceInitGeneral> slist = (List<AssResourceInitGeneral>) assResourceInitGeneralMapper
							.queryExists(data);
					if (slist.size() > 0) {
						assResourceInitGeneralMapper.update(data);
					} else {
						assResourceInitGeneralMapper.add(data);
					}
				} else if ("04".equals(naturs_code)) {
					List<AssResourceInitOther> slist = (List<AssResourceInitOther>) assResourceInitOtherMapper
							.queryExists(data);
					if (slist.size() > 0) {
						assResourceInitOtherMapper.update(data);
					} else {
						assResourceInitOtherMapper.add(data);
					}
				} else if ("05".equals(naturs_code)) {
					List<AssResourceInitInassets> slist = (List<AssResourceInitInassets>) assResourceInitInassetsMapper
							.queryExists(data);
					if (slist.size() > 0) {
						assResourceInitInassetsMapper.update(data);
					} else {
						assResourceInitInassetsMapper.add(data);
					}
				} else if ("06".equals(naturs_code)) {
					List<AssResourceInitLand> slist = (List<AssResourceInitLand>) assResourceInitLandMapper
							.queryExists(data);
					if (slist.size() > 0) {
						assResourceInitLandMapper.update(data);
					} else {
						assResourceInitLandMapper.add(data);
					}
				}

			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new SysException(e.getMessage());
		}
		return "{\"msg\":\"导入成功。\",\"state\":\"true\"}";
	}

	@Override
	public String importAssDepreInitData(Map<String, Object> entityMap) throws Exception {

		StringBuffer errorMsg = new StringBuffer();

		try {
			// 资金来源HOS_SOURCE
			Map<String, Object> sourceMap = new HashMap<String, Object>();
			List<Source> sourceList = sourceMapper.querySource(sourceMap);
			for (Source source : sourceList) {
				sourceMap.put(source.getSource_name(), source.getSource_code());
				sourceMap.put(source.getSource_code(), source.getSource_code());
			}

			// 资产性质 可通过编码或名称获取编码
			/*
			 * List<AssNaturs> natursList = assNatursMapper.queryAssNaturs(null);
			 * Map<String, String> natursMap = new HashMap<String, String>(); for (AssNaturs
			 * assNaturs : natursList) { natursMap.put(assNaturs.getNaturs_code(),
			 * assNaturs.getNaturs_code()); natursMap.put(assNaturs.getNaturs_name(),
			 * assNaturs.getNaturs_code()); }
			 */

			// 解析EXCEL
			List<Map<String, List<String>>> list = ReadFiles.readFiles(entityMap);
			if (list.size() == 0 || list == null) {
				return "{\"error\":\"表头或者数据为空！请重新导入.\"}";
			}

			for (Map<String, List<String>> map : list) {

				// 校验数据 暂用前台判断

				// 组装数据
				Map<String, Object> data = new HashMap<String, Object>();
				data.put("group_id", SessionManager.getGroupId());
				data.put("hos_id", SessionManager.getHosId());
				data.put("copy_code", SessionManager.getCopyCode());
				data.put("depre_no", map.get("depre_no").get(1));// 折旧序号

				Map<String, Object> cardData = new HashMap<String, Object>();
				cardData.put("group_id", SessionManager.getGroupId());
				cardData.put("hos_id", SessionManager.getHosId());
				cardData.put("copy_code", SessionManager.getCopyCode());
				cardData.put("ass_card_no", map.get("ass_card_no").get(1));
				Map<String, Object> cardMap = assCardBaseMapper.queryAssCardInitByCardNo(cardData);

				if (cardMap == null) {
					errorMsg.append(map.get("ass_card_no").get(0) + ":卡片编码不存在！<br/>");
				}

				// 获取性质编码
				String naturs_code = null;
				if (cardMap != null) {
					naturs_code = cardMap.get("ass_naturs").toString();
				}

				if (naturs_code == null) {
					errorMsg.append(map.get("ass_card_no").get(0) + ":卡片资产未维护资产分类！<br/>");
				}

				// 存在则忽略
				int count = 0;
				if ("01".equals(naturs_code)) {
					count = assDepreAccInitHouseMapper.queryCountByCode(data);
				} else if ("02".equals(naturs_code)) {
					count = assDepreAccInitSpecialMapper.queryCountByCode(data);
				} else if ("03".equals(naturs_code)) {
					count = assDepreAccInitGeneralMapper.queryCountByCode(data);
				} else if ("04".equals(naturs_code)) {
					count = assDepreAccInitOtherMapper.queryCountByCode(data);
				} else if ("05".equals(naturs_code)) {
					count = assDepreAccInitInassetsMapper.queryCountByCode(data);
				} else if ("06".equals(naturs_code)) {
					count = assDepreAccInitLandMapper.queryCountByCode(data);
				}
				if (count > 0) {
					continue;
				}

				// 使用科室
				DeptDict useDeptDict = null;
				Map<String, Object> deptMap = new HashMap<String, Object>();
				if (map.get("use_dept") != null && map.get("use_dept").get(1) != null) {
					// 在用
					deptMap.put("group_id", SessionManager.getGroupId());
					deptMap.put("hos_id", SessionManager.getHosId());
					deptMap.put("copy_code", SessionManager.getCopyCode());
					deptMap.put("dept_code", map.get("use_dept").get(1));
					deptMap.put("dept_name", map.get("use_dept").get(1));
					useDeptDict = deptDictMapper.queryDeptDictByCodeOrName(deptMap);
					// 查询可能存在已停用的科室
					if (useDeptDict == null || useDeptDict.getDept_id() == null) {
						deptMap.put("dept_name", null);
						deptMap.put("is_stop", 1);
						useDeptDict = deptDictMapper.queryDeptDictByDeptCode(deptMap);
					}
				}

				if (useDeptDict == null || useDeptDict.getDept_id() == null) {
					errorMsg.append(map.get("use_dept").get(0) + ":使用科室不存在！<br/>");
				}

				// 资金来源
				String source_id = null;
				if (map.get("source_id") != null && map.get("source_id").get(1) != null) {
					source_id = sourceMap.get(map.get("source_id").get(1)).toString();
				}

				if (source_id == null || "".equals(source_id)) {
					errorMsg.append(map.get("source_id").get(0) + ":资金来源不存在！<br/>");
				}

				if (!"".equals(errorMsg.toString())) {
					throw new SysException(errorMsg.toString());
				}

				data.put("depre_year", map.get("depre_year").get(1));// 折旧年度
				data.put("depre_month", map.get("depre_month").get(1));// 折旧期间
				data.put("ass_card_no", map.get("ass_card_no").get(1));// 卡片号
				data.put("source_id", source_id);// 资金来源
				data.put("use_dept_no", useDeptDict.getDept_no());// 使用科室
				data.put("use_dept_id", useDeptDict.getDept_id());
				data.put("use_percent", map.get("use_percent").get(1));// 分摊比例
				data.put("prim_money", map.get("prim_money").get(1));// 原值
				data.put("now_depre_amount", map.get("now_depre_amount").get(1));// 本期折旧
				data.put("add_depre_amount", map.get("add_depre_amount").get(1));// 累计折旧
				data.put("add_depre_month", map.get("add_depre_month").get(1));// 累计折旧月份
				data.put("cur_money", map.get("cur_money").get(1));// 现值
				data.put("fore_money", map.get("fore_money").get(1));// 残值
				data.put("operator", map.get("operator").get(1));// 操作员
				data.put("deal_date", map.get("deal_date").get(1));// 处理日期
				// data.put("equi_depre_code", map.get("equi_depre_code").get(1));
				data.put("equi_depre_code", "01");// 折旧方法
				data.put("note",
						map.get("note") == null || map.get("note").get(1) == null ? "" : map.get("note").get(1));// 摘要

				if ("01".equals(naturs_code)) {
					assDepreAccInitHouseMapper.add(data);
				} else if ("02".equals(naturs_code)) {
					assDepreAccInitSpecialMapper.add(data);
				} else if ("03".equals(naturs_code)) {
					assDepreAccInitGeneralMapper.add(data);
				} else if ("04".equals(naturs_code)) {
					assDepreAccInitOtherMapper.add(data);
				} else if ("05".equals(naturs_code)) {
					assDepreAccInitInassetsMapper.add(data);
				} else if ("06".equals(naturs_code)) {
					assDepreAccInitLandMapper.add(data);
				}

			}

		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new SysException(e.getMessage());
		}
		return "{\"msg\":\"导入成功。\",\"state\":\"true\"}";
	}

	@Override
	public String importAssManageDepreInitData(Map<String, Object> entityMap) throws Exception {

		StringBuffer errorMsg = new StringBuffer();

		try {
			// 资金来源HOS_SOURCE
			Map<String, Object> sourceMap = new HashMap<String, Object>();
			List<Source> sourceList = sourceMapper.querySource(sourceMap);
			for (Source source : sourceList) {
				sourceMap.put(source.getSource_name(), source.getSource_code());
				sourceMap.put(source.getSource_code(), source.getSource_code());
			}

			// 资产性质 可通过编码或名称获取编码
			/*
			 * List<AssNaturs> natursList = assNatursMapper.queryAssNaturs(null);
			 * Map<String, String> natursMap = new HashMap<String, String>(); for (AssNaturs
			 * assNaturs : natursList) { natursMap.put(assNaturs.getNaturs_code(),
			 * assNaturs.getNaturs_code()); natursMap.put(assNaturs.getNaturs_name(),
			 * assNaturs.getNaturs_code()); }
			 */

			// 解析EXCEL
			List<Map<String, List<String>>> list = ReadFiles.readFiles(entityMap);
			if (list.size() == 0 || list == null) {
				return "{\"error\":\"表头或者数据为空！请重新导入.\"}";
			}

			for (Map<String, List<String>> map : list) {

				// 校验数据 暂用前台判断

				// 组装数据
				Map<String, Object> data = new HashMap<String, Object>();
				data.put("group_id", SessionManager.getGroupId());
				data.put("hos_id", SessionManager.getHosId());
				data.put("copy_code", SessionManager.getCopyCode());
				data.put("depre_no", map.get("depre_no").get(1));// 折旧序号

				Map<String, Object> cardData = new HashMap<String, Object>();
				cardData.put("group_id", SessionManager.getGroupId());
				cardData.put("hos_id", SessionManager.getHosId());
				cardData.put("copy_code", SessionManager.getCopyCode());
				cardData.put("ass_card_no", map.get("ass_card_no").get(1));
				Map<String, Object> cardMap = assCardBaseMapper.queryAssCardInitByCardNo(cardData);

				if (cardMap == null) {
					errorMsg.append(map.get("ass_card_no").get(0) + ":卡片编码不存在！<br/>");
				}

				// 获取性质编码
				String naturs_code = null;
				if (cardMap != null) {
					naturs_code = cardMap.get("ass_naturs").toString();
				}

				if (naturs_code == null) {
					errorMsg.append(map.get("ass_card_no").get(0) + ":卡片资产未维护资产分类！<br/>");
				}

				// 存在则忽略
				int count = 0;
				if ("01".equals(naturs_code)) {
					count = assDepreManageInitHouseMapper.queryCountByCode(data);
				} else if ("02".equals(naturs_code)) {
					count = assDepreManageInitSpecialMapper.queryCountByCode(data);
				} else if ("03".equals(naturs_code)) {
					count = assDepreManageInitGeneralMapper.queryCountByCode(data);
				} else if ("04".equals(naturs_code)) {
					count = assDepreManageInitOtherMapper.queryCountByCode(data);
				} else if ("05".equals(naturs_code)) {
					count = assDepreManageInitInassetsMapper.queryCountByCode(data);
				} else if ("06".equals(naturs_code)) {
					count = assDepreManageInitLandMapper.queryCountByCode(data);
				}
				if (count > 0) {
					continue;
				}

				// 使用科室
				DeptDict useDeptDict = null;
				if (map.get("use_dept") != null && map.get("use_dept").get(1) != null) {
					// 在用
					Map<String, Object> deptMap = new HashMap<String, Object>();
					deptMap.put("group_id", SessionManager.getGroupId());
					deptMap.put("hos_id", SessionManager.getHosId());
					deptMap.put("copy_code", SessionManager.getCopyCode());
					deptMap.put("dept_code", map.get("use_dept").get(1));
					deptMap.put("dept_name", map.get("use_dept").get(1));
					useDeptDict = deptDictMapper.queryDeptDictByCodeOrName(deptMap);
				}

				if (useDeptDict == null || useDeptDict.getDept_id() == null) {
					errorMsg.append(map.get("use_dept").get(0) + ":使用科室不存在！<br/>");
				}

				// 资金来源
				String source_id = null;
				if (map.get("source_id") != null && map.get("source_id").get(1) != null) {
					source_id = sourceMap.get(map.get("source_id").get(1)).toString();
				}

				if (source_id == null || "".equals(source_id)) {
					errorMsg.append(map.get("source_id").get(0) + ":资金来源不存在！<br/>");
				}

				if (!"".equals(errorMsg.toString())) {
					throw new SysException(errorMsg.toString());
				}

				data.put("depre_year", map.get("depre_year").get(1));// 折旧年度
				data.put("depre_month", map.get("depre_month").get(1));// 折旧期间
				data.put("ass_card_no", map.get("ass_card_no").get(1));// 卡片号
				data.put("source_id", source_id);// 资金来源
				data.put("use_dept_no", useDeptDict.getDept_no());// 使用科室
				data.put("use_dept_id", useDeptDict.getDept_id());
				data.put("use_percent", map.get("use_percent").get(1));// 分摊比例
				data.put("prim_money", map.get("prim_money").get(1));// 原值
				data.put("now_depre_amount", map.get("now_depre_amount").get(1));// 本期折旧
				data.put("add_depre_amount", map.get("add_depre_amount").get(1));// 累计折旧
				data.put("add_depre_month", map.get("add_depre_month").get(1));// 累计折旧月份
				data.put("cur_money", map.get("cur_money").get(1));// 现值
				data.put("fore_money", map.get("fore_money").get(1));// 残值
				data.put("operator", map.get("operator").get(1));// 操作员
				data.put("deal_date", map.get("deal_date").get(1));// 处理日期
				// data.put("equi_depre_code", map.get("equi_depre_code").get(1));
				data.put("equi_depre_code", "01");// 折旧方法
				data.put("note",
						map.get("note") == null || map.get("note").get(1) == null ? "" : map.get("note").get(1));// 摘要

				if ("01".equals(naturs_code)) {
					assDepreManageInitHouseMapper.add(data);
				} else if ("02".equals(naturs_code)) {
					assDepreManageInitSpecialMapper.add(data);
				} else if ("03".equals(naturs_code)) {
					assDepreManageInitGeneralMapper.add(data);
				} else if ("04".equals(naturs_code)) {
					assDepreManageInitOtherMapper.add(data);
				} else if ("05".equals(naturs_code)) {
					assDepreManageInitInassetsMapper.add(data);
				} else if ("06".equals(naturs_code)) {
					assDepreManageInitLandMapper.add(data);
				}

			}

		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new SysException(e.getMessage());
		}
		return "{\"msg\":\"导入成功。\",\"state\":\"true\"}";
	}

	@Override
	public String importAssPayInitData(Map<String, Object> entityMap) throws Exception {

		StringBuffer errorMsg = new StringBuffer();

		try {
			// 付款条件
			MatPayTerm matPayTerm = null;
			if (matPayTermMapper.queryPayTerm(entityMap) != null
					&& matPayTermMapper.queryPayTerm(entityMap).size() > 0) {
				matPayTerm = (MatPayTerm) matPayTermMapper.queryPayTerm(entityMap).get(0);
			} else {
				matPayTerm = new MatPayTerm();
				matPayTerm.setPay_term_id((long) 1);
			}

			// 资产性质 可通过编码或名称获取编码
			List<AssNaturs> natursList = assNatursMapper.queryAssNaturs(entityMap);
			Map<String, String> natursMap = new HashMap<String, String>();
			for (AssNaturs assNaturs : natursList) {
				natursMap.put(assNaturs.getNaturs_code(), assNaturs.getNaturs_code());
				natursMap.put(assNaturs.getNaturs_name(), assNaturs.getNaturs_code());
			}

			// 解析EXCEL
			List<Map<String, List<String>>> list = ReadFiles.readFiles(entityMap);
			if (list.size() == 0 || list == null) {
				return "{\"error\":\"表头或者数据为空！请重新导入.\"}";
			}

			Map<String, Map<String, Object>> maps = new HashMap<String, Map<String, Object>>();

			for (Map<String, List<String>> map : list) {

				// 校验数据 暂用前台判断

				Map<String, Object> cardData = new HashMap<String, Object>();
				cardData.put("group_id", SessionManager.getGroupId());
				cardData.put("hos_id", SessionManager.getHosId());
				cardData.put("copy_code", SessionManager.getCopyCode());
				cardData.put("ass_card_no", map.get("ass_card_no").get(1));
				Map<String, Object> cardMap = assCardBaseMapper.queryAssCardInitByCardNo(cardData);

				if (cardMap == null) {
					errorMsg.append(map.get("ass_card_no").get(0) + ":卡片编码不存在！<br/>");
				}

				// 获取性质编码
				String naturs_code = null;
				if (cardMap != null) {
					naturs_code = cardMap.get("ass_naturs").toString();
				}

				if (naturs_code == null) {
					errorMsg.append(map.get("ass_card_no").get(0) + ":卡片资产未维护资产分类！<br/>");
				}

				AssSupDict assSupDict = null;
				if (map.get("ven_code") != null && map.get("ven_code").get(1) != null) {
					Map<String, Object> supMap = new HashMap<String, Object>();
					supMap.put("group_id", SessionManager.getGroupId());
					supMap.put("hos_id", SessionManager.getHosId());
					supMap.put("copy_code", SessionManager.getCopyCode());
					supMap.put("sup_code", map.get("ven_code").get(1));
					supMap.put("sup_name", map.get("ven_code").get(1));
					assSupDict = assSupDictMapper.queryAssSupDictByCodeOrName(supMap);
				}

				if (assSupDict == null || assSupDict.getSup_id() == null) {
					errorMsg.append(map.get("ven_code").get(0) + ":供应商不存在！<br/>");
				}

				if (cardMap.get("ass_card_no") == null || "".equals(cardMap.get("ass_card_no"))) {
					errorMsg.append(map.get("ass_card_no").get(0) + ":卡片编号不存在！<br/>");
				}

				if (!"".equals(errorMsg.toString())) {
					throw new SysException(errorMsg.toString());
				}

				Map<String, Object> data = new HashMap<String, Object>();
				data.put("group_id", SessionManager.getGroupId());
				data.put("hos_id", SessionManager.getHosId());
				data.put("copy_code", SessionManager.getCopyCode());
				data.put("ass_card_no", cardMap.get("ass_card_no"));
				data.put("stage_no", 1);// 付款期号
				data.put("stage_name", "期初");// 摘要
				data.put("pay_term_id", matPayTerm.getPay_term_id());// 付款条件
				data.put("pay_plan_date", DateUtil.stringToDate(map.get("pay_plan_date").get(1).trim()));// 预计付款时间
				data.put("pay_plan_percent", 1);// 付款比例
				data.put("pay_plan_money", map.get("pay_plan_money").get(1));// 付款金额
				data.put("pay_money", map.get("pay_money").get(1));// 已付金额
				data.put("unpay_money", map.get("unpay_money").get(1));// 未付金额

				Double unpay_money = Double.parseDouble(map.get("unpay_money").get(1));

				Double pay_plan_money = Double.parseDouble(map.get("pay_plan_money").get(1));

				Double pay_money = Double.parseDouble(map.get("pay_money").get(1));
				if (pay_money > pay_plan_money && pay_money - pay_plan_money < 100) {
					data.put("pay_money", pay_plan_money);
					data.put("unpay_money", "0");
				} else if (unpay_money <= 100) {
					if (data.get("pay_money") != null && !"".equals(data.get("pay_money"))
							&& !"0".equals(data.get("pay_money"))) {
						data.put("pay_money", Double.parseDouble(data.get("pay_money").toString()) + unpay_money);
						data.put("unpay_money", "0");
					}
				}

				data.put("ven_id", assSupDict.getSup_id());// 供应商ID
				data.put("ven_no", assSupDict.getSup_no());// 供应商变更ID
				data.put("note",
						map.get("note") == null || map.get("note").get(1) == null ? "" : map.get("note").get(1));// 备注

				if (maps.containsKey(cardMap.get("ass_card_no").toString())) {
					// 如果存在相同的卡片略过
					// 多期付款处理
					continue;
				}

				// 像临时Map对象赋值
				maps.put(cardMap.get("ass_card_no").toString(), data);

				if ("01".equals(naturs_code)) {
					assPayStageInitHouseMapper.add(data);
				} else if ("02".equals(naturs_code)) {
					assPayStageInitSpecialMapper.add(data);
				} else if ("03".equals(naturs_code)) {
					assPayStageInitGeneralMapper.add(data);
				} else if ("04".equals(naturs_code)) {
					assPayStageInitOtherMapper.add(data);
				} else if ("05".equals(naturs_code)) {
					assPayStageInitInassetsMapper.add(data);
				} else if ("06".equals(naturs_code)) {
					assPayStageInitLandMapper.add(data);
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new SysException(e.getMessage());
		}

		return "{\"msg\":\"导入成功。\",\"state\":\"true\"}";

	}


}
