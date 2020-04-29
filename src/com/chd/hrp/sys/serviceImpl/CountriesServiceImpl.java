/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/

package com.chd.hrp.sys.serviceImpl;

import java.util.*;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.SysPage;
import com.chd.base.util.ChdJson;
import com.chd.base.util.JsonListBeanUtil;
import com.chd.base.util.StringTool;
import com.chd.hrp.sys.dao.CountriesMapper;
import com.chd.hrp.sys.entity.Copy;
import com.chd.hrp.sys.entity.Countries;
import com.chd.hrp.sys.service.CountriesService;
import com.github.pagehelper.PageInfo;

/**
* @Title. @Description.
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


@Service("countriesService")
public class CountriesServiceImpl implements CountriesService {

	private static Logger logger = Logger.getLogger(CountriesServiceImpl.class);
	
	@Resource(name = "countriesMapper")
	private final CountriesMapper countriesMapper = null;
    
	/**
	 * @Description 添加Countries
	 * @param Countries entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addCountries(Map<String,Object> entityMap)throws DataAccessException{
		
		Countries countries = queryCountriesByCode(entityMap);

		if (countries != null) {

			return "{\"error\":\"编码：" +countries.getCountries_code()+ "已存在.\"}";

		}
		entityMap.put("spell_code", StringTool.toPinyinShouZiMu(entityMap.get("countries_name").toString()));
		entityMap.put("wbx_code", StringTool.toWuBi(entityMap.get("countries_name").toString()));
		try {
			
			countriesMapper.addCountries(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码 addCountries\"}";

		}

	}
	
	/**
	 * @Description 批量添加Countries
	 * @param  Countries entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addBatchCountries(List<Map<String,Object>> entityList)throws DataAccessException{

		try {
			
			countriesMapper.addBatchCountries(entityList);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码 addBatchCountries\"}";

		}
	}
	
	/**
	 * @Description 查询Countries分页
	 * @param  entityMap RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String queryCountries(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
		
		List<Countries> list = countriesMapper.queryCountries(entityMap, rowBounds);
		
		PageInfo page = new PageInfo(list);
		
		return ChdJson.toJson(list, page.getTotal());
		
	}
	
	/**
	 * @Description 查询CountriesByCode
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public Countries queryCountriesByCode(Map<String,Object> entityMap)throws DataAccessException{
		
		return countriesMapper.queryCountriesByCode(entityMap);
		
	}
	
	/**
	 * @Description 批量删除Countries
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String deleteBatchCountries(List<Map<String,Object>> entityList)throws DataAccessException{

		try {

				int state = countriesMapper.deleteBatchCountries(entityList);
				return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码  deleteBatchCountries\"}";

		}
		
	}
	
		/**
	 * @Description 删除Countries
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
  public String deleteCountries(Map<String, Object> entityMap) throws DataAccessException {
		
		try {
				countriesMapper.deleteCountries(entityMap);
				return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码  deleteCountries\"}";

		}
    }
	
	/**
	 * @Description 更新Countries
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateCountries(Map<String,Object> entityMap)throws DataAccessException{

		try {
			entityMap.put("spell_code", StringTool.toPinyinShouZiMu(entityMap.get("countries_name").toString()));
			entityMap.put("wbx_code", StringTool.toWuBi(entityMap.get("countries_name").toString()));
			countriesMapper.updateCountries(entityMap);

			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updateCountries\"}";

		}
	}
	
	/**
	 * @Description 批量更新Countries
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateBatchCountries(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {

			countriesMapper.updateBatchCountries(entityList);

			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updateCountries\"}";

		}
		
	}
	
	/**
	 * @Description 导入Countries
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String importCountries(Map<String,Object> entityMap)throws DataAccessException{

		try {

			

			return "{\"msg\":\"导入成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"导入失败 数据库异常 请联系管理员! 错误编码  import{className}\"}";

		}
	}
}
