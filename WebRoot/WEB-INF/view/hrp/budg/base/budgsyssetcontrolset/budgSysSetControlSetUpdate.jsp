<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%String path = request.getContextPath();%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <script src="<%=path%>/lib/ligerUI/js/ligerui.all.js"
	type="text/javascript"></script>
    <jsp:include page="${path}/resource.jsp">
	    <jsp:param value="dialog,datepicker,select,validate, grid" name="plugins" />
	</jsp:include><style>
  
            .grid_header .float_right {
                float: right;
               /*  padding: 5px 10px; */
            }
          /*   .leftContent{
           	    min-width: 220px;
   				max-width: 220px;
            } */





            /* 按钮样式 */
            button {
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

            /* 主集操作区 */
            .main_operation {
                padding: 1px 2px;
                border: 1px solid #aecaf0;
                background: linear-gradient(#e2f0ff, #c1dbfa, #e2f0ff)
            }


	      
	    
	      	
	      	
        </style>
	<script type="text/javascript" src="<%=path%>/lib/laydate/laydate.js"></script>
		
    <script>
        var is_jx,is_calculate,attend_types,is_stop,attend_ed_is,is_flag;

        $(function () {
        	initForm();
        	 initChildCrid();
        	 query();
     	    $("#children_block").hide();
        	//添加行
     		$("#btn_add").click(function() {
     			child_grid.addRow();
     		});
     	   $("#children_block").hide();
     	   
     	  if('${cont_m}' == "01"){
  		    $("#children_block").hide();

  		  $("#btn_batchSave").attr('disabled','disabled')
  	 }else if('${cont_m}' =='02'){
  		  $("#children_block").show();
  		
  		 $("#child_grid").css({height:270})
  		  	 cont_l.disabled();
  	 	 cont_p.disabled();
		    child_grid.refresh(); 
		    $("#btn_batchSave").removeAttr("disabled")
  	 }else{
  		 $("#children_block").show();
  		 $("#child_grid").css({height:270})
  		 // child_grid.option('height', '100%');
		    child_grid.refresh(); 

		    $("#btn_batchSave").attr('disabled','disabled')
  		 child_grid.getColumns()[3].editable=false;
  		 child_grid.getColumns()[4].editable=false;
  		 child_grid.getColumns()[5].editable=false;
  	 }
     	if('${use_nature}'== 0){
    		re_link.disabled();
    	}else {
    		re_link.enabled();
    	}
     	var use='${use_state}';
     	use_state.setValue(use.split(','))
     		var re_lin='${re_link}';
     		re_link.setValue(re_lin.split(','))
        })
        
        function initForm (){
        	//预算表
        	tab_code = $("#tab_code").etSelect({
                url: '../../queryTabCode.do?isCheck=false&plan_code='+'${plan_code}'+"&budg_year"+'${budg_year}',
                defaultValue: '${tab_code}',
                onChange: function(val){
                	cont_m.reload({
            		    url: '../../queryContMSelect.do?isCheck=false&tab_code='+val})
            		    
                }
            });
        	//cont_m 控制模式
        	cont_m = $("#cont_m").etSelect({
    		    url: '../../queryContMSelect.do?isCheck=false&tab_code='+'${tab_code}'+"&budg_year="+'${budg_year}',
    		    defaultValue: "${cont_m}",
    		     onChange: function(val){
    		    /* 	 if(val == "01"){
    		    		    $("#children_block").hide();
    		    			 cont_l.enabled();
    	  		    	 	 cont_p.enabled();
    	  		    	   $("#btn_batchSave").attr('disabled','disabled')
    		    	 }else if(val=='02'){
    		    		  $("#children_block").show();
    		    		 // child_grid.option('height', '55%');
  		    		    child_grid.refresh(); 
  		    		 cont_l.disabled();
  		    	 	 cont_p.disabled();
  		    	   $("#btn_batchSave").removeAttr("disabled")

    		    	 }else{
    		    		 cont_l.enabled();
	  		    	 	 cont_p.enabled();
    		    		 $("#children_block").show();
    		    		 // child_grid.option('height', '100%');
 		    		    child_grid.refresh(); 
    		    		 child_grid.getColumns()[3].editable=false;
    		    		 child_grid.getColumns()[4].editable=false;
    		    		 child_grid.getColumns()[5].editable=false;
    		    		  $("#btn_batchSave").attr('disabled','disabled')
    		    	 } */
    		    	 cont_l.reload({
    		    		 url: '../../queryContLSelect.do?isCheck=false&tab_code='+tab_code.getValue()+"&cont_m="+val+"&budg_year="+'${budg_year}',
    		    		 
    		    	 })
    		    	 
                	child_grid.getColumns()[3].editor.url ='../../queryContLSelect.do?isCheck=false&tab_code='+tab_code.getValue()+"&cont_m="+val+"&budg_year="+'${budg_year}';
                	//child_grid.getColumns()[4].editor.url = '../../queryContPSelect.do?isCheck=false&tab_code='+'${tab_code}'+"&cont_l="+ "${cont_l}"+"&cont_m="+ val+"&budg_year="+'${budg_year}',
                      
    		     }
    		});
        	/* 控制层次*/
    		cont_l = $("#cont_l").etSelect({
    		    url: '../../queryContLSelect.do?isCheck=false&tab_code='+'${tab_code}'+"&cont_m="+"${cont_m}"+"&budg_year="+'${budg_year}',
    		    defaultValue: "${cont_l}",
    		    onChange: function(val){
    		    cont_p.reload({
		    		 url: '../../queryContPSelect.do?isCheck=false&tab_code='+tab_code.getValue()+"&cont_l="+val+"&cont_m="+cont_m.getValue()+"&budg_year="+'${budg_year}',
		    		 
		    	 })
    		    }
    		});
    		/* 控制期间*/
    		cont_p = $("#cont_p").etSelect({
    		    url: '../../queryContPSelect.do?isCheck=false&tab_code='+'${tab_code}'+"&cont_l="+ "${cont_l}"+"&cont_m="+ "${cont_m}"+"&budg_year="+'${budg_year}',
    		    defaultValue: "${cont_p}",
    		});
    		/* 控制方式*/
    		cont_w= $("#cont_w").etSelect({
    		    url: '../../queryContWSelect.do?isCheck=false',
    		    defaultValue: "${cont_w}",
    		});
    		
    		//use_nature
    		//0独立占用；1联合占用
    			//占用性质
        	use_nature = $("#use_nature").etSelect({
                options: [
                    	{ id: 0, text: '独立占用'},
                    	{ id: 1, text: '联合占用'},
                ],
                defaultValue: '${use_nature}',
               onChange: function(id){
                	if(id == 0){
                		re_link.disabled();
                		re_link.clearItem();
                	}else {
                		re_link.enabled();
                	}
                } 
            });
    		//re_link 关联环节
    		re_link= $("#re_link").etSelect({
    		    url: '../../queryReLinkSelect.do?isCheck=false&mod_code='+'${mod_code}'+"&plan_code="+'${plan_code}',
    		    defaultValue: "${re_link}",
    		    checkboxMode:true
    		});
    		
    		//控制节点	cont_note	
    		cont_note= $("#cont_note").etSelect({
    		    url: '../../queryContNoteSelect.do?isCheck=false&link_code='+'${link_code}'+'&mod_code='+'${mod_code}',
    		    defaultValue: "${cont_note}",
    		});
    		//use_state 占用状态
    		use_state= $("#use_state").etSelect({
    		    url: '../../queryUseStateSelect.do?isCheck=false&link_code='+'${link_code}'+'&mod_code='+'${mod_code}',
    		    defaultValue: "${use_state}",
    		    checkboxMode:true
    		});
        	//保存
        	$("#save").click(function () {
        		if(!checked()){
       			  	return;
       		  	}
        		var saveObjs= [];
        		saveObjs.push({
        			 budg_year:'${budg_year}',
                	 plan_code:'${plan_code}',
                	 mod_code:'${mod_code}',
                	 link_code:'${link_code}',
                 	tab_code: tab_code.getValue(),
                 	cont_m:cont_m.getValue(),
                 	cont_l:cont_l.getValue(),
                 	cont_p:cont_p.getValue(),
                 	cont_w:cont_w.getValue(),
                 	use_nature:use_nature.getValue(),
                 	re_link:re_link.getValue(),
                 	cont_note:cont_note.getValue(),
                 	use_state:use_state.getValue(),
        			
        			
        		})
                ajaxPostData({
                    url: 'updateBudgSysSetControl.do',
                    data: {
                        'allData': JSON.stringify(saveObjs)
                    },
                     success: function (responseData) {
                    	 if(cont_m.getValue() == "01"){
     		    		    $("#children_block").hide();
     		    			 cont_l.enabled();
     	  		    	 	 cont_p.enabled();
     	  		    	   $("#btn_batchSave").attr('disabled','disabled')
     		    	 }else if(cont_m.getValue()=='02'){
     		    		  $("#children_block").show();
     		    		 // child_grid.option('height', '55%');
   		    		    child_grid.refresh(); 
   		    		 cont_l.disabled();
   		    	 	 cont_p.disabled();
   		    	   $("#btn_batchSave").removeAttr("disabled")

     		    	 }else{
     		    		 cont_l.enabled();
 	  		    	 	 cont_p.enabled();
     		    		 $("#children_block").show();
     		    		 // child_grid.option('height', '100%');
  		    		    child_grid.refresh(); 
     		    		 child_grid.getColumns()[3].editable=false;
     		    		 child_grid.getColumns()[4].editable=false;
     		    		 child_grid.getColumns()[5].editable=false;
     		    		  $("#btn_batchSave").attr('disabled','disabled')
     		    	 }
     		    	 cont_l.reload({
     		    		 url: '../../queryContLSelect.do?isCheck=false&tab_code='+tab_code.getValue()+"&cont_m="+val+"&budg_year="+'${budg_year}',
     		    		 
     		    	 })
     		    	 
                 	child_grid.getColumns()[3].editor.url ='../../queryContLSelect.do?isCheck=false&tab_code='+tab_code.getValue()+"&cont_m="+val+"&budg_year="+'${budg_year}';
                 	//child_grid.getColumns()[4].editor.url = '../../queryContPSelect.do?isCheck=false&tab_code='+'${tab_code}'+"&cont_l="+ "${cont_l}"+"&cont_m="+ val+"&budg_year="+'${budg_year}',
                       
     		     
                       },
                 })
        	});
      
        	$("#btn_save").click(function () {
        		//
        		var isPass;
        		if(cont_m.getValue()=='02'){
        			 isPass = child_grid.validateTest({
                		required: {
                			l_name :true,
                			p_name :true,
                			w_name:true
                		}
                	})
        		}else{
        		 isPass = child_grid.validateTest({
             		required: {
             			item_code :true,
             			item_name :true
             		}
             	})
             	}
        	if (!isPass) {
        		
        		return;
        	}
        	
        	 var selectData = child_grid.selectGet();
             if (selectData.length === 0) {
                 $.etDialog.error('请选择行');
                 return;
             } ;

            //验证重复数据
         /* 	if (!grid.checkRepeat(grid.selectGet(), ['emp_code','year'])){
                return;
            } */
             var param = [];
             var code;
             selectData.forEach(function (item) {
            	 if(item.rowData.state == 1){//验证行数据已经提交
            		 code = true;
    				 return;
            	 }
                 param.push({
                	 budg_year : '${budg_year}',
                	 plan_code : '${plan_code}',
                	 mod_code : '${mod_code}',
                	 link_code : '${link_code}',
                	 item_code : item.rowData.item_code,
                	 item_name : item.rowData.item_name,
                	 cont_l : item.rowData.cont_l,
                	 cont_p : item.rowData.cont_p,
                	 cont_w : item.rowData.cont_w,
                
                 });
             });
           
             
             ajaxPostData({
                 url: 'updateDetailBudgSysSetControl.do',
                 data: { paramVo: JSON.stringify(param) },
                 success: function () {
                     query();
                 }
            })
        	})
        	  	$("#btn_delete").click(function ()  {
                var data = child_grid.selectGet();
                if (data.length == 0) {
                    $.etDialog.error('请选择行');
                } else {
                    var param = [];
                    $(data).each(function () {
                        var rowdata = this.rowData;
                        param.push(rowdata);
                    });

                    $.etDialog.confirm('确定删除?', function () {
                        ajaxPostData({
                            url: 'deleteBudgSysSetControl.do',
                            data: {
                                paramVo: JSON.stringify(param)
                            },
                            success: function () {
                            	child_grid.deleteRows(data);
                            }
                        })
                    });
                }
            });
        	
        	   $("#btn_copy").click(function () {

                   parent.$.etDialog.open({
                       url: '../budgSetDetaddCopy.do?isCheck=false&plan_name=' + '${plan_name}'+'&mod_code='+'${mod_code}'+'&plan_code=' + '${plan_code}'+'&budg_year=' + '${budg_year}'+'&link_code='+'${link_code}',
               			width : 300,
       					height : 400,
                       frameNameObj: {
                           'add': window.name
                       },
                       title: '复制'
                   });
               
        	   })
          $("#btn_addBatch").click(function () {
          
                parent.$.etDialog.open({
                    url: '../budgSetDetaddBatch.do?isCheck=false&plan_code=' + '${plan_code}'+'&mod_code='+'${mod_code}'+'&budg_year=' + '${budg_year}'+'&link_code='+'${link_code}',
            			width : $(window).width()-200,
    					height : $(window).height()-100,
                    frameNameObj: {
                        'add': window.name
                    },
                    title: '批量添加'
                });
            
        });
            $("#btn_batchSave").click(function () {
                
                parent.$.etDialog.open({
                    url: '../budgSetBatch.do?isCheck=false&plan_code=' + '${plan_code}'+'&mod_code='+'${mod_code}'+'&tab_code='+'${tab_code}'+'link_code='+'${link_code}'+'&budg_year=' + '${budg_year}',
            			width : 300,
    					height :400,
                    frameNameObj: {
                        'add': window.name
                    },
                    title: '批量设置'
                });
            
        });
        	//关闭
        	$("#close").click(function () {
                var curIndex = parent.$.etDialog.getFrameIndex(window.name);
                parent.$.etDialog.close(curIndex);
                parent.tree.reAsyncChildNodes(null, 'refresh');
            });
        }
        var query = function () {
      	 
	       
            params = [
                { name: 'budg_year', value: '${budg_year}'},
                { name: 'plan_code', value: '${plan_code}' },
                {name:'mod_code',value:'${mod_code}'},
                {name:'link_code',value:'${link_code}'}

            ];
            child_grid.loadData(params,'queryBudgCDet.do');
        };
        //验证
        function checked(){
        	
        	
        	if(use_nature.getValue()==0){
        		var dataValidate = $.etValidate({
            		config:{},
            		items:[
            			{ el : $('#tab_code'), required : true },
            			{ el : $('#use_nature'), required : true },
            			{ el : $('#cont_note'), required : true },
            		]
            	});
            	
            	
            	
            	
            	
            	return dataValidate.test();
        	}else{
        		var dataValidate = $.etValidate({
            		config:{},
            		items:[
            			{ el : $('#tab_code'), required : true },
            			{ el : $('#use_nature'), required : true },
            			{ el : $('#cont_note'), required : true },
            			{ el : $('#re_link'), required : true },
            			
            		]
            	});
            	
            	
            	
            	
            	
            	return dataValidate.test();
        	}
       }
        
        function makeDis(value){
        	if(value=='0'){
        		$("#max_ed").val('${max_ed}');
        		$("#edName1").show();
        		$("#edName2").show();
        	}else{
        		$("#max_ed").val("");
        		$("#edName1").hide();
        		$("#edName2").hide();
        	}
        }
        
        var initChildCrid= function () {
            var child_columns = [
                           { display: '预算项编码', name: 'item_code', width: 120,editable:false}, 
                           { display: '预算项名称', name: 'item_name', width: 120,
                        	   editor: {
                               type: 'select',
                               keyField: 'item_code',
                               url:'queryBudgModSelect.do?isCheck=false&plan_code='+'${plan_code}',
                          	 change:function(rowdata,celldata){
                          		child_grid.updateRow(celldata.rowIndx,{item_code:rowdata.item_code})
    		          	     },
                           }},
                           { display: '控制层次', name: 'l_name', width: 120 ,
                        	   editor: {
                                   type: 'select',
                                   keyField: 'cont_l',
                                   url: '../../queryContLSelect.do?isCheck=false&tab_code='+tab_code.getValue()+"&cont_m="+cont_m.getValue()+"&budg_year="+'${budg_year}',
                                   change: function (value) {
                                	   child_grid.getColumns()[4].editor.url='../../queryContPSelect.do?isCheck=false&tab_code='+tab_code.getValue()+"&cont_l="+value.cont_l+"&cont_m="+ cont_m.getValue()+"&budg_year="+'${budg_year}';
                                   }
                               }},
                           { display: '控制期间', name: 'p_name', width: 120,
                        	   editor: {
                                   type: 'select',
                                   keyField: 'cont_p',
                                   url: '../../queryContPSelect.do?isCheck=false&tab_code='+tab_code.getValue()+"&cont_l="+ cont_l.getValue()+"&cont_m="+ cont_m.getValue()+"&budg_year="+'${budg_year}',
                                  
                               }},
                           { display: '控制方式', name: 'w_name', width: 120,
                        	   editor: {
                                   type: 'select',
                                   keyField: 'cont_w',
                                   url: '../../queryContWSelect.do?isCheck=false',
                                  
                               }},
                          
                       ];
                       var child_paramObj = {
                          // height: '100%',
                           checkbox: true,
                           editable: true,
                          /*  dataModel: {
                               url: 'queryBudgCDet.do&mod_code='+'${mod_code}'+"&plan_code="+'${plan_code}'+"&budg_year="+'${budg_year}'
                           },  */
                       /*     rowDblClick: function (event, ui) {
                               var rowData = ui.rowData;
                               var openParam = {
                             		  plan_code: rowData.plan_code
                               };

                               openUpdate(openParam);
                           },
                           rowClick:function (event, ui){
                         	  
                           } */
                           columns: child_columns,
                          
                       };
                       child_grid = $("#child_grid").etGrid(child_paramObj);

                     
                       //rowClick
                   };
    </script>
</head>

<body>
<div style="width: 100%;height:200px">
		<table class="table-layout" >
			<tr>
				<td class="label ">预算年度：</td>
				<td class="ipt"><input id="budg_year" type="text" value="${budg_year}" disabled/></td>
				
				<td class="label ">预算方案：</td>
				<td class="ipt"><input id="plan_name" type="text" value="${plan_name}" disabled/></td>
				
				<td class="label ">系统模块：</td>
				<td class="ipt"><input id="mod_name" type="text" value="${mod_name}" disabled/></td>
				
			</tr>
			<tr>
				
				<td class="label ">控制环节</td>
				<td class="ipt">
					<input id="link_name" type="text" value="${link_name}" disabled/></td>
				<td class="label ">预算表<font size="2" color="red">*</font>：</td>
				<td class="ipt"><select id="tab_code" style="width: 180px;"></select></td>
				
				<td class="label ">控制模式：</td>
				<td class="ipt"><select id="cont_m" style="width: 180px;"></select></td>
				
			</tr>
			<tr>
				<td class="label ">控制层次：</td>
				<td class="ipt">
					<select id="cont_l" style="width: 180px;"></select>
				</td>
				
				<td class="label edistd">控制期间：</td>
				<td class="ipt edistd">
					<select id="cont_p" style="width: 180px;"></select>
				</td>
					<td class="label edistd">控制方式：</td>
				<td class="ipt edistd">
					<select id="cont_w" style="width: 180px;"></select>
				</td>
				
			</tr>
			<tr >
				<td class="label ">占用性质<font size="2" color="red">*</font>：</td>
				<td class="ipt"><select id="use_nature" style="width: 180px;"></select></td>
				
				<td class="label ">关联环节：</td>
				<td class="ipt"><select id="re_link" style="width: 180px;"></select></td>
				
			</tr>
			<tr>	<td class="label ">控制节点<font size="2" color="red">*</font>：</td>
				<td class="ipt">
					<select id="cont_note" style="width: 180px;"></select>
				</td>
				<td class="label ">占用状态：</td>
				<td class="ipt">
					<select id="use_state" style="width: 180px;"></select>
				</td>
			</tr>
	</table>
	</div>
	<div>
        <div class="button-group btn">
	        <button id="save">保存</button>
	        <button id="close">关闭</button>
	    </div>
	</div> 
	  <div id="children_block">
                    <div class="grid_header clearfix">
	                    <div class="main_operation" >
	                        <label for="">明细控制方案：</label>
                           <button id='btn_add'>添加行</button>
                          <button id='btn_addBatch'> 批量添加</button>
                            <button id='btn_delete'>删除</button>
                        <button id='btn_batchSave'>批量设置</button>
                         <button id='btn_save'>保存</button>
                          <button id='btn_copy'>复制</button>
                          <div style="float:right;padding-right:10px;" id="child_button_group">
	                        </div>
                            
	                        </div>
	                    </div>
                
                    <div id="child_grid" style="height:170px"> </div>
                        </div>
</body>
</html>