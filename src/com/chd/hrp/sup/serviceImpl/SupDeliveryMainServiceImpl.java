/**
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */

package com.chd.hrp.sup.serviceImpl; 

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.MyConfig;
import com.chd.base.SessionManager;
import com.chd.base.SysPage;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.base.util.DateUtil;
import com.chd.base.util.JsonListMapUtil;
import com.chd.base.util.NumberUtil;
import com.chd.hrp.mat.dao.base.MatAffiInMapper;
import com.chd.hrp.mat.dao.base.MatCommonMapper;
import com.chd.hrp.mat.dao.base.MatInCommonMapper;
import com.chd.hrp.mat.dao.base.MatNoOtherMapper;
import com.chd.hrp.mat.dao.info.basic.MatInvMapper;
import com.chd.hrp.mat.service.base.MatCommonService;
import com.chd.hrp.sup.dao.SupDeliveryDetailMapper;
import com.chd.hrp.sup.dao.SupDeliveryMainMapper;
import com.chd.hrp.sup.entity.SupDeliveryMain;
import com.chd.hrp.sup.service.SupDeliveryMainService;
import com.github.pagehelper.PageInfo;
import com.sun.org.apache.xpath.internal.operations.And;

/** 
 * @Description: 100101 送货单主表
 * @Table: SUP_DELIVERY_MAIN
 * @Author: bell
 * @email: bell@s-chd.com
 * @Version: 1.0 
 */

@Service("supDeliveryMainService")
public class SupDeliveryMainServiceImpl implements SupDeliveryMainService {

	private static Logger logger = Logger.getLogger(SupDeliveryMainServiceImpl.class);

	// 引入DAO操作
	@Resource(name = "supDeliveryMainMapper")
	private final SupDeliveryMainMapper supDeliveryMainMapper = null;

	// 引入DAO操作
	@Resource(name = "supDeliveryDetailMapper")
	private final SupDeliveryDetailMapper supDeliveryDetailMapper = null;
	
	// 引入DAO操作
	@Resource(name = "matInCommonMapper")
	private final MatInCommonMapper matInCommonMapper = null;
	
	@Resource(name = "matCommonService")
	private final MatCommonService matCommonService = null;
	
	// 引入DAO操作
	@Resource(name = "matCommonMapper")
	private final MatCommonMapper matCommonMapper = null;
	
	@Resource(name = "matAffiInMapper")
	private final MatAffiInMapper matAffiInMapper = null;
	
	@Resource(name = "matInvMapper")
	private final MatInvMapper matInvMapper = null;
	
	@Resource(name = "matNoOtherMapper")
	private final MatNoOtherMapper matNoOtherMapper = null;

	/**
	 * @Description 添加100101 送货单主表<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String add(Map<String, Object> entityMap) throws DataAccessException {

		List<Map> detail = ChdJson.fromJsonAsList(Map.class, entityMap.get("detailData").toString());

		List<Map<String, Object>> detailList = new ArrayList<Map<String, Object>>();

		int delivery_id = supDeliveryMainMapper.queryDeliveryMainNextval();
		String delivery_no = null;
		String delivery_no_max = supDeliveryMainMapper.queryDeliveryMaxNo(entityMap);

		if (delivery_no_max.equals("2")) {
			delivery_no = "1";
			for (int i = 1; i < 6; i++) {
				delivery_no = "0" + delivery_no;
			}
			delivery_no = entityMap.get("sup_id").toString() + delivery_no;
		} else {
			delivery_no = delivery_no_max.substring(1, delivery_no_max.length());
		}

		try {
			entityMap.put("delivery_id", delivery_id);
			entityMap.put("delivery_no", delivery_no);
			supDeliveryMainMapper.add(entityMap);

			// 设置默认值
			for (Map<String, Object> detailVo : detail) {
				if (detailVo.get("inv_id") == null || detailVo.get("inv_id").toString().equals("")) {
					continue;
				}
				defaultDetailValue(detailVo);
				detailVo.put("delivery_no", delivery_no);
				detailList.add(detailVo);
			}

			supDeliveryDetailMapper.addBatch(detailList);
			return "{\"state\":\"true\",\"update_para\":\"" + delivery_id + "," + delivery_no + "," + entityMap.get("group_id").toString() + ","
			        + entityMap.get("hos_id").toString() + "," + entityMap.get("copy_code").toString() + "," + entityMap.get("acc_year").toString()
			        + "," + entityMap.get("acc_month").toString() + "\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException("{\"error\":\"添加失败\"}");

		}

	}

	/**
	 * @Description 批量添加100101 送货单主表<BR>
	 * @param entityList
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String addBatch(List<Map<String, Object>> entityList) throws DataAccessException {

		try {

			supDeliveryMainMapper.addBatch(entityList);

			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException("{\"error\":\"添加失败\"}");

		}

	}

	/**
	 * @Description 更新100101 送货单主表<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String update(Map<String, Object> entityMap) throws DataAccessException {

		try {

			int state = supDeliveryMainMapper.update(entityMap);

			String v_state = entityMap.get("state").toString();
			return "{\"msg\":\"更新成功.\",\"state\":\"true\",\"update_state\":\"" + v_state + "\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException("{\"error\":\"添加失败\"}");

		}

	}

	/**
	 * @Description 批量更新100101 送货单主表<BR>
	 * @param entityList
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String updateBatch(List<Map<String, Object>> entityList) throws DataAccessException {

		try {

			supDeliveryMainMapper.updateBatch(entityList);

			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException("{\"error\":\"添加失败\"}");

		}

	}

	/**
	 * @Description 删除100101 送货单主表<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String delete(Map<String, Object> entityMap) throws DataAccessException {

		try {

			int state = supDeliveryMainMapper.delete(entityMap);

			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException("{\"error\":\"添加失败\"}");

		}

	}

	/**
	 * @Description 批量删除100101 送货单主表<BR>
	 * @param entityList
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String deleteBatch(List<Map<String, Object>> entityList) throws DataAccessException {

		try {
			supDeliveryDetailMapper.deleteBatch(entityList);
			supDeliveryMainMapper.deleteBatch(entityList);

			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException("{\"error\":\"添加失败\"}");

		}
	}

	/**
	 * @Description 添加100101 送货单主表<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String addOrUpdate(Map<String, Object> entityMap) throws DataAccessException {
		/**
		 * 备注 先判断是否存在 存在即更新不存在则添加<br>
		 * 在判断是否存在时可根据实际情况进行修改传递的参数进行判断<br>
		 * 判断是否名称相同 判断是否 编码相同 等一系列规则
		 */
		// 判断是否存在对象100101 送货单主表
		Map<String, Object> mapVo = new HashMap<String, Object>();
		mapVo.put("group_id", entityMap.get("group_id"));
		mapVo.put("hos_id", entityMap.get("hos_id"));
		mapVo.put("copy_code", entityMap.get("copy_code"));
		// mapVo.put("acct_year", entityMap.get("acct_year"));
		// mapVo.put("acc_year", entityMap.get("acc_year"));
		// mapVo.put("acc_month", entityMap.get("acc_month"));
		mapVo.put("delivery_id", entityMap.get("delivery_id"));
		mapVo.put("delivery_no", entityMap.get("delivery_no"));

