package com.chd.hrp.ass.serviceImpl.card;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.NoTransactionException;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.multipart.MultipartFile;

import com.chd.base.MyConfig;
import com.chd.base.SessionManager;
import com.chd.base.exception.SysException;
import com.chd.base.ftp.FtpUtil;
import com.chd.base.util.NumberUtil;
import com.chd.base.util.SpreadTableJSUtil;
import com.chd.hrp.ass.dao.accessory.AssAccessoryGeneralMapper;
import com.chd.hrp.ass.dao.accessory.AssAccessoryHouseMapper;
import com.chd.hrp.ass.dao.accessory.AssAccessoryInassetsMapper;
import com.chd.hrp.ass.dao.accessory.AssAccessoryLandMapper;
import com.chd.hrp.ass.dao.accessory.AssAccessoryOtherMapper;
import com.chd.hrp.ass.dao.accessory.AssAccessorySpecialMapper;
import com.chd.hrp.ass.dao.card.AssCardBaseMapper;
import com.chd.hrp.ass.dao.card.AssCardGeneralMapper;
import com.chd.hrp.ass.dao.card.AssCardHouseMapper;
import com.chd.hrp.ass.dao.card.AssCardInassetsMapper;
import com.chd.hrp.ass.dao.card.AssCardLandMapper;
import com.chd.hrp.ass.dao.card.AssCardOtherMapper;
import com.chd.hrp.ass.dao.card.AssCardSpecialMapper;
import com.chd.hrp.ass.dao.dict.AssNatursMapper;
import com.chd.hrp.ass.dao.share.AssShareDeptGeneralMapper;
import com.chd.hrp.ass.dao.share.AssShareDeptHouseMapper;
import com.chd.hrp.ass.dao.share.AssShareDeptInassetsMapper;
import com.chd.hrp.ass.dao.share.AssShareDeptLandMapper;
import com.chd.hrp.ass.dao.share.AssShareDeptOtherMapper;
import com.chd.hrp.ass.dao.share.AssShareDeptRGeneralMapper;
import com.chd.hrp.ass.dao.share.AssShareDeptRHouseMapper;
import com.chd.hrp.ass.dao.share.AssShareDeptRInassetsMapper;
import com.chd.hrp.ass.dao.share.AssShareDeptRLandMapper;
import com.chd.hrp.ass.dao.share.AssShareDeptROtherMapper;
import com.chd.hrp.ass.dao.share.AssShareDeptRSpecialMapper;
import com.chd.hrp.ass.dao.share.AssShareDeptSpecialMapper;
import com.chd.hrp.ass.entity.accessory.AssAccessoryGeneral;
import com.chd.hrp.ass.entity.accessory.AssAccessoryHouse;
import com.chd.hrp.ass.entity.accessory.AssAccessoryInassets;
import com.chd.hrp.ass.entity.accessory.AssAccessoryLand;
import com.chd.hrp.ass.entity.accessory.AssAccessoryOther;
import com.chd.hrp.ass.entity.accessory.AssAccessorySpecial;
import com.chd.hrp.ass.entity.card.AssCardGeneral;
import com.chd.hrp.ass.entity.card.AssCardHouse;
import com.chd.hrp.ass.entity.card.AssCardInassets;
import com.chd.hrp.ass.entity.card.AssCardLand;
import com.chd.hrp.ass.entity.card.AssCardOther;
import com.chd.hrp.ass.entity.card.AssCardSpecial;
import com.chd.hrp.ass.entity.dict.AssNaturs;
import com.chd.hrp.ass.entity.share.AssShareDeptGeneral;
import com.chd.hrp.ass.entity.share.AssShareDeptHouse;
import com.chd.hrp.ass.entity.share.AssShareDeptInassets;
import com.chd.hrp.ass.entity.share.AssShareDeptLand;
import com.chd.hrp.ass.entity.share.AssShareDeptOther;
import com.chd.hrp.ass.entity.share.AssShareDeptRGeneral;
import com.chd.hrp.ass.entity.share.AssShareDeptRHouse;
import com.chd.hrp.ass.entity.share.AssShareDeptRInassets;
import com.chd.hrp.ass.entity.share.AssShareDeptRLand;
import com.chd.hrp.ass.entity.share.AssShareDeptROther;
import com.chd.hrp.ass.entity.share.AssShareDeptRSpecial;
import com.chd.hrp.ass.entity.share.AssShareDeptSpecial;
import com.chd.hrp.ass.service.base.AssBaseService;
import com.chd.hrp.ass.service.card.AssCardBasicService;
import com.chd.hrp.sys.dao.DeptDictMapper;
import com.chd.hrp.sys.entity.DeptDict;

