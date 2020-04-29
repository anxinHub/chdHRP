
/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 package com.chd.hrp.med.serviceImpl.info.basic;

import java.util.*;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.chd.base.SysPage;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.base.util.SpreadTableJSUtil;
import com.chd.base.util.StringTool;
import com.chd.hrp.med.dao.info.basic.MedPayTermMapper;
import com.chd.hrp.med.entity.MedPayTerm;
import com.chd.hrp.med.entity.MedStockType;
import com.chd.hrp.med.service.info.basic.MedPayTermService;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Description:
 * MED_PAY_TERM
 * @Table:
 * MED_PAY_TERM
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 


@Service("medPayTermService")
public class MedPayTermServiceImpl implements MedPayTermService {

	private static Logger logger = Logger.getLogger(MedPayTermServiceImpl.class);
	//引入DAO操作
	@Resource(name = "medPayTermMapper")
	private final MedPayTermMapper medPayTermMapper = null;
    
	/**
	 * @Description 
	 * 添加MED_PAY_TERM<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String add(Map<String,Object> entityMap)throws DataAccessException{
		
		//获取对象MED_PAY_TERM
		MedPayTerm medPayTerm = queryByCode(entityMap);

		if (medPayTerm != null) {

			return "{\"error\":\"数据重复,请重新添加.\"}";

		}
		
		try {
			
			int state = medPayTermMapper.add(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addMedPayTerm\"}";

		}
		
	}
	/**
	 * @Description 
	 * 批量添加MED_PAY_TERM<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String addBatch(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
			medPayTermMapper.addBatch(entityList);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addBatchMedPayTerm\"}";

		}
		
	}
	
		/**
	 * @Description 
	 * 更新MED_PAY_TERM<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String update(Map<String,Object> entityMap)throws DataAccessException{
		
		try {
			
		  int state = medPayTermMapper.update(entityMap);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 updateMedPayTerm\"}";

		}	
		
	}
	/**
	 * @Description 
	 * 批量更新MED_PAY_TERM<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String updateBatch(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
		  medPayTermMapper.updateBatch(entityList);
			
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 updateBatchMedPayTerm\"}";

		}	
		
	}
	/**
	 * @Description 
	 * 删除MED_PAY_TERM<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
    public String delete(Map<String, Object> entityMap) throws DataAccessException {
    	
    try {
			
			int state = medPayTermMapper.delete(entityMap);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 deleteMedPayTerm\"}";

		}	
		
  }
    
	/**
	 * @Description 
	 * 批量删除MED_PAY_TERM<BR> 
	 * @param  entityList
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String deleteBatch(List<Map<String,Object>> entityList)throws DataAccessException{
		
		try {
			
			medPayTermMapper.deleteBatch(entityList);
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 deleteBatchMedPayTerm\"}";

		}	
	}
	
	/**
	 * @Description 
	 * 查询结果集MED_PAY_TERM<BR> 
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String query(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<MedPayTerm> list = (List<MedPayTerm>) medPayTermMapper.query(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<MedPayTerm> list = (List<MedPayTerm>) medPayTermMapper.query(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
		
	}
	
	/**
	 * @Description 
	 * 获取对象MED_PAY_TERM<BR> 
	 * @param  entityMap<BR>
	 *  参数为主键
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public <T>T queryByCode(Map<String,Object> entityMap)throws DataAccessException{
		
		return medPayTermMapper.queryByCode(entityMap);
		
	}
	
	/**
	 * @Description 
	 * 获取MED_PAY_TERM<BR> 
	 * @param  entityMap<BR>
	 *  参数为要检索的字段
	 * @return MedPayTerm
	 * @throws DataAccessException
	*/
	
