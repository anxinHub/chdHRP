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
    	grid.options.parms.push({name:'hos_code',value:$("#hos_code").val()}); 
    	grid.options.parms.push({name:'hos_name',value:$("#hos_name").val()}); 

    	//加载查询条件
    	grid.loadData(grid.where);
     }
    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
           columns: [ 
                     { display: '单位编码', name: 'hos_code', align: 'left'
					 },
                     { display: '单位名称', name: 'hos_name', align: 'left'
					 },
					 { display: '单位简称', name: 'hos_simple', align: 'left'
					 }
                     ],
                     dataAction: 'server',dataType: 'server',usePager:true,url:'../../sys/infodict/queryHosInfoList.do',
                     width: '100%', height: '100%', checkbox: false,rownumbers:true,
                     selectRowButtonOnly:true/* heightDiff: -10,
                     toolbar: { items: [
                     	{ text: '查询', id:'search', click: query,icon:'search' },
                     	{ line:true }
    				]} */
                   });

        gridManager = $("#maingrid").ligerGetGridManager();
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
		$("#hos_code").ligerTextBox({width:160});
    	
    	$("#hos_name").ligerTextBox({width:160});
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
			title:'单位',
			columns: JSON.stringify(grid.getPrintColumns()),//表头
			class_name: "com.chd.hrp.sys.service.InfoDictService",
			method_name: "queryHosInfoListPrint",
			bean_name: "infoDictService",
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
        	 <td align="right" class="l-table-edit-td"  style="padding-left:20px;">单位编码：</td>
            <td align="left" class="l-table-edit-td"><input name="hos_code" type="text" id="hos_code" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">单位名称：</td>
            <td align="left" class="l-table-edit-td"><input name="hos_name" type="text" id="hos_name" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td>
            	<input class="l-button l-button-test"  type="button" value="查询(Q)" onclick="query();"/>
            	<input class="l-button l-button-test"  type="button" value="打 印" onclick="printDate();"/>
            </td>
        </tr> 
    </table>

	<div id="maingrid"></div>

</body>
</html>
