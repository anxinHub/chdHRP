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
        var is_jx,is_calculate,attend_types,is_stop,attend_ed_is,is_flag;

        $(function () {
        	initForm();
        })
        
        function initForm (){
        	//是否积休
        	is_jx = $("#is_jx").etSelect({
                options: [
                    	{ id: 0, text: '否'},
                    	{ id: 1, text: '是'},
                ],
                defaultValue: '${is_jx}',
                onChange: function(id){
                	if(id == 1){
                		attend_ed_is.setValue(1);
                	}else if(attend_ed_is.getValue() == 1 && "${attend_types}" == "03"){
                		$(".controltypetr").show();
                	}
                }
            });
        	//是否计算
        	is_calculate = $("#is_calculate").etSelect({
                options: [
                    	{ id: 0, text: '否'},
                    	{ id: 1, text: '是'},
                ],defaultValue: '${is_calculate}'
            });
        	//是否停用
        	is_stop  = $("#is_stop").etSelect({
                options: [
                      	{ id: 0, text: '否'},
                      	{ id: 1, text: '是'},
                  ],
                  defaultValue: '${is_stop}'
            });
        	
        	//是否停用
        	is_default  = $("#is_default").etSelect({
                options: [
                      	{ id: 0, text: '否'},
                      	{ id: 1, text: '是'},
                  ],
                  defaultValue: '${is_default}'
            });
        	//是否限额
        	attend_ed_is = $("#attend_ed_is").etSelect({
                options: [
                        	{ id: 0, text: '否'},
                        	{ id: 1, text: '是'},
                ],
                defaultValue: '${attend_ed_is}',
              
                onChange: function(id){
                	if(is_jx.getValue() == 1 || id == 0 ){
                		$(".controltypetr").hide();
                		$(".maxedtd").hide();
                		$("#control_type2").prop("checked", true);
                		$("#max_ed").val("");
                	}else{
                		$(".controltypetr").show();
                	}
                }
          
              });
        	
        	//考勤分类
        	attend_types = $("#attend_types").etSelect({
                url: '../../queryAttendTypes.do?isCheck=false',
                defaultValue: '${attend_types}',
                onChange: function(val){
                	if(val == "03"){
                		$(".edistd").show();
                	}else{
                		$(".controltypetr").hide();
                		$(".maxedtd").hide();
                		$("#control_type2").prop("checked", true);
                		$("#max_ed").val("");
                		$(".edistd").hide();
                		attend_ed_is.setValue(0);
                	}
                }
            });
        	
        	//额度控制方式
        	if('${control_type}'=='0'){
    			$('input:radio:first').attr('checked', 'checked');
    			$("#edName1").show();
        		$("#edName2").show();
    		}else if('${control_type}'=='1'){
    			$('input:radio:last').attr('checked', 'checked');
    			$("#edName1").hide();
        		$("#edName2").hide();
    		}
        	
        	// 除休假外，不显示
    		if("${attend_types}" != "03"){
    			$(".controltypetr").hide();
    			$(".edistd").hide();
           		$(".maxedtd").hide();
    		}else if(is_jx.getValue() == 1 || '${attend_ed_is}' == 0){
    			$(".controltypetr").hide();
        		$(".maxedtd").hide();
        		$("#control_type2").prop("checked", true);
        		$("#max_ed").val("");
    		  
    		}
        	
        	//保存
        	$("#save").click(function () {
        		if(!checked()){
       			  	return;
       		  	}
                ajaxPostData({
                    url: 'updateAttendItem.do',
                     data: { 
                     	attend_types: attend_types.getValue(),
                     	attend_code: $('#attend_code').val(),
                     	attend_name: $('#attend_name').val(),
                     	attend_shortname: $('#attend_shortname').val(),
                     	is_jx : is_jx.getValue(),
                     	is_default:is_default.getValue(),
                     	is_calculate : is_calculate.getValue(),
                     	attend_ed_is : attend_ed_is.getValue()==""? 0 :attend_ed_is.getValue(),
                     	control_type : $("input[type='radio']:checked").val(),
                     	max_ed : $('#max_ed').val(),
                     	is_stop : is_stop.getValue(),
                     	note : $('#note').val(),
                     	is_flag : is_flag
                    
                     },
                     success: function (responseData) {
                       	$.etDialog.success(
     							'修改成功',
     							 function (index, el) {
   								  var curIndex = parent.$.etDialog.getFrameIndex(window.name);
		                            
		                            var parentFrameName = parent.$.etDialog.parentFrameName;
		                            var parentWindow = parent.window[parentFrameName];
		                            parentWindow.query(); 
		                        	parentWindow.initTree();
		                            parent.$.etDialog.close(curIndex);
     							    }
     							)
                       },
                 })
        	});
        	
        	//关闭
        	$("#close").click(function () {
                var curIndex = parent.$.etDialog.getFrameIndex(window.name);
                parent.$.etDialog.close(curIndex);
                parent.tree.reAsyncChildNodes(null, 'refresh');
            });
        }
      
        //验证
        function checked(){
        	var dataValidate = $.etValidate({
        		config:{},
        		items:[
        			{ el : $('#attend_name'), required : true },
        			{ el : $('#attend_code'), required : true },
        			{ el : $('#attend_shortname'), required : true },
        			{ el : $('#attend_types'), required : true },
        			{ el : $('#is_jx'), required : true },
        			{ el : $('#is_calculate'), required : true },
        			{ el : $('#is_stop'), required : true },
        		]
        	});
        	
        	if(attend_ed_is.getValue() == '1'){
        		if($("input[type='radio']:checked").val()=="0"){
            		if($('#max_ed').val() == ""){
            			$.etDialog.error('请填写额度！');
       	        	 	return;
            		}
            	}
        	}
        	
        	
        	if('${attend_name}' != $('#attend_name').val()){
        		is_flag = 1;
        	}else{
        		is_flag = 0;
        	}
        	return dataValidate.test();
       }
        
        function makeDis(value){
        	if(value=='0'){
        		$("#max_ed").val('${max_ed}');
        		$("#edName1").show();
        		$("#edName2").show();
        	}else{
        		$("#max_ed").val("");
        		$("#edName1").hide();
        		$("#edName2").hide();
        	}
        }
    </script>
