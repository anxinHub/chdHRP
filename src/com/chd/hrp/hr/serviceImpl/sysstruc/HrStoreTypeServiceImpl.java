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

import com.chd.base.SessionManager;
import com.chd.base.SysPage;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.base.util.ReadFiles;
import com.chd.base.util.StringTool;
import com.chd.hrp.hr.dao.sysstruc.HrStoreTypeMapper;
import com.chd.hrp.hr.entity.sysstruc.HrStoreType;
import com.chd.hrp.hr.service.sysstruc.HrStoreTypeService;
import com.chd.hrp.sys.entity.StoreType;
import com.github.pagehelper.PageInfo;

/**
 * 档案库分类构建
 * 
 * @author Administrator
 *
 */
@Service("hrStoreTypeService")
@SuppressWarnings("unchecked")
public class HrStoreTypeServiceImpl implements HrStoreTypeService {
	private static Logger logger = Logger.getLogger(HrStoreTypeServiceImpl.class);

	@Resource(name = "hrStoreTypeMapper")
	private final HrStoreTypeMapper hrStoreTypeMapper = null;

	/**
	 * 增加档案库分类
	 */
	@Override
	public String add(Map<String, Object> entityMap) throws DataAccessException {
		entityMap.put("group_id", SessionManager.getGroupId());
		entityMap.put("hos_id", SessionManager.getHosId());
		HrStoreType list = hrStoreTypeMapper.queryByCode(entityMap);

		if (list != null) {
			if (list.getStore_type_code().equals(entityMap.get("store_type_code"))) {
				return "{\"error\":\"编码：" + list.getStore_type_code().toString() + "已存在.\"}";
			}
			if (list.getStore_type_name().equals(entityMap.get("store_type_name"))) {
				return "{\"error\":\"名称：" + list.getStore_type_name().toString() + "已存在.\"}";
			}
		}
		try {
			entityMap.put("spell_code", StringTool.toPinyinShouZiMu(entityMap.get("store_type_name").toString()));
			entityMap.put("wbx_code", StringTool.toWuBi(entityMap.get("store_type_name").toString()));

			hrStoreTypeMapper.add(entityMap);
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}
	}

	/**
	 * 查询数据
	 */
	@Override

	public String query(Map<String, Object> entityMap) throws DataAccessException {

		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		if (sysPage.getTotal() == -1) {

			List<StoreType> list = (List<StoreType>) hrStoreTypeMapper.query(entityMap);

			return ChdJson.toJson(list);

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

			List<StoreType> list = (List<StoreType>) hrStoreTypeMapper.query(entityMap, rowBounds);

			@SuppressWarnings("rawtypes")
			PageInfo page = new PageInfo(list);

			return ChdJson.toJson(list, page.getTotal());

		}
	}

	/**
	 * 删除档案库分类
	 */
	@Override
	public String deleteBatchHrStoreType(List<HrStoreType> entityList) throws DataAccessException {

		try {

			hrStoreTypeMapper.deleteBatchHrStoreType(entityList);

			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}
	}

	/**
	 * 修改档案库分类
	 */
	@Override
	public String update(Map<String, Object> entityMap) throws DataAccessException {
		try {
			entityMap.put("group_id", SessionManager.getGroupId());
			entityMap.put("hos_id", SessionManager.getHosId());
			entityMap.put("spell_code", StringTool.toPinyinShouZiMu(entityMap.get("store_type_name").toString()));
			entityMap.put("wbx_code", StringTool.toWuBi(entityMap.get("store_type_name").toString()));
			
			HrStoreType list = hrStoreTypeMapper.queryByCodeName(entityMap);

			if (list != null) {
				if (list.getStore_type_name().equals(entityMap.get("store_type_name"))) {
					return "{\"error\":\"名称：" + list.getStore_type_name().toString() + "已存在.\"}";
				}
			}
			HrStoreType exists = hrStoreTypeMapper.queryExistsByName(entityMap);
			// 不存在：新增；存在：更新
			if(exists == null){
				hrStoreTypeMapper.add(entityMap);
			}else{
				hrStoreTypeMapper.update(entityMap);
			}
			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}
	}

	/**
	 * 跳转修改查询
	 */
	@Override
	public HrStoreType queryByCode(Map<String, Object> entityMap) throws DataAccessException {
		return hrStoreTypeMapper.queryByCode(entityMap);
	}

	/**
	 * 导入数据
	 */
	@Override
	public String importDate(Map<String, Object> entityMap) throws DataAccessException {
		int successNum = 0;
		int failureNum = 0;
		StringBuilder failureMsg = new StringBuilder();
		List<Map<String, Object>> saveList = new ArrayList<Map<String, Object>>();
		Map<String, Object> whetherMap = new HashMap<String, Object>();
		whetherMap.put("是", "1");
		whetherMap.put("否", "0");
		whetherMap.put("1", "1");
		whetherMap.put("0", "0");

		try {
			List<Map<String, List<String>>> list = ReadFiles.readFiles(entityMap);
			if (list != null && list.size() > 0) {
				for (Map<String, List<String>> map : list) {
					if(map.get("store_type_code").get(1).toString().equals("")){
						continue;
					}
					Map<String, Object> saveMap = new HashMap<String, Object>();
					saveMap.put("group_id", SessionManager.getGroupId());
					saveMap.put("hos_id", SessionManager.getHosId());
					saveMap.put("copy_code", SessionManager.getCopyCode());
					saveMap.put("store_type_code", map.get("store_type_code").get(1));
					saveMap.put("store_type_name", map.get("store_type_name").get(1));
					saveMap.put("spell_code", StringTool.toPinyinShouZiMu(map.get("store_type_name").get(1)));
					saveMap.put("wbx_code", StringTool.toWuBi(map.get("store_type_name").get(1)));
					saveMap.put("note", map.get("note").get(1));

					HrStoreType type = hrStoreTypeMapper.queryByCode(saveMap);

					if (type != null) {
						if (saveMap.get("store_type_code").equals(type.getStore_type_code())) {
							failureMsg.append("<br/>类别代码 " + map.get("store_type_code") + " 已存在; ");
						} else if (saveMap.get("store_type_name").equals(type.getStore_type_name())) {
							failureMsg.append("<br/>类别名称 " + map.get("store_type_name") + " 已存在; ");
						}

						failureNum++;
						continue;
					}
					successNum++;
					saveList.add(saveMap);
				}
				if (saveList.size() > 0) {
					hrStoreTypeMapper.addBatch(saveList);
				}
				if (failureNum > 0) {
					failureMsg.insert(0, "，失败 " + failureNum + " 条，导入信息如下：");
				}
			}
			return "{\"msg\":\"已成功导入 " + successNum + "条" + failureMsg + "\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage());
			return "{\"error\":\"导入失败！\"}";
		}
	}

	@Override
	public List<Map<String, Object>> queryDicStoreType(Map<String, Object> map) throws DataAccessException {
		try{
			return hrStoreTypeMapper.queryDicStoreType(map);
		}catch(Exception e){
			logger.error(e.getMessage());
			return null;
		}
	}

}
