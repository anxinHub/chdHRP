package com.chd.hrp.ass.serviceImpl.assmap;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.util.ChdJson;
import com.chd.hrp.ass.dao.assmap.AssAssMapMapper;
import com.chd.hrp.ass.entity.assmap.AssAssMap;
import com.chd.hrp.ass.entity.assmap.AssAssMapBar;
import com.chd.hrp.ass.service.assmap.AssAssMapService;
@Service("assAssMapService")
public class AssAssMapServiceImpl implements AssAssMapService {
	private static Logger logger = Logger.getLogger(AssAssMapServiceImpl.class);
	//引入DAO操作
	@Resource(name = "assAssMapMapper")
	private final AssAssMapMapper assAssMapMapper = null;
	@Override
	public String queryAssStoreDistribution(Map<String, Object> entityMap)
			throws DataAccessException {
		List<AssAssMap> list = assAssMapMapper.queryAssStoreDistribution(entityMap);
		
		return ChdJson.toJson(list);
	}
	@Override
	public String queryAssDeptDistribution(Map<String, Object> entityMap)
			throws DataAccessException {
		List<AssAssMap> list = assAssMapMapper.queryAssDeptDistribution(entityMap);
		
		return ChdJson.toJson(list);
	}
	@Override
	public String queryDeptAssDistribution(Map<String, Object> entityMap)
			throws DataAccessException {
		List<AssAssMap> list = assAssMapMapper.queryDeptAssDistribution(entityMap);
		
		return ChdJson.toJson(list);
	}
	@Override
	public String queryAssCirculationView(Map<String, Object> entityMap)
			throws DataAccessException {
		List<AssAssMapBar> list = new ArrayList<AssAssMapBar>();
		List<AssAssMap> ruku = assAssMapMapper.queryAssInStore(entityMap);
		List<AssAssMap> deptLy = assAssMapMapper.queryInDept(entityMap);
		List<AssAssMap> deptBack = assAssMapMapper.queryBackDept(entityMap);
		List<AssAssMap> storeInStore = assAssMapMapper.queryStoreInStore(entityMap);
		List<AssAssMap> deptInDept = assAssMapMapper.queryDeptInDept(entityMap);
		Integer [] arr1 = getAccMonth(ruku);
		Integer [] arr2 = getAccMonth(deptLy);
		Integer [] arr3 = getAccMonth(deptBack);
		Integer [] arr4 = getAccMonth(storeInStore);
		Integer [] arr5 = getAccMonth(deptInDept);
		
		AssAssMapBar assAssMapBar = new AssAssMapBar();
		assAssMapBar.setRuku(arr1);
		assAssMapBar.setDeptLy(arr2);
		assAssMapBar.setDeptBack(arr3);
		assAssMapBar.setStoreInStore(arr4);
		assAssMapBar.setDeptInDept(arr5);
		list.add(assAssMapBar);
		return ChdJson.toJson(list);
	}
	
	public Integer [] getAccMonth(List<AssAssMap> obj){
		Integer [] arr1 = new Integer[12];
		for(AssAssMap temp1 : obj){
			if(temp1.getName().equals("01")){
				if(temp1.getValue() != null){
					arr1[0] = Integer.parseInt(temp1.getValue());
				}else{
					arr1[0] = Integer.parseInt("0");
				}
			}else if(temp1.getName().equals("02")){
				if(temp1.getValue() != null){
					arr1[1] = Integer.parseInt(temp1.getValue());
				}else{
					arr1[1] = Integer.parseInt("0");
				}
			}else if(temp1.getName().equals("03")){
				if(temp1.getValue() != null){
					arr1[2] = Integer.parseInt(temp1.getValue());
				}else{
					arr1[2] = Integer.parseInt("0");
				}
			}else if(temp1.getName().equals("04")){
				if(temp1.getValue() != null){
					arr1[3] = Integer.parseInt(temp1.getValue());
				}else{
					arr1[3] = Integer.parseInt("0");
				}
			}else if(temp1.getName().equals("05")){
				if(temp1.getValue() != null){
					arr1[4] = Integer.parseInt(temp1.getValue());
				}else{
					arr1[4] = Integer.parseInt("0");
				}
			}else if(temp1.getName().equals("06")){
				if(temp1.getValue() != null){
					arr1[5] = Integer.parseInt(temp1.getValue());
				}else{
					arr1[5] = Integer.parseInt("0");
				}
			}else if(temp1.getName().equals("07")){
				if(temp1.getValue() != null){
					arr1[6] = Integer.parseInt(temp1.getValue());
				}else{
					arr1[6] = Integer.parseInt("0");
				}
			}else if(temp1.getName().equals("08")){
				if(temp1.getValue() != null){
					arr1[7] = Integer.parseInt(temp1.getValue());
				}else{
					arr1[7] = Integer.parseInt("0");
				}
			}else if(temp1.getName().equals("09")){
				if(temp1.getValue() != null){
					arr1[8] = Integer.parseInt(temp1.getValue());
				}else{
					arr1[8] = Integer.parseInt("0");
				}
			}else if(temp1.getName().equals("10")){
				if(temp1.getValue() != null){
					arr1[9] = Integer.parseInt(temp1.getValue());
				}else{
					arr1[9] = Integer.parseInt("0");
				}
			}else if(temp1.getName().equals("11")){
				if(temp1.getValue() != null){
					arr1[10] = Integer.parseInt(temp1.getValue());
				}else{
					arr1[10] = Integer.parseInt("0");
				}
			}else if(temp1.getName().equals("12")){
				if(temp1.getValue() != null){
					arr1[11] = Integer.parseInt(temp1.getValue());
				}else{
					arr1[11] = Integer.parseInt("0");
				}
			}
		}
		return arr1;
	}
}
