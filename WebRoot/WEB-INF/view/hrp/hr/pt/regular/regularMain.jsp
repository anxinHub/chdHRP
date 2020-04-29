<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html style="overflow:hidden;">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link href="<%=path %>/lib/ligerUI/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css" />
<link href="<%=path %>/lib/ligerUI/skins/ligerui-icons.css" rel="stylesheet" type="text/css" />
<script src="<%=path %>/lib/jquery/jquery-1.3.2.min.js" type="text/javascript"></script>
<script src="<%=path %>/lib/hrp.js" type="text/javascript"></script>
<script src="<%=path %>/lib/ligerUI/js/ligerui.all.js" type="text/javascript"></script>
<script src="<%=path%>/lib/json2.js"></script>
<script src="<%=path%>/lib/My97DatePicker/WdatePicker.js"	type="text/javascript"></script>
<jsp:include page="${path}/inc_jquery_1.9.0.jsp" />
   <script src="<%=path%>/lib/hrp/hr/hr.js"></script> 

<script type="text/javascript">
    var grid;
    var gridManager = null;
   
     
        
        $(function () {
        	 // loadDict()//加载下拉框
          	//加载数据
          	loadHead(null);	
          	 $("#create_date").ligerTextBox({width:160});
          	//$("#station_name").ligerTextBox({width:160});

        })
        
        //查询方法
           function query() {
        	grid.options.parms=[];
        	  grid.options.parms.push({name:'create_date',value:$("#create_date").val()}); 
        	  grid.options.parms.push({name:'tab_code',value:'HR_REGULAR'}); 
        	//  grid.options.parms.push({name:'station_name',value:$("#station_name").val()}); 
        	 // grid.options.parms.push({name:'rjt',value:"grid"}); //默认值可以省略
        	  grid.options.parms.push({name:'design_code',value:"queryRegular.do"}); 
        	  
            grid.loadData(grid.where);
           
        	
        }
        //表格加载
            function loadHead(){
            	  var columns=getGridColumns({ui:'liger',group_id:${group_id},hos_id:${hos_id},gridTables:['HR_REGULAR'],desin:'queryRegular.do'});

            	grid = $("#maingrid").ligerGrid({
                    columns:columns,
                              dataAction: 'server',dataType: 'server',url:'queryRegular.do',delayLoad :true,
                              width: '100%', height: '100%', checkbox: true,rownumbers:true,usePager :true,
                              enabledEdit : true,
  						onBeforeEdit : f_onBeforeEdit,
                        toolbar: {
                            items: [
                                {text: '查询（<u>E</u>）', id:'search', click: query,icon:'search' }, 
                            	{text: '添加（<u>A</u>）', id:'add',click: add,icon: 'add' },
                            	{text: '删除（<u>D</u>）',id:'remove',click: remove,icon: 'delete'}, 
                            	//{text: '打印（<u>P</u>）',id:'print',click: print,icon: 'print'} , 
                            	{text: '导入（<u>I</u>）',id:'importMainGrid',click: importMainGrid,icon: 'up' } , 
                            	{ text: '提交', id:'auditRegularState', click:auditRegularState,icon:'audit'},
            	                { text: '试用科室审核', id:'auditTeacher', click:auditTeacher,icon:'audit',hide:'${updateRegularTeacherBatch}'==""?true:false },
            	                { text: '职能科室审核', id:'auditDept', click:auditDept,icon:'audit',hide:'${updateRegularDeptBatch}'==""?true:false },
            	                { text: '分管副院长审核', id:'auditCharge', click:auditCharge,icon:'audit',hide:'${updateRegularChargeBatch}'==""?true:false },
            	                { text: '人力资源部审核', id:'auditHR', click:auditHR,icon:'audit',hide:'${updateRegularHRBatch}'==""?true:false },
            	                { text: '院长审核', id:'auditPresident', click:auditPresident,icon:'audit' ,hide:'${updateRegularPresidentBatch}'==""?true:false},
            	            	{text: '模板设置', click: function (item){printSet()}, icon:'settings'}, { line:true },
            					{text: '模板打印', click: function (item){templatePrint()}, icon:'print'}, { line:true }
                           
                            ]
                        },
                        onDblClickRow  : function(data,rowid,rowdata)  {
							openUpdate(data);
						}
                            });

                 gridManager = $("#maingrid").ligerGetGridManager();
          
        }

        function add() {
        	$.ligerDialog.open({
                url: 'regularAddPage.do?isCheck=false',
                title: '添加',
                width: 450,
                height: 500,
                buttons : [ {
					text : '确定',
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
  function auditRegularState(){
        	var data = gridManager.getCheckedRows();
       
        if (data.length == 0){
         	$.ligerDialog.error('请选择行');
         }else{
        		var ParamVo  = [];
             var err ="";
             
             $(data).each(function (){
             	
           	 	 if(this.state == '0'){
						var rowdata = this;
							ParamVo.push({
								 state:'1',
				                   	tab_code: 'HR_REGULAR',
				                   	regular_id:rowdata.regular_id
							});
               	  }else{
						if(err == ""){
							err = this.row_id;
						}else{
							err += "、"+this.row_id;
						}
					}
             });
             if (err != "") {
   				$.ligerDialog.warn("第["+err+"]行转正申请已经提交，提交失败！");
   				return;
   			}
             ajaxJsonObjectByUrl("updateRegularStateBatch.do?isCheck=false",{ParamVo : JSON.stringify(ParamVo)}, function(
 					responseData) {
 		   if(responseData.state=="true"){
 				
                query();
             }
 			});
             }
        }
        function auditTeacher(){
        	var data = gridManager.getCheckedRows();
       
        if (data.length == 0){
         	$.ligerDialog.error('请选择行');
         }else{
             var ParamVo =[];
             var err ="";
             
             $(data).each(function (){
           	 	 if(this.state == '1'){
						var rowdata = this;
						rowdata.regular_state='auditTeacher';
						ParamVo.push(rowdata);
               	  }else{
						if(err == ""){
							err = this.row_id;
						}else{
							err += "、"+this.row_id;
						}
					}
             });
             if (err != "") {
   				$.ligerDialog.warn("第["+err+"]行转正申请未提交，请提交后审核！");
   				return;
   			}
             $.ligerDialog.open({url: 'regularStateMainPage.do?isCheck=false&',data:ParamVo, height: 400,width: 400, title:'审核',modal:true,showToggle:false,showMax:false,showMin: true,isResize:true});

             }
        }
        function auditDept(){

        	var data = gridManager.getCheckedRows();
       
        if (data.length == 0){
         	$.ligerDialog.error('请选择行');
         }else{
             var ParamVo =[];
             var err ="";
             
             $(data).each(function (){
           	 	 if(this.teacher_state == '1'){
						var rowdata = this;
						rowdata.regular_state='auditDept';
						ParamVo.push(rowdata);
               	  }else{
						if(err == ""){
							err = this.row_id;
						}else{
							err += "、"+this.row_id;
						}
					}
             });
             if (err != "") {
   				$.ligerDialog.warn("第["+err+"]行转正申请试用科室未审核，审核失败！");
   				return;
   			}
             $.ligerDialog.open({url: 'regularStateMainPage.do?isCheck=false&',data:ParamVo, height: 400,width: 400, title:'审核',modal:true,showToggle:false,showMax:false,showMin: true,isResize:true});

             }
        }
        
        function auditCharge(){
        	var data = gridManager.getCheckedRows();
            
            if (data.length == 0){
             	$.ligerDialog.error('请选择行');
             }else{
                 var ParamVo =[];
                 var err ="";
                 
                 $(data).each(function (){
               	 	 if(this.dept_state == '1'){
    						var rowdata = this;
    						rowdata.regular_state='auditCharge';
    						ParamVo.push(rowdata);
                   	  }else{
    						if(err == ""){
    							err = this.row_id;
    						}else{
    							err += "、"+this.row_id;
    						}
    					}
                 });
                 if (err != "") {
       				$.ligerDialog.warn("第["+err+"]行转正申请职能科室未审核，审核失败！");
       				return;
       			}
                 $.ligerDialog.open({url: 'regularStateMainPage.do?isCheck=false&',data:ParamVo, height: 400,width: 400, title:'审核',modal:true,showToggle:false,showMax:false,showMin: true,isResize:true});

                 }
        }
        function auditHR(){
        var data = gridManager.getCheckedRows();
            
            if (data.length == 0){
             	$.ligerDialog.error('请选择行');
             }else{
                 var ParamVo =[];
                 var err ="";
                 
                 $(data).each(function (){
               	 	 if(this.charge_state == '1'){
    						var rowdata = this;
    						rowdata.regular_state='auditHR';
    						ParamVo.push(rowdata);
                   	  }else{
    						if(err == ""){
    							err = this.row_id;
    						}else{
    							err += "、"+this.row_id;
    						}
    					}
                 });
                 if (err != "") {
       				$.ligerDialog.warn("第["+err+"]行转正申请分管副院长未审核，审核失败！");
       				return;
       			}
                 $.ligerDialog.open({url: 'regularStateMainPage.do?isCheck=false&',data:ParamVo, height: 400,width: 400, title:'审核',modal:true,showToggle:false,showMax:false,showMin: true,isResize:true});

                 }
        }
        function auditPresident(){
        var data = gridManager.getCheckedRows();
            
            if (data.length == 0){
             	$.ligerDialog.error('请选择行');
             }else{
                 var ParamVo =[];
                 var err ="";
                 
                 $(data).each(function (){
               	 	 if(this.hr_state == '1'){
    						var rowdata = this;
    						rowdata.regular_state='auditPresident';
    						ParamVo.push(rowdata);
                   	  }else{
    						if(err == ""){
    							err = this.row_id;
    						}else{
    							err += "、"+this.row_id;
    						}
    					}
                 });
                 if (err != "") {
       				$.ligerDialog.warn("第["+err+"]行转正申请人力资源部未审核，审核失败！");
       				return;
       			}
                 $.ligerDialog.open({url: 'regularStateMainPage.do?isCheck=false&',data:ParamVo, height: 400,width: 400, title:'审核',modal:true,showToggle:false,showMax:false,showMin: true,isResize:true});

                 }
        }
    	function f_onBeforeEdit(rowData) {
    		 if(rowData.record.__status!="add"&&rowData.column.columnindex == 2){
       			 return false ;
       		 }else{
       			 return true ;
       		 }
    		
    	}
    	
       function openUpdate(data) {
       
			var parm ="&group_id="+ 
			 data.group_id + "&hos_id=" + 
			  data.hos_id+ "&regular_id="+
			  data.regular_id+"&tab_code="+"HR_REGULAR";
			
       
            $.ligerDialog.open({
                url: 'regularUpdatePage.do?isCheck=false' +parm,
                title: '修改',
                width: 450,
                height: 500,
                buttons: [ { text: '确定', onclick: function (item, dialog) {dialog.frame.saveData();},cls:'l-dialog-btn-highlight' }, { text: '取消', onclick: function (item, dialog) { dialog.close(); } } ]
            
            })
        	
        }
        
        function remove() {
        	var data = grid.getCheckedRows();
            if (data.length == 0) {
            	$.ligerDialog.error('请选择行');
            } else {
            	var param = [];
            	var err="";
                $(data).each(function () {
                	if(this.state=='0'){
                	  var rowdata = this;
                      rowdata.tab_code='HR_REGULAR';
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
       				$.ligerDialog.warn("第["+err+"]行转正申请已提交！");
       				return;
       			}
                $.ligerDialog.confirm('确定删除?' , function (yes){
                    	if(yes){
                        	ajaxJsonObjectByUrl("deleteRegular.do",{paramVo: JSON.stringify(param)},function (responseData){
                        		if(responseData.state=="true"){
                        			query();
                        		}
                        	});
                    	}
                });
            }
        }
    /*     
		//打印
        function print() {
        	if(grid.getData()==null){
        		$.etDialog.error("请先查询数据！");
    			return;
    		}
        	var heads = {};
        	
        	var printPara={
          		title: "转正申请",//标题
          		columns: JSON.stringify(grid.getPrintColumns()),//表头
          		class_name: "com.chd.hrp.hr.service.QueryService",
       			bean_name: "queryService",
       			method_name: "queryBaseInfoPtint",
       			heads: JSON.stringify(heads),//表头需要打印的查询条件,可以为空
       			foots: ''//表尾需要打印的查询条件,可以为空 
           	};
        	
           	$.each(grid.options.parms,function(i,obj){
         		printPara[obj.name]=obj.value;
           	}); 
           	
           	officeGridPrint(printPara);
           	
        } */
      //打印模板设置
    	function printSet(){
    		//use_id (0统一打印，1按用户打印，2按仓库打印，3按供应商打印)
    		officeFormTemplate({template_code: "06103",use_id: 0});
    	}
    	
    	//模板打印
        function templatePrint(){
        	var params = {
        			template_code: '06103', //必填
        			use_id: 0, //默认0，(0统一打印，1按用户打印，2按仓库打印，3按供应商打印)
        			design_code: 'queryRegular.do', //必填 查询设计器编码
        			id_column: 'REGULAR_ID', //默认'EMP_ID'
        	};
        	
        	//查询条件
         	$.each(grid.options.parms,function(i,obj){
         		params[obj.name]=obj.value;
           	}); 
           	
            hrTemplatePrint(params);
        }
	 	//导入
        function importMainGrid() {
        	/*   var para = {"column": main_grid.getPrintColumns().rows}
    		importSpreadView("hrp/hr/os/stationbasics/importDataStationBasics.do?isCheck=false&tab_code="+'HR_REGULAR', para); 
        	  */
        	 $.ligerDialog.open({
                 url: "regularImprotPage.do?isCheck=false&tab_code=" + 'HR_REGULAR'+"&ui="+"liger",
             	parentframename : window.name,
                 height : $(window).height(),
					width : $(window).width(),
             })
	 	} 
      //导出excel
		function exportExcel(){
            location.href = "exportExcelStationBasics.do?isCheck=false&tab_code="+'HR_REGULAR';
			
		}
    </script>
</head>

<body>
  
<div id="pageloading" class="l-loading" style="display: none"></div>

	<div id="toptoolbar" ></div>
    <table cellpadding="0" cellspacing="0" class="l-table-edit" >
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">转正申请日期：</td>
            <td align="left" class="l-table-edit-td"><input name="create_date" ltype="text"  class="Wdate" id="create_date" ltype="text" onFocus="WdatePicker({isShowClear:true,readOnly:false,dateFmt:'yyyy-MM-dd'})" /></td>
            <td align="left"></td>
           <!--  <td align="right" class="l-table-edit-td"  style="padding-left:20px;">岗位名称：</td>
            <td align="left" class="l-table-edit-td"><input name="station_name" ltype="text" id="station_name" ltype="text" validate="{required:true,maxlength:20}" /></td>
            
            <td align="left"></td> -->
        </tr> 
    </table>
            <div id="maingrid"></div>
     
</body>

</html>