<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html style="overflow:hidden;">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="${path}/inc_jquery_1.9.0.jsp"/>
<link rel="stylesheet" href="<%=path %>/lib/Z-tree/css/zTreeStyle/zTreeStyle.css" type="text/css" />
<script type="text/javascript" src="<%=path %>/lib/Z-tree/js/jquery.ztree.core-3.5.js"></script>
<script type="text/javascript" src="<%=path %>/lib/Z-tree/js/jquery.ztree.excheck-3.5.js"></script>
<script type="text/javascript" src="<%=path %>/lib/Z-tree/js/jquery.ztree.exedit-3.5.js"></script>

<style type="text/css">
.tree { 
	width: auto;
	/* height: 800px; */
	position: relative;
	overflow: auto;
}
</style>
<script type="text/javascript">
    var grid;
    var tree;
    var gridManager = null;
    var userUpdateStr;
    var note;
    var setting = {
    		view: {
    			dblClickExpand: false
    		},
    		data: {
    			simpleData: { 
    				enable: true
    			}
    		},
    		callback: {
    			/*beforeClick: beforeClick,*/
    			onClick: onSelect,
    			onDblClick: onSelect
    		}
    	};
    $(function ()
    {
    	//先加载布局
    	$("#layout1").ligerLayout({ leftWidth: 285,
      		//heightDiff: -8,
      		//每展开左边树即刷新一次表格，以免结构混乱
      		onLeftToggle:function(){			
          		grid._onResize();
              },
          //每调整左边树宽度大小即刷新一次表格，以免结构混乱
          	onEndResize:function(a,b){    
    				grid._onResize();
              }	
    	});
        loadDict()//加载下拉框
    	//加载数据
    	loadHead(null);	
        loadTree();
        $('.tree').height($('.l-layout-left').height()-75);
        //alert($('.tree').height())
        $("#ass_id").ligerTextBox({width:160});
        $("#ass_code").ligerTextBox({width:160});
        $("#ass_name").ligerTextBox({width:160});
        $("#ass_spec").ligerTextBox({width:160});
        $("#ass_model").ligerTextBox({width:160});
        $("#ass_type_id").ligerTextBox({width:160});
        $("#acc_type_code").ligerTextBox({width:160});
        $("#is_depre").ligerComboBox({width:160});
        $("#ass_depre_code").ligerTextBox({width:160});
        $("#depre_years").ligerTextBox({width:160});
        $("#is_stop").ligerComboBox({width:160});
        $("#is_measure").ligerComboBox({width:160});
        $("#fac_id").ligerTextBox({width:160});
        $("#ven_id").ligerTextBox({width:160});
        $("#usage_code").ligerTextBox({width:160});
        $("#gb_code").ligerTextBox({width:160});
      var comboBox = $("#ass_id").ligerTextBox({
			width:160,
			onSuccess:function(event){
				if (event.keyCode == 38 || event.keyCode == 40 || event.keyCode == 13) //up 、down、enter
				{
				    return;
				} 
	    		comboBox.selectBox.table.find("td.l-over").removeClass("l-over");
	  			comboBox.selectBox.table.find("td[index=1]").addClass("l-over");
	  			comboBox._scrollAdjust(1);
    	    }
      });
      
      $("#ass_id").change(function(){
    	  	
    	    var ass_code = liger.get("ass_id").getValue();
    	    var treeObj = $.fn.zTree.getZTreeObj("tree");
    	    
    	    if(ass_code == -1){
    	    	 liger.get("ass_id").setText("");
    	    	 treeObj.expandAll(false);
    	    }else{
    	    	var ass_name = liger.get("ass_id").getText();
        	    var str = ass_name.replaceAll("　","");
        	    liger.get("ass_id").setText(str);
    	    }
    	    $("#ass_id").focus(function(e){
    	    	this.select();
    	    }).blur(function(){
    	    	window.getSelection().removeAllRanges();
    	    });
    	    
    		var node = treeObj.getNodesByParam("id",ass_code);
    		treeObj.getNodesByParam("ass_code",ass_code);
    		
    		treeObj.selectNode(node[0]);//选择节点
    		
    		treeObj.expandNode(node[0], true, true, true,false);//展开所选中的节点
    		
  	   });
  	 
     
    });

    
    String.prototype.replaceAll = function(s1,s2){ 
    	return this.replace(new RegExp(s1,"gm"),s2); 
    }

    
    //查询
    function  query(ass_code,pcode){
    		grid.options.parms=[];
    		grid.options.newPage=1;
    		if(pcode != null && pcode != ""){
    			 grid.options.parms.push({name:'ass_code',value:ass_code});
    		}else{
    			if(tree.getSelectedNodes().length > 0){
    				grid.options.parms.push({name:'ass_code',value:tree.getSelectedNodes()[0].id.split("@")[0]});
    			}
    			 grid.options.parms.push({name:'ass_code_like',value:$("#ass_code").val()}); 
    			 grid.options.parms.push({name:'ass_name',value:$("#ass_name").val().toUpperCase()}); 
    			 grid.options.parms.push({name:'gb_code',value:$("#gb_code").val()}); 
    			 grid.options.parms.push({name:'ass_type_id',value:liger.get("ass_type_id").getValue()}); 
    			 grid.options.parms.push({name:'acc_type_code',value:liger.get("acc_type_code").getValue()}); 
    			 grid.options.parms.push({name:'depre_years',value:$("#depre_years").val()}); 
    			 grid.options.parms.push({name:'is_depre',value:liger.get("is_depre").getValue()}); 
    			 grid.options.parms.push({name:'is_stop',value:liger.get("is_stop").getValue()}); 
    			 grid.options.parms.push({name:'is_measure',value:liger.get("is_measure").getValue()}); 
    			 grid.options.parms.push({name:'fac_id',value:liger.get("fac_id").getValue().split("@")[0]}); 
    			 grid.options.parms.push({name:'ven_id',value:liger.get("ven_id").getValue().split("@")[0]});
    			 grid.options.parms.push({name:'usage_code',value:liger.get("usage_code").getValue()});
    			 grid.options.parms.push({name:'ass_model',value:$("#ass_model").val()}); 
    			 grid.options.parms.push({name:'ass_spec',value:$("#ass_spec").val()}); 
    		}
    		grid.loadData(grid.where);
     }

    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
           columns: [ 
                     { display: '资产编码', name: 'ass_code', align: 'left',width: '80',frozen: true,
                    	 render : function(rowdata, rowindex,
  								value) {
 								
 								return "<a href=javascript:openUpdate('" + rowdata.group_id   + "|" + rowdata.hos_id   + "|" + rowdata.copy_code   + "|"  + rowdata.ass_id +"')>"+rowdata.ass_code+"</a>";

                     	 }
					 		},
                     { display: '资产名称', name: 'ass_name', align: 'left',width: '160',frozen: true
					 		},
                     { display: '资产分类', name: 'ass_type_name', align: 'left',width: '140',frozen: true
					 		},
					 { display: '规格', name: 'ass_spec', align: 'left',width: '100'
					 		},
                     { display: '型号', name: 'ass_model', align: 'left',width: '100'
					 		},
			 		 { display: '单价', name: 'price', align: 'left',width: '100'
				 		},
                     { display: '是否计量', name: 'is_measure', align: 'left',
					 			render : function(rowdata, rowindex,
										value) {
									if(rowdata.is_measure == 0){
										return "否";
									}else{
										return "是";
									}
								},width: '80'
					 			
					 		},
					 { display: '单位', name: 'ass_unit_name', align: 'left',width: '90'
						 	},
                     { display: '是否安装', name: 'is_ins', align: 'left',
					 			render : function(rowdata, rowindex,
										value) {
									if(rowdata.is_ins == 0){
										return "否";
									}else{
										return "是";
									}
								},width: '80'
					 			
					 		},
					 { display: '是否验收', name: 'is_accept', align: 'left',
					 			render : function(rowdata, rowindex,
										value) {
									if(rowdata.is_accept == 0){
										return "否";
									}else{
										return "是";
									}
								},width: '80'
					 			
					 		},
					 { display: '是否巡检', name: 'is_check', align: 'left',
					 			render : function(rowdata, rowindex,
										value) {
									if(rowdata.is_check == 0){
										return "否";
									}else{
										return "是";
									}
								},width: '80'
					 			
					 		},		
                     { display: '是否折旧', name: 'is_depre', align: 'left',
					 			render : function(rowdata, rowindex,
										value) {
									if(rowdata.is_depre == 0){
										return "否";
									}else{
										return "是";
									}
								},width: '80'
					 		},
					 { display: '是否条码管理', name: 'is_bar', align: 'left',
					 			render : function(rowdata, rowindex,
										value) {
									if(rowdata.is_bar == 0){
										return "否";
									}else{
										return "是";
									}
								},width: '100'
					 		},		
					 { display: '是否分摊', name: 'is_manage_depre', align: 'left',
					 			render : function(rowdata, rowindex,
										value) {
									if(rowdata.is_manage_depre == 0){
										return "否";
									}else{
										return "是";
									}
								},width: '80'
					 		},		
                     { display: '折旧方法', name: 'ass_depre_name', align: 'left',width: '200'
					 		},
                     { display: '折旧年限', name: 'depre_years', align: 'left',width: '100'
					 		},
					 { display: '分摊方法', name: 'manage_depr_method_name', align: 'left',width: '200'
					 		},
                     { display: '分摊年限', name: 'manage_depre_amount', align: 'left',width: '100'
					 		},		
                     { display: '是否停用', name: 'is_stop', align: 'left',
					 			render : function(rowdata, rowindex,
										value) {
									if(rowdata.is_stop == 0){
										return "否";
									}else{
										return "是";
									}
								},width: '80'
					 		},
					 { display: '品牌', name: 'ass_brand', align: 'left',width: '100'
					 		},
                     { display: '生产厂商', name: 'fac_code', align: 'left',width: '260'
					 		},
                     { display: '主要供应商', name: 'ven_code', align: 'left',width: '280'
					 		},
                     { display: '资产用途', name: 'usage_name', align: 'left',width: '100'
					 		},
					/*  { display: '条码类型', name: 'bar_type', align: 'left',width: '100'
					 		},	 */		
                     { display: '国标码', name: 'gb_code', align: 'left',width: '100'
					 		}
                     ],
                     dataAction: 'server',dataType: 'server',usePager:true,url:'queryAssDict.do',
                     width: '100%', height: '100%', checkbox: true,rownumbers:true,delayLoad :false,
                     selectRowButtonOnly:true,//heightDiff: -10,
                     toolbar: { items: [
				                     	{ text: '查询', id:'search', click: query,icon:'search' },
				                     	{ line:true },
				    					{ text: '添加', id:'add', click: itemclick, icon:'add' },
				    	                { line:true },
				    	                { text: '复制', id:'copy', click: itemclick, icon:'copy' },
				    	                { line:true },
				    	                { text: '删除', id:'delete', click: itemclick,icon:'delete' },
										{ line:true }, 
										{ text: '打印', id:'print', click: printDate,icon:'print' },
							            { line:true },
						                { text: '导入', id:'import', click: itemclick,icon:'up' },
						                { line:true },
						                { text: '校验', id:'check', click: check,icon:'lock' },
						                { line:true },
						                { text: '批量修改', id:'batchUpdate', click: batchUpdate,icon:'offset' }
				    				]},
    				onDblClickRow : function (rowdata, rowindex, value)
    				{
						openUpdate(
								rowdata.group_id   + "|" + 
								rowdata.hos_id   + "|" + 
								rowdata.copy_code   + "|" + 
								rowdata.ass_id 
							);
    				} 
                   });

        gridManager = $("#maingrid").ligerGetGridManager();
    }
    
    function batchUpdate(){
    	  var data = gridManager.getCheckedRows();
          if (data.length == 0){
          	$.ligerDialog.error('请选择行');
          }else{
              var ParamVo =[];
              $(data).each(function (){					
					ParamVo.push(
					this.group_id +"@"+ 
					this.hos_id +"@"+ 
					this.copy_code +"@"+ 
					this.ass_id 
					);
				});
              
              parent.$.ligerDialog.open({
      			title: '资产批量修改',
      			height : 400,
  				width : 1000,
      			url: 'hrp/ass/assdict/assDictUpdateBatchPage.do?isCheck=false&paramVo='+ParamVo.toString(),
      			modal: false, showToggle: false, showMax: true, showMin: false, isResize: true,
      			parentframename: window.name,  //用于parent弹出层调用本页面的方法或变量
      		}); 
          }
    }

    function itemclick(item){ 
        if(item.id)
        {
            switch (item.id)
            {
                case "add":
                	parent.$.ligerDialog.open({
            			title: '资产添加',
            			height : 400,
        				width : 1000,
            			url: 'hrp/ass/assdict/assDictAddPage.do?isCheck=false&ass_naturs='+ass_naturs+'&ass_type_code='+ass_type_code+'&ass_type_name='+ass_type_name+'&ass_type_id='+ass_type_id+'&manage_depre_amount='+manage_depre_amount,
            			modal: false, showToggle: false, showMax: true, showMin: false, isResize: true,
            			parentframename: window.name,  //用于parent弹出层调用本页面的方法或变量
            		}); 
              		return;
                case "copy":
                	var data = gridManager.getCheckedRows();
                    if (data.length != 1){
                    	$.ligerDialog.warn('只能选择单挑进行复制');
                    }else{
                    	ajaxJsonObjectByUrl("copyAssDict.do?isCheck=false",{ass_id : data[0].ass_id},function (responseData){
                    		if(responseData.state=="true"){
                    			openUpdate(responseData.update_para);
                    			query();
                    		}
                    	});
                    }
                    return;
                case "delete":
                    var data = gridManager.getCheckedRows();
                    if (data.length == 0){
                    	$.ligerDialog.error('请选择行');
                    }else{
                        var ParamVo =[];
                        $(data).each(function (){					
							ParamVo.push(
							this.group_id +"@"+ 
							this.hos_id +"@"+ 
							this.copy_code +"@"+ 
							this.ass_id 
							)
						});
                        $.ligerDialog.confirm('确定删除?', function (yes){
                        	if(yes){
                            	ajaxJsonObjectByUrl("deleteAssDict.do",{ParamVo : ParamVo.toString()},function (responseData){
                            		if(responseData.state=="true"){
                            			loadTree();
                            			query();
                            		}
                            	});
                        	}
                        }); 
                    }
                    return;
                case "import":
            		var para = {
    					 "column" : [{
    						"name" : "ass_code",
    						"display" : "资产编码",
    						"width" : "200",
    						"require" : true
    					}, {
    						"name" : 'ass_name',
    						"display" : '资产名称',
    						"width" : "200",
    						"require" : true
    					},  {
    						"name" : 'ass_type_name',
    						"display" : '资产分类',
    						"width" : "200",
    						"require" : true
    					},{
    						"name" : 'is_measure',
    						"display" : '是否计量',
    						"width" : "200"
    					},{
    						"name" : 'ass_unit_name',
    						"display" : '计量单位',
    						"width" : "200"
    					}, {
    						"name" : 'is_ins',
    						"display" : '是否安装',
    						"width" : "200"
    					}, {
    						"name" : 'is_accept',
    						"display" : '是否验收',
    						"width" : "200"
    					}, {
    						"name" : 'is_check',
    						"display" : '是否巡检',
    						"width" : "200"
    					}, {
    						"name" : 'is_depre',
    						"display" : '是否折旧',
    						"width" : "200"
    					}, {
    						"name" : 'depre_years',
    						"display" : '折旧年限',
    						"width" : "200"
    					}, {
    						"name" : 'is_manage_depre',
    						"display" : '是否分摊',
    						"width" : "200"
    					}, {
    						"name" : 'manage_depre_amount',
    						"display" : '分摊年限',
    						"width" : "200"
    					},{
    						"name" : "is_stop",
    						"display" : "是否停用",
    						"width" : "200"
    					},{
    						"name" : "ass_spec",
    						"display" : "规格",
    						"width" : "200"
    					},{
    						"name" : "ass_model",
    						"display" : "型号",
    						"width" : "200"
    					},{
    						"name" : "ass_brand",
    						"display" : "品牌",
    						"width" : "200"
    					},{
    						"name" : "fac_name",
    						"display" : "生产厂商",
    						"width" : "200"
    					},{
    						"name" : "ven_name",
    						"display" : "供应商",
    						"width" : "200"
    					},{
    						"name" : "ass_purpose",
    						"display" : "资产用途",
    						"width" : "200"
    					},{
    						"name" : "gb_code",
    						"display" : "国标码",
    						"width" : "200"
    					},{
    						"name" : "note",
    						"display" : "备注",
    						"width" : "200"
    					}
    					] 
    				
    				};
    				importSpreadView("hrp/ass/assdict/assDictImportPage.do?isCheck=false",para);
             
                case "export":
                	return;
                case "downTemplate":
                	location.href = "downTemplate.do?isCheck=false";
                	return;
               }   
        }
        
    }
    function openUpdate(obj){
		var vo = obj.split("|");
		if("null"==vo[3]){
			return false;
			
		}
		var parm ="&group_id="+ 
		   vo[0]   +"&hos_id="+ 
		   vo[1]   +"& copy_code="+ 
		   vo[2]   +"&ass_id="+ 
		   vo[3]; 
    	
		parent.$.ligerDialog.open({
			title: '资产修改',
			height : 600,
			width : 1000,
			url: 'hrp/ass/assdict/assDictUpdatePage.do?isCheck=false&'+parm,
			modal: false, showToggle: false, showMax: true, showMin: false, isResize: true,
			parentframename: window.name,  //用于parent弹出层调用本页面的方法或变量
		}); 
    }
    function loadDict(){
    	
    	autocomplete("#ass_id", "../queryAssNoDictTree.do?isCheck=false", "id",
				"text", true, false, null,null,null,"260");
    	autocomplete("#ass_type_id","../queryAssTypeDictIsLast.do?isCheck=false","id","text",true,true,null,null,null,"260");
    	autocomplete("#fac_id","../queryHosFacDictNo.do?isCheck=false","id","text",true,true,null,null,null,"400");
    	autocomplete("#ven_id","../queryHosSupDict.do?isCheck=false","id","text",true,true,null,null,null,"400");
    	autocomplete("#ass_depre_code", "../queryAssDepreMethodDict.do?isCheck=false", "id", "text", true, true,null,null,null,"280");
		autocomplete("#usage_code", "../queryAssUsageDict.do?isCheck=false", "id", "text", true, true,null,null,null,"200");
		autocomplete("#ass_unit", "../queryHosUnitDict.do?isCheck=false", "id", "text", true,true,null,null,null,"200");
		autocomplete("#acc_type_code", "../queryMatFinaTypeIsLast.do?isCheck=false", "id", "text", true,true,null,null,null,"280");
    	
		$('#is_depre,#is_stop,#is_measure').ligerComboBox({
			data:[{id:1,text:'是'},{id:0,text:'否'}],
			valueField: 'id',
            textField: 'text',
			cancelable:true
		})
    }  
    //校验资产字典的准确性，检查各个字段是否符合要求 不符合要求的给默认值 其他情况给予提示
    function check(){
    	
    	ajaxJsonObjectByUrl("checkAssDict.do?isCheck=false",{},function (responseData){
    			
    			var res=responseData;
    			console.log(responseData);  
		   		 if(responseData.Rows!= null&&responseData.Rows!= ""){
		   			if (!$('#dialogcontent').length) { //  需要重新生成节点 之前的节点已随着弹窗销毁

						$('div[position="center"]').append('<div id="dialogcontent"></div>');
					};
					var grid2 = $('#dialogcontent').ligerGrid({
						columns: [{
								display: '资产编码',
								name: 'ass_code',
								width: '10%'
							},
							{
								display: '资产名称',
								name: 'ass_name',
								width: '20%'
							},
							{
								display: '错误信息',
								name: 'ass_error'
							}
						],
						width: '100%',
						pkName: 'ass_code',
						usePager:false,
						height: '100%'
					});
					/* var $clone = $('#dialogcontent').clone(true); */
					parent.$.ligerDialog.open({
						title: '资产字典信息检测',
						height: 550,
						width: 950,
						target: $('#dialogcontent'),
						modal: true,
						// showMax: true,
						showToggle: true,
						showMin: false,
						isResize: true,
						slide: false
						
					});
					grid2.loadData(responseData);
		   		}else{
		   			$.ligerDialog.success('校验成功！');
		   		}
    			
		   		//query();
    	});
    }
  
	function printDate(){
	    	
	    	if(grid.getData().length==0){
	    		
				$.ligerDialog.error("请先查询数据！");
				
				return;
			}
	    	
	    	var selPara={};
	    	
	    	$.each(grid.options.parms,function(i,obj){
	    		
	    		selPara[obj.name]=obj.value;
	    		
	    	});
	   		
			var dates = getCurrentDate();
	    	
	    	var cur_date = dates.split(";")[2];
	    	//跨所有列:计算列数
	    	var colspan_num = grid.getColumns(1).length-1;
	   		
	    	var printPara={
	       			title:'资产字典',
	       			head:[
	    				{"cell":0,"value":"单位: ${hos_name}","colspan":colspan_num,"br":true}
	       			],
	       			foot:[
	    				{"cell":0,"value":"主管:","colspan":2,"br":false} ,
						{"cell":2,"value":"复核人:","colspan":colspan_num-2,"br":true},
						{"cell":0,"value":"制单人： ${user_name}","colspan":2,"br":false},
						{"cell":2,"value":"打印日期: " + cur_date,"colspan":colspan_num-2,"br":true}
	       			],
	       			columns:grid.getColumns(1),
	       			headCount:2,//列头行数
	       			autoFile:true,
	       			type:3
	       	};
	   		ajaxJsonObjectByUrl("queryAssDict.do?isCheck=false", selPara, function (responseData) {
	   			printGridView(responseData,printPara);
			});

	   		
	    }
	 
	var ass_naturs = "";
	var ass_type_code = "";
	var ass_type_name = "";
	var ass_type_id = "";
	var manage_depre_amount = "";
	/* 设置树形菜单 */
	function onSelect(event, treeId, treeNode) {
		query(treeNode.id.split("@")[0],1);
		ass_naturs = treeNode.id.split("@")[1];
		
		if(treeNode.is_last == 1){
			
			ass_type_code = treeNode.id.split("@")[0];
			ass_type_name = treeNode.name.split(" ")[1];
			ass_type_id = treeNode.ass_type_id;
			manage_depre_amount = treeNode.manage_depre_amount;
		} else {
			 ass_type_code = "";
			 ass_type_name = "";
			 ass_type_id = "";
			 manage_depre_amount = "";
		}
		
		 console.log(treeNode);
		 /*console.log(ass_type_name); */
	}
	
	
	function loadTree(ass_code){
		ajaxJsonObjectByUrl("queryAssNoDictTree.do?isCheck=false",{ass_code:ass_code},function (responseData){
		      tree=$.fn.zTree.init($("#tree"), setting, responseData.Rows);
		      //allNodes = tree.getNodes();
		},false);
	}
	
    </script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>
	<div id="layout1">
		<div position="left" title="资产">
			<div>
				<table style="width:100%;height: 50px;text-align: center;">
					<tr>
						<td>&nbsp;快速定位：</td>
						<td><input type="text" id="ass_id" name="ass_id"/></td>
					</tr>
				</table>
			</div>
			<div class="tree">
				<ul class="ztree" id="tree" ></ul>
			</div>
		</div>
		<div position="center">
			<table cellpadding="0" cellspacing="0" class="l-table-edit" width="100%">
		        <tr>
		            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">资产编码：</td>
		            <td align="left" class="l-table-edit-td"><input name="ass_code" type="text" id="ass_code"  /></td>
		            <td align="left"></td>
		            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">资产名称：</td>
		            <td align="left" class="l-table-edit-td"><input name="ass_name" type="text" id="ass_name"  /></td>
		            <td align="left"></td>
		            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">国&nbsp;&nbsp;标&nbsp;&nbsp;码：</td>
		            <td align="left" class="l-table-edit-td"><input name="gb_code" type="text" id="gb_code"  /></td>
		            <td align="left"></td>
		        </tr> 
		        <tr>
		            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">资产分类：</td>
		            <td align="left" class="l-table-edit-td"><input name="ass_type_id" type="text" id="ass_type_id"  /></td>
		            <td align="left"></td>
		            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">财务分类：</td>
		            <td align="left" class="l-table-edit-td"><input name="acc_type_code" type="text" id="acc_type_code"  /></td>
		            <td align="left"></td>
		            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">折旧年限：</td>
		            <td align="left" class="l-table-edit-td"><input name="depre_years" type="text" id="depre_years"  /></td>
		            <td align="left"></td>
		        </tr> 
		        <tr>
		            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">是否折旧：</td>
		            <td align="left" class="l-table-edit-td">
		            	<input name="is_depre" type="text" id="is_depre"  />
						<!-- <select id="is_depre" name="is_depre">
								<option value="">全部</option>
		                	    <option value="1">是</option>
		                		<option value="0">否</option>
		                </select> -->
					</td>
		            <td align="left"></td>
		            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">是否停用：</td>
		            <td align="left" class="l-table-edit-td">
		            	<input name="is_stop" type="text" id="is_stop"  />
		            	<!-- <select id="is_stop" name="is_stop">
		            			<option value="">全部</option>
		                		<option value="0">否</option>
		                		<option value="1">是</option>
		                </select> -->
		            </td>
		            <td align="left"></td>
		            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">生产厂商：</td>
		            <td align="left" class="l-table-edit-td"><input name="fac_id" type="text" id="fac_id"  /></td>
		            <td align="left"></td>
		        </tr> 
		        <tr>
		            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">供&nbsp;&nbsp;应&nbsp;&nbsp;商：</td>
		            <td align="left" class="l-table-edit-td"><input name="ven_id" type="text" id="ven_id"  /></td>
		            <td align="left"></td>
		            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">资产用途：</td>
		            <td align="left" class="l-table-edit-td"><input name="usage_code" type="text" id="usage_code"  /></td>
		            <td align="left"></td>
		            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">规格：</td>
		            <td align="left" class="l-table-edit-td"><input name="ass_spec" type="text" id="ass_spec"  /></td>
		        </tr> 
		        <tr>
		        	<td align="right" class="l-table-edit-td"  style="padding-left:20px;">型号：</td>
		            <td align="left" class="l-table-edit-td"><input name="ass_model" type="text" id="ass_model"  /></td>
		            <td align="left"></td>
		            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">是否计量：</td>
		            <td align="left" class="l-table-edit-td">
		            	<input name="is_measure" type="text" id="is_measure"  />
					</td>
		        </tr>
		    </table>
			<div id="maingrid"></div>
			<div id='dialogcontent'>
						<!-- <div id='dialogGrid'></div> -->
					</div>
		</div>
	</div>
</body>
</html>
