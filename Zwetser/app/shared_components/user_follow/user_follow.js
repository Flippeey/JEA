'use strict';

var mod_userFollow = angular.module('user_follow', []);

mod_userFollow.directive('userFollow', function () {
    return {
        restrict: 'E',
        templateUrl: 'shared_components/user_follow/user_follow.html',
        controller: 'UserFollowController'
    };
});

mod_userFollow.controller('UserFollowController', ['$scope', 'ZwetserAPI', function ($scope, ZwetserAPI) {
        $scope.nofollowers = "You have no followers";
        $scope.nofollowing = 'You are not following anyone';
    }]);