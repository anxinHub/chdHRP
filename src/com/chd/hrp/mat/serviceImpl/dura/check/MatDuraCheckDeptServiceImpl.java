/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.mat.serviceImpl.dura.check;

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
import com.chd.hrp.mat.dao.affi.in.MatAffiInCommonMapper;
import com.chd.hrp.mat.dao.base.MatCommonMapper;
import com.chd.hrp.mat.dao.dura.balance.MatDuraDeptBalanceMapper;
import com.chd.hrp.mat.dao.dura.balance.MatDuraDeptBarMapper;
import com.chd.hrp.mat.dao.dura.balance.MatDuraDeptSurplusMapper;
import com.chd.hrp.mat.dao.dura.check.MatDuraCheckDeptMapper;
import com.chd.hrp.mat.dao.dura.check.MatDuraDeptCheckRelaMapper;
import com.chd.hrp.mat.dao.dura.check.MatDuraDeptDetailMapper;
import com.chd.hrp.mat.dao.dura.check.MatDuraDeptMainMapper;
import com.chd.hrp.mat.service.base.MatCommonService;
import com.chd.hrp.mat.service.dura.check.MatDuraCheckDeptService;
import com.github.pagehelper.PageInfo;

/**
 * @Description: 耐用品科室盘点 
 * @Table: MAT_DURA_DEPT_(CHECK/CHECK_D) 
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
@Service("matDuraCheckDeptService")
public class MatDuraCheckDeptServiceImpl implements MatDuraCheckDeptService {

	private static Logger logger = Logger.getLogger(MatDuraCheckDeptServiceImpl.class);
	//引入DAO操作
	@Resource(name = "matDuraCheckDeptMapper")
	private final MatDuraCheckDeptMapper matDuraCheckDeptMapper = null;
	@Resource(name = "matCommonMapper")
	private final MatCommonMapper matCommonMapper = null;
	@Resource(name = "matCommonService")
	private final MatCommonService matCommonService = null;
	@Resource(name = "matDuraDeptBalanceMapper")
	private final MatDuraDeptBalanceMapper matDuraDeptBalanceMapper = null;
	@Resource(name = "matDuraDeptSurplusMapper")
	private final MatDuraDeptSurplusMapper matDuraDeptSurplusMapper = null;
	@Resource(name = "matDuraDeptBarMapper")
	private final MatDuraDeptBarMapper matDuraDeptBarMapper = null;
	@Resource(name = "superPrintService")
	private final SuperPrintService superPrintService = null;
	
	@Resource(name = "matDuraDeptMainMapper")
	private final MatDuraDeptMainMapper matDuraDeptMainMapper = null;
	@Resource(name = "matDuraDeptDetailMapper")
	private final MatDuraDeptDetailMapper matDuraDeptDetailMapper = null;
	@Resource(name = "matDuraDeptCheckRelaMapper")
	private final MatDuraDeptCheckRelaMapper matDuraDeptCheckRelaMapper = null;
	
	/**
	 * @Description 
	 * 添加<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String add(Map<String,Object> entityMap)throws DataAccessException{
		try {
			//编制日期
			if(entityMap.get("make_date") == null || "".equals(entityMap.get("make_date"))){
				return "{\"error\":\"编制日期不能为空\",\"state\":\"false\"}";
			}
			
			//截取期间
			String[] date = entityMap.get("make_date").toString().split("-");
			entityMap.put("year", date[0]);
			entityMap.put("month", date[1]);
			
			//转换日期格式
			if(entityMap.get("make_date") != null && !"".equals(entityMap.get("make_date"))){
				entityMap.put("make_date", DateUtil.stringToDate(entityMap.get("make_date").toString(), "yyyy-MM-dd"));
			}
			
			//判断存不存在此会计期间，如果不存在，提示请配置。
			int flag = matCommonMapper.existsAccYearMonthCheck(entityMap);
			if(flag == 0){
				return "{\"error\":\"所选期间不存在请配置！\",\"state\":\"false\"}";
			}
			
			//自动生成单据号
			if("自动生成".equals(entityMap.get("check_no"))){
				entityMap.put("table_code", "MAT_DURA_STORE_CHECK");
				entityMap.put("length_no", "5");  //流水码5位
				entityMap.put("containsDay", "0");  //单据号不按day生成
				entityMap.put("prefixe","PD");
				entityMap.put("check_no", matCommonService.getMatNextNo(entityMap));
			}
			
			entityMap.put("maker", SessionManager.getUserId());  //制单人
			entityMap.put("state", 1);  //状态
			
			//取出主表ID（自增序列）
			entityMap.put("check_id", matDuraCheckDeptMapper.queryMainSeq());
			
			//组装明细
			if(entityMap.get("detailData") != null && !"[]".equals(String.valueOf(entityMap.get("detailData")))){
				//保存明细数据
				JSONArray json = JSONArray.parseArray((String)entityMap.get("detailData"));
				List<Map<String,Object>> detailList = new ArrayList<Map<String,Object>>();
				Iterator it = json.iterator();
				while (it.hasNext()) {
					JSONObject jsonObj = JSONObject.parseObject(it.next().toString());
					if( !"".equals(jsonObj.get("inv_id")) && jsonObj.get("inv_id") != null){
						//存放明细数据
						Map<String,Object> detailMap = new HashMap<String, Object>();
						
						detailMap.put("group_id", entityMap.get("group_id").toString());
						detailMap.put("hos_id", entityMap.get("hos_id").toString());
						detailMap.put("copy_code", entityMap.get("copy_code").toString());
						detailMap.put("check_id", entityMap.get("check_id").toString());  //主表ID
						detailMap.put("detail_id", matDuraCheckDeptMapper.queryDetailSeq());  //明细表ID
						
						detailMap.put("inv_id",  jsonObj.get("inv_id").toString());  //材料ID
						detailMap.put("inv_no",  jsonObj.get("inv_no").toString());  //材料变更号
						detailMap.put("price",  jsonObj.get("price").toString());  //单价
						detailMap.put("cur_amount",  jsonObj.get("cur_amount") == null ? "" : jsonObj.get("cur_amount").toString());  //账面数量
						detailMap.put("chk_amount",  jsonObj.get("chk_amount") == null ? "" : jsonObj.get("chk_amount").toString());  //盘点数量
						detailMap.put("bar_code",  jsonObj.get("bar_code") == null ? "" : jsonObj.get("bar_code").toString());  //条码
						detailMap.put("note", jsonObj.get("note") == null ? "" : jsonObj.get("note").toString());  //备注
						
						detailList.add(detailMap);  
					}
				}
				
				if(detailList.size() == 0){
					return "{\"error\":\"明细数据为空，请选择材料\",\"state\":\"false\"}";
				}
				
				//新增入库主表数据
				matDuraCheckDeptMapper.addMain(entityMap);
				//新增入库明细数据
				matDuraCheckDeptMapper.addDetail(detailList);
			}else{
				return "{\"error\":\"明细数据为空，请选择材料\",\"state\":\"false\"}";
			}
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);
			
			throw new SysException("添加失败");
		}
		
		return "{\"msg1\":\"添加成功.\",\"state\":\"true\",\"update_para\":\""+
			entityMap.get("group_id").toString()+","+
			entityMap.get("hos_id").toString()+","+
			entityMap.get("copy_code").toString()+","+
			entityMap.get("check_id").toString()+","
			+"\"}";
	}
	
		/**
	 * @Description 
	 * 更新<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String update(Map<String,Object> entityMap)throws DataAccessException{
		
		try {
			//校验单据状态
			entityMap.put("check_state", 1);
			int flag = matDuraCheckDeptMapper.existsState(entityMap);
			if(flag > 0){
				return "{\"error\":\"单据不为未审核状态不能修改！\",\"state\":\"false\"}";
			}
			
			//组装明细
			if(entityMap.get("detailData") != null && !"[]".equals(String.valueOf(entityMap.get("detailData")))){
				//修改明细数据
				JSONArray json = JSONArray.parseArray((String)entityMap.get("detailData"));
				
				List<Map<String,Object>> detailList = new ArrayList<Map<String,Object>>();
				Iterator it = json.iterator();
				while (it.hasNext()) {
					JSONObject jsonObj = JSONObject.parseObject(it.next().toString());
					if( !"".equals(jsonObj.get("inv_id")) && jsonObj.get("inv_id") != null){
						//存放明细数据
						Map<String,Object> detailMap = new HashMap<String, Object>();
						
						detailMap.put("group_id", entityMap.get("group_id").toString());
						detailMap.put("hos_id", entityMap.get("hos_id").toString());
						detailMap.put("copy_code", entityMap.get("copy_code").toString());
						detailMap.put("check_id", entityMap.get("check_id").toString());  //主表ID
						
						detailMap.put("inv_id",  jsonObj.get("inv_id").toString());  //材料ID
						detailMap.put("inv_no",  jsonObj.get("inv_no").toString());  //材料变更号
						detailMap.put("price",  jsonObj.get("price").toString());  //单价
						detailMap.put("cur_amount",  jsonObj.get("cur_amount") == null ? "" : jsonObj.get("cur_amount").toString());  //账面数量
						detailMap.put("chk_amount",  jsonObj.get("chk_amount") == null ? "" : jsonObj.get("chk_amount").toString());  //盘点数量
						detailMap.put("bar_code",  jsonObj.get("bar_code") == null ? "" : jsonObj.get("bar_code").toString());  //条码
						detailMap.put("note", jsonObj.get("note") == null ? "" : jsonObj.get("note").toString());  //备注
						
						//明细表ID
						if(jsonObj.get("detail_id") == null || "".equals(jsonObj.get("detail_id"))){
							//获取明细ID
							detailMap.put("detail_id", String.valueOf(matDuraCheckDeptMapper.queryDetailSeq()));  //明细表ID
							detailList.add(detailMap);
						}else{
							detailMap.put("detail_id", jsonObj.get("detail_id").toString());  //明细ID
							detailList.add(detailMap);
						}
					}
				}
				
				if(detailList.size() == 0){
					return "{\"error\":\"明细数据为空，请选择材料\",\"state\":\"false\"}";
				}
				
				//修改入库主表数据
				matDuraCheckDeptMapper.updateMain(entityMap);
				
				//删除明细记录重新添加
				matDuraCheckDeptMapper.deleteDetail(entityMap);
				matDuraCheckDeptMapper.addDetail(detailList);
				
			}else{
				return "{\"error\":\"明细数据为空，请选择材料\",\"state\":\"false\"}";
			}
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);
			
			throw new SysException("更新失败");
		}		
		
		return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";
	}
    
	/**
	 * @Description 
	 * 批量删除<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String deleteBatch(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {	
			//状态不为新建不能删除
			int flag = matDuraCheckDeptMapper.existsStateBatch(entityList);
			if(flag > 0){
				return "{\"error\":\"所选单据含有状态不是未审核的单据，请重新选择！\",\"state\":\"true\"}";
			}
				
			//批量删除明细表
			matDuraCheckDeptMapper.deleteDetailBatch(entityList);
			//批量删除主表
			matDuraCheckDeptMapper.deleteMainBatch(entityList);
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);
			
			throw new SysException("删除失败");
		}	
		
		return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
	}

	/**
	 * @Description 
	 * 批量审核或消审<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	@Override
	public String auditOrUnAuditBatch(List<Map<String, Object>> entityList) throws DataAccessException {
		
		try {	
			//校验状态
			int flag = matDuraCheckDeptMapper.existsStateBatch(entityList);
			if(flag > 0){
				return "{\"error\":\"所选单据含有其他状态的单据，请重新选择！\",\"state\":\"true\"}";
			}
				
			//批量审核
			matDuraCheckDeptMapper.auditOrUnAuditBatch(entityList);
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);
			
			throw new SysException("操作失败");
		}	
		
		return "{\"msg\":\"操作成功.\",\"state\":\"true\"}";
	}
	
	/**
	 * @Description 
	 * 批量确认<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	@Override
	public synchronized String confirmBatch(List<Map<String, Object>> entityList)throws DataAccessException{
		
		try {	
			//校验状态
			int flag = matDuraCheckDeptMapper.existsStateBatch(entityList);
			if(flag > 0){
				return "{\"error\":\"所选单据含有其他状态的单据，请重新选择！\",\"state\":\"true\"}";
			}
			
			//金额位数
			int money_para = Integer.valueOf(MyConfig.getSysPara("04005"));
			
			//常量定义
			String date = DateUtil.dateToString(new Date(), "yyyy-MM-dd");
			String year = date.substring(0, 4);
			String month = date.substring(5, 7);
			
			//获取所选单据明细
			List<Map<String, Object>> detailList = matDuraCheckDeptMapper.queryDetailListForConfirm(entityList);
			
			//存放Balance表数据
			List<Map<String, Object>> updateBalanceList = new ArrayList<Map<String,Object>>();
			Map<String, Map<String, Object>> balanceMap = new HashMap<String, Map<String, Object>>();
			String balanceKey = ""; 
			
			String barKey = "";
			
			Double amount = null;  //盘点数量
			Double amount_money = null;  //盘点金额
			
			//用于记录数据
			Map<String, Object> map = null;
			
			//循环明细数据
			for(Map<String, Object> detailMap : detailList){
				//初始化循环变量
				amount = Double.valueOf(detailMap.get("amount").toString());
				//由于金额=数量*单价故需要格式化小数位数
				amount_money = NumberUtil.numberToRound(Double.valueOf(detailMap.get("amount_money").toString()), money_para);
				
				//查询库房信息
				Map<String, Object> searchMap = new HashMap<String, Object>();
				searchMap.put("group_id", detailMap.get("group_id"));
				searchMap.put("hos_id", detailMap.get("hos_id"));
				searchMap.put("copy_code", detailMap.get("copy_code"));
				searchMap.put("year", year);
				searchMap.put("month", month);
				searchMap.put("dept_id", detailMap.get("dept_id")); 
				searchMap.put("inv_id", detailMap.get("inv_id")); 
				searchMap.put("price", detailMap.get("price")); 
				searchMap.put("bar_code", detailMap.get("bar_code")); 
				
				barKey = detailMap.get("dept_id").toString() + detailMap.get("inv_id");
				balanceKey = barKey + detailMap.get("price").toString() + detailMap.get("bar_code").toString();
				
				/**操作mat_dura_dept_balance表***********begin***********/
				map = new HashMap<String, Object>();  //初始化map
				if(balanceMap.containsKey(balanceKey)){
					map = balanceMap.get(balanceKey);
				}else{
					//查询库房帐表数据
					map = matDuraDeptBalanceMapper.queryByCode(searchMap);
				}
				
				//如果不存在记录则添加
				if(map == null || map.size() == 0){
					map = new HashMap<String, Object>();
					map.putAll(searchMap);
					map.put("in_amount", amount);
					map.put("in_money", amount_money);
					map.put("out_amount", 0);
					map.put("out_money", 0);
					map.put("left_amount", amount);
					map.put("left_money", amount_money);
					map.put("operation", "insert");
				}else{
					
					if(Double.valueOf(detailMap.get("amount").toString()) > 0){
						//累加入库数据
						map.put("in_amount", NumberUtil.add(Double.valueOf(map.get("in_amount").toString()), amount));
						map.put("in_money", NumberUtil.add(Double.valueOf(map.get("in_money").toString()), amount_money));
						//累加库存量
						map.put("left_amount", NumberUtil.add(Double.valueOf(map.get("left_amount").toString()), amount));
						map.put("left_money", NumberUtil.add(Double.valueOf(map.get("left_money").toString()), amount_money));
					}else if(Double.valueOf(detailMap.get("amount").toString()) < 0){
						//判断库存是否充足
						if(-1 * Double.valueOf(map.get("left_amount").toString()) > amount){
							return "{\"error\":\"单据" + detailMap.get("check_no") + "中材料《" + 
									detailMap.get("inv_code") + " " + detailMap.get("inv_name") + "》库存不足\"}";
						}
						//累加出库数据
						map.put("out_amount", NumberUtil.sub(Double.valueOf(map.get("out_amount").toString()), amount));
						map.put("out_money", NumberUtil.sub(Double.valueOf(map.get("out_money").toString()), amount_money));
						//减少库存量
						map.put("left_amount", NumberUtil.add(Double.valueOf(map.get("left_amount").toString()), amount));
						map.put("left_money", NumberUtil.add(Double.valueOf(map.get("left_money").toString()), amount_money));
					}
					map.put("operation", "update");
				}
				//放入Map中
				balanceMap.put(balanceKey, map);
				/**操作mat_dura_store_balance表***********end*************/
			}
			
			//解析balanceMap获得需要修改的数据
			for(String key : balanceMap.keySet()){
				updateBalanceList.add(balanceMap.get(key));
			}
			
			//修改mat_dura_dept_balance表
			if(updateBalanceList.size() > 0){
				matDuraDeptBalanceMapper.updateOrAddBatch(updateBalanceList);
			}
			
			//修改单据状态
			matDuraCheckDeptMapper.confirmBatch(entityList);
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);
			
			throw new SysException("操作失败");
		}	
		
		return "{\"msg\":\"操作成功.\",\"state\":\"true\"}";
	}

	/**
	 * @Description 
	 * 查询结果集<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String query(Map<String,Object> entityMap) throws DataAccessException{
		SysPage sysPage = new SysPage();
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<?> list = matDuraCheckDeptMapper.query(entityMap);
			
			return ChdJson.toJson(list);
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			List<?> list = matDuraCheckDeptMapper.query(entityMap, rowBounds);
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
		}
	}
	
	/**
	 * @Description 
	 * 获取对象<BR> 
	 * @param  entityMap<BR>
	 *  参数为主键
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public <T> T queryMainByCode(Map<String,Object> entityMap)throws DataAccessException{
		
		return matDuraCheckDeptMapper.queryMainByCode(entityMap);
	}
	
	/**
	 * @Description 
	 * 获取对象<BR> 
	 * @param  entityMap<BR>
	 *  参数为主键
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String queryDetailByCode(Map<String,Object> entityMap)throws DataAccessException{

		List<?> list = matDuraCheckDeptMapper.queryDetailByCode(entityMap);
		
		return ChdJson.toJson(list);
	}

	/**
	 * @Description 
	 * 模板打印（包含主从表）<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	@Override
	public String queryDataByPrintTemlate(Map<String,Object> entityMap) throws DataAccessException{
		try{
			//查询主表
			if("1".equals(String.valueOf(entityMap.get("p_num")))){
				List<Map<String,Object>> map=matDuraCheckDeptMapper.queryMainForPrintTemlateBatch(entityMap);
				//查询明细表
				List<Map<String,Object>> list=matDuraCheckDeptMapper.queryDetailForPrintTemlate(entityMap);
				
				return superPrintService.getBatchListByPrintTemplateJson(entityMap,map,list);
			}else{
				Map<String,Object> map=matDuraCheckDeptMapper.queryMainForPrintTemlate(entityMap);
				//查询明细表
				List<Map<String,Object>> list=matDuraCheckDeptMapper.queryDetailForPrintTemlate(entityMap);
				
				return superPrintService.getMapListByPrintTemplateJson(entityMap,map,list);
			}
		}catch(Exception e){
			logger.error(e.getMessage(),e);
			throw new SysException(e.getMessage());
		}
	}
	
	/**
	 * 选择材料查询数据
	 */
	@Override
	public String queryMatDuraCheckByDeptId(Map<String, Object> entityMap) throws DataAccessException {
		SysPage sysPage = new SysPage();
		sysPage = (SysPage) entityMap.get("sysPage");
		if (sysPage.getTotal()==-1){
			List<Map<String,Object>> list = (List<Map<String,Object>>)matDuraCheckDeptMapper.queryMatDuraCheckByDeptId(entityMap);
			return ChdJson.toJsonLower(list);
		}else{
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			List<Map<String,Object>> list = (List<Map<String,Object>>)matDuraCheckDeptMapper.queryMatDuraCheckByDeptId(entityMap, rowBounds);
			PageInfo page = new PageInfo(list);
			return ChdJson.toJsonLower(list, page.getTotal());
		}
	}
	
	/**
	 * 组装盘点的材料
	 */
	@Override
	public String queryMatDuraCheckByDeptInvDetail(Map<String, Object> entityMap) throws DataAccessException {
		List<Map<String, Object>> detailList= new ArrayList<Map<String,Object>>();
		
		JSONArray json = JSONArray.parseArray((String)entityMap.get("allData"));
		Iterator it = json.iterator();
		while (it.hasNext()) {
			JSONObject jsonObj = JSONObject.parseObject(it.next().toString());
			if( !"".equals(jsonObj.get("inv_id")) && jsonObj.get("inv_id") != null){
				Map<String, Object> detailMap = new HashMap<String, Object>();
			
				detailMap.put("group_id", entityMap.get("group_id"));
				detailMap.put("hos_id", entityMap.get("hos_id"));
				detailMap.put("copy_code", entityMap.get("copy_code"));
				detailMap.put("dept_id", entityMap.get("dept_id"));
				detailMap.put("inv_id", jsonObj.get("inv_id"));
				detailMap.put("inv_no", jsonObj.get("inv_no"));
				detailMap.put("price", jsonObj.get("price"));
				detailList.add(detailMap);
			}
		}
		
		List<Map<String, Object>> list= matDuraCheckDeptMapper.queryMatDuraCheckByDeptInvDetail(detailList);
		return ChdJson.toJsonLower(list);
	}
	
	
	
	/**
	 * 生成出入库单
	 */
	@Override
	public String addInOut(List<Map<String, Object>> entityList) throws DataAccessException {
		try {
			//金额位数
			int money_para = Integer.valueOf(MyConfig.getSysPara("04005"));
			//同一时间
			String date = DateUtil.dateToString(new Date(), "yyyy-MM-dd");
			//String year = date.substring(0, 4);
			//String month = date.substring(5, 7);
			String day = date.substring(8, 10);
			
			//主表的年月取会计期间表
			Map<String,Object> eMap = new HashMap<String,Object>();
			eMap.put("group_id", SessionManager.getGroupId());
			eMap.put("hos_id", SessionManager.getHosId());
			eMap.put("copy_code", SessionManager.getCopyCode());
			eMap.put("dateString", date.toString());
			Map<String,Object> monthMap = JsonListMapUtil.toMapLower(matCommonMapper.queryAccYearAndMonth(eMap));
			String year = monthMap.get("year").toString();
			String month = monthMap.get("month").toString();
			
			
			Map<String, Object> checkMap = new HashMap<String, Object>();
			checkMap.put("group_id", SessionManager.getGroupId());
			checkMap.put("hos_id", SessionManager.getHosId());
			checkMap.put("copy_code", SessionManager.getCopyCode());
			checkMap.put("year", year);
			checkMap.put("month", month);
			checkMap.put("day", day);
			//判断当前编制日期所在期间是否结账，若已结账提示：当月已结账不能保存！			
			int flag = matCommonMapper.existsMatYearMonthIsAccount(checkMap);
			if(flag != 0){
				return "{\"error\":\"添加失败，当月已结账不能保存！\",\"state\":\"false\"}";
			}
			
			//存放出库数据
			List<Map<String, Object>> outMainList = new ArrayList<Map<String,Object>>();
			List<Map<String, Object>> outDetailList = new ArrayList<Map<String,Object>>();
			//存放入库数据
			List<Map<String, Object>> inMainList = new ArrayList<Map<String,Object>>();
			List<Map<String, Object>> inDetailList = new ArrayList<Map<String,Object>>();
			
			//存放盘点与出入库对应关系
			List<Map<String, Object>> relaList = new ArrayList<Map<String,Object>>();
			
			StringBuffer invEnoughMsg = new StringBuffer(); 
			
			//一个盘点单需要对应一张出入库单(根据盘盈盘亏有可能只有一张出库或一张入库单)，不能多张盘点单一起生成
			for(Map<String, Object> entityMap : entityList){
				//获取选中的主表数据
				Map<String, Object> mainMap = JsonListMapUtil.toMapLower(matDuraCheckDeptMapper.queryMatDuraCheckDeptMainForInOut(entityMap));
				//获取明细盘盈数据
				List<Map<String, Object>> inCheckList = JsonListMapUtil.toListMapLower(matDuraCheckDeptMapper.queryMatDuraCheckDeptDetailProfitForInOut(entityMap));
				//获取明细盘亏数据
				List<Map<String, Object>> outCheckList = JsonListMapUtil.toListMapLower(matDuraCheckDeptMapper.queryMatDuraCheckDeptDetailLossForInOut(entityMap));
				
				mainMap.put("brief", "由盘点单"+mainMap.get("check_no").toString()+"生成");
				
				//期间-年
				mainMap.put("year", year);
				//期间-月
				mainMap.put("month", month);
				mainMap.put("day", day);  //用于生成单号
				//制单人
				mainMap.put("maker", entityMap.get("user_id"));
				//状态
				mainMap.put("state", 1);
				
				//注：出库的时间都是String型，入库所用时间都是Date型
				//如果存在盘亏数据则根据先进先出生成出库单，
				if(outCheckList.size() > 0){
					Map<String, Object> outMap = new HashMap<String, Object>();
					outMap.putAll(mainMap);
					//业务类型
					outMap.put("bus_type_code", 44);
					outMap.put("table_code", "MAT_DURA_DEPT_MAIN");
					outMap.put("prefixe","PK");
					//单号
					String dura_no = matCommonService.getMatNextNo(outMap);
					if(dura_no.indexOf("error") > 0){
						return dura_no;
					}
					outMap.put("dura_no", dura_no);
					//单据ID
					outMap.put("dura_id", matDuraDeptMainMapper.queryMatDuraDeptMainSeq());
					outMap.put("make_date", DateUtil.stringToDate(date, "yyyy-MM-dd"));
					//明细--按先进先出取相关批次
					for(Map<String, Object> map : outCheckList){
						map.put("group_id", outMap.get("group_id").toString());
						map.put("hos_id", outMap.get("hos_id").toString());
						map.put("copy_code", outMap.get("copy_code").toString());
						map.put("dura_id", outMap.get("dura_id").toString());
						map.put("dura_no", outMap.get("dura_no").toString());
						map.put("dept_id", outMap.get("dept_id").toString());
						
						//查看库存是否足够
						Map<String, Object> leftmap = matDuraDeptBalanceMapper.queryByCode(map);
						Double amount = Double.valueOf(String.valueOf((map.get("amount") == null ? 0 : map.get("amount"))));
						Double left_amount = null;
						
						Map<String, Object> invMap = defaultOutDetailValue();
						invMap.putAll(map);
						invMap.put("detail_id", matDuraDeptMainMapper.queryMatDuraDeptMainSeq());
							
						//当前批次即时库存
						left_amount = Double.valueOf(String.valueOf((leftmap.get("left_amount") == null ? 0 : leftmap.get("left_amount"))));
						//判断当前库存是否充足
						if(amount <= left_amount){
							invMap.put("amount", amount.toString());
							//计算金额
							invMap.put("amount_money", String.valueOf(NumberUtil.numberToRound(amount * Double.valueOf(String.valueOf((leftmap.get("price") == null ? 0 : leftmap.get("price")))), money_para)));
							//记录出库明细表信息
							outDetailList.add(invMap);
						}else{
							invEnoughMsg.append(String.valueOf(map.get("inv_code"))).append(" ").append(String.valueOf(map.get("inv_name"))).append(",");
						}
					}

					//记录出库主表信息
					outMainList.add(outMap);
					//盘点单与出库单对应关系
					mainMap.put("out_dura_id", outMap.get("dura_id"));
				}
				
				//如果存在盘盈数据则生成入库单
				if(inCheckList.size() > 0){
					Map<String, Object> inMap = new HashMap<String, Object>();
					inMap.putAll(mainMap);
					//业务类型
					inMap.put("bus_type_code", 43);
					inMap.put("table_code", "MAT_DURA_DEPT_MAIN");
					inMap.put("prefixe","PY");
					//单号
					String dura_no = matCommonService.getMatNextNo(inMap);
					if(dura_no.indexOf("error") > 0){
						return dura_no;
					}
					inMap.put("dura_no", dura_no);
					//单据ID
					inMap.put("dura_id", matDuraDeptMainMapper.queryMatDuraDeptMainSeq());
					inMap.put("make_date", DateUtil.stringToDate(date, "yyyy-MM-dd"));
					//明细
					for(Map<String, Object> map : inCheckList){
						map.put("group_id", inMap.get("group_id"));
						map.put("hos_id", inMap.get("hos_id"));
						map.put("copy_code", inMap.get("copy_code"));
						map.put("dura_id", inMap.get("dura_id"));
						map.put("dura_no", inMap.get("dura_no"));
						map.put("detail_id", matDuraDeptDetailMapper.queryMatDuraDeptDetailSeq());
						inDetailList.add(map);
					}
					//记录入库主表
					inMainList.add(inMap);
					//盘点单与入库单对应关系
					mainMap.put("in_dura_id", inMap.get("dura_id"));
				}
				
				//保存盘点单和出入库单的对应关系
				if(mainMap.get("out_dura_id") == null){
					mainMap.put("out_dura_id", "");
				}
				
				if(mainMap.get("in_dura_id") == null){
					mainMap.put("in_dura_id", "");
				}
				relaList.add(mainMap);
			}

			if(invEnoughMsg.length() > 0){
				return "{\"error\":\""+invEnoughMsg.toString()+"库存物资不足\"}";
			}
			
			
			//保存出库信息
			if(outMainList.size() > 0){
				matDuraDeptMainMapper.addBatch(outMainList);
				matDuraDeptDetailMapper.addBatch(outDetailList);
			}
			
			//保存入库信息
			if(inMainList.size() > 0){
				matDuraDeptMainMapper.addBatch(inMainList);
				matDuraDeptDetailMapper.addBatch(inDetailList);
			}
			
			//保存盘点单与出入库单对应关系
			if(relaList.size() > 0){
				matDuraDeptCheckRelaMapper.addBatch(relaList);
			}
			//修改盘点单状态为3:已生成
			matDuraCheckDeptMapper.updateStateBatch(entityList);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"操作失败\"}");
			//return "{\"error\":\"操作失败 数据库异常 请联系管理员! 方法 addInOut\"}";
		}
		
		return "{\"msg\":\"操作成功.\",\"state\":\"true\"}";
	}
	
	// 返回用用于保存的默认值
	public Map<String, Object> defaultOutDetailValue() {
			
		Map<String, Object> mapDetailVo = new HashMap<String, Object>();
			
		mapDetailVo.put("group_id", "0");
		mapDetailVo.put("hos_id", "0");
		mapDetailVo.put("copy_code", "");
		mapDetailVo.put("out_id", "0");
		mapDetailVo.put("out_no", "");
		mapDetailVo.put("inv_id", "0");
		mapDetailVo.put("inv_no", "0");
		mapDetailVo.put("price", "0");
		mapDetailVo.put("amount", "0");
		mapDetailVo.put("amount_money", "0");
		mapDetailVo.put("bar_code", "-");
		mapDetailVo.put("note", "");
		
		return mapDetailVo;
	}
	/**
	 * 科室耐用品主页面查询
	 */
	@Override
	public String queryMatDuraDeptMain(Map<String, Object> entityMap) throws DataAccessException {
		SysPage sysPage = new SysPage();
		sysPage = (SysPage) entityMap.get("sysPage");
		if (sysPage.getTotal()==-1){
			List<Map<String,Object>> list = matDuraCheckDeptMapper.queryMatDuraDeptMain(entityMap);
			return ChdJson.toJsonLower(list);
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			List<Map<String,Object>> list = matDuraCheckDeptMapper.queryMatDuraDeptMain(entityMap, rowBounds);
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJsonLower(list, page.getTotal());
		}
	}
	
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
	
	/**
	 * 盘盈 盘亏单主表
	 */
	@Override
	public Map<String, Object> queryDuraDeptMain(Map<String, Object> entityMap) throws DataAccessException {
		return JsonListMapUtil.toMapLower(matDuraDeptMainMapper.queryMainByCode(entityMap));
	}
	
	/**
	 * 盘盈 盘亏单明细
	 */
	@Override
	public String queryMatDuraDeptDetailByCode(Map<String, Object> entityMap) throws DataAccessException {
		return ChdJson.toJsonLower(matDuraDeptDetailMapper.queryDetailByCode(entityMap));
	}
	/**
	 * 审核、销审 盘盈单 盘亏单
	 */
	@Override
	public String auditOrUnAuditDeptMainBatch(List<Map<String, Object>> entityList) throws DataAccessException {
		try {	
			//校验状态
			int flag = matDuraDeptMainMapper.existsMainStateBatch(entityList);
			if(flag > 0){
				return "{\"error\":\"所选单据含有其他状态的单据，请重新选择！\",\"state\":\"true\"}";
			}
			//批量审核
			matDuraDeptMainMapper.auditOrUnAuditMainBatch(entityList);
		}catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException("操作失败");
		}	
		return "{\"msg\":\"操作成功.\",\"state\":\"true\"}";
	}
	
	/**
	 * 盘盈单、盘亏单出入库确认
	 */
	@Override
	public String confirmMatDuraDeptMain(List<Map<String, Object>> entityList) throws DataAccessException {
		try {	
			//常量定义
			String date = DateUtil.dateToString(new Date(), "yyyy-MM-dd");
			String year = date.substring(0, 4);
			String month = date.substring(5, 7);
			
			//获取所选单据明细(盘盈)
			List<Map<String, Object>> detailInList = JsonListMapUtil.toListMapLower(matDuraDeptDetailMapper.queryMatDuraDeptDetailListForConfirmIn(entityList));
			//获取所选单据明细(盘亏)
			List<Map<String, Object>> detailOutList = JsonListMapUtil.toListMapLower(matDuraDeptDetailMapper.queryMatDuraDeptDetailListForConfirmOut(entityList));
			
			//存放Balance表数据
			List<Map<String, Object>> addBalanceList = new ArrayList<Map<String,Object>>();
			List<Map<String, Object>> updateBalanceList = new ArrayList<Map<String,Object>>();
			Map<String, Map<String, Object>> balanceMap = new HashMap<String, Map<String, Object>>();
			String balanceKey = ""; 
			
			//存放Surplus表数据
			List<Map<String, Object>> addSurplusList = new ArrayList<Map<String,Object>>();
			List<Map<String, Object>> updateSurplusList = new ArrayList<Map<String,Object>>();
			Map<String, Map<String, Object>> surplusMap = new HashMap<String, Map<String, Object>>();
			String surplusKey = "";
			
			//存放bar表数据
			List<Map<String, Object>> deleteBarList = new ArrayList<Map<String,Object>>();
			List<Map<String, Object>> insertBarList = new ArrayList<Map<String,Object>>();
			List<Map<String, Object>> updateBarList = new ArrayList<Map<String,Object>>();
			Map<String, Map<String, Object>> barMap = new HashMap<String, Map<String, Object>>();
			
			String barKey = "";
			Double amount = null;  //转移数量
			Double amount_money = null;  //转移金额
			//用于记录数据
			Map<String, Object> map = null;
			Map<String, Object> searchMap = null ;
			
			//盘亏情况
			if(detailOutList.size() > 0){
				
				for(Map<String,Object> detailMap : detailOutList){
					//初始化循环变量
					amount = Double.valueOf(detailMap.get("amount").toString());
					amount_money = Double.valueOf(detailMap.get("amount_money").toString());
					
					//查询科室信息
					searchMap = new HashMap<String, Object>();
					searchMap.put("group_id", detailMap.get("group_id"));
					searchMap.put("hos_id", detailMap.get("hos_id"));
					searchMap.put("copy_code", detailMap.get("copy_code"));
					searchMap.put("year", year);
					searchMap.put("month", month);
					searchMap.put("dept_id", detailMap.get("dept_id")); 
					searchMap.put("inv_id", detailMap.get("inv_id"));
					searchMap.put("price", detailMap.get("price"));
					searchMap.put("bar_code", detailMap.get("bar_code"));
					
					barKey = detailMap.get("dept_id").toString() + detailMap.get("inv_id");
					balanceKey = barKey + detailMap.get("price").toString();
					surplusKey = year + month + balanceKey;
					
					/**操作mat_dura_dept_balance表***********begin***********/
					map = new HashMap<String, Object>();  //初始化map
					if(balanceMap.containsKey(balanceKey)){
						map = balanceMap.get(balanceKey);
					}else{
						//查询科室帐表数据
						map = matDuraDeptBalanceMapper.queryByCode(searchMap);
					}
					
					//出库如果不存在记录不能确认
					if(map == null || map.size() == 0){
						return "{\"error\":\"单据" + detailMap.get("dura_no") + "中材料《" + 
								detailMap.get("inv_code") + " " + detailMap.get("inv_name") + "》库存不足\"}";
					}else{
						//判断库存是否充足
						if(Double.valueOf(map.get("left_amount").toString()) < amount){
							return "{\"error\":\"单据" + detailMap.get("dura_no") + "中材料《" + 
									detailMap.get("inv_code") + " " + detailMap.get("inv_name") + "》库存不足\"}";
						}
						//记录标示为修改
						map.put("flag", "update");
						//累加出库数据
						map.put("out_amount", NumberUtil.add(Double.valueOf(map.get("out_amount").toString()), amount));
						map.put("out_money", NumberUtil.add(Double.valueOf(map.get("out_money").toString()), amount_money));
						//减少库存量
						map.put("left_amount", NumberUtil.sub(Double.valueOf(map.get("left_amount").toString()), amount));
						map.put("left_money", NumberUtil.sub(Double.valueOf(map.get("left_money").toString()), amount_money));
					}
					//放入Map中
					balanceMap.put(balanceKey, map);
					/**操作mat_dura_dept_balance表***********end*************/
					
					/**操作mat_dura_dept_surplus表************begin***********/
					map = new HashMap<String, Object>();  //初始化map
					if(surplusMap.containsKey(surplusKey)){
						map = surplusMap.get(surplusKey);
					}else{
						//查询科室帐表数据
						map = matDuraDeptSurplusMapper.queryByCode(searchMap);
					}
					
					//出库如果不存在则新增
					if(map == null || map.size() == 0){
						map = new HashMap<String, Object>();  //初始化对象，防止空指针错误
						map.put("group_id", detailMap.get("group_id"));
						map.put("hos_id", detailMap.get("hos_id"));
						map.put("copy_code", detailMap.get("copy_code"));
						map.put("year", year);
						map.put("month", month);
						map.put("dept_id", detailMap.get("dept_id"));
						map.put("inv_id", detailMap.get("inv_id"));
						map.put("price", detailMap.get("price"));
						map.put("begin_amount", 0);
						map.put("begin_money", 0);
						map.put("in_amount", 0);
						map.put("in_money", 0);
						map.put("out_amount", amount);
						map.put("out_money", amount_money);
						map.put("end_amount", 0);
						map.put("end_money", 0);
						//记录标示为新增
						map.put("flag", "insert");
					}else{
						//累加出库数据
						map.put("out_amount", NumberUtil.add(Double.valueOf(map.get("out_amount").toString()), amount));
						map.put("out_money", NumberUtil.add(Double.valueOf(map.get("out_money").toString()), amount_money));
						
						//已标记过的记录不再标记（注：如果第一次标记为新增第二次就不能标记为修改）
						if(!map.containsKey("flag")){
							//记录标示为修改
							map.put("flag", "update");
						}
					}
					//放入Map中
					surplusMap.put(surplusKey, map);
					/**操作mat_dura_dept_surplus表************end*************/
					
					/**操作mat_dura_dept_bar表****************begin***********/
					//判断是否为存在条码
					if(detailMap.get("bar_code") == null || "".equals(detailMap.get("bar_code"))){
						 detailMap.put("bar_code", "-");
					}
					map = new HashMap<String, Object>();  //初始化map
					if(barMap.containsKey(barKey)){
						map = barMap.get(barKey);
					}else{
						//查询科室条码数据
						map = matDuraDeptBarMapper.queryByCode(searchMap);
					}

					if(map == null || map.size() == 0){
						map = new HashMap<String, Object>();  //初始化map
						map.put("group_id", detailMap.get("group_id"));
						map.put("hos_id", detailMap.get("hos_id"));
						map.put("copy_code", detailMap.get("copy_code"));
						map.put("dept_id", detailMap.get("dept_id")); 
						map.put("inv_id", detailMap.get("inv_id")); 
						map.put("price", detailMap.get("price")); 
						map.put("bar_code", detailMap.get("bar_code")); 
						map.put("amount", amount); 
						//记录标示为新增
						map.put("flag", "insert");
					}else{
						//减少条码数量
						map.put("amount", NumberUtil.sub(Double.valueOf(map.get("amount").toString()), amount));
						
						//已标记过的记录不再标记（注：如果第一次标记为新增第二次就不能标记为修改）
						if(!map.containsKey("flag")){
							//记录标示为修改
							map.put("flag", "update");
						}
					}
					//放入Map中
					barMap.put(barKey, map);
					/**操作mat_dura_dept_bar表****************end*************/
				}
			}
			
			//盘盈数据
			if(detailInList.size() > 0){
				for(Map<String,Object> detailInMap : detailInList){
					//初始化循环变量
					amount = Double.valueOf(detailInMap.get("amount").toString());
					amount_money = Double.valueOf(detailInMap.get("amount_money").toString());
					
					//查询科室信息
					searchMap = new HashMap<String, Object>();
					searchMap.put("group_id", detailInMap.get("group_id"));
					searchMap.put("hos_id", detailInMap.get("hos_id"));
					searchMap.put("copy_code", detailInMap.get("copy_code"));
					searchMap.put("year", year);
					searchMap.put("month", month);
					searchMap.put("dept_id", detailInMap.get("dept_id")); 
					searchMap.put("inv_id", detailInMap.get("inv_id"));
					searchMap.put("price", detailInMap.get("price"));
					searchMap.put("bar_code", detailInMap.get("bar_code"));
					
					barKey = detailInMap.get("dept_id").toString() + detailInMap.get("inv_id");
					balanceKey = barKey + detailInMap.get("price").toString();
					surplusKey = year + month + balanceKey;

					/**操作mat_dura_dept_balance表***********begin***********/
					map = new HashMap<String, Object>();  //初始化map
					if(balanceMap.containsKey(balanceKey)){
						map = balanceMap.get(balanceKey);
					}else{
						//查询科室帐表数据
						map = matDuraDeptBalanceMapper.queryByCode(searchMap);
					}
					
					//入库如果不存在则新增
					if(map == null || map.size() == 0){
						map = new HashMap<String, Object>();  //初始化对象，防止空指针错误
						map.put("group_id", detailInMap.get("group_id"));
						map.put("hos_id", detailInMap.get("hos_id"));
						map.put("copy_code", detailInMap.get("copy_code"));
						map.put("dept_id", detailInMap.get("dept_id")); 
						map.put("inv_id", detailInMap.get("inv_id"));
						map.put("price", detailInMap.get("price"));
						map.put("in_amount", detailInMap.get("amount"));
						map.put("in_money", detailInMap.get("amount_money"));
						map.put("out_amount", 0);
						map.put("out_money", 0);
						map.put("left_amount", detailInMap.get("amount"));
						map.put("left_money", detailInMap.get("amount_money"));
						//记录标示为新增
						map.put("flag", "insert");
					}else{
						//累加出库数据
						map.put("in_amount", NumberUtil.add(Double.valueOf(map.get("in_amount").toString()), amount));
						map.put("in_money", NumberUtil.add(Double.valueOf(map.get("in_money").toString()), amount_money));
						//增加库存量
						map.put("left_amount", NumberUtil.add(Double.valueOf(map.get("left_amount").toString()), amount));
						map.put("left_money", NumberUtil.add(Double.valueOf(map.get("left_money").toString()), amount_money));
						
						//已标记过的记录不再标记（注：如果第一次标记为新增第二次就不能标记为修改）
						if(!map.containsKey("flag")){
							//记录标示为修改
							map.put("flag", "update");
						}
					}
					//放入Map中
					balanceMap.put(balanceKey, map);
					/**操作mat_dura_dept_balance表***********end*************/
					
					/**操作mat_dura_dept_surplus表************begin***********/
					map = new HashMap<String, Object>();  //初始化map
					if(surplusMap.containsKey(surplusKey)){
						map = surplusMap.get(surplusKey);
					}else{
						//查询科室帐表数据
						map = matDuraDeptSurplusMapper.queryByCode(searchMap);
					}
					
					//移出库如果不存在则新增
					if(map == null || map.size() == 0){
						map = new HashMap<String, Object>();  //初始化对象，防止空指针错误
						map.put("group_id", detailInMap.get("group_id"));
						map.put("hos_id", detailInMap.get("hos_id"));
						map.put("copy_code", detailInMap.get("copy_code"));
						map.put("year", year);
						map.put("month", month);
						map.put("dept_id", detailInMap.get("dept_id"));
						map.put("inv_id", detailInMap.get("inv_id"));
						map.put("price", detailInMap.get("price"));
						map.put("begin_amount", 0);
						map.put("begin_money", 0);
						map.put("in_amount", amount);
						map.put("in_money", amount_money);
						map.put("out_amount", 0);
						map.put("out_money", 0);
						map.put("end_amount", 0);
						map.put("end_money", 0);
						//记录标示为新增
						map.put("flag", "insert");
					}else{
						//累加出库数据
						map.put("in_amount", NumberUtil.add(Double.valueOf(map.get("in_amount").toString()), amount));
						map.put("in_money", NumberUtil.add(Double.valueOf(map.get("in_money").toString()), amount_money));
						
						//已标记过的记录不再标记（注：如果第一次标记为新增第二次就不能标记为修改）
						if(!map.containsKey("flag")){
							//记录标示为修改
							map.put("flag", "update");
						}
					}
					//放入Map中
					surplusMap.put(surplusKey, map);
					/**操作mat_dura_dept_surplus表************end*************/
					
					/**操作mat_dura_dept_bar表****************begin***********/
					map = new HashMap<String, Object>();  //初始化map
					if(barMap.containsKey(barKey)){
						map = barMap.get(barKey);
					}else{
						//查询科室条码数据
						map = matDuraDeptBarMapper.queryByCode(searchMap);
					}

					if(map == null || map.size() == 0){
						map = new HashMap<String, Object>();  //初始化map
						map.put("group_id", detailInMap.get("group_id"));
						map.put("hos_id", detailInMap.get("hos_id"));
						map.put("copy_code", detailInMap.get("copy_code"));
						map.put("dept_id", detailInMap.get("dept_id")); 
						map.put("inv_id", detailInMap.get("inv_id")); 
						map.put("price", detailInMap.get("price")); 
						map.put("bar_code", detailInMap.get("bar_code")); 
						map.put("amount", amount); 
						//记录标示为新增
						map.put("flag", "insert");
					}else{
						//累加条码数量
						map.put("amount", NumberUtil.add(Double.valueOf(map.get("amount").toString()), amount));
						
						//已标记过的记录不再标记（注：如果第一次标记为新增第二次就不能标记为修改）
						if(!map.containsKey("flag")){
							//记录标示为修改
							map.put("flag", "update");
						}
					}
					//放入Map中
					barMap.put(barKey, map);
					/**操作mat_dura_dept_bar表****************end*************/
				}
			}
			
			
			//解析balanceMap获得需要添加和修改的数据
			for(String key : balanceMap.keySet()){
				Map<String, Object> vMap = balanceMap.get(key);
				if("insert".equals(vMap.get("flag").toString())){
					//新增
					addBalanceList.add(vMap);
				}else if("update".equals(vMap.get("flag").toString())){
					//修改
					updateBalanceList.add(vMap);
				}
			}
			
			//解析surplusMap获得需要添加和修改的数据
			for(String key : surplusMap.keySet()){
				Map<String, Object> vMap = surplusMap.get(key);
				if("insert".equals(vMap.get("flag").toString())){
					//新增
					addSurplusList.add(vMap);
				}else if("update".equals(vMap.get("flag").toString())){
					//修改
					updateSurplusList.add(vMap);
				}
			}
			
			//解析barMap获得需要添加和修改、删除的数据
			for(String key : barMap.keySet()){
				Map<String, Object> vMap = barMap.get(key);
				if("update".equals(vMap.get("flag").toString())){
					if(0 == Double.valueOf(vMap.get("amount").toString())){
						//移除
						deleteBarList.add(vMap);
					}else{
						//修改
						updateBarList.add(vMap);
					}
				}else if("insert".equals(vMap.get("flag").toString())){
					//新增
					insertBarList.add(vMap);
				}
			}
			
			//插入或修改mat_dura_dept_balance表
			if(addBalanceList.size() > 0){
				matDuraDeptBalanceMapper.addBatch(addBalanceList);
			}
			if(updateBalanceList.size() > 0){
				matDuraDeptBalanceMapper.updateBatch(updateBalanceList);
			}
			
			//插入或修改mat_dura_dept_surplus表
			if(addSurplusList.size() > 0){
				matDuraDeptSurplusMapper.addBatch(addSurplusList);
			}
			if(updateSurplusList.size() > 0){
				matDuraDeptSurplusMapper.updateBatch(updateSurplusList);
			}

			//插入、修改或删除mat_dura_dept_bar表
			if(deleteBarList.size() > 0){
				matDuraDeptBarMapper.deleteBatch(deleteBarList);
			}
			if(insertBarList.size() > 0){
				matDuraDeptBarMapper.addBatch(insertBarList);
			}
			if(updateBarList.size() > 0){
				matDuraDeptBarMapper.updateBatch(updateBarList);
			}
			
			//修改单据状态
			matDuraDeptMainMapper.confirmMainBatch(entityList);
			
		}catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException("操作失败");
		}	
		return "{\"msg\":\"操作成功.\",\"state\":\"true\"}";
	}
	
	/**
	 * 盘点单打印
	 */
	@Override
	public Map<String, Object> queryMatDuraDeptByPrintTemlate(Map<String, Object> entityMap) throws DataAccessException {
		try {
			Map<String,Object> reMap=new HashMap<String,Object>();
			WebApplicationContext context = ContextLoader.getCurrentWebApplicationContext();
			MatDuraCheckDeptMapper matDuraCheckDeptMapper = (MatDuraCheckDeptMapper)context.getBean("matDuraCheckDeptMapper");
			if("1".equals(String.valueOf(entityMap.get("p_num")))){ 
				List<Map<String, Object>> map = matDuraCheckDeptMapper.queryMainForPrintTemlateBatch(entityMap);
				List<Map<String,Object>> list=matDuraCheckDeptMapper.queryDetailForPrintTemlate(entityMap);
				reMap.put("main", map);
				reMap.put("detail", list);
			}else{
				Map<String,Object> map=matDuraCheckDeptMapper.queryMainForPrintTemlate(entityMap);
				//查询明细表
				List<Map<String,Object>>  list=matDuraCheckDeptMapper.queryDetailForPrintTemlate(entityMap);
				reMap.put("main", map);
				reMap.put("detail", list);
			}
			return reMap;
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			throw new SysException(e.getMessage());
		}
		
	}

	@Override
	public String queryMatInvByBalance(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		return ChdJson.toJsonLower(matDuraCheckDeptMapper.queryMatInvByBalance(entityMap));
	}

	
}
