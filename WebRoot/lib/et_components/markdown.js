(function () {
	var toc = document.querySelector('div');
	var toc_a = toc.getElementsByTagName('a');

	initMoveBar();
	
	/**
	 * 初始化移动条
	 */
	function initMoveBar () {
		var moveBar = document.createElement('div');
		var tocWidth = toc.offsetWidth;

		moveBar.className = 'moveBar';
		moveBar.style.left = tocWidth + 'px';

		// 添加移动条
		toc.appendChild(moveBar);

		// 绑定鼠标事件
		var moveFlag = false;
		var _body = document.body;


		moveBar.addEventListener("mousedown", function (e) {
			var e = e || window.event;

			moveFlag = true;
			// 设置禁止选择的class
			_body.className = 'moveing';
			moveBar.className = 'moveBar moveing';
		});
		_body.addEventListener("mouseup", function (e) {
			var e = e || window.event;

			moveFlag = false;
			_body.className = '';
			moveBar.className = 'moveBar';
		});

		_body.addEventListener("mousemove", function (e) {
			if (moveFlag) {
				var e = e || window.event;
				var offsetWidth = e.clientX;
				
				toc.style.width = offsetWidth + 'px';
				moveBar.style.left = offsetWidth + 'px';
			}
		});
	}

	/**
	 * 绑定点击高亮事件
	 */
	toc.addEventListener("click", function (e) {
		var e = e || window.event;
		var target = e.target || e.srcElement;

		if (target.nodeName.toLowerCase() === 'a') {

			Array.prototype.forEach.call(toc_a, function (item, index) {
				item.className = '';
			})

			target.className = 'active';
		}
	});
})()
