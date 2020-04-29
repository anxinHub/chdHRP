package com.chd.hrp.hpm.serviceImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.chd.base.SessionManager;
import com.chd.base.SysPage;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.base.util.DateUtil;
import com.chd.base.util.SpreadTableJSUtil;
import com.chd.hrp.hpm.dao.AphiDeptBonusAuditMapper;
import com.chd.hrp.hpm.dao.AphiDeptDictMapper;
import com.chd.hrp.hpm.dao.AphiDeptHipMapper;
import com.chd.hrp.hpm.dao.AphiDeptMapper;
import com.chd.hrp.hpm.dao.AphiSchemeConfMapper;
import com.chd.hrp.hpm.dao.AphiWorkitemDataMapper;
import com.chd.hrp.hpm.dao.AphiWorkitemSeqMapper;
import com.chd.hrp.hpm.entity.AphiDept;
import com.chd.hrp.hpm.entity.AphiDeptBonusAudit;
import com.chd.hrp.hpm.entity.AphiDeptDict;
import com.chd.hrp.hpm.entity.AphiDeptHip;
import com.chd.hrp.hpm.entity.AphiSchemeConf;
import com.chd.hrp.hpm.entity.AphiWorkitemConf;
import com.chd.hrp.hpm.entity.AphiWorkitemData;
import com.chd.hrp.hpm.entity.AphiWorkitemSeq;
import com.chd.hrp.hpm.service.AphiWorkitemDataService;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Title.
 * @Description.
 * @Copyright: Copyright (c) 2015-2-14 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @Author: LiuYingDuo
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Service("aphiWorkitemDataService")
public class AphiWorkitemDataServiceImpl implements AphiWorkitemDataService {

	private static Logger logger = Logger.getLogger(AphiWorkitemDataServiceImpl.class);

	@Resource(name = "aphiWorkitemDataMapper")
	private final AphiWorkitemDataMapper aphiWorkitemDataMapper = null;

	@Resource(name = "aphiDeptBonusAuditMapper")
	private final AphiDeptBonusAuditMapper aphiDeptBonusAuditMapper = null;

	@Resource(name = "aphiDeptDictMapper")
	private final AphiDeptDictMapper aphiDeptDictMapper = null;

	@Resource(name = "aphiWorkitemSeqMapper")
	private final AphiWorkitemSeqMapper aphiWorkitemSeqMapper = null;
	
	@Resource(name = "aphiDeptMapper")
	private final AphiDeptMapper aphiDeptMapper = null;
	
	@Resource(name = "aphiDeptHipMapper")
	private final AphiDeptHipMapper aphiDeptHipMapper = null;
	
	@Resource(name = "aphiSchemeConfMapper")
	private final AphiSchemeConfMapper aphiSchemeConfMapper = null;

	/**
	 * 
	 */
	@Override
	public String addWorkitemData(Map<String, Object> entityMap) throws DataAccessException {
		try {

			AphiDeptBonusAudit dba = aphiDeptBonusAuditMapper.queryDeptBonusAuditByCode(entityMap);

			if (dba != null) {

				if (dba.getIs_audit() == 1) {

					return "{\"error\":\"本月奖金已审核,不能生成本月数据\"}";

				}
				if (dba.getIs_grant() == 1) {

					return "{\"error\":\"本月奖金已发放,不能生成本月数据\"}";

				}

			}

			aphiWorkitemDataMapper.addWorkitemData(entityMap);

			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException("{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码  addHospTargetData\"}");

		}
	}

	/**
	 * 
	 */
	@Override
	public String queryWorkitemData(Map<String, Object> entityMap) throws DataAccessException {
		
		AphiSchemeConf sc = aphiSchemeConfMapper.querySchemeConfByYM(entityMap);
		if(sc == null){
			return "{\"warn\":\"当前年月未配置方案\"}";
		}
		
		entityMap.put("scheme_seq_no", sc.getScheme_seq_no());
		if (entityMap.get("user_id") == null) {
			entityMap.put("user_id", SessionManager.getUserId());
		}
		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		if (sysPage.getTotal() == -1) {

			List<AphiWorkitemData> list = aphiWorkitemDataMapper.queryWorkitemData(entityMap);

			return ChdJson.toJson(list);

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

			List<AphiWorkitemData> list = aphiWorkitemDataMapper.queryWorkitemData(entityMap, rowBounds);

			PageInfo page = new PageInfo(list);

			return ChdJson.toJson(list, page.getTotal());

		}

	}

