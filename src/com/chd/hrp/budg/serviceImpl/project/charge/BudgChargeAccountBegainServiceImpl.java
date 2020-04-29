/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.budg.serviceImpl.project.charge;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.SessionManager;
import com.chd.base.exception.SysException;
import com.chd.hrp.budg.dao.project.begin.BudgProjBegainMarkMapper;
import com.chd.hrp.budg.dao.project.begin.BudgProjDetailYearMapper;
import com.chd.hrp.budg.dao.project.begin.BudgProjYearMapper;
import com.chd.hrp.budg.dao.project.charge.BudgChargeAccountBegainMapper;
import com.chd.hrp.budg.entity.BudgProjBegainMark;
import com.chd.hrp.budg.service.project.charge.BudgChargeAccountBegainService;

/**
 * 
 * @Description:
 * tabledesc
 * @Table:
 * BUDG_PROJ_SET_UP
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 


@Service("budgChargeAccountBegainService")
public class BudgChargeAccountBegainServiceImpl implements BudgChargeAccountBegainService {

	private static Logger logger = Logger.getLogger(BudgChargeAccountBegainServiceImpl.class);
	//引入DAO操作
	@Resource(name = "budgChargeAccountBegainMapper")
	private final BudgChargeAccountBegainMapper budgChargeAccountBegainMapper = null;
	//引入DAO操作
	@Resource(name = "budgProjYearMapper")
	private final BudgProjYearMapper budgProjYearMapper = null;
	//引入DAO操作
	@Resource(name = "budgProjDetailYearMapper")
	private final BudgProjDetailYearMapper budgProjDetailYearMapper = null;
	//引入DAO操作
	@Resource(name = "budgProjBegainMarkMapper")
	private final BudgProjBegainMarkMapper budgProjBegainMarkMapper = null;
	
	RowBounds rowBoundsALL = new RowBounds(0, 20);
	/*@Override
	public String add(Map<String, Object> entityMap) throws DataAccessException {
		try {
				 List<?>   budgProjYear=budgProjYearMapper.query(entityMap);
				 List<?>  budgProjDetailYear=budgProjDetailYearMapper.query(entityMap);
				BudgProjBegainMark  budgProjBegainMark=budgProjBegainMarkMapper.queryByCode(entityMap);
				//项目资金来源无以前年度数据----budg_proj_year年度主表     -----budg_proj_detail_year年度明细表
			    if(budgProjYear.size()==0&&budgProjDetailYear.size()==0){
					 budgChargeAccountBegainMapper.add(entityMap);
					 budgChargeAccountBegainMapper.addDetail(entityMap);
					 
					 if(budgProjBegainMark==null){
					  budgProjYearMapper.addBudgProjBegainRemark(entityMap);						                            
					      }else{
					    	  return null;
					      }
					 return "{\"msg\":\"记账成功.\",\"state\":\"true\"}";
			         }else{
			        	 //项目资金来源有以前年度数据
			        	 budgChargeAccountBegainMapper.addLastTotalYearData(entityMap);
						 budgChargeAccountBegainMapper.addLastTotalYearDataDetail(entityMap);
			        	 return "{\"msg\":\"有数据的情况下记账,操作,.\",\"state\":\"true\"}";
			         }
			        
				}
				catch (Exception e) {

					logger.error(e.getMessage(), e);

					return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 \"}";

				}
	}*/
	@Override
	public String add(Map<String, Object> entityMap) throws DataAccessException {
		//查询所有数据表里面的年度
		//budg_proj_begain主表的数据
		List<Map<String,Object>> listVo=budgChargeAccountBegainMapper.queryBudgProjBegain(entityMap);
		//budg_proj_begain_detail明细表中的数据
		List<Map<String,Object>> listVoDetail=budgChargeAccountBegainMapper.queryBudgProjBegainDetail(entityMap);
		try{
			//要求没有期初项目预算  依然可以进行记账操作   若期初项目预算没有数据     所有用到期初项目预算数据的操作一律略过	
			if(!listVo.isEmpty()){
				//删除budg_proj_begain主表的数据
		        budgProjYearMapper.deleteBatch(listVo);  
		        //批量添加年度项目预算<BR> 
				budgProjYearMapper.addBatch(listVo);
				 //更新主表数据
				budgProjYearMapper.updateBatch(listVo);
			}
	    	
			if(!listVoDetail.isEmpty()){
				//删除budg_proj_begain_detail明细表中的数据
		        budgProjDetailYearMapper.deleteBatch(listVoDetail);
		    	
				budgProjDetailYearMapper.addBatch(listVoDetail);
				//更新明细表数据
				budgProjDetailYearMapper.updateBatch(listVoDetail);
			}
			
			
			//查询账表里面的数据
			List<Map<String,Object>> tlist=budgProjYearMapper.queryR(entityMap);
			//查询明细账表里面的数据
			List<Map<String,Object>> tlistDetail=budgProjYearMapper.queryRDetail(entityMap);
			
			//要求没有期初项目预算  依然可以进行记账操作   若期初项目预算没有数据     所有用到期初项目预算数据的操作一律略过	
			if(!tlist.isEmpty()){
				//更新账表里面的累计数据
				budgProjYearMapper.updateBatcht(tlist);
			}
			
			if(!tlistDetail.isEmpty()){
				//更新账表明细里面的累计数据
				budgProjYearMapper.updateBatchDetailt(tlistDetail);//
			}
			
			//查询账表里面的数据
			List<Map<String,Object>> trlist=budgProjYearMapper.queryTR(entityMap);
			//查询账明细表里面的数据
			List<Map<String,Object>> trlistDetail=budgProjYearMapper.queryTRDetail(entityMap);
			
			if(!trlist.isEmpty()){
				//更新账表里面的累计数据
				budgProjYearMapper.updateBatchtr(trlist);
			}
			
			if(!trlist.isEmpty()){
				//更新账表里面的累计数据
				budgProjYearMapper.updateBatchtrDetail(trlist);
			}
			
			budgProjBegainMarkMapper.add(entityMap);//三个字段：state:1 更新记账人：user_id更新记账日期mark_date						                            
		    
		    return "{\"msg\":\"操作成功.\",\"state\":\"true\"}";
		    
		}catch (Exception e) {

            logger.error(e.getMessage(), e);
			
			throw new SysException("{\"error\":\"操作失败！！\"}");

		}
	
	}
	@Override
	public String addBatch(List<Map<String, Object>> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public String update(Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public String updateBatch(List<Map<String, Object>> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public String delete(Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public String deleteBatch(List<Map<String, Object>> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public String addOrUpdate(Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public String query(Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public <T> T queryByCode(Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public <T> T queryByUniqueness(Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public List<?> queryExists(Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}


    
	
	
}
