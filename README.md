spaceblasters
=============

Grade 12 Computer Science CPT


=============
 To Do
=============
Connection Init:
DONE - Client will send: JOIN, Name, ip_address, 
DONE - Server will send: ip_address, ACCEPT, playernumber(1 = 0, 2 = 1, 3 = 2, 4 = 3)
DONE - Server will send: ip_address, DENY, -1

Msg To ALL to messagebox (ingame):
DONE - Server will send: BROADCAST, insertmessagehere
DONE - Client will send: CHAT, playernum, insermessagehere

Pregame:
DONE - Server will run a keepalive check
DONE - Server will send: WAITING,status,timeleft(in seconds)

Game Mechanics
DONE - Server will send: LOCATION, timeleft(in seconds), target1X, target1Y, etc etc all the way to target 10Y
DONE - Server will send: SCORES, roundnum, player1name, ..., player4name, player1score, player2score, ..., player4 score
Server will send: PLAYERLOC, player1X, player1Y, player2X, player2Y, player3X, player3Y, player4X, player4Y
Client will send: CLIENTLOCATION, playernumber, cursorx, cursory, ifclicked

Finish
----Server will send FINISHED, round, player1score,...,player4score
----Server will send GAMEEND, player1score,...,player4score
!!!DEPRECIATED. Ending with "WAITING command"!!!

Server Reset
DONE - Server will send: ALLRESET

Server Close:
DONE - Client will recieve a DISC message

Client Leave:
If client leaves, server will see a disconnect and wait until end of game before running a keepalive check
