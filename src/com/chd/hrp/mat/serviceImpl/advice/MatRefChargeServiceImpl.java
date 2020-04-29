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
import com.chd.hrp.mat.dao.advice.MatRefChargeMapper;
import com.chd.hrp.mat.entity.MatRefCharge;
import com.chd.hrp.mat.entity.MatRefStoreDept;
import com.chd.hrp.mat.service.advice.MatRefChargeService;
import com.github.pagehelper.PageInfo;
@Service("matRefChargeService")
public class MatRefChargeServiceImpl implements MatRefChargeService {
	private static Logger logger = Logger.getLogger(MatRefChargeServiceImpl.class);
	//引入DAO操作
	@Resource(name = "matRefChargeMapper")
	private final MatRefChargeMapper matRefChargeMapper = null;
	@Override
	public String addMatRefCharge(Map<String, Object> entityMap) throws DataAccessException {
		MatRefCharge matRefCharge = queryMatRefChargeByCode(entityMap);
		try {
			if (matRefCharge != null) {
				matRefChargeMapper.updateMatRefCharge(entityMap);
				return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

			}
			int state = matRefChargeMapper.addMatRefCharge(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addMatRefCharge\"}";

		}
	}

	@Override
	public String addBatchMatRefCharge(List<Map<String, Object>> entityMap) throws DataAccessException {
		try {
			
			matRefChargeMapper.addBatchMatRefCharge(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addBatchMatRefCharge\"}";

		}
	}

	@Override
	public String updateMatRefCharge(Map<String, Object> entityMap) throws DataAccessException {
		try {
			
			  int state = matRefChargeMapper.updateMatRefCharge(entityMap);
				
				return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

			}
			catch (Exception e) {

				logger.error(e.getMessage(), e);

				return "{\"error\":\"更新失败 数据库异常 请联系管理员! 方法 updateMatRefCharge\"}";

			}
	}

	@Override
	public String updateBatchMatRefCharge(List<Map<String, Object>> entityMap) throws DataAccessException {
		try {
			
			matRefChargeMapper.updateBatchMatRefCharge(entityMap);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 updateBatchMatRefCharge\"}";

		}
	}

	@Override
	public String deleteMatRefCharge(Map<String, Object> entityMap) throws DataAccessException {
		try {
			
			int state = matRefChargeMapper.deleteMatRefCharge(entityMap);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 deleteMatRefCharge\"}";

		}
	}

	@Override
	public String deleteBatchMatRefCharge(List<Map<String, Object>> entityMap) throws DataAccessException {
		try {
			
			matRefChargeMapper.deleteBatchMatRefCharge(entityMap);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 deleteBatchMatRefCharge\"}";

		}
	}

	@Override
	public String queryMatRefCharge(Map<String, Object> entityMap) throws DataAccessException {
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<MatRefCharge> list = matRefChargeMapper.queryMatRefCharge(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<MatRefCharge> list = matRefChargeMapper.queryMatRefCharge(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
	}

	@Override
	public MatRefCharge queryMatRefChargeByCode(Map<String, Object> entityMap) throws DataAccessException {
		return matRefChargeMapper.queryMatRefChargeByCode(entityMap);
	}

	@Override
	public MatRefCharge queryMatRefChargeByUniqueness(Map<String, Object> entityMap) throws DataAccessException {
		return matRefChargeMapper.queryMatRefChargeByUniqueness(entityMap);
	}

}
