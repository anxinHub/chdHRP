package com.chd.hrp.hr.serviceImpl.attendancemanagement.attend;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.chd.base.SessionManager;
import com.chd.base.SysPage;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.hrp.hr.dao.attendancemanagement.attend.HrOfficialHolidayMapper;
import com.chd.hrp.hr.entity.attendancemanagement.attend.HrOfficialHoliday;
import com.chd.hrp.hr.service.attendancemanagement.attend.HrOfficialHolidayService;
import com.github.pagehelper.PageInfo;

/**
 * 法定节假日设置
 * 
 * @author Administrator
 *
 */
@Service("hrOfficialHolidayService")
public class HrOfficialHolidayServiceImpl implements HrOfficialHolidayService {
	private static Logger logger = Logger.getLogger(HrOfficialHolidayServiceImpl.class);

	@Resource(name = "hrOfficialHolidayMapper")
	private final HrOfficialHolidayMapper hrOfficialHolidayMapper = null;

	// 默认显示20条数据
	RowBounds rowBoundsALL = new RowBounds(0, 20);

	/**
	 * 法定节假日设置增加
	 */
	@Override
	public String addOfficialHoliday(Map<String, Object> entityMap) throws DataAccessException {
		try {
			// 获取对象
			List<HrOfficialHoliday> list = JSONArray.parseArray(String.valueOf(entityMap.get("paramVo")),
					HrOfficialHoliday.class);
			List<HrOfficialHoliday> slist = new ArrayList<HrOfficialHoliday>();
			if (list.size() > 0) {
				for (HrOfficialHoliday hrOfficialHoliday : list) {
					hrOfficialHoliday.setGroup_id(Long.parseLong(SessionManager.getGroupId()));
					hrOfficialHoliday.setHos_id(Long.parseLong(SessionManager.getHosId()));
					if(StringUtils.isNotEmpty(hrOfficialHoliday.getAttend_date_state())){
						slist.add(hrOfficialHoliday);
					}
				}
				
				hrOfficialHolidayMapper.deleteOfficialHoliday(list);
				if(slist.size() > 0){
					hrOfficialHolidayMapper.addBatch(slist);
				}
			}
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}
	}

	/**
	 * 法定节假日设置修改
	 */
	@Override
	public String updateOfficialHoliday(Map<String, Object> entityMap) throws DataAccessException {

		try {

			@SuppressWarnings("unused")
			int state = hrOfficialHolidayMapper.update(entityMap);

			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}
	}

	/**
	 * 法定节假日设置删除
	 */
	@Override
	public String deleteOfficialHoliday(List<HrOfficialHoliday> entityList) throws DataAccessException {

		try {

			hrOfficialHolidayMapper.deleteOfficialHoliday(entityList);

			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}
	}

	/**
	 * 法定节假日设置查询
	 */
	@SuppressWarnings("unchecked")
	@Override
	public String queryOfficialHoliday(Map<String, Object> entityMap) throws DataAccessException {

		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		if (sysPage.getTotal() == -1) {

			List<HrOfficialHoliday> list = (List<HrOfficialHoliday>) hrOfficialHolidayMapper.query(entityMap);

			return ChdJson.toJson(list);

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

			List<HrOfficialHoliday> list = (List<HrOfficialHoliday>) hrOfficialHolidayMapper.query(entityMap,
					rowBounds);

			@SuppressWarnings("rawtypes")
			PageInfo page = new PageInfo(list);

			return ChdJson.toJson(list, page.getTotal());

		}

	}

	/**
	 * 跳转修改页面
	 */
	@Override
	public HrOfficialHoliday queryByCodeOfficialHoliday(Map<String, Object> entityMap) throws DataAccessException {
		return hrOfficialHolidayMapper.queryByCode(entityMap);
	}


}
