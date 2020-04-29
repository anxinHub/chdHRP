package com.chd.hrp.hr.serviceImpl.training;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.chd.base.SessionManager;
import com.chd.base.SysPage;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.base.util.JsonListMapUtil;
import com.chd.base.util.ReadFiles;
import com.chd.hrp.hr.dao.training.HrTrainExamineMapper;
import com.chd.hrp.hr.dao.training.HrTrainRecordMapper;
import com.chd.hrp.hr.dao.training.plant.HrPlantMapper;
import com.chd.hrp.hr.dao.training.setting.HrGenreTypeMapper;
import com.chd.hrp.hr.entity.training.exam.HrBukao;
import com.chd.hrp.hr.entity.training.exam.HrExam;
import com.chd.hrp.hr.entity.training.exam.HrExamResult;
import com.chd.hrp.hr.entity.training.exam.HrTrainCert;
import com.chd.hrp.hr.entity.training.exam.HrTrainEmpCert;
import com.chd.hrp.hr.entity.training.plant.HrPlant;
import com.chd.hrp.hr.entity.training.setting.HrGenreType;
import com.chd.hrp.hr.service.training.HrTrainExamineService;
import com.github.pagehelper.PageInfo;

/**
 * 【培训管理-培训考核】service
 * @author yang
 *
 */
@Service("hrTrainExamineService")
public class HrTrainExamineServiceImpl implements HrTrainExamineService {
	
	private static Logger logger = Logger.getLogger(HrTrainExamineServiceImpl.class);
	
	// 引入DAO
	@Resource(name = "hrTrainExamineMapper")
	private final HrTrainExamineMapper hrTrainExamineMapper = null;// 培训考核dao
	
	@Resource(name = "hrTrainRecordMapper")
	private final HrTrainRecordMapper hrTrainRecordMapper = null;// 培训记录dao
	
	@Resource(name = "hrGenreTypeMapper")
	private final HrGenreTypeMapper hrGenreTypeMapper = null;// 培训类别dao
	
	@Resource(name = "hrPlantMapper")
	private final HrPlantMapper hrPlantMapper = null;// 培训计划dao
	
	/**
	 * 查询培训计划树，返回json
	 */
	@Override
	public String queryTrainPlanTreeJson(Map<String, Object> paraMap) throws DataAccessException {
		paraMap.put("is_stop", "0");
		List<HrGenreType> trainTypeList = (List<HrGenreType>) hrGenreTypeMapper.query(paraMap);// 培训类别
		if(!trainTypeList.isEmpty()){
			StringBuilder sb = new StringBuilder();
			for(HrGenreType type : trainTypeList){
				sb.append(",{")
				  .append("'id':'" + type.getType_code() + "',")
				  .append("'pId':'0',")
				  .append("'name':'" + type.getType_name() + "'}");
			}
			
			List<HrPlant> trainPlanList = (List<HrPlant>) hrPlantMapper.query(paraMap);// 培训计划
			if(!trainPlanList.isEmpty()){
				if(paraMap.containsKey("isExam") && "1".equals(paraMap.get("isExam").toString())){
					// 只要培训计划要考核的
					for(HrPlant plan : trainPlanList){
						if("1".equals(plan.getIs_exam().toString())){
							sb.append(",{")
							  .append("'id':'" + plan.getPlan_id() + "',")
							  .append("'pId':'" + plan.getTrain_type_code() + "',")
							  .append("'name':'" + plan.getTrain_title() + "',")
							  .append("'train_way_code':'" + plan.getTrain_way_code() + "',")
							  .append("'isCert':'" + plan.getIs_cert() + "'}");
						}
					}
				}else{
					// 要所有培训计划
					for(HrPlant plan : trainPlanList){
						sb.append(",{")
						  .append("'id':'" + plan.getPlan_id() + "',")
						  .append("'pId':'" + plan.getTrain_type_code() + "',")
						  .append("'name':'" + plan.getTrain_title() + "',")
						  .append("'train_way_code':'" + plan.getTrain_way_code() + "',")
						  .append("'isCert':'" + plan.getIs_cert() + "'}");
					}
				}
			}
			return "[" + sb.substring(1).toString() + "]";
		}
		return null;
	}

