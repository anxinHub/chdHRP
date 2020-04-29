package com.chd.hrp.budg.serviceImpl.base.budgsubj;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.hrp.budg.dao.base.budgsubj.BudgSubjChargeKindMapper;
import com.chd.hrp.budg.service.base.budgsubj.BudgSubjChargeKindService;

@Service("budgSubjChargeKindService")
public  class BudgSubjChargeKindServiceImpl implements BudgSubjChargeKindService {

	private static Logger logger = Logger.getLogger(BudgSubjChargeKindServiceImpl.class);
	
	@Resource(name = "budgSubjChargeKindMapper")
	private  BudgSubjChargeKindMapper budgSubjChargeKindMapper; 

	@Override
	public String save(List<Map<String, Object>> entityMap)throws DataAccessException {
		
		List<Map<String, Object>> addList= new ArrayList<Map<String,Object>>();
		
		List<Map<String, Object>> updateList= new ArrayList<Map<String,Object>>();
		
		for(Map<String,Object> item : entityMap){
			
			if("1".equals(item.get("flag"))){ //添加
				
				addList.add(item) ;
				
			}else{ //修改
				
				updateList.add(item) ;
			}
			
		}
		
		try {
			String info="";
			if(addList.size()>0){
				//批量 查询 添加数据是否已存在
				
				for(Map<String,Object> map:addList){
					if(budgSubjChargeKindMapper.queryDataExist(map)>0){
						info+="预算科目 "+map.get("budg_subj_code").toString()+"与收费类别 "+map.get("charge_kind_code").toString()+",";
					}
				}
				if(!info.equals("")){
					info+="关系已存在,无法保存";
					return "{\"warn\":\""+info+"\",\"state\":\"false\"}";
				}
				budgSubjChargeKindMapper.addBatchBudgSubjChargeKind(addList);
			}
			
			if(updateList.size()>0){
				for(Map<String,Object> map:updateList){
					if(budgSubjChargeKindMapper.queryUpdateDataExist(map)>0){
						info+="预算科目 "+map.get("budg_subj_code").toString()+"与收费类别 "+map.get("charge_kind_code").toString()+",";
					}
				}
				if(!info.equals("")){
					info+="关系已存在,无法保存";
					return "{\"warn\":\""+info+"\",\"state\":\"false\"}";
				}
				int state = budgSubjChargeKindMapper.updateBatchBudgSubjChargeKind(updateList);
				
			}
			
			return "{\"msg\":\"保存成功.\",\"state\":\"true\"}";

		}catch (Exception e) {

			logger.error(e.getMessage(), e);
			
			throw new SysException("{\"error\":\"保存失败.\",\"state\":\"false\"}");

		}
		
	}


	@Override
	public String queryBudgSubjChargeKind(Map<String, Object> mapVo)
			throws DataAccessException {
		RowBounds rowBounds = new RowBounds(0, 20);
		if (mapVo.get("pageSize") != null) {
			rowBounds = new RowBounds(0, (Integer) mapVo.get("pageSize"));
		}
		return ChdJson.toJsonLower(budgSubjChargeKindMapper.queryBudgSubjChargeKind(mapVo, rowBounds));
	}

	@Override
	public String deleteBatchBudgSubjChargeKind(List<Map<String, Object>> listVo)
			throws DataAccessException {
		try{
			budgSubjChargeKindMapper.deleteBatch(listVo);
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		}catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"删除失败.\",\"state\":\"false\"}");
		}
	}
	@Override
	public int querySubjCodeExist(Map<String, Object> mapVo)
			throws DataAccessException {
		return budgSubjChargeKindMapper.queryIsExist(mapVo);
	}
	@Override
	public int queryIsExist(Map<String, Object> mapVo)
			throws DataAccessException {
		return budgSubjChargeKindMapper.queryDataExist(mapVo);
	}

}
