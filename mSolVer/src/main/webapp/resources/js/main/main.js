var app = angular.module('app_main', [ 'ngRoute', 'ui.bootstrap' ]);

var newUsrPwd="N";//사용자 암호 신규 입력 체크
var client;

app.config(function($httpProvider) {
	$httpProvider.responseInterceptors.push('httpInterceptorForLoading');
	var spinnerFunction = function(data, headersGetter) {
		$('#loading').show();
		return data;
	};
	$httpProvider.defaults.transformRequest.push(spinnerFunction);
});

app.config([ '$provide', function($provide) {
	$provide.decorator('$cacheFactory', function($delegate) {
		$delegate.removeAll = function() {
			angular.forEach($delegate.info(), function(ob, key) {
				$delegate.get(key).removeAll();
			});
		}

		$delegate.destroyAll = function() {
			angular.forEach($delegate.info(), function(ob, key) {
				$delegate.get(key).destroy();
			});
		}
		return $delegate;
	});
} ])

app.config(['$httpProvider', function($httpProvider) {
    $httpProvider.defaults.headers.common['X-Requested-With'] = 'XMLHttpRequest';
}]);

app.factory('httpInterceptorForLoading', function($q, $window) {
	return function(promise) {
		return promise.then(function(response) {
			$('#loading').hide();
			return response;

		}, function(response) {
			$('#loading').hide();
			return $q.reject(response);
		});
	};
});

