package com.chd.hrp.htcg.serviceImpl.collect;

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
import com.chd.base.util.DateUtil;
import com.chd.base.util.NumberUtil;
import com.chd.base.util.ReadFiles;
import com.chd.base.util.StringTool;
import com.chd.hrp.htcg.dao.collect.HtcgMrInfoMapper;
import com.chd.hrp.htcg.dao.info.HtcgIdentityTypeMapper;
import com.chd.hrp.htcg.dao.info.HtcgOutcomeDictMapper;
import com.chd.hrp.htcg.entity.collect.HtcgMrInfo;
import com.chd.hrp.htcg.entity.info.HtcgIdentityType;
import com.chd.hrp.htcg.entity.info.HtcgOutcomeDict;
import com.chd.hrp.htcg.service.collect.HtcgMrInfoService;
import com.chd.hrp.sys.dao.DeptDictMapper;
import com.chd.hrp.sys.entity.DeptDict;
import com.github.pagehelper.PageInfo;

@Service("htcgMrInfoService")
public class HtcgMrInfoServiceImpl implements HtcgMrInfoService {

	private static Logger logger = Logger.getLogger(HtcgMrInfoServiceImpl.class);

	@Resource(name = "htcgMrInfoMapper")
	private final HtcgMrInfoMapper htcgMrInfoMapper = null;

	@Resource(name = "deptDictMapper")
	private final DeptDictMapper deptDictMapper = null;
	
    /**
     * 医保类型
     */
	@Resource(name="htcgIdentityTypeMapper")
	private final HtcgIdentityTypeMapper htcgIdentityTypeMapper = null;
	
	/**
	 * 转归字典
	 */
	@Resource(name = "htcgOutcomeDictMapper")
	private final HtcgOutcomeDictMapper htcgOutcomeDictMapper = null;

