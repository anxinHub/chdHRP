package com.chd.hrp.htcg.serviceImpl.calculation;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.chd.base.SysPage;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.hrp.htcg.dao.calculation.HtcgChargeCostDeptMapper;
import com.chd.hrp.htcg.entity.calculation.HtcgChargeCostDept;
import com.chd.hrp.htcg.service.calculation.HtcgChargeCostDeptService;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Title. 
 * @Description.
 * @Copyright: Copyright (c) 2015-2-14 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @Author: LiuYingDuo
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
@Service("htcgChargeCostDeptService")
public class HtcgChargeCostDeptServiceImpl implements HtcgChargeCostDeptService {

	private static Logger logger = Logger.getLogger(HtcgChargeCostDeptServiceImpl.class);
	
	@Resource(name = "htcgChargeCostDeptMapper")
	private final HtcgChargeCostDeptMapper htcgChargeCostDeptMapper = null;

	@Override
	public String addHtcgChargeCostSchemeRela(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		try {
			
			List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
			if(entityMap.get("detailData") != null && !"[]".equals(String.valueOf(entityMap.get("detailData")))){
				JSONArray detailDataJsonArr = JSONArray.parseArray((String) entityMap.get("detailData"));
				Iterator detailDataJsonArrIt = detailDataJsonArr.iterator();
				while (detailDataJsonArrIt.hasNext()) {
					JSONObject jsonObj = JSONObject.parseObject(detailDataJsonArrIt.next().toString());
					Map<String,Object> detailMap = new HashMap<String,Object>();
					if(null != jsonObj.get("plan_code") && !"".equals(jsonObj.get("plan_code"))){
					detailMap.put("group_id", jsonObj.get("group_id"));
					detailMap.put("hos_id", jsonObj.get("hos_id"));
					detailMap.put("copy_code", jsonObj.get("copy_code"));
					detailMap.put("acc_year", jsonObj.get("acc_year"));
					detailMap.put("scheme_code", jsonObj.get("scheme_code"));
					detailMap.put("period_type_code", jsonObj.get("period_type_code"));
					detailMap.put("period_code", jsonObj.get("period_code"));
					detailMap.put("plan_code", jsonObj.get("plan_code"));
					list.add(detailMap);
					}
				}
				
				if(list.size() > 0 ){
					htcgChargeCostDeptMapper.deleteBatchHtcgChargeCostSchemeRela(list);
					htcgChargeCostDeptMapper.addBatchHtcgChargeCostSchemeRela(list);
				}
			}
			return "{\"msg\":\"保存成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"保存失败\"}");
		}
	}

	@Override
	public String queryHtcgChargeCostSchemeRela(
			Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		   SysPage sysPage = new SysPage();
			sysPage = (SysPage) entityMap.get("sysPage");
			if (sysPage.getTotal() == -1) {
				List<Map<String, Object>> list = htcgChargeCostDeptMapper.queryHtcgChargeCostSchemeRela(entityMap);
				return ChdJson.toJson(list);
			} else {
				RowBounds rowBounds = new RowBounds(sysPage.getPage(),sysPage.getPagesize());
				List<Map<String, Object>> list = htcgChargeCostDeptMapper.queryHtcgChargeCostSchemeRela(entityMap, rowBounds);
				PageInfo page = new PageInfo(list);
				return ChdJson.toJson(list, page.getTotal());
			}
	}

	@Override
	public String queryHtcgChargeCostDept(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		  SysPage sysPage = new SysPage();
			sysPage = (SysPage) entityMap.get("sysPage");
			if (sysPage.getTotal() == -1) {
				List<HtcgChargeCostDept> list = htcgChargeCostDeptMapper.queryHtcgChargeCostDept(entityMap);
				return ChdJson.toJson(list);
			} else {
				RowBounds rowBounds = new RowBounds(sysPage.getPage(),sysPage.getPagesize());
				List<HtcgChargeCostDept> list = htcgChargeCostDeptMapper.queryHtcgChargeCostDept(entityMap, rowBounds);
				PageInfo page = new PageInfo(list);
				return ChdJson.toJson(list, page.getTotal());
			}
	}

	@Override
	public String initHtcgChargeCostDept(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		  try {
				
			  htcgChargeCostDeptMapper.initHtcgChargeCostDept(entityMap);
		  
			return "{\"msg\":\""+entityMap.get("msg")+".\",\"state\":\"true\"}";
			
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\""+entityMap.get("msg")+"失败\"}");
		}
	}

	@Override
	public String deleteBatchHtcgChargeCostSchemeRela(
			List<Map<String, Object>> list) throws DataAccessException {
		// TODO Auto-generated method stub
      try {
			
    	  htcgChargeCostDeptMapper.deleteBatchHtcgChargeCostSchemeRela(list);
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"删除失败\"}");
		}
	}

	@Override
	public String deleteBathcHtcgChargeCostDept(List<Map<String, Object>> list)
			throws DataAccessException {
		// TODO Auto-generated method stub
		  try {
				
	    	  htcgChargeCostDeptMapper.deleteBathcHtcgChargeCostDept(list);
				return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
			} catch (Exception e) {
				// TODO: handle exception
				logger.error(e.getMessage(), e);
				throw new SysException("{\"error\":\"删除失败\"}");
			}
	}
}
