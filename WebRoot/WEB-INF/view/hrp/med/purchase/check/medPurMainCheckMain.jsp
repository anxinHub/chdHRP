<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html style="overflow:hidden;">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="${path}/inc_jquery_1.9.0.jsp" />
<script src="<%=path%>/lib/hrp/med/med.js"	type="text/javascript"></script>
<script type="text/javascript">
    var grid;
    var gridManager = null;
    var userUpdateStr;
    var is_dir = 0;
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
    	  grid.options.parms.push({name:'begin_date',value:$("#begin_date").val()});//日期范围：开始时间 
    	  grid.options.parms.push({name:'end_date',value:$("#end_date").val()});//日期范围：结束时间
    	  grid.options.parms.push({name:'dept_id',value:liger.get("dept_id").getValue().split(",")[0]});//编制部门
    	  grid.options.parms.push({name:'state',value:liger.get("planState").getValue()});//状态
    	  grid.options.parms.push({name:'brif',value:$("#brif").val()});//摘要
    	  grid.options.parms.push({name:'pur_type',value:liger.get("pur_type").getValue()});//计划类型
    	  if(liger.get("pur_type").getValue()=='2'){
    		  grid.options.parms.push({name:'req_hos_id',value:liger.get("req_hos_id").getValue().split(",")[0]});//请购单位
        	  grid.options.parms.push({name:'pur_hos_id',value:liger.get("pur_hos_id").getValue().split(",")[0]});//请购单位
    	  }
    	  grid.options.parms.push({name:'pur_code',value:$("#pur_code").val()});
    	  grid.options.parms.push({name:'is_dir',value:liger.get("is_dir").getValue()});//是否定向
    	  if(liger.get("is_dir").getValue()=='1'){//定向部门
    		  grid.options.parms.push({name:'dir_dept_id',value:liger.get("dir_dept_id").getValue().split(",")[0]});//请购单位
    	  }
    	  
    	//加载查询条件
    	grid.loadData(grid.where);
		$("#resultPrint > table > tbody").empty();
     }

    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
           columns: [ 
                     { display: '计划编号', name: 'pur_code', align: 'left',width:130,
                    	 render:  function(rowdata,rowindex,value){
	                    	 return '<a href=javascript:openUpdate("' 
								+ rowdata.pur_id+"|"+rowdata.state
								+ '")>'+rowdata.pur_code+'</a>';

                     	 }
					 },
					{ display: '编制部门', name: 'dept_name', align: 'left',width:100},
					{ display: '摘要', name: 'brif', align: 'left',width:100},
					{ display: '计划类型', name: 'plan_type', align: 'left',width:100},
		            { display: '采购单位', name: 'pur_hos_name', align: 'left',width:120},
					{ display: '请购单位', name: 'req_hos_name', align: 'left',width:120},
					{ display: '付款单位', name: 'pay_hos_name', align: 'left',width:120},
					{ display: '制单人', name: 'maker', align: 'left',width:100},
					{ display: '编制日期', name: 'make_date', align: 'left' ,width:100},
					{ display: '审核人', name: 'checker', align: 'left',width:100},
					{ display: '审核日期', name: 'check_date', align: 'left',width:100},
					{ display: '状态', name: 'state', align: 'center',width:100,render:
						function(rowdata){
							if(rowdata.state == '0'){
								return "已中止";
							}
							if(rowdata.state == '1'){
								 return "未审核";
							}
							if(rowdata.state == '2'){
								 return "已审核";
							}
							if(rowdata.state == '3'){
								return "已执行";
							}
						}
					},
					{ display: '定向计划', name: 'is_dir', align: 'left',width:100,
						render:function(rowdata){
							if(rowdata.is_dir == '1'){
								return "是";
							}
							if(rowdata.is_dir =='0'){
								return "否";
							}
						}
					},
					{ display: '定向科室', name: 'dir_dept_name', align: 'left',width:100 },
					{ display: '计划到货日期', name: 'arrive_date',width:100, align: 'left'},
                  ],
                  dataAction: 'server',dataType: 'server',usePager:true,url:'queryMedPurMainCheckMain.do?isCheck=false',
                  width: '100%', height: '100%', checkbox: true,rownumbers:true,delayLoad:true,isScroll:true,
                  selectRowButtonOnly:true,//heightDiff: -10,
                  toolbar: { items: [
                     	{ text: '查询', id:'search', click: query,icon:'search' },
                     	{ line:true },
        				{ text: '审核', id:'audit', click: audit, icon:'bluebook' },
        				{ line:true },
        				{ text: '取消审核', id:'unaudit', click: unaudit,icon:'bookpen' },
        				{ line:true }
        				//,{ text: '打印', id:'print', click: print,icon:'print' }
                     	]}
                   });

        gridManager = $("#maingrid").ligerGetGridManager();
    }
    
    //审核
    function audit(){
    	var data = gridManager.getCheckedRows();
        if (data.length == 0){
        	$.ligerDialog.error('请选择行');
        	return;
        }else{
        	 var Param =[];
        	 var flag;
             $(data).each(function (){
            	if(this.state != '1'){
            		flag = false;
            	}
					Param.push(
					//表的主键
					this.group_id+"@"+
					this.hos_id+"@"+
					this.copy_code+"@"+
					this.pur_id+"@"+
					this.state
					)
             });
             if(flag == false){
            	 //$.ligerDialog.error('只能审核状态为未审核的数据');
            	//return ;
             }
           $.ligerDialog.confirm('确定审核?', function (yes){
            	if(yes){
                	ajaxJsonObjectByUrl("checkMedPurMain.do?isCheck=false&paramVo="+Param,{},
                			function (responseData){
                		if(responseData.state=="true"){
                			query();
                		}
                	});
            	}
            });  
        }
    }
    //取消审核
    function unaudit(){
    	var data = gridManager.getCheckedRows();
        if (data.length == 0){
        	$.ligerDialog.error('请选择行');
        	return;
        }else{
        	 var Param =[];
        	 var flag;
             $(data).each(function (){
            	if(this.state != '2'){
            		flag = false;
            	}
					Param.push(
					//表的主键
					this.group_id+"@"+
					this.hos_id+"@"+
					this.copy_code+"@"+
					this.pur_id+"@"+
					this.state
					)
             });
             if(flag == false){
            	 $.ligerDialog.error('只能取消已审核的数据');
            	 return ;
             }
           $.ligerDialog.confirm('确定取消审核?', function (yes){
            	if(yes){
                	ajaxJsonObjectByUrl("cancelCheckMedPurMain.do?isCheck=false&paramVo="+Param,{},function (responseData){
                		if(responseData.state=="true"){
                			query();
                		}
                	});
            	}
            });  
        }
    }
   
    function downTemplate(){
    	
    	location.href = "downTemplate.do?isCheck=false";
    
    }
    
    //打开审核采购计划单页面
    function openUpdate(obj){
//     	var vo = obj.split("|");
// 		$.ligerDialog.open({ 
// 			url : 'medPurMainCheckDetailPage.do?isCheck=false&pur_id='+vo[0]+'&state='+vo[1],data:{}, height: 480,width: 1100,top:1,
// 			title:'审核采购计划单',modal:true,showToggle:false,showMax:true,showMin: false,isResize:true,
// 			buttons: [ 
// 				//{ text: '打印', onclick: function (item, dialog) { dialog.frame.saveMedPurMain(); },cls:'l-dialog-btn-highlight' }, 
// 				{ text: '关闭', onclick: function (item, dialog) { dialog.close(); } } 
// 			] });
		var vo = obj.split("|");
		var paras = 
			"pur_id="+vo[0] +"&"+ 
			"state="+vo[1] ;
		parent.$.ligerDialog.open({
			title: '审核采购计划单',
			height: $(window).height(),
			width: $(window).width(),
			url: 'hrp/med/purchase/check/medPurMainCheckDetailPage.do?isCheck=false&' + paras.toString(),
			modal: true, showToggle: false, showMax: true, showMin: false, isResize: true,
			parentframename: window.name,  //用于parent弹出层调用本页面的方法或变量
		});   
    }
   	//打印
   	function print(){
   		
   	}
    //键盘事件
	  function loadHotkeys() {

		hotkeys('Q', query);
		hotkeys('A', audit);
		hotkeys('U', unaudit);
		hotkeys('D', downTemplate);
		hotkeys('E', exportExcel);
		//hotkeys('P', print);
	 }
  //打印数据
	 function printDate(){
		//有数据直接打印
		if($("#resultPrint > table > tbody").html()!=""){
			lodopPrinterTable("resultPrint","开始打印","08113 物资仓库配套表",true);
			return;
		}
		
		//重新查询数据，避免分页导致打印数据不全
		var manager = $.ligerDialog.waitting('系统正在准备打印数据,请稍候...');

		var printPara={
			usePager:false,
           type_code:$("#type_code").val(),
           type_name:$("#type_name").val()
         };
		ajaxJsonObjectByUrl("queryMedStoreMedch.do?isCheck=false",printPara,function (responseData){
			$.each(responseData.Rows,function(idx,item){ 
				 var trHtml="<tr>";
					 trHtml+="<td>"+item.type_code+"</td>"; 
					 trHtml+="<td>"+item.type_name+"</td>"; 
				 trHtml+="</tr>";
				$("#resultPrint > table > tbody").empty();
				$("#resultPrint > table > tbody").append(trHtml);
			});
			manager.close();
			//alert($("#resultPrint").html())
			lodopPrinterTable("resultPrint","开始打印","08113 物资仓库配套表",true);
	    },true,manager);
		return;
	 }
	
	 
	 //导出数据
	 function exportExcel(){
		//有数据直接导出
		if($("#resultPrint > table > tbody").html()!=""){
			lodopExportExcel("resultPrint","导出Excel","08113 物资仓库配套表.xls",true);
			return;
		}
		
		//重新查询数据，避免分页导致打印数据不全
		var manager = $.ligerDialog.waitting('系统正在准备导出数据,请稍候...');

		var exportPara={
			usePager:false,
           type_code:$("#type_code").val(),
           type_name:$("#type_name").val()
         };
		ajaxJsonObjectByUrl("queryMedStoreMedch.do?isCheck=false",exportPara,function (responseData){
			$.each(responseData.Rows,function(idx,item){ 
				 var trHtml="<tr>";
					 trHtml+="<td>"+item.type_code+"</td>"; 
					 trHtml+="<td>"+item.type_name+"</td>"; 
				 trHtml+="</tr>";
				 $("#resultPrint > table > tbody").empty();
				 $("#resultPrint > table > tbody").append(trHtml);
			});
			manager.close();
			lodopExportExcel("resultPrint","导出Excel","08113 物资仓库配套表.xls",true);
	    },true,manager);
		return;
	 }
	 
	//加载字典下拉框
	 function loadDict(){
         
		 autocomplete("#planState", "../../queryMedPlanState.do?isCheck=false", "id", "text", true, true);//状态 下拉框		 
		 autocomplete("#dept_id", "../../queryPurDept.do?isCheck=false", "id", "text", true, true);//编制部门下拉框		 
		 autocomplete("#pur_type", "../../queryMedPlanType.do?isCheck=false", "id", "text", true, true);//计划类型下拉框		 
		 autodate("#begin_date", "yyyy-mm-dd", "month_first");
		 autodate("#end_date", "yyyy-mm-dd", "month_last");
		 autoCompleteByData("#is_dir", yes_or_no.Rows, "id", "text", true, true,'',false,false,'160');//是否定向
		 autocomplete("#pur_hos_id","../../queryMedHosInfoDict.do?isCheck=false","id","text",true,true,"",false,null,'160');//采购单位下拉框 
		 autocomplete("#req_hos_id","../../queryMedHosInfoDict.do?isCheck=false","id","text",true,true,"",false,null,'160');//请购单位下拉框 
		 autocomplete("#dir_dept_id","../../queryMedDeptDict.do?isCheck=false","id","text",true,true,"",false);//定向科室		
		 
	   	 $("#stateSelect").ligerTextBox({width:160});
	     $("#planState").ligerTextBox({width:160});
	     $("#store_id").ligerTextBox({width:160});
	     $("#brif").ligerTextBox({width:360});
	     $("#pur_code").ligerTextBox({width:160});
	     $("#dir_dept_id").ligerTextBox({width:160});
	     $("#begin_date").ligerTextBox({width:160});
	     $("#end_date").ligerTextBox({width:160});
		 
      }  
	
	 function change(){	//采购类型变化,是否定向改变，显示定向部门，显示采购单位、请购单位
		 var para = "";
		 if(liger.get("pur_type").getValue()=='2'){
			 $(".demo").attr("style","visibility:true");
			 para = 1;
		 }else if(liger.get("pur_type").getValue()=='1'){		
			 $(".demo").attr("style","visibility:hidden");
			 para = 0;
		 }else{
			 $(".demo").attr("style","visibility:hidden");
		 }
		 
		 autoCompleteByData("#is_dir", yes_or_no.Rows, "id", "text", true, true,"",false,para,'160');//是否定向
		 changeDir();
	 }
	//定向改变实现定向科室
	 function changeDir(){
		 if(liger.get("is_dir").getValue()=='1'){ 
			 $("#dir_dept_id").attr("type","hidden");
			 $(".dept").attr("style","display:table-cell");
			 //loadHead();
		 }else{
			 $(".dept").attr("style","display:none");
			 //loadHead();
		 }
	 }
    </script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

    <table cellpadding="0" cellspacing="0" class="l-table-edit" width="100%">
        <tr>
        	<td align="right" class="l-table-edit-td"  > 编制日期： </td>
			<td align="left" class="l-table-edit-td" width='100px;'>
				<input class="Wdate" name="begin_date" id="begin_date" type="text" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})"/>
			</td>
			<td align="right" class="l-table-edit-td"  width="10px;"> 至： </td>
			<td align="left" class="l-table-edit-td">
				<input class="Wdate" name="end_date" id="end_date" type="text" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})"/>
			</td>
			
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">编制部门：</td>
            <td align="left" class="l-table-edit-td"><input name="dept_id" type="text" id="dept_id" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">状态：</td>
            <td align="left" class="l-table-edit-td">
        		<input name="planState" type="text" id="planState" ltype="text" validate="{required:true,maxlength:20}" />
            </td>
            <td align="left"></td>
        </tr>
        
         <tr>
        	<td align="right" class="l-table-edit-td"  >摘要：</td>
			<td align="left" class="l-table-edit-td" colspan = '3'><input name="brif" id="brif" type="text"/></td>
			
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">计划类型：</td>
            <td align="left" class="l-table-edit-td">
                <input name="pur_type" type="text" id="pur_type" ltype="text" onChange="change();" validate="{required:true,maxlength:20}" />
             </td>
            <td align="left"></td>
            
            <td align="right" class="l-table-edit-td" style="padding-left: 10px;">单据号：</td>
			<td align="left" class="l-table-edit-td" >
				<input name="pur_code" type="text" requried="false" id="pur_code" />
			</td>
			<td align="left"></td>
        </tr>
        <tr>
        	<td align="right" class="l-table-edit-td"  style="padding-left:20px;">是否定向：</td>
            <td align="left" class="l-table-edit-td" colspan = '3'>
            	<input name="is_dir" type="text" id="is_dir" ltype="text" onChange="changeDir();"/>
            </td>
            
            <td align="right" class="l-table-edit-td demo"  style="padding-left:20px;">采购单位：</td>
			<td align="left" class="l-table-edit-td demo">
				<input name="pur_hos_id" type="text" id="pur_hos_id" ltype="text" />
			</td>
			<td align="left" class="l-table-edit-td demo"></td>
							
			<td align="right" class="l-table-edit-td demo"  style="padding-left:20px;">请购单位：</td>
			<td align="left" class="l-table-edit-td demo">
				<input name="req_hos_id" type="text" id="req_hos_id" ltype="text"  />
			</td>
			<td align="left" class="l-table-edit-td demo"></td>
        </tr> 
        <tr>
        	<td align="right" class="l-table-edit-td dept"  style="padding-left:20px;">定向科室：</td>
            <td align="left" class="l-table-edit-td dept" colspan = '3'>
            	<input name="dir_dept_id" type="text" id="dir_dept_id" ltype="text"  />
            </td>
        </tr>
    </table>

	<div id="maingrid"></div>
	<div id="resultPrint" style="display:none">
	   <!-- 	<table width="100%">
			<thead>
				<tr>
	                <th width="200"></th>	
	                <th width="200"></th>	
	                <th width="200"></th>	
	                <th width="200"></th>	
				</tr>
			 </thead>
			<tbody></tbody>
	   	</table> -->
   	</div>
</body>
</html>
