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
    <script type="text/javascript">
    var dataFormat;
    var grid;
    var gridManager = null;
    var selectData = "";
    $(function (){
        loadDict();//加载下拉框
        loadHead(null);//加载表格数据
        query();

		$("#store_name").bind("change",function(){
	    	loadHead();
	    	grid.reRender();
		});
    });
    
    //查询
    function  query(){
    	
    		grid.options.parms=[];
    		grid.options.newPage=1;
        //根据表字段进行添加查询条件
    	  //grid.options.parms.push({name:'store_match_id',value:'${store_match_id}'}); 
        
    	  //grid.options.parms.push({name:'store_id',value:liger.get("store_name").getValue().split(",")[0]}); 

    	//加载查询条件
    	grid.loadData(grid.where);
     }
   
    
    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
           columns: [ 
	                     { display: '药品编码', name: 'inv_code', align: 'left'
						 },
					 		{ display: '药品名称',
		  					     name: 'inv_name',
		  					     align: 'left',
		  					     textField : 'inv_name',
		  					     valueField : 'inv_name',
		  					     editor :{
										type : 'select',
										textField : 'inv_name',
										valueField : 'inv_name',
										selectBoxWidth : 500,
										selectBoxHeight : 240,
										grid : {
											columns : [ {
												display : '药品编码',
												name : 'inv_code',
												align : 'left'
											}, {
												display : '药品名称',
												name : 'inv_name',
												align : 'left'
											}, {
												display : '规格型号',
												name : 'inv_model',
												align : 'left'
											}, {
												display : '计量单位',
												name : 'unit_name',
												align : 'left'
											} 
											],
											switchPageSizeApplyComboBox : false,
											onSelectRow: function (data) {
												var e = window.event;
												if (e && e.which == 1) {
													f_onSelectRow_detail(data);
												}
											},
											url : '../../queryMedInvList.do?isCheck=false&store_id=' + liger.get("store_name").getValue().split(",")[0],
											//delayLoad:true,
											usePager:true,
											pageSize : 30,
											onSuccess: function (data, g) { //加载完成时默认选中
												if (grid.editor.editParm) {
													var editor = grid.editor.editParm.record;
													var item = data.Rows.map(function (v, i) {
														return v.inv_name;
													});
													var index = item.indexOf(editor.inv_name) == -1 ? 0 : item.indexOf(editor.inv_name);
													//加载完执行
													setTimeout(function () {
														g.select(data.Rows[index]);
													}, 80);
												}
											}
					  					    },
											keySupport : true,
											autocomplete : true,
											onSuccess : function() {
												this.parent("tr").next(
													".l-grid-row").find(
													"td:first").focus();
											},
											ontextBoxKeyEnter: function (data) {
												f_onSelectRow_detail(data.rowdata);
											}
		  					     		}
		  					   },
                     { display: '规格型号', name: 'inv_model', align: 'left'
					 		},
		             { display: '计量单位', name: 'unit_name', align: 'left'
						 	},
				     { display: '数量', name: 'amount', align: 'left',editor:{type:'int'}
							}
					
                     ],
                     dataAction: 'server',dataType: 'server',usePager:true,url:'queryMedSmmDetailByCode.do?isCheck=false&store_match_id='+'${store_match_id}',
                     width: '100%', height: '100%', checkbox: true,rownumbers:true,enabledEdit:true,
                     alternatingRow : true,onBeforeEdit : f_onBeforeEdit,
                     onBeforeSubmitEdit : f_onBeforeSubmitEdit,onAfterEdit : f_onAfterEdit,
                     isScroll : true,
                     selectRowButtonOnly:true,//heightDiff: -10,
                     toolbar: { items: [
				    	         { text: '删除', id:'delete', click: deleteRow,icon:'delete' },{ line:true },
				    	         { text: '添加行（<u>C</u>）', id:'add', click: addCenterRow, icon:'add' },{ line:true }
				     ]}
                   });

        gridManager = $("#maingrid").ligerGetGridManager();
       
    }
    
    var rowindex_id = "";
	 var column_name="";
	 function f_onBeforeEdit(e) {
		 rowindex_id = e.rowindex;
		 column_name=e.column.name;
	 }
	
	
	
 	 //选中回充数据
	function f_onSelectRow_detail(data, rowindex, rowobj) {

		selectData = "";
		selectData = data;
		if (column_name == "inv_name") {
			if (selectData != "" || selectData != null) {
				//回充数据 
				//grid.updateCell('apply_emp', 100, e.record);
				grid.updateRow(
						rowindex_id,
						{inv_code : data.inv_code,
						inv_name : data.inv_name,
						inv_model : (data.inv_model == "" || data.inv_model == undefined) ? '': data.inv_model,
						unit_name : data.unit_name,
						inv_id:data.inv_id
				});

			}
		}
			return true;
	}
		
 	 function f_onSelectRow(data, rowindex, rowobj) {
			
			return true;
	 }
		
		// 编辑单元格提交编辑状态之前作判断限制
		function f_onBeforeSubmitEdit(e) {
			
			return true;
		}
		// 跳转到下一个单元格之前事件
		function f_onAfterEdit(e) {
			
			return true;
		}
    
    function itemclick(item){ 
        if(item.id)
        {
            switch (item.id)
            {
                case "delete":
                    var data = gridManager.getCheckedRows();
                    if (data.length == 0){
                    	$.ligerDialog.error('请选择行');
                    }else{
                    	 var Param =[];
                         $(data).each(function (){					
 							Param.push(
 							//表的主键
 							this.group_id+"@"+
 							this.hos_id+"@"+
 							this.copy_code+"@"+
 							this.store_match_id
 							)
                         });
                       $.ligerDialog.confirm('确定删除?', function (yes){
                        	if(yes){
                            	ajaxJsonObjectByUrl("deleteMedStoreMatch.do?isCheck=false&paramVo="+Param,{},function (responseData){
                            		if(responseData.state=="true"){
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
    
    //手动添加新行
	function addCenterRow(){ 
		
		gridManager.addRow();
		
    }
    
	//更新数据
    function save(){
        if($("#store_match_code").val() ==  ''){
    		$.ligerDialog.error('配套表编码不能为空');
    		return;
    	}
		
    	if($("#store_match_name").val() ==  ''){
    		$.ligerDialog.error('配套表名称不能为空');
    		return;
    	}
    	
		var store_match_name;
		if($("#store_match_name").val() == '${store_match_name}'){
			store_match_name = '';
		}else{
			store_match_name = $("#store_match_name").val();
		}
    	
    	if(liger.get("store_name").getValue().split(",")[0] == ''){
    		$.ligerDialog.error('仓库名称不能为空');
    		return;
    	}
    	
    	
    	var allData = gridManager.getData();
    	var flag = true;
    	var rows=0;
    	//非空判断
	    $(allData).each(function(){
	    	if(this.inv_id){
				if(this.inv_name == undefined || this.inv_name == ''){
	    			$.ligerDialog.error('请选择药品名称');
	    			return flag = false;
	    		}
	    		
	    		if(this.amount == undefined || this.amount == ''){
	    			$.ligerDialog.error('请填写数量');
	    			return flag = false;
	    		}
	    		rows++;
	    	}
	    		
	    });
	    	
	    if(flag == false){
	    	return;
	    }
    	
	    if(rows == 0){
 			$.ligerDialog.warn("请先添加药品！");  
			return false;  
 		}	
	    
    	
    	var formPara = {
    			store_match_id:'${store_match_id}',//配套表ID
				
    			store_id:liger.get("store_name").getValue().split(",")[0],//当前仓库ID
    			
    			store_match_code:$("#store_match_code").val(),//当前配套表名称
    			
    			store_match_name:store_match_name,//当前配套表名称
    			
    			allData : JSON.stringify(allData),//获取所有数据
    			
    		};

       
        ajaxJsonObjectByUrl("updateMedStoreMatch.do",formPara,function(responseData){
            
            if(responseData.state=="true"){
                parent.query();
            }
        });
    }
     
    function loadForm(){
    
    $.metadata.setType("attr", "validate");
     var v = $("form").validate({
         errorPlacement: function (lable, element)
         {
             if (element.hasClass("l-textarea"))
             {
                 element.ligerTip({ content: lable.html(), target: element[0] }); 
             }
             else if (element.hasClass("l-text-field"))
             {
                 element.parent().ligerTip({ content: lable.html(), target: element[0] });
             }
             else
             {
                 lable.appendTo(element.parents("td:first").next("td"));
             }
         },
         success: function (lable)
         {
             lable.ligerHideTip();
             lable.remove();
         },
         submitHandler: function ()
         {
             $("form .l-text,.l-textarea").ligerHideTip();
         }
     });
     $("form").ligerForm();
    }       
   	
    //保存数据
    function saveStoreMatchDetail(){
    	
    	grid.endEdit();
            save();
    }
    function loadDict(){
        //字典下拉框
		
		autocomplete("#store_name","../../queryMedStore.do?isCheck=false","id","text",true,true,'',false,"","","",220);//仓库信息
		liger.get("store_name").setValue("${store_id},${store_no}");
        liger.get("store_name").setText("${store_code}  ${store_name}");
        
		$("#store_match_code").ligerTextBox({width:160,disabled:true}); 
        $("#store_match_name").ligerTextBox({width:160});
     } 
    
    //删除选中行
	function deleteRow(){
		
		gridManager.deleteSelectedRow();
    }
    
	function is_addRow() {
		var allData = gridManager.getData();
		if(allData.length > 0){
			return;
		}
		setTimeout(function() { //当数据为空时 默认新增一行
			grid.addRow();
		}, 100);

    }
    </script>
  
  </head>
  
   <body onload="is_addRow()">
   <div id="pageloading" class="l-loading" style="display: none"></div>
   <form name="form1" method="post"  id="form1" >
        <table cellpadding="0" cellspacing="0" class="l-table-edit" >
		
        <tr>
         <td align="right" class="l-table-edit-td"  style="padding-left:20px;">配套表编码：</td>
            <td align="left" class="l-table-edit-td"><input name="store_match_code" type="text" id="store_match_code" disabled="disabled" validate="{required:true,maxlength:20}" value="${store_match_code}"/></td>
            <td align="left"></td>
        <td align="right" class="l-table-edit-td"  style="padding-left:20px;">配套表名称：</td>
            <td align="left" class="l-table-edit-td"><input name="store_match_name" type="text" id="store_match_name" validate="{required:true,maxlength:20}" value="${store_match_name}"/></td>
            <td align="left"></td>
        </tr> 
        <tr>
            
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">仓库名称：</td>
            <td align="left" class="l-table-edit-td"><input name="store_name" type="text" id="store_name" ltype="text" validate="{required:true,maxlength:20}"/></td>
            <td align="left"></td>
        </tr> 
			
        </table>
        
    </form>
        <div id="maingrid"></div>
    </body>
</html>
