var GC;
(function (GC) {
    (function (Spread) {
    (function (Sheets) {
        (function (designer) {
            (function (loader) {
                'use strict';
                loader.rootFolder = '../';
                var delayActions = [];
                var bindingApplied;

                function onReady(root) {
                    if (!bindingApplied) {
                        ko.applyBindings({ res: designer.res }, document.body);
                        ko.applyBindings({ res: designer.res }, document.head);
                    }

                    for (var i = 0; i < delayActions.length; i++) {
                        delayActions[i]();
                    }
                    delayActions = [];

                    if (!bindingApplied) {
                        setTimeout(function () {
                            root.find('.loading-placeholder').addClass('hidden');
                            app.notifyReady();
                        }, 0);
                    } else {
                        root.find('.loading-placeholder').addClass('hidden');
                        app.notifyReady();
                    }
                    bindingApplied = true;
                }
                function ready(func) {
                    delayActions.push(func);
                }
                loader.ready = ready;

                function getRootFolder() {
                    if (!loader.rootFolder) {
                        return './';
                    }

                    var folder = loader.rootFolder.trim();
                    if (folder.slice(-1) !== '/') {
                        folder = folder + '/';
                    }
                    return folder;
                }

                function loadContent(root) {
                    if (root === undefined) {
                        root = $(document);
                    }

                    var folder = getRootFolder();

                    var includes = root.find('[data-include]'), tasks = [], scripts = [], delayIncludes = [], i;

                    includes.each(function () {
                        var $this = $(this), include = $this.data('include'), script = $this.data('includeScript'), css = $this.data('includeCss');

                        var fileName = folder + include + '/' + include;
                        if (css) {
                            designer.util.loadCss(fileName + '.css');
                        }
                        tasks.push(designer.util.loadHtml(fileName + '.html', '.' + include, $this));
                        if (script) {
                            scripts.push(fileName);
                        }
                    });

                    return $.when.apply($, tasks).then(function () {
                        designer.ribbon.addLocalFonts();
                        if (scripts.length !== 0) {
                            var scriptTasks = scripts.map(function (s) {
                                return designer.util.loadScript(s + '.js');
                            });
                            $.when.apply($, scriptTasks).then(function () {
                                onReady(root);
                            });
                        } else {
                            onReady(root);
                        }
                    });
                }
                loader.loadContent = loadContent;
            })(designer.loader || (designer.loader = {}));
            var loader = designer.loader;
        })(Sheets.designer || (Sheets.designer = {}));
        var designer = Sheets.designer;
    })(Spread.Sheets || (Spread.Sheets = {}));
        var Sheets = Spread.Sheets;
    })(GC.Spread || (GC.Spread = {}));
    var Spread = GC.Spread;
})(GC || (GC = {}));
