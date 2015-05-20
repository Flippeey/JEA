'use strict';

var mod_timeline = angular.module('timeline', ['tweet']);

mod_timeline.directive('timeLine', function () {
    return {
        restrict: 'E',
        templateUrl: 'components/timeline/timeline.html',
        controller: 'TimeLineController'
    };
});

mod_timeline.controller('TimeLineController', ['$scope', 'ZwetserAPI', 'popupService', '$rootScope', '$state', 'TweetFactory', function ($scope, ZwetserAPI, popupService, $rootScope, $state, TweetFactory) {

//set current tab to timeline.
        $scope.tab = 1;
        //this function is called when a tab is selected, and sets it.
        $scope.selectTab = function (setTab) {
            $scope.tab = setTab;
        };
        //checks if tab is selected
        $scope.isSelected = function (checkTab) {
            return $scope.tab === checkTab;
        };
        //loads the tweets for this view, based on the selected tab.
        $scope.loadTweets = function () {
            if (!angular.isUndefined($scope.user)) {
                switch ($scope.tab) {
                    //timeline: select all tweets from the users whom are followed by the user.
                    case 1:
                        ZwetserAPI.getTimeline({id: $scope.user.id}).$promise.then(function (result) {
                            $scope.tweets = result;
                        });
                        break;
                        //your zweets: select all tweets for the user;    
                    case 2:
                        ZwetserAPI.getTweets({id: $scope.user.id}).$promise.then(function (result) {
                            $scope.tweets = result;
                        }).catch(function (error) {
                            console.log(error);
                        });
                        break;
                        //@mentions: gets all tweets where the current user is mentioned with @username
                    case 3:
                        ZwetserAPI.getMentions({id: $scope.user.username}).$promise.then(function (result) {
                            $scope.tweets = result;
                        }).catch(function (error) {
                            console.log(error);
                        });
                        break;
                        //moderator get all tweets
                    case 4:
                        ZwetserAPI.getAllTweets().$promise.then(function (result) {
                            $scope.tweets = result;
                        }).catch(function (error) {
                            console.log(error);
                        });
                        break;
                    default:
                        ZwetserAPI.getTimeline({id: $scope.user.id}).$promise.then(function (result) {
                            $scope.tweets = result;
                        });
                }
                //set data for live tweet
                $scope.tweet =
                        {
                            message: "",
                            postedBy: $scope.user.id,
                            postedFrom: $scope.user.username + "-PC",
                            screenName: $scope.user.username
                        };
            }
        };
        //Watch for changes to the tab variable. When a different tab is selected new tweets need to be loaded. 
        $scope.$watch('tab', function () {
            $scope.loadTweets();
        });
        //Watch for changes to the user variable. When a new user is selected new tweets need to be loaded. 
        $scope.$watch('user', function () {
            $scope.loadTweets();
        });
        //Watches for a broadcast callback after a new tweet has been posted, so the tweets can be reloaded.
        $scope.$on('newTweet', function (event, data) {
            $scope.loadTweets();
        });
        //Watches for a broadcast for when the user wants to edit a tweet.
        $scope.$on('clickedTweet', function (event, tweet) {
            $scope.tweet = tweet;
        });

        //Post tweet
        $scope.postTweet = function () {
            //check if tweet meets the requirements for posting a tweet.
            var tweetcheck = TweetFactory.checkTweetLength($scope.tweet);
            if (tweetcheck === ("success")) {
                //check if the tweet already has an ID, if it doesn't then add a new tweet.
                if (angular.isUndefined($scope.tweet.id)) {
                    ZwetserAPI.addTweet({id: $scope.user.id}, $scope.tweet).$promise.then(
                            function (value) {
                                $rootScope.$broadcast('newTweet', value);
                            }, function (error) {
                        console.log("Error posting tweet");
                    });
                    //if the tweet has an id already, that means it needs to be updated.
                } else {
                    ZwetserAPI.updateTweet({id: $scope.user.id}, $scope.tweet).$promise.then(
                            function (value) {
                                $rootScope.$broadcast('newTweet', value);
                            }, function (error) {
                        console.log("Error posting tweet");
                    });
                }
                //select tab 2 so the user can see the fruits of his efforts.
                $scope.selectTab(2);
                //reset the preview tweet.
                $scope.tweet =
                        {
                            message: "",
                            postedBy: $scope.user.id,
                            postedFrom: $scope.user.username + "-PC",
                            screenName: $scope.user.username
                        };
                //if the tweet doesn't meet the requirements show a popup informing the user that he needs to take action.
            } else {
                popupService.showPopup(tweetcheck);
            }
        };
    }]);