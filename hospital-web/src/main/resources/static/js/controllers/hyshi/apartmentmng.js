app.controller('ApartmentMngCtrl', ['$rootScope','$scope', '$http', '$state', '$stateParams', function($rootScope,$scope, $http, $state, $stateParams) {
	
	$scope.params = $rootScope.conditon||{pageNo:1,pageSize:10,totalItems:100};
	
	$scope.getStart = function(){
		return ($scope.params.pageNo - 1) * $scope.params.pageSize;
	}
	
	$scope.getEnd = function(){
		return $scope.params.pageNo * $scope.params.pageSize;
	}
	
	$scope.dataList = [];
	for(var i=$scope.getStart(); i<$scope.getEnd(); i++){
		$scope.dataList.push({
			'id':i,
			'name':'部门名称' + i,
			'code':'部门编号' + i
		});
	}
	
	
	$scope.paginationConf = {
            currentPage:$scope.params.pageNo,
            totalItems: $scope.params.totalItems,
            itemsPerPage:$scope.params.pageSize,
            pagesLength:$scope.params.pageSize,
            perPageOptions: [10, 15, 20, 25, 30, 50],
            onChange: function(){
            	if(($scope.paginationConf.currentPage != 0 && $scope.params.pageNo != $scope.paginationConf.currentPage) || 
            			$scope.params.pageSize != $scope.paginationConf.itemsPerPage) {//防止初始化时被调用
            		
            		$scope.params.pageNo = $scope.paginationConf.currentPage;
            		//保留列表页条件
            		$rootScope.conditon = $scope.params;
            		
            		$scope.dataList = [];
            		for(var i=$scope.getStart(); i<$scope.getEnd(); i++){
            			$scope.dataList.push({
            				'name':'部门名称' + i,
            				'code':'部门编号' + i
            			});
            		}
            	}
            }
	};
	
	$scope.goEdit = function(data) {
		$state.go('app.hyshi.apartmentedit', data);
	}
}]);