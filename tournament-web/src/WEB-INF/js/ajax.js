/**
 * de.sb.util.AJAX: XmlHttpRequest invocation singleton.
 * Copyright (c) 2013-2015 Sascha Baumeister
 */
"use strict";
this.de = this.de || {};
this.de.htw = this.de.htw || {};
this.de.htw.tournament = this.de.htw.tournament || {};
(function () {

	/**
	 * AJAX singleton for simplified XmlHttpRequest processing.
	 */
	de.sb.util.AJAX = new function() {

		/**
		 * Sends an XmlHttpRequest with the given arguments. If a callback function is specified,
		 * the HTTP request is executed asynchronously, and the given callback method is invoked
		 * once the HTTP response is received. Otherwise, the request is executed synchronously.
		 * @param resource {String} the HTTP request URI
		 * @param method {String} the HTTP request method
		 * @param header {Object} a map associating HTTP request header keys with values,
		          or null for none
		 * @param body {Object} the HTTP request body, or null for none
		 * @param credentials an object that provides userAlias and userPassword fields
		          (for HTTP basic authentication), or null for unauthenticated access
		 * @param callback {Function} a function that takes an XmlHttpRequest as an argument,
		          and is executed once the corresponding response becomes available, or
		          null for synchronized request processing
		 * @return the XmlHttpRequest
		 */
		this.invoke = function (resource, method, header, body, credentials, callback) {
			var request = new XMLHttpRequest();
			request.overrideMimeType("text/plain");

			var asynchronous = typeof callback == "function";
			if (asynchronous) {
				request.addEventListener("readystatechange", function () {
					if (this.readyState === 4) callback.call(null, this);
				});
			}

			if (credentials) {
				request.open(method, resource, asynchronous, credentials.userAlias, credentials.userPassword);
			} else {
				request.open(method, resource, asynchronous);
			}

			for (var key in (header || {})) {
				request.setRequestHeader(key, header[key]);
			}
			request.send(body || "");

			return request;
		}
	}
} ());