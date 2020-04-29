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
        <jsp:param value="select,datepicker,etValidate,dialog,grid" name="plugins" />
    </jsp:include>
    <script src="<%=path%>/lib/hrp/budg/budg.js" type="text/javascript"></script>
    <script type="text/javascript">
	 var validate;
	 $(function (){
	    loadDict();  //加载下拉框
	    loadForm();
	 });  

	function loadForm(){
	    validate = $.etValidate({
	       config: {
	           // tipTime: 1000
	       },
	       items: [
	           {
	               el: $("#year"),
	               required: true
	           },
	           {
	               el: $("#begin_month"),
	               required: true
	           },
	           {
	               el: $("#end_month"),
	               required: true
	           }
	       ]
	   })
	}       
   
	function loadDict() {
	     ajaxPostData({
	         url: "../../queryBudgYear.do?isCheck=false",
	         success: function (data) {
	             // 年度
	             year = $("#year").etDatepicker({
	                 defaultDate: data[0].text,
	                 view: "years",
	                 minView: "years",
	                 dateFormat: "yyyy",
	              /*    minDate: data[data.length - 1].text,
	                 maxDate: data[0].text, */
	                 todayButton: false,
	                 onChanged: function (value) {
	                 }
	             });
	         }
	     });
	     
	     // 月份
	     month = $(".month").etDatepicker({
	         view: "months",
	         maxView: "months",
	         minView: "months",
	         dateFormat: "mm",
	         showNav: false,
	         todayButton: false,
	         position: "bottom right"
	     });
	    
	    // 帐套
	    ajaxPostData({
	        url: "../../../sys/queryCopyCodeDictPerm.do?isCheck=false",
	        data: {},
	        success: function(responseData) {
	        	var html = '';
	            $.each(responseData,function(d){
	            	html += '<input name="copy_code" id="code'+ this.id +'" value="'+ this.id +'" type="checkbox"><label for="code'+ this.id +'"> '+ this.text +' </label></br>';
	            });
	            $("#copy_code_list").html(html);
	        }
	    })
	} 
	
	function getDatafromAcc(){
		  if($("input[type='checkbox']").is(':checked')==false){
			  $.etDialog.error('账套不能为空');
				return false;
		  }
		 if(validate.test()) {
			 var budg_year = $('#year').val();
			 var begin_month = $('#begin_month').val();
			 var end_month = $('#end_month').val();
			 
			 var arr = new Array();
	         $("input[type=checkbox]:checked").each(function(i){
	             arr[i] = $(this).val();
	         });
	         var copy_codes = arr.join(",");
	         
		   	 ajaxPostData({
		     	url: "getDatafromAcc.do?isCheck=false",
		    	data: {budg_year:budg_year,begin_month:begin_month,end_month:end_month,copy_codes:copy_codes},
		    	success: function (responseData) {
		    		parent.queryNew();
		    	},
		     })
	     }
	}
    </script>
  </head>
  
   <body>
   <div id="pageloading" class="l-loading" style="display: none"></div>
    <table class="table-layout">
        <tr>
            <td class="label no-empty">年度：</td>
            <td class="ipt">
                <input type="text" id="year" readonly style="cursor:pointer" />                    
            </td>
            <td class="label no-empty">自</td>
            <td class="ipt">
                <input type="text" class="month" id="begin_month" readonly style="cursor:pointer;width:70px;" />
                至
                <input type="text" class="month" id="end_month" readonly style="cursor:pointer;width:70px;" />
            </td>
        </tr>
        <tr>
        	<td class="label no-empty">帐套：</td>
        	<td class="ipt" id="copy_code_list">
               
            </td>
        </tr>
    </table>
   
    </body>
</html>
