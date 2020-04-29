<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<jsp:include page="${path}/resource.jsp">
	<jsp:param value="hr,dialog,form,checkbox" name="plugins" />
</jsp:include>
<style type="text/css">
*{
	margin: 0;
	padding: 0;
}
.main {
	border: 1px solid #aecaf0;
    padding: 40px 0 20px 0;
}
.radios{
	width: 4%;
	text-align: center; 
}

.radios input{
    margin-bottom: 20px;
}

.ipt {
	width: 24% !important;
}
.content {
	width: 100%;
    height: 50px;
    background: #c3d9fb;
    display: inline-block;
    margin-bottom: 20px;
}

.content .numbers{
	display: inline-block;
    width: 15%;
    height: inherit;
    text-align: center;
    line-height: 24px;
    float: left;
}

.content .numbers .nums {
    width: 25px;
    height: 25px;
    border-radius: 50%;
    text-align: center;
    background: #6b97da;
    color: #fff;
    margin-top: 10px;
    display: inline-block;
}

.content .messages{
	display: inline-block;
    width: 85%;
    height: inherit;
    float: left;
}
.content .messages p:nth-child(1){
	margin-top: 7px;
	font-weight: 700;
}

</style>
<script>
	$(function () {
    	initQuery();
    });
    
    function initQuery() {
    	var str = '';
    	ajaxPostData({
        	url: 'queryAccVouchFlow.do?isCheck=false',
     		async: false,	
            success: function (res) {
            	if(res.data.length > 0){
            		$.each(res.data,function(index,item){
               			if(this.is_check == '0'){
               				str += '<tr><td class="radios"><input type="radio" name="radio" value="'+ index + '" /></td>';
               			}else{
               				str += '<tr><td class="radios"><input type="radio" name="radio" value="'+ index + '" checked /></td>';
               			};
               			$.each(item.Row,function(i,v){
               				str += '<td class="ipt">';
               				str += '<span class="content">';
               				str += '<span class="numbers">';
               				str += '<p class="nums">' +v.node_id+ '</p>';
               				str += '</span>';
               				str += '<span class="messages">';
               				str += '<p>' +v.node_name+ '</p>';
               				str += '<p>' +v.note+ '</p>';
               				str += '</span>';
               				str += '</span>';
               				str += '</td>';
               			});
               			str += '<td style="display: none">';
               			str += '<span id="span'+index+'">';
               			str += JSON.stringify(item.Row);
               			str += '</span>';
               			str += '</td>';
               			str += '</tr>';
               		});
               		$('.main').html(str);
            	}
            }
        })
	};
	
	function saveData(){
		var indexs = $("input[name='radio']:checked").val();
		var rowData = $('#span' + indexs).text();
		
		ajaxPostData({
        	url: 'saveAccVouchFlow.do?isCheck=false',
        	data: {
             	allData: rowData
            },		
            success: function (res) {
            	if(res.state=="true"){
            		initQuery();
            	}
            }
        })
	};
    </script>
</head>

<body>
	<table class="table-layout main" style="width: 100%"></table>

	<div class="button-group">
		<button id="save" onClick="saveData()">保存</button>
	</div>
</body>
</html>