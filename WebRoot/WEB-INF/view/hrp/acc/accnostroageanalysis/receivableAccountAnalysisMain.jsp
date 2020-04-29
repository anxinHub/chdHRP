<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="${path}/inc_jquery_1.9.0.jsp"/>
<script>
    var grid;  
    var gridManager = null;   
    var userUpdateStr;
    var flag = false;
    var rangeIsExits = 0;
    var aArray = [];
    var dataAll;
    
    $(function (){
    	loadForm();
		loadDict();
    	loadHead(null);	//加载数据
    	
    	rangeIsExits = '${rangeIsExits}';
    });
    //查询
    function query(){
    	
    	grid.options.parms=[];
        grid.options.newPage=1;
        //根据表字段进行添加查询条件
        grid.options.parms.push({name:'subj_code',value:liger.get("subj_code").getValue()}); 
        grid.options.parms.push({name:'analysisDate',value:$("#analysisDate").val()});
        	
        if(""==liger.get("check_type").getValue()){
    		grid.options.parms.push({name:'check_type',value:""}); 
    		grid.options.parms.push({name:'check_type_id',value:""}); 
        }else{
        	grid.options.parms.push({name:'check_type',value:liger.get("check_type").getValue().split(" ")[0]}); 
       		grid.options.parms.push({name:'check_type_id',value:liger.get("check_type").getValue().split(" ")[1]}); //type_id
        }
        	
        grid.options.parms.push({name:'end_date',value:$("#end_date").val()});
        if(""==liger.get("proj1").getValue()||""==liger.get("proj2").getValue()){
    		grid.options.parms.push({name:'proj1',value:""}); 
        	grid.options.parms.push({name:'proj2',value:""});
    	}else{
    		grid.options.parms.push({name:'proj1',value:liger.get("proj1").getValue().split(".")[0]}); 
        	grid.options.parms.push({name:'proj2',value:liger.get("proj2").getValue().split(".")[0]});
    	} 
        
        var is_check = 0;
        var showItem = 0;
        if($("#is_check").is(":checked")){
        	is_check = 1;
        }
        
        if($("#showItem").is(":checked")){
        	showItem = 1;
        }
        
        grid.options.parms.push({name:'is_check',value:is_check});
        grid.options.parms.push({name:'showItem',value:showItem}); 
        //加载查询条件
        grid.loadData(grid.where);
    	
     }

    function loadHead(){
    	$.post("../accbudgrange/queryAccBudgRangeTitle.do?isCheck=false",null,function(data){
    			var columns = "{ display: '项目编码', name: 'check_code', align: 'left'" +"},"
		                     +"{ display: '项目名称', name: 'check_name', align: 'left'"+"},"
		                     +"{ display: '余额', name: 'end_os', align: 'right'"     
					     +"}";
					     
				dataAll = data;
	    		$.each(data,function(index,value){
	    			$.each(value,function(i,v){
	    				aArray.splice(0,aArray.length);//清空数组
	    				aArray.push(v.note);
	    				if(v.note !=undefined){
	    					var a = i+1;
	        				columns = columns + ",{display: '"+v.note+"', name: 'col_"+a+"', align: 'right'}"
	        				i=i+1;
	    				}
	    			});
	    		});
    			//alert(columns);
    			grid = $("#maingrid").ligerGrid({
    		           columns: eval("["+columns+"]"),
    		           dataAction: 'server',dataType: 'server',usePager:true,url:'queryReceivableAccount.do?isCheck=true',
    		           width: '100%', height: '100%', checkbox: false,rownumbers:true,
    		           delayLoad:true,pageSizeOptions:[100, 200, 500],pageSize: 100,
    		           selectRowButtonOnly:true,
    		           toolbar: { items: [
    		          		{ text: '查询', id:'search', click: itemclick ,icon:'search' },
   		                	{ line:true },
   	                     	{ text: '打印', id:'print', click: printData, icon:'print' },
   	    					{ line:true }
    		    			]}
    		     });
    		     gridManager = $("#maingrid").ligerGetGridManager();
    	},"json");
    }
    
    	function printData(){
       	
			if (grid.getData().length == 0) {
				$.ligerDialog.error("请先查询数据！");
				return;
			}
			var printPara={
					title: "应收账龄分析",//标题
					columns: JSON.stringify(grid.getPrintColumns()),//表头
					class_name: "com.chd.hrp.acc.service.verification.AccVerificationService",
					method_name: "collectReceivableAccountPrint",
					bean_name: "accVerificationService"/* ,
					heads: JSON.stringify(heads) *///表头需要打印的查询条件,可以为空
					/* foots: JSON.stringify(foots)//表尾需要打印的查询条件,可以为空 */
					};
			
			/* var printPara = {
				title : "应收账龄分析",
				columns : JSON.stringify(grid.getPrintColumns()),//表头 //数据格式化
				class_name : "com.chd.hrp.acc.service.verification.AccVerificationService",
				method_name : "collectReceivableAccountPrint",
				bean_name : "accVerificationService"
			}; */
			
			$.each(grid.options.parms,function(i,obj){
				printPara[obj.name]=obj.value;
			});
		
			officeGridPrint(printPara);

		}

		//有百分比的表头
		function f_setColumns() {
			var a = 0;
			var columns = "{ display: '项目编码', name: 'check_code', align: 'left'"
					+ "},"
					+ "{ display: '项目名称', name: 'check_name', align: 'left'"
					+ "},"
					+ "{ display: '余额', name: 'end_os', align: 'right'"
					+ "}";

			$.each(dataAll, function(index, value) {
				$.each(value, function(i, v) {
					if (v.note != undefined) {
						var a = i + 1;
						columns = columns + ",{display: '" + v.note
								+ "', name: 'col_" + a + "', align: 'right'},"
								+ "{display: '百分比', name: 'col_bal_" + a
								+ "', align: 'right'" + "}"
						i = i + 1;
					}

				});
			});

			grid.set('columns', eval("[" + columns + "]"));
			grid.reRender();

		}

		function itemclick(item) {
			if (item.id) {
				switch (item.id) {
				case "search":
					if (rangeIsExits == 0) {
						$.ligerDialog.error('请维护账龄期间表！');
						return;
					} else {
						if ($("#showItem").attr("checked") == true) {
							if ($("form").valid()) {
								f_setColumns();
								query();
							}
						} else {
							if ($("form").valid()) {
								loadHead();
								query();
							}
						}
					}

					return;
				case "Word":
				case "PDF":
				case "TXT":
				case "XML":
				}
			}

		}

		function loadForm() {

			$.metadata.setType("attr", "validate");
			var v = $("form").validate({
				errorPlacement : function(lable, element) {
					if (element.hasClass("l-textarea")) {
						element.ligerTip({
							content : lable.html(),
							target : element[0]
						});
					} else if (element.hasClass("l-text-field")) {
						element.parent().ligerTip({
							content : lable.html(),
							target : element[0]
						});
					} else {
						lable.appendTo(element.parents("td:first").next("td"));
					}
				},
				success : function(lable) {
					lable.ligerHideTip();
					lable.remove();
				},
				submitHandler : function() {
					$("form .l-text,.l-textarea").ligerHideTip();
				}
			});
			// $("form").ligerForm();
		}

		function loadDict() {
			//字典下拉框
			var param = {
				SUBJ_NATURE_CODE1 : '04',
				SUBJ_NATURE_CODE2 : '04',
				is_last : 1
			};
			autodate("#end_date");//默认当前日期
			$("#subj_code").ligerComboBox({
				parms : param,
				url : '../querySubj.do?isCheck=false',
				valueField : 'id',
				textField : 'text',
				selectBoxWidth : subjWidth,
				autocomplete : true,
				width : 180
			});

			$("#analysisDate").ligerTextBox({
				width : 180
			});
			$("#end_date").ligerTextBox({
				width : 180
			});
		}

		function subjChange() {
			$("#check_type").val("");
			var subj_code = liger.get("subj_code").getValue();

			if (subj_code == '' || subj_code == null) {
				$("#check_type").ligerComboBox({
					parms : "",
					url : '../queryCheckTable.do?isCheck=false',
					valueField : 'check_table',
					textField : 'check_type_name',
					autocomplete : true,
					selectBoxWidth : 180,
					width : 180
				});

			} else {
				subj_code = subj_code;
				$("#check_type").ligerComboBox({
					parms : {
						'subj_code' : subj_code
					},
					url : '../queryCheckTypeBySubjId.do?isCheck=false',
					valueField : 'check_table',
					textField : 'check_type_name',
					initValue : 1,
					autocomplete : true,
					selectBoxWidth : 180,
					width : 180
				});
			}

		}

		function checkTypeChange() {
			$("#proj1").val("");
			$("#proj2").val("");
			var check_type_code = liger.get("check_type").getValue().split(" ")[0];

			$("#proj1").ligerComboBox({
				selectBoxWidth : 180,
				autocomplete : true,
				width : 180
			});
			$("#proj2").ligerComboBox({
				selectBoxWidth : 180,
				autocomplete : true,
				width : 180
			});

			switch (check_type_code) {
			case "HOS_DEPT_DICT":
				$("#proj1").ligerComboBox({
					url : '../../sys/queryDeptDict.do?isCheck=false',
					valueField : 'id',
					textField : 'text'
				});
				$("#proj2").ligerComboBox({
					url : '../../sys/queryDeptDict.do?isCheck=false',
					valueField : 'id',
					textField : 'text'
				});
				return;
			case "HOS_EMP_DICT":
				$("#proj1").ligerComboBox({
					url : '../../sys/queryEmp.do?isCheck=false',
					valueField : 'id',
					textField : 'text'
				});
				$("#proj2").ligerComboBox({
					url : '../../sys/queryEmp.do?isCheck=false',
					valueField : 'id',
					textField : 'text'
				});
				return;
			case "HOS_PROJ_DICT":
				$("#proj1").ligerComboBox({
					url : '../../sys/queryProjDictDict.do?isCheck=false',
					valueField : 'id',
					textField : 'text'
				});
				$("#proj2").ligerComboBox({
					url : '../../sys/queryProjDictDict.do?isCheck=false',
					valueField : 'id',
					textField : 'text'
				});
				return;
			case "HOS_STORE_DICT":
				$("#proj1").ligerComboBox({
					url : '../../sys/queryStoreDictDict.do?isCheck=false',
					valueField : 'id',
					textField : 'text'
				});
				$("#proj2").ligerComboBox({
					url : '../../sys/queryStoreDictDict.do?isCheck=false',
					valueField : 'id',
					textField : 'text'
				});
				return;
			case "HOS_CUS_DICT":
				$("#proj1").ligerComboBox({
					url : '../../sys/queryCusDict.do?isCheck=false',
					valueField : 'id',
					textField : 'text'
				});
				$("#proj2").ligerComboBox({
					url : '../../sys/queryCusDict.do?isCheck=false',
					valueField : 'id',
					textField : 'text'
				});
				return;
			case "HOS_SUP_DICT":
				$("#proj1").ligerComboBox({
					url : '../../sys/querySupDictDict.do?isCheck=false',
					valueField : 'id',
					textField : 'text'
				});
				$("#proj2").ligerComboBox({
					url : '../../sys/querySupDictDict.do?isCheck=false',
					valueField : 'id',
					textField : 'text'
				});
				return;
			case "HOS_SOURCE":
				$("#proj1").ligerComboBox({
					url : '../../sys/querySourceDict.do?isCheck=false',
					valueField : 'id',
					textField : 'text'
				});
				$("#proj2").ligerComboBox({
					url : '../../sys/querySourceDict.do?isCheck=false',
					valueField : 'id',
					textField : 'text'
				});
				return;
			case "V_HOS_DICT":
				$("#proj1").ligerComboBox({
					url : 'queryVHosDict.do?isCheck=false',
					valueField : 'id',
					textField : 'text'
				});
				$("#proj2").ligerComboBox({
					url : 'queryVHosDict.do?isCheck=false',
					valueField : 'id',
					textField : 'text'
				});
				return;
			case "ACC_CHECK_ITEM"://自定义核算
				$("#proj1").ligerComboBox({
					url : '../queryCheckItem.do?isCheck=false',
					valueField : 'id',
					textField : 'text'
				});
				$("#proj2").ligerComboBox({
					url : '../queryCheckItem.do?isCheck=false',
					valueField : 'id',
					textField : 'text'
				});
				return;
			}
		}
	</script>
