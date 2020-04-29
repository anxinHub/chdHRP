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
import com.chd.hrp.ass.dao.accessory.AssAccessoryGeneralMapper;
import com.chd.hrp.ass.dao.card.AssCardGeneralMapper;
import com.chd.hrp.ass.dao.depre.acc.AssDepreAccGeneralMapper;
import com.chd.hrp.ass.dao.depre.manager.AssDepreManageGeneralMapper;
import com.chd.hrp.ass.dao.file.AssFileGeneralMapper;
import com.chd.hrp.ass.dao.pay.AssPayStageGeneralMapper;
import com.chd.hrp.ass.dao.photo.AssPhotoGeneralMapper;
import com.chd.hrp.ass.dao.prepay.AssPrePayMapper;
import com.chd.hrp.ass.dao.resource.AssResourceGeneralMapper;
import com.chd.hrp.ass.dao.share.AssShareDeptGeneralMapper;
import com.chd.hrp.ass.dao.share.AssShareDeptRGeneralMapper;
import com.chd.hrp.ass.entity.card.AssCardGeneral;
import com.chd.hrp.ass.entity.file.AssFileGeneral;
import com.chd.hrp.ass.entity.photo.AssPhotoGeneral;
import com.chd.hrp.ass.service.base.AssBaseService;
import com.chd.hrp.ass.service.card.AssCardGeneralService;
import com.chd.hrp.mat.dao.info.basic.MatPayTermMapper;
import com.chd.hrp.mat.entity.MatPayTerm;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Description:
 * 资产卡片维护_一般设备
 * @Table:
 * ASS_CARD_GENERAL
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 


@Service("assCardGeneralService")
public class AssCardGeneralServiceImpl implements AssCardGeneralService { 

	private static Logger logger = Logger.getLogger(AssCardGeneralServiceImpl.class);
	//引入DAO操作
	@Resource(name = "assCardGeneralMapper")
	private final AssCardGeneralMapper assCardGeneralMapper = null;
	
	@Resource(name = "assResourceGeneralMapper")
	private final AssResourceGeneralMapper assResourceGeneralMapper = null;
	
	@Resource(name = "assShareDeptGeneralMapper")
	private final AssShareDeptGeneralMapper assShareDeptGeneralMapper = null;
	
	@Resource(name = "assShareDeptRGeneralMapper")
	private final AssShareDeptRGeneralMapper assShareDeptRGeneralMapper = null;
	
	@Resource(name = "assFileGeneralMapper")
	private final AssFileGeneralMapper assFileGeneralMapper = null;
	
	@Resource(name = "assPhotoGeneralMapper")
	private final AssPhotoGeneralMapper assPhotoGeneralMapper = null;
	
	@Resource(name = "assAccessoryGeneralMapper")
	private final AssAccessoryGeneralMapper assAccessoryGeneralMapper = null;
	
	@Resource(name = "assDepreAccGeneralMapper")
	private final AssDepreAccGeneralMapper assDepreAccGeneralMapper = null;
	
	@Resource(name = "assDepreManageGeneralMapper")
	private final AssDepreManageGeneralMapper assDepreManageGeneralMapper = null;
	
	@Resource(name = "assPayStageGeneralMapper")
	private final AssPayStageGeneralMapper assPayStageGeneralMapper = null;
	
	@Resource(name = "assBaseService")
	private final AssBaseService assBaseService = null;
	
	@Resource(name = "assPrePayMapper")
	private final AssPrePayMapper assPrePayMapper = null;

	@Resource(name = "matPayTermMapper")
	private final MatPayTermMapper matPayTermMapper = null;
    
