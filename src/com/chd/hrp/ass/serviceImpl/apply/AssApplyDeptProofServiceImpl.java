package com.chd.hrp.ass.serviceImpl.apply;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.SysPage;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.hrp.ass.dao.apply.AssApplyDeptProofMapper;
import com.chd.hrp.ass.entity.apply.AssApplyDeptProof;
import com.chd.hrp.ass.entity.apply.AssApplyDeptProofDetail;
import com.chd.hrp.ass.service.apply.AssApplyDeptProofService;
import com.github.pagehelper.PageInfo;

import org.apache.log4j.Logger;

@Service("assApplyDeptProofService")
public class AssApplyDeptProofServiceImpl implements AssApplyDeptProofService {
	
	 private static Logger logger = Logger.getLogger(AssApplyDeptProofServiceImpl.class);
	 
  @Resource(name="assApplyDeptProofMapper")
  AssApplyDeptProofMapper assApplyDeptProofMapper=null;
	@Override
	public  AssApplyDeptProof queryApplyProof(Map<String, Object> paramMap) throws DataAccessException {
		
		    AssApplyDeptProof assApplyDeptProof =assApplyDeptProofMapper.queryApplyProof(paramMap);
		   return assApplyDeptProof;
	}
	@Override
	public String queryApplyProofDetail(
			Map<String, Object> paramMap) throws DataAccessException {
		
		  SysPage sysPage = new SysPage();
		    
		    sysPage = (SysPage)paramMap.get("sysPage");
		    
		    RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
		 
		List<AssApplyDeptProofDetail> list=assApplyDeptProofMapper.queryApplyProofDetail(paramMap,rowBounds);
		PageInfo page = new PageInfo(list);
	    return ChdJson.toJson(list, Long.valueOf(page.getTotal()));

	}
	@Override
	public String deleteApplyProofDetail(List<Map<String, Object>> mapVo){
	try
    {
		int state=assApplyDeptProofMapper.deleteApplyProofDetail(mapVo);
      return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
    }
    catch (Exception e)
    {
      logger.error(e.getMessage(), e);
    }
    return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码 deleteApplyProofDetail\"}";
	}
	@Override
	public String deleteApplyProof(Map<String, Object> vmap)
			throws DataAccessException {
		try
	    {
			int state=assApplyDeptProofMapper.deleteApplyProof(vmap);
			int res=assApplyDeptProofMapper.deleteApplyProofDetailm(vmap);
	      return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
	    }
	    catch (Exception e)
	    {
	      logger.error(e.getMessage(), e);
	    }
	    return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码 deleteApplyProofDetail\"}";
	}
	
	/***
	 * 更新方法（新增方法放到采购明旭表里）
	 */
	@Override
	public String saveorupdateApplyProof(Map<String, Object> entityMap)throws DataAccessException {
		/**
		* 备注 先判断是否存在 存在即更新不存在则添加<br>
		* 在判断是否存在时可根据实际情况进行修改传递的参数进行判断<br>
		* 判断是否名称相同 判断是否 编码相同 等一系列规则
		*/
		String proof_id= entityMap.get("proof_id").toString();
		String apply_id=entityMap.get("apply_id").toString();
		String detail_id=entityMap.get("detail_id").toString();
		//判断是否存在对象050701 资产入库主表
		Map<String, Object> mapVo=new HashMap<String, Object>();
		mapVo.put("group_id",entityMap.get("group_id"));
		mapVo.put("hos_id",entityMap.get("hos_id"));
    	mapVo.put("copy_code", entityMap.get("copy_code"));
    	mapVo.put("proof_id", entityMap.get("proof_id"));
    	mapVo.put("apply_id", entityMap.get("apply_id"));
    	mapVo.put("detail_id", entityMap.get("detail_id"));
    	//判断主表是否存在，存在更新，不存在添加
    	List<AssApplyDeptProof> aDeptProofs=assApplyDeptProofMapper.queryApplyDeptProofExists(mapVo);
    	try{
    	//执行更新方法
    	if(aDeptProofs.size()>0){
    		int state=assApplyDeptProofMapper.updateApplyDeptProof(entityMap);
    		
    	}else{
    		///如果不存在，就新增
    		if((apply_id!=null)&&(!"".equals(apply_id))&&(detail_id!=null)&&(!"".equals(detail_id))){
    		int state=assApplyDeptProofMapper.addApplyDeptProof(entityMap);
    		proof_id=String.valueOf(queryApplyDeptProofSequence());
    		
    		}
    		//return "{\"msg\":\"更新失败.\",\"state\":\"false\",\"proof_id\":\"" + proof_id+ "\"}";
    	}
    	
    	//处理明细
		List<Map> detail = ChdJson.fromJsonAsList(Map.class, entityMap.get("ParamVoDetail").toString());
		for (Map<String, Object> map : detail) {
			
			if (map.get("year_num") == null || "".equals(map.get("year_num"))) {
				continue;
			}
			map.put("group_id",entityMap.get("group_id"));
			map.put("hos_id",entityMap.get("hos_id"));
			map.put("copy_code", entityMap.get("copy_code"));
			map.put("proof_id", proof_id);
			
			Map<String, Object> queyMap=new HashMap<String, Object>();
			queyMap.put("group_id",map.get("group_id"));
			queyMap.put("hos_id",map.get("hos_id"));
			queyMap.put("copy_code", map.get("copy_code"));
			queyMap.put("proof_id", map.get("proof_id"));
			queyMap.put("proof_detail_id", map.get("proof_detail_id"));
			List<AssApplyDeptProofDetail> proofDetail=assApplyDeptProofMapper.queryApplyDeptProofDetailExists(queyMap);
			//更新
			if(proofDetail.size()>0){
				int state=assApplyDeptProofMapper.updateApplyDeptProofDetail(map);
			}else{
			//新增
				
			int state=assApplyDeptProofMapper.addApplyDeptProofDetail(map);
			}
		}
		return "{\"msg\":\"保存成功.\",\"state\":\"true\",\"proof_id\":\"" + proof_id+ "\"}";
		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());
		}
		
	}
	
	public Long queryApplyDeptProofSequence()throws DataAccessException {
		
		return assApplyDeptProofMapper.queryApplyDeptProofSequence();
	}
	@Override
	public String addApplyProof(Map<String, Object> entityMap)
			throws DataAccessException {
		return null;
	}
	@Override
	public String deleteProofInfo(List<Map<String, Object>> listVo) throws DataAccessException {
		try {
			//根据 购置申请信息 查询 论证数据信息
			List<Map<String,Object>> proofList = ChdJson.toListLower(assApplyDeptProofMapper.queryApplyProofInfo(listVo));
			
			if(proofList.size()>0){
				//根据 论证主表id 批量删除 论证明细数据
				assApplyDeptProofMapper.deleteBatchApplyProofDetailByProofId(proofList);
				
				//根据 id 批量删除 论证数据
				assApplyDeptProofMapper.deleteBatchApplyProofById(proofList);
			}
			
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
			
		} catch (Exception e) {
			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());
		}
		
	}

}
