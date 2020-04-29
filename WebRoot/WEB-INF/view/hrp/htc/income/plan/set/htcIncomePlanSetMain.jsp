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
    $(function ()
    {
        loadDict()//加载下拉框
    	//加载数据
    	loadHead(null);	
    });
    //查询
    function  query(){
    	grid.options.parms=[];
    	grid.options.newPage=1;
        //根据表字段进行添加查询条件
    	grid.options.parms.push({name:'plan_code',value:$("#plan_code").val()}); 
    	//加载查询条件
    	grid.loadData(grid.where);
    

     }


    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
           columns: [ 
                     { display: '方案编码', name: 'plan_code', align: 'left',
                    	 render: function (rowdata, rowindex, value){
                    		 return "<a href='#' onclick=\"openUpdate('"
						        + rowdata.group_id + "|"
						        + rowdata.hos_id + "|"
						        + rowdata.copy_code + "|"
						        + rowdata.acc_year + "|"
								+ rowdata.plan_code
								+ "');\" >"
								+ rowdata.plan_code + "</a>";
			           }
					 },
                     { display: '方案名称', name: 'plan_name', align: 'left'},
					 { display: '所属年度', name: 'acc_year', align: 'left'},
                     { display: '开始月', name: 'start_month', align: 'left'},
                     { display: '结束月', name: 'end_month', align: 'left'},
                     { display: '审核标志', name: 'is_check', align: 'left',
                    	 render: function (rowdata, rowindex, value){
                            if(rowdata.is_check == 0){
                                 return "未审核"
                             }else if(rowdata.is_check == 1){
                            	 return "审核"
                           } 
 			             }
			           },
                     {display: '核算方法', name: 'method', align: 'left',
                    	 render: function (rowdata, rowindex, value){
                    		 if(rowdata.method == "01"){
                                 return "作业成本法"
                             }else if(rowdata.method == "02"){
                            	 return "收入比法"
                             }else if(rowdata.method == "03"){
                            	 return "相对价值比率法"
                          }
                    	 }
			            },
                      {display: '是否当前方案', name: 'is_current', align: 'left',
	                    	 render: function (rowdata, rowindex, value){
	                    		 if(rowdata.is_current == 0){
	                                 return "否"
	                             }else if(rowdata.is_current == 1){
	                            	 return "是"
	                              } 
	 			             }
			            },
                     { display: '设置科室', name: 'set_dept', align: 'left',
                    	 render: function (rowdata, rowindex, value){
                    		 return "<a href='#' onclick=\"setDept('"
						        + rowdata.group_id + "|"
						        + rowdata.hos_id + "|"
						        + rowdata.copy_code + "|"
						        + rowdata.acc_year + "|"
								+ rowdata.plan_code
								+ "');\" >设置科室</a>";
 			              }
					    }
                     ],
                     dataAction: 'server',dataType: 'server',usePager:true,url:'queryHtcIncomePlanSet.do',
                     width: '100%', height: '100%',checkbox: true,rownumbers:true,delayLoad:true,
                     selectRowButtonOnly:true,//heightDiff: -10,
                     toolbar: { items: [
                     	{ text: '查询', id:'search', click: query,icon:'search' },
                     	{ line:true },
    					{ text: '添加', id:'add', click: add_open, icon:'add' },
    	                { line:true },
    	                { text: '删除', id:'delete', click: remove,icon:'delete' },
    	                { line:true }
    				]}
                   });

        gridManager = $("#maingrid").ligerGetGridManager();
    }

    function add_open(){
	    	$.ligerDialog.open({
	    	    url: 'htcIncomePlanSetAddPage.do?isCheck=false',
	    	    height: 300,
	    	    width: 500,
	    	    title: '添加',
	    	    modal: true,
	    	    showToggle: false,
	    	    showMax: false,
	    	    showMin: true,
	    	    isResize: true,
	    	    buttons: [{
	    	        text: '确定',
	    	        onclick: function(item, dialog) {
	    	            dialog.frame.savePlanSet();
	    	        },
	    	        cls: 'l-dialog-btn-highlight'
	    	    },
	    	    {
	    	        text: '取消',
	    	        onclick: function(item, dialog) {
	    	            dialog.close();
	    	        }
	    	    }]
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
	            			this.group_id + "@"
	            		+	this.hos_id + "@"
	            		+	this.copy_code + "@"
	            		+	this.acc_year + "@"
	            		+	this.plan_code	);//实际代码中temp替换主键
	            });
	            $.ligerDialog.confirm('确定删除?', function (yes){
	            	if(yes){
	                	ajaxJsonObjectByUrl("deleteHtcIncomePlanSet.do",{ParamVo : ParamVo.toString()},function (responseData){
	                		if(responseData.state=="true"){
	                			query();
	                		}
	                	});
	            	}
	            }); 
	        }
        }

    
    function openUpdate(obj){
        
    	var vo = obj.split("|");
    	
		var parm = "&" + "group_id="  + vo[0] 
				 + "&" + "hos_id="    + vo[1]
				 + "&" + "copy_code=" + vo[2] 
				 + "&" + "acc_year="  + vo[3]
				 + "&" + "plan_code=" + vo[4]
        
    	//实际代码中&temp替换主键
    	$.ligerDialog.open({
    	    url: 'htcIncomePlanSetUpdatePage.do?isCheck=false' + parm,
    	    data: {},
    	    height: 300,
    	    width: 500,
    	    title: '修改',
    	    modal: true,
    	    showToggle: false,
    	    showMax: false,
    	    showMin: false,
    	    isResize: true,
    	    buttons: [{
    	        text: '确定',
    	        onclick: function(item, dialog) {
    	            dialog.frame.savePlanSet();
    	        },
    	        cls: 'l-dialog-btn-highlight'
    	    },
    	    {
    	        text: '取消',
    	        onclick: function(item, dialog) {
    	            dialog.close();
    	        }
    	    }]
    	});

    }
    
    function setDept(obj){
    	//实际代码中&temp替换主键
    	var vo = obj.split("|");
		var parm = "&" + "group_id="  + vo[0] 
				 + "&" + "hos_id="    + vo[1]
				 + "&" + "copy_code=" + vo[2] 
				 + "&" + "acc_year="  + vo[3]
				 + "&" + "plan_code=" + vo[4]
    	$.ligerDialog.open({
    	    url: 'htcIncomePlanSetDeptPage.do?isCheck=false'+parm,
    	    data: {},
    	    height: 500,
    	    width: 1010,
    	    title: '设置科室',
    	    modal: true,
    	    showToggle: false,
    	    showMax: false,
    	    showMin: false,
    	    isResize: true,
    	    buttons: [{
    	        text: '确定',
    	        onclick: function(item, dialog) {
    	            dialog.frame.saveDeptSet();
    	        },
    	        cls: 'l-dialog-btn-highlight'
    	    },
    	    {
    	        text: '取消',
    	        onclick: function(item, dialog) {
    	            dialog.close();
    	        }
    	    }]
    	});

    }
    function loadDict(){
            //字典下拉框

        $("#plan_code").ligerTextBox({width:160});
        
    }   
    
    </script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<div id="toptoolbar" ></div>
    <table cellpadding="0" cellspacing="0" class="l-table-edit" >
        <tr>
			<td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">方案：</td>
            <td align="left" class="l-table-edit-td"><input name="plan_code" type="text" id="plan_code"/>
            </td>
        </tr> 
    </table>
	<div id="maingrid"></div>
</body>
</html>