	@Override
	public String addHtcgMrInfo(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		try {

			entityMap.put("in_dept_id", StringTool.isNotBlank(entityMap.get("in_dept_id")) ? entityMap.get("in_dept_id") : "");
			entityMap.put("in_dept_no", StringTool.isNotBlank(entityMap.get("in_dept_no")) ? entityMap.get("in_dept_no") : "");
			entityMap.put("out_dept_id", StringTool.isNotBlank(entityMap.get("out_dept_id")) ? entityMap.get("out_dept_id") : "");
			entityMap.put("out_dept_no", StringTool.isNotBlank(entityMap.get("out_dept_no")) ? entityMap.get("out_dept_no") : "");
			entityMap.put("in_date",StringTool.isNotBlank(entityMap.get("in_date")) ? DateUtil.stringToDate(entityMap.get("in_date").toString(),"yyyy-MM-dd") : "");
			entityMap.put("out_date",StringTool.isNotBlank(entityMap.get("out_date")) ? DateUtil.stringToDate(entityMap.get("out_date").toString(),"yyyy-MM-dd") : "");
			entityMap.put("birth_date",StringTool.isNotBlank(entityMap.get("birth_date")) ? DateUtil.stringToDate(entityMap.get("birth_date").toString(), "yyyy-MM-dd") : "");
			HtcgMrInfo htcgMrInfo = htcgMrInfoMapper.queryHtcgMrInfoByCode(entityMap);

			if (null != htcgMrInfo) {

				return "{\"error\":\"病案号住院号重复！.\",\"state\":\"false\"}";
			}

			htcgMrInfoMapper.addHtcgMrInfo(entityMap);
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"添加失败\"}");
		}
	}

	@Override
	public String addBatchHtcgMrInfo(List<Map<String, Object>> list)
			throws DataAccessException {
		// TODO Auto-generated method stub
		try {

			htcgMrInfoMapper.addBatchHtcgMrInfo(list);

			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"添加失败\"}");
		}
	}

	@Override
	public String queryHtcgMrInfo(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		if (sysPage.getTotal() == -1) {

			List<HtcgMrInfo> list = htcgMrInfoMapper.queryHtcgMrInfo(entityMap);

			return ChdJson.toJson(list);
		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(),
					sysPage.getPagesize());

			List<HtcgMrInfo> list = htcgMrInfoMapper.queryHtcgMrInfo(entityMap,
					rowBounds);

			PageInfo page = new PageInfo(list);

			return ChdJson.toJson(list, page.getTotal());

		}
	}

	@Override
	public HtcgMrInfo queryHtcgMrInfoByCode(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		return htcgMrInfoMapper.queryHtcgMrInfoByCode(entityMap);
	}

	@Override
	public String deleteHtcgMrInfo(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		try {

			htcgMrInfoMapper.deleteHtcgMrInfo(entityMap);

			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"删除失败\"}");
		}
	}

	@Override
	public String deleteBatchHtcgMrInfo(List<Map<String, Object>> list)
			throws DataAccessException {
		// TODO Auto-generated method stub
		try {

			htcgMrInfoMapper.deleteBatchHtcgMrInfo(list);

			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"删除失败\"}");
		}
	}

	@Override
	public String updateHtcgMrInfo(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		try {

			entityMap.put("in_dept_id", StringTool.isNotBlank(entityMap
					.get("in_dept_id")) ? entityMap.get("in_dept_id") : "");
			entityMap.put("in_dept_no", StringTool.isNotBlank(entityMap
					.get("in_dept_no")) ? entityMap.get("in_dept_no") : "");
			entityMap.put("out_dept_id", StringTool.isNotBlank(entityMap
					.get("out_dept_id")) ? entityMap.get("out_dept_id") : "");
			entityMap.put("out_dept_no", StringTool.isNotBlank(entityMap
					.get("out_dept_no")) ? entityMap.get("out_dept_no") : "");
			entityMap.put(
					"in_date",
					StringTool.isNotBlank(entityMap.get("in_date")) ? DateUtil
							.stringToDate(entityMap.get("in_date").toString(),
									"yyyy-MM-dd") : "");
			entityMap.put(
					"out_date",
					StringTool.isNotBlank(entityMap.get("out_date")) ? DateUtil
							.stringToDate(entityMap.get("out_date").toString(),
									"yyyy-MM-dd") : "");
			entityMap
					.put("birth_date",
							StringTool.isNotBlank(entityMap.get("birth_date")) ? DateUtil
									.stringToDate(entityMap.get("birth_date")
											.toString(), "yyyy-MM-dd") : "");

			htcgMrInfoMapper.updateHtcgMrInfo(entityMap);

			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";

		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"修改失败\"}");
		}
	}

	@Override
	public String importHtcgMrInfo(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		try {

			Map<String, Object> patientSexMap = new HashMap<String, Object>();
			patientSexMap.put("男", 1);
			patientSexMap.put("女", 2);

			List<Map<String, List<String>>> list = ReadFiles
					.readFiles(entityMap);

			List<Map<String, Object>> resultSet = new ArrayList<Map<String, Object>>();

			if (list.size() == 0 || list == null) {
				return "{\"error\":\"表头或者数据为空！请重新导入.\"}";
			}

			for (Map<String, List<String>> map : list) {

				Map<String, Object> mapVo = new HashMap<String, Object>();

				mapVo.put("group_id", SessionManager.getGroupId());

				mapVo.put("hos_id", SessionManager.getHosId());

				mapVo.put("copy_code", SessionManager.getCopyCode());

				mapVo.put("mr_no", map.get("mr_no").get(1));

				mapVo.put("in_hos_no", map.get("in_hos_no").get(1));

				mapVo.put("patient_name", map.get("patient_name").get(1));

				mapVo.put("patient_sex", map.get("patient_sex").get(1));

				mapVo.put("patient_age", map.get("patient_age").get(1));

				mapVo.put("birth_date", map.get("birth_date").get(1));

				mapVo.put("identity_code", map.get("identity_code").get(1));

				mapVo.put("identity_name", map.get("identity_name").get(1));

				mapVo.put("in_date", map.get("in_date").get(1));

				mapVo.put("in_dept_code", map.get("in_dept_code").get(1));

				mapVo.put("in_dept_name", map.get("in_dept_name").get(1));

				mapVo.put("out_date", map.get("out_date").get(1));

				mapVo.put("out_dept_code", map.get("out_dept_code").get(1));

				mapVo.put("out_dept_name", map.get("out_dept_name").get(1));

				mapVo.put("in_days", map.get("in_days").get(1));

				mapVo.put("director_name", map.get("director_name").get(1));

				mapVo.put("major_name", map.get("major_name").get(1));

				mapVo.put("in_hos_name", map.get("in_hos_name").get(1));

				mapVo.put("outcome_code", map.get("outcome_code").get(1));

				mapVo.put("outcome_name", map.get("outcome_name").get(1));

				if (!StringTool.isNotBlank(mapVo.get("patient_name"))) {
					mapVo.put("patient_name", "");
				}
				if (StringTool.isNotBlank(mapVo.get("patient_sex"))) {
					if (StringTool.isNotBlank(patientSexMap.get(mapVo
							.get("patient_sex")))) {
						mapVo.put("patient_sex",
								patientSexMap.get(mapVo.get("patient_sex")));
					} else {
						return "{\"error\":\"" + map.get("patient_sex").get(0)
								+ "\n\n性别只能输入(男或女)\",\"state\":\"false\"}";
					}
				} else {
					mapVo.put("patient_sex", "");
				}

				if (StringTool.isNotBlank(mapVo.get("patient_age"))) {
					if (!NumberUtil.isNumeric(mapVo.get("patient_age").toString())) {
						return "{\"error\":\"" + map.get("patient_age").get(0)
								+ "\n\n请输入数字\",\"state\":\"false\"}";
					}
				} else {
					mapVo.put("patient_age", "");
				}

				if(StringTool.isNotBlank(mapVo.get("birth_date"))){
					if(!DateUtil.CheckDate(mapVo.get("birth_date").toString())){
						return "{\"error\":\"" + map.get("birth_date").get(0)
								+ "\n\n日期格式必须是yyyy-mm-dd\",\"state\":\"false\"}";
					}	
				}else {
					
					mapVo.put("birth_date", "");
				}
		
				if(StringTool.isNotBlank(mapVo.get("identity_code"))){
					Map<String, Object> identityCodeMap = new HashMap<String, Object>();
					identityCodeMap.put("group_id", mapVo.get("group_id"));
					identityCodeMap.put("hos_id", mapVo.get("hos_id"));
					identityCodeMap.put("copy_code", mapVo.get("copy_code"));
					identityCodeMap.put("identity_code", mapVo.get("identity_code"));
					HtcgIdentityType htcgIdentityType = htcgIdentityTypeMapper.queryHtcgIdentityTypeByCode(identityCodeMap);
					 if(null == htcgIdentityType){
						 return "{\"error\":\""+ map.get("identity_code").get(0)+"\n\n医保类型不存在:"+map.get("identity_code").get(1)+"\",\"state\":\"false\"}";
					 }
				}else {
					
					mapVo.put("identity_code", "");
				}
				
				if(StringTool.isNotBlank(mapVo.get("in_date"))){
					if(!DateUtil.CheckDate(mapVo.get("in_date").toString())){
						return "{\"error\":\"" + map.get("in_date").get(0)
								+ "\n\n日期格式必须是yyyy-mm-dd\",\"state\":\"false\"}";
					}	
				}else {
					
					mapVo.put("in_date", "");
				}
				/**
				 * 入院科室的ID,NO
				 */
				if (StringTool.isNotBlank(mapVo.get("in_dept_code"))) {
					Map<String, Object> inDeptMap = new HashMap<String, Object>();
					inDeptMap.put("group_id", mapVo.get("group_id"));
					inDeptMap.put("hos_id", mapVo.get("hos_id"));
					inDeptMap.put("copy_code", mapVo.get("copy_code"));
					inDeptMap.put("dept_code", mapVo.get("in_dept_code"));
					inDeptMap.put("is_stop", mapVo.get("0"));
					DeptDict inDeptDict = deptDictMapper.queryDeptDictByDeptCode(inDeptMap);
					if (null != inDeptDict) {
						mapVo.put("in_dept_id", inDeptDict.getDept_id());
						mapVo.put("in_dept_no", inDeptDict.getDept_no());
					} else {
						 return "{\"error\":\""+ map.get("in_dept_code").get(0)+"\n\n入院科室编码不存在:"+map.get("in_dept_code").get(1)+"\",\"state\":\"false\"}";
					}

				} else {
					mapVo.put("in_dept_id", "");
					mapVo.put("in_dept_no", "");
				}
				
				if(StringTool.isNotBlank(mapVo.get("out_date"))){
					if(!DateUtil.CheckDate(mapVo.get("out_date").toString())){
						return "{\"error\":\"" + map.get("out_date").get(0)
								+ "\n\n日期格式必须是yyyy-mm-dd\",\"state\":\"false\"}";
					}	
				}else {
					
					mapVo.put("out_date", "");
				}
				
				/**
				 * 出院科室的ID,NO
				 */
				if (StringTool.isNotBlank(mapVo.get("out_dept_code"))) {
					Map<String, Object> outDeptMap = new HashMap<String, Object>();
					outDeptMap.put("group_id", mapVo.get("group_id"));
					outDeptMap.put("hos_id", mapVo.get("hos_id"));
					outDeptMap.put("copy_code", mapVo.get("copy_code"));
					outDeptMap.put("dept_code", mapVo.get("out_dept_code"));
					outDeptMap.put("is_stop", mapVo.get("0"));
					DeptDict outDeptDict = deptDictMapper
							.queryDeptDictByDeptCode(outDeptMap);

					if (null != outDeptDict) {
						mapVo.put("out_dept_id", outDeptDict.getDept_id());
						mapVo.put("out_dept_no", outDeptDict.getDept_no());
					} else {
						return "{\"error\":\""+ map.get("out_dept_code").get(0)+"\n\n出院科室编码不存在:"+map.get("out_dept_code").get(1)+"\",\"state\":\"false\"}";
					}
				} else {

					mapVo.put("out_dept_id", "");
					mapVo.put("out_dept_no", "");
				}

				if (StringTool.isNotBlank(mapVo.get("in_days"))) {
					if (!NumberUtil.isNumeric(mapVo.get("in_days").toString())) {
						return "{\"error\":\"" + map.get("in_days").get(0)
								+ "\n\n请输入数字\",\"state\":\"false\"}";
					}
				} else {
					mapVo.put("in_days", "");
				}
		
				if (!StringTool.isNotBlank(mapVo.get("director_name"))) {
					mapVo.put("director_name", "");
				}
				if (!StringTool.isNotBlank(mapVo.get("major_name"))) {
					mapVo.put("major_name", "");
				}
				if (!StringTool.isNotBlank(mapVo.get("in_hos_name"))) {
					mapVo.put("in_hos_name", "");
				}
			   
				if(StringTool.isNotBlank(mapVo.get("outcome_code"))){
					Map<String, Object> outcomeMap = new HashMap<String, Object>();
					outcomeMap.put("group_id", mapVo.get("group_id"));
					outcomeMap.put("hos_id", mapVo.get("hos_id"));
					outcomeMap.put("copy_code", mapVo.get("copy_code"));
					outcomeMap.put("outcome_code", mapVo.get("outcome_code"));
					HtcgOutcomeDict htcgOutcomeDict = htcgOutcomeDictMapper.queryHtcgOutcomeDictByCode(outcomeMap);
					 if(null == htcgOutcomeDict){
						 return "{\"error\":\""+ map.get("outcome_code").get(0)+"\n\n转归编码不存在:"+map.get("outcome_code").get(1)+"\",\"state\":\"false\"}";
					 }
				}else {
					
					mapVo.put("outcome_code","");
				}

				
				HtcgMrInfo htcgMrInfo = htcgMrInfoMapper.queryHtcgMrInfoByCode(mapVo);

				if (null != htcgMrInfo) {
					return "{\"error\":\""+ map.get("mr_no").get(0)+"\n\n病案号！:"+map.get("mr_no").get(1)+"或者"+ map.get("in_hos_no").get(0)+"\n\n住院号！:"+map.get("in_hos_no").get(1)+"\n重复.\",\"state\":\"false\"}";
				}
				resultSet.add(mapVo);

			}
			if (resultSet.size() > 0) {
				htcgMrInfoMapper.addBatchHtcgMrInfo(resultSet);
			}
			return "{\"msg\":\"导入成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			// TODO: handle exception
			logger.debug(e.getMessage());
			throw new SysException(e.getMessage());
		}
	}

}
