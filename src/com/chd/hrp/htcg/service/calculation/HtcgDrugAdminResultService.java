package com.chd.hrp.htcg.service.calculation;
import java.util.*;
import org.springframework.dao.DataAccessException;

public interface HtcgDrugAdminResultService {

	public String queryHtcgDrugAdminResult(Map<String,Object> entityMap) throws DataAccessException;
}
