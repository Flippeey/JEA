'use strict';

var mod_userInfo = angular.module('user_info', []);

mod_userInfo.directive('userInfo', function () {
    return {
        restrict: 'E',
        templateUrl: 'shared_components/user_info/user_info.html',
        controller: 'UserInfoController'
    };
});

mod_userInfo.controller('UserInfoController', [function () {
       
    }]);