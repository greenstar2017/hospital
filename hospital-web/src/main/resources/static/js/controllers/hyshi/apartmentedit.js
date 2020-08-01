app.controller('ApartmentEditCtrl', ['$scope', '$http', '$state', '$stateParams', function($scope, $http, $state, $stateParams) {
	$scope.form = {};
	$scope.form.id = $stateParams.id||'';
	$scope.form.name = $stateParams.name||'';
	$scope.form.code = $stateParams.code||'';
	
	$scope.save = function(){
		$state.go('app.hyshi.apartmentmng');
	}
}]);