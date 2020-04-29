/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.mat.serviceImpl.storage.out;

import java.util.*;

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
import com.chd.hrp.mat.dao.base.MatApplyOutRelaMapper;
import com.chd.hrp.mat.dao.base.MatApplyPurRelaMapper;
import com.chd.hrp.mat.dao.base.MatCommonMapper;
import com.chd.hrp.mat.dao.base.MatNoManageMapper;
import com.chd.hrp.mat.dao.storage.out.MatCommonOutApplyCheckMapper;
import com.chd.hrp.mat.dao.storage.out.MatCommonOutApplyCollectMapper;
import com.chd.hrp.mat.dao.storage.out.MatCommonOutApplyMapper;
import com.chd.hrp.mat.entity.MatApplyMain;
import com.chd.hrp.mat.service.base.MatCommonService;
import com.chd.hrp.mat.service.storage.out.MatCommonOutApplyCollectService;
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
@Service("matCommonOutApplyCollectService")
public class MatCommonOutApplyCollectServiceImpl implements MatCommonOutApplyCollectService {

	private static Logger logger = Logger.getLogger(MatCommonOutApplyCollectServiceImpl.class);
	//引入DAO操作
	@Resource(name = "matCommonOutApplyCollectMapper")
	private final MatCommonOutApplyCollectMapper matCommonOutApplyCollectMapper = null;
	@Resource(name = "matCommonService")
	private final MatCommonService matCommonService = null;
	@Resource(name = "matNoManageMapper")
	private final MatNoManageMapper matNoManageMapper = null;
	@Resource(name = "matCommonOutApplyMapper")
	private final MatCommonOutApplyMapper matCommonOutApplyMapper = null;
	@Resource(name = "matCommonOutApplyCheckMapper")
	private final MatCommonOutApplyCheckMapper matCommonOutApplyCheckMapper = null;
	@Resource(name = "matApplyOutRelaMapper")
	private final MatApplyOutRelaMapper matApplyOutRelaMapper = null;
	@Resource(name = "matApplyPurRelaMapper")
	private final MatApplyPurRelaMapper matApplyPurRelaMapper = null;
	@Resource(name = "matCommonMapper")
	private final MatCommonMapper matCommonMapper = null;
	
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
			List<Map<String, Object>> list = (List<Map<String, Object>>) matCommonOutApplyCollectMapper.query(entityMap);
			
