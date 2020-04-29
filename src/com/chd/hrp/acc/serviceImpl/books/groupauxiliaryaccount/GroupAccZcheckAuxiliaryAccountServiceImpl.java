package com.chd.hrp.acc.serviceImpl.books.groupauxiliaryaccount;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.chd.base.util.ChdJson;
import com.chd.hrp.acc.dao.HrpAccSelectMapper;
import com.chd.hrp.acc.dao.books.groupauxiliaryaccount.GroupAccZcheckAuxiliaryAccountMapper;
import com.chd.hrp.acc.entity.AccSupAuxiliaryAccount;
import com.chd.hrp.acc.service.books.groupauxiliaryaccount.GroupAccZcheckAuxiliaryAccountService;
;
;
;
@Service("groupAccZcheckAuxiliaryAccountService")
public class GroupAccZcheckAuxiliaryAccountServiceImpl implements GroupAccZcheckAuxiliaryAccountService {
	
	@Resource(name = "groupAccZcheckAuxiliaryAccountMapper")
	private final GroupAccZcheckAuxiliaryAccountMapper groupAccZcheckAuxiliaryAccountMapper = null;
	
	@Resource(name = "hrpAccSelectMapper")
	private final HrpAccSelectMapper hrpAccSelectMapper = null;
	
	//查询核算项科目总账
	@Override
	@Transactional(rollbackFor=Exception.class)
	public String collectGroupAccZcheckGeneralLedger(Map<String, Object> entityMap)
			throws DataAccessException {
		
		entityMap.put("source_id", "0");
		
		groupAccZcheckAuxiliaryAccountMapper.collectGroupAccZcheckGeneralLedger(entityMap);
		
		TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		
		return ChdJson.toJsonLower((List<Map<String, Object>>) entityMap.get("rst"));
	}

	@Override
	@Transactional(rollbackFor=Exception.class)
	public String collectGroupAccZcheckDetailLedger(Map<String, Object> entityMap)
			throws DataAccessException {
		
		entityMap.put("source_id", "0");
		
		groupAccZcheckAuxiliaryAccountMapper.collectGroupAccZcheckDetailLedger(entityMap);
		
		TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		
		//System.out.println(ChdJson.toJson((List<Map<String, Object>>)entityMap.get("objdata")));
		
		return ChdJson.toJsonLower((List<Map<String, Object>>) entityMap.get("rst"));
	}

	@Override
	@Transactional(rollbackFor=Exception.class)
	public String collectGroupAccSubjZcheckGeneralLedger(
			Map<String, Object> entityMap) throws DataAccessException {
		
		entityMap.put("source_id", "0");
		
		groupAccZcheckAuxiliaryAccountMapper.collectGroupAccSubjZcheckGeneralLedger(entityMap);
		
		TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		
		return ChdJson.toJsonLower((List<Map<String, Object>>) entityMap.get("rst"));
	}

	@Override
	@Transactional(rollbackFor=Exception.class)
	public String collectGroupAccSubjZcheckDetailLedger(Map<String, Object> entityMap)
			throws DataAccessException {

		
		
		entityMap.put("source_id", "0");
		
		groupAccZcheckAuxiliaryAccountMapper.collectGroupAccSubjZcheckDetailLedger(entityMap);
		
		TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		
		return ChdJson.toJson((List<AccSupAuxiliaryAccount>)entityMap.get("rst"));
	}

