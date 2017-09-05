app.controller('textCtrl', ['$scope', 'httpReqService',
                  function($scope, httpReqService) {
  $scope.names = ['andrew','andrewbarnie','barnie','bill','fred','princess','ted','warrior','zina'];
  $scope.greeting = 'hello';

  function setData() {
	  return function(data) {
		  $scope.property = {};
		  $scope.property.name = data['Bad Paths'][0]['name'];
	  }
  }
  
  
  
  
  
  
  $scope.getFiles = function(){
	  httpReqService.files();

  };

  $scope.getAllProps = function() {
	  httpReqService.allProp(setData());
  };
  

  
  $scope.getProperties = function() {
	  httpReqService.prop();
  };
  
}]);

app.controller('dirFormCtrl', ['$scope', 'httpReqService',
			function($scope, httpReqService) {
	  $scope.initialize = function() {
		  httpReqService.setup($scope.directory);
	  };
}]);

