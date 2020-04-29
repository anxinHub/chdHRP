/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.ass.serviceImpl.check.house;

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
import com.chd.hrp.ass.dao.check.house.AssCheckApDetailHouseMapper;
import com.chd.hrp.ass.dao.check.house.AssCheckApHouseMapper;
import com.chd.hrp.ass.entity.check.house.AssCheckApHouse;
import com.chd.hrp.ass.service.base.AssBaseService;
import com.chd.hrp.ass.service.check.house.AssCheckApHouseService;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Description:
 * 051101 盘盈处置申请单(房屋及建筑)
 * @Table:
 * ASS_CHECK_AP_House
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

 
@Service("assCheckApHouseService")
public class AssCheckApHouseServiceImpl implements AssCheckApHouseService {

	private static Logger logger = Logger.getLogger(AssCheckApHouseServiceImpl.class);
	//引入DAO操作
	@Resource(name = "assCheckApHouseMapper")
	private final AssCheckApHouseMapper assCheckApHouseMapper = null;
	
	@Resource(name = "assCheckApDetailHouseMapper")
	private final AssCheckApDetailHouseMapper assCheckApDetailHouseMapper = null;
	
	@Resource(name = "assBaseService")
	private final AssBaseService assBaseService = null;
    
	/**
	 * @Description 
	 * 添加051101 盘盈处置申请单(房屋及建筑)<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String add(Map<String,Object> entityMap)throws DataAccessException{
		
		//获取对象051101 盘盈处置申请单(房屋及建筑)
		AssCheckApHouse assCheckApHouse = queryByCode(entityMap);

		if (assCheckApHouse != null) {

			return "{\"error\":\"数据重复,请重新添加.\"}";

		}
		
		try {
			
			int state = assCheckApHouseMapper.add(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 add\"}";

		}
		
	}
	/**
	 * @Description 
	 * 批量添加051101 盘盈处置申请单(房屋及建筑)<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addBatch(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
			assCheckApHouseMapper.addBatch(entityList);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addBatch\"}";

		}
		
	}
	
		/**
	 * @Description 
	 * 更新051101 盘盈处置申请单(房屋及建筑)<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String update(Map<String,Object> entityMap)throws DataAccessException{
		
		try {
			
		  int state = assCheckApHouseMapper.update(entityMap);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 update\"}";

		}	
		
	}
	/**
	 * @Description 
	 * 批量更新051101 盘盈处置申请单(房屋及建筑)<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateBatch(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
		  assCheckApHouseMapper.updateBatch(entityList);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 updateBatch\"}";

		}	
		
	}
	/**
	 * @Description 
	 * 删除051101 盘盈处置申请单(房屋及建筑)<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
    public String delete(Map<String, Object> entityMap) throws DataAccessException {
    	
    try {
			
			int state = assCheckApHouseMapper.delete(entityMap);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 delete\"}";

		}	
		
  }
    
	/**
	 * @Description 
	 * 批量删除051101 盘盈处置申请单(房屋及建筑)<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String deleteBatch(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
			assCheckApDetailHouseMapper.deleteBatch(entityList);
			assCheckApHouseMapper.deleteBatch(entityList);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 deleteBatch\"}";

		}	
	}
	
	/**
	 * @Description 
	 * 添加051101 盘盈处置申请单(房屋及建筑)<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addOrUpdate(Map<String,Object> entityMap)throws DataAccessException{
		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();
		//判断是否存在对象051101 盘盈处置申请单(房屋及建筑)
		List<AssCheckApHouse> list = (List<AssCheckApHouse>)assCheckApHouseMapper.queryExists(entityMap);
		try {
			entityMap.put("state", "0");
			if (list.size()>0) {
				assCheckApHouseMapper.update(entityMap);
			}else{
				if(entityMap.get("create_date") != null && !"".equals(entityMap.get("create_date"))){
					entityMap.put("year", entityMap.get("create_date").toString().substring(0,4));
					entityMap.put("month", entityMap.get("create_date").toString().substring(5,7));
				}
				entityMap.put("bill_table", "ASS_CHECK_AP_HOUSE");
				String check_ap_no = assBaseService.getBillNOSeqNo(entityMap);
				entityMap.put("check_ap_no", check_ap_no);
				assCheckApHouseMapper.add(entityMap);
				assBaseService.updateAssBillNoMaxNo(entityMap);
			}
			
			List<Map> detailList = ChdJson.fromJsonAsList(Map.class, entityMap.get("ParamVo").toString());
			for (Map<String, Object> detailVo : detailList) {
				if (detailVo.get("ass_id") == null || "".equals(detailVo.get("ass_id"))) {
					continue;
				}
				detailVo.put("group_id", entityMap.get("group_id"));
				detailVo.put("hos_id", entityMap.get("hos_id"));
				detailVo.put("copy_code", entityMap.get("copy_code"));
				detailVo.put("check_ap_no", entityMap.get("check_ap_no"));

				String ass_id_no = detailVo.get("ass_id").toString();
				detailVo.put("ass_id", ass_id_no.split("@")[0]);
				detailVo.put("ass_no", ass_id_no.split("@")[1]);
				
				if (detailVo.get("acc_amount") != null && !detailVo.get("acc_amount").equals("")) {
					detailVo.put("acc_amount", detailVo.get("acc_amount").toString());
				} else {
					detailVo.put("acc_amount", "0");
				}

				if (detailVo.get("price") != null && !detailVo.get("price").equals("")) {
					detailVo.put("price", detailVo.get("price").toString());
				} else {
					detailVo.put("price", "0");
				}

				if (detailVo.get("add_depre") != null && !detailVo.get("add_depre").equals("")) {
					detailVo.put("add_depre", detailVo.get("add_depre").toString());
				} else {
					detailVo.put("add_depre", "0");
				}

				if (detailVo.get("cur_money") != null && !detailVo.get("cur_money").equals("")) {
					detailVo.put("cur_money", detailVo.get("cur_money").toString());
				} else {
					detailVo.put("cur_money", "0");
				}

				if (detailVo.get("fore_money") != null && !detailVo.get("fore_money").equals("")) {
					detailVo.put("fore_money", detailVo.get("fore_money").toString());
				} else {
					detailVo.put("fore_money", "0");
				}

				if (detailVo.get("add_depre_month") != null && !detailVo.get("add_depre_month").equals("")) {
					detailVo.put("add_depre_month", detailVo.get("add_depre_month"));
				} else {
					detailVo.put("add_depre_month", 0);
				}

				if (detailVo.get("p_reason") != null && !detailVo.get("p_reason").equals("")) {
					detailVo.put("p_reason", detailVo.get("p_reason"));
				} else {
					detailVo.put("p_reason", "");
				}

				listVo.add(detailVo);
			}
			assCheckApDetailHouseMapper.delete(entityMap);
			assCheckApDetailHouseMapper.addBatch(listVo);
			
			return "{\"msg\":\"保存成功.\",\"state\":\"true\",\"check_ap_no\":\"" + entityMap.get("check_ap_no").toString()+"\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());
		}
		
	}
	/**
	 * @Description 
	 * 查询结果集051101 盘盈处置申请单(房屋及建筑)<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String query(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<AssCheckApHouse> list = (List<AssCheckApHouse>)assCheckApHouseMapper.query(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<AssCheckApHouse> list = (List<AssCheckApHouse>)assCheckApHouseMapper.query(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
		
	}
	
	/**
	 * @Description 
	 * 获取对象051101 盘盈处置申请单(房屋及建筑)<BR> 
	 * @param  entityMap<BR>
	 *  参数为主键
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public <T> T queryByCode(Map<String,Object> entityMap)throws DataAccessException{
		return assCheckApHouseMapper.queryByCode(entityMap);
	}
	
	/**
	 * @Description 
	 * 获取051101 盘盈处置申请单(房屋及建筑)<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return AssCheckApHouse
	 * @throws DataAccessException
	*/
	@Override
	public <T> T queryByUniqueness(Map<String,Object> entityMap)throws DataAccessException{
		return assCheckApHouseMapper.queryByUniqueness(entityMap);
	}
	
	/**
	 * @Description 
	 * 获取051101 盘盈处置申请单(房屋及建筑)<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return List<AssCheckApHouse>
	 * @throws DataAccessException
	*/
	@Override
	public List<?> queryExists(Map<String,Object> entityMap)throws DataAccessException{
		return assCheckApHouseMapper.queryExists(entityMap);
	}
	@Override
	public String updateConfirmChkAHouse(List<Map<String, Object>> listVo) throws DataAccessException {
		try {
			assCheckApHouseMapper.updateBatchConfirm(listVo);
			return "{\"msg\":\"确认成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			throw new SysException(e.getMessage());
		}
	}
	
}
