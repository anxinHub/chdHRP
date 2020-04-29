package com.chd.hrp.hr.serviceImpl.sysstruc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.chd.base.SessionManager;
import com.chd.base.SysPage;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.base.util.ReadFiles;
import com.chd.hrp.hr.dao.sysstruc.HrStoreTabStrucMapper;
import com.chd.hrp.hr.dao.sysstruc.HrStoreTypeMapper;
import com.chd.hrp.hr.dao.sysstruc.HrTabStrucMapper;
import com.chd.hrp.hr.entity.sysstruc.HrColStruc;
import com.chd.hrp.hr.entity.sysstruc.HrStoreTabStruc;
import com.chd.hrp.hr.entity.sysstruc.HrStoreType;
import com.chd.hrp.hr.entity.sysstruc.HrTabStruc;
import com.chd.hrp.hr.service.sysstruc.HrStoreTabStrucService;
import com.github.pagehelper.PageInfo;

/**
 * 档案库数据集配置
 * 
 * @author Administrator
 *
 */
@Service("hrStoreTabStrucService")
public class HrStoreTabStrucServiceImpl implements HrStoreTabStrucService {
	private static Logger logger = Logger.getLogger(HrStoreTabStrucServiceImpl.class);

	@Resource(name = "hrStoreTabStrucMapper")
	private final HrStoreTabStrucMapper hrStoreTabStrucMapper = null;

	// 引入DAO操作
	@Resource(name = "hrTabStrucMapper")
	private final HrTabStrucMapper hrTabStrucMapper = null;

	@Resource(name = "hrStoreTypeMapper")
	private final HrStoreTypeMapper hrStoreTypeMapper = null;

	// 默认显示20条数据
	RowBounds rowBoundsALL = new RowBounds(0, 20);

	/**
	 * 查询所有数据
	 */
	@Override
	public String query(Map<String, Object> entityMap) throws DataAccessException {

		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		if (sysPage.getTotal() == -1) {

			List<HrStoreTabStruc> list = (List<HrStoreTabStruc>) hrStoreTabStrucMapper.query(entityMap);

			return ChdJson.toJson(list);

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

			List<HrStoreTabStruc> list = (List<HrStoreTabStruc>) hrStoreTabStrucMapper.query(entityMap, rowBounds);

			PageInfo page = new PageInfo(list);

			return ChdJson.toJson(list, page.getTotal());

		}

	}

	/**
	 * 删除
	 */
	@Override
	public String deleteBatch(List<HrStoreTabStruc> entityList) throws DataAccessException {

		try {

			hrStoreTabStrucMapper.deleteBatchHrColStruc(entityList);

			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}
	}

	/**
	 * 根据档案编码查询
	 */
	@Override
	public String queryStoreTabStrucByCode(Map<String, Object> entityMap) throws DataAccessException {

		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		if (sysPage.getTotal() == -1) {

			List<HrStoreTabStruc> list = (List<HrStoreTabStruc>) hrStoreTabStrucMapper
					.queryStoreTabStrucByCode(entityMap);

			return ChdJson.toJson(list);

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

			List<HrStoreTabStruc> list = (List<HrStoreTabStruc>) hrStoreTabStrucMapper
					.queryStoreTabStrucByCode(entityMap, rowBounds);

			PageInfo page = new PageInfo(list);

			return ChdJson.toJson(list, page.getTotal());

		}
	}

	/**
	 * 左侧树形
	 */
	@Override
	public String queryStoreTabStrucTree(Map<String, Object> entityMap) throws DataAccessException {

		/*
		 * StringBuilder treeJson = new StringBuilder();
		 * 
		 * treeJson.append("["); List<HrStoreType> storeTypeList = (List<HrStoreType>)
		 * hrStoreTypeMapper.query(entityMap); for (HrStoreType storeType :
		 * storeTypeList) { treeJson.append("{'id':'" + storeType.getStore_type_code() +
		 * "', 'pId':'0', 'name':'" + storeType.getStore_type_name() + "'},");
		 * entityMap.put("type_tab_code", storeType.getStore_type_code());
		 * List<HrTabStruc> hrTabStrucList = (List<HrTabStruc>)
		 * hrStoreTypeMapper.queryHrHosTabStruc(entityMap); if (hrTabStrucList!=null) {
		 * for (HrTabStruc hrTabStruc : hrTabStrucList) { treeJson.append("{'id':'" +
		 * hrTabStruc.getTab_code() + "', 'pId':'" + storeType.getStore_type_code() +
		 * "', 'name':'" + hrTabStruc.getTab_name() + "【" + hrTabStruc.getTab_code() +
		 * "】" + "'},"); } }
		 * 
		 * } treeJson.append("]");
		 */

		List<Map<String, Object>> tree = hrStoreTypeMapper.queryStoreTabStrucTree(entityMap);

		return JSONArray.toJSONString(tree);
	}

	@Override
	public List<?> queryStoreTabStrucByTree(Map<String, Object> entityMap) throws DataAccessException {
		List<?> l_map = hrStoreTabStrucMapper.queryStoreTabStrucByTree(entityMap);
		return l_map;
	}

