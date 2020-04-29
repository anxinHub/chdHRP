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
<!-- 资产折旧明细表 -->
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
    	
		if ($("#depre_year_month").val() == "") {
			$.ligerDialog.error('折旧年月不能为空');
			return;
		}
		
   		grid.options.parms=[];
   		grid.options.newPage=1;
   		
        //根据表字段进行添加查询条件
    	grid.options.parms.push({name:'ass_type_id',value:liger.get("ass_type_id").getValue()}); 
    	grid.options.parms.push({name:'ass_name',value:liger.get("ass_name").getValue()});
    	grid.options.parms.push({name:'source_id',value:liger.get("source_id").getValue()}); 
    	
    	grid.options.parms.push({name:'ass_card_no',value:$('#ass_card_no').val()});
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
	        	     { display: '资产卡片号', name: 'ass_card_no', align: 'left',width: '120',frozen: true
	                 },
                     { display: '资产编码', name: 'ass_code', align: 'left',width: '120',frozen: true
                     },
                     { display: '资产名称', name: 'ass_name', align: 'left',width: '120',frozen: true
					 },
					 { display: '资产分类', name: 'ass_type_name', align: 'left', width: 120
					 },
					 { display: '规格', name: 'ass_spec', align: 'left',width: '120'
					 },
					 { display: '型号', name: 'ass_mondl', align: 'left',width: '120'
					 },
					 { display: '品牌', name: 'ass_brand', align: 'left',width: '120'
					 },
					 { display: '原价', name: 'prim_money', align: 'right',width: '120',
						 render : function(rowdata, rowindex,value) {
							return formatNumber(value,'${ass_05005 }',1);
						 }
					 },
					 { display: '净值', name: 'cur_money', align: 'right',width: '120',
						 render : function(rowdata, rowindex,value) {
								return formatNumber(value,'${ass_05005 }',1);
							 }
					 },
					 { display: '使用年限', name: 'acc_depre_amount', align: 'left',width: '120'
					 },
					 { display: '本期折旧', name: 'now_depre_amount', align: 'right',width: '120',
						 render : function(rowdata, rowindex,value) {
								return formatNumber(value,'${ass_05005 }',1);
							 }
					 },
					 { display: '累计折旧', name: 'add_depre_amount', align: 'right',width: '120',
						 render : function(rowdata, rowindex,value) {
								return formatNumber(value,'${ass_05005 }',1);
							 }
					 }
                     ],
                     dataAction: 'server',dataType: 'server',usePager:true,url:'queryAssDepreciationInfo.do',
                     width: '100%', height: '100%', checkbox: true,rownumbers:true,delayLoad :true,
                     selectRowButtonOnly:true,//heightDiff: -10,
                     tree:{columnId:'ass_type_code'},
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
    	autocomplete("#ass_type_id","../queryAssTypeDictIsLast.do?isCheck=false","id","text",true,true,null,null,null,"180");
    	autocomplete("#source_id","../querySourceDict.do?isCheck=false","id","text",true,true,null,null,null,"180");
    	autocomplete("#ass_nature", "../queryAssNaturs.do?isCheck=false","id", "text",true,true,null,false,null,"180");
    	
    	$("#ass_name,#ass_card_no,#depre_year_month").ligerTextBox({width:180});
    	
    
		autodate("#depre_year_month","YYYYMM"); 
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
    	          {"cell":14,"value":"报表日期:"},
  				  {"cell":15,"value":date} ,
    	          
        	]}; 
    	//表尾
    	var foots = {
    			rows: [
    				{"cell":14,"value":"制表人:"},
    				{"cell":15,"value":"${sessionScope.user_name}"},
    			]
    		}; 
 		var printPara={
 				title: "资产折旧月明细",//标题
 				columns: JSON.stringify(grid.getPrintColumns()),//表头
 				class_name: "com.chd.hrp.ass.service.guanLiReports.AssDepreciationReportService",
 				method_name: "queryAssInfoPrint",
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
     <table cellpadding="0" cellspacing="0" class="l-table-edit" width="100%">
        <tr>
        	<td align="right" class="l-table-edit-td"  style="padding-left:20px;">折旧年月：</td>
            <td align="left" class="l-table-edit-td"><input name="depre_year_month" type="text" id="depre_year_month" ltype="text" validate="{required:true,maxlength:20}" class="Wdate" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyyMM'})"/></td>
        	<td align="left"></td>
        	
			<td align="right" class="l-table-edit-td"  style="padding-left:20px;">资产性质：</td>
            <td align="left" class="l-table-edit-td"><input name="ass_nature" type="text" id="ass_nature"  /></td>
			<td align="right" class="l-table-edit-td"  style="padding-left:20px;">资产分类：</td>
		    <td align="left" class="l-table-edit-td"><input name="ass_type_id" type="text" id="ass_type_id"  /></td>
		    <td align="left"></td>
        </tr>
        <tr>
        	
		    <td align="right" class="l-table-edit-td"  style="padding-left:20px;">资产名称：</td>
            <td align="left" class="l-table-edit-td"><input name="ass_name" type="text" id="ass_name"  /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">卡片号：</td>
            <td align="left" class="l-table-edit-td"><input name="ass_card_no" type="text" id="ass_card_no"  /></td>
           
			<td align="right" class="l-table-edit-td"  style="padding-left:20px;">资金来源：</td>
            <td align="left" class="l-table-edit-td"><input name="source_id" type="text" id="source_id"  /></td>
            <td  align="left"></td>
        </tr>
    
    </table>

	<div id="maingrid"></div>
</body>
</html>