	/**
	 * 
	 */
	@Override
	public AphiWorkitemData queryWorkitemDataByCode(Map<String, Object> entityMap) throws DataAccessException {
		if (entityMap.get("user_id") == null) {
			entityMap.put("user_id", SessionManager.getUserId());
		}
		return aphiWorkitemDataMapper.queryWorkitemDataByCode(entityMap);

	}

	/**
	 * 
	 */
	@Override
	public String deleteWorkitemData(Map<String, Object> entityMap, String codes) throws DataAccessException {

		try {

			if (codes.length() > 0) {

				String code = codes.split(",")[0];

				String[] code_array = code.split(";");

				entityMap.put("acct_year", code_array[3]);

				entityMap.put("acct_month", code_array[4]);

				AphiDeptBonusAudit dba = aphiDeptBonusAuditMapper.queryDeptBonusAuditByCode(entityMap);

				if (dba != null) {

					if (dba.getIs_audit() == 1) {

						return "{\"error\":\"本月奖金已审核,不能删除本月数据\"}";

					}
					if (dba.getIs_grant() == 1) {

						return "{\"error\":\"本月奖金已发放,不能删除本月数据\"}";

					}

				}

			}

			if (codes != null && !codes.equals("")) {

				String[] code_codeArray = codes.split(",");

				for (String code : code_codeArray) {

					String[] code_array = code.split(";");

					entityMap.put("dept_id", code_array[0]);
					entityMap.put("dept_no", code_array[1]);
					entityMap.put("work_item_code", code_array[2]);

					entityMap.put("acct_year", code_array[3]);

					entityMap.put("acct_month", code_array[4]);

					aphiWorkitemDataMapper.deleteWorkitemData(entityMap);

				}

				return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

			} else {

				return "{\"msg\":\"没有要删除的数据.\",\"state\":\"false\"}";

			}
		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException("{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码  deleteWorkitemData\"}");

		}
	}

	/**
	 * 
	 */
	@Override
	public String updateWorkitemData(Map<String, Object> entityMap) throws DataAccessException {

		AphiDeptBonusAudit dba = aphiDeptBonusAuditMapper.queryDeptBonusAuditByCode(entityMap);

		if (dba != null) {

			if (dba.getIs_audit() == 1) {

				return "{\"error\":\"本月奖金已审核,不能修改本月数据\"}";

			}
			if (dba.getIs_grant() == 1) {

				return "{\"error\":\"本月奖金已发放,不能修改本月数据\"}";

			}

		}

		try {

			aphiWorkitemDataMapper.updateWorkitemData(entityMap);

			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException("{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updateHospTargetData\"}");

		}
	}

