/**
 * @Copyright: Copyright (c) 2015-2-14
 * @Company: 智慧云康（北京）数据科技有限公司
 */

package com.chd.hrp.sys.serviceImpl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.nutz.lang.Strings;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.SessionManager;
import com.chd.base.SysPage;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.hrp.sys.dao.CopyMapper;
import com.chd.hrp.sys.dao.InitProcMapper;
import com.chd.hrp.sys.entity.Copy;
import com.chd.hrp.sys.service.CopyService;
import com.chd.hrp.sys.service.base.SysConfigService;
import com.github.pagehelper.PageInfo;

/**
 * @Title. @Description.
 * 
 * @Author: LiuYingDuo
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Service("copyService")
public class CopyServiceImpl implements CopyService {

	private static Logger logger = Logger.getLogger(CopyServiceImpl.class);

	@Resource(name = "copyMapper")
	private final CopyMapper copyMapper = null;

	@Resource(name = "initProcMapper")
	private final InitProcMapper initProcMapper = null;
	@Resource(name = "sysConfigService")
	private final SysConfigService sysConfigService = null;

	/**
	 * @Description 添加Copy
	 * @param Copy
	 *            entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String addCopy(Map<String, Object> entityMap) throws DataAccessException {
		entityMap.put("group_id", SessionManager.getGroupId());
		entityMap.put("hos_id", SessionManager.getHosId());
		Copy copy = queryCopyByCode(entityMap);

		if (copy != null) {

			return "{\"error\":\"编码：" + copy.getCopy_code().toString() + "已存在.\"}";

		}
		try {

			int count=copyMapper.addCopy(entityMap);
			// 初始化内置数据
			if(count>0){
				initProcMapper.saveInitCopyProc(entityMap);
			}

			//加载系统参数
			sysConfigService.querySysParaInit(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage(), e);

		}

	}

	/**
	 * @Description 批量添加Copy
	 * @param Copy
	 *            entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String addBatchCopy(List<Map<String, Object>> entityList) throws DataAccessException {

		try {

			copyMapper.addBatchCopy(entityList);

			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码 addBatchCopy\"}";

		}
	}

	/**
	 * @Description 查询Copy分页
	 * @param entityMap
	 *            RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String queryCopy(Map<String, Object> entityMap) throws DataAccessException {


		List<Copy> list = copyMapper.queryCopy(entityMap);


		return ChdJson.toJson(list);

	}

	/**
	 * @Description 查询CopyByCode
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public Copy queryCopyByCode(Map<String, Object> entityMap) throws DataAccessException {

		return copyMapper.queryCopyByCode(entityMap);

	}

	/**
	 * @Description 批量删除Copy
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String deleteBatchCopy(List<Map<String, Object>> entityList) throws DataAccessException {

		try {

			int state = copyMapper.deleteBatchCopy(entityList);
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage(), e);

		}

	}

	/**
	 * @Description 删除Copy
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String deleteCopy(Map<String, Object> entityMap) throws DataAccessException {

		try {
			copyMapper.deleteCopy(entityMap);
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码  deleteCopy\"}";

		}
	}

	/**
	 * @Description 更新Copy
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String updateCopy(Map<String, Object> entityMap) throws DataAccessException {

		try {

			copyMapper.updateCopy(entityMap);

			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage(), e);

		}
	}

	/**
	 * @Description 批量更新Copy
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String updateBatchCopy(List<Map<String, Object>> entityList) throws DataAccessException {

		try {

			copyMapper.updateBatchCopy(entityList);

			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updateCopy\"}";

		}

	}

	/**
	 * @Description 导入Copy
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String importCopy(Map<String, Object> entityMap) throws DataAccessException {

		try {

			return "{\"msg\":\"导入成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"导入失败 数据库异常 请联系管理员! 错误编码  import{className}\"}";

		}
	}

}
