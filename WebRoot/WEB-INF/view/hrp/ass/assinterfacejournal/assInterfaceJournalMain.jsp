<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%String path = request.getContextPath();%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <jsp:include page="${path}/resource.jsp">
	    <jsp:param value="hr,tree,grid,select,dialog,validate,tab,datepicker,upload,checkbox" name="plugins" />
	</jsp:include>
	<script src="<%=path%>/lib/ligerUI/js/ligerui.all.js" type="text/javascript"></script>
    <style>
    .gray {
         -webkit-filter: grayscale(100%);  
         -moz-filter: grayscale(100%);  
         -ms-filter: grayscale(100%); 
         -o-filter: grayscale(100%);  
                 
filter: grayscale(100%);
 	      filter: gray;
 	       }
 	     
</style>
    <script>
        var grid;
        var PI_OperateDate,PI_isredo;
        var PI_sOperateTime,PI_MakeupDate;
        var PI_eOperateTime,PI_type;
        var query = function () {
            params = [
                { name: 'PI_type', value: $("#PI_type").val() },
                { name: 'PI_OperateResult', value: $("#PI_OperateResult").val() },
                { name: 'PI_OperateDate', value: $("#PI_OperateDate").val() },
                { name: 'PI_sOperateTime', value: $("#PI_sOperateTime").val() },
                { name: 'PI_eOperateTime', value: $("#PI_eOperateTime").val() },
                { name: 'PI_MakeupResult', value: $("#PI_MakeupResult").val() },
                { name: 'PI_IsMakeup', value: PI_isredo.status=='checked'?1:'' }
            ];
            //console.log(params);
            grid.loadData(params,'queryAssInterfaceJournal.do?isCheck=false');
        };
        
        var initSelect=  function(){
          //接口分类
        	PI_type=$("#PI_type").etSelect({
        		 url: 'queryPItype.do?isCheck=false',
          	   defaultValue: "none",
        	});
           var PI_OperateResult = $('#PI_OperateResult').etSelect({
               valueField: 'id',
               labelField: 'text',
               searchField: 'text',
               options: [
                   { id: "1", text: '成功' },
                   { id: "0", text: '失败' }
               ],
               create: false
           });
           
           
           var PI_MakeupResult = $('#PI_MakeupResult').etSelect({
               valueField: 'id',
               labelField: 'text',
               searchField: 'text',
               options: [
                   { id: "1", text: '成功' },
                   { id: "0", text: '失败' }
               ],
               create: false
           });
        }
        
        var initGrid = function () {
            var columns = [
                 { display: 'pi_logid', name: 'pi_logid', width: '80px',hidden:true},
            	 { display: '分类code', name: 'pi_typecode', width: '80px',hidden:true},
            	 { display: '分类', name: 'pi_typename', width: '80px'},
                 { display: '操作日期', name: 'pi_operatedate', width: '120px'},
                 { display: '操作时间', name: 'pi_operatetime',  width: '80px'},
                 { display: '操作结果', name: 'pi_operateresult',  width: '80px',
                	 render:function(ui){
                		// console.log(ui);
                		 var value=ui.cellData;
                		if(value){
                		 if(value=="1"){
                			 return "成功";
                		 }else{
                			 return "失败";
                		 }
                	 }else{return "失败";}
                	 }
                 },
                 { display: '是否补录', name: 'pi_ismakeup',  width: '80px',
                	render:function(ui){
                		 var value=ui.cellData;
                		 var index=ui.rowIndx;
                		if(value){
                			if(value=="1"){
                   			 return '<input type="checkbox" id="pi_ismakeup" checked="checked"  disabled="disabled">';
                   		 }else{
                   			 return '<input type="checkbox" id="pi_ismakeup"  disabled="disabled">';
                   		 }	
                		}else{
                			return '<input type="checkbox" id="pi_ismakeup"  disabled="disabled">';
                		}
                	}},
                 { display: '补录结果', name: 'pi_makeupresult',  width: '80px',
                	 render:function(ui){
                		var value=ui.cellData;
                		var pi_ismakeup=ui.rowData.pi_ismakeup;
                 		if(pi_ismakeup=="1"){
                 		 if(value=="1"){
                 			 return "成功";
                 		 }else{
                 			 return "失败";
                 		 }
                 	 }
                 	 }
                	}
            ];
            var paramObj = {
            	editable: false,
            	height: '87%',
            	width:'100%',
            	checkbox: true,
            	selectionModel:{
            		type: "cell", 
            		mode:"single"
            	},
                dataModel: {
                   //url: 'queryAssInterfaceJournal.do?isCheck=false'
                },
                toolbar: {
                    items: [
                        { type: 'button', label: '补录', listeners: [{ click: redo }], icon: 'add' }
                    ]
                },
                rowClick:  rowClick_f,
                rowSelect:  rowClick_f, 
                columns: columns
            };
            grid = $("#maingrid").etGrid(paramObj);
        };

       ///表格行点击时间，加在右边内容
       var rowClick_f=function(event,ui){
    	  // console.log(ui);
    	   $("#PI_Operater").val(ui.rowData.pi_operater);
    	   $("#PI_Makeuper").val(ui.rowData.pi_makeuper);
    	   $("#PI_MakeupDate").val(ui.rowData.pi_makeupdate);
    	   $("#PI_MakeupTime").val(ui.rowData.pi_makeuptime);
    	   $("#PI_MethodName").val(ui.rowData.pi_methodname);
    	   $("#PI_ClassDesc").val(ui.rowData.pi_classdesc);
    	   $("#PI_Record").val(ui.rowData.pi_record);
    	   $("#PI_RecordData").val(ui.rowData.pi_recorddata);
    	   $("#PI_ClassName").val(ui.rowData.pi_calssname);
    	  // $("#pi_operatetype").val(ui.rowData.pi_operatetype);
    	  if(ui.rowData.pi_operatetype=="A"){
    	   $("#add").attr("class", "add");
    	   $("#update").attr("class", "gray");
    	   $("#delete").attr("class", "gray");
    	  }else if(ui.rowData.pi_operatetype=="U"){
    	   $("#add").attr("class", "gray");
       	   $("#update").attr("class", "update");
       	   $("#delete").attr("class", "gray");  
    		  
    	  }else if(ui.rowData.pi_operatetype=="D"){
    		$("#add").attr("class", "gray");
          	$("#update").attr("class", "gray");
          	$("#delete").attr("class", "delete");    
    		  
    	  }else{
    		  $("#add").attr("class", "add");
              $("#update").attr("class", "update");
              $("#delete").attr("class", "delete");   
    	  }
       };
        //跳转保存页面
        var redo = function (data) {
        	var data=grid.selectGet();
        	
        	if (data.length != 1) {
                $.etDialog.error('请选择一条数据');
                return;
            }
        	
        	if(data[0].rowData.pi_operateresult=="1"){
        		
        		  $.etDialog.error('操作成功，无需补录');
        		  return;
        	}
        	//console.log(MapVo);
            ajaxPostData({
                url: 'addMakeUpInterface.do?isCheck=false',
                data:{
                	pi_logid:data[0].rowData.pi_logid,
                	pi_typecode:data[0].rowData.pi_typecode
                },
                success: function () {
                	query();

                }
            })
        };
    
   
        $(function () {
            initSelect();
            initfrom();
            initGrid();
        })
        
        //日期
        var initfrom = function(){
        
        	PI_isredo = $('#PI_isredo').etCheck();
          
        	PI_sOperateTime = $("#PI_sOperateTime").etDatepicker({
        	   dateFormat:'',
    		   autoClose: true , 
    		   //timeFormat: 'hh:ii',
        	   timepicker: true 
    		});
    	 
        	PI_eOperateTime = $("#PI_eOperateTime").etDatepicker({
        	   dateFormat:'',
    		   autoClose: true  ,
    		   timepicker: true 
   		});
   	   
        	PI_MakeupTime = $("#PI_MakeupTime").etDatepicker({
         	   dateFormat:'',
     		   autoClose: true  ,
     		   timepicker: true 
    		});
        	
           PI_OperateDate = $("#PI_OperateDate").etDatepicker({
    			//defaultDate: "yyyy-fM-fd"
    		});
           
           PI_MakeupDate=$("#PI_MakeupDate").etDatepicker({
   			//defaultDate: "yyyy-fM-fd"
   		});
	}
    </script>