	/**
	 * 数据构建 数据表分类下拉框
	 * 
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@Override
	public String queryHrHosTabStruc(Map<String, Object> entityMap) throws DataAccessException {
		List<Map<String, Object>> list = ChdJson.toListLower(hrStoreTabStrucMapper.queryHrHosTabStruc(entityMap));
		return ChdJson.toJson(list);
	}

	/**
	 * 增加 修改
	 */
	@Override
	public String addOrUpdateHrColStruc(Map<String, Object> entityMap) throws DataAccessException {
		try {
			List<String> codeList = new ArrayList<String>();

			List<HrStoreTabStruc> alllistVo = JSONArray.parseArray(String.valueOf(entityMap.get("allData")),
					HrStoreTabStruc.class);

			if (alllistVo.size() > 0 && alllistVo!=null) {

				for (HrStoreTabStruc hrColStruc : alllistVo) {
					if (codeList.contains(hrColStruc.getTab_code())) {
						throw new SysException("数据表编码" + hrColStruc.getTab_code() + "已存在.");
					}
					hrColStruc.setGroup_id(Double.valueOf(entityMap.get("group_id").toString()));
					hrColStruc.setHos_id(Double.valueOf(entityMap.get("hos_id").toString()));
					hrColStruc.setStore_type_code(entityMap.get("store_type_code").toString());
					// hrColStruc.setTab_code(entityMap.get("tab_code").toString());

					codeList.add(hrColStruc.getTab_code());

				}

				/**
				 * 先删除
				 */
				hrStoreTabStrucMapper.deleteHrColStrucBySTypeCode(entityMap,alllistVo);

				/**
				 * 增加
				 */
				hrStoreTabStrucMapper.addBatchHrColStruc(alllistVo);
			}

			return "{\"msg\":\"保存成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}
	}

	@Override
	public String addBatch(List<Map<String, Object>> entityList) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Map<String, Object>> queryHrStoreTabStrucByPrint(Map<String, Object> entityMap)
			throws DataAccessException {
		List<Map<String, Object>> list = ChdJson
				.toListLower(hrStoreTabStrucMapper.queryHrStoreTabStrucByPrint(entityMap));
		return list;
	}

	/**
	 * 导入
	 */
	@Override
	public String importExcel(Map<String, Object> entityMap) throws DataAccessException {

		// 判断类型的map
		Map<String, Object> typeMap = new HashMap<String, Object>();
		List<Map<String, Object>> typeList = ChdJson.toListLower(hrStoreTabStrucMapper.queryHrHosTabStruc(entityMap));
		for (Map<String, Object> map : typeList) {
			typeMap.put(map.get("tab_code").toString(), map.get("tab_code"));
			typeMap.put(map.get("tab_name").toString(), map.get("tab_code"));
		}

		int successNum = 0;
		int countNum = 1;
		boolean flag = true;
		StringBuilder failureMsg = new StringBuilder();
		List<HrStoreTabStruc> saveList = new ArrayList<HrStoreTabStruc>();

		try {
			List<Map<String, List<String>>> list = ReadFiles.readFiles(entityMap);
			if (list != null && list.size() > 0) {
				for (Map<String, List<String>> map : list) {
					HrStoreTabStruc saveMap = new HrStoreTabStruc();
					Map<String, Object> sMap = new HashMap<String, Object>();
					sMap.put("group_id", SessionManager.getGroupId().toString());
					sMap.put("hos_id", SessionManager.getHosId().toString());
					sMap.put("copy_code", SessionManager.getCopyCode());
					sMap.put("store_type_code", entityMap.get("store_type_code").toString());

					saveMap.setGroup_id(Double.parseDouble(SessionManager.getGroupId().toString()));
					saveMap.setHos_id(Double.parseDouble(SessionManager.getHosId().toString()));
					saveMap.setStore_type_code(entityMap.get("store_type_code").toString());
					saveMap.setTab_code(map.get("tab_code").get(1));
					saveMap.setTab_name(map.get("tab_name").get(1));
					failureMsg.append("<br/> 第" + countNum + "行 ");

					// 数据列字段
					if (map.get("tab_code").get(1) != null && map.get("tab_code").get(1) != "") {
						String tab_code = map.get("tab_code").get(1).toUpperCase();
						if (typeMap.get(tab_code) != null) {
							saveMap.setTab_code(typeMap.get(tab_code).toString());
							sMap.put("tab_code", typeMap.get(tab_code).toString());
							HrStoreTabStruc hrTab = hrStoreTabStrucMapper.queryByCodeHrColStruc(sMap);
							if (hrTab != null) {
								failureMsg.append(" 数据表编码已存在！ ");
								flag = false;
								countNum++;
								continue;
							}
						} else {
							sMap.put("tab_name", map.get("tab_name").get(1));
							HrTabStruc i = hrTabStrucMapper.queryByName(sMap);
							if (i != null) {
								if (i.getTab_name().equals(map.get("tab_name").get(1))) {
									failureMsg.append(" 数据表名称已存在！ ");
									flag = false;
									countNum++;
									continue;
								}
							}
						}
					} else {
						failureMsg.append(" 数据表编码不能为空！");
						flag = false;
					}

					successNum++;
					countNum++;
					saveList.add(saveMap);
				}
			}

			if (flag == false) {
				return "{\"error\":\"导入失败！  " + failureMsg + "\"}";
			} else {
				if (saveList.size() > 0) {
					hrStoreTabStrucMapper.addBatchHrColStruc(saveList);
				}
				return "{\"msg\":\"已成功导入 " + successNum + "条\",\"state\":\"true\"}";
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			return "{\"error\":\"导入失败！\"}";
		}
	}

	@Override
	public String addStoreTabBatch(Map<String, Object> mapVo) {
		try {
			List<String> arrlist = JSONArray.parseArray(mapVo.get("arrid").toString(),String.class);
			int addCount = hrStoreTabStrucMapper.addStoreTabBatch(mapVo,arrlist);
			if (addCount == 0) {
				throw new SysException("添加失败,请刷新页面后重试!");
			}
			return "{\"msg\":\"添加成功!\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			return "{\"error\":\"导入 失败 :"+e.getMessage()+"\"}";
		}
	}

}
