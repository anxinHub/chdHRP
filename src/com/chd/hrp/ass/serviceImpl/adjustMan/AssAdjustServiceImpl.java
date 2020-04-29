package com.chd.hrp.ass.serviceImpl.adjustMan;

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

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.chd.base.SessionManager;
import com.chd.base.SysPage;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.hrp.ass.dao.adjustMan.AssAdjustMapper;
import com.chd.hrp.ass.dao.dict.AssDictMapper;
import com.chd.hrp.ass.service.adjustMan.AssAdjustService;
import com.chd.hrp.ass.service.base.AssBaseService;
import com.chd.hrp.mat.service.adjustMan.changePriceBill.MatAdjustService;
import com.github.pagehelper.PageInfo;


@Service("assAdjustService")
public class AssAdjustServiceImpl  implements AssAdjustService{
 
	private static Logger logger = Logger.getLogger(AssAdjustServiceImpl.class);  

	@Resource(name = "assAdjustMapper")
	private AssAdjustMapper assAdjustMapper = null ;
	
	@Resource(name = "assBaseService")
	private final AssBaseService assBaseService = null;
	
	@Resource(name = "assDictMapper")
	private final AssDictMapper assDictMapper = null;   

	@Override
	public String add(Map<String, Object> entityMap) throws DataAccessException {
		try {
			
			if(entityMap.get("group_id") == null || "".equals(entityMap.get("group_id").toString())){
				
				return "{\"error\":\"保存失败 group_id不能为空! 错误编码 add\"}";
			}
			
			if(entityMap.get("hos_id") == null || "".equals(entityMap.get("hos_id").toString())){
				
				return "{\"error\":\"保存失败 hos_id不能为空! 错误编码 add\"}";
			}
			
			if(entityMap.get("copy_code") == null || "".equals(entityMap.get("copy_code").toString())){
				
				return "{\"error\":\"保存失败 copy_code不能为空! 错误编码 add\"}";
			}
			
			if(entityMap.get("create_date") == null || "".equals(entityMap.get("create_date").toString())){
				
				return "{\"error\":\"保存失败 create_date不能为空! 错误编码 add\"}";
			}
			
			if(entityMap.get("allData") == null || "".equals(entityMap.get("allData").toString())){
				
				return "{\"error\":\"保存失败 allData不能为空! 错误编码 add\"}";
			}
			
			String adjust_code;
			
			String adjust_id;
			
			String create_date = entityMap.get("create_date").toString();
			
			entityMap.put("create_year",create_date.substring(0, 4));
			
			entityMap.put("create_month", create_date.substring(5, 7));
			
			entityMap.put("bill_table", "ASS_ADJUST_MAIN");
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			
			 	adjust_code = assBaseService.getBillNOSeqNo(entityMap);
		
				
				entityMap.put("maker", SessionManager.getUserId());//制单人取当前用户id
				
				entityMap.put("make_date", sdf.format(new Date()));
				
				entityMap.put("adjust_code", adjust_code);
				
				entityMap.put("state", 0);
				
				entityMap.put("checker","");
				
				entityMap.put("adjust_date", "");
				
				assAdjustMapper.add(entityMap);
				
				adjust_id = String.valueOf(queryCurrentSequence());
				
				assBaseService.updateAssBillNoMaxNo(entityMap);
				
				List<Map<String,Object>> allDataList = new ArrayList<Map<String,Object>>();//添加数据参数集合
				
				JSONArray allDataJson = JSONArray.parseArray((String) entityMap.get("allData"));//获取要添加的数据
				
				if(allDataJson == null){
					
					new SysException("{\"error\":\"操作失败\"}");
					//return "{\"error\":\"保存失败 allDataJson不能为空! 错误编码 add\"}";
				}
				
				Iterator iterator = allDataJson.iterator();
				
				while(iterator.hasNext()){
					
					Map<String,Object> map = defaultValue();
					
					JSONObject jsonObj = JSONObject.parseObject(iterator.next().toString());
					
					if(jsonObj.get("ass_id") != null && !"".equals(jsonObj.get("ass_id"))){
						
						map.put("group_id",Long.parseLong(entityMap.get("group_id").toString()));
						
						map.put("hos_id", Long.parseLong(entityMap.get("hos_id").toString()));
						
						map.put("copy_code", entityMap.get("copy_code"));
						
						if(entityMap.get("adjust_id") == null || "".equals(entityMap.get("adjust_id"))){
							
							new SysException("{\"error\":\"操作失败\"}");
							//return "{\"error\":\"保存失败 adjust_id不能为空! 错误编码 add\"}";
						}
						
						map.put("adjust_id", entityMap.get("adjust_id"));
						
						if(jsonObj.get("ass_id") != null && !"".equals(jsonObj.get("ass_id"))){
							
							map.put("ass_id",jsonObj.get("ass_id"));
						}
						
						if(jsonObj.get("ass_no") != null && !"".equals(jsonObj.get("ass_no"))){
							
							map.put("ass_no", jsonObj.get("ass_no"));
						}
						
						if(jsonObj.get("old_price") != null && !"".equals(jsonObj.get("old_price"))){
							
							map.put("old_price", jsonObj.get("old_price"));
						}
						
						if(jsonObj.get("new_price") != null && !"".equals(jsonObj.get("new_price"))){
							
							map.put("new_price",jsonObj.get("new_price"));
						}
						
						if(jsonObj.get("adjust_reason") != null && !"".equals(jsonObj.get("adjust_reason"))){
							
							map.put("adjust_reason", jsonObj.get("adjust_reason"));
						}
						
						allDataList.add(map);
					}
					
				}
				
				if(allDataList.size() > 0 ){
						
					assAdjustMapper.addBatchAssAdjustDetail(allDataList);//保存调价单明细数据
						
				}
					
				return "{\"msg1\":\"保存成功.\",\"state\":\"true\",\"update_para\":\""+entityMap.get("adjust_id").toString()+",0\"}";
				
			} catch (Exception e) {
				
				logger.error(e.getMessage());
				
				throw new SysException("{\"error\":\"操作失败\"}");
			}
	}

