# Chess

This is a Chess application entirely written in Java. The goal for this project was to develop a lightweight chess application, with minimal system requirements.

## Table of Contents

[1. Introduction](#introduction)  
[2. Getting Started](#gettingStarted)  
[3. About](#about)

<a name="introduction"/></a>
## Introduction

![Screenshot of the in game view](https://github.com/JannisGz/Chess/blob/master/doc/inGameScreenshot.png?raw=true)


This Java application simulates a chess board for two players on the same computer. Just like in a real world chess game, players move their pieces alternately, following the rules and conditions of their chess pieces, until either a checkmate (one player wins) or a remis (tie) has been reached. The application itself checks if the player inputs correspond to valid moves and if checks, checkmates or a remis occured. Additionally a panel to the side holds details about the ongoing match. It displays the currently active player and a list of moves that have been carried out so far. At any time the board can be reset to its initial state, by clicking on the 'restart'- Button.

Chess pieces are moved, by first clicking on a chess piece, which belongs the currently active player. The tile this chess piece is located on, is then marked with a small blue highlight. Next, a valid target tile or enemy chess piece has to be chosen. Each type of chess piece has a different move set. For every move the application checks if it complies to the rules of chess. If it detects an invalid move the board is reset to the choosing phase. However, if the move is considered valid, it is carried out.

<a name="gettingStarted"/></a>
## Getting started

An executable jar file is placed in the root directory of this project. The Chess Application can be started with a simple double-click. Other than a Java distribution, no additional software is needed. The full source code is also available by cloning this repository.

<a name="about"/></a>
## About

![Simplified UML class diagram of the application](https://github.com/JannisGz/Chess/blob/master/doc/ClassDiagram.png?raw=true)

The design for this application consists of several different classes. The UML class diagram above displays the involved parts and relationships in a slightly simplified way. The most important classes are discussed in more detail below. Furthermore all classes and methods have a more detailed description in the source code.

#### Gui

The Gui is the interface for human players. It is created when the application is initialized. It consists of two major parts: A visual representation of a chess board and a side panel, which displays additional information for the current match. The chessboard displays the current state of an observed Game and the side panel shows, which player is currently active and what moves have been carried out so far. Additionally a "restart"- button, allows to reset the Game to its starting state at any point in time.

#### Game

The Game class represents a chess match. A match is created on start-up and whenever the reset button of the Gui is clicked. It consists of two players, their ChessPieces and a Board on which the chess pieces are located. The Game class checks if the move input of the players is valid and if certain game states, like check, checkmate or remis are reached.

#### ChessColor and Player

The ChessColor class represents both the graphical properties of ChessPieces and Tiles (i.e. whether they should be displayed in white or black color) and to which Player a ChessPiece belongs to. There are always exactly two players in every match: One black player owning all black pieces and a white player, who owns all white pieces.

#### Board and Tile

The Board class represents a chess board and acts as container for Tiles. Tiles represent the individual fields on the chess board. ChessPieces can be placed on Tiles and are then displayed as part of the Gui. Tiles also act as buttons and are therefore the main input source for the Game itself. Every time a Tile is clicked, the data is forwarded to the processInput(Tile ClickedTile) method of the Game class who owns the Player to which the ChessPiece belongs to. The Game class checks if the clicked Tile is part of a valid move and acts accordingly. The outcome is observed and displayed by the Gui.

#### ChessPieces

The ChessPiece class is an abstraction of a ChessPiece. It holds both graphical information (e.g. which icon should be displayed for this ChessPiece) and game-loop relevant information (e.g. on which Tile is this ChessPiece currently located and what are valid moves for it). The subclasses of ChessPiece are the individual types of different chess pieces: King, Queen, Rook, Knight, Bishop and Pawn. They implement the move set and conditions that are valid for their specific type. All ChessPieces have a clone() method, which can be used to copy ChessPieces or whole game states including Tiles and Boards, to evaluate the outcome of a move without altering the currently displayed Game. This is used to test if conditions for events like checkmate or remis have been reached.





<a name="todo"/></a>
### To do:

This section deals with known issues or functionalities that will be potentially addressed at same point in the future.

- Optional: Include graphical representation of possible moves, checks and checkmates
- Optional: Graphical display of captured pieces
- Remis detection: In some situations both players can not checkmate the enemy because of the pieces they have left, this should result in a remis, which is currently not detected (e.g. both players left only with their Kings)
- Pawns are automatically transformed into Queens when they reach the other side of the board. While this is the best move in almost all cases, the player should be provided with a choice.
