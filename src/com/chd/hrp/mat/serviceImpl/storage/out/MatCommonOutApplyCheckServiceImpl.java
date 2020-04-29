/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.mat.serviceImpl.storage.out;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.chd.base.MyConfig;
import com.chd.base.SessionManager;
import com.chd.base.SysPage;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.base.util.DateUtil;
import com.chd.base.util.JsonListMapUtil;
import com.chd.base.util.NumberUtil;
import com.chd.hrp.acc.service.vouch.SuperPrintService;
import com.chd.hrp.mat.dao.affi.tran.MatAffiTranRelaMapper;
import com.chd.hrp.mat.dao.base.MatAffiInMapper;
import com.chd.hrp.mat.dao.base.MatApplyOutRelaMapper;
import com.chd.hrp.mat.dao.base.MatCommonMapper;
import com.chd.hrp.mat.dao.base.MatInCommonMapper;
import com.chd.hrp.mat.dao.base.MatNoManageMapper;
import com.chd.hrp.mat.dao.base.MatOutResourceMapper;
import com.chd.hrp.mat.dao.storage.out.MatCommonOutApplyCheckMapper;
import com.chd.hrp.mat.dao.storage.out.MatOutDetailMapper;
import com.chd.hrp.mat.dao.storage.out.MatOutMainMapper;
import com.chd.hrp.mat.dao.storage.tran.MatTranRelaMapper;
import com.chd.hrp.mat.entity.MatApplyMain;
import com.chd.hrp.mat.service.base.MatCommonService;
import com.chd.hrp.mat.service.storage.out.MatCommonOutApplyCheckService;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Description: 
 * 科室申请单主表
 * @Table:
 * MAT_APPLY_MAIN
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
@Service("matCommonOutApplyCheckService")
public class MatCommonOutApplyCheckServiceImpl implements MatCommonOutApplyCheckService {

