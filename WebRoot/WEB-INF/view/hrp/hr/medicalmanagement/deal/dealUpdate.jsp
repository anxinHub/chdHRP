<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<jsp:include page="${path}/resource.jsp">
	<jsp:param value="hr,select,validate,grid,upload,datepicker,dialog" name="plugins" />
</jsp:include>
<script>
	var application_date, surgery_name;
	var leftGrid, RightGrid;
	  var patient_hide_data = {};
	/*     var formValidate;

        var initValidate = function () {
            formValidate = $.etValidate({
                items: [
                    { el: $("#year"), required: true },
                    { el: $("#dept_code"), required: true },
                    { el: $("#user_code"), required: true },
                    { el: $("#apply_level_code"), required: true },
                    { el: $("#apply_date"), required: true }
                ]
            });
        }; */
	var initForm = function() {
		in_hos_no = $("#in_hos_no").etSelect({
              url: "queryinHosNo.do?isCheck=false",
              defaultValue: "${in_hos_no}",
              onInit: function (value) {
                  ajaxPostData({
                      url: 'queryinHosNoDetail.do?isCheck=false',
                      data: {
                    	  in_hos_no: value
                      },
                      success: function (res) {
                    	  ajaxPostData({
                              url: 'queryResearchD.do?isCheck=false',
                               data: {
                             	  in_hos_no: value ,
                             	  occ_date: res.occ_date || '',
                             	  plaint_date: res.plaint_date || '',
                               },
                               success: function (res) {
	                               $("#nature_name").val(res.nature_name || '');
	                               $("#prob_name").val(res.prob_name || '');
	                               $("#case_name").val(res.case_name || '');
	                               $("#case_no").val(res.case_no || '');
	                               $("#join_doc").val(res.join_doc || '');
	                               $("#opinion").val(res.opinion || '');
	                               $("#prob_reason").val(res.prob_reason || '');
	                               <%--patient_hide_data = {
	                            		is_commit: res.is_commit || ''
	                               }--%>
	                               patient_hide_data.is_commit = res.is_commit || '';
                               }  
                     	  }),
                     	 <%--patient_hide_data = {
                    			  patient: res.patient || '',
                    			  plainter: res.plainter || '',
                    			  plaint_tel: res.plaint_tel || '',
                    			  patient_ref: res.patient_ref || '',
                    			  plaint_date: res.plaint_date || '',
                    			  occ_date: res.occ_date || '',
                    			  content: res.content || ''
                          };--%>
                          patient_hide_data.patient = res.patient || '';
                          patient_hide_data.plainter = res.plainter || '';
                          patient_hide_data.plaint_tel = res.plaint_tel || '';
                          patient_hide_data.patient_ref = res.patient_ref || '';
                          patient_hide_data.plaint_date = res.plaint_date || '';
                          patient_hide_data.occ_date = res.occ_date || ''
                          patient_hide_data.content = res.content || '';
                          $("#patient").val(res.patient || '');
                          $("#plainter").val(res.plainter || '');
                          $("#plaint_tel").val(res.plaint_tel || '');
                          $("#patient_ref").val(res.patient_ref || '');
                          $("#plaint_date").val(res.plaint_date || '');
                          $("#occ_date").val(res.occ_date || '');
                          $("#content").val(res.content || '');
                      },
                  })
              }
	
	});
		
		
		$("#add").click(function() {
	    	 grid.addRow();
	     });
	     $("#submit").click(function() {
	    		if ($("#in_hos_no").val() == "") {
					$.etDialog.error('住院号/门诊号不能为空');
					return;
				}
	    		var formPara = {
	    				in_hos_no : in_hos_no.getValue(),
	    				   occ_date: patient_hide_data.occ_date,
	   	                plaint_date: patient_hide_data.plaint_date,
					};
	    		$.etDialog.confirm('确定提交?', function() {
					ajaxPostData({
						url : 'confirmHrDealAdd.do',
						data : formPara,
						success : function() {
							var parentFrameName = parent.$.etDialog.parentFrameName;
	                       	var parentWindow = parent.window[parentFrameName];
	                       	parentWindow.query();
	                       	var curIndex = parent.$.etDialog.getFrameIndex(window.name);
	    					parent.$.etDialog.close(curIndex);
						}
					})
				});
	     });
	 	$("#close").click(function() {

         	var curIndex = parent.$.etDialog.getFrameIndex(window.name);
           	parent.$.etDialog.close(curIndex);
          
		})
	   //撤回
			$("#cancel").click(function() {

				
				if ($("#in_hos_no").val() == "") {
					$.etDialog.error('住院号/门诊号不能为空');
					return;
				}
				if(patient_hide_data.is_commit == 0){
					$.etDialog.msg("已经是未提交状态");
					return;
				}
	    		var formPara = [{
	    			in_hos_no : in_hos_no.getValue(),
	    			occ_date: patient_hide_data.occ_date,
	   	        	plaint_date: patient_hide_data.plaint_date,
	   	        	is_commit: patient_hide_data.is_commit
				}];
	    		$.etDialog.confirm('确定撤回?', function() {
					ajaxPostData({
						url : 'reConfirmHrDeal.do',
						data : {paramVo: JSON.stringify(formPara)},
						success : function() {
							var parentFrameName = parent.$.etDialog.parentFrameName;
	                       	var parentWindow = parent.window[parentFrameName];
	                       	parentWindow.query();
	                       	var curIndex = parent.$.etDialog.getFrameIndex(window.name);
	    					parent.$.etDialog.close(curIndex);
						}
					})
				});

			});
		$("#remove").click(function() {
		      var selectData = grid.selectGet();
	            if (selectData.length === 0) {
	                $.etDialog.error('请选择行');
	                return;
	            }else{
	            	var param = [];
				selectData.forEach(function(item) {
					param.push({
						occ_date : $("#occ_date").val(),
						plaint_date : $("#plaint_date").val(),
						in_hos_no : $("#in_hos_no").val()
					});
				})
				ajaxPostData({
					url : 'deleteDealDetail.do',
					data : {
						paramVo : JSON.stringify(param)
					},
					success : function() {
						 grid.deleteRows(selectData);
					}
				})
	                   
	            }
	     });
		// 验证grid重复数据
		// data = grid.gatAllData()
		// ["col1", "col2",...]
		function checkRepeat(data, cols){
			var len = data.length;
			var len2 = cols.length;
			var flag = true;
			for(var i = 0; i < len - 1; i++){
				var a1 = data[i];
				for(var j = i + 1; j < len; j++){
					var a2 = data[j];
					var count = 0;
					for(var k = 0; k < len2; k++){
						if(a1[cols[k]] == a2[cols[k]]){
							count++;
							if(count == len2){ flag = false; break; }
						}
					}
					if(!flag){ break; }
				}
				if(!flag){ break; }
			}
			return flag;
		}
		$("#save").click(function() {
	        var gridAllData = grid.getAllData();
	        if(!checkRepeat(gridAllData, ["dept_code", "emp_id"])){
	        	$.etDialog.error("责任科室与责任人不能完全重复");
	        	return;
	        }
	        if(gridAllData!=null && gridAllData.length!=0){
            gridAllData.forEach(function (item) {
          	if(item.dept_code!=null){
          		item.dept_id =item.dept_code.split('@')[1];
          		item.occ_date=patient_hide_data.occ_date;
          		item.plaint_date=patient_hide_data.plaint_date;
          	}
          })
	        }
          var data = {
        		  
	                // 表单 填写的
	                in_hos_no: in_hos_no.getValue(),
	                deal_type: $("#deal_type").val(),
	                damage: $("#damage").val(),
	                other: $("#other").val(),
	                // 表单 带出来的
	                occ_date: patient_hide_data.occ_date,
	                plaint_date: patient_hide_data.plaint_date,
	                // 表格
	        
	                Param: JSON.stringify(gridAllData),
	            };
	            ajaxPostData({
	               url: 'updateDeal.do',
	                data: data,
	                success: function () {
	                	var parentFrameName = parent.$.etDialog.parentFrameName;
                       	var parentWindow = parent.window[parentFrameName];
                       	parentWindow.query();
	                },
	                delayCallback: true
	            })
	     });
	}
     
	   var initGrid = function () {
           var columns = [
               { display: '责任科室', name: 'dept_name', width: 120,
               	editor: {
                    type: 'select',
                    keyField: 'dept_code',
                    url:'../../queryHosDeptSelect.do?isCheck=false',
                    change: function (value) {
                    	grid.getColumns()[2].editor.url ='../../queryEmpSelect.do?isCheck=false&dept_code='+value.dept_code;
                    }
                    

                }  },
               { display: '责任人', name: 'emp_name', width: 120 ,	
                	editor: {
                        type: 'select',
                        keyField: 'emp_id',
                        url:'../../queryEmpSelect.do?isCheck=false',
                    } },
               { display: '责任人性质', name: 'emp_nature', width: 120 },
               { display: '承担比例', name: 'ratio', width: 120 },
               { display: '扣款金额', name: 'damage', width: 120 },
               { display: '处理意见', name: 'note', width: 120 }
 
           ];
           var paramObj = {
               height: '100%',
               inWindowHeight: true,
               checkbox: true,
               showTitle: true,
               editable: true,
               columns: columns,
               dataModel: {
                   url: 'queryDealDetail.do?isCheck=false&in_hos_no='+"${in_hos_no}"
               },
           };
           grid = $("#mainGrid").etGrid(paramObj);
       };
	$(function() {
		// initValidate();
		initForm();
		initGrid();
	})
