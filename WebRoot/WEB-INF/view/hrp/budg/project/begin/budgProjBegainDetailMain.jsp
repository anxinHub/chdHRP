<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="${path}/inc_jquery_1.9.0.jsp" />
 <style>
        
        input {
            border: 1px solid #aecaf0;
            height: 20px;
        }
        dl dt,
        dl dd {
            display: inline-block;
        }
        
        .table-star {
            color: red;
        }
        .form-group {
            margin: 15px 0;
        }
        .add_button,
        .delete_button {
            color: #0066cc;
            text-decoration: underline;
            cursor: pointer;
        }
        .remark_form {
            margin: 20px;
        }
        .message {
            margin: 10px 0;
            padding: 10px 0;
            text-align: right;
            border-bottom: 1px solid #a3c0e8;
            border-top: 1px solid #a3c0e8;
        }
        .message dd {
            margin-right: 40px;
        }
        .button-group {
            margin: 10px 0;
            padding-left: 10px;
        }
        .button-group > input[type="button"] {
            padding: 2px 30px;
            min-width: 90px;
            height: 30px;
            box-sizing: border-box;
            margin-right: 25px;
            cursor: pointer;
            font-weight: bold;
            background: #e0edff;
            border: 1px solid #a3c0e8;
        }
        .button-group > input[type="button"]:hover {
            background: #ffbe76;
        }
        
    </style>
   <script>
    var grid;
    var gridManager = null;
    var userUpdateStr;
    var proj_id;
    $(function ()
    {
        loadDict()//加载下拉框
    	//加载数据
    	 loadHead(null);	

		 loadHotkeys();
    })
    //查询
    function  query(){
    		grid.options.parms=[];
    		grid.options.newPage=1;
        //根据表字段进行添加查询条件
    	  grid.options.parms.push({name:'budg_year',value:$("#budg_year").val()}); 
    	  grid.options.parms.push({name:'proj_id',value:$("#proj_id").val()}); 
    	  grid.options.parms.push({name:'source_id',value:$("#source_id").val()}); 

    	//加载查询条件
    	grid.loadData(grid.where);
		$("#resultPrint > table > tbody").empty();
     }

    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
           columns: [ 
                     { display: '资金来源', name: 'source_name', align: 'left',valueField:'id',textField:'text',
		        		   editor : {
								type : 'select',
								valueField : 'id',
								textField : 'text',
								url : 'queryBudgSourceName.do?isCheck=false',
								keySupport : true,
								autocomplete : true,
							} 
					 		},   
                     { display: '预算金额', name: 'budg_amount', align: 'left',editor : {type : 'float'},
					 		},
                     { display: '到账金额', name: 'in_amount', align: 'left',editor : {type : 'float'},
					 		},
                     { display: '支出金额', name: 'cost_amount', align: 'left',editor : {type : 'float'},
					 		},
                     { display: '预算余额', name: 'remain_amount', align: 'left',editor : {type : 'float'},
					 		}
                     ],    
                     dataAction: 'server',dataType: 'server',usePager:true,url:'queryBeginProject.do?isCheck=false',
                     width: '100%', height: '40%', checkbox: true,rownumbers:true,
                     selectRowButtonOnly:true,
                     toolbar: { items: [
						            	{ text: '查询（<u>E</u>）', id:'search', click: query,icon:'search' },
						            	{ line:true },
				    					{ text: '添加（<u>A</u>）', id:'add', click: add_open, icon:'add' },
				    	                { line:true },
				    	                { text: '删除（<u>D</u>）', id:'delete', click: del,icon:'delete' },
						                { line:true }
				    				]},
    				onDblClickRow : function (rowdata, rowindex, value)
    				{
						openUpdate(
								rowdata.group_id   + "|" + 
								rowdata.hos_id   + "|" + 
								rowdata.copy_code   + "|" + 
								rowdata.budg_year   + "|" + 
								rowdata.proj_id   + "|" + 
								rowdata.source_id   + "|" + 
								rowdata.payment_item_id   + "|" + 
								rowdata.payment_item_no 
							);
    				} 
                   });
    	
    	  gridManager = $("#maingrid").ligerGetGridManager();
    	
    }
 
    function add_open(){
    	 $("body").keydown(function() {
	            if (event.keyCode == "9") {//keyCode=9是Tab
	             grid.addRowEdited({
	               	source_id: '' ,
	                budg_amount:0,
	                in_amount:0,
	                cost_amount:0,
	                remain_amount:0
	         		});
	             }          
	        });
		
    	}
    function del(){
      	 var data = grid.getCheckedRows();
     		 if(data.length == 0){
     				$.ligerDialog.error('请选择行');
                  return;
              }else{
              	 for (var i = 0; i < data.length; i++){
              		 grid.remove(data[i]);
                   } 
              }
      }
    function Save(){
    	 var data = gridManager.getData();
       	 var data1 = grid.getCheckedRows();
           budg_year = liger.get("budg_year").getValue();
		   proj_id=liger.get("proj_id").getValue();
    	var ParamVo=[];
    	if(data1.length==0){		
    		$.ligerDialog.error('请选择行');
			return;
    	}
    	$(data1).each(function (){					
    		ParamVo.push(
    				budg_year+"@"+
    				proj_id+"@"+    				
    				this.source_id +"@"+
					this.budg_amount   +"@"+ 
					this.in_amount   +"@"+ 
					this.cost_amount   +"@"+ 
					this.remain_amount  
				) 
        });
			$.ligerDialog.confirm('确定要保存吗?', function (yes){
				if(yes){//isCheck=true;可以根据不同用户来让他进行不同的操作,一般不可建议使用
					ajaxJsonObjectByUrl("saveBudgProjBegain.do?isCheck=false",{ParamVo : ParamVo.toString()},function (responseData){
						if(responseData.state=="true"){
							query();
						}
					});
				}
			}); 
		} 
    	
   function OutProjectSet(){
	   proj_id=liger.get("proj_id").getValue();
		 $.ligerDialog.open({ url : 'outProjectSet.do?isCheck=false&proj_id='+proj_id,data:{}, height:550,width:1350, 
             title:'期初项目预算',modal:true,showToggle:false,showMax:false,showMin: false,isResize:true
   	}); 
	   
   }
    function remove(){
    	
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
								this.budg_year   +"@"+ 
								this.proj_id   +"@"+ 
								this.source_id   +"@"+ 
								this.payment_item_id   +"@"+ 
								this.payment_item_no 
							) });
                        $.ligerDialog.confirm('确定删除?', function (yes){
                        	if(yes){
                            	ajaxJsonObjectByUrl("deleteBudgProjBegainDetail.do",{ParamVo : ParamVo.toString()},function (responseData){
                            		if(responseData.state=="true"){
                            			query();
                            		}
                            	});
                        	}
                        }); 
                    }
    	}
    function imp(){
    	
    	var index = layer.open({
					type : 2,
					title : '导入',
					shadeClose : false,
					shade : false,
					maxmin : true, //开启最大化最小化按钮
					area : [ '893px', '500px' ],
					content : 'budgProjBegainDetailImportPage.do?isCheck=false'
				});
				layer.full(index);
    	}	
    function downTemplate(){
    	
    	location.href = "downTemplate.do?isCheck=false";
    	}	
   
    function openUpdate(obj){
    		
		var vo = obj.split("|");
		var parm = 
			"group_id="+vo[0]   +"&"+ 
			"hos_id="+vo[1]   +"&"+ 
			"copy_code="+vo[2]   +"&"+ 
			"budg_year="+vo[3]   +"&"+ 
			"proj_id="+vo[4]   +"&"+ 
			"source_id="+vo[5]   +"&"+ 
			"payment_item_id="+vo[6]   +"&"+ 
			"payment_item_no="+vo[7] 
		 
		 
		 $.ligerDialog.open({ url : 'budgProjBegainDetailUpdatePage.do?isCheck=false&',data:{}, height: 300,width: 450, title:'期初项目预算明细',modal:true,showToggle:false,showMax:false,showMin: false,isResize:true,
			buttons: [ { text: '确定', onclick: function (item, dialog) { dialog.frame.savebudgProjBegainDetail(); },cls:'l-dialog-btn-highlight' },
			           { text: '取消', onclick: function (item, dialog) { dialog.close(); } } 
			          ]
    	}); 
    }
  
    function loadDict(){
            //字典下拉框
    	  //加载年度   
        autocomplete("#budg_year","../../queryBudgYear.do?isCheck=false","id","text",true,true,'',true);  
      //加载项目名称
        autocomplete("#proj_id","../../queryProjName.do?isCheck=false","id","text",true,true,'',true); 
      //加载项目负责人
        autocomplete("#con_emp_id","../../queryConEmpId.do?isCheck=false","id","text",true,true,'',true);  
        $("#budg_year").ligerTextBox({width:160});
        $("#proj_id").ligerTextBox({width:160});
        $("#source_id").ligerTextBox({width:160});
        
         }  
    //键盘事件
	  function loadHotkeys() {

		hotkeys('Q', query);

		hotkeys('A', add);
		hotkeys('D', remove);

		hotkeys('B', downTemplate);

		hotkeys('E', exportExcel);

		hotkeys('P', printDate);
		hotkeys('I', imp);
		

	 }
 	  
