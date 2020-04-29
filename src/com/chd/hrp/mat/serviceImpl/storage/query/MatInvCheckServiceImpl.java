/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.mat.serviceImpl.storage.query;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.chd.base.SessionManager;
import com.chd.base.SysPage;
import com.chd.base.util.ChdJson;
import com.chd.base.util.DateUtil;
import com.chd.hrp.acc.entity.AccYearMonth;
import com.chd.hrp.mat.dao.storage.out.MatOutDetailMapper;
import com.chd.hrp.mat.dao.storage.query.MatInvBarRoutingMapper;
import com.chd.hrp.mat.dao.storage.query.MatOutDeptMapper;
import com.chd.hrp.mat.dao.storage.query.MatInDetailMapper;
import com.chd.hrp.mat.dao.storage.query.MatInSupCountMapper;
import com.chd.hrp.mat.dao.storage.query.MatInSupDetailMapper;
import com.chd.hrp.mat.dao.storage.query.MatStockDetailMapper;
import com.chd.hrp.mat.dao.storage.query.MatStockRoutingMapper;
import com.chd.hrp.mat.dao.storage.query.MatWorkDetailMapper;
import com.chd.hrp.mat.service.storage.query.MatInDetailService;
import com.chd.hrp.mat.service.storage.query.MatInvCheckService;
import com.chd.hrp.sys.dao.base.SysBaseMapper;
import com.github.pagehelper.PageInfo;

/**
 * 材料验收统计
 * @author Administrator
 *
 */
@Service("matInvCheckService")
public class MatInvCheckServiceImpl implements MatInvCheckService {

	private static Logger logger = Logger.getLogger(MatInvCheckServiceImpl.class);
	
	//引入DAO操作
	@Resource(name = "matStockDetailMapper")
	private final MatStockDetailMapper matStockDetailMapper = null;
	RowBounds rowBoundsALL = new RowBounds(0, 20);
	/**
	 * 查询材料验收明细
	 * @param page
	 * @return
	 */
	@Override
	public String queryMatInvCheckDetail(Map<String, Object> entityMap) {
		
		if (entityMap.get("inv_id")!=null) {//将材料名称大写
			entityMap.put("inv_id",entityMap.get("inv_id").toString().toUpperCase());
		}
		if (entityMap.get("in_no")!=null) {//将单号大写
			entityMap.put("in_no",entityMap.get("in_no").toString().toUpperCase());
		}
		if (entityMap.get("sup_name")!=null) {//将供应商大写
			entityMap.put("sup_name",entityMap.get("sup_name").toString().toUpperCase());
		}
		SysPage sysPage = new SysPage();
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			List<Map<String, Object>> list = matStockDetailMapper.queryMatInvCheckDetail(entityMap);
			return ChdJson.toJson(list);
		}else{
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			List<Map<String, Object>> list = matStockDetailMapper.queryMatInvCheckDetail(entityMap, rowBounds);
			PageInfo page = new PageInfo(list);
			return ChdJson.toJson(list, page.getTotal());
		}
		
		
	}
	/**
	 * 查询材料类别
	 * @param mapVo
	 * @return
	 */
	@Override
	public String queryMatType(Map<String, Object> mapVo) {
		return JSON.toJSONString(matStockDetailMapper.queryMatType(mapVo));
	}
	@Override
	public List<Map<String, Object>> queryMatInvCheckDetailPrint(Map<String, Object> entityMap) {
		
		if (entityMap.get("inv_id")!=null) {//将材料名称大写
			entityMap.put("inv_id",entityMap.get("inv_id").toString().toUpperCase());
		}
		if (entityMap.get("in_no")!=null) {//将单号大写
			entityMap.put("in_no",entityMap.get("in_no").toString().toUpperCase());
		}
		if (entityMap.get("sup_name")!=null) {//将供应商大写
			entityMap.put("sup_name",entityMap.get("sup_name").toString().toUpperCase());
		}		
			List<Map<String, Object>> list = matStockDetailMapper.queryMatInvCheckDetail(entityMap);
			
			return list;
	 
	}


}
