/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/

package com.chd.hrp.acc.serviceImpl.autovouch;

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

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.chd.base.SessionManager;
import com.chd.base.SysPage;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.base.util.NumberToCN;
import com.chd.hrp.acc.dao.autovouch.AccBudgTpMapper;
import com.chd.hrp.acc.service.autovouch.AccBudgTpService;
import com.github.pagehelper.PageInfo;

/**
* @Title. @Description.
* 平行记账模板<BR> 
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


@Service("accBudgTpService")
public class AccBudgTpServiceImpl implements AccBudgTpService {

	private static Logger logger = Logger.getLogger(AccBudgTpServiceImpl.class);
	
	@Resource(name = "accBudgTpMapper")
	private final AccBudgTpMapper accBudgTpMapper = null;
	
	//列表查询
	@Override
	public String queryAccBudgTp(Map<String,Object> entityMap) throws DataAccessException{
		
		entityMap.put("group_id", SessionManager.getGroupId());
		entityMap.put("hos_id", SessionManager.getHosId());
		entityMap.put("copy_code", SessionManager.getCopyCode());
		
		SysPage sysPage = new SysPage();
		sysPage = (SysPage) entityMap.get("sysPage");
		
		RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
		
		List<Map<String, Object>> list = accBudgTpMapper.queryList(entityMap, rowBounds);
		
		PageInfo page = new PageInfo(list);
		
		return ChdJson.toJson(list, page.getTotal());
	}
	
	//主表精准查询
	@Override
	public Map<String, Object> queryMainByCode(Map<String, Object> entityMap) throws DataAccessException{
		Map<String, Object> retMap = new HashMap<String, Object>();
		try {
			entityMap.put("group_id", SessionManager.getGroupId());
			entityMap.put("hos_id", SessionManager.getHosId());
			entityMap.put("copy_code", SessionManager.getCopyCode());
			
			retMap = accBudgTpMapper.queryMainByCode(entityMap);
		} catch (Exception e) {
			
			logger.error(e.getMessage(), e);
			throw new SysException("系统繁忙，请稍后再试");
		}
		
		return retMap;
	}
	
	//明细精准查询
	@Override
	public String queryDetailByCode(Map<String,Object> entityMap) throws DataAccessException{
		
		entityMap.put("group_id", SessionManager.getGroupId());
		entityMap.put("hos_id", SessionManager.getHosId());
		entityMap.put("copy_code", SessionManager.getCopyCode());
		entityMap.put("acc_year", SessionManager.getAcctYear());
		
		List<Map<String, Object>> list = accBudgTpMapper.queryDetailByCode(entityMap);
		
		return ChdJson.toJson(list);
	}

	//保存
	@Override
	public Map<String, Object> saveAccBudgTp(Map<String,Object> entityMap)throws DataAccessException{
		Map<String, Object> retMap = new HashMap<String, Object>();
		retMap.put("state", "true");
		
		try {
			entityMap.put("group_id", SessionManager.getGroupId());
			entityMap.put("hos_id", SessionManager.getHosId());
			entityMap.put("copy_code", SessionManager.getCopyCode());
			entityMap.put("user_id", SessionManager.getUserId());
			
			//必填项判断
			if(entityMap.get("tp_code") == null || "".equals(entityMap.get("tp_code").toString())){
				retMap.put("state", "false");
				retMap.put("error", "编码不能为空");
				return retMap;
			}
			if(entityMap.get("tp_name") == null || "".equals(entityMap.get("tp_name").toString())){
				retMap.put("state", "false");
				retMap.put("error", "名称不能为空");
				return retMap;
			}
			
			String is_update = entityMap.get("is_update").toString();
			int is_flag = 0;
			int sort_code = 0; 
			boolean update = true;  //判断是添加还是修改
			//添加需判断编码是否重复
			if(is_update != null && "0".equals(is_update)){
				is_flag = accBudgTpMapper.existsByCode(entityMap);
				if(is_flag > 0){
					retMap.put("state", "false");
					retMap.put("error", "编码重复，请修改");
					return retMap;
				}
				is_flag = 0;
				update = false;
				
				//添加自动获取排序号
				sort_code = accBudgTpMapper.getMaxSortCode(entityMap);
				entityMap.put("sort_code", sort_code + 1);
			}

			//判断名称是否重复
			is_flag = accBudgTpMapper.existsByName(entityMap);
			if(is_flag > 0){
				retMap.put("state", "false");
				retMap.put("error", "名称重复，请修改");
				return retMap;
			}
			
			//解析明细数据
			List<Map<String, Object>> detailList = new ArrayList<Map<String,Object>>();
			JSONArray json = JSONArray.parseArray(String.valueOf(entityMap.get("allData")));
			Iterator iterator = json.iterator();
			JSONObject jsonObj = null;
			Map<String, Object> detailMap = null;
			int index = 1;
			while(iterator.hasNext()){
				jsonObj = JSONObject.parseObject(iterator.next().toString());
				
				detailMap = new HashMap<String, Object>();
				detailMap.put("group_id", entityMap.get("group_id"));
				detailMap.put("hos_id", entityMap.get("hos_id"));
				detailMap.put("copy_code", entityMap.get("copy_code"));
				detailMap.put("tp_code", entityMap.get("tp_code"));
				detailMap.put("sort_code", jsonObj.getString("sort_code"));
				detailMap.put("subj_code", jsonObj.getString("subj_code"));
				detailMap.put("kind_code", jsonObj.getString("kind_code"));
				detailMap.put("summary", jsonObj.getString("summary"));
				detailMap.put("dire", jsonObj.getString("dire"));
				detailMap.put("cal", jsonObj.getString("cal"));
				detailMap.put("did", index);
				
				detailList.add(detailMap);
				index ++;
			}
			
			if(detailList.size() == 0){
				retMap.put("state", "false");
				retMap.put("error", "请添加分录信息");
				return retMap;
			}
			
			if(update){
				//修改
				accBudgTpMapper.updateMain(entityMap);
				//删除明细
				accBudgTpMapper.deleteDetailByCode(entityMap);
				//添加明细
				accBudgTpMapper.addDetail(detailList);
			}else{
				//添加
				accBudgTpMapper.addMain(entityMap);
				//添加明细
				accBudgTpMapper.addDetail(detailList);
			}

			retMap.put("msg", "操作成功");
			retMap.put("sort_code", sort_code);
		} catch (Exception e) {
			
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}
		
		return retMap;
	}

	//凭证制单-保存平行记账模板
	@Override
	public Map<String, Object> saveAccBudgTpByVouch(Map<String, Object> entityMap) throws DataAccessException {
		
		Map<String, Object> retMap = new HashMap<String, Object>();
		retMap.put("state", "true");
		
		try {
			entityMap.put("group_id", SessionManager.getGroupId());
			entityMap.put("hos_id", SessionManager.getHosId());
			entityMap.put("copy_code", SessionManager.getCopyCode());
			entityMap.put("user_id", SessionManager.getUserId());
			
			//必填项判断
			if(entityMap.get("tp_code") == null || "".equals(entityMap.get("tp_code").toString())){
				retMap.put("state", "false");
				retMap.put("error", "编码不能为空");
				return retMap;
			}
			if(entityMap.get("tp_name") == null || "".equals(entityMap.get("tp_name").toString())){
				retMap.put("state", "false");
				retMap.put("error", "名称不能为空");
				return retMap;
			}
			
			String tp_code=entityMap.get("tp_code").toString();
			String is_replace=entityMap.get("is_replace").toString();//是否替换
			int is_flag=0;
			if(is_replace.equals("1")){
				entityMap.put("tp_codes","'"+tp_code+"'");
				//删除主表、明细
				accBudgTpMapper.deleteAll(entityMap);
			}else{
				//不替换才会存在编码重复
				is_flag = accBudgTpMapper.existsByCode(entityMap);
				if(is_flag > 0){
					retMap.put("state", "false");
					retMap.put("error", "编码重复，请修改");
					return retMap;
				}
			}
			
			//判断名称是否重复
			is_flag = accBudgTpMapper.existsByName(entityMap);
			if(is_flag > 0){
				retMap.put("state", "false");
				retMap.put("error", "名称重复，请修改");
				return retMap;
			}
			
			//添加自动获取排序号
			int sort_code = accBudgTpMapper.getMaxSortCode(entityMap);
			entityMap.put("sort_code", sort_code + 1);
			
			//解析明细数据
			JSONArray detailjson = JSONObject.parseArray(entityMap.get("detail").toString());
			List<Map<String,Object>> detailList=new ArrayList<Map<String,Object>>();
			Map<String,Object> detailMap=null;
			int index = 1;
			for(int i=0;i<detailjson.size();i++){
				JSONObject detail = JSONObject.parseObject(detailjson.getString(i));
				detailMap=new HashMap<String, Object>();
				detailMap.put("group_id", entityMap.get("group_id"));
				detailMap.put("hos_id", entityMap.get("hos_id"));
				detailMap.put("copy_code", entityMap.get("copy_code"));
				detailMap.put("tp_code", entityMap.get("tp_code"));
				String excelCol=NumberToCN.excelColIndexToStr(Integer.parseInt(detail.getString("vouch_row")));
				/*for (byte b : vouch_row.getBytes()) {
					zm=zm+(char) (b + 48);//数字转字母，27不能转成AA
				}*/
				detailMap.put("sort_code", excelCol);
				detailMap.put("subj_code", detail.getString("sid"));
				String kind_code=detail.getString("kind_code");
				detailMap.put("kind_code", kind_code);
				detailMap.put("summary", detail.getString("summary"));
				float credit=Float.parseFloat(detail.getString("credit"));
				detailMap.put("dire", credit!=0?1:0);
				
				if(kind_code.equals("02")){
					detailMap.put("cal", excelCol);
				}else{
					detailMap.put("cal", null);
				}
				detailMap.put("did", index);
				detailList.add(detailMap);
				index ++;
				
			}
			
			if(detailList.size() == 0){
				retMap.put("state", "false");
				retMap.put("error", "请添加分录信息");
				return retMap;
			}
			
			//添加
			accBudgTpMapper.addMain(entityMap);
			//添加明细
			accBudgTpMapper.addDetail(detailList);

			retMap.put("msg", "操作成功");
			retMap.put("sort_code", sort_code);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}
		return retMap;
		
	}
	
	
	//删除
	@Override
	public Map<String, Object> deleteAccBudgTp(Map<String, Object> entityMap)throws DataAccessException{
		Map<String, Object> retMap = new HashMap<String, Object>();
		retMap.put("state", "true");
		
		try {
			if(entityMap.get("tp_codes") == null || "".equals(entityMap.get("tp_codes").toString())){
				retMap.put("state", "false");
				retMap.put("error", "请选择数据");
				return retMap;
			}
			entityMap.put("group_id", SessionManager.getGroupId());
			entityMap.put("hos_id", SessionManager.getHosId());
			entityMap.put("copy_code", SessionManager.getCopyCode());
			
			accBudgTpMapper.deleteAll(entityMap);
			retMap.put("msg", "操作成功");
		} catch (Exception e) {
			
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}
		
		return retMap;
	}
	
	//查询科目
	@Override
	public String querySubjSelect(Map<String, Object> entityMap) throws DataAccessException{

		entityMap.put("group_id", SessionManager.getGroupId());
		entityMap.put("hos_id", SessionManager.getHosId());
		entityMap.put("copy_code", SessionManager.getCopyCode());
		if(entityMap.get("acc_year")==null){
			//凭证页面会传会计年度
			entityMap.put("acc_year", SessionManager.getAcctYear());
		}

		return JSON.toJSONString(accBudgTpMapper.querySubjSelect(entityMap));
	}
	
	//查询用户
	@Override
	public String queryUserSelect(Map<String, Object> entityMap) throws DataAccessException{

		entityMap.put("group_id", SessionManager.getGroupId());
		entityMap.put("hos_id", SessionManager.getHosId());

		return JSON.toJSONString(accBudgTpMapper.queryUserSelect(entityMap));
	}
	
	
	//凭证页面查询明细
	@Override
	public String queryDetailByCodeVouch(Map<String,Object> entityMap) throws DataAccessException{
		
		entityMap.put("group_id", SessionManager.getGroupId());
		entityMap.put("hos_id", SessionManager.getHosId());
		entityMap.put("copy_code", SessionManager.getCopyCode());
		
		List<Map<String, Object>> list = accBudgTpMapper.queryDetailByCodeVouch(entityMap);
		
		return ChdJson.toJson(list);
	}
	
	//打印
	@Override
	public List<Map<String, Object>> queryAccBudgTpPrint(Map<String,Object> entityMap) throws DataAccessException{
		
		entityMap.put("group_id", SessionManager.getGroupId());
		entityMap.put("hos_id", SessionManager.getHosId());
		entityMap.put("copy_code", SessionManager.getCopyCode());
		
		List<Map<String, Object>> list = accBudgTpMapper.queryList(entityMap);
		
		return list;
	}
}
