/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.ass.serviceImpl.pay.back;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import com.chd.base.SessionManager;
import com.chd.base.SysPage;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.base.util.DateUtil;
import com.chd.hrp.ass.dao.HrpAssSelectMapper;
import com.chd.hrp.ass.dao.bill.back.AssBackBillDetailMapper;
import com.chd.hrp.ass.dao.bill.back.AssBackBillMainMapper;
import com.chd.hrp.ass.dao.bill.back.AssBackBillStageMapper;
import com.chd.hrp.ass.dao.pay.AssPayStageGeneralMapper;
import com.chd.hrp.ass.dao.pay.AssPayStageHouseMapper;
import com.chd.hrp.ass.dao.pay.AssPayStageInassetsMapper;
import com.chd.hrp.ass.dao.pay.AssPayStageLandMapper;
import com.chd.hrp.ass.dao.pay.AssPayStageOtherMapper;
import com.chd.hrp.ass.dao.pay.AssPayStageSpecialMapper;
import com.chd.hrp.ass.dao.pay.back.AssBackPayDetailMapper;
import com.chd.hrp.ass.dao.pay.back.AssBackPayMainMapper;
import com.chd.hrp.ass.dao.pay.back.AssBackPayStageMapper;
import com.chd.hrp.ass.dao.resource.AssResourceGeneralMapper;
import com.chd.hrp.ass.dao.resource.AssResourceHouseMapper;
import com.chd.hrp.ass.dao.resource.AssResourceInassetsMapper;
import com.chd.hrp.ass.dao.resource.AssResourceLandMapper;
import com.chd.hrp.ass.dao.resource.AssResourceOtherMapper;
import com.chd.hrp.ass.dao.resource.AssResourceSpecialMapper;
import com.chd.hrp.ass.entity.bill.back.AssBackBillMain;
import com.chd.hrp.ass.entity.pay.back.AssBackPayMain;
import com.chd.hrp.ass.service.base.AssBaseService;
import com.chd.hrp.ass.service.pay.back.AssBackPayMainService;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Description:
 * tabledesc
 * @Table:
 * ASS_BACK_PAY_MAIN
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

 
@Service("assBackPayMainService")
public class AssBackPayMainServiceImpl implements AssBackPayMainService {

	private static Logger logger = Logger.getLogger(AssBackPayMainServiceImpl.class);
	//引入DAO操作
	@Resource(name = "assBackPayMainMapper")
	private final AssBackPayMainMapper assBackPayMainMapper = null;
	
	@Resource(name = "assBackPayStageMapper")
	private final AssBackPayStageMapper assBackPayStageMapper = null;
	
	@Resource(name = "assBackPayDetailMapper")
	private final AssBackPayDetailMapper assBackPayDetailMapper = null;
	
	@Resource(name = "assBackBillStageMapper")
	private final AssBackBillStageMapper assBackBillStageMapper = null;

	@Resource(name = "assBackBillDetailMapper")
	private final AssBackBillDetailMapper assBackBillDetailMapper = null;
	
	@Resource(name = "assBackBillMainMapper")
	private final AssBackBillMainMapper assBackBillMainMapper = null;
	
	@Resource(name = "assBaseService")
	private final AssBaseService assBaseService = null;
	
	@Resource(name = "hrpAssSelectMapper")
	private final HrpAssSelectMapper hrpAssSelectMapper = null;
	
	@Resource(name = "assPayStageGeneralMapper")
	private final AssPayStageGeneralMapper assPayStageGeneralMapper = null;

	@Resource(name = "assPayStageHouseMapper")
	private final AssPayStageHouseMapper assPayStageHouseMapper = null;

	@Resource(name = "assPayStageInassetsMapper")
	private final AssPayStageInassetsMapper assPayStageInassetsMapper = null;

	@Resource(name = "assPayStageLandMapper")
	private final AssPayStageLandMapper assPayStageLandMapper = null;

	@Resource(name = "assPayStageOtherMapper")
	private final AssPayStageOtherMapper assPayStageOtherMapper = null;

