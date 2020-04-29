
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
import com.chd.hrp.ass.dao.dict.AssBillNoMapper;
import com.chd.hrp.ass.entity.dict.AssBillNo;
import com.chd.hrp.ass.service.dict.AssBillNoService;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Description: 050110 资产单据号规则设置
 * @Table: ASS_BILL_NO
 * @Author: bell
 * @email: bell@s-chd.com
 * @Version: 1.0
 */
 
@Service("assBillNoService")
public class AssBillNoServiceImpl implements AssBillNoService {

	private static Logger logger = Logger.getLogger(AssBillNoServiceImpl.class);
	// 引入DAO操作
	@Resource(name = "assBillNoMapper")
	private final AssBillNoMapper assBillNoMapper = null;

	/**
	 * @Description 添加050110 资产单据号规则设置<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String addAssBillNo(Map<String, Object> entityMap) throws DataAccessException {

		// 获取对象050110 资产单据号规则设置
		AssBillNo assBillNo = queryAssBillNoByCode(entityMap);

		if (assBillNo != null) {

			return "{\"error\":\"数据重复,请重新添加.\"}";

		}

		try {

			int state = assBillNoMapper.addAssBillNo(entityMap);

			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}

	}

	/**
	 * @Description 批量添加050110 资产单据号规则设置<BR>
	 * @param entityList
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String addBatchAssBillNo(List<Map<String, Object>> entityList) throws DataAccessException {

		try {

			assBillNoMapper.addBatchAssBillNo(entityList);

			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}

	}

	/**
	 * @Description 更新050110 资产单据号规则设置<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String updateAssBillNo(Map<String, Object> entityMap) throws DataAccessException {

		try {
			Map<String, Object> m = new HashMap<String, Object>();
			m.put("sql", "select count(*) table_count from " + entityMap.get("bill_table").toString());

			int isexists = assBillNoMapper.queryAssBillNoIsExists(m);

			if (isexists <= 0) {
				assBillNoMapper.updateAssBillNo(entityMap);
				return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";
			} else {
				return "{\"msg\":\"已经存在数据不允许更改\",\"state\":\"true\"}";
			}
		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}

	}

	/**
	 * @Description 更新050110 资产单据号更新最大值<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String updateAssBillNoMaxNo(Map<String, Object> entityMap) throws DataAccessException {

		try {

			int state = assBillNoMapper.updateAssBillNoMaxNo(entityMap);

			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}

	}

	/**
	 * @Description 批量更新050110 资产单据号规则设置<BR>
	 * @param entityList
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String updateBatchAssBillNo(List<Map<String, Object>> entityList) throws DataAccessException {

		try {
			for (Map<String, Object> entityMap : entityList) {

				Map<String, Object> m = new HashMap<String, Object>();
				m.put("sql", "select count(*) table_count from " + entityMap.get("bill_table").toString());

				int isexists = assBillNoMapper.queryAssBillNoIsExists(m);

				if (isexists <= 0) {
					assBillNoMapper.updateAssBillNo(entityMap);
				}

			}

			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}

	}

	/**
	 * @Description 删除050110 资产单据号规则设置<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String deleteAssBillNo(Map<String, Object> entityMap) throws DataAccessException {

		try {

			int state = assBillNoMapper.deleteAssBillNo(entityMap);

			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}

	}

	/**
	 * @Description 批量删除050110 资产单据号规则设置<BR>
	 * @param entityList
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String deleteBatchAssBillNo(List<Map<String, Object>> entityList) throws DataAccessException {

		try {

			assBillNoMapper.deleteBatchAssBillNo(entityList);

			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}
	}

	/**
	 * @Description 查询结果集050110 资产单据号规则设置<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String queryAssBillNo(Map<String, Object> entityMap) throws DataAccessException {

		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		if (sysPage.getTotal() == -1) {

			List<AssBillNo> list = assBillNoMapper.queryAssBillNo(entityMap);

			return ChdJson.toJson(list);

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

			List<AssBillNo> list = assBillNoMapper.queryAssBillNo(entityMap, rowBounds);

			PageInfo page = new PageInfo(list);

			return ChdJson.toJson(list, page.getTotal());

		}

	}

	/**
	 * @Description 获取对象050110 资产单据号规则设置<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public AssBillNo queryAssBillNoByCode(Map<String, Object> entityMap) throws DataAccessException {
		return assBillNoMapper.queryAssBillNoByCode(entityMap);
	}

}
