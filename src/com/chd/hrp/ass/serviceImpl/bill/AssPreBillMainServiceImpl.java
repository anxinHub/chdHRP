/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.ass.serviceImpl.bill;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import com.chd.base.SysPage;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.hrp.ass.dao.bill.AssPreBillDetailMapper;
import com.chd.hrp.ass.dao.bill.AssPreBillMainMapper;
import com.chd.hrp.ass.entity.bill.AssPreBillMain;
import com.chd.hrp.ass.service.base.AssBaseService;
import com.chd.hrp.ass.service.bill.AssPreBillMainService;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Description:
 * tabledesc
 * @Table:
 * ASS_PRE_BILL_MAIN
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

 
@Service("assPreBillMainService")
public class AssPreBillMainServiceImpl implements AssPreBillMainService {

	private static Logger logger = Logger.getLogger(AssPreBillMainServiceImpl.class);
	//引入DAO操作
	@Resource(name = "assPreBillMainMapper")
	private final AssPreBillMainMapper assPreBillMainMapper = null;
    
	@Resource(name = "assPreBillDetailMapper")
	private final AssPreBillDetailMapper assPreBillDetailMapper = null;
	
	@Resource(name = "assBaseService")
	private final AssBaseService assBaseService = null;
	/**
	 * @Description 
	 * 添加tabledesc<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String add(Map<String,Object> entityMap)throws DataAccessException{
		
		//获取对象tabledesc
		AssPreBillMain assPreBillMain = queryByCode(entityMap);

		if (assPreBillMain != null) {

			return "{\"error\":\"数据重复,请重新添加.\"}";

		}
		
		try {
			
			int state = assPreBillMainMapper.add(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 add\"}";

		}
		
	}
	/**
	 * @Description 
	 * 批量添加tabledesc<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addBatch(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
			assPreBillMainMapper.addBatch(entityList);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addBatch\"}";

		}
		
	}
	
		/**
	 * @Description 
	 * 更新tabledesc<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String update(Map<String,Object> entityMap)throws DataAccessException{
		
		try {
			
		  int state = assPreBillMainMapper.update(entityMap);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 update\"}";

		}	
		
	}
	/**
	 * @Description 
	 * 批量更新tabledesc<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateBatch(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
		  assPreBillMainMapper.updateBatch(entityList);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 updateBatch\"}";

		}	
		
	}
	/**
	 * @Description 
	 * 删除tabledesc<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
    public String delete(Map<String, Object> entityMap) throws DataAccessException {
    	
    try {
			int state = assPreBillMainMapper.delete(entityMap);
						
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 delete\"}";

		}	
		
  }
    
	/**
	 * @Description 
	 * 批量删除tabledesc<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String deleteBatch(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			assPreBillDetailMapper.deleteBatch(entityList);
			assPreBillMainMapper.deleteBatch(entityList);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 deleteBatch\"}";

		}	
	}
	
	/**
	 * @Description 
	 * 添加tabledesc<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addOrUpdate(Map<String,Object> entityMap)throws DataAccessException{
		
		List<AssPreBillMain> list = (List<AssPreBillMain>)assPreBillMainMapper.queryExists(entityMap);
		
		if (list.size()>0) {

			int state = assPreBillMainMapper.update(entityMap);
			assPreBillDetailMapper.delete(entityMap);
			List<Map> detail = ChdJson.fromJsonAsList(Map.class, entityMap.get("ParamVo").toString());
			for (Map<String,Object> map : detail) {
				if( map.get("ass_id")!= null && !("").equals(map.get("ass_id")) ){
				map.put("group_id", entityMap.get("group_id"));
				map.put("hos_id", entityMap.get("hos_id"));
				map.put("copy_code", entityMap.get("copy_code"));
				map.put("bill_no", entityMap.get("bill_no"));
				map.put("ass_no", map.get("ass_id").toString().split("@")[1]);
				map.put("ass_id", map.get("ass_id").toString().split("@")[0]);
				assPreBillDetailMapper.add(map);
				}
			}
			return "{\"msg\":\"更新成功.\",\"state\":\"true\",\"bill_no\":\""+entityMap.get("bill_no")+"\"}";

		}
		
		try {
			if(entityMap.get("create_date") != null && !"".equals(entityMap.get("create_date"))){
				entityMap.put("year", entityMap.get("create_date").toString().substring(0,4));
				entityMap.put("month", entityMap.get("create_date").toString().substring(5,7));
			}
			entityMap.put("bill_table", "ASS_PRE_BILL_MAIN");
			String bill_no = assBaseService.getBillNOSeqNo(entityMap);
			entityMap.put("bill_no", bill_no);
			int state = assPreBillMainMapper.add(entityMap);
			assBaseService.updateAssBillNoMaxNo(entityMap);
			
			List<Map> detail = ChdJson.fromJsonAsList(Map.class, entityMap.get("ParamVo").toString());
			for (Map<String,Object>  map : detail) {
				if(map.get("ass_id")!= null && !("").equals(map.get("ass_id"))){
					map.put("group_id", entityMap.get("group_id"));
					map.put("hos_id", entityMap.get("hos_id"));
					map.put("copy_code", entityMap.get("copy_code"));
					map.put("bill_no", entityMap.get("bill_no"));
					map.put("ass_no", map.get("ass_id").toString().split("@")[1]);
					map.put("ass_id", map.get("ass_id").toString().split("@")[0]);
					assPreBillDetailMapper.add(map);
				}
				
			}
			
			
			
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\",\"bill_no\":\""+bill_no+"\"}";
			
		}
		
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addOrUpdate\"}";

		}
		
	}
	/**
	 * @Description 
	 * 查询结果集tabledesc<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String query(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<AssPreBillMain> list = (List<AssPreBillMain>)assPreBillMainMapper.query(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<AssPreBillMain> list = (List<AssPreBillMain>)assPreBillMainMapper.query(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
		
	}
	
	/**
	 * @Description 
	 * 获取对象tabledesc<BR> 
	 * @param  entityMap<BR>
	 *  参数为主键
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public <T> T queryByCode(Map<String,Object> entityMap)throws DataAccessException{
		return assPreBillMainMapper.queryByCode(entityMap);
	}
	
	/**
	 * @Description 
	 * 获取tabledesc<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return AssPreBillMain
	 * @throws DataAccessException
	*/
	@Override
	public <T> T queryByUniqueness(Map<String,Object> entityMap)throws DataAccessException{
		return assPreBillMainMapper.queryByUniqueness(entityMap);
	}
	
