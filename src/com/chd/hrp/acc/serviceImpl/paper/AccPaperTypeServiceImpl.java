package com.chd.hrp.acc.serviceImpl.paper;
/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/

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
import com.chd.hrp.acc.dao.paper.AccPaperTypeMapper;
import com.chd.hrp.acc.service.paper.AccPaperTypeService;
import com.chd.hrp.sys.dao.notGeneral.SysFunUtilMapper;
import com.github.pagehelper.PageInfo;
 
 

@Service("accPaperTypeService")
public class AccPaperTypeServiceImpl implements AccPaperTypeService{

	private static Logger logger = Logger.getLogger(AccPaperTypeServiceImpl.class);
	
	@Resource(name = "accPaperTypeMapper")
	private final AccPaperTypeMapper accPaperTypeMapper = null;
	
	@Resource(name = "sysFunUtilMapper")
	private final SysFunUtilMapper sysFunUtilMapper = null;

	@Override
	public String addAccPaperType(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
	 try {
		 
		  Map<String, Object> existMap =  accPaperTypeMapper.queryAccPaperTypeByCode(entityMap);
		  
		  if(existMap != null){
			  
			  return "{\"error\":\"类型编码或名称重复！\",\"state\":\"false\"}";
		  }
		 
		 /*
			 *自动生成排序号 
			 * */
			Map<String, Object> utilMap = new HashMap<String, Object>();
			
			utilMap.put("group_id", entityMap.get("group_id"));
			
			utilMap.put("hos_id", entityMap.get("hos_id"));
			
			utilMap.put("copy_code",entityMap.get("copy_code"));
			
			utilMap.put("field_table","acc_paper_type");
			
			utilMap.put("field_sort", "sort_code");
			
			int count = sysFunUtilMapper.querySysMaxSort(utilMap);
			
			entityMap.put("sort_code", count);
		 
		    accPaperTypeMapper.addAccPaperType(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码 addAccPaperType\"}";

		}
	}

	@Override
	public String addBatchAccPaperType(List<Map<String, Object>> list)
			throws DataAccessException {
		// TODO Auto-generated method stub
		 try {
				
			    accPaperTypeMapper.addBatchAccPaperType(list);
				
				return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

			}
			catch (Exception e) {

				logger.error(e.getMessage(), e);

				return "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码 addBatchAccPaperType\"}";

			}
	}

	@Override
	public String updateAccPaperType(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		 try {
				
			    accPaperTypeMapper.updateAccPaperType(entityMap);
				
				return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";

			}
			catch (Exception e) {

				logger.error(e.getMessage(), e);

				return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码 updateAccPaperType\"}";

			}
	}

	@Override
	public String deleteBatchAccPaperType(List<Map<String, Object>> list)
			throws DataAccessException {
		// TODO Auto-generated method stub
		 try {
				
			    accPaperTypeMapper.deleteBatchAccPaperType(list);
				
				return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

			}
			catch (Exception e) {

				logger.error(e.getMessage(), e);

				return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码 updateAccPaperType\"}";

			}
	}

	@Override
	public String queryAccPaperType(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
         SysPage sysPage = new SysPage();
		 
		 sysPage = (SysPage) entityMap.get("sysPage");
		 
		 
		 if (sysPage.getTotal()==-1){
				
				List<Map<String, Object>> list= accPaperTypeMapper.queryAccPaperType(entityMap);
				
				return ChdJson.toJson(list);
				
			
			}else{
				
				RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
				
				List<Map<String, Object>> list= accPaperTypeMapper.queryAccPaperType(entityMap,rowBounds);
				
		        PageInfo page = new PageInfo(list); 
				
				return ChdJson.toJson(list, page.getTotal());
				
			}

	}

	@Override
	public Map<String, Object> queryAccPaperTypeByCode(
			Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		return accPaperTypeMapper.queryAccPaperTypeByCode(entityMap);
	}

	@Override
	public List<Map<String, Object>> queryAccPaperTypePrint(
			Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		List<Map<String, Object>> list= accPaperTypeMapper.queryAccPaperType(entityMap);
		
		return list;
	}

	@Override
	public List<Map<String, Object>> queryAccPaperTypeSearch(
			Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		return  accPaperTypeMapper.queryAccPaperType(entityMap);
	}

	
	
}
