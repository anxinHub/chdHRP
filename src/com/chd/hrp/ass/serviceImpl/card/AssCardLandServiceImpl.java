/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.ass.serviceImpl.card;

import java.math.BigDecimal;
import java.math.RoundingMode;
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

import com.chd.base.MyConfig;
import com.chd.base.SessionManager;
import com.chd.base.SysPage;
import com.chd.base.exception.SysException;
import com.chd.base.ftp.FtpUtil;
import com.chd.base.util.ChdJson;
import com.chd.base.util.DateUtil;
import com.chd.hrp.ass.dao.accessory.AssAccessoryLandMapper;
import com.chd.hrp.ass.dao.card.AssCardLandMapper;
import com.chd.hrp.ass.dao.check.land.AssChkInDetailLandMapper;
import com.chd.hrp.ass.dao.check.land.AssChkInMainLandMapper;
import com.chd.hrp.ass.dao.depre.acc.AssDepreAccLandMapper;
import com.chd.hrp.ass.dao.depre.manager.AssDepreManageLandMapper;
import com.chd.hrp.ass.dao.file.AssFileLandMapper;
import com.chd.hrp.ass.dao.in.AssInDetailLandMapper;
import com.chd.hrp.ass.dao.in.AssInMainLandMapper;
import com.chd.hrp.ass.dao.in.rest.AssInRestDetailLandMapper;
import com.chd.hrp.ass.dao.in.rest.AssInRestMainLandMapper;
import com.chd.hrp.ass.dao.pay.AssPayStageLandMapper;
import com.chd.hrp.ass.dao.photo.AssPhotoLandMapper;
import com.chd.hrp.ass.dao.prepay.AssPrePayMapper;
import com.chd.hrp.ass.dao.resource.AssResourceLandMapper;
import com.chd.hrp.ass.dao.share.AssShareDeptLandMapper;
import com.chd.hrp.ass.dao.share.AssShareDeptRLandMapper;
import com.chd.hrp.ass.entity.card.AssCardLand;
import com.chd.hrp.ass.entity.check.land.AssChkInMainLand;
import com.chd.hrp.ass.entity.file.AssFileLand;
import com.chd.hrp.ass.entity.in.AssInMainLand;
import com.chd.hrp.ass.entity.in.rest.AssInRestMainLand;
import com.chd.hrp.ass.entity.photo.AssPhotoLand;
import com.chd.hrp.ass.service.base.AssBaseService;
import com.chd.hrp.ass.service.card.AssCardLandService;
import com.chd.hrp.mat.dao.info.basic.MatPayTermMapper;
import com.chd.hrp.mat.entity.MatPayTerm;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Description:
 * 资产卡片维护_土地
 * @Table:
 * ASS_CARD_LAND
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

 
@Service("assCardLandService")
public class AssCardLandServiceImpl implements AssCardLandService { 

	private static Logger logger = Logger.getLogger(AssCardLandServiceImpl.class);
	//引入DAO操作
	@Resource(name = "assCardLandMapper")
	private final AssCardLandMapper assCardLandMapper = null;
	
	@Resource(name = "assResourceLandMapper")
	private final AssResourceLandMapper assResourceLandMapper = null;
	
	@Resource(name = "assShareDeptLandMapper")
	private final AssShareDeptLandMapper assShareDeptLandMapper = null;
	
	@Resource(name = "assShareDeptRLandMapper")
	private final AssShareDeptRLandMapper assShareDeptRLandMapper = null;
	
	@Resource(name = "assFileLandMapper")
	private final AssFileLandMapper assFileLandMapper = null;
	
	@Resource(name = "assPhotoLandMapper")
	private final AssPhotoLandMapper assPhotoLandMapper = null;
	
	@Resource(name = "assAccessoryLandMapper")
	private final AssAccessoryLandMapper assAccessoryLandMapper = null;
	
	@Resource(name = "assDepreAccLandMapper")
	private final AssDepreAccLandMapper assDepreAccLandMapper = null;
	
	@Resource(name = "assDepreManageLandMapper")
	private final AssDepreManageLandMapper assDepreManageLandMapper = null;
	
	@Resource(name = "assPayStageLandMapper")
	private final AssPayStageLandMapper assPayStageLandMapper = null;
	
	@Resource(name = "assBaseService")
	private final AssBaseService assBaseService = null;
	
	@Resource(name = "assPrePayMapper")
	private final AssPrePayMapper assPrePayMapper = null;

	@Resource(name = "matPayTermMapper")
	private final MatPayTermMapper matPayTermMapper = null;
	
	@Resource(name = "assInMainLandMapper")
	private AssInMainLandMapper  assInMainLandMapper = null;
	
	@Resource(name = "assInDetailLandMapper")
	private AssInDetailLandMapper  assInDetailLandMapper = null;
	
	@Resource(name = "assInRestMainLandMapper")
	private AssInRestMainLandMapper  assInRestMainLandMapper = null;
	
	@Resource(name = "assInRestDetailLandMapper")
	private AssInRestDetailLandMapper  assInRestDetailLandMapper = null;
	
	@Resource(name = "assChkInMainLandMapper")
	private AssChkInMainLandMapper  assChkInMainLandMapper = null;
	
	@Resource(name = "assChkInDetailLandMapper")
	private AssChkInDetailLandMapper  assChkInDetailLandMapper = null;
    
	/**
	 * @Description 
	 * 添加资产卡片维护_土地<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String add(Map<String,Object> entityMap)throws DataAccessException{
		
		try {
			if(entityMap.get("ven_id") == null || entityMap.get("ven_id").equals("") || entityMap.get("ven_id").equals("undefined")){
				entityMap.put("ven_id","");
				entityMap.put("ven_no","");
			}
			
			if(entityMap.get("fac_id") == null || entityMap.get("fac_id").equals("") || entityMap.get("fac_id").equals("undefined")){
				entityMap.put("fac_id","");
				entityMap.put("fac_no","");
			}
			
			if (entityMap.get("is_addin") !=null && entityMap.get("is_addin").toString().equals("true")) {
				return saveAssInStore(entityMap);
			}else{
				Map<String,Object> delMap = new HashMap<String, Object>();
				delMap.put("group_id", entityMap.get("group_id"));
				delMap.put("hos_id", entityMap.get("hos_id"));
				delMap.put("copy_code", entityMap.get("copy_code"));
				delMap.put("ass_card_no", entityMap.get("ass_card_no_old"));
				assShareDeptRLandMapper.delete(delMap);
				assShareDeptLandMapper.delete(delMap);
				assResourceLandMapper.delete(delMap);
				assFileLandMapper.delete(delMap);
				assPhotoLandMapper.delete(delMap);
				assAccessoryLandMapper.delete(delMap);
				assPayStageLandMapper.delete(delMap);
				assDepreAccLandMapper.delete(delMap);
				assDepreManageLandMapper.delete(delMap);
				assCardLandMapper.delete(delMap);
				
				assCardLandMapper.add(entityMap);
				
				Double price = Double.parseDouble(entityMap.get("price").toString());

				/**
				 * 通过资产ID和供应商ID查询 查询预付款核定 如果存在，取出资金来源数据插入卡片资金来源 ，插入分期付款 如果资产原值 -
				 * 预付余额 - 回冲金额 = 0 那么分期付款为一条数据 如果资产原值 - 预付余额 - 回冲金额 > 0那么分期付款产生两条数据
				 * 资产原值 - 预付余额 - 回冲金额 = 未付金额
				 */
				entityMap.put("naturs_code", "06");
				MatPayTerm matPayTerm = null;
				
				if(matPayTermMapper.queryPayTerm(entityMap) != null && matPayTermMapper.queryPayTerm(entityMap).size() > 0){
					matPayTerm = (MatPayTerm) matPayTermMapper.queryPayTerm(entityMap).get(0);
				}else{
					matPayTerm = new MatPayTerm();
					matPayTerm.setPay_term_id((long)1);
				}
				List<Map<String, Object>> prePayStageList = assPrePayMapper.queryByVenAndAss(entityMap);// 汇总的核定数据
				List<Map<String, Object>> prePaySourceList = assPrePayMapper.queryByVenAndAssSource(entityMap);//带有资金来源的核定数据

