/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.ass.serviceImpl.bill;

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
import com.chd.hrp.ass.dao.bill.AssBillDetailMapper;
import com.chd.hrp.ass.dao.bill.AssBillMainMapper;
import com.chd.hrp.ass.dao.bill.AssBillStageMapper;
import com.chd.hrp.ass.dao.pay.AssPayStageGeneralMapper;
import com.chd.hrp.ass.dao.pay.AssPayStageHouseMapper;
import com.chd.hrp.ass.dao.pay.AssPayStageInassetsMapper;
import com.chd.hrp.ass.dao.pay.AssPayStageLandMapper;
import com.chd.hrp.ass.dao.pay.AssPayStageOtherMapper;
import com.chd.hrp.ass.dao.pay.AssPayStageSpecialMapper;
import com.chd.hrp.ass.entity.bill.AssBillDetail;
import com.chd.hrp.ass.entity.bill.AssBillStage;
import com.chd.hrp.ass.service.bill.AssBillStageService;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Description:
 * 051601 发票卡片分期付款
 * @Table:
 * ASS_BILL_STAGE
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 


@Service("assBillStageService")
public class AssBillStageServiceImpl implements AssBillStageService {

	private static Logger logger = Logger.getLogger(AssBillStageServiceImpl.class);
	//引入DAO操作
	@Resource(name = "assBillStageMapper")
	private final AssBillStageMapper assBillStageMapper = null;
	
	@Resource(name = "assBillDetailMapper")
	private final AssBillDetailMapper assBillDetailMapper = null;
    
