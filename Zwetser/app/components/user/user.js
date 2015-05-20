'use strict';

var mod_user = angular.module('user', ['zwetser_navbar', 'user_stats', 'user_info', 'user_details', 'user_follow', 'tweet', 'user_options']);

mod_user.controller('UserController', ['$scope', '$stateParams', 'ZwetserAPI', '$state', function ($scope, $stateParams, ZwetserAPI, $state) {
        var id = $stateParams.id;
        //get the selected user
        ZwetserAPI.getUser({id: id}).$promise.then(function (result) {
            $scope.user = result;
            $scope.loadData();
        });

        $scope.loadData = function () {
            //get the tweets for this user.
            ZwetserAPI.getTweets({id: $scope.user.id}).$promise.then(function (result) {
                $scope.user.tweets = result;
            }).catch(function (error) {
                console.error(error);
            });
            //get the followers for this user.
            ZwetserAPI.getFollowers({id: $scope.user.id}).$promise.then(function (result) {
                $scope.user.followers = result;
            }).catch(function (error) {
                console.error(error);
            });
            //get the following for this user.
            ZwetserAPI.getFollowing({id: $scope.user.id}).$promise.then(function (result) {
                $scope.user.following = result;
            }).catch(function (error) {
                console.error(error);
            });
        };

        $scope.goToUser = function (id) {
            $state.go('users', {id: id});
        };

        $scope.$on('addFollow', function (event, data) {
           $scope.loadData();
        });
        
         $scope.$on('removeFollow', function (event, data) {
           $scope.loadData();
        });
    }]);


        