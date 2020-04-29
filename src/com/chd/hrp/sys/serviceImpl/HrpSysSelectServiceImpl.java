/**
 * @Copyright: Copyright (c) 2015-2-14
 * @Company: 鏅烘収浜戝悍锛堝寳浜級鏁版嵁绉戞妧鏈夐檺鍏徃
 */
package com.chd.hrp.sys.serviceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.chd.base.startup.LoadMenuFile;
import com.chd.base.util.ChdJson;
import com.chd.hrp.sys.dao.HrpSysSelectMapper;
import com.chd.hrp.sys.entity.HrpSysSelect;
import com.chd.hrp.sys.service.HrpSysSelectService;

/**
 * @Title. @Description.
 * 
 * @Author: LiuYingDuo
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Service("hrpSysSelectService")
public class HrpSysSelectServiceImpl implements HrpSysSelectService {

	private static Logger logger = Logger.getLogger(EmpServiceImpl.class);

	@Resource(name = "hrpSysSelectMapper")
	private final HrpSysSelectMapper hrpSysSelectMapper = null;

	// 鍚庢湡閫氳繃閰嶇疆鏂囦欢璇诲彇
	RowBounds rowBoundsALL = new RowBounds(0, 20);

	/**
	 * @Description 鑱屽伐涓嬫媺妗�
	 * @param map
	 *            map
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String queryEmpDict(Map<String, Object> map) throws DataAccessException {
		RowBounds rowBounds = new RowBounds(0, 20);
		if (map.get("pageSize") != null) {
			rowBounds = new RowBounds(0, (Integer) map.get("pageSize"));
		} else {
			rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(hrpSysSelectMapper.queryEmpDict(map, rowBounds));
	}
	
	@Override
	public String queryEmpDictDept(Map<String, Object> map) throws DataAccessException {
		RowBounds rowBounds = new RowBounds(0, 20);
		if (map.get("pageSize") != null) {
			rowBounds = new RowBounds(0, (Integer) map.get("pageSize"));
		} else {
			rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(hrpSysSelectMapper.queryEmpDictDept(map, rowBounds));
	}
	
	@Override
	public String queryEmpDictForCode(Map<String, Object> map) throws DataAccessException {
		
		RowBounds rowBounds = new RowBounds(0, 20);
		
		if (map.get("pageSize") != null) {
			
			rowBounds = new RowBounds(0, (Integer) map.get("pageSize"));
		
		} else {
			
			rowBounds = rowBoundsALL;
		
		}
		
		return JSON.toJSONString(hrpSysSelectMapper.queryEmpDictForCode(map, rowBounds));
	}

	@Override
	public String queryHosLevelDict(Map<String, Object> entityMap) throws DataAccessException {
		RowBounds rowBounds = new RowBounds(0, 20);
		if (entityMap.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
		} else {
			rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(hrpSysSelectMapper.queryHosLevelDict(entityMap, rowBounds));
	}

	@Override
	public String queryHosTypeDict(Map<String, Object> entityMap) throws DataAccessException {
		RowBounds rowBounds = new RowBounds(0, 20);
		if (entityMap.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
		} else {
			rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(hrpSysSelectMapper.queryHosTypeDict(entityMap, rowBounds));
	}

	@Override
	public String queryCountriesDict(Map<String, Object> entityMap) throws DataAccessException {
		RowBounds rowBounds = new RowBounds(0, 20);
		if (entityMap.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
		} else {
			rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(hrpSysSelectMapper.queryCountriesDict(entityMap, rowBounds));
	}

	@Override
	public String queryRegionDict(Map<String, Object> entityMap) throws DataAccessException {
		RowBounds rowBounds = new RowBounds(0, 20);
		if (entityMap.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
		} else {
			rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(hrpSysSelectMapper.queryRegionDict(entityMap, rowBounds));
	}

	@Override
	public String queryPoliticalDict(Map<String, Object> entityMap) throws DataAccessException {
		RowBounds rowBounds = new RowBounds(0, 20);
		if (entityMap.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
		} else {
			rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(hrpSysSelectMapper.queryPoliticalDict(entityMap, rowBounds));
	}

	@Override
	public String queryUserDict(Map<String, Object> entityMap) throws DataAccessException {
		
		return JSON.toJSONString(hrpSysSelectMapper.queryUserDict(entityMap));
	}
	
	@Override
	public String queryUserDictBySys(Map<String, Object> entityMap) throws DataAccessException {
		
		return JSON.toJSONString(hrpSysSelectMapper.queryUserDictBySys(entityMap));
	}
	
	//璁拌处浜�
	@Override
	public String queryAccUserDict(Map<String, Object> entityMap) throws DataAccessException {
		return JSON.toJSONString(hrpSysSelectMapper.queryAccUserDict(entityMap));
	}
	
	//瀹℃牳浜�
	@Override
	public String queryAuditUserDict(Map<String, Object> entityMap) throws DataAccessException {
		return JSON.toJSONString(hrpSysSelectMapper.queryAuditUserDict(entityMap));
	}
	
	//鍒跺崟浜�
	@Override
	public String queryCreateUserDict(Map<String, Object> entityMap) throws DataAccessException {
		return JSON.toJSONString(hrpSysSelectMapper.queryCreateUserDict(entityMap));
	}
	
	//鍑虹撼浜�
	@Override
	public String queryCashUserDict(Map<String, Object> entityMap) throws DataAccessException {
		return JSON.toJSONString(hrpSysSelectMapper.queryCashUserDict(entityMap));
	}


	@Override
	public String queryHosInfoDict(Map<String, Object> entityMap) throws DataAccessException {
		RowBounds rowBounds = new RowBounds(0, 20);
		if (entityMap.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
		} else {
			rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(hrpSysSelectMapper.queryHosInfoDict(entityMap, rowBounds));
	}

	@Override
	public String queryHosInfo(Map<String, Object> entityMap) throws DataAccessException {
		RowBounds rowBounds = new RowBounds(0, 20);
		if (entityMap.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
		} else {
			rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(hrpSysSelectMapper.queryHosInfo(entityMap, rowBounds));
	}

	@Override
	public String queryCopyCodeDict(Map<String, Object> entityMap) throws DataAccessException {
		
		return JSON.toJSONString(hrpSysSelectMapper.queryCopyCodeDict(entityMap));
	}

	@Override
	public String queryRoleDict(Map<String, Object> entityMap) throws DataAccessException {
		
		return JSON.toJSONString(hrpSysSelectMapper.queryRoleDict(entityMap));
	}

	@Override
	public String queryModDict(Map<String, Object> entityMap) throws DataAccessException {
		RowBounds rowBounds = new RowBounds(0, 20);
		if (entityMap.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
		} else {
			rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(hrpSysSelectMapper.queryModDict(entityMap, rowBounds));
	}

	@Override
	public String queryDeptKindDict(Map<String, Object> entityMap) throws DataAccessException {
		RowBounds rowBounds = new RowBounds(0, 20);
		if (entityMap.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
		} else {
			rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(hrpSysSelectMapper.queryDeptKindDict(entityMap, rowBounds));
	}

	@Override
	public String queryDeptDict(Map<String, Object> entityMap) throws DataAccessException {
		RowBounds rowBounds = new RowBounds(0, 20);
		if (entityMap.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
		} else {
			rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(hrpSysSelectMapper.queryDeptDict(entityMap, rowBounds));
	}
	@Override
	public String queryDeptDictLast(Map<String, Object> entityMap) throws DataAccessException {
		RowBounds rowBounds = new RowBounds(0, 20);
		if (entityMap.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
		} else {
			rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(hrpSysSelectMapper.queryDeptDictLast(entityMap, rowBounds));
	}

	@Override
	public String querySchoolDict(Map<String, Object> entityMap) throws DataAccessException {
		RowBounds rowBounds = new RowBounds(0, 20);
		if (entityMap.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
		} else {
			rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(hrpSysSelectMapper.querySchoolDict(entityMap, rowBounds));
	}

	@Override
	public String queryStationDict(Map<String, Object> entityMap) throws DataAccessException {
		RowBounds rowBounds = new RowBounds(0, 20);
		if (entityMap.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
		} else {
			rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(hrpSysSelectMapper.queryStationDict(entityMap, rowBounds));
	}

	@Override
	public String queryDutyDict(Map<String, Object> entityMap) throws DataAccessException {
		RowBounds rowBounds = new RowBounds(0, 20);
		if (entityMap.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
		} else {
			rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(hrpSysSelectMapper.queryDutyDict(entityMap, rowBounds));
	}

	@Override
	public String queryEmpKindDict(Map<String, Object> entityMap) throws DataAccessException {
		RowBounds rowBounds = new RowBounds(0, 20);
		if (entityMap.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
		} else {
			rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(hrpSysSelectMapper.queryEmpKindDict(entityMap, rowBounds));
	}

	@Override
	public String queryProjTypeDict(Map<String, Object> entityMap) throws DataAccessException {
		RowBounds rowBounds = new RowBounds(0, 20);
		if (entityMap.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
		} else {
			rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(hrpSysSelectMapper.queryProjTypeDict(entityMap, rowBounds));
	}

	@Override
	public String queryProjLevelDict(Map<String, Object> entityMap) throws DataAccessException {
		RowBounds rowBounds = new RowBounds(0, 20);
		if (entityMap.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
		} else {
			rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(hrpSysSelectMapper.queryProjLevelDict(entityMap, rowBounds));
	}

	@Override
	public String queryProjUseDict(Map<String, Object> entityMap) throws DataAccessException {
		RowBounds rowBounds = new RowBounds(0, 20);
		if (entityMap.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
		} else {
			rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(hrpSysSelectMapper.queryProjUseDict(entityMap, rowBounds));
	}

	@Override
	public String queryProjDict(Map<String, Object> entityMap) throws DataAccessException {
		RowBounds rowBounds = new RowBounds(0, 20);
		if (entityMap.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
		} else {
			rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(hrpSysSelectMapper.queryProjDict(entityMap, rowBounds));
	}

	@Override
	public String querySupDict(Map<String, Object> entityMap) throws DataAccessException {
		RowBounds rowBounds = new RowBounds(0, 20);
		if (entityMap.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
		} else {
			rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(hrpSysSelectMapper.querySupDict(entityMap, rowBounds));
	}

	@Override
	public String querySupTypeDict(Map<String, Object> entityMap) throws DataAccessException {
		RowBounds rowBounds = new RowBounds(0, 20);
		if (entityMap.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
		} else {
			rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(hrpSysSelectMapper.querySupTypeDict(entityMap, rowBounds));
	}

	@Override
	public String queryCusDict(Map<String, Object> entityMap) throws DataAccessException {
		RowBounds rowBounds = new RowBounds(0, 20);
		if (entityMap.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
		} else {
			rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(hrpSysSelectMapper.queryCusDict(entityMap, rowBounds));
	}

	@Override
	public String queryCusTypeDict(Map<String, Object> entityMap) throws DataAccessException {
		RowBounds rowBounds = new RowBounds(0, 20);
		if (entityMap.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
		} else {
			rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(hrpSysSelectMapper.queryCusTypeDict(entityMap, rowBounds));
	}

	@Override
	public String queryFacDict(Map<String, Object> entityMap) throws DataAccessException {
		RowBounds rowBounds = new RowBounds(0, 20);
		if (entityMap.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
		} else {
			rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(hrpSysSelectMapper.queryFacDict(entityMap, rowBounds));
	}

	@Override
	public String queryFacTypeDict(Map<String, Object> entityMap) throws DataAccessException {
		RowBounds rowBounds = new RowBounds(0, 20);
		if (entityMap.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
		} else {
			rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(hrpSysSelectMapper.queryFacTypeDict(entityMap, rowBounds));
	}

	@Override
	public String queryStoreDict(Map<String, Object> entityMap) throws DataAccessException {
		RowBounds rowBounds = new RowBounds(0, 20);
		if (entityMap.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
		} else {
			rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(hrpSysSelectMapper.queryStoreDict(entityMap, rowBounds));
	}

	@Override
	public String queryStoreTypeDict(Map<String, Object> entityMap) throws DataAccessException {
		RowBounds rowBounds = new RowBounds(0, 20);
		if (entityMap.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
		} else {
			rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(hrpSysSelectMapper.queryStoreTypeDict(entityMap, rowBounds));
	}

	@Override
	public String queryUnitDict(Map<String, Object> entityMap) throws DataAccessException {
		RowBounds rowBounds = new RowBounds(0, 20);
		if (entityMap.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
		} else {
			rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(hrpSysSelectMapper.queryUnitDict(entityMap, rowBounds));
	}

	@Override
	public String queryPatientTypeDict(Map<String, Object> entityMap) throws DataAccessException {
		RowBounds rowBounds = new RowBounds(0, 20);
		if (entityMap.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
		} else {
			rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(hrpSysSelectMapper.queryPatientTypeDict(entityMap, rowBounds));
	}

	@Override
	public String querySourceDict(Map<String, Object> entityMap) throws DataAccessException {
		RowBounds rowBounds = new RowBounds(0, 20);
		if (entityMap.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
		} else {
			rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(hrpSysSelectMapper.querySourceDict(entityMap, rowBounds));
	}

	/**
	 * @Description 閮ㄩ棬涓嬫媺妗�
	 * @param map
	 *            map
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String queryDept(Map<String, Object> entityMap) throws DataAccessException {
		RowBounds rowBounds = new RowBounds(0, 20);
		if (entityMap.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
		} else {
			rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(hrpSysSelectMapper.queryDept(entityMap, rowBounds));
	}

	@Override
	public String queryCopyCodeDictPerm(Map<String, Object> entityMap) throws DataAccessException {
	
		return JSON.toJSONString(hrpSysSelectMapper.queryCopyCodeDictPerm(entityMap));
	}

	@Override
	public String queryModDictPerm(Map<String, Object> entityMap) throws DataAccessException {

		List<HrpSysSelect> list = hrpSysSelectMapper.queryModDictPerm(entityMap);

		List<HrpSysSelect> listperm = new ArrayList<HrpSysSelect>();

		for (int i = 0; i < list.size(); i++) {
			// 閫氳繃License 鍒ゆ柇鐢ㄦ埛鎺堟潈
			if (LoadMenuFile.getModMap().get(list.get(i).getId()) != null) {
				listperm.add(list.get(i));
			}
		}

		return JSON.toJSONString(listperm);
	}

	@Override
	public String queryHosInfoDictPerm(Map<String, Object> entityMap) throws DataAccessException {
		
		return JSON.toJSONString(hrpSysSelectMapper.queryHosInfoDictPerm(entityMap));
	}

	@Override
	public String queryHosNature(Map<String, Object> entityMap) throws DataAccessException {
		RowBounds rowBounds = new RowBounds(0, 20);
		if (entityMap.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
		} else {
			rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(hrpSysSelectMapper.queryHosNature(entityMap, rowBounds));
	}

	@Override
	public String queryEmp(Map<String, Object> map) throws DataAccessException {
//		RowBounds rowBounds = new RowBounds(0, 20);
//		if (map.get("pageSize") != null) {
//			rowBounds = new RowBounds(0, (Integer) map.get("pageSize"));
//		} else {
//			rowBounds = rowBoundsALL;
//		}
		return JSON.toJSONString(hrpSysSelectMapper.queryEmp(map));
	}
	
	@Override
	public String queryEmpNew(Map<String, Object> map) throws DataAccessException {
		RowBounds rowBounds = new RowBounds(0, 20);
		if (map.get("pageSize") != null) {
			rowBounds = new RowBounds(0, (Integer) map.get("pageSize"));
		} else {
			rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(hrpSysSelectMapper.queryEmpNew(map, rowBounds));
	}

	@Override
	public String queryModDictAdminPerm(Map<String, Object> map) throws DataAccessException {
//		RowBounds rowBounds = new RowBounds(0, 20);
//		if (map.get("pageSize") != null) {
//			rowBounds = new RowBounds(0, (Integer) map.get("pageSize"));
//		} else {
//			rowBounds = rowBoundsALL;
//		}

		List<HrpSysSelect> list = hrpSysSelectMapper.queryModDictAdminPerm(map);
		List<HrpSysSelect> listperm = new ArrayList<HrpSysSelect>();

		for (int i = 0; i < list.size(); i++) {
			// 閫氳繃License 鍒ゆ柇鐢ㄦ埛鎺堟潈
			if (LoadMenuFile.getModMap().get(list.get(i).getId()) != null) {
				listperm.add(list.get(i));
			}
		}

		return JSON.toJSONString(listperm);
	}
	

	//加载项目
	@Override
	public String queryProjDictDict(Map<String, Object> entityMap) throws DataAccessException {
		RowBounds rowBounds = new RowBounds(0, 20);
		if (entityMap.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
		} else {
			rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(hrpSysSelectMapper.queryProjDictDictDet(entityMap, rowBounds));
	}
	
	//加载项目明细
	@Override
	public String queryProjDictDictDet(Map<String, Object> entityMap) throws DataAccessException {
		RowBounds rowBounds = new RowBounds(0, 20);
		if (entityMap.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
		} else {
			rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(hrpSysSelectMapper.queryProjDictDictDet(entityMap, rowBounds));
	}

	//加载仓库
	@Override
	public String queryStoreDictDict(Map<String, Object> entityMap) throws DataAccessException {
		RowBounds rowBounds = new RowBounds(0, 20);
		if (entityMap.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
		} else {
			rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(hrpSysSelectMapper.queryStoreDictDict(entityMap, rowBounds));
	}
	
	//加载虚仓
	@Override
	public String querySetDictDict(Map<String, Object> entityMap) throws DataAccessException {
		RowBounds rowBounds = new RowBounds(0, 20);
		if (entityMap.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
		} else {
			rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(hrpSysSelectMapper.querySetDictDict(entityMap, rowBounds));
	}

	//加载供应商
	@Override
	public String querySupDictDict(Map<String, Object> entityMap) throws DataAccessException {
		RowBounds rowBounds = new RowBounds(0, 20);
		if (entityMap.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
		} else {
			rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(hrpSysSelectMapper.querySupDictDict(entityMap, rowBounds));
	}

	@Override
	public String queryPermData(Map<String, Object> entityMap) throws DataAccessException {
		return JSON.toJSONString(hrpSysSelectMapper.queryPermData(entityMap));
	}

	@Override
	public String queryHosUnitDict(Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		RowBounds rowBounds = new RowBounds(0, 20);
		if (entityMap.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
		} else {
			rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(hrpSysSelectMapper.queryHosUnitDict(entityMap, rowBounds));
	}

	// 鑷姩鍑瘉妯″潡绫诲埆
	@Override
	public String querySysBusiMod(Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
//		RowBounds rowBounds = new RowBounds(0, 20);
//		if (entityMap.get("pageSize") != null) {
//			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
//		} else {
//			rowBounds = rowBoundsALL;
//		}
		return JSON.toJSONString(hrpSysSelectMapper.querySysBusiMod(entityMap));
	}

	@Override
	public String querySysSourceNature(Map<String, Object> entityMap)
			throws DataAccessException {
		
		RowBounds rowBounds = new RowBounds(0, 20);
		
		if (entityMap.get("pageSize") != null) {
			
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
		
		} else {
			
			rowBounds = rowBoundsALL;
		
		}
		
		return JSON.toJSONString(hrpSysSelectMapper.querySysSourceNature(entityMap, rowBounds));
	}

	//部门类型
	@Override
	public String queryAccDeptType(Map<String, Object> entityMap)
			throws DataAccessException {
		
		RowBounds rowBounds = new RowBounds(0, 20); 
		if (entityMap.get("pageSize") != null) { 
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
		
		} else { 
			rowBounds = rowBoundsALL; 
		} 
		return JSON.toJSONString(hrpSysSelectMapper.queryAccDeptType(entityMap, rowBounds));
	}
	
	//支出性质
	@Override
	public String queryAccDeptOut(Map<String, Object> entityMap)
			throws DataAccessException {
		
		RowBounds rowBounds = new RowBounds(0, 20); 
		if (entityMap.get("pageSize") != null) { 
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
		
		} else { 
			rowBounds = rowBoundsALL; 
		} 
		return JSON.toJSONString(hrpSysSelectMapper.queryAccDeptOut(entityMap, rowBounds));
	}
	/**
	 * @Description 
	 * 科目类别<BR> 查询AccSubjType分页
	 * @param  mapVo RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String queryAccSubjType(Map<String,Object> mapVo) throws DataAccessException{
		return ChdJson.toJson(hrpSysSelectMapper.queryAccSubjType(mapVo));
	}
	/**
	 * 查询科目级次
	 * @param mapVo
	 * @return
	 */
	@Override
	public String querySubjLevel(Map<String, Object> mapVo) {
		return JSON.toJSONString(hrpSysSelectMapper.querySubjLevel(mapVo));
	}
	/**
	 * 查询 凭证类型
	 */
	@Override
	public String queryVouchType(Map<String, Object> entityMap) throws DataAccessException {
		return JSON.toJSONString(hrpSysSelectMapper.queryVouchType(entityMap));
	}
	/**
	 * 查询币种
	 * 
	 */
	@Override
	public String queryCur(Map<String, Object> entityMap) throws DataAccessException {
		return JSON.toJSONString(hrpSysSelectMapper.queryCur(entityMap));
	}
	/**
	 * 查询币种集团专用
	 * 
	 */
	@Override
	public String queryGroupCur(Map<String, Object> entityMap) throws DataAccessException {
		return JSON.toJSONString(hrpSysSelectMapper.queryGroupCur(entityMap));
	}
	/**
	 * 核算类型
	 * @param mapVo
	 * @return
	 */
	@Override
	public String queryCheckType(Map<String, Object> entityMap) throws DataAccessException {
		return JSON.toJSONString(hrpSysSelectMapper.queryCheckType(entityMap));
	}
	/**
	 * 科目性质
	 */
	@Override
	public String querySubjNature(Map<String, Object> entityMap) throws DataAccessException {
		return JSON.toJSONString(hrpSysSelectMapper.querySubjNature(entityMap));
	}
	/**
	 * 支出性质-现金流量项目
	 * @param mapVo
	 * @return
	 */
	@Override
	public String queryDeptOutSelect(Map<String, Object> mapVo) {
		return JSON.toJSONString(hrpSysSelectMapper.queryDeptOutSelect(mapVo));
	}
	/**
	 * 科目类别下拉框
	 * @param mapVo
	 * @return
	 */
	@Override
	public String querySubjTypeForSelect(Map<String, Object> mapVo) {
		return JSON.toJSONString(hrpSysSelectMapper.querySubjTypeForSelect(mapVo));
	}
	@Override
	public String queryDeptType(Map<String, Object> entityMap) throws DataAccessException {
		return JSON.toJSONString(hrpSysSelectMapper.queryDeptType(entityMap));
	}
	@Override
	public String queryDeptNatur(Map<String, Object> mapVo) {
		return JSON.toJSONString(hrpSysSelectMapper.queryDeptNatur(mapVo));
	}
	@Override
	public String queryEmpPay(Map<String, Object> mapVo) {
		return JSON.toJSONString(hrpSysSelectMapper.queryEmpPay(mapVo));
	}
	//科室字典(根据参数决定是否按权限)
	@Override
	public String queryHosDept(Map<String, Object> entityMap) throws DataAccessException {
		
		RowBounds rowBounds = new RowBounds(0, 20);

		if (entityMap.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
		} else {
			rowBounds = rowBoundsALL;
		}

		return JSON.toJSONString(hrpSysSelectMapper.queryHosDept(entityMap, rowBounds));
	}

	//查询自定义辅助核算字典对应表
	public String queryAccBusiZCheck(Map<String, Object> mapVo) throws DataAccessException{
		return JSON.toJSONString(hrpSysSelectMapper.queryAccBusiZCheck(mapVo));
	}
	
	@Override
	public String queryHosNatureDict(Map<String, Object> entityMap) throws DataAccessException {
		
		RowBounds rowBounds = new RowBounds(0, 20);
		
		if (entityMap.get("pageSize") != null) {
			
			rowBounds = new RowBounds(0, (Integer) entityMap.get("pageSize"));
			
		}else{
			
			rowBounds = rowBoundsALL;
			 
		}
		
		return JSON.toJSONString(hrpSysSelectMapper.queryHosNatureDict(entityMap, rowBounds));
		
	}
	@Override
	public String queryCopyDict(Map<String, Object> entityMap) throws DataAccessException {
		
		RowBounds rowBounds = new RowBounds(0, 20);
		
		if (entityMap.get("pageSize") != null) {
			
			rowBounds = new RowBounds(0, (Integer) entityMap.get("pageSize"));
			
		}else{
			
			rowBounds = rowBoundsALL;
			 
		}
		
		return JSON.toJSONString(hrpSysSelectMapper.queryCopyDict(entityMap, rowBounds));
		
	}

	@Override
	public String queryAcctYearDict(Map<String, Object> entityMap) throws DataAccessException {
		
		RowBounds rowBounds = new RowBounds(0, 20);
		
		if (entityMap.get("pageSize") != null) {
			
			rowBounds = new RowBounds(0, (Integer) entityMap.get("pageSize"));
			
		}else{
			
			rowBounds = rowBoundsALL;
			 
		}
		
		return JSON.toJSONString(hrpSysSelectMapper.queryAcctYearDict(entityMap, rowBounds));
		
	}

	@Override
	public String queryHosCopyDict(Map<String, Object> entityMap) throws DataAccessException {
		
		return JSON.toJSONString(hrpSysSelectMapper.queryHosCopyDict(entityMap));
	}
	
	@Override
	public String queryAccYearDict(Map<String, Object> entityMap) throws DataAccessException {
		RowBounds rowBounds = new RowBounds(0, 20);
		if (entityMap.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
		} else {
			rowBounds = rowBoundsALL;
		}
		return JSON.toJSONString(hrpSysSelectMapper.queryAccYearDict(entityMap, rowBounds));
	}
	//集团化管理  集团部门下拉框  用于部门辅助账
	@Override
	public String queryDeptByRela(Map<String, Object> entityMap) throws DataAccessException {
		RowBounds rowBounds = new RowBounds(0, 50);
		if (entityMap.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
		} else {
			rowBounds = rowBounds;
		}
		return JSON.toJSONString(hrpSysSelectMapper.queryDeptByRela(entityMap, rowBounds));
	}
	//集团化管理  集团职工下拉框  用于职工辅助账
	@Override
	public String queryEmpByRela(Map<String, Object> entityMap) throws DataAccessException {
		RowBounds rowBounds = new RowBounds(0, 50);
		if (entityMap.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
		} else {
			rowBounds = rowBounds;
		}
		return JSON.toJSONString(hrpSysSelectMapper.queryEmpByRela(entityMap, rowBounds));
	}
	//集团化管理  集团项目下拉框  用于项目辅助账
	@Override
	public String queryProjDictDictByRela(Map<String, Object> entityMap) throws DataAccessException {
		RowBounds rowBounds = new RowBounds(0, 50);
		if (entityMap.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
		} else {
			rowBounds = rowBounds;
		}
		return JSON.toJSONString(hrpSysSelectMapper.queryProjDictDictByRela(entityMap, rowBounds));
	}
	
	//集团化管理  集团供应商下拉框  用于供应商辅助账
	@Override
	public String querySupDictDictByRela(Map<String, Object> entityMap) throws DataAccessException {
		RowBounds rowBounds = new RowBounds(0, 50);
		if (entityMap.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
		} else {
			rowBounds = rowBounds;
		}
		return JSON.toJSONString(hrpSysSelectMapper.querySupDictDictByRela(entityMap, rowBounds));
	}
	//集团化管理  集团客户下拉框  用于客户辅助账
	@Override
	public String queryCusDictByRela(Map<String, Object> entityMap) throws DataAccessException {
		RowBounds rowBounds = new RowBounds(0, 50);
		if (entityMap.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
		} else {
			rowBounds = rowBounds;
		}
		return JSON.toJSONString(hrpSysSelectMapper.queryCusDictByRela(entityMap, rowBounds));
	}

	@Override
	public String querySysHisDeptDict(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		RowBounds rowBounds = new RowBounds(0, 50);
		if (entityMap.get("pageSize") != null) {
			rowBounds = new RowBounds(0, Integer.parseInt(entityMap.get("pageSize").toString()));
		} else {
			rowBounds = rowBounds;
		}
		return JSON.toJSONString(hrpSysSelectMapper.querySysHisDeptDict(entityMap, rowBounds));
	}

}