	@Resource(name = "assPayStageSpecialMapper")
	private final AssPayStageSpecialMapper assPayStageSpecialMapper = null;

	@Resource(name = "assResourceGeneralMapper")
	private final AssResourceGeneralMapper assResourceGeneralMapper = null;

	@Resource(name = "assResourceHouseMapper")
	private final AssResourceHouseMapper assResourceHouseMapper = null;

	@Resource(name = "assResourceInassetsMapper")
	private final AssResourceInassetsMapper assResourceInassetsMapper = null;

	@Resource(name = "assResourceLandMapper")
	private final AssResourceLandMapper assResourceLandMapper = null;

	@Resource(name = "assResourceOtherMapper")
	private final AssResourceOtherMapper assResourceOtherMapper = null;

	@Resource(name = "assResourceSpecialMapper")
	private final AssResourceSpecialMapper assResourceSpecialMapper = null;
    
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
		AssBackPayMain assBackPayMain = queryByCode(entityMap);

		if (assBackPayMain != null) {

			return "{\"error\":\"数据重复,请重新添加.\"}";

		}
		
		try {
			
			int state = assBackPayMainMapper.add(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 add\"}";

		}
		
	}
	/**
	 * 资产退款打印
	 */
	@Override
	public Map<String,Object> queryAssBackPayDY(Map<String, Object> map)throws DataAccessException {
		
		try{
			Map<String,Object> reMap=new HashMap<String,Object>();
			WebApplicationContext context = ContextLoader.getCurrentWebApplicationContext();
			AssBackPayMainMapper assBackPayMainMapper = (AssBackPayMainMapper)context.getBean("assBackPayMainMapper");
			if("1".equals(String.valueOf(map.get("p_num")))){
				// 退款打印模板主表
				List<Map<String,Object>> mainList=assBackPayMainMapper.queryAssBackPayBatch(map);
						
				//退款打印模板从表
				List<Map<String,Object>> detailList=assBackPayMainMapper.queryAssBackPay_detail(map);
				
				reMap.put("main", mainList);
				reMap.put("detail", detailList);
			}else{
				Map<String,Object> mainList=assBackPayMainMapper.querAssBackPayByPrint(map);
				
				//退款打印模板从表
				List<Map<String,Object>> detailList=assBackPayMainMapper.queryAssBackPay_detail(map);
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
	 * 批量添加tabledesc<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addBatch(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
			assBackPayMainMapper.addBatch(entityList);
			
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
			
		  int state = assBackPayMainMapper.update(entityMap);
			
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
			
		  assBackPayMainMapper.updateBatch(entityList);
			
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
			
			int state = assBackPayMainMapper.delete(entityMap);
			
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
			assBackPayStageMapper.deleteBatch(entityList);
			assBackPayDetailMapper.deleteBatch(entityList);
			assBackPayMainMapper.deleteBatch(entityList);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());

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
		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();
		Map<String, Object> mapVo=new HashMap<String, Object>();
		mapVo.put("group_id",entityMap.get("group_id"));
		mapVo.put("hos_id",entityMap.get("hos_id"));
    	mapVo.put("copy_code", entityMap.get("copy_code"));
    	mapVo.put("pay_no", entityMap.get("pay_no"));
		
		//通过供应商检索卡片是否存在
		Map<String, Object> vVenMap =new HashMap<String, Object>();
		vVenMap.put("group_id",entityMap.get("group_id"));
		vVenMap.put("hos_id",entityMap.get("hos_id"));
    	vVenMap.put("copy_code", entityMap.get("copy_code"));
		vVenMap.put("ven_id", entityMap.get("ven_id"));
		vVenMap.put("ven_no", entityMap.get("ven_no"));
		
		
		entityMap.put("state", "0");
    	if(entityMap.get("create_date") != null && !"".equals(entityMap.get("create_date"))){
			entityMap.put("year", entityMap.get("create_date").toString().substring(0,4));
			entityMap.put("month", entityMap.get("create_date").toString().substring(5,7));
		}
    	
    	entityMap.put("bill_table", "ASS_BACK_PAY_MAIN");
		List<AssBackPayMain> list = (List<AssBackPayMain>)assBackPayMainMapper.queryExists(mapVo);
		try {
			if (list.size()>0) {
				assBackPayMainMapper.update(entityMap);
			}else{
				String pay_no = assBaseService.getBillNOSeqNo(entityMap);
				entityMap.put("pay_no", pay_no);
				assBackPayMainMapper.add(entityMap);
				assBaseService.updateAssBillNoMaxNo(entityMap);
			}
			List<Map> detail = ChdJson.fromJsonAsList(Map.class, entityMap.get("ParamVo").toString());
			
			List<Map<String, Object>> payStageList = assBackPayDetailMapper.queryByPayNo(mapVo);
			
			List<Map<String, Object>> delPayStageList = new ArrayList<Map<String,Object>>();
			for (Map<String, Object> det : payStageList) {
				boolean falg = false;
				for (Map<String, Object> temp : detail) {
					if (temp.get("bill_no") == null || "".equals(temp.get("bill_no"))) {
						continue;
					}
					if (det.get("bill_no").toString().equals(temp.get("bill_no").toString())) {
						falg = true;
						break;
					}
				}
				if (falg == false) {
					delPayStageList.add(det);
				}
			}
			
			/**
			 * 如果更换发票，先删除支付方式
			 */
			if(delPayStageList.size() > 0){
				assBackPayStageMapper.deleteBatch(delPayStageList);
			}
			
			boolean flag1 = true;
			Double pay_money = 0.0;
			for (Map<String, Object> detailVo : detail) {
				if (detailVo.get("bill_no") == null || "".equals(detailVo.get("bill_no"))) {
					continue;
				}
				
				detailVo.put("group_id",entityMap.get("group_id"));
				detailVo.put("hos_id",entityMap.get("hos_id"));
				detailVo.put("copy_code", entityMap.get("copy_code"));
				detailVo.put("pay_no", entityMap.get("pay_no"));
				detailVo.put("bill_no", detailVo.get("bill_no"));
				vVenMap.put("bill_no", detailVo.get("bill_no"));
				
				Integer vVen = hrpAssSelectMapper.queryBackBillDetailExists(vVenMap);
				if(vVen == 0){
					flag1 = false;
					break;
				}
				if(detailVo.get("bill_money") != null && !detailVo.get("bill_money").equals("")){
					detailVo.put("bill_money",detailVo.get("bill_money")+"");
					pay_money = pay_money + Double.parseDouble(detailVo.get("bill_money").toString());
				}else{
					detailVo.put("bill_money","0");
				}
				
				if(detailVo.get("note") != null && !detailVo.get("note").equals("")){
					detailVo.put("note",detailVo.get("note"));
				}else{
					detailVo.put("note","");
				}
				
				listVo.add(detailVo);
			}
			
			if(!flag1){
				TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
				return "{\"warn\":\"存在非本供应商的发票不能保存.\"}";
			}
			
			assBackPayDetailMapper.delete(entityMap);
			assBackPayDetailMapper.addBatch(listVo);
			
			entityMap.put("pay_money", pay_money);
			assBackPayMainMapper.updatePayMoney(entityMap);
			
			return "{\"msg\":\"保存成功.\",\"state\":\"true\",\"pay_no\":\""+entityMap.get("pay_no")+"\",\"pay_money\":\""+pay_money+"\"}";

		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());

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
			
			List<AssBackPayMain> list = (List<AssBackPayMain>)assBackPayMainMapper.query(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<AssBackPayMain> list = (List<AssBackPayMain>)assBackPayMainMapper.query(entityMap, rowBounds);
			
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
		return assBackPayMainMapper.queryByCode(entityMap);
	}
	
	/**
	 * @Description 
	 * 获取tabledesc<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return AssBackPayMain
	 * @throws DataAccessException
	*/
	@Override
	public <T> T queryByUniqueness(Map<String,Object> entityMap)throws DataAccessException{
		return assBackPayMainMapper.queryByUniqueness(entityMap);
	}
	
	/**
	 * @Description 
	 * 获取tabledesc<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return List<AssBackPayMain>
	 * @throws DataAccessException
	*/
	@Override
	public List<?> queryExists(Map<String,Object> entityMap)throws DataAccessException{
		return assBackPayMainMapper.queryExists(entityMap);
	}
	@Override
	public String updateBackPayConfirm(List<Map<String, Object>> entityMap) {
		try {
			List<Map<String, Object>> listSpecial = new ArrayList<Map<String, Object>>();
			List<Map<String, Object>> listGeneral = new ArrayList<Map<String, Object>>();
			List<Map<String, Object>> listHouse = new ArrayList<Map<String, Object>>();
			List<Map<String, Object>> listOther = new ArrayList<Map<String, Object>>();
			List<Map<String, Object>> listInassets = new ArrayList<Map<String, Object>>();
			List<Map<String, Object>> listLand = new ArrayList<Map<String, Object>>();

			List<Map<String, Object>> listResourceSpecial = new ArrayList<Map<String, Object>>();
			List<Map<String, Object>> listResourceGeneral = new ArrayList<Map<String, Object>>();
			List<Map<String, Object>> listResourceHouse = new ArrayList<Map<String, Object>>();
			List<Map<String, Object>> listResourceOther = new ArrayList<Map<String, Object>>();
			List<Map<String, Object>> listResourceInassets = new ArrayList<Map<String, Object>>();
			List<Map<String, Object>> listResourceLand = new ArrayList<Map<String, Object>>();

			for (Map<String, Object> mainMap : entityMap) {
				List<Map<String, Object>> detailList = assBackPayDetailMapper.queryByPayNo(mainMap);// 明细数据获取发票单据

				/**
				 * 循环付款明细 通过付款明细找到发票明细
				 */
				for (Map<String, Object> payDetailMap : detailList) {

					List<Map<String, Object>> billDetailList = assBackBillDetailMapper.queryByBillNo(payDetailMap);// 发票明细数据

					/**
					 * 循环发票明细数据
					 * 
					 */
					for (Map<String, Object> billDetail : billDetailList) {
						List<Map<String, Object>> cardResource = null;
						if (billDetail.get("naturs_code").toString().equals("01")) {
							cardResource = assResourceHouseMapper.queryByAssCardNoMap(billDetail);// 卡片资金来源
							
						}
						if (billDetail.get("naturs_code").toString().equals("02")) {
							cardResource = assResourceSpecialMapper.queryByAssCardNoMap(billDetail);// 卡片资金来源
							
						}
						if (billDetail.get("naturs_code").toString().equals("03")) {
							cardResource = assResourceGeneralMapper.queryByAssCardNoMap(billDetail);// 卡片资金来源

						}
						if (billDetail.get("naturs_code").toString().equals("04")) {
							cardResource = assResourceOtherMapper.queryByAssCardNoMap(billDetail);// 卡片资金来源

						}
						if (billDetail.get("naturs_code").toString().equals("05")) {
							cardResource = assResourceInassetsMapper.queryByAssCardNoMap(billDetail);// 卡片资金来源

						}
						if (billDetail.get("naturs_code").toString().equals("06")) {
							cardResource = assResourceLandMapper.queryByAssCardNoMap(billDetail);// 卡片资金来源

						}

						billDetail.put("pay_no", mainMap.get("pay_no"));
						Double bill_money = Math.abs(Double.valueOf(billDetail.get("bill_money").toString()));// 发票明细金额
						List<Map<String, Object>> payList = assBackPayStageMapper.queryPayStageByBillNoSource(billDetail);// 付款明细支付方式
						for (Map<String, Object> vresource : payList) {// 循环支付方式带资金来源冗余
							if (bill_money > 0) {
								boolean falg = false;
								Double pay_money = 0.0;
								String ass_card_no = "";
								for (Map<String, Object> vcardsource : cardResource) {// 循环卡片资金来源
									if (vresource.get("source_id").equals(vcardsource.get("source_id"))) {
										pay_money = Double.parseDouble(vcardsource.get("pay_money").toString());
										ass_card_no = vcardsource.get("ass_card_no").toString();
										falg = true;
										break;
									}
								}
								if (falg == false && bill_money > 0) {
									return "{\"warn\":\"支付方式与卡片资金来源对应关系不匹配! \"}";
								}
								if (pay_money > 0) {
									
									Map<String, Object> sourceMap = new HashMap<String, Object>();
									sourceMap.put("group_id", SessionManager.getGroupId());
									sourceMap.put("hos_id", SessionManager.getHosId());
									sourceMap.put("copy_code", SessionManager.getCopyCode());

									if (bill_money < pay_money) {
										sourceMap.put("pay_money", "-"+ bill_money + "");
										bill_money = bill_money - bill_money;
									} else {
										sourceMap.put("pay_money", "-" + bill_money * (pay_money / bill_money) + "");
										bill_money = bill_money - (bill_money * (pay_money / bill_money));
									}

									sourceMap.put("ass_card_no", ass_card_no);
									
									sourceMap.put("source_id", vresource.get("source_id"));

									if (billDetail.get("naturs_code").toString().equals("01")) {
										listResourceHouse.add(sourceMap);
									}
									if (billDetail.get("naturs_code").toString().equals("02")) {
										listResourceSpecial.add(sourceMap);
									}
									if (billDetail.get("naturs_code").toString().equals("03")) {
										listResourceGeneral.add(sourceMap);
									}
									if (billDetail.get("naturs_code").toString().equals("04")) {
										listResourceOther.add(sourceMap);
									}
									if (billDetail.get("naturs_code").toString().equals("05")) {
										listResourceInassets.add(sourceMap);
									}
									if (billDetail.get("naturs_code").toString().equals("06")) {
										listResourceLand.add(sourceMap);
									}
								}
							}
						}

					}

					/**
					 * 分期付款处理 循环获取发票分期付款，更新到卡片对应的分期付款中
					 */
					List<Map<String, Object>> billStageList = assBackBillStageMapper.queryByBillNo(payDetailMap);// 通过单据查找所对应发票的分期付款记录
					for (Map<String, Object> stage : billStageList) {
						if (stage.get("naturs_code").toString().equals("01")) {
							listHouse.add(stage);
						}
						if (stage.get("naturs_code").toString().equals("02")) {
							listSpecial.add(stage);
						}
						if (stage.get("naturs_code").toString().equals("03")) {
							listGeneral.add(stage);
						}
						if (stage.get("naturs_code").toString().equals("04")) {
							listOther.add(stage);
						}
						if (stage.get("naturs_code").toString().equals("05")) {
							listInassets.add(stage);
						}
						if (stage.get("naturs_code").toString().equals("06")) {
							listLand.add(stage);
						}
					}

				}

			}

			/**
			 * 更新资金来源
			 */
			if (listResourceHouse.size() > 0) {
				assResourceHouseMapper.updateBatchByPay(listResourceHouse);
			}
			if (listResourceSpecial.size() > 0) {
				assResourceSpecialMapper.updateBatchByPay(listResourceSpecial);
			}
			if (listResourceGeneral.size() > 0) {
				assResourceGeneralMapper.updateBatchByPay(listResourceGeneral);
			}
			if (listResourceOther.size() > 0) {
				assResourceOtherMapper.updateBatchByPay(listResourceOther);
			}
			if (listResourceInassets.size() > 0) {
				assResourceInassetsMapper.updateBatchByPay(listResourceInassets);
			}
			if (listResourceLand.size() > 0) {
				assResourceLandMapper.updateBatchByPay(listResourceLand);
			}

			
			/**
			 * 更新分期付款
			 */
			if (listHouse.size() > 0) {
				assPayStageHouseMapper.updateBatchPayMoney(listHouse);
			}
			if (listSpecial.size() > 0) {
				assPayStageSpecialMapper.updateBatchPayMoney(listSpecial);
			}
			if (listGeneral.size() > 0) {
				assPayStageGeneralMapper.updateBatchPayMoney(listGeneral);
			}
			if (listOther.size() > 0) {
				assPayStageOtherMapper.updateBatchPayMoney(listOther);
			}
			if (listInassets.size() > 0) {
				assPayStageInassetsMapper.updateBatchPayMoney(listInassets);
			}
			if (listLand.size() > 0) {
				assPayStageLandMapper.updateBatchPayMoney(listLand);
			}

			/**
			 * 更新主表状态
			 */
			assBackPayMainMapper.updateConfirm(entityMap);

			return "{\"msg\":\"确认成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}
	}
	
	/**
	 *引入 
	 */
	@Override
	public String initAssBackPaySave(Map<String, Object> entityMap)
			throws DataAccessException {
		
		entityMap.put("bill_table", "ASS_BACK_PAY_MAIN");
		//List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> listVo2 = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> listVo3 = new ArrayList<Map<String, Object>>();
		try {
			//List<AssBackBillDetail> assPayMain = assBackBillDetailMapper.queryByImportPay(entityMap);
			List<AssBackBillMain> assPayMain = assBackBillMainMapper.queryByImportPay(entityMap);
			//String[] ids = entityMap.get("ven_id").toString().split("@");		
			
			Double bill_money = 0.0;
			
			
			// 主表增加
			entityMap.put("bill_table", "ASS_BACK_PAY_MAIN");
			String cr_date = DateUtil.dateFormat(entityMap.get("create_date"), "yyyy-MM-dd");
			entityMap.put("year", cr_date.substring(0, 4));
			entityMap.put("month", cr_date.substring(5, 7));
			String pay_no = assBaseService.getBillNOSeqNo(entityMap);
			entityMap.put("pay_no", pay_no);
			entityMap.put("state", "0");
			entityMap.put("note", "引入退款发票登记");
			entityMap.put("ven_id", entityMap.get("ven_id"));
			entityMap.put("ven_no", entityMap.get("ven_no"));
			entityMap.put("create_emp", SessionManager.getUserId());
			entityMap.put("create_date", DateUtil.dateToString(new Date(), "yyyy-MM-dd"));
			assBackPayMainMapper.add(entityMap);
			assBaseService.updateAssBillNoMaxNo(entityMap);
			// 明细表增加
			
			for (AssBackBillMain assPayDetailSpecial : assPayMain) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("group_id", assPayDetailSpecial.getGroup_id());
				map.put("hos_id", assPayDetailSpecial.getHos_id());
				map.put("copy_code", assPayDetailSpecial.getCopy_code());
				map.put("pay_no", pay_no);
				map.put("bill_no", assPayDetailSpecial.getBill_no());
				map.put("bill_money", assPayDetailSpecial.getBill_money());
				map.put("note", assPayDetailSpecial.getNote());
				
				listVo2.add(map);
				
				Map<String, Object> payStage = new HashMap<String, Object>();
				payStage.put("group_id", entityMap.get("group_id"));
				payStage.put("hos_id", entityMap.get("hos_id"));
				payStage.put("copy_code", entityMap.get("copy_code"));
				payStage.put("pay_no", pay_no);
				payStage.put("bill_no", assPayDetailSpecial.getBill_no());
				payStage.put("pay_code", "01");
				payStage.put("pay_money", assPayDetailSpecial.getBill_money());
				payStage.put("note", "");
				listVo3.add(payStage);
			}
			assBackPayDetailMapper.addBatch(listVo2);


			assBackPayStageMapper.addBatch(listVo3);
			
			
			return "{\"msg\":\"引入成功.\",\"state\":\"true\",\"update_para\":\"" + entityMap.get("group_id") + "|"
					+ entityMap.get("hos_id") + "|" + entityMap.get("copy_code") + "|" + pay_no  + "|1\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}
	}
	@Override
	public List<String> queryAssBackPayState(Map<String, Object> mapVo)
			throws DataAccessException {
	
		return assBackPayMainMapper.queryAssBackPayState(mapVo);
	}
	
}
