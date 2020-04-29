<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%String path = request.getContextPath();%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html style="overflow:hidden;"  xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="${path}/inc_jquery_1.9.0.jsp"/>
<script type="text/javascript">
    var grid;
    var gridManager = null;
    var userUpdateStr;
    
    //初始化加载
	$(function (){
		$("#acct_yearm").ligerTextBox({ width:160 });
		autodate("#acct_yearm","yyyymm");
		
        loadDict();//加载字典
    	loadHead(null);	//加载grid
    	query();
    	toolbar();//加载工具栏
    	loadHotkeys();//加载快捷键
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
    function  query(){
        //根据表字段进行添加查询条件
    	grid.options.parms=[];
    	grid.options.newPage=1;
    	
    	var template_code = '009';
    	var template_type = '01'
    	grid.options.parms.push({name : 'template_code',value : template_code});
    	grid.options.parms.push({name : 'template_type',value : template_type});
    	grid.options.parms.push({name:'acct_yearm',value:$("#acct_yearm").val()}); 
    	grid.options.parms.push({name:'dept_id',value:liger.get("dept_id").getValue().split(",")[0]}); 
    	grid.options.parms.push({name:'dept_no',value:liger.get("dept_id").getValue().split(",")[1]}); 
    	//加载查询条件
    	grid.loadData(grid.where);
    	loadHead();	
    	$("#resultPrint > table > tbody").empty();
	}
	
    //加载grid
	function loadHead(){
    	
    	grid = $("#maingrid").ligerGrid({
			columns: [ 
				{ display: '奖金模块', name: 'hpm_name', align: 'left',width:100},
				{ display: '项目', name: 'item_name', align: 'left',width:150},
				{ display: '工作量', name: 'work_amount', align: 'left',width:150},
				{ display: '单元奖金', name: 'unit_bonus', align: 'right',width:150},
				{ display: '核算奖金', name: 'account_bonus', align: 'left',width:150},
				{ display: '结构比例', name: 'struct_propor', align: 'left',width:150},
				{ display: '核算说明', name: 'account_elu', align: 'left',width:150}
            ],
            dataAction: 'server',dataType: 'server',usePager:false,url:'queryReport_009.do',
            width: '100%',height: '100%',   checkbox: false,rownumbers:false,delayLoad:true,
            selectRowButtonOnly:true,//heightDiff: -10,
            onAfterShowData: function (s) {
                setTimeout(function () {
             	   $('#maingrid .l-grid-body-table tbody').rowspan('hpm_name', grid);
                }, 0)
            }
         });

        gridManager = $("#maingrid").ligerGetGridManager();
    }
    
    
    
	//工具栏
	function toolbar(){
      	var obj = [];
      	
      	obj.push({ text: '查询（<u>Q</u>）', id:'search', click: query,icon:'search' });
      	obj.push({ line:true });
      	
      	$("#toptoolbar").ligerToolBar({ items: obj});
	}
 	
	//键盘事件
	function loadHotkeys(){
		
		hotkeys('Q',query);
		
	}
	
	

   
    //字典下拉框
    function loadDict(){
    	
    	changeAcctYear();
	}
    
  	//核算年月绑定事件
    function changeAcctYear(){
	  
	  	var para = {
	  		acct_yearm:$("#acct_yearm").val()
	  	};
    	autocomplete("#dept_id","../queryDeptDictByPerm.do?isCheck=false","id","text",true,true,para);
    }
  
    </script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<table cellpadding="0" cellspacing="0" class="l-table-edit">

		<tr>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">核算年月：</td>
			<td align="left" class="l-table-edit-td"><input class="Wdate" name="acct_yearm" type="text" id="acct_yearm" ltype="text"
				onFocus="WdatePicker({isShowClear:false,readOnly:true,dateFmt:'yyyyMM'})" onchange="changeAcctYear()" /></td>
			<td align="left"></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">科室名称：</td>
			<td align="left" class="l-table-edit-td"><input name="dept_id" type="text" id="dept_id" ltype="text" /></td>
		</tr>
	</table>

	<div id="toptoolbar"></div>
	<div id="maingrid"></div>

	
</body>
</html>
