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
import com.chd.hrp.prm.dao.PrmHosFunStackMapper;
import com.chd.hrp.prm.entity.PrmHosFunStack;
import com.chd.hrp.prm.service.PrmHosFunStackService;
import com.github.pagehelper.PageInfo;

@Service("prmHosFunStackService")
public class PrmHosFunStackServiceImpl implements PrmHosFunStackService{
	private static Logger logger = Logger.getLogger(PrmHosFunStackServiceImpl.class);
	// 引入DAO操作
	@Resource(name = "prmHosFunStackMapper")
	private final PrmHosFunStackMapper PrmHosFunStackMapper = null;

	/**
	 * @Description 添加0503 指示灯<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String addPrmHosFunStack(Map<String, Object> entityMap) throws DataAccessException {

		// 获取对象0503 指示灯
		PrmHosFunStack PrmHosFunStack = queryPrmHosFunStackByCode(entityMap);

		if (PrmHosFunStack != null) {

			return "{\"error\":\"数据重复,请重新添加.\"}";

		}

		try {

			int state = PrmHosFunStackMapper.addPrmHosFunStack(entityMap);

			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addPrmHosFunStack\"}";

		}

	}

	/**
	 * @Description 批量添加0503 指示灯<BR>
	 * @param entityList
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String addBatchPrmHosFunStack(List<Map<String, Object>> entityList) throws DataAccessException {

		try {

			PrmHosFunStackMapper.addBatchPrmHosFunStack(entityList);

			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addBatchPrmHosFunStack\"}";

		}

	}

	/**
	 * @Description 更新0503 指示灯<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String updatePrmHosFunStack(Map<String, Object> entityMap) throws DataAccessException {

		try {

			int state = PrmHosFunStackMapper.updatePrmHosFunStack(entityMap);

			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 updatePrmHosFunStack\"}";

		}

	}

	/**
	 * @Description 批量更新0503 指示灯<BR>
	 * @param entityList
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String updateBatchPrmHosFunStack(List<Map<String, Object>> entityList) throws DataAccessException {

		try {

			PrmHosFunStackMapper.updateBatchPrmHosFunStack(entityList);

			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 updateBatchPrmHosFunStack\"}";

		}

	}

	/**
	 * @Description 删除0503 指示灯<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String deletePrmHosFunStack(Map<String, Object> entityMap) throws DataAccessException {

		try {

			int state = PrmHosFunStackMapper.deletePrmHosFunStack(entityMap);

			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 deletePrmHosFunStack\"}";

		}

	}

	/**
	 * @Description 批量删除0503 指示灯<BR>
	 * @param entityList
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String deleteBatchPrmHosFunStack(List<Map<String, Object>> entityList) throws DataAccessException {

		try {

			PrmHosFunStackMapper.deleteBatchPrmHosFunStack(entityList);

			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 deleteBatchPrmHosFunStack\"}";

		}
	}

	/**
	 * @Description 查询结果集0503 指示灯<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String queryPrmHosFunStack(Map<String, Object> entityMap) throws DataAccessException {

		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");
		if (sysPage.getTotal() == -1) {

			List<PrmHosFunStack> list = PrmHosFunStackMapper.queryPrmHosFunStack(entityMap);

			return ChdJson.toJson(list);

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

			List<PrmHosFunStack> list = PrmHosFunStackMapper.queryPrmHosFunStack(entityMap, rowBounds);

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
	public PrmHosFunStack queryPrmHosFunStackByCode(Map<String, Object> entityMap) throws DataAccessException {
		return PrmHosFunStackMapper.queryPrmHosFunStackByCode(entityMap);
	}
}
