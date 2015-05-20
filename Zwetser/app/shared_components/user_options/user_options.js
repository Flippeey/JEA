'use strict';

var mod_userOptions = angular.module('user_options', []);

mod_userOptions.directive('userOptions', function () {
    return {
        restrict: 'E',
        templateUrl: 'shared_components/user_options/user_options.html',
        controller: 'UserOptionsController'
    };
});

mod_userOptions.controller('UserOptionsController', ['$scope', 'ZwetserAPI', 'authFactory', function ($scope, ZwetserAPI, authFactory) {
        if (authFactory.isAuthenticated()) {
            authFactory.getSessionUser(authFactory.getAuthData()).success(function (result) {
                $scope.sessionUser = result;
            });
        }
        $scope.canFollow = function () {
            if (!angular.isUndefined($scope.user)) {
                if (!angular.isUndefined($scope.user.followers)) {
                    var followers = $scope.user.followers;
                    for (var i = 0; i < followers.length; i++) {
                        if (followers[i].id === $scope.sessionUser.id) {
                            return false;
                        }
                    }
                    return true;
                }
            }
        };

        $scope.followUser = function () {
            var follow = {follower: $scope.sessionUser, followed: $scope.user};
            ZwetserAPI.addFollow({id: $scope.sessionUser.id}, follow).$promise.then(function (result) {
                $scope.$broadcast('addFollow', result);
            });
        };

        $scope.unFollowUser = function () {
            var follow = {follower: $scope.sessionUser, followed: $scope.user};
            ZwetserAPI.deleteFollow({id: $scope.sessionUser.id}, follow).$promise.then(function (result) {
                $scope.$broadcast('removeFollow', result);
            });
        };


    }]);