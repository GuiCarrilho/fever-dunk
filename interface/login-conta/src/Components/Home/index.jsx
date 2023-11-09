import React, { useState } from 'react';

function App() {
  // Lista de jogadores disponíveis para compra
  const [availablePlayers, setAvailablePlayers] = useState([
    { id: 1, name: 'Jogador A', price: 100 },
    { id: 2, name: 'Jogador B', price: 150 },
    { id: 3, name: 'Jogador C', price: 120 },
    // Adicione mais jogadores disponíveis aqui
  ]);

  // Lista de jogadores no time
  const [teamPlayers, setTeamPlayers] = useState([]);

  // Dinheiro disponível
  const [money, setMoney] = useState(1000);

  // Função para comprar um jogador
  const buyPlayer = (player) => {
    if (money >= player.price) {
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
      <h1>Monte Seu Time de Basquete</h1>
      <div>
        <h2>Dinheiro Disponível: ${money}</h2>
      </div>
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
  );
}

export default App;
