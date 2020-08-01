app.filter('propsFilter', function() {
    return function(items, props) {
        var out = [];
        if (angular.isArray(items)) {
          $(items).each(function(i, item) {
            var itemMatches = false;

            var keys = Object.keys(props);
            for (var i = 0; i < keys.length; i++) {
              var prop = keys[i];
              var text = props[prop].toLowerCase();
              if (item[prop].toString().toLowerCase().indexOf(text) !== -1) {
                itemMatches = true;
                break;
              }
            }

            if (itemMatches) {
              out.push(item);
            }
          });
        } else {
          out = items;
        }

        return out;
    };
})

app.controller('SystemAccountEditCtrl', ['$scope', '$http', '$stateParams', '$state', '$filter', 'toaster', 
                                    function($scope, $http, $stateParams, $state, $filter, toaster) {
	
	CommonRequest.AngularBaseCtrl.call( this, $scope,$http,toaster);
	
	$scope.data = {};
	$scope.data.id = $stateParams.id||'';
	$scope.data.allowSiteSelected = [];
	
	if(undefined != $scope.data.id && $scope.data.id != '') {
		
		$scope.postRequest({url: "/console/systemAccount/systemAccountInfo.do", data: $.param($scope.data)}, function(resp){
			$scope.data.userAccount = resp.data.userAccount;
		});
	}else {
		$scope.data.type = 0;
	}
	
	$scope.cancel = function(){
		$state.go('app.systemAccount');
	}
	
	$scope.save = function(){
		var allowSite = "";
		if($scope.data.allowSiteSelected.length > 0) {
			for(var i=0; i<$scope.data.allowSiteSelected.length; i++) {
				allowSite += $scope.data.allowSiteSelected[i].key + ",";
			}
		}
		if(allowSite.length > 0) {
			allowSite = allowSite.substring(0, allowSite.length -1);
		}
		$scope.data.userAccount.allowSite = allowSite;
		$scope.postRequest({url: "/console/systemAccount/saveSystemAccount.do", data: $.param($scope.data.userAccount)}, function(resp){
			if(resp.state == 0) {
				$scope.toaster_success();
				setTimeout(function(){
					$state.go('app.systemAccount');
				}, 500);
			}
		}, function(resp){
			$scope.toaster_error(resp.message);
		})
	}
	
}]);
