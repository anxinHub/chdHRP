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
<style>
	element.style {
		width:300px;
	}

</style>
<script type="text/javascript">
	
     var dataFormat;
     
     var dayRows = {
    			Rows : [ 
    			   {"id" : "1","text" : "1日" }, 
    			   {"id" : "2","text" : "2日" }, 
    			   {"id" : "3","text" : "3日" }, 
    			   {"id" : "4","text" : "4日" }, 
    			   {"id" : "5","text" : "5日" }, 
    			   {"id" : "6","text" : "6日" }, 
    			   {"id" : "7","text" : "7日" }, 
    			   {"id" : "8","text" : "8日" }, 
    			   {"id" : "9","text" : "9日" }, 
    			   {"id" : "10","text" : "10日" }, 
    			   {"id" : "11","text" : "11日" }, 
    			   {"id" : "12","text" : "12日" }, 
    			   {"id" : "13","text" : "13日" }, 
    			   {"id" : "14","text" : "14日" }, 
    			   {"id" : "15","text" : "15日" }, 
    			   {"id" : "16","text" : "16日" }, 
    			   {"id" : "17","text" : "17日" }, 
    			   {"id" : "18","text" : "18日" }, 
    			   {"id" : "19","text" : "19日" }, 
    			   {"id" : "20","text" : "20日" }, 
    			   {"id" : "21","text" : "21日" }, 
    			   {"id" : "22","text" : "22日" }, 
    			   {"id" : "23","text" : "23日" }, 
    			   {"id" : "24","text" : "24日" }, 
    			   {"id" : "25","text" : "25日" }, 
    			   {"id" : "26","text" : "26日" }, 
    			   {"id" : "27","text" : "27日" }, 
    			   {"id" : "28","text" : "28日" }, 
    			   {"id" : "29","text" : "29日" }, 
    			   {"id" : "30","text" : "30日" }, 
    			   {"id" : "31","text" : "31日" }
    			],
    			Total : 31

	 };
     var monthRows = {
	 			Rows : [
	 			   {"id" : "1","text" : "1月" }, {"id" : "2","text" : "2月" },  {"id" : "3","text" : "3月" }, 
	 			   {"id" : "4","text" : "4月" }, {"id" : "5","text" : "5月" }, {"id" : "6","text" : "6月" }, 
	 			   {"id" : "7","text" : "7月" },  {"id" : "8","text" : "8月" },  {"id" : "9","text" : "9月" }, 
	 			   {"id" : "10","text" : "10月" }, {"id" : "11","text" : "11月" }, {"id" : "12","text" : "12月" }
	 			],
	 			Total : 12
	 	 };
	 var hourRows = {
	 			Rows : [{"id" : "0","text" : "0点" },
	 			   {"id" : "1","text" : "1点" }, {"id" : "2","text" : "2点" },  {"id" : "3","text" : "3点" }, 
	 			   {"id" : "4","text" : "4点" }, {"id" : "5","text" : "5点" }, {"id" : "6","text" : "6点" }, 
	 			   {"id" : "7","text" : "7点" },  {"id" : "8","text" : "8点" },  {"id" : "9","text" : "9点" }, 
	 			   {"id" : "10","text" : "10点" }, {"id" : "11","text" : "11点" }, {"id" : "12","text" : "12点" }, 
	 			   {"id" : "13","text" : "13点" }, {"id" : "14","text" : "14点" }, {"id" : "15","text" : "15点" }, 
	 			   {"id" : "16","text" : "16点" }, {"id" : "17","text" : "17点" }, {"id" : "18","text" : "18点" }, 
	 			   {"id" : "19","text" : "19点" }, {"id" : "20","text" : "20点" },{"id" : "21","text" : "21点" }, 
	 			   {"id" : "22","text" : "22点" }, {"id" : "23","text" : "23点" }

	 			],
	 			Total : 24
	 	 };
     var minRows = {
 			Rows : [{"id" : "0","text" : "0分" },
 			   {"id" : "1","text" : "1分" }, {"id" : "2","text" : "2分" },  {"id" : "3","text" : "3分" }, 
 			   {"id" : "4","text" : "4分" }, {"id" : "5","text" : "5分" }, {"id" : "6","text" : "6分" }, 
 			   {"id" : "7","text" : "7分" },  {"id" : "8","text" : "8分" },  {"id" : "9","text" : "9分" }, 
 			   {"id" : "10","text" : "10分" }, {"id" : "11","text" : "11分" }, {"id" : "12","text" : "12分" }, 
 			   {"id" : "13","text" : "13分" }, {"id" : "14","text" : "14分" }, {"id" : "15","text" : "15分" }, 
 			   {"id" : "16","text" : "16分" }, {"id" : "17","text" : "17分" }, {"id" : "18","text" : "18分" }, 
 			   {"id" : "19","text" : "19分" }, {"id" : "20","text" : "20分" },{"id" : "21","text" : "21分" }, 
 			   {"id" : "22","text" : "22分" }, {"id" : "23","text" : "23分" },{"id" : "24","text" : "24分" }, 
 			   {"id" : "25","text" : "25分" }, {"id" : "26","text" : "26分" },{"id" : "27","text" : "27分" }, 
 			   {"id" : "28","text" : "28分" }, {"id" : "29","text" : "29分" }, {"id" : "30","text" : "30分" }, 
 			   {"id" : "31","text" : "31分" }, {"id" : "32","text" : "32分" }, {"id" : "33","text" : "33分" },
 			   {"id" : "34","text" : "34分" }, {"id" : "35","text" : "35分" }, {"id" : "36","text" : "36分" }, 
			   {"id" : "37","text" : "37分" }, {"id" : "38","text" : "38分" }, {"id" : "39","text" : "39分" }, 
			   {"id" : "40","text" : "40分" }, {"id" : "41","text" : "41分" }, {"id" : "42","text" : "42分" }, 
			   {"id" : "43","text" : "43分" }, {"id" : "44","text" : "44分" },{"id" : "45","text" : "45分" }, 
			   {"id" : "46","text" : "46分" }, {"id" : "47","text" : "47分" },{"id" : "48","text" : "48分" }, 
			   {"id" : "49","text" : "49分" }, {"id" : "50","text" : "50分" },{"id" : "51","text" : "51分" }, 
			   {"id" : "52","text" : "52分" }, {"id" : "53","text" : "53分" }, {"id" : "54","text" : "54分" }, 
			   {"id" : "55","text" : "55分" }, {"id" : "56","text" : "56分" }, {"id" : "57","text" : "57分" },
			   {"id" : "58","text" : "58分" }, {"id" : "59","text" : "59分" }

 			],
 			Total : 60
 	 };
     $(function (){
    	 
    	 loadDict()
        
         loadForm();
    	 
    	 loadCss();
    	 setUse();
     });  
     function setUse(){
    	 
     }
     function loadCss(){
 		
 		$("#period").ligerTextBox({
 			width : 400
 		});

 		$("#weekdata").ligerTextBox({
 			width : 400
 		});
 		$("#monthdata").ligerTextBox({
 			width : 400
 		});
 		$("#daydata").ligerTextBox({
 			width : 400
 		});
 		$("#hourdata").ligerTextBox({
 			width : 400
 		});
 		$("#mindata").ligerTextBox({
 			width : 400
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
 
 function loadDict(){
	 
     var cron='${cron}';
     var cronA=cron.split(" ");
     var ptype=3;
     if(cronA[5]!='?'&&cronA[5]!='*')
     	ptype=5;
     else if(cronA[4]!='?'&&cronA[4]!='*')
    	 ptype=4;
     else if(cronA[3]!='?'&&cronA[3]!='*')
    	 ptype=3;
     else if(cronA[2]!='?'&&cronA[2]!='*')
    	 ptype=2;
     else if(cronA[1]!='?'&&cronA[1]!='*')
    	 ptype=1;
     $("#weekdata").ligerComboBox({  
         data: [
			{"id" : "2","text" : "周一"}, 
    		{"id" : "3","text" : "周二"}, 
    		{"id" : "4","text" : "周三"}, 
    		{"id" : "5","text" : "周四"}, 
    		{"id" : "6","text" : "周五"}, 
    		{"id" : "7","text" : "周六"}, 
    		{"id" : "1","text" : "周日"}
         ],
         initWidth: 400
     });  
     
     
     autoCompleteByData("#monthdata", monthRows.Rows, "id", "text", true, true, true,true,null,400);
     autoCompleteByData("#daydata", dayRows.Rows, "id", "text", true, true, true,true,"3",400);
     autoCompleteByData("#hourdata", hourRows.Rows, "id", "text", true, true, true,true,null,400);
     autoCompleteByData("#mindata", minRows.Rows, "id", "text", true, true, true,true,null,400);
//     autoCompleteByData("#period", periodRows.Rows, "id", "text", true, true, true,true,4,400);
     
     $("#period").ligerComboBox({  
         data: [
			{"id" : "5","text" : "按周"}, 
			{"id" : "4","text" : "按月"}, 
			{"id" : "3","text" : "按天"}, 
			{"id" : "2","text" : "按时"}, 
			{"id" : "1","text" : "按分"}
         ],
         initWidth: 400
     }); 
    
	 liger.get("period").setValue(ptype);
   	
	 if(cron!=''){
	   	liger.get("mindata").setValue(cronA[1]);
	   	liger.get("mindata").setText(cronA[1]+'分'); 
	   	liger.get("hourdata").setValue(cronA[2]);
	   	liger.get("hourdata").setText(cronA[2]+'点'); 
	   	if(cronA[3]!='*'&&cronA[3]!='?'){
		   	liger.get("daydata").setValue(cronA[3]);
		   	liger.get("daydata").setText(cronA[3]+'日'); 
	   	}
	   	if(cronA[4]!='*'&&cronA[4]!='?'){
		   	liger.get("monthdata").setValue(cronA[4]);
		   	liger.get("monthdata").setText(cronA[4]+'月'); 
	   	}
	   	liger.get("weekdata").setValue(cronA[5]);
	 }else{
			liger.get("weekdata").setValue(2);
	 }
   } 
 	function changePeriod(){
 		var p=liger.get("period").getValue();		
 		if(p=="")
 			return;
 		switch(p){
 		case "5"://如果是周，隐藏下面的天
 			$('#tr3').hide();
 			$('#tr4').hide();
 			$('#tr5').show();
 			for(var i=1;i<2;i++){
 	 			$('#tr'+i).show();
 	 		}
 			break;
 		case "4"://如果是月，隐藏下面的周
 			$('#tr5').hide();
 			for(var i=1;i<5;i++){
 	 			$('#tr'+i).show();
 	 		}
 			break;
 		default:
	 		for(var i=5;i>p;i--){
	 			$('#tr'+i).hide();
	 		}
 			for(var i=1;i<=p;i++){
 				$('#tr'+i).show();
 			}
 		}
 		
 	}

  function getCron(){
	//转换cron
		var p=liger.get("period").getValue();		
 		if(p=="")
 			return;
 		var cronEx="0 * * * * ? *".split(" ");
		
		if(p=="4"){
			cronEx[3]=liger.get("daydata").getValue();
			cronEx[4]=liger.get("monthdata").getValue();
			cronEx[5]="?";
			cronEx[1]=liger.get("mindata").getValue();
			cronEx[2]=liger.get("hourdata").getValue();
		}
		if(p=="5"){
			cronEx[1]=liger.get("mindata").getValue();
			cronEx[2]=liger.get("hourdata").getValue();
			cronEx[3]="?";
			cronEx[4]="*";
			cronEx[5]=liger.get("weekdata").getValue(); 	
		}
		if(p=="3"){
			cronEx[1]=liger.get("mindata").getValue();
			cronEx[2]=liger.get("hourdata").getValue();
			cronEx[3]=liger.get("daydata").getValue();
		}
		if(p=="2"){
			cronEx[1]=liger.get("mindata").getValue();
			cronEx[2]=liger.get("hourdata").getValue();
		}
		if(p=="1"){
			cronEx[1]=liger.get("mindata").getValue();
		}
		return cronEx.join(" ");
  }
    </script>
  
  </head>
  
   <body>
   <br><br></br></br>
   <div id="pageloading" class="l-loading" style="display: none"></div>
   <form name="form1" method="post"  id="form1" >
        <table cellpadding="0" cellspacing="0" class="l-table-edit" border=0 width="90%">

            <tr>
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">任务周期:</td>
                <td align="left" class="l-table-edit-td">
                	<input name="period" type="text" id="period" ltype="text" maxlength="20" validate="{required:true}" onchange="changePeriod();"/>
                </td>
            </tr> 
            <tr><td colspan=2>&nbsp;</td></tr>
            <tr id="tr5">
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">周</td>
                <td align="left" class="l-table-edit-td">
                	 <input name="weekdata" type="text" id="weekdata" ltype="text" maxlength="50" validate="{required:true}" />
                </td>
            </tr> 
            <tr id="tr4">
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">月</td>
                <td align="left" class="l-table-edit-td">
                	 <input name="monthdata" type="text" id="monthdata" ltype="text" maxlength="50" validate="{required:true}" />
                </td>
            </tr> 
            <tr id="tr3">
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">天</td>
                <td align="left" class="l-table-edit-td">
                	 <input name="daydata" type="text" id="daydata" ltype="text" maxlength="50" validate="{required:true}" />
                </td>
            </tr> 
            <tr  id="tr2">
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">时</td>
                <td align="left" class="l-table-edit-td">
                	 <input name="hourdata" type="text" id="hourdata" ltype="text" maxlength="50" validate="{required:true}" />
                </td>

            </tr> 
             <tr  id="tr1">
                <td align="right" class="l-table-edit-td"  style="padding-left:20px;">分</td>
                <td align="left" class="l-table-edit-td">
                	 <input name="mindata" type="text" id="mindata" ltype="text" maxlength="50" validate="{required:true}" />
                </td>

            </tr> 
           
           
            
        </table>
    </form>
   
    </body>
</html>
       
