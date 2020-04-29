
/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 package com.chd.hrp.med.serviceImpl.info.basic;

import java.util.*;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.chd.base.SysPage;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.base.util.SpreadTableJSUtil;
import com.chd.base.util.StringTool;
import com.chd.hrp.med.dao.info.basic.MedStockTypeMapper;
import com.chd.hrp.med.entity.MedStockType;
import com.chd.hrp.med.service.info.basic.MedStockTypeService;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Description:
 * 08118 采购类型
 * @Table:
 * MED_STOCK_TYPE
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 


@Service("medStockTypeService")
public class MedStockTypeServiceImpl implements MedStockTypeService {

	private static Logger logger = Logger.getLogger(MedStockTypeServiceImpl.class);
	//引入DAO操作
	@Resource(name = "medStockTypeMapper")
	private final MedStockTypeMapper medStockTypeMapper = null;
    
	/**
	 * @Description 
	 * 添加08118 采购类型<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String add(Map<String,Object> entityMap)throws DataAccessException{
		
		//获取对象08118 采购类型
		MedStockType medStockType = queryByCode(entityMap);

		if (medStockType != null) {

			return "{\"error\":\"数据重复,请重新添加.\"}";

		}
		
		try {
			
			int state = medStockTypeMapper.add(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addMedStockType\"}";

		}
		
	}
	/**
	 * @Description 
	 * 批量添加08118 采购类型<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addBatch(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
			medStockTypeMapper.addBatch(entityList);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addBatchMedStockType\"}";

		}
		
	}
	
		/**
	 * @Description 
	 * 更新08118 采购类型<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String update(Map<String,Object> entityMap)throws DataAccessException{
		
		try {
			
		  int state = medStockTypeMapper.update(entityMap);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 updateMedStockType\"}";

		}	
		
	}
	/**
	 * @Description 
	 * 批量更新08118 采购类型<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateBatch(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
		  medStockTypeMapper.updateBatch(entityList);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 updateBatchMedStockType\"}";

		}	
		
	}
	/**
	 * @Description 
	 * 删除08118 采购类型<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
    public String delete(Map<String, Object> entityMap) throws DataAccessException {
    	
    try {
			
			int state = medStockTypeMapper.delete(entityMap);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 deleteMedStockType\"}";

		}	
		
  }
    
	/**
	 * @Description 
	 * 批量删除08118 采购类型<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String deleteBatch(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
			medStockTypeMapper.deleteBatch(entityList);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 deleteBatchMedStockType\"}";

		}	
	}
	
	/**
	 * @Description 
	 * 查询结果集08118 采购类型<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String query(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<MedStockType> list = (List<MedStockType>) medStockTypeMapper.query(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<MedStockType> list = (List<MedStockType>) medStockTypeMapper.query(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
		
	}
	
	/**
	 * @Description 
	 * 获取对象08118 采购类型<BR> 
	 * @param  entityMap<BR>
	 *  参数为主键
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public <T>T queryByCode(Map<String,Object> entityMap)throws DataAccessException{
		return medStockTypeMapper.queryByCode(entityMap);
	}
	
	/**
	 * @Description 
	 * 获取08118 采购类型<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return MedStockType
	 * @throws DataAccessException
	*/
	@Override
	public <T>T queryByUniqueness(Map<String,Object> entityMap)throws DataAccessException{
		return medStockTypeMapper.queryByUniqueness(entityMap);
	}
	@Override
	public String addOrUpdate(Map<String, Object> entityMap)
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
	public String importMedStockType(Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		
		try {
			
			//1.判断表头是否为空
			String columns=entityMap.get("columns").toString();
			JSONArray jsonColumns = JSONObject.parseArray(columns);	
			if(jsonColumns==null || jsonColumns.size()==0){
				return "{\"error\":\"表头为空！\",\"state\":\"false\"}";
			}
			
			//2.判断数据是否为空
			String content=entityMap.get("content").toString();
			List<Map<String,List<String>>> liData=SpreadTableJSUtil.toListMap(content,1);
			if(liData==null || liData.size()==0){
				return "{\"error\":\"没有数据！\",\"state\":\"false\"}";
			}
			
			List<MedStockType> stockTypeList = (List<MedStockType>)medStockTypeMapper.query(entityMap);//查询采购类型
			Map<String,Object> stockTypeQueryMap = new HashMap<String,Object>();
			for(MedStockType mst : stockTypeList){
				stockTypeQueryMap.put(mst.getStock_type_code(),mst.getStock_type_code());
			}
			
			// 用于存储传的数据值,判断数据是否重复
			Map<String, String> exitMap = new HashMap<String, String>();
			// 用于记录重复数据
			StringBuffer err_sb = new StringBuffer();
			//用于存储添加数据
			List<Map<String,Object>> addList = new ArrayList<Map<String,Object>>();
			
			// 遍历导入数据
			for (Map<String, List<String>> item : liData) {
				
				List<String> stock_type_code = item.get("采购类型编码");
				List<String> stock_type_name = item.get("采购类型名称");
				List<String> is_stop = item.get("是否停用");
				
				if (stock_type_code.get(1) == null && stock_type_name.get(1) == null) {//去除空行
					continue;
				}
				
				if (stock_type_code.get(1) == null) {
					return "{\"warn\":\"采购类型编码为空！\",\"state\":\"false\",\"row_cell\":\"" + stock_type_code.get(0) + "\"}";
				}else{
					if(stockTypeQueryMap.get(stock_type_code.get(1)) != null){
						return "{\"warn\":\"采购类型编码已经存在！\",\"state\":\"false\",\"row_cell\":\"" + stock_type_code.get(0) + "\"}";
					}
				}
				
				if (stock_type_name.get(1) == null) {
					return "{\"warn\":\"采购类型名称为空！\",\"state\":\"false\",\"row_cell\":\"" + stock_type_name.get(0) + "\"}";
				}
				
				if (is_stop.get(1) == null) {
					return "{\"warn\":\"是否停用为空！\",\"state\":\"false\",\"row_cell\":\"" + is_stop.get(0) + "\"}";
				}
				
				// 以职工编码+职工名称+科室名称+职务名称为键值,判断导入数据是否重复
				String key = stock_type_code.get(1) + stock_type_name.get(1);
				if (exitMap.get(key) != null) {
					err_sb.append(stock_type_code.get(1) + "采购类型编码," + stock_type_name.get(1) + "采购类型名称");
				} else {
					exitMap.put(key, key);
				}
				
				
				Map<String,Object> addMap = new HashMap<String,Object>();
				addMap.put("group_id", entityMap.get("group_id"));
				addMap.put("hos_id", entityMap.get("hos_id"));
				addMap.put("copy_code", entityMap.get("copy_code"));
				addMap.put("stock_type_code", stock_type_code.get(1));
				addMap.put("stock_type_name", stock_type_name.get(1));
				
				String stop_code = is_stop.get(1);
				if("是".equals(stop_code) || "1".equals(stop_code)){
					addMap.put("is_stop", 1);
				}else if("否".equals(stop_code) || "0".equals(stop_code)){
					addMap.put("is_stop",0);
				}else{
					return "{\"warn\":\"是否停用值错误！\",\"state\":\"false\",\"row_cell\":\"" + is_stop.get(0) + "\"}";
				}
				
				
				addMap.put("wbx_code", StringTool.toWuBi(stock_type_name.get(1)));
				addMap.put("spell_code",StringTool.toPinyinShouZiMu(stock_type_name.get(1)));
				addMap.put("note","");
				addMap.put("sort_code","");
				
				addList.add(addMap);
			}
			
			if (err_sb.length() > 0) {// 重复数据是否存在
				return "{\"warn\":\"以下数据【" + err_sb.toString() + "】重复！\",\"state\":\"false\"}";
			}
			
			
			medStockTypeMapper.addBatch(addList);
			
			return "{\"msg\":\"导入成功.\",\"state\":\"true\"}";
			
		} catch (Exception e) {
			// TODO: handle exception
			
			logger.error(e.getMessage(), e);

			throw new SysException("{\"error\":\"操作失败 \"}");
		}
		
	}
}
