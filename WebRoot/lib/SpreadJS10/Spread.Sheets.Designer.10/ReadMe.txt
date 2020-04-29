Spread.Sheets 在线表格编辑器
--------------

概述
----
基于HTML5的Web前端应用程序。
以“所见即所得”的设计思想以及类Excel的用户体验，让用户能快速方便的设计自己的 Spread.Sheets 模板。
并提供导入导出功能，将设计产物保存至Excel或 Spread.Sheets 支持文件。

组件使用
----------------------------------------------------------------------------------------
1. Knockout: 使用绑定（data-bind）支持本地化。
2. JQuery: 便捷对HTML元素的操作。
3. JQuery UI: 提供菜单、对话框等。
4. File Saver: 完成对保存本地文件的支持。
5. Z-Tree: 提供树型结构的显示和操作。
6. Spread.Sheets v10: 提供类似Excel的纯Web前端组件。
7. Spread.Sheets v10 Client Side Excel IO: 提供纯前端的Excel导入/导出功能。
8. 其他封装的组件：
	a. Ribbon: 提供类似Excel的UI和功能（命令）（ ./src/ribbon目录 ）；
	b. Spread.Sheets 相关封装 （./src/spreadWrapper目录）
	c. Color Picker: 提供对颜色选择的支持（ ./src/widgets/colorpicker目录 ）；
	d. Border Picker: 提供边框设定的支持（ ./src/widgets/borderpicker目录 ）；
	e. Status Bar: 提供对状态条的支持（ ./src/statusBar目录 ）；
	f. 其他 ...

开发相关内容
-----------
Spread.Sheets 在线表格编辑器应用程序使用纯前端技术实现，不需要任何插件便可运行在 HTML5 支持的浏览器中。
如需运行,部署或调试，请使用Web Server部署并运行。


浏览器限制
---------
基于Web的应用，对本地文件的操作会受到限制同时依赖于浏览器的支持。因此要使用文件相关操作则需要使用支持File API的现代浏览器（IE 10+，Chorme, Firefox）。

应用程序本地化
-------------
1. 不同语言的产品资源被定义在不同的资源文件中，见 "./common/resources.xx.js".
2. 不同语言通过在index页面中引入不同资源文件而实现本地化。
3. "./index/index.html"为中文版。
4. "./index/index_en.html"为英文版。