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
<!-- 资产折旧汇总表 -->
<script type="text/javascript">

    var grid;
    var gridManager = null;
    
    $(function (){
        loadDict();//加载下拉框
        loadHotkeys();
    	loadHead(null);	//加载数据
    	
    });
    
    //查询
    function query(){
    	
   		grid.options.parms=[];
   		grid.options.newPage=1;
   		
        //根据表字段进行添加查询条件
    	grid.options.parms.push({name:'source_id',value:liger.get("source_id").getValue()}); 
    	grid.options.parms.push({name:'dept_level',value:liger.get("dept_level").getValue()});
    	grid.options.parms.push({name:'ass_nature',value:liger.get("ass_nature").getValue()});
    	grid.options.parms.push({name:'depre_year_month',value:$("#depre_year_month").val()}); 
    	
    	//加载查询条件
    	grid.loadData(grid.where);
    	loadHead();	
		$("#resultPrint > table > tbody").empty();
		
	}
    
    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
           columns: [ 
	        	     { display: '部门编码', name: 'dept_code', align: 'left'
	                 },
                     { display: '部门名称', name: 'dept_name', align: 'left'
                     },
                     { display: '级次', name: 'dept_level', align: 'left'
					 },
					 { display: '本期折旧', name: 'now_depre_amount', align: 'right',
						 render : function(rowdata, rowindex,value) {
							return formatNumber(value,'${ass_05005 }',1);
						 }
					 },
					 { display: '原值', name: 'prim_money', align: 'right',
						 render : function(rowdata, rowindex,value) {
								return formatNumber(value,'${ass_05005 }',1);
							 }
					 },
					 { display: '累计折旧', name: 'add_depre_amount', align: 'right',
						 render : function(rowdata, rowindex,value) {
								return formatNumber(value,'${ass_05005 }',1);
							 }
					 }
					 
                     ],
                     dataAction: 'server',dataType: 'server',usePager:true,url:'queryAssDepreciationSummary.do',
                     width: '100%', height: '100%', checkbox: true,rownumbers:true,delayLoad :true,
                     selectRowButtonOnly:true,//heightDiff: -10,
                     //tree:{columnId:'ass_type_code'},
                     toolbar: { 
                    	 items: [
                            { text: '查询', id:'sum', click: query,icon:'search' },
                            { line:true },
						    { text: '打   印 （<u>P</u>）', id:'print', click: printDate,icon:'print' } 
                         ]},
                   });

        gridManager = $("#maingrid").ligerGetGridManager();
    }
    
    //字典下拉框
    function loadDict(){
    	var param = {query_key:''};
    	autocomplete("#source_id","../querySourceDict.do?isCheck=false","id","text",true,true,param,true);
    	
    	$("#dept_level").ligerComboBox({
            width : 160,
            data: [
                { text: '1', id: '1' },
                { text: '2', id: '2' },
                { text: '3', id: '3' }
            ]
        });
    	
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

    	
    	$("#depre_year_month").ligerTextBox({width:160});
        
    } 
    //键盘事件
	function loadHotkeys() {
	}
    
	//打印数据
 	function printDate(){
 		if(grid.getData().length==0){
   			$.ligerDialog.error("请先查询数据！");
   			return;
   		}
 		
 		var time=new Date();
    	var date=$("#depre_year_month").val();
    	var heads={
        		"isAuto":true,//系统默认，页眉显示页码
        		"rows": [
				  {"cell":0,"value":"折旧年限:"},
				  {"cell":1,"value":date} ,
    	          {"cell":2,"value":"资金来源："},
    	          {"cell":3,"value":liger.get("source_id").getText().split(" ")[1]},
				  {"cell":4,"value":"级次:"},
  				  {"cell":5,"value":liger.get("dept_level").getValue()} ,
        	]}; 
    	//表尾
    	var foots = {
    			rows: [
    				{"cell":4,"value":"制表人:"},
    				{"cell":5,"value":"${sessionScope.user_name}"},
    			]
    		}; 
 		var printPara={
 				title: "资产折旧汇总",//标题
 				columns: JSON.stringify(grid.getPrintColumns()),//表头
 				class_name: "com.chd.hrp.ass.service.guanLiReports.AssDepreciationReportService",
 				method_name: "queryAssDeprePrint",
 				bean_name: "assDepreciationReportService" ,
 				heads: JSON.stringify(heads), //表头需要打印的查询条件,可以为空
 				foots: JSON.stringify(foots)//表尾需要打印的查询条件,可以为空 
 				};
 		
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
           <td align="right" class="l-table-edit-td"  style="padding-left:20px;">折旧年月：</td>
            <td align="left" class="l-table-edit-td"><input name="depre_year_month" type="text" id="depre_year_month" ltype="text" validate="{required:true,maxlength:20}" class="Wdate" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyyMM'})"/></td>
        	<td align="left"></td>
        	<td align="right" class="l-table-edit-td"  style="padding-left:20px;">资产性质：</td>
            <td align="left" class="l-table-edit-td"><input name="ass_nature" type="text" id="ass_nature" ltype="text" validate="{required:true,maxlength:20}" /></td>
        	<td  align="left"></td>
        	<td align="right" class="l-table-edit-td"  style="padding-left:20px;">资金来源：</td>
            <td align="left" class="l-table-edit-td"><input name="source_id" type="text" id="source_id"  /></td>
            <td  align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">级次：</td>
			<td align="left" class="l-table-edit-td">
            	<input name="dept_level" type="text" id="dept_level"/>
            </td>
            <td  align="left"></td>
        </tr> 
       	<tr>
       		
       	</tr>
    </table>

	<div id="maingrid"></div>
</body>
</html>
