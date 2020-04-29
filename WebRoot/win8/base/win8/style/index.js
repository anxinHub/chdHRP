
	window.onload = function () {
        absoluteCenter1("#enter");
        absoluteCenter("#pro_window");
        absoluteCenter("#pro_list");
        absoluteCenter(".solve_list");
    }
    $(window).resize(function () {

        absoluteCenter1("#enter");
        absoluteCenter("#pro_window");
        absoluteCenter("#pro_list");
        absoluteCenter(".solve_list");
    })
    function moveTarget(obj, targetX, targetY) {
        var osection = $(obj);
        var timer = null;
        var targetL = targetX + "px";
        var targetT = targetY + "px";
        $(obj).animate({
            left: targetL, top: targetY
        }
	, "normal");
    }

    function upstair() {
        moveTarget("#index_dl", 0, -1200);
        document.onmousemove = null;
        document.onmouseup = null;
    }
    function leftstairhrp() {
        //clearTimeout(timer);
        document.onmousemove = null;
        document.onmouseup = null;
        moveTarget("#pro", -1680, 0);
        moveTarget("#hrp", 0, 0);
    }
	function leftstairhtc() {
        //clearTimeout(timer);
        document.onmousemove = null;
        document.onmouseup = null;
        moveTarget("#pro", -1680, 0);
        moveTarget("#htc", 0, 0);
    }
	function leftstairhbi() {
        //clearTimeout(timer);
        document.onmousemove = null;
        document.onmouseup = null;
        moveTarget("#pro", -1680, 0);
        moveTarget("#hbi", 0, 0);
    }
	function leftstairhpm() {
        //clearTimeout(timer);
        document.onmousemove = null;
        document.onmouseup = null;
        moveTarget("#pro", -1680, 0);
        moveTarget("#hpm", 0, 0);
    }
    function solve() {
        //clearTimeout(timer);
        document.onmousemove = null;
        document.onmouseup = null;
        moveTarget("#pro", -1680, 0);
        moveTarget("#solve", 0, 0);
    }

    function backIn() {
        //clearTimeout(timer);
        document.onmousemove = null;
        document.onmouseup = null;
        moveTarget("#pro", 0, 0);
        moveTarget("#hrp", 1680, 0);
		moveTarget("#htc", 1680, 0);
		moveTarget("#hbi", 1680, 0);
		moveTarget("#hpm", 1680, 0);
        $("#pro_window ul li").css({ "transform": "scale(1)", "-webkit-transform": "scale(1)", "-moz-transform": "scale(1)", "-ms-transform": "scale(1)" });
    }

    function absoluteCenter(obj)
    {
        var windowHeight = $(window).height();
        var proHeight = $(obj).height();
        var centerTop = 0;
        if (windowHeight - 84 > proHeight) {
            centerTop = parseInt((windowHeight - proHeight - 84) / 2);
        }
        else {
            centerTop = parseInt((windowHeight - proHeight) / 2);
        }
        var pro_window = $(obj);
        if (centerTop > 0) {
            centerTop *= 1;
        }
        else {
            centerTop *= -1;
        }
        $(obj).css("padding-top", centerTop);

    }

    function absoluteCenter1(obj) 
    {
        var windowHeight = $(window).height();
        var proHeight = $(obj).height();
        var centerTop = parseInt((windowHeight - proHeight) / 2);
        var pro_window = $(obj);
        if (centerTop > 0) {
            centerTop *= 1;
        }
        else {
            centerTop *= -1;
        }
        $(obj).css("top", centerTop);

    }
    $(document).ready(function () {
	var str=window.location.href;
	var es=/ss=/;
	es.exec(str);
	var right=RegExp.rightContext;
	zxzh();
	var funcookie = right;
	switch (funcookie) {
		case "upstair": upstair();
			break;
		case "leftstair": upstair(); leftstair();
			break;
		case "solve": upstair(); solve();
			break;
	}
    });
    function zxzh() {
        $("#zxzx1 ul").html($('#hidcarousel').val());
        $("#syzh1 ul").html($('#hidzhanhui').val());
    }
    //var p = new etControl.process();