	/**
	 * 查询一个培训计划关联的考试、补考、证书
	 */
	@Override
	public Map<String, Object> queryTrainPlanRelationExam(Map<String, Object> paraMap) throws DataAccessException {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			// 取考试安排
			HrExam exam = hrTrainExamineMapper.queryExamByPK(paraMap);
			if (exam != null) {
				map.put("exam", exam);
			} else {
				// 查培训安排
				Map<String, Object> trainClass = hrTrainRecordMapper.queryTrainClass(paraMap);
				if(trainClass != null){
					Map<String, Object> examMap = new HashMap<String, Object>();
					examMap.put("train_site", trainClass.get("train_site"));
					map.put("exam", examMap);
				}
			}
			
			// 取补考安排
			HrBukao bukao = hrTrainExamineMapper.queryBukaoByPK(paraMap);
			if (bukao != null) {
				map.put("bukao", bukao);
			} else {
				// 查询补考
				Map<String, Object> trainBK = hrTrainRecordMapper.queryTrainRecordBK(paraMap);
				if(trainBK != null){
					Map<String, Object> bukaoMap = new HashMap<String, Object>();
					bukaoMap.put("exam_site", trainBK.get("train_site"));
					map.put("bukao", bukaoMap);
				}
			}
			
			// 取培训证书
			HrTrainCert cert = hrTrainExamineMapper.queryTrainCertByPK(paraMap);
			if (cert != null) {
				map.put("cert", cert);
			}

			return map;
		}catch(Exception e){
			logger.error(e.getMessage(), e);
			return JSONObject.parseObject("{\"error\":\"查询失败.\",\"state\":\"false\"}");
		}
	}

	/**
	 * 保存考试安排
	 */
	@Override
	public String saveTrainExam(Map<String, Object> paraMap) throws DataAccessException {
		try{
			if(paraMap.containsKey("exam_way_code") && StringUtils.isEmpty(paraMap.get("exam_way_code").toString())){
				return "{\"error\":\"请选择考试方式.\",\"state\":\"true\"}";
			}
			if(paraMap.containsKey("train_date")){
				String datestr = paraMap.get("train_date").toString();
				paraMap.put("train_date", new SimpleDateFormat("yyyy-MM-dd").parse(datestr));
			}
			
			hrTrainExamineMapper.deleteTrainExam(paraMap);
			hrTrainExamineMapper.insertTrainExam(paraMap);
			return "{\"msg\":\"保存成功.\",\"state\":\"true\"}";
		}catch(ParseException pe){
			logger.error(pe.getMessage(), pe);
			return "{\"error\":\"考试日期格式错误.\",\"state\":\"false\"}";
		}catch(Exception e){
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}
	}

	/**
	 * 查询培训考试成绩
	 */
	@Override
	public String queryExamResult(Map<String, Object> paraMap) throws DataAccessException {
		if(paraMap.containsKey("dept_id") && StringUtils.isNotEmpty(paraMap.get("dept_id").toString())){
			String deptId = paraMap.get("dept_id").toString().split("@")[1];
			paraMap.put("dept_id", deptId);
		}
		
		SysPage sysPage = new SysPage();
		sysPage = (SysPage) paraMap.get("sysPage");
		if (sysPage.getTotal() == -1) {
			List<HrExamResult> list = (List<HrExamResult>) hrTrainExamineMapper.queryExamResult(paraMap);
			return ChdJson.toJson(list);
		} else {
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			List<HrExamResult> list = (List<HrExamResult>) hrTrainExamineMapper.queryExamResult(paraMap, rowBounds);
			PageInfo page = new PageInfo(list);
			return ChdJson.toJson(list, page.getTotal());
		}
	}

	/**
	 * 考试考试成绩
	 */
	@Override
	public String saveExamResultBatch(List<HrExamResult> list) throws DataAccessException {
		try{
			if(list.isEmpty()){
				return "{\"warn\":\"没有要保存的数据.\",\"state\":\"true\"}";
			}
			
			List<Map<String, Object>> mapList = JsonListMapUtil.beanToListMap(list);
			hrTrainExamineMapper.deleteExamResultBatch(mapList);
			hrTrainExamineMapper.insertExamResultBatch(mapList);
			return "{\"msg\":\"保存成功\",\"state\":\"true\"}";
		}catch(Exception e){
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}
	}

	/**
	 * 删除考试成绩
	 */
	@Override
	public String deleteExamResultBatch(List<HrExamResult> list) throws DataAccessException {
		try{
			if(!list.isEmpty()){
				hrTrainExamineMapper.deleteExamResultBatch(JsonListMapUtil.beanToListMap(list));
			}
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		}catch(Exception e){
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}
	}

	/**
	 * 导入考试成绩
	 */
	@Override
	public String importExamResult(Map<String, Object> paraMap) throws DataAccessException {
		Map<String, String> pMap = new HashMap<String, String>();
		pMap.put("1", "1");
		pMap.put("是", "1");
		pMap.put("0", "2");
		pMap.put("2", "2");
		pMap.put("否", "2");
		try{
			Map<String,Object> para = (Map<String, Object>) JSONArray.parse(paraMap.get("para").toString());
			String planId = para.get("plan_id").toString();
			
			List<Map<String, List<String>>> list = ReadFiles.readFiles(paraMap);
			if(list.isEmpty()){
				return "{\"warn\":\"没有导入数据.\",\"state\":\"true\"}";
			}
			
			String groupId = SessionManager.getGroupId();
			String hosId = SessionManager.getHosId();
			
			List<Map<String, Object>> saveList = new ArrayList<Map<String, Object>>();
			for(Map<String, List<String>> row : list){
				if(StringUtils.isEmpty(row.get("emp_code").get(1)) || "null".equals(row.get("emp_code").get(1))){
					continue;
				}
				if(StringUtils.isEmpty(row.get("is_pass").get(1)) || "null".equals(row.get("is_pass").get(1))){
					continue;
				}
				String empName = row.get("emp_name").get(1) == null ? "" : row.get("emp_name").get(1);
				Map<String, Object> save = new HashMap<String, Object>();
				save.put("group_id", groupId);
				save.put("hos_id", hosId);
				save.put("emp_code", row.get("emp_code").get(1));
				save.put("plan_id", planId);
				Map<String, Object> empIdMap = hrTrainExamineMapper.queryTrainObjByEmpCode(save);// trim去掉工号前面的"0"做判断
				if(empIdMap != null && empIdMap.containsKey("EMP_ID")){
					save.put("emp_id", empIdMap.get("EMP_ID"));
				}else{
					return "{\"warn\":\"培训对象中没有职工<br/>" + row.get("emp_code").get(1) + empName + "\",\"state\":\"false\"}";
				}
				save.put("score", row.get("score").get(1));
				save.put("is_pass", pMap.get(row.get("is_pass").get(1)) == null ? "2" : pMap.get(row.get("is_pass").get(1)));
				
				saveList.add(save);
			}
			hrTrainExamineMapper.deleteExamResultBatch(saveList);
			int count = hrTrainExamineMapper.insertExamResultBatch(saveList);
			if(count > 0){
				return "{\"msg\":\"共" + list.size() + "条，成功导入" + count + "条.\",\"state\":\"true\"}";
			}else{
				return "{\"msg\":\"共" + list.size() + "条，" + count + "条被导入.\",\"state\":\"true\"}";
			}
		}catch(Exception e){
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}
	}
	
	/**
	 * 保存补考安排
	 */
	@Override
	public String saveTrainBukao(Map<String, Object> paraMap) throws DataAccessException {
		try{
			if(paraMap.containsKey("exam_way_code") && StringUtils.isEmpty(paraMap.get("exam_way_code").toString())){
				return "{\"error\":\"请选择考核方式.\",\"state\":\"true\"}";
			}
			
			if(paraMap.containsKey("exam_date")){
				String datestr = paraMap.get("exam_date").toString();
				paraMap.put("exam_date", new SimpleDateFormat("yyyy-MM-dd").parse(datestr));
			}
			
			hrTrainExamineMapper.deleteTrainBukao(paraMap);
			hrTrainExamineMapper.insertTrainBukao(paraMap);
			return "{\"msg\":\"保存成功.\",\"state\":\"true\"}";
		}catch(ParseException pe){
			logger.error(pe.getMessage(), pe);
			return "{\"error\":\"考试日期格式错误.\",\"state\":\"false\"}";
		}catch(Exception e){
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}
	}

	/**
	 * 查询补考人员
	 */
	@Override
	public String queryBukaoEmp(Map<String, Object> paraMap) throws DataAccessException {
		if(paraMap.containsKey("dept_id") && StringUtils.isNotEmpty(paraMap.get("dept_id").toString())){
			String deptId = paraMap.get("dept_id").toString().split("@")[1];
			paraMap.put("dept_id", deptId);
		}
		
		SysPage sysPage = new SysPage();
		sysPage = (SysPage) paraMap.get("sysPage");
		if (sysPage.getTotal() == -1) {
			List<HrExamResult> list = (List<HrExamResult>) hrTrainExamineMapper.queryBukaoEmp(paraMap);
			return ChdJson.toJson(list);
		} else {
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			List<HrExamResult> list = (List<HrExamResult>) hrTrainExamineMapper.queryBukaoEmp(paraMap, rowBounds);
			PageInfo page = new PageInfo(list);
			return ChdJson.toJson(list, page.getTotal());
		}
	}

	/**
	 * 删除补考人员
	 */
	@Override
	public String deleteBukaoEmpBatch(List<HrExamResult> list) throws DataAccessException {
		try{
			if(!list.isEmpty()){
				hrTrainExamineMapper.deleteBukaoEmpBatch(JsonListMapUtil.beanToListMap(list));
			}
			return "{\"msg\":\"删除成绩\",\"state\":\"true\"}";
		}catch(Exception e){
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}
	}

	/**
	 * 保存补考人员
	 */
	@Override
	public String saveBukaoEmpBatch(List<HrExamResult> list) throws DataAccessException {
		try{
			if(list.isEmpty()){
				return "{\"warn\":\"没有要保存的数据.\",\"state\":\"true\"}";
			}
			
			hrTrainExamineMapper.deleteBukaoEmpBatch(JsonListMapUtil.beanToListMap(list));
			hrTrainExamineMapper.insertBukaoEmpBatch(JsonListMapUtil.beanToListMap(list));
			return "{\"msg\":\"保存成功.\",\"state\":\"true\"}";
		}catch(Exception e){
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}
	}

	/**
	 * 导入补考人员
	 */
	@Override
	public String importBukaoEmp(Map<String, Object> paraMap) throws DataAccessException {
		Map<String, String> pMap = new HashMap<String, String>();
		pMap.put("1", "1");
		pMap.put("是", "1");
		pMap.put("0", "2");
		pMap.put("2", "2");
		pMap.put("否", "2");
		try{
			Map<String,Object> para = (Map<String, Object>) JSONArray.parse(paraMap.get("para").toString());
			String planId = para.get("plan_id").toString();
			
			List<Map<String, List<String>>> list = ReadFiles.readFiles(paraMap);
			if(list.isEmpty()){
				return "{\"warn\":\"没有导入数据.\",\"state\":\"true\"}";
			}
			
			String groupId = SessionManager.getGroupId();
			String hosId = SessionManager.getHosId();
			List<Map<String, Object>> saveList = new ArrayList<Map<String, Object>>();
			for(Map<String, List<String>> row : list){
				if(StringUtils.isEmpty(row.get("emp_code").get(1)) || "null".equals(row.get("emp_code").get(1))){
					continue;
				}
				if(StringUtils.isEmpty(row.get("is_pass").get(1)) || "null".equals(row.get("is_pass").get(1))){
					continue;
				}
				String empName = row.get("emp_name").get(1) == null ? "" : row.get("emp_name").get(1);
				Map<String, Object> save = new HashMap<String, Object>();
				save.put("group_id", groupId);
				save.put("hos_id", hosId);
				save.put("emp_code", row.get("emp_code").get(1));
				save.put("plan_id", planId);
				Map<String, Object> empIdMap = hrTrainExamineMapper.queryTrainObjByEmpCode(save);// trim去掉工号前面的"0"做判断
				if(empIdMap != null && empIdMap.containsKey("EMP_ID")){
					save.put("emp_id", empIdMap.get("EMP_ID"));
				}else{
					return "{\"warn\":\"培训对象中没有职工<br/>" + row.get("emp_code").get(1) + empName + "\",\"state\":\"false\"}";
				}
				save.put("score", row.get("score").get(1));
				save.put("is_pass", pMap.get(row.get("is_pass").get(1)) == null ? "2" : pMap.get(row.get("is_pass").get(1)));
				
				saveList.add(save);
			}
			
			hrTrainExamineMapper.deleteBukaoEmpBatch(saveList);
			int count = hrTrainExamineMapper.insertBukaoEmpBatch(saveList);
			
			Map<String, Object> tMap = new HashMap<String, Object>();
			tMap.put("group_id", groupId);
			tMap.put("hos_id", hosId);
			tMap.put("plan_id", planId);
			List<HrExamResult> allBukao = hrTrainExamineMapper.queryBukaoEmp(tMap);
			tMap.put("emp_num", allBukao.size());
			hrTrainExamineMapper.updateTrainBukao(tMap);
			
			if(count > 0){
				return "{\"msg\":\"共" + list.size() + "条，成功导入" + count + "条.\",\"state\":\"true\"}";
			}else{
				return "{\"msg\":\"共" + list.size() + "条，" + count + "条被导入.\",\"state\":\"true\"}";
			}
		}catch(Exception e){
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}
	}

	/**
	 * 保存培训证书
	 */
	@Override
	public String saveTrainCert(Map<String, Object> paraMap) throws DataAccessException {
		try{
			if(paraMap.containsKey("cert_name") && StringUtils.isEmpty(paraMap.get("cert_name").toString())){
				return "{\"error\":\"请填写证书名称.\",\"state\":\"true\"}";
			}
			
			if(paraMap.containsKey("cert_date")){
				String datestr = paraMap.get("cert_date").toString();
				paraMap.put("cert_date", new SimpleDateFormat("yyyy-MM-dd").parse(datestr));
			}
			
			hrTrainExamineMapper.deleteTrainCert(paraMap);
			hrTrainExamineMapper.insertTrainCert(paraMap);
			return "{\"msg\":\"保存成功\",\"state\":\"true\"}";
		}catch(ParseException pe){
			logger.error(pe.getMessage(), pe);
			return "{\"error\":\"发证日期格式错误.\",\"state\":\"true\"}";
		}catch(Exception e){
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}
	}

	/**
	 * 查询职工培训证书
	 */
	@Override
	public String queryTrainEmpCert(Map<String, Object> paraMap) throws DataAccessException {
		if(paraMap.containsKey("dept_id") && StringUtils.isNotEmpty(paraMap.get("dept_id").toString())){
			String deptId = paraMap.get("dept_id").toString().split("@")[1];
			paraMap.put("dept_id", deptId);
		}
		
		SysPage sysPage = new SysPage();
		sysPage = (SysPage) paraMap.get("sysPage");
		if (sysPage.getTotal() == -1) {
			List<HrTrainEmpCert> list = (List<HrTrainEmpCert>) hrTrainExamineMapper.queryTrainEmpCert(paraMap);
			return ChdJson.toJson(list);
		} else {
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			List<HrTrainEmpCert> list = (List<HrTrainEmpCert>) hrTrainExamineMapper.queryTrainEmpCert(paraMap, rowBounds);
			PageInfo page = new PageInfo(list);
			return ChdJson.toJson(list, page.getTotal());
		}
	}

	/**
	 * 保存职工培训证书
	 */
	@Override
	public String saveTrainEmpCertBatch(List<HrTrainEmpCert> list) throws DataAccessException {
		try{
			if(list.isEmpty()){
				return "{\"warn\":\"请选择要保存的行.\",\"state\":\"true\"}";
			}
			
			Long groupId = Long.valueOf(SessionManager.getGroupId());
			Long hosId = Long.valueOf(SessionManager.getHosId());
			for(HrTrainEmpCert exam : list){
				exam.setGroup_id(groupId);
				exam.setHos_id(hosId);
			}
			
			hrTrainExamineMapper.deleteTrainEmpCertBatch(JsonListMapUtil.beanToListMap(list));
			hrTrainExamineMapper.insertTrainEmpCertBatch(JsonListMapUtil.beanToListMap(list));
			return "{\"msg\":\"保存成功\",\"state\":\"true\"}";
		}catch(Exception e){
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}
	}

	/**
	 * 删除培训证书
	 */
	@Override
	public String deleteTrainEmpCertBatch(List<HrTrainEmpCert> list) throws DataAccessException {
		try{
			if(!list.isEmpty()){
				hrTrainExamineMapper.deleteTrainEmpCertBatch(JsonListMapUtil.beanToListMap(list));
			}
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		}catch(Exception e){
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}
	}

	/**
	 * 生成职工培训证书记录
	 */
	@Override
	public String generateTrainExamCert(Map<String, Object> paraMap) throws DataAccessException {
		try{
			// 生成
			hrTrainExamineMapper.generateTrainExamCert(paraMap);
			
			makeUpCertCode(paraMap);// 填充证书编号
			return "{\"msg\":\"生成成功.\",\"state\":\"true\"}";
		}catch(Exception e){
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}
	}
	
	/**
	 * 补充证书编号
	 */
	private void makeUpCertCode(Map<String, Object> paraMap) throws DataAccessException{
		try{
			HrPlant plan = hrPlantMapper.queryByCode(paraMap);
			if(plan.getIs_cert() == 1){
				List<HrTrainEmpCert> list = hrTrainExamineMapper.queryNotCertCodeData(paraMap);// 没有证书编号的记录
				if(!list.isEmpty()){
					Map<String, Object> pMap = hrTrainExamineMapper.queryMaxCertCode(paraMap);// 查最大证书编号
					// 自动生成编号
					String certCode_ = plan.getTrain_type_code() + plan.getYear();
					int count = 0;
					if(pMap != null){
						String maxCertCode = pMap.get("CERT_CODE").toString();
						count = Integer.valueOf(maxCertCode.substring(maxCertCode.length() - 5));
					}
					for(HrTrainEmpCert empCert : list){
						empCert.setCert_code(certCode_ + String.format("%05d", ++count));
					}
					
					hrTrainExamineMapper.updateTrainEmpCertBatch(JsonListMapUtil.beanToListMap(list));
				}
			}
		}catch(Exception e){
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}
	}

	/**
	 * 更新一条考试成绩记录 by pk
	 */
	@Override
	public boolean updateExamResult(Map<String, Object> paraMap) throws DataAccessException {
		try{
			hrTrainExamineMapper.updateExamResult(paraMap);
			return true;
		}catch(Exception e){
			logger.error(e.getMessage(), e);
			return false;
		}
	}

	/**
	 * 更新一条补考记录 by pk
	 */
	@Override
	public boolean updateBukaoEmp(Map<String, Object> paraMap) throws DataAccessException {
		try{
			hrTrainExamineMapper.updateBukaoEmp(paraMap);
			return true;
		}catch(Exception e){
			logger.error(e.getMessage(), e);
			return false;
		}
	}

	/**
	 * 更新一条职工培训证书记录 by pk
	 */
	@Override
	public boolean updateTrainEmpCert(Map<String, Object> paraMap) throws DataAccessException {
		try{
			hrTrainExamineMapper.updateTrainEmpCert(paraMap);
			return true;
		}catch(Exception e){
			logger.error(e.getMessage(), e);
			return false;
		}
	}

	/**
	 * 查询补考记录返回集合
	 */
	@Override
	public List<HrExamResult> queryBukaoEmpList(Map<String, Object> paraMap) throws DataAccessException {
		return hrTrainExamineMapper.queryBukaoEmp(paraMap);
	}

	/**
	 * 批量更新补考记录
	 */
	@Override
	public boolean updateBukaoEmpBatch(List<Map<String, Object>> list) throws DataAccessException {
		try{
			hrTrainExamineMapper.updateBukaoEmpBatch(list);
			return true;
		}catch(Exception e){
			logger.error(e.getMessage(), e);
			return false;
		}
	}
	
	/**
	 * 批量更新考试成绩
	 */
	@Override
	public boolean updateExamResultBatch(List<Map<String, Object>> list) throws DataAccessException {
		try{
			hrTrainExamineMapper.updateExamResultBatch(list);
			return true;
		}catch(Exception e){
			logger.error(e.getMessage(), e);
			return false;
		}
	}
	
	/**
	 * 批量更新职工培训证书
	 */
	@Override
	public boolean updateTrainEmpCertBatch(List<Map<String, Object>> list) throws DataAccessException {
		try{
			hrTrainExamineMapper.updateTrainEmpCertBatch(list);
			return true;
		}catch(Exception e){
			logger.error(e.getMessage(), e);
			return false;
		}
	}

	/**
	 * 查考试成绩记录返回集合
	 */
	@Override
	public List<HrExamResult> queryExamResultList(Map<String, Object> paraMap) throws DataAccessException {
		return hrTrainExamineMapper.queryExamResult(paraMap);
	}

	/**
	 * 查职工培训证书记录返回集合
	 */
	@Override
	public List<HrTrainEmpCert> queryTrainEmpCertList(Map<String, Object> paraMap) throws DataAccessException {
		return hrTrainExamineMapper.queryTrainEmpCert(paraMap);
	}

	/**
	 * 取考试安排 by 培训计划
	 */
	@Override
	public HrExam getExam(Map<String, Object> paraMap) throws DataAccessException {
		return hrTrainExamineMapper.queryExamByPK(paraMap);
	}
	
	/**
	 * 取补考安排 by 培训计划
	 */
	@Override
	public HrBukao getBukao(Map<String, Object> paraMap) throws DataAccessException {
		return hrTrainExamineMapper.queryBukaoByPK(paraMap);
	}

	/**
	 * 取培训证书 By 培训计划
	 */
	@Override
	public HrTrainCert getTrainCert(Map<String, Object> paraMap) throws DataAccessException {
		return hrTrainExamineMapper.queryTrainCertByPK(paraMap);
	}

	/**
	 * 按考试不合格，生成补考记录
	 */
	@Override
	public String generateBukaoEmpBatch(Map<String, Object> paraMap) throws DataAccessException {
		try{
			hrTrainExamineMapper.generateBukaoEmpBatch(paraMap);
			return "{\"msg\":\"生成成功.\",\"state\":\"true\"}";
		}catch(Exception e){
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}
	}

	/**
	 * 更新补考安排
	 */
	@Override
	public boolean updateTrainBukao(Map<String, Object> paraMap) throws DataAccessException {
		try{
			hrTrainExamineMapper.updateTrainBukao(paraMap);
			return true;
		}catch(Exception e){
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}
	}

	/**
	 *  查询考试结果、与缺考职工数据
	 */
	@Override
	public String queryExamResultEmpData(Map<String, Object> paraMap) throws DataAccessException {
		SysPage sysPage = new SysPage();
		sysPage = (SysPage) paraMap.get("sysPage");
		if (sysPage.getTotal() == -1) {
			List<HrExamResult> list = (List<HrExamResult>) hrTrainExamineMapper.queryExamResultEmpData(paraMap);
			return ChdJson.toJson(list);
		} else {
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			List<HrExamResult> list = (List<HrExamResult>) hrTrainExamineMapper.queryExamResultEmpData(paraMap, rowBounds);
			PageInfo page = new PageInfo(list);
			return ChdJson.toJson(list, page.getTotal());
		}
	}

	/**
	 * 查询考试、补考职工数据
	 */
	@Override
	public String queryExamBukaoResultEmpData(Map<String, Object> paraMap) throws DataAccessException {
		SysPage sysPage = new SysPage();
		sysPage = (SysPage) paraMap.get("sysPage");
		if (sysPage.getTotal() == -1) {
			List<HrExamResult> list = (List<HrExamResult>) hrTrainExamineMapper.queryExamBukaoResultEmpData(paraMap);
			return ChdJson.toJson(list);
		} else {
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			List<HrExamResult> list = (List<HrExamResult>) hrTrainExamineMapper.queryExamBukaoResultEmpData(paraMap, rowBounds);
			PageInfo page = new PageInfo(list);
			return ChdJson.toJson(list, page.getTotal());
		}
	}

	/**
	 * 取一个职工培训证书记录 By PK（暂未使用）
	 */
	@Override
	public HrTrainEmpCert getTrainEmpCert(Map<String, Object> paraMap) throws DataAccessException {
		return hrTrainExamineMapper.getTrainEmpCert(paraMap);
	}

	/**
	 * 电子证书打印，通过主键取职工、证书数据
	 */
	@Override
	public Map<String, Object> generateDZBTrainEmpCert(Map<String, Object> paraMap) throws DataAccessException {
		//批量打印处理
		if(paraMap.get("emp_ids") == null && paraMap.get("print_id") != null){
			paraMap.put("emp_ids", paraMap.get("print_id"));
		}
		
		WebApplicationContext context = ContextLoader.getCurrentWebApplicationContext();
		HrTrainExamineMapper trainExamineMapper = (HrTrainExamineMapper)context.getBean("hrTrainExamineMapper");
		
		Map<String, Object> reMap = new HashMap<String, Object>();
		List<Map<String, Object>> trainEmpCertList = trainExamineMapper.getTrainEmpCertMapDZB(paraMap);
		reMap.put("main", trainEmpCertList.get(0));
		return reMap;
	}
	/**
	 * 电子证书批量打印，通过主键取职工、证书数据
	 */
	@Override
	public Map<String, Object> generateDZBTrainEmpCertBatch(Map<String, Object> paraMap) throws DataAccessException {
		WebApplicationContext context = ContextLoader.getCurrentWebApplicationContext();
		HrTrainExamineMapper trainExamineMapper = (HrTrainExamineMapper)context.getBean("hrTrainExamineMapper");
		
		Map<String, Object> reMap = new HashMap<String, Object>();
		List<Map<String, Object>> trainEmpCertList = trainExamineMapper.getTrainEmpCertMapDZB(paraMap);
		reMap.put("main", trainEmpCertList);
		return reMap;
	}

	/**
	 * 通过培训计划，取上传过职工培训证书的记录
	 */
	@Override
	public List<HrTrainEmpCert> queryTrainEmpCertExistsCertPath(Map<String, Object> paraMap)
			throws DataAccessException {
		return hrTrainExamineMapper.queryTrainEmpCertExistsCertPath(paraMap);
	}

	@Override
	public String getMaxCertCode(Map<String, Object> paraMap) throws DataAccessException {
		Map<String, Object> map = hrTrainExamineMapper.queryMaxCertCode(paraMap);
		if(map == null){
			return null;
		}
		return map.get("CERT_CODE").toString();
	}

	/**
	 * 查询培训对象职工数据
	 */
	@Override
	public String queryTrainObjEmpData(Map<String, Object> paraMap) throws DataAccessException {
		SysPage sysPage = new SysPage();
		sysPage = (SysPage) paraMap.get("sysPage");
		if (sysPage.getTotal() == -1) {
			List<HrExamResult> list = (List<HrExamResult>) hrTrainExamineMapper.queryTrainObjEmpData(paraMap);
			return ChdJson.toJson(list);
		} else {
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			List<HrExamResult> list = (List<HrExamResult>) hrTrainExamineMapper.queryTrainObjEmpData(paraMap, rowBounds);
			PageInfo page = new PageInfo(list);
			return ChdJson.toJson(list, page.getTotal());
		}
	}

	@Override
	public String generateExamResult(Map<String, Object> paraMap) throws DataAccessException {
		try{
			hrTrainExamineMapper.generateExamResult(paraMap);
			return "{\"msg\":\"生成成功.\",\"state\":\"true\"}";
		}catch(Exception e){
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}
	}

	@Override
	public List<HrExamResult> queryNoPassAndNoExamEmpData(Map<String, Object> paraMap) throws DataAccessException {
		return hrTrainExamineMapper.queryNoPassAndNoExamEmpData(paraMap);
	}

}
