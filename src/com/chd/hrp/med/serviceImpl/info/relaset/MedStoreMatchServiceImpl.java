/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.med.serviceImpl.info.relaset;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.chd.base.SessionManager;
import com.chd.base.SysPage;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.hrp.med.dao.base.MedCommonMapper;
import com.chd.hrp.med.dao.base.MedNoManageMapper;
import com.chd.hrp.med.dao.info.relaset.MedStoreMatchMapper;
import com.chd.hrp.med.entity.MedDeptMatch;
import com.chd.hrp.med.entity.MedStoreMatch;
import com.chd.hrp.med.service.info.relaset.MedStoreMatchService;
import com.github.pagehelper.PageInfo;


/**
 * 
 * @Description:
 * 08113 药品仓库配套表
 * @Table:
 * MED_STORE_MATCH
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 


@Service("medStoreMatchService")
public class MedStoreMatchServiceImpl implements MedStoreMatchService {
	
	private static Logger logger = Logger.getLogger(MedStoreMatchServiceImpl.class);
	
	@Resource(name = "medStoreMatchMapper")
	private MedStoreMatchMapper medStoreMatchMapper = null;
	
	@Resource(name="medNoManageMapper")
	private MedNoManageMapper medNoManageMapper = null;
	
	@Resource(name="medCommonMapper")
	private MedCommonMapper medCommonMapper = null;
	
	/**
	 * @Description 
	 * 配套表<BR>添加配套表、配套表明细
	 * @param  entityMap<BR>
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String add(Map<String, Object> entityMap) throws DataAccessException {
		
		try{
			List<MedStoreMatch> list = (List<MedStoreMatch>)medStoreMatchMapper.queryExists(entityMap);
			if (list.size()>0){
				return "{\"error\":\"配套表编码:已存在\"}";
			}
			
			List<MedStoreMatch> list2 = (List<MedStoreMatch>)medStoreMatchMapper.queryByName(entityMap);
			if (list2.size()>0){
				return "{\"error\":\"配套表名称:已存在\"}";
			}
			
			entityMap.put("store_match_id", medStoreMatchMapper.queryStoreMatchNextSeq());
			List<Map<String,Object>> allDataList = new ArrayList<Map<String,Object>>();//添加数据参数集合
			if(entityMap.get("allData") != null && !"".equals(entityMap.get("allData"))){
				JSONArray allDataJson = JSONArray.parseArray((String) entityMap.get("allData"));//获取要添加的数据
				Iterator allDataIt = allDataJson.iterator();
				while (allDataIt.hasNext()) {
					Map<String,Object> map = new HashMap<String,Object>();
					JSONObject jsonObj = JSONObject.parseObject(allDataIt.next().toString());
					if( !"".equals(jsonObj.get("inv_id")) && jsonObj.get("inv_id") != null){
						map.put("group_id",entityMap.get("group_id"));
						map.put("hos_id", entityMap.get("hos_id"));
						map.put("copy_code", entityMap.get("copy_code"));
						map.put("store_match_id",entityMap.get("store_match_id"));
						map.put("inv_id",jsonObj.get("inv_id"));
						map.put("amount",jsonObj.get("amount"));
						allDataList.add(map);
					}
				}
			}
			
			medStoreMatchMapper.add(entityMap);//保存配套表主表信息
			if(allDataList.size() > 0){//有数据、就添加
				medStoreMatchMapper.addBatchMsmDetail(allDataList);
			}
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";
		}catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"添加失败\"}");
		}
	}

	@Override
	public String addBatch(List<Map<String, Object>> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}
	
	/**
	 * @Description 
	 * 配套表<BR>修改仓库名称、仓库ID 
	 * @param  entityMap<BR>
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String update(Map<String, Object> entityMap) throws DataAccessException {
			medStoreMatchMapper.update(entityMap);
			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";
	}

	@Override
	public String updateBatch(List<Map<String, Object>> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String delete(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}
	
	/**
	 * @Description 
	 * 删除  配套表(仓库)<BR> 
	 * @param  entityMap<BR>
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String deleteBatch(List<Map<String, Object>> entityMap) throws DataAccessException {
		try{
			medStoreMatchMapper.deleteBatchMsmDetail(entityMap);//删除明细数据
			medStoreMatchMapper.deleteBatch(entityMap);//删除主表
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		}catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"添加失败\"}");
		}
	}

	@Override
	public String addOrUpdate(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}
	
	/**
	 * @Description 
	 * 查询  配套表(仓库)<BR> 
	 * @param  entityMap<BR>
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String query(Map<String, Object> entityMap) throws DataAccessException {
		
		List<?> list = medStoreMatchMapper.query(entityMap);
		return ChdJson.toJson(list);
		
		/*SysPage sysPage = new SysPage();
		sysPage = (SysPage)entityMap.get("sysPage");
		if(sysPage.getTotal() == -1){
			List<?> list = medStoreMatchMapper.query(entityMap);
			return ChdJson.toJson(list);
		}else{
			RowBounds rowBounds = new RowBounds(sysPage.getPage(),sysPage.getPagesize());
			List<?> list = medStoreMatchMapper.query(entityMap, rowBounds);
			PageInfo page = new PageInfo(list);
			return ChdJson.toJson(list,page.getTotal());
		}*/
	}

	@Override
	public <T> T queryByCode(Map<String, Object> entityMap) throws DataAccessException {
		
		return  medStoreMatchMapper.queryByCode(entityMap);
	}

	@Override
	public <T> T queryByUniqueness(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<?> queryExists(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}
	
	/**
	 * @Description 
	 * 药品仓库配套表<BR> 查询 材料明细
	 * @param entityMap
	 * @return List<MedStoreMatch>
	 * @throws DataAccessException
	*/
	@Override
	public String querySmmDetailByCode(Map<String, Object> entityMap) throws DataAccessException {
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage)entityMap.get("sysPage");
		
		List<MedStoreMatch> list = new ArrayList<MedStoreMatch>();
		
		if(sysPage.getTotal() == -1){
			
			list = medStoreMatchMapper.querySmmDetailByCode(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(),sysPage.getPagesize());
			
			list = medStoreMatchMapper.querySmmDetailByCode(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list,page.getTotal());
		}
	}
	
	/**
	 * @Description 
	 * 药品仓库配套表<BR>更新 配套表材料明细
	 * @param entityMap
	 * @return List<MedStoreMatch>
	 * @throws DataAccessException
	*/
	@Override
	public String updateMsmDetail(Map<String, Object> entityMap) throws DataAccessException {
		try{
			List<MedStoreMatch> listbm = (List<MedStoreMatch>)medStoreMatchMapper.queryByName(entityMap);
			if (listbm.size()>0){
				return "{\"error\":\"配套表名称:已存在\"}";
			}
			List<Map<String,Object>> list  = new ArrayList<Map<String,Object>>();
			List<Map<String,Object>> allDataList = new ArrayList<Map<String,Object>>();//批量修改参数集合
			
			if(entityMap.get("allData") != null && !"".equals(entityMap.get("allData"))){
				JSONArray allDataJson = JSONArray.parseArray((String) entityMap.get("allData"));//获取修改的数据
				Iterator allDataIt = allDataJson.iterator();
				while (allDataIt.hasNext()) {
					Map<String,Object> map = new HashMap<String,Object>();
					JSONObject jsonObj = JSONObject.parseObject(allDataIt.next().toString());
					if( !"".equals(jsonObj.get("inv_id")) && jsonObj.get("inv_id") != null){
						map.put("group_id",entityMap.get("group_id"));
						map.put("hos_id", entityMap.get("hos_id"));
						map.put("copy_code", entityMap.get("copy_code"));
						map.put("store_match_id",entityMap.get("store_match_id"));
						map.put("inv_id",jsonObj.get("inv_id"));
						map.put("amount",jsonObj.get("amount"));
						allDataList.add(map);
					}
				}
			}
			medStoreMatchMapper.update(entityMap);//保存配套表主表信息
			list.add(entityMap);
			if(list.size() > 0){
				medStoreMatchMapper.deleteBatchMsmDetail(list);//删除明细表中所有数据
			}
			if(allDataList.size() > 0){//有数据、就添加
				medStoreMatchMapper.addBatchMsmDetail(allDataList);
			}
			return "{\"msg\":\"保存成功.\",\"state\":\"true\"}";
		}catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"添加失败\"}");
		}
		
			
	}
	
	/**
	 * 系统  自动生成 仓库配套表编码   （规则   仓库别名+“—”年份（后两位）+月份+流水号
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	@Override
	public String getMedNextNo(Map<String, Object> entityMap) throws DataAccessException{
		if(entityMap.get("group_id") == null){
			entityMap.put("group_id", SessionManager.getGroupId());
		}
		if(entityMap.get("hos_id") == null){
			entityMap.put("hos_id", SessionManager.getHosId());
		}
		if(entityMap.get("copy_code") == null){
			entityMap.put("copy_code", SessionManager.getCopyCode());
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("group_id", entityMap.get("group_id"));
		map.put("hos_id", entityMap.get("hos_id"));
		map.put("copy_code", entityMap.get("copy_code"));
		map.put("store_id", entityMap.get("store_id"));
		//获取仓库别名store_alias
		String store_alias = medCommonMapper.queryStoreAliasById(map);
		map.put("store_alias", store_alias);
		map.put("table_code", entityMap.get("table_code").toString().toUpperCase());
		map.put("year", entityMap.get("year"));
		map.put("month", entityMap.get("month"));
		map.put("prefixe", entityMap.get("prefixe"));
		
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
		for (int i = max_no.length() ; i < 3; i++) {
			max_no = "0" + max_no;
		}
		//组装流水码
		String next_no = store_alias + "-" + entityMap.get("year").toString().substring(2, 4) + entityMap.get("month").toString() + max_no;
		
		return next_no;
	}
}
