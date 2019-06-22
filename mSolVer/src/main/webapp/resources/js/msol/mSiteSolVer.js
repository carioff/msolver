
app.controller('ctr_mSiteSolVer', function($scope, $http, $document, $window, $q) {
	
	var ctrUrl = 'msol/mngSiteSolVer';
	
	var hshelper_siteId;
	var hshelper_solVersion; 
	
//	var readOnlyYn;
	
	$scope.pageInitiation = function() {
		$scope.page_siteId = {};
		$scope.page_siteId.currentPage = 1;
		$scope.page_siteId.perPage = 20;
		$scope.page_siteId.totalItems = 0;
		
		
		$scope.page_solVersion = {};
		$scope.page_solVersion.currentPage = 1;
		$scope.page_solVersion.perPage = 20;
		$scope.page_solVersion.totalItems = 0;
	}
	
	$scope.getSiteList = function() {
		$scope.page_siteId.currentPage = 1; 
		
		var dataObj = {};
		var paramDataObj = {};
		addDataObj(jQuery, paramDataObj, "SVC_ID", "getSiteList");
		
		addDataObj(jQuery, paramDataObj, "siteId", $scope.input_siteId);
		addDataObj(jQuery, paramDataObj, "siteName", $scope.input_siteName);
		
		addDataObj(jQuery, paramDataObj, "SITEID_PER_PAGE", $scope.page_siteId.perPage);
		addDataObj(jQuery, paramDataObj, "SITEID_CUR_PAGE", $scope.page_siteId.currentPage);
		
		addDataObj(jQuery, dataObj, "PARAM_MAP", paramDataObj);
		
		var afterSuccessFunc = function(returnData) {
			exceptionHandler(returnData.RESULT, "Site ID", "N");
			
			hshelper_siteId.init();
			hshelper_siteId.setData(returnData.siteList);
			$scope.page_siteId.totalItems = returnData.VARIABLE_MAP.siteCnt;
	
		};
		
		commonHttpPostSender($http, ctrUrl, dataObj, afterSuccessFunc);
	};
	
	$scope.getSolVersionList = function() {
		$scope.page_solVersion.currentPage = 1; 
		
		var dataObj = {};
		var paramDataObj = {};
		addDataObj(jQuery, paramDataObj, "SVC_ID", "getSolVersionList");
		
		addDataObj(jQuery, paramDataObj, "solVersion", $scope.input_solVersion);
		addDataObj(jQuery, paramDataObj, "solName", $scope.input_solName);
		
		addDataObj(jQuery, paramDataObj, "SOLVERSION_PER_PAGE", $scope.page_solVersion.perPage);
		addDataObj(jQuery, paramDataObj, "SOLVERSION_CUR_PAGE", $scope.page_solVersion.currentPage);
		
		addDataObj(jQuery, dataObj, "PARAM_MAP", paramDataObj);
		
		var afterSuccessFunc = function(returnData) {
			exceptionHandler(returnData.RESULT, "Solution Version", "N");
			
			hshelper_solVersion.init();
			hshelper_solVersion.setData(returnData.solVersionList);
			$scope.page_solVersion.totalItems = returnData.VARIABLE_MAP.solVersionCnt;
		
		};
		
		commonHttpPostSender($http, ctrUrl, dataObj, afterSuccessFunc);
	};
	
	$scope.setSiteIdGrid = function (){
		var do_codeObj = {};

		var hsc_ins = document.getElementById('hst_siteId');
		
		var metaData = {};
		metaData.readonlyBool 		= false;
		metaData.colHeaders 		= ["","No.", "Site ID*", "Site Name*"];
		metaData.colWidths 			= [42, 40, 100, 210];
		metaData.columns 			= [
							           {data: "CHK", type: "checkbox", readOnly:false},
							           {data: "RNK", type: "textCenter", readOnly: true},
		                 			   {data: "siteId", type: "textCenter", validator: /[a-zA-Z0-9]/g}, 
		                 			   {data: "siteName", type: "text"}
		                 			   ];
		metaData.pkColumns			= ["siteId"];
		metaData.heightVal			= 513;
		metaData.rowHeaders 		= false;
		
		
		hshelper_siteId = new HandsontableHelper(hsc_ins, metaData);
		hshelper_siteId.init();
		hshelper_siteId.setData();
	};
	
	$scope.setSolVersionGrid = function (){
		var do_codeObj = {};
		
		var hsc_ins = document.getElementById('hst_solVersion');
		
		var metaData = {};
		metaData.readonlyBool 		= false;
		metaData.colHeaders 		= ["", "No.", "Solution Version*", "Solution Name*"];
		metaData.colWidths 			= [42, 40, 100, 210];
		metaData.columns 			= [
			 						   {data: "CHK", type: "checkbox", readOnly:false},
			   						   {data: "RNK", type: "textCenter", readOnly: true},
		                 			   {data: "solVersion", type: "textCenter", validator: /[a-zA-Z0-9]/g},
		                 			   {data: "solName", type: "text"}];
		metaData.pkColumns			= ["solVersion"];
		metaData.heightVal			= 513;
		metaData.rowHeaders 		= false;

		hshelper_solVersion = new HandsontableHelper(hsc_ins, metaData);
		hshelper_solVersion.init();
		hshelper_solVersion.setData();
	}
	
	$scope.addSite = function() {
		if (hshelper_siteId == undefined) {
			bootbox.alert("추가할 테이블이 없습니다.");
			return false;
		}
		
		var addRow = hshelper_siteId.addData({}, true);
		hshelper_siteId.selectCell(addRow, 1);
	}
	
	$scope.addSolVersion = function() {
		if (hshelper_solVersion == undefined) {
			bootbox.alert("추가할 테이블이 없습니다.");
			return false;
		}
		
		var addRow = hshelper_solVersion.addData({}, true);
		hshelper_solVersion.selectCell(addRow, 1);
	}
	
	$scope.delSite = function() {
		var chkCnt = cfn_chkCount(hshelper_siteId.getHsGridData());
		
		if(chkCnt == 0) {
			bootbox.alert("삭제할 항목을 체크해 주십시오.");
			return;
		}
		
		if(hshelper_siteId.delChkedRow()) {
			bootbox.alert("저장버튼을 누르면 삭제됩니다.");
		}
	};
	
	$scope.delSolVersion = function() {
		var chkCnt = cfn_chkCount(hshelper_solVersion.getHsGridData());
		
		if(chkCnt == 0) {
			bootbox.alert("삭제할 항목을 체크해 주십시오.");
			return;
		}
		
		if(hshelper_solVersion.delChkedRow()) {
			bootbox.alert("저장버튼을 누르면 삭제됩니다.");
		}
	};
	
	$scope.saveSite = function() {
		var dataObj = {};
		var paramDataObj = {};
		addDataObj(jQuery, paramDataObj, "SVC_ID", "saveSite");
		addDataObj(jQuery, dataObj, "PARAM_MAP", paramDataObj);
		addDataObj(jQuery, dataObj, "site_chg", hshelper_siteId.getHsChgData());
		
		// Validation 수정필요 190619
//		if(lengthCheck(dataObj.site_chg, {siteId: 4, siteName: 25}, ["Site ID", "Site Name"])) return;
//		if(mandantoryColumnCheck(dataObj.site_chg, ["siteId","siteName"], ["Site ID", "Site Name"])) return;
//		if(alphabetNumCheck(dataObj.site_chg, ["siteId"], ["Site ID"])) return;
		
		var afterSuccessFunc = function(returnData) {
			exceptionHandler(returnData.RESULT, "Site Save", "N");
			//저장후 재조회
			if(returnData.RESULT.ERRORCODE == "0") $scope.getSiteList();
		};
		
		commonHttpPostSender($http, ctrUrl, dataObj, afterSuccessFunc);
	}
	
	$scope.saveSolVersion = function() {
		var dataObj = {};
		var paramDataObj = {};
		addDataObj(jQuery, paramDataObj, "SVC_ID", "saveSolVersion");
		addDataObj(jQuery, dataObj, "PARAM_MAP", paramDataObj);
		addDataObj(jQuery, dataObj, "solVersion_chg", hshelper_solVersion.getHsChgData());
		
		// Validation 수정필요 190619
//		if(lengthCheck(dataObj.solVersion_chg, {solVersion: 4, solName: 25}, 
//												["Solution Version", "Solution Name"])) return;
//		if(mandantoryColumnCheck(dataObj.solVersion_chg, ["solVersion", "solName"], ["Solution Version", "Solution Name"])) return;
//		if(numCheck(dataObj.solVersion_chg, ["solVersion"], ["Solution Version"])) return;
		
		var afterSuccessFunc = function(returnData) {
			exceptionHandler(returnData.RESULT, "Solution Version Save", "N");
			//저장후 재조회
			if(returnData.RESULT.ERRORCODE == "0") $scope.getSolVersionList();
		};
		
		commonHttpPostSender($http, ctrUrl, dataObj, afterSuccessFunc);
	};
	
	$scope.initGrid = function() {
		$scope.setSiteIdGrid();
		$scope.setSolVersionGrid();
	};
	
	$scope.getInitList = function() {
		$scope.getSiteList();
		$scope.getSolVersionList();
	};
	
	$document.ready(function() {
		
		var buttonObj = $(":button");//화면이 로딩되기전 셀렉트 금지 필터링 필요
		buttonObj.hide();//화면이 로딩되기전 셀렉트 금지 필터링 필요
		
		$scope.pageInitiation();
		$scope.initGrid();
		$scope.getInitList();
		
		buttonObj.show();
	});	
	
});