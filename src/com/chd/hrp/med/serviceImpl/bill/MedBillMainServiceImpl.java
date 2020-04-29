/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.med.serviceImpl.bill;

import java.text.SimpleDateFormat;
import java.util.*;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import com.alibaba.fastjson.JSONObject;
import com.chd.base.SessionManager;
import com.chd.base.SysPage;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.base.util.DateUtil;
import com.chd.hrp.acc.service.vouch.SuperPrintService;
import com.chd.hrp.med.dao.base.MedNoManageMapper;
import com.chd.hrp.med.dao.bill.MedBillMainMapper;
import com.chd.hrp.med.dao.storage.out.MedCommonOutApplyCheckMapper;
import com.chd.hrp.med.entity.MedBillMain;
import com.chd.hrp.med.service.bill.MedBillDetailService;
import com.chd.hrp.med.service.bill.MedBillMainService;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Description:
 * 保存采购发票的主要信息
 * @Table:
 * MED_BILL_MAIN
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 


@Service("medBillMainService")
public class MedBillMainServiceImpl implements MedBillMainService {

	private static Logger logger = Logger.getLogger(MedBillMainServiceImpl.class);
	//引入DAO操作
	@Resource(name = "medBillMainMapper")
	private final MedBillMainMapper medBillMainMapper = null;
    
	//引入DAO操作
	@Resource(name = "medBillDetailService")
	private final MedBillDetailService medBillDetailService = null;
	
	@Resource(name = "medNoManageMapper")
	private final MedNoManageMapper medNoManageMapper = null;
	
