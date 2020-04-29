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
    <script src="<%=path%>/lib/hrp/budg/budg.js"	type="text/javascript"></script>
    <script type="text/javascript">
     var dataFormat;
     
     var is_single_count ;
     var resolve_or_sum ;
     var resolve_level ;
     var sum_level ;
     var dataStack ;
     var flag = 0 ;
     var num = 0 ;
     $(function (){
    	
        loadDict()//加载下拉框
        loadForm();
        
        // 是否独立核算，若是则编辑编制方法、取值方法、计算公式、取值函数，否则只能编辑 “分解&汇总”部分 
		// 独立核算  分解或汇总部分不可编辑 (清空分解或汇总部分的下拉框 )
        $("#is_single_count").change(function(){
        	if(liger.get("is_single_count").getValue() == 1){
           		 
				$("#edit_method").ligerTextBox({disabled:false,cancelable:true});
        		
        		$("#edit_method").removeClass("notValid");
        		
            	$("#resolve_or_sum").ligerTextBox({disabled:true,cancelable:false});
        		
        		$("#resolve_or_sum").addClass("notValid") ;
        		
        		$("#resolve_way").addClass("notValid");
        		
        		$("#resolve_level").addClass("notValid");
        		
        		$("#sum_level").addClass("notValid");
        		
        		 liger.get("edit_method").setValue("");
        		 liger.get("get_way").setValue("");
            	 liger.get("resolve_or_sum").setValue("");
           		 liger.get("resolve_way").setValue("");
           		 liger.get("resolve_level").setValue("");
           		 liger.get("sum_level").setValue("");
            }
            // 非独立核算 编制方法、取值方法、计算公式、取值函数 不可编辑。(清空编制方法、取值方法、计算公式、取值函数 下拉框 , 内置 分解或汇总、分解层次、汇总层次)
            if(liger.get("is_single_count").getValue() == 0 ){
           		
				$("#edit_method").ligerTextBox({disabled:true,cancelable:false});
            	
            	$("#edit_method").addClass("notValid");
            	
            	$("#resolve_or_sum").ligerTextBox({disabled:false,cancelable:true});
            	
            	$("#resolve_or_sum").removeClass("notValid");
            	
            	$("#get_way").addClass("notValid");
            	
            	$("#formula_id").addClass("notValid");
            	
            	$("#fun_id").addClass("notValid");
            	
    			liger.get("edit_method").setValue("");
    			liger.get("get_way").setValue("");
    			liger.get("formula_id").setValue("");
    			liger.get("fun_id").setValue("");
    			
    			liger.get("resolve_or_sum").setValue("");
           		liger.get("resolve_level").setValue("");
           		liger.get("sum_level").setValue("");
            }
        })
         
  		$("#edit_method").change(function(){
        	 
        	 var edit_method = liger.get("edit_method").getValue();
        	 
			if(liger.get("is_single_count").getValue() == 1){
        		 
        		 $("#edit_method").removeClass("notValid");
        		 
        		 liger.get("get_way").setValue("");
        		 
        		 $("#resolve_or_sum").ligerTextBox({disabled:true,cancelable:false});
        		 
        		 $("#resolve_or_sum").addClass("notValid");
        		 
        		 $("#resolve_way").ligerTextBox({disabled:true,cancelable: false});
        		 
        		 $("#resolve_way").addClass("notValid");
        		 
           		 $("#resolve_level").ligerTextBox({disabled:true,cancelable: false});
           		 
           		 $("#resolve_level").addClass("notValid");
           		 
           		 $("#sum_level").ligerTextBox({disabled:true,cancelable: false});
           		 
           		 $("#sum_level").addClass("notValid");
           		
           		 liger.get("resolve_way").setValue("");
           		 liger.get("resolve_level").setValue("");
           		 liger.get("sum_level").setValue("");
           		 
           		 if(edit_method){
           		 	
 					$("#get_way").ligerTextBox({width:180,disabled:false,cancelable:true});
            		 
            		 if( edit_method == '01' || edit_method == '02' || edit_method == '03' ){
            			 
            			 $("#get_way").removeClass("notValid");
            			 
            		 }else{
            			 
            			 $("#get_way").addClass("notValid");
            		 }
	           		 
	           	 }else{
	   					$("#get_way").ligerTextBox({width:180,disabled:true});
	   					  		 
	   					$("#get_way").addClass("notValid");
	   					
	           	 }
	           	 
	           	 //falg = 0  第一次打开页面加载数据
	    			if( flag == 0){
	    				autocomplete("#get_way","../../../../queryBudgGetWay.do?isCheck=false&edit_method="+"${edit_method}","id","text",true,true,'',false,'${get_way}',180);
	    			}else{
	    				liger.get("get_way").setValue("")
	    				 //与编制方法下拉框联动查询   取值方法下拉框 
	    	        	 autocomplete("#get_way","../../../../queryBudgGetWay.do?isCheck=false&edit_method="+edit_method,"id","text",true,true,'',false,'',180);
	    			}
	    			flag  += 1 ;
				}	 
         });
  		
         $("#get_way").change(function(){
          	
			if(liger.get("get_way").getValue() !='02' && liger.get("get_way").getValue() !='03' ){
          		
          		$("#selectFun").ligerButton({click: selectFun, width:90,disabled:true});
          		
          		$("#fun_id").addClass("notValid");
          		
          		$("#selectFormula").ligerButton({click: selectFormula, width:90,disabled:true});
          		
          		$("#formula_id").addClass("notValid");
          		
          		liger.get("fun_id").setValue("");
          		
          		liger.get("formula_id").setValue("");
          	}
          	
          	if(liger.get("get_way").getValue()=='02'){
          		
          		$("#selectFun").ligerButton({click: selectFun, width:90,disabled:false});
          		
          		$("#fun_id").removeClass("notValid");
          		 
          		$("#selectFormula").ligerButton({click: selectFormula, width:90,disabled:true});
          		
          		$("#formula_id").addClass("notValid");
          		
          		liger.get("formula_id").setValue("");
          	}
          	if(liger.get("get_way").getValue()=='03'){
          		
          		$("#selectFormula").ligerButton({click: selectFormula, width:90,disabled:false});
          		
          		$("#formula_id").removeClass("notValid");
          		
          		$("#selectFun").ligerButton({click: selectFun, width:90,disabled:true});
          		
          		$("#fun_id").addClass("notValid");
          		
          		liger.get("fun_id").setValue("");
          	}
         })
         
         $("#resolve_or_sum").change(function(){
        	 if(liger.get("is_single_count").getValue() == 0){
        		 
	    		 $("#resolve_or_sum").removeClass("notValid");
	    		 
				 $("#edit_method").ligerTextBox({width:180,disabled:true,cancelable:false});
				 
				 $("#edit_method").addClass("notValid");
				 
				 $("#get_way").ligerTextBox({width:180,disabled:true,cancelable:false});
				 
				 $("#get_way").addClass("notValid");
				 
				 $("#selectFormula").ligerButton({click: selectFormula, width:90,disabled:true});
				 
				 $("#formula_id").addClass("notValid");
				 
				 $("#selectFun").ligerButton({click: selectFun, width:90,disabled:true});
				 
				 $("#fun_id").addClass("notValid");
				 
	    		 liger.get("edit_method").setValue("");
	    		 liger.get("get_way").setValue("");
	    		 liger.get("formula_id").setValue("");
	    		 liger.get("fun_id").setValue("");
	    		 
	    		 if(liger.get("resolve_or_sum").getValue() =='01'){
	        		 $("#resolve_way").ligerTextBox({disabled:false,cancelable: true});
	        		 
	        		 $("#resolve_way").removeClass("notValid");
	        		 
	           		 $("#resolve_level").ligerTextBox({disabled:false,cancelable: true});
	           		 
	           		 $("#resolve_level").removeClass("notValid");
	           		 
	           		 $("#sum_level").ligerTextBox({disabled:true,cancelable: false});
	           		 
	           		 $("#sum_level").addClass("notValid");
	           		 
	           		 if(num > 0 ){
	           			 liger.get("resolve_way").setValue('');
		          		 liger.get("resolve_level").setValue('');
		           		 liger.get("sum_level").setValue("");
	           		 }
	           		
	        	 }else if (liger.get("resolve_or_sum").getValue() =='02'){
	        		 
	        		 $("#resolve_way").ligerTextBox({disabled:true,cancelable: false});
	        		 
	        		 $("#resolve_way").addClass("notValid");
	        		 
	           		 $("#resolve_level").ligerTextBox({disabled:true,cancelable: false});
	           		 
	           		 $("#resolve_level").addClass("notValid");
	           		 
	           		 $("#sum_level").ligerTextBox({disabled:false,cancelable: true});
	           		 
	           		 $("#sum_level").removeClass("notValid");
	           		 
	           		 liger.get("resolve_way").setValue("");
	          		 liger.get("resolve_level").setValue("");
	           		 liger.get("sum_level").setValue(sum_level);
	        	 }else{
	        		 
	        		 $("#resolve_way").ligerTextBox({disabled:true,cancelable: false});
	        		 
	        		 $("#resolve_way").addClass("notValid");
	        		 
	           		 $("#resolve_level").ligerTextBox({disabled:true,cancelable: false});
	           		 
	           		 $("#resolve_level").addClass("notValid");
	           		 
	 				 $("#sum_level").ligerTextBox({disabled:true,cancelable: true});
	           		 
	           		 $("#sum_level").addClass("notValid");
	           		 
	           		 liger.get("resolve_way").setValue("");
	         		 liger.get("resolve_level").setValue("");
	          		 liger.get("sum_level").setValue("");
	        		 
	        	 }
	    		 
	    	 }else{
	    		 
	    		 $("#edit_method").ligerTextBox({width:180,disabled:false,cancelable:true});
	    		 
	    		 $("#edit_method").removeClass("notValid");
	    		 
	    		 $("#resolve_or_sum").ligerTextBox({disabled:true,cancelable: false});
	    		 
	    		 $("#resolve_or_sum").addClass("notValid");
	    		 
	    		 $("#resolve_way").ligerTextBox({disabled:true,cancelable: false});
	    		 
	    		 $("#resolve_way").addClass("notValid");
	    		 
	       		 $("#resolve_level").ligerTextBox({disabled:true,cancelable: false});
	       		 
	       		 $("#resolve_level").addClass("notValid");
	       		 
	       		 $("#sum_level").ligerTextBox({disabled:true,cancelable: false});
	       		 
	       		 $("#sum_level").addClass("notValid");
	       		 
	       		 liger.get("resolve_way").setValue("");
	       		 liger.get("resolve_level").setValue("");
	       		 liger.get("sum_level").setValue("");
	       		 
	    	 }
	    	 
	     })
	     
	     $("#resolve_way").change(function(){
        	
	    	if(num > 0 ){
	    		$("#reference_years").val("") ;
	    		liger.get("resolve_data").setValue("");
	    	}
        	
        	if(liger.get("resolve_way").getValue() =='01'){
        		
				$("#reference_years").ligerTextBox({disabled:false,cancelable:true});
            	
            	$("#reference_years").removeClass("notValid");
            	
 				$("#selectResolve").ligerButton({click: selectResolve, width:90,disabled:true});
				 
				$("#resolve_data").addClass("notValid");
        		
        	}else if(liger.get("resolve_way").getValue() =='05'){
        		
        		$("#selectResolve").ligerButton({click: selectResolve, width:90,disabled:false});
				 
				$("#resolve_data").removeClass("notValid");
				
				$("#reference_years").ligerTextBox({disabled:true,cancelable:false});
            	
            	$("#reference_years").addClass("notValid");
        		
        	}else{
        		
        		$("#selectResolve").ligerButton({click: selectResolve, width:90,disabled:true});
				 
				$("#resolve_data").addClass("notValid");
				
				$("#reference_years").ligerTextBox({disabled:true,cancelable:false});
            	
            	$("#reference_years").addClass("notValid");
        	}
       		num = num + 1 ;
        })
        
     });  
     
     function  save(){
        var formPara={
            
        	budg_level : "01" ,
        	budg_year : $("#budg_year").val(),
			 
			index_code : liger.get("index_code").getValue(),
			 
			is_single_count:liger.get("is_single_count").getValue(),
			
			edit_method : liger.get("edit_method").getValue(),
			get_way : liger.get("get_way").getValue(),
			formula_id : liger.get("formula_id").getValue(),
			fun_id : liger.get("fun_id").getValue(),
			resolve_or_sum : liger.get("resolve_or_sum").getValue(),
			resolve_way : liger.get("resolve_way").getValue(),
			resolve_level : liger.get("resolve_level").getValue(),
			reference_years : $("#reference_years").val(),
			resolve_data : liger.get("resolve_data").getValue() ,
			sum_level : liger.get("sum_level").getValue() ,
			
			dataStack : dataStack
		};
        ajaxJsonObjectByUrl("updateBudgHosYearPlanUp.do?isCheck=fasle",formPara,function(responseData){
            
            if(responseData.state=="true"){
				
            	parentFrameUse().query();
                
            	parentFrameUse().$.ligerDialog.success('修改成功');
                
                frameElement.dialog.close();
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
 		           //提示信息  显示2秒后消失
 		           setTimeout(function(){
 		          	  lable.ligerHideTip();
 		                lable.remove();
 		           },2000)
 		       },
 		       success: function (lable)
 		       {
 		           lable.ligerHideTip();
 		           lable.remove();
 		       },
 		       submitHandler: function ()
 		       {
 		           $("form .l-text,.l-textarea").ligerHideTip();
 		       },
 		       ignore: ".notValid"
 		   });
 		  
 		  //$("form").ligerForm();
 	}       
	
     //选择计算公式页面
    function selectFormula(){
    		
    		var index_code = liger.get("index_code").getValue();
     	 
    		if(!index_code){
   			$.ligerDialog.error("指标编码不能为空");
   			return false ;
   		}
     	 
    		parent.$.ligerDialog.open({
   			url : 'hrp/budg/common/budgformula/budgFormulaBusinessPage.do?isCheck=false',height : 500,width : 750,title : '计算公式选择',
   			modal : true,showToggle : false,showMax : true,showMin :false ,	isResize : true,
   			parentframename:window.name,  //用于parent弹出层调用本页面的方法或变量
   		});
    		
    }
      
    	// 选择函数页面
   	function selectFun(){
   		var budg_year = liger.get("budg_year").getValue();
   		
  		var index_code = liger.get("index_code").getValue();
  		
   		var index_type_code = "03" // 指标类型 01 基本指标 02 费用指标 03 预算指标 
   		
 		if(!budg_year){
   			$.ligerDialog.error("预算年度不能为空");
   			return false ;
   		}
   		
   		if(!index_code){
   			$.ligerDialog.error("指标编码不能为空");
   			return false ;
   		}
   		parent.$.ligerDialog.open({
   			url : 'hrp/budg/common/budgfun/budgFunBusinessPage.do?isCheck=false&budg_year='+budg_year+'&budg_level='+'01'+'&index_code='+index_code+'&index_type_code='+index_type_code,height : 500,width : 750,title : '取值函数选择',
   			modal : true,showToggle : false,showMax : true,showMin :false ,	isResize : true,
   			parentframename:window.name,  //用于parent弹出层调用本页面的方法或变量
   			buttons: [ { text: '确定', onclick: function (item, dialog) { dialog.frame.saveSelectFun(); },cls:'l-dialog-btn-highlight' },
  			           { text: '取消', onclick: function (item, dialog) { dialog.close(); } } 
  			          ]
   		});
    }
    	
  //选择自定义分解系数页面 
   	function selectResolve(){
   		
   		parent.$.ligerDialog.open({
  			url : 'hrp/budg/common/budgresolvedatadict/budgResolveDataPage.do?isCheck=false&budg_level=01',
  				height : 500,width : 750,title : '自定义分解系数',
  			modal : true,showToggle : false,showMax : true,showMin :false ,	isResize : true,
  			parentframename:window.name,  //用于parent弹出层调用本页面的方法或变量
  		});
   		
   	}
    function saveBudgHosYearPlan(){
    	if($("form").valid()){
    		save();
    	}
	}
    
    function loadDict(){
    	//字典下拉框
    	
        
        //预算指标下拉框
        autocomplete("#index_code","../../../../queryBudgIndexDict.do?isCheck=false","id","text",true,true,'',false,'${index_code}',180);
        
        //编制方法 下拉框
        autocomplete("#edit_method","../../../../queryBudgEditMethod.do?isCheck=false","id","text",true,true,'',false,'${edit_method}',180);
        
        
        //初始化 取值方法下拉框 （编制方法下拉框 与 取值方法下拉框 联动）
	   	//$("#get_way").ligerComboBox({width:180});
        autocomplete("#get_way","../../../../queryBudgGetWay.do?isCheck=false&edit_method="+"${edit_method}","id","text",true,true,'',false,'${get_way}',180);
        
        //计算公式 下拉框
        autocomplete("#formula_id","../../../../queryBudgFormula.do?isCheck=false","id","text",true,true,'',false,'${formula_id}',180);
        
        //取值函数 下拉框
        autocomplete("#fun_id","../../../../queryBudgFun.do?isCheck=false","id","text",true,true,'',false,'${fun_id}',180);
        
     	//分解或汇总 下拉框
	   	autocomplete("#resolve_or_sum","../../../../queryBudgResolveOrSum.do?isCheck=false","id","text",true,true,'',false,'${resolve_or_sum}',180);
     	
	    //分解方法 下拉框
        autocomplete("#resolve_way","../../../../queryBudgResolveWay.do?isCheck=false","id","text",true,true,'',false,'${resolve_way}',180);
     	
      //自定义分解系数 下拉框
        autocomplete("#resolve_data","../../../../queryBudgResolveDataDict.do?isCheck=false&budg_level=01","id","text",true,true,'',false,'${resolve_data}',180);
      
	 	//分解层次 下拉框
	   	autocomplete("#resolve_level","../../../../queryBudgLevel.do?isCheck=false","id","text",true,true,'',false,'${resolve_level}',180);
	 	
	  /* //参考年限
	   	autocomplete("#reference_years","../../../../queryBudgYear.do?isCheck=false","id","text",true,true,'',false,'${reference_years}',180); */
	 	
	  	//汇总层次 下拉框
	   	autocomplete("#sum_level","../../../../queryBudgLevel.do?isCheck=false","id","text",true,true,'',false,'${sum_level}',180);
        
        autoCompleteByData("#is_single_count", yes_or_no.Rows, "id", "text", true, true,'',false);
        
        liger.get("is_single_count").setValue("${is_single_count}");
        
        if(${reference_years}){
        	$("#reference_years").val("${reference_years}")
        }
        
        
        $("#budg_year").ligerTextBox({ width:180,disabled:true,cancelable:false});
    	$("#index_code").ligerTextBox({ width:180,disabled:true,cancelable:false});
    	$("#is_single_count").ligerTextBox({ width:180});
    	$("#reference_years").ligerTextBox({ width:180});
    	
    	$("#formula_id").ligerTextBox({width:180,disabled:true,cancelable:false});
    	
    	$("#fun_id").ligerTextBox({width:180,disabled:true,cancelable:false});
    	
    	$("#resolve_data").ligerTextBox({width:180,disabled:true,cancelable:false});
    	
		if("${is_single_count}" == 1){
    		
    		$("#resolve_or_sum").ligerTextBox({width:180,disabled:true,cancelable:false});
    		
    		$("#resolve_or_sum").addClass("notValid");
    		
    		$("#resolve_way").ligerTextBox({width:180,disabled:true,cancelable:false});
    		
    		$("#resolve_way").addClass("notValid");
    		
        	$("#sum_level").ligerTextBox({width:180,disabled:true,cancelable:false});
        	
        	$("#sum_level").addClass("notValid");
        	
        	$("#resolve_level").ligerTextBox({width:180,disabled:true,cancelable:false});
        	
        	$("#resolve_level").addClass("notValid");
        	
    	}else{
    		
    		$("#edit_method").ligerTextBox({width:180,disabled:true,cancelable:false});
    		
    		$("#edit_method").addClass("notValid");
    		
        	$("#get_way").ligerTextBox({width:180,disabled:true,cancelable:false});
        	
        	$("#get_way").addClass("notValid");
        	
    	   	
    	   	$("#formula_id").addClass("notValid");
    	   	
        	
        	$("#fun_id").addClass("notValid");
    	}
    	
    	$("#is_single_count").ligerTextBox({width:180});
    	$("#budg_year").val("${budg_year}") ;
    	
		$("#selectFormula").ligerButton({click: selectFormula, width:90,disabled:true});
    	
		$("#selectFun").ligerButton({click: selectFun, width:90,disabled:true});
		
		$("#selectResolve").ligerButton({click: selectResolve, width:90,disabled:true});
     } 
    </script>
  
  </head>
  
   <body>
   <div id="pageloading" class="l-loading" style="display: none"></div>
   <form name="form1" method="post"  id="form1" >
        <table cellpadding="0" cellspacing="0" class="l-table-edit" >
		
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b>预算年度<font color="red" >*</font>:</b></td>
            <td align="left" class="l-table-edit-td"><input  name="budg_year" type="text" id="budg_year"  ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b>指标编码<font color="red">*</font>:</b></td>
            <td align="left" class="l-table-edit-td"><input name="index_code" type="text" id="index_code" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
        </tr> 
         <tr>
         	<td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b>是否独立核算<font color="red">*</font>:</b></td>
            <td align="left" class="l-table-edit-td">
            	<input name="is_single_count" type="text" id="is_single_count" ltype="text" validate="{required:true,maxlength:20}" />
            </td>
            <td align="left"></td> 
        </tr>  
         <tr> 
             <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b>编制方法:</b></td> 
            <td align="left" class="l-table-edit-td"><input name="edit_method" type="text" id="edit_method" ltype="text" validate="{required:true,maxlength:20}" /></td> 
            <td align="left"></td> 
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b>取值方法:</b></td> 
            <td align="left" class="l-table-edit-td"><input name="get_way" type="text" id="get_way" ltype="text" validate="{required:true,maxlength:20}" /></td> 
            <td align="left"></td> 
        </tr>  
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b>计算公式:</b></td> 
            <td align="left" class="l-table-edit-td"><input name="formula_id" type="text" id="formula_id"  ltype="text" validate="{required:true,maxlength:200}" /></td> 
            <td align="left"></td> 
            <td align="center" class="l-table-edit-td" colspan="3">
					<button id ="selectFormula"><b>选择公式</b></button>
			</td>
		</tr>  
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b>取值函数:</b></td> 
            <td align="left" class="l-table-edit-td"><input name="fun_id" type="text" id="fun_id"  ltype="text" validate="{required:true,maxlength:200}" /></td>
            <td align="left"></td>
            <td align="center" class="l-table-edit-td" colspan="3">
					<button id ="selectFun" ><b>选择函数</b></button>
			</td> 
        </tr>  
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b>分解或汇总:</b></td> 
            <td align="left" class="l-table-edit-td"><input name="resolve_or_sum" type="text" id="resolve_or_sum" ltype="text" validate="{required:true,maxlength:20}" /></td> 
            <td align="left"></td> 
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b>分解方法:</b></td> 
            <td align="left" class="l-table-edit-td"><input name="resolve_way" disabled="disabled" type="text" id="resolve_way" ltype="text" validate="{required:true,maxlength:20}" /></td> 
            <td align="left"></td>
        </tr> 
         <tr>
        	<td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b>分解层次:</b></td> 
            <td align="left" class="l-table-edit-td"><input name="resolve_level" type="text" id="resolve_level"  ltype="text" validate="{required:true,maxlength:20}" /></td> 
            <td align="left"></td>
          	<td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b>参考年限:</b></td> 
            <td align="left" class="l-table-edit-td"><input name="reference_years" type="text" id="reference_years" disabled="disabled"  ltype="text" validate="{required:true,digits:true,range:[1,3],maxlength:4}" /></td> 
            <td align="left"></td>
        </tr> 
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b>自定义分解系数:</b></td> 
            <td align="left" class="l-table-edit-td"><input name="resolve_data" type="text" id="resolve_data"  disabled="disabled" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td> 
            <td align="center" class="l-table-edit-td" colspan="3">
					<button id ="selectResolve" ><b>选择自定义分解系数</b></button>
			</td>
        </tr> 
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b>汇总层次:</b></td> 
            <td align="left" class="l-table-edit-td"><input name="sum_level" type="text" id="sum_level"   ltype="text" validate="{required:true,maxlength:20}" /></td> 
            <td align="left"></td> 
        </tr>  
    </table>
    </form>
   
    </body>
</html>