@Service("assCardBasicService")
public class AssCardBasicServiceImpl implements AssCardBasicService {    
 
	@Resource(name = "assBaseService")
	private final AssBaseService assBaseService = null;
	
	@Resource(name = "assCardBaseMapper")
	private final AssCardBaseMapper assCardBaseMapper = null;

	@Resource(name = "deptDictMapper") 
	private final DeptDictMapper deptDictMapper = null;

	@Resource(name = "assNatursMapper")
	private final AssNatursMapper assNatursMapper = null;

	@Resource(name = "assShareDeptGeneralMapper")
	private final AssShareDeptGeneralMapper assShareDeptGeneralMapper = null;

	@Resource(name = "assShareDeptHouseMapper")
	private final AssShareDeptHouseMapper assShareDeptHouseMapper = null;

	@Resource(name = "assShareDeptOtherMapper")
	private final AssShareDeptOtherMapper assShareDeptOtherMapper = null;

	@Resource(name = "assShareDeptSpecialMapper")
	private final AssShareDeptSpecialMapper assShareDeptSpecialMapper = null;

	@Resource(name = "assShareDeptInassetsMapper")
	private final AssShareDeptInassetsMapper assShareDeptInassetsMapper = null;

	@Resource(name = "assShareDeptLandMapper")
	private final AssShareDeptLandMapper assShareDeptLandMapper = null;

	@Resource(name = "assShareDeptRGeneralMapper")
	private final AssShareDeptRGeneralMapper assShareDeptRGeneralMapper = null;

	@Resource(name = "assShareDeptRHouseMapper")
	private final AssShareDeptRHouseMapper assShareDeptRHouseMapper = null;

	@Resource(name = "assShareDeptROtherMapper")
	private final AssShareDeptROtherMapper assShareDeptROtherMapper = null;

	@Resource(name = "assShareDeptRSpecialMapper")
	private final AssShareDeptRSpecialMapper assShareDeptRSpecialMapper = null;

	@Resource(name = "assShareDeptRInassetsMapper")
	private final AssShareDeptRInassetsMapper assShareDeptRInassetsMapper = null;

	@Resource(name = "assShareDeptRLandMapper")
	private final AssShareDeptRLandMapper assShareDeptRLandMapper = null;

	@Resource(name = "assAccessoryGeneralMapper")
	private final AssAccessoryGeneralMapper assAccessoryGeneralMapper = null;

	@Resource(name = "assAccessoryHouseMapper")
	private final AssAccessoryHouseMapper assAccessoryHouseMapper = null;

	@Resource(name = "assAccessoryOtherMapper")
	private final AssAccessoryOtherMapper assAccessoryOtherMapper = null;

	@Resource(name = "assAccessorySpecialMapper")
	private final AssAccessorySpecialMapper assAccessorySpecialMapper = null;

	@Resource(name = "assAccessoryInassetsMapper")
	private final AssAccessoryInassetsMapper assAccessoryInassetsMapper = null;

	@Resource(name = "assAccessoryLandMapper")
	private final AssAccessoryLandMapper assAccessoryLandMapper = null;

	@Resource(name = "assCardGeneralMapper")
	private final AssCardGeneralMapper assCardGeneralMapper = null;

	@Resource(name = "assCardHouseMapper")
	private final AssCardHouseMapper assCardHouseMapper = null;

	@Resource(name = "assCardOtherMapper")
	private final AssCardOtherMapper assCardOtherMapper = null;

	@Resource(name = "assCardSpecialMapper")
	private final AssCardSpecialMapper assCardSpecialMapper = null;

	@Resource(name = "assCardInassetsMapper")
	private final AssCardInassetsMapper assCardInassetsMapper = null;

