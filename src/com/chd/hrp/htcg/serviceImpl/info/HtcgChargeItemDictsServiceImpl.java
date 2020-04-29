
/*
 *
 */package com.chd.hrp.htcg.serviceImpl.info;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.SysPage;
import com.chd.base.util.ChdJson;
import com.chd.hrp.htcg.dao.info.HtcgChargeItemDictsMapper;
import com.chd.hrp.htcg.entity.info.HtcgChargeItemDicts;
import com.chd.hrp.htcg.service.info.HtcgChargeItemDictsService;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Title. 
 * @Description.
 * @Copyright: Copyright (c) 2015-2-14 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @Author: LiuYingDuo
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */


@Service("htcgChargeItemDictsService")
public class HtcgChargeItemDictsServiceImpl implements HtcgChargeItemDictsService {

	private static Logger logger = Logger.getLogger(HtcgChargeItemDictsServiceImpl.class);
	
	@Resource(name = "htcgChargeItemDictsMapper")
	private final HtcgChargeItemDictsMapper htcgChargeItemDictsMapper = null;
    
	
	/**
	 * 
	 */
	@Override
	public String queryHtcgChargeItemDicts(Map<String,Object> entityMap) throws DataAccessException{
		
		SysPage sysPage = new SysPage();
		sysPage = (SysPage) entityMap.get("sysPage");
		if (sysPage.getTotal()==-1){
			
			List<HtcgChargeItemDicts> list = htcgChargeItemDictsMapper.queryHtcgChargeItemDicts(entityMap) ;
			
			return ChdJson.toJson(list);
			
		}else{
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<HtcgChargeItemDicts> list = htcgChargeItemDictsMapper.queryHtcgChargeItemDicts(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
		}
	}


}
