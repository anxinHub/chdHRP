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
import com.chd.hrp.htcg.dao.setout.HtcgMedicalAdvicePmergeMapper;
import com.chd.hrp.htcg.entity.setout.HtcgMedicalAdvicePmerge;
import com.chd.hrp.htcg.service.setout.HtcgMedicalAdvicePmergeService;
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
@Service("htcgMedicalAdvicePmergeService")
public class HtcgMedicalAdvicePmergeServiceImpl implements HtcgMedicalAdvicePmergeService {

	private static Logger logger = Logger.getLogger(HtcgMedicalAdvicePmergeServiceImpl.class);
	
	@Resource(name = "htcgMedicalAdvicePmergeMapper")
	private final HtcgMedicalAdvicePmergeMapper htcgMedicalAdvicePmergeMapper = null;

	@Override
	public String initHtcgMedicalAdvicePmerge(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		  try {
				
			    htcgMedicalAdvicePmergeMapper.initHtcgMedicalAdvicePmerge(entityMap);
			  
				return "{\"msg\":\""+entityMap.get("msg")+".\",\"state\":\"true\"}";
				
			} catch (Exception e) {
				// TODO: handle exception
				logger.error(e.getMessage(), e);
				throw new SysException("{\"error\":\""+entityMap.get("msg")+"失败\"}");
			}
	}

	@Override
	public String queryHtcgMedicalAdvicePmerge(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		SysPage sysPage = new SysPage();
		sysPage = (SysPage) entityMap.get("sysPage");
		if (sysPage.getTotal() == -1) {
			List<HtcgMedicalAdvicePmerge> list = htcgMedicalAdvicePmergeMapper.queryHtcgMedicalAdvicePmerge(entityMap);
			return ChdJson.toJson(list);
		} else {
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			List<HtcgMedicalAdvicePmerge> list = htcgMedicalAdvicePmergeMapper.queryHtcgMedicalAdvicePmerge(entityMap, rowBounds);
			PageInfo page = new PageInfo(list);
			return ChdJson.toJson(list, page.getTotal());
		}
	}

	@Override
	public String deleteBathcHtcgMedicalAdvicePmerge(
			List<Map<String, Object>> list) throws DataAccessException {
		// TODO Auto-generated method stub
		 try {
			    htcgMedicalAdvicePmergeMapper.deleteBathcHtcgMedicalAdvicePmerge(list);
				return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
			} catch (Exception e) {
				// TODO: handle exception
				logger.error(e.getMessage(), e);
				throw new SysException("{\"error\":\"删除失败\"}");
			}
	}
}
