<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html style="overflow:hidden;">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="${path}/inc.jsp"/>
<script type="text/javascript">
    var grid;
    var gridManager = null;
    var userUpdateStr;
    $(function ()
    {
    	$("#acct_yearm").ligerTextBox({ width:160 });
   	 	autodate("#acct_yearm","yyyymm");
        loadDict()//加载下拉框
    	//加载数据
    	loadHead(null);	
        
        toolbar();
        loadHotkeys();
    });
    //查询
    function  query(){
   		grid.options.parms=[];
   		grid.options.newPage=1;
        //根据表字段进行添加查询条件
    	 
   	  	grid.options.parms.push({name:'acct_yearm',value:$("#acct_yearm").val()}); 
    	grid.options.parms.push({name:'dept_id',value:liger.get("dept_id").getValue().split(",")[0]});
    	grid.options.parms.push({name:'dept_no',value:liger.get("dept_id").getValue().split(",")[1]});
    	grid.options.parms.push({name:'cost_item_code',value:liger.get("cost_item_code").getValue()}); 
    	$("#resultPrint > table > tbody").html("");
    	//加载查询条件
    	grid.loadData(grid.where);
     }
   

    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
           	columns: [ 
						{ display: '年份', name: 'acct_year', align: 'left'},
						{ display: '月份', name: 'acct_month', align: 'left'}, 
						{ display: '科室编码', name: 'dept_code', align: 'left'/* ,
							 render: function (rowdata, rowindex, value){
									return "<a href='#' onclick=\"openUpdate('"+rowdata.dept_id+"','"+rowdata.dept_no+"','"+rowdata.income_item_code+"','"+rowdata.acct_year+"','"+rowdata.acct_month+"');\" >"+rowdata.dept_code+"</a>";
						    } */},
						{ display: '科室名称', name: 'dept_name', align: 'left'},
						{ display: '支出项目编码', name: 'cost_item_code', align: 'left'},
						{ display: '支出项目名称', name: 'cost_iitem_name', align: 'left'},
                     ],
                     dataAction: 'server',dataType: 'server',usePager:true,url:'queryHpmDeptCostItemMaping.do',
                     width: '100%', height: '100%', checkbox: true,rownumbers:true,
                     selectRowButtonOnly:true,//heightDiff: -10,
    				onDblClickRow : function (rowdata, rowindex, value)
    				{
						openUpdate(
								rowdata.group_id   + "|" + 
								rowdata.hos_id   + "|" + 
								rowdata.copy_code   + "|" + 
								rowdata.dept_id   + "|" + 
								rowdata.income_item_code 
							);
    				} 
                   });

        gridManager = $("#maingrid").ligerGetGridManager();
    }
    
  	//工具栏
	function toolbar(){
		var obj = [];
		
		obj.push({ text: '查询', id:'search', click: query,icon:'search' });
       	obj.push({ line:true });
       	
       	obj.push({ text: '添加', id:'add', click: addDeptMaping, icon:'add' });
       	obj.push({ line:true });
       	
       	obj.push({ text: '删除', id:'delete', click: deleteDeptMaping,icon:'delete' });
       	obj.push({ line:true });
       	
       	$("#toptool").ligerToolBar({ items: obj});
	}

    //键盘事件
	function loadHotkeys(){
		
		hotkeys('Q',query);
		 
		hotkeys('A',addDeptMaping);
		
		hotkeys('D',deleteDeptMaping);
	}
    
    function addDeptMaping(){
    	
    	parent.$.ligerDialog.open({url: 'hrp/hpm/hpmdeptcostitem/hpmDeptCostItemAddPage.do?isCheck=false', height: $(window).height(),
			width: $(window).width(), title:'添加',modal: true, showToggle: false, showMax: true, showMin: false, isResize: true,
			parentframename: window.name });
  		
    }
    
    function deleteDeptMaping (){
    	
    	var data = gridManager.getCheckedRows();
        if (data.length == 0){
        	$.ligerDialog.error('请选择行');
        }else{
            var ParamVo =[];
            $(data).each(function (){					
													ParamVo.push(
													this.group_id   +"@"+ 
													this.hos_id   +"@"+ 
													this.copy_code   +"@"+ 
													this.acct_year +"@"+
													this.acct_month +"@"+
													this.dept_id   +"@"+ 
													this.cost_item_code 
													) });
            $.ligerDialog.confirm('确定删除?', function (yes){
            	if(yes){
                	ajaxJsonObjectByUrl("deleteHpmDeptCostItemMapping.do?isCheck=false",{ParamVo : ParamVo},function (responseData){
                		if(responseData.state=="true"){
                			query();
                		}
                	});
            	}
            }); 
        }
    }
    
    
  //字典下拉框
    function loadDict(){
		changeAcctYear();//核算年月绑定事件
	}
    
  //核算年月绑定事件
    function changeAcctYear(){
	  
    	autocomplete("#dept_id","../queryDeptDictByPerm.do?isCheck=false","id","text",true,true);
    	autocomplete("#cost_item_code","../../hpm/queryAphiCostItem.do?isCheck=false", "id", "text",true, true);
    } 
    </script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<div id="toptoolbar" ></div>
    <table cellpadding="0" cellspacing="0" class="l-table-edit" >
	
        <tr>
        </tr> 
	
        <tr>
       		<td align="right" class="l-table-edit-td" style="padding-left: 20px;">核算年月：</td>
			<td align="left" class="l-table-edit-td"><input
				name="acct_yearm" class="Wdate" type="text" id="acct_yearm"
				ltype="text" validate="{required:true,maxlength:20}"
				onFocus="WdatePicker({isShowClear:false,readOnly:true,dateFmt:'yyyyMM'})" onchange="changeAcctYear()" /></td>
			<td align="left"></td>
        	<td align="right" class="l-table-edit-td" style="padding-left: 20px;">科室名称：</td>
			<td align="left" class="l-table-edit-td"><input name="dept_id"
				type="text" id="dept_id" ltype="text"
				validate="{required:true,maxlength:20}" /></td>
			<td align="left"></td>
          	<td align="right" class="l-table-edit-td" style="padding-left: 20px;">支出项目：</td>
			<td align="left" class="l-table-edit-td"><input
				name="cost_item_code" type="text" id="cost_item_code"
				ltype="text" validate="{required:true,maxlength:20}" /></td>
			<td align="left"></td>
        </tr> 
    </table>
	
	<div id="toptool"></div>
	<div id="maingrid"></div>
</body>
</html>