				List<Map<String, Object>> payStageListVo = new ArrayList<Map<String, Object>>();
				Double cur_money = 0.0;
				Double pay_money = 0.0;
				if(prePayStageList.size() > 0){
					cur_money = Double.parseDouble(prePayStageList.get(0).get("cur_money").toString());// 预付
					pay_money = Double.parseDouble(prePayStageList.get(0).get("pay_money") == null ? "0.0" :prePayStageList.get(0).get("pay_money").toString());// 回冲
					if(cur_money - pay_money == 0){
						Map<String, Object> payMap = new HashMap<String, Object>();
						Integer stage_no = assPayStageLandMapper.queryStageLandMaxNo(entityMap);
						payMap.put("group_id", entityMap.get("group_id"));
						payMap.put("hos_id", entityMap.get("hos_id"));
						payMap.put("copy_code", entityMap.get("copy_code"));
						payMap.put("ass_card_no", entityMap.get("ass_card_no"));
						payMap.put("stage_no", stage_no);
						payMap.put("stage_name", "一期款");
						payMap.put("pay_term_id", matPayTerm.getPay_term_id());// 付款条件
						payMap.put("pay_plan_date", DateUtil.dateToString(new Date(),"yyyy-MM-dd"));
						payMap.put("pay_plan_percent", 1);
						payMap.put("pay_plan_money", price);
						payMap.put("pay_money", "0");
						payMap.put("unpay_money", price);
						payMap.put("bill_money", "0");
						payMap.put("unbill_money", price);
						payMap.put("ven_id", entityMap.get("ven_id"));
						payMap.put("ven_no", entityMap.get("ven_no"));
						payMap.put("note", "");
						payMap.put("is_pre", "0");
						payStageListVo.add(payMap);
					}else{
						if ((price - (cur_money - pay_money)) == 0) {
							Map<String, Object> payMap = new HashMap<String, Object>();
							Integer stage_no = assPayStageLandMapper.queryStageLandMaxNo(entityMap);
							payMap.put("group_id", entityMap.get("group_id"));
							payMap.put("hos_id", entityMap.get("hos_id"));
							payMap.put("copy_code", entityMap.get("copy_code"));
							payMap.put("ass_card_no", entityMap.get("ass_card_no"));
							payMap.put("stage_no", stage_no);
							payMap.put("stage_name", "一期款");
							payMap.put("pay_term_id", matPayTerm.getPay_term_id());// 付款条件
							payMap.put("pay_plan_date", DateUtil.dateToString(new Date(),"yyyy-MM-dd"));
							payMap.put("pay_plan_percent", 1);
							payMap.put("pay_plan_money", price);
							payMap.put("pay_money", price);
							payMap.put("unpay_money", "0");
							payMap.put("bill_money", price);
							payMap.put("unbill_money", "0");
							payMap.put("ven_id", entityMap.get("ven_id"));
							payMap.put("ven_no", entityMap.get("ven_no"));
							payMap.put("note", "预付款");
							payMap.put("is_pre", "1");
							payStageListVo.add(payMap);
						}
						if ((price - (cur_money - pay_money)) > 0) {
							/**
							 * 第一条数据
							 */
							Map<String, Object> payMap = new HashMap<String, Object>();
							Integer stage_no1 = assPayStageLandMapper.queryStageLandMaxNo(entityMap);
							payMap.put("group_id", entityMap.get("group_id"));
							payMap.put("hos_id", entityMap.get("hos_id"));
							payMap.put("copy_code", entityMap.get("copy_code"));
							payMap.put("ass_card_no", entityMap.get("ass_card_no"));
							payMap.put("stage_no", stage_no1);
							payMap.put("stage_name", "一期款");
							payMap.put("pay_term_id", matPayTerm.getPay_term_id());// 付款条件
							payMap.put("pay_plan_date", DateUtil.dateToString(new Date(),"yyyy-MM-dd"));
							BigDecimal bg = new BigDecimal((cur_money - pay_money) / price).setScale(2, RoundingMode.UP);
							payMap.put("pay_plan_percent", bg.doubleValue() +"");
							payMap.put("pay_plan_money", cur_money - pay_money +"");
							payMap.put("pay_money", cur_money - pay_money +"");
							payMap.put("unpay_money", "0");
							payMap.put("bill_money", cur_money - pay_money +"");
							payMap.put("unbill_money", "0");
							payMap.put("ven_id", entityMap.get("ven_id"));
							payMap.put("ven_no", entityMap.get("ven_no"));
							payMap.put("note", "预付款");
							payMap.put("is_pre", "1");
							payStageListVo.add(payMap);

							/**
							 * 第二条数据
							 */
							Double unpay_money = price - (cur_money - pay_money);
							Map<String, Object> payMap2 = new HashMap<String, Object>();
							Integer stage_no2 = assPayStageLandMapper.queryStageLandMaxNo(entityMap);
							payMap2.put("group_id", entityMap.get("group_id"));
							payMap2.put("hos_id", entityMap.get("hos_id"));
							payMap2.put("copy_code", entityMap.get("copy_code"));
							payMap2.put("ass_card_no", entityMap.get("ass_card_no"));
							payMap2.put("stage_no", stage_no2 + 1);
							payMap2.put("stage_name", "二期款");
							payMap2.put("pay_term_id", matPayTerm.getPay_term_id());// 付款条件
							String date2 = DateUtil.dateToString(new Date(), "yyyy-MM-dd");
							String yearMonth = date2.split("-")[0] + date2.split("-")[1];
							yearMonth = DateUtil.getNextYear_Month(yearMonth);
							date2 = yearMonth.substring(0, 4) + "-" + yearMonth.substring(4, 6) + "-" + date2.split("-")[2];
							payMap2.put("pay_plan_date", date2);
							BigDecimal bg2 = new BigDecimal(unpay_money / price).setScale(2, RoundingMode.UP);
							payMap2.put("pay_plan_percent", bg2.doubleValue() +"");
							payMap2.put("pay_plan_money", unpay_money +"");
							payMap2.put("pay_money", "0");
							payMap2.put("unpay_money", unpay_money +"");
							payMap2.put("bill_money", "0");
							payMap2.put("unbill_money", unpay_money +"");
							payMap2.put("ven_id", entityMap.get("ven_id"));
							payMap2.put("ven_no", entityMap.get("ven_no"));
							payMap2.put("note", "");
							payMap2.put("is_pre", "0");
							payStageListVo.add(payMap2);
						}

						if ((price - (cur_money - pay_money)) < 0) {
							Map<String, Object> payMap = new HashMap<String, Object>();
							Integer stage_no = assPayStageLandMapper.queryStageLandMaxNo(entityMap);
							payMap.put("group_id", entityMap.get("group_id"));
							payMap.put("hos_id", entityMap.get("hos_id"));
							payMap.put("copy_code", entityMap.get("copy_code"));
							payMap.put("ass_card_no", entityMap.get("ass_card_no"));
							payMap.put("stage_no", stage_no);
							payMap.put("stage_name", "一期款");
							payMap.put("pay_term_id", matPayTerm.getPay_term_id());// 付款条件
							payMap.put("pay_plan_date", DateUtil.dateToString(new Date(),"yyyy-MM-dd"));
							payMap.put("pay_plan_percent", 1);
							payMap.put("pay_plan_money", price);
							payMap.put("pay_money", price);
							payMap.put("unpay_money", "0");
							payMap.put("bill_money", price);
							payMap.put("unbill_money", "0");
							payMap.put("ven_id", entityMap.get("ven_id"));
							payMap.put("ven_no", entityMap.get("ven_no"));
							payMap.put("note", "预付款");
							payMap.put("is_pre", "1");
							payStageListVo.add(payMap);
						}
					}
					
				}else{
					Map<String, Object> payMap = new HashMap<String, Object>();
					Integer stage_no = assPayStageLandMapper.queryStageLandMaxNo(entityMap);
					payMap.put("group_id", entityMap.get("group_id"));
					payMap.put("hos_id", entityMap.get("hos_id"));
					payMap.put("copy_code", entityMap.get("copy_code"));
					payMap.put("ass_card_no", entityMap.get("ass_card_no"));
					payMap.put("stage_no", stage_no);
					payMap.put("stage_name", "一期款");
					payMap.put("pay_term_id", matPayTerm.getPay_term_id());// 付款条件
					payMap.put("pay_plan_date", DateUtil.dateToString(new Date(),"yyyy-MM-dd"));
					payMap.put("pay_plan_percent", 1);
					payMap.put("pay_plan_money", price);
					payMap.put("pay_money", "0");
					payMap.put("unpay_money", price);
					payMap.put("bill_money", "0");
					payMap.put("unbill_money", price);
					payMap.put("ven_id", entityMap.get("ven_id"));
					payMap.put("ven_no", entityMap.get("ven_no"));
					payMap.put("note", "");
					payMap.put("is_pre", "0");
					payStageListVo.add(payMap);
				}
				
