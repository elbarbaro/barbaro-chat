var listMessages = document.getElementById("listMessages");
var listUsers = document.getElementById("listUsers");
var stompClient = null;

document.getElementById("btnConnect").onclick = connect;
document.getElementById("btnDisconnect").onclick = disconnect;
document.getElementById("btnSendMessage").onclick = sendMessage;
document.getElementById("formMessage").onsubmit = (e) => e.preventDefault();

function connect() {

	var socket = new SockJS("/barbarochat");
	//var socket = new SockJS("https://barbaro-chat.herokuapp.com/publish");
	stompClient = Stomp.over(socket);
	// Recibe tres argumentos data de frame, success function, error function
	// objeto es para mandar datos como headers en el frame STOMP

	let inputUsername = document.getElementById("txtUsername");


	stompClient.connect({ chatroomuser: inputUsername.value }, function(frame) {
		console.log(frame);
		console.log("Connected");
		stompClient.subscribe("/app/connected.users", processUsers);
		stompClient.subscribe("/topic/chatroom", processMessage);
		stompClient.subscribe("/topic/connected.users", processUsers);
	});
}

function disconnect() {
	if(stompClient != null) {
		sendDisconnectMessage();
		stompClient.disconnect();
		console.log("Disconnected");
	}
}

function processMessage(stompMessage) {
	console.log(stompMessage);
	let messageJSON = stompMessage.body;
	console.log(messageJSON);
	let message = JSON.parse(messageJSON);
	console.log(message);
	createMessage(message);
}

function createMessage(message) {
	
	let	itemMessage = document.createElement("div");
	let itemHeader = document.createElement("div");
	let itemUsername = document.createElement("h4");
	let itemDate = document.createElement("span");
	let itemContent = document.createElement("p");

	itemHeader.classList.add('d-flex', 'justify-content-between');

	itemUsername.innerText = message.username;
	itemDate.innerText = new Date().toLocaleString();
	itemContent.innerText = message.content;

	itemHeader.appendChild(itemUsername);
	itemHeader.appendChild(itemDate);

	itemMessage.appendChild(itemHeader);
	itemMessage.appendChild(itemContent);

	listMessages.appendChild(itemMessage);
}

function buildMessage() {

	let inputUsername = document.getElementById("txtUsername");
	let inputMessage = document.getElementById("txtMessage");

	return JSON.stringify({ username: inputUsername.value, content: inputMessage.value });
}

function sendMessage(){
	let message = buildMessage();
	stompClient.send("/app/publish", {}, message);
}

function sendDisconnectMessage() {
	let inputUsername = document.getElementById("txtUsername");
	stompClient.send("/app/disconnect", {}, { username: inputUsername.value });
}

function processUsers(stompMessage) {
	console.log("Por qué aqui no entraaaaaa!");
	console.log(stompMessage);
	let messageJSON = stompMessage.body;
	console.log(messageJSON);
	let users = JSON.parse(messageJSON);
	createListUsers(users);
}

function createListUsers(users) {

	for (const user of users) {
		createItemUser(user);
	}
}

function createItemUser(user) {

	let elemUsername = document.createElement("h4");

	elemUsername.innerText = user.username;

	listUsers.appendChild(elemUsername);
}







