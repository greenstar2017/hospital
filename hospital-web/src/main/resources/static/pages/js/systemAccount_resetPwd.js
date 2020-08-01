app.controller('SystemResetPwdCtrl', ['$scope', '$http', '$stateParams', '$state', '$filter', 'toaster', 
                                    function($scope, $http, $stateParams, $state, $filter, toaster) {
	
	CommonRequest.AngularBaseCtrl.call( this, $scope,$http,toaster);
	
	$scope.data = {};
	
	$scope.save = function(){
		if($scope.data.newPwd != $scope.data.newPwdConfirm) {
			layer.msg("两次密码不一致，请确认");
			return false;
		}
		$scope.postRequest({url: "/resetPwd.do", data: $.param($scope.data)}, function(resp){
			if(resp.state == 0) {
				$scope.toaster_success();
				setTimeout(function(){
					window.location.href = "/logout";
				}, 500);
			}
		}, function(resp){
			$scope.toaster_error(resp.message);
		})
	}
	
}]);
