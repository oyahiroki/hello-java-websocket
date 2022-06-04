<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Hello WebSocket</title>
<!-- jQuery -->
<script src="https://ajax.aspnetcdn.com/ajax/jQuery/jquery-3.3.1.min.js"></script>

<script>
	var connection;

	function print(msg) {
		$("#msg").append($("#msg").val() + "\n" + msg);
	}

	$(function() {

		console.log("hello");

		//WebSocket接続
		connection = new WebSocket(
				"ws://localhost:8080/hello-websocket/helloendpoint");

		//接続通知
		connection.onopen = function(event) {
			console.log("open");
			console.log(event);
			print("open");
		};

		//エラー発生
		connection.onerror = function(event) {
			console.log("onerror");
			console.log(event);
			print("onerror");
		};

		//メッセージ受信
		connection.onmessage = function(event) {
			console.log("onmessage");
			console.log(event);
			print("onmessage");
			print(event.data);
			//			document.getElementById("dispMsg").value = event.data;
		};

		//切断
		connection.onclose = function() {
			console.log("onclose");
			print("onclose");
		};
		
		$("#btn1").on("click",function(){
			connection.send("hi");
		});

	});
</script>

</head>

<body>
	<h1>Hello WebSocket</h1>
	<div>
		<button id="btn1">Send Message</button>
	</div>
	<div>
		<pre id="msg"></pre>
	</div>
</body>

</html>