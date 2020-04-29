package com.chd.hrp.hr.serviceImpl.nursing;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.chd.base.SessionManager;
import com.chd.base.SysPage;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.base.util.DateUtil;
import com.chd.hrp.hr.dao.nursing.HrInserviceEducationMapper;
import com.chd.hrp.hr.dao.nursing.HrInserviceStatisticsMapper;
import com.chd.hrp.hr.entity.nursing.HrEducationStudent;
import com.chd.hrp.hr.entity.nursing.HrInserviceEducation;
import com.chd.hrp.hr.service.nursing.HrInserviceEducationService;
import com.github.pagehelper.PageInfo;

/**
 * 年度在职教育培训
 * 
 * @author Administrator
 *
 */
@Service("hrInserviceEducationService")
public class HrInserviceEducationServiceImpl implements HrInserviceEducationService {
	private static Logger logger = Logger.getLogger(HrInserviceEducationServiceImpl.class);

	@Resource(name = "hrInserviceEducationMapper")
	private final HrInserviceEducationMapper hrInserviceEducationMapper = null;
	
	@Resource(name = "hrInserviceStatisticsMapper")
	private final HrInserviceStatisticsMapper hrInserviceStatisticsMapper = null;

	// 默认显示20条数据
	RowBounds rowBoundsALL = new RowBounds(0, 20);

	/**
	 * 年度在职教育培训增加
	 */
	@Override
	public String addInserviceEducation(Map<String, Object> entityMap) throws DataAccessException {
		
		entityMap.put("edu_date", DateUtil.stringToDate(entityMap.get("edu_date").toString(), "yyyy-MM-dd"));
		// 获取对象
		List<HrInserviceEducation> list = hrInserviceEducationMapper.queryInserviceEducationById(entityMap);

		if (list.size() > 0) {
			for (HrInserviceEducation hrInserviceEducation : list) {
				if (hrInserviceEducation.getClasss_name().equals(entityMap.get("class_name"))) {
					return "{\"error\":\"课程名称：" + hrInserviceEducation.getClasss_name().toString() + "已存在.\"}";
				}
			}
		}
		try {
			
			List<HrEducationStudent> listVo = JSONArray.parseArray(entityMap.get("Param").toString(),HrEducationStudent.class);
			if(listVo==null){
				return "{\"error\":\"添加失败，请选择员工！\",\"state\":\"false\"}";
			}
			
			if (listVo!=null) {
				for (HrEducationStudent hrUserPermData : listVo) {
					Map<String,Object> map  = new HashMap<String,Object>();
					hrUserPermData.setGroup_id(Long.parseLong(SessionManager.getGroupId()));
					hrUserPermData.setHos_id(Long.parseLong(SessionManager.getHosId()));
					hrUserPermData.setEdu_date((Date)entityMap.get("edu_date"));
					hrUserPermData.setClass_name(entityMap.get("class_name").toString());
				}
				addEducationStudent(listVo);
			}
			
			int student_num = 0;
			entityMap.put("student_num", listVo.size());
			
			int state = hrInserviceEducationMapper.add(entityMap);
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}

	}

	/**
	 * 年度在职教育培训修改
	 */
	@Override
	public String updateInserviceEducation(Map<String, Object> entityMap) throws DataAccessException {
		try { 
			entityMap.put("edu_date", DateUtil.stringToDate(entityMap.get("edu_date").toString(), "yyyy-MM-dd"));
			deleteEducationStudent(entityMap);
			List<HrEducationStudent> listVo = JSONArray.parseArray(entityMap.get("Param").toString(),
					HrEducationStudent.class);
			if (listVo != null && listVo.size()>0) {
				for (HrEducationStudent hrUserPermData : listVo) {

					hrUserPermData.setGroup_id(Long.parseLong(SessionManager.getGroupId()));
					hrUserPermData.setHos_id(Long.parseLong(SessionManager.getHosId()));
					hrUserPermData.setEdu_date((Date)entityMap.get("edu_date"));
					hrUserPermData.setClass_name(entityMap.get("class_name").toString());
				}
				addEducationStudent(listVo);
			}
			
			entityMap.put("student_num", listVo==null ? 0:listVo.size());
			
			@SuppressWarnings("unused")
			int state = hrInserviceEducationMapper.update(entityMap);

			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}

	}

