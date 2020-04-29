package com.chd.hrp.sys.serviceImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.nutz.dao.DaoException;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.chd.base.ChdToken;
import com.chd.base.SessionManager;
import com.chd.base.SysPage;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.base.util.DateUtil;
import com.chd.base.util.JsonListMapUtil;
import com.chd.base.util.NumberUtil;
import com.chd.hrp.mat.dao.base.MatCommonMapper;
import com.chd.hrp.mat.dao.base.MatInCommonMapper;
import com.chd.hrp.mat.dao.base.MatNoOtherMapper;
import com.chd.hrp.mat.service.base.MatCommonService;
import com.chd.hrp.mat.service.info.basic.MatInvService;
import com.chd.hrp.mat.service.storage.in.MatStorageInService;
import com.chd.hrp.sys.controller.SupController;
import com.chd.hrp.sys.dao.EcsMapper;
import com.chd.hrp.sys.service.EcsService;
import com.chd.webservice.client.hrp.impl.MatWebService;
import com.github.pagehelper.PageInfo;

@Service("ecsService")
public class EcsServiceImpl implements EcsService{

	private static Logger logger = Logger.getLogger(EcsServiceImpl.class);
	
	@Resource(name="ecsMapper")
	private final EcsMapper ecsMapper = null;
	@Resource(name="matInvService")
	private final MatInvService matInvService = null;
	@Resource(name="matStorageInService")
	private final MatStorageInService matStorageInService = null;
	@Resource(name="matCommonMapper")
	private final MatCommonMapper matCommonMapper = null;
	@Resource(name="matCommonService")
	private final MatCommonService matCommonService = null;
	@Resource(name="matInCommonMapper")
	private final MatInCommonMapper matInCommonMapper = null;
	@Resource(name="matNoOtherMapper")
	private final MatNoOtherMapper matNoOtherMapper = null;
	
	
	@Bean
	public MatWebService matWebService(){
		return new MatWebService();
	}
	
	@Override
	public String queryMatSupProdSpec(Map<String, Object> mapVo) {
		
		SysPage sysPage = new SysPage();
		sysPage = (SysPage) mapVo.get("sysPage");
		
		mapVo.put("user_id", SessionManager.getUserId());
		
		List<Map<String, Object>> list = null;
		if (sysPage == null || sysPage.getTotal()==-1){
			list = ecsMapper.queryMatSupProdSpec(mapVo);
			
			return ChdJson.toJsonLower(list);
		}else{
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			list = ecsMapper.queryMatSupProdSpec(mapVo, rowBounds);
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJsonLower(list, page.getTotal());
		}
	}
	@Override
	public Map<String,Object> querySupProdSpecStateById(Map<String, Object> mapVo) {
		Map<String, Object> map= ecsMapper.querySupProdSpecStateById(mapVo);
		return map;
	}

	@Override
	public Map<String, Object> addMatSupProdSpecInv(Map<String, Object> mapVo) {
		
		try {
			Map<String,Object> MatSupProdSpec = new HashMap<String, Object>();
			 
			Map<String,Object> map =ecsMapper.querySupProdSpecStateById(mapVo);
			if(!map.get("CHECK_STATE").toString().equals("1")){
				 MatSupProdSpec.put("error", "重复审核");
				 MatSupProdSpec.put("state", false);
				return MatSupProdSpec;
			}
			
			String httpJson=matWebService().pushSupProSpecAudit(JSON.toJSONString(mapVo));
			boolean isHttp=false;
			if(httpJson!=null && !httpJson.equals("")){
					JSONObject json1=JSONObject.parseObject(httpJson);
					if(json1.getString("state")!=null && json1.getString("state").equals("false")){
						MatSupProdSpec.put("state", false);
						MatSupProdSpec.put("error", json1.getString("msg"));
						return MatSupProdSpec;
					}else if(json1.getString("state")!=null && json1.getString("state").equals("true")){
						isHttp=true;
					}
			 }
			
			if(!isHttp){
				 MatSupProdSpec.put("state", false);
				 MatSupProdSpec.put("error", "前置机通信异常");
				 return MatSupProdSpec;
			 }
			
			if(mapVo.get("check_state").toString().equals("2")){
				ecsMapper.addMatSupProdSpecInv(mapVo);
			}
			 
			ecsMapper.updateSupProdSpecStateById(mapVo);
				 
			 MatSupProdSpec.put("msg", "操作成功");
			 MatSupProdSpec.put("state", true);
			 return MatSupProdSpec;
		} catch (DaoException e) {
			
			logger.error(e.getMessage(), e);
			
			throw new SysException(e);
		}
	}

