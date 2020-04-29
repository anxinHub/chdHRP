<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%String path = request.getContextPath();%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <jsp:include page="${path}/resource.jsp">
        <jsp:param value="select,tree,hr,validate,datepicker,dialog,grid,form" name="plugins" />
    </jsp:include>
    <style type="text/css">
        * {
            margin: 0;
            padding: 0;
        }

        ul {
            list-style: none;
        }

        .wrapper {
            width: 1000px;
            height: 275px;
            margin: 0 auto;
            margin-top: 100px;
        }

        .tab {
            border: 1px solid #ddd;
            border-bottom: 0;
            height: 36px;
            width: 320px;
        }

        .tab li {
            position: relative;
            float: left;
            width: 80px;
            height: 34px;
            line-height: 34px;
            text-align: center;
            cursor: pointer;
            border-top: 4px solid #fff;
        }

        .tab span {
            position: absolute;
            right: 0;
            top: 10px;
            background: #ddd;
            width: 1px;
            height: 14px;
            overflow: hidden;
        }

        .products {
            width: 502px;
            border: 1px solid #ddd;
            height: 276px;
        }

        .products .main {
            float: left;
            display: none;
        }

        .products .main.selected {
            display: block;
        }

        .tab li.active {
            border-color: red;
            border-bottom: 0;
        }

    </style>

	<script>
		function getValue(){
			return $("#cv").val();
		}
		function secondControl(s){
			debugger;
			var type=parseInt(s.value);
			var cv=$("#cv").val();
			var a=cv.split(" ");		 
			switch(type){
				case 1:
					a[0]="*";
					break;
				case 2:
					a[0]=$("#secondStart").val()+"-"+$("#secondEnd").val();
					break;
				case 3:
					a[0]=$("#ss").val()+"/"+$("#se").val();
					break;
			
			}
			$("#cv").val(""+a.join(" "));
		}
		 
		function minuteControl(s){
			var type=parseInt(s.value);
			var cv=$("#cv").val();
			var a=cv.split(" ");		 
			switch(type){
				case 1:
					a[1]="*";
					break;
				case 2:
					a[1]=$("#minStart").val()+"-"+$("#minEnd").val();
					break;
				case 3:
					a[1]=$("#ms").val()+"/"+$("#me").val();
					break;
			
			}
			$("#cv").val(""+a.join(" "));
		}
		function hourControl(s){
			
			var type=parseInt(s.value);
			var cv=$("#cv").val();
			var a=cv.split(" ");		 
			switch(type){
				case 1:
					a[2]="*";
					break;
				case 2:
					a[2]=$("#hourStart").val()+"-"+$("#hourEnd").val();
					break;
				case 3:
					a[2]=$("#hs").val()+"/"+$("#he").val();
					break;
			
			}
			$("#cv").val(""+a.join(" "));
		}
		function dateControl(s){
			
			var type=parseInt(s.value);
			var cv=$("#cv").val();
			var a=cv.split(" ");		 
			switch(type){
				case 1:
					a[3]="*";
					break;
				case 2:
					a[3]=$("#dateStart").val()+"-"+$("#dateEnd").val();
					break;
				case 3:
					a[3]=$("#ds").val()+"/"+$("#de").val();
					break;
			
			}
			$("#cv").val(""+a.join(" "));
		}
		
	</script>
