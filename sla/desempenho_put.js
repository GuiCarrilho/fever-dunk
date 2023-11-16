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
    const url = 'http://localhost:8080/desempenho';
    const payload = JSON.stringify({
        data: "2023-11-16",
        pontos: 15,
        rebotes: 8,
        assistencias: 5,
        minJogados: 30,
        jogador: {
            id: "65559914371e5a1417e2c600"
        }
    });

    const params = {
        headers: {
            'Content-Type': 'application/json',
            'Authorization': 'Bearer eyJhbGciOiJIUzM4NCJ9.eyJzdWIiOiJlbWFpbEBlbWFpbC5jb20iLCJleHAiOjE3MDAyNTE2OTB9.cnffqjlL7HU3FkTTH-bkkaz0iSFu4fusPiZaPAThw2WZd7Y_pH_XLkVYMHf1x6xY'
        },
    };

    http.put(url, payload, params);


    sleep(1);
}