			return ChdJson.toJsonLower(list);
		}else{
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			List<Map<String, Object>> list = (List<Map<String, Object>>) matCommonOutApplyCollectMapper.query(entityMap, rowBounds);
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJsonLower(list, page.getTotal());
		}
	}

	/**
	 * @Description 处理请购单主查询--按材料汇总查询<BR>
	 * @param entityMap
	 * @return String
	 * @throws DataAccessException
	 */
	@Override
	public String queryInvCollect(Map<String, Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		sysPage = (SysPage) entityMap.get("sysPage");
		if (sysPage.getTotal()==-1){
			List<Map<String, Object>> list = (List<Map<String, Object>>) matCommonOutApplyCollectMapper.queryInvCollect(entityMap);
			
			return ChdJson.toJsonLower(list);
		}else{
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			List<Map<String, Object>> list = (List<Map<String, Object>>) matCommonOutApplyCollectMapper.queryInvCollect(entityMap, rowBounds);
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJsonLower(list, page.getTotal());
		}
	}

	/**
	 * @Description 处理请购单明细查询--以材料为主显示每个科室的申请情况<BR>
	 * @param entityMap
	 * @return String
	 * @throws DataAccessException
	 */
	@Override
	public String queryInvCollectDetail(Map<String, Object> entityMap) throws DataAccessException{
		
		List<Map<String, Object>> list = (List<Map<String, Object>>) matCommonOutApplyCollectMapper.queryInvCollectDetail(entityMap);
			
		return ChdJson.toJsonLower(list);
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
			//return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addMatCommonOutApplyCollect\"}";
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
			//return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addBatchMatCommonOutApplyCollect\"}";
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
			//return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addMatCommonOutApplyCollect\"}";
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
			//return "{\"error\":\"更新失败 数据库异常 请联系管理员! 方法 updateBatchMatCommonOutApplyCollect\"}";
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
			//return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 deleteMatCommonOutApplyCollect\"}";
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
			//return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 deleteBatchMatCommonOutApplyCollect\"}";
		}	
	}

	@Override
	public String addByBack(List<Map<String, Object>> entityList) throws DataAccessException {
		
		try {	
			//获取单据主表信息（申请科室，相应库房等）
			List<Map<String, Object>> mainList = JsonListMapUtil.toListMapLower(matCommonOutApplyCollectMapper.queryMainByBack(entityList));
			if(mainList.size() != 1){
				return "{\"error\":\"请选择同一科室、库房的材料\"}";
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
			map.put("year", mainMap.get("year"));
			map.put("month", mainMap.get("month"));
			map.put("table_code", "MAT_APPLY_MAIN");
			map.put("prefixe", "SQ");
			String apply_no = matCommonService.getMatNextNo(map);
			
			mainMap.put("apply_no", apply_no);
			/******************生成单号********end*************************/
			//取出主表ID（自增序列）
			mainMap.put("apply_id", matCommonOutApplyMapper.queryMatApplyMainSeq());
			//获取退回的材料信息
			List<Map<String, Object>> detailList = JsonListMapUtil.toListMapLower(matCommonOutApplyCollectMapper.queryDetailByBack(entityList));
			
			if(detailList.size() == 0){
				return "{\"error\":\"请选择未完成的材料\"}";
			}
			
			for(Map<String, Object> detailMap : detailList){
				detailMap.put("apply_id", mainMap.get("apply_id"));
				detailMap.put("detail_id", matCommonOutApplyMapper.queryMatApplyDetailSeq());
				//退回数量=申请数量-处理数量
				double amount = NumberUtil.sub(Double.valueOf(detailMap.get("app_amount").toString()), Double.valueOf(detailMap.get("rela_amount").toString()));
				detailMap.put("app_amount", amount);
				detailMap.put("note",  null);//备注
			}
			
			//插入主表
			matCommonOutApplyMapper.addMatApplyMain(mainMap);
			//插入明细表
			matCommonOutApplyMapper.addMatApplyDetail(detailList);
			
			//修改明细状态为退回
			matCommonOutApplyCollectMapper.updateDetailByBack(entityList);
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);

			throw new SysException("{\"error\":\"操作失败\"}");
			//return "{\"error\":\"操作失败 数据库异常 请联系管理员! 方法 updateBackMatCommonOutApplyCollectBatch\"}";
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

			int state = matCommonOutApplyCollectMapper.update(entityMap);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		
		try {
			
			int state = matCommonOutApplyCollectMapper.add(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addOrUpdateMatCommonOutApplyCollect\"}";

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
		return matCommonOutApplyCollectMapper.queryByCode(entityMap);
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
		return matCommonOutApplyCollectMapper.queryByUniqueness(entityMap);
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
		
		return JsonListMapUtil.toListMapLower(matCommonOutApplyCollectMapper.queryOutMain(entityMap));
	}
	/**
	 * 组装生成出库单明细表数据
	 */
	@Override
	public String queryOutDetail(Map<String, Object> entityMap) throws DataAccessException {
		try{
			//明细中材料信息
			List<Map<String, Object>> invList = JsonListMapUtil.toListMapLower(matCommonOutApplyCollectMapper.querySelectDetailForOut(entityMap));
			return matCommonService.getInvJsonByInvList(invList);
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);

			return "{\"error\":\"生成失败 数据库异常 请联系管理员! 方法 MatCommonOutApply--queryOutDetail\"}";
		}
	}

	/**
	 * 组装生成代销出库单明细表数据
	 */
	@Override
	public String queryAffiOutDetail(Map<String, Object> entityMap) throws DataAccessException {
		try{
			//明细材料信息
			List<Map<String, Object>> invList = JsonListMapUtil.toListMapLower(matCommonOutApplyCollectMapper.querySelectDetailForOut(entityMap));
			
			return matCommonService.getAffiInvJsonByAffiInvList(invList);
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);

			return "{\"error\":\"生成失败 数据库异常 请联系管理员! 方法 MatCommonOutApply--queryOutDetail\"}";
		}
	}
	
	/**
	 * 查询管理部门对应的仓库信息<BR> 
	*/
	@Override
	public String queryStoreByDept(Map<String,Object> entityMap)throws DataAccessException{
		
		String store = matCommonOutApplyCollectMapper.queryStoreByDept(entityMap);
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
		
		return JsonListMapUtil.toListMapLower(matCommonOutApplyCollectMapper.queryTranMain(entityMap));
	}
	/**
	 * 组装生成调拨单明细表数据
	 */
	@Override
	public String queryTranDetail(Map<String, Object> entityMap) throws DataAccessException {
		try{
			//材料基础信息
			List<Map<String, Object>> invList = JsonListMapUtil.toListMapLower(matCommonOutApplyCollectMapper.querySelectDetailForOut(entityMap));

			return matCommonService.getInvJsonByInvList(invList);
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);

			return "{\"error\":\"生成失败 数据库异常 请联系管理员! 方法 MatCommonOutApply--queryOutDetail\"}";
		}
	}

	/**
	 * 组装生成代销调拨单明细表数据
	 */
	@Override
	public String queryAffiTranDetail(Map<String, Object> entityMap) throws DataAccessException {
		try{
			//材料基础信息
			List<Map<String, Object>> invList = JsonListMapUtil.toListMapLower(matCommonOutApplyCollectMapper.querySelectDetailForOut(entityMap));

			return matCommonService.getAffiInvJsonByAffiInvList(invList);
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);

			return "{\"error\":\"生成失败 数据库异常 请联系管理员! 方法 MatCommonOutApply--queryOutDetail\"}";
		}
	}
	
	/**
	 * 组装生成科室需求计划主表数据
	 */
	@Override
	public List<Map<String, Object>> queryReqMain(Map<String, Object> entityMap) throws DataAccessException {
		return JsonListMapUtil.toListMapLower(matCommonOutApplyCollectMapper.queryReqMain(entityMap));
	}
	/**
	 * 组装生成科室需求计划明细表数据
	 */
	@Override
	public String queryReqDetail(Map<String, Object> entityMap) throws DataAccessException {
		try{
			
			return ChdJson.toJson(JsonListMapUtil.toListMapLower(matCommonOutApplyCollectMapper.queryReqDetail(entityMap)));
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);

			return "{\"error\":\"生成失败 数据库异常 请联系管理员! 方法 MatCommonOutApply--queryOutDetail\"}";
		}
	}

	/**
	 * 汇总生成科室需求计划
	 */
	@Override
	public String addMatReqByApplyCollect(Map<String, Object> entityMap) throws DataAccessException {
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
			
			//校验所选材料是否含已停用的材料
			String stopInvs = matCommonOutApplyCollectMapper.existsContainsInvIsStop(entityMap);
			if(stopInvs != null && !"".equals(stopInvs)){
				return "{\"error\":\"以下材料已停用不能生成科室需求计划：<br/>"+stopInvs.toString()+"\"}";
			}
			
			//校验所选材料是否已经生成过请购单或科室需求计划
//			String alreadyInvs = matCommonOutApplyCollectMapper.existsAlreadyPurOrReq(entityMap);
//			if(alreadyInvs != null && !"".equals(alreadyInvs)){
//				return "{\"error\":\"以下材料已经生成过请购单或科室需求计划不能重复生成：<br/>"+alreadyInvs.toString()+"\"}";
//			}
			
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
			List<Map<String, Object>> reqList = JsonListMapUtil.toListMapLower(matCommonOutApplyCollectMapper.queryReqByCollect(entityMap));
			
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
					codeMap.put("table_code", "MAT_REQUIRE_MAIN");
					codeMap.put("year", year);
					codeMap.put("month", month);
					codeMap.put("day", day);
					codeMap.put("prefixe", "XQ");
					codeMap.put("store_alias", "");
					codeMap.put("bus_type", "");
					req_code = matCommonService.getMatNextNo(codeMap);
					mainMap.put("req_code", req_code);
					/**自动生成需求单号单据号-----------------------------end-----------------------------*/
					//获得自增ID
					req_id = matCommonOutApplyCheckMapper.queryMatRequireMainSeqNext();
					mainMap.put("req_id", String.valueOf(req_id));
					
					mainMap.put("come_from", 2);
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
					detailMap.put("req_detail_id", String.valueOf(matCommonOutApplyCheckMapper.queryMatRequireDetailSeqNext()));
					
					detailMap.put("inv_id", map.get("inv_id").toString());  //材料ID
					detailMap.put("inv_no", map.get("inv_no").toString());  //材料变更ID
					
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
				matCommonOutApplyCheckMapper.addMatRequireMainByAppBatch(reqMainList);
				//插入明细表
				matCommonOutApplyCheckMapper.addMatRequireDetailByApp(reqDetailList);
				//添加对应关系
				matApplyOutRelaMapper.addMatApplyOutRelaBatch(applyRelaList);
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
	public String addMatPurByApplyCollect(Map<String, Object> entityMap) throws DataAccessException {
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
			
			//校验所选材料是否含已停用的材料
			String stopInvs = matCommonOutApplyCollectMapper.existsContainsInvIsStop(entityMap);
			if(stopInvs != null && !"".equals(stopInvs)){
				return "{\"error\":\"以下材料已停用不能生成请购单：<br/>"+stopInvs.toString()+"\"}";
			}
			
			//校验所选材料是否已经生成过请购单或科室需求计划
		String alreadyInvs = matCommonOutApplyCollectMapper.existsAlreadyPurOrReq(entityMap);
		if(alreadyInvs != null && !"".equals(alreadyInvs)){
			return "{\"error\":\"以下材料已经生成过请购单或科室需求计划不能重复生成：<br/>"+alreadyInvs.toString()+"\"}";
		}
			
			//存放主表信息
			List<Map<String, Object>> purMainList = new ArrayList<Map<String,Object>>();
			Map<String, String> purMainKey = new HashMap<String, String>();
			//存放明细信息
			List<Map<String, Object>> purDetailList = new ArrayList<Map<String,Object>>();
			//存放对应关系
			List<Map<String, Object>> applyRelaList= new ArrayList<Map<String,Object>>();
			//记录明细表信息
			Map<String, Map<String, Object>> purDetail = new HashMap<String, Map<String, Object>>();
			String purDetailKey = "";
			
			/**自动生成需求单号单据号-----------------------------begin---------------------------*/
			Map<String, Object> codeMap = new HashMap<String, Object>();
			codeMap.put("group_id", group_id);
			codeMap.put("hos_id", hos_id);
			codeMap.put("copy_code", copy_code);
			codeMap.put("table_code", "MAT_PUR_MAIN");
			codeMap.put("year", year);
			codeMap.put("month", month);
			codeMap.put("day", day);
			codeMap.put("prefixe", "CG");
			codeMap.put("store_alias", "");
			codeMap.put("bus_type", "");
			/**自动生成需求单号单据号-----------------------------end-----------------------------*/
			
			//是否含没有维护默认供应商的材料
			boolean is_defaultSup = false;
			StringBuffer inv_error = new StringBuffer();		
			StringBuffer amount_error = new StringBuffer();		
			String pur_id = null;
			String pur_code = "";
			
			//查询所选主从表数据
			List<Map<String, Object>> purList = JsonListMapUtil.toListMapLower(matCommonOutApplyCollectMapper.queryPurByCollect(entityMap));
			
			for(Map<String, Object> map : purList){
				/*******************处理主表信息*********begin**********/
				if(!purMainKey.containsKey(map.get("store_id").toString())){
					
					Map<String, Object> purMain = new HashMap<String, Object>();
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
					String stock_dept = matCommonMapper.queryMatStockDept(map);
					if(stock_dept.split(",").length > 1){
						purMain.put("dept_no", stock_dept.split(",")[1]);
						purMain.put("dept_id", stock_dept.split(",")[0]);
					}else{
						return "{\"error\":\"请维护采购科室！\"}";
					}
					if(entityMap.containsKey("isStore")){
						purMain.put("store_id", entityMap.get("store_id"));
						purMain.put("store_no", entityMap.get("store_no"));
					}else{
						purMain.put("store_id", map.get("store_id"));
						purMain.put("store_no", map.get("store_no"));
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
					String stateValue = MyConfig.getSysPara("04046");
					if("0".equals(stateValue)){
						purMain.put("state", "2");
					}
					if("1".equals(stateValue)){
						purMain.put("state", "1");
					}
					//获得自增ID
					pur_id = matCommonOutApplyCollectMapper.queryMatPurMainSeqNext().toString();
					purMain.put("pur_id", pur_id);
					//获取单号
					pur_code = matCommonService.getMatNextNo(codeMap);
					purMain.put("pur_code", pur_code);
					
					purMain.put("come_from", 3);
					
					purMainList.add(purMain);
					purMainKey.put(map.get("store_id").toString(), pur_id+","+pur_code);
				}else{
					String keys[] = purMainKey.get(map.get("store_id").toString()).split(",");
					pur_id = keys[0];
					pur_code = keys[1];
				}
				/*******************处理主表信息*********end************/

				/*******************处理明细信息*********begin**********/
				//判断数量是否小于等于0 
				if(Double.valueOf(map.get("amount").toString()) <= 0){
					amount_error.append(map.get("apply_no") + " " + map.get("inv_code") + " " + map.get("inv_name") + "<br/>");
					continue;
				}
				//要求不合并材料
				purDetailKey = map.get("detail_id").toString();
				//合并材料
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
					detailMap.put("pur_detail_id", String.valueOf(matCommonOutApplyCollectMapper.queryMatPurDetailSeqNext()));
					
					detailMap.put("inv_id", map.get("inv_id").toString());  //材料ID
					detailMap.put("inv_no", map.get("inv_no").toString());  //材料变更ID
					
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
				/*******************处理明细信息*********end************/

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

			if(amount_error.length() > 0){
				return "{\"error\":\"以下材料生成数量小于等于0：<br/>"+amount_error.toString()+"\"}";
			}
			if(is_defaultSup){
				return "{\"error\":\"以下材料无默认供应商：<br/>"+inv_error.toString()+"\"}";
			}
			
			//解析明细表
			for (String key : purDetail.keySet()) {
				purDetailList.add(purDetail.get(key));
			}
			
			if(purDetailList.size() > 0){
				//插入主表
				matCommonOutApplyCollectMapper.addMatPurMainByAppBatch(purMainList);
				//插入明细表
				matCommonOutApplyCollectMapper.addMatPurDetailByApp(purDetailList);
				//添加对应关系
				matApplyPurRelaMapper.addMatApplyPurRelaBatch(applyRelaList);
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
	 * 按材料汇总页面--汇总生成采购计划
	 */
	@Override
	public String addMatPurByApplyInvCollect(Map<String, Object> entityMap) throws DataAccessException {
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
			
			/**------------根据选择的材料信息获取申请单明细ID------begin----------*/
			//转换日期格式
			if(entityMap.get("begin_app_date") != null && !"".equals(entityMap.get("begin_app_date"))){
				entityMap.put("begin_app_date", DateUtil.stringToDate(entityMap.get("begin_app_date").toString()+" 00:00:00", "yyyy-MM-dd HH:mm:ss"));
			}
			if(entityMap.get("end_app_date") != null && !"".equals(entityMap.get("end_app_date"))){
				entityMap.put("end_app_date", DateUtil.stringToDate(entityMap.get("end_app_date").toString()+" 23:59:59", "yyyy-MM-dd HH:mm:ss"));
			}
			//添加用户
			entityMap.put("user_id", user_id);
			
			String detail_ids = matCommonOutApplyCollectMapper.queryAppDetailIdsByInvCollect(entityMap);
			if(detail_ids == null || "".equals(detail_ids)){
				return "{\"error\":\"操作失败，没有得到选择的数据！\"}";
			}
			entityMap.put("detail_ids", detail_ids);
			/**------------根据选择的材料信息获取申请单明细ID------end------------*/
			
			//校验所选材料是否含已停用的材料
			String stopInvs = matCommonOutApplyCollectMapper.existsContainsInvIsStop(entityMap);
			if(stopInvs != null && !"".equals(stopInvs)){
				return "{\"error\":\"以下材料已停用不能生成请购单：<br/>"+stopInvs.toString()+"\"}";
			}
			
			//校验所选材料是否已经生成过请购单或科室需求计划
//			String alreadyInvs = matCommonOutApplyCollectMapper.existsAlreadyPurOrReq(entityMap);
//			if(alreadyInvs != null && !"".equals(alreadyInvs)){
//				return "{\"error\":\"以下材料已经生成过请购单或科室需求计划不能重复生成：<br/>"+alreadyInvs.toString()+"\"}";
//			}
			
			//存放主表信息
			List<Map<String, Object>> purMainList = new ArrayList<Map<String,Object>>();
			Map<String, String> purMainKey = new HashMap<String, String>();
			//存放明细信息
			List<Map<String, Object>> purDetailList = new ArrayList<Map<String,Object>>();
			//存放对应关系
			List<Map<String, Object>> applyRelaList= new ArrayList<Map<String,Object>>();
			//记录明细表信息
			Map<String, Map<String, Object>> purDetail = new HashMap<String, Map<String, Object>>();
			String purDetailKey = "";
			
			/**自动生成需求单号单据号-----------------------------begin---------------------------*/
			Map<String, Object> codeMap = new HashMap<String, Object>();
			codeMap.put("group_id", group_id);
			codeMap.put("hos_id", hos_id);
			codeMap.put("copy_code", copy_code);
			codeMap.put("table_code", "MAT_PUR_MAIN");
			codeMap.put("year", year);
			codeMap.put("month", month);
			codeMap.put("day", day);
			codeMap.put("prefixe", "CG");
			codeMap.put("store_alias", "");
			codeMap.put("bus_type", "");
			/**自动生成需求单号单据号-----------------------------end-----------------------------*/
			
			//是否含没有维护默认供应商的材料
			boolean is_defaultSup = false;
			StringBuffer inv_error = new StringBuffer();		
			StringBuffer amount_error = new StringBuffer();		
			String pur_id = null;
			String pur_code = "";	
			
			//查询所选主从表数据
			List<Map<String, Object>> purList = JsonListMapUtil.toListMapLower(matCommonOutApplyCollectMapper.queryPurByInvCollect(entityMap));
			double sum_amount = 0; //需采购总量
			double cur_amount = 0; //即时库存 
			double app_amount = 0; //申请数量
			double out_amount = 0; //已出库数量
			double pur_amount = 0; //已采购数量
			double req_amount = 0; //已需求数量
			double amount = 0; //采购数量
			String inv_id = "";
			/**
			 * 相同材料采购数量算法：（采购数量 = 申请数量 - 已出库数量 - 已采购数量 - 已需求数量）
			 * 方法一：（以即时库存为依据）
			 * 1.每次循环库存量如果大于0，则用下次循环的库存 = 当前库存 - 需采购数量 > 0 ? 当前库存 - 需采购数量 : 0;
			 * 2.如果库存 >= 当前采购数量，则本条信息不进行采购，并减少库存;
			 * 3.如果库存 < 当前采购数量，则本条信息需进行采购（采购数量 = 需采购数量 - 库存），并使库存为0;
			 * 方法二：（以需采购总量为依据）
			 * 1.如果需采购总量 >= 当前需采购量，则本条信息需进行采购（采购数量=需采购数量-库存），并减少需采购总量;
			 * 2.如果需采购总量 < 当前需采购量，则本条信息需进行采购（采购数量=需采购数量-库存），并使库存为0;
			 * 3.如果需采购总量 = 0，则不进行采购;
			 * */
			for(Map<String, Object> map : purList){

				/*******************处理主表信息*********begin**********/
				if(!purMainKey.containsKey(map.get("store_id").toString())){
					
					Map<String, Object> purMain = new HashMap<String, Object>();
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
					String stock_dept = matCommonMapper.queryMatStockDept(map);
					if(stock_dept.split(",").length > 1){
						purMain.put("dept_no", stock_dept.split(",")[1]);
						purMain.put("dept_id", stock_dept.split(",")[0]);
					}else{
						return "{\"error\":\"请维护采购科室！\"}";
					}

					purMain.put("store_id", map.get("store_id"));
					purMain.put("store_no", map.get("store_no"));
					purMain.put("checker", null);
					purMain.put("check_date", null);
					purMain.put("arrive_date", null);
					purMain.put("is_dir", 0);
					purMain.put("is_collect", 0);
					purMain.put("dir_dept_id", null);
					purMain.put("dir_dept_no", null);
					purMain.put("come_from", 1);
					purMain.put("brif", "科室申领汇总生成");
					String stateValue = MyConfig.getSysPara("04046");
					if("0".equals(stateValue)){
						purMain.put("state", "2");
					}
					if("1".equals(stateValue)){
						purMain.put("state", "1");
					}
					//获得自增ID
					pur_id = matCommonOutApplyCollectMapper.queryMatPurMainSeqNext().toString();
					purMain.put("pur_id", pur_id);
					//获取单号
					pur_code = matCommonService.getMatNextNo(codeMap);
					purMain.put("pur_code", pur_code);
					
					purMain.put("come_from", 3);
					purMainList.add(purMain);
					purMainKey.put(map.get("store_id").toString(), pur_id+","+pur_code);
				}else{
					String keys[] = purMainKey.get(map.get("store_id").toString()).split(",");
					pur_id = keys[0];
					pur_code = keys[1];
				}
				/*******************处理主表信息*********end************/

				/*******************处理明细信息*********begin**********/
				//如果材料与上次循环相同则不重新取即时库存/需采购总量，反之则取
				if(!inv_id.equals(map.get("inv_id").toString())){
					cur_amount = Double.valueOf(map.get("cur_amount").toString());
					sum_amount = Double.valueOf(map.get("sum_amount").toString());
					inv_id = map.get("inv_id").toString();
				}
				//获取申请数量
				app_amount = Double.valueOf(map.get("app_amount").toString());
				//获取已出库数量
				out_amount = Double.valueOf(map.get("out_amount").toString());
				//获取已采购数量
				pur_amount = Double.valueOf(map.get("pur_amount").toString());
				//获取已需求数量
				req_amount = Double.valueOf(map.get("req_amount").toString());
				//计算需采购量
				amount = NumberUtil.sub(NumberUtil.sub(NumberUtil.sub(app_amount, out_amount), pur_amount), req_amount);
				
				//以库存为基准获取实际采购数量
				/*if(cur_amount >= amount){
					cur_amount = NumberUtil.sub(cur_amount, amount);
					continue;
				}else{
					amount = NumberUtil.sub(amount, cur_amount);
					cur_amount = 0;
				}*/
				
				//以需采购总量为基准获取实际采购数量
				if(sum_amount == 0){
					continue;
				}
				if(sum_amount >= amount){
					sum_amount = NumberUtil.sub(sum_amount, amount);
				}else{
					amount = sum_amount;
					sum_amount = 0;
				}
				
				//判断需采购量是否小于等于0 
				if(amount <= 0){
					amount_error.append(map.get("apply_no") + " " + map.get("inv_code") + " " + map.get("inv_name") + "<br/>");
					continue;
				}
				
				//要求不合并材料
				purDetailKey = map.get("detail_id").toString();
				//合并材料
				//purDetailKey = map.get("dept_id").toString() + map.get("inv_id").toString() + map.get("app_date").toString();
				Map<String, Object> detailMap;
				if(purDetail.containsKey(purDetailKey)){
					detailMap = purDetail.get(purDetailKey);
					detailMap.put("amount", Double.valueOf(detailMap.get("amount").toString()) + amount);
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
					detailMap.put("pur_detail_id", String.valueOf(matCommonOutApplyCollectMapper.queryMatPurDetailSeqNext()));
					
					detailMap.put("inv_id", map.get("inv_id").toString());  //材料ID
					detailMap.put("inv_no", map.get("inv_no").toString());  //材料变更ID
					
					detailMap.put("price", map.get("price").toString());  //计划单价
					detailMap.put("amount", amount);  //数量
					detailMap.put("req_amount", app_amount);  //需求数量
					
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
				/*******************处理明细信息*********end************/

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
				relaMap.put("rela_amount", amount);
				applyRelaList.add(relaMap);
				/*******************处理对应关系*********end************/
			}

			if(amount_error.length() > 0){
				return "{\"error\":\"以下材料生成数量小于等于0：<br/>"+amount_error.toString()+"\"}";
			}
			if(is_defaultSup){
				return "{\"error\":\"以下材料无默认供应商：<br/>"+inv_error.toString()+"\"}";
			}
			
			//解析明细表
			for (String key : purDetail.keySet()) {
				purDetailList.add(purDetail.get(key));
			}
			
			if(purDetailList.size() > 0){
				//插入主表
				matCommonOutApplyCollectMapper.addMatPurMainByAppBatch(purMainList);
				//插入明细表
				matCommonOutApplyCollectMapper.addMatPurDetailByApp(purDetailList);
				//添加对应关系
				matApplyPurRelaMapper.addMatApplyPurRelaBatch(applyRelaList);
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
	 * 查看关闭的材料
	 */
	@Override
	public String queryMatApplyCloseInvInfo(Map<String, Object> entityMap) throws DataAccessException {
		SysPage sysPage = new SysPage();
		sysPage = (SysPage) entityMap.get("sysPage");
		entityMap.put("user_id", SessionManager.getUserId());
		
		if (sysPage.getTotal()==-1){
			List<Map<String,Object>> list = (List<Map<String,Object>>)matCommonOutApplyCollectMapper.queryMatApplyCloseInvInfo(entityMap);
			return ChdJson.toJsonLower(list);
		}else{
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			List<Map<String,Object>> list =(List<Map<String,Object>>) matCommonOutApplyCollectMapper.queryMatApplyCloseInvInfo(entityMap, rowBounds);
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJsonLower(list, page.getTotal());
		}
	}
	/**
	 * 取消关闭材料
	 */
	@Override
	public String updateMatApplyCancleCloseInv(List<Map<String, Object>> entityList) throws DataAccessException {
		try {	
			//批量关闭材料
			matCommonOutApplyCollectMapper.updateMatApplyCancleCloseInv(entityList);
		}catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"操作失败\"}");
			//return "{\"error\":\"操作失败 数据库异常 请联系管理员! 方法 updateBackMatCommonOutApplyCheckBatch\"}";
		}	
		return "{\"msg\":\"操作成功.\",\"state\":\"true\"}";
	}
}
