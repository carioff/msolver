var obj_NgApp = angular.module('app_chnPwd', []);

obj_NgApp.config(['$httpProvider', function($httpProvider) {
    $httpProvider.defaults.headers.common['X-Requested-With'] = 'XMLHttpRequest';
}]);

obj_NgApp.controller('ctr_chnPwd', function($scope, $http, $document, $window, $q, $location) {
	
	var ctrUrl = 'chnPwd.do'; 
	
	var hshelper_cd;
	
//	$scope.getParams = $routeParams;
	
	var commonHttpPostSender = function($http, ctrUrl, dataObj, afterSuccessFunc, afterErrorFunc) {

		if (typeof (afterSuccessFunc) == 'undefined') {
			var afterSuccessFunc = function(returnData) {
				exceptionHandler(returnData.RESULT);
			};
		}

		if (typeof (afterErrorFunc) == 'undefined') {
			var afterErrorFunc = function(data, status, headers, config) {
				alert('error: ' + status);
			};
		}

		$http.post(ctrUrl, dataObj).success(afterSuccessFunc).error(afterErrorFunc);
	};

    function isContinuedValue(value) {
        console.log("value = " + value);
        var intCnt1 = 0;
        var intCnt2 = 0;
        var temp0 = "";
        var temp1 = "";
        var temp2 = "";
        var temp3 = "";
 
        for (var i = 0; i < value.length-3; i++) {
            console.log("=========================");
            temp0 = value.charAt(i);
            temp1 = value.charAt(i + 1);
            temp2 = value.charAt(i + 2);
            temp3 = value.charAt(i + 3);
 
            console.log(temp0)
            console.log(temp1)
            console.log(temp2)
            console.log(temp3)
 
            if (temp0.charCodeAt(0) - temp1.charCodeAt(0) == 1
                    && temp1.charCodeAt(0) - temp2.charCodeAt(0) == 1
                    && temp2.charCodeAt(0) - temp3.charCodeAt(0) == 1) {
                intCnt1 = intCnt1 + 1;
            }
 
            if (temp0.charCodeAt(0) - temp1.charCodeAt(0) == -1
                    && temp1.charCodeAt(0) - temp2.charCodeAt(0) == -1
                    && temp2.charCodeAt(0) - temp3.charCodeAt(0) == -1) {
                intCnt2 = intCnt2 + 1;
            }
            console.log("=========================");
        }
 
        console.log(intCnt1 > 0 || intCnt2 > 0);
        return (intCnt1 > 0 || intCnt2 > 0);
    };
    
	$scope.saveCd = function() {
		
		if ($scope.layer_input.USR_PWD == undefined || $scope.layer_input.USR_PWD == null) {
			bootbox.alert("암호를 입력해 주세요.");
			$("#inp_USR_PWD").focus();
			return;
		}
		
		if ($scope.layer_input.USR_PWD_CONFIRM == undefined || $scope.layer_input.USR_PWD_CONFIRM == null) {
			bootbox.alert("암호확인을 입력해 주세요.");
			$("#inp_USR_PWD_CONFIRM").focus();
			return;
		}
		
		if($scope.layer_input.USR_PWD != $scope.layer_input.USR_PWD_CONFIRM ) {
			bootbox.alert("암호와 암호확인이 서로 다릅니다. 확인해주세요.");
			$("#inp_USR_PWD").focus();
			return;
		}

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
//        if($scope.layer_input.USR_PWD.search(/[a-z]/ig)  != -1 ) chk ++;
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
		
		var dataObj = {};
		var paramDataObj = {};

		addDataObj(jQuery, paramDataObj, "SVC_ID", "chnPwd");
		addDataObj(jQuery, paramDataObj, "USR_ID", $scope.layer_input.USR_ID);
		addDataObj(jQuery, paramDataObj, "USR_PWD", $scope.layer_input.USR_PWD);

		addDataObj(jQuery, dataObj, "PARAM_MAP", paramDataObj);
		
		var afterSuccessFunc = function(returnData) {
			exceptionHandler(returnData.RESULT, "암호변경", "Y");
//			window.location.replace(getContextPath());
			setTimeout(function() {
				logOutFunc($http);
			}, 2000);
		};
		
		commonHttpPostSender($http, ctrUrl, dataObj, afterSuccessFunc);

	};
	
	$("#modal-background, #modal-close, #modal-close2").click(function () {

//		$("#modal-content,#modal-background").toggleClass("active");
		logOutFunc($http);
	});
	
/*	function getQueryParam(param) {
	    var result =  window.location.search.match(
	        new RegExp("(\\?|&)" + param + "(\\[\\])?=([^&]*)")
	    );

	    return result ? result[3] : false;
	};*/
	
	$scope.modalModelInit = function() {
		$scope.layer_input = {
			USR_PWD_CONFIRM:""
			,USR_PWD:""
			,USR_ID:""
			,uid:""
		};
		
	};
	
	$scope.getUsrId = function() {
		
		var dataObj = {};
		var paramDataObj = {};

		addDataObj(jQuery, paramDataObj, "SVC_ID", "getUsrId");

		addDataObj(jQuery, dataObj, "PARAM_MAP", paramDataObj);
		
		var afterSuccessFunc = function(returnData) {

			$scope.layer_input.USR_ID = returnData.VARIABLE_MAP.REG_USR_ID;
		};
		
		commonHttpPostSender($http, ctrUrl, dataObj, afterSuccessFunc);
	};
	
 	$document.ready(function() {
 		
 		$scope.modalModelInit();
 		$scope.getUsrId();
 		
 	});
 	
});