	@Override
	public String addBatch(List<Map<String, Object>> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String update(Map<String, Object> entityMap)
			throws DataAccessException {
		if(entityMap.get("group_id") == null || "".equals(entityMap.get("group_id").toString())){
			
			return "{\"error\":\"保存失败 group_id不能为空! 错误编码 update\"}";
		}
		
		if(entityMap.get("hos_id") == null || "".equals(entityMap.get("hos_id").toString())){
			
			return "{\"error\":\"保存失败 hos_id不能为空! 错误编码 update\"}";
		}
		
		if(entityMap.get("copy_code") == null || "".equals(entityMap.get("copy_code").toString())){
			
			return "{\"error\":\"保存失败 copy_code不能为空! 错误编码 update\"}";
		}
		
		if(entityMap.get("create_date") == null || "".equals(entityMap.get("create_date").toString())){
			
			return "{\"error\":\"保存失败 create_date不能为空! 错误编码 update\"}";
		}
		
		if(entityMap.get("allData") == null || "".equals(entityMap.get("allData").toString())){
			
			return "{\"error\":\"保存失败 allData不能为空! 错误编码 update\"}";
		}
		
		if(entityMap.get("adjust_id") == null || "".equals(entityMap.get("adjust_id").toString())){
			
			return "{\"error\":\"保存失败 adjust_id不能为空! 错误编码 update\"}";
		}
			
		List<Map<String,Object>> list  = new ArrayList<Map<String,Object>>();
			
		List<Map<String,Object>> allDataList = new ArrayList<Map<String,Object>>();//添加数据参数集合
			
		JSONArray allDataJson = JSONArray.parseArray((String) entityMap.get("allData"));//获取要添加的数据
			
		if(allDataJson == null ){
				
			return "{\"error\":\"保存失败 allDataJson不能为空! 错误编码 update\"}";
		}
			
		Iterator iterator = allDataJson.iterator();
			
		while(iterator.hasNext()){
				
			Map<String,Object> map = defaultDetailValue();
				
			JSONObject jsonObj = JSONObject.parseObject(iterator.next().toString());
				
			if(jsonObj.get("ass_id") != null && !"".equals(jsonObj.get("ass_id"))){
				
				map.put("group_id",Long.parseLong(entityMap.get("group_id").toString()));
				
				map.put("hos_id", Long.parseLong(entityMap.get("hos_id").toString()));
					
				map.put("copy_code", entityMap.get("copy_code"));
					
				map.put("adjust_id", entityMap.get("adjust_id"));
					
				if(jsonObj.get("ass_id") != null && !"".equals(jsonObj.get("ass_id"))){
					map.put("ass_id",jsonObj.get("ass_id"));
				}
					
				if(jsonObj.get("ass_no") != null && !"".equals(jsonObj.get("ass_no"))){
					map.put("ass_no", jsonObj.get("ass_no"));
				}
				
				if(jsonObj.get("old_price") != null && !"".equals(jsonObj.get("old_price"))){
					map.put("old_price", jsonObj.get("old_price"));
				}else{
					map.put("old_price", 0);
				}
					
				if(jsonObj.get("new_price") != null && !"".equals(jsonObj.get("new_price"))){
					map.put("new_price",jsonObj.get("new_price"));
				}else{
					map.put("new_price",0);
				}
					
					
				if(jsonObj.get("adjust_reason") != null){
					map.put("adjust_reason",jsonObj.get("adjust_reason"));
				}else{
					map.put("adjust_reason","");
				}
					
				allDataList.add(map);
			}
		}
			
		
		try {
			
			assAdjustMapper.update(entityMap);//修改主表数据
			
			list.add(entityMap);
			
			if(list.size() > 0 ){
				
				assAdjustMapper.deleteBatchAssAdjustDetail(list);//删除调价单明细数据
			}
				
			if(allDataList.size() > 0 ){//有数据、就添加
					
				assAdjustMapper.addBatchAssAdjustDetail(allDataList);//添加明细数据
					
			}
			
			return "{\"msg\":\"保存成功.\",\"state\":\"true\"}";
			
		} catch (Exception e) {
			
			logger.error(e.getMessage());
			
			throw new SysException("{\"error\":\"操作失败\"}");
		}
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

	@Override
	public String deleteBatch(List<Map<String, Object>> entityMap)
			throws DataAccessException {
		if(entityMap == null || entityMap.size() <=0){
			
			return "{\"error\":\"删除失败 entityMap不能为空! 错误编码 deleteBatch\"}";
		}
		
		for(Map<String,Object> map:entityMap){
				
			if(map.get("group_id") == null || "".equals(map.get("group_id").toString())){
					
				return "{\"error\":\"删除失败 group_id不能为空! 错误编码 deleteBatch\"}";
			}
				
			if(map.get("hos_id") == null || "".equals(map.get("hos_id").toString())){
					
				return "{\"error\":\"删除失败 hos_id不能为空! 错误编码 deleteBatch\"}";
			}
				
			if(map.get("copy_code") == null || "".equals(map.get("copy_code").toString())){
					
				return "{\"error\":\"删除失败 copy_code不能为空! 错误编码 deleteBatch\"}";
			}
				
			if(map.get("adjust_id") == null || "".equals(map.get("adjust_id").toString())){
					
				return "{\"error\":\"删除失败 adjust_id不能为空! 错误编码 deleteBatch\"}";
			}
		}
			
		try {
			
			assAdjustMapper.deleteBatchAssAdjustDetail(entityMap);//批量删除调价单明细
			
			assAdjustMapper.deleteBatch(entityMap);//批量删除调价单主表数据
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
			
		} catch (Exception e) {
			
			logger.error(e.getMessage());
			
			throw new SysException("{\"error\":\"操作失败\"}");
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
	 * 调价单<BR>查询
	 * @param entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	@Override
	public String query(Map<String, Object> entityMap)
			throws DataAccessException {
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage)entityMap.get("sysPage");
		
		if(sysPage.getTotal() == -1){
			
			List<?> list = assAdjustMapper.query(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(),sysPage.getPagesize());
			
			List<?> list = assAdjustMapper.query(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list,page.getTotal());
		}
	}

	@Override
	public <T> T queryByCode(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		return assAdjustMapper.queryByCode(entityMap);
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

	@Override
	public String queryDetails(Map<String, Object> page)
			throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String queryAssList(Map<String, Object> entityMap)
			throws DataAccessException {
		SysPage sysPage = new SysPage();
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			List<?> list = assAdjustMapper.queryAssList(entityMap);
			
			return ChdJson.toJson(list);
		}else{
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			List<?> list = assAdjustMapper.queryAssList(entityMap, rowBounds);
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
		}
	}

	@Override
	public Long queryCurrentSequence() throws DataAccessException {
		// TODO Auto-generated method stub
		return assAdjustMapper.queryCurrentSequence();
	}
	
	// 返回用用于保存的默认值
	public Map<String, Object> defaultValue() {

		Map<String, Object> mapDetailVo = new HashMap<String, Object>();
				
		mapDetailVo.put("ass_id", "");
				
		mapDetailVo.put("ass_no", "");
				
		mapDetailVo.put("old_sell_price", "");
				
		mapDetailVo.put("new_sell_price", "");
				
		mapDetailVo.put("adjust_reason", "");
				
		return mapDetailVo;
	}

	@Override
	public String queryAssAdjustDetailByCode(Map<String, Object> entityMap)
			throws DataAccessException {
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage)entityMap.get("sysPage");
		
		if(sysPage.getTotal() == -1){
			
			List<?> list = assAdjustMapper.queryAssAdjustDetailByCode(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(),sysPage.getPagesize());
			
			List<?> list = assAdjustMapper.queryAssAdjustDetailByCode(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list,page.getTotal());
		}
	}
	
	// 返回用用于保存的默认值
	public Map<String, Object> defaultDetailValue() {

		Map<String, Object> mapDetailVo = new HashMap<String, Object>();

		mapDetailVo.put("note","");
				
		mapDetailVo.put("checker", "");
		
		mapDetailVo.put("adjust_reason","");
				
		return mapDetailVo;
	}

	@Override
	public String updateAssAdjustState(List<Map<String, Object>> entityMap)
			throws DataAccessException {
		StringBuffer ass_nos = new StringBuffer();
		Map<String,Object> vMap = new HashMap<String,Object>();
		try {
			List<Map<String,Object>> list = (List<Map<String, Object>>) assAdjustMapper.queryBatchAssAdjustDetailByCode(entityMap);
			
			assAdjustMapper.updateAssAdjustState(entityMap);//修改主表审核状态
			
			assAdjustMapper.updateBatchAssPrice(list);//修改资产表计划价
			
			List<Map<String,Object>> assListNew = new ArrayList<Map<String,Object>>();
			for(Map<String,Object> map : list){
				Map<String,Object> assMap = assDictMapper.queryByCode(map); 
				ass_nos.append(assMap.get("ass_no")).append(",");
				assMap.put("is_stop", "0");
				assMap.put("price", map.get("new_price"));  
				assListNew.add(assMap);
			}
			
			// 先停用资产 新添加一条记录
			if(ass_nos.toString().length() > 0){
				vMap.put("ass_nos", ass_nos.substring(0, ass_nos.length()-1));
				vMap.put("group_id", SessionManager.getGroupId());
				vMap.put("hos_id", SessionManager.getHosId());
				vMap.put("copy_code", SessionManager.getCopyCode());
				vMap.put("is_stop", "1");
				assAdjustMapper.updateBatchAssIsStop(vMap);
			}
			
			if(assListNew.size() > 0){
				assDictMapper.addBatch(assListNew);
			}
			
			assAdjustMapper.updateBatchAssDictPlanPrice(list);//修改资产变更表计划价
			
			return "{\"msg\":\"审核成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			throw new SysException("{\"error\":\"操作失败\"}");
		}
	}

}
