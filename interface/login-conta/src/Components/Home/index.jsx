import React, { useState, useEffect } from 'react';
import './index.css';
import backgroundImg from '../Assets/quadra-basquete.jpg';

function App() {
  const [availablePlayers, setAvailablePlayers] = useState([]);
  const [teamPlayers, setTeamPlayers] = useState([]);
  const [money, setMoney] = useState(1000);

  const iniciar = async () => {
    const manager = await getManager();
    setMoney(manager['dinheiro']);

    await getPlayers();
  }

  const getManager = async () => {
    try{
      const response = await fetch("http://localhost:8080/manager", {
        method: "GET",
        headers: {
          Accept: "application/json",
          Authorization: window.localStorage.getItem("Authorization")
        },
      });
      return response.json();
    }catch (error){

    }
  }

  const getTime = async () => {
    try{
      const response = await fetch("http://localhost:8080/time/manager", {
        method: "GET",
        headers: {
          Accept: "application/json",
          Authorization: window.localStorage.getItem("Authorization")
        },
      });

      return response.json();
    }
    catch (error){

    }
  }

  const getPlayers = async () => {
    try {
      const response = await fetch("http://localhost:8080/jogadores", {
        method: "GET",
        headers: {
          Accept: "application/json",
        },
      });

      if (response.status === 200) {
        const players = await response.json();
        setAvailablePlayers(players);
      } else {
        console.log("Falha ao obter jogadores.");
      }
    } catch (error) {
      console.error("Erro ao obter jogadores:", error);
    }
  };

  const buyPlayer = (player) => {
    if (money >= player['valor'] && teamPlayers.length < 5) {
      try {
        const response =  fetch("http://localhost:8080/contrato", {
          method: "POST",
          headers: {
            Accept: "application/json",
          },
          body: "{\n\"jogadorId\":" + player['id'] + "\n\"timeId\":" + getTime()["id"] + "\n}"
        });


        const updatedAvailablePlayers = availablePlayers.filter((p) => p.id !== player.id);
        const updatedTeamPlayers = [...teamPlayers, player];
        const updatedMoney = money - player['valor'];

        setAvailablePlayers(updatedAvailablePlayers);
        setTeamPlayers(updatedTeamPlayers);
        setMoney(updatedMoney);
      } catch (error){

      }
    }
  };

  const sellPlayer = (player) => {
    try {
      const response = fetch("http://localhost:8080/contrato", {
        method: "DELETE",
        headers: {
          Accept: "application/json",
        },
        body: "{\n\"jogadorId\":" + player['id'] + "\n\"timeId\":" + getTime()["id"] + "\n}"
      });
      const updatedTeamPlayers = teamPlayers.filter((p) => p.id !== player.id);
      const updatedAvailablePlayers = [...availablePlayers, player];
      const updatedMoney = money + player['valor'];
      setAvailablePlayers(updatedAvailablePlayers);
      setTeamPlayers(updatedTeamPlayers);
      setMoney(updatedMoney);
    }catch (error){

    }
  };

  return (
    <div style={{ backgroundImage: `url(${backgroundImg})` }} className="app-container">
      <div className="titulo">Gerencie Seu Time de Basquete!</div>
      <div>
        <h2>Dinheiro Disponível: ${money}</h2>
        <button onClick={iniciar}>Começar</button>
      </div>
      <div>
        <h2>Dinheiro Disponível: ${money}</h2>
      </div>
      <div className="columns">
        <div className="column">
          <div>
            <h3>Jogadores Disponíveis</h3>
            <ul>
              {availablePlayers.map((player) => (
                <li key={player.id}>
                  {player.name} - ${player.price}
                  <button onClick={() => buyPlayer(player)}>Comprar</button>
                </li>
              ))}
            </ul>
          </div>
        </div>
        <div className="column">
          <div>
            <h3>Seu Time</h3>
            <ul>
              {teamPlayers.map((player) => (
                <li key={player.id}>
                  {player.name} - ${player.price}
                  <button onClick={() => sellPlayer(player)}>Vender</button>
                </li>
              ))}
            </ul>
          </div>
        </div>
      </div>
    </div>
  );
}

export default App;
