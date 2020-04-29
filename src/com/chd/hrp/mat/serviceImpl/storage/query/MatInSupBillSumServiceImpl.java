/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.mat.serviceImpl.storage.query;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;

import org.apache.commons.lang.ObjectUtils.Null;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import com.alibaba.fastjson.JSON;
import com.chd.base.MyConfig;
import com.chd.base.SessionManager;
import com.chd.base.SysPage;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.base.util.DateUtil;
import com.chd.hrp.acc.entity.AccYearMonth;
import com.chd.hrp.mat.dao.base.MatCommonMapper;
import com.chd.hrp.mat.dao.matprepay.MatPrePayMainMapper;
import com.chd.hrp.mat.dao.storage.out.MatOutDetailMapper;
import com.chd.hrp.mat.dao.storage.query.MatInSupBillSumMapper;
import com.chd.hrp.mat.dao.storage.query.MatInvBarRoutingMapper;
import com.chd.hrp.mat.dao.storage.query.MatOutDeptMapper;
import com.chd.hrp.mat.dao.storage.query.MatInDetailMapper;
import com.chd.hrp.mat.dao.storage.query.MatInSupCountMapper;
import com.chd.hrp.mat.dao.storage.query.MatInSupDetailMapper;
import com.chd.hrp.mat.dao.storage.query.MatStockDetailMapper;
import com.chd.hrp.mat.dao.storage.query.MatStockRoutingMapper;
import com.chd.hrp.mat.dao.storage.query.MatWorkDetailMapper;
import com.chd.hrp.mat.service.storage.query.MatInDetailService;
import com.chd.hrp.mat.service.storage.query.MatInSupBillSumService;
import com.chd.hrp.sys.dao.base.SysBaseMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Description:
 * 入库明细查询
 * @Table:
 * 
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
@Service("matInSupBillSumService")
public class MatInSupBillSumServiceImpl implements MatInSupBillSumService {

	private static Logger logger = Logger.getLogger(MatInSupBillSumServiceImpl.class);
	
	//引入DAO操作
	@Resource(name = "matInSupBillSumMapper")
	private final MatInSupBillSumMapper matInSupBillSumMapper = null;

	
	
	
	/**
	 * 供应商入库汇总查询
	 */
	@Override
	public String queryMatInSupBillSum(Map<String, Object> entityMap)  throws DataAccessException {
		SysPage sysPage = new SysPage();
		sysPage = (SysPage) entityMap.get("sysPage");
		if (sysPage.getTotal()==-1){
			List<Map<String, Object>> list = matInSupBillSumMapper.queryMatInSupBillSum(entityMap);
			return ChdJson.toJsonLower(list);
		}else{
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			List<Map<String, Object>> list = matInSupBillSumMapper.queryMatInSupBillSum(entityMap, rowBounds);
			PageInfo page = new PageInfo(list);
			return ChdJson.toJsonLower(list, page.getTotal());
		}
	}

	/**
	 * 供应商入库汇总查询（打印）
	 */
	@Override
	public List<Map<String, Object>> queryMatInSupBillSumPrint(Map<String, Object> entityMap) throws DataAccessException {

		entityMap.put("group_id", SessionManager.getGroupId());
		entityMap.put("hos_id", SessionManager.getHosId());
		entityMap.put("copy_code", SessionManager.getCopyCode());
		entityMap.put("user_id", SessionManager.getUserId());
		entityMap.put("show_history", MyConfig.getSysPara("03001"));
		List<Map<String, Object>> list = matInSupBillSumMapper.queryMatInSupBillSum(entityMap);
		return list;
	}
	
	
}