	/**
	 * @Description 
	 * 添加资产卡片维护_一般设备<BR> 
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
			
			Map<String, Object> delMap = new HashMap<String, Object>();
			delMap.put("group_id", entityMap.get("group_id"));
			delMap.put("hos_id", entityMap.get("hos_id"));
			delMap.put("copy_code", entityMap.get("copy_code"));
			delMap.put("ass_card_no", entityMap.get("ass_card_no_old"));
			assShareDeptRGeneralMapper.delete(delMap);
			assShareDeptGeneralMapper.delete(delMap);
			assResourceGeneralMapper.delete(delMap);
			assFileGeneralMapper.delete(delMap);
			assPhotoGeneralMapper.delete(delMap);
			assAccessoryGeneralMapper.delete(delMap);
			assPayStageGeneralMapper.delete(delMap);
			assDepreAccGeneralMapper.delete(delMap);
			assDepreManageGeneralMapper.delete(delMap);
			assCardGeneralMapper.delete(delMap);
			assCardGeneralMapper.add(entityMap);

			Double price = Double.parseDouble(entityMap.get("price").toString());

			/**
			 * 通过资产ID和供应商ID查询 查询预付款核定 如果存在，取出资金来源数据插入卡片资金来源 ，插入分期付款 如果资产原值 -
			 * 预付余额 - 回冲金额 = 0 那么分期付款为一条数据 如果资产原值 - 预付余额 - 回冲金额 > 0那么分期付款产生两条数据
			 * 资产原值 - 预付余额 - 回冲金额 = 未付金额
			 */
			entityMap.put("naturs_code", "03");
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
					Integer stage_no = assPayStageGeneralMapper.queryStageGeneralMaxNo(entityMap);
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
						Integer stage_no = assPayStageGeneralMapper.queryStageGeneralMaxNo(entityMap);
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
						Integer stage_no1 = assPayStageGeneralMapper.queryStageGeneralMaxNo(entityMap);
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
						Integer stage_no2 = assPayStageGeneralMapper.queryStageGeneralMaxNo(entityMap);
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
						Integer stage_no = assPayStageGeneralMapper.queryStageGeneralMaxNo(entityMap);
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
				Integer stage_no = assPayStageGeneralMapper.queryStageGeneralMaxNo(entityMap);
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
			
			assPayStageGeneralMapper.addBatch(payStageListVo);
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
			
			assResourceGeneralMapper.addBatch(reSourceListVo);
			
			if(payMoneyListVo.size() > 0){
				assPrePayMapper.updateBatchPayMoney(payMoneyListVo);
			}

			if (entityMap.get("dept_id") != null && !entityMap.get("dept_id").equals("")
					&& entityMap.get("dept_no") != null && !entityMap.get("dept_no").equals("")) {
				entityMap.put("area", 1);
				entityMap.put("ass_year", getAssYear());
				entityMap.put("ass_month", getAssMonth());

				assShareDeptRGeneralMapper.add(entityMap);

				assShareDeptGeneralMapper.add(entityMap);
			}

			String basePath = "ass";
			String group_id = entityMap.get("group_id").toString();
			String hos_id = entityMap.get("hos_id").toString();
			String copy_code = entityMap.get("copy_code").toString();
			String assTwoPath = "assbartwo";
			String assOnePath = "assbarone";

			String twoFilePath = group_id + "/" + hos_id + "/" + copy_code + "/" + basePath + "/" + assTwoPath + "/03/";// 资产性质为01
			String oneFilePath = group_id + "/" + hos_id + "/" + copy_code + "/" + basePath + "/" + assOnePath + "/03/";// 资产性质为01


			if (entityMap.get("is_bar") != null && !entityMap.get("is_bar").equals("")) {
				if (entityMap.get("is_bar").toString().equals("1")) {
					if (entityMap.get("bar_type") != null && !entityMap.get("bar_type").equals("")) {
						if (entityMap.get("bar_type").toString().equals("1")) {
							FtpUtil.getBarcodeWriteFile(entityMap.get("ass_card_no").toString(), "", oneFilePath,
									entityMap.get("ass_card_no") + ".png");// 一维码
							entityMap.put("bar_url", oneFilePath + entityMap.get("ass_card_no") + ".png");

						} else if (entityMap.get("bar_type").toString().equals("2")) {
							FtpUtil.getQRWriteFile(entityMap.get("ass_card_no").toString(), "", twoFilePath,
									entityMap.get("ass_card_no") + ".png");// 二维码
							entityMap.put("bar_url", twoFilePath + entityMap.get("ass_card_no") + ".png");
						}
					}
				}
			}

