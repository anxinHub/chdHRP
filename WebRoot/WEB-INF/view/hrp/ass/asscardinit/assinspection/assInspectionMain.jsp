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
<!-- 资产巡检记录  页面 -->
<script type="text/javascript">
    var grid;
    var gridManager = null;
    var userUpdateStr;
    $(function ()
    {
        loadDict();//加载下拉框
    	loadHead(null);	
		 loadHotkeys();  
    });
    //查询
    function  query(){
    		grid.options.parms=[];
    		grid.options.newPage=1;
        //根据表字段进行添加查询条件 
    	  grid.options.parms.push({name:'ins_name',value:$("#ins_name").val()});  
    	  grid.options.parms.push({name:'ass_card_no',value:'${ass_card_no}'}); 
    	  grid.options.parms.push({name:'dept_id',value:liger.get("dept_id").getValue().split("@")[0]}); 
    	  //grid.options.parms.push({name:'dept_no',value:$("#dept_no").val()}); 
    	  grid.options.parms.push({name:'create_emp',value:liger.get("create_emp").getValue()}); 
    	  grid.options.parms.push({name:'create_date_begin',value:$("#create_date_begin").val()}); 
    	  grid.options.parms.push({name:'create_date_end',value:$("#create_date_end").val()});
    	  grid.options.parms.push({name:'audit_emp',value:liger.get("audit_emp").getValue()}); 
    	  grid.options.parms.push({name:'audit_date_begin',value:$("#audit_date_begin").val()}); 
    	  grid.options.parms.push({name:'audit_date_end',value:$("#audit_date_end").val()}); 
    	  grid.options.parms.push({name:'state',value:liger.get("state").getValue()});  

    	//加载查询条件
    	grid.loadData(grid.where);
		$("#resultPrint > table > tbody").empty();
     }
    

    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
           columns: [  
                     { display: '巡检编号', name: 'ins_no', align: 'left',frozen: true,
                    	 
                    	 render : function(rowdata, rowindex,value) {
                    		 
 							return "<a href=javascript:openUpdate('"+rowdata.group_id   + "|" + 
																	rowdata.hos_id   + "|" + 
																	rowdata.copy_code   + "|" + 
																	rowdata.ins_id   +"')>"+
 									                                 rowdata.ins_no+"</a>";
 										}
					 		},
                     { display: '巡检名称', name: 'ins_name', align: 'left'
					 		},
		            { display: '巡检科室', name: 'dept_name', align: 'left'
						     },
                     { display: '资产性质', name: 'naturs_name', align: 'left'
					 		},
                     { display: '制单人', name: 'create_emp_name', align: 'left'
					 		},
                     { display: '制单日期', name: 'create_date', align: 'left'
					 		},
                     { display: '审核人', name: 'audit_emp_name', align: 'left'
					 		},
                     { display: '审核日期', name: 'audit_date', align: 'left'
					 		},
                     { display: '状态', name: 'state', align: 'left',
								render : function(rowdata, rowindex,
										value) {
										if(rowdata.state==0){
											return "新建";
										}
										else if(rowdata.state==1){
											return "审核";
										}else if(rowdata.state==2){
											return "终止";
										}
								   }
					 			
					 		},
                     { display: '备注', name: 'note', align: 'left'
					 		}
                     ],
                     dataAction: 'server',dataType: 'server',usePager:true,url:'queryAssInspectionMain.do?isCheck=false',
                     width: '100%', height: '100%', checkbox: true,rownumbers:true,
                     selectRowButtonOnly:true,//heightDiff: -10,
                     toolbar: { items: [
                     	{ text: '查询（<u>E</u>）', id:'search', click: query,icon:'search' }
                     	
				    				]},
    				onDblClickRow : function (rowdata, rowindex, value)
    				{
						openUpdate(
								rowdata.group_id   + "|" + 
								rowdata.hos_id   + "|" + 
								rowdata.copy_code   + "|" + 
								rowdata.ins_id   
							);
    				} 
                   });

        gridManager = $("#maingrid").ligerGetGridManager();
    }
    

   
     
   
      
       
       
       
    function openUpdate(obj){
    		
		var vo = obj.split("|");
		if("null"==vo[3]){
			return false;
			
		}
		var parm = "&group_id="+ 
		    vo[0] +"&hos_id="+ 
		    vo[1] +"& copy_code="+ 
		    vo[2] +"&ins_id="+
		    vo[3] ;
		 
		 parent.$.ligerDialog.open({
			 url: './assInspectionUpdatePage.do?isCheck=false&' + parm,
			 modal : true,
				showToggle : false,
				showMax : true,
				showMin : false,
				isResize : true
		 })
				layer.full(index);	
    
    }
    function loadDict(){
    	
		var param = {
            	query_key:''
        };
		
            //字典下拉框
    	autocomplete("#audit_emp", "../../../hrp/sys/queryUserDict.do?isCheck=false", "id", "text", true, true,param,true);
		
		autocomplete("#dept_id", "../queryDeptDict.do?isCheck=false", "id", "text", true, true,param,true);
		
		autocomplete("#create_emp", "../../../hrp/sys/queryUserDict.do?isCheck=false","id", "text",true,true,param,true);
		
		autocomplete("#ass_nature", "../queryAssNaturs.do?isCheck=false","id", "text",true,true,param,true);
		
        $("#ins_name").ligerTextBox({width:160});
        
        $("#create_date_begin").ligerTextBox({width:90});
        
        $("#create_date_end").ligerTextBox({width:90});
        
        $("#audit_date_begin").ligerTextBox({width:90});
        
        $("#audit_date_end").ligerTextBox({width:90});
        
        /* $("#state").ligerComboBox({width:160}); */
        $('#state').ligerComboBox({
			data:[{id:2,text:'确认'},{id:1,text:'审核'},{id:0,text:'新建'}],
			valueField: 'id',
            textField: 'text',
			cancelable:true,
			width:160
		})
        
         }  
    //键盘事件
	  function loadHotkeys() {

		hotkeys('E', query);

		hotkeys('A', add_open);
		
		hotkeys('D', remove);
		
		hotkeys('R', auditAssInspection);
		
		hotkeys('B', backAssInspection);
		
		hotkeys('S', stopAssInspection);
		
		hotkeys('E', exportExcel);

		hotkeys('P', printDate);
		 
	 }
  //打印数据
