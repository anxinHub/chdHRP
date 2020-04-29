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
<!-- 综合查询-自定义报表 -->
<script type="text/javascript">

    var grid;
    var gridManager = null;
    
    var rhead_id = '${rhead_id}';
    var items = ${items};
    
    $(function (){
        loadDict();//加载下拉框
        loadForm();
    	loadHead(null);	//加载数据
    	loadHotkeys();
    });
    
    //查询
    function query(){
    	
   		grid.options.parms=[];
   		grid.options.newPage=1;
   		
        //根据表字段进行添加查询条件
    	var t = $('#form1').serializeArray();
        $.each(t, function() {
          grid.options.parms.push({name:this.name,value:this.value});
        });
    	
    	//加载查询条件
    	grid.loadData(grid.where);
    	loadHead();	
		$("#resultPrint > table > tbody").empty();
		
	}
    
    function loadForm(){
    	var fields = [];
    	$.each(items.Rows, function(i, v) {
    		//console.log(v)
    		if(v.s_flag == 1){
    			var field = { display: v.field_text, name: v.field_name, type: v.field_type,newline:false};
    			fields.push(field);
    		}
    	});
    	$("#form1").ligerForm({
            inputWidth: 170, labelWidth: 90, space: 40,
            fields: fields
        }); 
    }
    
    function loadHead(){
    	var cols = [];
    	$.each(items.Rows, function(i, v) {
    		//console.log(v)
    		if(v.is_show == 1){
    			var col = { display: v.field_text, name: v.field_name, align: 'left',width: '120'};
    			cols.push(col);
    		}
    	});
    	//console.log(cols)
    	grid = $("#maingrid").ligerGrid({
          columns: cols,
          dataAction: 'server',dataType: 'server',usePager:true,url:'queryAssDefinedQueryRun.do?isCheck=false&rhead_id='+rhead_id,
          width: '100%', height: '100%', checkbox: true,rownumbers:true,delayLoad :true,
          selectRowButtonOnly:true,//heightDiff: -10,
          //tree:{columnId:'ass_type_code'},
          toolbar: { 
         	 items: [
                 { text: '查询', id:'query', click: query,icon:'search' }
              ]},
        });

        gridManager = $("#maingrid").ligerGetGridManager();
    }
    
    function run(id){
    	console.log(id)
    }
    
    function itemclick(item) {
		if (item.id) {
			switch (item.id) {
			case "add":
				$.ligerDialog.open({
					url : 'assDefinedQueryAddPage.do?isCheck=false',
					height: $(window).height() / 1.4,
					width: $(window).width()  / 1.4,
					title : '添加',
					modal : true,
					showToggle : false,
					showMax : true,
					showMin : true,
					isResize : true,
					buttons : [ {
						text : '确定',
						onclick : function(item, dialog) {
							dialog.frame.saveAssDefinedQuery();
						},
						cls : 'l-dialog-btn-highlight'
					}, {
						text : '关闭',
						onclick : function(item, dialog) {
							dialog.close();
						}
					} ]
				});
				return;
			case "modify":
				return;
			case "delete":
				var data = gridManager.getCheckedRows();
				if (data.length == 0) {
					$.ligerDialog.error('请选择行');
				} else {
					var ParamVo = [];
					$(data).each(
							function() {
								ParamVo.push(this.group_id + "@" + this.hos_id
										+ "@" + this.copy_code + "@"
										+ this.prop_code)
							});
					$.ligerDialog.confirm('确定删除?', function(yes) {
						if (yes) {
							ajaxJsonObjectByUrl("deleteAssPropDict.do", {
								ParamVo : ParamVo.toString()
							}, function(responseData) {
								if (responseData.state == "true") {
									query();
								}
							});
						}
					});
				}
				return;
			}
		}

	}
    
    //字典下拉框
    function loadDict(){
    	$("#name,#code").ligerTextBox({width:180});
    } 
    //键盘事件
	function loadHotkeys() {
	}
    
	function printDate(){
   		
    }
	  
</script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<div id="toptoolbar" ></div>
    <form id="form1"></form> 

	<div id="maingrid"></div>
</body>
</html>
