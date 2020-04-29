package com.chd.hrp.htcg.serviceImpl.setout;

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
import com.chd.hrp.htcg.dao.audit.HtcgSchemeConfMapper;
import com.chd.hrp.htcg.dao.making.HtcgSchemePathConfMapper;
import com.chd.hrp.htcg.dao.setout.HtcgMedicalAdviceStepMapper;
import com.chd.hrp.htcg.entity.setout.HtcgMedicalAdviceStep;
import com.chd.hrp.htcg.service.setout.HtcgMedicalAdviceStepService;
import com.github.pagehelper.PageInfo;

/**
 * htcg_medical_advice_step
 * @Title. 
 * @Description.
 * @Copyright: Copyright (c) 2015-2-14 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @Author: LiuYingDuo
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */


@Service("htcgMedicalAdviceStepService")
public class HtcgMedicalAdviceStepServiceImpl implements HtcgMedicalAdviceStepService {

	private static Logger logger = Logger.getLogger(HtcgMedicalAdviceStepServiceImpl.class);
	
	@Resource(name = "htcgMedicalAdviceStepMapper")
	private final HtcgMedicalAdviceStepMapper htcgMedicalAdviceStepMapper = null;
	
	@Override
	public String initHtcgMedicalAdviceStep(Map<String, Object> entityMap)throws DataAccessException {
		
		try {
			
			htcgMedicalAdviceStepMapper.initHtcgMedicalAdviceStep(entityMap);
				
		     return "{\"msg\":\""+entityMap.get("msg")+".\",\"state\":\"true\"}";
	    } catch (Exception e) {
		// TODO: handle exception
		   logger.error(e.getMessage(), e);
		   throw new SysException("{\"error\":\"划分失败\"}");
	    }
	}
	
	@Override
	public String queryHtcgMedicalAdviceStep(Map<String,Object> entityMap) throws DataAccessException{
		SysPage sysPage = new SysPage();
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			
			List<HtcgMedicalAdviceStep> list = htcgMedicalAdviceStepMapper.queryHtcgMedicalAdviceStep(entityMap) ;
			
			return ChdJson.toJson(list);
			
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<HtcgMedicalAdviceStep> list = htcgMedicalAdviceStepMapper.queryHtcgMedicalAdviceStep(entityMap, rowBounds) ;
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
			
		}
		
	}
	public String deleteBatchHtcgMedicalAdviceStep(List<Map<String,Object>> list)throws DataAccessException{
		
		try{
			 
	         htcgMedicalAdviceStepMapper.deleteBatchHtcgMedicalAdviceStep(list);
        	 
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
			
	    } catch (Exception e) {

			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"删除失败\"}");

		}  
	}
	 
}
