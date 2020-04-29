package com.chd.hrp.mat.serviceImpl.storage.out;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.nutz.lang.Strings;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.chd.base.SysPage;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.base.util.JsonListMapUtil;
import com.chd.hrp.mat.dao.base.HrpMatSelectMapper;
import com.chd.hrp.mat.dao.base.MatCommonMapper;
import com.chd.hrp.mat.dao.storage.out.MatOutApplyNotStoreMapper;
import com.chd.hrp.mat.entity.MatApplyMain;
import com.chd.hrp.mat.service.base.MatCommonService;
import com.chd.hrp.mat.service.storage.out.MatOutApplyNotStoreService;
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
@Service("matOutApplyNotStoreService")
public class MatOutApplyNotStoreServiceImpl implements MatOutApplyNotStoreService {
		private static Logger logger = Logger.getLogger(MatOutApplyNotStoreServiceImpl.class);
		//引入DAO操作
		@Resource(name = "matOutApplyNotStoreMapper")
		private final MatOutApplyNotStoreMapper matOutApplyNotStoreMapper = null;
		@Resource(name = "matCommonMapper")
		private final MatCommonMapper matCommonMapper = null;
		@Resource(name = "matCommonService")
		private final MatCommonService matCommonService = null;
		@Resource(name = "hrpMatSelectMapper")
		private final HrpMatSelectMapper hrpMatSelectMapper = null;
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
				//判断存不存在此会计期间，如果不存在，提示请配置。
				int flag = matCommonMapper.existsAccYearMonthCheck(entityMap);
				if(flag == 0){
					return "{\"error\":\"添加失败，所选期间不存在请配置！\",\"state\":\"false\"}";
				}
				
