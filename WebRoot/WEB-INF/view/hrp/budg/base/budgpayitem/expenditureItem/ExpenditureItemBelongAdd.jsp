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
    var up_id = '${up_id}';
	var up_text = '${up_text}';
    var up_id2 = '${up_id2}';
	var up_text2 = '${up_text2}';
    
    $(function (){
        loadDict();
        loadHead();
        loadForm();
        
        if(up_id != null && up_id != ''){
        	query();
        	
        }
    });  
    
    //查询
    function  query(){
    	
    	var duty_dept_name = liger.get("duty_dept_name").getValue()
    	var payment_item_name = liger.get("payment_item_name").getValue()

		
    	if(duty_dept_name == '' || payment_item_name == ''){
    		$.ligerDialog.warn('请选择归口科室或支出项目！'); 
    		return;
    	}
    	
   		grid.options.parms=[];
   		grid.options.newPage=1;
   		
        //根据表字段进行添加查询条件
        grid.options.parms.push({name:'duty_dept_id',value:duty_dept_name}); 
        grid.options.parms.push({name:'payment_item_id',value:payment_item_name}); 
        
        grid.options.parms.push({name:'acc_dept_type',value:liger.get("acc_dept_type").getValue()}); 
		grid.options.parms.push({name:'hos_dept_kind',value:liger.get("hos_dept_kind").getValue()}); 
		grid.options.parms.push({name:'acc_dept_natur',value:liger.get("acc_dept_natur").getValue()});
		grid.options.parms.push({name:'acc_dept_out',value:liger.get("acc_dept_out").getValue()});

    	//加载查询条件
    	grid.loadData(grid.where);
     }

    function loadHead(){
    	grid = $("#maingrid").ligerGrid({
           columns: [
                     { display: '部门类型', name: 'type_name', align: 'left',
					 	},
                     { display: '部门编码', name: 'dept_code', align: 'left'
					 		},
					 { display: '部门名称', name: 'dept_name', align: 'left'
					 		},
                     { display: '部门分类', name: 'kind_name', align: 'left'
					 		},
					 { display: '部门性质', name: 'natur_name', align: 'left'
					 		},
                     { display: '支出性质', name: 'out_name', align: 'left'
					 		}
                     ],
                     dataAction: 'server',dataType: 'server',usePager:false,url:'addQueryExpenditureItemBelong.do?isCheck=false',
                     width: '100%', height: '100%', checkbox: true,rownumbers:true,
                     isChecked : function(item){
                 		if (item.ischeck) {
                			return true;
                		} else {
                			return false;
                		}
                     },
                     selectRowButtonOnly:true,//heightDiff: -10,
                     delayLoad:true,
                     checkBoxDisplay:function(item){
         				if (item.isdisable) {
         					return false;
         				} else {
         					return true;
         				}
         			},
                     toolbar: { items: [
                     	{ text: '查询（<u>Q</u>）', id:'search', click: query,icon:'search' },
                     	{ line:true },
    					{ text: '保存（<u>S</u>）', id:'save', click: save, icon:'save' }
    				]},
    				
                   });

        gridManager = $("#maingrid").ligerGetGridManager();
    }

	function initCheck(e) {
		if (e.ischeck) {
			return true;
		} else {
			return false;
		}
	}

    function save(){
		if (!$("form").valid()) {
			return;
		}
		
    	var duty_dept_id = liger.get("duty_dept_name").getValue()
    	var payment_item_id = liger.get("payment_item_name").getValue()
    	
    	if(duty_dept_id == '' || duty_dept_id == null || payment_item_id == '' || payment_item_id == null ){
    		$.ligerDialog.error('请选择归口科室或支出项目！');
    		return;
    	}
    	
		var data = grid.getSelectedRows();
		if (data.length == 0) {
			$.ligerDialog.error('请选择行数据后再保存！');
			return false;
		}
		
    	var ParamVo =[];
    	//console.info(data);
		$(data).each(function(){
			ParamVo.push(this.dept_id)
		});
        //console.info(ParamVo);

        	
       	var formPara={
       			ParamVo : ParamVo.toString(),
       			duty_dept_id : duty_dept_id ,
       			payment_item_id : payment_item_id
       	 	}; 
       	
		ajaxJsonObjectByUrl("addExpenditureItemBelongData.do?isCheck=false",formPara,function(responseData){
			if(responseData.state == 'true'){
				query();
			}
		});
        	
        
        
    }
     
	function loadForm() {
		$.metadata.setType("attr", "validate");
		var v = $("form").validate({
			errorPlacement : function(lable, element) {
				if (element.hasClass("l-textarea")) {
					element.ligerTip({
						content : lable.html(),
						target : element[0]
					});
				} else if (element.hasClass("l-text-field")) {
					element.parent().ligerTip({
						content : lable.html(),
						target : element[0]
					});
				} else {
					lable.appendTo(element.parents("td:first").next("td"));
				}
			},
			success : function(lable) {
				lable.ligerHideTip();
				lable.remove();
			},
			submitHandler : function() {
				$("form .l-text,.l-textarea").ligerHideTip();
			}
		});
		$("form").ligerForm();
	}
    function loadDict(){
        //字典下拉框/

    	if(up_id != null && up_id != ''){
    		//归口科室名称
    		autocomplete("#duty_dept_name","queryDutyDept.do?isCheck=false","id","text",true,true,'',false);
            //支出项目：
    		autocomplete("#payment_item_name","queryPaymentItem.do?isCheck=false","id","text",true,true,'',false);
            
            
    		liger.get("duty_dept_name").setValue(up_id);
            liger.get("duty_dept_name").setText(up_text);
            liger.get("duty_dept_name").setDisabled(true);
            
    		liger.get("payment_item_name").setValue(up_id2);
            liger.get("payment_item_name").setText(up_text2);
            liger.get("payment_item_name").setDisabled(true);
            
            $("#duty_dept_name").ligerTextBox({disable:true});
            $("#payment_item_name").ligerTextBox({width:160});
    	}else{
    		//归口科室名称
    		autocomplete("#duty_dept_name","queryDutyDept.do?isCheck=false","id","text",true,true,'',true);
    		autocomplete("#payment_item_name","queryPaymentItem.do?isCheck=false","id","text",true,true,'',true);
    	}
        
        
        
		//部门类型   /hrp/acc/queryDeptType /hrp/budg/base/budgpayitem/expenditureItem/queryExpenditureItemBelong
        autocomplete("#acc_dept_type","../../../../acc/queryDeptType.do?isCheck=false","id","text",true,true,'',false);
		//部门分类  /hrp/ass/queryDeptKindDict
        autocomplete("#hos_dept_kind","../../../../ass/queryDeptKindDict.do?isCheck=false","id","text",true,true,'',false);
        
		//部门性质   /hrp/acc/queryDeptNatur
        autocomplete("#acc_dept_natur","../../../../acc/queryDeptNatur.do?isCheck=false","id","text",true,true,'',false);
        
		//支出性质   /hrp/acc/queryDeptOut
        autocomplete("#acc_dept_out","../../../../acc/queryDeptOut.do?isCheck=false","id","text",true,true,'',false);
        
        
        
     }   
    </script>
  
  </head>
  
   <body>
   <div id="pageloading" class="l-loading" style="display: none"></div>
   <form name="form1" method="post"  id="form1" >
        <table cellpadding="0" cellspacing="0" class="l-table-edit" >
	        <tr>
	            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b>归口科室<font color="red">*</font>:</b></td>
	            <td align="left" class="l-table-edit-td"><input name="duty_dept_name" type="text" id="duty_dept_name" ltype="text" /></td>
	            <td align="left"></td>
	            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b>支出项目<font color="red">*</font>:</b></td>
	            <td align="left" class="l-table-edit-td"><input name="payment_item_name" type="text" id="payment_item_name" ltype="text"  /></td>
	            <td align="left"></td>
	        
	        </tr> 
	        <tr>
	            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b>部门类型:</b></td>
	            <td align="left" class="l-table-edit-td"><input name="acc_dept_type" type="text" id="acc_dept_type" ltype="text"  /></td>
	            <td align="left"></td>
	            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b>部门分类:</b></td>
	            <td align="left" class="l-table-edit-td"><input name="hos_dept_kind" type="text" id="hos_dept_kind" ltype="text" /></td>
	            <td align="left"></td>
	            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b>部门性质:</b></td>
	            <td align="left" class="l-table-edit-td"><input name="acc_dept_natur" type="text" id="acc_dept_natur" ltype="text" /></td>
	            <td align="left"></td>
	            <td align="right" class="l-table-edit-td"  style="padding-left:20px;"><b>支出性质:</b></td>
	            <td align="left" class="l-table-edit-td"><input name="acc_dept_out" type="text" id="acc_dept_out" ltype="text" /></td>
	            <td align="left"></td>
	        </tr> 
        </table>
    </form>
    <div id="maingrid"></div>
    </body>
</html>
