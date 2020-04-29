<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%String path = request.getContextPath();%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html style="overflow:hidden;"  xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="${path}/inc_jquery_1.9.0.jsp"/>
<script type="text/javascript">
var grid;
var fileName;
$(function(){
	 loadHead(null);
    function plupload(){  
     $("#uploader").pluploadQueue({           
            runtimes : 'flash,html5,gears,browserplus,silverlight,html4',           
            url : "readIncomeitemIncreaseDataFiles.do?isCheck=false",    
            chunk_size : '1mb',           
            filters : {               
                max_file_size : '2048kb',  
                mime_types: [  
                    {title : "excel files", extensions : "xls,xlsx"}
                ]  
            },  
            flash_swf_url : '<%=path%>/lib/plupload-2.1.3/js/Moxie.swf',                 
   	     	silverlight_xap_url : '<%=path%>/lib/plupload-2.1.3/js/Moxie.xap',
								// 参数   可不加
								init : {
									FileUploaded : function(uploader, file,
											responseObject) {
										var res = responseObject.response;
										var json = eval('(' + res + ')');
										if (json.Rows != null
												&& json.Rows != "") {
											$("#uploader").css("display",
													"none");
											$("#maingrid").css("display",
													"block");
											$("#Reload").css("display", "none");
											grid.loadData(json);
										} else {
											$("#message").html("导入成功！");
										}
									}
								}
							});
		}

		plupload();

		$('#Reload').click(function() {
			$("#uploader").css("display", "block");
			$("#maingrid").css("display", "none");
			plupload();
		});
	});
	function getJsonObjLength(jsonObj) {
		var Length = 0;
		for ( var item in jsonObj) {
			Length++;
		}
		return Length;
	}
	function loadHead() {

		grid = $("#maingrid").ligerGrid(
				{
					columns : [
							{
								display : '核算年度',
								name : 'acct_year',
								align : 'left',
								editor : {
									type : 'text'
								}
							},
							{
								display : '核算月份',
								name : 'acct_month',
								align : 'left',
								editor : {
									type : 'text'
								}
							},
							{
								display : '收入项目',
								name : 'income_item_code',
								align : 'left',
								editor : {
									type : 'text'
								}
							},{
								display : '医疗收入',
								name : 'income_money',
								align : 'left',
								editor : {
									type : 'text'
								}
							},
							{
								display : '计提收入',
								name : 'income_ret_money',
								align : 'left',
								editor : {
									type : 'text'
								}
							},
							{
								display : '错误说明',
								name : 'error_type',
								align : 'left',
								width : '35%',
								render : function(rowdata, rowindex, value) {
									return "<span style='color:red'>"
											+ rowdata.error_type + "</span>";
								}
							}

					],
					width : '99%',
					height : '370',
					enabledEdit : true,
					usePager : false,
					rownumbers : true,
					selectRowButtonOnly : true,
					toolbar : {
						items : [ {
							text : '保存',
							id : 'save',
							click : itemclick,
							icon : 'add',
						}, {
							line : true
						}, {
							text : '删除',
							id : 'delete',
							click : itemclick,
							icon : 'delete'
						}, {
							line : true
						}

						]
					}
				});
		gridManager = $("#maingrid").ligerGetGridManager();
		formatYesNo();
	}
	function itemclick(item) {
		if (item.id) {
			switch (item.id) {
			case "save":
				var data = gridManager.getData();
				ajaxJsonObjectByUrl("addBatchIncomeitemIncreaseDataDict.do?isCheck=false", {
					ParamVo : JSON.stringify(data)
				}, function(responseData) {
					if (responseData.state == "true") {
						$("#uploader").css("display", "block");
						$("#maingrid").css("display", "none");
						$("#Reload").css("display", "block");
						plupload();
					} else if (responseData.state == "err") {

						$("#uploader").css("display", "block");
						$("#maingrid").css("display", "none");
						$("#Reload").css("display", "block");
						plupload();

					} else {

						grid.loadData(responseData);

					}

				});

				return;
			case "delete":
				var data = gridManager.getCheckedRows();

				if (data.length == 0) {
					$.ligerDialog.error('请选择行');
				} else {
					var data = gridManager.deleteSelectedRow();
				}
				return;
			}
		}

	}
</script>

</head>

<body>
	<div id="pageloading" class="l-loading" style="display: none"></div>
	<div style="width: 750px; margin: 0px auto;">
		<div id="uploader"></div>
	</div>

	<div id="message" align="center" style="font-size: 18px; margin-top: -20px; color: red;"></div>
	<div id="maingrid" style="display: none; padding-top: 20px"></div>
</body>
</html>
