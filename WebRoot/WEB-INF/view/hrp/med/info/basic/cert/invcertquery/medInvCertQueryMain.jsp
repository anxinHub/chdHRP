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
<script type="text/javascript">
    var grid;
    var gridManager = null;
    var userUpdateStr;
    $(function ()
    {
        loadDict()//加载下拉框
    	//加载数据
    	loadHead(null);
        loadHotkeys();
        
    });
    //查询
    function  query(){
		grid.options.parms=[];
		grid.options.newPage=1;
    	//根据表字段进行添加查询条件
		grid.options.parms.push({name:'cert_id',value:liger.get("cert_id").getValue()}); 
		grid.options.parms.push({name:'med_type_id',value:liger.get("med_type_id").getValue().split(",")[0]}); 
		grid.options.parms.push({name:'inv_id',value:liger.get("inv_code").getValue().split(",")[0]}); 
		grid.options.parms.push({name:'fac_id',value:liger.get("fac_id").getValue().split(",")[0]}); 
		grid.options.parms.push({name:'inv_msg',value:$("#inv_msg").val()}); 
    	grid.options.parms.push({name:'inv_model',value:$("#inv_model").val()}); 
    	//加载查询条件
    	grid.loadData(grid.where);
     }

    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
			columns: [
				{ display: '证件编号', name: 'cert_code', align: 'left'},
				{ display: '证件名称', name: 'type_name', align: 'left'},
				{ display: '药品编码', name: 'inv_code', align: 'left'},
				{ display: '药品名称', name: 'inv_name', align: 'left'},
				{ display: '规格型号', name: 'inv_model', align: 'left'},
				{ display: '生产厂商', name: 'fac_name', align: 'left'}
			],
			dataAction: 'server',dataType: 'server',usePager:true,url:'queryMedInvCertQuery.do',
			width: '100%', height: '100%',rownumbers:true,
			delayLoad:true,selectRowButtonOnly:false,//heightDiff: -30,
            toolbar: { 
	            items:[
	            	{ text: '查询（<u>Q</u>）', id:'search', click: query,icon:'search' },
	                { line:true },
	                { text: '打印（<u>P</u>）', id:'print', click: print,icon:'print' },
	                { line:true }
	            ]
			},
		});

        gridManager = $("#maingrid").ligerGetGridManager();
    }
    
  //打印
	function print(){
    	if(grid.getData().length==0){
			$.ligerDialog.error("请先查询数据！");
			return;
		}
    	
    	var selPara={};
    	$.each(grid.options.parms,function(i,obj){
    		selPara[obj.name]=obj.value;
    	});
   		
		var dates = getCurrentDate();
    	var cur_date = dates.split(";")[2];
    	//跨所有列:计算列数
    	var colspan_num = grid.getColumns(1).length-1;
    	
   		var printPara={
   			title:'证件药品查询',
   			head:[
				{"cell":0,"value":"单位: ${sessionScope.hos_name}","colspan":colspan_num,"br":true}
   			],
   			foot:[
				{"cell":0,"value":"制单人： ${sessionScope.user_name}","colspan":colspan_num,"br":true},
				{"cell":0,"value":"打印日期: " + cur_date,"colspan":colspan_num,"br":true}
   			],
   			columns:grid.getColumns(1),
   			headCount:1,//列头行数
   			autoFile:true,
   			type:3
   		};
   		ajaxJsonObjectByUrl("queryMedInvCertQuery.do?isCheck=false", selPara, function (responseData) {
   			printGridView(responseData,printPara);
		});
    }
    
    //字典下拉框
    function loadDict(){
	 	//证件编码下拉框
		autocomplete("#cert_id", "../../../../queryMedInvCert.do?isCheck=false", "id", "text", true, true,'',false,'','160');
		//药品类别下拉框
		autocomplete("#med_type_id", "../../../../queryMedTypeDict.do?isCheck=false","id", "text", true, true, {is_last : 1},false,'','160');
		//生产厂商 下拉框 
		autocomplete("#fac_id", "../../../../queryHosFacDict.do?isCheck=false", "id", "text", true, true,'',false,'','160');
		//药品材料 下拉框 
		autocomplete("#inv_code", "../../../../queryMedInvDict.do?isCheck=false", "id", "text", true, true,'',false,'','160');
			
		$("#inv_msg").ligerTextBox({width:160}); 
		$("#inv_model").ligerTextBox({width:160}); 
	}
    
    //键盘事件
	function loadHotkeys() {
		hotkeys('Q', query);
		hotkeys('P', print);
	 }
	
    </script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<div id="toptoolbar" ></div>
    <table cellpadding="0" cellspacing="0" class="l-table-edit" width="100%">
        <tr>
        </tr> 
        <tr>
        	<td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b>药品:</b></td>
            <td align="left" class="l-table-edit-td"><input name="inv_code" type="text" id="inv_code" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b>证件编码:</b></td>
            <td align="left" class="l-table-edit-td"><input name="cert_id" type="text" id="cert_id" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b>药品类别:</b></td>
            <td align="left" class="l-table-edit-td"><input name="med_type_id" type="text" id="med_type_id" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            
        </tr>
         <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b>生产厂商:</b></td>
            <td align="left" class="l-table-edit-td"><input name="fac_id" type="text" id="fac_id" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b>药品信息:</b></td>
            <td align="left" class="l-table-edit-td"><input name="inv_msg" type="text" id="inv_msg" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b>规格型号:</b></td>
            <td align="left" class="l-table-edit-td"><input name="inv_model" type="text" id="inv_model" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
        </tr> 
         
    </table>

	<div id="maingrid"></div>
	<div id="resultPrint" style="display:none">
	   	<table width="100%">
			<thead>
		 
			<tr>
                <th width="200">证件</th>	
                <th width="200">药品</th>	
			</tr>
			   	</thead>
			   	<tbody></tbody>
	   	</table>
   	</div>
</body>
</html>
