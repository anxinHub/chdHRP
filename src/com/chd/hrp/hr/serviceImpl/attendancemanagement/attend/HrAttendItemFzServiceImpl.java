package com.chd.hrp.hr.serviceImpl.attendancemanagement.attend;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.hrp.hr.dao.attendancemanagement.attend.HrAttendItemFzMapper;
import com.chd.hrp.hr.dao.base.HrSelectMapper;
import com.chd.hrp.hr.entity.attendancemanagement.attend.HrAttendItemFz;
import com.chd.hrp.hr.service.attendancemanagement.attend.HrAttendItemFzService;
import com.chd.hrp.hr.service.base.HrBaseService;

/**
 * 考勤项目分组
 * 
 * @author Administrator
 *
 */
@Service("hrAttendItemFzService")
public class HrAttendItemFzServiceImpl implements HrAttendItemFzService {
	private static Logger logger = Logger.getLogger(HrAttendItemFzServiceImpl.class);

	@Resource(name = "hrAttendItemFzMapper")
	private final HrAttendItemFzMapper hrAttendItemFzMapper = null;

	@Resource(name = "hrBaseService")
	private final HrBaseService hrBaseService = null;
	
	@Resource(name = "hrSelectMapper")
	private final HrSelectMapper hrSelectMapper = null;
	// 默认显示20条数据
	RowBounds rowBoundsALL = new RowBounds(0, 20);

	/**
	 * 考勤项目增加
	 */
	@Override
	public String addAttendItemFz(Map<String, Object> entityMap) throws DataAccessException {

		String attend_code = "";
		try {
			List<HrAttendItemFz> list =hrAttendItemFzMapper.queryByCodeItem(entityMap);
			if (list!=null) {
				for (HrAttendItemFz hrAttendItemFz : list) {

					if (hrAttendItemFz.getAttend_code_fz().equals(entityMap.get("attend_code_fz").toString())) {
						return "{\"error\":\"编码：" + hrAttendItemFz.getAttend_code_fz().toString() + "已存在.\"}";
					}
					if (hrAttendItemFz.getAttend_name_fz().equals(entityMap.get("attend_name_fz").toString())) {
						return "{\"error\":\"名称：" +hrAttendItemFz.getAttend_name_fz().toString() + "已存在.\"}";
					}
					
				}
			}
		/*else{
				entityMap.put("attend_item", entityMap.get("attend_result_is").toString());
			}*/
			
			
			int state = hrAttendItemFzMapper.addAttendItemFz(entityMap);
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}

	}

	/**
	 * @Description 更新Dept
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String updateAttendItemFz(Map<String, Object> entityMap) throws DataAccessException {
		try {
			hrAttendItemFzMapper.updateAttendItemFz(entityMap);
			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";
		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}
		
	}
	/**
	 * 考勤项目删除
	 */
	@Override
	public String deleteAttendItemFz(List<HrAttendItemFz> entityList) throws DataAccessException {

		try {

			hrAttendItemFzMapper.deleteAttendItemFz(entityList);

			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}
	}

	/**
	 * 考勤项目查询
	 */
	@SuppressWarnings("unchecked")
	@Override
	public String queryAttendItemFz(Map<String, Object> entityMap) throws DataAccessException {


		List<HrAttendItemFz> list = (List<HrAttendItemFz>) hrAttendItemFzMapper.query(entityMap);

		return ChdJson.toJson(list);


	}




	@Override
	public HrAttendItemFz queryByCodeAttendItemFz(Map<String, Object> entityMap) {
		return hrAttendItemFzMapper.queryByCode(entityMap);
	}


	


}
