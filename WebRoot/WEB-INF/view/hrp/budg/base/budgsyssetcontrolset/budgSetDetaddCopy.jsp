<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%String path = request.getContextPath();%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <jsp:include page="${path}/resource.jsp">
	    <jsp:param value="dialog,select,grid,hr,tree" name="plugins" />
	</jsp:include>
    <script>
        var dept_name, duty, title, dict_level,attend_areacode,yh_code;
        var tree, grid;
        var parentFrameName = parent.$.etDialog.getFrameName('add');
        var parentWindow = parent.window[parentFrameName];
        
        var initFrom = function () {
       
        	
        	/* 控制层次*/
    		link_name = $("#link_name").etSelect({
    		    url: '../budgsysset/budgsysset/queryLinkName.do?isCheck=false&plan_code='+'${plan_code}',
    		    defaultValue: "none",
    		   
    		});
    		
            
        };
   
      
        var close = function () {
            var curIndex = parent.$.etDialog.getFrameIndex(window.name);
            parent.$.etDialog.close(curIndex);
        };
       
      

        $(function () {
            initFrom();
          //保存
        	$("#save").click(function () {
        		
                ajaxPostData({
                    url: '../budgsysset/budgsysset/updateSetBudgCopy.do?isCheck=false',
                     data: { 
                    	 budg_year:'${budg_year}',
                    	 plan_code:'${plan_code}',
                    	 mod_code:link_name.getValue().split('@')[1],
                    	 copymod_code:'${mod_code}',
                    	 copylink_code:'${link_code}',
                    	link_code:link_name.getValue().split('@')[0],
                     	
                     },
                     success: function (responseData) {
                    		var parentFrameName = parent.$.etDialog.parentFrameName;
                			
                			var parentWindow = parent.window[parentFrameName];
                			parentWindow.query();
                       },
                 })
        	});
        	
     		$("#close").click(function () {
    			var curIndex = parent.$.etDialog.getFrameIndex(window.name);
    			parent.$.etDialog.close(curIndex);
    		})
        })
    </script>
</head>

<body>
    <div class="container">
       
        <div class="center">
            <table class="table-layout">
                <tr>
                <td class="label ">预算方案：</td>
				<td class="ipt"><input id="plan_name" type="text" value="${plan_name}" disabled/></td>
                
					
                </tr>
               <tr>
                 <td class="label ">预算项类别：</td>
				<td class="ipt">
					<input id="item_tab_name" type="text" value="${item_tab_name}" disabled/>
				</td>
				
			
               </tr>
               <tr>
               	<td class="label edistd">环节：</td>
				<td class="ipt edistd">
					<select id="link_name" style="width: 180px;"></select>
				</td>
               
               
               </tr>
            </table>
          
    
        	<div>
        <div class="button-group btn">
	        <button id="save">保存</button>
	        <button id="close">关闭</button>
	    </div>
	        </div>
	</div> 
    </div>
</body>

</html>