package com.chd.hrp.sys.serviceImpl.baseData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import com.alibaba.fastjson.JSON;
import com.chd.base.SessionManager;
import com.chd.base.SysPage;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.hrp.acc.dao.commonbuilder.AccSubjTypeMapper;
import com.chd.hrp.acc.entity.AccCheckType;
import com.chd.hrp.acc.entity.AccCur;
import com.chd.hrp.acc.entity.AccDeptAttr;
import com.chd.hrp.acc.entity.AccSubj;
import com.chd.hrp.acc.entity.AccSubjNature;
import com.chd.hrp.acc.entity.AccSubjType;
import com.chd.hrp.acc.entity.AccVouchType;
import com.chd.hrp.sys.dao.baseData.SysAccSubjMapper;
import com.chd.hrp.sys.entity.Rules;
import com.chd.hrp.sys.service.baseData.SysAccSubjService;
import com.github.pagehelper.PageInfo;

@Service("sysAccSubjService")
public class SysAccSubjServiceImpl implements SysAccSubjService {

	private static Logger logger=Logger.getLogger(SysAccSubjServiceImpl.class);
	@Resource(name="sysAccSubjMapper")
	private final SysAccSubjMapper sysAccSubjMapper=null;
	@Resource(name = "accSubjTypeMapper")
	private final AccSubjTypeMapper accSubjTypeMapper = null;
	/**
	 * 查询会计科目
	 * @param mapVo
	 * @return
	 */
	@Override
	public String queryAccSubj(Map<String, Object> mapVo) {
		SysPage sysPage = new SysPage();
		sysPage = (SysPage) mapVo.get("sysPage");
		if(sysPage==null || sysPage.getTotal() == -1){
			List<Map<String, Object>> list = sysAccSubjMapper.queryAccSubj(mapVo);
			return ChdJson.toJson(list);
		}else{
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			List<Map<String, Object>> list = sysAccSubjMapper.queryAccSubj(mapVo, rowBounds);
			PageInfo page = new PageInfo(list);
			return ChdJson.toJson(list,page.getTotal());
		}
	}
	/**
	 * @Description 会计科目<BR>
	 *              批量删除AccSubj
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	@Transactional(rollbackFor=Exception.class)
	public String deleteBatchAccSubj(List<Map<String, Object>> entityList) throws DataAccessException {

		try {
			
			String subjIdStr="";//删除科目，判断业务使用
			String subjIdIsLast="";//删除科目，判断是否末级
			String reStr="";
			String superCode="";
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("dict_code", "ACC_SUBJ");
			map.put("group_id", entityList.get(0).get("group_id"));
			map.put("hos_id", entityList.get(0).get("hos_id"));
			map.put("copy_code", entityList.get(0).get("copy_code"));
			map.put("acc_year", entityList.get(0).get("acc_year"));
			map.put("p_flag", "1");//包括子科目
			if(entityList!=null && entityList.size()>0){
				for(Map<String, Object> mapVo : entityList){
					
					if(superCode.indexOf(mapVo.get("super_code").toString())==-1){
						superCode+="'"+mapVo.get("super_code").toString()+"',";
					}
					subjIdStr+=mapVo.get("subj_id")+",";
					subjIdIsLast+=mapVo.get("subj_id")+",";
					
					if(subjIdStr.length()>2900){
						map.put("dict_id_str", subjIdStr.substring(0, subjIdStr.length()-1));
						//删除科目时，判断业务表是否使用
						sysAccSubjMapper.querySysDictDelCheck(map);
						subjIdStr="";
						if(map.get("reNote")!=null)reStr+=map.get("reNote");
					}
					
				}
				
				if(!subjIdStr.equals("")){
					map.put("dict_id_str", subjIdStr.substring(0, subjIdStr.length()-1));
					//删除科目时，判断业务表是否使用
					sysAccSubjMapper.querySysDictDelCheck(map);
					subjIdStr="";
					System.out.println(map.get("reNote"));
					if(map.get("reNote")!=null)reStr+=map.get("reNote");
				}
			}
			
			if(reStr!=null && !reStr.equals("")){
				return "{\"error\":\"删除失败，选择的科目被以下业务使用：【"+reStr.substring(0,reStr.length()-1)+"】。\",\"state\":\"false\"}";
			}
			//批量删除
			int state = sysAccSubjMapper.deleteBatchAccSubj(entityList);
			if(state>0){
				//根据父科目查询是否有子科目，没有就修改为末级
				map.put("super_code", superCode.substring(0,superCode.length()-1));
				sysAccSubjMapper.updateSubjIsLastBySuperSubjCode(map);
			}
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码  deleteBatchAccSubj\"}";

		}

	}
	/**
	 * 查询科目编码规则
	 * @param mapVo
	 * @return
	 */
	@Override
	public Rules queryRulesByCode(Map<String, Object> mapVo) {
		return sysAccSubjMapper.queryRulesByCode(mapVo);
	}
	
