/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.ass.serviceImpl.pay.main;

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
import com.chd.hrp.ass.dao.pay.main.AssPayDetailMapper;
import com.chd.hrp.ass.dao.pay.main.AssPayMainMapper;
import com.chd.hrp.ass.dao.pay.main.AssPayStageMapper;
import com.chd.hrp.ass.entity.pay.main.AssPayDetail;
import com.chd.hrp.ass.service.pay.main.AssPayDetailService;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Description:
 * 付款明细表
 * @Table:
 * ASS_PAY_DETAIL
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 


@Service("assPayDetailService")
public class AssPayDetailServiceImpl implements AssPayDetailService {

	private static Logger logger = Logger.getLogger(AssPayDetailServiceImpl.class);
	//引入DAO操作
	@Resource(name = "assPayDetailMapper")
	private final AssPayDetailMapper assPayDetailMapper = null;
	
	@Resource(name = "assPayStageMapper")
	private final AssPayStageMapper assPayStageMapper = null;
	
	@Resource(name = "assPayMainMapper")
	private final AssPayMainMapper assPayMainMapper = null;
    
	/**
	 * @Description 
	 * 添加付款明细表<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String add(Map<String,Object> entityMap)throws DataAccessException{
		
		//获取对象付款明细表
		AssPayDetail assPayDetail = queryByCode(entityMap);

		if (assPayDetail != null) {

			return "{\"error\":\"数据重复,请重新添加.\"}";

		}
		
		try {
			
			int state = assPayDetailMapper.add(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 add\"}";

		}
		
	}
	/**
	 * @Description 
	 * 批量添加付款明细表<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addBatch(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
			assPayDetailMapper.addBatch(entityList);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addBatch\"}";

		}
		
	}
	
		/**
	 * @Description 
	 * 更新付款明细表<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String update(Map<String,Object> entityMap)throws DataAccessException{
		
		try {
			
		  int state = assPayDetailMapper.update(entityMap);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 update\"}";

		}	
		
	}
	/**
	 * @Description 
	 * 批量更新付款明细表<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateBatch(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
		  assPayDetailMapper.updateBatch(entityList);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 updateBatch\"}";

		}	
		
	}
	/**
	 * @Description 
	 * 删除付款明细表<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
    public String delete(Map<String, Object> entityMap) throws DataAccessException {
    	
    try {
			
			int state = assPayDetailMapper.delete(entityMap);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 delete\"}";

		}	
		
  }
    
	/**
	 * @Description 
	 * 批量删除付款明细表<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String deleteBatch(List<Map<String,Object>> entityList)throws DataAccessException{
		try {
			assPayStageMapper.deleteBatch(entityList);
			assPayDetailMapper.deleteBatch(entityList);
			Map<String, Object> mapVo = new HashMap<String, Object>();
			mapVo.put("group_id", entityList.get(0).get("group_id"));
			mapVo.put("hos_id", entityList.get(0).get("hos_id"));
			mapVo.put("copy_code", entityList.get(0).get("copy_code"));
			mapVo.put("pay_no", entityList.get(0).get("pay_no"));
			
			List<AssPayDetail> detailList = (List<AssPayDetail>)assPayDetailMapper.query(mapVo);
			Double pay_money = 0.0;
			for(AssPayDetail temp : detailList){
				pay_money += temp.getBill_money();
			}
			mapVo.put("pay_money", pay_money);
			
			assPayMainMapper.updatePayMoney(mapVo);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\",\"pay_money\":\""+pay_money+"\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());

		}
	}
	
	/**
	 * @Description 
	 * 添加付款明细表<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addOrUpdate(Map<String,Object> entityMap)throws DataAccessException{
		/**
		* 备注 先判断是否存在 存在即更新不存在则添加<br>
		* 在判断是否存在时可根据实际情况进行修改传递的参数进行判断<br>
		* 判断是否名称相同 判断是否 编码相同 等一系列规则
		*/
		//判断是否存在对象付款明细表
		Map<String, Object> mapVo=new HashMap<String, Object>();
		mapVo.put("group_id",entityMap.get("group_id"));
		mapVo.put("hos_id",entityMap.get("hos_id"));
    	mapVo.put("copy_code", entityMap.get("copy_code"));
    	mapVo.put("acct_year", entityMap.get("acct_year"));
		
		List<AssPayDetail> list = (List<AssPayDetail>)assPayDetailMapper.queryExists(mapVo);
		
		if (list.size()>0) {

			int state = assPayDetailMapper.update(entityMap);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		
		try {
			
			int state = assPayDetailMapper.add(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addOrUpdate\"}";

		}
		
	}
	/**
	 * @Description 
	 * 查询结果集付款明细表<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String query(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<AssPayDetail> list = (List<AssPayDetail>)assPayDetailMapper.query(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<AssPayDetail> list = (List<AssPayDetail>)assPayDetailMapper.query(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
		
	}
	
	/**
	 * @Description 
	 * 获取对象付款明细表<BR> 
	 * @param  entityMap<BR>
	 *  参数为主键
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public <T> T queryByCode(Map<String,Object> entityMap)throws DataAccessException{
		return assPayDetailMapper.queryByCode(entityMap);
	}
	
	/**
	 * @Description 
	 * 获取付款明细表<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return AssPayDetail
	 * @throws DataAccessException
	*/
	@Override
	public <T> T queryByUniqueness(Map<String,Object> entityMap)throws DataAccessException{
		return assPayDetailMapper.queryByUniqueness(entityMap);
	}
	
	/**
	 * @Description 
	 * 获取付款明细表<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return List<AssPayDetail>
	 * @throws DataAccessException
	*/
	@Override
	public List<?> queryExists(Map<String,Object> entityMap)throws DataAccessException{
		return assPayDetailMapper.queryExists(entityMap);
	}
	@Override
	public List<Map<String, Object>> queryByAll(Map<String, Object> entityMap) throws DataAccessException {
		return assPayDetailMapper.queryByAll(entityMap);
	}
	@Override
	public List<Map<String, Object>> queryByPayNo(Map<String, Object> entityMap) throws DataAccessException {
		return assPayDetailMapper.queryByPayNo(entityMap);
	}
	@Override
	public String[] queryBillNo(Map<String, Object> mapVo) {
		// TODO Auto-generated method stub
		return assPayDetailMapper.queryBillNo(mapVo);
	}
	
	
}
