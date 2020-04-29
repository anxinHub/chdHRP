<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%String path = request.getContextPath();%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<jsp:include page="${path}/resource.jsp">
	<jsp:param value="dialog,select,validate" name="plugins" />
</jsp:include>
<script type="text/javascript" src="<%=path%>/lib/laydate/laydate.js"></script>
<script>
	var formValidate,budg_code, cont_l, cont_p,cont_w;

	$(function () {
		initDict();
   		initValidate();
	})
	
	function initDict(){
		budg_code=$("#budg_code").etSelect({
			url:'../budgsysset/budgsysset/queryBudgModSelect.do?isCheck=false&plan_code='+'${plan_code}',
		    defaultValue: "none",
		});
			
			
		/* 控制层次*/
		cont_l = $("#cont_l").etSelect({
		    url: '../queryContLSelect.do?isCheck=false',
		    defaultValue: "none",
		});
		/* 控制期间*/
		cont_p = $("#cont_p").etSelect({
		    url: '../queryContPSelect.do?isCheck=false',
		    defaultValue: "none",
		});
		/* 控制方式*/
		cont_w= $("#cont_w").etSelect({
		    url: '../queryContWSelect.do?isCheck=false',
		    defaultValue: "none",
		});
	}


	 function query() {
		
         params = [
                   { name: 'cont_p', value: cont_p.getValue()},
                   { name: 'cont_l', value: cont_l.getValue() },
                   { name: 'cont_w', value: cont_w.getValue()},
                   { name: 'plan_code', value: '${plan_code}' },
                   { name: 'budg_year', value: '${budg_year}' },
                   { name: 'mod_code', value: '${mod_code}' },
                   { name: 'budg_code', value: budg_code.getValue() },

               ];
         
     	var curIndex = parent.$.etDialog.getFrameIndex(window.name);
           
			var parentFrameName = parent.$.etDialog.parentFrameName;
			
			var parentWindow = parent.window[parentFrameName];
			
			//parentWindow.searchParam = params;
			parentWindow.child_grid.loadData(params,"queryBudgCDet.do");
              
			parentWindow.child_grid.refreshView();
			parent.$.etDialog.close(curIndex);
		 
      }
	var initValidate = function () {
		
		
 		$("#close").click(function () {
			var curIndex = parent.$.etDialog.getFrameIndex(window.name);
			parent.$.etDialog.close(curIndex);
		})
	};
</script>
</head>

<body>
	<div class="button-group" >
		 <table class="table-layout" style="width: 100%;">
	        <tr>
	            <td class="label ">预算项：</td>
	            <td class="ipt">
	                <select id="budg_code" style="width:160px"></select>
	            </td>
	              <td class="label ">控制方式：</td>
	            <td class="ipt">
	                <select id="cont_w" style="width:160px"></select>
	            </td>
	        </tr>
	        <tr>
	            <td class="label ">控制层次：</td>
	            <td class="ipt">
	                <select id="cont_l" style="width:160px"></select>
	            </td>
	             <td class="label ">控制期间：</td>
	            <td class="ipt">
	                <select id="cont_p" style="width:160px"></select>
	            </td>
	        </tr>
    	</table>
	</div>   
</body>

</html>