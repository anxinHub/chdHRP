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
    $(function ()
    {
        loadDict();//加载下拉框
    	//加载数据
    	loadHead(null);	
		loadHotkeys();
		$("#near").change(function(){
			if($("#near").prop("checked") == true){
				state = 1 ;
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
				$('#near').prop('checked',false) ;
				$('#last').prop('checked',false) ;
			}else{
				state = '';
			}
			query();
		})
    });
    //查询
    function  query(){
    		grid.options.parms=[];
    		grid.options.newPage=1;
        //根据表字段进行添加查询条件
        grid.options.parms.push({name:'type_code',value:liger.get("type_code").getValue().split(",")[0]});
        grid.options.parms.push({name:'sup_id',value:liger.get("sup_id").getValue().split(",")[0]});
    	grid.options.parms.push({name:'type_id',value:liger.get("type_id").getValue().split(",")[0]});
    	grid.options.parms.push({name:'cert_id',value:$("#cert_id").val()});
    	grid.options.parms.push({name:'cert_code',value:$("#cert_code").val()});
    	grid.options.parms.push({name:'queryDate',value:$("#queryDate").val()});
    	grid.options.parms.push({name:'state',value:state}); 
    	//加载查询条件
    	grid.loadData(grid.where);
		$("#resultPrint > table > tbody").empty();
     }

    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
           columns: [ 
                     { display: '供应商编码', name: 'sup_code', align: 'left',width:120},
                     { display: '供应商名称', name: 'sup_name', align: 'left',width:240},
					 { display: '证件类型', name: 'type_code', align: 'left',width:60},
                     { display: '证件名称', name: 'type_name', align: 'left',width:160},
				 	 { display: '证件编号', name: 'cert_code', align: 'left',width:250, 
                    	 render: function(rowdata,index,value){
 				 		 	return "<a href=javascript:openCert('"+rowdata.group_id+"|"+
 				 		 			rowdata.hos_id+"|"+rowdata.copy_code+"|"+rowdata.cert_id+"')>"+value+"</a>";
 				 	 	}
                     },
                     { display: '截止日期', name: 'end_date', align: 'left',width:80},
                     { display: '查询日期', name: 'queryDate', align: 'left',width:80},
					 { display: '状态', name: '', align: 'center',width:60,
					 			render:function(rowdata,index,value){
					 				if(rowdata.days < 0){
					 					return '<span style="color:red">过期</span>';
					 				}else if(rowdata.days == 0){
					 					return '<span style="color:blue">到期</span>';
					 				}else if(rowdata.days < rowdata.warn_days){
					 					return '<span style="color:#00009C">临近</span>';
					 				}
					 			}
					 		},
					  { display: '天数', name: 'days', align: 'center',width:80,
						 		render:function(rowdata,index,value){
						 			if(rowdata.days == 0){
		                    			 return "0";
		                    		 }else{
		                    			 return Medh.abs(rowdata.days);
		                    		 }
						 		}
	                    	 }
                     ],
                     dataAction: 'server',dataType: 'server',usePager:true,url:'queryMedSupCertWarning.do?isCheck=false',
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
    
    function openCert(obj){
    	var vo = obj.split("|");
		var parm = "group_id="+vo[0]+"&hos_id="+vo[1]+"&copy_code="+vo[2]+"&cert_id="+vo[3];
	
		parent.parent.$.ligerDialog.open({  
			title: '供应商证件修改',
			height: 400,
			width: 700,
			url: 'hrp/med/info/basic/cert/vencertdetail/medVenCertDetailUpdatePage.do?isCheck=false&' + parm.toString(),
			modal: true, showToggle: false, showMax: false, showMin: false, isResize: true,
			parentframename: window.name,  //用于parent弹出层调用本页面的方法或变量
			buttons: [ 
				{ text: '确定', onclick: function (item, dialog) { dialog.frame.saveMedVenCertDetail(); query();}, cls:'l-dialog-btn-highlight' }, 
				{ text: '取消', onclick: function (item, dialog) { dialog.close(); } } 
			]
		});
    }
    
    function loadDict(){
       //字典下拉框
    	//供应商类别 下拉框
		autocomplete("#type_code", "../../queryHosSupType.do?isCheck=false", "id", "text", true, true,'',false,'',200);
       
       //供应商 下拉框
		autocomplete("#sup_id", "../../queryHosSupDict.do?isCheck=false", "id", "text", true, true,'',false,'',300);
       
    	//供应商证件类型 下拉框
		autocomplete("#type_id", "../../queryMedVenCertType.do?isCheck=false", "id", "text", true, true,'',false,'',200);
    	
    	autodate("#queryDate",'yyyy-MM-dd');
    	
		$("#type_code").ligerTextBox({width:200});
		$("#sup_id").ligerTextBox({width:300});
		$("#type_id").ligerTextBox({width:200});
		$("#cert_id").ligerTextBox({width:200});
		$("#cert_code").ligerTextBox({width:300});
		$("#queryDate").ligerTextBox({width:200});
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
	   			title:'供应商证件效期预警',
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
	   		ajaxJsonObjectByUrl("queryMedSupCertWarning.do?isCheck=false", selPara, function (responseData) {
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
        	<td align="right" class="l-table-edit-td"  style="padding-left:20px;">供应商类别：</td>
            <td align="left" class="l-table-edit-td"><input name="type_code" type="text" id="type_code" ltype="text" " /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">供应商：</td>
            <td align="left" class="l-table-edit-td"><input name="sup_id" type="text" id="sup_id" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
           <td align="right" class="l-table-edit-td"  style="padding-left:20px;">证件类型：</td>
            <td align="left" class="l-table-edit-td">
            	<input name="type_id" type="text" id="type_id" ltype="text" type="text" />
            </td>
        </tr>
        <tr>
        	<td align="right" class="l-table-edit-td"  style="padding-left:20px;">证件名称：</td>
            <td align="left" class="l-table-edit-td">
            	<input name="cert_id" type="text" id="cert_id" ltype="text" type="text" />
            </td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">证件编号：</td>
            <td align="left" class="l-table-edit-td">
            	<input name="cert_code" type="text" id="cert_code" ltype="text" type="text" />
            </td>
             <td align="left"></td>
        	<td align="right" class="l-table-edit-td"  style="padding-left:20px;">查询日期：</td>
            <td align="left" class="l-table-edit-td">
            	<input class="Wdate" name="queryDate" type="text" id="queryDate" ltype="text" type="text" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})" />
            </td>
            <td align="left"></td>
        </tr> 
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">查询内容：</td>
            <td align="left" class="l-table-edit-td" colspan="2">
	            <input id="near" type="checkbox" ltype="text" />临近
	            <input id="last" type="checkbox" ltype="text" />到期
	            <input id="past" type="checkbox" ltype="text" />过期
	        </td>
	        <td align="left"></td>
        </tr> 
    </table>
	<div id="maingrid"></div>
	<div id="resultPrint" style="display:none">
	   	<table width="100%">
			<thead>
			<tr>
                <th width="200">供应商编码</th>	
                <th width="200">供应商名称</th>	
                <th width="200">证件类型</th>
                <th width="200">证件名称</th>
                <th width="200">证件编号</th>	
                <th width="200">截止日期</th>
                <th width="200">查询日期</th>	
                <th width="200">状态</th>	
                <th width="200">天数</th>	
			</tr>
			   	</thead>
			   	<tbody></tbody>
	   	</table>
   	</div>
</body>
</html>
