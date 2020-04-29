package com.chd.hrp.hr.serviceImpl.nursing;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.chd.base.SessionManager;
import com.chd.base.SysPage;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.hrp.hr.dao.nursing.HrPromotionSummaryN3Mapper;
import com.chd.hrp.hr.dao.nursing.HrPromotionSummaryN3Mapper;
import com.chd.hrp.hr.entity.nursing.HrPromotionSummaryN3;
import com.chd.hrp.hr.entity.nursing.HrPromotionSummaryN3;
import com.chd.hrp.hr.entity.nursing.HrPromotionSummaryN3;
import com.chd.hrp.hr.entity.nursing.HrPromotionSummaryN3;
import com.chd.hrp.hr.service.nursing.HrPromotionSummaryN3Service;
import com.github.pagehelper.PageInfo;

/**
 * 护理晋级汇总审批表(N3)
 * 
 * @author Administrator
 *
 */
@Service("hrPromotionSummaryN3Service")
public class HrPromotionSummaryN3ServiceImpl implements HrPromotionSummaryN3Service {
	private static Logger logger = Logger.getLogger(HrPromotionSummaryN3ServiceImpl.class);

	@Resource(name = "hrPromotionSummaryN3Mapper")
	private final HrPromotionSummaryN3Mapper hrPromotionSummaryN3Mapper = null;

	// 默认显示20条数据
	RowBounds rowBoundsALL = new RowBounds(0, 20);

	/**
	 * 护理晋级汇总审批表(N3)查询
	 */
	@SuppressWarnings("unchecked")
	@Override
	public String queryPromotionSummaryN3(Map<String, Object> entityMap) throws DataAccessException {

		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		if (sysPage.getTotal() == -1) {

			List<HrPromotionSummaryN3> list = (List<HrPromotionSummaryN3>) hrPromotionSummaryN3Mapper.query(entityMap);

			return ChdJson.toJson(list);

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

			List<HrPromotionSummaryN3> list = (List<HrPromotionSummaryN3>) hrPromotionSummaryN3Mapper.query(entityMap,
					rowBounds);

			@SuppressWarnings("rawtypes")
			PageInfo page = new PageInfo(list);

			return ChdJson.toJson(list, page.getTotal());

		}

	}

	/**
	 * 护士长审核
	 * 
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	@Override
	public String auditHnurseAuditN3(List<HrPromotionSummaryN3> listVo)
			throws DataAccessException {

		try {
			String msg="";
		
			
             hrPromotionSummaryN3Mapper.auditHnurseAuditN3(listVo);
				msg="{\"msg\":\"审核成功.\",\"state\":\"true\"}";
			return msg;

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}
	}

	/**
	 * 护士长销审
	 * 
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	@Override
	public String reAuditHnurseAuditN3(Map<String, Object> entityMap) throws DataAccessException {

		try {
			List<Map> listVo = JSONArray.parseArray(entityMap.get("paramVo").toString(),
					Map.class);
             for (Map<String, Object> m : listVo) {
				entityMap.put("year", m.get("year"));
				entityMap.put("emp_id", m.get("emp_id"));
				entityMap.put("hnurse_audit",m.get("hnurse_audit"));
				hrPromotionSummaryN3Mapper.reAuditHnurseAuditN3(entityMap);
			}
			
			
			return "{\"msg\":\"销审成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}
	}

	/**
	 * 科护士长审核
	 * 
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	@Override
	public String auditDhnurseAuditN3(List<HrPromotionSummaryN3> listVo) throws DataAccessException {

		try {
				hrPromotionSummaryN3Mapper.auditDhnurseAuditN3(listVo);

		
			return "{\"msg\":\"审核成功.\",\"state\":\"true\"}";	
		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}
	}

	/**
	 * 科护士长销审
	 * 
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	@Override
	public String reAuditDhnurseAuditN3(Map<String, Object> entityMap) throws DataAccessException {

		try {
			List<Map> listVo = JSONArray.parseArray(entityMap.get("paramVo").toString(),
					Map.class);
             for (Map<String, Object> m : listVo) {
				entityMap.put("year", m.get("year"));
				entityMap.put("emp_id", m.get("emp_id"));
				entityMap.put("dhnurse_audit",m.get("dhnurse_audit"));
				entityMap.put("pass_reason", m.get("pass_reason"));
				hrPromotionSummaryN3Mapper.reAuditDhnurseAuditN3(entityMap);
			}

			return "{\"msg\":\"销审成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}
	}


	@Override
	public HrPromotionSummaryN3 queryPromotionSummaryN3ByCode(HrPromotionSummaryN3 hrPromotionSummaryN3) {
		return hrPromotionSummaryN3Mapper.queryPromotionSummaryN3ByCode(hrPromotionSummaryN3);
	}

	@Override
	public List<Map<String, Object>> queryPromotionN3ByPrint(
			Map<String, Object> entityMap) throws DataAccessException {
		 List<Map<String,Object>> list = ChdJson.toListLower(hrPromotionSummaryN3Mapper.queryPromotionN3ByPrint(entityMap));
		 return list;
	}

	@Override
	public String auditPromotionAuditN3(Map<String, Object> mapVo) throws DataAccessException {
		String stationTransef = "";
		List<HrPromotionSummaryN3> listVo = JSONArray.parseArray(String.valueOf(mapVo.get("paramVo")),
				HrPromotionSummaryN3.class);
		for (HrPromotionSummaryN3 hrPromotionSummaryN3 : listVo) {
			if (hrPromotionSummaryN3.getHnurse() == null) {
				stationTransef = "{\"error\":\"审核失败！请先进行护士长审核！\",\"state\":\"false\"}";
				return stationTransef;
			} else if(hrPromotionSummaryN3.getHnurse_audit() == 0){
				stationTransef = "{\"error\":\"审核失败！护士长未审核通过！\",\"state\":\"false\"}";
				return stationTransef;
			}else if (hrPromotionSummaryN3.getDhnurse() == null) {
				stationTransef = "{\"error\":\"审核失败！请先进行科护士长审核！\",\"state\":\"false\"}";
				return stationTransef;
			} else if(hrPromotionSummaryN3.getDhnurse_audit() == 0){
				stationTransef = "{\"error\":\"审核失败！科护士长未审核通过！\",\"state\":\"false\"}";
				return stationTransef;
			}else if (hrPromotionSummaryN3.getPromotion_audit() == null) {
				stationTransef = "{\"error\":\"审核失败！请先检查状态！\",\"state\":\"false\"}";
				return stationTransef;
			} else {
				hrPromotionSummaryN3.setPromotion(Long.parseLong(SessionManager.getUserId()));
				hrPromotionSummaryN3.setPromotion_date(new Date());
				hrPromotionSummaryN3Mapper.auditPromotionAuditN3(hrPromotionSummaryN3);
			}
		}
		stationTransef = "{\"msg\":\"审核成功.\",\"state\":\"true\"}";
		return stationTransef;
	}

}
