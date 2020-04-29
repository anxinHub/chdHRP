package com.chd.hrp.prm.serviceImpl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.SysPage;
import com.chd.base.util.ChdJson;
import com.chd.hrp.prm.dao.PrmDeptFunStackMapper;
import com.chd.hrp.prm.entity.PrmDeptFunStack;
import com.chd.hrp.prm.service.PrmDeptFunStackService;
import com.github.pagehelper.PageInfo;

@Service("prmDeptFunStackService")
public class PrmDeptFunStackServiceImpl implements PrmDeptFunStackService{
	private static Logger logger = Logger.getLogger(PrmDeptFunStackServiceImpl.class);
	// 引入DAO操作
	@Resource(name = "prmDeptFunStackMapper")
	private final PrmDeptFunStackMapper PrmDeptFunStackMapper = null;

	/**
	 * @Description 添加0503 指示灯<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String addPrmDeptFunStack(Map<String, Object> entityMap) throws DataAccessException {

		// 获取对象0503 指示灯
		PrmDeptFunStack PrmDeptFunStack = queryPrmDeptFunStackByCode(entityMap);

		if (PrmDeptFunStack != null) {

			return "{\"error\":\"数据重复,请重新添加.\"}";

		}

		try {

			int state = PrmDeptFunStackMapper.addPrmDeptFunStack(entityMap);

			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addPrmDeptFunStack\"}";

		}

	}

	/**
	 * @Description 批量添加0503 指示灯<BR>
	 * @param entityList
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String addBatchPrmDeptFunStack(List<Map<String, Object>> entityList) throws DataAccessException {

		try {

			PrmDeptFunStackMapper.addBatchPrmDeptFunStack(entityList);

			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addBatchPrmDeptFunStack\"}";

		}

	}

	/**
	 * @Description 更新0503 指示灯<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String updatePrmDeptFunStack(Map<String, Object> entityMap) throws DataAccessException {

		try {

			int state = PrmDeptFunStackMapper.updatePrmDeptFunStack(entityMap);

			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 updatePrmDeptFunStack\"}";

		}

	}

	/**
	 * @Description 批量更新0503 指示灯<BR>
	 * @param entityList
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String updateBatchPrmDeptFunStack(List<Map<String, Object>> entityList) throws DataAccessException {

		try {

			PrmDeptFunStackMapper.updateBatchPrmDeptFunStack(entityList);

			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 updateBatchPrmDeptFunStack\"}";

		}

	}

	/**
	 * @Description 删除0503 指示灯<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String deletePrmDeptFunStack(Map<String, Object> entityMap) throws DataAccessException {

		try {

			int state = PrmDeptFunStackMapper.deletePrmDeptFunStack(entityMap);

			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 deletePrmDeptFunStack\"}";

		}

	}

	/**
	 * @Description 批量删除0503 指示灯<BR>
	 * @param entityList
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String deleteBatchPrmDeptFunStack(List<Map<String, Object>> entityList) throws DataAccessException {

		try {

			PrmDeptFunStackMapper.deleteBatchPrmDeptFunStack(entityList);

			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 deleteBatchPrmDeptFunStack\"}";

		}
	}

	/**
	 * @Description 查询结果集0503 指示灯<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String queryPrmDeptFunStack(Map<String, Object> entityMap) throws DataAccessException {

		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");
		if (sysPage.getTotal() == -1) {

			List<PrmDeptFunStack> list = PrmDeptFunStackMapper.queryPrmDeptFunStack(entityMap);

			return ChdJson.toJson(list);

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

			List<PrmDeptFunStack> list = PrmDeptFunStackMapper.queryPrmDeptFunStack(entityMap, rowBounds);

			PageInfo page = new PageInfo(list);

			return ChdJson.toJson(list, page.getTotal());

		}

	}

	/**
	 * @Description 获取对象0503 指示灯<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public PrmDeptFunStack queryPrmDeptFunStackByCode(Map<String, Object> entityMap) throws DataAccessException {
		return PrmDeptFunStackMapper.queryPrmDeptFunStackByCode(entityMap);
	}
}