	@Override
	public <T>T queryByUniqueness(Map<String,Object> entityMap)throws DataAccessException{
		return medPayTermMapper.queryByUniqueness(entityMap);
	}
	@Override
	public String addOrUpdate(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public List<?> queryExists(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public String importMedPayTerm(Map<String, Object> entityMap) throws DataAccessException {
		
		try {
			
			//1.判断表头是否为空
			String columns=entityMap.get("columns").toString();
			JSONArray jsonColumns = JSONObject.parseArray(columns);	
			if(jsonColumns==null || jsonColumns.size()==0){
				return "{\"error\":\"表头为空！\",\"state\":\"false\"}";
			}
			
			//2.判断数据是否为空
			String content=entityMap.get("content").toString();
			List<Map<String,List<String>>> liData=SpreadTableJSUtil.toListMap(content,1);
			if(liData==null || liData.size()==0){
				return "{\"error\":\"没有数据！\",\"state\":\"false\"}";
			}
			
			List<MedPayTerm> peyTermList = (List<MedPayTerm>)medPayTermMapper.query(entityMap);//查询采购类型
			Map<String,Object> peyTermQueryMap = new HashMap<String,Object>();
			for(MedPayTerm mpt : peyTermList){
				peyTermQueryMap.put(mpt.getPay_term_code(),mpt.getPay_term_code());
			}
			
			// 用于存储传的数据值,判断数据是否重复
			Map<String, String> exitMap = new HashMap<String, String>();
			// 用于记录重复数据
			StringBuffer err_sb = new StringBuffer();
			//用于存储添加数据
			List<Map<String,Object>> addList = new ArrayList<Map<String,Object>>();
			
			// 遍历导入数据
			for (Map<String, List<String>> item : liData) {
				
				List<String> pay_term_code = item.get("付款条件编码");
				List<String> pay_term_name = item.get("付款条件名称");
				List<String> is_stop = item.get("是否停用");
				
				if (pay_term_code.get(1) == null && pay_term_name.get(1) == null) {//去除空行
					continue;
				}
				
				if (pay_term_code.get(1) == null) {
					return "{\"warn\":\"付款条件编码为空！\",\"state\":\"false\",\"row_cell\":\"" + pay_term_code.get(0) + "\"}";
				}else{
					if(peyTermQueryMap.get(pay_term_code.get(1)) != null){
						return "{\"warn\":\"付款条件编码已经存在！\",\"state\":\"false\",\"row_cell\":\"" + pay_term_code.get(0) + "\"}";
					}
				}
				
				if (pay_term_name.get(1) == null) {
					return "{\"warn\":\"付款条件名称为空！\",\"state\":\"false\",\"row_cell\":\"" + pay_term_name.get(0) + "\"}";
				}
				
				if (is_stop.get(1) == null) {
					return "{\"warn\":\"是否停用为空！\",\"state\":\"false\",\"row_cell\":\"" + is_stop.get(0) + "\"}";
				}
				
				// 以付款条件编码 + 付款条件名称 为键值,判断导入数据是否重复
				String key = pay_term_code.get(1) + pay_term_name.get(1);
				if (exitMap.get(key) != null) {
					err_sb.append(pay_term_code.get(1) + "付款条件编码," + pay_term_name.get(1) + "付款条件名称");
				} else {
					exitMap.put(key, key);
				}
				
				
				Map<String,Object> addMap = new HashMap<String,Object>();
				addMap.put("group_id", entityMap.get("group_id"));
				addMap.put("hos_id", entityMap.get("hos_id"));
				addMap.put("copy_code", entityMap.get("copy_code"));
				addMap.put("pay_term_code", pay_term_code.get(1));
				addMap.put("pay_term_name", pay_term_name.get(1));
				
				String stop_code = is_stop.get(1);
				if("是".equals(stop_code) || "1".equals(stop_code)){
					addMap.put("is_stop", 1);
				}else if("否".equals(stop_code) || "0".equals(stop_code)){
					addMap.put("is_stop",0);
				}else{
					return "{\"warn\":\"是否停用值错误！\",\"state\":\"false\",\"row_cell\":\"" + is_stop.get(0) + "\"}";
				}
				
				
				addMap.put("wbx_code", StringTool.toWuBi(pay_term_name.get(1)));
				addMap.put("spell_code",StringTool.toPinyinShouZiMu(pay_term_name.get(1)));
				addMap.put("note","");
				addMap.put("sort_code","");
				
				addList.add(addMap);
			}
			
			if (err_sb.length() > 0) {// 重复数据是否存在
				return "{\"warn\":\"以下数据【" + err_sb.toString() + "】重复！\",\"state\":\"false\"}";
			}
			
			
			medPayTermMapper.addBatch(addList);
			
			return "{\"msg\":\"导入成功.\",\"state\":\"true\"}";
			
		} catch (Exception e) {
			// TODO: handle exception
			
			logger.error(e.getMessage(), e);

			throw new SysException("{\"error\":\"操作失败 \"}");
		}
	}
	
}
