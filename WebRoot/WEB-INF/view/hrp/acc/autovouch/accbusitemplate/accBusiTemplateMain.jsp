<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html style="overflow:hidden;">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="${path}/inc_jquery_1.9.0.jsp" /> 
<script src="<%=path%>/lib/stringbuffer.js"></script>
<!-- 弹出层组件 -->
<script src="<%=path%>/lib/et_components/et_plugins/etDialog.min.js"></script>

<script type="text/javascript">

    var grid;
    
    var gridManager = null;
    
    var bottomGridAcc;
    
    var bottomGridManagerAcc = null;
    
	var bottomGridBudg;
    
    var bottomGridManagerBudg = null;

    var userUpdateStr;
    
    var tree;
    
    var treeManager =null;
    
    var busiRelaDataAcc = null;
    
    var busiRelaDataBudg = null;
    
    var bottomDataAcc = null;
    
    var bottomDataBudg = null;
    
    var l1 = null;
    
    var l2 = null;
    
	var saveFlag = true;//执行新增的时候为true 修改的时候为false
    
    $(function ()
    {
        loadDict();//加载下拉框
    	
    	loadHead(null);	
        
    	loadBottomHead(null);
    	
    	loadTree();

    	l1 = $("#layout1").ligerLayout({leftWidth: 200,allowLeftResize: true,
			onRightToggle : function() {
				grid._onResize();
				bottomGridAcc._onResize();
				bottomGridBudg._onResize();
			},
			//每调整左边树宽度大小即刷新一次表格，以免结构混乱
			onEndResize : function(a, b) {
				grid._onResize();
				bottomGridAcc._onResize();
				bottomGridBudg._onResize();
			},
			
			onLeftToggle: function(a, b) {
				grid._onResize();
				bottomGridAcc._onResize();
				bottomGridBudg._onResize();
				l2._onResize();
			}
		});
    	
    	l2 = $("#layout2").ligerLayout({
				topHeight: 60, 
				leftWidth: "50%",
				InWindow: true, 
				height: '100%', 
				rightWidth: "50%", 
	    		onRightToggle : function(a, b) {
					grid._onResize();
					bottomGridAcc._onResize();
					bottomGridBudg._onResize();
					//l2._onResize();
				},
				//每调整左边树宽度大小即刷新一次表格，以免结构混乱
				onEndResize : function(a, b) {
					grid._onResize();
					bottomGridAcc._onResize();
					bottomGridBudg._onResize();
				},
			
				onLeftToggle: function(a, b) {
					grid._onResize();
					bottomGridAcc._onResize();
					bottomGridBudg._onResize();
					//l2._onResize();
				}
    	});
    	
    	$("#tree").css("height", $(window).height()-55);

    });
    //查询
    function  query(){
    	
		grid.options.parms = [];grid.options.newPage = 1;
		
		grid.options.parms.push({name : 'mod_code',value : liger.get("mod_code").getValue()});
	
		grid.loadData(grid.where);//加载查询条件
		
     }
    
    function loadHead(){
		grid = $("#maingrid").ligerGrid({
			columns : [
					{display : '模板编码',name : 'template_code',width: 180,align : 'left'}, 
					{display : '模板名称',name : 'template_name',width: 180,align : 'left'}, 
					{display : '凭证类型',name : 'vouch_type_name',width: 180,align : 'left'},
					{display : '摘要',name : 'summary',align : 'left',width: 230,align : 'left'}
					],

			dataAction : 'server',dataType : 'server',usePager : false,url : 'queryAccBusiTemplate.do',delayLoad:true,
			width : '100%',height : '45%',checkbox : true,selectRowButtonOnly : true,alternatingRow: false,//heightDiff: -10,
			toolbar : {
				items : [
				         {text : '添加模板',id : 'search',click : addTemplate,icon : 'add'},
				         {line : true},
				         {text : '保存模板',id : 'search',click : saveAccBusiTemplate,icon : 'save'},
				         {line : true},
				         {text : '删除模板',id : 'search',click : delTemplate,icon : 'delete'},
				         {line : true}
				         ]
			},
			onDblClickRow : function (rowdata, rowindex, value){

				var formPara = {
						
						group_id : rowdata.group_id,
						
						hos_id : rowdata.hos_id,
						
						copy_code : rowdata.copy_code,
						
						mod_code : rowdata.mod_code,
						
						acc_year : rowdata.acc_year,
						
						busi_type_code : rowdata.busi_type_code,
						
						template_code : rowdata.template_code

				};
				
				ajaxJsonObjectByUrl("queryAccBusiTemplateDetail.do?isCheck=false", formPara, function(responseData) {
					bottomDataAcc = responseData.accData;
					bottomDataBudg = responseData.budgData;

					$("#template_code").val(responseData.template_code);$("#template_code").ligerTextBox({disabled: true});
					
					$("#template_name").val(responseData.template_name);
					
					if(responseData.summary !='null'){
						
						$("#summary").val(responseData.summary);
						
					}else{
						
						$("#summary").val('');
						
					}

					liger.get("vouch_type_code").setValue(responseData.vouch_type_code);
					
					liger.get("vouch_type_code").setText(responseData.vouch_type_name);
					
					if(responseData.is_detail_summary  == 1){
						
						liger.get("is_detail_summary").setValue(true);
						
					}else{
					
						liger.get("is_detail_summary").setValue(false);
					}
					
					saveFlag = false;

					bottomGridAcc.loadData(bottomDataAcc);
					
					bottomGridBudg.loadData(bottomDataBudg);

				});

			} 
		});

		gridManager = $("#maingrid").ligerGetGridManager();
		
		$("#maingridgrid").height(184);
    }
 
    var directionData = { Rows :[{ direction: 0, text: '借' }, { direction: 1, text: '贷'}]};
	
    function loadBottomHead(){
		
    	bottomGridAcc = $("#bottomGridAcc").ligerGrid({
			columns : [
					{display : '分录',name : 'vouch_row',width: 60,align : 'center',editor: { type: 'int'}}, 
					{display : '摘要',name : 'summary',width: 200,align : 'left',editor: { type: 'text' }},
					{display : '分录内容',name : 'meta_code',align : 'left',width: 100,align : 'left',
						editor: {type: 'select', data: busiRelaDataAcc, valueField: 'meta_code',textField:'meta_name',keySupport:true,selectBoxWidth:250},
						render: function (item) {
							if(!busiRelaDataAcc)return;
	                        for (var i = 0; i < busiRelaDataAcc.length; i++){
	                        	
	                            if (busiRelaDataAcc[i]['meta_code'] == item.meta_code){
	                            	
	                            	if(busiRelaDataAcc[i]['is_auto'] ==1 ){

	                            		item.setTemplate = "<a onClick=openAccBusiRelaForAccSubjAutoPage('"+busiRelaDataAcc[i]['meta_code']+"','"+liger.get("mod_code").getValue()+"','"+busiRelaDataAcc[i]['type_table']+"','"+busiRelaDataAcc[i]['is_store']+"','"+busiRelaDataAcc[i]['is_resource']+"','"+i+"') >自动</a>";
	                            		
	                            	}else{
	                            		
	                            		item.setTemplate = "<a onClick=openAccBusiRelaForAccSubjSetPage('"+busiRelaDataAcc[i]['meta_code']+"','"+liger.get("mod_code").getValue()+"','','') >设置</a>";
	                            		
	                            	}

	                            	return busiRelaDataAcc[i]['meta_name'];
	                            	
	                            }
	                            
	                        }
	                        
	                        return item.meta_name;
	                    }

					},
					{display : '方向',name : 'direction',align : 'left',width: 60,align : 'left',
					 		editor : {
								type : 'select',
								valueField : 'direction',
								textField : 'text',
								data : directionData.Rows,
								keySupport : true,
								autocomplete : true,
								onSuccess : function(data) {
									if (initvalue != undefined
											&& initvalue != "") {
										this.setValue(initvalue);
										initvalue = "";
									}
								}
							},
		                    render: function (item)
		                    {
		                    	 if (item.direction == 0) {
		                    		 return '借';
		                    	 }else if(item.direction == 1){
		                    		 return '贷';
		                    	 }else{
		                    		 return '';
		                    	 }
		                    		 
			                       
		                    }
					},
					{display : '设置',name : 'setTemplate',align : 'left',width: 80,align : 'left'},
					{display : '编码',name : 'sort_code',align : 'left',width: 60,align : 'left',hide:true
					}
					
					],
			usePager : false,width : '100%',height : "100%",checkbox : true,enabledEdit : true,
			data:bottomDataAcc,selectRowButtonOnly : true,alternatingRow: false,//heightDiff: -10,
			isAddRow:false,
			toolbar : {
				items : [
				         {text : '添加行',id : 'search',click : addRowAcc,icon : 'add'},
				         {line : true},
				         //{text : '模板保存',id : 'search',click : saveAccBusiTemplateAcc,icon : 'save'},
				         //{line : true},
				         {text : '删除明细',id : 'search',click : delTemplateDetailAcc,icon : 'delete'}
				         ]
			}
		});

    	bottomGridManagerAcc = $("#bottomGridAcc").ligerGetGridManager();
    	
    	
    	
    	bottomGridBudg = $("#bottomGridBudg").ligerGrid({
			columns : [
					{display : '分录',name : 'vouch_row',width: 60,align : 'center',editor: { type: 'int'}}, 
					{display : '摘要',name : 'summary',width: 200,align : 'left',editor: { type: 'text' }},
					{display : '分录内容',name : 'meta_code',align : 'left',width: 100,align : 'left',
						editor: {type: 'select', data: busiRelaDataBudg, valueField: 'meta_code',textField:'meta_name',keySupport:true,selectBoxWidth:250},
						render: function (item) {
							if(!busiRelaDataBudg)return;
	                        for (var i = 0; i < busiRelaDataBudg.length; i++){
	                        	
	                            if (busiRelaDataBudg[i]['meta_code'] == item.meta_code){
	                            	
	                            	if(busiRelaDataBudg[i]['is_auto'] ==1 ){

	                            		item.setTemplate = "<a onClick=openBudgBusiRelaForAccSubjAutoPage('"+busiRelaDataBudg[i]['meta_code']+"','"+liger.get("mod_code").getValue()+"','"+busiRelaDataBudg[i]['type_table']+"') >自动</a>";
	                            		
	                            	}else{
	                            		
	                            		item.setTemplate = "<a onClick=openBudgBusiRelaForAccSubjSetPage('"+busiRelaDataBudg[i]['meta_code']+"','"+liger.get("mod_code").getValue()+"','','') >设置</a>";
	                            		
	                            	}

	                            	return busiRelaDataBudg[i]['meta_name'];
	                            	
	                            }
	                            
	                        }
	                        
	                        return item.meta_name;
	                    }

					},
					{display : '方向',name : 'direction',align : 'left',width: 60,align : 'left',
					 		editor : {
								type : 'select',
								valueField : 'direction',
								textField : 'text',
								data : directionData.Rows,
								keySupport : true,
								autocomplete : true,
								onSuccess : function(data) {
									if (initvalue != undefined
											&& initvalue != "") {
										this.setValue(initvalue);
										initvalue = "";
									}
								}
							},
		                    render: function (item)
		                    {
		                    	 if (item.direction == 0) {
		                    		 return '借';
		                    	 }else if(item.direction == 1){
		                    		 return '贷';
		                    	 }else{
		                    		 return '';
		                    	 }
		                    		 
			                       
		                    }
					},
					{display : '取值公式',name : 'cal',align : 'left',width: 170,align : 'left',editor: { type: 'text' },hide:true
					},
					{display : '设置',name : 'setTemplate',align : 'left',width: 80,align : 'left'},
					],
			usePager : false,width : '100%',height :"100%",checkbox : true,enabledEdit : true,
			data:bottomDataBudg,selectRowButtonOnly : true,alternatingRow: false,isAddRow:false,
			//heightDiff: -10,
			toolbar : {
				items : [
				         {text : '添加行',id : 'search',click : addRowBudg,icon : 'add'},
				         {line : true},
				         //{text : '模板保存',id : 'search',click : saveAccBusiTemplateBudg,icon : 'save'},
				         //{line : true},
				         {text : '删除明细',id : 'search',click : delTemplateDetailBudg,icon : 'delete'}
				         ]
			}
		});

    	bottomGridManagerBudg = $("#bottomGridBudg").ligerGetGridManager();
    }
    
    function incChar(char0){
		if(!char0){
			return "A";
		}
		
		char0 = char0.toUpperCase(); //转大写
		var char1 = 0, char2 = 0, newChar = "";
		
		char1 = char0.substr(0, 2).charCodeAt();
		
		if(char0.length > 1){
			char2 = char0.substr(1, 2).charCodeAt();
			if(char2 == 90){
				char2 = 65;
				char1 += 1;
			}else{
				char2 += 1;
			}
			newChar = String.fromCharCode(char1) + String.fromCharCode(char2);
		}else{
			if(char1 == 90){
				newChar = "AA";
			}else{
				newChar = String.fromCharCode(char1 + 1);
			}
		}
		
		return newChar;
	}
    var accSortCode = "", acc_first = true;
    
   
    
    
    function openAccBusiRelaForAccSubjSetPage(obj,obj1,obj2,obj3){
    	
		var parm = "meta_code="+obj+"&mod_code="+obj1+"&type_id="+obj2+"&sub_type_id="+obj3+"&acc_year="+$("#acc_year").val();
		
    	parent.$.ligerDialog.open({ url : 'hrp/acc/autovouch/accbusitemplate/queryAccBusiRelaForAccSubjSetPage.do?isCheck=false&' + parm,data:{}, height: $(window).height(),width: $(window).width()-100, title:'选择科目',modal:true,showToggle:false,showMax:false,showMin: false,isResize:true});
    
    }
    
    function openAccBusiRelaForAccSubjAutoPage(obj,obj1,obj2){
    	
		var parm = "meta_code="+obj+"&mod_code="+obj1+"&type_table="+obj2+"&acc_year="+$("#acc_year").val();
		
		parent.$.ligerDialog.open({ url : 'hrp/acc/autovouch/accbusitemplate/queryAccBusiRelaForAccSubjAutoPage.do?isCheck=false&' + parm,data:{}, height: $(window).height(),width: $(window).width()-100, title:'类别设置',modal:true,showToggle:false,showMax:false,showMin: false,isResize:true });
    
    }
    
    
 function openBudgBusiRelaForAccSubjSetPage(obj,obj1,obj2,obj3){
    	
		var parm = "meta_code="+obj+"&mod_code="+obj1+"&type_id="+obj2+"&sub_type_id="+obj3+"&acc_year="+$("#acc_year").val();
		
    	parent.$.ligerDialog.open({ url : 'hrp/acc/autovouch/accbusitemplate/queryBudgBusiRelaForAccSubjSetPage.do?isCheck=false&' + parm,data:{}, height: $(window).height(),width: $(window).width()-100, title:'选择科目',modal:true,showToggle:false,showMax:false,showMin: false,isResize:true});
    
    }

	var accBusiRela;  //子页面使用勿动
	function openAccBusiRelaForAccSubjAutoPage(obj,obj1,obj2,obj3,obj4, obj5){
		var parm = "meta_code="+obj+"&mod_code="+obj1+"&type_table="+obj2+"&acc_year="+$("#acc_year").val()+"&is_store="+obj3+"&is_resource="+obj4+"&where_sql="+busiRelaDataAcc[obj5]['where_sql']+"&kind_code="+busiRelaDataAcc[obj5]['kind_code'];
		if(obj3 == 1){
	 		accBusiRela = {
	 			meta_code: obj, 
	 			mod_code: obj1, 
	 			table_id: obj2, 
	 			acc_year: $("#acc_year").val(), 
	 			is_store: obj3,
	 			is_resource: obj4, 
	 			where_sql: busiRelaDataAcc[obj5]['where_sql'], 
	 			kind_code: busiRelaDataAcc[obj5]['kind_code']
	 		}
			parent.$.etDialog.open({
				url: 'hrp/acc/autovouch/accbusitemplate/accBusiRelaForAccStoreSubjAutoPage.do?isCheck=false&' + parm,
				frameName : window.name,
				width: $(parent.window).width(),
				height: $(parent.window).height(),
				title: '类别库房设置',
				showMax: true,
				showMin: true,
				shade: 0,
				maxmin: false,
				restore: function(layero){
				  	//得到iframe页的窗口对象，执行iframe页的方法：iframeWin.method();
					var iframeWin = parent.window[layero.find('iframe')[0]['name']];
				  	setTimeout(function() {
			  			iframeWin.$grid.refreshView();
				  	}, 100);
			  	}
			});	
	 	}else{
			parent.$.ligerDialog.open({ url : 'hrp/acc/autovouch/accbusitemplate/queryAccBusiRelaForAccSubjAutoPage.do?isCheck=false&' + parm,data:{}, height: $(window).height(),width: $(window).width()-100, title:'类别设置',modal:true,showToggle:false,showMax:false,showMin: false,isResize:true });
	 	}
 }
    
    function openBudgBusiRelaForAccSubjAutoPage(obj,obj1,obj2){
    	
		var parm = "meta_code="+obj+"&mod_code="+obj1+"&type_table="+obj2+"&acc_year="+$("#acc_year").val();
		
		parent.$.ligerDialog.open({ url : 'hrp/acc/autovouch/accbusitemplate/queryBudgBusiRelaForAccSubjAutoPage.do?isCheck=false&' + parm,data:{}, height: $(window).height(),width: $(window).width()-100, title:'类别设置',modal:true,showToggle:false,showMax:false,showMin: false,isResize:true });
    
    }
	
    

	function addRowAcc(){
		if(acc_first && bottomGridAcc.getData() && bottomGridAcc.getData().length > 0){
			accSortCode = bottomGridAcc.getData()[bottomGridAcc.getData().length - 1].sort_code;
			acc_first = false;
		}
		
		accSortCode = incChar(accSortCode);

		var is_detail_summary = liger.get("is_detail_summary").getValue();
		
		var summary="";
		
		if(is_detail_summary == true){
			
			summary = $("#summary").val();
			
		}
		var meta_code;
		
		if(busiRelaDataAcc == null || busiRelaDataAcc == ""){
			meta_code = "";
		}else{
			meta_code = busiRelaDataAcc[0].meta_code;
		}

		bottomGridAcc.addRow({"busi_type_code":"","direction":0,"meta_code":meta_code,"summary":summary,"sort_code":accSortCode});
		
	}
	
	function addRowBudg(){
		
		var is_detail_summary = liger.get("is_detail_summary").getValue();
		
		var summary="";
		
		if(is_detail_summary == true){
			
			summary = $("#summary").val();
			
		}
		
		var meta_code;
		
		if(busiRelaDataBudg == null || busiRelaDataBudg == ""){
			meta_code = "";
		}else{
			meta_code = busiRelaDataBudg[0].meta_code;
		}

		bottomGridBudg.addRow({"busi_type_code":"","direction":0,"meta_code":meta_code,"summary":summary});
		
	}
	
	function addTemplate(){
		accSortCode = incChar(accSortCode);
		bottomDataAcc ={"Rows":[{"acc_year":"","busi_type_code":"","direction":0,"meta_code":"","mod_code":"","summary":"","vouch_row":1,"sort_code":accSortCode}],"Total":1};
		
		bottomGridAcc.loadData(bottomDataAcc);
		
		bottomDataBudg ={"Rows":[{"acc_year":"","busi_type_code":"","direction":0,"meta_code":"","mod_code":"","summary":"","vouch_row":1,"cal":""}],"Total":1};
		
		bottomGridBudg.loadData(bottomDataBudg);
		
		$("#template_code").val('');
		$("#template_code").ligerTextBox({disabled: false});
		
		$("#template_name").val('');
		
		$("#summary").val('');
		
		liger.get("is_detail_summary").setValue(true);
		
    	saveFlag = true;
		
	}
	
	function delTemplate(){

		var data = gridManager.getCheckedRows();
        if (data.length == 0){
        	$.ligerDialog.error('请选择行');
        }else{
            var ParamVo =[];
            $(data).each(function (){					
													ParamVo.push(
													this.group_id   +"@"+ 
													this.hos_id   +"@"+ 
													this.copy_code   +"@"+ 
													this.acc_year   +"@"+ 
													this.mod_code   +"@"+ 
													this.busi_type_code   +"@"+ 
													this.template_code
													) });
            $.ligerDialog.confirm('确定删除?', function (yes){
            	if(yes){
                	ajaxJsonObjectByUrl("delAccBusiTemplate.do",{ParamVo : ParamVo.toString()},function (responseData){
                		if(responseData.state=="true"){
                			gridManager.deleteSelectedRow();
                		}
                	});
            	}
            }); 
        }
	}
	
	function delTemplateDetailAcc(){

		bottomGridAcc.deleteSelectedRow2();
		
	}
	
	function delTemplateDetailBudg(){

		bottomGridBudg.deleteSelectedRow2();
		
	}

    function loadDict(){
    	
    	$("#acc_year").val($("#curDate").val().substring(0,4));
      
      	$("#mod_code").ligerComboBox({
          	url: '../../../sys/querySysBusiMod.do?isCheck=false',
          	valueField: 'id',
           	textField: 'text', 
           	selectBoxWidth: 180,
          	autocomplete: true,
          	width: 180,
          	onSuccess : function (data){
          		
          		this.setValue(data[0].id);

           },
    		onSelected: function (selectValue){
    			
    			var para = {mod_code:selectValue};
    			
    			ajaxJsonObjectByUrl("queryAccBusiTypeTree.do?isCheck=false", para,
    			
    				function(responseData) {
    				
    						if (responseData != null) {

    							tree.set('data', responseData.Rows);
    							
    							$("#tree").width("auto");

    						}
    						
					});
    			
    			grid.options.parms = [];grid.options.newPage = 1;
    			
    			grid.options.parms.push({name : 'mod_code',value : 'AAAAAAAAAAAA'});
    		
    			grid.loadData(grid.where);//加载查询条件
    			
    			bottomDataAcc ={"Rows":[],"Total":0};
    			
    			bottomGridAcc.loadData(bottomDataAcc);
    			
    			$("#template_code").val('');$("#template_code").ligerTextBox({disabled: false});
    			
    			$("#template_name").val('');
    			
    			$("#summary").val('');
    			
    			liger.get("is_detail_summary").setValue(true);

    	    	saveFlag = true;
    			
			}

    	 });
    	
    	autocomplete("#vouch_type_code","../../queryVouchType.do?isCheck=false","id","text",true,true,"",true,false,'180');
    	
    	$("#template_code").ligerTextBox({width:180});
    	
    	$("#template_name").ligerTextBox({width:180});
    	
    	$("#summary").ligerTextBox({width:180});
    	
    	$("#summary").ligerTextBox({width:180});
    	
    	var is_detail_summary = $("#is_detail_summary").ligerCheckBox();
    	
    	is_detail_summary.setValue(true);

	}  
    var accSortCodes = {};
	var errorMsg = "";
    function checkCal(index_x, cal) {
		var flag = true;
	    try{
			var ret=cal.replace(/[a-zA-Z]+/g, function () {
				//调用方法时内部会产生 this 和 arguments
				if(!accSortCodes[arguments[0]]){
					errorMsg += "预算会计第" + index_x + "行取值公式中编号" + arguments[0] + "不存在<br/>";
					flag = false;
				}
				return 1;
			});
	    	eval("cal="+ret);
	    	
	    	return flag;
	    }catch(err){
	    	errorMsg += "预算会计第" + index_x + "行取值公式不合法<br/>";
	    	return false;
	    }
	}
    
    
    function saveAccBusiTemplate(){
    	if($("#acc_year").val()==""){
			$.ligerDialog.warn("请选择会计年度");
			return false;
    	}
    	var note = treeManager.getSelected(); 

		if(note == null){
			
			$.ligerDialog.warn("请选择业务类型");
			
			return false;
		}
		
		var is_detail_summary = liger.get("is_detail_summary").getValue();

		var template_code = $("#template_code").val();
		
		if(template_code == null || template_code == ""){
			
			$.ligerDialog.warn("请输入模板编码");
			
			return false;
		}
		
		var template_name = $("#template_name").val();
		
		if(template_name == null || template_name == ""){
			
			$.ligerDialog.warn("请输入模板名称");
			
			return false;
		}
		
		var vouch_type_code = liger.get("vouch_type_code").getValue();
		
		if(vouch_type_code == null || vouch_type_code == ""){
			
			$.ligerDialog.warn("请输入凭证类型");
			
			return false;
		}
		
		if(is_detail_summary == true){
			
			is_detail_summary =1;
			
		}else{
			
			var summary = $("#summary").val();
			
			if(summary == null || summary == ""){
				
				$.ligerDialog.warn("请输入摘 要");
				
				return false;
			}
			
			is_detail_summary =0;
			
		}

		
		var acc_busi_template_detail_acc = bottomGridAcc.getData();
		
		var validate_detail_buf_acc = new StringBuffer();
		
		if(acc_busi_template_detail_acc.length > 0){
			
			$.each(acc_busi_template_detail_acc, function(d_index, d_content){
				
				var indexNo = d_index+1; 
				
				
				if (isnull(d_content.vouch_row)) {
					bottomGridAcc.deleteRow(d_index);
					return;
				}
				
				if(typeof(d_content.vouch_row) == "undefined"){
      				
					validate_detail_buf_acc.append("财务模板明细：第["+indexNo+"]行 分录行号为空 请输入\n");
  				
  		  		}
				
				if(typeof(d_content.summary) == "undefined" || d_content.summary == ""){
      				
					validate_detail_buf_acc.append("财务模板明细：第["+indexNo+"]行 摘要为空 请输入\n");
  				
  		  		}
				
				if(typeof(d_content.meta_code) == "undefined" || d_content.meta_code == ""){
      				
					validate_detail_buf_acc.append("财务模板明细：第["+indexNo+"]行 分录内容为空 请输入\n");
  				
  		  		}
  		  		
				if(typeof(d_content.direction) == "undefined"){
		
					validate_detail_buf_acc.append("财务模板明细：第["+indexNo+"]行 借贷方向为空 请输入\n");
			
				}
				accSortCodes[d_content.sort_code] = 1;
			}); 
			
		}else{
			
			$.ligerDialog.warn("请填写模板财务明细 !");
			return false;
		}
		
		
		
		var acc_busi_template_detail_budg = bottomGridBudg.getData();
		
		var validate_detail_buf_budg = new StringBuffer();
		
		if(acc_busi_template_detail_budg.length > 0){
			
			$.each(acc_busi_template_detail_budg, function(d_index, d_content){
				
				var indexNo = d_index+1; 
				
				
				if (isnull(d_content.vouch_row)) {
					bottomGridBudg.deleteRow(d_index);
					return;
				}
				
				if(typeof(d_content.vouch_row) == "undefined"){
      				
					validate_detail_buf_budg.append("预算模板明细：第["+indexNo+"]行 分录行号为空 请输入\n");
  				
  		  		}
				
				if(typeof(d_content.summary) == "undefined" || d_content.summary == ""){
      				
					validate_detail_buf_budg.append("预算模板明细：第["+indexNo+"]行 摘要为空 请输入\n");
  				
  		  		}
				
				if(typeof(d_content.meta_code) == "undefined" || d_content.meta_code == ""){
      				
					validate_detail_buf_budg.append("预算模板明细：第["+indexNo+"]行 分录内容为空 请输入\n");
  				
  		  		}
  		  		
				if(typeof(d_content.direction) == "undefined"){
		
					validate_detail_buf_budg.append("预算模板明细：第["+indexNo+"]行 借贷方向为空 请输入\n");
			
				}
				
				//if(typeof(d_content.cal) == "undefined"){
					
					//validate_detail_buf_budg.append("预算模板明细：第["+indexNo+"]行 计算公式为空 请输入\n");
			
				//}else{
					//if(!checkCal(indexNo,d_content.cal)){
						//validate_detail_buf_budg.append(errorMsg);
					//}
				//}
				
			}); 
			
		}
		

		if(validate_detail_buf_acc.toString()  != ""){
			
			$.ligerDialog.warn(validate_detail_buf_acc.toString());
			
			return false;
		}
		
		if(validate_detail_buf_budg.toString()  != ""){
			
			$.ligerDialog.warn(validate_detail_buf_budg.toString());
			
			return false;
		}
		
		var formPara = {
				
				mod_code : note.data.mod_code,
				
				busi_type_code : note.data.busi_type_code,
				
				template_code : template_code,
				
				template_name : template_name,
				
				vouch_type_code : vouch_type_code,
				
				summary : $("#summary").val(),
				
				is_detail_summary : is_detail_summary,
				
				saveFlag:saveFlag,
				
				acc_year:$("#acc_year").val(),

				acc_busi_template_detail_acc : JSON.stringify(acc_busi_template_detail_acc),
				
				acc_busi_template_detail_budg : JSON.stringify(acc_busi_template_detail_budg)

		};

		ajaxJsonObjectByUrl("addAccBusiTemplate.do", formPara, function(responseData) {

			if (responseData.state == "true") {
				
				grid.options.parms = [];
				
				grid.options.newPage = 1;
				
				grid.options.parms.push({name : 'mod_code',value : note.data.mod_code});
				
				grid.options.parms.push({name : 'busi_type_code',value : note.data.busi_type_code});
				
				grid.options.parms.push({name : 'acc_year',value :$("#acc_year").val()});
			
				grid.loadData(grid.where);//加载查询条件
				
			}
		});
    }
    
    
	 
	 /* 设置树形菜单 */
     function onSelect(note,accYear){//根据表字段进行添加查询条件
		 	if(accYear==undefined){
		 		accYear=$("#acc_year").val();
			}
			
			grid.options.parms=[];grid.options.newPage=1;

			grid.options.parms.push({name:'mod_code',value:note.data.mod_code});
    	   
			grid.options.parms.push({name:'busi_type_code',value:note.data.busi_type_code});
			
			grid.options.parms.push({name:'acc_year',value:accYear});

			grid.loadData(grid.where);//加载查询条件
			

		 	var formPara = {
					mod_code : note.data.mod_code,
					busi_type_code : note.data.busi_type_code
			}; 

			 ajaxJsonObjectByUrl("queryAccBusiRelaForMetaCode.do?isCheck=false", formPara, function(responseData) {
				 
				 var accData = [];
				 
				 var budgData = [];
				 
				 $.each(responseData.Rows,function(){
					 if(this.kind_code == '01'){
						 accData.push(this);
					 }else{
						 budgData.push(this);
					 }
				 });
			
				

				busiRelaDataAcc = accData;
				
				busiRelaDataBudg = budgData;
				
				bottomGridManagerAcc = null;
				
				bottomGridAcc = null;
				
				bottomGridManagerBudg = null;
				
				bottomGridBudg = null;
				
		    	loadBottomHead(null);
		    	
		    	bottomGridAcc.set({ data: {}});
		    	
		    	bottomGridBudg.set({ data: {}});
		    	
		    	saveFlag = true;
			});  
			
       }
       
        function loadTree(){
        	
        	var data = [];

        	tree = $("#tree").ligerTree({
				
				data : data,
				
				checkbox : false,
				
				idFieldName : 'busi_type_code',
				
				parentIDFieldName : 'super_code',
				
				textFieldName:'busi_type_name',
				
				onSelect: onSelect,
				
				isExpand: 3,
				
				nodeWidth:200

			});
        	
        	$("#tree").width("auto");
			
			treeManager = $("#tree").ligerGetTreeManager();
			
			treeManager.collapseAll();

	}
    
        
    //年度发生改变，查询凭证模板
    function accYearFunc(newDateStr,curDate){
    	
    	if(newDateStr==""){
			return;
		}
    	
    	var node = treeManager.getSelected()
        if(node==null){
        	return false;
        }
    		
    	onSelect(node,newDateStr);
    	
    }
   
