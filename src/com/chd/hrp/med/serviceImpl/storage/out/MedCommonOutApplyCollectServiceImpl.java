/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.med.serviceImpl.storage.out;

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
import com.chd.base.util.ChdJson;
import com.chd.base.util.DateUtil;
import com.chd.base.util.JsonListMapUtil;
import com.chd.base.util.NumberUtil;
import com.chd.hrp.med.dao.base.MedApplyOutRelaMapper;
import com.chd.hrp.med.dao.base.MedApplyPurRelaMapper;
import com.chd.hrp.med.dao.base.MedCommonMapper;
import com.chd.hrp.med.dao.base.MedNoManageMapper;
import com.chd.hrp.med.dao.storage.out.MedCommonOutApplyCheckMapper;
import com.chd.hrp.med.dao.storage.out.MedCommonOutApplyCollectMapper;
import com.chd.hrp.med.dao.storage.out.MedCommonOutApplyMapper;
import com.chd.hrp.med.entity.MedApplyMain;
import com.chd.hrp.med.service.base.MedCommonService;
import com.chd.hrp.med.service.storage.out.MedCommonOutApplyCollectService;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Description:
 * 科室申请单主表
 * @Table:
 * MED_APPLY_MAIN
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
@Service("medCommonOutApplyCollectService")
public class MedCommonOutApplyCollectServiceImpl implements MedCommonOutApplyCollectService {

	private static Logger logger = Logger.getLogger(MedCommonOutApplyCollectServiceImpl.class);
	//引入DAO操作
	@Resource(name = "medCommonOutApplyCollectMapper")
	private final MedCommonOutApplyCollectMapper medCommonOutApplyCollectMapper = null;
	@Resource(name = "medCommonService")
	private final MedCommonService medCommonService = null;
	@Resource(name = "medNoManageMapper")
	private final MedNoManageMapper medNoManageMapper = null;
	@Resource(name = "medCommonOutApplyMapper")
	private final MedCommonOutApplyMapper medCommonOutApplyMapper = null;
	@Resource(name = "medCommonOutApplyCheckMapper")
	private final MedCommonOutApplyCheckMapper medCommonOutApplyCheckMapper = null;
	@Resource(name = "medApplyOutRelaMapper")
	private final MedApplyOutRelaMapper medApplyOutRelaMapper = null;
	@Resource(name = "medApplyPurRelaMapper")
	private final MedApplyPurRelaMapper medApplyPurRelaMapper = null;
	@Resource(name = "medCommonMapper")
	private final MedCommonMapper medCommonMapper = null;
	
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
			List<Map<String, Object>> list = (List<Map<String, Object>>) medCommonOutApplyCollectMapper.query(entityMap);
			
