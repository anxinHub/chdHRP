<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<% String path = request.getContextPath(); %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link href="<%=path%>/lib/ligerUI/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css" />
<link href="<%=path%>/lib/ligerUI/skins/ligerui-icons.css" rel="stylesheet" type="text/css" />
<link href="<%=path%>/lib/SpreadJS/css/gcspread.sheets.9.40.20161.0.css" rel="stylesheet" type="text/css" />
<link href="<%=path%>/lib/Z-tree/css/zTreeStyle/zTreeStyle.css" rel="stylesheet" type="text/css" />
<script src="<%=path%>/lib/jquery/jquery-1.9.0.min.js" type="text/javascript"></script>
<script src="<%=path%>/lib/json2.js" type="text/javascript"></script>
<script src="<%=path%>/lib/hrp.js" type="text/javascript"></script>
<script src="<%=path%>/lib/ligerUI/js/core/base.js" type="text/javascript"></script>
<script src="<%=path%>/lib/ligerUI/js/ligerui.all.js" type="text/javascript"></script>
<script src="<%=path%>/lib/jquery-validation/jquery.validate.min.js" type="text/javascript"></script>
<script src="<%=path%>/lib/jquery-validation/jquery.metadata.js" type="text/javascript"></script>
<script src='<%=path%>/lib/SpreadJS/scripts/gcspread.sheets.all.9.40.20161.0.min.js' type='text/javascript'></script>
<script src="<%=path%>/lib/Z-tree/js/jquery.ztree.core-3.5.js" type="text/javascript"></script>
<script src="<%=path%>/lib/Z-tree/js/jquery.ztree.excheck-3.5.js" type="text/javascript"></script>
<script src="<%=path%>/lib/Z-tree/js/jquery.ztree.exedit-3.5.js" type="text/javascript"></script>
<script type="text/javascript">
	var mainform = null;
	var formManager = null;
	var tree;
	var setting;
	var groupicon = "<%=path%>/lib/ligerUI/skins/icons/communication.gif";
	var dialog = frameElement.dialog; //调用页面的dialog对象(ligerui对象)
	var sheet = dialog.get('data').sheet;
	/* var activeRowIndex = sheet.getActiveRowIndex();
	 var activeColumnIndex = sheet.getActiveColumnIndex(); */
	var spreadNS = dialog.get('data').spreadNS;
	//var comment = new spreadNS.Comment();
	var commJson = null;
	$(function() {
		$("#layout1").ligerLayout({
			leftWidth : 210,
			allowLeftResize : true
		});
		setting = {
			data : {
				simpleData : {
					enable : true
				}
			},
			treeNode : {
				open : true
			},
			callback : {
				onClick : myClick
			}
		};
		mainform = $("form");
		myClick();
		$("#treeDiv").css("height", $(window).height() - 28);
		$("#ele_code").ligerTextBox({
			width : 460,
			readonly : true
		});
		$("#ele_note").ligerTextBox({
			width : 460,
			readonly : true
		});
		$(':button').ligerButton({
			width : 100
		});

	});

	var eleSelCode;
	var eleSelParaArray;

	function myClick() {
		/* var node = tree.getSelectedNodes()[0];
		if(node==null || node.pId==null || node.ele_type==null){
			return;
		} */

		var para = {
			ele_code : '${ele_code}',
			is_stop : 0
		};

		ajaxJsonObjectByUrl("../../../acc/superReport/querySuperReportParaByEle.do?isCheck=false", para,
			function(responseData) {
				//console.log(responseData.Rows)
				//if(node.ele_type==1 || node.ele_type==2 || node.ele_type==3 || node.ele_type==4 || node.ele_type==5){
				loadForm(responseData.Rows);
				/* }else{
					if(formManager!=null)formManager._setFields({});
				} */
			}
		);

	}

	function saveAccItemCal() {
		var paraValueStr = "";
		var paraValueStr_eng = "";
		var data = formManager.getData();
		if (mainform.valid()) {
			var eleCode = "";
			//if(node.ele_type==1){
			//函数
			//eleCode="REP(\""+$("#ele_code").val()+"\"";
			/* }else if(node.ele_type==2){
				//存储过程
				eleCode="REP(\""+$("#ele_code").val()+"\"";
			}else if(node.ele_type==4){
				//自定义SQL
				eleCode="REP(\""+$("#ele_code").val()+"\"";
			}else if(node.ele_type==5){
				//系统函数
				eleCode="REP(\""+$("#ele_code").val()+"\"";
			} */

			//paraValueStr=paraValueStr+eleCode;
			var isVlidate = true;
			for ( var d in data) {
				if (data[d] == "undefined") {
					isVlidate = false;
					return false;
				}
				isdh = true;
			}

			$.each(formManager.form[0], function(i, v) {
				if (i % 2 == 0) {
					paraValueStr = paraValueStr + ",\'" + v.value + "\'";
				} else {
					paraValueStr_eng = paraValueStr_eng + ",\'" + v.value + "\'";
				}
			})

			if (!isVlidate) {
				$.ligerDialog.error(d + "，没有获取到下拉框的值，确保该字典的SQL可正确执行！");
				return false;
			}
			paraValueStr = "(" + paraValueStr.substring(1, paraValueStr.length)
					+ ")|("
					+ paraValueStr_eng.substring(1, paraValueStr_eng.length)
					+ ")";
		}

		return paraValueStr;

	}

	//创建表单结构
	var isInitComBox = true;
	function loadForm(json) {
		isInitComBox = true;
		$.metadata.setType("attr", "validate");
		mainform.validate({
			//调试状态，不会提交数据的
			debug : false,
			errorPlacement : function(lable, element) {
				if (element.hasClass("l-textarea")) {
					element.addClass("l-textarea-invalid");
				} else if (element.hasClass("l-text-field")) {
					element.parent().addClass("l-text-invalid");
				}
				$(element).removeAttr("title").ligerHideTip();
				$(element).attr("title", lable.html()).ligerTip();
			},
			success : function(lable) {
				var element = $("#" + lable.attr("for"));
				if (element.hasClass("l-textarea")) {
					element.removeClass("l-textarea-invalid");
				} else if (element.hasClass("l-text-field")) {
					element.parent().removeClass("l-text-invalid");
				}
				$(element).removeAttr("title").ligerHideTip();
			},
			submitHandler : function() {
				alert("Submitted!");
			}
		});

		var fieldJosn = [];
		var filedJsonValue = {};
		//存初始化参数值
		if (eleSelParaArray && eleSelCode == '${ele_code}') {
			$.each(json, function(i, obj) {
				filedJsonValue[obj.para_code] = eleSelParaArray[i];
			});
		}

		$.each(json, function(i, obj) {
			var isNewline = false;
			var group = "";

			/* if(i==0){
				group="参数信息";
			} */
			if (i % 2 == 0) {
				isNewline = true;
			}

			var paraJson = JSON.parse(obj.para_json);
			var inputWidth = 170;
			var isRequired = false;
			//定义宽度
			if (paraJson != null && paraJson.width != null && paraJson.width != "") {
				inputWidth = paraJson.width;
			}
			//是否必填
			if (paraJson != null && paraJson.required != null && paraJson.required == "true") {
				isRequired = true;
			}

			if (obj.para_type == 3 || obj.para_type == 4) {
				//下拉框、检索框
				var key = "";
				var paras = "";

				if (eleSelParaArray && eleSelCode == '${ele_code}' && obj.para_type == 4) {
					key = eleSelParaArray[i];
				}

				if (paraJson != null && paraJson.para != null && paraJson.para != "") {
					$.each(paraJson.para.split(","), function(i, p) {
						paras = paras + p + "@"
								+ (filedJsonValue[p] == undefined ? "" : filedJsonValue[p])
								+ ",";
					});

					paras = paras.substring(0, paras.length - 1)
				}
				
				fieldJosn.push({
					display : obj.para_name,
					name : obj.para_code,
					comboboxName : obj.para_code + "_name",
					type : "combobox",
					width : inputWidth,
					validate : {
						required : isRequired
					},
					editor : {
						parms : {
							dict_code : obj.dict_code,
							key : key,
							paras : paras
						},
						url : "../../../acc/superReport/querySuperReportParaSelectData.do.do?isCheck=false",
						valueField : "id",
						textField : "text",
						selectBoxWidth : inputWidth,
						setTextBySource : true,
						//width: 300,
						autocomplete : true,
						highLight : true,
						keySupport : true,
						delayLoad : false, //是否延时加载
						triggerToLoad : false, //是否在点击下拉按钮时加载
						async : false,
						onSuccess : function(data) {
							//没有公式的情况下，默认回写参数值，所以不需要默认加载第一个值了
							if (data.length > 0 && isInitComBox && eleSelCode != '${ele_code}') {
								if (data[0].id != undefined && data[0].id != "" && data[0].id != null) {
									this.setValue(data[0].id);
									this.setText(data[0].text);
									filedJsonValue[obj.para_code] = data[0].id;
								}
							}
						},
						onBeforeOpen : function(selectValue) {
							if (paraJson != null && paraJson.para != null && paraJson.para != "") {
								var $combox = this;
								$.each(paraJson.para.split(","), function(i, p) {
									$combox.setParm(p, $("[name=" + p + "]").val());
								});
								$combox.reload();
							}
						},
						onBeforeSelect : function(selectValue) {
							if (selectValue == $("[name=" + obj.para_code + "]").val()) {
								return;
							}
							getParaObj(json, obj);
						},
						onSelected : function(selectValue) {

						}
					},
					newline : isNewline,
					group : group,
					groupicon : groupicon
				});

			} else if (obj.para_type == 5) {
				//复选框
				fieldJosn.push({
					display : obj.para_name,
					name : obj.para_code,
					type : "checkbox",
					width : 20,
					validate : {
						required : isRequired
					},
					newline : isNewline,
					group : group,
					groupicon : groupicon
				});
			} else {
				//文本框
				fieldJosn.push({
					display : obj.para_name,
					name : obj.para_code,
					type : "text",
					width : inputWidth,
					validate : {
						required : isRequired
					},
					newline : isNewline,
					group : group,
					groupicon : groupicon
				});
			}
		});

		formManager = mainform.ligerForm({
			inputWidth : 170,
			labelWidth : 80,
			space : 40,
			fields : fieldJosn
		});

		if (eleSelCode == '${ele_code}') {
			var count = 0;
			var paraValJson = "";
			var data = formManager.getData();
			for ( var d in data) {
				if (d == "ele_code" || d == "ele_note") {
					continue;
				}
				paraValJson = paraValJson + "\"" + d + "\":\""
						+ eleSelParaArray[count] + "\",";
				count++;
			}
			if (paraValJson != "") {
				paraValJson = "{" + paraValJson.substr(0, paraValJson.length - 1) + "}";
				//var paraValeMap={"acc_month":"本期间","acc_year":"本年度","con_acc":0,"copy_code":"001","cur_code":"RMB","group_id":"2","hos_id":"1","subj_id":"1001"};
				formManager.setData(JSON.parse(paraValJson));
			}
		}

		isInitComBox = false;
	}

	//根据当前参数编码，查找影响的级联，并且清空数据。
	function getParaObj(json, obj) {
		$.each(json, function(i, j) {
			var paraJson = JSON.parse(j.para_json);
			if (paraJson != null && paraJson.para != null && paraJson.para != "") {
				$.each(paraJson.para.split(","), function(z, p) {
					if (p == obj.para_code) {
						if ($("[name=" + j.para_code + "]").val() == "本医院"
								|| $("[name=" + j.para_code + "]").val() == "本账套"
								|| $("[name=" + j.para_code + "]").val() == "本年度"
								|| $("[name=" + j.para_code + "]").val() == "RMB") {
							return true;
						}

						$("[name=" + j.para_code + "]").val("");
						$("[name=" + j.para_code + "_name]").val("");
					}
				});
			}
		});
	}

	function myClose() {
		dialog.close();
	}
</script>
</head>
<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>
	<input id="mod_code" type="text" value="0102" style="display: none" />
	<div class="l-layout" id="layout1">
		<div position="center" id="centerReport" style="overflow: auto;">
			<form></form>
		</div>
	</div>
</body>
</html>
