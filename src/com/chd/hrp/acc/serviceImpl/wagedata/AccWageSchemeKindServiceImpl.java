/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/

package com.chd.hrp.acc.serviceImpl.wagedata;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.chd.base.SessionManager;
import com.chd.base.SysPage;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.hrp.acc.dao.wagedata.AccWageSchemeKindMapper;
import com.chd.hrp.acc.dao.wagedata.AccWageSchemeMapper;
import com.chd.hrp.acc.entity.AccWageSKind;
import com.chd.hrp.acc.entity.AccWageScheme;
import com.chd.hrp.acc.entity.AccWageSchemeKind;
import com.chd.hrp.acc.service.wagedata.AccWageSchemeKindService;
import com.github.pagehelper.PageInfo;

/**
* @Title. @Description.
* 工资方案职工<BR> 
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


@Service("accWageSchemeKindService")
public class AccWageSchemeKindServiceImpl implements AccWageSchemeKindService {

	private static Logger logger = Logger.getLogger(AccWageSchemeKindServiceImpl.class);
	
	@Resource(name = "accWageSchemeKindMapper")
	private final AccWageSchemeKindMapper accWageSchemeKindMapper = null;
	
	@Resource(name = "accWageSchemeMapper")
	private final AccWageSchemeMapper accWageSchemeMapper = null;
	
	/**
	 * @Description 
	 * 工资方案职工<BR> 添加AccWageSchemeKind
	 * @param AccWageSchemeKind entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addAccWageSchemeKind(Map<String,Object> entityMap)throws DataAccessException{
		
		AccWageSchemeKind AccWageSchemeKind = queryAccWageSchemeKindByCode(entityMap);

		if (AccWageSchemeKind != null) {

			return "{\"error\":\"编码：" + entityMap.get("wage_code").toString() + "重复.\"}";

		}
		
		try {
			
			accWageSchemeKindMapper.addAccWageSchemeKind(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码 addAccWageSchemeKind\"}";

		}

	}
	
	/**
	 * @Description 
	 * 工资方案职工<BR> 批量添加AccWageSchemeKind
	 * @param  AccWageSchemeKind entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addBatchAccWageSchemeKind(List<Map<String,Object>> entityList)throws DataAccessException{

		try {
			
			accWageSchemeKindMapper.addBatchAccWageSchemeKind(entityList);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码 addBatchAccWageSchemeKind\"}";

		}
	}
	
	/**
	 * @Description 工资方案职工<BR>
	 *              查询AccWageSchemeKind分页
	 * @param entityMap
	 *            RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String queryAccWageSchemeKind(Map<String, Object> entityMap) throws DataAccessException {
		SysPage sysPage = new SysPage();
		sysPage = (SysPage) entityMap.get("sysPage");
		if (sysPage.getTotal() == -1) {
			List<AccWageSchemeKind> list = accWageSchemeKindMapper.queryAccWageSchemeKind(entityMap);
			return ChdJson.toJson(list);
		} else {
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			List<AccWageSchemeKind> list = accWageSchemeKindMapper.queryAccWageSchemeKind(entityMap, rowBounds);
			PageInfo page = new PageInfo(list);
			return ChdJson.toJson(list, page.getTotal());
		}
	}
	
	/**
	 * @Description 
	 * 工资方案职工<BR> 查询AccWageSchemeKindByCode
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public AccWageSchemeKind queryAccWageSchemeKindByCode(Map<String,Object> entityMap)throws DataAccessException{
		
		return accWageSchemeKindMapper.queryAccWageSchemeKindByCode(entityMap);
		
	}
	
	/**
	 * @Description 
	 * 工资方案职工<BR> 批量删除AccWageSchemeKind
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String deleteBatchAccWageSchemeKind(List<Map<String,Object>> entityList)throws DataAccessException{

		try {

				int state = accWageSchemeKindMapper.deleteBatchAccWageSchemeKind(entityList);
				
				return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码  deleteBatchAccWageSchemeKind\"}";

		}
		
	}
	
	/**
	 * @Description 
	 * 工资方案职工<BR> 删除AccWageSchemeKind
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
  public String deleteAccWageSchemeKind(Map<String, Object> entityMap) throws DataAccessException {
		
		try {
			accWageSchemeKindMapper.deleteAccWageSchemeKind(entityMap);
				
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码  deleteAccWageSchemeKind\"}";

		}
    }
	
	/**
	 * @Description 
	 * 工资方案职工<BR> 更新AccWageSchemeKind
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateAccWageSchemeKind(Map<String,Object> entityMap)throws DataAccessException{

		try {

			accWageSchemeKindMapper.updateAccWageSchemeKind(entityMap);

			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updateAccWageSchemeKind\"}";

		}
	}
	
	/**
	 * @Description 
	 * 工资方案职工<BR> 批量更新AccWageSchemeKind
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateBatchAccWageSchemeKind(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {

			accWageSchemeKindMapper.updateBatchAccWageSchemeKind(entityList);

			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updateAccWageSchemeKind\"}";

		}
		
	}
	
	/**
	 * @Description 
	 * 工资方案职工<BR> 导入AccWageSchemeKind
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String importAccWageSchemeKind(Map<String,Object> entityMap)throws DataAccessException{

		try {

			

			return "{\"msg\":\"导入成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"导入失败 数据库异常 请联系管理员! 错误编码  import{className}\"}";

		}
	}

	// 保存工资方案与职工分类关系
	@Override
	public String saveAccWageSKind(Map<String, Object> paramMap) throws DataAccessException {
		paramMap.put("group_id", SessionManager.getGroupId());
		paramMap.put("hos_id", SessionManager.getHosId());
		paramMap.put("copy_code", SessionManager.getCopyCode());
		try {
			List<AccWageSKind> list = JSONArray
					.parseArray(String.valueOf(paramMap.get("paramVo")), AccWageSKind.class);
			Long schemeId;
			if(paramMap.get("scheme_id") != null && paramMap.get("scheme_id").toString() != ""){
				schemeId = Long.valueOf(paramMap.get("scheme_id").toString());
			}else{
				AccWageScheme scheme = accWageSchemeMapper.queryAccWageSchemeByCode(paramMap);
				schemeId = scheme.getScheme_id();
			}
			if(list.size() > 0){
				for(AccWageSKind sk : list){
					sk.setScheme_id(schemeId);
				}
				accWageSchemeKindMapper.deleteAccWageSKindBySchemeId(paramMap);
				accWageSchemeKindMapper.addAccWageSKindBatch(list);
			}
			return "{\"msg\":\"保存成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"保存失败\",\"state\":\"false\"}");
		}
	}

	// 查询工资方案与职工类别的关系
	@Override
	public String queryAccWageSKind(Map<String, Object> paramMap) throws DataAccessException{
		SysPage sysPage = new SysPage();
		sysPage = (SysPage) paramMap.get("sysPage");
		if (sysPage.getTotal() == -1) {
			List<AccWageSKind> list = accWageSchemeKindMapper.queryAccWageSKind(paramMap);
			return ChdJson.toJson(list);
		} else {
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			List<AccWageSKind> list = accWageSchemeKindMapper.queryAccWageSKind(paramMap, rowBounds);
			PageInfo page = new PageInfo(list);
			return ChdJson.toJson(list, page.getTotal());
		}
	}
	
	// 删除工资方案与职工类别的关系
	@Override
	public String deleteAccWageSKind(Map<String, Object> paramMap) throws DataAccessException{
		try{
			List<AccWageSKind> list = JSONArray
					.parseArray(String.valueOf(paramMap.get("paramVo")), AccWageSKind.class);
			accWageSchemeKindMapper.deleteAccWageSKindBatch(list);
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		}catch(Exception e){
			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"删除失败\",\"state\":\"false\"}");
		}
	}
}