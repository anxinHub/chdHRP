<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html style="overflow:hidden;">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="${path}/inc_jquery_1.9.0.jsp" />
<script type="text/javascript">
    var grid;
    var gridManager = null; 
    var userUpdateStr;
    $(function ()
    {
        loadDict();//加载下拉框
    	//加载数据
    	loadHead(null);	
		 loadHotkeys();
		  $("#acc_year_month_begin").ligerTextBox({width:70}); 
		  $("#acc_year_month_end").ligerTextBox({width:70}); 

    });
    //查询
    function  query(){
    	if(isnull($("#acc_year_month_begin").val()) || isnull($("#acc_year_month_end").val())){
   			$.ligerDialog.error("请选择起始会计期间！");
   			return;
   		}
    	grid.options.parms=[];
    	grid.options.newPage=1;
    	if(!isnull($("#acc_year_month_begin").val())){
    		 grid.options.parms.push({name:'year_month_begin',value:$("#acc_year_month_begin").val()});
    		 grid.options.parms.push({name:'year_month_end',value:$("#acc_year_month_end").val()});
    	}
        //根据表字段进行添加查询条件
    /* 	grid.options.parms.push({name:'ass_naturs',value:liger.get("ass_nature").getValue()});  */
    	  grid.options.parms.push({name:'ass_type_id',value:liger.get("ass_type_id").getValue()});
    	  grid.options.parms.push({name:'ass_nature',value:liger.get("ass_nature").getValue()});
    	 /*  grid.options.parms.push({name:'store_id',value:liger.get("store_id").getValue().split("@")[0]}); */
    	//加载查询条件
    	grid.loadData(grid.where);
    	$("#resultPrint > table > tbody").empty();
     }

    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
           columns: [ 
                     { display: '变动方式', name: 'note', align: 'left'
					 		},
                     { display: '资产原值', name: 'price', align: 'right',
						 		 render : function(rowdata, rowindex,
											value) {
										 return formatNumber(rowdata.price,'${ass_05006}',1);
									},formatter:'###,##0.00'
					 		},
                     { display: '累计折旧', name: 'depre_money', align: 'right',
						 		 render : function(rowdata, rowindex,
											value) {
										 return formatNumber(rowdata.depre_money,'${ass_05005 }',1);
									},formatter:'###,##0.00'
					 		},
                     { display: '净值', name: 'cur_money', align: 'right',
						 		 render : function(rowdata, rowindex,
											value) {
										 return formatNumber(rowdata.cur_money,'${ass_05006 }',1);
									},formatter:'###,##0.00'
					 		}
                     ],
                     dataAction: 'server',
                     dataType: 'server',
                     usePager:false,
                     url:'queryAssZengJian.do',
                     width: '100%', 
                     height: '100%', 
                     checkbox: false,
                     rownumbers:false,
                     delayLoad :true,
                     selectRowButtonOnly:true,//heightDiff: -10,
                     toolbar: { items : [ {
							text : '查询（<u>Q</u>）',
							id : 'search',
							click : query,
							icon : 'search'
						 },{ line:true }, 
						   { text: '打   印 （<u>P</u>）', 
							 id:'print', 
							 click: printDate,
							 icon:'print' 
							}
				     ]}
                   });

        gridManager = $("#maingrid").ligerGetGridManager();
    }
   
    function loadDict(){
    	
		var param = {
            query_key:''
        };
        //字典下拉框
		//autocomplete("#ass_nature", "../queryAssNaturs.do?isCheck=false","id",  "text",true,true,param,true);
		
        autocomplete("#ass_type_id", "../queryAssTypeDict.do?isCheck=false&is_last=1","id",  "text",true,true,param,true);
	      
        autocomplete("#ass_name", "../queryAssNoDict.do?isCheck=false","id",  "text",true,true,param,true);

        /* autocomplete("#store_id", "../queryHosStoreDict.do?isCheck=false","id", "text",true,true,param,true);
 */
        
      //字典下拉框
		$("#ass_nature").ligerComboBox({
          	url: '../queryAssNaturs.do?isCheck=false',
          	valueField: 'id',
           	textField: 'text', 
           	selectBoxWidth: 160,
          	autocomplete: true,
          	width: 160,
          	onSelected :function(value,text){
          		//loadHead(null);
          		query();
          	}
 		  });

 		autodate("#acc_year_month_begin","YYYYMM"); 
 		autodate("#acc_year_month_end","YYYYMM");
 
        
      /*   $("#ass_year").ligerTextBox({width:70});
        
        $("#ass_month").ligerTextBox({width:50});
        
        $("#ass_month1").ligerTextBox({width:50});
        
		autodate("#ass_year","YYYY");
		
		autodate("#ass_month","MM");
		
		autodate("#ass_month1","MM");
		 */
     }  
    
  //打印数据
 	function printDate(){
 		if(grid.getData().length==0){
   			$.ligerDialog.error("请先查询数据！");
   			return;
   		}
 		
 		var time=new Date();
    	var date=$("#acc_year_month_begin").val()+"至"+$("#acc_year_month_end").val();
    	var heads={
        		"isAuto":true,//系统默认，页眉显示页码
        		"rows": [
    	          {"cell":0,"value":"报表日期："},
    	          {"cell":1,"value":date},
    	          {"cell":2,"value":"资产性质:"},
				  {"cell":3,"value":$("#ass_nature").val()} 
  				]}; 
    	//表尾
    	var foots = {
    			rows: [
    				{"cell":0,"value":"制表人:"},
    				{"cell":1,"value":"${sessionScope.user_name}"}
    		]}; 
 		var printPara={
 				title: "资产增减表",//标题
 				columns: JSON.stringify(grid.getPrintColumns()),//表头
 				class_name: "com.chd.hrp.ass.service.zengjian.AssZengJianMainService",
 				method_name: "queryAssZengJianMainPrint",
 				bean_name: "assZengJianMainService" ,
 				heads: JSON.stringify(heads), //表头需要打印的查询条件,可以为空
 				foots: JSON.stringify(foots)//表尾需要打印的查询条件,可以为空 
 				};
 		
 		$.each(grid.options.parms,function(i,obj){
 				printPara[obj.name]=obj.value;
 		});
 		
 		officeGridPrint(printPara);
 	   		
 	}
    
     //键盘事件
	 function loadHotkeys() {

		hotkeys('Q', query);
	 }
    
    </script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>
	<div id="toptoolbar" ></div>
    <table cellpadding="0" cellspacing="0" class="l-table-edit" >
        <tr>
           <td align="right" class="l-table-edit-td"  style="padding-left:20px;">会计期间：</td>
            <td align="left" class="l-table-edit-td"><input name="acc_year_month_begin" type="text" id="acc_year_month_begin" ltype="text" validate="{required:true,maxlength:20}" class="Wdate" 
            onFocus="WdatePicker({maxDate:'#F{$dp.$D(\'acc_year_month_end\');}',isShowClear:true,readOnly:false,dateFmt:'yyyyMM'})"/></td>
            <td align="left" width="2%">&nbsp;至：</td>
			<td align="left"><input name="acc_year_month_end" type="text" id="acc_year_month_end" class="Wdate"
				onFocus="WdatePicker({minDate:'#F{$dp.$D(\'acc_year_month_begin\');}',isShowClear:true,readOnly:false,dateFmt:'yyyyMM'})" onchange="changeYearMonth()" /></td>
           
            <td align="left"></td>
          <td align="right" class="l-table-edit-td"  style="padding-left:20px;">资产分类：</td>
			<td align="left" class="l-table-edit-td"><input name="ass_type_id" type="text" id="ass_type_id" /></td> 
			<td align="right" class="l-table-edit-td"  style="padding-left:20px;">资产性质：</td>
            <td align="left" class="l-table-edit-td"><input name="ass_nature" type="text" id="ass_nature" ltype="text" validate="{required:true,maxlength:20}" /></td>
			<!-- <td align="right" class="l-table-edit-td"  style="padding-left:20px;">库房：</td>
			<td align="left" class="l-table-edit-td"><input name="store_id" type="text" id="store_id" /></td>  -->
    <td align="left"></td>
    
        </tr> 
    </table>

	<div id="maingrid"></div>
</body>
</html>
