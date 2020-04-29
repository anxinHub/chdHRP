/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.hr.serviceImpl.medicalmanagement;

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
import com.chd.hrp.hr.dao.medicalmanagement.HrDealDetailMapper;
import com.chd.hrp.hr.dao.medicalmanagement.HrDealMapper;
import com.chd.hrp.hr.entity.medicalmanagement.HrDeal;
import com.chd.hrp.hr.entity.medicalmanagement.HrDealDetail;
import com.chd.hrp.hr.service.medicalmanagement.HrDealService;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Description:
 * 
 * @Table:
 * HR_Deal
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 


@Service("hrDealService")
public class HrDealServiceImpl implements HrDealService {

	private static Logger logger = Logger.getLogger(HrDealServiceImpl.class);
	//引入DAO操作
	@Resource(name = "hrDealMapper")
	private final HrDealMapper hrDealMapper = null;
	
	//引入DAO操作
		@Resource(name = "hrDealDetailMapper")
		private final HrDealDetailMapper hrDealDetailMapper = null;
	
	
	
	@Override
	public String addDeal(Map<String, Object> entityMap) throws DataAccessException {
		String msg = null;
		try {
			HrDeal hrDeal = hrDealMapper.queryByCode(entityMap);
			// @SuppressWarnings("unused")
			if (hrDeal == null) {
				int state = hrDealMapper.add(entityMap);
				List<HrDealDetail> alllistVo = JSONArray.parseArray(String.valueOf(entityMap.get("Param")),
						HrDealDetail.class);
				if (alllistVo != null) {
					for (HrDealDetail hrDealDetail : alllistVo) {
						hrDealDetail.setHos_id(Double.parseDouble(SessionManager.getHosId()));
						hrDealDetail.setGroup_id(Double.parseDouble(SessionManager.getGroupId()));
						hrDealDetail.setIn_hos_no(entityMap.get("in_hos_no").toString());
					}
					state = hrDealDetailMapper.addBatch(alllistVo);
				}
				return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";
			} else {
				return "{\"error\":\"请勿重复保存！\",\"state\":\"true\"}";
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}
	}

	@Override
	public HrDeal queryByCode(Map<String, Object> entityMap)
			throws DataAccessException {
		return hrDealMapper.queryByCode(entityMap);
		}
	
	@Override
	public String updateDeal(Map<String, Object> entityMap) throws DataAccessException {
		try {
			@SuppressWarnings("unused")
			int state = hrDealMapper.update(entityMap);
			List<HrDealDetail> alllistVo = JSONArray.parseArray(String.valueOf(entityMap.get("Param")),
					HrDealDetail.class);
			if (alllistVo != null && alllistVo.size() > 0) {
				for (HrDealDetail hrDealDetail : alllistVo) {
					hrDealDetail.setHos_id(Double.parseDouble(SessionManager.getHosId()));
					hrDealDetail.setGroup_id(Double.parseDouble(SessionManager.getGroupId()));
					hrDealDetail.setOcc_date(DateUtil.stringToDate(entityMap.get("occ_date").toString(), "yyyy-MM-dd"));
					hrDealDetail.setPlaint_date(
							DateUtil.stringToDate(entityMap.get("plaint_date").toString(), "yyyy-MM-dd"));
					hrDealDetail.setIn_hos_no(entityMap.get("in_hos_no").toString());
				}
				hrDealDetailMapper.deleteBatchUpdate(alllistVo);
				hrDealDetailMapper.addBatch(alllistVo);
			}
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}
	}
	
	@Override
	public String deleteDeal(List<HrDeal> entityList)
			throws DataAccessException {

		try {
			if(entityList!=null ){
				Map<String,Object> mapVo=new HashMap<String,Object>();
				mapVo.put("group_id", SessionManager.getGroupId())   ;
				mapVo.put("hos_id", SessionManager.getHosId())   ;
				hrDealDetailMapper.deleteBatchDeal(entityList,mapVo);
				hrDealMapper.deleteDeal(entityList,mapVo);
			}
			

			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}
	}
	@Override
	public String queryDeal(Map<String, Object> entityMap)
			throws DataAccessException {

		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		if (sysPage.getTotal() == -1) {

			List<HrDeal> list = (List<HrDeal>) hrDealMapper.query(entityMap);

			return ChdJson.toJson(list);

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(),
					sysPage.getPagesize());

			List<HrDeal> list = (List<HrDeal>) hrDealMapper.query(entityMap, rowBounds);

			@SuppressWarnings("rawtypes")
			PageInfo page = new PageInfo(list);

			return ChdJson.toJson(list, page.getTotal());

		}

	}

