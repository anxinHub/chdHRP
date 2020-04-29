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
    var grid,  ptm_position ,ptm_position1;
    var gridManager = null;
    var pt_rowid, ptm_rowid , ptm_no_sheet,ptm_no_grid ;
    $(function () {
    	loadDict();
    	loadHead();	//加载数据
    	
    	$('input:radio[name="ptm_position"]').bind('change', function (){
    		var str;
    		$('input:radio[name="ptm_position"]').each(function () {
    			if(this.checked){
    				str = this.value ;
    			}
                
            });
    		if(str==7){
    			$("#ptm_position_x").ligerTextBox({disabled: false});
    	    	$("#ptm_position_y").ligerTextBox({ disabled: false});
    		}else{
    			$("#ptm_position_x").ligerTextBox({ disabled: true});
    	    	$("#ptm_position_y").ligerTextBox({ disabled: true});
    	    	$("#ptm_position_x").val("");
    	    	$("#ptm_position_y").val("");
    		}
         });
    	$('input:radio[name="ptm_position1"]').bind('change', function (){
    		var str;
    		$('input:radio[name="ptm_position1"]').each(function () {
    			if(this.checked){
    				str = this.value ;
    			}
                
            });
    		if(str==7){
    			$("#ptm_position_x1").ligerTextBox({disabled: false});
    	    	$("#ptm_position_y1").ligerTextBox({ disabled: false});
    		}else{
    			$("#ptm_position_x1").ligerTextBox({ disabled: true});
    	    	$("#ptm_position_y1").ligerTextBox({ disabled: true});
    	    	$("#ptm_position_x1").val("");
    	    	$("#ptm_position_y1").val("");
    		}
         });
    });
    
	  
    //查询
    function query(){
    	grid.options.parms = [];

		grid.options.newPage = 1;
		//根据表字段进行添加查询条件
		grid.options.parms.push({ name: 'pt_rowid', value: liger.get("pt_rowid").getValue() });

		grid.options.parms.push({ name: 'pt_attribute', value: liger.get("pt_attribute").getValue() });

		//加载查询条件
		grid.loadData(grid.where);
	}

    // 加载grid
    function loadHead(){
    	// 合同配置方案grid
		grid = $("#maingrid").ligerGrid({
			columns: [ 
				{ display: '合同配置方案', name: "pt_name", align: 'left',width:'50%',editor:{type:'string'}},
				{ display: '合同分类', name: "pt_type", align: 'left',width:'30%',editor:{type:'string'}},
				{ display: '属性', name: "pt_attribute", align: 'left', width:'20%',
					render :function(rowidex,rowdata,value){
						if(value==0){
							return "内置" ;
						}else if(value ==1){
							return "自定义";
						}
					}	
				}
			],
			dataAction: 'server',dataType: 'server',usePager : true, checkbox: true,rownumbers:true,enabledEdit: true,
			width: '100%', height: '100%', isAddRow:false ,
			url:'queryPactTemplateSet.do?isCheck=false',
			onSelectRow:queryModular,
			selectRowButtonOnly:true,//heightDiff: -10,
			toolbar: {
				items: [
					{ text: '新增', id:'add', click: addRow,icon:'add' },
					{ line:true },
					{ text: '保存', id:'save', click: save,icon:'save' },
					{ line:true },
					{ text: '删除', id:'delete', click: deleteTemplate,icon:'delete' },
					{ line:true }
				]
			}
		});

        gridManager = $("#maingrid").ligerGetGridManager();
        
    }
    //新增行
    function addRow(){
    	grid.addRow({"pt_attribute":1})
    }
    // 保存
    function save(){
  	  	var data = grid.getChanges();
        var ParamVo = [];

        if (data.length > 0 ) {
	          if (!validateGrid(data)) {
	              return false;
	          }
	          $(data).each(function() {
			  	if(this.__status =="add" || this.__status == "update"){
			  		 ParamVo.push(
		            			(this.pt_rowid ?this.pt_rowid:-1) +"@" +
			                  	this.pt_name + "@" +
			                  	this.pt_type + "@" +
			                  	this.pt_attribute + "@" +
			                  	this.ROW_ID 
			              )
			  	}
	             
	          });
	          if(ParamVo.length > 0){
	        	  ajaxJsonObjectByUrl("savePactTemplateSet.do?isCheck=false",{"ParamVo":ParamVo.toString()}, function(responseData) {
		    			if (responseData.state == "true") {
		    				query();
		    			}
		    	 });
	          }else{
	        	  $.ligerDialog.warn('没有需要保存的数据!');
	          }
	          
        } else {
            $.ligerDialog.warn('没有需要保存的数据!');
        }
    }
    function validateGrid(data) {  
    	var msg="";
 		var rowm = "";
 		//判断grid 中的数据是否为空
 		var targetMap = new HashMap();
 		$.each(data,function(i, v){
 			rowm = "";
			if (!v.pt_name) {
				rowm+="[合同配置方案]、";
			}
			if (!v.pt_type) {
				rowm+="[合同分类]、";
			}
			if (!v.pt_attribute) {
				rowm+="[属性]、";
			}
			if(rowm != ""){
				rowm = "第"+(Number(v.ROW_ID)+1)+"行" + rowm.substring(0, rowm.length-1) + "不能为空" + "\n\r";
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
    //删除
    function deleteTemplate(){
    	var data = grid.getCheckedRows();
        var ParamVo = [];

        if (data.length > 0 ) {
	          $(data).each(function() {
					ParamVo.push(
						this.pt_rowid 
					)
	          });
	          
	          ajaxJsonObjectByUrl("deletePactTemplateSet.do?isCheck=false",{"ParamVo":ParamVo.toString()}, function(responseData) {
	    			if (responseData.state == "true") {
	    				grid.deleteRange(data);
	    				query();
	    			}
	    	  });
        } else {
            $.ligerDialog.warn('请选择要删除的数据!');
        }
    }
  //新增 表单样式DIV
    function addSheetDiv(){
    	var data = grid.getCheckedRows();
    	if(data.length != 1){
    		$.ligerDialog.warn('请选择一行数据!');
    	}else{
    		var ptm_width = $("#ptm_width").val();
        	var ptm_height = $("#ptm_height").val() ;
        	if(!ptm_width){
        		$.ligerDialog.warn('请填写表单宽度(W)!');
        		return false ;
        	}
        	if(!ptm_height){
        		$.ligerDialog.warn('请填写表单高度(H)!');
        		return false ;
        	}
        	var ptm_position ;
        	$('input:radio[name="ptm_position"]').each(function () {
    			if(this.checked){
    				ptm_position = this.value ;
    			}
                
            });
        	if(ptm_position){
        		if(ptm_position==7){
        			var ptm_position_x = $("#ptm_position_x").val();
        			var ptm_position_y = $("#ptm_position_y").val();
        			if(!ptm_position_x || !ptm_position_y){
        	    		$.ligerDialog.warn('表格位置选择自定义时,请填写表单x轴/y轴值!');
        	    		return false ;
        	    	}
        		}
        	}else{
        		 $.ligerDialog.warn('请选择表单位置!');
        		 return false ;
        	}
        	var ptm_position_name ;
        	if(ptm_position== 1){
        		ptm_position_name="底部居中"
        	}else if(ptm_position== 2){
        		ptm_position_name="顶部居中"
        	}else if(ptm_position== 3){
        		ptm_position_name="底部居右"
        	}else if(ptm_position== 4){
        		ptm_position_name="	底部居左"
        	}else if(ptm_position== 5){
        		ptm_position_name="顶部居右"
        	}else if(ptm_position== 6){
        		ptm_position_name="顶部居左"
        	}
        	 var box=document.getElementById("rightDiv");
        	 var odiv=document.createElement("div"); 
        	 var ptm_name ='表单模块表单';
        	 var ptm_code ='2'
        		 ++ptm_no_sheet;
        	 if(ptm_no_sheet<10){
        		 ptm_name +='0'+ptm_no_sheet
        		 ptm_code +="0"+ptm_no_sheet
        		 odiv.id="0-"+ptm_code+"-"+ptm_no_sheet;
        		 odiv.title=ptm_name;
        	 }else{
        		 ptm_name +=ptm_no_sheet
        		 ptm_code +=ptm_no_sheet
        		 odiv.id="0-"+ptm_code+"-"+ptm_no_sheet;
        		 odiv.title=ptm_name;
        	 }
        	 //var text=document.createTextNode(ptm_name); 
              	
              	odiv.style.border="2px solid #aecaf0";
              	odiv.style.height="100";
              	odiv.style.width="auto";
              	odiv.style.padding = "30px";
              	odiv.style.filter="alpha(opacity=70)";            
              	odiv.style.margin="5px";     
              	odiv.style.cursor="hand";
              	odiv.algin="center"; 
              	odiv.ondblclick = function(){deleteDiv(odiv.id);};//双击删除DIV
             if(ptm_position==7){
            	 odiv.innerHTML="<ul><li><a href=javascript:openSheet("+ptm_rowid+")><h3>"+ptm_name+"</h3></a></li>"+
            	 "<li>宽度:"+ptm_width+"%|高度:"+ptm_height+"%|位置:7-自定义|x:"+ptm_position_x+"|y:"+ptm_position_y+"</li><ul>"; 
             }else{
            	 odiv.innerHTML="<ul><li><a href=javascript:openSheet("+ptm_rowid+")><h3>"+ptm_name+"</h3></a></li>"+
            	 "<li>宽度:"+ptm_width+"%|高度:"+ptm_height+"%|位置:"+ptm_position+"-"+ptm_position_name+"</li><ul>";
             }
        	 
        	 box.appendChild(odiv);
    	}
    	
    }
 	//新增 表格样式DIV
    function addGridDiv(){
    	var data = grid.getCheckedRows();
    	if(data.length != 1){
    		$.ligerDialog.warn('请选择一行数据!');
    	}else{
    		var ptm_width = $("#ptm_width1").val();
        	var ptm_height = $("#ptm_height1").val() ;
        	if(!ptm_width){
        		$.ligerDialog.warn('请填写表格宽度(W)!');
        		return false ;
        	}
        	if(!ptm_height){
        		$.ligerDialog.warn('请填写表格高度(H)!');
        		return false ;
        	}
        	var ptm_position ;
        	$('input:radio[name="ptm_position1"]').each(function () {
    			if(this.checked){
    				ptm_position = this.value ;
    			}
                
            });
        	if(ptm_position){
        		if(ptm_position==7){
        			var ptm_position_x = $("#ptm_position_x1").val();
        			var ptm_position_y = $("#ptm_position_y1").val();
        			if(!ptm_position_x || !ptm_position_y){
        	    		$.ligerDialog.warn('表格位置选择自定义时,请填写表格x轴/y轴值!');
        	    		return false ;
        	    	}
        		}
        	}else{
        		 $.ligerDialog.warn('请选择表单位置!');
        		 return false ;
        	}
        	var ptm_position_name ;
        	if(ptm_position== 1){
        		ptm_position_name="底部居中"
        	}else if(ptm_position== 2){
        		ptm_position_name="顶部居中"
        	}else if(ptm_position== 3){
        		ptm_position_name="底部居右"
        	}else if(ptm_position== 4){
        		ptm_position_name="	底部居左"
        	}else if(ptm_position== 5){
        		ptm_position_name="顶部居右"
        	}else if(ptm_position== 6){
        		ptm_position_name="顶部居左"
        	}
        	 var box=document.getElementById("rightDiv");
        	 var odiv=document.createElement("div"); 
        	 var ptm_name ='表格模块表格';
        	 var ptm_code ='1'
        		 ++ptm_no_grid;
        	 if(ptm_no_grid<10){
        		 ptm_name +='0'+ptm_no_grid
        		 ptm_code +="0"+ptm_no_grid
        		 odiv.id="0-"+ptm_code+"-"+ptm_no_grid;
        		 odiv.title=ptm_name;
        	 }else{
        		 ptm_name +=ptm_no_grid
        		 ptm_code +=ptm_no_grid
        		 odiv.id="0-"+ptm_code+"-"+ptm_no_grid;
        		 odiv.title=ptm_name;
        	 }
        	 //var text=document.createTextNode(ptm_name); 
              	odiv.style.border="2px solid #aecaf0";
              	odiv.style.height="100";
              	odiv.style.width="auto";
              	odiv.style.padding = "30px";
              	odiv.style.filter="alpha(opacity=70)";            
              	odiv.style.margin="5px";     
              	odiv.style.cursor="hand";
              	odiv.algin="center";   
              	odiv.ondblclick = function(){deleteDiv(odiv.id);};//双击删除DIV
           	 if(ptm_position==7){
            	 odiv.innerHTML="<ul><li><a href=javascript:openGrid("+ptm_rowid+")><h3>"+ptm_name+"</h3></a></li>"+
            	 "<li>宽度:"+ptm_width+"%|高度:"+ptm_height+"%|位置:7-自定义|x:+"+ptm_position_x+"|y:"+ptm_position_y+"</li><ul>"; 
             }else{
            	 odiv.innerHTML="<ul><li><a href=javascript:openGrid("+ptm_rowid+")><h3>"+ptm_name+"</h3></a></li>"+
            	 "<li>宽度:"+ptm_width+"%|高度:"+ptm_height+"%|位置:"+ptm_position+"-"+ptm_position_name+"</li><ul>";
             }
        	 box.appendChild(odiv);
    	}
    	
    }
    function deleteDiv(id){
    	$.ligerDialog.confirm('确定删除?', function(yes) {
    		if(yes){
    			var ptm_rowid = id.split("-")[0]
    			if(ptm_rowid){
    				ajaxJsonObjectByUrl("deletePactTemplateModular.do?isCheck=false?&ptm_rowid="+ptm_rowid,"", function(responseData) {
    	    			if (responseData.state == "true") {
    	    				
    	    			}
    	    	  	});
    			}
    			var e = document.getElementById(id);
    	    	document.getElementById("rightDiv").removeChild(e);
    			
    		}
    	})
    	
    }
    //保存 合同模板配置方案模块
    function saveModular(){
    	var data = grid.getCheckedRows();
    	if(data.length != 1){
    		$.ligerDialog.warn('请选择一行数据!');
    	}else{
    		var pt_rowid ;
    		$(data).each(function() {
    			 pt_rowid = this.pt_rowid ;
	         });
        	var ParamVo = [];
        	var divArr = $("#rightDiv").find("div");
    		$(divArr).each(function() {
    			var str = this.innerHTML.substring(this.innerHTML.lastIndexOf("<li>")+4,this.innerHTML.lastIndexOf("</li>"))
    			var ptm_rowid = this.id.split("-")[0];
    			var ptm_code = this.id.split("-")[1];
    			var ptm_no = this.id.split("-")[2];
    			var setInfo = str.split("|");
    			var count = setInfo.length ;
    			
    			ParamVo.push(// 数据格式  pt_rowid@ptm_rowid@ptm_code@ptm_name@ptm_showmode
    					//@ptm_no@ptm_height@ptm_width@ptm_position@ptm_position_x@ptm_position_y   
    					
    					//div id 组成 格式  ptm_rowid（新增时 赋0）_ptm_code_ptm_no
    					pt_rowid+"@"+ptm_rowid +"@"+ ptm_code +"@"+this.title+"@"+
    					(ptm_code.startsWith("1")?1:2) +"@"+ptm_no+"@"+setInfo[1].split(":")[1]+"@"
    					+setInfo[0].split(":")[1]+"@"+setInfo[2].split(":")[1].split("-")[0]
    					+"@"+(count==3?'':setInfo[3].split(":")[1])+"@"+(count==3?'':setInfo[4].split(":")[1])
    			)
	        });
    		if(ParamVo.length>0){
    			ajaxJsonObjectByUrl("savePactTemplateModular.do?isCheck=false",{"ParamVo":ParamVo.toString()}, function(responseData) {
	    			if (responseData.state == "true") {
	    				location.reload() ;
	    			}
	    	  });
    		}else{
    			$.ligerDialog.warn('没有需要保存的数据!');
    		}
        	
    	}
    	
    }
    
    function queryModular(data, rowindex, rowobj){
    	ptm_no_sheet = data.ptm_no_sheet;
    	ptm_no_grid = data.ptm_no_grid ;
    	if(data.pt_rowid){
    		pt_rowid = data.pt_rowid ;
    		 ajaxJsonObjectByUrl("queryTemplateModular.do?isCheck=false&pt_rowid="+data.pt_rowid,"", function(responseData) {
	    			if (responseData.Total >0) {
	    				//选中行数据 重新 渲染 配置样式展示div
	    				$('#rightDiv').empty();
	    				$(responseData.Rows).each(function() {
	    					//重新渲染 配置样式展示
	    					renderDiv(this)
	    				})
	    			}
	    	 });
    	}
    }
    function renderDiv(obj){
		var box=document.getElementById("rightDiv");
		var odiv=document.createElement("div"); 
		
		 odiv.id=obj.ptm_rowid+"-"+obj.ptm_code+"-"+obj.ptm_no;
		 odiv.title=obj.ptm_name;
		 var ptm_position_name ;
	    	if(obj.ptm_position== 1){
	    		ptm_position_name="底部居中"
	    	}else if(obj.ptm_position== 2){
	    		ptm_position_name="顶部居中"
	    	}else if(obj.ptm_position== 3){
	    		ptm_position_name="底部居右"
	    	}else if(obj.ptm_position== 4){
	    		ptm_position_name="	底部居左"
	    	}else if(obj.ptm_position== 5){
	    		ptm_position_name="顶部居右"
	    	}else if(obj.ptm_position== 6){
	    		ptm_position_name="顶部居左"
	    	}
		//var text=document.createTextNode(ptm_name); 
	    	odiv.style.border="2px solid #aecaf0";
	    	odiv.style.height="100";
	    	odiv.style.width="auto";
	    	odiv.style.padding = "30px";
	    	odiv.style.filter="alpha(opacity=70)";            
	    	odiv.style.margin="5px";     
	    	odiv.style.cursor="hand";
	    	odiv.algin="center";   
	    	odiv.ondblclick = function(){deleteDiv(odiv.id);};//双击删除DIV
	    	if(obj.ptm_showmode == 1){
	    		if(obj.ptm_position==7){
	   		  	 odiv.innerHTML="<ul><li><a href=javascript:openGrid("+obj.ptm_rowid+")><h3>"+obj.ptm_name+"</h3></a></li>"+
	   		  	 "<li>宽度:"+obj.ptm_width+"%|高度:"+obj.ptm_height+"%|位置:7-自定义|x:+"+obj.ptm_position_x+"|y:"+obj.ptm_position_y+"</li><ul>"; 
	   		   }else{
	   		  	 odiv.innerHTML="<ul><li><a href=javascript:openGrid("+obj.ptm_rowid+")><h3>"+obj.ptm_name+"</h3></a></li>"+
	   		  	 "<li>宽度:"+obj.ptm_width+"%|高度:"+obj.ptm_height+"%|位置:"+obj.ptm_position+"-"+ptm_position_name+"</li><ul>";
	   		   }
	    	}else{
	    		if(obj.ptm_position==7){
		   		  	 odiv.innerHTML="<ul><li><a href=javascript:openSheet("+obj.ptm_rowid+")><h3>"+obj.ptm_name+"</h3></a></li>"+
		   		  	 "<li>宽度:"+obj.ptm_width+"%|高度:"+obj.ptm_height+"%|位置:7-自定义|x:+"+obj.ptm_position_x+"|y:"+obj.ptm_position_y+"</li><ul>"; 
		   		   }else{
		   		  	 odiv.innerHTML="<ul><li><a href=javascript:openSheet("+obj.ptm_rowid+")><h3>"+obj.ptm_name+"</h3></a></li>"+
		   		  	 "<li>宽度:"+obj.ptm_width+"%|高度:"+obj.ptm_height+"%|位置:"+obj.ptm_position+"-"+ptm_position_name+"</li><ul>";
		   		   }
	    	}
		 	
		box.appendChild(odiv);
    }
    
    // 表单模板项属性配置页面链接
    function openSheet(ptm_rowid){
    	if(ptm_rowid){
    		parent.$.ligerDialog.open({
				url : 'hrp/pac/template/pacttemplateset/pactTemplateSheetSetPage.do?isCheck=false&ptm_rowid='+ptm_rowid+'&pt_rowid='+pt_rowid,
				height : $(window).height(),
				width : 1200,
				modal : true,
				showToggle : false,
				showMax : false,
				showMin : false,
				isResize : true,
				parentframename : window.name,
				title : '表单配置'
			});
    	}else{
    		$.ligerDialog.error("表单样式模块数据未保存,请保存后再配置！");
    	}  
    	
    }
	// 表格模板项属性配置页面链接
    function openGrid(ptm_rowid){
    	if(ptm_rowid){
    		parent.$.ligerDialog.open({
				url : 'hrp/pac/template/pacttemplateset/pactTemplateGridSetPage.do?isCheck=false&ptm_rowid='+ptm_rowid+'&pt_rowid='+pt_rowid,
				height : $(window).height(),
				width : 1200,
				modal : true,
				showToggle : false,
				showMax : false,
				showMin : false,
				isResize : true,
				parentframename : window.name,
				title : '表格配置'
			});
    	}else{
    		$.ligerDialog.error("表格样式模块数据未保存,请保存后再配置！");
    	} 
    	
    }
    function loadDict(){
    	autocomplete("#pt_rowid","../../basicset/select/queryPactTemplate.do?isCheck=false","id","text",true,true);
    	autoCompleteByData("#pt_attribute",[{id: 0, text: '内置'}, {id: 1, text: '自定义'}],"id","text",true,true,)
    	//布局
    	$("#layout1").ligerLayout({ leftWidth: 400,rightWidth: 1050,allowLeftCollapse:false,allowRightCollapse:false}); 
    	$("#layout2").ligerLayout({ centerBottomHeight:300});
    	
    	$("#ptm_width").ligerTextBox({ width:'40'});
    	$("#ptm_height").ligerTextBox({ width:'40'});
    	$("#ptm_width1").ligerTextBox({ width:'40'});
    	$("#ptm_height1").ligerTextBox({ width:'40'});
    	$("#ptm_position_x").ligerTextBox({ width:'40',disabled: true});
    	$("#ptm_position_y").ligerTextBox({ width:'40',disabled: true});
    	$("#ptm_position_x1").ligerTextBox({ width:'40',disabled: true});
    	$("#ptm_position_y1").ligerTextBox({ width:'40',disabled: true});
    	
    	ptm_position = $('input:radio[name="ptm_position"]').ligerRadio();
    	ptm_position1 = $('input:radio[name="ptm_position1"]').ligerRadio();
    	
    	$(':button').ligerButton({ width: 80 });
    }
    
</script>
</head>

<body style="padding: 0px; overflow: hidden;">
	<table cellpadding="0" cellspacing="0" class="l-table-edit" >
        <tr>
        	<td align="right" class="l-table-edit-td" style="padding-left:20px;">模板方案：</td>
			<td align="left" class="l-table-edit-td"><input name="pt_rowid" type="text" id="pt_rowid" /></td>
			<td align="left"></td>
       		<td align="right" class="l-table-edit-td"  style="padding-left:20px;">属性:</td>
       		<td align="left"><input type="text" id="pt_attribute" name="pt_attribute" /></td>
       		<td align="left"></td>
       		<td align="right"><input type="button" value="查询" onclick="query();"  /></td>
       		<td align="right"><input type="button" value="保存" onclick="saveModular();"/></td>
       		
        </tr> 
	</table>
	<div style="width: 100%;">
	</div>
	 <div id="layout1">
             <div position="left">
				<div id="toptoolbar" ></div>
				<div id="maingrid"></div>
			</div>
            <div  id="layout2" position="center">
    				<div id="sheetMod" position="center" title="表单样式模块">
    					<div >
    						<table cellpadding="0" cellspacing="0" class="l-table-edit" >
    							<tr><h4>大小</h4></tr>
		     					<tr>
						        	<td align="right" class="l-table-edit-td" style="padding-left:20px;">W:</td>
									<td align="left" class="l-table-edit-td">
										<input name="ptm_width" type="text" id="ptm_width" /></td >
									<td align="right">%</td>
									<td align="left" class="l-table-edit-td"></td>
						       		<td align="right" class="l-table-edit-td"  style="padding-left:20px;">H:</td>
						       		<td align="left" class="l-table-edit-td">
						       			<input type="text" id="ptm_height" type="text" /></td>
						       		<td align="right">%</td>
						       		<td align="left" class="l-table-edit-td"></td>
						        </tr> 
						   </table>
    					</div>
    					<div>
    						<table cellpadding="0" title="位置" cellspacing="0" class="l-table-edit" >
    							<tr><h4>位置</h4></tr>
		     					<tr>
		     						<td align="right" class="l-table-edit-td" style="padding-left:20px;">
		     							<input type="radio" value="1" name="ptm_position" /></td>
		     						<td align="left" class="l-table-edit-td">底部居中</td>
		     						<td align="right" class="l-table-edit-td" style="padding-left:20px;">
		     							<input type="radio" value="2" name="ptm_position" /></td>
		     						<td align="left" class="l-table-edit-td">顶部居中</td>
		     					</tr>
		     					<tr>	
		     						<td align="right" class="l-table-edit-td" style="padding-left:20px;" >
		     							<input type="radio" value="3" name="ptm_position" /></td>
		     						<td align="left" >底部居右</td>
		     						<td align="right" class="l-table-edit-td" style="padding-left:20px;" >
		     							<input type="radio" value="4" name="ptm_position" /></td>
		     						<td align="left" >底部居左</td>
		     					</tr>
		     					<tr>
		     						<td align="right" class="l-table-edit-td" style="padding-left:20px;" >
		     							<input type="radio" value="5" name="ptm_position" /></td>
		     						<td align="left" >顶部居右</td>
		     						<td align="right" class="l-table-edit-td" style="padding-left:20px;">
		     							<input type="radio" value="6" name="ptm_position" /></td>
		     						<td align="left" >顶部居左</td>
		     					</tr>
		     					<tr>
		     					<tr>
		     						<td align="right" class="l-table-edit-td" style="padding-left:20px;">
		     							<input type="radio" value="7" name="ptm_position" /></td>
		     						<td align="left" class="l-table-edit-td">自定义</td>
		     						<td align="left" class="l-table-edit-td"></td>
		     						<td align="left" class="l-table-edit-td"></td>
		     					</tr>
		     					<tr>
		     						<td align="right" class="l-table-edit-td" style="padding-left:20px;">X:</td>
									<td align="left" >
										<input id="ptm_position_x" type="text" /></td >
						       		<td align="right" class="l-table-edit-td"  style="padding-left:20px;">Y:</td>
						       		<td align="left">
						       			<input type="text" id="ptm_position_y"  /></td>
			     				</tr>
			     			</table>
			     			 <br>
			     			&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp
			     			<input type="button" value="添加表单样式" onclick="addSheetDiv();"  />
	    				</div>
    					
    			</div>
    			
    			<div id="sheetMod"  position="centerbottom" title="表格样式模块">
    					<div >
	     					<table cellpadding="0" cellspacing="0" class="l-table-edit" >
	     						<tr><h4>大小</h4></tr>
		     					<tr>
						        	<td align="right" class="l-table-edit-td" style="padding-left:20px;">W:</td>
									<td align="left" class="l-table-edit-td">
										<input name="ptm_width" type="text" id="ptm_width1" /></td >
									<td align="right">%</td>
									<td align="left" class="l-table-edit-td"></td>
						       		<td align="right" class="l-table-edit-td"  style="padding-left:20px;">H:</td>
						       		<td align="left" class="l-table-edit-td">
						       			<input type="text" id="ptm_height1" type="text" /></td>
						       		<td align="right">%</td>
						       		<td align="left" class="l-table-edit-td"></td>
						        </tr> 
						   </table>
    					</div>
    					<div>
    						<table cellpadding="0" title="位置" cellspacing="0" class="l-table-edit" >
    							<tr><h4>位置</h4></tr>
		     					<tr>
		     						<td align="right" class="l-table-edit-td" style="padding-left:20px;">
		     							<input type="radio" value="1" name="ptm_position1" /></td>
		     						<td align="left" class="l-table-edit-td">底部居中</td>
		     						<td align="right" class="l-table-edit-td" style="padding-left:20px;">
		     							<input type="radio" value="2" name="ptm_position1" /></td>
		     						<td align="left" class="l-table-edit-td">顶部居中</td>
		     					</tr>
		     					<tr>	
		     						<td align="right" class="l-table-edit-td" style="padding-left:20px;" >
		     							<input type="radio" value="3" name="ptm_position1" /></td>
		     						<td align="left" >底部居右</td>
		     						<td align="right" class="l-table-edit-td" style="padding-left:20px;" >
		     							<input type="radio" value="4" name="ptm_position1" /></td>
		     						<td align="left" >底部居左</td>
		     					</tr>
		     					<tr>
		     						<td align="right" class="l-table-edit-td" style="padding-left:20px;" >
		     							<input type="radio" value="5" name="ptm_position1" /></td>
		     						<td align="left" >顶部居右</td>
		     						<td align="right" class="l-table-edit-td" style="padding-left:20px;">
		     							<input type="radio" value="6" name="ptm_position1" /></td>
		     						<td align="left" >顶部居左</td>
		     					</tr>
		     					<tr>
		     					<tr>
		     						<td align="right" class="l-table-edit-td" style="padding-left:20px;">
		     							<input type="radio" value="7" name="ptm_position1" /></td>
		     						<td align="left" class="l-table-edit-td">自定义</td>
		     						<td align="left" class="l-table-edit-td"></td>
		     						<td align="left" class="l-table-edit-td"></td>
		     					</tr>
		     					<tr>
		     						<td align="right" class="l-table-edit-td" style="padding-left:20px;">X:</td>
									<td align="left" >
										<input id="ptm_position_x1" type="text"  /></td >
						       		<td align="right" class="l-table-edit-td"  style="padding-left:20px;">Y:</td>
						       		<td align="left">
						       			<input type="text" id="ptm_position_y1" /></td>
			     				</tr>
						   </table>
						    <br>
			     			&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp
			     			<input type="button" value="添加表格样式" onclick="addGridDiv();"  />
    				</div>
     					
     			</div>
            </div>
            <div id="rightDiv" position="right" title="配置样式展示">
            
            </div>
        </div> 
</body>
</html>