	@Override
	public String queryMatInvList(Map<String, Object> mapVo) {

		SysPage sysPage = new SysPage();
		sysPage = (SysPage) mapVo.get("sysPage");
		
		mapVo.put("user_id", SessionManager.getUserId());
		
		List<Map<String, Object>> list = null;
		if (sysPage == null || sysPage.getTotal()==-1){
			list = ecsMapper.queryMatInvList(mapVo);
			
			return ChdJson.toJsonLower(list);
		}else{
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			list = ecsMapper.queryMatInvList(mapVo, rowBounds);
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJsonLower(list, page.getTotal());
		}
	}

	@Override
	public Map<String, Object> addMatInv(Map<String, Object> mapVo){
		Map<String, Object> retMap = new HashMap<String, Object>();
		
		try{
			//添加材料
			Map<String, Object> retInvMap = JSON.parseObject(matInvService.add(mapVo));
			if(retInvMap.get("state") == null || "false".equals(retInvMap.get("state").toString())){
				
				throw new SysException("操作失败");
			}
			
			//根据证件名称判断证件是否已存在，如不存在则新增证件，如存在则新增对应关系
			Map<String, Object> certMap = new HashMap<String, Object>();
			certMap.put("group_id", mapVo.get("group_id"));
			certMap.put("hos_id", mapVo.get("hos_id"));
			certMap.put("copy_code", mapVo.get("copy_code"));
			certMap.put("cert_code", mapVo.get("cert_code"));
			certMap.put("fac_id", mapVo.get("fac_id"));
			certMap.put("sup_id", mapVo.get("sup_id"));
			String cert_id = ecsMapper.queryMatCertIdByEcs(mapVo);
			if(cert_id == null || "".equals(cert_id)){
				//供应商证件不存在需新增
				cert_id = String.valueOf(ecsMapper.queryMatInvCertSeq());
				certMap.put("cert_id", cert_id);
				certMap.put("type_id", ecsMapper.queryMatInvCertTypeID(mapVo));
				if(mapVo.get("start_date") != null && !"".equals(mapVo.get("start_date").toString())){
					certMap.put("cert_date", DateUtil.stringToDate(mapVo.get("start_date").toString(), "yyyy-MM-dd"));
					certMap.put("start_date", DateUtil.stringToDate(mapVo.get("start_date").toString(), "yyyy-MM-dd"));
				}else{
					certMap.put("cert_date", null);
					certMap.put("start_date", null);
				}
				if(mapVo.get("end_date") != null && !"".equals(mapVo.get("end_date").toString())){
					certMap.put("end_date", DateUtil.stringToDate(mapVo.get("end_date").toString(), "yyyy-MM-dd"));
				}else{
					certMap.put("end_date", null);
				}
				certMap.put("issuing_authority", mapVo.get("package"));
				certMap.put("inv_name", mapVo.get("inv_name"));
				certMap.put("user_id", SessionManager.getUserId());
				//新增供应商证件
				ecsMapper.addMatInvCertByEcs(certMap);
			}
			//新增材料证件对应关系
			certMap.put("inv_id", retInvMap.get("inv_id"));
			ecsMapper.addMatInvCertRelaByEcs(certMap);
			
			
			//添加对应关系
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("group_id", mapVo.get("group_id"));
			map.put("hos_id", mapVo.get("hos_id"));
			map.put("copy_code", mapVo.get("copy_code"));
			map.put("prod_id", mapVo.get("prod_id"));
			map.put("spec_id", mapVo.get("spec_id"));
			map.put("chos_id", mapVo.get("chos_id"));
			map.put("csup_id", mapVo.get("csup_id"));
			map.put("sid", mapVo.get("sid"));
			map.put("inv_id", retInvMap.get("inv_id"));
			map.put("check_state", 2);
			
			Map<String, Object> retSupMap = addMatSupProdSpecInv(map);
			if(retSupMap.get("state") == null || "false".equals(retSupMap.get("state").toString())){
				
				throw new SysException("操作失败");
			}
			
			retMap.put("state", "true");
			retMap.put("msg", "操作成功");
		}catch(Exception e){
			
			logger.error(e.getMessage(), e);
			throw new SysException("操作失败");
		}
		return retMap;
	}
	
