/**
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
package com.chd.hrp.ass.serviceImpl.dict;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.SessionManager;
import com.chd.base.SysPage;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.base.util.ReadFiles;
import com.chd.base.util.StringTool;
import com.chd.hrp.ass.dao.dict.AssAcceptItemAffiMapper;
import com.chd.hrp.ass.dao.dict.AssCheckItemAffiMapper;
import com.chd.hrp.ass.dao.dict.AssDictMapper;
import com.chd.hrp.ass.dao.dict.AssFacDictMapper;
import com.chd.hrp.ass.dao.dict.AssNoDictMapper;
import com.chd.hrp.ass.dao.dict.AssSupDictMapper;
import com.chd.hrp.ass.dao.dict.AssTypeDictMapper;
import com.chd.hrp.ass.dao.dict.AssUsageDictMapper;
import com.chd.hrp.ass.entity.dict.AssDict;
import com.chd.hrp.ass.entity.dict.AssFacDict;
import com.chd.hrp.ass.entity.dict.AssSupDict;
import com.chd.hrp.ass.entity.dict.AssTypeDict;
import com.chd.hrp.ass.entity.dict.AssUsageDict;
import com.chd.hrp.ass.service.dict.AssDictService;
import com.chd.hrp.sys.dao.UnitMapper;
import com.chd.hrp.sys.entity.Unit;
import com.github.pagehelper.PageInfo;

/**
 * @Description: 050102 资产字典
 * @Table: ASS_DICT
 * @Author: bell
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Service("assDictService")
public class AssDictServiceImpl implements AssDictService {
 
	private static Logger logger = Logger.getLogger(AssDictServiceImpl.class);

	// 引入DAO操作
	@Resource(name = "assDictMapper")
	private final AssDictMapper assDictMapper = null;

	// 引入Service服务
	@Resource(name = "assNoDictMapper")
	private final AssNoDictMapper assNoDictMapper = null;

	@Resource(name = "assAcceptItemAffiMapper")
	private final AssAcceptItemAffiMapper assAcceptItemAffiMapper = null;

	@Resource(name = "assCheckItemAffiMapper")
	private final AssCheckItemAffiMapper assCheckItemAffiMapper = null;

	// 引入DAO操作
	@Resource(name = "assTypeDictMapper")
	private final AssTypeDictMapper assTypeDictMapper = null;

	@Resource(name = "assFacDictMapper")
	private final AssFacDictMapper assFacDictMapper = null;

	@Resource(name = "assSupDictMapper")
	private final AssSupDictMapper assSupDictMapper = null;

	@Resource(name = "assUsageDictMapper")
	private final AssUsageDictMapper assUsageDictMapper = null;

	@Resource(name = "unitMapper")
	private final UnitMapper unitMapper = null;

	/**
	 * @Description 添加050102 资产字典<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String addAssDict(Map<String, Object> entityMap) throws DataAccessException {

		// 获取对象050102 资产字典
		AssDict assDict = assDictMapper.queryAssDictByAssCode(entityMap);

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("group_id", entityMap.get("group_id"));
		map.put("hos_id", entityMap.get("hos_id"));
		map.put("copy_code", entityMap.get("copy_code"));
		map.put("ass_type_code", entityMap.get("ass_code"));

		AssTypeDict assTypeDict = assTypeDictMapper.queryAssTypeDictByCode(map);

		if (assTypeDict != null) {
			return "{\"msg\":\"资产编码不能和分类编码一样,请重新添加.\"}";
		}

		if (assDict != null) {
			return "{\"msg\":\"编码重复,请重新添加.\"}";
		}

		try {

			assDictMapper.addAssDict(entityMap);
			Map<String, Object> vo = new HashMap<String, Object>();
			vo.put("group_id", entityMap.get("group_id"));
			vo.put("hos_id", entityMap.get("hos_id"));
			vo.put("copy_code", entityMap.get("copy_code"));
			vo.put("ass_code", entityMap.get("ass_code"));
			AssDict ad = assDictMapper.queryAssDictByUniqueness(vo);
			entityMap.put("ass_no", 0);
			entityMap.put("ass_id", ad.getAss_id());
			assNoDictMapper.addAssNoDict(entityMap);

			Integer is_accept = Integer.parseInt(entityMap.get("is_accept").toString());

			Integer is_check = Integer.parseInt(entityMap.get("is_check").toString());

			if (is_accept == 1) {
				if (!"".equals(entityMap.get("acceptItemData")) && entityMap.get("acceptItemData") != null) {
					List<Map<String, Object>> acceptItemList = new ArrayList<Map<String, Object>>();
					List<Map> acceptItemData = ChdJson.fromJsonAsList(Map.class,
							entityMap.get("acceptItemData").toString());
					for (Map temp : acceptItemData) {
						temp.put("group_id", entityMap.get("group_id"));
						temp.put("hos_id", entityMap.get("hos_id"));
						temp.put("copy_code", entityMap.get("copy_code"));
						temp.put("accept_item_code", temp.get("accept_item_code"));
						temp.put("ass_id", ad.getAss_id());
						acceptItemList.add(temp);
					}
					if (acceptItemList.size() > 0) {
						assAcceptItemAffiMapper.addBatchAssAcceptItemAffi(acceptItemList);
					}
				}
			}

			if (is_check == 1) {
				if (!"".equals(entityMap.get("checkItemData")) && entityMap.get("checkItemData") != null) {
					List<Map<String, Object>> checkItemList = new ArrayList<Map<String, Object>>();
					List<Map> checkItemData = ChdJson.fromJsonAsList(Map.class,
							entityMap.get("checkItemData").toString());
					for (Map temp : checkItemData) {
						temp.put("group_id", entityMap.get("group_id"));
						temp.put("hos_id", entityMap.get("hos_id"));
						temp.put("copy_code", entityMap.get("copy_code"));
						temp.put("check_item_code", temp.get("check_item_code"));
						temp.put("ass_id", ad.getAss_id());
						checkItemList.add(temp);
					}
					if (checkItemList.size() > 0) {
						assCheckItemAffiMapper.addBatchAssCheckItemAffi(checkItemList);
					}
				}
			}

			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}

	}

	/**
	 * @Description 批量添加050102 资产字典<BR>
	 * @param entityList
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String addBatchAssDict(List<Map<String, Object>> entityList) throws DataAccessException {

		try {

			assDictMapper.addBatchAssDict(entityList);

			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}

	}

	/**
	 * @Description 更新050102 资产字典<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String updateAssDict(Map<String, Object> entityMap) throws DataAccessException {
		try {
			assDictMapper.updateAssDict(entityMap);
			if (entityMap.get("history").equals("true")) {

				Map<String, Object> map = new HashMap<String, Object>();
				map.put("group_id", entityMap.get("group_id"));
				map.put("hos_id", entityMap.get("hos_id"));
				map.put("copy_code", entityMap.get("copy_code"));
				map.put("ass_id", entityMap.get("ass_id"));
				map.put("is_stop", "1");
				assNoDictMapper.updateAssNoDictSaveCharge(map);
				int state = assNoDictMapper.addAssNoDict(entityMap);
			} else {
				assDictMapper.updateAssDict(entityMap);
				assNoDictMapper.updateAssNoDictCharge(entityMap);
			}

			Integer is_accept = Integer.parseInt(entityMap.get("is_accept").toString());

			Integer is_check = Integer.parseInt(entityMap.get("is_check").toString());

			if (is_accept == 1) {
				assAcceptItemAffiMapper.deleteAssAcceptItemAffi(entityMap);
				if (!"".equals(entityMap.get("acceptItemData")) && entityMap.get("acceptItemData") != null) {
					List<Map<String, Object>> acceptItemList = new ArrayList<Map<String, Object>>();
					List<Map> acceptItemData = ChdJson.fromJsonAsList(Map.class,
							entityMap.get("acceptItemData").toString());
					for (Map temp : acceptItemData) {
						temp.put("group_id", entityMap.get("group_id"));
						temp.put("hos_id", entityMap.get("hos_id"));
						temp.put("copy_code", entityMap.get("copy_code"));
						temp.put("accept_item_code", temp.get("accept_item_code"));
						temp.put("ass_id", entityMap.get("ass_id"));
						acceptItemList.add(temp);
					}
					if (acceptItemList.size() > 0) {
						assAcceptItemAffiMapper.addBatchAssAcceptItemAffi(acceptItemList);
					}
				}
			}

			if (is_check == 1) {
				assCheckItemAffiMapper.deleteAssCheckItemAffi(entityMap);
				if (!"".equals(entityMap.get("checkItemData")) && entityMap.get("checkItemData") != null) {
					List<Map<String, Object>> checkItemList = new ArrayList<Map<String, Object>>();
					List<Map> checkItemData = ChdJson.fromJsonAsList(Map.class,
							entityMap.get("checkItemData").toString());
					for (Map temp : checkItemData) {
						temp.put("group_id", entityMap.get("group_id"));
						temp.put("hos_id", entityMap.get("hos_id"));
						temp.put("copy_code", entityMap.get("copy_code"));
						temp.put("check_item_code", temp.get("check_item_code"));
						temp.put("ass_id", entityMap.get("ass_id"));
						checkItemList.add(temp);
					}
					if (checkItemList.size() > 0) {
						assCheckItemAffiMapper.addBatchAssCheckItemAffi(checkItemList);
					}
				}
			}
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";
		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}

	}

	/**
	 * @Description 批量更新050102 资产字典<BR>
	 * @param entityList
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String updateBatchAssDict(List<Map<String, Object>> entityList) throws DataAccessException {

		try {

			assDictMapper.updateBatchAssDict(entityList);

			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}

	}

	/**
	 * @Description 删除050102 资产字典<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String deleteAssDict(Map<String, Object> entityMap) throws DataAccessException {
		try {
			assDictMapper.deleteAssDict(entityMap);
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}

	}

	/**
	 * @Description 批量删除050102 资产字典<BR>
	 * @param entityList
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String deleteBatchAssDict(List<Map<String, Object>> entityList) throws DataAccessException {

		try {
			assCheckItemAffiMapper.deleteBatchAssCheckItemAffi(entityList);
			assAcceptItemAffiMapper.deleteBatchAssAcceptItemAffi(entityList);
			assNoDictMapper.deleteBatchAssNoDict(entityList);
			assDictMapper.deleteBatchAssDict(entityList);

			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}
	}

	/**
	 * @Description 查询结果集050102 资产字典<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String queryAssDict(Map<String, Object> entityMap) throws DataAccessException {

		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		if (sysPage.getTotal() == -1) {

			List<AssDict> list = assDictMapper.queryAssDict(entityMap);

			return ChdJson.toJson(list);

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

			List<AssDict> list = assDictMapper.queryAssDict(entityMap, rowBounds);

			PageInfo page = new PageInfo(list);

			return ChdJson.toJson(list, page.getTotal());

		}

	}

	/**
	 * @Description 获取对象050102 资产字典<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public synchronized AssDict queryAssDictByCode(Map<String, Object> entityMap) throws DataAccessException {
		return assDictMapper.queryAssDictByCode(entityMap);
	}

	@Override
	public synchronized AssDict queryAssDictByUniqueness(Map<String, Object> entityMap) throws DataAccessException {
		return assDictMapper.queryAssDictByUniqueness(entityMap);
	}

	/**
	 * 导入
	 */
	@Override
	public String readAssTypeDictFiles(Map<String, Object> entityMap) throws DataAccessException {
		List<Map<String, Object>> batchAddList = new ArrayList<Map<String, Object>>();

		StringBuffer errorMsg = new StringBuffer();

		// 是否
		Map<String, Object> whetherMap = new HashMap<String, Object>();
		whetherMap.put("是", "1");
		whetherMap.put("否", "0");
		whetherMap.put("1", "1");
		whetherMap.put("0", "0");

		// 计量单位HOS_UNIT
		Map<String, Object> unitMap = new HashMap<String, Object>();
		unitMap.put("group_id", SessionManager.getGroupId());
		unitMap.put("hos_id", SessionManager.getHosId());
		List<Unit> unitList = unitMapper.queryUnit(unitMap);
		for (Unit unit : unitList) {
			unitMap.put(unit.getUnit_name(), unit.getUnit_code());
			unitMap.put(unit.getUnit_code(), unit.getUnit_code());
		}

		try {

			List<Map<String, List<String>>> list = ReadFiles.readFiles(entityMap);
			if (list.size() == 0 || list == null) {
				return "{\"error\":\"表头或者数据为空！请重新导入.\"}";
			}

			// 判断数据是否为空
			for (Map<String, List<String>> map : list) {

				AssTypeDict assTypeDict = null;

				if (map.get("ass_code") == null || map.get("ass_code").get(1) == null) {
					errorMsg.append(map.get("ass_code").get(0) + ":资产编码不能为空！<br/>");
				} else if (map.get("ass_name") == null || map.get("ass_code").get(1) == null) {
					errorMsg.append(map.get("ass_name").get(0) + ":资产名称不能为空！<br/>");
				} else if (map.get("ass_type_name") == null || map.get("ass_type_name").get(1) == null) {
					errorMsg.append(map.get("ass_type_name").get(0) + ":资产分类不能为空！<br/>");
				} else {
					// 查询资产分类是否存在
					if (map.get("ass_type_name") != null && map.get("ass_type_name").get(1) != null) {
						Map<String, Object> assTypeDictMap = new HashMap<String, Object>();
						assTypeDictMap.put("group_id", SessionManager.getGroupId());
						assTypeDictMap.put("hos_id", SessionManager.getHosId());
						assTypeDictMap.put("copy_code", SessionManager.getCopyCode());
						assTypeDictMap.put("ass_type_code", map.get("ass_type_name").get(1));
						assTypeDictMap.put("ass_type_name", map.get("ass_type_name").get(1));
						assTypeDict = assTypeDictMapper.queryAssTypeDictByCodeOrName(assTypeDictMap);
					}

					if (assTypeDict == null || assTypeDict.getAss_type_id() == null) {
						errorMsg.append(map.get("ass_type_name").get(0) + ":资产分类不存在！<br/>");
					}
				}
				if (!"".equals(errorMsg.toString())) {
					throw new SysException(errorMsg.toString());
				}

				Map<String, Object> dataMap = new HashMap<String, Object>();

				dataMap.put("group_id", SessionManager.getGroupId());
				dataMap.put("hos_id", SessionManager.getHosId());
				dataMap.put("copy_code", SessionManager.getCopyCode());
				dataMap.put("ass_code", map.get("ass_code").get(1));// 资产编码
				dataMap.put("ass_name", map.get("ass_name").get(1));// 资产名称

				// 查询资产编码或资产名称是否重复
				int data_exc_extis = assDictMapper.queryAssDictByCodeOrName(dataMap);
				if (data_exc_extis > 0) {
					continue;
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

				// 供应商HOS_SUP_DICT
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

				// 用途ASS_USAGE_DICT
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

				dataMap.put("ass_type_id", assTypeDict.getAss_type_id());// 资产分类
				dataMap.put("is_measure",
						map.get("is_measure") == null || whetherMap.get(map.get("is_measure").get(1)) == null ? 0
								: whetherMap.get(map.get("is_measure").get(1)));// 是否计量
				
				dataMap.put("measure_type",
						map.get("measure_type") == null || whetherMap.get(map.get("measure_type").get(1)) == null ? 0
								: whetherMap.get(map.get("measure_type").get(1)));
				
				dataMap.put("is_s_measure",
						map.get("is_s_measure") == null || whetherMap.get(map.get("is_s_measure").get(1)) == null ? 0
								: whetherMap.get(map.get("is_s_measure").get(1)));
				
				dataMap.put("measure_king_code",
						map.get("measure_king_code") == null || whetherMap.get(map.get("measure_king_code").get(1)) == null ? 0
								: whetherMap.get(map.get("measure_king_code").get(1)));
				
				dataMap.put("ass_unit",
						map.get("ass_unit_name").get(1) == null ? ""
								: unitMap.get(map.get("ass_unit_name").get(1)) == null ? ""
										: unitMap.get(map.get("ass_unit_name").get(1)));// 单位

				dataMap.put("is_stop",
						map.get("is_stop").get(1) == null ? "0" : whetherMap.get(map.get("is_stop").get(1)));// 是否停用
				dataMap.put("is_ins",
						map.get("is_ins").get(1) == null ? "0" : whetherMap.get(map.get("is_ins").get(1)));// 是否安装
				dataMap.put("is_accept",
						map.get("is_accept").get(1) == null ? "0" : whetherMap.get(map.get("is_accept").get(1)));// 是否验收
				dataMap.put("is_check",
						map.get("is_check").get(1) == null ? "0" : whetherMap.get(map.get("is_check").get(1)));// 是否巡检
				dataMap.put("is_depre",
						map.get("is_depre").get(1) == null ? "0" : whetherMap.get(map.get("is_depre").get(1)));// 是否折旧
				dataMap.put("ass_depre_code", "01");// 折旧方法
				if (dataMap.get("is_depre") != null && "1".equals(dataMap.get("is_depre"))) {
					dataMap.put("depre_years",
							map.get("depre_years").get(1) == null ? "" : map.get("depre_years").get(1));// 计提年限
				} else {
					dataMap.put("ass_depre_code", "01");// 折旧方法
					dataMap.put("depre_years", "");// 计提年限
				}

				dataMap.put("is_manage_depre", map.get("is_manage_depre").get(1) == null ? "0"
						: whetherMap.get(map.get("is_manage_depre").get(1)));// 是否分摊
				if (dataMap.get("is_manage_depre") != null && "1".equals(dataMap.get("is_manage_depre"))) {
					dataMap.put("manage_depr_method", "01");// 分摊方法
					dataMap.put("manage_depre_amount",
							map.get("manage_depre_amount") == null ? "" : map.get("manage_depre_amount").get(1));// 分摊年限
				} else {
					dataMap.put("manage_depr_method", "");// 分摊方法
					dataMap.put("manage_depre_amount", "");// 分摊年限
				}

				dataMap.put("ass_spec", map.get("ass_spec").get(1) == null ? "" : map.get("ass_spec").get(1));// 规格
				dataMap.put("ass_model", map.get("ass_model").get(1) == null ? "" : map.get("ass_model").get(1));// 型号
				dataMap.put("ass_brand", map.get("ass_brand").get(1) == null ? "" : map.get("ass_brand").get(1));// 品牌
				dataMap.put("gb_code", map.get("gb_code").get(1) == null ? "" : map.get("gb_code").get(1));// 国标码

				dataMap.put("fac_id", hosFacDict == null ? "" : hosFacDict.getFac_id());// 生产厂商
				dataMap.put("fac_no", hosFacDict == null ? "" : hosFacDict.getFac_no());
				dataMap.put("ven_id", assSupDict == null ? "" : assSupDict.getSup_id());// 供应商
				dataMap.put("ven_no", assSupDict == null ? "" : assSupDict.getSup_no());

				dataMap.put("usage_code", assUsageDict == null ? "" : assUsageDict.getEqui_usage_code()); // 用途

				dataMap.put("spell_code", StringTool.toPinyinShouZiMu(dataMap.get("ass_name").toString()));
				dataMap.put("wbx_code", StringTool.toWuBi(dataMap.get("ass_name").toString()));

				dataMap.put("is_bar", "1");
				dataMap.put("bar_type", "2");
				
				dataMap.put("reg_no", "");
				
				dataMap.put("is_fae", "1");

				dataMap.put("note",
						map.get("note") == null || map.get("note").get(1) == null ? "" : map.get("note").get(1));

				assDictMapper.addAssDict(dataMap);
				assNoDictMapper.addAssNoDict(dataMap);
				// batchAddList.add(dataMap);

			}

			//if (batchAddList != null && batchAddList.size() > 0) {
				// assDictMapper.batchImportAssDict(batchAddList);

				// assNoDictMapper.batchImportAssNoDict(batchAddList);
				// assNoDictMapper.addBatchAssNoDict(batchAddList);

			//}
			return "{\"msg\":\"导入成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.debug(e.getMessage());
			throw new SysException(e.getMessage());
		}
	}

	@Override
	public String queryasstypeid(Map<String, Object> mapVo) throws DataAccessException {
		Map<String, Object> asstypeid = assDictMapper.queryasstypeid(mapVo);

		String asscode = asstypeid.get("ASS_CODE").toString();
		Long value = Long.parseLong(asscode) + 1;
		if (asscode.startsWith("0")) {
			asscode = "0" + value.toString();
		} else {
			asscode = value.toString();
		}

		return "{\"state\":\"true\",\"asscode\":\"" + asscode + "\"}";
	}
	

	@Override
	public String copyAssDict(Map<String, Object> mapVo) throws DataAccessException {
		try {
			Map<String, Object> whetherMap = new HashMap<String, Object>();

			AssDict assDict = assDictMapper.queryAssDictByAssId(mapVo);
			
			mapVo.put("ass_type_id", assDict.getAss_type_id());
			Map<String, Object> asstypeid = assDictMapper.queryasstypeid(mapVo);
			String asscode = asstypeid.get("ASS_CODE").toString();
			
			
			Long value = Long.parseLong(asscode) + 1;
			if (asscode.startsWith("0")) {
				asscode = "0" + value.toString();
			} else {
				asscode = value.toString();
			}
			
			whetherMap.put("group_id", SessionManager.getGroupId());
			whetherMap.put("hos_id", SessionManager.getHosId());
			whetherMap.put("copy_code", SessionManager.getCopyCode());
			whetherMap.put("ass_code", asscode);
			whetherMap.put("ass_name", assDict.getAss_name());
			whetherMap.put("ass_type_id", assDict.getAss_type_id());
			whetherMap.put("ass_unit", assDict.getAss_unit());
			whetherMap.put("is_measure", assDict.getIs_measure());
			whetherMap.put("is_depre", assDict.getIs_depre());
			whetherMap.put("ass_depre_code", assDict.getAss_depre_code());
			whetherMap.put("depre_years", assDict.getDepre_years());
			whetherMap.put("is_stop", "0");
			whetherMap.put("ass_spec", assDict.getAss_spec());
			whetherMap.put("ass_model", assDict.getAss_model());
			whetherMap.put("ass_brand", assDict.getAss_brand());
			whetherMap.put("fac_id", assDict.getFac_id());
			whetherMap.put("fac_no", assDict.getFac_no());
			whetherMap.put("ven_id", assDict.getVen_id());
			whetherMap.put("ven_no", assDict.getVen_no());
			whetherMap.put("usage_code", assDict.getUsage_code());
			whetherMap.put("gb_code", assDict.getGb_code());
			whetherMap.put("spell_code", assDict.getSpell_code());
			whetherMap.put("wbx_code", assDict.getWbx_code());
			whetherMap.put("is_ins", assDict.getIs_ins());
			whetherMap.put("is_accept", assDict.getIs_accept());
			whetherMap.put("is_check", assDict.getIs_check());
			whetherMap.put("is_bar", assDict.getIs_bar());
			whetherMap.put("bar_type", assDict.getBar_type());
			whetherMap.put("is_manage_depre", assDict.getIs_manage_depre());
			whetherMap.put("manage_depr_method", assDict.getManage_depr_method());
			whetherMap.put("manage_depre_amount", assDict.getManage_depre_amount());
			whetherMap.put("note", assDict.getNote());
			whetherMap.put("reg_no", assDict.getReg_no());
			whetherMap.put("price", assDict.getPrice());
			whetherMap.put("measure_type", assDict.getMeasure_type() == null?"":assDict.getMeasure_type());
			whetherMap.put("is_s_measure", assDict.getIs_s_measure() == null?"":assDict.getIs_s_measure());
			whetherMap.put("measure_king_code", assDict.getMeasure_king_code() == null?"":assDict.getMeasure_king_code());
			whetherMap.put("is_fae", assDict.getIs_fae());
			whetherMap.put("common_name", assDict.getCommon_name());
			whetherMap.put("type_code", assDict.getType_code());
			
			assDictMapper.addAssDict(whetherMap);
			Map<String, Object> vo = new HashMap<String, Object>();
			vo.put("group_id", whetherMap.get("group_id"));
			vo.put("hos_id", whetherMap.get("hos_id"));
			vo.put("copy_code", whetherMap.get("copy_code"));
			vo.put("ass_code", whetherMap.get("ass_code"));
			AssDict ad = assDictMapper.queryAssDictByUniqueness(vo);
			whetherMap.put("ass_no", 0);
			whetherMap.put("ass_id", ad.getAss_id());
			assNoDictMapper.addAssNoDict(whetherMap);
			
			return "{\"msg\":\"复制成功.\",\"state\":\"true\",\"update_para\":\"" + whetherMap.get("group_id") + "|"
			+ whetherMap.get("hos_id") + "|" + whetherMap.get("copy_code") + "|" + ad.getAss_id() + "\"}";
		} catch (NumberFormatException e) {
			logger.debug(e.getMessage());
			throw new SysException(e.getMessage());
		} catch (Exception e) {
			logger.debug(e.getMessage());
			throw new SysException(e.getMessage());
		}
	}

	@Override
	public List<AssTypeDict> queryNodict(Map<String, Object> mapVo1) {
		// TODO Auto-generated method stub
		return assTypeDictMapper.queryAssTypeDiceChildd(mapVo1);
	}

	@Override
	public String updateBatchDict(List<Map<String, Object>> entityMap) throws DataAccessException {
		try {
			assNoDictMapper.updateBatchAssNoDict(entityMap);
			assDictMapper.updateBatchAssDict(entityMap);

			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}
	}

}
