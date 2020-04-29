/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.ass.serviceImpl.tran.dept;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import com.chd.base.MyConfig;
import com.chd.base.SessionManager;
import com.chd.base.SysPage;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.hrp.acc.service.vouch.SuperPrintService;
import com.chd.hrp.ass.dao.HrpAssSelectMapper;
import com.chd.hrp.ass.dao.card.AssCardInassetsMapper;
import com.chd.hrp.ass.dao.share.AssShareDeptRInassetsMapper;
import com.chd.hrp.ass.dao.share.AssShareDeptInassetsMapper;
import com.chd.hrp.ass.dao.tran.dept.AssTranDeptDetailInassetsMapper;
import com.chd.hrp.ass.dao.tran.dept.AssTranDeptInassetsMapper;
import com.chd.hrp.ass.dao.tran.dept.AssTranDeptSpecialMapper;
import com.chd.hrp.ass.dao.tran.dept.AssTranDeptInassetsMapper;
import com.chd.hrp.ass.entity.tran.dept.AssTranDeptDetailInassets;
import com.chd.hrp.ass.entity.tran.dept.AssTranDeptInassets;
import com.chd.hrp.ass.entity.tran.dept.AssTranDeptInassets;
import com.chd.hrp.ass.service.base.AssBaseService;
import com.chd.hrp.ass.service.tran.dept.AssTranDeptInassetsService;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Description:
 * 050901 资产转移主表(科室到科室)_无形资产
 * @Table:
 * ASS_TRAN_DEPT_INASSETS
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

 
@Service("assTranDeptInassetsService")
public class AssTranDeptInassetsServiceImpl implements AssTranDeptInassetsService {

	private static Logger logger = Logger.getLogger(AssTranDeptInassetsServiceImpl.class);
	//引入DAO操作
		@Resource(name = "assTranDeptInassetsMapper")
		private final AssTranDeptInassetsMapper assTranDeptInassetsMapper = null;
		
		@Resource(name = "assTranDeptDetailInassetsMapper")
		private final AssTranDeptDetailInassetsMapper assTranDeptDetailInassetsMapper = null;
		
		@Resource(name = "assShareDeptInassetsMapper")
		private final AssShareDeptInassetsMapper assShareDeptInassetsMapper = null;
		
		@Resource(name = "assShareDeptRInassetsMapper")
		private final AssShareDeptRInassetsMapper assShareDeptRInassetsMapper = null;
		
		@Resource(name = "assBaseService")
		private final AssBaseService assBaseService = null;
		
		@Resource(name = "hrpAssSelectMapper")
		private final HrpAssSelectMapper hrpAssSelectMapper = null;
		
		@Resource(name = "assCardInassetsMapper")
		private final AssCardInassetsMapper assCardInassetsMapper = null;
	    
		/**
		 * @Description 
		 * 添加050901 资产转移主表(科室到科室)_无形资产<BR> 
		 * @param  entityMap
		 * @return String JSON
		 * @throws DataAccessException
		*/
		@Override
		public String add(Map<String,Object> entityMap)throws DataAccessException{
			
			//获取对象050901 资产转移主表(科室到科室)_无形资产
			AssTranDeptInassets assTranDeptInassets = queryByCode(entityMap);

			if (assTranDeptInassets != null) {

				return "{\"error\":\"数据重复,请重新添加.\"}";

			}
			
			try {
				
				int state = assTranDeptInassetsMapper.add(entityMap);
				
				return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

			}
			catch (Exception e) {

				logger.error(e.getMessage(), e);

				return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 add\"}";

			}
			
		}
		/**
		 * @Description 
		 * 批量添加050901 资产转移主表(科室到科室)_无形资产<BR> 
		 * @param  entityList
		 * @return String JSON
		 * @throws DataAccessException
		*/
		@Override
		public String addBatch(List<Map<String,Object>> entityList)throws DataAccessException{
			
			try {
				
				assTranDeptInassetsMapper.addBatch(entityList);
				
				return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

			}
			catch (Exception e) {

				logger.error(e.getMessage(), e);

				return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addBatch\"}";

			}
			
		}
		