	/**
	 * 删除学员
	 * 
	 * @param entityMap
	 * @return
	 */
	private String deleteEducationStudent(Map<String, Object> entityMap) {

		try {
			hrInserviceEducationMapper.deleteEducationStudent(entityMap);
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}
	}

	/**
	 * 年度在职教育培训删除
	 */
	@Override
	public String deleteInserviceEducation(List<HrInserviceEducation> entityList) throws DataAccessException {

		try {
			deleteEducationStudentBatch(entityList);
			
			hrInserviceEducationMapper.deleteInserviceEducation(entityList);

			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}
	}

	private String deleteEducationStudentBatch(List<HrInserviceEducation> entityList) throws DataAccessException {
		try {
			Map<String, Object> entityMap = new HashMap<String, Object>();
			for (HrInserviceEducation hrInserviceEducation : entityList) {
				entityMap.put("hos_id",SessionManager.getHosId());
				entityMap.put("group_id", SessionManager.getGroupId());
				entityMap.put("class_name", hrInserviceEducation.getClasss_name());
				entityMap.put("edu_date", hrInserviceEducation.getEdu_date());
				hrInserviceEducationMapper.deleteEducationStudent(entityMap);
			}
			
		

			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}

	}

	/**
	 * 年度在职教育培训查询
	 */
	@SuppressWarnings("unchecked")
	@Override
	public String queryInserviceEducation(Map<String, Object> entityMap) throws DataAccessException {

		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		if (sysPage.getTotal() == -1) {

			List<HrInserviceEducation> list = (List<HrInserviceEducation>) hrInserviceEducationMapper.queryInserviceEducation(entityMap);

			return ChdJson.toJson(list);

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

			List<HrInserviceEducation> list = (List<HrInserviceEducation>) hrInserviceEducationMapper.queryInserviceEducation(entityMap,
					rowBounds);

			@SuppressWarnings("rawtypes")
			PageInfo page = new PageInfo(list);

			return ChdJson.toJson(list, page.getTotal());

		}

	}

	/**
	 * 跳转修改页面
	 */
	@Override
	public HrInserviceEducation queryByCodeInserviceEducation(Map<String, Object> entityMap)
			throws DataAccessException {
		return hrInserviceEducationMapper.queryByCode(entityMap);
	}

	/**
	 * 审核年度在职教育
	 */
	@Override
	public String auditInserviceEducation(List<Map<String, Object>> entityList) throws DataAccessException {
		return hrInserviceEducationMapper.auditInserviceEducation(entityList);

	}

	/**
	 * 取消审核年度在职教育
	 */
	@Override
	public String reAuditInserviceEducation(List<Map<String, Object>> entityList) throws DataAccessException {
		return hrInserviceEducationMapper.reAuditInserviceEducation(entityList);

	}

	/**
	 * 提交年度在职教育
	 */
	@Override
	public String confirmInserviceEducation(HrInserviceEducation hrInserviceEducation) throws DataAccessException {
		try {
			String msg="";
			SimpleDateFormat a = new SimpleDateFormat("yyyy/MM/dd");
			SimpleDateFormat a1 = new SimpleDateFormat("yyyy");
			Map<String, Object> entityMap= new HashMap<String, Object>();
			entityMap.put("edu_date",a.format(hrInserviceEducation.getEdu_date()) );
			entityMap.put("class_name", hrInserviceEducation.getClasss_name());
			//查询所有学员信息
			List<HrEducationStudent> list = (List<HrEducationStudent>) hrInserviceStatisticsMapper.queryStudent(entityMap);
			for (HrEducationStudent hrEducationStudent : list) {
				//查询学员信息是否存在(晋级汇总表)
				HrEducationStudent hrEducation=hrInserviceEducationMapper.queryEducation(hrEducationStudent);
				if (hrEducation!=null) {
					/*return"{\"error\":\"请先提交学员护理晋级申请表！.\"}";
					}else {*/
						hrInserviceEducationMapper.addConfirmInserviceEducation(hrEducationStudent);
				         
						hrInserviceEducationMapper.confirmInserviceEducation(hrInserviceEducation);
						msg="{\"msg\":\"提交成功.\",\"state\":\"true\"}";
					}
				
			}
			return msg;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}

	}

