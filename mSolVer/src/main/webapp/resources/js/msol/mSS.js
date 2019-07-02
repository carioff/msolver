
app.controller('ctr_mSS', function($scope, $http, $document, $window, $q) {
	var ctrUrl = 'msol/mSS';

	var hshelper_cd;
	
	var site_source; //For Comb
	var sol_ver_source; //For Comb
	
	$scope.pageInitiation = function() {
		$scope.page_cd = {};
		$scope.page_cd.currentPage = 1;
		$scope.page_cd.perPage = 20;
		$scope.page_cd.totalItems = 0;
	}
	
	$scope.getSiteSolVerList = function() {
		$scope.page_cd.currentPage = 1; 
		
		var dataObj = {};
		var paramDataObj = {};
		addDataObj(jQuery, paramDataObj, "SVC_ID", "getSiteSolVerList");
		
		let selectedCmbSite = "";
		let selectedCmbSolVer = "";
		
		//select box들 값의 유무 체크 
		if ($scope.selectedCmbSite != undefined && $scope.selectedCmbSite != null) {
			selectedCmbSite= $scope.selectedCmbSite;
		}
		//select box들 값의 유무 체크 
		if ($scope.selectedCmbSolVer != undefined && $scope.selectedCmbSolVer != null) {
			selectedCmbSolVer= $scope.selectedCmbSolVer;
		}
		
		addDataObj(jQuery, paramDataObj, "siteId", selectedCmbSite);
		addDataObj(jQuery, paramDataObj, "solVersion", $scope.selectedCmbSolVer);
		
		addDataObj(jQuery, paramDataObj, "applyDateFrom", $scope.input_applyDateFrom);
		addDataObj(jQuery, paramDataObj, "applyDateTo", $scope.input_applyDateTo);
		
		addDataObj(jQuery, paramDataObj, "applyWorker", $scope.input_applyWorker);
		addDataObj(jQuery, paramDataObj, "applyContents", $scope.input_applyContents);
		
		addDataObj(jQuery, paramDataObj, "SOLVERSION_PER_PAGE", $scope.page_cd.perPage);
		addDataObj(jQuery, paramDataObj, "SOLVERSION_CUR_PAGE", $scope.page_cd.currentPage);
		
		addDataObj(jQuery, dataObj, "PARAM_MAP", paramDataObj);

		var afterSuccessFunc = function(returnData) {
			exceptionHandler(returnData.RESULT, "코드", "N");
//			var gridData= cdToNmOfGridData(returnData.siteSolVerList);//Cd를 Name형식으로 변환
//			hshelper_cd.init();
//			hshelper_cd.setData(gridData);
			hshelper_cd.setData(returnData.siteSolVerList);
			$scope.page_cd.totalItems = returnData.VARIABLE_MAP.siteSolVerCnt;
		};
		
		commonHttpPostSender($http, ctrUrl, dataObj, afterSuccessFunc);
	}
	
	function cdToNmOfGridData(obj) {
		for (i=0; i< obj.length; i++){
			for (var key in obj[i]){
				if(key =='siteId'){
					for (j=0; j< $scope.siteId.length; j++){
						var tmpStr = $scope.siteId[j].siteId;
						obj[i][key] = tmpStr;
					}
				}

				if(key =='solVersion'){
					for (j=0; j< $scope.solVersion.length; j++){
						var tmpStr = $scope.solVersion[j].solVersion;
						obj[i][key] = tmpStr;
					}
				}
			}
		}
		return obj;
	};
				
	function setCdGrid(site_source, sol_ver_source){
		var do_codeObj = {};

		var hsc_ins = document.getElementById('hst_ss');
		
		var metaData = {};
		metaData.readonlyBool 		= false;
		metaData.colHeaders 		= ["", "No.", "ID*", "Site ID*", "Solution Version*", 
										"Apply Date*", "Apply Worker", "Apply Contents", 
										"RGST DATE", "RGST ID", "UPD DATE", "UPD ID"];
		metaData.colWidths 			= [42, 40, 40, 180, 100, 
		                   			   100, 120, 200,
		                   			   120, 110, 120, 110];
		metaData.columns 			= [
			 						   {data: "CHK", type: "checkbox", readOnly:false},
			   						   {data: "RNK", type: "textCenter", readOnly: true},
			   						   {data: "solSiteId", type: "textCenter", readOnly: true},
				   					   {data: "siteId", type: "autocompleteCenter",
		                 				    source: site_source,
		                 				    strinct: false,
		                 				    filter: false,
		                 				    readOnly: false},
	                 				   {data: "solVersion", type: "autocompleteCenter",
		                 				    source: sol_ver_source,
		                 				    strinct: false,
		                 				    filter: false,
		                 				    readOnly: false},
		                 				    
		                 			   {data: "applyDate", type: "textCenter", validator: /[0-9]/g},
		                 			   {data: "applyWorker", type: "text"}, 
		                 			   {data: "applyContents", type:"text"},

		                 			   {data: "rgstDate", type: "textCenter", readOnly: true},
		                 			   {data: "rgstId", type: "textCenter", readOnly: true},
		                 			   {data: "updDate", type: "textCenter", readOnly: true},
		                 			   {data: "updId", type: "textCenter", readOnly: true}
		                 			   ];
		metaData.pkColumns			= ["solSiteId"];
		metaData.heightVal			= 510;
		metaData.rowHeaders 		= false;

		hshelper_cd = new HandsontableHelper(hsc_ins, metaData);
		hshelper_cd.init();
		hshelper_cd.setData();
	}
	
	$scope.addSS = function() {
		if (hshelper_cd == undefined) {
			bootbox.alert("추가할 테이블이 없습니다");
			return false;
		}
		
		var addRow = hshelper_cd.addData({}, true);
		hshelper_cd.selectCell(addRow, 1);
	}
	
	$scope.delSS = function() {
		var chkCnt = cfn_chkCount(hshelper_cd.getHsGridData());
		
		if(chkCnt == 0) {
			bootbox.alert("삭제할 항목을 체크해 주십시오.");
			return;
		}
		
		if(hshelper_cd.delChkedRow()) {
			bootbox.alert("저장버튼을 누르면 삭제됩니다.");
		}
	};
	
	$scope.saveSS = function() {
		var dataObj = {};
		var paramDataObj = {};
		addDataObj(jQuery, paramDataObj, "SVC_ID", "saveSiteSolVer");
		addDataObj(jQuery, dataObj, "PARAM_MAP", paramDataObj);
		addDataObj(jQuery, dataObj, "ss_chg", hshelper_cd.getHsChgData());
		
		if(lengthCheck(dataObj.ss_chg, {applyDate: 8, applyWorker: 25, applyContents: 25}, 
											["Apply Date", "Apply Worker", "Apply Contents"])) return;
		if(mandantoryColumnCheck(dataObj.ss_chg, ["siteId", "solVersion","applyDate"], 
				["Site ID", "Solution Version", "Apply Date"])) return;
//		if(alphabetNumCheck(dataObj.ss_chg, ["AIR_CD"], ["항공사 코드"])) return;
		if(numCheck(dataObj.ss_chg, ["applyDate"], ["Apply Date"])) return;
		
		var afterSuccessFunc = function(returnData) {
			exceptionHandler(returnData.RESULT, "Solution Version On Site", "Y");
			
			//저장후 재조회 & Push Updated Contents
			if(returnData.RESULT.ERRORCODE == "0") {
				if(returnData.pushMsg != undefined){
					let pushMsgObj = returnData.pushMsg[0];
					let pushMsg = pushMsgObj.siteId + "|" + pushMsgObj.solVersion + "|" 
					+ pushMsgObj.applyDate + "|" + pushMsgObj.applyWorker + "|" 
					+ pushMsgObj.applyContents + "|" + pushMsgObj.exeCategory;
					message = new Paho.MQTT.Message(pushMsg);
					message.destinationName = "/test";
					message.retained = true;
					client.send(message);
				}
				
				$scope.getSiteSolVerList();
			}
		};
		
		commonHttpPostSender($http, ctrUrl, dataObj, afterSuccessFunc);
	}
	
	$( "#input_applyDateFrom" ).datepicker({
        defaultDate: "+1w",
        changeMonth: true,
        changeYear: true,
        numberOfMonths: 1,
        dateFormat    :  "yy.mm.dd",
        onClose: function( selectedDate ) {
//           var dt = dtFormatCompl(selectedDate);
         $scope.input_applyDateFrom = selectedDate;
         $('#input_applyDateFrom').val(selectedDate);
         $( "#input_applyDateTo" ).datepicker( "option", "minDate",  selectedDate);
        }
      });
     $( "#input_applyDateTo" ).datepicker({
        defaultDate: "+1w",
        changeMonth: true,
        changeYear: true,
        numberOfMonths: 1,
        dateFormat    : "yy.mm.dd",
        onClose: function( selectedDate ) {
//         var dt = dtFormatCompl(selectedDate);
         $scope.input_applyDateTo = selectedDate;
         $('#input_applyDateTo').val(selectedDate);
         $( "#input_applyDateFrom" ).datepicker( "option", "maxDate", selectedDate );
        }
     });
     
     $scope.setDefaultDate = function() {
 		
 	  	var date = new Date();
 	   	var current_month, current_date, current_year;

 	   	current_year = date.getFullYear();
 	   	current_month= date.getMonth()+1;
 	   	current_date = date.getDate(); 
 	   	
// 	    alert(current_month + " " + current_date);
 	    
 	    var past_month, past_date, past_year;
 	   
 	    date.setDate(date.getDate() - 1); 

 	    past_year = date.getFullYear();
 	    past_month = date.getMonth()+1;
 	    past_date = date.getDate(); 
 	    
// 	    alert(past_month + " " + past_date);
 	    
 	   	//숫자의 길이가 한자리인 경우 두자리로 통일하기 위한 작업
 	   	if (("" + current_date).length == 1) current_date = "0" + current_date;
 	   	if (("" + current_month).length == 1) current_month = "0" + current_month;
 	   	
 	   	if (("" + past_date).length == 1) past_date = "0" + past_date;
 	   	if (("" + past_month).length == 1) past_month = "0" + past_month;
 	   	
 	   	 $scope.input_applyDateTo = current_year + '.' + current_month + '.' + current_date;
 		 $scope.input_applyDateFrom = past_year + '.' + past_month + '.' + past_date;
 		 
// 	   	 $scope.issue_to = current_year + '.' + current_month + '.' + current_date;
// 		 $scope.issue_from = past_year + '.' + past_month + '.' + past_date;
 	};

	
	/**
	 * <ul>
	 * <li>2016.09.29</li>
	 * <li>ckim</li>
	 * <li>function name: setSelectedCmbs</li>
	 * <li>function description: 건색조건을 selectbox로 만들어준다</li>
	 * </ul>
	 */
	$scope.getSelectedCmbs = function() {
		var dataObj = {};
		var paramDataObj = {};
		addDataObj(jQuery, paramDataObj, "SVC_ID", "getSelectedCmbs");
		
		addDataObj(jQuery, dataObj, "PARAM_MAP", paramDataObj);

		var afterSuccessFunc = function(returnData) {
			exceptionHandler(returnData.RESULT, "getSelectedCmbs", "N");
			setSelectedCmbs(returnData);
		};
		
		commonHttpPostSender($http, ctrUrl, dataObj, afterSuccessFunc);
	}
	
	/**
	 * <ul>
	 * <li>2016.09.29</li>
	 * <li>ckim</li>
	 * <li>function name: setSelectedCmbs</li>
	 * <li>function description: 건색조건을 selectbox로 만들어준다</li>
	 * </ul>
	 * 
	 * @param returnData
	 * @return: none
	 */
	function setSelectedCmbs(returnData){

		var siteArr = [];
		var solVersionArr = [];
		
		$scope.siteId = returnData.siteForComb;
		$scope.solVersion = returnData.solVersionForComb;
		
		for (var i = 0; i < returnData.siteForComb.length; i++) {
			siteArr.push(returnData.siteForComb[i].siteId);
		}
		for (var i = 0; i < returnData.solVersionForComb.length; i++) {
			solVersionArr.push(returnData.solVersionForComb[i].solVersion);
		}
		
		setCdGrid(siteArr, solVersionArr);
	}	
	
	$document.ready(function() {

		var buttonObj = $(":button");//화면이 로딩되기전 셀렉트 금지 필터링 필요 170213 ckim
		buttonObj.hide();//화면이 로딩되기전 셀렉트 금지 필터링 필요 170213 ckim
		
		$scope.pageInitiation();
		$scope.getSelectedCmbs();
		$scope.getSiteSolVerList();
//		$scope.setDefaultDate();

		buttonObj.show();//화면이 로딩되기전 셀렉트 금지 필터링 필요 170213 ckim
	});
			
});