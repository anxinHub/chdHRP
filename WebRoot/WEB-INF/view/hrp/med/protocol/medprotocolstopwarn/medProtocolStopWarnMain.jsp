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
          grid.options.parms.push({name:'beginDate',value:$("#beginDate").val()});
    	  grid.options.parms.push({name:'endDate',value:$("#endDate").val()});
    	  grid.options.parms.push({name:'type_id',value:liger.get("type_id").getValue().split(",")[0]}); 
    	  grid.options.parms.push({name:'sup_id',value:liger.get("sup_id").getValue().split(",")[0]});
    	  grid.options.parms.push({name:'protocol_name',value:$("#protocol_name").val()});
    	  grid.options.parms.push({name:'protocol_code',value:$("#protocol_code").val()}); 
    	  grid.options.parms.push({name:'dept_id',value:liger.get("dept_id").getValue().split(",")[0]});
    	  grid.options.parms.push({name:'first_man',value:liger.get("first_man").getValue().split(",")[0]}); 
    	  grid.options.parms.push({name:'second_man',value:$("#second_man").val()}); 
    	  grid.options.parms.push({name:'state',value:$("#state").val()}); 

    	//加载查询条件
    	grid.loadData(grid.where);
		$("#resultPrint > table > tbody").empty();
     }

    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
           columns: [ 
                     { display: '协议编号', name: 'protocol_code', align: 'left',
                    	 render:function(rowdata,index,value){
                    		 return "<a href=javascript:openUpdate('"+rowdata.group_id+"|"+rowdata.hos_id+"|"+rowdata.copy_code+"|"+rowdata.protocol_id+"|"+rowdata.state+"')>"+rowdata.protocol_code+"</a>";
                    	 }
					 		},
                     
                     { display: '协议名称', name: 'protocol_name', align: 'left'},
                     { display: '签订日期', name: 'sign_date', align: 'left',width:80},
                     { display: '供应商', name: 'sup_name', align: 'left',width:200},
					 { display: '签订单位', name: 'hos_name', align: 'left'},
                     { display: '签订部门', name: 'dept_name', align: 'left'},
				 	 { display: '开始日期', name: 'start_date', align: 'left',width:80},
                     { display: '截止日期', name: 'end_date', align: 'left',width:80},
					 { display: '设置天数', name: 'war_days', align: 'right',width:80},
					 { display: '预警天数', name: '', align: 'right',width:80,
					 			render:function(rowdata,index,value){
					 				return getDays(rowdata.end_date);
					 			}
					 		},
                     ],
                     dataAction: 'server',dataType: 'server',usePager:true,url:'queryMedProtocolStopWarn.do?isCheck=true',
                     width: '100%', height: '100%', checkbox: true,rownumbers:true,
                     selectRowButtonOnly:true,heightDiff: 0,
                     toolbar: { items: [
								{ text: '查询（<u>Q</u>）', id:'search', click: query,icon:'search' },
								{ line:true },
// 								{ text: '导出Excel（<u>E</u>）', id:'export', click: exportExcel,icon:'pager' },
// 								{ line:true },
								{ text: '打印（<u>P</u>）', id:'print', click: print,icon:'print' },
								{ line:true }
    				]},
    				
    				onDblClickRow : function (rowdata, rowindex, value)
    				{
						openUpdate(
								rowdata.group_id   + "|" + 
								rowdata.hos_id   + "|" + 
								rowdata.copy_code   + "|" + 
								rowdata.protocol_id + "|" + 
								rowdata.state
							);
    				} 
                   });

        gridManager = $("#maingrid").ligerGetGridManager();
    }
    
    function openUpdate(obj){
    		
		var vo = obj.split("|");
		var parm = 
			"group_id="+vo[0]   +"&"+ 
			"hos_id="+vo[1]   +"&"+ 
			"copy_code="+vo[2]   +"&"+ 
			"protocol_id="+vo[3] 
		$.ligerDialog.open({ url : 'medProtocolStopWarnUpdatePage.do?isCheck=false&'+parm,data:{}, 
			height: 650,width: 1000, title:'修改',modal:true,showToggle:false,showMax:false,
			showMin: false,isResize:true,
			});
    }
    function getDays(end_date){
        	var str = end_date.split("-");
        	var endDate = new Date(str[1] + '-'+ str[2] + '-'+str[0]);
        	var t1= str[0]+str[1]+str[2];
	    	var str1 = new Date();
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
				days = '-'+ parseInt(Math.abs(endDate - curentDate)  /  1000  /  60  /  60  /24);
			}else{
				days =  parseInt(Math.abs(endDate - curentDate)  /  1000  /  60  /  60  /24);
			}
			return days;
    }
    function loadDict(){
       //字典下拉框
    	
    	//协议类别下拉框
		autocomplete("#type_id", "../../queryMedProtocolType.do?isCheck=false", "id", "text", true, true,'',true,'',160);
		//供应商下拉框
		autocomplete("#sup_id", "../../queryHosSupDict.do?isCheck=false", "id", "text", true, true);
		//签订部门：当前用户有权限的职能科室列表（HOS_DEPT_DICT 中is_stop=0的职能科室列表）
		autocomplete("#dept_id", "../../querySignedDept.do?isCheck=false", "id", "text", true, true);
		//甲方负责人（职工下拉框）
		autocomplete("#first_man", "../../queryMedEmpDict.do?isCheck=false", "id", "text", true, true,'',false,'',160);
		
		autodate("#beginDate", "yyyy-mm-dd", "month_first");
	    autodate("#endDate", "yyyy-mm-dd", "month_last");
		$("#beginDate").ligerTextBox({width:160});
		$("#endDate").ligerTextBox({width:160});
		$("#sign_date").ligerTextBox({width:160});
		$("#type_id").ligerTextBox({width:160});
		$("#sup_id").ligerTextBox({width:160});
		$("#protocol_name").ligerTextBox({width:370});
		$("#protocol_code").ligerTextBox({width:370});
		$("#dept_id").ligerTextBox({width:160});
		$("#first_man").ligerTextBox({width:160});
	    $("#second_man").ligerTextBox({width:160});
	    $("#state").ligerTextBox({width:160});
    }
    //键盘事件
	  function loadHotkeys() {
		hotkeys('Q', query);
		hotkeys('E', exportExcel);
		hotkeys('P', printDate);
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
	   			title:'协议临近到期提醒',
	   			head:[
					{"cell":0,"value":"单位: ${sessionScope.hos_name}","colspan":colspan_num,"br":true},
					{"cell":0,"value":"统计日期: " + $("#beginDate").val() +"至"+ $("#endDate").val(),"colspan":colspan_num,"br":true}
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
	   		ajaxJsonObjectByUrl("queryMedProtocolStopWarn.do?isCheck=false", selPara, function (responseData) {
	   			printGridView(responseData,printPara);
			});

	   		
	    }
/*   //打印数据
	 function printDate(){
		//有数据直接打印
		if($("#resultPrint > table > tbody").html()!=""){
			lodopPrinterTable("resultPrint","开始打印","协议截止提醒",true);
			return;
		}
		
		//重新查询数据，避免分页导致打印数据不全
		var manager = $.ligerDialog.waitting('系统正在准备打印数据,请稍候...');

		var printPara={
			usePager:false,
			sign_date:$("#sign_date").val(),
			type_id:liger.get("type_id").getValue().split(",")[0],
			sup_id:liger.get("sup_id").getValue().split(",")[0],
			protocol_name:$("#protocol_name").val(),
            protocol_code:$("#protocol_code").val(),
           	dept_id:liger.get("dept_id").getValue().split(",")[0],
           	first_man:liger.get("first_man").getValue().split(",")[0],
           	second_man:$("#second_man").val(),
           	state:$("#state").val()
         };
		ajaxJsonObjectByUrl("queryMedProtocolStopWarn.do",printPara,function (responseData){
			 var trHtml='';
				$.each(responseData.Rows,function(idx,item){ 
					 	 trHtml+="<tr>";
						 trHtml+="<td>"+item.protocol_code+"</td>"; 
						 trHtml+="<td>"+item.type_name+"</td>"; 
						 trHtml+="<td>"+item.protocol_name+"</td>"; 
						 trHtml+="<td>"+item.sign_date+"</td>"; 
						 trHtml+="<td>"+item.sup_name+"</td>";
						 trHtml+="<td>"+item.hos_name+"</td>";
						 trHtml+="<td>"+item.dept_name+"</td>";
						 trHtml+="<td>"+item.start_date+"</td>"; 
						 trHtml+="<td>"+item.end_date+"</td>";
						 trHtml+="<td>"+item.war_days+"</td>";
						 trHtml+="<td>"+getDays(item.end_date)+"</td>";
				trHtml+="</tr>";
				$("#resultPrint > table > tbody").empty();
				$("#resultPrint > table > tbody").append(trHtml);
			});
			manager.close();
			//alert($("#resultPrint").html())
			lodopPrinterTable("resultPrint","开始打印","协议截止提醒",true);
	    },true,manager);
		return;
	 }
	
	 
	 //导出数据
	 function exportExcel(){
		//有数据直接导出
		if($("#resultPrint > table > tbody").html()!=""){
			lodopExportExcel("resultPrint","导出Excel","协议截止提醒.xls",true);
			return;
		}
		
		//重新查询数据，避免分页导致打印数据不全
		var manager = $.ligerDialog.waitting('系统正在准备导出数据,请稍候...');

		var exportPara={
			usePager:false,
			sign_date:$("#sign_date").val(),
			type_id:liger.get("type_id").getValue().split(",")[0],
			sup_id:liger.get("sup_id").getValue().split(",")[0],
			protocol_name:$("#protocol_name").val(),
            protocol_code:$("#protocol_code").val(),
           	dept_id:liger.get("dept_id").getValue().split(",")[0],
           	first_man:liger.get("first_man").getValue().split(",")[0],
           	second_man:$("#second_man").val(),
           	state:$("#state").val()
         };
		ajaxJsonObjectByUrl("queryMedProtocolStopWarn.do",exportPara,function (responseData){
			 var trHtml='';
			$.each(responseData.Rows,function(idx,item){ 
				trHtml+="<tr>";
				 trHtml+="<td>"+item.protocol_code+"</td>"; 
				 trHtml+="<td>"+item.type_name+"</td>"; 
				 trHtml+="<td>"+item.protocol_name+"</td>"; 
				 trHtml+="<td>"+item.sign_date+"</td>"; 
				 trHtml+="<td>"+item.sup_name+"</td>";
				 trHtml+="<td>"+item.hos_name+"</td>";
				 trHtml+="<td>"+item.dept_name+"</td>";
				 trHtml+="<td>"+item.start_date+"</td>"; 
				 trHtml+="<td>"+item.end_date+"</td>";
				 trHtml+="<td>"+item.war_days+"</td>";
				 trHtml+="<td>"+getDays(item.end_date)+"</td>";
				 trHtml+="</tr>";
				 $("#resultPrint > table > tbody").empty();
				 $("#resultPrint > table > tbody").append(trHtml);
			});
			manager.close();
			lodopExportExcel("resultPrint","导出Excel","协议截止提醒.xls",true);
	    },true,manager);
		return;
	 }	 */	  
    </script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>
	<div id="toptoolbar" ></div>
    <table cellpadding="0" cellspacing="0" class="l-table-edit" >
        <tr>
        	<td align="right" class="l-table-edit-td"  style="padding-left:20px;">签订日期：</td>
            <td align="left" class="l-table-edit-td" style="width: 160">
            	<input class="Wdate" name="beginDate" id="beginDate" type="text" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})"/>
            </td>
            <td align="left" class="l-table-edit-td" style="width: 15">至：</td>
            <td align="left" class="l-table-edit-td" style="width: 160"><input class="Wdate" name="endDate" id="endDate" type="text" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})"/></td>
        	<td align="right" class="l-table-edit-td"  style="padding-left:20px;"><font color="red">*</font>协议类别：</td>
            <td align="left" class="l-table-edit-td"><input name="type_id" type="text" id="type_id" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">供应商：</td>
            <td align="left" class="l-table-edit-td"><input name="sup_id" type="text" id="sup_id" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
        </tr> 
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">协议名称：</td>
            <td align="left" class="l-table-edit-td" colspan="3"><input name="protocol_name" type="text" id="protocol_name" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">甲方负责人：</td>
            <td align="left" class="l-table-edit-td" ><input name="first_man" type="text" id="first_man" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">签订部门：</td>
            <td align="left" class="l-table-edit-td"><input name="dept_id" type="text" id="dept_id" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
        </tr> 
           
        <tr>
        	<td align="right" class="l-table-edit-td"  style="padding-left:20px;">协议编号：</td>
            <td align="left" class="l-table-edit-td" colspan="3"><input name="protocol_code" type="text" id="protocol_code" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">乙方负责人：</td>
            <td align="left" class="l-table-edit-td"><input name="second_man" type="text" id="second_man" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">状态：</td>
            <td align="left" class="l-table-edit-td">
            	<select name="state" id="state"style="width: 135px;" >
            			<option value="">请选择</option>
                		<option value="1">新建</option>
                		<option value="2">审核</option>
                		<option value="3">确认</option>
                		<option value="4">终止</option>
            	</select>
            </td>
            <td align="left"></td>
        </tr> 
    </table>
	<div id="maingrid"></div>
	<div id="resultPrint" style="display:none">
	   	<table width="100%">
			<thead>
			<tr>
                <th width="200">协议编号</th>	
                <th width="200">类别</th>	
                <th width="200">协议名称</th>	
                <th width="200">签订日期</th>
                <th width="200">供应商</th>
                <th width="200">签订单位</th>	
                <th width="200">签订科室</th>
                <th width="200">开始日期</th>	
                <th width="200">截止日期</th>	
                <th width="200">设置天数</th>	
                <th width="200">预警天数</th>	
			</tr>
			   	</thead>
			   	<tbody></tbody>
	   	</table>
   	</div>
</body>
</html>
