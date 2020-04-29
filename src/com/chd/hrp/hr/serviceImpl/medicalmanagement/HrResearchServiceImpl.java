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
import org.springframework.util.CollectionUtils;

import com.alibaba.fastjson.JSONArray;
import com.chd.base.SessionManager;
import com.chd.base.SysPage;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.hrp.hr.dao.medicalmanagement.HrResearchDetailMapper;
import com.chd.hrp.hr.dao.medicalmanagement.HrResearchMapper;
import com.chd.hrp.hr.entity.medicalmanagement.HrResearch;
import com.chd.hrp.hr.entity.medicalmanagement.HrResearchDetail;
import com.chd.hrp.hr.service.medicalmanagement.HrResearchService;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Description:
 * 
 * @Table:
 * HR_RESEARCH
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 


@Service("hrResearchService")
public class HrResearchServiceImpl implements HrResearchService {

	private static Logger logger = Logger.getLogger(HrResearchServiceImpl.class);
	//引入DAO操作
	@Resource(name = "hrResearchMapper")
	private final HrResearchMapper hrResearchMapper = null;
	
	//引入DAO操作
		@Resource(name = "hrResearchDetailMapper")
		private final HrResearchDetailMapper hrResearchDetailMapper = null;
	
	
	
	@Override
	public String addResearch(Map<String, Object> entityMap)
			throws DataAccessException {
		String msg=null;
		try {
			HrResearch hrResearch	=hrResearchMapper.queryByCodeResearch(entityMap);
		//	@SuppressWarnings("unused")
			if(hrResearch==null){
				int state = hrResearchMapper.add(entityMap);


				List<HrResearchDetail> alllistVo = JSONArray.parseArray(String.valueOf(entityMap.get("Param")),
						HrResearchDetail.class);
				if (alllistVo!=null) {
					for (HrResearchDetail hrResearchDetail : alllistVo) {
						hrResearchDetail.setHos_id(Double.parseDouble(SessionManager.getHosId()));
						hrResearchDetail.setGroup_id(Double.parseDouble(SessionManager.getGroupId()));
						hrResearchDetail.setIn_hos_no(entityMap.get("in_hos_no").toString());
					}
					 state =	hrResearchDetailMapper.addBatch(alllistVo);
				}
			     
				 
			
				 return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";
			}else{
				return "{\"error\":\"请勿重复保存！\",\"state\":\"true\"}";
			}
			

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}

	}

