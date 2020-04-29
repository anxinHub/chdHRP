package com.chd.hrp.ass.serviceImpl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.chd.base.SessionManager;
import com.chd.base.SysPage;
import com.chd.base.util.ChdJson;
import com.chd.hrp.ass.dao.HrpAssSelectMapper;
import com.chd.hrp.ass.entity.HrpAssSelect;
import com.chd.hrp.ass.entity.dict.AssMaintainItemDict;
import com.chd.hrp.ass.entity.dict.AssNoDict;
import com.chd.hrp.ass.service.HrpAssSelectService;
import com.github.pagehelper.PageInfo;

@Service("hrpAssSelectService")
public class HrpAssSelectServiceImpl implements HrpAssSelectService {
 
	private static Logger logger = Logger.getLogger(HrpAssSelectServiceImpl.class);

	@Resource(name = "hrpAssSelectMapper")
	private final HrpAssSelectMapper hrpAssSelectMapper = null;

	// 默认显示20条数据
	RowBounds rowBoundsALL = new RowBounds(0, 20);  

	/**
	 * 资产分类下拉框检索
	 * 
	 * @param entityMap 
	 * @return
	 * @throws DataAccessException
	 */
	@Override
	public String queryAssTypeDict(Map<String, Object> entityMap) throws DataAccessException { 
		RowBounds rowBounds = new RowBounds(0, 20);
		if (entityMap.get("pageSize") != null) { 
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
		} else {
			rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(hrpAssSelectMapper.queryAssTypeDict(entityMap, rowBounds));
	}

	/**
	 * 仓库下拉框检索
	 * 
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	@Override
	public String queryHosStoreDict(Map<String, Object> entityMap) throws DataAccessException {
		RowBounds rowBounds = new RowBounds(0, 20);
		if (entityMap.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
		} else {
			rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(hrpAssSelectMapper.queryHosStoreDict(entityMap, rowBounds));
	}

	/**
	 * 仓库下拉框检索
	 * 
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	@Override
	public String queryAllotHosStoreDict(Map<String, Object> entityMap) throws DataAccessException {
		RowBounds rowBounds = new RowBounds(0, 20);
		if (entityMap.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
		} else {
			rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(hrpAssSelectMapper.queryAllotHosStoreDict(entityMap, rowBounds));
	}

	/**
	 * 生产厂商下拉框检索
	 * 
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	@Override
	public String queryHosFacDict(Map<String, Object> entityMap) throws DataAccessException {
		RowBounds rowBounds = new RowBounds(0, 20);
		if (entityMap.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
		} else {
			rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(hrpAssSelectMapper.queryHosFacDict(entityMap, rowBounds));
	}

	/**
	 * 供应商下拉框检索
	 * 
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	@Override
	public String queryHosSupDict(Map<String, Object> entityMap) throws DataAccessException {
		RowBounds rowBounds = new RowBounds(0, 20);
		if (entityMap.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
		} else {
			rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(hrpAssSelectMapper.queryHosSupDict(entityMap, rowBounds));
	}

	/**
	 * 资产用途下拉框检索
	 * 
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	@Override
	public String queryAssUsageDict(Map<String, Object> entityMap) throws DataAccessException {
		RowBounds rowBounds = new RowBounds(0, 20);
		if (entityMap.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
		} else {
			rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(hrpAssSelectMapper.queryAssUsageDict(entityMap, rowBounds));
	}

	/**
	 * 折旧方法下拉框检索
	 * 
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	@Override
	public String queryAssDepreMethodDict(Map<String, Object> entityMap) throws DataAccessException {
		RowBounds rowBounds = new RowBounds(0, 20);
		if (entityMap.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
		} else {
			rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(hrpAssSelectMapper.queryAssDepreMethodDict(entityMap, rowBounds));
	}

	/**
	 * 资产变更下拉框检索
	 * 
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	@Override
	public String queryAssNoDict(Map<String, Object> entityMap) throws DataAccessException {
		RowBounds rowBounds = new RowBounds(0, 20);
		if (entityMap.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
		} else {
			rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(hrpAssSelectMapper.queryAssNoDict(entityMap, rowBounds));

	}

	/**
	 * 资产列表
	 * 
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	@Override
	public String queryAssNoDictTable(Map<String, Object> entityMap) throws DataAccessException {

		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

		List<AssNoDict> list = hrpAssSelectMapper.queryAssNoDictTable(entityMap, rowBounds);

		PageInfo page = new PageInfo(list);

		return ChdJson.toJson(list, page.getTotal());

	}

	/**
	 * 科室下拉框检索
	 * 
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	@Override
	public String queryDeptDict(Map<String, Object> entityMap) throws DataAccessException {
		
		return JSON.toJSONString(hrpAssSelectMapper.queryDeptDict(entityMap));

	}

	/**
	 * 职工下拉框检索
	 * 
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	@Override
	public String queryHeadEmp(Map<String, Object> entityMap) throws DataAccessException {
		RowBounds rowBounds = new RowBounds(0, 20);
		if (entityMap.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
		} else {
			rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(hrpAssSelectMapper.queryHeadEmp(entityMap, rowBounds));
	}

	/**
	 * 合同下拉框检索
	 * 
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	@Override
	public String queryContractMain(Map<String, Object> entityMap) throws DataAccessException {
		RowBounds rowBounds = new RowBounds(0, 20);
		if (entityMap.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
		} else {
			rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(hrpAssSelectMapper.queryContractMain(entityMap, rowBounds));
	}

	/**
	 * 资产性质下拉框检索
	 * 
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	@Override
	public String queryAssNaturs(Map<String, Object> entityMap) throws DataAccessException {
		RowBounds rowBounds = new RowBounds(0, 20);
		if (entityMap.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
		} else {
			rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(hrpAssSelectMapper.queryAssNaturs(entityMap, rowBounds));
	}

	/**
	 * 资产业务类型下拉框检索
	 * 
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	@Override
	public String queryAssBusType(Map<String, Object> entityMap) throws DataAccessException {
		RowBounds rowBounds = new RowBounds(0, 20);
		if (entityMap.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
		} else {
			rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(hrpAssSelectMapper.queryAssBusType(entityMap, rowBounds));
	}

	/**
	 * 申请单号
	 * 
	 * @param entityMap
	 * @param rowBounds
	 * @return
	 * @throws DataAccessException
	 */
	@Override
	public String queryAssApplyNoDict(Map<String, Object> entityMap) throws DataAccessException {
		RowBounds rowBounds = new RowBounds(0, 20);
		if (entityMap.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
		} else {
			rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(hrpAssSelectMapper.queryAssApplyNoDict(entityMap, rowBounds));
	}

	@Override
	public String queryAssTypeSuperCode(Map<String, Object> entityMap) throws DataAccessException {
		RowBounds rowBounds = new RowBounds(0, 20);
		if (entityMap.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
		} else {
			rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(hrpAssSelectMapper.queryAssTypeSuperCode(entityMap, rowBounds));
	}

	/**
	 * 保养计划
	 * 
	 * @param entityMap
	 * @param rowBounds
	 * @return
	 * @throws DataAccessException
	 */
	@Override
	public String queryAssMaintainPlanDict(Map<String, Object> entityMap) throws DataAccessException {
		RowBounds rowBounds = new RowBounds(0, 20);
		if (entityMap.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
		} else {
			rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(hrpAssSelectMapper.queryAssMaintainPlanDict(entityMap, rowBounds));
	}

	/**
	 * 保养项目字典下拉框检索，表格形式
	 * 
	 * @param entityMap
	 * @param rowBounds
	 * @return
	 * @throws DataAccessException
	 */
	@Override
	public String queryAssMaintainItemTable(Map<String, Object> entityMap) throws DataAccessException {
		RowBounds rowBounds = new RowBounds(0, 20);
		if (entityMap.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
		} else {
			rowBounds = rowBoundsALL;
		}
		List<AssMaintainItemDict> list = hrpAssSelectMapper.queryAssMaintainItemTable(entityMap, rowBounds);
		PageInfo page = new PageInfo(list);
		return ChdJson.toJson(list, page.getTotal());
	}

	/**
	 * 计量计划
	 * 
	 * @param entityMap
	 * @param rowBounds
	 * @return
	 * @throws DataAccessException
	 */
	@Override
	public String queryAssMeasurePlanDict(Map<String, Object> entityMap) throws DataAccessException {
		RowBounds rowBounds = new RowBounds(0, 20);
		if (entityMap.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
		} else {
			rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(hrpAssSelectMapper.queryAssMeasurePlanDict(entityMap, rowBounds));
	}

	@Override
	public String queryAssDeperMethodDict(Map<String, Object> entityMap) throws DataAccessException {
		RowBounds rowBounds = new RowBounds(0, 20);
		if (entityMap.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
		} else {
			rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(hrpAssSelectMapper.queryAssDeperMethodDict(entityMap, rowBounds));
	}

	@Override
	public String queryAssCardTable(Map<String, Object> entityMap) throws DataAccessException {
		
		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

		List<Map<String, Object>> list = null;
		if (entityMap.get("ass_nature") != null && !entityMap.get("ass_nature").equals("")) {
			if (entityMap.get("ass_nature").toString().equals("02")) {
				list = hrpAssSelectMapper.queryAssCardSpecial(entityMap, rowBounds);
			} else if (entityMap.get("ass_nature").toString().equals("03")) {
				list = hrpAssSelectMapper.queryAssCardGeneral(entityMap, rowBounds);
			} else if (entityMap.get("ass_nature").toString().equals("01")) {
				list = hrpAssSelectMapper.queryAssCardHouse(entityMap, rowBounds);
			} else if (entityMap.get("ass_nature").toString().equals("04")) {
				list = hrpAssSelectMapper.queryAssCardOther(entityMap, rowBounds);
			} else if (entityMap.get("ass_nature").toString().equals("05")) {
				list = hrpAssSelectMapper.queryAssCardInassets(entityMap, rowBounds);
			} else if (entityMap.get("ass_nature").toString().equals("06")) {
				list = hrpAssSelectMapper.queryAssCardLand(entityMap, rowBounds);
			}
		} else {
			
			list = hrpAssSelectMapper.queryAssCardAll(entityMap, rowBounds);
		}

//		List<Map<String, Object>> newList = new ArrayList<Map<String, Object>>();
//		for (Map<String, Object> map : list) {
//			Map<String, Object> newMap = new HashMap<String, Object>();
//			for (String key : map.keySet()) {
//				newMap.put(key.toLowerCase(), map.get(key));
//			}
//			newList.add(newMap);
//		}
		
		PageInfo page = new PageInfo(list);

		return ChdJson.toJson(list, page.getTotal());
	}

	/**
	 * 资金来源字典
	 * 
	 * @param entityMap
	 * @param rowBounds
	 * @return
	 * @throws DataAccessException
	 */
	@Override
	public String querySourceDict(Map<String, Object> entityMap) throws DataAccessException {
		RowBounds rowBounds = new RowBounds(0, 20);
		if (entityMap.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
		} else {
			rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(hrpAssSelectMapper.querySourceDict(entityMap, rowBounds));

	}
	/**
	 * 设备来源字典
	 * 
	 * @param entityMap
	 * @param rowBounds
	 * @return
	 * @throws DataAccessException
	 */
	@Override
	public String queryDeviceDict(Map<String, Object> entityMap) throws DataAccessException {
		RowBounds rowBounds = new RowBounds(0, 20);
		if (entityMap.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
		} else {
			rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(hrpAssSelectMapper.queryDeviceDict(entityMap, rowBounds));

	}
	/**
	 * 申购类别字典
	 * 
	 * @param entityMap
	 * @param rowBounds
	 * @return
	 * @throws DataAccessException
	 */
	@Override
	public String queryBuyDict(Map<String, Object> entityMap) throws DataAccessException {
		RowBounds rowBounds = new RowBounds(0, 20);
		if (entityMap.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
		} else {
			rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(hrpAssSelectMapper.queryBuyDict(entityMap, rowBounds));

	}

	/**
	 * 计量单位字典
	 * 
	 * @param entityMap
	 * @param rowBounds
	 * @return
	 * @throws DataAccessException
	 */
	@Override
	public String queryHosUnitDict(Map<String, Object> entityMap) throws DataAccessException {
		RowBounds rowBounds = new RowBounds(0, 20);
		if (entityMap.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
		} else {
			rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(hrpAssSelectMapper.queryHosUnitDict(entityMap, rowBounds));

	}

	/**
	 * 集团对应医院字典
	 * 
	 * @param entityMap
	 * @param rowBounds
	 * @return
	 * @throws DataAccessException
	 */
	@Override
	public String queryHosInfoDict(Map<String, Object> entityMap) throws DataAccessException {
		RowBounds rowBounds = new RowBounds(0, 20);
		if (entityMap.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
		} else {
			rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(hrpAssSelectMapper.queryHosInfoDict(entityMap, rowBounds));
	}

	/**
	 * 集团字典
	 * 
	 * @param entityMap
	 * @param rowBounds
	 * @return
	 * @throws DataAccessException
	 */
	@Override
	public String queryGroupDict(Map<String, Object> entityMap) throws DataAccessException {
		RowBounds rowBounds = new RowBounds(0, 20);
		if (entityMap.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
		} else {
			rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(hrpAssSelectMapper.queryGroupDict(entityMap, rowBounds));
	}

	@Override
	public String queryContractMainDict(Map<String, Object> entityMap) throws DataAccessException {
		RowBounds rowBounds = new RowBounds(0, 20);
		if (entityMap.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
		} else {
			rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(hrpAssSelectMapper.queryContractMainDict(entityMap, rowBounds));
	}

	@Override
	public String queryMatFinaType(Map<String, Object> entityMap) throws DataAccessException {
		RowBounds rowBounds = new RowBounds(0, 20);
		if (entityMap.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
		} else {
			rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(hrpAssSelectMapper.queryMatFinaType(entityMap, rowBounds));
	}

	@Override
	public String queryAssTypeDictIsLast(Map<String, Object> entityMap) throws DataAccessException {
		RowBounds rowBounds = new RowBounds(0, 20);
		if (entityMap.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
		} else {
			rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(hrpAssSelectMapper.queryAssTypeDictIsLast(entityMap, rowBounds));
	}

	@Override
	public String queryMatFinaTypeIsLast(Map<String, Object> entityMap) throws DataAccessException {
		RowBounds rowBounds = new RowBounds(0, 20);
		if (entityMap.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
		} else {
			rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(hrpAssSelectMapper.queryMatFinaTypeIsLast(entityMap, rowBounds));
	}

	@Override
	public String queryDeptDictInitValue(Map<String, Object> entityMap) throws DataAccessException {
		return JSON.toJSONString(hrpAssSelectMapper.queryDeptDictInitValue(entityMap));
	}

	@Override
	public String queryAssNoDictTree(Map<String, Object> entityMap) throws DataAccessException {
		RowBounds rowBounds = new RowBounds(0, 20);
		if (entityMap.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
		} else {
			rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(hrpAssSelectMapper.queryAssNoDictTree(entityMap, rowBounds));
	}

	@Override
	public String queryAssFileTypeDict(Map<String, Object> entityMap) throws DataAccessException {
		RowBounds rowBounds = new RowBounds(0, 20);
		if (entityMap.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
		} else {
			rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(hrpAssSelectMapper.queryAssFileTypeDict(entityMap, rowBounds));
	}

	// 根据资产ID查询验收项目字典
	@Override
	public String queryAssAcceptItem(Map<String, Object> entityMap) throws DataAccessException {
		RowBounds rowBounds = new RowBounds(0, 20);
		if (entityMap.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
		} else {
			rowBounds = rowBoundsALL;
		}
		List<HrpAssSelect> list = hrpAssSelectMapper.queryAssAcceptItemAffi(entityMap, rowBounds);
		List<HrpAssSelect> list1 = hrpAssSelectMapper.queryAssAcceptItemDict(entityMap, rowBounds);
		if (list.size() > 0) {
			PageInfo page = new PageInfo(list);
			return ChdJson.toJson(list, page.getTotal());
		} else {
			PageInfo page = new PageInfo(list1);
			return ChdJson.toJson(list1, page.getTotal());
		}
	}

	@Override
	public String queryAssStructDict(Map<String, Object> entityMap) throws DataAccessException {
		RowBounds rowBounds = new RowBounds(0, 20);
		if (entityMap.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
		} else {
			rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(hrpAssSelectMapper.queryAssStructDict(entityMap, rowBounds));
	}

	@Override
	public String queryAssLandSourceDict(Map<String, Object> entityMap) throws DataAccessException {
		RowBounds rowBounds = new RowBounds(0, 20);
		if (entityMap.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
		} else {
			rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(hrpAssSelectMapper.queryAssLandSourceDict(entityMap, rowBounds));
	}

	@Override
	public String queryAssPropDict(Map<String, Object> entityMap) throws DataAccessException {
		RowBounds rowBounds = new RowBounds(0, 20);
		if (entityMap.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
		} else {
			rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(hrpAssSelectMapper.queryAssPropDict(entityMap, rowBounds));
	}

	@Override
	public String queryAssRelicGradeDict(Map<String, Object> entityMap) throws DataAccessException {
		RowBounds rowBounds = new RowBounds(0, 20);
		if (entityMap.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
		} else {
			rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(hrpAssSelectMapper.queryAssRelicGradeDict(entityMap, rowBounds));
	}

	@Override
	public String queryUserDict(Map<String, Object> entityMap) throws DataAccessException {
		RowBounds rowBounds = new RowBounds(0, 20);
		if (entityMap.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
		} else {
			rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(hrpAssSelectMapper.queryUserDict(entityMap, rowBounds));
	}

	@Override
	public String queryAssInMainDict(Map<String, Object> entityMap) throws DataAccessException {
		RowBounds rowBounds = new RowBounds(0, 20);
		if (entityMap.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
		} else {
			rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(hrpAssSelectMapper.queryAssInMainDict(entityMap, rowBounds));
	}

	@Override
	public String queryAssBarTypeDict(Map<String, Object> entityMap) throws DataAccessException {
		RowBounds rowBounds = new RowBounds(0, 20);
		if (entityMap.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
		} else {
			rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(hrpAssSelectMapper.queryAssBarTypeDict(entityMap, rowBounds));
	}

	@Override
	public String queryAssCardUseStateDict(Map<String, Object> entityMap) throws DataAccessException {
		RowBounds rowBounds = new RowBounds(0, 20);
		if (entityMap.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
		} else {
			rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(hrpAssSelectMapper.queryAssCardUseStateDict(entityMap, rowBounds));
	}

	@Override
	public String queryAssDisposeTypeDict(Map<String, Object> entityMap) throws DataAccessException {
		RowBounds rowBounds = new RowBounds(0, 20);
		if (entityMap.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
		} else {
			rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(hrpAssSelectMapper.queryAssDisposeTypeDict(entityMap, rowBounds));
	}

	@Override
	public String queryAssProjDict(Map<String, Object> entityMap) throws DataAccessException {
		RowBounds rowBounds = new RowBounds(0, 20);
		if (entityMap.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
		} else {
			rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(hrpAssSelectMapper.queryAssProjDict(entityMap, rowBounds));
	}

	/**
	 * 生产厂商下拉列表
	 */
	@Override
	public String queryFacTypeDict(Map<String, Object> entityMap) throws DataAccessException {
		RowBounds rowBounds = new RowBounds(0, 20);
		if (entityMap.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
		} else {
			rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(hrpAssSelectMapper.queryFacTypeDict(entityMap, rowBounds));
	}

	@Override
	public String queryHosFac(Map<String, Object> entityMap) throws DataAccessException {
		RowBounds rowBounds = new RowBounds(0, 20);
		if (entityMap.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
		} else {
			rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(hrpAssSelectMapper.queryHosFac(entityMap, rowBounds));
	}

	@Override
	public String queryMatPayTerm(Map<String, Object> entityMap) throws DataAccessException {
		RowBounds rowBounds = new RowBounds(0, 20);

		if (entityMap.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
		} else {
			rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(hrpAssSelectMapper.queryMatPayTerm(entityMap, rowBounds));
	}

	// 主表库房是否停用
	@Override
	public String queryMatYearOrNo(Map<String, Object> entityMap) throws DataAccessException {
		RowBounds rowBounds = new RowBounds(0, 20);
		if (entityMap.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
		} else {
			rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(hrpAssSelectMapper.queryMatYearOrNo(entityMap, rowBounds));
	}

	// 库房分类
	@Override
	public String queryMatStoreType(Map<String, Object> entityMap) throws DataAccessException {
		RowBounds rowBounds = new RowBounds(0, 20);
		if (entityMap.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
		} else {
			rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(hrpAssSelectMapper.queryMatStoreType(entityMap, rowBounds));
	}

	/**
	 * 职能科室
	 * 
	 * @param mapVo
	 * @return
	 */
	@Override
	public String queryMatDeptIsManager(Map<String, Object> entityMap) throws DataAccessException {
		RowBounds rowBounds = new RowBounds(0, 20);
		if (entityMap.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
		} else {
			rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(hrpAssSelectMapper.queryMatDeptIsManager(entityMap, rowBounds));
	}

	/**
	 * 普通职工/领料人 --职工表
	 * 
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	@Override
	public String queryMatEmp(Map<String, Object> entityMap) throws DataAccessException {
		RowBounds rowBounds = new RowBounds(0, 20);
		if (entityMap.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
		} else {
			rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(hrpAssSelectMapper.queryMatEmp(entityMap, rowBounds));
	}

	// 采购人
	@Override
	public String queryMatStockEmp(Map<String, Object> entityMap) throws DataAccessException {
		RowBounds rowBounds = new RowBounds(0, 20);
		if (entityMap.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
		} else {
			rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(hrpAssSelectMapper.queryMatStockEmp(entityMap, rowBounds));
	}

	// 库房管理员
	@Override
	public String queryMatManagerEmp(Map<String, Object> entityMap) throws DataAccessException {
		RowBounds rowBounds = new RowBounds(0, 20);
		if (entityMap.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
		} else {
			rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(hrpAssSelectMapper.queryMatManagerEmp(entityMap, rowBounds));
	}

	@Override
	public String queryHosCopyDict(Map<String, Object> mapVo) throws DataAccessException {
		RowBounds rowBounds = new RowBounds(0, 20);
		if (mapVo.get("pageSize") != null) {
			rowBounds = new RowBounds(0, (Integer) mapVo.get("pageSize"));
		} else {
			rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(hrpAssSelectMapper.queryHosCopyDict(mapVo, rowBounds));
	}

	@Override
	public String queryAssIsDept(Map<String, Object> mapVo) throws DataAccessException {
		RowBounds rowBounds = new RowBounds(0, 20);
		if (mapVo.get("pageSize") != null) {
			rowBounds = new RowBounds(0, (Integer) mapVo.get("pageSize"));
		} else {
			rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(hrpAssSelectMapper.queryAssIsDept(mapVo, rowBounds));
	}

	@Override
	public String queryBusType(Map<String, Object> mapVo) {
		RowBounds rowBounds = new RowBounds(0, 20);
		if (mapVo.get("pageSize") != null) {
			rowBounds = new RowBounds(0, (Integer) mapVo.get("pageSize"));
		} else {
			rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(hrpAssSelectMapper.queryBusType(mapVo, rowBounds));
	}

	@Override
	public String queryHosDeptTerr(Map<String, Object> entityMap) {
		StringBuilder jsonResult = new StringBuilder();

		jsonResult.append("{Rows:[");

		jsonResult.append("{'id':'0','name':'科室列表'},");
		List<Map<String, Object>> ListVo = hrpAssSelectMapper.queryHosDeptTerr(entityMap);
		for (int i = 0; i < ListVo.size(); i++) {
			Map<String, Object> mapVo = ListVo.get(i);
			if ((i + 1) == ListVo.size()) {
				jsonResult.append("{'id':'" + mapVo.get("dept_code") + "','pId':'0','name':'" + mapVo.get("dept_name")
						+ "',deptId:'" + mapVo.get("dept_id") + "'}");
			} else {
				jsonResult.append("{'id':'" + mapVo.get("dept_code") + "','pId':'0','name':'" + mapVo.get("dept_name")
						+ "',deptId:'" + mapVo.get("dept_id") + "'},");

			}
		}
		jsonResult.append("]}");

		return jsonResult.toString();
	}

	@Override
	public String queryPayStageGrid(Map<String, Object> entityMap) throws DataAccessException {
		RowBounds rowBounds = new RowBounds(0, 20);
		if (entityMap.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
		} else {
			rowBounds = rowBoundsALL;
		}
		List<Map<String, Object>> list = hrpAssSelectMapper.queryPayStageGrid(entityMap, rowBounds);
		PageInfo page = new PageInfo(list);
		return ChdJson.toJson(list, page.getTotal());

	}

	@Override
	public String queryBillDetailGrid(Map<String, Object> entityMap) throws DataAccessException {
		
		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

		List<Map<String, Object>> list = hrpAssSelectMapper.queryBillDetailGrid(entityMap, rowBounds);

		PageInfo page = new PageInfo(list);

		return ChdJson.toJson(list, page.getTotal());

	}

	@Override
	public String queryAccPayType(Map<String, Object> mapVo) throws DataAccessException {
		RowBounds rowBounds = new RowBounds(0, 20);
		if (mapVo.get("pageSize") != null) {
			rowBounds = new RowBounds(0, (Integer) mapVo.get("pageSize"));
		} else {
			rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(hrpAssSelectMapper.queryAccPayType(mapVo, rowBounds));
	}

	@Override
	public String queryAssCardNoDict(Map<String, Object> mapVo) throws DataAccessException {
		RowBounds rowBounds = new RowBounds(0, 20);
		if (mapVo.get("pageSize") != null) {
			rowBounds = new RowBounds(0, (Integer) mapVo.get("pageSize"));
		} else {
			rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(hrpAssSelectMapper.queryAssCardNoDict(mapVo, rowBounds));
	}

	/**
	 * 维修班组下拉框
	 */
	@Override
	public String queryRepTeam(Map<String, Object> mapVo) {
		RowBounds rowBounds = new RowBounds(0, 20);
		if (mapVo.get("pageSize") != null) {
			rowBounds = new RowBounds(0, (Integer) mapVo.get("pageSize"));
		} else {
			rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(hrpAssSelectMapper.queryRepTeam(mapVo, rowBounds));
	}

	public String queryAssInvArrt(Map<String, Object> mapVo) {
		RowBounds rowBounds = new RowBounds(0, 20);
		if (mapVo.get("pageSize") != null) {
			rowBounds = new RowBounds(0, (Integer) mapVo.get("pageSize"));
		} else {
			rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(hrpAssSelectMapper.queryAssInvArrt(mapVo, rowBounds));
	}

	@Override
	public String queryBackBillDetailGrid(Map<String, Object> entityMap) throws DataAccessException {
		RowBounds rowBounds = new RowBounds(0, 20);
		if (entityMap.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
		} else {
			rowBounds = rowBoundsALL;
		}
		List<Map<String, Object>> list = hrpAssSelectMapper.queryBackBillDetailGrid(entityMap, rowBounds);
		PageInfo page = new PageInfo(list);
		return ChdJson.toJson(list, page.getTotal());
	}

	@Override
	public String queryAssCardSpecial(Map<String, Object> entityMap) {
		
		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");
		
		entityMap.put("user_id", SessionManager.getUserId());

		RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

		List<Map<String, Object>> list = hrpAssSelectMapper.queryAssCardSpecial(entityMap, rowBounds);

		PageInfo page = new PageInfo(list);

		return ChdJson.toJson(list, page.getTotal());

	}

	@Override
	public String queryAssCardGeneral(Map<String, Object> entityMap) {

		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");
		entityMap.put("user_id", SessionManager.getUserId());

		RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
		List<Map<String, Object>> list = hrpAssSelectMapper.queryAssCardGeneral(entityMap, rowBounds);
		PageInfo page = new PageInfo(list);
		return ChdJson.toJson(list, page.getTotal());

	}

	@Override
	public String queryAssCardHouse(Map<String, Object> entityMap) {

		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
		List<Map<String, Object>> list = hrpAssSelectMapper.queryAssCardHouse(entityMap, rowBounds);
		PageInfo page = new PageInfo(list);
		return ChdJson.toJson(list, page.getTotal());

	}

	@Override
	public String queryAssCardInassets(Map<String, Object> entityMap) {

		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");
		entityMap.put("user_id", SessionManager.getUserId());

		RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
		List<Map<String, Object>> list = hrpAssSelectMapper.queryAssCardInassets(entityMap, rowBounds);
		PageInfo page = new PageInfo(list);
		return ChdJson.toJson(list, page.getTotal());

	}

	@Override
	public String queryAssCardOther(Map<String, Object> entityMap) {

		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");
		entityMap.put("user_id", SessionManager.getUserId());

		RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
		List<Map<String, Object>> list = hrpAssSelectMapper.queryAssCardOther(entityMap, rowBounds);
		PageInfo page = new PageInfo(list);
		return ChdJson.toJson(list, page.getTotal());

	}

	@Override
	public String queryAssCardLand(Map<String, Object> entityMap) {

		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
		List<Map<String, Object>> list = hrpAssSelectMapper.queryAssCardLand(entityMap, rowBounds);
		PageInfo page = new PageInfo(list);
		return ChdJson.toJson(list, page.getTotal());

	}

	@Override
	public String querySupTypeDict(Map<String, Object> mapVo) {
		RowBounds rowBounds = new RowBounds(0, 20);
		if (mapVo.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(mapVo.get("pageSize").toString()));
		} else {
			rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(hrpAssSelectMapper.querySupTypeDict(mapVo, rowBounds));
	}

	/**
	 * 资产分类快速查询
	 */
	@Override
	public String queryAssFinaDictTree(Map<String, Object> entityMap) {
		RowBounds rowBounds = new RowBounds(0, 20);
		if (entityMap.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
		} else {
			rowBounds = rowBoundsALL;
		}
		List<Map<String, Object>> list = hrpAssSelectMapper.queryAssFinaDictTree(entityMap, rowBounds);

		return JSON.toJSONString(list);
	}

	/**
	 * 财务分类上级下拉框(cjc)
	 */
	@Override
	public String queryMatFinaTypes(Map<String, Object> entityMap) throws DataAccessException {
		RowBounds rowBounds = new RowBounds(0, 20);
		if (entityMap.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
		} else {
			rowBounds = rowBoundsALL;
		}
		List<Map<String, Object>> list = hrpAssSelectMapper.queryMatFinaTypes(entityMap, rowBounds);
		return JSON.toJSONString(list);
	}

	/**
	 * 资产分类快速查询(cjc)
	 */
	@Override
	public String queryAssTypeDictTree(Map<String, Object> entityMap) throws DataAccessException {
		RowBounds rowBounds = new RowBounds(0, 20);
		if (entityMap.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
		} else {
			rowBounds = rowBoundsALL;
		}
		List<Map<String, Object>> list = hrpAssSelectMapper.queryAssTypeDictTree(entityMap, rowBounds);

		return JSON.toJSONString(list);
	}

	@Override
	public String queryAssGBDict(Map<String, Object> entityMap) throws DataAccessException {
		RowBounds rowBounds = new RowBounds(0, 20);
		if (entityMap.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
		} else {
			rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(hrpAssSelectMapper.queryAssGBDict(entityMap, rowBounds));
	}
	/**
	 * 维修申请资产卡片下拉框表单
	 */
	@Override
	public String queryAssCardNoDictTable(Map<String, Object> entityMap)
			throws DataAccessException {
		
		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

		List<AssNoDict> list = hrpAssSelectMapper.queryAssCardNoDictTable(entityMap, rowBounds);

		PageInfo page = new PageInfo(list);

		return ChdJson.toJson(list, page.getTotal());
	}

	@Override
	public String queryDeptKindDict(Map<String, Object> mapVo) throws DataAccessException {
		RowBounds rowBounds = new RowBounds(0, 20);
		if (mapVo.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(mapVo.get("pageSize").toString()));
		} else {
			rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(hrpAssSelectMapper.queryDeptKindDict(mapVo, rowBounds));
	}

	@Override
	public String querySuperLocationSelect(Map<String, Object> mapVo) {
		
		return JSON.toJSONString(hrpAssSelectMapper.querySuperLocationSelect(mapVo));
	}
	@Override
	public String queryNotExistsLocationSelect(Map<String, Object> mapVo) {
		
		return JSON.toJSONString(hrpAssSelectMapper.queryNotExistsLocationSelect(mapVo));
	}

	@Override
	public String querySuperFaultTypeSelect(Map<String, Object> mapVo) {
		// TODO Auto-generated method stub
		return JSON.toJSONString(hrpAssSelectMapper.querySuperFaultTypeSelect(mapVo));
	}

	@Override
	public String queryAssCardNoDictSelect(Map<String, Object> mapVo) {
		

		List<Map<String,Object>> list = hrpAssSelectMapper.queryAssCardNoDictSelect(mapVo);


		return JSON.toJSONString(list);
	}

	@Override
	public String queryAssMaintainItem(Map<String, Object> entityMap) {
		RowBounds rowBounds = new RowBounds(0, 20);
		if (entityMap.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
		} else {
			rowBounds = rowBoundsALL;
		}
		List<AssMaintainItemDict> list = hrpAssSelectMapper.queryAssMaintainItem(entityMap, rowBounds);
		PageInfo page = new PageInfo(list);
		return ChdJson.toJson(list, page.getTotal());
	}
	@Override
	public String queryAssStoreDept(Map<String, Object> entityMap) {
		
		return JSON.toJSONString(hrpAssSelectMapper.queryAssStoreDept(entityMap));
	}

	@Override
	public String queryDeptDictAll(Map<String, Object> mapVo) {
		return JSON.toJSONString(hrpAssSelectMapper.queryDeptDictAll(mapVo));
	}

	@Override
	public String queryHosSupDictNo(Map<String, Object> entityMap) {
		RowBounds rowBounds = new RowBounds(0, 20);
		if (entityMap.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
		} else {
			rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(hrpAssSelectMapper.queryHosSupDictNo(entityMap, rowBounds));
	}

	@Override
	public String queryHosFacDictNo(Map<String, Object> entityMap) {
		RowBounds rowBounds = new RowBounds(0, 20);
		if (entityMap.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
		} else {
			rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(hrpAssSelectMapper.queryHosFacDictNo(entityMap, rowBounds));
	}

	/**
	 * 资产凭证下拉框
	 */
	@Override
	public String queryCertNo(Map<String, Object> entityMap) {
		RowBounds rowBounds = new RowBounds(0, 20);
		if (entityMap.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
		} else {
			rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(hrpAssSelectMapper.queryCertNo(entityMap, rowBounds));
	}

	@Override
	public String queryAssMeasureKingDict(Map<String, Object> mapVo) {
		RowBounds rowBounds = new RowBounds(0, 20);
		if (mapVo.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(mapVo.get("pageSize").toString()));
		} else {
			rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(hrpAssSelectMapper.queryAssMeasureKingDict(mapVo, rowBounds));
	}

	@Override
	public String queryAccEmpAttr(Map<String, Object> mapVo) {
		return JSON.toJSONString(hrpAssSelectMapper.queryAccEmpAttr(mapVo));
	}

	@Override
	public String queryReportBusType(Map<String, Object> mapVo) {
		return JSON.toJSONString(hrpAssSelectMapper.queryReportBusType(mapVo));
	}
	
	@Override
	public String queryAssTypeSixEight(Map<String, Object> mapVo) throws DataAccessException {
		RowBounds rowBounds = new RowBounds(0, 20);
		if (mapVo.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(mapVo.get("pageSize").toString()));
		} else {
			rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(hrpAssSelectMapper.queryAssTypeSixEight(mapVo, rowBounds));
	}
	
	/**
	 * 	
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	@Override
	public String queryAssBudgTable(Map<String, Object> entityMap) throws DataAccessException {

		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

		List<Map<String,Object>> list = hrpAssSelectMapper.queryAssBudgTable(entityMap, rowBounds);

		PageInfo page = new PageInfo(list);

		return ChdJson.toJsonLower(list, page.getTotal());

	}

	@Override
	public String queryPactMainFkht(Map<String, Object> mapVo) throws DataAccessException {
		
		return JSON.toJSONString(hrpAssSelectMapper.queryPactMainFkht(mapVo));
	}

}