</script>

</head>
</head>

<body style="padding: 0px;" >
<input type="text" id="minDate" style="display:none" value="${minDate}"/>
<input type="text" id="maxDate" style="display:none" value="${maxDate}"/>
<input type="text" id="curDate" style="display:none" value="${curDate}"/>
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<table cellpadding="0" cellspacing="0" class="l-table-edit" >
   		 <tr>
            <td align="right" class="l-table-edit-td"  style=padding-left:20px;">系统名称：</td>
            <td align="left" class="l-table-edit-td"><input name="mod_code" type="text" id="mod_code" ltype="text" /></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">会计年度：</td>
			<td align="left" class="l-table-edit-td" ><input class="Wdate" name="acc_year" ltype="text" type="text" id="acc_year"  onFocus="WdatePicker({minDate:'#F{$dp.$D(\'minDate\')}',maxDate:'#F{$dp.$D(\'maxDate\')}',isShowClear:false,readOnly:true,dateFmt:'yyyy',ychanging:function(dp){accYearFunc(dp.cal.getNewDateStr());}})"/></td>

        </tr> 
    </table>
    
    <div id="layout1" class="l-layout">
            <div position="left" title="业务类型"><ul id="tree" style="overflow:auto"></ul></div>
            <div position="center"><div id="maingrid"></div>
            	<div position="centerbottom" > 
            		<div position="top">
            			<table cellpadding="0" cellspacing="0" class="table-layout">
					   		 <tr>
					            <td align="right" class="l-table-edit-td"  style=padding-left:20px;"> 模板编码：</td>
					            <td align="left" class="l-table-edit-td"><input name="template_code" type="text" id="template_code" ltype="text" validate="{required:true,maxlength:20}"/></td>
					            <td align="left"></td>
					            <td align="right" class="l-table-edit-td"  style=padding-left:20px;"> 模板名称：</td>
					            <td align="left" class="l-table-edit-td"><input name="template_name" type="text" id="template_name" ltype="text" validate="{required:true,maxlength:20}"/></td>
					            <td align="left"></td>
					            <td align="left">摘要按明细：<input name="is_detail_summary"  id="is_detail_summary" type="text"  /> </td>
					        </tr> 
					        <tr>
					            <td align="right" class="l-table-edit-td"  style=padding-left:20px;">凭证类型：</td>
					            <td align="left" class="l-table-edit-td"><input name="vouch_type_code" type="text" id="vouch_type_code" ltype="text" validate="{required:true,maxlength:20}"/></td>
					            <td align="left"></td>
					            <td align="right" class="l-table-edit-td"  style=padding-left:20px;">摘    要：</td>
					            <td align="left" class="l-table-edit-td"><input name="summary" type="text" id="summary" ltype="text" validate="{required:true,maxlength:20}"/></td>
					            <!-- td align="left"></td>
					            <td align="left"><input class="l-button l-button-test"  type="button" value="保存" onclick="saveAccBusiTemplate()"/></td-->
					        </tr> 
					    </table>
            		</div>
				    <div id="layout2" style="z-index: 2">
				    	 <div position="left" title="财务会计"><div id="bottomGridAcc"></div></div>
	            		 <div position="right" title="预算会计"><div id="bottomGridBudg"></div></div>  
					</div>
	            </div>
            </div>    
        </div>	
</body>
</html>
