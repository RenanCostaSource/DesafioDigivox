import React, { useState, useEffect } from 'react';
import { useSelector } from 'react-redux';
import { Bar } from 'react-chartjs-2';


export default function Grafico() {
  const alugueisentrega = useSelector(state => state.alugueisentrega);
  const alugueisretirada = useSelector(state => state.alugueisretirada);
  const reservasentrega = useSelector(state => state.reservasentrega);
  const reservasretirada = useSelector(state => state.reservasretirada);
  const [data, setData] = useState({
    labels: ['Retiradas', 'Devoluções'],
    datasets: [
      {
        label: 'Reservas',
        backgroundColor: '#4A5A9F',
        borderWidth: 1,
        data: [0, 0]
      },
      {
        label: 'Alugueis',
        backgroundColor: '#D9E02C',
        data: [0, 0]
      }
    ]
  });
  useEffect(() => {
    setData({
      labels: ['Retiradas', 'Devoluções'],
      datasets: [
        {
          label: 'Reservas',
          backgroundColor: '#4A5A9F',
          borderWidth: 1,
          data: [reservasretirada.length, reservasentrega.length]
        },
        {
          label: 'Alugueis',
          backgroundColor: '#D9E02C',
          data: [alugueisretirada.length, alugueisentrega.length]
        }
      ]
    })
  }, [alugueisentrega, alugueisretirada, reservasentrega, reservasretirada]);
  return (
    <div>
      <h2>Atividade Semanal</h2>
      <Bar
        data={data}
        options={{
          maintainAspectRatio: false,
          responsive: true,
          scales: {
            yAxes: [{
              ticks: {
                beginAtZero: true
              }
            }]
          }
        }}

      />
    </div>
  );

};