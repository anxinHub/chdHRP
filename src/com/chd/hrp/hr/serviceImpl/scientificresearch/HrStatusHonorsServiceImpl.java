package com.chd.hrp.hr.serviceImpl.scientificresearch;

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
import com.chd.base.ftp.FtpUtil;
import com.chd.base.util.ChdJson;
import com.chd.base.util.DateUtil;
import com.chd.hrp.hr.dao.scientificresearch.HrStatusHonorsMapper;
import com.chd.hrp.hr.entity.scientificresearch.HrEmpAcadeStatusDetail;
import com.chd.hrp.hr.entity.scientificresearch.HrStatusHonors;
import com.chd.hrp.hr.service.base.HrBaseService;
import com.chd.hrp.hr.service.scientificresearch.HrStatusHonorsService;
import com.github.pagehelper.PageInfo;

/**
 * 个人学术地位申请
 * 
 * @author Administrator
 *
 */
@Service("hrStatusHonorsService")
public class HrStatusHonorsServiceImpl implements HrStatusHonorsService {
	private static Logger logger = Logger.getLogger(HrStatusHonorsServiceImpl.class);

	@Resource(name = "hrStatusHonorsMapper")
	private final HrStatusHonorsMapper hrStatusHonorsMapper = null;
	

	@Resource(name = "hrBaseService")
	private final HrBaseService hrBaseService = null;

	// 默认显示20条数据
	RowBounds rowBoundsALL = new RowBounds(0, 20);