	@Resource(name = "assCardLandMapper")
	private final AssCardLandMapper assCardLandMapper = null;

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

	public Map<String, Object> queryCardPrint(Map<String, Object> entityMap) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			WebApplicationContext context = ContextLoader.getCurrentWebApplicationContext();
			if (entityMap.get("ass_naturs").equals("01")) {
				AssCardHouseMapper assCardHouseMapper = (AssCardHouseMapper) context.getBean("assCardHouseMapper");
				if ("1".equals(String.valueOf(entityMap.get("p_num")))) {
					entityMap.put("is_list", true);
					List<Map<String, Object>> map = assCardHouseMapper.queryAssCardHousePrint(entityMap);
					result.put("main", map);
				} else {
					entityMap.put("is_list", false);
					Map<String, Object> map = assCardHouseMapper.queryAssCardHousePrint(entityMap).get(0);
					result.put("main", map);
				}

			} else if (entityMap.get("ass_naturs").equals("02")) {

				AssCardSpecialMapper assCardSpecialMapper = (AssCardSpecialMapper) context
						.getBean("assCardSpecialMapper");
				if ("1".equals(String.valueOf(entityMap.get("p_num")))) {
					entityMap.put("is_list", true);
					List<Map<String, Object>> map = assCardSpecialMapper.queryAssCardSpeciaPrint(entityMap);
					result.put("main", map);
				} else {
					entityMap.put("is_list", false);
					Map<String, Object> map = assCardSpecialMapper.queryAssCardSpeciaPrint(entityMap).get(0);
					result.put("main", map);
				}

			} else if (entityMap.get("ass_naturs").equals("03")) {
				AssCardGeneralMapper assCardGeneralMapper = (AssCardGeneralMapper) context
						.getBean("assCardGeneralMapper");
				if ("1".equals(String.valueOf(entityMap.get("p_num")))) {
					entityMap.put("is_list", true);
					List<Map<String, Object>> map = assCardGeneralMapper.queryAssCardGeneralPrint(entityMap);
					result.put("main", map);
				} else {
					entityMap.put("is_list", false);
					Map<String, Object> map = assCardGeneralMapper.queryAssCardGeneralPrint(entityMap).get(0);
					result.put("main", map);
				}

			} else if (entityMap.get("ass_naturs").equals("04")) {
				AssCardOtherMapper assCardOtherMapper = (AssCardOtherMapper) context.getBean("assCardOtherMapper");
				if ("1".equals(String.valueOf(entityMap.get("p_num")))) {
					entityMap.put("is_list", true);
					List<Map<String, Object>> map = assCardOtherMapper.queryAssCardOtherPrint(entityMap);
					result.put("main", map);
				} else {
					entityMap.put("is_list", false);
					Map<String, Object> map = assCardOtherMapper.queryAssCardOtherPrint(entityMap).get(0);
					result.put("main", map);
				}

			} else if (entityMap.get("ass_naturs").equals("05")) {
				AssCardInassetsMapper assCardInassetsMapper = (AssCardInassetsMapper) context
						.getBean("assCardInassetsMapper");
				if ("1".equals(String.valueOf(entityMap.get("p_num")))) {
					entityMap.put("is_list", true);
					List<Map<String, Object>> map = assCardInassetsMapper.queryAssCardInassetsPrint(entityMap);
					result.put("main", map);
				} else {
					entityMap.put("is_list", false);
					Map<String, Object> map = assCardInassetsMapper.queryAssCardInassetsPrint(entityMap).get(0);
					result.put("main", map);
				}

			} else if (entityMap.get("ass_naturs").equals("06")) {
				AssCardLandMapper assCardLandMapper = (AssCardLandMapper) context.getBean("assCardLandMapper");
				if ("1".equals(String.valueOf(entityMap.get("p_num")))) {
					entityMap.put("is_list", true);
					List<Map<String, Object>> map = assCardLandMapper.queryAssCardLandPrint(entityMap);
					result.put("main", map);
				} else {
					entityMap.put("is_list", false);
					Map<String, Object> map = assCardLandMapper.queryAssCardLandPrint(entityMap).get(0);
					result.put("main", map);
				}

			}
		} catch (Exception e) {
			throw new SysException(e.getMessage());
		}
		return result;
	}
	
	
	
	public Map<String, Object> queryCardPrintCode(Map<String, Object> entityMap) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			WebApplicationContext context = ContextLoader.getCurrentWebApplicationContext();
			 if (entityMap.get("ass_naturs").equals("02")) {

				AssCardSpecialMapper assCardSpecialMapper = (AssCardSpecialMapper) context
						.getBean("assCardSpecialMapper");
				if ("1".equals(String.valueOf(entityMap.get("p_num")))) {
					entityMap.put("is_list", true);
					List<Map<String, Object>> map = assCardSpecialMapper.queryAssCardSpeciaPrintCode(entityMap);
					result.put("main", map);
				} else {
					entityMap.put("is_list", false);
					Map<String, Object> map = assCardSpecialMapper.queryAssCardSpeciaPrintCode(entityMap).get(0);
					result.put("main", map);
				}
			} else if (entityMap.get("ass_naturs").equals("03")) {
				AssCardGeneralMapper assCardGeneralMapper = (AssCardGeneralMapper) context
						.getBean("assCardGeneralMapper");
				if ("1".equals(String.valueOf(entityMap.get("p_num")))) {
					entityMap.put("is_list", true);
					List<Map<String, Object>> map = assCardGeneralMapper.queryAssCardGeneralPrintCode(entityMap);
					result.put("main", map);
				} else {
					entityMap.put("is_list", false);
					Map<String, Object> map = assCardGeneralMapper.queryAssCardGeneralPrintCode(entityMap).get(0);
					result.put("main", map);
				}
			} else if (entityMap.get("ass_naturs").equals("04")) {
				AssCardOtherMapper assCardOtherMapper = (AssCardOtherMapper) context.getBean("assCardOtherMapper");
				if ("1".equals(String.valueOf(entityMap.get("p_num")))) {
					entityMap.put("is_list", true);
					List<Map<String, Object>> map = assCardOtherMapper.queryAssCardOtherPrintCode(entityMap);
					result.put("main", map);
				} else {
					entityMap.put("is_list", false);
					Map<String, Object> map = assCardOtherMapper.queryAssCardOtherPrintCode(entityMap).get(0);
					result.put("main", map);
				}
			}
		} catch (Exception e) {
			throw new SysException(e.getMessage());
		}
		return result;
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
			 * if (entityMap.get("ass_naturs").equals("01")) { if
			 * (isAppend.equals("true")) {
			 * assShareDeptSpecialMapper.delete(entityMap); } } else if
			 * (entityMap.get("ass_naturs").equals("02")) { if
			 * (isAppend.equals("true")) {
			 * assShareDeptGeneralMapper.delete(entityMap); } } else if
			 * (entityMap.get("ass_naturs").equals("03")) { if
			 * (isAppend.equals("true")) {
			 * assShareDeptHouseMapper.delete(entityMap); } } else if
			 * (entityMap.get("ass_naturs").equals("04")) { if
			 * (isAppend.equals("true")) {
			 * assShareDeptOtherMapper.delete(entityMap); } } else if
			 * (entityMap.get("ass_naturs").equals("05")) { if
			 * (isAppend.equals("true")) {
			 * assShareDeptInassetsMapper.delete(entityMap); } } else if
			 * (entityMap.get("ass_naturs").equals("06")) { if
			 * (isAppend.equals("true")) {
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

				mapVo.put("ass_year", getAssYear());

				mapVo.put("ass_month", getAssMonth());

				if (entityMap.get("ass_naturs").equals("01")) {

					AssShareDeptHouse assShareDeptHouse = (AssShareDeptHouse) assShareDeptHouseMapper
							.queryByCode(mapVo);
					AssShareDeptRHouse assShareDeptRHouse = (AssShareDeptRHouse) assShareDeptRHouseMapper
							.queryByCode(mapVo);
					if (assShareDeptHouse != null) {
						continue;
					} else {
						assShareDeptHouseMapper.add(mapVo);
					}
					if (assShareDeptRHouse != null) {
						continue;
					} else {
						assShareDeptRHouseMapper.add(mapVo);
					}

				} else if (entityMap.get("ass_naturs").equals("02")) {

					AssShareDeptSpecial assShareDeptSpecial = (AssShareDeptSpecial) assShareDeptSpecialMapper
							.queryByCode(mapVo);
					AssShareDeptRSpecial assShareDeptRSpecial = (AssShareDeptRSpecial) assShareDeptRSpecialMapper
							.queryByCode(mapVo);
					if (assShareDeptSpecial != null) {
						continue;
					} else {
						assShareDeptSpecialMapper.add(mapVo);
					}

					if (assShareDeptRSpecial != null) {
						continue;
					} else {
						assShareDeptRSpecialMapper.add(mapVo);
					}

				} else if (entityMap.get("ass_naturs").equals("03")) {
					AssShareDeptGeneral assShareDeptGeneral = (AssShareDeptGeneral) assShareDeptGeneralMapper
							.queryByCode(mapVo);
					AssShareDeptRGeneral assShareDeptRGeneral = (AssShareDeptRGeneral) assShareDeptRGeneralMapper
							.queryByCode(mapVo);
					if (assShareDeptGeneral != null) {
						continue;
					} else {
						assShareDeptGeneralMapper.add(mapVo);
					}

					if (assShareDeptRGeneral != null) {
						continue;
					} else {
						assShareDeptRGeneralMapper.add(mapVo);
					}

				} else if (entityMap.get("ass_naturs").equals("04")) {

					AssShareDeptOther assShareDeptOther = (AssShareDeptOther) assShareDeptOtherMapper
							.queryByCode(mapVo);
					AssShareDeptROther assShareDeptROther = (AssShareDeptROther) assShareDeptROtherMapper
							.queryByCode(mapVo);
					if (assShareDeptOther != null) {
						continue;
					} else {
						assShareDeptOtherMapper.add(mapVo);
					}

					if (assShareDeptROther != null) {
						continue;
					} else {
						assShareDeptROtherMapper.add(mapVo);
					}
				} else if (entityMap.get("ass_naturs").equals("05")) {

					AssShareDeptInassets assShareDeptInassets = (AssShareDeptInassets) assShareDeptInassetsMapper
							.queryByCode(mapVo);
					AssShareDeptRInassets assShareDeptRInassets = (AssShareDeptRInassets) assShareDeptRInassetsMapper
							.queryByCode(mapVo);
					if (assShareDeptInassets != null) {
						continue;
					} else {
						assShareDeptInassetsMapper.add(mapVo);
					}

					if (assShareDeptRInassets != null) {
						continue;
					} else {
						assShareDeptRInassetsMapper.add(mapVo);
					}
				} else if (entityMap.get("ass_naturs").equals("06")) {

					AssShareDeptLand assShareDeptLand = (AssShareDeptLand) assShareDeptLandMapper.queryByCode(mapVo);
					AssShareDeptRLand assShareDeptRLand = (AssShareDeptRLand) assShareDeptRLandMapper
							.queryByCode(mapVo);
					if (assShareDeptLand != null) {
						continue;
					} else {
						assShareDeptLandMapper.add(mapVo);
					}

					if (assShareDeptRLand != null) {
						continue;
					} else {
						assShareDeptRLandMapper.add(mapVo);
					}
				}
			}
			return "{\"msg\":\"导入成功。\",\"state\":\"true\"}";
		} catch (Exception e) {
			e.printStackTrace();
			throw new SysException(e.getMessage());
		}
	}

	private String getAssYear() {
		String yearMonth = MyConfig.getCurAccYearMonth();
		return yearMonth.substring(0, 4);
	}

	private String getAssMonth() {
		String yearMonth = MyConfig.getCurAccYearMonth();
		return yearMonth.substring(4, 6);
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
	public String UploadPic(Map<String, Object> entityMap, MultipartFile uploadFile, HttpServletRequest request,
			HttpServletResponse response, String filePath ,String fileName) throws Exception {
		try {
			
			if (entityMap.get("ord_file_url") != null && !entityMap.get("ord_file_url").equals("")) {
				String ord_file_url = entityMap.get("ord_file_url").toString();
				String file_name = ord_file_url.substring(ord_file_url.lastIndexOf("/") + 1, ord_file_url.length());
				String path = ord_file_url.substring(0, ord_file_url.lastIndexOf("/"));
				if (!FtpUtil.deleteFileTwo(path, file_name)) {
					TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
					return "error";
				}
			}
			
			if (uploadFile != null) {
				if (!FtpUtil.uploadFile(uploadFile, "", filePath,fileName, request, response)) {
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
				String file_name = map.get("file_url").toString().substring(map.get("file_url").toString().lastIndexOf("/") + 1, map.get("file_url").toString().length());
				String path = map.get("file_url").toString().substring(0,map.get("file_url").toString().lastIndexOf("/"));
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
			 * if (entityMap.get("ass_naturs").equals("01")) { if
			 * (isAppend.equals("true")) {
			 * assAccessorySpecialMapper.delete(entityMap); } } else if
			 * (entityMap.get("ass_naturs").equals("02")) { if
			 * (isAppend.equals("true")) {
			 * assAccessoryGeneralMapper.delete(entityMap); } } else if
			 * (entityMap.get("ass_naturs").equals("03")) { if
			 * (isAppend.equals("true")) {
			 * assAccessoryHouseMapper.delete(entityMap); } } else if
			 * (entityMap.get("ass_naturs").equals("04")) { if
			 * (isAppend.equals("true")) {
			 * assAccessoryOtherMapper.delete(entityMap); } } else if
			 * (entityMap.get("ass_naturs").equals("05")) { if
			 * (isAppend.equals("true")) {
			 * assAccessoryInassetsMapper.delete(entityMap); } } else if
			 * (entityMap.get("ass_naturs").equals("06")) { if
			 * (isAppend.equals("true")) {
			 * assAccessoryLandMapper.delete(entityMap); } }
			 */

			for (Map<String, List<String>> mapData : liData) {

				Map<String, Object> mapVo = new HashMap<String, Object>();
				mapVo.put("group_id", entityMap.get("group_id"));
				mapVo.put("hos_id", entityMap.get("hos_id"));
				mapVo.put("copy_code", entityMap.get("copy_code"));
				mapVo.put("ass_card_no", entityMap.get("ass_card_no"));

				mapVo.put("accessory_code", mapData.get("附件编码").get(1));
				mapVo.put("accessory_name", mapData.get("附件名称").get(1));

				if (mapData.get("资产性质编码") != null && !mapData.get("资产性质编码").equals("")) {
					String naturs_code = mapData.get("资产性质编码").get(1);
					if (naturs_code != null) {
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
									AssCardSpecial assCardSpecial = assCardSpecialMapper.queryByCode(vdataMap);
									if (assCardSpecial != null) {
										mapVo.put("accessory_card_no", assCardSpecial.getAss_card_no());
									} else {
										return "{\"msg\":\"" + mapData.get("附卡").get(0)
												+ "，卡片编码不存在不存在！\",\"state\":\"false\",\"row_cell\":\""
												+ mapData.get("附卡").get(0) + "\"}";
									}
								} else if (assNaturs.getNaturs_code().equals("02")) {
									AssCardGeneral assCardGeneral = assCardGeneralMapper.queryByCode(vdataMap);
									if (assCardGeneral != null) {
										mapVo.put("accessory_card_no", assCardGeneral.getAss_card_no());
									} else {
										return "{\"msg\":\"" + mapData.get("附卡").get(0)
												+ "，卡片编码不存在不存在！\",\"state\":\"false\",\"row_cell\":\""
												+ mapData.get("附卡").get(0) + "\"}";
									}
								} else if (assNaturs.getNaturs_code().equals("03")) {
									AssCardHouse assCardHouse = assCardHouseMapper.queryByCode(vdataMap);
									if (assCardHouse != null) {
										mapVo.put("accessory_card_no", assCardHouse.getAss_card_no());
									} else {
										return "{\"msg\":\"" + mapData.get("附卡").get(0)
												+ "，卡片编码不存在不存在！\",\"state\":\"false\",\"row_cell\":\""
												+ mapData.get("附卡").get(0) + "\"}";
									}
								} else if (assNaturs.getNaturs_code().equals("04")) {
									AssCardOther assCardOther = assCardOtherMapper.queryByCode(vdataMap);
									if (assCardOther != null) {
										mapVo.put("accessory_card_no", assCardOther.getAss_card_no());
									} else {
										return "{\"msg\":\"" + mapData.get("附卡").get(0)
												+ "，卡片编码不存在不存在！\",\"state\":\"false\",\"row_cell\":\""
												+ mapData.get("附卡").get(0) + "\"}";
									}
								} else if (assNaturs.getNaturs_code().equals("05")) {
									AssCardInassets assCardInassets = assCardInassetsMapper.queryByCode(vdataMap);
									if (assCardInassets != null) {
										mapVo.put("accessory_card_no", assCardInassets.getAss_card_no());
									} else {
										return "{\"msg\":\"" + mapData.get("附卡").get(0)
												+ "，卡片编码不存在不存在！\",\"state\":\"false\",\"row_cell\":\""
												+ mapData.get("附卡").get(0) + "\"}";
									}
								} else if (assNaturs.getNaturs_code().equals("06")) {
									AssCardLand assCardLand = assCardLandMapper.queryByCode(vdataMap);
									if (assCardLand != null) {
										mapVo.put("accessory_card_no", assCardLand.getAss_card_no());
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

					AssAccessoryHouse assAccessoryHouse = (AssAccessoryHouse) assAccessoryHouseMapper
							.queryByCode(mapVo);
					if (assAccessoryHouse != null) {
						continue;
					} else {
						assAccessoryHouseMapper.add(mapVo);
					}

				} else if (entityMap.get("ass_naturs").equals("02")) {

					AssAccessorySpecial assAccessorySpecial = (AssAccessorySpecial) assAccessorySpecialMapper
							.queryByCode(mapVo);
					if (assAccessorySpecial != null) {
						continue;
					} else {
						assAccessorySpecialMapper.add(mapVo);
					}

				} else if (entityMap.get("ass_naturs").equals("03")) {
					AssAccessoryGeneral assAccessoryGeneral = (AssAccessoryGeneral) assAccessoryGeneralMapper
							.queryByCode(mapVo);
					if (assAccessoryGeneral != null) {
						continue;
					} else {
						assAccessoryGeneralMapper.add(mapVo);
					}

				} else if (entityMap.get("ass_naturs").equals("04")) {

					AssAccessoryOther assAccessoryOther = (AssAccessoryOther) assAccessoryOtherMapper
							.queryByCode(mapVo);
					if (assAccessoryOther != null) {
						continue;
					} else {
						assAccessoryOtherMapper.add(mapVo);
					}
				} else if (entityMap.get("ass_naturs").equals("05")) {

					AssAccessoryInassets assAccessoryInassets = (AssAccessoryInassets) assAccessoryInassetsMapper
							.queryByCode(mapVo);
					if (assAccessoryInassets != null) {
						continue;
					} else {
						assAccessoryInassetsMapper.add(mapVo);
					}
				} else if (entityMap.get("ass_naturs").equals("06")) {

					AssAccessoryLand assAccessoryLand = (AssAccessoryLand) assAccessoryLandMapper.queryByCode(mapVo);
					if (assAccessoryLand != null) {
						continue;
					} else {
						assAccessoryLandMapper.add(mapVo);
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
	public String resetBarCode(Map<String, Object> entityMap) throws Exception {
		String basePath = "ass";
		String group_id = entityMap.get("group_id").toString();
		String hos_id = entityMap.get("hos_id").toString();
		String copy_code = entityMap.get("copy_code").toString();
		String assTwoPath = "assbartwo";
		
		//增加查询条件,解决条码为为空报空指针的问题
		entityMap.put("barcode_reset", "1");
		 
		
		List<Map<String, Object>> list = assCardBaseMapper.queryAssCardView(entityMap);
		
		for(Map<String, Object> map : list){
			String twoFilePath = group_id + "/" + hos_id + "/" + copy_code + "/" + basePath + "/" + assTwoPath + "/"+map.get("ass_naturs").toString()+"/";// 资产性质为01
			FtpUtil.getQRWriteFile(map.get("bar_code").toString(), "", twoFilePath,
					map.get("bar_code").toString() + ".png");// 二维码
		} 
		
		return "{\"msg\":\"重置并将所有条码重新生成成功！\",\"state\":\"true\"}";
	}

}
