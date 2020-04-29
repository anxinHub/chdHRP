/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.ass.serviceImpl.bill.back;

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
import com.chd.hrp.ass.dao.bill.back.AssBackBillDetailMapper;
import com.chd.hrp.ass.dao.bill.back.AssBackBillMainMapper;
import com.chd.hrp.ass.dao.bill.back.AssBackBillStageMapper;
import com.chd.hrp.ass.dao.pay.AssPayStageGeneralMapper;
import com.chd.hrp.ass.dao.pay.AssPayStageHouseMapper;
import com.chd.hrp.ass.dao.pay.AssPayStageInassetsMapper;
import com.chd.hrp.ass.dao.pay.AssPayStageLandMapper;
import com.chd.hrp.ass.dao.pay.AssPayStageOtherMapper;
import com.chd.hrp.ass.dao.pay.AssPayStageSpecialMapper;
import com.chd.hrp.ass.entity.bill.back.AssBackBillDetail;
import com.chd.hrp.ass.service.bill.back.AssBackBillDetailService;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Description:
 * 红冲发票明细表
 * @Table:
 * ASS_BACK_BILL_DETAIL
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 


@Service("assBackBillDetailService")
public class AssBackBillDetailServiceImpl implements AssBackBillDetailService {

	private static Logger logger = Logger.getLogger(AssBackBillDetailServiceImpl.class);
	//引入DAO操作
	@Resource(name = "assBackBillDetailMapper")
	private final AssBackBillDetailMapper assBackBillDetailMapper = null;
    
	@Resource(name = "assBackBillStageMapper")
	private final AssBackBillStageMapper assBackBillStageMapper = null;
	
	@Resource(name = "assBackBillMainMapper")
	private final AssBackBillMainMapper assBackBillMainMapper = null;
	
	@Resource(name = "assPayStageSpecialMapper")
	private final AssPayStageSpecialMapper assPayStageSpecialMapper = null;
	
	@Resource(name = "assPayStageOtherMapper")
	private final AssPayStageOtherMapper assPayStageOtherMapper = null;
	
	@Resource(name = "assPayStageLandMapper")
	private final AssPayStageLandMapper assPayStageLandMapper = null;
	
	@Resource(name = "assPayStageInassetsMapper")
	private final AssPayStageInassetsMapper assPayStageInassetsMapper = null;
	
	@Resource(name = "assPayStageHouseMapper")
	private final AssPayStageHouseMapper assPayStageHouseMapper = null;
	
	@Resource(name = "assPayStageGeneralMapper")
	private final AssPayStageGeneralMapper assPayStageGeneralMapper = null;
    