	@Override
	public String confirmDeal(HrDeal hrDeal) throws DataAccessException {
		try {
			HrDeal deal = hrDealMapper.queryByCodeCon(hrDeal);
			if(deal == null){
				return "{\"error\":\"提交失败！请先保存！\",\"state\":\"false\"}";
			}
			if(deal.getIs_commit() == 1){
				return "{\"error\":\"提交失败！请勿重复提交！\",\"state\":\"false\"}";
			}
			deal.setIs_commit(1);
			hrDealMapper.confirmDeal(deal);
			
			return "{\"msg\":\"提交成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}
	}
	/**
	 * TODO yangyunfei
	 */
	@Override
	public String confirmDeal(List<HrDeal> list) throws DataAccessException {
		try {
			hrDealMapper.confirmDeal(list.get(0));
			return "{\"msg\":\"提交成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}
	}
	@Override
	public String confirmDealBatch(List<HrDeal> list) throws DataAccessException {
		try {
			hrDealMapper.confirmDealBatch(list);
			return "{\"msg\":\"提交成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}
	}
	
	@Override
	public String reConfirmHrDeal(HrDeal hrDeal) throws DataAccessException {
		try {
			HrDeal deal = hrDealMapper.queryByCodeCon(hrDeal);
			if(deal == null){
				return "{\"error\":\"撤回失败,找不到数据！\",\"state\":\"false\"}";
			}
			if(deal.getIs_commit() == 0){
				return "{\"msg\":\"未提交状态数据，无需撤回.\",\"state\":\"true\"}";
			}
			
			deal.setIs_commit(0);
			hrDealMapper.updateIsCommit(deal);
			return "{\"msg\":\"撤回成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}
	}
	@Override
	public String reConfirmHrDealBatch(List<HrDeal> list) throws DataAccessException {
		try {
			if(list == null || list.size() == 0){
				return "{\"msg\":\"撤回失败，请选择行.\",\"state\":\"false\"}";
			}
			hrDealMapper.confirmDealBatch(list);
			return "{\"msg\":\"撤回成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}
	}

	@Override
	public String queryDealDetail(Map<String, Object> entityMap)
			throws DataAccessException {

		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		if (sysPage.getTotal() == -1) {

			List<HrDealDetail> list = (List<HrDealDetail>) hrDealDetailMapper.query(entityMap);

			return ChdJson.toJson(list);

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(),
					sysPage.getPagesize());

			List<HrDealDetail> list = (List<HrDealDetail>) hrDealDetailMapper.query(entityMap, rowBounds);

			@SuppressWarnings("rawtypes")
			PageInfo page = new PageInfo(list);

			return ChdJson.toJson(list, page.getTotal());

		}

	}

	@Override
	public HrDeal queryByCodeCon(HrDeal hrdeal)
			throws DataAccessException {
		return hrDealMapper.queryByCodeCon(hrdeal);
		}
	
	@Override
	public int queryByIsCommit(Map<String, Object> map, List<HrDeal> list) throws DataAccessException {
		return hrDealMapper.queryByIsCommit(map, list);
	}

	@Override
	public List<Map<String, Object>> queryDealByPrint(Map<String, Object> entityMap)
			throws DataAccessException {
		 List<Map<String,Object>> list = ChdJson.toListLower(hrDealMapper.queryDealByPrint(entityMap));
		 return list;
	}

	@Override
	public String queryResearchInHosNo(Map<String, Object> entityMap)
			throws DataAccessException {
		List<Map<String, Object>> list = hrDealMapper.queryResearchInHosNo(entityMap);
		return JSONArray.toJSONString(list);
	}

	@Override
	public String deleteDealDetail(List<HrDeal> entityList)
			throws DataAccessException {

		try {
			if(entityList!=null ){
				Map<String,Object> mapVo=new HashMap<String,Object>();
				mapVo.put("group_id", SessionManager.getGroupId())   ;
				mapVo.put("hos_id", SessionManager.getHosId())   ;
				hrDealDetailMapper.deleteBatchDeal(entityList,mapVo);
			}

			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}
	}



	
}
