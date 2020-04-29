package com.chd.hrp.acc.serviceImpl.books.check;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.chd.base.util.ChdJson;
import com.chd.hrp.acc.dao.books.check.AccBooksCheckMapper;
import com.chd.hrp.acc.service.books.check.AccBooksCheckService;

@Service("accBooksCheckService")
public class AccBooksCheckServiceImpl implements AccBooksCheckService {

	private static Logger logger = Logger.getLogger(AccBooksCheckServiceImpl.class);
	
	@Resource(name = "accBooksCheckMapper")
	private final AccBooksCheckMapper accBooksCheckMapper = null;
	 
	@Autowired
	private DataSourceTransactionManager txManager;
	
	/**
	 * 总账查询
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public String collectAccBooksCheckZZ(Map<String, Object> entityMap) throws DataAccessException {

		entityMap.put("p_flag", "fzz101");
		entityMap.put("rst", new ArrayList<Map<String, Object>>());
		accBooksCheckMapper.queryAccBooksCheck(entityMap);
		
		//错误信息
		if(entityMap.get("error_msg") != null && !"".equals(entityMap.get("error_msg").toString())){

			return "{\"error\":\""+entityMap.get("error_msg").toString()+"\"}";
		}
		
		TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		return ChdJson.toJsonLower((List<Map<String,Object>>)entityMap.get("rst"),Long.parseLong(entityMap.get("tcount").toString()));
	}

	/**
	 *  总账模板打印
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public Map<String, Object> collectAccBooksCheckPrintZZ(Map<String, Object> entityMap) throws DataAccessException {

		Map<String, Object> reMap = new HashMap<String, Object>();

		entityMap.put("p_flag", "fzz101");
		entityMap.put("rst", new ArrayList<Map<String, Object>>());
		accBooksCheckMapper.queryAccBooksCheck(entityMap);

		TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();

		reMap.put("detail", (List<Map<String, Object>>) entityMap.get("rst"));
		return reMap;
	}

	/**
	 *  总账表格打印
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public List<Map<String, Object>> collectAccBooksCheckPrintGridZZ(Map<String, Object> entityMap) throws DataAccessException {

		entityMap.put("p_flag", "fzz101");
		entityMap.put("rst", new ArrayList<Map<String, Object>>());
		accBooksCheckMapper.queryAccBooksCheck(entityMap);

		TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();

		return (List<Map<String, Object>>) entityMap.get("rst");
	}
	
	/**
	 * 明细账查询
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public String collectAccBooksCheckMXZ(Map<String, Object> entityMap) throws DataAccessException {

		entityMap.put("p_flag", "fzz102");
		entityMap.put("rst", new ArrayList<Map<String, Object>>());
		accBooksCheckMapper.queryAccBooksCheck(entityMap);
		
		//错误信息
		if(entityMap.get("error_msg") != null && !"".equals(entityMap.get("error_msg").toString())){

			return "{\"error\":\""+entityMap.get("error_msg").toString()+"\"}";
		}
		
		TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		return ChdJson.toJsonLower((List<Map<String,Object>>)entityMap.get("rst"),Long.parseLong(entityMap.get("tcount").toString()));
	}

	/**
	 *  明细账模板打印
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public Map<String, Object> collectAccBooksCheckPrintMXZ(Map<String, Object> entityMap) throws DataAccessException {

		Map<String, Object> reMap = new HashMap<String, Object>();

		entityMap.put("p_flag", "fzz102");
		entityMap.put("rst", new ArrayList<Map<String, Object>>());
		accBooksCheckMapper.queryAccBooksCheck(entityMap);

		TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();

		reMap.put("detail", (List<Map<String, Object>>) entityMap.get("rst"));
		return reMap;
	}

	/**
	 *  明细账表格打印
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public List<Map<String, Object>> collectAccBooksCheckPrintGridMXZ(Map<String, Object> entityMap) throws DataAccessException {

		entityMap.put("p_flag", "fzz102");
		entityMap.put("rst", new ArrayList<Map<String, Object>>());
		accBooksCheckMapper.queryAccBooksCheck(entityMap);

		TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();

		return (List<Map<String, Object>>) entityMap.get("rst");
	}
	
	/**
	 * 余额表查询
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public String collectAccBooksCheckYEB(Map<String, Object> entityMap) throws DataAccessException {

		entityMap.put("p_flag", "fzz103");
		entityMap.put("rst", new ArrayList<Map<String, Object>>());
		accBooksCheckMapper.queryAccBooksCheck(entityMap);
		
		//错误信息
		if(entityMap.get("error_msg") != null && !"".equals(entityMap.get("error_msg").toString())){

			return "{\"error\":\""+entityMap.get("error_msg").toString()+"\"}";
		}
		
		TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		return ChdJson.toJsonLower((List<Map<String,Object>>)entityMap.get("rst"),Long.parseLong(entityMap.get("tcount").toString()));
	}

	/**
	 *  余额表模板打印
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public Map<String, Object> collectAccBooksCheckPrintYEB(Map<String, Object> entityMap) throws DataAccessException {

		Map<String, Object> reMap = new HashMap<String, Object>();

		entityMap.put("p_flag", "fzz103");
		entityMap.put("rst", new ArrayList<Map<String, Object>>());
		accBooksCheckMapper.queryAccBooksCheck(entityMap);

		TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();

		reMap.put("detail", (List<Map<String, Object>>) entityMap.get("rst"));
		return reMap;
	}

	/**
	 *  余额表表格打印
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public List<Map<String, Object>> collectAccBooksCheckPrintGridYEB(Map<String, Object> entityMap) throws DataAccessException {

		entityMap.put("p_flag", "fzz103");
		entityMap.put("rst", new ArrayList<Map<String, Object>>());
		accBooksCheckMapper.queryAccBooksCheck(entityMap);

		TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();

		return (List<Map<String, Object>>) entityMap.get("rst");
	}
	
	/**
	 * 交叉表查询
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public String collectAccBooksCheckJCB(Map<String, Object> entityMap) throws DataAccessException {

		entityMap.put("p_flag", "fzz104");
		entityMap.put("rst", new ArrayList<Map<String, Object>>());
		accBooksCheckMapper.queryAccBooksCheck(entityMap);
		
		//错误信息
		if(entityMap.get("error_msg") != null && !"".equals(entityMap.get("error_msg").toString())){

			return "{\"error\":\""+entityMap.get("error_msg").toString()+"\"}";
		}
		
		//行转列
		List<Map<String, Object>> tmpList = getJCBStyleList((List<Map<String, Object>>) entityMap.get("rst"));
		//处理分页
		List<Map<String, Object>> retList = new ArrayList<Map<String,Object>>();
		int fristIndex = Integer.valueOf(entityMap.get("pagesize").toString()) * (Integer.valueOf(entityMap.get("page").toString()) - 1);
		int endIndex = Integer.valueOf(entityMap.get("pagesize").toString()) * Integer.valueOf(entityMap.get("page").toString()) - 1;
		if(endIndex > tmpList.size() - 1){
			endIndex = tmpList.size() - 1;
		}
		for(int i = fristIndex; i <= endIndex; i++){
			retList.add(tmpList.get(i));
		}
		
		
		TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		return ChdJson.toJson(retList, tmpList.size());
	}
	
	public List<Map<String, Object>> getJCBStyleList(List<Map<String, Object>> tmpList) throws DataAccessException{
		List<Map<String, Object>> retList = new ArrayList<Map<String,Object>>();
		Map<String, Integer> existsMap = new HashMap<String, Integer>();
		Map<String, Object> retMap = null;
		int index = 0;
		String key = "";
		for(Map<String, Object> tmpMap : tmpList){
			key = "";
			if(tmpMap.get("CHECK1") != null){
				key += tmpMap.get("CHECK1").toString();
			}
			if(tmpMap.get("CHECK2") != null){
				key += tmpMap.get("CHECK2").toString();
			}
			if(tmpMap.get("CHECK3") != null){
				key += tmpMap.get("CHECK3").toString();
			}
			if(tmpMap.get("CHECK4") != null){
				key += tmpMap.get("CHECK4").toString();
			}
			
			if(existsMap.containsKey(key)){
				retMap = retList.get(existsMap.get(key));
				retMap.put("d_"+tmpMap.get("SUBJ_CODE").toString(), tmpMap.get("END_OS"));
			}else{
				retMap = new HashMap<String, Object>();
				retMap.put("check1_name", tmpMap.get("CHECK1_NAME"));
				retMap.put("check2_name", tmpMap.get("CHECK2_NAME"));
				retMap.put("check3_name", tmpMap.get("CHECK3_NAME"));
				retMap.put("check4_name", tmpMap.get("CHECK4_NAME"));
				retMap.put("d_"+tmpMap.get("SUBJ_CODE").toString(), tmpMap.get("END_OS"));
				
				retList.add(retMap);
				existsMap.put(key, index);
				index ++;
			}
		}
		return retList;
	}

	/**
	 *  交叉表模板打印
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public Map<String, Object> collectAccBooksCheckPrintJCB(Map<String, Object> entityMap) throws DataAccessException {

		Map<String, Object> reMap = new HashMap<String, Object>();

		entityMap.put("p_flag", "fzz104");
		entityMap.put("rst", new ArrayList<Map<String, Object>>());
		accBooksCheckMapper.queryAccBooksCheck(entityMap);

		TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();

		reMap.put("detail", getJCBStyleList((List<Map<String, Object>>) entityMap.get("rst")));
		return reMap;
	}

	/**
	 *  交叉表表格打印
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public List<Map<String, Object>> collectAccBooksCheckPrintGridJCB(Map<String, Object> entityMap) throws DataAccessException {

		entityMap.put("p_flag", "fzz104");
		entityMap.put("rst", new ArrayList<Map<String, Object>>());
		accBooksCheckMapper.queryAccBooksCheck(entityMap);

		TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();

		return getJCBStyleList((List<Map<String, Object>>) entityMap.get("rst"));
	}
	
	/**
	* 交叉表查询表头
	*/
	@Override
	public List<Map<String, Object>> querySubjHeadByJCB(Map<String, Object> mapVo) throws DataAccessException{
		return accBooksCheckMapper.querySubjHeadByJCB(mapVo);
	}

	/**
	 * 根据核算项名称获取核算项ID
	 */
	@Override
	public Map<String, Object> queryCheckTypeByCheck(Map<String, Object> entityMap) throws DataAccessException{
		
		Map<String, Object> checkType = accBooksCheckMapper.queryCheckTypeByCheck(entityMap);
		
		return checkType;
	}
}
