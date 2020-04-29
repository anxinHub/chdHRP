<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%> 
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html style="overflow:hidden;">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link href="<%=path %>/lib/ligerUI/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css" />
<link href="<%=path %>/lib/ligerUI/skins/ligerui-icons.css" rel="stylesheet" type="text/css" />
<script src="<%=path %>/lib/jquery/jquery-1.9.0.min.js" type="text/javascript"></script>
<script src="<%=path %>/lib/hrp.js" type="text/javascript"></script>
<script src="<%=path %>/lib/ligerUI/js/ligerui.all.js" type="text/javascript"></script>
<script src="<%=path%>/lib/json2.js"></script>
<script src="<%=path%>/lib/My97DatePicker/WdatePicker.js"	type="text/javascript"></script>
<link rel="stylesheet" href="<%=path %>/lib/Z-tree/css/zTreeStyle/zTreeStyle.css" type="text/css">
<script type="text/javascript" src="<%=path %>/lib/Z-tree/js/jquery.ztree.core-3.5.js"></script>
<script type="text/javascript" src="<%=path %>/lib/Z-tree/js/jquery.ztree.excheck-3.5.js"></script>
<script type="text/javascript" src="<%=path %>/lib/Z-tree/js/jquery.ztree.exedit-3.5.js"></script>
<script src="<%=path%>/pageoffice.js" type="text/javascript" id="po_js_main"></script>
<script src="<%=path%>/lib/stringbuffer.js"></script>
 <script src="<%=path%>/lib/et_components/et_plugins/etSelect.min.js" type="text/javascript"></script>
  <script src="<%=path%>/lib/et_components/et_plugins/etDatepicker.min.js"></script>
<script type="text/javascript">

//合并单元格 主要实现代码
    //合并单元格 根据tankAlias进行合并   
jQuery.fn.rowspan = function (colname, tableObj) {
    var colIdx , colTank;
   
        for (var i = 0, n = tableObj.columns.length; i < n; i++) {
          
            if (tableObj.columns[i]["columnname"] == colname) {
                colIdx = i ;
                
                break;
            }
        }
    
    return this.each(function () {
        var that;
        $('tr', this).each(function (row, element) {
            var a = $(element).children();
　　　　 //tankAlias对应的列的数组
            var tankText = $('td:eq(' + colTank + ')', this);
            $('td:eq(' + colIdx + ')', this).filter(':visible').each(function (i,col) {
            /*   var innerText = tankText.init(i).innerText;
              //console.log(this);
                var flag = false;
                if(innerText){
                    flag = true;;
              } */
              if (that != null && $(this).html() == $(that).html()&&$(this).text().length>5) {
                  rowspan = $(that).attr("rowSpan");
                  if (rowspan == undefined) {
                      $(that).attr("rowSpan", 1);
                      rowspan = $(that).attr("rowSpan");
                      
                  }
                  //单元格是否为空 tankAlias为空不合并单元格 
             
                    rowspan = Number(rowspan) + 1;
                      $(that).attr("rowSpan", rowspan); 
                      $(this).hide();
                  
              } else {
                  that = this;
              }
            });
        });
    });
}

var grid;