			/**
		 * @Description 
		 * 更新050901 资产转移主表(科室到科室)_无形资产<BR> 
		 * @param  entityMap
		 * @return String JSON
		 * @throws DataAccessException
		*/
		@Override
		public String update(Map<String,Object> entityMap)throws DataAccessException{
			
			try {
				
			  int state = assTranDeptInassetsMapper.update(entityMap);
				
				return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

			}
			catch (Exception e) {

				logger.error(e.getMessage(), e);

				return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 update\"}";

			}	
			
		}
		/**
		 * @Description 
		 * 批量更新050901 资产转移主表(科室到科室)_无形资产<BR> 
		 * @param  entityList
		 * @return String JSON
		 * @throws DataAccessException
		*/
		@Override
		public String updateBatch(List<Map<String,Object>> entityList)throws DataAccessException{
			
			try {
				
			  assTranDeptInassetsMapper.updateBatch(entityList);
				
				return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

			}
			catch (Exception e) {

				logger.error(e.getMessage(), e);

				return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 updateBatch\"}";

			}	
			
		}
		/**
		 * @Description 
		 * 删除050901 资产转移主表(科室到科室)_无形资产<BR> 
		 * @param  entityMap
		 * @return String JSON
		 * @throws DataAccessException
		*/
		@Override
	    public String delete(Map<String, Object> entityMap) throws DataAccessException {
	    	
	    try {
	    		assTranDeptDetailInassetsMapper.delete(entityMap);
				int state = assTranDeptInassetsMapper.delete(entityMap);
				
				return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

			}
			catch (Exception e) {

				logger.error(e.getMessage(), e);

				return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 delete\"}";

			}	
			
	  }
	    
		/**
		 * @Description 
		 * 批量删除050901 资产转移主表(科室到科室)_无形资产<BR> 
		 * @param  entityList
		 * @return String JSON
		 * @throws DataAccessException
		*/
		@Override
		public String deleteBatch(List<Map<String,Object>> entityList)throws DataAccessException{
			
			try {
				assTranDeptDetailInassetsMapper.deleteBatch(entityList);
				assTranDeptInassetsMapper.deleteBatch(entityList);
				
				return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

			}
			catch (Exception e) {

				logger.error(e.getMessage(), e);

				return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 deleteBatch\"}";

			}	
		}
		
