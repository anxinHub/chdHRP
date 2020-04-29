package com.chd.hrp.hpm.serviceImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.SessionManager;
import com.chd.base.SysPage;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.base.util.JsonListBeanUtil;
import com.chd.base.util.SpreadTableJSUtil;
import com.chd.base.util.StringTool;
import com.chd.hrp.hpm.dao.AphiWorkitemMapper;
import com.chd.hrp.hpm.entity.AphiWorkitem;
import com.chd.hrp.hpm.service.AphiWorkitemService;
import com.github.pagehelper.PageInfo;

/**
 * alfred
 */


@Service("aphiWorkitemService")
public class AphiWorkitemServiceImpl implements AphiWorkitemService {

	private static Logger logger = Logger.getLogger(AphiWorkitemServiceImpl.class);
	
	@Resource(name = "aphiWorkitemMapper")
	private final AphiWorkitemMapper aphiWorkitemMapper = null;
    
	/**
	 * 
	 */
	@Override
	public String addWorkitem(Map<String,Object> entityMap)throws DataAccessException{

	    AphiWorkitem wk=queryWorkitemByCode(entityMap);
		
		if(wk != null){
			
	    	return "{\"error\":\"工作量指标编码：" + entityMap.get("work_item_code").toString() + "重复.\"}";
	   
		}
		
		try{
			
			String work_item_code = entityMap.get("work_item_code").toString();
			
			entityMap.put("spell_code", StringTool.toPinyinShouZiMu(work_item_code));
			
			entityMap.put("wbx_code", StringTool.toWuBi(work_item_code));
			
			entityMap.put("data_source", "手工录入");

			
			aphiWorkitemMapper.addWorkitem(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";
			
		}catch (Exception e) {
			
			logger.error(e.getMessage(), e);
			
			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码 addWorkitem\"}";
						
		}	
	}
	
	/**
	 * 
	 */
	@Override
	public String queryWorkitem(Map<String,Object> entityMap) throws DataAccessException{
		
		
		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		if (sysPage.getTotal() == -1) {

			List<AphiWorkitem> list = aphiWorkitemMapper.queryWorkitem(entityMap);

			return ChdJson.toJson(list);

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

			List<AphiWorkitem> list = aphiWorkitemMapper.queryWorkitem(entityMap, rowBounds);

			PageInfo page = new PageInfo(list);

			return ChdJson.toJson(list, page.getTotal());

		}
	}
	
	/**
	 * 
	 */
	@Override
	public AphiWorkitem queryWorkitemByCode(Map<String,Object> entityMap)throws DataAccessException{
		
		return aphiWorkitemMapper.queryWorkitemByCode(entityMap);
		
	}
	
	/**
	 * 
	 */
	@Override
	public String deleteWorkitem(Map<String,Object> mapVo,String codes)throws DataAccessException{
		
		try{
			
			//String income_item_codes = mapVo.get("income_item_codes").toString();
			
			if (codes != null && !codes.equals("")) {
				
				String[] code_array = codes.split(",");
				
				for (String code : code_array) {
					
					mapVo.put("work_item_code", code);
					
					aphiWorkitemMapper.deleteWorkitem(mapVo);
				}
				
				return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
				
			} else {
				
				return "{\"msg\":\"没有要删除的数据.\",\"state\":\"false\"}";
				
			}
			
		}catch(Exception e){
			
			logger.error(e.getMessage(), e);
			
			return "{\"error\":\"工作量指标配置Aphi_WorkItem _Conf中已引用此项目，不能删除\"}";
			
		}
	}
	
	/**
	 * 
	 */
	@Override
	public String updateWorkitem(Map<String,Object> entityMap)throws DataAccessException{

		try{
			
			String work_item_code = entityMap.get("work_item_code").toString();
			
			entityMap.put("spell_code", StringTool.toPinyinShouZiMu(work_item_code));
			
			entityMap.put("wbx_code", StringTool.toWuBi(work_item_code));
			
			entityMap.put("data_source", "手工录入");
			
			aphiWorkitemMapper.updateWorkitem(entityMap);
			
			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";
			
		}catch(Exception e){
			
			logger.error(e.getMessage(), e);
			
			return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updateWorkitem\"}";
			
		}
	}

	@Override
	public String addBatchWorkitem(List<Map<String, Object>> entityMap)
			throws DataAccessException {
		int state = aphiWorkitemMapper.addBatchWorkitem(entityMap);
		if(state>0){
			return "{\"msg\":\"导入成功.\",\"state\":\"true\"}";
		}
		return "{\"msg\":\"导入失败.\",\"state\":\"false\"}";
	}

	//新版导入
	@Override
	public String hpmWorkitemImport(Map<String, Object> map)throws DataAccessException {
		// TODO Auto-generated method stub
		try {
			String content=map.get("content").toString();
			
			List<Map<String,List<String>>> liData=SpreadTableJSUtil.toListMap(content,1);
			
			if(liData==null || liData.size()==0){
				return "{\"error\":\"没有数据！\",\"state\":\"false\"}";
			}
			
			//new Map查询导入时对应数据信息
			Map<String, Object> entityMap=new HashMap<String,Object>();
			entityMap.put("group_id", SessionManager.getGroupId());
			entityMap.put("hos_id", SessionManager.getHosId());
			entityMap.put("copy_code", SessionManager.getCopyCode());
			entityMap.put("is_stop", "0");// 查询未停用
			
			//查询导入表数据，防止重复
			List<AphiWorkitem> aphiWorkitemList = aphiWorkitemMapper.queryWorkitem(entityMap);
			
			//
			Map<String,AphiWorkitem> aphiWorkitemMap = new HashMap<String, AphiWorkitem>();
			
			//用于存储传的数据值,判断数据是否重复
			Map<String,String> exitMap = new HashMap<String,String>();
			
			//存储要添加保存的数据List
			List<Map<String,Object>> returnList = new ArrayList<Map<String,Object>>();
			
			//用于记录重复数据
			StringBuffer err_sb = new StringBuffer();
			
			//
			for(AphiWorkitem aphiWorkitem:aphiWorkitemList){
				aphiWorkitemMap.put(aphiWorkitem.getWork_item_code(), aphiWorkitem);
				aphiWorkitemMap.put(aphiWorkitem.getWork_item_name(), aphiWorkitem);
			}
			
			//遍历导入数据-遍历单一导入数据,得到名称-判断是否为空-跳出循环
			for(Map<String, List<String>> item:liData){
				for(String st :item.keySet()){
					if(item.get(st).get(1) == null){
						break;
					}
					
					List<String> work_item_code = item.get("工作量指标编码");
					List<String> work_item_name = item.get("工作量指标名称");
					List<String> is_stop = item.get("是否停用");
					
					
					if (work_item_code.get(1) == null) {
						return "{\"warn\":\"工作量指标编码为空！\",\"state\":\"false\",\"row_cell\":\"" + work_item_code.get(0) + "\"}";
					}
					
					if (work_item_name.get(1) == null) {
						return "{\"warn\":\"工作量指标名称为空！\",\"state\":\"false\",\"row_cell\":\"" + work_item_name.get(0) + "\"}";
					}
					
					for(AphiWorkitem aphiWorkitem:aphiWorkitemList){
						if (work_item_code.get(1).equals(aphiWorkitem.getWork_item_code())) {
							return "{\"warn\":\"工作量指标编码已经存在！\",\"state\":\"false\",\"row_cell\":\"" + work_item_code.get(0) + "\"}";
						}else if(work_item_name.get(1).equals(aphiWorkitem.getWork_item_name())){
							return "{\"warn\":\"工作量指标名称已经存在！\",\"state\":\"false\",\"row_cell\":\"" + work_item_name.get(0) + "\"}";
						}
					}
					
					/*if (is_stop.get(1) == null) {
						return "{\"warn\":\"是否停用为空！\",\"state\":\"false\",\"row_cell\":\"" + is_stop.get(0) + "\"}";
					}else if(Character.isDigit(is_stop.get(1).charAt(0))){
						is_stop.add(1,(is_stop.get(1).equals("1")?is_stop.get(1).replace("1","是"):is_stop.get(1).replace("0","否")));
					}
					
					//判断可为空字段
					List<String> data_source = new ArrayList<String>();
					if(item.get("数据来源") == null){
						data_source.add("");
						data_source.add(1,"");
					}else{
						data_source = item.get("数据来源");
					}*/
					
					//判断导入数据是否重复
					String key = work_item_code.get(1) + work_item_name.get(1);
					if(exitMap.get(key) != null ){
						err_sb.append(work_item_code.get(1)+"工作量指标编码," +work_item_name.get(1)+"工作量指标名称 ");
					}else{
						exitMap.put(key, key);
					}
					
					//添加数据Map中add到returnList
					Map<String,Object> returnMap = new HashMap<String,Object>();
					returnMap.put("group_id", SessionManager.getGroupId());
					returnMap.put("hos_id", SessionManager.getHosId());
					returnMap.put("copy_code", SessionManager.getCopyCode());
					returnMap.put("work_item_code", work_item_code.get(1));
					returnMap.put("work_item_name", work_item_name.get(1));
					returnMap.put("spell_code", StringTool.toPinyinShouZiMu(work_item_name.get(1)));
					returnMap.put("wbx_code", StringTool.toWuBi(work_item_name.get(1)));
					returnMap.put("data_source", "");
					returnMap.put("is_stop", 0);
					
					returnList.add(returnMap);
					break;
				}
				
			}
			
			if (err_sb.toString().length() > 0 ) {
				return "{\"warn\":\"以下数据【" +err_sb.toString() + "】数据重复！\",\"state\":\"false\"}";
			}else{
				aphiWorkitemMapper.addBatchWorkitem(returnList);
				return "{\"msg\":\"导入成功.\",\"state\":\"true\"}";
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}
	}
}