app.config(['$compileProvider', function($compileProvider) {
	$compileProvider
			.imgSrcSanitizationWhitelist(/^\s*(https?|ftp|file|blob):|data:image\//);
} ]);

app.directive('format', ['$filter', function ($filter) {
	return {
		require: '?ngModel',
		link: function (scope, elem, attrs, ctrl) {
			if (!ctrl) return;
			
			ctrl.$formatters.unshift(function (a) {
				return $filter(attrs.format)(ctrl.$modelValue);
			});
			
			ctrl.$parsers.unshift(function (viewValue) {
				var plainNumber = viewValue.replace(/[^\d|\-+|\.+]/g, '');
				elem.val($filter('number')(plainNumber));
				return plainNumber;
			});
		}
	};
}]);

app.controller('ctr_main', function($scope, $http, $document, $window, $location) {
	
	var ctrUrl = 'main';

	$scope.ChangeLocation = function(url){
	    window.location = url;
	}
	
	$(".collapse").on("show.bs.collapse", function() {
		$('.sub-menu').collapse("hide");
	});
	
	
	$scope.logOutBtn = function() {
		logOutFunc($http);
	};


	$scope.getUsrAcnt = function() {
		$("#main-modal-content,#main-modal-background").toggleClass("active");
		$("#main-modal-content,#main-modal-background").draggable();
		$("input[name=inp_main_password]").attr("readonly",true);//추후 암호리셋 기능 필요
	};
	
	$("#main-modal-background, #main-modal-close, #main-modal-close2").click(function () {
		$("#main-modal-content,#main-modal-background").toggleClass("active");
	});
	
	$scope.showRetainedMsg = function() {
		// Create a client instance
		client = new Paho.MQTT.Client('10.10.19.28', 1883, $scope.userId);

		// set callback handlers
		client.onConnectionLost = $scope.onConnectionLost;
		client.onMessageArrived = $scope.onMessageArrived;

		// connect the client
		client.connect({
			timeout:3,
			onSuccess:$scope.onConnect,
			onFailure:$scope.onFailure,
		});
//		$scope.layer_input.retainedMsg = 123123123; 
//		$("#main-modal-msg,#main-modal-msg-background").toggleClass("active");
//		$("#main-modal-msg,#main-modal-msg-background").draggable();
	};
	
	// called when the client connects
	$scope.onConnect = function() {
	  // Once a connection has been made, make a subscription and send a message.
	  console.log("onConnect");
	  client.subscribe("linux/test");
//	  message = new Paho.MQTT.Message("Hello");
//	  message.destinationName = "/test";
//	  client.send(message);
	};
	
	$scope.onFailure = function(message) {
		console.log('Connection Attempt to Host' +'10.10.19.28'+ 'Failed');
		setTimeout($scope.showRetainedMsg, 50000);
	};
	
	// called when the client loses its connection
	$scope.onConnectionLost = function(responseObject) {
	  if (responseObject.errorCode !== 0) {
	    console.log("onConnectionLost:"+responseObject.errorMessage);
	    setTimeout($scope.showRetainedMsg, 50000);
	  }
	};

	// called when a message arrives
	$scope.onMessageArrived = function(message) {
		console.log("onMessageArrived:"+message.payloadString);
		window.setTimeout(function() {
//			$scope.layer_input.retainedMsg = message.payloadString; //bindidng이 안됨 이유를 모르겠음...ㅂ
			let pushMsg = message.payloadString.split("|");
			$("#inp_siteId").val(pushMsg[0]);
			$("#inp_solVersion").val(pushMsg[1]);
			$("#inp_applyDate").val(pushMsg[2]);
			$("#inp_applyWorker").val(pushMsg[3]);
			$("#inp_applyContents").val(pushMsg[4]);
			$("#inp_exeCategory").text(pushMsg[5]);
			$("#main-modal-msg,#main-modal-msg-background").toggleClass("active");
			$("#main-modal-msg,#main-modal-msg-background").draggable();
		}, 1000);

	};
	
	$("#main-modal-msg-background, #main-modal-close3, #main-modal-close4").click(function () {
		$("#main-modal-msg, #main-modal-msg-background").toggleClass("active");
	});
	
	$scope.updateUsrAcnt = function() {
		
		var dataObj = {};
		var paramDataObj = {};

		addDataObj(jQuery, paramDataObj, "SVC_ID", "updateUsrAcnt");
		
		if(newUsrPwd == 'Y'){
			if ($scope.layer_input.USR_PWD == null || $scope.layer_input.USR_PWD.replace(/ /gi,"") == "") {
				bootbox.alert('사용자 암호로 공백은 사용할 수 없습니다.');
			    return;
			}
			
			/*암호 체크 로직*/
			var pwdPattern = /^[a-zA-Z0-9!@#$%^&*()?_~]{8,16}$/g;
			if(!pwdPattern.test($scope.layer_input.USR_PWD)) {
				bootbox.alert("암호는 8~16자 영문자 또는 숫자이어야 합니다.");
				 return;
			}
			  // 영문, 숫자, 특수문자 2종 이상 혼용
	        var chk = 0;
	        if($scope.layer_input.USR_PWD.search(/[a-zA-Z0-9]/g) != -1 ) chk ++;
//	        if($scope.layer_input.USR_PWD.search(/[a-z]/ig)  != -1 ) chk ++;
	        if($scope.layer_input.USR_PWD.search(/[!@#$%^&*()?_~]/g)  != -1  ) chk ++;
	        if(chk < 2)
	        { 
	        	bootbox.alert("비밀번호는 (숫자+영문)과 특수문자를 혼용하여야 합니다."); 
	            return;
	        }
	         
	        // 동일한 문자/숫자 4이상, 연속된 문자
	        if(/(\w)\1\1\1/.test($scope.layer_input.USR_PWD) || isContinuedValue($scope.layer_input.USR_PWD))
	        {
	        	bootbox.alert("비밀번호에 4자 이상의 연속 또는 반복 문자 및 숫자를 사용하실 수 없습니다."); 
	            return;
	        }
	         
	        // 아이디 포함 여부
	        if($scope.layer_input.USR_PWD.search($scope.layer_input.USR_ID)>-1)
	        {
	        	bootbox.alert("ID가 포함된 비밀번호는 사용하실 수 없습니다."); 
	            return;
	        }
			
			addDataObj(jQuery, paramDataObj, "USR_PWD", $scope.layer_input.USR_PWD);
		}
		
		addDataObj(jQuery, paramDataObj, "userId", $scope.layer_input.userId);
		addDataObj(jQuery, paramDataObj, "userName", $scope.layer_input.userName);
		addDataObj(jQuery, paramDataObj, "email", $scope.layer_input.email);
		
		addDataObj(jQuery, dataObj, "PARAM_MAP", paramDataObj);
		
		var layerInputObj = [];
		layerInputObj[0] = $scope.layer_input;
		
		addDataObj(jQuery, dataObj, "layer_input", layerInputObj);
		
		if(lengthCheck(dataObj.layer_input, {
			USR_NM: 50, DEPT_NM: 100, POSI_NM: 100
			})) return;
		if(mandantoryColumnCheck(dataObj.layer_input, ["USR_NM"], ["사용자 명"])) return;

		var afterSuccessFunc = function(returnData) {
			exceptionHandler(returnData.RESULT, "회원정보 업데이트", "Y");
			$("#main-modal-content,#main-modal-background").toggleClass("active");			
		};
		
		commonHttpPostSender($http, ctrUrl, dataObj, afterSuccessFunc);

	};
	
	$scope.modalModelInit = function() {
		$scope.layer_input = {
			 userId:""
			,password:""
			,userName:""
			,email:""
			,uid:""
			,retainedMsg:""
		};
		
	};
	
	$scope.getMenuList = function() {
		var dataObj = {};
		var paramDataObj = {};
		addDataObj(jQuery, paramDataObj, "SVC_ID", "getMenu");
		addDataObj(jQuery, dataObj, "PARAM_MAP", paramDataObj);
		
		var afterSuccessFunc = function(returnData) {
			exceptionHandler(returnData.RESULT, "", "N");
			$scope.upperMenuList = returnData.upperMenuList;
			$scope.subMenuList = returnData.subMenuList;
			$scope.userId = returnData.VARIABLE_MAP.userId;
			$scope.layer_input.userId = returnData.VARIABLE_MAP.userId;
			$scope.layer_input.userName = returnData.VARIABLE_MAP.userName;
			$scope.layer_input.password = returnData.VARIABLE_MAP.password;
			$scope.layer_input.email = returnData.VARIABLE_MAP.email;
			$scope.layer_input.retainedMsg = returnData.VARIABLE_MAP.retainedMsg;
			if($scope.userId != undefined 
					&& $scope.userId != null) {
				$scope.showRetainedMsg();
			}
		};
		
		commonHttpPostSender($http, ctrUrl, dataObj, afterSuccessFunc);
	};
	
	$scope.subscribeMqtt = function() {
		var dataObj = {};
		var paramDataObj = {};
		addDataObj(jQuery, paramDataObj, "SVC_ID", "subscribeMqtt");
		addDataObj(jQuery, dataObj, "PARAM_MAP", paramDataObj);
		
		var afterSuccessFunc = function(returnData) {
			exceptionHandler(returnData.RESULT, "", "N");
			$scope.retainedMsg = returnData.retainedMsg;
		};
		
		commonHttpPostSender($http, ctrUrl, dataObj, afterSuccessFunc);
	};
	
	$document.ready(function() {

		newUsrPwd="N";
		client = "";
		
		$scope.modalModelInit();
		
		$scope.getMenuList();
		
		/*// Create a client instance
		client = new Paho.MQTT.Client('10.10.19.28', 1883, 'TEST');

		// set callback handlers
		client.onConnectionLost = $scope.onConnectionLost;
		client.onMessageArrived = $scope.onMessageArrived;

		// connect the client
		client.connect({onSuccess:$scope.onConnect});*/
		

		/*// called when the client connects
		function onConnect() {
		  // Once a connection has been made, make a subscription and send a message.
		  console.log("onConnect");
		  client.subscribe("World");
		  message = new Paho.MQTT.Message("Hello");
		  message.destinationName = "World";
		  client.send(message);
		}

		// called when the client loses its connection
		function onConnectionLost(responseObject) {
		  if (responseObject.errorCode !== 0) {
		    console.log("onConnectionLost:"+responseObject.errorMessage);
		  }
		}

		// called when a message arrives
		function onMessageArrived(message) {
		  console.log("onMessageArrived:"+message.payloadString);
		}
		
		// Create a client instance
		client = new Paho.MQTT.Client(location.hostname, Number(location.port), "clientId");

		// set callback handlers
		client.onConnectionLost = onConnectionLost;
		client.onMessageArrived = onMessageArrived;

		// connect the client
		client.connect({onSuccess:onConnect});*/
		
	});	
	
});

app.config([ '$routeProvider', function($routeProvider) {
	$routeProvider.when('/msol/mngSiteSolVer', {
		templateUrl : 'msol/mngSiteSolVer',
		controller: 'ctr_mSiteSolVer'
	}).when('/msol/mSS', {
		templateUrl : 'msol/mSS',
		controller: 'ctr_mSS'
	}).otherwise({
		templateUrl : 'msol/mSS',
		controller: 'ctr_mSS'
	});
	
} ]);