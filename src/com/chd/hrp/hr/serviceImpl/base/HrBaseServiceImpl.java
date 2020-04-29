package com.chd.hrp.hr.serviceImpl.base;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;

import oracle.jdbc.OracleTypes;

import org.apache.log4j.Logger;
import org.nutz.dao.Dao;
import org.nutz.dao.Sqls;
import org.nutz.dao.entity.Record;
import org.nutz.dao.sql.Sql;
import org.nutz.lang.Strings;
import org.nutz.mvc.Mvcs;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.SessionManager;
import com.chd.hrp.hr.dao.base.HrAccParaMapper;
import com.chd.hrp.hr.dao.base.HrAssBaseMapper;
import com.chd.hrp.hr.dao.base.HrAssBillNoMapper;
import com.chd.hrp.hr.dao.base.HrCommonMapper;
import com.chd.hrp.hr.dao.base.HrSysFunUtilMapper;
import com.chd.hrp.hr.entity.base.HrAccPara;
import com.chd.hrp.hr.entity.base.HrAssBillNo;
import com.chd.hrp.hr.entity.base.HrColumn;
import com.chd.hrp.hr.service.base.HrBaseService;

/**
 * @Description: 公用服务类
 * @Author: bell
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Service("hrBaseService")
public class HrBaseServiceImpl implements HrBaseService {

	private static Logger logger = Logger.getLogger(HrBaseServiceImpl.class);
	
	// 引入DAO操作
	@Resource(name = "hrAssBaseMapper")
	private final HrAssBaseMapper hrAssBaseMapper = null;

	// 引入DAO操作
	@Resource(name = "hrAssBillNoMapper")
	private final HrAssBillNoMapper hrAssBillNoMapper = null;

	// 引入DAO操作
	@Resource(name = "hrSysFunUtilMapper")
	private final HrSysFunUtilMapper hrSysFunUtilMapper = null;

	// 引入DAO操作
	@Resource(name = "hrAccParaMapper")
	private final HrAccParaMapper hrAccParaMapper = null;

	@Resource(name = "hrCommonMapper")
	private final HrCommonMapper hrCommonMapper = null;
	@Override
	public Map<Object, Object> getHosRules(Map<String, Object> entityMap) throws DataAccessException {
		Map<String, Object> rules = hrAssBaseMapper.getHosRules(entityMap);
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
		return hrAssBillNoMapper.updateAssBillNoMaxNo(entityMap);
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
		return hrAssBillNoMapper.updateAssBillNoMaxNo(entityMap);
	}

	/**
	 * @Description 获取对应业务表的单据号生成规则<BR>
	 * @param entityMap
	 * @return AssBillNo
	 * @throws DataAccessException
	 */
	@Override
	public HrAssBillNo queryAssBillNoByName(Map<String, Object> entityMap) throws DataAccessException {
		if (entityMap.get("bill_table") != null) {
			entityMap.put("bill_table", entityMap.get("bill_table").toString().toUpperCase());
		}
		return hrAssBillNoMapper.queryAssBillNoByCode(entityMap);
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
		HrAssBillNo bill = queryAssBillNoByName(entityMap);
		String pref = bill.getPref();
		int seq_no = bill.getSeq_no();
		int max_no = bill.getMax_no();
		return pref + Strings.alignRight(max_no, seq_no, '0');
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
		HrAssBillNo bill = queryAssBillNoByName(entityMap);
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

		hrSysFunUtilMapper.querySysDictDelCheck(entityMap);

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
		hrSysFunUtilMapper.querySysDictDelCheck(entityMap);

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
		hrSysFunUtilMapper.querySysDictDelCheck(entityMap);

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
		List<HrAccPara> lp = hrAccParaMapper.queryAccPara(paraVo);
		Map<String, Object> paraMap = new HashMap<String, Object>();
		for (HrAccPara accPara : lp) {
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

		List<Map<String, Object>> m = (List<Map<String, Object>>) hrAssBaseMapper.queryAssYearMonth(mapVo);

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

		List<Map<String, Object>> m = (List<Map<String, Object>>) hrAssBaseMapper.queryAssYearMonth(mapVo);

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

		List<Map<String, Object>> m = (List<Map<String, Object>>) hrAssBaseMapper.queryAssYearMonth(mapVo);

		return (Integer) m.get(0).get("ass_flag") == 0 ? false : true;

	}

	@Override
	public List<?> queryDepts(Map<String, Object> entityMap) throws DataAccessException {
		List<Map<String, Object>> m = (List<Map<String, Object>>) hrAssBaseMapper.queryDepts(entityMap);
		return m;
	}

	@Override
    public List<?> queryStores(Map<String, Object> entityMap) throws DataAccessException {
		List<Map<String, Object>> m = (List<Map<String, Object>>) hrAssBaseMapper.queryStores(entityMap);
		return m;
    }

	@Override
	public String updateAndQueryHrBillNo(Map<String, Object> entityMap) throws DataAccessException {
		Dao nutDao = Mvcs.ctx().getDefaultIoc().get(Dao.class, "dao");
		Sql sql = Sqls.create("CALL PKG_HR_APP.PROC_GET_BILL_NO(@prm_group_id, @prm_hos_id, @prm_copy_code, @prm_bill_code,@prm_perfixe, @OUTprm_bill_no_value, @OUTprm_AppCode, @OUTprm_ErrTxt)"); 
		sql.setEntity(nutDao.getEntity(String.class));
		sql.params().set("prm_group_id", entityMap.get("group_id")); // 设置入参
		sql.params().set("prm_hos_id", entityMap.get("hos_id")); 
		sql.params().set("prm_copy_code", entityMap.get("copy_code")); 
		sql.params().set("prm_bill_code", entityMap.get("bill_code"));
		sql.params().set("prm_perfixe", entityMap.get("prm_perfixe"));
	    sql.params().set("OUTprm_bill_no_value", OracleTypes.VARCHAR);
	    sql.params().set("OUTprm_AppCode", OracleTypes.NUMBER);//TODO 后期处理
	    sql.params().set("OUTprm_ErrTxt", OracleTypes.VARCHAR);//TODO 后期处理
	    // 设置出参类型,注意,必须加OUT开头 
	    sql = nutDao.execute(sql); 
	    Record re = sql.getOutParams();
		return re.getString("prm_bill_no_value");
	}

	@Override
	public String QueryHrBillNo(Map<String, Object> entityMap)
			throws DataAccessException {
		Dao nutDao = Mvcs.ctx().getDefaultIoc().get(Dao.class, "dao");
		Sql sql = Sqls.create("CALL PKG_HR_APP.PROC_SELECT_BILL_NO(@prm_group_id, @prm_hos_id, @prm_copy_code, @prm_bill_code,@prm_perfixe, @OUTprm_bill_no_value, @OUTprm_AppCode, @OUTprm_ErrTxt)"); 
		sql.setEntity(nutDao.getEntity(String.class));
		sql.params().set("prm_group_id", entityMap.get("group_id")); // 设置入参
		sql.params().set("prm_hos_id", entityMap.get("hos_id")); 
		sql.params().set("prm_copy_code", entityMap.get("copy_code")); 
		sql.params().set("prm_bill_code", entityMap.get("bill_code"));
		sql.params().set("prm_perfixe", entityMap.get("prm_perfixe"));
	    sql.params().set("OUTprm_bill_no_value", OracleTypes.VARCHAR);
	    sql.params().set("OUTprm_AppCode", OracleTypes.NUMBER);//TODO 后期处理
	    sql.params().set("OUTprm_ErrTxt", OracleTypes.VARCHAR);//TODO 后期处理
	    // 设置出参类型,注意,必须加OUT开头 
	    sql = nutDao.execute(sql); 
	    Record re = sql.getOutParams();
		return re.getString("prm_bill_no_value");
	}

	@Override
	public String insertTable(Map<String, Object> entityMap,List<Map<String, Object>> list)
			throws DataAccessException {
		List<String> insertList= new ArrayList<String>();
		List<String> deleteList= new ArrayList<String>();
		entityMap.put("tab_code", entityMap.get("Table_code"));
		
		/*hrAssBaseMapper.deleteTable(entityMap);*/
		
		List<HrColumn> hrColStrucList = hrCommonMapper.queryColStruc(entityMap);
		 
			//遍历获取到的数据
			for (Map<String, Object> map : list) {
				StringBuffer insertSql = new StringBuffer();
				StringBuffer insertColSql = new StringBuffer();
				StringBuffer insertVaSql = new StringBuffer();
				StringBuffer deletetSql = new StringBuffer();
				StringBuffer deleteColSql = new StringBuffer();
				StringBuffer deleteVaSql = new StringBuffer();
				insertSql.append("insert into "+entityMap.get("Table_code")+" ( group_id,hos_id,");
				deletetSql.append("delete from  "+entityMap.get("Table_code"));
		        for(Entry<String, Object> entry : map.entrySet()){
		        	//遍历表
		        	for (HrColumn hrColumn : hrColStrucList) {
		        		Map<String,Object> map1 = new HashMap<String, Object>();
		        	  if(hrColumn.getCol_code().equals(entry.getKey().toUpperCase()) ){
		        		insertColSql.append(hrColumn.getCol_code()+" ,");
		        		deleteColSql.append(hrColumn.getCol_code()+"=");
		        		map1.put("COL_VALUE",entry.getValue() );
						map1.put("DATA_TYPE", hrColumn.getData_type());
		        		insertVaSql.append(dataTypeStr(map1) +" ,");
		        		deleteColSql.append(dataTypeStr(map1) +" and ");
		        	}
				  
		            }
		    
			}
		    	if(insertColSql.length()>1){
					insertColSql.deleteCharAt(insertColSql.length() - 1);
					insertVaSql.deleteCharAt(insertVaSql.length() - 1);
					insertSql.append(insertColSql);
					insertSql.append(" ) VALUES ("+SessionManager.getGroupId()+","+SessionManager.getHosId()+",");
				
					insertSql.append(insertVaSql+" )");
					insertList.add(insertSql.toString());
					
					deletetSql.append(" where ");
					deleteColSql.delete(deleteColSql.length()-4,deleteColSql.length());
					deletetSql.append(deleteColSql);
					
					deleteList.add(deletetSql.toString());
				}
			
		}

			if (insertList.size() > 0 ) {
				
				List<String> stringSqlList= new  ArrayList<String>();
				List<String> deleteSqlList= new  ArrayList<String>();
				for (int i = 0; i < deleteList.size(); i++) {

					deleteSqlList.add(deleteList.get(i));

					if (i > 0 && i % 500 == 0 || i == deleteList.size() - 1) {

						hrAssBaseMapper.batchDeleteTable(deleteSqlList);

					}

				}
				for (int i=0;  i<  insertList.size(); i++) {
					
					stringSqlList.add(insertList.get(i));
					
					if( i>0 && i%500==0 || i == insertList.size() - 1) {
						
						hrAssBaseMapper.batchinsertTable(stringSqlList);
						
					}
                     
				}
		
			}
		
		return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";	
	}
	@Override
	public String updateTable(Map<String, Object> entityMap,List<Map<String, Object>> list)
			throws DataAccessException {
	StringBuffer updateSql = new StringBuffer();
	updateSql.append(" update "+entityMap.get("Table_code")+" set ");
	for (Map<String, Object> map : list) {
        for(Entry<String, Object> entry : map.entrySet()){
        	
	      updateSql.append(entry.getKey()+" = ");
	      
		   updateSql.append(entry.getValue()+",");
		  
            }
		
	}
	updateSql.deleteCharAt(updateSql.length() - 1);
	updateSql.append(" where  group_id="+SessionManager.getGroupId()+" and hos_id="+SessionManager.getHosId()+" and emp_id="+entityMap.get("emp_id")  );
	entityMap.put("sql", updateSql);	
	hrAssBaseMapper.updateTable(entityMap);
	return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";
	}
	
	private String dataTypeStr(Map<String, Object> map) {
		
		String str = null;
		// 根据字段数据类型 拼接语句
		if ("NUMBER".equals(map.get("DATA_TYPE"))) {
			
			str = " " + map.get("COL_VALUE");
			
		} else if ("VARCHAR2".equals(map.get("DATA_TYPE"))) {
			
			str = " '" + map.get("COL_VALUE") + "'";
			
		} else if ("DATE".equals(map.get("DATA_TYPE"))) {
			
			str = " TO_DATE('" + map.get("COL_VALUE") + "','yyyy-MM-dd')";
			
		} else {
			
			str = " '" + map.get("COL_VALUE") + "'";
		}
		
		return str;
		
	}
}