	@Resource(name = "assBillMainMapper")
	private final AssBillMainMapper assBillMainMapper = null;
	
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
	 * 添加051601 发票卡片分期付款<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String add(Map<String,Object> entityMap)throws DataAccessException{
		
		//获取对象051601 发票卡片分期付款
		AssBillStage assBillStage = queryByCode(entityMap);

		if (assBillStage != null) {

			return "{\"error\":\"数据重复,请重新添加.\"}";

		}
		
		try {
			
			int state = assBillStageMapper.add(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 add\"}";

		}
		
	}
	/**
	 * @Description 
	 * 批量添加051601 发票卡片分期付款<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addBatch(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
			assBillStageMapper.addBatch(entityList);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addBatch\"}";

		}
		
	}
	
		/**
	 * @Description 
	 * 更新051601 发票卡片分期付款<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String update(Map<String,Object> entityMap)throws DataAccessException{
		
		try {
			
		  int state = assBillStageMapper.update(entityMap);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 update\"}";

		}	
		
	}
	/**
	 * @Description 
	 * 批量更新051601 发票卡片分期付款<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateBatch(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
		  assBillStageMapper.updateBatch(entityList);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 updateBatch\"}";

		}	
		
	}
	/**
	 * @Description 
	 * 删除051601 发票卡片分期付款<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
    public String delete(Map<String, Object> entityMap) throws DataAccessException {
    	
    try {
			
			int state = assBillStageMapper.delete(entityMap);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 delete\"}";

		}	
		
  }
    
	/**
	 * @Description 
	 * 批量删除051601 发票卡片分期付款<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String deleteBatch(List<Map<String,Object>> entityList)throws DataAccessException{
		try {
			
			assBillStageMapper.deleteBatch(entityList);
			
			Map<String, Object> mapVo = new HashMap<String, Object>();
			mapVo.put("group_id", entityList.get(0).get("group_id"));
			mapVo.put("hos_id", entityList.get(0).get("hos_id"));
			mapVo.put("copy_code", entityList.get(0).get("copy_code"));
			mapVo.put("bill_no", entityList.get(0).get("bill_no"));
			mapVo.put("naturs_code", entityList.get(0).get("naturs_code"));
			mapVo.put("ass_card_no", entityList.get(0).get("ass_card_no"));
			List<AssBillStage> billStageList = (List<AssBillStage>)assBillStageMapper.query(mapVo);
			Double bill_money = 0.0;
			for(AssBillStage assBillStage : billStageList){
				bill_money = bill_money + assBillStage.getPay_plan_money();
			}
			mapVo.put("bill_money", bill_money + "");
			assBillDetailMapper.updateBillMoney(mapVo);
			
			Map<String, Object> detMapVo = new HashMap<String, Object>();
			detMapVo.put("group_id", entityList.get(0).get("group_id"));
			detMapVo.put("hos_id", entityList.get(0).get("hos_id"));
			detMapVo.put("copy_code", entityList.get(0).get("copy_code"));
			detMapVo.put("bill_no", entityList.get(0).get("bill_no"));
			
			List<AssBillDetail> detailList = (List<AssBillDetail>)assBillDetailMapper.query(detMapVo);
			Double main_bill_money = 0.0;
			for(AssBillDetail temp : detailList){
				main_bill_money += temp.getBill_money();
			}
			detMapVo.put("bill_money", main_bill_money + "");
			
			assBillMainMapper.updateBillMoney(detMapVo);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\",\"bill_money\":\""+bill_money+"\",\"main_bill_money\":\""+main_bill_money+"\"}";

		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}	
	}
	
	/**
	 * @Description 
	 * 添加051601 发票卡片分期付款<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addOrUpdate(Map<String,Object> entityMap)throws DataAccessException{
		List<Map<String, Object>> listVo = new ArrayList<Map<String,Object>>();
    	try{	
    		List<Map> detail = ChdJson.fromJsonAsList(Map.class, entityMap.get("ParamVo").toString());
    		assBillStageMapper.delete(entityMap);
    		Integer stage_no = assBillStageMapper.queryBillStageMaxNo(entityMap);
    		for (Map<String, Object> detailVo : detail) {
				if (detailVo.get("stage_name") == null || "".equals(detailVo.get("stage_name"))) {
					continue;
				}
				
				detailVo.put("group_id",entityMap.get("group_id"));
				detailVo.put("hos_id",entityMap.get("hos_id"));
				detailVo.put("copy_code", entityMap.get("copy_code"));
				detailVo.put("bill_no", entityMap.get("bill_no"));
				detailVo.put("naturs_code", entityMap.get("naturs_code"));
				detailVo.put("ass_card_no", entityMap.get("ass_card_no"));
				detailVo.put("stage_no", stage_no);
				detailVo.put("stage_name", detailVo.get("stage_name"));
				
				if(detailVo.get("pay_plan_money") != null && !detailVo.get("pay_plan_money").equals("")){
					detailVo.put("pay_plan_money", detailVo.get("pay_plan_money")+"");
					//detailVo.put("bill_money", detailVo.get("pay_plan_money")+"");
				}else{
					detailVo.put("pay_plan_money", "0");
					//detailVo.put("bill_money", "0");
				}
				stage_no++;
				
				listVo.add(detailVo);
    		}
    		assBillStageMapper.addBatch(listVo);
    		
    		
    		
    		Map<String, Object> mapVo = new HashMap<String, Object>();
			mapVo.put("group_id", entityMap.get("group_id"));
			mapVo.put("hos_id", entityMap.get("hos_id"));
			mapVo.put("copy_code", entityMap.get("copy_code"));
			mapVo.put("bill_no", entityMap.get("bill_no"));
			mapVo.put("naturs_code", entityMap.get("naturs_code"));
			mapVo.put("ass_card_no", entityMap.get("ass_card_no"));
			List<AssBillStage> billStageList = (List<AssBillStage>)assBillStageMapper.query(mapVo);
			Double bill_money = 0.0;
			for(AssBillStage assBillStage : billStageList){
				bill_money = bill_money + assBillStage.getPay_plan_money();
			}
			mapVo.put("bill_money", bill_money + "");
			assBillDetailMapper.updateBillMoney(mapVo);
			
			Map<String, Object> detMapVo = new HashMap<String, Object>();
			detMapVo.put("group_id", entityMap.get("group_id"));
			detMapVo.put("hos_id", entityMap.get("hos_id"));
			detMapVo.put("copy_code", entityMap.get("copy_code"));
			detMapVo.put("bill_no", entityMap.get("bill_no"));
			
			List<AssBillDetail> detailList = (List<AssBillDetail>)assBillDetailMapper.query(detMapVo);
			Double main_bill_money = 0.0;
			for(AssBillDetail temp : detailList){
				main_bill_money += temp.getBill_money();
			}
			detMapVo.put("bill_money", main_bill_money + "");
			
			assBillMainMapper.updateBillMoney(detMapVo);
    		
    		return "{\"msg\":\"保存成功.\",\"state\":\"true\",\"bill_money\":\""+bill_money+"\",\"main_bill_money\":\""+main_bill_money+"\"}";
		}catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}
		
	}
	/**
	 * @Description 
	 * 查询结果集051601 发票卡片分期付款<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String query(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<AssBillStage> list = (List<AssBillStage>)assBillStageMapper.query(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<AssBillStage> list = (List<AssBillStage>)assBillStageMapper.query(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
		
	}
	
	/**
	 * @Description 
	 * 获取对象051601 发票卡片分期付款<BR> 
	 * @param  entityMap<BR>
	 *  参数为主键
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public <T> T queryByCode(Map<String,Object> entityMap)throws DataAccessException{
		return assBillStageMapper.queryByCode(entityMap);
	}
	
	/**
	 * @Description 
	 * 获取051601 发票卡片分期付款<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return AssBillStage
	 * @throws DataAccessException
	*/
	@Override
	public <T> T queryByUniqueness(Map<String,Object> entityMap)throws DataAccessException{
		return assBillStageMapper.queryByUniqueness(entityMap);
	}
	
	/**
	 * @Description 
	 * 获取051601 发票卡片分期付款<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return List<AssBillStage>
	 * @throws DataAccessException
	*/
	@Override
	public List<?> queryExists(Map<String,Object> entityMap)throws DataAccessException{
		return assBillStageMapper.queryExists(entityMap);
	}
	@Override
	public List<Map<String, Object>> queryByAssBillNo(Map<String, Object> entityMap) throws DataAccessException {
		return assBillStageMapper.queryByBillNo(entityMap);
	}
	@Override
	public List<AssBillStage> queryByConfirmAssBill(Map<String, Object> entityMap)
			throws DataAccessException {
		return assBillStageMapper.queryByConfirmAssBill(entityMap);
	}
	
}
