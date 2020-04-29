<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%String path = request.getContextPath();%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html style="overflow:hidden;"  xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="${path}/inc_jquery_1.9.0.jsp"/>
<script type="text/javascript">
     var dataFormat;
     $(function (){
        loadForm();
        
        $("#save").click( function () {
			save();
		});
		$("#close").click( function () {
			this_close();
		});
     });  
     
     function  save(){
        var formPara={
        		year_month:$("#year_month").val(),
        		dept_id:$("#dept_id").val(),
	        	type :$('#wrap input[name="rbtnl"]:checked').val()
         };								
        ajaxJsonObjectByUrl("hrAttendResultExtandData.do?isCheck=false",formPara,function(responseData){
            if(responseData.state=="true"){
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
   
    function saveIncomeItemTargetConf(){
        if($("form").valid()){
            save();
        }
   }
    
    var this_close = function () {
		var curIndex = parent.$.etDialog.getFrameIndex(window.name);
		parent.$.etDialog.close(curIndex);
	}

    </script>

</head>

<body>
	<div id="pageloading" class="l-loading" style="display: none"></div>
	<form name="form1" method="post" id="form1">
		<input type="hidden" name="year_month" id="year_month" value="${year_month}" /> 
		<input type="hidden" name="dept_id" id="dept_id" value="${dept_id}" /> 
		<table cellpadding="0" cellspacing="0" class="l-table-edit">

			<tr>
				<td align="right" class="l-table-edit-td" style="padding-left: 20px;">生成类型：</td>
				<td align="left" class="l-table-edit-td">
					<div id="wrap">
						<table>
							<!-- <tr>
								<td>
									<input id="rbtnl_0" type="radio" name="rbtnl" value="cover"  /> 
									<label for="rbtnl_0">覆盖生成</label>
								</td>
								<td style="padding-left: 12px">
									<input id="rbtnl_0" type="radio" name="rbtnl" value="incre" /> 
									<label for="rbtnl_1">增量生成</label> <br/> 
								</td>
							</tr> -->
							<tr>
								<td>
									<input id="rbtnl_0" type="radio" name="rbtnl" value="inherit_value" checked="checked"/> 
									<label for="rbtnl_1">继承上月数据(带值) </label>
								</td>
								<td style="padding-left: 12px">
									<input id="rbtnl_0" type="radio" name="rbtnl" value="inherit_novalue" /> 
									<label for="rbtnl_1">继承上月数据(不带值) </label>
								</td>
							</tr>
						</table>
					</div>
				</td>
			</tr>
			<tr >
				<td colspan="2" style="padding-left: 40px;padding-top: 40px">
					<div>
					<ul>
					<li>
						<div class="l-button" id="save" ligeruiid="Button1000" style="width: 60px; margin-left: 60px">
							<div class="l-button-l"></div>
							<div class="l-button-r"></div><span>保存</span>
						</div>
					</li>
					<li>
						<div class="l-button" id="close" ligeruiid="Button1001" style="width: 60px; margin-left: 60px">
							<div class="l-button-l"></div>
							<div class="l-button-r"></div>
							<span>关闭</span>
						</div>
					</li>
					</ul>
					</div> 
				</td>
			</tr>
		</table>
	</form>

</body>
</html>
