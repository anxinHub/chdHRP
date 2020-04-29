
/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 package com.chd.hrp.mat.serviceImpl.info.basic;

import java.text.DateFormat;
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

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.chd.base.SessionManager;
import com.chd.base.SysPage;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.base.util.DateUtil;
import com.chd.base.util.JsonListMapUtil;
import com.chd.base.util.NumberUtil;
import com.chd.base.util.SpreadTableJSUtil;
import com.chd.hrp.mat.dao.info.basic.MatCertSupMapper;
import com.chd.hrp.mat.dao.info.basic.MatInvCertMapper;
import com.chd.hrp.mat.dao.info.basic.MatInvCertRelaMapper;
import com.chd.hrp.mat.entity.MatInvCert;
import com.chd.hrp.mat.entity.MatInvCertRela;
import com.chd.hrp.mat.service.info.basic.MatInvCertService;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Description:
 * MAT_INV_CERT
 * @Table:
 * MAT_INV_CERT
 * @Author: bell
 * @email:  bell@s-chd.com 
 * @Version: 1.0
 */

@Service("matInvCertService")
public class MatInvCertServiceImpl implements MatInvCertService {

	private static Logger logger = Logger.getLogger(MatInvCertServiceImpl.class);
	//引入DAO操作
	@Resource(name = "matInvCertMapper")
	private final MatInvCertMapper matInvCertMapper = null;
	
	@Resource(name = "matInvCertRelaMapper")
	private final MatInvCertRelaMapper matInvCertRelaMapper = null;
	
	@Resource(name = "matCertSupMapper")
	private final MatCertSupMapper matCertSupMapper = null;
	
	
    
