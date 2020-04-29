<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html style="overflow:hidden;">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="${path}/inc_jquery_1.9.0.jsp"/>
<script src="<%=path%>/lib/My97DatePicker/WdatePicker.js"	type="text/javascript"></script>
<script type="text/javascript">
	
    var hosComBoBox;
    var copyComBoBox;
    var checkComBoBox;
    $(function (){
        loadDict();
    });  
     
    function save(){
    	if(liger.get("hos_id").getValue()==""){
    		$.ligerDialog.error('请选择医院');
    		return;
    	}
    	if(liger.get("copy_code").getValue()==""){
    		$.ligerDialog.error('请选择账套');
    		return;
    	}
    	if(liger.get("check_type_id").getValue()==""){
    		$.ligerDialog.error('请选择核算类型');
    		return;
    	}
        var formPara={
        hos_id:liger.get("hos_id").getValue(),	
        copy_code:liger.get("copy_code").getValue(),
        check_type_id:liger.get("check_type_id").getValue(),
        cur_check_type_id:${check_type_id}
        };
        $.ligerDialog.confirm('确定要将[ '+liger.get("check_type_id").getText()+' ]里面的核算项增量导到当前核算类[ ${check_type_name} ]里面吗?', function (yes){
        	if(yes){
        		ajaxJsonObjectByUrl("addBatchAccCheckItem.do?isCheck=false",formPara,function(responseData){
                    if(responseData.state=="true"){
                        parentFrameUse().query();
                    }
                });
        	}
        }); 
        
        
    }
   
   
    function saveAccCheckItem(){
            save();
    }
    function loadDict(){
        //字典下拉框
       // autocomplete("#hos_id","../queryHosInfoDict.do?isCheck=false","id","text",true,true);
       // autocomplete("#copy_code","../queryCopyDict.do?isCheck=false","id","text",true,true);
       // autocomplete("#check_type_id","../queryCheckTypeDict.do?isCheck=false","id","text",true,true);
       
       
        //加载核算类型
        checkComBoBox=$("#check_type_id").ligerComboBox({
    		valueField : "id",
    		textField : "text",
    		selectBoxWidth: 160,
    		selectBoxHeight: 160,
    		width: 160,
    		highLight : "highLight",
    		keySupport:true,
    		cancelable: false
    	});
        
         //加载账套
        copyComBoBox=$("#copy_code").ligerComboBox({
    		valueField : "id",
    		textField : "text",
    		selectBoxWidth: 160,
    		width: 160,
    		highLight : "highLight",
    		keySupport:true,
    		cancelable: false,
    		onSelected: function (newvalue){copyChange(newvalue);}
    	});
        
       
       
    	//加载医院
        hosComBoBox=$("#hos_id").ligerComboBox({
    		data:${hos_code},
    		valueField : "id",
    		textField : "text",
    		selectBoxWidth: 160,
    		width: 160,
    		highLight : "highLight",
    		keySupport:true,
    		cancelable: false,
    		onSelected: function (newvalue){hosChange(newvalue);}
    	});
        
        if(${hos_code}.length>0){
        	hosComBoBox.setValue(${hos_code}[0].id);
        	hosComBoBox.setText(${hos_code}[0].text);
    	}
      
     }
    
    //医院事件,加载账套
    function hosChange(newvalue){
    	copyComBoBox.setData("");
		checkComBoBox.setData("");
    	if(newvalue!=""){
			ajaxJsonObjectByUrl("../queryCopyDict.do?isCheck=false.do?isCheck=false",{hos_id:newvalue},function (responseData){
				if(responseData.length>0){
					copyComBoBox.setData(responseData);	
					copyComBoBox.setValue(responseData[0].id);
					copyComBoBox.setText(responseData[0].text);
				}else{
					copyComBoBox.setData([{id:'',text:'没有账套...'}]);
				}
		    });
		}
    }
    
    //账套事件,加载核算类型
    function copyChange(newvalue){
    	if(newvalue!=""){
			ajaxJsonObjectByUrl("../queryCheckTypeDict.do?isCheck=false",{hos_id:hosComBoBox.getValue(),copy_code:newvalue},function (responseData){
				if(responseData.length>0){
					checkComBoBox.setData(responseData);
					checkComBoBox.setValue(responseData[0].id);
					checkComBoBox.setText(responseData[0].text);
				}else{
					checkComBoBox.setData([{id:'',text:'没有核算类型...'}]);
				}
		    });
		}
    }
    
    </script>
  
  </head>
  
   <body>
   <div id="pageloading" class="l-loading" style="display: none"></div>
   <form name="form1" method="post"  id="form1" >
        <table cellpadding="0" cellspacing="0" class="l-table-edit" >
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">医院：</td>
                <td align="left" class="l-table-edit-td"><input name="hos_id" type="text" id="hos_id" ltype="text"  validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
            </tr> 
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">账套：</td>
                <td align="left" class="l-table-edit-td"><input name="copy_code" type="text" id="copy_code" ltype="text"  validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
            </tr>
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">核算类型：</td>
                <td align="left" class="l-table-edit-td"><input name="check_type_id" type="text" id="check_type_id" ltype="text"  validate="{required:true,maxlength:20}" /></td>
                <td align="left"></td>
            </tr>
            <tr>
            	<td colspan="3" style="padding-left:20px;"><font style="color:blue;font-size:15px"><br/>说明： 选择核算类增量导到当前核算类[ </font>
            	<font style="color:red;font-size:15px"><b>${check_type_name}</b></font>
            	<font style="color:blue;font-size:15px">]里面。</font></td>
            </tr>  
        </table>
    </form>
    </body>
</html>
