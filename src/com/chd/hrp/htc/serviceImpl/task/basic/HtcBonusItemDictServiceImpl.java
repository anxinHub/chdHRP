package com.chd.hrp.htc.serviceImpl.task.basic;

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
import com.chd.hrp.htc.dao.task.basic.HtcBonusItemDictMapper;
import com.chd.hrp.htc.entity.task.basic.HtcBonusItemDict;
import com.chd.hrp.htc.service.task.basic.HtcBonusItemDictService;
import com.chd.hrp.sys.dao.notGeneral.SysFunUtilMapper;
import com.github.pagehelper.PageInfo;

/**
 * @Title.
 * @Description.
 * @Copyright: Copyright (c) 2015-2-14 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @Author: LiuYingDuo
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Service("htcBonusItemDictService")
public class HtcBonusItemDictServiceImpl implements HtcBonusItemDictService {

	private static Logger logger = Logger.getLogger(HtcBonusItemDictServiceImpl.class);

	@Resource(name = "htcBonusItemDictMapper")
	private final HtcBonusItemDictMapper htcBonusItemDictMapper = null;

	@Resource(name = "sysFunUtilMapper")
	private final SysFunUtilMapper sysFunUtilMapper = null;
	
	@Override
	public String addHtcBonusItemDict(Map<String, Object> entityMap) throws DataAccessException {
		
		try {
			
			Map<String, Object> utilMap = new HashMap<String, Object>();
			utilMap.put("group_id", entityMap.get("group_id"));
			utilMap.put("hos_id", entityMap.get("hos_id"));
			utilMap.put("copy_code", entityMap.get("copy_code"));
			utilMap.put("field_table", "htc_bonus_item_dict".toUpperCase());
			utilMap.put("field_sort", "sort_code");
			int count = sysFunUtilMapper.querySysMaxSort(utilMap);
			entityMap.put("sort_code", count);
			
			HtcBonusItemDict htcBonusItemDict = htcBonusItemDictMapper.queryHtcBonusItemDictByCode(entityMap);
			
			if(null != htcBonusItemDict){
				return "{\"error\":\"奖金编码重复.\",\"state\":\"false\"}";
			}
			
			htcBonusItemDictMapper.addHtcBonusItemDict(entityMap);
			
			Map<String, Object> utilMapVo = new HashMap<String, Object>();
			utilMapVo.put("group_id", entityMap.get("group_id"));
			utilMapVo.put("hos_id", entityMap.get("hos_id"));
			utilMapVo.put("copy_code", entityMap.get("copy_code"));
			utilMapVo.put("acc_year", entityMap.get("acc_year"));
			Map<String, Object> map = queryHtcBonusItemMapMaxId(utilMapVo);
			String clum_id = map.get("CLUM_ID").toString();
				
			Map<String, Object> wageItemMap =new HashMap<String, Object>();
				
			wageItemMap.put("group_id", entityMap.get("group_id"));
			wageItemMap.put("hos_id", entityMap.get("hos_id"));
			wageItemMap.put("copy_code", entityMap.get("copy_code"));
			wageItemMap.put("acc_year", entityMap.get("acc_year"));
			wageItemMap.put("bonus_item_code", entityMap.get("bonus_item_code"));
			wageItemMap.put("clum_code", "BONUS"+clum_id);
			wageItemMap.put("clum_id", clum_id);
			htcBonusItemDictMapper.addHtcBonusItemMap(wageItemMap);
			htcBonusItemDictMapper.addHtcBonusItemMapByAlter(wageItemMap);
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";	
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"添加失败\"}");
		}
	}

	
	
	@Override
	public String queryHtcBonusItemDict(Map<String, Object> entityMap) throws DataAccessException {
		
		SysPage sysPage = new SysPage();
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if(sysPage.getTotal() == -1){
			List<HtcBonusItemDict> list = htcBonusItemDictMapper.queryHtcBonusItemDict(entityMap);
			
			return ChdJson.toJson(list);
		}else{
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<HtcBonusItemDict> list = htcBonusItemDictMapper.queryHtcBonusItemDict(entityMap, rowBounds);
		
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
		}
	}

	@Override
	public HtcBonusItemDict queryHtcBonusItemDictByCode(Map<String, Object> entityMap) throws DataAccessException {
		
		return htcBonusItemDictMapper.queryHtcBonusItemDictByCode(entityMap);
		
	}

	@Override
	public String deleteBatchHtcBonusItemDict(List<Map<String, Object>> entityList) throws DataAccessException{
		
		try {
			
			htcBonusItemDictMapper.deleteBatchHtcBonusItemDict(entityList);
			htcBonusItemDictMapper.deleteBatchHtcBonusItemMap(entityList);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"删除失败\"}");
		}
	}
	
	@Override
	public String updateHtcBonusItemDict(Map<String, Object> entityMap) throws DataAccessException {
		
		try {
			
			htcBonusItemDictMapper.updateHtcBonusItemDict(entityMap);
			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"修改失败\"}");
		}
	}


	
	public Map<String, Object> queryHtcBonusItemMapMaxId(Map<String, Object> entityMap) throws DataAccessException {
		return htcBonusItemDictMapper.queryHtcBonusItemMapMaxId(entityMap);
    }
}
