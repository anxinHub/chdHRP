/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.mat.serviceImpl.matpayquery;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.MyConfig;
import com.chd.base.SessionManager;
import com.chd.base.SysPage;
import com.chd.base.util.ChdJson;
import com.chd.base.util.DateUtil;
import com.chd.base.util.JsonListMapUtil;
import com.chd.hrp.mat.service.matpayquery.MatPayQueryService;
import com.chd.hrp.mat.dao.matpayquery.MatPayQueryMapper;
import com.github.pagehelper.PageInfo;

/**
 * @Description:
 * @Table:
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
@Service("matPayQueryService")
public class MatPayQueryServiceImpl implements MatPayQueryService {

	private static Logger logger = Logger.getLogger(MatPayQueryServiceImpl.class);
	//引入DAO操作
	@Resource(name = "matPayQueryMapper")
	private final MatPayQueryMapper MatPayQueryMapper = null;

	/**
	 * 入库发票查询
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	@Override
	public String queryMatInBillReport(Map<String, Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		sysPage = (SysPage) entityMap.get("sysPage");
		entityMap.put("user_id", SessionManager.getUserId());
		entityMap.put("show_history", MyConfig.getSysPara("03001"));
		
		//转换日期格式
		if(entityMap.get("begin_in_date") != null && !"".equals(entityMap.get("begin_in_date"))){
			entityMap.put("begin_in_date", DateUtil.stringToDate(entityMap.get("begin_in_date").toString(), "yyyy-MM-dd"));
		}
		if(entityMap.get("end_in_date") != null && !"".equals(entityMap.get("end_in_date"))){
			entityMap.put("end_in_date", DateUtil.stringToDate(entityMap.get("end_in_date").toString(), "yyyy-MM-dd"));
		}
		if(entityMap.get("begin_bill_date") != null && !"".equals(entityMap.get("begin_bill_date"))){
			entityMap.put("begin_bill_date", DateUtil.stringToDate(entityMap.get("begin_bill_date").toString(), "yyyy-MM-dd"));
		}
		if(entityMap.get("end_bill_date") != null && !"".equals(entityMap.get("end_bill_date"))){
			entityMap.put("end_bill_date", DateUtil.stringToDate(entityMap.get("end_bill_date").toString(), "yyyy-MM-dd"));
		}
		
		if (sysPage.getTotal()==-1){
			List<Map<String, Object>> list = MatPayQueryMapper.queryMatInBillReport(entityMap);
			
			return ChdJson.toJsonLower(list);
		}else{
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			List<Map<String, Object>> list = MatPayQueryMapper.queryMatInBillReport(entityMap, rowBounds);
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJsonLower(list, page.getTotal());
		}
	}
	
	/**
	 * 入库发票打印查询
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	@Override
	public List<Map<String, Object>> printMatInBillReport(Map<String, Object> entityMap) throws DataAccessException {

		entityMap.put("user_id", SessionManager.getUserId());
		entityMap.put("show_history", MyConfig.getSysPara("03001"));
		//转换日期格式
		if(entityMap.get("begin_in_date") != null && !"".equals(entityMap.get("begin_in_date"))){
			entityMap.put("begin_in_date", DateUtil.stringToDate(entityMap.get("begin_in_date").toString(), "yyyy-MM-dd"));
		}
		if(entityMap.get("end_in_date") != null && !"".equals(entityMap.get("end_in_date"))){
			entityMap.put("end_in_date", DateUtil.stringToDate(entityMap.get("end_in_date").toString(), "yyyy-MM-dd"));
		}
		if(entityMap.get("begin_bill_date") != null && !"".equals(entityMap.get("begin_bill_date"))){
			entityMap.put("begin_bill_date", entityMap.get("begin_bill_date").toString());
		}
		if(entityMap.get("end_bill_date") != null && !"".equals(entityMap.get("end_bill_date"))){
			entityMap.put("end_bill_date", entityMap.get("end_bill_date"));
		}
		
		List<Map<String, Object>> list = MatPayQueryMapper.queryMatInBillReport(entityMap);
			
		return JsonListMapUtil.toListMapLower(list);
	}
}
