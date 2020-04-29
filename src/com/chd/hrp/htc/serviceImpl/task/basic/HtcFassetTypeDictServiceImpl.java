package com.chd.hrp.htc.serviceImpl.task.basic;
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
import com.chd.hrp.htc.dao.task.basic.HtcFassetTypeDictMapper;
import com.chd.hrp.htc.entity.task.basic.HtcFassetTypeDict;
import com.chd.hrp.htc.service.task.basic.HtcFassetTypeDictService;
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
@Service("htcFassetTypeDictService")
public class HtcFassetTypeDictServiceImpl implements HtcFassetTypeDictService {

	private static Logger logger = Logger.getLogger(HtcFassetTypeDictServiceImpl.class);
	
	@Resource(name = "htcFassetTypeDictMapper")
	private final HtcFassetTypeDictMapper htcFassetTypeDictMapper = null;
    
	@Override
	public String addHtcFassetTypeDict(Map<String,Object> entityMap)throws DataAccessException{
		
		try{
			
			HtcFassetTypeDict htcFassetTypeDict =  htcFassetTypeDictMapper.queryHtcFassetTypeDictByCode(entityMap);
			
			if(null != htcFassetTypeDict){
				if (htcFassetTypeDict.getAsset_type_code().equals(entityMap.get("asset_type_code"))) {
					return "{\"error\":\"资产类别编码重复.\",\"state\":\"false\"}";
				}
			}
			
			htcFassetTypeDictMapper.addHtcFassetTypeDict(entityMap);
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";
		}catch(Exception e){
			
			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"添加失败\"}");
		}
	}
	
	@Override
	public String queryHtcFassetTypeDict(Map<String,Object> entityMap) throws DataAccessException{
		SysPage sysPage = new SysPage();
		sysPage = (SysPage) entityMap.get("sysPage");
		if(sysPage.getTotal() == -1){
			List<HtcFassetTypeDict> list = htcFassetTypeDictMapper.queryHtcFassetTypeDict(entityMap);
			
			return ChdJson.toJson(list);
		}else{
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			
			List<HtcFassetTypeDict> list = htcFassetTypeDictMapper.queryHtcFassetTypeDict(entityMap, rowBounds);
			
			PageInfo page = new PageInfo(list);
			
			return ChdJson.toJson(list, page.getTotal());
		}
	}
	
	@Override
	public HtcFassetTypeDict queryHtcFassetTypeDictByCode(Map<String,Object> entityMap)throws DataAccessException{
		return htcFassetTypeDictMapper.queryHtcFassetTypeDictByCode(entityMap);
	}

	
	@Override
    public String deleteHtcFassetTypeDict(Map<String, Object> entityMap) throws DataAccessException {
		try {
			htcFassetTypeDictMapper.deleteHtcFassetTypeDict(entityMap);
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage(), e);
			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码 deleteHtcFassetTypeDict\"}";
		}
    }
	
	@Override
	public String deleteBatchHtcFassetTypeDict(List<Map<String, Object>> entityList)throws DataAccessException{
		try {
			htcFassetTypeDictMapper.deleteBatchHtcFassetTypeDict(entityList);
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"删除失败\"}");
		}
	}
	
	@Override
	public String updateHtcFassetTypeDict(Map<String,Object> entityMap)throws DataAccessException{
		try {
			htcFassetTypeDictMapper.updateHtcFassetTypeDict(entityMap);
			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"修改失败\"}");
		}
	}

	@Override
	public String synchroHtcFassetTypeDict(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		try {
			htcFassetTypeDictMapper.synchroHtcFassetTypeDict(entityMap);
			return "{\"msg\":\"同步成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"同步失败\"}");
		}
	}

}