	private static Logger logger = Logger.getLogger(MatCommonOutApplyCheckServiceImpl.class);
	//引入DAO操作
	@Resource(name = "matCommonOutApplyCheckMapper")
	private final MatCommonOutApplyCheckMapper matCommonOutApplyCheckMapper = null;
	@Resource(name = "matCommonMapper")
	private final MatCommonMapper matCommonMapper = null;
	@Resource(name = "matCommonService")
	private final MatCommonService matCommonService = null;
	@Resource(name = "matNoManageMapper")
	private final MatNoManageMapper matNoManageMapper = null;
	@Resource(name = "matOutMainMapper")
	private final MatOutMainMapper matOutMainMapper = null;
	@Resource(name = "matOutDetailMapper")
	private final MatOutDetailMapper matOutDetailMapper = null;
	@Resource(name = "matOutResourceMapper")
	private final MatOutResourceMapper matOutResourceMapper = null;
	@Resource(name = "matApplyOutRelaMapper")
	private final MatApplyOutRelaMapper matApplyOutRelaMapper = null;
	@Resource(name = "matInCommonMapper")
	private final MatInCommonMapper matInCommonMapper = null;
	@Resource(name = "matAffiInMapper")
	private final MatAffiInMapper matAffiInMapper = null;
	@Resource(name = "matTranRelaMapper")
	private final MatTranRelaMapper matTranRelaMapper = null;
	@Resource(name = "matAffiTranRelaMapper")
	private final MatAffiTranRelaMapper matAffiTranRelaMapper = null;
	
	
	/**
	 * @Description 
	 * 添加科室申请<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String add(Map<String,Object> entityMap)throws DataAccessException{
		try {
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);

			throw new SysException("{\"error\":\"添加失败\"}");
			//return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addMatCommonOutApplyCheck\"}";
		}
	}
	/**
	 * @Description 
	 * 批量添加科室申请<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addBatch(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			//暂无该业务
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);

			throw new SysException("{\"error\":\"添加失败\"}");
			//return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addBatchMatCommonOutApplyCheck\"}";
		}
	}
	
	/**
	 * @Description 
	 * 更新科室申请<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String update(Map<String,Object> entityMap)throws DataAccessException{
		
		try {
			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);

			throw new SysException("{\"error\":\"添加失败\"}");
			//return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addMatCommonOutApplyCheck\"}";
		}
	}
	
	/**
	 * @Description 
	 * 批量生成出库单<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addOutBatch(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			//金额位数
			int money_para = Integer.valueOf(MyConfig.getSysPara("04005"));
			//金额存数的时候默认不设置小数点几位
			//int money_para = 6;
			//存放出库单主信息
			List<Map<String, Object>> outMainList = new ArrayList<Map<String,Object>>();
			//存放出库单明细信息
			List<Map<String, Object>> outDetailList = new ArrayList<Map<String,Object>>();
			//存放出库单资金来源信息
			List<Map<String, Object>> outSourceList = new ArrayList<Map<String,Object>>();
			//存放科室申请明细信息
			List<Map<String, Object>> applyRelaList = new ArrayList<Map<String,Object>>();
			//出库材料Map，用于判断库存(key : inv_id)
			Map<String, List<Map<String, Object>>> immeMap = new HashMap<String, List<Map<String,Object>>>();
			//材料Map的key值
			String immeKey = "";
			
			String brief = "";
			//记录未启用的库房
			StringBuffer errorMsg = new StringBuffer();
			//记录库存不足的材料
//			StringBuffer invEnoughMsg = new StringBuffer();
			//记录总金额
			double money_sum = 0;
			
			Date date = new Date();
			String dt = DateUtil.dateToString(date, "yyyy-MM-dd");
			String year = dt.substring(0, 4);
			String month = dt.substring(5, 7);
			String day = dt.substring(8, 10);
			  
			//循环单据生成出库单
			for(Map<String, Object> entityMap : entityList){ 
				//初始化总金额
				money_sum = 0;
				//主表数据
				Map<String, Object> appMap = matCommonOutApplyCheckMapper.queryMatApplyMainByCode(entityMap);
				  
				Map<String, Object> mainMap = new HashMap<String, Object>(); 
				//出库单主表信息
				mainMap.put("group_id", appMap.get("group_id"));
				mainMap.put("hos_id", appMap.get("hos_id"));
				mainMap.put("copy_code", appMap.get("copy_code"));
				mainMap.put("bus_type_code", "3");  //科室领用
				mainMap.put("maker", entityMap.get("user_id").toString());  //制单人
				mainMap.put("out_date", date);  //出库日期
				mainMap.put("year", year);  //期间年
				mainMap.put("month", month);  //期间月
				mainMap.put("day", day);  //用于生成单号
				mainMap.put("store_id", appMap.get("store_id").toString());
				mainMap.put("store_no", appMap.get("store_no").toString());
				mainMap.put("dept_id", appMap.get("dept_id").toString());
				mainMap.put("dept_no", appMap.get("dept_no").toString());
				mainMap.put("dept_emp", appMap.get("app_emp") == null ? null : appMap.get("app_emp").toString());
				brief=appMap.get("brief") == null ? null : appMap.get("brief").toString();
				mainMap.put("brief", "由科室申领单"+appMap.get("apply_no").toString()+"生成----"+brief);
				mainMap.put("state", "1");
				mainMap.put("use_code", null);
				mainMap.put("proj_id", null); 
				if(appMap.get("app_date") != null && !"".equals(appMap.get("app_date").toString())){
					mainMap.put("app_date", DateUtil.stringToDate(appMap.get("app_date").toString(), "yyyy-MM-dd HH:mm:ss"));  
				}else{
					mainMap.put("app_date", null);
				}
				
				//判断库房是否已启用
				String store_flag = matCommonMapper.queryMatStoreIsStart(mainMap);
				if(store_flag != null && !"".equals(store_flag)){
					errorMsg.append(store_flag).append("<br/>");
					continue;
				}
				
				//获取单号
				mainMap.put("table_code", "MAT_OUT_MAIN");
				String out_no = matCommonService.getMatNextNo(mainMap);
				if(out_no.indexOf("error") > 0){
					return out_no;
				}
				mainMap.put("out_no", out_no);
				mainMap.put("out_id", String.valueOf(matCommonOutApplyCheckMapper.queryMatOutMainSeqNext()));
				
				//明细信息
				List<Map<String, Object>> detailList = toListMapLower((List<Map<String, Object>>) matCommonOutApplyCheckMapper.queryMatApplyDetailByCode(entityMap));
				
				for(Map<String, Object> map : detailList){
					map.put("store_id", mainMap.get("store_id").toString()); //用于查询帐表
					Double amount = Double.valueOf(map.get("app_amount") == null ? "0" : map.get("app_amount").toString());
					Double imme_amount = null;
					immeKey = map.get("inv_id").toString();
					//先进先出材料列表
					List<Map<String, Object>> fifoList;
					if(immeMap.containsKey(immeKey)){
						fifoList = immeMap.get(immeKey);
					}else{
						//fifo帐表信息
						fifoList = toListMapLower((List<Map<String, Object>>) matCommonMapper.queryMatApplyCheckInvDetailList(map));
					}
					//按先进先出生成出库单
					for(Map<String, Object> fifoMap : fifoList){
						//当前批次即时库存
						imme_amount = Double.valueOf(String.valueOf((fifoMap.get("imme_amount") == null ? 0 : fifoMap.get("imme_amount"))));
						
						if(imme_amount == 0){
							continue;
						}
						
						Map<String, Object> detailMap = new HashMap<String, Object>();
						Map<String, Object> relaMap = new HashMap<String, Object>();
						
						detailMap.put("group_id", mainMap.get("group_id").toString());
						detailMap.put("hos_id", mainMap.get("hos_id").toString());
						detailMap.put("copy_code", mainMap.get("copy_code").toString());
						detailMap.put("out_id", mainMap.get("out_id").toString());
						detailMap.put("out_no", mainMap.get("out_no").toString());
						detailMap.put("out_detail_id", String.valueOf(matCommonOutApplyCheckMapper.queryMatOutDetailSeqNext()));
						
						//处理对应关系
						relaMap.put("group_id", appMap.get("group_id").toString());
						relaMap.put("hos_id", appMap.get("hos_id").toString());
						relaMap.put("copy_code", appMap.get("copy_code").toString());
						relaMap.put("apply_id", appMap.get("apply_id").toString());
						relaMap.put("app_detail_id", map.get("detail_id"));
						relaMap.put("rela_type", "1");
						relaMap.put("rela_id", detailMap.get("out_id").toString());
						relaMap.put("rela_detail_id", detailMap.get("out_detail_id"));

						detailMap.put("inv_id", map.get("inv_id").toString());
						detailMap.put("inv_no", map.get("inv_no").toString());
						detailMap.put("price", fifoMap.get("price").toString());
						detailMap.put("sale_price", fifoMap.get("sale_price") == null ? "0" : fifoMap.get("sale_price").toString());
						detailMap.put("sell_price", fifoMap.get("sell_price") == null ? "0" : fifoMap.get("sell_price").toString());
						detailMap.put("batch_no", fifoMap.get("batch_no").toString());
						detailMap.put("batch_sn", fifoMap.get("batch_sn").toString());
						detailMap.put("bar_code", fifoMap.get("bar_code").toString());
						if(ChdJson.validateJSON(String.valueOf(fifoMap.get("inva_date")))){
							detailMap.put("inva_date", (Date)fifoMap.get("inva_date"));
						}else{
							detailMap.put("inva_date", null);
						}
						if(ChdJson.validateJSON(String.valueOf(fifoMap.get("disinfect_date")))){
							detailMap.put("disinfect_date", (Date)fifoMap.get("disinfect_date"));
						}else{
							detailMap.put("disinfect_date", null);
						}
						if(ChdJson.validateJSON(String.valueOf(fifoMap.get("location_id")))){
							detailMap.put("location_id", fifoMap.get("location_id").toString());
						}else{
							detailMap.put("location_id", "0");
						}
						detailMap.put("note", map.get("note"));
						//判断当前批号批次是否充足
						if(amount <= imme_amount){
							detailMap.put("amount", amount.toString());
							//计算金额
							detailMap.put("amount_money", String.valueOf(NumberUtil.numberToRound(amount * Double.valueOf(String.valueOf((fifoMap.get("price") == null ? 0 : fifoMap.get("price")))), money_para)));
							detailMap.put("sale_money", String.valueOf(NumberUtil.numberToRound(amount * Double.valueOf(String.valueOf((fifoMap.get("sale_price") == null ? 0 : fifoMap.get("sale_price")))), money_para)));
							detailMap.put("sell_money", String.valueOf(NumberUtil.numberToRound(amount * Double.valueOf(String.valueOf((fifoMap.get("sell_price") == null ? 0 : fifoMap.get("sell_price")))), money_para)));
							detailMap.put("allot_money", String.valueOf(NumberUtil.numberToRound(amount * Double.valueOf(String.valueOf((fifoMap.get("allot_price") == null ? 0 : fifoMap.get("allot_price")))), money_para)));
							//money_sum = money_sum + Float.parseFloat(detailMap.get("amount_money").toString());//记录总金额
							money_sum = NumberUtil.add(Double.valueOf(money_sum), Double.valueOf(detailMap.get("amount_money").toString()));//记录总金额
							
							outDetailList.add(detailMap);
							relaMap.put("rela_amount", amount.toString());
							applyRelaList.add(relaMap);
							fifoMap.put("imme_amount", NumberUtil.sub(Double.valueOf(fifoMap.get("imme_amount").toString()), amount));
							amount = 0.0;
							break;
						}else{
							//取当前批号批次数量并且申请单数量响应减少
							detailMap.put("amount", imme_amount.toString());		
							//计算金额
							detailMap.put("amount_money", String.valueOf(NumberUtil.numberToRound(imme_amount * Double.valueOf(String.valueOf((fifoMap.get("price") == null ? 0 : fifoMap.get("price")))), money_para)));
							detailMap.put("sale_money", String.valueOf(NumberUtil.numberToRound(imme_amount * Double.valueOf(String.valueOf((fifoMap.get("sale_price") == null ? 0 : fifoMap.get("sale_price")))), money_para)));
							detailMap.put("sell_money", String.valueOf(NumberUtil.numberToRound(imme_amount * Double.valueOf(String.valueOf((fifoMap.get("sell_price") == null ? 0 : fifoMap.get("sell_price")))), money_para)));
							detailMap.put("allot_money", String.valueOf(NumberUtil.numberToRound(imme_amount * Double.valueOf(String.valueOf((fifoMap.get("allot_price") == null ? 0 : fifoMap.get("allot_price")))), money_para)));
							//money_sum = money_sum + Float.parseFloat(detailMap.get("amount_money").toString());//记录总金额
							money_sum = NumberUtil.add(Double.valueOf(money_sum), Double.valueOf(detailMap.get("amount_money").toString()));//记录总金额
							outDetailList.add(detailMap);			
							relaMap.put("rela_amount", imme_amount.toString());
							applyRelaList.add(relaMap);
							fifoMap.put("imme_amount", 0);
							amount = NumberUtil.sub(amount, imme_amount);
						}
						//当数量为0，证明已经完成先进先出操作
						if(amount == 0){
							break;
						}
					}
					immeMap.put(immeKey, fifoList);
					/** 由于医院提出库存有多少就出多少，不需要提示库存不足的信息，故注释以下代码
					if(amount > 0){
						invEnoughMsg.append(String.valueOf(map.get("inv_code"))).append(" ").append(String.valueOf(map.get("inv_name"))).append("<br>");
					}
					*/
				}  
				mainMap.put("come_from", 2);
				mainMap.put("amount_money", money_sum);
				outMainList.add(mainMap);
				mainMap.put("source_money", money_sum);
				outSourceList.add(mainMap);
			}
			
			//存在未启用库房
			if(errorMsg != null && !"".equals(errorMsg.toString())){
				return "{\"error\":\"以下库房本期间未启用：<br/>"+errorMsg.toString()+"\"}";
			}

			/** 由于医院提出库存有多少就出多少，不需要提示库存不足的信息，故注释以下代码
			if(invEnoughMsg != null && !"".equals(invEnoughMsg.toString())){
				return "{\"error\":\"以下材料库存物资不足：<br/>"+invEnoughMsg.toString()+"\"}";
			}
			*/
			if(outMainList.size() == 0 || outDetailList.size() == 0){
				return "{\"error\":\"所选单据库存物资不足\"}";
			}
			 
			//保存出库单主表
			matCommonOutApplyCheckMapper.addMatOutMainByAppBatch(outMainList);
			//保存出库单明细表
			matCommonOutApplyCheckMapper.addMatOutDetailByApp(outDetailList);
			//保存资金来源表
			matOutResourceMapper.addBatch(outSourceList);
			//保存对应关系
			matApplyOutRelaMapper.addMatApplyOutRelaBatch(applyRelaList);
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);

			throw new SysException("{\"error\":\"操作失败\"}");
			//return "{\"error\":\"更新失败 数据库异常 请联系管理员! 方法 addOutBatchMatCommonOutApplyCheck\"}";
		}	
		
		return "{\"msg\":\"操作成功.\",\"state\":\"true\"}";
	}
	
	/**
	 * @Description 
	 * 汇总生成出库单<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public Map<String, Object> queryOutMainByCollect(List<Map<String,Object>> entityList)throws DataAccessException{
		boolean is_frist = true;
		
		Map<String, Object> mainMap = new HashMap<String, Object>(); 
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
		String Apptime= "1900-01-01 00:00:00";  
		 
		for(Map<String, Object> entityMap : entityList){
			
			//变换apptime的类型为Date
			Date bt = null;
			try {
				bt = sdf.parse(Apptime);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//出库单主表信息--只提取一次
			if(is_frist){
				//查询科室申请主表数据
				mainMap = toMapLower(matCommonOutApplyCheckMapper.queryOutMain(entityMap));
				String app_date = mainMap.get("app_date").toString();
				
				//变换app_date的类型为date
				Date et = null;
				try {
					et = sdf.parse(app_date);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			  
				//判断app_date  取所有数据中的最大的时间  即墨需求  2017/04/07
				if (bt.before(et)){ 
					//表示bt小于et
					Apptime =  mainMap.get("app_date").toString() ; 
					 
					}  
				  
				//mainMap.put("brief", "由科室申领单汇总生成");
			}
			is_frist = true;
		}
		//将得到的时间转为Date类型
		Date dateAppDate = null;
		try {
			dateAppDate = sdf.parse(Apptime); 
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		mainMap.put("app_date",  DateUtil.dateToString(dateAppDate, "yyyy-MM-dd HH:mm:ss") );  
		 
		return mainMap;
	}
	
	
	/**
	 * @Description 
	 * 汇总生成出库单<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String queryOutDetailByCollect(List<Map<String,Object>> entityList)throws DataAccessException{
		//存放出库单明细信息
		List<Map<String, Object>> outDetailList = new ArrayList<Map<String,Object>>();
		
		//出库材料Map，用于合并相同材料(key：inv_id+batch_no+bar_code+price+location_id，value：明细Map)
		Map<String, Map<String, Object>> invMap = new HashMap<String, Map<String, Object>>();

		//用于合并相同批次的材料明细信息(key：invMapKey+batch_sn，value：批次明细Map)
		Map<String, Map<String, Object>> invJsonMap = new HashMap<String, Map<String, Object>>();

		String invKey = "";  //材料Map的key值
		String immeKey = "";	//材料Map的key值
		String invJsonKey = "";  //材料JsonMap的key值
		boolean isInvAlready = false;  //判断材料是否存在Map中
		boolean isInvJsonAlready = false;  //判断材料是否存在Map中
		StringBuffer invEnoughMsg = new StringBuffer();  //记录库存不足的材料
		//出库材料Map，用于判断库存(key : inv_id，value：材料库存List)
		Map<String, List<Map<String, Object>>> immeMap = new HashMap<String, List<Map<String,Object>>>();
		//记录处理的上一个申请单的序号 
		int applyMainPre=0;
		Map<String, Object> isFirstInvAppMap=new HashMap<String, Object>();//是否是该材料的第一个申请单
		
		try {
			//循环单据生成出库单
		    for(int applyMainCur=0;applyMainCur<entityList.size();applyMainCur++){
		    	
		    	Map<String, Object> entityMap = entityList.get(applyMainCur); 
				//明细信息
				List<Map<String, Object>> detailList = toListMapLower((List<Map<String, Object>>) matCommonOutApplyCheckMapper.queryDetailByCollect(entityMap));
				
				for(Map<String, Object> map : detailList){
					double amount = Double.valueOf(map.get("amount") == null ? "0" : map.get("amount").toString());
					//如果数量为0则不获取该材料
					if(amount == 0){
						continue;
					}
					double imme_amount = 0;
					double do_amount = 0;
					immeKey = map.get("inv_id").toString();
					//先进先出材料列表
					List<Map<String, Object>> fifoList;
					if(immeMap.containsKey(immeKey)){
						fifoList = immeMap.get(immeKey);
					}else{
						//fifo帐表信息
						fifoList = toListMapLower((List<Map<String, Object>>) matCommonMapper.queryMatStockInvDetailList(map));
					}
					
					//存放明细记录
					Map<String, Object> detailMap = new HashMap<String, Object>();
					detailMap.put("group_id", map.get("group_id"));
					detailMap.put("hos_id", map.get("hos_id"));
					detailMap.put("copy_code", map.get("copy_code"));
					detailMap.put("store_id", map.get("store_id"));
					detailMap.put("inv_id", map.get("inv_id"));
					detailMap.put("inv_no", map.get("inv_no"));
					detailMap.put("inv_code", map.get("inv_code"));
					detailMap.put("inv_name", map.get("inv_name"));
					detailMap.put("location_new_id", map.get("location_new_id"));
					detailMap.put("location_new_code", map.get("location_new_code"));
					detailMap.put("location_new_name", map.get("location_new_name"));
					detailMap.put("note", map.get("note"));
					if (fifoList.size() > 0) {
					//按先进先出生成出库单
						for(Map<String, Object> fifoMap : fifoList){
							
							//当前批次即时库存
							imme_amount = Double.valueOf(String.valueOf((fifoMap.get("imme_amount") == null ? 0 : fifoMap.get("imme_amount"))));
							if(imme_amount == 0){
								continue;
							}
							
							//获取invKey
							invKey = map.get("inv_id").toString() + fifoMap.get("batch_no").toString() + fifoMap.get("bar_code").toString() + fifoMap.get("price").toString();
							if(amount > 0){
								invJsonKey = invKey + fifoMap.get("batch_sn").toString();
							}

							//初始化变量--材料是否存在
							isInvAlready = false;
							//如果该材料已存在
							if(invMap.containsKey(invKey)){
								//获取已有的detailMap
								detailMap.putAll(invMap.get(invKey));
								isInvAlready = true;
							}
							
							Map<String,Object> detailJsonMap;
							if(invJsonMap.containsKey(invJsonKey)){
								//获取已有的detailJsonMap
								detailJsonMap = invJsonMap.get(invJsonKey);
								isInvJsonAlready = true;
							}else{
								//新建detailJsonMap
								detailJsonMap = new HashMap<String,Object>();
								isInvJsonAlready=false;
							}
							
							//材料是否已出完
							if(amount > 0){
								/******************材料主信息********begin******************************/
								if(!isInvAlready){
									//如果该材料不存在则需要添加新的明细记录
									/**---------------------数量初始化--begin---------------------*/
									detailMap.put("amount", 0);
									detailMap.put("sum_amount", 0);
									detailMap.put("cur_amount", 0);
									detailMap.put("imme_amount", 0);
									detailMap.put("amount_money", 0);
									detailMap.put("sale_money", 0);
									detailMap.put("sell_money", 0);
									/**---------------------数量初始化--end-----------------------*/
									detailMap.put("inv_id", fifoMap.get("inv_id"));
									detailMap.put("inv_no", fifoMap.get("inv_no"));
									detailMap.put("inv_code", fifoMap.get("inv_code"));
									detailMap.put("inv_name", fifoMap.get("inv_name"));
									detailMap.put("inv_model", fifoMap.get("inv_model"));
									detailMap.put("unit_name", fifoMap.get("unit_name"));
									detailMap.put("mat_type_name", fifoMap.get("mat_type_name"));
									detailMap.put("fac_name", fifoMap.get("fac_name"));
									detailMap.put("batch_no", fifoMap.get("batch_no"));
									detailMap.put("bar_code", fifoMap.get("bar_code"));
									detailMap.put("price", fifoMap.get("price"));
									detailMap.put("sale_price", fifoMap.get("sale_price"));
									detailMap.put("sell_price", fifoMap.get("sell_price"));
									detailMap.put("location_id", fifoMap.get("location_id"));
									detailMap.put("location_code", fifoMap.get("location_code"));
									detailMap.put("location_name", fifoMap.get("location_name"));
 									if(ChdJson.validateJSON(String.valueOf(fifoMap.get("inva_date")))){
										detailMap.put("inva_date", DateUtil.dateToString((Date)fifoMap.get("inva_date"), "yyyy-MM-dd"));
									}else{
										detailMap.put("inva_date", fifoMap.get("inva_date"));
									}
									if(ChdJson.validateJSON(String.valueOf(fifoMap.get("disinfect_date")))){
										detailMap.put("disinfect_date", DateUtil.dateToString((Date)fifoMap.get("disinfect_date"), "yyyy-MM-dd"));
									}else{
										detailMap.put("disinfect_date", fifoMap.get("disinfect_date"));
									}
									/**---------------------数量计算---------------------*/
									detailMap.put("cur_amount", fifoMap.get("cur_amount"));
									detailMap.put("imme_amount", fifoMap.get("imme_amount"));
								}else{
									if (!isFirstInvAppMap.containsKey(map.get("inv_id").toString())) {
										//库存信息累加
										detailMap.put("cur_amount", Double.valueOf(detailMap.get("cur_amount").toString())+Double.valueOf(fifoMap.get("cur_amount").toString()));
										detailMap.put("imme_amount", Double.valueOf(detailMap.get("imme_amount").toString())+Double.valueOf(fifoMap.get("imme_amount").toString()));
									}
									
								}
								/******************材料主信息********end********************************/
								/******************材料批次信息******begin******************************/
								if(!isInvJsonAlready){
									detailJsonMap.put("inv_id", fifoMap.get("inv_id"));
									detailJsonMap.put("inv_no", fifoMap.get("inv_no"));
									detailJsonMap.put("inv_code", fifoMap.get("inv_code"));
									detailJsonMap.put("inv_name", fifoMap.get("inv_name"));
									detailJsonMap.put("batch_sn", fifoMap.get("batch_sn"));
									detailJsonMap.put("cur_amount", fifoMap.get("cur_amount"));
									detailJsonMap.put("imme_amount", fifoMap.get("imme_amount"));
									detailJsonMap.put("price", fifoMap.get("price"));
									detailJsonMap.put("sale_price", fifoMap.get("sale_price"));
									detailJsonMap.put("sell_price", fifoMap.get("sell_price"));
									detailJsonMap.put("amount", 0);
									detailJsonMap.put("amount_money", 0);
									detailJsonMap.put("sale_money", 0);
									detailJsonMap.put("sell_money", 0);
								}
								
								//本批次数量充足直接出库
								if(amount <= imme_amount){
									detailMap.put("amount", Double.valueOf(detailMap.get("amount").toString()) + amount);
									detailMap.put("sum_amount", Double.valueOf(detailMap.get("sum_amount").toString()) + amount);
									detailMap.put("amount_money", Double.valueOf(detailMap.get("amount_money").toString()) + amount*Double.valueOf(String.valueOf((fifoMap.get("price") == null ? 0 : fifoMap.get("price")))));
									detailMap.put("sale_money", Double.valueOf(detailMap.get("sale_money").toString()) + amount*Double.valueOf(String.valueOf((fifoMap.get("sale_price") == null ? 0 : fifoMap.get("sale_price")))));
									detailMap.put("sell_money", Double.valueOf(detailMap.get("sell_money").toString()) + amount*Double.valueOf(String.valueOf((fifoMap.get("sell_price") == null ? 0 : fifoMap.get("sell_price")))));
									
									detailJsonMap.put("amount", Double.valueOf(detailJsonMap.get("amount").toString()) + amount);
									detailJsonMap.put("amount_money", Double.valueOf(detailJsonMap.get("amount_money").toString()) + amount*Double.valueOf(String.valueOf((fifoMap.get("price") == null ? 0 : fifoMap.get("price")))));
									detailJsonMap.put("sale_money", Double.valueOf(detailJsonMap.get("sale_money").toString()) + amount*Double.valueOf(String.valueOf((fifoMap.get("sale_price") == null ? 0 : fifoMap.get("sale_price")))));
									detailJsonMap.put("sell_money", Double.valueOf(detailJsonMap.get("sell_money").toString()) + amount*Double.valueOf(String.valueOf((fifoMap.get("sell_price") == null ? 0 : fifoMap.get("sell_price")))));
									
									fifoMap.put("imme_amount", NumberUtil.sub(imme_amount, amount));
									do_amount += amount;  //实际处理数量
									amount = 0.0;
								}else{//本批次数量不足，先出完本批次剩余数量并减少出库数量再出下一批次
									detailMap.put("amount", Double.valueOf(detailMap.get("amount").toString()) + imme_amount);
									detailMap.put("sum_amount", Double.valueOf(detailMap.get("sum_amount").toString()) + imme_amount);
									detailMap.put("amount_money", Double.valueOf(detailMap.get("amount_money").toString()) + imme_amount*Double.valueOf(String.valueOf((fifoMap.get("price") == null ? 0 : fifoMap.get("price")))));
									detailMap.put("sale_money", Double.valueOf(detailMap.get("sale_money").toString()) + imme_amount*Double.valueOf(String.valueOf((fifoMap.get("sale_price") == null ? 0 : fifoMap.get("sale_price")))));
									detailMap.put("sell_money", Double.valueOf(detailMap.get("sell_money").toString()) + imme_amount*Double.valueOf(String.valueOf((fifoMap.get("sell_price") == null ? 0 : fifoMap.get("sell_price")))));

									detailJsonMap.put("amount", Double.valueOf(detailJsonMap.get("amount").toString()) + imme_amount);
									detailJsonMap.put("amount_money", Double.valueOf(detailJsonMap.get("amount_money").toString()) + imme_amount*Double.valueOf(String.valueOf((fifoMap.get("price") == null ? 0 : fifoMap.get("price")))));
									detailJsonMap.put("sale_money", Double.valueOf(detailJsonMap.get("sale_money").toString()) + imme_amount*Double.valueOf(String.valueOf((fifoMap.get("sale_price") == null ? 0 : fifoMap.get("sale_price")))));
									detailJsonMap.put("sell_money", Double.valueOf(detailJsonMap.get("sell_money").toString()) + imme_amount*Double.valueOf(String.valueOf((fifoMap.get("sell_price") == null ? 0 : fifoMap.get("sell_price")))));
									
									fifoMap.put("imme_amount", 0);
									do_amount += imme_amount;  //实际处理数量
									amount = NumberUtil.sub(amount, imme_amount);
								}
								/******************材料批次信息******end********************************/
							}else{
								/**数量为0判断是否继续累加库存还是跳出循环********begin***************/
								if(isInvAlready){
									//库存信息累加
									if (!isFirstInvAppMap.containsKey(map.get("inv_id").toString())) {
										detailMap.put("cur_amount", Double.valueOf(detailMap.get("cur_amount").toString())+Double.valueOf(fifoMap.get("cur_amount").toString()));
										detailMap.put("imme_amount", Double.valueOf(detailMap.get("imme_amount").toString())+Double.valueOf(fifoMap.get("imme_amount").toString()));
									}
									//清空fifo中的剩余数量防止这次没出完下次循环的时候重复累计
									/*
									//liusiqi  180420 如果有了不需要清空fifo里面的数据,因为
									fifoMap.put("cur_amount", 0);
									fifoMap.put("imme_amount", 0);*/
								}/*else{
									//记录材料信息，并跳出循环
									if(detailMap.get("inv_id") != null){
										//处理科室申领对应关系
										if(map.get("apply_id") != null && !"".equals(map.get("apply_id").toString()) && do_amount != 0){
											String other_ids = detailMap.get("other_ids") == null ? "" : detailMap.get("other_ids").toString();
											if("".equals(other_ids)){
												other_ids = map.get("apply_id").toString() + "@" + map.get("detail_id").toString() + "@" + do_amount;
											}else{
												other_ids += "," + map.get("apply_id").toString() + "@" + map.get("detail_id").toString() + "@" + do_amount;
											}
											do_amount = 0;
											detailMap.put("other_ids", other_ids);
										}
										//存储
										invMap.put(invKey, detailMap);
										invJsonMap.put(invJsonKey, detailJsonMap);
									}
									break;
								}*/
								/**数量为0判断是否继续累加库存还是跳出循环********end*****************/
							}
							//记录材料信息
							if(detailMap.get("inv_id") != null){
								//处理科室申领对应关系
								if(map.get("apply_id") != null && !"".equals(map.get("apply_id").toString()) && do_amount != 0){
									String other_ids = detailMap.get("other_ids") == null ? "" : detailMap.get("other_ids").toString();
									if("".equals(other_ids)){
										other_ids = map.get("apply_id").toString() + "@" + map.get("detail_id").toString() + "@" + do_amount;
									}else{
										other_ids += "," + map.get("apply_id").toString() + "@" + map.get("detail_id").toString() + "@" + do_amount;
									}
									do_amount = 0;
									detailMap.put("other_ids", other_ids);
								}
								//存储
								invMap.put(invKey, detailMap);
								invJsonMap.put(invJsonKey, detailJsonMap);
							}
							
							detailMap = new HashMap<String, Object>();
							do_amount = 0;
						}
						
						immeMap.put(immeKey, fifoList);
					}
					isFirstInvAppMap.put( map.get("inv_id").toString(),"");
				}
				applyMainPre=applyMainCur;//处理完成本生清单后,改变上一个申请单序号,记录为刚刚处理完成的这个
			}
			
			for (String key : invMap.keySet()) {
				Map<String, Object> detailMap = invMap.get(key);
				StringBuffer detailJson = new StringBuffer();
				detailJson.append("[");
				for(String jsonKey : invJsonMap.keySet()){
					if(jsonKey.startsWith(key)){
						Map<String, Object> detailJsonMap = invJsonMap.get(jsonKey);
						detailJson.append("{\"inv_id\":").append(detailJsonMap.get("inv_id"))
							.append(",\"inv_no\":").append(detailJsonMap.get("inv_no"))
							.append(",\"inv_code\":\"").append(detailJsonMap.get("inv_code"))
							.append("\",\"inv_name\":\"").append(detailJsonMap.get("inv_name"))
							.append("\",\"batch_sn\":").append(detailJsonMap.get("batch_sn"))
							.append(",\"cur_amount\":").append(detailJsonMap.get("cur_amount"))
							.append(",\"imme_amount\":").append(detailJsonMap.get("imme_amount"))
							.append(",\"price\":").append(detailJsonMap.get("price"))
							.append(",\"sale_price\":").append(detailJsonMap.get("sale_price"))
							.append(",\"sell_price\":").append(detailJsonMap.get("sell_price"))
							.append(",\"amount\":").append(detailJsonMap.get("amount"))
							.append(",\"amount_money\":").append(detailJsonMap.get("amount_money"))
							.append(",\"sale_money\":").append(detailJsonMap.get("sale_money"))
							.append(",\"sell_money\":").append(detailJsonMap.get("sell_money"))
							.append(",\"location_new_name\":").append(detailJsonMap.get("location_new_name"))
							.append(",\"note\":").append(detailJsonMap.get("note"))
							.append("},");
					}
				}
				detailJson.substring(0, detailJson.length()-1);
				detailJson.append("]");
				detailMap.put("inv_detail_data", detailJson.toString());
				outDetailList.add(detailMap);
			}
			
			if(outDetailList.size() == 0){
				return "{\"error\":\"所选单据材料库存物资不足\"}";
			}
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);

			throw new SysException("{\"error\":\"操作失败\"}");
			//return "{\"error\":\"更新失败 数据库异常 请联系管理员! 方法 addOutBatchMatCommonOutApplyCheck\"}";
		}	
		System.out.println("++++++++++++++"+ChdJson.toJson(outDetailList));
		return ChdJson.toJson(outDetailList);
	}
	
	/**
	 * @Description 
	 * 批量更新科室申请，无此业务<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateBatch(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			//暂无该业务
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);

			throw new SysException("{\"error\":\"更新失败\"}");
			//return "{\"error\":\"更新失败 数据库异常 请联系管理员! 方法 updateBatchMatCommonOutApplyCheck\"}";
		}	
	}
	/**
	 * @Description 
	 * 删除科室申请<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
    public String delete(Map<String, Object> entityMap) throws DataAccessException {
    	
		try {
			//暂无该业务
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);

			throw new SysException("{\"error\":\"删除失败\"}");
			//return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 deleteMatCommonOutApplyCheck\"}";
		}	
  }
    
	/**
	 * @Description 
	 * 批量删除科室申请<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String deleteBatch(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {	
			//暂无该业务
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);

			throw new SysException("{\"error\":\"删除失败\"}");
			//return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 deleteBatchMatCommonOutApplyCheck\"}";
		}	
	}

	@Override
	public String updateBackMatCommonOutApplyCheckBatch(List<Map<String, Object>> entityMap) throws DataAccessException {
		
		try {	
			//批量退回
			matCommonOutApplyCheckMapper.updateMatApplyBackBatch(entityMap);
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);

			throw new SysException("{\"error\":\"操作失败\"}");
			//return "{\"error\":\"操作失败 数据库异常 请联系管理员! 方法 updateBackMatCommonOutApplyCheckBatch\"}";
		}	
		
		return "{\"msg\":\"操作成功.\",\"state\":\"true\"}";
	}

	@Override
	public String updateMatCommonOutApplyCheckCloseInv(List<Map<String, Object>> entityList) throws DataAccessException {
		
		try {	
			//批量关闭材料
			matCommonOutApplyCheckMapper.updateMatCommonOutApplyCheckCloseInv(entityList);
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);

			throw new SysException("{\"error\":\"操作失败\"}");
			//return "{\"error\":\"操作失败 数据库异常 请联系管理员! 方法 updateBackMatCommonOutApplyCheckBatch\"}";
		}	
		
		return "{\"msg\":\"操作成功.\",\"state\":\"true\"}";
	}
	
	/**
	 * @Description 
	 * 添加科室申请<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addOrUpdate(Map<String,Object> entityMap)throws DataAccessException{
		
		//获取对象科室申请
		MatApplyMain MatApplyMain = queryByCode(entityMap);

		if (MatApplyMain != null) {

			int state = matCommonOutApplyCheckMapper.update(entityMap);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		
		try {
			
			int state = matCommonOutApplyCheckMapper.add(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addOrUpdateMatCommonOutApplyCheck\"}";

		}
		
	}
	/**
	 * @Description 
	 * 查询结果集科室申请<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String query(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		sysPage = (SysPage) entityMap.get("sysPage");
		if (sysPage.getTotal()==-1){
			List<?> list = matCommonOutApplyCheckMapper.query(entityMap);
			
			return ChdJson.toJson(list);
		}else{
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			List<?> list = matCommonOutApplyCheckMapper.query(entityMap, rowBounds);
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
		}
	}
	
	/**
	 * @Description 
	 * 查询结果集科室申请<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String queryDetailN(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		sysPage = (SysPage) entityMap.get("sysPage");
		if (sysPage.getTotal()==-1){
			List<?> list = matCommonOutApplyCheckMapper.queryDetailN(entityMap);
			
			return ChdJson.toJson(list);
		}else{
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			List<?> list = matCommonOutApplyCheckMapper.queryDetailN(entityMap, rowBounds);
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
		}
	}
	
	
	/**
	 * @Description 
	 * 获取对象科室申请<BR> 
	 * @param  entityMap<BR>
	 *  参数为主键
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public <T> T queryByCode(Map<String,Object> entityMap)throws DataAccessException{
		return matCommonOutApplyCheckMapper.queryMatApplyMainByCode(entityMap);
	}
	
	/**
	 * @Description 
	 * 获取对象科室申请<BR> 
	 * @param  entityMap<BR>
	 *  参数为主键
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String queryDetailByCode(Map<String,Object> entityMap)throws DataAccessException{

		List<?> list = matCommonOutApplyCheckMapper.queryMatApplyDetailByCode(entityMap);
		
		return ChdJson.toJson(list);
	}
	
	/**
	 * @Description 
	 * 获取科室申请<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return MatCommonOutApply
	 * @throws DataAccessException
	*/
	@Override
	public  <T>T queryByUniqueness(Map<String,Object> entityMap)throws DataAccessException{
		return matCommonOutApplyCheckMapper.queryByUniqueness(entityMap);
	}
	
	@Override
	public List<?> queryExists(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}
	
	/**
	 * 组装生成出库单主表数据
	 */
	@Override
	public Map<String, Object> queryOutMain(Map<String, Object> entityMap) throws DataAccessException {
		
		return toMapLower(matCommonOutApplyCheckMapper.queryOutMain(entityMap));
	}
	/**
	 * 组装生成出库单明细表数据
	 */
	@Override
	public String queryOutDetail(Map<String, Object> entityMap) throws DataAccessException {
		try{
			//明细中材料信息
			List<Map<String, Object>> invList = toListMapLower(matCommonOutApplyCheckMapper.querySelectDetailForOut(entityMap));
			return matCommonService.getInvJsonByInvList(invList);
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);

			return "{\"error\":\"生成失败 数据库异常 请联系管理员! 方法 MatCommonOutApply--queryOutDetail\"}";
		}
	}
	
	/**
	 * @Description 出库单保存<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String addOut(Map<String, Object> entityMap) throws DataAccessException {
		try {
			//金额位数
			int money_para = Integer.valueOf(MyConfig.getSysPara("04005"));
			//金额存数的时候默认不设置小数点几位
			//int money_para = 6;
			
			int flag = 1;
			// (1)、判断当前编制日期所在期间是否存在，若不存在提示：请配置期间。
			flag = matCommonMapper.existsAccYearMonthCheck(entityMap);
			if(flag == 0){
				return "{\"error\":\"添加失败，所选期间不存在请配置！\",\"state\":\"false\"}";
			}
			
			//判断库房是否已启用
			flag = matCommonMapper.existsMatStoreIsStart(entityMap);
			if(flag == 0){
				return "{\"error\":\"添加失败，库房本期间未启用请配置！\",\"state\":\"false\"}";
			}
			
			entityMap.put("out_id", matCommonOutApplyCheckMapper.queryMatOutMainSeqNext().toString());
			// 获取出库单号
			entityMap.put("table_code", "MAT_OUT_MAIN");
			String out_no = matCommonService.getMatNextNo(entityMap);
			if(out_no.indexOf("error") > 0){
				return out_no;
			}
			entityMap.put("out_no", out_no);
			 
			double money_sum = 0;//记录明细总金额

			//存放申领单与出库单对应关系
			List<Map<String, Object>> appOutRelaList = new ArrayList<Map<String, Object>>();// 存放明细
			//存放出库明细数据
			List<Map<String, Object>> out_detail_batch = new ArrayList<Map<String, Object>>();// 存放明细
			// 解析明细数据
			JSONArray out_detail_json = JSONArray.parseArray((String) entityMap.get("detailData"));
			StringBuffer invEnoughMsg = new StringBuffer(); 
			Iterator out_detail_it = out_detail_json.iterator();
			while (out_detail_it.hasNext()) {
				Map<String, Object> mapDetailVo = new HashMap<String, Object>();
				Map<String, Object> appOutRelaMap = new HashMap<String, Object>();

				mapDetailVo.put("group_id", entityMap.get("group_id").toString());
				mapDetailVo.put("hos_id", entityMap.get("hos_id").toString());
				mapDetailVo.put("copy_code", entityMap.get("copy_code").toString());
				mapDetailVo.put("out_id", entityMap.get("out_id").toString());
				mapDetailVo.put("out_no", entityMap.get("out_no").toString());
				//用于批次查询
				mapDetailVo.put("store_id", entityMap.get("store_id").toString());
				
				//处理对应关系
				appOutRelaMap.put("group_id", entityMap.get("group_id").toString());
				appOutRelaMap.put("hos_id", entityMap.get("hos_id").toString());
				appOutRelaMap.put("copy_code", entityMap.get("copy_code").toString());
				appOutRelaMap.put("rela_type", 1);
				appOutRelaMap.put("rela_id", entityMap.get("out_id").toString());
				
				JSONObject jsonObj = JSONObject.parseObject(out_detail_it.next().toString());
				//不存在材料ID视为空行
				if (ChdJson.validateJSON(String.valueOf(jsonObj.get("inv_id")))) {
					mapDetailVo.put("inv_id", jsonObj.get("inv_id").toString());
					mapDetailVo.put("inv_no", jsonObj.get("inv_no").toString());
					/**材料主信息-----------------begin---------------------*/
					if (ChdJson.validateJSON(String.valueOf(jsonObj.get("batch_no")))) {
						mapDetailVo.put("batch_no", jsonObj.get("batch_no").toString());
					}else{
						mapDetailVo.put("batch_no", null);
					}
					if (ChdJson.validateJSON(String.valueOf(jsonObj.get("bar_code")))) {
						mapDetailVo.put("bar_code", jsonObj.get("bar_code").toString());
					}else{
						mapDetailVo.put("bar_code", null);
					}
					if (ChdJson.validateJSON(String.valueOf(jsonObj.get("price")))) {
						mapDetailVo.put("price", jsonObj.get("price").toString());
					}else{
						mapDetailVo.put("price", "0");
					}
					if (ChdJson.validateJSON(String.valueOf(jsonObj.get("sale_price")))) {
						mapDetailVo.put("sale_price", jsonObj.get("sale_price").toString());
					}else{
						mapDetailVo.put("sale_price", "0");
					}
					if (ChdJson.validateJSON(String.valueOf(jsonObj.get("sell_price")))) {
						mapDetailVo.put("sell_price", jsonObj.get("sell_price").toString());
					}else{
						mapDetailVo.put("sell_price", "0");
					}
					if (ChdJson.validateJSON(String.valueOf(jsonObj.get("inva_date")))) {
						mapDetailVo.put("inva_date", DateUtil.stringToDate(jsonObj.get("inva_date").toString(), "yyyy-MM-dd"));
					}else{
						mapDetailVo.put("inva_date", null);
					}
					if (ChdJson.validateJSON(String.valueOf(jsonObj.get("disinfect_date")))) {
						mapDetailVo.put("disinfect_date", DateUtil.stringToDate(jsonObj.get("disinfect_date").toString(), "yyyy-MM-dd"));
					}else{
						mapDetailVo.put("disinfect_date", null);
					}
					if (ChdJson.validateJSON(String.valueOf(jsonObj.get("location_id")))) {
						mapDetailVo.put("location_id", jsonObj.get("location_id").toString());
					}else{
						mapDetailVo.put("location_id", "0");
					}
					if (ChdJson.validateJSON(String.valueOf(jsonObj.get("note")))) {
						mapDetailVo.put("note", jsonObj.get("note").toString());
					}else{
						mapDetailVo.put("note", null);
					}
					/**材料主信息-----------------end-----------------------*/
					/**申请单信息-----------------begin---------------------*/
					String[] otherIds = null;
					int otherIndex = 0;  //数组下标
					int otherLen = 0;  //数组下标最大值
					if (ChdJson.validateJSON(String.valueOf(jsonObj.get("other_ids")))) {
						otherIds = jsonObj.get("other_ids").toString().split(",");
						otherLen = otherIds.length - 1;  
					}
					/**申请单信息-----------------end-----------------------*/
					/**材料批次信息---------------begin---------------------*/
					//如果sum_amount存在值并且等于amount则取inv_detail_data中的批次信息
					if(ChdJson.validateJSON(String.valueOf(jsonObj.get("sum_amount"))) && ChdJson.validateJSON(String.valueOf(jsonObj.get("amount"))) && ChdJson.validateJSON(String.valueOf(jsonObj.get("inv_detail_data"))) && String.valueOf(jsonObj.get("sum_amount")).equals(String.valueOf(jsonObj.get("amount")))){
						
						for(Map<String, Object> m : JsonListMapUtil.getListMap(jsonObj.get("inv_detail_data").toString())){
							//没材料ID视为空行
							if(m.get("inv_id") != null && !"".equals(String.valueOf(m.get("inv_id")))){
								Map<String, Object> detailMap = new HashMap<String, Object>();
								detailMap.putAll(mapDetailVo);
								detailMap.put("out_detail_id", String.valueOf(matCommonOutApplyCheckMapper.queryMatOutDetailSeqNext()));
								if (ChdJson.validateJSON(String.valueOf(m.get("batch_sn")))) {
									detailMap.put("batch_sn", m.get("batch_sn").toString());
								}else{
									detailMap.put("batch_sn", null);
								}
								if (ChdJson.validateJSON(String.valueOf(m.get("amount")))) {
									detailMap.put("amount", m.get("amount").toString());
								}else{
									detailMap.put("amount", null);
								}
								//金额根据系统参数重新对数量和单价的乘积进行四舍五入
								detailMap.put("amount_money", String.valueOf(NumberUtil.numberToRound(Double.valueOf(detailMap.get("amount").toString()) * Double.valueOf(String.valueOf((detailMap.get("price") == null ? 0 : detailMap.get("price")))), money_para)));
								//money_sum = money_sum + Float.parseFloat(detailMap.get("amount_money").toString());//记录总金额
								money_sum = NumberUtil.add(Double.valueOf(money_sum), Double.valueOf(detailMap.get("amount_money").toString()));//记录总金额
								/*if (ChdJson.validateJSON(String.valueOf(m.get("amount_money")))) {
									detailMap.put("amount_money", m.get("amount_money").toString());
									money_sum = money_sum + Float.parseFloat(detailMap.get("amount_money").toString());//记录总金额
								}else{
									detailMap.put("amount_money", "0");
								}*/
								detailMap.put("sale_money", String.valueOf(NumberUtil.numberToRound(Double.valueOf(detailMap.get("amount").toString()) * Double.valueOf(String.valueOf((detailMap.get("sale_price") == null ? 0 : detailMap.get("sale_price")))), money_para)));
								/*if (ChdJson.validateJSON(String.valueOf(m.get("sale_money")))) {
									detailMap.put("sale_money", m.get("sale_money").toString());
								}else{
									detailMap.put("sale_money", "0");
								}*/
								detailMap.put("sell_money", String.valueOf(NumberUtil.numberToRound(Double.valueOf(detailMap.get("amount").toString()) * Double.valueOf(String.valueOf((detailMap.get("sell_price") == null ? 0 : detailMap.get("sell_price")))), money_para)));
								/*if (ChdJson.validateJSON(String.valueOf(m.get("sell_money")))) {
									detailMap.put("sell_money", m.get("sell_money").toString());
								}else{
									detailMap.put("sell_money", "0");
								}*/
								out_detail_batch.add(detailMap);
			
								/**-----------------处理对应关系---------begin---------*/
								if(otherIds != null && otherIndex <= otherLen){
									
									boolean is_addRela = false;
									//获取出库数量
									double do_amount = Double.valueOf(detailMap.get("amount") == null ? "0" : detailMap.get("amount").toString());
									double rela_amount = 0;
									String appStr = "";
									Map<String, Object> relaMap = new HashMap<String, Object>();
									relaMap.putAll(appOutRelaMap);
									//循环减对应关系数量
									while(do_amount > 0 && otherIndex <= otherLen){
										String[] rela = otherIds[otherIndex].split("@");
										double app_amount = Double.valueOf(rela[2]);
										//如果一样证明来自不同的申请单需添加新的对应关系
										if(!"".equals(appStr) && !appStr.equals(rela[0] + rela[1])){
											appOutRelaList.add(relaMap);
											relaMap = new HashMap<String, Object>();
											relaMap.putAll(appOutRelaMap);
											is_addRela = false;
										}
										if(!is_addRela){
											relaMap.put("apply_id", rela[0]);
											relaMap.put("app_detail_id", rela[1]);
											relaMap.put("rela_detail_id", detailMap.get("out_detail_id").toString());
										}
										rela_amount = relaMap.get("rela_amount") == null ? 0 : Double.valueOf(relaMap.get("rela_amount").toString());
										if(do_amount >= app_amount){
											relaMap.put("rela_amount", rela_amount + app_amount);
											do_amount = NumberUtil.sub(do_amount, app_amount);
											otherIndex ++;
										}else{
											relaMap.put("rela_amount", rela_amount + do_amount);
											otherIds[otherIndex] = rela[0] + "@" + rela[1] + "@" + (NumberUtil.sub(app_amount, do_amount)); 
											do_amount = 0;
										}
										is_addRela = true;
										appStr = rela[0] + rela[1];
									}
									appOutRelaList.add(relaMap);
									
								}
								/**-----------------处理对应关系--------end------------*/
							}
						}
					}else{
						//按先进先出匹配批次信息
						List<Map<String, Object>> fifoList = toListMapLower((List<Map<String, Object>>) matCommonMapper.queryMatStockInvDetailList(mapDetailVo));
						Double amount = Double.valueOf(String.valueOf((jsonObj.get("amount") == null ? 0 : jsonObj.get("amount"))));
						Double imme_amount = null;
						//按先进先出生成出库单
						for(Map<String, Object> map : fifoList){
							Map<String, Object> detailMap = new HashMap<String, Object>();
							detailMap.putAll(mapDetailVo);
							detailMap.put("out_detail_id", String.valueOf(matCommonOutApplyCheckMapper.queryMatOutDetailSeqNext()));
							detailMap.put("batch_sn", map.get("batch_sn").toString());
							//当前批次即时库存
							imme_amount = Double.valueOf(String.valueOf((map.get("imme_amount") == null ? 0 : map.get("imme_amount"))));
							//判断当前批号批次是否充足
							if(amount <= imme_amount){
								detailMap.put("amount", amount.toString());
								//计算金额
								detailMap.put("amount_money", String.valueOf(NumberUtil.numberToRound(amount * Double.valueOf(String.valueOf((map.get("price") == null ? 0 : map.get("price")))), money_para)));
								detailMap.put("sale_money", String.valueOf(NumberUtil.numberToRound(amount * Double.valueOf(String.valueOf((map.get("sale_price") == null ? 0 : map.get("sale_price")))), money_para)));
								detailMap.put("sell_money", String.valueOf(NumberUtil.numberToRound(amount * Double.valueOf(String.valueOf((map.get("sell_price") == null ? 0 : map.get("sell_price")))), money_para)));
								detailMap.put("allot_money", String.valueOf(NumberUtil.numberToRound(amount * Double.valueOf(String.valueOf((map.get("allot_price") == null ? 0 : map.get("allot_price")))), money_para)));
								//money_sum = money_sum + Float.parseFloat(detailMap.get("amount_money").toString());//记录总金额
								money_sum = NumberUtil.add(Double.valueOf(money_sum), Double.valueOf(detailMap.get("amount_money").toString()));//记录总金额
								out_detail_batch.add(detailMap);
			
								/**-----------------处理对应关系---------begin---------*/
								if(otherIds != null && otherIndex <= otherLen){
									
									boolean is_addRela = false;
									//获取出库数量
									double do_amount = Double.valueOf(detailMap.get("amount") == null ? "0" : detailMap.get("amount").toString());
									double rela_amount = 0;
									String appStr = "";
									Map<String, Object> relaMap = new HashMap<String, Object>();
									relaMap.putAll(appOutRelaMap);
									//循环减对应关系数量
									while(do_amount > 0 && otherIndex <= otherLen){
										String[] rela = otherIds[otherIndex].split("@");
										double app_amount = Double.valueOf(rela[2]);
										//如果一样证明来自不同的申请单需添加新的对应关系
										if(!"".equals(appStr) && !appStr.equals(rela[0] + rela[1])){
											appOutRelaList.add(relaMap);
											relaMap = new HashMap<String, Object>();
											relaMap.putAll(appOutRelaMap);
											is_addRela = false;
										}
										if(!is_addRela){
											relaMap.put("apply_id", rela[0]);
											relaMap.put("app_detail_id", rela[1]);
											relaMap.put("rela_detail_id", detailMap.get("out_detail_id").toString());
										}
										rela_amount = relaMap.get("rela_amount") == null ? 0 : Double.valueOf(relaMap.get("rela_amount").toString());
										if(do_amount >= app_amount){
											relaMap.put("rela_amount", rela_amount + app_amount);
											do_amount = NumberUtil.sub(do_amount, app_amount);
											otherIndex ++;
										}else{
											relaMap.put("rela_amount", rela_amount + do_amount);
											otherIds[otherIndex] = rela[0] + "@" + rela[1] + "@" + (NumberUtil.sub(app_amount, do_amount)); 
											do_amount = 0;
										}
										is_addRela = true;
										appStr = rela[0] + rela[1];
									}
									appOutRelaList.add(relaMap);
									
								}
								/**-----------------处理对应关系--------end------------*/
								amount = 0.0;
								break;
							}else{
								//取当前批号批次数量并且申请单数量响应减少
								detailMap.put("amount", imme_amount.toString());		
								//计算金额
								detailMap.put("amount_money", String.valueOf(NumberUtil.numberToRound(imme_amount * Double.valueOf(String.valueOf((map.get("price") == null ? 0 : map.get("price")))), money_para)));
								detailMap.put("sale_money", String.valueOf(NumberUtil.numberToRound(imme_amount * Double.valueOf(String.valueOf((map.get("sale_price") == null ? 0 : map.get("sale_price")))), money_para)));
								detailMap.put("sell_money", String.valueOf(NumberUtil.numberToRound(imme_amount * Double.valueOf(String.valueOf((map.get("sell_price") == null ? 0 : map.get("sell_price")))), money_para)));
								detailMap.put("allot_money", String.valueOf(NumberUtil.numberToRound(imme_amount * Double.valueOf(String.valueOf((map.get("allot_price") == null ? 0 : map.get("allot_price")))), money_para)));
								//money_sum = money_sum + Float.parseFloat(detailMap.get("amount_money").toString());//记录总金额
								money_sum = NumberUtil.add(Double.valueOf(money_sum), Double.valueOf(detailMap.get("amount_money").toString()));//记录总金额
								out_detail_batch.add(detailMap);		
			
								/**-----------------处理对应关系---------begin---------*/
								if(otherIds != null && otherIndex <= otherLen){
									
									boolean is_addRela = false;
									//获取出库数量
									double do_amount = Double.valueOf(detailMap.get("amount") == null ? "0" : detailMap.get("amount").toString());
									double rela_amount = 0;
									String appStr = "";
									Map<String, Object> relaMap = new HashMap<String, Object>();
									relaMap.putAll(appOutRelaMap);
									//循环减对应关系数量
									while(do_amount > 0 && otherIndex <= otherLen){
										String[] rela = otherIds[otherIndex].split("@");
										double app_amount = Double.valueOf(rela[2]);
										//如果一样证明来自不同的申请单需添加新的对应关系
										if(!"".equals(appStr) && !appStr.equals(rela[0] + rela[1])){
											appOutRelaList.add(relaMap);
											relaMap = new HashMap<String, Object>();
											relaMap.putAll(appOutRelaMap);
											is_addRela = false;
										}
										if(!is_addRela){
											relaMap.put("apply_id", rela[0]);
											relaMap.put("app_detail_id", rela[1]);
											relaMap.put("rela_detail_id", detailMap.get("out_detail_id").toString());
										}
										rela_amount = relaMap.get("rela_amount") == null ? 0 : Double.valueOf(relaMap.get("rela_amount").toString());
										if(do_amount >= app_amount){
											relaMap.put("rela_amount", rela_amount + app_amount);
											do_amount = NumberUtil.sub(do_amount, app_amount);
											otherIndex ++;
										}else{
											relaMap.put("rela_amount", rela_amount + do_amount);
											otherIds[otherIndex] = rela[0] + "@" + rela[1] + "@" + (NumberUtil.sub(app_amount, do_amount)); 
											do_amount = 0;
										}
										is_addRela = true;
										appStr = rela[0] + rela[1];
									}
									appOutRelaList.add(relaMap);
									
								}
								/**-----------------处理对应关系--------end------------*/
								amount = NumberUtil.sub(amount, imme_amount);
							}
							//当数量为0，证明已经完成先进先出操作
							if(amount == 0){
								break;
							}
						}
						if(amount > 0){
							invEnoughMsg.append(String.valueOf(jsonObj.get("inv_code"))).append(" ").append(String.valueOf(jsonObj.get("inv_name"))).append("<br>");
						}
					}
					/**材料批次信息---------------end-----------------------*/
				}
			}
			if(out_detail_batch.size() <= 0){
				return "{\"error\":\"出库单明细数据为空!\"}";
			}
			if(invEnoughMsg != null && !"".equals(invEnoughMsg.toString())){
				return "{\"error\":\"以下材料库存物资不足："+invEnoughMsg.toString()+"\"}";
			}
			//判断即时库存是否充足(防止多人同时操作同一材料，额外加的判断)
			String invEnough = matCommonMapper.existsMatStockInvIsEnough(out_detail_batch);
			if(invEnough != null && !"".equals(invEnough)){
				return "{\"error\":\"以下材料库存物资不足!"+invEnough+"\"}";
			} 
			if(entityMap.get("app_date") != null && !"".equals(entityMap.get("app_date"))){
				entityMap.put("app_date", DateUtil.stringToDate(entityMap.get("app_date").toString(), "yyyy-MM-dd HH:mm:ss"));
			}else{
				entityMap.put("app_date", null);
			}

			entityMap.put("come_from", 2);
			entityMap.put("amount_money", money_sum);
			matCommonOutApplyCheckMapper.addMatOutMainByApp(entityMap);// 保存主表

			matCommonOutApplyCheckMapper.addMatOutDetailByApp(out_detail_batch);// 保存明细表
			
			entityMap.put("source_money", money_sum);
			
			matOutResourceMapper.add(entityMap);// 保存资金来源
			
			matApplyOutRelaMapper.addMatApplyOutRelaBatch(appOutRelaList);//保存对应关系 
			
		} catch (Exception e) { 
			logger.error(e.getMessage(), e); 

			throw new SysException("{\"error\":\"添加失败\"}");
			//return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 add\"}";
		}
		
		return "{\"msg1\":\"添加成功.\",\"state\":\"true\",\"update_para\":\""+
			entityMap.get("group_id").toString()+","+
			entityMap.get("hos_id").toString()+","+
			entityMap.get("copy_code").toString()+","+
			entityMap.get("out_id").toString()+","+
			entityMap.get("store_id").toString()+","
			+"\"}";
	}

	/**
	 * 组装生成代销出库单明细表数据
	 */
	@Override
	public String queryAffiOutDetail(Map<String, Object> entityMap) throws DataAccessException {
		try{
			//明细材料信息
			List<Map<String, Object>> invList = toListMapLower(matCommonOutApplyCheckMapper.querySelectDetailForOut(entityMap));
			
			return matCommonService.getAffiInvJsonByAffiInvList(invList);
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);

			return "{\"error\":\"生成失败 数据库异常 请联系管理员! 方法 MatCommonOutApply--queryOutDetail\"}";
		}
	}
	
	/**
	 * @Description 代销出库单保存<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String addAffiOut(Map<String, Object> entityMap) throws DataAccessException {
		try {
			//金额位数
			int money_para = Integer.valueOf(MyConfig.getSysPara("04005"));
			//金额存数的时候默认不设置小数点几位
			//int money_para = 6;
			
			int flag = 1;
			// (1)、判断当前编制日期所在期间是否存在，若不存在提示：请配置期间。
			flag = matCommonMapper.existsAccYearMonthCheck(entityMap);
			if(flag == 0){
				return "{\"error\":\"添加失败，所选期间不存在请配置！\",\"state\":\"false\"}";
			}
			
			// (2)、判断库房是否已启用
			flag = matCommonMapper.existsMatStoreIsStart(entityMap);
			if(flag == 0){
				return "{\"error\":\"添加失败，库房本期间未启用请配置！\",\"state\":\"false\"}";
			}
			
			// 查询序列
			entityMap.put("out_id", matCommonOutApplyCheckMapper.queryMatAffiOutSeqNext());
			// 获取出库单号
			entityMap.put("table_code", "MAT_AFFI_OUT");
			String out_no = matCommonService.getMatNextNo(entityMap);
			if(out_no.indexOf("error") > 0){
				return out_no;
			}
			entityMap.put("out_no", out_no);
			
			//存放申领单与出库单对应关系
			List<Map<String, Object>> appOutRelaList = new ArrayList<Map<String, Object>>();// 存放明细
			//存放出库明细数据
			List<Map<String, Object>> out_detail_batch = new ArrayList<Map<String, Object>>();// 存放明细
			// 解析明细数据
			JSONArray out_detail_json = JSONArray.parseArray((String) entityMap.get("detailData"));
			StringBuffer invEnoughMsg = new StringBuffer(); 
			Iterator out_detail_it = out_detail_json.iterator();
			while (out_detail_it.hasNext()) {
				Map<String, Object> mapDetailVo = new HashMap<String, Object>();
				Map<String, Object> appOutRelaMap = new HashMap<String, Object>();

				mapDetailVo.put("group_id", entityMap.get("group_id").toString());
				mapDetailVo.put("hos_id", entityMap.get("hos_id").toString());
				mapDetailVo.put("copy_code", entityMap.get("copy_code").toString());
				mapDetailVo.put("out_id", entityMap.get("out_id").toString());
				mapDetailVo.put("out_no", entityMap.get("out_no").toString());
				//用于批次查询
				mapDetailVo.put("store_id", entityMap.get("store_id").toString());
				
				//处理对应关系
				appOutRelaMap.put("group_id", entityMap.get("group_id").toString());
				appOutRelaMap.put("hos_id", entityMap.get("hos_id").toString());
				appOutRelaMap.put("copy_code", entityMap.get("copy_code").toString());
				appOutRelaMap.put("rela_type", 3);
				appOutRelaMap.put("rela_id", entityMap.get("out_id").toString());
				
				JSONObject jsonObj = JSONObject.parseObject(out_detail_it.next().toString());
				//不存在材料ID视为空行
				if (ChdJson.validateJSON(String.valueOf(jsonObj.get("inv_id")))) {
					mapDetailVo.put("inv_id", jsonObj.get("inv_id").toString());
					mapDetailVo.put("inv_no", jsonObj.get("inv_no").toString());
					/**材料主信息-----------------begin---------------------*/
					if (ChdJson.validateJSON(String.valueOf(jsonObj.get("batch_no")))) {
						mapDetailVo.put("batch_no", jsonObj.get("batch_no").toString());
					}else{
						mapDetailVo.put("batch_no", null);
					}
					if (ChdJson.validateJSON(String.valueOf(jsonObj.get("bar_code")))) {
						mapDetailVo.put("bar_code", jsonObj.get("bar_code").toString());
					}else{
						mapDetailVo.put("bar_code", null);
					}
					if (ChdJson.validateJSON(String.valueOf(jsonObj.get("price")))) {
						mapDetailVo.put("price", jsonObj.get("price").toString());
					}else{
						mapDetailVo.put("price", "0");
					}
					if (ChdJson.validateJSON(String.valueOf(jsonObj.get("sale_price")))) {
						mapDetailVo.put("sale_price", jsonObj.get("sale_price").toString());
					}else{
						mapDetailVo.put("sale_price", "0");
					}
					if (ChdJson.validateJSON(String.valueOf(jsonObj.get("sell_price")))) {
						mapDetailVo.put("sell_price", jsonObj.get("sell_price").toString());
					}else{
						mapDetailVo.put("sell_price", "0");
					}
					if (ChdJson.validateJSON(String.valueOf(jsonObj.get("inva_date")))) {
						mapDetailVo.put("inva_date", DateUtil.stringToDate(jsonObj.get("inva_date").toString(), "yyyy-MM-dd"));
					}else{
						mapDetailVo.put("inva_date", null);
					}
					if (ChdJson.validateJSON(String.valueOf(jsonObj.get("disinfect_date")))) {
						mapDetailVo.put("disinfect_date", DateUtil.stringToDate(jsonObj.get("disinfect_date").toString(), "yyyy-MM-dd"));
					}else{
						mapDetailVo.put("disinfect_date", null);
					}
					if (ChdJson.validateJSON(String.valueOf(jsonObj.get("location_id")))) {
						mapDetailVo.put("location_id", jsonObj.get("location_id").toString());
					}else{
						mapDetailVo.put("location_id", "0");
					}
					if (ChdJson.validateJSON(String.valueOf(jsonObj.get("note")))) {
						mapDetailVo.put("note", jsonObj.get("note").toString());
					}else{
						mapDetailVo.put("note", null);
					}
					if (ChdJson.validateJSON(String.valueOf(jsonObj.get("sup_id")))) {
						mapDetailVo.put("sup_id", jsonObj.get("sup_id").toString());
					}else{
						mapDetailVo.put("sup_id", null);
					}
					if (ChdJson.validateJSON(String.valueOf(jsonObj.get("sup_no")))) {
						mapDetailVo.put("sup_no", jsonObj.get("sup_no").toString());
					}else{
						mapDetailVo.put("sup_no", null);
					}
					/**材料主信息-----------------end-----------------------*/
					/**申请单信息-----------------begin---------------------*/
					String[] otherIds = null;
					int otherIndex = 0;  //数组下标
					int otherLen = 0;  //数组下标最大值
					if (ChdJson.validateJSON(String.valueOf(jsonObj.get("other_ids")))) {
						otherIds = jsonObj.get("other_ids").toString().split(",");
						otherLen = otherIds.length - 1;  
					}
					/**申请单信息-----------------end-----------------------*/
					/**材料批次信息---------------begin---------------------*/
					//如果sum_amount存在值并且等于amount则取inv_detail_data中的批次信息
					if(ChdJson.validateJSON(String.valueOf(jsonObj.get("sum_amount"))) && ChdJson.validateJSON(String.valueOf(jsonObj.get("amount"))) && ChdJson.validateJSON(String.valueOf(jsonObj.get("inv_detail_data"))) && String.valueOf(jsonObj.get("sum_amount")).equals(String.valueOf(jsonObj.get("amount")))){
						
						for(Map<String, Object> m : JsonListMapUtil.getListMap(jsonObj.get("inv_detail_data").toString())){
							//没材料ID视为空行
							if(m.get("inv_id") != null && !"".equals(String.valueOf(m.get("inv_id")))){
								Map<String, Object> detailMap = new HashMap<String, Object>();
								detailMap.putAll(mapDetailVo);
								detailMap.put("detail_id", String.valueOf(matCommonOutApplyCheckMapper.queryMatAffiOutDetailSeqNext()));
								if (ChdJson.validateJSON(String.valueOf(m.get("batch_sn")))) {
									detailMap.put("batch_sn", m.get("batch_sn").toString());
								}else{
									detailMap.put("batch_sn", null);
								}
								if (ChdJson.validateJSON(String.valueOf(m.get("amount")))) {
									detailMap.put("amount", m.get("amount").toString());
								}else{
									detailMap.put("amount", null);
								}
								//金额根据系统参数重新对数量和单价的乘积进行四舍五入
								if (ChdJson.validateJSON(String.valueOf(m.get("amount_money")))) {
									detailMap.put("amount_money", m.get("amount_money").toString());
									detailMap.put("amount_money", String.valueOf(NumberUtil.numberToRound(Double.valueOf(detailMap.get("amount").toString()) * Double.valueOf(String.valueOf((detailMap.get("price") == null ? 0 : detailMap.get("price")))), money_para)));
								}else{
									detailMap.put("amount_money", "0");
								}
								if (ChdJson.validateJSON(String.valueOf(m.get("sale_money")))) {
									detailMap.put("sale_money", m.get("sale_money").toString());
									detailMap.put("sale_money", String.valueOf(NumberUtil.numberToRound(Double.valueOf(detailMap.get("amount").toString()) * Double.valueOf(String.valueOf((detailMap.get("sale_price") == null ? 0 : detailMap.get("sale_price")))), money_para)));
								}else{
									detailMap.put("sale_money", "0");
								}
								if (ChdJson.validateJSON(String.valueOf(m.get("sell_money")))) {
									detailMap.put("sell_money", m.get("sell_money").toString());
									detailMap.put("sell_money", String.valueOf(NumberUtil.numberToRound(Double.valueOf(detailMap.get("amount").toString()) * Double.valueOf(String.valueOf((detailMap.get("sell_price") == null ? 0 : detailMap.get("sell_price")))), money_para)));
								}else{
									detailMap.put("sell_money", "0");
								}
								out_detail_batch.add(detailMap);
			
								/**-----------------处理对应关系---------begin---------*/
								if(otherIds != null && otherIndex <= otherLen){
									
									boolean is_addRela = false;
									//获取出库数量
									double do_amount = Double.valueOf(detailMap.get("amount") == null ? "0" : detailMap.get("amount").toString());
									double rela_amount = 0;
									String appStr = "";
									Map<String, Object> relaMap = new HashMap<String, Object>();
									relaMap.putAll(appOutRelaMap);
									//循环减对应关系数量
									while(do_amount > 0 && otherIndex <= otherLen){
										String[] rela = otherIds[otherIndex].split("@");
										double app_amount = Double.valueOf(rela[2]);
										//如果一样证明来自不同的申请单需添加新的对应关系
										if(!"".equals(appStr) && !appStr.equals(rela[0] + rela[1])){
											appOutRelaList.add(relaMap);
											relaMap = new HashMap<String, Object>();
											relaMap.putAll(appOutRelaMap);
											is_addRela = false;
										}
										if(!is_addRela){
											relaMap.put("apply_id", rela[0]);
											relaMap.put("app_detail_id", rela[1]);
											relaMap.put("rela_detail_id", detailMap.get("detail_id").toString());
										}
										rela_amount = relaMap.get("rela_amount") == null ? 0 : Double.valueOf(relaMap.get("rela_amount").toString());
										if(do_amount >= app_amount){
											relaMap.put("rela_amount", rela_amount + app_amount);
											do_amount = NumberUtil.sub(do_amount, app_amount);
											otherIndex ++;
										}else{
											relaMap.put("rela_amount", rela_amount + do_amount);
											otherIds[otherIndex] = rela[0] + "@" + rela[1] + "@" + (NumberUtil.sub(app_amount, do_amount)); 
											do_amount = 0;
										}
										is_addRela = true;
										appStr = rela[0] + rela[1];
									}
									appOutRelaList.add(relaMap);
									
								}
								/**-----------------处理对应关系--------end------------*/
							}
						}
					}else{
						//按先进先出匹配批次信息
						List<Map<String, Object>> fifoList = toListMapLower((List<Map<String, Object>>) matCommonMapper.queryMatAffiOutDetailInvList(mapDetailVo));
						Double amount = Double.valueOf(String.valueOf((jsonObj.get("amount") == null ? 0 : jsonObj.get("amount"))));
						Double imme_amount = null;
						//按先进先出生成出库单
						for(Map<String, Object> map : fifoList){
							Map<String, Object> detailMap = new HashMap<String, Object>();
							detailMap.putAll(mapDetailVo);
							detailMap.put("detail_id", String.valueOf(matCommonOutApplyCheckMapper.queryMatAffiOutDetailSeqNext()));
							detailMap.put("batch_sn", map.get("batch_sn").toString());
							//当前批次即时库存
							imme_amount = Double.valueOf(String.valueOf((map.get("imme_amount") == null ? 0 : map.get("imme_amount"))));
							//判断当前批号批次是否充足
							if(amount <= imme_amount){
								detailMap.put("amount", amount.toString());
								//计算金额
								detailMap.put("amount_money", String.valueOf(NumberUtil.numberToRound(amount * Double.valueOf(String.valueOf((map.get("price") == null ? 0 : map.get("price")))), money_para)));
								detailMap.put("sale_money", String.valueOf(NumberUtil.numberToRound(amount * Double.valueOf(String.valueOf((map.get("sale_price") == null ? 0 : map.get("sale_price")))), money_para)));
								detailMap.put("sell_money", String.valueOf(NumberUtil.numberToRound(amount * Double.valueOf(String.valueOf((map.get("sell_price") == null ? 0 : map.get("sell_price")))), money_para)));
								detailMap.put("allot_money", String.valueOf(NumberUtil.numberToRound(amount * Double.valueOf(String.valueOf((map.get("allot_price") == null ? 0 : map.get("allot_price")))), money_para)));
								out_detail_batch.add(detailMap);
			
								/**-----------------处理对应关系---------begin---------*/
								if(otherIds != null && otherIndex <= otherLen){
									
									boolean is_addRela = false;
									//获取出库数量
									double do_amount = Double.valueOf(detailMap.get("amount") == null ? "0" : detailMap.get("amount").toString());
									double rela_amount = 0;
									String appStr = "";
									Map<String, Object> relaMap = new HashMap<String, Object>();
									relaMap.putAll(appOutRelaMap);
									//循环减对应关系数量
									while(do_amount > 0 && otherIndex <= otherLen){
										String[] rela = otherIds[otherIndex].split("@");
										double app_amount = Double.valueOf(rela[2]);
										//如果一样证明来自不同的申请单需添加新的对应关系
										if(!"".equals(appStr) && !appStr.equals(rela[0] + rela[1])){
											appOutRelaList.add(relaMap);
											relaMap = new HashMap<String, Object>();
											relaMap.putAll(appOutRelaMap);
											is_addRela = false;
										}
										if(!is_addRela){
											relaMap.put("apply_id", rela[0]);
											relaMap.put("app_detail_id", rela[1]);
											relaMap.put("rela_detail_id", detailMap.get("detail_id").toString());
										}
										rela_amount = relaMap.get("rela_amount") == null ? 0 : Double.valueOf(relaMap.get("rela_amount").toString());
										if(do_amount >= app_amount){
											relaMap.put("rela_amount", rela_amount + app_amount);
											do_amount = NumberUtil.sub(do_amount, app_amount);
											otherIndex ++;
										}else{
											relaMap.put("rela_amount", rela_amount + do_amount);
											otherIds[otherIndex] = rela[0] + "@" + rela[1] + "@" + (NumberUtil.sub(app_amount, do_amount)); 
											do_amount = 0;
										}
										is_addRela = true;
										appStr = rela[0] + rela[1];
									}
									appOutRelaList.add(relaMap);
									
								}
								/**-----------------处理对应关系--------end------------*/
								
								amount = 0.0;
								break;
							}else{
								//取当前批号批次数量并且申请单数量响应减少
								detailMap.put("amount", imme_amount.toString());		
								//计算金额
								detailMap.put("amount_money", String.valueOf(NumberUtil.numberToRound(imme_amount * Double.valueOf(String.valueOf((map.get("price") == null ? 0 : map.get("price")))), money_para)));
								detailMap.put("sale_money", String.valueOf(NumberUtil.numberToRound(imme_amount * Double.valueOf(String.valueOf((map.get("sale_price") == null ? 0 : map.get("sale_price")))), money_para)));
								detailMap.put("sell_money", String.valueOf(NumberUtil.numberToRound(imme_amount * Double.valueOf(String.valueOf((map.get("sell_price") == null ? 0 : map.get("sell_price")))), money_para)));
								detailMap.put("allot_money", String.valueOf(NumberUtil.numberToRound(imme_amount * Double.valueOf(String.valueOf((map.get("allot_price") == null ? 0 : map.get("allot_price")))), money_para)));
								out_detail_batch.add(detailMap);	
			
								/**-----------------处理对应关系---------begin---------*/
								if(otherIds != null && otherIndex <= otherLen){
									
									boolean is_addRela = false;
									//获取出库数量
									double do_amount = Double.valueOf(detailMap.get("amount") == null ? "0" : detailMap.get("amount").toString());
									double rela_amount = 0;
									String appStr = "";
									Map<String, Object> relaMap = new HashMap<String, Object>();
									relaMap.putAll(appOutRelaMap);
									//循环减对应关系数量
									while(do_amount > 0 && otherIndex <= otherLen){
										String[] rela = otherIds[otherIndex].split("@");
										double app_amount = Double.valueOf(rela[2]);
										//如果一样证明来自不同的申请单需添加新的对应关系
										if(!"".equals(appStr) && !appStr.equals(rela[0] + rela[1])){
											appOutRelaList.add(relaMap);
											relaMap = new HashMap<String, Object>();
											relaMap.putAll(appOutRelaMap);
											is_addRela = false;
										}
										if(!is_addRela){
											relaMap.put("apply_id", rela[0]);
											relaMap.put("app_detail_id", rela[1]);
											relaMap.put("rela_detail_id", detailMap.get("detail_id").toString());
										}
										rela_amount = relaMap.get("rela_amount") == null ? 0 : Double.valueOf(relaMap.get("rela_amount").toString());
										if(do_amount >= app_amount){
											relaMap.put("rela_amount", rela_amount + app_amount);
											do_amount = NumberUtil.sub(do_amount, app_amount);
											otherIndex ++;
										}else{
											relaMap.put("rela_amount", rela_amount + do_amount);
											otherIds[otherIndex] = rela[0] + "@" + rela[1] + "@" + (NumberUtil.sub(app_amount, do_amount)); 
											do_amount = 0;
										}
										is_addRela = true;
										appStr = rela[0] + rela[1];
									}
									appOutRelaList.add(relaMap);
									
								}
								/**-----------------处理对应关系--------end------------*/
								amount = NumberUtil.sub(amount, imme_amount);
							}
							//当数量为0，证明已经完成先进先出操作
							if(amount == 0){
								break;
							}
						}
						if(amount > 0){
							invEnoughMsg.append(String.valueOf(jsonObj.get("inv_code"))).append(" ").append(String.valueOf(jsonObj.get("inv_name"))).append("<br>");
						}
					}
					/**材料批次信息---------------end-----------------------*/
				}
			}
			if(out_detail_batch.size() <= 0){
				return "{\"error\":\"代销出库单明细数据为空!\"}";
			}
			if(invEnoughMsg != null && !"".equals(invEnoughMsg.toString())){
				return "{\"error\":\"以下材料库存物资不足："+invEnoughMsg.toString()+"\"}";
			}
			//判断即时库存是否充足(防止多人同时操作同一材料，额外加的判断)
			String invEnough = matCommonMapper.existsMatAffiInvIsEnough(out_detail_batch);
			if(invEnough != null && !"".equals(invEnough)){
				return "{\"error\":\"以下材料库存物资不足!"+invEnough+"\"}";
			}

			entityMap.put("come_from", 2);
			//entityMap.put("amount_money", money_sum);
			matCommonOutApplyCheckMapper.addMatAffiOutByApp(entityMap);// 保存主表

			matCommonOutApplyCheckMapper.addMatAffiOutDetailByApp(out_detail_batch);// 保存明细表
			
			matApplyOutRelaMapper.addMatApplyOutRelaBatch(appOutRelaList);//保存对应关系
			
		} catch (Exception e) {
			logger.error(e.getMessage(), e);

			throw new SysException("{\"error\":\"添加失败\"}");
			//return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 add\"}";
		}
		
		return "{\"msg1\":\"添加成功.\",\"state\":\"true\",\"update_para\":\""+
				entityMap.get("group_id").toString()+","+
				entityMap.get("hos_id").toString()+","+
				entityMap.get("copy_code").toString()+","+
				entityMap.get("out_id").toString()+","+
				entityMap.get("store_id").toString()+","
				+"\"}";
	}
	
	/**
	 * 查询管理部门对应的仓库信息<BR> 
	*/
	@Override
	public String queryStoreByDept(Map<String,Object> entityMap)throws DataAccessException{
		
		String store = matCommonOutApplyCheckMapper.queryStoreByDept(entityMap);
		if(store == null || "".equals(store)){
			
			return "{\"error\":\"该科室无对应的仓库请设置！\",\"state\":\"false\"}";
		}else{
			
			return "{\"state\":\"true\"}";
		}
	}
	
	/**
	 * 组装生成调拨单主表数据
	 */
	@Override
	public Map<String, Object> queryTranMain(Map<String, Object> entityMap) throws DataAccessException {
		
		return toMapLower(matCommonOutApplyCheckMapper.queryTranMain(entityMap));
	}
	/**
	 * 组装生成调拨单明细表数据
	 */
	@Override
	public String queryTranDetail(Map<String, Object> entityMap) throws DataAccessException {
		try{
			//材料基础信息
			List<Map<String, Object>> invList = toListMapLower(matCommonOutApplyCheckMapper.querySelectDetailForOut(entityMap));

			return matCommonService.getInvJsonByInvList(invList);
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);

			return "{\"error\":\"生成失败 数据库异常 请联系管理员! 方法 MatCommonOutApply--queryOutDetail\"}";
		}
	}

	/**
	 * @Description 保存调拨单
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addTran(Map<String,Object> entityMap)throws DataAccessException{
		
		//获取对象调拨方式TRAN_METHOD：1同价调拨  2 异价调拨 调拨类型：1 院内调拨  2 院外调拨
		try {

			//金额位数
			int money_para = Integer.valueOf(MyConfig.getSysPara("04005"));
			//金额存数的时候默认不设置小数点几位
			//int money_para = 6;
			
			int flag = 1;
			
			// (1)、判断当前编制日期所在期间是否存在，若不存在提示：请配置期间。
			flag = matCommonMapper.existsAccYearMonthCheck(entityMap);
			if(flag == 0){
				return "{\"error\":\"添加失败，所选期间不存在请配置！\",\"state\":\"false\"}";
			}
			
			//(2)、判断库房是否已启用
			flag = matCommonMapper.existsMatStoreIsStart(entityMap);
			if(flag == 0){
				return "{\"error\":\"添加失败，库房本期间未启用请配置！\",\"state\":\"false\"}";
			}

			entityMap.put("tran_id", matCommonOutApplyCheckMapper.queryMatTranMainSeqNext());
			
			/*---------------生成调拨单号--------------------begin---------------*/
			Map<String, Object> tranMap = new HashMap<String, Object>();
			tranMap.put("group_id", entityMap.get("group_id"));
			tranMap.put("hos_id", entityMap.get("hos_id"));
			tranMap.put("copy_code", entityMap.get("copy_code"));
			tranMap.put("store_id", entityMap.get("store_id"));
			tranMap.put("year", entityMap.get("year"));
			tranMap.put("month", entityMap.get("month"));
			tranMap.put("table_code", "MAT_TRAN_MAIN");
			tranMap.put("prefixe", "DB");
			tranMap.put("store_alias", "");
			tranMap.put("bus_type", "");

			// 判断是否存在该业务流水码
			flag = matNoManageMapper.queryIsExists(tranMap);
			String max_no = "";
			if (flag == 0) {// 如不存在则流水码为1，并插入流水码表中
				max_no = "1";
				tranMap.put("max_no", 1);
				matNoManageMapper.add(tranMap);
			} else {
				// 更新该业务流水码+1
				matNoManageMapper.updateMaxNo(tranMap);
				// 取出该业务更新后的流水码
				max_no = matNoManageMapper.queryMaxCode(tranMap);
			}
			// 补流水码前缀0
			for (int i = max_no.length(); i < 5; i++) {
				max_no = "0" + max_no;
			}
			// 组装流水码
			String tran_no = tranMap.get("prefixe") + "-" + tranMap.get("year").toString() + tranMap.get("month").toString() + max_no;
			entityMap.put("tran_no", tran_no);// 获取调拨单号
			/*---------------生成调拨单号--------------------begin---------------*/
			
			/*--------------需要同时生成出入库单--begin----------------------------*/
			//注：院内调拨需要同时生成出入库单；院外调拨只生成出库单，入库单在调入单位调入确认时生成。
			String brief = "由调拨单"+entityMap.get("tran_no")+"生成";
			String out_bus = "";
			String in_bus = "";
			boolean is_in = false;
			if("1".equals(String.valueOf(entityMap.get("tran_type")))){
				//院内调拨
				out_bus = "15";  //移出库
				in_bus = "14";  //移入库
				is_in = true;
			}else{
				//院外调拨
				if("1".equals(entityMap.get("bus_type").toString())){
					out_bus = "7";  //无偿调出
					in_bus = "6";  //无偿调入
				}else{
					out_bus = "5";  //有偿调出
					in_bus = "4";  //有偿调入
				}
			}
			//存放出库和调拨对应关系
			List<Map<String, Object>> appOutRelaList = new ArrayList<Map<String, Object>>();
			//存放出库主从表
			Map<String, Object> outMap = new HashMap<String, Object>();
			List<Map<String, Object>> outDetailList = new ArrayList<Map<String, Object>>();// 存放明细
			//存放入库主从表
			Map<String, Object> inMap = new HashMap<String, Object>();
			List<Map<String, Object>> inDetailList = new ArrayList<Map<String, Object>>();// 存放明细
			//生成出库单
			outMap.put("group_id", entityMap.get("group_id"));
			outMap.put("hos_id", entityMap.get("hos_id"));
			outMap.put("copy_code", entityMap.get("copy_code"));
			outMap.put("year", entityMap.get("year"));
			outMap.put("month", entityMap.get("month"));
			outMap.put("day", entityMap.get("day"));  //用于生成单号
			outMap.put("bus_type_code", out_bus);
			outMap.put("state", 1);
			outMap.put("brief", brief);
			//出库的主表用的to_date函数转换的，所以这里直接用String型
			outMap.put("maker", SessionManager.getUserId());
			outMap.put("out_date", entityMap.get("tran_date"));
			outMap.put("store_id", entityMap.get("out_store_id"));
			outMap.put("store_no", entityMap.get("out_store_no"));
			outMap.put("dept_id", null);
			outMap.put("dept_no", null);
			outMap.put("dept_emp", null);
			outMap.put("use_code", null);
			outMap.put("proj_id", null);
			outMap.put("table_code", "MAT_OUT_MAIN");
			outMap.put("prefixe", "");
			long out_id = matCommonOutApplyCheckMapper.queryMatOutMainSeqNext();
			outMap.put("out_id", out_id);
			String out_no = matCommonService.getMatNextNo(outMap);
			if(out_no.indexOf("error")!=-1){
				return out_no;
			}
			outMap.put("out_no", out_no);
			
			if(is_in){
				//生成入库单
				inMap.put("group_id", entityMap.get("group_id"));
				inMap.put("hos_id", entityMap.get("hos_id"));
				inMap.put("copy_code", entityMap.get("copy_code"));
				inMap.put("year", entityMap.get("year"));
				inMap.put("month", entityMap.get("month"));
				inMap.put("day", entityMap.get("day"));  //用于生成单号
				inMap.put("bus_type_code", in_bus);
				inMap.put("state", 1);
				inMap.put("brief", brief);
				inMap.put("maker", SessionManager.getUserId());
				inMap.put("in_date", entityMap.get("tran_date"));
				
				inMap.put("bus_type_code", 14);//移入库
				inMap.put("store_id", entityMap.get("in_store_id"));
				inMap.put("store_no", entityMap.get("in_store_no"));
				inMap.put("table_code", "MAT_IN_MAIN");
				inMap.put("prefixe", "");
				inMap.put("in_id", matInCommonMapper.queryMatInMainSeq());
				String in_no = matCommonService.getMatNextNo(inMap);
				if(in_no.indexOf("error")!=-1){
					return in_no;
				}
				inMap.put("in_no", in_no);
			}
			/*--------------需要同时生成出入库单--end------------------------------*/
			
			// 保存明细数据
			List<Map<String, Object>> tran_detail_batch = new ArrayList<Map<String, Object>>();// 存放明细
			// 解析明细数据
			JSONArray tran_detail_json = JSONArray.parseArray((String) entityMap.get("detailData"));
			
			double money_sum = 0;//记录明细总金额
			StringBuffer invEnoughMsg = new StringBuffer();//记录库存不足的材料
			Iterator tran_detail_it = tran_detail_json.iterator();

			while (tran_detail_it.hasNext()) {

				Map<String, Object> mapDetailVo = new HashMap<String, Object>();
				Map<String, Object> appOutRelaMap = new HashMap<String, Object>();
				
				/*材料主信息-----------------begin---------------------*/
				mapDetailVo.put("group_id", entityMap.get("group_id").toString());
				mapDetailVo.put("hos_id", entityMap.get("hos_id").toString());
				mapDetailVo.put("copy_code", entityMap.get("copy_code").toString());
				mapDetailVo.put("tran_id", entityMap.get("tran_id").toString());
				mapDetailVo.put("tran_no", entityMap.get("tran_no").toString());
				mapDetailVo.put("store_id", entityMap.get("out_store_id").toString());//用于查询材料信息
				
				//处理对应关系
				appOutRelaMap.put("group_id", entityMap.get("group_id").toString());
				appOutRelaMap.put("hos_id", entityMap.get("hos_id").toString());
				appOutRelaMap.put("copy_code", entityMap.get("copy_code").toString());
				appOutRelaMap.put("rela_type", 2);
				appOutRelaMap.put("rela_id", entityMap.get("tran_id").toString());

				JSONObject jsonObj = JSONObject.parseObject(tran_detail_it.next().toString());
				//不存在材料ID视为空行
				if (ChdJson.validateJSON(String.valueOf(jsonObj.get("inv_id")))) {
					mapDetailVo.put("inv_id", jsonObj.get("inv_id").toString());
					mapDetailVo.put("inv_no", jsonObj.get("inv_no").toString());
					if (ChdJson.validateJSON(String.valueOf(jsonObj.get("batch_no")))) {
						mapDetailVo.put("batch_no", jsonObj.get("batch_no").toString());
					}else{
						mapDetailVo.put("batch_no", null);
					}
					if (ChdJson.validateJSON(String.valueOf(jsonObj.get("amount")))) {
						mapDetailVo.put("amount", jsonObj.get("amount").toString());
					}else{
						mapDetailVo.put("amount", null);
					}
					if (ChdJson.validateJSON(String.valueOf(jsonObj.get("price")))) {
						mapDetailVo.put("price", jsonObj.get("price").toString());
					}else{
						mapDetailVo.put("price", "0");
					}
					if (ChdJson.validateJSON(String.valueOf(jsonObj.get("sale_price")))) {
						mapDetailVo.put("sale_price", jsonObj.get("sale_price").toString());
					}else{
						mapDetailVo.put("sale_price", "0");
					}
					if (ChdJson.validateJSON(String.valueOf(jsonObj.get("sell_price")))) {
						mapDetailVo.put("sell_price", jsonObj.get("sell_price").toString());
					}else{
						mapDetailVo.put("sell_price", "0");
					}
					if (ChdJson.validateJSON(String.valueOf(jsonObj.get("allot_price")))) {
						mapDetailVo.put("allot_price", jsonObj.get("allot_price").toString());
					}else{
						mapDetailVo.put("allot_price", "0");
					}
					if (ChdJson.validateJSON(String.valueOf(jsonObj.get("bar_code")))) {
						mapDetailVo.put("bar_code", jsonObj.get("bar_code").toString());
					}else{
						mapDetailVo.put("bar_code", null);
					}
					if (ChdJson.validateJSON(String.valueOf(jsonObj.get("inva_date")))) {
						mapDetailVo.put("inva_date", DateUtil.stringToDate(jsonObj.get("inva_date").toString(), "yyyy-MM-dd"));
					}else{
						mapDetailVo.put("inva_date", null);
					}
					if (ChdJson.validateJSON(String.valueOf(jsonObj.get("disinfect_date")))) {
						mapDetailVo.put("disinfect_date", DateUtil.stringToDate(jsonObj.get("disinfect_date").toString(), "yyyy-MM-dd"));
					}else{
						mapDetailVo.put("disinfect_date", null);
					}
					if (ChdJson.validateJSON(String.valueOf(jsonObj.get("location_out_id")))) {
						mapDetailVo.put("location_out_id", jsonObj.get("location_out_id").toString());
					}else{
						mapDetailVo.put("location_out_id", "0");
					}
					mapDetailVo.put("location_id", mapDetailVo.get("location_out_id"));  //用于先进先出算法
					if (ChdJson.validateJSON(String.valueOf(jsonObj.get("location_in_id")))) {
						mapDetailVo.put("location_in_id", jsonObj.get("location_in_id").toString());
					}else{
						mapDetailVo.put("location_in_id", "0");
					}
					if (ChdJson.validateJSON(String.valueOf(jsonObj.get("note")))) {
						mapDetailVo.put("note", jsonObj.get("note").toString());
					}else{
						mapDetailVo.put("note", null);
					}
					/*材料主信息-----------------end-----------------------*/
					
					/**申请单信息-----------------begin---------------------*/
					String[] otherIds = null;
					int otherIndex = 0;  //数组下标
					int otherLen = 0;  //数组下标最大值
					if (ChdJson.validateJSON(String.valueOf(jsonObj.get("other_ids")))) {
						otherIds = jsonObj.get("other_ids").toString().split(",");
						otherLen = otherIds.length - 1;  
					}
					/**申请单信息-----------------end-----------------------*/
	
					/*材料批次信息---------------begin---------------------*/
					//如果sum_amount存在值并且等于amount则取inv_detail_data中的批次信息
					if(ChdJson.validateJSON(String.valueOf(jsonObj.get("sum_amount"))) && ChdJson.validateJSON(String.valueOf(jsonObj.get("amount"))) && ChdJson.validateJSON(String.valueOf(jsonObj.get("inv_detail_data"))) && String.valueOf(jsonObj.get("sum_amount")).equals(String.valueOf(jsonObj.get("amount")))){
						
						for(Map<String, Object> m : JsonListMapUtil.getListMap(jsonObj.get("inv_detail_data").toString())){
							//没材料ID视为空行
							if(m.get("inv_id") != null && !"".equals(String.valueOf(m.get("inv_id")))){
								Map<String, Object> detailMap = new HashMap<String, Object>();
								Map<String, Object> inDetailMap = new HashMap<String, Object>();
								Map<String, Object> outDetailMap = new HashMap<String, Object>();
								detailMap.putAll(mapDetailVo);
								detailMap.put("tran_detail_id", String.valueOf(matCommonOutApplyCheckMapper.queryMatTranDetailSeqNext()));
								if (ChdJson.validateJSON(String.valueOf(m.get("batch_sn")))) {detailMap.put("batch_sn", m.get("batch_sn").toString());}
								if (ChdJson.validateJSON(String.valueOf(m.get("amount")))) {detailMap.put("amount", m.get("amount").toString());}
								//金额根据系统参数重新对数量和单价的乘积进行四舍五入
								if (ChdJson.validateJSON(String.valueOf(m.get("amount_money")))) {
									detailMap.put("amount_money", m.get("amount_money").toString());
									detailMap.put("amount_money", String.valueOf(NumberUtil.numberToRound(Double.valueOf(detailMap.get("amount").toString()) * Double.valueOf(String.valueOf((detailMap.get("price") == null ? 0 : detailMap.get("price")))), money_para)));
									//money_sum = money_sum + Float.parseFloat(detailMap.get("amount_money").toString());//记录总金额
									money_sum = NumberUtil.add(Double.valueOf(money_sum), Double.valueOf(detailMap.get("amount_money").toString()));//记录总金额
								}
								if (ChdJson.validateJSON(String.valueOf(m.get("sale_money")))) {
									detailMap.put("sale_money", m.get("sale_money").toString());
									detailMap.put("sale_money", String.valueOf(NumberUtil.numberToRound(Double.valueOf(detailMap.get("amount").toString()) * Double.valueOf(String.valueOf((detailMap.get("sale_price") == null ? 0 : detailMap.get("sale_price")))), money_para)));
								}
								if (ChdJson.validateJSON(String.valueOf(m.get("sell_money")))) {
									detailMap.put("sell_money", m.get("sell_money").toString());
									detailMap.put("sell_money", String.valueOf(NumberUtil.numberToRound(Double.valueOf(detailMap.get("amount").toString()) * Double.valueOf(String.valueOf((detailMap.get("sell_price") == null ? 0 : detailMap.get("sell_price")))), money_para)));
								}
								if (ChdJson.validateJSON(String.valueOf(m.get("allot_money")))) {detailMap.put("allot_money", m.get("allot_money").toString());}
								tran_detail_batch.add(detailMap);
			
								/**-----------------处理对应关系---------begin---------*/
								if(otherIds != null && otherIndex <= otherLen){
									
									boolean is_addRela = false;
									//获取出库数量
									double do_amount = Double.valueOf(detailMap.get("amount") == null ? "0" : detailMap.get("amount").toString());
									double rela_amount = 0;
									String appStr = "";
									Map<String, Object> relaMap = new HashMap<String, Object>();
									relaMap.putAll(appOutRelaMap);
									//循环减对应关系数量
									while(do_amount > 0 && otherIndex <= otherLen){
										String[] rela = otherIds[otherIndex].split("@");
										double app_amount = Double.valueOf(rela[2]);
										//如果一样证明来自不同的申请单需添加新的对应关系
										if(!"".equals(appStr) && !appStr.equals(rela[0] + rela[1])){
											appOutRelaList.add(relaMap);
											relaMap = new HashMap<String, Object>();
											relaMap.putAll(appOutRelaMap);
											is_addRela = false;
										}
										if(!is_addRela){
											relaMap.put("apply_id", rela[0]);
											relaMap.put("app_detail_id", rela[1]);
											relaMap.put("rela_detail_id", detailMap.get("tran_detail_id").toString());
										}
										rela_amount = relaMap.get("rela_amount") == null ? 0 : Double.valueOf(relaMap.get("rela_amount").toString());
										if(do_amount >= app_amount){
											relaMap.put("rela_amount", rela_amount + app_amount);
											do_amount = NumberUtil.sub(do_amount, app_amount);
											otherIndex ++;
										}else{
											relaMap.put("rela_amount", rela_amount + do_amount);
											otherIds[otherIndex] = rela[0] + "@" + rela[1] + "@" + (NumberUtil.sub(app_amount, do_amount)); 
											do_amount = 0;
										}
										is_addRela = true;
										appStr = rela[0] + rela[1];
									}
									appOutRelaList.add(relaMap);
									
								}
								/**-----------------处理对应关系--------end------------*/
								
								//处理出入库货位信息
								outDetailMap.putAll(detailMap);
								outDetailMap.put("out_id", outMap.get("out_id").toString());
								outDetailMap.put("out_no", outMap.get("out_no").toString());
								outDetailMap.put("out_detail_id", matCommonOutApplyCheckMapper.queryMatOutDetailSeqNext());
								if(ChdJson.validateJSON(String.valueOf(detailMap.get("location_out_id")))){
									outDetailMap.put("location_id", detailMap.get("location_out_id").toString());
								}else{
									outDetailMap.put("location_id", "0");
								}
								outDetailList.add(outDetailMap);
								if(is_in){
									inDetailMap.putAll(detailMap);
									inDetailMap.put("in_id", inMap.get("in_id").toString());
									inDetailMap.put("in_no", inMap.get("in_no").toString());
									inDetailMap.put("in_detail_id", matInCommonMapper.queryMatInDetailSeq());
									if(ChdJson.validateJSON(String.valueOf(detailMap.get("inva_date")))){
										inDetailMap.put("inva_date", (Date)detailMap.get("inva_date"));
									}else{
										inDetailMap.put("inva_date",  null);
									}
									if(ChdJson.validateJSON(String.valueOf(detailMap.get("disinfect_date")))){
										inDetailMap.put("disinfect_date", (Date)detailMap.get("disinfect_date"));
									}else{
										inDetailMap.put("disinfect",  null);
									}
									if(ChdJson.validateJSON(String.valueOf(detailMap.get("location_in_id")))){
										inDetailMap.put("location_id", detailMap.get("location_in_id").toString());
									}else{
										inDetailMap.put("location_id", "0");
									}
									inDetailList.add(inDetailMap);
								}
							}
						}
					}else{
						//按先进先出匹配批次信息
						List<Map<String, Object>> fifoList = toListMapLower((List<Map<String, Object>>) matCommonMapper.queryMatStockInvDetailList(mapDetailVo));
						Double amount = Double.valueOf(String.valueOf((jsonObj.get("amount") == null ? 0 : jsonObj.get("amount"))));
						Double imme_amount = null;
						//按先进先出生成出库单
						for(Map<String, Object> map : fifoList){
							Map<String, Object> detailMap = new HashMap<String, Object>();
							Map<String, Object> inDetailMap = new HashMap<String, Object>();
							Map<String, Object> outDetailMap = new HashMap<String, Object>();
							detailMap.putAll(mapDetailVo);
							detailMap.put("tran_detail_id", String.valueOf(matCommonOutApplyCheckMapper.queryMatTranDetailSeqNext()));
							detailMap.put("batch_sn", map.get("batch_sn").toString());
							//当前批次即时库存
							imme_amount = Double.valueOf(String.valueOf((map.get("imme_amount") == null ? 0 : map.get("imme_amount"))));
							//判断当前批号批次是否充足
							if(amount <= imme_amount){
								detailMap.put("amount", amount.toString());
								//计算金额
								detailMap.put("amount_money", String.valueOf(NumberUtil.numberToRound(amount * Double.valueOf(String.valueOf((map.get("price") == null ? 0 : map.get("price")))), money_para)));
								detailMap.put("sale_money", String.valueOf(NumberUtil.numberToRound(amount * Double.valueOf(String.valueOf((map.get("sale_price") == null ? 0 : map.get("sale_price")))), money_para)));
								detailMap.put("sell_money", String.valueOf(NumberUtil.numberToRound(amount * Double.valueOf(String.valueOf((map.get("sell_price") == null ? 0 : map.get("sell_price")))), money_para)));
								detailMap.put("allot_money", String.valueOf(NumberUtil.numberToRound(amount * Double.valueOf(String.valueOf((map.get("allot_price") == null ? 0 : map.get("allot_price")))), money_para)));
								//money_sum = money_sum + Float.parseFloat(detailMap.get("amount_money").toString());//记录总金额
								money_sum = NumberUtil.add(Double.valueOf(money_sum), Double.valueOf(detailMap.get("amount_money").toString()));//记录总金额
								tran_detail_batch.add(detailMap);
			
								/**-----------------处理对应关系---------begin---------*/
								if(otherIds != null && otherIndex <= otherLen){
									
									boolean is_addRela = false;
									//获取出库数量
									double do_amount = Double.valueOf(detailMap.get("amount") == null ? "0" : detailMap.get("amount").toString());
									double rela_amount = 0;
									String appStr = "";
									Map<String, Object> relaMap = new HashMap<String, Object>();
									relaMap.putAll(appOutRelaMap);
									//循环减对应关系数量
									while(do_amount > 0 && otherIndex <= otherLen){
										String[] rela = otherIds[otherIndex].split("@");
										double app_amount = Double.valueOf(rela[2]);
										//如果一样证明来自不同的申请单需添加新的对应关系
										if(!"".equals(appStr) && !appStr.equals(rela[0] + rela[1])){
											appOutRelaList.add(relaMap);
											relaMap = new HashMap<String, Object>();
											relaMap.putAll(appOutRelaMap);
											is_addRela = false;
										}
										if(!is_addRela){
											relaMap.put("apply_id", rela[0]);
											relaMap.put("app_detail_id", rela[1]);
											relaMap.put("rela_detail_id", detailMap.get("tran_detail_id").toString());
										}
										rela_amount = relaMap.get("rela_amount") == null ? 0 : Double.valueOf(relaMap.get("rela_amount").toString());
										if(do_amount >= app_amount){
											relaMap.put("rela_amount", rela_amount + app_amount);
											do_amount = NumberUtil.sub(do_amount, app_amount);
											otherIndex ++;
										}else{
											relaMap.put("rela_amount", rela_amount + do_amount);
											otherIds[otherIndex] = rela[0] + "@" + rela[1] + "@" + (NumberUtil.sub(app_amount, do_amount)); 
											do_amount = 0;
										}
										is_addRela = true;
										appStr = rela[0] + rela[1];
									}
									appOutRelaList.add(relaMap);
									
								}
								/**-----------------处理对应关系--------end------------*/
								
								//处理出入库货位信息
								outDetailMap.putAll(detailMap);
								outDetailMap.put("out_id", outMap.get("out_id").toString());
								outDetailMap.put("out_no", outMap.get("out_no").toString());
								outDetailMap.put("out_detail_id", matCommonOutApplyCheckMapper.queryMatOutDetailSeqNext());
								if(ChdJson.validateJSON(String.valueOf(detailMap.get("location_out_id")))){
									outDetailMap.put("location_id", detailMap.get("location_out_id").toString());
								}else{
									outDetailMap.put("location_id",  "0");
								}
								outDetailList.add(outDetailMap);
								if(is_in){
									inDetailMap.putAll(detailMap);
									inDetailMap.put("in_id", inMap.get("in_id").toString());
									inDetailMap.put("in_no", inMap.get("in_no").toString());
									inDetailMap.put("in_detail_id", matInCommonMapper.queryMatInDetailSeq());
									if(ChdJson.validateJSON(String.valueOf(detailMap.get("inva_date")))){
										inDetailMap.put("inva_date", (Date)detailMap.get("inva_date"));
									}else{
										inDetailMap.put("inva_date",  null);
									}
									if(ChdJson.validateJSON(String.valueOf(detailMap.get("disinfect_date")))){
										inDetailMap.put("disinfect_date", (Date)detailMap.get("disinfect_date"));
									}else{
										inDetailMap.put("disinfect_date",  null);
									}
									if(ChdJson.validateJSON(String.valueOf(detailMap.get("location_in_id")))){
										inDetailMap.put("location_id", detailMap.get("location_in_id").toString());
									}else{
										inDetailMap.put("location_id",  "0");
									}
									inDetailList.add(inDetailMap);
								}
								amount = 0.0;
								break;
							}else{
								//取当前批号批次数量并且申请单数量响应减少
								detailMap.put("amount", imme_amount.toString());		
								//计算金额
								detailMap.put("amount_money", String.valueOf(NumberUtil.numberToRound(imme_amount * Double.valueOf(String.valueOf((map.get("price") == null ? 0 : map.get("price")))), money_para)));
								detailMap.put("sale_money", String.valueOf(NumberUtil.numberToRound(imme_amount * Double.valueOf(String.valueOf((map.get("sale_price") == null ? 0 : map.get("sale_price")))), money_para)));
								detailMap.put("sell_money", String.valueOf(NumberUtil.numberToRound(imme_amount * Double.valueOf(String.valueOf((map.get("sell_price") == null ? 0 : map.get("sell_price")))), money_para)));
								detailMap.put("allot_money", String.valueOf(NumberUtil.numberToRound(imme_amount * Double.valueOf(String.valueOf((map.get("allot_price") == null ? 0 : map.get("allot_price")))), money_para)));
								//money_sum = money_sum + Float.parseFloat(detailMap.get("amount_money").toString());//记录总金额
								money_sum = NumberUtil.add(Double.valueOf(money_sum), Double.valueOf(detailMap.get("amount_money").toString()));//记录总金额
								tran_detail_batch.add(detailMap);		
			
								/**-----------------处理对应关系---------begin---------*/
								if(otherIds != null && otherIndex <= otherLen){
									
									boolean is_addRela = false;
									//获取出库数量
									double do_amount = Double.valueOf(detailMap.get("amount") == null ? "0" : detailMap.get("amount").toString());
									double rela_amount = 0;
									String appStr = "";
									Map<String, Object> relaMap = new HashMap<String, Object>();
									relaMap.putAll(appOutRelaMap);
									//循环减对应关系数量
									while(do_amount > 0 && otherIndex <= otherLen){
										String[] rela = otherIds[otherIndex].split("@");
										double app_amount = Double.valueOf(rela[2]);
										//如果一样证明来自不同的申请单需添加新的对应关系
										if(!"".equals(appStr) && !appStr.equals(rela[0] + rela[1])){
											appOutRelaList.add(relaMap);
											relaMap = new HashMap<String, Object>();
											relaMap.putAll(appOutRelaMap);
											is_addRela = false;
										}
										if(!is_addRela){
											relaMap.put("apply_id", rela[0]);
											relaMap.put("app_detail_id", rela[1]);
											relaMap.put("rela_detail_id", detailMap.get("tran_detail_id").toString());
										}
										rela_amount = relaMap.get("rela_amount") == null ? 0 : Double.valueOf(relaMap.get("rela_amount").toString());
										if(do_amount >= app_amount){
											relaMap.put("rela_amount", rela_amount + app_amount);
											do_amount = NumberUtil.sub(do_amount, app_amount);
											otherIndex ++;
										}else{
											relaMap.put("rela_amount", rela_amount + do_amount);
											otherIds[otherIndex] = rela[0] + "@" + rela[1] + "@" + (NumberUtil.sub(app_amount, do_amount)); 
											do_amount = 0;
										}
										is_addRela = true;
										appStr = rela[0] + rela[1];
									}
									appOutRelaList.add(relaMap);
									
								}
								/**-----------------处理对应关系--------end------------*/
								
								//处理出入库货位信息
								outDetailMap.putAll(detailMap);
								outDetailMap.put("out_id", outMap.get("out_id").toString());
								outDetailMap.put("out_no", outMap.get("out_no").toString());
								outDetailMap.put("out_detail_id", matCommonOutApplyCheckMapper.queryMatOutDetailSeqNext());
								if(ChdJson.validateJSON(String.valueOf(detailMap.get("location_out_id")))){
									outDetailMap.put("location_id", detailMap.get("location_out_id").toString());
								}else{
									outDetailMap.put("location_id",  "0");
								}
								outDetailList.add(outDetailMap);
								if(is_in){
									inDetailMap.putAll(detailMap);
									inDetailMap.put("in_id", inMap.get("in_id").toString());
									inDetailMap.put("in_no", inMap.get("in_no").toString());
									inDetailMap.put("in_detail_id", matInCommonMapper.queryMatInDetailSeq());
									if(ChdJson.validateJSON(String.valueOf(detailMap.get("inva_date")))){
										inDetailMap.put("inva_date", (Date)detailMap.get("inva_date"));
									}else{
										inDetailMap.put("inva_date",  null);
									}
									if(ChdJson.validateJSON(String.valueOf(detailMap.get("disinfect_date")))){
										inDetailMap.put("disinfect_date", (Date)detailMap.get("disinfect_date"));
									}else{
										inDetailMap.put("disinfect",  null);
									}
									if(ChdJson.validateJSON(String.valueOf(detailMap.get("location_in_id")))){
										inDetailMap.put("location_id", detailMap.get("location_in_id").toString());
									}else{
										inDetailMap.put("location_id",  "0");
									}
									inDetailList.add(inDetailMap);
								}
								amount = NumberUtil.sub(amount, imme_amount);
							}
							//当数量为0，证明已经完成先进先出操作
							if(amount == 0){
								break;
							}
						}
						if(amount > 0){
							invEnoughMsg.append(String.valueOf(jsonObj.get("inv_code"))).append(" ").append(String.valueOf(jsonObj.get("inv_name"))).append("<br>");
						}
					}
					/*材料批次信息---------------end-----------------------*/
				}
			}
			if(tran_detail_batch.size() <= 0){
				return "{\"error\":\"调拨单明细数据为空!\"}";
			}
			if(invEnoughMsg != null && !"".equals(invEnoughMsg.toString())){
				return "{\"error\":\"以下材料库存物资不足："+invEnoughMsg.toString()+"\"}";
			}

			//判断即时库存是否充足(防止多人同时操作同一材料，额外加的判断)
			String invEnough = matCommonMapper.existsMatStockInvIsEnough(tran_detail_batch);
			if(invEnough != null && !"".equals(invEnough)){
				return "{\"error\":\"以下材料库存物资不足!"+invEnough+"\"}";
			}
			
			//新增调拨单主表
			entityMap.put("amount_money", money_sum);
			matCommonOutApplyCheckMapper.addMatTranMainByApp(entityMap);
			//新增调拨单明细表
			matCommonOutApplyCheckMapper.addMatTranDetailByApp(tran_detail_batch);
			
			//新增出库单主表
			outMap.put("come_from", 5);
			outMap.put("amount_money", money_sum);
			matCommonOutApplyCheckMapper.addMatOutMainByApp(outMap);
			//新增出库单明细表
			matCommonOutApplyCheckMapper.addMatOutDetailByApp(outDetailList);
			//新增出库单资金来源
			outMap.put("source_money", money_sum);
			matOutResourceMapper.add(outMap);
			
			//新增入库单主表
			inMap.put("come_from", 5);
			inMap.put("amount_money", money_sum);
			matInCommonMapper.addMatInMain(inMap);
			//新增入库单明细表
			matInCommonMapper.addMatInDetailBatch(inDetailList);
			//新增入库单资金来源
			inMap.put("source_money", money_sum);
			matInCommonMapper.insertMatInResource(inMap);

			//添加调拨单与出入库单对应关系
			entityMap.put("out_id", outMap.get("out_id"));
			entityMap.put("out_no", outMap.get("out_no"));
			entityMap.put("in_id", inMap.get("in_id"));
			entityMap.put("in_no", inMap.get("in_no"));
			matTranRelaMapper.add(entityMap);
			
			//添加申请单与对应关系
			matApplyOutRelaMapper.addMatApplyOutRelaBatch(appOutRelaList);
			
		} catch (Exception e) {
			
			logger.error(e.getMessage(), e);

			throw new SysException("{\"error\":\"添加失败\"}");
			//return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 add\"}";
		}
		
		return "{\"msg1\":\"添加成功.\",\"state\":\"true\",\"update_para\":\""+
			entityMap.get("group_id").toString()+","+
			entityMap.get("hos_id").toString()+","+
			entityMap.get("copy_code").toString()+","+
			entityMap.get("tran_id").toString()+","+
			entityMap.get("out_store_id").toString()+","
			+"\"}";
	}

	/**
	 * 组装生成代销调拨单明细表数据
	 */
	@Override
	public String queryAffiTranDetail(Map<String, Object> entityMap) throws DataAccessException {
		try{
			//材料基础信息
			List<Map<String, Object>> invList = toListMapLower(matCommonOutApplyCheckMapper.querySelectDetailForOut(entityMap));

			return matCommonService.getAffiInvJsonByAffiInvList(invList);
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);

			return "{\"error\":\"生成失败 数据库异常 请联系管理员! 方法 MatCommonOutApply--queryOutDetail\"}";
		}
	}

	/**
	 * @Description 保存代销调拨单
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addAffiTran(Map<String,Object> entityMap)throws DataAccessException{
		
		//获取对象调拨方式TRAN_METHOD：1同价调拨  2 异价调拨 调拨类型：1 院内调拨  2 院外调拨
		try {

			//金额位数
			int money_para = Integer.valueOf(MyConfig.getSysPara("04005"));
			//金额存数的时候默认不设置小数点几位
			//int money_para = 6;
			
			int flag = 1;
			
			// (1)、判断当前编制日期所在期间是否存在，若不存在提示：请配置期间。
			flag = matCommonMapper.existsAccYearMonthCheck(entityMap);
			if(flag == 0){
				return "{\"error\":\"添加失败，所选期间不存在请配置！\",\"state\":\"false\"}";
			}
			
			//(2)、判断库房是否已启用
			flag = matCommonMapper.existsMatStoreIsStart(entityMap);
			if(flag == 0){
				return "{\"error\":\"添加失败，库房本期间未启用请配置！\",\"state\":\"false\"}";
			}

			entityMap.put("tran_id", matCommonOutApplyCheckMapper.queryMatAffiTranMainSeqNext());
			
			/*---------------生成调拨单号--------------------begin---------------*/
			Map<String, Object> tranMap = new HashMap<String, Object>();
			tranMap.put("group_id", entityMap.get("group_id"));
			tranMap.put("hos_id", entityMap.get("hos_id"));
			tranMap.put("copy_code", entityMap.get("copy_code"));
			tranMap.put("store_id", entityMap.get("store_id"));
			tranMap.put("year", entityMap.get("year"));
			tranMap.put("month", entityMap.get("month"));
			tranMap.put("table_code", "MAT_AFFI_TRAN_MAIN");
			tranMap.put("prefixe", "DB");
			tranMap.put("store_alias", "");
			tranMap.put("bus_type", "");

			// 判断是否存在该业务流水码
			flag = matNoManageMapper.queryIsExists(tranMap);
			String max_no = "";
			if (flag == 0) {// 如不存在则流水码为1，并插入流水码表中
				max_no = "1";
				tranMap.put("max_no", 1);
				matNoManageMapper.add(tranMap);
			} else {
				// 更新该业务流水码+1
				matNoManageMapper.updateMaxNo(tranMap);
				// 取出该业务更新后的流水码
				max_no = matNoManageMapper.queryMaxCode(tranMap);
			}
			// 补流水码前缀0
			for (int i = max_no.length(); i < 5; i++) {
				max_no = "0" + max_no;
			}
			// 组装流水码
			String tran_no = tranMap.get("prefixe") + "-" + tranMap.get("year").toString() + tranMap.get("month").toString() + max_no;
			entityMap.put("tran_no", tran_no);// 获取调拨单号
			/*---------------生成调拨单号--------------------begin---------------*/
			
			/*--------------需要同时生成出入库单--begin----------------------------*/
			//注：院内调拨需要同时生成出入库单；院外调拨只生成出库单，入库单在调入单位调入确认时生成。
			String brief = "由调拨单"+entityMap.get("tran_no")+"生成";
			String out_bus = "";
			String in_bus = "";
			boolean is_in = false;
			if("1".equals(String.valueOf(entityMap.get("tran_type")))){
				//院内调拨
				out_bus = "32";  //移出库
				in_bus = "31";  //移入库
				is_in = true;
			}else{
				//院外调拨
				if("1".equals(entityMap.get("bus_type").toString())){
					out_bus = "7";  //无偿调出
					in_bus = "6";  //无偿调入
				}else{
					out_bus = "5";  //有偿调出
					in_bus = "4";  //有偿调入
				}
			}
			//存放出库和调拨对应关系
			List<Map<String, Object>> appOutRelaList = new ArrayList<Map<String, Object>>();
			//存放出库主从表
			Map<String, Object> outMap = new HashMap<String, Object>();
			List<Map<String, Object>> outDetailList = new ArrayList<Map<String, Object>>();// 存放明细
			//存放入库主从表
			Map<String, Object> inMap = new HashMap<String, Object>();
			List<Map<String, Object>> inDetailList = new ArrayList<Map<String, Object>>();// 存放明细
			//生成出库单
			outMap.put("group_id", entityMap.get("group_id"));
			outMap.put("hos_id", entityMap.get("hos_id"));
			outMap.put("copy_code", entityMap.get("copy_code"));
			outMap.put("year", entityMap.get("year"));
			outMap.put("month", entityMap.get("month"));
			outMap.put("day", entityMap.get("day"));  //用于生成单号
			outMap.put("bus_type_code", out_bus);
			outMap.put("state", 1);
			outMap.put("brief", brief);
			outMap.put("maker", SessionManager.getUserId());
			outMap.put("out_date", entityMap.get("tran_date"));
			outMap.put("store_id", entityMap.get("out_store_id"));
			outMap.put("store_no", entityMap.get("out_store_no"));
			outMap.put("dept_id", null);
			outMap.put("dept_no", null);
			outMap.put("dept_emp", null);
			outMap.put("use_code", null);
			outMap.put("proj_id", null);
			outMap.put("table_code", "MAT_AFFI_OUT");
			outMap.put("prefixe", "");
			long out_id = matCommonOutApplyCheckMapper.queryMatAffiOutSeqNext();
			outMap.put("out_id", out_id);
			String out_no = matCommonService.getMatNextNo(outMap);
			if(out_no.indexOf("error")!=-1){
				return out_no;
			}
			outMap.put("out_no", out_no);
			
			if(is_in){
				//生成入库单
				inMap.put("group_id", entityMap.get("group_id"));
				inMap.put("hos_id", entityMap.get("hos_id"));
				inMap.put("copy_code", entityMap.get("copy_code"));
				inMap.put("year", entityMap.get("year"));
				inMap.put("month", entityMap.get("month"));
				inMap.put("day", entityMap.get("day"));  //用于生成单号
				inMap.put("bus_type_code", in_bus);
				inMap.put("state", 1);
				inMap.put("brief", brief);
				inMap.put("maker", SessionManager.getUserId());
				inMap.put("in_date", entityMap.get("tran_date"));
				
				inMap.put("bus_type_code", 14);//移入库
				inMap.put("store_id", entityMap.get("in_store_id"));
				inMap.put("store_no", entityMap.get("in_store_no"));
				inMap.put("table_code", "MAT_AFFI_IN");
				inMap.put("prefixe", "");
				inMap.put("in_id", matAffiInMapper.queryAffiInMainSeq());
				String in_no = matCommonService.getMatNextNo(inMap);
				if(in_no.indexOf("error")!=-1){
					return in_no;
				}
				inMap.put("in_no", in_no);
			}
			/*--------------需要同时生成出入库单--end------------------------------*/
			
			// 保存明细数据
			List<Map<String, Object>> tran_detail_batch = new ArrayList<Map<String, Object>>();// 存放明细
			// 解析明细数据
			JSONArray tran_detail_json = JSONArray.parseArray((String) entityMap.get("detailData"));
			
			double money_sum = 0;//记录明细总金额
			StringBuffer invEnoughMsg = new StringBuffer();//记录库存不足的材料
			Iterator tran_detail_it = tran_detail_json.iterator();

			while (tran_detail_it.hasNext()) {

				Map<String, Object> mapDetailVo = new HashMap<String, Object>();
				Map<String, Object> appOutRelaMap = new HashMap<String, Object>();
				
				/*材料主信息-----------------begin---------------------*/
				mapDetailVo.put("group_id", entityMap.get("group_id").toString());
				mapDetailVo.put("hos_id", entityMap.get("hos_id").toString());
				mapDetailVo.put("copy_code", entityMap.get("copy_code").toString());
				mapDetailVo.put("tran_id", entityMap.get("tran_id").toString());
				mapDetailVo.put("tran_no", entityMap.get("tran_no").toString());
				mapDetailVo.put("store_id", entityMap.get("out_store_id").toString());//用于查询材料信息
				
				//处理对应关系
				appOutRelaMap.put("group_id", entityMap.get("group_id").toString());
				appOutRelaMap.put("hos_id", entityMap.get("hos_id").toString());
				appOutRelaMap.put("copy_code", entityMap.get("copy_code").toString());
				appOutRelaMap.put("rela_type", 4);
				appOutRelaMap.put("rela_id", entityMap.get("tran_id").toString());

				JSONObject jsonObj = JSONObject.parseObject(tran_detail_it.next().toString());
				//不存在材料ID视为空行
				/*if (ChdJson.validateJSON(String.valueOf(jsonObj.get("inv_id")))) {
					continue;
				}*/
				if( jsonObj.get("inv_id") != null && !"".equals(jsonObj.get("inv_id"))){
					mapDetailVo.put("inv_id", jsonObj.get("inv_id").toString());
					mapDetailVo.put("inv_no", jsonObj.get("inv_no").toString());
					if (ChdJson.validateJSON(String.valueOf(jsonObj.get("batch_no")))) {
						mapDetailVo.put("batch_no", jsonObj.get("batch_no").toString());
					}else{
						mapDetailVo.put("batch_no", null);
					}
					if (ChdJson.validateJSON(String.valueOf(jsonObj.get("amount")))) {
						mapDetailVo.put("amount", jsonObj.get("amount").toString());
					}else{
						mapDetailVo.put("amount", null);
					}
					if (ChdJson.validateJSON(String.valueOf(jsonObj.get("price")))) {
						mapDetailVo.put("price", jsonObj.get("price").toString());
					}else{
						mapDetailVo.put("price", "0");
					}
					if (ChdJson.validateJSON(String.valueOf(jsonObj.get("sale_price")))) {
						mapDetailVo.put("sale_price", jsonObj.get("sale_price").toString());
					}else{
						mapDetailVo.put("sale_price", "0");
					}
					if (ChdJson.validateJSON(String.valueOf(jsonObj.get("sell_price")))) {
						mapDetailVo.put("sell_price", jsonObj.get("sell_price").toString());
					}else{
						mapDetailVo.put("sell_price", "0");
					}
					if (ChdJson.validateJSON(String.valueOf(jsonObj.get("allot_price")))) {
						mapDetailVo.put("allot_price", jsonObj.get("allot_price").toString());
					}else{
						mapDetailVo.put("allot_price", "0");
					}
					if (ChdJson.validateJSON(String.valueOf(jsonObj.get("bar_code")))) {
						mapDetailVo.put("bar_code", jsonObj.get("bar_code").toString());
					}else{
						mapDetailVo.put("bar_code", null);
					}
					if (ChdJson.validateJSON(String.valueOf(jsonObj.get("inva_date")))) {
						mapDetailVo.put("inva_date", DateUtil.stringToDate(jsonObj.get("inva_date").toString(), "yyyy-MM-dd"));
					}else{
						mapDetailVo.put("inva_date", null);
					}
					if (ChdJson.validateJSON(String.valueOf(jsonObj.get("disinfect_date")))) {
						mapDetailVo.put("disinfect_date", DateUtil.stringToDate(jsonObj.get("disinfect_date").toString(), "yyyy-MM-dd"));
					}else{
						mapDetailVo.put("disinfect_date", null);
					}
					if (ChdJson.validateJSON(String.valueOf(jsonObj.get("location_out_id")))) {
						mapDetailVo.put("location_out_id", jsonObj.get("location_out_id").toString());
					}else{
						mapDetailVo.put("location_out_id", "0");
					}
					mapDetailVo.put("location_id", mapDetailVo.get("location_out_id"));  //用于先进先出算法
					if (ChdJson.validateJSON(String.valueOf(jsonObj.get("location_in_id")))) {
						mapDetailVo.put("location_in_id", jsonObj.get("location_in_id").toString());
					}else{
						mapDetailVo.put("location_in_id", "0");
					}
					if (ChdJson.validateJSON(String.valueOf(jsonObj.get("note")))) {
						mapDetailVo.put("note", jsonObj.get("note").toString());
					}else{
						mapDetailVo.put("note", null);
					}
					/*材料主信息-----------------end-----------------------*/
					

					/**申请单信息-----------------begin---------------------*/
					String[] otherIds = null;
					int otherIndex = 0;  //数组下标
					int otherLen = 0;  //数组下标最大值
					if (ChdJson.validateJSON(String.valueOf(jsonObj.get("other_ids")))) {
						otherIds = jsonObj.get("other_ids").toString().split(",");
						otherLen = otherIds.length - 1;  
					}
					/**申请单信息-----------------end-----------------------*/

					/*材料批次信息---------------begin---------------------*/
					//如果sum_amount存在值并且等于amount则取inv_detail_data中的批次信息
					if(ChdJson.validateJSON(String.valueOf(jsonObj.get("sum_amount"))) && ChdJson.validateJSON(String.valueOf(jsonObj.get("amount"))) && ChdJson.validateJSON(String.valueOf(jsonObj.get("inv_detail_data"))) && String.valueOf(jsonObj.get("sum_amount")).equals(String.valueOf(jsonObj.get("amount")))){
						
						for(Map<String, Object> m : JsonListMapUtil.getListMap(jsonObj.get("inv_detail_data").toString())){
							//没材料ID视为空行
							if(m.get("inv_id") != null && !"".equals(String.valueOf(m.get("inv_id")))){
								Map<String, Object> detailMap = new HashMap<String, Object>();
								Map<String, Object> inDetailMap = new HashMap<String, Object>();
								Map<String, Object> outDetailMap = new HashMap<String, Object>();
								detailMap.putAll(mapDetailVo);
								detailMap.put("tran_detail_id", String.valueOf(matCommonOutApplyCheckMapper.queryMatAffiTranDetailSeqNext()));
								if (ChdJson.validateJSON(String.valueOf(m.get("batch_sn")))) {detailMap.put("batch_sn", m.get("batch_sn").toString());}
								if (ChdJson.validateJSON(String.valueOf(m.get("amount")))) {detailMap.put("amount", m.get("amount").toString());}
								//金额根据系统参数重新对数量和单价的乘积进行四舍五入
								if (ChdJson.validateJSON(String.valueOf(m.get("amount_money")))) {
									detailMap.put("amount_money", m.get("amount_money").toString());
									detailMap.put("amount_money", String.valueOf(NumberUtil.numberToRound(Double.valueOf(detailMap.get("amount").toString()) * Double.valueOf(String.valueOf((detailMap.get("price") == null ? 0 : detailMap.get("price")))), money_para)));
									//money_sum = money_sum + Float.parseFloat(detailMap.get("amount_money").toString());//记录总金额
									money_sum = NumberUtil.add(Double.valueOf(money_sum), Double.valueOf(detailMap.get("amount_money").toString()));//记录总金额
								}
								if (ChdJson.validateJSON(String.valueOf(m.get("sale_money")))) {
									detailMap.put("sale_money", m.get("sale_money").toString());
									detailMap.put("sale_money", String.valueOf(NumberUtil.numberToRound(Double.valueOf(detailMap.get("amount").toString()) * Double.valueOf(String.valueOf((detailMap.get("sale_price") == null ? 0 : detailMap.get("sale_price")))), money_para)));
								}
								if (ChdJson.validateJSON(String.valueOf(m.get("sell_money")))) {
									detailMap.put("sell_money", m.get("sell_money").toString());
									detailMap.put("sell_money", String.valueOf(NumberUtil.numberToRound(Double.valueOf(detailMap.get("amount").toString()) * Double.valueOf(String.valueOf((detailMap.get("sell_price") == null ? 0 : detailMap.get("sell_price")))), money_para)));
								}
								if (ChdJson.validateJSON(String.valueOf(m.get("allot_money")))) {detailMap.put("allot_money", m.get("allot_money").toString());}
								tran_detail_batch.add(detailMap);
								
								/**-----------------处理对应关系---------begin---------*/
								if(otherIds != null && otherIndex <= otherLen){
									
									boolean is_addRela = false;
									//获取出库数量
									double do_amount = Double.valueOf(detailMap.get("amount") == null ? "0" : detailMap.get("amount").toString());
									double rela_amount = 0;
									String appStr = "";
									Map<String, Object> relaMap = new HashMap<String, Object>();
									relaMap.putAll(appOutRelaMap);
									//循环减对应关系数量
									while(do_amount > 0 && otherIndex <= otherLen){
										String[] rela = otherIds[otherIndex].split("@");
										double app_amount = Double.valueOf(rela[2]);
										//如果一样证明来自不同的申请单需添加新的对应关系
										if(!"".equals(appStr) && !appStr.equals(rela[0] + rela[1])){
											appOutRelaList.add(relaMap);
											relaMap = new HashMap<String, Object>();
											relaMap.putAll(appOutRelaMap);
											is_addRela = false;
										}
										if(!is_addRela){
											relaMap.put("apply_id", rela[0]);
											relaMap.put("app_detail_id", rela[1]);
											relaMap.put("rela_detail_id", detailMap.get("tran_detail_id").toString());
										}
										rela_amount = relaMap.get("rela_amount") == null ? 0 : Double.valueOf(relaMap.get("rela_amount").toString());
										if(do_amount >= app_amount){
											relaMap.put("rela_amount", rela_amount + app_amount);
											do_amount = NumberUtil.sub(do_amount, app_amount);
											otherIndex ++;
										}else{
											relaMap.put("rela_amount", rela_amount + do_amount);
											otherIds[otherIndex] = rela[0] + "@" + rela[1] + "@" + (NumberUtil.sub(app_amount, do_amount)); 
											do_amount = 0;
										}
										is_addRela = true;
										appStr = rela[0] + rela[1];
									}
									appOutRelaList.add(relaMap);
									
								}
								/**-----------------处理对应关系--------end------------*/
								
								//处理出入库货位信息
								outDetailMap.putAll(detailMap);
								outDetailMap.put("out_id", outMap.get("out_id").toString());
								outDetailMap.put("out_no", outMap.get("out_no").toString());
								outDetailMap.put("detail_id", matCommonOutApplyCheckMapper.queryMatAffiOutDetailSeqNext());
								if(ChdJson.validateJSON(String.valueOf(detailMap.get("location_out_id")))){
									outDetailMap.put("location_id", detailMap.get("location_out_id").toString());
								}else{
									outDetailMap.put("location_id", "0");
								}
								outDetailList.add(outDetailMap);
								if(is_in){
									inDetailMap.putAll(detailMap);
									inDetailMap.put("in_id", inMap.get("in_id").toString());
									inDetailMap.put("in_no", inMap.get("in_no").toString());
									inDetailMap.put("detail_id", matAffiInMapper.queryAffiInDetailSeq());
									if(ChdJson.validateJSON(String.valueOf(detailMap.get("inva_date")))){
										inDetailMap.put("inva_date", (Date)detailMap.get("inva_date"));
									}else{
										inDetailMap.put("inva_date",  null);
									}
									if(ChdJson.validateJSON(String.valueOf(detailMap.get("disinfect_date")))){
										inDetailMap.put("disinfect_date", (Date)detailMap.get("disinfect_date"));
									}else{
										inDetailMap.put("disinfect_date",  null);
									}
									if(ChdJson.validateJSON(String.valueOf(detailMap.get("location_in_id")))){
										inDetailMap.put("location_id", detailMap.get("location_in_id").toString());
									}else{
										inDetailMap.put("location_id", "0");
									}
									inDetailList.add(inDetailMap);
								}
							}
						}
					}else{
						//按先进先出匹配批次信息
						List<Map<String, Object>> fifoList = toListMapLower((List<Map<String, Object>>) matCommonMapper.queryMatStockInvDetailList(mapDetailVo));
						Double amount = Double.valueOf(String.valueOf((jsonObj.get("amount") == null ? 0 : jsonObj.get("amount"))));
						Double imme_amount = null;
						//按先进先出生成出库单
						for(Map<String, Object> map : fifoList){
							Map<String, Object> detailMap = new HashMap<String, Object>();
							Map<String, Object> inDetailMap = new HashMap<String, Object>();
							Map<String, Object> outDetailMap = new HashMap<String, Object>();
							detailMap.putAll(mapDetailVo);
							detailMap.put("tran_detail_id", String.valueOf(matCommonOutApplyCheckMapper.queryMatAffiTranDetailSeqNext()));
							detailMap.put("batch_sn", map.get("batch_sn").toString());
							//当前批次即时库存
							imme_amount = Double.valueOf(String.valueOf((map.get("imme_amount") == null ? 0 : map.get("imme_amount"))));
							//判断当前批号批次是否充足
							if(amount <= imme_amount){
								detailMap.put("amount", amount.toString());
								//计算金额
								detailMap.put("amount_money", String.valueOf(NumberUtil.numberToRound(amount * Double.valueOf(String.valueOf((map.get("price") == null ? 0 : map.get("price")))), money_para)));
								detailMap.put("sale_money", String.valueOf(NumberUtil.numberToRound(amount * Double.valueOf(String.valueOf((map.get("sale_price") == null ? 0 : map.get("sale_price")))), money_para)));
								detailMap.put("sell_money", String.valueOf(NumberUtil.numberToRound(amount * Double.valueOf(String.valueOf((map.get("sell_price") == null ? 0 : map.get("sell_price")))), money_para)));
								detailMap.put("allot_money", String.valueOf(NumberUtil.numberToRound(amount * Double.valueOf(String.valueOf((map.get("allot_price") == null ? 0 : map.get("allot_price")))), money_para)));
								//money_sum = money_sum + Float.parseFloat(detailMap.get("amount_money").toString());//记录总金额
								money_sum = NumberUtil.add(Double.valueOf(money_sum), Double.valueOf(detailMap.get("amount_money").toString()));//记录总金额
								tran_detail_batch.add(detailMap);
								
								/**-----------------处理对应关系---------begin---------*/
								if(otherIds != null && otherIndex <= otherLen){
									
									boolean is_addRela = false;
									//获取出库数量
									double do_amount = Double.valueOf(detailMap.get("amount") == null ? "0" : detailMap.get("amount").toString());
									double rela_amount = 0;
									String appStr = "";
									Map<String, Object> relaMap = new HashMap<String, Object>();
									relaMap.putAll(appOutRelaMap);
									//循环减对应关系数量
									while(do_amount > 0 && otherIndex <= otherLen){
										String[] rela = otherIds[otherIndex].split("@");
										double app_amount = Double.valueOf(rela[2]);
										//如果一样证明来自不同的申请单需添加新的对应关系
										if(!"".equals(appStr) && !appStr.equals(rela[0] + rela[1])){
											appOutRelaList.add(relaMap);
											relaMap = new HashMap<String, Object>();
											relaMap.putAll(appOutRelaMap);
											is_addRela = false;
										}
										if(!is_addRela){
											relaMap.put("apply_id", rela[0]);
											relaMap.put("app_detail_id", rela[1]);
											relaMap.put("rela_detail_id", detailMap.get("tran_detail_id").toString());
										}
										rela_amount = relaMap.get("rela_amount") == null ? 0 : Double.valueOf(relaMap.get("rela_amount").toString());
										if(do_amount >= app_amount){
											relaMap.put("rela_amount", rela_amount + app_amount);
											do_amount = NumberUtil.sub(do_amount, app_amount);
											otherIndex ++;
										}else{
											relaMap.put("rela_amount", rela_amount + do_amount);
											otherIds[otherIndex] = rela[0] + "@" + rela[1] + "@" + (NumberUtil.sub(app_amount, do_amount)); 
											do_amount = 0;
										}
										is_addRela = true;
										appStr = rela[0] + rela[1];
									}
									appOutRelaList.add(relaMap);
									
								}
								/**-----------------处理对应关系--------end------------*/
								
								//处理出入库货位信息
								outDetailMap.putAll(detailMap);
								outDetailMap.put("out_id", outMap.get("out_id").toString());
								outDetailMap.put("out_no", outMap.get("out_no").toString());
								outDetailMap.put("detail_id", matCommonOutApplyCheckMapper.queryMatAffiOutDetailSeqNext());
								if(ChdJson.validateJSON(String.valueOf(detailMap.get("location_out_id")))){
									outDetailMap.put("location_id", detailMap.get("location_out_id").toString());
								}else{
									outDetailMap.put("location_id", "0");
								}
								outDetailList.add(outDetailMap);
								if(is_in){
									inDetailMap.putAll(detailMap);
									inDetailMap.put("in_id", inMap.get("in_id").toString());
									inDetailMap.put("in_no", inMap.get("in_no").toString());
									inDetailMap.put("detail_id", matAffiInMapper.queryAffiInDetailSeq());
									if(ChdJson.validateJSON(String.valueOf(detailMap.get("inva_date")))){
										inDetailMap.put("inva_date", (Date)detailMap.get("inva_date"));
									}else{
										inDetailMap.put("inva_date",  null);
									}
									if(ChdJson.validateJSON(String.valueOf(detailMap.get("disinfect_date")))){
										inDetailMap.put("disinfect_date", (Date)detailMap.get("disinfect_date"));
									}else{
										inDetailMap.put("disinfect_date",  null);
									}
									if(ChdJson.validateJSON(String.valueOf(detailMap.get("location_in_id")))){
										inDetailMap.put("location_id", detailMap.get("location_in_id").toString());
									}else{
										inDetailMap.put("location_id", "0");
									}
									inDetailList.add(inDetailMap);
								}
								amount = 0.0;
								break;
							}else{
								//取当前批号批次数量并且申请单数量响应减少
								detailMap.put("amount", imme_amount.toString());		
								//计算金额
								detailMap.put("amount_money", String.valueOf(NumberUtil.numberToRound(imme_amount * Double.valueOf(String.valueOf((map.get("price") == null ? 0 : map.get("price")))), money_para)));
								detailMap.put("sale_money", String.valueOf(NumberUtil.numberToRound(imme_amount * Double.valueOf(String.valueOf((map.get("sale_price") == null ? 0 : map.get("sale_price")))), money_para)));
								detailMap.put("sell_money", String.valueOf(NumberUtil.numberToRound(imme_amount * Double.valueOf(String.valueOf((map.get("sell_price") == null ? 0 : map.get("sell_price")))), money_para)));
								detailMap.put("allot_money", String.valueOf(NumberUtil.numberToRound(imme_amount * Double.valueOf(String.valueOf((map.get("allot_price") == null ? 0 : map.get("allot_price")))), money_para)));
								//money_sum = money_sum + Float.parseFloat(detailMap.get("amount_money").toString());//记录总金额
								money_sum = NumberUtil.add(Double.valueOf(money_sum), Double.valueOf(detailMap.get("amount_money").toString()));//记录总金额
								tran_detail_batch.add(detailMap);	
								
								/**-----------------处理对应关系---------begin---------*/
								if(otherIds != null && otherIndex <= otherLen){
									
									boolean is_addRela = false;
									//获取出库数量
									double do_amount = Double.valueOf(detailMap.get("amount") == null ? "0" : detailMap.get("amount").toString());
									double rela_amount = 0;
									String appStr = "";
									Map<String, Object> relaMap = new HashMap<String, Object>();
									relaMap.putAll(appOutRelaMap);
									//循环减对应关系数量
									while(do_amount > 0 && otherIndex <= otherLen){
										String[] rela = otherIds[otherIndex].split("@");
										double app_amount = Double.valueOf(rela[2]);
										//如果一样证明来自不同的申请单需添加新的对应关系
										if(!"".equals(appStr) && !appStr.equals(rela[0] + rela[1])){
											appOutRelaList.add(relaMap);
											relaMap = new HashMap<String, Object>();
											relaMap.putAll(appOutRelaMap);
											is_addRela = false;
										}
										if(!is_addRela){
											relaMap.put("apply_id", rela[0]);
											relaMap.put("app_detail_id", rela[1]);
											relaMap.put("rela_detail_id", detailMap.get("tran_detail_id").toString());
										}
										rela_amount = relaMap.get("rela_amount") == null ? 0 : Double.valueOf(relaMap.get("rela_amount").toString());
										if(do_amount >= app_amount){
											relaMap.put("rela_amount", rela_amount + app_amount);
											do_amount = NumberUtil.sub(do_amount, app_amount);
											otherIndex ++;
										}else{
											relaMap.put("rela_amount", rela_amount + do_amount);
											otherIds[otherIndex] = rela[0] + "@" + rela[1] + "@" + (NumberUtil.sub(app_amount, do_amount)); 
											do_amount = 0;
										}
										is_addRela = true;
										appStr = rela[0] + rela[1];
									}
									appOutRelaList.add(relaMap);
									
								}
								/**-----------------处理对应关系--------end------------*/
								
								//处理出入库货位信息
								outDetailMap.putAll(detailMap);
								outDetailMap.put("out_id", outMap.get("out_id").toString());
								outDetailMap.put("out_no", outMap.get("out_no").toString());
								outDetailMap.put("detail_id", matCommonOutApplyCheckMapper.queryMatAffiOutDetailSeqNext());
								if(ChdJson.validateJSON(String.valueOf(detailMap.get("location_out_id")))){
									outDetailMap.put("location_id", detailMap.get("location_out_id").toString());
								}else{
									outDetailMap.put("location_id", "0");
								}
								outDetailList.add(outDetailMap);
								if(is_in){
									inDetailMap.putAll(detailMap);
									inDetailMap.put("in_id", inMap.get("in_id").toString());
									inDetailMap.put("in_no", inMap.get("in_no").toString());
									inDetailMap.put("detail_id", matAffiInMapper.queryAffiInDetailSeq());
									if(ChdJson.validateJSON(String.valueOf(detailMap.get("inva_date")))){
										inDetailMap.put("inva_date", (Date)detailMap.get("inva_date"));
									}else{
										inDetailMap.put("inva_date",  null);
									}
									if(ChdJson.validateJSON(String.valueOf(detailMap.get("disinfect_date")))){
										inDetailMap.put("disinfect_date", (Date)detailMap.get("disinfect_date"));
									}else{
										inDetailMap.put("disinfect_date",  null);
									}
									if(ChdJson.validateJSON(String.valueOf(detailMap.get("location_in_id")))){
										inDetailMap.put("location_id", detailMap.get("location_in_id").toString());
									}else{
										inDetailMap.put("location_id", "0");
									}
									inDetailList.add(inDetailMap);
								}
								amount = NumberUtil.sub(amount, imme_amount);
							}
							//当数量为0，证明已经完成先进先出操作
							if(amount == 0){
								break;
							}
						}
						if(amount > 0){
							invEnoughMsg.append(String.valueOf(jsonObj.get("inv_code"))).append(" ").append(String.valueOf(jsonObj.get("inv_name"))).append("<br>");
						}
					}
					/*材料批次信息---------------end-----------------------*/
					
					
				}
				

				
			}
			if(tran_detail_batch.size() <= 0){
				return "{\"error\":\"代销调拨单明细数据为空!\"}";
			}
			if(invEnoughMsg != null && !"".equals(invEnoughMsg.toString())){
				return "{\"error\":\"以下代销材料库存物资不足："+invEnoughMsg.toString()+"\"}";
			}

			//判断即时库存是否充足(防止多人同时操作同一材料，额外加的判断)
			String invEnough = matCommonMapper.existsMatAffiInvIsEnough(tran_detail_batch);
			if(invEnough != null && !"".equals(invEnough)){
				return "{\"error\":\"以下代销材料库存物资不足!"+invEnough+"\"}";
			}
			
			//新增代销调拨单主表
			matCommonOutApplyCheckMapper.addMatAffiTranMainByApp(entityMap);
			//新增代销调拨单明细表
			matCommonOutApplyCheckMapper.addMatAffiTranDetailByApp(tran_detail_batch);
			
			//新增代销出库单主表
			matCommonOutApplyCheckMapper.addMatAffiOutByApp(outMap);
			//新增代销出库单明细表
			matCommonOutApplyCheckMapper.addMatAffiOutDetailByApp(outDetailList);
			
			//新增代销入库单主表
			matAffiInMapper.addMatAffiInMain(inMap);
			//新增代销入库单明细表
			matAffiInMapper.addMatAffiInDetail(inDetailList);

			//添加调拨单与出入库单对应关系
			entityMap.put("out_id", outMap.get("out_id"));
			entityMap.put("out_no", outMap.get("out_no"));
			entityMap.put("in_id", inMap.get("in_id"));
			entityMap.put("in_no", inMap.get("in_no"));
			matAffiTranRelaMapper.add(entityMap);
			
			//添加申请单与对应关系
			matApplyOutRelaMapper.addMatApplyOutRelaBatch(appOutRelaList);
			
		} catch (Exception e) {
			
			logger.error(e.getMessage(), e);

			throw new SysException("{\"error\":\"添加失败\"}");
			//return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 add\"}";
		}

		return "{\"msg1\":\"添加成功.\",\"state\":\"true\",\"update_para\":\""+
				entityMap.get("group_id").toString()+","+
				entityMap.get("hos_id").toString()+","+
				entityMap.get("copy_code").toString()+","+
				entityMap.get("tran_id").toString()+","+
				entityMap.get("out_store_id").toString()+","
				+"\"}";
	}
	
	/**
	 * 组装生成科室需求计划主表数据
	 */
	@Override
	public Map<String, Object> queryReqMain(Map<String, Object> entityMap) throws DataAccessException {
		
		return toMapLower(matCommonOutApplyCheckMapper.queryReqMain(entityMap));
	}
	/**
	 * 组装生成科室需求计划明细表数据
	 */
	@Override
	public String queryReqDetail(Map<String, Object> entityMap) throws DataAccessException {
		try{
			
			return ChdJson.toJson(toListMapLower(matCommonOutApplyCheckMapper.queryReqDetail(entityMap)));
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);

			return "{\"error\":\"生成失败 数据库异常 请联系管理员! 方法 MatCommonOutApply--queryOutDetail\"}";
		}
	}

	/**
	 * @Description 保存科室需求计划
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addReq(Map<String,Object> entityMap)throws DataAccessException{
		Long req_id = (long) 0;
		//组装流水码
		String req_code = "";
		try{
			//用于保存的主表数据
			
			if(ChdJson.validateJSON(String.valueOf(entityMap.get("make_date")))){
				entityMap.put("make_date", DateUtil.stringToDate(entityMap.get("make_date").toString(), "yyyy-MM-dd"));
			}else{
				entityMap.put("make_date", null);
			}
			if(ChdJson.validateJSON(String.valueOf(entityMap.get("rdate")))){
				entityMap.put("rdate", DateUtil.stringToDate(entityMap.get("rdate").toString(), "yyyy-MM-dd"));
			}else{
				entityMap.put("rdate", null);
			}
			/**自动生成需求单号单据号-----------------------------begin---------------------------*/
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("group_id", entityMap.get("group_id"));
			map.put("hos_id", entityMap.get("hos_id"));
			map.put("copy_code", entityMap.get("copy_code"));
			map.put("table_code", "MAT_REQUIRE_MAIN");
			map.put("year", entityMap.get("year"));
			map.put("month", entityMap.get("month"));
			map.put("day", entityMap.get("day"));
			map.put("prefixe", "XQ");
			map.put("store_alias", "");
			map.put("bus_type", "");
			req_code = matCommonService.getMatNextNo(map);
			entityMap.put("req_code", req_code);
			/**自动生成需求单号单据号-----------------------------end-----------------------------*/
			//获得自增ID
			req_id = matCommonOutApplyCheckMapper.queryMatRequireMainSeqNext();
			entityMap.put("req_id", String.valueOf(req_id));
			
			//存放对应关系
			List<Map<String, Object>> applyRelaList= new ArrayList<Map<String,Object>>();
			
			//插入明细数据
			List<Map<String, Object>> detailList= new ArrayList<Map<String,Object>>();
			JSONArray json = JSONArray.parseArray((String)entityMap.get("detailData"));
			Iterator it = json.iterator();
			while (it.hasNext()) {
				JSONObject jsonObj = JSONObject.parseObject(it.next().toString());
				Map<String, Object> detailMap = new HashMap<String, Object>();

				//不存在材料ID视为空行
				if(ChdJson.validateJSON(String.valueOf(jsonObj.get("inv_id")))){
					detailMap.put("group_id", entityMap.get("group_id").toString());
					detailMap.put("hos_id", entityMap.get("hos_id").toString());
					detailMap.put("copy_code", entityMap.get("copy_code").toString());
					detailMap.put("req_id", entityMap.get("req_id").toString());//计划单ID
					detailMap.put("req_code", entityMap.get("req_code").toString());//计划单code
					detailMap.put("req_detail_id", String.valueOf(matCommonOutApplyCheckMapper.queryMatRequireDetailSeqNext()));
					
					detailMap.put("inv_id", jsonObj.getString("inv_id"));//材料ID
					detailMap.put("inv_no", jsonObj.getString("inv_no"));//材料变更ID
					//数量
					if (ChdJson.validateJSON(String.valueOf(jsonObj.get("amount")))) {
						detailMap.put("amount", jsonObj.getString("amount"));
					}else{
						detailMap.put("amount", null);
					}
					//计划单价
					if (ChdJson.validateJSON(String.valueOf(jsonObj.get("price")))) {
						detailMap.put("price", jsonObj.getString("price"));
					}else{
						detailMap.put("price", null);
					}
					//包装单位
					if (ChdJson.validateJSON(String.valueOf(jsonObj.get("pack_code")))) {
						detailMap.put("pack_code", jsonObj.getString("pack_code"));
					}else{
						detailMap.put("pack_code", null);
					}
					//包装数量
					if (ChdJson.validateJSON(String.valueOf(jsonObj.get("num")))) {
						detailMap.put("num", jsonObj.getString("num"));
					}else{
						detailMap.put("num", null);
					}
					//包装换算量
					if (ChdJson.validateJSON(String.valueOf(jsonObj.get("num_exchange")))) {
						detailMap.put("num_exchange", jsonObj.getString("num_exchange"));
					}else{
						detailMap.put("num_exchange", null);
					}
					
					if(ChdJson.validateJSON(String.valueOf(jsonObj.get("sup_id")))){//如果在下拉列表修改了供应商 值为sup_id+","+sup_no
						if(jsonObj.get("sup_id").toString().contains(",")){
							detailMap.put("sup_id",jsonObj.get("sup_id").toString().split(",")[0]);
							detailMap.put("sup_no",jsonObj.get("sup_id").toString().split(",")[1]);						
						}else{
							detailMap.put("sup_id",jsonObj.get("sup_id").toString());
							detailMap.put("sup_no",jsonObj.get("sup_no").toString());
						}
					}else{
						detailMap.put("sup_id", null);
						detailMap.put("sup_no", null);
					}
					//备注
					if (ChdJson.validateJSON(String.valueOf(jsonObj.get("memo")))) {
						detailMap.put("memo", jsonObj.getString("memo"));
					}else{
						detailMap.put("memo", null);
					}
					
					detailList.add(detailMap);

					/*******************处理对应关系*********begin**********/
					if(ChdJson.validateJSON(String.valueOf(jsonObj.get("other_ids")))){
						String[] otherIds = jsonObj.get("other_ids").toString().split(",");
						if(otherIds.length > 1){
							for(String rela_ids : jsonObj.get("other_ids").toString().split(",")){
								String[] rela = rela_ids.split("@");
								Map<String, Object> relaMap = new HashMap<String, Object>();
								relaMap.put("group_id", detailMap.get("group_id"));
								relaMap.put("hos_id", detailMap.get("hos_id"));
								relaMap.put("copy_code", detailMap.get("copy_code"));
								relaMap.put("rela_id", detailMap.get("req_id"));
								relaMap.put("rela_type", "5");
								relaMap.put("apply_id", rela[0]);
								relaMap.put("app_detail_id", rela[1]);
								relaMap.put("rela_detail_id", detailMap.get("req_detail_id").toString());
								relaMap.put("rela_amount", rela[2]);
								applyRelaList.add(relaMap);
							}
						}else{
							String[] rela = otherIds[0].split("@");
							Map<String, Object> relaMap = new HashMap<String, Object>();
							relaMap.put("group_id", detailMap.get("group_id"));
							relaMap.put("hos_id", detailMap.get("hos_id"));
							relaMap.put("copy_code", detailMap.get("copy_code"));
							relaMap.put("rela_id", detailMap.get("req_id"));
							relaMap.put("rela_type", "5");
							relaMap.put("apply_id", rela[0]);
							relaMap.put("app_detail_id", rela[1]);
							relaMap.put("rela_detail_id", detailMap.get("req_detail_id").toString());
							relaMap.put("rela_amount", detailMap.get("amount").toString());
							applyRelaList.add(relaMap);
						}
					}
					/*******************处理对应关系*********end************/
				}
			}
			if(detailList.size() > 0){
				//插入主表
				entityMap.put("come_from", 2);
				matCommonOutApplyCheckMapper.addMatRequireMainByApp(entityMap);
				//插入明细表
				matCommonOutApplyCheckMapper.addMatRequireDetailByApp(detailList);
				//添加对应关系
				matApplyOutRelaMapper.addMatApplyOutRelaBatch(applyRelaList);
			}else{
				return "{\"error\":\"添加失败，明细数据不能为空！\"}";
			}
		}catch (Exception e) {
			logger.error(e.getMessage(), e);
			
			throw new SysException("{\"error\":\"添加失败\"}");
		}	

		return "{\"msg1\":\"添加成功.\",\"state\":\"true\",\"update_para\":\""+
				entityMap.get("group_id").toString()+","+
				entityMap.get("hos_id").toString()+","+
				entityMap.get("copy_code").toString()+","+
				entityMap.get("req_id").toString()+","+
				entityMap.get("req_code").toString()+","
				+"\"}";
	}
	
	/**
	 * @Description 
	 * 查询对应的单据列表<BR> 
	 * @param  entityMap<BR>
	 * @return String
	 * @throws DataAccessException
	*/
	@Override
	public String queryMatApplyRela(Map<String,Object> entityMap)throws DataAccessException{
		return ChdJson.toJsonLower(matCommonOutApplyCheckMapper.queryMatApplyRela(entityMap));
	}
	
	/**
	 * @Description 
	 * 删除对应的单据<BR> 
	 * @param  entityMap<BR>
	 * @return String
	 * @throws DataAccessException
	*/
	@Override
	public String deleteMatApplyRela(List<Map<String,Object>> entityList)throws DataAccessException{
		try {
			String flag = matApplyOutRelaMapper.existsMatApplyOutRelaStateBatch(entityList);
			if(flag != null && !"".equals(flag) && !"0".equals(flag)){
				return "{\"error\":\"所选单据不是新建或未审核的单据\"}";
			}else{
				//存放需要删除的单据
				List<Map<String, Object>> outDeleteList = new ArrayList<Map<String,Object>>();
				List<Map<String, Object>> affiOutDeleteList = new ArrayList<Map<String,Object>>();
				List<Map<String, Object>> tranDeleteList = new ArrayList<Map<String,Object>>();
				List<Map<String, Object>> affiTranDeleteList = new ArrayList<Map<String,Object>>();
				List<Map<String, Object>> reqDeleteList = new ArrayList<Map<String,Object>>();
				//删除相应单据
				for(Map<String, Object> entityMap : entityList){
					if(entityMap.get("rela_type") != null){
						Map<String, Object> map = new HashMap<String, Object>();
						map.put("group_id", entityMap.get("group_id"));
						map.put("hos_id", entityMap.get("hos_id"));
						map.put("copy_code", entityMap.get("copy_code"));
						//出库
						if("1".equals(entityMap.get("rela_type").toString())){
							map.put("out_id", entityMap.get("rela_id"));
							outDeleteList.add(map);
						}//调拨
						else if("2".equals(entityMap.get("rela_type").toString())){
							map.put("tran_id", entityMap.get("rela_id"));
							tranDeleteList.add(map);
						}//代销出库
						else if("3".equals(entityMap.get("rela_type").toString())){
							map.put("out_id", entityMap.get("rela_id"));
							affiOutDeleteList.add(map);
						}//代销调拨
						else if("4".equals(entityMap.get("rela_type").toString())){
							map.put("tran_id", entityMap.get("rela_id"));
							affiTranDeleteList.add(map);
						}//科室需求计划
						else if("5".equals(entityMap.get("rela_type").toString())){
							map.put("req_id", entityMap.get("rela_id"));
							reqDeleteList.add(map);
						}
					}
				}
				
				if(outDeleteList.size() > 0){
					//删除出库单资金来源
					matOutResourceMapper.deleteBatch(outDeleteList);
					//删除出库单明细表
					matCommonOutApplyCheckMapper.deleteMatOutDetailByApp(outDeleteList);
					//删除出库单主表
					matCommonOutApplyCheckMapper.deleteMatOutMainByApp(outDeleteList);
				}
				if(tranDeleteList.size() > 0){
					//查询调拨单对应出入库单据
					List<Map<String, Object>> tranRelaList = toListMapLower(matTranRelaMapper.queryListForDelete(tranDeleteList));
					if(tranRelaList.size() > 0){
						//删除入库单资金来源
						matInCommonMapper.deleteMatInResourceBatch(tranRelaList);
						//删除入库单明细表
						matInCommonMapper.deleteMatInDetailBatch(tranRelaList);
						//删除入库单主表
						matInCommonMapper.deleteMatInMainBatch(tranRelaList);
						//删除出库单资金来源
						matOutResourceMapper.deleteBatch(tranRelaList);
						//删除出库单明细表
						matCommonOutApplyCheckMapper.deleteMatOutDetailByApp(tranRelaList);
						//删除出库单主表
						matCommonOutApplyCheckMapper.deleteMatOutMainByApp(tranRelaList);
					}
					//删除调拨单与出入库单对应关系
					matTranRelaMapper.deleteBatch(tranDeleteList);
					//删除调拨单明细表
					matCommonOutApplyCheckMapper.deleteMatTranDetailByApp(tranDeleteList);
					//删除调拨单明细表
					matCommonOutApplyCheckMapper.deleteMatTranMainByApp(tranDeleteList);
				}
				if(affiOutDeleteList.size() > 0){
					//删除代销出库单明细
					matCommonOutApplyCheckMapper.deleteMatAffiOutDetailByApp(affiOutDeleteList);
					//删除代销出库单主表
					matCommonOutApplyCheckMapper.deleteMatAffiOutByApp(affiOutDeleteList);
				}
				if(affiTranDeleteList.size() > 0){
					//查询代销调拨单对应代销出入库单据
					List<Map<String, Object>> tranRelaList = toListMapLower(matAffiTranRelaMapper.queryListForDelete(affiTranDeleteList));
					if(tranRelaList.size() > 0){
						//删除代销入库单明细表
						matAffiInMapper.deleteBatchMatAffiInDetail(tranRelaList);//明细表
						//删除代销入库单主表
						matAffiInMapper.deleteBatchMatAffiInMain(tranRelaList);//主表
						//删除代销出库单明细表
						matCommonOutApplyCheckMapper.deleteMatAffiOutDetailByApp(tranRelaList);
						//删除代销出库单主表
						matCommonOutApplyCheckMapper.deleteMatAffiOutByApp(tranRelaList);
					}
					//删除代销调拨单与代销出入库单对应关系
					matAffiTranRelaMapper.deleteBatch(affiTranDeleteList);
					//删除代销调拨单明细表
					matCommonOutApplyCheckMapper.deleteMatAffiTranDetailByApp(affiTranDeleteList);
					//删除代销调拨单主表
					matCommonOutApplyCheckMapper.deleteMatAffiTranMainByApp(affiTranDeleteList);
				}
				if(reqDeleteList.size() > 0){
					//删除科室需求计划明细表
					matCommonOutApplyCheckMapper.deleteMatRequireDetailByApp(reqDeleteList);
					//删除科室需求计划主表
					matCommonOutApplyCheckMapper.deleteMatRequireMainByApp(reqDeleteList);
				}
				//删除对应关系
				matApplyOutRelaMapper.deleteMatApplyOutRelaBatch(entityList);
			}
		} catch (Exception e) {

			logger.error(e.getMessage(), e);
			
			throw new SysException("{\"error\":\"删除失败\"}");
		}	
		
		return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
	}
	
	/**
	 * @Description 
	 * 查询对应的出库单主表信息<BR> 
	 * @param  entityMap<BR>
	 * @return String
	 * @throws DataAccessException
	*/
	@Override
	public Map<String, Object> queryRelaOutMainByCode(Map<String,Object> entityMap)throws DataAccessException{
		return toMapLower(matCommonOutApplyCheckMapper.queryRelaOutMainByCode(entityMap));
	}
	
	/**
	 * @Description 
	 * 查询对应的出库单明细表信息<BR> 
	 * @param  entityMap<BR>
	 * @return String
	 * @throws DataAccessException
	*/
	@Override
	public String queryRelaOutDetailByCode(Map<String,Object> entityMap)throws DataAccessException{
		return ChdJson.toJsonLower(matCommonOutApplyCheckMapper.queryRelaOutDetailByCode(entityMap));
	}
	
	/**
	 * @Description 
	 * 查询对应的代销出库单主表信息<BR> 
	 * @param  entityMap<BR>
	 * @return String
	 * @throws DataAccessException
	*/
	@Override
	public Map<String, Object> queryRelaAffiOutMainByCode(Map<String,Object> entityMap)throws DataAccessException{
		return toMapLower(matCommonOutApplyCheckMapper.queryRelaAffiOutMainByCode(entityMap));
	}
	
	/**
	 * @Description 
	 * 查询对应的代销出库单明细表信息<BR> 
	 * @param  entityMap<BR>
	 * @return String
	 * @throws DataAccessException
	*/
	@Override
	public String queryRelaAffiOutDetailByCode(Map<String,Object> entityMap)throws DataAccessException{
		return ChdJson.toJsonLower(matCommonOutApplyCheckMapper.queryRelaAffiOutDetailByCode(entityMap));
	}
	
	/**
	 * @Description 
	 * 查询对应的调拨单主表信息<BR> 
	 * @param  entityMap<BR>
	 * @return String
	 * @throws DataAccessException
	*/
	@Override
	public Map<String, Object> queryRelaTranMainByCode(Map<String,Object> entityMap)throws DataAccessException{
		return toMapLower(matCommonOutApplyCheckMapper.queryRelaTranMainByCode(entityMap));
	}
	
	/**
	 * @Description 
	 * 查询对应的调拨单明细表信息<BR> 
	 * @param  entityMap<BR>
	 * @return String
	 * @throws DataAccessException
	*/
	@Override
	public String queryRelaTranDetailByCode(Map<String,Object> entityMap)throws DataAccessException{
		return ChdJson.toJsonLower(matCommonOutApplyCheckMapper.queryRelaTranDetailByCode(entityMap));
	}
	
	/**
	 * @Description 
	 * 查询对应的代销调拨单主表信息<BR> 
	 * @param  entityMap<BR>
	 * @return String
	 * @throws DataAccessException
	*/
	@Override
	public Map<String, Object> queryRelaAffiTranMainByCode(Map<String,Object> entityMap)throws DataAccessException{
		return toMapLower(matCommonOutApplyCheckMapper.queryRelaAffiTranMainByCode(entityMap));
	}
	
	/**
	 * @Description 
	 * 查询对应的代销调拨单明细表信息<BR> 
	 * @param  entityMap<BR>
	 * @return String
	 * @throws DataAccessException
	*/
	@Override
	public String queryRelaAffiTranDetailByCode(Map<String,Object> entityMap)throws DataAccessException{
		return ChdJson.toJsonLower(matCommonOutApplyCheckMapper.queryRelaAffiOutDetailByCode(entityMap));
	}
	
	/**
	 * @Description 
	 * 查询对应的科室需求计划主表信息<BR> 
	 * @param  entityMap<BR>
	 * @return String
	 * @throws DataAccessException
	*/
	@Override
	public Map<String, Object> queryRelaReqMainByCode(Map<String,Object> entityMap)throws DataAccessException{
		return toMapLower(matCommonOutApplyCheckMapper.queryRelaReqMainByCode(entityMap));
	}
	
	/**
	 * @Description 
	 * 查询对应的科室需求计划明细表信息<BR> 
	 * @param  entityMap<BR>
	 * @return String
	 * @throws DataAccessException
	*/
	@Override
	public String queryRelaReqDetailByCode(Map<String,Object> entityMap)throws DataAccessException{
		return ChdJson.toJsonLower(matCommonOutApplyCheckMapper.queryRelaReqDetailByCode(entityMap));
	}
	
	//map键转小写
	public Map<String, Object> toMapLower(Map<String, Object> map) {
		
		Map<String, Object> viewMap = new HashMap<String, Object>();
		for (String key : map.keySet()) {

			viewMap.put(key.toLowerCase(), map.get(key));
		}
		
		return viewMap;
	}
	//list<map>键转小写
	public List<Map<String, Object>> toListMapLower(List<Map<String, Object>> list) {
		
		List<Map<String, Object>> viewList = new ArrayList<Map<String, Object>>();
		
		if (list.size() > 0) {

			for (Map<String, Object> map : list) {

				Map<String, Object> viewMap = new HashMap<String, Object>();

				for (String key : map.keySet()) {

					viewMap.put(key.toLowerCase(), map.get(key));
				}

				viewList.add(viewMap);
			}
		}
		return viewList;
	}
	@Resource(name = "superPrintService")
	private final SuperPrintService superPrintService = null;
	@Override
	public String queryMatOutByPrintTemlate(Map<String, Object> entityMap)throws DataAccessException {
		
		// TODO Auto-generated method stub
		try{
			//查询入库主表
			if("1".equals(String.valueOf(entityMap.get("p_num")))){ 
				
				//查询入库主表
				List<Map<String,Object>> map=matCommonOutApplyCheckMapper.queryApplyCheckPrintTemlateByMainInBatch(entityMap);
				
				//查询入库明细表
				List<Map<String,Object>> list=matCommonOutApplyCheckMapper.queryMatOutPrintTemlateByDetail(entityMap);
				 
				return superPrintService.getBatchListByPrintTemplateJson(entityMap,map,list);
			
			}else{
				//查询入库主表
				Map<String,Object> map=matCommonOutApplyCheckMapper.queryMatOutPrintTemlateByMain(entityMap);
				
				//查询入库明细表
				List<Map<String,Object>> list=matCommonOutApplyCheckMapper.queryMatOutPrintTemlateByDetail(entityMap);
				
				return superPrintService.getMapListByPrintTemplateJson(entityMap,map,list);
			}
			  
		}catch(Exception e){
			logger.error(e.getMessage(),e);
			throw new SysException(e.getMessage());
		}
	}
	@Override
    public List<Map<String, Object>> queryMatOutRequirelExists(Map<String, Object> entityMap) throws DataAccessException {
		//查询入库明细表
		List<Map<String,Object>> list=matCommonOutApplyCheckMapper.queryMatOutRequirelExists(entityMap);
	    return list;
    }
	
	/**
	 * 新版打印
	 */
	@Override
	public Map<String,Object> queryMatOutByPrintPage(Map<String, Object> entityMap)throws DataAccessException {
		try{
			Map<String,Object> reMap=new HashMap<String,Object>();
			WebApplicationContext context = ContextLoader.getCurrentWebApplicationContext();
			MatCommonOutApplyCheckMapper matCommonOutApplyCheckMapper = (MatCommonOutApplyCheckMapper)context.getBean("matCommonOutApplyCheckMapper");
			
			//查询入库主表
			if("1".equals(String.valueOf(entityMap.get("p_num")))){ 
				
				//查询入库主表
				List<Map<String,Object>> map=matCommonOutApplyCheckMapper.queryApplyCheckPrintTemlateByMainInBatch(entityMap);
				
				//查询入库明细表
				List<Map<String,Object>> list=matCommonOutApplyCheckMapper.queryMatOutPrintTemlateByDetail(entityMap);
				 
				reMap.put("main", map);
				reMap.put("detail", list);
				
				return reMap;
			
			}else{
				//查询入库主表
				Map<String,Object> map=matCommonOutApplyCheckMapper.queryMatOutPrintTemlateByMain(entityMap);
				
				//查询入库明细表
				List<Map<String,Object>> list=matCommonOutApplyCheckMapper.queryMatOutPrintTemlateByDetail(entityMap);
				
				reMap.put("main", map);
				reMap.put("detail", list);
				
				return reMap;
			}
			  
		}catch(Exception e){
			logger.error(e.getMessage(),e);
			throw new SysException(e.getMessage());
		}
	}
	@Override
	public String updateNullifyMatCommonOutApplyBatch( List<Map<String, Object>> entityMap) throws DataAccessException {
		try {	
			//批量审核
			matCommonOutApplyCheckMapper.updateByNullifyBatch(entityMap);
		}
		catch (DataAccessException e) {
			logger.error(e.getMessage(), e);
			
			throw new SysException("{\"error\":\"操作失败\"}");
			//return "{\"error\":\"发送失败 数据库异常 请联系管理员! 方法 updateSendMatCommonOutApplyBatch\"}";
		}	
		
		return "{\"msg\":\"操作成功.\",\"state\":\"true\"}";
	}

	
	 
}
