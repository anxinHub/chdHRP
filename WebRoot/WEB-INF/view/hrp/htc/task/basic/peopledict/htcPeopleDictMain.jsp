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

    function loadDict(){
        //字典下拉框
		autocomplete("#title_code","../../../info/base/queryHtcPeopleTitleDict.do?isCheck=false","id","text",true,false);
		autocomplete("#people_type_code","../../../info/base/queryHtcPeopleTypeDict.do?isCheck=false","id","text",true,true);
		$("#people_code").ligerTextBox({width:160});
	} 
    
    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
           columns: [ 
                     { display: '人员编码', name: 'people_code', align: 'left',
                    	 render: function (rowdata, rowindex, value){
                            return "<a href='#' onclick=\"openUpdate('"+rowdata.people_code+"');\" >"+rowdata.people_code+"</a>";
			           }
					 },
                     { display: '人员姓名', name: 'people_name', align: 'left'},
                     { display: '职称', name: 'title_name', align: 'left'},
                     { display: '人员类别', name: 'people_type_name', align: 'left'},
                     { display: '科室', name: 'dept_name', align: 'left'},
                     { display: '备注', name: 'people_note', align: 'left'},
                     { display: '是否停用', name: 'is_stop', align: 'left',				
							render : function(rowdata, rowindex, value) {
								return rowdata.is_stop == 1 ? "是" : "否";
							}
                      },
                     ],
                     dataAction: 'server',dataType: 'server',usePager:true,url:'queryHtcPeopleDict.do',
                     width: '100%', height: '100%', checkbox: true,rownumbers:true,delayLoad:true,
                     selectRowButtonOnly:true,//heightDiff: -10,
                     toolbar: { items: [
                     	{ text: '查询', id:'search', click: query,icon:'search' },
                     	{ line:true },
    					{ text: '添加', id:'add', click: add_open, icon:'add' },
    	                { line:true },
    	                { text: '删除', id:'delete', click: remove,icon:'delete' },
    	                { line:true },
    	                {text : '同步',id : 'synchro',click : synchro,icon : 'bluebook'},
						 {line : true},
		                { text: '导入', id:'import', click: imp,icon:'up' },
		                { line:true },
    				]},
    				onDblClickRow : function (rowdata, rowindex, value)
    				{
    					openUpdate(rowdata.people_code);//实际代码中temp替换主键
    				} 
                   });

        gridManager = $("#maingrid").ligerGetGridManager();
    }

    //查询
    function  query(){
    		grid.options.parms=[];
    		grid.options.newPage=1;
        //根据表字段进行添加查询条件
    	grid.options.parms.push({name:'people_code',value:$("#people_code").val()}); 
    	grid.options.parms.push({name:'title_code',value:liger.get("title_code").getValue()}); 
    	grid.options.parms.push({name:'people_type_code',value:liger.get("people_type_code").getValue()}); 
    	//加载查询条件
    	$("#resultPrint > table > tbody").html("");
    	grid.loadData(grid.where);
     }
    //添加
	function add_open(){
		$.ligerDialog.open({
			url: 'htcPeopleDictAddPage.do?isCheck=false',
			height: 330,
			width: 350,
			title: '添加',
			modal: true,
			showToggle: false,
			showMax: false,
			showMin: true,
			isResize: true,
		    buttons: [{
		    	text: '确定',
		    	onclick: function(item, dialog) {
		    		dialog.frame.savePeopleDict();
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
    
    //删除
    function remove(){
    	var data = gridManager.getCheckedRows();
		if (data.length == 0) {
			$.ligerDialog.error('请选择行');
		} else {
			var ParamVo = [];
			$(data).each(function() {
				ParamVo.push(
					this.people_code
				);//实际代码中temp替换主键
			});
			$.ligerDialog.confirm('确定删除?', function(yes) {
				if (yes) {
					ajaxJsonObjectByUrl("deleteHtcPeopleDict.do", {
						ParamVo : ParamVo.toString()
					}, function(responseData) {
						if (responseData.state == "true") {
							query();
						}
					});
				}
			});
		}
    }
    //更新
    function openUpdate(obj){
    	//实际代码中&temp替换主键
    	$.ligerDialog.open({ 
    		url: 'htcPeopleDictUpdatePage.do?isCheck=false&people_code='+obj,data:{}, 
    		height: 330,
    		width: 350, 
    		title:'修改',
    		modal:true,
    		showToggle:false,
    		showMax:false,
    		showMin: false,
    		isResize:true,
    		buttons: [ 
    			{ 
    				text: '确定',
    				onclick: function (item, dialog) { 
    					dialog.frame.savePeopleDict(); 
    				},
    				cls:'l-dialog-btn-highlight' 
    			}, 
    			{ 
    				text: '取消', 
    				onclick: function (item, dialog) { 
    					dialog.close(); 
    				} 
    			} 
    		] 
    	});
    }
 

   function synchro(){

		ajaxJsonObjectByUrl("synchroHtcPeopleDict.do",null,function(responseData) {
			if (responseData.state == "true") {
				query()
			}
		});
		    
	}

	function imp(){
		var para={
			    "column": [
			        {
			            "name": "people_code",
			            "display": "人员编码",
			            "width": "200",
			            "require":true
			        },{
			            "name": "people_name",
			            "display": "人员名称",
			            "width": "200",
			            "require":true
			        },{
			            "name": "title_code",
			            "display": "职称编码",
			            "width": "200",
			            "require":true
			        },{
			            "name": "title_name",
			            "display": "职称名称",
			            "width": "200",
			            "require":true
			        },{
			            "name": "people_type_code",
			            "display": "人员类别编码",
			            "width": "200",
			            "require":true
			        },{
			            "name": "people_type_name",
			            "display": "人员类别名称",
			            "width": "200",
			            "require":true
			        },{
			            "name": "dept_code",
			            "display": "科室编码",
			            "width": "200",
			            "require":true
			        },{
			            "name": "dept_name",
			            "display": "科室名称",
			            "width": "200",
			            "require":true
			        },{
			            "name": "people_note",
			            "display": "备注",
			            "width": "200"
			        }
			    ]
			};
		
		  importSpreadView("hrp/htc/task/basic/peopledict/impHtcPeopleDict.do?isCheck=false",para);
		
	}
    </script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<div id="toptoolbar" ></div>
    <table cellpadding="0" cellspacing="0" class="l-table-edit" >
	
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">人员信息：</td>
            <td align="left" class="l-table-edit-td"><input name="people_code" type="text" id="people_code" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">职称编码：</td>
            <td align="left" class="l-table-edit-td"><input name="title_code" type="text" id="title_code" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">人员类别编码：</td>
            <td align="left" class="l-table-edit-td"><input name="people_type_code" type="text" id="people_type_code" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
        </tr> 
    </table>
	<div id="maingrid"></div>
</body>
</html>