			return ChdJson.toJson(list);
		}else{
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			List<Map<String, Object>> list = (List<Map<String, Object>>) medCommonOutApplyCollectMapper.query(entityMap, rowBounds);
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJsonLower(list, page.getTotal());
		}
	}
	
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
			//return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addMedCommonOutApplyCollect\"}";
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
			//return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addBatchMedCommonOutApplyCollect\"}";
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
			//return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addMedCommonOutApplyCollect\"}";
		}
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
			//return "{\"error\":\"更新失败 数据库异常 请联系管理员! 方法 updateBatchMedCommonOutApplyCollect\"}";
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
			//return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 deleteMedCommonOutApplyCollect\"}";
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
			//return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 deleteBatchMedCommonOutApplyCollect\"}";
		}	
	}

	@Override
	public String addByBack(List<Map<String, Object>> entityList) throws DataAccessException {
		
		try {	
			//获取单据主表信息（申请科室，相应库房等）
			List<Map<String, Object>> mainList = JsonListMapUtil.toListMapLower(medCommonOutApplyCollectMapper.queryMainByBack(entityList));
			if(mainList.size() != 1){
				return "{\"error\":\"请选择同一科室、库房的药品\"}";
			}
			Map<String, Object> mainMap = mainList.get(0);
			String date = DateUtil.dateToString(new Date(), "yyyy-MM-dd");
			//制单人
			mainMap.put("maker", SessionManager.getUserId());
			mainMap.put("app_date", new Date());
			//截取期间
			mainMap.put("year", date.substring(0, 4));
			mainMap.put("month", date.substring(5, 7));
			mainMap.put("day", date.substring(8, 10));
			//退回原因
			mainMap.put("back_reason", entityList.get(0).get("back_reason"));
			//状态
			mainMap.put("state", 1);
			/******************生成单号********begin***********************/
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("group_id", mainMap.get("group_id"));
			map.put("hos_id", mainMap.get("hos_id"));
			map.put("copy_code", mainMap.get("copy_code"));
			map.put("table_code", "MED_APPLY_MAIN");
			map.put("year", mainMap.get("year"));
			map.put("month", mainMap.get("month"));
			map.put("prefixe", "SQ");
			map.put("store_alias", "");
			map.put("bus_type", "");
			//判断是否存在该业务流水码
			int flag = medNoManageMapper.queryIsExists(map);
			String max_no = "";
			if(flag == 0){
				//如不存在则流水码为1，并插入流水码表中
				max_no = "1";
				map.put("max_no", 1);
				medNoManageMapper.add(map);
			}else{
				//更新该业务流水码+1
				medNoManageMapper.updateMaxNo(map);
				//取出该业务更新后的流水码
				max_no = medNoManageMapper.queryMaxCode(map);
			}
			//补流水码前缀0
			for (int i = max_no.length() ; i < 5; i++) {
				max_no = "0" + max_no;
			}
			//组装流水码
			String apply_no = "SQ-" + map.get("year").toString() + map.get("month").toString() + max_no;
			
			mainMap.put("apply_no", apply_no);
			/******************生成单号********end*************************/
			//取出主表ID（自增序列）
			mainMap.put("apply_id", medCommonOutApplyMapper.queryMedApplyMainSeq());
			//获取退回的药品信息
			List<Map<String, Object>> detailList = JsonListMapUtil.toListMapLower(medCommonOutApplyCollectMapper.queryDetailByBack(entityList));
			
			if(detailList.size() == 0){
				return "{\"error\":\"请选择未完成的药品\"}";
			}
			
			for(Map<String, Object> detailMap : detailList){
				detailMap.put("apply_id", mainMap.get("apply_id"));
				detailMap.put("detail_id", medCommonOutApplyMapper.queryMedApplyDetailSeq());
				//退回数量=申请数量-处理数量
				double amount = NumberUtil.sub(Double.valueOf(detailMap.get("app_amount").toString()), Double.valueOf(detailMap.get("rela_amount").toString()));
				detailMap.put("app_amount", amount);
				detailMap.put("note",  null);//备注
			}
			
			//插入主表
			medCommonOutApplyMapper.addMedApplyMain(mainMap);
			//插入明细表
			medCommonOutApplyMapper.addMedApplyDetail(detailList);
			
			//修改明细状态为退回
			medCommonOutApplyCollectMapper.updateDetailByBack(entityList);
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);

			throw new SysException("{\"error\":\"操作失败\"}");
			//return "{\"error\":\"操作失败 数据库异常 请联系管理员! 方法 updateBackMedCommonOutApplyCollectBatch\"}";
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
		MedApplyMain MedApplyMain = queryByCode(entityMap);

		if (MedApplyMain != null) {

			int state = medCommonOutApplyCollectMapper.update(entityMap);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		
		try {
			
			int state = medCommonOutApplyCollectMapper.add(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addOrUpdateMedCommonOutApplyCollect\"}";

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
		return medCommonOutApplyCollectMapper.queryByCode(entityMap);
	}
	
	/**
	 * @Description 
	 * 获取科室申请<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return MedCommonOutApply
	 * @throws DataAccessException
	*/
	@Override
	public  <T>T queryByUniqueness(Map<String,Object> entityMap)throws DataAccessException{
		return medCommonOutApplyCollectMapper.queryByUniqueness(entityMap);
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
	public List<Map<String, Object>> queryOutMain(Map<String, Object> entityMap) throws DataAccessException {
		
		return JsonListMapUtil.toListMapLower(medCommonOutApplyCollectMapper.queryOutMain(entityMap));
	}
	/**
	 * 组装生成出库单明细表数据
	 */
	@Override
	public String queryOutDetail(Map<String, Object> entityMap) throws DataAccessException {
		try{
			//明细中药品信息
			List<Map<String, Object>> invList = JsonListMapUtil.toListMapLower(medCommonOutApplyCollectMapper.querySelectDetailForOut(entityMap));
			return medCommonService.getInvJsonByInvList(invList);
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);

			return "{\"error\":\"生成失败 数据库异常 请联系管理员! 方法 MedCommonOutApply--queryOutDetail\"}";
		}
	}

	/**
	 * 组装生成代销出库单明细表数据
	 */
	@Override
	public String queryAffiOutDetail(Map<String, Object> entityMap) throws DataAccessException {
		try{
			//明细药品信息
			List<Map<String, Object>> invList = JsonListMapUtil.toListMapLower(medCommonOutApplyCollectMapper.querySelectDetailForOut(entityMap));
			
			return medCommonService.getAffiInvJsonByAffiInvList(invList);
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);

			return "{\"error\":\"生成失败 数据库异常 请联系管理员! 方法 MedCommonOutApply--queryOutDetail\"}";
		}
	}
	
	/**
	 * 查询管理部门对应的仓库信息<BR> 
	*/
	@Override
	public String queryStoreByDept(Map<String,Object> entityMap)throws DataAccessException{
		
		String store = medCommonOutApplyCollectMapper.queryStoreByDept(entityMap);
		if(store == null || "".equals(store)){
			//20170606 hsy由于即墨存在二级库下管多个科室所有这个判断不能直接
			return "{\"error1\":\"该科室无对应的仓库请设置！\",\"state\":\"false\"}";
		}else{
			
			return "{\"state\":\"true\"}";
		}
	}
	
	/**
	 * 组装生成调拨单主表数据
	 */
	@Override
	public List<Map<String, Object>> queryTranMain(Map<String, Object> entityMap) throws DataAccessException {
		
		return JsonListMapUtil.toListMapLower(medCommonOutApplyCollectMapper.queryTranMain(entityMap));
	}
	/**
	 * 组装生成调拨单明细表数据
	 */
	@Override
	public String queryTranDetail(Map<String, Object> entityMap) throws DataAccessException {
		try{
			//药品基础信息
			List<Map<String, Object>> invList = JsonListMapUtil.toListMapLower(medCommonOutApplyCollectMapper.querySelectDetailForOut(entityMap));

			return medCommonService.getInvJsonByInvList(invList);
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);

			return "{\"error\":\"生成失败 数据库异常 请联系管理员! 方法 MedCommonOutApply--queryOutDetail\"}";
		}
	}

	/**
	 * 组装生成代销调拨单明细表数据
	 */
	@Override
	public String queryAffiTranDetail(Map<String, Object> entityMap) throws DataAccessException {
		try{
			//药品基础信息
			List<Map<String, Object>> invList = JsonListMapUtil.toListMapLower(medCommonOutApplyCollectMapper.querySelectDetailForOut(entityMap));

			return medCommonService.getAffiInvJsonByAffiInvList(invList);
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);

			return "{\"error\":\"生成失败 数据库异常 请联系管理员! 方法 MedCommonOutApply--queryOutDetail\"}";
		}
	}
	
	/**
	 * 组装生成科室需求计划主表数据
	 */
	@Override
	public List<Map<String, Object>> queryReqMain(Map<String, Object> entityMap) throws DataAccessException {
		return JsonListMapUtil.toListMapLower(medCommonOutApplyCollectMapper.queryReqMain(entityMap));
	}
	/**
	 * 组装生成科室需求计划明细表数据
	 */
	@Override
	public String queryReqDetail(Map<String, Object> entityMap) throws DataAccessException {
		try{
			
			return ChdJson.toJson(JsonListMapUtil.toListMapLower(medCommonOutApplyCollectMapper.queryReqDetail(entityMap)));
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);

			return "{\"error\":\"生成失败 数据库异常 请联系管理员! 方法 MedCommonOutApply--queryOutDetail\"}";
		}
	}

	/**
	 * 汇总生成科室需求计划
	 */
	@Override
	public String addMedReqByApplyCollect(Map<String, Object> entityMap) throws DataAccessException {
		try{
			/**-------------常量-------begin---------*/
			Date make_date = new Date();
			String[] dates =  DateUtil.dateToString(make_date, "yyyy-MM-dd").split("-");
			String year = dates[0];
			String month = dates[1];
			String day = dates[2];
			String user_id = SessionManager.getUserId();
			String group_id = entityMap.get("group_id").toString();
			String hos_id = entityMap.get("hos_id").toString();
			String copy_code = entityMap.get("copy_code").toString();
			/**-------------常量-------end-----------*/
			
			//校验所选药品是否含已停用的药品
			String stopInvs = medCommonOutApplyCollectMapper.existsContainsInvIsStop(entityMap);
			if(stopInvs != null && !"".equals(stopInvs)){
				return "{\"error\":\"以下药品已停用不能生成科室需求计划：<br/>"+stopInvs.toString()+"\"}";
			}
			
			//存放主表信息
			List<Map<String, Object>> reqMainList = new ArrayList<Map<String,Object>>();
			//存放明细信息
			List<Map<String, Object>> reqDetailList = new ArrayList<Map<String,Object>>();
			//存放对应关系
			List<Map<String, Object>> applyRelaList= new ArrayList<Map<String,Object>>();
			
			//记录主表信息
			Map<String, Map<String, Object>> reqMain = new HashMap<String, Map<String, Object>>();
			String reqMainKey = "";  
			//记录明细表信息
			Map<String, Map<String, Object>> reqDetail = new HashMap<String, Map<String, Object>>();
			String reqDetailKey = "";
			
			Long req_id;
			String req_code;
			
			//查询所选主从表数据
			List<Map<String, Object>> reqList = JsonListMapUtil.toListMapLower(medCommonOutApplyCollectMapper.queryReqByCollect(entityMap));
			
			for(Map<String, Object> map : reqList){
				//用于保存的主表数据
				Map<String, Object> mainMap;
				
				reqMainKey = map.get("dept_id").toString()+map.get("store_id").toString();
				reqDetailKey = reqMainKey + map.get("inv_id").toString();
				
				if(reqMain.containsKey(reqMainKey)){
					mainMap = reqMain.get(reqMainKey);
					req_id = Long.parseLong(mainMap.get("req_id").toString());
					req_code = mainMap.get("req_code").toString();
				}else{
					mainMap = new HashMap<String, Object>();
					mainMap.put("group_id", group_id);
					mainMap.put("hos_id", hos_id);
					mainMap.put("copy_code", copy_code);
					mainMap.put("make_date", make_date);
					mainMap.put("rdate", make_date);
					mainMap.put("maker", user_id);
					mainMap.put("dept_id", map.get("dept_id"));
					mainMap.put("dept_no", map.get("dept_no"));
					mainMap.put("store_id", map.get("store_id"));
					mainMap.put("store_no", map.get("store_no"));
					mainMap.put("brif", "科室申领汇总生成");
					mainMap.put("other_inv", null);
					mainMap.put("state", 1);
					/**自动生成需求单号单据号-----------------------------begin---------------------------*/
					Map<String, Object> codeMap = new HashMap<String, Object>();
					codeMap.put("group_id", group_id);
					codeMap.put("hos_id", hos_id);
					codeMap.put("copy_code", copy_code);
					codeMap.put("table_code", "MED_REQUIRE_MAIN");
					codeMap.put("year", year);
					codeMap.put("month", month);
					codeMap.put("day", day);
					codeMap.put("prefixe", "XQ");
					codeMap.put("store_alias", "");
					codeMap.put("bus_type", "");
					//判断是否存在该业务流水码
					int flag = medNoManageMapper.queryIsExists(codeMap);
					String max_no = "";
					if(flag == 0){
						//如不存在则流水码为1，并插入流水码表中
						max_no = "1";
						codeMap.put("max_no", 1);
						medNoManageMapper.add(codeMap);
					}else{
						//更新该业务流水码+1
						medNoManageMapper.updateMaxNo(codeMap);
						//取出该业务更新后的流水码
						max_no = medNoManageMapper.queryMaxCode(codeMap);
					}
					//补流水码前缀0
					for (int i = max_no.length() ; i < 4; i++) {
						max_no = "0" + max_no;
					}
					
					req_code = "XQ" + year.substring(2, 4) + month + day + max_no;
					mainMap.put("req_code", req_code);
					/**自动生成需求单号单据号-----------------------------end-----------------------------*/
					//获得自增ID
					req_id = medCommonOutApplyCheckMapper.queryMedRequireMainSeqNext();
					mainMap.put("req_id", String.valueOf(req_id));
					
					//存放到reqMain中
					reqMain.put(reqMainKey, mainMap);
				}
				Map<String, Object> detailMap;
				if(reqDetail.containsKey(reqDetailKey)){
					detailMap = reqDetail.get(reqDetailKey);
					detailMap.put("amount", ""+(Double.valueOf(detailMap.get("amount").toString())+Double.valueOf(map.get("amount").toString())));
				}else{
					detailMap = new HashMap<String, Object>();
					detailMap.put("group_id", group_id);
					detailMap.put("hos_id", hos_id);
					detailMap.put("copy_code", copy_code);
					
					detailMap.put("req_id", req_id);  //计划单ID
					detailMap.put("req_code", req_code);  //计划单号
					detailMap.put("req_detail_id", String.valueOf(medCommonOutApplyCheckMapper.queryMedRequireDetailSeqNext()));
					
					detailMap.put("inv_id", map.get("inv_id").toString());  //药品ID
					detailMap.put("inv_no", map.get("inv_no").toString());  //药品变更ID
					
					detailMap.put("amount", map.get("amount").toString());  //数量
					detailMap.put("price", map.get("price").toString());  //计划单价
					
					detailMap.put("pack_code", null);  //包装单位
					detailMap.put("num", null);  //包装数量
					detailMap.put("num_exchange", null);  //包装换算量
					
					detailMap.put("sup_id", map.get("sup_id") == null ? null : map.get("sup_id").toString());  //供应商ID
					detailMap.put("sup_no", map.get("sup_no") == null ? null : map.get("sup_no").toString());  //供应商变更号
					
					detailMap.put("memo", map.get("note") == null ? null : map.get("note").toString());  //备注
				}
				//存放到reqDetail中
				reqDetail.put(reqDetailKey, detailMap);

				/*******************处理对应关系*********begin**********/
				Map<String, Object> relaMap = new HashMap<String, Object>();
				relaMap.put("group_id", group_id);
				relaMap.put("hos_id", hos_id);
				relaMap.put("copy_code", copy_code);
				relaMap.put("rela_id", req_id);
				relaMap.put("rela_type", "5");
				relaMap.put("apply_id", map.get("apply_id"));
				relaMap.put("app_detail_id", map.get("detail_id"));
				relaMap.put("rela_detail_id", detailMap.get("req_detail_id").toString());
				relaMap.put("rela_amount", map.get("amount").toString());
				applyRelaList.add(relaMap);
				/*******************处理对应关系*********end************/
			}
			
			//解析主表
			for (String key : reqMain.keySet()) {
				reqMainList.add(reqMain.get(key));
			}
			
			//解析明细表
			for (String key : reqDetail.keySet()) {
				reqDetailList.add(reqDetail.get(key));
			}
			
			if(reqDetailList.size() > 0){
				//插入主表
				medCommonOutApplyCheckMapper.addMedRequireMainByAppBatch(reqMainList);
				//插入明细表
				medCommonOutApplyCheckMapper.addMedRequireDetailByApp(reqDetailList);
				//添加对应关系
				medApplyOutRelaMapper.addMedApplyOutRelaBatch(applyRelaList);
			}else{
				return "{\"error\":\"汇总失败，明细数据不能为空！\"}";
			}
			
		}catch (Exception e) {
			logger.error(e.getMessage(), e);
			
			throw new SysException("{\"error\":\"汇总失败\"}");
		}	

		return "{\"msg\":\"汇总成功.\",\"state\":\"true\"}";
	}

	/**
	 * 汇总生成采购计划
	 */
	@Override
	public String addMedPurByApplyCollect(Map<String, Object> entityMap) throws DataAccessException {
		try{
			/**-------------常量-------begin---------*/
			Date make_date = new Date();
			String[] dates =  DateUtil.dateToString(make_date, "yyyy-MM-dd").split("-");
			String year = dates[0];
			String month = dates[1];
			String day = dates[2];
			String user_id = SessionManager.getUserId();
			String group_id = entityMap.get("group_id").toString();
			String hos_id = entityMap.get("hos_id").toString();
			String copy_code = entityMap.get("copy_code").toString();
			/**-------------常量-------end-----------*/
			
			//校验所选药品是否含已停用的药品
			String stopInvs = medCommonOutApplyCollectMapper.existsContainsInvIsStop(entityMap);
			if(stopInvs != null && !"".equals(stopInvs)){
				return "{\"error\":\"以下药品已停用不能生成请购单：<br/>"+stopInvs.toString()+"\"}";
			}
			
			//存放主表信息
			Map<String, Object> purMain = new HashMap<String, Object>();
			//存放明细信息
			List<Map<String, Object>> purDetailList = new ArrayList<Map<String,Object>>();
			//存放对应关系
			List<Map<String, Object>> applyRelaList= new ArrayList<Map<String,Object>>();
			//记录明细表信息
			Map<String, Map<String, Object>> purDetail = new HashMap<String, Map<String, Object>>();
			String purDetailKey = "";
			
			purMain.put("group_id", group_id);
			purMain.put("hos_id", hos_id);
			purMain.put("copy_code", copy_code);
			purMain.put("make_date", make_date);
			purMain.put("maker", user_id);
			
			//计划类型为自购计划，采购单位、请购单位、付款单位默认保存为当前医院ID。
			purMain.put("pur_type", 1);
			purMain.put("pur_hos_id", SessionManager.getHosId());
			purMain.put("req_hos_id", SessionManager.getHosId());
			purMain.put("pay_hos_id", SessionManager.getHosId());
			
			//默认为采购科室
			String stock_dept = medCommonMapper.queryMedStockDept(entityMap);
			if(stock_dept.split(",").length > 1){
				purMain.put("dept_no", stock_dept.split(",")[1]);
				purMain.put("dept_id", stock_dept.split(",")[0]);
			}else{
				return "{\"error\":\"请维护采购科室！\"}";
			}
			
			purMain.put("checker", null);
			purMain.put("check_date", null);
			purMain.put("arrive_date", null);
			purMain.put("is_dir", 0);
			purMain.put("is_collect", 0);
			purMain.put("dir_dept_id", null);
			purMain.put("dir_dept_no", null);
			purMain.put("come_from", 1);
			purMain.put("brif", "科室申领汇总生成");
			String stateValue = MyConfig.getSysPara("08046").toString();
			if("0".equals(stateValue)){
				purMain.put("state", "2");
			}
			if("1".equals(stateValue)){
				purMain.put("state", "1");
			}
			
			/**自动生成需求单号单据号-----------------------------begin---------------------------*/
			Map<String, Object> codeMap = new HashMap<String, Object>();
			codeMap.put("group_id", group_id);
			codeMap.put("hos_id", hos_id);
			codeMap.put("copy_code", copy_code);
			codeMap.put("table_code", "MED_PUR_MAIN");
			codeMap.put("year", year);
			codeMap.put("month", month);
			codeMap.put("day", day);
			codeMap.put("prefixe", "CG");
			codeMap.put("store_alias", "");
			codeMap.put("bus_type", "");
			//判断是否存在该业务流水码
			int flag = medNoManageMapper.queryIsExists(codeMap);
			String max_no = "";
			if(flag == 0){
				//如不存在则流水码为1，并插入流水码表中
				max_no = "1";
				codeMap.put("max_no", 1);
				medNoManageMapper.add(codeMap);
			}else{
				//更新该业务流水码+1
				medNoManageMapper.updateMaxNo(codeMap);
				//取出该业务更新后的流水码
				max_no = medNoManageMapper.queryMaxCode(codeMap);
			}
			//补流水码前缀0
			for (int i = max_no.length() ; i < 4; i++) {
				max_no = "0" + max_no;
			}
			
			String pur_code = "CG" + year.substring(2, 4) + month + day + max_no;
			purMain.put("pur_code", pur_code);
			/**自动生成需求单号单据号-----------------------------end-----------------------------*/
			//获得自增ID
			Long pur_id = medCommonOutApplyCollectMapper.queryMedPurMainSeqNext();
			purMain.put("pur_id", pur_id);
			
			//是否含没有维护默认供应商的药品
			boolean is_defaultSup = false;
			StringBuffer inv_error = new StringBuffer();
			
			//查询所选主从表数据
			List<Map<String, Object>> purList = JsonListMapUtil.toListMapLower(medCommonOutApplyCollectMapper.queryPurByCollect(entityMap));
			
			for(Map<String, Object> map : purList){
				//要求不合并药品
				purDetailKey = map.get("detail_id").toString();
				//合并药品
				//purDetailKey = map.get("detail_id").toString() + map.get("dept_id").toString() + map.get("inv_id").toString() + map.get("app_date").toString();
				Map<String, Object> detailMap;
				if(purDetail.containsKey(purDetailKey)){
					detailMap = purDetail.get(purDetailKey);
					detailMap.put("amount", ""+(Double.valueOf(detailMap.get("amount").toString())+Double.valueOf(map.get("amount").toString())));
				}else{
					if(map.get("sup_id") == null || "".equals(map.get("sup_id"))){
						inv_error.append(map.get("inv_code") + " " + map.get("inv_name") + "<br/>");
						is_defaultSup = true;
					}
					if(is_defaultSup){
						continue;
					}
					detailMap = new HashMap<String, Object>();
					detailMap.put("group_id", group_id);
					detailMap.put("hos_id", hos_id);
					detailMap.put("copy_code", copy_code);
					
					detailMap.put("pur_id", pur_id);  //计划单ID
					detailMap.put("pur_code", pur_code);  //计划单号
					detailMap.put("pur_detail_id", String.valueOf(medCommonOutApplyCollectMapper.queryMedPurDetailSeqNext()));
					
					detailMap.put("inv_id", map.get("inv_id").toString());  //药品ID
					detailMap.put("inv_no", map.get("inv_no").toString());  //药品变更ID
					
					detailMap.put("price", map.get("price").toString());  //计划单价
					detailMap.put("amount", map.get("amount").toString());  //数量
					detailMap.put("req_amount", map.get("amount").toString());  //需求数量
					
					detailMap.put("pack_code", null);  //包装单位
					detailMap.put("num", null);  //包装数量
					detailMap.put("num_exchange", null);  //包装换算量
					
					detailMap.put("sup_id", map.get("sup_id") == null ? null : map.get("sup_id").toString());  //供应商ID
					detailMap.put("sup_no", map.get("sup_no") == null ? null : map.get("sup_no").toString());  //供应商变更号
					
					detailMap.put("memo", map.get("note") == null ? null : map.get("note").toString());  //备注

					detailMap.put("is_closed", "0");	//是否关闭
					detailMap.put("app_dept_id", map.get("dept_id").toString());	//申请科室ID
					detailMap.put("app_dept_no", map.get("dept_no").toString());	//申请科室变更号
					detailMap.put("app_date", make_date);//map.get("app_date"));	//申请日期
				}
				//存放到purDetail中
				purDetail.put(purDetailKey, detailMap);

				/*******************处理对应关系*********begin**********/
				Map<String, Object> relaMap = new HashMap<String, Object>();
				relaMap.put("group_id", group_id);
				relaMap.put("hos_id", hos_id);
				relaMap.put("copy_code", copy_code);
				relaMap.put("rela_id", pur_id);
				relaMap.put("rela_type", "1");  //采购
				relaMap.put("apply_id", map.get("apply_id"));
				relaMap.put("app_detail_id", map.get("detail_id"));
				relaMap.put("rela_detail_id", detailMap.get("pur_detail_id").toString());
				relaMap.put("rela_amount", map.get("amount").toString());
				applyRelaList.add(relaMap);
				/*******************处理对应关系*********end************/
			}
			
			if(is_defaultSup){
				return "{\"error\":\"以下药品无默认供应商：<br/>"+inv_error.toString()+"\"}";
			}
			
			//解析明细表
			for (String key : purDetail.keySet()) {
				purDetailList.add(purDetail.get(key));
			}
			
			if(purDetailList.size() > 0){
				//插入主表
				medCommonOutApplyCollectMapper.addMedPurMainByApp(purMain);
				//插入明细表
				medCommonOutApplyCollectMapper.addMedPurDetailByApp(purDetailList);
				//添加对应关系
				medApplyPurRelaMapper.addMedApplyPurRelaBatch(applyRelaList);
			}else{
				return "{\"error\":\"汇总失败，明细数据不能为空！\"}";
			}
			
		}catch (Exception e) {
			logger.error(e.getMessage(), e);
			
			throw new SysException("{\"error\":\"汇总失败\"}");
		}	

		return "{\"msg\":\"汇总成功.\",\"state\":\"true\"}";
	}
	/**
	 * 查看关闭的药品
	 */
	@Override
	public String queryMedApplyCloseInvInfo(Map<String, Object> entityMap) throws DataAccessException {
		SysPage sysPage = new SysPage();
		sysPage = (SysPage) entityMap.get("sysPage");
		entityMap.put("user_id", SessionManager.getUserId());
		
		if (sysPage.getTotal()==-1){
			List<Map<String,Object>> list = (List<Map<String,Object>>)medCommonOutApplyCollectMapper.queryMedApplyCloseInvInfo(entityMap);
			return ChdJson.toJsonLower(list);
		}else{
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			List<Map<String,Object>> list =(List<Map<String,Object>>) medCommonOutApplyCollectMapper.queryMedApplyCloseInvInfo(entityMap, rowBounds);
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJsonLower(list, page.getTotal());
		}
	}
	/**
	 * 取消关闭药品
	 */
	@Override
	public String updateMedApplyCancleCloseInv(List<Map<String, Object>> entityList) throws DataAccessException {
		try {	
			//批量关闭药品
			medCommonOutApplyCollectMapper.updateMedApplyCancleCloseInv(entityList);
		}catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"操作失败\"}");
			//return "{\"error\":\"操作失败 数据库异常 请联系管理员! 方法 updateBackMedCommonOutApplyCheckBatch\"}";
		}	
		return "{\"msg\":\"操作成功.\",\"state\":\"true\"}";
	}
}
