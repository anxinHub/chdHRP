/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/

package com.chd.hrp.acc.serviceImpl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.chd.base.SessionManager;
import com.chd.base.util.ChdJson;
import com.chd.hrp.acc.dao.AccMpayVeriMapper;
import com.chd.hrp.acc.entity.AccLederCheck;
import com.chd.hrp.acc.entity.AccMpayVeri;
import com.chd.hrp.acc.service.AccMpayVeriService;
import com.chd.hrp.sys.dao.notGeneral.SysFunUtilMapper;


@Service("accMpayVeriService")
public class AccMpayVeriServiceImpl implements AccMpayVeriService {

	private static Logger logger = Logger.getLogger(AccMpayVeriServiceImpl.class);
	
	@Resource(name = "accMpayVeriMapper")
	private final AccMpayVeriMapper accMpayVeriMapper = null;
    
	@Resource(name = "sysFunUtilMapper")
	private final SysFunUtilMapper sysFunUtilMapper = null;
	
	@Override
	public String addAccMpayVeri(Map<String,Object> mapVo)throws DataAccessException{
	
		try {
			
			List<Map<String, Object>> entityList = new ArrayList<Map<String,Object>>();
			
			JSONArray jsonJ = JSONArray.parseArray((String)mapVo.get("check_dataJ"));
			
			JSONArray jsonD = JSONArray.parseArray((String)mapVo.get("check_dataD"));
			
			Date now = new Date(); 
			
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			
	        Iterator itJ = jsonJ.iterator();
	        
	        Iterator itD = jsonD.iterator();
	        
	        JSONObject jsonObj =JSONObject.parseObject(itJ.next().toString());
	        
        	while(itD.hasNext()){
        		
        		JSONObject jsonObjJ = JSONObject.parseObject(itD.next().toString());
        		
        		Map<String, Object> mapJ = new HashMap<String, Object>();
        		
        		mapJ.put("group_id", SessionManager.getGroupId());
        		
        		mapJ.put("hos_id", SessionManager.getHosId());
        		
        		mapJ.put("copy_code", SessionManager.getCopyCode());
        		
        		mapJ.put("acc_year", SessionManager.getAcctYear());
        		
        		mapJ.put("in_id", jsonObjJ.get("in_id"));
        		
        		mapJ.put("pay_id", jsonObj.get("pay_id"));
        		
        		mapJ.put("veri_money", jsonObjJ.get("amount_money"));
        		
        		mapJ.put("create_date", dateFormat.format(now));
        		
        		mapJ.put("create_user", SessionManager.getUserId());
        		
        		mapJ.put("note", "核销供应商【"+jsonObjJ.get("sup_name")+"】"+jsonObjJ.get("amount_money")+"！");	

        		entityList.add(mapJ);
        	}
			
        	accMpayVeriMapper.addBatchAccMpayVeri(entityList);
        	
        	return "{\"msg\":\"核销成功！\",\"state\":\"true\"}";
		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"核销失败 数据库异常 请联系管理员! 错误编码 addAccMpayVeri\"}";

		}

	}
	
	/**
	 * @Description 
	 * 工资套<BR> 批量添加AccMpayVeri
	 * @param  AccMpayVeri entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addBatchAccMpayVeri(List<Map<String,Object>> entityList)throws DataAccessException{

		try {
			
			accMpayVeriMapper.addBatchAccMpayVeri(entityList);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码 addBatchAccMpayVeri\"}";

		}
	}
	
	/**
	 * @Description 
	 * 工资套<BR> 查询AccMpayVeri分页
	 * @param  entityMap RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String queryAccMpayVeri(Map<String,Object> entityMap) throws DataAccessException{
		
		/*SysPage sysPage = new SysPage();
		
		sysPage = (SysPage)entityMap.get("sysPage");
		
		if(sysPage.getTotal() == -1){*/
			
			List<Map<String, Object>> list = accMpayVeriMapper.queryAccMpayVeri(entityMap);
			
