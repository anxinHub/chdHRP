
package com.chd.hrp.htc.serviceImpl.info.basic;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.SessionManager;
import com.chd.base.SysPage;
import com.chd.base.util.ChdJson;
import com.chd.hrp.htc.dao.info.basic.HtcCostItemDictMapper;
import com.chd.hrp.htc.dao.info.basic.HtcCostItemDictNoMapper;
import com.chd.hrp.htc.entity.info.basic.HtcCostItemDict;
import com.chd.hrp.htc.service.info.basic.HtcCostItemDictService;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Title. 
 * @Description.
 * @Copyright: Copyright (c) 2015-2-14 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @Author: LiuYingDuo
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */


@Service("htcCostItemDictService")
public class HtcCostItemDictServiceImpl implements HtcCostItemDictService {

	private static Logger logger = Logger.getLogger(HtcCostItemDictServiceImpl.class);
	
	@Resource(name = "htcCostItemDictMapper")
	private final HtcCostItemDictMapper htcCostItemDictMapper = null;
    
	@Resource(name = "htcCostItemDictNoMapper")
	private final HtcCostItemDictNoMapper htcCostItemDictNoMapper = null;
	
	@Override
	public String addHtcCostItemDict(Map<String,Object> entityMap)throws DataAccessException{
		
		
		Map<String, Object> map_code = new HashMap<String, Object>();
		map_code.put("group_id", entityMap.get("group_id"));
		map_code.put("hos_id", entityMap.get("hos_id"));
		map_code.put("copy_code", entityMap.get("copy_code"));
		map_code.put("cost_item_code", entityMap.get("cost_item_code"));
		// 判断编码是否重复
		HtcCostItemDict data_exc_extis_code = htcCostItemDictMapper.queryHtcCostItemDictByCode(map_code);

		if (data_exc_extis_code != null) {

			return "{\"error\":\"编码重复,请重新添加.\"}";

		}

		// 判断上级编码是否为空 不为空则反查上级编码所属分类
		if (!"0".equals(entityMap.get("supp_item_code").toString())) {
			Map<String, Object> map_super = new HashMap<String, Object>();
			map_super.put("group_id", entityMap.get("group_id"));
			map_super.put("hos_id", entityMap.get("hos_id"));
			map_super.put("copy_code", entityMap.get("copy_code"));
			map_super.put("cost_item_code", entityMap.get("supp_item_code"));

			HtcCostItemDict data_exc_extis_super = htcCostItemDictMapper.queryHtcCostItemDictByCode(map_super);

			if (null != data_exc_extis_super) {
				entityMap.put("cost_type_id", data_exc_extis_super.getCost_type_id());
			} else {
				return "{\"error\":\"上级编码不存在,请重新添加.\"}";
			}
			// 判断上级编码是否为末级
			if (data_exc_extis_super.getIs_last() == 1) {
				Map<String, Object> update_is_last = new HashMap<String, Object>();
				update_is_last.put("group_id", entityMap.get("group_id"));
				update_is_last.put("hos_id", entityMap.get("hos_id"));
				update_is_last.put("copy_code", entityMap.get("copy_code"));
				update_is_last.put("cost_item_id", data_exc_extis_super.getCost_item_id());
				update_is_last.put("is_last", "0");
				htcCostItemDictMapper.updateHtcCostItemDict(update_is_last);
			}
		}
		
		try {

			int result = htcCostItemDictMapper.addHtcCostItemDict(entityMap);
			if (result > 0) {
				entityMap.put("cost_item_id", htcCostItemDictMapper.queryCurrentSequence());
				entityMap.put("user_code", SessionManager.getUserCode());
				entityMap.put("create_date", new Date());
				entityMap.put("note", "添加");
				entityMap.put("is_stop", 0);
				htcCostItemDictNoMapper.addHtcCostItemDictNo(entityMap);
			}
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码 addHtcCostItemDict\"}";

		}

	}	
	
