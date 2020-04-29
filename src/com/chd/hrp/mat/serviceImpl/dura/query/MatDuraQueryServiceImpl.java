/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.mat.serviceImpl.dura.query;

import java.util.*;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.MyConfig;
import com.chd.base.SessionManager;
import com.chd.base.SysPage;
import com.chd.base.util.ChdJson;
import com.chd.hrp.acc.entity.AccSubj;
import com.chd.hrp.acc.entity.AccSubjType;
import com.chd.hrp.mat.dao.dura.query.MatDuraQueryAccountStatedMapper;
import com.chd.hrp.mat.dao.dura.query.MatDuraQueryAmortizeMapper;
import com.chd.hrp.mat.dao.dura.query.MatDuraQueryBalanceReportMapper;
import com.chd.hrp.mat.dao.dura.query.MatDuraQueryDeptUseMapper;
import com.chd.hrp.mat.dao.dura.query.MatDuraQueryDistributionMapper;
import com.chd.hrp.mat.dao.dura.query.MatDuraQueryQrCodeMapper;
import com.chd.hrp.mat.dao.dura.query.MatDuraQueryReceptionMapper;
import com.chd.hrp.mat.dao.dura.query.MatDuraQueryStockMapper;
import com.chd.hrp.mat.dao.dura.query.MatDuraQueryStoreStockMapper;
import com.chd.hrp.mat.dao.dura.query.MatDuraQueryTranMapper;
import com.chd.hrp.mat.entity.MatTypeDict;
import com.chd.hrp.mat.service.dura.query.MatDuraQueryService;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Description: 耐用品查询
 * @Table:
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 
@Service("matDuraQueryService")
public class MatDuraQueryServiceImpl implements MatDuraQueryService {

	private static Logger logger = Logger.getLogger(MatDuraQueryServiceImpl.class);
	//引入DAO操作
	@Resource(name = "matDuraQueryTranMapper")
	private final MatDuraQueryTranMapper matDuraQueryTranMapper = null;
	@Resource(name = "matDuraQueryStoreStockMapper")
	private final MatDuraQueryStoreStockMapper matDuraQueryStoreStockMapper = null;
	@Resource(name = "matDuraQueryDeptUseMapper")
	private final MatDuraQueryDeptUseMapper matDuraQueryDeptUseMapper = null;
	@Resource(name = "matDuraQueryAccountStatedMapper")
	private final MatDuraQueryAccountStatedMapper matDuraQueryAccountStatedMapper = null;
	@Resource(name = "matDuraQueryDistributionMapper")
	private final MatDuraQueryDistributionMapper matDuraQueryDistributionMapper = null;
	@Resource(name = "matDuraQueryStockMapper")
	private final MatDuraQueryStockMapper matDuraQueryStockMapper = null;
	@Resource(name = "matDuraQueryBalanceReportMapper")
	private final MatDuraQueryBalanceReportMapper matDuraQueryBalanceReportMapper = null;
	@Resource(name = "matDuraQueryAmortizeMapper")
	private final MatDuraQueryAmortizeMapper matDuraQueryAmortizeMapper = null;
	@Resource(name = "matDuraQueryReceptionMapper")
	private final MatDuraQueryReceptionMapper matDuraQueryReceptionMapper = null;
	@Resource(name = "matDuraQueryQrCodeMapper")
	private final MatDuraQueryQrCodeMapper matDuraQueryQrCodeMapper = null;
	
