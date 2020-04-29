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
import com.chd.hrp.hr.dao.nursing.HrPromotionSummaryN1Mapper;
import com.chd.hrp.hr.entity.nursing.HrPromotionSummaryN1;
import com.chd.hrp.hr.service.nursing.HrPromotionSummaryN1Service;
import com.github.pagehelper.PageInfo;

/**
 * 护理晋级汇总审批表(N1)
 * 
 * @author Administrator
 *
 */
@Service("hrPromotionSummaryN1Service")
public class HrPromotionSummaryN1ServiceImpl implements HrPromotionSummaryN1Service {
	private static Logger logger = Logger.getLogger(HrPromotionSummaryN1ServiceImpl.class);

	@Resource(name = "hrPromotionSummaryN1Mapper")
	private final HrPromotionSummaryN1Mapper hrPromotionSummaryN1Mapper = null;

	// 默认显示20条数据
	RowBounds rowBoundsALL = new RowBounds(0, 20);

	/**
	 * 护理晋级汇总审批表(N1)查询
	 */
	@SuppressWarnings("unchecked")
	@Override
	public String queryPromotionSummaryN1(Map<String, Object> entityMap) throws DataAccessException {

		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		if (sysPage.getTotal() == -1) {

			List<HrPromotionSummaryN1> list = (List<HrPromotionSummaryN1>) hrPromotionSummaryN1Mapper.query(entityMap);

			return ChdJson.toJson(list);

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

			List<HrPromotionSummaryN1> list = (List<HrPromotionSummaryN1>) hrPromotionSummaryN1Mapper.query(entityMap,
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
	public String auditHnurseAuditN1(List<HrPromotionSummaryN1> listVo)
			throws DataAccessException {

		try {
			String msg="";
		
			
             hrPromotionSummaryN1Mapper.auditHnurseAuditN1(listVo);
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
	public String reAuditHnurseAuditN1(Map<String, Object> entityMap) throws DataAccessException {

		try {
			List<Map> listVo = JSONArray.parseArray(entityMap.get("paramVo").toString(),
					Map.class);
             for (Map<String, Object> m : listVo) {
				entityMap.put("year", m.get("year"));
				entityMap.put("emp_id", m.get("emp_id"));
				entityMap.put("hnurse_audit",m.get("hnurse_audit"));
				hrPromotionSummaryN1Mapper.reAuditHnurseAuditN1(entityMap);
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
	public String auditDhnurseAuditN1(List<HrPromotionSummaryN1> listVo) throws DataAccessException {

		try {
				hrPromotionSummaryN1Mapper.auditDhnurseAuditN1(listVo);

		
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
	public String reAuditDhnurseAuditN1(Map<String, Object> entityMap) throws DataAccessException {

		try {
			List<Map> listVo = JSONArray.parseArray(entityMap.get("paramVo").toString(),
					Map.class);
             for (Map<String, Object> m : listVo) {
				entityMap.put("year", m.get("year"));
				entityMap.put("emp_id", m.get("emp_id"));
				entityMap.put("dhnurse_audit",m.get("dhnurse_audit"));
				entityMap.put("pass_reason", m.get("pass_reason"));
				hrPromotionSummaryN1Mapper.reAuditDhnurseAuditN1(entityMap);
			}

			return "{\"msg\":\"销审成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}
	}


	@Override
	public HrPromotionSummaryN1 queryPromotionSummaryN1ByCode(HrPromotionSummaryN1 hrPromotionSummaryN1) {
		return hrPromotionSummaryN1Mapper.queryPromotionSummaryN1ByCode(hrPromotionSummaryN1);
	}

	@Override
	public List<Map<String, Object>> queryPromotionN1ByPrint(
			Map<String, Object> entityMap) throws DataAccessException {
		 List<Map<String,Object>> list = ChdJson.toListLower(hrPromotionSummaryN1Mapper.queryPromotionN1ByPrint(entityMap));
		 return list;
	}

	@Override
	public String auditPromotionAuditN1(Map<String, Object> mapVo) throws DataAccessException {
		String stationTransef = "";
		List<HrPromotionSummaryN1> listVo = JSONArray.parseArray(String.valueOf(mapVo.get("paramVo")),
				HrPromotionSummaryN1.class);
		for (HrPromotionSummaryN1 hrPromotionSummaryN1 : listVo) {
			if (hrPromotionSummaryN1.getHnurse() == null) {
				stationTransef = "{\"error\":\"审核失败！请先进行护士长审核！\",\"state\":\"false\"}";
				return stationTransef;
			} else if(hrPromotionSummaryN1.getHnurse_audit() == 0){
				stationTransef = "{\"error\":\"审核失败！护士长未审核通过！\",\"state\":\"false\"}";
				return stationTransef;
			}else if (hrPromotionSummaryN1.getDhnurse() == null) {
				stationTransef = "{\"error\":\"审核失败！请先进行科护士长审核！\",\"state\":\"false\"}";
				return stationTransef;
			} else if(hrPromotionSummaryN1.getDhnurse_audit() == 0){
				stationTransef = "{\"error\":\"审核失败！科护士长未审核通过！\",\"state\":\"false\"}";
				return stationTransef;
			}else if (hrPromotionSummaryN1.getPromotion_audit() == null) {
				stationTransef = "{\"error\":\"审核失败！请先检查状态！\",\"state\":\"false\"}";
				return stationTransef;
			} else {
				hrPromotionSummaryN1.setPromotion(Long.parseLong(SessionManager.getUserId()));
				hrPromotionSummaryN1.setPromotion_date(new Date());
				hrPromotionSummaryN1Mapper.auditPromotionAuditN1(hrPromotionSummaryN1);
			}
		}
		stationTransef = "{\"msg\":\"审核成功.\",\"state\":\"true\"}";
		return stationTransef;
	}

               
  
}
