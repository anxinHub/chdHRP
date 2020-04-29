<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
	String path = request.getContextPath();
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<jsp:include page="${path}/resource.jsp">
	<jsp:param value="hr,tree,grid,select,dialog,validate,tab,datepicker,upload,checkbox,pageOffice" name="plugins" />
</jsp:include>
<script src="<%=path%>/lib/ligerUI/js/ligerui.all.js" type="text/javascript"></script>

<script type="text/javascript">
var mouldGrid;
var dataProp = [];
var mould_code,data_pro;
var mould_item;
var forkey;

$(function(){
	//debugger;
	initSelect();
	initPactMouldConfGrid();
	/*
	if (!$("#forkey").val() ) {
		$("#fore_key").ligerTextBox('option','disabled',true);
	}
	*/
})

var query = function () {
         params = [
             { name: 'mould_item', value: $("#mould_item").val() },
             { name: 'data_pro', value: $("#data_pro").val() },
             { name: 'fore_key', value: $("#fore_key").val() }
         ];
         mouldGrid.loadData(params);
     };
     
var initSelect=  function(){
	
	mould_item = $("#mould_item").etSelect({ url: 'queryMouldItem.do?isCheck=false'});
    
	forkey = $('#forkey').etCheck({
		onChange: function(state){
			if(state == 'checked'){
      			$("#fore_key").removeAttr("disabled");
			 }else{
      			$("#fore_key").attr("disabled","disabled");
			 }
		}
	});
	
	var is_linker = $('#is_linker').etCheck({
		onChange: function(state){
			if(state == 'checked'){
      			$("#linker").removeAttr("disabled");
			 }else{
      			$("#linker").attr("disabled","disabled");
			 }
		}
	});
	
	var $select = $('#data_pro').etSelect({
        valueField: 'id',
        labelField: 'text',
        searchField: 'text',
        options: [
            { id: "Binary", text: 'Binary' },
            { id: "Boolean", text: 'Boolean' },
            { id: "Currency", text: 'Currency' },
            { id: "Date", text: 'Date' },
            { id: "Double", text: 'Double' },
            { id: "Integer", text: 'Integer' },
            { id: "List", text: 'List' },
            { id: "Numeric", text: 'Numeric' },
            { id: "String", text: 'String' },
            { id: "Text", text: 'Text' },
            { id: "Time", text: 'Time' },
            { id: "TimeStamp", text: 'TimeStamp' }
        ],
        create: false
    	});
	
	};
	
    
	var initPactMouldConfGrid = function () {
    var mouldConfigColumns = [
		 { display: '编制项id', name: 'mould_id', width: '5%', hidden:true},
    	 { display: '编制项编码', name: 'mould_code', width: '10%',editor: { type: 'textbox' }},
    	 { display: '编制项名称', name: 'mould_name', width: '15%',editor: { type: 'textbox' }},
         { display: '数据属性', name: 'data_pro', width: '10%',
           valueField: 'id', textField: 'text',
       	   editor: {
      		     type: 'select', 
    	 		 source: [	{ "id": "Binary", "text": "Binary",label:"Binary"}, 
			       			{ "id": "Boolean", "text": "Boolean",label:"Boolean"},
			       			{ "id": "Currency", "text": "Currency",label:"Currency"},
			       			{ "id": "Date", "text": "Date",label:"Date"},
			       			{ "id": "Double", "text": "Double",label:"Double"}, 
			       			{ "id": "Integer", "text": "Integer",label:"Integer"},
			       			{ "id": "List", "text": "List",label:"List"},
			       			{ "id": "Numeric", "text": "Numeric",label:"Numeric"},
			       			{ "id": "String", "text": "String",label:"String"}, 
			       			{ "id": "Text", "text": "Text",label:"Text"},
			       			{ "id": "Time", "text": "Time",label:"Time"},
			       			{ "id": "TimeStamp", "text": "TimeStamp",label:"TimeStamp"}
			      		 ],
					keySupport: true,
					autocomplete: true,
      		 },
      		editable: true
         },
         { display: '是否外键', name: 'is_foreign', width: '8%', align: 'center' ,type: 'checkbox',
         	render: function (obj) {
      			var is_foreign = obj.rowData.is_foreign;
      			//console.log(obj.rowData);
      			if(is_foreign == "1"){
      				return "<input type='checkbox' style='margin-top:7px;' id='is_foreign'"+obj.rowData._rowIndx+" onchange=\"checkEnable("+obj.rowData._rowIndx+",this)\" checked='checked'/>";
      			}else{
      				return "<input type='checkbox' style='margin-top:7px;' id='is_foreign'"+obj.rowData._rowIndx+" onchange=\"checkEnable("+obj.rowData._rowIndx+",this)\" />";
      			}
          	}
	 	 },
    	 { display: '关联外键', name: 'foreign_name', width: '20%',editor: { type: 'textbox' }},
    	 { display: '是否带链接', name: 'is_linker', width: '8%',align: 'center' ,type: 'checkbox',
    		 render: function (obj) {
       			var is_linker = obj.rowData.is_linker;
       			if(is_linker == "1"){
       				return "<input type='checkbox' style='margin-top:7px;' id='is_linker'"+obj.rowData._rowIndx+" onchange=\"checkEnableLin("+obj.rowData._rowIndx+",this)\" checked='checked'/>";
       			}else{
       				return "<input type='checkbox' style='margin-top:7px;' id='is_linker'"+obj.rowData._rowIndx+" onchange=\"checkEnableLin("+obj.rowData._rowIndx+",this)\" />";
       			}
           	}	
		 },
    	 { display: '链接', name: 'linker', width: '20%',editor: { type: 'textbox' }},
         
    ];
    var paramObj = {
    	editable: true,
    	height: '100%',
    	inWindowHeight: true,
    	checkbox: true,
    	usePager: false,
        columns: mouldConfigColumns,
        dataModel: {
           url: 'queryPactMouldConfigItem.do?isCheck=false'
        },
        toolbar: {
            items: [
                { type: 'button', label: '查询', listeners: [{ click: query }], icon: 'search' },
           	 	{ type: 'button', label: '增加', listeners: [{ click: addMouldConf }], icon: 'add' },
                { type: 'button', label: '保存',  listeners: [{ click: saveMouldConf }],  icon: 'save' },
                { type: 'button', label: '删除',  listeners: [{ click:  delMouldConf }],  icon: 'del' }
            ]
        }
    };
    mouldGrid =  $("#pactmouldconf").etGrid(paramObj);
    
};