	/**
	 * @Description 
	 * 添加发票明细表<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String add(Map<String,Object> entityMap)throws DataAccessException{
		
		//获取对象发票明细表
		AssBackBillDetail assBackBillDetail = queryByCode(entityMap);

		if (assBackBillDetail != null) {

			return "{\"error\":\"数据重复,请重新添加.\"}";

		}
		
		try {
			
			int state = assBackBillDetailMapper.add(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 add\"}";

		}
		
	}
	/**
	 * @Description 
	 * 批量添加发票明细表<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addBatch(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
			assBackBillDetailMapper.addBatch(entityList);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addBatch\"}";

		}
		
	}
	
		/**
	 * @Description 
	 * 更新发票明细表<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String update(Map<String,Object> entityMap)throws DataAccessException{
		
		try {
			
		  int state = assBackBillDetailMapper.update(entityMap);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 update\"}";

		}	
		
	}
	/**
	 * @Description 
	 * 批量更新发票明细表<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateBatch(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
		  assBackBillDetailMapper.updateBatch(entityList);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 updateBatch\"}";

		}	
		
	}
	/**
	 * @Description 
	 * 删除发票明细表<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
    public String delete(Map<String, Object> entityMap) throws DataAccessException {
    	
    try {
			
			int state = assBackBillDetailMapper.delete(entityMap);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 delete\"}";

		}	
		
  }
    
	/**
	 * @Description 
	 * 批量删除发票明细表<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String deleteBatch(List<Map<String,Object>> entityList)throws DataAccessException{
		try {
			Map<String, Object> mapVo = new HashMap<String, Object>();
			mapVo.put("group_id", entityList.get(0).get("group_id"));
			mapVo.put("hos_id", entityList.get(0).get("hos_id"));
			mapVo.put("copy_code", entityList.get(0).get("copy_code"));
			mapVo.put("bill_no", entityList.get(0).get("bill_no"));
			
			List<Map<String, Object>> stageList = assBackBillStageMapper.queryByBillNo(mapVo);
			
			for (Map<String, Object> detailVo : stageList) {
				
				detailVo.put("group_id",detailVo.get("group_id"));
				detailVo.put("hos_id",detailVo.get("hos_id"));
				detailVo.put("copy_code", detailVo.get("copy_code"));
				detailVo.put("bill_no", detailVo.get("bill_no"));
				detailVo.put("naturs_code", detailVo.get("naturs_code"));
				detailVo.put("ass_card_no", detailVo.get("ass_card_no"));
				detailVo.put("stage_no", detailVo.get("stage_no"));
		
    		}
			
			
			assBackBillStageMapper.deleteBatch(entityList);
			assBackBillDetailMapper.deleteBatch(entityList);
			
			List<AssBackBillDetail> detailList = (List<AssBackBillDetail>)assBackBillDetailMapper.query(mapVo);
			Double bill_money = 0.0;
			for(AssBackBillDetail temp : detailList){
				bill_money += temp.getBill_money();
			}
			mapVo.put("bill_money", bill_money);
			
			assBackBillMainMapper.updateBillMoney(mapVo);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\",\"bill_money\":\""+bill_money+"\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());

		}	
	}
	
	/**
	 * @Description 
	 * 添加发票明细表<BR> 
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
		//判断是否存在对象发票明细表
		Map<String, Object> mapVo=new HashMap<String, Object>();
		mapVo.put("group_id",entityMap.get("group_id"));
		mapVo.put("hos_id",entityMap.get("hos_id"));
    	mapVo.put("copy_code", entityMap.get("copy_code"));
    	mapVo.put("acct_year", entityMap.get("acct_year"));
		
		List<AssBackBillDetail> list = (List<AssBackBillDetail>)assBackBillDetailMapper.queryExists(mapVo);
		
		if (list.size()>0) {

			int state = assBackBillDetailMapper.update(entityMap);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		
		try {
			
			int state = assBackBillDetailMapper.add(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addOrUpdate\"}";

		}
		
	}
	/**
	 * @Description 
	 * 查询结果集发票明细表<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String query(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<AssBackBillDetail> list = (List<AssBackBillDetail>)assBackBillDetailMapper.query(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<AssBackBillDetail> list = (List<AssBackBillDetail>)assBackBillDetailMapper.query(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
		
	}
	
	/**
	 * @Description 
	 * 获取对象发票明细表<BR> 
	 * @param  entityMap<BR>
	 *  参数为主键
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public <T> T queryByCode(Map<String,Object> entityMap)throws DataAccessException{
		return assBackBillDetailMapper.queryByCode(entityMap);
	}
	
	/**
	 * @Description 
	 * 获取发票明细表<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return AssBillDetail
	 * @throws DataAccessException
	*/
	@Override
	public <T> T queryByUniqueness(Map<String,Object> entityMap)throws DataAccessException{
		return assBackBillDetailMapper.queryByUniqueness(entityMap);
	}
	
	/**
	 * @Description 
	 * 获取发票明细表<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return List<AssBillDetail>
	 * @throws DataAccessException
	*/
	@Override
	public List<?> queryExists(Map<String,Object> entityMap)throws DataAccessException{
		return assBackBillDetailMapper.queryExists(entityMap);
	}
	@Override
	public List<Map<String, Object>> queryByAssBillNo(Map<String, Object> entityMap) throws DataAccessException {
		return assBackBillDetailMapper.queryByBillNo(entityMap);
	}
	@Override
	public List<Map<String, Object>> queryByAll(Map<String, Object> entityMap) throws DataAccessException {
		return assBackBillDetailMapper.queryByAll(entityMap);
	}
	
}
