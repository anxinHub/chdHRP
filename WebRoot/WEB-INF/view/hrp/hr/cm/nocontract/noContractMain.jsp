<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%String path = request.getContextPath();%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html style="overflow:hidden;"><head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="${path}/inc_jquery_1.9.0.jsp" />
<script src="<%=path%>/lib/hrp/hr/hr.js"></script> 
<script type="text/javascript">
    var grid;
    var gridManager = null;
	$(function () {
		loadDict();
		loadHead(null);	
	})
	//表格加载
	function loadHead(){
		var columns=getGridColumns({ui:'liger',group_id:'${group_id}',hos_id:'${hos_id}',gridTables:['HOS_EMP'],design:'queryEmpNoContract.do'});
    	grid = $("#maingrid").ligerGrid({
            columns: columns,
                    dataAction: 'server',dataType: 'server',url:'queryEmpNoContract.do',delayLoad :true,
                    width: '100%', height: '100%', checkbox: true,rownumbers:true,usePager :true,
				toolbar: {
					items: [
						{text: '查询', id:'search', click: query,icon:'search' },{line : true},
						{text: '签订合同', id:'add', click: add,icon:'add' },
						{text: '导入（<u>I</u>）',id:'importMainGrid',click: importMainGrid,icon: 'up'} ,
					]
				},
			});
		gridManager = $("#maingrid").ligerGetGridManager();  
	}
	//查询方法
	function query() {
		grid.options.parms=[];
		grid.options.parms.push({name:'emp_id',value:$("#emp_id").val()}); 
		grid.options.parms.push({name:'tab_code',value:'HR_EMP_CONTRACT'}); 
		grid.options.parms.push({name:'dept_id',value:$("#dept_id").val()}); 
		grid.options.parms.push({name:'rjt',value:"grid"}); 
		grid.options.parms.push({name:'design_code',value:"queryNoContract.do"}); 
		grid.loadData(grid.where);	
	}
	//添加
	function add() {
		var data = grid.getCheckedRows();
    	if (data.length == 0) {
        	$.ligerDialog.error('请选择行');
        }else  
        if(data.length > 1){$.ligerDialog.error('只能选择一行');return;}else{
        	var param = [];
            $(data).each(function () {
                var rowdata = this;
                param.push(rowdata);
            });
        	parent.$.ligerDialog.open({
                url: 'hrp/hr/cm/nocontract/noContractAddPage.do?isCheck=false',
                title: '签订合同',
                height : $(window).height()-200,
				width : $(window).width(),
				 data:{
	                	param
	                },
                buttons : [ {
					text : '保存',
					onclick : function(item, dialog) {
						dialog.frame.saveData();
					},
					cls : 'l-dialog-btn-highlight'
				}, {
					text : '取消',
					onclick : function(item, dialog) {
						dialog.close();
					}
				} ] });
        }
	}
	//导入
    function importMainGrid() {
 		
    	 $.ligerDialog.open({
             url: "noContractImprotPage.do?isCheck=false&tab_code=" + 'HR_EMP_CONTRACT'+"&ui="+"liger",
         	parentframename : window.name,
             height : $(window).height(),
				width : $(window).width(),
         })
 	
 	} 
	function loadDict(){
		autocomplete("#emp_id","../../baseSelect.do?isCheck=false&&field_tab_code=HOS_EMP","id","text",true,true,'',false,'',175);
		autocomplete("#dept_id","../../baseSelect.do?isCheck=false&&field_tab_code=SYS_DEPT","id","text",true,true,'','false','',175);		
	}
 </script>
</head>

<body>
<div id="pageloading" class="l-loading" style="display: none"></div>
	<div id="toptoolbar" ></div>
	<table cellpadding="0" cellspacing="0" class="l-table-edit" >
		<tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">职工：</td>
            <td align="left" class="l-table-edit-td"><input name="emp_id" type="text" id="emp_id" ltype="text" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">科室：</td>
            <td align="left" class="l-table-edit-td"><input name="dept_id" type="text" id="dept_id" ltype="text" /></td>
            <td align="left"></td>
        </tr> 
	</table>
        
    <div id="maingrid"></div>     
</body>

</html>