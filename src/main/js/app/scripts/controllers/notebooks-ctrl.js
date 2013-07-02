'use strict';

angular.module('jsApp')
    .controller('NotebookListCtrl', function ($scope, notebooks) {
        $scope.notebooks = notebooks.data;
    })
    .controller('NewNotebookCtrl', ['$scope','$http', function ($scope,$http) {
        $scope.notebook = { flashCards: [] };

        $scope.addFlashCard = function(){
            $scope.notebook.flashCards.push({});

        }

        $scope.removeFlashCard = function(index){
            $scope.notebook.flashCards.splice(index, 1);
        }

        $scope.save = function(notebook){

            notebook.created = Date.now();

            for ( var card in notebook.flashCards){
                delete notebook.flashCards[card]['$$hashKey'];
            }

            $http( { url: '/notebooks/new', method: 'get', params: {json: ""+JSON.stringify(notebook)} });
        }
    }])
    .config(['$routeProvider', function($routeProvider) {
        $routeProvider
            .when('/notebooks', {
                templateUrl: 'views/notebooks.html',

                resolve: {
                    notebooks: ['$http', function($http) {
                        return $http.get('/notebooks')
                    }]
                },
                controller: 'NotebookListCtrl'
            }).when('/notebook/new', {
                templateUrl: 'views/notebookForm.html',
                controller: 'NewNotebookCtrl'
            })

    }]);