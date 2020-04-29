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
import com.chd.hrp.hr.dao.medicalmanagement.HrScoreDetailMapper;
import com.chd.hrp.hr.entity.medicalmanagement.HrScoreDetail;
import com.chd.hrp.hr.service.medicalmanagement.HrScoreDetailService;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Description:
 * 
 * @Table:
 * HR_SCORE_DETAIL 投诉扣分明细
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 


@Service("hrScoreDetailService")
public class HrScoreDetailServiceImpl implements HrScoreDetailService {

	private static Logger logger = Logger.getLogger(HrScoreDetailServiceImpl.class);
	//引入DAO操作
	@Resource(name = "hrScoreDetailMapper")
	private final HrScoreDetailMapper hrScoreDetailMapper = null;
	
	
	
	@Override
	public String addScoreDetail(Map<String, Object> entityMap)
			throws DataAccessException {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			/**
			 * 先删除
			 */
			List<HrScoreDetail> alllistVo = JSONArray.parseArray(String.valueOf(entityMap.get("paramVo")),
					HrScoreDetail.class);
			
			/**
			 * 增加
			 */
			if (alllistVo!=null && alllistVo.size() > 0) {
				
				for (HrScoreDetail hrScoreDetail : alllistVo) {
			
				
					if (hrScoreDetail.getIs_commit()==null) {
						hrScoreDetail.setIs_commit(0);
						

					}
				}}
			hrScoreDetailMapper.deleteScoreDetail(alllistVo,entityMap);
			int state =hrScoreDetailMapper.addBatchScoreDetail(alllistVo,entityMap);
			return "{\"msg\":\"保存成功.\",\"state\":\"true\"}";
		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}
			
		}
	@Override
	public String deleteScoreDetail(List<HrScoreDetail> entityList)
			throws DataAccessException {

		try {
			if (entityList !=null) {
				Map<String,Object> mapVo=new HashMap<String,Object>();
				mapVo.put("group_id", SessionManager.getGroupId())   ;
				mapVo.put("hos_id", SessionManager.getHosId())   ;
				
				
				hrScoreDetailMapper.deleteScoreDetail(entityList,mapVo);
			}

			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}
	}
	@Override
	public String queryScoreDetail(Map<String, Object> entityMap)
			throws DataAccessException {
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<HrScoreDetail> list = (List<HrScoreDetail>)hrScoreDetailMapper.query(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<HrScoreDetail> list = (List<HrScoreDetail>)hrScoreDetailMapper.query(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
		
	}
	@Override
	public HrScoreDetail queryByCode(HrScoreDetail hrScoreDetail)
			throws DataAccessException {
		return hrScoreDetailMapper.queryByCodeScoreDetail(hrScoreDetail);
		}
	@Override
	public String confirmScoreDetail(String paramVo)
			throws DataAccessException {
		try {
	        
			List<HrScoreDetail> listVo = JSONArray.parseArray(paramVo, HrScoreDetail.class);
			
			if(listVo.size() == 0){
				return "{\"msg\":\"提交失败，请选择行！\",\"state\":\"false\"}";
			}
			Map<String,Object> map=new HashMap<String,Object>();
			map.put("group_id", SessionManager.getGroupId());
			map.put("hos_id", SessionManager.getHosId());
			map.put("is_commit", 1);
			
			if(hrScoreDetailMapper.queryScoreDetailIsComit(map,listVo) > 0){
				return "{\"error\":\"只能包含未提交过的数据\",\"state\":\"false\"}";
			}
			
			hrScoreDetailMapper.confirmScoreDetailBatch(map,listVo);
			
			return "{\"msg\":\"提交成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}

}
	@Override
	public String reConfirmScoreDetail(HrScoreDetail hrScoreDetail) throws DataAccessException {
		try {
			hrScoreDetailMapper.reConfirmScoreDetail(hrScoreDetail);
			return "{\"msg\":\"撤回成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}
	}
	@Override
	public String reConfirmScoreDetailBatch(List<HrScoreDetail> list) throws DataAccessException {
		try {
			if(list == null || list.size() == 0){
				return "{\"msg\":\"撤回失败,请选择行！\",\"state\":\"false\"}";
			}
			Map<String,Object> map=new HashMap<String,Object>();
			map.put("group_id", SessionManager.getGroupId());
			map.put("hos_id", SessionManager.getHosId());
			map.put("is_commit", 0);
			hrScoreDetailMapper.confirmScoreDetailBatch(map, list);
			return "{\"msg\":\"撤回成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}
	}
	@Override
	public String queryHrResearch(Map<String, Object> entityMap)
			throws DataAccessException {
		List<Map<String, Object>> list = hrScoreDetailMapper.queryHrResearch(entityMap);
		return ChdJson.toJson(list);
	}
	@Override
	public List<Map<String, Object>> queryScoreDetailByPrint(
			Map<String, Object> entityMap) throws DataAccessException {
		 List<Map<String,Object>> list = ChdJson.toListLower(hrScoreDetailMapper.queryScoreDetailByPrint(entityMap));
		 return list;
	}
    
	
}
