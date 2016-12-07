# Ping Pong Game

## Overview
Created a UDP controlled Peer-to-peer ping pong game that allows 4 different people to play in real time against each other using differnt computers. The game code was written in **JAVA** and the UI was created using the **Java Swing Library**. The game has many salient features to make it appealing.
+ **Physics Engine**  
A complex Physics engine was created to provide a real life simulation feel for the players in which oblique collision model those in the real world.  
+ **Artificial Intelligence**  
Basic intelligence was coded into the computer controlled players in the case the number of human players is less than 4. Also the game handles the dissconnection of a player in which after a certain period of waiting time the timed-out player is replaced by a computer player. The level and game play of the computer player increases with the level of the game to introduce a ramped up notion of difficulty.  
+ **Multiple Balls**  
We have added the feature that the players can play with upto 5 balls in the play field at the same time by picking the corresponding game level. The balls interact with each other and can bounce off one another.  
+ **Power Ups**  
Power ups randomly appear in the game in the shape of a colored ball. A player must capture the colored ball to receive the effects of the power up which include things like: extra lives, longer paddle, etc. The normal balls can interact with the powerups as well and can bounce off them.
+ **UDP Sockets**  
Information between the players is sent through UDP which proves to be a quick and efficient tool in live gameplay as it aids in fast communictaion and updates. Multiple threads are set up at each player's end to receive data such as paddle and ball position and various relative speeds.  

## Gameplay  
1) To run the game you must run the jar file on each player's computer using the following command in terminal or command prompt `java -jar PingPong.jar`.  
2) Now you can enter things such as your **Name** and select the **Game Level** in the UI screen that pops up.  
3) One person needs to create a game by clicking on the *'Create New Game'* button.  
4) Then the user must choose the number of people playing and enter all their IP Addresses in the text boxes provided.  
5) Other players must click on *'Join Another Game'* button to join an existing game.  
6) These players must enter the IP Address of the person who created the game and then press *'Request to Join'*.  
7) Once everyone has joined, the creator must click on *'Start Game!'* to begin.  
8) All the paddles are controlled using the mouse and each player starts off with 5 lives. A player's paddle will freeze once they loose all 5 of their lives indicating **Game Over**.  
9) Game Continues until only one player is left.  

## Design Doc
All the relavent details to the individual classes and the physics engine used in the game code are explained in the attached [Design Doc](https://github.com/NikhilGupta1997/Ping-Pong-Peer-to-Peer-Game/blob/master/Designdoc/DesignDoc.pdf).

## Authors
* [Nikhil Gupta](https://github.com/NikhilGupta1997)
* [Ayush Bhardwaj](https://github.com/Ayushbh)
* [Aditi](https://github.com/aditi741997)

Course Project under [**Prof. Vinay Ribeiro**](http://www.cse.iitd.ac.in/~vinay/)
