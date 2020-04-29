/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.mat.serviceImpl.base;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.MyConfig;
import com.chd.base.SessionManager;
import com.chd.base.util.DateUtil;
import com.chd.hrp.mat.dao.base.MatNoManageMapper;
import com.chd.hrp.mat.service.base.MatNoManageService;

/**
 * @Description:
 * 04199 单据号管理表
 * @Table: MAT_NO_MANAGE
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 
@Service("matNoManageService")
public class MatNoManageServiceImpl implements MatNoManageService {

	private static Logger logger = Logger.getLogger(MatNoManageServiceImpl.class);
	//引入DAO操作
	@Resource(name = "matNoManageMapper")
	private final MatNoManageMapper matNoManageMapper = null;

	/**********需求计划********begin************/
	//科室需求计划，规则 前缀+年+月+流水码
	@Override
	public String getMatReqDeptNextNo() throws DataAccessException{

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("table_code", "MAT_REQUIRE_MAIN");
		map.put("prefixe", "XQ");
		String[] dates = DateUtil.dateToString(new Date(), "yyyy-MM-dd").split("-");
		map.put("year", dates[0]);
		map.put("month", dates[1]);
		map.put("day", dates[2]);
		
		return getMatNextNo(map);
	}
	
	//仓库需求计划，规则 前缀+年+月+流水码
	@Override
	public String getMatReqStoreNextNo() throws DataAccessException{

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("table_code", "MAT_REQ_STORE_MAIN");
		map.put("prefixe", "XQ");
		String[] dates = DateUtil.dateToString(new Date(), "yyyy-MM-dd").split("-");
		map.put("year", dates[0]);
		map.put("month", dates[1]);
		map.put("day", dates[2]);
		
		return getMatNextNo(map);
	}
	/**********需求计划********end**************/

	/**********采购管理********begin************/
	//采购计划，规则 前缀+年+月+流水码
	@Override
	public String getMatPurNextNo() throws DataAccessException{

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("table_code", "MAT_PUR_MAIN");
		map.put("prefixe", "CG");
		String[] dates = DateUtil.dateToString(new Date(), "yyyy-MM-dd").split("-");
		map.put("year", dates[0]);
		map.put("month", dates[1]);
		map.put("day", dates[2]);
		
		return getMatNextNo(map);
	}
	/**********采购管理********end**************/

	/**********订单管理********begin************/
	//订单，规则 前缀+年+月+流水码
	@Override
	public String getMatOrderNextNo() throws DataAccessException{

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("table_code", "MAT_ORDER_MAIN");
		map.put("prefixe", "DD");
		String[] dates = DateUtil.dateToString(new Date(), "yyyy-MM-dd").split("-");
		map.put("year", dates[0]);
		map.put("month", dates[1]);
		map.put("day", dates[2]);
		
		return getMatNextNo(map);
	}
	/**********订单管理********end**************/

	/**********库存管理********begin************/
	//注：代销管理单号规则与库存管理同业务一致
	//入库单，规则 业务类型简称+"-"+仓库简称+年+月+日+流水码
	@Override
	public String getMatInNextNo(String store_id, String bus_type_code) throws DataAccessException{
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("table_code", "MAT_IN_MAIN");
		map.put("store_id", store_id);
		map.put("bus_type_code", bus_type_code);
		String[] dates = DateUtil.dateToString(new Date(), "yyyy-MM-dd").split("-");
		map.put("year", dates[0]);
		map.put("month", dates[1]);
		map.put("day", dates[2]);
		
		return getMatNextNo(map);
	}
	
	//出库单，规则 业务类型简称+"-"+仓库简称+年+月+日+流水码
	@Override
	public String getMatOutNextNo(String store_id, String bus_type_code) throws DataAccessException{

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("table_code", "MAT_OUT_MAIN");
		map.put("store_id", store_id);
		map.put("bus_type_code", bus_type_code);
		String[] dates = DateUtil.dateToString(new Date(), "yyyy-MM-dd").split("-");
		map.put("year", dates[0]);
		map.put("month", dates[1]);
		map.put("day", dates[2]);
		
		return getMatNextNo(map);
	}
	
	//调拨单，规则 业务类型简称+"-"+仓库简称+年+月+日+流水码
	@Override
	public String getMatTranNextNo(String store_id, String bus_type_code) throws DataAccessException{

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("table_code", "MAT_TRAN_MAIN");
		map.put("store_id", store_id);
		map.put("bus_type_code", bus_type_code);
		String[] dates = DateUtil.dateToString(new Date(), "yyyy-MM-dd").split("-");
		map.put("year", dates[0]);
		map.put("month", dates[1]);
		map.put("day", dates[2]);
		
		return getMatNextNo(map);
	}
	
	//盘点单，规则 业务类型简称+"-"+仓库简称+年+月+日+流水码
	@Override
	public String getMatCheckNextNo(String store_id, String bus_type_code) throws DataAccessException{

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("table_code", "MAT_CHECK_MAIN");
		map.put("store_id", store_id);
		map.put("bus_type_code", bus_type_code);
		String[] dates = DateUtil.dateToString(new Date(), "yyyy-MM-dd").split("-");
		map.put("year", dates[0]);
		map.put("month", dates[1]);
		map.put("day", dates[2]);
		
		return getMatNextNo(map);
	}
	
	//科室申领，规则 前缀+年+月+5位流水码
	@Override
	public String getMatAppNextNo() throws DataAccessException{

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("table_code", "MAT_APPLY_MAIN");
		map.put("prefixe", "SQ");
		map.put("length_no", "5");
		String[] dates = DateUtil.dateToString(new Date(), "yyyy-MM-dd").split("-");
		map.put("year", dates[0]);
		map.put("month", dates[1]);
		
		return getMatNextNo(map);
	}
	/**********库存管理********end**************/
	
	/**********耐用品管理******begin************/
	//耐用品流转（库-库），规则：前缀+年+月+5位流水码
	public String getMatDuraTranStoreStoreNextNo() throws DataAccessException{

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("table_code", "MAT_DURA_STORE_STORE");
		map.put("prefixe", "LZA");
		map.put("length_no", "5");
		String[] dates = DateUtil.dateToString(new Date(), "yyyy-MM-dd").split("-");
		map.put("year", dates[0]);
		map.put("month", dates[1]);
		
		return getMatNextNo(map);
	}
	
	//耐用品流转（库-科室），规则：前缀+年+月+5位流水码
	public String getMatDuraTranStoreDeptNextNo() throws DataAccessException{

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("table_code", "MAT_DURA_STORE_DEPT");
		map.put("prefixe", "LZB");
		map.put("length_no", "5");
		String[] dates = DateUtil.dateToString(new Date(), "yyyy-MM-dd").split("-");
		map.put("year", dates[0]);
		map.put("month", dates[1]);
		
		return getMatNextNo(map);
	}
	
	//耐用品流转（科室-科室），规则：前缀+年+月+5位流水码
	public String getMatDuraTranDeptDeptNextNo() throws DataAccessException{

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("table_code", "MAT_DURA_DEPT_DEPT");
		map.put("prefixe", "LZC");
		map.put("length_no", "5");
		String[] dates = DateUtil.dateToString(new Date(), "yyyy-MM-dd").split("-");
		map.put("year", dates[0]);
		map.put("month", dates[1]);
		
		return getMatNextNo(map);
	}
	
	//耐用品库房报废，规则：前缀+年+月+5位流水码
	public String getMatDuraScrapStoreNextNo() throws DataAccessException{

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("table_code", "MAT_DURA_SCRAP_STORE");
		map.put("prefixe", "BFA");
		map.put("length_no", "5");
		String[] dates = DateUtil.dateToString(new Date(), "yyyy-MM-dd").split("-");
		map.put("year", dates[0]);
		map.put("month", dates[1]);
		
		return getMatNextNo(map);
	}
	
	//耐用品科室报废，规则：前缀+年+月+5位流水码
	public String getMatDuraScrapDeptNextNo() throws DataAccessException{

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("table_code", "MAT_DURA_SCRAP_DEPT");
		map.put("prefixe", "BFB");
		map.put("length_no", "5");
		String[] dates = DateUtil.dateToString(new Date(), "yyyy-MM-dd").split("-");
		map.put("year", dates[0]);
		map.put("month", dates[1]);
		
		return getMatNextNo(map);
	}
	
	//耐用品库房盘点，规则：前缀+年+月+5位流水码
	public String getMatDuraCheckStoreNextNo() throws DataAccessException{

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("table_code", "MAT_DURA_CHECK_STORE");
		map.put("prefixe", "PDA");
		map.put("length_no", "5");
		String[] dates = DateUtil.dateToString(new Date(), "yyyy-MM-dd").split("-");
		map.put("year", dates[0]);
		map.put("month", dates[1]);
		
		return getMatNextNo(map);
	}
	
	//耐用品科室盘点，规则：前缀+年+月+5位流水码
	public String getMatDuraCheckDeptNextNo() throws DataAccessException{

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("table_code", "MAT_DURA_CHECK_DEPT");
		map.put("prefixe", "PDB");
		map.put("length_no", "5");
		String[] dates = DateUtil.dateToString(new Date(), "yyyy-MM-dd").split("-");
		map.put("year", dates[0]);
		map.put("month", dates[1]);
		
		return getMatNextNo(map);
	}
	/**********耐用品管理******end**************/
	
	/**********付款管理********begin************/
	//期初未付款送货单，规则 前缀+年+月+流水码
	@Override
	public String getMatNoPayDeliverNextNo() throws DataAccessException{

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("table_code", "MAT_NOPAY_DELIVER");
		map.put("prefixe", "WF");
		String[] dates = DateUtil.dateToString(new Date(), "yyyy-MM-dd").split("-");
		map.put("year", dates[0]);
		map.put("month", dates[1]);
		map.put("day", dates[2]);
		
		return getMatNextNo(map);
	}
	
	//采购发票，规则 前缀+年+月+流水码
	@Override
	public String getMatBillNextNo() throws DataAccessException{

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("table_code", "MAT_BILL_MAIN");
		map.put("prefixe", "FP");
		String[] dates = DateUtil.dateToString(new Date(), "yyyy-MM-dd").split("-");
		map.put("year", dates[0]);
		map.put("month", dates[1]);
		map.put("day", dates[2]);
		
		return getMatNextNo(map);
	}
	
	//预付款单，规则：前缀+年+月+流水码
	@Override
	public String getMatPrePayNextNo() throws DataAccessException{

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("table_code", "MAT_PRE_PAY_MAIN");
		map.put("prefixe", "YF");
		String[] dates = DateUtil.dateToString(new Date(), "yyyy-MM-dd").split("-");
		map.put("year", dates[0]);
		map.put("month", dates[1]);
		map.put("day", dates[2]);
		
		return getMatNextNo(map);
	}
	
	//付款单，规则：前缀+年+月+流水码
	@Override
	public String getMatPayNextNo() throws DataAccessException{

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("table_code", "MAT_PAY_MAIN");
		map.put("prefixe", "FK");
		String[] dates = DateUtil.dateToString(new Date(), "yyyy-MM-dd").split("-");
		map.put("year", dates[0]);
		map.put("month", dates[1]);
		map.put("day", dates[2]);
		
		return getMatNextNo(map);
	}
	/**********付款管理********end**************/

	/**********安全库存调整****begin************/
	@Override
	public String getMatSafeChangeNextNo() throws DataAccessException{

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("table_code", "MAT_SAFE_CHANGE");
		map.put("prefixe", "AQ");
		String[] dates = DateUtil.dateToString(new Date(), "yyyy-MM-dd").split("-");
		map.put("year", dates[0]);
		map.put("month", dates[1]);
		map.put("day", dates[2]);
		
		return getMatNextNo(map);
	}
	/**********安全库存调整****end**************/
	
	
	//根据参数生成物流系统中下一个单号
	@Override
	public String getMatNextNo(Map<String, Object> entityMap) throws DataAccessException{
		boolean ishave_store = true;
		boolean ishave_bus = true;
		boolean change_length = true;
		int length_no = 4;  //默认流水码长度为4位
		if(entityMap.get("group_id") == null){
			entityMap.put("group_id", SessionManager.getGroupId());
		}
		if(entityMap.get("hos_id") == null){
			entityMap.put("hos_id", SessionManager.getHosId());
		}
		if(entityMap.get("copy_code") == null){
			entityMap.put("copy_code", SessionManager.getCopyCode());
		}
		//仓库ID为空则单据号不根据库房别名生成
		if(entityMap.get("store_id") == null || "".equals(entityMap.get("store_id").toString())){
			ishave_store = false;
		}
		//业务类型为空则单据号不根据业务类型简称生成
		if(entityMap.get("bus_type_code") == null || "".equals(entityMap.get("bus_type_code").toString())){
			ishave_bus = false;
		}
		if(entityMap.get("prefixe") == null){
			entityMap.put("prefixe", "");
		}
		//单号长度
		if(entityMap.get("length_no") != null && !"".equals(entityMap.get("length_no").toString())){
			length_no = Integer.valueOf(entityMap.get("length_no").toString());
			change_length = false;
		}
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("group_id", entityMap.get("group_id"));
		map.put("hos_id", entityMap.get("hos_id"));
		map.put("copy_code", entityMap.get("copy_code"));
		map.put("store_id", entityMap.get("store_id"));
		map.put("bus_type_code", entityMap.get("bus_type_code"));

		//获取仓库别名store_alias
		String store_alias = "";
		if(ishave_store){
			store_alias = matNoManageMapper.queryStoreAliasById(map);
		}
		//取业务类型简称type_flag
		String bus_type = "";
		if(ishave_bus){
			bus_type = matNoManageMapper.queryBusTypeFlagByCode(map);
		}
		map.put("store_alias", store_alias);
		map.put("bus_type", bus_type);
		map.put("table_code", entityMap.get("table_code").toString().toUpperCase());
		map.put("year", entityMap.get("year"));
		map.put("month", entityMap.get("month"));
		map.put("day", entityMap.get("day"));
		map.put("prefixe", entityMap.get("prefixe"));
		
		//校验是否按日期生成单号
		String containsDay; 
		if(entityMap.get("containsDay") != null && !"".equals(entityMap.get("containsDay"))){
			containsDay = entityMap.get("containsDay").toString();
		}else{
			containsDay = MyConfig.getSysPara("04037");
		}
		//不按日生成单据号则Map中移除日期并单据流水长度+2
		if("0".equals(containsDay)){
			map.remove("day");
			entityMap.remove("day");
			if(change_length){
				length_no = length_no + 2;
			}
		}
		
		//表里day字段默认为00（由于增加系统参数04037导致非月初更改参数会造成当月存在多条数据而生成单号报错）
		map.put("day", map.get("day") == null ? "00" : map.get("day"));
		
		//判断是否存在该业务流水码
		int flag = matNoManageMapper.queryIsExists(map);
		String max_no = "";
		if(flag == 0){
			//如不存在则流水码为1，并插入流水码表中
			max_no = "1";
			map.put("max_no", 1);
			matNoManageMapper.add(map);
		}else{
			//更新该业务流水码+1
			matNoManageMapper.updateMaxNo(map);
			//取出该业务更新后的流水码
			max_no = matNoManageMapper.queryMaxCode(map);
		}
		//补流水码前缀0
		for (int i = max_no.length() ; i < length_no; i++) {
			max_no = "0" + max_no;
		}
		//组装流水码
		String next_no = "";
		if(ishave_store){
			if(ishave_bus){
				//有业务类型的由于单据号过长所有年份取后两位
				next_no = store_alias + "-" + bus_type + entityMap.get("year").toString().substring(2, 4) + entityMap.get("month").toString() + (entityMap.get("day") == null ? "" : entityMap.get("day")) + max_no;
			}else{
				next_no = store_alias + "-" + entityMap.get("year").toString() + entityMap.get("month").toString() + (entityMap.get("day") == null ? "" : entityMap.get("day")) + max_no;
			}
		}else{
			next_no = entityMap.get("prefixe").toString() + entityMap.get("year") + entityMap.get("month").toString() + (entityMap.get("day") == null ? "" : entityMap.get("day")) + max_no;
		}
		
		return next_no;
	}
}
