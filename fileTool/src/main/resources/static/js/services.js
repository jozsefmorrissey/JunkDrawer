app.service('httpReqService', ['$http',  function($http) {
	console.log("hi");
  /**
   * Request checkin data from rest controller.
   */
  this.setup = function(filename) {
  
	  var req = {
		  method: 'POST',
		  url: '/property/setup',
		  headers: {
		    'Content-Type': undefined
		  },
		  data: { name: filename }
		 }

		 $http(req).then((response) => {
		      console.log(response.data);
		    });
  }
  
  this.files = function() {
	    $http({
	      method: 'GET',
	      url: '/property/fileMap',
	    }).then((response) => {
	      console.log(response.data);
	    });
	  }
  
  this.allProp = function(func) {
	    $http({
	      method: 'GET',
	      url: '/property/map/all',
	    }).then((response) => {
	      console.log(response.data);
	      func(response.data);
	    });
	  }
  
  this.prop = function(name) {
	    $http({
	      method: 'GET',
	      url: '/property/map',
	    }).then((response) => {
	      console.log(response.data);
	    });
	  }
   
}]);



