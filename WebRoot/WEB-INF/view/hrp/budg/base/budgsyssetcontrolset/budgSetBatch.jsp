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
    		cont_l = $("#cont_l").etSelect({
    		    url: '../queryContLSelect.do?isCheck=false',
    		    defaultValue: "${cont_l}",
    		    onChange: function(val){
    		    $("#cont_p").etSelect({
		    		 url: '../queryContPSelect.do?isCheck=false&tab_code='+tab_code+"&budg_year="+'${budg_year}',
		    		 
		    	 })
    		    }
    		});
    		/* 控制期间*/
    		cont_p = $("#cont_p").etSelect({
    		    url: '../queryContPSelect.do?isCheck=false',
    		    defaultValue: "${cont_p}",
    		});
    		/* 控制方式*/
    		cont_w= $("#cont_w").etSelect({
    		    url: '../queryContWSelect.do?isCheck=false',
    		    defaultValue: "${cont_w}",
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
        		if(cont_l.getValue()!=''||cont_p.getValue()!=''||cont_w.getValue()!=''){
        			   ajaxPostData({
                           url: '../budgsysset//budgsysset/updateSetBudgSetControl.do',
                            data: { 
                           	 budg_year:'${budg_year}',
                           	 plan_code:'${plan_code}',
                           	 mod_code:'${mod_code}',
                           	 link_code:'${link_code}',
                            	cont_l:cont_l.getValue(),
                            	cont_p:cont_p.getValue(),
                            	cont_w:cont_w.getValue(),
                            },
                            success: function (responseData) {

                                params = [
                                        
                                          { name: 'plan_code', value: '${plan_code}' },
                                          { name: 'budg_year', value: '${budg_year}' },
                                          { name: 'mod_code', value: '${mod_code}' }
                                         

                                      ];
                           		var curIndex = parent.$.etDialog.getFrameIndex(window.name);
                       			parent.$.etDialog.close(curIndex);
                       			var parentWindow = parent.window[parentFrameName];
                       			
                       			parentWindow.child_grid.loadData(params,"queryBudgCDet.do");                              
                       			parentWindow.child_grid.refreshView();
                              },
                        })	
        		}
             
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
                <td class="label edistd">控制方式：</td>
				<td class="ipt edistd">
					<select id="cont_w" style="width: 180px;"></select>
				</td>
                
					
                </tr>
               <tr>
                 <td class="label ">控制层次：</td>
				<td class="ipt">
					<select id="cont_l" style="width: 180px;"></select>
				</td>
				
			
               </tr>
               <tr>
               	<td class="label edistd">控制期间：</td>
				<td class="ipt edistd">
					<select id="cont_p" style="width: 180px;"></select>
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