	@Override
	public HrResearch queryByCodeResearch(Map<String, Object> entityMap)
			throws DataAccessException {
		return hrResearchMapper.queryByCodeResearch(entityMap);
		}
	@Override
	public String updateResearch(Map<String, Object> entityMap)
			throws DataAccessException {

		try {

			@SuppressWarnings("unused")
			
			int state = hrResearchMapper.update(entityMap);

			List<HrResearchDetail> alllistVo = JSONArray.parseArray(String.valueOf(entityMap.get("Param")),
					HrResearchDetail.class);
			if (alllistVo!=null && alllistVo.size()>0) {
				for (HrResearchDetail hrResearchDetail : alllistVo) {
					hrResearchDetail.setHos_id(Double.parseDouble(SessionManager.getHosId()));
					hrResearchDetail.setGroup_id(Double.parseDouble(SessionManager.getGroupId()));
					hrResearchDetail.setIn_hos_no(entityMap.get("in_hos_no").toString());
				}
				   hrResearchDetailMapper.deleteBatchUpdate(alllistVo);
				   hrResearchDetailMapper.addBatch(alllistVo);
			    }
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}

	}
	@Override
	public String deleteResearch(List<HrResearch> entityList)
			throws DataAccessException {

		try {
			if(entityList!=null){
				Map<String,Object> mapVo=new HashMap<String,Object>();
				mapVo.put("group_id", SessionManager.getGroupId())   ;
				mapVo.put("hos_id", SessionManager.getHosId())   ;
				
				
			hrResearchDetailMapper.deleteBatchResearch(entityList,mapVo);
			hrResearchMapper.deleteResearch(entityList,mapVo);
			}
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}
	}
	@Override
	public String queryResearch(Map<String, Object> entityMap)
			throws DataAccessException {

		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		if (sysPage.getTotal() == -1) {

			List<HrResearch> list = (List<HrResearch>) hrResearchMapper.query(entityMap);

			return ChdJson.toJson(list);

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(),
					sysPage.getPagesize());

			List<HrResearch> list = (List<HrResearch>) hrResearchMapper.query(entityMap, rowBounds);

			@SuppressWarnings("rawtypes")
			PageInfo page = new PageInfo(list);

			return ChdJson.toJson(list, page.getTotal());

		}

	}
	@Override
	public HrResearch queryByCode(HrResearch hrNursingPromotion)
			throws DataAccessException {
		return hrResearchMapper.queryByCode(hrNursingPromotion);
		}
	
	@Override
	public String confirmHrTechRec(List<HrResearch> list) throws DataAccessException {
		try {
			if(CollectionUtils.isEmpty(list)){
				return "{\"error\":\"提交失败！请选择行\",\"state\":\"false\"}";
			}
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("group_id", SessionManager.getGroupId());
			map.put("hos_id", SessionManager.getHosId());
			map.put("is_commit", 1);
			if(hrResearchMapper.queryByIsCommit(map, list) > 0){
				return "{\"error\":\"提交失败！请勿重复提交！\",\"state\":\"false\"}";
			}
			
			for (HrResearch hr : list) {
				hr.setGroup_id(Double.parseDouble(SessionManager.getGroupId()));
				hr.setHos_id(Double.parseDouble(SessionManager.getHosId()));
				hr.setIs_commit(1);
			}
			hrResearchMapper.updateIsCommitBatch(list);
			return "{\"msg\":\"提交成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}
	}
	@Override
	public String reConfirmHrHrTechRec(List<HrResearch> list) throws DataAccessException {
		try {
			if(CollectionUtils.isEmpty(list)){
				return "{\"error\":\"撤回失败！请选择行\",\"state\":\"false\"}";
			}
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("group_id", SessionManager.getGroupId());
			map.put("hos_id", SessionManager.getHosId());
			map.put("is_commit", 0);
			if(hrResearchMapper.queryByIsCommit(map, list) > 0){
				return "{\"error\":\"撤回失败！只能包含已提交状态的数据！\",\"state\":\"false\"}";
			}
			
			for (HrResearch hr : list) {
				hr.setGroup_id(Double.parseDouble(SessionManager.getGroupId()));
				hr.setHos_id(Double.parseDouble(SessionManager.getHosId()));
				hr.setIs_commit(0);
			}
			hrResearchMapper.updateIsCommitBatch(list);
			return "{\"msg\":\"撤回成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}
	}
	@Override
	public String auditHrTechRec(HrResearch hrNursingPromotion)
			throws DataAccessException {
		try {
	        
			hrResearchMapper.auditHrTechRec(hrNursingPromotion);
			
			return "{\"msg\":\"提交成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}

}
	@Override
	public String unauditHrHrTechRec(HrResearch hrNursingPromotion)
			throws DataAccessException {
		try {
	        
			hrResearchMapper.unauditHrHrTechRec(hrNursingPromotion);
			
			return "{\"msg\":\"销审成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}

}
	@Override
	public HrResearch queryByCodeAdd(Map<String, Object> entityMap)
			throws DataAccessException {
		return hrResearchMapper.queryByCodeAdd(entityMap);
		}
	
	@Override
	public String confirmHrResearchAdd(HrResearch research) throws DataAccessException {
		try {
			HrResearch hr = hrResearchMapper.queryByCode(research);
			if(hr == null){
				return "{\"error\":\"提交失败！请先保存！\",\"state\":\"false\"}";
			}
			if(hr.getIs_commit() == 0){
				hr.setIs_commit(1);
				hrResearchMapper.updateIsCommit(hr);
			}else{
				return "{\"error\":\"提交失败！请勿重复提交！\",\"state\":\"false\"}";
			}
			
			return "{\"msg\":\"提交成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}
	}
	
	@Override
	public String reConfirmHResearchAdd(HrResearch research) throws DataAccessException {
		try {
			HrResearch hr = hrResearchMapper.queryByCode(research);
			if(hr == null){
				return "{\"error\":\"撤回失败,找不到数据！\",\"state\":\"false\"}";
			}
			if(hr.getIs_commit() == 1){
				hr.setIs_commit(0);
				hrResearchMapper.updateIsCommit(hr);
			}else{
				return "{\"error\":\"撤回失败,当前为未提交状态\",\"state\":\"false\"}";
			}
			
			return "{\"msg\":\"撤回成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}
	}

	@Override
	public String queryinHosNo(Map<String, Object> entityMap)
			throws DataAccessException {
		List<Map<String, Object>> list = hrResearchMapper.queryinHosNo(entityMap);
		return JSONArray.toJSONString(list);
	}
	@Override
	public String queryinHosNoDetail(Map<String, Object> entityMap)
			throws DataAccessException {
		Map<String, Object> list = hrResearchMapper.queryinHosNoDetail(entityMap);
		return JSONArray.toJSONString(list);
	}

	@Override
	public String queryResearchDetail(Map<String, Object> entityMap)
			throws DataAccessException {

		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		if (sysPage.getTotal() == -1) {

			List<HrResearchDetail> list = (List<HrResearchDetail>) hrResearchDetailMapper.query(entityMap);

			return ChdJson.toJson(list);

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(),
					sysPage.getPagesize());

			List<HrResearchDetail> list = (List<HrResearchDetail>) hrResearchDetailMapper.query(entityMap, rowBounds);

			@SuppressWarnings("rawtypes")
			PageInfo page = new PageInfo(list);

			return ChdJson.toJson(list, page.getTotal());

		}

	}

	@Override
	public String queryByCodeResearchD(Map<String, Object> entityMap)
			throws DataAccessException {
		return JSONArray.toJSONString(hrResearchMapper.queryByCodeResearchD(entityMap));
	}

	@Override
	public List<Map<String, Object>> queryResearchByPrint(
			Map<String, Object> entityMap) throws DataAccessException {
		 List<Map<String,Object>> list = ChdJson.toListLower(hrResearchMapper.queryResearchByPrint(entityMap));
		 return list;
	}

	@Override
	public String deleteResearchDetail(List<HrResearch> entityList)
			throws DataAccessException {

		try {
			if(entityList!=null){
				Map<String,Object> mapVo=new HashMap<String,Object>();
				mapVo.put("group_id", SessionManager.getGroupId())   ;
				mapVo.put("hos_id", SessionManager.getHosId())   ;
				
				
			hrResearchDetailMapper.deleteBatchResearch(entityList,mapVo);
			}
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}
	}



	
}
