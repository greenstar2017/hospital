'use strict';

function CommonDateTimeUtils() {
};

CommonDateTimeUtils.init = function($scope, $http, toaster) {

	/**
	 * 将yyy-MM-dd格式的字符串转换为日期
	 */
	$scope.strToDate = function(dateStr) {
		var dateStr = dateStr.replace(/-/g, "/");// 现将yyyy-MM-dd类型转换为yyyy/MM/dd
		var dateTime = Date.parse(dateStr);// 将日期字符串转换为表示日期的秒数
		// 注意：Date.parse(dateStr)默认情况下只能转换：月/日/年 格式的字符串，但是经测试年/月/日格式的字符串也能被解析
		var data = new Date(dateTime);// 将日期秒数转换为日期格式
		return data;
	}

	/**
	 * 将日期转化字符串(yyyy-MM-dd)
	 */
	$scope.DateToStr = function(date) {
		var year = date.getFullYear();
		var month = (date.getMonth() + 1).toString();
		var day = (date.getDate()).toString();
		if (month.length == 1) {
			month = "0" + month;
		}
		if (day.length == 1) {
			day = "0" + day;
		}
		var dateTime = year + "-" + month + "-" + day;
		return dateTime;
	}

	/**
	 * 日期加上天数后的新日期.
	 */
	$scope.AddDays = function(date, days) {
		var nd = new Date(date);
		nd = nd.valueOf();
		nd = nd + days * 24 * 60 * 60 * 1000;
		nd = new Date(nd);
		// alert(nd.getFullYear() + "年" + (nd.getMonth() + 1) + "月" +
		// nd.getDate() + "日");
		var y = nd.getFullYear();
		var m = nd.getMonth() + 1;
		var d = nd.getDate();
		if (m <= 9)
			m = "0" + m;
		if (d <= 9)
			d = "0" + d;
		var cdate = y + "-" + m + "-" + d;
		return cdate;
	}

	/**
	 * 两个日期的差值(d1 - d2).
	 */
	$scope.DateDiff = function(d1, d2) {
		var day = 24 * 60 * 60 * 1000;
		try {
			var dateArr = d1.split("-");
			var checkDate = new Date();
			checkDate.setFullYear(dateArr[0], dateArr[1] - 1, dateArr[2]);
			var checkTime = checkDate.getTime();

			var dateArr2 = d2.split("-");
			var checkDate2 = new Date();
			checkDate2.setFullYear(dateArr2[0], dateArr2[1] - 1, dateArr2[2]);
			var checkTime2 = checkDate2.getTime();

			var cha = (checkTime - checkTime2) / day;
			return cha;
		} catch (e) {
			return false;
		}
	}
	
	/**
	 * 格式化日期
	 */
	$scope.formatDateToStr = function(source) {
		var target = source;
		if($scope.isNotNull(source)) {
			if(toString.apply(source) == '[object Date]') {
				target = $scope.DateToStr(source);
			}else if(typeof source == 'number'){
				target = $scope.DateToStr(new Date(source));
			}
		}
		return target;
	}
};
