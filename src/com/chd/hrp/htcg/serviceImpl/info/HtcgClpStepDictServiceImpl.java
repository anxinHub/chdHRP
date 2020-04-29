/*
 *
 */package com.chd.hrp.htcg.serviceImpl.info;

import java.util.ArrayList;
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
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.base.util.ReadFiles;
import com.chd.base.util.StringTool;
import com.chd.hrp.htcg.dao.info.HtcgClpStepDictMapper;
import com.chd.hrp.htcg.entity.info.HtcgClpStepDict;
import com.chd.hrp.htcg.service.info.HtcgClpStepDictService;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Title.
 * @Description.
 * @Copyright: Copyright (c) 2015-2-14 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @Author: LiuYingDuo
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Service("htcgClpStepDictService")
public class HtcgClpStepDictServiceImpl implements HtcgClpStepDictService {

	private static Logger logger = Logger
			.getLogger(HtcgClpStepDictServiceImpl.class);

	@Resource(name = "htcgClpStepDictMapper")
	private final HtcgClpStepDictMapper htcgClpStepDictMapper = null;

	@Override
	public String addHtcgClpStepDict(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		try {

			HtcgClpStepDict htcgClpStepDict = htcgClpStepDictMapper
					.queryHtcgClpStepDictByCode(entityMap);

			if (null != htcgClpStepDict) {
				return "{\"error\":\"编码已存在.\",\"state\":\"false\"}";
			}

			htcgClpStepDictMapper.addHtcgClpStepDict(entityMap);
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"添加失败\"}");
		}
	}

	@Override
	public String addBatchHtcgClpStepDict(List<Map<String, Object>> list)
			throws DataAccessException {
		// TODO Auto-generated method stub
		try {

			htcgClpStepDictMapper.addBatchHtcgClpStepDict(list);
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"添加失败\"}");
		}
	}

	@Override
	public String queryHtcgClpStepDict(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		SysPage sysPage = new SysPage();
		sysPage = (SysPage) entityMap.get("sysPage");
		if (sysPage.getTotal() == -1) {
			List<HtcgClpStepDict> list = htcgClpStepDictMapper
					.queryHtcgClpStepDict(entityMap);
			return ChdJson.toJson(list);
		} else {
			RowBounds rowBounds = new RowBounds(sysPage.getPage(),
					sysPage.getPagesize());
			List<HtcgClpStepDict> list = htcgClpStepDictMapper
					.queryHtcgClpStepDict(entityMap, rowBounds);
			PageInfo page = new PageInfo(list);
			return ChdJson.toJson(list, page.getTotal());

		}
	}

	@Override
	public HtcgClpStepDict queryHtcgClpStepDictByCode(
			Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		return htcgClpStepDictMapper.queryHtcgClpStepDictByCode(entityMap);
	}

	@Override
	public String deleteHtcgClpStepDict(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		try {
			htcgClpStepDictMapper.deleteHtcgClpStepDict(entityMap);
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"删除失败\"}");
		}
	}

	@Override
	public String deleteBatchHtcgClpStepDict(List<Map<String, Object>> list)
			throws DataAccessException {
		// TODO Auto-generated method stub
		try {
			htcgClpStepDictMapper.deleteBatchHtcgClpStepDict(list);
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"删除失败\"}");
		}
	}

	@Override
	public String updateHtcgClpStepDict(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		try {
			htcgClpStepDictMapper.updateHtcgClpStepDict(entityMap);
			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"修改失败\"}");
		}
	}

	@Override
	public String importHtcgClpStepDict(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		try {
			
			 List<Map<String, List<String>>> list = ReadFiles.readFiles(entityMap);
	       	  
        	 List<Map<String,Object>> resultSet = new ArrayList<Map<String,Object>>();
        	 
        	 if (list.size() == 0 || list == null) {
  				return "{\"error\":\"表头或者数据为空！请重新导入.\"}";
  			 }
             
        	 for (Map<String, List<String>> map : list) {
        		 
                 Map<String, Object> mapVo = new HashMap<String, Object>();
        		 
        		 mapVo.put("group_id", SessionManager.getGroupId());
 				
 				 mapVo.put("hos_id", SessionManager.getHosId());
 				
 				 mapVo.put("copy_code", SessionManager.getCopyCode());
 				 
 				 mapVo.put("clp_step_code", map.get("clp_step_code").get(1));
 				 
 				 mapVo.put("clp_step_name", map.get("clp_step_name").get(1));
 				 
 				 mapVo.put("is_stop", 0);
 				 
 				 mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("clp_step_name").toString()));
 				
 				 mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("clp_step_name").toString()));
 				 
 				HtcgClpStepDict htcgClpStepDict = htcgClpStepDictMapper
 						.queryHtcgClpStepDictByCode(mapVo);

 				if (null != htcgClpStepDict) {
 					
 					return "{\"error\":\""+ map.get("clp_step_code").get(0)+"时程编码:"+map.get("clp_step_code").get(1)+" 重复.\",\"state\":\"false\"}";
 				}
 				 
 				 resultSet.add(mapVo);
        	 }
        	 
        	  if(resultSet.size() > 0 ){
        		  htcgClpStepDictMapper.addBatchHtcgClpStepDict(resultSet);
        	  }
			
			 return "{\"msg\":\"导入成功.\",\"state\":\"true\"}";
			 
		} catch (Exception e) {
			// TODO: handle exception
			logger.debug(e.getMessage());
			throw new SysException(e.getMessage());
		}
	}

}
