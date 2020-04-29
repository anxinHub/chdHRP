package com.chd.hrp.mat.serviceImpl.account.report.outCategoryCount;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.chd.base.MyConfig;
import com.chd.base.SessionManager;
import com.chd.base.SysPage;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.hrp.mat.dao.account.report.outCategoryCount.MatAccountReportDeptTypeCountMapper;
import com.chd.hrp.mat.service.account.report.outCategoryCount.MatAccountReportDeptTypeCountService;
import com.github.pagehelper.PageInfo;

/**
 * @Description:
 * 出库分类统计:科室类型统计-查询表
 * @Table: 无针对表
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */

@Service("matAccountReportDeptTypeCountService")
public class MatAccountReportDeptTypeCountServiceImpl implements MatAccountReportDeptTypeCountService {
	private static Logger logger = Logger.getLogger(MatAccountReportDeptCountServiceImpl.class);
	
	@Resource(name = "matAccountReportDeptTypeCountMapper")
	private final MatAccountReportDeptTypeCountMapper matAccountReportDeptTypeCountMapper = null;
	
	@Override
	public String queryMatAccountReportDeptTypeCount(Map<String, Object> entityMap) throws DataAccessException {
		
		String[] column=entityMap.get("column_name").toString().split(",");
		
		if(column==null || column.length==0){
			
			return "{\"error\":\"表头为空\"}";
		}
		
		if(entityMap.get("type_level") == null || "".equals(entityMap.get("type_level"))){
			
			return "{\"error\":\"类别级次不能为空\"}";
		}
		
		if(entityMap.get("dept_level") == null || "".equals(entityMap.get("dept_level"))){
			
			return "{\"error\":\"科室级次不能为空\"}";
		}
		
		//拼接行转列SQL
		StringBuffer columnSql=new StringBuffer();
		
		//拼接查询字段、合计列SQL
		StringBuffer columnSql2=new StringBuffer();
		
		//合计金额
		StringBuffer columnSql2_1=new StringBuffer();
		//合计数量
		StringBuffer columnSql2_2=new StringBuffer();
		
		columnSql2_1.append(",");
		
		columnSql2_2.append(",");
		//拼接小计、总计SQL
		StringBuffer columnSql3=new StringBuffer();
		
		if(entityMap.get("mat_type_id") != null && !"".equals(entityMap.get("mat_type_id"))){//如果选中物资类别,查当前物资类别
			
			for(String s :column){
				/*
				行转列格式:
				sum(decode(temp.mat_type_code,'0101',nvl(temp.amount_money,0))) as m1,
                sum(decode(temp.mat_type_code,'0101',nvl(temp.amount,0))) as n1,
                sum(decode(temp.mat_type_code,'0104',nvl(temp.amount_money,0))) as m2,
                sum(decode(temp.mat_type_code,'0104',nvl(temp.amount,0))) as n2*/
				//行转列
				columnSql.append(",sum(decode(temp.mat_type_code,"+s+",nvl(temp.amount_money,0))) as m_"+s+" ");
				columnSql.append(",sum(decode(temp.mat_type_code,"+s+",nvl(temp.amount,0))) as n_"+s+" ");
				
				/*
				查询字段、合计列格式
				sum(temp2.m1),
		        sum(temp2.n1),
		        sum(temp2.m2),
		        sum(temp2.n2),
				sum(nvl(temp2.m1,0))+sum(nvl(temp2.m2,0)) as money_count,
		        sum(nvl(temp2.n1,0))+sum(nvl(temp2.n2,0)) as amount_count
		        */
				//查询字段、合计列
				columnSql2.append(",sum(temp2.m_" + s + ") as m_"+ s);
				columnSql2.append(",sum(temp2.n_" + s + ") as n_"+ s);
				
				/*columnSql2.append(",sum(nvl(temp2.m_"+s+",0)) as m_"+s+"_money_count");
				columnSql2.append(",sum(nvl(temp2.n_"+s+",0)) as n_"+s+"_amount_count");*/
				
				//合计列
				columnSql2_1.append("sum(nvl(temp2.m_"+s+",0))+");
				columnSql2_2.append("sum(nvl(temp2.n_"+s+",0))+");
				
				 /*
				  小计、总计格式
				 sum(nvl(a.m1,0)) as m1,
		         sum(nvl(a.n1,0)) as n1,
		         */
				columnSql3.append(",sum(nvl(a.m_"+s+",0)) as m_"+s);
				columnSql3.append(",sum(nvl(a.n_"+s+",0)) as n_"+s);
				
				
			}
			
			columnSql2_1.deleteCharAt(columnSql2_1.length()-1);
			
			columnSql2_1.append("as money_count ");
			
			columnSql2.append(columnSql2_1);
			
			columnSql2_2.deleteCharAt(columnSql2_2.length()-1);
			
			columnSql2_2.append("as amount_count ");
			
			columnSql2.append(columnSql2_2);
			
			entityMap.put("column_sql", columnSql.toString());
			
			entityMap.put("column_sql2", columnSql2.toString());
			
			entityMap.put("column_sql3", columnSql3.toString());
			
		}else{//如果不选中,查所有物资类别
			
			for(String s :column){
				/*
				行转列格式
				sum(decode(temp.mat_type_code,'0101',nvl(temp.amount_money,0))) as m1,
                sum(decode(temp.mat_type_code,'0101',nvl(temp.amount,0))) as n1,
                sum(decode(temp.mat_type_code,'0104',nvl(temp.amount_money,0))) as m2,
                sum(decode(temp.mat_type_code,'0104',nvl(temp.amount,0))) as n2
                */
				columnSql.append(",sum(decode(temp.mat_type_code,"+s+",nvl(temp.amount_money,0))) as m_"+s+" ");
				columnSql.append(",sum(decode(temp.mat_type_code,"+s+",nvl(temp.amount,0))) as n_"+s+" ");
				
				/*
				查询字段、合计列格式
				sum(temp2.m1) as m1,
		        sum(temp2.n1) as n1,
		        sum(temp2.m2) as m2,
		        sum(temp2.n2) as n2,
				*/
				//查询字段
				columnSql2.append(",sum(temp2.m_" + s + ") as m_" + s);
				columnSql2.append(",sum(temp2.n_"+ s + ") as n_" + s);
				
				/*
				合计列格式
				sum(nvl(temp2.m1,0))+sum(nvl(temp2.m2,0)) as money_count,
		        sum(nvl(temp2.n1,0))+sum(nvl(temp2.n2,0)) as amount_count
		        */
				//合计列
				columnSql2_1.append("sum(nvl(temp2.m_"+s+",0))+");
				columnSql2_2.append("sum(nvl(temp2.n_"+s+",0))+");
				
				
				/* 
				 小计、总计 查询字段
				 sum(nvl(a.m1,0)) as m1,
		         sum(nvl(a.n1,0)) as n1,
		         sum（nvl(a.m2,0)) as m2,
		         sum(nvl(a.n2,0)) as n2,
		         */
				columnSql3.append(",sum(nvl(a.m_"+s+",0)) as m_"+s);
				columnSql3.append(",sum(nvl(a.n_"+s+",0)) as n_"+s);
				
			}
			
			columnSql2_1.deleteCharAt(columnSql2_1.length()-1);
			
			columnSql2_1.append("as money_count ");
			
			columnSql2.append(columnSql2_1);
			
			columnSql2_2.deleteCharAt(columnSql2_2.length()-1);
			
			columnSql2_2.append("as amount_count ");
			
			columnSql2.append(columnSql2_2);
			
			entityMap.put("column_sql", columnSql.toString());
			
			entityMap.put("column_sql2", columnSql2.toString());
			
			entityMap.put("column_sql3", columnSql3.toString());
		}
		try{
			
			SysPage sysPage = new SysPage();
			
			sysPage = (SysPage) entityMap.get("sysPage");
			
			if (sysPage.getTotal()==-1){
				
				List<Map<String, Object>> list = matAccountReportDeptTypeCountMapper.queryMatAccountReportDeptTypeCount(entityMap);
				
				return ChdJson.toJsonLower(list);
				
			}else{
				
				RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
				
				List<Map<String, Object>> list = matAccountReportDeptTypeCountMapper.queryMatAccountReportDeptTypeCount(entityMap, rowBounds);
				
				PageInfo page = new PageInfo(list);
				
				return ChdJson.toJsonLower(list, page.getTotal());
			}
			
		}catch (Exception e) {
			
			logger.error(e.getMessage(), e);
			
			throw new SysException(e.getMessage());
		}
	}

