package com.chd.hrp.hr.serviceImpl.sysstruc;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.chd.base.SessionManager;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.base.util.ReadFiles;
import com.chd.hrp.hr.dao.base.BaseCRUDMapper;
import com.chd.hrp.hr.dao.base.HrSelectMapper;
import com.chd.hrp.hr.dao.sc.HrTableStrucMapper;
import com.chd.hrp.hr.dao.sysstruc.HrFiiedTabStrucMapper;
import com.chd.hrp.hr.dao.sysstruc.HrFiiedViewMapper;
import com.chd.hrp.hr.dao.sysstruc.HrStoreConditionMapper;
import com.chd.hrp.hr.dao.sysstruc.HrStoreTabStrucMapper;
import com.chd.hrp.hr.dao.sysstruc.HrStoreTypeMapper;
import com.chd.hrp.hr.entity.sc.HrTableColumn;
import com.chd.hrp.hr.entity.sc.HrTableStruc;
import com.chd.hrp.hr.entity.sysstruc.HrStoreCondition;
import com.chd.hrp.hr.entity.sysstruc.HrStoreTabStruc;
import com.chd.hrp.hr.entity.sysstruc.HrStoreType;
import com.chd.hrp.hr.service.sysstruc.HrStoreConditionService;

/**
 * 档案库人员限定配置
 * 
 * @author Administrator
 *
 */
@Service("hrStoreConditionService")
public class HrStoreConditionServiceImpl implements HrStoreConditionService {

	private static Logger logger = Logger
			.getLogger(HrStoreConditionServiceImpl.class);
	// 引入DAO操作

	@Resource(name = "hrStoreConditionMapper")
	private final HrStoreConditionMapper hrStoreConditionMapper = null;

	@Resource(name = "hrStoreTypeMapper")
	private final HrStoreTypeMapper hrStoreTypeMapper = null;

	@Resource(name = "hrStoreTabStrucMapper")
	private final HrStoreTabStrucMapper hrStoreTabStrucMapper = null;

	@Resource(name = "hrFiiedTabStrucMapper")
	private final HrFiiedTabStrucMapper hrFiiedTabStrucMapper = null;

	@Resource(name = "hrFiiedViewMapper")
	private final HrFiiedViewMapper hrFiiedViewMapper = null;

	@Resource(name = "baseCRUDMapper")
	private final BaseCRUDMapper baseCRUDMapper = null;

	@Resource(name = "hrSelectMapper")
	private final HrSelectMapper hrSelectMapper = null;
	
	// 引入DAO操作
	@Resource(name = "hrTableStrucMapper")
	private final HrTableStrucMapper hrTableStrucMapper = null;


	// 默认显示20条数据
	RowBounds rowBoundsALL = new RowBounds(0, 20);