	/**
	 * @Description 预付款发票打印
	 * @param entityMap
	 * @return String
	 * @throws DataAccessException
	 */
	@Override
	public Map<String,Object> queryAssPreBillDY(Map<String, Object> map)throws DataAccessException {
		
		try{
			Map<String,Object> reMap=new HashMap<String,Object>();
			WebApplicationContext context = ContextLoader.getCurrentWebApplicationContext();
			AssPreBillMainMapper assPreBillMainMapper = (AssPreBillMainMapper)context.getBean("assPreBillMainMapper");
			if("1".equals(String.valueOf(map.get("p_num")))){
				// 预付款发票打印模板主表
				List<Map<String,Object>> mainList=assPreBillMainMapper.queryAssPreBillMainBatch(map);
						
				//预付款发票打印模板从表
				List<Map<String,Object>> detailList=assPreBillMainMapper.queryAssPreBill_detail(map);
				
				reMap.put("main", mainList);
				reMap.put("detail", detailList);
			}else{
				Map<String,Object> mainList=assPreBillMainMapper.querAssPreBillByPrint(map);
				
				//预付款发票打印模板从表
				List<Map<String,Object>> detailList=assPreBillMainMapper.queryAssPreBill_detail(map);
				reMap.put("main", mainList);
				reMap.put("detail", detailList);
			}
			
		
			
			return reMap;
		}catch(Exception e){
			logger.error(e.getMessage(),e);
			throw new SysException(e.getMessage());
		}
		
	}
	
	
	
	
	/**
	 * @Description 
	 * 获取tabledesc<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return List<AssPreBillMain>
	 * @throws DataAccessException
	*/
	@Override
	public List<?> queryExists(Map<String,Object> entityMap)throws DataAccessException{
		return assPreBillMainMapper.queryExists(entityMap);
	}
	@Override
	public String queryAssPreBilldetail(Map<String, Object> mapVo) {
		List<AssPreBillMain> list = (List<AssPreBillMain>)assPreBillDetailMapper.query(mapVo);
		return ChdJson.toJson(list);
	}
	/**
	 *  发票审核
	 */
	@Override
	public String checkAssPreBillMain(List<Map<String, Object>> listVo) {
		try {
			assPreBillMainMapper.checkAssPreBillMain(listVo);
				return "{\"msg\":\"成功.\",\"state\":\"true\"}";
			
		}
		
		catch (Exception e) {

			throw new SysException(e);

		}
	}
	@Override
	public String updateNotToExamineAssPreBillMain(List<Map<String, Object>> listVo) {
		try {
			assPreBillMainMapper.updateNotToExamineAssPreBillMain(listVo);
			return "{\"msg\":\"成功.\",\"state\":\"true\"}";
		} catch (Exception e) {

			throw new SysException(e);
		}
		
	}
	@Override
	public List<String> queryAssPreBillState(Map<String, Object> mapVo)
			throws DataAccessException {
		
		return assPreBillMainMapper.queryAssPreBillState(mapVo);
	}
	
}
