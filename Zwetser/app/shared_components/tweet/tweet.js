'use strict';

var mod_tweet = angular.module('tweet', []);

mod_tweet.directive('userTweet', function () {
    return {
        restrict: 'E',
        templateUrl: 'shared_components/tweet/tweet.html',
        controller: 'TweetController'
    };
});

mod_tweet.factory('TweetFactory', function () {
    var maxlength = 140;
    var minlength = 1;

    return {
        checkTweetLength: function (tweet) {
            if (tweet.message.length < minlength) {
                return "Your tweet is too short. Your tweet must be at least " + minlength + " character long.";
            } else {
                if (tweet.message.length > maxlength) {
                    return "Your tweet is too long. The limit is " + maxlength + " characters and your tweet contains " + tweet.message.length + " characters.";
                } else {
                    return "success";
                }
            }
        }        
    };
});

mod_tweet.controller('TweetController', ['$rootScope', '$scope', 'ZwetserAPI', '$state', 'popupService', function ($rootScope, $scope, ZwetserAPI, $state, popupService) {
        $scope.clickedTweet = function (tweet) {
            switch ($scope.tab) {
                //user clicked another user's tweet, so he goes to that user's profile.
                case 1:
                    $state.go('users', {id: tweet.postedBy});
                    break;
                    //user selects his own tweet: he wants to edit it.   
                case 2:
                    $rootScope.$broadcast('clickedTweet', tweet);
                    break;
                    //user selected a tweet in which he was mentioned, and goes to profile of the user who mentioned him.
                case 3:
                    $state.go('users', {id: tweet.postedBy});
                    break;
                    //moderator selects tweet: delete it.
                case 4:
                    if (popupService.showPopup('Are you sure you want to delete this tweet?')) {
                        ZwetserAPI.deleteTweet({id: tweet.id}, tweet).$promise.then(function (result) {
                            $scope.$broadcast('newTweet', result);
                        }).catch(function (error) {
                            console.log(error.toString());
                        });
                    }
                    break;
            }
        };
    }]);