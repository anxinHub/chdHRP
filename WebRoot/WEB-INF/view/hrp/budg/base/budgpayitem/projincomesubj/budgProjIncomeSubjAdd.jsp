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
    var budg_year ;
    $(function (){
    	loadForm();
        loadDict();
        loadHead();
        
        $("#budg_year").change(function(){
        	
        	budg_year  = liger.get("budg_year").getValue();
        	
        	liger.get("subj_code").setValue('');
        	
        	liger.get("subj_code").setText('');
        	
        	//预算科目下拉框（subj_type 04 收入预算科目 ，05 支出预算科目 ）
            autocomplete("#subj_code","../../../queryBudgSubj.do?isCheck=false&subj_type=04&is_last=1&budg_year="+budg_year,"id","text",true,true,'',false,'',200);
        	
        	query();
        })
        $("#fund_nature").change(function(){
        		query();
        })
        $("#subj_code").change(function(){
        		query();
        })
         $("#type_code").change(function(){
        	
        	query();
        })
         $("#level_code").change(function(){
        	
        	query();
        })
        $("#proj_state").change(function(){
        	
        	query();
        })
        $("#proj_id").change(function(){
        	
        	query();
        })
        $("#con_emp_id").change(function(){
        	
        	query();
        })
    });  
    
    //查询
    function  query(){
    	
    	budg_year  = liger.get("budg_year").getValue();
    	
    	var fund_nature = liger.get("fund_nature").getValue();
    	
    	if(!budg_year){
    		
    		$.ligerDialog.warn('预算年度不能为空');
    		return false ;
    	}
    	if(!fund_nature){
    		$.ligerDialog.warn('资金不能为空');
    		return false ; 
    	}
   		grid.options.parms=[];
   		grid.options.newPage=1;
   		
        //根据表字段进行添加查询条件
        grid.options.parms.push({name:'budg_year',value:liger.get("budg_year").getValue()}); 
		grid.options.parms.push({name:'fund_nature',value:liger.get("fund_nature").getValue()}); 
		grid.options.parms.push({name:'subj_code',value:liger.get("subj_code").getValue()});
		grid.options.parms.push({name:'type_code',value:liger.get("type_code").getValue()});
		grid.options.parms.push({name:'level_code',value:liger.get("level_code").getValue()});
		grid.options.parms.push({name:'proj_state',value:liger.get("proj_state").getValue()});
		grid.options.parms.push({name:'proj_id',value:liger.get("proj_id").getValue().split(",")[0]});
		grid.options.parms.push({name:'con_emp_id',value:liger.get("con_emp_id").getValue()});

    	//加载查询条件
    	grid.loadData(grid.where);
     }

    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
           columns: [ 
					{ display: '', name: 'budg', align: 'center',width:50,
							render: function(rowdata, rowindex,value) {
									return "<input id=is_check"+rowdata.proj_id+"  type ='checkbox' style='margin-top:8px;'>";
							}
						},
                     { display: '项目名称', name: 'proj_name', align: 'left',width:300
                    	
					 	},
                     { display: '项目类型', name: 'type_name', align: 'left'
					 		},
					 { display: '项目级别', name: 'level_name', align: 'left'
					 		},
                     { display: '项目负责人', name: 'emp_name', align: 'left'
					 		},
					 { display: '立项日期', name: 'set_up_date', align: 'left'
					 		},
                     { display: '结项日期', name: 'complete_date', align: 'left'
					 		},
					 { display: '报销截止日期', name: 'pay_end_date', align: 'left'
					 		},
					 { display: '中止日期', name: 'sespend_date', align: 'left'
					 		} ,
					 { display: '项目状态', name: 'proj_state_name', align: 'left'
					 		}
					 		
                     ],
                     dataAction: 'server',dataType: 'server',usePager:false,url:'queryBudgProjDict.do?isCheck=false',
                     width: '100%', height: '100%', checkbox: false,rownumbers:true,
                     selectRowButtonOnly:true,//heightDiff: -10,
                     delayLoad:true,
                     onAfterShowData : initCheckEnable, 
                     toolbar: { items: [
                     	{ text: '查询（<u>Q</u>）', id:'search', click: query,icon:'search' },
                     	{ line:true },
    					{ text: '保存（<u>S</u>）', id:'save', click: saveBudgProjIncomeSubj, icon:'save' }
    				]},
    				
                   });

        gridManager = $("#maingrid").ligerGetGridManager();
    }
    
    //
    function initCheckEnable(){
    	var subj_code = liger.get("subj_code").getValue();
    	var fund_nature = liger.get("fund_nature").getValue();
    	
		var initData = grid.getData();
		
		$(initData).each(function(){
			
			if(this.subj_code && fund_nature && subj_code ){ //存在对应关系时
				
				if(this.fund_nature == fund_nature ){
					
					if(this.subj_code ==  subj_code ){ // 与当前收入预算科目存在对应关系时 选中
						
						$("#is_check"+this.proj_id+"").prop("checked",true);
						
	 					$("#is_check"+this.proj_id+"").prop("disabled",false);
	 					
					}else{//否则 禁止选择 
						$("#is_check"+this.proj_id+"").prop("checked",false);
						
	 					$("#is_check"+this.proj_id+"").prop("disabled",true);
					}
					
				}else{
					
					$("#is_check"+this.proj_id+"").prop("checked",false);
					
					$("#is_check"+this.proj_id+"").prop("disabled",false);
					
				}
				
			}else{
				
				$("#is_check"+this.proj_id+"").prop("checked",false);
				
				$("#is_check"+this.proj_id+"").prop("disabled",false);
				
			}
			
		})
	}
    function save(){
    	var budg_year = liger.get("budg_year").getValue();
         
        var fund_nature = liger.get("fund_nature").getValue();
         
        var subj_code = liger.get("subj_code").getValue() ;
    	
    	var data = gridManager.getData();
    	var ParamVo =[];
    	
        $(data).each(function (){	
        	if($("#is_check"+this.proj_id+"").prop("checked")){
				ParamVo.push(
						budg_year   +"@"+ 
						fund_nature   +"@"+ 
						subj_code +"@"+ 
						this.proj_id
				) 
        	}
		});
        
        if(ParamVo.length > 0 ){
        	ajaxJsonObjectByUrl("addBudgProjIncomeSubj.do?isCheck=false",{ParamVo : ParamVo.toString()},function(responseData){
                
                if(responseData.state=="true"){
                	
                    parentFrameUse().query();
                    
                    parentFrameUse().$.ligerDialog.success('保存成功'); 
                    
                    frameElement.dialog.close();
                }
            });
        	
        }else{
        	
        	var formPara={
        	        budg_year : budg_year ,
        	        fund_nature : fund_nature ,
        	        subj_code : subj_code
        	 	}; 
        	
			ajaxJsonObjectByUrl("deleteBudgProjIncomeSubjData.do?isCheck=false",formPara,function(responseData){
                
                if(responseData.state=="true"){
                	 parentFrameUse().query();
                     
                	 parentFrameUse().$.ligerDialog.success('保存成功');
                     
                     frameElement.dialog.close();
                }
            });
        	
        }
        
        
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
	           //提示信息  显示2秒后消失
	             setTimeout(function(){
	            	  lable.ligerHideTip();
	                  lable.remove();
	             },2000)
	         },
	         success: function (lable)
	         {
	             lable.ligerHideTip();
	             lable.remove();
	         },
	         submitHandler: function ()
	         {
	             $("form .l-text,.l-textarea").ligerHideTip();
	         },
	         ignore: ".notValid"
	     });
	    
	    //$("form").ligerForm();
	}       
   
    function saveBudgProjIncomeSubj(){
        if($("form").valid()){
            save();
        }
    }
    function loadDict(){
        //字典下拉框/
        
		autocompleteAsync("#budg_year","../../../queryBudgYear.do?isCheck=false","id","text",true,true,'',true,'',200);
        
        budg_year = liger.get("budg_year").getValue();
        
        //预算科目下拉框（subj_type 04 收入预算科目 ，05 支出预算科目 ）
        autocomplete("#subj_code","../../../queryBudgSubj.do?isCheck=false&subj_type=04&is_last=1&budg_year="+budg_year,"id","text",true,true,'',false,'',200);
       	
        autocomplete("#fund_nature","../../../../sys/querySysSourceNature.do?isCheck=false","id","text",true,true,'',true,'',200); 
        
        //项目类型 下拉框
        autocomplete("#type_code","../../../queryBudgProjType.do?isCheck=false","id","text",true,true,'',false,'',200); 
        
        //项目级别 下拉框
        autocomplete("#level_code","../../../queryBudgProjLevel.do?isCheck=false","id","text",true,true,'',false,'',200); 
        
        //项目状态 下拉框
        autocomplete("#proj_state","../../../qureyProjStateSelect.do?isCheck=false","id","text",true,true,'',false,'',200); 
        
        //项目名称 下拉框
        autocomplete("#proj_id","../../../queryProjName.do?isCheck=false","id","text",true,true,'',false,'',200); 
        
        //项目负责人 下拉框
        autocomplete("#con_emp_id ","../../../queryConEmpId.do?isCheck=false","id","text",true,true,'',false,'',200); 
       
        
        //字典下拉框
        $("#budg_year").ligerTextBox({width:200});
        $("#fund_nature").ligerTextBox({width:200});
        $("#subj_code").ligerTextBox({width:200});
        
     }   
    </script>
  
  </head>
  
   <body>
   <div id="pageloading" class="l-loading" style="display: none"></div>
   <form name="form1" method="post"  id="form1" >
        <table cellpadding="0" cellspacing="0" class="l-table-edit" >
	        <tr>
	            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b>预算年度<font color="red">*</font>:</b></td>
	            <td align="left" class="l-table-edit-td"><input name="budg_year" type="text" id="budg_year" ltype="text"  validate="{required:true,maxlength:20}" /></td>
	            <td align="left"></td>
	            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b>资金性质<font color="red">*</font>:</b></td>
	            <td align="left" class="l-table-edit-td"><input name="fund_nature" type="text" id="fund_nature" ltype="text"   validate="{required:true,maxlength:20}" /></td>
	            <td align="left"></td>
	        
	            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b>收入预算科目<font color="red">*</font>:</b></td>
	            <td align="left" class="l-table-edit-td"><input name="subj_code" type="text" id="subj_code" ltype="text"  validate="{required:true,maxlength:20}" /></td>
	            <td align="left"></td>
	        </tr> 
        </table>
        <hr />
        <table cellpadding="0" cellspacing="0" class="l-table-edit" >
	        <tr>
	            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b>项目类型:</b></td>
	            <td align="left" class="l-table-edit-td"><input name="type_code" type="text" id="type_code" ltype="text" /></td>
	            <td align="left"></td>
	            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b>项目级别:</b></td>
	            <td align="left" class="l-table-edit-td"><input name="level_code" type="text" id="level_code" ltype="text"  /></td>
	            <td align="left"></td>
	        
	            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b>项目状态:</b></td>
	            <td align="left" class="l-table-edit-td"><input name="proj_state" type="text" id="proj_state" ltype="text" /></td>
	            <td align="left"></td>
	        </tr> 
	        <tr>
	            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b>项目名称:</b></td>
	            <td align="left" class="l-table-edit-td"><input name="proj_id" type="text" id="proj_id" ltype="text"  /></td>
	            <td align="left"></td>
	            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b>项目负责人:</b></td>
	            <td align="left" class="l-table-edit-td"><input name="con_emp_id" type="text" id="con_emp_id" ltype="text" /></td>
	            <td align="left"></td>
	        </tr> 
        </table>
    </form>
    <div id="maingrid"></div>
    </body>
</html>