</head>
<body>
<div class="wrapper">
    <ul class="tab">
        <li class="tab-item active">秒</li>
        <li class="tab-item">分</li>
        <li class="tab-item">时</li>
        <li class="tab-item">日</li>
    </ul>
    <div class="products">
        <div class="main selected">
            <div class="a">
						<table><tr><td>
                            <input type="radio" checked="checked" name="second" value=1 onclick="secondControl(this);">
                            每秒执行</td></tr>
                            <tr><td><input type="radio" name="second"  value=2 onclick="secondControl(this);">
                            在<input  style="width: 36px; height: 20px; line-height: 20px;"  value="2" id="secondStart"  value="1">-
							<input  style="width: 36px; height: 20px; line-height: 20px;"  value="2" id="secondEnd"  value="2">
							周期内,每秒执行一次</td></tr>
                         
                            <tr><td><input type="radio" name="second" value=3 onclick="secondControl(this)">
                            从
                            <input class="numberspinner numberspinner-f spinner-text spinner-f validatebox-text numberbox-f" style="width: 36px; height: 20px; line-height: 20px;" data-options="min:0,max:59" value="0" id="ss">
                            秒开始,每
                           <input class="numberspinner numberspinner-f spinner-text spinner-f validatebox-text numberbox-f" style="width: 36px; height: 20px; line-height: 20px;" data-options="min:1,max:59" value="1" id="se"><input type="hidden" value="1">
                            秒执行一次 </td></tr></table>
			
			
			</div>
        </div>


        <div class="main">
				<table><tr><td>
						<input type="radio" checked="checked" name="min" value=1 onclick="minuteControl(this)">
                            每分钟执行</td></tr>
                        <tr><td>
                            <input type="radio" name="min" value=2 onclick="minuteControl(this)">
                            周期从
                            <input  style="width: 36px; height: 20px; line-height: 20px;" data-options="min:1,max:58" value="1" id="minStart">
                            -
                            <input  style="width: 36px; height: 20px; line-height: 20px;" value="2" id="minEnd">
                            分钟</div></td></tr>
                        <tr><td>
                            <input type="radio" name="min" value=3 onclick="minuteControl(this)">
                            从
                            <input  style="width: 36px; height: 20px; line-height: 20px;" data-options="min:0,max:59" value="0" id="ms">
                            分钟开始,每
                            <input  style="width: 36px; height: 20px; line-height: 20px;" data-options="min:1,max:59" value="1" id="me">
                            分钟执行一次</td></tr></table>
                 


        </div>
			<div class="main">
				<table>
					<tr>
						<td><input type="radio" checked="checked" name="hour" value=1
							onclick="hourControl(this)"> 每小时执行</td>
					</tr>
					<tr>
						<td><input type="radio" name="hour" value=2
							onclick="hourControl(this)"> 在 <input
							style="width: 36px; height: 20px; line-height: 20px;"
							data-options="min:1,max:58" value="1" id="hourStart"> - <input
							style="width: 36px; height: 20px; line-height: 20px;" value="2"
							id="hourEnd"> 周期内，每小时执行一次
							</div></td>
					</tr>
					<tr>
						<td><input type="radio" name="hour" value=3
							onclick="hourControl(this)"> 从 <input
							style="width: 36px; height: 20px; line-height: 20px;"
							data-options="min:0,max:59" value="0" id="hs"> 小时开始,每 <input
							style="width: 36px; height: 20px; line-height: 20px;"
							data-options="min:1,max:59" value="1" id="he"> 小时执行</td>
					</tr>
				</table>
			</div>
			<div class="main">
            <table><tr><td>
						<input type="radio" checked="checked" name="date" value=1 onclick="dateControl(this)">
                            每日执行</td></tr>
                        <tr><td>
                            <input type="radio" name="date" value=2 onclick="dateControl(this)">
                            在
                            <input  style="width: 36px; height: 20px; line-height: 20px;" data-options="min:1,max:58" value="1" id="dateStart">
                            -
                            <input  style="width: 36px; height: 20px; line-height: 20px;" value="2" id="dateEnd">
                            周期内，每日执行一次</div></td></tr>
                        <tr><td>
                            <input type="radio" name="date" value=3 onclick="dateControl(this)">
                            从
                            <input  style="width: 36px; height: 20px; line-height: 20px;" data-options="min:0,max:59" value="0" id="ds">
                            日开始,每
                            <input  style="width: 36px; height: 20px; line-height: 20px;" data-options="min:1,max:59" value="1" id="de">
                            日执行</td></tr></table>
        </div>
    </div>
</div>
<div style="margin: -30px 0px 0px 25px;">
cron表达式：<input name="cron_value" id="cv" value="* * * * * ? *"></div>
</body>
    <script>
        $(function () {
            $(".wrapper .tab-item").click(function () {
                $(this).addClass("active").siblings().removeClass("active");
                $(".products .main").eq($(this).index()).show().siblings().hide();
            })
        })

		
    </script>
</html>