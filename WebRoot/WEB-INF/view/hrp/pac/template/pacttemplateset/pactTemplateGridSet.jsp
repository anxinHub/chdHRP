<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html style="overflow:hidden;">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title></title>
<jsp:include page="${path}/inc_jquery_1.9.0.jsp"/>
<script type="text/javascript">
var grid,itemgrid;
var gridManager = null;
var itemGridManager = null;
var pt_rowid = "${pt_rowid}";
var ptm_rowid = "${ptm_rowid}";
var tabStr = "${tabStr}";// 渲染页签用
var buttonStr = "${buttonStr}";// 渲染按钮用
var et_rowid ="${et_rowid}" ;
var gt_istotalrow ="${gt_istotalrow}";
   $(function () {
    	loadDict();
    	loadHead();	//加载数据
    	loadItemHead() ;
    	query();
    	queryItem();
    	renderPage();
    });
    
   function query(){
		grid.options.parms = [];
		grid.options.newPage = 1;
		//根据表字段进行添加查询条件
		grid.options.parms.push({ name: 'et_rowid', value: et_rowid });// 此条件  不允许变动
		//加载查询条件
		grid.loadData(grid.where);
   }
   function queryItem(){
	   itemgrid.options.parms = [];
	   itemgrid.options.newPage = 1;
		//根据表字段进行添加查询条件
		itemgrid.options.parms.push({ name: 'et_rowid', value: et_rowid });// 此条件  不允许变动
		//加载查询条件
		itemgrid.loadData(grid.where);
  }

    // 加载grid
    function loadHead(){
		grid = $("#maingrid").ligerGrid({
			columns: [ 
				{ display: '配置项', name: "mould_config_name", align: 'left',width:'100%'},
			],
			dataAction: 'server',dataType: 'server',usePager : true, checkbox: false,rownumbers:true,enabledEdit: true,
			width: '100%', height: '100%', isAddRow:false ,
			url:'queryPactTemplateItem.do?isCheck=false&flag=1',
			selectRowButtonOnly:true,//heightDiff: -10,
			onDblClickRow:function (data, rowindex, rowobj) {
				addItemRow(data);
			}
		});

        gridManager = $("#maingrid").ligerGetGridManager();
        
    }
 	// 加载grid
    function loadItemHead(){
		itemgrid = $("#itemgrid").ligerGrid({
			columns: [ 
				{ display: '模板项', name: "be_name", align: 'left',width:'60%'},
				{ display: '标题行号', name: "be_tittlerow", align: 'center',width:'20%',editor:{type:'float'}},
				{ display: '列号', name: "be_columnno", align: 'center',width:'20%',editor:{type:'float'}},
			],
			dataAction: 'server',dataType: 'server',usePager : true, checkbox: true,rownumbers:true,enabledEdit: true,
			width: '100%', height: '100%', isAddRow:false ,
			url:'queryPactTemplateItemSet.do?isCheck=false&flag=1',
			onSelectRow:setInfo,
			onAfterShowData: renderQuery,
			selectRowButtonOnly:true,//heightDiff: -10,
			onDblClickRow:function (data, rowindex, rowobj) {
				deleteItemRow(data);
			}
		});

        itemGridManager = $("#itemgrid").ligerGetGridManager();
        
    }
  //双击配置项列表  配置项设置列表增加行
    function addItemRow(data){
    	itemgrid.addRow({"be_name":data.mould_config_name,
    		"be_code":data.mould_config_code,
    		"be_itemid":data.mould_config_id,
    		"group_id" : data.group_id ,
    		"hos_id" : data.hos_id ,
    		"copy_code": data.copy_code });
    	grid.deleteRow(data);
    	
    }
    //双击配置项设置列表  配置项设置列表删除行
    function deleteItemRow(data){
    	$.ligerDialog.confirm('确定删除编制项?', function(yes) {
    		if(yes){
    			itemgrid.deleteRow(data);
    	    	grid.addRow({
    	    		"group_id" : data.group_id ,
    	    		"hos_id" : data.hos_id ,
    	    		"copy_code": data.copy_code,
    	    		"mould_config_id" : data.be_itemid ,     
    				"mould_config_code":data.be_code , 
    				"mould_config_name":data.be_name 
    	    	});
    	    	deleteQuery(data.row_id);
    		}
    	})
    	
    }
    // 保存
    function save(){
    	var data = itemgrid.getData();
    	if(data.length == 0){
    		$.ligerDialog.warn('请配置编制项数据!');
    	}else{
    		  var gt_tab="";
    		  $('input:radio[name="addTab"]').each(function () {
      			 if(this.checked){
      				 gt_tab=this.value;
      			 }
              });
    		  if(!gt_tab){
    			  $.ligerDialog.warn('请选择标签页!');
    			  return false ;
    		  }
   	          if (!validateGrid(data)) {
   	              return false;
   	          }
   	          //按钮数据 
	   	      var table = document.getElementById("newButton");
	   	 	  var buttonInfo = table.getElementsByTagName('input');
	   	 	  var buttonData = "";
	   	 	  $(buttonInfo).each(function (){
	   	 		 buttonData += this.value + ","
	   	 	  })
	       	  var data = {
	    				pt_rowid : pt_rowid ,
	    				ptm_rowid : ptm_rowid ,
	    				et_rowid : (et_rowid?et_rowid:"-1"),
	    				gt_tab : gt_tab ,
		   				gt_istotalrow : ($("#gt_istotalrow").ligerCheckBox().getValue()?1:0),
		   				buttonData : buttonData.substring(0,buttonData.length-1),
		   				detail : JSON.stringify(data),
		   				ptm_showmode : 2 // 1-表格  2-表单
	    			};
	       	 ajaxJsonObjectByUrl('savePactTemplateGridItemSet.do?isCheck=false',data ,
	       			function(responseData) {
	       		 		if (responseData.state == "true") {
	       		 			location.reload() ; //刷新页面
	       		 		}
	       		 	}
    		)
    	}
    }

       
    function validateGrid(data) { 
    	var msg="";
 		var rowm = "";
 		//判断grid 中的数据是否为空
 		var targetMap = new HashMap();
 		
 		$.each(data,function(i, v){
 			rowm = "";
			if (!v.be_tittlerow) {
				rowm+="[行号]、";
			}
			if (!v.be_columnno) {
				rowm+="[列号]、";
			}
			if(rowm != ""){
				rowm = "编制项:"+v.be_name+"-" + rowm.substring(0, rowm.length-1) + "不能为空" + "\n\r";
			}
			msg += rowm;
 		});
 		if(msg != ""){
 			$.ligerDialog.warn(msg);  
			return false;  
 		}else{
 			return true;  
 		} 	
 	}
    
    // 模拟页签切换事件    选择不同页签时 重新渲染页面
    function tabChange(id){
    	if(id.indexOf("_") > -1 ){
    		et_rowid = id.split("_")[0];
    		ajaxJsonObjectByUrl("queryPactTemplateGridSetInfo.do?isCheck=false&et_rowid="+et_rowid+"&pt_rowid="+pt_rowid+"&ptm_rowid="+ptm_rowid,
    				"",function(responseData){
		    			if (responseData.state == "true") {
		    				buttonStr = responseData.buttonStr
		    				gt_istotalrow = responseData.gt_istotalrow
		    				if(gt_istotalrow==1){
		    					$("#gt_istotalrow").ligerCheckBox().setValue(true);
		    				}else{
		    					$("#gt_istotalrow").ligerCheckBox().setValue(false);
		    				}
		    			}
   			})
    	}else{// 新增页签  置空 数据
    		buttonStr = "";// 渲染按钮用
    		et_rowid ="" ;
    		gt_istotalrow = 0 ;
    		
    		$("#gt_istotalrow").ligerCheckBox().setValue(false);
    	}
    	renderButton(buttonStr);
    	query();//刷新表格数据
    	queryItem();//刷新表格数据
    	renderQuery(null);
    	// 清空右侧 表单数据
    	$("#be_isquery").ligerCheckBox().setValue(false);
		$("#be_isestablish").ligerCheckBox().setValue(false);
		$("#be_ishide").ligerCheckBox().setValue(false);
		$("#be_alias").val("");
		liger.get("be_establishattribute").setValue("");
		liger.get("cs_rowid").setValue("");
		liger.get("be_enter").setValue("");
		liger.get("be_background").setValue("");
		liger.get("be_fontcolor").setValue("");
		$("#be_startcolumn").val("");
		$("#be_occupycolumn").val("");
		liger.get("be_align").setValue("");
    }
    
    
   	// 渲染页面
    function renderPage(){
    	if(gt_istotalrow == 1){
    		$("#gt_istotalrow").ligerCheckBox().setValue(true);
    	}else{
    		$("#gt_istotalrow").ligerCheckBox().setValue(false);
    	}
    	 if(tabStr){
    		 renderTab(tabStr); // 渲染页签
    	 }
    	 if(buttonStr){
    		 renderButton(buttonStr); // 渲染按钮
    	 }
    	 $('input:radio[name="addTab"]').each(function () {
 			if("${et_rowid}" == this.id.split("_")[0]){
 				this.checked = true ;
 			}
         });
    	 grid.reload();
    }
    //渲染页签
    function renderTab(tabStr){
    	if(tabStr){
    		var data = tabStr.split(",");
       		$(data).each(function(){
          			$("#newTab").append("<input type=\"radio\" name=\"addTab\" id=\""+this+"\" value=\""+
              				this.split("_")[1]+"\" onChange=\"tabChange('"+this+"')\" /><b id=\""+this+"_1\" ondblclick=\"deleteTab('"+this+"')\">"+this.split("_")[1]+"</b>");
          		
           	})
    	}
    }
    // 渲染按钮
    function renderButton(buttonStr){
    	$("#newButton").html("");
    	if(buttonStr){
    		var data = buttonStr.split(",");
       		$(data).each(function(){
           		$("#newButton").append("<input type=\"button\" id=\""+this.split("@")[0]+"\"value=\""+this.split("@")[1]+"\" ondblclick=\"deleteButton('"+this.split("@")[0]+"')\"  />");
       	    	$('#'+this.split("@")[0]).ligerButton({ width: 80 });
           	})
    	}
    	
    }
   function queryChange(){
    	var data = itemgrid.getCheckedRows();
    	if(data.length != 1){
    		$.ligerDialog.warn('请选择一行数据!');
    		return false;
    	}else{
    		if($("#be_isquery").ligerCheckBox().getValue()==1){
    			$(data).each(function(){
    				$("#query").append("<input type=\"button\" id=\""+this.row_id+"\"value=\""+
          					this.be_name+"\" ondblclick=\"deleteQuery('"+this.row_id+"')\"  />");
      	    		$('#'+this.row_id).ligerButton({ width:80});
    			})
        	}else{
        		$(data).each(function(){
        			deleteQuery(this.row_id);
    			})
        	}
    	}
    	
    }
    // 渲染 查询条件
    function renderQuery(rowdata){
    	$("#query").html("");
    	if(rowdata){
    		var data =  rowdata.Rows ;
       	 	//查询条件数据 
    	    var table = document.getElementById("query");
    	 	var queryInfo = table.getElementsByTagName('input');
    	 	var queryData = "";
    	 	$(queryInfo).each(function (){
    	 		queryData += this.id + ","
    	 	})
        	if(data.length > 0 ){
    			$(data).each(function(){
    				if(this.be_isquery == 1 && queryData.indexOf(this.row_id)==-1)
              		$("#query").append("<input type=\"button\" id=\""+this.row_id+"\"value=\""+
              					this.be_name+"\" ondblclick=\"deleteQuery('"+this.row_id+"')\"  />");
          	    	$('#'+this.row_id).ligerButton({ width:80});
              	})
        	}
    	}
    	
    }
    // 删除查询条件
    function deleteQuery(id){
    	var data = itemgrid.getData();
    	$(data).each(function(){
    		if(this.row_id == id){
    			this.be_isquery = 0;
    			itemgrid.updateRow(this,{be_isquery:0});
			}
    	})
    	$("#"+id).remove();
    	
    }
   	//添加页签
    function addTab(){
        $.ligerDialog.prompt('标签名称', '自定义', function (yes, value){
        	if(yes){
        		$("#newTab").append("<input type=\"radio\" name=\"addTab\" id=\""+value+"\" value=\""+
        				value+"\" onChange=\"tabChange('"+value+"')\" /><b id=\""+value+"_1\" ondblclick=\"deleteTab('"+value+"')\">"+value+"</b>");
        		
        	}
        }); 
    }
   	function deleteTab(id){
   		$.ligerDialog.confirm('确定删除页签吗?', function(yes) {
   			if(yes){
   				if(id.indexOf("_") > -1 ){
   					var et_rowid = id.split("_")[0]
   	    			if(et_rowid){
   	    				ajaxJsonObjectByUrl("deletePactTemplateGridtab.do?isCheck=false?&et_rowid="+et_rowid,"", function(responseData) {
   	    	    			if (responseData.state == "true") {
   	    	    				
   	    	    			}
   	    	    	  	});
   	    			}
   	   			}
   	   			var parent=document.getElementById("newTab");
   	   		 	var son1=document.getElementById(id);
   	   		 	var son2=document.getElementById(id+"_1");
   	   		 	parent.removeChild(son1);
   	   		 	parent.removeChild(son2);
   			}
   			
    	})
   	}
 	// 选中 编制项数据  后  渲染 设置信息
    function setInfo(data, rowindex, rowobj){
	  	
		if(data.be_isquery == 1){
			$("#be_isquery").ligerCheckBox().setValue(true);
		}else{
			$("#be_isquery").ligerCheckBox().setValue(false);
		}
		if(data.be_isestablish == 1){
			$("#be_isestablish").ligerCheckBox().setValue(true);
		}else{
			$("#be_isestablish").ligerCheckBox().setValue(false);
		}
		if(data.be_ishide == 1){
			$("#be_ishide").ligerCheckBox().setValue(true);
		}else{
			$("#be_ishide").ligerCheckBox().setValue(false);
		}
		$("#be_alias").val(data.be_alias);
		liger.get("be_establishattribute").setValue(data.be_establishattribute);
		liger.get("cs_rowid").setValue(data.cs_rowid);
		liger.get("be_enter").setValue(data.be_enter);
		liger.get("be_background").setValue(data.be_background);
		liger.get("be_fontcolor").setValue(data.be_fontcolor);
		$("#be_startcolumn").val(data.be_startcolumn);
		$("#be_occupycolumn").val(data.be_occupycolumn);
		liger.get("be_align").setValue(data.be_align)
    }
 	// 按钮添加
    function addButton(){
    	$.ligerDialog.prompt('按钮名称', '自定义', function (yes, value){
    		if (yes){
    			$("#newButton").append("<input type=\"button\" id=\""+value+"\"value=\""+value+"\" ondblclick=\"deleteButton('"+value+"')\"  />");
    	    	$('#'+value).ligerButton({ width: 80 });
    		}
    	})
    	
    }
    function deleteButton(id){
    	$.ligerDialog.confirm('确定删除按钮?', function(yes) {
    		if(yes){
    			$("#"+id).remove();
    		}
    	})
    }
    function saveSetInfo(){
    	var data = itemgrid.getCheckedRows();
    	if(data.length != 1){
    		$.ligerDialog.warn('请选择一行数据!');
    	}else{
    		$(data).each(function() {
    			this.be_isquery = $("#be_isquery").ligerCheckBox().getValue()?1:0;
    			this.be_isestablish = $("#be_isestablish").ligerCheckBox().getValue()?1:0;
  				this.be_ishide = $("#be_ishide").ligerCheckBox().getValue()?1:0;
			  	this.be_alias = $("#be_alias").val();
			  	this.be_establishattribute = liger.get("be_establishattribute").getValue();
			  	//alert(liger.get("be_establishattribute").getValue());
			  	this.cs_rowid = liger.get("cs_rowid").getValue();
			  	this.be_enter = liger.get("be_enter").getValue();
			  	this.be_background = liger.get("be_background").getValue();
			  	this.be_fontcolor = liger.get("be_fontcolor").getValue();
			  	this.be_startcolumn = $("#be_startcolumn").val();
			  	this.be_occupycolumn = $("#be_occupycolumn").val();
			  	this.be_align = liger.get("be_align").getValue();
			  	this.be_function=liger.get("cs_rowid").getValue();
    		})
    	}
    }
    function loadDict(){
    	
    	//布局
    	$("#layout1").ligerLayout({leftWidth: 200,allowLeftCollapse:true}); 
    	$("#layout2").ligerLayout({leftWidth: 400,allowLeftCollapse:false,topHeight:110});
    	autoCompleteByData("#be_align",[{id: 'left', text: 'left'}, {id: 'center', text: 'center'},{id: 'right', text: 'right'}],
    			"id","text",true,true,'',false,'',100);
    	autoCompleteByData("#be_establishattribute",
    			[{id: 'selectdata', text: '下拉框'}, {id: 'select', text: '下拉数据集'},
    			 {id: 'text', text: '文本框'},{id: 'number', text: '数字框'},
    			 {id: 'date', text: '日期框'},{id: 'datetime', text: '日期时间框'},
    			 {id: 'password', text: '密码框'},{id: 'checkbox', text: '复选框'},
    			],
    			"id","text",true,true,'',false,'',100);
    	autoCompleteByData("#be_background",
    			[{id: 0, text: '无'}, {id: 'red', text: '赤'},
    			 {id: 'orange', text: '橙'},{id:'yellow', text: '黄'},
    			 {id: 'green', text: '绿'},{id: 'green', text: '青'},
    			 {id: 'blue', text: '蓝'},{id: 'purple', text: '紫'},
    			 {id: 'black', text: '黑'}
    			],
    			"id","text",true,true,'',false,'',100);
    	autoCompleteByData("#be_fontcolor",
    			[{id: 0, text: '无'}, {id: 'red', text: '赤'},
    			 {id: 'orange', text: '橙'},{id:'yellow', text: '黄'},
    			 {id: 'green', text: '绿'},{id: 'green', text: '青'},
    			 {id: 'blue', text: '蓝'},{id: 'purple', text: '紫'},
    			 {id: 'black', text: '黑'}
    			],
    			"id","text",true,true,'',false,'',100)
    	
    	
    	//$("#query").ligerTextBox({ width:'800'});
    	$("#pts_columns").ligerTextBox({ width:'100'});
    	
    	$("#gt_istotalrow").ligerCheckBox();
    	$("#be_isquery").ligerCheckBox();
		$("#be_isestablish").ligerCheckBox();
		$("#be_ishide").ligerCheckBox();
    	
    	$("#be_alias").ligerTextBox({ width:'100'});
    
    	$("#be_establishattribute").ligerTextBox({ width:'100'});
       	$("#cs_rowid").ligerComboBox({
    		url: "queryCsCode.do?isCheck=false",
    		valueField: "id",
    		textField: "text",
    		selectBoxWidth: '200',
    		selectBoxHeight:'260',
    		setTextBySource: true,
    		width: '100',
    		autocomplete: true,
    		highLight: true,
    		keySupport: true,
    		async: true,
    		alwayShowInDown: true,
    	});
    	$("#be_enter").ligerTextBox({ width:'100'});
    	$("#be_startcolumn").ligerTextBox({ width:'100'});
    	$("#be_occupycolumn").ligerTextBox({ width:'100'});
    	$("#be_background").ligerTextBox({ width:'100'});
    	$("#be_fontcolor").ligerTextBox({ width:'100'});
    	$("#be_align").ligerTextBox({ width:'100'});
    	
    	$(':button').ligerButton({ width: 80 });
    }
    
