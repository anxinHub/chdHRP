<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html style="overflow:hidden;">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link href="<%=path %>/lib/ligerUI/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css" />
<script src="<%=path %>/lib/jquery/jquery-1.3.2.min.js" type="text/javascript"></script>
<script src="<%=path %>/lib/ligerUI/js/ligerui.all.js" type="text/javascript"></script>
<script src="<%=path %>/lib/jquery-validation/jquery.validate.min.js" type="text/javascript"></script>
<script src="<%=path %>/lib/jquery-validation/jquery.metadata.js" type="text/javascript"></script>
<script src="<%=path %>/lib/jquery-validation/messages_cn.js" type="text/javascript"></script>
<script src="<%=path %>/lib/hrp.js" type="text/javascript"></script>
<script src="<%=path%>/lib/json2.js"></script>
<script type="text/javascript">
	
     var dataFormat;
     
     var i=0;
     
     var data;
     
     $(function (){
     	
        loadDict();//加载下拉框
        
        loadForm();
        
        loadHead(null);
        
     });  
     
     function  query(){
 		grid.options.parms=[];
 		grid.options.newPage=1;
     //根据表字段进行添加查询条件
     grid.options.parms.push({name:'wage_code',value:'${wage_code}'}); 

 	//加载查询条件
 	grid.loadData(grid.where);
  }
     
     function loadHead(){
     	grid = $("#maingrid").ligerGrid({
            columns: [ 
                      { display: '工资项目编码', name: 'ITEM_CODE', align: 'left'
 					 },
                      { display: '工资项目名称', name: 'ITEM_NAME', align: 'left'
 					 },
 					 { display: '金额', name: 'money', align: 'left',type: 'text',
                         editor: { type: 'text'}
 					 }
                      ],
                      dataAction: 'server',dataType: 'server',usePager:true,url:'queryAccWageItemPay.do?wage_code='+'${wage_code}&acc_year='+'${acc_year}',
                      width: '100%', height: '70%', checkbox: true,rownumbers:true,
                      selectRowButtonOnly:true, enabledEdit: true,
                      toolbar: { items: [
 						{ text: '查询', id:'search', click: query,icon:'search' },
 						{ line:true }
     				]}
                    });

         gridManager = $("#maingrid").ligerGetGridManager();
         
        
     }
     
     function  save(){
    	 
    	 var item="";
    	 
    	 var money="";
    	 
    	 var data = gridManager.getCheckedRows();
    	 
    	 if(data.leng==0){
    		 
    		 $.ligerDialog.error('请选择工资项');
    		 
    	 }else{
    		 
    		$(data).each(function (){					
 				item+=";"+this.COLUMN_ITEM;
 				money+=";"+this.money;
          });
    		 
    	 }
    	 
        var formPara={
        		
        	item:item,
        	
        	money:money,
        	
           wage_code:'${wage_code}',
        		
           acc_year:'${acc_year}',
            
           acc_month:'${acc_month}',
           
           dept_id:liger.get("dept_code").getValue().split(".")[0],
           
           dept_no:liger.get("dept_code").getValue().split(".")[1],
           
           kind_code:liger.get("kind_code").getValue() ,
           
           station_code:liger.get("station_code").getValue(),
           
           duty_code:liger.get("duty_code").getValue()
            
         };
        
        ajaxJsonObjectByUrl("addBatchAccWagePay.do",formPara,function(responseData){
            
            if(responseData.state=="true"){
				/*  $("input[name='para_code']").val('');
				 $("input[name='mod_code']").val('');
				 $("input[name='para_name']").val('');
				 $("input[name='para_value']").val('');
				 $("input[name='note']").val('');
				 $("input[name='is_stop']").val(''); */
                parent.query();
            }
        });
    }
     
 function loadForm(){
    
    $.metadata.setType("attr", "validate");
     var v = $("form").validate({
         errorPlacement: function (lable, element)
         {
             if (element.hasClass("l-textarea"))
             {
                 element.ligerTip({ content: lable.html(), target: element[0] }); 
             }
             else if (element.hasClass("l-text-field"))
             {
                 element.parent().ligerTip({ content: lable.html(), target: element[0] });
             }
             else
             {
                 lable.appendTo(element.parents("td:first").next("td"));
             }
         },
         success: function (lable)
         {
             lable.ligerHideTip();
             lable.remove();
         },
         submitHandler: function ()
         {
             $("form .l-text,.l-textarea").ligerHideTip();
         }
     });
     $("form").ligerForm();
 }       
   
    function saveAccPara(){
        
            save();
        
   }
    function loadDict(){
            //字典下拉框
    	 autocomplete("#kind_code","../../sys/queryEmpKindDict.do?isCheck=false&is_stop=0","id","text",true,true);
         
    	 autocomplete("#station_code","../../sys/queryStationDict.do?isCheck=false&is_stop=0","id","text",true,true);
          
    	 autocomplete("#duty_code","../../sys/queryDutyDict.do?isCheck=false&is_stop=0","id","text",true,true);
         
         autocomplete("#dept_code","../queryDeptDictNo.do?isCheck=false&is_stop=0","id","text",true,true);
         
     } 
    
    function detail(){
    	
    	var formPara={
        		
    	           emp_id:liger.get("emp_code").getValue()
    	            
    	         };
    	        
    	        ajaxJsonObjectByUrl("queryEmpByWageCode.do",formPara,function(responseData){
    	            
    	        	data = responseData;
    	        	
    	        	$.each(responseData,function(i,v){
    	        		
    	        		$("#wage_code").val(v.wage_name);
    	        		
    	        		$("#dept_code").val(v.dept_name);
    	        		
    	        		$("#kind_code").val(v.kind_name);
    	        		
    	        		$("#sex").val(v.sex);
    	        		
    	        		$("#pay_type_name").val(v.pay_type_name);
    	        		
    	        		$("#station_code").val(v.station_name);
    	        		
    	        		$("#duty_code").val(v.duty_name);
    	        		
    	        		$("#id_number").val(v.id_number);
    	        		
    	        	});
    	        	
    	        });
    }
    </script>
  
  </head>
  
   <body>
   <div id="pageloading" class="l-loading" style="display: none"></div>
   <div id="maingrid"></div>
   <form name="form1" method="post"  id="form1" >
        <table cellpadding="0" cellspacing="0" class="l-table-edit"  id="table_id">
            
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">部门：</td>
                <td align="left" class="l-table-edit-td"><input name="dept_code" type="text" id="dept_code" ltype="text" validate="{required:true,maxlength:20}"/></td>
                <td align="left"></td>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">职工分类：</td>
                <td align="left" class="l-table-edit-td"><input name="kind_code" type="text" id="kind_code" ltype="text" validate="{required:true,maxlength:20}"/></td>
                <td align="left"></td>
            </tr>
            
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">岗位：</td>
                <td align="left" class="l-table-edit-td"><input name="station_code" type="text" id="station_code" ltype="text" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">职务：</td>
                <td align="left" class="l-table-edit-td"><input name="duty_code" type="text" id="duty_code" ltype="text" validate="{required:true,maxlength:20}"/></td>
                <td align="left"></td>
            </tr>
        </table>
    </form>
   
    </body>
</html>
