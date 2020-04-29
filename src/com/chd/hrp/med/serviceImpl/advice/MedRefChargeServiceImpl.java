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
import com.chd.hrp.med.dao.advice.MedRefChargeMapper;
import com.chd.hrp.med.entity.MedRefCharge;
import com.chd.hrp.med.entity.MedRefStoreDept;
import com.chd.hrp.med.service.advice.MedRefChargeService;
import com.github.pagehelper.PageInfo;
@Service("medRefChargeService")
public class MedRefChargeServiceImpl implements MedRefChargeService {
	private static Logger logger = Logger.getLogger(MedRefChargeServiceImpl.class);
	//引入DAO操作
	@Resource(name = "medRefChargeMapper")
	private final MedRefChargeMapper medRefChargeMapper = null;
	@Override
	public String addMedRefCharge(Map<String, Object> entityMap) throws DataAccessException {
		MedRefCharge medRefCharge = queryMedRefChargeByCode(entityMap);
		try {
			if (medRefCharge != null) {
				medRefChargeMapper.updateMedRefCharge(entityMap);
				return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

			}
			int state = medRefChargeMapper.addMedRefCharge(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addMedRefCharge\"}";

		}
	}

	@Override
	public String addBatchMedRefCharge(List<Map<String, Object>> entityMap) throws DataAccessException {
		try {
			
			medRefChargeMapper.addBatchMedRefCharge(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addBatchMedRefCharge\"}";

		}
	}

	@Override
	public String updateMedRefCharge(Map<String, Object> entityMap) throws DataAccessException {
		try {
			
			  int state = medRefChargeMapper.updateMedRefCharge(entityMap);
				
				return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

			}
			catch (Exception e) {

				logger.error(e.getMessage(), e);

				return "{\"error\":\"更新失败 数据库异常 请联系管理员! 方法 updateMedRefCharge\"}";

			}
	}

	@Override
	public String updateBatchMedRefCharge(List<Map<String, Object>> entityMap) throws DataAccessException {
		try {
			
			medRefChargeMapper.updateBatchMedRefCharge(entityMap);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 updateBatchMedRefCharge\"}";

		}
	}

	@Override
	public String deleteMedRefCharge(Map<String, Object> entityMap) throws DataAccessException {
		try {
			
			int state = medRefChargeMapper.deleteMedRefCharge(entityMap);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 deleteMedRefCharge\"}";

		}
	}

	@Override
	public String deleteBatchMedRefCharge(List<Map<String, Object>> entityMap) throws DataAccessException {
		try {
			
			medRefChargeMapper.deleteBatchMedRefCharge(entityMap);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 deleteBatchMedRefCharge\"}";

		}
	}

	@Override
	public String queryMedRefCharge(Map<String, Object> entityMap) throws DataAccessException {
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<MedRefCharge> list = medRefChargeMapper.queryMedRefCharge(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<MedRefCharge> list = medRefChargeMapper.queryMedRefCharge(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
	}

	@Override
	public MedRefCharge queryMedRefChargeByCode(Map<String, Object> entityMap) throws DataAccessException {
		return medRefChargeMapper.queryMedRefChargeByCode(entityMap);
	}

	@Override
	public MedRefCharge queryMedRefChargeByUniqueness(Map<String, Object> entityMap) throws DataAccessException {
		return medRefChargeMapper.queryMedRefChargeByUniqueness(entityMap);
	}

}
