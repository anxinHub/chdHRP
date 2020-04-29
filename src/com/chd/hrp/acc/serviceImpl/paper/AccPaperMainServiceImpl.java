package com.chd.hrp.acc.serviceImpl.paper;
/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/

import java.util.ArrayList;
import java.util.HashMap;
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
import com.chd.hrp.acc.dao.paper.AccPaperDetailMapper;
import com.chd.hrp.acc.dao.paper.AccPaperMainMapper;
import com.chd.hrp.acc.dao.paper.AccPaperTypeMapper;
import com.chd.hrp.acc.service.paper.AccPaperMainService;
import com.github.pagehelper.PageInfo;

@Service("accPaperMainService")
public class AccPaperMainServiceImpl implements AccPaperMainService {

	private static Logger logger = Logger.getLogger(AccPaperMainServiceImpl.class);

	@Resource(name = "accPaperMainMapper")
	private final AccPaperMainMapper accPaperMainMapper = null;

	@Resource(name = "accPaperDetailMapper")
	private final AccPaperDetailMapper accPaperDetailMapper = null;

	@Resource(name = "accPaperTypeMapper")
	private final AccPaperTypeMapper accPaperTypeMapper = null;

	@Override
	public String addAccPaperMain(Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		try {

			Long begin_num = Long.parseLong(entityMap.get("begin_num").toString()); // 开始号码

			Long end_num = Long.parseLong(entityMap.get("end_num").toString()); // 结束号码

			entityMap.put("amount", (end_num - begin_num) + 1);

			/*
			 * 明细数据开始号码和结束号码只许录入一次
			 */
			Map<String, Object> map = new HashMap<String, Object>();

			map.put("group_id", entityMap.get("group_id"));

			map.put("hos_id", entityMap.get("hos_id"));

			map.put("copy_code", entityMap.get("copy_code"));

			map.put("type_code", entityMap.get("type_code"));

			map.put("begin_num", begin_num);

			map.put("end_num", end_num);

			map.put("begin_end", "1");

			List<Map<String, Object>> list = accPaperMainMapper.queryAccPaperMain(map);

			if (list.size() > 0) {

				return "{\"error\":\"添加失败：开始号码或结束号码重复.\",\"state\":\"true\"}";
			}

			String paper_prefix = "";
			/*
			 * 获取票据类型后缀 票据号
			 */
			Map<String, Object> paperMap = accPaperTypeMapper.queryAccPaperTypeByCode(entityMap);

			if (paperMap.get("paper_prefix") != null) {

				paper_prefix = paperMap.get("paper_prefix").toString(); // 后缀

			}

			int paper_clen_lenth = Integer.parseInt(paperMap.get("paper_clen").toString()); // 票据号长度

			if (entityMap.get("begin_num").toString().length() > paper_clen_lenth) {

				return "{\"error\":\"添加失败：开始长度不能大于票据号长度.\",\"state\":\"true\"}";

			}

			if (entityMap.get("end_num").toString().length() > paper_clen_lenth) {

				return "{\"error\":\"添加失败：结束长度不能大于票据号长度.\",\"state\":\"true\"}";

			}

			List<Map<String, Object>> addlist = new ArrayList<Map<String, Object>>();

			Map<String, Object> v_map = new HashMap<String, Object>();

			v_map.put("begin_num", begin_num);

			v_map.put("end_num", end_num);

			v_map.put("paper_clen_lenth", paper_clen_lenth);

			Map n_map = getNumMap(entityMap, v_map);

			// int pid = accPaperMainMapper.queryAccPaperMainSeq();
			int pid = accPaperMainMapper.queryAccPaperMainPid(entityMap) + 1;

			for (Long i = begin_num; i <= end_num; i++) {

				Map<String, Object> detailMap = new HashMap<String, Object>();

				detailMap.put("group_id", entityMap.get("group_id"));

				detailMap.put("hos_id", entityMap.get("hos_id"));

				detailMap.put("copy_code", entityMap.get("copy_code"));

				detailMap.put("pid", pid);

				detailMap.put("type_code", entityMap.get("type_code"));

				detailMap.put("paper_num", paper_prefix + n_map.get(begin_num));

				detailMap.put("out_user_id1", null);

				detailMap.put("out_date1", null);

				detailMap.put("out_user_id2", null);

				detailMap.put("out_date2", null);

				detailMap.put("inva_user_id", null);

				detailMap.put("inva_date", null);

				detailMap.put("state1", 1); // 一次领用 1新建、2领用、3作废

				detailMap.put("note", entityMap.get("note"));

				detailMap.put("state2", 1); // 二次领用 1新建、2领用、3作废

				detailMap.put("check_user_id", null);

				detailMap.put("check_date", null);

				detailMap.put("is_check", "0");

				addlist.add(detailMap);

				begin_num++;

			}

			String begin_num_s = entityMap.get("begin_num").toString(); // 开始号码

			String end_num_s = entityMap.get("end_num").toString(); // 结束号码

			for (int j = 1; j <= paper_clen_lenth; j++) {

				begin_num_s = "0" + begin_num_s;

				end_num_s = "0" + end_num_s;

			}

			entityMap.put("begin_num", begin_num_s.substring(begin_num_s.length() - paper_clen_lenth));

			entityMap.put("end_num", end_num_s.substring(end_num_s.length() - paper_clen_lenth));

			entityMap.put("pid", pid);

			accPaperMainMapper.addAccPaperMain(entityMap);

			accPaperDetailMapper.addBatchAccPaperDetail(addlist);

			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}
	}

