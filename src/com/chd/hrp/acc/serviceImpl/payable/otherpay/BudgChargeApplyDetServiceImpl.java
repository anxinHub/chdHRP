/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.acc.serviceImpl.payable.otherpay;

import java.util.ArrayList;
import java.util.HashMap;
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
import com.chd.hrp.acc.dao.payable.otherpay.BudgChargeApplyDetMapper;
import com.chd.hrp.acc.dao.payable.otherpay.BudgChargeApplyMapper;
import com.chd.hrp.acc.entity.payable.BudgChargeApplyDet;
import com.chd.hrp.acc.service.payable.otherpay.AccChargePayService;
import com.chd.hrp.acc.service.payable.otherpay.BudgChargeApplyDetService;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Description:
 * 借款明细
 * @Table:
 * BUDG_BORR_Apply_DET
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
@Service("budgChargeApplyDetService")
public class BudgChargeApplyDetServiceImpl implements BudgChargeApplyDetService {

	private static Logger logger = Logger.getLogger(BudgChargeApplyDetServiceImpl.class);
	//引入DAO操作
	@Resource(name = "budgChargeApplyDetMapper")
	private final BudgChargeApplyDetMapper budgChargeApplyDetMapper = null;
	
	@Resource(name = "budgChargeApplyMapper")
	private final BudgChargeApplyMapper budgChargeApplyMapper = null;
    
	@Override
	public String queryForUpdate(Map<String, Object> entityMap) throws DataAccessException {
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<BudgChargeApplyDet> list = budgChargeApplyDetMapper.queryForUpdate(entityMap);
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<BudgChargeApplyDet> list = budgChargeApplyDetMapper.queryForUpdate(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
	}
	@Override
	public String deleteBudgChargeApplyDet(Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		
		try {
			
			String paramVo = String.valueOf(entityMap.get("paramVo"));
			List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
			
			for ( String id: paramVo.split(",")) {
						
					Map<String, Object> mapVo=new HashMap<String, Object>();
						
					String[] ids=id.split("@");
						
					mapVo.put("group_id", ids[0])   ;
					mapVo.put("hos_id", ids[1])   ;
					mapVo.put("copy_code", ids[2])   ;
					mapVo.put("apply_code", ids[3])   ;
					mapVo.put("source_id", ids[4])   ;
					mapVo.put("payment_item_id", ids[5]);
						
			      listVo.add(mapVo);
			      
			}
			
			budgChargeApplyDetMapper.deleteBatch(listVo);//删除明细
			budgChargeApplyMapper.updateAmount(entityMap);//更改主表
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
			
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e.getMessage(), e);

			throw new SysException("{\"error\":\"操作失败.\",\"state\":\"false\"}");
			
		}
		
	}
	
}
