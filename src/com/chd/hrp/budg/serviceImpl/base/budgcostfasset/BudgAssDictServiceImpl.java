/**
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
package com.chd.hrp.budg.serviceImpl.base.budgcostfasset;

import java.util.*;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.SessionManager;
import com.chd.base.SysPage;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.hrp.ass.dao.dict.AssNoDictMapper;
import com.chd.hrp.ass.entity.dict.AssDict;
import com.chd.hrp.budg.dao.base.budgcostfasset.BudgAssDictMapper;
import com.chd.hrp.budg.service.base.budgcostfasset.BudgAssDictService;
import com.github.pagehelper.PageInfo;

/**
 * @Description: 050102 资产字典
 * @Table: ASS_DICT
 * @Author: bell
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Service("budgAssDictService")
public class BudgAssDictServiceImpl implements BudgAssDictService {

	private static Logger logger = Logger.getLogger(BudgAssDictServiceImpl.class);

	// 引入DAO操作
	@Resource(name = "budgAssDictMapper")
	private final BudgAssDictMapper budgAssDictMapper = null;

	@Resource(name = "assNoDictMapper")
	private final AssNoDictMapper assNoDictMapper = null;

	/**
	 * @Description 添加050102 资产字典<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String add(Map<String, Object> entityMap) throws DataAccessException {

		// 获取对象050102 资产字典
		AssDict assDict = budgAssDictMapper.queryByCode(entityMap);

		if (assDict != null) {

			return "{\"error\":\"数据重复,请重新添加.\"}";

		}

		try {

			budgAssDictMapper.add(entityMap);

			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}

	}

	/**
	 * @Description 批量添加050102 资产字典<BR>
	 * @param entityList
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String addBatch(List<Map<String, Object>> entityList) throws DataAccessException {

		try {

			budgAssDictMapper.addBatch(entityList);

			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}

	}

	/**
	 * @Description 更新050102 资产字典<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String update(Map<String, Object> entityMap) throws DataAccessException {
		try {
			
			budgAssDictMapper.update(entityMap);
			
			if (entityMap.get("history").equals("true")) {

				Map<String, Object> map = new HashMap<String, Object>();
				map.put("group_id", entityMap.get("group_id"));
				map.put("hos_id", entityMap.get("hos_id"));
				map.put("copy_code", entityMap.get("copy_code"));
				map.put("ass_id", entityMap.get("ass_id"));
				map.put("is_stop", "1");
				assNoDictMapper.updateAssNoDictCharge(map);

				int state = assNoDictMapper.addAssNoDict(entityMap);

				return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

			} else {

				budgAssDictMapper.update(entityMap);
				
				assNoDictMapper.updateAssNoDictCharge(entityMap);
				
				return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";
			}


		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}

	}

	/**
	 * @Description 批量更新050102 资产字典<BR>
	 * @param entityList
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String updateBatch(List<Map<String, Object>> entityList) throws DataAccessException {

		try {

			budgAssDictMapper.updateBatch(entityList);

			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}

	}

	/**
	 * @Description 删除050102 资产字典<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String delete(Map<String, Object> entityMap) throws DataAccessException {
		try {
			budgAssDictMapper.delete(entityMap);
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}

	}

	/**
	 * @Description 批量删除050102 资产字典<BR>
	 * @param entityList
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String deleteBatch(List<Map<String, Object>> entityList) throws DataAccessException {

		try {

			budgAssDictMapper.deleteBatch(entityList);

			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}
	}

	/**
	 * @Description 查询结果集050102 资产字典<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String query(Map<String, Object> entityMap) throws DataAccessException {

		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		if (sysPage.getTotal() == -1) {

			List<AssDict> list = (List<AssDict>) budgAssDictMapper.query(entityMap);

			return ChdJson.toJson(list);

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

			List<AssDict> list = (List<AssDict>) budgAssDictMapper.query(entityMap, rowBounds);

			PageInfo page = new PageInfo(list);

			return ChdJson.toJson(list, page.getTotal());

		}

	}

	/**
	 * @Description 获取对象050102 资产字典<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public <T> T queryByCode(Map<String, Object> entityMap) throws DataAccessException {
		return budgAssDictMapper.queryByCode(entityMap);
	}

	@Override
	public <T> T queryByUniqueness(Map<String, Object> entityMap) throws DataAccessException {
		return budgAssDictMapper.queryByUniqueness(entityMap);
	}

	@Override
	public String addOrUpdate(Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<?> queryExists(Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

}
