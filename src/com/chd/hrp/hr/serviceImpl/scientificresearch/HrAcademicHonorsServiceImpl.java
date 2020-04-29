package com.chd.hrp.hr.serviceImpl.scientificresearch;

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
import com.chd.base.SessionManager;
import com.chd.base.SysPage;
import com.chd.base.exception.SysException;
import com.chd.base.ftp.FtpUtil;
import com.chd.base.util.ChdJson;
import com.chd.hrp.hr.dao.scientificresearch.HrAcademicHonorsMapper;
import com.chd.hrp.hr.entity.scientificresearch.HrAcadeHonorDetail;
import com.chd.hrp.hr.entity.scientificresearch.HrAcademicHonors;
import com.chd.hrp.hr.entity.scientificresearch.HrEmpAcadeStatusDetail;
import com.chd.hrp.hr.service.base.HrBaseService;
import com.chd.hrp.hr.service.scientificresearch.HrAcademicHonorsService;
import com.github.pagehelper.PageInfo;

/**
 * 个人学术荣誉申请
 * 
 * @author Administrator
 *
 */
@Service("hrAcademicHonorsService")
public class HrAcademicHonorsServiceImpl implements HrAcademicHonorsService {
	private static Logger logger = Logger.getLogger(HrAcademicHonorsServiceImpl.class);

	@Resource(name = "hrAcademicHonorsMapper")
	private final HrAcademicHonorsMapper hrAcademicHonorsMapper = null;


	@Resource(name = "hrBaseService")
	private final HrBaseService hrBaseService = null;
	// 默认显示20条数据
	RowBounds rowBoundsALL = new RowBounds(0, 20);

