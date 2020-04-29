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
import com.chd.hrp.prm.dao.PrmEmpFunStackMapper;
import com.chd.hrp.prm.entity.PrmEmpFunStack;
import com.chd.hrp.prm.service.PrmEmpFunStackService;
import com.github.pagehelper.PageInfo;

@Service("prmEmpFunStackService")
public class PrmEmpFunStackServiceImpl implements PrmEmpFunStackService{
	private static Logger logger = Logger.getLogger(PrmEmpFunStackServiceImpl.class);
	// 引入DAO操作
	@Resource(name = "prmEmpFunStackMapper")
	private final PrmEmpFunStackMapper PrmEmpFunStackMapper = null;

	/**
	 * @Description 添加0503 指示灯<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String addPrmEmpFunStack(Map<String, Object> entityMap) throws DataAccessException {

		// 获取对象0503 指示灯
		PrmEmpFunStack PrmEmpFunStack = queryPrmEmpFunStackByCode(entityMap);

		if (PrmEmpFunStack != null) {

			return "{\"error\":\"数据重复,请重新添加.\"}";

		}

		try {

			int state = PrmEmpFunStackMapper.addPrmEmpFunStack(entityMap);

			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addPrmEmpFunStack\"}";

		}

	}

	/**
	 * @Description 批量添加0503 指示灯<BR>
	 * @param entityList
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String addBatchPrmEmpFunStack(List<Map<String, Object>> entityList) throws DataAccessException {

		try {

			PrmEmpFunStackMapper.addBatchPrmEmpFunStack(entityList);

			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addBatchPrmEmpFunStack\"}";

		}

	}

	/**
	 * @Description 更新0503 指示灯<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String updatePrmEmpFunStack(Map<String, Object> entityMap) throws DataAccessException {

		try {

			int state = PrmEmpFunStackMapper.updatePrmEmpFunStack(entityMap);

			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 updatePrmEmpFunStack\"}";

		}

	}

	/**
	 * @Description 批量更新0503 指示灯<BR>
	 * @param entityList
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String updateBatchPrmEmpFunStack(List<Map<String, Object>> entityList) throws DataAccessException {

		try {

			PrmEmpFunStackMapper.updateBatchPrmEmpFunStack(entityList);

			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 updateBatchPrmEmpFunStack\"}";

		}

	}

	/**
	 * @Description 删除0503 指示灯<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String deletePrmEmpFunStack(Map<String, Object> entityMap) throws DataAccessException {

		try {

			int state = PrmEmpFunStackMapper.deletePrmEmpFunStack(entityMap);

			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 deletePrmEmpFunStack\"}";

		}

	}

	/**
	 * @Description 批量删除0503 指示灯<BR>
	 * @param entityList
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String deleteBatchPrmEmpFunStack(List<Map<String, Object>> entityList) throws DataAccessException {

		try {

			PrmEmpFunStackMapper.deleteBatchPrmEmpFunStack(entityList);

			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 deleteBatchPrmEmpFunStack\"}";

		}
	}

	/**
	 * @Description 查询结果集0503 指示灯<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String queryPrmEmpFunStack(Map<String, Object> entityMap) throws DataAccessException {

		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");
		if (sysPage.getTotal() == -1) {

			List<PrmEmpFunStack> list = PrmEmpFunStackMapper.queryPrmEmpFunStack(entityMap);

			return ChdJson.toJson(list);

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

			List<PrmEmpFunStack> list = PrmEmpFunStackMapper.queryPrmEmpFunStack(entityMap, rowBounds);

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
	public PrmEmpFunStack queryPrmEmpFunStackByCode(Map<String, Object> entityMap) throws DataAccessException {
		return PrmEmpFunStackMapper.queryPrmEmpFunStackByCode(entityMap);
	}
}
