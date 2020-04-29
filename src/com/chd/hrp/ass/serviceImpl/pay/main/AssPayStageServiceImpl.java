/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.ass.serviceImpl.pay.main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.SysPage;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.hrp.ass.dao.bill.AssBillStageMapper;
import com.chd.hrp.ass.dao.pay.main.AssPayDetailMapper;
import com.chd.hrp.ass.dao.pay.main.AssPayMainMapper;
import com.chd.hrp.ass.dao.pay.main.AssPayStageMapper;
import com.chd.hrp.ass.entity.bill.AssBillDetail;
import com.chd.hrp.ass.entity.bill.AssBillStage;
import com.chd.hrp.ass.entity.pay.main.AssPayDetail;
import com.chd.hrp.ass.entity.pay.main.AssPayStage;
import com.chd.hrp.ass.service.pay.main.AssPayStageService;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Description:
 * 付款支付方式表
 * @Table:
 * ASS_PAY_STAGE
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 


@Service("assPayStageService")
public class AssPayStageServiceImpl implements AssPayStageService {

	private static Logger logger = Logger.getLogger(AssPayStageServiceImpl.class);
	//引入DAO操作
	@Resource(name = "assPayStageMapper")
	private final AssPayStageMapper assPayStageMapper = null;
	
	@Resource(name = "assBillStageMapper")
	private final AssBillStageMapper assBillStageMapper = null;
	
	@Resource(name = "assPayDetailMapper")
	private final AssPayDetailMapper assPayDetailMapper = null;
    
