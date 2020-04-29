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
import com.chd.hrp.acc.dao.AccFinaContentMapper;
import com.chd.hrp.acc.entity.AccFinaContent;
import com.chd.hrp.acc.entity.AccItialIncome;
import com.chd.hrp.acc.service.AccFinaContentService;
import com.github.pagehelper.PageInfo;
@Service("accFinaContentService")
public class AccFinaContentServiceImpl implements AccFinaContentService {

	private static Logger logger = Logger.getLogger(AccFinaContentServiceImpl.class);
	
	@Resource(name = "accFinaContentMapper")
	private final AccFinaContentMapper accFinaContentMapper = null;
	
	@Override
	public String queryAccFinaContent(Map<String, Object> entityMap)
			throws DataAccessException {

		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<Map<String, Object>> list = accFinaContentMapper.queryAccFinaContent(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<Map<String, Object>> list = accFinaContentMapper.queryAccFinaContent(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
		
	}

	@Override
	public String addAccFinaContent(Map<String, Object> entityMap)
			throws DataAccessException {
			
		AccFinaContent accFinaContent = accFinaContentMapper.queryAccFinaContentByCode(entityMap);

		if (accFinaContent != null) {
			
			return "{\"error\":\"编码：" + accFinaContent.getContent_code().toString() + "重复.\"}";
		
		}
		
		try {
			accFinaContentMapper.addAccFinaContent(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {
			
			logger.error(e.getMessage(), e);
			
			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码 addAccFinaContent\"}";
		}
	}

	@Override
	public String addBatchAccFinaContent(List<Map<String, Object>> entityMap)
			throws DataAccessException {
		
		try {
			
			accFinaContentMapper.addBatchAccFinaContent(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";
		}
		catch (Exception e) {
			
			logger.error(e.getMessage(), e);
			
			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码 addBatchAccFinaContent\"}";
		}
	}

	@Override
	public String deleteAccFinaContent(Map<String, Object> entityMap)
			throws DataAccessException {
		
		try {

			accFinaContentMapper.deleteAccFinaContent(entityMap);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

			}
			catch (Exception e) {
		
				logger.error(e.getMessage(), e);
		
				return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码  deleteAccFinaContent\"}";
		
			}
	}

	@Override
	public String deleteBatchAccFinaContent(List<Map<String, Object>> entityList)
			throws DataAccessException {
		
		try {
				String error ="";
				for(Map<String,Object> item : entityList){
					int count = accFinaContentMapper.queryIsUsed(item);
					
					if(count>0){
						error += item.get("content_code").toString() +",";
						
					}
				}
				if(error !=""){
					return "{\"error\":\"[财政补助内容编码:" + error.substring(0, error.length()-1) + "]正在被引用,不能删除.\"}"; 
				}else{
					accFinaContentMapper.deleteBatchAccFinaContent(entityList);
					
					return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
				}
	
			}catch (Exception e) {
		
				logger.error(e.getMessage(), e);
		
				return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码  deleteBatchAccFinaContent\"}";
		
			}
	}

	@Override
	public String updateAccFinaContent(Map<String, Object> entityMap)
			throws DataAccessException {

		try {

			accFinaContentMapper.updateAccFinaContent(entityMap);
			
			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";

			}
			catch (Exception e) {
		
				logger.error(e.getMessage(), e);
		
				return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updateAccFinaContent\"}";
		
			}
	}

	@Override
	public String updateBatchAccFinaContent(List<Map<String, Object>> entityMap)
			throws DataAccessException {
		
		try {

			accFinaContentMapper.updateBatchAccFinaContent(entityMap);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

			}
			catch (Exception e) {
		
				logger.error(e.getMessage(), e);
		
				return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码  updateBatchAccFinaContent\"}";
		
			}
	}

	@Override
	public AccFinaContent queryAccFinaContentByCode(Map<String, Object> entityMap)
			throws DataAccessException {
		
		return accFinaContentMapper.queryAccFinaContentByCode(entityMap);
	}

	@Override
	public List<Map<String, Object>> queryAccFinaContentPrint(
			Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		List<Map<String, Object>> list = accFinaContentMapper.queryAccFinaContent(entityMap);
		
		return list;
		
	}

}
