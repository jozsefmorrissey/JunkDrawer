app.directive('fileTemplate', function() {
	return {
		templateUrl : 'modules/directorySelect.html'
	};
});

app.directive('fileSelect', function() {
	return {
		templateUrl : 'modules/fileSelect.html'
	};
});

app.directive('propertyDisplay', function() {
	return {
		templateUrl : 'modules/propertyDisplay.html'
	};
});

app.directive('propertyList', function() {
	return {
		templateUrl : 'modules/propertyList.html'
	};
});


// Ripped of stack overflow no idea how it works 
// https://stackoverflow.com/questions/18839048/how-to-read-a-file-in-angularjs
app.directive('fileSelect', ['$window', function ($window) {
    return {
        restrict: 'A',
        require: 'ngModel',
        link: function (scope, el, attr, ctrl) {
            var fileReader = new $window.FileReader();

            fileReader.onload = function () {
                ctrl.$setViewValue(fileReader.result);

                if ('fileLoaded' in attr) {
                    scope.$eval(attr['fileLoaded']);
                }
            };

            fileReader.onprogress = function (event) {
                if ('fileProgress' in attr) {
                    scope.$eval(attr['fileProgress'], 
                    {'$total': event.total, '$loaded': event.loaded});
                }
            };

            fileReader.onerror = function () {
                if ('fileError' in attr) {
                    scope.$eval(attr['fileError'], 
                    {'$error': fileReader.error});
                }
            };

            var fileType = attr['fileSelect'];

            el.bind('change', function (e) {
                var fileName = e.target.files[0];

                if (fileType === '' || fileType === 'text') {
                    fileReader.readAsText(fileName);
                } else if (fileType === 'data') {
                    fileReader.readAsDataURL(fileName);
                }
            });
        }
    };
}]);