	public Map<Long, String> getNumMap(Map<String, Object> entityMap, Map<String, Object> v_map) {

		Map<Long, String> numMap = new HashMap<Long, String>();// 返回序列号结果集

		String paper_num = "";// 初始化 的序列号

		Long bejinNum = Long.parseLong(v_map.get("begin_num").toString());

		Long endNum = Long.parseLong(v_map.get("end_num").toString());

		Integer paper_clen_lenth = Integer.parseInt(v_map.get("paper_clen_lenth").toString());

		for (Long i = bejinNum; i <= endNum; i++) {

			paper_num = String.valueOf(bejinNum);

			for (int j = 1; j <= paper_clen_lenth; j++) {

				paper_num = "0" + paper_num;

			}

			numMap.put(i, paper_num.substring(paper_num.length() - paper_clen_lenth));

			bejinNum++;
		}

		return numMap;
	}

	/*
	 * public Map<Integer,String> getNumMap(Map<String, Object>
	 * entityMap,Map<String, Object> v_map){
	 * 
	 * Map<Integer,String> numMap = new HashMap<Integer,String>();//返回序列号结果集
	 * 
	 * String paper_num = "";//初始化 的序列号
	 * 
	 * Integer bejinNum = Integer.parseInt(v_map.get("begin_num").toString());
	 * 
	 * Integer endNum = Integer.parseInt(v_map.get("end_num").toString());
	 * 
	 * for(int i=bejinNum;i<=endNum;i++){
	 * 
	 * Integer paper_clen_lenth =
	 * Integer.parseInt(v_map.get("paper_clen_lenth").toString());
	 * 
	 * if(i==bejinNum){
	 * 
	 * paper_num = accPaperDetailMapper.queryMaxAccPaperMainNum(entityMap);
	 * 
	 * if( null ==paper_num){
	 * 
	 * String paper_number = "";
	 * 
	 * for (int j = 1; j <= paper_clen_lenth; j++) {
	 * 
	 * if(j == paper_clen_lenth){
	 * 
	 * paper_number = paper_number + '1';
	 * 
	 * }else {
	 * 
	 * paper_number = paper_number + '0';
	 * 
	 * }
	 * 
	 * }
	 * 
	 * paper_num = paper_number;
	 * 
	 * numMap.put(i, paper_num); }else{
	 * 
	 * String returnNumString = "";
	 * 
	 * Long paper_num_long = Long.parseLong(paper_num)+ 1;
	 * 
	 * for(int j = 1; j < paper_clen_lenth; j++){
	 * 
	 * returnNumString = returnNumString+"0";
	 * 
	 * }
	 * 
	 * paper_num = returnNumString+paper_num_long;
	 * 
	 * numMap.put(i, paper_num.substring(paper_num.length() - paper_clen_lenth));
	 * 
	 * }
	 * 
	 * }else{
	 * 
	 * String returnNumString = "";
	 * 
	 * Long paper_num_long = Long.parseLong(paper_num)+ 1;
	 * 
	 * for(int j = 1; j < paper_clen_lenth; j++){
	 * 
	 * returnNumString = returnNumString+"0";
	 * 
	 * }
	 * 
	 * paper_num = returnNumString+paper_num_long;
	 * 
	 * numMap.put(i, paper_num.substring(paper_num.length() - paper_clen_lenth));
	 * 
	 * }
	 * 
	 * 
	 * }
	 * 
	 * 
	 * return numMap; }
	 */
	@Override
	public String addBatchAccPaperMain(List<Map<String, Object>> list) throws DataAccessException {
		// TODO Auto-generated method stub
		try {

			accPaperMainMapper.addBatchAccPaperMain(list);

			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码 addBatchAccPaperType\"}";

		}
	}

	@Override
	public String updateAccPaperMain(Map<String, Object> entityMap) throws DataAccessException {
		try {
			List<Map<String, Object>> list = new ArrayList<>();
			list.add(entityMap);

			deleteBatchAccPaperMain(list);
			String string = addAccPaperMain(entityMap);
			if (string.contains("error")) {
				throw new SysException(string);
			}

			return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage(),e.getCause());
			//return "{\"error\":\"修改失败 数据库异常 请联系管理员! 错误编码 updateAccPaperType\"}";
		}
	}

	@Override
	public String deleteBatchAccPaperMain(List<Map<String, Object>> list) throws DataAccessException {
		try {
			accPaperDetailMapper.deleteBatchAccPaperDetail(list);
			accPaperMainMapper.deleteBatchAccPaperMain(list);
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage(),e.getCause());
		}
	}

	@Override
	public String queryAccPaperMain(Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		if (sysPage.getTotal() == -1) {

			List<Map<String, Object>> list = accPaperMainMapper.queryAccPaperMain(entityMap);

			return ChdJson.toJson(list);

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

			List<Map<String, Object>> list = accPaperMainMapper.queryAccPaperMain(entityMap, rowBounds);

			PageInfo page = new PageInfo(list);

			return ChdJson.toJson(list, page.getTotal());

		}

	}

	@Override
	public Map<String, Object> queryAccPaperMainByCode(Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		return accPaperMainMapper.queryAccPaperMainByCode(entityMap);
	}

	@Override
	public List<Map<String, Object>> queryAccPaperMainPrint(Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		List<Map<String, Object>> list = accPaperMainMapper.queryAccPaperMain(entityMap);

		return list;
	}

}