	@Override
	public String queryHtcCostItemDict(Map<String,Object> entityMap) throws DataAccessException{
		SysPage sysPage = new SysPage();
		sysPage = (SysPage) entityMap.get("sysPage");
		if(sysPage.getTotal() == -1){
			List<HtcCostItemDict> list = htcCostItemDictMapper.queryHtcCostItemDict(entityMap);
			
			return ChdJson.toJson(list);
		}else{
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<HtcCostItemDict> list = htcCostItemDictMapper.queryHtcCostItemDict(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
		}
	}
	
	@Override
	public HtcCostItemDict queryHtcCostItemDictByCode(Map<String,Object> entityMap)throws DataAccessException{
		return htcCostItemDictMapper.queryHtcCostItemDictByCode(entityMap);
	}
	
	@Override
    public String deleteHtcCostItemDict(Map<String, Object> entityMap) throws DataAccessException {
		
		try {
			htcCostItemDictNoMapper.deleteHtcCostItemDictNo(entityMap);
			htcCostItemDictMapper.deleteHtcCostItemDict(entityMap);
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码  deleteHtcCostItemDict\"}";

		}
    }
 
	@Override
	public String deleteBatchHtcCostItemDict(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			htcCostItemDictNoMapper.deleteBatchHtcCostItemDictNo(entityList);
			htcCostItemDictMapper.deleteBatchHtcCostItemDict(entityList);
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码  deleteBatchHtcCostItemDict\"}";

		}
	}
	/**
	 * 
	 */
	@Override
	public String updateHtcCostItemDict(Map<String,Object> entityMap)throws DataAccessException{
		
		// 判断上级编码是否为空 不为空则反查上级编码所属分类
		if (!"0".equals(entityMap.get("supp_item_code").toString())) {
			Map<String, Object> map_super = new HashMap<String, Object>();
			map_super.put("group_id", entityMap.get("group_id"));
			map_super.put("hos_id", entityMap.get("hos_id"));
			map_super.put("copy_code", entityMap.get("copy_code"));
			map_super.put("cost_item_code", entityMap.get("supp_item_code"));

			HtcCostItemDict data_exc_extis_super = htcCostItemDictMapper.queryHtcCostItemDictByCode(map_super);

			if (null != data_exc_extis_super) {
				entityMap.put("cost_type_id", data_exc_extis_super.getCost_type_id());
			} else {
				return "{\"error\":\"上级编码不存在,请重新添加.\"}";
			}
			// 判断上级编码是否为末级
			if (data_exc_extis_super.getIs_last() == 1) {
				Map<String, Object> update_is_last = new HashMap<String, Object>();
				update_is_last.put("group_id", entityMap.get("group_id"));
				update_is_last.put("hos_id", entityMap.get("hos_id"));
				update_is_last.put("copy_code", entityMap.get("copy_code"));
				update_is_last.put("cost_item_id", data_exc_extis_super.getCost_item_id());
				update_is_last.put("is_last", "0");
				htcCostItemDictMapper.updateHtcCostItemDict(update_is_last);
			}
		}
		
		try {
			
			htcCostItemDictMapper.updateHtcCostItemDict(entityMap);
			
			htcCostItemDictNoMapper.updateHtcCostItemDictNo(entityMap);
			
			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";
			
		}
		catch (Exception e) {
			
			logger.error(e.getMessage(), e);
			
			return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updateHtcCostItemDict\"}";
			
		}
	}
	
	@Override
	public String updateBatchHtcCostItemDict(List<Map<String, Object>> entityList) throws DataAccessException {

		try {
			
			htcCostItemDictMapper.updateBatchHtcCostItemDict(entityList);
			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";
		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);
			return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updateBatchHtcCostItemDict\"}";
		}

	}

	@Override
	public String addBatchHtcCostItemDict(List<Map<String, Object>> entityMap)
			throws DataAccessException {
		int state = htcCostItemDictMapper.addBatchHtcCostItemDict(entityMap);
		if (state > 0) {
			return "{\"msg\":\"导入成功.\",\"state\":\"true\"}";
		} else {
			return "{\"msg\":\"导入失败.\",\"state\":\"false\"}";
		}
	}

	@Override
	public List<?> queryHtcCostItemDictByTree(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		List<?> l_map = htcCostItemDictMapper.queryHtcCostItemDictByTree(entityMap);
		return l_map;
	}
	
	@Override
    public String updateHtcCostItemBatch(Map<String, Object> entityMap) throws DataAccessException {
		try {

			htcCostItemDictMapper.updateHtcCostItemBatch(entityMap);
			htcCostItemDictNoMapper.updateHtcCostItemNoBatch(entityMap);
			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";
		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);
			return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updateCostItemBatch\"}";
		}
    }
	
}