		/**
		 * @Description 
		 * 添加050901 资产转移主表(科室到科室)_无形资产<BR> 
		 * @param  entityMap
		 * @return String JSON
		 * @throws DataAccessException
		*/
		@Override
		public String addOrUpdate(Map<String,Object> entityMap)throws DataAccessException{
			List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();
			Map<String, Object> mapVo=new HashMap<String, Object>();
			mapVo.put("group_id",entityMap.get("group_id"));
			mapVo.put("hos_id",entityMap.get("hos_id"));
	    	mapVo.put("copy_code", entityMap.get("copy_code"));
	    	mapVo.put("tran_no", entityMap.get("tran_no"));
	    	
	    	Map<String, Object> vCreateDateMap = new HashMap<String, Object>();
	    	vCreateDateMap.put("group_id",entityMap.get("group_id"));
	    	vCreateDateMap.put("hos_id",entityMap.get("hos_id"));
			vCreateDateMap.put("copy_code", entityMap.get("copy_code"));
			vCreateDateMap.put("ass_nature", "05");
			vCreateDateMap.put("use_state", "1,2,3,4,5");
			vCreateDateMap.put("is_dept", "1");
			vCreateDateMap.put("in_date", entityMap.get("create_date"));
			
			Map<String, Object> vDeptMap = new HashMap<String, Object>();
			vDeptMap.put("group_id",entityMap.get("group_id"));
			vDeptMap.put("hos_id",entityMap.get("hos_id"));
			vDeptMap.put("copy_code", entityMap.get("copy_code"));
			vDeptMap.put("ass_nature", "05");
			vDeptMap.put("use_state", "1,2,3,4,5");
			vDeptMap.put("is_dept", "1");
			vDeptMap.put("dept_id", entityMap.get("out_dept_id"));
			entityMap.put("state", "0");
	    	
			List<AssTranDeptInassets> list = (List<AssTranDeptInassets>)assTranDeptInassetsMapper.queryExists(mapVo);
			
			try {
				if (list.size()>0) {
					
					assTranDeptInassetsMapper.update(entityMap);
				}else{
					if(entityMap.get("create_date") != null && !"".equals(entityMap.get("create_date"))){
						entityMap.put("year", entityMap.get("create_date").toString().substring(0,4));
						entityMap.put("month", entityMap.get("create_date").toString().substring(5,7));
					}
					entityMap.put("bill_table", "ASS_TRAN_DEPT_INASSETS");
					String tran_no = assBaseService.getBillNOSeqNo(entityMap);
					entityMap.put("tran_no", tran_no);
					assTranDeptInassetsMapper.add(entityMap);
					assBaseService.updateAssBillNoMaxNo(entityMap);
					
				}
				boolean flag = true;
				boolean flag1 = true;
				List<Map> detail = ChdJson.fromJsonAsList(Map.class, entityMap.get("ParamVo").toString());
				
				for (Map<String, Object> detailVo : detail) {
					if (detailVo.get("ass_card_no") == null || "".equals(detailVo.get("ass_card_no"))) {
						continue;
					}
					vCreateDateMap.put("ass_card_no", detailVo.get("ass_card_no"));
					vDeptMap.put("ass_card_no", detailVo.get("ass_card_no"));
					
					Integer createDateExists = hrpAssSelectMapper.queryAssExistsInassetsCard(vCreateDateMap);
					if(createDateExists == 0){
						flag = false;
						break;
					}
					
					Integer storeExists = hrpAssSelectMapper.queryAssExistsInassetsCard(vDeptMap);
					if(storeExists == 0){
						flag1 = false;
						break;
					}
					
					detailVo.put("group_id",entityMap.get("group_id"));
					detailVo.put("hos_id",entityMap.get("hos_id"));
					detailVo.put("copy_code", entityMap.get("copy_code"));
					detailVo.put("tran_no", entityMap.get("tran_no"));
					listVo.add(detailVo);
					
				}
				
				if(!flag){
					TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
					return "{\"warn\":\"存在尚未入账的卡片不能进行转移.\"}";
				}
				if(!flag1){
					TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
					return "{\"warn\":\"存在非本科室的卡片,不能转移.\"}";
				}
				
				assTranDeptDetailInassetsMapper.delete(entityMap);
				assTranDeptDetailInassetsMapper.addBatch(listVo);
				return "{\"msg\":\"保存成功.\",\"state\":\"true\",\"tran_no\":\""+entityMap.get("tran_no")+"\"}";
			}
			catch (Exception e) {

				logger.error(e.getMessage(), e);
				throw new SysException(e.getMessage());
			}
			
		}
		/**
		 * @Description 
		 * 查询结果集050901 资产转移主表(科室到科室)_无形资产<BR> 
		 * @param  entityMap
		 * @return String JSON
		 * @throws DataAccessException
		*/
		@Override
		public String query(Map<String,Object> entityMap) throws DataAccessException{
			
			SysPage sysPage = new SysPage();
			
			sysPage = (SysPage) entityMap.get("sysPage");
			
			if (sysPage.getTotal()==-1){
				
				List<AssTranDeptInassets> list = (List<AssTranDeptInassets>)assTranDeptInassetsMapper.query(entityMap);
				
				return ChdJson.toJson(list);
				
			}else{
				
				RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
				
				List<AssTranDeptInassets> list = (List<AssTranDeptInassets>)assTranDeptInassetsMapper.query(entityMap, rowBounds);
				
				PageInfo page = new PageInfo(list);
				
				return ChdJson.toJson(list, page.getTotal());
				
			}
			
		}
		
		/**
		 * @Description 
		 * 获取对象050901 资产转移主表(科室到科室)_无形资产<BR> 
		 * @param  entityMap<BR>
		 *  参数为主键
		 * @return String JSON
		 * @throws DataAccessException
		*/
		@Override
		public <T> T queryByCode(Map<String,Object> entityMap)throws DataAccessException{
			return assTranDeptInassetsMapper.queryByCode(entityMap);
		}
		
		/**
		 * @Description 
		 * 获取050901 资产转移主表(科室到科室)_无形资产<BR> 
		 * @param  entityMap<BR>
		 *  参数为要检索的字段
		 * @return AssTranDeptInassets
		 * @throws DataAccessException
		*/
		@Override
		public <T> T queryByUniqueness(Map<String,Object> entityMap)throws DataAccessException{
			return assTranDeptInassetsMapper.queryByUniqueness(entityMap);
		}
		
