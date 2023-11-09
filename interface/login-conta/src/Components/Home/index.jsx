import React, { useState, useEffect } from 'react';
import './index.css';
import backgroundImg from '../Assets/quadra-basquete.jpg';

function App() {
  const [availablePlayers, setAvailablePlayers] = useState([]);
  const [teamPlayers, setTeamPlayers] = useState([]);
  const [money, setMoney] = useState(1000);
  const[time, setTime] = useState();

  const iniciar = async () => {
    const manager = await getManager();
    setMoney(manager['dinheiro']);
    await getPlayers();
    setTime(await getTime());
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
      const response = await fetch("http://localhost:8080/jogador", {
        method: "GET",
        headers: {
          Accept: "application/json",
          Authorization: window.localStorage.getItem("Authorization")
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

  const buyPlayer = async (player) => {
    if (money >= player['valor'] && teamPlayers.length < 5) {
      try {
        const response =  await fetch("http://localhost:8080/contrato", {
          method: "POST",
          headers: {
            "Content-Type": "application/json; charset=utf8",
            Accept: "application/json",
            Authorization: window.localStorage.getItem('Authorization'),
          },
          body: JSON.stringify({
            jogadorId: player['id'],
            timeId: time['id'],
          }),
        });


        const updatedAvailablePlayers = availablePlayers.filter((p) => p.id !== player.id);
        const updatedTeamPlayers = [...teamPlayers, player];
        const updatedMoney = money - player['valor'];

        setAvailablePlayers(updatedAvailablePlayers);
        setTeamPlayers(updatedTeamPlayers);
        setMoney(updatedMoney);
      } catch (error){

      }
    }else {
      console.log('Compra invalida');
    }
  };

  const sellPlayer = async (player) => {
    try {
      const response =  await fetch("http://localhost:8080/contrato", {
        method: "DELETE",
        headers: {
          "Content-Type": "application/json; charset=utf8",
          Accept: "application/json",
          Authorization: window.localStorage.getItem('Authorization'),
        },
        body: JSON.stringify({
          jogadorId: player['id'],
          timeId: time['id'],
        }),
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
                <li key={player['id']}>
                  {player['nome']} - ${player['valor']}
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
                  {player['nome']} - ${player['valor']}
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
