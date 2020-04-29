<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>条件查询</title>
<jsp:include page="${path}/resource.jsp">
	<jsp:param
		value="select, datepicker, checkbox, grid, hr, dialog, upload, form, validate"
		name="plugins" />
</jsp:include>
<style type="text/css">
    .form_group {
        margin: 0;
	    height: 28px;
	    line-height: 28px;
	    font-weight: bold;
	    font-size: 12px;
	    color: #333;
	    border-bottom: solid 1px #ebebeb;
	    padding-top: 5px;
	    margin-bottom: 10px;
	    display: block;
	    position: relative;
	    clear: both;
    }
    .form_group span {
        margin-left: 40px;
    }
</style>
<script>
    var main_form, child_form, validate, validates;
    var formsArr = [];
    $(function () {
    	initForm();
    });

    function initForm() {
        
        var curIndex = parent.$.etDialog.getFrameIndex(window.name);
        var parentFrameName = parent.$.etDialog.parentFrameName;
        var parentWindow = parent.parent.window[parentFrameName];
        
        var gridTables = parentWindow.gridTables;
        var gridTableNames = parentWindow.gridTableNames;
        
        //加载form
        $.each(gridTables, function(index, item){
        	$("#forms").append("<div class='form_group'><span>"+gridTableNames[index]+"<span></div><form id='form_"+index+"'></form>");
        	var formInfo = getSearchField({group_id: ${group_id}, hos_id: ${hos_id}, formTable: item});
        	defaultValue(parentWindow.searchParam, formInfo.fieldItems);
        	var form = $('#forms #form_'+index).etForm(formInfo);
        	form.initWidget();
        	form.initValidate();
        	formsArr.push(form.getWidgetArray());
        })
        
        //分组
        var groupColumnOptions = [];
        $.each(parentWindow.grid.columns, function(i, v){
            if(v.type == "select" && v.hide == false){
                groupColumnOptions.push({id: v.name+','+v.display, text: v.display})
            }
        })
        
        $("#forms").append("<div class='form_group'><span>其它<span></div><form id='form_group'></form>");
        
        var groupColumn = {colNum: 2, fieldItems: [{OPTIONS:{defaultValue:"none", maxOptions:1000,options: groupColumnOptions}, 
            place: 1, align: "left", id:"groupColumn", name:"分组显示", type:"select", width:"160"}]};
        defaultValue(parentWindow.searchParam, groupColumn.fieldItems);
        var form_group =  $('#forms #form_group').etForm(groupColumn);
        form_group.initWidget();
        formsArr.push(form_group.getWidgetArray());
    }
       
    function defaultValue(searchParam, fields){
        if(!searchParam){return}
        for (var i=0;i<searchParam.length;i++){
            var fieldItems;
            var name=searchParam[i].name;
            for(var j=0;j<fields.length;j++){
                var optionName=fields[j].id;
                if(name==optionName){
               		fields[j].value=searchParam[i].value;
                	if(fields[j].OPTIONS){
                		fields[j].OPTIONS.defaultValue=searchParam[i].value; 
                	}
                }
            }
            
        }
    	
    }  

    function query() {
    	var curIndex = parent.$.etDialog.getFrameIndex(window.name);
        var parentFrameName = parent.$.etDialog.parentFrameName;
        var parentWindow = parent.parent.window[parentFrameName];
        var formData = []
    	$.each(formsArr, function(index, item){
    		formData = formData.concat(getFormData(item));
        }) 
        parentWindow.loadGridData(formData);
    }
       
    function getFormData(widgetArray) {
        function getValue(value) {
            var { type, $field, widget } = value;
            if (type === 'select') {
                return widget.getValue();
            } else if (type === 'date') {
                return widget.getValue();
            } else if (type === 'file') {
                return $field.find('input').val();
            } else if (type === 'checkbox') {
                return widget.checked;
            } else if (type === 'range') {
                const array = [$field.find('input').eq(0).val(), $field.find('input').eq(1).val()];
                return array;
            }
            return $field.val();
        }

        var formData = [];

        widgetArray.forEach((v, i) => {
            if (v.type === 'range') {
                var el0 = v.$field.find('input').eq(0);
                var el1 = v.$field.find('input').eq(1);
                if(el0.val()){
                	formData.push({name: el0.attr('id'), value: el0.val()});
                }
                if(el1.val()){
                	formData.push({name: el1.attr('id'), value: el1.val()});
                }
            } else {
                if(getValue(v)){
                	var obj = {};
                	obj.name = v.id;
                    obj.value = getValue(v);
                    formData.push(obj);
                }
                
            }
        });
        return formData;
    }
       
</script>
</head>

<body style="overflow: auto">
    <div id="forms"></div>
</body>
</html>