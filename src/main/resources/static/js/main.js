'use strict';

var usernamePage = document.querySelector('#username-page'); //selects the element with the id username-page
var chatPage = document.querySelector('#chat-page'); //selects the element with the id chat-page
var usernameForm = document.querySelector('#usernameForm'); //selects the element with the id usernameForm
var messageForm = document.querySelector('#messageForm'); //selects the element with the id messageForm
var messageInput = document.querySelector('#message'); //selects the element with the id message
var messageArea = document.querySelector('#messageArea'); //selects the element with the id messageArea
var connectingElement = document.querySelector('.connecting'); //selects the element with the class connecting

var stompClient = null; //initialize the stompClient variable
var username = null; //initialize the username variable

var colors = [
'#2196F3', '#32c787', '#00BCD4', '#ff5652',
'#ffc107', '#ff85af', '#FF9800', '#39bbb0'
];//array of colors

/*
*add an event listener to the usernameForm element that
listens for the submit event and calls the connect
function when the event is triggered
*/
function connect(event){
    username = document.querySelector('#name').value.trim(); //get the value of the name input field
    if(username){
        usernamePage.classList.add('hidden'); //add the hidden class to the usernamePage element
        chatPage.classList.remove('hidden'); //remove the hidden class from the chatPage element

        var socket = new SockJS('/ws'); //create a new SockJS object
        stompClient = Stomp.over(socket); //create a new Stomp object
        stompClient.connect({}, onConnected, onError); //connect to the server
    }
    event.preventDefault(); //prevent the default action of the event
}

function onConnected(){
    stompClient.subscribe('/topic/public', onMessageReceived); //subscribe to the /topic/public topic
    stompClient.send("/app/chat.addUser", {}, JSON.stringify({sender: username, type: 'JOIN'})); //send a message to the /app/chat.addUser endpoint
    connectingElement.classList.add('hidden'); //add the hidden class to the connectingElement
}

function onError(error){
    connectingElement.textContent = 'Could not connect to WebSocket server. Please refresh this page to try again!'; //set the text content of the connectingElement
    connectingElement.style.color = 'red'; //set the color of the connectingElement
}


function sendMessage(event){
    var messageContent = messageInput.value.trim(); //get the value of the message input field

    if(messageContent && stompClient){
        var chatMessage = {
            sender: username,
            content: messageContent,
            type: 'CHAT'
        };
        stompClient.send("/app/chat.sendMessage", {}, JSON.stringify(chatMessage)); //send a message to the /app/chat.sendMessage endpoint
        messageInput.value = ''; //set the value of the message input field to an empty string
    }
    event.preventDefault(); //prevent the default action of the event
}

function onMessageReceived(payload){
    var message = JSON.parse(payload.body); //parse the message from the payload
    var messageElement = document.createElement('li'); //create a new li element
    if(message.type === 'JOIN'){
        messageElement.classList.add('event-message'); //add the event-message class to the messageElement
        message.content = message.sender + ' joined!';//set the content of the messageElement
        }
        else if (message.type === 'LEAVE'){
          messageElement.classList.add('event-message'); //add the event-message class to the messageElement
          message.content = message.sender + ' left!'; //set the content of the messageElement
        }
        else{
            messageElement.classList.add('chat-message'); //add the chat-message class to the messageElement
            var avatarElement = document.createElement('i'); //create a new i element
            var avatarText = document.createTextNode(message.sender[0]); //create a new text node
            avatarElement.appendChild(avatarText); //append the text node to the avatarElement
            avatarElement.style['background-color'] = getAvatarColor(message.sender); //set the background-color of the avatarElement
            messageElement.appendChild(avatarElement); //append the avatarElement to the messageElement

            var usernameElement = document.createElement('span'); //create a new span element
            var usernameText = document.createTextNode(message.sender); //create a new text node
            usernameElement.appendChild(usernameText); //append the text node to the usernameElement
            messageElement.appendChild(usernameElement); //append the usernameElement to the messageElement
        }
        var textElement = document.createElement('p'); //create a new p element
        var messageText = document.createTextNode(message.content); //create a new text node
        textElement.appendChild(messageText); //append the text node to the textElement

        messageElement.appendChild(textElement); //append the textElement to the messageElement

        messageArea.appendChild(messageElement); //append the messageElement to the messageArea
        messageArea.scrollTop = messageArea.scrollHeight; //set the scrollTop property of the messageArea to the scrollHeight property of the messageArea
}

function getAvatarColor(messageSender){
    var hash = 0; //initialize the hash variable
    for(var i = 0; i < messageSender.length; i++){
        hash = 31 * hash + messageSender.charCodeAt(i); //calculate the hash value
    }
    var index = Math.abs(hash % colors.length); //calculate the index value
    return colors[index]; //return the color at the index value
}


usernameForm.addEventListener('submit', connect, true);
messageForm.addEventListener('submit', sendMessage, true);
