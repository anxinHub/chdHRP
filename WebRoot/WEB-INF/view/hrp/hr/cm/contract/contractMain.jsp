<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%String path = request.getContextPath();%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html style="overflow:hidden;"><head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="${path}/inc_jquery_1.9.0.jsp" />
<script src="<%=path%>/lib/et_components/et_plugins/etDialog.min.js"></script>
<script src="<%=path%>/lib/et_assets/common.js"></script>
<script src="<%=path%>/lib/et_assets/hr/common.js"></script>
<script src="<%=path%>/lib/hrp/hr/hr.js"></script>
<script src="<%=path%>/lib/et_components/et_plugins/etUpload.min.js"></script>
<script type="text/javascript">
    var grid;
    var gridManager = null;
	$(function () {
		loadDict();
		loadHead(null);	
	})
	//表格加载
	function loadHead(){
		var columns=getGridColumns({ui:'liger',group_id:'${group_id}',hos_id:'${hos_id}',gridTables:['HR_EMP_CONTRACT'],design:'queryContract.do'});
			grid = $("#maingrid").ligerGrid({
			columns: columns,
			dataAction: 'server',dataType: 'server',url:'queryContract.do',delayLoad :true,
			width: '100%', height: '100%', checkbox: true,rownumbers:true,usePager :true,enabledEdit : true,
			toolbar: {
				items: [
					{text: '查询', id:'search', click: query,icon:'search' },{line : true},
					{text: '续签合同', id:'add',click: add,icon: 'add' },{line : true},
					{text: '终止合同',id:'stop',click: stop,icon: 'delete'},{line : true},
					{text: '存档合同',id:'save',click: save,icon: 'save'},{line : true},
					{text: '上传图片附件',id:'import',click: importPh,icon: 'up'},{line : true},
					{text: '查看附件',id:'openResume',click:openResume,icon: 'communication'} 
				]
			},
			onDblClickRow : function(rowdata, rowindex, value) {
				openUpdate(rowdata.group_id + "|" + rowdata.hos_id
						+ "|" + rowdata.emp_id+ "|" 
						+ rowdata.beg_date+ "|" 
						+ rowdata.end_date);
			}
		});
		gridManager = $("#maingrid").ligerGetGridManager();  
	}
	//查询方法
	function query() {
		grid.options.parms=[];
		grid.options.parms.push({name:'emp_id',value:liger.get("emp_id").getValue()}); 
		grid.options.parms.push({name:'tab_code',value:'HR_EMP_CONTRACT'}); 
		grid.options.parms.push({name:'beg_date',value:$("#beg_date").val()}); 
		grid.options.parms.push({name:'end_date',value:$("#end_date").val()}); 
		grid.options.parms.push({name:'pro_beg_date',value:$("#pro_beg_date").val()}); 
		grid.options.parms.push({name:'pro_end_date',value:$("#pro_end_date").val()}); 
		grid.options.parms.push({name:'pro_type',value:liger.get("pro_type").getValue()}); 
		grid.options.parms.push({name:'pro_year',value:liger.get("pro_year").getValue()}); 
		grid.options.parms.push({name:'state',value:liger.get("state").getValue()}); 
		grid.options.parms.push({name:'rjt',value:"grid"}); 
		grid.options.parms.push({name:'design_code',value:"queryContract.do"}); 
		grid.loadData(grid.where);
	}
        
		//续签合同
        function add() {
        	var data = grid.getCheckedRows();
      
            	
            if (data.length == 0) {$.ligerDialog.error('请选择行');return;}
            else if(data.length > 1){$.ligerDialog.error('只能选择一行');return;
            }
            else{
            
            	var param = [];
                $(data).each(function () {
                    var rowdata = this;
                    rowdata.tab_code='HR_EMP_CONTRACT';
                    param.push(rowdata);
                });
 	
            
    			var parm ="&group_id="+ param[0].group_id  + "&hos_id=" +param[0].hos_id+ "&emp_id="+ param[0].emp_id+ "&end_date="+ param[0].end_date+ "&beg_date="+ param[0].beg_date+"&tab_code="+"HR_EMP_CONTRACT";
    			
            	//if(${updateStationBasicsDisplay}){
                parent.$.ligerDialog.open({
                    url: 'hrp/hr/cm/contract/empContractUpdatePage.do?isCheck=false' + parm,
                    title: '变更合同',
                    height : $(window).height()-200,
    				width : $(window).width(),
    				parentframename: window.name,  //用于parent弹出层调用本页面的方法或变量
                    buttons: [ { text: '确定', onclick: function (item, dialog) {dialog.frame.saveData();},cls:'l-dialog-btn-highlight' }, { text: '取消', onclick: function (item, dialog) { dialog.close(); } } ]
                
                });
            	}
		}
		function importPh(){
			var dataSelect = grid.getCheckedRows();
			if (dataSelect.length == 0|| dataSelect.length > 1) {
				$.ligerDialog.error('请选择一个员工');
				return ;
			}  
			if(dataSelect[0].img_attachment != null && dataSelect[0].img_attachment != ''){
				$.ligerDialog.confirm(
						'员工已经上传过照片，是否覆盖？',
						function(yes) {
							if (yes) {
								importResume(dataSelect);
							}
						});
			}else{
				importResume(dataSelect);
			}
	}
	
	function importResume(dataSelect){
		
	    var fileUpload;
         var dialogIndex = $.etDialog.open({
             content: '<div id="fileUpload"></div>',
             title: '文件上传',
             width: 400,
             height: 350,
             btn: ['上传', '关闭'],
             btn1: function () {
                 var fileValue = fileUpload.getValues();
                 var formData = new FormData();
				 var ParamVo = [];
                 fileValue.forEach((file) => {formData.append('files', file, file.name);});

                 ajaxPostFormData({
                     url: '../../fileUpload.do?isCheck=false',
                     data: formData,
                     success: function (res) {
                         if(res.data && Array.isArray(res.data)){
                        	 //业务处理，返回值是json数据，直接把这个数组保存到数据库字段里
                        	 
                        	   $(dataSelect).each(function () {
                    var rowdata = this;
                    rowdata.img_attachment=res.data;
                    ParamVo.push(rowdata);
                });
 	
                        	
                        	 $.post("updateEmpContract.do?isCheck=false&tab_code=HR_EMP_CONTRACT",  {ParamVo:JSON.stringify(ParamVo)},
							   function(data,status){
								   if(data.state == "true"){
									   query(dataSelect[0].emp_id); 
									   $.etDialog.success('上传成功！');
									   $.etDialog.close(dialogIndex);
								   }else{
									   $.etDialog.warn("上传失败！");
								   }
									
							 },"json");
                         }
                     }
                 });
             },
             success: function () {
                 fileUpload = $('#fileUpload').etUpload({
                     type: 'file',
                     multiple: true
                 });
             }
         });
	}
	  function openResume(){
		  var data = grid.getCheckedRows();
			if (data.length == 0|| data.length > 1) {
				$.ligerDialog.error('请选择一个员工');
				return ;
			}  else {
				var fileUpload;
				var dialogIndex = $.etDialog.open({
	                content: '<div id="fileUpload"></div>',
	                title: '文件查看',
	                width: 400,
	                height: 350,
	                btn: ['关闭'],
	                btn1: function () {
	                    $.etDialog.close(dialogIndex);
	                },
	                success: function () {
	                	fileUpload = $('#fileUpload').etUpload({
	                        type: 'file'
	                    });

	                    var valueStr = data[0].img_attachment;
	                    if (valueStr && Array.isArray(JSON.parse(valueStr))) {
	                        var fileArr = [];
	                        JSON.parse(valueStr).forEach((item) => {
	                            fileArr.push(item.url);
	                        });
	                        fileUpload.setValues(fileArr);
	                    }
	                }
	            });
			}
	  }
		//终止合同
        function stop() {
        	var data = grid.getCheckedRows();
        	//console.log(data);
        	
            if (data.length == 0) {$.ligerDialog.error('请选择行');return;}
            else if(data.length > 1){$.ligerDialog.error('只能选择一行');return;
            }
            else{
            
                	var param = [];
                 
                    var err ="";
                    
                    $(data).each(function (){
                    	
                  	 	 if(this.state!='04'){
       						var rowdata = this;
       			                      param.push(rowdata);
                      	  }else{
       						if(err == ""){
       							err = this.row_id;
       						}else{
       							err += "、"+this.row_id;
       						}
       					}
                    });
                    if (err != "") {
          				$.ligerDialog.warn("第["+err+"]行已终止，请检查状态！");
          				return;
          			}
              parent.$.ligerDialog.open({
                    url: 'hrp/hr/cm/contract/contractStopAddPage.do?isCheck=false',
                title: '终止合同',
                width:800,
                height: 300,
                data:{
                	param
                },
            	parentframename: window.name, 
                buttons : [ {
					text : '保存',
					onclick : function(item, dialog) {
						dialog.frame.saveData();
					},
					cls : 'l-dialog-btn-highlight'
				}, {
					text : '取消',
					onclick : function(item, dialog) {
						dialog.close();
					}
				} ] });
            }
		}
		//存档合同
		function save(){
			var data = grid.getCheckedRows();
            if (data.length == 0) {
            	$.ligerDialog.error('请选择行');
            } else {
            	
        		var param  = [];
             var err ="";
             
             $(data).each(function (){
             	
           	 	 if(this.state != '03'&&this.state!='04'){
						
								 var rowdata = this;
			                      rowdata.tab_code='HR_EMP_CONTRACT';
			                      rowdata.state='03';
			                      param.push(rowdata);
							
               	  }else{
						if(err == ""){
							err = this.row_id;
						}else{
							err += "、"+this.row_id;
						}
					}
             });
             if (err != "") {
   				$.ligerDialog.warn("第["+err+"]行已存档或已终止，请检查状态！");
   				return;
   			}
            	

                $.ligerDialog.confirm('确定存档?' , function (yes){
                    	if(yes){
                        	ajaxJsonObjectByUrl("updateEmpContract.do?isCheck=false",{ParamVo: JSON.stringify(param)},function (responseData){
                        		if(responseData.state=="true"){
                        			query();
                        		}
                        	});
                    	}
                });
            }
		}
		//变更合同
		var openUpdate =function (obj) {
        	var vo = obj.split("|");
			if("null"==vo[2]){
				return false;
				
			}
			//var parm ="&group_id="+ vo[0] + "&hos_id=" + vo[1]+ "&contract_no="+ vo[2]+"&tab_code="+"HR_EMP_CONTRACT";

			var parm ="&group_id="+ vo[0]  + "&hos_id=" +vo[1]+ "&emp_id="+ vo[2]+ "&end_date="+ vo[4]+ "&beg_date="+ vo[3]+"&tab_code="+"HR_EMP_CONTRACT";
			
        	//if(${updateStationBasicsDisplay}){
           parent.$.ligerDialog.open({
                    url: 'hrp/hr/cm/contract/empContractUpdatePage.do?isCheck=false' +parm,
                title: '变更合同',
                height : $(window).height()-200,
				width : $(window).width(),
				parentframename: window.name,  //用于parent弹出层调用本页面的方法或变量
                buttons: [ { text: '确定', onclick: function (item, dialog) {dialog.frame.saveData();},cls:'l-dialog-btn-highlight' }, { text: '取消', onclick: function (item, dialog) { dialog.close(); } } ]
            
            });
        }
		function loadDict(){
			autocomplete("#emp_id","../../baseSelect.do?isCheck=false&&field_tab_code=HOS_EMP","id","text",true,true,'',false,'',175);
			autocomplete("#pro_year","../../baseSelect.do?isCheck=false&&field_tab_code=DIC_PRO_YEAR","id","text",true,true,'','false','',175);
			autocomplete("#state","../../baseSelect.do?isCheck=false&&field_tab_code=DIC_PRO_STATE","id","text",true,true,'','false','',175);
			autocomplete("#pro_type","../../baseSelect.do?isCheck=false&&field_tab_code=DIC_PRO_TYPE","id","text",true,true,'','false','',175);
			autocomplete("#dept_id","../../baseSelect.do?isCheck=false&&field_tab_code=SYS_DEPT","id","text",true,true,'','false','',175);
			$("#beg_date").ligerTextBox({width:80});
			$("#end_date").ligerTextBox({width:80});
			$("#pro_beg_date").ligerTextBox({width:80});
			$("#pro_end_date").ligerTextBox({width:80});
		}
    </script>
