------------------------------------------------------------------------------------

<h1 align="center">TCPlugin</h1>
<p align="center">
    <img src="https://raster.shields.io/badge/Made%20With-Kotlin-brightgreen?style=for-the-badge" alt="Tetriversal logo"/>
    <img src="https://raster.shields.io/tokei/lines/github/Questionable-Research-Labs/TCPlugin?style=for-the-badge" alt="Tetriversal logo"/>
    <img src="https://raster.shields.io/github/license/Questionable-Research-Labs/TCPlugin?style=for-the-badge" alt="Tetriversal logo"/>
</p>

------------------------------------------------------------------------------------

# 🤔 What is it?
TetriVersal is a multiplayer tetris game with a twist, everyone shares the same board. Instead of players each having
their own boards, all players share a board, and take turns controlling the game.

# 🤷 Why?
Why would we make such a game? TetriVersal is intended for use in a school project that required us to setup a multiplayer
game server as well as a file share, the catch is that this has to work entirely off line. To avoid any legal issues, with
using a prietied version game, and not being able to use services like steam, we have decided to make our own game

# 📘 How it works
This game is made up of two parts, the nodejs server backend and this unity game front end.
The server repo can be found here:

[https://github.com/jacobtread/tetriversal](https://github.com/jacobtread/tetriversal).

The server and client use the WebSocket protocol for communication, allow for a full duplex connection between the client and
server.

# ‼ IMPORTANT
When using this project, make sure to run a NuGet restore inside the unity editor to download the NuGet packages. There sould be
a NuGet option in the menu bar, under there should be the restore option.
