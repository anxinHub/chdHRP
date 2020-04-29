<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html style="overflow:hidden;">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
 <jsp:include page="${path}/resource.jsp">
            <jsp:param value="select, datepicker, checkbox, grid, tab, tree, hr, dialog, form, validate, upload, pageOffice, jquery_print" name="plugins" />
  </jsp:include>
 <script src="<%=path%>/lib/et_components/et_plugins/etTree.min.js"></script>
<style type="text/css">
.search-form {
    padding: 5px;
    /* text-align: center; */
    box-sizing: border-box;
    background: #e0ecff;
    border-bottom: 1px solid #a3c0e8;
}
span.title{
   display: block;
    width: 100%;
    border: 1px solid #CCCCCC;
    position: relative;
    text-align: left;
    background: #e0ecff;
    font-size: 14px;
    font-weight: bold;
    text-indent : 10px;
}
   /*     	#table {
       		width: 80%;
    		    margin: 0 auto;
    border-collapse: collapse;

		}
		 	#table2 {
       		width: 80%;
    		    margin: 0 auto;
    border-collapse: collapse;
  
		} */
</style>
<script type="text/javascript">
	var grid;
	var rightGrid;
	var gridManager = null;
	var rightManager=null;
	 var dialog = frameElement.dialog; //调用页面的dialog对象(ligerui对象)
	 var dialogData = dialog.get('data');//获取data参数
	var is_update;
	var data;
	$(function() {
		$.post("queryStationDescription.do?isCheck=false", {
			'tab_code' : 'HR_STATION_DESCRIPTION',
			'station_code':dialogData.param[0].station_code,
			 'dept_id':dialogData.param[0].dept_id,
			 'dept_no':dialogData.param[0].dept_no,
			 'rjt':'json'
		
		},function(responseData){
			data=responseData.Rows;
			 $.each(responseData.Rows,function(i,v){
					
		   			is_update=v.STATION_CODE;
		   			
			 })
				loadDict();
		}
		)
		
		

		//加载数据
		loadHead(null);
	
	
		$("#save").click(function () {
			
			   if (!validate.test()) {return;}
			var formData = form.getFormData();
		    var formData1 = form1.getFormData();
		    formData.append('tab_code',"HR_STATION_DESCRIPTION");
		    formData.append('dept_no', dialogData.param[0].dept_no);
		    formData.append('dept_id',dialogData.param[0].dept_id);
		    formData.append('station_code', dialogData.param[0].station_code);
		    
			/*判断是否为修改数据
			*/
			if(is_update!=null){
			/* 	ajaxJsonObjectByUrl("updateStationDescription.do?isCheck=false", formPara, function(
						responseData) {
					
						  parent.query();
					
				}); */
		    	  ajaxPostFormData({
                      url: 'updateStationDescription.do?isCheck=false',
                      data: formData,
                      dataType: 'json',
                      success: function (data) {
                          if (data.state == 'true') {
        					  parent.query();
                          } else {
                              $.etDialog.error(data.error)
                          }
                      }
                  });
			}else{
		/* 	ajaxJsonObjectByUrl("addStationDescription.do?isCheck=false", formPara, function(
					responseData) {
				if (responseData.state == "true") {
					
					  parent.query();
				}
			}); */
			 ajaxPostFormData({
                 url: 'addStationDescription.do?isCheck=false',
                 data: formData,
                 dataType: 'json',
                 success: function (data) {
                     if (data.state == 'true') {
   					  parent.query();
                     } else {
                         $.etDialog.error(data.error)
                     }
                 }
             });
			}
			
		})

		$("#close").click(function () {
			frameElement.dialog.close();
		})
       	query()
});
	  
	function loadDict() {
		var from=getFromField({ui:'et',group_id:'${group_id}',hos_id:'${hos_id}',formTable:['HR_STATION_MANAGE']})
	   	 $('.form_content').html(''); //初始化前清空
	   	 $('.form_content').height(150);
	 	var arr = new Array();
		var option=from.fieldItems;
	     $(dialogData.param).each(function () {
	         var rowdata = this;
	         Object.keys(rowdata).forEach(function(key){
	       		 arr.push({name: key,value: rowdata[key]})
	       		 }
	     )
		}); 
		 for (var i=0;i<arr.length;i++){
	  		var fieldItems;
	  		var name=arr[i].name;
	  		for(var j=0;j<option.length;j++){
	  			var optionName=option[j].id;
	  			if(name==optionName){
	  			if(option[j].type=='select'){
	  				if(name=="dept_id"){
	  					option[j].OPTIONS.defaultOption={id: dialogData.param[0].dept_id+'@'+ dialogData.param[0].dept_no, text:dialogData.param[0].dept_id_name}
	  				}
	   				option[j].OPTIONS.defaultValue=arr[i].value;;
	   				option[j].disabled='true';
	  			}else{
	  				option[j].value=arr[i].value;
	  				option[j].disabled='true';
	  			}	
	  			}}
	  	}
		 form = $('.form_content').etForm(from);
	     form.initWidget();
	     validate = form.initValidate();
	                         
	    //任职条件                 
	     var from1=getFromField({ui:'et',group_id:'${group_id}',hos_id:'${hos_id}',formTable:['HR_STATION_DESCRIPTION']})
	      $('.form_content1').html(''); //初始化前清空
	      $('.form_content1').height(150);
	      var arr1 = new Array();
	      var option=from1.fieldItems;
	      $(data).each(function () {
	         var rowdata = this;
	         Object.keys(rowdata).forEach(function(key){
	           arr1.push({name: key.toLowerCase(),value: rowdata[key]})
	         })
	}); 
	               		 for (var i=0;i<arr1.length;i++){
	               	  		var fieldItems;
	               	  		
	               	  		var name=arr1[i].name;
	               	  		
	               	  		for(var j=0;j<option.length;j++){
	               	  			var optionName=option[j].id;
	               	  			if(name==optionName){
	               	  			if(option[j].type=='select'){
	               	   				option[j].OPTIONS.defaultValue=arr1[i].value;;
	               	  			}else{
	               	  				option[j].value=arr1[i].value;
	               	  			}	
	               	  			}
	               	  		}
	               	  	}
	               form1 = $('.form_content1').etForm(from1);
	               form1.initWidget();
	               validate = form1.initValidate();
	}
	//查询
	function query(code) {
	
		  params = [
		             { name: 'station_code', value: dialogData.param[0].station_code}, 
		             { name: 'dept_id', value:dialogData.param[0].dept_id},
		             { name: 'dept_no', value: dialogData.param[0].dept_no } ,
		             { name: 'tab_code', value: 'HR_STATION_DESCRIPTION' } 
		        ];
		        grid.loadData(params,'queryStaDesProportion.do');
	}

	function loadHead() {
  	  var columns=getGridColumns({ui:'et',group_id:${group_id},hos_id:${hos_id},gridTables:['HR_STATION_DUTY'],design:'queryStaDesProportion.do'});
	    var paramObj = {
        		height: '100%',
            
                checkbox: true,
                showBottom:false,
              /*   dataModel: {
                	url: ''
                }, */
                columns: columns,
                editable:true,
                toolbar: {
                    items: [
                        { type: 'button', label: '保存', listeners: [{ click: saveGrid }], icon: 'save' },
                        { type: 'button', label: '添加', listeners: [{ click: add }], icon: 'add' },
                        { type: 'button', label: '删除', listeners: [{ click: remove }], icon: 'delete' },
                    ]
                }
        };

        grid = $("#mainGrid").etGrid(paramObj);
		//gridManager = $("#maingrid").ligerGetGridManager();
	}



	function add(){
		grid.addRow();
	}