	/**
	 * 取消提交年度在职教育
	 */
	@Override
	public String reConfirmInserviceEducation(HrInserviceEducation hrInserviceEducation) throws DataAccessException {
		try {

			String msg="";
			SimpleDateFormat a = new SimpleDateFormat("yyyy/MM/dd");
			SimpleDateFormat a1 = new SimpleDateFormat("yyyy");
			Map<String, Object> entityMap= new HashMap<String, Object>();
			entityMap.put("edu_date",a.format(hrInserviceEducation.getEdu_date()) );
			entityMap.put("class_name", hrInserviceEducation.getClasss_name());
			//查询所有学员信息
			List<HrEducationStudent> list = (List<HrEducationStudent>) hrInserviceStatisticsMapper.queryStudent(entityMap);
			for (HrEducationStudent hrEducationStudent : list) {
				//查询学员信息是否存在(晋级汇总表)
				HrEducationStudent hrEducation=hrInserviceEducationMapper.queryEducation(hrEducationStudent);
				if (hrEducation==null) {
					msg="{\"error\":\"职工晋级申请表不存在.\"}";
					}else {
						hrInserviceEducationMapper.reConfirmInserviceEducationEvaluate(hrEducationStudent);
				         
						hrInserviceEducationMapper.reConfirmInserviceEducation(hrEducationStudent);
						msg="{\"msg\":\"撤回成功.\",\"state\":\"true\"}";
					}
				
			}
			return msg;
			
		} catch (Exception e) {
			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}

	}

	/**
	 * 增加培训学员
	 * 
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */

	@Override
	public String addEducationStudent(List<HrEducationStudent> entityMap) throws DataAccessException {
		try {

			int state = hrInserviceEducationMapper.addEducationStudent(entityMap);

			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}

	}

	@Override
	public String queryEducationStudent(Map<String, Object> entityMap) throws DataAccessException {
		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		if (sysPage.getTotal() == -1) {

			List<HrEducationStudent> list = (List<HrEducationStudent>) hrInserviceEducationMapper
					.queryEducationStudent(entityMap);

			return ChdJson.toJson(list);

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

			List<HrEducationStudent> list = (List<HrEducationStudent>) hrInserviceEducationMapper.queryEducationStudent(entityMap, rowBounds);

			@SuppressWarnings("rawtypes")
			PageInfo page = new PageInfo(list);

			return ChdJson.toJson(list, page.getTotal());

		}

	}
	
