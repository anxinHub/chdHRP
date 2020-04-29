/**
 * @Copyright: Copyright (c) 2015-2-14 
 * @Company: 智慧云康（北京）数据科技有限公司
 */

package com.chd.hrp.acc.serviceImpl.InternetBank;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.chd.base.SysPage;
import com.chd.base.util.ChdJson;
import com.chd.hrp.acc.dao.InternetBank.AccBankNetCommonMapper;
import com.chd.hrp.acc.service.InternetBank.AccBankNetCommonService;
import com.github.pagehelper.PageInfo;

/**
 * @Title. @Description<BR>
 * @Author: LiuYingDuo
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Service("accBankNetCommonService")
public class AccBankNetCommonServiceImpl implements AccBankNetCommonService {

	private static Logger logger = Logger
			.getLogger(AccBankNetCommonServiceImpl.class);

	@Resource(name = "accBankNetCommonMapper")
	private final AccBankNetCommonMapper accBankNetCommonMapper = null;

	@Override
	public String queryAccBankForInternet(Map<String, Object> entityMap)
			throws DataAccessException {

		List<Map<String, Object>> list = accBankNetCommonMapper
				.queryAccBankForInternet(entityMap);

		return ChdJson.toJsonLower(list);
	}

	@Override
	public String querySupBankForInternet(Map<String, Object> entityMap)
			throws DataAccessException {

		List<Map<String, Object>> list = accBankNetCommonMapper
				.querySupBankForInternet(entityMap);

		return ChdJson.toJsonLower(list);
	}

	@Override
	public String queryAccBankNetICBCCode(Map<String, Object> entityMap)
			throws DataAccessException {

		RowBounds rowBounds = new RowBounds(0, 20);

		return JSON.toJSONString(accBankNetCommonMapper
				.queryAccBankNetICBCCode(entityMap, rowBounds));
	}

	@Override
	public String queryAccWageItem(Map<String, Object> entityMap)
			throws DataAccessException {
		RowBounds rowBounds = new RowBounds(0, 20);

		return JSON.toJSONString(accBankNetCommonMapper.queryAccWageItem(
				entityMap, rowBounds));
	}

	@Override
	public List<Map<String, Object>> queryAccICBCIBPS(
			Map<String, Object> entityMap) throws DataAccessException {

		List<Map<String, Object>> list = accBankNetCommonMapper
				.queryAccICBCIBPSMain(entityMap);

		return list;
	}

	@Override
	public String queryAccICBCIBPSMain(Map<String, Object> entityMap)
			throws DataAccessException {

		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		if (sysPage.getTotal() == -1) {

			List<Map<String, Object>> list = accBankNetCommonMapper
					.queryAccICBCIBPSMain(entityMap);

			return ChdJson.toJson(list);

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(),
					sysPage.getPagesize());

			List<Map<String, Object>> list = accBankNetCommonMapper
					.queryAccICBCIBPSMain(entityMap, rowBounds);

			PageInfo page = new PageInfo(list);

			return ChdJson.toJson(list, page.getTotal());
		}
	}

	@Override
	public int updateBatchICBCIBPS(List<Map<String, Object>> entityMap)
			throws DataAccessException {
		return accBankNetCommonMapper.updateBatchICBCIBPS(entityMap);
	}

	@Override
	public int addBatchICBCIBPS(List<Map<String, Object>> entityMap)
			throws DataAccessException {
		return accBankNetCommonMapper.addBatchICBCIBPS(entityMap);
	}

	@Override
	public int deleteBatchICBCIBPS() throws DataAccessException {
		return accBankNetCommonMapper.deleteBatchICBCIBPS();
	}

	@Override
	public String queryAccICBCIBPSCode(Map<String, Object> entityMap)
			throws DataAccessException {

		RowBounds rowBounds = new RowBounds(0, 50);

		return JSON.toJSONString(accBankNetCommonMapper.queryAccICBCIBPSCode(
				entityMap, rowBounds));
	}

	@Override
	public int getCharCount(String s) {
		int length = 0;
		for (int i = 0; i < s.length(); i++) {
			int ascii = Character.codePointAt(s, i);
			if (ascii >= 0 && ascii <= 255)
				length++;
			else
				length += 2;

		}
		return length;
	}

	@Override
	public String subChar(String s, int length) throws Exception {
		byte[] bytes = s.getBytes("Unicode");
		int n = 0; // 表示当前的字节数
		int i = 2; // 要截取的字节数，从第3个字节开始
		for (; i < bytes.length && n < length; i++) {
			// 奇数位置，如3、5、7等，为UCS2编码中两个字节的第二个字节
			if (i % 2 == 1) {
				n++; // 在UCS2第二个字节时n加1
			} else {
				// 当UCS2编码的第一个字节不等于0时，该UCS2字符为汉字，一个汉字算两个字节
				if (bytes[i] != 0) {
					n++;
				}
			}
		}
		// 如果i为奇数时，处理成偶数
		if (i % 2 == 1)

		{
			// 该UCS2字符是汉字时，去掉这个截一半的汉字
			if (bytes[i - 1] != 0)
				i = i - 1;
			// 该UCS2字符是字母或数字，则保留该字符
			else
				i = i + 1;
		}

		return new String(bytes, 0, i, "Unicode");
	}
}
