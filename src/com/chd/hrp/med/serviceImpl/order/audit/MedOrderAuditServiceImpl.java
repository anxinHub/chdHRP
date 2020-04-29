package com.chd.hrp.med.serviceImpl.order.audit;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.SysPage;
import com.chd.base.util.ChdJson;
import com.chd.hrp.acc.service.vouch.SuperPrintService;
import com.chd.hrp.med.dao.order.MedOrderDetailMapper;
import com.chd.hrp.med.dao.order.MedOrderMainMapper;
import com.chd.hrp.med.dao.order.audit.MedOrderAuditMapper;
import com.chd.hrp.med.service.base.MedCommonService;
import com.chd.hrp.med.service.order.audit.MedOrderAuditService;
import com.github.pagehelper.PageInfo;

@Service("medOrderAuditService")
public class MedOrderAuditServiceImpl implements MedOrderAuditService {
	private static Logger logger = Logger.getLogger(MedOrderAuditServiceImpl.class);
	
	//引入dao
	@Resource(name = "medOrderMainMapper")
	private final MedOrderMainMapper medOrderMainMapper = null;
	
	@Resource(name = "medOrderDetailMapper")
	private final MedOrderDetailMapper medOrderDetailMapper = null;
	
	
	@Resource(name = "medOrderAuditMapper")
	private final MedOrderAuditMapper medOrderAuditMapper = null;
	
	@Resource(name = "medCommonService")
	private final MedCommonService medCommonService = null;
	
	
	@Override
	public String add(Map<String, Object> entityMap) throws DataAccessException {
			try {
				return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";
			}
			catch (Exception e) {
				logger.error(e.getMessage(), e);
				return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addMedOrderMain\"}";
			}
	}
	
	@Override
	public String addBatch(List<Map<String, Object>> entityMap) throws DataAccessException {
		try {
			//暂无该业务
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";
		}catch (Exception e) {
			logger.error(e.getMessage(), e);
			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addBatchMedOrderMain\"}";
		}
	}
	
	
	@Override
	public String update(Map<String, Object> entityMap) throws DataAccessException {
		try {			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";
		}catch (Exception e) {
			logger.error(e.getMessage(), e);
			return "{\"error\":\"更新失败 数据库异常 请联系管理员! 方法 updateMedOrderMain\"}";
		}
	}
	
	
	@Override
	public String updateBatch(List<Map<String, Object>> entityMap) throws DataAccessException {
		try {
			//暂无该业务
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";
		}catch (Exception e) {
			logger.error(e.getMessage(), e);
			return "{\"error\":\"更新失败 数据库异常 请联系管理员! 方法 updateBatchMedOrderMain\"}";
		}	
	}

	@Override
	public String delete(Map<String, Object> entityMap) throws DataAccessException {
		return null;
	}
	
	@Override
	public String deleteBatch(List<Map<String, Object>> entityList) throws DataAccessException {
		try {
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		}catch (Exception e) {
			logger.error(e.getMessage(), e);
			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 deleteBatchMedOrderInit\"}";
		}
	}
	
