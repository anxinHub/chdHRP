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
<script src="<%=path%>/lib/hrp/hr/hr.js"></script>
<style type="text/css">
.l-layout-content{
	height: 100%;
}
/* 搜索框 */
.search-form {
	padding: 5px;
	box-sizing: border-box;
	background: #e0ecff;
	border-bottom: 1px solid #a3c0e8;
}
/* 文本input */
.text-input {
	box-sizing: border-box;
	width: 180px;
	height: 26px;
	padding: 1px 1px 1px 5px;
	border: 1px solid #aecaf0;
	border-radius: 1px;
	outline: none;
	width: 140px;
	-webkit-box-shadow: inset 0 1px 1px rgba(0, 0, 0, .1);
	box-shadow: inset 0 1px 1px rgba(0, 0, 0, .1);
}
</style>
<script type="text/javascript">
	var tree
	$(function() {
		init()
		loadTree();
	});
	function init(){
		$("#layout").ligerLayout({leftWidth: 230, minLeftWidth: 230, allowLeftResize: false});
		
		 $("#toptoolbar").ligerToolBar({ items: [
                 { text: '样式', click: styleTemplate},
                 { line:true },
                 { text: '选择模板', click: setTemplate,icon:'account'},
                 { line:true },
                 { text: '保存', click: saveTemplate,icon:'add'},
                 { line:true },
                 { text:'模板编码:',id:"template_code_id",disable: true},
             ]});
		$("div[toolbarid='template_code_id']","#toptoolbar").after("<div style='margin:2px;'><input type='text' id='template_code'  disabled='true' /></div>");

		$("#search_input").keyup(function(e) {
			var key = $("#search_input").val();
			tree.options.url = 'queryHrPageDesignTree.do?isCheck=false&key='+key;
			tree.reload();
		});
	}
	function loadTree() {
		tree = $("#mainTree").ligerTree(
				{url: 'queryHrPageDesignTree.do?isCheck=false', 
            	 ajaxType: 'post' ,
            	 idFieldName:'id',
            	 textFieldName: 'text',
                 parentIDFieldName:'pid', 
                 nodeWidth:'100%',
            	 checkbox: false,
            	 isExpand: false, 
            });
	}
	
        //选择模板
	    setTemplate = function (){
	    	parent.$.ligerDialog.open({
				title : '模板设置',
				height : $(window).height(),
				width : $(window).width(),
				url : 'hrp/hr/sc/hrpagetemplate/hrPageTemplateMainPage.do?isCheck=false',
				modal : true,
				showToggle : false,
				showMax : false,
				showMin : true,
				isResize : true,
				parentframename : window.name, //用于parent弹出层调用本页面的方法或变量
				buttons: [{
					text: '取消',
					onclick: function(item, dialog) {
						dialog.close();
					}
				} ]
			});
	    }
	   //保存
	    saveTemplate = function (){
	    	   if(!tree.getSelected()){
	     		 $.ligerDialog.error('请选择树节点');
	     		 return;
	     	   }
	    	  
			   if($("#template_code").val()==""){
			    	 $.ligerDialog.error('请选择模板');
			    	 return 
			   }
			    var textMap = new HashMap(); 
			    var checkboxMap = new HashMap(); 
			    var inputs= document.getElementById('elemTemplateDiv').getElementsByTagName('input');
	        	for(var i=0; i<inputs.length;i++){
	        		if(inputs[i].type == "text"){
	        			textMap.put(inputs[i].id,$("#"+inputs[i].id).val())
	        		}
	        		if(inputs[i].type == "checkbox"){
	        			checkboxMap.put(inputs[i].name,inputs[i].name)
	        		}
	        	}
	        	
	          console.log(textMap)
	        	
		      para = {
		    		  template_code:$("#template_code").val(),//模板编码
		    		  table_type_code:(tree.getSelected().data.table_type_code==null?null:tree.getSelected().data.table_type_code),
					  page_code:(tree.getSelected().data.page_code==null?null:tree.getSelected().data.page_code)
				     }
	    	        ajaxJsonObjectByUrl("saveHrPageDesign.do?isCheck=false",para,function(responseData){
	          });
		}

	    styleTemplate= function (){
	    	parent.$.ligerDialog.open({
				title : '模板设置',
				height : $(window).height(),
				width : $(window).width(),
				url : 'hrp/hr/sc/hrpagetemplate/hrPageTemplateStyleTestMainPage.do?isCheck=false',
				modal : true,
				showToggle : false,
				showMax : true,
				showMin : true,
				isResize : true,
				parentframename : window.name, //用于parent弹出层调用本页面的方法或变量
			});
		}

	    useTemplateDetails = function (template_data){
	    	$("#template_code").val(template_data.template_code);
	    	selectorTemplate(template_data);
		}
        
        selectorTemplate = function (template_data){
         	document.getElementById("elemTemplateDiv").innerHTML='';
	        var template_json = JSON.parse(template_data.template_json);
	        var num=1;
	        for(var p in template_json){
	          if(template_json[p] =="grid"){gridElemTemplate(num)}
              if(template_json[p] =="tree"){treeElemTemplate(num)}
	          num++;
	        }
	        eventElemTemplate();
	    }
          //树形
          treeElemTemplate = function (id){
        	  var elem_template_div = document.getElementById("elemTemplateDiv");
        	  var elem_table = createTemplateElemTable();
        	  var elem_tr = createTemplateElemTr();
        	  var elem_td = createTemplateElemTd();
        	  var elem_span = createTemplateElemSpan("树形:")
        	  elem_td.appendChild(elem_span);
        	  elem_tr.appendChild(elem_td);
        	  elem_table.appendChild(elem_tr);
        	  elem_template_div.appendChild(elem_table);

        	  var elem_td  = createTemplateElemTd();
        	  var elem_div = createTemplateElemDiv();
        	  var elem_input_text = createTemplateElemInputText("template_tree"+id);
        	  elem_div.appendChild(elem_input_text);
        	  elem_td.appendChild(elem_div);
        	  elem_tr.appendChild(elem_td);
        	  var elem_td  = createTemplateElemTd();
        	  var elem_button = createTemplateElemInputButton(function (){
	        			parent.$.ligerDialog.open({
	        				title : '请求',
	        				width : 600,
	        				height : 500,
	        				url : 'hrp/hr/sc/hrpagedesign/hrPageDesignTreeElemMainPage.do?isCheck=false',
	        				modal : true,
	        				showToggle : false,
	        				showMax : false,
	        				showMin : true,
	        				isResize : true,
	        				parentframename : window.name, //用于parent弹出层调用本页面的方法或变量
	        				treeElemInputTextId:"template_tree"+id,
	        				buttons: [{text: '确定',onclick: function (item, dialog) {dialog.frame.save();},cls:'l-dialog-btn-highlight' },
		        			          { text: '取消', onclick: function (item, dialog) { dialog.close(); }
			        	              }]
	        			});
            	  });
        	  elem_td.appendChild(elem_button);
        	  elem_tr.appendChild(elem_td);
        	  var elem_div = createTemplateElemCheckBoxDiv("");
        	  for(var api in ligerUiTreeApi){
        		  var elemcheckbox = createTemplateElemInputCheckbox("template_tree_checkbox"+id,api);
        		  elem_div.appendChild(elemcheckbox);
        		  var elemcheckboxspan = createTemplateElemCheckBoxSpan(ligerUiTreeApi[api]);
        		  elem_div.appendChild(elemcheckboxspan);
    	        }
        	  elem_template_div.appendChild(elem_div);
          }

          function setElemTemplateTree(treeElemInputTextId,treeData){
              var id = document.getElementById(treeElemInputTextId);
              $(id).val(treeData.data.text)
          }
          
         //表格
         gridElemTemplate = function (id){
       	  var elem_template_div = document.getElementById("elemTemplateDiv");
       	  var elem_table = createTemplateElemTable();
       	  var elem_tr = createTemplateElemTr();
       	  var elem_td = createTemplateElemTd();
       	  var elem_span = createTemplateElemSpan("表格:")
       	  elem_td.appendChild(elem_span);
       	  elem_tr.appendChild(elem_td);
       	  elem_table.appendChild(elem_tr);
       	  elem_template_div.appendChild(elem_table);

       	  var elem_td  = createTemplateElemTd();
       	  var elem_div = createTemplateElemDiv();
       	  var elem_input_text = createTemplateElemInputText("template_grid"+id);
       	  elem_div.appendChild(elem_input_text);
      	  elem_td.appendChild(elem_div);
      	  elem_tr.appendChild(elem_td);
       	  var elem_td  = createTemplateElemTd();
       	  var elem_button = createTemplateElemInputButton(function (){
	       		parent.$.ligerDialog.open({
					title : '请求',
					width : 600,
					height : 500,
					url : 'hrp/hr/sc/hrpagedesign/hrPageDesignGridElemMainPage.do?isCheck=false',
					modal : true,
					showToggle : false,
					showMax : false,
					showMin : true,
					isResize : true,
					parentframename : window.name, //用于parent弹出层调用本页面的方法或变量
					gridElemInputTextId:"template_grid"+id,
					buttons: [{text: '确定',onclick: function (item, dialog) {dialog.frame.save();},cls:'l-dialog-btn-highlight' },
	    			          { text: '取消', onclick: function (item, dialog) { dialog.close(); }
	        	              }]
				});
           	  });
       	  elem_td.appendChild(elem_button);
       	  elem_tr.appendChild(elem_td);
       	  var elem_div = createTemplateElemCheckBoxDiv("");
       	  for(var api in ligerUiGridApi){
       		  var elemcheckbox = createTemplateElemInputCheckbox("template_grid_checkbox"+id,api);
       		  elem_div.appendChild(elemcheckbox);
       		  var elemcheckboxspan = createTemplateElemCheckBoxSpan(ligerUiGridApi[api]);
       		  elem_div.appendChild(elemcheckboxspan);
   	        }
       	  elem_template_div.appendChild(elem_div);
         }

         function setElemTemplateGrid(gridElemInputTextId,treeData){
             var id = document.getElementById(gridElemInputTextId);
             $(id).val(treeData.data.text)
         }
         //事件
         eventElemTemplate = function (){
          	  var elem_template_div = document.getElementById("elemTemplateDiv");
          	  var elem_table = createTemplateElemTable();
          	  var elem_tr = createTemplateElemTr();
          	  var elem_td = createTemplateElemTd();
          	  var elem_span = createTemplateElemSpan("事件:")
          	  elem_td.appendChild(elem_span);
          	  elem_tr.appendChild(elem_td);
          	  elem_table.appendChild(elem_tr);
          	  elem_template_div.appendChild(elem_table);

          	  var elem_td  = createTemplateElemTd();
          	  var elem_div = createTemplateElemDiv();
          	  var elem_input_text = createTemplateElemInputText("template_event");
          	  elem_div.appendChild(elem_input_text);
         	  elem_td.appendChild(elem_div);
         	  elem_tr.appendChild(elem_td);
          	  var elem_td  = createTemplateElemTd();
          	  var elem_button = createTemplateElemInputButton(function (){
	          		parent.$.ligerDialog.open({
						title : '请求',
						width : 600,
						height : 500,
						url : 'hrp/hr/sc/hrpagedesign/hrPageDesignEventElemMainPage.do?isCheck=false',
						modal : true,
						showToggle : false,
						showMax : false,
						showMin : true,
						isResize : true,
						parentframename : window.name, //用于parent弹出层调用本页面的方法或变量
						eventElemInputTextId:"template_event",
						buttons: [{text: '确定',onclick: function (item, dialog) {dialog.frame.save();},cls:'l-dialog-btn-highlight' },
		    			          { text: '取消', onclick: function (item, dialog) { dialog.close(); }
		        	              }]
					});
              	  });
          	  elem_td.appendChild(elem_button);
          	  elem_tr.appendChild(elem_td);
          	
            }
       //反写事件数据参数1 获取inputID用于通过ID反写input的值,2左侧树形数据,3右侧树形数据
       function setElemTemplateEvent(eventElemInputTextId,leftTreeData,rightTreeData){
    	   var id = document.getElementById(eventElemInputTextId);
    	   $(id).val(leftTreeData.data.name);
    	   var elem_template_div = document.getElementById("elemTemplateDiv");
    	   var eventCheckBoxDivId = document.getElementById("eventCheckBoxDivId");
    	   if(eventCheckBoxDivId!=null){eventCheckBoxDivId.parentNode.removeChild(eventCheckBoxDivId);}
    	   var elem_div = createTemplateElemCheckBoxDiv("eventCheckBoxDivId");
    		if(rightTreeData.length > 0){
    	      $.each(rightTreeData, function(v_index, v_data){ 	
    	    	  var elemcheckbox = createTemplateElemInputCheckbox("template_event_checkbox",v_data.sql_code);
          		  elem_div.appendChild(elemcheckbox);
          		  var elemcheckboxspan = createTemplateElemCheckBoxSpan(v_data.sql_name);
          		  elem_div.appendChild(elemcheckboxspan);
        	   }); 
    		}
    		elem_template_div.appendChild(elem_div);
        }
       
       //创建table元素
       function createTemplateElemTable(){
            var elem_table;
            elem_table = document.createElement("table");
            elem_table.style.margin = "10px";
            return elem_table;
       }
      //创建tr元素
      function createTemplateElemTr(){
           var elem_tr;
           elem_tr = document.createElement("tr");
           return elem_tr;
       }
      //创建td元素
      function createTemplateElemTd(){
           var elem_td;
           elem_td = document.createElement("td");
           elem_td.style.paddingLeft = "10px";
           return elem_td;
       }
      
	  //创建span元素（span名称）
      function createTemplateElemSpan(elem_span_name){
           var elem_span;
           elem_span = document.createElement("span");
           elem_span.innerHTML=elem_span_name;
           elem_span.style.fontSize="15px";
           elem_span.style.fontWeight="bold";
           return elem_span;
       }

      //创建div元素
      function createTemplateElemDiv(){
           var elem_div;
           var elem_div = document.createElement("div");
           elem_div.className = "l-text l-text-disabled";
           elem_div.style.width = "350px";
           return elem_div;
       }
      
      //创建Input元素
      function createTemplateElemInputText(id){
           var elem_input;
           elem_input = document.createElement("input");
           elem_input.type="text";
           elem_input.className = "l-text-field";
           elem_input.id=id
           elem_input.disabled="true";
           elem_input.style.width = "350px";
           return elem_input;
       }

      //创建Button元素
      function createTemplateElemInputButton(callback){
           var elem_button;
           elem_button = document.createElement("button");
           elem_button.style.width = "120px";
           elem_button.innerHTML = '选择'; 
           elem_button.className = "l-button";
           elem_button.onclick= callback;
           return elem_button;
       }

      //创建checkboxDiv元素
      function createTemplateElemCheckBoxDiv(id){
           var elem_div;
           var elem_div = document.createElement("div");
           elem_div.style.width="800px";
           elem_div.style.margin="10px";
           if(id!=""){
        	  elem_div.id=id;
           }
           return elem_div;
       }
      
      //创建checkbox元素
      function createTemplateElemInputCheckbox(name,value){
           var elem_checkbox;
           elem_checkbox = document.createElement("input");
           elem_checkbox.type="checkbox";
           elem_checkbox.name = name
           elem_checkbox.value = value
           elem_checkbox.checked = 'checked'
           elem_checkbox.style.margin="5px";    
           return elem_checkbox;
       }

      //创建CheckBoxSpan元素
      function createTemplateElemCheckBoxSpan(name){
           var elem_span;
           var elem_span = document.createElement("span");
           elem_span.innerHTML=name;
           elem_span.style.fontSize="15px";
           return elem_span;
       }
      
      function addPageDesign(){
    	  parent.$.ligerDialog.open({
				url: 'hrp/hr/sc/hrpagedesign/hrPageDesignAddPage.do?isCheck=false',
				height: 400,
				width: 700,
				title: '页面设计器增加',
				modal: true,
				showToggle: false,
				showMax: false,
				showMin: false,
				isResize: true,
				//top: 0,
				parentframename: window.name, //用于parent弹出层调用本页面的方法或变量
				buttons: [ {
					text: '确定',
					onclick: function(item, dialog) {
						dialog.frame.savePageDesign();
						//loadTree();
						//search();
					},
					cls: 'l-dialog-btn-highlight'
				}, {
					text: '取消',
					onclick: function(item, dialog) {
						dialog.close();
					}
				} ]
			});
      }
      
      function updatPageDesign(){
    	  if(!tree.getSelected()){
    		 $.ligerDialog.error('请选择树节点');
    		 return;
    	  }
    		var parm = 'table_type_code=' + tree.getSelected().data.table_type_code
    		   +"&page_code="+(tree.getSelected().data.page_code==null?null:tree.getSelected().data.page_code);
			parent.$.ligerDialog
					.open({
						url: 'hrp/hr/sc/hrpagedesign/hrPageDesignUpdatePage.do?isCheck=false&'+ parm,
						title: '页面设计器修改',
						height: 400,
						width: 700,
						modal: true,
						showToggle: false,
						showMax: false,
						showMin: false,
						isResize: true,
						//top: 0,
						parentframename: window.name,
						//用于parent弹出层调用本页面的方法或变量
						buttons: [ {
							text: '确定',
							onclick: function(item, dialog) {
								dialog.frame.savePageDesign();
								//loadTree();
								//search();
							},
							cls: 'l-dialog-btn-highlight'
						}, {
							text: '取消',
							onclick: function(item, dialog) {
								dialog.close();
							}
						} ]
					});
    	  
      }
      function deletePageDesign(){
    	  if(!tree.getSelected()){
     		 $.ligerDialog.error('请选择树节点');
     		 return;
     	  }
	   		$.ligerDialog.confirm('确定删除?', function(yes) {
				if(yes){
					var formPara = {
							table_type_code:(tree.getSelected().data.table_type_code==null?null:tree.getSelected().data.table_type_code),
							page_code:(tree.getSelected().data.page_code==null?null:tree.getSelected().data.page_code)
					};
					ajaxJsonObjectByUrl("deleteHrPageDesign.do?isCheck=false", formPara, function(responseData) {
						if (responseData.state == "true") {
							tree.reload();
						}
					});
				}
			});
      }
      
    //导入数据
  	function importPageDesignData() {
  		$.ligerDialog.open({
  			url: 'hrPageDesignImportPage?isCheck=false',
  			height: 500,
  			width: 800, 
  			title:'导入',
  			modal:true,
  			showToggle:false,
  			showMax:false,
  			showMin: true,
  			isResize:true 
  		});
  	}
    
     function exportPageDesignData(){
    	    var table_type_code = '';
    		var page_code = '';
            var page_name = '';
            if(tree.getSelected()){
            	table_type_code = tree.getSelected().data.table_type_code;
                page_code = tree.getSelected().data.page_code;
            	page_name = tree.getSelected().data.page_name;
            }
            location.href = "exportHrPageDesign.do?isCheck=false&table_type_code="+table_type_code+"&page_code="+page_code+"&page_name="+page_name;
     }
</script>
</head>
<body style="padding: 0px; overflow: hidden;">
    <div id="pageloading" class="l-loading" style="display: none"></div>
	<div class="l-layout" id="layout" style="height: 100%;">
		<div position="left" title="设计器">
		   <div d="form1"  class="liger-form left-buttons">
				<div class="buttons">
					<input data-text="增加" data-click="addPageDesign"/>
					<input data-text="修改" data-click="updatPageDesign"/>
					<input data-text="删除" data-click="deletePageDesign"/>
					<input data-text="导入" data-click="importPageDesignData" /> 
                    <input data-text="导出" data-click="exportPageDesignData" />
				</div>
			</div>
			<div class="search-form">
				<label>快速定位</label> 
				<input type="text" id="search_input" class="text-input"> 
			</div>
			<div style="width:100%;height:calc(100% - 145px);overflow:auto;">
				<ul id="mainTree"></ul>
			</div>
			<div class="container-bar"></div>
		</div>
		<div position="center">
		    <div style="width:100%;height:100%;">
			     <div id="toptoolbar"></div>
			     <div id="elemTemplateDiv"></div>
		    </div>
		</div>
	</div>
</body>
</html>