// 	 function printDate(){
// 		//有数据直接打印
// 		if($("#resultPrint > table > tbody").html()!=""){
// 			lodopPrinterTable("resultPrint","开始打印","资产巡检记录",true);
// 			return;
// 		}
		
// 		//重新查询数据，避免分页导致打印数据不全
// 		var manager = $.ligerDialog.waitting('系统正在准备打印数据,请稍候...');

// 		var printPara={
// 			usePager:false,  
// 			create_emp :liger.get("create_emp").getValue(),
// 	        create_date_begin:$("#create_date_begin").val(),
// 	        create_date_end:$("#create_date_end").val(),
// 	        ins_name:$("#ins_name").val(),
//             dept_id:liger.get("dept_id").getValue().split("@")[0], 
//             state :liger.get("state").getValue(),
//             audit_emp :liger.get("audit_emp").getValue(),
//             audit_date_begin:$("#audit_date_begin").val(),
//             audit_date_end:$("#audit_date_end").val()
            
//          };
// 		ajaxJsonObjectByUrl("queryAssInspectionMain.do",printPara,function (responseData){
// 			$.each(responseData.Rows,function(idx,item){ 
// 				 var trHtml="<tr>"; 
// 					 trHtml+="<td>"+item.ins_no+"</td>"; 
// 					 trHtml+="<td>"+item.ins_name+"</td>"; 
// 					 trHtml+="<td>"+item.ass_year+"</td>"; 
// 					 trHtml+="<td>"+item.ass_month+"</td>"; 
// 					 trHtml+="<td>"+item.naturs_name+"</td>"; 
// 					 trHtml+="<td>"+item.dept_name+"</td>"; 
// 					 trHtml+="<td>"+item.create_emp_name+"</td>"; 
// 					 trHtml+="<td>"+item.create_date+"</td>"; 
// 					 trHtml+="<td>"+item.audit_emp_name  +"</td>"; 
// 					 trHtml+="<td>"+item.audit_date +"</td>"; 
// 					 if (item.state == 0){
						  
