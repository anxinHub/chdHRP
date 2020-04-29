package com.chd.hrp.hr.serviceImpl.nursing;

import java.text.SimpleDateFormat;
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
import com.chd.hrp.hr.dao.nursing.HrPromotionSummaryN2Mapper;
import com.chd.hrp.hr.dao.nursing.HrPromotionSummaryN2Mapper;
import com.chd.hrp.hr.entity.nursing.HrEducationStudent;
import com.chd.hrp.hr.entity.nursing.HrPromotionSummaryN2;
import com.chd.hrp.hr.entity.nursing.HrPromotionSummaryN2;
import com.chd.hrp.hr.entity.nursing.HrPromotionSummaryN2;
import com.chd.hrp.hr.entity.nursing.HrPromotionSummaryN2;
import com.chd.hrp.hr.service.nursing.HrPromotionSummaryN2Service;
import com.github.pagehelper.PageInfo;

/**
 * 护理晋级汇总审批表(N2)
 * 
 * @author Administrator
 *
 */
@Service("hrPromotionSummaryN2Service")
public class HrPromotionSummaryN2ServiceImpl implements HrPromotionSummaryN2Service {
	private static Logger logger = Logger.getLogger(HrPromotionSummaryN2ServiceImpl.class);

	@Resource(name = "hrPromotionSummaryN2Mapper")
	private final HrPromotionSummaryN2Mapper hrPromotionSummaryN2Mapper = null;

	// 默认显示20条数据
	RowBounds rowBoundsALL = new RowBounds(0, 20);

	/**
	 * 护理晋级汇总审批表(N2)查询
	 */
	@SuppressWarnings("unchecked")
	@Override
	public String queryPromotionSummaryN2(Map<String, Object> entityMap) throws DataAccessException {

		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		if (sysPage.getTotal() == -1) {

			List<HrPromotionSummaryN2> list = (List<HrPromotionSummaryN2>) hrPromotionSummaryN2Mapper.query(entityMap);

			return ChdJson.toJson(list);

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

			List<HrPromotionSummaryN2> list = (List<HrPromotionSummaryN2>) hrPromotionSummaryN2Mapper.query(entityMap,
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
	public String auditHnurseAuditN2(List<HrPromotionSummaryN2> listVo)
			throws DataAccessException {

		try {
			String msg="";
		
			
             hrPromotionSummaryN2Mapper.auditHnurseAuditN2(listVo);
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
	public String reAuditHnurseAuditN2(Map<String, Object> entityMap) throws DataAccessException {

		try {
			List<Map> listVo = JSONArray.parseArray(entityMap.get("paramVo").toString(),
					Map.class);
             for (Map<String, Object> m : listVo) {
				entityMap.put("year", m.get("year"));
				entityMap.put("emp_id", m.get("emp_id"));
				entityMap.put("hnurse_audit",m.get("hnurse_audit"));
				hrPromotionSummaryN2Mapper.reAuditHnurseAuditN2(entityMap);
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
	public String auditDhnurseAuditN2(List<HrPromotionSummaryN2> listVo) throws DataAccessException {

		try {
				hrPromotionSummaryN2Mapper.auditDhnurseAuditN2(listVo);

		
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
	public String reAuditDhnurseAuditN2(Map<String, Object> entityMap) throws DataAccessException {

		try {
			List<Map> listVo = JSONArray.parseArray(entityMap.get("paramVo").toString(),
					Map.class);
             for (Map<String, Object> m : listVo) {
				entityMap.put("year", m.get("year"));
				entityMap.put("emp_id", m.get("emp_id"));
				entityMap.put("dhnurse_audit",m.get("dhnurse_audit"));
				entityMap.put("pass_reason", m.get("pass_reason"));
				hrPromotionSummaryN2Mapper.reAuditDhnurseAuditN2(entityMap);
			}

			return "{\"msg\":\"销审成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}
	}


	@Override
	public HrPromotionSummaryN2 queryPromotionSummaryN2ByCode(HrPromotionSummaryN2 hrPromotionSummaryN2) {
		return hrPromotionSummaryN2Mapper.queryPromotionSummaryN2ByCode(hrPromotionSummaryN2);
	}

	@Override
	public List<Map<String, Object>> queryPromotionN2ByPrint(
			Map<String, Object> entityMap) throws DataAccessException {
		 List<Map<String,Object>> list = ChdJson.toListLower(hrPromotionSummaryN2Mapper.queryPromotionN2ByPrint(entityMap));
		 return list;
	}

	@Override
	public String auditPromotionAuditN2(Map<String, Object> mapVo) throws DataAccessException {
		String stationTransef = "";
		List<HrPromotionSummaryN2> listVo = JSONArray.parseArray(String.valueOf(mapVo.get("paramVo")),
				HrPromotionSummaryN2.class);
		for (HrPromotionSummaryN2 hrPromotionSummaryN2 : listVo) {
			if (hrPromotionSummaryN2.getHnurse() == null) {
				stationTransef = "{\"error\":\"审核失败！请先进行护士长审核！\",\"state\":\"false\"}";
			} else if(hrPromotionSummaryN2.getHnurse_audit() == 0){
				stationTransef = "{\"error\":\"审核失败！护士长未审核通过！\",\"state\":\"false\"}";
			}else if (hrPromotionSummaryN2.getDhnurse() == null) {
				stationTransef = "{\"error\":\"审核失败！请先进行科护士长审核！\",\"state\":\"false\"}";
			} else if(hrPromotionSummaryN2.getDhnurse_audit() == 0){
				stationTransef = "{\"error\":\"审核失败！科护士长未审核通过！\",\"state\":\"false\"}";
			}else if (hrPromotionSummaryN2.getPromotion_audit() == null) {
				stationTransef = "{\"error\":\"审核失败！请先检查状态！\",\"state\":\"false\"}";
			} else {
				hrPromotionSummaryN2.setPromotion(Long.parseLong(SessionManager.getUserId()));
				hrPromotionSummaryN2.setPromotion_date(new Date());
				hrPromotionSummaryN2Mapper.auditPromotionAuditN2(hrPromotionSummaryN2);
				stationTransef = "{\"msg\":\"审核成功.\",\"state\":\"true\"}";
			}
		}
		return stationTransef;
	}

}
