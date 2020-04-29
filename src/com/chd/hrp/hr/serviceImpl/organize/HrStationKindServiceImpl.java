package com.chd.hrp.hr.serviceImpl.organize;

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
import com.chd.hrp.hr.dao.organize.HrStationKindMapper;
import com.chd.hrp.hr.entity.orangnize.HrStationKind;
import com.chd.hrp.hr.service.organize.HrStationKindService;
import com.github.pagehelper.PageInfo;

@Service("hrStationKindService")
public class HrStationKindServiceImpl implements HrStationKindService {

	private static Logger logger = Logger.getLogger(HrStationKindServiceImpl.class);

	@Resource(name = "hrStationKindMapper")
	private final HrStationKindMapper hrStationKindMapper = null;

	// 默认显示20条数据
	RowBounds rowBoundsALL = new RowBounds(0, 20);

	/**
	 * 增加岗位分类
	 */
	@Override
	public String add(Map<String, Object> entityMap) throws DataAccessException {
		entityMap.put("group_id", SessionManager.getGroupId());
		entityMap.put("hos_id", SessionManager.getHosId());
		entityMap.put("copy_code", SessionManager.getCopyCode());
		//获取对象职务等级
		List<HrStationKind> list = hrStationKindMapper.queryStationKindById(entityMap);

		if (list.size() > 0) {

			for (HrStationKind hrDutyLevel : list) {
				if (hrDutyLevel.getKind_code().equals(entityMap.get("kind_code"))) {
					return "{\"error\":\"编码：" + hrDutyLevel.getKind_code().toString() + "已存在.\"}";
				}
				if (hrDutyLevel.getKind_name().equals(entityMap.get("kind_name"))) {
					return "{\"error\":\"名称：" + hrDutyLevel.getKind_name().toString() + "已存在.\"}";
				}
			}
		}
			
		
		try {
			entityMap.put("spell_code", StringTool.toPinyinShouZiMu(entityMap.get("kind_name").toString()));
			entityMap.put("wbx_code", StringTool.toWuBi(entityMap.get("kind_name").toString()));

			hrStationKindMapper.addStationKind(entityMap);
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}
	}

	@Override
	public HrStationKind queryByCode(Map<String, Object> entityMap) throws DataAccessException {
		return hrStationKindMapper.queryByCode(entityMap);
	}

	/**
	 * 删除岗位
	 */
	@Override
	public String deleteBatchStationKind(List<HrStationKind> entityList) throws DataAccessException {

		try {

			hrStationKindMapper.deleteBatchStationKind(entityList);

			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}
	}

	/**
	 * 修改保存
	 */
	@Override
	public String updateHrStationKind(Map<String, Object> entityMap) throws DataAccessException {

		try {
			entityMap.put("group_id", SessionManager.getGroupId());
			entityMap.put("hos_id", SessionManager.getHosId());
			entityMap.put("copy_code", SessionManager.getCopyCode());
			entityMap.put("spell_code", StringTool.toPinyinShouZiMu(entityMap.get("kind_name").toString()));
			entityMap.put("wbx_code", StringTool.toWuBi(entityMap.get("kind_name").toString()));

			hrStationKindMapper.updateHrStationKind(entityMap);
			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}
	}

	/**
	 * 查询所有岗位
	 */
	@SuppressWarnings("unchecked")
	@Override
	public String queryHrStationKind(Map<String, Object> entityMap) throws DataAccessException {

		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		if (sysPage.getTotal() == -1) {

			List<HrStationKind> list = (List<HrStationKind>) hrStationKindMapper.query(entityMap);

			return ChdJson.toJson(list);

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

			List<HrStationKind> list = (List<HrStationKind>) hrStationKindMapper.query(entityMap, rowBounds);

			PageInfo page = new PageInfo(list);

			return ChdJson.toJson(list, page.getTotal());

		}

	}

	@Override
	public String queryHosEmpKindTree(Map<String, Object> entityMap) throws DataAccessException {

		StringBuilder treeJson = new StringBuilder();

		treeJson.append("[");
		/*List<HrStation> storeTypeList = (List<HrStation>) hrStationKindMapper.query(entityMap);
		for (HrStation storeType : storeTypeList) {
			treeJson.append(
					"{'id':'" + storeType.getKind_code() + "', 'pId':'0', 'name':'" + storeType.getKind_name() + "'},");

		}*/
		treeJson.append("]");
		return treeJson.toString();
	}
	
	/**
	 * 导入数据
	 */
	@Override
	public String importDate(Map<String, Object> entityMap)
			throws DataAccessException {
		int successNum = 0;
		int failureNum = 0;
		StringBuilder failureMsg = new StringBuilder();
		List<Map<String,Object>> saveList = new ArrayList<Map<String,Object>>();
		Map<String, Object> whetherMap = new HashMap<String, Object>();
		whetherMap.put("是", "1");
		whetherMap.put("否", "0");
		whetherMap.put("1", "1");
		whetherMap.put("0", "0");
		
		try {
			List<Map<String, List<String>>> list = ReadFiles.readFiles(entityMap);
			if(list != null && list.size() > 0){
				for (Map<String, List<String>> map : list) {
					Map<String,Object> saveMap = new HashMap<String,Object>();
					saveMap.put("group_id", SessionManager.getGroupId());
					saveMap.put("hos_id", SessionManager.getHosId());
					saveMap.put("copy_code", SessionManager.getCopyCode());
					saveMap.put("kind_code", map.get("kind_code").get(1));
					saveMap.put("kind_name", map.get("kind_name").get(1));
					saveMap.put("spell_code", StringTool.toPinyinShouZiMu(map.get("kind_name").get(1)));
					saveMap.put("wbx_code", StringTool.toWuBi(map.get("kind_name").get(1)));
					saveMap.put("is_stop", whetherMap.get(map.get("is_stop").get(1)));
					saveMap.put("note", map.get("note").get(1));
					
					HrStationKind type = hrStationKindMapper.queryByCode(saveMap);
					
					if(type != null){
						failureMsg.append("<br/>类别代码 "+map.get("kind_name")+" 已存在; ");
						failureNum++;
						continue;
					}
					successNum++;
					saveList.add(saveMap);
				}
				if(saveList.size() > 0){
					hrStationKindMapper.addBatch(saveList);
				}
				if (failureNum>0){
					failureMsg.insert(0, "，失败 "+failureNum+" 条，导入信息如下：");
				}
			}
			return "{\"msg\":\"已成功导入 "+successNum+"条"+failureMsg+"\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage());
			return "{\"error\":\"导入失败！\"}";
		}
	}

}