	//查询送货单列表
	@Override
	public String queryMatDeliveryList(Map<String, Object> mapVo){

		SysPage sysPage = new SysPage();
		sysPage = (SysPage) mapVo.get("sysPage");
		
		if(mapVo.get("begin_date") != null && !"".equals(mapVo.get("begin_date"))){
			mapVo.put("begin_date", DateUtil.stringToDate(mapVo.get("begin_date").toString(), "yyyy-MM-dd"));
		}
		if(mapVo.get("end_date") != null && !"".equals(mapVo.get("end_date"))){
			mapVo.put("end_date", DateUtil.stringToDate(mapVo.get("end_date").toString(), "yyyy-MM-dd"));
		}
		
		List<Map<String, Object>> list = null;
		if (sysPage == null || sysPage.getTotal()==-1){
			
			list = ecsMapper.queryMatDeliveryList(mapVo);
			
			return ChdJson.toJsonLower(list);
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			list = ecsMapper.queryMatDeliveryList(mapVo, rowBounds);
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJsonLower(list, page.getTotal());
		}
	}

	//主表加载
	@Override
	public Map<String, Object> queryMatDeliveryMainByCode(Map<String, Object> mapVo){
		Map<String, Object> retMap = new HashMap<String, Object>();
		
		try{
			retMap = JsonListMapUtil.toMapLower(ecsMapper.queryMatDeliveryMainByCode(mapVo));
			
		}catch(Exception e){
			
			logger.error(e.getMessage(), e);
			throw new SysException("操作失败");
		}
		return retMap;
	}

	//根据主表主键加载明细
	@Override
	public String queryMatDeliveryDetailByCode(Map<String, Object> mapVo){

		List<Map<String, Object>> list = ecsMapper.queryMatDeliveryDetailByCode(mapVo);
			
		return ChdJson.toJsonLower(list);
	}
	
	//送货单签收/作废
	@Override
	public Map<String, Object> updateMatDeliveryState(Map<String, Object> entityMap){
		Map<String, Object> retMap = new HashMap<String, Object>();
		try {
			if(entityMap.get("state") == null || "".equals(entityMap.get("state").toString())){
				retMap.put("state", false);
				retMap.put("error", "state为空");
				return retMap;
			}
			
			entityMap.put("is_com", Integer.valueOf(entityMap.get("state").toString()) + 1);  //该字段用于平台
			String httpJson=matWebService().pushSupPsdState(entityMap);
			boolean isHttp=false;
			if(httpJson != null && !httpJson.equals("")){
				JSONObject json1 = JSONObject.parseObject(httpJson);
				if(json1.getString("state") != null && json1.getString("state").equals("false")){
					retMap.put("state", false);
					retMap.put("error", json1.getString("msg"));
					return retMap;
				}else if(json1.getString("state") != null && json1.getString("state").equals("true")){
					isHttp = true;
				}
			}
			
			if(!isHttp){
				retMap.put("state", false);
				retMap.put("error", "前置机通信异常");
				return retMap;
			}
			
			//修改送货单状态
			ecsMapper.updateMatDeliveryState(entityMap);
			
			retMap.put("state", "true");
			retMap.put("msg", "操作成功");
		} catch (Exception e) {
			// TODO: handle exception
		}
		return retMap;
	}
	
