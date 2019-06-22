var obj_NgApp = angular.module('app_login', []);

obj_NgApp.config(['$httpProvider', function($httpProvider) {
    $httpProvider.defaults.headers.common['X-Requested-With'] = 'XMLHttpRequest';
}]);

obj_NgApp.controller('ctr_login', function($scope, $document, $window) {
	
	function getContextPath() {
    	var hostname = window.location.hostname;
    	var contextEnd = window.location.pathname.indexOf("/", 2);
    
    	var returnVal = hostname;
    	if(window.location.port != 80) {
    		returnVal = returnVal + ':' + window.location.port;
    	}
    	if(contextEnd != -1) {
    		returnVal += window.location.pathname.substring(0, contextEnd);
    	}
    	returnVal = window.location.protocol + '//' + returnVal;
    	
    	return returnVal;
    }
	
	$document.ready(function() {
		
	});
	
	$scope.loginBtn = function() {
		
		var url = getContextPath() + '/login';
		var form = $(document.createElement('form'));
		$(form).attr("action", url);
    	$(form).attr("method", "POST");
	
		var userId = $("<input>")
		.attr("type", "hidden")
		.attr("name", "userId")
		.val($scope.inputUsrId);
		$(form).append($(userId));


		var password = $("<input>")
		.attr("type", "hidden")
		.attr("name", "password")
		.val($scope.inputUsrPwd);
		$(form).append($(password));

		form.appendTo( document.body );
		$(form).submit();

		};
	

	$(function(){
	    $("#reqNewAcntBtn").click(function(){
	    	$window.location.replace(getContextPath() + "/reqAcnt");
	    })
	});
	
});