</head>

<body>
		<table class="table-layout" style="width: 100%;">
			<tr>
				<td class="label ">项目编码<font size="2" color="red">*</font>：</td>
				<td class="ipt"><input id="attend_code" type="text" value="${attend_code}" disabled/></td>
				
				<td class="label ">项目名称<font size="2" color="red">*</font>：</td>
				<td class="ipt"><input id="attend_name" type="text" value="${attend_name}" /></td>
			</tr>
			<tr>
				<td class="label ">项目简称<font size="2" color="red">*</font>：</td>
				<td class="ipt"><input id="attend_shortname" type="text" value="${attend_shortname}" /></td>
				
				<td class="label ">考勤分类<font size="2" color="red">*</font>：</td>
				<td class="ipt">
					<select id="attend_types" style="width: 180px;" disabled></select>
				</td>
			</tr>
			<tr>
				<td class="label ">是否积休<font size="2" color="red">*</font>：</td>
				<td class="ipt"><select id="is_jx" style="width: 180px;"></select></td>
				
				<td class="label ">是否计算<font size="2" color="red">*</font>：</td>
				<td class="ipt"><select id="is_calculate" style="width: 180px;"></select></td>
			</tr>
			<tr>
				<td class="label ">是否停用<font size="2" color="red">*</font>：</td>
				<td class="ipt">
					<select id="is_stop" style="width: 180px;">
				</td>
				
				<td class="label edistd">是否限额：</td>
				<td class="ipt edistd">
					<select id="attend_ed_is" style="width: 180px;">
				</td>
			</tr>
			<tr class="controltypetr">
				<td class="label ">额度控制方式<font size="2" color="red">*</font>：</td>
				<td class="ipt">
					<input  type="radio" name="control_type"   value='0' onclick="makeDis('0')"/>全院
					<input  type="radio" name="control_type"   value="1"  onclick="makeDis('1')"/>个人
				</td>
				
				<td id="edName1" class="label">额度上限：</td>
				<td id="edName2" class="ipt"><input id="max_ed" type="text" value="${max_ed}"/></td>
			</tr>
			<tr>	<td class="label ">是否默认考勤<font size="2" color="red">*</font>：</td>
				<td class="ipt">
					<select id="is_default" style="width: 180px;">
				</td>
				<td class="label ">备注：</td>
				<td class="ipt">
					<input id="note" type="text" style="width: 180px;" value="${note}"/>
				</td>
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