</head>

<body >
	<div id="pageloading" class="l-loading" style="display: none"></div>
    <table class="table-layout">
        <tr>
            <td class="label" style="width: 100px;">接口分类：</td>
            <td  colspan="2" ><select id="PI_type" style="width: 268px"></select> </td>
            <td align="left"></td>
            <td class="label" style="width: 100px;">操作结果：</td>
            <td class="ipt">  <input id="PI_OperateResult" type="text" style="width: 180px"/> </td>
            <td align="left"></td>
            <td class="label" style="width: 100px;">操作日期：</td>
            <td class="ipt"> <input type="text" id="PI_OperateDate"> </td>
        </tr>
       <tr>
           <td class="label" style="width: 100px;">操作时间：</td>
            <td ><input type="text" id="PI_sOperateTime" style="width: 130px"/></td>
            <td  ><input type="text" id="PI_eOperateTime" style="width: 130px"/> </td>
            <td align="left"></td>
            <td class="label" style="width: 100px;">补录结果：</td>
            <td class="ipt">  <input id="PI_MakeupResult" type="text" style="width: 180px"/> </td>
            <td align="left"></td>
            <td class="label" style="width: 100px;"></td>
            <td class="ipt"> <input type="checkbox" id="PI_isredo" >是否补录 </td>
             <td><input type="button" value="查询" onclick="query();"/></td>
             
       </tr>
    </table>

   <div class="container" style="width: 100%;" > 
	     <div align="left" style="width: 50%" >
            <div id="maingrid"></div>
        </div>
        
      <div align="left" style="width: 50%;" id="con" >  
       <table  class="table-layout">
       
       <tr>
       <td class="label"  align="right" style="width: 100px;"></td>
       <td style="width: 180px;" ></td>
       <td class="label"  align="right" style="width: 100px;">操作动作：</td>
       <td><div ><img src="../../../dhc/images/pact/add.png" id="add" title="增加动作"></img>
       <img src="../../../dhc/images/pact/update.png" id="update" title="更新动作"></img>
       <img src="../../../dhc/images/pact/delete.png" id="delete" title="删除动作"></img>
       </div></td>
       </tr>
        
       <tr>
       <td class="label"  align="right" style="width: 100px;">操作人：</td>
       <td  ><input id="PI_Operater" type="text" style="width: 180px;"/></td>
       <td class="label"  align="right" style="width: 100px;">补录人：</td>
       <td><input id="PI_Makeuper" type="text" style="width: 180px;"/></td>
       </tr>
    
       <tr>
       <td class="label"  align="right"  style="width: 100px;">补录日期：</td>
       <td ><input id="PI_MakeupDate" type="text" style="width:180px"/></td>
       <td class="label"  align="right"  style="width: 100px;">补录时间：</td>
       <td ><input id="PI_MakeupTime" type="text" style="width:180px"/></td>
       </tr>
       
       <tr>
        <td class="label"   style="width: 100px;">方法名：</td>
       <td colspan="3"><textarea id="PI_MethodName"  style="width: 471px;"></textarea></td>      
       </tr>
       
         <tr>
        <td class="label"   style="width: 100px;">类名：</td>
       <td colspan="3"><textarea id="PI_ClassName"  style="width: 471px;"></textarea></td>      
       </tr>
       
       <tr>
        <td class="label"   style="width: 100px;">类描述：</td>
       <td colspan="3"><textarea id="PI_ClassDesc"  style="width: 471px;"></textarea></td>      
       </tr>
       
       <tr>
        <td class="label"   style="width: 100px;">操作记录主键：</td>
       <td colspan="3"><textarea id="PI_Record"  style="width: 471px;"></textarea></td>      
       </tr>
       
       <tr>
        <td class="label"   style="width: 100px;">操作数据：</td>
       <td colspan="3"><textarea id="PI_RecordData"  style="width: 471px;"></textarea></td>      
       </tr>
       
         </table>
        </div>
</div>
</body>

</html>