			assBaseService.updateAssBillNoMaxNo("ASS_CARD_GENERAL");
			return "{\"msg\":\"添加成功.\",\"state\":\"true\",\"ass_card_no\":\"" + entityMap.get("ass_card_no") + "\"}";

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());

		}

	}
	private String getAssYear() {
		String yearMonth = MyConfig.getCurAccYearMonth();
		return yearMonth.substring(0, 4);
	}

	private String getAssMonth() {
		String yearMonth = MyConfig.getCurAccYearMonth();
		return yearMonth.substring(4, 6);
	}
	
	/**
	 * @Description 
	 * 批量添加资产卡片维护_一般设备<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addBatch(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
			assCardGeneralMapper.addBatch(entityList);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());

		}
		
	}
	
		/**
	 * @Description 
	 * 更新资产卡片维护_一般设备<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String update(Map<String,Object> entityMap)throws DataAccessException{

		try {
			String basePath = "ass";
			String group_id = entityMap.get("group_id").toString();
			String hos_id = entityMap.get("hos_id").toString();
			String copy_code = entityMap.get("copy_code").toString();
			String assTwoPath = "assbartwo";
			String assOnePath = "assbarone";

			String twoFilePath = group_id + "/" + hos_id + "/" + copy_code + "/" + basePath +"/" + assTwoPath + "/03/";// 资产性质
			String oneFilePath = group_id + "/" + hos_id + "/" + copy_code + "/" + basePath +"/" + assOnePath + "/03/";// 资产性质


			
			AssCardGeneral assCardGeneral = assCardGeneralMapper.queryByCode(entityMap);
			if(assCardGeneral.getBar_url() != null && !assCardGeneral.getBar_url().equals("")){
				String file_name = assCardGeneral.getBar_url().substring(assCardGeneral.getBar_url().lastIndexOf("/") + 1,  assCardGeneral.getBar_url().length());
				String path =  assCardGeneral.getBar_url().substring(0, assCardGeneral.getBar_url().lastIndexOf("/"));
				FtpUtil.deleteFile(path, file_name);
			}
			
			if(entityMap.get("is_bar") != null && !entityMap.get("is_bar").equals("")){
				if(entityMap.get("is_bar").toString().equals("1")){
					if(entityMap.get("bar_type") != null && !entityMap.get("bar_type").equals("")){
						if(entityMap.get("bar_type").toString().equals("1")){
							FtpUtil.getBarcodeWriteFile(entityMap.get("bar_code").toString(),"",oneFilePath,entityMap.get("bar_code")+".png");//一维码
							entityMap.put("bar_url", oneFilePath + entityMap.get("bar_code")+".png");
							
						}else if(entityMap.get("bar_type").toString().equals("2")){
							FtpUtil.getQRWriteFile(entityMap.get("bar_code").toString(),"",twoFilePath,entityMap.get("bar_code")+".png");//二维码
							entityMap.put("bar_url", twoFilePath + entityMap.get("bar_code")+".png");
						}
					}
				}
			}
			int state = assCardGeneralMapper.update(entityMap);

			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());

		}

	}
	/**
	 * @Description 
	 * 批量更新资产卡片维护_一般设备<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateBatch(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
			
			
		  assCardGeneralMapper.updateBatch(entityList);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());

		}	
		
	}
	/**
	 * @Description 
	 * 删除资产卡片维护_一般设备<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
    public String delete(Map<String, Object> entityMap) throws DataAccessException {
    	
    try {
			
			int state = assCardGeneralMapper.delete(entityMap);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());

		}	
		
  }
    
	/**
	 * @Description 
	 * 批量删除资产卡片维护_一般设备<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String deleteBatch(List<Map<String,Object>> entityList)throws DataAccessException{

		try {
			// 删除条码文件
			for (Map<String, Object> map : entityList) {
				AssCardGeneral assCardGeneral = assCardGeneralMapper.queryByCode(map);
				if (assCardGeneral.getBar_url() != null && !assCardGeneral.getBar_url().equals("")) {
					String file_name = assCardGeneral.getBar_url().substring(
							assCardGeneral.getBar_url().lastIndexOf("/") + 1, assCardGeneral.getBar_url().length());
					String path = assCardGeneral.getBar_url().substring(0,
							assCardGeneral.getBar_url().lastIndexOf("/"));
					FtpUtil.deleteFile(path, file_name);
				}
			}

			// 删除资产文档
			for (Map<String, Object> fileMap : entityList) {
				List<AssFileGeneral> assFileGeneralList = (List<AssFileGeneral>) assFileGeneralMapper
						.queryExists(fileMap);
				if (assFileGeneralList.size() > 0 && assFileGeneralList != null) {
					for (AssFileGeneral assFileGeneral : assFileGeneralList) {
						if (assFileGeneral.getFile_url() != null && !assFileGeneral.getFile_url().equals("")) {
							String file_name = assFileGeneral.getFile_url().substring(
									assFileGeneral.getFile_url().lastIndexOf("/") + 1,
									assFileGeneral.getFile_url().length());
							String path = assFileGeneral.getFile_url().substring(0,
									assFileGeneral.getFile_url().lastIndexOf("/"));
							FtpUtil.deleteFile(path, file_name);
							path = path.substring(0,path.lastIndexOf("/"));
							FtpUtil.deleteDirectory(assFileGeneral.getAss_card_no(), path);
						}
					}
				}
			}

			// 删除资产照片
			for (Map<String, Object> photoMap : entityList) {
				List<AssPhotoGeneral> assPhotoGeneralList = (List<AssPhotoGeneral>) assPhotoGeneralMapper
						.queryExists(photoMap);
				if (assPhotoGeneralList.size() > 0 && assPhotoGeneralList != null) {
					for (AssPhotoGeneral assPhotoGeneral : assPhotoGeneralList) {
						if (assPhotoGeneral.getFile_url() != null && !assPhotoGeneral.getFile_url().equals("")) {
							String file_name = assPhotoGeneral.getFile_url().substring(
									assPhotoGeneral.getFile_url().lastIndexOf("/") + 1,
									assPhotoGeneral.getFile_url().length());
							String path = assPhotoGeneral.getFile_url().substring(0,
									assPhotoGeneral.getFile_url().lastIndexOf("/"));
							FtpUtil.deleteFile(path, file_name);
							path = path.substring(0,path.lastIndexOf("/"));
							FtpUtil.deleteDirectory(assPhotoGeneral.getAss_card_no(), path);
						}
					}
				}
			}

			assShareDeptRGeneralMapper.deleteBatch(entityList);
			assShareDeptGeneralMapper.deleteBatch(entityList);
			assResourceGeneralMapper.deleteBatch(entityList);
			assFileGeneralMapper.deleteBatch(entityList);
			assPhotoGeneralMapper.deleteBatch(entityList);
			assAccessoryGeneralMapper.deleteBatch(entityList);
			assPayStageGeneralMapper.deleteBatch(entityList);
			assDepreAccGeneralMapper.deleteBatch(entityList);
			assDepreManageGeneralMapper.deleteBatch(entityList);
			assCardGeneralMapper.deleteBatch(entityList);

			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());

		}
	}
	
	/**
	 * @Description 
	 * 添加资产卡片维护_一般设备<BR> 
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
		//判断是否存在对象资产卡片维护_一般设备
		Map<String, Object> mapVo=new HashMap<String, Object>();
		mapVo.put("group_id",entityMap.get("group_id"));
		mapVo.put("hos_id",entityMap.get("hos_id"));
    	mapVo.put("copy_code", entityMap.get("copy_code"));
    	mapVo.put("acct_year", entityMap.get("acct_year"));
		
		List<AssCardGeneral> list = (List<AssCardGeneral>)assCardGeneralMapper.queryExists(mapVo);
		
		if (list.size()>0) {

			int state = assCardGeneralMapper.update(entityMap);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		
		try {
			
			int state = assCardGeneralMapper.add(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());

		}
		
	}
	/**
	 * @Description 
	 * 查询结果集资产卡片维护_一般设备<BR> 
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
			
			List<AssCardGeneral> list = (List<AssCardGeneral>)assCardGeneralMapper.query(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<AssCardGeneral> list = (List<AssCardGeneral>)assCardGeneralMapper.query(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
		
	}
	
	/**
	 * @Description 
	 * 获取对象资产卡片维护_一般设备<BR> 
	 * @param  entityMap<BR>
	 *  参数为主键
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public <T> T queryByCode(Map<String,Object> entityMap)throws DataAccessException{
		return assCardGeneralMapper.queryByCode(entityMap);
	}
	
	/**
	 * @Description 
	 * 获取资产卡片维护_一般设备<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return AssCardGeneral
	 * @throws DataAccessException
	*/
	@Override
	public <T> T queryByUniqueness(Map<String,Object> entityMap)throws DataAccessException{
		return assCardGeneralMapper.queryByUniqueness(entityMap);
	}
	
	/**
	 * @Description 
	 * 获取资产卡片维护_一般设备<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return List<AssCardGeneral>
	 * @throws DataAccessException
	*/
	@Override
	public List<?> queryExists(Map<String,Object> entityMap)throws DataAccessException{
		return assCardGeneralMapper.queryExists(entityMap);
	}
	@Override
	public Integer queryCardExistsByAssInNo(Map<String, Object> entityMap) throws DataAccessException {
		return assCardGeneralMapper.queryCardExistsByAssInNo(entityMap);
	}

	@Override
	public List<Map<String, Object>> queryAssCardByAssInNo(Map<String, Object> entityMap) throws DataAccessException {
		return assCardGeneralMapper.queryByAssInNo(entityMap);
	}
	@Override
	public List<AssCardGeneral> queryCount(Map<String, Object> vCreateDateMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		return assCardGeneralMapper.queryCount(vCreateDateMap);
	}
	@Override
	public String updateIsBarPrint(List<Map<String, Object>> listCardVo) throws DataAccessException {
		try{
			assCardGeneralMapper.updateIsBarPrint(listCardVo);
			return "{\"msg\":\"打印成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}
	}
	@Override
	public List<Map<String,Object>> queryPrint(Map<String, Object> entityMap) throws DataAccessException {
		
		    entityMap.put("user_id", SessionManager.getUserId());
			List<Map<String,Object>> list = (List<Map<String,Object>>) assCardGeneralMapper.queryPrint(entityMap);
			return list;
	}
	
}
