if (isDefaultTreeEnabled) {

    // Load the default deep tree ToC
    Modernizr.load([{
        load: ['stylesheets/webtoc.css',
                'script/desktop.webtoc.js'],
        complete: function () {
            constructDesktopToc();
        }
    }]);

}
else {

    // Loading in responsive mode so disable JQM page initialization
    $(document).bind('mobileinit', function () {
        $.mobile.autoInitializePage = false;
    });

    yepnope.insertBeforeElement = document.getElementById('responsive-marker');

    // Conditionally load either responsive or desktop tree
    switch (getDeviceType()) {

        case "MOBILE":
        case "TABLET":
            Modernizr.load([{
                load: ['http://code.jquery.com/mobile/1.1.1/jquery.mobile-1.1.1.min.css',
                        'http://code.jquery.com/mobile/1.1.1/jquery.mobile-1.1.1.min.js',
                        'script/responsive.webtoc.min.js'],
                complete: function () {
                    $('ul#root').css('display', 'none');
                    constructMobileToC();
                }
            }]);
            break;

        case "DESKTOP":
            Modernizr.load([{
                load: ['stylesheets/webtoc.css',
                        'script/desktop.webtoc.js'],
                complete: function () {
                    constructDesktopToc();
                }
            }]);
            break;
    }

}

if (isPostMessageEnabled()) {
    addMessageListener(tocMessageHandler);
}

/**
* @param {Object} event
**/
function tocMessageHandler(event) {
    var message = getMessage(event.data);

    switch (message.messageType) {
        case "syncToC":
            syncToCNode(message.messageData)
            break;
    }
}

/**
* @param {string} url
**/
function syncToCNode(url) {

    var anchor = $('div#container > ul a[href="' + url + '"]').first();
    if (anchor.length) {
        syncToCNodeImplementation(anchor);
    }

}