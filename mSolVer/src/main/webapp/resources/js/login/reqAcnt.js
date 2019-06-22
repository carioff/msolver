var obj_NgApp = angular.module('app_reqAcnt', []);

obj_NgApp.config(['$httpProvider', function($httpProvider) {
    $httpProvider.defaults.headers.common['X-Requested-With'] = 'XMLHttpRequest';
}]);

obj_NgApp.controller('ctr_reqAcnt', function($scope, $http, $document) {
	
	var ctrUrl = 'reqAcnt'; 

	var chkIdDup = "N";//ID중복체크용
	
	var commonHttpPostSender = function($http, ctrUrl, dataObj, afterSuccessFunc, afterErrorFunc) {

		if (typeof (afterSuccessFunc) == 'undefined') {
			var afterSuccessFunc = function(returnData) {
				exceptionHandler(returnData.RESULT);
			};
		}

		if (typeof (afterErrorFunc) == 'undefined') {
			var afterErrorFunc = function(status) {
				alert('error: ' + status);
			};
		}

		$http.post(ctrUrl, dataObj).success(afterSuccessFunc).error(afterErrorFunc);
	};

	$scope.addUser = function() {
		
		if(chkIdDup == "N"){
			bootbox.alert("ID 중복체크가 필요힙니다.");
			return;
		}
		
		if ($scope.layer_input.password == null || $scope.layer_input.password.replace(/ /gi,"") == "") {
			bootbox.alert('사용자 암호로 공백은 사용할 수 없습니다.');
		    return;
		}
		
		/*암호 체크 로직*/
		var pwdPattern = /^[a-zA-Z0-9!@#$%^&*()?_~]{8,16}$/g;
		if(!pwdPattern.test($scope.layer_input.password)) {
			bootbox.alert("암호는 8~16자 영문자 또는 숫자이어야 합니다.");
			 return;
		}
		  // 영문, 숫자, 특수문자 2종 이상 혼용
        var chk = 0;
        if($scope.layer_input.password.search(/[a-zA-Z0-9]/g) != -1 ) chk ++;
//        if($scope.layer_input.password.search(/[a-z]/ig)  != -1 ) chk ++;
        if($scope.layer_input.password.search(/[!@#$%^&*()?_~]/g)  != -1  ) chk ++;
        if(chk < 2)
        { 
        	bootbox.alert("비밀번호는 (숫자+영문)과 특수문자를 혼용하여야 합니다."); 
            return;
        }
         
        // 동일한 문자/숫자 4이상, 연속된 문자
        if(/(\w)\1\1\1/.test($scope.layer_input.password) || isContinuedValue($scope.layer_input.password))
        {
        	bootbox.alert("비밀번호에 4자 이상의 연속 또는 반복 문자 및 숫자를 사용하실 수 없습니다."); 
            return;
        }
         
        // 아이디 포함 여부
        if($scope.layer_input.password.search($scope.layer_input.userId)>-1)
        {
        	bootbox.alert("ID가 포함된 비밀번호는 사용하실 수 없습니다."); 
            return;
        }
        
		var dataObj = {};
		var paramDataObj = {};

		addDataObj(jQuery, paramDataObj, "SVC_ID", "addUser");
		addDataObj(jQuery, dataObj, "PARAM_MAP", paramDataObj);

		var layerInputObj = [];
		layerInputObj[0] = $scope.layer_input;
		addDataObj(jQuery, dataObj, "layer_input", layerInputObj);
		
		if(lengthCheck(dataObj.layer_input, {
			userId: 25, password: 25, userName: 50, email: 30
			})) return;
		if(mandantoryColumnCheck(dataObj.layer_input, ["userId", "password", "userName"], ["ID", "PW", "NAME"])) return;
		
		var afterSuccessFunc = function(returnData) {

			if(returnData.VARIABLE_MAP.userId != $scope.layer_input.userId){	
				bootbox.alert(returnData.VARIABLE_MAP.userId + "!!! 계정신청이 실패하였습니다. 관리자에게 문의하세요.");
				$scope.layer_input.userId = "";
				$scope.layer_input.password = "";
				$scope.layer_input.userName = "";
				$scope.layer_input.email = "";
				chkIdDup = "N";
			}
			else{
				bootbox.alert(returnData.VARIABLE_MAP.userId + "님의 계정신청이 되었습니다. 관리자의 확인 후 로그인 가능합니다.");
				chkIdDup = "Y";
			}
			
			setTimeout(function() {
				window.location.replace(getContextPath());
			}, 3000);

		};
		
		commonHttpPostSender($http, ctrUrl, dataObj, afterSuccessFunc);

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
    
	$("#modal-background, #modal-close, #modal-close2").click(function () {

//		$("#modal-content,#modal-background").toggleClass("active");
		window.location.replace(getContextPath());
	});
	
	$scope.chkIdDup = function() {
//		var pattern = /^[A-Za-z0-9]{4,50}$/;
//		var pattern = /^[A-Za-z]{1}[A-Za-z0-9]{3,19}$/;
		var pattern = /^[A-za-z]+[A-Za-z0-9]{3,19}$/g;
		
		if(!pattern.test($scope.layer_input.userId)) {
			bootbox.alert("아이디는 영문자로 시작하는 4~20자 영문자 또는 숫자이어야 합니다.");
//			 bootbox.alert('영문(대,소문자) + 숫자만 입력가능하며 첫번째 문자는 영문만 가능합니다.');
			 return;
		}

		var dataObj = {};
		var paramDataObj = {};
		addDataObj(jQuery, paramDataObj, "SVC_ID", "chkIdDup");
		addDataObj(jQuery, dataObj, "PARAM_MAP", paramDataObj);

		var layerInputObj = [];
		layerInputObj[0] = $scope.layer_input;
		addDataObj(jQuery, dataObj, "layer_input", layerInputObj);

		var afterSuccessFunc = function(returnData) {
			exceptionHandler(returnData.RESULT, "chkIdDup", "N");
			if(returnData.VARIABLE_MAP.userId == "Y"){	
				bootbox.alert($scope.layer_input.userId + " 은(는) 사용할 수 없는 아이디 입니다");
				$scope.layer_input.userId = "";
			}
			else{
				bootbox.alert($scope.layer_input.userId + " 은(는) 사용할 수 있는 아이디 입니다.");
				chkIdDup = "Y";
			}

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
		};
	};
	
 	$document.ready(function() {

 		$scope.modalModelInit();
		chkIdDup = "N";
 	});
 	
});