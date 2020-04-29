<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
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
        
    });
    //查询
    function  query(){
    	grid.options.parms=[];
    	grid.options.newPage=1;
        //根据表字段进行添加查询条件
    	grid.options.parms.push({name:'set_id',value:$("#set_id").val()}); 
    	grid.options.parms.push({name:'store_id',value:$("#store_id").val()}); 

    	//加载查询条件
    	grid.loadData(grid.where);
		$("#resultPrint > table > tbody").empty();
     }
    //获取查询条件的数值
    function f_getWhere(){
    	if (!grid) return null;
        var clause = function (rowdata, rowindex){
                	if($("#set_id").val()!=""){
                		return rowdata.set_id.indexOf($("#set_id").val()) > -1;	
                	}
                	if($("#store_id").val()!=""){
                		return rowdata.store_id.indexOf($("#store_id").val()) > -1;	
                	}
        };
        return clause; 
    }

    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
           columns: [ 
                     { display: '仓库编码', name: 'store_code', align: 'left'},
                     { display: '仓库名称', name: 'store_name', align: 'left'}
           ],
           dataAction: 'server',dataType: 'server',
           usePager: false, url:'queryMatStoreData.do?isCheck=false&set_id=${set_id}',
           width: '100%', height: '100%', checkbox: true,rownumbers:true,isChecked: f_isChecked,
           selectRowButtonOnly:true,//heightDiff: -10,
           toolbar: { items: [
    					{ text: '保存（<u>A</u>）', id:'add', click: add_open, icon:'add' },
    	                { line:true },
    			]}
        });

        gridManager = $("#maingrid").ligerGetGridManager();
    }
    
    function f_isChecked(rowdata){
    	if(typeof(rowdata.set_id) =="number"){
            return true;
    	}
        return false;
    }
    function add_open(){
    	var data = gridManager.getCheckedRows();
    	var ParamVo =[];
        var set_id = ${set_id};
        if (data.length == 0){
        	ParamVo.push('null'+"@"+set_id) 
        }else{
            
            $(data).each(function (){					
				ParamVo.push(
				this.store_id + "@"+ set_id
				) });
    	}
        ajaxJsonObjectByUrl("addMatStoreDetail.do?isCheck=true", {ParamVo : ParamVo.toString()},function(responseData){
            if(responseData.state=="true"){
            	loadHead(null);
            }
        });
    }
    function loadDict(){
        //字典下拉框
    	$("#set_id").ligerTextBox({width:160,disabled:true});
    	$("#set_name").ligerTextBox({width:160,disabled:true});
        $("#store_id").ligerTextBox({width:160});     
    }
    
    //键盘事件
	function loadHotkeys() {
		hotkeys('A', add_open);
	}
    
    </script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<div id="toptoolbar" ></div>
    <table cellpadding="0" cellspacing="0" class="l-table-edit" >
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">代码：</td>
            <td align="left" class="l-table-edit-td"><input name="set_id" type="text" id="set_id" value='${set_id}' disabled="disabled" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">名称：</td>
            <td align="left" class="l-table-edit-td"><input name="set_name" disabled="disabled" type="text" id="set_name" value='${set_name}' ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
        </tr> 
    </table>

	<div id="maingrid"></div>
	<div id="resultPrint" style="display:none">
	   	<table width="100%">
			<thead>
			<tr>
                <th width="200">设置ID</th>	
                <th width="200">仓库ID</th>	
			</tr>
			   	</thead>
			   	<tbody></tbody>
	   	</table>
   	</div>
</body>
</html>