	//入库单新增
	@Override
	public Map<String, Object> addMatInByDelivery(Map<String, Object> entityMap){
		Map<String, Object> retMap = new HashMap<String, Object>();

		entityMap.put("group_id", SessionManager.getGroupId());
		entityMap.put("hos_id", SessionManager.getHosId());
		entityMap.put("copy_code", SessionManager.getCopyCode());
		entityMap.put("maker", SessionManager.getUserId());
		
		try {

			//金额位数
			//int money_para = Integer.valueOf(MyConfig.getSysPara("04005"));
			//金额进数据库的时候小数点不根据参数控制，只有在显示的时候控制
			int money_para =6;
			
			if(entityMap.get("in_date") == null || "".equals(entityMap.get("in_date"))){
				
				retMap.put("state", "false");
				retMap.put("error", "制单日期不能为空");
				return retMap;
			}
			//截取期间
			String[] date = entityMap.get("in_date").toString().split("-");
			//entityMap.put("year", date[0]);
			//entityMap.put("month", date[1]);
			entityMap.put("day", date[2]);  //用于生成单号
			
			//主表的年月取会计期间表
			entityMap.put("dateString", entityMap.get("in_date").toString());
			Map<String,Object> monthMap = JsonListMapUtil.toMapLower(matCommonMapper.queryAccYearAndMonth(entityMap));
			entityMap.put("year", monthMap.get("year"));
			entityMap.put("month", monthMap.get("month"));
			
			//转换日期格式
			if(entityMap.get("in_date") != null && !"".equals(entityMap.get("in_date"))){
				entityMap.put("in_date", DateUtil.stringToDate(entityMap.get("in_date").toString(), "yyyy-MM-dd"));
			}
			//发票时间
			if(entityMap.get("bill_date") != null && !entityMap.get("bill_date").toString().equals("")){
				entityMap.put("bill_date", DateUtil.stringToDate(entityMap.get("bill_date").toString(), "yyyy-MM-dd"));
			}
			 
			//判断存不存在此会计期间，如果不存在，提示请配置。
			int flag = matCommonMapper.existsAccYearMonthCheck(entityMap);
			if(flag == 0){

				retMap.put("state", "false");
				retMap.put("error", "所选期间不存在请配置！");
				return retMap;
			}
			//判断库房是否已启用
			flag = matCommonMapper.existsMatStoreIsStart(entityMap);
			if(flag == 0){

				retMap.put("state", "false");
				retMap.put("error", "添加失败，库房本期间未启用请配置！");
				return retMap;
			}
			
			//自动生成常备材料入库单据号
			if("自动生成".equals(entityMap.get("in_no"))){
				entityMap.put("table_code", "MAT_IN_MAIN");
				String in_no = matCommonService.getMatNextNo(entityMap);
				entityMap.put("in_no", in_no);
			}
			//取出主表ID（自增序列）
			entityMap.put("in_id", matInCommonMapper.queryMatInMainSeq());
			//组装明细
			if(entityMap.get("detailData") != null && !"[]".equals(String.valueOf(entityMap.get("detailData")))){
				float money_sum = 0;//记录明细总金额
				
				/*用于查询批次----begin-----*/
				Map<String,Object> batchSnMap = new HashMap<String,Object>();
				batchSnMap.put("group_id", entityMap.get("group_id"));
				batchSnMap.put("hos_id", entityMap.get("hos_id"));
				batchSnMap.put("copy_code", entityMap.get("copy_code"));
				batchSnMap.put("c_max", "batch_sn");
				batchSnMap.put("table_name", "mat_in_detail");
				batchSnMap.put("c_name", "inv_id");//查询批次所用
				batchSnMap.put("c_name1", "batch_no");//查询批次所用
				//存放相同材料批号的最大批次号
				Map<String, Integer> invBatchKeySnMap = new HashMap<String, Integer>();
				String invBatchKey = "";
				/*用于查询批次----end-----*/
				//保存明细数据
				JSONArray json = JSONArray.parseArray((String)entityMap.get("detailData"));
				List<Map<String,Object>> detailList = new ArrayList<Map<String,Object>>();
				List<Map<String,Object>> relaList = new ArrayList<Map<String,Object>>();
				Iterator it = json.iterator();
				while (it.hasNext()) {
					JSONObject jsonObj = JSONObject.parseObject(it.next().toString());
					if( jsonObj.get("inv_id") != null && !"".equals(jsonObj.get("inv_id"))){
						Map<String,Object> detailMap = new HashMap<String,Object>();
						//取出明细表ID（自增序列）
						detailMap.put("in_detail_id", matInCommonMapper.queryMatInDetailSeq());
						detailMap.put("group_id", entityMap.get("group_id"));
						detailMap.put("hos_id", entityMap.get("hos_id"));
						detailMap.put("copy_code", entityMap.get("copy_code"));
						detailMap.put("in_id", entityMap.get("in_id"));//主表ID
						detailMap.put("in_no", entityMap.get("in_no"));//主表单号
						detailMap.put("inv_id",  jsonObj.get("inv_id"));//材料ID
						detailMap.put("inv_no",  jsonObj.get("inv_no"));//材料ID
						detailMap.put("price",  jsonObj.get("price"));//单价
						detailMap.put("amount",  jsonObj.get("amount"));//数量
						//由于有时会出现（单价 * 数量 != 金额）的情况，所以不直接取前台的金额放到后台计算
						detailMap.put("amount_money", String.valueOf(NumberUtil.numberToRound(Double.valueOf(jsonObj.get("amount").toString())*Double.valueOf(jsonObj.get("price").toString()), money_para)));//金额
						
						money_sum = money_sum + Float.parseFloat(detailMap.get("amount_money").toString());//记录总金额
						
						if(ChdJson.validateJSON(String.valueOf(jsonObj.get("cert_id")))){
							detailMap.put("cert_id", jsonObj.get("cert_id"));//注册证号
						}
						if(ChdJson.validateJSON(String.valueOf(jsonObj.get("cert_code")))){
							detailMap.put("cert_code", jsonObj.get("cert_code"));//注册证号名称
						}
						if(ChdJson.validateJSON(String.valueOf(jsonObj.get("fac_date")))){
							detailMap.put("fac_date", DateUtil.stringToDate(jsonObj.get("fac_date").toString(), "yyyy-MM-dd"));//生产日期
						}
						if(ChdJson.validateJSON(String.valueOf(jsonObj.get("disinfect_no")))){
							detailMap.put("disinfect_no", jsonObj.get("disinfect_no"));//灭菌批号
						}

						if(ChdJson.validateJSON(String.valueOf(jsonObj.get("batch_no")))){
							detailMap.put("batch_no",  jsonObj.get("batch_no").toString().trim());//批号
						}else{
							detailMap.put("batch_no", "-");//默认批号
						}
						/**********************自动生成批次-------begin--------*/
						invBatchKey = detailMap.get("inv_id").toString() + detailMap.get("batch_no").toString();
						//判断是否已经取出该批号的最大批次
						if(invBatchKeySnMap.get(invBatchKey) == null){
							//查询该批号最大批次
							batchSnMap.put("c_value", detailMap.get("inv_id"));//材料ID
							batchSnMap.put("c_value1", detailMap.get("batch_no"));//材料批号
							String batchSn = matCommonMapper.getMatMaxNo(batchSnMap);//最大批次
							if(batchSn == null || "".equals(batchSn) || "0".equals(batchSn)){
								detailMap.put("batch_sn",  1);//批次
							}else{
								detailMap.put("batch_sn",  Integer.parseInt(batchSn) + 1);//批次
							}
							invBatchKeySnMap.put(invBatchKey, Integer.parseInt(String.valueOf(detailMap.get("batch_sn"))));
						}else{
							detailMap.put("batch_sn",  invBatchKeySnMap.get(invBatchKey) + 1);//批次
							invBatchKeySnMap.put(invBatchKey, invBatchKeySnMap.get(invBatchKey) + 1);
						}
						/**********************自动生成批次-------end---------*/
						if(ChdJson.validateJSON(String.valueOf(jsonObj.get("sale_price")))){
							detailMap.put("sale_price",  jsonObj.get("sale_price"));//批发价
						}else{
							detailMap.put("sale_price",  "0");//批发价
						}
						if(ChdJson.validateJSON(String.valueOf(jsonObj.get("sale_money")))){
							detailMap.put("sale_money",  jsonObj.get("sale_money"));//批发金额
						}else{
							detailMap.put("sale_money",  "0");//批发金额
						}
						if(ChdJson.validateJSON(String.valueOf(jsonObj.get("sell_price")))){
							detailMap.put("sell_price",  jsonObj.get("sell_price"));//零售价
						}else{
							detailMap.put("sell_price",  "0");//零售价
						}
						if(ChdJson.validateJSON(String.valueOf(jsonObj.get("sell_money")))){
							detailMap.put("sell_money",  jsonObj.get("sell_money"));//零售金额
						}else{
							detailMap.put("sell_money",  "0");//零售金额
						}
						if(ChdJson.validateJSON(String.valueOf(jsonObj.get("allot_price")))){
							detailMap.put("allot_price",  jsonObj.get("allot_price"));//调拨价
						}else{
							detailMap.put("allot_price",  "0");//调拨价
						}
						if(ChdJson.validateJSON(String.valueOf(jsonObj.get("allot_money")))){
							detailMap.put("allot_money",  jsonObj.get("allot_money"));//调拨金额
						}else{
							detailMap.put("allot_money",  "0");//调拨金额
						}

						if(ChdJson.validateJSON(String.valueOf(jsonObj.get("pack_code")))){
							detailMap.put("pack_code",  jsonObj.get("pack_code"));//包装单位
						}
						if(ChdJson.validateJSON(String.valueOf(jsonObj.get("num_exchange")))){
							detailMap.put("num_exchange",  jsonObj.get("num_exchange"));//转换量
						}
						if(ChdJson.validateJSON(String.valueOf(jsonObj.get("num")))){
							detailMap.put("num",  jsonObj.get("num"));//包装数量
						}
						if(ChdJson.validateJSON(String.valueOf(jsonObj.get("pack_price")))){
							detailMap.put("pack_price",  jsonObj.get("pack_price"));//包装单价
						}else{
							detailMap.put("pack_price",  "0");//包装单价
						}
						if(ChdJson.validateJSON(String.valueOf(jsonObj.get("inva_date")))){
							detailMap.put("inva_date", DateUtil.stringToDate(jsonObj.get("inva_date").toString(), "yyyy-MM-dd"));//有效日期
						}
						if(ChdJson.validateJSON(String.valueOf(jsonObj.get("disinfect_date")))){
							detailMap.put("disinfect_date", DateUtil.stringToDate(jsonObj.get("disinfect_date").toString(), "yyyy-MM-dd"));//灭菌日期
						}
						if(ChdJson.validateJSON(String.valueOf(jsonObj.get("sn")))){
							detailMap.put("sn",  jsonObj.get("sn"));//条形码（这里用个体码）
						}else{
							detailMap.put("sn", "-");
						}
						//之前的货位名称，如果存在在存当前的货位其他则为0 
						 if(ChdJson.validateJSON(String.valueOf(jsonObj.get("location_id")))){
							detailMap.put("location_id",  jsonObj.get("location_id"));//货位
						}else{
							detailMap.put("location_id",  "0");//货位
						} 
						 
						if(ChdJson.validateJSON(String.valueOf(jsonObj.get("note")))){
							detailMap.put("note",  jsonObj.get("note"));//备注
						}
						if(ChdJson.validateJSON(String.valueOf(jsonObj.get("bar_code")))){
							detailMap.put("bar_code",  jsonObj.get("bar_code").toString());//个体码
						}else{
							detailMap.put("bar_code", detailMap.get("sn"));//个体码--个体码默认条形码
						}
						
						/************处理与送货单的关系********begin*****************/
						Map<String, Object> relaMap = new HashMap<String, Object>();
						relaMap.put("group_id", detailMap.get("group_id"));
						relaMap.put("hos_id", detailMap.get("hos_id"));
						relaMap.put("copy_code", detailMap.get("copy_code"));
						relaMap.put("in_id", detailMap.get("in_id"));
						relaMap.put("in_detail_id", detailMap.get("in_detail_id"));
						relaMap.put("in_amount", detailMap.get("amount"));
						relaMap.put("dlv_id", jsonObj.get("delivery_id"));
						relaMap.put("dlv_detail_id", jsonObj.get("detail_id"));
						relaMap.put("dlv_amount", jsonObj.get("dlv_amount"));
						
						relaList.add(relaMap);
						/************处理与送货单的关系********begin*****************/
						
						//该条明细数据添加到List中
						detailList.add(detailMap);
					}
				}
				//判断发票状态
				if (entityMap.get("bill_no") != null && !"".equals(entityMap.get("bill_no").toString())) {
					entityMap.put("bill_state", 1); //货票同到
				}else{
					entityMap.put("bill_state", 0); //货到票未到
				}
				
				if(detailList.size() > 0){
					//新增入库主表数据
					entityMap.put("amount_money", money_sum);
					matInCommonMapper.addMatInMain(entityMap);
					//新增入库明细数据
					matInCommonMapper.addMatInDetailBatch(detailList);
					//新增资金来源表
					entityMap.put("source_money", money_sum);
					matInCommonMapper.insertMatInResource(entityMap);

					//新增入库单和送货单对应关系表
					ecsMapper.addDlvInRela(relaList);
				}else{
					retMap.put("state", "false");
					retMap.put("error", "请选择材料");
					return retMap;
					
				}
			}else{
				retMap.put("state", "false");
				retMap.put("error", "添加失败 明细数据为空");
				return retMap;
			}
			
		} catch (Exception e) {

			logger.error(e.getMessage(), e);
			
			throw new SysException(e.getMessage());
		}
		
		
		return retMap;
	}
	
}
