'use strict';

var mod_userDetails = angular.module('user_details', []);

mod_userDetails.directive('userDetails', function () {
    return {
        restrict: 'E',
        templateUrl: 'shared_components/user_details/user_details.html',
        controller: 'UserDetailsController'
    };
});

mod_userDetails.controller('UserDetailsController', [function () {
       
    }]);