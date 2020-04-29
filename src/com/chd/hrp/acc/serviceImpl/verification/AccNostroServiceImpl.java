/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/

package com.chd.hrp.acc.serviceImpl.verification;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.alibaba.fastjson.JSONObject;
import com.chd.base.SysPage;
import com.chd.base.util.ChdJson;
import com.chd.base.util.JsonListMapUtil;
import com.chd.base.util.ReadFiles;
import com.chd.base.util.StringTool;
import com.chd.base.util.UUIDLong;
import com.chd.hrp.acc.dao.AccLederMapper;
import com.chd.hrp.acc.dao.verification.AccNostroMapper;
import com.chd.hrp.acc.dao.vouch.AccVouchCheckMapper;
import com.chd.hrp.acc.entity.AccLederCheck;
import com.chd.hrp.acc.entity.AccSubj;
import com.chd.hrp.acc.service.verification.AccNostroService;
import com.github.pagehelper.PageInfo;

/**
* @Title. @Description.
* 财务辅助账表<BR> 
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


@Service("accNostroService")
public class AccNostroServiceImpl implements AccNostroService {

	private static Logger logger = Logger.getLogger(AccNostroServiceImpl.class);
	
	
	@Resource(name = "accNostroMapper")
	private final AccNostroMapper accNostroMapper = null;
	
	@Resource(name = "accLederMapper")
	private final AccLederMapper accLederMapper = null;
	
	@Resource(name = "accVouchCheckMapper")
	private final AccVouchCheckMapper accVouchCheckMapper = null;
	
	/**
	 * 初始账删除
	 */
	@Override
	public String deleteAccVouchCheckInit(List<Map<String, Object>> entityList) throws DataAccessException{
		try {
			accNostroMapper.deleteBatchAccVouchCheck(entityList);
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		}catch (Exception e) {
			logger.error(e.getMessage(), e);
			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码  deleteBatchAccVouchCheck\"}";
		}
	}
	/**
	 * 往来初始账更新页面查询
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	@Override
	public String queryAccCurrentAccountInit(Map<String, Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		sysPage = (SysPage) entityMap.get("sysPage");
		RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

        StringBuffer colName = new StringBuffer();//查询列名
        StringBuffer whereSql = new StringBuffer();//连接sql
        
        List<Map<String,Object>> sqlList = JsonListMapUtil.toListMapLower(accNostroMapper.querySubjCheckColumnBySubjList(entityMap));
       
		int index = 1;
		String tab = "";
		for(Map<String, Object> sqlMap : sqlList){
			tab = " temp"+index;  //表别名
			colName.append(" ,").append(tab).append(".").append(sqlMap.get("column_code")).append(" ||' '||").append(tab).append(".").append(sqlMap.get("column_name")).append(" as ").append(sqlMap.get("column_name"));
			
			whereSql.append(" left join ").append(sqlMap.get("check_table")).append(tab).append(" on ").append(tab).append(".group_id = avc.group_id and ").append(tab).append(".hos_id = avc.hos_id ");
			
			//是否有变更表
			if(sqlMap.get("is_change") != null && "1".equals(sqlMap.get("is_change").toString())){
				colName.append(" ,").append(tab).append(".").append(sqlMap.get("column_id")).append(" ||' '||").append(tab).append(".").append(sqlMap.get("column_no")).append(" as ").append(sqlMap.get("column_check"));
				whereSql.append(" and ").append(tab).append(".").append(sqlMap.get("column_id")).append(" = avc.").append(sqlMap.get("column_check")).append("_id ");
				whereSql.append(" and ").append(tab).append(".").append(sqlMap.get("column_no")).append(" = avc.").append(sqlMap.get("column_check")).append("_no ");
			}else{
				if(sqlMap.get("check_table").equals("ACC_CHECK_ITEM")){
					colName.append(" ,").append(tab).append(".").append(sqlMap.get("column_id")).append(" as ").append(sqlMap.get("column_code"));
					whereSql.append(" and ").append(tab).append(".").append(sqlMap.get("column_id")).append(" = avc.").append(sqlMap.get("column_check"));
				}else{
					colName.append(" ,").append(tab).append(".").append(sqlMap.get("column_id")).append(" as ").append(sqlMap.get("column_check"));
					whereSql.append(" and ").append(tab).append(".").append(sqlMap.get("column_id")).append(" = avc.").append(sqlMap.get("column_check")).append("_id ");
				}
			}
			index++;
		}
		
		entityMap.put("colName", colName.toString());
		entityMap.put("whereSql", whereSql.toString());
		
		if (sysPage.getTotal()==-1){
			List<Map<String,Object>> list = JsonListMapUtil.toListMapLower(accNostroMapper.queryAccCurrentAccountInit(entityMap));
			return ChdJson.toJson(list);
		}else{
			List<Map<String,Object>> list = JsonListMapUtil.toListMapLower(accNostroMapper.queryAccCurrentAccountInit(entityMap, rowBounds));
			PageInfo page = new PageInfo(list);
			return ChdJson.toJson(list, sysPage.getTotal());
		}
	}
	
	/**
	 * 余额查询  普通核算查询
	 */
	@Override
	public String queryAccLederCheckBalanceO(Map<String, Object> entityMap) throws DataAccessException {
		 
		SysPage sysPage = new SysPage();		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
		
		StringBuffer whereSql = new StringBuffer();
		StringBuffer whereSqll = new StringBuffer();
		StringBuffer groupBy = new StringBuffer();
		StringBuffer colName = new StringBuffer();
		StringBuffer aWhere = new StringBuffer();
		
		if(entityMap.get("is_check").equals("1")){
			entityMap.put("state", " and av.state > 0 and av.state < 99");
	    }else{
	    	entityMap.put("state", " and av.state = 99");
	    }
		
		if(entityMap.get("check_type")!=null && !"".equals(entityMap.get("check_type").toString())){
			//查看当前会计科目挂哪个辅助核算
			Map<String, Object>sqlMap = accNostroMapper.querySubjCheckColumnBySubj(entityMap);
			
				colName.append(" dd.").append(sqlMap.get("COLUMN_CODE")).append(" as check_code, dd.").append(sqlMap.get("COLUMN_NAME").toString()).append(" as check_name,");
			    groupBy.append(",dd.").append(sqlMap.get("COLUMN_CODE").toString()).append(",dd.").append(sqlMap.get("COLUMN_NAME").toString());
			    //是否是变更表
			    if(sqlMap.get("CHECK_TABLE")!=null && !"".equals(sqlMap.get("CHECK_TABLE").toString())){
			    	if(sqlMap.get("IS_CHANGE") != null &&  "1".equals(sqlMap.get("IS_CHANGE").toString())){
				    	whereSql.append("left join ").append(sqlMap.get("CHECK_TABLE")).append(" dd ");
						whereSql.append(" on dd.group_id = avc.group_id and dd.hos_id = avc.hos_id  and dd.").append(sqlMap.get("COLUMN_ID")).append("= avc.").append(sqlMap.get("COLUMN_CHECK")).append("_ID").append(" ");
						whereSql.append(" and dd.").append(sqlMap.get("COLUMN_NO")).append(" = avc.").append(sqlMap.get("COLUMN_CHECK").toString()).append("_NO ");
						colName.append(" dd.").append(sqlMap.get("COLUMN_ID")).append(" as checkID,dd.").append(sqlMap.get("COLUMN_NO").toString()).append(" as checkNO,");
						groupBy.append(",dd.").append(sqlMap.get("COLUMN_ID").toString()).append(",dd.").append(sqlMap.get("COLUMN_NO").toString());
						if(entityMap.get("proj1").equals("")){
							whereSql.append("");
						}else{
							if(entityMap.get("proj2").equals("")){
						    	whereSql.append("");
							}else{
						    	whereSql.append(" and dd.").append(sqlMap.get("COLUMN_ID")).append(" between '" + entityMap.get("proj1") + "' and '" + entityMap.get("proj2")+"' ");
							}
						}
						aWhere.append(" and d_check.checkID = l_check.checkID and d_check.checkNO = l_check.checkNO ");
				 	}else{
				    	//自定义辅助核算
				        if(sqlMap.get("CHECK_TABLE").equals("ACC_CHECK_ITEM")){
				        	whereSql.append(" and aci.").append(sqlMap.get("COLUMN_ID")).append(" = avc.").append(sqlMap.get("COLUMN_CHECK")).append(" ");
				        	whereSqll.append(" and aci.").append(sqlMap.get("COLUMN_ID")).append(" = alc.").append(sqlMap.get("COLUMN_CHECK")).append(" ");
				        	colName.append(" aci.").append(sqlMap.get("COLUMN_ID")).append(" as checkID,");
				        	if(entityMap.get("proj1").equals("")){
				    		    whereSql.append("");
				    		    whereSqll.append("");
				    		}else{
				    		    if(entityMap.get("proj2").equals("")){
				    		    	whereSql.append("");
				    		    	whereSqll.append("");
				    			}else{
				    				whereSql.append("  and aci.").append(sqlMap.get("COLUMN_ID")).append(" between '" + entityMap.get("proj1") + "' and '" + entityMap.get("proj2")+"' ");
				    		    	whereSqll.append(" and aci.").append(sqlMap.get("COLUMN_ID")).append(" between '" + entityMap.get("proj1") + "' and '" + entityMap.get("proj2")+"' ");
				    			}
				    		} 
				        	aWhere.append(" and d_check.checkID = l_check.checkId ");
				       }else{
				       	//资金来源、单位
				    	whereSql.append("left join ").append(sqlMap.get("CHECK_TABLE")).append(" dd ");
						whereSql.append(" on dd.group_id = avc.group_id and dd.hos_id = avc.hos_id and dd.").append(sqlMap.get("COLUMN_ID")).append("= avc.").append(sqlMap.get("COLUMN_CHECK")).append("_ID ");
						colName.append(" dd.").append(sqlMap.get("COLUMN_ID")).append(" as checkID,");
						groupBy.append(",dd.").append(sqlMap.get("COLUMN_ID").toString()).append(" ");
						if(entityMap.get("proj1").equals("")){
							whereSql.append("");
						}else{
							if(entityMap.get("proj2").equals("")){
							   whereSql.append("");
							}else{
								whereSql.append(" and dd.").append(sqlMap.get("COLUMN_ID")).append(" between '" + entityMap.get("proj1") + "' and '" + entityMap.get("proj2")+"' ");
							}
						}
						aWhere.append(" and d_check.checkID = l_check.checkId ");
				     }
							
				  }
			    }
		}else{
	    	groupBy.append("");
	    	colName.append("");
	    	whereSql.append("");
	    }
	    
	    List<AccLederCheck> list = new ArrayList<AccLederCheck>();
	    if("".equals(whereSqll.toString())){
	    	entityMap.put("colName", colName.toString());
		    entityMap.put("groupBy", groupBy.toString()); 
		    entityMap.put("whereSql", whereSql.toString());
		    entityMap.put("aWhere", aWhere.toString());
		    list = accNostroMapper.queryAccLederCheckBalanceO(entityMap, rowBounds);
	    }else{
	    	//自定义辅助核算
	    	entityMap.put("whereSql", whereSql.toString());
	    	entityMap.put("whereSqll", whereSqll.toString());
	    	list = accNostroMapper.queryAccLederCheckBalanceZ(entityMap, rowBounds);
	    }
	    
	    PageInfo page = new PageInfo(list);
		return ChdJson.toJson(list, page.getTotal());
		
	}

	/**
	 * 往来初始账 更新页面跳转查询主表
	 */
	@Override
	public AccLederCheck queryAccLederCheckBySubjId(Map<String, Object> entityMap) throws DataAccessException {
		return accNostroMapper.queryAccLederCheckBySubjId(entityMap);
	}
	/**
	 * 往来处理--明细账查询  自定义辅助核算
	 */
	@Override
	public String queryAccDetailAccountAndZItem(Map<String, Object> entityMap) throws DataAccessException {
		SysPage sysPage = new SysPage();
		sysPage = (SysPage) entityMap.get("sysPage");
		
		RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
		List<AccLederCheck> list = accNostroMapper.queryAccDetailAccountAndZItem(entityMap, rowBounds);
		PageInfo page = new PageInfo(list);
		return ChdJson.toJson(list, page.getTotal());
	}
	
	/**
	 * 明细账查询  
	 */
	@Override
	@Transactional(rollbackFor=Exception.class)
	public String collectQueryAccDetailAccount(Map<String, Object> entityMap) throws DataAccessException {
		
		String check_table = "";//辅助核算表
		String column_no = "";//辅助核算字段NO
		String column_id = "";//辅助核算字段ID
		String column_code = "";//辅助核算字段Code
		String column_name = "";//辅助核算字段name
		String column_check = "";//自定义辅助核算项
		String is_change = "";//是否变更表
		
		if(entityMap.get("check_type")!=null && !"".equals(entityMap.get("check_type").toString())){
			//查看当前会计科目挂哪个辅助核算
			Map<String, Object> sqlMap = accNostroMapper.querySubjCheckColumnBySubj(entityMap);
			if(sqlMap == null) {
				return ChdJson.toJsonLower(new ArrayList<>());
			}
			check_table=sqlMap.get("CHECK_TABLE").toString();
			if(sqlMap.containsKey("COLUMN_ID")){
				column_id=sqlMap.get("COLUMN_ID").toString();
			}
			//由于自定义辅助核算项 没有COLUMN_NO
			if(sqlMap.get("COLUMN_NO") != null){
				if(sqlMap.containsKey("COLUMN_NO")){
					column_no=sqlMap.get("COLUMN_NO").toString();
				}
			}
			
			
			column_code=sqlMap.get("COLUMN_CODE").toString();
			column_name=sqlMap.get("COLUMN_NAME").toString();
			column_check = sqlMap.get("COLUMN_CHECK").toString();
			is_change = sqlMap.get("IS_CHANGE").toString();
		}
    
		
		entityMap.put("check_table", check_table);
		entityMap.put("column_no", column_no);
		entityMap.put("column_id", column_id);
		entityMap.put("column_code", column_code);
		entityMap.put("column_name", column_name);
		entityMap.put("column_check", column_check);
		entityMap.put("is_change", is_change);
		
		entityMap.put("rst", new ArrayList<Map<String, Object>>());
		
		accNostroMapper.queryAccDetailAccount(entityMap);
		TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		return ChdJson.toJsonLower((List<Map<String,Object>>)entityMap.get("rst"),Long.parseLong(entityMap.get("tcount").toString()));
		
	}
	
	/**
	 * 核销清册明细查询
	 */
	@Override
	public String queryAccVerificationDetail(Map<String, Object> entityMap) throws DataAccessException{
		SysPage sysPage = new SysPage();		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
		
		StringBuffer whereSql = new StringBuffer();
		StringBuffer whereSqll = new StringBuffer();
		StringBuffer groupBy = new StringBuffer();
		StringBuffer colName = new StringBuffer();
		
		if(entityMap.get("is_check").equals("1")){
			entityMap.put("state", " and av.state > 0 and av.state < 99");
	    }else{
	    	entityMap.put("state", " and av.state = 99");
	    }
		
		if(entityMap.get("check_type")!=null && !"".equals(entityMap.get("check_type").toString())){
			//查看当前会计科目挂哪个辅助核算
			Map<String, Object> sqlMap = accNostroMapper.querySubjCheckColumnBySubj(entityMap);
			if(sqlMap != null) {
				colName.append(" dd.").append(sqlMap.get("COLUMN_CODE")).append(" as check_code, to_char(dd.").append(sqlMap.get("COLUMN_NAME").toString()+")").append(" as check_name,");
				groupBy.append(",dd.").append(sqlMap.get("COLUMN_CODE")).append(",dd.").append(sqlMap.get("COLUMN_NAME"));
				       
				//是否是变更表
				    if(sqlMap.get("CHECK_TABLE")!=null && !"".equals(sqlMap.get("CHECK_TABLE").toString())){
				    	if(sqlMap.get("IS_CHANGE") != null &&  "1".equals(sqlMap.get("IS_CHANGE").toString())){
					    	whereSql.append(" join ").append(sqlMap.get("CHECK_TABLE")).append(" dd ");
							whereSql.append(" on dd.group_id = avc.group_id and dd.hos_id = avc.hos_id  and dd.").append(sqlMap.get("COLUMN_ID")).append("= avc.").append(sqlMap.get("COLUMN_CHECK")).append("_ID");
							whereSql.append(" and dd.").append(sqlMap.get("COLUMN_NO")).append(" = avc.").append(sqlMap.get("COLUMN_CHECK").toString()).append("_NO");
							whereSql.append(" and dd."+sqlMap.get("COLUMN_CODE")+" is not null");
							if(entityMap.get("proj1").equals("")){
								whereSql.append("");
							}else{
								if(entityMap.get("proj2").equals("")){
							    	whereSql.append("");
								}else{
							    	whereSql.append(" and dd.").append(sqlMap.get("COLUMN_ID")).append(" between '" + entityMap.get("proj1") + "' and '" + entityMap.get("proj2")+"' ");
								}
							} 
					 	}else{
					    	//自定义辅助核算
					        if(sqlMap.get("CHECK_TABLE").equals("ACC_CHECK_ITEM")){
					        	whereSql.append(" and aci.").append(sqlMap.get("COLUMN_ID")).append(" = avc.").append(sqlMap.get("COLUMN_CHECK"));
					        	whereSqll.append(" and aci.").append(sqlMap.get("COLUMN_ID")).append(" = alc.").append(sqlMap.get("COLUMN_CHECK"));
					        	if(entityMap.get("proj1").equals("")){
					    		    whereSql.append("");
					    		    whereSqll.append("");
					    		}else{
					    		    if(entityMap.get("proj2").equals("")){
					    		    	whereSql.append("");
					    		    	whereSqll.append("");
					    			}else{
					    				whereSql.append("  and aci.").append(sqlMap.get("COLUMN_ID")).append(" between '" + entityMap.get("proj1") + "' and '" + entityMap.get("proj2")+"' ");
					    		    	whereSqll.append(" and aci.").append(sqlMap.get("COLUMN_ID")).append(" between '" + entityMap.get("proj1") + "' and '" + entityMap.get("proj2")+"' ");
					    			}
					    		} 
					       }else{
					       	//资金来源、单位
					    	whereSql.append("left join ").append(sqlMap.get("CHECK_TABLE")).append(" dd ");
							whereSql.append(" on dd.group_id = avc.group_id and dd.hos_id = avc.hos_id and dd.").append(sqlMap.get("COLUMN_ID")).append("= avc.").append(sqlMap.get("COLUMN_CHECK")).append("_ID");
								if(entityMap.get("proj1").equals("")){
									whereSql.append("");
								}else{
									if(entityMap.get("proj2").equals("")){
									   whereSql.append("");
									}else{
										whereSql.append(" and dd.").append(sqlMap.get("COLUMN_ID")).append(" between '" + entityMap.get("proj1") + "' and '" + entityMap.get("proj2")+"' ");
									}
								} 
					       }
					 	}
				    }
			}
		}else{
	    	groupBy.append("");
	    	colName.append("");
	    	whereSql.append("");
	    }
	    
	    List<AccLederCheck> list = new ArrayList<AccLederCheck>();
	    if("".equals(whereSqll.toString())){
	    	entityMap.put("colName", colName.toString());
		    entityMap.put("groupBy", groupBy.toString()); 
		    entityMap.put("whereSql", whereSql.toString());
		    
		    list = accNostroMapper.queryAccVerificationDetail(entityMap, rowBounds);
	    }else{
	    	//自定义辅助核算
	    	entityMap.put("whereSql", whereSql.toString());
	    	//entityMap.put("whereSqll", whereSqll.toString());
	    	list = accNostroMapper.queryAccVerificationDetailZ(entityMap, rowBounds);
	    }
	    
	    PageInfo page = new PageInfo(list);
		return ChdJson.toJson(list, page.getTotal());
		
	}
	
	/**
	 * 个人往来催款单查询
	 */
	@Override
	public String queryContactsReminderDetail(Map<String, Object> entityMap) throws DataAccessException{	
		List<AccLederCheck> list = accNostroMapper.queryContactsReminderDetail(entityMap);
		return ChdJson.toJson(list);
	}
	
	/**
	 * 往来初始账 
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	@Override
	public String queryCurrentAccount(Map<String, Object> entityMap) throws DataAccessException {
		
        SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<AccSubj> list = accNostroMapper.queryCurrentAccount(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<AccSubj> list = accNostroMapper.queryCurrentAccount(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
	}
	/**
	 * 往来初始校验 明细页面查询
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	@Override
	public String queryCurrentAccountCheckDetail(Map<String, Object> entityMap) throws DataAccessException {
		SysPage sysPage = new SysPage();
		sysPage = (SysPage) entityMap.get("sysPage");
		RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

        
        StringBuffer orderBy = new StringBuffer();//排序sql
        StringBuffer colName = new StringBuffer();//查询列名
        StringBuffer whereSql = new StringBuffer();//连接sql1
        StringBuffer selSql = new StringBuffer();//别名字段
        StringBuffer conntionSql = new StringBuffer();//连接sql2
        
        List<Map<String,Object>> sqlList = accNostroMapper.querySubjCheckColumnBySubjList(entityMap);
       
		StringBuffer groupSql = new StringBuffer();  //group列
		int index = 1;
		String tab = "";
		for(Map<String, Object> sqlMap : sqlList){
			tab = " temp"+index;  //表别名
			String columnName = sqlMap.get("COLUMN_CHECK").toString().toLowerCase();
			colName.append(" ,").append(tab).append(".").append(sqlMap.get("COLUMN_CODE")).append(" ||' '||").append(tab).append(".").append(sqlMap.get("COLUMN_NAME")).append(" as ").append(columnName).append("_str ");
			whereSql.append("left join ").append(sqlMap.get("CHECK_TABLE")).append(tab).append(" on ").append(tab).append(".group_id = alc.group_id and ").append(tab).append(".hos_id = alc.hos_id ");
			orderBy.append(" ,").append(tab).append(".").append(sqlMap.get("COLUMN_CODE")).append(",").append(tab).append(".").append(sqlMap.get("COLUMN_NAME")).append(" " );
			selSql.append(" l_check.").append(columnName).append("_str, ");
			conntionSql.append("and l_check.").append(columnName).append("_str = v_check.").append(columnName).append("_str ");
			
			//是否有变更表
			if(sqlMap.get("IS_CHANGE") != null && "1".equals(sqlMap.get("IS_CHANGE").toString())){
				whereSql.append(" and ").append(tab).append(".").append(sqlMap.get("COLUMN_ID")).append(" = alc.").append(sqlMap.get("COLUMN_CHECK")).append("_ID ");
				whereSql.append(" and ").append(tab).append(".").append(sqlMap.get("COLUMN_NO")).append(" = alc.").append(sqlMap.get("COLUMN_CHECK")).append("_NO ");
			}else{
				if(sqlMap.get("CHECK_TABLE").equals("ACC_CHECK_ITEM")){
					whereSql.append(" and ").append(tab).append(".").append(sqlMap.get("COLUMN_ID")).append(" = alc.").append(sqlMap.get("COLUMN_CHECK"));
				}else{
					whereSql.append(" and ").append(tab).append(".").append(sqlMap.get("COLUMN_ID")).append(" = alc.").append(sqlMap.get("COLUMN_CHECK")).append("_ID ");
				}
			}
			index++;
		}
		
		entityMap.put("colName", colName.toString());
		entityMap.put("whereSql", whereSql.toString());
		entityMap.put("orderBy", orderBy.toString());
		entityMap.put("selSql", selSql.toString());
		entityMap.put("conntionSql", conntionSql.toString());
		
		List<Map<String,Object>> list = JsonListMapUtil.toListMapLower(accNostroMapper.queryCurrentAccountCheckDetail(entityMap, rowBounds));
		
		PageInfo page = new PageInfo(list);
		return ChdJson.toJson(list, page.getTotal());
	}
	
	/**
	 * 往来初始校验 页面查询
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public String queryCurrentAccountCheck(Map<String, Object> entityMap) throws DataAccessException {
		
        SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<AccSubj> list = accNostroMapper.queryCurrentAccountCheck(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<AccSubj> list = accNostroMapper.queryCurrentAccountCheck(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
		

	}
	
	@Override
	public List<Map<String, Object>> queryCurrentAccountPrint(Map<String, Object> entityMap) throws DataAccessException {
		
			List<Map<String, Object>> list = accNostroMapper.queryCurrentAccountPrint(entityMap);
			
			return list;
			
	}
	
	public List<Map<String, Object>> queryCurrentAccountCheckPrint(Map<String, Object> entityMap) throws DataAccessException {
		
			
			List<Map<String, Object>> list = accNostroMapper.queryCurrentAccountCheckPrint(entityMap);
			
			return list;

	}
	
	@Override
	public List<Map<String, Object>> queryAccLederCheckBalanceOPrint(Map<String, Object> entityMap) throws DataAccessException {
		 
		StringBuffer whereSql = new StringBuffer();
		StringBuffer whereSqll = new StringBuffer();
		StringBuffer groupBy = new StringBuffer();
		StringBuffer colName = new StringBuffer();
		StringBuffer aWhere = new StringBuffer();
		
		if(entityMap.get("is_check").equals("1")){
			entityMap.put("state", " and av.state > 0 and av.state < 99");
	    }else{
	    	entityMap.put("state", " and av.state = 99");
	    }
		
		if(entityMap.get("check_type")!=null && !"".equals(entityMap.get("check_type").toString())){
			//查看当前会计科目挂哪个辅助核算
			Map<String, Object>sqlMap = accNostroMapper.querySubjCheckColumnBySubj(entityMap);
			
				colName.append(" dd.").append(sqlMap.get("COLUMN_CODE")).append(" as check_code, dd.").append(sqlMap.get("COLUMN_NAME").toString()).append(" as check_name,");
			    groupBy.append(",dd.").append(sqlMap.get("COLUMN_CODE").toString()).append(",dd.").append(sqlMap.get("COLUMN_NAME").toString());
			    //是否是变更表
			    if(sqlMap.get("CHECK_TABLE")!=null && !"".equals(sqlMap.get("CHECK_TABLE").toString())){
			    	if(sqlMap.get("IS_CHANGE") != null &&  "1".equals(sqlMap.get("IS_CHANGE").toString())){
				    	whereSql.append("left join ").append(sqlMap.get("CHECK_TABLE")).append(" dd ");
						whereSql.append(" on dd.group_id = avc.group_id and dd.hos_id = avc.hos_id  and dd.").append(sqlMap.get("COLUMN_ID")).append("= avc.").append(sqlMap.get("COLUMN_CHECK")).append("_ID").append(" ");
						whereSql.append(" and dd.").append(sqlMap.get("COLUMN_NO")).append(" = avc.").append(sqlMap.get("COLUMN_CHECK").toString()).append("_NO ");
						colName.append(" dd.").append(sqlMap.get("COLUMN_ID")).append(" as checkID,dd.").append(sqlMap.get("COLUMN_NO").toString()).append(" as checkNO,");
						groupBy.append(",dd.").append(sqlMap.get("COLUMN_ID").toString()).append(",dd.").append(sqlMap.get("COLUMN_NO").toString());
						if(entityMap.get("proj1").equals("")){
							whereSql.append("");
						}else{
							if(entityMap.get("proj2").equals("")){
						    	whereSql.append("");
							}else{
						    	whereSql.append(" and dd.").append(sqlMap.get("COLUMN_ID")).append(" between '" + entityMap.get("proj1") + "' and '" + entityMap.get("proj2")+"' ");
							}
						}
						aWhere.append(" and d_check.checkID = l_check.checkID and d_check.checkNO = l_check.checkNO ");
				 	}else{
				    	//自定义辅助核算
				        if(sqlMap.get("CHECK_TABLE").equals("ACC_CHECK_ITEM")){
				        	whereSql.append(" and aci.").append(sqlMap.get("COLUMN_ID")).append(" = avc.").append(sqlMap.get("COLUMN_CHECK")).append(" ");
				        	whereSqll.append(" and aci.").append(sqlMap.get("COLUMN_ID")).append(" = alc.").append(sqlMap.get("COLUMN_CHECK")).append(" ");
				        	colName.append(" aci.").append(sqlMap.get("COLUMN_ID")).append(" as checkID,");
				        	if(entityMap.get("proj1").equals("")){
				    		    whereSql.append("");
				    		    whereSqll.append("");
				    		}else{
				    		    if(entityMap.get("proj2").equals("")){
				    		    	whereSql.append("");
				    		    	whereSqll.append("");
				    			}else{
				    				whereSql.append("  and aci.").append(sqlMap.get("COLUMN_ID")).append(" between '" + entityMap.get("proj1") + "' and '" + entityMap.get("proj2")+"' ");
				    		    	whereSqll.append(" and aci.").append(sqlMap.get("COLUMN_ID")).append(" between '" + entityMap.get("proj1") + "' and '" + entityMap.get("proj2")+"' ");
				    			}
				    		} 
				        	aWhere.append(" and d_check.checkID = l_check.checkId ");
				       }else{
				       	//资金来源、单位
				    	whereSql.append("left join ").append(sqlMap.get("CHECK_TABLE")).append(" dd ");
						whereSql.append(" on dd.group_id = avc.group_id and dd.hos_id = avc.hos_id and dd.").append(sqlMap.get("COLUMN_ID")).append("= avc.").append(sqlMap.get("COLUMN_CHECK")).append("_ID ");
						colName.append(" dd.").append(sqlMap.get("COLUMN_ID")).append(" as checkID,");
						groupBy.append(",dd.").append(sqlMap.get("COLUMN_ID").toString()).append(" ");
						if(entityMap.get("proj1").equals("")){
							whereSql.append("");
						}else{
							if(entityMap.get("proj2").equals("")){
							   whereSql.append("");
							}else{
								whereSql.append(" and dd.").append(sqlMap.get("COLUMN_ID")).append(" between '" + entityMap.get("proj1") + "' and '" + entityMap.get("proj2")+"' ");
							}
						}
						aWhere.append(" and d_check.checkID = l_check.checkId ");
				     }
							
				  }
			    }
		}else{
	    	groupBy.append("");
	    	colName.append("");
	    	whereSql.append("");
	    }
	    
	    List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
	    if("".equals(whereSqll.toString())){
	    	entityMap.put("colName", colName.toString());
		    entityMap.put("groupBy", groupBy.toString()); 
		    entityMap.put("whereSql", whereSql.toString());
		    entityMap.put("aWhere", aWhere.toString());
		    list = accNostroMapper.queryAccLederCheckBalanceOPrint(entityMap);
	    }else{
	    	//自定义辅助核算
	    	entityMap.put("whereSql", whereSql.toString());
	    	entityMap.put("whereSqll", whereSqll.toString());
	    	list = accNostroMapper.queryAccLederCheckBalanceZPrint(entityMap);
	    }
	    
	    
		return list;
		
	}
	
	@Override
	@Transactional(rollbackFor=Exception.class)
	public List<Map<String, Object>> collectQueryAccDetailAccountPrint(Map<String, Object> entityMap) throws DataAccessException {
		
		String check_table = "";//辅助核算表
		String column_no = "";//辅助核算字段NO
		String column_id = "";//辅助核算字段ID
		String column_code = "";//辅助核算字段Code
		String column_name = "";//辅助核算字段name
		String column_check = "";//自定义辅助核算项
		String is_change = "";//是否变更表
		
		if(entityMap.get("check_type")!=null && !"".equals(entityMap.get("check_type").toString())){
			//查看当前会计科目挂哪个辅助核算
			Map<String, Object> sqlMap = accNostroMapper.querySubjCheckColumnBySubj(entityMap);
			
				check_table=sqlMap.get("CHECK_TABLE").toString();
				if(sqlMap.containsKey("COLUMN_ID")){
					column_id=sqlMap.get("COLUMN_ID").toString();
				}
				if(sqlMap.containsKey("COLUMN_NO")&&null!=sqlMap.get("COLUMN_NO")){
					column_no=sqlMap.get("COLUMN_NO").toString();
				}
				
				column_code=sqlMap.get("COLUMN_CODE").toString();
				column_name=sqlMap.get("COLUMN_NAME").toString();
				column_check = sqlMap.get("COLUMN_CHECK").toString();
				is_change = sqlMap.get("IS_CHANGE").toString();
			}
	    
		
		entityMap.put("check_table", check_table);
		entityMap.put("column_no", column_no);
		entityMap.put("column_id", column_id);
		entityMap.put("column_code", column_code);
		entityMap.put("column_name", column_name);
		entityMap.put("column_check", column_check);
		entityMap.put("is_change", is_change);
		
		entityMap.put("rst", new ArrayList<Map<String, Object>>());
		accNostroMapper.queryAccDetailAccount(entityMap);	
		
		TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		
		return (List<Map<String, Object>>) entityMap.get("rst");
	}
	
	@Override
	public List<Map<String, Object>> queryAccVerificationDetailPrint(Map<String, Object> entityMap) throws DataAccessException{
		
		StringBuffer whereSql = new StringBuffer();
		StringBuffer whereSqll = new StringBuffer();
		StringBuffer groupBy = new StringBuffer();
		StringBuffer colName = new StringBuffer();
		
		if(entityMap.get("is_check").equals("1")){
			entityMap.put("state", " and av.state > 0 and av.state < 99");
	    }else{
	    	entityMap.put("state", " and av.state = 99");
	    }
		
		if(entityMap.get("check_type")!=null && !"".equals(entityMap.get("check_type").toString())){
			//查看当前会计科目挂哪个辅助核算
			Map<String, Object> sqlMap = accNostroMapper.querySubjCheckColumnBySubj(entityMap);
			colName.append(" dd.").append(sqlMap.get("COLUMN_CODE")).append(" as check_code, to_char(dd.").append(sqlMap.get("COLUMN_NAME").toString()+")").append(" as check_name,");
			groupBy.append(",dd.").append(sqlMap.get("COLUMN_CODE")).append(",dd.").append(sqlMap.get("COLUMN_NAME"));
			       
			//是否是变更表
			    if(sqlMap.get("CHECK_TABLE")!=null && !"".equals(sqlMap.get("CHECK_TABLE").toString())){
			    	if(sqlMap.get("IS_CHANGE") != null &&  "1".equals(sqlMap.get("IS_CHANGE").toString())){
				    	whereSql.append("left join ").append(sqlMap.get("CHECK_TABLE")).append(" dd ");
						whereSql.append(" on dd.group_id = avc.group_id and dd.hos_id = avc.hos_id  and dd.").append(sqlMap.get("COLUMN_ID")).append("= avc.").append(sqlMap.get("COLUMN_CHECK")).append("_ID");
						whereSql.append(" and dd.").append(sqlMap.get("COLUMN_NO")).append(" = avc.").append(sqlMap.get("COLUMN_CHECK").toString()).append("_NO");
						if(entityMap.get("proj1").equals("")){
							whereSql.append("");
						}else{
							if(entityMap.get("proj2").equals("")){
						    	whereSql.append("");
							}else{
						    	whereSql.append(" and dd.").append(sqlMap.get("COLUMN_ID")).append(" between '" + entityMap.get("proj1") + "' and '" + entityMap.get("proj2")+"' ");
							}
						} 
				 	}else{
				    	//自定义辅助核算
				        if(sqlMap.get("CHECK_TABLE").equals("ACC_CHECK_ITEM")){
				        	whereSql.append(" and aci.").append(sqlMap.get("COLUMN_ID")).append(" = avc.").append(sqlMap.get("COLUMN_CHECK"));
				        	whereSqll.append(" and aci.").append(sqlMap.get("COLUMN_ID")).append(" = alc.").append(sqlMap.get("COLUMN_CHECK"));
				        	if(entityMap.get("proj1").equals("")){
				    		    whereSql.append("");
				    		    whereSqll.append("");
				    		}else{
				    		    if(entityMap.get("proj2").equals("")){
				    		    	whereSql.append("");
				    		    	whereSqll.append("");
				    			}else{
				    				whereSql.append("  and aci.").append(sqlMap.get("COLUMN_ID")).append(" between '" + entityMap.get("proj1") + "' and '" + entityMap.get("proj2")+"' ");
				    		    	whereSqll.append(" and aci.").append(sqlMap.get("COLUMN_ID")).append(" between '" + entityMap.get("proj1") + "' and '" + entityMap.get("proj2")+"' ");
				    			}
				    		} 
				       }else{
				       	//资金来源、单位
				    	whereSql.append("left join ").append(sqlMap.get("CHECK_TABLE")).append(" dd ");
						whereSql.append(" on dd.group_id = avc.group_id and dd.hos_id = avc.hos_id and dd.").append(sqlMap.get("COLUMN_ID")).append("= avc.").append(sqlMap.get("COLUMN_CHECK")).append("_ID");
						if(entityMap.get("proj1").equals("")){
							whereSql.append("");
						}else{
							if(entityMap.get("proj2").equals("")){
							   whereSql.append("");
							}else{
								whereSql.append(" and dd.").append(sqlMap.get("COLUMN_ID")).append(" between '" + entityMap.get("proj1") + "' and '" + entityMap.get("proj2")+"' ");
							}
						} 
				     }
							
				  }
			    }
			
		}else{
	    	groupBy.append("");
	    	colName.append("");
	    	whereSql.append("");
	    }
	    
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		
	    if("".equals(whereSqll.toString())){
	    	
	    	entityMap.put("colName", colName.toString());
	    	
		    entityMap.put("groupBy", groupBy.toString()); 
		    
		    entityMap.put("whereSql", whereSql.toString());
		    
		    list = accNostroMapper.queryAccVerificationDetailPrint(entityMap);
		    
	    }else{
	    	//自定义辅助核算
	    	entityMap.put("whereSql", whereSql.toString());
	    	//entityMap.put("whereSqll", whereSqll.toString());
	    	list = accNostroMapper.queryAccVerificationDetailZPrint(entityMap);
	    }
	    
		return list;
		
	}
	
	@Override
	public List<Map<String, Object>> queryContactsReminderDetailPrint(Map<String, Object> entityMap) throws DataAccessException{	
		
		List<Map<String, Object>> list = accNostroMapper.queryContactsReminderDetailPrint(entityMap);
		
		return list;
	}
	
	
	
	public String readFilesImport(Map<String, Object> mapVo, Map<String, Object> table) {
		
		List<Map<String, List<String>>> list = null;
		try {
			list = ReadFiles.readFiles(mapVo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (list.size() == 0 || list == null) {
			return "{\"error\":\"表头或者数据为空！请重新导入.\"}";
		}
//		System.out.println("/*********************************");
//		System.out.println("mapVo::"+mapVo);
//		System.out.println("table::"+table);
//		System.out.println("list::"+list);
//		System.out.println("/*********************************");

		
		
		StringBuffer sql = new StringBuffer();//拼接插入数据的列名
		
		List<Map<String, Object>> list_err = new ArrayList<Map<String, Object>>();
		try {
			
//			String [] data = entityMap.get("check_data").toString().split(",");
			
			String para = mapVo.get("para").toString();
			JSONObject JSONOpara = JSONObject.parseObject(para);
			
			//循环行数，对每一行进行解析。
			for(int i=0;i<list.size();i++){
				sql.setLength(0);
				
				StringBuffer err_sb = new StringBuffer();
				
				StringBuffer sqlValue = new StringBuffer();//拼接插入数据的列值
				
				Map<String, Object> map = new HashMap<String, Object>();
				
				Map<String, List<String>> hangmap = list.get(i);
				
				Map<String, Object> mapVoo = new HashMap<String, Object>();
				
				map.put("group_id", mapVo.get("group_id"));
				map.put("hos_id", mapVo.get("hos_id"));
				map.put("copy_code", mapVo.get("copy_code"));
				map.put("acc_year", mapVo.get("acc_year"));
				map.put("subj_code", mapVo.get("subj_code"));
				map.put("vouch_check_ids", UUIDLong.absStringUUID());
				map.put("vouch_detail_id","");
				map.put("vouch_id","");
				
				
				mapVoo.put("group_id", mapVo.get("group_id"));
				mapVoo.put("hos_id", mapVo.get("hos_id"));
				mapVoo.put("copy_code", mapVo.get("copy_code"));
				
				
				String startsj = list.get(i).get("year").get(1);
				String endsj = list.get(i).get("month").get(1);
//				System.out.println("开始日期！：："+DateUtil.stringToDate(startsj,"yyyy-MM-dd"));
//				System.out.println("结束日期！：："+DateUtil.stringToDate(endsj,"yyyy-MM-dd"));
				//前段页面有校验 省略
				if("0".equals(mapVo.get("subj_dire"))){ //store_name
					map.put("debit", list.get(i).get("stocker_name").get(1));					
					
					map.put("credit", 0);
							
					mapVoo.put("money", list.get(i).get("stocker_name").get(1));				

					
				}else {
					map.put("debit", 0);
					map.put("credit", list.get(i).get("stocker_name").get(1));
					mapVoo.put("money", list.get(i).get("stocker_name").get(1));
				}
				
				if(StringTool.isNotBlank(list.get(i).get("store_name").get(1))){
					
					mapVoo.put("business_no", list.get(i).get("store_name").get(1));		
						
					map.put("business_no", list.get(i).get("store_name").get(1));			
					
				 }else {
					 
					 mapVoo.put("business_no", "");
					 map.put("business_no", "");
					
				 }
				
				if (StringTool.isNotBlank(list.get(i).get("sup_name").get(1))) {
					mapVoo.put("con_no", list.get(i).get("sup_name").get(1));
					map.put("con_no", list.get(i).get("sup_name").get(1));
				}else {
					mapVoo.put("con_no", "");
					map.put("con_no", "");

				}
				if (StringTool.isNotBlank(list.get(i).get("month").get(1))) {
					mapVoo.put("due_date", list.get(i).get("month").get(1));
					map.put("due_date", list.get(i).get("month").get(1));
				}else {
					mapVoo.put("due_date","");
					map.put("due_date","");
				}
				mapVoo.put("occur_date", list.get(i).get("year").get(1));
				map.put("occur_date", list.get(i).get("year").get(1));
				if (StringTool.isNotBlank(list.get(i).get("summary").get(1))) {
					mapVoo.put("summary", list.get(i).get("summary").get(1));
					map.put("summary", list.get(i).get("summary").get(1));
				}else {
					mapVoo.put("summary", "");
					map.put("summary", "");
				}
				
				map.put("sqlValue", sqlValue);
//				list_err.add(map);
				
				
				//
				if (StringTool.isNotBlank(list.get(i).get("column_code_value1"))) {
					String column_code_value1 = list.get(i).get("column_code_value1").get(1);
					
					mapVoo.put("column_code_value", column_code_value1.trim());
					map.put("check1_code", column_code_value1.trim());
					if(StringTool.isNotBlank(list.get(i).get("column_name_value1").get(1))){
						map.put("check1_name", list.get(i).get("column_name_value1").get(1));
					}
					mapVoo.put("column_code", table.get("COLUMN1_CODE"));
					mapVoo.put("column_name", table.get("COLUMN1_NAME"));
					mapVoo.put("column_id", table.get("COLUMN1_ID"));
					mapVoo.put("column_no", table.get("COLUMN1_NO"));
					mapVoo.put("query_table", table.get("CHECK1_TABLE"));
					if(table.get("CHECK1_TABLE").toString().toLowerCase().indexOf("acc_check_item")>-1){
						mapVoo.put("check_type_id", table.get("CHECK1_TYPE_ID"));
						
						Map<String, Object> checkItem= accLederMapper.queryAccCheckItem(mapVoo);
						
						if (checkItem==null) {
							err_sb.append("第"+(i+1)+"行"+table.get("CHECK1_NAME")+"编码不存在，请核查！");
						}else {
							
							sqlValue.append(checkItem.get(table.get("COLUMN1_ID"))+",");
							
							sql.append(table.get("COLUMN1_CHECK").toString()+",");
						}
						
						
					}else {
						
//						System.out.println(mapVoo);
						Map<String, Object> checkItem= accLederMapper.queryAccCheckItem(mapVoo);
						if(checkItem != null){
							
							sqlValue.append(checkItem.get(table.get("COLUMN1_ID"))+",");
							
							sqlValue.append(checkItem.get(table.get("COLUMN1_NO"))+",");
							//用于动态的获取辅助核算
							 String item =(String) table.get("COLUMN1_CHECK") ;
							 
								sql.append(item+"_id,");
								
								sql.append(item+"_no,");

							
								mapVoo.put("check_type_id", "");
							
						}else{
							
							err_sb.append("第"+(i+1)+"行"+table.get("CHECK1_NAME")+"编码不存在  ");
						}
					}
					
				}else {
					
					System.out.println("column_code_value1:"+"为空");

				}
				
				//
				if (StringTool.isNotBlank(list.get(i).get("column_code_value2"))) {
					String column_code_value2 = list.get(i).get("column_code_value2").get(1);
					
					mapVoo.put("column_code_value", column_code_value2);
					
					map.put("check2_code", column_code_value2);
					
					if(StringTool.isNotBlank(list.get(i).get("column_code_value2").get(1))){
						
						map.put("check2_name", list.get(i).get("column_code_value2").get(1));
						
					}
					
					mapVoo.put("column_code", table.get("COLUMN2_CODE").toString().trim());
					
					mapVoo.put("column_name", table.get("COLUMN2_NAME"));
					
					mapVoo.put("column_id", table.get("COLUMN2_ID"));
					
					mapVoo.put("column_no", table.get("COLUMN2_NO"));
					
					mapVoo.put("query_table", table.get("CHECK2_TABLE"));
					
					if(table.get("CHECK2_TABLE").toString().toLowerCase().indexOf("acc_check_item")>-1){
						
						mapVoo.put("check_type_id", table.get("CHECK2_TYPE_ID"));
						
						Map<String, Object> checkItem= accLederMapper.queryAccCheckItem(mapVoo);
						
						if (checkItem==null) {
							err_sb.append("第"+(i+1)+"行"+table.get("CHECK2_NAME")+"编码不存在，请核查！");
						}else {
							sqlValue.append(checkItem.get(table.get("COLUMN2_ID"))+",");
							
							sql.append(table.get("COLUMN2_CHECK").toString()+",");
							
						}
						
						//map.put(table.get("COLUMN2_CHECK").toString(), checkItem.get(table.get("COLUMN2_ID")));
						
						
						
					}else {
						
						Map<String, Object> checkItem= accLederMapper.queryAccCheckItem(mapVoo);
						
						/*map.put("check2_id", checkItem.get(table.get("COLUMN2_ID")));
						
						map.put("check2_no", checkItem.get(table.get("COLUMN2_NO")));*/
						
						if(checkItem != null){
							
							sqlValue.append(checkItem.get(table.get("COLUMN2_ID"))+",");
							
							sqlValue.append(checkItem.get(table.get("COLUMN2_NO")) +",");
							
							//用于动态的获取辅助核算
							 String item =(String) table.get("COLUMN2_CHECK") ;
							 
								sql.append(item+"_id,");
								
								sql.append(item+"_no,");

							
								mapVoo.put("check_type_id", "");
						
							
							}else{
								
								err_sb.append("第"+(i+1)+"行"+table.get("CHECK2_NAME")+"编码不存在  ");
							}
						
					}
					
				}else {
					
					System.out.println("column_code_value2:"+"为空");
				}
				
				
				
				//
				if (StringTool.isNotBlank(list.get(i).get("column_code_value3"))) {
					String column_code_value3 = list.get(i).get("column_code_value3").get(1);
					
					mapVoo.put("column_code_value", column_code_value3);
					
					map.put("check3_code", column_code_value3);
					
					if(StringTool.isNotBlank(list.get(i).get("column_name_value3"))){
						
						map.put("check3_name", list.get(i).get("column_name_value3").get(1));
						
					}
					
					mapVoo.put("column_code", table.get("COLUMN3_CODE"));
					
					mapVoo.put("column_name", table.get("COLUMN3_NAME"));
					
					mapVoo.put("column_id", table.get("COLUMN3_ID"));
					
					mapVoo.put("column_no", table.get("COLUMN3_NO"));
					
					mapVoo.put("query_table", table.get("CHECK3_TABLE"));
					
					if(table.get("CHECK3_TABLE").toString().toLowerCase().indexOf("acc_check_item")>-1){
						
						mapVoo.put("check_type_id", table.get("CHECK3_TYPE_ID"));
						
						Map<String, Object> checkItem= accLederMapper.queryAccCheckItem(mapVoo);
						
						if (checkItem==null) {
							err_sb.append("第"+(i+1)+"行"+table.get("CHECK3_NAME")+"编码不存在，请核查！");
						}else {
							sqlValue.append(checkItem.get(table.get("COLUMN3_ID"))+",");
							
							sql.append(table.get("COLUMN3_CHECK").toString()+",");
							
						}
						
						//map.put(table.get("COLUMN3_CHECK").toString(), checkItem.get(table.get("COLUMN3_ID")));
						
						
						
					}else{
						
						Map<String, Object> checkItem= accLederMapper.queryAccCheckItem(mapVo);
						
						/*map.put("check3_id", checkItem.get(table.get("COLUMN3_ID")));
						
						map.put("check3_no", checkItem.get(table.get("COLUMN3_NO")));*/
						
						if(checkItem != null){
							
							sqlValue.append(checkItem.get(table.get("COLUMN3_ID"))+",");
							
							sqlValue.append(checkItem.get(table.get("COLUMN3_NO"))+",");
							
							//用于动态的获取辅助核算
							 String item =(String) table.get("COLUMN3_CHECK") ;
							 
								sql.append(item+"_id,");
								
								sql.append(item+"_no,");

							
							
							mapVo.put("check_type_id", "");
							
						}else{
							
							err_sb.append("第"+(i+1)+"行"+table.get("CHECK3_NAME")+"编码不存在  ");
						}
						
					}
					
					
				}else {
					
					System.out.println("column_code_value3:"+"为空");
				}
				
				
				//
				if (StringTool.isNotBlank(list.get(i).get("column_code_value4"))) {
					
					String column_code_value4 = list.get(i).get("column_code_value4").get(1);
					
					mapVoo.put("column_code_value", column_code_value4);
					
					map.put("check4_code", column_code_value4);
					
					if(StringTool.isNotBlank(list.get(i).get("column_code_value4"))){
						
						map.put("check4_name", list.get(i).get("column_code_value4").get(1));
						
					}
					
					mapVoo.put("column_code", table.get("COLUMN4_CODE"));
					
					mapVoo.put("column_name", table.get("COLUMN4_NAME"));
					
					mapVoo.put("column_id", table.get("COLUMN4_ID"));
					
					mapVoo.put("column_no", table.get("COLUMN4_NO"));
					
					mapVoo.put("query_table", table.get("CHECK4_TABLE"));
					
					if(table.get("CHECK4_TABLE").toString().toLowerCase().indexOf("acc_check_item")>-1){
						
						mapVoo.put("check_type_id", table.get("CHECK4_TYPE_ID"));
						
						Map<String, Object> checkItem= accLederMapper.queryAccCheckItem(mapVoo);
						
						if (checkItem==null) {
							err_sb.append("第"+(i+1)+"行"+table.get("CHECK4_NAME")+"编码不存在，请核查！");
						}else {
							
							sqlValue.append(checkItem.get(table.get("COLUMN4_ID"))+",");
							
							sql.append(table.get("COLUMN4_CHECK").toString()+",");
						}
						
						//map.put(table.get("COLUMN4_CHECK").toString(), checkItem.get(table.get("COLUMN4_ID")));
						
						
						
					}else{
						
						Map<String, Object> checkItem= accLederMapper.queryAccCheckItem(mapVoo);
						
						/*map.put("check4_id", checkItem.get(table.get("COLUMN4_ID")));
						
						map.put("check4_no", checkItem.get(table.get("COLUMN4_NO")));*/
						
						if(checkItem != null){
							
							sqlValue.append(checkItem.get(table.get("COLUMN4_ID"))+",");
							
							sqlValue.append(checkItem.get(table.get("COLUMN4_NO"))+",");
							
							
							//用于动态的获取辅助核算
								
							 String item =(String) table.get("COLUMN4_CHECK") ;
							 
							 sql.append(item+"_id,");
								
							 sql.append(item+"_no,");
							
							mapVo.put("check_type_id", "");
							
						}else{
							
							err_sb.append("第"+(i+1)+"行"+table.get("CHECK4_NAME")+"编码不存在  ");
						}
						
					}
				}else {
					
					System.out.println("column_code_value4:"+"为空");
				}
				map.put("error_type",err_sb.toString());
				mapVoo.put("sql", sql);
				list_err.add(map);
				
				
			}
			
			mapVo.put("itemList", list_err);
			
			String error_type = "";
			
			mapVo.put("sql", sql);
			for (Map<String, Object> map2 : list_err) {
				error_type+=map2.get("error_type").toString();
			}
			if("".equals(error_type)){
				
//				accVouchCheckService.importAccVouchCheck(mapVo);
				//accVouchCheckMapper.deleteAccVouchCheck(mapVo);
				
				accVouchCheckMapper.addBatchAccVouchCheckImport(mapVo);
				
				
				list_err.clear();//导入成功，删除list 中的数据
				
			}else {
				
				return "{\"error\":\""+error_type+"\"}";
				
			}
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";
			
		} catch (Exception e) {
			
			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码 addAccVouchCheckByNostro\"}";

		}
	}
}
