package testes;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import objetos.Atingir;
import objetos.Efeitos;
import objetos.Inimigos;
import objetos.Jogador;

public class teste {
    //Jogador
    @Test
    public void isAlive(){
        Jogador jogador = new Jogador();
        Assertions.assertEquals(true, jogador.isAlive());
    }    

    //Efeitos 
    @Test
    public void distanciaEfeito(){
        Efeitos efeitos = new Efeitos(2, 2, 10, 50, 50, 10, null);
        Assertions.assertEquals(true, efeitos.check());
        
    }

    //Inimigos
    @Test
    public void checkInimigos(){
        Inimigos inimigos = new Inimigos();
        Assertions.assertEquals(true, inimigos.check(20, 50));
    }

    //Atingir
    @Test
    public void checkAtingiu(){
        Atingir atingir = new Atingir(10, 10, 45, 50, 20);
        Assertions.assertEquals(true, atingir.check(20, 50));
    }
}
