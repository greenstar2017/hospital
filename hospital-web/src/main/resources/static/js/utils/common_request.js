'use strict';

function CommonRequest(){};

CommonRequest.AngularBaseCtrl = function ( $scope,$http,toaster,$localStorage ) {
	
	$scope.bingLinkedMenuEvent = function(){
		setTimeout(function(){
			angular.element(".linkedMenu").bind('click', function (event) {
				event.stopPropagation();
			});
		}, 1000);
	}
	$(document).ready(function(){
		$scope.bingLinkedMenuEvent();
	});
	
	$scope.toaster_success = function() {
		toaster.pop('success',
				'提示',
				'操作成功');
	};
	$scope.toaster_warn = function(msg) {
		toaster.pop('warning',
				'消息',
				msg);
	};
	$scope.toaster_error = function(msg) {
		if(undefined == msg || '' == msg) {
			msg = '系统异常';
		}
		toaster.pop('error',
				'消息',
				msg);
	};
	
	/**
	 * 确认弹框
	 */
	$scope.commonConfirm = function(title, delYesFun, noFun) {
		var inx = layer.confirm(title, {
            btn: ['确定','取消'], //按钮
            scrollbar: false,
            shadeClose:true
        }, delYes);
        function delYes(){
            layer.close(inx);
            if(delYesFun) {
            	delYesFun();
            }
        }
	}
	
	/**
	 * 分页参数
	 */
	$scope.commonPaginationConf = function() {
		$scope.paginationConf = {
				currentPage: $scope.params['page'],
				totalItems: 100000000,
				itemsPerPage: $scope.params['page.size'],
				pagesLength: $scope.params['page.size'],
				perPageOptions: [10, 15, 20, 25, 30, 50],
				onChange: function(){
					if(($scope.paginationConf.currentPage != 0 && $scope.params['page'] != $scope.paginationConf.currentPage) || 
							$scope.params['page.size'] != $scope.paginationConf.itemsPerPage) {//防止初始化时被调用
						$scope.params['page'] = $scope.paginationConf.currentPage;
						$scope.params['page.size'] = $scope.paginationConf.itemsPerPage;
						$scope.queryResult();
					}
				}
		};
	}
  
  var requestFlag = false;
  $scope.postRequest = function(options, callback, failcallback){
	  if(requestFlag && !options.notRepeatRequestFlag){
		  $scope.toaster_error("查询中，请稍后。。。");
		  return false;
	  }
	  requestFlag = true;
		$http({
			method: 'POST' ,
			url: options.url ,
			timeout: 60000,
			data: options.data, // pass in data as strings
			headers: { 'Content-Type' : 'application/x-www-form-urlencoded; charset=UTF-8; ajax' }
		}).success( function (resp) {
			requestFlag = false;
			if(resp.state == 301) {
				var message = resp.message||"登录状态已过期，请重新登录";
				$scope.toaster_warn(message);
				setTimeout(function(){
					window.location.href = "/login.html";
				}, 500);
			}else if(resp.state == 0){
				if(undefined != callback) {
					callback(resp);
				}
			}else {
				if(undefined != failcallback) {
					failcallback(resp);
				}else {
					$scope.toaster_error(resp.message);
				}
			}
		}).error(function(data) {
			requestFlag = false;
			if(data.state == 301) {
				var message = data.message||"登录状态已过期，请重新登录";
				$scope.toaster_warn(message);
				setTimeout(function(){
					window.location.href = "/login.html";
				}, 500);
			}else {
				$scope.toaster_error(data.message);
			}
		});
	}
  
  //判断非空
  $scope.isNotNull = function(val) {
	  if(undefined != val && val.length != 0) {
		  return true;
	  }
	  return false;
  }
  

  //判断非空
  $scope.nullWithDefault = function(val, defaultVal) {
	  if($scope.isNotNull(val)) {
		  return val;
	  }
	  return defaultVal;
  }
  
  //记住历史
  $scope.commonMemorizeStore = function(ctrlName, storeData) {
	  if(!$localStorage.commonMemorize) {
		  $localStorage.commonMemorize = {};
	  }
	  $localStorage.commonMemorize[ctrlName] = storeData;
  }
  //获取历史
  $scope.commonMemorizeGet = function(ctrlName) {
	  if(!$localStorage.commonMemorize) {
		  $localStorage.commonMemorize = {};
	  }
	  if($localStorage.commonMemorize[ctrlName]) {
		  $scope.params = $localStorage.commonMemorize[ctrlName];
	  }
  }
  //清除历史
  $scope.commonMemorizeClear = function(ctrlName) {
	  if(!$localStorage.commonMemorize) {
		  $localStorage.commonMemorize = {};
	  }
	  
	  for(var key in $localStorage.commonMemorize[ctrlName]) {
		  delete $localStorage.commonMemorize[ctrlName][key];
	  }
  }
};