				assPayStageLandMapper.addBatch(payStageListVo);
				Double total_cur_money = 0.0;
				if(prePayStageList.size() > 0){
					total_cur_money = Double.parseDouble(prePayStageList.get(0).get("cur_money").toString());//总预付金额
				}
				
				
				List<Map<String, Object>> reSourceListVo = new ArrayList<Map<String, Object>>();
				List<Map<String, Object>> payMoneyListVo = new ArrayList<Map<String, Object>>();
				if(prePaySourceList.size() > 0){
					if(cur_money - pay_money == 0){
						Map<String, Object> sourceMap = new HashMap<String, Object>(); 
						sourceMap.put("group_id", entityMap.get("group_id"));
						sourceMap.put("hos_id", entityMap.get("hos_id"));
						sourceMap.put("copy_code", entityMap.get("copy_code"));
						sourceMap.put("ass_card_no", entityMap.get("ass_card_no"));
						sourceMap.put("source_id", 1);
						sourceMap.put("price", entityMap.get("price"));
						sourceMap.put("depre_money", entityMap.get("depre_money"));
						sourceMap.put("manage_depre_money", entityMap.get("manage_depre_money"));
						sourceMap.put("cur_money", entityMap.get("cur_money"));
						sourceMap.put("fore_money", entityMap.get("fore_money"));
						sourceMap.put("pay_money", "0");
						sourceMap.put("unpay_money", price);
						reSourceListVo.add(sourceMap);
					}else{
						for(Map<String, Object> map :prePaySourceList){
							Double source_price = Math.rint(price * (Double.parseDouble(map.get("cur_money").toString()) / total_cur_money));
							Double source_cur_money = Math.rint(Double.parseDouble(entityMap.get("cur_money").toString()) * (Double.parseDouble(map.get("cur_money").toString()) / total_cur_money));
							Double source_fore_money = Math.rint(Double.parseDouble(entityMap.get("fore_money") == null || entityMap.get("fore_money").equals("")? "0.0":entityMap.get("fore_money").toString()) * (Double.parseDouble(map.get("cur_money").toString()) / total_cur_money));
							Double source_depre_money = Math.rint(Double.parseDouble(entityMap.get("depre_money") == null  || entityMap.get("depre_money").equals("")? "0.0":entityMap.get("depre_money").toString()) * (Double.parseDouble(map.get("cur_money").toString()) / total_cur_money));
							Double source_manage_depre_money = Math.rint(Double.parseDouble(entityMap.get("manage_depre_money") == null || entityMap.get("manage_depre_money").equals("")? "0.0":entityMap.get("manage_depre_money").toString()) * (Double.parseDouble(map.get("cur_money").toString()) / total_cur_money));
							
							Map<String, Object> sourceMap = new HashMap<String, Object>(); 
							sourceMap.put("group_id", entityMap.get("group_id"));
							sourceMap.put("hos_id", entityMap.get("hos_id"));
							sourceMap.put("copy_code", entityMap.get("copy_code"));
							sourceMap.put("ass_card_no", entityMap.get("ass_card_no"));
							sourceMap.put("source_id", map.get("source_id"));
							sourceMap.put("price", source_price);
							sourceMap.put("cur_money", source_cur_money);
							sourceMap.put("fore_money", source_fore_money);
							sourceMap.put("depre_money", source_depre_money);
							sourceMap.put("manage_depre_money", source_manage_depre_money);
							sourceMap.put("pay_money",  (price - (cur_money - pay_money)) < 0 ? source_price : Double.parseDouble(map.get("cur_money").toString()) - Double.parseDouble(map.get("pay_money") == null ? "0" :map.get("pay_money").toString()));
							sourceMap.put("unpay_money", (price - (cur_money - pay_money)) < 0 ? "0":source_price - (Double.parseDouble(map.get("cur_money").toString()) - Double.parseDouble(map.get("pay_money") == null ? "0" :map.get("pay_money").toString())));
							reSourceListVo.add(sourceMap);
							
							map.put("pay_money", (price - (cur_money - pay_money)) < 0 ? source_price : Double.parseDouble(map.get("cur_money").toString()) - Double.parseDouble(map.get("pay_money") == null ? "0" :map.get("pay_money").toString()));
							payMoneyListVo.add(map);
						}
					}
					
				}else{
					Map<String, Object> sourceMap = new HashMap<String, Object>(); 
					sourceMap.put("group_id", entityMap.get("group_id"));
					sourceMap.put("hos_id", entityMap.get("hos_id"));
					sourceMap.put("copy_code", entityMap.get("copy_code"));
					sourceMap.put("ass_card_no", entityMap.get("ass_card_no"));
					sourceMap.put("source_id", 1);
					sourceMap.put("price", entityMap.get("price"));
					sourceMap.put("depre_money", entityMap.get("depre_money"));
					sourceMap.put("manage_depre_money", entityMap.get("manage_depre_money"));
					sourceMap.put("cur_money", entityMap.get("cur_money"));
					sourceMap.put("fore_money", entityMap.get("fore_money"));
					sourceMap.put("pay_money", "0");
					sourceMap.put("unpay_money", price);
					reSourceListVo.add(sourceMap);
				}
				
				assResourceLandMapper.addBatch(reSourceListVo);
				
				if(payMoneyListVo.size() > 0){
					assPrePayMapper.updateBatchPayMoney(payMoneyListVo);
				}
				
				if(entityMap.get("dept_id") != null && !entityMap.get("dept_id").equals("") && entityMap.get("dept_no") != null && !entityMap.get("dept_no").equals("")){
					entityMap.put("area",1);
					entityMap.put("ass_year",getAssYear());
					entityMap.put("ass_month",getAssMonth());
					
					assShareDeptRLandMapper.add(entityMap);
					
					assShareDeptLandMapper.add(entityMap);
				}
				
				assBaseService.updateAssBillNoMaxNo("ASS_CARD_LAND");
				
