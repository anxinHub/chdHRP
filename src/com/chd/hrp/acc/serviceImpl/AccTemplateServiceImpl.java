/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/

package com.chd.hrp.acc.serviceImpl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.SysPage;
import com.chd.base.util.ChdJson;
import com.chd.hrp.acc.dao.AccTemplateMapper;
import com.chd.hrp.acc.dao.vouch.AccVouchMapper;
import com.chd.hrp.acc.entity.AccTemplate;
import com.chd.hrp.acc.entity.AccVouch;
import com.chd.hrp.acc.service.AccTemplateService;
import com.github.pagehelper.PageInfo;

/**
* @Title. @Description.
* 凭证模板主表<BR> 
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


@Service("accTemplateService")
public class AccTemplateServiceImpl implements AccTemplateService {

	private static Logger logger = Logger.getLogger(AccTemplateServiceImpl.class);
	
	@Resource(name = "accTemplateMapper")
	private final AccTemplateMapper accTemplateMapper = null;
    
	@Resource(name = "accVouchMapper")
	private final AccVouchMapper accVouchMapper = null;
	/**
	 * @Description 
	 * 凭证模板主表<BR> 添加AccTemplate
	 * @param AccTemplate entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addAccTemplate(Map<String,Object> entityMap)throws DataAccessException{
		
		AccTemplate accTemplate = queryAccTemplateByCode(entityMap);

		if (accTemplate != null) {

			return "{\"error\":\"编码：" + entityMap.get("item_code").toString() + "重复.\"}";

		}
		
		try {
			
			accTemplateMapper.addAccTemplate(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码 addAccTemplate\"}";

		}

	}
	
	/**
	 * @Description 
	 * 凭证模板主表<BR> 批量添加AccTemplate
	 * @param  AccTemplate entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addBatchAccTemplate(List<Map<String,Object>> entityList)throws DataAccessException{

		try {
			
			accTemplateMapper.addBatchAccTemplate(entityList);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码 addBatchAccTemplate\"}";

		}
	}
	
	/**
	 * @Description 
	 * 凭证模板主表<BR> 查询AccTemplate分页
	 * @param  entityMap RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String queryAccTemplate(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
		List<AccTemplate> list = accTemplateMapper.queryAccTemplate(entityMap, rowBounds);
		PageInfo page = new PageInfo(list);
		return ChdJson.toJson(list, page.getTotal());
	}
	
	/**
	 * @Description 
	 * 凭证模板主表<BR> 查询AccTemplateByCode
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public AccTemplate queryAccTemplateByCode(Map<String,Object> entityMap)throws DataAccessException{
		
		return accTemplateMapper.queryAccTemplateByCode(entityMap);
		
	}
	
	/**
	 * @Description 
	 * 凭证模板主表<BR> 批量删除AccTemplate
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String deleteBatchAccTemplate(List<Map<String,Object>> entityList)throws DataAccessException{

		try {

				int state = accTemplateMapper.deleteBatchAccTemplate(entityList);
				return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码  deleteBatchAccTemplate\"}";

		}
		
	}
	
	/**
	 * @Description 
	 * 凭证模板主表<BR> 删除AccTemplate
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
  public String deleteAccTemplate(Map<String, Object> entityMap) throws DataAccessException {
		
		try {
				accTemplateMapper.deleteAccTemplate(entityMap);
				return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码  deleteAccTemplate\"}";

		}
    }
	
	/**
	 * @Description 
	 * 凭证模板主表<BR> 更新AccTemplate
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateAccTemplate(Map<String,Object> entityMap)throws DataAccessException{

		try {

			accTemplateMapper.updateAccTemplate(entityMap);

			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updateAccTemplate\"}";

		}
	}
	
	/**
	 * @Description 
	 * 凭证模板主表<BR> 批量更新AccTemplate
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateBatchAccTemplate(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {

			accTemplateMapper.updateBatchAccTemplate(entityList);

			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updateAccTemplate\"}";

		}
		
	}
	
	/**
	 * @Description 
	 * 凭证模板主表<BR> 导入AccTemplate
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String importAccTemplate(Map<String,Object> entityMap)throws DataAccessException{

		try {

			

			return "{\"msg\":\"导入成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"导入失败 数据库异常 请联系管理员! 错误编码  import{className}\"}";

		}
	}

	@Override
	public String queryAccVouchSubj(Map<String, Object> entityMap) throws DataAccessException {
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
		
		List<AccVouch> list = accVouchMapper.queryAccVouchSubjCode(entityMap, rowBounds);
		
		PageInfo page = new PageInfo(list);
		
		return ChdJson.toJson(list, page.getTotal());
		
	}
	
	@Override
	public String extendAccTemplate(Map<String,Object> entityList)throws DataAccessException{

		try {
			
			accTemplateMapper.queryAccAddTemplateAll(entityList);
			
			return "{\"msg\":\"继承成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"继承失败 数据库异常 请联系管理员! 错误编码  extendAccTemplate\"}";

		}
		
	}
}
