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
<style type="text/css">
.table-layout td.label{width:102px;}
</style>
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
              defaultValue: "none",
              onChange: function (value) {
                  ajaxPostData({
                      url: 'queryinHosNoDetail.do?isCheck=false',
                      data: {
                    	  in_hos_no: value
                      },
                      success: function (res) {
                    	  patient_hide_data = {
                    			  patient: res.patient || '',
                    			  plainter: res.plainter || '',
                    			  plaint_tel: res.plaint_tel || '',
                    			  patient_ref: res.patient_ref || '',
                    			  plaint_date: res.plaint_date || '',
                    			  occ_date: res.occ_date || '',
                    			  content: res.content || '',
                          };
                          $("#patient").val(res.patient || '');
                          $("#plainter").val(res.plainter || '');
                          $("#plaint_tel").val(res.plaint_tel || '');
                          $("#patient_ref").val(res.patient_ref || '');
                          $("#plaint_date").val(res.plaint_date || '');
                          $("#occ_date").val(res.occ_date || '');
                          $("#content").val(res.content || '');
                      },
                  })
                  var parentFrameName = parent.$.etDialog.parentFrameName;
                 	var parentWindow = parent.window[parentFrameName];
                 	parentWindow.query();
              }
	
	
	});
		prob_nature = $("#prob_nature").etSelect({
            url: "../../queryProbNature.do?isCheck=false",
            defaultValue: "none",
		});
		prob_type = $("#prob_type").etSelect({
            url: "../../queryProbType.do?isCheck=false",
            defaultValue: "none",
		});
		case_discuss = $("#case_discuss").etSelect({
			options : [ {
				id : 0,
				text : '无'
			}, {
				id : 01,
				text : '有'
			} ],
			defaultValue : "none",
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
						url : 'confirmHrResearchAdd.do?isCheck=false',
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
	   //撤回
			$("#cancel").click(function() {
				var formPara = {
	    				in_hos_no : in_hos_no.getValue(),
	    				   occ_date: patient_hide_data.occ_date,
	   	                plaint_date: patient_hide_data.plaint_date,
					};
				
					$.etDialog.confirm('确定撤回?', function() {
						ajaxPostData({
							url : 'reConfirmHResearchAdd.do?isCheck=false',
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
						url : 'deleteResearchDetail.do',
						data : {
							paramVo : JSON.stringify(param)
						},
						success : function() {
							 grid.deleteRows(selectData);
						}
					})
	            }
	     });
		$("#close").click(function() {

         	var curIndex = parent.$.etDialog.getFrameIndex(window.name);
           	parent.$.etDialog.close(curIndex);
          
		})
		
		// 验证grid重复数据
		// data = grid.gatAllData()
		// ["col1", "col2",...]
		function checkRepeat(data, cols){
			var len = data.length;
			var len2 = cols.length;
			var flag = true;
			for(var i = 0; i < len; i++){
				var a1 = data[i];
				for(var j = i + 1; j < len; j++){
					var a2 = data[j];
					var count = 0;
					for(var k = 0; k < len2; k++){
						console.log(a1[cols[k]], a2[cols[k]], a1[cols[k]] == a2[cols[k]]);
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
			   var isPass = grid.validateTest({
                   required: {
                	   dept_id: true,
                	   emp_name :true
                   }
               });
               if (!isPass) {
                   return;
               }
			if(in_hos_no.getValue() == ''){
				$.etDialog.error('门诊号不能为空')
				return ; 
			}
			
	        var gridAllData = grid.getAllData();
	        if(gridAllData == null) {
                $.etDialog.error('请选择责任科室');
                return;
            }else{
            	if(!checkRepeat(gridAllData, ["dept_code", "emp_id"])){
    	        	$.etDialog.error("责任科室与责任人不能完全重复");
    	        	return;
    	        }
	            gridAllData.forEach(function (item) {
	          	if(item.dept_code!=null){
	          		item.dept_id =item.dept_code.split('@')[1],
	          		item.occ_date=patient_hide_data.occ_date,
	          		item.plaint_date=patient_hide_data.plaint_date
	          	}
	          })
	        }
          var data = {
        		  
	                // 表单 填写的
	                in_hos_no: in_hos_no.getValue(),
	                prob_nature: prob_nature.getValue(),
	                prob_type: prob_type.getValue(),
	                case_discuss: case_discuss.getValue(),
	                case_no: $("#case_no").val(),
	                join_doc: $("#join_doc").val(),
	                opinion: $("#opinion").val(),
	                prob_reason: $("#prob_reason").val(),
	                // 表单 带出来的
	                occ_date: patient_hide_data.occ_date,
	                plaint_date: patient_hide_data.plaint_date,
	                // 表格
	        
	                Param: JSON.stringify(gridAllData),
	            };
	            ajaxPostData({
	               url: 'addResearch.do',
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
               { display: '责任科室', name: 'dept_id', width: 120,
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
               { display: '事发时科内成员', name: 'other_member', width: 120 },
               { display: '依据', name: 'gist', width: 120 },
               { display: '依据附件', name: 'gist_accessory', width: 120, width: 200,
                   fileModel: {
                       keyField: 'file',
                       type:'file',
                       url: 'addAccessory.do?isCheck=false'
                   }
 },
           ];
           var paramObj = {
               height: '100%',
               inWindowHeight: true,
               checkbox: true,
               editable: true,
               columns: columns,
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

<body style="overflow:auto;">
	<div class="main">
	<span class="flex-item-1 align-right" style="line-height: 30px;">投诉基本信息</span>
	<div class="flex-item-1 single-block" style="margin:0; padding: 0;">
		<table class="table-layout" style="width: 100%;">
			<tr>
				<td class="label">门诊号：</td>
				<td class="ipt">
				 <select id="in_hos_no" style="width:180px;"/>
			     </td>
				<td class="label">患者名称：</td>
				<td class="ipt"><input id="patient" type="text" disabled/></td>
				<td class="label">投诉人：</td>
				<td class="ipt"><input id="plainter" type="text" disabled/></td>
			</tr>
			<tr>
				<td class="label">投诉人电话：</td>
				<td class="ipt"><input id="plaint_tel" type="text" disabled/></td>
				<td class="label">与患者关系：</td>
				<td class="ipt"><input id="patient_ref" type="text" disabled/></td>
				<td class="label">投诉日期：</td>
				<td class="ipt"><input id="plaint_date" type="text" disabled/></td>
			</tr>
			<tr>
				<td class="label">发生时间：</td>
				<td class="ipt"><input id="occ_date" type="text" disabled/></td>
				<td class="label">投诉内容：</td>
				<td class="ipt"> <textarea name="content" id="content" style='width:180px' disabled></textarea></td>
			</tr>
		</table>
		</div>
	</div>
	<div class="main" >
	<span class="flex-item-1 align-right" style="line-height: 30px;">投诉调查情况</span>
	<div class="flex-item-1 single-block" style="padding:0; margin: 0;">
		<table class="table-layout" style="width: 100%;">
			<tr>
				<td class="label">问题定性：</td>
				<td class="ipt">
				 <select id="prob_nature" style="width:180px;"></select>
			     </td>
				<td class="label">问题归类：</td>
				<td class="ipt">
				 <select id="prob_type" style="width:180px;"></select>
				</td>
				<td class="label">病例讨论：</td>
				<td class="ipt">
				 <select id="case_discuss" style="width:180px;"></select>
				</td>
			</tr>
			<tr>
				<td class="label">病案号：</td>
				<td class="ipt">
				 <input id="case_no" type="text"/>
			     </td>
				<td class="label">参与人员：</td>
				<td class="ipt">
				 <input id="join_doc" type="text" style="width:180px;"/>
				</td>
			</tr>
			<tr>
				<td class="label">讨论意见：</td>
				<td class="ipt">
				<textarea name="opinion" id="opinion" style='width:180px' ></textarea>
				<td class="label">具体存在问题分析：</td>
				<td class="ipt">
				<textarea name="prob_reason" id="prob_reason" style='width:180px' ></textarea>
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