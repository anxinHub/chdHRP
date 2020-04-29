<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%String path = request.getContextPath();%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <jsp:include page="${path}/resource.jsp">
	    <jsp:param value="validate,dialog,select,datepicker" name="plugins" />
	</jsp:include>
    <script>
        var is_stop,dept_code,train_type,train_way,month;
     
   
        //验证
        function checked(){
        	var dataValidate = $.etValidate({
        		config:{},
        		items:[
        			  { el: $("#train_type"), required: true },
                    { el: $("#dept_code"), required: true },
                    { el: $("#month"), required: true },
                    { el: $("#train_title"), required: true },
                    { el: $("#train_way"), required: true },
                    { el: $("#train_object"), required: true }
        		]
        	});
        	
        	
        	return dataValidate.test();
       }
       
        var initForm = function () {
        	month = $("#month").etDatepicker({
      		  view: "months",
      		  minView: "months",
      		  dateFormat: "mm",
      		  defaultDate: true,
          });// 编制日期
          
          //培训部门
          dept_code = $("#dept_code").etSelect({
              url: "../../queryHosDeptSelect.do?isCheck=false",
              defaultValue: "none"
          });
          //培训类别
          train_type = $("#train_type").etSelect({
              url: "../../queryHosTrainTypeSelect.do?isCheck=false",
              defaultValue: "none"
          });
          //培训方式
          train_way = $("#train_way").etSelect({
              url: "../../queryHosTrainWaySelect.do?isCheck=false",
              defaultValue: "none"
             
          });
       /*    makeDis('0') */
     $("#is_exam").click(function () {

    	   if($('input[name="is_exam"]:checked ').val()==0){
    		   $(":radio[name='is_cert'][value='0']").prop("checked", "checked");
    		   $(":radio[name='is_cert']").prop("disabled", "true"); 
    	   }
    	
       })
       $("#is_exama").click(function () {

  	   if($('input[name="is_exam"]:checked ').val()==1){
  		   $(":radio[name='is_cert']").removeAttr("disabled"); 
  	   }
  	
     }
      )
        };

        var save = function () {
        
        	if(!checked()){
   			  	return;
   		  	}
            var is_exam = $('input[name="is_exam"]:checked ').val();
            var is_cert = $('input[name="is_cert"]:checked ').val();

            ajaxPostData({
                url: 'addPlant.do',
                data: {
             	    train_type_code : train_type.getValue(),
                	dept_id:dept_code.getValue().split('@')[1],
                	dept_no:dept_code.getValue().split('@')[0],
                	month :month.getValue(),
                	train_title: $("#train_title").val(),
                	train_way_code:train_way.getValue(),
                	train_object: $("#train_object").val(),
                	is_exam: is_exam,
                	is_cert: is_cert,
                	credit_hour: $("#credit_hour").val(),
                    note:$("#note").val()

                },
                delayCallback:true,
                success: function () {
                    var curIndex = parent.$.etDialog.getFrameIndex(window.name);
                     parent.$.etDialog.close(curIndex);
                    parent.query();
                    $("#way_code").val('');
                    $("#way_name").val('');
                    is_stop.clearItem();
                }
            })
        };

        $(function () {
            initForm()
        })
         //封装一个限制字数方法
    var checkStrLengths = function (str, maxLength) {
        var maxLength = maxLength;
        var result = 0;
        if (str && str.length > maxLength) {
            result = maxLength;
        } else {
            result = str.length;
        }
        return result;
    }

    //监听输入
    $(".wishContent").on('input propertychange', function () {

        //获取输入内容
        var userDesc = $(this).val();

        //判断字数
        var len;
        if (userDesc) {
            len = checkStrLengths(userDesc, 50);
        } else {
            len = 0
        }

        //显示字数
        $(".wordsNum").html(len + '/50');
    });
    </script>
</head>

<body>
    <div class="mian">
        <table class="table-layout" style="width: 100%;">
            <tr>
               <td class="label no-empty" >培训类别：</td>
            <td class="ipt">
              <select id="train_type" type="text" style="width:180px"></select>

            </td>
                 <td class="label no-empty">培训部门：</td>
                <td class="ipt">
                    <select id="dept_code" type="text" style="width:180px"></select>
                </td>

            </tr>
            <tr>
                <td class="label no-empty">培训主题：</td>
                <td class="ipt">
                    <input id="train_title" type="text" />
                </td>
                <td class="label no-empty">计划月份：</td>
                <td class="ipt">
                    <input id="month" type="text" />
                </td>
            </tr>
                   <tr>
                <td class="label no-empty">培训对象：</td>
                <td class="ipt">
                    <input id="train_object" type="text" />
                </td>
                <td class="label no-empty">培训方式：</td>
                <td class="ipt">
                  <select id="train_way" type="text" style="width:180px"></select>
                </td>
            </tr>
                   <tr>
                <td class="label no-empty">是否考核：</td>
                <td class="ipt">
                  <input type="radio" name="is_exam" id="is_exama" value='1' onclick="makeDis('1')"/><label>是&nbsp;&nbsp;</label>  
                	<input name="is_exam"  type="radio" id="is_exam" checked value="0" onclick="makeDis('0')" /><label>否&nbsp;&nbsp;</label>  

                </td>
                <td class="label ">学分：</td>
                <td class="ipt">
                    <input id="credit_hour" type="number" />
                </td>
            </tr>
                   <tr>
                <td class="label no-empty">是否发证：</td>
                <td class="ipt">
                  <input type="radio" name="is_cert"   value='1' disabled onclick="makeDis('1')"/><label>是&nbsp;&nbsp;</label>  
                	<input name="is_cert"  type="radio" checked value="0"  disabled onclick="makeDis('0')" /><label>否&nbsp;&nbsp;</label>  

                </td>
               <!--  <td class="label no-empty">学分：</td>
                <td class="ipt">
                    <input id="credit_hour" type="text" />
                </td> 
            </tr>
            <tr>-->
                <td class="label">备注：</td>
                <td class="ipt">
                 <textarea id="note" rows="20" cols="25" class="wishContent" placeholder="请输入不超过50个字" maxlength="50"></textarea>
                </td>
            </tr>

        </table>
    </div>
</body>

</html>