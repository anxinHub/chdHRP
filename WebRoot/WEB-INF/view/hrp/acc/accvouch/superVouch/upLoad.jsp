<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head> 
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />

<%-- <script src="<%=path%>/lib/jquery/jquery-1.9.0.min.js" type="text/javascript"></script> --%>
<script src="<%=path%>/lib/json2.js"></script>
<script src="<%=path%>/lib/hrp.js" type="text/javascript"></script>
<jsp:include page="${path}/inc_jquery_1.9.0.jsp"/>

<script type="text/javascript">
var grid;
var gridManager = null;
var userUpdateStr;
var vouch_id = '${vouch_id}';
	$(function() {
		loadDict();
		loadHead();
		 //webuploader
		query();
	});
	
	
    function  query(){
		grid.options.parms=[];
		grid.options.newPage=1;
        //根据表字段进行添加查询条件

    	//加载查询条件
    	grid.loadData(grid.where);
     } 
	
	
	 function loadHead(){
    	grid = $("#maingrid").ligerGrid({
    		columns:[{
				display: '文件名称', name: 'att_name_o', align: 'left',
				render : function(rowdata, rowindex,value) {
					return "<a href=javascript:down('"+ rowdata.att_path + "')>"
							+ rowdata.att_name_o+ "</a>";
				}
			}, {
				display: '附件类型', name: 'att_type', align: 'left',width:70,
				render : function(rowdata, rowindex,value) {
					if(rowdata.att_type == 1){
						return '文档';
					}else{
						return '发票';
					}
				}
			}, {
				display: '发票号码', name: 'invo_num', align: 'left'
			}, {
				display: '发票金额', name: 'invo_money', align: 'right',width:150,
				render : function(rowdata, rowindex, value) {
  					return formatNumber(rowdata.invo_money, 2, 1);
  				}

			}, {
				display: '发票日期', name: 'invo_date', align: 'left',width:80
			}, {
				display: '上传人', name: 'create_name', align: 'left',width:80
			}, {
				display: '上传时间', name: 'create_date', align: 'left',width:150
			},{
				display: '文件大小(KB)', name: 'att_size', align: 'left',width:90
			}, {
				display: '名称', name: 'att_name_n', align: 'left',width:1,hide:true
			}
   		     ],
	        dataAction: 'server',dataType: 'server',usePager:false,url:'queryAccFile.do?isCheck=false&vouch_id='+vouch_id,
			width: '99%', height: '100%', checkbox: true,rownumbers:true,
			delayLoad: true,
			selectRowButtonOnly:true,//heightDiff: -10,
			toolbar: { items: [
				/* { text: '查询(<u>Q</u>)', id:'search', click: query,icon:'search' },
				{ line:true }, */
				{ text: '添加(<u>A</u>)', id:'add', click: add_open, icon:'add' },
				{ line:true },
				{ text: '删除(<u>D</u>)', id:'delete', click: remove, icon:'delete' },
				{ line:true },
				{ text: '关闭(<u>L</u>)', id:'candle', click: this_close, icon:'candle' }
			]}
    	});

        gridManager = $("#maingrid").ligerGetGridManager();
	 }
	function add_open(){
		
		$.ligerDialog
				.open({
					title : '附件添加',
					height : 350,
					width : 400,
					url : 'accFileAddPage.do?isCheck=false&vouch_id='+vouch_id,
					showToggle : false,
					showMax : false,
					showMin : false,
					isResize : false,
					parentframename : window.name,
					top : 90,
					buttons : [ {
						text : '确定',onclick : function(item, dialog) {
							dialog.frame.saveAccFile();
						},cls : 'l-dialog-btn-highlight'
					}, {
						text : '关闭',onclick : function(item, dialog) {
							dialog.close();
						}
					} ]
				});
	}
	function remove(){
		var data = gridManager.getCheckedRows();
		if (data.length == 0) {
			parent.$.ligerDialog.error('请选择行');
		} else {
			var ParamVo = [];
			$(data).each(
					function() {
						ParamVo.push(this.group_id + "@" + this.hos_id + "@"
								+ this.copy_code + "@" + this.vouch_id + "@"
								+ this.att_name_n + "@" + this.att_path)
					});
			parent.$.ligerDialog.confirm('确定删除?', function(yes) {
				if (yes) {
					ajaxJsonObjectByUrl("deleteAccFile.do?isCheck=false", {
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
	
	function down(att_path) {
		location.href = "downAccFile.do?isCheck=false&att_path="+att_path;
	}
	
	function loadDict(){
		
	}
	
	//关闭
	function this_close(){
		frameElement.dialog.close();
	}
	

</script>
</head>

<body style="padding: 0px;"  >
 
 	 <div id="maingrid"></div> 
	
</body>
</html>
