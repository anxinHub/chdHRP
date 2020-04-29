<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html style="overflow:hidden;">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title></title>
<jsp:include page="${path}/inc_jquery_1.9.0.jsp"/>
<script type="text/javascript">
	
    var grid;
    
    var gridManager = null;
    
    var userUpdateStr;
    
    $(function ()
    {
		loadDict();
    	
    	loadHead(null);	//加载数据
    	
    	loadHotkeys();
    	
    	
    });
    //查询
    function  query(){
    	
    	grid.options.parms=[];
    	
    	grid.options.newPage=1; 
        //根据表字段进行添加查询条件
        grid.options.parms.push({name:'kind_code',value:liger.get("kind_code").getValue()}); 
        grid.options.parms.push({name:'dept_code',value:$("#dept_code").val()});  
        grid.options.parms.push({name:'natur_code',value:liger.get("natur_code").getValue()});
        grid.options.parms.push({name:'type_code',value:liger.get("type_code").getValue()});
        grid.options.parms.push({name:'out_code',value:liger.get("out_code").getValue()});
        grid.options.parms.push({name:'is_disable',value:$("#is_disable").val()}); 

    	//加载查询条件
    	grid.loadData(grid.where);
     }

    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
           columns: [ 
                     { display: '部门编码', name: 'dept_code', align: 'left',
							render : function(rowdata, rowindex,
									value) {
									return "<a href=javascript:openUpdate('"+rowdata.group_id   + "|" + 
									rowdata.hos_id   + "|" + 
									rowdata.dept_id  +"')>"+rowdata.dept_code+"</a>";
							}
					 },
                     { display: '部门名称', name: 'dept_name', align: 'left',
						 render : function(rowdata, rowindex, value) {
								return formatSpace(rowdata.dept_name,
										rowdata.dept_level - 1);
							}
					 },
					 { display: '部门分类', name: 'kind_name', align: 'left'
					 },
					 { display: '部门类型', name: 'type_name', align: 'left'
					 },
					 { display: '部门性质', name: 'natur_name', align: 'left'
					 },
					 { display: '支出性质', name: 'out_name', align: 'left'
					 },
                     { display: '是否停用', name: 'is_disable', align: 'left',
							render : function(rowdata, rowindex,
									value) {
								if(rowdata.is_disable == 0){
									return "否";
								}else{
									return "是"
								}
							}
					 }
                     ],
                     dataAction: 'server',dataType: 'server',usePager:true,url:'../../sys/deptdict/queryDeptDict.do?isCheck=false',
                     width: '100%', height: '100%', checkbox: true,rownumbers:true,
                     selectRowButtonOnly:true,//heightDiff: -10,
                    /*  toolbar: { items: [
                     	{ text: '查询', id:'search', click: query,icon:'search' },
                     	{ line:true }
    				]}, */
    				onDblClickRow : function (rowdata, rowindex, value)
    				{
						openUpdate(
								rowdata.group_id   + "|" + 
								rowdata.hos_id   + "|" + 
								rowdata.dept_id 
							);
    				} 
                   });

        gridManager = $("#maingrid").ligerGetGridManager();
    }

    function openUpdate(obj){
		var vo = obj.split("|");
		var parm = "group_id="+
			vo[0]   +"&hos_id="+ 
			vo[1]   +"&dept_id="+ 
			vo[2];
    	parent.$.ligerDialog.open({ url : 'accCheckDeptUpdatePage.do?' + parm,data:{}, height: 450,width: 500, 
    			title:'修改',modal:true,showToggle:false,showMax:false,showMin: false,isResize:true,
    			parentframename: window.name,  
    			buttons: [ { text: '确定', onclick: function (item, dialog) { dialog.frame.saveAccDeptAttr(); },
    			cls:'l-dialog-btn-highlight' }, { text: '取消', onclick: function (item, dialog) { dialog.close(); } } ] });

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
  
    function loadDict(){
            //字典下拉框
    	//autocomplete("#dept_code","../../sys/queryDeptAll.do?isCheck=false","id","text",true,true);
            
    	autocomplete("#natur_code","../queryDeptNatur.do?isCheck=false","id","text",true,true);
    	
    	autocomplete("#kind_code","../../sys/queryDeptKindDict.do?isCheck=false","id","text",true,true);
    	
    	autocomplete("#type_code","../../sys/queryAccDeptType.do?isCheck=false","id","text",true,true);
    	
    	autocomplete("#out_code","../../sys/queryAccDeptOut.do?isCheck=false","id","text",true,true);
    	
    	$("#is_disable").ligerComboBox({width:160}); 
    	
    	$("#dept_code").ligerTextBox({width:160}); 
    }
    
    function batchUpdate(){
    	
    	var data = gridManager.getCheckedRows();
    	
        if (data.length == 0){

        	$.ligerDialog.error('请选择行');
        	
        	return;
        	
        }else{

        	var ParamVo ="";
        	
            $(data).each(function (){	

				ParamVo+=this.dept_id+",";  
				
            });
         
            parent.$.ligerDialog.open({ url : 'accCheckDeptBatchUpdatePage.do?isCheck=false&param='+ParamVo,
            		data:{}, height: 360,width: 500, title:'批量修改',modal:true,showToggle:false,showMax:false,
            		showMin: false,isResize:true, parentframename:window.name,
            		buttons: [ { text: '确定', onclick: function (item, dialog) { dialog.frame.saveAccDeptAttr(); },
            		cls:'l-dialog-btn-highlight' }, { text: '取消', onclick: function (item, dialog) { dialog.close(); } } ] });
        }
    	
    }
    
    function printDate(){
		 if(grid.getData().length==0){
		
			$.ligerDialog.error("请先查询数据！");
			
			return;
		}
	var heads={
	      		  //"isAuto": true/false 默认true，页眉右上角默认显示页码
	      		  "rows": [
	      		  ]
	      	};
	   		
		var printPara={
			rowCount:1,
			title:'部门',
			columns: JSON.stringify(grid.getPrintColumns()),//表头
			class_name: "com.chd.hrp.sys.service.DeptDictService",
			method_name: "queryDeptDictPrint",
			bean_name: "deptDictService",
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
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">部&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;门：</td>
            <td align="left" class="l-table-edit-td"><input name="dept_code" type="text" id="dept_code" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">部门性质：</td>
            <td align="left" class="l-table-edit-td"><input name="natur_code" type="text" id="natur_code" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
             <td align="right" class="l-table-edit-td"  style="padding-left:20px;">部门类型：</td>
            <td align="left" class="l-table-edit-td"><input name="type_code" type="text" id="type_code" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
        </tr>
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">部门分类：</td>
            <td align="left" class="l-table-edit-td"><input name="kind_code" type="text" id="kind_code" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">是否停用：</td>
                <td align="left" class="l-table-edit-td">
              		   <select id="is_disable" name="is_disable" style="width: 135px;">
           		   			<option value="">&nbsp;</option>
	                		<option value="0">启用</option>
	                		<option value="1">停用</option>
                	</select>
                </td>
                <td align="left"></td>
                 <td align="right" class="l-table-edit-td"  style="padding-left:20px;">支出性质：</td>
            <td align="left" class="l-table-edit-td"><input name="out_code" type="text" id="out_code" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td colspan="2">
            	<input class="l-button" style="width: 80px" type="button" value="查询(Q)" onclick="query();"/>
            	<input class="l-button" style="width: 80px" type="button" value="批量修改" onclick="batchUpdate();"/>
            	<input class="l-button" style="width: 80px" type="button" value="打 印" onclick="printDate();"/>
            </td>
        </tr> 
    </table>

	<div id="maingrid"></div>

</body>
</html>