</script>
</head>

<body style="padding: 0px; overflow: hidden;">
	 <div id="layout1">
             <div position="left">
				<div id="toptoolbar" ></div>
				<div id="maingrid"></div>
			</div>
            <div  position="center">
 				<table cellpadding="0" cellspacing="0" class="l-table-edit" >
    				<tr>
			        	<td align="right" class="l-table-edit-td" style="padding-left:10px;">标签页设置:</td>
			        	<td align="left" class="l-table-edit-td" >
			        		<input type="button" value="添加" onclick="addTab();"  />
			        	</td>
						<td align="left" id ="newTab" class="l-table-edit-td" colspan="17">
						</td>
			        </tr> 
			    </table>
				<div  id="layout2" position="center">
					<div id="buttonMod" position="top" >
						<table>
							<tr>
								<td align="right" class="l-table-edit-td" style="padding-left:10px;">查询条件:</td>
								<td align="left" id="query" colspan="18" ></td>
							</tr>
						</table>
						<table>
							<tr>
						   		<td align="right" class="l-table-edit-td" style="padding-left:10px;">按钮设置:</td>
								<td align="left" class="l-table-edit-td" >
									<input type="button" value="添加" onclick="addButton();"  /></td>
								<td  id='newButton' align="left" class="l-table-edit-td" colspan="17"></td>
						    </tr> 
							<tr>
								<td align="right" class="l-table-edit-td" style="padding-left:20px;">
										<input type="checkbox" value="1" id="gt_istotalrow" /></td>
								<td align="left" class="l-table-edit-td">是否合计行</td>
						  		<td align="left" ></td>
						  		<td align="left" ></td>
					       		<td align="left" colspan="15">
					       			<input type="button" value="保存" onclick="save();"  />
					       		</td>
							</tr>
									
						</table>
					</div>
					<div position="left">
							<div id="itemgrid"></div>
					</div>
					<div   position="center" >
						<div >
							<table cellpadding="0" cellspacing="0" class="l-table-edit" >
								<tr>
									<td align="right" class="l-table-edit-td" style="padding-left:20px;">
										<input type="checkbox" value="1" id="be_isquery" onChange="queryChange()"/></td>
									<td align="left" class="l-table-edit-td">是否作为查询条件</td>
									<td align="right" class="l-table-edit-td" style="padding-left:20px;">
										<input type="checkbox" value="1" id="be_isestablish" /></td>
									<td align="left" class="l-table-edit-td">是否可编辑</td>
									<td align="right" class="l-table-edit-td" style="padding-left:20px;">
										<input type="checkbox" value="1" id="be_ishide" /></td>
									<td align="left" class="l-table-edit-td">是否隐藏</td>
								</tr>
								<tr>	
									<td align="right" class="l-table-edit-td" style="padding-left:20px;" >展示名:</td>
								<td align="left" ><input type="text" id="be_alias" /></td>
								<td align="left" ></td>
								<td align="right" class="l-table-edit-td" style="padding-left:20px;" >填报属性:</td>
									<td align="left" ><input type="text" id="be_establishattribute" /></td>
									<td align="left" ></td>
								</tr>
								<tr>	
									<td align="right" class="l-table-edit-td" style="padding-left:20px;" >取值函数:</td>
								<td align="left" ><input type="text" id="cs_rowid" /></td>
								<td align="left" ></td>
								<td align="right" class="l-table-edit-td" style="padding-left:20px;" >回车跳转:</td>
									<td align="left" ><input type="text" id="be_enter" /></td>
									<td align="left" ></td>
								</tr>
								<tr>	
									<td align="right" class="l-table-edit-td" style="padding-left:20px;" >背景色:</td>
								<td align="left" ><input type="text" id="be_background" /></td>
								<td align="left" ></td>
								<td align="right" class="l-table-edit-td" style="padding-left:20px;" >字体色:</td>
									<td align="left" ><input type="text" id="be_fontcolor" /></td>
									<td align="left" ></td>
								</tr>
								<tr>	
									<td align="right" class="l-table-edit-td" style="padding-left:20px;" >开始列:</td>
								<td align="left" ><input type="text" id="be_startcolumn" /></td>
								<td align="left" ></td>
								<td align="right" class="l-table-edit-td" style="padding-left:20px;" >占用列:</td>
									<td align="left" ><input type="text" id="be_occupycolumn" /></td>
									<td align="left" ></td>
								</tr>
								<tr>	
									<td align="right" class="l-table-edit-td" style="padding-left:20px;" >对齐方式:</td>
									<td align="left" ><input type="text" id="be_align" /></td>
									<td align="left" ></td>
									<td align="left" ></td>
									<td align="left" ></td>
									<td align="left" ></td>
								</tr>
							</table>
					 		<br><br><br><br><br><br><br><br>
							&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp
							&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp
							&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp
							<input type="button" value="确定" onclick="saveSetInfo();"  />
						</div>	
					</div>
				</div>
            </div>
        </div> 
</body>
</html>