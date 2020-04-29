package com.chd.hrp.mat.serviceImpl.advice;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.SysPage;
import com.chd.base.util.ChdJson;
import com.chd.hrp.mat.dao.advice.MatRefStoreDeptMapper;
import com.chd.hrp.mat.entity.MatAdvice;
import com.chd.hrp.mat.entity.MatRefStoreDept;
import com.chd.hrp.mat.service.advice.MatRefStoreDeptService;
import com.github.pagehelper.PageInfo;
@Service("matRefStoreDeptService")
public class MatRefStoreDeptServiceImpl implements MatRefStoreDeptService {
	private static Logger logger = Logger.getLogger(MatRefStoreDeptServiceImpl.class);
	//引入DAO操作
	@Resource(name = "matRefStoreDeptMapper")
	private final MatRefStoreDeptMapper matRefStoreDeptMapper = null;
	@Override
	public String addMatRefStoreDept(Map<String, Object> entityMap) throws DataAccessException {
		MatRefStoreDept matRefStoreDept = queryMatRefStoreDeptOutByCode(entityMap);

		if (matRefStoreDept != null) {

			return "{\"error\":\"数据重复,请重新添加.\"}";

		}
		
		try {
			
			int state = matRefStoreDeptMapper.addMatRefStoreDept(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addMatRefStoreDept\"}";

		}
	}

	@Override
	public String addBatchMatRefStoreDept(List<Map<String, Object>> entityMap) throws DataAccessException {
		try {
			
			matRefStoreDeptMapper.addBatchMatRefStoreDept(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addBatchMatRefStoreDept\"}";

		}
	}

	@Override
	public String updateMatRefStoreDept(Map<String, Object> entityMap) throws DataAccessException {
		try {
			
			  int state = matRefStoreDeptMapper.updateMatRefStoreDept(entityMap);
				
				return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

			}
			catch (Exception e) {

				logger.error(e.getMessage(), e);

				return "{\"error\":\"更新失败 数据库异常 请联系管理员! 方法 updateMatRefStoreDept\"}";

			}
	}

	@Override
	public String updateBatchMatRefStoreDept(List<Map<String, Object>> entityMap) throws DataAccessException {
		try {
			
			matRefStoreDeptMapper.updateBatchMatRefStoreDept(entityMap);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 updateBatchMatRefStoreDept\"}";

		}
	}

	@Override
	public String deleteMatRefStoreDept(Map<String, Object> entityMap) throws DataAccessException {
		try {
			
			int state = matRefStoreDeptMapper.deleteMatRefStoreDept(entityMap);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 deleteMatRefStoreDept\"}";

		}
	}

	@Override
	public String deleteBatchMatRefStoreDept(List<Map<String, Object>> entityMap) throws DataAccessException {
		try {
			
			matRefStoreDeptMapper.deleteBatchMatRefStoreDept(entityMap);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 deleteBatchMatRefStoreDept\"}";

		}
	}

	@Override
	public String queryMatRefStoreDept(Map<String, Object> entityMap) throws DataAccessException {
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<MatRefStoreDept> list = matRefStoreDeptMapper.queryMatRefStoreDept(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<MatRefStoreDept> list = matRefStoreDeptMapper.queryMatRefStoreDept(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
	}

	@Override
	public MatRefStoreDept queryMatRefStoreDeptOutByCode(Map<String, Object> entityMap) throws DataAccessException {
		return matRefStoreDeptMapper.queryMatRefStoreDeptOutByCode(entityMap);
	}

	@Override
	public MatRefStoreDept queryMatRefStoreDeptOutByUniqueness(Map<String, Object> entityMap)
			throws DataAccessException {
		return matRefStoreDeptMapper.queryMatRefStoreDeptOutByUniqueness(entityMap);
	}

	@Override
	public String queryMatRefStoreDeptByStore(Map<String, Object> entityMap) throws DataAccessException {
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<MatRefStoreDept> list = matRefStoreDeptMapper.queryMatRefStoreDeptByStore(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<MatRefStoreDept> list = matRefStoreDeptMapper.queryMatRefStoreDeptByStore(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
	}

}
