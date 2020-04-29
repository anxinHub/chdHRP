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
        var year, begin_date, end_date;
        var formValidate;

        var initValidate = function () {
        	
        	attend_class_typecode = $("#attend_class_typecode").etSelect({
                url: "../../queryAttendCalssType.do?isCheck=false",
                defaultValue:"${attend_class_typecode}",
            });
        	attend_code=$("#attend_code").etSelect({
        		  url: "../../queryAttendCodeCla.do?isCheck=false&attend_types="+"01",
                defaultValue: "${attend_code}",
            
            });
        	if("${attend_begtime2}"!=""){
        		$('#attend2').show();
        		$('#a1').hide();
        	}
        	if( "${attend_begtime3}"!=""){
        		$('#attend3').show();
        		$('#a2').hide();
               	$('#a3').hide();
        	}
        	attend_begtime1 = laydate.render({
				elem: '#attend_begtime1',
				type: 'time',
        		format: 'HH:mm'
    		});
        	attend_endtime1 = laydate.render({
     		  	elem: '#attend_endtime1',
      			type: 'time',
      		  	format: 'HH:mm'
  			});
        	attend_begtime2 = laydate.render({
    			elem: '#attend_begtime2',
      			type: 'time',
      			format: 'HH:mm'
  			});
        	attend_endtime2 = laydate.render({
     		  	elem: '#attend_endtime2',
      			type: 'time',
      		  	format: 'HH:mm'
  			});
        	attend_begtime3 = laydate.render({
    			elem: '#attend_begtime3',
      			type: 'time',
      			format: 'HH:mm'
  			});
        	attend_endtime3 = laydate.render({
     		  	elem: '#attend_endtime3',
      			type: 'time',
      		  	format: 'HH:mm'
  			});
     			
        	  $("#save").click( function () {
        		  var ad = $('#attend_days').val();
        		  if(ad < 0){
        			  $.etDialog.error('核算天数必须大于0！');
        			  return;
        		  }
        		  //验证
        		  if(!checked()){
        			  return;
        		  }
                  ajaxPostData({
                     url: 'updateAttendClass.do',
                      data: {
                    	   attend_class_typecode:attend_class_typecode.getValue(),
                      	attend_code: attend_code.getValue(),
                      	old_attend_classcode :"${attend_classcode}",
                      	attend_classcode: $('#attend_classcode').val(),
                      	attend_classname: $('#attend_classname').val(),
                      	attend_classsname: $('#attend_classsname').val(),
                      	attend_begtime1: $('#attend_begtime1').val(),
                      	attend_endtime1: $('#attend_endtime1').val(),
                      	attend_begtime2: $('#attend_begtime2').val(),
                      	attend_endtime2: $('#attend_endtime2').val(),
                      	attend_begtime3: $('#attend_begtime3').val(),
                      	attend_endtime3: $('#attend_endtime3').val(),
                      	attend_days: $('#attend_days').val()
                      },
                      success: function (responseData) {
                        	$.etDialog.success(
      							'修改成功',
      							 function (index, el) {
      								  var curIndex = parent.$.etDialog.getFrameIndex(window.name);
      		                            
      		                            var parentFrameName = parent.$.etDialog.parentFrameName;
      		                            var parentWindow = parent.window[parentFrameName];
      		                            parentWindow.query(); 
      		                            parent.$.etDialog.close(curIndex);
      							    }
      							)
                        },
                      delayCallback: true
                  })
              })
            $("#close").click(function () {
                          var curIndex = parent.$.etDialog.getFrameIndex(window.name);
                          parent.$.etDialog.close(curIndex);
                      })
        };
        function  doSomething1(obj) {  
        	if($('#attend_begtime1').val()!=null&&$('#attend_endtime1').val()!=""){
        	 $('#attend2').show();
        	 $('#a1').hide();
        	}else{
      		  $.etDialog.error('请选择时间1');
              return;
    	}
        }  
        function  doSomething2(obj) { 
        	if($('#attend_begtime2').val()!=null &&$('#attend_endtime2').val()!=""){
       	 $('#attend3').show();
       	 $('#a2').hide();
       	$('#a3').hide();
        	}
        	else{
        		  $.etDialog.error('请选择时间2');
                  return;
        	}
       } 
        function  deleteSomething1(obj) { 
        	$('#attend_begtime2').val('')
        	$('#attend_endtime2').val('')
          	 $('#attend2').hide();
          	 $('#a1').show();
          }
        function  deleteSomething2(obj) { 
        	$('#attend_begtime3').val('')
        	$('#attend_endtime3').val('')
          	 $('#attend3').hide();
          	 $('#a2').show();
         	$('#a3').show();
          }
        function checked(){
        	var dataValidate = $.etValidate({
        		config:{},
        		items:[
                    { el : $('#attend_classcode'), required : true },
        			{ el : $('#attend_classname'), required : true },
        			{ el : $('#attend_classsname'), required : true },
        			{ el : $('#attend_code'), required : true },
        			{ el : $('#attend_class_typecode'), required : true },
        			{ el : $('#attend_begtime1'), required : true },
        			{ el : $('#attend_endtime1'), required : true },
        			{ el : $('#attend_days'), required : true }
        			
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
            <td class="label "><font size="2" color="red">*</font>班次编码：</td>
            <td class="ipt">
                <input id="attend_classcode" disabled value="${attend_classcode }"   type="text" />
            </td>
        </tr>
        <tr>
            <td class="label "><font size="2" color="red">*</font>班次名称：</td>
            <td class="ipt">
                <input id="attend_classname" value="${attend_classname }"  type="text" />
            </td>
        </tr>
        <tr>
            <td class="label "><font size="2" color="red">*</font>班次简称：</td>
            <td class="ipt">
                <input id="attend_classsname" value="${attend_classsname }"  type="text" />
            </td>
        </tr>
            <tr>
	               <td class="label"><font size="2" color="red">*</font>班次分类：</td>
                        <td class="ipt">
                        <select id="attend_class_typecode" style="width: 180px;"></select>
                            
                        </td>
	        </tr>
         <tr>
            <td class="label "><font size="2" color="red">*</font>考勤项目：</td>
            <td class="ipt">
              <select id="attend_code" style="width: 180px;"></select>
            </td>
        </tr>
        <tr>
            <td class="label "><font size="2" color="red">*</font>上下班时间1：</td>
            <td class="ipt">
                <input id="attend_begtime1"  type="text" placeholder="HH:mm" style="width: 120px;" value="${attend_begtime1 }" />
                <input id="attend_endtime1" type="text" placeholder="HH:mm" style="width: 120px;" value="${attend_endtime1 }" />
                <a href="javascript:void(0)" id="a1" onclick="doSomething1()">增加</a>
            </td>
           
        </tr>
            <tr id="attend2" style="display:none">
            <td class="label ">上下班时间2：</td>
            <td class="ipt">
                <input id="attend_begtime2"  type="text" placeholder="HH:mm" style="width: 120px;" value="${attend_begtime2 }" />
                <input id="attend_endtime2" type="text" placeholder="HH:mm" style="width: 120px;" value="${attend_endtime2 }" />
                <a href="javascript:void(0)" id="a2" onclick="doSomething2()">增加</a>
                  <a href="javascript:void(0)" id="a3" onclick="deleteSomething1()">删除</a>
            </td>
           
        </tr>
            <tr id="attend3" style="display:none">
            <td class="label ">上下班时间3：</td>
            <td class="ipt">
                <input id="attend_begtime3"  type="text" placeholder="HH:mm" style="width: 120px;" value="${attend_begtime3 }" />
                <input id="attend_endtime3" type="text" placeholder="HH:mm" style="width: 120px;" value="${attend_endtime3 }" />
                  <a href="javascript:void(0)" onclick="deleteSomething2()">删除</a>
            </td>
           
        </tr>
        <tr>
            <td class="label ">核算天数：</td>
            <td class="ipt">
                <input id="attend_days" step="0.5" type="number" value="${attend_days }"  min="0.5" />
            </td>
        </tr>
        <tr>
      
    </tr>
    </table>
        <div class="button-group btn">
        <button id="save">保存</button>
        <button id="close">关闭</button>
    </div>
</body>

</html>