				//自动生成科室申请单据号
				if("自动生成".equals(entityMap.get("apply_no"))){
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("year", entityMap.get("year"));
					map.put("month", entityMap.get("month"));
					map.put("table_code", "MAT_APPLY_MAIN");
					map.put("prefixe", "SQ");
					entityMap.put("apply_no", matCommonService.getMatNextNo(map));
				}
				//取出主表ID（自增序列）
				entityMap.put("apply_id", matOutApplyNotStoreMapper.queryMatApplyMainSeq());
				/*//获取仓库对应的材料集合
				List<Map<String, Object>> list= (List<Map<String, Object>>) matOutApplyNotStoreMapper.queryMatStoreInvs(entityMap);
				Set<String> set = new HashSet<String>(); 
				for (Map<String, Object> map : list) {
		            set.add(map.get("inv_id").toString());
	            }
				StringBuilder sb=new StringBuilder();*/
				//组装明细
				if(entityMap.get("detailData") != null && !"[]".equals(String.valueOf(entityMap.get("detailData")))){
					//保存明细数据
					JSONArray json = JSONArray.parseArray((String)entityMap.get("detailData"));
					List<Map<String,Object>> detailList = new ArrayList<Map<String,Object>>();
					Iterator it = json.iterator();
					while (it.hasNext()) {
						JSONObject jsonObj = JSONObject.parseObject(it.next().toString());
						if( !"".equals(jsonObj.get("inv_id")) && jsonObj.get("inv_id") != null){
							/*//判断该单据材料与所选仓库是否一致
							if(!set.contains(jsonObj.get("inv_id").toString())){
								//不一致给予提示
								sb.append(jsonObj.get("inv_code")).append(" ");
								continue;
							} */
							Map<String,Object> detailMap = getDetailMap();
							detailMap.put("group_id", entityMap.get("group_id").toString());
							detailMap.put("hos_id", entityMap.get("hos_id").toString());
							detailMap.put("copy_code", entityMap.get("copy_code").toString());
							detailMap.put("apply_id", entityMap.get("apply_id").toString());//主表ID
							detailMap.put("detail_id", String.valueOf(matOutApplyNotStoreMapper.queryMatApplyDetailSeq()));//明细表ID
							detailMap.put("inv_id",  jsonObj.get("inv_id").toString());//材料ID
							detailMap.put("inv_no",  jsonObj.get("inv_no").toString());//材料ID
							detailMap.put("app_amount",  jsonObj.get("app_amount").toString());//申请数量
							detailMap.put("note",  jsonObj.get("note"));//备注
							
							detailList.add(detailMap);
						}
					}
					
					/*if(null!=sb && !"".equals(sb.toString().trim())){
						return "{\"msg\":\"材料："+sb.toString()+" 与所选仓库不一致.\",\"state\":\"false\"}";
					}*/
					
					if(detailList.size() == 0){
						return "{\"error\":\"添加失败 明细数据为空\",\"state\":\"false\"}";
					}
					//新增入库主表数据
					matOutApplyNotStoreMapper.addMatApplyMain(entityMap);
					//新增入库明细数据
					matOutApplyNotStoreMapper.addMatApplyDetail(detailList);
				}else{
					return "{\"error\":\"添加失败 明细数据为空\",\"state\":\"false\"}";
				}
			}
			catch (Exception e) {
				logger.error(e.getMessage(), e);
				throw new SysException("{\"error\":\"添加失败\"}");
				//throw new SysException("{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addMatCommonOutApply\"}");
			}
			
			return "{\"msg1\":\"添加成功.\",\"state\":\"true\",\"update_para\":\""+
				entityMap.get("group_id").toString()+","+
				entityMap.get("hos_id").toString()+","+
				entityMap.get("copy_code").toString()+","+
				entityMap.get("apply_id").toString()+","
				+"\"}";
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
			}
			catch (DataAccessException e) {
				logger.error(e.getMessage(), e);

				throw new SysException("{\"error\":\"操作失败\"}");
				//return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addBatchMatCommonOutApply\"}";
			}
			//暂无该业务
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";
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
				if(entityMap.get("state") != null && !"".equals(entityMap.get("state")) && !"1".equals(entityMap.get("state"))){
					return "{\"error\":\"更新失败,单据状态不为未审核状态！.\",\"state\":\"false\"}";
				}
				//组装明细
				if(entityMap.get("detailData") != null && !"[]".equals(String.valueOf(entityMap.get("detailData")))){
					//修改明细数据
					JSONArray json = JSONArray.parseArray((String)entityMap.get("detailData"));
					
					//获取仓库对应的材料集合
					/*List<Map<String, Object>> list= (List<Map<String, Object>>) matOutApplyNotStoreMapper.queryMatStoreInvs(entityMap);
					Set<String> set = new HashSet<String>(); 
					for (Map<String, Object> map : list) {
			            set.add(map.get("inv_id").toString());
		            }
					StringBuilder sb=new StringBuilder();*/
					
					List<Map<String,Object>> detailList = new ArrayList<Map<String,Object>>();
					StringBuffer detail_ids = new StringBuffer();
					Iterator it = json.iterator();
					while (it.hasNext()) {
						JSONObject jsonObj = JSONObject.parseObject(it.next().toString());
						if( !"".equals(jsonObj.get("inv_id")) && jsonObj.get("inv_id") != null){
							/*//判断该单据材料与所选仓库是否一致
							if(!set.contains(jsonObj.get("inv_id").toString())){
								//不一致给予提示
								sb.append(jsonObj.get("inv_code")).append(" ");
								continue;
							}*/
							
							Map<String,Object> detailMap = getDetailMap();
							detailMap.put("group_id", entityMap.get("group_id").toString());
							detailMap.put("hos_id", entityMap.get("hos_id").toString());
							detailMap.put("copy_code", entityMap.get("copy_code").toString());
							detailMap.put("apply_id", entityMap.get("apply_id").toString());//主表ID
							detailMap.put("inv_id",  jsonObj.get("inv_id").toString());//材料ID
							detailMap.put("inv_no",  jsonObj.get("inv_no").toString());//材料变更号
							detailMap.put("app_amount",  jsonObj.get("app_amount").toString());//申请数量
							detailMap.put("note",  jsonObj.get("note"));//备注
							
							//明细表ID
							if(jsonObj.get("detail_id") == null || "".equals(jsonObj.get("detail_id"))){
								//获取明细ID
								detailMap.put("detail_id", String.valueOf(matOutApplyNotStoreMapper.queryMatApplyDetailSeq()));//明细表ID
								detailList.add(detailMap);
							}else{
								detailMap.put("detail_id", jsonObj.get("detail_id").toString());
								detailList.add(detailMap);
							}
						}
					}
					/*if(null!=sb && !"".equals(sb.toString().trim())){
						return "{\"msg\":\"材料："+sb.toString()+" 与所选仓库不一致.\",\"state\":\"false\"}";
					}*/
					
					if(detailList.size() == 0){
						return "{\"error\":\"添加失败 明细数据为空\",\"state\":\"false\"}";
					}
					//修改入库主表数据
					matOutApplyNotStoreMapper.updateMatApplyMain(entityMap);
					Map<String,Object> deleteMap = new HashMap<String,Object>();
					deleteMap.put("group_id", entityMap.get("group_id"));
					deleteMap.put("hos_id", entityMap.get("hos_id"));
					deleteMap.put("copy_code", entityMap.get("copy_code"));
					deleteMap.put("apply_id", entityMap.get("apply_id"));//主表ID
					
					//删除明细记录重新添加
					matOutApplyNotStoreMapper.deleteMatApplyDetail(deleteMap);
					matOutApplyNotStoreMapper.addMatApplyDetail(detailList);
					
				}else{
					return "{\"error\":\"更新失败，明细数据为空\",\"state\":\"false\"}";
				}
			}
			catch (DataAccessException e) {
				logger.error(e.getMessage(), e);
				throw new SysException("{\"error\":\"更新失败\"}");
				//return "{\"error\":\"更新失败 数据库异常 请联系管理员! 方法 updateMatCommonOutApply\"}";
			}		
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";
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
			}
			catch (DataAccessException e) {
				logger.error(e.getMessage(), e);
				
				throw new SysException("{\"error\":\"更新失败\"}");
				//return "{\"error\":\"更新失败 数据库异常 请联系管理员! 方法 updateBatchMatCommonOutApply\"}";
			}	
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";
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
				//状态不为新建不能删除，从页面判断
				//期初记账状态不为0的不能删除，从页面判断
				
				//删除明细
				matOutApplyNotStoreMapper.deleteMatApplyDetail(entityMap);
				//删除主表
				matOutApplyNotStoreMapper.deleteMatApplyMain(entityMap);
			}
			catch (DataAccessException e) {
				logger.error(e.getMessage(), e);
				
				throw new SysException("{\"error\":\"删除失败\"}");
				//return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 deleteMatCommonOutApply\"}";
			}	
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
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
				//状态不为新建不能删除，从页面判断
				//批量删除明细表
				matOutApplyNotStoreMapper.deleteMatApplyDetailBatch(entityList);
				//批量删除主表
				matOutApplyNotStoreMapper.deleteMatApplyMainBatch(entityList);
			}
			catch (DataAccessException e) {
				logger.error(e.getMessage(), e);
				throw new SysException("{\"error\":\"删除失败\"}");
				//return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 deleteBatchMatCommonOutApply\"}";
			}	
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		}

		@Override
		public String updateSendMatCommonOutApplyBatch(List<Map<String, Object>> entityMap) throws DataAccessException {
			
			try {	
				//批量审核
				matOutApplyNotStoreMapper.updateBySendBatch(entityMap);
			}
			catch (DataAccessException e) {
				logger.error(e.getMessage(), e);
				
				throw new SysException("{\"error\":\"操作失败\"}");
				//return "{\"error\":\"发送失败 数据库异常 请联系管理员! 方法 updateSendMatCommonOutApplyBatch\"}";
			}	
			
			return "{\"msg\":\"操作成功.\",\"state\":\"true\"}";
		}

		//取消发送
		@Override
		public String updateBackSendMatCommonOutApplyBatch(List<Map<String, Object>> entityMap) throws DataAccessException {
			
			try {	
				
				matOutApplyNotStoreMapper.updateBackBySendBatch(entityMap);
			}
			catch (Exception e) {
				logger.error(e.getMessage(), e);

				throw new SysException("{\"error\":\"操作失败\"}");
				//return "{\"error\":\"操作失败 数据库异常 请联系管理员! 方法 updateBackMatCommonOutApplyCheckBatch\"}";
			}	
			
			return "{\"msg\":\"操作成功.\",\"state\":\"true\"}";
		}

		@Override
		public String auditMatCommonOutApplyBatch(List<Map<String, Object>> entityMap) throws DataAccessException {
			
			try {	
				//批量审核
				matOutApplyNotStoreMapper.auditOrUnAuditBatch(entityMap);
			}
			catch (DataAccessException e) {
				logger.error(e.getMessage(), e);
				
				throw new SysException("{\"error\":\"操作失败\"}");
				//return "{\"error\":\"操作失败 数据库异常 请联系管理员! 方法 auditMatCommonOutApplyBatch\"}";
			}	
			
			return "{\"msg\":\"操作成功.\",\"state\":\"true\"}";
		}

		@Override
		public String unAuditMatCommonOutApplyBatch(List<Map<String, Object>> entityMap) throws DataAccessException {
			
			try {	
				//批量消审
				matOutApplyNotStoreMapper.auditOrUnAuditBatch(entityMap);
			}
			catch (DataAccessException e) {
				logger.error(e.getMessage(), e);
				
				throw new SysException("{\"error\":\"操作失败\"}");
				//return "{\"error\":\"操作失败 数据库异常 请联系管理员! 方法 unAuditMatCommonOutApplyBatch\"}";
			}	
			
			return "{\"msg\":\"操作成功.\",\"state\":\"true\"}";
		}

		@Override
		public String failedMatCommonOutApplyBatch(List<Map<String, Object>> entityMap) throws DataAccessException {
			
			try {	
				//批量消审
				matOutApplyNotStoreMapper.auditOrUnAuditBatch(entityMap);
			}
			catch (DataAccessException e) {
				logger.error(e.getMessage(), e);
				
				throw new SysException("{\"error\":\"操作失败\"}");
				//return "{\"error\":\"操作失败 数据库异常 请联系管理员! 方法 failedMatCommonOutApplyBatch\"}";
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

				int state = matOutApplyNotStoreMapper.update(entityMap);
				
				return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

			}
			
			try {
				
				int state = matOutApplyNotStoreMapper.add(entityMap);
				
				return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

			}
			catch (DataAccessException e) {

				logger.error(e.getMessage(), e);

				return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addOrUpdateMatCommonOutApply\"}";

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
			//System.out.println(entityMap.get("app_state"));
			SysPage sysPage = new SysPage();
			sysPage = (SysPage) entityMap.get("sysPage");
			if (sysPage.getTotal()==-1){
				List<?> list = matOutApplyNotStoreMapper.query(entityMap);
				
				return ChdJson.toJson(list);
			}else{
				RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
				List<?> list = matOutApplyNotStoreMapper.query(entityMap, rowBounds);
				PageInfo page = new PageInfo(list);
				
				return ChdJson.toJson(list, page.getTotal());
			}
		}
		
		/**
		 * @Description 
		 * 查询结果集科室申请  明细查询
		 * @param  entityMap
		 * @return String JSON
		 * @throws DataAccessException
		*/
		@Override
		public String queryNDetail(Map<String,Object> entityMap) throws DataAccessException{
			//System.out.println(entityMap.get("app_state"));
			SysPage sysPage = new SysPage();
			sysPage = (SysPage) entityMap.get("sysPage");
			if (sysPage.getTotal()==-1){
				List<Map<String,Object>> list = JsonListMapUtil.toListMapLower(matOutApplyNotStoreMapper.queryNDetail(entityMap));
				return ChdJson.toJson(list);
			}else{
				RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
				List<Map<String,Object>> list = matOutApplyNotStoreMapper.queryNDetail(entityMap, rowBounds);
				PageInfo page = new PageInfo(list);
				
				return ChdJson.toJson(JsonListMapUtil.toListMapLower(list), page.getTotal());
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
			return matOutApplyNotStoreMapper.queryMatApplyMainByCode(entityMap);
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

			List<?> list = matOutApplyNotStoreMapper.queryMatApplyDetailByCode(entityMap);
			
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
			return matOutApplyNotStoreMapper.queryByUniqueness(entityMap);
		}
		
		@Override
		public List<?> queryExists(Map<String, Object> entityMap)
				throws DataAccessException {
			// TODO Auto-generated method stub
			return null;
		}
		@Override
		public String queryMatch(Map<String, Object> entityMap) throws DataAccessException {
			
			List<?> list = matOutApplyNotStoreMapper.queryMatch(entityMap);
			
			return ChdJson.toJson(list);
		}
		
		public Map<String, Object> getDetailMap(){
			Map<String, Object> detailMap = new HashMap<String, Object>();
			detailMap.put("group_id", 0);
			detailMap.put("hos_id", 0);
			detailMap.put("copy_code", "");
			detailMap.put("apply_id", 0);
			detailMap.put("detail_id", 0);
			detailMap.put("inv_id", 0);
			detailMap.put("inv_no", 0);
			detailMap.put("app_amount", 0);
			detailMap.put("do_amount", 0);
			detailMap.put("through_amount", 0);
			detailMap.put("note", "");
			return detailMap;
		}
		
		/**
		 * 新增、修改操作 中途改变响应仓库时 查询询之前表格中已添加的材料 是否存在于当前选择的响应仓库中
		 */
		@Override
		public String queryStoreExistInv(List<Map<String, Object>> listVo)throws DataAccessException {
			String str = new String(); 
			for(Map<String,Object> item: listVo){
				int count = matOutApplyNotStoreMapper.queryStoreExistInv(item);
				if(count == 0){
					str += item.get("inv_code")+" "+item.get("inv_name")+"、";
				}
			}
			if(!Strings.isEmpty(str)){
				return"{\"info\":\"所选响应仓库以下材料:【"+str.substring(0, str.length()-1)+"】不存在.\",\"state\":\"false\"}";
			}else{
				return "{\"info\":\"校验通过.\",\"state\":\"true\"}";
			}
			
		}
}