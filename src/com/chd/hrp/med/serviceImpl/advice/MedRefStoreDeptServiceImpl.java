package com.chd.hrp.med.serviceImpl.advice;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.SysPage;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.hrp.med.dao.advice.MedRefStoreDeptMapper;
import com.chd.hrp.med.entity.MedAdvice;
import com.chd.hrp.med.entity.MedRefStoreDept;
import com.chd.hrp.med.service.advice.MedRefStoreDeptService;
import com.github.pagehelper.PageInfo;
@Service("medRefStoreDeptService")
public class MedRefStoreDeptServiceImpl implements MedRefStoreDeptService {
	private static Logger logger = Logger.getLogger(MedRefStoreDeptServiceImpl.class);
	//引入DAO操作
	@Resource(name = "medRefStoreDeptMapper")
	private final MedRefStoreDeptMapper medRefStoreDeptMapper = null;
	@Override
	public String addMedRefStoreDept(Map<String, Object> entityMap) throws DataAccessException {
		MedRefStoreDept medRefStoreDept = queryMedRefStoreDeptOutByCode(entityMap);

		if (medRefStoreDept != null) {

			return "{\"error\":\"数据重复,请重新添加.\"}";

		}
		
		try {
			
			int state = medRefStoreDeptMapper.addMedRefStoreDept(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addMedRefStoreDept\"}";

		}
	}

	@Override
	public String addBatchMedRefStoreDept(List<Map<String, Object>> entityMap) throws DataAccessException {
		try {
			
			//先删除,后添加 --避免违反唯一约束
			medRefStoreDeptMapper.deleteBatchMedRefStoreDept(entityMap);
			
			medRefStoreDeptMapper.addBatchMedRefStoreDept(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException("{\"error\":\"操作失败 \"}");

		}
	}

	@Override
	public String updateMedRefStoreDept(Map<String, Object> entityMap) throws DataAccessException {
		try {
			
			  int state = medRefStoreDeptMapper.updateMedRefStoreDept(entityMap);
				
				return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

			}
			catch (Exception e) {

				logger.error(e.getMessage(), e);

				return "{\"error\":\"更新失败 数据库异常 请联系管理员! 方法 updateMedRefStoreDept\"}";

			}
	}

	@Override
	public String updateBatchMedRefStoreDept(List<Map<String, Object>> entityMap) throws DataAccessException {
		try {
			
			medRefStoreDeptMapper.updateBatchMedRefStoreDept(entityMap);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 updateBatchMedRefStoreDept\"}";

		}
	}

	@Override
	public String deleteMedRefStoreDept(Map<String, Object> entityMap) throws DataAccessException {
		try {
			
			int state = medRefStoreDeptMapper.deleteMedRefStoreDept(entityMap);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 deleteMedRefStoreDept\"}";

		}
	}

	@Override
	public String deleteBatchMedRefStoreDept(List<Map<String, Object>> entityMap) throws DataAccessException {
		try {
			
			medRefStoreDeptMapper.deleteBatchMedRefStoreDept(entityMap);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 deleteBatchMedRefStoreDept\"}";

		}
	}

	@Override
	public String queryMedRefStoreDept(Map<String, Object> entityMap) throws DataAccessException {
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<MedRefStoreDept> list = medRefStoreDeptMapper.queryMedRefStoreDept(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<MedRefStoreDept> list = medRefStoreDeptMapper.queryMedRefStoreDept(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
	}

	@Override
	public MedRefStoreDept queryMedRefStoreDeptOutByCode(Map<String, Object> entityMap) throws DataAccessException {
		return medRefStoreDeptMapper.queryMedRefStoreDeptOutByCode(entityMap);
	}

	@Override
	public MedRefStoreDept queryMedRefStoreDeptOutByUniqueness(Map<String, Object> entityMap)
			throws DataAccessException {
		return medRefStoreDeptMapper.queryMedRefStoreDeptOutByUniqueness(entityMap);
	}

	@Override
	public String queryMedRefStoreDeptByStore(Map<String, Object> entityMap) throws DataAccessException {
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<MedRefStoreDept> list = medRefStoreDeptMapper.queryMedRefStoreDeptByStore(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<MedRefStoreDept> list = medRefStoreDeptMapper.queryMedRefStoreDeptByStore(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
	}

}
