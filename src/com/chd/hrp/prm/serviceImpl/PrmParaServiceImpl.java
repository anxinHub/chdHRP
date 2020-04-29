package com.chd.hrp.prm.serviceImpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.SysPage;
import com.chd.base.util.ChdJson;
import com.chd.hrp.prm.dao.PrmParaMapper;
import com.chd.hrp.prm.entity.PrmPara;
import com.chd.hrp.prm.service.PrmParaService;
import com.chd.hrp.sys.service.base.SysConfigService;
import com.github.pagehelper.PageInfo;

@Service("prmParaService")
public class PrmParaServiceImpl implements PrmParaService {

	private static Logger logger = Logger.getLogger(PrmParaServiceImpl.class);

	@Resource(name = "prmParaMapper")
	private final PrmParaMapper prmParaMapper = null;
	
	@Resource(name = "sysConfigService")
	private final SysConfigService sysConfigService = null;

	/**
	 * @Description 系统参数<BR>
	 *              添加PrmPara
	 * @param PrmPara
	 *            entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String addPrmPara(Map<String, Object> entityMap) throws DataAccessException {

		PrmPara PrmPara = queryPrmParaByCode(entityMap);

		if (PrmPara != null) {

			return "{\"error\":\"编码：" + entityMap.get("item_code").toString() + "重复.\"}";

		}

		try {

			prmParaMapper.addPrmPara(entityMap);

			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码 addPrmPara\"}";

		}

	}

	/**
	 * @Description 系统参数<BR>
	 *              批量添加PrmPara
	 * @param PrmPara
	 *            entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String addBatchPrmPara(List<Map<String, Object>> entityList) throws DataAccessException {

		try {

			prmParaMapper.addBatchPrmPara(entityList);

			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码 addBatchPrmPara\"}";

		}
	}

	/**
	 * @Description 系统参数<BR>
	 *              查询PrmPara分页
	 * @param entityMap
	 *            RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String queryPrmPara(Map<String, Object> entityMap) throws DataAccessException {

		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
		List<PrmPara> list = prmParaMapper.queryPrmPara(entityMap, rowBounds);
		PageInfo page = new PageInfo(list);
		return ChdJson.toJson(list, page.getTotal());

	}

	/**
	 * @Description 系统参数<BR>
	 *              查询PrmParaByCode
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public PrmPara queryPrmParaByCode(Map<String, Object> entityMap) throws DataAccessException {

		return prmParaMapper.queryPrmParaByCode(entityMap);

	}

	/**
	 * @Description 系统参数<BR>
	 *              批量删除PrmPara
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String deleteBatchPrmPara(List<Map<String, Object>> entityList) throws DataAccessException {

		try {

			int state = prmParaMapper.deleteBatchPrmPara(entityList);
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码  deleteBatchPrmPara\"}";

		}

	}

	/**
	 * @Description 系统参数<BR>
	 *              删除PrmPara
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String deletePrmPara(Map<String, Object> entityMap) throws DataAccessException {

		try {
			prmParaMapper.deletePrmPara(entityMap);
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码  deletePrmPara\"}";

		}
	}

	/**
	 * @Description 系统参数<BR>
	 *              更新PrmPara
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String updatePrmPara(Map<String, Object> entityMap) throws DataAccessException {

		try {

			prmParaMapper.updatePrmPara(entityMap);
			
			entityMap.put("mod_code", "07");
			
			//加载系统参数
			sysConfigService.querySysParaInit(entityMap);

			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updatePrmPara\"}";

		}
	}

	/**
	 * @Description 系统参数<BR>
	 *              批量更新PrmPara
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String updateBatchPrmPara(List<Map<String, Object>> entityList) throws DataAccessException {

		try {
			
			Map map = entityList.get(0);
			
			map.put("mod_code", "07");
			
			//加载系统参数
			sysConfigService.querySysParaInit(map);

			prmParaMapper.updateBatchPrmPara(entityList);

			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updatePrmPara\"}";

		}

	}

	/**
	 * @Description 系统参数<BR>
	 *              导入PrmPara
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String importPrmPara(Map<String, Object> entityMap) throws DataAccessException {

		try {

			return "{\"msg\":\"导入成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"导入失败 数据库异常 请联系管理员! 错误编码  import\"}";

		}
	}

	@Override
    public String queryPrmParaValue(Map<String, Object> entityMap, String para_code) throws DataAccessException {
		
		Map<String,Object> map_para = new HashMap<String,Object>();
		
		map_para.put("group_id", entityMap.get("group_id"));
		
		map_para.put("hos_id", entityMap.get("hos_id"));
		
		map_para.put("copy_code", entityMap.get("copy_code"));
		
		map_para.put("para_code", para_code);
		
		PrmPara prmPara = prmParaMapper.queryPrmParaByCode(map_para);
		
		return String.valueOf(prmPara.getPara_value());
    }


}
