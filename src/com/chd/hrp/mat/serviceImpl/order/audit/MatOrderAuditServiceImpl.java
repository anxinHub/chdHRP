package com.chd.hrp.mat.serviceImpl.order.audit;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.apache.xmlbeans.impl.tool.Diff;
import org.nutz.dao.DaoException;
import org.springframework.context.annotation.Bean;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.chd.base.MyConfig;
import com.chd.base.SessionManager;
import com.chd.base.SysPage;
import com.chd.base.exception.SysException;
import com.chd.base.jdbc.ConfigInit;
import com.chd.base.util.ChdJson;
import com.chd.base.util.DateUtil;
import com.chd.hrp.acc.service.vouch.SuperPrintService;
import com.chd.hrp.mat.dao.initial.MatInitAffiInMapper;
import com.chd.hrp.mat.dao.order.MatOrderDetailMapper;
import com.chd.hrp.mat.dao.order.MatOrderMainMapper;
import com.chd.hrp.mat.dao.order.audit.MatOrderAuditMapper;
import com.chd.hrp.mat.service.base.MatCommonService;
import com.chd.hrp.mat.service.order.audit.MatOrderAuditService;
import com.chd.hrp.mat.service.sms.MatSupSmsService;
import com.chd.webservice.client.hrp.impl.MatWebService;
import com.github.pagehelper.PageInfo;

@Service("matOrderAuditService")
public class MatOrderAuditServiceImpl implements MatOrderAuditService {
	private static Logger logger = Logger.getLogger(MatOrderAuditServiceImpl.class);
	
	//引入dao 
	@Resource(name = "matOrderMainMapper")
	private final MatOrderMainMapper matOrderMainMapper = null;
	
	@Resource(name = "matOrderDetailMapper")
	private final MatOrderDetailMapper matOrderDetailMapper = null;
	
	
	@Resource(name = "matOrderAuditMapper")
	private final MatOrderAuditMapper matOrderAuditMapper = null;
	
	@Resource(name = "matCommonService")
	private final MatCommonService matCommonService = null;
	
	//引入Service服务
	@Resource(name = "matSupSmsService")
	private final MatSupSmsService matSupSmsService = null;
	
	@Bean
	public MatWebService matWebService(){
		return new MatWebService();
	}
	
	@Override
	public String add(Map<String, Object> entityMap) throws DataAccessException {
			try {
				return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";
			}
			catch (Exception e) {
				logger.error(e.getMessage(), e);
				return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addMatOrderMain\"}";
			}
	}
	
