<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html style="overflow:hidden;">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="${path}/inc_jquery_1.9.0.jsp"/>
<script type="text/javascript">
	
    var yearComBoBox;
    $(function (){
        loadDict();
        $(':button').ligerButton({width:80});
    });  
    
   
    function save(){
    	var selectHosId="";
    	var selectCopyCode="";
    	if(liger.get("extened_code").getValue()==1){
    		/* if(liger.get("hos_copy_code").getValue()==""){
    			$.ligerDialog.error('请选择账套');
        		return;
    		} */
    		if(liger.get("year_month").getValue()==""){
    			$.ligerDialog.error('请选择会计年度');
        		return;
    		}
    		selectHosId='${hos_id}';
//     		selectCopyCode=liger.get("hos_copy_code").getValue();
    	}else if(liger.get("extened_code").getValue()==2){
    		/* if(liger.get("group_copy_code").getValue()==""){
    			$.ligerDialog.error('请选择账套');
        		return;
    		} */
    		if(liger.get("year_month").getValue()==""){
    			$.ligerDialog.error('请选择会计年度');
        		return;
    		}
    		selectHosId=0;
//     		selectCopyCode=liger.get("group_copy_code").getValue();
    	}else{
    		if(liger.get("nature_code").getValue()==""){
    			$.ligerDialog.error('请选择行业性质');
        		return;
    		}
    	}
    	
    	
        var formPara={
    	extened_code :liger.get("extened_code").getValue(),
        nature_code :liger.get("nature_code").getValue(),
        select_hos_id:selectHosId,
        select_copy_code:0,
        select_acc_year:liger.get("year_month").getValue(),
        acc_year:'${acc_year}'
        };
        
        ajaxJsonObjectByUrl("addBatchAccSubj.do?isCheck=false",formPara,function(responseData){
            
            if(responseData.state=="true"){
                parent.query();
            }
        });
    }
     
   
    function saveAccSubj(){
    	 var formPara={
    		        hos_code : liger.get("hos_code").getValue(),
    		        copy_id : liger.get("copy_code").getValue(),
    		        select_copy_code:0,
    		        select_acc_year : liger.get("year_month").getValue(),
    		        acc_year:'${acc_year}'
    		        };
    	
    		        ajaxJsonObjectByUrl("addBatchAccSubj.do",formPara,function(responseData){
    		            
    		            if(responseData.state=="true"){
    		                parent.query();
    		            }
    		        });
    }
    function loadDict(){
    	autocomplete("#year_month","../queryAccYearDict.do?isCheck=false","id","text",true,true);
        /* //加载会计年度
        yearComBoBox=$("#year_month").ligerComboBox({
    		valueField : "id",
    		textField : "text",
    		selectBoxWidth: 160,
    		selectBoxHeight: 160,
    		width: 160,
    		highLight : "highLight",
    		keySupport:true,
    		cancelable: false
    	}); */
        
    	autocomplete("#nature_code","../queryHosNatureDict.do?isCheck=false","id","text",false,true,'',true);
      //autocomplete("#hos_code","../queryHosInfoDict.do?isCheck=false","id","text",true,true);
      //集团账套  
       /*  autocomplete("#group_copy_code","../queryCopyDict.do?isCheck=false","id","text",false,true,{group_id:'${group_id}',hos_id:0},true);
     	$("#group_copy_code").change(function(){
     		yearComBoBox.setData("");
     		 if(this.value!=""){
     			ajaxJsonObjectByUrl("../queryAcctYearDict.do?isCheck=false",{group_id:'${group_id}',hos_id:0,copy_code:liger.get("group_copy_code").getValue()},function (responseData){
    				if(responseData.length>0){
    					yearComBoBox.setData(responseData);	
    					yearComBoBox.setValue(responseData[0].id);
    					yearComBoBox.setText(responseData[0].text);
    				}else{
    					yearComBoBox.setData([{id:'',text:'没有会计年度...'}]);
    				}
    		    });
     		 }
          }); */
        
        
       /*  //医院账套
        autocomplete("#hos_copy_code","../queryCopyDict.do?isCheck=false","id","text",false,true,{group_id:'${group_id}',hos_id:'${hos_id}'},true);
        $("#hos_copy_code").change(function(){
     		yearComBoBox.setData("");
     		 if(this.value!=""){
     			ajaxJsonObjectByUrl("../queryAcctYearDict.do?isCheck=false",{group_id:'${group_id}',hos_id:'${hos_id}',copy_code:liger.get("hos_copy_code").getValue()},function (responseData){
    				if(responseData.length>0){
    					yearComBoBox.setData(responseData);	
    					yearComBoBox.setValue(responseData[0].id);
    					yearComBoBox.setText(responseData[0].text);
    				}else{
    					yearComBoBox.setData([{id:'',text:'没有会计年度...'}]);
    				}
    		    });
     		 }
          }); */
        
        $("#group_code").ligerComboBox({data:[{id:'${group_id}',text:'${group_name}'}],cancelable: false,width:160});        
        liger.get("group_code").setValue('${group_id}');
	    liger.get("group_code").setText('${group_name}');
	    
        $("#nature_code").ligerComboBox({cancelable: false});
        $("#group_year_month").ligerComboBox({cancelable: false});
//         $("#group_copy_code").ligerComboBox({cancelable: false});
//         $("#hos_copy_code").ligerComboBox({cancelable: false});
        
        $("#extened_code").ligerComboBox({cancelable: false,width:160
        ,onSelected: function (newvalue)
        {
        	if(newvalue==1){
//         		yearComBoBox.setData("");
//         		$('#hos_copy_code').trigger('change');
				 $("#nature_code_tr").css("display","none");
				 $("#group_code_tr").css("display","none");
// 				 $("#group_copy_code_tr").css("display","none");
// 				 $("#hos_copy_code_tr").css("display","block");
				 $("#year_month_tr").css("display","block");
        	}else if(newvalue==2){
//         		yearComBoBox.setData("");
//         		$('#group_copy_code').trigger('change');
        		 $("#nature_code_tr").css("display","none");
        		 $("#group_code_tr").css("display","block");
//         		 $("#group_copy_code_tr").css("display","block");
// 				 $("#hos_copy_code_tr").css("display","none");
				 $("#year_month_tr").css("display","block");
        	}else if(newvalue==3){
//         		yearComBoBox.setData("");
        		 $("#nature_code_tr").css("display","block");
				 $("#group_code_tr").css("display","none");
// 				 $("#group_copy_code_tr").css("display","none");
// 				 $("#hos_copy_code_tr").css("display","none");
				 $("#year_month_tr").css("display","none");
        	}
        }
        }); 
        liger.get("extened_code").setValue('1');
	    liger.get("extened_code").setText('本院会计科目');
	  
     } 
  
    </script>
  
  </head>
  
   <body>
   <div id="pageloading" class="l-loading" style="display: none"></div>
   <form name="form1" method="post"  id="form1" >
   		 <table cellpadding="0" cellspacing="0" class="l-table-edit" border=0 >         
           <tr>	
                <td align="left" class="l-table-edit-td"  style="padding-left:20px;">继承方式：</td>
                <td align="left" class="l-table-edit-td">
                 <select name="extened_code" id="extened_code" ltype="select" >
					<option value="1" selected>本院会计科目</option>
					<option value="2">集团会计科目</option>
					<option value="3">行业性质会计科目</option>
				</select>
                </td>
            </tr> 
          </table>
   
        <table cellpadding="0" cellspacing="0" class="l-table-edit" border=0>
            <tr id="nature_code_tr">	
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">行业性质：</td>
                <td align="left" class="l-table-edit-td"><input name="nature_code" type="text" id="nature_code" ltype="text"   /></td>
            </tr>
             <tr id="group_code_tr">
                <td align="right" class="l-table-edit-td"  style="padding-left:44px;">集团：</td>
                <td align="left" class="l-table-edit-td"><input name="group_code" type="text" id="group_code" ltype="text"  /></td>
            </tr>
           <!--  <tr id="group_copy_code_tr">
                <td align="right" class="l-table-edit-td"  style="padding-left:44px;">账套：</td>
                <td align="left" class="l-table-edit-td"><input name="group_copy_code" type="text" id="group_copy_code" ltype="text"  /></td>
            </tr>
            <tr id="hos_copy_code_tr">
                <td align="right" class="l-table-edit-td"  style="padding-left:44px;">账套：</td>
                <td align="left" class="l-table-edit-td"><input name="hos_copy_code" type="text" id="hos_copy_code" ltype="text"  /></td>
            </tr>  -->
            <tr id="year_month_tr">
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">会计年度：</td>
                <td align="left" class="l-table-edit-td"><input name="year_month" type="text" id="year_month" ltype="text"   /></td>
            </tr> 
          </table>
    </form>
    </body>
</html>
