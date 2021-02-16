# Chess

This is a Chess application entirely written in Java. The goal for this project was to develop a lightweight chess application, with minimal requirements.

## Table of Contents

[1. Introduction](#introduction)  
[2. Getting Started](#gettingStarted)  
[3. About](#about)

<a name="introduction"/></a>
## Introduction

- example image

This Java application simulates a chess board for two players on the same computer. Just like in a real chess game, players move their pieces alternately. Additionally a panel to the side holds details about the match. It displays the currently active player and a list of moves that have been carried out so far. At any time the board can be reset to its initial state, by clicking on the 'restart'- Button.

Chess pieces are moved by first clicking on a chess piece, that belongs the currently active player. The chosen piece is marked with a small blue highlight around the tile it is located on. Next, a valid target tile or enemy chess piece has to be chosen. Each type of chess piece has a different move set. For every move the application checks if it complies to the chess rules. If it detects an invalid move the board is reset to the chosing phase. However, if the move is valid, it is carried out.

Additionally, after every turn the board is checked if it meets check, checkmate or remis conditions. If a remis or checkmate is detected, the game ends. 

<a name="gettingStarted"/></a>
## Getting started

An executable jar file is placed in the root directory of this project. The Chess Application can be started with a simple doubleclick. Other than a Java Version, no additional software is needed. The full source code is also available by cloning this repository.

<a name="about"/></a>
## About

- class diagram
The code for this application consists of several classes and modules. They are displayed in a Classdiagramm. The most important classes are discussed in more detail below.

#### Gui

#### Game

#### Board and Tile

#### ChessPiece(s)



<a name="todo"/></a>
### To do:

This section deals with known issues or functionalities that will be potentially addressed at same point in the future.

- Update ReadMe with Introduction, About, Getting started, Images, ClassDiagram, ..., Known Issues
- Optional: Include graphical display of moves, checks, checkmate
- Optional: Graphical display of captured pieces
- Remis detection: In some situations both players can not checkmate the enemy because of the pieces they have left, this should result in a remis, which is currently not detected (e.g. both players left only with their Kings)
- Pawns are automatically transformed into Queens when they reach the other side of the board. While this is the best move in allmost all cases, the player should be provided with a choice.