var gridManager = null;
var year_month;
var userUpdateStr;
	$(function () {
		initDict();
		//leftGrid();
		 loadHead(null); //加载数据
		
		// 给输入框绑定搜索树事件
	    $(".text-input").on('keyup', function () {
	        var $self = $(this);
	        searchTree({
	            tree: tree,
	            value: $self.val(),
	            callback: function () {
	                $self.focus();
	            }
	        })
	    })
	});

	function initDict() {
		
		  /*  $("#emp_code").ligerComboBox({
		       	 url:'../../queryEmpSelectAttend.do?isCheck=false',
			       		 valueField: 'id',
			                textField: 'text'/* ,
			    			 cancelable:true */
			       	/* ,width:160}); */
		   emp_code = $("#emp_code").etSelect({
			    url: '../../queryEmpSelectAttend.do?isCheck=false',
			  
			    
			});
	/*     $("#dept_id_c").ligerComboBox({
	       	 url:'../../queryHosDeptSelect.do?isCheck=false',
		       		 valueField: 'id',
		                textField: 'text',
		                isShowCheckBox: true, 
		                isMultiSelect: true,
		    			 cancelable:true,
		    			 setEnabled :true
		       	 ,width:160}); */
	    
	    dept_id_c = $("#dept_id_c").etSelect({
		    url: '../../queryHosDeptSelect.do?isCheck=false',
		    defaultValue: "none",
		   // checkboxMode: true,
		    checkboxMode: true,
		    
		});
		       	attend_item = $("#attend_item").etSelect({
		 		    url: '../../queryAttendCodeCla.do?isCheck=false',
		 		    defaultValue: "none",
		 		    checkboxMode: true,
		 		    
		 		});
	
		
		 	kind_code = $("#kind_code").etSelect({
				    url: '../../queryHrEmpKindSelect.do?isCheck=false',
				    defaultValue: "none",
				    checkboxMode: true,
				    
				});
		 	
		 	yh_code = $("#yh_code").etSelect({
				    url: '../../queryAttendFieldOption.do?isCheck=false',
				    defaultValue: "none",
				    checkboxMode: true,
				    
				});
		
       	year_month = $("#year_month").etDatepicker({
            view: "months",
            minView: "months",
            dateFormat: "yyyy-mm",
            defaultDate: true
   		});
	
	/* 	ajaxPostData({
			url: '../../queryAttendCodeCla.do?isCheck=false',
			async: true, 
			success: function (data) {
				$.each(data, function(){
					attend_codes.push(
						{"label": this.text, "id": this.id}
					)
				})
			}
		}) */
	};
	
	var query = function (queryFor) {
		loadHead();
		var arrDept =dept_id_c.getValue();
		var deptIds = []; 
		if(arrDept){
		arrDept.forEach(v => {
			deptIds.push( v.split("@")[1] );
		});}
		grid.options.parms=[];
		        grid.options.parms.push({name:'year_month',value:year_month.getValue().replace("-", "")}); 
	   	    	grid.options.parms.push({name:'emp_id',value:emp_code.getValue()}); 
	   	    	grid.options.parms.push({name:'dept_id_c_s',value:deptIds == 'undefined' ? '':deptIds}); 
	   	    	grid.options.parms.push({name:'kind_code',value:kind_code.getValue()}); 
	   	    	grid.options.parms.push({name:'attend_item',value:attend_item.getValue()}); 
	   	    	grid.options.parms.push({name:'yh_code',value:yh_code.getValue()}); 
	        
	   //	grid.loadData(grid.where,'queryAttendResultItem.do');  
	   	grid.set("url", "queryAttendResultItem.do");
    /* 	//重新查询后对象应为空
    	saveObj= {};
    	//查询
        leftGrid.loadData(params, "queryAttendResultItem.do"); */
    }; 
    
 	/* 左表 基础列头 */
