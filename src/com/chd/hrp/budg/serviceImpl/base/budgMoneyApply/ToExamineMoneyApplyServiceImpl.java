package com.chd.hrp.budg.serviceImpl.base.budgMoneyApply;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.chd.base.SysPage;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.hrp.budg.dao.base.budgMoneyApply.MoneyApplyMapper;
import com.chd.hrp.budg.dao.base.budgMoneyApply.ToExamineMoneyApplyMapper;
import com.chd.hrp.budg.service.base.budgMoneyApply.MoneyApplyService;
import com.chd.hrp.budg.service.base.budgMoneyApply.ToExamineMoneyApplyService;
import com.github.pagehelper.PageInfo;

@Service("toExamineMoneyApplyService")
public class ToExamineMoneyApplyServiceImpl implements ToExamineMoneyApplyService {
	
	private static Logger logger = Logger.getLogger(ToExamineMoneyApplyServiceImpl.class);
	
	@Resource(name = "toExamineMoneyApplyMapper")
	private final ToExamineMoneyApplyMapper toExamineMoneyApplyMapper = null;
	
	@Resource(name = "moneyApplyMapper")
	private final MoneyApplyMapper moneyApplyMapper = null;
	
	/**
	 * 主页面查询
	 */
	@Override
	public String queryToExamineMoneyApply(Map<String, Object> entityMap) {
		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		if (sysPage.getTotal() == -1) {

			 List<Map<String,Object>> list = (List<Map<String, Object>>) toExamineMoneyApplyMapper.query(entityMap);

			return ChdJson.toJson(list);

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

			List<Map<String,Object>> list = (List<Map<String, Object>>) toExamineMoneyApplyMapper.query(entityMap, rowBounds);

			PageInfo page = new PageInfo(list);

			return ChdJson.toJson(list, page.getTotal());

		}
	}




	
	/**
	 * 跳转update页面所需要展示的数据
	 */
	@Override
	public Map<String, Object> ToExamineMoneyApplyDetail(Map<String, Object> mapVo) {
		
		List<Map<String,Object>> listMain = toExamineMoneyApplyMapper.queryUpdatePageMainData(mapVo);
		
		return listMain.get(0);
	}
	
	
	/**
	 * update页面查询详细
	 */
	@Override
	public String queryToExamineMoneyApplyDet(Map<String, Object> mapVo) {
		
		
		List<Map<String,Object>> list = toExamineMoneyApplyMapper.queryUpdatePageDetiData(mapVo);
		return ChdJson.toJson(list);
	}

	


	
	/**
	 * 主页面提交、明细页面提交   state_t 01新建、02已提交、03已审核
	 */
	@Override
	public String updateToExamineMoneyApplyState(HashMap<String, Object> mapVo) {
		try {
			for (Map<String, Object> map : (List<Map<String, Object>>) mapVo.get("list")) {
				List<Map<String, Object>> ded = (List<Map<String, Object>>) moneyApplyMapper.query(map);
				String state_t = ded.get(0).get("state_t").toString();
				if (!"02".equals(state_t)) {
					return "{\"warn\":\"只能审核状态为已提交的数据！\",\"state\":\"false\"}";
				}
				
			}
			
			toExamineMoneyApplyMapper.updateToExamineMoneyApplyState(mapVo);
			
			return "{\"msg\":\"用款申请已经审核完毕.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage(),e);
		}
	}

	
	/**
	 * 主页面撤回、明细页面撤回   state_t 01新建、02已提交、03已审核
	 * @return
	 */
	@Override
	public String updateToExamineMoneyApplyStateRevoke(List<Map<String, Object>> list) {
		try {
			
			for (Map<String, Object> map : list) {
				List<Map<String, Object>> ded = (List<Map<String, Object>>) moneyApplyMapper.query(map);
				String state_t = ded.get(0).get("state_t").toString();
				if (!"03".equals(state_t)) {
					return "{\"warn\":\"只能消审状态为已审核的数据！\",\"state\":\"false\"}";
				}
				
			}
			
			toExamineMoneyApplyMapper.updateToExamineMoneyApplyStateRevoke(list);
			
			return "{\"msg\":\"用款申请已经消审完毕.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage(),e);
		}
	}
	
	

}