	/**
	 * 新增 修改
	 */
	@Override
	public String addStoreConditionPage(Map<String, Object> entityMap)
			throws DataAccessException {
		try {

			List<Map> AddedlistVo = JSONArray.parseArray(
					String.valueOf(entityMap.get("Added")), Map.class);
			if (AddedlistVo.size() > 0 && AddedlistVo != null) {
				Map<String, String> tabCodeMap = new HashMap<String, String>();
				// 查询条件
				List<Map<String, Object>> signList = hrStoreConditionMapper
						.queryHrHosConSignName(entityMap);
				// 查询连接符
				List<Map<String, Object>> joinSingList = hrStoreConditionMapper
						.queryHrHosJoinSignName(entityMap);

				// 取前台数据 共计使用多少个数据表
				for (Map map : AddedlistVo) {

					tabCodeMap.put(map.get("tab_code").toString(),
							map.get("tab_code").toString());
					map.put("group_id", SessionManager.getGroupId());

					map.put("hos_id", SessionManager.getHosId());

					map.put("copy_code", SessionManager.getCopyCode());
					/* map.put("line_no", map.get("row")); */
					map.put("store_type_code", entityMap.get("store_type_code"));
					// if (map.get("field_col_code") != null
					// &&
					// StringUtils.isNotBlank(map.get("field_col_code").toString()))
					// {
					// map.put("col_value", map.get("field_col_code"));
					// } else {
					map.put("col_value", map.get("field_col_name"));
					// }
					if (map.get("join_sign_note") != null
							&& !map.get("join_sign_note").equals("")) {
						for (Map<String, Object> map1 : joinSingList) {
							if (map.get("join_sign_note").toString()
									.equals(map1.get("join_sign_note"))) {
								map.put("join_sign_code",
										map1.get("JOIN_SIGN_CODE").toString());
							}
						}
					} else {
						map.put("join_sign_code", "");
					}
				}

				// 根据tab_code 取表配置
				StringBuffer sql = new StringBuffer("t.tab_code in (");

				for (String key : tabCodeMap.keySet()) {

					sql.append("'" + key + "',");

				}
				sql.deleteCharAt(sql.length() - 1);

				sql.append(")");

				entityMap.put("sql", sql.toString());

				List<Map<String, Object>> hrColStrucList = hrStoreConditionMapper
						.queryHrColStrucEntity(entityMap);

				// 将表配置以 tab_code col_code为键 存储
				Map<String, Map<String, Object>> hrColStrucMap = new LinkedHashMap<String, Map<String, Object>>();

				for (Map<String, Object> map : hrColStrucList) {

					hrColStrucMap.put(map.get("TAB_CODE")
							+ map.get("COL_CODE").toString(), map);

				}

				// 再次循环前台传入数据
				StringBuffer validateSql = new StringBuffer(
						"select count(0) from ");
				for (String key : tabCodeMap.keySet()) {

					validateSql.append("" + key + " " + key + ",");

				}
				validateSql.deleteCharAt(validateSql.length() - 1);

				validateSql.append(" where ");
				int line_no = 0;
				for (Map<String, Object> map : AddedlistVo) {

					String str = map.get("tab_code")
							+ map.get("col_code").toString();

					if (hrColStrucMap.get(str) != null) {
						String conSignCode = null;
						String joinSingCode = null;
						String fileCode = null;

						Map<String, Object> tmpHrColStrucMap = hrColStrucMap
								.get(str);
						if (map.get("l_bracket") != null) {
							validateSql.append(map.get("l_bracket"));
						}

						for (Map<String, Object> map1 : signList) {
							if (map.get("con_sign_code").toString()
									.equals(map1.get("CON_SIGN_CODE"))) {
								conSignCode = map1.get("CON_SIGN_NAME")
										.toString();
							}
						}

						if (map.get("field_col_code") != ""
								&& map.get("field_col_code") != null) {

							fileCode = map.get("field_col_code").toString();

						} else {
							fileCode = map.get("field_col_name").toString();
						}
						if (tmpHrColStrucMap.get("IS_CHANGE") != null
								&& tmpHrColStrucMap.get("IS_CHANGE").toString()
										.equals("1")) {
							validateSql.append(map.get("tab_code")
									+ "."
									+ map.get("col_code").toString()
									+ "||'@'||"
									+ tmpHrColStrucMap.get("CHANGE_COL_CODE")
											.toString()
									+ conSignCode.toString() + "'" + fileCode
									+ "'");
						} else if (tmpHrColStrucMap.get("DATA_TYPE") != null
								&& tmpHrColStrucMap.get("DATA_TYPE").toString()
										.equals("DATE")) {
							validateSql.append(map.get("tab_code") + "."
									+ map.get("col_code").toString() + " "
									+ conSignCode + " to_date ( '"
									+ map.get("field_col_name")
									+ "','yyyy/mm/dd')");
						} else if (tmpHrColStrucMap.get("DATA_TYPE") != null
								&& tmpHrColStrucMap.get("DATA_TYPE").toString()
										.equals("NUMBER")) {
							validateSql.append(map.get("tab_code") + "."
									+ map.get("col_code").toString() + " "
									+ conSignCode + " " + fileCode);
						} else if (tmpHrColStrucMap.get("DATA_TYPE") != null
								&& tmpHrColStrucMap.get("DATA_TYPE").toString()
										.equals("VARCHAR2")) {
							validateSql.append(map.get("tab_code") + "."
									+ map.get("col_code").toString() + " "
									+ conSignCode + " '" + fileCode + "'");
						} else {
							validateSql.append(map.get("tab_code") + "."
									+ map.get("col_code").toString() + " "
									+ conSignCode + " '" + fileCode + "'");

						}

						if (map.get("r_bracket") != null) {
							validateSql.append(map.get("r_bracket"));
						}

						if (map.get("join_sign_note") != null
								&& !map.get("join_sign_note").equals("")) {

							for (Map<String, Object> map1 : joinSingList) {
								if (map.get("join_sign_code").toString()
										.equals(map1.get("JOIN_SIGN_CODE"))) {
									joinSingCode = map1.get("JOIN_SIGN_NAME")
											.toString();
								}
							}
							validateSql.append(" " + joinSingCode + " ");
						}
						map.put("col_value", fileCode);
						if (map.get("line_no") != null) {
							line_no = Integer.parseInt(map.get("line_no")
									.toString());
						} else {
							map.put("line_no", line_no + 1);
							line_no = line_no + 1;
						}

					}

				}
				try {
					hrStoreConditionMapper.queryCount(validateSql);
//					if (count == 0) {
//						return "{\"error\":\""
//								+ "选择的限定配置查询不到数据.\",\"state\":\"false\"}";
//					}
				} catch (Exception e) {
					logger.error(e.getMessage(), e);
					throw new SysException("条件语句错误、请检查");
				}
				if (AddedlistVo != null && AddedlistVo.size() > 0) {
					// 先删除
					hrStoreConditionMapper.deleteBatchStore(AddedlistVo);
					// 增加
					hrStoreConditionMapper.addStoreCondition(AddedlistVo);
				}
			}
			return "{\"msg\":\"保存成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}
	}

	/**
	 * 查询数据
	 */
	@Override
	public String queryStoreCondition(Map<String, Object> entityMap)
			throws DataAccessException {
		// SysPage sysPage = new SysPage();
		//
		// sysPage = (SysPage) entityMap.get("sysPage");
		//
		// if (sysPage.getTotal() == -1) {

		List<HrStoreCondition> list = (List<HrStoreCondition>) hrStoreConditionMapper
				.query(entityMap);

		for (int i = 0; i < list.size(); i++) {
			String colNameSql = list.get(i).getField_col_name();
			if (colNameSql == null) {
				continue;
			}
			if (colNameSql.contains("select")) {
				colNameSql = colNameSql.replace("@group_id", "'"
						+ SessionManager.getGroupId() + "'");
				colNameSql = colNameSql.replace("@hos_id",
						"'" + SessionManager.getHosId() + "'");
				colNameSql = "  select field_col_name from (  " + colNameSql
						+ "  ) where field_col_code = '"
						+ list.get(i).getCol_value() + "'";
				list.get(i).setField_col_name(
						hrStoreConditionMapper.queryColNam(colNameSql));
			}
		}

		return ChdJson.toJson(list);

		// } else {
		//
		// RowBounds rowBounds = new RowBounds(sysPage.getPage(),
		// sysPage.getPagesize());
		//
		// List<HrStoreCondition> list = (List<HrStoreCondition>)
		// hrStoreConditionMapper.query(entityMap, rowBounds);
		//
		// PageInfo page = new PageInfo(list);
		//
		// return ChdJson.toJson(list, page.getTotal());
		//
		// }
	}

	/**
	 * 删除
	 */
	@Override
	public String deleteBatch(List<HrStoreCondition> entityList)
			throws DataAccessException {

		try {
			for (HrStoreCondition hrStoreCondition : entityList) {
				hrStoreCondition.setGroup_id(Double.valueOf(SessionManager
						.getGroupId().toString()));
				hrStoreCondition.setHos_id(Double.valueOf(SessionManager
						.getHosId().toString()));
			}
			hrStoreConditionMapper.deleteBatchStoreCondition(entityList);

			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}
	}

	/**
	 * 左侧列表
	 */
	@Override
	public String queryStoreConditionTree(Map<String, Object> entityMap)
			throws DataAccessException {

		StringBuilder treeJson = new StringBuilder();

		treeJson.append("[");
		List<HrStoreType> storeTypeList = (List<HrStoreType>) hrStoreTypeMapper
				.query(entityMap);
		for (HrStoreType storeType : storeTypeList) {
			treeJson.append("{'id':'" + storeType.getStore_type_code()
					+ "', 'pId':'0', 'name':'" + storeType.getStore_type_name()
					+ "'},");

		}
		treeJson.append("]");
		return treeJson.toString();
	}

	/**
	 * 查询系统结构表
	 */
	@Override
	public String queryHrHosConditionTabStruc(Map<String, Object> entityMap)
			throws DataAccessException {
		List<Map<String, Object>> list = hrStoreConditionMapper
				.queryHrHosTabStruc(entityMap);
		return JSONArray.toJSONString(list);
	}

	/**
	 * 查询系统结构列名
	 */

	@Override
	public String queryHrHosColStruc(Map<String, Object> entityMap)
			throws DataAccessException {
		List<Map<String, Object>> list = hrStoreConditionMapper
				.queryHrHosColStruc(entityMap);
		return JSONArray.toJSONString(list);
	}

	/**
	 * 条件符号查询
	 */
	@Override
	public String queryHrHosConSign(Map<String, Object> entityMap)
			throws DataAccessException {
		List<Map<String, Object>> list = hrStoreConditionMapper
				.queryHrHosConSign(entityMap);
		return JSONArray.toJSONString(list);
	}

	/**
	 * 连接符号查询
	 */
	@Override
	public String queryHrHosJoinSign(Map<String, Object> entityMap)
			throws DataAccessException {
		List<Map<String, Object>> list = hrStoreConditionMapper
				.queryHrHosJoinSign(entityMap);
		return JSONArray.toJSONString(list);
	}

	/**
	 * 打印
	 */
	@Override
	public List<Map<String, Object>> queryStoreConditionByPrint(
			Map<String, Object> entityMap) throws DataAccessException {
		List<Map<String, Object>> list = ChdJson
				.toListLower(hrStoreConditionMapper
						.queryStoreConditionByPrint(entityMap));

		return list;
	}

	/**
	 * 导入
	 */
	@Override
	public String importExcelCondition(Map<String, Object> entityMap)
			throws DataAccessException {
		// 判断数据表名的map
		Map<String, Object> typeMap = new HashMap<String, Object>();
		List<Map<String, Object>> typeList = ChdJson
				.toListLower(hrStoreTabStrucMapper
						.queryHrHosTabStruc(entityMap));
		for (Map<String, Object> map : typeList) {
			typeMap.put(map.get("tab_code").toString(), map.get("tab_code"));
			typeMap.put(map.get("tab_name").toString(), map.get("tab_code"));
		}
		// 判断数据表列的map
		Map<String, Object> colMap = new HashMap<String, Object>();

		// 判断条件的map
		Map<String, Object> signMap = new HashMap<String, Object>();
		List<Map<String, Object>> signList = hrStoreConditionMapper
				.queryHrHosConSignName(entityMap);
		for (Map<String, Object> sMap : signList) {
			signMap.put(sMap.get("con_sign_code").toString(),
					sMap.get("con_sign_code").toString());
			signMap.put(sMap.get("con_sign_name").toString(),
					sMap.get("con_sign_code").toString());
			signMap.put(sMap.get("con_sign_note").toString(),
					sMap.get("con_sign_code").toString());
		}

		// 判断数据项值的map
		Map<String, Object> colValueMap = new HashMap<String, Object>();

		// 判断连接符值的map
		Map<String, Object> joinMap = new HashMap<String, Object>();
		List<Map<String, Object>> joinList = hrStoreConditionMapper
				.queryHrHosJoinSignName(entityMap);
		for (Map<String, Object> jMap : joinList) {
			joinMap.put(jMap.get("join_sign_code").toString(),
					jMap.get("join_sign_code").toString());
			joinMap.put(jMap.get("join_sign_name").toString(),
					jMap.get("join_sign_code").toString());
			joinMap.put(jMap.get("join_sign_note").toString(),
					jMap.get("join_sign_code").toString());
		}

		int successNum = 0;
		int countNum = 1;
		boolean flag = true;
		StringBuilder failureMsg = new StringBuilder();
		List<HrStoreTabStruc> saveList = new ArrayList<HrStoreTabStruc>();

		try {
			List<Map<String, List<String>>> list = ReadFiles
					.readFiles(entityMap);
			if (list != null && list.size() > 0) {
				for (Map<String, List<String>> map : list) {
					/*
					 * HrStoreTabStruc saveMap = new HrStoreTabStruc();
					 * Map<String,Object> sMap = new HashMap<String,Object>();
					 * sMap.put("group_id",
					 * SessionManager.getGroupId().toString());
					 * sMap.put("hos_id", SessionManager.getHosId().toString());
					 * sMap.put("copy_code", SessionManager.getCopyCode());
					 * sMap.put("store_type_code",
					 * entityMap.get("store_type_code").toString());
					 * 
					 * saveMap.setGroup_id(Double.parseDouble(SessionManager.
					 * getGroupId().toString()));
					 * saveMap.setHos_id(Double.parseDouble
					 * (SessionManager.getHosId().toString()));
					 * saveMap.setCopy_code(SessionManager.getCopyCode());
					 * saveMap
					 * .setStore_type_code(entityMap.get("store_type_code"
					 * ).toString());
					 * failureMsg.append("<br/> 第"+countNum+"行 ");
					 * 
					 * //数据列字段 if(map.get("tab_code").get(1)!=null &&
					 * map.get("tab_code").get(1)!=""){ String
					 * tab_code=map.get("tab_code").get(1).toUpperCase();
					 * if(typeMap.get(tab_code).toString()!=null){
					 * saveMap.setTab_code(typeMap.get(tab_code).toString());
					 * sMap.put("tab_code", typeMap.get(tab_code).toString());
					 * HrStoreTabStruc hrTab =
					 * hrStoreTabStrucMapper.queryByCodeHrColStruc(sMap);
					 * if(hrTab!=null){ failureMsg.append(" 数据表编码已存在！ ");
					 * flag=false; countNum++; continue; } }else{
					 * failureMsg.append(" 数据表编码不存在！ "); flag=false; countNum++;
					 * continue; } }else{ failureMsg.append(" 数据表编码不能为空！");
					 * flag=false; }
					 * 
					 * successNum++; countNum++; saveList.add(saveMap);
					 */
				}
			}

			if (flag == false) {
				return "{\"error\":\"导入失败！  " + failureMsg + "\"}";
			} else {
				if (saveList.size() > 0) {
					hrStoreTabStrucMapper.addBatchHrColStruc(saveList);
				}
				return "{\"msg\":\"已成功导入 " + successNum
						+ "条\",\"state\":\"true\"}";
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			return "{\"error\":\"导入失败！\"}";
		}
	}

	@Override
	public String queryHrTabColStruc(Map<String, Object> entityMap)
			throws DataAccessException {
		List<Map<String, Object>> list=new ArrayList<Map<String,Object>>();
		HrTableStruc hrTableStruc = hrTableStrucMapper.queryTabColsByCode(entityMap);
		if (hrTableStruc != null) {
			List<HrTableColumn> data = hrTableStruc.getTableColumn();
			Collections.sort(data);
			
			for (HrTableColumn hrTableColumn : data) {
				
				Map<String, Object> map=new HashMap<String, Object>();
				map.put("id", hrTableColumn.getCol_code());
				map.put("text",  hrTableColumn.getCol_code()+" "+hrTableColumn.getCol_name());
				map.put("type", hrTableColumn.getData_type_name());
				list.add(map);
			}
			
		}
		
		
		
		return JSONArray.toJSONString(list);

	}

}