	@Override
	public String createWorkitemData(Map<String, Object> entityMap) throws DataAccessException {
		
		if(entityMap.get("acct_yearm") == null){
			return "{\"msg\":\"核算年月不能为空.\",\"state\":\"true\"}";
		}
		if (entityMap.get("user_id") == null) {
			entityMap.put("user_id", SessionManager.getUserId());
		}
		String year_month = (String) entityMap.get("acct_yearm");

		entityMap.put("acct_year",year_month.substring(0,4));
		entityMap.put("acct_month", year_month.substring(4,6));

		AphiDeptBonusAudit dba = aphiDeptBonusAuditMapper.queryDeptBonusAuditByCode(entityMap);
		
		//判断当前年月是否审核、发放
		/*if (dba != null) {

			if (dba.getIs_audit() == 1) {
				return "{\"error\":\"本月奖金已审核,不能生成本月数据\"}";
			}
			
			if (dba.getIs_grant() == 1) {
				return "{\"error\":\"本月奖金已发放,不能生成本月数据\"}";
			}

		}*/

		AphiSchemeConf sc = aphiSchemeConfMapper.querySchemeConfByYM(entityMap);
		if(sc == null){
			return "{\"msg\":\"当前期间没有核算方案.\",\"state\":\"false\"}";
		}

		entityMap.put("scheme_seq_no", sc.getScheme_seq_no());//当前年月方案审核序号
		String rbtnl = (String) entityMap.get("rbtnl");//获取生成类型
		
		try {
			
			if ("all".equals(rbtnl)) {//覆盖生成

				aphiWorkitemDataMapper.deleteWorkitemData(entityMap);

				List<AphiWorkitemConf> list = getWorkitemConfSeq(entityMap);

				if (list.size() == 0) {
					return "{\"msg\":\"没有要生成的数据.\",\"state\":\"false\"}";
				}
				
				for (AphiWorkitemConf wc : list) {
					
					Map<String, Object> map = new HashMap<String, Object>();
					
					map.put("group_id", entityMap.get("group_id"));
					map.put("hos_id", entityMap.get("hos_id"));
					map.put("copy_code", entityMap.get("copy_code"));
					
					map.put("acct_year", entityMap.get("acct_year"));
					map.put("acct_month", entityMap.get("acct_month"));
					
					map.put("dept_id", wc.getDept_id());
					map.put("dept_no", wc.getDept_no());
					map.put("work_item_code", wc.getWork_item_code());
					
					aphiWorkitemDataMapper.initWorkitemData(map);
				}

		} else if ("check".equals(rbtnl)) {//增量生成

			List<AphiWorkitemData> listWorkitemData = aphiWorkitemDataMapper.queryWorkitemData(entityMap);
			Map<String, String> mapWorkitemData = new HashMap<String, String>();

			for (AphiWorkitemData wd : listWorkitemData) {
				mapWorkitemData.put(wd.getDept_id() + wd.getDept_no() + wd.getWork_item_code(), wd.getDept_id() + wd.getDept_no() + wd.getWork_item_code());
			}

			List<AphiWorkitemConf> workItmeList = getWorkitemConfSeq(entityMap);

			if (workItmeList.size() == 0) {
				return "{\"msg\":\"没有要生成的数据.\",\"state\":\"false\"}";
			}

			for (int i = 0; i < workItmeList.size(); i++) {
					
				AphiWorkitemConf workitemConf = workItmeList.get(i);
					
				if (mapWorkitemData.get(workitemConf.getDept_id() + workitemConf.getDept_no() + workitemConf.getWork_item_code()) == null) {
						
					entityMap.put("dept_id", workitemConf.getDept_id());
					entityMap.put("dept_no", workitemConf.getDept_no());
					entityMap.put("work_item_code", workitemConf.getWork_item_code());
						
					aphiWorkitemDataMapper.addWorkitemData(entityMap);
				}
			}

		} else if ("inherit_value".equals(rbtnl)){//继承、带值
			aphiWorkitemDataMapper.deleteWorkitemData(entityMap);
			
			Map<String, Object> inheritMap = new HashMap<String, Object>();

			inheritMap.put("group_id", entityMap.get("group_id"));
			inheritMap.put("hos_id", entityMap.get("hos_id"));
			inheritMap.put("copy_code", entityMap.get("copy_code"));

			String preYearMonth = DateUtil.getPreData((String) entityMap.get("acct_yearm"));
			inheritMap.put("acct_year", preYearMonth.substring(0, 4));
			inheritMap.put("acct_month", preYearMonth.substring(4, 6));
			inheritMap.put("scheme_seq_no", entityMap.get("scheme_seq_no"));
			inheritMap.put("user_id",SessionManager.getUserId());
			List<AphiWorkitemData> list = aphiWorkitemDataMapper.queryWorkitemData(inheritMap);

			if (list.size() == 0) {
				return "{\"msg\":\"没有要继承的数据.\",\"state\":\"false\"}";
			} 
				
			for (AphiWorkitemData wc : list) {
					
				Map<String, Object> map = new HashMap<String, Object>();
					
				map.put("group_id", entityMap.get("group_id"));
				map.put("hos_id", entityMap.get("hos_id"));
				map.put("copy_code", entityMap.get("copy_code"));
					
				map.put("acct_year", entityMap.get("acct_year"));
				map.put("acct_month", entityMap.get("acct_month"));
					
				map.put("dept_id", wc.getDept_id());
				map.put("dept_no", wc.getDept_no());
				map.put("work_item_code", wc.getWork_item_code());
					
				map.put("work_amount", wc.getWork_amount());
				map.put("work_money", wc.getWork_money());
					
				aphiWorkitemDataMapper.initWorkitemData(map);
			}

		}else{//继承-不带值
			
			aphiWorkitemDataMapper.deleteWorkitemData(entityMap);
			
			Map<String, Object> inheritMap = new HashMap<String, Object>();

			inheritMap.put("group_id", entityMap.get("group_id"));
			inheritMap.put("hos_id", entityMap.get("hos_id"));
			inheritMap.put("copy_code", entityMap.get("copy_code"));

			String preYearMonth = DateUtil.getPreData((String) entityMap.get("acct_yearm"));
			inheritMap.put("acct_year", preYearMonth.substring(0, 4));
			inheritMap.put("acct_month", preYearMonth.substring(4, 6));
			inheritMap.put("scheme_seq_no", entityMap.get("scheme_seq_no"));
			inheritMap.put("user_id",SessionManager.getUserId());
			List<AphiWorkitemData> list = aphiWorkitemDataMapper.queryWorkitemData(inheritMap);

			if (list.size() == 0) {
				return "{\"msg\":\"没有要继承的数据.\",\"state\":\"false\"}";
			} 
				
			for (AphiWorkitemData wc : list) {
					
				Map<String, Object> map = new HashMap<String, Object>();
					
				map.put("group_id", entityMap.get("group_id"));
				map.put("hos_id", entityMap.get("hos_id"));
				map.put("copy_code", entityMap.get("copy_code"));
					
				map.put("acct_year", entityMap.get("acct_year"));
				map.put("acct_month", entityMap.get("acct_month"));
					
				map.put("dept_id", wc.getDept_id());
				map.put("dept_no", wc.getDept_no());
				map.put("work_item_code", wc.getWork_item_code());
					
				map.put("work_amount", 0);
				map.put("work_money", 0);
					
				aphiWorkitemDataMapper.initWorkitemData(map);
			}

		}
			
			return "{\"msg\":\"生成成功.\",\"state\":\"true\"}";
			
		} catch (Exception e) {
			
			logger.error(e.getMessage(), e);
			
			throw new SysException("{\"error\":\"操作失败 \"}");
		}
	}

