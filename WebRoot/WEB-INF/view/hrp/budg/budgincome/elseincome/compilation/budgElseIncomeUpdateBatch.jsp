<%@ page language="java" contentType="text/html; charset=UTF-8"
  pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html style="overflow:hidden;">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<jsp:include page="${path}/static_resource.jsp">
	<jsp:param value="select,datepicker,ligerUI,dialog,etValidate" name="plugins" />
</jsp:include>
<script type="text/javascript">
	var dataFormat;
	$(function() {
	    $("#grow_rate").change(function(){
      	  
           	if($("#grow_rate").val()){
           		
           		$("#grow_value").attr('disabled', true);
          		
          		$("#grow_value").val("");
           		
           	}else{
           		$("#grow_value").attr('disabled', false);
          		
          		$("#grow_value").val("");
            }
        })
	    
	    $("#grow_value").change(function(){
	      	  
           	if($("#grow_value").val()){
           		$("#grow_rate").attr('disabled', true);
          		
          		$("#grow_rate").val("");
           	}else{
           		$("#grow_rate").attr('disabled', false)
           		
          		$("#grow_rate").val("");
            }
        })
	});
	
	function save() {
		
		var data = parent.grid.selectGetChecked();
		
		var grow_rate = $("#grow_rate").val()?$("#grow_rate").val():"-1";
		var grow_value = $("#grow_value").val() ? $("#grow_value").val() : "-1";
		var remark = $("#remark").val() ? $("#remark").val() : "-1";
		
		var ParamVo =[];
	    $(data).each(function (){
	    	ParamVo.push(
    			this.rowData.budg_year   +"@"+ 
				this.rowData.month   +"@"+ 
				this.rowData.subj_code  +"@"+
				this.rowData.last_income +"@"+ 
				grow_rate   +"@"+ 
				grow_value   +"@"+ 
				remark 
	 		)
	    });
	    ajaxPostData({
			url: "updateBatchBudgElseIncome.do?isCheck=false",
			data: { ParamVo: ParamVo.toString() },
			success: function (res) {
				if (res.state == "true") {
					parent.query();
		        	close_page();
				}
			}
		})
	}
	
	//关闭当前页面
    function close_page() {
        var curIndex = parent.$.etDialog.getFrameIndex(window.name);
        parent.$.etDialog.close(curIndex);
    }
	
	function saveBudgElseIncome() {
	    save();
	}
</script>
  
</head>
<body>
	<div class="main">
   		<table class="table-layout" >
			<tr>
	            <td class="label">增长比例：</td>
	            <td class="ipt">
	                <input id="grow_rate" name="grow_rate" type="text"/>%
	            </td>
	            <td></td>
	        </tr>
			<tr>
	            <td class="label">增长额：</td>
	            <td class="ipt">
	                <input id="grow_value" name="grow_value" type="text"/>
	            </td>
	        </tr>
	        <tr>
	            <td class="label">说明：</td>
	            <td class="ipt">
	                <input name="remark"  type="text" id="remark" style="width: 200px;" />
	            </td>
	        </tr>
	    </table>
    </div>
    <!-- <div id="pageloading" class="l-loading" style="display: none"></div>
    <form name="form1" method="post"  id="form1" >
        <table cellpadding="0" cellspacing="0" class="l-table-edit" >
        <tr> 
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">增长比例：</td>
            <td align="left" class="l-table-edit-td">
              <table>
                <tr>
                  <td><input name="grow_rate" type="text" id="grow_rate" ltype="text" validate="{required:true,range:[-100,100],maxlength:20}" /></td>
                  <td>%</td>
                </tr>
              </table>
            </td>
        </tr> 
        <tr> 
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">增长额：</td>
            <td align="left" class="l-table-edit-td">
              <table>
                <tr>
                  <td><input name="grow_value" type="text" id="grow_value" ltype="text" validate="{required:true,maxlength:20}" /></td>
                </tr>
              </table>
            </td>
        </tr> 
        <tr> 
            <td align="right" class="l-table-edit-td"  style="padding-left:20px;">说明：</td>
            <td align="left" colspan="4" class="l-table-edit-td">
				<textarea rows="5" class="liger-textarea" id="remark" name="remark" style="width:180px" ></textarea>
			</td>
        </tr> 
    </table>
    </form>
     -->
  </body>
</html>