/*	var leftcolumns = [
		
		{display : '出勤科室',name : 'dept_name_c',width : 100,editable: false},
		{display : '职工编码',name : 'emp_code',width : 60,editable: false},
		{display : '职工名称',name : 'emp_name',width : 60,editable: false}
	];
	
	*/
    
	/* 生成日期的列 */
	/*var genegrateMonthsColumns = function() {
		var columns = [];
		var date = year_month.getValue().replace("-", "");
		var year = year_month.getValue().substr(0, 4);
		 $.ajax({
	            type: "POST",
			  url: 'queryResultItemHead.do?isCheck=false&item_code='+,
			  dataType: "json",
	            async: false,
	            success: function(data) {
	                if (data.Rows.length > 0) {
	                    $.each(data.Rows, function(i, v) {
	                    
	                    })
				 	  jsonHead = eval(data.jsonHead);
					  $.each(jsonHead,function(index, v){
		    				if(v.name){
		    					headData[v.name] = v.date;
		    					columns.push({
		    						display : v.display ,
		    						name : v.name,
		    						width : 50,
		    					
		    					})
		    			}
	    			}); 
				}
				  
				  
				var newColumns = leftcolumns.concat(columns);
				leftGrid.option('columns', newColumns);
				leftGrid.refreshView();
			},
		});
	}; 
	 */
	function loadHead() {
		var columns = "";
		columns = columns + "{ display: '出勤科室', name: 'dept_name_c', align: 'left',frozen: true,width:'100'},"
		    + "{ display: '职工名称', name: 'emp_name', align: 'left',frozen: true,width:'100'}";

        para = "";
        sumPara = "";
        var item;
        
     if(attend_item.getValue()!=''&&attend_item.getItem().length>=2){

         for(j = 0; j < attend_item.getValue().length; j++) {
        	 item+=attend_item.getValue()[j]+',';
         } 
         item=item.substring(9,item.length-1);
     }else{
    	 item=attend_item.getValue();
     };
     //
        $.ajax({
            type: "POST",
            url: "queryResultItemHead.do?isCheck=false",
            data: {
                'item_code': item

            },
            dataType: "json",
            async: false,
            success: function(data) {
                if (data.Rows.length > 0) {
                    $.each(data.Rows, function(i, v) {
                        columns = columns + ",{ display: '" + v.ATTEND_SHORTNAME + "', name: '" + v.ATTEND_ITEM + "', align: 'right',width:'50'" +"}";
                    
                    });
                  //  columns = columns.substr(0, columns.length - 1);
                
                }
                
                grid = $("#maingrid").ligerGrid({
                    columns: eval("[" + columns + "]"),
                    dataAction: 'server',
                    dataType: 'server',
                    usePager: true,
                    url: '',
                   /*  onAfterEdit: f_onAfterEdit,
                    onBeforeEdit: f_onBeforeEdit, */
                    height: '96%', 
                    rownumbers: true,
                    minColumnWidth: 100,
                    clickToEdit: true,
                    selectRowButtonOnly: true,
                    delayLoad: true,
                  //  columnWidth: '15%',
                    enabledEdit: true,
                   // lodopExportExcel:true,
                    // contextmenuEidtor: lodopExportExcel,
                    toolbar: {
                        items: [
                            { text: '查询', id: 'search', click: query, icon: 'search' },
                           
                            { text: '打印', id:'print', click: printData,icon:'print' },
                            { line:true } 
                        ]
                    },
                          onAfterShowData: function (s) {
                    	                setTimeout(function () {
                    	                    $('#maingrid .l-grid-body-table tbody').rowspan('dept_name_c', grid)
                    	                })
                    	            }
                    
                });

                gridManager = $("#maingrid").ligerGetGridManager();
            }
        });
    }

	
	//打印
	function printData(){	
		if(grid.getData().length==0){
    		$.etDialog.error("请先查询数据！");
			return;
		}
    	var heads = {};
    	
    	var printPara={
          		title: "考勤项目统计表",//标题
          		columns: JSON.stringify(grid.getPrintColumns()),//表头
          		class_name: "com.chd.hrp.hr.service.attendancemanagement.attendresult.HrAttendResultItemService",
       			bean_name: "hrAttendResultItemService",
       			method_name: "queryAttendResultItemPrint",
       			//heads: JSON.stringify(heads),//表头需要打印的查询条件,可以为空
       			
           	};
        	
        	//执行方法的查询条件
       		$.each(grid.options.parms,function(i,obj){
       			printPara[obj.name]=obj.value;
        	});
       		
        	officeGridPrint(printPara);
        	
	}
	
	
</script>    


<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading"></div>
	 <table cellpadding="0" cellspacing="0" class="l-table-edit" >
        <tr>
             <td align="right" class="l-table-edit-td"  style="padding-left:20px;">考勤周期：</td>
            <td align="left" class="l-table-edit-td"><input name="year_month" type="text" id="year_month" ltype="text" style="width: 160px" validate="{required:true,maxlength:50}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">出勤科室：</td>
            <td align="left" class="l-table-edit-td"><input name="dept_id_c" type="text" id="dept_id_c" ltype="text" style="width: 160px" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">职工类别：</td>
            <td align="left" class="l-table-edit-td"><input name="kind_code" type="text" id="kind_code" ltype="text" style="width: 160px" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
           
           
       </tr>
       <tr>
          <td align="right" class="l-table-edit-td"  style="padding-left:20px;">考勤项目：</td>
            <td align="left" class="l-table-edit-td"><input name="attend_item" type="text" id="attend_item" ltype="text" style="width: 160px"  validate="{required:true,maxlength:50}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">职工：</td>
            <td align="left" class="l-table-edit-td"><input name="emp_code" type="text" id="emp_code" ltype="text" style="width: 160px" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">医护属性：</td>
            <td align="left" class="l-table-edit-td"><input name="yh_code" type="text" id="yh_code" ltype="text" style="width: 160px" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
           
       
       </tr>
      </table>
      <div id="maingrid"></div>
	
</body>
</html>