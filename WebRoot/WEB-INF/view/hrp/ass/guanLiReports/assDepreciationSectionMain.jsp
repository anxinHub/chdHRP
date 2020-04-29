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
    
    $(function (){
        loadDict();//加载下拉框
        loadHead(null);	//加载数据
        loadHotkeys();
    });
    
    //查询
    function query(){
    	if(isnull($("#acc_year_month_begin").val()) || isnull($("#acc_year_month_end").val())){
   			$.ligerDialog.error("请选择起始会计期间！");
   			return;
   		}
		grid.options.parms=[];
		grid.options.newPage=1;
        //根据表字段进行添加查询条件
    	grid.options.parms.push({name:'ass_nature',value:liger.get("ass_nature").getValue()}); 
    	grid.options.parms.push({name:'use_dept_id',value:liger.get("use_dept_id").getValue().split("@")[0]}); 
    	grid.options.parms.push({name:'use_dept_no',value:liger.get("use_dept_id").getValue().split("@")[1]}); 
    	grid.options.parms.push({name:'year_month_begin',value:$("#acc_year_month_begin").val()}); 
    	grid.options.parms.push({name:'year_month_end',value:$("#acc_year_month_end").val()}); 
     	 
    	//加载查询条件
    	grid.loadData(grid.where);
		$("#resultPrint > table > tbody").empty();
     }

    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
           columns: [ 
                     { display: '部门编码', name: 'dept_code', align: 'left'},
                    	
                     { display: '部门名称', name: 'dept_name', align: 'left'},
                     { display: '本期折旧', name: 'now_depre_amount', align: 'left',
			 				render : function(rowdata, rowindex,value) {
			 					return formatNumber(
			 					rowdata.now_depre_amount == null ? 0: rowdata.now_depre_amount,'${ass_05005 }',1);
			 					},formatter:'###,##0.00'
	
             		 },
             		{ display: '自筹资金', name: 'zc_price', align: 'left',
			 				render : function(rowdata, rowindex,value) {
			 					return formatNumber(
			 					rowdata.zc_price == null ? 0: rowdata.zc_price,'${ass_05005 }',1);
			 					},formatter:'###,##0.00'
	
            		  },
                     { display: '财政资金', name: 'cz_price', align: 'left',
				 				render : function(rowdata, rowindex,value) {
				 					return formatNumber(
				 					rowdata.cz_price == null ? 0: rowdata.cz_price,'${ass_05005 }',1);
				 					},formatter:'###,##0.00'
		
                     },
                     { display: '科研资金', name: 'ky_price', align: 'left',
			 				render : function(rowdata, rowindex,value) {
			 					return formatNumber(
			 					rowdata.ky_price == null ? 0: rowdata.ky_price,'${ass_05005 }',1);
			 					},formatter:'###,##0.00'
	
              		},
              		{ display: '教学资金', name: 'jx_price', align: 'left',
		 				render : function(rowdata, rowindex,value) {
		 					return formatNumber(
		 					rowdata.jx_price == null ? 0: rowdata.jx_price,'${ass_05005 }',1);
		 					},formatter:'###,##0.00'
	
    		         }
                     ],
                     dataAction: 'server',dataType: 'server',usePager:true,url:'queryAssDepreciationSectionMain.do?isCheck=false',
                     width: '100%', height: '100%', checkbox: false,rownumbers:true,
                     selectRowButtonOnly:true,delayLoad:true,
                     toolbar: { items: [
                             { text: '查   询', id:'search', click: query,icon:'search' },
                             { line:true },
 						     { text: '打 印', id:'print', click: printDate,icon:'print' },
 						     { line:true }
						     
                     ]},
                   });

        gridManager = $("#maingrid").ligerGetGridManager();
    }
    
    function loadDict(){
    	
		var param = {query_key:''};
        
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
    	 //科室编码
    	autocomplete("#use_dept_id", "../queryDeptDict.do?isCheck=false", "id","text", true, true,param,true);

        $("#acc_year_month_begin").ligerTextBox({width:70});
        $("#acc_year_month_end").ligerTextBox({width:70});
        
      //默认年
        if(${ass_year_month}){
			$("#acc_year_month_begin").val('${ass_year_month }')
        }else{
        	autodate("#acc_year_month_begin","YYYYMM"); 
        }
        if(${ass_year_month}){
			$("#acc_year_month_end").val('${ass_year_month }')
        }else{
        	autodate("#acc_year_month_end","YYYYMM"); 
        }

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
 		var date=$("#acc_year_month_begin").val()+"至"+$("#acc_year_month_end").val();
    	var heads={
        		"isAuto":true,//系统默认，页眉显示页码
        		"rows": [
    	          {"cell":0,"value":"报表日期:"},
  				  {"cell":1,"value":date} ,
  				  {"cell":5,"value":"资产性质:"},
				  {"cell":6,"value":$("#ass_nature").val()} ,
        	]}; 
    	//表尾
    	var foots = {
    			rows: [
    				{"cell":2,"value":"制表人:"},
    				{"cell":3,"value":"${sessionScope.user_name}"},
    			]
    		}; 
 		var printPara={
 				title: "资金折旧月报表",//标题
 				columns: JSON.stringify(grid.getPrintColumns()),//表头
 				class_name: "com.chd.hrp.ass.service.guanLiReports.AssDepreciationSectionMainService",
 				method_name: "queryAssDepreciationSectionPrint",
 				bean_name: "assDepreciationSectionMainService" ,
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
		
        <tr >
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">折旧年月：</td>
            <td align="left" class="l-table-edit-td"><input name="acc_year_month_begin" type="text" id="acc_year_month_begin" ltype="text" validate="{required:true,maxlength:20}" class="Wdate" 
            onFocus="WdatePicker({maxDate:'#F{$dp.$D(\'acc_year_month_end\');}',isShowClear:true,readOnly:false,dateFmt:'yyyyMM'})"/></td>
        	 <td align="left" width="2%">&nbsp;至：</td>
			<td align="left"><input name="acc_year_month_end" type="text" id="acc_year_month_end" class="Wdate"
				onFocus="WdatePicker({minDate:'#F{$dp.$D(\'acc_year_month_begin\');}',isShowClear:true,readOnly:false,dateFmt:'yyyyMM'})" onchange="changeYearMonth()" /></td>
           
        	<td align="left"></td>
        	<td align="right" class="l-table-edit-td"  style="padding-left:20px;">资产性质：</td>
            <td align="left" class="l-table-edit-td"><input name="ass_nature" type="text" id="ass_nature" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">使用科室：</td>
            <td align="left" class="l-table-edit-td"><input name="use_dept_id" type="text" id="use_dept_id" ltype="text" validate="{required:true,maxlength:20}" /></td>
        </tr> 
       
    </table>

	<div id="maingrid"></div>
</body>
</html>