</script>

</head>

<body style="padding: 0px; overflow: hidden;">
	<div id="pageloading" class="l-loading" style="display: none"></div>

	<div id="toptoolbar" ></div>
	<div>
    <table cellpadding="0" cellspacing="0" class="l-table-edit" >
        <tr>
        </tr> 
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">预算年度：<span class="table-star">*</span> :</td>
            <td align="left" class="l-table-edit-td"><input name="budg_year" type="text" id="budg_year" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">项目名称：<span class="table-star">*</span> :</td>
            <td align="left" class="l-table-edit-td"><input name="proj_id" type="text" id="proj_id" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">项目负责人：<span class="table-star">*</span> :</td>
            <td align="left" class="l-table-edit-td"><input name="con_emp_id" type="text" id="con_emp_id" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
        </tr> 
        <tr>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">预算金额：</td>
            <td align="left" class="l-table-edit-td"><input name="budg_amount" type="text" id="budg_amount" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">到账金额：</td>
            <td align="left" class="l-table-edit-td"><input name="in_amount" type="text" id="in_amount" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">支出金额：</td>
            <td align="left" class="l-table-edit-td"><input name="cost_amount" type="text" id="cost_amount" ltype="text" validate="{required:true,maxlength:20}" /></td>
            <td align="left"></td>
        </tr>     
    </table>
    
	<div id="maingrid"></div>
	<div style="padding:10px 50px">
		<span style="padding:0 20px">审核人：${checker}</span>
		<span style="padding:0 20px">审核日期：${check_date}</span>
		<span style="padding:0 20px">：状态：${state}</span>
	</div>
	<div class="button-group">
		<input name="保存"  type="button" value="保存" onclick="Save();"/>
		<input name="支出项目设置"   type="button" value="支出项目设置" onclick="OutProjectSet();"/>
	</div>
	<div id="detailgrid"></div>
</body>
</html>
