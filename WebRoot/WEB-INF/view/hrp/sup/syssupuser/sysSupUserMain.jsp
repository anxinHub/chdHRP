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
        $("#user_code").ligerTextBox({width:160});
        $("#user_name").ligerTextBox({width:160});
        $("#sup_id").ligerTextBox({width:160});
		
    });
    //查询
    function  query(){
    		grid.options.parms=[];
    		grid.options.newPage=1;
        //根据表字段进行添加查询条件
    	  grid.options.parms.push({name:'user_code',value:$("#user_code").val()}); 
    	  grid.options.parms.push({name:'user_name',value:$("#user_name").val()}); 
    	  grid.options.parms.push({name:'copy_code',value:liger.get("copy_code").getValue()}); 
    	  grid.options.parms.push({name:'sup_id',value:liger.get("sup_id").getValue().split(".")[0]}); 

    	//加载查询条件
    	grid.loadData(grid.where);
		$("#resultPrint > table > tbody").empty();
     }
    //获取查询条件的数值
    function f_getWhere(){
    	if (!grid) return null;
        var clause = function (rowdata, rowindex){
                	if($("#user_code").val()!=""){
                		return rowdata.user_code.indexOf($("#user_code").val()) > -1;	
                	}
                	if($("#user_name").val()!=""){
                		return rowdata.user_name.indexOf($("#user_name").val()) > -1;	
                	}
                	if($("#mod_code").val()!=""){
                		return rowdata.mod_code.indexOf($("#mod_code").val()) > -1;	
                	}
                	if($("#sup_id").val()!=""){
                		return rowdata.sup_id.indexOf($("#sup_id").val()) > -1;	
                	}
        };
        return clause; 
    }

    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
           columns: [ 
                     { display: '用户编码', name: 'user_code', align: 'left'},
                     { display: '用户名称', name: 'user_name', align: 'left'},
                     { display: '系统模块', name: 'mod_code', align: 'left',
						render: function (rowdata, rowindex, value) {
							return "供应商平台";
						}
					 },
                     { display: '所属供应商', name: 'sup_name', align: 'left'},
                     { display: '状态', name: 'is_disable', align: 'left',reg:"1=停用,else=启用", 
         				render : function(rowdata, rowindex, value) {
         					return rowdata.is_disable == 1 ? "启用" : "停用";
         				}
                     },
                     { display: '是否是管理员', name: 'is_manager', align: 'left',reg:"1=是,else=否", 
          				render : function(rowdata, rowindex, value) {
          					return rowdata.is_manager == 1 ? "是" : "否";
          				}
                      }
                     ],
                     dataAction: 'server',dataType: 'server',usePager:true,url:'querySysSupUser.do',
                     width: '100%', height: '100%', checkbox: true,rownumbers:true,
                     selectRowButtonOnly:true,//heightDiff: -10,
                     toolbar: { items: [
                     	{ text: '查询（<u>E</u>）', id:'search', click: query,icon:'search' },
                     	{ line:true },
				    					{ text: '添加（<u>A</u>）', id:'add', click: add_open, icon:'add' },
				    	                { line:true },
				    	                { text: '删除（<u>D</u>）', id:'delete', click: remove,icon:'delete' },
											{ line:true },
				    	                { text: '根据供应商生成（<u>P</u>）', id:'generate', click: generate,icon:'add' },
											{ line:true },
				    	                { text: '重置密码（<u>P</u>）', id:'reset', click: reset,icon:'back' },
											{ line:true }
				    				]},
    				onDblClickRow : function (rowdata, rowindex, value)
    				{
						openUpdate(
								rowdata.group_id   + "|" + 
								rowdata.hos_id   + "|" + 
								rowdata.user_code + "|" + 
								rowdata.sup_id
							);
    				} 
                   });

        gridManager = $("#maingrid").ligerGetGridManager();
    }
    
    function add_open(){
    	$.ligerDialog.open({ url : 'sysSupUserAddPage.do?isCheck=false&',data:{}, height: 300,width: 450, title:'添加',modal:true,showToggle:false,showMax:false,showMin: false,isResize:true,
			buttons: [ { text: '确定', onclick: function (item, dialog) { dialog.frame.saveSysSupUser(); },cls:'l-dialog-btn-highlight' },
			           { text: '取消', onclick: function (item, dialog) { dialog.close(); } } 
			          ]
    	}); 
    }
    //更加供应商生成用户
    function generate(){
    	if (!liger.get("copy_code").getValue()) {
			$.ligerDialog.warn("账套不能为空");
			return false;
		}else{
			 var formPara={
  		           copy_code: liger.get("copy_code").getValue()
  		         };
			ajaxJsonObjectByUrl("generate.do",formPara,function (responseData){
	    		if(responseData.state=="true"){
	    			query();
	    		}
	    	});
		}
    	
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
							this.user_code 
							) });
                        $.ligerDialog.confirm('确定删除?', function (yes){
                        	if(yes){
                            	ajaxJsonObjectByUrl("deleteSysSupUser.do",{ParamVo : ParamVo.toString()},function (responseData){
                            		if(responseData.state=="true"){
                            			query();
                            		}
                            	});
                        	}
                        }); 
                    }
    	}
    function reset(){
    	
    	var data = gridManager.getCheckedRows();
                    if (data.length == 0){
                    	$.ligerDialog.error('请选择行');
                    }else{
                        var ParamVo =[];
                        $(data).each(function (){					
							ParamVo.push(
							this.group_id   +"@"+ 
							this.hos_id   +"@"+ 
							this.user_code 
							) });
                        $.ligerDialog.confirm('确定重置密码?', function (yes){
                        	if(yes){
                            	ajaxJsonObjectByUrl("updateSysSupUserPwd.do",{ParamVo : ParamVo.toString()},function (responseData){
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
		var parm = 
			"group_id="+vo[0]   +"&"+ 
			"hos_id="+vo[1]   +"&"+ 
			"user_code="+vo[2] +"&"+"sup_id="+vo[3];
		$.ligerDialog.open({ url : 'sysSupUserUpdatePage.do?isCheck=false&' + parm,data:{}, height: 300,width: 450, title:'修改',modal:true,showToggle:false,showMax:false,showMin: false,isResize:true,
			buttons: [ { text: '确定', onclick: function (item, dialog) { dialog.frame.saveSysSupUser(); },cls:'l-dialog-btn-highlight' },
			           { text: '取消', onclick: function (item, dialog) { dialog.close(); } } 
			          ]
		});
    
    }
    function loadDict(){
            //字典下拉框
    	autocomplete("#sup_id","../../sys/querySupDictDict.do?isCheck=false","id","text",true,true);
    	autocomplete("#copy_code","../../sys/queryCopyCodeDict.do?isCheck=false","id","text",true,true,'',true);  
    	 
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
        	<td align="right" class="l-table-edit-td"  style="padding-left:20px;">账套：</td>
            <td align="left" class="l-table-edit-td"><input name="copy_code" type="text" id="copy_code" ltype="text"  validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">用户编码：</td>
            <td align="left" class="l-table-edit-td"><input name="user_code" type="text" id="user_code" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">用户名称：</td>
            <td align="left" class="l-table-edit-td"><input name="user_name" type="text" id="user_name" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">所属供应商：</td>
            <td align="left" class="l-table-edit-td"><input name="sup_id" type="text" id="sup_id" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            
        </tr> 
    </table>

	<div id="maingrid"></div>
	
</body>
</html>
