app.controller('textCtrl', ['$scope', '$filter', 'UtilsService', 'changeGreetingService', 'helloFac',
                  function($scope, $filter, UtilsService, changeGreetingService, helloFac) {
  $scope.names = ['andrew','andrewbarnie','barnie','bill','fred','princess','ted','warrior','zina'];
  $scope.greeting = 'hello';

  $scope.addName = function(name){
    $scope.popsicle = $scope.names;
    if($scope.names.indexOf(name) == -1) {
      $scope.names.push(name);

    UtilsService.myFunction();
    let newGreeting = changeGreetingService.modify();
    $scope.greeting = (newGreeting ? newGreeting : $scope.greeting);
    $filter('sortAlpha')($scope.names);
  }
};

  $scope.removeName = function(name) {
    index = $scope.names.indexOf(name);
    $scope.names.splice(index, 1);
  };

  $scope.hiFromTheFac = function() {
    return helloFac.sayHello("everybody");
  }
  
  $scope.naomi = {
    name: 'Naomi',
    address: '1600 Amphitheatre'
  };
  $scope.igor = {
    name: 'igor',
    address: 'Haunted house or evil lab'
  }
}]);