	//科室类型统计:按物资类别查询动态表头
	@Override
	public String queryDeptTypeCountHead(Map<String, Object> entityMap)throws DataAccessException {
		try{
			
			//return ChdJson.toJsonLower(matAccountReportDeptCountMapper.queryDeptCountHead(entityMap));
			
			return ChdJson.toJsonLower(matAccountReportDeptTypeCountMapper.queryDeptTypeCountHead(entityMap));
			
		}catch (Exception e) {
			
			logger.error(e.getMessage(), e);
			
			throw new SysException(e.getMessage());

		}
	}
	
	//科室类型统计:按物资类别查询动态表头New
	@Override
	public String queryDeptTypeCountHeadNew(Map<String, Object> entityMap)throws DataAccessException {
		try{
			if(entityMap.get("mat_type_id") != null && entityMap.get("mat_type_id") != "" && !"".equals(entityMap.get("mat_type_id"))){
				String str = "";
				List<Map<String, Object>> listMap = matAccountReportDeptTypeCountMapper.queryDeptTypeCountHeadByCode(entityMap);
				for(Map<String, Object> map :listMap){
					 str = (String) map.get("MAT_TYPE_CODE");
				}
				entityMap.put("mat_type_code",str.substring(0,2));
			}
			
			return ChdJson.toJsonLower(matAccountReportDeptTypeCountMapper.queryDeptTypeCountHeadNew(entityMap));
			
		}catch (Exception e) {
			
			logger.error(e.getMessage(), e);
			
			throw new SysException(e.getMessage());

		}
	}
	//科室类型统计:发生过业务的物资类别
	@Override
	public String queryOccurDeptTypeHead(Map<String, Object> entityMap)throws DataAccessException {
		return ChdJson.toJsonLower(matAccountReportDeptTypeCountMapper.queryOccurDeptTypeHead(entityMap));
	}
	@Override
	public List<Map<String, Object>> queryMatAccountReportDeptTypeCountPrint(Map<String, Object> entityMap)	throws DataAccessException {
		
		entityMap.put("group_id", SessionManager.getGroupId());
		entityMap.put("hos_id", SessionManager.getHosId());
		entityMap.put("copy_code", SessionManager.getCopyCode());
		entityMap.put("user_id", SessionManager.getUserId());
		entityMap.put("show_history", MyConfig.getSysPara("03001"));
		String[] column=entityMap.get("column_name").toString().split(",");
		//拼接行转列SQL
		StringBuffer columnSql=new StringBuffer();
		
		//拼接查询字段、合计列SQL
		StringBuffer columnSql2=new StringBuffer();
		
		//合计金额
		StringBuffer columnSql2_1=new StringBuffer();
		//合计数量
		StringBuffer columnSql2_2=new StringBuffer();
		
		columnSql2_1.append(",");
		
		columnSql2_2.append(",");
		//拼接小计、总计SQL
		StringBuffer columnSql3=new StringBuffer();
		
		if(entityMap.get("mat_type_id") != null && !"".equals(entityMap.get("mat_type_id"))){//如果选中物资类别,查当前物资类别
			
			for(String s :column){
				/*
				行转列格式:
				sum(decode(temp.mat_type_code,'0101',nvl(temp.amount_money,0))) as m1,
                sum(decode(temp.mat_type_code,'0101',nvl(temp.amount,0))) as n1,
                sum(decode(temp.mat_type_code,'0104',nvl(temp.amount_money,0))) as m2,
                sum(decode(temp.mat_type_code,'0104',nvl(temp.amount,0))) as n2*/
				//行转列
				columnSql.append(",sum(decode(temp.mat_type_code,"+s+",nvl(temp.amount_money,0))) as m_"+s+" ");
				columnSql.append(",sum(decode(temp.mat_type_code,"+s+",nvl(temp.amount,0))) as n_"+s+" ");
				
				/*
				查询字段、合计列格式
				sum(temp2.m1),
		        sum(temp2.n1),
		        sum(temp2.m2),
		        sum(temp2.n2),
				sum(nvl(temp2.m1,0))+sum(nvl(temp2.m2,0)) as money_count,
		        sum(nvl(temp2.n1,0))+sum(nvl(temp2.n2,0)) as amount_count
		        */
				//查询字段、合计列
				columnSql2.append(",sum(temp2.m_" + s + ") as m_"+ s);
				columnSql2.append(",sum(temp2.n_" + s + ") as n_"+ s);
				
				/*columnSql2.append(",sum(nvl(temp2.m_"+s+",0)) as m_"+s+"_money_count");
				columnSql2.append(",sum(nvl(temp2.n_"+s+",0)) as n_"+s+"_amount_count");*/
				
				//合计列
				columnSql2_1.append("sum(nvl(temp2.m_"+s+",0))+");
				columnSql2_2.append("sum(nvl(temp2.n_"+s+",0))+");
				
				 /*
				  小计、总计格式
				 sum(nvl(a.m1,0)) as m1,
		         sum(nvl(a.n1,0)) as n1,
		         */
				columnSql3.append(",sum(nvl(a.m_"+s+",0)) as m_"+s);
				columnSql3.append(",sum(nvl(a.n_"+s+",0)) as n_"+s);
				
				
			}
			
			columnSql2_1.deleteCharAt(columnSql2_1.length()-1);
			
			columnSql2_1.append("as money_count ");
			
			columnSql2.append(columnSql2_1);
			
			columnSql2_2.deleteCharAt(columnSql2_2.length()-1);
			
			columnSql2_2.append("as amount_count ");
			
			columnSql2.append(columnSql2_2);
			
			entityMap.put("column_sql", columnSql.toString());
			
			entityMap.put("column_sql2", columnSql2.toString());
			
			entityMap.put("column_sql3", columnSql3.toString());
			
		}else{//如果不选中,查所有物资类别
			
			for(String s :column){
				/*
				行转列格式
				sum(decode(temp.mat_type_code,'0101',nvl(temp.amount_money,0))) as m1,
                sum(decode(temp.mat_type_code,'0101',nvl(temp.amount,0))) as n1,
                sum(decode(temp.mat_type_code,'0104',nvl(temp.amount_money,0))) as m2,
                sum(decode(temp.mat_type_code,'0104',nvl(temp.amount,0))) as n2
                */
				columnSql.append(",sum(decode(temp.mat_type_code,"+s+",nvl(temp.amount_money,0))) as m_"+s+" ");
				columnSql.append(",sum(decode(temp.mat_type_code,"+s+",nvl(temp.amount,0))) as n_"+s+" ");
				
				/*
				查询字段、合计列格式
				sum(temp2.m1) as m1,
		        sum(temp2.n1) as n1,
		        sum(temp2.m2) as m2,
		        sum(temp2.n2) as n2,
				*/
				//查询字段
				columnSql2.append(",sum(temp2.m_" + s + ") as m_" + s);
				columnSql2.append(",sum(temp2.n_"+ s + ") as n_" + s);
				
				/*
				合计列格式
				sum(nvl(temp2.m1,0))+sum(nvl(temp2.m2,0)) as money_count,
		        sum(nvl(temp2.n1,0))+sum(nvl(temp2.n2,0)) as amount_count
		        */
				//合计列
				columnSql2_1.append("sum(nvl(temp2.m_"+s+",0))+");
				columnSql2_2.append("sum(nvl(temp2.n_"+s+",0))+");
				
				
				/* 
				 小计、总计 查询字段
				 sum(nvl(a.m1,0)) as m1,
		         sum(nvl(a.n1,0)) as n1,
		         sum（nvl(a.m2,0)) as m2,
		         sum(nvl(a.n2,0)) as n2,
		         */
				columnSql3.append(",sum(nvl(a.m_"+s+",0)) as m_"+s);
				columnSql3.append(",sum(nvl(a.n_"+s+",0)) as n_"+s);
				
			}
			
			columnSql2_1.deleteCharAt(columnSql2_1.length()-1);
			
			columnSql2_1.append("as money_count ");
			
			columnSql2.append(columnSql2_1);
			
			columnSql2_2.deleteCharAt(columnSql2_2.length()-1);
			
			columnSql2_2.append("as amount_count ");
			
			columnSql2.append(columnSql2_2);
			
			entityMap.put("column_sql", columnSql.toString());
			
			entityMap.put("column_sql2", columnSql2.toString());
			
			entityMap.put("column_sql3", columnSql3.toString());
		}
		try{
			
				
				List<Map<String, Object>> list = matAccountReportDeptTypeCountMapper.queryMatAccountReportDeptTypeCount(entityMap);
				
				return list;
				
			
		}catch (Exception e) {
			
			logger.error(e.getMessage(), e);
			
			throw new SysException(e.getMessage());
		}
	}
}
