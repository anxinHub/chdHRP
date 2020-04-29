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
          	 $("#station_code").ligerTextBox({width:160});
          	$("#station_name").ligerTextBox({width:160});
        })
        
        //查询方法
           function query() {
        	grid.options.parms=[];
        	  grid.options.parms.push({name:'station_code',value:$("#station_code").val()}); 
        	  grid.options.parms.push({name:'tab_code',value:'HR_STATION_BASICS'}); 
        	  grid.options.parms.push({name:'station_name',value:$("#station_name").val()}); 
        	 // grid.options.parms.push({name:'rjt',value:"grid"}); //默认值可以省略
        	  grid.options.parms.push({name:'design_code',value:"queryStationBasics.do"}); 
        	  
            grid.loadData(grid.where);
           
        	
        }
        //表格加载
            function loadHead(){
          	  var columns=getGridColumns({ui:'liger',group_id:${group_id},hos_id:${hos_id},gridTables:['HR_STATION_BASICS'],design:'queryStationBasics'});

            	grid = $("#maingrid").ligerGrid({
                    columns:columns,

                  /*   columns: [ { display: '岗位编码',name: 'station_code',width: 100,algin:'left',
                    	render : function(rowdata, rowindex,
    							value) {
    						if(rowdata.station_code == "" || rowdata.station_code == null){
    							return "";
    							
    						}
                    		return "<a href=javascript:openUpdate('" + rowdata.group_id   + "|" + rowdata.hos_id + "|"  + rowdata.station_code +"')>"+rowdata.station_code+"</a>";
    					},
    					editor : {
    						type : 'text'
    					},
                    },
        			{ display: '岗位名称',name: 'station_name',width: 100,algin:'left',editor : {
						type : 'text'
					},},
        			{ display: '专业技术分类',name: 'technical_type_name',width: 200,algin:'left',
						render : function(rowdata, rowindex,
								value) {
							return rowdata.technical_type_name;
						},editor : {
						type : 'select',
						valueField : 'id',
						textField : 'text',
						url : '../../baseSelect.do?isCheck=false&field_tab_code=DIC_TECHNICAL_TYPE',
						keySupport : true,
						autocomplete : true
					},},
        			{ display: '岗位类别',name: 'station_type_name',width: 100,algin:'left',
						render : function(rowdata, rowindex,
								value) {
							return rowdata.station_type_name;
						},editor : {
						type : 'select',
						valueField : 'id',
						textField : 'text',
						url : '../../baseSelect.do?isCheck=false&field_tab_code=DIC_STATION_TYPE',
						keySupport : true,
						autocomplete : true
					},},
        			{ display: '岗位性质',name: 'station_nature_name',width: 100,algin:'left',
						render : function(rowdata, rowindex,
								value) {
							return rowdata.station_nature_name;
						},editor : {
						type : 'select',
						valueField : 'id',
						textField : 'text',
						url : '../../baseSelect.do?isCheck=false&field_tab_code=DIC_STATION_NATURE',
						keySupport : true,
						autocomplete : true
					},},
        			{ display: '是否停用',name: 'is_stop_name',width: 100,algin:'left',
        				render: function (rowdata, rowindex,
								value) {
                        if (value == "否") {
                            return  '否';
                        } else if(value == "是"){
                            return '<span style="color:red;"> 是</span>';

                        }else{
                        	return  '否';
                        }
                    },editor : {
						type : 'select',
						valueField : 'id',
						textField : 'text',
						data:[{id:1,text:'是'},{id:0,text:'否'}],
						keySupport : true,
						autocomplete : true
					},},
        			{ display: '备注',name: 'note',width: 100,editor : {
						type : 'text'
					},}
                              ], */
                              dataAction: 'server',dataType: 'server',url:'queryStationBasics.do',delayLoad :true,
                              width: '100%', height: '100%', checkbox: true,rownumbers:true,usePager :true,
                              enabledEdit : true,
  						onBeforeEdit : f_onBeforeEdit,
  					   // onChangeRow:onChangeRowSeve,
                        toolbar: {
                            items: [
                                {text: '查询（<u>E</u>）', id:'search', click: query,icon:'search' },
                            	{text: '添加（<u>A</u>）', id:'add',click: add,icon: 'add' },
                            	{text: '删除（<u>D</u>）',id:'remove',click: remove,icon: 'delete'},
                            	{text: '打印（<u>P</u>）',id:'print',click: print,icon: 'print'} ,
                            	{text: '导入（<u>I</u>）',id:'importMainGrid',click: importMainGrid,icon: 'up'} ,
                            	/* { text: '导出Excel',id:'down.',icon:'down', click: exportExcel,hide:!${exportStationBasicsDisplay} }, */
                            ]
                        },  
                        onDblClickRow  : function(data,rowid,rowdata)  {
							openUpdate(data.group_id + "|" + 
									data.hos_id+ "|" + 
									data.station_code );
						}
                    
                            });

                 gridManager = $("#maingrid").ligerGetGridManager();
          
        }

        function add() {
        	 parent.$.ligerDialog.open({
                url: 'hrp/hr/os/stationbasics/stationBasicsAddPage.do?isCheck=false',
                title: '添加',
                height : $(window).height()-200,
				width : $(window).width(),
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
    	function f_onBeforeEdit(rowData) {
    		 if(rowData.record.__status!="add"&&rowData.column.columnindex == 2){
       			 return false ;
       		 }else{
       			 return true ;
       		 }
    		
    	}
    	
    	//换行保存
    	function  onChangeRowSeve(e){
    		if(e.record.__status=="update"){
		 		ajaxJsonObjectByUrl(
 		 				'updateStationBasics.do',
 		 				{
 		 					 station_code: e.record.station_code,
 		 	                 station_name : e.record.station_name,
 		 	                 technical_type: e.record.technical_type,
 		 	                 station_type :e.record.station_type,
 		 	                 station_nature: e.record.station_nature,
 		 	                 is_stop:e.record.is_stop,
 		 	                 note:e.record.note,
 		 	            	tab_code: 'HR_STATION_BASICS'
 		 				},
 		 				function(responseData) {
 		 					if (responseData.state == "true") {
 		 						query();
 		 					}
 		 		});
 		 	}
    	}
        var openUpdate = function (obj) {
        	var vo = obj.split("|");
			if("null"==vo[2]){
				return false;
				
			}
			var parm ="&group_id="+ 
			  vo[0] + "&hos_id=" + 
			  vo[1]+ "&station_code="+
			  vo[2]+"&tab_code="+"HR_STATION_BASICS";
			
        
			 parent.$.ligerDialog.open({
	                url: 'hrp/hr/os/stationbasics/stationBasicsUpdatePage.do?isCheck=false' +parm,
                title: '修改',
                height : $(window).height()-200,
				width : $(window).width(),
                buttons: [ { text: '确定', onclick: function (item, dialog) {dialog.frame.saveData();},cls:'l-dialog-btn-highlight' }, { text: '取消', onclick: function (item, dialog) { dialog.close(); } } ]
            
            })
        	
        }
        
        function remove() {
        	var data = grid.getCheckedRows();
            if (data.length == 0) {
            	$.ligerDialog.error('请选择行');
            } else {
            	var param = [];
                $(data).each(function () {
                	  var rowdata = this;
                      rowdata.tab_code='HR_STATION_BASICS';
                      param.push(rowdata);
                });

                $.ligerDialog.confirm('确定删除?' , function (yes){
                    	if(yes){
                        	ajaxJsonObjectByUrl("deleteStationBasics.do",{paramVo: JSON.stringify(param)},function (responseData){
                        		if(responseData.state=="true"){
                        			query();
                        		}
                        	});
                    	}
                });
            }
        }
        
		//打印
        function print() {
        	if(grid.getData()==null){
        		$.etDialog.error("请先查询数据！");
    			return;
    		}
        	var heads = {};
        	
        	var printPara={
          		title: "岗位标准基础数据",//标题
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
           	
        }
		
	 	//导入
        function importMainGrid() {
	 		
        	 $.ligerDialog.open({
                 url: "statinBasicsImprotPage.do?isCheck=false&tab_code=" + 'HR_STATION_BASICS'+"&ui="+"liger",
             	parentframename : window.name,
                 height : $(window).height(),
					width : $(window).width(),
             })
	 	
	 	} 
      //导出excel
		function exportExcel(){
            location.href = "exportExcelStationBasics.do?isCheck=false&tab_code="+'HR_STATION_BASICS';
			
		}
    </script>
</head>

<body>
  
<div id="pageloading" class="l-loading" style="display: none"></div>

	<div id="toptoolbar" ></div>
    <table cellpadding="0" cellspacing="0" class="l-table-edit" >
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">岗位编码：</td>
            <td align="left" class="l-table-edit-td"><input name="station_code" ltype="text" id="station_code" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">岗位名称：</td>
            <td align="left" class="l-table-edit-td"><input name="station_name" ltype="text" id="station_name" ltype="text" validate="{required:true,maxlength:20}" /></td>
            
            <td align="left"></td>
        </tr> 
    </table>
            <div id="maingrid"></div>
     
</body>

</html>