	/**
	 * 打印
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	@Override
	public List<Map<String, Object>> queryInserviceEducationByPrint(Map<String, Object> entityMap) throws DataAccessException {
		List<Map<String,Object>> list = ChdJson.toListLower(hrInserviceEducationMapper.queryInserviceEducationByPrint(entityMap));
		return list;
 	}
	
	/**
	 * 批量提交
	 */
	@Override
	public String confirmBatchInserviceEducation(List<HrInserviceEducation> entityList) throws DataAccessException {
		try {
			SimpleDateFormat a = new SimpleDateFormat("yyyy/MM/dd");
			SimpleDateFormat a1 = new SimpleDateFormat("yyyy");
			Map<String, Object> entityMap= new HashMap<String, Object>();
			entityMap.put("group_id", SessionManager.getGroupId());
			entityMap.put("hos_id", SessionManager.getHosId());
			
			
			
			

			String msg="";
			/*	SimpleDateFormat a = new SimpleDateFormat("yyyy/MM/dd");
			SimpleDateFormat a1 = new SimpleDateFormat("yyyy");
			Map<String, Object> entityMap= new HashMap<String, Object>();
			entityMap.put("edu_date",a.format(hrInserviceEducation.getEdu_date()) );
			entityMap.put("class_name", hrInserviceEducation.getClasss_name());*/
			//查询所有学员信息
			List<HrEducationStudent> list = (List<HrEducationStudent>) hrInserviceStatisticsMapper.queryStudent(entityMap);
			for (HrEducationStudent hrEducationStudent : list) {
				//查询学员信息是否存在(晋级汇总表)
				HrEducationStudent hrEducation=hrInserviceEducationMapper.queryEducation(hrEducationStudent);
				if (hrEducation!=null) {
				
						hrInserviceEducationMapper.addConfirmInserviceEducation(hrEducationStudent);
				         
						//hrInserviceEducationMapper.confirmInserviceEducation(hrInserviceEducation);
						msg="{\"msg\":\"提交成功.\",\"state\":\"true\"}";
					}
				
			}
			hrInserviceEducationMapper.confirmBatchInserviceEducation(entityList,entityMap);
			return "{\"msg\":\"提交成功.\",\"state\":\"true\"}";
		
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}
	}
	
	/**
	 * 批量撤回
	 */
	@Override
	public String reConfirmBatchInserviceEducation(List<HrInserviceEducation> entityList) throws DataAccessException {
		try {
			SimpleDateFormat a = new SimpleDateFormat("yyyy/MM/dd");
			SimpleDateFormat a1 = new SimpleDateFormat("yyyy");
			Map<String, Object> entityMap= new HashMap<String, Object>();
			entityMap.put("group_id", SessionManager.getGroupId());
			entityMap.put("hos_id", SessionManager.getHosId());
			
			//查询所有学员信息
			List<HrEducationStudent> list = (List<HrEducationStudent>) hrInserviceStatisticsMapper.queryStudent(entityMap);
			for (HrEducationStudent hrEducationStudent : list) {
				//查询学员信息是否存在(晋级汇总表)
				HrEducationStudent hrEducation=hrInserviceEducationMapper.queryEducation(hrEducationStudent);
				if (hrEducation!=null) {
					/*msg="{\"error\":\"职工晋级申请表不存在.\"}";
					}else {*/
						hrInserviceEducationMapper.reConfirmInserviceEducationEvaluate(hrEducationStudent);
				         
					/*	hrInserviceEducationMapper.reConfirmInserviceEducation(hrEducationStudent);
						msg="{\"msg\":\"撤回成功.\",\"state\":\"true\"}";*/
					}
				
			}
			hrInserviceEducationMapper.reConfirmBatchInserviceEducation(entityList,entityMap);
			return "{\"msg\":\"撤回成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}
	}
    /**
     * 修改页面删除学员
     */
	@Override
	public String deleteEducationStudentBatc(List<HrEducationStudent> listVo)
			throws DataAccessException {
		try{
		if(listVo.size()>0){
			SimpleDateFormat a = new SimpleDateFormat("yyyy/MM/dd");
			Map<String, Object> entityMap= new HashMap<String, Object>();
			entityMap.put("group_id", SessionManager.getGroupId());
			entityMap.put("hos_id", SessionManager.getHosId());
			for (HrEducationStudent hrEducationStudent : listVo) {
				entityMap.put("edu_date",a.format(hrEducationStudent.getEdu_date()) );
				entityMap.put("class_name", hrEducationStudent.getClass_name());
			}
			hrInserviceEducationMapper.deleteEducationStudentBatc(listVo,entityMap);
		}
		
		return "{\"msg\":\"删除学员成功.\",\"state\":\"true\"}";
	}catch (Exception e){
		logger.error(e.getMessage(), e);
		throw new SysException(e.getMessage());
	}
	}

	@Override
	public Map<String, Object> queryEmp(Map<String, Object> mapVo)
			throws DataAccessException {
		return hrInserviceEducationMapper.queryEmp(mapVo);
	}
}
