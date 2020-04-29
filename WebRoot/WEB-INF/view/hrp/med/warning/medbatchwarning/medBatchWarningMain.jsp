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
    var state ;
    var is_com ;
    $(function ()
    {
        loadDict();//加载下拉框
    	//加载数据
    	loadHead(null);	
		loadHotkeys();
		$("#near").change(function(){
			if($("#near").prop("checked") == true){
				state = 1 ;
				if($("#aff").prop("checked") == true){
					is_com = 1 ;
				}else{
					if($("#noAff").prop("checked") == true){
						is_com = 0 ;
					}else{
						is_com = '';
					}
				}
				$('#last').prop('checked',false) ;
				$('#past').prop('checked',false) ;
			}else{
				state = '' ;
			}
			query();
		})
		$("#last").change(function(){
			if($("#last").prop("checked") == true){
				state = 2 ;
				if($("#aff").prop("checked") == true){
					is_com = 1 ;
				}else{
					if($("#noAff").prop("checked") == true){
						is_com = 0 ;
					}else{
						is_com = '';
					}
				}
				$('#near').prop('checked',false) ;
				$('#past').prop('checked',false) ;
			}else{
				state = '' ;
			}
			query();
		})
		$("#past").change(function(){
			if($("#past").prop("checked") == true){
				state = 3 ;
				if($("#aff").prop("checked") == true){
					is_com = 1 ;
				}else{
					if($("#noAff").prop("checked") == true){
						is_com = 0 ;
					}else{
						is_com = '';
					}
				}
				$('#near').prop('checked',false) ;
				$('#last').prop('checked',false) ;
			}else{
				state = '' ;
			}
			query();
		})
		$("#aff").change(function(){
			if($("#aff").prop("checked") == true){
				is_com = 1 ;
				$('#noAff').prop('checked',false) ;
			}else{
				
				if($("#noAff").prop("checked") == true){
					is_com = 0 ;
					$('#aff').prop('checked',false) ;
				}else{
					is_com = '';
				}
			}
			query();
		})
		$("#noAff").change(function(){
			if($("#noAff").prop("checked") == true){
				is_com = 0 ;
				$('#aff').prop('checked',false) ;
			}else{
				if($("#aff").prop("checked") == true){
					is_com = 1 ;
					$('#noAff').prop('checked',false) ;
				}else{
					is_com = '';
				}
			}
			query();
		})
		
    });
    //查询
    function  query(){
    		grid.options.parms=[];
    		grid.options.newPage=1;
        //根据表字段进行添加查询条件
        grid.options.parms.push({name:'store_id',value:liger.get("store_id").getValue().split(",")[0]});
    	grid.options.parms.push({name:'type_id',value:liger.get("type_id").getValue().split(",")[0]}); 
    	grid.options.parms.push({name:'inv_model',value:$("#inv_model").val()});
    	grid.options.parms.push({name:'inv_name',value:$("#inv_name").val()});
    	grid.options.parms.push({name:'queryDate',value:$("#queryDate").val()});
    	grid.options.parms.push({name:'state',value:state}); 
    	grid.options.parms.push({name:'is_com',value:is_com});
    	//加载查询条件
    	grid.loadData(grid.where);
		$("#resultPrint > table > tbody").empty();
     }

    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
           columns: [ 
                     { display: '仓库名称', name: 'store_name', align: 'left',width:100},
                     { display: '药品编码', name: 'inv_code', align: 'left',width:100},
                     { display: '药品名称', name: 'inv_name', align: 'left',width:160},
                     { display: '规格型号', name: 'inv_model', align: 'left',width:200},
					 { display: '计量单位', name: 'unit_name', align: 'left',width:80},
                     { display: '批号', name: 'batch_no', align: 'left',width:100},
				 	 { display: '条码', name: 'bar_code', align: 'left',width:110},
                     { display: '结存数量', name: 'left_amount', align: 'left',width:80},
                     { display: '有效日期', name: 'inva_date', align: 'left',width:80},
                     { display: '查询日期', name: 'queryDate', align: 'left',width:80},
					 { display: '天数', name: 'days', align: 'center',width:80,
	                    	 render:function(rowdata,index,value){
		                    	 if(rowdata.days == 0){
		                			 return "0";
		                		 }else{
		                			 return Medh.abs(rowdata.days);
		                		 }
	                    	 }
                    	 },
					/*  { display: '状态1', name: 'state_name', align: 'center',width:60,hide:true
					 		}, */
					 		
					 		 { display: '状态', name: 'state_name', align: 'center',width:60,
						 		  	render:function(rowdata,index,value){
						 				if(value =='过期'){
						 					return '<span style="color:red">过期</span>';
						 				}else if(value == '到期'){
						 					return '<span style="color:blue">到期</span>';
						 				}else if(value =='临近'){
						 					return '<span style="color:#00009C">临近</span>';
						 				}else if(value =='安全'){
						 					return '<span style="color:#00FF7F">安全</span>';
						 				}
						 			}  
						 		},
                     ],
                     dataAction: 'server',dataType: 'server',usePager:true,url:'queryMedBatchWarning.do?isCheck=false',
                     width: '100%', height: '100%', checkbox: true,rownumbers:true,
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
    
    /* function getDays(rowdata){
        	var str = rowdata.inva_date.split("-");
        	var invaDate = new Date(str[1] + '-'+ str[2] + '-'+str[0]);
        	var t1= str[0]+str[1]+str[2];
	    	var str1 = $("#queryDate").val();
			var year = str1.getFullYear();
			var month = str1.getMonth()+1;
			var day = str1.getDate();
			
			if(month <10){
				month = "0"+month;
			}
			if(day <10){
				day = "0"+day;
			}
			var t2 = year+month+day;
			var curentDate = new Date(month + '-'+ day + '-'+year);
			if(t1<t2){
				days = '-'+ parseInt(Medh.abs(endDate - curentDate)  /  1000  /  60  /  60  /24);
			}else{
				days =  parseInt(Medh.abs(endDate - curentDate)  /  1000  /  60  /  60  /24);
			}
			return days;
    } */
    function loadDict(){
       //字典下拉框
    	
       
       //仓库下拉框
		autocomplete("#store_id", "../../queryMedStore.do?isCheck=false", "id", "text", true, true);
    	//物资类别下拉框
		autocomplete("#type_id", "../../queryMedType.do?isCheck=false", "id", "text", true, true);
    	
    	autodate("#queryDate",'yyyy-MM-dd');
    	
		$("#store_id").ligerTextBox({width:160});
		$("#type_id").ligerTextBox({width:160});
		$("#queryDate").ligerTextBox({width:160});
		
		$("#inv_model").ligerTextBox({width:160});
		$("#inv_name").ligerTextBox({width:160});
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
	   			title:'药品效期预警',
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
	   		ajaxJsonObjectByUrl("queryMedBatchWarning.do?isCheck=false", selPara, function (responseData) {
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
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">查询日期：</td>
            <td align="left" class="l-table-edit-td">
            	<input class="Wdate" name="queryDate" type="text" id="queryDate" ltype="text" type="text" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})" />
            </td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">物资类别：</td>
            <td align="left" class="l-table-edit-td"><input name="type_id" type="text" id="type_id" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
        </tr> 
        <tr>
        	<td align="right" class="l-table-edit-td"  style="padding-left:20px;">药品名称：</td>
            <td align="left" class="l-table-edit-td"><input name="inv_name" type="text" id="inv_name" ltype="text" " /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">规格型号：</td>
            <td align="left" class="l-table-edit-td"><input name="inv_model" type="text" id="inv_model" ltype="text" " /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">查询内容：</td>
            <td align="left" class="l-table-edit-td" colspan="2">
	            <input id="near" type="checkbox" ltype="text" />临近
	            <input id="last" type="checkbox" ltype="text" />到期
	            <input id="past" type="checkbox" ltype="text" />过期
	            <input id="aff" type="checkbox" ltype="text" />代销
	            <input id="noAff" type="checkbox" ltype="text" />非代销
	        </td>
	        <td align="left"></td>
        </tr> 
    </table>
	<div id="maingrid"></div>
	<div id="resultPrint" style="display:none">
	   	<table width="100%">
			<thead>
			<tr>
                <th width="200">仓库名称</th>	
                <th width="200">药品编码</th>	
                <th width="200">药品名称</th>	
                <th width="200">规格型号</th>
                <th width="200">计量单位</th>
                <th width="200">批号</th>	
                <th width="200">条码</th>
                <th width="200">结存数量</th>	
                <th width="200">有效日期</th>
                <th width="200">查询日期</th>		
                <th width="200">天数</th>	
                <th width="200">状态</th>	
			</tr>
			   	</thead>
			   	<tbody></tbody>
	   	</table>
   	</div>
</body>
</html>