	@Override
	public List<AphiWorkitemConf> getWorkitemConfSeq(Map<String, Object> entityMap) throws DataAccessException {
		return aphiWorkitemDataMapper.getWorkitemConfSeq(entityMap);
	}

	@Override
	public String addBatchWorkitemData(List<Map<String, Object>> entityMap) throws DataAccessException {
		int state = aphiWorkitemDataMapper.addBatchWorkitemData(entityMap);
		if (state > 0) {
			return "{\"msg\":\"导入成功.\",\"state\":\"true\"}";
		} else {
			return "{\"msg\":\"导入失败.\",\"state\":\"false\"}";
		}
	}

	@Override
	public String getWorkitemTargetValue(Map<String, Object> entityMap) throws DataAccessException {

		String acct_year_month = (String) entityMap.get("acct_yearm");

		entityMap.put("acct_year", acct_year_month.substring(0, 4));

		entityMap.put("acct_month", acct_year_month.substring(4, 6));

		List<AphiDeptBonusAudit> deptBonusAuditList = aphiDeptBonusAuditMapper.deptBonusIsAudit(entityMap);

		if (deptBonusAuditList.size() > 0) {

			return "{\"state\":\"true\"}";

		} else {

			return "{\"state\":\"false\"}";

		}
	}

	@Override
	public String importHpmWorkitemData(Map<String, Object> map) throws DataAccessException {
		// TODO Auto-generated method stub
		try {
			String columns=map.get("columns").toString();
			JSONArray jsonColumns = JSONObject.parseArray(columns);	
			if(jsonColumns==null || jsonColumns.size()==0){
				return "{\"error\":\"表头为空！\",\"state\":\"false\"}";
			}
			
			String content = map.get("content").toString();
			List<Map<String, List<String>>> liData = SpreadTableJSUtil.toListMap(content, 1);
			if (liData == null || liData.size() == 0) {
				return "{\"error\":\"没有数据！\",\"state\":\"false\"}";
			}

			// new Map查询导入时对应数据信息
			Map<String, Object> entityMap = new HashMap<String, Object>();
			entityMap.put("group_id", SessionManager.getGroupId());
			entityMap.put("hos_id", SessionManager.getHosId());
			entityMap.put("copy_code", SessionManager.getCopyCode());
			entityMap.put("user_id", SessionManager.getUserId());
			entityMap.put("is_stop", "0");// 查询未停用
			entityMap.put("dept_kind_code",map.get("dept_kind_code"));
			
			// 查询工作量字典List
			List<AphiWorkitemSeq> workitemSeqList = aphiWorkitemSeqMapper.queryWorkitemSeq(entityMap);
			// 以键值对的形式存储,用于判断是否存在工作量
			Map<String, AphiWorkitemSeq> workitemSeqMap = new HashMap<String, AphiWorkitemSeq>();
			// 工作量List 放入Map 键 work_item_code 值 AphiWorkitemSeq
			for (AphiWorkitemSeq workitemSeq : workitemSeqList) {
				workitemSeqMap.put(workitemSeq.getWork_item_code(), workitemSeq);
				workitemSeqMap.put(workitemSeq.getWork_item_name(), workitemSeq);
			}

			//判断表头中工作量指标是否存在
			StringBuffer sb = new StringBuffer();//提示信息:用于存储表头中不存在的工作量指标
			Map<String,String> itemColumnMap = new HashMap<String,String>();//用于存储表头中的工作量指标,作为遍历数据时取指标值
			
			for(Map<String,List<String>> item : liData ){
				for(Map.Entry<String, List<String>> entry : item.entrySet()){
					String key = entry.getKey();
					
					if("核算年度".equals(key) || "核算月份".equals(key) || "科室名称".equals(key)){
						continue;
					}
					
					if(key != null){
						itemColumnMap.put(key, key);
					}
					
					if(workitemSeqMap.get(key) == null){
						sb.append("工作量指标" + key + "不存在,");
					}
				}
				break;//判断指标表头是否存在,只遍历一次
			}
			
			if(itemColumnMap == null || "".equals(itemColumnMap)){
				return "{\"error\":\"表头中未存在工作量指标\",\"state\":\"false\"}";
			}
			
			//表头含有不存在工作量指标 返回提示
			if(sb.length() > 0){
				return "{\"error\":\"" + sb.deleteCharAt(sb.length() -1).toString()+ "\",\"state\":\"false\"}";
			}

			//查询所有绩效科室
			List<AphiDeptDict> deptDictList = aphiDeptDictMapper.queryPrmDeptDict(entityMap);
			//用于存储查询deptList中的AphiDept对象,以键值对的形式存储,用于判断科室是否存在
			Map<String,AphiDeptDict> deptDictMap = new HashMap<String,AphiDeptDict>();
			//将科室List存入Map   键:dept_name 值:AphiDeptDict
			for(AphiDeptDict deptDict : deptDictList){
				deptDictMap.put(deptDict.getDept_name(), deptDict);
				deptDictMap.put(deptDict.getDept_code(), deptDict);
			}
			
			//按科室与系统平台对应关系查询科室  List
			List<AphiDept> deptList = aphiDeptMapper.queryAphiDeptRelaByMaping(entityMap);
			//用于存储查询deptList中的AphiDept对象,以键值对的形式存储,用于判断科室是否存在
			Map<String,AphiDept> deptMap = new HashMap<String,AphiDept>();
			//将科室List存入Map   键:dept_name 值:AphiDept
			for(AphiDept dept : deptList){
				deptMap.put(dept.getDept_name(), dept);
				deptMap.put(dept.getDept_code(), dept);
			}
			
			
			//按科室与其它平台对应关系查询科室  List
			List<AphiDeptHip> deptHipList = aphiDeptHipMapper.queryAphiDeptRelaByMapingHip(entityMap);			
			//用于存储查询deptHipList中的AphiDeptHip对象,以键值对的形式存储,用于判断科室是否存在
			Map<String,AphiDeptHip> deptHipMap = new HashMap<String,AphiDeptHip>();
			//将科室List存入Map   键:dept_name 值:AphiDeptHip
			for(AphiDeptHip deptHip : deptHipList){
				deptHipMap.put(deptHip.getDept_name(), deptHip);
				deptHipMap.put(deptHip.getDept_code(), deptHip);
			}
			
			List<AphiWorkitemData> awidList = aphiWorkitemDataMapper.queryWorkitemDataAll(entityMap);//查询数据
			Map<String,AphiWorkitemData> dataIsExitMap = new HashMap<String,AphiWorkitemData>();//用于判断数据是否存在
			for(AphiWorkitemData awid : awidList){
				String key = awid.getAcct_year() + "|" + awid.getAcct_month() + "|" + awid.getDept_id() + "|" + awid.getDept_no() + "|" + awid.getWork_item_code();
				dataIsExitMap.put(key, awid);
			}
			
			// 用于存储传的数据值,判断数据是否重复
			Map<String, String> exitMap = new HashMap<String, String>();
			// 存储要添加保存的数据List
			List<Map<String, Object>> addList = new ArrayList<Map<String, Object>>();
			// 用于记录导入的重复数据
			StringBuffer err_sb = new StringBuffer();
			
			// 用于记录数据库已经存在的数据
			StringBuffer err_is_exit = new StringBuffer();
			
			// 遍历导入数据-遍历单一导入数据,得到名称-判断是否为空-跳出循环
			for (Map<String, List<String>> item : liData) {
				
				if(item == null){
					continue;
				}
				
				for(Map.Entry<String, String> entry:itemColumnMap.entrySet()){
					
					List<String> acct_year = item.get("核算年度");
					List<String> acct_month = item.get("核算月份");
					List<String> dept_name = item.get("科室名称");
					List<String> work_item_code = item.get(entry.getKey());
					
					if(acct_year == null || acct_month == null || dept_name == null || work_item_code == null){
						continue;
					}


					if (acct_year.get(1) == null) {
						return "{\"warn\":\"年度为空！\",\"state\":\"false\",\"row_cell\":" + acct_year.get(0) + "\"\"}";
					}

					if (acct_month.get(1) == null) {
						return "{\"warn\":\"月份为空！\",\"state\":\"false\",\"row_cell\":\"" + acct_month.get(0) + "\"}";
					}

					if(dept_name.get(1) == null){
						return "{\"warn\":\"科室名称为空！\",\"state\":\"false\",\"row_cell\":\"\"}";
					}else{
						if(deptMap.get(dept_name.get(1)) == null && deptHipMap.get(dept_name.get(1)) == null && deptDictMap.get(dept_name.get(1)) == null){
							return "{\"warn\":\"" + dept_name.get(1) + ",科室名称不存在！\",\"state\":\"false\",\"row_cell\":\"" + dept_name.get(0) + "\"}";
						}
					}

					if (work_item_code.get(1) == null) {
						return "{\"warn\":\"工作量指标值为空！\",\"state\":\"false\",\"row_cell\":\"" + work_item_code.get(0) + "\"}";
					}else{
						try {
							Double.parseDouble(work_item_code.get(1));
						} catch (Exception e) {
							// TODO: handle exception
							return "{\"warn\":\"工作量指标值错误,请重新输入！\",\"state\":\"false\",\"row_cell\":\"" + work_item_code.get(0) + "\"}";
						}
					} 


					// 判断可为空字段
					List<String> work_money = new ArrayList<String>(4);
					if (item.get("计件奖金") == null) {
						work_money.add("");
						work_money.add(1, "");
					} else {
						work_money = item.get("计件奖金");
					}

					// 添加数据Map中add到addList
					Map<String, Object> addMap = new HashMap<String, Object>();
					addMap.put("group_id", SessionManager.getGroupId());
					addMap.put("hos_id", SessionManager.getHosId());
					addMap.put("copy_code", SessionManager.getCopyCode());
					addMap.put("acct_year", acct_year.get(1));
					addMap.put("acct_month", acct_month.get(1));
					addMap.put("dept_name", dept_name.get(1));
					//系统平台科室
					if(deptMap.get(dept_name.get(1)) != null){
						addMap.put("dept_id",deptMap.get(dept_name.get(1)).getDept_id());
						addMap.put("dept_no",deptMap.get(dept_name.get(1)).getDept_no());
					}
						
					//其它平台科室
					if(deptHipMap.get(dept_name.get(1)) != null){
						addMap.put("dept_id",deptHipMap.get(dept_name.get(1)).getDept_id());
						addMap.put("dept_no",deptHipMap.get(dept_name.get(1)).getDept_no());
					}
						
					//绩效科室
					if(deptDictMap.get(dept_name.get(1)) != null){
						addMap.put("dept_id",deptDictMap.get(dept_name.get(1)).getDept_id());
						addMap.put("dept_no",deptDictMap.get(dept_name.get(1)).getDept_no());
					}
					addMap.put("work_item_code", workitemSeqMap.get(entry.getKey()).getWork_item_code());
					addMap.put("work_amount", work_item_code.get(1));
					addMap.put("work_money", work_money.get(1));
					
					// 以年度+月份+指标编码+科室名称为键值,判断导入数据是否重复
//					String key = acct_year.get(1) + "|" + acct_month.get(1) + "|" + work_item_code.get(1) + "|" + dept_name.get(1);
//					if (exitMap.get(key) != null) {
//						err_sb.append(acct_year.get(1) + "年度," + acct_month.get(1) + "月份," + dept_name.get(1) + "科室名称 ," + entry.getKey() + "工作量指标" );
//					} else {
//						exitMap.put(key, key);
//					}
					
					// 以年度+月份+科室Id+科室no+工作量指标编码为键值,判断数据是否存在
					String isExitkey = acct_year.get(1) + "|" + acct_month.get(1) + "|" + addMap.get("dept_id") + "|" + addMap.get("dept_no") + "|" +  addMap.get("work_item_code");
					if (dataIsExitMap.get(isExitkey) != null) {
						err_is_exit.append(acct_year.get(1) + "年度," + acct_month.get(1) + "月份," + dept_name.get(1) + "科室名称 ," + entry.getKey() + "工作量指标" );
					} 
					
					addList.add(addMap);
				}
			}
			
			if(err_is_exit.toString().length() > 0){
				return "{\"warn\":\"以下数据【" + err_is_exit.toString() + "】数据已经存在！\",\"state\":\"false\"}";
			}

			if (err_sb.toString().length() > 0) {
				return "{\"warn\":\"以下数据【" + err_sb.toString() + "】数据重复！\",\"state\":\"false\"}";
			} else {
				aphiWorkitemDataMapper.addBatchWorkitemData(addList);
				return "{\"msg\":\"导入成功.\",\"state\":\"true\"}";
			}

		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}
	}

	@Override
	public List<Map<String, Object>> queryWorkitemDataPrint(Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		
		entityMap.put("user_id", SessionManager.getUserId());
		
		if (!"".equals(entityMap.get("acct_yearm")) && entityMap.get("acct_yearm") != null) {

			String acct_year = entityMap.get("acct_yearm").toString().substring(0, 4);
			String acct_month = entityMap.get("acct_yearm").toString().substring(4);

			entityMap.put("acct_year", acct_year);
			entityMap.put("acct_month", acct_month);
		}
		
		
		AphiSchemeConf sc = aphiSchemeConfMapper.querySchemeConfByYM(entityMap);
		
		entityMap.put("scheme_seq_no", sc.getScheme_seq_no());
		List<Map<String, Object>> list = aphiWorkitemDataMapper.queryWorkitemDataPrint(entityMap);
		
		return list ;

	}
}
