/**
 * @Copyright: Copyright (c) 2015-2-14 
 * @Company: 智慧云康（北京）数据科技有限公司
 */

package com.chd.hrp.acc.serviceImpl.books.subjaccount;
 
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.jsoup.helper.StringUtil;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.chd.base.SessionManager;
import com.chd.base.SysPage;
import com.chd.base.util.ChdJson;
import com.chd.base.util.DateUtil;
import com.chd.base.util.JsonListMapUtil;
import com.chd.hrp.acc.dao.AccLederMapper;
import com.chd.hrp.acc.dao.books.subjaccount.AccSubjLedgerMapper;
import com.chd.hrp.acc.dao.commonbuilder.AccSubjMapper;
import com.chd.hrp.acc.dao.verification.AccNostroMapper;
import com.chd.hrp.acc.entity.AccMultiPlan;
import com.chd.hrp.acc.service.books.subjaccount.AccSubjLedgerService;
import com.github.pagehelper.PageInfo;
import com.intersys.globals.internal.Session;


@Service("accSubjLedgerService")
public class AccSubjLedgerServiceImpl implements AccSubjLedgerService {

	private static Logger logger = Logger.getLogger(AccSubjLedgerServiceImpl.class);

	@Resource(name = "accSubjLedgerMapper")
	private final AccSubjLedgerMapper accSubjLedgerMapper = null;

	@Resource(name = "accLederMapper")
	private final AccLederMapper accLederMapper = null;

	@Resource(name = "accNostroMapper")
	private final AccNostroMapper accNostroMapper = null;

	@Resource(name = "accSubjMapper")
	private final AccSubjMapper accSubjMapper = null;

	/**
	 * 科目总账（按月） 查询
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public String collectAccSubjLedger(Map<String, Object> entityMap) throws DataAccessException {

		entityMap.put("is_subj_flag", entityMap.get("subj_select_flag"));
		entityMap.put("rst", new ArrayList<Map<String, Object>>());
		entityMap.put("p_flag", "km1001");

		accSubjLedgerMapper.collectAccSubjLedger(entityMap);
		TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		return ChdJson.toJsonLower((List<Map<String, Object>>) entityMap.get("rst"),
				Long.parseLong(entityMap.get("tcount").toString()));

	}

	/**
	 * 科目总账（按月） 打印
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public Map<String, Object> collectAccSubjLedgerPrint(Map<String, Object> entityMap) throws DataAccessException {

		Map<String, Object> reMap = new HashMap<String, Object>();

		entityMap.put("is_subj_flag", entityMap.get("subj_select_flag"));
		entityMap.put("rst", new ArrayList<Map<String, Object>>());
		entityMap.put("p_flag", "km1001");

		accSubjLedgerMapper.collectAccSubjLedger(entityMap);

		TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();

		reMap.put("detail", (List<Map<String, Object>>) entityMap.get("rst"));

		return reMap;

	}

	/**
	 * 科目总账 按天
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public String collectAccSubjLedgerDetail(Map<String, Object> entityMap) throws DataAccessException {

		entityMap.put("is_subj_flag", entityMap.get("subj_select_flag"));
		entityMap.put("rst", new ArrayList<Map<String, Object>>());
		entityMap.put("p_flag", "km1002");

		accSubjLedgerMapper.collectAccSubjLedger(entityMap);
		// accSubjLedgerMapper.collectAccSubjLedgerDetail(entityMap);
		TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		return ChdJson.toJsonLower((List<Map<String, Object>>) entityMap.get("rst"),
				Long.parseLong(entityMap.get("tcount").toString()));

	}

	/**
	 * PageOffice打印，科目总账（按天）
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public Map<String, Object> collectAccSubjLedgerDetailPrint(Map<String, Object> entityMap)
			throws DataAccessException {

		Map<String, Object> reMap = new HashMap<String, Object>();
		entityMap.put("is_subj_flag", entityMap.get("subj_select_flag"));
		entityMap.put("rst", new ArrayList<Map<String, Object>>());
		entityMap.put("p_flag", "km1002");

		accSubjLedgerMapper.collectAccSubjLedger(entityMap);
		// accSubjLedgerMapper.collectAccSubjLedgerDetail(entityMap);
		TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		reMap.put("detail", (List<Map<String, Object>>) entityMap.get("rst"));
		return reMap;

	}

	/**
	 * 三栏明细账查询
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public String collectThreeColumnLedgerDetail(Map<String, Object> entityMap) throws DataAccessException {

		entityMap.put("is_subj_flag", entityMap.get("subj_select_flag"));

		entityMap.put("rst", new ArrayList<Map<String, Object>>());
		entityMap.put("p_flag", "km1003");
		entityMap.put("obj_select_flag", "");
		entityMap.put("obj_code", "");
		entityMap.put("source_id", "");
		entityMap.put("table_flag", "");
		entityMap.put("cur_code", "");
		entityMap.put("emp_code", "");

		/* entityMap.put("rst",OracleTypes.CURSOR); */
		accSubjLedgerMapper.collectAccSubjLedger(entityMap);
		// accSubjLedgerMapper.collectThreeColumnLedgerDetail(entityMap);

		TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();

