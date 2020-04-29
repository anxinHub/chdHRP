<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="${path}/inc_jquery_1.9.0.jsp" />
<script type="text/javascript">	
    var grid;
    var gridManager = null;
    var userUpdateStr;
    var jsonHead;
    var show_way = 1;
    var columns1 = [ { 
    	display: '操作', name: 'SUBJ_CODE', width: 120, align: 'left',
    	render: function(rowdata, index, value){
    		return "<a href='#' onClick='javascript:addAccSubjContent(\""+value+"\",\""+rowdata.SUBJ_CODE_K+"\",\""+index+"\")'>【设置】</a>&nbsp;&nbsp;&nbsp;&nbsp;"
    			+"<a href='#' onClick='javascript:delAccSubjContent("+index+")'>【删除】</a>";
    	}
    }, { 
    	display: '财务会计科目', name: 'CAIWU', align: 'left', width: 500
	} ];
    var column2 = {display: '预算会计科目', name: 'YUSUAN', align: 'left', width: 350};
    var columns2 = [];
    var columns3 = [];
    
    $(function ()
    {
    	columns2.push(column2);
    	
		loadDict();
    	loadHead(null);	//加载数据
		loadButton();
    	
    	//绑定事件
    	$("input[name='show_way']").click(function(){
    		changeCol();
    		query();
    	})
    });
    //查询
    function  query(){
    	gridManager.options.parms=[];
    	gridManager.options.newPage=1;
    	gridManager.options.parms.push({name:'subj_code_c',value:$("#subj_code_c").val()}); 
    	gridManager.options.parms.push({name:'code',value:liger.get("code").getValue()});
    	gridManager.options.parms.push({name:'subj_code_k',value:$("#subj_code_k").val()}); 
    	show_way = $("input[name='show_way']:checked").val();
    	gridManager.options.parms.push({name: 'show_way',value: show_way}); 
    	//加载查询条件
    	gridManager.loadData(gridManager.where);
     }

    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
    		columns: columns1.concat(columns2),
    		dataAction: 'server',dataType: 'server',usePager:true,url:'queryFinancialAccountingComparison.do',
    		width: '100%', height: '100%', checkbox: false,rownumbers:true,
    		selectRowButtonOnly:true,//heightDiff: -10,
    	});

    	gridManager = $("#maingrid").ligerGetGridManager();
    }

	/* 生成资金来源列 */
	function getColumns3() {
		ajaxJsonObjectByUrl("queryHosSource.do?isCheck=false", "", function(data){
			if(data.length > 0){
				jsonHead = data;
				$.each(data, function(index, v){
					if(v.source_id){
						columns3.push({
							display : v.source_name, 
							name : "name_"+v.source_id, 
							align: 'left',
							width : 250,
						})
					}
				});
			}
		}, false);
	}
	
	function changeCol(){
		columns2 = [];
		var show_way = $("input[name='show_way']:checked").val();
		if(show_way == 1){
			if(column2.columns){
				delete column2.columns;
			}
		}else{
			if(!column2.columns){
				if(columns3.length == 0){
					getColumns3();
				}
				column2.columns = columns3;
			}
		}
		
		columns2.push(column2);
		grid.set("columns", columns1.concat(columns2));
		grid.reRender();
	}

    function addAccSubjContent(SUBJ_CODE, SUBJ_CODE_K, index){
    	if(show_way == 1){
    		$.ligerDialog.open({
    			title:'设置预算会计科目', 
    			url: 'addFinancialAccountingComparisonPage.do?isCheck=false&SUBJ_CODE_K='+SUBJ_CODE_K, 
    			height: 380, width: 620, 
    			modal:true,showToggle:false,showMax:false,showMin: true,isResize:true,
				buttons: [ { 
					text: '确定', onclick: function (item, dialog) { dialog.frame.save(SUBJ_CODE,dialog); },cls:'l-dialog-btn-highlight' 
				}, { 
					text: '关闭', onclick: function (item, dialog) { dialog.close(); } 
				} ]
			});
    	}else{
    		var rowData = grid.getRow(index);
    		var init_values = {};
			$.each(jsonHead, function(index, v){
				init_values["source_"+v.source_id] = v.source_id;
				init_values["id_"+v.source_id] = rowData["id_"+v.source_id];
				init_values["name_"+v.source_id] = rowData["name_"+v.source_id];
			});
			$.ligerDialog.open({
    			title:'按资金来源设置预算会计科目', 
    			url: 'addFinancialAccountingComparisonBySourcePage.do?isCheck=false', 
    			height: 380, width: 620, 
    			data: {subj_code_c: SUBJ_CODE, jsonHead: jsonHead, init_values: init_values},
    			modal: true, showToggle: false, showMax: false, showMin: true, isResize: true,
				buttons: [ { 
					text: '确定', onclick: function (item, dialog) { dialog.frame.save(); },cls: 'l-dialog-btn-highlight' 
				}, { 
					text: '关闭', onclick: function (item, dialog) { dialog.close(); } 
				} ]
			});
    	}
    }
    
	function delAccSubjContent(index){
		var obj = gridManager.getRow(index);
		$.ligerDialog.confirm('确定删除?', function (yes){
			if(yes){
				ajaxJsonObjectByUrl("deleteFinancialAccountingComparison.do",{"subj_code_c" : obj.SUBJ_CODE},function (responseData){
					if(responseData.state == true){
						var rowData = {
							SUBJ_CODE_K: null
						}; 
						if(show_way == 1){
							rowData.YUSUAN = null;
						}else{
							$.each(jsonHead, function(index, v){
								rowData["id_"+v.source_id] = null;
								rowData["name_"+v.source_id] = null;
							});
						}
						grid.updateRow(index, rowData);
					}
				});
			}
		}); 
	}

	
	function loadButton(){
		$("#query").ligerButton({click: query, width:60});
		$("#printDate").ligerButton({click: printDate, width:60});
		$("#imports").ligerButton({click: imports, width:60});
		$("#smart").ligerButton({click: updateSmartSubj, width:60});
	}
    
	function updateSmartSubj(){
		$.ligerDialog.confirm('程序只能对照一部分有规则的科目，确定自动对照?', function (yes){
        	if(yes){
            	ajaxJsonObjectByUrl("updateSmartSubj.do",{},function (responseData){
            		if(responseData.state == "true"){
            			query();
            		}
            	});
        	}
        }); 
	}
	
    function loadDict(){
    	
    	$("#subj_code_c").ligerTextBox({width:160});
    	
    	$("#subj_code_k").ligerTextBox({width:160});

    	$("#code").ligerComboBox({
            width : 80,
            data: [
                { text: '否', id: 0 },
                { text: '是', id: 1 },
            ],  
            initIsTriggerEvent: false,
            onChange: function ()
            {
            	query();
            }
        });
    	
    }  
    
    function printDate(){
    	
    	var jsonstr = grid.getPrintColumns();
    	var pushs = [];
    	pushs.push(grid.getPrintColumns().rows[1]);
    	pushs.push(grid.getPrintColumns().rows[2]);
    	jsonstr.rows = pushs;
		if(gridManager.getData().length==0){
		
			$.ligerDialog.error("请先查询数据！");
			
			return;
		}
	var heads={
	      		  //"isAuto": true/false 默认true，页眉右上角默认显示页码
	      		  "rows": [
							//{"cell":0,"value":"购置日期："+$("#begin_date").val()+"至"+$("#end_date").val(),"colSpan":"5"},
							//{"cell":3,"value":"系统名称："+liger.get("mod_code").getText(),"from":"right","align":"right","colSpan":"4"}  
	      		  ]
	      	};
	   		
		var printPara={
			rowCount:1,
			title:'财务会计科目对照',
			columns: JSON.stringify(jsonstr),//表头
			class_name: "com.chd.hrp.acc.service.autovouch.AccFinancialAccountingComparisonService",
			method_name: "queryFinancialAccountingComparisonPrint",
			bean_name: "accFinancialAccountingComparisonService",
			heads: JSON.stringify(heads)//表头需要打印的查询条件,可以为空
			};
	
		//执行方法的查询条件
		$.each(gridManager.options.parms,function(i,obj){
			printPara[obj.name]=obj.value;
	});
		
	officeGridPrint(printPara); 
	
	}
    
    function imports(){
    	
        	//导入所需表头
        	var paraLedger = {
        			"column" : [
        				{ "name" : "ACC_SUBJ_CODE", "display" : "财务会计科目编码", "width" : "", "require" : true },
        				{ "name" : "ACC_SUBJ_NAME", "display" : "财务会计科目名称", "width" : "", "require" : false },
        				{ "name" : "BUDG_SUBJ_CODE", "display" : "预算会计科目编码", "width" : "", "require" : true },
        				{ "name" : "BUDG_SUBJ_NAME", "display" : "预算会计科目名称", "width" : "", "require" : false} ] }
        				
        	importSpreadView("/hrp/acc/autovouch/financialaccountingcomparison/importFinancialAccountingComparison.do?isCheck=false", paraLedger, query());

    }
    
    </script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<div id="toptoolbar" ></div>
	<!-- <div id="topmenu"></div> -->
    <table cellpadding="0" cellspacing="0" class="l-table-edit" >
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">财务会计科目：</td>
            <td align="left" class="l-table-edit-td"><input name="subj_code_c" type="text" id="subj_code_c" ltype="text"  /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">预算会计科目：</td>
            <td align="left" class="l-table-edit-td"><input name="subj_code_k" type="text" id="subj_code_k" ltype="text" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">是否对照：</td>
            <td align="left" class="l-table-edit-td"><input name="code" type="text" id="code" ltype="text" /></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">对照方式：</td>
            <td align="left" class="l-table-edit-td">
            	<input name="show_way" type="radio" value="1" checked/>按科目
            	&nbsp;&nbsp;&nbsp;&nbsp;
            	<input name="show_way" type="radio" value="2"/>按资金来源
            </td>
            <td align="left"></td>
            <td align="left"></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">
				<input type="button" id="query" accessKey="T"  value="查询"/>
				<input type="button" id="imports" value="导入"/>
				<input  type="button" id="smart" value="自动对照" title="程序只能自动对照一部分有规则的科目"/>
				<input  type="button" id="printDate" value="打印" /> 
			</td>
			<td align="left"></td>
        </tr> 
    </table>

	<div id="maingrid"></div>

</body>
</html>
