/**
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */

package com.chd.hrp.ass.serviceImpl.base;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.nutz.lang.Strings;
import org.nutz.mapl.Mapl;
import org.omg.CORBA.LongLongSeqHelper;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.SessionManager;
import com.chd.base.util.ChdJson;
import com.chd.base.util.DateUtil;
import com.chd.hrp.acc.dao.AccParaMapper;
import com.chd.hrp.acc.entity.AccPara;
import com.chd.hrp.ass.dao.base.AssBaseMapper;
import com.chd.hrp.ass.dao.dict.AssBillNoMapper;
import com.chd.hrp.ass.entity.dict.AssBillNo;
import com.chd.hrp.ass.service.base.AssBaseService;
import com.chd.hrp.sys.dao.notGeneral.SysFunUtilMapper;

/**
 * @Description: 资产公用服务类
 * @Author: bell
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Service("assBaseService")
public class AssBaseServiceImpl implements AssBaseService {

	private static Logger logger = Logger.getLogger(AssBaseServiceImpl.class);

	// 引入DAO操作
	@Resource(name = "assBaseMapper")
	private final AssBaseMapper assBaseMapper = null;

	// 引入DAO操作
	@Resource(name = "assBillNoMapper")
	private final AssBillNoMapper assBillNoMapper = null;

	// 引入DAO操作
	@Resource(name = "sysFunUtilMapper")
	private final SysFunUtilMapper sysFunUtilMapper = null;

	// 引入DAO操作
	@Resource(name = "accParaMapper")
	private final AccParaMapper accParaMapper = null;

	@Override
	public Map<Object, Object> getHosRules(Map<String, Object> entityMap) throws DataAccessException {
		Map<String, Object> rules = assBaseMapper.getHosRules(entityMap);
		Map<Object, Object> rules_map = new HashMap<Object, Object>();
		Map<Object, Object> rules_type_level = new HashMap<Object, Object>();
		Map<Object, Object> rules_level_length = new HashMap<Object, Object>();
		StringBuilder rules_view = new StringBuilder();
		if (rules == null) {
			return rules_map;
		}
		int count = 0;

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
			rules_level_length.put(6, count);
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
	 * @Description 更新最大单号<BR>
	 * @param entityMap
	 * @return Map<String,Object>
	 * @throws DataAccessException
	 */
	@Override
	public int updateAssBillNoMaxNo(Map<String, Object> entityMap) throws DataAccessException {
		
		return assBillNoMapper.updateAssBillNoManageMaxNo(entityMap);
	}

	/**
	 * @Description 更新最大单号<BR>
	 * @param tableName
	 *            表名
	 * @throws DataAccessException
	 */
	@Override
	public int updateAssBillNoMaxNo(String tableName) throws DataAccessException {
		Map<String, Object> entityMap = new HashMap<String, Object>();
		entityMap.put("group_id", SessionManager.getGroupId());
		entityMap.put("hos_id", SessionManager.getHosId());
		entityMap.put("copy_code", SessionManager.getCopyCode());
		entityMap.put("bill_table", tableName);
		return assBillNoMapper.updateAssBillNoMaxNo(entityMap);
	}

	/**
	 * @Description 获取对应业务表的单据号生成规则<BR>
	 * @param entityMap
	 * @return AssBillNo
	 * @throws DataAccessException
	 */
	@Override
	public AssBillNo queryAssBillNoByName(Map<String, Object> entityMap) throws DataAccessException {
		if (entityMap.get("bill_table") != null) {
			entityMap.put("bill_table", entityMap.get("bill_table").toString().toUpperCase());
		}
		return assBillNoMapper.queryAssBillNoByCode(entityMap);
	}

	/**
	 * @Description 获取对应业务表的单据号<BR>
	 * @param entityMap
	 *            group_id hos_id copy_code
	 * @return AssBillNo
	 * @throws DataAccessException
	 */
	@Override
	public String getBillNOSeqNo(Map<String, Object> entityMap) throws DataAccessException {
		if (entityMap.get("bill_table") != null) {
			entityMap.put("bill_table", entityMap.get("bill_table").toString().toUpperCase());
		}

		if (entityMap.get("group_id") == null) {
			entityMap.put("group_id", SessionManager.getGroupId());
		}
		
		if (entityMap.get("hos_id") == null) {
			entityMap.put("hos_id", SessionManager.getHosId());
		}
		
		if (entityMap.get("copy_code") == null) {
			entityMap.put("copy_code", SessionManager.getCopyCode());
		}
		
		AssBillNo abn= queryAssBillNoByName(entityMap);
		String pref=abn.getPref()==null?"":abn.getPref();
		String store_code="";
		String year="";
		String month="";
		String day="";
		String max_no="";
		int seq_no=abn.getSeq_no();
		
		//判断拼接规则
		boolean year_flag=false;
		boolean month_flag=false;
		boolean day_flag=false;
		boolean store_flag=false;
		
		
		year_flag=(abn.getIs_year() != null && abn.getIs_year().equals("1"))?true:false;
		month_flag=(abn.getIs_month() != null && abn.getIs_month().equals("1"))?true:false;
		day_flag=(abn.getIs_day() != null && abn.getIs_day().equals("1"))?true:false;
		store_flag=(abn.getIs_store() != null && abn.getIs_store().equals("1"))?true:false;
		
		String sysdate=DateUtil.getSysDate();
		String v_year=sysdate.substring(0, 4);
		String v_month=sysdate.substring(5, 7);
		String v_day=sysdate.substring(8, 10);
		String v_store_code=entityMap.get("store_code")==null?"":entityMap.get("store_code").toString();
		
		v_year=entityMap.get("year")==null?v_year:entityMap.get("year").toString();
		v_month=entityMap.get("month")==null?v_month:entityMap.get("month").toString();
		v_day=entityMap.get("day")==null?v_day:entityMap.get("day").toString();
		
		store_code=store_flag==true?v_store_code:"";
		year=year_flag==true?v_year:"";
		month=month_flag==true?v_month:"";
		day=day_flag==true?v_day:"";
		
		
		entityMap.put("pref",pref);
		entityMap.put("store_code", store_code);
		entityMap.put("year", year);
		entityMap.put("month", month);
		entityMap.put("day", day);
		
		
		Map<String,Object> map= assBillNoMapper.queryAssBillNoManageByCode(entityMap);
		
		if(map==null) {
			assBillNoMapper.insertAssBillNoManage(entityMap);
			max_no="1";
		}else {
		 max_no=map.get("max_no").toString();
		}
		
		String pref_point=Strings.isEmpty(abn.getPref_point())==true?"":abn.getPref_point();
		String year_point=Strings.isEmpty(abn.getIs_year_point())==true?"":abn.getIs_year_point();
		String month_point=Strings.isEmpty(abn.getIs_month_point())==true?"":abn.getIs_month_point();
		String day_point=Strings.isEmpty(abn.getIs_day_point())==true?"":abn.getIs_day_point();
		String store_point=Strings.isEmpty(abn.getIs_store_point())==true?"":abn.getIs_store_point();
		 
		//拼接单据号  规则  前缀+前缀连接符+仓库+仓库连接符+年+年连接符+月+月连接符+日+日连接符+流水号
		StringBuffer sb=new StringBuffer();
		sb.append(pref);
		sb.append(pref_point);
		sb.append(store_code);
		sb.append(store_point);
		sb.append(year);
		sb.append(year_point);
		sb.append(month);
		sb.append(month_point);
		sb.append(day);
		sb.append(day_point);
		
		return sb.toString()+ Strings.alignRight(max_no, seq_no, '0');

	}

	/**
	 * @Description 获取对应业务表的单据号<BR>
	 * @param tableName
	 *            表名
	 * @return SeqNo
	 * @throws DataAccessException
	 */
	@Override
	public String getBillNOSeqNo(String tableName) throws DataAccessException {
		Map<String, Object> entityMap = new HashMap<String, Object>();
		entityMap.put("group_id", SessionManager.getGroupId());
		entityMap.put("hos_id", SessionManager.getHosId());
		entityMap.put("copy_code", SessionManager.getCopyCode());
		entityMap.put("bill_table", tableName.toUpperCase());
		AssBillNo bill = queryAssBillNoByName(entityMap);
		String pref = bill.getPref();
		int seq_no = bill.getSeq_no();
		int max_no = bill.getMax_no();
		return pref + Strings.alignRight(max_no, seq_no, '0');
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
		entityMap.put("acc_year", SessionManager.getAcctYear());
		entityMap.put("p_flag", "0");
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
	 * @param p_flag
	 *            表是否带级次 0:不带 1：带
	 * @return
	 * @throws DataAccessException
	 */
	@Override
	public String isExistsDataByTable(String tableName, Object id, String p_flag) throws DataAccessException {
		Map<String, Object> entityMap = new HashMap<String, Object>();
		entityMap.put("group_id", SessionManager.getGroupId());
		entityMap.put("hos_id", SessionManager.getHosId());
		entityMap.put("copy_code", SessionManager.getCopyCode());
		entityMap.put("dict_id_str", id);
		entityMap.put("dict_code", tableName.toUpperCase());
		entityMap.put("acc_year", SessionManager.getAcctYear());
		entityMap.put("p_flag", p_flag);
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
		paraVo.put("mode_code", mod_code);
		List<AccPara> lp = accParaMapper.queryAccPara(paraVo);
		Map<String, Object> paraMap = new HashMap<String, Object>();
		for (AccPara accPara : lp) {
			paraMap.put(accPara.getPara_code(), accPara.getPara_value());
		}
		return paraMap;
	}

	@Override
	public String getAssPara(String para_code) throws DataAccessException {
		// 获取系统参数
		Map<String, Object> paraVo = getSysParaMap("05");

		return paraVo.get("para_code") == null ? null : paraVo.get("para_code").toString();
	}

	@Override
	public String getAssYearMonth() throws DataAccessException {
		Map<String, Object> mapVo = new HashMap<String, Object>();
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("acc_year", SessionManager.getAcctYear());
		mapVo.put("ass_flag", "0");

		List<Map<String, Object>> m = (List<Map<String, Object>>) assBaseMapper.queryAssYearMonth(mapVo);

		if (m.size() > 0) {

			return m.get(0).get("acc_year") + "-" + m.get(0).get("acc_month");

		} else {

			return "0000-00";

		}

	}

	@Override
	public String getLastAssYearMonth() throws DataAccessException {
		// TODO Auto-generated method stub
		Map<String, Object> mapVo = new HashMap<String, Object>();
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("acc_year", SessionManager.getAcctYear());
		mapVo.put("ass_flag", "1");

		List<Map<String, Object>> m = (List<Map<String, Object>>) assBaseMapper.queryAssYearMonth(mapVo);

		if (m.size() > 0) {

			return m.get(m.size() - 1).get("acc_year") + "-" + m.get(m.size() - 1).get("acc_month");

		} else {

			return "0000-00";

		}
	}

	@Override
	public boolean getAssIsCheckOut(String year, String month) throws DataAccessException {
		Map<String, Object> mapVo = new HashMap<String, Object>();
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("acc_year", year);
		mapVo.put("acc_month", month);

		List<Map<String, Object>> m = (List<Map<String, Object>>) assBaseMapper.queryAssYearMonth(mapVo);

		return (Integer) m.get(0).get("ass_flag") == 0 ? false : true;

	}

	@Override
	public List<?> queryDepts(Map<String, Object> entityMap) throws DataAccessException {
		List<Map<String, Object>> m = (List<Map<String, Object>>) assBaseMapper.queryDepts(entityMap);
		return m;
	}

	@Override
    public List<?> queryStores(Map<String, Object> entityMap) throws DataAccessException {
		List<Map<String, Object>> m = (List<Map<String, Object>>) assBaseMapper.queryStores(entityMap);
		return m;
    }

	@Override
    public List<?> queryCheckPlanFin(Map<String, Object> entityMap) throws DataAccessException {
		List<Map<String, Object>> m = (List<Map<String, Object>>) assBaseMapper.queryCheckPlanFin(entityMap);
		return m;
    }

}
