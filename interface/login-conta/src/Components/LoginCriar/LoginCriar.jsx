import React, { useState } from 'react'
import './LoginCriar.css'

import email_icon from '../Assets/email.png'
import password_icon from '../Assets/password.png'

export const LoginCriar = () => {

  const [action, setAction] = useState("Registrar");

  return (
    <div className='container'>
      <div className="header">
        <div className="texto">Login</div>
        <div className="sublinhado"></div>
      </div>
      <div className="inputs">
        <div className="input">
          <img src={email_icon} alt="" />
          <input type="email" placeholder="E-mail" id="" />
        </div>
        <div className="input">
          <img src={password_icon} alt="" />
          <input type="senha" placeholder="Senha" id="" />
        </div>
        <div className="enviar-container">
            <div className={action==="Login"?"enviar cinza":"enviar"} 
            onClick={()=>{setAction("Registrar")}}>Registrar</div>
            <div className={action==="Registrar"?"enviar cinza":"enviar"}
            onClick={()=>{setAction("Login")}}>Login</div>
        </div>
      </div>
    </div>
  )
}