	/**
	 * 个人学术荣誉申请增加
	 */
	@Override
	public String addAcademicHonors(Map<String, Object> entityMap) throws DataAccessException {
		try {
			Map<String, Object> mapVo = new HashMap<String, Object>();
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());
			mapVo.put("bill_code", "HR_ACADE_HONOR_APPLY");
			mapVo.put("prm_perfixe", "HO");
			String apply_no = hrBaseService.QueryHrBillNo(mapVo);
			entityMap.put("apply_no", apply_no);
			int state = 0;

			@SuppressWarnings("unused")
			List<HrAcadeHonorDetail> alllistVo = JSONArray.parseArray(String.valueOf(entityMap.get("gridData")), HrAcadeHonorDetail.class);

			List<HrAcadeHonorDetail> list = new ArrayList<HrAcadeHonorDetail>();

			/**
			 * 增加
			 */
			if (alllistVo != null && alllistVo.size() > 0) {
				for (HrAcadeHonorDetail hrAcadeHonorDetail : alllistVo) {
					hrAcadeHonorDetail.setHos_id(Double.parseDouble(SessionManager.getHosId()));
					hrAcadeHonorDetail.setGroup_id(Double.parseDouble(SessionManager.getGroupId()));
					hrAcadeHonorDetail.setEmp_id(Double.parseDouble(entityMap.get("emp_id").toString()));
					hrAcadeHonorDetail.setApply_no(entityMap.get("apply_no").toString());
					hrAcadeHonorDetail.setHonor_code(entityMap.get("honor_code").toString());
					list.add(hrAcadeHonorDetail);
				}

			}
			state = hrAcademicHonorsMapper.add(entityMap);
			if (list.size() > 0) {
				state = hrAcademicHonorsMapper.addHonorDetail(list);
			}
			if (state != 0) {
				hrBaseService.updateAndQueryHrBillNo(mapVo);
			}
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}

	}

	/**
	 * 个人学术荣誉申请修改
	 */
	@Override
	public String updateAcademicHonors(Map<String, Object> entityMap) throws DataAccessException {

		try {
			@SuppressWarnings("unused")
			List<HrAcadeHonorDetail> alllistVo = JSONArray.parseArray(String.valueOf(entityMap.get("gridData")), HrAcadeHonorDetail.class);
			List<HrAcadeHonorDetail> entityList = new ArrayList<HrAcadeHonorDetail>();
			/**
			 * 增加
			 */
			if (alllistVo != null && alllistVo.size() > 0) {
				for (HrAcadeHonorDetail hrAcadeHonorDetail : alllistVo) {
					
					hrAcadeHonorDetail.setHos_id(Double.parseDouble(SessionManager.getHosId()));
					hrAcadeHonorDetail.setGroup_id(Double.parseDouble(SessionManager.getGroupId()));
					hrAcadeHonorDetail.setEmp_id(Double.parseDouble(entityMap.get("emp_id").toString()));
					hrAcadeHonorDetail.setApply_no(entityMap.get("apply_no").toString());
					hrAcadeHonorDetail.setHonor_code(entityMap.get("honor_code").toString());
					entityList.add(hrAcadeHonorDetail);
					
				}
			}
			
			if(entityList.size() > 0){
				hrAcademicHonorsMapper.update(entityMap);
				hrAcademicHonorsMapper.deleteHonorDetail(entityList);
				hrAcademicHonorsMapper.addHonorDetail(entityList);
			}
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}

	}

	/**
	 * 个人学术荣誉申请删除
	 */
	@Override
	public String deleteAcademicHonors(List<HrAcademicHonors> entityList) throws DataAccessException {
		try {
			List<HrAcademicHonors> listMNew = new ArrayList<HrAcademicHonors>();
			List<HrAcadeHonorDetail> listDNew = new ArrayList<HrAcadeHonorDetail>();
			boolean falg = true;
			
			for (HrAcademicHonors hrAcademicHonors : entityList) {

				hrAcademicHonors.setGroup_id(Integer.parseInt(SessionManager.getGroupId()));
				hrAcademicHonors.setHos_id(Integer.parseInt(SessionManager.getHosId()));
				
				//判断状态
				if (hrAcademicHonors.getState() !=null && hrAcademicHonors.getState()!=0) {
					falg = false;
					continue;
				}
				
				List<HrAcadeHonorDetail> list = hrAcademicHonorsMapper.queryHrDetailByCode(hrAcademicHonors);
				if (list.size() != 0) {
					for (HrAcadeHonorDetail hrAcadeHonorDetail : list) {
						String pathall = hrAcadeHonorDetail.getAccessory();
						String path = pathall.substring(pathall.indexOf("/", pathall.indexOf("/") + 1), pathall.lastIndexOf("/") + 1);
						String file_name = pathall.substring(pathall.lastIndexOf("/") + 1);
						FtpUtil.deleteFile(path, file_name);
						listDNew.add(hrAcadeHonorDetail);
					}
				}
				listMNew.add(hrAcademicHonors);
			}
			
			if (!falg) {
				return ("{\"error\":\"删除失败,请选择新建状态申请删除\",\"state\":\"false\"}");
			}
			
			if (listMNew.size() > 0) {
				//删除明细
				if(listDNew.size()>0){
					hrAcademicHonorsMapper.deleteHonorDetail(listDNew);
				}
            	//删除主表
    			hrAcademicHonorsMapper.deleteAcademicHonors(listMNew);
			}
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}
	}

	/**
	 * 个人学术荣誉申请查询
	 */
	@SuppressWarnings("unchecked")
	@Override
	public String queryAcademicHonors(Map<String, Object> entityMap) throws DataAccessException {

		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		if (sysPage.getTotal() == -1) {

			List<HrAcademicHonors> list = (List<HrAcademicHonors>) hrAcademicHonorsMapper.query(entityMap);

			return ChdJson.toJson(list);

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

			List<HrAcademicHonors> list = (List<HrAcademicHonors>) hrAcademicHonorsMapper.query(entityMap, rowBounds);

			@SuppressWarnings("rawtypes")
			PageInfo page = new PageInfo(list);

			return ChdJson.toJson(list, page.getTotal());

		}

	}

	/**
	 * 跳转修改页面
	 */
	@Override
	public HrAcademicHonors queryByCodeAcademicHonors(Map<String, Object> entityMap) throws DataAccessException {
		return hrAcademicHonorsMapper.queryByCode(entityMap);
	}

	@Override
	public String queryHonor(Map<String, Object> entityMap) throws DataAccessException {
		List<Map<String, Object>> list = hrAcademicHonorsMapper.queryHonor(entityMap);
		return JSONArray.toJSONString(list);

	}

	@Override
	public String queryHrAcadeHonorDetail(Map<String, Object> entityMap) throws DataAccessException {

		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		if (sysPage.getTotal() == -1) {

			List<HrAcadeHonorDetail> list = (List<HrAcadeHonorDetail>) hrAcademicHonorsMapper.queryHrAcadeHonorDetail(entityMap);

			return ChdJson.toJson(list);

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

			List<HrAcadeHonorDetail> list = (List<HrAcadeHonorDetail>) hrAcademicHonorsMapper.queryHrAcadeHonorDetail(entityMap, rowBounds);

			@SuppressWarnings("rawtypes")
			PageInfo page = new PageInfo(list);

			return ChdJson.toJson(list, page.getTotal());

		}

	}

	@Override
	public List<HrAcadeHonorDetail> queryHrDetailByCode(HrAcademicHonors hrAcademicHonors) throws DataAccessException {
			List<HrAcadeHonorDetail> list =hrAcademicHonorsMapper.queryHrDetailByCode(hrAcademicHonors);
			return list;
	}

	@Override
	public String confirmAcademicHonors(HrAcademicHonors hrAcademicHonors) throws DataAccessException {
		try {
			hrAcademicHonorsMapper.confirmAcademicHonors(hrAcademicHonors);
				
				return "{\"msg\":\"提交成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}
	}

	@Override
	public String reConfirmAcademicHonors(HrAcademicHonors hrAcademicHonors) throws DataAccessException {
		try {
			hrAcademicHonorsMapper.reConfirmAcademicHonors(hrAcademicHonors);
				
				return "{\"msg\":\"撤回成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}
	}

	@Override
	public String auditAcademicHonors(HrAcademicHonors hrAcademicHonors) throws DataAccessException {
		try {
			hrAcademicHonorsMapper.auditAcademicHonors(hrAcademicHonors);
				
				return "{\"msg\":\"审核成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}
	}

	@Override
	public String unAuditAcademicHonors(HrAcademicHonors hrAcademicHonors) throws DataAccessException {
		try {
			hrAcademicHonorsMapper.unAuditAcademicHonors(hrAcademicHonors);
				
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
	public String deleteAcademicHonorsDetail(List<HrAcadeHonorDetail> entityList) throws DataAccessException {
		try {
            if (entityList.size() > 0) {
            	hrAcademicHonorsMapper.deleteHonorDetail(entityList);
			}
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}
	}



}
