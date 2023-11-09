import React, { useState, useEffect } from 'react';
import './index.css';

const Index = () => {
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
              <button>Comprar</button>
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
              <button>Vender</button>
            </li>
          ))}
        </ul>
      </div>
    </div>
  );
};

export default Index;
