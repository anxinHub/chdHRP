<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<script src="<%=path%>/lib/stringbuffer.js"></script>
<jsp:include page="${path}/inc.jsp"/>
	<script>
    var grid;    
    var gridManager = null;   
    var userUpdateStr;  
	var titleData;  
    var cusData;
    var deptData;
    var empData;
    var projData;
    var storeData;
    var sourceData;
    var supData;
    var checkItemData;
    $(function (){
    	loadColDictData();
    	loadHead(null);	//加载数据
    	if('${subj_dire}' == 0){
    		$("#subj_dire_text").text("借");
    	}else{
    		$("#subj_dire_text").text("贷");
    	}
    });
    
    
    function loadColDictData(){
		$.post("../../sys/queryCusDict.do?isCheck=false",null,function(data){cusData = data;},"json");
		$.post("../../sys/queryDeptDict.do?isCheck=false",null,function(data){deptData = data;},"json");
		$.post("../../sys/queryEmp.do?isCheck=false",null,function(data){empData = data;},"json");
		$.post("../../sys/queryProjDictDict.do?isCheck=false",null,function(data){projData = data;},"json");
		$.post("../../sys/queryStoreDictDict.do?isCheck=false",null,function(data){storeData = data;},"json");
		$.post("../../sys/querySourceDict.do?isCheck=false",null,function(data){sourceData = data;},"json");
		$.post("../../sys/querySupDictDict.do?isCheck=false",null,function(data){supData = data;},"json");
		$.post("../queryCheckItem.do?isCheck=false",null,function(data){checkItemData = data;},"json");
	}
	
	function getColDictData(check_code){
		var data;
			if (check_code == "1"){
				data = deptData;
			}else if(check_code == "2"){
				data = empData;
			}else if(check_code == "3"){
				data = projData;
			}else if(check_code == "4"){
				data = storeData;
			}else if(check_code == "5"){
				data = cusData;
			}else if(check_code == "6"){
				data = supData;
			}else if(check_code == "7"){
				data = sourceData;
			}else {
				data = checkItemData;
			}
		return data;
	}
	
	function getSelectName(check_code){		
		var name;	
		if (check_code == "check1"){		
			name = "dept_name";		
		}else if(check_code == "check2"){		
			name = "emp_name";		
		}else if(check_code == "check3"){		
			name = "proj_name";		
		}else if(check_code == "check4"){		
			name = "store_name";		
		}else if(check_code == "check5"){		
			name = "cus_name";		
		}else if(check_code == "check6"){		
			name = "sup_name";		
		}else if(check_code == "check7"){		
			name = "source_name";		
		}else {		
			name = "check_item_name";		
		}	
		return name;
	}

	function getSelectCode(check_code){		
		var name;		
		if (check_code == "check1"){		
			name = "dept_code";		
		}else if(check_code == "check2"){		
			name = "emp_code";		
		}else if(check_code == "check3"){		
			name = "proj_code";		
		}else if(check_code == "check4"){		
			name = "store_code";		
		}else if(check_code == "check5"){		
			name = "cus_code";		
		}else if(check_code == "check6"){		
			name = "sup_code";		
		}else if(check_code == "check7"){		
			name = "source_code";		
		}else {		
			name = "check_item_code";		
		}	
		return name;	
	}
	
    function loadHead(){
    	var subj_code = '${subj_code}';
    	var columns = "";
    	var checktype = new StringBuffer(); 
    	
    	$.post("../accleder/getSubjItemTitle.do?isCheck=false",{'subj_code':subj_code},function(data){
			var url;
 		 	if( data != "" && data!=null){	
 	 			if(data[0].check_type_name1 != null && data[0].check_type_name1 != ""){
 	 				columns = columns + "{ display: '"+data[0].check_type_name1+"', name: '"+data[0].column_item1.toLowerCase()+"_str',align: 'left'"+"},";	
 	 				para = data[0].column_check1.toLowerCase();
 	 				if(data[0].column_item1!=undefined){
 	 					checktype.append(data[0].column_item1+",");
 	 				}
 	 			}
 	 			
 	 			if(data[0].check_type_name2 != null && data[0].check_type_name2 != ""){
 	 				columns = columns + "{ display: '"+data[0].check_type_name2+"', name: '"+data[0].column_item2.toLowerCase()+"_str', align: 'left'"+"},";
 	 				para =para+";"+data[0].column_check2.toLowerCase();
 	 				if(data[0].column_item2!=undefined){
 	 					checktype.append(data[0].column_item2+",");
 	 				}
 	 			}
 	 			
 	 			if(data[0].check_type_name3 != null && data[0].check_type_name3 != ""){
 	 				columns = columns + "{ display: '"+data[0].check_type_name3+"', name: '"+data[0].column_item3.toLowerCase()+"_str', align: 'left'"+"},";
 	 				para =para+";"+data[0].column_check3.toLowerCase();
 	 				
 	 				if(data[0].column_item3!=undefined){
 	 					checktype.append(data[0].column_item3+",");
 	 				}
 	 			}
 	 			
 	 			if(data[0].check_type_name4 != null && data[0].check_type_name4 != ""){
 	 				columns = columns + "{ display: '"+data[0].check_type_name4+"', name: '"+data[0].column_item4.toLowerCase()+"_str',align: 'left'"+"},";
 	 				para =para+";"+data[0].column_check4.toLowerCase();
 	 				if(data[0].column_item4!=undefined){
 	 					checktype.append(data[0].column_item4+",");
 	 				}
 	 				
 	 			}
 		 		
 		 	}

 			columns = columns + "{ display: '总账期初余额', name: 'end_os', align: 'right', render: function(rowdata){ return formatNumber(rowdata.end_os ==null ? 0 : rowdata.end_os, 2, 1);},"
            	+"totalSummary:{"
                +"type: 'sum',"
                +"render: function (suminf, column, cell)"
                +"{return '<div>' + formatNumber(suminf.sum ==null ? 0 : suminf.sum, 2, 1) + '</div>';}}},"
    			+"{ display: '往来初始余额', name: 'debit', align: 'right',render: function(rowdata){ return formatNumber(rowdata.debit ==null ? 0 : rowdata.debit, 2, 1);},"
            	+"totalSummary:{"
                +"type: 'sum',"
                +"render: function (suminf, column, cell)"
                +"{return '<div>' + formatNumber(suminf.sum ==null ? 0 : suminf.sum, 2, 1) + '</div>';}}"
	 			+"}";
	 			
	 		//alert(columns);
 			grid = $("#maingrid").ligerGrid({
 	           columns: eval("["+columns+"]"),
 	                     dataAction: 'server',dataType: 'server',usePager:true,url:'queryAccCurrentAccountCheckDetail.do?isCheck=false&subj_code=${subj_code}&checktype='+checktype,
 	                     width: '100%', height: '95%', checkbox: false,rownumbers:true,
 	                     selectRowButtonOnly:true
 	                   });
 	        gridManager = $("#maingrid").ligerGetGridManager();
    	},"json");
    }
    </script>
</head>
<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>
	<div id="toptoolbar" ></div>
	<font id="subj">科目：${subj_code}&nbsp;${subj_name }（金额方向：<font id="subj_dire_text"></font>）</font>
	<div id="maingrid"></div>
</body>
</html>
