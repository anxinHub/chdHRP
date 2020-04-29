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
    <script>
      
        var formValidate;

        var initValidate = function () {
        
        
     			
        	  $("#save").click( function () {
        		  
        		  //验证
        		  if(!checked()){
        			  return;
        		  }
        		  
                  ajaxPostData({
                     url: 'updateAttendClassType.do',
                      data: {
                           old_calss_typecode:'${attend_class_typecode}',
                    	  attend_class_typecode: $('#attend_class_typecode').val(),
                    	  attend_class_typename: $('#attend_class_typename').val()
                    
                      },
                      success: function (responseData) {
                      /*   var curIndex = parent.$.etDialog.getFrameIndex(window.name);
                        parent.$.etDialog.close(curIndex); */
                        var parentFrameName = parent.$.etDialog.parentFrameName;
                        var parentWindow = parent.window[parentFrameName];
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
        			{ el : $('#attend_class_typecode'), required : true }
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
	<div class="button-group" style="overflow-y: auto">
		 <table class="table-layout" style="width: 100%;">
	        <tr>
	            <td class="label ">班次分类编码：</td>
	            <td class="ipt">
	                <input id="attend_class_typecode"  value="${attend_class_typecode}"  disabled type="text" />
	            </td>
	        </tr>
	        <tr>
	            <td class="label ">班次分类名称：</td>
	            <td class="ipt">
	                <input id="attend_class_typename" value="${attend_class_typename}" type="text" />
	            </td>
	        </tr>
    	</table>
        <div class="button-group btn">
	        <button id="save">保存</button>
	        <button id="close">关闭</button>
	    </div>
	</div>   
</body>

</html>