		/**
		 * @Description 
		 * 获取050901 资产转移主表(科室到科室)_无形资产<BR> 
		 * @param  entityMap<BR>
		 *  参数为要检索的字段
		 * @return List<AssTranDeptInassets>
		 * @throws DataAccessException
		*/
		@Override
		public List<?> queryExists(Map<String,Object> entityMap)throws DataAccessException{
			return assTranDeptInassetsMapper.queryExists(entityMap);
		}
		@Override
		public String updateConfirmTranDeptInassets(List<Map<String, Object>> entityMap,
				List<Map<String, Object>> cardEntityMap) throws DataAccessException {
			try {
				List<Map<String, Object>> cardList = new ArrayList<Map<String,Object>>();
				for(Map<String, Object> cardEntity : cardEntityMap){
					cardEntity.put("dept_id", cardEntity.get("in_dept_id"));
					cardEntity.put("dept_no", cardEntity.get("in_dept_no"));
					cardEntity.put("area", 1);
					cardEntity.put("ass_year", getAssYear());
					cardEntity.put("ass_month", getAssMonth());
					cardList.add(cardEntity);
					
				}
				//删除原来的使用科室
				assShareDeptRInassetsMapper.deleteBatchByAssYearMonth(cardEntityMap);
				assShareDeptInassetsMapper.deleteBatchByAssCardNo(cardEntityMap);
				
				//增加新的使用科室
				assShareDeptRInassetsMapper.addBatch(cardList);
				assShareDeptInassetsMapper.addBatch(cardList);
				
				//更新转移单据表
				assTranDeptInassetsMapper.updateConfirm(entityMap);
				assCardInassetsMapper.updateTranDeptConfirm(cardList);
				
				return "{\"msg\":\"确认成功.\",\"state\":\"true\"}";
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
		@Override
		public List<AssTranDeptDetailInassets> queryByTranNo(Map<String, Object> entityMap) {
			return assTranDeptDetailInassetsMapper.queryByTranNo(entityMap);
		}
	
		@Override
		public List<String> queryState(Map<String, Object> entityMap) throws DataAccessException {
			 return assTranDeptInassetsMapper.queryState(entityMap);
			  
		}
		
		//出库模板打印（包含主从表）
		@Resource(name = "superPrintService")
		private final SuperPrintService superPrintService = null;
		@Override
		public Map<String,Object> assTranDeptInassetsByPrintTemlate(Map<String, Object> entityMap)
				throws DataAccessException {
			// TODO Auto-generated method stub
			
			Map<String,Object> reMap=new HashMap<String,Object>();
			WebApplicationContext context = ContextLoader.getCurrentWebApplicationContext();
			AssTranDeptInassetsMapper assTranDeptInassetsMapper = (AssTranDeptInassetsMapper)context.getBean("assTranDeptInassetsMapper");
			
			
			if("1".equals(String.valueOf(entityMap.get("p_num")))){
				//查询移库主表批量
				List<Map<String,Object>> map = assTranDeptInassetsMapper.queryAssTranDeptInassetsPrintTemlateByMainBatch(entityMap);
				//查询移库明细表
				List<Map<String,Object>> list = assTranDeptInassetsMapper.queryAssTranDeptInassetsPrintTemlateByDetail(entityMap);
				
				reMap.put("main", map);
				reMap.put("detail", list);
				
				return reMap;
			}else{
				//查询移库主表
				Map<String,Object> map = assTranDeptInassetsMapper.queryAssTranDeptInassetsPrintTemlateByMain(entityMap);
				//查询移库明细表
				List<Map<String,Object>> list = assTranDeptInassetsMapper.queryAssTranDeptInassetsPrintTemlateByDetail(entityMap);
				
				reMap.put("main", map);
				reMap.put("detail", list);
				
				return reMap;
			}
			
		}
		@Override
		public String queryDetails(Map<String, Object> entityMap) {
			SysPage sysPage = new SysPage();

			sysPage = (SysPage) entityMap.get("sysPage");

			if (sysPage.getTotal() == -1) {

				List<Map<String, Object>> list = (List<Map<String, Object>>) assTranDeptInassetsMapper.queryDetails(entityMap);

				return ChdJson.toJson(list);

			} else {

				RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

				List<Map<String, Object>> list = (List<Map<String, Object>>) assTranDeptInassetsMapper.queryDetails(entityMap, rowBounds);

				PageInfo page = new PageInfo(list);

				return ChdJson.toJson(list, page.getTotal());

			}
		}
		
}
