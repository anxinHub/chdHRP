<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html style="overflow:hidden;">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="${path}/inc_jquery_1.9.0.jsp" />
<script type="text/javascript">
    var grid;
    var gridManager = null;
    var userUpdateStr;
    $(function ()
    {
        loadDict()//加载下拉框
    	//加载数据
    	loadHead(null);	
		 loadHotkeys();
        $("#eq_unit_code").ligerTextBox({width:160});
        $("#eo_eq_group").ligerTextBox({width:160});
        $("#main_flag").ligerTextBox({width:160});
        $("#income_rate").ligerTextBox({width:160});
        $("#expend_rate").ligerTextBox({width:160});
        $("#from_date").ligerTextBox({width:160});
        $("#to_date").ligerTextBox({width:160});
        $("#update_date").ligerTextBox({width:160});
        $("#update_time").ligerTextBox({width:160});
        $("#update_userdr").ligerTextBox({width:160});
        $("#invalid_flag").ligerTextBox({width:160});
        $("#remark").ligerTextBox({width:160});
		
    });
    //查询
    function  query(){
    		grid.options.parms=[];
    		grid.options.newPage=1;
        //根据表字段进行添加查询条件
    	  grid.options.parms.push({name:'eq_unit_code',value:$("#eq_unit_code").val()}); 
    	  grid.options.parms.push({name:'eo_eq_group',value:$("#eo_eq_group").val()}); 
    	  grid.options.parms.push({name:'main_flag',value:$("#main_flag").val()}); 
    	  grid.options.parms.push({name:'income_rate',value:$("#income_rate").val()}); 
    	  grid.options.parms.push({name:'expend_rate',value:$("#expend_rate").val()}); 
    	  grid.options.parms.push({name:'from_date',value:$("#from_date").val()}); 
    	  grid.options.parms.push({name:'to_date',value:$("#to_date").val()}); 
    	  grid.options.parms.push({name:'update_date',value:$("#update_date").val()}); 
    	  grid.options.parms.push({name:'update_time',value:$("#update_time").val()}); 
    	  grid.options.parms.push({name:'update_userdr',value:$("#update_userdr").val()}); 
    	  grid.options.parms.push({name:'invalid_flag',value:$("#invalid_flag").val()}); 
    	  grid.options.parms.push({name:'remark',value:$("#remark").val()}); 

    	//加载查询条件
    	grid.loadData(grid.where);
		$("#resultPrint > table > tbody").empty();
     }
    //获取查询条件的数值
    function f_getWhere(){
    	if (!grid) return null;
        var clause = function (rowdata, rowindex){
                	if($("#eq_unit_code").val()!=""){
                		return rowdata.eq_unit_code.indexOf($("#eq_unit_code").val()) > -1;	
                	}
                	if($("#eo_eq_group").val()!=""){
                		return rowdata.eo_eq_group.indexOf($("#eo_eq_group").val()) > -1;	
                	}
                	if($("#main_flag").val()!=""){
                		return rowdata.main_flag.indexOf($("#main_flag").val()) > -1;	
                	}
                	if($("#income_rate").val()!=""){
                		return rowdata.income_rate.indexOf($("#income_rate").val()) > -1;	
                	}
                	if($("#expend_rate").val()!=""){
                		return rowdata.expend_rate.indexOf($("#expend_rate").val()) > -1;	
                	}
                	if($("#from_date").val()!=""){
                		return rowdata.from_date.indexOf($("#from_date").val()) > -1;	
                	}
                	if($("#to_date").val()!=""){
                		return rowdata.to_date.indexOf($("#to_date").val()) > -1;	
                	}
                	if($("#update_date").val()!=""){
                		return rowdata.update_date.indexOf($("#update_date").val()) > -1;	
                	}
                	if($("#update_time").val()!=""){
                		return rowdata.update_time.indexOf($("#update_time").val()) > -1;	
                	}
                	if($("#update_userdr").val()!=""){
                		return rowdata.update_userdr.indexOf($("#update_userdr").val()) > -1;	
                	}
                	if($("#invalid_flag").val()!=""){
                		return rowdata.invalid_flag.indexOf($("#invalid_flag").val()) > -1;	
                	}
                	if($("#remark").val()!=""){
                		return rowdata.remark.indexOf($("#remark").val()) > -1;	
                	}
        };
        return clause; 
    }

    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
           columns: [ 
                     { display: '计量编码（HOS_UNIT）', name: 'eq_unit_code', align: 'left'
					 		},
                     { display: '单价', name: 'eo_eq_group', align: 'left'
					 		},
                     { display: '更新日期', name: 'main_flag', align: 'left'
					 		},
                     { display: '备注', name: 'income_rate', align: 'left'
					 		},
                     { display: '无效标志', name: 'expend_rate', align: 'left'
					 		},
                     { display: 'glRemark', name: 'from_date', align: 'left'
					 		},
                     { display: 'grTodate', name: 'to_date', align: 'left'
					 		},
                     { display: 'glUpdatedate', name: 'update_date', align: 'left'
					 		},
                     { display: 'glUpdatetime', name: 'update_time', align: 'left'
					 		},
                     { display: 'glUpdateuserdr', name: 'update_userdr', align: 'left'
					 		},
                     { display: 'glInvalidflag', name: 'invalid_flag', align: 'left'
					 		},
                     { display: 'glSort', name: 'remark', align: 'left'
					 		}
                     ],
                     dataAction: 'server',dataType: 'server',usePager:true,url:'queryAssEqgroupdetail.do',
                     width: '100%', height: '100%', checkbox: true,rownumbers:true,
                     selectRowButtonOnly:true,//heightDiff: -10,
                     toolbar: { items: [
                     	{ text: '查询（<u>E</u>）', id:'search', click: query,icon:'search' },
                     	{ line:true },
				    					{ text: '添加（<u>A</u>）', id:'add', click: add_open, icon:'add' },
				    	                { line:true },
				    	                { text: '删除（<u>D</u>）', id:'delete', click: remove,icon:'delete' },
						                { line:true },
						                { text: '下载导入模板（<u>B</u>）', id:'downTemplate', click:downTemplate,icon:'down' },
						                { line:true },
						                { text: '导入（<u>I</u>）', id:'import', click: imp,icon:'up' }
				    				]},
    				onDblClickRow : function (rowdata, rowindex, value)
    				{
						openUpdate(
								rowdata.group_id   + "|" + 
								rowdata.hos_id   + "|" + 
								rowdata.copy_code   + "|" + 
								rowdata.eq_unit_code   + "|" + 
								rowdata.eo_eq_group 
							);
    				} 
                   });

        gridManager = $("#maingrid").ligerGetGridManager();
    }
    
    function add_open(){
    	
		$.ligerDialog.open({ url : 'assEqgroupdetailAddPage.do?isCheck=false&',data:{}, height: 300,width: 450, title:'00机组明细 ASS_EQGroupDetail',modal:true,showToggle:false,showMax:false,showMin: false,isResize:true,
			buttons: [ { text: '确定', onclick: function (item, dialog) { dialog.frame.saveassEqgroupdetail(); },cls:'l-dialog-btn-highlight' },
			           { text: '取消', onclick: function (item, dialog) { dialog.close(); } } 
			          ]
    	}); 
		
    	/*var index = layer.open({
					type : 2,
					title : '00机组明细 ASS_EQGroupDetail',
					shadeClose : false,
					shade : false,
					maxmin : true, //开启最大化最小化按钮
					area : [ '893px', '500px' ],
					content : 'assEqgroupdetailAddPage.do?isCheck=false'
				});
				layer.full(index);
				*/
    	}
    	
    function remove(){
    	
    	var data = gridManager.getCheckedRows();
                    if (data.length == 0){
                    	$.ligerDialog.error('请选择行');
                    }else{
                        var ParamVo =[];
                        $(data).each(function (){					
							ParamVo.push(
							this.group_id   +"@"+ 
							this.hos_id   +"@"+ 
							this.copy_code   +"@"+ 
							this.eq_unit_code   +"@"+ 
							this.eo_eq_group 
							) });
                        $.ligerDialog.confirm('确定删除?', function (yes){
                        	if(yes){
                            	ajaxJsonObjectByUrl("deleteAssEqgroupdetail.do",{ParamVo : ParamVo.toString()},function (responseData){
                            		if(responseData.state=="true"){
                            			query();
                            		}
                            	});
                        	}
                        }); 
                    }
    	}
    function imp(){
    	
    	var index = layer.open({
					type : 2,
					title : '导入',
					shadeClose : false,
					shade : false,
					maxmin : true, //开启最大化最小化按钮
					area : [ '893px', '500px' ],
					content : 'assEqgroupdetailImportPage.do?isCheck=false'
				});
				layer.full(index);
    	}	
    function downTemplate(){
    	
    	location.href = "downTemplate.do?isCheck=false";
    	}	
   
    function openUpdate(obj){
    		
		var vo = obj.split("|");
		var parm = 
			"group_id="+vo[0]   +"&"+ 
			"hos_id="+vo[1]   +"&"+ 
			"copy_code="+vo[2]   +"&"+ 
			"eq_unit_code="+vo[3]   +"&"+ 
			"eo_eq_group="+vo[4] 
		 
		 
		 $.ligerDialog.open({ url : 'assEqgroupdetailUpdatePage.do?isCheck=false&',data:{}, height: 300,width: 450, title:'00机组明细 ASS_EQGroupDetail',modal:true,showToggle:false,showMax:false,showMin: false,isResize:true,
			buttons: [ { text: '确定', onclick: function (item, dialog) { dialog.frame.saveassEqgroupdetail(); },cls:'l-dialog-btn-highlight' },
			           { text: '取消', onclick: function (item, dialog) { dialog.close(); } } 
			          ]
    	}); 
		 
    /*var index = layer.open({
					type : 2,
					title : '更新',
					shadeClose : false,
					shade : false,
					maxmin : true, //开启最大化最小化按钮
					area : [ '893px', '500px' ],
					content : 'assEqgroupdetailUpdatePage.do?isCheck=false&' + parm
				});
				layer.full(index);	
    */
    }
    function loadDict(){
            //字典下拉框
            
         }  
    //键盘事件
	  function loadHotkeys() {

		hotkeys('Q', query);

		hotkeys('A', add);
		hotkeys('D', remove);

		hotkeys('B', downTemplate);

		hotkeys('E', exportExcel);

		hotkeys('P', printDate);
		hotkeys('I', imp);
		

	 }
 	  
    </script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<div id="toptoolbar" ></div>
    <table cellpadding="0" cellspacing="0" class="l-table-edit" >
        <tr>
        </tr> 
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">计量编码（HOS_UNIT）：</td>
            <td align="left" class="l-table-edit-td"><input name="eq_unit_code" type="text" id="eq_unit_code" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">单价：</td>
            <td align="left" class="l-table-edit-td"><input name="eo_eq_group" type="text" id="eo_eq_group" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">更新日期：</td>
            <td align="left" class="l-table-edit-td"><input name="main_flag" type="text" id="main_flag" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
        </tr> 
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">备注：</td>
            <td align="left" class="l-table-edit-td"><input name="income_rate" type="text" id="income_rate" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">无效标志：</td>
            <td align="left" class="l-table-edit-td"><input name="expend_rate" type="text" id="expend_rate" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">glRemark：</td>
            <td align="left" class="l-table-edit-td"><input name="from_date" type="text" id="from_date" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
        </tr> 
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">grTodate：</td>
            <td align="left" class="l-table-edit-td"><input name="to_date" type="text" id="to_date" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">glUpdatedate：</td>
            <td align="left" class="l-table-edit-td"><input name="update_date" type="text" id="update_date" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">glUpdatetime：</td>
            <td align="left" class="l-table-edit-td"><input name="update_time" type="text" id="update_time" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
        </tr> 
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">glUpdateuserdr：</td>
            <td align="left" class="l-table-edit-td"><input name="update_userdr" type="text" id="update_userdr" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">glInvalidflag：</td>
            <td align="left" class="l-table-edit-td"><input name="invalid_flag" type="text" id="invalid_flag" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">glSort：</td>
            <td align="left" class="l-table-edit-td"><input name="remark" type="text" id="remark" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
        </tr> 
    </table>

	<div id="maingrid"></div>
	
</body>
</html>
