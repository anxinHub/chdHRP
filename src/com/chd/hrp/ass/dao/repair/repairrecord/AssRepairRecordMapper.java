package com.chd.hrp.ass.dao.repair.repairrecord;

import java.util.List;
import java.util.Map;






















import org.apache.ibatis.session.RowBounds;

import com.chd.base.SqlMapper;

public interface AssRepairRecordMapper  extends SqlMapper{
   /**
    * 材料下拉框
    * @param entityMap
    * @param rowBounds
    * @return
    */

	List<Map<String, Object>> queryMatInvDictSelect(Map<String, Object> entityMap);

List<Map<String, Object>> queryMatInvDictSelectInfo(Map<String, Object> mapVo);


void addAssRepairRecord(Map<String, Object> mapVo);

List<Map<String, Object>> queryAssRepairRecord(Map<String, Object> mapVo);

Map<String, Object> queryAssRecordByCode(Map<String, Object> mapVo);

void addRecordRepairInv(Map<String, Object> invMap);

List<Map<String, Object>> queryImgUrlByRecordCode(Map<String, Object> mapVo);

List<Map<String, Object>> queryInvUpdate(Map<String, Object> mapVo);

List<Map<String, Object>> queryAssRepairRecord(Map<String, Object> entityMap,RowBounds rowBounds);

List<Map<String, Object>> queryInvUpdate(Map<String, Object> entityMap,RowBounds rowBounds);

void updateAssRepairRecord(Map<String, Object> mapVo);

void deleteBatchRecordRepairInv(Map<String, Object> mapVo);

List<Map<String, Object>> queryTimeLineRecordRender(Map<String, Object> mapVo);

Map<String, Object> queryRecordCardDataByCode(Map<String, Object> mapVo);

List<Map<String, Object>> queryAssRepairRecordPrint(Map<String, Object> mapVo);

void deleteAssRepairRecord(Map<String, Object> map);

List<Map<String, Object>> queryImgUrlByRepRecordCode(Map<String, Object> map);

void addassRepairRecordAtt(Map<String, Object> map);

String queryRecordMaxNo(Map<String, Object> mapVo);

void deleteassRecordRepairAtt(Map<String, Object> map);

Map<String, Object> queryAssRepairByCode(Map<String, Object> map);

}
