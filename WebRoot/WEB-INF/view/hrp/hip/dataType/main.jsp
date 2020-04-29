<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html style="overflow:hidden;">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="${path}/resource.jsp">
	<jsp:param value="dialog" name="plugins" />
</jsp:include>
<jsp:include page="${path}/inc_jquery_1.9.0.jsp" />
<script type="text/javascript">
    var grid;
    var gridManager = null;
    var userUpdateStr;
    var data_type_info = {
    		Rows : [ {
 				"id" : "0",
 				"text" : "无" 
 			},{
 				"id" : "1",
 				"text" : "按日期取数" 
 			}],
 			Total : 2
    };
    var sync_type_info = {
    		Rows : [ {
 				"id" : "0",
 				"text" : "删除同步" 
 			}, {
 				"id" : "1",
 				"text" : "增量同步" 
 			}],
 			Total : 2
    };
    $(function ()
    {
         loadDict()//加载下拉框
    	//加载数据
    	 loadHead(null);	
         loadHotkeys();
         query();
    });
    //查询
    function  query(){
		grid.options.parms=[];
		grid.options.newPage=1;
		
		grid.options.parms.push({name:'type_code',value:$("#type_code").val()});
		grid.options.parms.push({name:'mod_code',value:liger.get("mod_code").getValue()}); 
		grid.options.parms.push({name:'data_type',value:liger.get("data_type").getValue()}); 
		grid.options.parms.push({name:'sync_type',value:liger.get("sync_type").getValue()}); 
    	//加载查询条件
    	grid.loadData(grid.where);
     }
	
    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
			columns: [{
					    display: '业务编码', name: 'type_code', align: 'left',isSort:false,
                   	 render : function(rowdata, rowindex,
								value) {
								return "<a href=javascript:open_update('" + rowdata.group_id   + "|" + rowdata.hos_id  + "|"  + rowdata.type_id +"')>"+rowdata.type_code+"</a>";
                  	 }
				      },{
					    display: '业务名称', name: 'type_name', align: 'left',isSort:false 
				      },{
					    display: '数据源', name: 'source_name', align: 'left',isSort:false 
				      },{
					    display: '模块', name: 'mod_name', align: 'left',isSort:false 
				      },{
					    display: '取值方式', name: 'data_type_name', align: 'left',isSort:false 
				      },{
					    display: '同步方式', name: 'sync_type_name', align: 'left',isSort:false 
				      },{
					    display: '状态', name: 'is_stop', align: 'left',isSort:false,
					    render: function(rowData, rowIndex, value){
					    	if(value=="停用"){
					    		return "<span style='color: red;'>"+value+"</span>";
					    	}else{
					    		return value;
					    	}
							
						}
				      },{
						display: '操作', name: 'dict_decode', align: 'center',isSort:false, 
						render: function(rowData, rowIndex, value){
							return "<a href=javascript:open_decodeSet('"+rowData.type_id+"|"+rowData.to_table+"')>字段转换</a>&nbsp;&nbsp;<a href=javascript:open_log('"+rowData.type_id+"')>日志</a>";
						}
				      }],
			dataAction: 'server',dataType: 'server',usePager:true,url:'queryHipDataType.do',
			width: '100%', height: '100%', checkbox: true,rownumbers:true,
			delayLoad: true,//初始化加载，默认false
			selectRowButtonOnly:true,//heightDiff: -10,
			toolbar: { items: [
			   				{ text: '查询（<u>Q</u>）', id:'search', click: query, icon:'search' },
			   				{ line:true }, 
			   				{ text: '添加（<u>A</u>）',id : 'add',click : add_open,icon : 'add' },
			   				{ line:true },
			   				{ text: '删除（<u>D</u>）',id : 'delete',click : remove,icon : 'delete'},	
			   				{ line:true },	 
			   				{ text: '同步数据（<u>S</u>）', id:'loadData', click: loadData, icon:'settings' },
			   				{ line:true },	 
			   				{ text: '日志（<u>C</u>）', id:'viewLog', click: viewLog, icon:'initwage' }
			   			]},
	    	onDblClickRow : function (rowdata, rowindex, value)
	    				{
	    		open_update(
									rowdata.group_id   + "|" + 
									rowdata.hos_id   + "|" + 
									rowdata.type_id 
								);
	    				}

		});

        gridManager = $("#maingrid").ligerGetGridManager();
    }
    
    function add_open(){
    	
    	parent.openFullDialog('hrp/hip/dataType/addPage.do?isCheck=false','添加',0,0,true,true);

    }
    
    function open_log(obj){
    	var vo = obj.split("|");
		if("null"==vo[0]){
			return false;
		}
		var parm = "&type_id="+ vo[0]; 
		
		
		parent.$.ligerDialog.open({
			title: '日志',
			height: $(window).height() / 1.2,
			width: $(window).width() /  1.2,
			url: 'hrp/hip/dataType/logPage.do?isCheck=false'+parm,
			modal: true, showToggle: false, showMax: false, showMin: true, isResize: true,slide:false,
			parentframename: window.name,  //用于parent弹出层调用本页面的方法或变量
		});
		   
		//parent.openFullDialog(''+parm,'日志',0,0,true,true);
    }
    
    function open_update(obj){
    	
    	var vo = obj.split("|");
		if("null"==vo[3]){
			return false;
			
		}
		var parm ="&group_id="+ 
		   vo[0]   +"&hos_id="+ 
		   vo[1]   +"&type_id="+ 
		   vo[2]; 
		parent.openFullDialog('hrp/hip/dataType/updatePage.do?isCheck=false'+parm,'修改',0,0,true,true);
    	
    }
    
    
	function remove(){
		var data = gridManager.getCheckedRows();
        if (data.length == 0){
        	$.ligerDialog.error('请选择行');
        }else{
            var ParamVo =[];
            $(data).each(function (){	
				ParamVo.push(
				"null@"+ 
				this.type_id 
				);
			});
           
            $.ligerDialog.confirm('确定删除?', function (yes){
            	if(yes){
                	ajaxJsonObjectByUrl("deleteHipDataType.do?isCheck=false",{ParamVo : ParamVo.toString()},function (responseData){
                		if(responseData.state=="true"){
                			query();
                		}
                	});
            	}
            }); 
        }
    }
	
	var hipDataType;  //子页面使用勿动
	function open_decodeSet(obj){
    	var vo = obj.split("|");
		hipDataType = {type_id: vo[0], to_table: vo[1]};
		parent.$.etDialog.open({
			url: 'hrp/hip/dataType/decodeSetPage.do?isCheck=false',
			frameName : window.name,
			width: $(parent.window).width(),
			height: $(parent.window).height(),
			title: '字典转换设置',
			showMax: true,
			shade: 0,
			maxmin: true,
			restore: function(layero){
			  	//得到iframe页的窗口对象，执行iframe页的方法：iframeWin.method();
				var iframeWin = parent.window[layero.find('iframe')[0]['name']];
			  	setTimeout(function() {
		  			iframeWin.$grid.refreshView();
			  	}, 100);
		  	}
		});
	}
    
    
	//键盘事件
	function loadHotkeys() {
		hotkeys('Q', query);
		hotkeys('A', add_open);
		hotkeys('D', remove);
		hotkeys('S', loadData);
		hotkeys('C', viewLog);
	}
    function loadDict(){
    	
    	$("#type_code").ligerTextBox({
			width : 120
		});
    	
    	//当前用户登录类型
        var user_type = parent.sessionJson.type_code;
        //当前用户登录的模块
        var l_mod_code = '${sessionScope.mod_code}';
    	//if(user_type == '0'){
    	//	autocompleteAsync("#mod_code","../../sys/queryModDict.do?isCheck=false&mod_code="+l_mod_code+"&type_code="+user_type,"id","text",false,true,'',true,'',120);
    	//}else{
    		autocompleteAsync("#mod_code","../../sys/queryModDict.do?isCheck=false&type_code="+user_type,"id","text",false,true,'',true,'',120);
    	//}
    	$("#mod_code").ligerComboBox({ cancelable: false });
		autoCompleteByData("#data_type", data_type_info.Rows, "id", "text", false, true,null,true,null,120);
		
		$("#data_type").ligerComboBox({ cancelable: false });
        
    	autoCompleteByData("#sync_type", sync_type_info.Rows, "id", "text", false, true, true,false,null,120);
    	
    	$("#data_type").change(function(){
    		query();
    	});
    }  
    
   
    function loadData(){
         
		var data = gridManager.getCheckedRows();
		if (data.length == 0) {
			$.ligerDialog.error('请选择行');
		} else {
			var paramVo = [];
			var isType=true;
			$(data).each(function() {
				if(this.data_type!=liger.get("data_type").getValue()){
					isType=false;
					return false;
				}
				if(this.is_stop=="停用"){
					return true;
				}
				paramVo.push(this.type_id)
			});
			
			if(!isType){
				$.ligerDialog.error('选择取值方式后请重新查询');
				return;
			}
			
			if(paramVo.length==0){
				$.ligerDialog.error('请选择行');
				return;
			}
			
			if(liger.get("data_type").getValue()==0){
				$.ligerDialog.confirm('确认同步数据？', function (yes) {
					if (yes){
	            	    var para={
	       					type_id: paramVo.toString()
	       		        }; 
	       		        	
	       				var loadIndex = layer.load(1);
	       				ajaxJsonObjectBylayer("syncData.do?isCheck=false",para,function (responseData){
	       					
	       				},layer,loadIndex);
	               }
				});
			}else{
				//按日期取数
				parent.$.ligerDialog.open({
					title : '同步数据',
					height : 300,
					width : 500,
					url : 'hrp/hip/dataType/runJobPage.do?isCheck=false',
					modal : true,
					showToggle : false,
					showMax : false,
					showMin : false,
					isResize : true,
					data:{typeId: paramVo},
					parentframename : window.name
				});
			}
			
			
		}
    }
    
    function viewLog(){
    	parent.$.ligerDialog.open({
			title: '日志',
			height: $(window).height() / 1.2,
			width: $(window).width() /  1.2,
			url: 'hrp/hip/dataType/logPage.do?isCheck=false&',
			modal: true, showToggle: false, showMax: false, showMin: true, isResize: true,slide:false,
			parentframename: window.name,  //用于parent弹出层调用本页面的方法或变量
		});
    }

	</script>
</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>
	 <table cellpadding="0" cellspacing="0" class="l-table-edit" style="margin-left: 50px">
        <tr>
        	<td align="right" class="l-table-edit-td"  style="padding-left:20px;">系统模块：</td>
            <td align="left" class="l-table-edit-td"><input name="mod_code" type="text" id="mod_code"  /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">取值方式：</td>
            <td align="left" class="l-table-edit-td"><input name="data_type" type="text" id="data_type" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">同步方式：</td>
            <td align="left" class="l-table-edit-td"><input name="sync_type" type="text" id="sync_type" /></td>
            <td align="left"></td>
            <td align="left"  class="l-table-edit-td" style="padding-left: 20px;">业务：</td>
			<td align="left"  class="l-table-edit-td"><input name="type_code"
				type="text" id="type_code" 
				 /></td>
        </tr> 
    </table>
	<div id="maingrid"></div>
</body>
</html>
