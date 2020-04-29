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
    var gridObj;
    var parm=null;
    var gridManager = null;
    var userUpdateStr;
    $(function ()
    {
        loadDict()//加载下拉框
    	//加载数据
    	loadHead(null);	
        loadHead2();
		loadHotkeys();

        $("#rep_team_code").ligerTextBox({width:160});
        $("#rep_team_name").ligerTextBox({width:160});
        $("#is_stop").ligerTextBox({width:160});
		
    });
    //查询
    function  query(){
    		grid.options.parms=[];
    		grid.options.newPage=1;
        //根据表字段进行添加查询条件
    	  grid.options.parms.push({name:'rep_team_code',value:$("#rep_team_code").val()}); 
    	  grid.options.parms.push({name:'rep_team_name',value:$("#rep_team_name").val()}); 
    	  grid.options.parms.push({name:'is_stop',value:liger.get("is_stop").getValue()}); 

    	//加载查询条件
    	grid.loadData(grid.where);
		$("#resultPrint > table > tbody").empty();
     }
    //获取查询条件的数值
    function f_getWhere(){
    	if (!grid) return null;
        var clause = function (rowdata, rowindex){
                	if($("#rep_team_code").val()!=""){
                		return rowdata.rep_team_code.indexOf($("#rep_team_code").val()) > -1;	
                	}
                	if($("#rep_team_name").val()!=""){
                		return rowdata.rep_team_name.indexOf($("#rep_team_name").val()) > -1;	
                	}
                	if($("#is_stop").val()!=""){
                		return rowdata.is_stop.indexOf($("#is_stop").val()) > -1;	
                	}
        };
        return clause; 
    }

    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
           columns: [ 
                     { display: '班组编码', name: 'rep_team_code', align: 'left',frozen:true,
                    	 render : function(rowdata, rowindex,value) {
                    		 
   							return "<a href=javascript:openUpdate('"+rowdata.group_id   + "|" + 
 																	rowdata.hos_id   + "|" + 
 																	rowdata.rep_team_code   +"')>"+
 																	value+"</a>";
   										}
					 		},
                     { display: '班组名称', name: 'rep_team_name', align: 'left'
					 		},
                     { display: '是否停用', name: 'is_stop_name', align: 'left'
					 		},
                     { display: '职工', name: '操作', align: 'left' ,
			 			render : function(rowdata, rowindex,value) {
              		 
					return "<a href=javascript:queryRepUser('"+rowdata.rep_team_code+"')>操作</a>";
								}
					 		}
                     ],
                     dataAction: 'server',dataType: 'server',usePager:true,url:'queryAssRepairTeamDict.do',
                     width: '30%', height: '100%', checkbox: true,rownumbers:true,
                     selectRowButtonOnly:true,//heightDiff: -10,
                     toolbar: { items: [
                     	{ text: '查询（<u>E</u>）', id:'search', click: query,icon:'search' },
                     	{ line:true },
    					{ text: '添加（<u>A</u>）', id:'add', click: add_open, icon:'add' },
    	                { line:true },
    	                { text: '删除（<u>D</u>）', id:'delete', click: remove,icon:'delete' }
				    				]},
    				onDblClickRow : function (rowdata, rowindex, value)
    				{
						openUpdate(
								rowdata.group_id   + "|" + 
								rowdata.hos_id   + "|" + 
								rowdata.rep_team_code 
							);
    				} 
                   });
    	

        gridManager = $("#maingrid").ligerGetGridManager();
    }
    function loadHead2() {
    	gridObj = $("#usergrid").ligerGrid({
            columns: [ 
                      { display: '人员编码', name: 'user_code', align: 'left',
                     	 render : function(rowdata, rowindex,value) {
    							return "<a href=javascript:openUserUpdate('"+rowdata.user_id+"')>"+value+"</a>";
                     	 }
			 		  },
                      { display: '人员名称', name: 'user_name', align: 'left'
 					 		},
                      { display: '是否停用', name: 'is_stop_name', align: 'left'
 					 		},
                      { display: '院内电话', name: 'phone1', align: 'left'
 					 		},
                      { display: '联系电话', name: 'phone2', align: 'left'
 					 		},
                      { display: '排序号', name: 'sort_code', align: 'left'
 					 		}
                      
                      ],
                      dataAction: 'server',
                      dataType: 'server',
                      usePager:true,
                      url:'queryRepUser.do?isCheck=false',
                      width: '49%', 
                      height: '100%', 
                      checkbox: true,
                      rownumbers:true,
                      delayLoad:true,
                      selectRowButtonOnly:true,//heightDiff: -10,
                      toolbar: { items: [
                      	{ text: '查询（<u>E</u>）', id:'search', click: queryRepUser,icon:'search' },
                      	{ line:true },
     					{ text: '添加（<u>A</u>）', id:'add', click: addUserOpen, icon:'add' },
     	                { line:true },
     	                { text: '删除（<u>D</u>）', id:'delete', click: removeUser,icon:'delete' }
 				    				]}
     				 
                    });
    	gridObj._onResize()
    }
    
    function queryRepUser(obj){
    	if(typeof obj ==='string'){
    		parm=obj
    	}
 	 	gridObj.options.parms=[];
 	 	console.log(obj)
 	 	
    	gridObj.options.parms.push({name:'rep_team_code',value:parm});
    	gridObj.loadData(gridObj.where);
    }
    function addUserOpen(){
    	if(!parm){
    		$.ligerDialog.error('请选择班组后维护班组人员');
    		return;
    	}
    	$.ligerDialog.open({ 
    		url : 'assRepairUserAddPage.do?isCheck=false&rep_team_code='+parm,
			data:{}, 
			height: 500,
			width: 700, 
			title:'添加人员',
			odal:true,
			showToggle:false,
			showMax:false ,
			showMin: false,
			isResize:true,
			buttons: [ { text: '确定', onclick: function (item, dialog) { dialog.frame.save(); },cls:'l-dialog-btn-highlight' },
			           { text: '取消', onclick: function (item, dialog) { dialog.close(); } } 
			          ]
    	}); 
    }
    function openUserUpdate(obj){
    	$.ligerDialog.open({ 
    		url : 'assRepairUserUpdatePage.do?isCheck=false&user_id='+obj,
			data:{}, 
			height: 400,
			width: 400, 
			title:'添加人员',
			odal:true,
			showToggle:false,
			showMax:false ,
			showMin: false,
			isResize:true,
			buttons: [ { text: '确定', onclick: function (item, dialog) { dialog.frame.save(); },cls:'l-dialog-btn-highlight' },
			           { text: '取消', onclick: function (item, dialog) { dialog.close(); } } 
			          ]
    	}); 
    }
    
    function removeUser(){

    	var data = gridObj.getCheckedRows();
    	console.log(data);
                    if (data.length == 0){
                    	$.ligerDialog.error('请选择行');
                    }else{
                        var ParamVo =[];
                        $(data).each(function (){					
							ParamVo.push(
							this.user_id  
							) });
                        $.ligerDialog.confirm('确定删除?', function (yes){
                        	if(yes){
                            	ajaxJsonObjectByUrl("deleteAssRepairUser.do?isCheck=false",{ParamVo : ParamVo.toString()},function (responseData){
                            		if(responseData.state=="true"){
                            			queryRepUser();
                            		}
                            	});
                        	}
                        }); 
                    }
    }
    function add_open(){
    	
		$.ligerDialog.open({ url : 'assRepairTeamDictAddPage.do?isCheck=false&',data:{}, height: 400,width: 600, title:'新增班组',modal:true,showToggle:false,showMax:false ,showMin: false,isResize:true,
			buttons: [ { text: '确定', onclick: function (item, dialog) { dialog.frame.saveAssRepairTeamDict(); },cls:'l-dialog-btn-highlight' },
			           { text: '取消', onclick: function (item, dialog) { dialog.close(); } } 
			          ]
    	}); 
		
    	/*var index = layer.open({
					type : 2,
					title : 'tabledesc',
					shadeClose : false,
					shade : false,
					maxmin : true, //开启最大化最小化按钮
					area : [ '893px', '500px' ],
					content : 'assRepairTeamDictAddPage.do?isCheck=false'
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
							this.rep_team_code 
							) });
                        $.ligerDialog.confirm('确定删除?', function (yes){
                        	if(yes){
                            	ajaxJsonObjectByUrl("deleteAssRepairTeamDict.do",{ParamVo : ParamVo.toString()},function (responseData){
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
					content : 'assRepairTeamDictImportPage.do?isCheck=false'
				});
				layer.full(index);
    	}	
    function downTemplate(){
    	
    	location.href = "downTemplate.do?isCheck=false";
    	}	
   
    function openUpdate(obj){
    		
		var vo = obj.split("|");
		if("null"==vo[3]){
			return false;
			
		}
		var parm = 
			"group_id="+vo[0]   +"&"+ 
			"hos_id="+vo[1]   +"&"+ 
			"rep_team_code="+vo[2] 
		 
		 
		 $.ligerDialog.open({ url : 'assRepairTeamDictUpdatePage.do?isCheck=false&parm='+parm,height: 400,width: 600, title:'班组维护',modal:true,showToggle:false,showMax:false,showMin: false,isResize:true,
			buttons: [ { text: '确定', onclick: function (item, dialog) { dialog.frame.saveAssRepairTeamDict(); },cls:'l-dialog-btn-highlight' },
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
					content : 'assRepairTeamDictUpdatePage.do?isCheck=false&' + parm
				});
				layer.full(index);	
    */
    }
    function loadDict(){
            //字典下拉框
    	autocomplete("#is_stop", "../../queryAssYesAndNo.do?isCheck=false", "id","text", true, true,null,true);

         }  
    //键盘事件
	  function loadHotkeys() {

		hotkeys('Q', query);

		hotkeys('A', add);
		hotkeys('D', remove);

		

	 }
 	  
    </script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<div id="toptoolbar" ></div>
    <table cellpadding="0" cellspacing="0" class="l-table-edit" >
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">班组编码：</td>
            <td align="left" class="l-table-edit-td"><input name="rep_team_code" type="text" id="rep_team_code" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">班组名称：</td>
            <td align="left" class="l-table-edit-td"><input name="rep_team_name" type="text" id="rep_team_name" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">是否停用：</td>
            <td align="left" class="l-table-edit-td"><input name="is_stop" type="text" id="is_stop" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
        </tr> 
    </table>
	<div class="container">
		<div id="maingrid" style="float:left;"></div>
		<div id="usergrid" style="float: left;"></div>		
	</div>	
</body>
</html>
