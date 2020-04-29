package com.chd.hrp.med.serviceImpl.account.report.outCategoryCount;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.SysPage;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.hrp.med.dao.account.report.outCategoryCount.MedAccountReportDeptTypeCountMapper;
import com.chd.hrp.med.service.account.report.outCategoryCount.MedAccountReportDeptTypeCountService;
import com.github.pagehelper.PageInfo;

/**
 * @Description:
 * 出库分类统计:科室类型统计-查询表
 * @Table: 无针对表
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */

@Service("medAccountReportDeptTypeCountService")
public class MedAccountReportDeptTypeCountServiceImpl implements MedAccountReportDeptTypeCountService {
	private static Logger logger = Logger.getLogger(MedAccountReportDeptCountServiceImpl.class);
	
	@Resource(name = "medAccountReportDeptTypeCountMapper")
	private final MedAccountReportDeptTypeCountMapper medAccountReportDeptTypeCountMapper = null;
	
	@Override
	public String queryMedAccountReportDeptTypeCount(Map<String, Object> entityMap) throws DataAccessException {
		
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
		
		if(entityMap.get("med_type_id") != null && !"".equals(entityMap.get("med_type_id"))){//如果选中药品类别,查当前药品类别
			
			for(String s :column){
				/*
				行转列格式:
				sum(decode(temp.med_type_code,'0101',nvl(temp.amount_money,0))) as m1,
                sum(decode(temp.med_type_code,'0101',nvl(temp.amount,0))) as n1,
                sum(decode(temp.med_type_code,'0104',nvl(temp.amount_money,0))) as m2,
                sum(decode(temp.med_type_code,'0104',nvl(temp.amount,0))) as n2*/
				//行转列
				columnSql.append(",sum(decode(temp.med_type_code,"+s+",nvl(temp.amount_money,0))) as m_"+s+" ");
				columnSql.append(",sum(decode(temp.med_type_code,"+s+",nvl(temp.amount,0))) as n_"+s+" ");
				
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
			
		}else{//如果不选中,查所有药品类别
			
			for(String s :column){
				/*
				行转列格式
				sum(decode(temp.med_type_code,'0101',nvl(temp.amount_money,0))) as m1,
                sum(decode(temp.med_type_code,'0101',nvl(temp.amount,0))) as n1,
                sum(decode(temp.med_type_code,'0104',nvl(temp.amount_money,0))) as m2,
                sum(decode(temp.med_type_code,'0104',nvl(temp.amount,0))) as n2
                */
				columnSql.append(",sum(decode(temp.med_type_code,"+s+",nvl(temp.amount_money,0))) as m_"+s+" ");
				columnSql.append(",sum(decode(temp.med_type_code,"+s+",nvl(temp.amount,0))) as n_"+s+" ");
				
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
				
				List<Map<String, Object>> list = medAccountReportDeptTypeCountMapper.queryMedAccountReportDeptTypeCount(entityMap);
				
				return ChdJson.toJsonLower(list);
				
			}else{
				
				RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
				
				List<Map<String, Object>> list = medAccountReportDeptTypeCountMapper.queryMedAccountReportDeptTypeCount(entityMap, rowBounds);
				
				PageInfo page = new PageInfo(list);
				
				return ChdJson.toJsonLower(list, page.getTotal());
			}
			
		}catch (Exception e) {
			
			logger.error(e.getMessage(), e);
			
			throw new SysException(e.getMessage());
		}
	}

	//科室类型统计:按药品类别查询动态表头
	@Override
	public String queryDeptTypeCountHead(Map<String, Object> entityMap)throws DataAccessException {
		try{
			
			//return ChdJson.toJsonLower(medAccountReportDeptCountMapper.queryDeptCountHead(entityMap));
			
			return ChdJson.toJsonLower(medAccountReportDeptTypeCountMapper.queryDeptTypeCountHead(entityMap));
			
		}catch (Exception e) {
			
			logger.error(e.getMessage(), e);
			
			throw new SysException(e.getMessage());

		}
	}
	
	//科室类型统计:按药品类别查询动态表头New
	@Override
	public String queryDeptTypeCountHeadNew(Map<String, Object> entityMap)throws DataAccessException {
		try{
			if(entityMap.get("med_type_id") != null && entityMap.get("med_type_id") != "" && !"".equals(entityMap.get("med_type_id"))){
				String str = "";
				List<Map<String, Object>> listMap = medAccountReportDeptTypeCountMapper.queryDeptTypeCountHeadByCode(entityMap);
				for(Map<String, Object> map :listMap){
					 str = (String) map.get("MED_TYPE_CODE");
				}
				entityMap.put("med_type_code",str.substring(0,2));
			}
			
			return ChdJson.toJsonLower(medAccountReportDeptTypeCountMapper.queryDeptTypeCountHeadNew(entityMap));
			
		}catch (Exception e) {
			
			logger.error(e.getMessage(), e);
			
			throw new SysException(e.getMessage());

		}
	}
}
