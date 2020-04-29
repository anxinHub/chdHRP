
/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
package com.chd.hrp.ass.serviceImpl.dict;

import java.util.*;
import javax.annotation.Resource;
import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import com.chd.base.SysPage;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.hrp.acc.entity.AccPayType;
import com.chd.hrp.ass.dao.dict.AssNatursMapper;
import com.chd.hrp.ass.entity.dict.AssNaturs;
import com.chd.hrp.ass.service.dict.AssNatursService;
import com.chd.hrp.sys.entity.StoreDict;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Description: 050103 资产性质
 * @Table: ASS_NATURS
 * @Author: bell
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Service("assNatursService")
public class AssNatursServiceImpl implements AssNatursService {

	private static Logger logger = Logger.getLogger(AssNatursServiceImpl.class);
	// 引入DAO操作
	@Resource(name = "assNatursMapper")
	private final AssNatursMapper assNatursMapper = null;

	/**
	 * @Description 添加050103 资产性质<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String addAssNaturs(Map<String, Object> entityMap) throws DataAccessException {

		// 获取对象050103 资产性质
		AssNaturs assNaturs = queryAssNatursByCode(entityMap);

		if (assNaturs != null) {

			return "{\"error\":\"数据重复,请重新添加.\"}";

		}

		try {

			int state = assNatursMapper.addAssNaturs(entityMap);

			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}

	}

	/**
	 * @Description 批量添加050103 资产性质<BR>
	 * @param entityList
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String addBatchAssNaturs(List<Map<String, Object>> entityList) throws DataAccessException {

		try {

			assNatursMapper.addBatchAssNaturs(entityList);

			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}

	}

	/**
	 * @Description 更新050103 资产性质<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String updateAssNaturs(Map<String, Object> entityMap) throws DataAccessException {

		try {

			int state = assNatursMapper.updateAssNaturs(entityMap);

			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}

	}

	/**
	 * @Description 批量更新050103 资产性质<BR>
	 * @param entityList
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String updateBatchAssNaturs(List<Map<String, Object>> entityList) throws DataAccessException {

		try {

			assNatursMapper.updateBatchAssNaturs(entityList);

			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}

	}

	/**
	 * @Description 删除050103 资产性质<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String deleteAssNaturs(Map<String, Object> entityMap) throws DataAccessException {

		try {

			int state = assNatursMapper.deleteAssNaturs(entityMap);

			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}

	}

	/**
	 * @Description 批量删除050103 资产性质<BR>
	 * @param entityList
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String deleteBatchAssNaturs(List<Map<String, Object>> entityList) throws DataAccessException {

		try {

			assNatursMapper.deleteBatchAssNaturs(entityList);

			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}
	}

	/**
	 * @Description 查询结果集050103 资产性质<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String queryAssNaturs(Map<String, Object> entityMap) throws DataAccessException {

		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		if (sysPage.getTotal() == -1) {

			List<AssNaturs> list = assNatursMapper.queryAssNaturs(entityMap);

			return ChdJson.toJson(list);

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

			List<AssNaturs> list = assNatursMapper.queryAssNaturs(entityMap, rowBounds);

			PageInfo page = new PageInfo(list);

			return ChdJson.toJson(list, page.getTotal());

		}

	}

	/**
	 * @Description 获取对象050103 资产性质<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public AssNaturs queryAssNatursByCode(Map<String, Object> entityMap) throws DataAccessException {
		return assNatursMapper.queryAssNatursByCode(entityMap);
	}

	/**
	 * 查询仓库
	 */
	@Override
	public String queryHosStoreDict(Map<String, Object> entityMap) throws DataAccessException {

		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
		List<Map<String, Object>> list = assNatursMapper.queryHosStoreDictAll(entityMap, rowBounds);
		PageInfo page = new PageInfo(list);
		return ChdJson.toJson(list, page.getTotal());

	}

	// 修改资产性质对应仓库
	@Override
	public String updateAssNatursStore(ArrayList<Map<String, Object>> entityMap) throws DataAccessException {
		try {

			Map<String, Object> mapVo = new HashMap<String, Object>();
			mapVo.put("group_id", entityMap.get(0).get("group_id"));
			mapVo.put("hos_id", entityMap.get(0).get("hos_id"));
			mapVo.put("copy_code", entityMap.get(0).get("copy_code"));
			mapVo.put("naturs_code", entityMap.get(0).get("naturs_code"));
			mapVo.put("store_id", entityMap.get(0).get("store_id"));
			assNatursMapper.deleteAssNatursStore(entityMap);
			if (entityMap.get(0).get("store_id") != null && !entityMap.get(0).get("store_id").equals("")){
				assNatursMapper.updateAssNatursStore(entityMap);
			}

			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}

	}

	/**
	 * 查询仓库
	 *//*
	@Override
	public String queryHosStoreDictByNaturs(Map<String, Object> entityMap) throws DataAccessException {

		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
		List<Map<String, Object>> list1 = assNatursMapper.queryHosStoreDict(entityMap, rowBounds);
		List<Map<String, Object>> list = assNatursMapper.queryHosStoreDictByNaturs(entityMap, rowBounds);
		List<Map<String, Object>> returnlist = new ArrayList<Map<String, Object>>();
		for (Map<String, Object> storeDict : list1) {

			for (Map<String, Object> map : list) {
				if (storeDict.get("store_id").equals(map.get("store_id"))) {
					storeDict.put("naturs_code", map.get("naturs_code"));
				}
			}

			returnlist.add(storeDict);
		}
		PageInfo page = new PageInfo(returnlist);
		return ChdJson.toJson(returnlist, page.getTotal());

	}
*/
}
