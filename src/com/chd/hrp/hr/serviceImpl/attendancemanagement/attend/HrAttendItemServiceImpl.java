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

import com.alibaba.fastjson.JSON;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.base.util.StringTool;
import com.chd.hrp.hr.dao.attendancemanagement.attend.HrAttendItemMapper;
import com.chd.hrp.hr.dao.base.HrSelectMapper;
import com.chd.hrp.hr.entity.attendancemanagement.attend.HrAttendItem;
import com.chd.hrp.hr.service.attendancemanagement.attend.HrAttendItemService;
import com.chd.hrp.hr.service.base.HrBaseService;

/**
 * 考勤项目
 * 
 * @author Administrator
 *
 */
@Service("hrAttendItemService")
public class HrAttendItemServiceImpl implements HrAttendItemService {
	private static Logger logger = Logger.getLogger(HrAttendItemServiceImpl.class);

	@Resource(name = "hrAttendItemMapper")
	private final HrAttendItemMapper hrAttendItemMapper = null;

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
	public String addAttendItem(Map<String, Object> entityMap) throws DataAccessException {

		String attend_code = "";
		try {
			List<HrAttendItem> list = hrAttendItemMapper.queryByCodeItem(entityMap);
			if (list != null) {
				for (HrAttendItem hrAttendItem : list) {
					if (hrAttendItem.getAttend_code().equals(entityMap.get("attend_code").toString())) {
						return "{\"error\":\"项目编码：" + hrAttendItem.getAttend_code().toString() + "已存在.\"}";
					}
					if (hrAttendItem.getAttend_name().equals(entityMap.get("attend_name"))) {
						return "{\"error\":\"项目名称：" + hrAttendItem.getAttend_name().toString() + "已存在.\"}";
					}
					if (hrAttendItem.getAttend_shortname().equals(entityMap.get("attend_shortname"))) {
						return "{\"error\":\"项目简称：" + hrAttendItem.getAttend_shortname().toString() + "已存在.\"}";
					}
				}
			}
			
			//生成考勤列item
			List<Map<String, Object>> attend_item1 = hrAttendItemMapper.queryAttendResult(entityMap);
			Map<String, String> keyMap = new HashMap<String, String>();
			for (Map<String, Object> map : attend_item1) {
				keyMap.put(map.get("attend_item").toString(), "00");
			}
			
			for(int i = 1; 1 < 200; i++){
				if(!keyMap.containsKey("item" + i)){
					entityMap.put("attend_item", "item" + i);
					break;
				}
			}

			// 看是否有积休项目
			if ("1".equals(entityMap.get("is_jx"))) {
				int count = hrAttendItemMapper.queryAttendItemIsJx(entityMap);
				if (count > 0) {
					return "{\"error\":\"已经存在积休项目，不能再次设置！\"}";
				}
			}
			// 看是否默认考勤项目
			if ("1".equals(entityMap.get("is_default"))) {
				int count = hrAttendItemMapper.queryAttendItemIsDefault(entityMap);
				if (count > 0) {
					return "{\"error\":\"已经存在默认考勤项目，不能再次设置！\"}";
				}
			}
			// 拼音码
			entityMap.put("spell", StringTool.toPinyinShouZiMu(entityMap.get("attend_name").toString()));

			int state = hrAttendItemMapper.addAttendItem(entityMap);
			return "{\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}
	}

	/**
	 * @Description 更新AttendItem
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String updateAttendItem(Map<String, Object> entityMap) throws DataAccessException {
		try {
			// 名字是否重复
			List<HrAttendItem> list = hrAttendItemMapper.queryByCodeItemName(entityMap);
			if (list != null) {
				for (HrAttendItem hrAttendItem : list) {
					if (hrAttendItem.getAttend_name().equals(entityMap.get("attend_name"))) {
						return "{\"error\":\"项目名称：" + hrAttendItem.getAttend_name().toString() + "已存在.\"}";
					}
					
					if (hrAttendItem.getAttend_name().equals(entityMap.get("attend_shortname"))) {
						return "{\"error\":\"简称：" + hrAttendItem.getAttend_shortname().toString() + "已存在.\"}";
					}
				}
			}
			
			// 看是否有积休项目
			if ("1".equals(entityMap.get("is_jx"))) {
				int count = hrAttendItemMapper.queryAttendItemIsJx(entityMap);
				if (count > 0) {
					return "{\"error\":\"已经存在积休项目，不能再次设置！\"}";
				}
			}
			
			// 看是否默认考勤项目
			if ("1".equals(entityMap.get("is_default"))) {
				int count = hrAttendItemMapper.queryAttendItemIsDefault(entityMap);
				if (count > 0) {
					return "{\"error\":\"已经存在默认考勤项目，不能再次设置！\"}";
				}
			}

			//生成考勤列item
			List<Map<String, Object>> attend_item1 = hrAttendItemMapper.queryAttendResult(entityMap);
			Map<String, String> keyMap = new HashMap<String, String>();
			for (Map<String, Object> map : attend_item1) {
				keyMap.put(map.get("attend_item").toString(), "00");
			}
			
			for(int i = 1; 1 < 200; i++){
				if(!keyMap.containsKey("item" + i)){
					entityMap.put("attend_item", "item" + i);
					break;
				}
			}

			hrAttendItemMapper.updateAttendItem(entityMap);
			return "{\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}
	}

	/**
	 * 考勤项目删除
	 */
	@Override
	public String deleteAttendItem(List<HrAttendItem> entityList) throws DataAccessException {

		try {

			hrAttendItemMapper.deleteAttendItem(entityList);

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
	public String queryAttendItem(Map<String, Object> entityMap) throws DataAccessException {

		List<HrAttendItem> list = (List<HrAttendItem>) hrAttendItemMapper.query(entityMap);

		return ChdJson.toJson(list);

	}

	@Override
	public String queryHosName(Map<String, Object> entityMap) throws DataAccessException {
		return JSON.toJSONString(hrAttendItemMapper.queryHosName(entityMap));
	}

	@Override
	public HrAttendItem queryByCodeAttendItem(Map<String, Object> entityMap) {
		return hrAttendItemMapper.queryByCode(entityMap);
	}

	@Override
	public String queryHrFiiedData(Map<String, Object> entityMap) throws DataAccessException {
		List<Map<String, Object>> options = new ArrayList<Map<String, Object>>();

		hrSelectMapper.queryHrFiiedDataDicByTabCol(entityMap);
		List<Map<String, Object>> fiiedDataList = (List<Map<String, Object>>) entityMap.get("resultlist");
		for (Map<String, Object> map : fiiedDataList) {
			Map<String, Object> option = new HashMap<String, Object>();
			option.put("id", map.get("field_col_code"));
			option.put("text", map.get("field_col_name"));
			option.put("label", map.get("field_col_name"));
			options.add(option);
		}

		return JSON.toJSONString(options);
	}

	@Override
	public List<Map<String, Object>> queryAttendItemByPrint(Map<String, Object> entityMap) throws DataAccessException {
		return hrAttendItemMapper.queryAttendItemByPrint(entityMap);
	}

	@Override
	public String deleteAttendItem(Map<String, Object> entityMap) throws DataAccessException{
		try {
			hrAttendItemMapper.delete(entityMap);
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}
	}
	
	


}
