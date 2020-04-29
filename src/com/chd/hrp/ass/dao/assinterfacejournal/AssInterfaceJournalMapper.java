package com.chd.hrp.ass.dao.assinterfacejournal;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;

import com.chd.base.SqlMapper;

public interface AssInterfaceJournalMapper extends SqlMapper {
	/**
	 * 查询
	 * @param mapVo
	 * @return
	 */
	public List<Map<String, Object>> queryAssInterfaceJournal(Map<String, Object> mapVo);
	public List<Map<String, Object>> queryAssInterfaceJournal(Map<String, Object> mapVo,RowBounds rowBounds);
	
	public Map<String, Object> queryAssInterfaceJournalByID(Map<String, Object> mapVo);
	
   /**
    * 插入
    * @param mapVo
    * @return
    */
	public int insertAssInterfaceJournal(Map<String, Object> mapVo);
	/**
	 * 更新
	 * @param mapVo
	 * @return
	 */
	public int updateAssInterfaceJournal(Map<String, Object> mapVo);
	
	public List<Map<String, Object>> queryPItype(Map<String, Object> mapVo);
	
	public Map<String, Object> queryInterfaceType(Map<String, Object> mapVo);
}
