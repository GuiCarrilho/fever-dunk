import React, { useState } from 'react';
import './index.css'

function App() {
  // Lista de jogadores disponíveis para compra
  const [availablePlayers, setAvailablePlayers] = useState([
    { id: 1, name: 'Jogador A', price: 100 },
    { id: 2, name: 'Jogador B', price: 150 },
    { id: 3, name: 'Jogador C', price: 120 },
    { id: 4, name: 'Jogador D', price: 120 },
    { id: 5, name: 'Jogador E', price: 120 },
    { id: 6, name: 'Jogador F', price: 120 },
    { id: 7, name: 'Jogador G', price: 120 },
    // Adicione mais jogadores disponíveis aqui
  ]);

  // Lista de jogadores no time
  const [teamPlayers, setTeamPlayers] = useState([]);

  // Dinheiro disponível
  const [money, setMoney] = useState(1000);

  // Função para comprar um jogador
  const buyPlayer = (player) => {
    if (money >= player.price && teamPlayers.length < 5) { // Verifique se o time tem menos de 5 jogadores
      // Remove o jogador da lista de jogadores disponíveis
      const updatedAvailablePlayers = availablePlayers.filter((p) => p.id !== player.id);

      // Adiciona o jogador à lista do time
      const updatedTeamPlayers = [...teamPlayers, player];

      // Atualiza o dinheiro disponível
      const updatedMoney = money - player.price;

      setAvailablePlayers(updatedAvailablePlayers);
      setTeamPlayers(updatedTeamPlayers);
      setMoney(updatedMoney);
    }
  };

  // Função para vender um jogador
  const sellPlayer = (player) => {
    // Remove o jogador da lista do time
    const updatedTeamPlayers = teamPlayers.filter((p) => p.id !== player.id);

    // Adiciona o jogador à lista de jogadores disponíveis
    const updatedAvailablePlayers = [...availablePlayers, player];

    // Atualiza o dinheiro disponível
    const updatedMoney = money + player.price;

    setAvailablePlayers(updatedAvailablePlayers);
    setTeamPlayers(updatedTeamPlayers);
    setMoney(updatedMoney);
  };

  return (
      <div>
      <div className="titulo">Gerencie Seu Time de Basquete!</div>
      <div className="sublinha"></div>
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
