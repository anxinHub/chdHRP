package com.chd.hrp.hr.service.ss;
import java.util.*;

import org.springframework.dao.DataAccessException;


public interface SeqManageService {

	  //查询  
	 String querySeqManage(Map<String, Object> mapVo) throws DataAccessException;
		
		//新增
	 String addSeqManage(Map<String, Object> mapVo) throws DataAccessException;
		//删除
	 String deleteSeqManage(List<Map<String, Object>> listVo) throws DataAccessException;
	 //生成
	 String createSeqManage(Map<String, Object> mapVo)throws DataAccessException;
	 //删除序列
	 String dropSeqManage(List<Map<String, Object>> listVo) throws DataAccessException;
}
