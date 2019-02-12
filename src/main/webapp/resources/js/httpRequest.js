var xhr = null;

function createRequest() {
	if ( xhr != null ) return;
	
	if ( window.ActiveXObject == true ) {
		xhr = new ActiveXObject("Microsoft.XMLHTTP");
	} else {
		xhr = new XMLHttpRequest();
	}
	
}

function sendRequest( url, param, callBack, method ) {
	createRequest();
	
	var httpMethod = "GET";
	if ( method == 'POST' || method == 'post' ) {
		httpMethod = "POST";
	}
	
	var httpParam = null;
	if ( param != null && param != '' ) {
		httpParam = param;
	}
	
	var httpURL = url;
	
	if ( httpMethod == 'GET' && httpParam != null ) {
		httpURL = httpURL + "?" + httpParam;
	}
	
	xhr.open( httpMethod, httpURL, true );
	
	xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	
	xhr.onreadystatechange = callBack;
	
	xhr.send( httpMethod == "POST" ? httpParam : null );
}


















