'use strict';

var mod_sockets = angular.module('sockets', []);

mod_sockets.directive('sockets', function () {
    return {
        restrict: 'E',
        templateUrl: 'components/sockets/sockets.html',
        controller: 'SocketController',
        // observe and manipulate the DOM
        link: function ($scope, element, attrs) {
            $scope.$on('SocketUpdate', function (event, message) {
                var p = message;
                var el = element.children('.outputfield');
                el.append(p);
            });

        }
    };
});

mod_sockets.factory('SocketService', function ($rootScope) {
    var websocket = new WebSocket("ws://localhost:8080/Zwetser-Backend/echo-socket/tweets");
    websocket.onopen = function (evt) {
        var text = '<span>CONNECTED</span><br>';
        $rootScope.$broadcast('SocketUpdate', text);
    };
    websocket.onclose = function (evt) {
        var text = '<span>DISCONNECTED</span><br>';
        $rootScope.$broadcast('SocketUpdate', text);
        websocket.close();
    };
    websocket.onmessage = function (evt) {
        //convert json to javascript object
        var message = JSON.parse(evt.data);
        //write message.text to screen
        var text = '<span style="color: green;">RESPONSE: ' + message.text + '</span><br>';
        $rootScope.$broadcast('SocketUpdate', text);
    };
    websocket.onerror = function (evt) {
        var text = '<span style="color: red;">ERROR:</span> ' + event.data;
        $rootScope.$broadcast('SocketUpdate', text);
    };
    return {
        sendMessage: function (message) {
            var text = "<span>SENT: " + message + "</span><br>";
            var object = {text: message};
            var json = JSON.stringify(object);
            websocket.send(json);
            $rootScope.$broadcast('SocketUpdate', text);
        }
    };
});
mod_sockets.controller('SocketController', ['SocketService', '$scope', '$window', function (SocketService, $scope, $window) {
        $scope.input = "";

        $scope.doSend = function (message) {
            SocketService.sendMessage(message);
        };

    }]);