	//自定义科目核算余额表
	@Override
	public String collectGroupAccZcheckEndOs(Map<String, Object> entityMap)throws DataAccessException {
		
		String group_id=entityMap.get("group_id").toString();
		String hos_id=entityMap.get("hos_id").toString();
		String copy_code=entityMap.get("copy_code").toString();
		String check_item_code=entityMap.get("check_item_code").toString();
		//根据核算类ID查询核算类型表
		Map<String,Object> checkMap=new HashMap<String,Object>();
		checkMap=hrpAccSelectMapper.queryCheckTypeById(entityMap);
		
		StringBuilder checkSql=new StringBuilder();
		StringBuilder whereSql=new StringBuilder();
		int isSys=Integer.parseInt(checkMap.get("IS_SYS").toString());
		int isChange=Integer.parseInt(checkMap.get("IS_CHANGE").toString());
		String checkTable=checkMap.get("CHECK_TABLE").toString();
		String columnId=checkMap.get("COLUMN_ID").toString();
		String objCode=checkMap.get("COLUMN_CODE").toString();
		String objName=checkMap.get("COLUMN_NAME").toString();
		String columnCheck=checkMap.get("COLUMN_CHECK").toString();
		
		//科目方向
		int dire=groupAccZcheckAuxiliaryAccountMapper.queryGroupAccSubjDire(entityMap);
		
		
		
		if(isSys==1){
			//系统核算
			checkSql.append("left join "+checkTable+" ck on ck.group_id="+group_id+" and ck.hos_id="+hos_id+" ");
			columnCheck=columnCheck+"_id";
			
			if(isChange==1){
				//变更表
				checkSql.append(" and ck."+columnId+"=r1.obj_id and ck.is_stop=0 ");
				
			}else{
				checkSql.append(" and ck."+columnId+"=r1.obj_id ");
			}
			
		}else{
			//自定义核算
			checkSql.append("left join "+checkTable+" ck on ck.group_id="+group_id+" and ck.hos_id="+hos_id+" and ck.copy_code='"+copy_code+"' ");
			checkSql.append(" and ck.check_type_id="+entityMap.get("check_type_id").toString()+" and ck.check_item_id=r1.obj_id ");
		}
		
		//过滤核算项数据
		if(!check_item_code.equals("")){
			whereSql.append(" and c."+columnCheck+" in("+check_item_code+") ");
		}
		
		//核算项编码
		entityMap.put("obj_code", objCode);
		//核算项名称
		entityMap.put("obj_name", objName);
		//科目方向
		entityMap.put("dire", dire);
		//核算项字段
		entityMap.put("column_check", columnCheck);
		//关联核算项sql语句
		entityMap.put("check_sql", checkSql);
		//过滤核算项sql语句
		entityMap.put("where_sql", whereSql);
		List<Map<String,Object>> reList=new ArrayList<Map<String,Object>>();
		reList=groupAccZcheckAuxiliaryAccountMapper.collectGroupAccZcheckEndOs(entityMap);
		
		
		return ChdJson.toJson(reList);
	}
	
	//打印核算项科目总账   2017-09-18
	@Override
	@Transactional(rollbackFor = Exception.class)
	public List<Map<String, Object>> collectGroupAccZcheckGeneralLedgerPrint(Map<String, Object> entityMap) throws DataAccessException {

		entityMap.put("source_id", "0");
		
		groupAccZcheckAuxiliaryAccountMapper.collectGroupAccZcheckGeneralLedger(entityMap);

		TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();

		List<Map<String, Object>> resList=(List<Map<String, Object>>) entityMap.get("rst");
		
		return resList;

	}
	
	//打印核算项科目明细账 2017-09-18
		@Override
		@Transactional(rollbackFor = Exception.class)
		public List<Map<String, Object>> collectGroupAccZcheckDetailLedgerPrint(Map<String, Object> entityMap) throws DataAccessException {
			
			Map<String, Object> listMap = new HashMap<String, Object>();
			
			List<Map<String, Object>> listResult = new ArrayList<Map<String,Object>>();
			
			entityMap.put("source_id", "0");
			
			groupAccZcheckAuxiliaryAccountMapper.collectGroupAccZcheckDetailLedger(entityMap);

			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();

			List<Map<String, Object>> resList=(List<Map<String, Object>>) entityMap.get("rst");
			
			String vouch_no = null ;
			
			for(Map<String, Object> map :resList){
				
				if(map.get("VOUCH_NO")!=null){ 
					
					//  重新封装凭证号
					vouch_no = map.get("VOUCH_NO").toString().split("-")[0]+"-"+map.get("VOUCH_NO").toString().split("-")[1];
					 
					listMap.put("VOUCH_NO", vouch_no);
					
					map.remove("VOUCH_NO");
					
					map.put("VOUCH_NO", listMap.get("VOUCH_NO"));
					
				} 
				
				listResult.add(map);
			} 
			 return listResult;

		}
		
