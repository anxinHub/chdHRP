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
        var is_jx,is_calculate,attend_types,is_stop,attend_ed_is;
        
        $(function () {
        	initForm();
        })
        
        function initForm () {
        	//是否积休
        	is_jx = $("#is_jx").etSelect({
                options: [
                    	{ id: 0, text: '否'},
                    	{ id: 1, text: '是'},
                ],
                onChange: function(id){
                	if(id == 1 /* && "${attend_types}" == "03" || attend_types.getValue() == "03" */){
                		attend_ed_is.setValue(1);
                	}else if(attend_ed_is.getValue() == 1 && "${attend_types}" == "03" || attend_types.getValue() == "03"){
                		$(".controltypetr").show();
                	}
                }
            });
        	//是否计算
        	is_calculate = $("#is_calculate").etSelect({
                options: [
                    	{ id: 0, text: '否'},
                    	{ id: 1, text: '是'},
                ]
            });
        	//是否停用
        	is_stop  = $("#is_stop").etSelect({
                options: [
                      	{ id: 0, text: '否'},
                      	{ id: 1, text: '是'},
                  ]
            });
        	//是否停用
        	is_default  = $("#is_default").etSelect({
                options: [
                      	{ id: 0, text: '否'},
                      	{ id: 1, text: '是'},
                  ]
            });
        	//是否限额
        	attend_ed_is = $("#attend_ed_is").etSelect({
                options: [
                   	{ id: 0, text: '否'},
                   	{ id: 1, text: '是'},
                ],
                onChange: function(id){
                	if(is_jx.getValue() == 1 || id == 0){
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
        	
        	$("#edName2").val("");
    		$("#edName1").hide();
    		$("#edName2").hide();
    		
    		// 除休假外，不显示
    		$(".controltypetr").hide();
    		if("${attend_types}" != "03" || attend_types.getValue() != "03"){
    			$(".edistd").hide();
           		$(".maxedtd").hide();
    		}
    		
        	//考勤分类设置是否可用
        	if('${attend_types}' == 0){
        		attend_types.enabled();
        	}else{
        		attend_types.disabled();
        	}
        	
        	//保存
        	$("#save").click(function () {
        		if(!checked()){
       			  	return;
       		  	}
        		if($('#attend_code').val().length >=4){
        			
        		
        		var attend_code=$('#attend_code').val().substring(0,2);
        		if(attend_code.indexOf("${attend_types}")!=-1){
        			
                ajaxPostData({
                    url: 'addAttendItem.do',
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
                     	note : $('#note').val()
                    
                     },
                     success: function (responseData) {
                       	$.etDialog.success(
     							'添加成功',
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
        		}else{
        			$.etDialog.error("项目编码必须以"+"${attend_types}"+"开头！");
        			return;
        		}
        		}else{
        			$.etDialog.error("项目编码必须长度必须大于4位");
        			return;
        		}
        	});
        	
        	//关闭
        	$("#close").click(function () {
                var curIndex = parent.$.etDialog.getFrameIndex(window.name);
                parent.$.etDialog.close(curIndex);
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
        			{ el : $('#is_jx'), required : true },
        			{ el : $('#is_default'), required : true },
        			{ el : $('#is_calculate'), required : true },
        			{ el : $('#is_stop'), required : true },
        			{ el : $('#attend_types'), required : true }
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
        	
        	return dataValidate.test();
       }
        
        function makeDis(value){
        	if(value=='0'){
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
				<td class="ipt"><input id="attend_code" type="text" placeholder="请以选择的考勤分类编码开头" <%-- value="${attend_types}" --%>/></td>
				
				<td class="label ">项目名称<font size="2" color="red">*</font>：</td>
				<td class="ipt"><input id="attend_name" type="text" /></td>
			</tr>
			<tr>
				<td class="label ">项目简称<font size="2" color="red">*</font>：</td>
				<td class="ipt"><input id="attend_shortname" type="text" /></td>
				
				<td class="label ">考勤分类<font size="2" color="red">*</font>：</td>
				<td class="ipt">
					<select id="attend_types" style="width: 180px;" ></select>
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
				<td class="label">额度控制方式<font size="2" color="red">*</font>：</td>
				<td class="ipt">
					<input id="control_type1" type="radio" name="control_type"   value='0' onclick="makeDis('0')"/>全院
					<input id="control_type2" type="radio" name="control_type"   value="1"  onclick="makeDis('1')"/>个人
				</td>
				
				<td id="edName1" class="label maxedtd">额度上限：</td>
				<td id="edName2" class="ipt maxedtd"><input id="max_ed" type="text" /></td>
			</tr>
			<tr>
					<td class="label ">是否默认考勤<font size="2" color="red">*</font>：</td>
				<td class="ipt">
					<select id="is_default" style="width: 180px;">
				</td>
				<td class="label ">备注：</td>
				<td class="ipt">
					<input id="note" type="text" style="width: 180px;"/>
				</td>
			</tr>
	</table>
	<div>
        <div class="button-group btn">
	        <button id="save">保存</button>
	        <button id="close">关闭</button>
	    </div>
	</div> 
	
	<%-- 	<tr style="display: none">
				<td class="label ">额度取值方式：</td>
				<td class="ipt"><input type="radio" name="attend_ed_type"
					checked value='0' /><label>手动输入</label> <input
					name="attend_ed_type" type="radio" value="1" /><label>计算</label></td>
			</tr>

			<tr>
			</tr>

		</table>
<div id="box">
  <div id="tab_content">
<p style="margin-left: -525px; margin-bottom: -10px;">计算公式：</p>
<br/>
<textarea	 id="formula_method_chs" name="formula_method_chs" style="height: 100px; margin-left: -80px;width: 600px; font-size: 16px; resize: none;"	validate="{required:true}" onblur=""></textarea> 
<table>
<tr>
<td height="100px">参考项目
<table> 
<tr>
<td class="label ">信息集：</td>
<td><select id="tab_code" style="width: 150px;"></select></td>
</tr>
<tr>
<td  class="label ">项目：</td>
<td><select id="col_name" style="width: 150px;"></select></td>
</tr>
<tr>
<td  class="label ">选项：</td>
<td><select id="option" style="width: 150px;"></select></td>
</tr>
<tr>
<td ></td>
</tr>
</table>
</td>
<!-- 右侧运算符号 -->
<td  height="100px">运算符号
<table> 
<tr>


<td class="button-group btn"> 
<button id="+">+</button>
<button id="-">-</button>
<button id="*">*</button>
<button id="/">/</button>
<button id="(">(</button>
<button id="clear">清空</button>
</td>
</tr>
<tr>
<td class="button-group btn"> 
<button id=")">)</button>
<button id="=">=</button>
<button id=">">></button>
<button id="<"><</button>
<button id="%">%</button>
<button id="case">如果</button>
</td>
</tr>
<tr>
<td class="button-group btn"> 


<button id="&&">并且</button>
<button id="|| ">或者</button>
<button id="!">非</button>
<button id="that">那么</button>
</td>
</tr>
</table>

</td>

</tr> --%>
	
</body>

</html>