		List<SupDeliveryMain> list = (List<SupDeliveryMain>) supDeliveryMainMapper.queryExists(mapVo);

		if (list.size() > 0) {

			int state = supDeliveryMainMapper.update(entityMap);

			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}

		try {

			int state = supDeliveryMainMapper.add(entityMap);

			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException("{\"error\":\"操作失败\"}");

		}

	}

	/**
	 * @Description 添加100101 送货单主表<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String addOrUpdateBatch(Map<String, Object> entityMap) throws DataAccessException {
		/**
		 * 三个集合 添加集合 更新集合 删除集合<br>
		 */
		Map<String, Object> mapVo = new HashMap<String, Object>();
		mapVo.put("group_id", entityMap.get("group_id"));
		mapVo.put("hos_id", entityMap.get("hos_id"));
		mapVo.put("copy_code", entityMap.get("copy_code"));
		mapVo.put("delivery_id", entityMap.get("delivery_id"));
		mapVo.put("delivery_no", entityMap.get("delivery_no"));

		// 最终结果集
		List<Map<String, Object>> addList = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> updateList = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> delList = new ArrayList<Map<String, Object>>();
		// 已经存在的集合ID
		Set<String> exists = new HashSet<String>();
		Map<String, Map<String, Object>> existsMap = new HashMap<String, Map<String, Object>>();
		// 前台获取集合
		List<Map> detail = ChdJson.fromJsonAsList(Map.class, entityMap.get("detailData").toString());
		// 当前单据已有数据集合
		List<Map<String, Object>> detailExists = (List<Map<String, Object>>) supDeliveryDetailMapper.queryExistsById(mapVo);
		// 逻辑处理
		for (Map<String, Object> map : detailExists) {

			exists.add(map.get("delivery_no").toString() + map.get("inv_id") + map.get("batch_no"));

			existsMap.put(map.get("delivery_no").toString() + map.get("inv_id") + map.get("batch_no"), map);
		}
		for (Map<String, Object> m : detail) {
			if (m.get("inv_id") == null || m.get("inv_id").toString().equals("")) {
				continue;
			}
			defaultDetailValue(m);

			if (exists.contains(m.get("delivery_no").toString() + m.get("inv_id") + m.get("batch_no"))) {
				updateList.add(m);
				// 清除集合中元素
				exists.remove(m.get("delivery_no").toString() + m.get("inv_id") + m.get("batch_no"));
			} else {
				m.put("delivery_no", entityMap.get("delivery_no"));
				addList.add(m);
			}
		}
		for (String key : exists) {
			delList.add((Map<String, Object>) existsMap.get(key));
		}
		try {
			if (delList.size() > 0) {
				supDeliveryDetailMapper.deleteBatch(delList);
				supDeliveryDetailMapper.deleteRelaBatch(delList);
			}
			if (updateList.size() > 0) {
				supDeliveryDetailMapper.updateBatch(updateList);
			}
			if (addList.size() > 0) {
				supDeliveryDetailMapper.addBatch(addList);
			}

			return "{\"msg\":\"操作成功.\",\"state\":\"true\"}";
		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException("{\"error\":\"操作失败\"}");

		}

	}

	/**
	 * @Description 查询结果集100101 送货单主表<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String query(Map<String, Object> entityMap) throws DataAccessException {

		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		if (sysPage.getTotal() == -1) {

			List<SupDeliveryMain> list = (List<SupDeliveryMain>) supDeliveryMainMapper.query(entityMap);

			return ChdJson.toJson(list);

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

			List<SupDeliveryMain> list = (List<SupDeliveryMain>) supDeliveryMainMapper.query(entityMap, rowBounds);

			PageInfo page = new PageInfo(list);

			return ChdJson.toJson(list, page.getTotal());

		}

	}

	/**
	 * @Description 获取对象100101 送货单主表<BR>
	 * @param entityMap
	 * <BR>
	 *            参数为主键
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public <T> T queryByCode(Map<String, Object> entityMap) throws DataAccessException {
		return supDeliveryMainMapper.queryByCode(entityMap);
	}

	/**
	 * @Description 获取100101 送货单主表<BR>
	 * @param entityMap
	 * <BR>
	 *            参数为要检索的字段
	 * @return SupDeliveryMain
	 * @throws DataAccessException
	 */
	@Override
	public <T> T queryByUniqueness(Map<String, Object> entityMap) throws DataAccessException {
		return supDeliveryMainMapper.queryByUniqueness(entityMap);
	}

	/**
	 * @Description 获取100101 送货单主表<BR>
	 * @param entityMap
	 * <BR>
	 *            参数为要检索的字段
	 * @return List<SupDeliveryMain>
	 * @throws DataAccessException
	 */
	@Override
	public List<?> queryExists(Map<String, Object> entityMap) throws DataAccessException {
		return supDeliveryMainMapper.queryExists(entityMap);
	}

	/**
	 * 当数据为空时给默认值
	 */
	private Map<String, Object> defaultDetailValue(Map<String, Object> map) {
		if (map.get("group_id") == null) {
			map.put("group_id", SessionManager.getGroupId());
		}

		if (map.get("hos_id") == null) {
			map.put("hos_id", SessionManager.getHosId());
		}

		if (map.get("copy_code") == null) {
			map.put("copy_code", SessionManager.getCopyCode());
		}
		if (map.get("acc_year") == null) {
			map.put("acc_year", MyConfig.getCurAccYearMonth().substring(0, 4));
		}
		if (map.get("acc_month") == null) {
			map.put("acc_month", MyConfig.getCurAccYearMonth().substring(4, 6));
		}
		if (map.get("delivery_id") == null) {
			map.put("delivery_id", "");
		}
		if (map.get("delivery_no") == null) {
			map.put("delivery_no", "");
		}

		if (map.get("batch_no") == null || map.get("batch_no").toString().equals("")) {
			map.put("batch_no", "-");
		}
		if (map.get("fac_date") == null) {
			map.put("fac_date", "");
		} else {
			map.put("fac_date", DateUtil.stringToDate(map.get("fac_date").toString(), "yyyy-MM-dd"));
		}
		if (map.get("inva_date") == null) {
			map.put("inva_date", "");
		} else {
			map.put("inva_date", DateUtil.stringToDate(map.get("inva_date").toString(), "yyyy-MM-dd"));
		}
		if (map.get("disinfect_date") == null) {
			map.put("disinfect_date", "");
		} else {
			map.put("disinfect_date", DateUtil.stringToDate(map.get("disinfect_date").toString(), "yyyy-MM-dd"));
		}
		if (map.get("sn") == null) {
			map.put("sn", "-");
		}
		if (map.get("bar_code") == null) {
			map.put("bar_code", "-");
		}

		if (map.get("note") == null) {
			map.put("note", "");
		}
		if (map.get("inv_id") == null) {
			map.put("inv_id", "");
		}
		if (map.get("inv_no") == null) {
			map.put("inv_no", "");
		}

		return map;
	}

	@Override
	public String queryDetailByCode(Map<String, Object> entityMap) throws DataAccessException {
		List<?> list = supDeliveryDetailMapper.queryDetail(entityMap);
		return ChdJson.toJson(list);
	}

	@Override
	public String generateAffi(Map<String, Object> entityMap) throws DataAccessException {
		List<Map> detail = ChdJson.fromJsonAsList(Map.class, entityMap.get("detailData").toString());
		int money_para = 6;
		List<Map<String, Object>> detailList = new ArrayList<Map<String, Object>>();

		Long in_id = matAffiInMapper.queryAffiInMainSeq();
		String in_no = null;
		//String in_no_max = matCommonService.getMatNextNo(entityMap);
		
		//截取期间
		
		String[] date = DateUtil.dateToString((Date)entityMap.get("in_date"), "yyyy-MM-dd").split("-");
		entityMap.put("year", date[0]);
		entityMap.put("month", date[1]);
		entityMap.put("day", date[2]);  //用于生成单号
		
		//自动生成常备材料入库单据号
		if("自动生成".equals(entityMap.get("in_no"))){
			entityMap.put("table_code", "MAT_AFFI_IN");
			in_no = matCommonService.getMatNextNo(entityMap);
			if(in_no.indexOf("error") > 0){
				return in_no;
			}
			entityMap.put("in_no", in_no);
		}

		try {
			entityMap.put("in_id", in_id);
			if (entityMap.get("bill_date") != null ) {
				if(!entityMap.get("bill_date").toString().equals("")){
					entityMap.put("bill_date", DateUtil.stringToDate(entityMap.get("bill_date").toString(), "yyyy-MM-dd"));
				}
			}
			
			if (entityMap.get("bill_no") != null) {
				entityMap.put("bill_no", entityMap.get("bill_no").toString());
			}
			
			List<Map<String, Object>> rela = new ArrayList<Map<String, Object>>();
			
			Map<String, Map<String, Object>> detailMap = new HashMap<String, Map<String, Object>>();
			float money_sum = 0;//记录明细总金额
			// 设置默认值
			
			for (Map<String, Object> detailVo : detail) {
				Map<String, Object> relaMap = new HashMap<String, Object>();
				Long in_detail_id= matAffiInMapper.queryAffiInDetailSeq();
				if (detailVo.get("inv_id") == null || detailVo.get("inv_id").toString().equals("") || detailVo.get("amount") == null
				        || detailVo.get("amount").toString().equals("0") || detailVo.get("amount").toString().equals("")) {
					continue;
				}
				
				//生成个体码--如果系统参数04010高值材料自动生成条形码为是，则不管是否为个体码管理都要拆分生成个体码
				if("0".equals(detailVo.get("is_per_bar").toString()) && ("0".equals(String.valueOf(detailVo.get("is_highvalue"))) || "0".equals(MyConfig.getSysPara("04010")))){
					if(ChdJson.validateJSON(String.valueOf(detailVo.get("bar_code")))){
						detailVo.put("bar_code",  detailVo.get("bar_code").toString());//个体码
					}else{
						detailVo.put("bar_code", detailVo.get("sn"));//个体码--个体码默认条形码
					}
					//该条明细数据添加到List中
					//detailList.add(detailMap);
				}else{
					//根据一码一物规则自动拆分数量并生成个体码
					for(int i = 1; i <= Integer.parseInt(detailVo.get("amount").toString()); i++){
						Map<String, Object> barMap = new HashMap<String, Object>();
						barMap.putAll(detailVo);
						
						if(i > 1){
							barMap.put("detail_id", matAffiInMapper.queryAffiInDetailSeq());
						}
						//拆分数量和金额
						barMap.put("amount",  1);//数量
						barMap.put("amount_money",  String.valueOf(NumberUtil.numberToRound(Double.valueOf(detailVo.get("price").toString()), money_para)));
						/*if(detailVo.containsKey("bar_code")){
							barMap.put("bar_code",  detailVo.get("bar_code").toString());//个体码
						}*/
						
						if(entityMap.get("bar_code")!=null && !"".equals(entityMap.get("bar_code"))){
							entityMap.put("bar_code", entityMap.get("bar_code").toString());
						}
						//该条明细数据添加到List中
						//detailList.add(barMap);
					}
				}
				//平台的条形码即医院的个体码
				detailVo.put("bar_code", detailVo.get("sn"));
				
				defaultDetailValue(detailVo);
				// 判断 汇总材料数量 按照 材料id和批号进行判断
				String inv_batch = detailVo.get("inv_id").toString() + detailVo.get("batch_no")+ detailVo.get("bar_code")+ detailVo.get("serial_no");
				detailVo.put("detail_id", in_detail_id);
				detailVo.put("in_no", in_no);
				
				money_sum = money_sum + Float.parseFloat(detailVo.get("amount_money").toString());//记录总金额
				
				if (detailMap.get(inv_batch) == null) {
					detailMap.put(inv_batch, detailVo);
				} else {
					Map<String, Object> v = detailMap.get(inv_batch);
					Integer amount = Integer.valueOf(v.get("amount").toString()) + Integer.valueOf(detailVo.get("amount").toString());
					v.put("amount", amount);
					detailMap.put(inv_batch, v);
				}
				
				/*用于查询批次----begin-----*/
				Map<String,Object> batchSnMap = new HashMap<String,Object>();
				batchSnMap.put("group_id", entityMap.get("group_id"));
				batchSnMap.put("hos_id", entityMap.get("hos_id"));
				batchSnMap.put("copy_code", entityMap.get("copy_code"));
				batchSnMap.put("c_max", "batch_sn");
				batchSnMap.put("table_name", "mat_affi_in_detail");
				batchSnMap.put("c_name", "inv_id");//查询批次所用
				batchSnMap.put("c_name1", "batch_no");//查询批次所用
				//存放相同材料批号的最大批次号
				Map<String, Integer> invBatchKeySnMap = new HashMap<String, Integer>();
				String invBatchKey = "";
				/*用于查询批次----end-----*/
				/**********************自动生成批次-------begin--------*/
				invBatchKey = detailVo.get("inv_id").toString() + detailVo.get("batch_no").toString();
				//判断是否已经取出该批号的最大批次
				if(invBatchKeySnMap.get(invBatchKey) == null){
					//查询该批号最大批次
					batchSnMap.put("c_value", detailVo.get("inv_id"));//材料ID
					batchSnMap.put("c_value1", detailVo.get("batch_no"));//材料批号
					String batchSn = matCommonMapper.getMatMaxNo(batchSnMap);//最大批次
					if(batchSn == null || "".equals(batchSn) || "0".equals(batchSn)){
						detailVo.put("batch_sn",  1);//批次
					}else{
						detailVo.put("batch_sn",  Integer.parseInt(batchSn) + 1);//批次
					}
				}else{
					detailVo.put("batch_sn",  invBatchKeySnMap.get(invBatchKey) + 1);//批次
				}
				/**********************自动生成批次-------end---------*/
				
				// 添加关系
				relaMap = detailVo;
				relaMap.put("in_amount", detailVo.get("amount"));
				relaMap.put("in_id", in_id);
				relaMap.put("in_detail_id", detailVo.get("detail_id"));
				relaMap.put("is_com", 1);
				rela.add(relaMap);

			}
			// 拼装明细数据
			for (String key : detailMap.keySet()) {
				detailList.add(detailMap.get(key));
			}
			
			// 生成主表
			matAffiInMapper.addMatAffiInMain(entityMap);
						
			if(detailList.size()>0){
				// 批量插入明细数据
				matAffiInMapper.addMatAffiInDetail(detailList);
				// 批量插入关系数据
				matInCommonMapper.addRelaBatch(rela);
			}else{
				return "{\"state\":\"true\",\"state_mag\":\"1\"}";
			}
//			entityMap.put("source_money", money_sum);
//			//插入资金来源
//			matInCommonMapper.insertMatInResource(entityMap);
			
			return "{\"state\":\"true\",\"update_para\":\""  + entityMap.get("group_id").toString() + ","
			        + entityMap.get("hos_id").toString() + "," + entityMap.get("copy_code").toString() + "," + in_id + "," + in_no + ","+ entityMap.get("year").toString()
			        + "," + entityMap.get("month").toString() + "\"}";

		}catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"添加失败\"}");
		}
	}
	
	@Override
	public String generate(Map<String, Object> entityMap) throws DataAccessException {
		//金额位数
		int money_para = Integer.valueOf(MyConfig.getSysPara("04005"));
		
		List<Map> detail = ChdJson.fromJsonAsList(Map.class, entityMap.get("detailData").toString());
		//System.out.println(">>>>>>>>>>>>>"+entityMap);
		List<Map<String, Object>> detailList = new ArrayList<Map<String, Object>>();

		int in_id = supDeliveryMainMapper.queryDeliveryMainNextval();
		String in_no = null;
		//String in_no_max = matCommonService.getMatNextNo(entityMap);
		
		
		//截取期间
		
		String[] date = DateUtil.dateToString((Date)entityMap.get("in_date"), "yyyy-MM-dd").split("-");
		entityMap.put("year", date[0]);
		entityMap.put("month", date[1]);
		entityMap.put("day", date[2]);  //用于生成单号
		
		//自动生成常备材料入库单据号
		if("自动生成".equals(entityMap.get("in_no"))){
			entityMap.put("table_code", "MAT_IN_MAIN");
			in_no = matCommonService.getMatNextNo(entityMap);
			if(in_no.indexOf("error") > 0){
				return in_no;
			}
			entityMap.put("in_no", in_no);
		}

		try {
			entityMap.put("in_id", in_id);
			if (entityMap.get("bill_date") != null ) {
				if(!entityMap.get("bill_date").toString().equals("")){
					entityMap.put("bill_date", DateUtil.stringToDate(entityMap.get("bill_date").toString(), "yyyy-MM-dd"));
				}
			}
			
			if (entityMap.get("bill_no") != null) {
				
				entityMap.put("bill_no", entityMap.get("bill_no").toString());
			}
			if (detail.get(0).get("delivery_no") != null) {
				entityMap.put("brief", entityMap.get("brief").toString()+detail.get(0).get("delivery_no"));
			}
			
			

			/*用于查询个体码----begin-----*/
			Map<String,Object> barCodeMap = new HashMap<String,Object>();
			barCodeMap.put("group_id", entityMap.get("group_id"));
			barCodeMap.put("hos_id", entityMap.get("hos_id"));
			barCodeMap.put("type_code", 1);
			String bar_code = matNoOtherMapper.queryMatNoOther(barCodeMap);//获取当前个体码
			//如果不存在则插入
			if(bar_code == null || "".equals(bar_code)){
				bar_code = "000000000000";
				barCodeMap.put("max_no", bar_code);
				matNoOtherMapper.insertMatNoOther(barCodeMap);
			}
			String init_bar_code = bar_code;
			/*用于查询个体码----end-------*/
			
			List<Map<String, Object>> rela = new ArrayList<Map<String, Object>>();
			Map<String, Object> relaMap = new HashMap<String, Object>();
			Map<String, Map<String, Object>> detailMap = new HashMap<String, Map<String, Object>>();
			float money_sum = 0;//记录明细总金额
			// 设置默认值
			for (Map<String, Object> detailVo : detail) {
				String cert_code=supDeliveryMainMapper.queryCertCode(detailVo);
				detailVo.put("cert_code", cert_code);
				Long in_detail_id= matInCommonMapper.queryMatInDetailSeq();
				if (detailVo.get("inv_id") == null || detailVo.get("inv_id").toString().equals("") || detailVo.get("amount") == null
				        || detailVo.get("amount").toString().equals("0") || detailVo.get("amount").toString().equals("")) {
					continue;
				}
				detailVo.put("in_detail_id", in_detail_id);
				detailVo.put("in_no", in_no);
				defaultDetailValue(detailVo);
				
				money_sum = money_sum + Float.parseFloat(detailVo.get("amount_money").toString());//记录总金额
				
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
				/**********************自动生成批次-------begin--------*/
				invBatchKey = detailVo.get("inv_id").toString() + detailVo.get("batch_no").toString();
				//判断是否已经取出该批号的最大批次
				if(invBatchKeySnMap.get(invBatchKey) == null){
					//查询该批号最大批次
					batchSnMap.put("c_value", detailVo.get("inv_id"));//材料ID
					batchSnMap.put("c_value1", detailVo.get("batch_no"));//材料批号
					String batchSn = matCommonMapper.getMatMaxNo(batchSnMap);//最大批次
					if(batchSn == null || "".equals(batchSn) || "0".equals(batchSn)){
						detailVo.put("batch_sn",  1);//批次
					}else{
						detailVo.put("batch_sn",  Integer.parseInt(batchSn) + 1);//批次
					}
					invBatchKeySnMap.put(invBatchKey, Integer.parseInt(String.valueOf(detailVo.get("batch_sn"))));
				}else{
					detailVo.put("batch_sn",  invBatchKeySnMap.get(invBatchKey) + 1);//批次
					invBatchKeySnMap.put(invBatchKey, invBatchKeySnMap.get(invBatchKey) + 1);
				}
				/**********************自动生成批次-------end---------*/

				
				
				//如果是个体码材料,则条码根据由系统为每个材料自动生成---begin---
				//查询材料是否是个体码
				Map<String, Object> invMap = matInvMapper.queryByCode(detailVo);
				if (invMap==null) {
					continue;
				}
				if (invMap.get("is_per_bar")==null || StringUtils.isBlank(invMap.get("is_per_bar").toString()) || !"1".equals(invMap.get("is_per_bar").toString())) {
					
					//平台的条形码即医院的个体码
					detailVo.put("bar_code", detailVo.get("sn"));
					// 判断 汇总材料数量 按照 材料id和批号进行判断
					String inv_batch = detailVo.get("inv_id").toString() + detailVo.get("batch_no") + detailVo.get("bar_code");
					if (detailMap.get(inv_batch) == null) {
						detailMap.put(inv_batch, detailVo);
					} else {
						Map<String, Object> v = detailMap.get(inv_batch);
						Integer amount = Integer.valueOf(v.get("amount").toString()) + Integer.valueOf(detailVo.get("amount").toString());
						v.put("amount", amount);
						detailMap.put(inv_batch, v);
					}
					// 添加关系
					relaMap = detailVo;
					relaMap.put("in_amount", detailVo.get("amount"));
					relaMap.put("in_id", in_id);
					relaMap.put("in_detail_id", in_detail_id);
					relaMap.put("is_com", "0");
					rela.add(relaMap);
					
				}else{
					
					
					//根据一码一物规则自动拆分数量并生成个体码
					for(int i = 1; i <= Double.parseDouble(detailVo.get("amount").toString()) ; i++){
						//拆分组装一条记录
						//添加批次
						System.out.println(invBatchKeySnMap+">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
						detailVo.put("batch_sn",  invBatchKeySnMap.get(invBatchKey) +i-1);//个体码,让每个材料的批次都不同
						Map<String, Object> barMap = new HashMap<String, Object>();
						barMap.putAll(detailVo);
						//系统自动生成个体码
						bar_code = matCommonService.getNextBar_code(bar_code);
						if(i > 1){
							barMap.put("in_detail_id", matInCommonMapper.queryMatInDetailSeq());
						}
						//拆分数量和金额
						barMap.put("amount",  1);//数量
						if(detailVo.get("num_exchange") != null){
							barMap.put("num",  Float.parseFloat(barMap.get("amount").toString())/Float.parseFloat(detailVo.get("num_exchange").toString()));//包装件数
						}
						if(detailVo.get("num") != null){
							barMap.put("pack_price",  Float.parseFloat(detailVo.get("num").toString())*Float.parseFloat(detailVo.get("price").toString()));//包装件数
						}
						barMap.put("amount_money",  String.valueOf(NumberUtil.numberToRound(Double.valueOf(detailVo.get("price").toString()), money_para)));
						barMap.put("bar_code",  bar_code);//个体码
						// 单独成为 汇总材料一条记录 数量为1 按照 材料id和批号进行判断
						String inv_batch = barMap.get("inv_id").toString() + barMap.get("batch_no") + barMap.get("bar_code");
						detailMap.put(inv_batch, barMap);
						// 添加关系
						relaMap = barMap;
						relaMap.put("in_amount", 1);
						relaMap.put("in_id", in_id);
						relaMap.put("is_com", "0");
						rela.add(relaMap);
					}
				}
				//如果是个体码材料,则条码根据由系统为每个材料自动生成---end---

			}
			// 拼装明细数据
			for (String key : detailMap.keySet()) {
				detailList.add(detailMap.get(key));
			}
			

			//判断发票状态
			if (entityMap.get("bill_no") != null && !"".equals(entityMap.get("bill_no").toString())) {
				entityMap.put("bill_state", 1); //货票同到
			}else{
				entityMap.put("bill_state", 0); //货到票未到
			}
			
			// 生成主表
			entityMap.put("amount_money", money_sum);
			matInCommonMapper.addMatInMain(entityMap);
						
			if(detailList.size()>0){
				//更新个体码
				if(!init_bar_code.equals(bar_code)){
					barCodeMap.put("max_no", bar_code);
					matNoOtherMapper.updateMatNoOther(barCodeMap);
				}
				// 批量插入明细数据
				matInCommonMapper.addMatInDetailBatch(detailList);
				// 批量插入关系数据
				matInCommonMapper.addRelaBatch(rela);
			}else{
				return "{\"state\":\"true\",\"state_mag\":\"1\"}";
			}
			entityMap.put("source_money", money_sum);
			//插入资金来源
			matInCommonMapper.insertMatInResource(entityMap);
			
			return "{\"state\":\"true\",\"update_para\":\""  + entityMap.get("group_id").toString() + ","
			        + entityMap.get("hos_id").toString() + "," + entityMap.get("copy_code").toString() + "," + in_id + "," + in_no + ","+ entityMap.get("year").toString()
			        + "," + entityMap.get("month").toString() + "\"}";

		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);
			
			throw new SysException("{\"error\":\"添加失败\"}");
		}
	}
	
	//按送货单明细生成入库
	@Override
	public String generateInDetail(Map<String, Object> entityMap) throws DataAccessException {
		List<Map> detail = ChdJson.fromJsonAsList(Map.class, entityMap.get("detailData").toString());
	//System.out.println("IN=============="+detail);
		List<Map<String, Object>> detailList = new ArrayList<Map<String, Object>>();

		int in_id = supDeliveryMainMapper.queryDeliveryMainNextval();
		String in_no = null;
		//String in_no_max = matCommonService.getMatNextNo(entityMap);
		
		//截取期间
		
		String[] date = DateUtil.dateToString((Date)entityMap.get("in_date"), "yyyy-MM-dd").split("-");
		entityMap.put("year", date[0]);
		entityMap.put("month", date[1]);
		entityMap.put("day", date[2]);  //用于生成单号
		
		//自动生成常备材料入库单据号
		if("自动生成".equals(entityMap.get("in_no"))){
			entityMap.put("table_code", "MAT_IN_MAIN");
			in_no = matCommonService.getMatNextNo(entityMap);
			if(in_no.indexOf("error") > 0){
				return in_no;
			}
			entityMap.put("in_no", in_no);
		}

		try {
			entityMap.put("in_id", in_id);
			if (entityMap.get("bill_date") != null ) {
				if(!entityMap.get("bill_date").toString().equals("")){
					entityMap.put("bill_date", DateUtil.stringToDate(entityMap.get("bill_date").toString(), "yyyy-MM-dd"));
				}
			}
			
			if (entityMap.get("bill_no") != null) {
				
				entityMap.put("bill_no", entityMap.get("bill_no").toString());
			}

			List<Map<String, Object>> rela = new ArrayList<Map<String, Object>>();
			Map<String, Object> relaMap = new HashMap<String, Object>();
			Map<String, Map<String, Object>> detailMap = new HashMap<String, Map<String, Object>>();
			float money_sum = 0;//记录明细总金额
			// 设置默认值
			for (Map<String, Object> detailVo : detail) {
				Long in_detail_id= matInCommonMapper.queryMatInDetailSeq();
				if (detailVo.get("inv_id") == null || detailVo.get("inv_id").toString().equals("") || detailVo.get("amount") == null
				        || detailVo.get("amount").toString().equals("0") || detailVo.get("amount").toString().equals("")) {
					continue;
				}
				//平台的条形码即医院的个体码
				detailVo.put("bar_code", detailVo.get("sn"));

				defaultDetailValue(detailVo);
				// 判断 汇总材料数量 按照 材料id和批号进行判断
				String inv_batch = detailVo.get("inv_id").toString() + detailVo.get("batch_no") + detailVo.get("bar_code") + detailVo.get("serial_no");
				detailVo.put("in_detail_id", in_detail_id);
				detailVo.put("in_no", in_no);
				
				money_sum = money_sum + Float.parseFloat(detailVo.get("amount_money").toString());//记录总金额
				
				if (detailMap.get(inv_batch) == null) {
					detailMap.put(inv_batch, detailVo);
				} else {

					Map<String, Object> v = detailMap.get(inv_batch);

					Integer amount = Integer.valueOf(v.get("amount").toString()) + Integer.valueOf(detailVo.get("amount").toString());

					v.put("amount", amount);

					detailMap.put(inv_batch, v);
				}
				
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
				/**********************自动生成批次-------begin--------*/
				invBatchKey = detailVo.get("inv_id").toString() + detailVo.get("batch_no").toString();
				//判断是否已经取出该批号的最大批次
				if(invBatchKeySnMap.get(invBatchKey) == null){
					//查询该批号最大批次
					batchSnMap.put("c_value", detailVo.get("inv_id"));//材料ID
					batchSnMap.put("c_value1", detailVo.get("batch_no"));//材料批号
					String batchSn = matCommonMapper.getMatMaxNo(batchSnMap);//最大批次
					if(batchSn == null || "".equals(batchSn) || "0".equals(batchSn)){
						detailVo.put("batch_sn",  1);//批次
					}else{
						detailVo.put("batch_sn",  Integer.parseInt(batchSn) + 1);//批次
					}
				}else{
					detailVo.put("batch_sn",  invBatchKeySnMap.get(invBatchKey) + 1);//批次
				}
				/**********************自动生成批次-------end---------*/
				
				// 添加关系
				relaMap = detailVo;
				relaMap.put("in_amount", detailVo.get("amount"));
				relaMap.put("in_id", in_id);
				relaMap.put("in_detail_id", in_detail_id);
				relaMap.put("is_com", 0);
				rela.add(relaMap);
//System.out.println("rela============"+rela);
			}
			// 拼装明细数据
			for (String key : detailMap.keySet()) {
				detailList.add(detailMap.get(key));
			}
			// 生成主表
			matInCommonMapper.addMatInMain(entityMap);
						
			if(detailList.size()>0){
				// 批量插入明细数据
				matInCommonMapper.addMatInDetailBatch(detailList);
				// 批量插入关系数据
				matInCommonMapper.addRelaBatch(rela);
				
			}else{
				return "{\"state\":\"true\",\"state_mag\":\"1\"}";
			}
			
			entityMap.put("source_money", money_sum);
			//插入资金来源
			matInCommonMapper.insertMatInResource(entityMap);
			
			return "{\"state\":\"true\",\"update_para\":\""  + entityMap.get("group_id").toString() + ","
			        + entityMap.get("hos_id").toString() + "," + entityMap.get("copy_code").toString() + "," + in_id + "," + in_no + ","+ entityMap.get("year").toString()
			        + "," + entityMap.get("month").toString() + "\"}";

		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);
			
			throw new SysException("{\"error\":\"添加失败\"}");
		}
	}

	@Override
	public String updateDeliveryDetailGenerate(Map<String, Object> entityMap) throws DataAccessException {

		String in_id = entityMap.get("in_id").toString();
		String in_no = entityMap.get("in_no").toString();
		List<Map> detail = ChdJson.fromJsonAsList(Map.class, entityMap.get("detailData").toString());
		// 用于更新明细数量
		Map<String, Object> updateMap = new HashMap<String, Object>();
		updateMap.put("in_id", in_id);
		updateMap.put("in_no", in_no);
		// 用于根据材料ID和送货单编号删除对应关系数据
		Map<String, Object> deleteMap = new HashMap<String, Object>();
		deleteMap.put("in_id", entityMap.get("in_id"));
		deleteMap.put("in_no", entityMap.get("in_no"));
		deleteMap.put("inv_id", entityMap.get("inv_id"));
		deleteMap.put("group_id", entityMap.get("group_id"));
		deleteMap.put("hos_id", entityMap.get("hos_id"));
		deleteMap.put("copy_code", entityMap.get("copy_code"));

		

		List<Map<String, Object>> detailList = new ArrayList<Map<String, Object>>();

		List<Map<String, Object>> addList = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> updateList = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> deleteList = new ArrayList<Map<String, Object>>();
		
		
		// 已经存在的集合ID
		Set<String> exists = new HashSet<String>();
		Map<String, Map<String, Object>> existsMap = new HashMap<String, Map<String, Object>>();

		try {
			List<Map<String, Object>> addRela = new ArrayList<Map<String, Object>>();
			List<Map<String, Object>> updateRela = new ArrayList<Map<String, Object>>();
			List<Map<String, Object>> deleRela = new ArrayList<Map<String, Object>>();
			Map<String, Object> relaMap = new HashMap<String, Object>();
			Map<String, Map<String, Object>> detailMap = new HashMap<String, Map<String, Object>>();
			// 设置默认值
			for (Map<String, Object> detailVo : detail) {
				if (detailVo.get("inv_id") == null || detailVo.get("inv_id").toString().equals("") || detailVo.get("amount") == null
				        || detailVo.get("amount").toString().equals("0") || detailVo.get("amount").toString().equals("")) {
					continue;
				}
				//平台的条形码即医院的个体码
				detailVo.put("bar_code", detailVo.get("sn"));

				defaultDetailValue(detailVo);
				// 判断 汇总材料数量 按照 材料id进行判断
				String inv_batch = detailVo.get("inv_id").toString();

				if (detailMap.get(inv_batch) == null) {
					Map<String, Object> m = updateMap;
					m.put("inv_id", detailVo.get("inv_id"));
					m.put("amount", detailVo.get("amount"));
					detailMap.put(inv_batch, m);
				} else {

					Map<String, Object> v = detailMap.get(inv_batch);

					Integer amount = Integer.valueOf(v.get("amount").toString()) + Integer.valueOf(detailVo.get("amount").toString());

					v.put("amount", amount);

					detailMap.put(inv_batch, v);
				}
				detailVo.put("in_no", in_no);
				// 添加关系
				relaMap = detailVo;
				relaMap.put("in_amount", detailVo.get("amount"));
				relaMap.put("in_id", in_id);
				addRela.add(relaMap);

			}
			
			deleRela.add(deleteMap);
			
			// 拼装明细数据
			for (String key : detailMap.keySet()) {
				updateList.add(detailMap.get(key));
			}
			
			if(deleRela.size()>0){
				// 批量删除关系数据
				supDeliveryDetailMapper.deleteRelaBatch(deleRela);
			}
			if(addRela.size()>0){
				// 批量插入关系数据
				supDeliveryDetailMapper.addRelaBatch(addRela);
			}
			
			if(updateList.size()>0){
				// 批量更新明细数据
				matInCommonMapper.updateMatInDetail(updateList);
			}

			
			return "{\"state\":\"true\",\"update_para\":\"" + entityMap.get("group_id").toString() + ","
			        + entityMap.get("hos_id").toString() + "," + entityMap.get("copy_code").toString() + ","+ in_id + "," + in_no + ","  + entityMap.get("year").toString()
			        + "," + entityMap.get("month").toString() + "\"}";

		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"添加失败\"}");
		}
	}

	@Override
	public List<String> queryDeliveryOrderRelaExists(Map<String, Object> entityMap) throws DataAccessException {
		return supDeliveryMainMapper.queryDeliveryOrderRelaExists(entityMap);
	}
	/**
	 * 更新订单状态
	 */
	@Override
	public void updateMatOrderState(Map<String, Object> entityMap) throws DataAccessException {
		
		List<Map<String,Object>> orderList  = JsonListMapUtil.toListMapLower(supDeliveryMainMapper.queryDeliveryOrderIds(entityMap));
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		
		
		//keyMap中存放<主键, 具体信息的map>用于判断
		Map<String,Map<String,Object>> keyMap = new HashMap<String,Map<String,Object>>();
		
		if(orderList.size() > 0 ){
			for(Map<String,Object> map : orderList){
				String key = map.get("group_id").toString()+"-"+map.get("hos_id").toString()+"-"+map.get("copy_code").toString()
							+"-"+map.get("order_id").toString()+"-"+map.get("sum_order_amount").toString();
				
				if(!keyMap.containsKey(key)){
					keyMap.put(key, map);
				}
			}
			
			for (String key : keyMap.keySet()) {
				Map<String,Object> orderMap = new HashMap<String,Object>();//需要改变的MAP
				int sum_amount = 0;//用于计算入库的总数量
				Map<String,Object> mapK = keyMap.get(key);
				int sum_order_amount = Integer.parseInt(mapK.get("sum_order_amount").toString());//订单总的数量
				for(Map<String,Object> mapO : orderList){
					String keyO = mapO.get("group_id").toString()+"-"+mapO.get("hos_id").toString()+"-"+mapO.get("copy_code").toString()
							+"-"+mapO.get("order_id").toString()+"-"+mapO.get("sum_order_amount").toString();
					if(key.equals(keyO)){
						if(Integer.parseInt(mapO.get("order_amount").toString()) <= Integer.parseInt(mapO.get("in_amount").toString())){
							sum_amount += Integer.parseInt(mapO.get("in_amount").toString());
						}
					}
				}
				
				if(sum_order_amount <= sum_amount){
					orderMap.put("group_id", mapK.get("group_id"));
					orderMap.put("hos_id", mapK.get("hos_id"));
					orderMap.put("copy_code", mapK.get("copy_code"));
					orderMap.put("order_id", mapK.get("order_id"));
					orderMap.put("state", "5");
					list.add(orderMap);
				}
			}
		}
		if(list.size() > 0){
			supDeliveryMainMapper.updateDeliveryOrderStates(list);
		}
	}

	@Override
	public void updateMatAffiOrderState(Map<String, Object> entityMap) throws DataAccessException {
		List<Map<String,Object>> orderList  = JsonListMapUtil.toListMapLower(supDeliveryMainMapper.queryDeliveryAffiOrderIds(entityMap));
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		
		
		//keyMap中存放<主键, 具体信息的map>用于判断
		Map<String,Map<String,Object>> keyMap = new HashMap<String,Map<String,Object>>();
		
		if(orderList.size() > 0 ){
			for(Map<String,Object> map : orderList){
				String key = map.get("group_id").toString()+"-"+map.get("hos_id").toString()+"-"+map.get("copy_code").toString()
							+"-"+map.get("order_id").toString()+"-"+map.get("sum_order_amount").toString();
				
				if(!keyMap.containsKey(key)){
					keyMap.put(key, map);
				}
			}
			
			for (String key : keyMap.keySet()) {
				Map<String,Object> orderMap = new HashMap<String,Object>();//需要改变的MAP
				int sum_amount = 0;//用于计算入库的总数量
				Map<String,Object> mapK = keyMap.get(key);
				int sum_order_amount = Integer.parseInt(mapK.get("sum_order_amount").toString());//订单总的数量
				for(Map<String,Object> mapO : orderList){
					String keyO = mapO.get("group_id").toString()+"-"+mapO.get("hos_id").toString()+"-"+mapO.get("copy_code").toString()
							+"-"+mapO.get("order_id").toString()+"-"+mapO.get("sum_order_amount").toString();
					if(key.equals(keyO)){
						if(Integer.parseInt(mapO.get("order_amount").toString()) <= Integer.parseInt(mapO.get("in_amount").toString())){
							sum_amount += Integer.parseInt(mapO.get("in_amount").toString());
						}
					}
				}
				
				if(sum_order_amount <= sum_amount){
					orderMap.put("group_id", mapK.get("group_id"));
					orderMap.put("hos_id", mapK.get("hos_id"));
					orderMap.put("copy_code", mapK.get("copy_code"));
					orderMap.put("order_id", mapK.get("order_id"));
					orderMap.put("state", "5");
					list.add(orderMap);
				}
			}
		}
		if(list.size() > 0){
			supDeliveryMainMapper.updateDeliveryOrderStates(list);
		}
		
	}
}