function saveGrid(){

	
	var addData = grid.getAdded();//添加数据
	var modData = grid.getUpdated(); //修改数据'
	 var addDataVo = [];
    var modDataVo = [];
	var num = 0;
	if (addData.length==0&&modData.length==0) {
		$.etDialog.error('未获取到新增数据');
		return false;
	}
	if(modData.length==0&&addData.length==0){
		$.etDialog.error('未获取到修改数据');
		return false;
	}
	 $(addData).each(function () {
		 var rowdata = this.rowData;
    	if(rowdata.obligation!=undefined){
       addDataVo.push(rowdata)};
    });
    $(modData).each(function () {
    	 var rowdata = this.rowData;
    	if(rowdata.dept_id!=""){
        modDataVo.push(rowdata)};
    });

	
		
			
		if(modDataVo.length!=0){
			var modPara = {
					dept_no : dialogData.param[0].dept_no,
					dept_id: dialogData.param[0].dept_id,
					station_code : dialogData.param[0].station_code,
					tab_code : 'HR_STATION_DUTY', 
				
			
				ParamVo : JSON.stringify(modDataVo)
			};
			ajaxJsonObjectByUrl("updateStationDuty.do?isCheck=false", modPara, function(
					responseData) {
				if (responseData.state == "true") {
					  parent.query();
				}
			});
		}
		if(addDataVo.length!=0){{

			var formPara = {
					dept_no : dialogData.param[0].dept_no,
					dept_id: dialogData.param[0].dept_id,
					station_code : dialogData.param[0].station_code,
					tab_code :  'HR_STATION_DUTY', 
				
			
				ParamVo : JSON.stringify(addDataVo)};
			ajaxJsonObjectByUrl("addStationDuty.do?isCheck=false", formPara, function(
					responseData) {
				if (responseData.state == "true") {
					  parent.query();
				}
			});
		}	
		}
}

	function remove(){
		var data = grid.selectGet();
	    if (data.length == 0) {
	    	$.etDialog.error('请选择行');
	    } else {
	    	var param = [];
	        $(data).each(function () {
	        	   var rowdata = this.rowData;
	            rowdata.tab_code='HR_STATION_DUTY';
	            param.push(rowdata);
	        });
	     
	        $.etDialog.confirm('确定删除?' , function (yes){
	            	if(yes){
	                	ajaxJsonObjectByUrl("deleteStationDuty.do?isCheck=false",{paramVo: JSON.stringify(param)},function (responseData){
	                		if(responseData.state=="true"){
	                			query();
	                		}
	                	});
	            	}
	        });
	    }
	}
	
</script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>
	<div id="layout1">
		
		<div >  
		<div > 
		 <span class="title">基础信息</span>
		<div class='form_content' style="width:100%;overflow-x:auto" >
		</div>
		</div>
		  <div > 
		 <span class="title">所需资格</span>
		    <div class='form_content1' style="width:100%;overflow-x:auto"></div>
		    </div>
    <div class="button-group">
        <button id="save">保存</button>
        <button id="close">关闭</button>
    </div>
		
		  <div > 
		 <span class="title">工作职责</span>
			<div id="mainGrid"></div>
			</div>
		</div>


	</div>
</body>
</html>
