package com.chd.hrp.acc.serviceImpl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.SysPage;
import com.chd.base.util.ChdJson;
import com.chd.hrp.acc.dao.AccSubjContentMapper;
import com.chd.hrp.acc.entity.AccSubjContent;
import com.chd.hrp.acc.service.AccSubjContentService;
import com.github.pagehelper.PageInfo;
@Service("accSubjContentService")
public class AccSubjContentServiceImpl implements AccSubjContentService {

	private static Logger logger = Logger.getLogger(AccSubjContentServiceImpl.class);
	
	@Resource(name = "accSubjContentMapper")
	private final AccSubjContentMapper accSubjContentMapper = null;
	
	@Override
	public String queryAccSubjContent(Map<String, Object> entityMap)
			throws DataAccessException {

		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<Map<String, Object>> list = accSubjContentMapper.queryAccSubjContent(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<Map<String, Object>> list = accSubjContentMapper.queryAccSubjContent(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
		
	}

	@Override
	public String addAccSubjContent(Map<String, Object> entityMap)
			throws DataAccessException {
			
		AccSubjContent accSubjContent = accSubjContentMapper.queryAccSubjContentByCode(entityMap);

		if (accSubjContent != null) {
			
			//return "{\"error\":\"编码：" + accSubjContent.getContent_code().toString() + "重复.\"}";
			accSubjContentMapper.updateAccSubjContent(entityMap);
			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";
		}
		
		try {
			accSubjContentMapper.addAccSubjContent(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {
			
			logger.error(e.getMessage(), e);
			
			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码 addAccSubjContent\"}";
		}
	}

	@Override
	public String addBatchAccSubjContent(List<Map<String, Object>> entityMap)
			throws DataAccessException {
		
		try {
			
			accSubjContentMapper.addBatchAccSubjContent(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";
		}
		catch (Exception e) {
			
			logger.error(e.getMessage(), e);
			
			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码 addBatchAccSubjContent\"}";
		}
	}

	@Override
	public String deleteAccSubjContent(Map<String, Object> entityMap)
			throws DataAccessException {
		
		try {

			accSubjContentMapper.deleteAccSubjContent(entityMap);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

			}
			catch (Exception e) {
		
				logger.error(e.getMessage(), e);
		
				return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码  deleteAccSubjContent\"}";
		
			}
	}

	@Override
	public String deleteBatchAccSubjContent(List<Map<String, Object>> entityMap)
			throws DataAccessException {
		
		try {

			accSubjContentMapper.deleteBatchAccSubjContent(entityMap);
			
			return "{\"msgs\":\"删除成功.\",\"state\":\"true\"}";

			}
			catch (Exception e) {
		
				logger.error(e.getMessage(), e);
		
				return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码  deleteBatchAccSubjContent\"}";
		
			}
	}

	@Override
	public String updateAccSubjContent(Map<String, Object> entityMap)
			throws DataAccessException {

		try {

			accSubjContentMapper.updateAccSubjContent(entityMap);
			
			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";

			}
			catch (Exception e) {
		
				logger.error(e.getMessage(), e);
		
				return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updateAccSubjContent\"}";
		
			}
	}

	@Override
	public String updateBatchAccSubjContent(List<Map<String, Object>> entityMap)
			throws DataAccessException {
		
		try {

			accSubjContentMapper.updateBatchAccSubjContent(entityMap);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

			}
			catch (Exception e) {
		
				logger.error(e.getMessage(), e);
		
				return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码  updateBatchAccSubjContent\"}";
		
			}
	}

	@Override
	public AccSubjContent queryAccSubjContentByCode(Map<String, Object> entityMap)
			throws DataAccessException {
		
		return accSubjContentMapper.queryAccSubjContentByCode(entityMap);
	}

	@Override
	public List<Map<String, Object>> queryAccSubjContentPrint(
			Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		List<Map<String, Object>> list = accSubjContentMapper.queryAccSubjContent(entityMap);
		
		return list;
	}

}