	@Resource(name = "assPayMainMapper")
	private final AssPayMainMapper assPayMainMapper = null;
	
    
	/**
	 * @Description 
	 * 添加付款支付方式表<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String add(Map<String,Object> entityMap)throws DataAccessException{
		
		//获取对象付款支付方式表
		AssPayStage assPayStage = queryByCode(entityMap);

		if (assPayStage != null) {

			return "{\"error\":\"数据重复,请重新添加.\"}";

		}
		
		try {
			
			int state = assPayStageMapper.add(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 add\"}";

		}
		
	}
	/**
	 * @Description 
	 * 批量添加付款支付方式表<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addBatch(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
			assPayStageMapper.addBatch(entityList);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addBatch\"}";

		}
		
	}
	
		/**
	 * @Description 
	 * 更新付款支付方式表<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String update(Map<String,Object> entityMap)throws DataAccessException{
		
		try {
			
		  int state = assPayStageMapper.update(entityMap);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 update\"}";

		}	
		
	}
	/**
	 * @Description 
	 * 批量更新付款支付方式表<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateBatch(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
		  assPayStageMapper.updateBatch(entityList);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 updateBatch\"}";

		}	
		
	}
	/**
	 * @Description 
	 * 删除付款支付方式表<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
    public String delete(Map<String, Object> entityMap) throws DataAccessException {
    	
    try {
			
			int state = assPayStageMapper.delete(entityMap);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 delete\"}";

		}	
		
  }
    
	/**
	 * @Description 
	 * 批量删除付款支付方式表<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String deleteBatch(List<Map<String,Object>> entityList)throws DataAccessException{
		try {
			
			assPayStageMapper.deleteBatch(entityList);
			
			
			Map<String, Object> mapVo = new HashMap<String, Object>();
			mapVo.put("group_id", entityList.get(0).get("group_id"));
			mapVo.put("hos_id", entityList.get(0).get("hos_id"));
			mapVo.put("copy_code", entityList.get(0).get("copy_code"));
			mapVo.put("pay_no", entityList.get(0).get("pay_no"));
			mapVo.put("bill_no", entityList.get(0).get("bill_no"));
			List<AssPayStage> payStageList = (List<AssPayStage>)assPayStageMapper.query(mapVo);
			Double bill_money = 0.0;
			for(AssPayStage assPayStage : payStageList){
				bill_money = bill_money + assPayStage.getPay_money();
			}
			mapVo.put("bill_money", bill_money + "");
			assPayDetailMapper.updateBillMoney(mapVo);
			
			Map<String, Object> detMapVo = new HashMap<String, Object>();
			detMapVo.put("group_id", entityList.get(0).get("group_id"));
			detMapVo.put("hos_id", entityList.get(0).get("hos_id"));
			detMapVo.put("copy_code", entityList.get(0).get("copy_code"));
			detMapVo.put("pay_no", entityList.get(0).get("pay_no"));
			
			List<AssPayDetail> detailList = (List<AssPayDetail>)assPayDetailMapper.query(detMapVo);
			Double pay_money = 0.0;
			for(AssPayDetail temp : detailList){
				pay_money += temp.getBill_money();
			}
			detMapVo.put("pay_money", pay_money + "");
			
			assPayMainMapper.updatePayMoney(detMapVo);
    		
    		return "{\"msg\":\"保存成功.\",\"state\":\"true\",\"bill_money\":\""+bill_money+"\",\"pay_money\":\""+pay_money+"\"}";

		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}		
	}
	
	/**
	 * @Description 
	 * 添加付款支付方式表<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addOrUpdate(Map<String,Object> entityMap)throws DataAccessException{
		List<Map<String, Object>> listVo = new ArrayList<Map<String,Object>>();
    	try{	
    		List<Map> detail = ChdJson.fromJsonAsList(Map.class, entityMap.get("ParamVo").toString());
    		for (Map<String, Object> detailVo : detail) {
				if (detailVo.get("pay_code") == null || "".equals(detailVo.get("pay_code"))) {
					continue;
				}
				
				detailVo.put("group_id",entityMap.get("group_id"));
				detailVo.put("hos_id",entityMap.get("hos_id"));
				detailVo.put("copy_code", entityMap.get("copy_code"));
				detailVo.put("pay_no", entityMap.get("pay_no"));
				detailVo.put("bill_no", entityMap.get("bill_no"));
				detailVo.put("pay_code", detailVo.get("pay_code"));
				if(detailVo.get("pay_money") != null && !detailVo.get("pay_money").equals("")){
					detailVo.put("pay_money", detailVo.get("pay_money")+"");
				}else{
					detailVo.put("pay_money", "0");
				}
				
				if(detailVo.get("note") != null && !detailVo.get("note").equals("")){
					detailVo.put("note", detailVo.get("note"));
				}else{
					detailVo.put("note", "");
				}
				listVo.add(detailVo);
    		}
    		assPayStageMapper.delete(entityMap);
    		assPayStageMapper.addBatch(listVo);
    		
    		Map<String, Object> mapVo = new HashMap<String, Object>();
			mapVo.put("group_id", entityMap.get("group_id"));
			mapVo.put("hos_id", entityMap.get("hos_id"));
			mapVo.put("copy_code", entityMap.get("copy_code"));
			mapVo.put("pay_no", entityMap.get("pay_no"));
			mapVo.put("bill_no", entityMap.get("bill_no"));
			List<AssPayStage> payStageList = (List<AssPayStage>)assPayStageMapper.query(mapVo);
			Double bill_money = 0.0;
			for(AssPayStage assPayStage : payStageList){
				bill_money = bill_money + assPayStage.getPay_money();
			}
			mapVo.put("bill_money", bill_money + "");
			assPayDetailMapper.updateBillMoney(mapVo);
			
			Map<String, Object> detMapVo = new HashMap<String, Object>();
			detMapVo.put("group_id", entityMap.get("group_id"));
			detMapVo.put("hos_id", entityMap.get("hos_id"));
			detMapVo.put("copy_code", entityMap.get("copy_code"));
			detMapVo.put("pay_no", entityMap.get("pay_no"));
			
			List<AssPayDetail> detailList = (List<AssPayDetail>)assPayDetailMapper.query(detMapVo);
			Double pay_money = 0.0;
			for(AssPayDetail temp : detailList){
				pay_money += temp.getBill_money();
			}
			detMapVo.put("pay_money", pay_money + "");
			
			assPayMainMapper.updatePayMoney(detMapVo);
    		
    		return "{\"msg\":\"保存成功.\",\"state\":\"true\",\"bill_money\":\""+bill_money+"\",\"pay_money\":\""+pay_money+"\"}";
		}catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}
		
	}
	/**
	 * @Description 
	 * 查询结果集付款支付方式表<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String query(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<AssPayStage> list = (List<AssPayStage>)assPayStageMapper.query(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<AssPayStage> list = (List<AssPayStage>)assPayStageMapper.query(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
		
	}
	
	/**
	 * @Description 
	 * 获取对象付款支付方式表<BR> 
	 * @param  entityMap<BR>
	 *  参数为主键
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public <T> T queryByCode(Map<String,Object> entityMap)throws DataAccessException{
		return assPayStageMapper.queryByCode(entityMap);
	}
	
	/**
	 * @Description 
	 * 获取付款支付方式表<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return AssPayStage
	 * @throws DataAccessException
	*/
	@Override
	public <T> T queryByUniqueness(Map<String,Object> entityMap)throws DataAccessException{
		return assPayStageMapper.queryByUniqueness(entityMap);
	}
	
