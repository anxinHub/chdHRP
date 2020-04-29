/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/

package com.chd.hrp.acc.serviceImpl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.MyConfig;
import com.chd.base.SessionManager;
import com.chd.base.SysPage;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.hrp.acc.dao.AccSupAttrMapper;
import com.chd.hrp.acc.entity.AccSupAttr;
import com.chd.hrp.acc.service.AccSupAttrService;
import com.chd.hrp.sys.dao.SupDictMapper;
import com.chd.hrp.sys.dao.SupMapper;
import com.chd.hrp.sys.service.SupService;
import com.chd.hrp.sys.service.base.SysBaseService;
import com.github.pagehelper.PageInfo;

/**
* @Title. @Description.
* 供应商字典属性表<BR> 
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


@Service("accSupAttrService")
public class AccSupAttrServiceImpl implements AccSupAttrService {

	private static Logger logger = Logger.getLogger(AccSupAttrServiceImpl.class);
	
	@Resource(name = "accSupAttrMapper")
	private final AccSupAttrMapper accSupAttrMapper = null;
	
	@Resource(name = "sysBaseService")
	private final SysBaseService sysBaseService = null;
	
	@Resource(name = "supService")
	private final SupService supService = null;
	
	@Resource(name = "supMapper")
	private final SupMapper supMapper = null;
	
	@Resource(name = "supDictMapper")
	private final SupDictMapper supDictMapper = null;
    
	/**
	 * @Description 
	 * 供应商字典属性表<BR> 添加AccSupAttr
	 * @param AccSupAttr entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addAccSupAttr(Map<String,Object> entityMap)throws DataAccessException{
		String supJson = "";
		//获取编码规则
		Map<String, Map<String, Object>> mv = sysBaseService.getHosRulesList(entityMap);
		
		//供应商编码自动生成是否使用供应商类别做为前缀
		String p98006 = MyConfig.getSysPara(SessionManager.getGroupId(), "0", "0", "98006");
		
		if (null != mv) {
			if (mv.get("HOS_SUP").get("is_auto").toString().equals("1")) {
				if (p98006.equals("1")) {
					supJson = supService.addSupType(entityMap);
				} else {
					// 台州单独自动生成编码规则
					supJson = supService.addSupNotType(entityMap);
					// supJson = supService.addSupTz(mapVo);
				}
				
			} else {
				supJson = supService.addSup(entityMap);
			}
		} else {
			supJson = supService.addSup(entityMap);
		}
		
		Long sup_id =supMapper.querySupCurrentSequence();
		
		entityMap.put("sup_id", sup_id);
		
		try {
			
			accSupAttrMapper.addAccSupAttr(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			throw new SysException("添加失败");

		}

	}
	
	/**
	 * @Description 
	 * 供应商字典属性表<BR> 批量添加AccSupAttr
	 * @param  AccSupAttr entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addBatchAccSupAttr(List<Map<String,Object>> entityList)throws DataAccessException{

		try {
			
			accSupAttrMapper.addBatchAccSupAttr(entityList);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码 addBatchAccSupAttr\"}";

		}
	}
	
	/**
	 * @Description 
	 * 供应商字典属性表<BR> 查询AccSupAttr分页
	 * @param  entityMap RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String queryAccSupAttr(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
		List<AccSupAttr> list = accSupAttrMapper.queryAccSupAttr(entityMap, rowBounds);
		PageInfo page = new PageInfo(list);
		return ChdJson.toJson(list, page.getTotal());
		
	}
	
	/**
	 * @Description 
	 * 供应商字典属性表<BR> 查询AccSupAttrByCode
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public AccSupAttr queryAccSupAttrByCode(Map<String,Object> entityMap)throws DataAccessException{
		
		return accSupAttrMapper.queryAccSupByCode(entityMap);
		
	}
	
	/**
	 * @Description 
	 * 供应商字典属性表<BR> 批量删除AccSupAttr
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String deleteBatchAccSupAttr(List<Map<String,Object>> entityList)throws DataAccessException{

		try {

				int state = accSupAttrMapper.deleteBatchAccSupAttr(entityList);
				return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码  deleteBatchAccSupAttr\"}";

		}
		
	}
	
	/**
	 * @Description 
	 * 供应商字典属性表<BR> 删除AccSupAttr
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
  public String deleteAccSupAttr(Map<String, Object> entityMap) throws DataAccessException {
		
		try {
				accSupAttrMapper.deleteAccSupAttr(entityMap);
				return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码  deleteAccSupAttr\"}";

		}
    }
	
	/**
	 * @Description 
	 * 供应商字典属性表<BR> 更新AccSupAttr
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateAccSupAttr(Map<String,Object> entityMap)throws DataAccessException{

		try {
			
			if("0".equals(entityMap.get("is_disable").toString())){
				
				entityMap.put("history", false);
				
			}else{
				
				entityMap.put("history", true);
				
			}
				
				supService.updateSup(entityMap);
				
				accSupAttrMapper.updateAccSupAttr(entityMap);

				return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updateAccSupAttr\"}";

		}
	}
	
	/**
	 * @Description 
	 * 供应商字典属性表<BR> 批量更新AccSupAttr
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateBatchAccSupAttr(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {

			accSupAttrMapper.updateBatchAccSupAttr(entityList);

			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updateAccSupAttr\"}";

		}
		
	}
	
	/**
	 * @Description 
	 * 供应商字典属性表<BR> 导入AccSupAttr
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String importAccSupAttr(Map<String,Object> entityMap)throws DataAccessException{

		try {

			

			return "{\"msg\":\"导入成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"导入失败 数据库异常 请联系管理员! 错误编码  import{className}\"}";

		}
	}

	@Override
	public AccSupAttr queryHosSupByCode(Map<String, Object> entityMap)
			throws DataAccessException {
		
		return accSupAttrMapper.queryHosSupByCode(entityMap);
	}
}
