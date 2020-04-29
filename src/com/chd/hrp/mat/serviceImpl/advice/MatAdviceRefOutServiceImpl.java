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
import com.chd.hrp.mat.dao.advice.MatAdviceRefOutMapper;
import com.chd.hrp.mat.entity.MatAdviceRefOut;
import com.chd.hrp.mat.service.advice.MatAdviceRefOutService;
import com.github.pagehelper.PageInfo;
@Service("matAdviceRefOutService")
public class MatAdviceRefOutServiceImpl implements MatAdviceRefOutService {
	private static Logger logger = Logger.getLogger(MatAdviceRefOutServiceImpl.class);
	//引入DAO操作
	@Resource(name = "matAdviceRefOutMapper")
	private final MatAdviceRefOutMapper matAdviceRefOutMapper = null;
	@Override
	public String addMatAdviceRefOut(Map<String, Object> entityMap) throws DataAccessException {
		MatAdviceRefOut matAdviceRefOut = queryMatAdviceRefOutByCode(entityMap);

		if (matAdviceRefOut != null) {

			return "{\"error\":\"数据重复,请重新添加.\"}";

		}
		
		try {
			
			int state = matAdviceRefOutMapper.addMatAdviceRefOut(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addMatAdviceRefOut\"}";

		}
	}

	@Override
	public String addBatchMatAdviceRefOut(List<Map<String, Object>> entityMap) throws DataAccessException {
		try {
			
			matAdviceRefOutMapper.addBatchMatAdviceRefOut(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addBatchMatAdviceRefOut\"}";

		}
	}

	@Override
	public String updateMatAdviceRefOut(Map<String, Object> entityMap) throws DataAccessException {
		try {
			
			  int state = matAdviceRefOutMapper.updateMatAdviceRefOut(entityMap);
				
				return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

			}
			catch (Exception e) {

				logger.error(e.getMessage(), e);

				return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 updateMatAdviceRefOut\"}";

			}	
	}

	@Override
	public String updateBatchMatAdviceRefOut(List<Map<String, Object>> entityMap) throws DataAccessException {
		try {
			
				matAdviceRefOutMapper.updateBatchMatAdviceRefOut(entityMap);
				
				return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

			}
			catch (Exception e) {

				logger.error(e.getMessage(), e);

				return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 updateBatchMatAdviceRefOut\"}";

			}	
	}

	@Override
	public String deleteMatAdviceRefOut(Map<String, Object> entityMap) throws DataAccessException {
		try {
			
			int state = matAdviceRefOutMapper.deleteMatAdviceRefOut(entityMap);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 deleteMatAdviceRefOut\"}";

		}
	}

	@Override
	public String deleteBatchMatAdviceRefOut(List<Map<String, Object>> entityMap) throws DataAccessException {
		try {
			
			matAdviceRefOutMapper.deleteBatchMatAdviceRefOut(entityMap);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 deleteBatchMatAdviceRefOut\"}";

		}	
	}

	@Override
	public String queryMatAdviceRefOut(Map<String, Object> entityMap) throws DataAccessException {
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<MatAdviceRefOut> list = matAdviceRefOutMapper.queryMatAdviceRefOut(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<MatAdviceRefOut> list = matAdviceRefOutMapper.queryMatAdviceRefOut(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
	}

	@Override
	public MatAdviceRefOut queryMatAdviceRefOutByCode(Map<String, Object> entityMap) throws DataAccessException {
		return matAdviceRefOutMapper.queryMatAdviceRefOutByCode(entityMap);
	}

	@Override
	public MatAdviceRefOut queryMatAdviceRefOutByUniqueness(Map<String, Object> entityMap) throws DataAccessException {
		return matAdviceRefOutMapper.queryMatAdviceRefOutByUniqueness(entityMap);
	}

}
