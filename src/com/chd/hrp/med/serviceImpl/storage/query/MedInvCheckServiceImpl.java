/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.med.serviceImpl.storage.query;

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
import com.chd.hrp.med.dao.storage.out.MedOutDetailMapper;
import com.chd.hrp.med.dao.storage.query.MedInvBarRoutingMapper;
import com.chd.hrp.med.dao.storage.query.MedOutDeptMapper;
import com.chd.hrp.med.dao.storage.query.MedInDetailMapper;
import com.chd.hrp.med.dao.storage.query.MedInSupCountMapper;
import com.chd.hrp.med.dao.storage.query.MedInSupDetailMapper;
import com.chd.hrp.med.dao.storage.query.MedStockDetailMapper;
import com.chd.hrp.med.dao.storage.query.MedStockRoutingMapper;
import com.chd.hrp.med.dao.storage.query.MedWorkDetailMapper;
import com.chd.hrp.med.service.storage.query.MedInDetailService;
import com.chd.hrp.med.service.storage.query.MedInvCheckService;
import com.chd.hrp.sys.dao.base.SysBaseMapper;
import com.github.pagehelper.PageInfo;

/**
 * 药品验收统计
 * @author Administrator
 *
 */
@Service("medInvCheckService")
public class MedInvCheckServiceImpl implements MedInvCheckService {

	private static Logger logger = Logger.getLogger(MedInvCheckServiceImpl.class);
	
	//引入DAO操作
	@Resource(name = "medStockDetailMapper")
	private final MedStockDetailMapper medStockDetailMapper = null;
	RowBounds rowBoundsALL = new RowBounds(0, 20);
	/**
	 * 查询药品验收明细
	 * @param page
	 * @return
	 */
	@Override
	public String queryMedInvCheckDetail(Map<String, Object> entityMap) {
		
		if (entityMap.get("inv_id")!=null) {//将药品名称大写
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
			List<Map<String, Object>> list = medStockDetailMapper.queryMedInvCheckDetail(entityMap);
			return ChdJson.toJson(list);
		}else{
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			List<Map<String, Object>> list = medStockDetailMapper.queryMedInvCheckDetail(entityMap, rowBounds);
			PageInfo page = new PageInfo(list);
			return ChdJson.toJson(list, page.getTotal());
		}
		
		
	}
	/**
	 * 查询药品类别
	 * @param mapVo
	 * @return
	 */
	@Override
	public String queryMedType(Map<String, Object> mapVo) {
		return JSON.toJSONString(medStockDetailMapper.queryMedType(mapVo));
	}


}