	/**
	 * @Description 会计科目<BR>
	 *              添加AccSubj
	 * @param AccSubj
	 *            entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	@Transactional(rollbackFor=Exception.class)
	public String addAccSubj(Map<String, Object> entityMap) throws DataAccessException {

		WebApplicationContext ctx = ContextLoader.getCurrentWebApplicationContext();
		
		DataSourceTransactionManager transactionManager = (DataSourceTransactionManager) ctx.getBean("transactionManager");
	    
		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
		// 事物隔离级别，开启新事务，这样会比较安全些。
		def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW); 
	    
		TransactionStatus status = transactionManager.getTransaction(def); // 获得事务状态
		
		String result_state="";
		
		try {
			System.out.println(entityMap);
			Map<String,Object> utilMap=new HashMap<String,Object>();
			utilMap.put("group_id", entityMap.get("group_id"));
			utilMap.put("hos_id", entityMap.get("hos_id"));
			utilMap.put("copy_code", entityMap.get("copy_code"));
			utilMap.put("acc_year", entityMap.get("acc_year"));
			utilMap.put("field_table","ACC_SUBJ");
			
			if(!entityMap.get("super_code").toString().equalsIgnoreCase("top")){
				utilMap.put("field_key1", "subj_code");
				utilMap.put("field_value1", entityMap.get("super_code"));		
				int count = sysAccSubjMapper.existsSysCodeNameByAdd(utilMap);
				if (count ==0) {
					return "{\"error\":\"上级编码：[" + entityMap.get("super_code").toString() + "]不存在.\"}";
				}
			}
			
			utilMap.put("field_key1", "subj_code");
			utilMap.put("field_value1", entityMap.get("subj_code"));		
			int count = sysAccSubjMapper.existsSysCodeNameByAdd(utilMap);
			if (count >0) {
				return "{\"error\":\"科目编码：[" + entityMap.get("subj_code").toString() + "]重复.\"}";
			}
			
			
			AccSubj accSubj = queryAccSubjByCode(entityMap);
	
			if (accSubj != null) {
	
				return "{\"error\":\"编码：" + entityMap.get("item_code").toString() + "重复.\"}";
	
			}
	
			//设置科目全称
			if(entityMap.get("super_code").toString().equalsIgnoreCase("top")){

				if(entityMap.get("subj_code").toString().startsWith("5001")){
					
					entityMap.put("subj_name_all", "医疗业务成本-"+entityMap.get("subj_name"));
					
				}else if(entityMap.get("subj_code").toString().startsWith("5301")){
					
					entityMap.put("subj_name_all", "管理费用-"+entityMap.get("subj_name"));
				
				}else{
					
					entityMap.put("subj_name_all", entityMap.get("subj_name"));
						
				}
		    }else{
		    	
		    	if(entityMap.get("subj_code").toString().startsWith("5001")){
					
					entityMap.put("subj_name_all", "医疗业务成本-"+sysAccSubjMapper.querySubjBySuperCode(entityMap)+"-"+entityMap.get("subj_name"));
					
				}else if(entityMap.get("subj_code").toString().startsWith("5301")){
					
					entityMap.put("subj_name_all", "管理费用-"+sysAccSubjMapper.querySubjBySuperCode(entityMap)+"-"+entityMap.get("subj_name"));
				
				}else{
					
		    	entityMap.put("subj_name_all", sysAccSubjMapper.querySubjBySuperCode(entityMap)+"-"+entityMap.get("subj_name"));
		    
				}
		    }

			int state=sysAccSubjMapper.addAccSubj(entityMap);
			
			//调用生成拼音码、五笔码和科目全称存储过程
			sysAccSubjMapper.prcUpdateSubjInfoSigle(entityMap);
			//返回0，表示更新生成五笔码，拼音码成功；返回-1 表示生成失败
			result_state=entityMap.get("prm_AppCode").toString();
				
			if(state>0){
				//更新父级编码是否末级=0
				entityMap.put("is_last",0);
				sysAccSubjMapper.updateSubjIsLastBySubjCode(entityMap);
			}
			
			if(null!=SessionManager.getAccParaMap()&&null!=SessionManager.getAccParaMap().get("031")&&"1".equals(SessionManager.getAccParaMap().get("031").toString())){
				
				if(entityMap.get("subj_code").toString().startsWith("5001")){
					
					entityMap.put("is_last",1);
					
					entityMap.put("super_code", entityMap.get("super_code").toString().replace("5001", "5301"));
					
					entityMap.put("subj_code", entityMap.get("subj_code").toString().replace("5001", "5301"));
					
					if(!entityMap.get("super_code").toString().equalsIgnoreCase("top")){
						
						entityMap.put("subj_name_all", sysAccSubjMapper.querySubjBySuperCode(entityMap)+"-"+entityMap.get("subj_name"));
						
					}else{
						
						entityMap.put("subj_name_all", entityMap.get("subj_name"));
						
					}
					
					
					 state=sysAccSubjMapper.addAccSubj(entityMap);
					 
					//调用生成拼音码、五笔码和科目全称存储过程
						sysAccSubjMapper.prcUpdateSubjInfoSigle(entityMap);
						
						result_state=entityMap.get("prm_AppCode").toString();
					
					if(state>0){
						//更新父级编码是否末级=0
						entityMap.put("is_last",0);
						
						sysAccSubjMapper.updateSubjIsLastBySubjCode(entityMap);
					}
					
				}else if(entityMap.get("subj_code").toString().startsWith("5301")){
					
						entityMap.put("is_last",1);
					
						entityMap.put("super_code", entityMap.get("super_code").toString().replace("5301", "5001"));
					
						entityMap.put("subj_code", entityMap.get("subj_code").toString().replace("5301", "5001"));
						
						if(!entityMap.get("super_code").toString().equalsIgnoreCase("top")){
							
							entityMap.put("subj_name_all", /*"医疗业务成本-"+*/sysAccSubjMapper.querySubjBySuperCode(entityMap)+"-"+entityMap.get("subj_name"));
							
						}else{
							
							entityMap.put("subj_name_all", /*"医疗业务成本-"+*/entityMap.get("subj_name"));
							
						}
						
						 state=sysAccSubjMapper.addAccSubj(entityMap);
						 
						 result_state=entityMap.get("prm_AppCode").toString();
						
						if(state>0){
							//更新父级编码是否末级=0
							entityMap.put("is_last",0);
							sysAccSubjMapper.updateSubjIsLastBySubjCode(entityMap);
						}
						
					}
				
			}
			
			//返回0，表示更新生成五笔码，拼音码成功；返回-1 表示生成失败
			if("0".equals(result_state)){
				
				//不存在重复数据，提交事务
				transactionManager.commit(status);
				
			}else{
				
				transactionManager.rollback(status);
				
				return "{\"msg\":\""+entityMap.get("prm_ErrTxt").toString()+".\",\"state\":\"false\"}";
				
			}
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}

	}
	
	/**
	 * @Description 会计科目<BR>
	 *              查询AccSubjByCode
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public AccSubj queryAccSubjByCode(Map<String, Object> entityMap) throws DataAccessException {

		return sysAccSubjMapper.queryAccSubjByCode(entityMap);

	}
	/**
	 * 判断科目是否使用
	 * @param mapVo
	 * @return
	 */
	@Override
	public String existsSubjCheck(Map<String, Object> mapVo) {
		mapVo.put("p_flag", 1);//包括子科目
		sysAccSubjMapper.querySysDictDelCheck(mapVo);
		String reStr="";
		if(mapVo.get("reNote")!=null)reStr=mapVo.get("reNote").toString();
		return reStr;
	}
	/**
	 * 判断辅助核算是否使用
	 */
	@Override
	public int existsVouchLederBySubjCheck(Map<String, Object> mapVo) {
		return sysAccSubjMapper.existsVouchLederBySubjCheck(mapVo);
	}
	@Override
	public String queryCheckTypeBySubjId(Map<String, Object> entityMap) throws DataAccessException {
		
		if(entityMap.get("acc_year") == null){
			
			entityMap.put("acc_year", SessionManager.getAcctYear());
	        
		}
		
		return JSON.toJSONString(sysAccSubjMapper.queryCheckTypeBySubjId(entityMap));
	}
	@Override
	public AccCheckType queryCheckType(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		return sysAccSubjMapper.queryCheckType(entityMap);
	}
	@Override
	public AccCheckType queryCheckColumn(Map<String, Object> entityMap)
			throws DataAccessException {
		return sysAccSubjMapper.queryCheckColumn(entityMap);
	}
	/**
	 * @Description 会计科目<BR>
	 *              更新AccSubj
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	@Transactional(rollbackFor=Exception.class)
	public String updateAccSubj(Map<String, Object> entityMap) throws DataAccessException {

		WebApplicationContext ctx = ContextLoader.getCurrentWebApplicationContext();
		
		DataSourceTransactionManager transactionManager = (DataSourceTransactionManager) ctx.getBean("transactionManager");
	    
		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
		// 事物隔离级别，开启新事务，这样会比较安全些。
		def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW); 
	    
		TransactionStatus status = transactionManager.getTransaction(def); // 获得事务状态
		
		String result_state="";
		
		try {
			
			Map<String,Object> utilMap=new HashMap<String,Object>();
			utilMap.put("group_id", entityMap.get("group_id"));
			utilMap.put("hos_id", entityMap.get("hos_id"));
			utilMap.put("copy_code", entityMap.get("copy_code"));
			utilMap.put("acc_year", entityMap.get("acc_year"));
			utilMap.put("field_table","ACC_SUBJ");
			utilMap.put("field_id", "subj_id");
			utilMap.put("field_id_value", entityMap.get("subj_id"));
			
			if(!entityMap.get("super_code_new").toString().equalsIgnoreCase("top")){
				utilMap.put("field_key1", "subj_code");
				utilMap.put("field_value1", entityMap.get("super_code_new"));		
				int count = sysAccSubjMapper.existsSysCodeNameByUpdate(utilMap);
				if (count ==0) {
					return "{\"error\":\"上级编码：[" + entityMap.get("super_code_new").toString() + "]不存在.\"}";
				}
			}
			
			
			utilMap.put("field_key1", "subj_code");
			utilMap.put("field_value1", entityMap.get("subj_code"));		
			int count = sysAccSubjMapper.existsSysCodeNameByUpdate(utilMap);
			if (count >0) {
				return "{\"error\":\"科目编码：[" + entityMap.get("subj_code").toString() + "]重复.\"}";
			}
			
			/*utilMap.put("dict_code", "ACC_SUBJ");			
			utilMap.put("p_flag", "0");//不包含子科目，根据科目编码查询
			utilMap.put("dict_id_str", entityMap.get("super_code_new"));
			/*sysFunUtilMapper.querySysDictDelCheck(utilMap);
			if(utilMap.get("reNote")!=null && !utilMap.get("reNote").equals("")){
				return "{\"error\":\"上级编码：[" + entityMap.get("super_code_new").toString() + "]，被以下业务使用：【"+utilMap.get("reNote").toString().substring(0,utilMap.get("reNote").toString().length()-1)+"】.\"}";
			}*/

			//设置科目全称
			if(entityMap.get("super_code").toString().equalsIgnoreCase("top")){
				entityMap.put("subj_name_all", entityMap.get("subj_name"));
		    }else{
		    	entityMap.put("subj_name_all", sysAccSubjMapper.querySubjBySuperCode(entityMap)+"-"+entityMap.get("subj_name"));
		    }
			
			int state=sysAccSubjMapper.updateAccSubj(entityMap);
			//修改编码操作时
			if(state>0 && !entityMap.get("super_code").toString().equalsIgnoreCase(entityMap.get("super_code_new").toString())){
				//修改操作，根据父科目查询是否有子科目，老的父科目没有就修改为末级
				//entityMap.put("super_code", "'"+entityMap.get("super_code").toString()+"'");
				//accSubjMapper.updateSubjIsLastBySuperSubjCode(entityMap);
				
				//修改操作，根据父科目查询是否有子科目，新的父科目没有就修改为非末级
				entityMap.put("super_code", entityMap.get("super_code_new"));
				entityMap.put("is_last",0);
				sysAccSubjMapper.updateSubjIsLastBySubjCode(entityMap);
			}
			//调用生成拼音码、五笔码和科目全称存储过程
			//accSubjMapper.prcUpdateSubjInfoSigle(entityMap);
			 sysAccSubjMapper.prcUpdateSubjInfo(entityMap);
			//返回0，表示更新生成五笔码，拼音码成功；返回-1 表示生成失败
			 result_state=entityMap.get("prm_AppCode").toString();
			
			//String para=SessionManager.getAccParaMap().get("031").toString();
			
			if(null!=SessionManager.getAccParaMap()&&null!=SessionManager.getAccParaMap().get("031")&&"1".equals(SessionManager.getAccParaMap().get("031").toString())){
				
				if(entityMap.get("subj_code").toString().startsWith("5001")){
					
					entityMap.put("super_code", entityMap.get("super_code").toString().replace("5001", "5301"));
					
					entityMap.put("subj_code", entityMap.get("subj_code").toString().replace("5001", "5301"));
					
					if(!entityMap.get("super_code").toString().equalsIgnoreCase("top")){
						
						entityMap.put("subj_name_all", sysAccSubjMapper.querySubjBySuperCode(entityMap)+"-"+entityMap.get("subj_name"));
						
					}else{
						
						entityMap.put("subj_name_all", entityMap.get("subj_name"));
						
					}
					
					AccSubj accSubj = sysAccSubjMapper.queryAccSubjByCode(entityMap);
					
					entityMap.put("subj_id", accSubj.getSubj_id());
					
					 state=sysAccSubjMapper.updateAccSubj(entityMap);
					//修改编码操作时
					if(state>0 && !entityMap.get("super_code").toString().equalsIgnoreCase(entityMap.get("super_code_new").toString())){
						//修改操作，根据父科目查询是否有子科目，老的父科目没有就修改为末级
						//entityMap.put("super_code", "'"+entityMap.get("super_code").toString()+"'");
						//accSubjMapper.updateSubjIsLastBySuperSubjCode(entityMap);
						
						//修改操作，根据父科目查询是否有子科目，新的父科目没有就修改为非末级
						//entityMap.put("super_code", entityMap.get("super_code_new"));
						entityMap.put("is_last",0);
						sysAccSubjMapper.updateSubjIsLastBySubjCode(entityMap);
					}
					//调用生成拼音码、五笔码和科目全称存储过程
					
					sysAccSubjMapper.prcUpdateSubjInfo(entityMap);
						
					result_state=entityMap.get("prm_AppCode").toString();
					
				}else if(entityMap.get("subj_code").toString().startsWith("5301")){
					
						//entityMap.put("is_last",1);
					
						entityMap.put("super_code", entityMap.get("super_code").toString().replace("5301", "5001"));
					
						entityMap.put("subj_code", entityMap.get("subj_code").toString().replace("5301", "5001"));
						
						if(!entityMap.get("super_code").toString().equalsIgnoreCase("top")){
							
							entityMap.put("subj_name_all", /*"医疗业务成本-"+*/sysAccSubjMapper.querySubjBySuperCode(entityMap)+"-"+entityMap.get("subj_name"));
							
						}else{
							
							entityMap.put("subj_name_all", /*"医疗业务成本-"+*/entityMap.get("subj_name"));
							
						}
						
						AccSubj accSubj = sysAccSubjMapper.queryAccSubjByCode(entityMap);
						
						entityMap.put("subj_id", accSubj.getSubj_id());
						
						state=sysAccSubjMapper.updateAccSubj(entityMap);
						
						//修改编码操作时
						if(state>0 && !entityMap.get("super_code").toString().equalsIgnoreCase(entityMap.get("super_code_new").toString())){

							//修改操作，根据父科目查询是否有子科目，新的父科目没有就修改为非末级
							//entityMap.put("super_code", entityMap.get("super_code_new"));
							entityMap.put("is_last",0);
							
							sysAccSubjMapper.updateSubjIsLastBySubjCode(entityMap);
						}
						//调用生成拼音码、五笔码和科目全称存储过程
						
						sysAccSubjMapper.prcUpdateSubjInfo(entityMap);
							
						result_state=entityMap.get("prm_AppCode").toString();
						
					}
				
			}
			
			if("0".equals(entityMap.get("prm_AppCode").toString())){
				
				//不存在重复数据，提交事务
				transactionManager.commit(status);
				
			}else{
				
				transactionManager.rollback(status);
				
				return "{\"msg\":\""+entityMap.get("prm_ErrTxt").toString()+".\",\"state\":\"false\"}";
				
			}

			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updateAccSubj\"}";

		}
	}
	/**
	 * 打印
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	@Override
	public List<Map<String, Object>> queryAccSubjPrint(Map<String, Object> mapVo) throws DataAccessException {
		if(mapVo.get("group_id") == null) {mapVo.put("group_id", SessionManager.getGroupId());}
		if(mapVo.get("hos_id") == null) {mapVo.put("hos_id", SessionManager.getHosId());}
		if(mapVo.get("copy_code") == null) {mapVo.put("copy_code", "0");}
		if(mapVo.get("acc_year") == null) {mapVo.put("acc_year", SessionManager.getAcctYear());}
		List<Map<String, Object>> list = sysAccSubjMapper.queryAccSubjPrint(mapVo);
		
		return list;
	}
	/**
	 * @Description 会计科目<BR>
	 * 导入时查询会计科目编码是否存在
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public AccSubj queryAccSubjCode(Map<String, Object> entityMap) throws DataAccessException {

		return sysAccSubjMapper.queryAccSubjCode(entityMap);

	}
	/**
	 * @Description 
	 * 币种<BR> 查询AccCurByCode
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public AccCur queryAccCurByCode(Map<String,Object> entityMap)throws DataAccessException{
		return sysAccSubjMapper.queryAccCurByCode(entityMap);
	}
	/**
	 * @Description 
	 * 科目类别<BR> 查询AccSubjTypeByCode
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public AccSubjType queryAccSubjTypeByCode(Map<String,Object> entityMap)throws DataAccessException{
		
		return sysAccSubjMapper.queryAccSubjTypeByCode(entityMap);
		
	}
	
	
	/**
	 * @Description 
	 * 科目性质<BR> 查询AccSubjNatureByCode
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public AccSubjNature queryAccSubjNatureByCode(Map<String,Object> entityMap)throws DataAccessException{
		
		return sysAccSubjMapper.queryAccSubjNatureByCode(entityMap);
		
	}
	/**
	 * @Description 
	 * 凭证类型<BR> 查询AccVouchTypeByCode
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public AccVouchType queryAccVouchTypeByCode(Map<String,Object> entityMap)throws DataAccessException{
		
		return sysAccSubjMapper.queryAccVouchTypeByCode(entityMap);
		
	}
	
	@Override
	public AccDeptAttr queryAccOutDeptByName(Map<String, Object> entityMap) throws DataAccessException {

		return sysAccSubjMapper.queryAccOutDeptByName(entityMap);
	}
	
	/**
	 * @Description 
	 * 核算类<BR> 查询AccCheckTypeByCode
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public AccCheckType queryAccCheckTypeByName(Map<String,Object> entityMap)throws DataAccessException{
		
		return sysAccSubjMapper.queryAccCheckTypeByName(entityMap);
		
	}
	
	/**
	 * @Description 会计科目<BR>
	 *              导入AccSubj
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public List<AccSubj> accSubjImport(List<Map<String,Object>> entityMap) throws DataAccessException {

		WebApplicationContext ctx = ContextLoader.getCurrentWebApplicationContext();
		
		DataSourceTransactionManager transactionManager = (DataSourceTransactionManager) ctx.getBean("transactionManager");
	    
		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
		// 事物隔离级别，开启新事务，这样会比较安全些。
		def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW); 
	    
		TransactionStatus status = transactionManager.getTransaction(def); // 获得事务状态
		
		List<AccSubj> list = new ArrayList<AccSubj>();
		
		try {
			//添加导入数据
			sysAccSubjMapper.addBatchAccSubj(entityMap);
			//修改父级科目是否末级
			updateBatchAccSubj(entityMap);
			//查询导入数据中科目编码重复的科目
			list =sysAccSubjMapper.queryDistinctAccSubjList(entityMap.get(0));
			
			if(list.size()>0){
				//存在重复数据（科目编码重复）,回滚事务，并将重复数据返回前台
				transactionManager.rollback(status);
				
				return list;
			}
			
			Map<String,Object> map = new HashMap<String, Object>();
			
			map.put("group_id", entityMap.get(0).get("group_id"));
			
			map.put("hos_id", entityMap.get(0).get("hos_id"));
			
			map.put("copy_code", entityMap.get(0).get("copy_code"));
			
			map.put("acc_year", entityMap.get(0).get("acc_year"));
			
			//生成导入科目全程、拼音码以及五笔码
			sysAccSubjMapper.prcUpdateSubjInfoALL(map);
			//返回0，表示更新生成五笔码，拼音码成功；返回-1或者100 表示生成失败
			if("0".equals(map.get("prm_AppCode").toString())){
				
				//不存在重复数据，提交事务
				transactionManager.commit(status);
				
			}else{
				
				AccSubj accsubj = new AccSubj();
				
				accsubj.setError_type(map.get("prm_ErrTxt").toString());
				
				list.add(accsubj);
				
				transactionManager.rollback(status);
				
			}
			
			return list;

		} catch (Exception e) {

			logger.error(e.getMessage(), e);
			
			AccSubj asubj = new AccSubj();
			
			asubj.setError_type("导入失败！");
			
			list.add(asubj);

			return list;

		}
	}
	

	/**
	 * @Description 会计科目<BR>
	 *              批量更新AccSubj
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String updateBatchAccSubj(List<Map<String, Object>> entityList) throws DataAccessException {

		try {

			sysAccSubjMapper.updateBatchAccSubj(entityList);

			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updateAccSubj\"}";

		}

	}
	@Override
	public AccSubj queryAccSubj_id(Map<String, Object> mapVo) {
		return sysAccSubjMapper.queryAccSubj_id(mapVo);
	}
	
	//继承会计科目
		@Override
		public String addBatchAccSubjExtend(Map<String, Object> mapVo) throws DataAccessException {
			try {
				mapVo.put("field_table", "ACC_SUBJ");
				int count = sysAccSubjMapper.existsSysCodeNameByAdd(mapVo);
				if(count>0){
					return "{\"error\":\"本年度已经有数据了，无法继承.\",\"state\":\"false\"}";
				}
				int state=0;
				
				if(mapVo.get("extened_code").toString().equals("3")){
					//按行业性质导会计科目
					state= sysAccSubjMapper.addBatchAccSubjByNatureCode(mapVo);
				}else{
					//按医院历史年度、按集团导会计科目
					state= sysAccSubjMapper.addBatchAccSubjByAccYear(mapVo);
				}
				
				if(state>0){
					return "{\"msg\":\"继承成功.\",\"state\":\"true\"}";
				}else{
					return "{\"error\":\"没有数据，无法继承.\",\"state\":\"false\"}";
				}

			} catch (Exception e) {

				logger.error(e.getMessage(), e);

				return "{\"error\":\"继承失败 数据库异常 请联系管理员! 错误编码 addBatchAccSubjByNatureCode\"}";

			}
		
		}
		/**
		 *  账簿中的科目选择器
		 */
	@Override
	public String queryGroupSubjBySelector(Map<String, Object> entityMap)throws DataAccessException {
			
			StringBuilder jsonResult = new StringBuilder();
			
			jsonResult.append("{Rows:[");
			
			List<AccSubjType> subjTypeList = accSubjTypeMapper.queryGroupAccSubjType(entityMap);
			
			if (subjTypeList.size()>0) {
				
				int rows = 0;
				
				for(AccSubjType accSubjType : subjTypeList){
					
					if(rows== 0){
						
						jsonResult.append("{'id':'"+accSubjType.getSubj_type_code()+"','pId':'0','name':'"+accSubjType.getSubj_type_name()+"','open':false");
						
						
					}else{
					
						jsonResult.append(",{'id':'"+accSubjType.getSubj_type_code()+"','pId':'0','name':'"+accSubjType.getSubj_type_name()+"','open':false");
						
					
					}
					
					entityMap.put("subj_type_code", accSubjType.getSubj_type_code());
					
					entityMap.put("super_code", "top");
					
					rows++;
					
					if(rows== subjTypeList.size()){
					
						jsonResult.append("}");
						
					}else{
					
						jsonResult.append("},");
					
					}
					
					entityMap.put("jsonResult", jsonResult);
					
					jsonResult.append(subjRecursion(entityMap));
				}
			}
			jsonResult.append("]}");
			
			return jsonResult.toString().replace(",,", ",");
		}

	public String subjRecursion(Map<String, Object> entityMap){
		
		int rows = 0;
		
		StringBuilder jsonResult = new StringBuilder();
		
		List<AccSubj> subjDictList = sysAccSubjMapper.queryGroupAccSubjByMenu(entityMap);
			
		for (AccSubj subjDict : subjDictList) {
			
			if(entityMap.get("jsonResult").toString().endsWith(",")){
				jsonResult.append("{");
			}else{
				jsonResult.append(",{");
			}
			
			//用来判断 该科目选择器是否是辅助账的选择器 && Integer.parseInt(entityMap.get("sign").toString()) == 0 
			if(entityMap.get("sign") != null ){
				
				if(Integer.parseInt(entityMap.get("sign").toString()) == 0){
					
					//用来判断选择完级次后，当科目为末级后自动展示到分类下面
					if (entityMap.get("subj_level") != ""){
						
						jsonResult.append("'id':'" + subjDict.getSubj_code() + "',");
						
						jsonResult.append("'subj_id':'" + subjDict.getSubj_id() + "',");
						
						jsonResult.append("'name':'"+subjDict.getSubj_code() + " " +subjDict.getSubj_name()+ "',");
						
						jsonResult.append("'subj_dire':'" + subjDict.getSubj_dire() + "',");
						
						jsonResult.append("'pId':'"+ entityMap.get("subj_type_code")+ "'");
						
					}else {
					
						if(subjDict.getIs_last() == 1 ){ 
							
							jsonResult.append("'id':'" + subjDict.getSubj_code() + "',");
							
							jsonResult.append("'subj_id':'" + subjDict.getSubj_id() + "',");
							
							jsonResult.append("'name':'"+subjDict.getSubj_code() + " " +subjDict.getSubj_name()+ "',");
							
							jsonResult.append("'subj_dire':'" + subjDict.getSubj_dire() + "',");
							
							if(!"top".equals(subjDict.getSuper_code())){
								
								jsonResult.append("'pId':'"+ subjDict.getSuper_code()+ "'");
								
							}else{
								
								jsonResult.append("'pId':'"+ entityMap.get("subj_type_code")+ "'");
							}
							
						}else{
							
							jsonResult.append("'id':'" + subjDict.getSubj_code() + "',");
							
							jsonResult.append("'subj_id':'" + subjDict.getSubj_id() + "',");
							
							jsonResult.append("'name':'"+subjDict.getSubj_code() + " " +subjDict.getSubj_name()+ "',");
							
							jsonResult.append("'subj_dire':'" + subjDict.getSubj_dire() + "',");
							
							if(!"top".equals(subjDict.getSuper_code())){
								
								jsonResult.append("'pId':'"+ subjDict.getSuper_code()+ "'");
								
							}else{
								
								jsonResult.append("'pId':'"+ entityMap.get("subj_type_code")+ "'");
							}
							
							entityMap.put("super_code", subjDict.getSubj_code()); 
							 
						}
					
					}
					
				}else {
					
				
					//用来判断选择完级次后，当科目为末级后自动展示到分类下面
					if (entityMap.get("subj_level") != ""){
						
						jsonResult.append("'id':'" + subjDict.getSubj_code() + "',");
						
						jsonResult.append("'subj_id':'" + subjDict.getSubj_id() + "',");
						
						jsonResult.append("'name':'"+subjDict.getSubj_code() + " " +subjDict.getSubj_name()+ "',");
						
						jsonResult.append("'subj_dire':'" + subjDict.getSubj_dire() + "',");
						
						jsonResult.append("'pId':'"+ entityMap.get("subj_type_code")+ "'");
						
					}else {
						
						if(subjDict.getIs_last() == 1 ){ 
							
							jsonResult.append("'id':'" + subjDict.getSubj_code() + "',");
							
							jsonResult.append("'subj_id':'" + subjDict.getSubj_id() + "',");
							
							jsonResult.append("'name':'"+subjDict.getSubj_code() + " " +subjDict.getSubj_name()+ "',");
							
							jsonResult.append("'subj_dire':'" + subjDict.getSubj_dire() + "',");
							
							//判断如果getJudge_subj_code 是否存在数据，不存在直接放在分类下面
							if(subjDict.getJudge_subj_code() == null || "".equals(subjDict.getJudge_subj_code())){
								 
								 jsonResult.append("'pId':'"+ entityMap.get("subj_type_code")+ "'");
								 
							}else{ 
								
								if(!"top".equals(subjDict.getSuper_code())){
								
									jsonResult.append("'pId':'"+ subjDict.getSuper_code()+ "'");
								
								}else{
									
									jsonResult.append("'pId':'"+ entityMap.get("subj_type_code")+ "'");
								}
								
							 }
							 
							entityMap.put("super_code", subjDict.getSubj_code()); 
							 
						}else{
							
							jsonResult.append("'id':'" + subjDict.getSubj_code() + "',");
							
							jsonResult.append("'subj_id':'" + subjDict.getSubj_id() + "',");
							
							jsonResult.append("'name':'"+subjDict.getSubj_code() + " " +subjDict.getSubj_name()+ "',");
							
							jsonResult.append("'subj_dire':'" + subjDict.getSubj_dire() + "',");
								
						    if(subjDict.getJudge_subj_code() == null || "".equals(subjDict.getJudge_subj_code())){
									 
						    	jsonResult.append("'pId':'"+ entityMap.get("subj_type_code")+ "'");
									 
							}else{ 
									
								if(!"top".equals(subjDict.getSuper_code())){
									
									jsonResult.append("'pId':'"+ subjDict.getSuper_code()+ "'");
								
								}else{
									
									jsonResult.append("'pId':'"+ entityMap.get("subj_type_code")+ "'");
								}
									
						    }
								
							entityMap.put("super_code", subjDict.getSubj_code()); 
							
						}
					
					}
					
				}
				
			} else{
				 
				//用来判断选择完级次后，当科目为末级后自动展示到分类下面
				if (entityMap.get("subj_level") != ""){
					
					jsonResult.append("'id':'" + subjDict.getSubj_code() + "',");
					
					jsonResult.append("'subj_id':'" + subjDict.getSubj_id() + "',");
					
					jsonResult.append("'name':'"+subjDict.getSubj_code() + " " +subjDict.getSubj_name()+ "',");
					
					jsonResult.append("'subj_dire':'" + subjDict.getSubj_dire() + "',");
					
					jsonResult.append("'pId':'"+ entityMap.get("subj_type_code")+ "'");
					
				}else {
				
					if(subjDict.getIs_last() == 1 ){ 
						
						jsonResult.append("'id':'" + subjDict.getSubj_code() + "',");
						
						jsonResult.append("'subj_id':'" + subjDict.getSubj_id() + "',");
						
						jsonResult.append("'name':'"+subjDict.getSubj_code() + " " +subjDict.getSubj_name()+ "',");
						
						jsonResult.append("'subj_dire':'" + subjDict.getSubj_dire() + "',");
						
						if(!"top".equals(subjDict.getSuper_code())){
							
							jsonResult.append("'pId':'"+ subjDict.getSuper_code()+ "'");
							
						}else{
							
							jsonResult.append("'pId':'"+ entityMap.get("subj_type_code")+ "'");
						}
						
					}else{
						
						jsonResult.append("'id':'" + subjDict.getSubj_code() + "',");
						
						jsonResult.append("'subj_id':'" + subjDict.getSubj_id() + "',");
						
						jsonResult.append("'name':'"+subjDict.getSubj_code() + " " +subjDict.getSubj_name()+ "',");
						
						jsonResult.append("'subj_dire':'" + subjDict.getSubj_dire() + "',");
						
						if(!"top".equals(subjDict.getSuper_code())){
							
							jsonResult.append("'pId':'"+ subjDict.getSuper_code()+ "'");
							
						}else{
							
							jsonResult.append("'pId':'"+ entityMap.get("subj_type_code")+ "'");
						}
						
						entityMap.put("super_code", subjDict.getSubj_code()); 
						 
					}
				
				}
			
			}
			
			rows++;
			if(rows == subjDictList.size()){
				jsonResult.append("}");
			}else{
				jsonResult.append("},");
			}
		}
		return jsonResult.toString();
	}

}