	/**
	 * 个人学术地位申请增加
	 */
	@Override
	public String addStatusHonors(Map<String, Object> entityMap) throws DataAccessException {
		try {
			Date d1 = new SimpleDateFormat("yyyy-MM-dd").parse((String)entityMap.get("beg_date"));
			Date d2 = new SimpleDateFormat("yyyy-MM-dd").parse((String)entityMap.get("end_date"));
			if(DateUtil.compareDate(d1, d2)){
				return "{\"msg\":\"任职结束日期不能早于任职开始日期\",\"state\":\"false\"}";
			}
			
			int state = 0;
			
			Map<String, Object> mapVo = new HashMap<String, Object>();
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());
			mapVo.put("bill_code", "HR_EMP_ACADE_STATUS_APPLY");
			mapVo.put("prm_perfixe", "AC");
			String apply_no = hrBaseService.QueryHrBillNo(mapVo);
			entityMap.put("apply_no", apply_no);
			@SuppressWarnings("unused")

			List<HrEmpAcadeStatusDetail> alllistVo = JSONArray.parseArray(String.valueOf(entityMap.get("gridData")),
					HrEmpAcadeStatusDetail.class);
			List<HrEmpAcadeStatusDetail> list = new ArrayList<HrEmpAcadeStatusDetail>();
			/**
			 * 增加
			 */
			if (alllistVo != null && alllistVo.size() > 0) {
				for (HrEmpAcadeStatusDetail hrEmpAcadeStatusDetail : alllistVo) {
					hrEmpAcadeStatusDetail.setHos_id(Double.parseDouble(SessionManager.getHosId()));
					hrEmpAcadeStatusDetail.setGroup_id(Double.parseDouble(SessionManager.getGroupId()));
					hrEmpAcadeStatusDetail.setEmp_id(Double.parseDouble(entityMap.get("emp_id").toString()));
					hrEmpAcadeStatusDetail.setApply_no(entityMap.get("apply_no").toString());
					hrEmpAcadeStatusDetail.setStatus_code(entityMap.get("status_code").toString());
					list.add(hrEmpAcadeStatusDetail);
				}
			}
			
			state = hrStatusHonorsMapper.add(entityMap);
			if(list.size() > 0){
				state = hrStatusHonorsMapper.addStatusDetail(list);
			}
			
           if (state!=0) {
        	   hrBaseService.updateAndQueryHrBillNo(mapVo);
           }
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}

	}

	/**
	 * 个人学术地位申请修改
	 */
	@Override
	public String updateStatusHonors(Map<String, Object> entityMap) throws DataAccessException {
		try {
			@SuppressWarnings("unused")
			List<HrEmpAcadeStatusDetail> alllistVo = JSONArray.parseArray(String.valueOf(entityMap.get("gridData")), HrEmpAcadeStatusDetail.class);
			List<HrEmpAcadeStatusDetail> entityList = new ArrayList<HrEmpAcadeStatusDetail>();
			/**
			 * 增加
			 */
			if (alllistVo != null && alllistVo.size() > 0) {
				for (HrEmpAcadeStatusDetail hrEmpAcadeStatusDetail : alllistVo) {
					
					
					
					hrEmpAcadeStatusDetail.setHos_id(Double.parseDouble(SessionManager.getHosId()));
					hrEmpAcadeStatusDetail.setGroup_id(Double.parseDouble(SessionManager.getGroupId()));
					hrEmpAcadeStatusDetail.setEmp_id(Double.parseDouble(entityMap.get("emp_id").toString()));
					hrEmpAcadeStatusDetail.setApply_no(entityMap.get("apply_no").toString());
					hrEmpAcadeStatusDetail.setStatus_code(entityMap.get("status_code").toString());
					entityList.add(hrEmpAcadeStatusDetail);
				}

			}
			
			if(entityList.size() > 0){
				hrStatusHonorsMapper.update(entityMap);
				hrStatusHonorsMapper.deleteStatusDetail(entityList);
				hrStatusHonorsMapper.addStatusDetail(entityList);
			}
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}

	}

	/**
	 * 个人学术地位申请删除
	 */
	@Override
	public String deleteStatusHonors(List<HrStatusHonors> entityList) throws DataAccessException {
		
		try {
			List<HrStatusHonors> listMNew = new ArrayList<HrStatusHonors>();
			List<HrEmpAcadeStatusDetail> listDNew = new ArrayList<HrEmpAcadeStatusDetail>();
			
			boolean falg = true;
			
			for (HrStatusHonors hrStatusHonors : entityList) {

				hrStatusHonors.setGroup_id(Integer.parseInt(SessionManager.getGroupId()));
				hrStatusHonors.setHos_id(Integer.parseInt(SessionManager.getHosId()));
				
				//判断状态
				if (hrStatusHonors.getState() !=null && hrStatusHonors.getState()!=0) {
					falg = false;
					continue;
				}
				
				List<HrEmpAcadeStatusDetail> list = hrStatusHonorsMapper.queryHrDetailByCode(hrStatusHonors);
				if (list.size() != 0) {
					for (HrEmpAcadeStatusDetail hrAcadeHonorDetail : list) {
						String pathall = hrAcadeHonorDetail.getAccessory();
						String path = pathall.substring(pathall.indexOf("/", pathall.indexOf("/") + 1), pathall.lastIndexOf("/") + 1);
						String file_name = pathall.substring(pathall.lastIndexOf("/") + 1);
						FtpUtil.deleteFile(path, file_name);
						listDNew.add(hrAcadeHonorDetail);
					}
				}
				listMNew.add(hrStatusHonors);
			}
			
			if (!falg) {
				return ("{\"error\":\"删除失败,请选择新建状态申请删除\",\"state\":\"false\"}");
			}
			
			if (listMNew.size() > 0) {
				//删除明细
				if(listDNew.size()>0){
					hrStatusHonorsMapper.deleteStatusDetail(listDNew);
				}
            	//删除主表
				hrStatusHonorsMapper.deleteStatusHonors(listMNew);
			}
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}
		
	}

	/**
	 * 个人学术地位申请查询
	 */
	@SuppressWarnings("unchecked")
	@Override
	public String queryStatusHonors(Map<String, Object> entityMap) throws DataAccessException {

		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		if (sysPage.getTotal() == -1) {

			List<HrStatusHonors> list = (List<HrStatusHonors>) hrStatusHonorsMapper.query(entityMap);

			return ChdJson.toJson(list);

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

			List<HrStatusHonors> list = (List<HrStatusHonors>) hrStatusHonorsMapper.query(entityMap, rowBounds);

			@SuppressWarnings("rawtypes")
			PageInfo page = new PageInfo(list);

			return ChdJson.toJson(list, page.getTotal());

		}

	}

	/**
	 * 跳转修改页面
	 */
	@Override
	public HrStatusHonors queryByCodeStatusHonors(Map<String, Object> entityMap) throws DataAccessException {
		return hrStatusHonorsMapper.queryByCode(entityMap);
	}

	@Override
	public String queryStatus(Map<String, Object> entityMap) throws DataAccessException {
		List<Map<String, Object>> list = hrStatusHonorsMapper.queryStatus(entityMap);
		return JSONArray.toJSONString(list);

	}

	@Override
	public String queryHrEmpAcadeStatusDetail(Map<String, Object> entityMap) throws DataAccessException {
		SysPage sysPage = new SysPage();
		sysPage = (SysPage) entityMap.get("sysPage");
		if (sysPage.getTotal() == -1) {
			List<HrEmpAcadeStatusDetail> list = (List<HrEmpAcadeStatusDetail>) hrStatusHonorsMapper.queryHrEmpAcadeStatusDetail(entityMap);
			return ChdJson.toJson(list);
		} else {
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			List<HrEmpAcadeStatusDetail> list = (List<HrEmpAcadeStatusDetail>) hrStatusHonorsMapper.queryHrEmpAcadeStatusDetail(entityMap, rowBounds);
			@SuppressWarnings("rawtypes")
			PageInfo page = new PageInfo(list);
			return ChdJson.toJson(list, page.getTotal());
		}

	}

	@Override
	public List<HrEmpAcadeStatusDetail> queryHrDetailByCode(HrStatusHonors hrStatusHonors) throws DataAccessException {
			List<HrEmpAcadeStatusDetail> list =hrStatusHonorsMapper.queryHrDetailByCode(hrStatusHonors);
			return list;
	}
	/**
	 * 提交
	 */
	@Override
	public String confirmStatusHonors(HrStatusHonors hrStatusHonors) throws DataAccessException {
		try {
			hrStatusHonorsMapper.confirmStatusHonors(hrStatusHonors);
				
				return "{\"msg\":\"提交成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}
	}
	/**
	 * 取消提交
	 */
	@Override
	public String reConfirmStatusHonors(HrStatusHonors hrStatusHonors) throws DataAccessException {
		try {
			hrStatusHonorsMapper.reConfirmStatusHonors(hrStatusHonors);
				
				return "{\"msg\":\"撤回成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}
	}
	/**
	 * 审核
	 */
	@Override
	public String auditStatusHonors(HrStatusHonors hrStatusHonors) throws DataAccessException {
		try {
			hrStatusHonorsMapper.auditStatusHonors(hrStatusHonors);
				
				return "{\"msg\":\"审核成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}
	}
	/**
	 * 取消审核
	 */
	@Override
	public String unAuditStatusHonors(HrStatusHonors hrStatusHonors) throws DataAccessException {
		try {
			hrStatusHonorsMapper.unAuditStatusHonors(hrStatusHonors);
				
				return "{\"msg\":\"销审成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}
	}
	
	/**
	 * 删除明细
	 */
	@Override
	public String deleteAcademicHonorsDetail(List<HrEmpAcadeStatusDetail> entityList) throws DataAccessException {
		try {
            if (entityList.size() > 0) {
            	hrStatusHonorsMapper.deleteStatusDetail(entityList);
			}
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}
	}



}