</head>
<body>
	<div id="pageloading" class="l-loading" style="display: none"></div>
	<div id="toptoolbar"></div>
	<table cellpadding="0" cellspacing="0" class="l-table-edit" >
		<tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">职工：</td>
            <td align="left" class="l-table-edit-td"><input name="emp_id" type="text" id="emp_id" ltype="text" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">合同日期：</td>
            <td align="left" class="l-table-edit-td"><input name="beg_date" type="text" id="beg_date" ltype="text" class="Wdate"  id="resignation_date" ltype="text" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})" /></td>
            <td align="center">-</td>
            <td align="left" class="l-table-edit-td"><input name="end_date" type="text" id="end_date" ltype="text" class="Wdate"  id="resignation_date" ltype="text" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">试用日期：</td>
            <td align="left" class="l-table-edit-td"><input name="pro_beg_date" type="text" id="pro_beg_date" ltype="text" class="Wdate"  id="resignation_date" ltype="text" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})" /></td>
            <td align="center">-</td>
            <td align="left" class="l-table-edit-td"><input name="pro_end_date" type="text" id="pro_end_date" ltype="text" class="Wdate"  id="resignation_date" ltype="text" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})" /></td>
            <td align="left"></td>
        </tr> 
        </table>
        <table cellpadding="0" cellspacing="0" class="l-table-edit" >
        <tr>
            <!-- <td align="right" class="l-table-edit-td"  style="padding-left:20px;">科室：</td>
            <td align="left" class="l-table-edit-td"><input name="dept_id" type="text" id="dept_id" ltype="text" /></td>
            <td align="left"></td> -->
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">状态：</td>
            <td align="left" class="l-table-edit-td"><input name="state" type="text" id="state" ltype="text"/></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">合同类型：</td>
            <td align="left" class="l-table-edit-td"><input name="pro_type" type="text" id="pro_type" ltype="text"/></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">合同期限：</td>
            <td align="left" class="l-table-edit-td"><input name="pro_year" type="text" id="pro_year" ltype="text"/></td>
            <td align="left"></td>
        </tr> 
    </table>
	<div id="maingrid"></div>
</body>
</html>