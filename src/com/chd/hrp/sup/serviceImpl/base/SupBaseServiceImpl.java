package com.chd.hrp.sup.serviceImpl.base;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.SysPage;
import com.chd.base.util.ChdJson;
import com.chd.base.util.DateUtil;
import com.chd.hrp.sup.dao.base.SupBaseMapper;
import com.chd.hrp.sup.service.base.SupBaseService;
import com.github.pagehelper.PageInfo;

@Service("supBaseService")
public class SupBaseServiceImpl implements SupBaseService {
private static Logger logger = Logger.getLogger(SupBaseServiceImpl.class);
	
	@Resource(name = "supBaseMapper")
	private final SupBaseMapper supBaseMapper = null;
	//材料字典列表(有材料库存)
		@Override
		public String queryMatInvList(Map<String, Object> entityMap) throws DataAccessException {

			SysPage sysPage = new SysPage();
			sysPage = (SysPage) entityMap.get("sysPage");
			
			if (sysPage.getTotal()==-1){
				List<?> list = supBaseMapper.queryMatInvList(entityMap);
				
				return ChdJson.toJson(list);
			}else{
				RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
				List<?> list = supBaseMapper.queryMatInvList(entityMap, rowBounds);
				PageInfo page = new PageInfo(list);
				
				return ChdJson.toJson(list, page.getTotal());
			}
		}
		@Override
        public String querySupDict(Map<String, Object> entityMap) throws DataAccessException {
			SysPage sysPage = new SysPage();
			sysPage = (SysPage) entityMap.get("sysPage");
			
			if (sysPage.getTotal()==-1){
				List<?> list = supBaseMapper.querySupDict(entityMap);
				
				return ChdJson.toJson(list);
			}else{
				RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
				List<?> list = supBaseMapper.querySupDict(entityMap, rowBounds);
				PageInfo page = new PageInfo(list);
				
				return ChdJson.toJson(list, page.getTotal());
			}
        }
		@Override
        public List<?> querySupDictList(Map<String, Object> entityMap) throws DataAccessException {
	        return supBaseMapper.querySupDict(entityMap);
        }

		/**
		 * @Description 判断批号有效日期是否一致
		 * @param mapVo
		 * @param mode
		 * @return Map<String, Object>
		 * @throws Exception
		 */
		@Override
		public String queryMatInvBatchInva(Map<String,Object> entityMap) throws DataAccessException{
			try {	
				String inva_date = DateUtil.dateToString(supBaseMapper.queryMatInvBatchInva(entityMap), "yyyy-MM-dd");
				if(inva_date != null && !"".equals(inva_date) && ChdJson.validateJSON(String.valueOf(entityMap.get("inva_date"))) && !inva_date.equals(DateUtil.jsDateToString(entityMap.get("inva_date").toString(), "yyyy-MM-dd"))){
					return "{\"state\":\"false\",\"inva_date\":\""+inva_date+"\"}";
				}
			}
			catch (Exception e) {
				logger.error(e.getMessage(), e);

				return "{\"error\":\"操作失败 数据库异常 请联系管理员! 方法 queryMatInvBatchInva\"}";
			}	
			
			return "{\"state\":\"true\"}";
		}
		
		/**
		 * @Description 判断批号灭菌日期是否一致
		 * @param mapVo
		 * @param mode
		 * @return Map<String, Object>
		 * @throws Exception
		 */
		@Override
		public String queryMatInvBatchDisinfect(Map<String,Object> entityMap) throws DataAccessException{
			try {	
				String disinfect_date = DateUtil.dateToString(supBaseMapper.queryMatInvBatchDisinfect(entityMap), "yyyy-MM-dd");
				if(disinfect_date != null && !"".equals(disinfect_date) && ChdJson.validateJSON(String.valueOf(entityMap.get("disinfect_date"))) && !disinfect_date.equals(DateUtil.jsDateToString(entityMap.get("disinfect_date").toString(), "yyyy-MM-dd"))){
					return "{\"state\":\"false\",\"disinfect_date\":\""+disinfect_date+"\"}";
				}
			}
			catch (Exception e) {
				logger.error(e.getMessage(), e);

				return "{\"error\":\"操作失败 数据库异常 请联系管理员! 方法 queryMatInvBatchDisinfect\"}";
			}	
			return "{\"state\":\"true\"}";
		}
		/**
		 * @Description 判断批号单价是否一致
		 * @param mapVo
		 * @param mode
		 * @return Map<String, Object>
		 * @throws Exception
		 */
		@Override
		public String queryMatInvBatchPrice(Map<String,Object> entityMap) throws DataAccessException{
			try {	
				Double price = null;
				if(entityMap.get("type") != null && "affi".equals(entityMap.get("type").toString().toLowerCase())){
					//代销
					price = supBaseMapper.queryMatAffiInvBatchPrice(entityMap);
				}else{
					//非代销
					price = supBaseMapper.queryMatInvBatchPrice(entityMap);
				}
				if(price != null && !"".equals(price.toString()) && ChdJson.validateJSON(String.valueOf(entityMap.get("price"))) && price - Double.valueOf(entityMap.get("price").toString()) != 0){
					return "{\"state\":\"false\",\"price\":\""+price+"\"}";
				}
			}
			catch (Exception e) {
				logger.error(e.getMessage(), e);
				
				return "{\"error\":\"操作失败 数据库异常 请联系管理员! 方法 queryMatInvBatchPrice\"}";
			}	
			return "{\"state\":\"true\"}";
		}

}
