/**
 * @Copyright: Copyright (c) 2015-2-14
 * @Company: 智慧云康（北京）数据科技有限公司
 */

package com.chd.hrp.sys.serviceImpl;

import java.util.*;

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
import com.chd.base.util.JsonListBeanUtil;
import com.chd.base.util.StringTool;
import com.chd.hrp.sys.dao.StoreDictMapper;
import com.chd.hrp.sys.dao.StoreMapper;
import com.chd.hrp.sys.dao.notGeneral.SysFunUtilMapper;
import com.chd.hrp.sys.entity.Copy;
import com.chd.hrp.sys.entity.Store;
import com.chd.hrp.sys.entity.Sup;
import com.chd.hrp.sys.service.StoreService;
import com.chd.hrp.sys.service.base.SysBaseService;
import com.github.pagehelper.PageInfo;

/**
 * @Title. @Description.
 * @Author: LiuYingDuo
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Service("storeService")
public class StoreServiceImpl implements StoreService {

	private static Logger logger = Logger.getLogger(StoreServiceImpl.class);

	@Resource(name = "storeMapper")
	private final StoreMapper storeMapper = null;

	@Resource(name = "storeDictMapper")
	private final StoreDictMapper storeDictMapper = null;

	@Resource(name = "sysFunUtilMapper")
	private final SysFunUtilMapper sysFunUtilMapper = null;

	// 引入Service服务
	@Resource(name = "sysBaseService")
	private final SysBaseService sysBaseService = null;

	/**
	 * @Description 添加Store
	 * @param Store
	 *            entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String addStore(Map<String, Object> entityMap) throws DataAccessException {
		entityMap.put("group_id", SessionManager.getGroupId());
		entityMap.put("hos_id", SessionManager.getHosId());

		Map<String, Object> utilMap = new HashMap<String, Object>();
		utilMap.put("group_id", entityMap.get("group_id"));
		utilMap.put("hos_id", entityMap.get("hos_id"));
		utilMap.put("copy_code", "");
		utilMap.put("field_table", "HOS_STORE");
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

		List<Store> list = storeMapper.queryStoreById(entityMap);

		if (list.size() > 0) {

			for (Store item : list) {

				if (item.getStore_code().equals(entityMap.get("store_code"))) {

					return "{\"error\":\"编码：" + item.getStore_code().toString() + "已存在.\"}";
				}

				if (item.getStore_name().equals(entityMap.get("store_name"))) {

					return "{\"error\":\"编码：" + item.getStore_name().toString() + "已存在.\"}";
				}
			}
		}
		entityMap.put("spell_code", StringTool.toPinyinShouZiMu(entityMap.get("store_name").toString()));
		entityMap.put("wbx_code", StringTool.toWuBi(entityMap.get("store_name").toString()));

		// 添加编码规则判断
		entityMap.put("proj_code", "HOS_STORE");
		entityMap.put("mod_code", "00");

		String proj_code = entityMap.get("store_code").toString();

		Map<Object, Object> rules = sysBaseService.getHosRules(entityMap);
		if (null != entityMap.get("store_code")) {
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
		try {

			
			int result = storeMapper.addStore(entityMap);
			if (result > 0) {
				entityMap.put("store_id", storeMapper.queryCurrentSequence());
				entityMap.put("user_code", SessionManager.getUserCode());
				entityMap.put("create_date", new Date());
				entityMap.put("dlog", "添加");
				entityMap.put("is_stop", 0);
				entityMap.put("is_disable", entityMap.get("is_stop"));
			
				storeDictMapper.addStoreDict(entityMap);
			}
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码 addStore\"}";

		}

	}
	
	public String addImportStore(Map<String, Object> entityMap) throws DataAccessException {
		
		try {

			int result = storeMapper.addStore(entityMap);
			if (result > 0) {
				entityMap.put("store_id", storeMapper.queryCurrentSequence());
				entityMap.put("user_code", SessionManager.getUserCode());
				entityMap.put("create_date", new Date());
				entityMap.put("dlog", "添加");
				entityMap.put("is_stop", 0);
				storeDictMapper.addStoreDict(entityMap);
			}
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码 addStore\"}";

		}

	}

	/**
	 * @Description 批量添加Store
	 * @param Store
	 *            entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String addBatchStore(List<Map<String, Object>> entityList) throws DataAccessException {

		try {

			storeMapper.addBatchStore(entityList);

			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码 addBatchStore\"}";

		}
	}

	/**
	 * @Description 查询Store分页
	 * @param entityMap
	 *            RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String queryStore(Map<String, Object> entityMap) throws DataAccessException {

		entityMap.put("group_id", SessionManager.getGroupId());
		entityMap.put("hos_id", SessionManager.getHosId());
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage)entityMap.get("sysPage");
		
		if(sysPage.getTotal() == -1){
			
			List<Store> list = storeMapper.queryStore(entityMap);
			
			return ChdJson.toJson(list);
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<Store> list = storeMapper.queryStore(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list,page.getTotal());
		}

	}

	/**
	 * @Description 查询StoreByCode
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public Store queryStoreByCode(Map<String, Object> entityMap) throws DataAccessException {

		return storeMapper.queryStoreByCode(entityMap);

	}

	/**
	 * @Description 批量删除Store
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String deleteBatchStore(List<Map<String, Object>> entityList) throws DataAccessException {

		try {
			//controlle层判断 zhaon
			/*String storeIdStr="";//删除科目，判断业务使用
			String reStr="";
			String superCode="";
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("dict_code", "HOS_STORE");
			map.put("group_id", entityList.get(0).get("group_id"));
			map.put("hos_id", entityList.get(0).get("hos_id"));
			map.put("copy_code", "");
			map.put("acc_year", "");
			map.put("p_flag", "");//包括子科目
			if(entityList!=null && entityList.size()>0){
				for(Map<String, Object> mapVo : entityList){
					storeIdStr+=mapVo.get("store_id")+",";
					
						map.put("dict_id_str", storeIdStr.substring(0, storeIdStr.length()-1));
						//删除科目时，判断业务表是否使用
						//if(LoadSystemInfo.getHrpMap().get("hrpDatatype").toString().equalsIgnoreCase("oracle")){
						
						sysFunUtilMapper.querySysDictDelCheck(map);
						
						storeIdStr="";
						//}
						if(map.get("reNote")!=null)reStr+=map.get("reNote");
					
				}
			}
			if(reStr!=null && !reStr.equals("")){
				return "{\"error\":\"删除失败，选择的库房被以下业务使用：【"+reStr.substring(0,reStr.length()-1)+"】。\",\"state\":\"false\"}";
			}*/
			storeDictMapper.deleteBatchStoreDict(entityList);
			storeMapper.deleteBatchStore(entityList);
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);
			
			throw new SysException(e.getMessage());

		}

	}

	/**
	 * @Description 删除Store
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String deleteStore(Map<String, Object> entityMap) throws DataAccessException {		
		try {
			storeDictMapper.deleteStoreDict(entityMap);
			storeMapper.deleteStore(entityMap);
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);
			
			throw new SysException(e.getMessage());

		}
	}

	/**
	 * @Description 更新Store
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String updateStore(Map<String, Object> entityMap) throws DataAccessException {

		if (entityMap.get("is_auto").equals("true")) {
			entityMap.put("spell_code", StringTool.toPinyinShouZiMu(entityMap.get("store_name").toString()));
			entityMap.put("wbx_code", StringTool.toWuBi(entityMap.get("store_name").toString()));
		}
		entityMap.put("mode_code", entityMap.get("mode_code"));
		// 添加编码规则判断
		entityMap.put("proj_code", "HOS_STORE");
		entityMap.put("mod_code", "00");

		String proj_code = entityMap.get("store_code").toString();

		Map<Object, Object> rules = sysBaseService.getHosRules(entityMap);
		if (null != entityMap.get("store_code")) {
			Map<Object, Object> level_map = (Map<Object, Object>) rules.get("rules_type_level");
			Map<Object, Object> rules_level_length = (Map<Object, Object>) rules.get("rules_level_length");

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
		List<Store> list = storeMapper.queryStoreById(entityMap);

		if (list.size() > 0) {

			for (Store store : list) {

				if (store.getStore_code().equals(entityMap.get("store_code"))) {

					return "{\"error\":\"编码：" + store.getStore_code().toString() + "已存在.\"}";
				}

				if (store.getStore_name().equals(entityMap.get("store_name"))) {

					return "{\"error\":\"编码：" + store.getStore_name().toString() + "已存在.\"}";
				}
			}
		}

		try {

			storeMapper.updateStore(entityMap);

			if (entityMap.get("history").equals("true")) {
				Map<String, Object> map = new HashMap<String, Object>();
				
				
				map.put("group_id", entityMap.get("group_id"));
				map.put("hos_id", entityMap.get("hos_id"));
				map.put("store_id", entityMap.get("store_id"));
				map.put("is_stop", 1);
				
			
				storeDictMapper.updateStoreDictState(map);

				entityMap.put("user_code", SessionManager.getUserCode());
				entityMap.put("create_date", new Date());
				entityMap.put("dlog", "变更");
				entityMap.put("is_disable", entityMap.get("is_stop"));
		     	entityMap.put("is_stop", "0");
				
				storeDictMapper.addStoreDict(entityMap);
				

				return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";
			} else {
				

				entityMap.put("user_code", SessionManager.getUserCode());
				entityMap.put("create_date", new Date());
				entityMap.put("dlog", "变更");
				

				storeDictMapper.updateStoreDict_Store(entityMap);

				return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";
			}

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);
           throw new SysException(e.getMessage());
			

		}
	}

	/**
	 * @Description 批量更新Store
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String updateBatchStore(List<Map<String, Object>> entityList) throws DataAccessException {

		try {

			storeMapper.updateBatchStore(entityList);

			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updateStore\"}";

		}

	}

	/**
	 * @Description 导入Store
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String importStore(Map<String, Object> entityMap) throws DataAccessException {

		try {

			return "{\"msg\":\"导入成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"导入失败 数据库异常 请联系管理员! 错误编码  import{className}\"}";

		}
	}
}
