package com.chd.hrp.mat.serviceImpl.affi.query;

import java.util.Date;
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
import com.chd.hrp.acc.entity.AccYearMonth;
import com.chd.hrp.mat.dao.affi.query.MatAffiInDetailMapper;
import com.chd.hrp.mat.dao.affi.query.MatAffiOutDeptMapper;
import com.chd.hrp.mat.dao.affi.query.MatAffiOutDetailMapper;
import com.chd.hrp.mat.dao.affi.query.MatAffiStockDetailMapper;
import com.chd.hrp.mat.dao.affi.query.MatAffiStockRoutingMapper;
import com.chd.hrp.mat.dao.affi.query.MatAffiSupCountMapper;
import com.chd.hrp.mat.dao.affi.query.MatAffiSupDetailMapper;
import com.chd.hrp.mat.service.affi.query.MatAffiStockSearchService;
import com.chd.hrp.mat.serviceImpl.storage.query.MatInDetailServiceImpl;
import com.chd.hrp.sys.dao.base.SysBaseMapper;
import com.github.pagehelper.PageInfo;

/**
 * @Description:
 * 代销-库存查询 
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
@Service("matAffiStockSearchService")
public class MatAffiStockSearchServiceImpl implements MatAffiStockSearchService {
	
	private static Logger logger = Logger.getLogger(MatAffiStockSearchServiceImpl.class);
	
	@Resource(name = "matAffiStockDetailMapper")
	private final MatAffiStockDetailMapper matAffiStockDetailMapper = null;
	
	@Resource(name = "matAffiStockRoutingMapper")
	private final MatAffiStockRoutingMapper matAffiStockRoutingMapper = null;
	
	@Resource(name = "matAffiSupCountMapper")
	private final MatAffiSupCountMapper matAffiSupCountMapper = null;
	
	@Resource(name = "matAffiSupDetailMapper")
	private final MatAffiSupDetailMapper matAffiSupDetailMapper = null;
	
	@Resource(name = "matAffiInDetailMapper")
	private final MatAffiInDetailMapper matAffiInDetailMapper = null;
	
	@Resource(name = "matAffiOutDeptMapper")
	private final MatAffiOutDeptMapper matAffiOutDeptMapper = null;
	
	@Resource(name = "matAffiOutDetailMapper")
	private final MatAffiOutDetailMapper matAffiOutDetailMapper = null;
	
	@Resource(name = "sysBaseMapper")
	private final SysBaseMapper sysBaseMapper = null;
	
	/**
	 * 代销-库存明细查询-页面打印
	 * @param entityMap
	 * @return
	 * @throws DataAccessException 
	 */
	@Override
	public List<Map<String, Object>> queryMatAffiStorageQueryStockDetailPrint(Map<String, Object> entityMap) throws DataAccessException {

		entityMap.put("group_id", SessionManager.getGroupId());
		entityMap.put("hos_id", SessionManager.getHosId());
		entityMap.put("copy_code", SessionManager.getCopyCode());
		entityMap.put("user_id", SessionManager.getUserId());
		entityMap.put("show_history", MyConfig.getSysPara("03001"));
		
		
		entityMap.put("acc_year", entityMap.get("year"));
		entityMap.put("acc_month", entityMap.get("month"));
		
		
		List<AccYearMonth> accYearMonth = sysBaseMapper.queryAccYearMonth(entityMap);
		
		AccYearMonth acc=accYearMonth.get(0);
		
		Date begin_date = DateUtil.stringToDate(acc.getBegin_date(), "yyyy-MM-dd");
				
		Date end_date = DateUtil.stringToDate(acc.getEnd_date(), "yyyy-MM-dd");
		
		entityMap.put("begin_date", DateUtil.dateToString(begin_date,"yyyy-MM-dd"));
		
		entityMap.put("end_date", DateUtil.dateToString(end_date,"yyyy-MM-dd"));
		
		List<Map<String, Object>> list = matAffiStockDetailMapper.queryMatAffiStorageQueryStockDetail(entityMap);
		
		return list;
			
	}
	
	@Override
	public String queryMatAffiStorageQueryStockDetail(Map<String, Object> entityMap) throws DataAccessException {
		
		
		
		entityMap.put("acc_year", entityMap.get("year"));
		entityMap.put("acc_month", entityMap.get("month"));
		
		
		List<AccYearMonth> accYearMonth = sysBaseMapper.queryAccYearMonth(entityMap);
		
		if(accYearMonth.size() == 0){
			return "{\"error\":\"不存在该会计期间\"}";
		}
		
		AccYearMonth acc=accYearMonth.get(0);
		
		Date begin_date = DateUtil.stringToDate(acc.getBegin_date(), "yyyy-MM-dd");
				
		Date end_date = DateUtil.stringToDate(acc.getEnd_date(), "yyyy-MM-dd");
		
		entityMap.put("begin_date", DateUtil.dateToString(begin_date,"yyyy-MM-dd"));
		
		entityMap.put("end_date", DateUtil.dateToString(end_date,"yyyy-MM-dd"));
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<Map<String, Object>> list = matAffiStockDetailMapper.queryMatAffiStorageQueryStockDetail(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<Map<String, Object>> list = matAffiStockDetailMapper.queryMatAffiStorageQueryStockDetail(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
		}
	}

	@Override
	public List<Map<String, Object>> queryMatAffiStockRoutingPrint(Map<String, Object> entityMap)throws DataAccessException {
		
		entityMap.put("group_id", SessionManager.getGroupId());
		entityMap.put("hos_id", SessionManager.getHosId());
		entityMap.put("copy_code", SessionManager.getCopyCode());
		entityMap.put("user_id", SessionManager.getUserId());
		entityMap.put("show_history", MyConfig.getSysPara("03001"));
		
		List<Map<String, Object>> list = matAffiStockRoutingMapper.queryMatAffiStockRouting(entityMap);
		return list;
	}
	@Override
	public String queryMatAffiStockRouting(Map<String, Object> entityMap)throws DataAccessException {
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<Map<String, Object>> list = matAffiStockRoutingMapper.queryMatAffiStockRouting(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<Map<String, Object>> list = matAffiStockRoutingMapper.queryMatAffiStockRouting(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
		}
	}

	@Override
	public String queryMatAffiSupCount(Map<String, Object> entityMap) throws DataAccessException {

		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<Map<String, Object>> list = matAffiSupCountMapper.queryMatAffiSupCount(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<Map<String, Object>> list = matAffiSupCountMapper.queryMatAffiSupCount(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
		}
	}

	@Override
	public String queryMatAffiSupDetail(Map<String, Object> entityMap) throws DataAccessException {

		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<Map<String, Object>> list = matAffiSupDetailMapper.queryMatAffiSupDetail(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<Map<String, Object>> list = matAffiSupDetailMapper.queryMatAffiSupDetail(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
		}
	}

	@Override
	public String queryMatAffiInDetail(Map<String, Object> entityMap) throws DataAccessException {
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<Map<String, Object>> list = matAffiInDetailMapper.queryMatAffiInDetail(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<Map<String, Object>> list = matAffiInDetailMapper.queryMatAffiInDetail(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
		}
	}
	@Override
	public String queryMatAffiInInvCollection(Map<String, Object> entityMap) throws DataAccessException {
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<Map<String, Object>> list = matAffiInDetailMapper.queryMatAffiInInvCollection(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<Map<String, Object>> list = matAffiInDetailMapper.queryMatAffiInInvCollection(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
		}
	}
	/**
	 * 代销材料入库查询打印
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	@Override
	public List<Map<String, Object>> queryMatAffiInDetailPrint(Map<String, Object> entityMap)
			throws DataAccessException {
			entityMap.put("group_id", SessionManager.getGroupId());
			entityMap.put("hos_id", SessionManager.getHosId());
			entityMap.put("copy_code", SessionManager.getCopyCode());
			entityMap.put("user_id", SessionManager.getUserId());
			entityMap.put("show_history", MyConfig.getSysPara("03001"));
			List<Map<String, Object>> list=null;
			if (entityMap.get("show_detail")!=null && "1".equals(String.valueOf(entityMap.get("show_detail")))) {
				list = matAffiInDetailMapper.queryMatAffiInDetail(entityMap);
			}else{
				list=matAffiInDetailMapper.queryMatAffiInInvCollection(entityMap);
			}
		return list;
	}

	@Override
	public String queryMatAffiOutDept(Map<String, Object> entityMap) throws DataAccessException {
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<Map<String, Object>> list = matAffiOutDeptMapper.queryMatAffiOutDept(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<Map<String, Object>> list = matAffiOutDeptMapper.queryMatAffiOutDept(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
		}
	}

	@Override
	public String queryMatAffiOutDetail(Map<String, Object> entityMap) throws DataAccessException {
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<Map<String, Object>> list = matAffiOutDetailMapper.queryMatAffiOutDetail(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<Map<String, Object>> list = matAffiOutDetailMapper.queryMatAffiOutDetail(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
		}
	}
	@Override
	public String queryMatAffiOutInvCollection(Map<String, Object> entityMap) throws DataAccessException {
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<Map<String, Object>> list = matAffiOutDetailMapper.queryMatAffiOutInvCollection(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<Map<String, Object>> list = matAffiOutDetailMapper.queryMatAffiOutInvCollection(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
		}
	}

	@Override
	public String queryMatAffiOutSupMessage(Map<String, Object> entityMap) throws DataAccessException {

		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<Map<String, Object>> list = matAffiOutDetailMapper.queryMatAffiOutSupMessage(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<Map<String, Object>> list = matAffiOutDetailMapper.queryMatAffiOutSupMessage(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
		}
	}
	/**
	 * 植入介入材料查询
	 */
	@Override
	public String queryMatAffiOutImplant(Map<String, Object> entityMap) throws DataAccessException {
		SysPage sysPage = new SysPage();
		sysPage = (SysPage) entityMap.get("sysPage");
		if (sysPage.getTotal()==-1){
			List<Map<String, Object>> list = matAffiOutDetailMapper.queryMatAffiOutImplant(entityMap);
			return ChdJson.toJsonLower(list);
		}else{
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			List<Map<String, Object>> list = matAffiOutDetailMapper.queryMatAffiOutImplant(entityMap, rowBounds);
			PageInfo page = new PageInfo(list);
			return ChdJson.toJsonLower(list, page.getTotal());
		}
	}
	
	/**
	 * 材料库存分布查询(供应商)
	 */
	@Override
	public String queryMatAffiStockRoutingBySup(Map<String, Object> entityMap) throws DataAccessException {
		SysPage sysPage = new SysPage();
		sysPage = (SysPage) entityMap.get("sysPage");
		if (sysPage.getTotal()==-1){
			List<Map<String, Object>> list = matAffiOutDetailMapper.queryMatAffiStockRoutingBySup(entityMap);
			return ChdJson.toJsonLower(list);
		}else{
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			List<Map<String, Object>> list = matAffiOutDetailMapper.queryMatAffiStockRoutingBySup(entityMap, rowBounds);
			PageInfo page = new PageInfo(list);
			return ChdJson.toJsonLower(list, page.getTotal());
		}
	}
	
	/**
	 * 代销入库台账查询
	 */
	@Override
	public String queryMatAffiOutCertDetail(Map<String, Object> entityMap) throws DataAccessException {
		SysPage sysPage = new SysPage();
		sysPage = (SysPage) entityMap.get("sysPage");
		if (sysPage.getTotal()==-1){
			List<Map<String, Object>> list = matAffiOutDetailMapper.queryMatAffiOutCertDetail(entityMap);
			return ChdJson.toJsonLower(list);
		}else{
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			List<Map<String, Object>> list = matAffiOutDetailMapper.queryMatAffiOutCertDetail(entityMap, rowBounds);
			PageInfo page = new PageInfo(list);
			return ChdJson.toJsonLower(list, page.getTotal());
		}
	}
	/**
	 * 打印
	 */
	@Override
	public List<Map<String, Object>> queryMatAffiOutImplantPrint(Map<String, Object> entityMap) throws DataAccessException {
			entityMap.put("group_id", SessionManager.getGroupId());
			entityMap.put("hos_id", SessionManager.getHosId());
			entityMap.put("copy_code", SessionManager.getCopyCode());
			entityMap.put("user_id", SessionManager.getUserId());
			entityMap.put("show_history", MyConfig.getSysPara("03001"));
		List<Map<String, Object>> list = matAffiOutDetailMapper.queryMatApplyCountPrint(entityMap);
		return list;
	}
	
	/**
	 * 入库台账查询打印
	 */
	@Override
	public List<Map<String, Object>> queryMatAffiOutCertDetailPrint(Map<String, Object> entityMap) throws DataAccessException {
			entityMap.put("group_id", SessionManager.getGroupId());
			entityMap.put("hos_id", SessionManager.getHosId());
			entityMap.put("copy_code", SessionManager.getCopyCode());
			entityMap.put("user_id", SessionManager.getUserId());
			entityMap.put("show_history", MyConfig.getSysPara("03001"));
		List<Map<String, Object>> list = matAffiOutDetailMapper.queryMatAffiOutCertDetailPrint(entityMap);
		return list;
	}
	
	/**
	 * 出库情况查询打印
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	@Override
	public List<Map<String, Object>> queryMatAffiOutDetailPrint(Map<String, Object> entityMap)
			throws DataAccessException {
				entityMap.put("group_id", SessionManager.getGroupId());
				entityMap.put("hos_id", SessionManager.getHosId());
				entityMap.put("copy_code", SessionManager.getCopyCode());
				entityMap.put("user_id", SessionManager.getUserId());
				entityMap.put("show_history", MyConfig.getSysPara("03001"));
			
			
			List<Map<String, Object>> list=null;
			if (entityMap.get("show_detail")!=null && "1".equals(String.valueOf(entityMap.get("show_detail")))) {
				list = matAffiOutDetailMapper.queryMatAffiOutDetail(entityMap);
			}else{
				list=matAffiOutDetailMapper.queryMatAffiOutInvCollection(entityMap);
			}
		return list;
			
	}
	

}
