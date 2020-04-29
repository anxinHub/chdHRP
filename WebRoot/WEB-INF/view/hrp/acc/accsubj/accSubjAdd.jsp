<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html style="overflow:hidden;">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="${path}/inc_jquery_1.9.0.jsp"/>
<script type="text/javascript">
	
     var dataFormat;
     $(function (){
    	 $.post("getRules.do?isCheck=false",null,function(responseData){
    		 $("#rules").val(responseData)
    		 $("#font2").text(responseData);
         });
    	 
		 $("#check_type1").ligerComboBox({isShowCheckBox: true,isMultiSelect: true,selectBoxWidth: 178,cancelable: false});
    	 
    	 $("#check_type2").ligerComboBox({isShowCheckBox: true,isMultiSelect: true,selectBoxWidth: 178,cancelable: false});
    	 
    	 $("#check_type3").ligerComboBox({isShowCheckBox: true,isMultiSelect: true,selectBoxWidth: 178,cancelable: false});
    	 
    	 $("#check_type4").ligerComboBox({isShowCheckBox: true,isMultiSelect: true,selectBoxWidth: 178,cancelable: false});
    	 
     	
        loadDict();//加载下拉框
        
        loadForm();
        
     });  
     
     function  save(){
    	 
    	 var is_remit = 0;
    	 
    	 var is_cash = 0;
    	 
    	 var is_check = 0;
    	 
    	 var is_bill = 0;
    	 
    	 var is_jrsz = 0;
    	 
    	 var is_wlhx = 0;
    	 
    	 var check1="",check2="",check3="",check4="";
         
         if(liger.get("check1").getText()!= "" && liger.get("check1").getText() != null){
        	 
        	 check1= liger.get("check1").getValue();
        	 
         }
         
    	if(liger.get("check2").getText()!= "" && liger.get("check2").getText() != null){
    	    	 
    		check2= liger.get("check2").getValue();
    		
    	}
    	     
    	if(liger.get("check3").getText()!= "" && liger.get("check3").getText() != null){
    		 
    		check3= liger.get("check3").getValue();
    		
    	}
    	
    	if(liger.get("check4").getText()!= "" && liger.get("check4").getText() != null){
    		 
    		check4= liger.get("check4").getValue();
    		
    	}
    	
    
    	 
    	 if($("#is_remit").prop("checked") == true){
    		 
    		 if(liger.get("cur_code").getValue() == "RMB" || liger.get("cur_code").getValue() == ""){
    			 
 		 		$.ligerDialog.error('非外币不能勾选期末调汇');
 		 		
	 			return;
	 			
 		 	}
    		 
    		 is_remit = 1;
    		 
    	 }
    	 
    	 if($("#is_cash").prop("checked") == true){
    		 is_cash = 1;
    	 }
    	 if(liger.get("check1").getValue() != null && liger.get("check1").getValue() != ""){
    		 is_check = 1;
    	 }
    	 
    	 if($("#is_bill").prop("checked") == true){
    		 
    		 is_bill = 1;
    		 
    	 }
    	 
		if($("#is_jrsz").prop("checked") == true){
    		 
			is_jrsz = 1;
    		 
    	 }
    	 
		if($("#is_wlhx").prop("checked") == true){
   		 
			is_wlhx = 1;
    		 
    	 }
        var formPara={
        		
           subj_id:'',
            
           subj_code:$("#subj_code").val(),
            
           cur_code:liger.get("cur_code").getValue(),
            
           subj_type_code:liger.get("subj_type_code").getValue().split(",")[0],
           
           kind_code:liger.get("subj_type_code").getValue().split(",")[1],
            
           subj_nature_code:liger.get("subj_nature_code").getValue(),
            
           vouch_type_code:liger.get("vouch_type_code").getValue(),
            
           subj_name:$("#subj_name").val(),
            
		//subj_name_all:$("#subj_name_all").val(),
            
           subj_name_en:$("#subj_name_en").val(),
           
           is_remit:is_remit,
           
           is_cash:is_cash,
           
           is_bill:is_bill,
           
           is_jrsz:is_jrsz,
           
           is_wlhx:is_wlhx,
           
           subj_dire:$("#subj_dire").val(),
           is_stop : 0,
            
           is_check:is_check,
            
           check1:liger.get("check1").getValue(),
            
           check2:liger.get("check2").getValue(),
            
           check3:liger.get("check3").getValue(),
            
           check4:liger.get("check4").getValue(),
            
           check5:'',
            
           check6:'',
           
           check7:'',
            
           check8:'',
            
           check9:'',
            
           check10:'',
          
           check_type_code1:liger.get("check_type1").getValue(),
           
           check_type_code2:liger.get("check_type2").getValue(),
            
           check_type_code3:liger.get("check_type3").getValue(),
            
           check_type_code4:liger.get("check_type4").getValue(),
           
           rules:$("#rules").val(),
           
           out_code:liger.get("out_code").getValue()
            
         };
        
        ajaxJsonObjectByUrl("addAccSubj.do",formPara,function(responseData){
            
            if(responseData.state=="true"){
				
				 $("input[name='subj_code']").val('');
				//$("input[name='cur_code']").val('');
				 //$("input[name='subj_type_code']").val('');
				 //$("input[name='subj_nature_code']").val('');
				 //$("input[name='vouch_type_code']").val('');
				 $("input[name='subj_name']").val('');
				// $("input[name='subj_name_all']").val('');
				 $("input[name='subj_name_en']").val('');
				 $("input[name='is_cash']").val('');
				// $("input[name='subj_dire']").val('');
				 $("input[name='is_check']").val('');
				 $("input[name='check1']").val('');
				 $("input[name='check2']").val('');
				 $("input[name='check3']").val('');
				 $("input[name='check4']").val('');
				 //$("input[name='check5']").val('');
				 //$("input[name='check6']").val('');
				// $("input[name='check7']").val('');
				// $("input[name='check8']").val('');
				// $("input[name='check9']").val('');
				// $("input[name='check10']").val('');
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
   
    function saveAccSubj(){
        if($("form").valid()){
            save();
        }
   }
    function loadDict(){
        //字典下拉框
        //autocomplete("#subj_code","../querySubjBylevel.do?isCheck=false","id","text",true,true,'',false,false,'215');
        
    	autocomplete("#subj_type_code","../querySubjTypeKind.do?isCheck=false","id","text",true,true,'',false);
        
    	 liger.get("subj_type_code").setValue("${subj_type_code}");

         liger.get("subj_type_code").setText("${subj_type_name}");
        
        autocomplete("#subj_nature_code","../querySubjNature.do?isCheck=false","id","text",true,true,'',true);
        
        autocomplete("#vouch_type_code","../queryVouchType.do?isCheck=false","id","text",true,true);
        
        autocomplete("#cur_code","../queryCur.do?isCheck=false","id","text",true,true,'',true);
        
        autocomplete("#check1","../queryCheckType.do?isCheck=false","id","text",true,true,'',false,'','',120);
        
        autocomplete("#check2","../queryCheckType.do?isCheck=false","id","text",true,true);
        
        autocomplete("#check3","../queryCheckType.do?isCheck=false","id","text",true,true);
        
        autocomplete("#check4","../queryCheckType.do?isCheck=false","id","text",true,true);
        
        autocomplete("#out_code","../queryDeptOutSelect.do?isCheck=false","id","text",true,true);
       
        $("#check1").ligerComboBox({ disabled: false,cancelable: false});
        $("#check2").ligerComboBox({ disabled: true,cancelable: false });
		$("#check3").ligerComboBox({ disabled: true,cancelable: false });
    	$("#check4").ligerComboBox({ disabled: true,cancelable: false });
    	
    	$("#subj_name_all").ligerTextBox({disabled:true});
    	
    	//初始化下拉框
    	$("#check_type1").ligerComboBox({ disabled: true,cancelable: false });
    	
        $("#check_type2").ligerComboBox({ disabled: true,cancelable: false });
        
		$("#check_type3").ligerComboBox({ disabled: true,cancelable: false });
		
    	$("#check_type4").ligerComboBox({ disabled: true,cancelable: false });
    	
    	disabledCheck();
    	on_change();
     } 
    //当会计科目改变时触发 科目类别 以及 余额方向 同步更改
    //1---资产/借方 2---负责/贷方  3---净资产/贷方 4----收入/贷方  5----费用/借方
    function on_change(){
        $("#subj_code").change(function(){
        	
        	var v_subj_code=$("#subj_code").val();
        	
        	var first_str= v_subj_code.substr(0, 1)
        	if(first_str=='1'){
        		//科目类别
            	liger.get("subj_type_code").setValue('01,01');
     	     	liger.get("subj_type_code").setText('资产');
     	     //余额方向
     	     	liger.get("subj_dire").setValue('0');
     	     	liger.get("subj_dire").setText('借方');
        	}else if(first_str=='2'){
        		//科目类别
            	liger.get("subj_type_code").setValue('02,01');
     	     	liger.get("subj_type_code").setText('负债');
     	     //余额方向
     	     	liger.get("subj_dire").setValue('1');
     	     	liger.get("subj_dire").setText('贷方');
        	}else if(first_str=='3'){
        		//科目类别
            	liger.get("subj_type_code").setValue('03,01');
     	     	liger.get("subj_type_code").setText('净资产');
     	     	//余额方向
     	     	liger.get("subj_dire").setValue('1');
     	     	liger.get("subj_dire").setText('贷方');
        	}else if(first_str=='4'){
        		//科目类别
            	liger.get("subj_type_code").setValue('04,01');
     	     	liger.get("subj_type_code").setText('收入');
     	     	//余额方向
     	     	liger.get("subj_dire").setValue('1');
     	     	liger.get("subj_dire").setText('贷方');
        	}else if(first_str=='5'){
        		//科目类别
            	liger.get("subj_type_code").setValue('05,01');
     	     	liger.get("subj_type_code").setText('费用');
     	     	//余额方向
     	     	liger.get("subj_dire").setValue('0');
     	     	liger.get("subj_dire").setText('借方');
        	}else{
        		//科目类别
            	liger.get("subj_type_code").setValue('');
     	     	liger.get("subj_type_code").setText('');
     	     	//余额方向
     	     	liger.get("subj_dire").setValue('');
     	     	liger.get("subj_dire").setText('');
        	}
          });
    	
    }
    function disabledCheck(){
    	var check_type;
    	//辅助核算联动
     	$("#check1").change(function(){
     		 //checkDisabled(this.value);
     		 if(this.value==""){
     			$("#check2").ligerComboBox({ disabled: true,cancelable: false });
     	     	liger.get("check2").setValue('');
     	     	liger.get("check2").setText('');
     	     	$("#check3").ligerComboBox({ disabled: true,cancelable: false });
     	     	liger.get("check3").setValue('');
     	     	liger.get("check3").setText('');
     	       	$("#check4").ligerComboBox({ disabled: true,cancelable: false });
     	        liger.get("check4").setValue('');
     	     	liger.get("check4").setText('');
     	     	$("#check_type1").ligerComboBox({ disabled: true,cancelable: false });
     	     	liger.get("check_type1").setValue('');
     	     	liger.get("check_type1").setText('');
     	     	$("#check_type2").ligerComboBox({ disabled: true,cancelable: false });
     	     	liger.get("check_type2").setValue('');
     	     	liger.get("check_type2").setText('');
     	     	$("#check_type3").ligerComboBox({ disabled: true,cancelable: false });
     	     	liger.get("check_type3").setValue('');
     	     	liger.get("check_type3").setText('');
     	       	$("#check_type4").ligerComboBox({ disabled: true,cancelable: false });
     	        liger.get("check_type4").setValue('');
     	     	liger.get("check_type4").setText('');
     		 }else{
     			 
     			 $("#check2").ligerComboBox({ disabled: false,cancelable: false });
     	    	 $("#check3").ligerComboBox({ disabled: true,cancelable: false });
     	         $("#check4").ligerComboBox({ disabled: true,cancelable: false });
     	         
     	        $("#check_type1").ligerComboBox({ disabled: false,cancelable: false });
     	         
     	         check_type=$("#check1").val();
     	        
     	        chooseType(check_type,"#check_type1","check_type1");
     		 }
     		
          });
           $("#check2").change(function(){
         	if(this.value==""){
           		$("#check3").ligerComboBox({ disabled: true,cancelable: false });
           		liger.get("check3").setValue('');
     	     	liger.get("check3").setText('');
               	$("#check4").ligerComboBox({ disabled: true,cancelable: false });
               	liger.get("check4").setValue('');
      	     	liger.get("check4").setText('');

      	     	$("#check_type2").ligerComboBox({ disabled: true,cancelable: false });
	 	     	liger.get("check_type2").setValue('');
	 	     	liger.get("check_type2").setText('');
      	     	$("#check_type3").ligerComboBox({ disabled: true,cancelable: false });
	 	     	liger.get("check_type3").setValue('');
	 	     	liger.get("check_type3").setText('');
	 	     	$("#check_type4").ligerComboBox({ disabled: true,cancelable: false });
	 	        liger.get("check_type4").setValue('');
	 	     	liger.get("check_type4").setText('');
	           	}else{
	           		$("#check3").ligerComboBox({ disabled: false,cancelable: false });
	               	$("#check4").ligerComboBox({ disabled: true,cancelable: false });
	               	
	               	$("#check_type2").ligerComboBox({ disabled: false,cancelable: false });
	     	        check_type=$("#check2").val();
	     	       
	     	        chooseType(check_type,"#check_type2","check_type2");
	           	}
          });
           $("#check3").change(function(){
         	if(this.value==""){
               	$("#check4").ligerComboBox({ disabled: true,cancelable: false });
             	liger.get("check4").setValue('');
      	     	liger.get("check4").setText('');
      	  		

      	     	$("#check_type3").ligerComboBox({ disabled: true,cancelable: false });
	 	     	liger.get("check_type3").setValue('');
	 	     	liger.get("check_type3").setText('');
           	}else{
               	
           		$("#check4").ligerComboBox({ disabled: false,cancelable: false });
           	
           		$("#check_type3").ligerComboBox({ disabled: false,cancelable: false });
           		
    	        check_type=$("#check3").val();
    	         
    	        
     	        chooseType(check_type,"#check_type3","check_type3");
           	}
          });
           
           $("#check4").change(function(){

            	if(this.value==""){
         	     	$("#check_type4").ligerComboBox({ disabled: true,cancelable: false });
	   	 	     	liger.get("check_type4").setValue('');
	   	 	     	liger.get("check_type4").setText('');
              	}else{
					$("#check_type4").ligerComboBox({ disabled: false,cancelable: false });
	            	
					check_type=$("#check4").val();
					
					chooseType(check_type,"#check_type4","check_type4");
              	}
             });
    }
    //传入选择的辅助核算值，进行判断核算类型下拉框
    function chooseType(obj,id,name){
    	
    	if("部门"==obj){
			
			if(""!=$(id).val()){
				
	    		liger.get(name).setValue('');
	    		
	 	     	liger.get(name).setText('');
			}
    		
			initSelect(id,"../queryDeptType.do?isCheck=false");
    		
    	}else if("职工"==obj){
    		
			//$(id).ligerComboBox({ disabled: false});
			
			if(""!=$(id).val()){
				
	    		liger.get(name).setValue('');
	    		
	 	     	liger.get(name).setText('');
			}
    		
    		initSelect(id,"../../sys/queryEmpKindDict.do?isCheck=false");
    		 	
    	}else if("项目"==obj){
    		
			//$(id).ligerComboBox({ disabled: false});
			
			if(""!=$(id).val()){
				
	    		liger.get(name).setValue('');
	    		
	 	     	liger.get(name).setText('');
			}
    		
    		initSelect(id,"../../sys/queryProjTypeDict.do?isCheck=false");
    		 	
    	}else if("库房"==obj){
    		
			$(id).ligerComboBox({ disabled: false});
			
			if(""!=$(id).val()){
				
	    		liger.get(name).setValue('');
	    		
	 	     	liger.get(name).setText('');
			}
    		
    		initSelect(id,"../../sys/queryStoreTypeDict.do?isCheck=false");
    		 	
    	}else if("客户"==obj){
    		
			//$(id).ligerComboBox({ disabled: false});
			
			if(""!=$(id).val()){
				
	    		liger.get(name).setValue('');
	    		
	 	     	liger.get(name).setText('');
			}
    		//客户分类下拉框
    		initSelect(id,"../../sys/queryCusTypeDict.do?isCheck=false");
    		 	
    	}else if("供应商"==obj){
    		
			//$(id).ligerComboBox({ disabled: false});
			
			if(""!=$(id).val()){
				
	    		liger.get(name).setValue('');
	    		
	 	     	liger.get(name).setText('');
			}
			//供应商分类下拉框
    		initSelect(id,"../../sys/querySupTypeDict.do?isCheck=false");
    		 	
    	}else if("资金来源"==obj){
    		
			//$(id).ligerComboBox({ disabled: false});
			
			if(""!=$(id).val()){
				
	    		liger.get(name).setValue('');
	    		
	 	     	liger.get(name).setText('');
			}
			//资金分类下拉框
    		initSelect(id,"../../sys/querySysSourceNature.do?isCheck=false");
    		 	
    	}else if("单位"==obj){
    		
			//$(id).ligerComboBox({ disabled: false});
			
			if(""!=$(id).val()){
				
	    		liger.get(name).setValue('');
	    		
	 	     	liger.get(name).setText('');
			}
	    	$(id).ligerComboBox({
	    		data : {},
	    		valueField : "id",
	    		textField : "text",
	    		selectBoxWidth: 180,
	    		width: 180,
	    		keySupport:true,
	    		setTextBySource:true,
	    		autocomplete : true/* ,
	    		selectBoxHeight: 1 */
	    	});
    	}else{
    		
    		if(obj == ''){
    			return; 
   			}else{
   				if(""!=$(type).val()){
   				
   				    liger.get(name).setValue('');
   				
   				    liger.get(name).setText('');
   				 // 自定义核算  核算分类下拉框
   				}
   				if(type =='#check_type1'){
   				 initSelect(type,"../queryCheckItemType.do?isCheck=false&check_type_id=${check1}");
   				}
   				if(type =='#check_type2'){
   				 initSelect(type,"../queryCheckItemType.do?isCheck=false&check_type_id=${check2}");
   				}
   				if(type =='#check_type3'){
   				 initSelect(type,"../queryCheckItemType.do?isCheck=false&check_type_id=${check3}");
   				}
   				if(type =='#check_type4'){
   				 initSelect(type,"../queryCheckItemType.do?isCheck=false&check_type_id=${check4}");
   				} 
   			}
    	}
    	
    }
    
    function initSelect(id,data){
    	
    	$(id).ligerComboBox({
    		url : data,
    		valueField : "id",
    		textField : "text",
    		selectBoxWidth: 180,
    		width: 180,
    		keySupport:true,
    		setTextBySource:true,
    		autocomplete : true/* ,
    		selectBoxHeight: 1 */
    	});
    }
    </script>
 
  </head>
  
   <body>
   <div id="pageloading" class="l-loading" style="display: none"></div>
   <form name="form1" method="post"  id="form1" >
   <font id="font1">编码规则：<font id="font2" color="red"></font></font><hr/>
   <input type="hidden" id="rules" name="rules" />
   <div id="panel1-1" >
         <table cellpadding="0" cellspacing="0" class="l-table-edit">
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;padding-top:5px;"><b><font color="red">*</font></b>科目编码：</td>
                <td align="left" class="l-table-edit-td" style="padding-top:5px;"><input name="subj_code" type="text" id="subj_code" ltype="text" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;padding-top:5px;"><b><font color="red">*</font></b>科目名称：</td>
                <td align="left" class="l-table-edit-td" style="padding-top:5px;"><input name="subj_name" type="text" id="subj_name" ltype="text" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 
              <tr>
            	<td align="right" class="l-table-edit-td"  style="padding-left:20px;padding-top:10px;"><b><font color="red">*</font></b>科目全称：</td>
                <td align="left" class="l-table-edit-td"  style="padding-top:10px;"><input name="subj_name_all" type="text" id="subj_name_all" ltype="text" value="系统生成" disabled="disabled" validate="{required:true,maxlength:200}" /></td>
                <td align="left"></td>
              	<td align="right" class="l-table-edit-td"  style="padding-left:20px;padding-top:10px;">英文名称：</td>
                <td align="left" class="l-table-edit-td" style="padding-top:10px;"><input name="subj_name_en" type="text" id="subj_name_en" ltype="text"  /></td>
                <td align="left"></td>
            </tr> 
            <tr>
            	<td align="right" class="l-table-edit-td"  style="padding-left:20px;padding-top:10px;"><b><font color="red">*</font></b>科目类别：</td>
                <td align="left" class="l-table-edit-td"  style="padding-top:10px;"><input name="subj_type_code" type="text" id="subj_type_code" ltype="text" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
              	<td align="right" class="l-table-edit-td"  style="padding-left:20px;padding-top:10px;"><b><font color="red">*</font></b>科目性质：</td>
                <td align="left" class="l-table-edit-td" style="padding-top:10px;"><input name="subj_nature_code" type="text" id="subj_nature_code" ltype="text" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 
             <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;padding-top:10px;"><b><font color="red">*</font></b>币种：</td>
                <td align="left" class="l-table-edit-td" style="padding-top:10px;"><input name="cur_code" type="text" id="cur_code" ltype="text" validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;padding-top:10px;"><b><font color="red">*</font></b>余额方向：</td>
                <td align="left" class="l-table-edit-td" style="padding-top:10px;">
                	<select id="subj_dire" name="subj_dire">
                		<option value="0">借方</option>
                		<option value="1">贷方</option>
                	</select>
                </td>
                <td align="left"></td>
            </tr> 
            <tr>
				<td align="right" class="l-table-edit-td"  style="padding-left:20px;padding-top:10px;">凭证类型：</td>
                <td align="left" class="l-table-edit-td" style="padding-top:10px;"><input name="vouch_type_code" type="text" id="vouch_type_code" ltype="text"  /></td>
                <td align="left"></td>     
               <td align="right" class="l-table-edit-td"  style="padding-left:20px;padding-top:10px;">支出性质：</td>
                <td align="left" class="l-table-edit-td" style="padding-top:10px;"><input name="out_code" type="text" id="out_code" ltype="text" /></td>
                <td align="left"></td>
            </tr> 
<!--             <tr>
            	<td align="right" class="l-table-edit-td"  style="padding-left:20px;padding-top:10px;"><b><font color="red">*</font></b>是否停用：</td>
                <td align="left" class="l-table-edit-td" style="padding-top:10px;">
                	<select id="is_stop" name="is_stop">
                		<option value="0">否</option>
                		<option value="1">是</option>
                	</select>
                </td>
                <td align="left"></td>
            </tr> -->
            <tr>
            	<td align="right" class="l-table-edit-td" colspan="4" style="padding-left:20px;padding-top:10px;">
                	<input name="is_remit" type="checkbox" id="is_remit" />期末调汇&nbsp;&nbsp;
                	<input name="is_cash" type="checkbox" id="is_cash"  />核算现金流
                	<input name="is_bill" type="checkbox" id="is_bill"  />是否票据核销
                	<input name="is_jrsz" type="checkbox" id="is_jrsz"  />是否记入收支
                	<input name="is_wlhx" type="checkbox" id="is_wlhx"  />是否往来核销
                </td>
            </tr>
           </table>
   </div>
   辅助核算<hr/>
   <div id="panel1-2" >
   		<table cellpadding="0" cellspacing="0" class="l-table-edit">
   			<!-- <tr>
	   			<td align="right" class="l-table-edit-td"  style="padding-left:20px;padding-top:2px;">
	   				<div class="l-button" style="width: 60px; " ligeruiid="Button1002" onclick="">
			       				<span>辅助核算</span></div>
	   			</td>
	            <td align="left" class="l-table-edit-td" colspan="4">
	            	
	            </td>
	            <td align="left" ></td>
   			</tr>  -->
   			<tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;padding-top:5px;">辅助核算1：</td>
                <td align="left" class="l-table-edit-td" style="padding-top:5px;"><input name="check1" type="text" id="check1" ltype="text" /></td>
                <td align="left"></td>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;padding-top:5px;">辅助核算2：</td>
                <td align="left" class="l-table-edit-td" style="padding-top:5px;"><input name="check2" type="text" id="check2" ltype="text" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;padding-top:10px;">辅助核算3：</td>
                <td align="left" class="l-table-edit-td" style="padding-top:10px;"><input name="check3" type="text" id="check3" ltype="text" /></td>
                <td align="left"></td>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;padding-top:10px;">辅助核算4：</td>
                <td align="left" class="l-table-edit-td" style="padding-top:10px;"><input name="check4" type="text" id="check4" ltype="text" /></td>
                <td align="left"></td>
            </tr> 
             
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;padding-top:10px;">核算分类1：</td>
                <td align="left" class="l-table-edit-td" style="padding-top:10px;"><input name="check_type1" type="text" id="check_type1" ltype="text" /></td>
                <td align="left"></td>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;padding-top:10px;">核算分类2：</td>
                <td align="left" class="l-table-edit-td" style="padding-top:10px;"><input name="check_type2" type="text" id="check_type2" ltype="text" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;padding-top:10px;">核算分类3：</td>
                <td align="left" class="l-table-edit-td" style="padding-top:10px;"><input name="check_type3" type="text" id="check_type3" ltype="text"  /></td>
                <td align="left"></td>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;padding-top:10px;">核算分类4：</td>
                <td align="left" class="l-table-edit-td" style="padding-top:10px;"><input name="check_type4" type="text" id="check_type4" ltype="text"  /></td>
                <td align="left"></td>
            </tr> 
           <!-- <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;padding-top:10px;">辅助核算9：</td>
                <td align="left" class="l-table-edit-td" style="padding-top:10px;"><input name="check9" type="text" id="check9" ltype="text" /></td>
                <td align="left"></td>
                 <td align="right" class="l-table-edit-td"  style="padding-left:20px;padding-top:10px;">辅助核算10：</td>
                <td align="left" class="l-table-edit-td" style="padding-top:10px;"><input name="check10" type="text" id="check10" ltype="text" /></td>
                <td align="left"></td>
            </tr> 
 -->
        </table>
   </div>
   
    </form>
   
    </body>
</html>
