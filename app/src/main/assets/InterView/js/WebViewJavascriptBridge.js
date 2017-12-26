<!DOCTYPE html>
<html>

	<head>
		<meta charset="utf-8" />
		<title>MarkDown文件加载</title>
		<style>
			body {
				background-color: blueviolet;
			}
			
			* {
				max-width: 100vw;
				text-overflow: ellipsis;
				overflow: hidden;
			}
			
			;
		</style>
	</head>
	<script src="js/jquery-3.2.1.min.js"></script>
	<script src="js/marked.js"></script>
	<script src="js/http.js"></script>
	<script src="js/WebViewJavascriptBridge.js"></script>

	<body>

	</body>
	<script>
		function loadData(url) {
			var data1 = {
				"url": url
			};
			var data2 = {
				"isProcessing": {
					"val": false
				},
				"downloadedFiles": {
					"val": 0
				},
				"totalFiles": {
					"val": 0
				}
			};
			var data3 = {};
			downloadZippedFiles(data1, data2, data3);
		}

		function GetUrlRelativePath(path)　　 {　　　　

			var arrUrl = path.split("//");

			　　　　
			var start = arrUrl[1].indexOf("/");　　　　
			var relUrl = arrUrl[1].substring(start); //stop省略，截取从start开始到结尾的所有字符

			　　　　
			if(relUrl.indexOf("?") != -1) {　　　　　　
				relUrl = relUrl.split("?")[0];　　　　
			}　　　　
			return relUrl;　　
		}

		function parseInfo(parameters) {

			//			var repoPath = new URL(parameters.url).pathname;
			var repoPath = GetUrlRelativePath(parameters.url);
			console.log("repoPath：" + repoPath);

			var splitPath = repoPath.split("/");
			console.log("splitPath：" + splitPath);

			var info = {};

			info.author = splitPath[1];
			info.repository = splitPath[2];
			info.branch = splitPath[4];
			info.rootName = splitPath[splitPath.length - 1];

			if(!!splitPath[4]) {
				info.resPath = repoPath.substring(
					repoPath.indexOf(splitPath[4]) + splitPath[4].length + 1);
			}

			info.urlPrefix = "https://api.github.com/repos/" + info.author +
				"/" + info.repository + "/contents/";
			info.urlPostfix = "?ref=" + info.branch;
			if(!parameters.fileName || parameters.fileName == "") {
				info.downloadFileName = info.rootName;
			} else {
				info.downloadFileName = parameters.fileName;
			}
			if(parameters.rootDirectory == "false") {
				info.rootDirectoryName = "";
			} else if(!parameters.rootDirectory || parameters.rootDirectory == "" ||
				parameters.rootDirectory == "true") {
				info.rootDirectoryName = info.rootName + "/";
			} else {
				info.rootDirectoryName = parameters.rootDirectory + "/";
			}
			console.log(JSON.stringify(info));
			return info;
		}

		function downloadZippedFiles(parameters, progress, toastr) {
			var repoInfo = parseInfo(parameters);
			if(!repoInfo.resPath || repoInfo.resPath == "") {
				if(!repoInfo.branch || repoInfo.branch == "") {
					repoInfo.branch = "master";
				}
				var downloadUrl = "https://github.com/" + repoInfo.author + "/" +
					repoInfo.repository + "/archive/" + repoInfo.branch + ".zip";
				window.location = downloadUrl;
			} else {
				//普通get请求
				http.get(repoInfo.urlPrefix + repoInfo.resPath + repoInfo.urlPostfix, function(err, result) {
						if(err) {
							console.log(JSON.stringify(err));
						} else {
							$.get(result.download_url, function(response, status, xhr) {
									$("body").html(marked(response));
									var data = $('body').text();
									resultData(data);
								);
							});
					}
				});

		}
		}
	</script>
	<script>
		function connectWebViewJavascriptBridge(callback) {
			if(window.WebViewJavascriptBridge) {
				callback(WebViewJavascriptBridge)
			} else {
				document.addEventListener(
					'WebViewJavascriptBridgeReady',
					function() {
						callback(WebViewJavascriptBridge)
					},
					false
				);
			}
		}
		connectWebViewJavascriptBridge(function(bridge) {
			bridge.init(function(message, responseCallback) {

			});

		});

		function resultData(data) {
			window.WebViewJavascriptBridge.callHandler('resultText', data, function(responseData) {

				}
			}
	</script>

</html>