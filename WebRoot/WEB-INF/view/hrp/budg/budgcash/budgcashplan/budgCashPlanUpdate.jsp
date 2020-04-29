<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html style="overflow:hidden;">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="${path}/static_resource.jsp">
    <jsp:param value="select,datepicker,dialog,grid" name="plugins" />
</jsp:include>
<style>
	#remark{
		width:400px;
		height:30px;
		outline: none;
		resize: none;
		border: 1px solid #aecaf0;
	}

</style>
<script type="text/javascript">
    var grid;
    var gridManager = null;
    var userUpdateStr;
    var addData ;// 记录添加数据用变量
    var budg_year = "${budg_year}" ;
 	var plan_code = "${plan_code}";
    var cash_item_name;//现金流量项目名称
 	var event;
 	var remark ;
 	var state = '${state}';
  	
    $(function (){
      	//加载数据
      	loadHead(null);	
    });
    
    function loadHead(){
    	grid = $("#maingrid").etGrid({
           columns: [
        	   { 
	        	   display: '现金流量类别',
	               name: 'cash_type_name',
	               align: 'left',
	               width: 150,
	               editor: {
	                   type: 'select',
	                   keyField: 'cash_type_id',
	                   url: '../../queryCashType.do?isCheck=false&is_stop=0'
	               }
	           },
	           {
	               display: '现金流量项目',
	               name: 'cash_item_name',
	               align: 'left',
	               width: 200,
	               editor: {
	                   type: 'select',
	                   keyField: 'cash_item_id',
	                   url: 'queryCashItem.do?isCheck=false&cash_type_id=',
	                   create: function(rowData, cellData, setting) {
	                       if (rowData.cash_type_id) {
	                           var cash_type_id = rowData.cash_type_id;
	                           setting.url = 'queryCashItem.do?isCheck=false&cash_type_id=' + cash_type_id;
	                           // alert(11111)
	                       } else {
	                           $.etDialog.warn('请先填写现金流量类别');
	                           return false;
	                       }
	                   }
	               }
	           },
	           {
	               display: '合计(元)',
	               name: 'count',
	               align: 'right',
	               width: 120,
	               editable:false,
	               render: function(ui) {
	                   var sum = Number(ui.rowData.m01) + Number(ui.rowData.m02) + Number(ui.rowData.m03) + Number(ui.rowData.m04) +
	                       Number(ui.rowData.m05) + Number(ui.rowData.m06) + Number(ui.rowData.m07) + Number(ui.rowData.m08) +
	                       Number(ui.rowData.m09) + Number(ui.rowData.m10) + Number(ui.rowData.m11) + Number(ui.rowData.m12);
	                   return formatNumber(sum, 2, 1);
	                   
	               }
	           },
	           {
	               display: '01月(E)',
	               name: 'm01',
	               align: 'right',
	               width: 120,
	               editor: { type: 'float' },
	               render: function(ui) {
	                   var value = ui.cellData;
	                   return formatNumber(value, 2, 1);
	               }
	           },
	           {
	               display: '02月(E)',
	               name: 'm02',
	               align: 'right',
	               width: 120,
	               editor: { type: 'float' },
	               render: function(ui) {
	                   var value = ui.cellData;
	                   return formatNumber(value, 2, 1);
	               }
	           },
	           {
	               display: '03月(E)',
	               name: 'm03',
	               align: 'right',
	               width: 120,
	               editor: { type: 'float' },
	               render: function(ui) {
	                   var value = ui.cellData;
	                   return formatNumber(value, 2, 1);
	               }
	           },
	           {
	               display: '04月(E)',
	               name: 'm04',
	               align: 'right',
	               width: 120,
	               editor: { type: 'float' },
	               render: function(ui) {
	                   var value = ui.cellData;
	                   return formatNumber(value, 2, 1);
	               }
	           },
	           {
	               display: '05月(E)',
	               name: 'm05',
	               align: 'right',
	               width: 120,
	               editor: { type: 'float' },
	               render: function(ui) {
	                   var value = ui.cellData;
	                   return formatNumber(value, 2, 1);
	               }
	           },
	           {
	               display: '06月(E)',
	               name: 'm06',
	               align: 'right',
	               width: 120,
	               editor: { type: 'float' },
	               render: function(ui) {
	                   var value = ui.cellData;
	                   return formatNumber(value, 2, 1);
	               }
	           },
	           {
	               display: '07月(E)',
	               name: 'm07',
	               align: 'right',
	               width: 120,
	               editor: { type: 'float' },
	               render: function(ui) {
	                   var value = ui.cellData;
	                   return formatNumber(value, 2, 1);
	               }
	           },
	           {
	               display: '08月(E)',
	               name: 'm08',
	               align: 'right',
	               width: 120,
	               editor: { type: 'float' },
	               render: function(ui) {
	                   var value = ui.cellData;
	                   return formatNumber(value, 2, 1);
	               }
	           },
	           {
	               display: '09月(E)',
	               name: 'm09',
	               align: 'right',
	               width: 120,
	               editor: { type: 'float' },
	               render: function(ui) {
	                   var value = ui.cellData;
	                   return formatNumber(value, 2, 1);
	               }
	           },
	           {
	               display: '10月(E)',
	               name: 'm10',
	               align: 'right',
	               width: 120,
	               editor: { type: 'float' },
	               render: function(ui) {
	                   var value = ui.cellData;
	                   return formatNumber(value, 2, 1);
	               }
	           },
	           {
	               display: '11月(E)',
	               name: 'm11',
	               align: 'right',
	               width: 120,
	               editor: { type: 'float' },
	               render: function(ui) {
	                   var value = ui.cellData;
	                   return formatNumber(value, 2, 1);
	               }
	           },
	           {
	               display: '12月(E)',
	               name: 'm12',
	               align: 'right',
	               width: 120,
	               editor: { type: 'float' },
	               render: function(ui) {
	                   var value = ui.cellData;
	                   return formatNumber(value, 2, 1);
	               }
	           }
	       ],
	       dataModel: {
	           method: 'POST',
	           location: 'remote',
	           url: 'queryBudgCashPlanDet.do?isCheck=false&budg_year='+budg_year +'&plan_code='+plan_code,
	           recIndx: 'year'
	       },
	       usePager: true,
	       width: '100%',
	       height: '100%',
	       checkbox: true,
	       editable: true,
	       addRowByKey: true,
	       editorEnd: function (event, ui) {
	           // 判断当月份列编辑结束后刷新合计单元格
	           if (/^m\d/.test(ui.dataIndx)) {
	               grid.refreshCell(ui.rowIndx, 'count');
	           }
	       },
           toolbar: {
               items: [
                   { type: "button", label: '保存', icon: 'disk', listeners: [{ click: save }] },
                   { type: "button", label: '删除', icon: 'minus', listeners: [{ click: del }] },
                   { type: 'button', label: '审核', icon: 'audit', listeners: [{ click: review }]},
                   { type: "button", label: '销审', icon: 'unAudit', listeners: [{ click: cancel }] },
                   { type: 'button', label: '添加行', listeners: [{ click: addRow }], icon: 'add' }
               ]
           },
       });
    }
    
  	//添加一行
    function addRow() {
        grid.addRow();
    }
    
    function  save(){
    	if(state != '01'){
    		$.etDialog.warn('该数据不是新建状态，无法执行保存操作！');
    		return false;
    	}
    	 event = $("#event").val();
		 if(!event) {
			$.etDialog.warn('资金流动事项必填,不得为空,请填写后操作!');
			return false;
		 }   	
   		 var data = grid.getAllData();
       	 if (data == null){
            	$.etDialog.warn('请添加行数据');
            }else{
           	 if(!validateGrid(data)){
           		 return false;
           	 }
           	 budg_year = budg_year;
           	 plan_code = plan_code;
           	 remark = $("#remark").val();
   	    	 var ParamVo =[];
   	         $(data).each(function (){	
   	        	ParamVo.push( budg_year +"@"+ "01" +"@"+ this.cash_item_id +"@"+ event +"@"+ plan_code +"@"+ (remark? remark:"-1") +"@"+ (this.m01? this.m01:"-1") +"@"+ this.cash_item_name);
   	        	ParamVo.push( budg_year +"@"+ "02" +"@"+ this.cash_item_id +"@"+ event +"@"+ plan_code +"@"+ (remark? remark:"-1") +"@"+ (this.m02? this.m02:"-1") +"@"+ this.cash_item_name);
   	        	ParamVo.push( budg_year +"@"+ "03" +"@"+ this.cash_item_id +"@"+ event +"@"+ plan_code +"@"+ (remark? remark:"-1") +"@"+ (this.m03? this.m03:"-1") +"@"+ this.cash_item_name);
   	        	ParamVo.push( budg_year +"@"+ "04" +"@"+ this.cash_item_id +"@"+ event +"@"+ plan_code +"@"+ (remark? remark:"-1") +"@"+ (this.m04? this.m04:"-1") +"@"+ this.cash_item_name);
   	        	ParamVo.push( budg_year +"@"+ "05" +"@"+ this.cash_item_id +"@"+ event +"@"+ plan_code +"@"+ (remark? remark:"-1") +"@"+ (this.m05? this.m05:"-1") +"@"+ this.cash_item_name);
   	        	ParamVo.push( budg_year +"@"+ "06" +"@"+ this.cash_item_id +"@"+ event +"@"+ plan_code +"@"+ (remark? remark:"-1") +"@"+ (this.m06? this.m06:"-1") +"@"+ this.cash_item_name);
   	        	ParamVo.push( budg_year +"@"+ "07" +"@"+ this.cash_item_id +"@"+ event +"@"+ plan_code +"@"+ (remark? remark:"-1") +"@"+ (this.m07? this.m07:"-1") +"@"+ this.cash_item_name);
   	        	ParamVo.push( budg_year +"@"+ "08" +"@"+ this.cash_item_id +"@"+ event +"@"+ plan_code +"@"+ (remark? remark:"-1") +"@"+ (this.m08? this.m08:"-1") +"@"+ this.cash_item_name);
   	        	ParamVo.push( budg_year +"@"+ "09" +"@"+ this.cash_item_id +"@"+ event +"@"+ plan_code +"@"+ (remark? remark:"-1") +"@"+ (this.m09? this.m09:"-1") +"@"+ this.cash_item_name);
   	        	ParamVo.push( budg_year +"@"+ "10" +"@"+ this.cash_item_id +"@"+ event +"@"+ plan_code +"@"+ (remark? remark:"-1") +"@"+ (this.m10? this.m10:"-1") +"@"+ this.cash_item_name);
   	        	ParamVo.push( budg_year +"@"+ "11" +"@"+ this.cash_item_id +"@"+ event +"@"+ plan_code +"@"+ (remark? remark:"-1") +"@"+ (this.m11? this.m11:"-1") +"@"+ this.cash_item_name);
   	        	ParamVo.push( budg_year +"@"+ "12" +"@"+ this.cash_item_id +"@"+ event +"@"+ plan_code +"@"+ (remark? remark:"-1") +"@"+ (this.m12? this.m12:"-1") +"@"+ this.cash_item_name);
   	         });
	   	     ajaxPostData({
		           url: 'updateBudgCashPlan.do?isCheck=false',
		           data: {ParamVo : ParamVo.toString()},
		           success: function (res) {
		        	   parentQuery();
		           }
		     }) 
         }
      }
      function del(){
   	     if(state != '01'){
     		$.etDialog.warn('该数据不是新建状态，无法执行保存操作！');
     		return false;
      	 }
      	 var data = grid.selectGet();
 		 if(data.length == 0){
 			$.etDialog.warn('请选择要删除的行!');
            return;
         }else{
          	for (var i = 0; i < data.length; i++){
        		grid.remove(data[i]);
            } 
         }
      }
      
      function validateGrid(data) {  
      	  var msg="";
   		  var rowm = "";
   		  //判断grid 中的数据是否重复或者为空
   		  var targetMap = new HashMap();
   		  $.each(data,function(i, v){
   			  rowm = "";
   			  /* if (v.cash_type_name == "" || v.cash_type_name == null || v.cash_type_name == 'undefined') {
   				  rowm+="[现金流量类别]、";
   			  }  */
   			  if (v.cash_item_id == "" || v.cash_item_id == null || v.cash_item_id == 'undefined') {
   				  rowm+="[现金流量项目]、";
   			  }
   			 
   			  if(rowm != ""){
   				  rowm = "第"+(i+1)+"行" + rowm.substring(0, rowm.length-1) + "不能为空" + "\n\r";
   			  }
   			  msg += rowm;
   			  var key=v.cash_item_id 
   			  var value="第"+(i+1)+"行";
   			  if(targetMap.get(key)== null || targetMap.get(key) == 'undefined' || targetMap.get(key) == ""){
   				  targetMap.put(key ,value);
   			  }else{
   				  msg += targetMap.get(key)+"与"+value+"数据重复!!" + "\n\r";
   			  }
   		  });
   		  if(msg != ""){
   			  $.etDialog.warn(msg);  
   			  return false;  
   		  }else{
   			  return true;  
   		  } 	
      }
      //审核
      function review(){
      	
          var ParamVo = [];
		  ParamVo.push(
			 $("#budg_year").val()  +"@"+ 
			 $("#plan_code").val()
		  )
		  $.etDialog.confirm('确定审核?', function() {
             ajaxPostData({
                url: "reviewbudgCashPlan.do?isCheck=false",
                data: { ParamVo: ParamVo.toString() },
                success: function() {
                	state = '1';
                	parentQuery();
                }
             });
          });
      }
      
      //销审
      function cancel(){
      	
          var ParamVo =[];
		  ParamVo.push(
		     $("#budg_year").val()  +"@"+ 
			 $("#plan_code").val()
		  ) 
		  $.etDialog.confirm('确定销审?', function(yes) {
             ajaxPostData({
                url: "cancelbudgCashPlan.do?isCheck=false",
                data: { ParamVo: ParamVo.toString() },
                success: function(responseData) {
                	state = '0';
                	parentQuery();
                }
             });
          });
      }
	
      //父页面查询
      function parentQuery(){
      	var parentFrameName = parent.$.etDialog.parentFrameName;
      	var parentWindow = parent.window[parentFrameName];
      	parentWindow.query();
      }
 	  
    </script>
</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>
	<div id="toptoolbar" ></div>
	<div class="main">
        <table class="table-layout">
            <tr>
                <td class="label">资金计划单号：</td>
                <td class="ipt">
                    <input type="text" id="plan_code" disabled value="${plan_code}" />
                </td>
                <td class="label no-empty">预算年度：</td>
                <td class="ipt">
                    <input type="text" id="budg_year" disabled value="${budg_year}"/>
                </td>
                <td class="label no-empty">资金流动事项：</td>
                <td class="ipt">
                    <input type="text" id="event" value="${event}" style="width:240px;" />
                </td>
            </tr>
            <tr>
                <td class="label">说明：</td>
                <td class="ipt" colspan="3">
                    <input type="text" id="remark" value="${remark}" style="width:480px;" />
                </td>
            </tr>
        </table>
    </div>
	
	<div id="maingrid"></div>
	
</body>
</html>
