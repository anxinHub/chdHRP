<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html style="overflow:hidden;">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title></title>
<jsp:include page="${path}/inc_jquery_1.9.0.jsp" />
<script type="text/javascript">
    var grid;
    var gridManager = null;
    var userUpdateStr;
	var bonus_clum = "";
	var sum_clum="";
	var is_DataCanSave = false;    // 数据是否能保存  
	var column = "";
    $(function ()
    {

    	loadDict()//加载下拉框
    	loadHead(null);	
    });
  //查询
    function  query(){
    	grid.options.parms=[];
    	grid.options.newPage=1;
        //根据表字段进行添加查询条件
    	grid.options.parms.push({ name: 'bonus_clum', value: bonus_clum});
		grid.options.parms.push({ name: 'sum_clum', value: sum_clum});
		//根据表字段进行添加查询条件
		grid.options.parms.push({
			name : 'start_year_month',
			value : $("#start_year_month").val()
		});

		grid.options.parms.push({
			name : 'end_year_month',
			value : $("#end_year_month").val()
		});

		grid.options.parms.push({
			name : 'dept_no',
			value : liger.get("dept_code").getValue().split(".")[1]
		});
	 	grid.options.parms.push({
			name : 'dept_id',
			value : liger.get("dept_code").getValue().split(".")[0]
		});
	 	grid.options.parms.push({
			name : 'people_type_code',
			value : liger.get("people_type_code").getValue().split(".")[0]
		});
	 	grid.options.parms.push({
			name : 'people_code',
			value : liger.get("people_code").getValue().split(".")[0]
		}); 
    	//加载查询条件
    	grid.loadData(grid.where);
     }

    var result=0;
    function loadHead(){

    	var columns = [ {
			display : '核算期间',
			name : '',
			align : 'left',
			frozen : true,
			minWidth : 60,
			render:function(rowdata){ return rowdata.ACC_YEAR + rowdata.ACC_MONTH}
		}, {
			display : '科室编码',
			name : 'DEPT_CODE',
			align : 'left',
			frozen : true,
			minWidth : 60
		}, {
			display : '科室名称',
			name : 'DEPT_NAME',
			align : 'left',
			frozen : true,
			minWidth : 120
		}, {
			display : '人员类别编码',
			name : 'PEOPLE_TYPE_CODE',
			align : 'left',
			frozen : true,
			minWidth : 60
		}, {
			display : '人员类别名称',
			name : 'PEOPLE_TYPE_NAME',
			align : 'left',
			hide : true,
			minWidth : 120
		}, {
			display : '人员编码',
			name : 'PEOPLE_CODE',
			align : 'left',
			frozen : true,
			minWidth : 60
		}, {
			display : '人员名称',
			name : 'PEOPLE_NAME',
			align : 'left',
			frozen : true,
			minWidth : 120
		}];
		var bonus_item_clum_columns="";
    	ajaxJsonObjectByUrl("queryHtcPeopleBonusItemClumHead.do?isCheck=false", "", function (responseData){
    		if(responseData.Rows.length > 0){
    			$.each(responseData.Rows, function(v_index, v_data){ 
    				bonus_item_clum_columns="";
    				bonus_item_clum_columns="{"
        				      +"display: '"+v_data.bonus_item_name+"',"
        				      +"name:'"+v_data.clum_code+"',"
        				      +"align: 'left' ,"
        				      +"editor: {type:'float'} ,"
        				      +"formatter: '###,##0.00' ,"
        				      +"minWidth: 120 ,"
        				      +"render:function(rowdata){return formatNumber(rowdata." + v_data.clum_code + ",2,1)}"
    				          +",}"; 
    				bonus_clum += ","+v_data.clum_code+" as " + v_data.clum_code;
    				sum_clum += ",sum("+v_data.clum_code+") as " + v_data.clum_code;
   				    if(result==0){
                     	column += "," + v_data.clum_code;
						if(responseData.Rows.length==v_index+1){
                     		result=1;
                     	}
                     }
    				columns.push(eval("("+bonus_item_clum_columns+")"));
    			}); 
			}
    	}, false);
		grid = $("#maingrid").ligerGrid({
			columns : columns,
			dataAction : 'server',
			dataType : 'server',
			usePager : true,
			url : 'queryHtcPeopleBonusDetail.do',
			width : '100%',
			height : '100%',
			checkbox : true,
			rownumbers : true,
			enabledEdit : true,
			delayLoad:true,
			selectRowButtonOnly : true,//heightDiff: -10,
			onBeforeEdit: f_onBeforeEdit,
			onAfterEdit: f_onAfterEdit,
			checkBoxDisplay: isCheckDisplay,
			toolbar : {
				items : [ {
					text : '查询',
					id : 'search',
					click : query,
					icon : 'search'
				}, {
					line : true
				},{
					text : '添加',
					id : 'add',
					click : add_open,
					icon : 'add'
				},{
					line : true
				},{
					text : '删除',
					id : 'delete',
					click : remove,
					icon : 'delete'
				}, {
					line : true
				}]
			}
		});

		gridManager = $("#maingrid").ligerGetGridManager();
	    
    }

    function add_open(){
		$.ligerDialog.open({
			url : 'htcPeopleBonusDetailAddPage.do?isCheck=false',
			height : 350,
			width : 400,
			title : '添加',
			modal : true,
			showToggle : false,
			showMax : false,
			showMin : true,
			isResize : true,
			buttons : [ {
				text : '确定',
				onclick : function(item, dialog) {
					dialog.frame.savePeopleWageDetail();
				},
				cls : 'l-dialog-btn-highlight'
			}, {
				text : '取消',
				onclick : function(item, dialog) {
					dialog.close();
				}
			} ]
		});
	}

	function remove(){
		var data = gridManager.getCheckedRows();
        if (data.length == 0){
        	$.ligerDialog.error('请选择行');
        }else{
            var ParamVo =[];
            $(data).each(function (){
            	ParamVo.push(
            		this.GROUP_ID + "@" 
            	+	this.HOS_ID + "@"
            	+	this.COPY_CODE + "@"
            	+	this.ACC_YEAR + "@"
            	+	this.ACC_MONTH + "@"
            	+	this.DEPT_NO + "@"
            	+	this.DEPT_ID + "@"
            	+	this.PEOPLE_TYPE_CODE+"@"
            	+	this.PEOPLE_CODE);//实际代码中temp替换主键
            });
            $.ligerDialog.confirm('确定删除?', function (yes){
            	if(yes){
                	ajaxJsonObjectByUrl("deleteHtcPeopleBonusDetail.do",{ParamVo : ParamVo.toString()},function (responseData){
                		if(responseData.state=="true"){
                			query();
                		}
                	});
            	}
            }); 
        }
	}

	function f_onAfterEdit(e){
		
		var event1 = window.event;
		
		if(e.value != e.oldvalue) is_DataCanSave = true;
	
		if(is_DataCanSave){
			
			var len = column.substring(1, column.length).split(",")
			
			var item = "";
			
		   $(len).each(function(index, value) {

	            var v = e.record[len[index]];
	
	            if (v == "UNDEFINED" || v == null|| v == "") {
	
	                v = 0;
	            }
	
	            item += v + ";";
	        });
		   
             var formPara = {
                    group_id:e.record.GROUP_ID,
                    hos_id:e.record.HOS_ID,
                    copy_code:e.record.COPY_CODE,
                    acc_year:e.record.ACC_YEAR,
                    acc_month:e.record.ACC_MONTH,
                    dept_no:e.record.DEPT_NO,
                    dept_id:e.record.DEPT_ID,
                    people_type_code:e.record.PEOPLE_TYPE_CODE,
                    people_code:e.record.PEOPLE_CODE,
                    item:item,
                    item_column:column
             }
             //alert(JSON.stringify(formPara))
             ajaxJsonObjectByUrl("updateHtcPeopleBonusDetailItem.do?isCheck=false", formPara, function(responseData) {
                 /* if (responseData.state == "true") {
                 	query();
                 } */
             });
             
		}

   }

	function f_onBeforeEdit(e){
		if(e.record.ACC_YEAR + e.record.ACC_MONTH == "合计"){
			    return false;
			}
	}

    function isCheckDisplay(rowdata) {

        if (rowdata.ACC_YEAR + rowdata.ACC_MONTH == "合计")
            return false;
    }

    
    function loadDict(){
    	
   
    		autocomplete("#dept_code",
    				"../../../info/base/queryHtcDeptDict.do?isCheck=false", "id",
    				"text", true, true);
    		autocomplete("#people_type_code",
    				"../../../info/base/queryHtcPeopleTypeDict.do?isCheck=false",
    				"id", "text", true, true);
    		autocomplete("#people_code",
    				"../../../info/base/queryHtcPeopleDict.do?isCheck=false", "id",
    				"text", true, true);

    		$("#start_year_month").ligerTextBox({
    			width : 80
    		});
    		$("#end_year_month").ligerTextBox({
    			width : 80
    		});
    		autodate("#start_year_month", "YYYYmm");
    		autodate("#end_year_month", "YYYYmm");
         }   
    </script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>
	<div id="toptoolbar" ></div>
    <table cellpadding="0" cellspacing="0" class="l-table-edit" >
	 <tr>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">期间：</td>
			<td align="left" class="l-table-edit-td"><input class="Wdate"
				name="start_year_month" type="text" id="start_year_month"
				style="width: 70px;" ltype="text"
				onFocus="WdatePicker({isShowClear:false,readOnly:true,dateFmt:'yyyyMM'})"
				style="width:160px;" /></td>
			<td align="right"><span>&nbsp;至&nbsp;</span></td>
			<td><input class="Wdate" name="end_year_month" type="text"
				id="end_year_month" style="width: 70px;" ltype="text"
				onFocus="WdatePicker({isShowClear:false,readOnly:true,dateFmt:'yyyyMM'})"
				style="width:160px;" /></td>
			<td align="left"></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">科室编码：</td>
			<td align="left" class="l-table-edit-td"><input name="dept_code"
				type="text" id="dept_code" ltype="text" /></td>
			<td align="left"></td>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">人员类别：</td>
			<td align="left" class="l-table-edit-td"><input
				name="people_type_code" type="text" id="people_type_code"
				ltype="text" /></td>
			<td align="left"></td>
		</tr>
		<tr>
			<td align="right" class="l-table-edit-td" style="padding-left: 20px;">人员编码：</td>
			<td align="left" class="l-table-edit-td" colspan="3"><input
				name="people_code" type="text" id="people_code" ltype="text" /></td>
			<td align="left"></td>
		</tr> 
    </table>
	<div id="maingrid"></div>
</body>
</html>
