package com.chd.hrp.acc.service.books;

import java.util.List;
import java.util.Map;

public interface AccBookPrintService {

	//查询
	public String queryAccBooksPrintSubj(Map<String, Object> mapVo);
	
	//查询打印结果
	public Map<String, Object> collectAccBooksPrint(Map<String, Object> mapVo);
}