 //打印科目核算项总账 2017-09-18
	 @Override
     @Transactional(rollbackFor = Exception.class)
	 public List<Map<String, Object>> collectGroupAccSubjZcheckGeneralLedgerPrint(Map<String, Object> entityMap) throws DataAccessException {

		 	entityMap.put("source_id", "0");
			
			groupAccZcheckAuxiliaryAccountMapper.collectGroupAccSubjZcheckGeneralLedger(entityMap);
			
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			
			List<Map<String, Object>> resList=(List<Map<String, Object>>) entityMap.get("rst");
			
			return resList;
			 
      }
	 //打印科目核算项明细账 2017-09-18
	 @Override
	 @Transactional(rollbackFor = Exception.class)
	 public List<Map<String, Object>> collectGroupAccSubjZcheckDetailLedgerPrint(Map<String, Object> entityMap) throws DataAccessException {
		 
		 	entityMap.put("source_id", "0");
			
			groupAccZcheckAuxiliaryAccountMapper.collectGroupAccSubjZcheckDetailLedger(entityMap);
			
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		 
		 List<Map<String, Object>> resList=(List<Map<String, Object>>) entityMap.get("rst");
		 
		 return resList;
		 
	 }
	 //打印科目核算项余额表 2017-09-18
	 @Override
	 @Transactional(rollbackFor = Exception.class)
	 public List<Map<String, Object>> collectGroupAccSubjZcheckEndOsPrint(Map<String, Object> entityMap) throws DataAccessException {
		 
		 String group_id=entityMap.get("group_id").toString();
			String hos_id=entityMap.get("hos_id").toString();
			String copy_code=entityMap.get("copy_code").toString();
			String check_item_code=entityMap.get("check_item_code").toString();
			//根据核算类ID查询核算类型表
			Map<String,Object> checkMap=new HashMap<String,Object>();
			checkMap=hrpAccSelectMapper.queryCheckTypeById(entityMap);
			
			StringBuilder checkSql=new StringBuilder();
			StringBuilder whereSql=new StringBuilder();
			int isSys=Integer.parseInt(checkMap.get("IS_SYS").toString());
			int isChange=Integer.parseInt(checkMap.get("IS_CHANGE").toString());
			String checkTable=checkMap.get("CHECK_TABLE").toString();
			String columnId=checkMap.get("COLUMN_ID").toString();
			String objCode=checkMap.get("COLUMN_CODE").toString();
			String objName=checkMap.get("COLUMN_NAME").toString();
			String columnCheck=checkMap.get("COLUMN_CHECK").toString();
			
			//科目方向
			int dire=groupAccZcheckAuxiliaryAccountMapper.queryGroupAccSubjDire(entityMap);
			
			
			
			if(isSys==1){
				//系统核算
				checkSql.append("left join "+checkTable+" ck on ck.group_id="+group_id+" and ck.hos_id="+hos_id+" ");
				columnCheck=columnCheck+"_id";
				
				if(isChange==1){
					//变更表
					checkSql.append(" and ck."+columnId+"=r1.obj_id and ck.is_stop=0 ");
					
				}else{
					checkSql.append(" and ck."+columnId+"=r1.obj_id ");
				}
				
			}else{
				//自定义核算
				checkSql.append("left join "+checkTable+" ck on ck.group_id="+group_id+" and ck.hos_id="+hos_id+" and ck.copy_code='"+copy_code+"' ");
				checkSql.append(" and ck.check_type_id="+entityMap.get("check_type_id").toString()+" and ck.check_item_id=r1.obj_id ");
			}
			
			//过滤核算项数据
			if(!check_item_code.equals("")){
				whereSql.append(" and c."+columnCheck+" in("+check_item_code+") ");
			}
			
			//核算项编码
			entityMap.put("obj_code", objCode);
			//核算项名称
			entityMap.put("obj_name", objName);
			//科目方向
			entityMap.put("dire", dire);
			//核算项字段
			entityMap.put("column_check", columnCheck);
			//关联核算项sql语句
			entityMap.put("check_sql", checkSql);
			//过滤核算项sql语句
			entityMap.put("where_sql", whereSql);
			List<Map<String,Object>> reList=new ArrayList<Map<String,Object>>(); 
		 
		 TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		 
		 List<Map<String, Object>> resList=groupAccZcheckAuxiliaryAccountMapper.collectGroupAccZcheckEndOs(entityMap);
		 
		 return resList;
		 
	 }
	 
		
}
