/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/

package com.chd.hrp.acc.serviceImpl.wage;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.SysPage;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.hrp.acc.dao.wage.AccWageMapper;
import com.chd.hrp.acc.entity.AccWage;
import com.chd.hrp.acc.service.wage.AccWageService;
import com.chd.hrp.sys.dao.notGeneral.SysFunUtilMapper;
import com.github.pagehelper.PageInfo;


/**
* @Title. @Description.
* 工资套<BR> 
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


@Service("accWageService")
public class AccWageServiceImpl implements AccWageService {

	private static Logger logger = Logger.getLogger(AccWageServiceImpl.class);
	
	@Resource(name = "accWageMapper")
	private final AccWageMapper accWageMapper = null;
    
	@Resource(name = "sysFunUtilMapper")
	private final SysFunUtilMapper sysFunUtilMapper = null;
	
	/**
	 * @Description 工资套<BR>
	 *              添加AccWage
	 * @param AccWage
	 *            entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String addAccWage(Map<String, Object> entityMap) throws DataAccessException {
		String wage_code = ((String) entityMap.get("wage_code")).trim();
		String wage_name = ((String) entityMap.get("wage_name")).trim();
		if ("".equals(wage_code) || "".equals(wage_name)) {
			return "{\"error\":\"工资套编码或工资套名称不能为空.\"}";
		}

		AccWage AccWage = queryAccWageByCode(entityMap);
		if (AccWage != null) {
			return "{\"error\":\"编码：" + entityMap.get("wage_code").toString() + "重复.\"}";
		}

		try {
			accWageMapper.addAccWage(entityMap);
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码 addAccWage\"}";
		}
	}
	
	/**
	 * @Description 
	 * 工资套<BR> 批量添加AccWage
	 * @param  AccWage entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addBatchAccWage(List<Map<String,Object>> entityList)throws DataAccessException{

		try {
			
			accWageMapper.addBatchAccWage(entityList);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码 addBatchAccWage\"}";

		}
	}
	
	/**
	 * @Description 
	 * 工资套<BR> 查询AccWage分页
	 * @param  entityMap RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String queryAccWage(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage)entityMap.get("sysPage");
		
		if(sysPage.getTotal() == -1){
			
			List<AccWage> list = accWageMapper.queryAccWage(entityMap);
			
			return ChdJson.toJson(list);
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<AccWage> list = accWageMapper.queryAccWage(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list,page.getTotal());
		}
		
	}
	
	/**
	 * @Description 
	 * 工资套<BR> 查询AccWageByCode
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public AccWage queryAccWageByCode(Map<String,Object> entityMap)throws DataAccessException{
		
		return accWageMapper.queryAccWageByCode(entityMap);
		
	}
	
	/**
	 * @Description 工资套<BR>
	 *              批量删除AccWage
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String deleteBatchAccWage(Map<String, Object> mapVo) throws DataAccessException {
		try {
//			StringBuilder wageStr = new StringBuilder("");
//			String reStr = "";
//			Map<String, Object> map = new HashMap<String, Object>();
//			map.put("dict_code", "ACC_WAGE");
//			map.put("group_id", entityList.get(0).get("group_id"));
//			map.put("hos_id", entityList.get(0).get("hos_id"));
//			map.put("copy_code", entityList.get(0).get("copy_code"));
//			map.put("acc_year", entityList.get(0).get("acc_year"));
//			map.put("p_flag", "1");
//			if (entityList != null && entityList.size() > 0) {
//				for (Map<String, Object> mapVo : entityList) {
//					wageStr.append(",").append(mapVo.get("wage_code"));
//					if (wageStr.length() > 950) {
//						map.put("dict_id_str", wageStr.substring(1));
//						sysFunUtilMapper.querySysDictDelCheck(map);
//						wageStr = new StringBuilder("");
//						if (map.get("reNote") != null)
//							reStr += map.get("reNote");
//					}
//				}
//				if (!wageStr.equals("")) {
//					map.put("dict_id_str", wageStr.substring(1));
//					sysFunUtilMapper.querySysDictDelCheck(map);
//					if (map.get("reNote") != null)
//						reStr += map.get("reNote");
//				}
//			}
//			if (reStr != null && !reStr.equals("")) {
//				return "{\"error\":\"删除失败，工资套已被部分职工引用。\",\"state\":\"false\"}";
//			}
			
			List<Map<String,Object>> list = sysFunUtilMapper.querySysDictUseByCode("ACC_WAGE");
			for (Map<String, Object> map : list) {
				mapVo.put("table_code", map.get("table_code"));
				mapVo.put("column_code", map.get("column_code"));
				accWageMapper.deleteByAccWage(mapVo);
			}
			
			accWageMapper.deleteBatchAccWage(mapVo);
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException( "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码  deleteBatchAccWage\"}");
		}
	}
	
	/**
	 * @Description 
	 * 工资套<BR> 删除AccWage
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
  public String deleteAccWage(Map<String, Object> entityMap) throws DataAccessException {
		
		try {
			accWageMapper.deleteAccWage(entityMap);
				
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码  deleteAccWage\"}";

		}
    }
	
	/**
	 * @Description 
	 * 工资套<BR> 更新AccWage
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateAccWage(Map<String,Object> entityMap)throws DataAccessException{

		try {

			accWageMapper.updateAccWage(entityMap);

			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updateAccWage\"}";

		}
	}
	
	/**
	 * @Description 
	 * 工资套<BR> 批量更新AccWage
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateBatchAccWage(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {

			accWageMapper.updateBatchAccWage(entityList);

			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updateAccWage\"}";

		}
		
	}
	
	/**
	 * @Description 
	 * 工资套<BR> 导入AccWage
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String importAccWage(Map<String,Object> entityMap)throws DataAccessException{

		try {

			

			return "{\"msg\":\"导入成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"导入失败 数据库异常 请联系管理员! 错误编码  import{className}\"}";

		}
	}
}