</script>
</head>

<body>
	<div class="main">
	<span class="flex-item-1 align-right" style="line-height: 30px;">投诉基本信息</span>
	<div class="flex-item-1 single-block">
		<table class="table-layout" style="width: 100%;">
			<tr>
				<td class="label">门诊号：</td>
				<td class="ipt">
				 <select id="in_hos_no" style="width:120px;" disabled></select>
			     </td>
				<td class="label">患者名称：</td>
				<td class="ipt"><input id="patient" type="text" style="width:120px;" disabled/></td>
				<td class="label">投诉人：</td>
				<td class="ipt"><input id="plainter" type="text" style="width:120px;" disabled/></td>
			</tr>
			<tr>
				<td class="label">投诉人电话：</td>
				<td class="ipt"><input id="plaint_tel" type="text" style="width:120px;" disabled/></td>
				<td class="label">与患者关系：</td>
				<td class="ipt"><input id="patient_ref" type="text" style="width:120px;" disabled/></td>
				<td class="label">投诉日期：</td>
				<td class="ipt"><input id="plaint_date" type="text" style="width:120px;" disabled/></td>
			</tr>
			<tr>
				<td class="label">发生时间：</td>
				<td class="ipt"><input id="occ_date" type="text" style="width:120px;" disabled/></td>
				<td class="label">投诉内容：</td>
				<td class="ipt"> <textarea name="content" id="content" style="width:120px;" disabled></textarea></td>
			</tr>
		</table>
		</div>
	</div>
	<div class="main">
	<span class="flex-item-1 align-right" style="line-height: 30px;">投诉调查情况</span>
	<div class="flex-item-1 single-block">
		<table class="table-layout" style="width: 100%;">
			<tr>
				<td class="label">问题定性：</td>
				<td class="ipt">
				  <input id="nature_name"  type="text" style="width:120px;" disabled />
			     </td>
				<td class="label">问题归类：</td>
				<td class="ipt">
				 <input id="prob_name"  type="text" style="width:120px;" disabled />
				</td>
				<td class="label">病例讨论：</td>
				<td class="ipt">
				 <input id="case_name" type="text" style="width:120px;" disabled/>
				</td>
			</tr>
			<tr>
				<td class="label">病案号：</td>
				<td class="ipt">
				 <input id="case_no" type="text" style="width:120px;" disabled/>
			     </td>
				<td class="label">参与人员：</td>
				<td class="ipt">
				 <input id="join_doc" type="text"  style="width:120px;" disabled/>
				</td>
			</tr>
			<tr>
				<td class="label">讨论意见：</td>
				<td class="ipt">
				<textarea name="opinion" id="opinion" style='width:168px' disabled></textarea>
				<td class="label">具体存在问题分析：</td>
				<td class="ipt">
				<textarea name="prob_reason" id="prob_reason" style='width:168px' disabled ></textarea>
			</tr>
			<tr>
				<td class="label">处理方式：</td>
				<td class="ipt">
					 <input id="deal_type" type="text" value="${deal_type}" style="width:120px;"/>
				</td>
				<td class="label">赔款金额：</td>
				<td class="ipt">
					 <input id="damage" type="text"  value="${damage}" style="width:120px;"/>
				</td>
				<td class="label">其他款项：</td>
				<td class="ipt">
					 <input id="other" type="text" value="${other}" style="width:120px;"/>
				</td>
			</tr>
		</table>
	<div class="button-group">
	    <button id="add">添加</button>
	    <button id="remove">删除</button>
		<button id="save">保存</button>
		<button id="submit">提交</button>
		<button id="cancel">撤回</button>
		<button id="close">关闭</button>
	</div>
	<div id="mainGrid"></div>
	</div>
	</div>
</body>

</html>