	/**
	 * @Description 
	 * 耐用品流转查询<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String queryMatDuraQueryTran(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		sysPage = (SysPage) entityMap.get("sysPage");
		if (sysPage.getTotal()==-1){
			List<Map<String, Object>> list = matDuraQueryTranMapper.queryMatDuraQueryTran(entityMap);	
			return ChdJson.toJsonLower(list);
		}else{
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			List<Map<String, Object>> list = matDuraQueryTranMapper.queryMatDuraQueryTran(entityMap, rowBounds);	
			PageInfo page = new PageInfo(list);			
			return ChdJson.toJsonLower(list, page.getTotal());
		}
	}

	/**
	 * @Description 
	 * 耐用品在库库存查询<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String queryMatDuraQueryStoreStock(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		sysPage = (SysPage) entityMap.get("sysPage");
		if (sysPage.getTotal()==-1){
			List<Map<String, Object>> list = matDuraQueryStoreStockMapper.queryMatDuraQueryStoreStock(entityMap);	
			return ChdJson.toJsonLower(list);
		}else{
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			List<Map<String, Object>> list = matDuraQueryStoreStockMapper.queryMatDuraQueryStoreStock(entityMap, rowBounds);	
			PageInfo page = new PageInfo(list);			
			return ChdJson.toJsonLower(list, page.getTotal());
		}
	}

	
	/**
	 * @Description 
	 * 耐用品报废明细表查询<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String queryMatDuraQueryScrapDetail(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		sysPage = (SysPage) entityMap.get("sysPage");
		if (sysPage.getTotal()==-1){
			List<Map<String, Object>> list = matDuraQueryStoreStockMapper.queryMatDuraQueryScrapDetail(entityMap);	
			return ChdJson.toJsonLower(list);
		}else{
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			List<Map<String, Object>> list = matDuraQueryStoreStockMapper.queryMatDuraQueryScrapDetail(entityMap, rowBounds);	
			PageInfo page = new PageInfo(list);			
			return ChdJson.toJsonLower(list, page.getTotal());
		}
	}
	/**
	 * @Description 
	 * 耐用品报废明细表(科室)
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public List<Map<String, Object>> queryMatDuraQueryScrapDetailPrint(Map<String, Object> entityMap)
			throws DataAccessException {
			entityMap.put("group_id", SessionManager.getGroupId());
			entityMap.put("hos_id", SessionManager.getHosId());
			entityMap.put("copy_code", SessionManager.getCopyCode());
			entityMap.put("user_id", SessionManager.getUserId());
			entityMap.put("show_history", MyConfig.getSysPara("03001"));
		List<Map<String, Object>> list =matDuraQueryStoreStockMapper.queryMatDuraQueryScrapDetail(entityMap);
		
		return list;
	}
	
	/**
	 * @Description 
	 * 耐用品报废明细表查询仓库<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String queryMatDuraQueryScrapDetailStore(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		sysPage = (SysPage) entityMap.get("sysPage");
		if (sysPage.getTotal()==-1){
			List<Map<String, Object>> list = matDuraQueryStoreStockMapper.queryMatDuraQueryScrapDetailStore(entityMap);	
			return ChdJson.toJsonLower(list);
		}else{
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			List<Map<String, Object>> list = matDuraQueryStoreStockMapper.queryMatDuraQueryScrapDetailStore(entityMap, rowBounds);	
			PageInfo page = new PageInfo(list);			
			return ChdJson.toJsonLower(list, page.getTotal());
		}
	}

	/**
	 * @Description 
	 * 耐用品科室在用量查询<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String queryMatDuraQueryDeptUse(Map<String,Object> entityMap) throws DataAccessException{

		SysPage sysPage = new SysPage();
		sysPage = (SysPage) entityMap.get("sysPage");
		if (sysPage.getTotal()==-1){
			List<Map<String, Object>> list = matDuraQueryDeptUseMapper.queryMatDuraQueryDeptUse(entityMap);	
			return ChdJson.toJsonLower(list);
		}else{
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			List<Map<String, Object>> list = matDuraQueryDeptUseMapper.queryMatDuraQueryDeptUse(entityMap, rowBounds);	
			PageInfo page = new PageInfo(list);			
			return ChdJson.toJsonLower(list, page.getTotal());
		}
	}
	
	/**
	 * @Description 
	 * 耐用品明细账<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	@Override
	public String queryMatDuraQueryAccountStated(Map<String,Object> entityMap) throws DataAccessException{

		SysPage sysPage = new SysPage();
		sysPage = (SysPage) entityMap.get("sysPage");
		if (sysPage.getTotal()==-1){
			List<Map<String, Object>> list = matDuraQueryAccountStatedMapper.queryMatDuraQueryAccountStated(entityMap);	
			return ChdJson.toJsonLower(list);
		}else{
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			List<Map<String, Object>> list = matDuraQueryAccountStatedMapper.queryMatDuraQueryAccountStated(entityMap, rowBounds);	
			PageInfo page = new PageInfo(list);			
			return ChdJson.toJsonLower(list, page.getTotal());
		}
	}
	
	
	/**
	 * @Description 
	 * 耐用品明细账打印<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	@Override
	public List<Map<String, Object>> queryMatDuraQueryAccountStatedPrint(Map<String, Object> entityMap)
			throws DataAccessException {
			entityMap.put("group_id", SessionManager.getGroupId());
			entityMap.put("hos_id", SessionManager.getHosId());
			entityMap.put("copy_code", SessionManager.getCopyCode());
			entityMap.put("user_id", SessionManager.getUserId());
			entityMap.put("show_history", MyConfig.getSysPara("03001"));
		List<Map<String, Object>> list = matDuraQueryAccountStatedMapper.queryMatDuraQueryAccountStated(entityMap);
		
		return list;
	}
	/**
	
	
	/**
	 * @Description 
	 * 全院耐用品数量分布<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	@Override
	public String queryMatDuraQueryDistribution(Map<String,Object> entityMap) throws DataAccessException{

		SysPage sysPage = new SysPage();
		sysPage = (SysPage) entityMap.get("sysPage");
		if (sysPage.getTotal()==-1){
			List<Map<String, Object>> list = matDuraQueryDistributionMapper.queryMatDuraQueryDistribution(entityMap);	
			return ChdJson.toJsonLower(list);
		}else{
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			List<Map<String, Object>> list = matDuraQueryDistributionMapper.queryMatDuraQueryDistribution(entityMap, rowBounds);	
			PageInfo page = new PageInfo(list);			
			return ChdJson.toJsonLower(list, page.getTotal());
		}
	}
	//全院分布  耐用品选择器 树形加载
	@Override
	public String queryInvBySelector(Map<String, Object> entityMap)
			throws DataAccessException {
			
			StringBuilder jsonResult = new StringBuilder();
			
			jsonResult.append("{Rows:[");
			
			List<Map<String, Object>> subjTypeList = matDuraQueryDistributionMapper.queryInvMatType(entityMap);
			
			if (subjTypeList.size()>0) {
				int rows = 0;
				for(Map<String, Object> matInvType : subjTypeList){
					
					jsonResult.append("{id:'"+matInvType.get("mat_type_id")+"',pId:'0',name:'"+matInvType.get("mat_type_name")+"',open:false,nodes:[");
					entityMap.put("mat_type_id", matInvType.get("mat_type_id"));
					entityMap.put("super_code", "top");
					jsonResult.append(subjRecursion(entityMap));
					rows++;
					if(rows== subjTypeList.size()){
						jsonResult.append("]}");
					}else{
						jsonResult.append("]},");
					}
				}
			}
			jsonResult.append("]}");
		
			return jsonResult.toString();
		}
	
	public String subjRecursion(Map<String, Object> entityMap){
		int row = 0;
		StringBuilder jsonResult = new StringBuilder();
		List<Map<String, Object>> invDictList = matDuraQueryDistributionMapper.queryMatInvByMenu(entityMap);
		for (Map<String, Object> invDict : invDictList) {
			jsonResult.append("{");
				
				jsonResult.append("id:'" + invDict.get("inv_id") + "',");
				
				jsonResult.append("inv_code:'" + invDict.get("inv_code") + "',");
				
				jsonResult.append("name:'"+invDict.get("inv_code") + " " +invDict.get("inv_name")+ "("+ invDict.get("inv_model")+")" +"',");
				
				/*}else{
				
				jsonResult.append("id:'" + invDict.get("inv_code") + "',");
				
				jsonResult.append("inv_id:'" + invDict.get("inv_id") + "',");
				
				jsonResult.append("name:'"+invDict.get("inv_code") + " " +invDict.get("inv_name")+ "("+ invDict.get("inv_model")+")" +"',");
				
				jsonResult.append("nodes:[");
				
				entityMap.put("inv_code", invDict.get("inv_code"));
				
				jsonResult.append("]");
			}*/
			row++;
			if(row == invDictList.size()){
				jsonResult.append("}");
			}else{
				jsonResult.append("},");
			}
		}
		return jsonResult.toString();
	}
	
	/**
	 * @Description 
	 * 耐用品库存查询<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	@Override
	public String queryMatDuraQueryStock(Map<String,Object> entityMap) throws DataAccessException{

		SysPage sysPage = new SysPage();
		sysPage = (SysPage) entityMap.get("sysPage");
		if (sysPage.getTotal()==-1){
			List<Map<String, Object>> list = matDuraQueryStockMapper.queryMatDuraQueryStock(entityMap);	
			return ChdJson.toJsonLower(list);
		}else{
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			List<Map<String, Object>> list = matDuraQueryStockMapper.queryMatDuraQueryStock(entityMap, rowBounds);	
			PageInfo page = new PageInfo(list);			
			return ChdJson.toJsonLower(list, page.getTotal());
		}
	}
	
	@Override
	public List<Map<String, Object>> queryMatDuraQueryStockPrint(Map<String, Object> entityMap)
			throws DataAccessException {
			entityMap.put("group_id", SessionManager.getGroupId());
			entityMap.put("hos_id", SessionManager.getHosId());
			entityMap.put("copy_code", SessionManager.getCopyCode());
			entityMap.put("user_id", SessionManager.getUserId());
			entityMap.put("show_history", MyConfig.getSysPara("03001"));
		List<Map<String, Object>> list =  matDuraQueryStockMapper.queryMatDuraQueryStock(entityMap);	
		
		return list;
	}
	
	/**
	 * @Description 
	 * 耐用品收发存报表<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	@Override
	public String queryMatDuraQueryBalanceReport(Map<String,Object> entityMap) throws DataAccessException{

		SysPage sysPage = new SysPage();
		sysPage = (SysPage) entityMap.get("sysPage");
		if (sysPage.getTotal()==-1){
			List<Map<String, Object>> list = matDuraQueryBalanceReportMapper.queryMatDuraQueryBalanceReport(entityMap);	
			return ChdJson.toJsonLower(list);
		}else{
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			List<Map<String, Object>> list = matDuraQueryBalanceReportMapper.queryMatDuraQueryBalanceReport(entityMap, rowBounds);	
			PageInfo page = new PageInfo(list);			
			return ChdJson.toJsonLower(list, page.getTotal());
		}
	}
	

	@Override
	public List<Map<String, Object>> queryMatDuraQueryBalanceReportPrint(Map<String, Object> entityMap)
			throws DataAccessException {
			entityMap.put("group_id", SessionManager.getGroupId());
			entityMap.put("hos_id", SessionManager.getHosId());
			entityMap.put("copy_code", SessionManager.getCopyCode());
			entityMap.put("user_id", SessionManager.getUserId());
			entityMap.put("show_history", MyConfig.getSysPara("03001"));
		List<Map<String, Object>> list = matDuraQueryBalanceReportMapper.queryMatDuraQueryBalanceReport(entityMap);	
		
		return list;
	}
	
	/**
	 * @Description 
	 * 耐用品五五摊销报表<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	@Override
	public String queryMatDuraQueryAmortize(Map<String,Object> entityMap) throws DataAccessException{

		SysPage sysPage = new SysPage();
		sysPage = (SysPage) entityMap.get("sysPage");
		if (sysPage.getTotal()==-1){
			List<Map<String, Object>> list = matDuraQueryAmortizeMapper.queryMatDuraQueryAmortize(entityMap);	
			return ChdJson.toJsonLower(list);
		}else{
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			List<Map<String, Object>> list = matDuraQueryAmortizeMapper.queryMatDuraQueryAmortize(entityMap, rowBounds);	
			PageInfo page = new PageInfo(list);			
			return ChdJson.toJsonLower(list, page.getTotal());
		}
	}
	
	/**
	 * @Description 
	 * 耐用品领用查询<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	@Override
	public String queryMatDuraQueryReception(Map<String,Object> entityMap) throws DataAccessException{

		SysPage sysPage = new SysPage();
		sysPage = (SysPage) entityMap.get("sysPage");
		if (sysPage.getTotal()==-1){
			List<Map<String, Object>> list = matDuraQueryReceptionMapper.queryMatDuraQueryReception(entityMap);	
			return ChdJson.toJsonLower(list);
		}else{
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			List<Map<String, Object>> list = matDuraQueryReceptionMapper.queryMatDuraQueryReception(entityMap, rowBounds);	
			PageInfo page = new PageInfo(list);			
			return ChdJson.toJsonLower(list, page.getTotal());
		}
	}

	@Override
	public List<Map<String, Object>> queryMatDuraQueryReceptionPrint(Map<String, Object> entityMap)
			throws DataAccessException {
			entityMap.put("group_id", SessionManager.getGroupId());
			entityMap.put("hos_id", SessionManager.getHosId());
			entityMap.put("copy_code", SessionManager.getCopyCode());
			entityMap.put("user_id", SessionManager.getUserId());
			entityMap.put("show_history", MyConfig.getSysPara("03001"));
		List<Map<String, Object>> list =  matDuraQueryReceptionMapper.queryMatDuraQueryReception(entityMap);	
		
		return list;
	}
	
	
	
	@Override
	public List<Map<String, Object>> queryMatDuraQueryStoreStockPrint(Map<String, Object> entityMap)
			throws DataAccessException {
			entityMap.put("group_id", SessionManager.getGroupId());
			entityMap.put("hos_id", SessionManager.getHosId());
			entityMap.put("copy_code", SessionManager.getCopyCode());
			entityMap.put("user_id", SessionManager.getUserId());
			entityMap.put("show_history", MyConfig.getSysPara("03001"));
		List<Map<String, Object>> list = matDuraQueryStoreStockMapper.queryMatDuraQueryStoreStock(entityMap);	
		
		return list;
	}
	
	@Override
	public List<Map<String, Object>> queryMatDuraQueryDistributionPrint(Map<String, Object> entityMap)
			throws DataAccessException {
			entityMap.put("group_id", SessionManager.getGroupId());
			entityMap.put("hos_id", SessionManager.getHosId());
			entityMap.put("copy_code", SessionManager.getCopyCode());
			entityMap.put("user_id", SessionManager.getUserId());
			entityMap.put("show_history", MyConfig.getSysPara("03001"));
		List<Map<String, Object>> list = matDuraQueryDistributionMapper.queryMatDuraQueryDistribution(entityMap);
		
		return list;
	}

	@Override
	public String queryMatDuraQueryQrCode(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		SysPage sysPage = new SysPage();
		sysPage = (SysPage) entityMap.get("sysPage");
		if (sysPage.getTotal()==-1){
			List<Map<String, Object>> list = matDuraQueryQrCodeMapper.queryMatDuraQueryQrCode(entityMap);	
			return ChdJson.toJsonLower(list);
		}else{
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			List<Map<String, Object>> list = matDuraQueryQrCodeMapper.queryMatDuraQueryQrCode(entityMap, rowBounds);	
			PageInfo page = new PageInfo(list);			
			return ChdJson.toJsonLower(list, page.getTotal());
		}
	}
}
