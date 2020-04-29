
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"><html style="overflow:hidden;">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="${path}/inc_jquery_1.9.0.jsp" />
<script src="<%=path%>/lib/hrp/mat/mat.js"	type="text/javascript"></script>
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
    	  grid.options.parms.push({name:'begin_date',value:$("#begin_date").val()});//日期范围：开始时间 
    	  grid.options.parms.push({name:'end_date',value:$("#end_date").val()});//日期范围：结束时间
    	  grid.options.parms.push({name:'dept_id',value:liger.get("dept_code").getValue().split(",")[0]});//编制部门
    	  grid.options.parms.push({name:'state',value:liger.get("planState").getValue()});//状态
    	  grid.options.parms.push({name:'brif',value:$("#brif").val()});//摘要
    	  //grid.options.parms.push({name:'pur_type',value:liger.get("pur_type").getValue()});//计划类型
       	  grid.options.parms.push({name:'pur_code',value:$("#pur_code").val()});//
       	  grid.options.parms.push({name:'is_dir',value:liger.get("is_dir").getValue()});//是否定向
  	  	  
  		  grid.options.parms.push({name:'req_hos_id',value:liger.get("req_hos_id").getValue().split(",")[0]});//请购单位
      	  grid.options.parms.push({name:'pur_hos_id',value:liger.get("pur_hos_id").getValue().split(",")[0]});//采购单位
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
                     { display: '汇总采购计划编号', name: 'pur_code', align: 'left',width : 150,render:
                    	 function(rowdata,rowindex,value){
	                    	 return '<a href=javascript:openUpdate("' 
								+ rowdata.pur_id
								+ '")>'+rowdata.pur_code+'</a>';

                     	 }
					 },
                     { display: '编制部门', name: 'dept_name', align: 'left',width : 150},
					 { display: '摘要', name: 'brif', align: 'left',width : 200},
					 { display: '计划类型', name: 'plan_type', align: 'left', width : 150},
                     { display: '分采购计划单', name: 'pur_id', align: 'center', width : 150,render:
                    	 function(rowdata,rowindex,value){
	                    	 return '<a>'+'查看'+'</a>';
                     	 }
					 },
					 { display: '采购单位', name: 'pur_hos_name', align: 'left',width : 150},
					 { display: '请购单位', name: 'req_hos_name', align: 'left',width : 150},
					 { display: '付款单位', name: 'pay_hos_name', align: 'left',width : 150},
					 { display: '制单人', name: 'maker', align: 'left' ,width : 150},
					 { display: '制单日期', name: 'make_date', align: 'left' ,width : 150},
					 { display: '审核人', name: 'checker', align: 'left',width : 150},
					 { display: '审核日期', name: 'check_date', align: 'left',width : 150},
					 { display: '状态', name: 'state', align: 'center',width : 150,render:
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
					 },{ display: '定向计划', name: 'is_dir', align: 'left',width:100,
						 render:function(rowdata){
							 if(rowdata.is_dir == 1){
							 	return "是";
							 }
							 if(rowdata.is_dir == 0){
								 return "否";
							 }
						
						 }
					 },
					 { display: '定向科室', name: 'dir_dept_name', align: 'left',width:100 },
                     ],
                     dataAction: 'server',dataType: 'server',usePager:true,
                     url:'queryMatPurMainCollect.do?isCheck=true',
                     width: '100%', height: '100%', checkbox: true,rownumbers:true,delayLoad:true,
                     selectRowButtonOnly:true,//heightDiff: -10,
                     isScroll : true,
                     toolbar: { items: [
                     	{ text: '查询', id:'search', click: query,icon:'search' },
                     	{ line:true },
                     	/* { text: '打印', id:'print', click: printDate,icon:'print' },
        				{ line:true }, */
        				{ text: '按采购计划汇总', id:'collect', click: collect, icon:'collect' }
                     	]}
                   });

        gridManager = $("#maingrid").ligerGetGridManager();
    }
    //汇总
    function collect(){
    	
    	var parm = 'pur_hos_id='+liger.get("pur_hos_id").getValue()
		+'&pur_hos_name='+liger.get("pur_hos_id").getText();
    	//alert(parm);
    	$.ligerDialog.open({
    		url: 'prodMatPurMainCollectPage.do?isCheck=false&'+parm,height: 500,top:1,width: 1100, 
    		title:'选择采购计划',modal:true,showToggle:false,showMax:true,showMin: false,isResize:true 
    	}); 
        
    }
    
    function openUpdate(obj){
		$.ligerDialog.open({ 
			url : 'matPurMainCollectDetailPage.do?isCheck=false&pur_id='+obj,data:{}, height: 480,width: 1100, top:1,
			title:'汇总生成采购计划明细',modal:true,showToggle:false,showMax:true,showMin: false,isResize:true,
			buttons: [ 
			           { text: '保存', onclick: function (item, dialog) { dialog.frame.saveMatPurMain(); },cls:'l-dialog-btn-highlight' }, 
			           { text: '取消', onclick: function (item, dialog) { dialog.close(); } } 
			         ] 
		});
    }
   
    //键盘事件
	  function loadHotkeys() {

		hotkeys('Q', query);
		hotkeys('A', add);
		hotkeys('O', collect);
		//hotkeys('B', downTemplate);
		//hotkeys('E', exportExcel);
		//hotkeys('P', printDate);

	 }
  
	 function loadDict(){
        //字典下拉框
		autocomplete("#planState", "../../queryMatPlanState.do?isCheck=false", "id", "text", true, true,"",false,null,'160');//状态 下拉框		 
		autocomplete("#dept_code", "../../queryPurDept.do?isCheck=false", "id", "text", true, true,"",false,null,'160');//编制部门下拉框		 
		autocomplete("#pur_type", "../../queryMatPlanType.do?isCheck=false", "id", "text", true, true);//计划类型下拉框		 
		autodate("#begin_date", "yyyy-mm-dd", "month_first");
		autodate("#end_date", "yyyy-mm-dd", "month_last");
		autoCompleteByData("#is_dir", yes_or_no.Rows, "id", "text", true, true,'',false,false,'160');//是否定向
		autocomplete("#pur_hos_id","../../queryMatHosInfoDict.do?isCheck=false","id","text",true,true,"",false,null,'160');//采购单位下拉框 
		autocomplete("#req_hos_id","../../queryMatHosInfoDict.do?isCheck=false","id","text",true,true,"",false,null,'160');//请购单位下拉框 
		autocomplete("#dir_dept_id","../../queryMatDeptDict.do?isCheck=false","id","text",true,true,"",false);//定向科室
		
		$("#dir_dept_id").ligerTextBox({width:160});
		$("#begin_date").ligerTextBox({width:160});
		$("#end_date").ligerTextBox({width:160});	
		$("#brif").ligerTextBox({width:360});
	    $("#pur_code").ligerTextBox({width:160});
		 
     }
	 
	 //定向改变实现定向科室
	 function changeDir(){
		 if(liger.get("is_dir").getValue()=='1'){  
			 $(".demo").attr("style","visibility:true");
		 }else{	
			 $(".demo").attr("style","visibility:hidden");
		 }
	 }
    </script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

    <table cellpadding="0" cellspacing="0" class="l-table-edit" width="100%" border="0">
        <tr>
        	<td align="right" class="l-table-edit-td"  > 编制日期： </td>
			<td align="left" class="l-table-edit-td" width='100px;'>
				<input class="Wdate" name="begin_date" id="begin_date" type="text" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})"/>
			</td>
			<td align="right" class="l-table-edit-td"  width="10px;"> 至： </td>
			<td align="left" class="l-table-edit-td">
				<input class="Wdate" name="end_date" id="end_date" type="text" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})"/>
			</td>

			<td align="right" class="l-table-edit-td" style="padding-left: 10px;">编制科室：</td>
			<td align="left" class="l-table-edit-td">
				<input name="dept_code" type="text" requried="false" id="dept_code" />
			</td>

			<td align="right" class="l-table-edit-td" style="padding-left: 10px;">状态：</td>
			<td align="left" class="l-table-edit-td" >
				<input name="planState" type="text" requried="false" id="planState" />
			</td>
        </tr>
        
         <tr>
        	<td align="right" class="l-table-edit-td" style="padding-left: 10px;">摘要：</td>
			<td align="left" class="l-table-edit-td" colspan="3">
				<input name="brif" type="text" requried="false" id="brif" style="width:360px;"/>
			</td>
			
             <td align="right" class="l-table-edit-td" style="padding-left: 10px;">单据号：</td>
			<td align="left" class="l-table-edit-td" >
				<input name="pur_code" type="text" requried="false" id="pur_code" />
			</td>
			
			<td align="right" class="l-table-edit-td"  style="padding-left:20px;">是否定向：</td>
            <td align="left" class="l-table-edit-td" colspan = '3'>
            	<input name="is_dir" type="text" id="is_dir" ltype="text" onChange="changeDir();"/>
            </td>
            
        </tr>
        <tr>
        	<!-- <td align="right" class="l-table-edit-td"  style="padding-left:20px;">计划类型：</td>
            <td align="left" class="l-table-edit-td">
                <input name="pur_type" type="text" id="pur_type" ltype="text" onChange="change();" validate="{required:true,maxlength:20}" />
             </td> -->
            
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">采购单位：</td>
			<td align="left" class="l-table-edit-td" colspan="3">
				<input name="pur_hos_id" type="text" id="pur_hos_id" ltype="text" />
			</td>
							
			<td align="right" class="l-table-edit-td"  style="padding-left:20px;">请购单位：</td>
			<td align="left" class="l-table-edit-td">
				<input name="req_hos_id" type="text" id="req_hos_id" ltype="text"  />
			</td>
			
			
			<td align="right" class="l-table-edit-td demo"  style="padding-left:20px;">定向科室：</td>
            <td align="left" class="l-table-edit-td demo" colspan = '3'>
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
