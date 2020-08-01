app.controller('SystemAccountMngCtrl', ['$rootScope','$scope', '$http', '$state', '$stateParams', 'toaster', 
                              function($rootScope,$scope, $http, $state, $stateParams, toaster) {

	CommonRequest.AngularBaseCtrl.call( this, $scope,$http,toaster);
	CommonDateTimeUtils.init.call(this, $scope,$http,toaster);
	
	$scope.params = {'page':1,'page.size':10};
	$scope.result = {};
	
	$scope.feeForm = {};
	
	$scope.feeOptions = {};
	
	//查询
	$scope.queryResult = function() {
		
		$scope.postRequest({url: "/console/systemAccount/systemAccountList.do", data: $.param($scope.params)}, function(resp){
			$scope.result = resp;
			$scope.paginationConf.totalItems = resp.totalElements;
		});
    }
	$scope.queryResult();
	
	//删除
	$scope.goDelete = function(data) {
		$scope.commonConfirm("确定删除吗", function(){
			$scope.postRequest({url: "/console/systemAccount/delSystemAccount.do?id="+data.id, data: ''}, function(resp){
				$scope.toaster_success();
				$scope.queryResult();
			});
		});
    }
	
	//分页
	$scope.commonPaginationConf();
	
	//跳转编辑页面
	$scope.goEdit = function(data) {
		$state.go('app.systemAccount_edit', {id:data.id});
	}
	
	$scope.changeDateFormatToStr = function(source, name) {
		if($scope.isNotNull(source)) {
			if(toString.apply(source) == '[object Date]') {
				$scope.feeForm[name] = $scope.DateToStr(source);
			}else if(typeof source == 'number'){
				$scope.feeForm[name] = $scope.DateToStr(new Date(source));
			}
		}
	}
}]);