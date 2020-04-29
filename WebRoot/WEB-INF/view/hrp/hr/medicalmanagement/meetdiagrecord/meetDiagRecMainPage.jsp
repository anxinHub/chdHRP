<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%String path = request.getContextPath();%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <jsp:include page="${path}/resource.jsp">
	    <jsp:param value="dialog,select,grid,datepicker,pageOffice" name="plugins" />
	</jsp:include>
    <script>
        var md_date, MeetDiagRecord_date, MeetDiagRecorder, finder;
        var grid;
        var initFrom = function () {
            md_date = $("#md_date").etDatepicker({
            	onChange: query,
            });
        	dept_code = $("#dept_code").etSelect({
    			url : "../../queryHosDeptSelect.do?isCheck=false",
    			defaultValue : "none",
    			onChange : query
    		});
        	is_commit = $("#is_commit").etSelect({
                 options: [
                     { id: 0, text: '新建' },
                     { id: 1, text: '提交' }
                 ],
                 defaultValue: "none",
                 onChange: query,
             });
        };
        var query = function () {
            var params = [
                          { name: 'md_date', value: md_date.getValue('yyyy-mm-dd') },
                          { name: 'bill_no', value: $("#bill_no").val() },
                          { name: 'patient', value: $("#patient").val() },
                          { name: 'case_no', value: $("#case_no").val() },
                          { name: 'dept_id',value : dept_code.getValue().split('@')[1]}, 
                          { name: 'is_commit',value : is_commit.getValue()},
                         
            ];
            grid.loadData(params,'queryMeetDiagRecord.do');
        };
        var save = function () {
        	var msg="";
        	var ParamVo = [];
     	   var isPass = grid.validateTest({
               required: {
            	   bill_no: true,
            	   rec_date:true,
            	   dept_name:true,
            	   case_no:true
               }
           });
           if (!isPass) {
               return;
           }
            var data = grid.selectGet();
                 if (data.length == 0) {
                     $.etDialog.error('请选择行');
                     return;
                 } else {
                	 var flag = true;
                       $(data).each(function () {
                           var rowdata = this.rowData;
                           if(rowdata.commit_name!="" && rowdata.commit_name!="新建"){
                         		msg+='请选择新建状态修改';
                         		return false;
                         		
                         	}
                           if(rowdata.md_date > rowdata.rec_date){
                        	   flag = false;
                        	   return false;
                           }
                           ParamVo.push(rowdata);
                       })
                       if(!flag){
                    	   $.etDialog.error('申请日期不能小于会诊日期');
                    	   return;
                       }
                   }
                 //验证重复数据
              	if (!grid.checkRepeat(
              			data,
              			['bill_no','rec_date','dept_name','case_no']
              	)		
              	) 
              		 if(!msg){
              			  $.etDialog.error(msg);
                  	   return;
                     }
            ajaxPostData({
                url: 'addMeetDiagRecord.do',
                data: {
                    paramVo: JSON.stringify(ParamVo)
                },
                success: function () {
                	query();
                }
            })
        };
        var add = function () {
        	 grid.addRow();
        };
        // 提交
        var submit = function () {

            var msg="";
            var ParamVo = [];
            var data = grid.selectGet();
            if (data.length == 0) {
                $.etDialog.error('请选择行');
            } else {
                  $(data).each(function () {
                      var rowdata = this.rowData;
                      if(rowdata.is_commit!=0){
                  		msg+='请选择新建状态';
                  		return false;
                  		
                  	}else{
                      ParamVo.push(rowdata);
                  	}
                  });
                  if(msg!=""){
                	  $.etDialog.error(msg);
                	  return;
                  }
                $.etDialog.confirm('确定提交?', function () {
                    ajaxPostData({
                        url: 'confirmMeetDiagRecord.do?isCheck=false',
                        data: {
                            paramVo: JSON.stringify(ParamVo)
                        },
                        success: function () {
                        	 query();
                        }
                    })
                });
            }
        };
        // 撤回
        var cancel = function () {
            var msg="";
            var ParamVo = [];
            var data = grid.selectGet();
            if (data.length == 0) {
                $.etDialog.error('请选择行');
            } else {
                  $(data).each(function () {
                      var rowdata = this.rowData;
                      if(rowdata.is_commit!=1){
                  		msg+='请选择提交状态';
                  		return false;
                  		
                  	}else{
                      ParamVo.push(rowdata);
                  	}
                  });
                  if(msg!=""){
                	  $.etDialog.error(msg);
                	  return;
                  }
                $.etDialog.confirm('确定撤回?', function () {
                    ajaxPostData({
                        url: 'reConfirmHrMeetDiagRecord.do?isCheck=false',
                        data: {
                            paramVo: JSON.stringify(ParamVo)
                        },
                        success: function () {
                        	 query();
                        }
                    })
                });
            }
        };
        var remove = function () {
        	var msg=""
            var selectData = grid.selectGet();
            if (selectData.length === 0) {
                $.etDialog.error('请选择行');
                return;
            }
            var param = [];
            selectData.forEach(function (item) {
            	if(item.rowData.is_commit!=0){
            		msg+='请选择新建状态删除';
            		
            		
            	}else{
				
                param.push({
                	bill_no: item.rowData.bill_no
                });
                }
            })
  		  if(msg!=""){
          	$.etDialog.error('请选择新建状态删除' );
          	return;
          }
				$.etDialog.confirm('确定删除?', function () {
            ajaxPostData({
                 url: 'deleteMeetDiagRecord.do',
                data: { paramVo: JSON.stringify(param) },
                success: function () {
                    grid.deleteRows(selectData);
                }
            })
				})
        };
        var putin =  function () {
        	if(grid.getAllData()==null){
        		$.etDialog.error("请先查询数据！");
    			return;
    		}
        	var heads={
            		 /* "isAuto":true,//系统默认，页眉显示页码
            		"rows": [
        	          {"cell":0,"value":$('#dept_name').val()},
        	          {"cell":1,"value":dept_code},
            		] */  }; 
        	var printPara={
              		title: " 全院大会诊记录",//标题
              		columns: JSON.stringify(grid.getPrintColumns()),//表头
              		class_name: "com.chd.hrp.hr.service.medicalmanagement.HrMeetDiagRecordService",
           			method_name: "queryRecordByPrint",
           			bean_name: "hrMeetDiagRecordService",
           			heads: JSON.stringify(heads),//表头需要打印的查询条件,可以为空
           			foots: '',//表尾需要打印的查询条件,可以为空 
               	};
        	console.log(grid);
        	console.log(grid.getUrlParms());
        	
             	$.each(grid.getUrlParms(),function(i,obj){
           			printPara[obj.name]=obj.value;
            	}); 
             	
            	officeGridPrint(printPara);
        };
        var putout = function () {
        	exportGrid(grid);
        };
     
       
        var initGrid = function () {
            var columns = [
                { display: '会诊单号', name: 'bill_no', width: 120,
                	 editor: {
                        type: 'grid',
                        columns: [
                        	 { display: '会诊单号', name: 'bill_no', width: 120 },
                             { display: '病人姓名', name: 'patient', width: 120 },
                             { display: '病案号', name: 'case_no', width: 120 },
                             { display: '科室', name: 'dept_name', width: 120 },
                             { display: '床位', name: 'bed_no', width: 120 },
                             { display: '诊断', name: 'diagnose', width: 120 },
                             { display: '会诊日期', name: 'md_date', width: 100},
                             { display: '申请原因', name: 'reason', width: 120 },
                             { display: '', name: 'dept_id', hidden: true },
                        ],
                        width: '700px',
                        height: '205px',
                        dataModel: {
                            url: 'queryMeetDiagRecordApp.do?isCheck=false',
                        }, },
                        editable : function(col){
                        	if(col.rowData){
                     		if(col.rowData.hos_id!=null){
                     			return false;
                     		}
                     		return true;
                     	}else{
                     		return true;
                     	}
                     }
				
                
                
                },
                { display: '病人姓名', name: 'patient', width: 120,editable: false },
                { display: '病案号', name: 'case_no', width: 120,editable: false },
                { display: '科室', name: 'dept_name', width: 120 ,editable: false},
                { display: '床位', name: 'bed_no', width: 120,editable: false },
                { display: '诊断', name: 'diagnose', width: 120,editable: false },
                { display: '会诊日期', name: 'md_date', width: 100,editable: false},
                { display: '申请原因', name: 'reason', width: 120,editable: false },
                { display: '申请日期', name: 'rec_date', width: 120,
              	   editor: {
                       type: 'date',
                       dateFormat: 'yy-mm-dd',
                   } },
                { display: '会诊结果', name: 'md_result', width: 120 },
                { display: '是否提交', name: 'commit_name', width: 120 },
               
            ];
            var paramObj = {
                height: '100%',
                inWindowHeight: true,
                checkbox: true,
                editable: true,
                wrap: true,
                hwrap: true,
                dataModel: {
                    // url: ''
                },
                columns: columns,
                toolbar: {
            				items : [ {
            					type : 'button',
            					label : '查询',
            					listeners : [ {
            						click : query
            					} ],
            					icon : 'search'
            				}, {
            					type : 'button',
            					label : '保存',
            					listeners : [ {
            						click : save
            					} ],
            					icon : 'save'
            				}, {
            					type : 'button',
            					label : '添加',
            					listeners : [ {
            						click : add
            					} ],
            					icon : 'add'
            				}, {
            					type : 'button',
            					label : '删除',
            					listeners : [ {
            						click : remove
            					} ],
            					icon : 'delete'
            				},     
            				
					          {
									type : 'button',
									label : '提交',
									listeners : [ {
										click : submit
									} ],
									icon : 'submit'
								}, {
									type : 'button',
									label : '撤回',
									listeners : [ {
										click : cancel
									} ],
									icon : 'cancel'
								<%--}, {
									type : 'button',
									label : '导出',
									listeners : [ {
										click : putout
									} ],
									icon : 'export'--%>
								}, {
									type : 'button',
									label : '打印',
									listeners : [ {
										click : putin
									} ],
									icon : 'export'
								}

								]
							}
						};
						grid = $("#mainGrid").etGrid(paramObj);

						$("#mainGrid").on('click', '.openUpdate', function() {
							var rowIndex = $(this).attr('row-index');
							var currentRowData = grid.getAllData()[rowIndex];
							openUpdate(currentRowData);
						})
					};

					$(function() {
						initFrom();
						initGrid();
						query();
					})
				</script>
</head>

<body>
    <div class="main">
        <table class="table-layout">
            <tr>
                <td class="label">申请日期：</td>
                <td class="ipt">
                    <input id="md_date" type="text" />
                </td>
                <td class="label">病人姓名：</td>
                <td class="ipt">
                    <input id="patient" type="text" />
                </td>
                <td class="label" style="width:120px;">病案号：</td>
                <td class="ipt">
                    <input id="case_no" type="text" />
                </td>
            </tr>
            <tr>
               <td class="label">发生科室：</td>
                <td class="ipt">
                    <select id="dept_code" style="width:180px;"></select>
                </td>
                <td class="label">会诊单号：</td>
                <td class="ipt">
                  <input id="bill_no" type="text" />
                </td>
                <td class="label">状态：</td>
                <td class="ipt">
                <select id="is_commit" style="width:180px;"></select>
                </td>
            </tr>
        </table>
    </div>
    <div id="mainGrid"></div>
</body>

</html>