	/**
	 * @Description 
	 * 添加保存采购发票的主要信息<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addMedBillMain(Map<String,Object> entityMap)throws DataAccessException{
		
		//获取对象保存采购发票的主要信息
		long count = medBillMainMapper.queryMedBillMainExists(entityMap);

		if (count>0) {

			return "{\"error\":\"发票号重复,请重新添加.\"}";

		}
		
		
		try {
			Long bill_id = medBillMainMapper.queryBillMainNextSeq();
			entityMap.put("bill_id", bill_id);
			int state = medBillMainMapper.addMedBillMain(entityMap);
			
			return "{\"msg1\":\"添加成功.\",\"state\":\"true\",\"update_para\":\""+
				entityMap.get("group_id").toString()+"|"+
				entityMap.get("hos_id").toString()+"|"+
				entityMap.get("copy_code").toString()+"|"+
				entityMap.get("bill_id").toString()+"\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"添加失败\"}");

		}
		
	}
	/**
	 * @Description 
	 * 批量添加保存采购发票的主要信息<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addBatchMedBillMain(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
			medBillMainMapper.addBatchMedBillMain(entityList);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"添加失败\"}");

		}
		
	}
	
		/**
	 * @Description 
	 * 更新保存采购发票的主要信息<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateMedBillMain(Map<String,Object> entityMap)throws DataAccessException{
		
		try {
			
			medBillDetailService.deleteMedBillDetail(entityMap);
		    int state = medBillMainMapper.updateMedBillMain(entityMap);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\",\"detail\":\"1\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"更新失败\"}");

		}	
		
	}
	/**
	 * @Description 
	 * 批量更新保存采购发票的主要信息<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateBatchMedBillMain(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
		  medBillMainMapper.updateBatchMedBillMain(entityList);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);
			
			throw new SysException("{\"error\":\"更新失败\"}");

		}	
		
	}
	/**
	 * @Description 
	 * 删除保存采购发票的主要信息<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
    public String deleteMedBillMain(Map<String, Object> entityMap) throws DataAccessException {
    	
    try {
			
			int state = medBillMainMapper.deleteMedBillMain(entityMap);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);
			
			throw new SysException("{\"error\":\"删除失败\"}");

		}	
		
  }
    
	/**
	 * @Description 
	 * 批量删除保存采购发票的主要信息<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String deleteBatchMedBillMain(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
			medBillMainMapper.deleteBatchMedBillMain(entityList);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"删除失败\"}");

		}	
	}
	
	/**
	 * @Description 
	 * 添加保存采购发票的主要信息<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addOrUpdateMedBillMain(Map<String,Object> entityMap)throws DataAccessException{
		
		//获取对象保存采购发票的主要信息
		MedBillMain medBillMain = queryMedBillMainByCode(entityMap);

		if (medBillMain != null) {

			int state = medBillMainMapper.updateMedBillMain(entityMap);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		
		try {
			
			int state = medBillMainMapper.addMedBillMain(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addOrUpdateMedBillMain\"}";

		}
		
	}
	/**
	 * @Description 
	 * 查询结果集保存采购发票的主要信息<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String queryMedBillMain(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<MedBillMain> list = medBillMainMapper.queryMedBillMain(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<MedBillMain> list = medBillMainMapper.queryMedBillMain(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
		
	}
	
	/**
	 * @Description 
	 * 获取对象保存采购发票的主要信息<BR> 
	 * @param  entityMap<BR>
	 *  参数为主键
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public MedBillMain queryMedBillMainByCode(Map<String,Object> entityMap)throws DataAccessException{
		return medBillMainMapper.queryMedBillMainByCode(entityMap);
	}
	
	/**
	 * @Description 
	 * 获取保存采购发票的主要信息<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return MedBillMain
	 * @throws DataAccessException
	*/
	@Override
	public MedBillMain queryMedBillMainByUniqueness(Map<String,Object> entityMap)throws DataAccessException{
		return medBillMainMapper.queryMedBillMainByUniqueness(entityMap);
	}
	/**
	 * 入库单/期初未付款送货单查询 （明细没有全部被发票引用过的采购入库单/期初未付款送货单）
	 * @param entityMap
	 * @return
	 */
	public String queryMedCommonInBill(Map<String, Object> entityMap) throws DataAccessException {
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		
		if (sysPage.getTotal()==-1){
			
			List<Map<String, Object>> list = medBillMainMapper.queryMedCommonInBill(entityMap);
			
			for(Map<String, Object> item : list){
				if(1 == Integer.valueOf(String.valueOf(item.get("init")))){
					item.put("table","MED_NOPAY_DELIVER_D" );
					// 明细数据 查询
					List<Map<String,Object>> detailList = medBillMainMapper.queryMedInDetail(item);
					// 存放  明细数据 
					item.put("detail", JSONObject.parseObject(ChdJson.toJson(detailList)));
					
				}else{
					if("1".equals(item.get("flag"))){
						item.put("table", "MED_AFFI_IN_DETAIL");
					}else{
						item.put("table", "MED_IN_MAIN");
					}
					List<Map<String,Object>> detailList = medBillMainMapper.queryMedInDetail(item);
					
					item.put("detail", JSONObject.parseObject(ChdJson.toJson(detailList)));
				}
				
			}
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<Map<String, Object>> list = medBillMainMapper.queryMedCommonInBill(entityMap, rowBounds);
			
			for(Map<String, Object> item : list){
				if(1 == Integer.valueOf(String.valueOf(item.get("init")))){
					item.put("table","MED_NOPAY_DELIVER_D" );
					// 明细数据 查询
					List<Map<String,Object>> detailList = medBillMainMapper.queryMedInDetail(item);
					// 存放  明细数据 
					item.put("detail", JSONObject.parseObject(ChdJson.toJson(detailList)));
					
				}else{
					item.put("table", "MED_IN_DETAIL");
					
					List<Map<String,Object>> detailList = medBillMainMapper.queryMedInDetail(item);
					
					item.put("detail", JSONObject.parseObject(ChdJson.toJson(detailList)));
				}
			}
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
		}
	}
	/**
	 * 入库单/期初未付款送货单 明细查询
	 * @param entityMap
	 * @return
	 */
	public String queryMedInDetail(Map<String, Object> entityMap) throws DataAccessException {
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<Map<String, Object>> list = medBillMainMapper.queryMedCommonInBill(entityMap);
			
			for(Map<String, Object> item : list){
				if(1 == Integer.valueOf(String.valueOf(item.get("init")))){
					item.put("table","MED_NOPAY_DELIVER_D" );
					// 明细数据 查询
					List<Map<String,Object>> detailList = medBillMainMapper.queryMedInDetail(item);
					// 存放  明细数据 
					item.put("detail", JSONObject.parseObject(ChdJson.toJson(detailList)));
					
				}else{
					if("1".equals(item.get("flag"))){
						item.put("table", "MED_AFFI_IN_DETAIL");
					}else{
						item.put("table", "MED_IN_MAIN");
					}
					List<Map<String,Object>> detailList = medBillMainMapper.queryMedInDetail(item);
					
					item.put("detail", JSONObject.parseObject(ChdJson.toJson(detailList)));
				}
				
			}
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<Map<String, Object>> list = medBillMainMapper.queryMedCommonInBill(entityMap, rowBounds);
			
			for(Map<String, Object> item : list){
				if(1 == Integer.valueOf(String.valueOf(item.get("init")))){
					item.put("table","MED_NOPAY_DELIVER_D" );
					// 明细数据 查询
					List<Map<String,Object>> detailList = medBillMainMapper.queryMedInDetail(item);
					// 存放  明细数据 
					item.put("detail", JSONObject.parseObject(ChdJson.toJson(detailList)));
					
				}else{
					item.put("table", "MED_IN_DETAIL");
					
					List<Map<String,Object>> detailList = medBillMainMapper.queryMedInDetail(item);
					
					item.put("detail", JSONObject.parseObject(ChdJson.toJson(detailList)));
				}
			}
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
		}
	}

	/**
	 * 新增时查询发票明细对应的发票主表的Id
	 * @return
	 */
	public Long queryBillMainMaxId() throws DataAccessException {
		return medBillMainMapper.queryBillMainMaxId();
	}
	/**
	 * 根据 入库单Id 查询入库单明细Id
	 * @param mapVo
	 * @return
	 */
	public List<Long> queryIn_detail_id(Map<String, Object> mapVo) throws DataAccessException {
		return medBillMainMapper.queryIn_detail_id(mapVo);
	}
	/**
	 * 发票号和发票日期更新到对应入库单
	 * @param entityList
	 * @return
	 */
	public String updateBatchMedIn(List<Map<String, Object>> entityList) throws DataAccessException {
		try {
				
			medBillMainMapper.updateBatchMedIn(entityList);
			medBillMainMapper.updateBatchMedSpecial(entityList);
			
			return "{\"msg\":\"操作成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"操作过程出错 ，操作失败请联系管理员! 方法 updateBatchMedIn\"}";
		}	
	}
	/**
	 * 多表联合查询 发票详细信息（修改页面回值用）
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public Map<String, Object> queryMedBillMainReturnUpdate(Map<String, Object> mapVo) throws DataAccessException {
		return medBillMainMapper.queryMedBillMainReturnUpdate(mapVo);
	}
	/**
	 * 审核、消审
	 * @param listVo
	 * @return
	 */
	public String updateBillState(List<Map<String, Object>> entityList) {
		try {
			
			  medBillMainMapper.updateBillState(entityList);
				
				return "{\"msg\":\"操作成功.\",\"state\":\"true\"}";

			}
			catch (Exception e) {

				logger.error(e.getMessage(), e);
				throw new SysException("{\"error\":\"操作失败\"}");

			}	
	}
	/**
	 * 生成发票流水号
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public String setBillCode(Map<String, Object> mapVo) throws DataAccessException {
		
		mapVo.put("table_code", "MED_BILL_MAIN");
		
		if(mapVo.get("bill_type").toString().equals("1")){
			mapVo.put("prefixe", "FP");
		}else{
			mapVo.put("prefixe", "FP");
		}
		
		// 判断该 类型 协议编码是否存在（存在取 max_no,不存在 则max_no=1 添加新记录 ）
		int count = medNoManageMapper.queryIsExists(mapVo);
		String max_no = "";
		if(count == 0){
			max_no = "1";
			mapVo.put("max_no", max_no);
			medNoManageMapper.add(mapVo);
		}else{
			//更新该业务流水码+1
			medNoManageMapper.updateMaxNo(mapVo);
			//取出该业务更新后的流水码
			max_no = medNoManageMapper.queryMaxCode(mapVo);
		}
		//补充流水号为4位（如：0001）
		for (int i = max_no.length() ; i < 4; i++) {
			max_no = "0" + max_no;
		}
		mapVo.put("max_no", max_no);
		return ChdJson.toJson(mapVo);
	}
	
	//入库模板打印（包含主从表）
		@Resource(name = "superPrintService")
		private final SuperPrintService superPrintService = null;
		@Override
		public String queryMedBillMainByPrintTemlate(Map<String, Object> entityMap)
				throws DataAccessException {
			// TODO Auto-generated method stub
			//查询入库主表
			try {
				if("1".equals(String.valueOf(entityMap.get("p_num")))){
					List<Map<String,Object>> map=medBillMainMapper.queryMedBillMainPrintTemlateByMainBatch(entityMap);
					List<Map<String,Object>> list=medBillMainMapper.queryMedBillMainPrintTemlateByDetail(entityMap);
					
					return superPrintService.getBatchListByPrintTemplateJson(entityMap,map,list);
				}else{
					Map<String,Object> map=medBillMainMapper.queryMedBillMainPrintTemlateByMain(entityMap);
					
					//查询入库明细表
					List<Map<String,Object>> list=medBillMainMapper.queryMedBillMainPrintTemlateByDetail(entityMap);
					return superPrintService.getMapListByPrintTemplateJson(entityMap,map,list);
				}
			} catch (Exception e) {
				logger.error(e.getMessage(),e);
				throw new SysException(e.getMessage());
			}
		
		}
		
		/**
		 * 新版打印
		 */
		@Override
		public Map<String,Object> queryMedBillMainByPrintPage(Map<String, Object> entityMap)
				throws DataAccessException {
			//查询入库主表
			try {
				Map<String,Object> reMap=new HashMap<String,Object>();
				WebApplicationContext context = ContextLoader.getCurrentWebApplicationContext();
				MedBillMainMapper medBillMainMapper = (MedBillMainMapper)context.getBean("medBillMainMapper");
				
				if("1".equals(String.valueOf(entityMap.get("p_num")))){
					List<Map<String,Object>> map=medBillMainMapper.queryMedBillMainPrintTemlateByMainBatch(entityMap);
					List<Map<String,Object>> list=medBillMainMapper.queryMedBillMainPrintTemlateByDetail(entityMap);
				
					reMap.put("main", map);
					reMap.put("detail", list);
					
					return reMap;
				}else{
					Map<String,Object> map=medBillMainMapper.queryMedBillMainPrintTemlateByMain(entityMap);
					
					//查询入库明细表
					List<Map<String,Object>> list=medBillMainMapper.queryMedBillMainPrintTemlateByDetail(entityMap);
					
					reMap.put("main", map);
					reMap.put("detail", list);
					
					return reMap;
				}
			} catch (Exception e) {
				logger.error(e.getMessage(),e);
				throw new SysException(e.getMessage());
			}
		
		}
		
		/**
		 * 查询 发票是否已付款
		 */
		@Override
		public int queryPayOrNot(Map<String, Object> mapVo) throws DataAccessException {
			
			return medBillMainMapper.queryPayOrNot(mapVo);
		}
		@Override
		public String updateMedBillMainBillMoney(Map<String, Object> entityMap)
				throws DataAccessException {
			try {
				
				  medBillMainMapper.updateMedBillMainBillMoney(entityMap);
					
					return "{\"msg\":\"操作成功.\",\"state\":\"true\"}";

				}
				catch (Exception e) {

					logger.error(e.getMessage(), e);
					throw new SysException("{\"error\":\"操作失败\"}");

				}	
		}
		
		@Override
		public String updateBatchMedInMain(List<Map<String, Object>> entityList) throws DataAccessException {
			try {
				String inIds = medBillMainMapper.queryMedBillInIds(entityList);
				if(!"".equals(inIds)){
					Map<String,Object> entityMap = new HashMap<String,Object>();
					entityMap.put("group_id", SessionManager.getGroupId());
					entityMap.put("hos_id", SessionManager.getHosId());
					entityMap.put("copy_code", SessionManager.getCopyCode());
					entityMap.put("inIds", inIds);
					medBillMainMapper.updateBatchMedInMain(entityMap);
				}
				
				return "{\"msg\":\"操作成功.\",\"state\":\"true\"}";
			}catch (Exception e) {
				logger.error(e.getMessage(), e);
				throw new SysException("{\"error\":\"操作失败\"}");
			}	
		}
}
