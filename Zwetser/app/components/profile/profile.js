'use strict';

var mod_profile = angular.module('profile', ['zwetser_navbar', 'user_stats', 'user_info', 'user_details', 'user_follow', 'timeline', 'sockets']);

mod_profile.controller('ProfileController', function ($scope, $state, authFactory, popupService, ZwetserAPI) {
    //The user of this view is the logged in user stored in the session.
    if (authFactory.isAuthenticated()) {
        authFactory.getSessionUser(authFactory.getAuthData()).success(function (result) {
            $scope.user = result;
            if ($scope.user.role === "MODERATOR") {
                $scope.isModerator = true;
            } else {
                //get the followers for this user.
                ZwetserAPI.getFollowers({id: $scope.user.id}).$promise.then(function (result) {
                    $scope.user.followers = result;
                }).catch(function (error) {
                    console.log(error);
                });

                //get the following for this user.
                ZwetserAPI.getFollowing({id: $scope.user.id}).$promise.then(function (result) {
                    $scope.user.following = result;
                }).catch(function (error) {
                    console.log(error);
                });

                //get the tweets for this user to display the count in user info.
                ZwetserAPI.getTweets({id: $scope.user.id}).$promise.then(function (result) {
                    $scope.user.tweets = result;
                }).catch(function (error) {
                    console.log(error);
                });
            }
        });
    }

    //when a follower is selected it's time to go to view 1
    $scope.goToUser = function (id) {
        $state.go('users', {id: id});
    };

});
