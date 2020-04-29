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
        grid.options.parms.push({name:'store_id',value:liger.get("store_id").getValue().split(",")[0]});
    	grid.options.parms.push({name:'type_id',value:liger.get("type_id").getValue().split(",")[0]}); 
    	grid.options.parms.push({name:'inv_id',value:liger.get("inv_id").getValue().split(",")[0]});
    	grid.options.parms.push({
			name : 'inv_model',//规格型号
			value : $("#inv_model").val()
		});
    	//加载查询条件
    	grid.loadData(grid.where);
		$("#resultPrint > table > tbody").empty();
     }

    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
           columns: [ 
                     { display: '药品编码', name: 'inv_code', align: 'left',width:160},
                     { display: '药品名称', name: 'inv_name', align: 'left',width:240},
                     { display: '规格型号', name: 'inv_model', align: 'left',width:260},
					 { display: '计量单位', name: 'unit_name', align: 'left',width:110},
                     { display: '结存数量', name: 'cur_amount', align: 'left',width:110},
                     { display: '低限库存量', name: 'low_limit', align: 'left',width:110},
                     { display: '短缺量', name: 'diff', align: 'left',width:100}
                     ],
                     dataAction: 'server',dataType: 'server',usePager:true,url:'queryMedShortStoreWarning.do?isCheck=false',
                     width: '100%', height: '100%', checkbox: true,rownumbers:true,frozen:false ,
                     selectRowButtonOnly:true,heightDiff: -5,
                     toolbar: { items: [
								{ text: '查询（<u>Q</u>）', id:'search', click: query,icon:'search' },
								{ line:true },
								/* { text: '导出Excel（<u>E</u>）', id:'export', click: exportExcel,icon:'pager' },
								{ line:true }, */
								{ text: '打印（<u>P</u>）', id:'print', click: print,icon:'print' },
								{ line:true },
    				]},
    				
                   });

        gridManager = $("#maingrid").ligerGetGridManager();
    }
    
    function loadDict(){
       //字典下拉框
    	
       
       //仓库下拉框
		autocomplete("#store_id", "../../queryMedStore.do?isCheck=false", "id", "text", true, true,'',false,false,200);
    	//物资类别下拉框
		autocomplete("#type_id", "../../queryMedType.do?isCheck=false", "id", "text", true, true,'',false,false,200);
    	
		//物资类别下拉框
		//autocomplete("#inv_id", "../../queryMedInv.do?isCheck=false", "id", "text", true, true,'',false,false,240);
    	
		$("#store_id").ligerTextBox({width:200});
		$("#type_id").ligerTextBox({width:200});
		$("#inv_id").ligerTextBox({width:240});
		 $("#inv_model").ligerTextBox({width:160});
    }
    //键盘事件
	  function loadHotkeys() {
		hotkeys('Q', query);
		hotkeys('E', exportExcel);
		hotkeys('P', print);
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
	   			title:'短缺货预警',
	   			head:[
					{"cell":0,"value":"单位: ${sessionScope.hos_name}","colspan":colspan_num,"br":true},
					{"cell":0,"value":"统计日期: " + $("#queryDate").val() ,"colspan":colspan_num,"br":true}
	   			],
	   			foot:[
					{"cell":0,"value":"主管:","colspan":3,"br":false} ,
					{"cell":3,"value":"复核人:","colspan":colspan_num-5,"br":false},
					{"cell":colspan_num-2,"value":"制单人： ${sessionScope.user_name}","colspan":2,"br":true},
					{"cell":0,"value":"打印日期: " + cur_date,"colspan":colspan_num,"br":true}
	   			],
	   			columns:grid.getColumns(1),
	   			headCount:1,//列头行数
	   			autoFile:true,
	   			type:3
	   		};
	   		ajaxJsonObjectByUrl("queryMedShortStoreWarning.do?isCheck=false", selPara, function (responseData) {
	   			printGridView(responseData,printPara);
			});

	   		
	    }
	 
    </script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>
	<div id="toptoolbar" ></div>
    <table cellpadding="0" cellspacing="0" class="l-table-edit" >
        <tr>
        	<td align="right" class="l-table-edit-td"  style="padding-left:20px;">仓库名称：</td>
            <td align="left" class="l-table-edit-td"><input name="store_id" type="text" id="store_id" ltype="text" " /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">物资类别：</td>
            <td align="left" class="l-table-edit-td"><input name="type_id" type="text" id="type_id" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">药品名称：</td>
            <td align="left" class="l-table-edit-td">
            	<input name="inv_id" type="text" id="inv_id" ltype="text" type="text" />
            </td>
            <td align="left"></td>
        <td align="right" class="l-table-edit-td" >规格型号:</td>
		 <td align="left" class="l-table-edit-td" >
			<input name="inv_model" type="text" id="inv_model" ltype="text"    validate="{required:true}" />
		</td>
		
         <td align="left"></td>
        </tr> 
    </table>
	<div id="maingrid"></div>
	<div id="resultPrint" style="display:none">
	   	<table width="100%">
			<thead>
			<tr>
                <th width="200">药品编码</th>	
                <th width="200">药品名称</th>	
                <th width="200">规格型号</th>
                <th width="200">计量单位</th>
                <th width="200">结存数量</th>	
                <th width="200">低限库存量</th>
                <th width="200">短缺量</th>		
			</tr>
			   	</thead>
			   	<tbody></tbody>
	   	</table>
   	</div>
</body>
</html>
