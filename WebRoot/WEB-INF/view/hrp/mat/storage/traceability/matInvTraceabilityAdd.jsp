<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html style="overflow:hidden;">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />    
<title></title>
<link href="<%=path %>/lib/ligerUI/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css" />
<link href="<%=path %>/lib/ligerUI/skins/ligerui-icons.css" rel="stylesheet" type="text/css" />
<script src="<%=path %>/lib/jquery/jquery-1.3.2.min.js" type="text/javascript"></script>
<script src="<%=path %>/lib/hrp.js" type="text/javascript"></script>
<script src="<%=path %>/lib/ligerUI/js/ligerui.all.js" type="text/javascript"></script>
<script src="<%=path%>/lib/json2.js"></script>
<script src="<%=path%>/lib/My97DatePicker/WdatePicker.js"	type="text/javascript"></script>
<script type="text/javascript">
	
    var grid;
    
    var gridManager = null;
    
    
    $(function ()
    {
    	$("#begin_confirm_date").ligerTextBox({
			width : 110
		});
		$("#end_confirm_date").ligerTextBox({
			width : 110
		});
      	loadDict();
    	
    	loadHead(null);	//加载数据
        //query();
    	
    });
    //查询
    function  query(){
    		grid.options.parms=[];
    		grid.options.newPage=1;
        //根据表字段进行添加查询条件
    
    	grid.options.parms.push({name:'bar_code',value:$("#bar_code").val()}); 
    	grid.options.parms.push({name:'inv_code',value:$("#inv_code").val()}); 
    	grid.options.parms.push({name:'inv_model',value:$("#inv_model").val()}); 
    	grid.options.parms.push({name:'in_no',value:$("#in_no").val()}); 
    	grid.options.parms.push({name:'brief',value:$("#brief").val()});
    	grid.options.parms.push({
			name : 'begin_confirm_date',
			value : $("#begin_confirm_date").val()
		});
		grid.options.parms.push({
			name : 'end_confirm_date',
			value : $("#end_confirm_date").val()
		});
		grid.options.parms.push({
			name : 'store_id',
			value : liger.get("store_code").getValue() == null ? "" : liger
					.get("store_code").getValue().split(",")[0]
		});

    	//加载查询条件
    	grid.loadData(grid.where);  
     }

    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
           columns: [ 
					 { display: '条形码', name: 'bar_code', align: 'left'
					 },
                     { display: '材料名称', name: 'inv_code', align: 'left'
					 },
                     { display: '规格型号', name: 'inv_model', align: 'left'
					 },
                     { display: '使用日期', name: 'out_date', align: 'left'  
					 }
                     ],
                     dataAction: 'server',dataType: 'server',usePager:true,url:'queryMatInvTraceability.do?isCheck=false',
                     width: '100%', height: '100%', usePager : true,checkbox: false,rownumbers:true,delayLoad :true,//isSingleCheck:true,
                     selectRowButtonOnly:true,heightDiff: -10,
                     toolbar: { items: [
                                     	{ text: '查询', id:'search', click: query,icon:'search' }
                    				]},
                                    onSelectRow: function (data, rowindex, rowobj)
                                    {
                                    	parent.bar_code = data.bar_code;
                                        parent.query();
                                    }

                   });

        gridManager = $("#maingrid").ligerGetGridManager();
    }
    
    function show(){

    	
	       if($("#in_div").css("height")=="0px"){
		
		      $("#in_div").css("height","auto")

		 }else{
			   
			   $("#in_div").css("height","0px")
			 }
	
	grid._onResize();
	//$(".l-bar-btnload").click();
}

    function loadDict(){
	    	$("#inv_code").ligerTextBox({width:180});
	
			$("#inv_model").ligerTextBox({width:180}); 
			
			$("#bar_code").ligerTextBox({width:180}); 
			
			$("#in_no").ligerTextBox({width:180});
			$("#brief").ligerTextBox({width:180});
			
			autodate("#begin_confirm_date", "yyyy-mm-dd", "month_first");
			autodate("#end_confirm_date", "yyyy-mm-dd", "month_last");
			
			autocomplete("#store_code", "../../queryMatStoreDictDate.do?isCheck=false", "id", "text", true, true, null,true,true,"180");

         }   
    </script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<div id="toptoolbar" ></div>
    <table cellpadding="0" cellspacing="0" class="l-table-edit" >      
    	<tr>
	       <td align="right" class="l-table-edit-td"  width="10%">
            	入库日期：
            </td>
            <td align="left" class="l-table-edit-td"  width="20%">
				<table>
					<tr>
						<td>
							<input class="Wdate" name="begin_confirm_date" id="begin_confirm_date" type="text" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})"/>
						</td>
						<td align="right" class="l-table-edit-td"  >
							至
						</td>
						<td>
							<input class="Wdate" name="end_confirm_date" id="end_confirm_date" type="text" onFocus="WdatePicker({minDate:'#F{$dp.$D(\'begin_confirm_date\');}',isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})" />
						</td>
            		</tr>
				</table>
	        </td>
			<td align="right" class="l-table-edit-td" width="10%">条形码：</td>
            <td align="left" class="l-table-edit-td" ">
            	<input name="bar_code" type="text" id="bar_code" ltype="text" validate="{required:false,maxlength:100}" />
            </td>
            <td align="right" class="l-table-edit-td" style="padding-left: 20px;"><a href="javascript:show()">高级查询</a></td>
		</tr>
	</table>
	<div id="in_div"  style="height:0px; overflow:hidden" >
	<table cellpadding="0" cellspacing="0" class="l-table-edit" width="100%" >
	<tr>
		<td align="right" class="l-table-edit-td" width="10%">材料：</td>
           <td align="left" class="l-table-edit-td" ">
           	<input name="inv_code" type="text" id="inv_code" ltype="text" validate="{required:false,maxlength:100}" />
           </td>
		<td align="right" class="l-table-edit-td" style="padding-left:20px;">规格型号：</td>
		<td align="left" class="l-table-edit-td">
			<input name="inv_model" type="text" id="inv_model" ltype="text"  validate="{required:true,maxlength:20}" />
		</td>
		
	</tr>
	<tr>
		<td align="right" class="l-table-edit-td" style="padding-left:20px;">入库单号：</td>
		<td align="left" class="l-table-edit-td">
			<input name="in_no" type="text" id="in_no" ltype="text"  validate="{required:true,maxlength:20}" />
		</td>
		<td align="right" class="l-table-edit-td" style="padding-left:20px;">医嘱号：</td>
		<td align="left" class="l-table-edit-td">
			<input name="brief" type="text" id="brief" ltype="text"  validate="{required:true,maxlength:20}" />
		</td>
		 
	</tr>
	<tr>
		<td align="right" class="l-table-edit-td"  width="10%">
			仓库：
		</td>
           <td align="left" class="l-table-edit-td" width="20%">
           	<input name="store_code" type="text" id="store_code" ltype="text" validate="{required:false}" />
           </td>
	</tr>
   </table>
	</div>
	<div id="maingrid"></div>

</body>
</html>