	@Override
	public String addBatch(List<Map<String, Object>> entityMap) throws DataAccessException {
		try {
			//暂无该业务
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";
		}catch (Exception e) {
			logger.error(e.getMessage(), e);
			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addBatchMatOrderMain\"}";
		}
	}
	
	
	@Override
	public String update(Map<String, Object> entityMap) throws DataAccessException {
		try {			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";
		}catch (Exception e) {
			logger.error(e.getMessage(), e);
			return "{\"error\":\"更新失败 数据库异常 请联系管理员! 方法 updateMatOrderMain\"}";
		}
	}
	
	
	@Override
	public String updateBatch(List<Map<String, Object>> entityMap) throws DataAccessException {
		try {
			//暂无该业务
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";
		}catch (Exception e) {
			logger.error(e.getMessage(), e);
			return "{\"error\":\"更新失败 数据库异常 请联系管理员! 方法 updateBatchMatOrderMain\"}";
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
			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 deleteBatchMatOrderInit\"}";
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
		return matOrderMainMapper.queryByCode(entityMap);
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
		List<?> list = matOrderDetailMapper.query(entityMap);
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
			List<?> list = matOrderAuditMapper.query(entityMap);
			return ChdJson.toJson(list);
		}else{
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			List<?> list = matOrderAuditMapper.query(entityMap, rowBounds);
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
		
		Map<String,Object> mapM = new HashMap<String,Object>();
		mapM.put("group_id", SessionManager.getGroupId());
		mapM.put("hos_id", SessionManager.getHosId());
		mapM.put("copy_code", SessionManager.getCopyCode());
		
		//计算自动到货日期---------------begin------------
		mapM.put("para_code", "04007");
		int flag = 0;
		flag = Integer.parseInt(matCommonService.getMatAccPara(mapM));
		String arrive_date = "";
		if(flag == 1){
			mapM.put("para_code", "04008");
			//获取系统参数040008的值
			int num = Integer.parseInt(matCommonService.getMatAccPara(mapM));
			
			SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd");
			Calendar cal=Calendar.getInstance();
			Date date=cal.getTime();
			//获取当前系统日期
			cal.add(Calendar.DATE, num);
			date=cal.getTime();
			arrive_date = df.format(date);
			
		}
		
		//计算自动到货日期---------------end------------
		
		for (Map<String, Object> map : entityList) {
			Map<String, Object> order=	matOrderMainMapper.queryByCode(map);
			if(order.get("state").toString().equals("1")){
				if(null!=order.get("sup_id")){
					if (validateJSON(String.valueOf(arrive_date))) {//计划到货日期
						map.put("arrive_date", DateUtil.stringToDate(arrive_date, "yyyy-MM-dd"));//计划到货日期
					}
					map.put("checker", SessionManager.getUserId());
					newentityList.add(map);	
				}else{
					sup.append(order.get("order_code")).append(" ");
				}
				//newentityList.add(map);	
			}else{
				if(order.get("is_notice").toString().equals("1") && order.get("state").toString().equals("0")){
					sb.append(order.get("order_code")).append(" ");
				}
			}
        }
		
		try {	
			if(newentityList.size()>0){
				matOrderAuditMapper.auditOrderMain(newentityList);
				//更新订单主表
				matOrderMainMapper.updateBatch(newentityList);
				
			}
			
			if(null!=sb && !"".equals(sb.toString().trim())){
				return "{\"msg\":\""+sb.toString()+"审核成功.\",\"state\":\"true\"}";
			}else if(null!=sup && !"".equals(sup.toString().trim())){
				return "{\"error\":\""+sup.toString()+"单据供应商为空不能审核.\"}";
			}
			else{
				return "{\"msg\":\"操作成功.\",\"state\":\"true\"}";
			} 
		
		}catch (Exception e) {
			logger.error(e.getMessage(), e);
			
			throw new SysException(e.getMessage(), e);
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
			
			Map<String, Object> order=	matOrderMainMapper.queryByCode(map);
			
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
				matOrderAuditMapper.unAuditOrderMain(newentityList);
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
	public String sendOutOrderMain(List<Map<String, Object>> entityList,Map<String, Object> mapVo) throws DataAccessException {
		List<Map<String, Object>> newentityList=new ArrayList<Map<String,Object>>();
		
		StringBuilder sb=new StringBuilder();
		
		for (Map<String, Object> map : entityList) {
			
			Map<String, Object> order=	matOrderMainMapper.queryByCode(map);
			
			//已审核，未通知
			if(order.get("state").toString().equals("2") && (order.get("is_notice").toString().equals("0") || order.get("is_notice").toString().equals("2"))){
				if(order.get("is_notice").toString().equals("2")){
					map.put("is_notice", "5");
				}else{
					map.put("is_notice", "1");
				}
				
				newentityList.add(map);
			}else{
				sb.append(order.get("order_code")).append(" ");
			}
		
        }
		
		try {	
			String inv_no="";
			String supSmsMsg="";
			boolean isOpen=false;
			if(newentityList.size()>0){
				
				//HRP云平台（供应商订单推送）
				String url=ConfigInit.getConfigProperties("qzj_url");
				if(url!=null && !url.equals("") && mapVo.get("is_hrp").toString().equals("true")){
					boolean isHttp=false;
					 String reJson=matWebService().pushSupOrder(newentityList, url);
					 if(reJson!=null && !reJson.equals("")){
							JSONObject json=JSONObject.parseObject(reJson);
							if(json.getString("state")!=null && json.getString("state").equals("false")){
								return "{\"error\":\""+json.getString("msg")+"\"}";
							}else if(json.getString("state")!=null && json.getString("state").equals("true")){
								isHttp=true;
							}
							
							if(json.getString("is_open")!=null && json.getString("is_open").equals("true")){
								inv_no=json.getString("msg");
								isOpen=true;
							}
					 }
					 if(!isHttp){
						 return "{\"error\":\"前置机通信异常\"}";    
					 }
				}
				
				//更新订单状态is_notice=1
				matOrderAuditMapper.sendOutOrderMain(newentityList);   
				
				
				
				//订单发送成功才通知供应商
				Object send_para=MyConfig.getSysPara("04036");
				send_para=send_para==null?"0":send_para;
				int is_send = Integer.valueOf(send_para.toString());
				
				if(is_send==1 && mapVo.get("is_sms").toString().equals("true")){
					//短信通知供应商
					supSmsMsg=matSupSmsService.addBatch(newentityList); //正确为"",错误为供应商名称序列用空格分割(供应商1 供应商2)
				}
			} 
			
			if(null!=sb && !"".equals(sb.toString().trim())){
				return "{\"error\":\""+sb.toString()+"单据不是审核或未通知状态不能发送.\",\"state\":\"false\"}";
			}else{
				if(isOpen){
					return "{\"inv_no\":\""+inv_no+"\",\"state\":\"true\",\"is_open\":"+isOpen+"}";
				}else{
					if (!"".equals(supSmsMsg)) {
						return "{\"msg\":\"操作成功\",\"state\":\"true\",\"errSupMsgs\":\""+supSmsMsg+"信息不完善请补充\"}";
					}
					return "{\"msg\":\"操作成功\",\"state\":\"true\"}";
				}
				
				
			}
		}catch (Exception e) {
			logger.error(e.getMessage(), e);
			return "{\"error\":\"发送失败 数据库异常 请联系管理员! 方法 auditOrderMain\"}";
		}	
	}
	@Resource(name = "superPrintService")
	private final SuperPrintService superPrintService = null;
	@Override
	public String queryMatOrderAuditByPrintTemlate(Map<String, Object> entityMap) throws DataAccessException {
		
		//查询入库主表
		Map<String,Object> map=matOrderAuditMapper.queryMatOrderAuditPrintTemlateByMain(entityMap);
		
		//查询入库明细表
		List<Map<String,Object>> list=matOrderAuditMapper.queryMatOrderAuditPrintTemlateByDetail(entityMap);
		
		return superPrintService.getMapListByPrintTemplateJson(entityMap,map,list);
	}
	
	//新版打印
	public Map<String, Object> queryMatOrderAuditByPrintTemlateNewPrint(Map<String, Object> entityMap)
			throws DataAccessException {
		try {
			
			Map<String,Object> reMap=new HashMap<String,Object>();
			WebApplicationContext context = ContextLoader.getCurrentWebApplicationContext();
			MatOrderAuditMapper matOrderAuditMapper = (MatOrderAuditMapper)context.getBean("matOrderAuditMapper");
			
			//查询入库主表
			Map<String,Object> map=matOrderAuditMapper.queryMatOrderAuditPrintTemlateByMain(entityMap); 
			//查询入库明细表
			List<Map<String,Object>> list=matOrderAuditMapper.queryMatOrderAuditPrintTemlateByDetail(entityMap);
			
			reMap.put("main", map);
			reMap.put("detail", list); 
			
			return reMap;
	
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			throw new SysException(e.getMessage());
		}
	}
	/**
	 * 订单编制 查询--明细查询
	 */
	@Override
	public String queryShowDetailCheck(Map<String, Object> entityMap) {
		SysPage sysPage = new SysPage();
		sysPage = (SysPage) entityMap.get("sysPage");
		if (sysPage.getTotal()==-1){
			List<?> list = matOrderAuditMapper.queryShowDetailCheck(entityMap);
			
			return ChdJson.toJson(list);
		}else{
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			List<?> list = matOrderAuditMapper.queryShowDetailCheck(entityMap, rowBounds);
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
		}
	}
	
	//验证
	public boolean validateJSON(String str) {
		if (str != null && str != "null" && str != "" && str != "0") {
			return true;
		}
		return false;
	}
	
	// 撤销
	@Override
	public String revokeOutOrderMain(List<Map<String, Object>> listVo)
			throws DataAccessException {
		// TODO Auto-generated method stub
		try{
			String msg = "";
			
			for(Map<String, Object> mapVo : listVo){
				int i = matOrderAuditMapper.querySupOrderCount(mapVo);
				if(i != 0){
					msg = msg + mapVo.get("order_code").toString() + ",";
				}
			}
			
			if(msg != ""){
				return "{\"error\":\""+msg.substring(0, msg.length()-1)+"订单对应的供应商已出货.\",\"state\":\"false\"}";
			}
			
			matOrderAuditMapper.revokeOutOrderMain(listVo);
			
			return "{\"msg\":\"撤销成功\",\"state\":\"true\"}";
		}catch(Exception e){
			logger.error(e.getMessage(), e);
			return "{\"error\":\"撤销失败 数据库异常 请联系管理员! 方法 revokeOutOrderMain\"}";
		}	
	}
}