			return ChdJson.toJson(list);
		/*}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<AccMpayVeri> list = accMpayVeriMapper.queryAccMpayVeri(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list,page.getTotal());
		}*/
		
	}
	
	/**
	 * @Description 
	 * 工资套<BR> 查询AccMpayVeriByCode
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public AccMpayVeri queryAccMpayVeriByCode(Map<String,Object> entityMap)throws DataAccessException{
		
		return accMpayVeriMapper.queryAccMpayVeriByCode(entityMap);
		
	}
	
	/**
	 * @Description 
	 * 工资套<BR> 批量删除AccMpayVeri
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String deleteBatchAccMpayVeri(List<Map<String,Object>> entityList)throws DataAccessException{

		try {
			
			accMpayVeriMapper.deleteBatchAccMpayVeri(entityList);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码  deleteBatchAccMpayVeri\"}";

		}
		
	}
	
	/**
	 * @Description 
	 * 工资套<BR> 删除AccMpayVeri
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
  public String deleteAccMpayVeri(Map<String, Object> entityMap) throws DataAccessException {
		
		try {
			accMpayVeriMapper.deleteAccMpayVeri(entityMap);
				
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码  deleteAccMpayVeri\"}";

		}
    }

	@Override
	public String queryAccMpayVeriR(Map<String, Object> entityMap)
			throws DataAccessException {
		
		List<Map<String, Object>> list = accMpayVeriMapper.queryAccMpayVeriR(entityMap);
		
		return ChdJson.toJson(list);
	}

	@Override
	public String queryAccVeriAll(Map<String, Object> entityMap)
			throws DataAccessException {
		
		JSONObject lastJson = new JSONObject();//最终返回的json串
		
		StringBuffer  check = new StringBuffer();
		
        StringBuffer checkCol= new StringBuffer();
        
        StringBuffer is_autoWhere = new StringBuffer();
        
        is_autoWhere.append("");
        
        StringBuffer tableName = new StringBuffer();
        
        String item_name = "";
      
        if(entityMap.get("acc_monthLB") != null){
        	if(entityMap.get("acc_monthLB").equals("0") ){
        		entityMap.put("acc_monthLB", "00");
            }
        }
        if(entityMap.get("acc_monthRB") != null){
        	if(entityMap.get("acc_monthRB").equals("0") ){
        		entityMap.put("acc_monthRB", "00");
            }
        }
        
      List<Map<String, Object>> listL =  null;
      List<Map<String, Object>> listR =  null;
	  
	  if(entityMap.get("is_auto").equals("0")){
		  //手动核销开始处理----------begin------	
		listL = accMpayVeriMapper.queryAccMpayVeri(entityMap);
		JSONObject left = JSONArray.parseObject(ChdJson.toJson(listL));
		lastJson.put("left",left);
		 
		listR = accMpayVeriMapper.queryAccMpayVeriR(entityMap);
		JSONObject right = JSONArray.parseObject(ChdJson.toJson(listR));
		lastJson.put("right",right);
		 
		//手动核销开始处理----------end------  
	  }else if(entityMap.get("is_auto").equals("1")){
		  //自动核销开始处理----------begin------
		  List<Map<String, Object>> listLN =  new ArrayList<Map<String, Object>>();
	      List<Map<String, Object>> listRN =  new ArrayList<Map<String, Object>>();
		 
	      listL = accMpayVeriMapper.queryAccMpayVeri(entityMap);//左侧结果集

	      listR = accMpayVeriMapper.queryAccMpayVeriR(entityMap);//右侧结果集
		 
		  Map<String,AccLederCheck> mapL = new HashMap<String,AccLederCheck>();//存放比较键
		  
		  //处理左侧结果集
		  StringBuffer condition = new StringBuffer();

		  //未核销金额
		  if(entityMap.get("wbal_amt_check").equals("1")){
			  condition.append("amount_money@");  
		  }
		  //票据号
		  if(entityMap.get("bill_no").equals("1")){
			  condition.append("bill_no@"); 
		  }
		  //单据号
		  if(entityMap.get("in_no").equals("1")){
			  condition.append("in_no@"); 
		  }
		  //发生日期
		  if(entityMap.get("in_date").equals("1")){
			  condition.append("in_date@"); 
		  }
		  
		  //自动勾兑
		  for (int i = 0; i < condition.toString().split("@").length-1; i++) {
			
			  for (Map<String, Object> map : listL) {
					
				  for (Map<String, Object> mapR : listR) {
						
					  if(map.get(condition.toString().split("@")[i]).equals(mapR.get(condition.toString().split("@")[i]))){
						  
						  map.put("is_check", "true");
						  
						  listLN.add(map);
						  
						  listRN.add(mapR);
					  }
					  
				  }
				  
			  }
		}
		  
		JSONObject left = JSONArray.parseObject(ChdJson.toJson(listLN));
		lastJson.put("left",left);
		
		JSONObject right = JSONArray.parseObject(ChdJson.toJson(listRN));
		lastJson.put("right",right);	 
		//自动核销开始处理----------end------
	  }
	  return lastJson.toString();
	}
	
}