		return ChdJson.toJsonLower((List<Map<String, Object>>) entityMap.get("rst"),
				Long.parseLong(entityMap.get("tcount").toString()));

	}

	/**
	 * PageOffice打印，三栏明细账
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public Map<String, Object> collectThreeColumnLedgerDetailPrint(Map<String, Object> entityMap)
			throws DataAccessException {

		Map<String, Object> reMap = new HashMap<String, Object>();
		entityMap.put("is_subj_flag", entityMap.get("subj_select_flag"));
		entityMap.put("p_flag", "km1003");
		entityMap.put("obj_select_flag", "");
		entityMap.put("obj_code", "");
		entityMap.put("source_id", "");
		entityMap.put("table_flag", "");
		entityMap.put("cur_code", "");
		entityMap.put("emp_code", "");

		entityMap.put("rst", new ArrayList<Map<String, Object>>());
		/* entityMap.put("rst",OracleTypes.CURSOR); */
		accSubjLedgerMapper.collectAccSubjLedger(entityMap);
		// accSubjLedgerMapper.collectThreeColumnLedgerDetail(entityMap);
		TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();

		reMap.put("detail", (List<Map<String, Object>>) entityMap.get("rst"));

		return reMap;

	}
	
	/**
	 * 科目余额表查询
	 * 查询、普通打印、模板打印共用一个方法
	 */
	public List<Map<String, Object>> queryBalanceLedgerDetailList(Map<String, Object> entityMap) throws DataAccessException {
		
		StringBuffer stateSql = new StringBuffer();
		StringBuffer subjSql = new StringBuffer();
		StringBuffer subjCodeSql = new StringBuffer();
		// StringBuffer subjCodeSql2 = new StringBuffer();
		StringBuffer levelSql2 = new StringBuffer();
		StringBuffer curSql = new StringBuffer();
		StringBuffer copyCodeSql = new StringBuffer();
		StringBuffer subjLevelSql = new StringBuffer();

		subjCodeSql.append(" and c.subj_code in ( ");
		Map<String, Object> copyCodeMap = new HashMap<>();
		// subjCodeSql2.append(" and ( ");

		int a = 0;
		int size = 0;
		if (entityMap.get("sch_id") != null && entityMap.get("sch_id") != "") {
			List<Map<String, Object>> mapList = accSubjLedgerMapper.queryAccBookSch(entityMap);
			size = mapList.size();
			for (Map<String, Object> map : mapList) {
				a++;
				// 方案中是否勾选包含未记账
				if (Integer.parseInt(map.get("is_nacc").toString()) == 1) {
					entityMap.put("is_state", "1");
				} else {
					entityMap.put("is_state", "99");
				}

				if (!"".equals(map.get("p_copy_codes"))) {
					copyCodeSql.append(" and a.copy_code in ( ");
					String copyCodes = map.get("p_copy_codes").toString().replace(";", "','");
					String copyCode = copyCodes.substring(2, copyCodes.length() - 2);
					if (copyCodeMap.get(copyCode) == null) {
						copyCodeMap.put(copyCode, copyCode);
						copyCodeSql.append(copyCodes.substring(2, copyCodes.length() - 2)).append(" ) ");
						entityMap.put("copyCodeSql", copyCodeSql.toString());
					}
				}

				// 方案中是否勾选范围查询
				if (Integer.parseInt(map.get("is_fw").toString()) == 1) {
					if (Integer.parseInt(map.get("subj_level_begin").toString()) == 99) {
						subjSql.append(" and c.subj_code >= '").append(map.get("subj_code_begin"))
								.append("99999999999999999'");
						subjSql.append(" and c.subj_code <= '").append(map.get("subj_code_end")).append("'");
					} else if (Integer.parseInt(map.get("subj_level_end").toString()) == 99) {
						subjSql.append(" and c.subj_code >= '").append(map.get("subj_code_begin")).append("'");
						subjSql.append(" and c.subj_code <= '").append(map.get("subj_code_end"))
								.append("99999999999999999'");
					} else {
						subjSql.append(" and c.subj_code >= '").append(map.get("subj_code_begin")).append("'");// .append("'
																												// and
																												// '").append(map.get("subj_code_end")).append("'
																												// ");
						subjSql.append(" and (c.subj_code <= '").append(map.get("subj_code_end")).append("' or c.subj_code like '").append(map.get("subj_code_end")).append("%' )");// .append("'
																												// and
																												// '").append(map.get("subj_code_end")).append("'
																												// ");
					}
				} else {
					// if(Integer.parseInt(map.get("subj_is_last").toString())==1){
					if (a < size) {
						subjCodeSql.append("'").append(map.get("subj_code").toString());
						if (a % 1000 == 0) {
							subjCodeSql.append("') or c.subj_code in ( ");
						} else {
							subjCodeSql.append("',");
						}
					} else {
						subjCodeSql.append("'").append(map.get("subj_code").toString()).append("')");
					}
					/*
					 * }else{
					 * subjCodeSql2.append(" c.subj_code like '").append(map.get("subj_code").
					 * toString()).append("' ") }
					 */
				}

				entityMap.put("cur_code", map.get("cur_code").toString());
				entityMap.put("is_fw", map.get("is_fw").toString());
				entityMap.put("subj_level_begin", map.get("subj_level_begin").toString());
				entityMap.put("subj_level_end", map.get("subj_level_end").toString());
				entityMap.put("is_last", map.get("is_last").toString());
				entityMap.put("is_bqwfs", map.get("is_bqwfs").toString());
			}

			if (null!=entityMap.get("cur_code") && !"".equals(entityMap.get("cur_code").toString())) {
				curSql.append(" and v.cur_code = '").append(entityMap.get("cur_code").toString()).append("' ");
			} else {
				curSql.append("");
			}

			// 方案中是否选择科目级次
			if ((null!=entityMap.get("subj_level_begin") && Integer.parseInt(entityMap.get("subj_level_begin").toString()) != 0)
					|| (null!=entityMap.get("subj_level_end") && Integer.parseInt(entityMap.get("subj_level_end").toString()) != 0)) {
				if (Integer.parseInt(entityMap.get("subj_level_begin").toString()) == 99
						|| Integer.parseInt(entityMap.get("subj_level_end").toString()) == 99) {
					levelSql2.append(" and  c.is_last = 1 ");
				} else {
					levelSql2.append(" and r2.subj_level between '").append(entityMap.get("subj_level_begin"))
							.append("' and  '").append(entityMap.get("subj_level_end")).append("'  ");
				}
			} else {
				// 按照方案中选择的树形菜单里面的科目进行查询
				levelSql2.append("");
			}
		} else {
			// 不选方案，单选页面上的科目查询
			if (entityMap.get("subj_code_begin") != null && entityMap.get("subj_code_begin") != "") {
				subjSql.append(" and c.subj_code >= '").append(entityMap.get("subj_code_begin").toString()).append("'");
			} else {
				subjSql.append("");
			}
			if (entityMap.get("subj_code_end") != null && entityMap.get("subj_code_end") != "") {
				//获取该会计科目对应的末级最大会计科目
				Map<String, Object> qMap = new HashMap<String, Object>();
				qMap.put("group_id", SessionManager.getGroupId());
				qMap.put("hos_id", SessionManager.getHosId());
				qMap.put("copy_code", SessionManager.getCopyCode());
				qMap.put("acc_year", entityMap.get("acc_year_b"));
				qMap.put("subj_code", entityMap.get("subj_code_end"));
				
				String subj_code_end = accSubjLedgerMapper.getSubjLastMaxCodeBySubjCode(qMap);
				
				subjSql.append(" and c.subj_code <= '").append(subj_code_end).append("'");
			} else {
				subjSql.append("");
			}

			if (entityMap.get("subj_level") != null && entityMap.get("subj_level") != "") {
				subjLevelSql.append("subj_level <= '").append(entityMap.get("subj_level").toString()).append("'");
			} else {
				subjLevelSql.append("");
			}

			copyCodeSql.append(" and a.copy_code in ('").append(entityMap.get("copy_code")).append("')");
			entityMap.put("copyCodeSql", copyCodeSql.toString());
		}
		
		entityMap.put("levelSql2", levelSql2.toString());
		
		//页面条件  优先级最高
		if(entityMap.get("isLastChk") != null && "0".equals(entityMap.get("isLastChk").toString())){
			subjSql.append(" and c.is_last = 1");
		}

		if ("99".equals(entityMap.get("is_state"))) {
			stateSql.append(" and v.state = 99 ");
		}else{
			stateSql.append(" and v.state >= 1 ");
		}

		if (subjCodeSql.length() > 22) {
			entityMap.put("subjCodeSql", subjCodeSql.toString());
		} else {
			entityMap.put("subjCodeSql", "");
		}

		entityMap.put("stateSql", stateSql.toString());
		entityMap.put("subjSql", subjSql.toString());
		entityMap.put("subjLevelSql", subjLevelSql.toString());
		entityMap.put("curSql", curSql.toString());

		List<Map<String, Object>> reList = accSubjLedgerMapper.collectBalanceLedgerDetail(entityMap);
		return reList;
	}

	/**
	 * 科目余额表查询
	 */
	@Override
	public String collectBalanceLedgerDetail(Map<String, Object> entityMap) throws DataAccessException {
		List<Map<String, Object>> reList = queryBalanceLedgerDetailList(entityMap);
		return ChdJson.toJson(reList);
	}

	/*
	 * 科目余额表
	 * 模板打印
	 */
	@Override
	public Map<String, Object> collectBalanceLedgerDetailPrint(Map<String, Object> entityMap) throws DataAccessException {
		Map<String, Object> reMap = new HashMap<String, Object>();
		List<Map<String, Object>> reList = queryBalanceLedgerDetailList(entityMap);
		reMap.put("detail", reList);
		return reMap;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public String collectAccExpendFinancial(Map<String, Object> entityMap) throws DataAccessException {

		accSubjLedgerMapper.collectAccExpendFinancial(entityMap);

		TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();

		return ChdJson.toJsonLower((List<Map<String, Object>>) entityMap.get("rst"));

	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public List<Map<String, Object>> collectAccExpendFinancialPrint(Map<String, Object> entityMap)
			throws DataAccessException {

		accSubjLedgerMapper.collectAccExpendFinancial(entityMap);

		TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();

		List<Map<String, Object>> resList = (List<Map<String, Object>>) entityMap.get("rst");

		return resList;

	}

	@Override
	public String queryAccSubjLederDetail(Map<String, Object> entityMap) throws DataAccessException {

		if ("01".equals(entityMap.get("acc_month"))) {
			entityMap.put("begin_month", "00");
		} else {
			String yearMonth = entityMap.get("acc_year").toString() + "-" + entityMap.get("acc_month").toString()
					+ "-01";

			Date date = DateUtil.stringToDate(yearMonth.toString(), "yyyy-MM-dd");
			Calendar c = Calendar.getInstance();
			c.setTime(date);
			c.add(Calendar.MONTH, -1);
			String month = String.valueOf(c.get(Calendar.MONTH) + 1);
			if (month.length() == 1) {
				month = "0" + month;
			}
			entityMap.put("begin_month", month);

		}

		return ChdJson.toJsonLower(accSubjLedgerMapper.queryAccSubjLederDetail(entityMap));
	}

	/**
	 * 科目余额明细表打印
	 */
	@Override
	public Map<String, Object> queryAccSubjLederDetailPrint(Map<String, Object> entityMap) throws DataAccessException {

		Map<String, Object> reMap = new HashMap<String, Object>();

		if ("01".equals(entityMap.get("acc_month"))) {
			entityMap.put("begin_month", "00");
		} else {
			String yearMonth = entityMap.get("acc_year").toString() + "-" + entityMap.get("acc_month").toString()
					+ "-01";

			Date date = DateUtil.stringToDate(yearMonth.toString(), "yyyy-MM-dd");
			Calendar c = Calendar.getInstance();
			c.setTime(date);
			c.add(Calendar.MONTH, -1);
			String month = String.valueOf(c.get(Calendar.MONTH) + 1);
			if (month.length() == 1) {
				month = "0" + month;
			}
			entityMap.put("begin_month", month);

		}

		List<Map<String, Object>> list = accSubjLedgerMapper.queryAccSubjLederDetail(entityMap);

		reMap.put("detail", list);

		return reMap;
	}

	@Override
	public String queryAccSubjLederCheck(Map<String, Object> entityMap) throws DataAccessException {

		StringBuffer sf = new StringBuffer();

		StringBuffer gf = new StringBuffer();

		StringBuffer cf = new StringBuffer();

		StringBuffer tf = new StringBuffer();

		StringBuffer type_id1 = new StringBuffer();

		StringBuffer type_id2 = new StringBuffer();

		StringBuffer type_id3 = new StringBuffer();

		StringBuffer type_id4 = new StringBuffer();

		String sortname = null;
		if (entityMap.get("sortname") != null) {
			sortname = entityMap.get("sortname").toString();
			entityMap.remove("sortname");
		}

		Map<String, Object> map = accLederMapper.queryCheckItemTable(entityMap);

		if (map != null) {

			map.put("COLUMN_CODE1", String.valueOf(map.get("COLUMN_CODE1")).toLowerCase());

			map.put("COLUMN_CODE2", String.valueOf(map.get("COLUMN_CODE2")).toLowerCase());

			map.put("COLUMN_CODE3", String.valueOf(map.get("COLUMN_CODE3")).toLowerCase());

			map.put("COLUMN_CODE4", String.valueOf(map.get("COLUMN_CODE4")).toLowerCase());

			if (!"ACC_CHECK_ITEM".equals(String.valueOf(map.get("TABLE1")))) {

				if (map.get("TABLE1") != null) {

					if (StringUtil.isBlank(String.valueOf(map.get("COLUMN_CHECK1")))) {

						map.put("COLUMN_CHECK1", "");

					} else {
						map.put("COLUMN_CHECK1", map.get("COLUMN_CHECK1") + "_ID");

						sf.append(String.valueOf(map.get("COLUMN_CODE1")).toLowerCase() + ",");

						sf.append(String.valueOf(map.get("COLUMN_NAME1")).toLowerCase() + ",");

						sf.append(map.get("COLUMN_ID1") + ",");

						if (!"".equals(String.valueOf(map.get("COLUMN_NO1")))
								&& null != String.valueOf(map.get("COLUMN_NO1"))) {

							sf.append(String.valueOf(map.get("COLUMN_NO1")).toLowerCase() + ",");

						}

						gf.append("value1,check1,");
						cf.append("c.value1,c.check1,");

						tf.append(" null as value1,'期末余额' as check1,");

					}

					if ("V_HOS_DICT".equals(String.valueOf(map.get("TABLE1")))
							|| "HOS_SOURCE".equals(String.valueOf(map.get("TABLE1")))) {
						map.put("COLUMN_NO1", "");
					} else {
						map.put("COLUMN_CHECK1_NO", map.get("COLUMN_CHECK1").toString().substring(0, 6) + "_NO");

					}

					// type_id1.append(" left join acc_check_type act on alc.group_id=act.group_id
					// and alc.hos_id=act.hos_id and alc.copy_code=act.copy_code and
					// act."+map.get("COLUMN_ID1")+"="+entityMap.get("check_type_id1")+"act.");

					map.put("check_type_id1", "");
				}

			} else {

				map.put("COLUMN_NO1", "");

				sf.append("a." + String.valueOf(map.get("COLUMN_CODE1")).toLowerCase() + ",");

				sf.append("a." + String.valueOf(map.get("COLUMN_NAME1")).toLowerCase() + ",");

				sf.append("a." + map.get("COLUMN_ID1") + ",");

				gf.append("value1,check1,");

				cf.append("c.value1,c.check1,");

				tf.append(" null as value1,'期末余额' as check1,");

				type_id1.append("  and  a." + map.get("COLUMN_ID1") + "=" + entityMap.get("check_type_id1"));

			}
			if (!"ACC_CHECK_ITEM".equals(String.valueOf(map.get("TABLE2")))) {

				if (map.get("TABLE2") != null) {

					if (map.get("COLUMN_CHECK2") == null) {

						map.put("COLUMN_CHECK2", "");

					} else {

						map.put("COLUMN_CHECK2", map.get("COLUMN_CHECK2") + "_ID");

						sf.append(String.valueOf(map.get("COLUMN_CODE2")).toLowerCase() + ",");

						sf.append(String.valueOf(map.get("COLUMN_NAME2")).toLowerCase() + ",");

						sf.append(map.get("COLUMN_ID2") + ",");

						if (!"".equals(String.valueOf(map.get("COLUMN_NO2")))
								&& null != String.valueOf(map.get("COLUMN_NO2"))) {

							sf.append(String.valueOf(map.get("COLUMN_NO2")).toLowerCase() + ",");

						}

						gf.append("value2,check2,");

						cf.append("c.value2,c.check2,");

						tf.append(" null as value2,'期末余额' as check2,");

					}
					if ("V_HOS_DICT".equals(String.valueOf(map.get("TABLE2")))
							|| "HOS_SOURCE".equals(String.valueOf(map.get("TABLE2")))) {
						map.put("COLUMN_NO2", "");
					} else {
						map.put("COLUMN_CHECK2_NO", map.get("COLUMN_CHECK2").toString().substring(0, 6) + "_NO");
					}

					type_id2.append("  and  b." + map.get("COLUMN_ID2") + "=" + entityMap.get("check_type_id2"));

					map.put("check_type_id2", "");
				}

			} else {

				map.put("COLUMN_NO2", "");

				sf.append("b." + String.valueOf(map.get("COLUMN_CODE2")).toLowerCase() + ",");

				sf.append("b." + String.valueOf(map.get("COLUMN_NAME2")).toLowerCase() + ",");

				sf.append("b." + map.get("COLUMN_ID2") + ",");

				gf.append("value2,check2,");

				cf.append("c.value2,c.check2,");

				tf.append(" null as value2,'期末余额' as check2,");

				type_id2.append("  and  b." + map.get("COLUMN_ID2") + "=" + entityMap.get("check_type_id2"));

			}
			if (!"ACC_CHECK_ITEM".equals(String.valueOf(map.get("TABLE3")))) {
				if (map.get("TABLE3") != null) {
					if (map.get("COLUMN_CHECK3") == null) {
						map.put("COLUMN_CHECK3", "");
					} else {
						map.put("COLUMN_CHECK3", map.get("COLUMN_CHECK3") + "_ID");

						sf.append(String.valueOf(map.get("COLUMN_CODE3")).toLowerCase() + ",");

						sf.append(String.valueOf(map.get("COLUMN_NAME3")).toLowerCase() + ",");

						sf.append(map.get("COLUMN_ID3") + ",");

						if (!"".equals(String.valueOf(map.get("COLUMN_NO3")))
								&& null != String.valueOf(map.get("COLUMN_NO3"))) {

							sf.append(String.valueOf(map.get("COLUMN_NO3")).toLowerCase() + ",");

						}

						gf.append("value3,check3,");

						cf.append("c.value3,c.check3,");

						tf.append(" null as value3,'期末余额' as check3,");

					}

					if ("V_HOS_DICT".equals(String.valueOf(map.get("TABLE3")))
							|| "HOS_SOURCE".equals(String.valueOf(map.get("TABLE3")))) {
						map.put("COLUMN_NO3", "");
					} else {
						map.put("COLUMN_CHECK3_NO", map.get("COLUMN_CHECK3").toString().substring(0, 6) + "_NO");
					}

					type_id3.append("  and  c." + map.get("COLUMN_ID3") + "=" + entityMap.get("check_type_id3"));

					map.put("check_type_id3", "");
				}

			} else {

				map.put("COLUMN_NO3", "");

				sf.append("c." + String.valueOf(map.get("COLUMN_CODE3")).toLowerCase() + ",");

				sf.append("c." + String.valueOf(map.get("COLUMN_NAME3")).toLowerCase() + ",");

				sf.append("c." + map.get("COLUMN_ID3") + ",");

				gf.append("value3,check3,");

				cf.append("c.value3,c.check3,");

				tf.append(" null as value3,'期末余额' as check3,");

				type_id3.append("  and  c." + map.get("COLUMN_ID3") + "=" + entityMap.get("check_type_id3"));

			}
			if (!"ACC_CHECK_ITEM".equals(String.valueOf(map.get("TABLE4")))) {
				if (map.get("TABLE4") != null) {
					if (map.get("COLUMN_CHECK4") == null) {
						map.put("COLUMN_CHECK4", "");
					} else {
						map.put("COLUMN_CHECK4", map.get("COLUMN_CHECK4") + "_ID");

						sf.append(String.valueOf(map.get("COLUMN_CODE4")).toLowerCase() + ",");

						sf.append(String.valueOf(map.get("COLUMN_NAME4")).toLowerCase() + ",");

						sf.append(map.get("COLUMN_ID4") + ",");

						if (!"".equals(String.valueOf(map.get("COLUMN_NO4")))
								&& null != String.valueOf(map.get("COLUMN_NO4"))) {

							sf.append(String.valueOf(map.get("COLUMN_NO4")).toLowerCase() + ",");

						}
						gf.append("value4,check4,");

						cf.append("c.value4,c.check4,");

						tf.append(" null as value4,'期末余额' as check4,");
					}
					if ("V_HOS_DICT".equals(String.valueOf(map.get("TABLE4")))
							|| "HOS_SOURCE".equals(String.valueOf(map.get("TABLE4")))) {
						map.put("COLUMN_NO4", "");
					} else {
						map.put("COLUMN_CHECK4_NO", map.get("COLUMN_CHECK4").toString().substring(0, 6) + "_NO");
					}

					type_id4.append("  and  d." + map.get("COLUMN_ID4") + "=" + entityMap.get("check_type_id4"));

					map.put("check_type_id4", "");
				}

			} else {

				map.put("COLUMN_NO4", "");

				sf.append("d." + String.valueOf(map.get("COLUMN_CODE4")).toLowerCase() + ",");

				sf.append("d." + String.valueOf(map.get("COLUMN_NAME4")).toLowerCase() + ",");

				sf.append("d." + map.get("COLUMN_ID4") + ",");

				gf.append("value4,check4,");

				cf.append("c.value4,c.check4,");

				tf.append(" null as value4,'期末余额' as check4,");

				type_id4.append("  and  d." + map.get("COLUMN_ID4") + "=" + entityMap.get("check_type_id4"));

			}

			map.put("querySql", gf.length() == 0 ? "" : gf.substring(0, gf.length() - 1));

			map.put("groupSql", sf.length() == 0 ? "" : sf.substring(0, sf.length() - 1));

			map.put("searchSql", cf.length() == 0 ? "" : cf.substring(0, cf.length() - 1));

			map.put("totalSql", tf.length() == 0 ? "" : tf.substring(0, tf.length() - 1));

			map.put("typeSql1", type_id1);

			map.put("typeSql2", type_id2);

			map.put("typeSql3", type_id3);

			map.put("typeSql4", type_id4);

			entityMap.putAll(map);

		}

		if (sortname != null) {
			entityMap.put("sortname", sortname);
		}
		List<Map<String, Object>> list = accSubjLedgerMapper.queryAccSubjLederCheck(entityMap);

		return ChdJson.toJson(list);
	}

	// 科目余额表 辅助核算打印
	@Override
	public List<Map<String, Object>> queryAccSubjLederCheckPrint(Map<String, Object> entityMap)
			throws DataAccessException {

		StringBuffer sf = new StringBuffer();

		StringBuffer gf = new StringBuffer();

		StringBuffer cf = new StringBuffer();

		StringBuffer tf = new StringBuffer();

		StringBuffer type_id1 = new StringBuffer();

		StringBuffer type_id2 = new StringBuffer();

		StringBuffer type_id3 = new StringBuffer();

		StringBuffer type_id4 = new StringBuffer();

		String sortname = null;
		if (entityMap.get("sortname") != null) {
			sortname = entityMap.get("sortname").toString();
			entityMap.remove("sortname");
		}

		Map<String, Object> map = accLederMapper.queryCheckItemTable(entityMap);

		if (map != null) {

			map.put("COLUMN_CODE1", String.valueOf(map.get("COLUMN_CODE1")).toLowerCase());

			map.put("COLUMN_CODE2", String.valueOf(map.get("COLUMN_CODE2")).toLowerCase());

			map.put("COLUMN_CODE3", String.valueOf(map.get("COLUMN_CODE3")).toLowerCase());

			map.put("COLUMN_CODE4", String.valueOf(map.get("COLUMN_CODE4")).toLowerCase());

			if (!"ACC_CHECK_ITEM".equals(String.valueOf(map.get("TABLE1")))) {

				if (map.get("TABLE1") != null) {

					if (StringUtil.isBlank(String.valueOf(map.get("COLUMN_CHECK1")))) {

						map.put("COLUMN_CHECK1", "");

					} else {
						map.put("COLUMN_CHECK1", map.get("COLUMN_CHECK1") + "_ID");

						sf.append(String.valueOf(map.get("COLUMN_CODE1")).toLowerCase() + ",");

						sf.append(String.valueOf(map.get("COLUMN_NAME1")).toLowerCase() + ",");

						sf.append(map.get("COLUMN_ID1") + ",");

						if (!"".equals(String.valueOf(map.get("COLUMN_NO1")))
								&& null != String.valueOf(map.get("COLUMN_NO1"))) {

							sf.append(String.valueOf(map.get("COLUMN_NO1")).toLowerCase() + ",");

						}

						gf.append("value1,check1,");
						cf.append("c.value1,c.check1,");

						tf.append(" null as value1,'期末余额' as check1,");

					}

					if ("V_HOS_DICT".equals(String.valueOf(map.get("TABLE1")))
							|| "HOS_SOURCE".equals(String.valueOf(map.get("TABLE1")))) {
						map.put("COLUMN_NO1", "");
					} else {
						map.put("COLUMN_CHECK1_NO", map.get("COLUMN_CHECK1").toString().substring(0, 6) + "_NO");

					}

					// type_id1.append(" left join acc_check_type act on alc.group_id=act.group_id
					// and alc.hos_id=act.hos_id and alc.copy_code=act.copy_code and
					// act."+map.get("COLUMN_ID1")+"="+entityMap.get("check_type_id1")+"act.");

					map.put("check_type_id1", "");
				}

			} else {

				map.put("COLUMN_NO1", "");

				sf.append("a." + String.valueOf(map.get("COLUMN_CODE1")).toLowerCase() + ",");

				sf.append("a." + String.valueOf(map.get("COLUMN_NAME1")).toLowerCase() + ",");

				sf.append("a." + map.get("COLUMN_ID1") + ",");

				gf.append("value1,check1,");

				cf.append("c.value1,c.check1,");

				tf.append(" null as value1,'期末余额' as check1,");

				type_id1.append("  and  a." + map.get("COLUMN_ID1") + "=" + entityMap.get("check_type_id1"));

			}
			if (!"ACC_CHECK_ITEM".equals(String.valueOf(map.get("TABLE2")))) {

				if (map.get("TABLE2") != null) {

					if (map.get("COLUMN_CHECK2") == null) {

						map.put("COLUMN_CHECK2", "");

					} else {

						map.put("COLUMN_CHECK2", map.get("COLUMN_CHECK2") + "_ID");

						sf.append(String.valueOf(map.get("COLUMN_CODE2")).toLowerCase() + ",");

						sf.append(String.valueOf(map.get("COLUMN_NAME2")).toLowerCase() + ",");

						sf.append(map.get("COLUMN_ID2") + ",");

						if (!"".equals(String.valueOf(map.get("COLUMN_NO2")))
								&& null != String.valueOf(map.get("COLUMN_NO2"))) {

							sf.append(String.valueOf(map.get("COLUMN_NO2")).toLowerCase() + ",");

						}

						gf.append("value2,check2,");

						cf.append("c.value2,c.check2,");

						tf.append(" null as value2,'期末余额' as check2,");

					}
					if ("V_HOS_DICT".equals(String.valueOf(map.get("TABLE2")))
							|| "HOS_SOURCE".equals(String.valueOf(map.get("TABLE2")))) {
						map.put("COLUMN_NO2", "");
					} else {
						map.put("COLUMN_CHECK2_NO", map.get("COLUMN_CHECK2").toString().substring(0, 6) + "_NO");
					}

					type_id2.append("  and  b." + map.get("COLUMN_ID2") + "=" + entityMap.get("check_type_id2"));

					map.put("check_type_id2", "");
				}

			} else {

				map.put("COLUMN_NO2", "");

				sf.append("b." + String.valueOf(map.get("COLUMN_CODE2")).toLowerCase() + ",");

				sf.append("b." + String.valueOf(map.get("COLUMN_NAME2")).toLowerCase() + ",");

				sf.append("b." + map.get("COLUMN_ID2") + ",");

				gf.append("value2,check2,");

				cf.append("c.value2,c.check2,");

				tf.append(" null as value2,'期末余额' as check2,");

				type_id2.append("  and  b." + map.get("COLUMN_ID2") + "=" + entityMap.get("check_type_id2"));

			}
			if (!"ACC_CHECK_ITEM".equals(String.valueOf(map.get("TABLE3")))) {
				if (map.get("TABLE3") != null) {
					if (map.get("COLUMN_CHECK3") == null) {
						map.put("COLUMN_CHECK3", "");
					} else {
						map.put("COLUMN_CHECK3", map.get("COLUMN_CHECK3") + "_ID");

						sf.append(String.valueOf(map.get("COLUMN_CODE3")).toLowerCase() + ",");

						sf.append(String.valueOf(map.get("COLUMN_NAME3")).toLowerCase() + ",");

						sf.append(map.get("COLUMN_ID3") + ",");

						if (!"".equals(String.valueOf(map.get("COLUMN_NO3")))
								&& null != String.valueOf(map.get("COLUMN_NO3"))) {

							sf.append(String.valueOf(map.get("COLUMN_NO3")).toLowerCase() + ",");

						}

						gf.append("value3,check3,");

						cf.append("c.value3,c.check3,");

						tf.append(" null as value3,'期末余额' as check3,");

					}

					if ("V_HOS_DICT".equals(String.valueOf(map.get("TABLE3")))
							|| "HOS_SOURCE".equals(String.valueOf(map.get("TABLE3")))) {
						map.put("COLUMN_NO3", "");
					} else {
						map.put("COLUMN_CHECK3_NO", map.get("COLUMN_CHECK3").toString().substring(0, 6) + "_NO");
					}

					type_id3.append("  and  c." + map.get("COLUMN_ID3") + "=" + entityMap.get("check_type_id3"));

					map.put("check_type_id3", "");
				}

			} else {

				map.put("COLUMN_NO3", "");

				sf.append("c." + String.valueOf(map.get("COLUMN_CODE3")).toLowerCase() + ",");

				sf.append("c." + String.valueOf(map.get("COLUMN_NAME3")).toLowerCase() + ",");

				sf.append("c." + map.get("COLUMN_ID3") + ",");

				gf.append("value3,check3,");

				cf.append("c.value3,c.check3,");

				tf.append(" null as value3,'期末余额' as check3,");

				type_id3.append("  and  c." + map.get("COLUMN_ID3") + "=" + entityMap.get("check_type_id3"));

			}
			if (!"ACC_CHECK_ITEM".equals(String.valueOf(map.get("TABLE4")))) {
				if (map.get("TABLE4") != null) {
					if (map.get("COLUMN_CHECK4") == null) {
						map.put("COLUMN_CHECK4", "");
					} else {
						map.put("COLUMN_CHECK4", map.get("COLUMN_CHECK4") + "_ID");

						sf.append(String.valueOf(map.get("COLUMN_CODE4")).toLowerCase() + ",");

						sf.append(String.valueOf(map.get("COLUMN_NAME4")).toLowerCase() + ",");

						sf.append(map.get("COLUMN_ID4") + ",");

						if (!"".equals(String.valueOf(map.get("COLUMN_NO4")))
								&& null != String.valueOf(map.get("COLUMN_NO4"))) {

							sf.append(String.valueOf(map.get("COLUMN_NO4")).toLowerCase() + ",");

						}
						gf.append("value4,check4,");

						cf.append("c.value4,c.check4,");

						tf.append(" null as value4,'期末余额' as check4,");
					}
					if ("V_HOS_DICT".equals(String.valueOf(map.get("TABLE4")))
							|| "HOS_SOURCE".equals(String.valueOf(map.get("TABLE4")))) {
						map.put("COLUMN_NO4", "");
					} else {
						map.put("COLUMN_CHECK4_NO", map.get("COLUMN_CHECK4").toString().substring(0, 6) + "_NO");
					}

					type_id4.append("  and  d." + map.get("COLUMN_ID4") + "=" + entityMap.get("check_type_id4"));

					map.put("check_type_id4", "");
				}

			} else {

				map.put("COLUMN_NO4", "");

				sf.append("d." + String.valueOf(map.get("COLUMN_CODE4")).toLowerCase() + ",");

				sf.append("d." + String.valueOf(map.get("COLUMN_NAME4")).toLowerCase() + ",");

				sf.append("d." + map.get("COLUMN_ID4") + ",");

				gf.append("value4,check4,");

				cf.append("c.value4,c.check4,");

				tf.append(" null as value4,'期末余额' as check4,");

				type_id4.append("  and  d." + map.get("COLUMN_ID4") + "=" + entityMap.get("check_type_id4"));

			}

			map.put("querySql", gf.substring(0, gf.length() - 1));

			map.put("groupSql", sf.substring(0, sf.length() - 1));

			map.put("searchSql", cf.substring(0, cf.length() - 1));

			map.put("totalSql", tf.substring(0, tf.length() - 1));

			map.put("typeSql1", type_id1);

			map.put("typeSql2", type_id2);

			map.put("typeSql3", type_id3);

			map.put("typeSql4", type_id4);

			entityMap.putAll(map);

		}

		if (sortname != null) {
			entityMap.put("sortname", sortname);
		}
		List<Map<String, Object>> list = accSubjLedgerMapper.queryAccSubjLederCheck(entityMap);

		return list;
	}

	@Override
	public String queryAccSubjByPlan(Map<String, Object> entityMap) throws DataAccessException {

		List<Map<String, Object>> list = accSubjLedgerMapper.queryAccSubjByPlan(entityMap);

		return ChdJson.toJson(list);
	}

	/**
	 * 科目汇总查询统计凭证号
	 */
	@Override
	public String queryAccVouchCountSum(Map<String, Object> entityMap) throws DataAccessException {

		StringBuffer stateSql = new StringBuffer();
		StringBuffer subjSql = new StringBuffer();
		if ("1".equals(entityMap.get("is_state"))) {
			stateSql.append(" and a.state >= 1 ");
		}
		if ("99".equals(entityMap.get("is_state"))) {
			stateSql.append(" and a.state = 99 ");
		}

		entityMap.put("stateSql", stateSql.toString());

		if ("4".equals(entityMap.get("subj_select_flag"))) {
			subjSql.append(" and c.subj_code in (").append(entityMap.get("subj_codes").toString()).append(") ");
		} else if ("3".equals(entityMap.get("subj_select_flag"))) {
			String[] ids = entityMap.get("subj_codes").toString().split(",");
			subjSql.append(" and c.subj_code between '").append(ids[0]).append("' and  '").append(ids[1]).append("'  ");
		} else if ("2".equals(entityMap.get("subj_select_flag"))) {
			subjSql.append(" and c.subj_code like '").append(entityMap.get("subj_codes").toString()).append("%' ");
		} else {
			subjSql.append("");
		}

		entityMap.put("subjSql", subjSql.toString());

		List<Map<String, Object>> list = JsonListMapUtil
				.toListMapLower(accSubjLedgerMapper.queryAccVouchCountSum(entityMap));

		StringBuffer vouch_no = new StringBuffer();
		StringBuffer vouch_num = new StringBuffer();

		int a = 0;
		for (Map<String, Object> map : list) {
			int index = vouch_no.indexOf(map.get("vouch_type_short").toString());
			if (index < 0) {
				if (vouch_no.length() > 0) {
					vouch_no.append(",");
				}
				vouch_no.append(map.get("vouch_type_short").toString()).append(": ")
						.append(Integer.parseInt(map.get("vouch_min").toString()));
				vouch_no.append("至").append(Integer.parseInt(map.get("vouch_max").toString()));
			}

			if (a == 0) {
				vouch_num.append(" 凭证总张数:").append(map.get("vouch_num").toString()).append("、其中作废凭证张数为:")
						.append(map.get("vouch_void").toString());
				vouch_num.append("、附原始单据张数: ");
				vouch_num.append(Integer.parseInt(map.get("vouch_att_sums").toString()));
			}
			a++;
		}

		return "{\"vouch_no\":\"" + vouch_no.toString() + "\",\"vouch_num\":\"" + vouch_num.toString() + "\"}";
	}

	/**
	 * 科目余额表 辅助核算明细
	 */
	@Override
	public String queryAccSubjLedgerCheckDetail(Map<String, Object> entityMap) throws DataAccessException {
		// 看科目挂有那个辅助核算
		StringBuffer columnName = new StringBuffer();
		StringBuffer joinSql = new StringBuffer();
		StringBuffer whereSql = new StringBuffer();
		StringBuffer showColumn = new StringBuffer();
		StringBuffer groupColumn = new StringBuffer();

		List<Map<String, Object>> sqlList = accNostroMapper.querySubjCheckColumnBySubjList(entityMap);

		int a = 1;
		for (Map<String, Object> sqlMap : sqlList) {

			columnName.append(" ,dd").append(String.valueOf(a)).append(".").append(sqlMap.get("COLUMN_CODE"))
					.append(" ||' '|| dd").append(String.valueOf(a)).append(".")
					.append(sqlMap.get("COLUMN_NAME").toString()).append(" as value").append(String.valueOf(a))
					.append(" ");
			showColumn.append(",value").append(String.valueOf(a).toString()).append(" ");
			groupColumn.append(" ,dd").append(String.valueOf(a)).append(".").append(sqlMap.get("COLUMN_CODE"))
					.append(",dd").append(String.valueOf(a)).append(".").append(sqlMap.get("COLUMN_NAME").toString())
					.append(" ");

			// 是否是变更表
			if (sqlMap.get("CHECK_TABLE") != null && !"".equals(sqlMap.get("CHECK_TABLE").toString())) {
				if (sqlMap.get("IS_CHANGE") != null && "1".equals(sqlMap.get("IS_CHANGE").toString())) {

					joinSql.append(" left join ").append(sqlMap.get("CHECK_TABLE")).append(" dd")
							.append(String.valueOf(a)).append(" ");
					joinSql.append(" on dd").append(String.valueOf(a)).append(".group_id = avc.group_id and dd")
							.append(String.valueOf(a)).append(".hos_id = avc.hos_id  and dd").append(String.valueOf(a))
							.append(".").append(sqlMap.get("COLUMN_ID")).append("= avc.")
							.append(sqlMap.get("COLUMN_CHECK")).append("_ID");
					joinSql.append(" and dd").append(String.valueOf(a)).append(".").append(sqlMap.get("COLUMN_NO"))
							.append(" = avc.").append(sqlMap.get("COLUMN_CHECK").toString()).append("_NO");
					if (!"".equals(entityMap.get("value" + a))
							&& !"undefined".equals(entityMap.get("value" + a).toString())) {
						// showColumn.append(",value1");
						if (entityMap.get("value" + a).toString().contains(".")) {
							String[] s = entityMap.get("value" + a).toString().split("\\.");
							whereSql.append(" and dd").append(String.valueOf(a)).append(".")
									.append(sqlMap.get("COLUMN_ID")).append(" = ").append(s[0]);
							whereSql.append(" and dd").append(String.valueOf(a)).append(".")
									.append(sqlMap.get("COLUMN_NO")).append(" = ").append(s[1]);
						}
					}

				} else {
					// 自定义辅助核算
					if (sqlMap.get("CHECK_TABLE").equals("ACC_CHECK_ITEM")) {
						joinSql.append("  left join acc_check_item dd").append(String.valueOf(a)).append(" on dd")
								.append(String.valueOf(a)).append(".group_id = avc.group_id and dd")
								.append(String.valueOf(a)).append(".hos_id = avc.hos_id and dd")
								.append(String.valueOf(a)).append(".copy_code = avc.copy_code and dd")
								.append(String.valueOf(a)).append(".").append(sqlMap.get("COLUMN_ID")).append(" = avc.")
								.append(sqlMap.get("COLUMN_CHECK"));
						if (!"".equals(entityMap.get("value" + a))
								&& !"undefined".equals(entityMap.get("value" + a).toString())) {
							if (!entityMap.get("value" + a).toString().contains(".")) {
								whereSql.append(" and dd").append(String.valueOf(a)).append(".check_item_id = ")
										.append(entityMap.get("value" + a).toString());
							}

						}
						if (!"".equals(entityMap.get("check_type_id" + a))
								&& null != entityMap.get("check_type_id" + a)) {

							whereSql.append(" and dd").append(String.valueOf(a)).append(".").append("check_type_id")
									.append(" = ").append(entityMap.get("check_type_id" + a).toString());

						}

					} else {

						// 资金来源、单位
						joinSql.append(" left join ").append(sqlMap.get("CHECK_TABLE")).append(" dd")
								.append(String.valueOf(a)).append(" ");
						joinSql.append(" on dd").append(String.valueOf(a)).append(".group_id = avc.group_id and dd")
								.append(String.valueOf(a)).append(".hos_id = avc.hos_id and dd")
								.append(String.valueOf(a)).append(".").append(sqlMap.get("COLUMN_ID")).append("= avc.")
								.append(sqlMap.get("COLUMN_CHECK")).append("_ID");
						if (!"".equals(entityMap.get("value" + a))
								&& !"undefined".equals(entityMap.get("value" + a).toString())) {
							if (!entityMap.get("value" + a).toString().contains(".")) {
								whereSql.append(" and dd").append(String.valueOf(a)).append(".")
										.append(sqlMap.get("COLUMN_ID")).append(" = ")
										.append(entityMap.get("value" + a).toString());
							}

						}
						
					}
				}
			}
			a++;
		}

		entityMap.put("joinSql", joinSql.toString());
		entityMap.put("columnName", columnName.toString());
		entityMap.put("whereSql", whereSql.toString());
		entityMap.put("showColumn", showColumn.toString());
		entityMap.put("groupColumn", groupColumn.toString());

		if ("01".equals(entityMap.get("acc_month"))) {
			entityMap.put("begin_month", "00");
		} else {
			String yearMonth = entityMap.get("acc_year").toString() + "-" + entityMap.get("acc_month").toString()
					+ "-01";

			Date date = DateUtil.stringToDate(yearMonth.toString(), "yyyy-MM-dd");
			Calendar c = Calendar.getInstance();
			c.setTime(date);
			c.add(Calendar.MONTH, -1);
			String month = String.valueOf(c.get(Calendar.MONTH) + 1);
			if (month.length() == 1) {
				month = "0" + month;
			}
			entityMap.put("begin_month", month);

		}

		List<Map<String, Object>> list = accSubjLedgerMapper.queryAccSubjLedgerCheckDetail(entityMap);

		return ChdJson.toJsonLower(list);
	}

	/**
	 * 科目余额表 辅助核算明细打印
	 */
	@Override
	public List<Map<String, Object>> queryAccSubjLedgerCheckDetailPrint(Map<String, Object> entityMap)
			throws DataAccessException {
		// 看科目挂有那个辅助核算
		StringBuffer columnName = new StringBuffer();
		StringBuffer joinSql = new StringBuffer();
		StringBuffer whereSql = new StringBuffer();
		StringBuffer showColumn = new StringBuffer();
		StringBuffer groupColumn = new StringBuffer();

		List<Map<String, Object>> sqlList = accNostroMapper.querySubjCheckColumnBySubjList(entityMap);

		int a = 1;
		for (Map<String, Object> sqlMap : sqlList) {

			columnName.append(" ,dd").append(String.valueOf(a)).append(".").append(sqlMap.get("COLUMN_CODE"))
					.append(" ||' '|| dd").append(String.valueOf(a)).append(".")
					.append(sqlMap.get("COLUMN_NAME").toString()).append(" as value").append(String.valueOf(a))
					.append(" ");
			showColumn.append(",value").append(String.valueOf(a).toString()).append(" ");
			groupColumn.append(" ,dd").append(String.valueOf(a)).append(".").append(sqlMap.get("COLUMN_CODE"))
					.append(",dd").append(String.valueOf(a)).append(".").append(sqlMap.get("COLUMN_NAME").toString())
					.append(" ");

			// 是否是变更表
			if (sqlMap.get("CHECK_TABLE") != null && !"".equals(sqlMap.get("CHECK_TABLE").toString())) {
				if (sqlMap.get("IS_CHANGE") != null && "1".equals(sqlMap.get("IS_CHANGE").toString())) {

					joinSql.append(" left join ").append(sqlMap.get("CHECK_TABLE")).append(" dd")
							.append(String.valueOf(a)).append(" ");
					joinSql.append(" on dd").append(String.valueOf(a)).append(".group_id = avc.group_id and dd")
							.append(String.valueOf(a)).append(".hos_id = avc.hos_id  and dd").append(String.valueOf(a))
							.append(".").append(sqlMap.get("COLUMN_ID")).append("= avc.")
							.append(sqlMap.get("COLUMN_CHECK")).append("_ID");
					joinSql.append(" and dd").append(String.valueOf(a)).append(".").append(sqlMap.get("COLUMN_NO"))
							.append(" = avc.").append(sqlMap.get("COLUMN_CHECK").toString()).append("_NO");
					if (!"".equals(entityMap.get("value" + a))
							&& !"undefined".equals(entityMap.get("value" + a).toString())) {
						// showColumn.append(",value1");
						if (entityMap.get("value" + a).toString().contains(".")) {
							String[] s = entityMap.get("value" + a).toString().split("\\.");
							whereSql.append(" and dd").append(String.valueOf(a)).append(".")
									.append(sqlMap.get("COLUMN_ID")).append(" = ").append(s[0]);
							whereSql.append(" and dd").append(String.valueOf(a)).append(".")
									.append(sqlMap.get("COLUMN_NO")).append(" = ").append(s[1]);
						}
					}
					/*
					 * if(!"".equals(entityMap.get("check_type_id1")) && null !=
					 * entityMap.get("check_type_id1")){
					 * 
					 * //系统辅助核算不用加check_type_id
					 * //whereSql.append(" and dd").append(String.valueOf(a)).append(".").append(
					 * "check_type_id").append(" = ").append(entityMap.get("check_type_id1").
					 * toString());
					 * 
					 * } if(!"".equals(entityMap.get("value2")) &&
					 * !"undefined".equals(entityMap.get("value2").toString())){
					 * //showColumn.append(",value2");
					 * if(entityMap.get("value2").toString().contains(".")){ String[] s =
					 * entityMap.get("value2").toString().split("\\.");
					 * whereSql.append(" and dd").append(String.valueOf(a)).append(".").append(
					 * sqlMap.get("COLUMN_ID")).append(" = ").append(s[0]);
					 * whereSql.append(" and dd").append(String.valueOf(a)).append(".").append(
					 * sqlMap.get("COLUMN_NO")).append(" = ").append(s[1]); } }
					 * if(!"".equals(entityMap.get("check_type_id2")) && null !=
					 * entityMap.get("check_type_id2")){
					 * 
					 * //系统辅助核算不用加check_type_id //
					 * whereSql.append(" and dd").append(String.valueOf(a)).append(".").append(
					 * "check_type_id").append(" = ").append(entityMap.get("check_type_id2").
					 * toString());
					 * 
					 * } if(!"".equals(entityMap.get("value3")) &&
					 * !"undefined".equals(entityMap.get("value3").toString())){
					 * //showColumn.append(",value3");
					 * if(entityMap.get("value3").toString().contains(".")){ String[] s =
					 * entityMap.get("value3").toString().split("\\.");
					 * whereSql.append(" and dd").append(String.valueOf(a)).append(".").append(
					 * sqlMap.get("COLUMN_ID")).append(" = ").append(s[0]);
					 * whereSql.append(" and dd").append(String.valueOf(a)).append(".").append(
					 * sqlMap.get("COLUMN_NO")).append(" = ").append(s[1]); } }
					 * if(!"".equals(entityMap.get("check_type_id3")) && null !=
					 * entityMap.get("check_type_id3")){
					 * 
					 * //系统辅助核算不用加check_type_id //
					 * whereSql.append(" and dd").append(String.valueOf(a)).append(".").append(
					 * "check_type_id").append(" = ").append(entityMap.get("check_type_id3").
					 * toString());
					 * 
					 * } if(!"".equals(entityMap.get("value4")) &&
					 * !"undefined".equals(entityMap.get("value4").toString())){
					 * //showColumn.append(",value4");
					 * if(entityMap.get("value4").toString().contains(".")){ String[] s =
					 * entityMap.get("value4").toString().split("\\.");
					 * whereSql.append(" and dd").append(String.valueOf(a)).append(".").append(
					 * sqlMap.get("COLUMN_ID")).append(" = ").append(s[0]);
					 * whereSql.append(" and dd").append(String.valueOf(a)).append(".").append(
					 * sqlMap.get("COLUMN_NO")).append(" = ").append(s[1]); } }
					 * if(!"".equals(entityMap.get("check_type_id4")) && null !=
					 * entityMap.get("check_type_id4")){
					 * 
					 * //系统辅助核算不用加check_type_id
					 * //whereSql.append(" and dd").append(String.valueOf(a)).append(".").append(
					 * "check_type_id").append(" = ").append(entityMap.get("check_type_id4").
					 * toString());
					 * 
					 * }
					 */

				} else {
					// 自定义辅助核算
					if (sqlMap.get("CHECK_TABLE").equals("ACC_CHECK_ITEM")) {
						joinSql.append("  left join acc_check_item dd").append(String.valueOf(a)).append(" on dd")
								.append(String.valueOf(a)).append(".group_id = avc.group_id and dd")
								.append(String.valueOf(a)).append(".hos_id = avc.hos_id and dd")
								.append(String.valueOf(a)).append(".copy_code = avc.copy_code and dd")
								.append(String.valueOf(a)).append(".").append(sqlMap.get("COLUMN_ID")).append(" = avc.")
								.append(sqlMap.get("COLUMN_CHECK"));
						if (!"".equals(entityMap.get("value" + a))
								&& !"undefined".equals(entityMap.get("value" + a).toString())) {
							if (!entityMap.get("value" + a).toString().contains(".")) {
								whereSql.append(" and dd").append(String.valueOf(a)).append(".check_item_id = ")
										.append(entityMap.get("value" + a).toString());
							}

						}
						if (!"".equals(entityMap.get("check_type_id" + a))
								&& null != entityMap.get("check_type_id" + a)) {

							whereSql.append(" and dd").append(String.valueOf(a)).append(".").append("check_type_id")
									.append(" = ").append(entityMap.get("check_type_id" + a).toString());

						}

					} else {

						// 资金来源、单位
						joinSql.append(" left join ").append(sqlMap.get("CHECK_TABLE")).append(" dd")
								.append(String.valueOf(a)).append(" ");
						joinSql.append(" on dd").append(String.valueOf(a)).append(".group_id = avc.group_id and dd")
								.append(String.valueOf(a)).append(".hos_id = avc.hos_id and dd")
								.append(String.valueOf(a)).append(".").append(sqlMap.get("COLUMN_ID")).append("= avc.")
								.append(sqlMap.get("COLUMN_CHECK")).append("_ID");
						if (!"".equals(entityMap.get("value" + a))
								&& !"undefined".equals(entityMap.get("value" + a).toString())) {
							if (!entityMap.get("value" + a).toString().contains(".")) {
								whereSql.append(" and dd").append(String.valueOf(a)).append(".")
										.append(sqlMap.get("COLUMN_ID")).append(" = ")
										.append(entityMap.get("value" + a).toString());
							}

						}
						/*
						 * if(!"".equals(entityMap.get("check_type_id1")) && null !=
						 * entityMap.get("check_type_id1")){ //资金来源不用加check_type_id
						 * //whereSql.append(" and dd").append(String.valueOf(a)).append(".").append(
						 * "check_type_id").append(" = ").append(entityMap.get("check_type_id1").
						 * toString());
						 * 
						 * } if(!"".equals(entityMap.get("value2")) &&
						 * !"undefined".equals(entityMap.get("value2").toString())){
						 * if(!entityMap.get("value2").toString().contains(".")){
						 * whereSql.append(" and dd").append(String.valueOf(a)).append(".").append(
						 * sqlMap.get("COLUMN_ID")).append(" = ").append(entityMap.get("value2").
						 * toString()); } } if(!"".equals(entityMap.get("check_type_id2")) && null !=
						 * entityMap.get("check_type_id2")){ //资金来源不用加check_type_id
						 * //whereSql.append(" and dd").append(String.valueOf(a)).append(".").append(
						 * "check_type_id").append(" = ").append(entityMap.get("check_type_id2").
						 * toString());
						 * 
						 * } if(!"".equals(entityMap.get("value3")) &&
						 * !"undefined".equals(entityMap.get("value3").toString())){
						 * if(!entityMap.get("value3").toString().contains(".")){
						 * whereSql.append(" and dd").append(String.valueOf(a)).append(".").append(
						 * sqlMap.get("COLUMN_ID")).append(" = ").append(entityMap.get("value3").
						 * toString()); } } if(!"".equals(entityMap.get("check_type_id3")) && null !=
						 * entityMap.get("check_type_id3")){ //资金来源不用加check_type_id
						 * //whereSql.append(" and dd").append(String.valueOf(a)).append(".").append(
						 * "check_type_id").append(" = ").append(entityMap.get("check_type_id3").
						 * toString());
						 * 
						 * } if(!"".equals(entityMap.get("value4")) &&
						 * !"undefined".equals(entityMap.get("value4").toString())){
						 * if(!entityMap.get("value4").toString().contains(".")){
						 * whereSql.append(" and dd").append(String.valueOf(a)).append(".").append(
						 * sqlMap.get("COLUMN_ID")).append(" = ").append(entityMap.get("value4").
						 * toString()); } } if(!"".equals(entityMap.get("check_type_id4")) && null !=
						 * entityMap.get("check_type_id4")){ //资金来源不用加check_type_id
						 * //whereSql.append(" and dd").append(String.valueOf(a)).append(".").append(
						 * "check_type_id").append(" = ").append(entityMap.get("check_type_id4").
						 * toString());
						 * 
						 * }
						 */
					}
				}
			}
			a++;
		}

		entityMap.put("joinSql", joinSql.toString());
		entityMap.put("columnName", columnName.toString());
		entityMap.put("whereSql", whereSql.toString());
		entityMap.put("showColumn", showColumn.toString());
		entityMap.put("groupColumn", groupColumn.toString());

		if ("01".equals(entityMap.get("acc_month"))) {
			entityMap.put("begin_month", "00");
		} else {
			String yearMonth = entityMap.get("acc_year").toString() + "-" + entityMap.get("acc_month").toString()
					+ "-01";

			Date date = DateUtil.stringToDate(yearMonth.toString(), "yyyy-MM-dd");
			Calendar c = Calendar.getInstance();
			c.setTime(date);
			c.add(Calendar.MONTH, -1);
			String month = String.valueOf(c.get(Calendar.MONTH) + 1);
			if (month.length() == 1) {
				month = "0" + month;
			}
			entityMap.put("begin_month", month);

		}

		List<Map<String, Object>> list = accSubjLedgerMapper.queryAccSubjLedgerCheckDetail(entityMap);

		return list;
	}

	/**
	 * 科目汇总表查询
	 */
	@Override
	public String queryAccVouchCountSumDetail(Map<String, Object> entityMap) throws DataAccessException {

		StringBuffer stateSql = new StringBuffer();
		StringBuffer subjSql = new StringBuffer();
		StringBuffer subjCodeSql = new StringBuffer();
		// StringBuffer subjCodeSql2 = new StringBuffer();
		StringBuffer levelSql2 = new StringBuffer();
		StringBuffer curSql = new StringBuffer();
		StringBuffer copyCodeSql = new StringBuffer();

		subjCodeSql.append(" and c.subj_code in ( ");
		Map<String, String> copyMap = new HashMap<>();
		// subjCodeSql2.append(" and ( ");

		int a = 0;
		int size = 0;
		if (entityMap.get("sch_id") != null && entityMap.get("sch_id") != "") {
			List<Map<String, Object>> mapList = accSubjLedgerMapper.queryAccBookSch(entityMap);
			size = mapList.size();
			for (Map<String, Object> map : mapList) {
				a++;
				// 方案中是否勾选包含未记账
				if (Integer.parseInt(map.get("is_nacc").toString()) == 1) {
					entityMap.put("is_state", "1");
				} else {
					entityMap.put("is_state", "99");
				}

				if (!"".equals(map.get("p_copy_codes"))) {
					copyCodeSql.append(" and c.copy_code in ( ");
					String copyCodes = map.get("p_copy_codes").toString().replace(";", "','");
					String copyCode = copyCodes.substring(2, copyCodes.length() - 2);
					if (copyMap.get(copyCode) == null) {
						copyMap.put(copyCode, copyCode);
						copyCodeSql.append(copyCode).append(" ) ");
						entityMap.put("copyCodeSql", copyCodeSql.toString());
					}
				}

				// 方案中是否勾选范围查询
				if (Integer.parseInt(map.get("is_fw").toString()) == 1) {
					if (Integer.parseInt(map.get("subj_level_begin").toString()) == 99) {
						subjSql.append(" and c.subj_code >= '").append(map.get("subj_code_begin"))
								.append("99999999999999999'");
						subjSql.append(" and c.subj_code <= '").append(map.get("subj_code_end")).append("'");
					} else if (Integer.parseInt(map.get("subj_level_end").toString()) == 99) {
						subjSql.append(" and c.subj_code >= '").append(map.get("subj_code_begin")).append("'");
						subjSql.append(" and c.subj_code <= '").append(map.get("subj_code_end"))
								.append("99999999999999999'");
					} else {
						subjSql.append(" and c.subj_code >= '").append(map.get("subj_code_begin")).append("'");// .append("'
																												// and
																												// '").append(map.get("subj_code_end")).append("'
																												// ");
						subjSql.append(" and c.subj_code <= '").append(map.get("subj_code_end")).append("'");// .append("'
																												// and
																												// '").append(map.get("subj_code_end")).append("'
																												// ");
					}
				} else {
					// if(Integer.parseInt(map.get("subj_is_last").toString())==1){
					if (a < size) {
						subjCodeSql.append("'").append(map.get("subj_code").toString());
						if (a % 1000 == 0) {
							subjCodeSql.append("') or c.subj_code in ( ");
						} else {
							subjCodeSql.append("',");
						}
					} else {
						subjCodeSql.append("'").append(map.get("subj_code").toString()).append("')");
					}
					/*
					 * }else{
					 * subjCodeSql2.append(" c.subj_code like '").append(map.get("subj_code").
					 * toString()).append("' ") }
					 */
				}

				entityMap.put("cur_code", map.get("cur_code").toString());
				entityMap.put("is_fw", map.get("is_fw").toString());
				entityMap.put("subj_level_begin", map.get("subj_level_begin").toString());
				entityMap.put("subj_level_end", map.get("subj_level_end").toString());
				entityMap.put("is_last", map.get("is_last").toString());
				entityMap.put("is_bqwfs", map.get("is_bqwfs").toString());
			}

			if (!(entityMap.get("cur_code") == null || "".equals(entityMap.get("cur_code").toString()))) {
				curSql.append(" and v.cur_code = '").append(entityMap.get("cur_code").toString()).append("' ");
			} else {
				curSql.append("");
			}

			// 方案中是否选择科目级次
			if ((entityMap.get("subj_level_begin") != null && entityMap.get("subj_level_end") != null)
					&& (Integer.parseInt(entityMap.get("subj_level_begin").toString()) != 0
							|| Integer.parseInt(entityMap.get("subj_level_end").toString()) != 0)) {
				if (Integer.parseInt(entityMap.get("subj_level_begin").toString()) == 99
						|| Integer.parseInt(entityMap.get("subj_level_end").toString()) == 99) {
					levelSql2.append(" and  c.is_last = 1 ");
				} else {
					levelSql2.append(" and c.subj_level between '").append(entityMap.get("subj_level_begin"))
							.append("' and  '").append(entityMap.get("subj_level_end")).append("'  ");
				}
			} else {
				// 按照方案中选择的树形菜单里面的科目进行查询
				levelSql2.append("");
			}
		} else {
			// 不选方案，单选页面上的科目查询
			if (entityMap.get("subj_code") != null && entityMap.get("subj_code") != "") {
				subjSql.append(" and c.subj_code like '").append(entityMap.get("subj_code").toString()).append("%'");
			} else {
				subjSql.append("");
			}

			copyCodeSql.append(" and c.copy_code in ('").append(entityMap.get("copy_code")).append("')");
			entityMap.put("copyCodeSql", copyCodeSql.toString());
		}
		
		//页面条件  优先级最高
		if(entityMap.get("isLastChk") != null && "0".equals(entityMap.get("isLastChk").toString())){
			subjSql.append(" and c.is_last = 1");
		}

		entityMap.put("levelSql2", levelSql2.toString());

		if ("1".equals(entityMap.get("is_state"))) {
			stateSql.append(" and a.state >= 1 ");
		}
		if ("99".equals(entityMap.get("is_state"))) {
			stateSql.append(" and a.state = 99 ");
		}

		if (subjCodeSql.length() > 22) {
			entityMap.put("subjCodeSql", subjCodeSql.toString());
		} else {
			entityMap.put("subjCodeSql", "");
		}

		entityMap.put("stateSql", stateSql.toString());
		entityMap.put("subjSql", subjSql.toString());
		entityMap.put("curSql", curSql.toString());

		
		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");
		RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

		List<Map<String, Object>> list = accSubjLedgerMapper.queryAccVouchCountSumDetail(entityMap,rowBounds);

		PageInfo page = new PageInfo(list);
		
		
		return ChdJson.toJsonLower(list, page.getTotal());

	}

	/**
	 * 科目汇总表打印
	 */
	@Override
	public Map<String, Object> collectAccSubjLedgerSumMainPrint(Map<String, Object> entityMap)
			throws DataAccessException {

		Map<String, Object> reMap = new HashMap<String, Object>();

		StringBuffer stateSql = new StringBuffer();
		StringBuffer subjSql = new StringBuffer();
		StringBuffer subjCodeSql = new StringBuffer();
		// StringBuffer subjCodeSql2 = new StringBuffer();
		StringBuffer levelSql2 = new StringBuffer();
		StringBuffer curSql = new StringBuffer();
		StringBuffer copyCodeSql = new StringBuffer();

		subjCodeSql.append(" and c.subj_code in ( ");
		// subjCodeSql2.append(" and ( ");

		int a = 0;
		int size = 0;
		if (entityMap.get("sch_id") != null && entityMap.get("sch_id") != "") {
			List<Map<String, Object>> mapList = accSubjLedgerMapper.queryAccBookSch(entityMap);
			size = mapList.size();
			for (Map<String, Object> map : mapList) {
				a++;
				// 方案中是否勾选包含未记账
				if (Integer.parseInt(map.get("is_nacc").toString()) == 1) {
					entityMap.put("is_state", "1");
				} else {
					entityMap.put("is_state", "99");
				}

				if (!"".equals(map.get("p_copy_codes"))) {
					copyCodeSql.append(" and c.copy_code in ( ");
					String copyCodes = map.get("p_copy_codes").toString().replace(";", "','");
					copyCodeSql.append(copyCodes.substring(2, copyCodes.length() - 2)).append(" ) ");
					entityMap.put("copyCodeSql", copyCodeSql.toString());
				}

				// 方案中是否勾选范围查询
				if (Integer.parseInt(map.get("is_fw").toString()) == 1) {
					if (Integer.parseInt(map.get("subj_level_begin").toString()) == 99) {
						subjSql.append(" and c.subj_code >= '").append(map.get("subj_code_begin"))
								.append("99999999999999999'");
						subjSql.append(" and c.subj_code <= '").append(map.get("subj_code_end")).append("'");
					} else if (Integer.parseInt(map.get("subj_level_end").toString()) == 99) {
						subjSql.append(" and c.subj_code >= '").append(map.get("subj_code_begin")).append("'");
						subjSql.append(" and c.subj_code <= '").append(map.get("subj_code_end"))
								.append("99999999999999999'");
					} else {
						subjSql.append(" and c.subj_code >= '").append(map.get("subj_code_begin")).append("'");// .append("'
																												// and
																												// '").append(map.get("subj_code_end")).append("'
																												// ");
						subjSql.append(" and c.subj_code <= '").append(map.get("subj_code_end")).append("'");// .append("'
																												// and
																												// '").append(map.get("subj_code_end")).append("'
																												// ");
					}
				} else {
					// if(Integer.parseInt(map.get("subj_is_last").toString())==1){
					if (a < size) {
						subjCodeSql.append("'").append(map.get("subj_code").toString()).append("',");
					} else {
						subjCodeSql.append("'").append(map.get("subj_code").toString()).append("')");
					}
					/*
					 * }else{
					 * subjCodeSql2.append(" c.subj_code like '").append(map.get("subj_code").
					 * toString()).append("' ") }
					 */
				}

				entityMap.put("cur_code", map.get("cur_code").toString());
				entityMap.put("is_fw", map.get("is_fw").toString());
				entityMap.put("subj_level_begin", map.get("subj_level_begin").toString());
				entityMap.put("subj_level_end", map.get("subj_level_end").toString());
				entityMap.put("is_last", map.get("is_last").toString());
				entityMap.put("is_bqwfs", map.get("is_bqwfs").toString());
			}

			if (!"".equals(entityMap.get("cur_code").toString())) {
				curSql.append(" and v.cur_code = '").append(entityMap.get("cur_code").toString()).append("' ");
			} else {
				curSql.append("");
			}

			// 方案中是否选择科目级次
			if (Integer.parseInt(entityMap.get("subj_level_begin").toString()) != 0
					|| Integer.parseInt(entityMap.get("subj_level_end").toString()) != 0) {
				if (Integer.parseInt(entityMap.get("subj_level_begin").toString()) == 99
						|| Integer.parseInt(entityMap.get("subj_level_end").toString()) == 99) {
					levelSql2.append(" and  c.is_last = 1 ");
				} else {
					levelSql2.append(" and c.subj_level between '").append(entityMap.get("subj_level_begin"))
							.append("' and  '").append(entityMap.get("subj_level_end")).append("'  ");
				}
			} else {
				// 按照方案中选择的树形菜单里面的科目进行查询
				levelSql2.append("");
			}
		} else {
			// 不选方案，单选页面上的科目查询
			if (entityMap.get("subj_code") != null && entityMap.get("subj_code") != "") {
				subjSql.append(" and c.subj_code like '").append(entityMap.get("subj_code").toString()).append("%'");
			} else {
				subjSql.append("");
			}

			copyCodeSql.append(" and c.copy_code in ('").append(entityMap.get("copy_code")).append("')");
			entityMap.put("copyCodeSql", copyCodeSql.toString());
		}

		entityMap.put("levelSql2", levelSql2.toString());

		if ("1".equals(entityMap.get("is_state"))) {
			stateSql.append(" and a.state >= 1 ");
		}
		if ("99".equals(entityMap.get("is_state"))) {
			stateSql.append(" and a.state = 99 ");
		}

		if (subjCodeSql.length() > 22) {
			entityMap.put("subjCodeSql", subjCodeSql.toString());
		} else {
			entityMap.put("subjCodeSql", "");
		}
		entityMap.put("stateSql", stateSql.toString());
		entityMap.put("subjSql", subjSql.toString());
		entityMap.put("curSql", curSql.toString());

		List<Map<String, Object>> list = accSubjLedgerMapper.queryAccVouchCountSumDetail(entityMap);

		reMap.put("detail", ChdJson.toListLower(list));

		return reMap;
	}

	/**
	 * 查询科目 按subj_code查询
	 * 
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@Override
	public String queryAccSubjTree(Map<String, Object> mapVo) throws DataAccessException {
		StringBuilder jsonResult = new StringBuilder();
		jsonResult.append("{Rows:[");
		List<Map<String, Object>> list = accSubjLedgerMapper.queryAccSubjTree(mapVo);
		if (list.size() > 0) {

			for (Map<String, Object> map : list) {
				if ("top".equals(map.get("super_code"))) {
					jsonResult.append(
							"{'id' : '" + map.get("subj_code") + "' ,'name' : '" + map.get("subj_name") + "'},");

				} else {
					jsonResult.append("{'id' : '" + map.get("subj_code") + "' ,'name' : '" + map.get("subj_name")
							+ "', 'pId': '" + map.get("super_code") + "'},");
				}
			}
			jsonResult.setCharAt(jsonResult.length() - 1, ' ');
		}
		jsonResult.append("]}");
		return jsonResult.toString();
	}

	/**
	 * 多栏明细账查询
	 */
	@Override
	public String queryAccColumnsLedgerDetail(Map<String, Object> mapVo) {

		List<Map<String, Object>> returnList = new ArrayList<Map<String, Object>>();

		Map<String, Object> mapVoS = new HashMap<String, Object>();

		Map<String, Object> entityMap = accSubjLedgerMapper.queryAccColumnsLedgerBegin(mapVo);
		
		SysPage sysPage = new SysPage();
		sysPage = (SysPage) mapVo.get("sysPage");
		List<Map<String, Object>> ListVo = null;
		PageInfo page = null;
		//当所选科目没有期初数据的时候 要进行处理
		Map<String, Object> mm=accSubjLedgerMapper.queryAccColumnsLedgerDetail(mapVo).get(0);
		
		String beginMoney = mm.get("amount_money")==null?"0":mm.get("amount_money").toString();
		
		if (sysPage.getTotal()==-1){
			ListVo = accSubjLedgerMapper.queryAccColumnsLedgerDetail(mapVo);
		}else{
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			ListVo = accSubjLedgerMapper.queryAccColumnsLedgerDetail(mapVo, rowBounds);
			page = new PageInfo(ListVo);
		}

		if (entityMap == null) {

			mapVoS.put("end_os", 0);

		} else {
			// 查询多栏账时，增加条件如果end_os为空时，默认为0，否则报错空指针
			if (entityMap.get("end_os") != null) {

				entityMap.put("end_os", Double.parseDouble(entityMap.get("credit").toString())
						+ Double.parseDouble(entityMap.get("debit").toString()));
			} else {
				entityMap.put("end_os", "0");
			}
			mapVoS.putAll(entityMap);
		}

		if (ListVo.size() > 1) {

			Map<String, Object> map1 = ListVo.get(0);

			Double amount_money = Double.parseDouble(mapVoS.get("end_os").toString())
					+ Double.parseDouble(beginMoney);

			map1.put("end_os", amount_money);

			returnList.add(map1);

			for (int i = 1; i < ListVo.size(); i++) {

				Map<String, Object> map = ListVo.get(i);

				if ("借".equals(map.get("subj_dire"))) {

					amount_money = amount_money + Double.parseDouble(map.get("debit").toString())
							- Double.parseDouble(map.get("credit").toString());

					map.put("end_os", amount_money);

					String[] accAubj = map.get("subj_code").toString().split(",");

					if (accAubj.length > 0) {

						for (int j = 0; j < accAubj.length; j++) {

							if (Double.parseDouble(map.get("code_debit").toString().split(",")[j]) != 0) {
								map.put("j" + accAubj[j], map.get("code_debit").toString().split(",")[j]);
								// map.put("code_debit", map.get("code_debit").toString().split(",")[j]);
							} else {

								map.put("j" + accAubj[j], "-" + map.get("code_credit").toString().split(",")[j]);
								// map.put("code_debit", "-"+map.get("code_credit").toString().split(",")[j]);
							}

						}

					}

				} else {

					amount_money = amount_money + Double.parseDouble(map.get("credit").toString())
							- Double.parseDouble(map.get("debit").toString());

					map.put("end_os", amount_money);

					String[] accAubj = map.get("subj_code").toString().split(",");

					if (accAubj.length > 0) {

						for (int j = 0; j < accAubj.length; j++) {

							if (Double.parseDouble(map.get("code_credit").toString().split(",")[j]) != 0) {
								map.put("d" + accAubj[j], map.get("code_credit").toString().split(",")[j]);
								// map.put("code_credit",map.get("code_credit").toString().split(",")[j]);
							} else {

								map.put("d" + accAubj[j], "-" + map.get("code_debit").toString().split(",")[j]);
								// map.put("code_credit", "-"+map.get("code_debit").toString().split(",")[j]);
							}

						}
					}

				}

				returnList.add(map);
			}
		}
		if(page == null){
			return ChdJson.toJsonLower(returnList);
		}else{
			return ChdJson.toJsonLower(returnList, page.getTotal());
		}
	}

	@Override
	public List<Map<String, Object>> queryAccColumnsLedgerDetailPrint(Map<String, Object> entityMap)
			throws DataAccessException {

		List<Map<String, Object>> returnList = new ArrayList<Map<String, Object>>();

		Map<String, Object> mapVoS = new HashMap<String, Object>();

		Map<String, Object> entityMap1 = accSubjLedgerMapper.queryAccColumnsLedgerBegin(entityMap);

		List<Map<String, Object>> ListVo = accSubjLedgerMapper.queryAccColumnsLedgerDetail(entityMap);

		if (entityMap1 == null) {

			mapVoS.put("end_os", 0);

		} else {
			// 查询多栏账时，增加条件如果end_os为空时，默认为0，否则报错空指针
			if (entityMap.get("end_os") != null) {

				entityMap.put("end_os", Double.parseDouble(entityMap.get("credit").toString())
						+ Double.parseDouble(entityMap.get("debit").toString()));
			} else {
				entityMap.put("end_os", "0");
			}
			mapVoS.putAll(entityMap);
		}

		if (ListVo.size() > 1) {

			Map<String, Object> map1 = ListVo.get(0);

			Double amount_money = Double.parseDouble(mapVoS.get("end_os").toString())
					+ Double.parseDouble(map1.get("amount_money").toString());

			map1.put("end_os", amount_money);

			returnList.add(map1);

			for (int i = 1; i < ListVo.size(); i++) {

				Map<String, Object> map = ListVo.get(i);

				if ("借".equals(map.get("subj_dire"))) {

					amount_money = amount_money + Double.parseDouble(map.get("debit").toString())
							- Double.parseDouble(map.get("credit").toString());

					map.put("end_os", amount_money);

					String[] accAubj = map.get("subj_code").toString().split(",");

					if (accAubj.length > 0) {

						for (int j = 0; j < accAubj.length; j++) {

							if (Double.parseDouble(map.get("code_debit").toString().split(",")[j]) != 0) {
								map.put("j" + accAubj[j], map.get("code_debit").toString().split(",")[j]);
								// map.put("code_debit", map.get("code_debit").toString().split(",")[j]);
							} else {

								map.put("j" + accAubj[j], "-" + map.get("code_credit").toString().split(",")[j]);
								// map.put("code_debit", "-"+map.get("code_credit").toString().split(",")[j]);
							}

						}

					}

				} else {

					amount_money = amount_money + Double.parseDouble(map.get("credit").toString())
							- Double.parseDouble(map.get("debit").toString());

					map.put("end_os", amount_money);

					String[] accAubj = map.get("subj_code").toString().split(",");

					if (accAubj.length > 0) {

						for (int j = 0; j < accAubj.length; j++) {

							if (Double.parseDouble(map.get("code_credit").toString().split(",")[j]) != 0) {
								map.put("d" + accAubj[j], map.get("code_credit").toString().split(",")[j]);
								// map.put("code_credit",map.get("code_credit").toString().split(",")[j]);
							} else {

								map.put("d" + accAubj[j], "-" + map.get("code_debit").toString().split(",")[j]);
								// map.put("code_credit", "-"+map.get("code_debit").toString().split(",")[j]);
							}

						}
					}

				}

				returnList.add(map);
			}
		}

		return returnList;
	}

	/**
	 * 全年账簿打印
	 */
	@Override
	public List<Map<String, Object>> collectThreeColumnLedgerDetailPrintDate(Map<String, Object> entityMap)
			throws DataAccessException {

		entityMap.put("is_subj_flag", entityMap.get("subj_select_flag"));

		entityMap.put("rst", new ArrayList<Map<String, Object>>());

		/* entityMap.put("rst",OracleTypes.CURSOR); */

		accSubjLedgerMapper.collectThreeColumnLedgerDetail(entityMap);

		TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();

		List<Map<String, Object>> resList = (List<Map<String, Object>>) entityMap.get("rst");

		return resList;
	}

	/**
	 * 科目余额表
	 * 普通打印
	 */
	@Override
	public List<Map<String, Object>> collectBalanceLedgerDetailPrintDate(Map<String, Object> entityMap)throws DataAccessException {
		List<Map<String, Object>> reList = queryBalanceLedgerDetailList(entityMap);
		return reList;
	}

	/**
	 * 查凭证详情
	 */
	@Override
	public String queryKMZZTVouchNo(Map<String, Object> entityMap) throws DataAccessException {
		StringBuffer stateSql = new StringBuffer();
		List<String> copyCodeList = new ArrayList<>();

		if (entityMap.get("sch_id") != null && entityMap.get("sch_id") != "") {
			List<Map<String, Object>> mapList = accSubjLedgerMapper.queryAccBookSch(entityMap);
			// p_copy_codes=;001;002;003
			for (Map<String, Object> map : mapList) {
				// 方案中是否勾选包含未记账
				if (Integer.parseInt(map.get("is_nacc").toString()) == 1) {
					entityMap.put("is_state", "1");
				} else {
					entityMap.put("is_state", "99");
				}

				String copyCodes = (String) map.get("p_copy_codes");
				if (!("".equals(copyCodes) || copyCodes == null)) {
					String[] split = copyCodes.split(";");
					for (String string : split) {
						copyCodeList.add(string);
					}
				}
			}
			if (copyCodeList.isEmpty()) {
				return ChdJson.toJsonLower(mapList);
			}
		}else {
			copyCodeList.add((String) entityMap.get("copy_code"));
		}

		entityMap.put("copy_code", copyCodeList);
		if ("1".equals(entityMap.get("is_state"))) {
			stateSql.append(" and v.state >= 1 ");
		}
		if ("99".equals(entityMap.get("is_state"))) {
			stateSql.append(" and v.state = 99 ");
		}
		// entityMap.put("vouch_date",
		// entityMap.get("year")+"-"+entityMap.get("month")+"-"+entityMap.get("day"));
		entityMap.put("stateSql", stateSql.toString());

		List<Map<String, Object>> vouchList = ChdJson.toListLower(accSubjLedgerMapper.queryKMZZTVouchNo(entityMap));
		return ChdJson.toJsonLower(vouchList);
	}
	
	@Override
	public List<Map<String, Object>> queryAccVouchCountSumDetailPrint(Map<String, Object> entityMap) throws DataAccessException {

		StringBuffer stateSql = new StringBuffer();
		StringBuffer subjSql = new StringBuffer();
		StringBuffer subjCodeSql = new StringBuffer();
		// StringBuffer subjCodeSql2 = new StringBuffer();
		StringBuffer levelSql2 = new StringBuffer();
		StringBuffer curSql = new StringBuffer();
		StringBuffer copyCodeSql = new StringBuffer();

		subjCodeSql.append(" and c.subj_code in ( ");
		Map<String, String> copyMap = new HashMap<>();
		// subjCodeSql2.append(" and ( ");

		int a = 0;
		int size = 0;
		if (entityMap.get("sch_id") != null && entityMap.get("sch_id") != "") {
			List<Map<String, Object>> mapList = accSubjLedgerMapper.queryAccBookSch(entityMap);
			size = mapList.size();
			for (Map<String, Object> map : mapList) {
				a++;
				// 方案中是否勾选包含未记账
				if (Integer.parseInt(map.get("is_nacc").toString()) == 1) {
					entityMap.put("is_state", "1");
				} else {
					entityMap.put("is_state", "99");
				}

				if (!"".equals(map.get("p_copy_codes"))) {
					copyCodeSql.append(" and c.copy_code in ( ");
					String copyCodes = map.get("p_copy_codes").toString().replace(";", "','");
					String copyCode = copyCodes.substring(2, copyCodes.length() - 2);
					if (copyMap.get(copyCode) == null) {
						copyMap.put(copyCode, copyCode);
						copyCodeSql.append(copyCode).append(" ) ");
						entityMap.put("copyCodeSql", copyCodeSql.toString());
					}
				}

				// 方案中是否勾选范围查询
				if (Integer.parseInt(map.get("is_fw").toString()) == 1) {
					if (Integer.parseInt(map.get("subj_level_begin").toString()) == 99) {
						subjSql.append(" and c.subj_code >= '").append(map.get("subj_code_begin"))
								.append("99999999999999999'");
						subjSql.append(" and c.subj_code <= '").append(map.get("subj_code_end")).append("'");
					} else if (Integer.parseInt(map.get("subj_level_end").toString()) == 99) {
						subjSql.append(" and c.subj_code >= '").append(map.get("subj_code_begin")).append("'");
						subjSql.append(" and c.subj_code <= '").append(map.get("subj_code_end"))
								.append("99999999999999999'");
					} else {
						subjSql.append(" and c.subj_code >= '").append(map.get("subj_code_begin")).append("'");// .append("'
																												// and
																												// '").append(map.get("subj_code_end")).append("'
																												// ");
						subjSql.append(" and c.subj_code <= '").append(map.get("subj_code_end")).append("'");// .append("'
																												// and
																												// '").append(map.get("subj_code_end")).append("'
																												// ");
					}
				} else {
					// if(Integer.parseInt(map.get("subj_is_last").toString())==1){
					if (a < size) {
						subjCodeSql.append("'").append(map.get("subj_code").toString());
						if (a % 1000 == 0) {
							subjCodeSql.append("') or c.subj_code in ( ");
						} else {
							subjCodeSql.append("',");
						}
					} else {
						subjCodeSql.append("'").append(map.get("subj_code").toString()).append("')");
					}
					/*
					 * }else{
					 * subjCodeSql2.append(" c.subj_code like '").append(map.get("subj_code").
					 * toString()).append("' ") }
					 */
				}

				entityMap.put("cur_code", map.get("cur_code").toString());
				entityMap.put("is_fw", map.get("is_fw").toString());
				entityMap.put("subj_level_begin", map.get("subj_level_begin").toString());
				entityMap.put("subj_level_end", map.get("subj_level_end").toString());
				entityMap.put("is_last", map.get("is_last").toString());
				entityMap.put("is_bqwfs", map.get("is_bqwfs").toString());
			}

			if (!(entityMap.get("cur_code") == null || "".equals(entityMap.get("cur_code").toString()))) {
				curSql.append(" and v.cur_code = '").append(entityMap.get("cur_code").toString()).append("' ");
			} else {
				curSql.append("");
			}

			// 方案中是否选择科目级次
			if ((entityMap.get("subj_level_begin") != null && entityMap.get("subj_level_end") != null)
					&& (Integer.parseInt(entityMap.get("subj_level_begin").toString()) != 0
							|| Integer.parseInt(entityMap.get("subj_level_end").toString()) != 0)) {
				if (Integer.parseInt(entityMap.get("subj_level_begin").toString()) == 99
						|| Integer.parseInt(entityMap.get("subj_level_end").toString()) == 99) {
					levelSql2.append(" and  c.is_last = 1 ");
				} else {
					levelSql2.append(" and c.subj_level between '").append(entityMap.get("subj_level_begin"))
							.append("' and  '").append(entityMap.get("subj_level_end")).append("'  ");
				}
			} else {
				// 按照方案中选择的树形菜单里面的科目进行查询
				levelSql2.append("");
			}
		} else {
			// 不选方案，单选页面上的科目查询
			if (entityMap.get("subj_code") != null && entityMap.get("subj_code") != "") {
				subjSql.append(" and c.subj_code like '").append(entityMap.get("subj_code").toString()).append("%'");
			} else {
				subjSql.append("");
			}

			copyCodeSql.append(" and c.copy_code in ('").append(entityMap.get("copy_code")).append("')");
			entityMap.put("copyCodeSql", copyCodeSql.toString());
		}

		entityMap.put("levelSql2", levelSql2.toString());
		
		//页面条件  优先级最高
		if(entityMap.get("isLastChk") != null && "0".equals(entityMap.get("isLastChk").toString())){
			subjSql.append(" and c.is_last = 1");
		}

		if ("1".equals(entityMap.get("is_state"))) {
			stateSql.append(" and a.state >= 1 ");
		}
		if ("99".equals(entityMap.get("is_state"))) {
			stateSql.append(" and a.state = 99 ");
		}

		if (subjCodeSql.length() > 22) {
			entityMap.put("subjCodeSql", subjCodeSql.toString());
		} else {
			entityMap.put("subjCodeSql", "");
		}

		entityMap.put("stateSql", stateSql.toString());
		entityMap.put("subjSql", subjSql.toString());
		entityMap.put("curSql", curSql.toString());

		List<Map<String, Object>> list = accSubjLedgerMapper.queryAccVouchCountSumDetail(entityMap);

		return list;

	}

}