</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<div id="toptoolbar" ></div>
	<form name="form1" method="post"  id="form1" >
	    <table cellpadding="0" cellspacing="0" class="l-table-edit"  border="0">
	        <tr>
	            <td align="right" class="l-table-edit-td"  style="padding-left:10px;">应收科目<span style="color:red">*</span>：</td>
	            <td align="left" class="l-table-edit-td" >
	            	<input id="subj_code" name="subj_code" onchange="subjChange();" validate="{required:true}"/>
	            </td>
	            
	            <td align="right" class="l-table-edit-td"  style="padding-left:10px;">往来类型<span style="color:red">*</span>：</td>
	            <td align="left" class="l-table-edit-td" >
	            	<input id="check_type" name="check_type" onchange="checkTypeChange();" validate="{required:true}"/>
	            </td>
	            
	            <td align="right" class="l-table-edit-td"  style="padding-left:10px;" >往来项目：</td>
	            <td align="left" class="l-table-edit-td" ><input id="proj1" name="proj1" /></td>
	            <td align="left" class="l-table-edit-td">至:</td>
	            <td align="left" class="l-table-edit-td" ><input id="proj2" name="proj2" /></td>
	             
	        </tr> 
	         <tr>
	           	<td align="right" class="l-table-edit-td"  style="padding-left:10px;">分析日期：</td>
	            <td align="left" class="l-table-edit-td">
	            	<select id="analysisDate" name="analysisDate">
	            		<option value="occur_date">发生日期</option>
	            		<option value="due_date">到期日期</option>
	            	</select>
	            </td>
	            
	            <td align="right" class="l-table-edit-td"  style="padding-left:10px;">截止日期：</td>
	            <td align="left" class="l-table-edit-td">
	            	<input class="Wdate" name="end_date" type="text" id="end_date"  onFocus="WdatePicker({isShowClear:false,readOnly:false,dateFmt:'yyyy-MM-dd'})" ltype="text" />
	            </td>
	            
	            <td align="right" class="l-table-edit-td" ><input name="showItem" type="checkbox" id="showItem" ltype="text"  /></td>
	            <td align="left">显示百分比</td>
	            <td align="right" class="l-table-edit-td"  ><input name="is_check" type="checkbox" id="is_check"  /></td>
	            <td align="left" class="l-table-edit-td">含未记账</td>
	        </tr> 
	        
	    </table>
	</form>
	<center><h1>应收账龄分析</h1></center>
	<div id="maingrid"></div>
</body>
</html>
