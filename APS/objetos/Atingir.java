package objetos;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;

public class Atingir {
    private double x;
    private double y;
    private final Shape forma;
    private final Color color = new Color(255, 255, 255);
    private final float angulo;
    private double tamanho;
    private float velocidade = 1f;

    public Atingir(double x, double y, float angulo, double tamanho, float velocidade){
        x += Jogador.TAMANHO_J / 2 - (tamanho / 2);
        y += Jogador.TAMANHO_J / 2 - (tamanho / 2);
        this.x = x;
        this.y = y;
        this.angulo = angulo;
        this.tamanho = tamanho;
        this.velocidade = velocidade;
        forma = new Ellipse2D.Double(0,0,tamanho,tamanho);
    }

    public void atualizar(){
        x += Math.cos(Math.toRadians(angulo)) * velocidade;
        y += Math.sin(Math.toRadians(angulo)) * velocidade;
    }

    public boolean check(int width, int height){
        if(x <= -tamanho || y < -tamanho || x > width || y > height){
            return false;
        }
        else{
            return true;
        }
    }

    public void draw(Graphics2D g2){
        AffineTransform transforme = g2.getTransform();
        g2.setColor(color);
        g2.translate(x, y);
        g2.fill(forma);
        g2.setTransform(transforme);
    }

    public double getX(){
        return x;
    }

    public double getY(){
        return y;
    }

    public double getCentroX(){
        return x + tamanho / 2;
    }

    public double getCentroY(){
        return y + tamanho / 2;
    }

    public double getAngulo(){
        return angulo;
    }

    public double getTamanho(){
        return tamanho;
    }

    public Shape getForma(){
        return new Area(new Ellipse2D.Double(x,y,tamanho,tamanho));
    }
}