	@Override
	public String addOrUpdate(Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public <T> T queryByUniqueness(Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public List<?> queryExists(Map<String, Object> entityMap) throws DataAccessException {
		
		return null;
	}
	
	@Override
	public String query(Map<String, Object> entityMap) throws DataAccessException {
		return null;
	}
	
	/**
	 * @Description 
	 * 主表数据
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public <T> T queryByCode(Map<String,Object> entityMap)throws DataAccessException{
		return medOrderMainMapper.queryByCode(entityMap);
	}
	
	/**
	 * @Description 
	 * 明细表数据
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String queryDetail(Map<String, Object> entityMap) throws DataAccessException {
		List<?> list = medOrderDetailMapper.query(entityMap);
		return ChdJson.toJson(list);
	}
	
	/**
	 * @Description 
	 * 订单审核 -- 查询
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String queryOrderAudit(Map<String, Object> entityMap) throws DataAccessException {
		SysPage sysPage = new SysPage();
		sysPage = (SysPage) entityMap.get("sysPage");
		if (sysPage.getTotal()==-1){
			List<?> list = medOrderAuditMapper.query(entityMap);
			return ChdJson.toJson(list);
		}else{
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			List<?> list = medOrderAuditMapper.query(entityMap, rowBounds);
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
		}
	}
	
	/**
	 * @Description 
	 * 订单审核 -- 审核
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@SuppressWarnings("unused")
    @Override
	public String auditOrderMain(List<Map<String, Object>> entityList) throws DataAccessException {
		
		List<Map<String, Object>> newentityList=new ArrayList<Map<String,Object>>();
		
		StringBuilder sb=new StringBuilder();
		StringBuilder sup=new StringBuilder();
		
		for (Map<String, Object> map : entityList) {
			
			Map<String, Object> order=	medOrderMainMapper.queryByCode(map);
			
			if(order.get("state").toString().equals("1")){
				if(null!=order.get("sup_id")){
					newentityList.add(map);	
				}else{
					sup.append(order.get("order_code")).append(" ");
				}
				newentityList.add(map);	
			}else{
				
				if(order.get("is_notice").toString().equals("1") && order.get("state").toString().equals("0")){
					
					sb.append(order.get("order_code")).append(" ");
					
				}
			}
		
        }
		
		try {	
			if(newentityList.size()>0){
				medOrderAuditMapper.auditOrderMain(newentityList);
			}
			
			if(null!=sb && !"".equals(sb.toString().trim())){
				return "{\"msg\":\""+sb.toString()+"审核成功.\",\"state\":\"true\"}";
			}else if(null!=sup && !"".equals(sup.toString().trim())){
				return "{\"msg\":\""+sup.toString()+"单据供应商为空不能审核.\",\"state\":\"true\"}";
			}
			else{
				return "{\"msg\":\"操作成功.\",\"state\":\"true\"}";
			}
		
		}catch (Exception e) {
			logger.error(e.getMessage(), e);
			return "{\"error\":\"审核失败 数据库异常 请联系管理员! 方法 auditOrderMain\"}";
		}	
	}
	/**
	 * @Description 
	 * 订单审核 -- 取消审核
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@SuppressWarnings("unused")
    @Override
	public String unAuditOrderMain(List<Map<String, Object>> entityList) throws DataAccessException {
		List<Map<String, Object>> newentityList=new ArrayList<Map<String,Object>>();
		
		StringBuilder sb=new StringBuilder();
		
		for (Map<String, Object> map : entityList) {
			
			Map<String, Object> order=	medOrderMainMapper.queryByCode(map);
			
			if(order.get("state").toString().equals("2")){
				newentityList.add(map);
			}else{
				
				if(order.get("is_notice").toString().equals("1") && order.get("state").toString().equals("0")){
					
					sb.append(order.get("order_code")).append(" ");
					
				}
			}
		
        }
		
		try {	
			if(newentityList.size()>0){
				medOrderAuditMapper.unAuditOrderMain(newentityList);
			}
			
			if(null!=sb && !"".equals(sb.toString().trim())){
				return "{\"msg\":\""+sb.toString()+"销审成功.\",\"state\":\"true\"}";
			}else{
				return "{\"msg\":\"操作成功.\",\"state\":\"true\"}";
			}
		}catch (Exception e) {
			logger.error(e.getMessage(), e);
			return "{\"error\":\"销审失败 数据库异常 请联系管理员! 方法 auditOrderMain\"}";
		}	
	}
	
	/**
	 * @Description 
	 * 订单审核 -- 发送
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@SuppressWarnings("unused")
    @Override
	public String sendOutOrderMain(List<Map<String, Object>> entityList) throws DataAccessException {
		List<Map<String, Object>> newentityList=new ArrayList<Map<String,Object>>();
		
		StringBuilder sb=new StringBuilder();
		
		for (Map<String, Object> map : entityList) {
			
			Map<String, Object> order=	medOrderMainMapper.queryByCode(map);
			
			if(order.get("state").toString().equals("2")){
				newentityList.add(map);
			}else{
				
				if(order.get("is_notice").toString().equals("1") && order.get("state").toString().equals("0")){
					
					sb.append(order.get("order_code")).append(" ");
					
				}
			}
		
        }
		
		try {	
			if(newentityList.size()>0){
				medOrderAuditMapper.sendOutOrderMain(newentityList);
			}
			
			if(null!=sb && !"".equals(sb.toString().trim())){
				return "{\"msg\":\""+sb.toString()+"单据不是审核状态不能发送.\",\"state\":\"true\"}";
			}else{
				return "{\"msg\":\"操作成功.\",\"state\":\"true\"}";
			}
		}catch (Exception e) {
			logger.error(e.getMessage(), e);
			return "{\"error\":\"发送失败 数据库异常 请联系管理员! 方法 auditOrderMain\"}";
		}	
	}
	@Resource(name = "superPrintService")
	private final SuperPrintService superPrintService = null;
	@Override
	public String queryMedOrderAuditByPrintTemlate(Map<String, Object> entityMap) throws DataAccessException {
		
		//查询入库主表
		Map<String,Object> map=medOrderAuditMapper.queryMedOrderAuditPrintTemlateByMain(entityMap);
		
		//查询入库明细表
		List<Map<String,Object>> list=medOrderAuditMapper.queryMedOrderAuditPrintTemlateByDetail(entityMap);
		
		return superPrintService.getMapListByPrintTemplateJson(entityMap,map,list);
	}
	
}