function checkEnable(rowIndex,th){
	var data = mouldGrid.getRowData(rowIndex);
	//console.log(th.checked);
	if(th.checked){
		data.is_foreign = 1;
	}else{
		data.is_foreign = 0;
	}
}

function checkEnableLin(rowIndex,th){
	var data = mouldGrid.getRowData(rowIndex);
	if(th.checked){
		data.is_linker = 1;
	}else{
		data.is_linker = 0;
	}
}

function addMouldConf() {
	mouldGrid.addRow();
}

function saveMouldConf(){
	//debugger;
	var param = [];
	var data = mouldGrid.selectGet();
	if(data.length == 0){
		 $.etDialog.error('请选择数据');
		 return;
	}
	
	var error ;
	var curMouldId="",urlstr="";
	 $(data).each(function () {
        var rowdata = this.rowData;
        //console.log(this);
        var row = this.rowIndx +1 ;
        if(!rowdata.mould_code){error = "第"+row + "行编制编码不能为空";return;}
        if(!rowdata.mould_name){error = "第"+row + "行编制名称不能为空";return;}
        //if(!rowdata.datapro_id){error = "第"+row + "行数据属性不能为空";return;}
        rowdata.group_id = ${group_id};
        rowdata.hos_id = ${hos_id};
        rowdata.copy_code = '${copy_code}';
        //debugger;
        ////////行编辑值
        //console.log(rowdata);
        param.push(rowdata);
        
        curMouldId = rowdata.mould_id;
        //alert(curMouldId);
        //debugger;
    	if(curMouldId>0){
     		urlstr='updatePactMouldConfigItem.do?isCheck=false';
     	}else{
     		urlstr='addPactMouldConfigItem.do?isCheck=false';
     	}
    	
    	
    });
	 
	if(error){$.etDialog.error(error);return; }
	ajaxPostData({
	 	url: urlstr,
	 	 data: {
        	 mapVo: JSON.stringify(param)
         },
         success: function (result) {
			  query();
		  }
	});
}

function delMouldConf(){
	 var data = mouldGrid.selectGet();
	 console.log(data);
	 //var data = mouldGrid.getCheckedRows();
	 //alert(data);
     if (data.length == 0) {
         $.etDialog.error('请选择行');
     } else {
         var ParamVo = [];
         
         $(data).each(function (){	
        	 var rowdata = this.rowData;
				ParamVo.push(
				//表的主键
				rowdata.group_id   +"@"+ 
				rowdata.hos_id   +"@"+ 
				rowdata.copy_code   +"@"+
				rowdata.mould_id 
				)
				alert(rowdata.mould_id);
         });
         $.etDialog.confirm('确定删除?', function (yes) {
        	 if(yes){
        		 /*
             	ajaxJsonObjectByUrl("deletePactMouldConfigItem.do?isCheck=false",{ParamVo : ParamVo.toString()},function (responseData){
             		debugger;
             		if(responseData.state=="true"){
             			query();
             		}
             	});
        		 */
        		 ajaxPostData({
        			 	url: 'deletePactMouldConfigItem.do?isCheck=false',
        			 	 data: {
        			 		ParamVo : ParamVo.toString()
        		         },
        				  success: function (result) {
        					  mouldGrid.deleteRows(data);
        					  //query();
        				  },
        			});
         	}
         });
     }
}

</script>
</head>
<body>
	<table class="table-layout">
		<tr>
			<td class="label no-empty" style="width: 100px;">编制项：
			</td>
			<td class="ipt" style="width: 150px;"><select id="mould_item" style="width: 200px;"></select></td>
			<td class="label" style="width: 100px;">数据属性：
			</td>
			<td class="ipt"><select id="data_pro" style="width: 180px;"></select> 
			</td>
    	    <td class="label"><input id="forkey" type="checkbox" />关联外键</td>
			<td align="left" class="l-table-edit-td" >
    	        <input name="fore_key" type="text" id="fore_key" disabled="disabled" ltype="text" validate="{required:true,maxlength:20}" />
    	    </td>
    	    <td class="label" style="width: 100px;display: none">mould_id</td>
       		<td style="width: 735px;display: none" colspan="7"><textarea id="mould_id" style="width: 735px;height: 100px"></textarea></td>      
        
		</tr>
	</table>
	<div id="pactmouldconf"></div>
</body>
</html>