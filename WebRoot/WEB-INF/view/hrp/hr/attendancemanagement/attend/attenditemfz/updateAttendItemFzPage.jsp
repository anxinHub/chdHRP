<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%String path = request.getContextPath();%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <jsp:include page="${path}/resource.jsp">
	    <jsp:param value="dialog,datepicker,select,validate" name="plugins" />
	</jsp:include>
	<script type="text/javascript" src="<%=path%>/lib/laydate/laydate.js"></script>
	<style >
  #box {
        /* width:400px; */
      /*   height: 300px; */
        margin: 20px 320px 0;
    }
   #tab_content {
        width:700px; 
        height: 383px;
        border: 1px solid #a3c0e8;
        text-align: center;
        background: #FFF;
        overflow: hidden;
    }
	
	</style> 
    <script>
        var year, begin_date, end_date,attend_areacode,attend_begtime1,attend_endtime1,attend_begtime2,attend_endtime2,attend_begtime3,attend_endtime3;
        var formValidate;

        var initValidate = function () {
        	if("${attend_result_is_fz}"==0){
        	    $("input:radio[value='0']").attr('checked','true');
        	}else{
        		 $("input:radio[value='1']").attr('checked','true');
        	}
     
        	
        	  $("#save").click( function () {
        		  
        		  //验证
        		  if(!checked()){
        			  return;
        		  }
        		  
                  ajaxPostData({
                     url: 'updateAttendItemFz.do',
                      data: {
                        	attend_code_fz: $('#attend_code_fz').val(),
                          	attend_name_fz: $('#attend_name_fz').val(),
                          	attend_result_is_fz: $('input[name="attend_result_is_fz"]:checked ').val(),
                     
                      },
                      success: function (responseData) {
                    	
                        var curIndex = parent.$.etDialog.getFrameIndex(window.name);
                     	
                        parent.$.etDialog.close(curIndex);
                        var parentFrameName = parent.$.etDialog.parentFrameName;
                        var parentWindow = parent.window[parentFrameName];
                        //console.log(parentWindow)
                        parentWindow.query();
                     
                      },
                   
                  })
              })
            $("#close").click(function () {
                          var curIndex = parent.$.etDialog.getFrameIndex(window.name);
                          parent.$.etDialog.close(curIndex);
                      })
        };

        function checked(){
        	var dataValidate = $.etValidate({
        		config:{},
        		items:[
        				{ el : $('#attend_name_fz'), required : true },
        			{ el : $('#attend_code_fz'), required : true }
        		
        			
        		]
        	});
        	return dataValidate.test();
        }
        
        $(function () {
            initValidate();
        })

        
    </script>
</head>

<body>
	
		<table class="table-layout" style="width: 100%;">
			<tr>
				<td class="label ">考勤项目分组编码<font size="2" color="red">*</font>：</td>
				<td class="ipt">
				<input id="attend_code_fz"   type="text"  value="${attend_code_fz}" disabled/></td>
			</tr>
			<tr>
				<td class="label ">考勤项目分组名称<font size="2" color="red">*</font>：</td>
				<td class="ipt"><input id="attend_name_fz" value="${attend_name_fz}" type="text" /></td>
			</tr>
			<tr id="attend_ed">
				<td class="label " >是否在考勤表显示<font size="2" color="red">*</font>：</td>
				<td class="ipt"> <input type="radio"
					name="attend_result_is_fz" value='1' /><label>是</label>
					<input name="attend_result_is_fz" type="radio"  value="0" /><label>否</label></td>
			</tr>

</table>
	
<div>
        <div class="button-group btn">
	        <button id="save">保存</button>
	        <button id="close">关闭</button>
	    </div>
	</div> 
</body>

</html>