// 						 trHtml+="<td>新建</td>";
						 
// 					 }else if (item.state == 1){
						  
// 						 trHtml+="<td>审核</td>";
						 
// 					 }if (item.state == 2){
						  
// 						 trHtml+="<td>终止</td>";
						 
// 					 }
					  
// 					 trHtml+="<td>"+item.note+"</td>"; 
// 				 trHtml+="</tr>"; 
// 				$("#resultPrint > table > tbody").append(trHtml);
// 			});
// 			manager.close();
// 			//alert($("#resultPrint").html())
// 			lodopPrinterTable("resultPrint","开始打印","资产巡检记录",true);
// 	    },true,manager);
// 		return;
// 	 }
	   
			  
    </script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<div id="toptoolbar" ></div>
    <table cellpadding="0" cellspacing="0" class="l-table-edit" >
        <tr>
            <td align="right" class="l-table-edit-td" style="padding-left: 20px;">制单日期：</td>
			<td align="left" class="l-table-edit-td"><input
				name="create_date_begin" type="text" id="create_date_begin"
				 class="Wdate"
				onFocus="WdatePicker({minDate:'#F{$dp.$D(\'create_date_end\');}',isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})" /></td>
			<td align="left"  >至：</td>
			<td align="left"><input name="create_date_end" type="text"
				id="create_date_end" 
				 class="Wdate"
				onFocus="WdatePicker({minDate:'#F{$dp.$D(\'create_date_begin\');}',isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})" /></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">制单人：</td>
            <td align="left" class="l-table-edit-td"><input name="create_emp" type="text" id="create_emp"  /></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">巡检名称：</td>
            <td align="left" class="l-table-edit-td"><input name="ins_name" type="text" id="ins_name"  /></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">巡检科室：</td>
            <td align="left" class="l-table-edit-td"><input name="dept_id" type="text" id="dept_id"  /></td>
        </tr>
        <tr>
            <td align="right" class="l-table-edit-td" style="padding-left: 20px;">审核日期：</td>
			<td align="left" class="l-table-edit-td"><input name="audit_date_begin" type="text" id="audit_date_begin"
				 class="Wdate"
				onFocus="WdatePicker({minDate:'#F{$dp.$D(\'audit_date_end\');}',isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})" /></td>
			<td align="left" >至：</td>
			<td align="left"><input name="audit_date_end" type="text" id="audit_date_end" 
				 class="Wdate"
				onFocus="WdatePicker({minDate:'#F{$dp.$D(\'audit_date_begin\');}',isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})" /></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">审核人：</td>
            <td align="left" class="l-table-edit-td"><input name="audit_emp" type="text" id="audit_emp"  /></td>
         	<td align="right" class="l-table-edit-td"  style="padding-left:20px;">状态：</td>
            <td align="left" class="l-table-edit-td">
            <!-- <select id="state"  name="state">
						<option value=""></option>
                		<option value="0">新建</option>
                		<option value="1">审核</option>
                		<option value="2">终止</option>
                	</select> -->
                	<input name="state" type="text" id="state" />
                	</td>
            <td align="left"></td>
        </tr>  
    </table>

	<div id="maingrid"></div>
	<div id="resultPrint" style="display:none">
	   	<table width="100%">
			<thead>
		 
			<tr> 
                <th width="200">巡检编号</th>	
                <th width="200">巡检名称</th>	
                <th width="200">统计年度</th>	
                <th width="200">统计月份</th>	
                <th width="200">资产性质</th>	
                <th width="200">科室</th>	
                <th width="200">制单人</th>	
                <th width="200">制单日期</th>	
                <th width="200">审核人</th>	
                <th width="200">审核日期</th>	
                <th width="200">状态</th>	
                <th width="200">备注</th>	
			</tr>
			   	</thead>
			   	<tbody></tbody>
	   	</table>
   	</div>
</body>
</html>
