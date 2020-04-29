<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
    <html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>修改</title>
        <jsp:include page="${path}/resource.jsp">
            <jsp:param value="select, datepicker, checkbox, grid, tab, tree, hr, dialog, form, validate, upload, pageOffice, jquery_print" name="plugins" />
        </jsp:include>
        <style>
            .tree-container {
                height: 100%;
                overflow: auto;
                /* padding: 5px; */
                box-sizing: border-box
            }


            .grid_header .float_left {
                float: left;
            }

            .grid_header .float_right {
                float: right;
            }

            .grid_header {
                padding: 4px 10px;
            }

            .base_space {
                display: -webkit-flex;
                display: -ms-flexbox;
                display: flex;
                border: 1px solid #aecaf0;
                padding: 5px;
                padding-top: 45px;
                box-sizing: border-box;
                height: auto;
            }

            .base_space .form_content {
                -webkit-flex: 3;
                -ms-flex: 3;
                flex: 3;
            }

            .base_space .file_content {
                /* padding-right:170px;
                width: 110px; */
              /*   -webkit-flex: 1; */
                -ms-flex: 1;
               /*  flex: 1; */
            }

            .main_operation {
                /* border: 1px solid #aecaf0; */
                position: absolute;
                left: 0;
                right: 0;
                background: linear-gradient(#e2f0ff, #c1dbfa, #e2f0ff);
                border: 1px solid #aecaf0;
            }

            /* 按钮样式 */

            .grid_header button {
                box-sizing: border-box;
                height: 26px;
                padding-left: 10px;
                padding-right: 10px;
                border: 1px solid #aecaf0;
                background: #e5edf4;
                outline: none;
                border-radius: 2px;
                cursor: pointer;
                -webkit-box-shadow: inset 0 1px 1px rgba(0, 0, 0, .1);
                box-shadow: inset 0 1px 1px rgba(0, 0, 0, .1);
            }
        </style>
        <script>
            var child_select,
                child_grid,
                child_tree,
                form, validate,
                file;
           var tab="HOS_EMP";
           var tab_code="HOS_EMP";
           var show_child_count = 1;
			  var curIndex = parent.$.etDialog.getFrameIndex(window.name);
              
              var parentFrameName = parent.$.etDialog.parentFrameName;
              var parentWindow = parent.window[parentFrameName];

              var data =  parentWindow.main_grid.selectGet().length== 0 ? parentWindow.rowData : parentWindow.main_grid.selectGet()[0].rowData;
              var emp_id = data.emp_id;//主表ID
  			var emp_code=data.emp_code;
              var is_update=emp_id;
            // 子集表格参数
            var child_toolbar = {
                items: [
                    { type: "button", label: '添加', icon: 'add', listeners: [{ click: add }] },
                    { type: "button", label: '删除', icon: 'delete', listeners: [{ click: del }] },
                  /*   { type: "button", label: '保存', icon: 'save', listeners: [{ click: save }] }, */
                    { type: "button", label: '打印设置', icon: 'settings', listeners: [{ click: null }] },
                    { type: "button", label: '打印', icon: 'print', listeners: [{ click: null }] },
                    { type: "button", label: '导出', icon: 'import', listeners: [{ click: null }] },
                    { type: "button", label: '导入', icon: 'export', listeners: [{ click: null }] },
                ]
            };
            // ----------------------------------子集表格参数分割线--------------------------------
            var child_obj = {
            		
                editable: false,
                
                height: '30%',
                
                inWindowHeight: true,
                
                checkbox: true,
                
                columns: [],
                
                selectionModel: {type: 'row'},
                
                virtualY: false,
                
                showBottom: false,
                rowDblClick: function (e, ui) {
                
                	tab_code=  child_select.getValue();
                      
                    		var from=getFromField({group_id:'${group_id}',hos_id:'${hos_id}',formTable:[tab],})
                            is_update=ui.rowData.emp_id;
                        	 $('.form_content').html(''); //初始化前清空
                        	 
                        	

                        	 $('.form_content').height(450);
                            	var arr = new Array();
                             	var option=from.fieldItems;
                             	   var data = ui;
                              
                                 $(data).each(function () {
                                     var rowdata = this.rowData;
                                     Object.keys(rowdata).forEach(function(key){
                                   		 if(rowdata[key+"_name"]){
                                   			 arr.push({name: key,value: rowdata[key],text:rowdata[key+"_name"]}) 
                                   			 
                                   		 }else{
                                   		 arr.push({name: key,value: rowdata[key]})}
                                 })
                                

                            	}); 
                            	 for (var i=0;i<arr.length;i++){
                          		
                          		var fieldItems;
                          		
                          		var name=arr[i].name;
                          		
                          		for(var j=0;j<option.length;j++){
                          			
                          			var optionName=option[j].id;
                          			
                          			if(name==optionName){
                          			if(option[j].type=='select'){
                          				//option[j].OPTIONS.addOption={id:arr[i].value,text:arr[i].text};
                          				option[j].OPTIONS.defaultOption={id:arr[i].value,text:arr[i].text};

                          			}else{
                          				option[j].value=arr[i].value;
                          			}	
                          			}
                          		}
                          		
                          	}
             form = $('.form_content').etForm(from);
              	$("#emp_id").attr('disabled', true);//职工编码不能修改
        	      
                                 form.initWidget();
                                 validate = form.initValidate();
                                /*  if (data.PHOTO) {
                                     file.setValue("${photo}");
                                 } */
                
                }
            }
            
          
            function reloadChildGrid(value) {// 远程加载子集表格 child_grid
                
                var   tab_code=  child_select.getValue();
                
                             
                	var url;
              	  //拼接动态子集查询
              		var  arr=tab_code.toLowerCase().split("_");
              	  if(tab_code!="HOS_EMP"){
              		  url='query';
              	  for(var i=1;i< arr.length;i++){//遍历字符串
              		  url+=arr[i][0].toUpperCase() + arr[i].substring(1, arr[i].length);
              	      }
              	  
              	  
            	  url+='.do';
                  var columns=getGridColumns({ui:'et',group_id:'${group_id}',hos_id:'${hos_id}',gridTables:[tab_code],design:url});

              	child_grid.option('columns', columns);
                    var child_param = [{ name:'tab_code',value:tab_code}];
                    if(value){
                        child_param.push({name: 'emp_id',value: value })	
                    }
                    child_grid.loadData(child_param,url+"?isCheck=false");
                    
                    child_grid.refreshView();
                    
                    child_grid.commit();
              	  } 
            }
            // ----------------------------------子集表格参数分割线--------------------------------

            $(function () {
                init();
                $('.center').width($('.container').width() - $('.left').width()-1);
				
            });

            function init() {
                file = $("#base_file").etUpload();
              
                
                initTree();
                initSelect();
                initForm();
                initGrid();
                initEvent();
               
                // 初始化主集按钮
                initMainBtn();
            	if(parentWindow.main_select.getValue()=="HOS_EMP"){
            		 $("#child_show").html("");
                     $("#child_block").hide();
                     $('.base_space').get(0).style.height = '100%';
            	}
				
                
            }

            function initMainBtn() {
                var is_write = parentWindow.is_write;
                if (is_write === 0) {
                    $("#btn_save").attr('disabled', true);
                } else {
                    $("#btn_save").attr('disabled', false);
                }
            }

            function initTree() {
                var child_url = '../../queryHrStoreTabStruc.do?isCheck=false&store_type_code='+parentWindow.archives_select.getValue();
                child_tree = $("#child_tree").etTree({
                    async: {
                        enable: true,
                        url: child_url
                    },
                    callback: {
	onNodeCreated : function(event, treeId, node) {
            				
            				if ( node.id === tab ) {
            					child_tree.selectNode(node);
            				}
            			},
                    	 onClick: function (e, id, node) {
                         	if(node.id!="HOS_EMP"){
                                 $("#child_show").html("隐藏子集");
                                 $("#child_block").show();
                                 $('.base_space').height('auto');
                                 $('.base_space').get(0).style.height = 'auto';
                                 tab=node.id;
                                 child_select.setValue(node.id);
                                 child_grid.commit();//防止提交多余数据
                                 is_update=0;
                           		var from=getFromField({group_id:'${group_id}',hos_id:'${hos_id}',formTable:[node.id]})
                                  	from.fieldItems[0].OPTIONS.defaultOption={id: data.emp_id, text: data.emp_name}

                                	 $('.form_content').html(''); //初始化前清空
                              
                             		 $('.file_content').hide(true);
                             		 $('.form_content').height(450);
                             		  form = $('.form_content').etForm(from);
                                       
                                   	$("#emp_id").attr('disabled', true);//职工编码不能修改
                                   
                                                       form.initWidget();
                                                       validate = form.initValidate();
                        	}else{
                        		tab="HOS_EMP";
                        		  is_update=emp_id;
                        		 child_select.setValue("HOS_EMP");
                        		 $("#child_show").html("");
                        	   $("#child_block").hide();
                        	 $('.base_space').height('auto');
                          $('.base_space').get(0).style.height = 'auto';
                        	
                        		 $('.form_content').height(800);
                        		initForm();
                        	}
          
                               /*  if (data.PHOTO) {
                                    file.setValue("${photo}");
                                }; */
               
                        }
                    },
                    addSuffix: function () {
                        var treeNodes = child_tree.transformToArray(child_tree.getNodes());
                        return {
                            nodes: treeNodes,
                            rules: [
                                { rule: { is_write: 0 }, text: '只读', color: 'red' }
                            ]
                        }
                    }
                });
            }

            function initSelect() {
                child_select = $("#child_select").etSelect({
                    labelField: 'name',
                    maxOptions: '', 
                    onInit: function (value) {
                        reloadChildSelect(emp_id);
                    },
                    onChange: function (value) {
                    	if(tab=="HOS_EMP"||value==""){
               		 selectTreeNodeById(child_tree, "HOS_EMP");
               		tab=value;
           	}else{
               selectTreeNodeById(child_tree, value);
             
           	
               reloadChildGrid(emp_id);
               setPerm(value);}
                    /* 	var from=getFromField({group_id:'${group_id}',hos_id:'${hos_id}',formTable:[value],})
                        
                      	 $('.form_content').html(''); //初始化前清空
                      	 $('.file_content').hide(true);
                      	 $('.form_content').height(450);
                          
           form = $('.form_content').etForm(from);
            if(data){
           	$("#emp_id").attr('disabled', true);//职工编码不能修改
           } 
                               form.initWidget();
                               validate = form.initValidate(); */
                    }
                });
            }
            

            function initForm() {
            		var from=getFromField({group_id:'${group_id}',hos_id:'${hos_id}',formTable:["HOS_EMP"],})

                	$('.form_content').html(''); //初始化前清空
               	var arr = new Array();
                	var option=from.fieldItems;
             	   var data = parentWindow.main_grid.selectGet().length== 0 ? parentWindow.rowData : parentWindow.main_grid.selectGet()[0].rowData;
                 
                    $(data).each(function () {
                        var rowdata = this;
                        Object.keys(rowdata).forEach(function(key){

                      		 if(rowdata[key+"_name"]){
                      			 arr.push({name: key,value: rowdata[key],text:rowdata[key+"_name"]}) 
                      			 
                      		 }else{
                      		 arr.push({name: key,value: rowdata[key]})}
                    })
                   

               	}); 
               	 for (var i=0;i<arr.length;i++){
             		
             		var fieldItems;
             		
             		var name=arr[i].name;
             		
             		for(var j=0;j<option.length;j++){
             			
             			var optionName=option[j].id;
             			
             			if(name==optionName){
             			if(option[j].type=='select'){
             				/*  if(optionName=='dept_id'){
             					option[j].OPTIONS.defaultOption={id: data.dept_id+"@"+data.dept_no, text:arr[i].text };
             				}  */
             				//option[j].OPTIONS.defaultValue=arr[i].value;
              				option[j].OPTIONS.defaultOption={id:arr[i].value,text:arr[i].text};

             			}else{
             				option[j].value=arr[i].value;
             			}	
             			}
             		}
             		
             	}
form = $('.form_content').etForm(from);
if(data){
	$("#emp_code").attr('disabled', true);//职工编码不能修改
}
                    form.initWidget();
                    validate = form.initValidate();
                   /*  if (data.PHOTO) {
                        file.setValue("${photo}");
                    } */
            }

            function initGrid() {
            		  child_grid = $("#child_grid").etGrid(child_obj);
              
            }
            
			function verification(){
				var flag = false ;
				 var mobtle=$("#MOBILE").val()
                 var re =/^1([358][0-9]|4[579]|66|7[0135678]|9[89])[0-9]{8}$/;
	                 if(mobtle){
	                 	 if (re.test(mobtle)){
	                 		flag = false;    
	                      }else{
	                    	  flag = true;                 	  
	                 		 $.etDialog.error('请输入正确手机号')
							return true;
	                      }
	                 }
                 	 var PID = $("#ID_CARD").val();
                 	 var pre =/(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/;
                 	 if(PID){
	                 	 if (pre.test(PID)){
	                 		flag = false;                 	  
	                       }else{
	                    	   flag = true;                 	  
	                  		 $.etDialog.error('请输入正确身份证号')
							return true;
	                       }
                 	 }
                 	 var email = $("#EMAIL").val();
                 	 var ere = /^(\w-*\.*)+@(\w-?)+(\.\w{2,})+$/;
                 	 if(email){
                 		if (ere.test(email)){
                 			flag = false;                 	  
	                       }else{
	                    	   flag = true;
	                  		 $.etDialog.error('请输入正确邮箱')
							return true;
	                       }
                 	 }
                 	 return flag ; 
			}
			function autoCompute(){
				//alert(1);
			}
            function initEvent() {
                $("#search_input").keyup(function (e) {
                    var _this = this;
                    searchTree({
                        tree: child_tree,
                        value: _this.value,
                        callback: function (node) {
                            _this.focus();
                            child_select.setValue(node.id);
                        }
                    })
                });

                $("#btn_save").click(function () {
                	var updateUrl;
                	var addUrl;
                	var tab_code= child_tree.getSelectedNodes()[0].id ;
                	if(tab_code=="HOS_EMP"){
                		updateUrl="updateHosEmp.do?isCheck=false";
                		addUrl="addHosEmp.do?isCheck=false";
                	}else{
                	 //拼接动态子集查询
              		var  arr=tab_code.toLowerCase().split("_");
              		 var updateUrl='update';
              		var addUrl='add';
              	  for(var i=1;i< arr.length;i++){//遍历字符串
              		updateUrl+=arr[i][0].toUpperCase() + arr[i].substring(1, arr[i].length);
              		addUrl+=arr[i][0].toUpperCase() + arr[i].substring(1, arr[i].length);
              	      }
              	  
              	updateUrl+=".do?isCheck=false";
              	addUrl+=".do?isCheck=false";
                	}
                    if (!validate.test()) {return;}
                    
                    if(verification()){return;}
                    
                    var formData = form.getFormData();
                    if(formData.get('dept_id')!=null){
                    if(formData.get('dept_id')!=null&&formData.get('dept_id')==data.dept_id){
                    	 var dept_id=formData.get('dept_id');
                         var dept_no=data.dept_no;
                         formData.delete('dept_id');
                         formData.append('dept_id', dept_id);
                         formData.append('dept_no',dept_no);
                    }else{
                    	 var dept_id=formData.get('dept_id').split('@')[0];
                         var dept_no=formData.get('dept_id').split('@')[1];
                         formData.delete('dept_id');
                         formData.append('dept_id', dept_id);
                         formData.append('dept_no',dept_no);
                    }
                    }
                    formData.append('tab_code',tab_code);
                    formData.append('store_type_code', parentWindow.archives_select.getValue());
                    formData.append('emp_id', emp_id);
                   
                    //dept_select.getValue() 
                    formData.append('photo', file.getValue());
                    formData.append('kind_name', $("#KIND_CODE").text());
                    if(is_update==0){
                    	
                    	  ajaxPostFormData({
                              url: addUrl,
                              data: formData,
                              dataType: 'json',
                              success: function (data) {
                                  if (data.state == 'true') {
                                	  if(tab_code=="HOS_EMP"){
                                		  emp_id = data.EMP_ID;
                                	  }else{
                                		  emp_id = emp_id;
                                	  }
                                     
                                     // initForm();
                                      //reloadChildGrid();
                                      var parentFrameName = parent.$.etDialog.parentFrameName;
                                      var parentWindow = parent.window[parentFrameName];
                                      parentWindow.reloadMainGrid();
                                  } else {
                                      $.etDialog.error(data.error)
                                  }
                              }
                          });
                    	
                    }else{
                    ajaxPostFormData({
                        url: updateUrl,
                        data: formData,
                        dataType: 'json',
                        success: function (data) {
                            if (data.state == 'true') {
                                emp_id = emp_id;
                               // initForm();
                                //reloadChildGrid();
                                var parentFrameName = parent.$.etDialog.parentFrameName;
                                var parentWindow = parent.window[parentFrameName];
                                parentWindow.reloadMainGrid();
                            } else {
                                $.etDialog.error(data.error)
                            }
                        }
                    });
                    }
                })

                // 基础按钮展开收起
                var show_base_count = 1;
                $("#show_base_button").click(function () {
                    show_base_count++;
                    if (show_base_count % 2 === 0) {
                        $(this).html("<<展开");
                        $("#base_button_group button").css('visibility', 'hidden');
                    } else {
                        $(this).html(">>收起");
                        $("#base_button_group button").css('visibility', '');
                    }
                });

                // 子集按钮展开收起
                //var show_child_count = 1;
                $("#show_child_button").click(function () {
                    show_child_count++;
                    if (show_child_count % 2 === 0) {
                        $(this).html("<<展开");
                        $("#child_button_group button").css('visibility', 'hidden');
                    } else {
                        $(this).html(">>收起");
                        $("#child_button_group button").css('visibility', '');
                    }
                });

                $("#child_show").click(function () {
                    show_child_count++;
                    if (show_child_count % 2 === 0) {
                        $(this).html("显示子集");
                        $("#child_block").hide();
                        $('.base_space').get(0).style.height = '100%';
                    } else {
                        $(this).html("隐藏子集");
                        $("#child_block").show();
                        $('.base_space').height('auto');
                        $('.base_space').get(0).style.height = 'auto';
                    }
                });

                var curTabIndex = 0;
                $(document).on('keyup', function (e) {
                    if (e.keyCode === 13) {
                        var inputs = $('.form_content').find('input').not('[visibility]');
                        curTabIndex++;
                        $(inputs[curTabIndex]).focus();
                    }
                });

                $('.form_content').on('focus', 'input', function (e) {
                    var inputs = $('.form_content').find('input').not('[visibility]');
                    curTabIndex = inputs.index(e.target);
                });
            }

            

            // 远程加载子集下拉框 child_select
            function reloadChildSelect(value) {
                child_select.reload({
                    url: '../../queryHrStoreTabStruc.do?isCheck=false&ignore=HOS_EMP&store_type_code='+parentWindow.archives_select.getValue()
                });
            }

            function add() {
            	
            	tab=  child_select.getValue();
                
        		var from=getFromField({group_id:'${group_id}',hos_id:'${hos_id}',formTable:[tab],})
                  from.fieldItems[0].OPTIONS.defaultOption={id: data.emp_id, text: data.emp_name}

            	 $('.form_content').html(''); //初始化前清空
            	
            	 $('.form_content').height(450);
                	
               form = $('.form_content').etForm(from);
               if(tab!="HOS_EMP"){
          		 $('.file_content').hide(true);
                	$("#emp_id").attr('disabled', true);//职工编码能修改
          	 }else{        
          	 $("#emp_code").attr('disabled', true);//职工编码不能修改
          	 }
                     form.initWidget();
                     validate = form.initValidate();
            }

            function del() {
            	
                var data = child_grid.selectGet();
                var tab_code = child_select.getValue();
                var url;
                if(tab_code=="HOS_EMP"){
                	url="deleteHosEmp.do?isCheck=false"
                }else{
                	var  arr=tab_code.toLowerCase().split("_");
           		  url='delete';
           	  for(var i=1;i< arr.length;i++){//遍历字符串
           		  url+=arr[i][0].toUpperCase() + arr[i].substring(1, arr[i].length);
           	      }
           	  
           	  url+=".do?isCheck=false";
                }
                if (data.length == 0) {
                    $.etDialog.error('请选择行');
                } else {
                    var ParamVo = [];
                    $(data).each(function () {
                        var rowdata = this.rowData;
                        rowdata.tab_code=tab_code;
                        ParamVo.push(rowdata);
                    });
                    if (ParamVo.length > 0) {  // 通过后台所删的数据
                        $.etDialog.confirm('确定删除?', function () {
                            ajaxPostData({
                                url: url,
                                data: {
                                    paramVo: JSON.stringify(ParamVo),
                                },
                                success: function (res) {
                                    if (res.state == "true") {
                                        reloadChildGrid();
                                        child_grid.deleteSelectedRows();
                                    }
                                }
                            })
                        });
                    }
                }
            }
			
            function print_child(){
            	if(child_grid.getAllData().length==0){
            		$.etDialog.error("暂无数据！");
        			return;
        		}
            	   var value = child_select.getValue();
                   var url;
               if(value!='HOS_EMP'){
               	//拼接动态子集查询
           		var  arr=value.toLowerCase().split("_");
           		url='query';
           	  for(var i=1;i< arr.length;i++){//遍历字符串
           		  url+=arr[i][0].toUpperCase() + arr[i].substring(1, arr[i].length);
           	      }
           	  
           	  url+=".do";
               }else{
               	url='queryHosEmp.do';
               } ;          
               var heads={
                		 /* "isAuto":true,//系统默认，页眉显示页码
                		"rows": [
            	          {"cell":0,"value":"表名："+tree.getSelectedNodes()[0].name},
                		]  */}; 
            	var printPara={
            			title: child_tree.getSelectedNodes()[0].name,//标题
                  		columns: JSON.stringify(child_grid.getPrintColumns()),//表头
                  		class_name: "com.chd.hrp.hr.service.QueryService",
               			bean_name: "queryService",
               			method_name: "queryBaseInfoPtint",
               			heads: JSON.stringify(heads),//表头需要打印的查询条件,可以为空
               			foots: '',//表尾需要打印的查询条件,可以为空 
               			EMP_ID: emp_id,
                    	tab_code: child_select.getValue(),
                    	store_type_code: parentWindow.archives_select.getValue()
                   	};

            	//执行方法的查询条件
    	   		printPara['design_code']=url;
                	officeGridPrint(printPara);
            }
            
            
          /*   function save() {
                var tab_code = child_select.getValue();
                if (emp_id) {
                    if (tab_code != null && tab_code != '') {

                        var addData = child_grid.getAdded(); //添加数据
                        var modData = child_grid.getUpdated(); //修改数据'
                        
                        var addDataVo = [];
                        var modDataVo = [];
                        $(addData).each(function () {
                            addDataVo.push(this.rowData);
                        });
                        $(modData).each(function () {
                            modDataVo.push(this.rowData);
                        });

                        var formData = form.getFormData();
                        ajaxPostData({
                            url: 'addEmpSubItem.do?isCheck=false',
                            data: {
                                'tab_code': tab_code,
                                'store_type_code': "parentWindow.archives_select.getValue()",
                                'EMP_ID': emp_id,
                                'EMP_CODE': formData.get('EMP_CODE'),
                                'EMP_NAME': formData.get('EMP_NAME'),
                                'addData': JSON.stringify(addDataVo),
                                'modData': JSON.stringify(modDataVo),
                            },
                            delayCallback: true,
                            success: function (data) {
                                child_grid.commit();
                            }
                        })
                    } else {
                        $.etDialog.error('请选择子集菜单');
                    }
                } else {
                    $.etDialog.error("先保存主表");
                }
            }
 */
            // 下拉框上一条
            function prevSelect() {
                var options = child_select.getOptions();
                var current = child_select.getValue();
                var arrOptions = Object.keys(options).sort(function (a, b) {
                    return options[a].$order - options[b].$order;
                });
                var len = arrOptions.length;
                var prevIndex = arrOptions.indexOf(current) - 1;
                child_select.setValue(arrOptions[(prevIndex + len) % len]);
            }

            // 下拉框下一条
            function nextSelect() {
                var options = child_select.getOptions();
                var current = child_select.getValue();
                var arrOptions = Object.keys(options).sort(function (a, b) {
                    return options[a].$order - options[b].$order;
                });
                var len = arrOptions.length;
                var prevIndex = arrOptions.indexOf(current) + 1;
                child_select.setValue(arrOptions[(prevIndex + len) % len]);
            }

            function setPerm(treeId) {

                var node = child_tree.getNodesByParam('id', treeId)[0];
                if (node.is_write === 0) {
                    $("#child_block .float_left>button").attr('disabled', true)
                } else {
                    $("#child_block .float_left>button").attr('disabled', false)
                }
            }
            
            function importData(){
            	var para = {
      					 "column" : [{
      						"name" : "",
      						"display" : "",
      						"width" : "200",
      						"require" : true
      					}
      					] 
      				
      				};
      				importSpreadView("",para);
            }
            function printdiv(printpage) 
            { 
            /* var headstr = "<html><head><title></title></head><body>"; 
            var footstr = "</body>"; 
            var newstr = document.all.item(printpage).innerHTML; 
            var oldstr = document.body.innerHTML; 
            document.body.innerHTML = headstr+newstr+footstr;  */
            window.print(); 
            document.body.innerHTML = oldstr; 
            return false; 
            } 
        </script>
    </head>

    <body style="overflow:auto">
        <div class="container" style="overflow-x: hidden;">
            <div class="left border-right">
                <div class="tree-container">
                    <div class="search-form">
                        <label for="">定位：</label>
                        <input type="text" class="text-input" id="search_input" style="width:140px;">
                    </div>
                    <div id="child_tree"></div>
                </div>
            </div>
            <div class="center" style="padding:0;position:relative">
              <div id='child_block'>
                    <div class="grid_header clearfix">
                        <div class="float_left">
                          <!--   <button onclick='save()'>保存</button> -->
                            <button onclick='add()'>添加</button>
                            <button onclick='del()'>删除</button>
                            <label for="">子集：</label>
                            <a style="text-decoration:none" href="javascript:;" onclick="prevSelect()"><< </a>
                            <select name="" id="child_select" style="width:180px;"></select>
                            <a style="text-decoration:none" href="javascript:;" onclick="nextSelect()">>></a>
                        </div>
                        <div class="float_right" id="child_button_group">
                         	
                            <!-- <button>打印设置</button> -->
                          <!--   <button onclick="print_child()">打印</button> -->
                            <!-- <button onclick="importData()">导入</button> -->
                            <!-- <button>导出</button> -->
                         <!--    <a href="javascript:;" id="show_child_button" style="text-decoration:none">>>收起</a> -->
                        </div>
                    </div>
                    <div id="child_grid"></div>
                </div>
                <div class='grid_header clearfix main_operation'>
                    <div class="float_left">
                        <button id="btn_save">保存</button>
                    </div>
                    <div class="float_right" id="base_button_group">
                    	<a href="javascript:;" id='child_show' style="text-decoration:none">隐藏子集</a>
                        <!-- <button id="btn_printSetting">打印设置</button>
                        <button id="btn_printView">打印预览</button>
                        <button id="btn_print"  onclick="printdiv('base_space');">打印</button>
                        <a href="javascript:;" id="show_base_button" style="text-decoration:none">>>收起</a> -->
                    </div>
                </div>
                
                <div id="base_space" class='base_space'>
                   <!--  <div class='file_content'>
                        <div id="base_file"></div>
                    </div> -->
                    <div class='form_content' style="width:100%;overflow-x:auto"></div>
                    
                </div>
              
            </div>
        </div>
    </body>

    </html>