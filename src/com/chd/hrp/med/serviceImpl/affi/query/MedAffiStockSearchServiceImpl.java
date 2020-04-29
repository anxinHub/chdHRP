package com.chd.hrp.med.serviceImpl.affi.query;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.SysPage;
import com.chd.base.util.ChdJson;
import com.chd.base.util.DateUtil;
import com.chd.hrp.acc.entity.AccYearMonth;
import com.chd.hrp.med.dao.affi.query.MedAffiInDetailMapper;
import com.chd.hrp.med.dao.affi.query.MedAffiOutDeptMapper;
import com.chd.hrp.med.dao.affi.query.MedAffiOutDetailMapper;
import com.chd.hrp.med.dao.affi.query.MedAffiStockDetailMapper;
import com.chd.hrp.med.dao.affi.query.MedAffiStockRoutingMapper;
import com.chd.hrp.med.dao.affi.query.MedAffiSupCountMapper;
import com.chd.hrp.med.dao.affi.query.MedAffiSupDetailMapper;
import com.chd.hrp.med.service.affi.query.MedAffiStockSearchService;
import com.chd.hrp.med.serviceImpl.storage.query.MedInDetailServiceImpl;
import com.chd.hrp.sys.dao.base.SysBaseMapper;
import com.github.pagehelper.PageInfo;

/**
 * @Description:
 * 代销-库存查询
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
@Service("medAffiStockSearchService")
public class MedAffiStockSearchServiceImpl implements MedAffiStockSearchService {
	
	private static Logger logger = Logger.getLogger(MedAffiStockSearchServiceImpl.class);
	
	@Resource(name = "medAffiStockDetailMapper")
	private final MedAffiStockDetailMapper medAffiStockDetailMapper = null;
	
	@Resource(name = "medAffiStockRoutingMapper")
	private final MedAffiStockRoutingMapper medAffiStockRoutingMapper = null;
	
	@Resource(name = "medAffiSupCountMapper")
	private final MedAffiSupCountMapper medAffiSupCountMapper = null;
	
	@Resource(name = "medAffiSupDetailMapper")
	private final MedAffiSupDetailMapper medAffiSupDetailMapper = null;
	
	@Resource(name = "medAffiInDetailMapper")
	private final MedAffiInDetailMapper medAffiInDetailMapper = null;
	
	@Resource(name = "medAffiOutDeptMapper")
	private final MedAffiOutDeptMapper medAffiOutDeptMapper = null;
	
	@Resource(name = "medAffiOutDetailMapper")
	private final MedAffiOutDetailMapper medAffiOutDetailMapper = null;
	
	@Resource(name = "sysBaseMapper")
	private final SysBaseMapper sysBaseMapper = null;
	
	@Override
	public String queryMedAffiStorageQueryStockDetail(Map<String, Object> entityMap) throws DataAccessException {
		
		entityMap.put("acc_year", entityMap.get("year"));
		entityMap.put("acc_month", entityMap.get("month"));
		
		
		List<AccYearMonth> accYearMonth = sysBaseMapper.queryAccYearMonth(entityMap);
		
		AccYearMonth acc=accYearMonth.get(0);
		
		Date begin_date = DateUtil.stringToDate(acc.getBegin_date(), "yyyy-MM-dd");
				
		Date end_date = DateUtil.stringToDate(acc.getEnd_date(), "yyyy-MM-dd");
		
		entityMap.put("begin_date", DateUtil.dateToString(begin_date,"yyyy-MM-dd"));
		
		entityMap.put("end_date", DateUtil.dateToString(end_date,"yyyy-MM-dd"));
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<Map<String, Object>> list = medAffiStockDetailMapper.queryMedAffiStorageQueryStockDetail(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<Map<String, Object>> list = medAffiStockDetailMapper.queryMedAffiStorageQueryStockDetail(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
		}
	}

	@Override
	public String queryMedAffiStockRouting(Map<String, Object> entityMap)throws DataAccessException {
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<Map<String, Object>> list = medAffiStockRoutingMapper.queryMedAffiStockRouting(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<Map<String, Object>> list = medAffiStockRoutingMapper.queryMedAffiStockRouting(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
		}
	}

	@Override
	public String queryMedAffiSupCount(Map<String, Object> entityMap) throws DataAccessException {

		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<Map<String, Object>> list = medAffiSupCountMapper.queryMedAffiSupCount(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<Map<String, Object>> list = medAffiSupCountMapper.queryMedAffiSupCount(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
		}
	}

	@Override
	public String queryMedAffiSupDetail(Map<String, Object> entityMap) throws DataAccessException {

		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<Map<String, Object>> list = medAffiSupDetailMapper.queryMedAffiSupDetail(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<Map<String, Object>> list = medAffiSupDetailMapper.queryMedAffiSupDetail(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
		}
	}

	@Override
	public String queryMedAffiInDetail(Map<String, Object> entityMap) throws DataAccessException {
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<Map<String, Object>> list = medAffiInDetailMapper.queryMedAffiInDetail(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<Map<String, Object>> list = medAffiInDetailMapper.queryMedAffiInDetail(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
		}
	}

	@Override
	public String queryMedAffiOutDept(Map<String, Object> entityMap) throws DataAccessException {
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<Map<String, Object>> list = medAffiOutDeptMapper.queryMedAffiOutDept(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<Map<String, Object>> list = medAffiOutDeptMapper.queryMedAffiOutDept(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
		}
	}

	@Override
	public String queryMedAffiOutDetail(Map<String, Object> entityMap) throws DataAccessException {
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<Map<String, Object>> list = medAffiOutDetailMapper.queryMedAffiOutDetail(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<Map<String, Object>> list = medAffiOutDetailMapper.queryMedAffiOutDetail(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
		}
	}

	@Override
	public String queryMedAffiOutSupMessage(Map<String, Object> entityMap) throws DataAccessException {

		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<Map<String, Object>> list = medAffiOutDetailMapper.queryMedAffiOutSupMessage(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<Map<String, Object>> list = medAffiOutDetailMapper.queryMedAffiOutSupMessage(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
		}
	}
	/**
	 * 植入介入药品查询
	 */
	@Override
	public String queryMedAffiOutImplant(Map<String, Object> entityMap) throws DataAccessException {
		SysPage sysPage = new SysPage();
		sysPage = (SysPage) entityMap.get("sysPage");
		if (sysPage.getTotal()==-1){
			List<Map<String, Object>> list = medAffiOutDetailMapper.queryMedAffiOutImplant(entityMap);
			return ChdJson.toJsonLower(list);
		}else{
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			List<Map<String, Object>> list = medAffiOutDetailMapper.queryMedAffiOutImplant(entityMap, rowBounds);
			PageInfo page = new PageInfo(list);
			return ChdJson.toJsonLower(list, page.getTotal());
		}
	}
}
