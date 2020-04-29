<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="${path}/inc_jquery_1.9.0.jsp" />
<script src='<%=path%>/lib/hrp/acc/superReport/ktLayer.js'  type="text/javascript"></script>
<script src="<%=path%>/lib/layer-v2.3/layer/layer.js" type="text/javascript"></script>
<script type="text/javascript">
	
    var dataFormat;
    
    $(function (){
    	
        loadDict();
        
        loadForm();
       
    });  
    
    function save(){
       
        var dialog = frameElement.dialog; //调用页面的dialog对象(ligerui对象)
        
        var data=dialog!=null?dialog.get("data").data:"";
        
        var ParamVo =[];
        
        var tell_id = '${tell_id}';
        
        $(data).each(function (i,v){
        	
			if(this.tell_id != "" &&this.tell_id !=null){
				
				ParamVo.push(
						this.tell_id+"@"+
						this.s_occur_date+"@"+
						this.summary+"@"+
						this.att_num+"@"+
						this.other_subj_id+"@"+
						this.debit+"@"+
						this.credit+"@"+
						'${subj_id}' 
					)
			}
        });
		
        var para = {
        		ParamVo:ParamVo.toString(),
        		vouch_type_code:liger.get("vouch_type_code").getValue()
    	};
        
        var loadIndex = layer.load(1);
        
        ajaxJsonObjectBylayer("addAccTellVouch.do?isCheck=false",para,function(responseData){	
			
			//layer.close(loadIndex);
			
			var para={
				url:'hrp/acc/accvouch/superVouch/superVouchMainPage.do?isCheck=false&openType=autoVouch&openBusiType=tell',
				title:'会计凭证',
				width:0,
				height:0,
				isShowMin:true,
				isModal:true,
				parentframename: window.name,
				data:{vouch:responseData, busi_log_table:'acc_tell', busi_type_code:'C001',busi_no:tell_id.substring(0,tell_id.length-1)}
			};
			parent.parent.openDialog(para);
  		},layer,loadIndex);
        
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
   
    function accCashAccountCon(){
            save();
        
    }
    function loadDict(){
        //字典下拉框
    	 autocomplete("#vouch_type_code", "../queryVouchType.do?isCheck=false","id", "text", true, true);
       	 
     }   
    </script>
  
  </head>
  
   <body>
   <div id="pageloading" class="l-loading" style="display: none"></div>
   <form name="form1" method="post"  id="form1" >
        <table cellpadding="0" cellspacing="0" class="l-table-edit" style="margin-top: 70px;margin-left: 50px">
            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">凭证类型：</td>
                <td align="left" class="l-table-edit-td"><input name="vouch_type_code" type="text" id="vouch_type_code" ltype="text" /></td>
                <td align="left"></td>
            </tr> 
        </table>
    </form>
    </body>
</html>
