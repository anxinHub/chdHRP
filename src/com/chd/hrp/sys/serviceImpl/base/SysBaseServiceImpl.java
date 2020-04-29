/**
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */

package com.chd.hrp.sys.serviceImpl.base;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.nutz.lang.Strings;
import org.nutz.mapl.Mapl;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.MyConfig;
import com.chd.base.SessionManager;
import com.chd.hrp.acc.dao.AccParaMapper;
import com.chd.hrp.acc.dao.AccYearMonthMapper;
import com.chd.hrp.acc.entity.AccPara;
import com.chd.hrp.acc.entity.AccYearMonth;
import com.chd.hrp.sys.dao.ModStartMapper;
import com.chd.hrp.sys.dao.base.SysBaseMapper;
import com.chd.hrp.sys.dao.notGeneral.SysFunUtilMapper;
import com.chd.hrp.sys.entity.ModStart;
import com.chd.hrp.sys.service.base.SysBaseService;

/**
 * @Description: 资产公用服务类
 * @Author: bell
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Service("sysBaseService")
public class SysBaseServiceImpl implements SysBaseService {

	private static Logger logger = Logger.getLogger(SysBaseServiceImpl.class);

	// 引入DAO操作
	@Resource(name = "sysBaseMapper")
	private final SysBaseMapper sysBaseMapper = null;

	// 引入DAO操作
	@Resource(name = "sysFunUtilMapper")
	private final SysFunUtilMapper sysFunUtilMapper = null;

	// 引入DAO操作
	@Resource(name = "accParaMapper")
	private final AccParaMapper accParaMapper = null;
	// 引入DAO操作
	@Resource(name = "modStartMapper")
	private final ModStartMapper modStartMapper = null;
	
	@Resource(name = "accYearMonthMapper")
	private final AccYearMonthMapper accYearMonthMapper = null;

	@Override
	public Map<Object, Object> getHosRules(Map<String, Object> entityMap) throws DataAccessException {
		Map<String, Object> rules = sysBaseMapper.getHosRules(entityMap);
		Map<Object, Object> rules_map = new HashMap<Object, Object>();
		Map<Object, Object> rules_type_level = new HashMap<Object, Object>();
		Map<Object, Object> rules_level_length = new HashMap<Object, Object>();
		StringBuilder rules_view = new StringBuilder();
		int count = 0;
		if(rules==null){
			return rules_map;
		}
		rules_map.put("max_level", rules.get("max_level").toString());
		rules_map.put("max_length", rules.get("max_length").toString());
		
		if (Strings.isBlank(rules.get("level1").toString()) || Strings.equals(rules.get("level1").toString(), "0")) {
			rules_map.put("rules_view", rules_view);
			return rules_map;
		} else {
			rules_view.append("-");
			rules_view.append(rules.get("level1"));

			count = count + Integer.valueOf(rules.get("level1").toString());
			rules_type_level.put(count, 1);
			rules_level_length.put(1, count);

		}
		if (Strings.isBlank(rules.get("level2").toString()) || Strings.equals(rules.get("level2").toString(), "0")) {
			rules_map.put("rules_view", rules_view);
			rules_map.put("rules_type_level", rules_type_level);
			rules_map.put("rules_level_length", rules_level_length);
			return rules_map;
		} else {
			rules_view.append("-");
			rules_view.append(rules.get("level2"));

			count = count + Integer.valueOf(rules.get("level2").toString());
			rules_type_level.put(count, 2);
			rules_level_length.put(2, count);
		}
		if (Strings.isBlank(rules.get("level3").toString()) || Strings.equals(rules.get("level3").toString(), "0")) {
			rules_type_level.put("rules_view", rules_view);
			rules_map.put("rules_type_level", rules_type_level);
			rules_map.put("rules_level_length", rules_level_length);
			return rules_map;
		} else {
			rules_view.append("-");
			rules_view.append(rules.get("level3"));

			count = count + Integer.valueOf(rules.get("level3").toString());
			rules_type_level.put(count, 3);
			rules_level_length.put(3, count);
		}
		if (Strings.isBlank(rules.get("level4").toString()) || Strings.equals(rules.get("level4").toString(), "0")) {
			rules_map.put("rules_view", rules_view);
			rules_map.put("rules_type_level", rules_type_level);
			rules_map.put("rules_level_length", rules_level_length);
			return rules_map;
		} else {
			rules_view.append("-");
			rules_view.append(rules.get("level4"));

			count = count + Integer.valueOf(rules.get("level4").toString());
			rules_type_level.put(count, 4);
			rules_level_length.put(4, count);
		}
		if (Strings.isBlank(rules.get("level5").toString()) || Strings.equals(rules.get("level5").toString(), "0")) {
			rules_map.put("rules_view", rules_view);
			rules_map.put("rules_type_level", rules_type_level);
			rules_map.put("rules_level_length", rules_level_length);
			return rules_map;
		} else {
			rules_view.append("-");
			rules_view.append(rules.get("level5"));

			count = count + Integer.valueOf(rules.get("level5").toString());
			rules_type_level.put(count, 5);
			rules_level_length.put(5, count);
		}
		if (Strings.isBlank(rules.get("level6").toString()) || Strings.equals(rules.get("level6").toString(), "0")) {
			rules_map.put("rules_view", rules_view);
			rules_map.put("rules_type_level", rules_type_level);
			rules_map.put("rules_level_length", rules_level_length);
			return rules_map;
		} else {
			rules_view.append("-");
			rules_view.append(rules.get("level6"));

			count = count + Integer.valueOf(rules.get("level6").toString());
			rules_type_level.put(count, 6);
			rules_level_length.put(6, count);
		}
		if (Strings.isBlank(rules.get("level7").toString()) || Strings.equals(rules.get("level7").toString(), "0")) {
			rules_map.put("rules_view", rules_view);
			rules_map.put("rules_type_level", rules_type_level);
			rules_map.put("rules_level_length", rules_level_length);
			return rules_map;
		} else {
			rules_view.append("-");
			rules_view.append(rules.get("level7"));

			count = count + Integer.valueOf(rules.get("level7").toString());
			rules_type_level.put(count, 7);
			rules_level_length.put(7, count);
		}
		if (Strings.isBlank(rules.get("level8").toString()) || Strings.equals(rules.get("level8").toString(), "0")) {
			rules_map.put("rules_view", rules_view);
			rules_map.put("rules_type_level", rules_type_level);
			rules_map.put("rules_level_length", rules_level_length);
			return rules_map;
		} else {
			rules_view.append("-");
			rules_view.append(rules.get("level8"));

			count = count + Integer.valueOf(rules.get("level8").toString());
			rules_type_level.put(count, 8);
			rules_level_length.put(8, count);
		}
		if (Strings.isBlank(rules.get("level9").toString()) || Strings.equals(rules.get("level9").toString(), "0")) {
			rules_map.put("rules_view", rules_view);
			rules_map.put("rules_type_level", rules_type_level);
			rules_map.put("rules_level_length", rules_level_length);
			return rules_map;
		} else {
			rules_view.append("-");
			rules_view.append(rules.get("level9"));

			count = count + Integer.valueOf(rules.get("level9").toString());
			rules_type_level.put(count, 9);
			rules_level_length.put(9, count);
		}
		if (Strings.isBlank(rules.get("level10").toString()) || Strings.equals(rules.get("level10").toString(), "0")) {
			rules_map.put("rules_view", rules_view);
			rules_map.put("rules_type_level", rules_type_level);
			rules_map.put("rules_level_length", rules_level_length);
			return rules_map;
		} else {
			rules_view.append("-");
			rules_view.append(rules.get("level10"));

			count = count + Integer.valueOf(rules.get("level10").toString());
			rules_type_level.put(count, 10);
			rules_level_length.put(10, count);
			rules_map.put("rules_view", rules_view);
			rules_map.put("rules_type_level", rules_type_level);
			rules_map.put("rules_level_length", rules_level_length);
		}
		return rules_map;
	}

	/**
	 * @Description 判断字典中数据是否被引用<BR>
	 *              引用则不允许删除
	 * @param entityMap
	 * @return String
	 * @throws DataAccessException
	 */
	@Override
	public String isExistsDataByTable(Map<String, Object> entityMap) throws DataAccessException {
		if (entityMap.get("dict_code") != null) {
			entityMap.put("dict_code", entityMap.get("dict_code").toString().toUpperCase());
		}

		sysFunUtilMapper.querySysDictDelCheck(entityMap);

		return entityMap.get("reNote") != null ? entityMap.get("reNote").toString() : null;
	}

	/**
	 * @Description 判断字典中数据是否被引用<BR>
	 *              引用则不允许删除
	 * @param tableName
	 *            表名
	 * @param id
	 *            表的主键
	 * @return
	 * @throws DataAccessException
	 */
	@Override
	public String isExistsDataByTable(String tableName, Object id) throws DataAccessException {
		Map<String, Object> entityMap = new HashMap<String, Object>();
		entityMap.put("group_id", SessionManager.getGroupId());
		entityMap.put("hos_id", SessionManager.getHosId());
		entityMap.put("copy_code", SessionManager.getCopyCode());
		entityMap.put("dict_id_str", id);
		entityMap.put("dict_code", tableName.toUpperCase());
		entityMap.put("acc_year", MyConfig.getCurAccYear());
		entityMap.put("p_flag", "0");
		sysFunUtilMapper.querySysDictDelCheck(entityMap);

		return entityMap.get("reNote") != null ? entityMap.get("reNote").toString() : null;
	}

	@Override
	public Map<String, Object> getSysParaMap(String mod_code) throws DataAccessException {
		// 获取系统参数
		Map<String, Object> paraVo = new HashMap<String, Object>();
		paraVo.put("group_id", SessionManager.getGroupId());
		paraVo.put("hos_id", SessionManager.getHosId());
		paraVo.put("copy_code", SessionManager.getCopyCode());
		paraVo.put("mod_code", mod_code);
		List<AccPara> lp = accParaMapper.queryAccPara(paraVo);
		Map<String, Object> paraMap = new HashMap<String, Object>();
		for (AccPara accPara : lp) {
			paraMap.put(accPara.getPara_code(), accPara.getPara_value());
		}
		return paraMap;
	}

	@Override
	public String getSysPara(String mod_code, String para_code) throws DataAccessException {
		// 获取系统参数
		Map<String, Object> paraVo = getSysParaMap(mod_code);

		return paraVo.get("para_code") == null ? null : paraVo.get("para_code").toString();
	}



	@Override
	public List<AccYearMonth> queryAccYearMonth(Map<String, Object> entityMap) throws DataAccessException {
		List<AccYearMonth> m = (List<AccYearMonth>) sysBaseMapper.queryAccYearMonth(entityMap);
		return m;
	}
	
	@Override
	public Map<String, Object> queryAccYearMonthMap() throws DataAccessException {
		
		Map<String, Object> mapVo = new HashMap<String, Object>();
		Map<String, Object> returnMap = new TreeMap<String, Object>(
                new Comparator<String>() {
                    public int compare(String obj1, String obj2) {
                        return obj1.compareTo(obj2);
                    }
                });
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("acc_year", MyConfig.getCurAccYear());
		List<AccYearMonth> m = (List<AccYearMonth>) sysBaseMapper.queryAccYearMonth(mapVo);

		for (AccYearMonth map : m) {
			returnMap.put(map.getAcc_year() + map.getAcc_month(), Mapl.toMaplist(map));
		}

		return returnMap;
	}
	@Override
	public Map<String, Object> queryAccYearMonthMap(Map<String, Object> entityMap) throws DataAccessException {
		
		Map<String, Object> returnMap = new HashMap<String, Object>();
		List<AccYearMonth> m = (List<AccYearMonth>) sysBaseMapper.queryAccYearMonth(entityMap);
		if(m!=null && m.size()>0){
			for (AccYearMonth map : m) {
				returnMap.put(map.getAcc_year() + map.getAcc_month(), Mapl.toMaplist(map));
			}
		}
		
		return returnMap;
	}
	@Override
	public Map<String, Object> queryModStartMap(Map<String, Object> entityMap) throws DataAccessException {
		
		List<ModStart> modStart=  modStartMapper.queryModStart(entityMap);
		Map<String, Object> returnMap=new HashMap<String, Object>();
		if(modStart!=null && modStart.size()>0){
			for (ModStart mod : modStart) {
				String year=(null==mod.getStart_year())?"":mod.getStart_year();
				String month=(null==mod.getStart_month())?"":mod.getStart_month();
				returnMap.put(mod.getMod_code(), year+month);
			}
		}
		
		return returnMap;
	}
	/**
	 * 获取当前会计期间
	 * 
	 * @param flag
	 *            各系统结账标识 列名 大小写均可
	 * @return
	 * @throws DataAccessException
	 */
	@Override
	public  String getSysYearMonth(String flag) throws DataAccessException {

		// 获取当前系统会计期间集合
		Map<String, Object> accmap = (Map<String, Object>) queryAccYearMonthMap();
		String yearmonth = "000000";
		for (Map.Entry<String, Object> entry : accmap.entrySet()) {
			Map<String, Object> value = (Map<String, Object>) entry.getValue();
			Integer obj =(Integer) (value.get(flag.toLowerCase())==null? 0:value.get(flag.toLowerCase()));
			if (obj == 0) {
				yearmonth = entry.getKey();
				break;
			}
		}
		return yearmonth;

	}

	/**
	 * 获取上一个会计期间
	 * 
	 * @param flag
	 *            各系统结账标识 列名 大小写均可
	 * @return
	 * @throws DataAccessException
	 */
	@Override
	public  String getLastSysYearMonth(String flag) throws DataAccessException {

		String yearmonth = getSysYearMonth(flag);
		String year = yearmonth.substring(0, 4);
		String month = yearmonth.substring(4, 6);

		if (month.equals("00") || month.equals("01")) {
			return "000000";
		} else {
			int year_i = Integer.parseInt(year);
			int month_i = Integer.parseInt(month);

			if (month_i <= 10) {
				month = "0" + String.valueOf(month_i - 1);
			} else {
				month = String.valueOf(month_i - 1);
			}

			return year + month;
		}

	}

	/**
	 * 判断传递的年月是否已经结账
	 * 
	 * @param yearmonth
	 *            年月 yyyyMM
	 * @param flag
	 *            各系统结账标识 列名 大小写均可
	 * @return
	 * @throws DataAccessException
	 */
	@Override
	public  boolean getAccYearMonthIsCheckOut(String yearmonth, String flag) throws DataAccessException {
		// 获取当前系统会计期间集合
		Map<String, Object> accmap = queryAccYearMonthMap();
		// 获取查询年月会计期间集合
		Map<String, Object> yearmonthMap = (Map<String, Object>) accmap.get(yearmonth);
		Integer obj =(Integer) (yearmonthMap.get(flag.toLowerCase())==null? 0:yearmonthMap.get(flag.toLowerCase()));
		return (Integer) obj == 1 ? true : false;
	}

	@Override
    public Map<String, Map<String, Object>> getHosRulesList(Map<String, Object> entityMap) throws DataAccessException {
		List<Map<String, Object>>  list= sysBaseMapper.getHosRulesList(entityMap);
		Map<String, Map<String, Object>> map =new HashMap<String, Map<String, Object>>();
		for (Map<String, Object> m : list) {
	        map.put(m.get("proj_code").toString(), m);
        }
		
	    return map;
    }

	@Override
    public Map<String, ?> getSysParaMaps() throws DataAccessException {
		Map<String, Object> paraVo = new HashMap<String, Object>();
		paraVo.put("group_id", SessionManager.getGroupId());
		paraVo.put("hos_id", SessionManager.getHosId());
		paraVo.put("copy_code", SessionManager.getCopyCode());
		List<AccPara> lp = accParaMapper.queryAccPara(paraVo);
		Map<String, Map<String, Object>> paraMap = new HashMap<String, Map<String, Object>>();
		paraMap.put("", new HashMap<String,Object>());
		Map<String, Object> mvp=null;
		for (AccPara accPara : lp) {
			mvp=paraMap.get(accPara.getMod_code());
			mvp=null==mvp?new HashMap<String,Object>():mvp;
			mvp.put(accPara.getPara_code(), accPara.getPara_value());
			paraMap.put(accPara.getMod_code(), mvp);
		}
	    return paraMap;
    }
	
	@Override
    public Map<String, Map<String, Object>> queryGroupParaList(Map<String, Object> entityMap) throws DataAccessException {
		List<Map<String, Object>>  list= sysBaseMapper.queryGroupParaList(entityMap);
		Map<String, Map<String, Object>> map =new HashMap<String, Map<String, Object>>();
		for (Map<String, Object> m : list) {
	        map.put(m.get("para_code").toString(), m);
        }
		
	    return map;
    }
	
	@Override
    public Map<String, Map<String, Object>> queryGroupSFList(Map<String, Object> entityMap) throws DataAccessException {
		 
		List<Map<String, Object>>  list= sysBaseMapper.queryGroupSFList(entityMap);
		Map<String, Map<String, Object>> map =new HashMap<String, Map<String, Object>>();
		for (Map<String, Object> m : list) {
	        map.put(m.get("para_code").toString(), m);
        }
		
	    return map;
    }

	
}
