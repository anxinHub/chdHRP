<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html style="overflow:hidden;">
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <jsp:include page="${path}/inc_jquery_1.9.0.jsp"/>
    <script type="text/javascript">
	 var gridManager = null;
	 var grid  ;
     var year ;
     var charge_stan_code ;
     
     $(function (){
         loadDict()//加载下拉框
         loadHead();
        loadForm();
        
        budg_year = liger.get("budg_year").getValue();
        
        $("body").keydown(function() {
            if (event.keyCode == "9") {//keyCode=9是Tab
             grid.addRowEdited({
            	 	dept_id: '' ,
                   budg_value:'',
                   remark:''
         		});
             }          
        });
        
        $("#charge_stan_code").change(function(){
        	  
        	charge_stan_code = liger.get("charge_stan_code").getValue();
        	  
        	loadHead();
       })
     });  
     function loadHead(){
	     grid = $("#maingrid").ligerGrid({ 
			     columns : [ 
							{display : '预算科室', name : 'dept_id', align : 'left', valueField:"id",textField : 'text',
		                             editor : {
											type : 'select',
											valueField : 'id',
											textField : 'text',
											url : '../../../queryBudgChargeStanDept.do?isCheck=false&charge_stan_code='+charge_stan_code,
											keySupport : true,
											autocomplete : true,
										} 
							
								},
							{ display :'预算值', name : 'budg_value', align : 'right',editor : {type : 'float'},
									render:function(rowdata,rowindex,value){
										if(value){
											return formatNumber(value,2,1);
										}
									}
							
								
								},
					        { display :'说明', name : 'remark', align : 'left',editor : {type : 'text'}									           
					         
					         },
								
							],
							dataAction: 'server',dataType: 'server',checkbox: true,usePager:true,
		                    width: '100%', height: '100%',isAddRow:false,rownumbers:true,enabledEdit:true,
		                    selectRowButtonOnly:true,//heightDiff: -10,
		                    toolbar: { 
		                    	items: [
                   						{ text: '保存', id:'add', click: save , icon:'add' },
                   	                	{ line:true },
                   	                	{ text: '删除', id:'delete', click: del ,icon:'delete' },
                   	               		{ line:true }
                   				]},
					});
	     		gridManager = $("#maingrid").ligerGetGridManager();
	 }       

     function  save(){
    	 var data = gridManager.getData();
    	 year = liger.get("budg_year").getValue();
    	 charge_stan_code = liger.get("charge_stan_code").getValue();
    	 if(!year){
    		 $.ligerDialog.error('预算年度不能为空');
    		 return false;
    	 }
    	 if(!year){
    		 $.ligerDialog.error('费用标准不能为空');
    		 return false;
    	 }
    	 if (data.length == 0){
         	$.ligerDialog.error('请添加行数据');
         }else{
        	 if(!validateGrid(data)){
        		 return false;
        	 }
	    	 var ParamVo =[];
	         $(data).each(function (){					
	        	 ParamVo.push(					
	        		year   +"@"+ 
					charge_stan_code  +"@"+
					this.dept_id  +"@"+
					this.text  +"@"+
					(this.budg_value?this.budg_value:"") +"@"+
					(this.remark?this.remark:'-1'))
	         });
	    	 
	        ajaxJsonObjectByUrl("addBudgDeptChargeStan.do?isCheck=false",{ParamVo : ParamVo.toString()},function(responseData){
	            
	            if(responseData.state=="true"){
	                parent.query();
	                grid.deleteAllRows();
	                
	            }
	        });
	        
        }
    }
     function del(){
    	 var data = grid.getCheckedRows();
   		 if(data.length == 0){
   				$.ligerDialog.error('请选择行');
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
 			if (v.dept_id == "" || v.dept_id == null || v.dept_id == 'undefined') {
 				rowm+="[预算科室]、";
 			} 
 			if(rowm != ""){
   				rowm = "第"+(i+1)+"行" + rowm.substring(0, rowm.length-1) + "不能为空" + "\n\r";
   			}
   			msg += rowm;
   			var key=v.dept_id 
   			var value="第"+(i+1)+"行";
   			if(targetMap.get(key)== null || targetMap.get(key) == 'undefined' || targetMap.get(key) == ""){
   				targetMap.put(key ,value);
   			}else{
   				msg += targetMap.get(key)+"与"+value+"数据重复!!" + "\n\r";
   			}
   		});
   		if(msg != ""){
   			$.ligerDialog.warn(msg);  
   			return false;  
   		}else{
   			return true;  
   		} 	
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
     //$("form").ligerForm();
 }       
   
    function saveBudgDeptChargeStan(){
        if($("form").valid()){
            save();
        }
   }
    function loadDict(){
            //字典下拉框
    	 //年度   下拉框
        autocomplete("#budg_year","../../../queryBudgYear.do?isCheck=false","id","text",true,true,'',true,'',180); 
            
        //费用编码   下拉框
        autocomplete("#charge_stan_code","../../../queryBudgChargeStan.do?isCheck=false&charge_stan_nature=02","id","text",true,true,'',true,'',180);
        
        $("#budg_year").ligerTextBox({width:180});
        $("#charge_stan_code").ligerTextBox({width:180});
     } 
    </script>
  
  </head>
  
   <body>
   <div id="pageloading" class="l-loading" style="display: none"></div>
   <form name="form1" method="post"  id="form1" >
        <table cellpadding="0" cellspacing="0" class="l-table-edit" >
        
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">年度：</td>
            <td align="left" class="l-table-edit-td"><input name="budg_year" type="text" id="budg_year" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>  
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">费用标准：</td>
            <td align="left" class="l-table-edit-td"><input name="charge_stan_code" type="text" id="charge_stan_code" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>             
        </tr> 
             
    </table>
    </form>
     <div id="toptoolbar" ></div>
   	<div id="maingrid"></div>
    </body>
</html>
