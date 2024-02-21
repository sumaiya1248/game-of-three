var stompClient = null;

function setConnected(connected) {
    $("#connect").prop("disabled", connected);
    $("#disconnect").prop("disabled", !connected);
    $("#gameOverMessage").hide();
    if (connected) {
        $("#gameMessage").hide();
    }
    else {
        $("#playerName").prop("disabled", false);
    	$("#start").hide();
    	$("#start-ai").hide();
        $("#gameMessage").hide();
        $("#gameStatus").hide();
    }
}

function connect() {
    var socket = new SockJS('/game-of-three-websocket');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        setConnected(true);
        console.log('Connected: ' + frame);
        stompClient.subscribe('/topic/gameOfThree', function (gameStatus) {
        	var gameStatus = JSON.parse(gameStatus.body);
            showGameStatus(gameStatus);
            
            $("#currentNumber").val(gameStatus.currentNumber);
            $("#lastPlayer").val(gameStatus.lastPlayer);
            
            $("#playMinusOne").prop("disabled", isCurrentPlayer(gameStatus.lastPlayer));
            $("#playZero").prop("disabled", isCurrentPlayer(gameStatus.lastPlayer));
            $("#playOne").prop("disabled", isCurrentPlayer(gameStatus.lastPlayer));
            
        });
    });
}

function endGame(lastPlayer) {
    $("#gameMessage").hide();
    if($("#playerName").val() == lastPlayer) {
    	$("#gameOverMessage").replaceWith(
    			"<div id='gameOverMessage' class='alert alert-success'>"
    			+"Game over, the winner is : " + lastPlayer
    			+"</div>"
    	);
    } else {
    	$("#gameOverMessage").replaceWith(
    			"<div id='gameOverMessage' class='alert alert-danger'>"
    			+"Game over, the winner is : " + lastPlayer
    			+"</div>"
    	);
	}
}

function disconnect() {
    if (stompClient !== null) {	
        stompClient.disconnect();
    }
    setConnected(false);
    console.log("Disconnected");
}

function startGame(gameMode) {
    stompClient.send("/app/participate", {}, JSON.stringify({'playerName':$("#playerName").val(), 'gameMode':gameMode}));
    $("#playerName").prop("disabled", true);
    $("#start").hide();
    $("#start-ai").hide();
    $("#gameStatus").show();
    $("#gameMode").val(gameMode);
}

function play(inputNumber) {
	if((parseInt($("#currentNumber").val()) + inputNumber) % 3 != 0) {
		$("#gameStatus").append(
    			"<div class='alert alert-danger'>"
    			+"Sorry, invalid input number. It has to be dividable by 3 !"
    			+"</div>"
    	);
	} else {
		stompClient.send("/app/play", {}, JSON.stringify({'currentNumber': $("#currentNumber").val(), 'player': $("#playerName").val(), 'inputNumber': inputNumber, 'gameMode': $("#gameMode").val()}));
	}
}

function showGameStatus(gameStatus) {
    if(gameStatus.state == 'WAITING_FOR_PLAYER') {
    	if(isCurrentPlayer(gameStatus.lastPlayer) && gameStatus.gameMode != 'AI') {
    		$("#gameStatus").append(
    				"<div class='alert alert-info'>"
    				+"<p>Waiting for other player</p>"
    				+"</div>"
    		);
    	}
    } else {
    	if(gameStatus.lastNumberAdded != null) {
    		$("#gameStatus").append(
    				"<div class='alert " + classToUse(gameStatus.lastPlayer) + "'>" 
    				+ "<b>" + gameStatus.lastPlayer + "</b>" + " Added <b>" + gameStatus.lastNumberAdded + "</b>"
    				+"<br>"
    				+"<b>"
    				+ gameStatus.currentNumber
    				+"</b>"
    				+"</div>"
    		);
    	} else {
    		$("#gameStatus").append(
        			"<div class='alert " + classToUse(gameStatus.lastPlayer) + "'>" 
        			+"<b>"
        			+ gameStatus.currentNumber
        			+"</b>"
        			+"</div>"
        	);
    	}
    	
    	if(gameStatus.state == 'IN_PROGRESS') {
    		$("#gameMessage").show();
    	} else if(gameStatus.state == 'OVER') {
	    	$("#gameMessage").hide();
	        if(isCurrentPlayer(gameStatus.lastPlayer)) {
	        	$("#gameStatus").append(
	        			"<div class='alert alert-success'>"
	        			+" You won !"
	        			+"<br>Refresh the browser to start a new game"
	        			+"</div>"
	        	);
	        } else {
	        	$("#gameStatus").append(
	        			"<div class='alert alert-danger'>"
	        			+"You lost !"
	        			+"<br>Refresh the browser to start a new game"
	        			+"</div>"
	        	);
	    	}
	    }
	}
}

function classToUse(lastPlayer) {
	if(isCurrentPlayer(lastPlayer))
		return "alert-info";
	else {
		return "alert-warning";
	} 
}

function isCurrentPlayer(lastPlayer) {
	return $("#playerName").val() == lastPlayer;
}

function onChangePlayerName () {
	$("#start").prop("disabled", $( "#playerName").val() == '');
	$("#start-ai").prop("disabled", $( "#playerName").val() == '');
}

$(function () {
	$.get("/isPlayerWaiting", function( data ) {
		  if(data == true) {
			  $("#start-ai").hide();
			  $("#gameStatus").append(
	    				"<div class='alert alert-info'>"
	    				+"<p>A player is waiting for an opponent</p>"
	    				+"</div>"
	    		);
		  }
		});
	
    $("form").on('submit', function (e) {
        e.preventDefault();
    });
    $( "#connect" ).click(function() { connect(); });
    $( "#disconnect" ).click(function() { disconnect(); });
    $( "#start" ).click(function() { startGame('HUMAN'); });
    $( "#start-ai" ).click(function() { startGame('AI'); });

    $( "#playMinusOne" ).click(function() { play(-1); });
    $( "#playZero" ).click(function() { play(0); });
    $( "#playOne" ).click(function() { play(1); });
    $( "#playerName").change(function() { onChangePlayerName(); });
    
    $( document ).ready(function() { connect();onChangePlayerName(); });

    $("#connect").prop("disabled", true);
    
    $("#gameMessage").hide();
    
});