	/**
	 * @Description 
	 * 添加MAT_INV_CERT<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 * @throws ParseException 
	*/
	@Override
	public String addMatInvCert(Map<String,Object> entityMap)throws DataAccessException, ParseException{
		//获取对象MAT_INV_CERT
		List<Map<String,Object>> orderRelaList = new ArrayList<Map<String,Object>>();
		
		//证件供应商对应关系List
		List<Map<String,Object>> certSupList = new ArrayList<Map<String,Object>>();
		
		List<MatInvCert> list = matInvCertMapper.queryMatInvCertByID(entityMap);
		if (list.size() >0) {
			return "{\"error\":\"证件编码和供应商重复,请核对后重新填写.\"}";
		}
		/*DateFormat fd = new SimpleDateFormat("yyyy-MM-dd");
		Date date = fd.parse((fd.format(new Date())));
		Date  enddate =fd.parse(String.valueOf(entityMap.get("end_date")));;
		if(date.before(enddate)){
			entityMap.put("cert_state", 0);
		}else{
			entityMap.put("cert_state", 1);
		}*/
		
	/*	Boolean flag = compare_date(String.valueOf(entityMap.get("start_date")),String.valueOf(entityMap.get("end_date")));
		if(flag){
			entityMap.put("cert_state", "1");
		}else{
			entityMap.put("cert_state", "0");
		}*/
		
		try {
			entityMap.put("cert_id", matInvCertMapper.queryMatInvCertNextId());
			
			if(entityMap.get("detailData") != null && !"".equals(entityMap.get("detailData"))){
				JSONArray json = JSONArray.parseArray((String)entityMap.get("detailData"));
				Iterator it = json.iterator();
				while (it.hasNext()) {
					JSONObject jsonObj = JSONObject.parseObject(it.next().toString());
					if( jsonObj.get("inv_id") != null && !"".equals(jsonObj.get("inv_id"))){
						Map<String,Object> relaMap = new HashMap<String,Object>();
						relaMap.put("group_id", entityMap.get("group_id"));
						relaMap.put("hos_id", entityMap.get("hos_id"));
						relaMap.put("copy_code", entityMap.get("copy_code"));
						relaMap.put("inv_id",  jsonObj.get("inv_id"));//材料ID
						relaMap.put("cert_id", entityMap.get("cert_id"));
						relaMap.put("cert_code", entityMap.get("cert_code"));
						orderRelaList.add(relaMap);
					}
				}
			}
			
			/*if(entityMap.get("supData") != null && !"".equals(entityMap.get("supData"))){
				JSONArray json = JSONArray.parseArray((String)entityMap.get("supData"));
				Iterator it = json.iterator();
				while (it.hasNext()) {
					JSONObject jsonObj = JSONObject.parseObject(it.next().toString());
					if( jsonObj.get("sup_id") != null && !"".equals(jsonObj.get("sup_id"))){
						Map<String,Object> supMap = new HashMap<String,Object>();
						supMap.put("group_id", entityMap.get("group_id"));
						supMap.put("hos_id", entityMap.get("hos_id"));
						supMap.put("copy_code", entityMap.get("copy_code"));
						supMap.put("sup_id",  jsonObj.get("sup_id"));//材料ID
						supMap.put("cert_id", entityMap.get("cert_id"));
						certSupList.add(supMap);
					}
				}
			}*/
			
			int state = matInvCertMapper.addMatInvCert(entityMap);
			if(orderRelaList.size()>0){
				matInvCertRelaMapper.addBatchMatInvCertRela(orderRelaList);
			}
			
			/*if(certSupList.size()>0){
				matCertSupMapper.delete(entityMap);
				matCertSupMapper.addBatch(certSupList);
			}*/
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";
		}catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"添加失败!\"}");
		}
		
	}
	/**
	 * @Description 
	 * <BR> 添加 证件及证件供应商对应关系
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addBatchMatCertSup(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			//添加用list
			List<Map<String,Object>> certList = new ArrayList<Map<String,Object>>();
			
			//更新用 list
			List<Map<String,Object>> updateList = new ArrayList<Map<String,Object>>();
			//证件供应商对应关系List
			List<Map<String,Object>> certSupList = new ArrayList<Map<String,Object>>();
			
			//材料证件对应关系List
			List<Map<String,Object>> invCertList = new ArrayList<Map<String,Object>>();
			
			String inv_id = "";
			
			String certIds ="";
			
			for(Map<String,Object> item: entityList){
				
				MatInvCert invCert = matInvCertMapper.queryMatInvCertByCode(item);
				
				if( invCert == null){
					item.put("cert_id", matInvCertMapper.queryMatInvCertNextId());
					item.put("start_date", String.valueOf(item.get("start_date")).substring(0, 10));
					item.put("end_date", String.valueOf(item.get("end_date")).substring(0, 10));
					certList.add(item);
					certIds += item.get("cert_id") + "@"+item.get("cert_code") +",";
				}else{
					item.put("cert_id", String.valueOf(invCert.getCert_id()));
					item.put("start_date", String.valueOf(item.get("start_date")).substring(0, 10));
					item.put("end_date", String.valueOf(item.get("end_date")).substring(0, 10));
					updateList.add(item);
					certIds += invCert.getCert_id() +"@"+invCert.getCert_code() + ",";
				}
				
				/*if(item.get("supData") != null && !"".equals(item.get("supData"))){
					JSONArray json = JSONArray.parseArray(JSON.toJSONString(JSONObject.parseObject(String.valueOf(item.get("supData"))).get("Rows")));
					Iterator it = json.iterator();
					while (it.hasNext()) {
						JSONObject jsonObj = JSONObject.parseObject(it.next().toString());
						if( jsonObj.get("sup_id") != null && !"".equals(jsonObj.get("sup_id"))){
							
							Map<String,Object> supMap = new HashMap<String,Object>();
							supMap.put("group_id", item.get("group_id"));
							supMap.put("hos_id", item.get("hos_id"));
							supMap.put("copy_code", item.get("copy_code"));
							supMap.put("sup_id",  String.valueOf(jsonObj.get("sup_id")));
							supMap.put("cert_id", String.valueOf(item.get("cert_id")));
							certSupList.add(supMap);
						}
					}
				}*/
				
				//物资材料主页面 修改证件信息后 重新设置 材料证件关系  （只用于 物资材料主页面 修改证件信息）
				if(item.get("inv_id") != null && !"".equals(item.get("inv_id"))){
					
					Map<String,Object> invCertMap = new HashMap<String,Object>();
					invCertMap.put("group_id", item.get("group_id"));
					invCertMap.put("hos_id", item.get("hos_id"));
					invCertMap.put("copy_code", item.get("copy_code"));
					invCertMap.put("inv_id",  String.valueOf(item.get("inv_id")));
					invCertMap.put("cert_id", String.valueOf(item.get("cert_id")));
					invCertMap.put("cert_code", String.valueOf(item.get("cert_code")));
					invCertMap.put("cert_inv_name", String.valueOf(item.get("cert_inv_name")));
					
					inv_id = String.valueOf(item.get("inv_id")) ;//记录材料Id(物资材料材料主页面  修改证件时 删除材料证件 关系用)
					
					invCertList.add(invCertMap);
				}
				
			}
			
			Map<String,Object> delMap = new HashMap<String,Object>();
			
			if(inv_id != ""){
				delMap.put("group_id", SessionManager.getGroupId());
				delMap.put("hos_id", SessionManager.getHosId());
				delMap.put("copy_code", SessionManager.getCopyCode());
				delMap.put("inv_id",  inv_id);
			}
			
			
			if(certList.size() > 0){
				
				matInvCertMapper.addBatchMatInvCert(certList);
			}
			
			if(updateList.size() > 0){
				
				matInvCertMapper.updateBatchMatInvCert(updateList);
			}
			
			if(invCertList.size() > 0 ){
				matInvCertRelaMapper.deleteMatInvCertRela(delMap);
				
				matInvCertRelaMapper.addBatchMatInvCertRela(invCertList);
			}
			
			/*if(certSupList.size()>0){
				matCertSupMapper.deleteBatch(entityList);
				matCertSupMapper.addBatch(certSupList);
			}*/
			
			return "{\"msg\":\"操作成功.\",\"state\":\"true\",\"certIds\":\""+certIds+"\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"操作失败!\"}");
			//return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addBatchMatInvCert\"}";

		}
		
	}
	
		/**
	 * @Description 
	 * 更新MAT_INV_CERT<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
		 * @throws ParseException 
	*/
	@Override
	public String updateMatInvCert(Map<String,Object> entityMap)throws DataAccessException, ParseException{
		List<Map<String,Object>> insertList = new ArrayList<Map<String,Object>>();
		List<Map<String,Object>> updateList = new ArrayList<Map<String,Object>>();
		
		//证件供应商对应关系 List
		List<Map<String,Object>> certSupList = new ArrayList<Map<String,Object>>();
		
		List<MatInvCert> list = matInvCertMapper.queryMatInvCertByID(entityMap);
		if (list.size() >0) {
			return "{\"error\":\"证件编码重复,请核对后重新填写.\"}";
		}
		
		/*DateFormat fd = new SimpleDateFormat("yyyy-MM-dd");
		Date date = fd.parse((fd.format(new Date())));
		Date enddate = fd.parse(String.valueOf(entityMap.get("end_date")));
		
		Boolean flag = compare_date(String.valueOf(entityMap.get("start_date")),String.valueOf(entityMap.get("end_date")));
		if(flag){
			entityMap.put("cert_state", "1");
		}else{
			entityMap.put("cert_state", "0");
		}*/
		
		try {
			
			if(entityMap.get("insertData") != null && !"".equals(entityMap.get("insertData"))){
				JSONArray json = JSONArray.parseArray((String)entityMap.get("insertData"));
				
				Iterator it = json.iterator();
				while (it.hasNext()) {
					JSONObject jsonObj = JSONObject.parseObject(it.next().toString());
					if( jsonObj.get("inv_id") != null && !"".equals(jsonObj.get("inv_id"))){
						Map<String,Object> relaMap = new HashMap<String,Object>();
						relaMap.put("group_id", entityMap.get("group_id"));
						relaMap.put("hos_id", entityMap.get("hos_id"));
						relaMap.put("copy_code", entityMap.get("copy_code"));
						relaMap.put("inv_id",  jsonObj.get("inv_id"));//材料ID
						relaMap.put("cert_id", entityMap.get("cert_id"));
						relaMap.put("cert_code", entityMap.get("cert_code"));
						insertList.add(relaMap);
					}
				}
			}
			
			if(entityMap.get("updateData") != null && !"".equals(entityMap.get("updateData"))){
				JSONArray json = JSONArray.parseArray((String)entityMap.get("updateData"));
				
				Iterator it = json.iterator();
				while (it.hasNext()) {
					JSONObject jsonObj = JSONObject.parseObject(it.next().toString());
					if( jsonObj.get("inv_id") != null && !"".equals(jsonObj.get("inv_id"))){
						Map<String,Object> deleteMap = new HashMap<String,Object>();
						Map<String,Object> insertMap = new HashMap<String,Object>();
						
						if(!jsonObj.get("inv_id").equals(jsonObj.get("invid"))){
							deleteMap.put("group_id", entityMap.get("group_id"));
							deleteMap.put("hos_id", entityMap.get("hos_id"));
							deleteMap.put("copy_code", entityMap.get("copy_code"));
							deleteMap.put("cert_id", entityMap.get("cert_id"));
							deleteMap.put("cert_code", entityMap.get("cert_code"));
							deleteMap.put("cert_inv_name", entityMap.get("cert_inv_name"));
							deleteMap.put("inv_id", jsonObj.get("invid"));
							updateList.add(deleteMap);
							
							insertMap.put("group_id", entityMap.get("group_id"));
							insertMap.put("hos_id", entityMap.get("hos_id"));
							insertMap.put("copy_code", entityMap.get("copy_code"));
							insertMap.put("cert_id", entityMap.get("cert_id"));
							insertMap.put("cert_code", entityMap.get("cert_code"));
							insertMap.put("cert_inv_name", entityMap.get("cert_inv_name"));
							insertMap.put("inv_id", jsonObj.get("inv_id"));
							insertList.add(insertMap);
						}
					}
				}
			}
			
			/*if(entityMap.get("supData") != null && !"".equals(entityMap.get("supData"))){
				
				JSONArray json = JSONArray.parseArray((String)entityMap.get("supData"));
				
				Iterator it = json.iterator();
				while (it.hasNext()) {
					JSONObject jsonObj = JSONObject.parseObject(it.next().toString());
					if( jsonObj.get("sup_id") != null && !"".equals(jsonObj.get("sup_id"))){
						Map<String,Object> supMap = new HashMap<String,Object>();
						supMap.put("group_id", entityMap.get("group_id"));
						supMap.put("hos_id", entityMap.get("hos_id"));
						supMap.put("copy_code", entityMap.get("copy_code"));
						supMap.put("sup_id",  jsonObj.get("sup_id").toString());//材料ID
						supMap.put("cert_id", entityMap.get("cert_id").toString());
						certSupList.add(supMap);
					}
				}
			}*/
			
			int state = matInvCertMapper.updateMatInvCert(entityMap);
			if(insertList.size()>0){
				matInvCertRelaMapper.addBatchMatInvCertRela(insertList);
			}
			if(updateList.size()>0){
				matInvCertRelaMapper.deleteBatchMatInvCertRela(updateList);
			}
			
			/*matCertSupMapper.delete(entityMap);
			if(certSupList.size()>0){
				matCertSupMapper.addBatch(certSupList);
			}*/
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";
		}catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"修改失败!\"}");
		}	
		
	}
	/**
	 * @Description 
	 * 批量更新MAT_INV_CERT<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateBatchMatInvCert(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
		  matInvCertMapper.updateBatchMatInvCert(entityList);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 updateBatchMatInvCert\"}";

		}	
		
	}
	/**
	 * @Description 
	 * 删除MAT_INV_CERT<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
    public String deleteMatInvCert(Map<String, Object> entityMap) throws DataAccessException {
    	
	    try {
				matInvCertMapper.deleteMatInvCert(entityMap);
				//matInvCertRelaMapper.deleteMatInvCertRela(entityMap);
				return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
			}catch (Exception e) {
				logger.error(e.getMessage(), e);
				throw new SysException("{\"error\":\"删除失败!\"}");
			}	
		
  }
    
	/**
	 * @Description 
	 * 批量删除MAT_INV_CERT<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String deleteBatchMatInvCert(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
			matCertSupMapper.deleteBatch(entityList);
			
			matInvCertRelaMapper.deleteBatchMatInvCertRela(entityList);
			
			matInvCertMapper.deleteBatchMatInvCert(entityList);
			
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		}catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"删除失败!\"}");
		}	
	}
	
	/**
	 * @Description 
	 * 查询结果集MAT_INV_CERT<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String queryMatInvCert(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<MatInvCert> list = matInvCertMapper.queryMatInvCertNew(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<MatInvCert> list = matInvCertMapper.queryMatInvCertNew(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
		
	}
	
	/**
	 * @Description 
	 * 获取对象MAT_INV_CERT<BR> 
	 * @param  entityMap<BR>
	 *  参数为主键
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public MatInvCert queryMatInvCertByCode(Map<String,Object> entityMap)throws DataAccessException{
		return matInvCertMapper.queryMatInvCertByCode(entityMap);
	}
	
	/**
	 * @Description 
	 * 获取MAT_INV_CERT<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return MatInvCert
	 * @throws DataAccessException
	*/
	@Override
	public MatInvCert queryMatInvCertByUniqueness(Map<String,Object> entityMap) throws DataAccessException{
		return matInvCertMapper.queryMatInvCertByUniqueness(entityMap);
	}
	/**
	 * 修改时 查询获取MAT_INV_CERT<BR>（页面回值） 
	 * @param mapVo
	 * @return
	 */
	@Override
	public MatInvCert queryMatInvCertByCodeNew(Map<String, Object> mapVo) throws DataAccessException{
		return matInvCertMapper.queryMatInvCertByCodeNew(mapVo);
	}
	/**
	 * 查询 MatInvCert（判断证件编号 是否已经存在）
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	@Override
	public List<MatInvCert> queryMatInvCertByID(Map<String, Object> mapVo) throws DataAccessException{
		return matInvCertMapper.queryMatInvCertByID(mapVo);
	}
	/**
	 * 查询MatInvCert 数据是否被引用（引用不允许删除）
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	@Override
	public int queryDelDate(Map<String, Object> mapVo) throws DataAccessException{
		return matInvCertMapper.queryDelDate(mapVo);
	}
	
	/**
	 * 获取材料列表
	 */
	@Override
	public String queryMatCertInvList(Map<String, Object> entityMap) throws DataAccessException{
		SysPage sysPage = new SysPage();
		sysPage = (SysPage) entityMap.get("sysPage");
		entityMap.put("user_id", SessionManager.getUserId());
		
		if (sysPage.getTotal()==-1){
			List<Map<String,Object>> list = JsonListMapUtil.toListMapLower(matInvCertMapper.queryMatCertInvList(entityMap));
			return ChdJson.toJson(list);
		}else{
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<Map<String,Object>> list = matInvCertMapper.queryMatCertInvList(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(JsonListMapUtil.toListMapLower(list), page.getTotal());
		}
	}
	
	/**
	 * 根绝cert_id查询对应关系明细数据
	 */
	@Override
	public String queryMatInvCertDetail(Map<String, Object> entityMap) throws DataAccessException{
		SysPage sysPage = new SysPage();
		sysPage = (SysPage) entityMap.get("sysPage");
		if (sysPage.getTotal()==-1){
			List<Map<String,Object>> list = JsonListMapUtil.toListMapLower(matInvCertRelaMapper.queryMatInvCertDetail(entityMap));
			return ChdJson.toJson(list);
		}else{
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			List<Map<String,Object>> list = matInvCertRelaMapper.queryMatInvCertDetail(entityMap, rowBounds);
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(JsonListMapUtil.toListMapLower(list), page.getTotal());
		}
		
	}
	
	/**
	 * 比较日期大小
	 * @param date1
	 * @param date2
	 * @return
	 */
	public Boolean compare_date(String date1,String date2){
		Boolean flag = false;
		
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		String nowDate = df.format(new Date());
        try {
            Date now = df.parse(nowDate);
            Date dt2 = df.parse(date1);
            Date dt3 = df.parse(date2);
            if(now.getTime() >= dt2.getTime() && now.getTime() < dt3.getTime()){
            	flag = true;
            }else{
            	flag = false;
            }
            
        } catch (Exception exception) {
            exception.printStackTrace();
        }
       return flag;
	}
	/**
	 * 删除对应关系
	 * @param listVo
	 * @return
	 */
	@Override
	public String deleteMatInvCertRela(List<Map<String, Object>> entityList) throws DataAccessException{
		
		try {
			matInvCertRelaMapper.deleteBatchMatInvCertRela(entityList);
			//matInvCertRelaMapper.deleteBatchMatInvCertRela(entityList);
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		}catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"删除失败!\"}");
		}	
		
	}
	
	/**
	 * 查询 选择的材料
	 * @param listVo
	 * @return
	 */
	@Override
	public String queryMatCertInvChoiceInvList(Map<String, Object> entityMap) throws DataAccessException {

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
				detailMap.put("inv_id", jsonObj.get("inv_id"));
				detailMap.put("inv_no", jsonObj.get("inv_no"));
				detailList.add(detailMap);
			}
		}
		
		List<Map<String, Object>> list= matInvCertMapper.queryMatCertInvChoiceInvList(detailList);
		return ChdJson.toJsonLower(list);
	}
	
	/**
	 * 查询 证件供应商对应关系
	 * @param page
	 * @return
	 */
	@Override
	public String queryMatCerSup(Map<String, Object> entityMap) {
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<Map<String,Object>> list = (List<Map<String, Object>>) matCertSupMapper.query(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<Map<String,Object>> list = (List<Map<String, Object>>) matCertSupMapper.query(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
		}
	}
	
	/**
	 * @Description 
	 * 批量审核<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	@Override
	public String auditMatInvCertBatch(List<Map<String, Object>> entityMap) throws DataAccessException {
		
		try {	
			//批量审核
			matInvCertMapper.auditOrUnAuditBatch(entityMap);
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);

			throw new SysException("{\"error\":\"操作失败\"}");
			//return "{\"error\":\"操作失败 数据库异常 请联系管理员! 方法 auditMatStorageInBatch\"}";
		}	
		
		return "{\"msg\":\"操作成功.\",\"state\":\"true\"}";
	}
	
	/**
	 * @Description 
	 * 批量消审<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	@Override
	public String unAuditMatInvCertBatch(List<Map<String, Object>> entityMap) throws DataAccessException {
		
		try {	
			//批量消审
			matInvCertMapper.auditOrUnAuditBatch(entityMap);
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);

			throw new SysException("{\"error\":\"操作失败\"}");
			//return "{\"error\":\"操作失败 数据库异常 请联系管理员! 方法 unAuditMatStorageInBatch\"}";
		}	
		
		return "{\"msg\":\"操作成功.\",\"state\":\"true\"}";
	}
	
	/**
	 * @Description 
	 * 导入<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	@Override
	public String importData(Map<String, Object> entityMap)throws DataAccessException{
		
		try {	
			List<Map<String, Object>> certList = new ArrayList<Map<String,Object>>();
			List<Map<String, Object>> invRelaList = new ArrayList<Map<String,Object>>();
			List<Map<String, Object>> supRelaList = new ArrayList<Map<String,Object>>();
			String group_id = SessionManager.getGroupId();
			String hos_id = SessionManager.getHosId();
			String copy_code = SessionManager.getCopyCode();
			String user_id = SessionManager.getUserId();
			
			Map<String, Object> seachMap = new HashMap<String, Object>();
			seachMap.put("group_id", group_id);
			seachMap.put("hos_id", hos_id);
			seachMap.put("copy_code", copy_code);
			seachMap.put("user_id", user_id);
			
			//证件列表
			List<Map<String, Object>> certRList = matInvCertMapper.queryCertListForImport(seachMap);
			Map<String, Map<String, Object>> certRMap = new HashMap<String, Map<String, Object>>();
			for(Map<String, Object> certR : certRList){
				if(certR.get("CERT_CODE") != null){
					certRMap.put(certR.get("CERT_CODE").toString(), certR);
				}
			}
			
			//证件类别列表
			List<Map<String, Object>> certTypeList = matInvCertMapper.queryCertTypeListForImport(seachMap);
			Map<String, Map<String, Object>> certTypeMap = new HashMap<String, Map<String, Object>>();
			for(Map<String, Object> certType : certTypeList){
				if(certType.get("TYPE_CODE") != null){
					certTypeMap.put(certType.get("TYPE_CODE").toString(), certType);
				}
			}
			
			//生产厂商列表
			List<Map<String, Object>> facList = matInvCertMapper.queryFacListForImport(seachMap);
			Map<String, Map<String, Object>> facMap = new HashMap<String, Map<String, Object>>();
			for(Map<String, Object> fac : facList){
				if(fac.get("FAC_CODE") != null){
					facMap.put(fac.get("FAC_CODE").toString(), fac);
				}
			}
			
			//材料列表
			List<Map<String, Object>> invList = matInvCertMapper.queryInvListForImport(seachMap);
			Map<String, Map<String, Object>> invMap = new HashMap<String, Map<String, Object>>();
			for(Map<String, Object> inv : invList){
				if(inv.get("INV_CODE") != null){
					invMap.put(inv.get("INV_CODE").toString(), inv);
				}
			}
			
			//供应商列表
			List<Map<String, Object>> supList = matInvCertMapper.querySupListForImport(seachMap);
			Map<String, Map<String, Object>> supMap = new HashMap<String, Map<String, Object>>();
			for(Map<String, Object> sup : supList){
				if(sup.get("SUP_CODE") != null){
					supMap.put(sup.get("SUP_CODE").toString(), sup);
				}
			}
			
			//存放错误信息
			StringBuffer errorJson = new StringBuffer();
			
			//解析前台数据
			String data = entityMap.get("data").toString();
			List<Map<String, List<String>>> dataList = SpreadTableJSUtil.toListMap(data, 1);
			if(dataList==null || dataList.size()==0){
				return "{\"error\":\"没有数据！\",\"state\":\"false\"}";
			}
			String type_code;
			String fac_code;
			String inv_code;
			String inv_id;
			String sup_code;
			String sup_id;
			List<String> rowList=null;
			
			Map<String, Map<String, Object>> certMap = new HashMap<String, Map<String,Object>>();
			String certKey;
			Map<String, Map<String, Object>> invRelaMap = new HashMap<String, Map<String,Object>>();
			String invRelaKey;
			Map<String, Map<String, Object>> supRelaMap = new HashMap<String, Map<String,Object>>();
			String supRelaKey;
			for(Map<String, List<String>> dataMap : dataList){
				Map<String, Object> map = new HashMap<String, Object>();
				Map<String, Object> invRMap = new HashMap<String, Object>();
				Map<String, Object> supRMap = new HashMap<String, Object>();
				
				/**校验证件号************begin*****************/
				rowList = dataMap.get("证件号");
				if(rowList == null || rowList.get(1) == null || "".equals(rowList.get(1))){
					return "{\"warn\":\"" + rowList.get(0) + "，证件号为空！\",\"state\":\"false\",\"row_cell\":\"" + rowList.get(0) + "\"}";
				}
				
				certKey = rowList.get(1);
				if(certRMap.containsKey(certKey)){
					return "{\"warn\":\"" + rowList.get(0) + "，证件号重复！\",\"state\":\"false\",\"row_cell\":\"" + rowList.get(0) + "\"}";
					
				}
				/**校验证件号************end*******************/
				
				if(certRMap.containsKey(certKey)){
					map.put("cert_id", certRMap.get(certKey).get("CERT_ID"));
				}else{
					if(certMap.containsKey(certKey)){
						map = certMap.get(certKey);
					}else{
						//验证通过存放证件号
						map.put("cert_code", rowList.get(1));
						
						/**校验证件产品名称******begin*****************/
						rowList = dataMap.get("证件产品名称");
						if(rowList != null && rowList.get(1) != null && !"".equals(rowList.get(1))){
							map.put("cert_inv_name", rowList.get(1));
						}else{
							map.put("cert_inv_name", "");
						}
						/**校验证件产品名称******end*******************/
						
						/**校验证件类型**************begin*****************/
						rowList = dataMap.get("证件类型编码");
						if(rowList == null || rowList.get(1) == null || "".equals(rowList.get(1))){
							return "{\"warn\":\"" + rowList.get(0) + "，证件类型编码为空！\",\"state\":\"false\",\"row_cell\":\"" + rowList.get(0) + "\"}";
						}
						type_code = rowList.get(1);
						Map<String, Object> v_certType = certTypeMap.get(type_code);
						if(v_certType == null){
							return "{\"warn\":\"" + rowList.get(0) + "，证件类型编码不存在！\",\"state\":\"false\",\"row_cell\":\"" + rowList.get(0) + "\"}";
						}
						//验证通过存放证件类型ID
						map.put("type_id", v_certType.get("TYPE_ID"));
						/**校验证件类型**********end*******************/
						
						/**校验生产厂商编码******begin*****************/
						rowList = dataMap.get("生产厂商编码");
						if(rowList != null && rowList.get(1) != null && !"".equals(rowList.get(1))){
							fac_code = rowList.get(1);
							Map<String, Object> v_fac = facMap.get(fac_code);
							if(v_fac == null){
								return "{\"warn\":\"" + rowList.get(0) + "，生产厂商编码不存在！\",\"state\":\"false\",\"row_cell\":\"" + rowList.get(0) + "\"}";
							}
							//验证通过存放证件类型ID
							map.put("fac_id", v_fac.get("FAC_ID"));
						}else{
							map.put("fac_id", null);
						}
						/**校验生产厂商编码******end*******************/
		
						/**校验起始日期**********begin*****************/
						rowList = dataMap.get("起始日期");
						if(rowList == null || rowList.get(1) == null || "".equals(rowList.get(1))){
							return "{\"warn\":\"" + rowList.get(0) + "，起始日期为空！\",\"state\":\"false\",\"row_cell\":\"" + rowList.get(0) + "\"}";
						}
						//验证通过存放日期
						map.put("start_date", rowList.get(1));
						/**校验起始日期**********end*******************/
		
						/**校验截止日期**********begin*****************/
						rowList = dataMap.get("截止日期");
						if(rowList == null || rowList.get(1) == null || "".equals(rowList.get(1))){
							return "{\"warn\":\"" + rowList.get(0) + "，截止日期为空！\",\"state\":\"false\",\"row_cell\":\"" + rowList.get(0) + "\"}";
						}
						//验证通过存放日期
						map.put("end_date", rowList.get(1));
						/**校验截止日期**********end*******************/
						
						/**校验发证日期**********begin*****************/
						rowList = dataMap.get("发证日期");
						if(rowList != null && rowList.get(1) != null && !"".equals(rowList.get(1))){
							map.put("cert_date", rowList.get(1));
						}else{
							map.put("cert_date", "");
						}
						/**校验发证日期**********end*******************/
						
						/**校验联系人************begin*****************/
						rowList = dataMap.get("联系人");
						if(rowList != null && rowList.get(1) != null && !"".equals(rowList.get(1))){
							map.put("link_person", rowList.get(1));
						}else{
							map.put("link_person", "");
						}
						/**校验联系人************end*******************/
						
						/**校验包装规格**********begin*****************/
						rowList = dataMap.get("包装规格");
						if(rowList != null && rowList.get(1) != null && !"".equals(rowList.get(1))){
							map.put("issuing_authority", rowList.get(1));
						}else{
							map.put("issuing_authority", "");
						}
						/**校验包装规格**********end*******************/
						
						/**校验手机号************begin*****************/
						rowList = dataMap.get("手机号");
						if(rowList != null && rowList.get(1) != null && !"".equals(rowList.get(1))){
							map.put("link_mobile", rowList.get(1));
						}else{
							map.put("link_mobile", "");
						}
						/**校验手机号************end*******************/
						
						/**校验联系电话**********begin*****************/
						rowList = dataMap.get("联系电话");
						if(rowList != null && rowList.get(1) != null && !"".equals(rowList.get(1))){
							map.put("link_tel", rowList.get(1));
						}else{
							map.put("link_tel", "");
						}
						/**校验联系电话**********end*******************/
						
						/**校验存档位置**********begin*****************/
						rowList = dataMap.get("存档位置");
						if(rowList != null && rowList.get(1) != null && !"".equals(rowList.get(1))){
							map.put("file_address", rowList.get(1));
						}else{
							map.put("file_address", "");
						}
						/**校验存档位置**********end*******************/
						
						/**校验文档路径**********begin*****************/
						rowList = dataMap.get("文档路径");
						if(rowList != null && rowList.get(1) != null && !"".equals(rowList.get(1))){
							map.put("file_path", rowList.get(1));
						}else{
							map.put("file_path", "");
						}
						/**校验文档路径**********end*******************/
						
						/**校验证件描述**********begin*****************/
						rowList = dataMap.get("证件描述");
						if(rowList != null && rowList.get(1) != null && !"".equals(rowList.get(1))){
							map.put("cert_memo", rowList.get(1));
						}else{
							map.put("cert_memo", "");
						}
						/**校验证件描述**********end*******************/
						
						/**状态**************begin*****************/
						rowList = dataMap.get("审核状态");
						//存放状态
						if(rowList != null && rowList.get(1) != null && !"".equals(rowList.get(1)) && "已审核".equals(rowList.get(1))){
							map.put("state", "1");
						}else{
							map.put("state", "0");
						}
						rowList = dataMap.get("在用状态");
						//存放状态
						if(rowList != null && rowList.get(1) != null && !"".equals(rowList.get(1)) && "在用".equals(rowList.get(1))){
							map.put("cert_state", "1");
						}else{
							map.put("cert_state", "0");
						}
						/**状态**************end*******************/
						
						//必填项
						map.put("group_id", group_id);
						map.put("hos_id", hos_id);
						map.put("copy_code", copy_code);
						map.put("cert_id", matInvCertMapper.queryMatInvCertNextId());
						map.put("cert_state", 1);  //默认再用
						map.put("create_user", SessionManager.getUserId());
						
						//增加新记录
						certMap.put(certKey, map);
					}
				}
				
				/**校验材料编码******begin*****************/
				rowList = dataMap.get("材料编码");
				if(rowList != null && rowList.get(1) != null && !"".equals(rowList.get(1))){
					
					inv_code = rowList.get(1);
					Map<String, Object> v_inv = invMap.get(inv_code);
					if(v_inv == null){
						return "{\"warn\":\"" + rowList.get(0) + "，材料编码不存在！\",\"state\":\"false\",\"row_cell\":\"" + rowList.get(0) + "\"}";
					}
					inv_id = v_inv.get("INV_ID").toString();
					
					invRelaKey = certKey + inv_id;
					
					if(!invRelaMap.containsKey(invRelaKey)){
						invRMap.put("group_id", group_id);
						invRMap.put("hos_id", hos_id);
						invRMap.put("copy_code", copy_code);
						invRMap.put("cert_id", map.get("cert_id"));
						invRMap.put("cert_code", certKey);
						invRMap.put("inv_id", inv_id);
						
						//增加新记录
						invRelaMap.put(invRelaKey, invRMap);
					}
				}
				/**校验材料编码******end*******************/
				
				/**校验供应商编码****begin*****************/
				rowList = dataMap.get("供应商编码");
				if(rowList != null && rowList.get(1) != null && !"".equals(rowList.get(1))){
					sup_code = rowList.get(1);
					Map<String, Object> v_sup = supMap.get(sup_code);
					if(v_sup == null){
						return "{\"warn\":\"" + rowList.get(0) + "，供应商编码不存在！\",\"state\":\"false\",\"row_cell\":\"" + rowList.get(0) + "\"}";
					}
					sup_id = v_sup.get("SUP_ID").toString();
					
					supRelaKey = certKey + sup_id;
					
					if(!supRelaMap.containsKey(supRelaKey)){
						supRMap.put("group_id", group_id);
						supRMap.put("hos_id", hos_id);
						supRMap.put("copy_code", copy_code);
						supRMap.put("cert_id", map.get("cert_id"));
						supRMap.put("sup_id", sup_id);
						
						//增加新记录
						supRelaMap.put(supRelaKey, supRMap);
					}
				}
				/**校验供应商编码****end*******************/
			}

			//解析certMap获得需要添加的数据
			for(String key : certMap.keySet()){
				certList.add(certMap.get(key));
			}

			//解析invRelaMap获得需要添加的数据
			for(String key : invRelaMap.keySet()){
				invRelaList.add(invRelaMap.get(key));
			}

			//解析supRelaMap获得需要添加的数据
			for(String key : supRelaMap.keySet()){
				supRelaList.add(supRelaMap.get(key));
			}
			
			//批量添加证件
			matInvCertMapper.addBatchMatInvCert(certList);
			//批量添加证件材料关系
			if(invRelaList.size()>0){
				matInvCertRelaMapper.addBatchMatInvCertRela(invRelaList);
			}
			//批量添加证件供应商关系
			if(supRelaList.size()>0){
				matCertSupMapper.addBatch(supRelaList);
			}
		}catch (Exception e) {
			logger.error(e.getMessage(), e);
			
			throw new SysException("操作失败");
		}
		
		return "{\"msg\":\"操作成功.\",\"state\":\"true\"}";
	}
	public Map<String, Object> queryDataForTemplatePrint(Map<String, Object> mapVo){
		//获取bean
		Map<String, Object> reMap=new HashMap<String, Object>();
		WebApplicationContext context = ContextLoader.getCurrentWebApplicationContext();
		MatInvCertMapper matInvCertMapper=(MatInvCertMapper) context.getBean("matInvCertMapper");
		//装填主表数据
		Map<String, Object> mainMap=new HashMap<String, Object>();
		String user = SessionManager.getUserName();
		SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
		String date = fmt.format(new Date());
		mainMap.put("ID", 1); 
		mainMap.put("user_name", user);
		mainMap.put("print_date", date);
		//装填从表数据
		String[] cert_ids=mapVo.get("ids").toString().split(",");
		mapVo.put("cert_ids", cert_ids);
		List<Map<String,Object>> detailList=matInvCertMapper.queryDataForTemplatePrint(mapVo);
		
		
		reMap.put("main", mainMap);
		reMap.put("detail", detailList);
		return reMap;
	}
	
	@Override
	public String updateMatCertInvBatch(Map<String, Object> entityMap) throws DataAccessException {
		try {

			if(entityMap.get("cert_ids") != null && !"".equals(entityMap.get("cert_ids").toString())){
			
				matInvCertMapper.updateMatInvCert(entityMap);
				return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";
				
			}else{
				return "{\"error\":\"请选择材料！\"}";
			}
		}catch (DataAccessException e) {
			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"操作失败\"}");
			//return "{\"error\":\"更新失败 数据库异常 请联系管理员! 方法 updateMatInv\"}";
		}		
	}
}
