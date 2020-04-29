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
<!-- 资产折旧分析 -->
<script type="text/javascript">

    var grid;
    var gridManager = null;
    
    $(function (){
        loadDict();//加载下拉框
        loadHotkeys();
    	loadHead(null);	//加载数据
    });
    
    jQuery.fn.rowspan = function (colname, tableObj) {
    	
        var colIdx;
        for (var i = 0, n = tableObj.columns.length; i < n; i++) {
        	if (tableObj.columns[i]["columnname"] == colname) {
                //colIdx = i - 1 < 1 ? 0 : i - 1;
                colIdx = i;
                break;
            }
        }
        return this.each(function () {
            var that;
            $('tr', this).each(function (row) {
                $('td:eq(' + colIdx + ')', this).filter(':visible').each(function (col) {
                    if (that != null && $(this).html() == $(that).html()) {
                        rowspan = $(that).attr("rowSpan");
                        if (rowspan == undefined) {
                            $(that).attr("rowSpan", 1);
                            rowspan = $(that).attr("rowSpan");
                        }
                        rowspan = Number(rowspan) + 1;
                        $(that).attr("rowSpan", rowspan);
                        $(this).hide();
                    } else {
                        that = this;
                    }
                });
            });
        });
    }    
    //查询
    function query(){
    	
   		grid.options.parms=[];
   		grid.options.newPage=1;
   		
        //根据表字段进行添加查询条件
    	grid.options.parms.push({name:'type_level',value:liger.get('type_level').getValue()});
    	grid.options.parms.push({name:'is_show',value:liger.get('is_show').getValue()});
    	
    	if($("#depre_year_month").val() == "" || $("#depre_year_month").val() == null){
    		$.ligerDialog.warn("折旧年月不能为空！");
   			return;
    	}
    	
    	
    	grid.options.parms.push({name:'depre_year_month',value:$("#depre_year_month").val()}); 
    	
    	//加载查询条件
    	grid.loadData(grid.where);
    	loadHead();	
		$("#resultPrint > table > tbody").empty();
		
	}
    
    function loadHead(){
    	ajaxJsonObjectByUrl("queryAssTypeHead.do?isCheck=false",{type_level : liger.get('type_level').getValue()}, function(responseData) {
    		
    		var columns = new Array();
    		columns.push({ display: '科室分类', name: 'kind_name', align: 'left',width: '120'});
    		columns.push({ display: '类型', name: 'p_name', align: 'left',width: '120'});
    		columns.push({ display: '资产来源', name: 'source_name', align: 'left',width: '120'});
    		$.each( responseData.Rows, function(i, n){
    			columns.push({ display: n.ass_type_name, name: 'ass_'+n.ass_type_code, align: 'right',width: '120',
   				 render : function(rowdata, rowindex,value) {
   						return formatNumber(value,'${ass_05005 }',1);
   					 }
   				 });
    		});
    		columns.push({ display: '合计', name: 'total', align: 'right',width: '120',
				 render : function(rowdata, rowindex,value) {
						return formatNumber(value,'${ass_05005 }',1);
					 }
				 });
    		
    		grid = $("#maingrid").ligerGrid({
    	           columns:columns,
	               dataAction: 'server',dataType: 'server',usePager:false,url:'queryAssDepreciationAnalyse.do',
	               width: '100%', height: '100%', checkbox: false,rownumbers:false,delayLoad :true,
	               selectRowButtonOnly:true,//heightDiff: -10,
	               onAfterShowData: function (s) {
	                   setTimeout(function () {
	                	   $('#maingrid .l-grid-body-table tbody').rowspan('kind_name', grid);
	                       $('#maingrid .l-grid-body-table tbody').rowspan('p_name', grid);
	                   }, 0)
	               },
	               toolbar: { 
	                   items: [
	                            { text: '查询', id:'sum', click: query,icon:'search' },
	                            { line:true },
							    { text: '打   印 （<u>P</u>）', id:'print', click: printDate,icon:'print' }
	                    	  ]},
	               });

    	        gridManager = $("#maingrid").ligerGetGridManager();
		});
    	
    }
    
    //字典下拉框
    function loadDict(){
    	$("#depre_year_month").ligerTextBox({width:150});

    	
    	$('#is_show').ligerComboBox({
			data:[{id:1,text:'是'},{id:0,text:'否'}],
			valueField: 'id',
            textField: 'text',
            width:120,
			cancelable:true
		})
		liger.get('is_show').setValue(0);
    	
    	$("#type_level").ligerComboBox({
            width : 120,
            data: [
                { text: '1', id: '1' },
                { text: '2', id: '2' },
                { text: '3', id: '3' }
            ]
        });
        liger.get('type_level').setValue('1');
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
    	         // {"cell":0,"value":"仓库："},
    	          //{"cell":1,"value":liger.get("store_id").getText().split(" ")[1]},
    	          {"cell":3,"value":"报表日期:"},
  				  {"cell":4,"value":date} ,
    	          
        	]}; 
    	//表尾
    	var foots = {
    			rows: [
    				{"cell":3,"value":"制表人:"},
    				{"cell":4,"value":"${sessionScope.user_name}"},
    			]
    		}; 
 		var printPara={
 				title: "资产折旧分类",//标题
 				columns: JSON.stringify(grid.getPrintColumns()),//表头
 				class_name: "com.chd.hrp.ass.service.guanLiReports.AssDepreciationReportService",
 				method_name: "queryAssDepreciationAnalysePrint",
 				bean_name: "assDepreciationReportService" , 
 				heads: JSON.stringify(heads), //表头需要打印的查询条件,可以为空
 				foots: JSON.stringify(foots),//表尾需要打印的查询条件,可以为空 
 				mergeColumns : "kind_name@p_name"
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
     		<td align="right" class="l-table-edit-td"  style="padding-left:20px;">级次：</td> 
            <td align="left" class="l-table-edit-td">
            	<input name="type_level" type="text" id="type_level"/>
            </td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"> 是否显示累计折旧：</td> 
            <td align="left" class="l-table-edit-td">
            	<input name="is_show" type="text" id="is_show"/>
            </td>
            <td align="left"></td>
     	</tr>
       
    </table>

	<div id="maingrid"></div>
</body>
</html>
