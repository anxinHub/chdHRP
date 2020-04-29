/**
 * @Copyright: Copyright (c) 2015-2-14 
 * @Company: 智慧云康（北京）数据科技有限公司
 */

package com.chd.hrp.acc.serviceImpl.commonbuilder;
 
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
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import com.chd.base.MyConfig;
import com.chd.base.SessionManager;
import com.chd.base.SysPage;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.base.util.SpreadTableJSUtil;
import com.chd.base.util.StringTool;
import com.chd.hrp.acc.dao.AccLederCheckMapper;
import com.chd.hrp.acc.dao.AccLederMapper;
import com.chd.hrp.acc.dao.HrpAccSelectMapper;
import com.chd.hrp.acc.dao.commonbuilder.AccSubjMapper;
import com.chd.hrp.acc.dao.commonbuilder.AccSubjTypeMapper;
import com.chd.hrp.acc.entity.AccSubj;
import com.chd.hrp.acc.entity.AccSubjType;
import com.chd.hrp.acc.service.commonbuilder.AccSubjService;
import com.chd.hrp.sys.dao.notGeneral.SysFunUtilMapper;
import com.github.pagehelper.PageInfo;
 
/** 
 * @Title. @Description. 会计科目<BR>
 * @Author: LiuYingDuo
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Service("accSubjService")
public class AccSubjServiceImpl implements AccSubjService {

	private static Logger logger = Logger.getLogger(AccSubjServiceImpl.class);

	@Resource(name = "accSubjMapper")
	private final AccSubjMapper accSubjMapper = null;
	
	@Resource(name = "accSubjTypeMapper")
	private final AccSubjTypeMapper accSubjTypeMapper = null;

	@Resource(name = "hrpAccSelectMapper")
	private final HrpAccSelectMapper hrpAccSelectMapper = null;
	
	@Resource(name = "sysFunUtilMapper")
	private final SysFunUtilMapper sysFunUtilMapper = null;
	RowBounds rowBoundsALL = new RowBounds(0, 20);
	
	@Resource(name = "accLederMapper")
	private final AccLederMapper accLederMapper = null;
	
	@Resource(name = "accLederCheckMapper")
	private final AccLederCheckMapper accLederCheckMapper = null;
	
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
			
			Map<String,Object> utilMap=new HashMap<String,Object>();
			
			utilMap.put("group_id", entityMap.get("group_id"));
			
			utilMap.put("hos_id", entityMap.get("hos_id"));
			
			utilMap.put("copy_code", entityMap.get("copy_code"));
			
			utilMap.put("acc_year", entityMap.get("acc_year"));
			
			utilMap.put("field_table","ACC_SUBJ");
			
			if(!entityMap.get("super_code").toString().equalsIgnoreCase("top")){
				
				utilMap.put("field_key1", "subj_code");
				
				utilMap.put("field_value1", entityMap.get("super_code"));	
				
				int count = sysFunUtilMapper.existsSysCodeNameByAdd(utilMap);
				
				if (count ==0) {
					
					return "{\"error\":\"上级编码：[" + entityMap.get("super_code").toString() + "]不存在.\"}";
				
				}
				
				int num = sysFunUtilMapper.existsSuperCodeByVouch(entityMap);
				
				//不查询账表  因为结账后没有发生业务也可以增加下级科目
				//int lederNum = sysFunUtilMapper.existsSuperCodeByLeder(entityMap);
				
				if (num >0) {
					//if (num >0||lederNum>0) {
					
					return "{\"error\":\"上级编码：[" + entityMap.get("super_code").toString() + "]发生业务数据！无法添加子级科目.\"}";
				
				}
				
			}
			
			utilMap.put("field_key1", "subj_code");
			utilMap.put("field_value1", entityMap.get("subj_code"));		
			int count = sysFunUtilMapper.existsSysCodeNameByAdd(utilMap);
			if (count >0) {
				return "{\"error\":\"科目编码：[" + entityMap.get("subj_code").toString() + "]重复.\"}";
			}
			
			if(entityMap.get("subj_name").toString().indexOf("-")>-1){
				
				return "{\"error\":\"科目名称不能带有【-】.\"}";
				
			}
			AccSubj accSubj = queryAccSubjByCode(entityMap);
	
			if (accSubj != null) {
	
				return "{\"error\":\"编码：" + entityMap.get("item_code").toString() + "重复.\"}";
	
			}
	
			//设置科目全称
			if(entityMap.get("super_code").toString().equalsIgnoreCase("top")){

				if(entityMap.get("subj_code").toString().startsWith("5001")){
					
					entityMap.put("subj_name_all", "医疗业务成本-"+entityMap.get("subj_name"));
					
				}else if(entityMap.get("subj_code").toString().startsWith("5101")){
					
					entityMap.put("subj_name_all", "管理费用-"+entityMap.get("subj_name"));
				
				}else{
					
					entityMap.put("subj_name_all", entityMap.get("subj_name"));
						
				}
		    }else{
		    	
		    	if(entityMap.get("subj_code").toString().startsWith("5001")){
					
					entityMap.put("subj_name_all", "医疗业务成本-"+accSubjMapper.querySubjBySuperCode(entityMap)+"-"+entityMap.get("subj_name"));
					
				}else if(entityMap.get("subj_code").toString().startsWith("5101")){
					
					entityMap.put("subj_name_all", "管理费用-"+accSubjMapper.querySubjBySuperCode(entityMap)+"-"+entityMap.get("subj_name"));
				
				}else{
					
		    	entityMap.put("subj_name_all", accSubjMapper.querySubjBySuperCode(entityMap)+"-"+entityMap.get("subj_name"));
		    
				}
		    }

			int state=accSubjMapper.addAccSubj(entityMap);
			
			//调用生成拼音码、五笔码和科目全称存储过程
			accSubjMapper.prcUpdateSubjInfoSigle(entityMap);
			//返回0，表示更新生成五笔码，拼音码成功；返回-1 表示生成失败
			result_state=entityMap.get("prm_AppCode").toString();
				
			if(state>0){
				//更新父级编码是否末级=0
				entityMap.put("is_last",0);
				accSubjMapper.updateSubjIsLastBySubjCode(entityMap);
			} 
			if(null != MyConfig.getSysPara("031") && "1".equals(MyConfig.getSysPara("031").toString())){
				
				if(entityMap.get("subj_code").toString().startsWith("5001")){
					
					entityMap.put("is_last",1);
					
					entityMap.put("super_code", entityMap.get("super_code").toString().replace("5001", "5101"));
					
					entityMap.put("subj_code", entityMap.get("subj_code").toString().replace("5001", "5101"));
					
					if(!entityMap.get("super_code").toString().equalsIgnoreCase("top")){
						
						entityMap.put("subj_name_all", accSubjMapper.querySubjBySuperCode(entityMap)+"-"+entityMap.get("subj_name"));
						
					}else{
						
						entityMap.put("subj_name_all", entityMap.get("subj_name"));
						
					}
					
					
					 state=accSubjMapper.addAccSubj(entityMap);
					 
					//调用生成拼音码、五笔码和科目全称存储过程
						accSubjMapper.prcUpdateSubjInfoSigle(entityMap);
						
						result_state=entityMap.get("prm_AppCode").toString();
					
					if(state>0){
						//更新父级编码是否末级=0
						entityMap.put("is_last",0);
						
						accSubjMapper.updateSubjIsLastBySubjCode(entityMap);
					}
					
				}else if(entityMap.get("subj_code").toString().startsWith("5101")){
					
						entityMap.put("is_last",1);
					
						entityMap.put("super_code", entityMap.get("super_code").toString().replace("5101", "5001"));
					
						entityMap.put("subj_code", entityMap.get("subj_code").toString().replace("5101", "5001"));
						
						if(!entityMap.get("super_code").toString().equalsIgnoreCase("top")){
							
							entityMap.put("subj_name_all", /*"医疗业务成本-"+*/accSubjMapper.querySubjBySuperCode(entityMap)+"-"+entityMap.get("subj_name"));
							
						}else{
							
							entityMap.put("subj_name_all", /*"医疗业务成本-"+*/entityMap.get("subj_name"));
							
						}
						
						 state=accSubjMapper.addAccSubj(entityMap);
						 
						 result_state=entityMap.get("prm_AppCode").toString();
						
						if(state>0){
							//更新父级编码是否末级=0
							entityMap.put("is_last",0);
							accSubjMapper.updateSubjIsLastBySubjCode(entityMap);
						}
						
					}
				
			}
			 
			//查询当前新增加的科目是否是有子级科目   gaopei 
			int countLast  = accSubjMapper.queryAccSubjByCount(entityMap);
			//判断是否有子级科目
			if(countLast == 1 ){
				
				Map<String,Object> countlastMap  = accSubjMapper.queryAccSubjBySuperId(entityMap);
				
				//组装 通过上级编码查询上级科目的subj_id
				Map<String,Object> lederCodeMap=new HashMap<String,Object>();
				lederCodeMap.put("group_id", entityMap.get("group_id"));
				lederCodeMap.put("hos_id", entityMap.get("hos_id"));
				lederCodeMap.put("copy_code", entityMap.get("copy_code"));
				lederCodeMap.put("acc_year", entityMap.get("acc_year")); 
				lederCodeMap.put("super_subj_code", countlastMap.get("SUBJ_CODE"));
				
				
				//判断该添加的科目是否有挂辅助核算
				if("0".equals(entityMap.get("is_check"))){
					
					//组装 查询父级科目在账表中数据   所用的条件 
					Map<String,Object> lederAddMap=new HashMap<String,Object>();
					lederAddMap.put("group_id", entityMap.get("group_id"));
					lederAddMap.put("hos_id", entityMap.get("hos_id"));
					lederAddMap.put("copy_code", entityMap.get("copy_code"));
					lederAddMap.put("acc_year", entityMap.get("acc_year"));
					lederAddMap.put("subj_code", entityMap.get("subj_code"));
					lederAddMap.put("super_subj_code", countlastMap.get("SUBJ_CODE"));
					
					//账表中增加新添加的科目
					accLederMapper.addAccLederByCopy(lederAddMap);
					
					//删除之前的末级科目，因为账表中只存在末级科目，如果新增加科目则之前的科目就为非末级
					accLederMapper.deleteAccLederByCopy(lederCodeMap);
					
				}else {
					//组装 查询父级科目在账表中数据   所用的条件 
					Map<String,Object> lederAddMap=new HashMap<String,Object>();
					lederAddMap.put("group_id", entityMap.get("group_id"));
					lederAddMap.put("hos_id", entityMap.get("hos_id"));
					lederAddMap.put("copy_code", entityMap.get("copy_code"));
					lederAddMap.put("acc_year", entityMap.get("acc_year"));
					lederAddMap.put("subj_code", entityMap.get("subj_code"));
					lederAddMap.put("super_subj_code", countlastMap.get("SUBJ_CODE"));
					
					accLederMapper.addAccLederByCopy(lederAddMap); 
					accLederMapper.deleteAccLederByCopy(lederCodeMap);
					//账表中增加新添加的科目
					accLederCheckMapper.addAccLederCheckByCopy(lederAddMap);
					
					//删除之前的末级科目，因为账表中只存在末级科目，如果新增加科目则之前的科目就为非末级
					accLederCheckMapper.deleteAccLederCheckByCopy(lederCodeMap);
					
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
	 *              查询AccSubj分页
	 * @param entityMap
	 *            RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String queryAccSubj(Map<String, Object> entityMap) throws DataAccessException {

		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");
		
		if(sysPage.getTotal() == -1){
			
			List<AccSubj> list = accSubjMapper.queryAccSubj(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<AccSubj> list = accSubjMapper.queryAccSubj(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list,page.getTotal());
		}

		/*List<HrpAccSelect> accCheckType = hrpAccSelectMapper.queryCheckType(entityMap, new RowBounds(1, 999));

		Map<String, Object> mapCheck = new HashMap<String, Object>();

		for (HrpAccSelect hrpAccSelect : accCheckType) {

			mapCheck.put(hrpAccSelect.getId(), hrpAccSelect.getText());

		}

		List<AccSubj> arrayList = new ArrayList<AccSubj>();

		for (AccSubj accSubj : list) {

			if (!"".equals(accSubj.getCheck1()) && accSubj.getCheck1() != null) {

				accSubj.setCheck1_show_name(mapCheck.get(String.valueOf(accSubj.getCheck1())).toString());

			}
			if (!"".equals(accSubj.getCheck2()) && accSubj.getCheck2() != null) {

				accSubj.setCheck2_show_name(mapCheck.get(String.valueOf(accSubj.getCheck2())).toString());

			}
			if (!"".equals(accSubj.getCheck3()) && accSubj.getCheck3() != null) {

				accSubj.setCheck3_show_name(mapCheck.get(String.valueOf(accSubj.getCheck3())).toString());

			}
			if (!"".equals(accSubj.getCheck4()) && accSubj.getCheck4() != null) {

				accSubj.setCheck4_show_name(mapCheck.get(String.valueOf(accSubj.getCheck4())).toString());

			}

			arrayList.add(accSubj);

		}
		 */
		// 新增PageInfo对象，对返回结果进行封装

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

		return accSubjMapper.queryAccSubjByCode(entityMap);

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
			//String newSuperCode="";
			//List<Map<String, Object>> subjList = new ArrayList<Map<String,Object>>();
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
						//if(LoadSystemInfo.getHrpMap().get("hrpDatatype").toString().equalsIgnoreCase("oracle")){
						sysFunUtilMapper.querySysDictDelCheck(map);
						subjIdStr="";
						//}
						if(map.get("reNote")!=null)reStr+=map.get("reNote");
					}
				}
				
				if(!subjIdStr.equals("")){
					map.put("dict_id_str", subjIdStr.substring(0, subjIdStr.length()-1));
					//删除科目时，判断业务表是否使用
					//if(LoadSystemInfo.getHrpMap().get("hrpDatatype").toString().equalsIgnoreCase("oracle")){
					sysFunUtilMapper.querySysDictDelCheck(map);
					//}
					subjIdStr="";
					if(map.get("reNote")!=null)reStr+=map.get("reNote");
				}
			}
			
			if(reStr!=null && !reStr.equals("")){
				return "{\"error\":\"删除失败，选择的科目被以下业务使用：【"+reStr.substring(0,reStr.length()-1)+"】。\",\"state\":\"false\"}";
			}
			
			map.put("subjIdIsLast", subjIdIsLast.substring(0,subjIdIsLast.length()-1));
			List<String>  isLastList=accSubjMapper.existsSubjByIsLast(map);
			if(isLastList!=null && isLastList.size()>0){
				return "{\"error\":\"删除失败，以下科目不是末级：【"+isLastList.toString()+"】。\",\"state\":\"false\"}";
			}
			
			
			//批量删除
			int state = accSubjMapper.deleteBatchAccSubj(entityList);
			if(state>0){
				//根据父科目查询是否有子科目，没有就修改为末级
				map.put("super_code", superCode.substring(0,superCode.length()-1));
				accSubjMapper.updateSubjIsLastBySuperSubjCode(map);
			}
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码  deleteBatchAccSubj\"}";

		}

	}

	/**
	 * @Description 会计科目<BR>
	 *              删除AccSubj
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String deleteAccSubj(Map<String, Object> entityMap) throws DataAccessException {

		try {
			accSubjMapper.deleteAccSubj(entityMap);
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码  deleteAccSubj\"}";

		}
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
				int count = sysFunUtilMapper.existsSysCodeNameByUpdate(utilMap);
				if (count ==0) {
					return "{\"error\":\"上级编码：[" + entityMap.get("super_code_new").toString() + "]不存在.\"}";
				}
			}
			
			
			utilMap.put("field_key1", "subj_code");
			utilMap.put("field_value1", entityMap.get("subj_code"));		
			int count = sysFunUtilMapper.existsSysCodeNameByUpdate(utilMap);
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
		    	entityMap.put("subj_name_all", accSubjMapper.querySubjBySuperCode(entityMap)+"-"+entityMap.get("subj_name"));
		    }
			
			int state=accSubjMapper.updateAccSubj(entityMap);
			//修改编码操作时
			if(state>0 && !entityMap.get("super_code").toString().equalsIgnoreCase(entityMap.get("super_code_new").toString())){
				//修改操作，根据父科目查询是否有子科目，老的父科目没有就修改为末级
				//entityMap.put("super_code", "'"+entityMap.get("super_code").toString()+"'");
				//accSubjMapper.updateSubjIsLastBySuperSubjCode(entityMap);
				
				//修改操作，根据父科目查询是否有子科目，新的父科目没有就修改为非末级
				entityMap.put("super_code", entityMap.get("super_code_new"));
				entityMap.put("is_last",0);
				accSubjMapper.updateSubjIsLastBySubjCode(entityMap);
			}
			//调用生成拼音码、五笔码和科目全称存储过程
			//accSubjMapper.prcUpdateSubjInfoSigle(entityMap);
			 accSubjMapper.prcUpdateSubjInfo(entityMap);
			//返回0，表示更新生成五笔码，拼音码成功；返回-1 表示生成失败
			 result_state=entityMap.get("prm_AppCode").toString();
			
			if(null!=MyConfig.getSysPara("031")&&"1".equals(MyConfig.getSysPara("031").toString())){
				
				if(entityMap.get("subj_code").toString().startsWith("5001")){
					
					/*entityMap.put("super_code", entityMap.get("super_code").toString().replace("5001", "5301"));
					
					entityMap.put("subj_code", entityMap.get("subj_code").toString().replace("5001", "5301"));
					*/
					if(!entityMap.get("super_code").toString().equalsIgnoreCase("top")){
						
						entityMap.put("subj_name_all", accSubjMapper.querySubjBySuperCode(entityMap)+"-"+entityMap.get("subj_name"));
						
					}else{
						
						entityMap.put("subj_name_all", entityMap.get("subj_name"));
						
					}
					
					AccSubj accSubj = accSubjMapper.queryAccSubjByCode(entityMap);
					
					entityMap.put("subj_id", accSubj.getSubj_id());
					
					 state=accSubjMapper.updateAccSubj(entityMap);
					//修改编码操作时
					if(state>0 && !entityMap.get("super_code").toString().equalsIgnoreCase(entityMap.get("super_code_new").toString())){
						//修改操作，根据父科目查询是否有子科目，老的父科目没有就修改为末级
						//entityMap.put("super_code", "'"+entityMap.get("super_code").toString()+"'");
						//accSubjMapper.updateSubjIsLastBySuperSubjCode(entityMap);
						
						//修改操作，根据父科目查询是否有子科目，新的父科目没有就修改为非末级
						//entityMap.put("super_code", entityMap.get("super_code_new"));
						entityMap.put("is_last",0);
						accSubjMapper.updateSubjIsLastBySubjCode(entityMap);
					}
					//调用生成拼音码、五笔码和科目全称存储过程
					
					accSubjMapper.prcUpdateSubjInfo(entityMap);
						
					result_state=entityMap.get("prm_AppCode").toString();
					
				}else if(entityMap.get("subj_code").toString().startsWith("5101")){
					
						//entityMap.put("is_last",1);
					
						/*entityMap.put("super_code", entityMap.get("super_code").toString().replace("5301", "5001"));
					
						entityMap.put("subj_code", entityMap.get("subj_code").toString().replace("5301", "5001"));
						*/
						if(!entityMap.get("super_code").toString().equalsIgnoreCase("top")){
							
							entityMap.put("subj_name_all", /*"医疗业务成本-"+*/accSubjMapper.querySubjBySuperCode(entityMap)+"-"+entityMap.get("subj_name"));
							
						}else{
							
							entityMap.put("subj_name_all", /*"医疗业务成本-"+*/entityMap.get("subj_name"));
							
						}
						
						AccSubj accSubj = accSubjMapper.queryAccSubjByCode(entityMap);
						
						entityMap.put("subj_id", accSubj.getSubj_id());
						
						state=accSubjMapper.updateAccSubj(entityMap);
						
						//修改编码操作时
						if(state>0 && !entityMap.get("super_code").toString().equalsIgnoreCase(entityMap.get("super_code_new").toString())){

							//修改操作，根据父科目查询是否有子科目，新的父科目没有就修改为非末级
							//entityMap.put("super_code", entityMap.get("super_code_new"));
							entityMap.put("is_last",0);
							
							accSubjMapper.updateSubjIsLastBySubjCode(entityMap);
						}
						//调用生成拼音码、五笔码和科目全称存储过程
						
						accSubjMapper.prcUpdateSubjInfo(entityMap);
							
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
			transactionManager.rollback(status);
			return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updateAccSubj\"}";

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

			accSubjMapper.updateBatchAccSubj(entityList);

			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码  updateAccSubj\"}";

		}

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
			accSubjMapper.addBatchAccSubj(entityMap);
			//修改父级科目是否末级
			updateBatchAccSubj(entityMap);
			//查询导入数据中科目编码重复的科目
			list =accSubjMapper.queryDistinctAccSubjList(entityMap.get(0));
			
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
			accSubjMapper.prcUpdateSubjInfoALL(map);
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
			transactionManager.rollback(status);
			return list;
			//return "{\"error\":\"导入失败 数据库异常 请联系管理员! 错误编码  import{className}\"}";

		}
	}


	@Override
	public AccSubj queryAccSubj_id(Map<String, Object> mapVo) {
		return accSubjMapper.queryAccSubj_id(mapVo);
	}

	@Override
	public String queryAccSubjByMenu(Map<String, Object> entityMap)
			throws DataAccessException {
		
		StringBuilder jsonResult = new StringBuilder();
		
		jsonResult.append("{Rows:[");
		
		List<AccSubj> subjDictList = accSubjMapper.queryAccSubjByMenu(entityMap);
		
		if (subjDictList.size()>0) {
			
			jsonResult.append("{id:'01',pId:'0',name:'资产',open:true},");
			
			jsonResult.append("{id:'02',pId:'0',name:'负债',open:true},");
			
			jsonResult.append("{id:'03',pId:'0',name:'净资产',open:true},");
			
			jsonResult.append("{id:'04',pId:'0',name:'收入',open:true},");
			
			jsonResult.append("{id:'05',pId:'0',name:'费用',open:true},");
			
			int row = 0;
			
			for (AccSubj subjDict : subjDictList) {
				
				if (row == 0) {
				
					jsonResult.append("{");
					
				} else {
					
					jsonResult.append(",{");
					
				}
				row++;
				
				jsonResult.append("id:'" + subjDict.getSubj_code() + "',");
				
				if("top".equals(subjDict.getSuper_code())){
					
					jsonResult.append("pId:'"+subjDict.getSubj_type_code()+"',");
				
				}else{
					
					jsonResult.append("pId:'"+subjDict.getSuper_code()+"',");
					
				}
				
				jsonResult.append("group_id:'" + subjDict.getGroup_id() + "',");
				
				jsonResult.append("hos_id:'" + subjDict.getHos_id() + "',");
				
				jsonResult.append("copy_code:'" + subjDict.getCopy_code() + "',");
				
				jsonResult.append("name:'"+subjDict.getSubj_name()+ "',");

				jsonResult.append("}");
			
			}
		}
		
		jsonResult.append("]}");
		
		return jsonResult.toString();
		
	}

	@Override
	public String queryAccSubjByCashOrCheck(Map<String, Object> entityMap) throws DataAccessException {
		
		AccSubj  as= accSubjMapper.queryAccSubjByCashOrCheck(entityMap);
		
		if(as != null){
			
			return "{\"subj_code\":\""+as.getSubj_code()+"\",\"is_cash\":\""+as.getIs_cash()+"\",\"is_check\":\""+as.getIs_check()+"\",\"subj_id\":\""+as.getSubj_id()+"\",\"subj_nature_code\":\""+as.getSubj_nature_code()+"\",\"subj_dire\":\""+as.getSubj_dire()+"\"}";

		}else{
			
			return "{\"state\":\"1\"}";
			
		}
		
	}

	@Override
	public String queryAccSubjBySelector(Map<String, Object> entityMap)
			throws DataAccessException {
			
			StringBuilder jsonResult = new StringBuilder();
			
			jsonResult.append("{Rows:[");
			
			List<AccSubjType> subjTypeList = accSubjTypeMapper.queryAccSubjType(entityMap);
			
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
		
		List<AccSubj> subjDictList = accSubjMapper.queryAccSubjByMenu(entityMap);
			
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
							
							//jsonResult.append(subjRecursion(entityMap));
							
							//jsonResult.append("]");
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
						
						//jsonResult.append(subjRecursion(entityMap));
						
						//jsonResult.append("]");
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

	
	
	//判断辅助核算是否使用
	public int existsVouchLederBySubjCheck(Map<String, Object> mapVo) {
		return accSubjMapper.existsVouchLederBySubjCheck(mapVo);
	}
	
	//判断科目是否使用
	public String existsSubjCheck(Map<String, Object> mapVo) {
		mapVo.put("p_flag", 1);//包括子科目
		sysFunUtilMapper.querySysDictDelCheck(mapVo);
		String reStr="";
		if(mapVo.get("reNote")!=null)reStr=mapVo.get("reNote").toString();
		return reStr;
	}
	
	//继承会计科目
	@Override
	public String addBatchAccSubjExtend(Map<String, Object> mapVo) throws DataAccessException {
		try {
			mapVo.put("field_table", "ACC_SUBJ");
			int count = sysFunUtilMapper.existsSysCodeNameByAdd(mapVo);
			if(count>0){
				return "{\"error\":\"本年度已经有数据了，无法继承.\",\"state\":\"false\"}";
			}
			int state=0;
			
			if(mapVo.get("extened_code").toString().equals("3")){
				//按行业性质导会计科目
				state= accSubjMapper.addBatchAccSubjByNatureCode(mapVo);
			}else{
				//按医院历史年度、按集团导会计科目
				state= accSubjMapper.addBatchAccSubjByAccYear(mapVo);
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


	@Override
	public String addBatchAccSubj(List<Map<String,Object>> entityMap)
			throws DataAccessException {

		try {
			
			accSubjMapper.addBatchAccSubj(entityMap);
			
			updateBatchAccSubj(entityMap);
				
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";
				
		} catch (Exception e) {
			
			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码 addBatchAccSubj\"}";


		}
		
	}


	@Override
	public String querySubjBySuperCode(Map<String, Object> mapVo)
			throws DataAccessException {
		
		return accSubjMapper.querySubjBySuperCode(mapVo);
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

		return accSubjMapper.queryAccSubjCode(entityMap);

	}


	@Override
	public AccSubj querySubjByCode(Map<String, Object> entityMap)
			throws DataAccessException {
		
		return accSubjMapper.queryAccSubjByMenu(entityMap).get(0);
	}


	@Override
	public String querySubjIsCheck(Map<String, Object> entityMap)
			throws DataAccessException {
		
		 AccSubj accSubj=accSubjMapper.queryAccSubjByMenu(entityMap).get(0);
		 
		 if(null==accSubj){
			 
			 return "false@0";
			 
		 }

		 if("0".equals(accSubj.getIs_check().toString())){
			 
			 return "false@"+accSubj.getSubj_dire();
			 
		 }
		 
		 return "true@"+accSubj.getSubj_dire();
			 
	}


	@Override
	public AccSubj queryAccSubjNature(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		return accSubjMapper.queryAccSubjNature(entityMap);
	}


	@Override
	public List<Map<String, Object>> queryAccSubjPrint(
			Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		List<Map<String, Object>> list = accSubjMapper.queryAccSubjPrint(entityMap);
		
		return list;
	}


	@Override
	public String addSynGroupSubj(Map<String, Object> entityMap)throws DataAccessException {
		// TODO Auto-generated method stub
		List<Map<String, Object>> listAdd = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> listUpdate = new ArrayList<Map<String, Object>>();
		try{
			List<Map<String, Object>> liGroup = accSubjMapper.queryGroupSubjCode(entityMap);
			if(liGroup.size()==0){
				return "{\"error\":\"当前年度集团会计科目数据未生成\"}";
			}
			for (int i = 0; i < liGroup.size(); i++) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("group_id", entityMap.get("group_id"));
				map.put("hos_id", entityMap.get("hos_id"));
				map.put("copy_code", entityMap.get("copy_code"));
				map.put("acc_year", entityMap.get("acc_year"));
				map.put("subj_code", liGroup.get(i).get("SUBJ_CODE"));
				map.put("cur_code", liGroup.get(i).get("CUR_CODE"));
				map.put("subj_type_code", liGroup.get(i).get("SUBJ_TYPE_CODE"));
				map.put("subj_nature_code", liGroup.get(i).get("SUBJ_NATURE_CODE"));
				map.put("vouch_type_code", liGroup.get(i).get("VOUCH_TYPE_CODE"));
				map.put("super_code", liGroup.get(i).get("SUPER_CODE"));
				map.put("subj_name", liGroup.get(i).get("SUBJ_NAME"));
				map.put("subj_name_all", liGroup.get(i).get("SUBJ_NAME_ALL"));
				map.put("subj_name_en", liGroup.get(i).get("SUBJ_NAME_EN"));
				map.put("is_cash", liGroup.get(i).get("IS_CASH"));
				map.put("is_bill", liGroup.get(i).get("IS_BILL"));
				map.put("subj_dire", liGroup.get(i).get("SUBJ_DIRE"));
				map.put("subj_level", liGroup.get(i).get("SUBJ_LEVEL"));
				map.put("is_last", liGroup.get(i).get("IS_LAST"));
				map.put("spell_code", liGroup.get(i).get("SPELL_CODE"));
				map.put("wbx_code", liGroup.get(i).get("WBX_CODE"));
				map.put("is_stop", liGroup.get(i).get("IS_STOP"));
				map.put("is_check", liGroup.get(i).get("IS_CHECK"));
				map.put("is_remit", liGroup.get(i).get("IS_REMIT"));
				map.put("check1", liGroup.get(i).get("CHECK1"));
				map.put("check2", liGroup.get(i).get("CHECK2"));
				map.put("check3", liGroup.get(i).get("CHECK3"));
				map.put("check4", liGroup.get(i).get("CHECK4"));
				map.put("check5", liGroup.get(i).get("CHECK5"));
				map.put("check6", liGroup.get(i).get("CHECK6"));
				map.put("check7", liGroup.get(i).get("CHECK7"));
				map.put("check8", liGroup.get(i).get("CHECK8"));
				map.put("check9", liGroup.get(i).get("CHECK9"));
				map.put("check10", liGroup.get(i).get("CHECK10"));
				map.put("out_code", liGroup.get(i).get("OUT_CODE"));
				map.put("check_type_code1", liGroup.get(i).get("CHECK_TYPE_CODE1"));
				map.put("check_type_code2", liGroup.get(i).get("CHECK_TYPE_CODE2"));
				map.put("check_type_code3", liGroup.get(i).get("CHECK_TYPE_CODE3"));
				map.put("check_type_code4", liGroup.get(i).get("CHECK_TYPE_CODE4"));
				
				int count = accSubjMapper.querySynGroupSubjCode(map);
				if(count>0){
					listUpdate.add(map);
				}else{
					listAdd.add(map);
				}
			}
			
			if(listAdd.size()>0){
				accSubjMapper.addBatchAccSubj(listAdd);
				accSubjMapper.updateGroupSubj(listAdd);
			}
			if(listUpdate.size()>0){
				accSubjMapper.updateBatchGroupAccSubj(listUpdate);
			}
			
			return "{\"msg\":\"同步成功.\",\"state\":\"true\"}";
		} catch (Exception e) {

			logger.error(e.getMessage(), e);
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return "{\"error\":\"同步失败 数据库异常 请联系管理员! 错误编码 addSynGroupSubj\"}";

		}
		/*int count = accSubjMapper.querySynGroupSubjCode(entityMap);
		if(count>0){
			return "{\"error\":\"本年度已经有数据了，无法同步.\",\"state\":\"false\"}";
		}
		
		int state=accSubjMapper.addBatchAccSynGroupSubj(entityMap);
		if(state>0){
			return "{\"msg\":\"同步成功.\",\"state\":\"true\"}";
		}else{
			return "{\"error\":\"没有数据，无法同步.\",\"state\":\"false\"}";
		}*/
		
	}
	
	/**
	 * 会计科目导入
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public Map<String, Object> importAccSubjData(Map<String,Object> entityMap)throws DataAccessException{
		Map<String, Object> retMap = new HashMap<String, Object>();
		retMap.put("state", "true");
		
		List<Map<String, Object>> subjList = new ArrayList<Map<String,Object>>();
		List<Map<String, Object>> supRelaList = new ArrayList<Map<String,Object>>();
		String group_id = SessionManager.getGroupId();
		String hos_id = SessionManager.getHosId();
		String copy_code = SessionManager.getCopyCode();
		String user_id = SessionManager.getUserId();
		String acc_year = SessionManager.getAcctYear();
		
		Map<String, Object> seachMap = new HashMap<String, Object>();
		seachMap.put("group_id", group_id);
		seachMap.put("hos_id", hos_id);
		seachMap.put("copy_code", copy_code);
		seachMap.put("acc_year", acc_year);
		seachMap.put("user_id", user_id);

		try {	
			//判断会计期间是否存在
			int is_exists = accSubjMapper.existsAccYearMonth(seachMap);
			if(is_exists == 0){
				retMap.put("error", acc_year+"年期间不存在，请到系统平台添加年度！");
				retMap.put("state", "false");
				return retMap;
			}
			
			//会计科目--判断是否重复（键：科目编码or科目名称，值：科目编码）
			List<Map<String, Object>> accSubjList = accSubjMapper.queryAccSubjListForImport(seachMap);
			Map<String, Map<String, Object>> accSubjMap = new HashMap<String, Map<String, Object>>();
			for(Map<String, Object> accSubj : accSubjList){
				accSubjMap.put(accSubj.get("subj_name").toString(), accSubj);
				accSubjMap.put(accSubj.get("subj_code").toString(), accSubj);
			}
			
			//科目类别
			List<Map<String, Object>> subjTypeList = accSubjMapper.queryAccSubjTypeListForImport(seachMap);
			Map<String, String> subjTypeMap = new HashMap<String, String>();
			for(Map<String, Object> subjType : subjTypeList){
				subjTypeMap.put(subjType.get("SUBJ_TYPE_CODE").toString(), subjType.get("SUBJ_TYPE_CODE").toString() + "," + (subjType.get("KIND_CODE") == null ? "" : subjType.get("KIND_CODE").toString()));
				subjTypeMap.put(subjType.get("SUBJ_TYPE_NAME").toString(), subjType.get("SUBJ_TYPE_CODE").toString() + "," + (subjType.get("KIND_CODE") == null ? "" : subjType.get("KIND_CODE").toString()));
			}
			
			//科目性质
			List<Map<String, Object>> subjNatureList = accSubjMapper.queryAccSubjNatureListForImport(seachMap);
			Map<String, String> subjNatureMap = new HashMap<String, String>();
			for(Map<String, Object> subjNature : subjNatureList){
				subjNatureMap.put(subjNature.get("SUBJ_NATURE_CODE").toString(), subjNature.get("SUBJ_NATURE_CODE").toString());
				subjNatureMap.put(subjNature.get("SUBJ_NATURE_NAME").toString(), subjNature.get("SUBJ_NATURE_CODE").toString());
			}
			
			//币种
			List<Map<String, Object>> accCurList = accSubjMapper.queryAccCurListForImport(seachMap);
			Map<String, String> accCurMap = new HashMap<String, String>();
			for(Map<String, Object> accCur : accCurList){
				accCurMap.put(accCur.get("CUR_CODE").toString(), accCur.get("CUR_CODE").toString());
				accCurMap.put(accCur.get("CUR_NAME").toString(), accCur.get("CUR_CODE").toString());
			}
			
			//余额方向
			Map<String, String> subjDireMap = new HashMap<String, String>();
			subjDireMap.put("0", "0");
			subjDireMap.put("1", "1");
			subjDireMap.put("借方", "0");
			subjDireMap.put("贷方", "1");
			subjDireMap.put("借", "0");
			subjDireMap.put("贷", "1");
			
			//凭证类型
			List<Map<String, Object>> vouchTypeList = accSubjMapper.queryAccVouchTypeListForImport(seachMap);
			Map<String, String> vouchTypeMap = new HashMap<String, String>();
			for(Map<String, Object> vouchType : vouchTypeList){
				vouchTypeMap.put(vouchType.get("VOUCH_TYPE_CODE").toString(), vouchType.get("VOUCH_TYPE_CODE").toString());
				vouchTypeMap.put(vouchType.get("VOUCH_TYPE_NAME").toString(), vouchType.get("VOUCH_TYPE_CODE").toString());
			}
			
			//支出性质
			List<Map<String, Object>> deptOutList = accSubjMapper.queryAccDeptOutListForImport(seachMap);
			Map<String, String> deptOutMap = new HashMap<String, String>();
			for(Map<String, Object> deptOut : deptOutList){
				deptOutMap.put(deptOut.get("OUT_CODE").toString(), deptOut.get("OUT_CODE").toString());
				deptOutMap.put(deptOut.get("OUT_NAME").toString(), deptOut.get("OUT_CODE").toString());
			}
			
			//辅助核算
			List<Map<String, Object>> accCheckList = accSubjMapper.queryAccCheckListForImport(seachMap);
			Map<String, String> accCheckMap = new HashMap<String, String>();
			for(Map<String, Object> accCheck : accCheckList){
				accCheckMap.put(accCheck.get("CHECK_TYPE_NAME").toString(), accCheck.get("CHECK_TYPE_ID").toString());
			}
			
			//核算分类
			Map<String, Map<String, String>> checkTypeMap = new HashMap<String, Map<String,String>>();
			//核算分类--部门分类
			List<Map<String, Object>> deptKindList = accSubjMapper.queryHosDeptKindListForImport(seachMap);
			for(Map<String, Object> deptKind : deptKindList){
				Map<String, String> map = null;
				if(checkTypeMap.containsKey("部门")){
					map = checkTypeMap.get("部门");
				}else{
					map = new HashMap<String, String>();
				}
				map.put(deptKind.get("KIND_CODE").toString(), deptKind.get("KIND_CODE").toString());
				map.put(deptKind.get("KIND_NAME").toString(), deptKind.get("KIND_CODE").toString());
				checkTypeMap.put("部门", map);
			}
			//核算分类--职工分类
			List<Map<String, Object>> empKindList = accSubjMapper.queryHosEmpKindListForImport(seachMap);
			for(Map<String, Object> empKind : empKindList){
				Map<String, String> map = null;
				if(checkTypeMap.containsKey("职工")){
					map = checkTypeMap.get("职工");
				}else{
					map = new HashMap<String, String>();
				}
				map.put(empKind.get("KIND_CODE").toString(), empKind.get("KIND_CODE").toString());
				map.put(empKind.get("KIND_NAME").toString(), empKind.get("KIND_CODE").toString());
				checkTypeMap.put("职工", map);
			}
			//核算分类--项目分类
			List<Map<String, Object>> projTypeList = accSubjMapper.queryHosProjTypeListForImport(seachMap);
			for(Map<String, Object> projType : projTypeList){
				Map<String, String> map = null;
				if(checkTypeMap.containsKey("项目")){
					map = checkTypeMap.get("项目");
				}else{
					map = new HashMap<String, String>();
				}
				map.put(projType.get("TYPE_CODE").toString(), projType.get("TYPE_CODE").toString());
				map.put(projType.get("TYPE_NAME").toString(), projType.get("TYPE_CODE").toString());
				checkTypeMap.put("项目", map);
			}
			//核算分类--库房分类
			List<Map<String, Object>> storeTypeList = accSubjMapper.queryHosStoreTypeListForImport(seachMap);
			for(Map<String, Object> storeType : storeTypeList){
				Map<String, String> map = null;
				if(checkTypeMap.containsKey("库房")){
					map = checkTypeMap.get("库房");
				}else{
					map = new HashMap<String, String>();
				}
				map.put(storeType.get("TYPE_CODE").toString(), storeType.get("TYPE_CODE").toString());
				map.put(storeType.get("TYPE_NAME").toString(), storeType.get("TYPE_CODE").toString());
				checkTypeMap.put("库房", map);
			}
			//核算分类--客户分类
			List<Map<String, Object>> cusTypeList = accSubjMapper.queryHosCusTypeListForImport(seachMap);
			for(Map<String, Object> cusType : cusTypeList){
				Map<String, String> map = null;
				if(checkTypeMap.containsKey("客户")){
					map = checkTypeMap.get("客户");
				}else{
					map = new HashMap<String, String>();
				}
				map.put(cusType.get("TYPE_CODE").toString(), cusType.get("TYPE_CODE").toString());
				map.put(cusType.get("TYPE_NAME").toString(), cusType.get("TYPE_CODE").toString());
				checkTypeMap.put("客户", map);
			}
			//核算分类--供应商分类
			List<Map<String, Object>> supTypeList = accSubjMapper.queryHosSupTypeListForImport(seachMap);
			for(Map<String, Object> supType : supTypeList){
				Map<String, String> map = null;
				if(checkTypeMap.containsKey("供应商")){
					map = checkTypeMap.get("供应商");
				}else{
					map = new HashMap<String, String>();
				}
				map.put(supType.get("TYPE_CODE").toString(), supType.get("TYPE_CODE").toString());
				map.put(supType.get("TYPE_NAME").toString(), supType.get("TYPE_CODE").toString());
				checkTypeMap.put("供应商", map);
			}
			//核算分类--资金来源分类
			List<Map<String, Object>> sourceTypeList = accSubjMapper.queryHosSourceTypeListForImport(seachMap);
			for(Map<String, Object> sourceType : sourceTypeList){
				Map<String, String> map = null;
				if(checkTypeMap.containsKey("资金来源")){
					map = checkTypeMap.get("资金来源");
				}else{
					map = new HashMap<String, String>();
				}
				map.put(sourceType.get("NATURE_CODE").toString(), sourceType.get("NATURE_CODE").toString());
				map.put(sourceType.get("NATURE_NAME").toString(), sourceType.get("NATURE_CODE").toString());
				checkTypeMap.put("资金来源", map);
			}
			//核算分类--单位分类（无）
			//核算分类--自定义辅助核算分类
			List<Map<String, Object>> checkItemTypeList = accSubjMapper.queryAccCheckItemTypeListForImport(seachMap);
			String checkTypeName = "";
			for(Map<String, Object> checkItem : checkItemTypeList){
				Map<String, String> map = null;
				checkTypeName = checkItem.get("CHECK_TYPE_NAME").toString();
				if(checkTypeMap.containsKey(checkTypeName)){
					map = checkTypeMap.get(checkTypeName);
				}else{
					map = new HashMap<String, String>();
				}
				map.put(checkItem.get("TYPE_CODE").toString(), checkItem.get("TYPE_CODE").toString());
				map.put(checkItem.get("TYPE_NAME").toString(), checkItem.get("TYPE_CODE").toString());
				checkTypeMap.put(checkTypeName, map);
			}
			
			//是否
			Map<String, String> yesOrNoMap = new HashMap<String, String>();
			yesOrNoMap.put("0", "0");
			yesOrNoMap.put("1", "1");
			yesOrNoMap.put("是", "1");
			yesOrNoMap.put("否", "0");
			
			//编码规则
			if(entityMap.get("rules") == null || "".equals(entityMap.get("rules").toString())){
				retMap.put("state", "false");
				retMap.put("error", "获取编码规则异常，请联系管理员（或试着减少导入的行数）！");
				return retMap;
			}
			String rules = entityMap.get("rules").toString();
	        String[] ruless = rules.split("-");
			
			//解析前台数据
			if(entityMap.get("data") == null || "".equals(entityMap.get("data").toString())){
				retMap.put("state", "false");
				retMap.put("error", "获取数据异常，请联系管理员（或试着减少导入的行数）！");
				return retMap;
			}
			String data = entityMap.get("data").toString();
			List<Map<String, List<String>>> dataList = SpreadTableJSUtil.toListMap(data, 1);
			if(dataList==null || dataList.size()==0){
				retMap.put("state", "false");
				retMap.put("error", "没有数据！");
				return retMap;
			}
			List<String> rowList = null;
			String subj_code = "", subj_name = "";
			int subj_level = 0;
			String super_code = "", super_name_all = "";
			String check;
			String check_type = "";
			String[] check_types = null; 
			StringBuffer lastSubj = new StringBuffer();
			Map<String, Object> lastSubjMap = new HashMap<String, Object>();
			for(Map<String, List<String>> dataMap : dataList){
				Map<String, Object> map = new HashMap<String, Object>();
				Map<String, Object> superMap = new HashMap<String, Object>();
				
				/**校验科目编码************begin*****************/
				rowList = dataMap.get("科目编码");
				if(rowList == null || rowList.get(1) == null || "".equals(rowList.get(1))){
					retMap.put("state", "false");
					retMap.put("row_cell", rowList.get(0));
					retMap.put("warn", rowList.get(0) + "，科目编码为空！");
					return retMap;
				}
				subj_code = rowList.get(1);
				if(accSubjMap.containsKey(subj_code)){
					retMap.put("state", "false");
					retMap.put("row_cell", rowList.get(0));
					retMap.put("warn", rowList.get(0) + "，科目编码已存在！");
					return retMap;
				}
		        /****编码规则及上级编码校验*******************begin*********/
				Map<Integer,Integer> maxNumMap = new HashMap<Integer,Integer>();
				Map<Integer,Integer>  position = new HashMap<Integer,Integer>();
				int super_num = 0;
				
				for(int j = 0; j < ruless.length; j++){
					int num = Integer.parseInt(ruless[j].replace(" ", ""));
					super_num += num;
					maxNumMap.put(super_num, j + 1);
					position.put(j + 1, super_num);
				}
				
				if(maxNumMap.containsKey(subj_code.length())){//编码匹配
					subj_level = maxNumMap.get(subj_code.length());
					if(subj_level == 1){
						super_code = "top";
						/*if(subj_code.startsWith("5001")){
							super_name_all = "医疗业务成本";
						}else if(subj_code.startsWith("5301")){
							super_name_all = "管理费用";
						}else{
							super_name_all = "";
						}*/
						super_name_all = "";
					}else{
						int super_level =  subj_level - 1;//上级级次
						int subPosition = position.get(super_level);//上级级次位置
						super_code = subj_code.substring(0,subPosition);//截取上级编码
						
						if(accSubjMap.containsKey(super_code)){
							superMap = accSubjMap.get(super_code);
							//上级科目本年是否存在发生数据
							if(superMap.get("have_vouch") != null && Integer.parseInt(superMap.get("have_vouch").toString()) != 0){
								retMap.put("state", "false");
								retMap.put("row_cell", rowList.get(0));
								retMap.put("warn", rowList.get(0) + "，上级科目本年存在发生数据！");
								return retMap;
							}
							//上级科目本年是否存在期初数据
							if(superMap.get("have_init_leder") != null && Integer.parseInt(superMap.get("have_init_leder").toString()) != 0){
								retMap.put("state", "false");
								retMap.put("row_cell", rowList.get(0));
								retMap.put("warn", rowList.get(0) + "，上级科目本年存在发生数据！");
								return retMap;
							}
							//上级科目本年是否存在上年结转未核销数据
							if(superMap.get("have_bal_amt") != null && Integer.parseInt(superMap.get("have_bal_amt").toString()) != 0){
								retMap.put("state", "false");
								retMap.put("row_cell", rowList.get(0));
								retMap.put("warn", rowList.get(0) + "，上级科目本年存在发生数据！");
								return retMap;
							}
							//非末级的科目清空辅助核算项
							if(Integer.parseInt(superMap.get("is_last").toString()) == 1 && !lastSubjMap.containsKey(super_code)){
								lastSubj.append(super_code).append(",");
								lastSubjMap.put(super_code, super_code);
							}
							super_name_all = superMap.get("subj_name_all").toString() + "-";
						}else{
							retMap.put("state", "false");
							retMap.put("row_cell", rowList.get(0));
							retMap.put("warn", rowList.get(0) + "，上级科目编码不存在！");
							return retMap;
						}
					}
					map.put("subj_level", subj_level);
					map.put("super_code", super_code);
				}else{
					retMap.put("state", "false");
					retMap.put("row_cell", rowList.get(0));
					retMap.put("warn", rowList.get(0) + "，不符合编码规则！");
					return retMap;
		        }
				map.put("subj_code", subj_code);
		        /****编码规则及上级编码校验*******************end***********/
				/**校验科目编码************end*******************/
				
				/**校验科目名称************begin*****************/
				rowList = dataMap.get("科目名称");
				if(rowList == null || rowList.get(1) == null || "".equals(rowList.get(1))){
					retMap.put("state", "false");
					retMap.put("row_cell", rowList.get(0));
					retMap.put("warn", rowList.get(0) + "，科目名称为空！");
					return retMap;
				}
				subj_name = rowList.get(1);
				/*科目名称不严重重复
				if(accSubjMap.containsKey(subj_name)){
					retMap.put("state", "false");
					retMap.put("row_cell", rowList.get(0));
					retMap.put("warn", rowList.get(0) + "，科目名称重复！");
					return retMap;
				}*/
				super_name_all += subj_name;
				map.put("subj_name", subj_name);
				map.put("subj_name_all", super_name_all);
				/**校验科目名称************end*******************/ 
				
				/**校验英文名称************begin*****************/
				rowList = dataMap.get("英文名称");
				if(rowList == null || rowList.get(1) == null || "".equals(rowList.get(1))){
					map.put("subj_name_en", "");
				}else{
					map.put("subj_name_en", rowList.get(1));
				}
				/**校验英文名称************end*******************/ 
				
				/**校验币种************begin*****************/
				rowList = dataMap.get("币种");
				if(rowList == null || rowList.get(1) == null || "".equals(rowList.get(1))){
					retMap.put("state", "false");
					retMap.put("row_cell", rowList.get(0));
					retMap.put("warn", rowList.get(0) + "，币种为空！");
					return retMap;
				}
				if(!accCurMap.containsKey(rowList.get(1))){
					retMap.put("state", "false");
					retMap.put("row_cell", rowList.get(0));
					retMap.put("warn", rowList.get(0) + "，币种不存在！");
					return retMap;
				}
				map.put("cur_code", accCurMap.get(rowList.get(1)));
				/**校验币种************end*******************/ 
				
				/**校验科目类别************begin*****************/
				rowList = dataMap.get("科目类别");
				if(rowList == null || rowList.get(1) == null || "".equals(rowList.get(1))){
					retMap.put("state", "false");
					retMap.put("row_cell", rowList.get(0));
					retMap.put("warn", rowList.get(0) + "，科目类别为空！");
					return retMap;
				}
				if(!subjTypeMap.containsKey(rowList.get(1))){
					retMap.put("state", "false");
					retMap.put("row_cell", rowList.get(0));
					retMap.put("warn", rowList.get(0) + "，科目类别不存在！");
					return retMap;
				}
				map.put("subj_type_code", subjTypeMap.get(rowList.get(1)).split(",")[0]);
				//科目分类
				if(subjTypeMap.get(rowList.get(1)).split(",").length > 1){
					map.put("kind_code", subjTypeMap.get(rowList.get(1)).split(",")[1]);
				}else{
					map.put("kind_code", "");
				}
				/**校验科目类别************end*******************/ 
				
				/**校验科目性质************begin*****************/
				rowList = dataMap.get("科目性质");
				if(rowList == null || rowList.get(1) == null || "".equals(rowList.get(1))){
					retMap.put("state", "false");
					retMap.put("row_cell", rowList.get(0));
					retMap.put("warn", rowList.get(0) + "，科目性质为空！");
					return retMap;
				}
				if(!subjNatureMap.containsKey(rowList.get(1))){
					retMap.put("state", "false");
					retMap.put("row_cell", rowList.get(0));
					retMap.put("warn", rowList.get(0) + "，科目性质不存在！");
					return retMap;
				}
				map.put("subj_nature_code", subjNatureMap.get(rowList.get(1)));
				/**校验科目性质************end*******************/ 
				
				/**校验凭证类型************begin*****************/
				rowList = dataMap.get("凭证类型");
				if(rowList == null || rowList.get(1) == null || "".equals(rowList.get(1))){
					map.put("vouch_type_code", "");
				}else{
					if(!vouchTypeMap.containsKey(rowList.get(1))){
						retMap.put("state", "false");
						retMap.put("row_cell", rowList.get(0));
						retMap.put("warn", rowList.get(0) + "，凭证类型不存在！");
						return retMap;
					}
					map.put("vouch_type_code", vouchTypeMap.get(rowList.get(1)));
				}
				/**校验凭证类型************end*******************/ 
				
				/**校验支出性质************begin*****************/
				rowList = dataMap.get("支出性质");
				if(rowList == null || rowList.get(1) == null || "".equals(rowList.get(1))){
					map.put("out_code", "");
				}else{
					if(!deptOutMap.containsKey(rowList.get(1))){
						retMap.put("state", "false");
						retMap.put("row_cell", rowList.get(0));
						retMap.put("warn", rowList.get(0) + "，支出性质不存在！");
						return retMap;
					}
					map.put("out_code", deptOutMap.get(rowList.get(1)));
				}
				/**校验支出性质************end*******************/ 
				
				/**校验余额方向************begin*****************/
				rowList = dataMap.get("余额方向(0借,1贷)");
				if(rowList == null || rowList.get(1) == null || "".equals(rowList.get(1))){
					retMap.put("state", "false");
					retMap.put("row_cell", rowList.get(0));
					retMap.put("warn", rowList.get(0) + "，余额方向为空！");
					return retMap;
				}else{
					if(!subjDireMap.containsKey(rowList.get(1))){
						retMap.put("state", "false");
						retMap.put("row_cell", rowList.get(0));
						retMap.put("warn", rowList.get(0) + "，余额方向不存在！");
						return retMap;
					}
					map.put("subj_dire", subjDireMap.get(rowList.get(1)));
				}
				/**校验余额方向************end*******************/ 
				
				/**校验是否期末调汇************begin*****************/
				rowList = dataMap.get("是否期末调汇");
				if(rowList == null || rowList.get(1) == null || "".equals(rowList.get(1))){
					retMap.put("state", "false");
					retMap.put("row_cell", rowList.get(0));
					retMap.put("warn", rowList.get(0) + "，是否期末调汇为空！");
					return retMap;
				}
				if(!yesOrNoMap.containsKey(rowList.get(1))){
					retMap.put("state", "false");
					retMap.put("row_cell", rowList.get(0));
					retMap.put("warn", rowList.get(0) + "，是否期末调汇不存在！");
					return retMap;
				}
				map.put("is_remit", yesOrNoMap.get(rowList.get(1)));
				/**校验是否期末调汇************end*******************/ 
				
				/**校验是否核算现金流************begin*****************/
				rowList = dataMap.get("是否核算现金流");
				if(rowList == null || rowList.get(1) == null || "".equals(rowList.get(1))){
					retMap.put("state", "false");
					retMap.put("row_cell", rowList.get(0));
					retMap.put("warn", rowList.get(0) + "，是否核算现金流为空！");
					return retMap;
				}
				if(!yesOrNoMap.containsKey(rowList.get(1))){
					retMap.put("state", "false");
					retMap.put("row_cell", rowList.get(0));
					retMap.put("warn", rowList.get(0) + "，是否核算现金流不存在！");
					return retMap;
				}
				map.put("is_cash", yesOrNoMap.get(rowList.get(1)));
				/**校验是否核算现金流************end*******************/ 
				
				/**校验是否票据管理************begin*****************/
				rowList = dataMap.get("是否票据管理");
				if(rowList == null || rowList.get(1) == null || "".equals(rowList.get(1))){
					retMap.put("state", "false");
					retMap.put("row_cell", rowList.get(0));
					retMap.put("warn", rowList.get(0) + "，是否票据管理为空！");
					return retMap;
				}
				if(!yesOrNoMap.containsKey(rowList.get(1))){
					retMap.put("state", "false");
					retMap.put("row_cell", rowList.get(0));
					retMap.put("warn", rowList.get(0) + "，是否票据管理不存在！");
					return retMap;
				}
				map.put("is_bill", yesOrNoMap.get(rowList.get(1)));
				/**校验是否票据管理************end*******************/ 
				
				/**校验辅助核算1************begin*****************/
				rowList = dataMap.get("辅助核算1");
				if(rowList == null || rowList.get(1) == null || "".equals(rowList.get(1))){
					map.put("check1", "");
					map.put("check_type_code1", "");
					map.put("check2", "");
					map.put("check_type_code2", "");
					map.put("check3", "");
					map.put("check_type_code3", "");
					map.put("check4", "");
					map.put("check_type_code4", "");
					map.put("is_check", 0);
				}else{
					check = rowList.get(1);
					if(!accCheckMap.containsKey(check)){
						retMap.put("state", "false");
						retMap.put("row_cell", rowList.get(0));
						retMap.put("warn", rowList.get(0) + "，辅助核算1不存在！");
						return retMap;
					}
					map.put("check1", accCheckMap.get(check));
					map.put("is_check", 1);
					/**校验核算分类1************begin*****************/
					rowList = dataMap.get("核算分类1");
					if(rowList == null || rowList.get(1) == null || "".equals(rowList.get(1))){
						map.put("check_type_code1", "");
					}else{
						check_type = "";
						check_types = rowList.get(1).replace("，", ",").split(",");
						for(int i = 0; i <check_types.length; i++){
							if(!checkTypeMap.get(check).containsKey(check_types[i])){
								retMap.put("state", "false");
								retMap.put("row_cell", rowList.get(0));
								retMap.put("warn", rowList.get(0) + "，核算分类1不存在！");
								return retMap;
							}else{
								check_type += checkTypeMap.get(check).get(check_types[i]) + ",";
							}
						}
						
						map.put("check_type_code1", check_type.substring(0, check_type.length() - 1));
					}
					/**校验核算分类1************end*******************/ 
					/**校验辅助核算1************end*******************/ 
					
					/**校验辅助核算2************begin*****************/
					rowList = dataMap.get("辅助核算2");
					if(rowList == null || rowList.get(1) == null || "".equals(rowList.get(1))){
						map.put("check2", "");
						map.put("check_type_code2", "");
						map.put("check3", "");
						map.put("check_type_code3", "");
						map.put("check4", "");
						map.put("check_type_code4", "");
					}else{
						check = rowList.get(1);
						if(!accCheckMap.containsKey(check)){
							retMap.put("state", "false");
							retMap.put("row_cell", rowList.get(0));
							retMap.put("warn", rowList.get(0) + "，辅助核算2不存在！");
							return retMap;
						}
						map.put("check2", accCheckMap.get(check));
						/**校验核算分类2************begin*****************/
						rowList = dataMap.get("核算分类2");
						if(rowList == null || rowList.get(1) == null || "".equals(rowList.get(1))){
							map.put("check_type_code2", "");
						}else{
							check_type = "";
							check_types = rowList.get(1).replace("，", ",").split(",");
							for(int i = 0; i <check_types.length; i++){
								if(!checkTypeMap.get(check).containsKey(check_types[i])){
									retMap.put("state", "false");
									retMap.put("row_cell", rowList.get(0));
									retMap.put("warn", rowList.get(0) + "，核算分类2不存在！");
									return retMap;
								}else{
									check_type += checkTypeMap.get(check).get(check_types[i]) + ",";
								}
							}
							
							map.put("check_type_code2", check_type.substring(0, check_type.length() - 1));
						}
						/**校验核算分类2************end*******************/ 
						/**校验辅助核算2************end*******************/ 
						
						/**校验辅助核算3************begin*****************/
						rowList = dataMap.get("辅助核算3");
						if(rowList == null || rowList.get(1) == null || "".equals(rowList.get(1))){
							map.put("check3", "");
							map.put("check_type_code3", "");
							map.put("check4", "");
							map.put("check_type_code4", "");
						}else{
							check = rowList.get(1);
							if(!accCheckMap.containsKey(check)){
								retMap.put("state", "false");
								retMap.put("row_cell", rowList.get(0));
								retMap.put("warn", rowList.get(0) + "，辅助核算3不存在！");
								return retMap;
							}
							map.put("check3", accCheckMap.get(check));
							/**校验核算分类3************begin*****************/
							rowList = dataMap.get("核算分类3");
							if(rowList == null || rowList.get(1) == null || "".equals(rowList.get(1))){
								map.put("check_type_code3", "");
							}else{
								check_type = "";
								check_types = rowList.get(1).replace("，", ",").split(",");
								for(int i = 0; i <check_types.length; i++){
									if(!checkTypeMap.get(check).containsKey(check_types[i])){
										retMap.put("state", "false");
										retMap.put("row_cell", rowList.get(0));
										retMap.put("warn", rowList.get(0) + "，核算分类3不存在！");
										return retMap;
									}else{
										check_type += checkTypeMap.get(check).get(check_types[i]) + ",";
									}
								}
								
								map.put("check_type_code3", check_type.substring(0, check_type.length() - 1));
							}
							/**校验核算分类3************end*******************/ 
							/**校验辅助核算3************end*******************/ 
							
							/**校验辅助核算4************begin*****************/
							rowList = dataMap.get("辅助核算4");
							if(rowList == null || rowList.get(1) == null || "".equals(rowList.get(1))){
								map.put("check4", "");
								map.put("check_type_code4", "");
							}else{
								check = rowList.get(1);
								if(!accCheckMap.containsKey(check)){
									retMap.put("state", "false");
									retMap.put("row_cell", rowList.get(0));
									retMap.put("warn", rowList.get(0) + "，辅助核算4不存在！");
									return retMap;
								}
								map.put("check4", accCheckMap.get(check));
								/**校验核算分类4************begin*****************/
								rowList = dataMap.get("核算分类4");
								if(rowList == null || rowList.get(1) == null || "".equals(rowList.get(1))){
									map.put("check_type_code4", "");
								}else{
									check_type = "";
									check_types = rowList.get(1).replace("，", ",").split(",");
									for(int i = 0; i <check_types.length; i++){
										if(!checkTypeMap.get(check).containsKey(check_types[i])){
											retMap.put("state", "false");
											retMap.put("row_cell", rowList.get(0));
											retMap.put("warn", rowList.get(0) + "，核算分类4不存在！");
											return retMap;
										}else{
											check_type += checkTypeMap.get(check).get(check_types[i]) + ",";
										}
									}
									
									map.put("check_type_code4", check_type.substring(0, check_type.length() - 1));
								}
								/**校验核算分类4************end*******************/ 
							}
							/**校验辅助核算4************end*******************/ 
						}
					}
				}
				
				//默认值
				map.put("spell_code", StringTool.toPinyinShouZiMu(subj_name));
				map.put("wbx_code", StringTool.toWuBi(subj_name));
				map.put("group_id", group_id);
				map.put("hos_id", hos_id);
				map.put("copy_code", copy_code);
				map.put("acc_year", acc_year);
				map.put("is_last", 1);
				map.put("have_vouch", 0);
				map.put("have_init_leder", 0);
				map.put("have_bal_amt", 0);
				map.put("is_stop", 0);
				map.put("is_pact", null);
				
				
				//放到科目列表中参与业务取值
				accSubjMap.put(subj_code, map);
				accSubjMap.put(subj_name, map);
				
				//放到list中用于插入数据库
				subjList.add(map);
			}
			//批量添加
			accSubjMapper.insertAccSubjForImport(subjList);
			//修改非末级
			if(lastSubj.length() > 0){
				seachMap.put("subj_code", lastSubj.substring(0, lastSubj.length() - 1));
				accSubjMapper.updateAccSubjForImport(seachMap);
			}
			retMap.put("msg", "操作成功！");
		}catch (Exception e) {
			logger.error(e.getMessage(), e);
			
			throw new SysException("操作失败");
		}
		
		return retMap;
	}


	@Override
	public List<Map<String, Object>> queryAccSubjByVouch(
			Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		return accSubjMapper.queryAccSubjByVouch( entityMap);
	}
}