				return "{\"msg\":\"保存成功.\",\"state\":\"true\",\"ass_card_no\":\""+entityMap.get("ass_card_no")+"\"}";
			}
		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());

		}
		
	}
	
	private String saveAssInStore(Map<String,Object> entityMap){
		String ass_in_no = "";
		if (entityMap.get("is_addin") !=null && entityMap.get("is_addin").toString().equals("true")) {
			if (entityMap.get("in_type").equals("01")) {
				//获取最新单号
				if(entityMap.get("ass_in_no") == null || entityMap.get("ass_in_no").equals("")){
					entityMap.put("bill_table", "ASS_IN_MAIN_LAND");
					ass_in_no = assBaseService.getBillNOSeqNo(entityMap);
					entityMap.put("ass_in_no", ass_in_no);
					assBaseService.updateAssBillNoMaxNo(entityMap);
				}
			}else if (entityMap.get("in_type").equals("02")) {
				/**
				 * 其他入库
				 */
				//获取最新单号
				if(entityMap.get("ass_in_no") == null || entityMap.get("ass_in_no").equals("")){
					entityMap.put("bill_table", "ASS_IN_REST_MAIN_LAND");
					ass_in_no = assBaseService.getBillNOSeqNo(entityMap);
					entityMap.put("ass_in_no", ass_in_no);
					assBaseService.updateAssBillNoMaxNo(entityMap);
				}
			
			}else if (entityMap.get("in_type").equals("03")) {
				/**
				 * 盘盈入库
				 */
				//获取最新单号
				if(entityMap.get("ass_in_no") == null || entityMap.get("ass_in_no").equals("")){
					entityMap.put("bill_table", "ASS_CHK_IN_MAIN_LAND");
					ass_in_no = assBaseService.getBillNOSeqNo(entityMap);
					entityMap.put("ass_in_no", ass_in_no);
					assBaseService.updateAssBillNoMaxNo(entityMap);
				}
			}
			
			Map<String,Object> delMap = new HashMap<String, Object>();
			delMap.put("group_id", entityMap.get("group_id"));
			delMap.put("hos_id", entityMap.get("hos_id"));
			delMap.put("copy_code", entityMap.get("copy_code"));
			delMap.put("ass_card_no", entityMap.get("ass_card_no_old"));
			assShareDeptRLandMapper.delete(delMap);
			assShareDeptLandMapper.delete(delMap);
			assResourceLandMapper.delete(delMap);
			assFileLandMapper.delete(delMap);
			assPhotoLandMapper.delete(delMap);
			assAccessoryLandMapper.delete(delMap);
			assPayStageLandMapper.delete(delMap);
			assDepreAccLandMapper.delete(delMap);
			assDepreManageLandMapper.delete(delMap);
			assCardLandMapper.delete(delMap);
			
			assCardLandMapper.add(entityMap);
			
			Double price = Double.parseDouble(entityMap.get("price").toString());

			/**
			 * 通过资产ID和供应商ID查询 查询预付款核定 如果存在，取出资金来源数据插入卡片资金来源 ，插入分期付款 如果资产原值 -
			 * 预付余额 - 回冲金额 = 0 那么分期付款为一条数据 如果资产原值 - 预付余额 - 回冲金额 > 0那么分期付款产生两条数据
			 * 资产原值 - 预付余额 - 回冲金额 = 未付金额
			 */
			entityMap.put("naturs_code", "06");
			MatPayTerm matPayTerm = null;
			
			if(matPayTermMapper.queryPayTerm(entityMap) != null && matPayTermMapper.queryPayTerm(entityMap).size() > 0){
				matPayTerm = (MatPayTerm) matPayTermMapper.queryPayTerm(entityMap).get(0);
			}else{
				matPayTerm = new MatPayTerm();
				matPayTerm.setPay_term_id((long)1);
			}
			List<Map<String, Object>> prePayStageList = assPrePayMapper.queryByVenAndAss(entityMap);// 汇总的核定数据
			List<Map<String, Object>> prePaySourceList = assPrePayMapper.queryByVenAndAssSource(entityMap);//带有资金来源的核定数据

			List<Map<String, Object>> payStageListVo = new ArrayList<Map<String, Object>>();
			Double cur_money = 0.0;
			Double pay_money = 0.0;
			if(prePayStageList.size() > 0){
				cur_money = Double.parseDouble(prePayStageList.get(0).get("cur_money").toString());// 预付
				pay_money = Double.parseDouble(prePayStageList.get(0).get("pay_money") == null ? "0.0" :prePayStageList.get(0).get("pay_money").toString());// 回冲
				if(cur_money - pay_money == 0){
					Map<String, Object> payMap = new HashMap<String, Object>();
					Integer stage_no = assPayStageLandMapper.queryStageLandMaxNo(entityMap);
					payMap.put("group_id", entityMap.get("group_id"));
					payMap.put("hos_id", entityMap.get("hos_id"));
					payMap.put("copy_code", entityMap.get("copy_code"));
					payMap.put("ass_card_no", entityMap.get("ass_card_no"));
					payMap.put("stage_no", stage_no);
					payMap.put("stage_name", "一期款");
					payMap.put("pay_term_id", matPayTerm.getPay_term_id());// 付款条件
					payMap.put("pay_plan_date", DateUtil.dateToString(new Date(),"yyyy-MM-dd"));
					payMap.put("pay_plan_percent", 1);
					payMap.put("pay_plan_money", price);
					payMap.put("pay_money", "0");
					payMap.put("unpay_money", price);
					payMap.put("bill_money", "0");
					payMap.put("unbill_money", price);
					payMap.put("ven_id", entityMap.get("ven_id"));
					payMap.put("ven_no", entityMap.get("ven_no"));
					payMap.put("note", "");
					payMap.put("is_pre", "0");
					payStageListVo.add(payMap);
				}else{
					if ((price - (cur_money - pay_money)) == 0) {
						Map<String, Object> payMap = new HashMap<String, Object>();
						Integer stage_no = assPayStageLandMapper.queryStageLandMaxNo(entityMap);
						payMap.put("group_id", entityMap.get("group_id"));
						payMap.put("hos_id", entityMap.get("hos_id"));
						payMap.put("copy_code", entityMap.get("copy_code"));
						payMap.put("ass_card_no", entityMap.get("ass_card_no"));
						payMap.put("stage_no", stage_no);
						payMap.put("stage_name", "一期款");
						payMap.put("pay_term_id", matPayTerm.getPay_term_id());// 付款条件
						payMap.put("pay_plan_date", DateUtil.dateToString(new Date(),"yyyy-MM-dd"));
						payMap.put("pay_plan_percent", 1);
						payMap.put("pay_plan_money", price);
						payMap.put("bill_money", price);
						payMap.put("unbill_money", "0");
						payMap.put("ven_id", entityMap.get("ven_id"));
						payMap.put("ven_no", entityMap.get("ven_no"));
						payMap.put("note", "预付款");
						payMap.put("is_pre", "1");
						payStageListVo.add(payMap);
					}
					if ((price - (cur_money - pay_money)) > 0) {
						/**
						 * 第一条数据
						 */
						Map<String, Object> payMap = new HashMap<String, Object>();
						Integer stage_no1 = assPayStageLandMapper.queryStageLandMaxNo(entityMap);
						payMap.put("group_id", entityMap.get("group_id"));
						payMap.put("hos_id", entityMap.get("hos_id"));
						payMap.put("copy_code", entityMap.get("copy_code"));
						payMap.put("ass_card_no", entityMap.get("ass_card_no"));
						payMap.put("stage_no", stage_no1);
						payMap.put("stage_name", "一期款");
						payMap.put("pay_term_id", matPayTerm.getPay_term_id());// 付款条件
						payMap.put("pay_plan_date", DateUtil.dateToString(new Date(),"yyyy-MM-dd"));
						BigDecimal bg = new BigDecimal((cur_money - pay_money) / price).setScale(2, RoundingMode.UP);
						payMap.put("pay_plan_percent", bg.doubleValue() +"");
						payMap.put("pay_plan_money", cur_money - pay_money +"");
						payMap.put("pay_money", cur_money - pay_money +"");
						payMap.put("unpay_money", "0");
						payMap.put("bill_money", cur_money - pay_money +"");
						payMap.put("unbill_money", "0");
						payMap.put("ven_id", entityMap.get("ven_id"));
						payMap.put("ven_no", entityMap.get("ven_no"));
						payMap.put("note", "预付款");
						payMap.put("is_pre", "1");
						payStageListVo.add(payMap);

						/**
						 * 第二条数据
						 */
						Double unpay_money = price - (cur_money - pay_money);
						Map<String, Object> payMap2 = new HashMap<String, Object>();
						Integer stage_no2 = assPayStageLandMapper.queryStageLandMaxNo(entityMap);
						payMap2.put("group_id", entityMap.get("group_id"));
						payMap2.put("hos_id", entityMap.get("hos_id"));
						payMap2.put("copy_code", entityMap.get("copy_code"));
						payMap2.put("ass_card_no", entityMap.get("ass_card_no"));
						payMap2.put("stage_no", stage_no2 + 1);
						payMap2.put("stage_name", "二期款");
						payMap2.put("pay_term_id", matPayTerm.getPay_term_id());// 付款条件
						String date2 = DateUtil.dateToString(new Date(), "yyyy-MM-dd");
						String yearMonth = date2.split("-")[0] + date2.split("-")[1];
						yearMonth = DateUtil.getNextYear_Month(yearMonth);
						date2 = yearMonth.substring(0, 4) + "-" + yearMonth.substring(4, 6) + "-" + date2.split("-")[2];
						payMap2.put("pay_plan_date", date2);
						BigDecimal bg2 = new BigDecimal(unpay_money / price).setScale(2, RoundingMode.UP);
						payMap2.put("pay_plan_percent", bg2.doubleValue() +"");
						payMap2.put("pay_plan_money", unpay_money +"");
						payMap2.put("pay_money", "0");
						payMap2.put("unpay_money", unpay_money +"");
						payMap2.put("bill_money", "0");
						payMap2.put("unbill_money", unpay_money +"");
						payMap2.put("ven_id", entityMap.get("ven_id"));
						payMap2.put("ven_no", entityMap.get("ven_no"));
						payMap2.put("note", "");
						payMap2.put("is_pre", "0");
						payStageListVo.add(payMap2);
					}

					if ((price - (cur_money - pay_money)) < 0) {
						Map<String, Object> payMap = new HashMap<String, Object>();
						Integer stage_no = assPayStageLandMapper.queryStageLandMaxNo(entityMap);
						payMap.put("group_id", entityMap.get("group_id"));
						payMap.put("hos_id", entityMap.get("hos_id"));
						payMap.put("copy_code", entityMap.get("copy_code"));
						payMap.put("ass_card_no", entityMap.get("ass_card_no"));
						payMap.put("stage_no", stage_no);
						payMap.put("stage_name", "一期款");
						payMap.put("pay_term_id", matPayTerm.getPay_term_id());// 付款条件
						payMap.put("pay_plan_date", DateUtil.dateToString(new Date(),"yyyy-MM-dd"));
						payMap.put("pay_plan_percent", 1);
						payMap.put("pay_plan_money", price);
						payMap.put("pay_money", price);
						payMap.put("unpay_money", "0");
						payMap.put("bill_money", price);
						payMap.put("unbill_money", "0");
						payMap.put("ven_id", entityMap.get("ven_id"));
						payMap.put("ven_no", entityMap.get("ven_no"));
						payMap.put("note", "预付款");
						payMap.put("is_pre", "1");
						payStageListVo.add(payMap);
					}
				}
				
			}else{
				Map<String, Object> payMap = new HashMap<String, Object>();
				Integer stage_no = assPayStageLandMapper.queryStageLandMaxNo(entityMap);
				payMap.put("group_id", entityMap.get("group_id"));
				payMap.put("hos_id", entityMap.get("hos_id"));
				payMap.put("copy_code", entityMap.get("copy_code"));
				payMap.put("ass_card_no", entityMap.get("ass_card_no"));
				payMap.put("stage_no", stage_no);
				payMap.put("stage_name", "一期款");
				payMap.put("pay_term_id", matPayTerm.getPay_term_id());// 付款条件
				payMap.put("pay_plan_date", DateUtil.dateToString(new Date(),"yyyy-MM-dd"));
				payMap.put("pay_plan_percent", 1);
				payMap.put("pay_plan_money", price);
				payMap.put("pay_money", "0");
				payMap.put("unpay_money", price);
				payMap.put("bill_money", "0");
				payMap.put("unbill_money", price);
				payMap.put("ven_id", entityMap.get("ven_id"));
				payMap.put("ven_no", entityMap.get("ven_no"));
				payMap.put("note", "");
				payMap.put("is_pre", "0");
				payStageListVo.add(payMap);
			}
			
			assPayStageLandMapper.addBatch(payStageListVo);
			Double total_cur_money = 0.0;
			if(prePayStageList.size() > 0){
				total_cur_money = Double.parseDouble(prePayStageList.get(0).get("cur_money").toString());//总预付金额
			}
			
			
			List<Map<String, Object>> reSourceListVo = new ArrayList<Map<String, Object>>();
			List<Map<String, Object>> payMoneyListVo = new ArrayList<Map<String, Object>>();
			if(prePaySourceList.size() > 0){
				if(cur_money - pay_money == 0){
					Map<String, Object> sourceMap = new HashMap<String, Object>(); 
					sourceMap.put("group_id", entityMap.get("group_id"));
					sourceMap.put("hos_id", entityMap.get("hos_id"));
					sourceMap.put("copy_code", entityMap.get("copy_code"));
					sourceMap.put("ass_card_no", entityMap.get("ass_card_no"));
					sourceMap.put("source_id", 1);
					sourceMap.put("price", entityMap.get("price"));
					sourceMap.put("depre_money", entityMap.get("depre_money"));
					sourceMap.put("manage_depre_money", entityMap.get("manage_depre_money"));
					sourceMap.put("cur_money", entityMap.get("cur_money"));
					sourceMap.put("fore_money", entityMap.get("fore_money"));
					sourceMap.put("pay_money", "0");
					sourceMap.put("unpay_money", price);
					reSourceListVo.add(sourceMap);
				}else{
					for(Map<String, Object> map :prePaySourceList){
						Double source_price = Math.rint(price * (Double.parseDouble(map.get("cur_money").toString()) / total_cur_money));
						Double source_cur_money = Math.rint(Double.parseDouble(entityMap.get("cur_money").toString()) * (Double.parseDouble(map.get("cur_money").toString()) / total_cur_money));
						Double source_fore_money = Math.rint(Double.parseDouble(entityMap.get("fore_money") == null || entityMap.get("fore_money").equals("")? "0.0":entityMap.get("fore_money").toString()) * (Double.parseDouble(map.get("cur_money").toString()) / total_cur_money));
						Double source_depre_money = Math.rint(Double.parseDouble(entityMap.get("depre_money") == null  || entityMap.get("depre_money").equals("")? "0.0":entityMap.get("depre_money").toString()) * (Double.parseDouble(map.get("cur_money").toString()) / total_cur_money));
						Double source_manage_depre_money = Math.rint(Double.parseDouble(entityMap.get("manage_depre_money") == null || entityMap.get("manage_depre_money").equals("")? "0.0":entityMap.get("manage_depre_money").toString()) * (Double.parseDouble(map.get("cur_money").toString()) / total_cur_money));
						
						Map<String, Object> sourceMap = new HashMap<String, Object>(); 
						sourceMap.put("group_id", entityMap.get("group_id"));
						sourceMap.put("hos_id", entityMap.get("hos_id"));
						sourceMap.put("copy_code", entityMap.get("copy_code"));
						sourceMap.put("ass_card_no", entityMap.get("ass_card_no"));
						sourceMap.put("source_id", map.get("source_id"));
						sourceMap.put("price", source_price);
						sourceMap.put("cur_money", source_cur_money);
						sourceMap.put("fore_money", source_fore_money);
						sourceMap.put("depre_money", source_depre_money);
						sourceMap.put("manage_depre_money", source_manage_depre_money);
						sourceMap.put("pay_money",  (price - (cur_money - pay_money)) < 0 ? source_price : Double.parseDouble(map.get("cur_money").toString()) - Double.parseDouble(map.get("pay_money") == null ? "0" :map.get("pay_money").toString()));
						sourceMap.put("unpay_money", (price - (cur_money - pay_money)) < 0 ? "0":source_price - (Double.parseDouble(map.get("cur_money").toString()) - Double.parseDouble(map.get("pay_money") == null ? "0" :map.get("pay_money").toString())));
						reSourceListVo.add(sourceMap);
						
						map.put("pay_money", (price - (cur_money - pay_money)) < 0 ? source_price : Double.parseDouble(map.get("cur_money").toString()) - Double.parseDouble(map.get("pay_money") == null ? "0" :map.get("pay_money").toString()));
						payMoneyListVo.add(map);
					}
				}
				
			}else{
				Map<String, Object> sourceMap = new HashMap<String, Object>(); 
				sourceMap.put("group_id", entityMap.get("group_id"));
				sourceMap.put("hos_id", entityMap.get("hos_id"));
				sourceMap.put("copy_code", entityMap.get("copy_code"));
				sourceMap.put("ass_card_no", entityMap.get("ass_card_no"));
				sourceMap.put("source_id", 1);
				sourceMap.put("price", entityMap.get("price"));
				sourceMap.put("depre_money", entityMap.get("depre_money"));
				sourceMap.put("manage_depre_money", entityMap.get("manage_depre_money"));
				sourceMap.put("cur_money", entityMap.get("cur_money"));
				sourceMap.put("fore_money", entityMap.get("fore_money"));
				sourceMap.put("pay_money", "0");
				sourceMap.put("unpay_money", price);
				reSourceListVo.add(sourceMap);
			}
			
			assResourceLandMapper.addBatch(reSourceListVo);
			
			//不回充
			//if(payMoneyListVo.size() > 0){
				//assPrePayMapper.updateBatchPayMoney(payMoneyListVo);
			//}
			
			if(entityMap.get("dept_id") != null && !entityMap.get("dept_id").equals("") && entityMap.get("dept_no") != null && !entityMap.get("dept_no").equals("")){
				entityMap.put("area",1);
				entityMap.put("ass_year",getAssYear());
				entityMap.put("ass_month",getAssMonth());
				
				assShareDeptRLandMapper.add(entityMap);
				
				assShareDeptLandMapper.add(entityMap);
			}
			
			assBaseService.updateAssBillNoMaxNo("ASS_CARD_LAND");
			
			
			/**
			 * 反写入库单
			 */
			if (entityMap.get("is_addin") !=null && entityMap.get("is_addin").toString().equals("true")) {
				if (entityMap.get("in_type").equals("01")) {
					AssInMainLand assInMainLand = assInMainLandMapper.queryByCode(entityMap);
					if(assInMainLand != null){
						if (assInMainLand.getState() == 0) {
							//采购入库
							Map<String,Object> entityMapHouse= new HashMap<String, Object>();
							
							entityMapHouse.put("ass_in_no", entityMap.get("ass_in_no"));
							entityMapHouse.put("group_id", entityMap.get("group_id"));
							entityMapHouse.put("hos_id", entityMap.get("hos_id"));
							entityMapHouse.put("copy_code", entityMap.get("copy_code"));
							/**
							 * 删除原来入库单
							 */
							assInDetailLandMapper.delete(entityMapHouse);
							assInMainLandMapper.delete(entityMapHouse);
							
							entityMapHouse.put("ass_in_no", entityMap.get("ass_in_no"));
							entityMapHouse.put("group_id", entityMap.get("group_id"));
							entityMapHouse.put("hos_id", entityMap.get("hos_id"));
							entityMapHouse.put("copy_code", entityMap.get("copy_code"));
							entityMapHouse.put("ven_id", entityMap.get("ven_id"));
							entityMapHouse.put("ven_no", entityMap.get("ven_no"));
							entityMapHouse.put("in_money", entityMap.get("price"));
							entityMapHouse.put("note", entityMap.get("note"));
							entityMapHouse.put("create_emp", SessionManager.getUserId());
							entityMapHouse.put("create_date", DateUtil.dateToString(new Date(), "yyyy-MM-dd"));
							entityMapHouse.put("state", "0");
							int state =assInMainLandMapper.add(entityMapHouse);
							Map<String,Object> entityMapDetailHouse= new HashMap<String, Object>();
							entityMapDetailHouse.put("ass_in_no", entityMap.get("ass_in_no"));
							entityMapDetailHouse.put("group_id", entityMap.get("group_id"));
							entityMapDetailHouse.put("hos_id", entityMap.get("hos_id"));
							entityMapDetailHouse.put("copy_code", entityMap.get("copy_code"));
							entityMapDetailHouse.put("ass_id", entityMap.get("ass_id"));
							entityMapDetailHouse.put("ass_no", entityMap.get("ass_no"));
							entityMapDetailHouse.put("in_amount", "1");
							entityMapDetailHouse.put("price", entityMap.get("price"));
							entityMapDetailHouse.put("note", entityMap.get("note"));
							entityMapDetailHouse.put("reg_no", entityMap.get("reg_no"));
							int state1=	assInDetailLandMapper.add(entityMapDetailHouse);
						}
					}else{
						//采购入库
						Map<String,Object> entityMapHouse= new HashMap<String, Object>();
						
						entityMapHouse.put("ass_in_no", entityMap.get("ass_in_no"));
						entityMapHouse.put("group_id", entityMap.get("group_id"));
						entityMapHouse.put("hos_id", entityMap.get("hos_id"));
						entityMapHouse.put("copy_code", entityMap.get("copy_code"));
						entityMapHouse.put("ven_id", entityMap.get("ven_id"));
						entityMapHouse.put("ven_no", entityMap.get("ven_no"));
						entityMapHouse.put("in_money", entityMap.get("price"));
						entityMapHouse.put("note", entityMap.get("note"));
						entityMapHouse.put("create_emp", SessionManager.getUserId());
						entityMapHouse.put("create_date", DateUtil.dateToString(new Date(), "yyyy-MM-dd"));
						entityMapHouse.put("state", "0");
						int state =assInMainLandMapper.add(entityMapHouse);
						Map<String,Object> entityMapDetailHouse= new HashMap<String, Object>();
						entityMapDetailHouse.put("ass_in_no", entityMap.get("ass_in_no"));
						entityMapDetailHouse.put("group_id", entityMap.get("group_id"));
						entityMapDetailHouse.put("hos_id", entityMap.get("hos_id"));
						entityMapDetailHouse.put("copy_code", entityMap.get("copy_code"));
						entityMapDetailHouse.put("ass_id", entityMap.get("ass_id"));
						entityMapDetailHouse.put("ass_no", entityMap.get("ass_no"));
						entityMapDetailHouse.put("in_amount", "1");
						entityMapDetailHouse.put("price", entityMap.get("price"));
						entityMapDetailHouse.put("note", entityMap.get("note"));
						entityMapDetailHouse.put("reg_no", entityMap.get("reg_no"));
						int state1=	assInDetailLandMapper.add(entityMapDetailHouse);
					}
					
				}else if (entityMap.get("in_type").equals("02")) {
					AssInRestMainLand assInRestMainLand = assInRestMainLandMapper.queryByCode(entityMap);
					if(assInRestMainLand != null){
						if (assInRestMainLand.getState() == 0) {
							
							/**
							 * 其他入库
							 */
							Map<String,Object> entityMapHouse= new HashMap<String, Object>();
							entityMapHouse.put("ass_in_no", entityMap.get("ass_in_no"));
							entityMapHouse.put("group_id", entityMap.get("group_id"));
							entityMapHouse.put("hos_id", entityMap.get("hos_id"));
							entityMapHouse.put("copy_code", entityMap.get("copy_code"));
							/**
							 * 删除原来入库单
							 */
							assInRestDetailLandMapper.delete(entityMapHouse);
							assInRestMainLandMapper.delete(entityMapHouse);
							
							entityMapHouse.put("ass_in_no", entityMap.get("ass_in_no"));
							entityMapHouse.put("group_id", entityMap.get("group_id"));
							entityMapHouse.put("hos_id", entityMap.get("hos_id"));
							entityMapHouse.put("copy_code", entityMap.get("copy_code"));
							entityMapHouse.put("ven_id", entityMap.get("ven_id"));
							entityMapHouse.put("ven_no", entityMap.get("ven_no"));
							entityMapHouse.put("bus_type_code", entityMap.get("buy_type"));
							
							entityMapHouse.put("in_money", entityMap.get("price"));
							entityMapHouse.put("note", entityMap.get("note"));
							entityMapHouse.put("create_emp", SessionManager.getUserId());
							entityMapHouse.put("create_date",DateUtil.dateToString(new Date(), "yyyy-MM-dd"));
							entityMapHouse.put("state", "0");
							int state =assInRestMainLandMapper.add(entityMapHouse);
							Map<String,Object> entityMapDetailHouse= new HashMap<String, Object>();
							entityMapDetailHouse.put("ass_in_no", entityMap.get("ass_in_no"));
							entityMapDetailHouse.put("group_id", entityMap.get("group_id"));
							entityMapDetailHouse.put("hos_id", entityMap.get("hos_id"));
							entityMapDetailHouse.put("copy_code", entityMap.get("copy_code"));
							entityMapDetailHouse.put("ass_id", entityMap.get("ass_id"));
							entityMapDetailHouse.put("ass_no", entityMap.get("ass_no"));
							entityMapDetailHouse.put("in_amount", "1");
							entityMapDetailHouse.put("price", entityMap.get("price"));
							entityMapDetailHouse.put("note", entityMap.get("note"));
							int state1=	assInRestDetailLandMapper.add(entityMapDetailHouse);
						}
					}else{
						/**
						 * 其他入库
						 */
						Map<String,Object> entityMapHouse= new HashMap<String, Object>();
						
						entityMapHouse.put("ass_in_no", entityMap.get("ass_in_no"));
						entityMapHouse.put("group_id", entityMap.get("group_id"));
						entityMapHouse.put("hos_id", entityMap.get("hos_id"));
						entityMapHouse.put("copy_code", entityMap.get("copy_code"));
						entityMapHouse.put("ven_id", entityMap.get("ven_id"));
						entityMapHouse.put("ven_no", entityMap.get("ven_no"));
						entityMapHouse.put("bus_type_code", entityMap.get("buy_type"));
						
						entityMapHouse.put("in_money", entityMap.get("price"));
						entityMapHouse.put("note", entityMap.get("note"));
						entityMapHouse.put("create_emp", SessionManager.getUserId());
						entityMapHouse.put("create_date",DateUtil.dateToString(new Date(), "yyyy-MM-dd"));
						entityMapHouse.put("state", "0");
						int state =assInRestMainLandMapper.add(entityMapHouse);
						Map<String,Object> entityMapDetailHouse= new HashMap<String, Object>();
						entityMapDetailHouse.put("ass_in_no", entityMap.get("ass_in_no"));
						entityMapDetailHouse.put("group_id", entityMap.get("group_id"));
						entityMapDetailHouse.put("hos_id", entityMap.get("hos_id"));
						entityMapDetailHouse.put("copy_code", entityMap.get("copy_code"));
						entityMapDetailHouse.put("ass_id", entityMap.get("ass_id"));
						entityMapDetailHouse.put("ass_no", entityMap.get("ass_no"));
						entityMapDetailHouse.put("in_amount", "1");
						entityMapDetailHouse.put("price", entityMap.get("price"));
						entityMapDetailHouse.put("note", entityMap.get("note"));
						int state1=	assInRestDetailLandMapper.add(entityMapDetailHouse);
					}
					
				}else if (entityMap.get("in_type").equals("03")) {
					AssChkInMainLand ssChkInMainLand = assChkInMainLandMapper.queryByCode(entityMap);
					if(ssChkInMainLand != null){
						if (ssChkInMainLand.getState() == 0) {
							/**
							 * 盘盈入库
							 */
							Map<String,Object> entityMapHouse= new HashMap<String, Object>();
							entityMapHouse.put("ass_in_no", entityMap.get("ass_in_no"));
							entityMapHouse.put("group_id", entityMap.get("group_id"));
							entityMapHouse.put("hos_id", entityMap.get("hos_id"));
							entityMapHouse.put("copy_code", entityMap.get("copy_code"));
							/**
							 * 删除原来入库单
							 */
							assChkInDetailLandMapper.delete(entityMapHouse);
							assChkInMainLandMapper.delete(entityMapHouse);
							
							//获取最新单号
							entityMapHouse.put("ass_in_no", entityMap.get("ass_in_no"));
							entityMapHouse.put("group_id", entityMap.get("group_id"));
							entityMapHouse.put("hos_id", entityMap.get("hos_id"));
							entityMapHouse.put("copy_code", entityMap.get("copy_code"));
							entityMapHouse.put("ven_id", entityMap.get("ven_id"));
							entityMapHouse.put("ven_no", entityMap.get("ven_no"));
							entityMapHouse.put("in_money", entityMap.get("price"));
							entityMapHouse.put("note", entityMap.get("note"));
							entityMapHouse.put("create_emp", SessionManager.getUserId());
							entityMapHouse.put("create_date",DateUtil.dateToString(new Date(), "yyyy-MM-dd"));
							entityMapHouse.put("state", "0");
							int state =assChkInMainLandMapper.add(entityMapHouse);
							Map<String,Object> entityMapDetailHouse= new HashMap<String, Object>();
							entityMapDetailHouse.put("ass_in_no", entityMap.get("ass_in_no"));
							entityMapDetailHouse.put("group_id", entityMap.get("group_id"));
							entityMapDetailHouse.put("hos_id", entityMap.get("hos_id"));
							entityMapDetailHouse.put("copy_code", entityMap.get("copy_code"));
							entityMapDetailHouse.put("ass_id", entityMap.get("ass_id"));
							entityMapDetailHouse.put("ass_no", entityMap.get("ass_no"));
							entityMapDetailHouse.put("in_amount", "1");
							entityMapDetailHouse.put("price", entityMap.get("price"));
							entityMapDetailHouse.put("add_depre", entityMap.get("depre_money"));
							entityMapDetailHouse.put("add_depre_month", entityMap.get("add_depre_month"));
							entityMapDetailHouse.put("cur_money", entityMap.get("cur_money"));
							entityMapDetailHouse.put("fore_money", entityMap.get("fore_money"));
							entityMapDetailHouse.put("note", entityMap.get("note"));
							int state1=	assChkInDetailLandMapper.add(entityMapDetailHouse);
							
						}
					}else{
						Map<String,Object> entityMapHouse= new HashMap<String, Object>();
						
						//获取最新单号
						entityMapHouse.put("ass_in_no", entityMap.get("ass_in_no"));
						entityMapHouse.put("group_id", entityMap.get("group_id"));
						entityMapHouse.put("hos_id", entityMap.get("hos_id"));
						entityMapHouse.put("copy_code", entityMap.get("copy_code"));
						entityMapHouse.put("ven_id", entityMap.get("ven_id"));
						entityMapHouse.put("ven_no", entityMap.get("ven_no"));
						entityMapHouse.put("in_money", entityMap.get("price"));
						entityMapHouse.put("note", entityMap.get("note"));
						entityMapHouse.put("create_emp", SessionManager.getUserId());
						entityMapHouse.put("create_date",DateUtil.dateToString(new Date(), "yyyy-MM-dd"));
						entityMapHouse.put("state", "0");
						int state =assChkInMainLandMapper.add(entityMapHouse);
						Map<String,Object> entityMapDetailHouse= new HashMap<String, Object>();
						entityMapDetailHouse.put("ass_in_no", entityMap.get("ass_in_no"));
						entityMapDetailHouse.put("group_id", entityMap.get("group_id"));
						entityMapDetailHouse.put("hos_id", entityMap.get("hos_id"));
						entityMapDetailHouse.put("copy_code", entityMap.get("copy_code"));
						entityMapDetailHouse.put("ass_id", entityMap.get("ass_id"));
						entityMapDetailHouse.put("ass_no", entityMap.get("ass_no"));
						entityMapDetailHouse.put("in_amount", "1");
						entityMapDetailHouse.put("price", entityMap.get("price"));
						entityMapDetailHouse.put("add_depre", entityMap.get("depre_money"));
						entityMapDetailHouse.put("add_depre_month", entityMap.get("add_depre_month"));
						entityMapDetailHouse.put("cur_money", entityMap.get("cur_money"));
						entityMapDetailHouse.put("fore_money", entityMap.get("fore_money"));
						entityMapDetailHouse.put("note", entityMap.get("note"));
						int state1=	assChkInDetailLandMapper.add(entityMapDetailHouse);
					}
					
				}
			}
			
		}
		
		return "{\"msg\":\"保存成功.\",\"state\":\"true\",\"ass_card_no\":\"" + entityMap.get("ass_card_no") + "\",\"ass_in_no\":\"" + entityMap.get("ass_in_no") + "\"}";
	}
	
	private String updateAssInStore(Map<String,Object> entityMap){
		assCardLandMapper.update(entityMap);
		
		/**
		 * 反写入库单
		 */
		if (entityMap.get("is_addin") !=null && entityMap.get("is_addin").toString().equals("true")) {
			if (entityMap.get("in_type").equals("01")) {
				AssInMainLand assInMainLand = assInMainLandMapper.queryByCode(entityMap);
				if(assInMainLand != null){
					if (assInMainLand.getState() == 0) {
						//采购入库
						Map<String,Object> entityMapHouse= new HashMap<String, Object>();
						
						entityMapHouse.put("ass_in_no", entityMap.get("ass_in_no"));
						entityMapHouse.put("group_id", entityMap.get("group_id"));
						entityMapHouse.put("hos_id", entityMap.get("hos_id"));
						entityMapHouse.put("copy_code", entityMap.get("copy_code"));
						/**
						 * 删除原来入库单
						 */
						assInDetailLandMapper.delete(entityMapHouse);
						assInMainLandMapper.delete(entityMapHouse);
						
						entityMapHouse.put("ass_in_no", entityMap.get("ass_in_no"));
						entityMapHouse.put("group_id", entityMap.get("group_id"));
						entityMapHouse.put("hos_id", entityMap.get("hos_id"));
						entityMapHouse.put("copy_code", entityMap.get("copy_code"));
						entityMapHouse.put("ven_id", entityMap.get("ven_id"));
						entityMapHouse.put("ven_no", entityMap.get("ven_no"));
						entityMapHouse.put("in_money", entityMap.get("price"));
						entityMapHouse.put("note", entityMap.get("note"));
						entityMapHouse.put("create_emp", SessionManager.getUserId());
						entityMapHouse.put("create_date", DateUtil.dateToString(new Date(), "yyyy-MM-dd"));
						entityMapHouse.put("state", "0");
						int state =assInMainLandMapper.add(entityMapHouse);
						Map<String,Object> entityMapDetailHouse= new HashMap<String, Object>();
						entityMapDetailHouse.put("ass_in_no", entityMap.get("ass_in_no"));
						entityMapDetailHouse.put("group_id", entityMap.get("group_id"));
						entityMapDetailHouse.put("hos_id", entityMap.get("hos_id"));
						entityMapDetailHouse.put("copy_code", entityMap.get("copy_code"));
						entityMapDetailHouse.put("ass_id", entityMap.get("ass_id"));
						entityMapDetailHouse.put("ass_no", entityMap.get("ass_no"));
						entityMapDetailHouse.put("in_amount", "1");
						entityMapDetailHouse.put("price", entityMap.get("price"));
						entityMapDetailHouse.put("note", entityMap.get("note"));
						int state1=	assInDetailLandMapper.add(entityMapDetailHouse);
					}
				}
				
			}else if (entityMap.get("in_type").equals("02")) {
				AssInRestMainLand assInRestMainLand = assInRestMainLandMapper.queryByCode(entityMap);
				if(assInRestMainLand != null){
					if (assInRestMainLand.getState() == 0) {
						
						/**
						 * 其他入库
						 */
						Map<String,Object> entityMapHouse= new HashMap<String, Object>();
						entityMapHouse.put("ass_in_no", entityMap.get("ass_in_no"));
						entityMapHouse.put("group_id", entityMap.get("group_id"));
						entityMapHouse.put("hos_id", entityMap.get("hos_id"));
						entityMapHouse.put("copy_code", entityMap.get("copy_code"));
						/**
						 * 删除原来入库单
						 */
						assInRestDetailLandMapper.delete(entityMapHouse);
						assInRestMainLandMapper.delete(entityMapHouse);
						
						entityMapHouse.put("ass_in_no", entityMap.get("ass_in_no"));
						entityMapHouse.put("group_id", entityMap.get("group_id"));
						entityMapHouse.put("hos_id", entityMap.get("hos_id"));
						entityMapHouse.put("copy_code", entityMap.get("copy_code"));
						entityMapHouse.put("ven_id", entityMap.get("ven_id"));
						entityMapHouse.put("ven_no", entityMap.get("ven_no"));
						entityMapHouse.put("bus_type_code", entityMap.get("buy_type"));
						
						entityMapHouse.put("in_money", entityMap.get("price"));
						entityMapHouse.put("note", entityMap.get("note"));
						entityMapHouse.put("create_emp", SessionManager.getUserId());
						entityMapHouse.put("create_date",DateUtil.dateToString(new Date(), "yyyy-MM-dd"));
						entityMapHouse.put("state", "0");
						int state =assInRestMainLandMapper.add(entityMapHouse);
						Map<String,Object> entityMapDetailHouse= new HashMap<String, Object>();
						entityMapDetailHouse.put("ass_in_no", entityMap.get("ass_in_no"));
						entityMapDetailHouse.put("group_id", entityMap.get("group_id"));
						entityMapDetailHouse.put("hos_id", entityMap.get("hos_id"));
						entityMapDetailHouse.put("copy_code", entityMap.get("copy_code"));
						entityMapDetailHouse.put("ass_id", entityMap.get("ass_id"));
						entityMapDetailHouse.put("ass_no", entityMap.get("ass_no"));
						entityMapDetailHouse.put("in_amount", "1");
						entityMapDetailHouse.put("price", entityMap.get("price"));
						entityMapDetailHouse.put("note", entityMap.get("note"));
						int state1=	assInRestDetailLandMapper.add(entityMapDetailHouse);
					}
				}
				
			}else if (entityMap.get("in_type").equals("03")) {
				AssChkInMainLand ssChkInMainLand = assChkInMainLandMapper.queryByCode(entityMap);
				if(ssChkInMainLand != null){
					if (ssChkInMainLand.getState() == 0) {
						/**
						 * 盘盈入库
						 */
						Map<String,Object> entityMapHouse= new HashMap<String, Object>();
						entityMapHouse.put("ass_in_no", entityMap.get("ass_in_no"));
						entityMapHouse.put("group_id", entityMap.get("group_id"));
						entityMapHouse.put("hos_id", entityMap.get("hos_id"));
						entityMapHouse.put("copy_code", entityMap.get("copy_code"));
						/**
						 * 删除原来入库单
						 */
						assChkInDetailLandMapper.delete(entityMapHouse);
						assChkInMainLandMapper.delete(entityMapHouse);
						
						//获取最新单号
						entityMapHouse.put("ass_in_no", entityMap.get("ass_in_no"));
						entityMapHouse.put("group_id", entityMap.get("group_id"));
						entityMapHouse.put("hos_id", entityMap.get("hos_id"));
						entityMapHouse.put("copy_code", entityMap.get("copy_code"));
						entityMapHouse.put("ven_id", entityMap.get("ven_id"));
						entityMapHouse.put("ven_no", entityMap.get("ven_no"));
						entityMapHouse.put("in_money", entityMap.get("price"));
						entityMapHouse.put("note", entityMap.get("note"));
						entityMapHouse.put("create_emp", SessionManager.getUserId());
						entityMapHouse.put("create_date",DateUtil.dateToString(new Date(), "yyyy-MM-dd"));
						entityMapHouse.put("state", "0");
						int state =assChkInMainLandMapper.add(entityMapHouse);
						Map<String,Object> entityMapDetailHouse= new HashMap<String, Object>();
						entityMapDetailHouse.put("ass_in_no", entityMap.get("ass_in_no"));
						entityMapDetailHouse.put("group_id", entityMap.get("group_id"));
						entityMapDetailHouse.put("hos_id", entityMap.get("hos_id"));
						entityMapDetailHouse.put("copy_code", entityMap.get("copy_code"));
						entityMapDetailHouse.put("ass_id", entityMap.get("ass_id"));
						entityMapDetailHouse.put("ass_no", entityMap.get("ass_no"));
						entityMapDetailHouse.put("in_amount", "1");
						entityMapDetailHouse.put("price", entityMap.get("price"));
						entityMapDetailHouse.put("add_depre", entityMap.get("depre_money"));
						entityMapDetailHouse.put("add_depre_month", entityMap.get("add_depre_month"));
						entityMapDetailHouse.put("cur_money", entityMap.get("cur_money"));
						entityMapDetailHouse.put("fore_money", entityMap.get("fore_money"));
						entityMapDetailHouse.put("note", entityMap.get("note"));
						int state1=	assChkInDetailLandMapper.add(entityMapDetailHouse);
						
					}
				}
				
			}
		}
		
	
	return "{\"msg\":\"保存成功.\",\"state\":\"true\",\"ass_card_no\":\"" + entityMap.get("ass_card_no") + "\",\"ass_in_no\":\"" + entityMap.get("ass_in_no") + "\"}";
	}
	
	private String getAssYear(){
		String yearMonth=MyConfig.getCurAccYearMonth();
		return yearMonth.substring(0, 4);
	}
	
	private String getAssMonth(){
		String yearMonth=MyConfig.getCurAccYearMonth();
		return  yearMonth.substring(4, 6);
	}
	
	/**
	 * @Description 
	 * 批量添加资产卡片维护_土地<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addBatch(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
			assCardLandMapper.addBatch(entityList);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}
		
	}
	
		/**
	 * @Description 
	 * 更新资产卡片维护_土地<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String update(Map<String,Object> entityMap)throws DataAccessException{
		
		try {
			if (entityMap.get("is_update") != null || entityMap.get("is_update").toString().equals("true")) {
				return updateAssInStore(entityMap);
			}else{
				int state = assCardLandMapper.update(entityMap);
			}
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());

		}	
		
	}
	/**
	 * @Description 
	 * 批量更新资产卡片维护_土地<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateBatch(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
		  assCardLandMapper.updateBatch(entityList);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}	
		
	}
	/**
	 * @Description 
	 * 删除资产卡片维护_土地<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
    public String delete(Map<String, Object> entityMap) throws DataAccessException {
    	
    try {
			
			int state = assCardLandMapper.delete(entityMap);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());

		}	
		
  }
    
	/**
	 * @Description 
	 * 批量删除资产卡片维护_土地<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String deleteBatch(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			//删除资产文档
			for(Map<String,Object> fileMap : entityList){
				List<AssFileLand> assFileLandList = (List<AssFileLand>)assFileLandMapper.queryExists(fileMap);
				if(assFileLandList.size() > 0 && assFileLandList != null){
					for(AssFileLand assFileLand : assFileLandList){
						if(assFileLand.getFile_url() != null && !assFileLand.getFile_url().equals("")){
							String file_name = assFileLand.getFile_url().substring(assFileLand.getFile_url().lastIndexOf("/") + 1,  assFileLand.getFile_url().length());
							String path =  assFileLand.getFile_url().substring(0, assFileLand.getFile_url().lastIndexOf("/"));
							FtpUtil.deleteFile(path, file_name);
							path = path.substring(0,path.lastIndexOf("/"));
							FtpUtil.deleteDirectory(assFileLand.getAss_card_no(), path);
						}
					}
				}
			}
			
			//删除资产照片
			for(Map<String,Object> photoMap : entityList){
				List<AssPhotoLand> assPhotoLandList = (List<AssPhotoLand>)assPhotoLandMapper.queryExists(photoMap);
				if(assPhotoLandList.size() > 0 && assPhotoLandList != null){
					for(AssPhotoLand assPhotoLand : assPhotoLandList){
						if(assPhotoLand.getFile_url() != null && !assPhotoLand.getFile_url().equals("")){
							String file_name = assPhotoLand.getFile_url().substring(assPhotoLand.getFile_url().lastIndexOf("/") + 1,  assPhotoLand.getFile_url().length());
							String path =  assPhotoLand.getFile_url().substring(0, assPhotoLand.getFile_url().lastIndexOf("/"));
							FtpUtil.deleteFile(path, file_name);
							path = path.substring(0,path.lastIndexOf("/"));
							FtpUtil.deleteDirectory(assPhotoLand.getAss_card_no(), path);
						}
					}
				}
			}
			
			assShareDeptRLandMapper.deleteBatch(entityList);
			assShareDeptLandMapper.deleteBatch(entityList);
			assResourceLandMapper.deleteBatch(entityList);
			assFileLandMapper.deleteBatch(entityList);
			assPhotoLandMapper.deleteBatch(entityList);
			assAccessoryLandMapper.deleteBatch(entityList);
			assPayStageLandMapper.deleteBatch(entityList);
			assDepreAccLandMapper.deleteBatch(entityList);
			assDepreManageLandMapper.deleteBatch(entityList);
			assCardLandMapper.deleteBatch(entityList);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());

		}	
	}
	
	/**
	 * @Description 
	 * 添加资产卡片维护_土地<BR> 
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
		//判断是否存在对象资产卡片维护_土地
		Map<String, Object> mapVo=new HashMap<String, Object>();
		mapVo.put("group_id",entityMap.get("group_id"));
		mapVo.put("hos_id",entityMap.get("hos_id"));
    	mapVo.put("copy_code", entityMap.get("copy_code"));
    	mapVo.put("acct_year", entityMap.get("acct_year"));
		
		List<AssCardLand> list = (List<AssCardLand>)assCardLandMapper.queryExists(mapVo);
		
		if (list.size()>0) {

			int state = assCardLandMapper.update(entityMap);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		
		try {
			
			int state = assCardLandMapper.add(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());

		}
		
	}
	/**
	 * @Description 
	 * 查询结果集资产卡片维护_土地<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String query(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		entityMap.put("user_id", SessionManager.getUserId());
		
		if (sysPage.getTotal()==-1){
			
			List<AssCardLand> list = (List<AssCardLand>)assCardLandMapper.query(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<AssCardLand> list = (List<AssCardLand>)assCardLandMapper.query(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
		
	}
	
	/**
	 * @Description 
	 * 获取对象资产卡片维护_土地<BR> 
	 * @param  entityMap<BR>
	 *  参数为主键
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public <T> T queryByCode(Map<String,Object> entityMap)throws DataAccessException{
		return assCardLandMapper.queryByCode(entityMap);
	}
	
	/**
	 * @Description 
	 * 获取资产卡片维护_土地<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return AssCardLand
	 * @throws DataAccessException
	*/
	@Override
	public <T> T queryByUniqueness(Map<String,Object> entityMap)throws DataAccessException{
		return assCardLandMapper.queryByUniqueness(entityMap);
	}
	
	/**
	 * @Description 
	 * 获取资产卡片维护_土地<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return List<AssCardLand>
	 * @throws DataAccessException
	*/
	@Override
	public List<?> queryExists(Map<String,Object> entityMap)throws DataAccessException{
		return assCardLandMapper.queryExists(entityMap);
	}
	
	@Override
	public Integer queryCardExistsByAssInNo(Map<String, Object> entityMap) throws DataAccessException {
		return assCardLandMapper.queryCardExistsByAssInNo(entityMap);
	}

	@Override
	public List<Map<String, Object>> queryAssCardByAssInNo(Map<String, Object> entityMap) throws DataAccessException {
		return assCardLandMapper.queryByAssInNo(entityMap);
	}

	@Override
	public List<Map<String, Object>> queryPrint(Map<String, Object> entityMap) throws DataAccessException {
		entityMap.put("user_id", SessionManager.getUserId());
		List<Map<String,Object>> list = (List<Map<String,Object>>) assCardLandMapper.queryPrint(entityMap);
		return list;
	}
	
}
