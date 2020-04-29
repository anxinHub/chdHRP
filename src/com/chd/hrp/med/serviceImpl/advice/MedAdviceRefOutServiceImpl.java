package com.chd.hrp.med.serviceImpl.advice;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.SysPage;
import com.chd.base.util.ChdJson;
import com.chd.hrp.med.dao.advice.MedAdviceRefOutMapper;
import com.chd.hrp.med.entity.MedAdviceRefOut;
import com.chd.hrp.med.service.advice.MedAdviceRefOutService;
import com.github.pagehelper.PageInfo;
@Service("medAdviceRefOutService")
public class MedAdviceRefOutServiceImpl implements MedAdviceRefOutService {
	private static Logger logger = Logger.getLogger(MedAdviceRefOutServiceImpl.class);
	//引入DAO操作
	@Resource(name = "medAdviceRefOutMapper")
	private final MedAdviceRefOutMapper medAdviceRefOutMapper = null;
	@Override
	public String addMedAdviceRefOut(Map<String, Object> entityMap) throws DataAccessException {
		MedAdviceRefOut medAdviceRefOut = queryMedAdviceRefOutByCode(entityMap);

		if (medAdviceRefOut != null) {

			return "{\"error\":\"数据重复,请重新添加.\"}";

		}
		
		try {
			
			int state = medAdviceRefOutMapper.addMedAdviceRefOut(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addMedAdviceRefOut\"}";

		}
	}

	@Override
	public String addBatchMedAdviceRefOut(List<Map<String, Object>> entityMap) throws DataAccessException {
		try {
			
			medAdviceRefOutMapper.addBatchMedAdviceRefOut(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addBatchMedAdviceRefOut\"}";

		}
	}

	@Override
	public String updateMedAdviceRefOut(Map<String, Object> entityMap) throws DataAccessException {
		try {
			
			  int state = medAdviceRefOutMapper.updateMedAdviceRefOut(entityMap);
				
				return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

			}
			catch (Exception e) {

				logger.error(e.getMessage(), e);

				return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 updateMedAdviceRefOut\"}";

			}	
	}

	@Override
	public String updateBatchMedAdviceRefOut(List<Map<String, Object>> entityMap) throws DataAccessException {
		try {
			
				medAdviceRefOutMapper.updateBatchMedAdviceRefOut(entityMap);
				
				return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

			}
			catch (Exception e) {

				logger.error(e.getMessage(), e);

				return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 updateBatchMedAdviceRefOut\"}";

			}	
	}

	@Override
	public String deleteMedAdviceRefOut(Map<String, Object> entityMap) throws DataAccessException {
		try {
			
			int state = medAdviceRefOutMapper.deleteMedAdviceRefOut(entityMap);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 deleteMedAdviceRefOut\"}";

		}
	}

	@Override
	public String deleteBatchMedAdviceRefOut(List<Map<String, Object>> entityMap) throws DataAccessException {
		try {
			
			medAdviceRefOutMapper.deleteBatchMedAdviceRefOut(entityMap);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 deleteBatchMedAdviceRefOut\"}";

		}	
	}

	@Override
	public String queryMedAdviceRefOut(Map<String, Object> entityMap) throws DataAccessException {
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<MedAdviceRefOut> list = medAdviceRefOutMapper.queryMedAdviceRefOut(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<MedAdviceRefOut> list = medAdviceRefOutMapper.queryMedAdviceRefOut(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
	}

	@Override
	public MedAdviceRefOut queryMedAdviceRefOutByCode(Map<String, Object> entityMap) throws DataAccessException {
		return medAdviceRefOutMapper.queryMedAdviceRefOutByCode(entityMap);
	}

	@Override
	public MedAdviceRefOut queryMedAdviceRefOutByUniqueness(Map<String, Object> entityMap) throws DataAccessException {
		return medAdviceRefOutMapper.queryMedAdviceRefOutByUniqueness(entityMap);
	}

}
