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
import com.chd.hrp.mat.dao.dhc.MatDhcDictMapper;
import com.chd.hrp.mat.service.dhc.MatDhcDictService;

/**
 * @Description:  东华基础字典读取
 * @Table: 
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
@Service("matDhcDictService")
public class MatDhcDictServiceImpl implements MatDhcDictService {

	private static Logger logger = Logger.getLogger(MatDhcDictServiceImpl.class);
	//引入DAO操作
	@Resource(name = "matDhcDictMapper")
	private final MatDhcDictMapper matDhcDictMapper = null;

	/**
	 * @Description 
	 * 获取左侧树形结构<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String queryMatDhcDict(Map<String,Object> entityMap) throws DataAccessException{
		
		return JsonListMapUtil.listToJson(matDhcDictMapper.queryMatDhcDict(entityMap));
	}
	
	/**
	 * @Description 
	 * 导入东华数据<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public synchronized Map<String,Object> impMatDhcDict(Map<String,Object> entityMap)throws DataAccessException{

		Map<String,Object> retMap = new HashMap<String, Object>();
		retMap.put("state", true);
		try {	
			/**
			 * busi_type 业务类型：1(物资类别), 2(材料字典), 3(仓库字典), 4(科室字典), 5(供应商字典), 6(资金来源), 7(支付方式), 8(项目信息)
			 * imp_type 导入方式：1(追加), 2(覆盖)
			 */
			if(entityMap.get("busi_type") == null || "".equals(entityMap.get("busi_type").toString())){
				retMap.put("state", false);
				retMap.put("error", "请选择导入的字典！");
				return retMap;
			}
			if(entityMap.get("imp_type") == null || "".equals(entityMap.get("imp_type").toString())){
				retMap.put("state", false);
				retMap.put("error", "请选择导入的方式！");
				return retMap;
			}
			int busi_type = Integer.parseInt(entityMap.get("busi_type").toString());
			//由于需要生成唯一ID，字典暂不支持覆盖
			//int imp_type = Integer.parseInt(entityMap.get("imp_type").toString());
			//读取东华DHC字典数据
			List<Map<String, Object>> dhcList = new ArrayList<Map<String,Object>>();
			if(busi_type == 1){
				//物资类别
				entityMap.put("p_code", "TYPE_CODE");
				entityMap.put("p_name", "TYPE_NAME");
				entityMap.put("p_table", "");
				dhcList = queryDhcDict(entityMap);
			}else if(busi_type == 2){
				//材料字典
			}else if(busi_type == 3){
				//仓库字典
			}else if(busi_type == 4){
				//科室字典
			}else if(busi_type == 5){
				//供应商字典
			}else if(busi_type == 7){
				//资金来源
			}else if(busi_type == 8){
				//支付方式
			}else if(busi_type == 9){
				//项目信息
			}else{
				retMap.put("state", false);
				retMap.put("error", "请正确选择导入的字典！");
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
	public List<Map<String, Object>> queryDhcDict(Map<String,Object> entityMap)throws DataAccessException{

		List<Map<String,Object>> retList = new ArrayList<Map<String,Object>>();
		try {	
			//暂无该业务
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
	public Map<String, Object> addMatTypeDict(List<Map<String,Object>> entityList)throws DataAccessException{

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
	public Map<String, Object> addMatInvDict(List<Map<String,Object>> entityList)throws DataAccessException{

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
	public Map<String, Object> addHosStoreDict(List<Map<String,Object>> entityList)throws DataAccessException{

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
	public Map<String, Object> addHosDeptDict(List<Map<String,Object>> entityList)throws DataAccessException{

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
	public Map<String, Object> addHosSupDict(List<Map<String,Object>> entityList)throws DataAccessException{

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
	public Map<String, Object> addMatSourceDict(List<Map<String,Object>> entityList)throws DataAccessException{

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
	public Map<String, Object> addMatPayTypeDict(List<Map<String,Object>> entityList)throws DataAccessException{

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
	public Map<String, Object> addMatProjDict(List<Map<String,Object>> entityList)throws DataAccessException{

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
	public Map<String,Object> deleteMatDhcDict(List<Map<String,Object>> entityList)throws DataAccessException{
		
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
