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
import org.nutz.lang.Strings;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.SessionManager;
import com.chd.base.SysPage;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.base.util.StringTool;
import com.chd.hrp.acc.dao.AccCusAttrMapper;
import com.chd.hrp.acc.entity.AccCusAttr;
import com.chd.hrp.acc.service.AccCusAttrService;
import com.chd.hrp.sys.dao.CusDictMapper;
import com.chd.hrp.sys.dao.CusMapper;
import com.chd.hrp.sys.dao.DeptMapper;
import com.chd.hrp.sys.dao.EmpMapper;
import com.chd.hrp.sys.dao.notGeneral.SysFunUtilMapper;
import com.chd.hrp.sys.entity.Cus;
import com.chd.hrp.sys.service.CusService;
import com.chd.hrp.sys.service.base.SysBaseService;
import com.github.pagehelper.PageInfo;

/**
* @Title. @Description.
* 客户字典属性表<BR> 
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


@Service("accCusAttrService")
public class AccCusAttrServiceImpl implements AccCusAttrService {

	private static Logger logger = Logger.getLogger(AccCusAttrServiceImpl.class);
	
	@Resource(name = "accCusAttrMapper")
	private final AccCusAttrMapper accCusAttrMapper = null;
	
	@Resource(name = "deptMapper")
	private final DeptMapper deptMapper = null;
	
	@Resource(name = "empMapper")
	private final EmpMapper empMapper = null;
    
	@Resource(name = "cusService")
	private final CusService cusService = null;
	
	@Resource(name = "cusMapper")
	private final CusMapper cusMapper = null;

	@Resource(name = "cusDictMapper")
	private final CusDictMapper cusDictMapper = null;

	@Resource(name = "sysFunUtilMapper")
	private final SysFunUtilMapper sysFunUtilMapper = null;
	
	// 引入Service服务
	@Resource(name = "sysBaseService")
	private final SysBaseService sysBaseService = null;
	
	/**
	 * @Description 
	 * 客户字典属性表<BR> 添加AccCusAttr
	 * @param AccCusAttr entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addAccCusAttr(Map<String,Object> entityMap)throws DataAccessException{

		try {

			List<Cus> list = cusMapper.queryCusById(entityMap);

			if (list.size() > 0) {
				for (Cus cus : list) {

					if (cus.getCus_code().equals(entityMap.get("cus_code"))) {
						return "{\"error\":\"编码：" + cus.getCus_code().toString() + "已存在.\"}";
					}

					if (cus.getCus_name().equals(entityMap.get("cus_name"))) {
						return "{\"error\":\"编码：" + cus.getCus_name().toString() + "已存在.\"}";
					}
				}
			}
			
			entityMap.put("is_mat", entityMap.get("is_mat"));
			entityMap.put("is_ass", entityMap.get("is_ass"));
			entityMap.put("is_med", entityMap.get("is_med"));
			entityMap.put("is_sup", entityMap.get("is_sup"));

			entityMap.put("spell_code", StringTool.toPinyinShouZiMu(entityMap.get("cus_name").toString()));
			entityMap.put("wbx_code", StringTool.toWuBi(entityMap.get("cus_name").toString()));

			// 添加编码规则判断
			entityMap.put("proj_code", "HOS_CUS");
			entityMap.put("mod_code", "00");

			String proj_code = entityMap.get("cus_code").toString();

			Map<Object, Object> rules = sysBaseService.getHosRules(entityMap);
			if (null != entityMap.get("cus_code")) {
				Map<Object, Object> level_map = (Map<Object, Object>) rules.get("rules_type_level");
				Map<Object, Object> rules_level_length = (Map<Object, Object>) rules.get("rules_level_length");
				int max_level = Integer.parseInt(rules.get("max_level").toString());
				if(max_level>0){
				String rules_v = rules.get("rules_view").toString();
				String s_view = Strings.removeFirst(rules_v);
				Object level = proj_code.length();
				if(rules_level_length!=null){
					//当第一级为0时 不验证规则
					if(!rules_level_length.get(1).toString().equals("0")){
						
						if (level != rules_level_length.get(1)) {
							return "{\"error\":\"编码不符合要求,请重新添加.编码规则长度：" + s_view + "\"}";
						}
						
					}
				}
			  }
			}

			Map<String, Object> utilMap = new HashMap<String, Object>();
			utilMap.put("group_id", entityMap.get("group_id"));
			utilMap.put("hos_id", entityMap.get("hos_id"));
			utilMap.put("copy_code", "");
			utilMap.put("field_table", "HOS_CUS");
			utilMap.put("field_key1", "");
			utilMap.put("field_value1", "");
			utilMap.put("field_key2", "");
			utilMap.put("field_value2", "");

			if (entityMap.get("sort_code").equals("系统生成")) {
				utilMap.remove("field_key2");
				utilMap.put("field_sort", "sort_code");
				int count = sysFunUtilMapper.querySysMaxSort(utilMap);
				entityMap.put("sort_code", count);
			}
			
			int result = cusMapper.addCus(entityMap);
			if (result > 0) {
				if(entityMap.get("is_stop").equals("0")){
					entityMap.put("is_disable",0);
				}else if(entityMap.get("is_stop").equals("1")){
					entityMap.put("is_disable",1);
				} 
				
				entityMap.put("cus_id", cusMapper.queryCurrentSequence());
				entityMap.put("user_code", SessionManager.getUserCode());
				entityMap.put("create_date", new Date());
				entityMap.put("dlog", "添加");
				entityMap.put("is_stop", 0);
				entityMap.put("is_disable", entityMap.get("is_stop"));
				cusDictMapper.addCusDict(entityMap);
			}
			
			accCusAttrMapper.addAccCusAttr(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			throw new SysException("添加失败");

		}

	}
	
	/**
	 * @Description 
	 * 客户字典属性表<BR> 批量添加AccCusAttr
	 * @param  AccCusAttr entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addBatchAccCusAttr(List<Map<String,Object>> entityList)throws DataAccessException{

		try {
			
			accCusAttrMapper.addBatchAccCusAttr(entityList);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码 addBatchAccCusAttr\"}";

		}
	}
	
	/**
	 * @Description 
	 * 客户字典属性表<BR> 查询AccCusAttr分页
	 * @param  entityMap RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String queryAccCusAttr(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
		List<AccCusAttr> list = accCusAttrMapper.queryAccCusAttr(entityMap, rowBounds);
		PageInfo page = new PageInfo(list);
		return ChdJson.toJson(list, page.getTotal());
	}
	
	/**
	 * @Description 
	 * 客户字典属性表<BR> 查询AccCusAttrByCode
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public AccCusAttr queryAccCusAttrByCode(Map<String,Object> entityMap)throws DataAccessException{
		
		AccCusAttr accCusAttr =accCusAttrMapper.queryCusByCode(entityMap);
		
		return 	accCusAttr;
		
	}
	
	/**
	 * @Description 
	 * 客户字典属性表<BR> 批量删除AccCusAttr
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String deleteBatchAccCusAttr(List<Map<String,Object>> entityList)throws DataAccessException{

		try {

				int state = accCusAttrMapper.deleteBatchAccCusAttr(entityList);
				return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码  deleteBatchAccCusAttr\"}";

		}
		
	}
	
	/**
	 * @Description 
	 * 客户字典属性表<BR> 删除AccCusAttr
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
  public String deleteAccCusAttr(Map<String, Object> entityMap) throws DataAccessException {
		
		try {
				accCusAttrMapper.deleteAccCusAttr(entityMap);
				return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码  deleteAccCusAttr\"}";

		}
    }
	
	/**
	 * @Description 
	 * 客户字典属性表<BR> 更新AccCusAttr
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateAccCusAttr(Map<String,Object> entityMap)throws DataAccessException{

		try {
			
			int updateCount = accCusAttrMapper.updateAccCusAttrHistory(entityMap);
			if (updateCount == 0) {
				throw new SysException("修改失败!请刷新页面后重试!");
			}
			
			List<Cus> list = cusMapper.queryCusById(entityMap);

			if (list.size() > 0) {
				for (Cus cus : list) {

					if (cus.getCus_code().equals(entityMap.get("cus_code"))) {
						return "{\"error\":\"编码：" + cus.getCus_code().toString() + "已存在.\"}";
					}

					if (cus.getCus_name().equals(entityMap.get("cus_name"))) {
						return "{\"error\":\"编码：" + cus.getCus_name().toString() + "已存在.\"}";
					}
				}
			}
			
			entityMap.put("spell_code", StringTool.toPinyinShouZiMu(entityMap.get("cus_name").toString()));
			entityMap.put("wbx_code", StringTool.toWuBi(entityMap.get("cus_name").toString()));
			entityMap.put("is_disable", entityMap.get("is_disable"));
			entityMap.put("is_stop", 0);
			entityMap.put("user_code", SessionManager.getUserCode());
			entityMap.put("create_date", new Date());
			entityMap.put("dlog", "变更");
			
			//查询一个主键ID
			Map<String, Object> utilMap = new HashMap<String, Object>();
			utilMap.put("group_id", entityMap.get("group_id"));
			utilMap.put("hos_id", entityMap.get("hos_id"));
			utilMap.put("copy_code", "");
			utilMap.put("field_table", "HOS_CUS");
			utilMap.put("field_key1", "");
			utilMap.put("field_value1", "");
			utilMap.put("field_key2", "");
			utilMap.put("field_value2", "");

			if (entityMap.get("sort_code").equals("系统生成")) {
				utilMap.remove("field_key2");
				utilMap.put("field_sort", "sort_code");
				int count = sysFunUtilMapper.querySysMaxSort(utilMap);
				entityMap.put("sort_code", count);
			}

			int addCount = cusDictMapper.addCusDict(entityMap);
			if (addCount == 0) {
				throw new SysException("修改失败!请刷新页面后重试");
			}
			
			accCusAttrMapper.updateAccCusAttr(entityMap);

			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			throw new SysException("修改失败");

		}
	}
	
	/**
	 * @Description 
	 * 客户字典属性表<BR> 批量更新AccCusAttr
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateBatchAccCusAttr(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {

			accCusAttrMapper.updateBatchAccCusAttr(entityList);

			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updateAccCusAttr\"}";

		}
		
	}
	
	/**
	 * @Description 
	 * 客户字典属性表<BR> 导入AccCusAttr
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String importAccCusAttr(Map<String,Object> entityMap)throws DataAccessException{

		try {

			

			return "{\"msg\":\"导入成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"导入失败 数据库异常 请联系管理员! 错误编码  import{className}\"}";

		}
	}

	/**
	 * @Description 
	 * 修改客户时<BR> 查询CusByCode
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public AccCusAttr queryCusByCode(Map<String, Object> entityMap)
			throws DataAccessException {
		return accCusAttrMapper.queryCusByCode(entityMap);
	}

	@Override
	public AccCusAttr queryHosCusByCode(Map<String, Object> entityMap)
			throws DataAccessException {
		
		return accCusAttrMapper.queryHosCusByCode(entityMap);
	}
}
