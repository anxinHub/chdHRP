<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<jsp:include page="${path}/resource.jsp">
	<jsp:param value="dialog,hr.select,grid,datepicker,upload" name="plugins" />
</jsp:include>
<script>
	var the_date, dept_code, emp_id,cert_date;
	var tree, grid;
	var state=null ;
	var initFrom = function() {
		cert_date = $("#cert_date").etDatepicker({
         	range : true,
         	onChange: query,
         });
		
	};
	var query = function() {
		var params = [ { name: 'begin_date', value: cert_date.getValue()[0] || '' },
                       { name: 'end_date', value: cert_date.getValue()[1] || '' },
                       { name: 'cert_name', value: $("#cert_name").val() },
                       { name: 'state', value: state }
                       ];
		grid.loadData(params);
	};

	var save = function() {
		var ParamVo = [];
		   var isPass = grid.validateTest({
               required: {
            	   begin_date: true,
            	   cert_name: true
               }
           });
           if (!isPass) {
               return;
           }
        var data = grid.selectGet();
             if (data.length == 0) {
                 $.etDialog.error('请选择行');
                 return;
             } else {
                   $(data).each(function () {
                       var rowdata = this.rowData;
                       /* if(rowdata.dept_code!=null){
                      	 rowdata.dept_id = rowdata.dept_code.split('@')[1]
                       } */
                       ParamVo.push(rowdata);
                   }) 
                   }
             //验证重复数据
            	if (!grid.checkRepeat(
            			grid.selectGet(),
            			['cert_name']
            	)		
            	) {
                   return;
               }
		ajaxPostData({
			url : 'addCertificate.do',
			data : {
				paramVo : JSON.stringify(ParamVo)
			},
			success : function() {
				query()
			}
		})
	};

	var add = function() {
		grid.addRow();
	};
	var remove = function() {
		var selectData = grid.selectGet();
		if (selectData.length === 0) {
			$.etDialog.error('请选择行');
			return;
		}
		var param = [];
		selectData.forEach(function(item) {
			param.push({
				cert_code : item.rowData.cert_code,
				cert_name : item.rowData.cert_name
			});
		})
		
		$.etDialog.confirm('确定删除?', function () {
				ajaxPostData({
					url : 'deleteCertificate.do',
					data : {
						paramVo : JSON.stringify(param)
					},
					success : function() {
						grid.deleteRows(selectData);
					}
				})
		});
	};
	 //导入数据
    function putin(){
		//$("form[name=fileForm]").submit();
		var para = {
			"column" : [ {
				"name" : "cert_name",
				"display" : "证书名称",
				"width" : "400",
				"require" : true
			},{
				"name" : "begin_date",
				"display" : "有效期开始",
				"width" : "200",
				"require" : true
			},{
				"name" : "end_date",
				"display" : "有效期结束",
				"width" : "200"
			}]

		};
		importSpreadView("/hrp/hr/medicalmanagement/certificate/importCertificate.do?isCheck=false", para);
	}
	
	var putout = function() {
		exportGrid(grid);
	};

	var initGrid = function() {
		var yearEditor = getRecentYearForSelect();
		var columns = [
				{
					display : '证书编码',
					name : 'cert_code',
					width : 120, editable: false
				},
				{
					display : '证书名称',
					name : 'cert_name',
					width : 320 ,editable : function(col){
	                  	if(col.rowData){
	                   		if(col.rowData.hos_id!=null){
	                   			return false;
	                   		}
	                   		return true;
	                   	}else{
	                   		return true;
	                   	}
	                   }
				},
				{
					display : '有效期开始',
					name : 'begin_date',
					width : 150,
					editor : {
						type : 'date',
						dateFormat : 'yy-mm-dd',
					},
                    editable : function(col){
                       	if(col.rowData){
                    		if(col.rowData.hos_id!=null){
                    			return false;
                    		}
                    		return true;
                    	}else{
                    		return true;
                    	}
                    }
				},{
					display : '有效期结束',
					name : 'end_date',
					width : 150,
					editor : {
						type : 'date',
						dateFormat : 'yy-mm-dd',
					},
                    editable : function(col){
                       	if(col.rowData){
                    		if(col.rowData.hos_id!=null){
                    			return false;
                    		}
                    		return true;
                    	}else{
                    		return true;
                    	}
                    }
				},
				 {
						display : '天数',
						name : 'inva_day',
						width : 120, editable: false
					}, { display: '状态', name: 'state', align: 'center',width:100, editable: false,
			 		  	render:function(rowdata,index,value){
			 				if(value =='过期'){
			 					return '<span style="color:red">过期</span>';
			 				}else if(value == '到期'){
			 					return '<span style="color:blue">到期</span>';
			 				}else if(value =='临近'){
			 					return '<span style="color:#00009C">临近</span>';
			 				}else if(value =='安全'){
			 					return '<span style="color:#00FF7F">安全</span>';
			 				}
			 			}  
			 		}

		];
		var paramObj = {
			height : '100%',
			inWindowHeight : true,
			checkbox : true,
			editable : true,
			dataModel : {
				url : 'queryCertificate.do'
			},
			columns : columns,
			toolbar : {
				items : [ {
					type : 'button',
					label : '查询',
					listeners : [ {
						click : query
					} ],
					icon : 'search'
				}, {
					type : 'button',
					label : '保存',
					listeners : [ {
						click : save
					} ],
					icon : 'save'
				}, {
					type : 'button',
					label : '添加',
					listeners : [ {
						click : add
					} ],
					icon : 'add'
				}, {
					type : 'button',
					label : '删除',
					listeners : [ {
						click : remove
					} ],
					icon : 'delete'
				}, {
					type : 'button',
					label : '导入',
					listeners : [ {
						click : putin
					} ],
					icon : 'import'
				} ]
			}
		};
		grid = $("#mainGrid").etGrid(paramObj);

	};

	$(function() {
		initFrom();
		initGrid();
		
		$("#near").change(function(){
			
			if($("#near").prop("checked") == true){
				state = 1 ;
				
				$('#last').prop('checked',false) ;
				$('#past').prop('checked',false) ;
			}else{
				state = '' ;
			}
			query();
		})
		$("#last").change(function(){
			if($("#last").prop("checked") == true){
				state = 2 ;
				
				$('#near').prop('checked',false) ;
				$('#past').prop('checked',false) ;
			}else{
				state = '' ;
			}
			query();
		})
		$("#past").change(function(){
			if($("#past").prop("checked") == true){
				state = 3 ;
				
				$('#near').prop('checked',false) ;
				$('#last').prop('checked',false) ;
			}else{
				state = '' ;
			}
			query();
		})

		
	})
</script>
</head>

<body>
	<div class="main">
		<table class="table-layout">
			<tr>
                <td class="label">日期：</td>
                <td class="ipt">
                <input id="cert_date" type="text" />
                <td class="label">证件名称：</td>
                <td class="ipt">
                <input id="cert_name" type="text" />
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">查询内容：</td>
            	<td align="left" class="l-table-edit-td" colspan="2">
	            <input id="near" type="checkbox" ltype="text" />临近
	            <input id="last" type="checkbox" ltype="text" />到期
	            <input id="past" type="checkbox" ltype="text" />过期
	        </td>
	        <td align="left"></td>
                
              </tr>
		</table>
	</div>
	<div id="mainGrid"></div>
</body>

</html>