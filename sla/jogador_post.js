import http from 'k6/http';
import { sleep } from 'k6';

export let options = {
    stages: [
        { duration: '5m', target: 50 },
        { duration: '10m', target: 50 },
        { duration: '5m', target: 0 }
    ]
};

export default function () {
    const url = 'http://localhost:8080/jogador';
    const payload = JSON.stringify({
        nome:"LeBron",
        posicao:"SF",
        altura: 206,
        idade: 38,
        valor: 140,
        timeReal: "Lakers",
        pontuacao: 50
    });

    const params = {
        headers: {
            'Content-Type': 'application/json',
            'Authorization': 'Bearer eyJhbGciOiJIUzM4NCJ9.eyJzdWIiOiJlbWFpbEBlbWFpbC5jb20iLCJleHAiOjE3MDAxOTQzMjN9.0bTz7Z_5zx7xWNK8tewwEuS0bX3pY8pD-eMKgneoKNbx0uvXHzuePVF6DMnYeDY1'
        },
    };

    http.post(url, payload, params, );

    sleep(1);
}