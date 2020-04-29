/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/

package com.chd.hrp.acc.serviceImpl.wagedata;

import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import com.chd.base.SysPage;
import com.chd.base.util.ChdJson;
import com.chd.hrp.acc.dao.AccWageItemsMapper;
import com.chd.hrp.acc.dao.wagedata.AccWageItemSumMapper;
import com.chd.hrp.acc.entity.AccWagePay;
import com.chd.hrp.acc.service.wagedata.AccWageItemSumService;
import com.github.pagehelper.PageInfo;

/**
* @Title. @Description.
* 工资综合查询<BR>  
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


@Service("accWageItemSumService")
public class AccWageItemSumServiceImpl implements AccWageItemSumService {

	private static Logger logger = Logger.getLogger(AccWageItemSumServiceImpl.class);
	
	@Resource(name = "accWageItemSumMapper")
	private final AccWageItemSumMapper accWageItemSumMapper = null;
	
	@Resource(name = "accWageItemsMapper")
	private final AccWageItemsMapper accWageItemsMapper = null;
    
	/**
	 * @Description 
	 * 工资综合查询<BR> 查询AccWageItemSum分页
	 * @param  entityMap RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public String queryAccWageItemSum(Map<String,Object> entityMap) throws DataAccessException{
		
		StringBuffer sbf = new StringBuffer();
		
		StringBuffer group = new StringBuffer();
		
		StringBuffer sum_sql = new StringBuffer();
		
		StringBuffer order_sql = new StringBuffer();
		
		String is_check=entityMap.get("is_check").toString();
		//按照部门汇总查询
		if("1".equals(is_check)){
			
			sbf.append("select awp.acc_year||'.'||awp.acc_month  acc_year,count(awp.emp_id) emp_id,hdd.dept_code,hdd.dept_name "+entityMap.get("sum_item"));
			
			sbf.append(" from acc_wage_pay awp left join hos_dept_dict hdd on awp.group_id = hdd.group_id and awp.hos_id = hdd.hos_id and awp.dept_id = hdd.dept_id and awp.dept_no = hdd.dept_no ");
			
			sum_sql.append("select '合计'  acc_year,count(awp.emp_id) emp_id,'' dept_code,'' dept_name "+entityMap.get("sum_item"));
			
			sum_sql.append(" from acc_wage_pay awp left join hos_dept_dict hdd on awp.group_id = hdd.group_id and awp.hos_id = hdd.hos_id and awp.dept_id = hdd.dept_id and awp.dept_no = hdd.dept_no ");
			
			group.append(" group by awp.acc_year,awp.acc_month,hdd.dept_code,hdd.dept_name ");
		
			order_sql.append(" order by a.acc_year,a.dept_code ");
			//按照人员类别汇总查询	
		}else if("3".equals(is_check)){
			
			sbf.append("select awp.acc_year||'.'||awp.acc_month  acc_year,count(awp.emp_id) emp_id,awp.kind_code,awp.kind_name "+entityMap.get("sum_item"));
			
			sbf.append(" from acc_wage_pay awp ");
			
			sum_sql.append("select '合计'  acc_year,count(awp.emp_id) emp_id,'' kind_code,'' kind_name "+entityMap.get("sum_item"));
			
			sum_sql.append(" from acc_wage_pay awp ");
			
			group.append(" group by awp.acc_year,awp.acc_month,awp.kind_code,awp.kind_name ");
		
			order_sql.append(" order by a.acc_year,a.kind_code ");
			//按照部门支出性质汇总查询	
		}else if("2".equals(is_check)){
			
			entityMap.put("is_disable", "0");
			
			sbf.append("select awp.acc_year||'.'||awp.acc_month  acc_year,count(awp.emp_id) emp_id,ado.out_code,ado.out_name "+entityMap.get("sum_item"));
			
			sbf.append(" from acc_wage_pay awp left join acc_dept_attr ada on awp.group_id = ada.group_id and awp.hos_id= ada.hos_id ");
			
			sbf.append(" and awp.dept_id=ada.dept_id left join ACC_DEPT_OUT ado on ada.out_code = ado.out_code ");
			
			sbf.append(" left join hos_emp_dict hed on hed.group_id = awp.group_id and awp.hos_id= hed.hos_id  and hed.emp_id =awp.emp_id and hed.emp_no = awp.emp_no ");
			
			sum_sql.append("select '合计'  acc_year,count(awp.emp_id) emp_id,'' out_code,'' out_name "+entityMap.get("sum_item"));
			
			sum_sql.append(" from acc_wage_pay awp left join acc_dept_attr ada on awp.group_id = ada.group_id and awp.hos_id= ada.hos_id ");
			
			sum_sql.append(" and awp.dept_id=ada.dept_id left join ACC_DEPT_OUT ado on ada.out_code = ado.out_code ");
			
			sum_sql.append(" left join hos_emp_dict hed on hed.group_id = awp.group_id and awp.hos_id= hed.hos_id  and hed.emp_id =awp.emp_id and hed.emp_no = awp.emp_no ");
			
			group.append(" group by awp.acc_year,awp.acc_month,ado.out_code,ado.out_name ");
			
			order_sql.append(" order by a.acc_year,a.out_code ");
		}else{
			
			sbf.append("select awp.acc_year||'.'||awp.acc_month  acc_year,count(awp.emp_id) emp_id,ado.kind_code dept_kind_code,ado.kind_name dept_kind_name "+entityMap.get("sum_item"));
			
			sbf.append(" from acc_wage_pay awp left join hos_dept_dict hdd on awp.group_id = hdd.group_id and awp.hos_id = hdd.hos_id and awp.dept_id = hdd.dept_id and awp.dept_no = hdd.dept_no ");
			
			sbf.append("  left join hos_dept_kind ado on hdd.kind_code = ado.kind_code and hdd.group_id = ado.group_id and hdd.hos_id = ado.hos_id");
			
			sum_sql.append("select '合计'  acc_year,count(awp.emp_id) emp_id,'' out_code,'' out_name "+entityMap.get("sum_item"));
			
			sum_sql.append(" from acc_wage_pay awp left join hos_dept_dict hdd on awp.group_id = hdd.group_id and awp.hos_id = hdd.hos_id and awp.dept_id = hdd.dept_id and awp.dept_no = hdd.dept_no ");
			
			sum_sql.append("  left join hos_dept_kind ado on hdd.kind_code = ado.kind_code and hdd.group_id = ado.group_id and hdd.hos_id = ado.hos_id");
			
			group.append(" group by awp.acc_year,awp.acc_month,ado.kind_code,ado.kind_name ");
			
			order_sql.append(" order by a.acc_year,a.dept_kind_code ");
		}
		
		entityMap.put("select_table", sbf);
		
		entityMap.put("sum_select_table", sum_sql);
		
		entityMap.put("order_sql", order_sql);
		
		entityMap.put("group_by", group);
		
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage)entityMap.get("sysPage");
		
		if(sysPage.getTotal() == -1){
			
			List<Map<String,Object>> list = accWageItemSumMapper.queryAccWageItemSum(entityMap);
			
			return ChdJson.toJson(list);
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<Map<String,Object>> list = accWageItemSumMapper.queryAccWageItemSum(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list,page.getTotal());
		}
		
	}
	
	/**
	 * @Description 
	 * 工资综合查询<BR> 查询AccWageItemSumByCode
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	@Override
	public AccWagePay queryAccWageItemSumByCode(Map<String,Object> entityMap)throws DataAccessException{
		
		return accWageItemSumMapper.queryAccWageItemSumByCode(entityMap);
		
	}
	
	public List<Map<String,Object>> queryAccWageItemSumPrint(Map<String,Object> entityMap) throws DataAccessException{
		
		StringBuffer sbf = new StringBuffer();
		
		StringBuffer group = new StringBuffer();
		
		StringBuffer sum_sql = new StringBuffer();
		
		StringBuffer order_sql = new StringBuffer();
		
		String is_check=entityMap.get("is_check").toString();
		//按照部门汇总查询
		if("1".equals(is_check)){
			
			sbf.append("select awp.acc_year||'.'||awp.acc_month  acc_year,count(awp.emp_id) emp_id,hdd.dept_code,hdd.dept_name "+entityMap.get("sum_item"));
			
			sbf.append(" from acc_wage_pay awp left join hos_dept_dict hdd on awp.group_id = hdd.group_id and awp.hos_id = hdd.hos_id and awp.dept_id = hdd.dept_id and awp.dept_no = hdd.dept_no ");
			
			sum_sql.append("select '合计'  acc_year,count(awp.emp_id) emp_id,'' dept_code,'' dept_name "+entityMap.get("sum_item"));
			
			sum_sql.append(" from acc_wage_pay awp left join hos_dept_dict hdd on awp.group_id = hdd.group_id and awp.hos_id = hdd.hos_id and awp.dept_id = hdd.dept_id and awp.dept_no = hdd.dept_no ");
			
			group.append(" group by awp.acc_year,awp.acc_month,hdd.dept_code,hdd.dept_name ");
		
			order_sql.append(" order by a.acc_year,a.dept_code ");
			//按照人员类别汇总查询	
		}else if("3".equals(is_check)){
			
			sbf.append("select awp.acc_year||'.'||awp.acc_month  acc_year,count(awp.emp_id) emp_id,awp.kind_code,awp.kind_name "+entityMap.get("sum_item"));
			
			sbf.append(" from acc_wage_pay awp ");
			
			sum_sql.append("select '合计'  acc_year,count(awp.emp_id) emp_id,'' kind_code,'' kind_name "+entityMap.get("sum_item"));
			
			sum_sql.append(" from acc_wage_pay awp ");
			
			group.append(" group by awp.acc_year,awp.acc_month,awp.kind_code,awp.kind_name ");
		
			order_sql.append(" order by a.acc_year,a.kind_code ");
			//按照部门支出性质汇总查询	
		}else if("2".equals(is_check)){
			
			entityMap.put("is_disable", "0");
			
			sbf.append("select awp.acc_year||'.'||awp.acc_month  acc_year,count(awp.emp_id) emp_id,ado.out_code,ado.out_name "+entityMap.get("sum_item"));
			
			sbf.append(" from acc_wage_pay awp left join acc_dept_attr ada on awp.group_id = ada.group_id and awp.hos_id= ada.hos_id ");
			
			sbf.append(" and awp.dept_id=ada.dept_id left join ACC_DEPT_OUT ado on ada.out_code = ado.out_code ");
			
			sbf.append(" left join hos_emp_dict hed on hed.group_id = awp.group_id and awp.hos_id= hed.hos_id  and hed.emp_id =awp.emp_id and hed.emp_no = awp.emp_no ");
			
			sum_sql.append("select '合计'  acc_year,count(awp.emp_id) emp_id,'' out_code,'' out_name "+entityMap.get("sum_item"));
			
			sum_sql.append(" from acc_wage_pay awp left join acc_dept_attr ada on awp.group_id = ada.group_id and awp.hos_id= ada.hos_id ");
			
			sum_sql.append(" and awp.dept_id=ada.dept_id left join ACC_DEPT_OUT ado on ada.out_code = ado.out_code ");
			
			sum_sql.append(" left join hos_emp_dict hed on hed.group_id = awp.group_id and awp.hos_id= hed.hos_id  and hed.emp_id =awp.emp_id and hed.emp_no = awp.emp_no ");
			
			group.append(" group by awp.acc_year,awp.acc_month,ado.out_code,ado.out_name ");
			
			order_sql.append(" order by a.acc_year,a.out_code ");
		}else{
			
			sbf.append("select awp.acc_year||'.'||awp.acc_month  acc_year,count(awp.emp_id) emp_id,ado.kind_code dept_kind_code,ado.kind_name dept_kind_name "+entityMap.get("sum_item"));
			
			sbf.append(" from acc_wage_pay awp left join hos_dept_dict hdd on awp.group_id = hdd.group_id and awp.hos_id = hdd.hos_id and awp.dept_id = hdd.dept_id and awp.dept_no = hdd.dept_no ");
			
			sbf.append("  left join hos_dept_kind ado on hdd.kind_code = ado.kind_code and hdd.group_id = ado.group_id and hdd.hos_id = ado.hos_id");
			
			sum_sql.append("select '合计'  acc_year,count(awp.emp_id) emp_id,'' out_code,'' out_name "+entityMap.get("sum_item"));
			
			sum_sql.append(" from acc_wage_pay awp left join hos_dept_dict hdd on awp.group_id = hdd.group_id and awp.hos_id = hdd.hos_id and awp.dept_id = hdd.dept_id and awp.dept_no = hdd.dept_no ");
			
			sum_sql.append("  left join hos_dept_kind ado on hdd.kind_code = ado.kind_code and hdd.group_id = ado.group_id and hdd.hos_id = ado.hos_id");
			
			group.append(" group by awp.acc_year,awp.acc_month,ado.kind_code,ado.kind_name ");
			
			order_sql.append(" order by a.acc_year,a.dept_kind_code ");
		}
		
		entityMap.put("select_table", sbf);
		
		entityMap.put("sum_select_table", sum_sql);
		
		entityMap.put("order_sql", order_sql);
		
		entityMap.put("group_by", group);
		
		List<Map<String,Object>> list = accWageItemSumMapper.queryAccWageItemSum(entityMap);
		
		return list;
	}

}
