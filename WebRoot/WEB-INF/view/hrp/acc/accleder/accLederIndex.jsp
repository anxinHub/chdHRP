<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="${path}/inc_jquery_1.9.0.jsp"/>
<script type="text/javascript">	
    var grid;
    
    var gridManager = null;
    
    var userUpdateStr;
    
    $(function ()
    {
		loadDict();
		loadButton();
    	loadHead(null);	//加载数据
    	//changeColor();
    	
    	loadHotkeys();
    	
    	 autodate("#acc_year","yyyy");
    	 var date = $("#acc_year").val();
    	 $("#acc_year").val("${acc_year}");
    });

    //查询
    function  query(){
    		grid.options.parms=[];
    		grid.options.newPage=1;
        //根据表字段进行添加查询条件
    	grid.options.parms.push({name:'subj_type_code',value:liger.get("subj_type").getValue()}); 
    	grid.options.parms.push({name:'subj_level',value:liger.get("subj_level").getValue()}); 
    	grid.options.parms.push({name:'acc_year',value:$("#acc_year").val()}); 

    	//加载查询条件
    	grid.loadData(grid.where);
     }
    
    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
           columns: [ 
                     { display: '科目编码', name: 'subj_code', align: 'left',width:'15%'
					 },
                     { display: '科目全称', name: 'subj_name', align: 'left',width:'40%'
					 },
                     { display: '初始余额', align: 'left',
						 columns:[
							 { 
								 display: '借方', name: 'debit',  align: 'right',formatter: "###,##0.00",
		   						 render:function(rowdata){
		   	   						 return formatNumber(rowdata.debit, 2, 1);
		   						 }
							 },{ 
								 display: '贷方', name: 'credit',  align: 'right',formatter: "###,##0.00",
		   						 render:function(rowdata){
		   	   						 return formatNumber(rowdata.credit, 2, 1);
		   						 }
							 },]
					 }
                     ],
                     dataAction: 'server',dataType: 'server',usePager:false,url:'queryAccLederIndex.do',
                     width: '100%', height: '100%', //checkbox: true,
                     rownumbers:true,selectRowButtonOnly:true,delayLoad:true,
                     rowAttrRender:function(rowdata,rowid){ 
                     	if(rowdata.subj_code == "合计"){
                     		if(Math.abs(rowdata.credit) != Math.abs(rowdata.debit)){
                     		return "style='background:#f00;'";
                     		}
                     	}/* else{
                     		//return "style='background:#000;'";
                     	} */
                     	 
                      },
                      lodop:{
        	         		title:"试算平衡",
        	      			fn:{
        	          			debit:function(value){//借方
        	          				if(value == 0){return "";}
        	                 			else{return formatNumber(value, 2, 1);}
        	          			},
        	          			credit:function(value){//贷方
        	          				if(value == 0){return "";}
        	                			else{return formatNumber(value, 2, 1);}
        	         				},
        	         				end_os:function(value){//余额
        	      	   				 if(value==0){return "Q";}
        	      					 else{return formatNumber(value, 2, 1);}
        	        				}
        	          		}
        	      		}
                   });

        gridManager = $("#maingrid").ligerGetGridManager();
    }
    
  //键盘事件
	function loadHotkeys() {

		hotkeys('Q', query);
/* 
		hotkeys('A', add);
		hotkeys('D', remove);

		hotkeys('E', exportExcel);

		hotkeys('P', printDate);
		hotkeys('K', printBarcode); */

	}
	/*    //打印回调方法
    function lodopPrint(){
   
    	var head="<table class='head' width='100%'><tr><td>会计期间："+liger.get("acc_time").getValue()+"</td>";
 		grid.options.lodop.head=head;
    } */
    function loadDict(){
            //字典下拉框
    	autocomplete("#subj_level","../querySubjLevel.do?isCheck=false","id","text",true,true);
    	autocomplete("#subj_type","../querySubjType.do?isCheck=false","id","text",true,true);
     }  
    
    function loadButton(){
		$("#query").ligerButton({click: query, width:90});
		$("#verify").ligerButton({click: verify, width:90});
		$("#print").ligerButton({click: printDate, width:90});
	}
    
    function  verify(){
    	
    	$.ligerDialog.confirm('确定校验?', function (yes){
			if(yes){
				ajaxJsonObjectByUrl("verifyAccLederIndex.do?isCheck=false",{"acc_year":$("#acc_year").val()},function (responseData){
					if(responseData.state=="true"){
						query();
					}
				});
			}
		}); 
    }
    
    
    function printDate(){
    	if(grid.getData().length==0){
    		
			$.ligerDialog.error("请先查询数据！");
			
			return;
		}
    	var heads={
	      		  //"isAuto": true/false 默认true，页眉右上角默认显示页码
	      		  "rows": [
						{"cell":0,"value":"年度："+${acc_year},"colSpan":"5"},
	      		  ]
	      	};
    	
   		var printPara={
   	   			rowCount:1,
   	   			title:'试算平衡',
   	   			columns: JSON.stringify(grid.getPrintColumns()),//表头
   	   			class_name: "com.chd.hrp.acc.service.AccLederService",
   				method_name: "queryAccLederIndexPrint",
   				bean_name: "accLederService",
   				heads: JSON.stringify(heads)//表头需要打印的查询条件,可以为空
   	   			};
	  
    	
   		//执行方法的查询条件
   		$.each(grid.options.parms,function(i,obj){
   			printPara[obj.name]=obj.value;
    	});
   		
    	officeGridPrint(printPara);
	
    }
    </script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<div id="toptoolbar" ></div>
    <table cellpadding="0" cellspacing="0" class="l-table-edit" >
	
        <tr>
        	<td align="right" class="l-table-edit-td"  style="padding-left:20px;">年度：</td>
            <td align="left" ><input id="acc_year" type="text" class="Wdate" ltype="text" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy'})" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">科目类别：</td>
            <td align="left" class="l-table-edit-td"><input name="subj_type" type="text" id="subj_type" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">科目级次：</td>
            <td align="left" class="l-table-edit-td"><input name="subj_level" type="text" id="subj_level" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="left"></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">
			<input type="button" id="query"  value="查询（Q）"/>
			</td>
			<td align="right" class="l-table-edit-td" >
			<input  class="l-button"  style="width: 90px;" type="button" id="verify"  value="校    验"/>
			</td>
			<td align="right" class="l-table-edit-td">
				<input type="button" id="print" value="打 印"/>
			</td>
			<td align="left"></td>
        </tr> 
    </table>
	<div id="maingrid"></div>

</body>
</html>
