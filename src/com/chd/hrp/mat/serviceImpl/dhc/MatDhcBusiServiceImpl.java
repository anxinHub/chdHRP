/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.mat.serviceImpl.dhc;

import java.util.*;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.datasource.DataSource;
import com.chd.base.exception.SysException;
import com.chd.base.util.JsonListMapUtil;
import com.chd.hrp.mat.dao.dhc.MatDhcBusiMapper;
import com.chd.hrp.mat.service.dhc.MatDhcBusiService;

/**
 * @Description:  东华基础字典读取
 * @Table: 
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
@Service("matDhcBusiService")
public class MatDhcBusiServiceImpl implements MatDhcBusiService {

	private static Logger logger = Logger.getLogger(MatDhcBusiServiceImpl.class);
	//引入DAO操作
	@Resource(name = "matDhcBusiMapper")
	private final MatDhcBusiMapper matDhcBusiMapper = null;

	/**
	 * @Description 
	 * 获取左侧树形结构<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String queryMatDhcBusi(Map<String,Object> entityMap) throws DataAccessException{
		
		return JsonListMapUtil.listToJson(matDhcBusiMapper.queryMatDhcBusi(entityMap));
	}
	
	/**
	 * @Description 
	 * 导入东华数据<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	@DataSource("cache")
	public synchronized Map<String,Object> impMatDhcBusi(Map<String,Object> entityMap)throws DataAccessException{

		Map<String,Object> retMap = new HashMap<String, Object>();
		retMap.put("state", true);
		try {	
			/**
			 * busi_type 业务类型：1(入库), 2(出库), 3(发票), 4(付款)
			 * imp_type 导入方式：1(追加), 2(覆盖)
			 */
			/*
			if(entityMap.get("busi_type") == null || "".equals(entityMap.get("busi_type").toString())){
				retMap.put("state", false);
				retMap.put("error", "请选择导入的业务类型！");
				return retMap;
			}
			if(entityMap.get("imp_type") == null || "".equals(entityMap.get("imp_type").toString())){
				retMap.put("state", false);
				retMap.put("error", "请选择导入的方式！");
				return retMap;
			}
			*/
			
			int busi_type = 9;//Integer.parseInt(entityMap.get("busi_type").toString());
			int imp_type = 1;//Integer.parseInt(entityMap.get("imp_type").toString());
			
			//读取东华DHC业务数据
			List<Map<String, Object>> dhcMainList = new ArrayList<Map<String,Object>>();
			List<Map<String, Object>> dhcDetailList = new ArrayList<Map<String,Object>>();
			
			//判断需要读取的数据
			if(busi_type == 1){
				//入库数据
				dhcMainList = queryDhcInMainBusi(entityMap);
				dhcDetailList = queryDhcInDetailBusi(entityMap);
				//判断数据是否覆盖
				if(imp_type == 2){
					//删除已有数据
					deleteMatIn(entityMap);
				}
				//插入数据
				retMap = addMatIn(dhcMainList, dhcDetailList);
				
			}else if(busi_type == 2){
				//出库数据
				dhcMainList = queryDhcOutMainBusi(entityMap);
				dhcDetailList = queryDhcOutDetailBusi(entityMap);
				//判断数据是否覆盖
				if(imp_type == 2){
					//删除已有数据
					deleteMatOut(entityMap);
				}
				//插入数据
				retMap = addMatOut(dhcMainList, dhcDetailList);
				
			}else if(busi_type == 3){
				//发票数据
				dhcMainList = queryDhcPrePayMainBusi(entityMap);
				dhcDetailList = queryDhcPrePayDetailBusi(entityMap);
				//判断数据是否覆盖
				if(imp_type == 2){
					//删除已有数据
					deleteMatPrePay(entityMap);
				}
				//插入数据
				retMap = addMatPrePay(dhcMainList, dhcDetailList);
				
			}else if(busi_type == 4){
				//付款数据
				dhcMainList = queryDhcPayMainBusi(entityMap);
				dhcDetailList = queryDhcPayDetailBusi(entityMap);
				//判断数据是否覆盖
				if(imp_type == 2){
					//删除已有数据
					deleteMatPay(entityMap);
				}
				//插入数据
				retMap = addMatPay(dhcMainList, dhcDetailList);
				
			}else{
				//dhcMainList = queryDhcInMainBusi(entityMap);
				dhcMainList = queryDhcInDetailBusi(entityMap);
				
				retMap.put("state", false);
				retMap.put("error", "请正确选择导入的业务类型！");
				return retMap;
			}
			
			retMap.put("msg", "操作成功！");
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);

			throw new SysException("操作失败！");
		}	
		
		return retMap;
	}
	
	/**
	 * @Description 
	 * 查询东华数据<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	@Override
	@DataSource("cache")
	public List<Map<String, Object>> queryDhcInMainBusi(Map<String,Object> entityMap)throws DataAccessException{

		List<Map<String,Object>> retList = new ArrayList<Map<String,Object>>();
		try {	
			retList = matDhcBusiMapper.queryDhcInMainBusi(entityMap);
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);

			throw new SysException("操作失败！");
		}	
		
		return retList;
	}
	@Override
	public List<Map<String, Object>> queryDhcInDetailBusi(Map<String,Object> entityMap)throws DataAccessException{

		List<Map<String,Object>> retList = new ArrayList<Map<String,Object>>();
		try {	
			retList = matDhcBusiMapper.queryDhcInDetailBusi(entityMap);
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);

			throw new SysException("操作失败！");
		}	
		
		return retList;
	}

	@Override
	@DataSource("cache")
	public List<Map<String, Object>> queryDhcOutMainBusi(Map<String,Object> entityMap)throws DataAccessException{

		List<Map<String,Object>> retList = new ArrayList<Map<String,Object>>();
		try {	
			retList = matDhcBusiMapper.queryDhcOutMainBusi(entityMap);
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);

			throw new SysException("操作失败！");
		}	
		
		return retList;
	}
	@Override
	@DataSource("cache")
	public List<Map<String, Object>> queryDhcOutDetailBusi(Map<String,Object> entityMap)throws DataAccessException{

		List<Map<String,Object>> retList = new ArrayList<Map<String,Object>>();
		try {	
			retList = matDhcBusiMapper.queryDhcOutDetailBusi(entityMap);
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);

			throw new SysException("操作失败！");
		}	
		
		return retList;
	}

	@Override
	@DataSource("cache")
	public List<Map<String, Object>> queryDhcPrePayMainBusi(Map<String,Object> entityMap)throws DataAccessException{

		List<Map<String,Object>> retList = new ArrayList<Map<String,Object>>();
		try {	
			retList = matDhcBusiMapper.queryDhcPrePayMainBusi(entityMap);
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);

			throw new SysException("操作失败！");
		}	
		
		return retList;
	}
	@Override
	@DataSource("cache")
	public List<Map<String, Object>> queryDhcPrePayDetailBusi(Map<String,Object> entityMap)throws DataAccessException{

		List<Map<String,Object>> retList = new ArrayList<Map<String,Object>>();
		try {	
			retList = matDhcBusiMapper.queryDhcPrePayDetailBusi(entityMap);
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);

			throw new SysException("操作失败！");
		}	
		
		return retList;
	}

	@Override
	@DataSource("cache")
	public List<Map<String, Object>> queryDhcPayMainBusi(Map<String,Object> entityMap)throws DataAccessException{

		List<Map<String,Object>> retList = new ArrayList<Map<String,Object>>();
		try {	
			retList = matDhcBusiMapper.queryDhcPayMainBusi(entityMap);
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);

			throw new SysException("操作失败！");
		}	
		
		return retList;
	}
	@Override
	@DataSource("cache")
	public List<Map<String, Object>> queryDhcPayDetailBusi(Map<String,Object> entityMap)throws DataAccessException{

		List<Map<String,Object>> retList = new ArrayList<Map<String,Object>>();
		try {	
			retList = matDhcBusiMapper.queryDhcPayDetailBusi(entityMap);
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);

			throw new SysException("操作失败！");
		}	
		
		return retList;
	}
	
	/**
	 * @Description 
	 * 插入HRP数据表<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	@Override
	public Map<String, Object> addMatIn(List<Map<String,Object>> mainList, List<Map<String, Object>> detailList)throws DataAccessException{

		Map<String,Object> retMap = new HashMap<String, Object>();
		retMap.put("state", true);
		try {
			for(Map<String, Object> mainMap : mainList){
				//补充必填项
				
			}
			for(Map<String, Object> detailMap : detailList){
				//补充必填项
				
			}
			
			//插入数据
			matDhcBusiMapper.addMatInMain(mainList);
			matDhcBusiMapper.addMatInDetail(detailList);
			
			//组装数据
			retMap.put("msg", "操作成功！");
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);

			throw new SysException("操作失败！");
		}	
		
		return retMap;
	}
	@Override
	public Map<String, Object> addMatOut(List<Map<String,Object>> mainList, List<Map<String, Object>> detailList)throws DataAccessException{

		Map<String,Object> retMap = new HashMap<String, Object>();
		retMap.put("state", true);
		try {	
			for(Map<String, Object> mainMap : mainList){
				//补充必填项
				
			}
			for(Map<String, Object> detailMap : detailList){
				//补充必填项
				
			}
			
			//插入数据
			matDhcBusiMapper.addMatOutMain(mainList);
			matDhcBusiMapper.addMatOutDetail(detailList);
			
			retMap.put("msg", "操作成功！");
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);

			throw new SysException("操作失败！");
		}	
		
		return retMap;
	}
	
	@Override
	public Map<String, Object> addMatPrePay(List<Map<String,Object>> mainList, List<Map<String, Object>> detailList)throws DataAccessException{

		Map<String,Object> retMap = new HashMap<String, Object>();
		retMap.put("state", true);
		try {	
			for(Map<String, Object> mainMap : mainList){
				//补充必填项
				
			}
			for(Map<String, Object> detailMap : detailList){
				//补充必填项
				
			}
			
			//插入数据
			matDhcBusiMapper.addMatPrePayMain(mainList);
			matDhcBusiMapper.addMatPrePayDetail(detailList);
			
			retMap.put("msg", "操作成功！");
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);

			throw new SysException("操作失败！");
		}	
		
		return retMap;
	}
	
	@Override
	public Map<String, Object> addMatPay(List<Map<String,Object>> mainList, List<Map<String, Object>> detailList)throws DataAccessException{

		Map<String,Object> retMap = new HashMap<String, Object>();
		retMap.put("state", true);
		try {	
			for(Map<String, Object> mainMap : mainList){
				//补充必填项
				
			}
			for(Map<String, Object> detailMap : detailList){
				//补充必填项
				
			}
			
			//插入数据
			matDhcBusiMapper.addMatPayMain(mainList);
			matDhcBusiMapper.addMatPayDetail(detailList);
			
			retMap.put("msg", "操作成功！");
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);

			throw new SysException("操作失败！");
		}	
		
		return retMap;
	}
	
	/**
	 * @Description 
	 * 覆盖时需删除HRP数据表<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	@Override
	public Map<String, Object> deleteMatIn(Map<String,Object> mainMap)throws DataAccessException{

		Map<String,Object> retMap = new HashMap<String, Object>();
		retMap.put("state", true);
		try {	
			//暂无该业务
			retMap.put("msg", "操作成功！");
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);

			throw new SysException("操作失败！");
		}	
		
		return retMap;
	}
	
	@Override
	public Map<String, Object> deleteMatOut(Map<String,Object> entityMap)throws DataAccessException{

		Map<String,Object> retMap = new HashMap<String, Object>();
		retMap.put("state", true);
		try {	
			//暂无该业务
			retMap.put("msg", "操作成功！");
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);

			throw new SysException("操作失败！");
		}	
		
		return retMap;
	}
	
	@Override
	public Map<String, Object> deleteMatPrePay(Map<String,Object> entityMap)throws DataAccessException{

		Map<String,Object> retMap = new HashMap<String, Object>();
		retMap.put("state", true);
		try {	
			//暂无该业务
			retMap.put("msg", "操作成功！");
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);

			throw new SysException("操作失败！");
		}	
		
		return retMap;
	}
	
	@Override
	public Map<String, Object> deleteMatPay(Map<String,Object> entityMap)throws DataAccessException{

		Map<String,Object> retMap = new HashMap<String, Object>();
		retMap.put("state", true);
		try {	
			//暂无该业务
			retMap.put("msg", "操作成功！");
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);

			throw new SysException("操作失败！");
		}	
		
		return retMap;
	}
	
	/**
	 * @Description 
	 * 删除数据<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public Map<String,Object> deleteMatDhcBusi(List<Map<String,Object>> entityList)throws DataAccessException{
		
		Map<String,Object> retMap = new HashMap<String, Object>();
		retMap.put("state", true);
		try {	
			//暂无该业务
			retMap.put("msg", "操作成功！");
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);

			throw new SysException("操作失败！");
		}	
		
		return retMap;
	}
}