	/**
	 * @Description 
	 * 获取付款支付方式表<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return List<AssPayStage>
	 * @throws DataAccessException
	*/
	@Override
	public List<?> queryExists(Map<String,Object> entityMap)throws DataAccessException{
		return assPayStageMapper.queryExists(entityMap);
	}
	@Override
	public String queryBillStageDetail(Map<String, Object> entityMap) throws DataAccessException {
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			List<AssBillStage> list = (List<AssBillStage>)assBillStageMapper.queryBillStageByBillNo(entityMap);
			return ChdJson.toJson(list);
		}else{
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			List<AssBillStage> list = (List<AssBillStage>)assBillStageMapper.queryBillStageByBillNo(entityMap, rowBounds);
			PageInfo page = new PageInfo(list);
			return ChdJson.toJson(list, page.getTotal());
			
		}
	}
	@Override
	public List<Map<String, Object>> queryByPayNo(Map<String, Object> entityMap) throws DataAccessException {
		return assPayStageMapper.queryByPayNo(entityMap);
	}
	@Override
	public String addOrUpdateAss(Map<String, Object> entityMap) {
		List<Map<String, Object>> listVo = new ArrayList<Map<String,Object>>();
    	try{	
    		String bill_no = "";
    		List<Map> detail = ChdJson.fromJsonAsList(Map.class, entityMap.get("ParamVo").toString());
    		for (Map<String, Object> detailVo : detail) {
    			bill_no = detailVo.get("bill_no").toString();
				detailVo.put("group_id",entityMap.get("group_id"));
				detailVo.put("hos_id",entityMap.get("hos_id"));
				detailVo.put("copy_code", entityMap.get("copy_code"));
				detailVo.put("pay_no", entityMap.get("pay_no"));
				detailVo.put("bill_no", bill_no);
				detailVo.put("pay_code", entityMap.get("pay_code"));
				if(detailVo.get("bill_money_b") != null && !detailVo.get("bill_money_b").equals("")){
					detailVo.put("pay_money", detailVo.get("bill_money_b")+"");
				}else{
					detailVo.put("pay_money", "0");
				}
				
				if(detailVo.get("note") != null && !detailVo.get("note").equals("")){
					detailVo.put("note", detailVo.get("note"));
				}else{
					detailVo.put("note", "");
				}
				listVo.add(detailVo);
    		}
    		assPayStageMapper.deleteBatchs(listVo);
    		assPayStageMapper.addBatch(listVo);
    		
    		//更新付款金额用
    		Double bill_money = 0.0;
    		Double pay_money = 0.0;
    		
	    	for (Map<String, Object> detailVo : detail) {
	    		Map<String, Object> mapVo = new HashMap<String, Object>();
				mapVo.put("group_id", entityMap.get("group_id"));
				mapVo.put("hos_id", entityMap.get("hos_id"));
				mapVo.put("copy_code", entityMap.get("copy_code"));
				mapVo.put("pay_no", entityMap.get("pay_no"));
				mapVo.put("bill_no", detailVo.get("bill_no"));
				List<AssPayStage> payStageList = (List<AssPayStage>)assPayStageMapper.query(mapVo);
				
				for(AssPayStage assPayStage : payStageList){
					bill_money =  assPayStage.getPay_money();
				}
				mapVo.put("bill_money", bill_money + "");
				assPayDetailMapper.updateBillMoney(mapVo);
				
				Map<String, Object> detMapVo = new HashMap<String, Object>();
				detMapVo.put("group_id", entityMap.get("group_id"));
				detMapVo.put("hos_id", entityMap.get("hos_id"));
				detMapVo.put("copy_code", entityMap.get("copy_code"));
				detMapVo.put("pay_no", entityMap.get("pay_no"));
				detMapVo.put("bill_no", detailVo.get("bill_no"));
				
				List<AssPayDetail> detailList = (List<AssPayDetail>)assPayDetailMapper.query(detMapVo);
				
				for(AssPayDetail temp : detailList){
					pay_money += temp.getBill_money();
				}
				detMapVo.put("pay_money", pay_money + "");
				
				assPayMainMapper.updatePayMoney(detMapVo);
	    	}
    		return "{\"msg\":\"保存成功.\",\"state\":\"true\",\"bill_money\":\""+bill_money+"\",\"pay_money\":\""+pay_money+"\"}";
		}catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}
	}
	
}
